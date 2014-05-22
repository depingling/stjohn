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
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.apps.dataexchange.InboundInvoiceFlatFile.InboundInvoiceFlatFileData;


/**
 * Handles inbound invoices in a flat file format and does the mapping between
 * invoice and invoice detail so that the lat file sends only the apropriate
 * number of invoices instead of 1 per line
 * @author  bstevens
 */
public class InboundGenericInvoicePipeDelimitedV1 extends InboundFlatFile{
	protected Logger log = Logger.getLogger(this.getClass());
	ArrayList parsedObjects = new ArrayList(); 

	// Processing variables
	protected InvoiceRequestData reqInvoice = null;
	protected BigDecimal  controlTotalSumInvoice = null;
	protected boolean currInvoiceIsACredit = false;
	
	public InboundGenericInvoicePipeDelimitedV1() {
		super.setSepertorChar('|');
		super.setQuoteChar('"');
	}
	
	/**
	 * Overidden as we will be doing a whole bunch of post processing work
	 * and recreating this list.  @see addIntegrationRequestSuper.
	 */
	protected void addIntegrationRequest(Object pRequest){
		parsedObjects.add(pRequest);
	}
    
    protected void doPostProcessing() throws Exception{
    	//sort the list by entity key
		Collections.sort(parsedObjects,INOVICE_SORT);
		log.info("Sorted");
		//loop through
		String lastInvoiceNum=null;
		Iterator it = parsedObjects.iterator();
		while(it.hasNext()){
			InboundInvoiceData flat =(InboundInvoiceData) it.next();
			if(flat.getInvoiceNum() == null){
				//Can't do anything with it, skip it
				continue;
			}
			if(!flat.getInvoiceNum().equals(lastInvoiceNum)){
				if (lastInvoiceNum != null){// assume the input file can contains multiple invoice and make each invoice a transaction.
					processTransaction();
					createTransactionObject();
				}
				lastInvoiceNum = flat.getInvoiceNum();				
				initializeInvoiceRequest(flat);
			}
			
			addInvoiceItem(flat);
		}
		processTransaction();
    }
    
    /**
	 * Creates an empty invoice request intialized with the data from the single line invoice
	 */
    private InvoiceRequestData initializeInvoiceRequest(InboundInvoiceData flatInvoice){
    	reqInvoice = new InvoiceRequestData();
    	InvoiceDistData currInvoice = InvoiceDistData.createValue();
    	InvoiceDistDetailDataVector currDetails = new InvoiceDistDetailDataVector();
		reqInvoice.setInvoiceD(currInvoice);
		reqInvoice.setInvoiceDetailDV(currDetails);
		reqInvoice.setSkuTypeCd(getTranslator().getPartner().getSkuTypeCd());
		
		currInvoiceIsACredit = false;
		controlTotalSumInvoice=new BigDecimal(0);
		
		
		currInvoice.setInvoiceNum(flatInvoice.getInvoiceNum());
		currInvoice.setErpPoNum(flatInvoice.getPoNum());
		currInvoice.setInvoiceDate(flatInvoice.getInvoiceDate());
    	if(currInvoice.getInvoiceDate() == null){
    		currInvoice.setInvoiceDate(new Date());
    	}
    	currInvoice.setSubTotal(flatInvoice.getInvoiceTotal());
    	if(Utility.isSet(flatInvoice.getTax())){
    		currInvoice.setSalesTax(flatInvoice.getTax());
    		controlTotalSumInvoice = controlTotalSumInvoice.add(currInvoice.getSalesTax());
    	}
    	if(Utility.isSet(flatInvoice.getMiscCharge())){
    		currInvoice.setMiscCharges(flatInvoice.getMiscCharge());
    		controlTotalSumInvoice = controlTotalSumInvoice.add(currInvoice.getMiscCharges());
    	}		
    	if(Utility.isSet(flatInvoice.getFreight())){
    		currInvoice.setFreight(flatInvoice.getFreight());
    		controlTotalSumInvoice = controlTotalSumInvoice.add(currInvoice.getFreight());
    	}
    	
    	if (Utility.isSet(flatInvoice.getDiscount())){			
			currInvoice.setDiscounts(Utility.toNegative(flatInvoice.getDiscount()));
			controlTotalSumInvoice = controlTotalSumInvoice.add(currInvoice.getDiscounts());
		}
    	
    	if (Utility.isEqual(flatInvoice.getInvoiceType(), "CR"))    	
			currInvoiceIsACredit = true;

    	// will not set bill to num and ship to num unless used for matching account or site
		// reqInvoice.setDistributorAccountRefNum(flatInvoice.getBillToNum());
		// flatInvoice.getShipToName();
		currInvoice.setShipToName(flatInvoice.getShipToName());
		currInvoice.setShipToAddress2(flatInvoice.getShipToAddr1());
		currInvoice.setShipToAddress3(flatInvoice.getShipToAddr2());
		currInvoice.setShipToCity(flatInvoice.getShipToCity());
		currInvoice.setShipToState(flatInvoice.getShipToState());
		currInvoice.setShipToPostalCode(flatInvoice.getShipToPostal());
    	
    	currInvoice.setAddBy("System");
    	
    	
    	currInvoice.setInvoiceDistSourceCd(RefCodeNames.INVOICE_DIST_SOURCE_CD.EDI);
    	return reqInvoice;
    }
    
    /**
     * Adds an item to the invoice
     */
    private void addInvoiceItem(InboundInvoiceData flatInvoice){
    	if(!Utility.isSet(flatInvoice.getDistSku())){
    		log.info("Skipping blank line");
    		return;
    	}
    	InvoiceDistDetailData currDetail = InvoiceDistDetailData.createValue();
    	currDetail.setAddBy("system");
    	currDetail.setDistLineNumber(flatInvoice.getLineNum());
    	currDetail.setDistItemQuantity(flatInvoice.getQuantity());
    	currDetail.setDistItemUom(flatInvoice.getUom());
		currDetail.setDistItemPack(flatInvoice.getPack());
		currDetail.setItemReceivedCost(flatInvoice.getItemCost());    	
    	currDetail.setDistItemSkuNum(flatInvoice.getDistSku());
    	currDetail.setInvoiceDistSkuNum(flatInvoice.getDistSku());		
    	currDetail.setDistItemShortDesc(flatInvoice.getItemDesc());
    	log.info("Adding item: "+currDetail.getDistItemSkuNum());   	
    	
		
		BigDecimal quantity=new BigDecimal(currDetail.getDistItemQuantity()).abs();
		BigDecimal lineTotal = currDetail.getItemReceivedCost() == null ? new BigDecimal(0) : currDetail.getItemReceivedCost().multiply(quantity);
		controlTotalSumInvoice= controlTotalSumInvoice.add(lineTotal);
		reqInvoice.getInvoiceDetailDV().add(currDetail);
    }
    
    protected void processTransaction() throws Exception {
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
			//set the details appropriatly
			InvoiceDistDetailDataVector currDetails = reqInvoice.getInvoiceDetailDV();
			Iterator it = currDetails.iterator();
			while(it.hasNext()){
				InvoiceDistDetailData currDetail = (InvoiceDistDetailData)it.next();
				if(currDetail.getDistItemQuantity() > 0){
					currDetail.setDistItemQuantity(-1 * currDetail.getDistItemQuantity());
				}
				if(currDetail.getItemReceivedCost() == null){
					currDetail.setItemReceivedCost(new BigDecimal(0));
				}
				currDetail.setItemReceivedCost(currDetail.getItemReceivedCost().abs());
			}
			//set the header appropriatly
			if(currInvoice.getSubTotal() != null){
				currInvoice.setSubTotal(currInvoice.getSubTotal().abs().negate());
			}
		}
		appendIntegrationRequest(reqInvoice);
	}

    
    
	static final Comparator INOVICE_SORT = new Comparator() {
	    public int compare(Object o1, Object o2)
	    {
		String s1 = ((InboundInvoiceData)o1).getInvoiceNum();
		String s2 = ((InboundInvoiceData)o2).getInvoiceNum();
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
    
    public static class InboundInvoiceData{
    	private String invoiceNum;
    	private String poNum;
    	private Date invoiceDate;
    	private BigDecimal invoiceTotal;
    	private BigDecimal tax;
    	private BigDecimal miscCharge;
    	private BigDecimal freight;
    	private BigDecimal discount;
    	private String invoiceType;
    	private String billToNum;
    	private String shipToNum;    	
    	private String shipToName;
    	private String shipToAddr1;
    	private String shipToAddr2;
    	private String shipToCity;
    	private String shipToState;
    	private String shipToPostal;    	
    	private int lineNum;
    	private int quantity;
    	private String uom;
    	private String pack;
    	private BigDecimal itemCost;
    	private String distSku;
    	private String itemDesc;
		public void setInvoiceNum(String invoiceNum) {
			this.invoiceNum = invoiceNum;
		}
		public String getInvoiceNum() {
			return invoiceNum;
		}
		public void setPoNum(String poNum) {
			this.poNum = poNum;
		}
		public String getPoNum() {
			return poNum;
		}
		public void setInvoiceDate(Date invoiceDate) {
			this.invoiceDate = invoiceDate;
		}
		public Date getInvoiceDate() {
			return invoiceDate;
		}
		public void setInvoiceTotal(BigDecimal invoiceTotal) {
			this.invoiceTotal = invoiceTotal;
		}
		public BigDecimal getInvoiceTotal() {
			return invoiceTotal;
		}
		public void setTax(BigDecimal tax) {
			this.tax = tax;
		}
		public BigDecimal getTax() {
			return tax;
		}
		public void setMiscCharge(BigDecimal miscCharge) {
			this.miscCharge = miscCharge;
		}
		public BigDecimal getMiscCharge() {
			return miscCharge;
		}
		public void setFreight(BigDecimal freight) {
			this.freight = freight;
		}
		public BigDecimal getFreight() {
			return freight;
		}
		public void setDiscount(BigDecimal discount) {
			this.discount = discount;
		}
		public BigDecimal getDiscount() {
			return discount;
		}
		public void setInvoiceType(String invoiceType) {
			this.invoiceType = invoiceType;
		}
		public String getInvoiceType() {
			return invoiceType;
		}
		public void setBillToNum(String billToNum) {
			this.billToNum = billToNum;
		}
		public String getBillToNum() {
			return billToNum;
		}
		public void setShipToNum(String shipToNum) {
			this.shipToNum = shipToNum;
		}
		public String getShipToNum() {
			return shipToNum;
		}
		public void setShipToName(String shipToName) {
			this.shipToName = shipToName;
		}
		public String getShipToName() {
			return shipToName;
		}
		public void setShipToAddr1(String shipToAddr1) {
			this.shipToAddr1 = shipToAddr1;
		}
		public String getShipToAddr1() {
			return shipToAddr1;
		}
		public void setShipToAddr2(String shipToAddr2) {
			this.shipToAddr2 = shipToAddr2;
		}
		public String getShipToAddr2() {
			return shipToAddr2;
		}
		public void setShipToCity(String shipToCity) {
			this.shipToCity = shipToCity;
		}
		public String getShipToCity() {
			return shipToCity;
		}
		public void setShipToState(String shipToState) {
			this.shipToState = shipToState;
		}
		public String getShipToState() {
			return shipToState;
		}
		public void setShipToPostal(String shipToPostal) {
			this.shipToPostal = shipToPostal;
		}
		public String getShipToPostal() {
			return shipToPostal;
		}
		public void setLineNum(int lineNum) {
			this.lineNum = lineNum;
		}
		public int getLineNum() {
			return lineNum;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setUom(String uom) {
			this.uom = uom;
		}
		public String getUom() {
			return uom;
		}
		public void setPack(String pack) {
			this.pack = pack;
		}
		public String getPack() {
			return pack;
		}
		public void setItemCost(BigDecimal itemCost) {
			this.itemCost = itemCost;
		}
		public BigDecimal getItemCost() {
			return itemCost;
		}
		public void setDistSku(String distSku) {
			this.distSku = distSku;
		}
		public String getDistSku() {
			return distSku;
		}
		public void setItemDesc(String itemDesc) {
			this.itemDesc = itemDesc;
		}
		public String getItemDesc() {
			return itemDesc;
		}
    	
    }
}
