package com.cleanwise.service.apps.dataexchange;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;



public class OutboundPurchaseOrder50029 extends InterchangeOutboundSuper implements OutboundTransaction{
	protected Logger log = Logger.getLogger(this.getClass());
	private static final String  S1DocFormat = "FormText v1.20";
	private static final String  S1DocType="850 PO";
	private static final String  S1DocSource="JMCat";
	private static final String  cLineSeparator = "\n";
	private SimpleDateFormat timestampFrmt = new SimpleDateFormat("MMddyyyy-hhmmss");


	private StringBuffer mOutputDoc = null;

	public OutboundPurchaseOrder50029(){
		seperateFileForEachOutboundOrder = true;
		mOutputDoc = new StringBuffer();
	}

	/**
	 * Where the data is actually written to the output stream
	 */
	public void buildTransactionContent()
	throws Exception {
		log.info("processing order: "+currOutboundReq.getOrderD().getOrderNum());
		OrderData orderD = currOutboundReq.getOrderD();
		PurchaseOrderData purchaseOrderD = currOutboundReq.getPurchaseOrderD();
				
		String erpPoNum = currOutboundReq.getPurchaseOrderD().getErpPoNum();
		TradingPartnerData partner = translator.getPartner();
		String outboundPoNum =
			Utility.getOutboundPONumber(currOutboundReq.getStoreType(),orderD,partner,erpPoNum);

		String siteBudgetRefNum = "";
		PropertyDataVector propDV = currOutboundReq.getSiteProperties();
		if(propDV!=null) {
			for(Iterator spIter = propDV.iterator(); spIter.hasNext();) {
				PropertyData propD = (PropertyData) spIter.next();
				if(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER
						.equals(propD.getShortDesc())) {
					siteBudgetRefNum = propD.getValue();
				}
			}
		}

		mOutputDoc.append("S1DocFormat="+S1DocFormat);
		mOutputDoc.append(cLineSeparator);
		mOutputDoc.append("S1DocType="+S1DocType);
		mOutputDoc.append(cLineSeparator);
		mOutputDoc.append("S1DocSource="+S1DocSource);
		mOutputDoc.append(cLineSeparator);
		if(!Utility.isSet(getProfile().getGroupSender())){
			throw new RuntimeException("Group sender was not setup (possibly new account without mapping?)");
		}
		mOutputDoc.append("S1CustAcct="+ getProfile().getGroupSender());
		mOutputDoc.append(cLineSeparator);
		mOutputDoc.append("S1CustName="+currOutboundReq.getAccountName());
		mOutputDoc.append(cLineSeparator);
		mOutputDoc.append("S1CustShipToCode="+siteBudgetRefNum);
		mOutputDoc.append(cLineSeparator);
		mOutputDoc.append("S1PONum="+outboundPoNum);
		mOutputDoc.append(cLineSeparator);

		mOutputDoc.append("S1CPItemCodes_Start=Here");
		mOutputDoc.append(cLineSeparator);

		for(Iterator lineIt = currOutboundReq.getOrderItemDV().iterator(); lineIt.hasNext(); ){
			OrderItemData item = (OrderItemData) lineIt.next();
			String distSkuNum = item.getDistItemSkuNum();
			if(distSkuNum!=null) distSkuNum = distSkuNum.trim();
			mOutputDoc.append(distSkuNum+"="+item.getDistItemQuantity());
			mOutputDoc.append(cLineSeparator);
			
			item.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
			appendIntegrationRequest(item);
		}
		mOutputDoc.append("S1CPItemCodes_End=Here");
		purchaseOrderD.setPurchaseOrderStatusCd(RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR);
		appendIntegrationRequest(purchaseOrderD);

		translator.writeOutputStream(mOutputDoc.toString());
		mOutputDoc = new StringBuffer();
	}

	//may be left unimplemented (return null) and the default will be used
	public String getFileName()throws Exception{
		//use order number as name of file.  NOT customer
		//po number as this is not unique and not "traceable"
		log.info("IN getFileName::"+currOutboundReq.getOrderD().getOrderNum() + ".txt");
		return currOutboundReq.getOrderD().getOrderNum() + "_"+getTimeStamp()+".txt";
	}

	/**
	 * returns a String suitable for stamping transactions with a date (MMddyyyy-hhmmss).
	 */
	private String getTimeStamp(){
		Date d = new Date();
		return timestampFrmt.format(d);
	}


}



