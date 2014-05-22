/*
 * InboundInvoiceFlatFile.java
 *
 * Created on March 1, 2003, 9:35 PM
 */

package com.cleanwise.service.apps.dataexchange;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import com.cleanwise.service.api.value.InvoiceRequestData;


/**
 * Handles inbound invoices in a flat file format and does the mapping between
 * invoice and invoice detail so that the lat file sends only the apropriate
 * number of invoices instead of 1 per line
 * @author  bstevens
 */
public class InboundInvoiceFlatFile extends InboundFlatFile{
	protected Logger log = Logger.getLogger(this.getClass());
	ArrayList parsedObjects = new ArrayList(); 
	
	/**
	 * Overidden as we will be doing a whole bunch of post processing work
	 * and recreating this list.  @see addIntegrationRequestSuper.
	 */
	protected void addIntegrationRequest(Object pRequest){
		parsedObjects.add(pRequest);
	}
    
    protected void doPostProcessing(){
    	//sort the list by entity key
		Collections.sort(parsedObjects,INOVICE_SORT);
		log.info("Sorted");
		//loop through
		String lastInvoiceNum=null;
		InvoiceRequestData invoice = null;
		Iterator it = parsedObjects.iterator();
		while(it.hasNext()){
			InboundInvoiceFlatFileData flat =(InboundInvoiceFlatFileData) it.next();
			if(flat.getInvoice() == null){
				//Can't do anything with it, skip it
				continue;
			}
			if(!flat.getInvoice().equals(lastInvoiceNum)){
				lastInvoiceNum = flat.getInvoice();
				invoice = initializeInvoiceRequest(flat);
				addIntegrationRequestSuper(invoice); //add requst to really be processed
			}
            
			addInvoiceItem(invoice,flat);
		}
        
        
    }

    
    /**
	 * Calls the super call implementation of addIntegrationRequest
	 */
	private void addIntegrationRequestSuper(Object pRequest){
		super.addIntegrationRequest(pRequest);
	}
    
	/**
	 * Creates an empty invoice request intialized with the data from the single line invoice
	 */
    private InvoiceRequestData initializeInvoiceRequest(InboundInvoiceFlatFileData flatInvoice){
    	InvoiceRequestData invoice = InvoiceRequestData.createValue();
    	invoice.setInvoiceD(InvoiceDistData.createValue());
    	invoice.setInvoiceDetailDV(new InvoiceDistDetailDataVector());
    	if(Utility.isSet(flatInvoice.getTaxHeader())){
    		invoice.getInvoiceD().setSalesTax(flatInvoice.getTaxHeader());
    	}
    	if(Utility.isSet(flatInvoice.getFrtHeader())){
    		invoice.getInvoiceD().setSalesTax(flatInvoice.getFrtHeader());
    	}
    	if(Utility.isSet(flatInvoice.getMiscChargesHeader())){
    		invoice.getInvoiceD().setSalesTax(flatInvoice.getMiscChargesHeader());
    	}
    	
    	invoice.getInvoiceD().setAddBy("System");
    	invoice.getInvoiceD().setErpPoNum(flatInvoice.getPo());
    	
    	invoice.getInvoiceD().setInvoiceDate(flatInvoice.getInvoiceDate());
    	if(invoice.getInvoiceD().getInvoiceDate() == null){
    		invoice.getInvoiceD().setInvoiceDate(new Date());
    	}
    	invoice.getInvoiceD().setInvoiceDistSourceCd(RefCodeNames.INVOICE_DIST_SOURCE_CD.EDI);
    	invoice.getInvoiceD().setInvoiceNum(flatInvoice.getInvoice());
    	return invoice;
    }
    
    /**
     * Adds an item to the invoice
     */
    private void addInvoiceItem(InvoiceRequestData invoice,InboundInvoiceFlatFileData flatInvoice){
    	if(!Utility.isSet(flatInvoice.getDistSku())){
    		log.info("Skipping blank line");
    		return;
    	}
    	InvoiceDistDetailData aLine = InvoiceDistDetailData.createValue();
    	aLine.setAddBy("system");
    	aLine.setDistItemQuantity(flatInvoice.getQty());
    	aLine.setDistItemSkuNum(flatInvoice.getDistSku());
    	aLine.setDistItemShortDesc(flatInvoice.getItemDesc());
    	aLine.setItemReceivedCost(flatInvoice.getItemCost());
    	BigDecimal tax = Utility.addAmt(invoice.getInvoiceD().getSalesTax(),flatInvoice.getTaxLineItem());
    	BigDecimal frt = Utility.addAmt(invoice.getInvoiceD().getFreight(),flatInvoice.getFrtLineItem());
    	BigDecimal misc = Utility.addAmt(invoice.getInvoiceD().getMiscCharges(),flatInvoice.getMiscChargesLineItem());
    	invoice.getInvoiceD().setSalesTax(tax);
    	invoice.getInvoiceD().setFreight(frt);
    	invoice.getInvoiceD().setMiscCharges(misc);
    	log.info("Adding item: "+aLine.getDistItemSkuNum());
    	invoice.getInvoiceDetailDV().add(aLine);
    	//XXX Hard Coded anything starting with a "C" is a credit for the time being
        //this may need to be more intelligent.
        if(flatInvoice.getInvoiceTypeCd() != null && flatInvoice.getInvoiceTypeCd().startsWith("C")){
            //invoice is a credit
            log.info("Setting credit");
            invoice.setInvoiceAsCredit();
        }
    }
    
	static final Comparator INOVICE_SORT = new Comparator() {
	    public int compare(Object o1, Object o2)
	    {
		String s1 = ((InboundInvoiceFlatFileData)o1).getInvoice();
		String s2 = ((InboundInvoiceFlatFileData)o2).getInvoice();
		if(s1 == null && s2 == null){
			return 0;
		}else if(s1 == null){
			s1 = "";
		}else if(s2 == null){
			s2 = "";
		}
		
		return s1.compareTo(s2);
	    }
	};
    
    public static class InboundInvoiceFlatFileData{
    	//"0120022600","#305254-00  ","3M-74N              ",     1,     1,   19.49,     19.49,    1.12,     20.61,"413664"
    	String invoice;
    	String po;
    	String distSku;
    	String itemDesc;
    	int line;
    	int qty;
    	BigDecimal itemCost;
    	BigDecimal totalItemCost;
    	BigDecimal taxLineItem;
    	BigDecimal taxHeader;
    	BigDecimal frtLineItem;
    	BigDecimal frtHeader;
    	BigDecimal miscChargesLineItem;
    	BigDecimal miscChargesHeader;
    	Date invoiceDate;
    	String invoiceTypeCd;
        
		public Date getInvoiceDate() {
			return invoiceDate;
		}
		public void setInvoiceDate(Date invoiceDate) {
			this.invoiceDate = invoiceDate;
		}
		public BigDecimal getItemCost() {
			return itemCost;
		}
		public void setItemCost(BigDecimal itemCost) {
			this.itemCost = itemCost;
		}
		public String getInvoice() {
			return invoice;
		}
		public void setInvoice(String invoice) {
			this.invoice = invoice;
		}
		public String getDistSku() {
			return distSku;
		}
		public void setDistSku(String item) {
			this.distSku = item;
		}
		public int getLine() {
			return line;
		}
		public void setLine(int line) {
			this.line = line;
		}
		public BigDecimal getTotalItemCost() {
			return totalItemCost;
		}
		public void setTotalItemCost(BigDecimal totalItemCost) {
			this.totalItemCost = totalItemCost;
		}
		public String getPo() {
			return po;
		}
		public void setPo(String po) {
			this.po = po;
		}
		public int getQty() {
			return qty;
		}
		public void setQty(int qty) {
			this.qty = qty;
		}
		public BigDecimal getTaxHeader() {
			return taxHeader;
		}
		public void setTaxHeader(BigDecimal taxHeader) {
			this.taxHeader = taxHeader;
		}
		public BigDecimal getTaxLineItem() {
			return taxLineItem;
		}
		public void setTaxLineItem(BigDecimal taxLineItem) {
			this.taxLineItem = taxLineItem;
		}
		public String getItemDesc() {
			return itemDesc;
		}
		public void setItemDesc(String itemDesc) {
			this.itemDesc = itemDesc;
		}
		public BigDecimal getFrtHeader() {
			return frtHeader;
		}
		public void setFrtHeader(BigDecimal frtHeader) {
			this.frtHeader = frtHeader;
		}
		public BigDecimal getFrtLineItem() {
			return frtLineItem;
		}
		public void setFrtLineItem(BigDecimal frtLineItem) {
			this.frtLineItem = frtLineItem;
		}
		public BigDecimal getMiscChargesHeader() {
			return miscChargesHeader;
		}
		public void setMiscChargesHeader(BigDecimal miscChargesHeader) {
			this.miscChargesHeader = miscChargesHeader;
		}
		public BigDecimal getMiscChargesLineItem() {
			return miscChargesLineItem;
		}
		public void setMiscChargesLineItem(BigDecimal miscChargesLineItem) {
			this.miscChargesLineItem = miscChargesLineItem;
		}
        public String getInvoiceTypeCd() {
            return invoiceTypeCd;
        }
        public void setInvoiceTypeCd(String invoiceTypeCd) {
            this.invoiceTypeCd = invoiceTypeCd;
        }
    	
    }
}
