/*
 * ISSUKBackorderReport.java
 *
 * Created on May 3, 2011, 2:23 PM
 */

package com.cleanwise.service.api.reporting;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ReportShowProperty;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;

/**
 * Class that is used to crate a custom back order report in excel format.
 * 
 * @author Srinivas
 */
public class ISSUKBackorderReport implements GenericReportMulti, ReportShowProperty {
	
    private static final String PATTERN = "MM/dd/yyyy HH:mm";
    private final static TimeZone DEFAULT_TIMEZONE = TimeZone.getDefault();

    private static final BigDecimal ZERO = new BigDecimal(0); 

    
    protected HashMap<String,String> masterItemCategoryMap = new HashMap<String,String>();
    Connection con;
    
    private static String className="ISSUKBackorderReport";

    public com.cleanwise.service.api.value.GenericReportResultViewVector process(com.cleanwise.service.api.util.ConnectionContainer pCons,
    		com.cleanwise.service.api.value.GenericReportData pReportData, java.util.Map pParams)
            throws Exception {
        TimeZone tzSource = DEFAULT_TIMEZONE;
        APIAccess factory = new APIAccess();

        con = pCons.getDefaultConnection();
        GenericReportResultViewVector resultV =
                new GenericReportResultViewVector();
        
        String begDateS = (String) pParams.get("BEG_DATE");
        String endDateS = (String) pParams.get("END_DATE_OPT");
        String dateFmt = (String) pParams.get("DATE_FMT");
        String storeIdS = (String) pParams.get("STORE");
        String storeName = "";

        if (null == dateFmt) {
            dateFmt = "MM/dd/yyyy";
        }

        if (!ReportingUtils.isValidDate(begDateS, dateFmt)) {
            String mess = "^clw^\"" + begDateS + "\" is not a valid date of the form: " + dateFmt + "^clw^";
            throw new Exception(mess);
        }
        if(endDateS==null || endDateS.trim().equals("")) {
        	SimpleDateFormat sdf = new SimpleDateFormat(dateFmt);
        	endDateS = sdf.format(new Date());
        	Date begDateD = sdf.parse(begDateS);
            Date endDateD = sdf.parse(endDateS);
            if (endDateD != null && begDateD != null && endDateD.compareTo(begDateD) < 0) {
                String mess = "^clw^ Begin date: \"" + begDateS
                        + "\" should be less than or equals to today's date"  
                        + "\"^clw^";
                throw new Exception(mess);
            }
        	
        }
        if (!ReportingUtils.isValidDate(endDateS, dateFmt)) {
            String mess = "^clw^\"" + endDateS + "\" is not a valid date of the form: " + dateFmt + "^clw^";
            throw new Exception(mess);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFmt);
        Date begDateD = sdf.parse(begDateS);
        Date endDateD = sdf.parse(endDateS);
        if (endDateD != null && begDateD != null && endDateD.compareTo(begDateD) < 0) {
            String mess = "^clw^End date: \"" + endDateS
                    + "\" cannot be before start date: \"" + begDateS
                    + "\"^clw^";
            throw new Exception(mess);
        }
        String accountIdS = "0";
        accountIdS = (String) pParams.get("ACCOUNT_MULTI_OPT");

        int storeId = 0;
        try {
        	storeId = Integer.parseInt(storeIdS);
        } catch (NumberFormatException e) {
            String mess = "^clw^Store identifier is not a valid value^clw^";
            throw new Exception(mess);
        }
        
        ResultSet rs = null;
        if(storeId!=0){

            String storeSql = "SELECT SHORT_DESC FROM CLW_BUS_ENTITY WHERE "+
            					"BUS_ENTITY_ID = "+storeId;
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(storeSql);
            while(rs.next()) {
            	storeName = rs.getString("short_desc");
            }
            if(storeName==null) {
                String mess = "^clw^No Store Found^clw^";
                throw new Exception(mess);
            }
        }
        
        String accountIdListS = "";
        String accountNames = "ALL";
        List<Integer> acctIds = new ArrayList<Integer>();
        
        if(accountIdS==null || accountIdS.trim().length()==0){
        	
        	String accoundIdsSQL = "SELECT BE.BUS_ENTITY_ID AS ACCOUNT_ID FROM CLW_BUS_ENTITY BE " +
        	"WHERE BE.BUS_ENTITY_STATUS_CD='"+RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE+"' AND BE.BUS_ENTITY_ID IN " +
        	"(SELECT BEA.BUS_ENTITY1_ID AS ACCOUNT_ID FROM CLW_BUS_ENTITY_ASSOC BEA WHERE BEA.BUS_ENTITY2_ID="+storeId+")";
        	
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(accoundIdsSQL);
			
			while(rs.next()) {
				acctIds.add(rs.getInt("ACCOUNT_ID"));
			}
			
			if(acctIds.size()==0) {
				 String mess = "^clw^No Active Account Found^clw^";
	            throw new Exception(mess);
			}
	        
			accountIdS = getAccountIds(acctIds);
			
        }  else {
        	 StringTokenizer token = new StringTokenizer(accountIdS, ",");
             while (token.hasMoreTokens()) {
                 String acctS = token.nextToken();
                 try {
                     int acctId = Integer.parseInt(acctS.trim());
                     acctIds.add(acctId);
                 } catch (Exception exc) {
                     String mess = "^clw^" + acctS + " is not a valid account identifier ^clw^";
                     throw new Exception(mess);
                 }
             }
             
             if(acctIds.size()==0) {
       			 String mess = "^clw^No Active Account Found^clw^";
                   throw new Exception(mess);
       		}
             
           	accountIdS = getAccountIds(acctIds);
           	
           	StringBuilder builder = new StringBuilder();
            
         	DBCriteria criteria = new DBCriteria();
         	criteria.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,accountIdS);
         	BusEntityDataVector accountItemVector = BusEntityDataAccess.select(con,criteria);
         	BusEntityData busEntityData;
         	
         	//Get Account Names
         	for(int i=0;i<accountItemVector.size();i++){
         		busEntityData = (BusEntityData)accountItemVector.get(i);
         		if(busEntityData != null ){
         			//just use first account for timezone offset
             		if(i == 0){
            	        if (busEntityData.getTimeZoneCd() != null) {
            	            TimeZone tzBuffer = TimeZone.getTimeZone(busEntityData.getTimeZoneCd());
            	            if (tzBuffer != null && tzBuffer.getID().equals(busEntityData.getTimeZoneCd())) {
            	                tzSource = tzBuffer;
            	            }
            	        }
             		}
             		builder.append(busEntityData.getShortDesc());
              		if(i!=(accountItemVector.size()-1)){
              			builder.append(",");
              		}
              		builder.append(" ");
         		}
         	}
         	accountNames = builder.toString();
        }
        
        if(accountIdS==null || accountIdS.length()==0){
        	accountIdS = "0";
        }
        accountIdListS =  " AND O.ACCOUNT_ID IN ("+accountIdS+") ";
       
        String backOrderDetailsSQL = " SELECT \n"+
        				" O.ORDER_NUM AS Confirm_Num, \n"+
				        " O.REQUEST_PO_NUM AS PO_Num,\n"+
				        " NVL(O.REVISED_ORDER_DATE, O.ORIGINAL_ORDER_DATE) AS Order_Date, \n"+                                                                                                                                                                                                          
				        " NVL(O.REVISED_ORDER_TIME, O.ORIGINAL_ORDER_TIME) AS Order_Time, \n"+
				        " O.CURRENCY_CD AS Currency_Code, \n"+ 
				        " O.ORIGINAL_AMOUNT AS Order_Sub_Total, \n"+
				        " O.TOTAL_FREIGHT_COST AS Freight, \n"+
				        " O.TOTAL_MISC_COST AS Handling, \n" +
				        " O.ACCOUNT_ID As AccountId, \n" +
				         
				        " OI.OUTBOUND_PO_NUM AS Outbound_PO_Num, \n"+
				        " OI.SALE_TYPE_CD AS Sale_Type, \n"+
				        " OI.DIST_ITEM_SKU_NUM AS Dist_Sku, \n"+
				        " NVL(OI.CUST_ITEM_UOM,OI.ITEM_UOM) AS UOM, \n"+
				        " NVL(OI.CUST_ITEM_PACK,OI.ITEM_PACK) AS Pack, \n"+
				        " OI.ITEM_SIZE AS Item_Size, \n"+
				        " OI.MANU_ITEM_SKU_NUM AS Man_Sku, \n"+
				        
				        " OI.TOTAL_QUANTITY_ORDERED AS Order_Qty, \n"+
				        " OIA.QUANTITY as Ship_Qty, \n"+
				        " (OI.TOTAL_QUANTITY_ORDERED * OI.CUST_CONTRACT_PRICE) AS Line_Total, \n"+
				        " OI.ORDER_ITEM_ID, \n"+
				        " OI.ORDER_LINE_NUM, \n"+
				        
				        " I.SKU_NUM AS Store_Sku, \n"+
				        " I.SHORT_DESC AS Name, \n"+
				        
				        " OIA.COMMENTS AS Del_Ref_Num, \n"+
				        " OIA.ACTION_DATE AS Del_Ref_Num_Date, \n"+
				        " OIA.ACTION_TIME AS Del_Ref_Num_Time, \n"+
				        " (SELECT BE.SHORT_DESC FROM CLW_BUS_ENTITY BE WHERE BE.BUS_ENTITY_ID=O.SITE_ID)AS Site_Name, \n"+
				        " (SELECT BE.SHORT_DESC FROM CLW_BUS_ENTITY BE WHERE BE.BUS_ENTITY_ID =  \n"+
				        " (SELECT BEA.BUS_ENTITY2_ID FROM CLW_BUS_ENTITY_ASSOC BEA WHERE BEA.BUS_ENTITY1_ID=O.SITE_ID)) AS Account_Name, \n"+
				        " (SELECT CLW_VALUE FROM CLW_PROPERTY P WHERE P.SHORT_DESC='"+RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER+
				        "' AND BUS_ENTITY_ID=O.SITE_ID) AS Site_Budget_Ref, \n"+
				       " OI.DIST_ITEM_SHORT_DESC AS Distributor,  \n"+
				       " MANU_ITEM_SHORT_DESC AS Manufacturer,  \n"+
				          "(SELECT UPPER(OIM.CLW_VALUE) FROM CLW_ORDER_ITEM_META OIM WHERE OIM.NAME='"+
				          RefCodeNames.ORDER_ITEM_META_NAME.STANDARD_PRODUCT_LIST +"' AND "+
				          " OIM.ORDER_ITEM_ID = OI.ORDER_ITEM_ID) AS spl, \n" +
				          " OI.ITEM_ID AS Item_Id, \n"+
				          " (SELECT OAOC.AMOUNT FROM CLW_ORDER_ADD_ON_CHARGE OAOC WHERE OAOC.ORDER_ID=O.ORDER_ID)  AS Discount \n"+
        
				   " FROM \n"+
				   		" CLW_ORDER O \n" +
				   		 " JOIN CLW_ORDER_ITEM OI ON (OI.ORDER_ID = O.ORDER_ID) \n" +
				   		 " JOIN CLW_ITEM I ON (OI.ITEM_ID = I.ITEM_ID) \n"+
				   		 " LEFT OUTER JOIN (Select * from CLW_ORDER_ITEM_ACTION where ACTION_CD = '"+RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DELIVERY_REF_NUMBER+"') OIA \n" +
				   		 		"	ON (OI.ORDER_ID = OIA.ORDER_ID and OI.ORDER_ITEM_ID = OIA.ORDER_ITEM_ID) \n"+//note still need to sum up multiple shipments, this is just a first cut at qty
				   " WHERE \n"+
				          " O.ORDER_STATUS_CD = '"+RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED+"' AND \n"+
				          " OI.ORDER_ITEM_STATUS_CD  IN ('"+RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR+"','"+RefCodeNames.ORDER_ITEM_STATUS_CD.PO_ACK_SUCCESS+"','"+RefCodeNames.ORDER_ITEM_STATUS_CD.PO_ACK_ERROR+RefCodeNames.ORDER_ITEM_STATUS_CD.PO_ACK_REJECT+"') AND \n"+
				          " NVL(O.REVISED_ORDER_DATE, O.ORIGINAL_ORDER_DATE) between  TO_DATE('"+begDateS+"','MM/DD/YYYY') AND TO_DATE('"+endDateS+"','MM/DD/YYYY') AND \n"+
				          " OI.TOTAL_QUANTITY_ORDERED > NVL(OIA.QUANTITY,0) \n"
				          +accountIdListS+"\n"+
				          " AND O.STORE_ID= "+storeIdS;
        

        PreparedStatement stmt = null;

        //List backOrderDetails = new ArrayList();
        Map<Integer,List<BackOrderDetail>> backOrderDetailsMap  = new HashMap<Integer,List<BackOrderDetail>>();
        List<BackOrderSummary> backOrderSummary = new ArrayList<BackOrderSummary>();
        
        try {
	        stmt = con.prepareStatement(backOrderDetailsSQL);
	        rs = stmt.executeQuery();
	        
	        BackOrderDetail detail;
	        BackOrderSummary summary; 
	        Date _orderDate;
	        String accountName;
	        String siteName;
	        String siteBudgetRefNum;
	        String confirmationNum;
	        String poNum;
	        String currencyCode;
	        
	        BigDecimal defaultValue = new BigDecimal(0);
	        BigDecimal freight,handling,discount,subTotal,orderQty,shipQty,lineTotal;
	        Iterator it;
	        boolean isNewRecord;
	        BackOrderSummary existingRecord;
	        while (rs.next()) {
	        	summary = new BackOrderSummary();
	        	accountName =  rs.getString("Account_Name");
	        	summary.accountName = accountName;
	        	
	        	siteName = rs.getString("Site_Name");
	        	siteBudgetRefNum = rs.getString("Site_Budget_Ref");
	        	confirmationNum = rs.getString("Confirm_Num");
	        	summary.siteName = siteName;
	        	summary.siteBudgetRef = siteBudgetRefNum;
	        	summary.confirmNum = confirmationNum;
	        	
	        	Date buffer = getDate(rs.getDate("Order_Date"), rs.getTimestamp("Order_Time"));
	            buffer = convertDate(buffer, DEFAULT_TIMEZONE, tzSource);
	            _orderDate = new Date(buffer.getTime());
	            
	            summary.orderDate = _orderDate;
	            poNum = rs.getString("PO_Num");
	            summary.poNum = poNum;
	            summary.saleType = rs.getString("Sale_Type");
	            
	            subTotal = rs.getBigDecimal("Order_Sub_Total");
	            summary.orderSubTotal = (subTotal==null?defaultValue:subTotal);
	            
	            freight = rs.getBigDecimal("Freight");
	            summary.freight = (freight==null?defaultValue:freight);
	            
	            handling = rs.getBigDecimal("Handling");
	            summary.handling = (handling==null?defaultValue:handling);
	            
	            discount = rs.getBigDecimal("Discount");
	            summary.discount = (discount==null?defaultValue:discount);
	            
	            currencyCode = rs.getString("Currency_Code");
	            summary.currencyCode = currencyCode;
	            
	            it = backOrderSummary.iterator();
	            isNewRecord = true;
	            while(it.hasNext()){
	            	existingRecord = (BackOrderSummary)it.next();
	            	if(existingRecord.confirmNum.trim().equals(summary.confirmNum.trim())) {
	            		isNewRecord = false;
	            		break;
	            	}
	            }
	            
	            if(isNewRecord){
	            	 backOrderSummary.add(summary);
	            }
	            
	            Integer orderItemId = rs.getInt("ORDER_ITEM_ID");
	            List<BackOrderDetail> detailList = backOrderDetailsMap.get(orderItemId);
	            if(detailList == null){
	            	detailList = new ArrayList<BackOrderDetail>();
	            	backOrderDetailsMap.put(orderItemId,detailList);
	            }
	            detail = new BackOrderDetail();
	            detailList.add(detail);
	            
	            detail.orderLineNum = rs.getInt("ORDER_LINE_NUM");
	        	detail.storeId = storeId;
	        	detail.itemId = rs.getInt("Item_Id");
	        	detail.accountId = rs.getInt("AccountId");
	        	
	        	detail.accountName = accountName;
	        	detail.siteName = siteName;
	        	detail.confirmNum = confirmationNum;
	        	detail.poNum = poNum;
	        	
	        	
	            detail.orderDate = _orderDate;
	            
	            detail.currencyCode = currencyCode;
	            detail.outboundPONum = rs.getString("Outbound_PO_Num");
	            detail.distSku = rs.getString("Dist_Sku");
	            detail.UOM = rs.getString("UOM");
	            detail.pack = rs.getString("Pack");
	            detail.itemSize = rs.getString("Item_Size");
	            detail.manufSku = rs.getString("Man_Sku");
	            
	            orderQty = rs.getBigDecimal("Order_Qty");
	            detail.orderQty = (orderQty==null?defaultValue:orderQty);
	            
	            //have to combine with existing
	            shipQty = rs.getBigDecimal("Ship_Qty");
	            detail.shipQty = (shipQty==null?defaultValue:shipQty);
	            
	            lineTotal = rs.getBigDecimal("Line_Total");
	            detail.lineTotal = (lineTotal==null?defaultValue:lineTotal);
	            
	            detail.storeSku = rs.getString("Store_Sku");
	            detail.name = rs.getString("Name");
	            detail.delRefNum = rs.getString("Del_Ref_Num");
	            
	            Date delRefDate = getDate(rs.getDate("Del_Ref_Num_Date"), rs.getTimestamp("Del_Ref_Num_Time"));
	            delRefDate = convertDate(delRefDate, DEFAULT_TIMEZONE, tzSource);
	            Date delRefNumDate = new Date(delRefDate.getTime());
	            detail.delRefNumDate = delRefNumDate;
	            
	            detail.siteBudgetRef = siteBudgetRefNum;
	            detail.distributor = rs.getString("Distributor");
	            detail.manufacturer = rs.getString("Manufacturer");
		        detail.myCategory = detail.getCategory();
	            detail.spl = rs.getString("spl");
	            
	            
	            
		        
	        }
        }finally {
        	 if(rs!=null){
             	rs.close();
             }
             if(stmt!=null){
             	stmt.close();
             }
        }
        
       
        StringBuilder builder = new StringBuilder();
        builder.append(begDateS);
        builder.append(" ");
        builder.append("-");
        builder.append(" ");
        builder.append(endDateS);
        
        GenericReportColumnViewVector titleInfo = getReportTitle(pReportData.getName(), storeName, accountNames,builder.toString());
        
        //filter out anything that is back ordered quantity of 0.  We have to do this as a separate
        //loop in case there were multiple shipments that resulted in an over shipment.
        Iterator<Integer> it = backOrderDetailsMap.keySet().iterator();
        List<BackOrderDetail> backOrderDetails = new ArrayList<BackOrderDetail>();
        while(it.hasNext()){
        	Integer oid = it.next();
        	if(isBackOrderline(backOrderDetailsMap.get(oid))){
        		backOrderDetails.addAll(backOrderDetailsMap.get(oid));
        	}
        }
        //now sort the lists
        Collections.sort(backOrderSummary,BackOrderSummaryComp);
        Collections.sort(backOrderDetails,BackOrderDetailsComp);
        generateResults(resultV,backOrderSummary,backOrderDetails,titleInfo);
        
        return resultV;
    }
    
    /**
     * Determine if this line is a back order by summing up all the shipped quantity and comparing it to the order quantity.
     * If the order quantity is more than the ship quantity it is a back order.
     * @param detailList
     * @return true if it is a back order, otherwise false.
     */
    private boolean isBackOrderline(List<BackOrderDetail> detailList){
    	BigDecimal qtyShipped = ZERO;
    	BigDecimal qtyOrdered = ZERO;
    	Iterator<BackOrderDetail> it = detailList.iterator();
    	while(it.hasNext()){
    		BackOrderDetail detail = it.next();
    		qtyShipped = Utility.addAmt(detail.shipQty, qtyShipped);
    		qtyOrdered = detail.orderQty; //the same on every line
    	}
    	if(qtyOrdered.compareTo(qtyShipped) > 0){
    		return true;
    	}
    	return false;
    	
    }
    
	protected void generateResults(GenericReportResultViewVector resultV,List boSummery, List boDetails,
			GenericReportColumnViewVector titleInfo) throws Exception {
		
		processList(boDetails, resultV, "Backorder Details", getBackOrderDetailsHeader(),titleInfo);
		processList(boSummery, resultV, "Backorder Summary", getBackOrderSummeryHeader(),titleInfo);
	}
	
	protected void processList(List toProcess,GenericReportResultViewVector resultV,String name, GenericReportColumnViewVector header,
			GenericReportColumnViewVector titleInfo) {
		Iterator it = toProcess.iterator();
		GenericReportResultView result = null;
		if (it.hasNext()) {
			result = GenericReportResultView.createValue();
		    result.setTable(new ArrayList());
		    while (it.hasNext()) {
		        result.getTable().add(((aRecord) it.next()).toList());
		    }
		}
		    if(result==null){
		    	result = GenericReportResultView.createValue();
		    }
		    result.setColumnCount(header.size());
		    result.setTitle(titleInfo);
		    result.setHeader(header);
		    result.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
		    result.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
		    result.setFreezePositionRow(6);
		    result.setName(name);
		    resultV.add(result);
	}
	
	 protected GenericReportColumnViewVector getReportTitle(String pReportName, String pStoreName, String pAccountName,String date) {

	        GenericReportColumnViewVector title = new GenericReportColumnViewVector();

	        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Report Name: " + pReportName, 0, 255, "VARCHAR2"));
	      	title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Store Name: " + pStoreName, 0, 255, "VARCHAR2"));
	      	title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account Name: " + pAccountName, 0, 255, "VARCHAR2"));
	      	title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Date Period Selected: " + date, 0, 255, "VARCHAR2"));
	        return title;
	    }
    
    private GenericReportColumnViewVector getBackOrderDetailsHeader() {
		GenericReportColumnViewVector header = new GenericReportColumnViewVector();

		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account_Name", 0, 255, "VARCHAR2", "30", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site_Name", 0, 255, "VARCHAR2", "30", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site_Budget_Ref", 0, 255, "VARCHAR2", "30", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Confirm_Num", 0, 255, "VARCHAR2", "30", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Outbound_PO_Num", 0,255, "VARCHAR2","30",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Del Ref Num", 0, 255, "VARCHAR2","30",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Del Ref Num Date", 0, 0, "DATE", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "PO_Num", 0, 255, "VARCHAR2", "12", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Order_Date", 0, 0, "DATE", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Category", 0, 255, "VARCHAR2", "12", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Store_Sku", 0, 255, "VARCHAR2","6",false));
        
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Dist_Sku", 0, 255, "VARCHAR2", "30", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "UOM", 0, 255, "VARCHAR2", "30", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Pack", 0, 255, "VARCHAR2", "30", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Item_Size", 0, 255, "VARCHAR2", "30", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Man_Sku", 0, 255, "VARCHAR2", "30", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Manufacturer", 0, 255, "VARCHAR2", "30", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Distributor", 0, 255, "VARCHAR2", "30", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Name", 0, 255, "VARCHAR2", "30", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Order Qty", 2, 20, "NUMBER", "10", false));
        
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Backorder Qty", 2, 20, "NUMBER", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Line_Total", 2, 20, "NUMBER", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Currency Code", 0, 255, "VARCHAR2", "30", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "SPL", 0, 255, "VARCHAR2", "30", false));
        

        return header;
	}
    
    private GenericReportColumnViewVector getBackOrderSummeryHeader() {
		GenericReportColumnViewVector header = new GenericReportColumnViewVector();

		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account_Name", 0, 255, "VARCHAR2", "30", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site_Name", 0, 255, "VARCHAR2", "30", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site_Budget_Ref", 0, 255, "VARCHAR2", "10", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Confirm_Num", 0, 255, "VARCHAR2", "10", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Order_Date", 0, 0, "DATE", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "PO_Num", 0, 255, "VARCHAR2","30",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Sale_Type", 0, 255, "VARCHAR2", "30", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Order_Sub_Total", 2, 20, "NUMBER", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Freight", 2, 20, "NUMBER", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Handling", 2, 20, "NUMBER", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Discount", 2, 20, "NUMBER", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Currency Code", 0, 255, "VARCHAR2","6",false));
        

        return header;
	}


    protected interface aRecord {
        ArrayList toList();
    }

    protected class BackOrderDetail implements aRecord {
    	int storeId = 0;
        int itemId = 0;
        int accountId = 0;
    	String accountName;
    	String siteName;
    	String siteBudgetRef; 
    	String confirmNum; 
    	String outboundPONum; 
    	String delRefNum; 
    	Date delRefNumDate;
    	String poNum;
    	Date orderDate;
    	String myCategory;
    	String storeSku;
    	String distSku;
        String UOM;
        String pack;
        String itemSize;
        String manufSku;
        String manufacturer;
        String distributor;
        String name;
        BigDecimal orderQty;
        BigDecimal shipQty;
        BigDecimal lineTotal;
        String currencyCode;
        String spl;
        int orderLineNum;
    	
        protected BigDecimal getBackOrderQty(){
        	return Utility.subtractAmt(orderQty, shipQty);
        }
        
        protected String getCategory() {
        	
            myCategory = "Other";
	        if (storeId > 0 && itemId > 0 && accountId > 0) {
	        	
		           StringBuilder builder = new StringBuilder();
		           builder.append(accountId);
		           builder.append("::");
		           builder.append(itemId);
		           String key = builder.toString();
		           
		           myCategory = (String) masterItemCategoryMap.get(key);
		           if(myCategory!=null) {
		        	   return myCategory;
		           } else {
		        	   String sqlQuery = " SELECT CLW_ITEM.SHORT_DESC AS CategoryName " +
		      					" FROM CLW_ITEM,CLW_ITEM_ASSOC " +
		       				" WHERE CLW_ITEM_ASSOC.ITEM2_ID = CLW_ITEM.ITEM_ID AND CLW_ITEM_ASSOC.ITEM1_ID = "+itemId +
		       				" AND CLW_ITEM_ASSOC.CATALOG_ID = (SELECT CATALOG_ID FROM CLW_CATALOG WHERE " +
		       				" CATALOG_TYPE_CD='"+RefCodeNames.CATALOG_TYPE_CD.ACCOUNT+"'" +
		       				" AND CATALOG_ID IN (SELECT CATALOG_ID FROM CLW_CATALOG_ASSOC WHERE BUS_ENTITY_ID= "+accountId+" ))";
		        	   	
		        	   	  ResultSet rs = null;
		        	   	  Statement stmt = null;
					      try{
					    	  
					   	   	  stmt = con.createStatement();
					          rs = stmt.executeQuery(sqlQuery);
					          
					          while(rs.next()) {
					       	   	myCategory = rs.getString("CategoryName");
					          }
					      }catch (Exception e) {
					      } finally {
					    	  try{
						    	  if(rs!=null) {
						    		  rs.close();
						    	  }
						    	  if(stmt!=null){
						    		  stmt.close();
						    	  }
					    	  }catch(Exception e){
					    	  }
					      }
					      masterItemCategoryMap.put(key, myCategory);
	           		}
	        	}
	        
	           return myCategory;
          }
        
        public ArrayList toList() {
            ArrayList list = new ArrayList();
            list.add(accountName);
            list.add(siteName);
            list.add(siteBudgetRef);
            list.add(confirmNum);
            list.add(outboundPONum);
            list.add(delRefNum);
            list.add(delRefNumDate);
            list.add(poNum);
            list.add(orderDate);
            list.add(myCategory);
            list.add(storeSku);
            list.add(distSku);
            list.add(UOM);
            list.add(pack);
            list.add(itemSize);
            list.add(manufSku);
            list.add(manufacturer);
            list.add(distributor);
            list.add(name);
            list.add(orderQty);
            list.add(getBackOrderQty());
            list.add(lineTotal);
            list.add(currencyCode);
            list.add(spl);
            return list;
        }
    }
    
    protected class BackOrderSummary implements aRecord {
        
    	String accountName;
    	String siteName;
    	String siteBudgetRef; 
    	String confirmNum; 
    	Date orderDate;
    	String poNum; 
    	String saleType;
    	BigDecimal orderSubTotal;
    	BigDecimal freight;
    	BigDecimal handling;
    	BigDecimal discount;
    	String currencyCode;
    	
        public ArrayList toList() {
            ArrayList list = new ArrayList();
            list.add(accountName);
            list.add(siteName);
            list.add(siteBudgetRef);
            list.add(confirmNum);
            list.add(orderDate);
            list.add(poNum);
            list.add(saleType);
            list.add(orderSubTotal);
            list.add(freight);
            list.add(handling);
            list.add(discount);
            list.add(currencyCode);
            return list;
        }
    }
    
    
    /**
     * Sorts the BackOrderSummary list by order number (confirm number)
     */
    private static Comparator<BackOrderSummary> BackOrderSummaryComp = new Comparator<BackOrderSummary>() {
		public int compare(BackOrderSummary o1, BackOrderSummary o2)
		{
			String confirm1 = o1.confirmNum;
			String confirm2 = o2.confirmNum;
			return Utility.compareToIgnoreCase(confirm1, confirm2);
		}
	};

	/**
     * Sorts the BackOrderSummary list by order number (confirm number) then by line number
     */
    private static Comparator<BackOrderDetail> BackOrderDetailsComp = new Comparator<BackOrderDetail>() {
		public int compare(BackOrderDetail o1, BackOrderDetail o2)
		{
			String confirm1 = o1.confirmNum;
			String confirm2 = o2.confirmNum;
			int confirmComp = Utility.compareToIgnoreCase(confirm1, confirm2);
			if(confirmComp == 0){
				return o1.orderLineNum - o2.orderLineNum;
			}
			return confirmComp;
		}
	};
	
	
	public boolean showOnlyDownloadReportButton() {
		return true;
	}
	
	private final static SimpleDateFormat SDF_DATE = new SimpleDateFormat("MM/dd/yyyy");
    private final static SimpleDateFormat SDF_TIME = new SimpleDateFormat("HH:mm");
    private final static SimpleDateFormat SDF_FULL = new SimpleDateFormat("MM/dd/yyyy HH:mm");

	private final static java.util.Date getDate(java.util.Date date,
	            Timestamp time) throws Exception {
		
		Date dateTime = null;
		try {
			dateTime = SDF_FULL.parse(SDF_DATE.format(date) + " " + SDF_TIME.format(time));
		}catch(Exception e){
			dateTime = date;
		} finally {
			if(dateTime==null) {
				dateTime = new Date();
			}
		}
		
		return dateTime;
	}
	
	private final static int ONE_SECOND = 1000;
    private final static int ONE_MINUTE = ONE_SECOND * 60;
    private final static int ONE_HOUR = ONE_MINUTE * 60;
    
	public final static java.util.Date convertDate(java.util.Date source,
	            TimeZone tzSource, TimeZone tzTarget) {
	        long timeInMillis = source.getTime();
	        Calendar c = Calendar.getInstance();
	        c.setTime(source);
	        int millis = c.get(Calendar.HOUR_OF_DAY) * ONE_HOUR
	                + c.get(Calendar.MINUTE) * ONE_MINUTE;
	        long delta1 = tzSource.getOffset(c.get(Calendar.ERA), c
	                .get(Calendar.YEAR), c.get(Calendar.MONTH), c
	                .get(Calendar.DAY_OF_MONTH), c.get(Calendar.DAY_OF_WEEK),
	                millis);
	        int delta2 = tzTarget.getOffset(c.get(Calendar.ERA), c
	                .get(Calendar.YEAR), c.get(Calendar.MONTH), c
	                .get(Calendar.DAY_OF_MONTH), c.get(Calendar.DAY_OF_WEEK),
	                millis);
	        return new java.util.Date(timeInMillis + (delta2 - delta1));
	   }
	
    /**
     * Returns account ids as a string.
     * @param acctIds
     * @return
     */
    private static String getAccountIds(List<Integer> acctIds) {
    	 StringBuilder builder = new StringBuilder();
	    	int count = 0;
	    	for(int accId:acctIds){
	    		builder.append(accId);
	    		if(count!=(acctIds.size()-1)){
	    			builder.append(",");
	    		}
	    		count++;
	    	}
	    	String accountIdS = builder.toString();
	    	
    	return accountIdS;
    }


}
