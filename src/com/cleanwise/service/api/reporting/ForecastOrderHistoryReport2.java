/**
 * ForecastOrderHistoryReport.java
 */
package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;


import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.Utility;
import java.util.Map;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Date;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.math.BigDecimal;
import java.util.StringTokenizer;
import java.util.GregorianCalendar;
import org.apache.log4j.Logger;

/**
 * @author Ssharma
 *
 */
public class ForecastOrderHistoryReport2 implements GenericReportMulti {
        private static final Logger log = Logger.getLogger(ForecastOrderHistoryReport2.class);
        
	    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams)
	    throws Exception
	    {

	    	Connection con = pCons.getDefaultConnection();
	        GenericReportResultViewVector resultV = new GenericReportResultViewVector();

	        // Date interval
	        String begDateS = null;
	        String endDateS = null;
	        begDateS = (String)ReportingUtils.getParam(pParams,"BEG_DATE");
	        endDateS = (String)ReportingUtils.getParam(pParams,"END_DATE");

	        if(!ReportingUtils.isValidDate(begDateS) && begDateS.length()!=0){
	            String mess = "^clw^\""+begDateS+"\" is not a valid date of the form: mm/dd/yyyy^clw^";
	            throw new Exception(mess);
	        }
	        if(!ReportingUtils.isValidDate(endDateS) && endDateS.length()!=0){
	            String mess = "^clw^\""+endDateS+"\" is not a valid date of the form: mm/dd/yyyy^clw^";
	            throw new Exception(mess);
	        }

	        //Month year dropdown
	        String begDateF=null;
	        String endDateF=null;

	        if(begDateS.length()==0 && endDateS.length()==0){

	        	int endYear = Integer.parseInt((String) pParams.get("endYear_OPT"));
	        	int endMonth = Integer.parseInt((String) pParams.get("endMonth_OPT"));
	        	if(endYear!=0 && endMonth !=0){

	        		GregorianCalendar endC = new GregorianCalendar(endYear,endMonth-1,1);
	        		endC.set(Calendar.DAY_OF_MONTH, endC.getActualMaximum(Calendar.DATE));
	        		Date endDateC = endC.getTime();

	        		//GregorianCalendar begC = endC;
	        		//begC.set(GregorianCalendar.YEAR, endYear-1);

	        		GregorianCalendar begC = new GregorianCalendar(endYear,endMonth-1,1);
	        		Date beginDateC = begC.getTime();

	        		SimpleDateFormat df = new SimpleDateFormat("d MMM yyyy");

	        		begDateF= df.format(beginDateC);
	        		endDateF=df.format(endDateC);

	        	}
	        }

	        //Sites
	        String siteIdS = (String) pParams.get("SITES");

	        IdVector siteIdV = new IdVector();
	        if(siteIdS.length()!=0 && siteIdS != null){

	            StringTokenizer token = new StringTokenizer(siteIdS,",");

		        while(token.hasMoreTokens()) {
		           String siteS = token.nextToken();
		           if(siteS.trim().length()>0)
		           try {
		             int site = Integer.parseInt(siteS.trim());
		             siteIdV.add(new Integer(site));
		           } catch(Exception exc){
		             String mess = "^clw^"+siteS+" is not a valid site identifier ^clw^";
		             throw new Exception(mess);
		           }

		        }
	        }
	        String sites = null;
        	for(Iterator iter=siteIdV.iterator(); iter.hasNext();){
        		Integer siteIdI = (Integer) iter.next();

        		if(sites==null) {
        			sites = siteIdI.toString();
                  } else {
                	sites += ","+siteIdI.toString();
                  }

        	}

        	// Get all accounts for sites
        	IdVector acctIdV = new IdVector();
        	if(siteIdV.size() !=0 && !siteIdV.isEmpty()){

        		DBCriteria crit = new DBCriteria();
        		crit.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, sites);
        		crit.addEqualToIgnoreCase(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
        				RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
        		BusEntityAssocDataVector beaDV =
        			BusEntityAssocDataAccess.select(con, crit);

        		for(int i=0; i<beaDV.size(); i++){
        			BusEntityAssocData beaD = (BusEntityAssocData)beaDV.get(i);
        			int acctId = beaD.getBusEntity2Id();
        			if(! acctIdV.contains(new Integer(acctId))){
        				acctIdV.add(new Integer(acctId));
        			}
        		}
        	}

        	String accts = null;
        	for(Iterator iter1=acctIdV.iterator(); iter1.hasNext();){
        		Integer acctIdI = (Integer) iter1.next();

        		if(accts==null) {
        			accts = acctIdI.toString();
                  } else {
                	accts += ","+acctIdI.toString();
                  }

        	}

	        String sql= null;

	        if(begDateS.length()!=0 && endDateS.length()!=0){

	        	sql =
	        		"SELECT  "+
   	        		"site.short_desc AS site_name, "+
	        		"NVL(o.cust_item_sku_num,NVL((SELECT customer_sku_num FROM clw_catalog_assoc ca, clw_catalog_structure cs WHERE cs.catalog_id = ca.catalog_id AND ca.bus_entity_id = site.bus_entity_id AND cs.item_id =i.item_id),im.item_num)) as sku_num, "+
	        		"i.short_desc, "+
	        		"inv.par AS inventory_par_value, inv.qty_on_hand AS inventory_qty_on_hand, "+
	        		"inv.order_qty AS total_quantity_ordered, inv.add_date AS add_date , "+
	        		"o.order_num , o.ord_add_date, "+
	        		"cat.short_desc AS category, o.uom, NVL(o.price, inv.price) AS price FROM "+
	        		"(SELECT o.site_id,o.order_num,TRUNC(o.original_order_date) AS ord_add_date, "+
	        		  "oi.item_id,oi.cust_item_sku_num,oi.dist_erp_num,  "+
	        		  "NVL(oi.cust_item_uom,oi.item_uom) as uom, "+
	        		  "oi.cust_contract_price as price "+
	        		  "FROM CLW_ORDER_ITEM oi, CLW_ORDER o "+
	        		  "WHERE o.order_status_cd NOT IN ('Cancelled','ERP Rejected','Rejected','Duplicate','REFERENCE_ONLY') "+
	        		  "AND o.order_id = oi.order_id AND o.order_source_cd='Inventory') o, "+
	        		"CLW_INVENTORY_ORDER_QTY inv,  "+
	        		"CLW_BUS_ENTITY site, CLW_ITEM i,  "+
	        		"(SELECT item_id,item_num FROM CLW_ITEM_MAPPING WHERE item_mapping_cd = 'ITEM_DISTRIBUTOR')im , "+
	        		"clw_bus_entity_assoc ba, CLW_INVENTORY_ITEMS ii, "+
              "(SELECT itemass.item1_id, cat.short_desc,ba.bus_entity1_id as site_id  FROM clw_item cat, clw_catalog c, clw_catalog_assoc cass, "+
	        		      "clw_catalog_structure cst, clw_item_assoc itemass, clw_bus_entity_assoc ba "+
                    "WHERE "+
                    "cat.item_id = itemass.item2_id "+
	        		      "AND itemass.item_assoc_cd='PRODUCT_PARENT_CATEGORY' "+
	        		      "AND cst.item_id = cat.item_id "+
	        		      "AND itemass.catalog_id = c.catalog_id "+
	        		      "AND cst.catalog_id = c.catalog_id "+
	        		      "AND c.catalog_status_cd='ACTIVE' "+
	        		      "AND c.catalog_type_cd='ACCOUNT' "+
	        		      "AND cass.catalog_id = c.catalog_id  "+
	        		      "AND cass.catalog_assoc_cd='CATALOG_ACCOUNT' "+
	        		      "AND cass.bus_entity_id=ba.bus_entity2_id "+
              ") cat "+
"WHERE "+
	        		"site.bus_entity_id= inv.bus_entity_id "+
	        		"AND inv.item_id = i.item_id "+
	        		"AND ba.bus_entity1_id = inv.bus_entity_id "+
	        		"AND ba.bus_entity_assoc_cd = 'SITE OF ACCOUNT' "+
	        		"AND inv.item_id = ii.item_id (+) "+
	        		"AND i.item_id = ii.item_id "+
	        		"AND inv.item_id = im.item_id(+) "+
	        		"AND TRUNC(inv.add_date) = o.ord_add_date (+) "+
	        		"AND inv.item_type = 'Inventory' "+
	        		"AND inv.item_id = o.item_id (+) "+
	        		"AND inv.add_date BETWEEN TO_DATE('"+begDateS+"','mm/dd/yyyy') "+
	        		"AND TO_DATE('"+endDateS+"','mm/dd/yyyy') +1 "+
	        		"AND o.site_id(+)=inv.bus_entity_id "+
	        		"AND inv.bus_entity_id = cat.site_id (+) "+
              "AND ba.bus_entity2_id IN ("+accts+") "+
              "AND ba.bus_entity1_id  IN ("+sites+") "+
              "AND inv.item_id = cat.item1_id (+) "+
	        		"ORDER BY site.short_desc,add_date, "+
	        		"sku_num";

	        }
	        else if(begDateF != null && endDateF != null){

	        	sql =
	        		"SELECT  "+
   	        		"site.short_desc AS site_name, "+
	        		"NVL(o.cust_item_sku_num,NVL((SELECT customer_sku_num FROM clw_catalog_assoc ca, clw_catalog_structure cs WHERE cs.catalog_id = ca.catalog_id AND ca.bus_entity_id = site.bus_entity_id AND cs.item_id =i.item_id),im.item_num)) as sku_num, "+
	        		"i.short_desc, "+
	        		"inv.par AS inventory_par_value, inv.qty_on_hand AS inventory_qty_on_hand, "+
	        		"inv.order_qty AS total_quantity_ordered, inv.add_date AS add_date , "+
	        		"o.order_num , o.ord_add_date, "+
	        		"cat.short_desc AS category, o.uom, NVL(o.price, inv.price) AS price FROM "+
	        		"(SELECT o.site_id,o.order_num,TRUNC(o.original_order_date) AS ord_add_date, "+
	        		  "oi.item_id,oi.cust_item_sku_num,oi.dist_erp_num,  "+
	        		  "NVL(oi.cust_item_uom,oi.item_uom) as uom, "+
	        		  "oi.cust_contract_price as price "+
	        		  "FROM CLW_ORDER_ITEM oi, CLW_ORDER o "+
	        		  "WHERE o.order_status_cd NOT IN ('Cancelled','ERP Rejected','Rejected','Duplicate','REFERENCE_ONLY') "+
	        		  "AND o.order_id = oi.order_id AND o.order_source_cd='Inventory') o, "+
	        		"CLW_INVENTORY_ORDER_QTY inv,  "+
	        		"CLW_BUS_ENTITY site, CLW_ITEM i,  "+
	        		"(SELECT item_id,item_num FROM CLW_ITEM_MAPPING WHERE item_mapping_cd = 'ITEM_DISTRIBUTOR')im , "+
	        		"clw_bus_entity_assoc ba, CLW_INVENTORY_ITEMS ii, "+
              "(SELECT itemass.item1_id, cat.short_desc,ba.bus_entity1_id as site_id  FROM clw_item cat, clw_catalog c, clw_catalog_assoc cass, "+
	        		      "clw_catalog_structure cst, clw_item_assoc itemass, clw_bus_entity_assoc ba "+
                    "WHERE "+
                    "cat.item_id = itemass.item2_id "+
	        		      "AND itemass.item_assoc_cd='PRODUCT_PARENT_CATEGORY' "+
	        		      "AND cst.item_id = cat.item_id "+
	        		      "AND itemass.catalog_id = c.catalog_id "+
	        		      "AND cst.catalog_id = c.catalog_id "+
	        		      "AND c.catalog_status_cd='ACTIVE' "+
	        		      "AND c.catalog_type_cd='ACCOUNT' "+
	        		      "AND cass.catalog_id = c.catalog_id  "+
	        		      "AND cass.catalog_assoc_cd='CATALOG_ACCOUNT' "+
	        		      "AND cass.bus_entity_id=ba.bus_entity2_id "+
              ") cat "+
"WHERE "+
	        		"site.bus_entity_id= inv.bus_entity_id "+
	        		"AND inv.item_id = i.item_id "+
	        		"AND ba.bus_entity1_id = inv.bus_entity_id "+
	        		"AND ba.bus_entity_assoc_cd = 'SITE OF ACCOUNT' "+
	        		"AND inv.item_id = ii.item_id (+) "+
	        		"AND i.item_id = ii.item_id "+
	        		"AND inv.item_id = im.item_id(+) "+
	        		"AND TRUNC(inv.add_date) = o.ord_add_date (+) "+
	        		"AND inv.item_type = 'Inventory' "+
	        		"AND inv.item_id = o.item_id (+) "+
	        		"AND inv.add_date BETWEEN TO_DATE('"+begDateF+"') "+
	        		"AND TO_DATE('"+endDateF+"') +1 "+
	        		"AND o.site_id(+)=inv.bus_entity_id "+
	        		"AND inv.bus_entity_id = cat.site_id (+) "+
              "AND ba.bus_entity2_id IN ("+accts+") "+
              "AND ba.bus_entity1_id  IN ("+sites+") "+
              "AND inv.item_id = cat.item1_id (+) "+
	        		"ORDER BY site.short_desc,add_date, "+
	        		"sku_num";


	        }

	        log.info("sql: "+sql);

	        Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        ArrayList allOrders = new ArrayList();
	        while(rs.next()) {
	        	ForecastOrderHistoryDetail foh = new ForecastOrderHistoryDetail();
                foh.locName = rs.getString("site_name");
                foh.PN = rs.getString("sku_num");
                foh.desc = rs.getString("short_desc");
                foh.forecastQty = rs.getInt("inventory_par_value");
                foh.onhandQty = rs.getString("inventory_qty_on_hand");
                foh.orderQty = rs.getInt("total_quantity_ordered");
                foh.invEntryDate = rs.getDate("add_date");
                foh.orderNum = rs.getLong("order_num");
                foh.orderDate = rs.getDate("ord_add_date");
                foh.category = rs.getString("category");
                foh.uom = rs.getString("uom");
                foh.price = rs.getBigDecimal("price");

                allOrders.add(foh);
            }
            rs.close();
            stmt.close();

            GenericReportResultView result = GenericReportResultView.createValue();
            result.setTable(new ArrayList());
            for (Iterator iter1=allOrders.iterator(); iter1.hasNext();) {
            	ForecastOrderHistoryDetail det = (ForecastOrderHistoryDetail) iter1.next();
                result.getTable().add(det.toList());
            }

            GenericReportColumnViewVector fohHeader = getForecastOrderHistoryReportHeader();
            result.setColumnCount(fohHeader.size());
            result.setHeader(fohHeader);
            result.setName("Forecast Order History Report");
            result.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
            result.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
            resultV.add(result);


	    	return resultV;
	    }



	    private GenericReportColumnViewVector getForecastOrderHistoryReportHeader() {
            GenericReportColumnViewVector header = new GenericReportColumnViewVector();

            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Location Name", 0, 255, "VARCHAR2","20",false));
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "P/N", 0, 255, "VARCHAR2","15",false));
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Description", 0, 255, "VARCHAR2","30",false));
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Forecast Qty", 0,38, "NUMBER","13",false));
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "On-hand Qty", 0,38, "NUMBER","10",false));
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Order Qty", 0,38, "NUMBER","9",false));
            header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Inv Entry Date",0,0,"DATE","10",false));
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.Long", "Order #", 0,38, "NUMBER","9",false));
            header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Order Date",0,0,"DATE","10",false));
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Category", 0, 255, "VARCHAR2","20",false));
            header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "UOM", 0, 255, "VARCHAR2","6",false));
            header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Price", 2, 20, "NUMBER","7",false));

            return header;
        }

	    private class ForecastOrderHistoryDetail {

	    	String locName;
	    	String PN;
	    	String desc;
	    	int forecastQty;
	    	int orderQty;
	    	String onhandQty;
	    	java.util.Date invEntryDate;
	    	long orderNum;
	    	java.util.Date orderDate;
	    	String category;
	    	String uom;
	    	BigDecimal price;

	    	private ArrayList toList() {

	    		ArrayList list = new ArrayList();
	    		list.add(locName);
	    		list.add(PN);
	            list.add(desc);
	            list.add(new Integer(forecastQty));
				if(Utility.isSet(onhandQty)){
					list.add(new Integer(onhandQty));
				}else{
					list.add("");
				}
	            list.add(new Integer(orderQty));
	            list.add(invEntryDate);
	            list.add(new Long(orderNum));
	            list.add(orderDate);
	            list.add(category);
	            list.add(uom);
	            list.add(price);
	            return list;
	    	}

	    }
}
