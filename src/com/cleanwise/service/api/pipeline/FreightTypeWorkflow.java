/*
 * Created on August 25, 2003
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.OrderFreightDataAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;

import java.sql.Connection;

/**
 * Processes rules based Freight Handler of order.
 *
 * @author Evgeny Vlasov
 */
public class FreightTypeWorkflow implements OrderPipeline {

    private static final String className = "FreightTypeWorkflow";

    /**
     * Process this pipeline.
     *
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
        try {

            pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
            OrderData orderD = pBaton.getOrderData();
            int siteId = orderD.getSiteId();

            if (siteId <= 0) {
                return pBaton;
            }

            if (pBaton.hasErrors()) {
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
            wfrv = pBaton.getWorkflowRuleDataVector(RefCodeNames.WORKFLOW_RULE_TYPE_CD.FREIGHT_HANDLER);

            if (wfrv.size() <= 0) {
                return pBaton;
            }

            // Iterate through the rules for the site.
            for (int ruleidx = 0; ruleidx < wfrv.size(); ruleidx++) {

                WorkflowRuleData rd = (WorkflowRuleData) wfrv.get(ruleidx);
                String ruleType = rd.getRuleTypeCd();

                if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.FREIGHT_HANDLER.equals(ruleType)) {
                    if (processRule(rd.getRuleExpValue(), pBaton, pCon)) {
                    	if (RefCodeNames.PIPELINE_CD.CHECKOUT_CAPTURE.equals(pBaton.getPipelineTypeCd())){
                        	pBaton.addResultMessage(rd.getWarningMessage());
                        }else{
	                        String errorMessKey = "pipeline.message.requestedFreight"; 
	                        String[] argValA = new String[1];
	                        argValA[0] = rd.getRuleExpValue();
	                        String[] argTypeA = {"STRING"}; 
	                        OrderPipelineActor.performRuleAction(pCon, pBaton, rd, errorMessKey, argValA, argTypeA,null, bypassWkflRuleActionCd);
                        }
                    }
                }
            }

            return pBaton;
        }
        catch (Exception exc) {
            throw new PipelineException(exc.getMessage());
        }
    }

    private boolean processRule(String fhName, OrderPipelineBaton pBaton, Connection pCon) throws Exception {
    	if (RefCodeNames.PIPELINE_CD.CHECKOUT_CAPTURE.equals(pBaton.getPipelineTypeCd())){
        	return fhName.equals(pBaton.getFreightType());
        }else{
	        if (fhName != null && fhName.trim().length() > 0) {
	            DBCriteria cr = new DBCriteria();
	            cr.addEqualTo(OrderFreightDataAccess.SHORT_DESC, fhName);
	            cr.addEqualTo(OrderFreightDataAccess.ORDER_ID, pBaton
	                    .getOrderData().getOrderId());
	            OrderFreightDataVector freightHandlers = OrderFreightDataAccess
	                    .select(pCon, cr);
	            if (freightHandlers.size() > 0) {
	                return true;
	            }
	        }
        }
        return false;
    }

}
