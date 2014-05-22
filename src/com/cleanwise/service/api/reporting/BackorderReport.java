/**
 *
 */
package com.cleanwise.service.api.reporting;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
/**
 * @author ssharma
 *
 */
public class BackorderReport implements GenericReportMulti {

	public GenericReportResultViewVector process(ConnectionContainer pCons,
			GenericReportData pReportData,
			Map pParams) throws Exception {

		Connection con = pCons.getDefaultConnection();
		GenericReportResultViewVector resultV = new GenericReportResultViewVector();
		
		try {

			String backorderDateParams = (String)ReportingUtils.getParam(pParams, "SINGLE_DAYS_BACK_7");
				
			if (!Utility.isSet(backorderDateParams)) {
				String errorMess = "Backorder date not specified";
				throw new Exception(errorMess);
			}
			
			String userIdStr = (String)ReportingUtils.getParam(pParams, "CUSTOMER");
			
            int userId = 0;
            if (Utility.isSet(userIdStr)) {
                userId = Integer.parseInt(userIdStr);
            }
            
            APIAccess factory = new APIAccess();
            User userEjb = factory.getUserAPI();
            
            ArrayList sites = new ArrayList();
            BusEntityDataVector sitesV = userEjb.getSiteCollection(userId);
            for(int i=0; i<sitesV.size(); i++){
            	BusEntityData bed = (BusEntityData)sitesV.get(i);
            	sites.add(new Integer(bed.getBusEntityId()));
            }
            
            String userSites = null;
	        String sites1 = null;
	        String sites2 = null;

	        if(sites.size() > 1000){

	        	List id1 = sites.subList(0, 1000);
	        	List id2 = sites.subList(1000, sites.size());

	        	for(Iterator iter1=id1.iterator(); iter1.hasNext();){
		        	Integer siteIdI1 = (Integer) iter1.next();

		        	if(sites1==null) {
		        		sites1 = siteIdI1.toString();
		        	} else {
		        		sites1 += ","+siteIdI1.toString();
		        	}

		        }

	        	for(Iterator iter2=id2.iterator(); iter2.hasNext();){
		        	Integer siteIdI2 = (Integer) iter2.next();

		        	if(sites2==null) {
		        		sites2 = siteIdI2.toString();
		        	} else {
		        		sites2 += ","+siteIdI2.toString();
		        	}

		        }

	        }else{

		        for(Iterator iter=sites.iterator(); iter.hasNext();){
		        	Integer siteIdI = (Integer) iter.next();

		        	if(sites==null) {
		        		userSites = siteIdI.toString();
		        	} else {
		        		userSites += ","+siteIdI.toString();
		        	}

		        }
	        }
            

			/*String sql= "SELECT UPPER(b.location) AS location,"+
						"po.po_date AS sys_accepted_date,"+
						"b.item_num AS item_num,"+
						"oi.item_short_desc AS item_desc,"+
						"oi.manu_item_sku_num AS mfg_item_num,"+
						"oi.total_quantity_ordered AS order_qty,"+
						"oi.dist_item_uom AS order_qty_uom,"+
						"oia.quantity AS backorder_qty,"+
						"oi.dist_item_uom AS backorder_qty_uom "+
						"FROM "+
						"CLW_ORDER o, CLW_ORDER_ITEM oi,CLW_PURCHASE_ORDER po, CLW_ORDER_ITEM_ACTION oia, "+
						"(SELECT location,item_num,po_num FROM CLW_BACKORDER "+
						"WHERE TO_DATE(mod_date) = TO_DATE('"+backorderDateParams+"','yyyy/mm/dd')) b "+
						"WHERE "+
						"o.order_id = oi.order_id "+
						"AND po.order_id = o.order_id "+
						"AND po.outbound_po_num = b.po_num "+
						"AND oi.outbound_po_num = b.po_num "+
						"AND oi.dist_item_sku_num = TO_CHAR(b.item_num) "+
						"AND oia.order_item_id = oi.order_item_id "+
						"AND oia.action_cd  IN ('ACK Backordered','Backordered','Canceled') ";*/
	        
	        String sql = "SELECT UPPER(s.short_desc) AS location,"+
	        				"po.po_date AS sys_accepted_date,"+
	        				"oi.dist_item_sku_num AS item_num,"+
	        				"oi.item_short_desc AS item_desc,"+
	        				"oi.manu_item_sku_num AS mfg_item_num,"+
	        				"oi.total_quantity_ordered AS order_qty,"+
	        				"oi.dist_item_uom AS order_qty_uom,"+
	        				"oia.quantity AS backorder_qty,"+
	        				"oi.dist_item_uom AS backorder_qty_uom, " +
	        				"oa.city AS city, oa.state_province_cd as stateCd "+
	        				"FROM "+
	        				"CLW_ORDER o, CLW_ORDER_ITEM oi,CLW_PURCHASE_ORDER po, " +
	        				"CLW_ORDER_ITEM_ACTION oia, clw_bus_entity s, " +
	        				"clw_order_address oa "+
	        				"WHERE "+
	        				"o.order_id = oi.order_id "+
	        				"and o.site_id = s.bus_entity_id "+
	        				"AND po.order_id = o.order_id AND "+
	        				"oia.action_date >=  TO_DATE('"+backorderDateParams+"','MM/dd/yyyy') " +
	        				"and oia.action_date < TO_DATE('"+backorderDateParams+"','MM/dd/yyyy')+1 and "+
	        				"oia.order_id = o.order_id and oia.order_item_id = oi.order_item_id and "+
	        				"po.outbound_po_num = oi.outbound_po_num and po.purchase_order_id = oi.purchase_order_id "+
	        				"AND oia.action_cd  IN ('ACK Backordered','Backordered','Canceled') " +
	        				"and o.order_id = oa.order_id and oa.address_type_cd = 'SHIPPING' ";

			if(sites1 !=null && sites2 !=null){
				sql +=
					"AND ( o.site_id in ("+sites1+") "+
					"OR o.site_id in ("+sites2+")) "+
					" ORDER BY location";
			}else{
        		sql +=
        			"AND o.site_id in ("+userSites+") "+
					" ORDER BY location";
			}
			
			Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        ArrayList backorders = new ArrayList();
	        while(rs.next()) {
	        	BackorderDetail bod = new BackorderDetail();
                bod.location = rs.getString("location");
                bod.sysAcceptedDate = rs.getDate("sys_accepted_date");
                bod.itemNum = rs.getLong("item_num");
                bod.itemDesc = rs.getString("item_desc");
                bod.mfgItemNum = rs.getString("mfg_item_num");
                bod.orderQty = rs.getInt("order_qty");
                bod.orderQtyUom = rs.getString("order_qty_uom");
                bod.backorderQty = rs.getInt("backorder_qty");
                bod.backorderUom = rs.getString("backorder_qty_uom");
                bod.city = rs.getString("city");
                bod.stateCd = rs.getString("stateCd");

                backorders.add(bod);
            }
            rs.close();
            stmt.close();

            GenericReportResultView result = GenericReportResultView.createValue();
            result.setTable(new ArrayList());
            for (Iterator iter1=backorders.iterator(); iter1.hasNext();) {
            	BackorderDetail det = (BackorderDetail) iter1.next();
                result.getTable().add(det.toList());
            }

            GenericReportColumnViewVector boHeader = getBackorderReportHeader();
            result.setColumnCount(boHeader.size());
            result.setHeader(boHeader);
            result.setName("Daily Back Order Report");
            result.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
            result.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
            resultV.add(result);

			return resultV;
		} catch (NumberFormatException e) {
			throw new Exception(e.toString());
		}
	}

	private GenericReportColumnViewVector getBackorderReportHeader() {
		GenericReportColumnViewVector header = new GenericReportColumnViewVector();

		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Location", 0, 255, "VARCHAR2", "30", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "City", 0, 255, "VARCHAR2", "30", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "State", 0, 255, "VARCHAR2", "10", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "System Accepted Date", 0, 0,"DATE", "10", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Item #", 0,38, "NUMBER","10",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Item Description", 0, 255, "VARCHAR2","30",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Mfg. Item #", 0,38, "NUMBER","10",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Order Qty", 0,38, "NUMBER","10",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Order Qty UOM", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Back Order Qty", 0,38, "NUMBER","10",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Back Order Qty UOM", 0, 255, "VARCHAR2","6",false));

        return header;
	}

	private class BackorderDetail {

		String location;
		java.util.Date sysAcceptedDate;
    	long itemNum;
    	String itemDesc;
    	String mfgItemNum;
    	int orderQty;
    	String orderQtyUom;
    	int backorderQty;
    	String backorderUom;
    	String city;
    	String stateCd;

    	private ArrayList toList() {

    		ArrayList list = new ArrayList();
    		list.add(location);
    		list.add(city);
            list.add(stateCd);
          	list.add(sysAcceptedDate);
            list.add(new Long(itemNum));
            list.add(itemDesc);
            list.add(mfgItemNum);
            list.add(new Integer(orderQty));
            list.add(orderQtyUom);
            list.add(new Integer(backorderQty));
            list.add(backorderUom);
            return list;
    	}

	}
}
