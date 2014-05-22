/*
 * ProcessSiteWorkflow.java
 *
 * Created on February 6, 2006, 11:41 AM
 *
 */
package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;
import com.cleanwise.service.api.util.PipelineUtil;

/**
 * @author Ykupershmidt
 */
public class ProcessSiteWorkflow implements OrderPipeline {

    public OrderPipelineBaton process(
            OrderPipelineBaton pBaton,
            OrderPipelineActor pActor,
            Connection pCon,
            APIAccess pFactory)
            throws PipelineException {

        try {
            pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
            if (pBaton.getSimpleServiceOrderFl()) {
                return pBaton;
            }

            OrderData orderD = pBaton.getOrderData();
            int siteId = orderD.getSiteId();

            if (siteId <= 0) {
                return pBaton;
            }

            if (pBaton.hasErrors()) {
                return pBaton;
            }

            boolean bypassWorkflow = false;
            if (RefCodeNames.WORKFLOW_IND_CD.SKIP.equals(orderD.getWorkflowInd())) {
                bypassWorkflow = true;
            }

            if (pBaton.getOrderRequestData() != null) {
                if (pBaton.getOrderRequestData().isBypassCustomerWorkflow()) {
                    bypassWorkflow = true;
                }
            }
            
            if (RefCodeNames.ORDER_TYPE_CD.BATCH_ORDER.equals(orderD.getOrderTypeCd())){
            	bypassWorkflow = true;
            }

            if (bypassWorkflow) {
                return pBaton;
            }

            //Set the code that indicates the customer workflow sub system has run against
            //this order
            String workflowInd = orderD.getWorkflowInd();
            int wrSeq = 0;
            if (RefCodeNames.WORKFLOW_IND_CD.TO_RESUME.equals(workflowInd)) {
                OrderMetaDataVector omDV = pBaton.getOrderMetaDataVector();
                for (Iterator iter = omDV.iterator(); iter.hasNext();) {
                    OrderMetaData omD = (OrderMetaData) iter.next();
                    if (RefCodeNames.ORDER_PROPERTY_TYPE_CD.WORKFLOW_STEP.equals(omD.getName())) {
                        int val = omD.getValueNum();
                        if (val < wrSeq) {
                            wrSeq = val;
                        }
                        iter.remove();
                    }
                }
            }
            orderD.setWorkflowInd(RefCodeNames.WORKFLOW_IND_CD.PROCESSED);


            // Get the workflow for the site.
            WorkflowData wD = fetchWorkflowForSite(siteId, pCon);
            if (wD == null) {
                return pBaton;
            }

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(WorkflowRuleDataAccess.WORKFLOW_ID, wD.getWorkflowId());
            if (RefCodeNames.PIPELINE_CD.CHECKOUT_CAPTURE.equals(pBaton.getPipelineTypeCd()))
                crit.addIsNotNull(WorkflowRuleDataAccess.WARNING_MESSAGE);

            crit.addOrderBy(WorkflowRuleDataAccess.RULE_SEQ);
            crit.addOrderBy(WorkflowRuleDataAccess.WORKFLOW_RULE_ID);
            WorkflowRuleDataVector wrDV = WorkflowRuleDataAccess.select(pCon, crit);
            if (wrDV.size() <= 0) {
                return pBaton;
            }
            if (wrSeq != 0) {
                boolean breakPointFound = false;
                for (Iterator iter = wrDV.iterator(); iter.hasNext();) {
                    WorkflowRuleData wrD = (WorkflowRuleData) iter.next();
                    if (wrD.getRuleSeq() == wrSeq &&
                            RefCodeNames.WORKFLOW_RULE_TYPE_CD.BREAK_POINT.equals(wrD.getRuleTypeCd())) {
                        breakPointFound = true;
                        break;
                    }
                }
                if (breakPointFound) {
                    for (Iterator iter = wrDV.iterator(); iter.hasNext();) {
                        WorkflowRuleData wrD = (WorkflowRuleData) iter.next();
                        if (wrD.getRuleSeq() == wrSeq) {
                            break;
                        }
                        iter.remove();
                    }
                }
            }

            //
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:ms");
            HashMap workflowRulesCleanedHM = new HashMap();
            HashSet workflowRulesCleanedApproversHS = new HashSet(); //NG - bug 4692
            OrderPropertyDataVector opDV = pBaton.getOrderPropertyDataVector();
            if (opDV != null) {
                for (Iterator iter = opDV.iterator(); iter.hasNext();) {
                    OrderPropertyData opD = (OrderPropertyData) iter.next();
                    if (opD.getWorkflowRuleId() > 0 && opD.getApproveDate() != null) {
                        workflowRulesCleanedHM.put(new Integer(opD.getWorkflowRuleId()), opD);
                        workflowRulesCleanedApproversHS.add(new Integer(opD.getApproveUserId())); //NG - bug 4692
                    }
                }
            }
            HashMap wrApproverGroupUsersHM = new HashMap(); // NG-bug 4692
            HashMap wrApproverGroupsHM = new HashMap();
            HashSet wrGroupHS = new HashSet();
            boolean firstRuleFl = true;
            int prevRuleSeq = 0;
            WorkflowRuleDataVector currentRuleDV = new WorkflowRuleDataVector();
            LinkedList workflowRuleLL = new LinkedList();
            for (Iterator iter = wrDV.iterator(); iter.hasNext();) {
                WorkflowRuleData wrD = (WorkflowRuleData) iter.next();
                int ruleId = wrD.getWorkflowRuleId();
                int approverGroupId= wrD.getApproverGroupId();
                wrApproverGroupsHM.put(new Integer(ruleId), new Integer(approverGroupId));
                if (approverGroupId > 0 && !wrApproverGroupUsersHM.containsKey(new Integer(approverGroupId)) ){
                  wrApproverGroupUsersHM.put(new Integer(approverGroupId), PipelineUtil.getUsersForGroup(pBaton, approverGroupId)); // NG-bug 4692
                }
                int ruleSeq = wrD.getRuleSeq();
                if (firstRuleFl || prevRuleSeq != ruleSeq) {
                    firstRuleFl = false;
                    prevRuleSeq = ruleSeq;
                    currentRuleDV = new WorkflowRuleDataVector();
                    workflowRuleLL.add(currentRuleDV);
                }
                currentRuleDV.add(wrD);
            }
            pBaton.setApproverGroupsHM(wrApproverGroupsHM);
            pBaton.setApproverGroupUsersHM(wrApproverGroupUsersHM);
            pBaton.setWorkflowRulesCleanedApproversHS(workflowRulesCleanedApproversHS);

            rr:
            for (Iterator iter = workflowRuleLL.iterator(); iter.hasNext();) {
                currentRuleDV = (WorkflowRuleDataVector) iter.next();
                pBaton.setWorkflowRuleDataVector(currentRuleDV);
                WorkflowRuleData wrD = (WorkflowRuleData) currentRuleDV.get(0);
                //was upper rule triggered
                Integer wrIdI = new Integer(wrD.getWorkflowRuleId());
                String ruleGroup = wrD.getRuleGroup();
                if (Utility.isSet(ruleGroup) && wrGroupHS.contains(ruleGroup)) {
                    continue;
                }
                //was the rule cleaned before
                for (Iterator iter1 = currentRuleDV.iterator(); iter1.hasNext();) {
                    WorkflowRuleData wrD1 = (WorkflowRuleData) iter1.next();
                    if (workflowRulesCleanedHM.containsKey(new Integer(wrD1.getWorkflowRuleId()))) {
                        if (Utility.isSet(ruleGroup) && !wrGroupHS.contains(ruleGroup)) {
                            wrGroupHS.add(ruleGroup);
                        }
                        continue rr;
                    }
                }

                // Check apply and skip groups
                IdVector ruleIdV = new IdVector();
                for (Iterator iter1 = currentRuleDV.iterator(); iter1.hasNext();) {
                    WorkflowRuleData wrD1 = (WorkflowRuleData) iter1.next();
                    int ruleId = wrD1.getWorkflowRuleId();
                    ruleIdV.add(new Integer(ruleId));
                }

                // Skip group rules
                DBCriteria dbc = new DBCriteria();
                dbc.addOneOf(WorkflowAssocDataAccess.WORKFLOW_RULE_ID, ruleIdV);
                dbc.addEqualTo(WorkflowAssocDataAccess.WORKFLOW_ASSOC_CD,
                        RefCodeNames.WORKFLOW_ASSOC_CD.SKIP_FOR_GROUP_USERS);

                WorkflowAssocDataVector skipWaDV = WorkflowAssocDataAccess.select(pCon, dbc);
                if (skipWaDV.size() > 0) {

                    IdVector skipGroupIdV = new IdVector();

                    for (Iterator iter2 = skipWaDV.iterator(); iter2.hasNext();) {
                        WorkflowAssocData waD = (WorkflowAssocData) iter2.next();
                        int groupId = waD.getGroupId();
                        skipGroupIdV.add(new Integer(groupId));
                    }

                    dbc = new DBCriteria();
                    dbc.addOneOf(GroupAssocDataAccess.GROUP_ID, skipGroupIdV);
                    dbc.addEqualTo(GroupAssocDataAccess.USER_ID, orderD.getUserId());
                    dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD, RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);

                    IdVector skipUserIdV = GroupAssocDataAccess.selectIdOnly(pCon,
                            GroupAssocDataAccess.USER_ID, dbc);
                    if (skipUserIdV.size() > 0) {
                        continue rr; // Do not apply rule for the user
                    }

                } else { //No skip rules found. Check inforsing rules

                    dbc = new DBCriteria();
                    dbc.addOneOf(WorkflowAssocDataAccess.WORKFLOW_RULE_ID, ruleIdV);
                    dbc.addEqualTo(WorkflowAssocDataAccess.WORKFLOW_ASSOC_CD,
                            RefCodeNames.WORKFLOW_ASSOC_CD.APPLY_FOR_GROUP_USERS);

                    WorkflowAssocDataVector applyWaDV = WorkflowAssocDataAccess.select(pCon, dbc);

                    if (applyWaDV.size() > 0) { // some apply groups found

                        IdVector applyGroupIdV = new IdVector();

                        for (Iterator iter2 = applyWaDV.iterator(); iter2.hasNext();) {
                            WorkflowAssocData waD = (WorkflowAssocData) iter2.next();
                            int groupId = waD.getGroupId();
                            applyGroupIdV.add(new Integer(groupId));
                        }

                        dbc = new DBCriteria();
                        dbc.addOneOf(GroupAssocDataAccess.GROUP_ID, applyGroupIdV);
                        dbc.addEqualTo(GroupAssocDataAccess.GROUP_ASSOC_CD,
                                RefCodeNames.GROUP_ASSOC_CD.USER_OF_GROUP);

                        IdVector applyUserIdV = GroupAssocDataAccess.selectIdOnly(
                                pCon,
                                GroupAssocDataAccess.USER_ID,
                                dbc
                        );

                        if (!applyUserIdV.contains(new Integer(orderD.getUserId()))) {
                            continue rr; // User is not in the list. Do not apply rule;
                        }
                    }
                }

                // Process the rule;
                HashSet emailReceiversHS = pBaton.getEmailReceiversForApproval();
                IdVector usersV = PipelineUtil.getUsersForGroup(pBaton, wrD.getEmailGroupId()) ;
                if (usersV != null) {
                  for (int i = 0; i < usersV.size(); i++) {
                    emailReceiversHS.add(usersV.get(i));
                  }
                  pBaton.setEmailReceiversForApproval(emailReceiversHS);
                }

                String className = wrD.getRuleTypeCd() + "Workflow";
                String packageName = "com.cleanwise.service.api.pipeline";
                Class clazz =
                        Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className);
                Object scratch = clazz.newInstance();
                if (scratch instanceof OrderPipeline) {

                    OrderPipeline poc = (OrderPipeline) scratch;
                    int thisOrderId = 0;
                    if (orderD != null) {
                        thisOrderId = orderD.getOrderId();
                    }

                    try {
                        pBaton.setWorkflowRuleFlag(false);
                        pBaton = poc.process(pBaton, pActor, pCon, pFactory);

                        if (pBaton.getWorkflowRuleFlag() && Utility.isSet(ruleGroup)) {
                            wrGroupHS.add(ruleGroup);
                        }

                    } catch (Exception exc) {
                        throw exc;
                    }


                    OrderData oD = pBaton.getOrderData();
                    String dbOrderStatus = "";

                    if (oD.getOrderId() > 0) {
                        oD = OrderDataAccess.select(pCon, oD.getOrderId());
                        dbOrderStatus = oD.getOrderStatusCd();
                    }

                    String whatNext = pBaton.getWhatNext();

                    if (OrderPipelineBaton.STOP.equals(whatNext)) {
                        break;
                    }
                } else {
                    throw new Exception("Class " + className + " must implement OrderPipeline interface");
                }
            }
            //Return
            return pBaton;
        }
        catch (Exception exc) {
            exc.printStackTrace();
            throw new PipelineException(exc.getMessage());
        }
    }

    //--------------------------------------------------------------------------
    private WorkflowData fetchWorkflowForSite(int pSiteId, Connection pCon)
            throws Exception {

        WorkflowData wfd = null;
        IdVector wids;

        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(SiteWorkflowDataAccess.SITE_ID, pSiteId);

        wids = SiteWorkflowDataAccess.selectIdOnly(
                pCon,
                SiteWorkflowDataAccess.WORKFLOW_ID,
                crit
        );

        for (int ii = 0; ii < wids.size(); ii++) {
            WorkflowData wD = WorkflowDataAccess.select(pCon, ((Integer) wids.get(ii)).intValue());

            if (!RefCodeNames.WORKFLOW_STATUS_CD.INACTIVE.equals(wD.getWorkflowStatusCd()) &&
                    RefCodeNames.WORKFLOW_TYPE_CD.ORDER_WORKFLOW.equals(wD.getWorkflowTypeCd())) {
                if (wfd == null) {
                    wfd = wD;
                } else {
                    break;
                }
            }
        }

        return wfd;
    }

}
