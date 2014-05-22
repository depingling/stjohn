package com.cleanwise.service.api.pipeline;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

import  org.apache.log4j.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;



/**
 * Processes category total rules
 */
public class CategoryTotalWorkflow  implements OrderPipeline {
    private static final Logger log = Logger.getLogger(CategoryTotalWorkflow.class);

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
    					RefCodeNames.WORKFLOW_RULE_TYPE_CD.CATEGORY_TOTAL);

    		if (wfrv.size() <= 0) {
    			return pBaton;
    		}

    		OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
    		if(orderItemDV==null) {
    			return pBaton;
    		}

    		// Iterate through the rules for the site.
    		boolean evalNextRule = true;
    		double ruleAmount = -1;
    		int ruleCategoryId = 0;
    		String compSign = "";
    		WorkflowRuleData rdAction = null;
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
    			} else if(RefCodeNames.RULE_EXPRESSION.CATEGORY_ID.equals(ruleExp)) {
    				try {
    					ruleCategoryId = Integer.parseInt(ruleVal);
    				}catch(Exception exc){
    					exc.printStackTrace();
    					log.info("Non numeric category id: "+ruleVal);
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
    		IdVector itemIds = new IdVector();
    		Map<Integer, BigDecimal> itemTotalByItemId = new HashMap<Integer, BigDecimal>();
    		// Iterate through the items in the order.
    		for ( int oi_idx = 0; oi_idx < orderItemDV.size(); oi_idx++ ) {
    			OrderItemData oiD = (OrderItemData) orderItemDV.get(oi_idx);
    			Integer itemId = new Integer(oiD.getItemId());
    			itemIds.add(itemId);
    			itemTotalByItemId.put(itemId, new BigDecimal(oiD.getCustContractPrice().doubleValue()*oiD.getTotalQuantityOrdered()).setScale(2,BigDecimal.ROUND_HALF_UP));
    		}

    		if(!itemIds.isEmpty()) {
    			Site siteEjb = pFactory.getSiteAPI();
    			int catalogId = siteEjb.getShoppigCatalogIdForSite(siteId);            	

    			String sql =
    				"SELECT i.item_id, ic.short_desc as categ" +
    				" FROM clw_item i "+
    				" JOIN clw_item_assoc ia "+
    				"   ON  i.item_id = ia.item1_id "+
    				"   AND ia.item_assoc_cd = '"+RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY+"'"+
    				"   AND ia.catalog_id = ? "+
    				" JOIN clw_item ic " +
    				"   ON ia.item2_id = ic.item_id " +
    				"   AND ic.item_id = ? ";
    			String inSql = Utility.toSqlInClause("i.item_id",itemIds);

    			sql += " WHERE " + inSql;

    			int paramsCount = distIdList.size()>0 ? 3 : 2;
    			
    			if (distIdList.size()>0){ // items need to be filtered by distributors
    				CatalogInformation catEjb = pFactory.getCatalogInformationAPI();
    				CatalogData catData = catEjb.getSiteCatalog(siteId);
    				sql += " AND EXISTS (SELECT * FROM clw_catalog_structure " +
    				"	WHERE catalog_id = " + catData.getCatalogId() +
    				"	AND item_id = i.item_id " +
    				"	AND bus_entity_id = ?)";
    			}else{
    				distIdList.add(new Integer(0));
    			}

    			Object[] params = new Object[paramsCount];
    			String[] paramsTypes = new String[paramsCount];
    			paramsTypes[0] = RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING;
    			paramsTypes[1] = RefCodeNames.PIPELINE_MESSAGE_ARG_CD.PRICE;
    			if (paramsCount == 3)
    				paramsTypes[2] = RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING;
    			String priceStr =
    				(new BigDecimal(ruleAmount)).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
    			
    			PreparedStatement pstmt = pCon.prepareStatement(sql);
    			pstmt.setInt(1,catalogId);
    			pstmt.setInt(2,ruleCategoryId);
    			for (Object o : distIdList){
    				Integer distId = (Integer)o;
    				if (distId.intValue() > 0){
    					pstmt.setInt(3,distId.intValue());
    				}	                

    				ResultSet rs = pstmt.executeQuery();
    				String category = null;

    				BigDecimal categoryTotal = new BigDecimal(0);
    				itemIds = new IdVector();
    				while (rs.next()) {
    					int itemId = rs.getInt("item_id");
    					category = rs.getString("categ");
    					categoryTotal = categoryTotal.add(itemTotalByItemId.get(itemId));
    					itemIds.add(itemId);
    				}

    				if (itemIds.size()>0){
    					String messKey = null;
    					if (compSign.equals("<")) {
    						if (categoryTotal.doubleValue() - ruleAmount<-0.0001) {
    							messKey = "pipeline.message.categoryLessThan";
    							evalNextRule = processRuleAction(pBaton, pCon, bypassWkflRuleActionCd, 
    									rdAction, messKey, params, paramsTypes, priceStr, distId, category);
    						}
    					} else if (compSign.equals("<=")) {
    						if (categoryTotal.doubleValue() < ruleAmount ||
    								Math.abs(categoryTotal.doubleValue()-ruleAmount)<0.0001) {
    							messKey = "pipeline.message.categoryHasNotExceeded";
    							evalNextRule = processRuleAction(pBaton, pCon, bypassWkflRuleActionCd, 
    									rdAction, messKey, params, paramsTypes, priceStr, distId, category);
    						}
    					} else if (compSign.equals(">")) {
    						if ((categoryTotal.doubleValue() - ruleAmount)>0000.1) {
    							messKey = "pipeline.message.categoryHasExceeded";
    							evalNextRule = processRuleAction(pBaton, pCon, bypassWkflRuleActionCd, 
    									rdAction, messKey, params, paramsTypes, priceStr, distId, category);
    						}
    					} else if (compSign.equals(">=")) {
    						if (categoryTotal.doubleValue() > ruleAmount ||
    								Math.abs(categoryTotal.doubleValue()-ruleAmount)<0.0001) {                    	
    							messKey = "pipeline.message.categoryHasExceededOrEqual";
    							evalNextRule = processRuleAction(pBaton, pCon, bypassWkflRuleActionCd, 
    									rdAction, messKey, params, paramsTypes, priceStr, distId, category);
    						}
    					} else if (compSign.equals("==")) {
    						if (Math.abs(categoryTotal.doubleValue()-ruleAmount)<0.0001) {
    							evalNextRule = OrderPipelineActor.performRuleAction(pCon, pBaton, rdAction, null, null, bypassWkflRuleActionCd);
    						}
    					}

    					if (!evalNextRule && splitOrder && itemIds.size() < pBaton.getOrderItemDataVector().size()){
    						pActor.splitOrder(pCon, pBaton, itemIds, pFactory);
    					}	                    
    				}
    			}
    		}
			return pBaton;
		} catch(Exception exc) {
			throw new PipelineException(exc.getMessage());
		} finally{
		}
	}
    
    private boolean processRuleAction(OrderPipelineBaton pBaton,
			Connection pCon, String bypassWkflRuleActionCd,
			WorkflowRuleData rdAction, String messKey,
			Object[] params, String[] paramsTypes, String priceStr,
			Integer distId, String category) throws SQLException {
		boolean evalNextRule;
		params[0] = category;		
		params[1] = priceStr;
		if (distId.intValue() > 0){
			messKey += "ForDist";
			PreparedStatement pstmt = pCon.prepareStatement("select short_desc from clw_bus_entity where bus_entity_id = ?");
			pstmt.setInt(1, distId);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			params[2] = rs.getString(1);
		}
		evalNextRule =
			OrderPipelineActor.performRuleAction(pCon, pBaton, rdAction, messKey, params, paramsTypes,  null, bypassWkflRuleActionCd);
		return evalNextRule;
	}

}
