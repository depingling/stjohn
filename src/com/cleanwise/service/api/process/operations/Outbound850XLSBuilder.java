package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.reporting.ReportingUtils;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.IOUtilities;
import com.cleanwise.service.api.value.*;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import org.apache.log4j.Logger;

/**
 * Title:        Outbound850XLSBuilder
 * Description:  Constructs a xls document in the form of a xls document.
 * Purpose:      Construct a outbound purchase order xls.
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         24.08.2007
 * Time:         0:45:37
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class Outbound850XLSBuilder extends XlsBuilder {
    private static final Logger log = Logger.getLogger(Outbound850XLSBuilder.class);

    private static String className="Outbound850XLSBuilder";

    private static final char ADRESS_DELIM = 32;

    public Outbound850XLSBuilder() {
    }

    /**
     * Takes in an instance of @see OutboundEDIRequestData, @see DistributorData, and @see StoreData
     * and converts them into an outbound purchase order xls.  This document
     * looks very similar to the output when printing from directly from Lawson.
     *
     * @param pDist      the distributor this fax is going to.
     * @param p850s      the purchase orders to translate.
     * @param pStore     the store this po is comming from.
     * @param pOut       the output stream to write the xls to.
     * @throws java.io.IOException if any error occurs.
     */
    public void constructXlsPO(DistributorData pDist,
                               OutboundEDIRequestDataVector p850s,
                               StoreData pStore,
                               OutputStream pOut) throws IOException {

        if (p850s.size() == 0) {
            throw new IllegalArgumentException("OutboundEDIRequestDataVector size cannot be emtpy");
        }

        ArrayList lpos = new ArrayList();
        for (int i = 0, len = p850s.size(); i < len; i++) {
            OutboundEDIRequestData ediReq = (OutboundEDIRequestData) p850s.get(i);
            PropertyData ssmProp = null;
            if (ediReq.getSiteProperties() != null) {
                ssmProp = Utility.getProperty(ediReq.getSiteProperties(), RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG);
            }

            String siteShipMsg = null;
            if (ssmProp != null) {
                siteShipMsg = ssmProp.getValue();
            }

            String acctName = ediReq.getAccountName();
            if (acctName == null) {
                acctName = "";
            }
            Outbound850XLSBuilder.XlsPoStruct po =
                    new Outbound850XLSBuilder.XlsPoStruct
                            (pStore,ediReq.getOrderD(),
                                    ediReq.getOrderMetaDV(),
                                    ediReq.getPurchaseOrderD(),
                                    ediReq.getOrderItemDV(),
                                    ediReq.getShipAddr(),
                                    ediReq.getBillAddr(),
                                    acctName,
                                    ediReq.getShipVia(),
                                    siteShipMsg,
                                    ediReq.getSiteProperties(),
                                    ediReq.getAccountProperties(),
                                    ediReq.getOrderPropertyDV());
            lpos.add(po);
        }
        generateXls(pStore,pDist, lpos, pOut);
    }

    private void generateXls(StoreData pStore,DistributorData pDist, ArrayList lpos, OutputStream pOut) throws IOException {
        if(pStore!=null){
            try {

                GenericReportColumnViewVector header = makeXlsHeader();
                ArrayList body = makeXlsBody(lpos);
                writeXls(header,body,pOut);

            } catch (Exception e) {
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        }
    }

    private void writeXls(GenericReportColumnViewVector header, ArrayList body, OutputStream out) throws Exception {
        GenericReportResultView result = GenericReportResultView.createValue();
        result.setTable(body);
        result.setColumnCount(header.size());
        result.setHeader(header);
        result.setName("POs");
        writeExcelReport(result,out);
    }

    private ArrayList makeXlsBody(ArrayList lpos) {
        ArrayList body = new ArrayList();
        Iterator itar=lpos.iterator();
        while(itar.hasNext()){
            XlsPoStruct poStruct=(XlsPoStruct)itar.next();
            body.addAll(getTableData(poStruct));
        }
        return body;
    }

    private ArrayList getTableData(XlsPoStruct poStruct) {

        String address  = getShippingAddress(poStruct);
        String siteName = poStruct.getShipAddr()!=null?poStruct.getShipAddr().getShortDesc():"";
        String city     = poStruct.getShipAddr()!=null?poStruct.getShipAddr().getCity():"";
        String state    = poStruct.getShipAddr()!=null?poStruct.getShipAddr().getStateProvinceCd():"";
        String postalcd = poStruct.getShipAddr()!=null?poStruct.getShipAddr().getPostalCode():"";
        String currency = poStruct.getOrderD().getCurrencyCd();

        String siteRef;
        if(poStruct.getSiteProperties()!=null){
	        siteRef = Utility.getPropertyValue(poStruct.getSiteProperties(),RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
/*	        Iterator it = poStruct.getSiteProperties().iterator();
	        siteRef="";
	        while(it.hasNext()){
	            PropertyData prop = (PropertyData) it.next();
	            siteRef="----"+prop.getShortDesc()+"::"+prop.getValue();
	            if(prop.getShortDesc().equalsIgnoreCase(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER)){
	            	siteRef = prop.getValue();
	            }
	        }*/
	        if(siteRef == null){
	        	siteRef = "";
	        }else{
	        	siteRef = siteRef.trim();
	        }
        }else{
        	siteRef = "null";

        }

        Iterator itar=poStruct.getOrderItemDV().iterator();
        ArrayList tableData=new ArrayList();
        while(itar.hasNext()){

            OrderItemData oid = (OrderItemData)itar.next();
            int qty = getPoQty(oid);
            BigDecimal price = getPoCost(oid);

            XlsRow row = new XlsRow();

            row.setLineNumber(oid.getErpPoLineNum());
            row.setSiteName(siteName);
            row.setSiteReferenceNum(siteRef);
            row.setAddress(address);
            row.setCity(city);
            row.setState(state);
            row.setPostalcd(postalcd);
            row.setPoNum(oid.getOutboundPoNum());
            row.setDate(oid.getErpPoDate());
            row.setCurrency(currency);
            row.setQty(qty);
            row.setDistSku(oid.getDistItemSkuNum());
            row.setUom(oid.getItemUom());
            row.setPack(oid.getItemPack());
            row.setItemDesc(oid.getItemShortDesc());
            row.setPrice(price);
            row.setNetAmmount(new BigDecimal(qty).multiply(price));
            if(poStruct.getSiteShipMsg() == null){
            	row.setComments(poStruct.getOrderD().getComments());
            }else{
            	row.setComments(poStruct.getSiteShipMsg()+" "+poStruct.getOrderD().getComments());
            }

            tableData.add(row.getRowModel());
        }
        return tableData;
    }

    private int getPoQty(OrderItemData oi) {
        if (oi.getDistItemQuantity() > 0) {
            return oi.getDistItemQuantity();
        } else {
            return oi.getTotalQuantityOrdered();
        }
    }

    private BigDecimal getPoCost(OrderItemData oi) {
        if (oi.getDistUomConvCost() != null && oi.getDistUomConvCost().compareTo(new BigDecimal(0)) > 0) {
            return oi.getDistUomConvCost();
        } else {
            return oi.getDistItemCost();
        }
    }

    private String getShippingAddress(XlsPoStruct poStruct) {

        StringBuffer address= new StringBuffer();

        if (poStruct.getShipAddr() != null) {
            if (Utility.isSet(poStruct.getShipAddr().getAddress1())) {
                if(address.length()>0) {
                    address.append(ADRESS_DELIM);
                }
                address.append(poStruct.getShipAddr().getAddress1());
            }
            if (Utility.isSet(poStruct.getShipAddr().getAddress2())) {
                if(address.length()>0) {
                    address.append(ADRESS_DELIM);
                }
                address.append(poStruct.getShipAddr().getAddress2());
            }
            if (Utility.isSet(poStruct.getShipAddr().getAddress3())) {
                if(address.length()>0) {
                    address.append(ADRESS_DELIM);
                }
                address.append(poStruct.getShipAddr().getAddress3());
            }
            if (Utility.isSet(poStruct.getShipAddr().getAddress4())) {
                if(address.length()>0) {
                    address.append(ADRESS_DELIM);
                }
                address.append(poStruct.getShipAddr().getAddress4());
            }
        }
        return address.toString();
    }

    private GenericReportColumnViewVector makeXlsHeader() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Line Number",0,38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site Budget Reference Num", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Address", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "City", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "State/Province Code", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Postal Code", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "PO Number", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Currency", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Dist Sku", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Qty", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Uom", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Pack", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Item & Description", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Price", 2, 20, "NUMBER", "*", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Net Amount", 2, 20, "NUMBER", "*", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Shipping Comments", 0, 255, "VARCHAR2"));

        return header;
    }

    /**
     * Private inner class to represent the data the po needs to print, and the
     * relationship between the data.  This makes converting between different
     * objects (outboundEdiRequests, and PurchaseOrderDesc/orderItemVectors, etc.)
     */
    public class XlsPoStruct {

        private PurchaseOrderData mPurchaseOrderD;
        private OrderData mOrderD;
        private StoreData mStore;
        private OrderItemDataVector mOrderItemDV;
        private OrderAddressData mShipAddr;
        private OrderAddressData mBillAddr;
        private String mAccountName;
        private FreightHandlerView mShipVia;
        private OrderMetaDataVector mOrderMetaDV;
        private String mSiteShipMsg;
        private boolean mService;
        private HashMap mAssetInfo;
        private PropertyDataVector mSiteProps;
        private PropertyDataVector mAcctProps;
        private OrderPropertyDataVector mOrderProps;

        public XlsPoStruct(StoreData pStore,OrderData pOrderD,
                           OrderMetaDataVector pOmdv,
                           PurchaseOrderData pPurchaseOrderD,
                           OrderItemDataVector pOrderItemDV,
                           OrderAddressData pShipAddr,
                           OrderAddressData pBillAddr,
                           String pAccountName,
                           FreightHandlerView pShipVia,
                           String pSiteShipMsg,
                           PropertyDataVector pSiteProps,
                           PropertyDataVector pAcctProps,
                           OrderPropertyDataVector pOrderProps) {
            mStore=pStore;
            mPurchaseOrderD = pPurchaseOrderD;
            mOrderD = pOrderD;
            mOrderMetaDV = pOmdv;
            mOrderItemDV = pOrderItemDV;
            mShipAddr = pShipAddr;
            mBillAddr = pBillAddr;
            mAccountName = pAccountName;
            mShipVia = pShipVia;
            mSiteShipMsg = pSiteShipMsg;
            mService = false;
            mAssetInfo = null;
            mSiteProps = pSiteProps;
            mAcctProps = pAcctProps;
            mOrderProps = pOrderProps;
        }

        /*public XlsPoStruct(OrderData pOrderD,
                           OrderMetaDataVector pOmdv,
                           PurchaseOrderData pPurchaseOrderD,
                           OrderItemDataVector pOrderItemDV,
                           HashMap pAssetInfo,
                           OrderAddressData pShipAddr,
                           OrderAddressData pBillAddr,
                           String pAccountName,
                           FreightHandlerView pShipVia,
                           String pSiteShipMsg, boolean isService) {
            mPurchaseOrderD = pPurchaseOrderD;
            mOrderD = pOrderD;
            mOrderMetaDV = pOmdv;
            mOrderItemDV = pOrderItemDV;
            mShipAddr = pShipAddr;
            mBillAddr = pBillAddr;
            mAccountName = pAccountName;
            mShipVia = pShipVia;
            mSiteShipMsg = pSiteShipMsg;
            mService = isService;
            mAssetInfo = pAssetInfo;
        }*/

        private PurchaseOrderData getPurchaseOrderD() {
            return mPurchaseOrderD;
        }

        private OrderData getOrderD() {
            return mOrderD;
        }

        private OrderItemDataVector getOrderItemDV() {
            return mOrderItemDV;
        }

        private OrderMetaDataVector getOrderMetaDV() {
            return mOrderMetaDV;
        }

        private OrderAddressData getShipAddr() {
            return mShipAddr;
        }

        private OrderAddressData getBillAddr() {
            return mBillAddr;
        }

        private String getAccountName() {
            return mAccountName;
        }

        private FreightHandlerView getShipVia() {
            return mShipVia;
        }

        private String getSiteShipMsg() {
            return mSiteShipMsg;
        }

        public boolean isService() {
            return mService;
        }

        public HashMap getAssetInfo() {
            return mAssetInfo;
        }

        public PropertyDataVector getAccountProperties() {
            return mAcctProps;
        }

        public PropertyDataVector getSiteProperties() {
        	return mSiteProps;
        }

        public OrderPropertyDataVector getOrderProperties() {
            return mOrderProps;
        }

        public StoreData getStore() {
            return mStore;
        }

        public void setStore(StoreData pStore) {
            this.mStore = pStore;
        }
    }

    public class XlsRow{

        int lineNumber;
        String siteName;
        String siteReferenceNum;
        String address;
        String city;
        String state;
        String postalcd;
        String poNum;
        Date date;
        String currency;
        String distSku;
        int qty;
        String uom;
        String pack;
        String itemDesc;
        BigDecimal price;
        BigDecimal netAmmount;
        String comments;

        public List getRowModel(){
            ArrayList list = new ArrayList();
            list.add(new Integer(lineNumber));
            list.add(siteReferenceNum);
            list.add(siteName);
            list.add(address);
            list.add(city);
            list.add(state);
            list.add(postalcd);
            list.add(poNum);
            list.add(date);
            list.add(currency);
            list.add(distSku);
            list.add(new Integer(qty));
            list.add(uom);
            list.add(pack);
            list.add(itemDesc);
            list.add(price);
            list.add(netAmmount);
            list.add(comments);
            return list;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public String getSiteReferenceNum() {
            return siteReferenceNum;
        }

        public void setSiteReferenceNum(String siteReferenceNum) {
            this.siteReferenceNum = siteReferenceNum;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPoNum() {
            return poNum;
        }

        public void setPoNum(String poNum) {
            this.poNum = poNum;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getCurrency() {
            return currency;
        }

        public String getDistSku() {
        	return distSku;
        }

        public void setDistSku(String distSku){
        	this.distSku = distSku;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }


        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public String getUom() {
            return uom;
        }

        public void setUom(String uom) {
            this.uom = uom;
        }

        public String getPack() {
            return pack;
        }

        public void setPack(String pack) {
            this.pack = pack;
        }

        public String getItemDesc() {
            return itemDesc;
        }

        public void setItemDesc(String itemDesc) {
            this.itemDesc = itemDesc;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getNetAmmount() {
            return netAmmount;
        }

        public void setNetAmmount(BigDecimal netAmmount) {
            this.netAmmount = netAmmount;
        }


        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPostalcd() {
            return postalcd;
        }

        public void setPostalcd(String postalcd) {
            this.postalcd = postalcd;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }
    }


    /**
     * Error logging
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
     */
    private static void error(String message, Exception e){

        log.info("ERROR in " + className + " :: " + message);

        String errorMessage;
        StringWriter wr = new StringWriter();
        PrintWriter prW = new PrintWriter(wr);
        e.printStackTrace(prW);
        errorMessage = wr.getBuffer().toString();

        log.info("ERROR in " + className + " :: " + errorMessage);
    }
}

