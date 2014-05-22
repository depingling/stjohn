/*
 * Created on August 25, 2003
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.value.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Processes rules based type of order.
 *
 * @author Evgeny Vlasov
 */
public class RushOrderWorkflow implements OrderPipeline {
    private static final String RUL_VALUE_ALL_ITEMS = "All items";
    private static final String RUL_VALUE_INVENTORY_ITEMS = "Inventory Items";
    private static final String className = "RushOrderWorkflow";

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

            if(RefCodeNames.ORDER_SOURCE_CD.INVENTORY.equals(orderD.getOrderSourceCd())) {
                //Return if inventory order
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
            wfrv = pBaton.getWorkflowRuleDataVector(RefCodeNames.WORKFLOW_RULE_TYPE_CD.RUSH_ORDER);

            if (wfrv.size() <= 0) {
                return pBaton;
            }

            // Iterate through the rules for the site.
            String ruleExpValue;
            boolean evalNextRule = true;

            for (int ruleidx = 0; evalNextRule && ruleidx < wfrv.size(); ruleidx++) {

                WorkflowRuleData rd = (WorkflowRuleData) wfrv.get(ruleidx);
                String ruleType = rd.getRuleTypeCd();

                if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.RUSH_ORDER.equals(ruleType)) {
                    //Get Order type                     
                    String orderSourceCd = orderD.getOrderSourceCd();

                    SiteData sd = getSite(siteId);

                    // Get the rule type.
                    ruleExpValue = rd.getRuleExpValue();

                    if(ruleExpValue != null){

                        if ((RUL_VALUE_ALL_ITEMS.equals(ruleExpValue)) && (orderSourceCd != null) && (!RefCodeNames.ORDER_SOURCE_CD.INVENTORY.equals(orderSourceCd))) {
                        	if (RefCodeNames.PIPELINE_CD.CHECKOUT_CAPTURE.equals(pBaton.getPipelineTypeCd())){
                            	pBaton.addResultMessage(rd.getWarningMessage());
                            }else{
	                        	String errorMessKey = "pipeline.message.orderIsNotAnInventoryOrder"; //Order is not an inventory order
	                            OrderPipelineActor.performRuleAction(pCon, pBaton, rd, errorMessKey, null, bypassWkflRuleActionCd);
                            }
                        }


                        if(RUL_VALUE_INVENTORY_ITEMS.equals(ruleExpValue)){
                            OrderItemDataVector orderItems = pBaton.getOrderItemDataVector();
                            Iterator itItems = orderItems.iterator();

                            while(itItems.hasNext()){
                                OrderItemData orderItemD = (OrderItemData) itItems.next();
                                if ((this).isAnInventoryOrderItem(sd.getSiteInventory(), orderItemD.getItemId())){
                                	if (RefCodeNames.PIPELINE_CD.CHECKOUT_CAPTURE.equals(pBaton.getPipelineTypeCd())){
                                    	pBaton.addResultMessage(rd.getWarningMessage());
                                    }else{
	                                    String errorMessKey = "pipeline.message.oneOfItemsIsAnInventoryItem"; //One of items is an inventory item;
	                                    OrderPipelineActor.performRuleAction(pCon, pBaton, rd, errorMessKey, null, bypassWkflRuleActionCd);
                                    }
                                    break;
                                }
                            }
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

    public boolean isAnInventoryOrderItem(ArrayList siteInv, int pItemid) {
        for (int i = 0; siteInv != null && i < siteInv.size(); i++) {
            SiteInventoryInfoView siiv = (SiteInventoryInfoView) siteInv.get(i);
            if (pItemid == siiv.getItemId()) {
                return true;
            }
        }
        return false;
    }


    private SiteData getSite(int siteId) throws Exception {
        SiteData site = BusEntityDAO.getSiteFromCache(siteId);
        if (site == null) {
            throw new DataNotFoundException("Site not found.SiteId: " + siteId);
        }
        return site;
    }

}
