/*
 * OutboundPunchOutOrder_JCP.java
 *
 * Created on June 9, 2004, 5:26 PM
 */

package com.cleanwise.service.apps.dataexchange;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import java.util.Iterator;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import org.apache.poi.hssf.usermodel.*;

/**
 * 
 * @author  bstevens
 */
public class OutboundPunchOutOrder_JCP extends InterchangeOutboundSuper implements OutboundTransaction{
	protected Logger log = Logger.getLogger(this.getClass());
	short mRowCounter = 0;
    // create a new workbook
    HSSFWorkbook mXlsWorkBook;
    // create a new sheet
    HSSFSheet mXlsSheet;
    static final short H_CODE = 0;
    static final short H_BILL_UNIT = 1;
    static final short H_SHIP_UNIT = 2;
    static final short H_APPROP_AOR = 3;
    static final short H_REQUISITION_TYPE = 4;
    static final short H_DELIVERY_DATE_IND = 5;
    static final short H_DELIVERY_DATE = 6;
    static final short H_SHIP_TO = 7;
    static final short H_BILL_TO = 8;
    static final short H_ORN = 9;
    static final short H_PLANNED_JOB_NUMBER = 10;
    static final short H_PROJECT = 11;
    static final short H_SPECIAL_INSTRUCTIONS = 12;
    static final short H_SHIP_INSTRUCTIONS = 13;
    static final short H_OVERRIDE_ACCOUNT_NUMBER = 14;
    static final short H_PO_MEMO = 15;
    static final short H_BYPASS_APPROVAL = 16;
    static final short H_NEXT_TO_APPROVE = 17;
    static final short H_APPROVER_NOTE = 18;
    static final short H_AUTOREC_IND = 19;
    static final short H_REPLEN_IND = 20;
    static final short H_REPLEN_LOCATION_SUPPLIER = 21;
    static final short H_REPLEN_LOCATION_SHIPPOINT = 22;
    static final short H_INITIAL_ROLLOUT_IND = 23;
    static final short H_ARRIVAL_DATE = 24;
	static final short H_POET_IND = 25;
	static final short H_PA_PROJECT_NBR = 26;
	static final short H_ORGANIZATION_ID = 27;
	static final short H_TASK_NBR = 28;
    
    static final short D_CODE = 0;
    static final short D_ITEM = 1;
    static final short D_COLOR = 2;
    static final short D_QUANTITY = 3;
    static final short D_COST = 4;
    static final short D_UNIT_OF_PURCHASE  = 5;
    static final short D_UNIT_SIZE = 6;
    static final short D_ACCOUNT_NUMBER = 7;
    static final short D_UNSPSC = 8;
    static final short D_SUPPLIER = 9;
    static final short D_SHIP_POINT = 10;
    static final short D_LEAD_TIME = 11;
    static final short D_FREIGHT_IND = 12;
    static final short D_TAX_IND = 13;
    static final short D_BUYER = 14;
    static final short D_DESCRIPTION = 15;
    static final short D_COST_INDICATOR = 16;
    static final short D_DEMAND_AT_ZERO_COST = 17;
    static final short D_BYPASS_INV = 18;
	static final short D_CONTRACT = 19;
	static final short D_AMENDMENT = 20;
    static final short D_EXPENDITURE_TYPE = 21;

    public OutboundPunchOutOrder_JCP(){
    	mXlsWorkBook = new HSSFWorkbook();
        // create a new sheet
        mXlsSheet = mXlsWorkBook.createSheet();
    }
    private HSSFRow getNewRow(){
        HSSFRow row = mXlsSheet.createRow((short) mRowCounter);
        mRowCounter++;
        return row;
    }
    
    public void buildInterchangeHeader()
	throws Exception
	{
		super.buildInterchangeHeader();
		// declare a row object reference
        HSSFRow xlsRow = null;
        // declare a cell object reference
        HSSFCell xlsCell = null;
        // create a row
        xlsRow = getNewRow();
        
        //write out the header rows
        //first header row
        for(short i=0;i<=H_TASK_NBR;i++){
            xlsCell = xlsRow.createCell((short) i);
            switch (i) {
                case H_CODE:
                    xlsCell.setCellValue("C");
                    break;
                case H_BILL_UNIT:
                    xlsCell.setCellValue("BILL UNIT");
                    break;
                case H_SHIP_UNIT:
                    xlsCell.setCellValue("SHIP UNIT");
                    break;
                case H_APPROP_AOR:
                    xlsCell.setCellValue("APPROP/AOR");
                    break;
                case H_REQUISITION_TYPE:
                    xlsCell.setCellValue("REQUISITION TYPE");
                    break;
                case H_DELIVERY_DATE_IND:
                    xlsCell.setCellValue("DELIVERY DATE IND");
                    break;
                case H_DELIVERY_DATE:
                    xlsCell.setCellValue("DELIVERY DATE");
                    break;
                case H_SHIP_TO:
                    xlsCell.setCellValue("SHIP TO");
                    break;
                case H_BILL_TO:
                    xlsCell.setCellValue("BILL TO");
                    break;
                case H_ORN :
                    xlsCell.setCellValue("ORN");
                    break;
                case H_PLANNED_JOB_NUMBER:
                    xlsCell.setCellValue("PLANNED JOB NUMBER");
                    break;
                case H_PROJECT:
                    xlsCell.setCellValue("PROJECT");
                    break;
                case H_SPECIAL_INSTRUCTIONS:
                    xlsCell.setCellValue("SPECIAL INSTRUCTIONS");
                    break;
                case H_SHIP_INSTRUCTIONS:
                    xlsCell.setCellValue("SHIP INSTRUCTIONS");
                    break;
                case H_OVERRIDE_ACCOUNT_NUMBER:
                    xlsCell.setCellValue("OVERRIDE ACCOUNT NUMBER");
                    break;
                case H_PO_MEMO:
                    xlsCell.setCellValue("PO MEMO");
                    break;
                case H_BYPASS_APPROVAL:
                    xlsCell.setCellValue("NFR PROGRAM AUTHORIZATION");
                    break;
                case H_NEXT_TO_APPROVE:
                    xlsCell.setCellValue("NEXT TO APPROVE");
                    break;
                case H_APPROVER_NOTE:
                    xlsCell.setCellValue("APPROVER NOTE");
                    break;
                case H_AUTOREC_IND:
                    xlsCell.setCellValue("AUTOREC IND");
                    break;
                case H_REPLEN_IND:
                    xlsCell.setCellValue("REPLEN_IND");
                    break;
                case H_REPLEN_LOCATION_SUPPLIER:
                    xlsCell.setCellValue("REPLEN_LOCATION_SHIPPOINT");
                    break;
                case H_REPLEN_LOCATION_SHIPPOINT:
                    xlsCell.setCellValue("REPLEN_LOCATION_SUPPLIER");
                    break;
                case H_INITIAL_ROLLOUT_IND:
                    xlsCell.setCellValue("INITIAL_ROLLOUT_IND");
                    break;
                case H_ARRIVAL_DATE:
                    xlsCell.setCellValue("ARRIVAL_DATE");
                    break;
				case H_POET_IND:
                    xlsCell.setCellValue("POET IND");
                    break;
				case H_PA_PROJECT_NBR:
                    xlsCell.setCellValue("PA PROJECT NBR");
                    break;
				case H_ORGANIZATION_ID:
                    xlsCell.setCellValue("ORGANIZATION ID");
                    break;
				case H_TASK_NBR:
					xlsCell.setCellValue("TASK NBR");
					break;
            }
        }
        
        xlsRow = getNewRow();
        //second header row
        for(short i=0;i<=D_EXPENDITURE_TYPE;i++){
            xlsCell = xlsRow.createCell((short) i);
            switch (i) {
                case D_CODE:
                    xlsCell.setCellValue("C");
                    break;
                case D_ITEM :
                    xlsCell.setCellValue("ITEM");
                    break;
                case D_COLOR :
                    xlsCell.setCellValue("COLOR");
                    break;
                case D_QUANTITY :
                    xlsCell.setCellValue("QUANTITY");
                    break;
                case D_COST :
                    xlsCell.setCellValue("COST");
                    break;
                case D_UNIT_OF_PURCHASE  :
                    xlsCell.setCellValue("UNIT OF PURCHASE");
                    break;
                case D_UNIT_SIZE :
                    xlsCell.setCellValue("UNIT SIZE");
                    break;
                case D_ACCOUNT_NUMBER :
                    xlsCell.setCellValue("ACCOUNT NUMBER");
                    break;
                case D_UNSPSC :
                    xlsCell.setCellValue("UNSPSC");
                    break;
                case D_SUPPLIER :
                    xlsCell.setCellValue("SUPPLIER");
                    break;
                case D_SHIP_POINT :
                    xlsCell.setCellValue("SHIP POINT");
                    break;
                case D_LEAD_TIME :
                    xlsCell.setCellValue("LEAD TIME");
                    break;
                case D_FREIGHT_IND :
                    xlsCell.setCellValue("FREIGHT IND");
                    break;
                case D_TAX_IND :
                    xlsCell.setCellValue("TAX IND");
                    break;
                case D_BUYER :
                    xlsCell.setCellValue("BUYER");
                    break;
                case D_DESCRIPTION :
                    xlsCell.setCellValue("DESCRIPTION");
                    break;
                case D_COST_INDICATOR :
                    xlsCell.setCellValue("COST INDICATOR");
                    break;
                 case D_DEMAND_AT_ZERO_COST :
                    xlsCell.setCellValue("DEMAND AT ZERO COST");
                    break;
                case D_BYPASS_INV :
                    xlsCell.setCellValue("BYPASS INV");
                    break;
				case D_CONTRACT :
                    xlsCell.setCellValue("CONTRACT");
                    break;	
				case D_AMENDMENT :
                    xlsCell.setCellValue("AMENDMENT");
                    break;	
				case D_EXPENDITURE_TYPE :
                    xlsCell.setCellValue("EXPENDITURE TYPE");
                    break;	
							

            }
        }
	}
    
    public void buildInterchangeTrailer() throws Exception {
		// write the workbook to the output stream
        mXlsWorkBook.write(getTranslator().getOutputStream());
        super.buildInterchangeTrailer();		
	}
    	    
    public void buildTransactionContent()
    throws Exception {
    	currOrder = currOutboundReq.getOrderD();
    	items = currOutboundReq.getOrderItemDV();
    	drawOrderRecord();
        Iterator itmIt = items.iterator();
        while (itmIt.hasNext()){
            currItem = (OrderItemData) itmIt.next();
            drawOrderItemRecord();
        }
        currOrder.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.SENT_TO_CUST_SYSTEM);
        appendIntegrationRequest(currOrder);
	}
    
    private void drawOrderRecord(){
        String storeNum = Utility.getEDIToken(currOrder.getOrderSiteName());
        storeNum = storeNum.substring(0,4);
        // create a row
        HSSFRow xlsRow = getNewRow();
        for(short i=0;i<=H_TASK_NBR;i++){
            HSSFCell xlsCell = xlsRow.createCell((short) i);
            switch (i) {
                case H_CODE:
                    xlsCell.setCellValue("H");
                    break;
                case H_BILL_UNIT:
                    String sname = storeNum;
                    if(Utility.isSet(currOutboundReq.getCustomerBillingUnit())){
                        sname = currOutboundReq.getCustomerBillingUnit();
                    }
                    xlsCell.setCellValue(sname);
                    break;
                case H_SHIP_UNIT:
                    xlsCell.setCellValue(storeNum);
                    break;
                case H_APPROP_AOR:
                    xlsCell.setCellValue("007900");
                    break;
                case H_REQUISITION_TYPE:
                    xlsCell.setCellValue("RL");
                    break;
                case H_DELIVERY_DATE_IND:
                    xlsCell.setCellValue("N");
                    break;
                case H_DELIVERY_DATE:
                    xlsCell.setCellValue("");
                    break;
                case H_SHIP_TO:
                    xlsCell.setCellValue(storeNum);
                    break;
                case H_BILL_TO:
                    xlsCell.setCellValue("slchome");
                    break;
                case H_ORN :
                    xlsCell.setCellValue("");
                    break;
                case H_PLANNED_JOB_NUMBER:
                    xlsCell.setCellValue("");
                    break;
                case H_PROJECT:
                    java.util.Date now = new java.util.Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    xlsCell.setCellValue("CLEANWISE"+sdf.format(now)); //20 chars max
                    break;
                case H_SPECIAL_INSTRUCTIONS:
                    //xlsCell.setCellValue("~"+currOrder.getOrderNum()+"~");
                	String message = "CW Order Num ";
                    xlsCell.setCellValue(message + currOrder.getOrderNum());
                    break;
                case H_SHIP_INSTRUCTIONS:
                    xlsCell.setCellValue("MAKE DEL. ON DATE SHOWN OR BEFORE");
                    break;
                case H_OVERRIDE_ACCOUNT_NUMBER:
                    //xlsCell.setCellValue("7345");
                    //removed per Joe Parr 9/21/2004
                    xlsCell.setCellValue("");
                    break;
                case H_PO_MEMO:
                    xlsCell.setCellValue("");
                    break;
                case H_BYPASS_APPROVAL:
                    xlsCell.setCellValue("Y");
                    break;
                case H_NEXT_TO_APPROVE:
                    xlsCell.setCellValue("");
                    break;
                case H_APPROVER_NOTE:
                    xlsCell.setCellValue("");
                    break;
                case H_AUTOREC_IND:
                    xlsCell.setCellValue("N");
                    break;
                case H_REPLEN_IND:
                    xlsCell.setCellValue("N");
                    break;
                case H_REPLEN_LOCATION_SUPPLIER:
                    xlsCell.setCellValue("");
                    break;
                case H_REPLEN_LOCATION_SHIPPOINT:
                    xlsCell.setCellValue("");
                    break;
                case H_INITIAL_ROLLOUT_IND:
                    xlsCell.setCellValue("N");
                    break;
                case H_ARRIVAL_DATE:
                    xlsCell.setCellValue("");
                    break;
				case H_POET_IND:
                    xlsCell.setCellValue("N");
                    break;
				case H_PA_PROJECT_NBR:
                    xlsCell.setCellValue("");
                    break;
				case H_ORGANIZATION_ID:
                    xlsCell.setCellValue("");
                    break;
				case H_TASK_NBR:
					xlsCell.setCellValue("");
					break;					
            }
        }
    }
    
    private void drawOrderItemRecord(){
        HSSFRow xlsRow = getNewRow();
        //second header row
        for(short i=0;i<=D_EXPENDITURE_TYPE;i++){
            HSSFCell xlsCell = xlsRow.createCell((short) i);
            switch (i) {
                case D_CODE:
                    xlsCell.setCellValue("D");
                    break;
                case D_ITEM :
                    xlsCell.setCellValue(Utility.getActualSkuNumber(currOutboundReq.getStoreType(), currItem));
                    break;
                case D_COLOR :
                    xlsCell.setCellValue("");
                    break;
                case D_QUANTITY :
                    xlsCell.setCellValue(currItem.getTotalQuantityOrdered());
                    break;
                case D_COST :
                    //xlsCell.setCellValue(mCurrItem.getCustContractPrice().doubleValue());
                    //removed per JCP
                    xlsCell.setCellValue("");
                    break;
                case D_UNIT_OF_PURCHASE  :
                    /*String uom;
                    if(mTradingPartner.getSkuTypeCd().equals(RefCodeNames.SKU_TYPE_CD.CUSTOMER) && Utility.isSet(mCurrItem.getCustItemUom())){
                      uom = mCurrItem.getCustItemUom();
                    }else{
                      uom = mCurrItem.getItemUom();
                    }
                    xlsCell.setCellValue(mCurrItem.getItemUom());*/
                    //removed per JCP
                    xlsCell.setCellValue("");
                    break;
                case D_UNIT_SIZE :
                    xlsCell.setCellValue("");
                    break;
                case D_ACCOUNT_NUMBER :
                    xlsCell.setCellValue("");
                    break;
                case D_UNSPSC :
                    xlsCell.setCellValue("");
                    break;
                case D_SUPPLIER :
                    xlsCell.setCellValue("");
                    break;
                case D_SHIP_POINT :
                    xlsCell.setCellValue("");
                    break;
                case D_LEAD_TIME :
                    xlsCell.setCellValue("");
                    break;
                case D_FREIGHT_IND :
                    xlsCell.setCellValue("");
                    break;
                case D_TAX_IND :
                    xlsCell.setCellValue("");
                    break;
                case D_BUYER :
                    xlsCell.setCellValue("");
                    break;
                case D_DESCRIPTION :
                    xlsCell.setCellValue("");
                    break;
                case D_COST_INDICATOR :
                    xlsCell.setCellValue("N");
                    break;
                case D_DEMAND_AT_ZERO_COST :
                    xlsCell.setCellValue("N");
                    break;
                case D_BYPASS_INV :
                    xlsCell.setCellValue("N");
                    break;
				case D_CONTRACT :
                    xlsCell.setCellValue("");
                    break;	
				case D_AMENDMENT :
                    xlsCell.setCellValue("");
                    break;	
				case D_EXPENDITURE_TYPE :
                    xlsCell.setCellValue("");
                    break;						
            }
        }
    }
    public String getFileExtension() throws Exception {
        return ".xls";
    }
    public String getTranslationReport() {
		if(getTransactionsToProcess().size() == 0){
            return "no records translated";
        }
        return "Successfully processed "+getTransactionsToProcess().size() + " records";
	}
}
