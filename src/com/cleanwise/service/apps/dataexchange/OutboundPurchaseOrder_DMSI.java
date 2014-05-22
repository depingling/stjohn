/**
 *
 */
package com.cleanwise.service.apps.dataexchange;

import java.io.BufferedWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.math.BigDecimal;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

/**
 * @author Ssharma
 *
 */
public class OutboundPurchaseOrder_DMSI extends InterchangeOutboundSuper implements OutboundTransaction {
	protected Logger logger = Logger.getLogger(this.getClass());
	private static final String className = "OutboundPurchaseOrder_DMSI";
	private static final String note = "Sent to Customer System";

	private StringBuffer log = new StringBuffer();
	private static SimpleDateFormat poDateFormat = new SimpleDateFormat(
	"MM/dd/yyyy");

    BufferedWriter mOutput;
    private String mDoc;

    private OutboundEDIRequestData req;

    public OutboundPurchaseOrder_DMSI(){
    	seperateFileForEachOutboundOrder = false;
    }


    /*public void buildDocumentHeader(TradingProfileData pProfile)throws Exception{

        ArrayList headerRow = new ArrayList();
        headerRow.add("JDE Num");
        headerRow.add("Ship To");
        headerRow.add("Ship To Add1");
        headerRow.add("Ship To Add2");
        headerRow.add("Ship To Add3");
        headerRow.add("City");
        headerRow.add("State");
        headerRow.add("Zipcode");
        headerRow.add("Vendor num");
        headerRow.add("po num");
        headerRow.add("PO Date");
        headerRow.add("PO line num");
        headerRow.add("Vendor Item num");
        headerRow.add("Qty ");
        headerRow.add("UOM ");
        headerRow.add("Price/unit ");
        headerRow.add("Line Total ");

        writeRowToOutputStream(headerRow);
    }
*/


    /**
     * Where the data is actually written to the output stream
     */

    public void buildTransactionContent() throws Exception {

			mDoc = "";

			req = (OutboundEDIRequestData) currOutboundReq;
            getTransactionObject().setKeyString("Order id: "+ req.getOrderD().getOrderId());
            PropertyDataVector sitePropDV = req.getSiteProperties();
            String siteReferenceNumber = "";
            String siteComment = "";
            String custComment = "";
            String deliveryInstruct = "";
            if(sitePropDV != null) {
                for(Iterator propIt=sitePropDV.iterator(); propIt.hasNext();) {
                    PropertyData propD = (PropertyData) propIt.next();
                    if(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER
                                  .equalsIgnoreCase(propD.getShortDesc())) {
                    	siteReferenceNumber = propD.getValue();

                    }
                    if(RefCodeNames.PROPERTY_TYPE_CD.SITE_COMMENTS
                    		.equalsIgnoreCase(propD.getShortDesc())) {
                    	siteComment = propD.getValue();

                    }
                }
            }

            /*OrderPropertyDataVector orderPropDV = req.getOrderPropertyDV();
            if(orderPropDV != null){
            	for(Iterator opropIt=orderPropDV.iterator(); opropIt.hasNext();) {
            		PropertyData propD = (PropertyData) opropIt.next();
            		if(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS.equals(propD.getShortDesc())){
            			custComment = propD.getValue();
            		}
            	}
            }*/
            OrderData orderD = req.getOrderD();

            custComment = orderD.getComments();
            if(!(custComment==null || siteComment==null)){
            	deliveryInstruct= custComment+siteComment;
            }
            logger.info("Delivery Instructions: "+deliveryInstruct);
            String del1="";
            String del2="";
            int l = deliveryInstruct.length();
            if(l != 0 && deliveryInstruct != null){
            	if(l < 30){
            		del1 = deliveryInstruct;
            	}else if (l > 60 ){
            		del1 = deliveryInstruct.substring(0,30);
            		del2 = deliveryInstruct.substring(30,60);

            	}else{
            		del1 = deliveryInstruct.substring(0,30);
            		del2 = deliveryInstruct.substring(30,l);
            	}
            }

            //Create new property to indicate order was sent to customer
        	OrderPropertyData OPD = OrderPropertyData.createValue();
        	OPD.setOrderId(orderD.getOrderId());
            OPD.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_SENT_TO_EXTERNAL_SYS);
            OPD.setValue(note);
            OPD.setOrderPropertyStatusCd("ACTIVE");
            OPD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_SENT_TO_EXTERNAL_SYS);
            appendIntegrationRequest(OPD);

            String contactName = orderD.getOrderContactName();
            String contactNum = orderD.getOrderContactPhoneNum();
            String contactEmail = orderD.getOrderContactEmail();

            OrderAddressData addD = req.getShipAddr();
            String shipToName = addD.getShortDesc();
            String add1 = addD.getAddress1();
            String add2 = addD.getAddress2();
            String add3 = addD.getAddress3();
            String city = addD.getCity();
            String state = addD.getStateProvinceCd();
            String zip = addD.getPostalCode();

            PurchaseOrderData purchaseOrderD = req.getPurchaseOrderD();
            Date poDate = purchaseOrderD.getPoDate();
            String poDateS = (poDate!=null)? poDateFormat.format(poDate):"";

            String erpPoNum = Utility.strNN(purchaseOrderD.getOutboundPoNum());

            int lineNum = 1;
            for(Iterator lineIt = req.getOrderItemDV().iterator(); lineIt.hasNext(); lineNum++){

            	OrderItemData item = (OrderItemData) lineIt.next();

            	String vendorName = item.getDistItemShortDesc();
            	String vendorNum = req.getDistributorCustomerReferenceCode();
            	String vendorItemNum = item.getDistItemSkuNum();
            	String itemName = item.getItemShortDesc();
            	int poLineNum = item.getErpPoLineNum();
            	BigDecimal custPrice = item.getCustContractPrice();
            	String uom = item.getDistItemUom();
            	int qty = item.getTotalQuantityOrdered();
            	BigDecimal totPrice = custPrice.multiply(new BigDecimal(qty));
            	//String distItemSku = item.getDistItemSkuNum();

            	if(lineNum>1) {
	                mDoc += "\r\n";
	            }
	            mDoc += formatData(siteReferenceNumber )+
	            ','+ formatData(shipToName) +
	            ','+ formatData(add1) +
	            ','+ formatData(add2) +
	            ','+ formatData(add3) +
	            ','+ formatData(city) +
	            ','+ formatData(state) +
	            ','+ zip +
	            ',' + formatData(vendorNum) +
	            ',' + erpPoNum +
	            ',' + poDateS +
	            ',' + poLineNum +
	            ',' + formatData(vendorItemNum) +
	            ',' + qty +
	            ','+ uom +
	            ',' + custPrice +
	            ',' + totPrice ;

	            logger.info(formatData(siteReferenceNumber) +
	    	            ','+ formatData(shipToName) +
	    	            ','+ formatData(add1) +
	    	            ','+ formatData(add2) +
	    	            ','+ formatData(add3) +
	    	            ','+ formatData(city) +
	    	            ','+ formatData(state) +
	    	            ','+ zip +
	    	            ',' + formatData(vendorNum) +
	    	            ',' + erpPoNum +
	    	            ',' + poDateS +
	    	            ',' + poLineNum +
	    	            ',' + formatData(vendorItemNum) +
	    	            ',' + qty +
	    	            ','+ uom +
	    	            ',' + custPrice +
	    	            ',' + totPrice );

	            lineNum++;
            }

            mDoc += "\r\n";//last line feed

            translator.writeOutputStream(mDoc);


    }

	private String formatData(String val){
		if(val == null || val.equals("")){
			return " ";
		}
		val = val.replaceAll(",","");
		return val;
	}

    public String getTranslationReport(){
    	return log.toString();
    }

    //may be left unimplemented (return null) and the default will be used
    public String getFileName()throws Exception{
    	//String fileName = "POENTRY_"+translator.getDateTimeAsString()+".csv";
    	String fileName = "POENTRY.csv";
    	logger.info("IN getFileName::"+fileName);
		return fileName;
    }

    //may be left unimplemented (return null) and the default will be used
    public String getFileExtension(OutboundTranslate pHandler)throws Exception{
    	return ".csv";
    }

}
