/*
 * OrderExcludedFromBudgetWorkflow.java
 *
 * Created on Dec 16, 2011, 9:20 AM
 */

package com.cleanwise.service.api.pipeline;

import java.sql.Connection;
import java.util.Date;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.PreOrderData;
import com.cleanwise.service.api.value.WorkflowRuleData;
import com.cleanwise.service.api.value.WorkflowRuleDataVector;

/**
 *
 * @author Srinivas Ch
 */
public class OrderExcludedFromBudgetWorkflow implements OrderPipeline{
	  private static final Logger log = Logger.getLogger(OrderExcludedFromBudgetWorkflow.class);
    /** Process this pipeline.
     *
     * @param OrderRequestData the order request object to act upon
     * @param Connection a active database connection
     * @param APIAccess
     *
     */
    public OrderPipelineBaton process(OrderPipelineBaton pBaton,
            OrderPipelineActor pActor,
            Connection pCon,
            APIAccess pFactory)
            throws PipelineException {
        try{
            pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
            OrderData orderD = pBaton.getOrderData();
            if (RefCodeNames.ORDER_BUDGET_TYPE_CD.REBILL.equals(orderD.getOrderBudgetTypeCd())) {
            	return pBaton;
            }
            int siteId = orderD.getSiteId();
            
            if(siteId<=0) {
                return pBaton;
            }
            if(pBaton.hasErrors()) {
                return pBaton;
            }
            String bypassWkflRuleActionCd = pBaton.getBypassWkflRuleActionCd();
            
            // Check the Workflow role.  Workflow's apply only to
            // customers which are not APPROVERs.
            String wfrcd = pBaton.getUserWorkflowRoleCd();
            if (RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER.equals(wfrcd)) {
                // No need to check the rules since this user
                // has the authority to overide them.
                return pBaton;
            }
            WorkflowRuleDataVector wfrv =
                    pBaton.getWorkflowRuleDataVector(
                    RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_EXCLUDED_FROM_BUDGET);
            
            if (wfrv.size() <= 0) {
                return pBaton;
            }
            
            OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
            if(orderItemDV==null) {
                return pBaton;
            }
            
            //STJ-5014: If Exclude Order From Budget is set at Shopping
            PreOrderData preOrderD = pBaton.getPreOrderData();
            Date holdDate = null;
            if(preOrderD!=null) {
            	holdDate = preOrderD.getHoldUntilDate();
            }
            log.info("ORDER_EXCLUDED_FROM_BUDGET rule ====> orderD.getOrderBudgetTypeCd() ="+orderD.getOrderBudgetTypeCd());     
           
            //Hold Date (Pending Date) should be empty to forward the order for 
            //Pending Approval when 'Exclude Order From Budget' is Set at Shopping Portal and 
            //'Order Excluded from Budget' is Set at Admin portal. 
            if(holdDate==null && Utility.isSet(orderD.getOrderBudgetTypeCd()) 
          		  && RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equalsIgnoreCase(orderD.getOrderBudgetTypeCd().trim())) {
            	 for (int ruleidx = 0; ruleidx < wfrv.size(); ruleidx++) {
                     WorkflowRuleData wfRule = (WorkflowRuleData)wfrv.get(ruleidx);
                     //if(Utility.isTrue(wfRule.getRuleAction())) {
                    //	 String errorMsg = "pipeline.message.fwdForApproval";
                    //	 OrderPipelineActor.performRuleAction(pCon, pBaton,wfRule, errorMsg,null, bypassWkflRuleActionCd);
                    // }
                    OrderPipelineActor.performRuleAction(pCon, pBaton,wfRule,null, null, bypassWkflRuleActionCd);
            	 }
            }
            
            return pBaton;
        } catch(Exception exc) {
            throw new PipelineException(exc.getMessage());
        }
    }
}
