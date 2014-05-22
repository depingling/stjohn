/*
 * ReSetCostCenterInfo.java
 *
 * Created on October 17, 2005, 4:03 PM
 *
 * Copyright October 17, 2005 Cleanwise, Inc.
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
/**
 *
 * @author bstevens
 */
public class ReSetCostCenterInfo   implements OrderPipeline{
    private Connection mCon;
    private static HashMap cache = new HashMap();

    /** Process this pipeline.
     *
     * @param OrderRequestData the order request object to act upon
     * @param Connection a active database connection
     * @param APIAccess
     *
     */
    public OrderPipelineBaton process(OrderPipelineBaton pBaton,OrderPipelineActor pActor,Connection pCon,APIAccess pFactory)
    throws PipelineException {
        try{
            mCon = pCon;
            checkAndPopulateCache();
            OrderData order = pBaton.getOrderData();
            if(processOrderForAccount(order.getAccountId())){
                OrderItemDataVector oItms = pBaton.getOrderItemDataVector();
                reSetCostCenterInfo(order, oItms);
            }
            return pBaton;
        }catch(Exception exc) {
            exc.printStackTrace();
            throw new PipelineException(exc.getMessage());
        }

    }


    private void checkAndPopulateCache() throws SQLException{
        String key = "enabledAccounts";
        CacheEntry ce = (CacheEntry) cache.get(key);
        if(ce == null || !ce.isValid()){
            ArrayList accountList = new ArrayList();
            Statement stmt = mCon.createStatement();
            String q = "select distinct a.bus_entity_id from "
            + "  clw_cost_center cc, clw_catalog_assoc ca, clw_bus_entity a, clw_cost_center_assoc cca"
            + "  where "
            + "  ca.catalog_id = cca.catalog_id"
            + "  and cc.cost_center_status_cd = 'ACTIVE'"
            + "  and cca.cost_center_id = cc.cost_center_id"
            + "  and a.bus_entity_id = ca.bus_entity_id "
            + "  and a.bus_entity_type_cd = '"+RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT+"'";
            ResultSet rs = stmt.executeQuery(q);
            while (rs.next()){
                Integer acctId = new Integer(rs.getInt(1));
                accountList.add(acctId);
            }
            CacheEntry ent= new CacheEntry(accountList);
            cache.put(key,ent);
        }
    }

    private boolean processOrderForAccount(int accountId){
        Integer a = new Integer(accountId);
        String key = "enabledAccounts";
        CacheEntry ce = (CacheEntry) cache.get(key);
        return ((ArrayList)ce.getCachedValue()).contains(a);
    }

    /**
     *Does the work of actually resetting the cost center information for this order
     */
    private void reSetCostCenterInfo(OrderData order,OrderItemDataVector oItms) throws SQLException{
        int siteId = order.getSiteId();
        String siteRefNum = null;
		String facilityType = null;
				
		PropertyUtil pru = new PropertyUtil(mCon);
		try {
			siteRefNum = pru.fetchValue(0, siteId, RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
		} catch (Exception e){}
		try {
			facilityType = pru.fetchValue(0, siteId, RefCodeNames.PROPERTY_TYPE_CD.FACILITY_TYPE);
		} catch (Exception e){}

		Iterator it = oItms.iterator();
        while(it.hasNext()){
            OrderItemData oItm = (OrderItemData) it.next();
            if(oItm.getCostCenterId() > 0){
                continue;
            }

            int orderid = oItm.getOrderId();
            int itemid = oItm.getItemId();
            int newcostcenterid = 0;

            ProductDAO pdao = new ProductDAO();
            int cc_override = pdao.setCostCenterOverride(mCon,order.getAccountId(),itemid,siteRefNum,facilityType);

            if(cc_override>0){
            	newcostcenterid = cc_override;
            	Statement st = mCon.createStatement();
            	if ( newcostcenterid > 0 && newcostcenterid != oItm.getCostCenterId()) {
        			oItm.setCostCenterId(newcostcenterid);
        			OrderItemDataAccess.update(mCon,oItm);
        			String u = " delete from clw_site_ledger where "
        				+ " order_id = " + orderid
                        + " and cost_center_id = " +
                        newcostcenterid  ;
        			st.executeUpdate(u);

        		}
            	st.close();
            }else{

            	// Get the cost center.
/*
            	String q2 = "  select cost_center_id from "
            		+ "     clw_catalog_structure where "
            		+ "       item_id = " + itemid
                    + "       and catalog_id in ( "
                    + " SELECT cat.catalog_id FROM clw_catalog_assoc ca, "
                    + " clw_catalog cat WHERE bus_entity_id =  " + siteId
                    + " AND cat.catalog_id = ca.catalog_id "
                    + " AND catalog_status_cd = '"
                    + RefCodeNames.CATALOG_STATUS_CD.ACTIVE+"' "
                    + " AND catalog_type_cd = '"
                    + RefCodeNames.CATALOG_TYPE_CD.SHOPPING+"' "
                    + "                          )";
*/
          String q2 = "  select cost_center_id from "
                    + "     clw_catalog_structure where "
                    + "       item_id = " + itemid
                    + "       and catalog_id in ( "
                    + " select ca.CATALOG_ID  "
                    + "  from clw_bus_entity_assoc ba, clw_catalog_assoc ca, clw_catalog c "
                    + "  where ba.BUS_ENTITY1_ID = " + siteId
                    + "  and ba.BUS_ENTITY_ASSOC_CD = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+"' "
                    + "  and ba.BUS_ENTITY2_ID = ca.BUS_ENTITY_ID "
                    + "  and ca.CATALOG_ASSOC_CD = '"+RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT+"' "
                    + "  and ca.CATALOG_ID = c.CATALOG_ID "
                    + "  and c.CATALOG_STATUS_CD = '"+RefCodeNames.CATALOG_STATUS_CD.ACTIVE+"' "
                    + "  and c.CATALOG_TYPE_CD = '"+ RefCodeNames.CATALOG_TYPE_CD.ACCOUNT +"' "
                    + "                          )";


            	Statement s1 = mCon.createStatement();
            	ResultSet rs2 = s1.executeQuery(q2);
            	if ( rs2.next() ) {
            		newcostcenterid = rs2.getInt(1);
            		if ( newcostcenterid > 0 && newcostcenterid != oItm.getCostCenterId()) {
            			oItm.setCostCenterId(newcostcenterid);
            			OrderItemDataAccess.update(mCon,oItm);
            			String u = " delete from clw_site_ledger where "
            				+ " order_id = " + orderid
                            + " and cost_center_id = " +
                            newcostcenterid  ;
            			s1.executeUpdate(u);

            		}

            	}
            	rs2.close();
            	s1.close();
            //dblog( "-- done with orderid=" + orderid );
            }
        }
    }




    private class CacheEntry{
        private Date mValidUntil;
        private Object mCahcheValue;
        private CacheEntry(Object cacheVal){
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.HOUR, 24);
            mValidUntil = cal.getTime();
            mCahcheValue = cacheVal;
        }
        private boolean isValid(){
            if(mValidUntil.before(new Date())){
                return true;
            }else{
                return false;
            }
        }
        private Object getCachedValue(){
            return mCahcheValue;
        }
    }
}
