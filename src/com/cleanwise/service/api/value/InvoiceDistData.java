
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InvoiceDistData
 * Description:  This is a ValueObject class wrapping the database table CLW_INVOICE_DIST.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.InvoiceDistDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>InvoiceDistData</code> is a ValueObject class wrapping of the database table CLW_INVOICE_DIST.
 */
public class InvoiceDistData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -5608069171531099590L;
    private int mInvoiceDistId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER
    private int mOrderId;// SQL type:NUMBER
    private String mErpPoNum;// SQL type:VARCHAR2
    private String mDistOrderNum;// SQL type:VARCHAR2
    private String mInvoiceNum;// SQL type:VARCHAR2
    private Date mInvoiceDate;// SQL type:DATE
    private String mInvoiceStatusCd;// SQL type:VARCHAR2
    private String mDistShipmentNum;// SQL type:VARCHAR2
    private String mTrackingType;// SQL type:VARCHAR2
    private String mTrackingNum;// SQL type:VARCHAR2
    private String mCarrier;// SQL type:VARCHAR2
    private String mScac;// SQL type:VARCHAR2
    private String mShipToName;// SQL type:VARCHAR2
    private String mShipToAddress1;// SQL type:VARCHAR2
    private String mShipToAddress2;// SQL type:VARCHAR2
    private String mShipToAddress3;// SQL type:VARCHAR2
    private String mShipToAddress4;// SQL type:VARCHAR2
    private String mShipToCity;// SQL type:VARCHAR2
    private String mShipToState;// SQL type:VARCHAR2
    private String mShipToPostalCode;// SQL type:VARCHAR2
    private String mShipToCountry;// SQL type:VARCHAR2
    private String mShipFromName;// SQL type:VARCHAR2
    private String mShipFromAddress1;// SQL type:VARCHAR2
    private String mShipFromAddress2;// SQL type:VARCHAR2
    private String mShipFromAddress3;// SQL type:VARCHAR2
    private String mShipFromAddress4;// SQL type:VARCHAR2
    private String mShipFromCity;// SQL type:VARCHAR2
    private String mShipFromState;// SQL type:VARCHAR2
    private String mShipFromPostalCode;// SQL type:VARCHAR2
    private String mShipFromCountry;// SQL type:VARCHAR2
    private java.math.BigDecimal mSubTotal;// SQL type:NUMBER
    private java.math.BigDecimal mFreight;// SQL type:NUMBER
    private java.math.BigDecimal mSalesTax;// SQL type:NUMBER
    private java.math.BigDecimal mDiscounts;// SQL type:NUMBER
    private java.math.BigDecimal mMiscCharges;// SQL type:NUMBER
    private java.math.BigDecimal mCredits;// SQL type:NUMBER
    private int mBatchNumber;// SQL type:NUMBER
    private Date mBatchDate;// SQL type:DATE
    private Date mBatchTime;// SQL type:DATE
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2
    private String mInvoiceDistSourceCd;// SQL type:VARCHAR2
    private int mStoreId;// SQL type:NUMBER
    private String mErpSystemCd;// SQL type:VARCHAR2
    private String mRemitTo;// SQL type:VARCHAR2
    private String mErpPoRefNum;// SQL type:VARCHAR2
    private int mAccountId;// SQL type:NUMBER
    private int mSiteId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public InvoiceDistData ()
    {
        mErpPoNum = "";
        mDistOrderNum = "";
        mInvoiceNum = "";
        mInvoiceStatusCd = "";
        mDistShipmentNum = "";
        mTrackingType = "";
        mTrackingNum = "";
        mCarrier = "";
        mScac = "";
        mShipToName = "";
        mShipToAddress1 = "";
        mShipToAddress2 = "";
        mShipToAddress3 = "";
        mShipToAddress4 = "";
        mShipToCity = "";
        mShipToState = "";
        mShipToPostalCode = "";
        mShipToCountry = "";
        mShipFromName = "";
        mShipFromAddress1 = "";
        mShipFromAddress2 = "";
        mShipFromAddress3 = "";
        mShipFromAddress4 = "";
        mShipFromCity = "";
        mShipFromState = "";
        mShipFromPostalCode = "";
        mShipFromCountry = "";
        mAddBy = "";
        mModBy = "";
        mInvoiceDistSourceCd = "";
        mErpSystemCd = "";
        mRemitTo = "";
        mErpPoRefNum = "";
    }

    /**
     * Constructor.
     */
    public InvoiceDistData(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, Date parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13, String parm14, String parm15, String parm16, String parm17, String parm18, String parm19, String parm20, String parm21, String parm22, String parm23, String parm24, String parm25, String parm26, String parm27, String parm28, String parm29, String parm30, String parm31, java.math.BigDecimal parm32, java.math.BigDecimal parm33, java.math.BigDecimal parm34, java.math.BigDecimal parm35, java.math.BigDecimal parm36, java.math.BigDecimal parm37, int parm38, Date parm39, Date parm40, Date parm41, String parm42, Date parm43, String parm44, String parm45, int parm46, String parm47, String parm48, String parm49, int parm50, int parm51)
    {
        mInvoiceDistId = parm1;
        mBusEntityId = parm2;
        mOrderId = parm3;
        mErpPoNum = parm4;
        mDistOrderNum = parm5;
        mInvoiceNum = parm6;
        mInvoiceDate = parm7;
        mInvoiceStatusCd = parm8;
        mDistShipmentNum = parm9;
        mTrackingType = parm10;
        mTrackingNum = parm11;
        mCarrier = parm12;
        mScac = parm13;
        mShipToName = parm14;
        mShipToAddress1 = parm15;
        mShipToAddress2 = parm16;
        mShipToAddress3 = parm17;
        mShipToAddress4 = parm18;
        mShipToCity = parm19;
        mShipToState = parm20;
        mShipToPostalCode = parm21;
        mShipToCountry = parm22;
        mShipFromName = parm23;
        mShipFromAddress1 = parm24;
        mShipFromAddress2 = parm25;
        mShipFromAddress3 = parm26;
        mShipFromAddress4 = parm27;
        mShipFromCity = parm28;
        mShipFromState = parm29;
        mShipFromPostalCode = parm30;
        mShipFromCountry = parm31;
        mSubTotal = parm32;
        mFreight = parm33;
        mSalesTax = parm34;
        mDiscounts = parm35;
        mMiscCharges = parm36;
        mCredits = parm37;
        mBatchNumber = parm38;
        mBatchDate = parm39;
        mBatchTime = parm40;
        mAddDate = parm41;
        mAddBy = parm42;
        mModDate = parm43;
        mModBy = parm44;
        mInvoiceDistSourceCd = parm45;
        mStoreId = parm46;
        mErpSystemCd = parm47;
        mRemitTo = parm48;
        mErpPoRefNum = parm49;
        mAccountId = parm50;
        mSiteId = parm51;
        
    }

    /**
     * Creates a new InvoiceDistData
     *
     * @return
     *  Newly initialized InvoiceDistData object.
     */
    public static InvoiceDistData createValue ()
    {
        InvoiceDistData valueData = new InvoiceDistData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InvoiceDistData object
     */
    public String toString()
    {
        return "[" + "InvoiceDistId=" + mInvoiceDistId + ", BusEntityId=" + mBusEntityId + ", OrderId=" + mOrderId + ", ErpPoNum=" + mErpPoNum + ", DistOrderNum=" + mDistOrderNum + ", InvoiceNum=" + mInvoiceNum + ", InvoiceDate=" + mInvoiceDate + ", InvoiceStatusCd=" + mInvoiceStatusCd + ", DistShipmentNum=" + mDistShipmentNum + ", TrackingType=" + mTrackingType + ", TrackingNum=" + mTrackingNum + ", Carrier=" + mCarrier + ", Scac=" + mScac + ", ShipToName=" + mShipToName + ", ShipToAddress1=" + mShipToAddress1 + ", ShipToAddress2=" + mShipToAddress2 + ", ShipToAddress3=" + mShipToAddress3 + ", ShipToAddress4=" + mShipToAddress4 + ", ShipToCity=" + mShipToCity + ", ShipToState=" + mShipToState + ", ShipToPostalCode=" + mShipToPostalCode + ", ShipToCountry=" + mShipToCountry + ", ShipFromName=" + mShipFromName + ", ShipFromAddress1=" + mShipFromAddress1 + ", ShipFromAddress2=" + mShipFromAddress2 + ", ShipFromAddress3=" + mShipFromAddress3 + ", ShipFromAddress4=" + mShipFromAddress4 + ", ShipFromCity=" + mShipFromCity + ", ShipFromState=" + mShipFromState + ", ShipFromPostalCode=" + mShipFromPostalCode + ", ShipFromCountry=" + mShipFromCountry + ", SubTotal=" + mSubTotal + ", Freight=" + mFreight + ", SalesTax=" + mSalesTax + ", Discounts=" + mDiscounts + ", MiscCharges=" + mMiscCharges + ", Credits=" + mCredits + ", BatchNumber=" + mBatchNumber + ", BatchDate=" + mBatchDate + ", BatchTime=" + mBatchTime + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", InvoiceDistSourceCd=" + mInvoiceDistSourceCd + ", StoreId=" + mStoreId + ", ErpSystemCd=" + mErpSystemCd + ", RemitTo=" + mRemitTo + ", ErpPoRefNum=" + mErpPoRefNum + ", AccountId=" + mAccountId + ", SiteId=" + mSiteId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("InvoiceDist");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mInvoiceDistId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node =  doc.createElement("ErpPoNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpPoNum)));
        root.appendChild(node);

        node =  doc.createElement("DistOrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mDistOrderNum)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceNum");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceNum)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceDate");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceDate)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("DistShipmentNum");
        node.appendChild(doc.createTextNode(String.valueOf(mDistShipmentNum)));
        root.appendChild(node);

        node =  doc.createElement("TrackingType");
        node.appendChild(doc.createTextNode(String.valueOf(mTrackingType)));
        root.appendChild(node);

        node =  doc.createElement("TrackingNum");
        node.appendChild(doc.createTextNode(String.valueOf(mTrackingNum)));
        root.appendChild(node);

        node =  doc.createElement("Carrier");
        node.appendChild(doc.createTextNode(String.valueOf(mCarrier)));
        root.appendChild(node);

        node =  doc.createElement("Scac");
        node.appendChild(doc.createTextNode(String.valueOf(mScac)));
        root.appendChild(node);

        node =  doc.createElement("ShipToName");
        node.appendChild(doc.createTextNode(String.valueOf(mShipToName)));
        root.appendChild(node);

        node =  doc.createElement("ShipToAddress1");
        node.appendChild(doc.createTextNode(String.valueOf(mShipToAddress1)));
        root.appendChild(node);

        node =  doc.createElement("ShipToAddress2");
        node.appendChild(doc.createTextNode(String.valueOf(mShipToAddress2)));
        root.appendChild(node);

        node =  doc.createElement("ShipToAddress3");
        node.appendChild(doc.createTextNode(String.valueOf(mShipToAddress3)));
        root.appendChild(node);

        node =  doc.createElement("ShipToAddress4");
        node.appendChild(doc.createTextNode(String.valueOf(mShipToAddress4)));
        root.appendChild(node);

        node =  doc.createElement("ShipToCity");
        node.appendChild(doc.createTextNode(String.valueOf(mShipToCity)));
        root.appendChild(node);

        node =  doc.createElement("ShipToState");
        node.appendChild(doc.createTextNode(String.valueOf(mShipToState)));
        root.appendChild(node);

        node =  doc.createElement("ShipToPostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mShipToPostalCode)));
        root.appendChild(node);

        node =  doc.createElement("ShipToCountry");
        node.appendChild(doc.createTextNode(String.valueOf(mShipToCountry)));
        root.appendChild(node);

        node =  doc.createElement("ShipFromName");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromName)));
        root.appendChild(node);

        node =  doc.createElement("ShipFromAddress1");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromAddress1)));
        root.appendChild(node);

        node =  doc.createElement("ShipFromAddress2");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromAddress2)));
        root.appendChild(node);

        node =  doc.createElement("ShipFromAddress3");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromAddress3)));
        root.appendChild(node);

        node =  doc.createElement("ShipFromAddress4");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromAddress4)));
        root.appendChild(node);

        node =  doc.createElement("ShipFromCity");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromCity)));
        root.appendChild(node);

        node =  doc.createElement("ShipFromState");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromState)));
        root.appendChild(node);

        node =  doc.createElement("ShipFromPostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromPostalCode)));
        root.appendChild(node);

        node =  doc.createElement("ShipFromCountry");
        node.appendChild(doc.createTextNode(String.valueOf(mShipFromCountry)));
        root.appendChild(node);

        node =  doc.createElement("SubTotal");
        node.appendChild(doc.createTextNode(String.valueOf(mSubTotal)));
        root.appendChild(node);

        node =  doc.createElement("Freight");
        node.appendChild(doc.createTextNode(String.valueOf(mFreight)));
        root.appendChild(node);

        node =  doc.createElement("SalesTax");
        node.appendChild(doc.createTextNode(String.valueOf(mSalesTax)));
        root.appendChild(node);

        node =  doc.createElement("Discounts");
        node.appendChild(doc.createTextNode(String.valueOf(mDiscounts)));
        root.appendChild(node);

        node =  doc.createElement("MiscCharges");
        node.appendChild(doc.createTextNode(String.valueOf(mMiscCharges)));
        root.appendChild(node);

        node =  doc.createElement("Credits");
        node.appendChild(doc.createTextNode(String.valueOf(mCredits)));
        root.appendChild(node);

        node =  doc.createElement("BatchNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mBatchNumber)));
        root.appendChild(node);

        node =  doc.createElement("BatchDate");
        node.appendChild(doc.createTextNode(String.valueOf(mBatchDate)));
        root.appendChild(node);

        node =  doc.createElement("BatchTime");
        node.appendChild(doc.createTextNode(String.valueOf(mBatchTime)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node =  doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        node =  doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceDistSourceCd");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceDistSourceCd)));
        root.appendChild(node);

        node =  doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        node =  doc.createElement("ErpSystemCd");
        node.appendChild(doc.createTextNode(String.valueOf(mErpSystemCd)));
        root.appendChild(node);

        node =  doc.createElement("RemitTo");
        node.appendChild(doc.createTextNode(String.valueOf(mRemitTo)));
        root.appendChild(node);

        node =  doc.createElement("ErpPoRefNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpPoRefNum)));
        root.appendChild(node);

        node =  doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node =  doc.createElement("SiteId");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the InvoiceDistId field is not cloned.
    *
    * @return InvoiceDistData object
    */
    public Object clone(){
        InvoiceDistData myClone = new InvoiceDistData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mOrderId = mOrderId;
        
        myClone.mErpPoNum = mErpPoNum;
        
        myClone.mDistOrderNum = mDistOrderNum;
        
        myClone.mInvoiceNum = mInvoiceNum;
        
        if(mInvoiceDate != null){
                myClone.mInvoiceDate = (Date) mInvoiceDate.clone();
        }
        
        myClone.mInvoiceStatusCd = mInvoiceStatusCd;
        
        myClone.mDistShipmentNum = mDistShipmentNum;
        
        myClone.mTrackingType = mTrackingType;
        
        myClone.mTrackingNum = mTrackingNum;
        
        myClone.mCarrier = mCarrier;
        
        myClone.mScac = mScac;
        
        myClone.mShipToName = mShipToName;
        
        myClone.mShipToAddress1 = mShipToAddress1;
        
        myClone.mShipToAddress2 = mShipToAddress2;
        
        myClone.mShipToAddress3 = mShipToAddress3;
        
        myClone.mShipToAddress4 = mShipToAddress4;
        
        myClone.mShipToCity = mShipToCity;
        
        myClone.mShipToState = mShipToState;
        
        myClone.mShipToPostalCode = mShipToPostalCode;
        
        myClone.mShipToCountry = mShipToCountry;
        
        myClone.mShipFromName = mShipFromName;
        
        myClone.mShipFromAddress1 = mShipFromAddress1;
        
        myClone.mShipFromAddress2 = mShipFromAddress2;
        
        myClone.mShipFromAddress3 = mShipFromAddress3;
        
        myClone.mShipFromAddress4 = mShipFromAddress4;
        
        myClone.mShipFromCity = mShipFromCity;
        
        myClone.mShipFromState = mShipFromState;
        
        myClone.mShipFromPostalCode = mShipFromPostalCode;
        
        myClone.mShipFromCountry = mShipFromCountry;
        
        myClone.mSubTotal = mSubTotal;
        
        myClone.mFreight = mFreight;
        
        myClone.mSalesTax = mSalesTax;
        
        myClone.mDiscounts = mDiscounts;
        
        myClone.mMiscCharges = mMiscCharges;
        
        myClone.mCredits = mCredits;
        
        myClone.mBatchNumber = mBatchNumber;
        
        if(mBatchDate != null){
                myClone.mBatchDate = (Date) mBatchDate.clone();
        }
        
        if(mBatchTime != null){
                myClone.mBatchTime = (Date) mBatchTime.clone();
        }
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mInvoiceDistSourceCd = mInvoiceDistSourceCd;
        
        myClone.mStoreId = mStoreId;
        
        myClone.mErpSystemCd = mErpSystemCd;
        
        myClone.mRemitTo = mRemitTo;
        
        myClone.mErpPoRefNum = mErpPoRefNum;
        
        myClone.mAccountId = mAccountId;
        
        myClone.mSiteId = mSiteId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (InvoiceDistDataAccess.INVOICE_DIST_ID.equals(pFieldName)) {
            return getInvoiceDistId();
        } else if (InvoiceDistDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (InvoiceDistDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (InvoiceDistDataAccess.ERP_PO_NUM.equals(pFieldName)) {
            return getErpPoNum();
        } else if (InvoiceDistDataAccess.DIST_ORDER_NUM.equals(pFieldName)) {
            return getDistOrderNum();
        } else if (InvoiceDistDataAccess.INVOICE_NUM.equals(pFieldName)) {
            return getInvoiceNum();
        } else if (InvoiceDistDataAccess.INVOICE_DATE.equals(pFieldName)) {
            return getInvoiceDate();
        } else if (InvoiceDistDataAccess.INVOICE_STATUS_CD.equals(pFieldName)) {
            return getInvoiceStatusCd();
        } else if (InvoiceDistDataAccess.DIST_SHIPMENT_NUM.equals(pFieldName)) {
            return getDistShipmentNum();
        } else if (InvoiceDistDataAccess.TRACKING_TYPE.equals(pFieldName)) {
            return getTrackingType();
        } else if (InvoiceDistDataAccess.TRACKING_NUM.equals(pFieldName)) {
            return getTrackingNum();
        } else if (InvoiceDistDataAccess.CARRIER.equals(pFieldName)) {
            return getCarrier();
        } else if (InvoiceDistDataAccess.SCAC.equals(pFieldName)) {
            return getScac();
        } else if (InvoiceDistDataAccess.SHIP_TO_NAME.equals(pFieldName)) {
            return getShipToName();
        } else if (InvoiceDistDataAccess.SHIP_TO_ADDRESS_1.equals(pFieldName)) {
            return getShipToAddress1();
        } else if (InvoiceDistDataAccess.SHIP_TO_ADDRESS_2.equals(pFieldName)) {
            return getShipToAddress2();
        } else if (InvoiceDistDataAccess.SHIP_TO_ADDRESS_3.equals(pFieldName)) {
            return getShipToAddress3();
        } else if (InvoiceDistDataAccess.SHIP_TO_ADDRESS_4.equals(pFieldName)) {
            return getShipToAddress4();
        } else if (InvoiceDistDataAccess.SHIP_TO_CITY.equals(pFieldName)) {
            return getShipToCity();
        } else if (InvoiceDistDataAccess.SHIP_TO_STATE.equals(pFieldName)) {
            return getShipToState();
        } else if (InvoiceDistDataAccess.SHIP_TO_POSTAL_CODE.equals(pFieldName)) {
            return getShipToPostalCode();
        } else if (InvoiceDistDataAccess.SHIP_TO_COUNTRY.equals(pFieldName)) {
            return getShipToCountry();
        } else if (InvoiceDistDataAccess.SHIP_FROM_NAME.equals(pFieldName)) {
            return getShipFromName();
        } else if (InvoiceDistDataAccess.SHIP_FROM_ADDRESS_1.equals(pFieldName)) {
            return getShipFromAddress1();
        } else if (InvoiceDistDataAccess.SHIP_FROM_ADDRESS_2.equals(pFieldName)) {
            return getShipFromAddress2();
        } else if (InvoiceDistDataAccess.SHIP_FROM_ADDRESS_3.equals(pFieldName)) {
            return getShipFromAddress3();
        } else if (InvoiceDistDataAccess.SHIP_FROM_ADDRESS_4.equals(pFieldName)) {
            return getShipFromAddress4();
        } else if (InvoiceDistDataAccess.SHIP_FROM_CITY.equals(pFieldName)) {
            return getShipFromCity();
        } else if (InvoiceDistDataAccess.SHIP_FROM_STATE.equals(pFieldName)) {
            return getShipFromState();
        } else if (InvoiceDistDataAccess.SHIP_FROM_POSTAL_CODE.equals(pFieldName)) {
            return getShipFromPostalCode();
        } else if (InvoiceDistDataAccess.SHIP_FROM_COUNTRY.equals(pFieldName)) {
            return getShipFromCountry();
        } else if (InvoiceDistDataAccess.SUB_TOTAL.equals(pFieldName)) {
            return getSubTotal();
        } else if (InvoiceDistDataAccess.FREIGHT.equals(pFieldName)) {
            return getFreight();
        } else if (InvoiceDistDataAccess.SALES_TAX.equals(pFieldName)) {
            return getSalesTax();
        } else if (InvoiceDistDataAccess.DISCOUNTS.equals(pFieldName)) {
            return getDiscounts();
        } else if (InvoiceDistDataAccess.MISC_CHARGES.equals(pFieldName)) {
            return getMiscCharges();
        } else if (InvoiceDistDataAccess.CREDITS.equals(pFieldName)) {
            return getCredits();
        } else if (InvoiceDistDataAccess.BATCH_NUMBER.equals(pFieldName)) {
            return getBatchNumber();
        } else if (InvoiceDistDataAccess.BATCH_DATE.equals(pFieldName)) {
            return getBatchDate();
        } else if (InvoiceDistDataAccess.BATCH_TIME.equals(pFieldName)) {
            return getBatchTime();
        } else if (InvoiceDistDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (InvoiceDistDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (InvoiceDistDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (InvoiceDistDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (InvoiceDistDataAccess.INVOICE_DIST_SOURCE_CD.equals(pFieldName)) {
            return getInvoiceDistSourceCd();
        } else if (InvoiceDistDataAccess.STORE_ID.equals(pFieldName)) {
            return getStoreId();
        } else if (InvoiceDistDataAccess.ERP_SYSTEM_CD.equals(pFieldName)) {
            return getErpSystemCd();
        } else if (InvoiceDistDataAccess.REMIT_TO.equals(pFieldName)) {
            return getRemitTo();
        } else if (InvoiceDistDataAccess.ERP_PO_REF_NUM.equals(pFieldName)) {
            return getErpPoRefNum();
        } else if (InvoiceDistDataAccess.ACCOUNT_ID.equals(pFieldName)) {
            return getAccountId();
        } else if (InvoiceDistDataAccess.SITE_ID.equals(pFieldName)) {
            return getSiteId();
        } else {
            return null;
        }

    }
    /**
    * Gets table name
    *
    * @return Table name
    */
    public String getTable() {
        return InvoiceDistDataAccess.CLW_INVOICE_DIST;
    }

    
    /**
     * Sets the InvoiceDistId field. This field is required to be set in the database.
     *
     * @param pInvoiceDistId
     *  int to use to update the field.
     */
    public void setInvoiceDistId(int pInvoiceDistId){
        this.mInvoiceDistId = pInvoiceDistId;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceDistId field.
     *
     * @return
     *  int containing the InvoiceDistId field.
     */
    public int getInvoiceDistId(){
        return mInvoiceDistId;
    }

    /**
     * Sets the BusEntityId field.
     *
     * @param pBusEntityId
     *  int to use to update the field.
     */
    public void setBusEntityId(int pBusEntityId){
        this.mBusEntityId = pBusEntityId;
        setDirty(true);
    }
    /**
     * Retrieves the BusEntityId field.
     *
     * @return
     *  int containing the BusEntityId field.
     */
    public int getBusEntityId(){
        return mBusEntityId;
    }

    /**
     * Sets the OrderId field.
     *
     * @param pOrderId
     *  int to use to update the field.
     */
    public void setOrderId(int pOrderId){
        this.mOrderId = pOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderId field.
     *
     * @return
     *  int containing the OrderId field.
     */
    public int getOrderId(){
        return mOrderId;
    }

    /**
     * Sets the ErpPoNum field.
     *
     * @param pErpPoNum
     *  String to use to update the field.
     */
    public void setErpPoNum(String pErpPoNum){
        this.mErpPoNum = pErpPoNum;
        setDirty(true);
    }
    /**
     * Retrieves the ErpPoNum field.
     *
     * @return
     *  String containing the ErpPoNum field.
     */
    public String getErpPoNum(){
        return mErpPoNum;
    }

    /**
     * Sets the DistOrderNum field.
     *
     * @param pDistOrderNum
     *  String to use to update the field.
     */
    public void setDistOrderNum(String pDistOrderNum){
        this.mDistOrderNum = pDistOrderNum;
        setDirty(true);
    }
    /**
     * Retrieves the DistOrderNum field.
     *
     * @return
     *  String containing the DistOrderNum field.
     */
    public String getDistOrderNum(){
        return mDistOrderNum;
    }

    /**
     * Sets the InvoiceNum field.
     *
     * @param pInvoiceNum
     *  String to use to update the field.
     */
    public void setInvoiceNum(String pInvoiceNum){
        this.mInvoiceNum = pInvoiceNum;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceNum field.
     *
     * @return
     *  String containing the InvoiceNum field.
     */
    public String getInvoiceNum(){
        return mInvoiceNum;
    }

    /**
     * Sets the InvoiceDate field.
     *
     * @param pInvoiceDate
     *  Date to use to update the field.
     */
    public void setInvoiceDate(Date pInvoiceDate){
        this.mInvoiceDate = pInvoiceDate;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceDate field.
     *
     * @return
     *  Date containing the InvoiceDate field.
     */
    public Date getInvoiceDate(){
        return mInvoiceDate;
    }

    /**
     * Sets the InvoiceStatusCd field.
     *
     * @param pInvoiceStatusCd
     *  String to use to update the field.
     */
    public void setInvoiceStatusCd(String pInvoiceStatusCd){
        this.mInvoiceStatusCd = pInvoiceStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceStatusCd field.
     *
     * @return
     *  String containing the InvoiceStatusCd field.
     */
    public String getInvoiceStatusCd(){
        return mInvoiceStatusCd;
    }

    /**
     * Sets the DistShipmentNum field.
     *
     * @param pDistShipmentNum
     *  String to use to update the field.
     */
    public void setDistShipmentNum(String pDistShipmentNum){
        this.mDistShipmentNum = pDistShipmentNum;
        setDirty(true);
    }
    /**
     * Retrieves the DistShipmentNum field.
     *
     * @return
     *  String containing the DistShipmentNum field.
     */
    public String getDistShipmentNum(){
        return mDistShipmentNum;
    }

    /**
     * Sets the TrackingType field.
     *
     * @param pTrackingType
     *  String to use to update the field.
     */
    public void setTrackingType(String pTrackingType){
        this.mTrackingType = pTrackingType;
        setDirty(true);
    }
    /**
     * Retrieves the TrackingType field.
     *
     * @return
     *  String containing the TrackingType field.
     */
    public String getTrackingType(){
        return mTrackingType;
    }

    /**
     * Sets the TrackingNum field.
     *
     * @param pTrackingNum
     *  String to use to update the field.
     */
    public void setTrackingNum(String pTrackingNum){
        this.mTrackingNum = pTrackingNum;
        setDirty(true);
    }
    /**
     * Retrieves the TrackingNum field.
     *
     * @return
     *  String containing the TrackingNum field.
     */
    public String getTrackingNum(){
        return mTrackingNum;
    }

    /**
     * Sets the Carrier field.
     *
     * @param pCarrier
     *  String to use to update the field.
     */
    public void setCarrier(String pCarrier){
        this.mCarrier = pCarrier;
        setDirty(true);
    }
    /**
     * Retrieves the Carrier field.
     *
     * @return
     *  String containing the Carrier field.
     */
    public String getCarrier(){
        return mCarrier;
    }

    /**
     * Sets the Scac field.
     *
     * @param pScac
     *  String to use to update the field.
     */
    public void setScac(String pScac){
        this.mScac = pScac;
        setDirty(true);
    }
    /**
     * Retrieves the Scac field.
     *
     * @return
     *  String containing the Scac field.
     */
    public String getScac(){
        return mScac;
    }

    /**
     * Sets the ShipToName field.
     *
     * @param pShipToName
     *  String to use to update the field.
     */
    public void setShipToName(String pShipToName){
        this.mShipToName = pShipToName;
        setDirty(true);
    }
    /**
     * Retrieves the ShipToName field.
     *
     * @return
     *  String containing the ShipToName field.
     */
    public String getShipToName(){
        return mShipToName;
    }

    /**
     * Sets the ShipToAddress1 field.
     *
     * @param pShipToAddress1
     *  String to use to update the field.
     */
    public void setShipToAddress1(String pShipToAddress1){
        this.mShipToAddress1 = pShipToAddress1;
        setDirty(true);
    }
    /**
     * Retrieves the ShipToAddress1 field.
     *
     * @return
     *  String containing the ShipToAddress1 field.
     */
    public String getShipToAddress1(){
        return mShipToAddress1;
    }

    /**
     * Sets the ShipToAddress2 field.
     *
     * @param pShipToAddress2
     *  String to use to update the field.
     */
    public void setShipToAddress2(String pShipToAddress2){
        this.mShipToAddress2 = pShipToAddress2;
        setDirty(true);
    }
    /**
     * Retrieves the ShipToAddress2 field.
     *
     * @return
     *  String containing the ShipToAddress2 field.
     */
    public String getShipToAddress2(){
        return mShipToAddress2;
    }

    /**
     * Sets the ShipToAddress3 field.
     *
     * @param pShipToAddress3
     *  String to use to update the field.
     */
    public void setShipToAddress3(String pShipToAddress3){
        this.mShipToAddress3 = pShipToAddress3;
        setDirty(true);
    }
    /**
     * Retrieves the ShipToAddress3 field.
     *
     * @return
     *  String containing the ShipToAddress3 field.
     */
    public String getShipToAddress3(){
        return mShipToAddress3;
    }

    /**
     * Sets the ShipToAddress4 field.
     *
     * @param pShipToAddress4
     *  String to use to update the field.
     */
    public void setShipToAddress4(String pShipToAddress4){
        this.mShipToAddress4 = pShipToAddress4;
        setDirty(true);
    }
    /**
     * Retrieves the ShipToAddress4 field.
     *
     * @return
     *  String containing the ShipToAddress4 field.
     */
    public String getShipToAddress4(){
        return mShipToAddress4;
    }

    /**
     * Sets the ShipToCity field.
     *
     * @param pShipToCity
     *  String to use to update the field.
     */
    public void setShipToCity(String pShipToCity){
        this.mShipToCity = pShipToCity;
        setDirty(true);
    }
    /**
     * Retrieves the ShipToCity field.
     *
     * @return
     *  String containing the ShipToCity field.
     */
    public String getShipToCity(){
        return mShipToCity;
    }

    /**
     * Sets the ShipToState field.
     *
     * @param pShipToState
     *  String to use to update the field.
     */
    public void setShipToState(String pShipToState){
        this.mShipToState = pShipToState;
        setDirty(true);
    }
    /**
     * Retrieves the ShipToState field.
     *
     * @return
     *  String containing the ShipToState field.
     */
    public String getShipToState(){
        return mShipToState;
    }

    /**
     * Sets the ShipToPostalCode field.
     *
     * @param pShipToPostalCode
     *  String to use to update the field.
     */
    public void setShipToPostalCode(String pShipToPostalCode){
        this.mShipToPostalCode = pShipToPostalCode;
        setDirty(true);
    }
    /**
     * Retrieves the ShipToPostalCode field.
     *
     * @return
     *  String containing the ShipToPostalCode field.
     */
    public String getShipToPostalCode(){
        return mShipToPostalCode;
    }

    /**
     * Sets the ShipToCountry field.
     *
     * @param pShipToCountry
     *  String to use to update the field.
     */
    public void setShipToCountry(String pShipToCountry){
        this.mShipToCountry = pShipToCountry;
        setDirty(true);
    }
    /**
     * Retrieves the ShipToCountry field.
     *
     * @return
     *  String containing the ShipToCountry field.
     */
    public String getShipToCountry(){
        return mShipToCountry;
    }

    /**
     * Sets the ShipFromName field.
     *
     * @param pShipFromName
     *  String to use to update the field.
     */
    public void setShipFromName(String pShipFromName){
        this.mShipFromName = pShipFromName;
        setDirty(true);
    }
    /**
     * Retrieves the ShipFromName field.
     *
     * @return
     *  String containing the ShipFromName field.
     */
    public String getShipFromName(){
        return mShipFromName;
    }

    /**
     * Sets the ShipFromAddress1 field.
     *
     * @param pShipFromAddress1
     *  String to use to update the field.
     */
    public void setShipFromAddress1(String pShipFromAddress1){
        this.mShipFromAddress1 = pShipFromAddress1;
        setDirty(true);
    }
    /**
     * Retrieves the ShipFromAddress1 field.
     *
     * @return
     *  String containing the ShipFromAddress1 field.
     */
    public String getShipFromAddress1(){
        return mShipFromAddress1;
    }

    /**
     * Sets the ShipFromAddress2 field.
     *
     * @param pShipFromAddress2
     *  String to use to update the field.
     */
    public void setShipFromAddress2(String pShipFromAddress2){
        this.mShipFromAddress2 = pShipFromAddress2;
        setDirty(true);
    }
    /**
     * Retrieves the ShipFromAddress2 field.
     *
     * @return
     *  String containing the ShipFromAddress2 field.
     */
    public String getShipFromAddress2(){
        return mShipFromAddress2;
    }

    /**
     * Sets the ShipFromAddress3 field.
     *
     * @param pShipFromAddress3
     *  String to use to update the field.
     */
    public void setShipFromAddress3(String pShipFromAddress3){
        this.mShipFromAddress3 = pShipFromAddress3;
        setDirty(true);
    }
    /**
     * Retrieves the ShipFromAddress3 field.
     *
     * @return
     *  String containing the ShipFromAddress3 field.
     */
    public String getShipFromAddress3(){
        return mShipFromAddress3;
    }

    /**
     * Sets the ShipFromAddress4 field.
     *
     * @param pShipFromAddress4
     *  String to use to update the field.
     */
    public void setShipFromAddress4(String pShipFromAddress4){
        this.mShipFromAddress4 = pShipFromAddress4;
        setDirty(true);
    }
    /**
     * Retrieves the ShipFromAddress4 field.
     *
     * @return
     *  String containing the ShipFromAddress4 field.
     */
    public String getShipFromAddress4(){
        return mShipFromAddress4;
    }

    /**
     * Sets the ShipFromCity field.
     *
     * @param pShipFromCity
     *  String to use to update the field.
     */
    public void setShipFromCity(String pShipFromCity){
        this.mShipFromCity = pShipFromCity;
        setDirty(true);
    }
    /**
     * Retrieves the ShipFromCity field.
     *
     * @return
     *  String containing the ShipFromCity field.
     */
    public String getShipFromCity(){
        return mShipFromCity;
    }

    /**
     * Sets the ShipFromState field.
     *
     * @param pShipFromState
     *  String to use to update the field.
     */
    public void setShipFromState(String pShipFromState){
        this.mShipFromState = pShipFromState;
        setDirty(true);
    }
    /**
     * Retrieves the ShipFromState field.
     *
     * @return
     *  String containing the ShipFromState field.
     */
    public String getShipFromState(){
        return mShipFromState;
    }

    /**
     * Sets the ShipFromPostalCode field.
     *
     * @param pShipFromPostalCode
     *  String to use to update the field.
     */
    public void setShipFromPostalCode(String pShipFromPostalCode){
        this.mShipFromPostalCode = pShipFromPostalCode;
        setDirty(true);
    }
    /**
     * Retrieves the ShipFromPostalCode field.
     *
     * @return
     *  String containing the ShipFromPostalCode field.
     */
    public String getShipFromPostalCode(){
        return mShipFromPostalCode;
    }

    /**
     * Sets the ShipFromCountry field.
     *
     * @param pShipFromCountry
     *  String to use to update the field.
     */
    public void setShipFromCountry(String pShipFromCountry){
        this.mShipFromCountry = pShipFromCountry;
        setDirty(true);
    }
    /**
     * Retrieves the ShipFromCountry field.
     *
     * @return
     *  String containing the ShipFromCountry field.
     */
    public String getShipFromCountry(){
        return mShipFromCountry;
    }

    /**
     * Sets the SubTotal field.
     *
     * @param pSubTotal
     *  java.math.BigDecimal to use to update the field.
     */
    public void setSubTotal(java.math.BigDecimal pSubTotal){
        this.mSubTotal = pSubTotal;
        setDirty(true);
    }
    /**
     * Retrieves the SubTotal field.
     *
     * @return
     *  java.math.BigDecimal containing the SubTotal field.
     */
    public java.math.BigDecimal getSubTotal(){
        return mSubTotal;
    }

    /**
     * Sets the Freight field.
     *
     * @param pFreight
     *  java.math.BigDecimal to use to update the field.
     */
    public void setFreight(java.math.BigDecimal pFreight){
        this.mFreight = pFreight;
        setDirty(true);
    }
    /**
     * Retrieves the Freight field.
     *
     * @return
     *  java.math.BigDecimal containing the Freight field.
     */
    public java.math.BigDecimal getFreight(){
        return mFreight;
    }

    /**
     * Sets the SalesTax field.
     *
     * @param pSalesTax
     *  java.math.BigDecimal to use to update the field.
     */
    public void setSalesTax(java.math.BigDecimal pSalesTax){
        this.mSalesTax = pSalesTax;
        setDirty(true);
    }
    /**
     * Retrieves the SalesTax field.
     *
     * @return
     *  java.math.BigDecimal containing the SalesTax field.
     */
    public java.math.BigDecimal getSalesTax(){
        return mSalesTax;
    }

    /**
     * Sets the Discounts field.
     *
     * @param pDiscounts
     *  java.math.BigDecimal to use to update the field.
     */
    public void setDiscounts(java.math.BigDecimal pDiscounts){
        this.mDiscounts = pDiscounts;
        setDirty(true);
    }
    /**
     * Retrieves the Discounts field.
     *
     * @return
     *  java.math.BigDecimal containing the Discounts field.
     */
    public java.math.BigDecimal getDiscounts(){
        return mDiscounts;
    }

    /**
     * Sets the MiscCharges field.
     *
     * @param pMiscCharges
     *  java.math.BigDecimal to use to update the field.
     */
    public void setMiscCharges(java.math.BigDecimal pMiscCharges){
        this.mMiscCharges = pMiscCharges;
        setDirty(true);
    }
    /**
     * Retrieves the MiscCharges field.
     *
     * @return
     *  java.math.BigDecimal containing the MiscCharges field.
     */
    public java.math.BigDecimal getMiscCharges(){
        return mMiscCharges;
    }

    /**
     * Sets the Credits field.
     *
     * @param pCredits
     *  java.math.BigDecimal to use to update the field.
     */
    public void setCredits(java.math.BigDecimal pCredits){
        this.mCredits = pCredits;
        setDirty(true);
    }
    /**
     * Retrieves the Credits field.
     *
     * @return
     *  java.math.BigDecimal containing the Credits field.
     */
    public java.math.BigDecimal getCredits(){
        return mCredits;
    }

    /**
     * Sets the BatchNumber field.
     *
     * @param pBatchNumber
     *  int to use to update the field.
     */
    public void setBatchNumber(int pBatchNumber){
        this.mBatchNumber = pBatchNumber;
        setDirty(true);
    }
    /**
     * Retrieves the BatchNumber field.
     *
     * @return
     *  int containing the BatchNumber field.
     */
    public int getBatchNumber(){
        return mBatchNumber;
    }

    /**
     * Sets the BatchDate field.
     *
     * @param pBatchDate
     *  Date to use to update the field.
     */
    public void setBatchDate(Date pBatchDate){
        this.mBatchDate = pBatchDate;
        setDirty(true);
    }
    /**
     * Retrieves the BatchDate field.
     *
     * @return
     *  Date containing the BatchDate field.
     */
    public Date getBatchDate(){
        return mBatchDate;
    }

    /**
     * Sets the BatchTime field.
     *
     * @param pBatchTime
     *  Date to use to update the field.
     */
    public void setBatchTime(Date pBatchTime){
        this.mBatchTime = pBatchTime;
        setDirty(true);
    }
    /**
     * Retrieves the BatchTime field.
     *
     * @return
     *  Date containing the BatchTime field.
     */
    public Date getBatchTime(){
        return mBatchTime;
    }

    /**
     * Sets the AddDate field.
     *
     * @param pAddDate
     *  Date to use to update the field.
     */
    public void setAddDate(Date pAddDate){
        this.mAddDate = pAddDate;
        setDirty(true);
    }
    /**
     * Retrieves the AddDate field.
     *
     * @return
     *  Date containing the AddDate field.
     */
    public Date getAddDate(){
        return mAddDate;
    }

    /**
     * Sets the AddBy field.
     *
     * @param pAddBy
     *  String to use to update the field.
     */
    public void setAddBy(String pAddBy){
        this.mAddBy = pAddBy;
        setDirty(true);
    }
    /**
     * Retrieves the AddBy field.
     *
     * @return
     *  String containing the AddBy field.
     */
    public String getAddBy(){
        return mAddBy;
    }

    /**
     * Sets the ModDate field.
     *
     * @param pModDate
     *  Date to use to update the field.
     */
    public void setModDate(Date pModDate){
        this.mModDate = pModDate;
        setDirty(true);
    }
    /**
     * Retrieves the ModDate field.
     *
     * @return
     *  Date containing the ModDate field.
     */
    public Date getModDate(){
        return mModDate;
    }

    /**
     * Sets the ModBy field.
     *
     * @param pModBy
     *  String to use to update the field.
     */
    public void setModBy(String pModBy){
        this.mModBy = pModBy;
        setDirty(true);
    }
    /**
     * Retrieves the ModBy field.
     *
     * @return
     *  String containing the ModBy field.
     */
    public String getModBy(){
        return mModBy;
    }

    /**
     * Sets the InvoiceDistSourceCd field.
     *
     * @param pInvoiceDistSourceCd
     *  String to use to update the field.
     */
    public void setInvoiceDistSourceCd(String pInvoiceDistSourceCd){
        this.mInvoiceDistSourceCd = pInvoiceDistSourceCd;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceDistSourceCd field.
     *
     * @return
     *  String containing the InvoiceDistSourceCd field.
     */
    public String getInvoiceDistSourceCd(){
        return mInvoiceDistSourceCd;
    }

    /**
     * Sets the StoreId field.
     *
     * @param pStoreId
     *  int to use to update the field.
     */
    public void setStoreId(int pStoreId){
        this.mStoreId = pStoreId;
        setDirty(true);
    }
    /**
     * Retrieves the StoreId field.
     *
     * @return
     *  int containing the StoreId field.
     */
    public int getStoreId(){
        return mStoreId;
    }

    /**
     * Sets the ErpSystemCd field.
     *
     * @param pErpSystemCd
     *  String to use to update the field.
     */
    public void setErpSystemCd(String pErpSystemCd){
        this.mErpSystemCd = pErpSystemCd;
        setDirty(true);
    }
    /**
     * Retrieves the ErpSystemCd field.
     *
     * @return
     *  String containing the ErpSystemCd field.
     */
    public String getErpSystemCd(){
        return mErpSystemCd;
    }

    /**
     * Sets the RemitTo field.
     *
     * @param pRemitTo
     *  String to use to update the field.
     */
    public void setRemitTo(String pRemitTo){
        this.mRemitTo = pRemitTo;
        setDirty(true);
    }
    /**
     * Retrieves the RemitTo field.
     *
     * @return
     *  String containing the RemitTo field.
     */
    public String getRemitTo(){
        return mRemitTo;
    }

    /**
     * Sets the ErpPoRefNum field.
     *
     * @param pErpPoRefNum
     *  String to use to update the field.
     */
    public void setErpPoRefNum(String pErpPoRefNum){
        this.mErpPoRefNum = pErpPoRefNum;
        setDirty(true);
    }
    /**
     * Retrieves the ErpPoRefNum field.
     *
     * @return
     *  String containing the ErpPoRefNum field.
     */
    public String getErpPoRefNum(){
        return mErpPoRefNum;
    }

    /**
     * Sets the AccountId field.
     *
     * @param pAccountId
     *  int to use to update the field.
     */
    public void setAccountId(int pAccountId){
        this.mAccountId = pAccountId;
        setDirty(true);
    }
    /**
     * Retrieves the AccountId field.
     *
     * @return
     *  int containing the AccountId field.
     */
    public int getAccountId(){
        return mAccountId;
    }

    /**
     * Sets the SiteId field.
     *
     * @param pSiteId
     *  int to use to update the field.
     */
    public void setSiteId(int pSiteId){
        this.mSiteId = pSiteId;
        setDirty(true);
    }
    /**
     * Retrieves the SiteId field.
     *
     * @return
     *  int containing the SiteId field.
     */
    public int getSiteId(){
        return mSiteId;
    }


}
