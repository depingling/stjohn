package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.process.operations.Outbound850TxtSafewayBuilder;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;
import org.apache.log4j.Logger;


import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


public class Outbound850TxtSafeway extends InterchangeOutboundSuper implements OutboundTransaction {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMddyyyy");
    private final static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("KKmmss");
    public static final String PROPERTY_SITE_DIVISION  = "division name";
    public static final String PROPERTY_STORE_NUMBER  = "store";

    protected Logger logger = Logger.getLogger(this.getClass());

    private Outbound850TxtSafewayBuilder _documentBuilder;

    public Outbound850TxtSafeway() {
        seperateFileForEachOutboundOrder = true;
        _documentBuilder = null;
    }

    public void buildInterchangeHeader() throws Exception {
    	super.buildInterchangeHeader();
        _documentBuilder = new Outbound850TxtSafewayBuilder("Outbound850TxtSafeway");

        OrderAddressData accountAddress = currOutboundReq.getCustBillAddr();
        PropertyDataVector siteProperties = currOutboundReq.getSiteProperties();
        PurchaseOrderData purchaseOrder = currOutboundReq.getPurchaseOrderD();

        String companyCode = "0000";
        String siteDivision = getSitePropertyValue(siteProperties, PROPERTY_SITE_DIVISION);
        if("Denver".equalsIgnoreCase(siteDivision)){
        	siteDivision = "05";
        }else if("Phoenix".equalsIgnoreCase(siteDivision)){
        	siteDivision = "17";
        }else if("Portland".equalsIgnoreCase(siteDivision)){
        	siteDivision = "19";
        }else if("N. California".equalsIgnoreCase(siteDivision)){
        	siteDivision = "25";
        }else if("Spokane/Seattle".equalsIgnoreCase(siteDivision)){
        	siteDivision = "27";
        }else if("Eastern".equalsIgnoreCase(siteDivision)){
        	siteDivision = "35";
        }else if("US Supply".equalsIgnoreCase(siteDivision)){
        	siteDivision = "45";
        }else if("Alberta".equalsIgnoreCase(siteDivision)){
        	siteDivision = "7F";
        }else if("British Columbia".equalsIgnoreCase(siteDivision)){
        	siteDivision = "7Y";
        }else if("Prairie Provinces".equalsIgnoreCase(siteDivision)){
        	siteDivision = "73";
        }else if("Corporate".equalsIgnoreCase(siteDivision)){
        	siteDivision = "96";
        }else if("Cdn Supply".equalsIgnoreCase(siteDivision)){
        	siteDivision = "60";
        }else{
        	throw new Exception("Unknown Division Name: "+siteDivision+"  must be known type to map to appropriate code for Safeway");
        }
        String storeNumber = getSitePropertyValue(siteProperties, PROPERTY_STORE_NUMBER);
        String purchaseOrderType = "ORD";
        String purchaseOrderNumber = purchaseOrder.getOutboundPoNum();

        if (accountAddress != null) {
            if (RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES.equals(accountAddress.getCountryCd()) ||
                "USA".equals(accountAddress.getCountryCd()) ||
                "US".equals(accountAddress.getCountryCd())) {
                companyCode = "1101";
            }
            if (RefCodeNames.ADDRESS_COUNTRY_CD.CANADA.equals(accountAddress.getCountryCd()) ||
                "Canada".equals(accountAddress.getCountryCd())) {
                companyCode = "2101";
            }
        }

        Outbound850TxtSafewayBuilder.CommonArea commonArea = _documentBuilder.createCommonArea();
        commonArea.setCompanyCode(companyCode);
        commonArea.setSiteDivision(siteDivision);
        commonArea.setStoreNumber(storeNumber);
        commonArea.setPurchaseOrderType(purchaseOrderType);
        commonArea.setPurchaseOrderNumber(purchaseOrderNumber);
        _documentBuilder.setCommonArea(commonArea);
    }

    public void buildTransactionContent() throws Exception {
        OrderData order = currOutboundReq.getOrderD();
        OrderItemDataVector orderItems = currOutboundReq.getOrderItemDV();

        String orderCreationDate = DATE_FORMAT.format(order.getOriginalOrderDate());
        String orderCreationTime = DATE_FORMAT.format(order.getOriginalOrderTime());
        String processDate = DATE_FORMAT.format(new Date());
        String deliveryDate = "";

        Outbound850TxtSafewayBuilder.OrderArea orderArea = _documentBuilder.createOrderArea();
        Outbound850TxtSafewayBuilder.HeaderArea headerArea = _documentBuilder.createHeaderArea();

        headerArea.setOrderCreationDate(orderCreationDate);
        headerArea.setOrderCreationTime(orderCreationTime);
        headerArea.setProcessDate(processDate);
        headerArea.setDeliveryDate(deliveryDate);

        if (orderItems != null && orderItems.size() > 0) {
            for (int i = 0; i < orderItems.size(); ++i) {
                OrderItemData orderItem = (OrderItemData)orderItems.get(i);
                Outbound850TxtSafewayBuilder.DetailArea detail = _documentBuilder.createDetailArea();
                buildOrderItemContent(orderItem, detail);
                orderArea.addDetailArea(detail);
            }
        }
        orderArea.setHeaderArea(headerArea);
        _documentBuilder.addOrderArea(orderArea);
    }

    private void buildOrderItemContent(OrderItemData orderItem, Outbound850TxtSafewayBuilder.DetailArea detail) throws Exception {
        String dataBatch = "0167";
        String whItemId = "";
        String brItemCd = "";
        String corpItemCd = orderItem.getCustItemSkuNum();
        String mclBr = "";
        String productUpc = orderItem.getManuItemUpcNum();
        String orderItemQuantity = String.valueOf(orderItem.getDistItemQuantity());
        String skuNumber = orderItem.getCustItemSkuNum();

        if (skuNumber == null || skuNumber.length() == 0) {
            skuNumber = orderItem.getDistItemSkuNum();
        }

        detail.setDataBatch(dataBatch);
        detail.setWhItemId(whItemId);
        detail.setBrItemCd(brItemCd);
        detail.setCorpItemCd(corpItemCd);
        detail.setMclBr(mclBr);
        detail.setProductUpc(productUpc);
        detail.setOrderItemQuantity(orderItemQuantity);
        detail.setSkuNumber(skuNumber);
    }

    public void buildTransactionTrailer() throws Exception {
        writeData(getTranslator().getOutputStream());
        getTranslator().getOutputStream().flush();
    }


    private void writeData(OutputStream out) {
        if (out == null) {
            logger.error("Output stream to write 'TxtSafeway' document is not defined.");
            return;
        }
        if (_documentBuilder == null) {
            logger.error("Data for the 'TxtSafeway' document is not defined.");
            return;
        }

        logger.info("### [Outbound850TxtSafeway.writeData] Document: " + _documentBuilder);

        ArrayList<Outbound850TxtSafewayBuilder.ErrorInfo> errors = _documentBuilder.validate();
        if (errors != null && errors.size() > 0) {
            StringBuilder buff = new StringBuilder();
            for (int i = 0; i < errors.size(); ++i) {
                Outbound850TxtSafewayBuilder.ErrorInfo error = (Outbound850TxtSafewayBuilder.ErrorInfo)errors.get(i);
                buff.append(error.toString());
                buff.append("\r\n");
            }
            logger.error("Document is not valid. " + buff.toString());
            throw new RuntimeException("[Outbound850TxtSafeway.writeData] Document is not valid. " + buff.toString());
        }
        try {
            logger.debug("Try to write the 'TxtSafeway' document into stream.");
            _documentBuilder.writeData(out);
        } catch (Exception ex) {
            logger.info("[Outbound850TxtSafeway.writeData] An error occurred at writing 'TxtSafeway' document into stream. " + ex.getMessage());
            logger.error("An error occurred at writing 'TxtSafeway' document into stream. " + ex.getMessage());
        }
    }

    private String getSitePropertyValue(PropertyDataVector properties, String name) {
        if (properties == null || name == null) {
            return "";
        }
        String value = "";
        Iterator it = properties.iterator();
        while( it.hasNext()) {
            PropertyData prop = (PropertyData) it.next();
            if (prop.getPropertyTypeCd().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD) && 
                prop.getShortDesc().equalsIgnoreCase(name)) {
                value = prop.getValue();
                break;
            }
        }
        return value;
    }

}
