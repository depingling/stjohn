package com.cleanwise.service.apps.dataexchange;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.OrderCreditCardData;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.TradingPartnerData;
import com.cleanwise.service.api.value.OrderAddOnChargeData;
import com.cleanwise.service.api.value.OrderFreightData;

public class OutboundGenericOrderPipeDelimitedV2 extends InterchangeOutboundSuper implements OutboundTransaction{
	private static final Logger log = Logger.getLogger(OutboundGenericOrderPipeDelimitedV2.class);
	private static SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddhhmmss"); //used for file timestampping
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd"); //used for Date conversion of data in file
	private static final String SEPERATOR_STR = "|"; //the separator used for the file
	private static final String QUOTE_STR = "\""; //the quote used for the file
	
	private static final String VERSION = "2.0";  //the version number
	private static final BigDecimal ZERO = new BigDecimal("0.00");

	/**
	 * No header is included with the file.
	 */
	public void buildInterchangeHeader() throws Exception {
		super.buildInterchangeHeader();
	}
	
	/**
	 * Returns the separator used
	 */
	private String getSeperator(){
		return SEPERATOR_STR;
	}
	/**
	 * Returns the quote string used
	 */
	private String getQuoteChar(){
		return QUOTE_STR;
	}
	
	
	/**
	 * Formats the orders into the specified format.
	 * <pre>
     * 1|1234|1/10/2008|10.50|Deliver to back|.54|2.00|0|0|PROCESS||567ABC|123 Main St|Apt 1|||Marlborough|MA|01752|1|2|EA|7.96|7447A|”24\” Large Duster”|5678-Supplies
     * </pre>
	 */
	public void buildTransaction()
    throws Exception {
		log.info("buildTransactionContent. START");
		OrderData currOrder = currOutboundReq.getOrderD();
        OrderItemDataVector orderItems = currOutboundReq.getOrderItemDV();
        if(currOrder == null){
        	throw new NullPointerException("The order was null");
        }
        if(orderItems == null){
        	throw new NullPointerException("The order items list was null");
        }
        if(orderItems.size() == 0){
        	throw new RuntimeException("This order doesn't have any items.  This is currently not supported.");
        }
        
        NumberFormat numberFormatter = null;
        if(Utility.isSet(currOrder.getLocaleCd())){
        	try{
	        	Locale loc = Utility.parseLocaleCode(currOrder.getLocaleCd());
	        	numberFormatter = NumberFormat.getNumberInstance(loc);
        	}catch(Exception e){
        		log.error("NON FATAL ERROR Could not create number formatter from locale.",e);
        	}
        }
        if(numberFormatter == null){
        	log.info("Using default US number formatting");
        	numberFormatter = NumberFormat.getNumberInstance(Locale.US);
        }
        TradingPartnerData partner = getTranslator().getPartner();
        java.util.Iterator<OrderItemData> it = orderItems.iterator();
        while(it.hasNext()){
        	OrderItemData currOrderItem = it.next();
        	List<String> record = new ArrayList<String>();
        	record.add(VERSION); //Version
        	String poOutFin = Utility.getOutboundPONumber(currOutboundReq.getStoreType(),currOrder,partner,currOutboundReq.getPurchaseOrderD().getErpPoNum());
        	record.add(currOutboundReq.getPurchaseOrderD().getOutboundPoNum()); // PO Number
        	record.add(currOutboundReq.getPurchaseOrderD().getErpPoNum()); //Trans Ref Number
        	
        	record.add(dateFormat.format(currOutboundReq.getPurchaseOrderD().getPoDate()));
        	BigDecimal total = currOutboundReq.getPurchaseOrderD().getPurchaseOrderTotal();
        	total = Utility.addAmt(total, currOutboundReq.getPurchaseOrderD().getTaxTotal());
        	record.add(numberFormatter.format(total.doubleValue()));
        	
        	String msg = "";
    		if(Utility.isSetForDisplay(currOrder.getComments())){
    			msg = msg + " "+currOrder.getComments();
        	}
            
            PropertyData pd = Utility.getProperty(currOutboundReq.getSiteProperties(),RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG);
            String siteComments = null;
            if(pd != null){
                siteComments = pd.getValue();
            }
            if (!Utility.isSet(msg)){
                msg = siteComments;
            }else{
                if(Utility.isSet(siteComments)){
                    msg = msg + ". " + siteComments;
                }
            }
            
        	record.add(msg);
        	record.add(numberFormatter.format(currOutboundReq.getPurchaseOrderD().getTaxTotal()));        	
        	
           	record.add(formatBigDecimal(numberFormatter,currOutboundReq.getFuelSurcharge())); //fuel surcharge
            record.add(formatBigDecimal(numberFormatter,ZERO)); //handling charge temporary = 0 until clw_order_freight table is changed
           	if(currOutboundReq.getFreightInfo() != null){ //freight or handling charge => freight field
                BigDecimal freightAmt = currOutboundReq.getFreightInfo().getAmount();
        	    log.info("Freight or Handling Amount = " + freightAmt);
                record.add(formatBigDecimal(numberFormatter,freightAmt)); //BigDecimal
         	} else {
                record.add(formatBigDecimal(numberFormatter,ZERO)); //freight or handling charge = 0
         	}           	 
           	
        	//record.add(numberFormatter.format(ZERO)); //fuel surcharge: old
        	//record.add(formatBigDecimal(numberFormatter,ZERO)); //handling charge: old
        	//record.add(formatBigDecimal(numberFormatter,ZERO)); //freight charge: old
        	record.add(formatBigDecimal(numberFormatter,currOutboundReq.getDiscount()));  //discount: correct
        	record.add("PROCESS"); //order type code
        	record.add(currOutboundReq.getDistributorCompanyCode());  //Vendor Code
        	record.add(Utility.getIdForShipTo(partner, currOutboundReq)); //Site Number 	Reference to ship to (Store Number, Customer Site Id, etc). May repeat.
        	record.add(currOrder.getOrderSiteName()); //Ship To Address 1 	Shipping Information. May repeat.
        	record.add(currOutboundReq.getShipAddr().getAddress1()); //Ship To Address 2 	Shipping Information (optional) May repeat.
        	record.add(currOutboundReq.getShipAddr().getAddress2()); //Ship To Address 3 	Shipping Information (optional) May repeat.
        	record.add(currOutboundReq.getShipAddr().getAddress3()); //Ship To Address 4 	Shipping Information (optional) May repeat.
        	record.add(currOutboundReq.getShipAddr().getCity()); //Ship To City 	Shipping Information (optional) May repeat.
        	record.add(currOutboundReq.getShipAddr().getStateProvinceCd()); //Ship To State Province Code 	Shipping Information (optional) May repeat.
        	record.add(currOutboundReq.getShipAddr().getPostalCode()); //Ship To Postal Code 	Shipping Information (optional) May repeat.
        	record.add(Integer.toString(currOrderItem.getErpPoLineNum()));//Order Line Number	Line number on the order.
        	record.add(Integer.toString(currOrderItem.getDistItemQuantity()));//Order Qty 	The qty to be ordered.
        	String uom = currOrderItem.getDistItemUom();
        	if(!Utility.isSet(uom)){
        		uom = currOrderItem.getItemUom();
        	}
        	record.add(uom);//Uom 	UOM of line item, helpful in tracking down problems (optional).
        	String pack = currOrderItem.getDistItemPack();
        	if(!Utility.isSet(pack)){
        		pack = currOrderItem.getItemPack();
        	}
        	record.add(pack);//Pack count of line item, helpful in tracking down problems (optional).
        	BigDecimal qty = new BigDecimal(currOrderItem.getDistItemQuantity());
        	BigDecimal amt = currOrderItem.getDistItemCost();
        	amt = amt.multiply(qty);
        	String lineAmt = numberFormatter.format(amt);
        	record.add(lineAmt);//Line Amt 	Total line item amount of invoice (2 case invoiced at $5 each should be $10).
        	record.add(currOrderItem.getDistItemSkuNum());//Distributor SKU 	Distributor or Customer part number.
        	record.add(currOrderItem.getItemShortDesc());//Item Description 	Description of line.
 
        	//Tax exempt code (TAX, EXEMPT)
        	if (RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE.equals(currOrderItem.getSaleTypeCd())) {
        		record.add("EXEMPT");
        	}else{
        		record.add("TAX");
        	}
        	
        	/*
        	//Tax exempt code (TAX, EXEMPT)
        	if(Utility.isTrue(currOrderItem.getTaxExempt())){
        		record.add("EXEMPT");
        	}else{
        		record.add("TAX");
        	}
        	*/
        	String costCenterName="";
        	if(currOutboundReq.getCostCenters() != null && currOrderItem.getCostCenterId() > 0){
        		costCenterName = (String) currOutboundReq.getCostCenters().get(Integer.valueOf(currOrderItem.getCostCenterId()));
        	}
        	record.add(costCenterName);//Customer Cost Center Code 	Cost center of end use customer (Customers assigned budget number).
        	if(showCC()){
        		OrderCreditCardData occd = currOutboundReq.getOrderCreditCard();
        		if(occd != null){
        			record.add(occd.getEncryptedCreditCardNumber());
        			record.add(occd.getEncryptionAlgorithm());
        			record.add(occd.getCreditCardType());
        			record.add(Utility.padLeft((occd.getExpMonth()+1), '0', 2));
        			record.add(Integer.toString(occd.getExpYear()));
        			record.add(occd.getName());
        			record.add(occd.getAddress1());
        			record.add(occd.getAddress2());
        			record.add(occd.getAddress3());
        			record.add(occd.getAddress4());
        			record.add(occd.getCity());
        			record.add(occd.getStateProvinceCd());
        			record.add(occd.getPostalCode());
        		}
        	}
        	String aRow = toSeperatedString(record,getQuoteChar(),getSeperator());
        	getTranslator().writeOutputStream(aRow);
        	getTranslator().writeOutputStream("\n");
        
        	currOrderItem.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
        	appendIntegrationRequest(currOrderItem);
        }
        currOutboundReq.getPurchaseOrderD().setPurchaseOrderStatusCd(RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR);
        appendIntegrationRequest(currOutboundReq.getPurchaseOrderD());
        
        
        
        log.info("buildTransactionContent. END");
	}
	
	private String formatBigDecimal(NumberFormat numberFormatter, BigDecimal bd){
		if(bd == null){
			bd = ZERO;
		}
		return numberFormatter.format(bd);
	}
	
	private static List<Character> specialChars = new ArrayList<Character>();
	static{
		specialChars.add('\n');
		specialChars.add('\r');
	}
	
	/**
	 * Converts the specified list into a seperated list according to the seperator.
	 * It will quote anything with the seperator in it, and will escape the quotes with a \
	 * ex:
	 * 24” Large, Duster
	 * becomes:
	 * "24/” Large, Duster"
	 */
	private String toSeperatedString(List<String> pList, String quote, String seperator){
		StringBuffer theLine = new StringBuffer();
		Iterator<String> it = pList.iterator();
		while(it.hasNext()){
			String aRecord = it.next();
			if(aRecord == null){
				aRecord = "";
			}
			Iterator<Character> itSpecalChars = specialChars.iterator();
			while (itSpecalChars.hasNext()) {
				Character specialCharStr = (Character) itSpecalChars.next();
				aRecord = aRecord.replace(specialCharStr, ' ');
			}
			if(aRecord.contains(getSeperator())){
				if(aRecord.contains(quote)){
					//escape the quote char
					aRecord = aRecord.replace(quote, "\\"+quote);
				}
				theLine.append(quote);
				theLine.append(aRecord);
				theLine.append(quote);
			}else{
				theLine.append(aRecord);
			}
			if(it.hasNext()){
				theLine.append(seperator);
			}
		}
		return theLine.toString();
	}
	
	/**
	 * From the specification:
	 * 
	 * "File Naming Convention:
	 * Our standard would be the word “order” with a time stamp on it.  For example:
	 * order20081013103744.txt"
	 */
	public String getFileName()throws Exception{
		String timestamp = timeStampFormat.format(new Date());
		return "order"+timestamp+".txt";
	}
	
	boolean showCC(){
		return false;
	}
}
