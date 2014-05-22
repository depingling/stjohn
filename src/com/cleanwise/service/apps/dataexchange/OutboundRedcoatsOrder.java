package com.cleanwise.service.apps.dataexchange;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OutboundEDIRequestData;

public class OutboundRedcoatsOrder extends InterchangeOutboundSuper implements OutboundTransaction{
	protected Logger log = Logger.getLogger(this.getClass());

	public void buildInterchangeHeader()
	throws Exception
	{
		super.buildInterchangeHeader();
		translator.writeOutputStream("STARTING PO\r\n");
	}

	private String xlateUOM(String uom,String sku){
		uom = uom.trim();
		if(uom.equalsIgnoreCase("PK") || uom.equalsIgnoreCase("PACK")){
			return "PACK";
		}else if(uom.equalsIgnoreCase("PR") || uom.equalsIgnoreCase("PAIR")){
			return "PAIR";
		}else if(uom.equalsIgnoreCase("JUG")){
			return "JUG";
		}else if(uom.equalsIgnoreCase("GL") || uom.equalsIgnoreCase("GAL")){
			return "GAL";
		}else if(uom.equalsIgnoreCase("DZ") || uom.equalsIgnoreCase("DOZ")){
			return "DOZ";
		}else if(uom.equalsIgnoreCase("CN") || uom.equalsIgnoreCase("CAN")){
			return "CAN";
		}else if(uom.equalsIgnoreCase("QT")){
			return "QT";
		}else if(uom.equalsIgnoreCase("BX") || uom.equalsIgnoreCase("BOX")){
			return "BOX";
		}else if(uom.equalsIgnoreCase("RL") || uom.equalsIgnoreCase("REEL")){
			sku = sku.trim();
			if("A-5046".equalsIgnoreCase(sku)){
				return "REEL";
			}
			return "RL";
		}else if(uom.equalsIgnoreCase("CS")){
			return "CS";
		}else if(uom.equalsIgnoreCase("BOT")){
			return "BOT";
		}else if(uom.equalsIgnoreCase("EA")){
			return "EA";
		}else if(uom.equalsIgnoreCase("EACH")){
			return "EACH";
		}else if(uom.equalsIgnoreCase("BAG")){
			return "BAG";
		}else{
			throw new RuntimeException("Invalid UOM: "+uom);
		}
	}

	public void buildInterchangeContent() throws Exception {
		Collections.sort(getTransactionsToProcess(),TRANSACTION_SORT);
		super.buildInterchangeContent();
	}
	/**
	 * Where the data is actually written to the output stream
	 * STARTING PO
	JOB#: 0130038100| 6074220
	C-404611            | CS  | 0015 |     13.89| Y
	C-283005            | CS  | 0010 |     11.85| Y
	A-C24T              | EACH| 0012 |      3.87| Y
	NCL-1800            | GAL | 0006 |      6.63| Y
	E-SC5845            | EA  | 0002 |    249.00| Y
	 */
	public void buildTransactionContent()
	throws Exception {
		currOrder = currOutboundReq.getOrderD();
		items = currOutboundReq.getOrderItemDV();
		String siteRef = Utility.getPropertyValue(currOutboundReq.getSiteProperties(),RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
		if(siteRef == null){
			throw new Exception(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER+" cannot be null for order: "+currOutboundReq.getOrderD().getOrderNum());
		}
		siteRef = siteRef.trim();
		if(!siteRef.startsWith("0")){
			siteRef = "0" + siteRef;
		}

		String erpPo = ((OrderItemData)items.get(0)).getErpPoNum();
		String po = Utility.getOutboundPONumber(currOutboundReq.getStoreType(),currOrder,translator.getPartner(),erpPo,true);

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("JOB#: "+siteRef+"| "+po+"\r\n");

		Iterator lineIt = items.iterator();
		while(lineIt.hasNext()){
			//line example:
				//C-404611            | CS  | 0015 |     13.89| Y
			currItem = (OrderItemData) lineIt.next();
			currItem.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
			appendIntegrationRequest(currItem);

			StringBuffer aLine = new StringBuffer();
			String sku = currItem.getDistItemSkuNum();
			sku = sku.trim().toUpperCase();
			aLine.append(Utility.padRight(sku.trim(),' ',21));
			aLine.append("| "); //NOTE Spacing!
			String uom;
			if(Utility.isSet(currItem.getDistItemUom())){
				uom = currItem.getDistItemUom();
			}else{
				uom = currItem.getItemUom();
			}
			uom = xlateUOM(uom,sku);
			if(uom.length() > 4){
				throw new Exception("Uom ["+uom+"] cannot be longer then 4 chars");
			}
			aLine.append(Utility.padRight(uom,' ',4));
			aLine.append("| "); //NOTE Spacing!
			if(currItem.getDistItemQuantity() >= 10000){
				throw new Exception(currItem.getDistItemQuantity()+" to large a number! order "+currOrder.getOrderNum());
			}
			String qty = Utility.padLeft(Integer.toString(currItem.getDistItemQuantity()),'0',4);
			aLine.append(qty);
			aLine.append(" |"); //NOTE Spacing (this time on other sides!
			String amt = Utility.padLeft(currItem.getDistItemCost().toString(),' ',10);
			if(amt.length() > 10){
				throw new Exception("Dist Cost ["+amt+"] cannot be longer then 10 chars");
			}
			aLine.append(amt);
			aLine.append("| "); //NOTE Spacing!
			if(!Utility.isTaxableOrderItem(currItem)){
				aLine.append('N');
			}else{
				aLine.append('Y');
			}
			stringBuffer.append(aLine+"\r\n");
		}
		
		translator.writeOutputStream(stringBuffer.toString());
		currOutboundReq.getPurchaseOrderD().setPurchaseOrderStatusCd(RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR);
		appendIntegrationRequest(currOutboundReq.getPurchaseOrderD());		
	}

	public String getFileExtension(){
		return ".txt";
	}

	/**
	 * Sorts the transactions
	 */
	private static final Comparator TRANSACTION_SORT = new Comparator() {
		public int compare(Object o1, Object o2){
			String siteRef1 = Utility.getPropertyValue(((OutboundEDIRequestData)o1).getSiteProperties(),RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
			String siteRef2 = Utility.getPropertyValue(((OutboundEDIRequestData)o2).getSiteProperties(),RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
			if(siteRef1 == null){
				siteRef1 = "";
			}
			if(siteRef2 == null){
				siteRef2 = "";
			}
			return siteRef1.compareTo(siteRef2);
		}
	};

	public String getFileExtension(OutboundTranslate pHandler) throws Exception {
		return ".txt";
	}

	public String getFileName() throws Exception {
		return null;
	}
	
	public String getTranslationReport() {
		if(getTransactionsToProcess().size() == 0){
            return "no records translated";
        }
        return "Successfully processed "+getTransactionsToProcess().size() + " records";
	}

}
