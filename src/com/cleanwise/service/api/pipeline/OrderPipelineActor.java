package com.cleanwise.service.api.pipeline;

import java.util.ArrayList;
import java.util.Iterator;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.Utility;

import java.sql.*;
import java.text.NumberFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import com.cleanwise.service.api.util.PipelineUtil;
import org.apache.log4j.Logger;

/**
 * This class is the central location to store order error constantc
 * @author      Yuriy Kupershmidt
 *
 */
public class OrderPipelineActor {
  private static final Logger log = Logger.getLogger(OrderPipelineActor.class);
  private int MAX_ORDER_SPLIT = 10;
  private int MAX_PIPELINE_LENGTH = 100;
  ArrayList orderPipelineBatons = new ArrayList();
  String pipleLineTypeCd = null;
  int currNumber = 0;
  PipelineDataVector pipelineDV = null;


  public OrderPipelineBaton[] processPipeline(OrderPipelineBaton pBaton,
                                              String pPipelineType,
                                              Connection pCon,
                                              APIAccess pFactory) throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:ms");
    Date startTime = new Date();
    String startTimeS = sdf.format(startTime);
    String logMessage = " Pipeline: <" + pPipelineType +
                        "> Started at: <" + startTimeS + ">";
    if (currNumber == 0) {
      pBaton.setBatonNumber(currNumber);
      currNumber++;
      DBCriteria dbc = new DBCriteria();
      if (pBaton.getBypassOptional()) {
        String cond = "(nvl(" + PipelineDataAccess.OPTIONAL + ",0) =0 ";
        dbc.addCondition(cond);
      }
      dbc.addEqualTo(PipelineDataAccess.PIPELINE_TYPE_CD, pPipelineType);
      dbc.addEqualTo(PipelineDataAccess.PIPELINE_STATUS_CD, RefCodeNames.PIPELINE_STATUS_CD.ACTIVE);
      dbc.addOrderBy(PipelineDataAccess.PIPELINE_ORDER);
      pipelineDV = PipelineDataAccess.select(pCon, dbc);
      int index = 0;
      if (OrderPipelineBaton.GO_BREAK_POINT.equals(pBaton.getWhatNext())) {
        OrderMetaDataVector omDV = pBaton.getOrderMetaDataVector();
        int pipelineId = 0;
        for (Iterator iter = omDV.iterator(); iter.hasNext(); ) {
          OrderMetaData omD = (OrderMetaData) iter.next();
          boolean fl1 = RefCodeNames.WORKFLOW_IND_CD.TO_RESUME.equals(omD.getName());
          if (RefCodeNames.ORDER_PROPERTY_TYPE_CD.PIPELINE_STEP.equals(omD.getName()) &&
              pPipelineType.equals(omD.getValue())) {
            pipelineId = omD.getValueNum();
            break;
          }
        }
        if (pipelineId > 0) {
          for (Iterator iter = pipelineDV.iterator(); iter.hasNext(); ) {
            PipelineData pD = (PipelineData) iter.next();
            if (pipelineId == pD.getPipelineId()) {
              break;
            }
            index++;
          }
        }
      }
      pBaton.setStepNum(index);
      orderPipelineBatons.add(pBaton);
    } else {
      String mess = "Wrong OrderPipelineActor usage. Second start of pipeline" +
                    "when the first one is active";
      throw new Exception(mess);
    }
    for (int ii = 0; ii < orderPipelineBatons.size(); ii++) {
      OrderPipelineBaton baton = null;
      try {
        Date threadStartTime = new Date();
        String threadStartTimeS = sdf.format(threadStartTime);
        baton = (OrderPipelineBaton) orderPipelineBatons.get(ii);
        processPipeline(baton, startTimeS, threadStartTimeS, pCon, pFactory);
      } finally {
        if (baton != null && baton.isDoNoUpdateOrderStatusCd() && baton.getOrderData() != null &&
            baton.getOrderData().getOrderId() != 0) {
          String sql = "Update " + OrderDataAccess.CLW_ORDER + " set " + OrderDataAccess.ORDER_STATUS_CD + "='" +
                       baton.getStartingOrderStatusCd() + "' where " + OrderDataAccess.ORDER_ID + "=" +
                       baton.getOrderData().getOrderId();
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sql);
          stmt.close();
        }
      }
    }
    OrderPipelineBaton[] pipelineResult =
      new OrderPipelineBaton[orderPipelineBatons.size()];
    for (int ii = 0; ii < pipelineResult.length; ii++) {
      pipelineResult[ii] = (OrderPipelineBaton) orderPipelineBatons.get(ii);

    }
    return pipelineResult;
  }

//------------------------------------------------------------------------------
  private void processPipeline(OrderPipelineBaton pBaton,
                               String pPipelineStartTime,
                               String pPipelineThreadStartTime,
                               Connection pCon,
                               APIAccess pFactory) throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:ms");
    int index = pBaton.getStepNum();
    int loop = -1;
    while (index < pipelineDV.size()) {
      loop++;
      if (loop > MAX_PIPELINE_LENGTH) {
        String errorMess = "The pipeline step = " + loop +
                           " Probably endless loop. ";
        throw new Exception(errorMess);
      }
      PipelineData pipeline = (PipelineData) pipelineDV.get(index);
      int pipelineAcctStoreId = pipeline.getBusEntityId();
      if (pipelineAcctStoreId > 0) { //skip step if account/store ids are different
        OrderData orderD = pBaton.getOrderData();
        if (orderD != null) {

          int reqAccountId = orderD.getAccountId();
          int reqStoreId = orderD.getStoreId();
          if (pipelineAcctStoreId != reqAccountId &&
              pipelineAcctStoreId != reqStoreId) {
            index++;
            pBaton.setStepNum(index);
            continue;
          }
        }
      }
      pBaton.setPipelineData(pipeline);
      if (pipeline.getClassname() == null) {
        throw new Exception("Classname configured for pipeline: " +
                            pipeline.getPipelineId() + " cannot be null.");
      }
      Class clazz = Thread.currentThread().getContextClassLoader().loadClass(pipeline.getClassname());
      Object scratch = clazz.newInstance();
      if (scratch instanceof OrderPipeline) {

        OrderPipeline poc = (OrderPipeline) scratch;
        String className = pipeline.getClassname();
        int lastPointInd = className.lastIndexOf('.');
        if (lastPointInd > 0) className = className.substring(lastPointInd + 1);

        OrderData orderD = pBaton.getOrderData();
        int thisOrderId = 0;
        if (orderD != null) {
          thisOrderId = orderD.getOrderId();
        }

        Date startTime = new Date();
        String startTimeS = sdf.format(startTime);
        String logMessage = " Pipeline step: <" + className +
                            "> Pipeline stated at: <" + pPipelineStartTime +
                            "> Thread stated at: <" + pPipelineThreadStartTime +
                            "> Step stated at: <" + startTimeS + ">"
                            + " orderId=" + thisOrderId + " ";

        log.info("@@PPSS@@S" + logMessage + "@@PPSS@@");
        try {
          pBaton = poc.process(pBaton, this, pCon, pFactory);
        } catch (Exception exc) {
          Date endTime = new Date();
          String endTimeS = sdf.format(endTime);
          double dur = (double) endTime.getTime() - startTime.getTime();
          dur /= 1000;
          logMessage += " Step ended at: <" + endTimeS +
            "> duration: <" + dur +
            "> result: <Error>";
          log.info("@@PPSS@@E" + logMessage + "@@PPSS@@");
          throw exc;
        }
        Date endTime = new Date();
        String endTimeS = sdf.format(endTime);
        double dur = (double) endTime.getTime() - startTime.getTime();
        dur /= 1000;

        OrderData oD = pBaton.getOrderData();
        String dbOrderStatus = "";
        if (oD != null && oD.getOrderId() > 0) {
          oD = OrderDataAccess.select(pCon, oD.getOrderId());
          dbOrderStatus = oD.getOrderStatusCd();
        }

        String whatNext = pBaton.getWhatNext();
        if (OrderPipelineBaton.GO_FIRST_STEP.equals(whatNext)) {
          index = 0;
          pBaton.setStepNum(index);
          continue;
        } else if (OrderPipelineBaton.REPEAT.equals(whatNext)) {
          continue;
        } else if (OrderPipelineBaton.STOP.equals(whatNext)) {
          break;
        } else {
          index = pBaton.getStepNum() + 1;
          pBaton.setStepNum(index);
          continue;
        }
      } else {
        throw new Exception("Classname configured for pipeline: " +
                            pipeline.getPipelineId() + " must implement: " + OrderPipeline.class.getName());
      }
    }

  }

  //----------------------------------------------------------------------------
  public void addPipeline(OrderPipelineBaton pBaton, String pPipelineType, Connection pCon) throws Exception {
    DBCriteria dbc = new DBCriteria();
    if (pBaton.getBypassOptional()) {
      String cond = "(nvl(" + PipelineDataAccess.OPTIONAL + ",0) =0 ";
      dbc.addCondition(cond);
    }
    dbc.addEqualTo(PipelineDataAccess.PIPELINE_TYPE_CD, pPipelineType);
    dbc.addEqualTo(PipelineDataAccess.PIPELINE_STATUS_CD, RefCodeNames.PIPELINE_STATUS_CD.ACTIVE);
    dbc.addOrderBy(PipelineDataAccess.PIPELINE_ORDER);
    PipelineDataVector addPipelineDV = PipelineDataAccess.select(pCon, dbc);
    int index = pBaton.getStepNum();
    for (Iterator iter = addPipelineDV.iterator(); iter.hasNext(); ) {
      pipelineDV.add(++index, iter.next());
    }

  }

  //----------------------------------------------------------------------------
  public void addOrderToPipeline(OrderPipelineBaton pBaton) throws Exception {
    if (currNumber == 0) {
      String errorMess = "Wrong method usage. Should call processPipeline method fird";
      throw new Exception(errorMess);
    }
    currNumber++;
    orderPipelineBatons.add(pBaton);
    if (currNumber > MAX_ORDER_SPLIT) {
      String errorMess = "The pipeline created more than " + (MAX_ORDER_SPLIT - 1) +
                         " new orders. Probably program error";
      throw new Exception(errorMess);
    }
    if (OrderPipelineBaton.GO_NEXT.equals(pBaton.getWhatNext())) {
      pBaton.setStepNum(pBaton.getStepNum() + 1);
    } else if (OrderPipelineBaton.REPEAT.equals(pBaton.getWhatNext())) {
      //nothing to change
    } else {
      pBaton.setStepNum(0);
    }
  }

  ////////////////////////////////////////////////////////////////////////////
  ///////////////// pipeline common methods //////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////

  public static boolean performRuleAction
    (Connection pCon,
     OrderPipelineBaton pBaton,
     WorkflowRuleData pRule,
     String pErrorMessKey,
     CostCenterData pOptionalCostCenterData,
     String pBypassWkflRuleActionCd
    ) {
     return performRuleAction(pCon, pBaton, pRule, pErrorMessKey, null, null, pOptionalCostCenterData, pBypassWkflRuleActionCd);
  }

    public static boolean performRuleAction(
            Connection pCon,
            OrderPipelineBaton pBaton,
            WorkflowRuleData pRule,
            String pErrorMessKey,
            Object[] pErrorMessArgs,
            String[] pErrorMessArgsTypes,
            CostCenterData pOptionalCostCenterData,
            String pBypassWkflRuleActionCd
    ) {
    	if (RefCodeNames.PIPELINE_CD.CHECKOUT_CAPTURE.equals(pBaton.getPipelineTypeCd())){
      	  	pBaton.addResultMessage(pRule.getWarningMessage());
      	  	return true;
    	}
        String msg = "  Workflow rule triggered: \n" + pRule;
        
        WorkflowRuleResult rr = new WorkflowRuleResult();
    	
        OrderData orderD = pBaton.getOrderData();
        rr.setRule(pRule);
        rr.setStatus(WorkflowRuleResult.GOTO_NEXT_RULE);
        if (pBypassWkflRuleActionCd.equals(pRule.getRuleAction())) {
            return rr.isGotoNextRule();
        }
        if (skipRuleAction(pCon, pBaton, pRule )) {
          return rr.isGotoNextRule();

        }
        if (pOptionalCostCenterData != null) {
            rr.setCostCenterId(pOptionalCostCenterData.getCostCenterId());
            rr.setCostCenterName(pOptionalCostCenterData.getShortDesc());
            msg += " \n for cost center : " + pOptionalCostCenterData + "  == \n";
        }

        //String message = pErrorMess;
        String message = "";
        //Reject order

        // make error message with params and types
        Object[] errorMessArgs;
        String[] errorMessArgsTypes;
        if (!Utility.isSet(pErrorMessKey)) {
            errorMessArgs = new Object[3];
            errorMessArgsTypes = new String[3];
            errorMessArgs[0] = pRule.getRuleTypeCd();
            errorMessArgs[1] = pRule.getRuleExp();
            errorMessArgs[2] = pRule.getRuleExpValue();
            errorMessArgsTypes[0] = RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING;
            errorMessArgsTypes[1] = RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING;
            errorMessArgsTypes[2] = RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING;
        } else {
            if (pErrorMessArgs == null) {
                errorMessArgs = new Object[1];
                errorMessArgsTypes = new String[1];
            } else {
                errorMessArgs = new Object[pErrorMessArgs.length + 1];
                errorMessArgsTypes = new String[pErrorMessArgsTypes.length + 1];
            }
            errorMessArgs[0] = pErrorMessKey;
            errorMessArgsTypes[0] = RefCodeNames.PIPELINE_MESSAGE_ARG_CD.MESSAGE_KEY;

            if (pErrorMessArgs != null) {
                for (int i=0; i<pErrorMessArgs.length; i++) {
                    errorMessArgs[i+1] = pErrorMessArgs[i];
                    errorMessArgsTypes[i+1] = pErrorMessArgsTypes[i];
                }
            }
        }

        if (RefCodeNames.WORKFLOW_RULE_ACTION.REJECT_ORDER.equals(pRule.getRuleAction())) {

            PreOrderData preOrderD = pBaton.getPreOrderData();
            String orderStatusToSet = RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW;
            String source = null;

            if (preOrderD != null) {
                source = preOrderD.getOrderSourceCd();
            }

            if (RefCodeNames.ORDER_SOURCE_CD.EMAIL.equals(source) ||
                    RefCodeNames.ORDER_SOURCE_CD.EXTERNAL.equals(source) ||
                    RefCodeNames.ORDER_SOURCE_CD.FAX.equals(source) ||
                    RefCodeNames.ORDER_SOURCE_CD.LAW.equals(source) ||
                    RefCodeNames.ORDER_SOURCE_CD.MAIL.equals(source) ||
                    RefCodeNames.ORDER_SOURCE_CD.OTHER.equals(source) ||
                    RefCodeNames.ORDER_SOURCE_CD.TELEPHONE.equals(source) ||
                    RefCodeNames.ORDER_SOURCE_CD.WEB.equals(source)) {
                orderStatusToSet = RefCodeNames.ORDER_STATUS_CD.REJECTED;
            }

            String rtype = pRule.getRuleTypeCd();

            if (rtype.equals(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_TOTAL)) {
                rr.setStatus(WorkflowRuleResult.ORDER_TOTAL_RULE_FAIL);
                Double amount = new Double(pRule.getRuleExpValue());

                NumberFormat numberFormatter = NumberFormat.getCurrencyInstance(Utility.parseLocaleCode(orderD.getLocaleCd()));

                String amountOut = numberFormatter.format(amount);
                String shortDesc = RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_TOTAL;

                pBaton.addError(pCon, OrderPipelineBaton.WORKFLOW_RULE_ALARM, shortDesc,
                        orderStatusToSet, 0, pRule.getWorkflowRuleId(),
                        "pipeline.message.orderTotal",
                        pRule.getRuleExp(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
                        amountOut, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.PRICE);

            } else if (rtype.equals(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU)) {
                rr.setStatus(WorkflowRuleResult.ORDER_SKU_RULE_FAIL);
                String sku = pRule.getRuleExpValue();
                String shortDesc = RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU;
                pBaton.addError(pCon, OrderPipelineBaton.WORKFLOW_RULE_ALARM, shortDesc,
                        orderStatusToSet, 0, pRule.getWorkflowRuleId(),
                        "pipeline.message.orderSku",
                        sku, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);

            } else if (rtype.equals(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_VELOCITY)) {
                rr.setStatus(WorkflowRuleResult.ORDER_VELOCITY_RULE_FAIL);
                String intervalS = pRule.getRuleExpValue();
                String shortDesc = RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_VELOCITY;
                pBaton.addError(pCon, OrderPipelineBaton.WORKFLOW_RULE_ALARM, shortDesc,
                        orderStatusToSet, 0, pRule.getWorkflowRuleId(),
                        "pipeline.message.orderVelocity",
                        intervalS, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);

            } else if (rtype.equals(RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_REMAINING_PER_CC)) {
                rr.setStatus(WorkflowRuleResult.BUDGET_RULE_FAIL);
                message = orderD.getCostCenterName();
                String shortDesc = RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_REMAINING_PER_CC;
                pBaton.addError(pCon, OrderPipelineBaton.WORKFLOW_RULE_ALARM, shortDesc,
                        orderStatusToSet, 0, pRule.getWorkflowRuleId(),
                        "pipeline.message.budgetRemainingPerCC",
                        orderD.getCostCenterName(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
            } else if (rtype.equals(RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_YTD)) {
                rr.setStatus(WorkflowRuleResult.FAIL);
                String shortDesc = RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_YTD;
                pBaton.addError(pCon, OrderPipelineBaton.WORKFLOW_RULE_ALARM, shortDesc,
                        orderStatusToSet, 0, pRule.getWorkflowRuleId(),
                        "pipeline.message.budgetYTDFail");
            } else if (rtype.equals(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_EXCLUDED_FROM_BUDGET)) {
                rr.setStatus(WorkflowRuleResult.FAIL);
                String shortDesc = RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_EXCLUDED_FROM_BUDGET;
                pBaton.addError(pCon, OrderPipelineBaton.WORKFLOW_RULE_ALARM, shortDesc,
                        orderStatusToSet, 0, pRule.getWorkflowRuleId(),
                        "pipeline.message.orderExcludedFromBudgetFail");
            } else if (rtype.equals(RefCodeNames.WORKFLOW_RULE_TYPE_CD.SHOPPING_CONTROLS)) {
                rr.setStatus(WorkflowRuleResult.FAIL);
                String shortDesc = RefCodeNames.WORKFLOW_RULE_TYPE_CD.SHOPPING_CONTROLS;
                pBaton.addError(pCon, OrderPipelineBaton.WORKFLOW_RULE_ALARM, shortDesc,
                        orderStatusToSet, 0, pRule.getWorkflowRuleId(),
                        "pipeline.message.orderShoppingControlsFail");
            } else {
                rr.setStatus(WorkflowRuleResult.FAIL);
                String shortDesc = rtype;
                pBaton.addError(pCon, OrderPipelineBaton.WORKFLOW_RULE_ALARM, shortDesc,
                        orderStatusToSet, 0, pRule.getWorkflowRuleId(),
                        "pipeline.message.commonFail",
                        errorMessArgs, errorMessArgsTypes);
            }

            if (pRule.getEmailGroupId() > 0) {
                message = " " + rtype + " " + pRule.getRuleExp() + " " + pRule.getRuleExpValue();
                addWorkflowQueue(pBaton, pRule, message);
            }
        }

        //Forward for approval
        else if (RefCodeNames.WORKFLOW_RULE_ACTION.FWD_FOR_APPROVAL.equals(pRule.getRuleAction())) {
            pBaton.addError(pCon, OrderPipelineBaton.WORKFLOW_RULE_WARNING, "Workflow Note",
                    RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL, 0, pRule.getWorkflowRuleId(),
                    "pipeline.message.fwdForApproval",
                    errorMessArgs, errorMessArgsTypes);

            rr.setStatus(WorkflowRuleResult.PENDING_APPROVAL);

            //addQueue
            message = pRule.getRuleTypeCd() + " " + pRule.getRuleExp() + " " + pRule.getRuleExpValue();
            addWorkflowQueue(pBaton, pRule, message);
        }
        
        //STJ-5014: Forward the order for Approval, If Order Excluded from Budget Workflow Rule is Set and 
        //'Exclude Order From Budget' is Selected at shopping page.
     
        //Forward for approval
        /*
        else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_EXCLUDED_FROM_BUDGET.equals(pRule.getRuleTypeCd())
                   && "true".equalsIgnoreCase(pRule.getRuleAction())) {
            pBaton.addError(pCon, OrderPipelineBaton.WORKFLOW_RULE_WARNING, "Workflow Note",
                    RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL, 0, pRule.getWorkflowRuleId(),
                    "pipeline.message.fwdForApproval",
                    errorMessArgs, errorMessArgsTypes);

            rr.setStatus(WorkflowRuleResult.PENDING_APPROVAL);

            //addQueue
            message = pRule.getRuleTypeCd() + " " + pRule.getRuleExp() + " " + pRule.getRuleExpValue();
            addWorkflowQueue(pBaton, pRule, message);
        }       */
        
        //Stop Order
        else if (RefCodeNames.WORKFLOW_RULE_ACTION.STOP_ORDER.equals(pRule.getRuleAction())) {
            rr.setStatus(WorkflowRuleResult.PENDING_ORDER_REVIEW);
            pBaton.addError(pCon, OrderPipelineBaton.WORKFLOW_RULE_WARNING, null,
                    RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW, 0, pRule.getWorkflowRuleId(),
                    "pipeline.message.stopOrder",
                    errorMessArgs, errorMessArgsTypes);

            if ( pRule.getEmailGroupId() > 0 ||
                 Utility.isSet(pRule.getNextActionCd())) {
                message =  " " + pRule.getRuleTypeCd() + " " + pRule.getRuleExp() + " " + pRule.getRuleExpValue();
                addWorkflowQueue(pBaton, pRule, message);
            }
        }

        //Send eMail
        else if (pRule.getRuleAction().equals( RefCodeNames.WORKFLOW_RULE_ACTION.SEND_EMAIL)) {
            //add queue
            addWorkflowQueue(pBaton, pRule, message);
        }

        // The remaining action is to approve the order.
        return rr.isGotoNextRule();
    }

  //--------------------------------------------------------------------------
  public static void addWorkflowQueue(OrderPipelineBaton pBaton,
                                      WorkflowRuleData pRule,
                                      String pErrorMessage) {
    WorkflowQueueData wqD = WorkflowQueueData.createValue();
    OrderData orderD = pBaton.getOrderData();
    wqD.setOrderId(orderD.getOrderId());
    wqD.setBusEntityId(orderD.getSiteId());
    wqD.setShortDesc(pRule.getRuleAction());
    String role = orderD.getWorkflowStatusCd();
    if (role == null) role = RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN;
    wqD.setWorkflowRoleCd(role);
    wqD.setWorkflowId(pRule.getWorkflowId());
    wqD.setWorkflowRuleId(pRule.getWorkflowRuleId());
    wqD.setMessage(pErrorMessage);
    wqD.setAddBy("System");

    pBaton.addWorkflowQueueData(wqD);
  }

  /*
      public void copy(Object b) {
        Class clazz = Thread.currentThread().getContextClassLoader().loadClass(pipeline.getClassname());
        Object scratch = clazz.newInstance();
      }
   */
  public OrderPipelineBaton[] getOrderBatons() {
    OrderPipelineBaton[] batons = new OrderPipelineBaton[orderPipelineBatons.size()];
    for (int ii = 0; ii < batons.length; ii++) {
      batons[ii] = (OrderPipelineBaton) orderPipelineBatons.get(ii);
    }
    return batons;

  }
  /*
  ISS UK User Workflow Rule Enhancement:
   change to the User Workflow to analyze who has already \u201Capproved\u201D the order and
   if the order was approved by the user who belongs to the next rule, ignore the rule.
  */
  private static boolean skipRuleAction(Connection pCon, OrderPipelineBaton pBaton, WorkflowRuleData pRule) {
    int approverGroupId= pRule.getApproverGroupId();
    HashMap wrApproverGroupUsersHM = pBaton.getApproverGroupUsersHM();
    HashSet wrCleanedApproversHS = pBaton.getWorkflowRulesCleanedApproversHS();
    String ruleMessage = " " + pRule.getRuleTypeCd() + " " + pRule.getRuleExp() + " " + pRule.getRuleExpValue();

    IdVector usersV = null;
    if (approverGroupId > 0){
      usersV = (IdVector) wrApproverGroupUsersHM.get(new Integer(approverGroupId));
    } else {
      // try to find user with APPROVER role.
      // If it has already approved order then need to skip rule action.
      try {
        IdVector wrCleanedApproversIdV = new IdVector();
        wrCleanedApproversIdV.addAll(wrCleanedApproversHS);
        DBCriteria dbc = new DBCriteria();
        if (wrCleanedApproversIdV.size() > 0){
          dbc.addOneOf(UserDataAccess.USER_ID, wrCleanedApproversIdV);
          dbc.addEqualTo(UserDataAccess.WORKFLOW_ROLE_CD, RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER);
          usersV = UserDataAccess.selectIdOnly(pCon, dbc);
        }
      } catch (Exception e){
        e.printStackTrace();
      }
    }
    if (usersV != null) {
      for (Iterator iter2 = usersV.iterator(); iter2.hasNext(); ) {
        Integer userId = (Integer) iter2.next();
        if (wrCleanedApproversHS.contains(userId)) {
          // add order property
          UserData user = null;
          try {
            user = UserDataAccess.select(pCon, userId.intValue());
          } catch (Exception e){
            e.printStackTrace();
          }
          String userName = (user != null)? user.getUserName():""+userId;
          OrderPropertyData opD = OrderPropertyData.createValue();
          OrderData oD = pBaton.getOrderData();
          opD.setOrderId(oD.getOrderId());
          opD.setWorkflowRuleId(pRule.getWorkflowRuleId());
          opD.setAddBy("System (user: SYNCH_ASYNCH)");
          opD.setModBy("System");
          opD.setAddDate(new Date());
          opD.setModDate(new Date());
          opD.setApproveDate(new Date());
          opD.setShortDesc("Workflow Note");
          String messKey = "pipeline.message.skipRuleAction";
          //STJ-5604
          String mess = PipelineUtil.translateMessage(messKey, pBaton.getOrderData().getLocaleCd(),
            ruleMessage, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
            userName, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING, null, null, null, null);

          opD.setValue(mess);
          opD.setOrderPropertyStatusCd("ACTIVE");
          opD.setOrderPropertyTypeCd("Notes");
          pBaton.addOrderPropertyData(opD);
          //
         return true;
       }
     }
   }
   return false;
  }

	public void splitOrder(Connection con, OrderPipelineBaton pBaton,
			IdVector splitItemIds, APIAccess factory) throws Exception {
		
		// split two order items
		OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
    	OrderItemDataVector itemDV1 = new OrderItemDataVector();
    	OrderItemDataVector itemDV2 = new OrderItemDataVector();    	
    	for ( int oi_idx = 0; oi_idx < orderItemDV.size(); oi_idx++ ) {
            OrderItemData oiD = (OrderItemData) orderItemDV.get(oi_idx);
            Integer itemId = new Integer(oiD.getItemId());
            if (!splitItemIds.contains(itemId)){
            	itemDV1.add(oiD);                	
            }else{
            	itemDV2.add(oiD);
            }
    	}
    	
    	
    	
    	// get order status
    	String orderStatus = pBaton.getOrderStatus();
    	IdVector replacedOrderIds = null;
    	UpdateOrderSummaryInfo poc = new UpdateOrderSummaryInfo();    	
    	
		OrderRequestData orderReq = pBaton.getOrderRequestData();
		if (orderReq == null){// first time split
			// cancel original order and order items
			OrderPipelineBaton currBaton = pBaton.copy();
			OrderData orderD = currBaton.getOrderData();
			currBaton.setOrderStatus(RefCodeNames.ORDER_STATUS_CD.CANCELLED);
			
			orderItemDV = currBaton.getOrderItemDataVector();	    	
			for (int ii = 0; ii < orderItemDV.size(); ii++) {
			    OrderItemData oiD = (OrderItemData) orderItemDV.get(ii);
		        oiD.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED);
			}
			PipelineUtil.saveNewOrder(currBaton, this, con, factory);
						
			// add first order 
	    	orderReq = OrderRequestData.createValue();
			replacedOrderIds = new IdVector();
			orderReq.setReplacedOrderIds(replacedOrderIds);
			pBaton.setOrderRequestData(orderReq);
        	replacedOrderIds.add(pBaton.getOrderData().getOrderId());
	    	pBaton.getOrderRequestData().setReplacedOrderIds(replacedOrderIds);
	    	orderD = pBaton.getOrderData();
	    	orderD.setOrderTypeCd(RefCodeNames.ORDER_TYPE_CD.SPLIT);
	    	orderD.setOrderId(0);
	    	orderD.setOrderNum(null);
		}
		
	    replacedOrderIds = orderReq.getReplacedOrderIds();
	    pBaton.setOrderPropertyDataVector(new OrderPropertyDataVector());// do not copy the properties from the cancelled order 
	    
	    pBaton.setOrderStatus(RefCodeNames.ORDER_STATUS_CD.ORDERED);
	    pBaton.setOrderItemDataVector(itemDV1);
    	pBaton = poc.process(pBaton, this, con, factory);
	    
	    // add second order
    	OrderPipelineBaton currBaton = pBaton.copy();
    	currBaton.setOrderStatus(orderStatus);
    	currBaton.setOrderItemDataVector(itemDV2);
    	currBaton = poc.process(currBaton, this, con, factory);
    	addOrderToPipeline(currBaton);
	    pBaton.setErrors(new HashMap());
	}
}


