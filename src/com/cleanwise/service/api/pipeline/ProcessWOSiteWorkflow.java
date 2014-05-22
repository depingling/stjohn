/*
 * ProcessSiteWorkflow.java
 *
 * Created on February 6, 2006, 11:41 AM
 *
 */
package com.cleanwise.service.api.pipeline;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.process.workflow.ProcessWorkflow;
import com.cleanwise.service.api.process.workflow.RuleRequestData;
import com.cleanwise.service.api.process.workflow.RuleResponseData;
import com.cleanwise.service.api.process.workflow.WOProcessingData;
import com.cleanwise.service.api.process.workflow.WORuleRequestData;
import com.cleanwise.service.api.process.workflow.WorkfolowProcessingData;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.Group;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.session.WorkOrder;
import com.cleanwise.service.api.session.Workflow;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.EventEmailDataView;
import com.cleanwise.service.api.value.GroupSearchCriteriaView;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UserAccountRightsView;
import com.cleanwise.service.api.value.UserAccountRightsViewVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.service.api.value.WorkOrderData;
import com.cleanwise.service.api.value.WorkOrderDetailView;
import com.cleanwise.service.api.value.WorkOrderPropertyData;
import com.cleanwise.service.api.value.WorkflowData;
import com.cleanwise.service.api.value.WorkflowRuleData;
import com.cleanwise.service.api.value.WorkflowRuleDataVector;
import com.cleanwise.service.api.value.WorkflowWoQueueData;
import com.cleanwise.service.apps.ApplicationsEmailTool;

import org.apache.log4j.Logger;

/**
 * @author Evgeny Vlasov
 */
public class ProcessWOSiteWorkflow {
    private final static Logger log = Logger.getLogger(ProcessWOSiteWorkflow.class);
    private final static String className = "ProcessWOSiteWorkflow";
    private String methodName;

    private static final Comparator RULE_SEQ_COMPARE = new Comparator() {
        public int compare(Object o1, Object o2) {
            int seq1 = ((WorkflowRuleData) o1).getRuleSeq();
            int seq2 = ((WorkflowRuleData) o2).getRuleSeq();
            return seq1 - seq2;
        }
    };

    public WOProcessingData process(WorkOrderDetailView workOrderDetailView) throws Exception {
        methodName = "process";

        WOProcessingData woProcessingData = new WOProcessingData(workOrderDetailView);
        WorkOrderData orderD = workOrderDetailView.getWorkOrder();

        try {

            Workflow workflowEjb = APIAccess.getAPIAccess().getWorkflowAPI();

            int siteId = orderD.getBusEntityId();

            if (siteId <= 0) {
                log.info("END, CHECK_ERROR, No Site Id For Work Order=" + orderD.getWorkOrderId());
                woProcessingData.setWhatNext(WorkfolowProcessingData.CHECK_ERROR);
                woProcessingData.setReason("No Site Id For Work Order=" + orderD.getWorkOrderId());
                return woProcessingData;
            }

            WorkflowData workflow = null;

            try{
                workflow = workflowEjb.fetchWorkflowForSite(siteId, RefCodeNames.WORKFLOW_TYPE_CD.WORK_ORDER_WORKFLOW);
            }catch(DataNotFoundException ex){
                log.info("END, STOP_AND_RETURN, No configured Workflow  For Site=" + siteId);
                woProcessingData.setWhatNext(WorkfolowProcessingData.STOP_AND_RETURN);
                woProcessingData.setReason("No configured Workflow  For Site=" + siteId);
                return woProcessingData;
            }
            if (workflow == null) {
                log.info("END, STOP_AND_RETURN, No configured Workflow  For Site=" + siteId);
                woProcessingData.setWhatNext(WorkfolowProcessingData.STOP_AND_RETURN);
                woProcessingData.setReason("No configured Workflow  For Site=" + siteId);
                return woProcessingData;
            }

            WorkflowRuleDataVector workflowRules = workflowEjb.getWorkflowRulesCollection(workflow.getWorkflowId());
            if (workflowRules.size() <= 0) {
                log.info("END, STOP_AND_RETURN, No configured Rules For WF=" + workflow.getWorkflowId());
                woProcessingData.setWhatNext(WorkfolowProcessingData.STOP_AND_RETURN);
                woProcessingData.setReason("No configured Rules For WF=" + workflow.getWorkflowId());
                return woProcessingData;
            }

            Collections.sort(workflowRules, RULE_SEQ_COMPARE);

            for (Iterator iter = workflowRules.iterator(); iter.hasNext();) {

                //get Current Rule for processing
                WorkflowRuleData wrD = (WorkflowRuleData) iter.next();

                //Create Instance for running the rule class;
                String clazzName = wrD.getRuleTypeCd() + "Workflow";
                String packageName = "com.cleanwise.service.api.process.workflowrules";
                Class clazz = Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + clazzName);
                Object scratch = clazz.newInstance();

                if (scratch instanceof ProcessWorkflow) {

                    ProcessWorkflow rule = (ProcessWorkflow) scratch;

                    RuleRequestData workflowRuleRequest = new WORuleRequestData(wrD,workOrderDetailView);
                    RuleResponseData workfolowRuleResult = rule.process(workflowRuleRequest);

                    woProcessingData.processRuleResult(workfolowRuleResult);

                    if (WorkfolowProcessingData.FORWARD_FOR_APPROVE.equals(woProcessingData.getWhatNext())) {
                        performRuleAction(workOrderDetailView, wrD);
                    }
                } else {
                    woProcessingData.setWhatNext(WorkfolowProcessingData.CHECK_ERROR);
                    woProcessingData.setReason("Class " + className + " must implement ProcessWorkflow interface");
                    return woProcessingData;
                }
            }

            woProcessingData.setWhatNext(WorkfolowProcessingData.STATUS_PASSED);             
            //Return
            return woProcessingData;
        } catch (Exception exc) {
            woProcessingData.setWhatNext(WorkfolowProcessingData.CHECK_ERROR);
            woProcessingData.setReason(exc.getMessage());
            exc.printStackTrace();
            throw new Exception(exc.getMessage());
        }
    }


    private void performRuleAction(WorkOrderDetailView workOrderDV, WorkflowRuleData wrD) throws Exception {
        String lineSeparator = System.getProperty("line.separator");

        WorkOrder wrkEjb = APIAccess.getAPIAccess().getWorkOrderAPI();
        Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();
        Store storeEjb = APIAccess.getAPIAccess().getStoreAPI();
        User userEjb = APIAccess.getAPIAccess().getUserAPI();
        PropertyService propertyEjb = APIAccess.getAPIAccess().getPropertyServiceAPI();
        Workflow workflowEjb = APIAccess.getAPIAccess().getWorkflowAPI();
        
        int siteId = workOrderDV.getWorkOrder().getBusEntityId();
        AccountData accountD = accountEjb.getAccountForSite(siteId);
        int accountId = accountD.getAccountId();
        int storeId = storeEjb.getStoreIdByAccount(accountId);
        StoreData storeD = storeEjb.getStore(storeId);
        
        Date currentDate = new Date();
        String statusChangeTime = new SimpleDateFormat("MM/dd/yyyy hh:mm").format(currentDate);
        String action = wrD.getRuleAction();
        
        //write triggered workflow rule info to clw_work_order_property table
        WorkOrderPropertyData woPropertyD = WorkOrderPropertyData.createValue();
            woPropertyD.setWorkOrderPropertyId(0);
            woPropertyD.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.WORKFLOW_RULE);
            woPropertyD.setShortDesc(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.WORKFLOW_RULE);
            woPropertyD.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
            woPropertyD.setValue(String.valueOf(wrD.getWorkflowRuleId()));
            woPropertyD.setWorkOrderId(workOrderDV.getWorkOrder().getWorkOrderId());
        
        UserData userNameBox = UserData.createValue();
            userNameBox.setUserName("woWorkflow");
        
        wrkEjb.updateWorkOrderProperty(woPropertyD, userNameBox);
        
        if (RefCodeNames.WORKFLOW_RULE_ACTION.FWD_FOR_APPROVAL.equals(action)) {
            WorkOrderData currentWO = wrkEjb.getWorkOrder(workOrderDV.getWorkOrder().getWorkOrderId());
            if (!RefCodeNames.WORK_ORDER_STATUS_CD.PENDING_APPROVAL.equals(currentWO.getStatusCd())) {
                wrkEjb.updateStatus(workOrderDV.getWorkOrder().getWorkOrderId(), RefCodeNames.WORK_ORDER_STATUS_CD.PENDING_APPROVAL, className);
            }
            
            UserDataVector users = userEjb.getAllActiveUsers(siteId, User.ORDER_BY_ID);
            Iterator usersIt = users.iterator();

            IdVector accts = new IdVector();
            accts.add(new Integer(accountId));
            //get account permissions for users
            UserData uD;
            String rights = null;
            while (usersIt.hasNext()) {
                uD = (UserData)usersIt.next();
                try {
                    UserAccountRightsViewVector uRightsView = userEjb.getUserAccountRights(uD.getUserId(), accts);
                    if (uRightsView != null && uRightsView.size() > 0) {
                        // it is just one account in 'accts'
                        UserAccountRightsView uView = (UserAccountRightsView)uRightsView.get(0);
                        PropertyData userSettings = uView.getUserSettings();
                        rights = userSettings.getValue();
                    } else {
                        rights = uD.getUserRoleCd();                    
                    }
                    if (rights != null) {
                        if (rights.indexOf(UserInfoData.USER_GETS_EMAIL_ORDER_NEEDS_APPROVAL) <= 0) {
                            usersIt.remove();
                        }
                    } else {
                        usersIt.remove();
                    }
                } catch (Exception e){
                    log.info("User Account rights not set. UserId: " + uD.getUserId() + " AccountId: " + accountId);
                }
            }
            
            // In case the ApproversGroup or EmailUsersGroup is set
            int groupId = 0;
            UserDataVector groupUsers = new UserDataVector();
            if ( wrD.getEmailGroupId() > 0) {
                groupId = wrD.getEmailGroupId();
            } else if (wrD.getApproverGroupId() > 0) {
                groupId = wrD.getApproverGroupId();
            }

            if (groupId > 0) {
                Group groupEjb = APIAccess.getAPIAccess().getGroupAPI();

                GroupSearchCriteriaView gsc = GroupSearchCriteriaView.createValue();
                gsc.setGroupId(groupId);
                gsc.setStoreId(storeId);
                try {
                    groupUsers = groupEjb.getUsersForGroup(gsc, Group.NAME_EXACT, Group.ORDER_BY_NAME);
                } catch (RemoteException e) {}
            }
            
            if (!groupUsers.isEmpty()) {
                if (!users.isEmpty()) {
                    HashSet userIdsEmailTo = new HashSet();
                    //Put all the usersId to preliminary id list
                    usersIt = users.iterator();
                    while (usersIt.hasNext()) {
                        uD = (UserData)usersIt.next();
                        userIdsEmailTo.add(Integer.valueOf(uD.getUserId()));
                    }
                    //Update the 'users' list with the users from 'groupUsers'
                    usersIt = groupUsers.iterator();
                    while (usersIt.hasNext()) {
                        uD = (UserData)usersIt.next();
                        if (!userIdsEmailTo.contains(Integer.valueOf(uD.getUserId()))) {
                            users.add(uD);
                        }
                    }
                } else {
                    users.addAll(groupUsers);
                }
            }

            if (!users.isEmpty()) {
                WorkOrderData woD = workOrderDV.getWorkOrder();
                
                StringBuffer subject = new StringBuffer();
                subject.append("Work Order: ")
                       .append(woD.getWorkOrderNum())
                       .append(" (")
                       .append(woD.getShortDesc())
                       .append(") has been pended for approval");
                
                StringBuffer automatedMessage = new StringBuffer();
                automatedMessage.append("************************************************************************************************")
                                .append(lineSeparator)
                                .append("This is an automated email. Do not reply to this email.")
                                .append(lineSeparator)
                                .append(lineSeparator)
                                .append("The Work Order with Number ")
                                .append(woD.getWorkOrderNum())
                                .append(" (")
                                .append(woD.getShortDesc())
                                .append(")");
                if (woD != null && accountD != null && storeD != null) {
                    automatedMessage.append(" from Store: ")
                                    .append(storeD.getBusEntity().getShortDesc())
                                    .append("/Account: ")
                                    .append(accountD.getBusEntity().getShortDesc());
                }
                automatedMessage.append(" has been pended for approval. Triggered rule: ")
                                .append(wrD.getRuleTypeCd())
                                .append(" (")
                                .append(wrD.getRuleExp())
                                .append(" ")
                                .append(wrD.getRuleExpValue())
                                .append(")")
                                .append(lineSeparator)
                                .append(statusChangeTime)
                                .append(lineSeparator)
                                .append("************************************************************************************************")
                                .append(lineSeparator);
                
                String emailFromAddress = "";
                try {
                    emailFromAddress = propertyEjb.getBusEntityProperty(storeId, RefCodeNames.PROPERTY_TYPE_CD.WORK_ORDER_EMAIL_ADDRESS);
                } catch(DataNotFoundException e) {
                    log.info("Work order email not found for store " + storeId);
                }

                if (!(emailFromAddress.trim().length() > 0)) {
                    if (storeD != null && storeD.getPrimaryEmail() != null) {
                            emailFromAddress = storeD.getPrimaryEmail().getEmailAddress();
                    }
                }

                UserInfoData userInfo;
                EventEmailDataView eventEmail;
                String emailToAddress;
                usersIt = users.iterator();
                UserData userD;
                
                while (usersIt.hasNext()) {
                    userD = (UserData)usersIt.next();
                    userInfo = userEjb.getUserContactForNotification(userD.getUserId());

                    emailToAddress = null;
                    if (userInfo.getEmailData() != null) {
                        emailToAddress = userInfo.getEmailData().getEmailAddress();
                    }

                    if (Utility.isSet(emailToAddress)) {
                        eventEmail = new EventEmailDataView();

                        eventEmail.setFromAddress(emailFromAddress);
                        eventEmail.setToAddress(emailToAddress);
                        eventEmail.setSubject(subject.toString());
                        eventEmail.setText(automatedMessage.toString());
                        eventEmail.setEmailStatusCd(Event.STATUS_READY);
                        new ApplicationsEmailTool().createEvent(eventEmail);
                    }
                }
            }
            
            //Make the record in the CLW_WORKFLOW_WO_QUEUE table if needed
            int actionDays = 0;
            String nextActionType = null;
            if (Utility.isSet(wrD.getNextActionCd())) {
                String nextAction = wrD.getNextActionCd().trim();
                if (nextAction.startsWith("After")) {
                    int ind1 = nextAction.indexOf("days");
                    if (ind1 > 6) { //there is a number between
                        String days = nextAction.substring(6,ind1).trim();
                        try {
                            actionDays = Integer.parseInt(days);
                        } catch (NumberFormatException e) {}
                    }
                    if(nextAction.indexOf(RefCodeNames.WORKFLOW_RULE_ACTION.APPROVE_ORDER) != -1) {
                        nextActionType = RefCodeNames.WORKFLOW_RULE_ACTION.APPROVE_ORDER;
                    }
                    else if(nextAction.indexOf(RefCodeNames.WORKFLOW_RULE_ACTION.REJECT_ORDER) != -1) {
                        nextActionType = RefCodeNames.WORKFLOW_RULE_ACTION.REJECT_ORDER;
                    }
                }
                if (actionDays != 0 && nextActionType != null) { // put a record to the CLW_WORKFLOW_WO_QUEUE
                    WorkflowWoQueueData workflowWOD = WorkflowWoQueueData.createValue();
                    
                    workflowWOD.setShortDesc(RefCodeNames.WORKFLOW_RULE_ACTION.PENDING_REVIEW);
                    workflowWOD.setBusEntityId(workOrderDV.getWorkOrder().getBusEntityId());
                    workflowWOD.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER);
                    workflowWOD.setActionDays(actionDays);
                    workflowWOD.setActionType(nextActionType);
                    workflowWOD.setAddBy("woWorkflow");
                    workflowWOD.setModBy("woWorkflow");
                    workflowWOD.setStatusCd(RefCodeNames.WORKFLOW_STATUS_CD.ACTIVE);
                    workflowWOD.setWorkflowId(wrD.getWorkflowId());
                    workflowWOD.setWorkOrderId(workOrderDV.getWorkOrder().getWorkOrderId());
                    workflowWOD.setWorkflowRuleId(wrD.getWorkflowRuleId());
                    
                    workflowEjb.createWorkflowWoQueueEntry(workflowWOD);
                }
            }
        } else if (RefCodeNames.WORKFLOW_RULE_ACTION.SEND_EMAIL.equals(action)) {
            //send an email to the contact person if present
            //or to all users of ApproversGroup
            //or to all users of EmailUsersGroup

            UserDataVector emailToUsers = new UserDataVector();
            String nextAction = wrD.getNextActionCd();
            
            UserData emailUser = null;
            if (Utility.isSet(nextAction)) {
                int ind0;
                int ind1;
                if ((ind0 = nextAction.lastIndexOf("Id:")) > -1 ) {
                    ind0 += "Id:".length();
                    ind1 = nextAction.indexOf(".", ind0);
                    if (ind1 > ind0) {
                        String userIdStr = nextAction.substring(ind0, ind1).trim();
                        try {
                            int userId = Integer.parseInt(userIdStr);
                            emailUser = userEjb.getUser(userId);
                            if (emailUser != null) {
                                emailToUsers.add(emailUser);
                            }
                        } catch (NumberFormatException e) {
                        } catch (DataNotFoundException e) {} 
                    }
                }
            }
            
            if (emailUser == null) {
                int groupId = 0;
                if ( wrD.getEmailGroupId() > 0) {
                    groupId = wrD.getEmailGroupId();
                } else if (wrD.getApproverGroupId() > 0) {
                    groupId = wrD.getApproverGroupId();
                }
                
                if (groupId > 0) {
                    Group groupEjb = APIAccess.getAPIAccess().getGroupAPI();
                    
                    GroupSearchCriteriaView gsc = GroupSearchCriteriaView.createValue();
                    gsc.setGroupId(groupId);
                    gsc.setStoreId(storeId);
                    try {
                        emailToUsers = groupEjb.getUsersForGroup(gsc, Group.NAME_EXACT, Group.ORDER_BY_NAME);
                    } catch (RemoteException e) {}
                }
            }
            
            if (!emailToUsers.isEmpty()) {
                WorkOrderData woD = workOrderDV.getWorkOrder();
                
                StringBuffer subject = new StringBuffer();
                subject.append("Work Order: ")
                       .append(woD.getWorkOrderNum())
                       .append(" (")
                       .append(woD.getShortDesc())
                       .append(") has triggered Workflow Rule: " + wrD.getRuleTypeCd());
                
                StringBuffer automatedMessage = new StringBuffer();
                automatedMessage.append("************************************************************************************************")
                                .append(lineSeparator)
                                .append("This is an automated email. Do not reply to this email.")
                                .append(lineSeparator)
                                .append(lineSeparator)
                                .append("The Work Order with Number ")
                                .append(woD.getWorkOrderNum())
                                .append(" (")
                                .append(woD.getShortDesc())
                                .append(")");
                if (woD != null && accountD != null && storeD != null) {
                    automatedMessage.append(" from Store: ")
                                    .append(storeD.getBusEntity().getShortDesc())
                                    .append("/Account: ")
                                    .append(accountD.getBusEntity().getShortDesc());
                }
                automatedMessage.append(" has triggered Workflow Rule: ")
                                .append(wrD.getRuleTypeCd())
                                .append(" (")
                                .append(wrD.getRuleExp())
                                .append(" ")
                                .append(wrD.getRuleExpValue())
                                .append(")")
                                .append(lineSeparator)
                                .append(statusChangeTime)
                                .append(lineSeparator)
                                .append("************************************************************************************************")
                                .append(lineSeparator);
                
                String emailFromAddress = "";
                try {
                    emailFromAddress = propertyEjb.getBusEntityProperty(storeId, RefCodeNames.PROPERTY_TYPE_CD.WORK_ORDER_EMAIL_ADDRESS);
                } catch(DataNotFoundException e) {
                    log.info("Work order email not found for store " + storeId);
                }

                if (!(emailFromAddress.trim().length() > 0)) {
                    if (storeD != null && storeD.getPrimaryEmail() != null) {
                            emailFromAddress = storeD.getPrimaryEmail().getEmailAddress();
                    }
                }

                UserInfoData userInfo;
                EventEmailDataView eventEmail;
                String emailToAddress;
                Iterator usersIt = emailToUsers.iterator();
                UserData userD;
                
                while (usersIt.hasNext()) {
                    userD = (UserData)usersIt.next();
                    userInfo = userEjb.getUserContactForNotification(userD.getUserId());

                    emailToAddress = null;
                    if (userInfo.getEmailData() != null) {
                        emailToAddress = userInfo.getEmailData().getEmailAddress();
                    }

                    if (Utility.isSet(emailToAddress)) {
                        eventEmail = new EventEmailDataView();

                        eventEmail.setFromAddress(emailFromAddress);
                        eventEmail.setToAddress(emailToAddress);
                        eventEmail.setSubject(subject.toString());
                        eventEmail.setText(automatedMessage.toString());
                        eventEmail.setEmailStatusCd(Event.STATUS_READY);
                        new ApplicationsEmailTool().createEvent(eventEmail);
                    } else {
                        log.info("processWOSiteWorkflow() => WARNING: No EmailTo Address for userID: " + userD.getUserId() +
                            " (" + userD.getUserName() + ")");
                    }
                }
            }
        }
    }
}
