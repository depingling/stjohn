/**
 * 
 */
package com.cleanwise.service.api.pipeline;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.WorkflowRuleData;
import com.cleanwise.service.api.value.WorkflowRuleDataVector;

/**
 * Processes rules based on item price (not line total)
 * @author ssharma
 *
 */
public class ItemPriceWorkflow implements OrderPipeline {
	
	private static final String className = "ItemPriceWorkflow";
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

            pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
            OrderData orderD = pBaton.getOrderData();
            OrderItemDataVector orderItems = pBaton.getOrderItemDataVector();
            ArrayList itemPrices = new ArrayList();
            for(int i=0; i<orderItems.size(); i++){
            	OrderItemData oiD = (OrderItemData)orderItems.get(i);
            	
            	BigDecimal price = oiD.getCustContractPrice();
            	itemPrices.add(price);
            	
            }
            
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

            WorkflowRuleDataVector wfrv;
            wfrv = pBaton.getWorkflowRuleDataVector(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ITEM_PRICE);

            if (wfrv.size() <= 0) {
                return pBaton;
            }
            
            BigDecimal ruleExpValue;
            String ruleExp;

            boolean evalNextRule = true;
            for (int ruleidx = 0; evalNextRule && ruleidx < wfrv.size(); ruleidx++) {
                WorkflowRuleData rd = (WorkflowRuleData)wfrv.get(ruleidx);
                if (!rd.getRuleAction().equals(RefCodeNames.WORKFLOW_RULE_ACTION.DISPLAY_MESSAGE)){
                	if (RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER.equals(wfrcd)) {
                        // No need to check the rules since this user
                        // has the authority to overide them.
                        continue;
                    }
                }else{
                	if (!RefCodeNames.PIPELINE_CD.CHECKOUT_CAPTURE.equals(pBaton.getPipelineTypeCd())){
                		continue;
                	}
                }

                // Get the rule type.
                ruleExp = rd.getRuleExp();
                ruleExpValue = new BigDecimal(rd.getRuleExpValue());
                ruleExpValue = ruleExpValue.setScale(2, BigDecimal.ROUND_HALF_UP);
                //String errorMess;
                String messKey = "";
                Object[] params = new BigDecimal[1];
                String[] paramsTypes = new String[1];
                paramsTypes[0] = RefCodeNames.PIPELINE_MESSAGE_ARG_CD.PRICE;

                Iterator it = itemPrices.iterator();
                if (ruleExp.equals("<")) {
                	
                	while(it.hasNext()){
                		BigDecimal itmPrice = (BigDecimal)it.next();
                		if (itmPrice.doubleValue() < ruleExpValue.doubleValue()) {
                        	if (RefCodeNames.PIPELINE_CD.CHECKOUT_CAPTURE.equals(pBaton.getPipelineTypeCd())){
                            	pBaton.addResultMessage(rd.getWarningMessage());
                            }else{
    	                        messKey = "pipeline.message.itemPriceHasNotExceeded";
    	                        params[0] = ruleExpValue;
    	                        evalNextRule =
    	                          OrderPipelineActor.performRuleAction(pCon, pBaton, rd, messKey, params, paramsTypes,  null, bypassWkflRuleActionCd);
                            }
                        	break;
                        }
                	}
                    
                } else if (ruleExp.equals("<=")) {
                	
                	while(it.hasNext()){
                		BigDecimal itmPrice = (BigDecimal)it.next();
                		if (itmPrice.doubleValue() <= ruleExpValue.doubleValue()) {
                			if (RefCodeNames.PIPELINE_CD.CHECKOUT_CAPTURE.equals(pBaton.getPipelineTypeCd())){
                				pBaton.addResultMessage(rd.getWarningMessage());
                			}else{
                				messKey = "pipeline.message.itemPriceHasNotExceeded";
                				params[0] = ruleExpValue;
                				evalNextRule = OrderPipelineActor.performRuleAction(pCon, pBaton, rd, messKey, params, paramsTypes, null, bypassWkflRuleActionCd);
                			}
                			break;
                		}
                	}
                	
                } else if (ruleExp.equals(">")) {
                	
                	while(it.hasNext()){
                		BigDecimal itmPrice = (BigDecimal)it.next();
                		if (itmPrice.doubleValue() > ruleExpValue.doubleValue()) {
                			if (RefCodeNames.PIPELINE_CD.CHECKOUT_CAPTURE.equals(pBaton.getPipelineTypeCd())){
                				pBaton.addResultMessage(rd.getWarningMessage());
                			}else{
                				messKey = "pipeline.message.itemPriceHasExceeded";
                				params[0] = ruleExpValue;
                				evalNextRule = OrderPipelineActor.performRuleAction(pCon, pBaton, rd, messKey, params, paramsTypes, null, bypassWkflRuleActionCd);
                			}
                			break;
                		}
                	}
                	
                } else if (ruleExp.equals(">=")) {
                	
                	while(it.hasNext()){
                		BigDecimal itmPrice = (BigDecimal)it.next();
                		if (itmPrice.doubleValue() >= ruleExpValue.doubleValue()) {
                			if (RefCodeNames.PIPELINE_CD.CHECKOUT_CAPTURE.equals(pBaton.getPipelineTypeCd())){
                				pBaton.addResultMessage(rd.getWarningMessage());
                			}else{
                				messKey = "pipeline.message.itemPriceHasExceeded";
                				params[0] = ruleExpValue;
                				evalNextRule = OrderPipelineActor.performRuleAction(pCon, pBaton, rd, messKey, params, paramsTypes, null, bypassWkflRuleActionCd);
                			}
                			break;
                		}
                	}
                	
                } else if (ruleExp.equals("==")) {
                	
                	while(it.hasNext()){
                		BigDecimal itmPrice = (BigDecimal)it.next();
                		if (itmPrice.doubleValue() == ruleExpValue.doubleValue()) {
                			if (RefCodeNames.PIPELINE_CD.CHECKOUT_CAPTURE.equals(pBaton.getPipelineTypeCd())){
                				pBaton.addResultMessage(rd.getWarningMessage());
                			}else{
                				evalNextRule = OrderPipelineActor.performRuleAction(pCon, pBaton, rd, null, null, bypassWkflRuleActionCd);
                			}
                			break;
                		}
                	}
                }
            }
    	
            
            return pBaton;
        }
        catch(Exception exc) {
            exc.printStackTrace();
            throw new PipelineException(exc.getMessage());
        }
    }
    
}
