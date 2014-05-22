/**
 * 
 */
package com.cleanwise.service.api.reporting;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.service.api.value.IdVector;

/**
 * @author ssharma
 *
 */
public class OrdersAtAGlanceReport implements  GenericReportMulti {
	 
	protected static final String[] COL_WIDTH = new String[]
	    {"30","20","20","11","30","15","15","5","5","10","20","20","20","20","30",
	        "5","10","10","20","6","10"};
	
	@Override
	public GenericReportResultViewVector process(ConnectionContainer pCons,
			GenericReportData pReportData, Map pParams) throws Exception {
		
		Connection con = pCons.getDefaultConnection();
		GenericReportResultViewVector resultV = new GenericReportResultViewVector();
		
		try{
			
			String pBegDate = (String) pParams.get("BEG_DATE");
	        String pEndDate = (String) pParams.get("END_DATE");
	        
	        String userIdS = (String) pParams.get("CUSTOMER");
	        if(userIdS==null || userIdS.trim().length()==0){
	          String mess = "^clw^No user provided^clw^";
	          throw new Exception(mess);
	        }
	        int pUserId = 0;
	        try {
	        	pUserId = Integer.parseInt(userIdS);
	        }catch (Exception exc1) {
	          String mess = "^clw^Wrong user id format^clw^";
	          throw new Exception(mess);
	        }
			
			//List of lowest level categories
	        String catIdS = (String) pParams.get("CATEGORIES_OPT");

	        List categories = new ArrayList();
	        if(catIdS != null && catIdS.length()>0){

		        StringTokenizer token = new StringTokenizer(catIdS,",");

		        while(token.hasMoreTokens()) {
		           String catS = token.nextToken();
		           if(catS.trim().length()>0)
		           try {
		             int category = Integer.parseInt(catS.trim());
		             categories.add(new Integer(category));
		           } catch(Exception exc){
		             String mess = "^clw^"+catS+" is not a valid category identifier ^clw^";
		             throw new Exception(mess);
		           }

		        }
	        }
			
			String siteIdS = (String) pParams.get("LOCATE_SITE_MULTI_OPT");

	        List sites = new ArrayList();
	        if(siteIdS != null && siteIdS.length()>0){

		        StringTokenizer token = new StringTokenizer(siteIdS,",");

		        while(token.hasMoreTokens()) {
		           String siteS = token.nextToken();
		           if(siteS.trim().length()>0)
		           try {
		             int site = Integer.parseInt(siteS.trim());
		             sites.add(new Integer(site));
		           } catch(Exception exc){
		             String mess = "^clw^"+siteS+" is not a valid site identifier ^clw^";
		             throw new Exception(mess);
		           }

		        }
	        }
	        
	        String mfgIdS = (String) pParams.get("MANUFACTURER_OPT");

	        List mfgs = new ArrayList();
	        if(mfgIdS != null && mfgIdS.length()>0){

		        StringTokenizer token = new StringTokenizer(mfgIdS,",");

		        while(token.hasMoreTokens()) {
		           String mfgS = token.nextToken();
		           if(mfgS.trim().length()>0)
		           try {
		             int mfg = Integer.parseInt(mfgS.trim());
		             mfgs.add(new Integer(mfg));
		           } catch(Exception exc){
		             String mess = "^clw^"+mfgS+" is not a valid manufacturer ^clw^";
		             throw new Exception(mess);
		           }

		        }
	        }
	        
	        ArrayList<String> pOrderStatusList = new ArrayList<String>();
	        pOrderStatusList.add(RefCodeNames.ORDER_STATUS_CD.ORDERED);
	        pOrderStatusList.add(RefCodeNames.ORDER_STATUS_CD.INVOICED);
	        pOrderStatusList.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
			
			String sql = "select "+
				"site.short_desc as siteName,o.order_num as confNum, o.request_po_num as poNum, o.original_order_date, "+
				"i.sku_num as sysSku,oi.dist_item_sku_num , oi.dist_item_uom, oi.dist_item_pack,oi.item_size, "+
				"ia.item2_id as categoryId, categ.short_desc as catName, i.item_id, i.short_desc as itemDesc, " +
				"oi.manu_item_sku_num, mfg.short_desc as mfgName, "+
				"oi.dist_item_short_desc as distName, "+
				"sum(oi.cust_contract_price * oi.total_quantity_ordered) as lineTotal, oi.total_quantity_ordered as qty, "+
				"green.item_mapping_id as greenF ,"+
				"prop.clw_value as rebill, o.currency_cd, oi.sale_type_cd "+
				"from "+
				"clw_order o, clw_order_item oi, clw_item i, clw_user_assoc ua, clw_bus_entity acc, "+
				"clw_user_assoc ua2, clw_bus_entity site, clw_catalog c, clw_catalog_assoc ca, clw_item_assoc ia, "+
				"clw_item_mapping im, clw_bus_entity mfg,"+
				"clw_item categ, clw_item_mapping green, clw_order_property prop "+
				"where "+
				"o.original_order_date BETWEEN " +
				"TO_DATE('"+pBegDate+"','MM/dd/yyyy') AND TO_DATE('"+pEndDate+"','MM/dd/yyyy') "+
				"and o.order_id = oi.order_id "+
				"and ua.user_id = "+pUserId+
				" and ua2.user_id = ua.user_id and ua2.bus_entity_id = site.bus_entity_id " +
				"and ua2.user_assoc_cd='" + RefCodeNames.USER_ASSOC_CD.SITE +"' "+
				"and o.site_id = site.bus_entity_id ";
			
				if(sites!=null && sites.size()>0){
	    			sql = sql + " and site.bus_entity_id in ("+Utility.toCommaSting(sites)+") ";
	    		}
			
				sql = sql+"and ua.bus_entity_id = acc.bus_entity_id "+
					"and ua.user_assoc_cd='"+RefCodeNames.USER_ASSOC_CD.ACCOUNT +"' " +
					"and o.account_id = acc.bus_entity_id "+
					"and ca.bus_entity_id = acc.bus_entity_id "+
					"and acc.bus_entity_type_cd='"+RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT+"' " +
					"and ca.catalog_assoc_cd='"+RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT+"' "+
					"and ca.catalog_id = c.catalog_id "+
					"and c.catalog_type_cd='"+RefCodeNames.CATALOG_TYPE_CD.ACCOUNT+"' "+
					"and o.order_status_cd in ("+Utility.toCommaSting(pOrderStatusList, '\'')+") "+
					" and o.order_id = prop.order_id(+) and prop.order_property_type_cd(+)='"+RefCodeNames.ORDER_PROPERTY_TYPE_CD.REBILL_ORDER+"' "+
					"and i.item_id = oi.item_id and " +
					"ia.item1_id = i.item_id ";
				
					if(categories!=null && categories.size()>0){
		    			sql = sql + " and ia.item2_id in ("+Utility.toCommaSting(categories)+") ";
		    		}
				
					sql = sql +"and ia.catalog_id = c.catalog_id "+
						"and i.item_id = im.item_id "+
						"and mfg.bus_entity_id = im.bus_entity_id "+
						"and ia.item2_id = categ.item_id "+
						"and im.item_mapping_cd='"+RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER+
						"' and mfg.bus_entity_type_cd='"+RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER+"' ";
				
				if(mfgs!=null && mfgs.size()>0){
					sql = sql + "and mfg.bus_entity_id in ("+Utility.toCommaSting(mfgs)+") ";
				}
	    		
				sql = sql+"and green.item_id(+) = i.item_id and green.item_mapping_cd(+)='"
					+RefCodeNames.ITEM_MAPPING_CD.ITEM_CERTIFIED_COMPANY+"' "+
					"group by site.short_desc, o.order_num, o.request_po_num, o.original_order_date, i.sku_num, " +
					"oi.dist_item_sku_num, oi.dist_item_uom, oi.dist_item_pack, oi.item_size, ia.item2_id, categ.short_desc, " +
					"i.item_id, i.short_desc, oi.manu_item_sku_num, mfg.short_desc, oi.dist_item_short_desc, " +
					"oi.total_quantity_ordered, green.item_mapping_id, prop.clw_value, o.currency_cd, oi.sale_type_cd "+
					"order by i.item_id";

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			ArrayList allOrders = new ArrayList();
			while(rs.next()) {
				OrdersAtAGlanceDetail ordersDet = new OrdersAtAGlanceDetail();
				ordersDet.location = rs.getString("siteName");
				ordersDet.confNum = rs.getString("confNum");
				ordersDet.poNum = rs.getString("poNum");
				ordersDet.orderDate = rs.getDate("original_order_date");
				ordersDet.systemSku = rs.getString("sysSku");
				ordersDet.sku = rs.getString("dist_item_sku_num");
				ordersDet.distSku = rs.getString("dist_item_sku_num");
				ordersDet.uom = rs.getString("dist_item_uom");
				ordersDet.pack = rs.getString("dist_item_pack");
				ordersDet.itemSize = rs.getString("item_size");
				ordersDet.category = rs.getString("catName");
				ordersDet.itemDesc = rs.getString("itemDesc");
				ordersDet.mfgSku = rs.getString("manu_item_sku_num");
				ordersDet.mfg = rs.getString("mfgName");
				ordersDet.distributor = rs.getString("distName");
				ordersDet.lineTotal = rs.getBigDecimal("lineTotal");
				ordersDet.orderQty = rs.getInt("qty");
				
				String greenF = rs.getString("greenF");
				if(greenF != null){
					ordersDet.greenItem = "TRUE";
				}else{
					ordersDet.greenItem = "FALSE";
				}
				
				String rebillType = rs.getString("rebill");
				if(rebillType != null){
					ordersDet.orderType = "REBILL";
				}else{
					ordersDet.orderType = "STANDARD";
				}
				
				ordersDet.currencyCd = rs.getString("currency_cd");
				ordersDet.saleType = rs.getString("sale_type_cd");
				
				allOrders.add(ordersDet);
			}
			rs.close();
			stmt.close();
			
			GenericReportResultView result = GenericReportResultView.createValue();
			result.setTable(new ArrayList());
			for (Iterator iter1=allOrders.iterator(); iter1.hasNext();) {
				OrdersAtAGlanceDetail det = (OrdersAtAGlanceDetail) iter1.next();
				result.getTable().add(det.toList());
			}
			
			GenericReportColumnViewVector oagHeader = getOrdersAtAGlanceReportHeader();
			result.setColumnCount(oagHeader.size());
			result.setHeader(oagHeader);
			result.setName(pReportData.getName());
			result.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
			result.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
			resultV.add(result);
			
			return resultV;
						
			
		} catch (NumberFormatException e) {
			throw new Exception(e.toString());
		}
		
	}
	
	private GenericReportColumnViewVector getOrdersAtAGlanceReportHeader() {
		GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("location",  "COL_LOCATION", null,COL_WIDTH[0]));
        header.add(ReportingUtils.createGenericReportColumnView("Confirm_Num", "COL_CONFIRM_NUM", null,COL_WIDTH[1]));
        header.add(ReportingUtils.createGenericReportColumnView("PO_Num", "COL_PO_NUM", null,COL_WIDTH[2]));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Order_Date", 0, 0, "DATE", COL_WIDTH[3], false));
        header.add(ReportingUtils.createGenericReportColumnView("Category", "COL_CATEG", null,COL_WIDTH[4]));
        header.add(ReportingUtils.createGenericReportColumnView("SystemSku","COL_SYS_SKU","TYPE_PERCENT_DATA",COL_WIDTH[5]));
        header.add(ReportingUtils.createGenericReportColumnView("Sku","COL_SKU","TYPE_PERCENT_DATA",COL_WIDTH[6]));
        header.add(ReportingUtils.createGenericReportColumnView("UOM", "COL_UOM", null,COL_WIDTH[7]));
        header.add(ReportingUtils.createGenericReportColumnView("Pack", "COL_PACK", null,COL_WIDTH[8]));
        header.add(ReportingUtils.createGenericReportColumnView("Item_Size", "COL_SIZE", null,COL_WIDTH[9]));   
        header.add(ReportingUtils.createGenericReportColumnView("Man_Sku", "COL_MANUF_SKU", null,COL_WIDTH[10]));
        header.add(ReportingUtils.createGenericReportColumnView("Manufacturer", "COL_MANUF_NAME", null,COL_WIDTH[11]));       
        header.add(ReportingUtils.createGenericReportColumnView("Dist_Sku", "COL_DIST_SKU", null,COL_WIDTH[12]));       
        header.add(ReportingUtils.createGenericReportColumnView("Distributor", "COL_DIST_NAME", null,COL_WIDTH[13]));
        header.add(ReportingUtils.createGenericReportColumnView("ItemDesc", "COL_PROD_NAME", null,COL_WIDTH[14]));
        header.add(ReportingUtils.createGenericReportColumnView("Green_Item", "COL_GREEN_ITEM", null,COL_WIDTH[15]));
        header.add(ReportingUtils.createGenericReportColumnView("Sale_Type", "COL_SALE_TYPE", null,COL_WIDTH[16]));
        header.add(ReportingUtils.createGenericReportColumnView("Qty", "COL_QUANTITY","TYPE_QTY_DATA",COL_WIDTH[17]));
        header.add(ReportingUtils.createGenericReportColumnView("Line_Total", "COL_TOTAL","TYPE_AMOUNT_DATA",COL_WIDTH[18]));
        header.add(ReportingUtils.createGenericReportColumnView("Currency_Code", "rowInfo_Currency Code", 0, 255, "VARCHAR2", COL_WIDTH[19], false));
        header.add(ReportingUtils.createGenericReportColumnView("Order Type","COL_ORDER_TYPE",null,COL_WIDTH[20]));
        
        return header;
	}
	
	private class OrdersAtAGlanceDetail {

		String location;
		String confNum;
		String poNum;
		java.util.Date orderDate;
		String category;
		String systemSku;
		String sku;
		String uom;
		String pack;
		String itemSize;
		String mfgSku;
		String mfg;
		String distSku;
		String distributor;
    	String itemDesc;
    	String greenItem;
    	String saleType;	
    	int orderQty;
    	BigDecimal lineTotal;
    	String currencyCd;
    	String orderType;

    	private ArrayList toList() {

    		ArrayList list = new ArrayList();
    		list.add(location);
    		list.add(confNum);
    		list.add(poNum);
    		list.add(orderDate);
    		list.add(category);
    		list.add(systemSku);
    		list.add(sku);
    		list.add(uom);
    		list.add(pack);
    		list.add(itemSize);
    		list.add(mfgSku);
    		list.add(mfg);
    		list.add(distSku);
    		list.add(distributor);
    		list.add(itemDesc);
    		list.add(greenItem);
    		list.add(saleType);
    		list.add(orderQty);
    		list.add(lineTotal);
    		list.add("rowInfo_currencyCd=" + currencyCd);
    		list.add(orderType); 		
    		
            return list;
    	}

	}

}
