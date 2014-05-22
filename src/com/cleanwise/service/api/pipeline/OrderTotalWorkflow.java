/*
 * Created on August 25, 2003
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.WorkflowRuleData;
import com.cleanwise.service.api.value.WorkflowRuleDataVector;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Processes rules based on order total.
 * @author  YKupershmidt (copied from WorkflowBean)
 */
public class OrderTotalWorkflow  implements OrderPipeline
{
	private static final Logger log = Logger.getLogger(ItemCategoryWorkflow.class);
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
            throws PipelineException
    {
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

            //Calculate the order total
            BigDecimal orderTotal = orderD.getOriginalAmount();
                    //ReCalculateOrdersPipeline.getTotalAmountWhithALLOperations(pCon, orderD);

                        if(orderTotal==null || orderTotal.doubleValue()<0) {
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
            wfrv = pBaton.getWorkflowRuleDataVector(RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_TOTAL);

            if (wfrv.size() <= 0) {
                return pBaton;
            }

            // Iterate through the rules for the site.
            WorkflowRuleData rdAction = null;
            double ruleAmount = -1;
            String compSign = "";
            boolean splitOrder = false;
            IdVector distIdList = new IdVector();
            
            for (int ruleidx = 0; ruleidx < wfrv.size(); ruleidx++) {
                WorkflowRuleData rd = (WorkflowRuleData)wfrv.get(ruleidx);
                String ruleAction = rd.getRuleAction();
                if(rdAction==null && Utility.isSet(ruleAction)) {
                    rdAction = rd;
                }

                // Get the rule data.
                String ruleVal = rd.getRuleExpValue();
                String ruleExp = rd.getRuleExp();
                if(RefCodeNames.RULE_EXPRESSION.GREATER.equals(ruleExp) ||
                        RefCodeNames.RULE_EXPRESSION.GREATER_OR_EQUAL.equals(ruleExp)||
                        RefCodeNames.RULE_EXPRESSION.LESS.equals(ruleExp)||
                        RefCodeNames.RULE_EXPRESSION.EQUALS.equals(ruleExp) ||
                        RefCodeNames.RULE_EXPRESSION.LESS_OR_EQUAL.equals(ruleExp)) {
                    try {
                        ruleAmount = Double.parseDouble(ruleVal);
                        compSign = ruleExp;
                        continue;
                    }catch(Exception exc){
                        exc.printStackTrace();
                        log.info("Non numeric workflow rule quantity: "+ruleVal);
                        return pBaton;
                    }
                } else if(RefCodeNames.RULE_EXPRESSION.SPLIT_ORDER.equals(ruleExp)) {
                    try {
                        splitOrder = Utility.isTrue(ruleVal) && pBaton.getOrderStatus().equals(RefCodeNames.ORDER_STATUS_CD.ORDERED);
                    }catch(Exception exc){}
                } else if(RefCodeNames.RULE_EXPRESSION.DISTR_ID.equals(ruleExp)) {
                    try {
                        distIdList.add(Integer.parseInt(ruleVal));
                    }catch(Exception exc){
                        exc.printStackTrace();
                        log.info("Non numeric distributor id: "+ruleVal);
                        return pBaton;
                    }
                }
            }
            
            Map<Integer, BigDecimal> orderTotalByDistId = new HashMap<Integer, BigDecimal>();
            Map<Integer, IdVector> itemIdsByDistId = new HashMap<Integer, IdVector>();
            if (distIdList.size() > 0){
            	OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
            	
            	IdVector itemIds = new IdVector();
            	Map<Integer, BigDecimal> itemTotalByItemId = new HashMap<Integer, BigDecimal>();
                // Iterate through the items in the order.
                for ( int oi_idx = 0; oi_idx < orderItemDV.size(); oi_idx++ ) {
                    OrderItemData oiD = (OrderItemData) orderItemDV.get(oi_idx);
                    Integer itemId = new Integer(oiD.getItemId());
                    itemIds.add(itemId);
                    itemTotalByItemId.put(itemId, new BigDecimal(oiD.getCustContractPrice().doubleValue()*oiD.getTotalQuantityOrdered()).setScale(2,BigDecimal.ROUND_HALF_UP));
                }
                
                String inSql = Utility.toSqlInClause("item_id",itemIds);
                CatalogInformation catEjb = pFactory.getCatalogInformationAPI();
                CatalogData catData = catEjb.getSiteCatalog(siteId);
                String sql = "SELECT item_id FROM clw_catalog_structure " +
        		"	WHERE catalog_id = " + catData.getCatalogId() +
        		"	AND bus_entity_id = ? " +
        		"	AND " + inSql;
                PreparedStatement pstmt = pCon.prepareStatement(sql);
                
            	for (Object o : distIdList){
            		int distId = ((Integer)o).intValue();
                    pstmt.setInt(1,distId);
                    ResultSet rs = pstmt.executeQuery();
                    BigDecimal total = new BigDecimal(0);
                    IdVector itemIdsByDist = new IdVector();
                    while (rs.next()) {
                        int itemId = rs.getInt(1);
                        total = total.add(itemTotalByItemId.get(itemId));
                        itemIdsByDist.add(itemId);
                    }
                    if (total.doubleValue() - 0 > 0.0001){
                    	orderTotalByDistId.put(distId, total);
                    	itemIdsByDistId.put(distId, itemIdsByDist);
                    }        		
            	}
            	pstmt.close();
            }else{
            	orderTotalByDistId.put(new Integer(0), orderTotal);
            }

            if (orderTotalByDistId.size()==0)
            	return pBaton;
            
            String messKey = "";
            int paramsCount = distIdList.size()>0 ? 2 : 1;
            Object[] params = new Object[paramsCount];
            String[] paramsTypes = new String[paramsCount];
            paramsTypes[0] = RefCodeNames.PIPELINE_MESSAGE_ARG_CD.PRICE;
            if (paramsCount == 2)
            	paramsTypes[1] = RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING;
            String priceStr =
                (new BigDecimal(ruleAmount)).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
                        
            for (Integer distId : orderTotalByDistId.keySet()) {
            	boolean evalNextRule = true;                
                
            	BigDecimal total = orderTotalByDistId.get(distId);
            	if (compSign.equals("<")) {
                	if (total.doubleValue() - ruleAmount<-0.0001) {
                		messKey = "pipeline.message.orderLessThan";
                		evalNextRule = processRuleAction(pBaton, pCon, bypassWkflRuleActionCd, 
                				rdAction, messKey, params, paramsTypes, priceStr, distId);
                	}
                } else if (compSign.equals("<=")) {
                	if (total.doubleValue() < ruleAmount ||
                			Math.abs(total.doubleValue()-ruleAmount)<0.0001) {
                		messKey = "pipeline.message.orderHasNotExceeded";
                		evalNextRule = processRuleAction(pBaton, pCon, bypassWkflRuleActionCd, 
                				rdAction, messKey, params, paramsTypes, priceStr, distId);
                	}
                } else if (compSign.equals(">")) {
                	if ((total.doubleValue() - ruleAmount)>0000.1) {
                		messKey = "pipeline.message.orderHasExceeded";
                		evalNextRule = processRuleAction(pBaton, pCon, bypassWkflRuleActionCd, 
                				rdAction, messKey, params, paramsTypes, priceStr, distId);
                	}
                } else if (compSign.equals(">=")) {
                	if (total.doubleValue() > ruleAmount ||
                			Math.abs(total.doubleValue()-ruleAmount)<0.0001) {                    	
                		messKey = "pipeline.message.orderHasExceededOrEqual";
                		evalNextRule = processRuleAction(pBaton, pCon, bypassWkflRuleActionCd, 
                				rdAction, messKey, params, paramsTypes, priceStr, distId);
                	}
                } else if (compSign.equals("==")) {
                	if (Math.abs(total.doubleValue()-ruleAmount)<0.0001) {
                		evalNextRule = OrderPipelineActor.performRuleAction(pCon, pBaton, rdAction, null, null, bypassWkflRuleActionCd);
                	}
                }
            	// order split based on total of each distributor. So it could have multiple split if more than one distributor total match the rule.
            	if (!evalNextRule && splitOrder && distId.intValue() > 0 && itemIdsByDistId.get(distId).size() < pBaton.getOrderItemDataVector().size()){
            		pActor.splitOrder(pCon, pBaton, itemIdsByDistId.get(distId), pFactory);
            	}
            }          

            return pBaton;
        }
        catch(Exception exc) {
            exc.printStackTrace();
            throw new PipelineException(exc.getMessage());
        }
    }
	private boolean processRuleAction(OrderPipelineBaton pBaton,
			Connection pCon, String bypassWkflRuleActionCd,
			WorkflowRuleData rdAction, String messKey,
			Object[] params, String[] paramsTypes, String priceStr,
			Integer distId) throws SQLException {
		boolean evalNextRule;
		params[0] = priceStr;
		if (distId.intValue() > 0){
			messKey += "ForDist";
			PreparedStatement pstmt = pCon.prepareStatement("select short_desc from clw_bus_entity where bus_entity_id = ?");
			pstmt.setInt(1, distId);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			params[1] = rs.getString(1);
		}
		evalNextRule =
			OrderPipelineActor.performRuleAction(pCon, pBaton, rdAction, messKey, params, paramsTypes,  null, bypassWkflRuleActionCd);
		return evalNextRule;
	}

}
