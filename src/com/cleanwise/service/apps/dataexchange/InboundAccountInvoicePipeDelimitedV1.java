/*
 * InboundInvoiceFlatFile.java
 *
 * Created on March 1, 2003, 9:35 PM
 */

package com.cleanwise.service.apps.dataexchange;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import com.cleanwise.service.api.value.InvoiceRequestData;
import com.cleanwise.service.api.value.InvoiceRequestDataVector;
import com.cleanwise.service.api.value.OrderData;

/**
 * Handles inbound invoices in a flat file format and does the mapping between
 * invoice and invoice detail so that the lat file sends only the apropriate
 * number of invoices instead of 1 per line
 * @author  bstevens
 */
public class InboundAccountInvoicePipeDelimitedV1 extends InboundFlatFile{
	protected Logger log = Logger.getLogger(this.getClass());
	private static final int VERSION = 0;
	private static final int INVOICE_NUM = 1;
	private static final int CUST_PO_NUM = 2;
	private static final int STORE_ERP_PO_NUM = 3;
	private static final int INVOICE_DATE = 4;
	private static final int INVOICE_TOTAL = 5;
	private static final int TAX = 6;
	private static final int MISC_CHARGE = 7;
	private static final int FREIGHT = 8;
	private static final int DISCOUNT = 9;
	private static final int INVOICE_TYPE = 10;
	private static final int VENDOR_CODE = 11;
	private static final int BILL_TO_NUM = 12;
	private static final int BILL_TO_NAME = 13;
	private static final int BILL_TO_ADDR1 = 14;
	private static final int BILL_TO_ADDR2 = 15;
	private static final int BILL_TO_CITY = 16;
	private static final int BILL_TO_STATE = 17;
	private static final int BILL_TO_POSTAL = 18;
	private static final int SHIP_TO_NUM = 19;
	private static final int SHIP_TO_NAME = 20;
	private static final int SHIP_TO_ADDR1 = 21;
	private static final int SHIP_TO_ADDR2 = 22;
	private static final int SHIP_TO_ADDR3 = 23;
	private static final int SHIP_TO_ADDR4 = 24;	
	private static final int SHIP_TO_CITY = 25;
	private static final int SHIP_TO_STATE = 26;
	private static final int SHIP_TO_POSTAL = 27;
	private static final int LINE_NUM = 28;
	private static final int QUANTITY = 29;
	private static final int UOM = 30;
	private static final int PACK = 31;
	private static final int LINE_TOTAL = 32;
	private static final int ITEM_COST = 33;
	private static final int DIST_SKU = 34;
	private static final int ITEM_DESC = 35;
	private static final int INVOICE_LINE_NUM = 36;
	private Map<Integer, String> columnToMethodMap = new HashMap<Integer, String>();
	private Map<Integer, String> detailColumnToMethodMap = new HashMap<Integer, String>();
	private Map<String, String> invoiceTypeMap = new HashMap<String, String>(); // <invoiceNum, invoiceType>
	private InvoiceRequestDataVector reqInvoiceDV = new InvoiceRequestDataVector();
	
	// Processing variables
	protected InvoiceRequestData reqInvoice = null;
	protected BigDecimal  controlTotalSumInvoice = null;
	
	public InboundAccountInvoicePipeDelimitedV1() {
		super.setSepertorChar('|');
		super.setQuoteChar('"');	
		
		columnToMethodMap.put(INVOICE_NUM, "InvoiceNum");
		columnToMethodMap.put(CUST_PO_NUM, "DistOrderNum");
		columnToMethodMap.put(STORE_ERP_PO_NUM, "ErpPoNum");
		columnToMethodMap.put(INVOICE_DATE, "InvoiceDate");
		columnToMethodMap.put(INVOICE_TOTAL, "SubTotal");
		columnToMethodMap.put(TAX, "SalesTax");
		columnToMethodMap.put(MISC_CHARGE, "MiscCharges");
		columnToMethodMap.put(FREIGHT, "Freight");
		columnToMethodMap.put(DISCOUNT, "Discounts");
		//columnToMethodMap.put(VENDOR_CODE, ""); -- Reserved for future use
		//BILL_TO_NUM = 12;
		//BILL_TO_NAME = 13;
		//BILL_TO_ADDR1 = 14;
		//BILL_TO_ADDR2 = 15;
		//BILL_TO_CITY = 16;
		//BILL_TO_STATE = 17;
		//BILL_TO_POSTAL = 18;
		//columnToMethodMap.put(SHIP_TO_NUM, "shipToNum");		
		columnToMethodMap.put(SHIP_TO_NAME, "ShipToName");
		columnToMethodMap.put(SHIP_TO_ADDR1, "ShipToAddress1");
		columnToMethodMap.put(SHIP_TO_ADDR2, "ShipToAddress2");
		columnToMethodMap.put(SHIP_TO_ADDR3, "ShipToAddress3");
		columnToMethodMap.put(SHIP_TO_ADDR4, "ShipToAddress4");
		columnToMethodMap.put(SHIP_TO_CITY, "ShipToCity");
		columnToMethodMap.put(SHIP_TO_STATE, "ShipToState");
		columnToMethodMap.put(SHIP_TO_POSTAL, "ShipToPostalCode");
		
		detailColumnToMethodMap.put(LINE_NUM, "ErpPoLineNum");
		detailColumnToMethodMap.put(QUANTITY, "DistItemQuantity");
		detailColumnToMethodMap.put(UOM, "DistItemUom");
		detailColumnToMethodMap.put(PACK, "DistItemPack");
		detailColumnToMethodMap.put(LINE_TOTAL, "LineTotal");
		detailColumnToMethodMap.put(ITEM_COST, "ItemReceivedCost");
		detailColumnToMethodMap.put(DIST_SKU, "DistItemSkuNum");
		detailColumnToMethodMap.put(ITEM_DESC, "DistItemShortDesc");
		detailColumnToMethodMap.put(INVOICE_LINE_NUM, "DistLineNumber");
		
	}
	
	protected String getValueObjectClassName(){
		return "No ValueObjectClassName";
	}
	
	protected void parseDetailLine(List pParsedLine) throws Exception{
		if (pParsedLine.size() < INVOICE_LINE_NUM+1){
			errorMsgs.add("Wrong column count (" + pParsedLine.size() + ") on line# " + (currentLineNumber+1) + ", Expected = " + INVOICE_LINE_NUM+1);
		}
		String versionNumber = (String) pParsedLine.get(VERSION);
		if (Utility.isSet(versionNumber) && !Utility.isEqual("1", versionNumber)){
			errorMsgs.add("Wrong version# '" + versionNumber + "'. Expected Version#=1 on line# " + (currentLineNumber+1));
		}
		String invoiceNum = (String) pParsedLine.get(INVOICE_NUM);
		if (!Utility.isSet(invoiceNum)){
			errorMsgs.add("Missing Invoice Number on line# " + (currentLineNumber+1));
		}
		if (getErrorMsgs().size() > 0){
			throw new RuntimeException(getFormatedErrorMsgs());
		}
		if (reqInvoice == null || !reqInvoice.getInvoiceD().getInvoiceNum().equals(invoiceNum)){
			reqInvoice = new InvoiceRequestData();
			reqInvoiceDV.add(reqInvoice);
			InvoiceDistData invoiceD = InvoiceDistData.createValue();
			reqInvoice.setInvoiceD(invoiceD);
			reqInvoice.setInvoiceDetailDV(new InvoiceDistDetailDataVector());
			Iterator colIter = columnToMethodMap.keySet().iterator();
			while (colIter.hasNext()){
				Integer column = (Integer) colIter.next();
				String beanProp = columnToMethodMap.get(column);
				Method meth = Utility.getJavaBeanSetterMethod(invoiceD,beanProp);
				String value = (String) pParsedLine.get(column);
				try{
					Utility.populateJavaBeanSetterMethod(invoiceD, meth,value,dateFormat);
				}catch(Exception e){
					e.printStackTrace();
					errorMsgs.add("-Could not populate method: "+beanProp + " Reason:"+e.getMessage());
				}
			}
			
			String invoiceType = (String) pParsedLine.get(INVOICE_TYPE);			
			invoiceTypeMap.put(invoiceNum, invoiceType);
			checkInvoiceHeaderError(invoiceD, invoiceType, currentLineNumber+1);
		}
		InvoiceDistDetailData invoiceDetailD = InvoiceDistDetailData.createValue();
		reqInvoice.getInvoiceDetailDV().add(invoiceDetailD);
		Iterator colIter = detailColumnToMethodMap.keySet().iterator();
		while (colIter.hasNext()){
			Integer column = (Integer) colIter.next();
			String beanProp = detailColumnToMethodMap.get(column);
			Method meth = Utility.getJavaBeanSetterMethod(invoiceDetailD,beanProp);
			String value = (String) pParsedLine.get(column);
			try{
				Utility.populateJavaBeanSetterMethod(invoiceDetailD, meth,value,dateFormat);
			}catch(Exception e){
				e.printStackTrace();
				errorMsgs.add("-Could not populate method: "+beanProp + " Reason:"+e.getMessage());
			}
		}
		invoiceDetailD.setInvoiceDistSkuNum(invoiceDetailD.getDistItemSkuNum());
		checkInvoiceDetailError(invoiceDetailD, currentLineNumber+1);
	}

	private void checkInvoiceHeaderError(InvoiceDistData invoiceD, String invoiceType, int lineNum) {
		if (!Utility.isSet(invoiceD.getErpPoNum()))
			errorMsgs.add("Missing Cleanwise Purchase Order Number on line# " + lineNum);
		if (invoiceD.getInvoiceDate() == null)
			errorMsgs.add("Missing Invoice Date on line# " + lineNum);
		if (!Utility.isSet(invoiceD.getSubTotal()) || invoiceD.getSubTotal().abs().doubleValue() < 0.0001)
			errorMsgs.add("Missing Invoice Total on line# " + lineNum);
		if (!Utility.isSet(invoiceD.getShipToName()))
			errorMsgs.add("Missing Ship To Name on line# " + lineNum);
		if (!Utility.isSet(invoiceType))
			errorMsgs.add("Missing InvoiceType on line# " + lineNum);
		
	}
	
	private void checkInvoiceDetailError(InvoiceDistDetailData invoiceDetailD, int lineNum) {
		if(invoiceDetailD.getErpPoLineNum() == 0){
			errorMsgs.add("Missing Erp PO Line Num on line# " + lineNum);
    	}
		if(invoiceDetailD.getDistItemQuantity() == 0){
			errorMsgs.add("Missing Quantity on line# " + lineNum);
    	}
		if(!Utility.isSet(invoiceDetailD.getDistItemUom())){
			errorMsgs.add("Missing UOM on line# " + lineNum);
    	}
		if(!Utility.isSet(invoiceDetailD.getLineTotal()) || invoiceDetailD.getLineTotal().abs().doubleValue() < 0.0001){
			errorMsgs.add("Missing Line Total on line# " + lineNum);
    	}
		if(!Utility.isSet(invoiceDetailD.getItemReceivedCost())){
			errorMsgs.add("Missing Unit Amount on line# " + lineNum);
    	}
		if(!Utility.isSet(invoiceDetailD.getDistItemSkuNum())){
			errorMsgs.add("Missing Dist Sku Num on line# " + lineNum);
    	}
		if(!Utility.isSet(invoiceDetailD.getDistItemShortDesc())){
			errorMsgs.add("Missing Item Description on line# " + lineNum);
    	}
		if(invoiceDetailD.getDistLineNumber() == 0){
			errorMsgs.add("Missing Invoice Line Num on line# " + lineNum);
    	}

	}

	protected void doPostProcessing() throws Exception{
		if (getErrorMsgs().size() > 0){
			throw new RuntimeException(getFormatedErrorMsgs());
		}
		Iterator it = reqInvoiceDV.iterator();
		while(it.hasNext()){
			reqInvoice =(InvoiceRequestData) it.next();
			reqInvoice.setSkuTypeCd(getTranslator().getPartner().getSkuTypeCd());
			
			controlTotalSumInvoice=new BigDecimal(0);
			InvoiceDistData currInvoice = reqInvoice.getInvoiceD();

			if(Utility.isSet(currInvoice.getSalesTax())){
	    		controlTotalSumInvoice = controlTotalSumInvoice.add(currInvoice.getSalesTax());
	    	}
	    	if(Utility.isSet(currInvoice.getMiscCharges())){
	    		controlTotalSumInvoice = controlTotalSumInvoice.add(currInvoice.getMiscCharges());
	    	}		
	    	if(Utility.isSet(currInvoice.getFreight())){
	    		controlTotalSumInvoice = controlTotalSumInvoice.add(currInvoice.getFreight());
	    	}
	    	
	    	if (Utility.isSet(currInvoice.getDiscounts())){		
				controlTotalSumInvoice = controlTotalSumInvoice.add(currInvoice.getDiscounts());
			}	    	
	    	
	    	currInvoice.setInvoiceDistSourceCd(RefCodeNames.INVOICE_DIST_SOURCE_CD.EDI);
	    	
	    	for (Object o : reqInvoice.getInvoiceDetailDV()){
	    		InvoiceDistDetailData currDetail = (InvoiceDistDetailData) o;
	    		BigDecimal quantity=new BigDecimal(currDetail.getDistItemQuantity()).abs();
	    		BigDecimal lineTotal = currDetail.getItemReceivedCost() == null ? new BigDecimal(0) : currDetail.getItemReceivedCost().multiply(quantity);
	    		controlTotalSumInvoice= controlTotalSumInvoice.add(lineTotal);
	    	}
	    	
			processTransaction();
			createTransactionObject();
		}
    }
    
    protected void processTransaction() throws Exception {
    	boolean currInvoiceIsACredit = Utility.isEqual(invoiceTypeMap.get(reqInvoice.getInvoiceD().getInvoiceNum()), "CR");  	
		controlTotalSumInvoice = currInvoiceIsACredit ? controlTotalSumInvoice.abs().negate() : controlTotalSumInvoice;
		
		reqInvoice.setControlTotalSum(controlTotalSumInvoice);
		reqInvoice.setMatchPoNumType(RefCodeNames.MATCH_PO_NUM_TYPE_CD.STORE_ERP_PO_NUM);
		OrderData orderD = null;
		InvoiceDistData currInvoice = reqInvoice.getInvoiceD();
		orderD = getTranslator().getOrderDataByPoNum(currInvoice.getErpPoNum(), null, reqInvoice.getDistributorAccountRefNum(), reqInvoice.getMatchPoNumType());		
		
		int orderId = 0;
		int storeId = 0;
		String erpSystemCd = null;
		if(orderD!=null) {
			orderId = orderD.getOrderId();
			storeId = orderD.getStoreId();
			erpSystemCd = orderD.getErpSystemCd();
			currInvoice.setOrderId(orderId);
		}
		getTransactionObject().setOrderId(orderId);
		getTransactionObject().setKeyString("ErpPoNum: " + currInvoice.getErpPoNum()
				+ ", InvoiceNum: " + currInvoice.getInvoiceNum()
				+ ", DistShipmentNum: " + currInvoice.getDistShipmentNum());
		currInvoice.setStoreId(storeId);
		currInvoice.setErpSystemCd(erpSystemCd);
		//If this was a credit go through and setup the monitary and quantity values such that it is standardized,
		//qty always negative, and subtotal always negative
		if(currInvoiceIsACredit){
			reqInvoice.setInvoiceAsCredit();
		}
		appendIntegrationRequest(reqInvoice);
	}
}
