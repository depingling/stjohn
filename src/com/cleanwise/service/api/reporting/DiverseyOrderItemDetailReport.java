package com.cleanwise.service.api.reporting;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.apps.dataexchange.OutboundOrderTotalsDiversey;

public class DiverseyOrderItemDetailReport  implements GenericReport{
	protected Logger log = Logger.getLogger(this.getClass());
	
	private static int CONF_NUM = 4;
	private static int ORDER_DATE = 6;
	private static int QUANTITY = 20;
	private static int LINE_TOTAL = 21;
	private static int STORE_ID = 25;
    
	/**
     *Should return a populated GenericReportResultView object.  At a minimum the header should
     *be set so an empty report may be generated to the user.
     */
    public GenericReportResultView process(ConnectionContainer pCons,GenericReportData pReportData,Map pParams)
    throws Exception{
    	Connection mCon = pCons.getDefaultConnection();
    	String begDateS = (String) pParams.get("BEG_DATE");
        String endDateS = (String) pParams.get("END_DATE_OPT");
        String dateFmt = (String) pParams.get("DATE_FMT");
        String storeIdS = (String) pParams.get("STORE");
        String jdBrandsGroupIdS = (String) pParams.get("JD_BRAND_ID");

        if (null == dateFmt) {
            dateFmt = "MM/dd/yyyy";
        }
        
        int storeId = 0;
        try {
        	storeId = Integer.parseInt(storeIdS);
        } catch (NumberFormatException e) {
            log.info(e.getMessage());
            String mess = "^clw^Store identifier is not a valid value^clw^";
            throw new Exception(mess);
        } 
        
        OutboundOrderTotalsDiversey ortD = new OutboundOrderTotalsDiversey();  
        int jdBrandsManufGroupId = 0;
        try {
        	if (!Utility.isSet(jdBrandsGroupIdS)){
        		String mess = "^clw^JD_BRAND_ID is not defined^clw^";
                throw new Exception(mess);
        	}
        	jdBrandsManufGroupId = Integer.parseInt(jdBrandsGroupIdS);
        	
        } catch (NumberFormatException e) {
            log.info(e.getMessage());
            String mess = "^clw^JD_BRAND_ID is not a valid value^clw^";
            throw new Exception(mess);
        } 
        boolean filterOutQuantity = true;
        ArrayList tableData = ortD.getOrderItemDetailTable(mCon, storeId, dateFmt, begDateS, endDateS, jdBrandsManufGroupId, filterOutQuantity);
    	for(int i = 0; i < tableData.size(); i++){
    		List rowData = (List) tableData.get(i);
    		for (int j = 0; j < rowData.size(); j++){
    			String field = (String) rowData.get(j);
    			if (CONF_NUM == j+1 || QUANTITY == j+1 || STORE_ID <= j+1){
    				if (Utility.isSet(field))
    					rowData.set(j, new Integer(field));
    			}else if (LINE_TOTAL == j+1){
    				rowData.set(j, new BigDecimal(field));
    			}else if (ORDER_DATE == j+1){
    				SimpleDateFormat sdfO = new SimpleDateFormat("MM/dd/yyyy");
    				rowData.set(j, sdfO.parse(field));
    			}
    		}
    	}
    	
    	GenericReportResultView results = GenericReportResultView.createValue();
    	results.setTable(tableData);
    	results.setColumnCount(getReportHeader().size());
    	results.setHeader(getReportHeader());
    	results.setName("Diversey Order Item Detail");
    	return results;
    }
    
    private GenericReportColumnViewVector getReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Store Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Confirmation Number",0,50,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","PO Number",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Order Date",0,0,"DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category",0,50,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Store SKU",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Distributor SKU",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","UOM",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Pack",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item Size",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Manufacturer SKU",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Manufacturer",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Distributor",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Green Item",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Compliance Item",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Sale Type",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Quantity",0,10,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Line Total$",2,20,"NUMBER","*",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Currency",0,3,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Order Item Status",0,30,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Order Status",0,30,"VARCHAR2"));        
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Store Id",0,15,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Account Id",0,15,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Site Id",0,15,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Order Id",0,15,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Order Item Id",0,15,"NUMBER"));
        return header;
    }
}
