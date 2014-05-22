package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

import  org.apache.log4j.*;
import java.sql.Connection;

/**
 * Processes item rules
 */
public class ItemWorkflow  implements OrderPipeline {
    private static final Logger log = Logger.getLogger(ItemWorkflow.class);

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
            int siteId = orderD.getSiteId();

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

            WorkflowRuleDataVector wfrv =
                    pBaton.getWorkflowRuleDataVector(
                    RefCodeNames.WORKFLOW_RULE_TYPE_CD.ITEM);

            if (wfrv.size() <= 0) {
                return pBaton;
            }

            OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
            if(orderItemDV==null) {
                return pBaton;
            }

            // Iterate through the rules for the site.
            boolean evalNextRule = true;
            String compSign = "";
            WorkflowRuleData rdAction = null;
            IdVector itemIds = new IdVector();            
            boolean splitOrder = false;
            for (int ruleidx = 0; ruleidx < wfrv.size(); ruleidx++) {
                WorkflowRuleData rd = (WorkflowRuleData)wfrv.get(ruleidx);
                String ruleAction = rd.getRuleAction();
                if(rdAction==null && Utility.isSet(ruleAction)) {
                    rdAction = rd;
                }

                // Get the rule data.
                String ruleVal = rd.getRuleExpValue();
                String ruleExp = rd.getRuleExp();
                if(RefCodeNames.RULE_EXPRESSION.ITEM_ID.equals(ruleExp)) {
                    try {
                    	itemIds.add(Integer.parseInt(ruleVal));
                    }catch(Exception exc){
                        exc.printStackTrace();
                        log.info("Non numeric item id: "+ruleVal);
                        return pBaton;
                    }
                } else if(RefCodeNames.RULE_EXPRESSION.SPLIT_ORDER.equals(ruleExp)) {
                    try {
                    	splitOrder = Utility.isTrue(ruleVal) && pBaton.getOrderStatus().equals(RefCodeNames.ORDER_STATUS_CD.ORDERED);
                    }catch(Exception exc){}
                } 
            }
            String skuStr = null;
            IdVector splitItemIds = new IdVector();
            PropertyUtil pu = new PropertyUtil(pCon);
            String storeType = pu.fetchValueIgnoreMissing(0, orderD.getStoreId(), RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE);
            // Iterate through the items in the order.
            for ( int oi_idx = 0; oi_idx < orderItemDV.size(); oi_idx++ ) {
                OrderItemData oiD = (OrderItemData) orderItemDV.get(oi_idx);
                if (itemIds.contains(oiD.getItemId())){
                	if (splitOrder){
                    	splitItemIds.add(oiD.getItemId());
                    }
                    
                	if (skuStr == null)
                		skuStr = Utility.getActualSkuNumber(storeType, oiD);
                	else
                		skuStr += ", " + Utility.getActualSkuNumber(storeType, oiD);
                }
            }
            
            if(skuStr != null) {
                String messKey = "pipeline.message.ItemsHasSkus";
                Object[] args = new Object[1];
                String[] types = { RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING };                
                args[0] = skuStr;
                
                evalNextRule =
                        OrderPipelineActor.performRuleAction(pCon,
                          pBaton, rdAction, messKey, args, types, null,
                          bypassWkflRuleActionCd);
                if (!evalNextRule){
                	if (splitItemIds.size() > 0 && splitItemIds.size() < pBaton.getOrderItemDataVector().size()){
                    	pActor.splitOrder(pCon, pBaton, splitItemIds, pFactory);
                    }
                }
            }                       
             
            return pBaton;
        } catch(Exception exc) {
            throw new PipelineException(exc.getMessage());
        } finally{
        }
    }
}
