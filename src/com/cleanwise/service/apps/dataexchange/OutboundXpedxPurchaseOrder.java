package com.cleanwise.service.apps.dataexchange;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;

import org.dom4j.*;

import com.cleanwise.service.api.util.IOUtilities;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;

import com.cleanwise.service.api.value.*;

public class OutboundXpedxPurchaseOrder extends InterchangeOutboundSuper implements OutboundTransaction{
	protected Logger log = Logger.getLogger(this.getClass());
	private static SimpleDateFormat batchDateFormat = new SimpleDateFormat("yyyyMMdd");

	private Date now = new Date();
    Element SOPTransactionType;
	private Element itemsEl;
	Document theXmlDoc;
    private static final String DOCID = "WEBORD";
    private static final String SHIPMTHD="<GROUND>";

    public OutboundXpedxPurchaseOrder(){
    	seperateFileForEachOutboundOrder = true;    	
    }
    
    public void buildInterchangeHeader()
	throws Exception
	{
		super.buildInterchangeHeader();
		theXmlDoc = DocumentFactory.getInstance().createDocument();
    	//doc.setDocType(DocumentFactory.getInstance().createDocType("WEB_TRANS_BATCH", null, null));
    	Element scratchEl = theXmlDoc.addElement("eConnect");
        scratchEl.addAttribute("xmlns:dt","urn:schemas-microsoft-com:datatypes");
    	SOPTransactionType = scratchEl.addElement("SOPTransactionType");
        itemsEl  = SOPTransactionType.addElement("taSopLineIvcInsert_Items");
	}
    private void setText(Element el, String val){
    	if(val != null){
    		el.setText(val);
    	} else {
            el.setText("");
        }
    }

    String getCustomerCode(OutboundEDIRequestData req){
    	if(req.getSiteProperties() != null){
    		Iterator it = req.getSiteProperties().iterator();
    		while(it.hasNext()){
    			PropertyData p = (PropertyData) it.next();
    			if("Customer Code".equalsIgnoreCase(p.getShortDesc())){
    				if(Utility.isSet(p.getValue())){
    					return Utility.trimLeft(p.getValue().trim(),"0");
    				}
    			}
    		}
    	}
    	return getProfile().getGroupSender();
    }

    String getSiteId(OutboundEDIRequestData req){
    	if(req.getSiteProperties() != null){
    		Iterator it = req.getSiteProperties().iterator();
    		while(it.hasNext()){
    			PropertyData p = (PropertyData) it.next();
    			if("Address Code".equalsIgnoreCase(p.getShortDesc())){
    				if(Utility.isSet(p.getValue())){
    					//return Utility.trimLeft(p.getValue().trim(),"0");
    					return p.getValue().trim();
    				}
    			}
    		}
    	}



    	TradingPartnerData partner = translator.getPartner();
        if(RefCodeNames.SITE_IDENTIFIER_TYPE_CD.CONCATONATED.equals(partner.getSiteIdentifierTypeCd())){
            String identifier = null;
            try {
                identifier = Utility.getConcatonatedIdentifier(req.getAccountIdentifier(),
                        getIdForShipTo(partner,req),translator.getTradingPropertyMapDataVector(),
                        RefCodeNames.EDI_TYPE_CD.T850,"OUT");

            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            return identifier;
        }else if(RefCodeNames.SITE_IDENTIFIER_TYPE_CD.SEPERATED_ACCOUNT_IN_REF.equals(partner.getSiteIdentifierTypeCd())){
            return getIdForShipTo(partner,req);
        }else if(RefCodeNames.SITE_IDENTIFIER_TYPE_CD.SEPERATED_SITE_REF_NUMBER.equals(partner.getSiteIdentifierTypeCd())){
        	return getIdForShipTo(partner,req);
        }else{
        	return req.getSiteIdentifier(); //some sensible default
        }
    }

    private String getIdForShipTo(TradingPartnerData partner,OutboundEDIRequestData req) {

        if(RefCodeNames.SITE_IDENTIFIER_TYPE_CD.SEPERATED_SITE_REF_NUMBER.equals(partner.getSiteIdentifierTypeCd())){
            String value = Utility.getPropertyValue(req.getSiteProperties(),RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
            if(Utility.isSet(value)){
                return value;
            }
        }
        return req.getSiteIdentifier();
    }

    public void buildTransactionContent()
    throws Exception {
    	currOrder = currOutboundReq.getOrderD();
    	items = currOutboundReq.getOrderItemDV();
    	PropertyDataVector sitePropDV = currOutboundReq.getSiteProperties();
        String locationCode = "";
        locationCode = currOutboundReq.getDistributorCompanyCode();
        if(!Utility.isSet(locationCode)){
	        if(sitePropDV != null) {
	            for(Iterator <PropertyData> propIt=sitePropDV.iterator(); propIt.hasNext();) {
	                PropertyData propD = (PropertyData) propIt.next();
	                if("Location Code".equalsIgnoreCase(propD.getShortDesc())) {
	                    locationCode = propD.getValue();
	                }
	            }
	        }
        }
        PurchaseOrderData purchaseOrderD = currOutboundReq.getPurchaseOrderD();
        String erpPoNum = Utility.strNN(purchaseOrderD.getErpPoNum());
        boolean outOfStockFl = false;
        int lineNum = 1;
        BigDecimal subTotal = new BigDecimal(0);
        for(Iterator lineIt = currOutboundReq.getOrderItemDV().iterator(); lineIt.hasNext(); lineNum++){
        	currItem = (OrderItemData) lineIt.next();
        	currItem.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
        	appendIntegrationRequest(currItem);
            String itemErpPoNum = currItem.getErpPoNum();
            if(itemErpPoNum==null) itemErpPoNum = "";
            if(!erpPoNum.equals(itemErpPoNum)) {
                continue;
            }
        	Element itemEl = itemsEl.addElement("taSopLineIvcInsert");
            Element el;
			el = itemEl.addElement("SOPTYPE");
            setText(el,"2");

            el = itemEl.addElement("SOPNUMBE"); // ORDST2223E</SOPNUMBE> S17 Po Number (No #)
            if(itemErpPoNum.startsWith("#")) itemErpPoNum = itemErpPoNum.substring(1);
            setText(el,"W"+itemErpPoNum); //prepend "W" per Tim Sullivan on 2/2/6/2007

			el = itemEl.addElement("CUSTNMBR"); // ALTONMAN0001</CUSTNMBR> S15 Group Sender
            String groupReceiver = getCustomerCode(currOutboundReq);
            setText(el,groupReceiver);

            el = itemEl.addElement("PRSTADCD"); // SERVICE</PRSTADCD> S15 ?????
            setText(el,getSiteId(currOutboundReq));  //primary according to Todd Dickerson's (Interdyne) remark...changed to site id based off Michael Thomas(Interdyne)

            //PO date
			el = itemEl.addElement("DOCDATE"); // 2007-02-12</DOCDATE> D16 Po date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date poDate = currItem.getErpPoDate();
            String poDateS = (poDate==null)?"":sdf.format(poDate);
            setText(el,poDateS);

            //Site location code
			el = itemEl.addElement("LOCNCODE"); // WAREHOUSE</LOCNCODE> S10 Site Location Code
            setText(el,locationCode);

            //Routing Code
			el = itemEl.addElement("SHIPMTHD"); // UPS GROUND</SHIPMTHD> S15 mEdiRoutingCd (Outbound850)
            FreightHandlerView freightHandlerVw = currOutboundReq.getShipVia();
            if (freightHandlerVw!=null ) {
                setText(el,freightHandlerVw.getEdiRoutingCd());
            } else {
                setText(el,SHIPMTHD);
            }

            //Dist Sku #
			el = itemEl.addElement("ITEMNMBR");
            setText(el,currItem.getDistItemSkuNum());

			//Dist Cost
            el = itemEl.addElement("UNITPRCE"); // 9.95</UNITPRCE> N21 Dist. Cost
            BigDecimal itemCostBD = currItem.getDistItemCost();
            if(itemCostBD!=null) {
                itemCostBD = itemCostBD.setScale(2,BigDecimal.ROUND_HALF_UP);
                setText(el,itemCostBD.toString());
            } else {
                setText(el,null);
            }

            //Line Cost
			el = itemEl.addElement("XTNDPRCE"); // 19.90</XTNDPRCE> N21 Dist.Cost * Dist_Item_Quantiy (clw_order_item)
            int qty = currItem.getDistItemQuantity();
            if(itemCostBD!=null) {
                BigDecimal lineCostBD = itemCostBD.multiply(new BigDecimal(qty));
                lineCostBD = lineCostBD.setScale(2,BigDecimal.ROUND_HALF_UP);
                subTotal = subTotal.add(lineCostBD);
                setText(el,lineCostBD.toString());
            } else {
                setText(el,null);
            }

            //Quantity
			el = itemEl.addElement("QUANTITY"); // 2</QUANTITY> N21 Dist Item Quantiy (clw_order_item)
            setText(el,String.valueOf(qty));

            //
			el = itemEl.addElement("MRKDNAMT"); // 0</MRKDNAMT> N21 Constant
            setText(el,"0");

            // Short Desc
            el = itemEl.addElement("ITEMDESC"); // Phone Cord - 12 White</ITEMDESC> S100 Item Short Desc
            setText(el,currItem.getItemShortDesc());

            //
			el = itemEl.addElement("DOCID"); // STDORD</DOCID> Constant
            setText(el,DOCID);

            // Check outOfStock
            log.info("Checking on stocking levels for item: "+currItem.getDistItemSkuNum());
            int inventoryQty = Utility.getDistInventory(locationCode, currItem
					.getDistItemSkuNum(), null);
            if( inventoryQty < 0){
            	log.info("got -1, defaulting to webhold, some error occured for item: "+currItem.getDistItemSkuNum());
            	outOfStockFl = true;
            }
            log.info("Comparing inv qty: "+inventoryQty+" to order qty: "+currItem.getDistItemQuantity());
            log.info("outOfStockFl before="+outOfStockFl);
            if(inventoryQty<currItem.getDistItemQuantity()) outOfStockFl = true;
            log.info("outOfStockFl after="+outOfStockFl);
        }

        // Order info
        Element orderEl  = SOPTransactionType.addElement("taSopHdrIvcInsert");
        Element el;
        //
        el = orderEl.addElement("SOPTYPE"); // 2</SOPTYPE> Constant
        setText(el,"2");

		el = orderEl.addElement("DOCID"); // STDORD</DOCID> Constant
        setText(el,DOCID);

		// Po number
        el = orderEl.addElement("SOPNUMBE"); // ORDST2223E</SOPNUMBE> S17 Po Number
        String poNum = erpPoNum;
        if(poNum.startsWith("#")) poNum = poNum.substring(1);
        setText(el,"W"+poNum);//prepend "W" per Tim Sullivan on 2/2/6/2007

        //
		el = orderEl.addElement("ORIGTYPE"); // 0</ORIGTYPE> Constant
        setText(el,"0");

        //Routing Code
		el = orderEl.addElement("SHIPMTHD"); // UPS GROUND</SHIPMTHD> S15 mEdiRoutingCd (Outbound850)
        FreightHandlerView freightHandlerVw = currOutboundReq.getShipVia();
        if (freightHandlerVw!=null ) {
            setText(el,freightHandlerVw.getEdiRoutingCd());
        } else {
            setText(el,SHIPMTHD);
        }

        // Site location code
		el = orderEl.addElement("LOCNCODE"); // WAREHOUSE</LOCNCODE> S10 Site Location Code
        setText(el,locationCode);

        // Po date
		el = orderEl.addElement("DOCDATE"); // 2007-02-01</DOCDATE> D16 Po Date (or sysdate?)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date poDate = purchaseOrderD.getPoDate();
        String poDateS = (poDate==null)?"":sdf.format(poDate);
        setText(el,poDateS);

        // Group sender
		el = orderEl.addElement("CUSTNMBR"); // ALTONMAN0001</CUSTNMBR> S15 Group Sender
        String groupReceiver = getCustomerCode(currOutboundReq);
        setText(el,groupReceiver);

        // Customer name
        el = orderEl.addElement("CUSTNAME"); // Alton Manufacturing</CUSTNAME> S64 Account Short Desc
        setText(el,currOutboundReq.getAccountName());

        // Request po number
		el = orderEl.addElement("CSTPONBR"); // 4859</CSTPONBR> S20 Request PO Num
        setText(el,currOrder.getRequestPoNum());

        // Ship to name -- Site Short Desc --
		el = orderEl.addElement("ShipToName"); // SERVICE</ShipToName>
        setText(el,currOrder.getOrderSiteName());

        //Shipping address
        OrderAddressData shipToAddr = currOutboundReq.getShipAddr();
        el = orderEl.addElement("ADDRESS1"); // P.O. Box 3343</ADDRESS1> S60 Address 1
        setText(el,shipToAddr.getAddress1());

		el = orderEl.addElement("ADDRESS2"); //  AAAA </ADDRESS2> S60 Address 2
        setText(el,shipToAddr.getAddress2());

		el = orderEl.addElement("ADDRESS3"); //  BBBB </ADDRESS3> S60 Address 3
        setText(el,shipToAddr.getAddress3());

		el = orderEl.addElement("CITY"); // Detroit</CITY> S35
        setText(el,shipToAddr.getCity());

        el = orderEl.addElement("STATE"); // MI</STATE> S29
        setText(el,shipToAddr.getStateProvinceCd());

		el = orderEl.addElement("ZIPCODE"); // 48233-3343</ZIPCODE> S10
        setText(el,shipToAddr.getPostalCode());

		el = orderEl.addElement("COUNTRY"); // USA</COUNTRY> S60
        setText(el,shipToAddr.getCountryCd());

        // Order contact phone
		el = orderEl.addElement("PHNUMBR1"); // 55553289890000</PHNUMBR1> S14 Order Contact Phone #
        setText(el,currOrder.getOrderContactPhoneNum());

        // Where is PHNUMBR2 ???
		el = orderEl.addElement("PHNUMBR3"); // </PHNUMBR3> Constant
        setText(el,"");

        //Subtotal
        el = orderEl.addElement("SUBTOTAL"); // 19.90</SUBTOTAL> N21 Subtotal
        subTotal = subTotal.setScale(2,BigDecimal.ROUND_HALF_UP);
        setText(el,subTotal.toString());

        //Total = Subtotal
		el = orderEl.addElement("DOCAMNT"); // 24.90</DOCAMNT> N21 Total
        setText(el,subTotal.toString());

        //
        el = orderEl.addElement("PYMTRCVD"); // 0</PYMTRCVD> Constant this may change in the future with CC orders
        setText(el,"0");

        //
		el = orderEl.addElement("BACHNUMB"); // TEST</BACHNUMB>
        String batchNum = "W"+batchDateFormat.format(now);
        setText(el,batchNum); //WYYYYMMDD so march 2 2007 would be W20070302



		el = orderEl.addElement("PRBTADCD"); // PRIMARY</PRBTADCD> S15
        setText(el,"PRIMARY");

        el = orderEl.addElement("PRSTADCD"); // SERVICE</PRSTADCD> S15 ?????
        setText(el,getSiteId(currOutboundReq));  //primary according to Todd Dickerson's (Interdyne) remark...changed to site id based off Michael Thomas(Interdyne)

		el = orderEl.addElement("ORDRDATE"); // 2007-02-01</ORDRDATE> D16 Po Date
        setText(el,poDateS);

        /////////////////////// Hold Po section
        if(outOfStockFl) {
        	log.info("In out of stock hold");
            Element holdEl  = SOPTransactionType.addElement("taSopUpdateCreateProcessHold");
            el = holdEl.addElement("SOPTYPE");
            setText(el,"2");

            el = holdEl.addElement("SOPNUMBE");
            setText(el,"W"+poNum); //prepend "W" per Tim Sullivan on 2/2/6/2007

            el = holdEl.addElement("PRCHLDID");
            setText(el,"WBOUTOFSTK");

            el = holdEl.addElement("UpdateSopIfExists");
            setText(el,"1");
            }

            PropertyDataVector propDV = currOutboundReq.getAccountProperties();
            boolean holdOrderFl = false;
            if(propDV!=null) {
                for(Iterator iter = propDV.iterator();iter.hasNext();) {
                    PropertyData propD = (PropertyData) iter.next();
                    if(RefCodeNames.PROPERTY_TYPE_CD.HOLD_PO.equals(propD.getShortDesc())) {
                        String val = propD.getValue();
                        holdOrderFl = Utility.isTrue(val);
                        break;
                    }
                }
            } else {
log.info("OutboundXpedxPurchaseOrder XXXXXXXXXXXXXXXXXXXXXX no account properties found");
        }

        if(holdOrderFl) {
            Element holdEl  = SOPTransactionType.addElement("taSopUpdateCreateProcessHold");
            el = holdEl.addElement("SOPTYPE");
            setText(el,"2");

            el = holdEl.addElement("SOPNUMBE");
            setText(el,"W"+poNum); //prepend "W" per Tim Sullivan on 2/2/6/2007

            el = holdEl.addElement("PRCHLDID");
            setText(el,"WB");

            el = holdEl.addElement("UpdateSopIfExists");
            setText(el,"1");
        }

        //
        if(Utility.isSet(currOrder.getComments())){
			el = orderEl.addElement("COMMNTID"); // TEST</COMMNTID> S15 ?????
            setText(el,"SHIPCMT");

            //Order comment set to the  first item
            String[] commA = new String[4];
            for(int ii=0; ii<4; ii++) commA[ii] = "";
            String comment = currOrder.getComments();
            for(int ii=0; comment!=null && ii<4; ii++) {
                if(comment.length()<=50){
                    commA[ii] = comment;
                    break;
                }
                commA[ii] = comment.substring(0,50);
                comment = comment.substring(50);
            }
			el = orderEl.addElement("COMMENT_1"); // cmt1</COMMENT_1> S50
            setText(el,commA[0]);
			el = orderEl.addElement("COMMENT_2"); // cmt2</COMMENT_2> S50
            setText(el,commA[1]);
			el = orderEl.addElement("COMMENT_3"); // cmt3</COMMENT_3> S50
            setText(el,commA[2]);
			el = orderEl.addElement("COMMENT_4"); // cmt4</COMMENT_4> S50
            setText(el,commA[3]);
        }
        String xml = theXmlDoc.asXML();
        translator.writeOutputStream(xml);
        currOutboundReq.getPurchaseOrderD().setPurchaseOrderStatusCd(RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR);
        appendIntegrationRequest(currOutboundReq.getPurchaseOrderD());
    }

	/**
	 * Returns a transaction identifier (in this case the po number) from the
	 * source document, which is assumed to be an xml document this class would
	 * generally render.
	 */
	public static String getTransactionIdentifier(InputStream source) throws IOException{
		String data = IOUtilities.loadStream(source);
        int start = data.indexOf("<SOPNUMBE>");
        int end   = data.indexOf("</SOPNUMBE>");
        if(start >= 0 && end >=0){
        	return data.substring(start+"<SOPNUMBE>".length(),end);
        }
        return null;
	}

	public String getTranslationReport() {
		if(getTransactionsToProcess().size() == 0){
            return "no records translated";
        }
        return "Successfully processed "+getTransactionsToProcess().size() + " records";
	}
}
