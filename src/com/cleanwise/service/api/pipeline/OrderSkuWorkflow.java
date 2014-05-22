/*
 * CancelReplaceItemDuplication.java
 *
 * Created on August 25, 2003
 */

package com.cleanwise.service.api.pipeline;
import java.sql.Connection;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

/**
 * Processes special sku workflow rules
 * @author  YKupershmidt (copied from WorkflowBean)
 */
public class OrderSkuWorkflow  implements OrderPipeline {
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
            String orderStatusCd = orderD.getOrderStatusCd();
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
                    RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU);

            if (wfrv.size() <= 0) {
                return pBaton;
            }

            OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
            if(orderItemDV==null) {
                return pBaton;
            }

            PropertyUtil pu = new PropertyUtil(pCon);
            String storeType = pu.fetchValueIgnoreMissing(0, orderD.getStoreId(), RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE);

            // Iterate through the rules for the site.
            boolean evalNextRule = true;
            for (int ruleidx = 0; ruleidx < wfrv.size(); ruleidx++) {

                WorkflowRuleData rd = (WorkflowRuleData)wfrv.get(ruleidx);

                // Get the rule type.
                String ruleSku = rd.getRuleExpValue();
                if(ruleSku==null) continue; //no value for the rule
                int ruleSkuInt = 0;
                try {
                    ruleSkuInt = Integer.parseInt(ruleSku);
                }catch(Exception exc){}

                // Iterate through the items in the order.
                for ( int oi_idx = 0; oi_idx < orderItemDV.size(); oi_idx++ ) {
                    OrderItemData oid = (OrderItemData) orderItemDV.get(oi_idx);
                    String oisku1 = oid.getCustItemSkuNum();
                    int oisku2 = oid.getItemSkuNum();

                    if ( ruleSku.equals(oisku1) ||  ruleSkuInt == oisku2 ) {
                        //String errorMess = "The order has sku "+Utility.getActualSkuNumber(storeType, oid);;
                        String messKey = "pipeline.message.orderHasSku";
                        Object[] args = { Utility.getActualSkuNumber(storeType, oid) };
                        String[] types = { RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING };
                        evalNextRule =  OrderPipelineActor.performRuleAction(pCon, pBaton,rd, messKey, args, types, null, bypassWkflRuleActionCd);
                        break;
                    }
                }
            }
     /*
     if (!evalNextRule) {
       pBaton.stopWorkflow();
     }
      */
            //Return
            return pBaton;
        } catch(Exception exc) {
            throw new PipelineException(exc.getMessage());
        }
    }

}
