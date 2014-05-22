package com.cleanwise.service.apps.dataexchange;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Iterator;

import org.apache.log4j.Logger;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import java.text.DecimalFormat;

import com.americancoders.edi.DataElement;
import com.americancoders.edi.OBOEException;
import com.americancoders.edi.Segment;
import com.americancoders.edi.Table;
import com.americancoders.edi.TransactionSet;
import com.americancoders.edi.TransactionSetFactory;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.dao.OrderMetaDataAccess;
import java.util.ArrayList;
import com.cleanwise.service.api.util.ConnectionContainer;
import java.sql.Connection;
import com.cleanwise.service.api.APIAccess;


public class OutboundJd850Xml extends InterchangeOutboundSuper implements OutboundTransaction{
	protected Logger log = Logger.getLogger(this.getClass());
	private Document mXmlDoc;
	private Element mCxmlEl;
	private Element mRequestEl;
	private final OutputFormat outputFormat;
    String mEdiRoutingCd;
    protected FreightHandlerView mShipVia;
	protected TransactionSet ts = null;
    //private Connection con;
    private SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
	private static final BigDecimal ZERO = new BigDecimal(0);

	public OutboundJd850Xml(){
		seperateFileForEachOutboundOrder = true;
		outputFormat = new OutputFormat();
		outputFormat.setIndent(true);
		outputFormat.setIndentSize(4);
		outputFormat.setNewlines(true);
        /*
        try {
			con = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
         */
	}


	private String getIdForShipTo(TradingPartnerData partner, OutboundEDIRequestData currEDIRequestD) {
		if(RefCodeNames.SITE_IDENTIFIER_TYPE_CD.SEPERATED_SITE_REF_NUMBER.equals(partner.getSiteIdentifierTypeCd())){
			
			String value = Utility.getPropertyValue(currEDIRequestD.getSiteProperties(),RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
			if(Utility.isSet(value)){
				return value;
			}
		}
		return currEDIRequestD.getSiteIdentifier();
	}

	private void buildDocumentHeader2(String messageId)
	{
		mXmlDoc = DocumentFactory.getInstance().createDocument();

		//Adding header info
		TradingPropertyMapDataVector properties =
			translator.getTradingPropertyMapDataVector();
		String fromDomain = ""; //"orderline.xpedx.com"; //sysInterchangeDomain
		String fromIdentity = ""; // "orderline-T";  //sysInterchangeIdentity
		String fromSharedSecret = ""; // "2o67uy8s"; //sysInterchangeSharedSecret

		String toDomain = ""; // "orderline.xpedx.com"; //partnerInterchangeDomain
		String toIdentity = ""; // "orderline-T"; //partnerInterchangeIdentity

		String senderDomain = ""; // "NetworkId"; //sysGroupDomain
		String senderIdentity = ""; // "orderline-T"; //sysGroupIdentity
		String senderSharedSecret = ""; // "2o67uy8s"; //sysGroupSharedSecret

		for(Iterator iter = properties.iterator(); iter.hasNext();) {
			TradingPropertyMapData tpmD = (TradingPropertyMapData) iter.next();
			String propertyTypeCd = tpmD.getPropertyTypeCd();
			if("sysInterchangeDomain".equalsIgnoreCase(propertyTypeCd)) {
				fromDomain = tpmD.getHardValue();
			} else if("sysInterchangeIdentity".equalsIgnoreCase(propertyTypeCd)) {
				fromIdentity = tpmD.getHardValue();
			} else if("sysInterchangeSharedSecret".equalsIgnoreCase(propertyTypeCd)) {
				fromSharedSecret = tpmD.getHardValue();
			} else if("partnerInterchangeDomain".equalsIgnoreCase(propertyTypeCd)) {
				toDomain = tpmD.getHardValue();
			} else if("partnerInterchangeIdentity".equalsIgnoreCase(propertyTypeCd)) {
				toIdentity = tpmD.getHardValue();
			} else if("sysGroupDomain".equalsIgnoreCase(propertyTypeCd)) {
				senderDomain = tpmD.getHardValue();
			} else if("sysGroupIdentity".equalsIgnoreCase(propertyTypeCd)) {
				senderIdentity = tpmD.getHardValue();
			} else if("sysGroupSharedSecret".equalsIgnoreCase(propertyTypeCd)) {
				senderSharedSecret = tpmD.getHardValue();
			}
			// Set propertiy walue
		}

		Element fxEnvelope = mXmlDoc.addElement("fxEnvelope");
		// fxEnvelope Header
		Element fxHeader = fxEnvelope.addElement("header");
		Element message = fxHeader.addElement("message");
		createElement(message, "exchangeID", "ForestExpress");
		createElement(message, "subject", "Order");
		createElement(message, "payloadType", "FXS-2.1");
		createElement(message, "version", "2.1");
		Element from = fxHeader.addElement("from");
		createElement(from, "orgID", getProperty(properties,
		"header/from/orgID"));
		createElement(from, "locationID", getProperty(properties,
		"header/from/locationID"));
		createElement(from, "messageID", messageId+"");//xxx use the po number!

		Element to = fxHeader.addElement("to");
		createElement(to, "orgID", getProperty(properties, "header/to/orgID"));

		// fxEnvelope body, include cXML
		Element body = fxEnvelope.addElement("body");
		mCxmlEl = body.addElement("cXML");
		Element header = mCxmlEl.addElement("Header");
		//From
		Element fromEl = header.addElement("From");
		Element fromCredentialEl = fromEl.addElement("Credential");
		fromCredentialEl.addAttribute("domain",fromDomain);
		Element fromIdentityEl = fromCredentialEl.addElement("Identity");
		setText(fromIdentityEl,fromIdentity);
		Element fromSharedSecretEl = fromCredentialEl.addElement("SharedSecret");
		setText(fromSharedSecretEl,fromSharedSecret);

		//To
		Element toEl = header.addElement("To");
		Element toCredentialEl = toEl.addElement("Credential");
		toCredentialEl.addAttribute("domain",toDomain);
		Element toIdentityEl = toCredentialEl.addElement("Identity");
		setText(toIdentityEl,toIdentity);

		//Sender
		Element senderEl = header.addElement("Sender");
		Element senderCredentialEl = senderEl.addElement("Credential");
		senderCredentialEl.addAttribute("domain",senderDomain);
		Element senderIdentityEl = senderCredentialEl.addElement("Identity");
		setText(senderIdentityEl,senderIdentity);
		Element senderSharedSecretEl = senderCredentialEl.addElement("SharedSecret");
		setText(senderSharedSecretEl,senderSharedSecret);

		mRequestEl = mCxmlEl.addElement("Request");
		if(getProfile().getTestIndicator().trim().equals("T")){
			mRequestEl.addAttribute("deploymentMode", "test");
		}else if(getProfile().getTestIndicator().trim().equals("P")){
			mRequestEl.addAttribute("deploymentMode", "production");
		}else{
			throw new RuntimeException("Unknown test indicator: "+getProfile().getTestIndicator()+ " should be t or p");
		}

	}


	private String getProperty(TradingPropertyMapDataVector properties, String key) {
		for (int i = 0; properties != null && i < properties.size(); i++) {
			TradingPropertyMapData item = (TradingPropertyMapData) properties.get(i);
			if (RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE.equals(item.getEntityProperty())
					&& RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP.equals(item.getTradingPropertyMapCode())
					&& key.equals(item.getPropertyTypeCd())) {
				return item.getHardValue();
			}
		}
		return null;
	}

	private String getShipMethod(OutboundEDIRequestData reqD) {
		FreightHandlerView mShipVia = reqD.getShipVia();
		String shipMethod=null;
		if(mShipVia != null){
			shipMethod = mShipVia.getEdiRoutingCd();
		}

		if (!Utility.isSet(shipMethod)) {
			//This class appears not to be used, changed method ShipMethod to
			//get a list of all  order freightobjecrs so builder can decide
			//how to render them...and added the wayit used to work to here.
			if(reqD.getShipMethod() != null && reqD.getShipMethod().size() > 0){
				shipMethod = ((OrderFreightData)reqD.getShipMethod().get(0)).getShortDesc();
			}

		}
		if (shipMethod == null) {
			return "";
		}
		return shipMethod;
	}

	public void buildTransactionContent()
	throws Exception {
		currOrder = currOutboundReq.getOrderD();
		items = currOutboundReq.getOrderItemDV();
		currItem = (OrderItemData) items.get(0);

        mShipVia = currOutboundReq.getShipVia();
        if ( mShipVia == null ) {
            mEdiRoutingCd = "";
        } else {
            mEdiRoutingCd = mShipVia.getEdiRoutingCd();
        }

		final TradingPartnerData partner = translator.getPartner();
		final String poOutFin = Utility.getOutboundPONumber(currOutboundReq
				.getStoreType(), currOrder, partner, currItem.getOutboundPoNum());
//		buildDocumentHeader2(poOutFin);

		final OrderAddressData shipToAddr = currOutboundReq.getShipAddr();
		final Locale locale = getLocale(currOrder.getLocaleCd());

		mXmlDoc = DocumentFactory.getInstance().createDocument();
		final Element tset = mXmlDoc.addElement("tset");

		final String mPoDate = df.format(currItem.getErpPoDate());
		addAttributes(tset, new String[][] {
				{ "name", "850" }});

		createElement(tset, "BegCode", getProfile().getGroupReceiver());
//		createElement(tset, "BegCode", "JohnsonDiversey");
		createElement(tset, "SenderDunsNo", getProfile().getGroupSender());
		createElement(tset, "OrderType", "DS");
		createElement(tset, "PONumber", currOrder.getRequestPoNum());
		createElement(tset, "SupplierDunsNo", getProfile().getGroupReceiver());
		createElement(tset, "PODate", mPoDate);

		String requestedShipDate = "";
/*
        OrderMetaDataVector omdv;
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(OrderMetaDataAccess.ORDER_ID, currOrder.getOrderId());
        ArrayList types = new ArrayList();
        types.add(RefCodeNames.ORDER_PROPERTY_TYPE_CD.REQUESTED_SHIP_DATE);
        crit.addOneOf(OrderMetaDataAccess.NAME, types);
        omdv = OrderMetaDataAccess.select(con, crit);
 */
        OrderMetaDataVector omdv = currOutboundReq.getOrderMetaDV();
        if(omdv != null){
	        Iterator it = omdv.iterator();
	        while(it.hasNext()){
	        	OrderMetaData opd = (OrderMetaData) it.next();
	        	if(RefCodeNames.ORDER_PROPERTY_TYPE_CD.REQUESTED_SHIP_DATE.equals(opd.getName())){
	        		try{
	        			requestedShipDate = df.format(opd.getValue());
	        		}catch(Exception e){}
	        	}
	        }
        }

        String txbl = "0";
        int ttlItems = items.size();
        float ttlTaxAmount = 0;
        float purchaseOrderTtl = 0;
		for (int i = 0; i < items.size(); i++) {
			currItem = (OrderItemData) items.get(i);
			String taxExempt = currItem.getTaxExempt();
			if (taxExempt!=null && taxExempt.equalsIgnoreCase("TAXABLE_INDICATOR")) {
				txbl = "1";
			}
			float taxAmount = 0;
			if (currItem.getTaxAmount() != null) {
				taxAmount = currItem.getTaxAmount().floatValue();
			}
			ttlTaxAmount += taxAmount;

			BigDecimal distItemCost;
			float lITtl = 0;
			if ((currItem.getDistItemCost() != null)) {
				distItemCost = currItem.getDistItemCost();
				lITtl = distItemCost.floatValue() * currItem.getTotalQuantityOrdered();
			}
			purchaseOrderTtl += lITtl;
		}

		createElement(tset, "DeliveryRequestedDate", requestedShipDate);
		createElement(tset, "ShipNotBefore", "");
		createElement(tset, "ShipNotLater", "");
		createElement(tset, "RequiredByDate", "");
		createElement(tset, "DeliveryToName", currOrder.getOrderContactName());
		createElement(tset, "ShipToName", shipToAddr.getShortDesc());
		createElement(tset, "ShipToAddressOne", shipToAddr.getAddress1());
		createElement(tset, "ShipToAddressTwo", shipToAddr.getAddress2());
		createElement(tset, "ShipToCity", shipToAddr.getCity());
		createElement(tset, "ShipToZip", shipToAddr.getPostalCode());
		createElement(tset, "ShipToCountryCode", shipToAddr.getCountryCd());
		createElement(tset, "ShipToEmail", shipToAddr.getEmailAddress());
		createElement(tset, "ShipToPhoneNumber", shipToAddr.getPhoneNum());
		createElement(tset, "Routing", mEdiRoutingCd);

		final OrderAddressData billToAddr = currOutboundReq.getBillAddr();

		createElement(tset, "BillToName", billToAddr.getShortDesc());
		createElement(tset, "BillToAddressOne", billToAddr.getAddress1());
		createElement(tset, "BillToAddressTwo", billToAddr.getAddress2());
		createElement(tset, "BillToCity", billToAddr.getCity());
		createElement(tset, "BillToZipCode", billToAddr.getPostalCode());
		createElement(tset, "BillToCountryCode", billToAddr.getCountryCd());
		createElement(tset, "BillToEmailName", billToAddr.getEmailAddress());
		createElement(tset, "BillToTelephoneNumber", billToAddr.getPhoneNum());

		createElement(tset, "MoneyCurrency", currOrder.getCurrencyCd());
		createElement(tset, "Taxable", txbl);
		createElement(tset, "TotalTax", "" + ttlTaxAmount);

        String msg = currOrder.getComments();
        if(!RefCodeNames.STORE_TYPE_CD.MLA.equals(currOutboundReq.getStoreType()) && Utility.isSet(requestedShipDate)){
        	if(!Utility.isSetForDisplay(msg)){
        		msg = "";
        	}
    		msg = "Deliver before: " + requestedShipDate;
        }
        PropertyData pd = Utility.getProperty(currOutboundReq.getSiteProperties(),
                RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG);
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

		Element shpL = createElement(tset, "ShippingLoop");
		createElement(shpL, "ShippingMessage", msg);

		Element detailL = createElement(tset, "DetailLoop");
		for (int i = 0; i < items.size(); i++) {
			currItem = (OrderItemData) items.get(i);
			createElement(detailL, "LineNumber", currItem.getErpPoNum());
			createElement(detailL, "SupplierItemID", currItem.getDistItemSkuNum());
			createElement(detailL, "BuyerItemID");
			createElement(detailL, "UnitOfMeasure", currItem.getItemUom());
			BigDecimal distItemCost = currItem.getDistItemCost();
			createElement(detailL, "ItemUnitPrice", distItemCost.toString());
			createElement(detailL, "ItemDescription", currItem.getItemShortDesc());
			createElement(detailL, "ItemQuantity", Integer.toString(currItem.getTotalQuantityOrdered()));
			if (currItem.getDistItemCost() == null) {
				createElement(detailL, "LineItemTotal", "0");

			} else {
				createElement(detailL, "LineItemTotal", Long.toString(distItemCost.longValue() * currItem.getTotalQuantityOrdered()));
			}

		}
		createElement(tset, "TotalLineItems", Integer.toString(ttlItems));
		createElement(tset, "PurchaseOrderTotal", Float.toString(purchaseOrderTtl));

		getTranslator().writeOutputStream(mXmlDoc.asXML());
		log.info(mXmlDoc.asXML());
	}

	private String modifyCostCenterCode(CostCenterData costCenter,
			OutboundEDIRequestData currOutboundReq){

		String CCcode = costCenter.getCostCenterCode();
		String newCCcode = null;
		String storeNum = Utility.getPropertyValue(currOutboundReq.getSiteProperties(),RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
		storeNum = storeNum.substring(4, 8);
		log.info("STORE NUM : "+storeNum);
		log.info("Cost Center Code "+CCcode);

		String glTransformType = Utility.getPropertyValue(currOutboundReq.getAccountProperties(), RefCodeNames.PROPERTY_TYPE_CD.GL_TRANSFORMATION_TYPE);
		log.info("glTransformType "+glTransformType);

		if (glTransformType.equals(RefCodeNames.GL_TRANSFORMATION_TYPE.SITE_POS6_LZF4) ){

			DecimalFormat myFormat = new DecimalFormat("0000");
			storeNum = myFormat.format(Double.parseDouble(storeNum));

			newCCcode = CCcode.substring(0,6)+storeNum+CCcode.substring(6,CCcode.length());
			log.info("MODIFIED COST CENTER CODE : "+newCCcode);

			return newCCcode;
		}
		return newCCcode;
	}

	public String getTranslationReport() {
		if(getTransactionsToProcess().size() == 0){
			return "no records translated";
		}
		return "Successfully processed "+getTransactionsToProcess().size() + " records";
	}

	private final static Element createElement(final Element parent,
			final String elementName) {
		return createElement(parent, elementName, null, null);
	}

	private final static Element createElement(final Element parent,
			final String elementName, final String elementTextValue) {
		return createElement(parent, elementName, elementTextValue, null);
	}

	private final static Element createElement(final Element parent,
			final String elementName, final String elementTextValue,
			final String[][] attributes) {
		final Element element = parent.addElement(elementName);
		element.setText(elementTextValue != null ? elementTextValue : "");
		addAttributes(element, attributes);
		return element;
	}

	private final static void createElementIfTextNotEmpty(final Element parent,
			final String elementName, final String elementTextValue) {
		if (elementTextValue != null && elementTextValue.trim().length() > 0) {
			createElement(parent, elementName, elementTextValue);
		}
	}

	private final static void addAttributes(final Element element,
			final String attributes[][]) {
		for (int i = 0; attributes != null && i < attributes.length; i++) {
			if (attributes[i] != null && attributes[i].length == 2
					&& attributes[i][0] != null && attributes[i][1] != null) {
				element.addAttribute(attributes[i][0], attributes[i][1]);
			}
		}
	}

	private final static String getTotalSum(final OrderItemDataVector items) {
		BigDecimal total = new BigDecimal(0);
		for (int i = 0; i < items.size(); i++) {
			final OrderItemData currItem = (OrderItemData) items.get(i);
			final BigDecimal mCurrItemAdjustedCost = getPoCost(currItem);
			total = total.add(mCurrItemAdjustedCost);
		}
		return total.toString();
	}

	private final static BigDecimal getPoCost(OrderItemData oi) {
		if (oi.getDistUomConvCost() != null
				&& oi.getDistUomConvCost().compareTo(ZERO) > 0) {
			return oi.getDistUomConvCost();
		} else {
			return oi.getDistItemCost();
		}
	}

	private final static String getExtrinsicTaxable(
			final OutboundEDIRequestData currOutboundReq,
			final OrderItemData currItem) {
		final String buffer = getTaxExemptCode(currOutboundReq, currItem, false);
		if (buffer == null || buffer.equals("2") == false) {
			return "N";
		} else {
			return "Y";
		}
	}

	private final static String getTaxExemptCode(
			final OutboundEDIRequestData currOutboundReq,
			final OrderItemData currItem, final boolean headerLevel) {
		if (RefCodeNames.STORE_TYPE_CD.MLA.equals(currOutboundReq
				.getStoreType())) {
			return null;
		}
		String taxCode = null;
		// first check if this po has any tax, if so then taxcode is 2
		if (Utility.isSet(currOutboundReq.getPurchaseOrderD().getTaxTotal())) {
			taxCode = "2";
			// if calculating at item level header may have tax whereas item may
			// not
			if (headerLevel == false) {
				if (!Utility.isTaxableOrderItem(currItem)) {
					taxCode = "1";
				}
			}
		} else {
			taxCode = "1";
		}
		return taxCode;
	}

	private final static String getUnitOfMeasure(final OrderItemData currItem) {
		String mCurrItemAdjustedUom = currItem.getDistItemUom();
		if (Utility.isSet(mCurrItemAdjustedUom) == false) {
			mCurrItemAdjustedUom = currItem.getItemUom();
		}
		// UP case the UOM. durval 2006-9-14
		// Some customers have been entering their own UOMs in
		// lower case causing EDI generation errors.
		return mCurrItemAdjustedUom.toUpperCase();
	}

	public final static Locale getLocale(final String localeKey) {
		if (localeKey == null) {
			throw new NullPointerException("Locale key cann't be NULL!");
		}
		final int index = localeKey.indexOf('_');
		if (index != 2 || localeKey.length() != 5) {
			throw new IllegalArgumentException("Incorrect format locale key:>"
					+ localeKey + "<!");
		}
		final String language = localeKey.substring(0, index);
		final String country = localeKey.substring(index + 1);
		return new Locale(language, country);
	}

	private void setText(Element el, String val){
		if(val != null){
			el.setText(val);
		} else {
			el.setText("");
		}
	}

    public void buildHeaderRouting(Table inTable)
    throws OBOEException {
        if (mEdiRoutingCd == null || mEdiRoutingCd.length() == 0) {
            return;
        }
        log.info("1 buildHeaderTD5, mEdiRoutingCd=" +
                mEdiRoutingCd);

        try {
            Segment segment = inTable.createSegment("Routing");
            inTable.addSegment(segment);

            DataElement de;
            de = (DataElement)segment.buildDE(1); // 133 Routing Sequence Code
            de.set("");
            de = (DataElement)segment.buildDE(2); // 66 Identification Code Qualifier
            de.set("");
            de = (DataElement)segment.buildDE(3); // 67 Identification Code
            de.set("");
            de = (DataElement)segment.buildDE(4); // 91 Transportation Method/Type Code
            de.set("");
            de = (DataElement)segment.buildDE(5); // 387 Routing

            if (mEdiRoutingCd != null && mEdiRoutingCd.length() > 0) {
                de.set(mEdiRoutingCd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
