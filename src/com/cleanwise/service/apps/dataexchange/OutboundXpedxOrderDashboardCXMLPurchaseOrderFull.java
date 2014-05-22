package com.cleanwise.service.apps.dataexchange;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Iterator;

import org.apache.log4j.Logger;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import java.lang.*;
import java.text.DecimalFormat;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

public class OutboundXpedxOrderDashboardCXMLPurchaseOrderFull extends InterchangeOutboundSuper implements OutboundTransaction{
	protected Logger log = Logger.getLogger(this.getClass());
	private Document mXmlDoc;
	private Element mCxmlEl;
	private Element mRequestEl;
	private final OutputFormat outputFormat;
	private static final String SHIP_TO_OVERRIDE = "Ship-to Override";

	private final static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss");

	private static final BigDecimal ZERO = new BigDecimal(0);

	public OutboundXpedxOrderDashboardCXMLPurchaseOrderFull(){
		seperateFileForEachOutboundOrder = true;
		outputFormat = new OutputFormat();
		outputFormat.setIndent(true);
		outputFormat.setIndentSize(4);
		outputFormat.setNewlines(true);
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

	private String getShipMethod(OutboundEDIRequestData reqD) throws Exception {
		FreightHandlerView mShipVia = reqD.getShipVia();
		String shipMethod=null;
		if (!Utility.isSet(shipMethod)) {
			OrderFreightDataVector shipMethodV = reqD.getShipMethod();

			if(shipMethodV != null && shipMethodV.size()>0){
				for(Iterator it=shipMethodV.iterator(); it.hasNext();){
					// loop through to find 'Off-Cycle Order Charge' and remove it
					OrderFreightData fD = (OrderFreightData)it.next();
					String fName = fD.getShortDesc();
					fName = fName.replaceAll(" ", "");
					if(fName.equalsIgnoreCase("Off-CycleOrderCharge")){
						log.info("Removed off cycle order charge");
						it.remove();
					}
				}
				log.info("shipMethodV "+shipMethodV.toString());
				if(shipMethodV.size() > 1){
					throw new Exception("OutboundXpedxOrderDashboardCXMLPurchaseOrderFull ::" +
							"Multiple freights found for order > "+reqD.getOrderD().getOrderId());
				}else{
					shipMethod = ((OrderFreightData)shipMethodV.get(0)).getShortDesc();
				}
			}else{
				log.info("**** No Order Freight found ********");
			}
		}
		if (shipMethod == null) {
			return "";
		}
		log.info("return shipMethod "+shipMethod);
		return shipMethod;
	}

	public void buildTransactionContent()
	throws Exception {
		currOrder = currOutboundReq.getOrderD();
		items = currOutboundReq.getOrderItemDV();
		currItem = (OrderItemData) items.get(0);		

		final TradingPartnerData partner = translator.getPartner();
		final String poOutFin = Utility.getOutboundPONumber(currOutboundReq
				.getStoreType(), currOrder, partner, currItem.getOutboundPoNum());
		buildDocumentHeader2(currItem.getErpPoNum()); //this should NOT be the customer po number if it was entered that way.  This is because the messageId has charachater requierments that the  po does not

		final OrderAddressData shipToAddr = currOutboundReq.getShipAddr();
		final Locale locale = getLocale(currOrder.getLocaleCd());
		final String isoLang = locale.getLanguage();
		final String isoCountry = locale.getCountry();
		final Element orderRequest = mRequestEl.addElement("OrderRequest");
		final Element orderRequestHeader = createElement(orderRequest,
		"OrderRequestHeader");

		final String mPoDateTime = dateFormatter
		.format(currItem.getErpPoDate());
		addAttributes(orderRequestHeader, new String[][] {
				{ "orderID", poOutFin }, { "orderDate", mPoDateTime },
				{ "type", "new" } });
		final String sum = getTotalSum(items);
		createElement(createElement(orderRequestHeader, "Total"), "Money", sum,
				new String[][] { { "currency", currOrder.getCurrencyCd() } });
		// TODO Unintelligible: How get 'addressID'.
		final Element elemShipToAddress = createElement(createElement(
				orderRequestHeader, "ShipTo"), "Address", null, new String[][] {
			{ "isoCountryCode", isoCountry }, { "addressID", getIdForShipTo(partner,currOutboundReq) } });
		createElement(elemShipToAddress, "Name", Utility.toXMLString(currOrder.getOrderSiteName()),
				new String[][] { { "xml:lang", isoLang } });
		final Element elemShipToPostalAddress = createElement(
				elemShipToAddress, "PostalAddress", null, new String[][] { {
					"name", Utility.toXMLString(currOrder.getOrderSiteName()) } });
		createElement(elemShipToPostalAddress, "DeliverTo", Utility.toXMLString(currOrder.getOrderContactName()));
		createElementIfTextNotEmpty(elemShipToPostalAddress, "Street",
				Utility.toXMLString(shipToAddr.getAddress1()));
		createElementIfTextNotEmpty(elemShipToPostalAddress, "Street",
				Utility.toXMLString(shipToAddr.getAddress2()));
		createElementIfTextNotEmpty(elemShipToPostalAddress, "Street",
				Utility.toXMLString(shipToAddr.getAddress3()));
		createElementIfTextNotEmpty(elemShipToPostalAddress, "Street",
				Utility.toXMLString(shipToAddr.getAddress4()));
		createElement(elemShipToPostalAddress, "City", shipToAddr.getCity());
		createElement(elemShipToPostalAddress, "State", shipToAddr
				.getStateProvinceCd());
		createElement(elemShipToPostalAddress, "PostalCode", shipToAddr
				.getPostalCode());
		// TODO Is this correct getting 'Country' TEXT
		createElement(elemShipToPostalAddress, "Country", shipToAddr.getCountryCd(), new String[][] { {
			"isoCountryCode", isoCountry } });
		createElement(elemShipToAddress, "Email",
				currOrder.getOrderContactEmail(), new String[][] { { "name",
				"default" } });
		final Element phone = createElement(elemShipToAddress, "Phone", null,
				new String[][] { { "name", "work" } });
		final Element telephoneNumber = createElement(phone, "TelephoneNumber");
		createElement(telephoneNumber, "CountryCode", null, new String[][] { {
			"isoCountryCode", isoCountry } });
		createElement(telephoneNumber, "AreaOrCityCode", "");
		String phoneS;
		if(Utility.isSet(currOrder.getOrderContactPhoneNum())){
			phoneS = currOrder.getOrderContactPhoneNum();
		}else{
			phoneS = shipToAddr.getPhoneNum();
		}
		createElement(telephoneNumber, "Number", phoneS);
		final Element elemBillToAddress = createElement(createElement(
				orderRequestHeader, "BillTo"), "Address", null, new String[][] {
			// TODO Unintelligible: How get addressID, Name, PostalAddress.
			{ "isoCountryCode", isoCountry }, { "addressID", getProfile().getGroupSender() } });
		createElement(elemBillToAddress, "Name", Utility.toXMLString(currOutboundReq.getAccountName()), new String[][] { {
			"xml:lang", isoLang } });
		final OrderAddressData billToAddr = currOutboundReq.getBillAddr();
		final Element elemBillToPostalAddress = createElement(
				elemBillToAddress, "PostalAddress", null, new String[][] { {
					"name", Utility.toXMLString(currOutboundReq.getAccountName()) } });

		createElement(elemBillToPostalAddress, "DeliverTo", Utility.toXMLString(currOutboundReq
				.getPoAccountName()));
		createElementIfTextNotEmpty(elemBillToPostalAddress, "Street",
				Utility.toXMLString(billToAddr.getAddress1()));
		createElementIfTextNotEmpty(elemBillToPostalAddress, "Street",
				Utility.toXMLString(billToAddr.getAddress2()));
		createElementIfTextNotEmpty(elemBillToPostalAddress, "Street",
				Utility.toXMLString(billToAddr.getAddress3()));
		createElementIfTextNotEmpty(elemBillToPostalAddress, "Street",
				Utility.toXMLString(billToAddr.getAddress4()));
		createElement(elemBillToPostalAddress, "City", billToAddr.getCity());
		createElement(elemBillToPostalAddress, "State", billToAddr
				.getStateProvinceCd());
		createElement(elemBillToPostalAddress, "PostalCode", billToAddr
				.getPostalCode());
		createElement(elemBillToPostalAddress, "Country",
				billToAddr.getCountryCd(), new String[][] { {
					"isoCountryCode", isoCountry } });


		final Element shipping = createElement(orderRequestHeader, "Shipping");

		try{
			final String shipMethod = getShipMethod(currOutboundReq);
			createElement(shipping, "ShipMethod", shipMethod);
		}catch(Exception exc){
			throw new Exception(exc.getMessage());
		}
		createElement(shipping, "Money", "0.00000", new String[][] { {
			"currency", currOrder.getCurrencyCd() } });
		createElement(shipping, "Description", null, new String[][] { {
			"xml:lang", isoLang } });

		final Element taxOrderRequestHeader = createElement(orderRequestHeader,
		"Tax");
		createElement(taxOrderRequestHeader, "Money", "0.00000",
				new String[][] { { "currency", currOrder.getCurrencyCd() } });
		createElement(taxOrderRequestHeader, "Description", null,
				new String[][] { { "xml:lang", isoLang } });

		boolean isShipToOverride = false;
		OrderMetaDataVector omdv = currOutboundReq.getOrderMetaDV();
        if(omdv != null){
	        Iterator it = omdv.iterator();
	        while(it.hasNext()){
	        	OrderMetaData opd = (OrderMetaData) it.next();
	        	if(RefCodeNames.ORDER_PROPERTY_TYPE_CD.SHIP_TO_OVERRIDE.equals(opd.getName())){
	        		try{
	        			isShipToOverride = Utility.isTrue(opd.getValue());
	        			break;
	        		}catch(Exception e){}
	        	}
	        }
        }
		createElementIfTextNotEmpty(orderRequestHeader, "Comments",
				Utility.toXMLString(getComments(currOrder, isShipToOverride)));
		createElement(orderRequestHeader, "Extrinsic", getExtrinsicTaxable(
				currOutboundReq, currItem), new String[][] { { "name",
				"Taxable" } });
		createElement(orderRequestHeader, "Extrinsic",
				"1.000000000000000000000000", new String[][] { { "name",
				"ExchangeRate" } });
		createElement(orderRequestHeader, "Extrinsic", 
				currItem.getErpPoNum(), new String[][] { { "name",
				"OrderNum" } });
		for (int i = 0; i < items.size(); i++) {
			currItem = (OrderItemData) items.get(i);
			final Element itemOut = createElement(orderRequest, "ItemOut",
					null, new String[][] { { "quantity", Integer.toString(currItem.getDistItemQuantity()) },
					{ "lineNumber", Integer.toString(currItem.getErpPoLineNum()) },
					{ "requestedDeliveryDate", currOutboundReq.getOrderRequestedShipDate() } });

			/*final Element itemid = createElement(itemOut, "ItemID");
			createElement(itemid, "SupplierPartID",
						currItem.getDistItemSkuNum());

			if(currItem.getCustItemSkuNum()!=null){
				createElement(itemid,"BuyerPartID",
						currItem.getCustItemSkuNum());
			}*/
			createElement(createElement(itemOut, "ItemID"), "SupplierPartID",
					currItem.getDistItemSkuNum());

			BigDecimal distItemCost = currItem.getDistItemCost();
			if(currItem.getServiceFee()!=null){
				distItemCost=distItemCost.subtract(currItem.getServiceFee());
			}
			final Element itemDetail = createElement(itemOut, "ItemDetail");
			createElement(createElement(itemDetail, "UnitPrice"), "Money",
					distItemCost.toString(),
					new String[][] { { "currency", currOrder.getCurrencyCd() } });
			//item short desciprtion
			createElement(itemDetail, "Description",
					Utility.toXMLString(currItem.getItemShortDesc()),
					new String[][] { { "xml:lang", isoLang } });
			createElement(itemDetail, "UnitOfMeasure",
					getUnitOfMeasure(currItem));
			if(Utility.isSet(currItem.getCustItemSkuNum())){
			     //non standard buyer part id, as specified by xpedx on 10/22/2008
			     //<Extrinsic name="BuyerPartID">ASDF</Extrinsic> 
			     createElement(itemDetail, "Extrinsic",currItem.getCustItemSkuNum(), new String[][] { { "name", "BuyerPartID" } });
			}
			// TODO Unintelligible: How get 'Classification' TEXT.
			//createElement(itemDetail, "Classification", ,
			//		new String[][] { { "domain", "UNSPSC" } });
			final Element taxItemOut = createElement(itemOut, "Tax");
			createElement(taxItemOut, "Money", "0.00000", new String[][] { {
				"currency", currOrder.getCurrencyCd() } });
			createElement(taxItemOut, "Description", "", new String[][] { {
				"xml:lang", isoLang } });
			final Element distribution = createElement(itemOut, "Distribution");
			// TODO Unintelligible: How get much below information'.
			final Element accounting = createElement(distribution,
					"Accounting", null, new String[][] { { "name",
					"distribution charge" } });
			CostCenterData costCenter = (CostCenterData) currOutboundReq
			.getCostCenters().get("" + currItem.getCostCenterId());
			if (costCenter != null) {

				String id = modifyCostCenterCode(costCenter,currOutboundReq);

            	createElement(accounting, "Segment", null, new String[][] {
                        { "type", "cost center" },
                        { "id", id},
                        { "description", Utility.toXMLString(costCenter.getShortDesc()) } });


				/*createElement(accounting, "Segment", null, new String[][] {
						{ "type", "cost center" },
						{ "id", costCenter.getCostCenterCode()},
						{ "description", Utility.toXMLString(costCenter.getShortDesc()) } });*/
			}
			createElement(createElement(distribution, "Charge"), "Money",
					"100.00000", new String[][] { { "currency", currOrder.getCurrencyCd() },
				{ "alternateCurrency", "%" } });
			createElement(itemOut, "Comments", Utility.toXMLString(currItem.getComments()));
			currItem.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
			appendIntegrationRequest(currItem);
		}

		currOutboundReq.getPurchaseOrderD().setPurchaseOrderStatusCd(RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR);
		final XMLWriter writer = new XMLWriter(getTranslator().getOutputStream(), outputFormat);
		writer.write(mXmlDoc);
		writer.flush();
	}

	private String getComments(OrderData order, boolean isShipToOverride){
		String returnComments = "";
		if (!isShipToOverride){
			if (Utility.isSet(order.getComments())){
				returnComments += order.getComments();
			}
		}
		else {
			if (Utility.isSet(order.getComments()) && !SHIP_TO_OVERRIDE.equals(order.getComments()))
					returnComments += order.getComments() + ". PH: " + order.getOrderContactPhoneNum() + ". " + SHIP_TO_OVERRIDE;
			else
				returnComments += "PH: " + order.getOrderContactPhoneNum() + ". " + SHIP_TO_OVERRIDE;
		}
		return returnComments;		
	}
	private String modifyCostCenterCode(CostCenterData costCenter,
			OutboundEDIRequestData currOutboundReq){


		String isAllowSiteLLC = Utility.getPropertyValue(currOutboundReq.getAccountProperties(), RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SITE_LLC);
		if(isAllowSiteLLC != null && isAllowSiteLLC.equals("true")){

			String llc = Utility.getPropertyValue(currOutboundReq.getSiteProperties(), RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SITE_LLC);
			if(llc!=null && llc.length()>0){

				log.info("Site Line Level Code ::: "+llc);
				return llc;
			}
		}

		String CCcode = costCenter.getCostCenterCode();
		String newCCcode = CCcode;
		String glTransformType = Utility.getPropertyValue(currOutboundReq.getAccountProperties(), RefCodeNames.PROPERTY_TYPE_CD.GL_TRANSFORMATION_TYPE);
		if (glTransformType != null && glTransformType.equals(RefCodeNames.GL_TRANSFORMATION_TYPE.SITE_POS6_LZF4) ){
			String storeNum = Utility.getPropertyValue(currOutboundReq.getSiteProperties(),RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
			if(storeNum == null){
			    storeNum = "";
			}
			storeNum = storeNum.substring(4, 8);

			log.info("STORE NUM : "+storeNum);
			log.info("Cost Center Code "+CCcode);
			log.info("glTransformType "+glTransformType);


			DecimalFormat myFormat = new DecimalFormat("0000");
                        try{
			storeNum = myFormat.format(Double.parseDouble(storeNum));
                        }catch(Exception e){log.info("glTransformType for account = "+RefCodeNames.GL_TRANSFORMATION_TYPE.SITE_POS6_LZF4+" and store number not numberic: "+storeNum);}
                        if(newCCcode != null && newCCcode.length() > 6){
		            newCCcode = CCcode.substring(0,6)+storeNum+CCcode.substring(6,CCcode.length());
                        }else if(newCCcode == null){
                            newCCcode = storeNum;
                        }else{
                            newCCcode = newCCcode+storeNum;
                        }
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
			BigDecimal mCurrItemAdjustedCost = getPoCost(currItem);
			if(currItem.getServiceFee()!=null){
				mCurrItemAdjustedCost = mCurrItemAdjustedCost.subtract(currItem.getServiceFee());
			}
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

	public final static Locale getLocale(String localeKey) {
		if (localeKey == null) {
			throw new NullPointerException("Locale key cann't be NULL!");
		}
		//xpedx uses one non-standard locales to get around issues with their
		//admin system.  They expect these to come back to them as en_US.  
		if ("en2_US".equals(localeKey)){
			localeKey = "en_US";
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

}
