package com.cleanwise.service.apps.dataexchange;

import java.io.CharArrayWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.VisitorSupport;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.util.Iterator;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.OrderAddressData;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderFreightData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderStatusDescData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PurchaseOrderData;
import com.cleanwise.service.api.value.TradingPartnerData;
import com.cleanwise.service.api.value.TradingProfileData;
import com.cleanwise.service.api.value.TradingPropertyMapData;
import com.cleanwise.service.api.value.TradingPropertyMapDataVector;

public class OutboundISSBunzPurchaseOrder extends InterchangeOutboundSuper
        implements OutboundTransaction {
	protected Logger log = Logger.getLogger(this.getClass());
    private final static Namespace NAMESPACE_ROOT = new Namespace("",
            "urn:schemas-biztalk-org:biztalk/biztalk_1.xml");

    private final static Namespace NAMESPACE_ORDER = new Namespace("",
            "urn:www.basda.org/schema/eBIS-XML_order_v3.00.xml");

    private final static String ELEMENT_ORDER = "Order";

    private final static SimpleDateFormat sdfDateTime = new SimpleDateFormat(
            "dd/MM/yyyy HH:mm");

    private final static SimpleDateFormat sdfDate = new SimpleDateFormat(
            "dd/MM/yyyy");

    private final static SimpleDateFormat sdfTime = new SimpleDateFormat(
            "HH:mm");

    private final static NumberFormat nf1 = new DecimalFormat("#0.000");

    private final static NumberFormat nf2 = new DecimalFormat("#0.00");

    public OutboundISSBunzPurchaseOrder() {
        seperateFileForEachOutboundOrder = false;
    }

    private final static String MESSAGE_ID = "eBIS-XML_Order";

    private final static String SUBJECT = "Purchase Order";

    private final static String SCHEMA_VERSION = "3.0";

    private final static String PARAMETERS_DECIMALSEPARATOR = ".";

    private final static String PARAMETERS_PRECISION = "13.2";

    private final static String ORIGINATINGSOFTWARE_SOFTWAREMANUFACTURER = "Connexion";

    private final static String ORIGINATINGSOFTWARE_SOFTWAREPRODUCT = "Connexion";

    private final static String ORIGINATINGSOFTWARE_SOFTWAREVERSION = "1.0";

    private final static String ORDER_TYPE_CODE = "PUO";

    private final static String ORDER_TYPE_TEXT = "Purchase Order";

    private final static String CONTACT_DEPARTMENT = "1";

    private final static String TEST_FLAG = "1";

    private final static String AMOUNT_DISCOUNT = "0.00%";

    private final static String CHECKSUM = "50722";

    private final static String P_SEND_TO = "SEND_TO";

    private final static String P_EMAIL_TO = "EMAIL_TO";

    private final static String P_EMAIL_FROM = "EMAIL_FROM";

    private final static String COMMENTS[] = {
            " OpenAccounts-XML  Ver=1.0 ======================================================== ",
            " Created :         = #dateTime                                               ",
            " Type              = poptyp                                                         ",
            " Company           = 0322                                                           ",
            " Account           = 8115                                                           ",
            " Document          = 32605                                                          ",
            " Format            = C:\\openacc\\oalive60\\templates\\oa_porder.xml                    ",
            " Style Sheet       = oa_porder.xsl                                                  ",
            " Send To Address   = #sendToAddress                                    ",
            " ================================================================================== " };

    private Document mXmlDoc;

    private Element body;

    public void buildInterchangeHeader() throws Exception {
        super.buildInterchangeHeader();
        mXmlDoc = DocumentFactory.getInstance().createDocument();
        TradingPropertyMapDataVector propertyMaps = getTranslator()
                .getTradingPropertyMapDataVector();
        String sendTo = getProperty(getTranslator()
                .getTradingPropertyMapDataVector(), P_SEND_TO);
        for (String comment : COMMENTS) {
            String result = comment;
            result = result.replaceAll("#dateTime", sdfDateTime
                    .format(new Date()));
            result = result.replaceAll("#sendToAddress", sendTo);
            mXmlDoc.addComment(result);
        }
        Map map2 = new HashMap();
        map2.put("type", "text/xsl");
        map2.put("href", "oa_porder.xsl");
        mXmlDoc.addProcessingInstruction("xml-stylesheet", map2);
        final Element root = mXmlDoc.addElement("biztalk_1");
        root.add(NAMESPACE_ROOT);
        Element header = createElement(root, "header");
        Element headerDelivery = createElement(header, "delivery");
        Element message = createElement(headerDelivery, "message");
        createElement(message, "messageID", MESSAGE_ID);
        PurchaseOrderData purchaseOrder = currOutboundReq.getPurchaseOrderD();
        createElement(message, "sent", sdfDate
                .format(purchaseOrder.getPoDate())
                + "T" + sdfTime.format(purchaseOrder.getPoDate()) + "Z");
        createElement(message, "subject", SUBJECT);
        createElementWithNS(message, "SENDER", "basda", "urn:basda.org:header",
                profile.getGroupSender());
        createElementWithNS(message, "RECIPIENT", "basda",
                "urn:basda.org:header", profile.getGroupReceiver());

        Element to = createElement(headerDelivery, "to");
        createElement(to, "address", getProperty(propertyMaps, P_EMAIL_TO));
        Element from = createElement(headerDelivery, "from");
        createElement(from, "address", getProperty(propertyMaps, P_EMAIL_FROM));
        body = createElement(root, "body");
    }

    public void buildInterchangeTrailer() throws Exception {
        final OutputFormat outputFormat = new OutputFormat();
        outputFormat.setIndent(true);
        outputFormat.setIndentSize(4);
        outputFormat.setNewlines(true);
        mXmlDoc.accept(new VisitorSupport() {
            public void visit(Element element) {
                element.remove(element.getNamespace());
                if (isIncludedIn(element, ELEMENT_ORDER)) {
                    element.setQName(QName.get(element.getName(),
                            NAMESPACE_ORDER));
                } else {
                    element.setQName(QName.get(element.getName(),
                            NAMESPACE_ROOT));
                }
            }
        });
        XMLWriter writer = new XMLWriter(outputFormat);
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        writer.setWriter(charArrayWriter);
        writer.write(mXmlDoc);
        writer.close();
        String data = charArrayWriter.toString();
        getTranslator().writeOutputStream(data);
        super.buildInterchangeTrailer();
    }

    public void buildTransactionContent() throws Exception {
        OrderData order = currOutboundReq.getOrderD();
        OrderItemDataVector orderItems = currOutboundReq.getOrderItemDV();
        TradingProfileData profile = getTranslator().getProfile();
        TradingPartnerData partner = getTranslator().getPartner();
        TradingPropertyMapDataVector propertyMaps = getTranslator()
                .getTradingPropertyMapDataVector();
        PurchaseOrderData purchaseOrder = currOutboundReq.getPurchaseOrderD();
        String accountName = currOutboundReq.getAccountName();
        String distributorName = currOutboundReq.getDistributorName();
        String distributorCustomerReferenceCode = currOutboundReq
                .getDistributorCustomerReferenceCode();
        String siteName = order.getOrderSiteName();
        OrderAddressData accountAddress = currOutboundReq.getCustBillAddr();
        OrderAddressData orderAddressShipTo = currOutboundReq.getShipAddr();
        OrderAddressData orderAddressBillTo = currOutboundReq.getBillAddr();
        /** Only for testing, remove after START */
        accountAddress = orderAddressBillTo;
        /** Only for testing, remove after END */
        generateOrder(body, order, orderItems, profile, partner, propertyMaps,
                accountName, purchaseOrder, distributorName,
                distributorCustomerReferenceCode, siteName, accountAddress,
                orderAddressShipTo, orderAddressBillTo);
        
        
        purchaseOrder.setPurchaseOrderStatusCd(RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR);
        appendIntegrationRequest(purchaseOrder);
        if(orderItems != null){
	        Iterator it = orderItems.iterator();
	        while(it.hasNext()){
	        	OrderItemData orderItem = (OrderItemData) it.next();
	        	orderItem.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
	        	appendIntegrationRequest(orderItem);
	        }
        }
    }

    public Element generateOrder(Element body, OrderData order,
            OrderItemDataVector orderItems, TradingProfileData profile,
            TradingPartnerData partner,
            TradingPropertyMapDataVector propertyMaps, String accountName,
            PurchaseOrderData purchaseOrder, String distributorName,
            String distributorCustomerReferenceCode, String siteName,
            OrderAddressData accountAddress,
            OrderAddressData orderAddressShipTo,
            OrderAddressData orderAddressBillTo) throws Exception {
        OrderItemData firstOrderItem = ((OrderItemData) orderItems.get(0));
        Element orderEl = createElement(body, ELEMENT_ORDER);
        orderEl.add(NAMESPACE_ORDER);
        Element orderHeadEl = createElement(orderEl, "OrderHead");
        Element schemaEl = createElement(orderHeadEl, "Schema");
        createElement(schemaEl, "Version", SCHEMA_VERSION);
        Element parameters = createElement(orderHeadEl, "Parameters");
        createElement(parameters, "Language", order.getLocaleCd());
        createElement(parameters, "DecimalSeparator",
                PARAMETERS_DECIMALSEPARATOR);
        createElement(parameters, "Precision", PARAMETERS_PRECISION);

        Element originatingSoftware = createElement(orderHeadEl,
                "OriginatingSoftware");
        createElement(originatingSoftware, "SoftwareManufacturer",
                ORIGINATINGSOFTWARE_SOFTWAREMANUFACTURER);
        createElement(originatingSoftware, "SoftwareProduct",
                ORIGINATINGSOFTWARE_SOFTWAREPRODUCT);
        createElement(originatingSoftware, "SoftwareVersion",
                ORIGINATINGSOFTWARE_SOFTWAREVERSION);
        createElement(orderHeadEl, "TestFlag", TEST_FLAG);
        createElement(orderHeadEl, "OrderType", ORDER_TYPE_TEXT,
                new String[][] { { "Code", ORDER_TYPE_CODE } });

        Element orderCurrency = createElement(orderHeadEl, "OrderCurrency");
        createElement(orderCurrency, "Currency", order.getCurrencyCd(),
                new String[][] { { "Code", order.getCurrencyCd() } });

        Element invoiceCurrency = createElement(orderHeadEl, "InvoiceCurrency");
        createElement(invoiceCurrency, "Currency", order.getCurrencyCd(),
                new String[][] { { "Code", order.getCurrencyCd() } });
        createElement(orderHeadEl, "Checksum", CHECKSUM);

        Element orderReferences = createElement(orderEl, "OrderReferences");
        createElement(orderReferences, "BuyersOrderNumber", firstOrderItem
                .getErpPoNum(), new String[][] { { "Preserve", "true" } });
        createElement(orderReferences, "SuppliersOrderReference", purchaseOrder
                .getOutboundPoNum(), new String[][] { { "Preserve", "true" } });

        createElement(orderEl, "OrderDate", sdfDate.format(firstOrderItem
                .getErpPoDate()));

        Element supplier = createElement(orderEl, "Supplier");
        Element supplierReferences = createElement(supplier,
                "SupplierReferences");
        createElement(supplierReferences, "BuyersCodeForSupplier",
                distributorCustomerReferenceCode);
        createElement(supplier, "Party", distributorName);

        Element buyer = createElement(orderEl, "Buyer");
        createElement(buyer, "Party", accountName);
        Element address = createElement(buyer, "Address");
        createElementIfTextNotEmpty(address, "AddressLine", accountAddress
                .getAddress1());
        createElementIfTextNotEmpty(address, "AddressLine", accountAddress
                .getAddress2());
        createElementIfTextNotEmpty(address, "AddressLine", accountAddress
                .getAddress3());
        createElementIfTextNotEmpty(address, "AddressLine", accountAddress
                .getAddress4());
        createElement(address, "PostCode", accountAddress.getPostalCode());

        Element contact = createElement(buyer, "Contact");
        createElement(contact, "Name", order.getOrderContactName());
        createElement(contact, "Switchboard", order.getOrderContactPhoneNum());
        createElement(contact, "Department", CONTACT_DEPARTMENT);

        Element bodyDelivery = createElement(orderEl, "Delivery");
        Element bodyDeliveryTo = createElement(bodyDelivery, "DeliverTo");
        createElement(bodyDeliveryTo, "Party", siteName);
        Element bodyDeliveryToAddress = createElement(bodyDeliveryTo, "Address");
        createElementIfTextNotEmpty(bodyDeliveryToAddress, "AddressLine",
                orderAddressShipTo.getAddress1());
        createElementIfTextNotEmpty(bodyDeliveryToAddress, "AddressLine",
                orderAddressShipTo.getAddress2());
        createElementIfTextNotEmpty(bodyDeliveryToAddress, "AddressLine",
                orderAddressShipTo.getAddress3());
        createElementIfTextNotEmpty(bodyDeliveryToAddress, "AddressLine",
                orderAddressShipTo.getAddress4());
        createElement(bodyDeliveryToAddress, "PostCode", orderAddressShipTo
                .getPostalCode());
        createElement(bodyDeliveryTo, "DeliverToReferences", 
        		String.valueOf(order.getSiteId()));
       
        Element bodyInvoiceTo = createElement(orderEl, "InvoiceTo");
        createElement(bodyInvoiceTo, "Party", accountName);
        Element bodyInvoiceToAddress = createElement(bodyInvoiceTo, "Address");
        createElementIfTextNotEmpty(bodyInvoiceToAddress, "AddressLine",
                orderAddressBillTo.getAddress1());
        createElementIfTextNotEmpty(bodyInvoiceToAddress, "AddressLine",
                orderAddressBillTo.getAddress2());
        createElementIfTextNotEmpty(bodyInvoiceToAddress, "AddressLine",
                orderAddressBillTo.getAddress3());
        createElementIfTextNotEmpty(bodyInvoiceToAddress, "AddressLine",
                orderAddressBillTo.getAddress4());
        createElement(bodyInvoiceToAddress, "PostCode", orderAddressBillTo
                .getPostalCode());

        BigDecimal taxTotal = new BigDecimal(0);
        BigDecimal goodsValue = new BigDecimal(0);
        for (int i = 0; orderItems != null && i < orderItems.size(); i++) {
            OrderItemData orderItem = (OrderItemData) orderItems.get(i);
            Element orderLine = createElement(orderEl, "OrderLine");
            createElement(orderLine, "LineNumber", ""
                    + orderItem.getErpPoLineNum(), new String[][] { {
                    "Preserve", "true" } });
            Element product = createElement(orderLine, "Product");
            createElement(product, "Description", orderItem.getItemShortDesc());
            createElement(product, "BuyersProductCode", orderItem
                    .getDistItemSkuNum());
            Element quantity = createElement(orderLine, "Quantity");
            createElement(quantity, "Amount", nf1.format(orderItem
                    .getDistItemQuantity()));
            Element price = createElement(orderLine, "Price");
            BigDecimal unitPrice = orderItem.getDistItemCost().setScale(2,
                    BigDecimal.ROUND_HALF_UP);
            createElement(price, "UnitPrice", nf2.format(unitPrice
                    .doubleValue()));
            Element amountDiscount = createElement(orderLine, "AmountDiscount");
            createElement(amountDiscount, "Amount", AMOUNT_DISCOUNT);
            BigDecimal lineTotal = unitPrice.multiply(new BigDecimal(orderItem
                    .getDistItemQuantity()));
            createElement(orderLine, "LineTotal", nf2.format(lineTotal
                    .doubleValue()));
            Element delivery = createElement(orderLine, "Delivery");
            createElement(delivery, "PreferredDate", "");
            createElement(orderLine, "Narrative", "");

            if (orderItem.getTaxAmount() != null) {
                taxTotal = taxTotal.add(orderItem.getTaxAmount());
            }
            if (lineTotal != null) {
                goodsValue = goodsValue.add(lineTotal);
            }
        }
        // TODO Was taken from Oubtound850.java
        String mReqshipdate = null;
        if (!RefCodeNames.STORE_TYPE_CD.MLA.equals(currOutboundReq
                .getStoreType())) {
            mReqshipdate = OrderStatusDescData
                    .getRequestedShipDate(currOutboundReq.getOrderMetaDV());
            if (Utility.isSet(mReqshipdate)) {
                // convert the date from "MM/dd/yyyy" format to "yyyyMMdd"
                SimpleDateFormat dateFormatterFrom = new SimpleDateFormat(
                        "MM/dd/yyyy");
                // dateFormatterFrom.setTimeZone(TimeZone.getTimeZone(translator.getProfile().getTimeZone()));
                java.util.Date reqDelDate;
                try {
                    reqDelDate = dateFormatterFrom.parse(mReqshipdate);
                    mReqshipdate = sdfDate.format(reqDelDate);
                } catch (Exception e) {
                    log.info("ERROR BAD REQUESTED SHIP DATE: "
                            + mReqshipdate);
                    mReqshipdate = null;
                }
            }
        }
        
        String msg;
        OrderFreightData freightInfo = currOutboundReq.getFreightInfo();
        if (freightInfo != null) {
            msg = freightInfo.getShortDesc();
            if (Utility.isSet(msg)) {
                createElement(orderEl, "Narrative", msg);
            }
        }
        msg = order.getComments();
        if (Utility.isSet(msg)) {
            createElement(orderEl, "Narrative", msg);
        }
        if (RefCodeNames.STORE_TYPE_CD.MLA.equals(currOutboundReq
                .getStoreType()) == false
                && Utility.isSet(mReqshipdate)) {
            msg = "Deliver before: " + mReqshipdate;
            createElement(orderEl, "Narrative", msg);
        }
        PropertyData pd = Utility.getProperty(currOutboundReq
                .getSiteProperties(),
                RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG);
        String siteComments = null;
        if (pd != null) {
            siteComments = pd.getValue();
        }
        if (Utility.isSet(siteComments)) {
            createElement(orderEl, "Narrative", siteComments);
        }
        Element orderTotal = createElement(orderEl, "OrderTotal");
        createElement(orderTotal, "GoodsValue", "" + goodsValue);
        createElement(orderTotal, "TaxValue", "" + taxTotal);
        createElement(orderTotal, "GrossValue", "" + goodsValue.add(taxTotal));
        return body;
    }

    public String getTranslationReport() {
        if (getTransactionsToProcess().size() == 0) {
            return "no records translated";
        }
        return "Successfully processed " + getTransactionsToProcess().size()
                + " records";
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

    private String getProperty(TradingPropertyMapDataVector properties,
            String key) {
        for (int i = 0; properties != null && i < properties.size(); i++) {
            TradingPropertyMapData item = (TradingPropertyMapData) properties
                    .get(i);
            if (RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE.equals(item
                    .getEntityProperty())
                    && RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP
                            .equals(item.getTradingPropertyMapCode())
                    && key.equals(item.getPropertyTypeCd())) {
                return item.getHardValue();
            }
        }
        return null;
    }

    private static Element createElementWithNS(final Element parent,
            final String elementName, final String nsName, final String nsURI,
            final String elementTextValue) {
        final Element element = parent.addElement(elementName);
        element.addNamespace(nsName, nsURI);
        element.setName(nsName + ":" + elementName);
        element.setText(elementTextValue != null ? elementTextValue : "");
        return element;
    }

    private final static boolean isIncludedIn(Element element,
            String includedName) {
        if (element != null && includedName != null) {
            do {
                if (includedName.equals(element.getName())) {
                    return true;
                }
                element = element.getParent();
            } while (element != null);
        }
        return false;
    }
    
    public String getFileExtension(){
    	return ".xml";
    }
}
