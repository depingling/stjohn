/*
 * OutboundHHSInvoice.java
 *
 * Created on November 15, 2005, 2:45 PM
 *
 * Copyright November 15, 2005 Cleanwise, Inc.
 */

package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.InvoiceAbstractionDetailView;
import com.cleanwise.service.api.value.InvoiceAbstractionView;
import com.cleanwise.service.api.value.InvoiceCustDetailRequestData;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;


/**
 *
 * @author bstevens
 */
public class OutboundHHSInvoice extends InterchangeOutboundSuper implements OutboundTransaction{
	protected Logger log = Logger.getLogger(this.getClass());
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
	private boolean processingSummaryRow;
	private String costCenterTok;
	private InvoiceAbstractionView currInvoice;
	private boolean isACredit;
	private CostCenterGroup currCostCenterGroup;

	BigDecimal curTotalCheck;

	public void buildInterchangeHeader()
	throws Exception
	{
		super.buildInterchangeHeader();
		List headerRow = new ArrayList();
		headerRow.add("Doc Type");
		headerRow.add("PO");
		headerRow.add("Description");
		headerRow.add("Vendor");
		headerRow.add("Doc Date");
		headerRow.add("Doc Number");
		headerRow.add("Purchases Amount");
		headerRow.add("Account");
		headerRow.add("Dist Type");
		headerRow.add("Debit");
		headerRow.add("Credit");
		writeRowToOutputStream(headerRow);
		translator.writeOutputStream("\r\n");
	}

	public void buildTransactionContent()
	throws Exception {
		currInvoice = currOutboundReq.getInvoiceData();
		log.info("Processing invoice: "+currInvoice.getInvoiceNum());

		HashMap costCenterMap = new HashMap();
		processingSummaryRow = true;
		buildADetailRow();

		//break out the different cost centers
		List items = currOutboundReq.getInvoiceDetailDV();
		Iterator itmIt = items.iterator();

		while(itmIt.hasNext()){
			InvoiceCustDetailRequestData  oitem = (InvoiceCustDetailRequestData) itmIt.next();
			InvoiceAbstractionDetailView item = oitem.getInvoiceDetailD();
			if(item.isInvoiceDist()){
				String costCenterTok = item.getInvoiceDistDetailData().getErpAccountCode();
				if(Utility.isSet(costCenterTok)){
					costCenterTok = getFirstWord(costCenterTok);
				}else{
					costCenterTok = "700"; //miscelanious
				}
				costCenterTok = costCenterTok.trim();
				//move 300 to 400 for non taxable chemicals
				if("300".equals(costCenterTok)){
					if(!Utility.isTaxableOrderItem(oitem.getOrderItemD())){
						costCenterTok = "400";
					}
				}
				CostCenterGroup grp = getCostCenterGroup(costCenterTok, costCenterMap);
				grp.addItem(item);
			}
		}
		//if there is sales tax creat a row for it
		if(currInvoice.getSalesTax() != null && currInvoice.getSalesTax().compareTo(new BigDecimal(0.00))!=0){
			CostCenterGroup grp = getCostCenterGroup("800", costCenterMap);
			grp.otherAmt = currInvoice.getSalesTax();
		}

		//if there is freight create/add to a row for it
		BigDecimal frt = Utility.addAmt(currInvoice.getFreight(), currInvoice.getMiscCharges());
		if(frt.compareTo(new BigDecimal(0.00))!=0){
			CostCenterGroup grp = getCostCenterGroup("700", costCenterMap);
			grp.otherAmt = frt;
		}

		curTotalCheck = new BigDecimal(0);
		processingSummaryRow = false;
		//iterate through the cost centers and add a row for each entry
		Iterator ccmIt =costCenterMap.keySet().iterator();
		while(ccmIt.hasNext()){
			currCostCenterGroup = (CostCenterGroup) costCenterMap.get(ccmIt.next());
			costCenterTok = currCostCenterGroup.costCenterKey;
			buildADetailRow();
			curTotalCheck = Utility.addAmt(curTotalCheck,getUnFormattedCostCenterTotal());
		}
		if(curTotalCheck.compareTo(currInvoice.getNetDue()) != 0){
			throw new Exception("invoice number: "+currInvoice.getInvoiceNum()+" Does not balance!! (calc cost center,invoice in db) ("+curTotalCheck+","+currInvoice.getNetDue()+")");
		}
		currInvoice.markAcknowledged();
		appendIntegrationRequest(currInvoice);
	}

	private String getFirstWord(String str){
		if(str == null){
			return "";
		}
		int idx = str.indexOf(' ');
		int idx2 = str.indexOf('-');
		if(idx > 0 && idx2 > 0){
			idx = Utility.min(idx, idx2);
		}else if(idx2 > 0){
			idx = idx2;
		}
		if(idx >=  0){
			str = str.substring(0,idx);
		}
		return str;
	}

	/**
	 *Retrieves the CostCenterGroup from the mapping, adding it if it does not already exist
	 */
	private CostCenterGroup getCostCenterGroup(String costCenterTok, HashMap costCenterMap){
		CostCenterGroup grp = (CostCenterGroup) costCenterMap.get(costCenterTok);
		if(grp == null){
			grp = new CostCenterGroup(costCenterTok);
			costCenterMap.put(costCenterTok, grp);
		}
		return grp;
	}

	/**
	 *Returns "" if the passed in string is null, otherwise returns the passed in string
	 */
	private String nullWrt(String s){
		if(s == null){
			return "";
		}
		return s;
	}

	/**
	 *Does the work of assembeling a row for the output report
	 */
	private void buildADetailRow() throws IOException{
		ArrayList aRow = new ArrayList();
		if(RefCodeNames.INVOICE_TYPE_CD.CR.equals(currInvoice.getInvoiceType())){
			aRow.add("5");
			isACredit = true;
		}else{
			aRow.add("1");
			isACredit = false;
		}
		aRow.add(nullWrt(getPoNumber()));

		aRow.add(nullWrt(currOutboundReq.getDistributorName()));
		aRow.add(nullWrt(getFirstWord(currOutboundReq.getDistributorName())));
		if(currInvoice.getInvoiceDate() != null){
			aRow.add(dateFormatter.format(currInvoice.getInvoiceDate()));
		}else{
			aRow.add("");
		}
		aRow.add(nullWrt(currInvoice.getInvoiceNum()));
		aRow.add(nullWrt(getFormattedNumber(currInvoice.getNetDue())));
		aRow.add(nullWrt(getAccount()));
		aRow.add(nullWrt(getDistType()));
		aRow.add(nullWrt(getFormattedDebit()));
		aRow.add(nullWrt(getFormattedCredit()));

		writeRowToOutputStream(aRow);
		translator.writeOutputStream("\r\n");
	}


	/**
	 *Returns the invoice total
	 */
	private String getFormattedInvoiceTotal(){
		BigDecimal theNumber = currInvoice.getNetDue();
		return getFormattedNumber(theNumber);
	}

	private String getFormattedNumber(BigDecimal theNumber){
		theNumber = theNumber.abs();
		return theNumber.toString();
	}

	/**
	 *Returns the subtotal of the current cost center items/other amounts etc.
	 */
	private BigDecimal getUnFormattedCostCenterTotal(){
		BigDecimal theNumber = null;
		if(currCostCenterGroup.items != null){
			Iterator it = currCostCenterGroup.items.iterator();
			while(it.hasNext()){
				InvoiceAbstractionDetailView det = (InvoiceAbstractionDetailView) it.next();
				theNumber = Utility.addAmt(theNumber, det.getLineTotal());
			}
		}
		if(currCostCenterGroup.otherAmt != null){
			theNumber = Utility.addAmt(theNumber, currCostCenterGroup.otherAmt);
		}
		return theNumber;
	}     

	/**
	 *Returns the subtotal of the current cost center items/other amounts etc.
	 *as a formatted string.
	 */
	private String getFormattedCostCenterTotal(){
		return getFormattedNumber(getUnFormattedCostCenterTotal());
	}

	/**the credits and debits function differently if this is a invoice versus credit...they esentially reverse themselves*/
	private String getFormattedDebit(){
		if(isACredit){
			if(processingSummaryRow){
				return getFormattedInvoiceTotal();
			}else{
				return "";
			}
		}else{
			if(processingSummaryRow){
				return "";
			}else{
				return getFormattedCostCenterTotal();
			}
		}
	}

	/**the credits and debits function differently if this is a invoice versus credit...they esentially reverse themselves*/
	private String getFormattedCredit(){
		if(isACredit){
			if(processingSummaryRow){
				return "";
			}else{
				return getFormattedCostCenterTotal();
			}
		}else{
			if(processingSummaryRow){
				return getFormattedInvoiceTotal();
			}else{
				return "";
			}
		}

	}

	/**
	 *Returns the dist type based off wheather this is a summary row or not
	 */
	private String getDistType(){
		if(processingSummaryRow){
			return "2";
		}else{
			return "6";
		}
	}

	/**
	 *Returns the account number for the current request that is being processed as defined by the following rules:
	 *This is a number in the following format: 0-xxx-yyyy-zzz 
1.       0 will always be the first number

2.       xxx will be our 3 digit PC (profit center) number

3.       yyyy this will either be 2020 or 5030

4.       zzz will be one of the following:

100 - paper 
200 - liners 
300 - cleaners (taxable chemicals) 
350 - laundry chemicals (detergent) 
400 - waxes (non-tax chemicals) 
500 - mops 
600 - pads 
700 - miscellaneous 
800 - sales tax 
900 - hand soap 
(NOTE: if yyyy = 2020 then zzz will always be 100.  If yyyy = 5030 then zzz will be one of the above numbers to indicate the proper category) 

	 */
	private String getAccount(){
		StringBuffer acct = new StringBuffer("0");
		acct.append("-");  //----------------Seg break---------------
		String pcNum;
		if(this.processingSummaryRow){
			pcNum = "000";
		}else{
			pcNum = Utility.getPropertyValue(currOutboundReq.getSiteProperties(), RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
			if(!Utility.isSet(pcNum)){
				pcNum = Utility.getEDIToken(this.currOutboundReq.getInvoiceData().getShippingName());
			}
			if(!Utility.isSet(pcNum)){
				pcNum = "000"; //or error out?
			}
			if(pcNum.length() > 3){
				pcNum = pcNum.substring(pcNum.length() - 3);
			}
			pcNum = Utility.padLeft(pcNum, '0', 3);
		}
		acct.append(pcNum);
		acct.append("-");  //----------------Seg break---------------
		if(processingSummaryRow){
			acct.append("2020");
		}else{
			acct.append("5030");
		}
		acct.append("-");  //----------------Seg break---------------
		if(processingSummaryRow){
			acct.append("100");
		}else{
			acct.append(costCenterTok);
		}
		return acct.toString();
	}

	/**
	 *Returns the po number for the current request that is being processed
	 */
	private String getPoNumber(){
		if(currOutboundReq.getOrderD() != null && !"N/A".equals(currOutboundReq.getOrderD().getRequestPoNum()) && Utility.isSet(currOutboundReq.getOrderD().getRequestPoNum())){
			return currOutboundReq.getOrderD().getRequestPoNum();
		}
		return currOutboundReq.getInvoiceData().getErpPoNum();
	}

	public String getFileExtension()throws Exception{
		return ".txt";
	}

	private class CostCenterGroup{
		String costCenterKey;
		List items;
		BigDecimal otherAmt;

		private CostCenterGroup(String pCostCenterKey){
			costCenterKey = pCostCenterKey;
		}

		private void addItem(InvoiceAbstractionDetailView pItem){
			if(items == null){
				items = new ArrayList();
			}
			items.add(pItem);
		}
	}
}
