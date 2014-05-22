/*
 * ReSaleOrder.java
 *
 * Created on October 23, 2003, 2:23 PM
 */

package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.CatalogDataAccess;
import com.cleanwise.service.api.dao.ItemAssocDataAccess;
import com.cleanwise.service.api.dao.ItemDataAccess;
import com.cleanwise.service.api.dao.JoinDataAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.UserRightsTool;
import com.cleanwise.service.api.util.Utility;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.sql.*;
import java.math.BigDecimal;
/**
 *
 * @author  bstevens
 */
public class ReSaleOrderReportWithCost  implements GenericReportMulti {
	boolean mDisplaySalesTaxInfo;
	boolean mDisplayDistInfo;
	HashMap storeMap = new HashMap();
	HashMap masterItemCategoryMap = new HashMap();
	Connection con;
	private boolean multiAccounts = false;
	private HashMap accountMap = new HashMap();
	private boolean showPrice;


    public com.cleanwise.service.api.value.GenericReportResultViewVector process(com.cleanwise.service.api.util.ConnectionContainer pCons, com.cleanwise.service.api.value.GenericReportData pReportData, java.util.Map pParams)
    throws Exception {

        con = pCons.getDefaultConnection();
        GenericReportResultViewVector resultV =
        new GenericReportResultViewVector();
        //Lists of report results
        HashSet storeIdsInReport = new HashSet();
        ArrayList catTotals = new ArrayList();
        ArrayList orderTotals = new ArrayList();
        ArrayList details = new ArrayList();
        String begDateS = (String) pParams.get("BEG_DATE");
        String endDateS = (String) pParams.get("END_DATE");
        String dateFmt = (String) pParams.get("DATE_FMT");
        if ( null == dateFmt ) {
	          dateFmt = "MM/dd/yyyy";
	    }

        if(!ReportingUtils.isValidDate(begDateS, dateFmt)){
            String mess = "^clw^\""+begDateS+"\" is not a valid date of the form: " + dateFmt + "^clw^";
            throw new Exception(mess);
        }
        if(!ReportingUtils.isValidDate(endDateS, dateFmt)){
            String mess = "^clw^\""+endDateS+"\" is not a valid date of the form: " + dateFmt + "^clw^";
            throw new Exception(mess);
        }

        String accountIdS = (String) pParams.get("ACCOUNT_MULTI_OPT");
        String accountIdS2 = (String) pParams.get("runForAccounts");
        String siteIdS = (String) pParams.get("SITE_MULTI_OPT");

        if(accountIdS2 == null){
        	accountIdS2 = accountIdS;
        }else{
        	accountIdS2 += ","+accountIdS;
        }

        int acctCt = 0;
        multiAccounts = false;
        StringTokenizer token = new StringTokenizer(accountIdS2,",");
        while(token.hasMoreTokens()) {
           String acctS = token.nextToken();
           acctCt++;
           acctS=acctS.trim();
           if(acctS.length()>0)
           try {
             Integer.parseInt(acctS);
           } catch(Exception exc){
             String mess = "^clw^"+acctS+" is not a valid account identifier ^clw^";
             throw new Exception(mess);
           }
        }
        if(acctCt != 1){
        	multiAccounts = true;
        }

    	Iterator it3 = pParams.keySet().iterator();
	String repParams = "";
    	while(it3.hasNext()){
    		Object o = it3.next();
    		repParams += " " + o + "::"+ pParams.get(o);
    	}

        if(siteIdS != null){
        token = new StringTokenizer(siteIdS,",");
        while(token.hasMoreTokens()) {
           String siteS = token.nextToken();
           if(siteS.trim().length()>0)
           try {
             Integer.parseInt(siteS);
           } catch(Exception exc){
             String mess = "^clw^"+siteS+" is not a valid site identifier ^clw^";
             throw new Exception(mess);
           }

        }
        }

        ReportingUtils repUtil = new ReportingUtils();
        ReportingUtils.UserAccessDescription userDesc =
            repUtil.getUserAccessDescription(pParams,con);
        UserRightsTool urt = new UserRightsTool(userDesc.getUserData());
        showPrice = urt.getShowPrice();

        String orderTotalSql =
        "SELECT "+
        "site_name, (SELECT max(clw_value) FROM CLW_PROPERTY p WHERE p.short_desc = '"+RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER+"' AND p.property_type_cd = '"+RefCodeNames.PROPERTY_TYPE_CD.EXTRA+"' AND p.bus_entity_id = grp.site_id AND p.property_status_cd='"+RefCodeNames.PHONE_STATUS_CD.ACTIVE+"') AS projectCode, "+
        "order_num, request_po_num, sale_type_cd,dist_item_cost,total_price, original_order_date,total_tax_cost,total_misc_cost,total_freight_cost,store_id,account_id, CURRENCY_CD "+
        "FROM ( "+
        "SELECT ord.store_id, ord.site_id, site.short_desc AS site_name, "
            + " ord.account_id, "
            + " ord.order_num, request_po_num, "
            + " ord.total_tax_cost,ord.total_misc_cost,ord.total_freight_cost, " +
            "oi.dist_item_cost as dist_item_cost,"+
        "oi.sale_type_cd, SUM(total_quantity_ordered * cust_contract_price) as total_price, ord.original_order_date, ord.currency_cd "+
        "FROM  "+
        "CLW_ORDER ord, CLW_BUS_ENTITY site, CLW_ORDER_ITEM oi "+
        "WHERE  "+
        ReportingUtils.getValidOrdersSQL("ord") + " AND "+
        "ord.original_order_date between to_date('"+begDateS+"','"
+ dateFmt + "') AND to_date('"+endDateS+"','" + dateFmt + "') AND "+
        "(oi.order_item_status_cd NOT IN ('CANCELLED') OR oi.order_item_status_cd IS NULL) AND  "+
            "ord.site_id = site.bus_entity_id AND ord.order_id = oi.order_id ";

        if ( null != accountIdS && accountIdS.trim().length()>0) {
            orderTotalSql += " AND ord.account_id in ("+accountIdS+") ";
        }

        if ( null != siteIdS && siteIdS.trim().length()>0) {
            orderTotalSql += " AND ord.site_id in ("+siteIdS+") ";
        }

        if ( userDesc.hasAccessToAll() == false ) {
          if (userDesc.hasStoreAccess()) {
        	orderTotalSql += " and store_id in (" + userDesc.getAuthorizedSql() + ") ";            		  
      	  }else if (userDesc.hasAccountAccess()) {
                orderTotalSql += "and account_id in ("+
                    userDesc.getAuthorizedSql()+") ";
            }
            else if (userDesc.hasSiteAccess()) {
                orderTotalSql += "and site_id in ("+
                    userDesc.getAuthorizedSql()+") ";
            }
        }

        String foraccounts = (String) pParams.get("runForAccounts");
        if ( foraccounts != null && foraccounts.trim().length() > 0 ) {
            orderTotalSql += " and ord.account_id in ("+foraccounts+"  ) ";
        }


        orderTotalSql += "GROUP BY  "+
            "ord.site_id, site.short_desc , ord.account_id, ord.order_num , request_po_num, ord.currency_cd, "+
            "oi.sale_type_cd, oi.dist_item_cost,ord.total_price, ord.total_tax_cost,ord.total_misc_cost,ord.total_freight_cost, ord.original_order_date,ord.store_id "+
            ") grp "+
            "ORDER BY site_name,order_num";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(orderTotalSql);
        while(rs.next()){
            OrderTotal record = new OrderTotal();
            record.siteName = rs.getString("site_name");
            record.projectCode = rs.getString("projectCode");
            record.confirmNum = rs.getString("order_num");
            record.poNum = rs.getString("request_po_num");
            record.saleType = rs.getString("sale_type_cd");
            record.distCost = rs.getBigDecimal("dist_item_cost");
            record.orderTotal = rs.getBigDecimal("total_price");
            record.taxTotal = rs.getBigDecimal("total_tax_cost");
            record.totalMiscCost = rs.getBigDecimal("total_misc_cost");
            record.totalFreightCost= rs.getBigDecimal("total_freight_cost");
            if(record.totalMiscCost == null){
            	record.totalMiscCost = new BigDecimal(0);
            }
            if(record.totalFreightCost == null){
            	record.totalFreightCost = new BigDecimal(0);
            }
            record.orderDate = rs.getDate("original_order_date");
            storeIdsInReport.add(new Integer(rs.getInt("store_id")));

            Integer acctInt = new Integer(rs.getInt("account_id"));
            if(accountMap.containsKey(acctInt)){
            	record.account = (String) accountMap.get(acctInt);
            }else{
            	try{
	            	BusEntityData acct = BusEntityDataAccess.select(con,acctInt.intValue());
	            	accountMap.put(acctInt,acct.getShortDesc());
	            	record.account = acct.getShortDesc();
            	}catch(DataNotFoundException e){
            		accountMap.put(acctInt,"");
            		record.account = "";
            	}
            }

	        record.orderCurrencyCd = rs.getString("currency_cd");
            orderTotals.add(record);
        }
        rs.close();
        stmt.close();

        String detailSql =
        "SELECT "+
        "ord.account_id, ord.store_id, site.short_desc, ord.order_num,  ord.order_type_cd, ord.order_id, oi.cust_contract_price, "+
        " NVL(oi.service_fee,0) as service_fee, "+
        "(SELECT max(category.short_desc) FROM CLW_CONTRACT con,CLW_ITEM_ASSOC itmassoc, CLW_ITEM category WHERE  "+
	    "ord.contract_id = con.contract_id  "+
	    "AND con.catalog_id = itmassoc.catalog_id AND itmassoc.item1_id = oi.item_id AND category.item_id =itmassoc.item2_id "+
	    "AND  category.item_type_cd = 'CATEGORY') as category, "+
	    "(oi.total_quantity_ordered * oi.dist_item_cost) AS dist_item_cost,"+
        "NVL(oi.cust_item_sku_num,item_sku_num) as cust_item_sku_num, NVL(oi.cust_item_uom,item_uom) as cust_item_uom, NVL(oi.cust_item_pack,item_pack) as cust_item_pack, oi.item_size, "+
        "NVL(oi.cust_item_short_desc,item_short_desc) as cust_item_short_desc,(oi.total_quantity_ordered * oi.cust_contract_price) AS line_tot,oi.sale_type_cd,oi.total_quantity_ordered, "+
        "ord.original_order_date, oi.manu_item_sku_num, oi.manu_item_short_desc, oi.dist_item_short_desc, oi.dist_item_sku_num, oi.item_id, ord.currency_cd "+
        "FROM  "+
        "CLW_ORDER ord, CLW_BUS_ENTITY site, CLW_ORDER_ITEM oi "+
        "WHERE "+
        ReportingUtils.getValidOrdersSQL("ord") + " AND "+
        "ord.original_order_date between to_date('"+begDateS+"','"+dateFmt+"') AND to_date('"+endDateS+"','"+dateFmt+"') AND "+
        "(oi.order_item_status_cd NOT IN ('CANCELLED') OR oi.order_item_status_cd IS NULL) AND  "+
            "ord.site_id = site.bus_entity_id AND ord.order_id = oi.order_id ";

        if ( null != accountIdS && accountIdS.length() > 0) {
            detailSql += " AND ord.account_id in ("+accountIdS+") ";
        }

        if ( null != siteIdS && siteIdS.length() > 0) {
            detailSql += " AND ord.site_id in ("+siteIdS+") ";
        }

        if ( userDesc.hasAccessToAll() == false ) {
        	if (userDesc.hasStoreAccess()) {
        		detailSql += " and store_id in (" + userDesc.getAuthorizedSql() + ") ";            		  
      	  }else if (userDesc.hasAccountAccess()) {
                detailSql += "and account_id in ("+
                    userDesc.getAuthorizedSql()+") ";
            }
            else if (userDesc.hasSiteAccess()) {
                detailSql += "and site_id in ("+
                    userDesc.getAuthorizedSql()+") ";
            }
        }
        if ( foraccounts != null && foraccounts.length() > 0 ) {
            detailSql += " and ord.account_id in ("+foraccounts+"  ) ";
        }


        detailSql += " ORDER BY site.short_desc, ord.order_num";

        HashMap catTotalsMap = new HashMap();
        stmt = con.createStatement();
        rs = stmt.executeQuery(detailSql);
        int prevOrderId = -1;
        LinkedList consolidated = new LinkedList();
        while(rs.next()){
            Detail record = new Detail();
            record.storeId = rs.getInt("store_id");
            record.siteName = rs.getString("short_desc");
            record.confirmNum = rs.getString("order_num");
            record.myCategory = rs.getString("category");
            record.sku = rs.getString("cust_item_sku_num");
            record.distSku = rs.getString("dist_item_sku_num");
            record.UOM = rs.getString("cust_item_uom");
            record.pack = rs.getString("cust_item_pack");
            record.itemSize = rs.getString("item_size");
            record.name = rs.getString("cust_item_short_desc");
            record.manuItemSkuNum = rs.getString("manu_item_sku_num");
            record.manuShortDesc = rs.getString("manu_item_short_desc");
            record.distShortDesc = rs.getString("dist_item_short_desc");
            record.distCost = rs.getBigDecimal("dist_item_cost");
            record.lineTotal = rs.getBigDecimal("line_tot");
            record.saleType = rs.getString("sale_type_cd");
            record.qty = rs.getInt("total_quantity_ordered");
            record.orderDate = rs.getDate("original_order_date");
            int orderId = rs.getInt("order_id");
            record.orderId = orderId;
            record.itemPrice = rs.getBigDecimal("cust_contract_price");
            record.itemId = rs.getInt("item_id");

            /*
             * Subtracting service fee -xpedx
             */
            BigDecimal sfee = rs.getBigDecimal("service_fee");
            if(sfee!=null && sfee.compareTo(new BigDecimal(0))>0){
            	BigDecimal totSFee = sfee.multiply(new BigDecimal(record.qty));
            	record.lineTotal = (record.lineTotal).subtract(totSFee);
            }
                
            Integer acctInt = new Integer(rs.getInt("account_id"));
            if(accountMap.containsKey(acctInt)){
            	record.account = (String) accountMap.get(acctInt);
            }else{
            	try{
		    BusEntityData acct = BusEntityDataAccess.select(con,acctInt.intValue());
		    accountMap.put(acctInt,acct.getShortDesc());
		    record.account = acct.getShortDesc();
            	}catch(DataNotFoundException e){
		    accountMap.put(acctInt,"");
		    record.account = "";
            	}
            }
	    record.orderCurrencyCd = rs.getString("currency_cd");

            details.add(record);
            String orderTypeCd = rs.getString("order_type_cd");
            boolean cosolidatedFl =
                  RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(orderTypeCd);
            if(cosolidatedFl) {
              consolidated.add(record);
            }

            //now group the category totals
            String key = record.account + "::" +record.siteName + "::" + record.confirmNum + "::" + record.getCategory() + "::" + record.orderCurrencyCd;

	        if(catTotalsMap.containsKey(key)){
                ArrayList currDetail = (ArrayList) catTotalsMap.get(key);
                currDetail.add(record);
            }else{
                ArrayList newDetail = new ArrayList();
                newDetail.add(record);
                catTotalsMap.put(key, newDetail);
            }
        }
        rs.close();
        stmt.close();


        //now get the category totals from the map
        Collection groupedDetail = catTotalsMap.values();
        Iterator it = groupedDetail.iterator();
        while(it.hasNext()){
            ArrayList grouping = (ArrayList) it.next();
            Iterator gIt = grouping.iterator();
            BigDecimal grpTotal = null;
            Detail det = null;
            while(gIt.hasNext()){
                det = (Detail) gIt.next();
                if(grpTotal == null){
                    grpTotal = det.lineTotal;
                }else{
                    grpTotal = grpTotal.add(det.lineTotal);
                }
            }
            if(det != null){
                CategoryTotal record = new CategoryTotal();
                record.account = det.account;
                record.category = det.getCategory();
                record.confirmNum = det.confirmNum;
                record.siteName = det.siteName;
                record.categoryTotal = grpTotal;
                record.distCost = det.distCost;
                record.orderDate = det.orderDate;
		record.orderCurrencyCd = det.orderCurrencyCd;
		        catTotals.add(record);
            }
        }
        Collections.sort(catTotals,CATEGORY_TOTAL_COMPARE);

	//    	Create consolidate order page
        prevOrderId = -1;

        LinkedList consolidatedResult = new LinkedList();
        if(consolidated.size()>0) {
          int counter = 0;
          String orderIdS = null;
          LinkedList wrkOrders = new LinkedList();
          for(Iterator iter=consolidated.iterator(); iter.hasNext();) {
            Detail wrkOrder = (Detail) iter.next();
            int orderId = wrkOrder.orderId;
            if(prevOrderId!=orderId) {
              if(counter>=999) {
                LinkedList ll = getConsolidatedOrders(con,orderIdS,wrkOrders);
                consolidatedResult.addAll(ll);
                counter = 0;
                orderIdS = null;
              }
              prevOrderId = orderId;
              counter++;
              if(counter==1) {
                orderIdS = "" + wrkOrder.orderId;
              } else {
                 orderIdS += "," + wrkOrder.orderId;
              }
            }
            wrkOrders.add(wrkOrder);
          }
          if(orderIdS!=null) {
             LinkedList ll = getConsolidatedOrders(con,orderIdS,wrkOrders);
             consolidatedResult.addAll(ll);
          }

        }

        //find if any of the stores represented in this report have the sales tax
        //feature turned on.
        it = storeIdsInReport.iterator();
        mDisplaySalesTaxInfo = false;
        mDisplayDistInfo = false;
        while(it.hasNext()){
	    int storeId = ((Integer) it.next()).intValue();
	    if(storeId > 0){
		StoreInfo si = getStoreInfo(storeId);
		if(RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(si.getStoreType())){
		    mDisplayDistInfo = true;
		}
		if(si.isTaxable()){
		    mDisplaySalesTaxInfo = true;
		}
	    }
        }

        if(accountMap.keySet().size() > 1){
	    multiAccounts = true;
        }else{
	    multiAccounts = false;
        }

        generateResults(resultV, con, consolidatedResult,details,
			orderTotals,catTotals);
        return resultV;
    }



    private StoreInfo getStoreInfo(int storeId) throws Exception{
    	if(storeId == 0){
    		return null;
    	}
    	PropertyUtil pru = new PropertyUtil(con);
    	StoreInfo si = (StoreInfo) storeMap.get(new Integer(storeId));
    	if(si == null){
    		boolean taxable = Utility.isTrue(pru.fetchValueIgnoreMissing(0,storeId,RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR));
    		String storeTypeCd = pru.fetchValueIgnoreMissing(0,storeId,RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE);
    		int storeCatalogId = BusEntityDAO.getStoreCatalogId(con,storeId);
    		si = new StoreInfo(storeId, storeTypeCd,storeCatalogId,taxable);
    		storeMap.put(new Integer(storeId), si);
    	}
    	return si;
    }



    protected void generateResults
	(GenericReportResultViewVector resultV, Connection con,
	 List consolidatedResult, List details, List orderTotals,
	 List catTotals) throws Exception{

        if(consolidatedResult.size() > 0){
	    processList(consolidatedResult, resultV, "Consolidated_Details",getDetailConsolidatedReportHeader(), false);
        }

        processList(details, resultV, "Details",getDetailReportHeader(), true);

	processList(orderTotals, resultV, "Order_Totals",getOrderTotalsReportHeader(), false);
        processList(catTotals, resultV, "Category_Totals",getCategoryTotalsReportHeader(), false);
    }

    protected void processList
	(List toProcess,
	 GenericReportResultViewVector resultV,
	 String name, GenericReportColumnViewVector header,
	 boolean alwaysCreate)
    {

        Iterator it = toProcess.iterator();
        if(alwaysCreate || it.hasNext()) {
            GenericReportResultView result =
		GenericReportResultView.createValue();
            result.setTable(new ArrayList());
            while(it.hasNext()) {
                result.getTable().add(((aRecord) it.next()).toList());
            }
            result.setColumnCount(header.size());
            result.setHeader(header);
            result.setName(name);
            resultV.add(result);
        }
    }

    private LinkedList getConsolidatedOrders(Connection pCon, String pOrderIdS, LinkedList pConsolidatedOrders)
    throws Exception {

      LinkedList replacedOrderLL = new LinkedList();
      String sql =
       " SELECT oa.order2_id, o.order_id, o.original_order_date, o.order_num, o.order_site_name, "+
       " NVL(oi.cust_item_sku_num,item_sku_num) as cust_item_sku_num, "+
       "oi.dist_item_cost,"+
       " oi.total_quantity_ordered, oi.order_item_status_cd, o.currency_cd "+
       " FROM clw_order o, clw_order_item oi, clw_order_assoc oa "+
       " WHERE order2_id in ("+pOrderIdS+")"+
       " AND order1_id = o.order_id "+
       " AND order_assoc_cd = '"+RefCodeNames.ORDER_ASSOC_CD.CONSOLIDATED+"' "+
       " AND order_assoc_status_cd = '"+RefCodeNames.ORDER_ASSOC_STATUS_CD.ACTIVE+"' "+
       " AND o.order_id = oi.order_id "+
       " AND nvl(oi.order_item_status_cd,' ') != '"+RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED+"' "+
       " AND oi.total_quantity_ordered > 0 "+
       " ORDER BY oa.order2_id ";

       Statement stmt = pCon.createStatement();
       ResultSet rs = stmt.executeQuery(sql);
       while(rs.next()){
         ConsolidatedDetail consDet = new ConsolidatedDetail();
         replacedOrderLL.add(consDet);
         consDet.orderId = rs.getInt("order2_id");
         consDet.replOrderId = rs.getInt("order2_id");
         consDet.replSiteName = rs.getString("order_site_name");
         consDet.replOrderNum = rs.getString("order_num");
         consDet.sku = rs.getString("cust_item_sku_num");
         consDet.replQty = rs.getInt("total_quantity_ordered");
         consDet.replOrderDate = rs.getDate("original_order_date");
         consDet.orderCurrencyCd = rs.getString("currency_cd");
         consDet.distCost = rs.getBigDecimal("dist_item_cost");
       }
       rs.close();
       stmt.close();

       Object[] replacedOrderA = replacedOrderLL.toArray();
       LinkedList consolidatedOrders = new LinkedList();
       int prevOrderId = -1;
       int replOrderInd = 0;
       for(Iterator iter=pConsolidatedOrders.iterator(); iter.hasNext();){
          Detail det = (Detail) iter.next();
          String sku = det.sku;
          int orderId = det.orderId;
          if(prevOrderId!=orderId) {
            prevOrderId = orderId;
            replOrderInd = getIndex(replacedOrderA,orderId,0,replacedOrderA.length);
          }
          ConsolidatedDetail consolidatedOrder = null;
          if(replOrderInd>=0) {
            for(int ii=replOrderInd; ii<replacedOrderA.length; ii++) {
              ConsolidatedDetail cd = (ConsolidatedDetail) replacedOrderA[ii];
              if(orderId!=cd.orderId) {
                break;
              }
              if(sku.equals(cd.sku )) {
                consolidatedOrder = cd;
                consolidatedOrders.add(consolidatedOrder);
                consolidatedOrder.account = det.account;
                consolidatedOrder.siteName = det.siteName;
                consolidatedOrder.confirmNum = det.confirmNum;
                consolidatedOrder.myCategory = det.getCategory();
                consolidatedOrder.UOM = det.UOM;
                consolidatedOrder.pack = det.pack;
                consolidatedOrder.itemSize = det.itemSize;
                consolidatedOrder.name = det.name;
                consolidatedOrder.saleType = det.saleType;
                consolidatedOrder.qty = det.qty;
                consolidatedOrder.distCost = det.distCost;
                consolidatedOrder.lineTotal = det.lineTotal;
                consolidatedOrder.orderDate = det.orderDate;
                consolidatedOrder.itemPrice = det.itemPrice;
                consolidatedOrder.orderId = det.orderId;
                consolidatedOrder.storeId = det.storeId;
                consolidatedOrder.distShortDesc = det.distShortDesc;
                consolidatedOrder.distSku = det.distSku;
                consolidatedOrder.manuShortDesc = det.manuShortDesc;
                consolidatedOrder.manuItemSkuNum = det.manuItemSkuNum;
		consolidatedOrder.orderCurrencyCd =  det.orderCurrencyCd;
              }
            }
          }
          if(consolidatedOrder==null) {
            consolidatedOrder = new ConsolidatedDetail();
            consolidatedOrders.add(consolidatedOrder);
            consolidatedOrder.account = det.account;
            consolidatedOrder.siteName = det.siteName;
            consolidatedOrder.confirmNum = det.confirmNum;
            consolidatedOrder.myCategory = det.getCategory();
            consolidatedOrder.UOM = det.UOM;
            consolidatedOrder.pack = det.pack;
            consolidatedOrder.itemSize = det.itemSize;
            consolidatedOrder.name = det.name;
            consolidatedOrder.saleType = det.saleType;
            consolidatedOrder.qty = det.qty;
            consolidatedOrder.distCost = det.distCost;
            consolidatedOrder.lineTotal = det.lineTotal;
            consolidatedOrder.orderDate = det.orderDate;
            consolidatedOrder.itemPrice = det.itemPrice;
            consolidatedOrder.orderId = det.orderId;
            consolidatedOrder.storeId = det.storeId;
            consolidatedOrder.distShortDesc = det.distShortDesc;
            consolidatedOrder.distSku = det.distSku;
            consolidatedOrder.manuShortDesc = det.manuShortDesc;
            consolidatedOrder.manuItemSkuNum = det.manuItemSkuNum;
	    consolidatedOrder.orderCurrencyCd =  det.orderCurrencyCd;
          }
       }
       return consolidatedOrders;

    }

    int getIndex(Object[] pReplacedOrderA, int pOrderId, int pBegInd, int pEndInd)
    {
       int ind = (pEndInd + pBegInd)/2;
       ConsolidatedDetail cd = (ConsolidatedDetail) pReplacedOrderA[ind];
       if(cd.orderId==pOrderId) {
         for(; ind>=0;ind--) {
           cd = (ConsolidatedDetail) pReplacedOrderA[ind];
           if(cd.orderId!=pOrderId){
              return (ind+1);
           }
         }
         return 0;
       }
       if(pBegInd==pEndInd) {
         return -1;
       }
       if(cd.orderId>pOrderId) {
         return getIndex(pReplacedOrderA, pOrderId, pBegInd,ind-1);
       }
       return getIndex(pReplacedOrderA, pOrderId, ind+1, pEndInd);
    }

    private GenericReportColumnViewVector getCategoryTotalsReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        if(multiAccounts){
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account Name",0,255,"VARCHAR2"));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site_Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Confirm_Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Order_Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category",0,255,"VARCHAR2"));
        if(showPrice){header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Category_Cost_Total$",2,20,"NUMBER","*",true));}
        if(showPrice){header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Category_Price_Total$",2,20,"NUMBER","*",true));}
        if(showPrice){header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","rowInfo_Currency Code",0,255,"VARCHAR2"));}
        return header;
    }

    private GenericReportColumnViewVector getOrderTotalsReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        if(multiAccounts){
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account_Name",0,255,"VARCHAR2"));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site_Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Project_Code",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Confirm_Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Order_Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","PO_Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Sale_Type",0,255,"VARCHAR2"));
        if(showPrice){header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Order_Cost_Sub_Total$",2,20,"NUMBER","*",true));}
        if(showPrice){header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Order_Price_Sub_Total$",2,20,"NUMBER","*",true));}
        if(mDisplaySalesTaxInfo){
        	if(showPrice){header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Tax$",2,20,"NUMBER","*",true));}
        }
    	if(showPrice){header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Freight$",2,20,"NUMBER","*",true));}
    	if(showPrice){header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Handling$",2,20,"NUMBER","*",true));}
    	if(showPrice){header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","rowInfo_Currency Code",0,255,"VARCHAR2"));}

        return header;
    }

    private GenericReportColumnViewVector getDetailReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        if(multiAccounts){
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account_Name",0,255,"VARCHAR2"));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site_Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Confirm_Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Order_Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category",0,255,"VARCHAR2"));
        if(mDisplayDistInfo){
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Sys_Sku",0,255,"VARCHAR2"));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Sku",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","UOM",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Pack",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item_Size",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Man_Sku",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Manufacturer",0,255,"VARCHAR2"));
        if(mDisplayDistInfo){
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Distributor",0,255,"VARCHAR2"));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Sale_Type",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Qty",0,20,"NUMBER"));
        if(mDisplayDistInfo){
        	if(showPrice){header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Line_Cost_Total$",2,20,"NUMBER","*",true));}
        }
        if(showPrice){header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Line_Price_Total$",2,20,"NUMBER","*",true));}
        if(showPrice){header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","rowInfo_Currency Code",0,255,"VARCHAR2"));}
        return header;
    }

    private interface aRecord{
        ArrayList toList();
    }

    private GenericReportColumnViewVector getDetailConsolidatedReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        if(multiAccounts){
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account_Name",0,255,"VARCHAR2"));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site_Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Confirm_Num",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Order_Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category",0,255,"VARCHAR2"));
        if(mDisplayDistInfo){
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Sys_Sku",0,255,"VARCHAR2"));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Sku",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","UOM",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Pack",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item_Size",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Man_Sku",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Manufacturer",0,255,"VARCHAR2"));
        if(mDisplayDistInfo){
        	header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Distributor",0,255,"VARCHAR2"));
        }
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Sale_Type",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Sum_Qty",0,20,"NUMBER"));
        if(showPrice){header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Sum_Line_Total$",2,20,"NUMBER","*",false));}
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Original_Site_Name",0,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Original_Confirm_Num",0,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Original_Order_Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Original_Qty",0,20,"NUMBER"));
        if(showPrice){header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Original_Line_Total$",2,20,"NUMBER","*",true));}
        if(showPrice){header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","rowInfo_Currency Code",0,255,"VARCHAR2"));}
        return header;
    }

    protected class CategoryTotal implements aRecord{
        String siteName;
        String confirmNum;
        String category;
        String account;
        String orderCurrencyCd;
        BigDecimal distCost;
        BigDecimal categoryTotal;
        Date orderDate;

        public ArrayList toList(){
            ArrayList list = new ArrayList();
            if(multiAccounts){
            	list.add(account);
            }
            list.add(siteName);
            list.add(confirmNum);
            list.add(orderDate);
            list.add(category);
            if(showPrice){list.add(distCost);}
            if(showPrice){list.add(categoryTotal);}
            // Add row formating information
            if(showPrice){list.add("rowInfo_currencyCd="+orderCurrencyCd);}
            return list;
        }
    }

    protected class OrderTotal implements aRecord{
        String siteName;
        String projectCode;
        String confirmNum;
        String poNum;
        String saleType;
        BigDecimal distCost;
        BigDecimal orderTotal;
        BigDecimal taxTotal;
        BigDecimal totalFreightCost;
        BigDecimal totalMiscCost;
        Date orderDate;
        String account;
        String orderCurrencyCd;

        public ArrayList toList(){
            ArrayList list = new ArrayList();
            if(multiAccounts){
            	list.add(account);
            }
            list.add(siteName);
            list.add(projectCode);
            list.add(confirmNum);
            list.add(orderDate);
            list.add(poNum);
            list.add(saleType);
            if(showPrice){list.add(distCost);}
            if(showPrice){list.add(orderTotal);}
            if(mDisplaySalesTaxInfo){
            	if(showPrice){list.add(taxTotal);}
            }
            if(showPrice){list.add(totalFreightCost);}
            if(showPrice){list.add(totalMiscCost);}
            // Add row formating information
            if(showPrice){list.add("rowInfo_currencyCd="+orderCurrencyCd);}
            return list;
        }
    }

    protected class Detail implements aRecord{
    	int storeId = 0;
    	int itemId = 0;
        String siteName;
        String confirmNum;
        String myCategory;
        String sku;
        String distSku;
        String UOM;
        String pack;
        String itemSize;
        String name;
        String saleType;
        int qty;
        BigDecimal distCost;
        BigDecimal lineTotal;
        Date orderDate;
        BigDecimal itemPrice;
        int orderId;
        String manuItemSkuNum;
        String manuShortDesc;
        String distShortDesc;
        String account;
	String orderCurrencyCd;

        protected String getCategory(){

	    if(myCategory == null){
		if(storeId > 0 && itemId > 0){
		    StoreInfo si = null;
		    try{
			si = getStoreInfo(storeId);
		    }catch(Exception e){
			e.printStackTrace();
		    }
		    if(si != null && si.getStoreCatalogId() != 0){
			Object key = storeId + "::" + itemId;
			myCategory = (String) masterItemCategoryMap.get(key);
			if(myCategory == null){
			    DBCriteria crit = new DBCriteria();
			    String ia = ItemAssocDataAccess.CLW_ITEM_ASSOC;
			    String category = ItemDataAccess.CLW_ITEM;

			    crit.addJoinCondition(ia,ItemAssocDataAccess.ITEM2_ID,category,ItemDataAccess.ITEM_ID);
			    crit.addJoinTableEqualTo(ia,ItemAssocDataAccess.ITEM1_ID,itemId);
			    crit.addJoinTableEqualTo(ia,ItemAssocDataAccess.CATALOG_ID,si.getStoreCatalogId());
			    crit.addDataAccessForJoin(new ItemDataAccess());
			    try{
				List categoryVec = JoinDataAccess.select(con,crit);
				if(categoryVec != null && !categoryVec.isEmpty()){
				    myCategory = ((ItemData) ((List)categoryVec.get(0)).get(0)).getShortDesc();
				}
			    }catch(SQLException e){
				e.printStackTrace();

			    }

			    if(myCategory == null){
				myCategory = "Other";
			    }
			    masterItemCategoryMap.put(key,myCategory);
			}
		    }
		}
	    }
	    if(myCategory == null){
		myCategory = "Other";
	    }
	    return myCategory;
        }

        /**
         * Figures out wheather to display the dist info for this line
         */
        private boolean displayDistInfo(){
	    try{
		StoreInfo si = getStoreInfo(storeId);
		if(RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(si.getStoreType())){
		    return true;
            	}else{
		    return false;
            	}
	    }catch(Exception e){
		e.printStackTrace();
		return false;
	    }

        }

        public ArrayList toList(){
            ArrayList list = new ArrayList();
            if(multiAccounts){
            	list.add(account);
            }
            list.add(siteName);
            list.add(confirmNum);
            list.add(orderDate);
            list.add(getCategory());
            list.add(sku);
            if(mDisplayDistInfo){
            	if(!displayDistInfo() || distSku == null){
            		distSku = "";
            	}
            	list.add(distSku);
            }
            list.add(UOM);
            list.add(pack);
            list.add(itemSize);
            if(manuItemSkuNum == null){
            	list.add("");
            }else{
            	list.add(manuItemSkuNum);
            }
            if(manuShortDesc == null){
            	list.add("");
            }else{
            	list.add(manuShortDesc);
            }
            if(mDisplayDistInfo){
            	if(!displayDistInfo() || distSku == null){
            		distShortDesc = "";
            	}
            	list.add(distShortDesc);
            }
            list.add(name);
            list.add(saleType);
            list.add(new Integer(qty));
            if(showPrice){list.add(distCost);}
            if(showPrice){list.add(lineTotal);}
            // Add row formating information
            list.add("rowInfo_currencyCd="+orderCurrencyCd);
            return list;
        }
    }

    /**
     * cached instance of store data.  Lightweight version of the @see StoreData object
     */
    private class StoreInfo{
    	private String storeTypeCd;
    	private int storeId;
    	private int storeCatalogId;
    	private boolean taxable;


    	private int getStoreId(){
    		return storeId;
    	}
    	private String getStoreType(){
    		return storeTypeCd;
    	}
    	private int getStoreCatalogId(){
    		return storeCatalogId;
    	}
    	private boolean isTaxable(){
    		return taxable;
    	}
    	private StoreInfo(int pStoreId,String pStoreTypeCd, int pStoreCatalogId, boolean pTaxable){
    		storeId = pStoreId;
    		storeTypeCd = pStoreTypeCd;
    		storeCatalogId = pStoreCatalogId;
    		taxable = pTaxable;
    	}

    	public String toString(){
    		return "storeId = ["+storeId+"]::storeCatalogId = ["+storeCatalogId+"]::storeTypeCd = ["+storeTypeCd+"]";
    	}
    }

    protected class ConsolidatedDetail extends Detail implements aRecord
    {
        int replOrderId;
        String replSiteName;
        String replOrderNum;
        String orderCurrencyCd;
        int replQty;
        Date replOrderDate;

        public ArrayList toList(){
            ArrayList list = super.toList();
            list.add(replSiteName);
            list.add(replOrderNum);
            list.add(replOrderDate);
            list.add(new Integer(replQty));
            BigDecimal replTotalPrice =
                        (itemPrice==null)?
                        new BigDecimal(0):
                        itemPrice.multiply(new BigDecimal(replQty));
            if(showPrice){list.add(replTotalPrice);}
            // Add row formating information
            list.add("rowInfo_currencyCd="+orderCurrencyCd);
            return list;
        }
    }

    static final Comparator CATEGORY_TOTAL_COMPARE = new Comparator() {
	    public int compare(Object o1, Object o2)
	    {
		CategoryTotal c1 = (CategoryTotal)o1;
		CategoryTotal c2 = (CategoryTotal)o2;
                if(c1.siteName == null){
                    c1.siteName = "";
                }
                if(c2.siteName == null){
                    c2.siteName = "";
                }
                if(c1.confirmNum == null){
                    c1.confirmNum = "";
                }
                if(c2.confirmNum == null){
                    c2.confirmNum = "";
                }
                if(c1.category == null){
                    c1.category = "";
                }
                if(c2.category == null){
                    c2.category = "";
                }
                int val = c1.siteName.compareToIgnoreCase(c2.siteName);
                if(val != 0){
                    return val;
                }
                val = c1.confirmNum.compareToIgnoreCase(c2.confirmNum);
                if(val != 0){
                    return val;
                }
                val = c1.category.compareToIgnoreCase(c2.category);
                return val;
	    }
	};

}
