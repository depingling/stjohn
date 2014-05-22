/*
 * CancelReplaceItemDuplication.java
 *
 * Created on August 25, 2003
 */

package com.cleanwise.service.api.pipeline;
import java.sql.Connection;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.ItemSubstitutionDefDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;

import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.sql.*;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.text.NumberFormat;


import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Collection;
import java.util.Locale;
/**
 * Processes Budget Remaining Per CC site workflow rules
 * The YTD of this class is a mis-nomer.  This will base it's result off the
 * total budget of all the cost centers either by period or YTD based off the account
 * budget accrual type cd.
 * @author  YKupershmidt (copied from WorkflowBean)
 */
public class BudgetYTDWorkflow  implements OrderPipeline {
  Connection mCon;

    /**
     * Process this pipeline.
     * @param pBaton
     * @param pActor
     * @param pCon
     * @param pFactory
     * @return
     * @throws PipelineException
     */
    public OrderPipelineBaton process(OrderPipelineBaton pBaton,
                                      OrderPipelineActor pActor,
                                      Connection pCon,
                                      APIAccess pFactory)
            throws PipelineException {
        try{
            mCon = pCon;
            pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
            OrderData orderD = pBaton.getOrderData();
            // bypass this workflow for rebill order
            if (RefCodeNames.ORDER_BUDGET_TYPE_CD.REBILL.equals(orderD.getOrderBudgetTypeCd())) {
            	return pBaton;
            }
            int siteId = orderD.getSiteId();
            int accountId = orderD.getAccountId();

            if(siteId<=0) {
                return pBaton;
            }
            if(pBaton.hasErrors()) {
                return pBaton;
            }
            String bypassWkflRuleActionCd = pBaton.getBypassWkflRuleActionCd();

            // Check the workflow role.  Workflow's apply only to
            // customers which are not APPROVERs.
            String wfrcd = pBaton.getUserWorkflowRoleCd();
            if (RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER.equals(wfrcd)) {
                // No need to check the rules since this user
                // has the authority to overide them.
                return pBaton;
            }

            WorkflowRuleDataVector wfrv;
            wfrv = pBaton.getWorkflowRuleDataVector(RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_YTD);

            if (wfrv.size() <= 0) {
                return pBaton;
            }

            // Iterate through the rules for the site.
     		CostCenterDataVector ccDV = pBaton.getCostCenterDV(pCon, pFactory);
			boolean noBudgetFl = true;
			for(Iterator iter = ccDV.iterator(); iter.hasNext(); ) {
				CostCenterData ccD = (CostCenterData) iter.next();
				boolean fl = Utility.isTrue(ccD.getNoBudget(),false);
				if(!fl) {
					noBudgetFl = false;
					break;
				}
			}
			if(noBudgetFl) {
				return pBaton; //All cost centers should not apply to budget
			}

            BigDecimal ruleExpValue;
            String ruleExp;

            boolean evalNextRule = true;

            for (int ruleidx = 0; evalNextRule && ruleidx < wfrv.size(); ruleidx++) {

                WorkflowRuleData rd = (WorkflowRuleData)wfrv.get(ruleidx);
                //wkflrr.setRule(rd);

                // Get the rule type.
                ruleExp = rd.getRuleExp();
                ruleExpValue = new BigDecimal(rd.getRuleExpValue());

                try {
                	//get site data
                    SiteData siteD = BusEntityDAO.getSiteFromCache(siteId);
                    BigDecimal thisOrderSum = PipelineCalculationOperations.getTotalAmountWhithALLOperations(pCon,pBaton, siteD);
                    if(thisOrderSum.doubleValue()<0.000001) {//NO amount or Cost Ceneter doesn't affect the budget
                       return pBaton;
					}

                    Integer budgetYear = BudgetDAO.getCurrentBudgetYear(pCon, accountId);
                    if (budgetYear == null) {
                        throw new Exception("The year of budget couldn't be determined.AccountId:" + accountId);
                    }

                    BudgetDataVector siteBudget = BudgetDAO.getBudgetDataVector(pCon,
                            siteId,
                            0,
                            budgetYear,
                            Utility.getAsList(RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET));

                    BudgetDataVector acctBudget = BudgetDAO.getBudgetDataVector(pCon,
                            accountId,
                            0,
                            budgetYear,
                            Utility.getAsList(RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET));

                    //if this does not go against the budge then there is technically nothing allocated and the result of this rule should
                    //be preformed regardless of the amount
                    if(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(orderD.getOrderBudgetTypeCd())){
                        //evalNextRule = OrderPipelineActor.performRuleAction(pCon, pBaton,rd,"Orders not affecting budget require approval", null, bypassWkflRuleActionCd);
                        evalNextRule = OrderPipelineActor.performRuleAction(pCon, pBaton,rd,"pipeline.message.orderNotAffectingBudget", null, bypassWkflRuleActionCd);
                    }

                    if(evalNextRule){
                        evalNextRule =
						  processBudget(siteBudget, siteId, thisOrderSum,ruleExpValue, ruleExp,rd, pBaton,bypassWkflRuleActionCd, ccDV, pCon);
                        if(evalNextRule){
                            evalNextRule =
							   processBudget(acctBudget, siteId, thisOrderSum,ruleExpValue, ruleExp,rd,pBaton,bypassWkflRuleActionCd, ccDV, pCon);
                        }
                    }

                } catch (Exception exc) {
                    exc.printStackTrace();
                    String messKey = "pipeline.message.workflowRuleWarning";

                    pBaton.addError(pCon, OrderPipelineBaton.WORKFLOW_RULE_WARNING,
                            RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,0,rd.getWorkflowRuleId(),
                            messKey);
                }
            }

            return pBaton;

        } catch(Exception exc) {
            exc.printStackTrace();
            throw new PipelineException(exc.getMessage());
        }
    }


    /**
     *Processes a Budget Data object
     */
    private boolean processBudget(BudgetDataVector budgets,
                                  int siteId,
                                  BigDecimal thisOrderSum,
                                  BigDecimal ruleExpValue,
                                  String ruleExp,
                                  WorkflowRuleData rd,
                                  OrderPipelineBaton pBaton,
                                  String bypassWkflRuleActionCd,
								  CostCenterDataVector costCenterDV,
                                  Connection pCon)
            throws Exception{

        if(budgets == null || budgets.isEmpty()){
            return true;
        }

        //set the order status code to somthing that the budget will ignore
        //this case really only happens when an order is being re-processed
        //or it is going through a 2 level workflow.  This seems a bit of a
        //hack, but to account for this using the budget util also seems
        //overly complex.
        //note tried this using the pBaton.getOrderData() with strange results
        //there is an inconsistency between the db order and the baton order
        OrderData dbOrder = null;
        String origOrderStatus = null;
        if(pBaton.getOrderData().getOrderId() > 0){
	        dbOrder = OrderDataAccess.select(pCon, pBaton.getOrderData().getOrderId());
	        origOrderStatus=dbOrder.getOrderStatusCd();
	        dbOrder.setOrderStatusCd("ignore");
	        OrderDataAccess.update(pCon, dbOrder);
        }

		BigDecimal noBudgetAllocated = new BigDecimal(0);
		BigDecimal noBudgetSpent = new BigDecimal(0);
		int accountId = pBaton.getOrderData().getAccountId();
		for(Iterator iter = costCenterDV.iterator(); iter.hasNext(); ) {
			CostCenterData ccD = (CostCenterData) iter.next();
			boolean fl = Utility.isTrue(ccD.getNoBudget(),false);
			if(fl) {
				BudgetUtil bu = new BudgetUtil(pCon);
				BudgetSpendView bsVw = bu.getBudgetSpentForSite(accountId, siteId, ccD,null);
				noBudgetAllocated = noBudgetAllocated.add(bsVw.getAmountAllocated());
				noBudgetSpent = noBudgetSpent.add(bsVw.getAmountSpent());
			}
		}
        BudgetUtil bu = new BudgetUtil(pCon);
        BudgetSpendView moneySpent = bu.getBudgetSpentForSite(siteId, 0);

        //reset the status from the bogus status code we set it to
        if(dbOrder != null){
	        dbOrder.setOrderStatusCd(origOrderStatus);
	        OrderDataAccess.update(pCon, dbOrder);
        }

        BigDecimal siteBudgetWithThisOrder;

        siteBudgetWithThisOrder = moneySpent.getAmountAllocated().subtract(noBudgetAllocated);
        siteBudgetWithThisOrder = siteBudgetWithThisOrder.subtract(moneySpent.getAmountSpent().subtract(noBudgetSpent));
        siteBudgetWithThisOrder = siteBudgetWithThisOrder.subtract(thisOrderSum);

        // Check the remaining budget for the site.
        boolean evalNextRule = true;
        boolean ruleFlag = false;
        if (ruleExp.equals("<")) {
            if(siteBudgetWithThisOrder.doubleValue() < ruleExpValue.doubleValue()) {
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
        	evalNextRule =  OrderPipelineActor.performRuleAction(pCon, pBaton,rd, null, null, bypassWkflRuleActionCd);
        }

        return evalNextRule;
    }

}
