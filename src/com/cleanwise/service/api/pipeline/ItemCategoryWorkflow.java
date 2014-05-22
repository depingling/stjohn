/*
 * ItemCategoryWorkflow.java
 * Picks items of the rule category which are have prce < or <= or > or >= of rule amount
 * Created 01/14/2008
 * Author: YKupershmidt
 */

package com.cleanwise.service.api.pipeline;
import java.sql.Connection;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

import java.math.BigDecimal;
import java.sql.*;


import java.util.Iterator;

import  org.apache.log4j.*;

/**
 * Processes special sku workflow rules
 * @author  YKupershmidt (copied from WorkflowBean)
 */
public class ItemCategoryWorkflow  implements OrderPipeline {
    private static final Logger log = Logger.getLogger(ItemCategoryWorkflow.class);

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
                    RefCodeNames.WORKFLOW_RULE_TYPE_CD.ITEM_CATEGORY);

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
            // Iterate through the items in the order.
            for ( int oi_idx = 0; oi_idx < orderItemDV.size(); oi_idx++ ) {
                OrderItemData oiD = (OrderItemData) orderItemDV.get(oi_idx);
                BigDecimal oiPrice = oiD.getCustContractPrice();
                if(oiPrice!=null) {
                    double itemPrice = oiPrice.doubleValue();
                    if(RefCodeNames.RULE_EXPRESSION.GREATER.equals(compSign) &&
                            itemPrice > ruleAmount ||
                            RefCodeNames.RULE_EXPRESSION.GREATER_OR_EQUAL.equals(compSign) &&
                            itemPrice >= ruleAmount ||
                            RefCodeNames.RULE_EXPRESSION.LESS.equals(compSign) &&
                            itemPrice < ruleAmount ||
                            RefCodeNames.RULE_EXPRESSION.LESS_OR_EQUAL.equals(compSign) &&
                            itemPrice <= ruleAmount) {
                        itemIds.add(new Integer(oiD.getItemId()));
                    }
                }
            }
            if(!itemIds.isEmpty()) {
            	String sql =
                        " SELECT ca.catalog_id " +
                        " FROM clw_catalog_assoc ca " +
                        "  JOIN clw_catalog c "+
                        "    ON ca.catalog_id = c.catalog_id "+
                        "    AND c.catalog_type_cd = '"+RefCodeNames.CATALOG_TYPE_CD.ACCOUNT+"' "+
                        " WHERE ca.catalog_assoc_cd = '"+RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT+"' "+
                        "   AND ca.bus_entity_id = ?";

                PreparedStatement pstmt = pCon.prepareStatement(sql);
                pstmt.setInt(1,orderD.getAccountId());
                int catalogId = 0;
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    catalogId = rs.getInt(1);
                }
                rs.close();
                pstmt.close();

                if(catalogId<=0) {
                    String errorMess = "Did not find account catalog for account: "+orderD.getAccountId();
                    (new Exception(errorMess)).printStackTrace();
                    return pBaton;
                }

                
                sql =   "SELECT i.item_id, i.short_desc, ic.short_desc as categ" +
                        " FROM clw_item i "+
                        " JOIN clw_item_assoc ia "+
                        "   ON  i.item_id = ia.item1_id "+
                        "   AND ia.item_assoc_cd = '"+RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY+"'"+
                        "   AND ia.catalog_id = ? "+
                        " JOIN clw_item ic " +
                        "   ON ia.item2_id = ic.item_id ";
                if (ruleCategoryId > 0)
                    sql += "   AND ic.item_id = ? ";
                sql += " WHERE " + Utility.toSqlInClause("i.item_id",itemIds);
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
                
                String priceStr =
                    (new BigDecimal(ruleAmount)).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
                
                String messKey = null;
                if(RefCodeNames.RULE_EXPRESSION.GREATER.equals(compSign)){
                	messKey = "pipeline.message.itemCategoryMore";
                }else if(RefCodeNames.RULE_EXPRESSION.LESS.equals(compSign)){
                	messKey = "pipeline.message.itemCategoryLess";
                }else if(RefCodeNames.RULE_EXPRESSION.GREATER_OR_EQUAL.equals(compSign)){
                	messKey = "pipeline.message.itemCategoryGreaterOrEqual";
                }else if(RefCodeNames.RULE_EXPRESSION.LESS_OR_EQUAL.equals(compSign)){
                	messKey = "pipeline.message.itemCategoryLessOrEqual";
                }else {
                	messKey = "pipeline.message.itemCategoryEqual";
                }
                
                pstmt = pCon.prepareStatement(sql);
                int paramNum = 1;
                pstmt.setInt(paramNum++,catalogId);
                if (ruleCategoryId > 0)
                	pstmt.setInt(paramNum++,ruleCategoryId);                
                
                for (Object o : distIdList){
                	int distId = ((Integer)o).intValue();
                	if (distId > 0 )
                    	pstmt.setInt(paramNum,distId);
                    
                    rs = pstmt.executeQuery();
                    IdVector splitItemIds = new IdVector();
                    
                    while (rs.next()) {
                        int itemId = rs.getInt("item_id");
                        String itemName = rs.getString("short_desc");
                        String category = rs.getString("categ");
                        Object[] args = new Object[3];
                        String[] types = { RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
                        RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
                        RefCodeNames.PIPELINE_MESSAGE_ARG_CD.PRICE };
                        args[0] = category;
                        args[1] = itemName;
                        args[2] = priceStr;
                        for ( int oi_idx = 0; oi_idx < orderItemDV.size(); oi_idx++ ) {
                            OrderItemData oiD = (OrderItemData) orderItemDV.get(oi_idx);
                            if(oiD.getItemId()==itemId) {                                
                                evalNextRule =
                                        OrderPipelineActor.performRuleAction(pCon,
                                          pBaton, rdAction, messKey, args, types, null,
                                          bypassWkflRuleActionCd);
                                if (!evalNextRule && splitOrder){
                                	splitItemIds.add(itemId);
                                }
                            }
                        }
                    }
                    if (splitItemIds.size() > 0 && splitItemIds.size() < pBaton.getOrderItemDataVector().size()){
                    	pActor.splitOrder(pCon, pBaton, splitItemIds, pFactory);
                    }
                    rs.close();                                             
                }
                pstmt.close();
            }
            return pBaton;
        } catch(Exception exc) {
            throw new PipelineException(exc.getMessage());
        } finally{
        }
    }

}
