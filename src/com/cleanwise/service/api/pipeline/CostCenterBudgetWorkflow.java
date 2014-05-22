/*
 * CancelReplaceItemDuplication.java
 *
 * Created on August 25, 2003
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.CostCenterDataAccess;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.dao.BudgetDAO;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.util.BudgetUtil;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.PipelineCalculationOperations;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;


/**
 * Processes Budget Remaining Per CC site workflow rules
 *
 * @author YKupershmidt (copied from WorkflowBean)
 */
public class CostCenterBudgetWorkflow implements OrderPipeline {

    private static final Logger log = Logger.getLogger(CostCenterBudgetWorkflow.class);

    /**
     * Process this pipeline.
     *
     * @param pBaton     the order request object to act upon
     * @param pCon       a active database connection
     */
    public OrderPipelineBaton process(OrderPipelineBaton pBaton,
                                      OrderPipelineActor pActor,
                                      Connection pCon,
                                      APIAccess pFactory)
            throws PipelineException {
        try {
            log.info("process()=> BEGIN");

            pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
            OrderData orderD = pBaton.getOrderData();
            // bypass this workflow for rebill order
            if (RefCodeNames.ORDER_BUDGET_TYPE_CD.REBILL.equals(orderD.getOrderBudgetTypeCd())) {
            	return pBaton;
            }

            int siteId = orderD.getSiteId();

            if (siteId <= 0) {
                log.info("process()=> siteId <= 0");
                return pBaton;
            }

            if (pBaton.hasErrors()) {
                log.info("process()=> pBaton.hasErrors()");
                return pBaton;
            }

            BigDecimal orderTotal = orderD.getOriginalAmount();
            if (orderTotal == null || orderTotal.doubleValue() <= 0) {
                log.info("process()=> orderTotal == null || orderTotal.doubleValue() <= 0)");
                return pBaton;
            }

            String bypassWkflRuleActionCd = pBaton.getBypassWkflRuleActionCd();
            //WorkflowRuleResult wkflrr = new WorkflowRuleResult();

            // Check the workflow role.  Workflow's apply only to
            // customers which are not APPROVERs.
            String wfrcd = pBaton.getUserWorkflowRoleCd();
            if (RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER.equals(wfrcd)) {
                // No need to check the rules since this user
                // has the authority to overide them.
                return pBaton;
            }

            WorkflowRuleDataVector wfrv;
            wfrv = pBaton.getWorkflowRuleDataVector(RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_REMAINING_PER_CC);

            if (wfrv.size() <= 0) {
                return pBaton;
            }

            // Iterate through the rules for the site.
            BudgetSpendView moneySpent;
            BigDecimal siteBudgetWithThisOrder;
            BigDecimal ruleExpValue;
            String ruleExp;

            boolean evalNextRule = true;

            for (int ruleidx = 0; evalNextRule && ruleidx < wfrv.size(); ruleidx++) {

                WorkflowRuleData rd = (WorkflowRuleData) wfrv.get(ruleidx);

                // Get the rule type.
                ruleExp = rd.getRuleExp();
                ruleExpValue = new BigDecimal(rd.getRuleExpValue());

                //if this does not go against the budge then there is technically nothing allocated and the result of this rule should
                //be preformed regardless of the amount
                if (RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(orderD.getOrderBudgetTypeCd())) {
                    //evalNextRule = OrderPipelineActor.performRuleAction(pCon, pBaton, rd, "Orders not affecting budget require approval", null, bypassWkflRuleActionCd);
                    evalNextRule = OrderPipelineActor.performRuleAction(pCon, pBaton, rd, "pipeline.message.orderNotAffectingBudget", null, bypassWkflRuleActionCd);
                }

                try {

                    // Get the cost centers and amounts spent in this order.
                    OrderItemDataVector oiDV = pBaton.getOrderItemDataVector();
                    IdVector orderCostCenters = new IdVector();
                    for(int oi=0; oi<oiDV.size(); oi++){
                    	OrderItemData oid = (OrderItemData)oiDV.get(oi);
                    	if(!orderCostCenters.contains(new Integer(oid.getCostCenterId()))){
                    		orderCostCenters.add(new Integer(oid.getCostCenterId()));
                    	}
                    }
                    HashMap costCenterAmountHM = PipelineCalculationOperations.getTotalAmountForCostCenter(pCon, orderD,pBaton);
                    /*
                    for (int ii = 0; ii < oiDV.size(); ii++) {
                        OrderItemData oiD = (OrderItemData) oiDV.get(ii);
                        int costCenterId = oiD.getCostCenterId();

                        if (costCenterId <= 0) {
                            continue;
                        }

                        BigDecimal itemPrice = oiD.getCustContractPrice();
                        if (itemPrice == null) {
                            itemPrice = new BigDecimal(0);
                        }

                        BigDecimal price = itemPrice.multiply(new BigDecimal(oiD.getTotalQuantityOrdered()));

                        Integer costCenterIdI = new Integer(costCenterId);

                        BigDecimal amount = (BigDecimal) costCenterAmountHM.get(costCenterIdI);
                        if (amount == null) {
                            amount = price;
                        } else {
                            amount = amount.add(price);
                        }
                        costCenterAmountHM.put(costCenterIdI, amount);
                    }
                    */

                    BudgetUtil budUtil = new BudgetUtil(pCon);
                    Set entries = costCenterAmountHM.entrySet();

                    Iterator iter = entries.iterator();
                    while (iter.hasNext()) {
                        //get cost center ID and amount
                        Map.Entry entry = (Map.Entry) iter.next();

                        Integer thisCostCenterIdI = (Integer) entry.getKey();
                        int thisCostCenterId = thisCostCenterIdI.intValue();
						if(thisCostCenterId == 0){
						      continue;
						}

                        BigDecimal thisCostCenterSum = (BigDecimal) entry.getValue();
                        thisCostCenterSum = thisCostCenterSum.setScale(2, BigDecimal.ROUND_HALF_UP);

						//if there were no items in the order that belong to
                        //this cost center...skip
                        if(!orderCostCenters.contains(thisCostCenterIdI)){
                        	log.info("process() => Skip for cost center "+thisCostCenterId);
                        	continue;
                        }
                        
                        moneySpent = budUtil.getBudgetSpentForSite(siteId, thisCostCenterId);
                        
                        CostCenterData ccD = CostCenterDataAccess.select(pCon, thisCostCenterId);

                        if(Utility.isTrue(ccD.getNoBudget(), true)){
                        	continue;
                        }

                        int accountId = BusEntityDAO.getAccountForSite(pCon, siteId);
                        Integer budgetYear = BudgetDAO.getCurrentBudgetYear(pCon, accountId);
                        if (budgetYear == null) {
                            throw new Exception("The year of budget couldn't be determined.AccountId:" + accountId);
                        }
                        
                        BudgetDataVector siteBudgets = BudgetDAO.getBudgetDataForSite(pCon, accountId, siteId, thisCostCenterId, budgetYear);

                        if (siteBudgets.isEmpty()) {
                            log.info("process() => applySiteWorkflow Error, no site budget available. Site id: " + siteId);
                            continue;
                        } else if (siteBudgets.size() > 1) {
                            new Exception("More than one budget found for cost center id: " + thisCostCenterId + " site id: " + siteId);
                        }                       

                        BudgetData siteBudget = (BudgetData)siteBudgets.get(0);
                        
                        boolean amountSet = false;
                        BudgetView bView = BudgetDAO.getBudget(pCon, siteBudget);
                        BudgetDetailDataVector bDV = bView.getDetails();
                        
                        for(int ii=0; ii<bDV.size(); ii++){
                        	BudgetDetailData bDetail = (BudgetDetailData)bDV.get(ii);
                        	
                        	if(bDetail.getAmount() != null){
                        		amountSet = true;
                        	}
                        }

                        if(!amountSet){
                        	log.info("skip process() No Amounts set => site budget: " + siteBudget);
                        	 continue;
                        }
                        
                        log.info("process() => site budget: " + siteBudget);

                        //set the order status code to somthing that the budget will ignore
                        //this case really only happens when an order is being re-processed
                        //or it is going through a 2 level workflow.  This seems a bit of a
                        //hack, but to account for this using the budget util also seems
                        //overly complex.
                        //note tried this using the pBaton.getOrderData() with strange results
                        //there is an inconsistency between the db order and the baton order
                        try{
	                        OrderData dbOrder = OrderDataAccess.select(pCon, pBaton.getOrderData().getOrderId());
	                        String origOrderStatus=dbOrder.getOrderStatusCd();
	                        dbOrder.setOrderStatusCd("ignore");
	                        OrderDataAccess.update(pCon, dbOrder);
	
	                        //reset the status from the bogus status code we set it to
	                        dbOrder.setOrderStatusCd(origOrderStatus);
	                        OrderDataAccess.update(pCon, dbOrder);
                        }catch(DataNotFoundException e){
                        	//this happens during inventory processing.
                        	//if the order is not there though this should not be an error
                            log.info("process() => ERROR: "+  e.getMessage());
                        }


                        log.info("process() =>  site money spent: " + moneySpent.getAmountSpent() +
                                " for cost center id: " + thisCostCenterId);

                        siteBudgetWithThisOrder = moneySpent.getAmountAllocated();
                        siteBudgetWithThisOrder = siteBudgetWithThisOrder.subtract(moneySpent.getAmountSpent());
                        siteBudgetWithThisOrder = siteBudgetWithThisOrder.subtract(thisCostCenterSum);
                        
                        boolean ruleFlag = false;
                        // Check the remaining budget for the site.
                        if (ruleExp.equals("<")) {
                            log.info("process() => check remaining budget site order: " + siteBudgetWithThisOrder +
                                            " rule value: " + ruleExpValue);
                            if (siteBudgetWithThisOrder.doubleValue() < ruleExpValue.doubleValue()) {
                            	ruleFlag = true;
                            }

                        } else if (ruleExp.equals("<=")) {
                            if (siteBudgetWithThisOrder.doubleValue() <= ruleExpValue.doubleValue()) {
                            	ruleFlag = true;
                            }

                        } else if (ruleExp.equals("==")) {
                            if (siteBudgetWithThisOrder.doubleValue() == ruleExpValue.doubleValue()) {
                            	ruleFlag = true;
                            }
                        }
                        if (ruleFlag){
                        	evalNextRule = OrderPipelineActor.performRuleAction(pCon, pBaton, rd, null, ccD, bypassWkflRuleActionCd);
                        	break;
                        }
                    }

                } catch (Exception exc) {

                    log.error("process() =>  Exception: " + exc.getMessage());
                    exc.printStackTrace();

                    pBaton.addError(
                            pCon,
                            OrderPipelineBaton.WORKFLOW_RULE_WARNING,
                            RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,
                            0,
                            rd.getWorkflowRuleId(),
                            "pipeline.message.workflowRuleWarning"
                    );
                    //wkflrr.setStatus(WorkflowRuleResult.FAIL);
                    //wkflrr =  wkflrr;
                }
                //if ( wkflrr != null ) {
                //   evalNextRule = wkflrr.isGotoNextRule();
                //}
            }
            if (!evalNextRule) {
                //pBaton.stopWorkflow();
                // wkflrr.setStatus(WorkflowRuleResult.OK);
            }

            log.info("process()=> END");

            return pBaton;
        }
        catch (Exception exc) {
            throw new PipelineException(exc.getMessage());
        }
    }
}
