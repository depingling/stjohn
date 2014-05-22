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
public class OrderBudgetPeriodChangedWorkflow  implements OrderPipeline {
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
            wfrv = pBaton.getWorkflowRuleDataVector(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_BUDGET_PERIOD_CHANGED);

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
			
            boolean evalNextRule = true;

            for (int ruleidx = 0; evalNextRule && ruleidx < wfrv.size(); ruleidx++) {

                WorkflowRuleData rd = (WorkflowRuleData)wfrv.get(ruleidx);

                try {
                	//get site data
                    SiteData siteD = BusEntityDAO.getSiteFromCache(siteId);
                    BigDecimal thisOrderSum = PipelineCalculationOperations.getTotalAmountWhithALLOperations(pCon,pBaton, siteD);
                    if(thisOrderSum.doubleValue()<0.000001) {//NO amount or Cost Ceneter doesn't affect the budget
                       return pBaton;
					}
                    
                    //if this does not go against the budge then there is technically nothing allocated and the result of this rule should
                    //be preformed regardless of the amount
                    if(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(orderD.getOrderBudgetTypeCd())){
                        //evalNextRule = OrderPipelineActor.performRuleAction(pCon, pBaton,rd,"Orders not affecting budget require approval", null, bypassWkflRuleActionCd);
                        evalNextRule = OrderPipelineActor.performRuleAction(pCon, pBaton,rd,"pipeline.message.orderNotAffectingBudget", null, bypassWkflRuleActionCd);
                    }
                    if(evalNextRule){
                    	// get current budget year and period
                    	// get order budget year and period
                    	// order budge year and period is larger than current budget year
                    	// order budget year and period is less than current budget year and period
                    	String budgetYearAndPeriod = null;
                    	if(pBaton.getOrderPropertyDataVector() != null){
                    		Iterator it = pBaton.getOrderPropertyDataVector().iterator();
                    		while (it.hasNext()) {
                    			OrderPropertyData pop = (OrderPropertyData) it.next();
                    			if (RefCodeNames.ORDER_PROPERTY_TYPE_CD.BUDGET_YEAR_PERIOD.equals(pop.getShortDesc())) {
                    				budgetYearAndPeriod = pop.getValue();
                    				break;
                    			}
                    		}
                    	}
                        if (Utility.isSet(budgetYearAndPeriod)) {
                        	int year = Integer.parseInt(budgetYearAndPeriod.substring(0, budgetYearAndPeriod.indexOf(':')));
                        	int period = Integer.parseInt(budgetYearAndPeriod.substring(budgetYearAndPeriod.indexOf(':')+1));
                        	
                        	Integer budgetYear = BudgetDAO.getCurrentBudgetYear(pCon, accountId);
                            if (budgetYear == null) {
                                throw new Exception("The year of budget couldn't be determined.AccountId:" + accountId);
                            }

                            BusEntityDAO bd = new BusEntityDAO();
                        	java.util.Date ordDate = orderD.getRevisedOrderDate();
                            if(ordDate == null){
                                ordDate = orderD.getOriginalOrderDate();
                            }
                        	int budPeriod = bd.getAccountBudgetPeriod(pCon, orderD.getAccountId(), orderD.getSiteId(), ordDate);

                        	if (year == budgetYear.intValue() && period == budPeriod)
                        		return pBaton;
                        	if (year < budgetYear.intValue() || (year == budgetYear.intValue() && period < budPeriod)){
                        		String messKey = "pipeline.message.workflowRuleWarning";

                                pBaton.addError(pCon, OrderPipelineBaton.WORKFLOW_RULE_WARNING,
                                        RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,0,rd.getWorkflowRuleId(),
                                        messKey);
                                pBaton.addError(pCon, OrderPipelineBaton.WORKFLOW_RULE_WARNING, null, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0, 
                                		"error.simpleGenericError", "Can not change order budget period earlier than current Fiscal Period for order num:"
                                        + orderD.getOrderNum(), "java.lang.String");
                        	}else{
                        		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        		FiscalCalenderView fiscCal = bd.getFiscalCalenderVForYear(mCon,accountId,budgetYear);
                                FiscalCalendarInfo fiscalInfo = new FiscalCalendarInfo(fiscCal);
                                FiscalCalendarInfo.BudgetPeriodInfo periodInfo = fiscalInfo.getBudgetPeriod(budPeriod);
                                    
                        		//Order Budget Period Changed from Period 10 (10/101/2011) to Period 11 (11/01/2011).
                        		String messKey = "pipeline.message.orderBudgetPeriodChanged";
                        		String[] params = new String[2];
    	                        params[0] = budPeriod+" (" + sdf.format(periodInfo.getStartDate())+")";
    	                        
    	                        if (year != budgetYear.intValue()){
    	                        	fiscCal = bd.getFiscalCalenderVForYear(mCon,accountId,year);
    	                        	fiscalInfo = new FiscalCalendarInfo(fiscCal);
    	                        }
    	                        periodInfo = fiscalInfo.getBudgetPeriod(period);
    	                        params[1] = period+" (" + sdf.format(periodInfo.getStartDate()) + ")";
    	                        
    	                        String[] paramsTypes = {RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING};
    	                        
    	                        
    	                        evalNextRule = OrderPipelineActor.performRuleAction(pCon, pBaton, rd, messKey, params, paramsTypes, null, bypassWkflRuleActionCd);
                        	}                            
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

}
