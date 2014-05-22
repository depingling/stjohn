package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.value.*;
import org.dom4j.*;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;


/**
 * Class for parsing XML Invoice form ISSEaton.
 * 
 * Was made workarround for working with default namespace like
 * xmlns="urn:biztalk-org:biztalk:biztalk_1". Was taken from
 * "http://www.edankert.com/defaultnamespaces.html"
 * 
 * @author alukovnikov
 */
public class InboundISSEatonXMLInvoice extends InboundXMLSuper {
	protected Logger log = Logger.getLogger(this.getClass());
    private final static String XML_HEADER_SUBJECT = "//a:biztalk_1/a:header/a:delivery/a:message/a:subject";

    private final static String XML_INVOICE = "//a:biztalk_1/a:body/b:Invoice";

    private final static String XML_INVOICE_TYPE = "b:InvoiceHead/b:InvoiceType";

    private final static String XML_INVOICE_CURRENCY = "b:InvoiceHead/b:InvoiceCurrency/b:Currency";

    private final static String XML_PO_NUMBER = "b:InvoiceReferences/b:BuyersOrderNumber";

    private final static String INVOICE = "Invoice";

    private final static String CODE_ATTR = "Code";

    private final static String CODE_ATTR_VALUE = "INV";

    private final static String ACTION_ATTR_VALUE = "Add";

    private final static SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy-MM-dd");

    private final static Map NS = new HashMap();
    static {
        NS.put("a", "urn:biztalk-org:biztalk:biztalk_1");
        NS.put("b", "urn:www.basda.org/schema/eBIS-XML_invoice_v3.00.xml");
    }

    public void translate(Node nodeToOperateOn) throws Exception {
        boolean globalError = false;
        IntegrationRequestsVector integrationRequests = new IntegrationRequestsVector();
        APIAccess api = new APIAccess();
        IntegrationServices services = api.getIntegrationServicesAPI();
        try {
            Document doc = nodeToOperateOn.getDocument();
            Node subject = selectSingleNode(doc, XML_HEADER_SUBJECT);
            if (subject == null) {
                appendErrorMsgs("Could not parse request node.", globalError);
            } else if (INVOICE.equals(subject.getText()) == false) {
                appendErrorMsgs("Incorrect subject type '" + XML_HEADER_SUBJECT
                        + "':" + subject.getText(), globalError);
            }
            List invoices = selectNodes(doc, XML_INVOICE);
            log.info("Found " + invoices.size() + " invoices in the file.");
            List poNums = new ArrayList();
            for (int i = 0; i < invoices.size(); i++) {
                Node invoice = (Node) invoices.get(i);
                Node poNumberNode = selectSingleNode(invoice, XML_PO_NUMBER);
                if (poNumberNode == null) {
                    appendErrorMsgs("Not found PO_NUMBER for '" + XML_INVOICE
                            + "[" + i + "]" + XML_HEADER_SUBJECT + "'",
                            globalError);
                } else {
                    poNums.add(poNumberNode.getText());
                }
            }
            log.info("Getting orders... for PO_NUMBERs:" + poNums);
            Map orders = getOrders(poNums);
            for (int i = 0; i < invoices.size(); i++) {
                InvoiceRequestData reqInvoice = InvoiceRequestData
                        .createValue();
                InvoiceDistData invoiceDistD = InvoiceDistData.createValue();
                log.info("Processing " + i + " invoice...");
                Node invoice = (Node) invoices.get(i);
                Node invoiceType = selectSingleNode(invoice, XML_INVOICE_TYPE);
                String poNumber = getString(invoice, XML_PO_NUMBER);
                //bunzl postfixes everything with a , and then a number...ignore this data
                int endIdx = poNumber.lastIndexOf(",");
                if(endIdx > 0){
                	poNumber = poNumber.substring(0, endIdx);
                }
                OrderData orderD = (OrderData) orders.get(poNumber);
                if (orderD == null) {
                    appendErrorMsgs("Couldn't find order for " + i
                            + " invoice. PO Number =>" + poNumber, globalError);
                }
                invoiceDistD.setErpPoNum(poNumber);
                if (invoiceType == null) {
                    appendErrorMsgs("Couldn't find '" + XML_INVOICE_TYPE
                            + "' for " + i + " invoice.", globalError);
                } else {
                    String invoiceTypeCode = invoiceType.valueOf("@"
                            + CODE_ATTR);
                    if (invoiceTypeCode == null
                            || CODE_ATTR_VALUE.equals(invoiceTypeCode) == false) {
                        appendErrorMsgs("Not found or incorrect attribute '"
                                + XML_INVOICE + "[" + i + "]/"
                                + XML_INVOICE_TYPE + "@" + CODE_ATTR + "':"
                                + invoiceTypeCode, globalError);
                    }
                }
                Node currencyType = selectSingleNode(invoice,
                        XML_INVOICE_CURRENCY);
                if (currencyType == null) {
                    appendErrorMsgs("Not found or incorrect attribute '"
                            + XML_INVOICE + "[" + i + "]/"
                            + XML_INVOICE_CURRENCY + "@" + CODE_ATTR + "':"
                            + currencyType, globalError);
                } else {
                    String currencyTypeCode = currencyType.valueOf("@"
                            + CODE_ATTR);
                    if (currencyTypeCode == null
                            || (orderD != null && orderD.getCurrencyCd()
                                    .equals(currencyTypeCode) == false)) {
                        appendErrorMsgs(
                                "Not found currency attribute or not equals with order currency '"
                                        + XML_INVOICE + "[" + i + "]/"
                                        + XML_INVOICE_CURRENCY + "@"
                                        + CODE_ATTR + "':" + currencyTypeCode
                                        + ". Order currency:"
                                        + orderD.getCurrencyCd(), globalError);
                    }
                }
                
                String invoiceNumber = getString(invoice,
                        "b:InvoiceReferences/b:SuppliersInvoiceNumber");
                log.info("invoice number="+invoiceNumber);
                String invoiceDate = getString(invoice, "b:InvoiceDate");
                String shipFromName = getString(invoice, "b:Supplier/b:Party");
                // invoiceDistD.setInvoiceStatusCd();
                invoiceDistD.setInvoiceNum(invoiceNumber);
                invoiceDistD.setShipFromName(shipFromName);
                invoiceDistD.setInvoiceDate(sdf.parse(invoiceDate));
                OrderAddressData shipFromAddress = getAddress(selectSingleNode(
                        invoice, "b:Supplier/b:Address"),
                        RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
                OrderAddressData shipToAddress = getAddress(selectSingleNode(
                        invoice, "b:InvoiceTo/b:Address"),
                        RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_SHIPPING);
                InvoiceDistDetailDataVector distDetails = getLines(invoice, i);
                int numberOfLines = getInt(invoice,
                        "b:InvoiceTotal/b:NumberOfLines");
                if (numberOfLines != distDetails.size()) {
                    appendErrorMsgs(
                            "Value '.../InvoiceTotal/NumberOfLines' not equals with count of '.../InvoiceLine/' for '"
                                    + XML_INVOICE + "[" + i + "]'", globalError);
                }
                double lineValueTotal = getDouble(invoice,
                        "b:InvoiceTotal/b:LineValueTotal");
                double taxableTotal = getDouble(invoice,
                        "b:InvoiceTotal/b:TaxableTotal");
                if (lineValueTotal != taxableTotal) {
                    appendErrorMsgs(
                            "Value '.../InvoiceTotal/LineValueTotal' not equals with value '.../InvoiceTotal/TaxableTotal' for '"
                                    + XML_INVOICE + "[" + i + "]'", globalError);
                }
                double taxTotal = getDouble(invoice,
                        "b:InvoiceTotal/b:TaxTotal");
                double netPaymentTotal = getDouble(invoice,
                        "b:InvoiceTotal/b:NetPaymentTotal");
                double grossPaymentTotal = getDouble(invoice,
                        "b:InvoiceTotal/b:GrossPaymentTotal");
                invoiceDistD.setSalesTax(new BigDecimal(taxTotal));
                if (netPaymentTotal != grossPaymentTotal) {
                    appendErrorMsgs(
                            "Value '.../InvoiceTotal/NetPaymentTotal' not equals with value '.../InvoiceTotal/GrossPaymentTotal' for '"
                                    + XML_INVOICE + "[" + i + "]'", globalError);
                }
                if (taxableTotal + taxTotal != netPaymentTotal) {
                    appendErrorMsgs(
                            "Amount ('.../InvoiceTotal/TaxableTotal' + '.../InvoiceTotal/TaxTotal') not equals with value '.../InvoiceTotal/GrossPaymentTotal' for "
                                    + XML_INVOICE + "[" + i + "]'", globalError);
                }
                // if (orderD != null) {
                // invoiceDistD.setOrderId(orderD.getOrderId());
                // Order order = APIAccess.getAPIAccess().getOrderAPI();
                // order.updateInvoiceDist(invoiceDistD);
                // shipToAddress.setOrderId(orderD.getOrderId());
                // shipFromAddress.setOrderId(orderD.getOrderId());
                // order.updateOrderAddress(shipToAddress);
                // order.updateOrderAddress(shipFromAddress);
                // for (int ii = 0; ii < distDetails.size(); ii++) {
                // InvoiceDistDetailData item = (InvoiceDistDetailData)
                // distDetails
                // .get(ii);
                // item.setInvoiceDistId(invoiceDistD.getInvoiceDistId());
                // order.updateInvoiceDistDetail(item);
                // }
                // }
                // if(distAccRefNum != null){
                // reqInvoice.setDistributorAccountRefNum(invoiceDistD);
                // }
                reqInvoice.setInvoiceD(invoiceDistD);
                reqInvoice.setInvoiceDetailDV(distDetails);
                reqInvoice.setSkuTypeCd(getTranslator().getPartner().getSkuTypeCd());
                // reqInvoice.setDistributorCompanyCode(distributorCompanyCode);
                // reqInvoice.setControlTotalSum(controlTotalSumInvoice);
                integrationRequests.add(reqInvoice);
            }
            services.processIntegrationRequests(integrationRequests, null,
                    getTranslator().getPartner().getTradingPartnerId());
        } catch (Exception e) {
        	e.printStackTrace();
            appendErrorMsgs(e, true);
        }
        for (int i = 0; i < errorMsgs.size(); i++) {
            Object error = errorMsgs.get(i);
            log.error(error);
        }
    }

    private OrderAddressData getAddress(Node node, String type) {
        OrderAddressData addr = OrderAddressData.createValue();
        List addressLines = selectNodes(node, "b:AddressLine");
        addressLines.add(selectSingleNode(node, "b:Street"));
        addr.setCity(getString(node, "b:City"));
        addr.setStateProvinceCd(getString(node, "b:State"));
        addr.setPostalCode(getString(node, "b:PostCode"));
        initAddressLines(addr, addressLines);
        addr.setAddressTypeCd(type);
        return addr;
    }

    private static void initAddressLines(OrderAddressData addr, List nodes) {
        addr.setAddress1((nodes.size() > 0) ? getString((Node) nodes.get(0))
                : null);
        addr.setAddress2((nodes.size() > 1) ? getString((Node) nodes.get(1))
                : null);
        addr.setAddress3((nodes.size() > 2) ? getString((Node) nodes.get(2))
                : null);
        addr.setAddress4((nodes.size() > 3) ? getString((Node) nodes.get(3))
                : null);
    }

    private InvoiceDistDetailDataVector getLines(Node node, int invoiceNum) {
        List lines = selectNodes(node, "b:InvoiceLine");
        log.info("Found " + lines.size() + " invoice lines");
        InvoiceDistDetailDataVector res = new InvoiceDistDetailDataVector();
        for (int i = 0; lines != null && i < lines.size(); i++) {
            Node line = (Node) lines.get(i);
            String actionValue = line.valueOf("@Action");
            if (actionValue == null
                    && ACTION_ATTR_VALUE.equals(actionValue) == false) {
                appendErrorMsgs("Not found or not equals attribute '"
                        + ACTION_ATTR_VALUE + "'", false);
            }
            InvoiceDistDetailData iddd = InvoiceDistDetailData.createValue();
            iddd.setDistLineNumber(getInt(line, "b:LineNumber"));
            iddd.setDistItemSkuNum(getString(line,
                    "b:Product/b:SuppliersProductCode"));
            iddd.setDistItemShortDesc(getString(line, "b:Product/b:Description"));
            iddd.setDistItemUom(getString(line, "b:Quantity", "UOMCode"));
            iddd.setDistItemQuantity(getInt(line, "b:Quantity/b:Amount"));
            iddd.setItemReceivedCost(new BigDecimal(getString(line,
                    "b:Price/b:UnitPrice")));
            iddd.setItemCost(iddd.getItemCost());
            log.info("Adding line to invoice: "+iddd.getDistItemSkuNum()+" qty("+iddd.getDistItemQuantity()+")");
            res.add(iddd);
        }
        return res;
    }

    private Map getOrders(List poNums) throws Exception {
        Map map = new HashMap();
        for (int i = 0; poNums != null && i < poNums.size(); i++) {
            String poNum = (String) poNums.get(i);
            OrderData orderD;
            orderD = getTranslator().getOrderDataByErpPoNum(poNum);
            if (orderD != null) {
                map.put(poNum, orderD);
            }
        }
        return map;
    }

    private static String getString(Node node, String xpath, String attribute) {
        Node child = selectSingleNode(node, xpath);
        return (child == null) ? null : child.valueOf("@" + attribute);
    }

    private static String getString(Node node, String xpath) {
        return getString(selectSingleNode(node, xpath));
    }

    private static String getString(Node node) {
        return (node == null) ? null : node.getText();
    }

    private static int getInt(Node node, String xpath) {
        return getInt(selectSingleNode(node, xpath));
    }

    private static int getInt(Node node) {
        return (node == null) ? 0 : Utility.parseInt(node.getText());
    }

    private static double getDouble(Node node, String xpath) {
        return getDouble(selectSingleNode(node, xpath));
    }

    private static double getDouble(Node node) {
        try {
            return Double.parseDouble(node.getText());
        } catch (Exception e) {
            return 0d;
        }
    }

    private final static Node selectSingleNode(Object context, String pXpath) {
        XPath xpath = DocumentFactory.getInstance().createXPath(pXpath);
        xpath.setNamespaceURIs(NS);
        return xpath.selectSingleNode(context);
    }

    private static List selectNodes(Object context, String pXpath) {
        XPath xpath = DocumentFactory.getInstance().createXPath(pXpath);
        xpath.setNamespaceURIs(NS);
        return xpath.selectNodes(context);
    }
}
