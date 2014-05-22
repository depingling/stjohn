package com.cleanwise.service.apps.dataexchange;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;

import com.cleanwise.service.api.value.*;

public class OutboundWinnDixiePurchaseOrder extends InterchangeOutboundSuper implements OutboundTransaction{
	protected Logger log = Logger.getLogger(this.getClass());
	private static SimpleDateFormat poDateFormat = new SimpleDateFormat(
			"MM/dd/yyyy");
	private static SimpleDateFormat batchDateFormat = new SimpleDateFormat(
			"yyyyMMdd");
	private static char[] codes = { 'X', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I' };

	private String mDoc;
	int mCurrProcessingOrderIndex = 0;

	public OutboundWinnDixiePurchaseOrder(){
    	seperateFileForEachOutboundOrder = true;
    }

	public void buildTransactionContent()
    throws Exception {
		currOrder = currOutboundReq.getOrderD();
		items = currOutboundReq.getOrderItemDV();
		PurchaseOrderData purchaseOrderD = currOutboundReq.getPurchaseOrderD();

    	mDoc = "";
        PropertyDataVector sitePropDV = currOutboundReq.getSiteProperties();
        String siteReferenceNumber = "";
        if(sitePropDV != null) {
            for(Iterator propIt=sitePropDV.iterator(); propIt.hasNext();) {
                PropertyData propD = (PropertyData) propIt.next();
                if(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER
                              .equalsIgnoreCase(propD.getShortDesc())) {
                    siteReferenceNumber = propD.getValue();
                }
            }
        }
        String characterDistributionCenter = "Jax";
        String characterRA = "RA";

        Date poDate = purchaseOrderD.getPoDate();
        String poDateS = (poDate!=null)? poDateFormat.format(poDate):"";
        //Win Dixie should trim all the "0"s from the date
        poDateS = poDateS.replaceAll("/0", "/");
        poDateS = Utility.trimLeft(poDateS, "0");


        String erpPoNum = Utility.strNN(purchaseOrderD.getErpPoNum());
        int lineNum = 1;
        for(Iterator lineIt = items.iterator(); lineIt.hasNext(); lineNum++){
        	currItem = (OrderItemData) lineIt.next();
        	currItem.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
        	appendIntegrationRequest(currItem);
            String itemErpPoNum = currItem.getErpPoNum();
            if(itemErpPoNum==null) itemErpPoNum = "";
            if(!erpPoNum.equals(itemErpPoNum)) {
                continue;
            }
            String characterCommodity = "X";
            String distSkuNum = currItem.getDistItemSkuNum();
            if(distSkuNum==null) distSkuNum = "";
            if(distSkuNum.length()>=1) {
                 char firstChar = distSkuNum.charAt(0);
                 if(java.lang.Character.isDigit(firstChar)) {
                     int firstDigit = Integer.parseInt(String.valueOf(firstChar));
                     characterCommodity = String.valueOf(codes[firstDigit]);
                 }
            }
            int quantity = currItem.getTotalQuantityOrdered();

            if(lineNum>1) {
                mDoc += "\r\n";
            }
            mDoc += characterCommodity + ',' +
                    siteReferenceNumber + ',' +
                    distSkuNum + ',' +
                    quantity + ',' +
                    poDateS  + ',' +
                    characterDistributionCenter + "," +
                    characterRA;
            lineNum++;
        }
        mDoc += "\r\n";//last line feed

        translator.writeOutputStream(mDoc);
        purchaseOrderD.setPurchaseOrderStatusCd(RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR);
        appendIntegrationRequest(purchaseOrderD);
    }


	public String getTranslationReport() {
		if(getTransactionsToProcess().size() == 0){
            return "no records translated";
        }
        return "Successfully processed "+getTransactionsToProcess().size() + " records";
	}


    //may be left unimplemented (return null) and the default will be used
    public String getFileName()throws Exception{
    	String po = currOutboundReq.getPurchaseOrderD().getErpPoNum();
    	if(po.startsWith("#")){
    		po = po.substring(1);
    	}
    	return "WD"+po+"TS"+ batchDateFormat.format(new Date())+".alloc";
    }

}
