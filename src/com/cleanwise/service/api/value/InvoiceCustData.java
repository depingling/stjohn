
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InvoiceCustData
 * Description:  This is a ValueObject class wrapping the database table CLW_INVOICE_CUST.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.InvoiceCustDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>InvoiceCustData</code> is a ValueObject class wrapping of the database table CLW_INVOICE_CUST.
 */
public class InvoiceCustData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -7479247511565271689L;
    private int mInvoiceCustId;// SQL type:NUMBER, not null
    private int mStoreId;// SQL type:NUMBER
    private int mAccountId;// SQL type:NUMBER
    private int mSiteId;// SQL type:NUMBER
    private int mOrderId;// SQL type:NUMBER
    private String mErpPoNum;// SQL type:VARCHAR2
    private String mInvoiceNum;// SQL type:VARCHAR2
    private Date mInvoiceDate;// SQL type:DATE
    private String mInvoiceStatusCd;// SQL type:VARCHAR2
    private String mBillToName;// SQL type:VARCHAR2
    private String mBillToAddress1;// SQL type:VARCHAR2
    private String mBillToAddress2;// SQL type:VARCHAR2
    private String mBillToAddress3;// SQL type:VARCHAR2
    private String mBillToAddress4;// SQL type:VARCHAR2
    private String mBillToCity;// SQL type:VARCHAR2
    private String mBillToState;// SQL type:VARCHAR2
    private String mBillToPostalCode;// SQL type:VARCHAR2
    private String mBillToCountry;// SQL type:VARCHAR2
    private String mShippingName;// SQL type:VARCHAR2
    private String mShippingAddress1;// SQL type:VARCHAR2
    private String mShippingAddress2;// SQL type:VARCHAR2
    private String mShippingAddress3;// SQL type:VARCHAR2
    private String mShippingAddress4;// SQL type:VARCHAR2
    private String mShippingCity;// SQL type:VARCHAR2
    private String mShippingState;// SQL type:VARCHAR2
    private String mShippingPostalCode;// SQL type:VARCHAR2
    private String mShippingCountry;// SQL type:VARCHAR2
    private java.math.BigDecimal mNetDue;// SQL type:NUMBER
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
    private String mPaymentTermsCd;// SQL type:CHAR
    private String mCitStatusCd;// SQL type:VARCHAR2
    private int mCitAssignmentNumber;// SQL type:NUMBER
    private Date mCitTransactionDate;// SQL type:DATE
    private String mOriginalInvoiceNum;// SQL type:VARCHAR2
    private String mInvoiceType;// SQL type:VARCHAR2
    private String mErpSystemCd;// SQL type:VARCHAR2
    private java.math.BigDecimal mFuelSurcharge;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public InvoiceCustData ()
    {
        mErpPoNum = "";
        mInvoiceNum = "";
        mInvoiceStatusCd = "";
        mBillToName = "";
        mBillToAddress1 = "";
        mBillToAddress2 = "";
        mBillToAddress3 = "";
        mBillToAddress4 = "";
        mBillToCity = "";
        mBillToState = "";
        mBillToPostalCode = "";
        mBillToCountry = "";
        mShippingName = "";
        mShippingAddress1 = "";
        mShippingAddress2 = "";
        mShippingAddress3 = "";
        mShippingAddress4 = "";
        mShippingCity = "";
        mShippingState = "";
        mShippingPostalCode = "";
        mShippingCountry = "";
        mAddBy = "";
        mModBy = "";
        mPaymentTermsCd = "";
        mCitStatusCd = "";
        mOriginalInvoiceNum = "";
        mInvoiceType = "";
        mErpSystemCd = "";
    }

    /**
     * Constructor.
     */
    public InvoiceCustData(int parm1, int parm2, int parm3, int parm4, int parm5, String parm6, String parm7, Date parm8, String parm9, String parm10, String parm11, String parm12, String parm13, String parm14, String parm15, String parm16, String parm17, String parm18, String parm19, String parm20, String parm21, String parm22, String parm23, String parm24, String parm25, String parm26, String parm27, java.math.BigDecimal parm28, java.math.BigDecimal parm29, java.math.BigDecimal parm30, java.math.BigDecimal parm31, java.math.BigDecimal parm32, java.math.BigDecimal parm33, java.math.BigDecimal parm34, int parm35, Date parm36, Date parm37, Date parm38, String parm39, Date parm40, String parm41, String parm42, String parm43, int parm44, Date parm45, String parm46, String parm47, String parm48, java.math.BigDecimal parm49)
    {
        mInvoiceCustId = parm1;
        mStoreId = parm2;
        mAccountId = parm3;
        mSiteId = parm4;
        mOrderId = parm5;
        mErpPoNum = parm6;
        mInvoiceNum = parm7;
        mInvoiceDate = parm8;
        mInvoiceStatusCd = parm9;
        mBillToName = parm10;
        mBillToAddress1 = parm11;
        mBillToAddress2 = parm12;
        mBillToAddress3 = parm13;
        mBillToAddress4 = parm14;
        mBillToCity = parm15;
        mBillToState = parm16;
        mBillToPostalCode = parm17;
        mBillToCountry = parm18;
        mShippingName = parm19;
        mShippingAddress1 = parm20;
        mShippingAddress2 = parm21;
        mShippingAddress3 = parm22;
        mShippingAddress4 = parm23;
        mShippingCity = parm24;
        mShippingState = parm25;
        mShippingPostalCode = parm26;
        mShippingCountry = parm27;
        mNetDue = parm28;
        mSubTotal = parm29;
        mFreight = parm30;
        mSalesTax = parm31;
        mDiscounts = parm32;
        mMiscCharges = parm33;
        mCredits = parm34;
        mBatchNumber = parm35;
        mBatchDate = parm36;
        mBatchTime = parm37;
        mAddDate = parm38;
        mAddBy = parm39;
        mModDate = parm40;
        mModBy = parm41;
        mPaymentTermsCd = parm42;
        mCitStatusCd = parm43;
        mCitAssignmentNumber = parm44;
        mCitTransactionDate = parm45;
        mOriginalInvoiceNum = parm46;
        mInvoiceType = parm47;
        mErpSystemCd = parm48;
        mFuelSurcharge = parm49;
        
    }

    /**
     * Creates a new InvoiceCustData
     *
     * @return
     *  Newly initialized InvoiceCustData object.
     */
    public static InvoiceCustData createValue ()
    {
        InvoiceCustData valueData = new InvoiceCustData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InvoiceCustData object
     */
    public String toString()
    {
        return "[" + "InvoiceCustId=" + mInvoiceCustId + ", StoreId=" + mStoreId + ", AccountId=" + mAccountId + ", SiteId=" + mSiteId + ", OrderId=" + mOrderId + ", ErpPoNum=" + mErpPoNum + ", InvoiceNum=" + mInvoiceNum + ", InvoiceDate=" + mInvoiceDate + ", InvoiceStatusCd=" + mInvoiceStatusCd + ", BillToName=" + mBillToName + ", BillToAddress1=" + mBillToAddress1 + ", BillToAddress2=" + mBillToAddress2 + ", BillToAddress3=" + mBillToAddress3 + ", BillToAddress4=" + mBillToAddress4 + ", BillToCity=" + mBillToCity + ", BillToState=" + mBillToState + ", BillToPostalCode=" + mBillToPostalCode + ", BillToCountry=" + mBillToCountry + ", ShippingName=" + mShippingName + ", ShippingAddress1=" + mShippingAddress1 + ", ShippingAddress2=" + mShippingAddress2 + ", ShippingAddress3=" + mShippingAddress3 + ", ShippingAddress4=" + mShippingAddress4 + ", ShippingCity=" + mShippingCity + ", ShippingState=" + mShippingState + ", ShippingPostalCode=" + mShippingPostalCode + ", ShippingCountry=" + mShippingCountry + ", NetDue=" + mNetDue + ", SubTotal=" + mSubTotal + ", Freight=" + mFreight + ", SalesTax=" + mSalesTax + ", Discounts=" + mDiscounts + ", MiscCharges=" + mMiscCharges + ", Credits=" + mCredits + ", BatchNumber=" + mBatchNumber + ", BatchDate=" + mBatchDate + ", BatchTime=" + mBatchTime + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", PaymentTermsCd=" + mPaymentTermsCd + ", CitStatusCd=" + mCitStatusCd + ", CitAssignmentNumber=" + mCitAssignmentNumber + ", CitTransactionDate=" + mCitTransactionDate + ", OriginalInvoiceNum=" + mOriginalInvoiceNum + ", InvoiceType=" + mInvoiceType + ", ErpSystemCd=" + mErpSystemCd + ", FuelSurcharge=" + mFuelSurcharge + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("InvoiceCust");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mInvoiceCustId));

        node =  doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        node =  doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node =  doc.createElement("SiteId");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteId)));
        root.appendChild(node);

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node =  doc.createElement("ErpPoNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpPoNum)));
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

        node =  doc.createElement("BillToName");
        node.appendChild(doc.createTextNode(String.valueOf(mBillToName)));
        root.appendChild(node);

        node =  doc.createElement("BillToAddress1");
        node.appendChild(doc.createTextNode(String.valueOf(mBillToAddress1)));
        root.appendChild(node);

        node =  doc.createElement("BillToAddress2");
        node.appendChild(doc.createTextNode(String.valueOf(mBillToAddress2)));
        root.appendChild(node);

        node =  doc.createElement("BillToAddress3");
        node.appendChild(doc.createTextNode(String.valueOf(mBillToAddress3)));
        root.appendChild(node);

        node =  doc.createElement("BillToAddress4");
        node.appendChild(doc.createTextNode(String.valueOf(mBillToAddress4)));
        root.appendChild(node);

        node =  doc.createElement("BillToCity");
        node.appendChild(doc.createTextNode(String.valueOf(mBillToCity)));
        root.appendChild(node);

        node =  doc.createElement("BillToState");
        node.appendChild(doc.createTextNode(String.valueOf(mBillToState)));
        root.appendChild(node);

        node =  doc.createElement("BillToPostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mBillToPostalCode)));
        root.appendChild(node);

        node =  doc.createElement("BillToCountry");
        node.appendChild(doc.createTextNode(String.valueOf(mBillToCountry)));
        root.appendChild(node);

        node =  doc.createElement("ShippingName");
        node.appendChild(doc.createTextNode(String.valueOf(mShippingName)));
        root.appendChild(node);

        node =  doc.createElement("ShippingAddress1");
        node.appendChild(doc.createTextNode(String.valueOf(mShippingAddress1)));
        root.appendChild(node);

        node =  doc.createElement("ShippingAddress2");
        node.appendChild(doc.createTextNode(String.valueOf(mShippingAddress2)));
        root.appendChild(node);

        node =  doc.createElement("ShippingAddress3");
        node.appendChild(doc.createTextNode(String.valueOf(mShippingAddress3)));
        root.appendChild(node);

        node =  doc.createElement("ShippingAddress4");
        node.appendChild(doc.createTextNode(String.valueOf(mShippingAddress4)));
        root.appendChild(node);

        node =  doc.createElement("ShippingCity");
        node.appendChild(doc.createTextNode(String.valueOf(mShippingCity)));
        root.appendChild(node);

        node =  doc.createElement("ShippingState");
        node.appendChild(doc.createTextNode(String.valueOf(mShippingState)));
        root.appendChild(node);

        node =  doc.createElement("ShippingPostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mShippingPostalCode)));
        root.appendChild(node);

        node =  doc.createElement("ShippingCountry");
        node.appendChild(doc.createTextNode(String.valueOf(mShippingCountry)));
        root.appendChild(node);

        node =  doc.createElement("NetDue");
        node.appendChild(doc.createTextNode(String.valueOf(mNetDue)));
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

        node =  doc.createElement("PaymentTermsCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPaymentTermsCd)));
        root.appendChild(node);

        node =  doc.createElement("CitStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCitStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("CitAssignmentNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mCitAssignmentNumber)));
        root.appendChild(node);

        node =  doc.createElement("CitTransactionDate");
        node.appendChild(doc.createTextNode(String.valueOf(mCitTransactionDate)));
        root.appendChild(node);

        node =  doc.createElement("OriginalInvoiceNum");
        node.appendChild(doc.createTextNode(String.valueOf(mOriginalInvoiceNum)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceType");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceType)));
        root.appendChild(node);

        node =  doc.createElement("ErpSystemCd");
        node.appendChild(doc.createTextNode(String.valueOf(mErpSystemCd)));
        root.appendChild(node);

        node =  doc.createElement("FuelSurcharge");
        node.appendChild(doc.createTextNode(String.valueOf(mFuelSurcharge)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the InvoiceCustId field is not cloned.
    *
    * @return InvoiceCustData object
    */
    public Object clone(){
        InvoiceCustData myClone = new InvoiceCustData();
        
        myClone.mStoreId = mStoreId;
        
        myClone.mAccountId = mAccountId;
        
        myClone.mSiteId = mSiteId;
        
        myClone.mOrderId = mOrderId;
        
        myClone.mErpPoNum = mErpPoNum;
        
        myClone.mInvoiceNum = mInvoiceNum;
        
        if(mInvoiceDate != null){
                myClone.mInvoiceDate = (Date) mInvoiceDate.clone();
        }
        
        myClone.mInvoiceStatusCd = mInvoiceStatusCd;
        
        myClone.mBillToName = mBillToName;
        
        myClone.mBillToAddress1 = mBillToAddress1;
        
        myClone.mBillToAddress2 = mBillToAddress2;
        
        myClone.mBillToAddress3 = mBillToAddress3;
        
        myClone.mBillToAddress4 = mBillToAddress4;
        
        myClone.mBillToCity = mBillToCity;
        
        myClone.mBillToState = mBillToState;
        
        myClone.mBillToPostalCode = mBillToPostalCode;
        
        myClone.mBillToCountry = mBillToCountry;
        
        myClone.mShippingName = mShippingName;
        
        myClone.mShippingAddress1 = mShippingAddress1;
        
        myClone.mShippingAddress2 = mShippingAddress2;
        
        myClone.mShippingAddress3 = mShippingAddress3;
        
        myClone.mShippingAddress4 = mShippingAddress4;
        
        myClone.mShippingCity = mShippingCity;
        
        myClone.mShippingState = mShippingState;
        
        myClone.mShippingPostalCode = mShippingPostalCode;
        
        myClone.mShippingCountry = mShippingCountry;
        
        myClone.mNetDue = mNetDue;
        
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
        
        myClone.mPaymentTermsCd = mPaymentTermsCd;
        
        myClone.mCitStatusCd = mCitStatusCd;
        
        myClone.mCitAssignmentNumber = mCitAssignmentNumber;
        
        if(mCitTransactionDate != null){
                myClone.mCitTransactionDate = (Date) mCitTransactionDate.clone();
        }
        
        myClone.mOriginalInvoiceNum = mOriginalInvoiceNum;
        
        myClone.mInvoiceType = mInvoiceType;
        
        myClone.mErpSystemCd = mErpSystemCd;
        
        myClone.mFuelSurcharge = mFuelSurcharge;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (InvoiceCustDataAccess.INVOICE_CUST_ID.equals(pFieldName)) {
            return getInvoiceCustId();
        } else if (InvoiceCustDataAccess.STORE_ID.equals(pFieldName)) {
            return getStoreId();
        } else if (InvoiceCustDataAccess.ACCOUNT_ID.equals(pFieldName)) {
            return getAccountId();
        } else if (InvoiceCustDataAccess.SITE_ID.equals(pFieldName)) {
            return getSiteId();
        } else if (InvoiceCustDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (InvoiceCustDataAccess.ERP_PO_NUM.equals(pFieldName)) {
            return getErpPoNum();
        } else if (InvoiceCustDataAccess.INVOICE_NUM.equals(pFieldName)) {
            return getInvoiceNum();
        } else if (InvoiceCustDataAccess.INVOICE_DATE.equals(pFieldName)) {
            return getInvoiceDate();
        } else if (InvoiceCustDataAccess.INVOICE_STATUS_CD.equals(pFieldName)) {
            return getInvoiceStatusCd();
        } else if (InvoiceCustDataAccess.BILL_TO_NAME.equals(pFieldName)) {
            return getBillToName();
        } else if (InvoiceCustDataAccess.BILL_TO_ADDRESS_1.equals(pFieldName)) {
            return getBillToAddress1();
        } else if (InvoiceCustDataAccess.BILL_TO_ADDRESS_2.equals(pFieldName)) {
            return getBillToAddress2();
        } else if (InvoiceCustDataAccess.BILL_TO_ADDRESS_3.equals(pFieldName)) {
            return getBillToAddress3();
        } else if (InvoiceCustDataAccess.BILL_TO_ADDRESS_4.equals(pFieldName)) {
            return getBillToAddress4();
        } else if (InvoiceCustDataAccess.BILL_TO_CITY.equals(pFieldName)) {
            return getBillToCity();
        } else if (InvoiceCustDataAccess.BILL_TO_STATE.equals(pFieldName)) {
            return getBillToState();
        } else if (InvoiceCustDataAccess.BILL_TO_POSTAL_CODE.equals(pFieldName)) {
            return getBillToPostalCode();
        } else if (InvoiceCustDataAccess.BILL_TO_COUNTRY.equals(pFieldName)) {
            return getBillToCountry();
        } else if (InvoiceCustDataAccess.SHIPPING_NAME.equals(pFieldName)) {
            return getShippingName();
        } else if (InvoiceCustDataAccess.SHIPPING_ADDRESS_1.equals(pFieldName)) {
            return getShippingAddress1();
        } else if (InvoiceCustDataAccess.SHIPPING_ADDRESS_2.equals(pFieldName)) {
            return getShippingAddress2();
        } else if (InvoiceCustDataAccess.SHIPPING_ADDRESS_3.equals(pFieldName)) {
            return getShippingAddress3();
        } else if (InvoiceCustDataAccess.SHIPPING_ADDRESS_4.equals(pFieldName)) {
            return getShippingAddress4();
        } else if (InvoiceCustDataAccess.SHIPPING_CITY.equals(pFieldName)) {
            return getShippingCity();
        } else if (InvoiceCustDataAccess.SHIPPING_STATE.equals(pFieldName)) {
            return getShippingState();
        } else if (InvoiceCustDataAccess.SHIPPING_POSTAL_CODE.equals(pFieldName)) {
            return getShippingPostalCode();
        } else if (InvoiceCustDataAccess.SHIPPING_COUNTRY.equals(pFieldName)) {
            return getShippingCountry();
        } else if (InvoiceCustDataAccess.NET_DUE.equals(pFieldName)) {
            return getNetDue();
        } else if (InvoiceCustDataAccess.SUB_TOTAL.equals(pFieldName)) {
            return getSubTotal();
        } else if (InvoiceCustDataAccess.FREIGHT.equals(pFieldName)) {
            return getFreight();
        } else if (InvoiceCustDataAccess.SALES_TAX.equals(pFieldName)) {
            return getSalesTax();
        } else if (InvoiceCustDataAccess.DISCOUNTS.equals(pFieldName)) {
            return getDiscounts();
        } else if (InvoiceCustDataAccess.MISC_CHARGES.equals(pFieldName)) {
            return getMiscCharges();
        } else if (InvoiceCustDataAccess.CREDITS.equals(pFieldName)) {
            return getCredits();
        } else if (InvoiceCustDataAccess.BATCH_NUMBER.equals(pFieldName)) {
            return getBatchNumber();
        } else if (InvoiceCustDataAccess.BATCH_DATE.equals(pFieldName)) {
            return getBatchDate();
        } else if (InvoiceCustDataAccess.BATCH_TIME.equals(pFieldName)) {
            return getBatchTime();
        } else if (InvoiceCustDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (InvoiceCustDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (InvoiceCustDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (InvoiceCustDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (InvoiceCustDataAccess.PAYMENT_TERMS_CD.equals(pFieldName)) {
            return getPaymentTermsCd();
        } else if (InvoiceCustDataAccess.CIT_STATUS_CD.equals(pFieldName)) {
            return getCitStatusCd();
        } else if (InvoiceCustDataAccess.CIT_ASSIGNMENT_NUMBER.equals(pFieldName)) {
            return getCitAssignmentNumber();
        } else if (InvoiceCustDataAccess.CIT_TRANSACTION_DATE.equals(pFieldName)) {
            return getCitTransactionDate();
        } else if (InvoiceCustDataAccess.ORIGINAL_INVOICE_NUM.equals(pFieldName)) {
            return getOriginalInvoiceNum();
        } else if (InvoiceCustDataAccess.INVOICE_TYPE.equals(pFieldName)) {
            return getInvoiceType();
        } else if (InvoiceCustDataAccess.ERP_SYSTEM_CD.equals(pFieldName)) {
            return getErpSystemCd();
        } else if (InvoiceCustDataAccess.FUEL_SURCHARGE.equals(pFieldName)) {
            return getFuelSurcharge();
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
        return InvoiceCustDataAccess.CLW_INVOICE_CUST;
    }

    
    /**
     * Sets the InvoiceCustId field. This field is required to be set in the database.
     *
     * @param pInvoiceCustId
     *  int to use to update the field.
     */
    public void setInvoiceCustId(int pInvoiceCustId){
        this.mInvoiceCustId = pInvoiceCustId;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceCustId field.
     *
     * @return
     *  int containing the InvoiceCustId field.
     */
    public int getInvoiceCustId(){
        return mInvoiceCustId;
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
     * Sets the BillToName field.
     *
     * @param pBillToName
     *  String to use to update the field.
     */
    public void setBillToName(String pBillToName){
        this.mBillToName = pBillToName;
        setDirty(true);
    }
    /**
     * Retrieves the BillToName field.
     *
     * @return
     *  String containing the BillToName field.
     */
    public String getBillToName(){
        return mBillToName;
    }

    /**
     * Sets the BillToAddress1 field.
     *
     * @param pBillToAddress1
     *  String to use to update the field.
     */
    public void setBillToAddress1(String pBillToAddress1){
        this.mBillToAddress1 = pBillToAddress1;
        setDirty(true);
    }
    /**
     * Retrieves the BillToAddress1 field.
     *
     * @return
     *  String containing the BillToAddress1 field.
     */
    public String getBillToAddress1(){
        return mBillToAddress1;
    }

    /**
     * Sets the BillToAddress2 field.
     *
     * @param pBillToAddress2
     *  String to use to update the field.
     */
    public void setBillToAddress2(String pBillToAddress2){
        this.mBillToAddress2 = pBillToAddress2;
        setDirty(true);
    }
    /**
     * Retrieves the BillToAddress2 field.
     *
     * @return
     *  String containing the BillToAddress2 field.
     */
    public String getBillToAddress2(){
        return mBillToAddress2;
    }

    /**
     * Sets the BillToAddress3 field.
     *
     * @param pBillToAddress3
     *  String to use to update the field.
     */
    public void setBillToAddress3(String pBillToAddress3){
        this.mBillToAddress3 = pBillToAddress3;
        setDirty(true);
    }
    /**
     * Retrieves the BillToAddress3 field.
     *
     * @return
     *  String containing the BillToAddress3 field.
     */
    public String getBillToAddress3(){
        return mBillToAddress3;
    }

    /**
     * Sets the BillToAddress4 field.
     *
     * @param pBillToAddress4
     *  String to use to update the field.
     */
    public void setBillToAddress4(String pBillToAddress4){
        this.mBillToAddress4 = pBillToAddress4;
        setDirty(true);
    }
    /**
     * Retrieves the BillToAddress4 field.
     *
     * @return
     *  String containing the BillToAddress4 field.
     */
    public String getBillToAddress4(){
        return mBillToAddress4;
    }

    /**
     * Sets the BillToCity field.
     *
     * @param pBillToCity
     *  String to use to update the field.
     */
    public void setBillToCity(String pBillToCity){
        this.mBillToCity = pBillToCity;
        setDirty(true);
    }
    /**
     * Retrieves the BillToCity field.
     *
     * @return
     *  String containing the BillToCity field.
     */
    public String getBillToCity(){
        return mBillToCity;
    }

    /**
     * Sets the BillToState field.
     *
     * @param pBillToState
     *  String to use to update the field.
     */
    public void setBillToState(String pBillToState){
        this.mBillToState = pBillToState;
        setDirty(true);
    }
    /**
     * Retrieves the BillToState field.
     *
     * @return
     *  String containing the BillToState field.
     */
    public String getBillToState(){
        return mBillToState;
    }

    /**
     * Sets the BillToPostalCode field.
     *
     * @param pBillToPostalCode
     *  String to use to update the field.
     */
    public void setBillToPostalCode(String pBillToPostalCode){
        this.mBillToPostalCode = pBillToPostalCode;
        setDirty(true);
    }
    /**
     * Retrieves the BillToPostalCode field.
     *
     * @return
     *  String containing the BillToPostalCode field.
     */
    public String getBillToPostalCode(){
        return mBillToPostalCode;
    }

    /**
     * Sets the BillToCountry field.
     *
     * @param pBillToCountry
     *  String to use to update the field.
     */
    public void setBillToCountry(String pBillToCountry){
        this.mBillToCountry = pBillToCountry;
        setDirty(true);
    }
    /**
     * Retrieves the BillToCountry field.
     *
     * @return
     *  String containing the BillToCountry field.
     */
    public String getBillToCountry(){
        return mBillToCountry;
    }

    /**
     * Sets the ShippingName field.
     *
     * @param pShippingName
     *  String to use to update the field.
     */
    public void setShippingName(String pShippingName){
        this.mShippingName = pShippingName;
        setDirty(true);
    }
    /**
     * Retrieves the ShippingName field.
     *
     * @return
     *  String containing the ShippingName field.
     */
    public String getShippingName(){
        return mShippingName;
    }

    /**
     * Sets the ShippingAddress1 field.
     *
     * @param pShippingAddress1
     *  String to use to update the field.
     */
    public void setShippingAddress1(String pShippingAddress1){
        this.mShippingAddress1 = pShippingAddress1;
        setDirty(true);
    }
    /**
     * Retrieves the ShippingAddress1 field.
     *
     * @return
     *  String containing the ShippingAddress1 field.
     */
    public String getShippingAddress1(){
        return mShippingAddress1;
    }

    /**
     * Sets the ShippingAddress2 field.
     *
     * @param pShippingAddress2
     *  String to use to update the field.
     */
    public void setShippingAddress2(String pShippingAddress2){
        this.mShippingAddress2 = pShippingAddress2;
        setDirty(true);
    }
    /**
     * Retrieves the ShippingAddress2 field.
     *
     * @return
     *  String containing the ShippingAddress2 field.
     */
    public String getShippingAddress2(){
        return mShippingAddress2;
    }

    /**
     * Sets the ShippingAddress3 field.
     *
     * @param pShippingAddress3
     *  String to use to update the field.
     */
    public void setShippingAddress3(String pShippingAddress3){
        this.mShippingAddress3 = pShippingAddress3;
        setDirty(true);
    }
    /**
     * Retrieves the ShippingAddress3 field.
     *
     * @return
     *  String containing the ShippingAddress3 field.
     */
    public String getShippingAddress3(){
        return mShippingAddress3;
    }

    /**
     * Sets the ShippingAddress4 field.
     *
     * @param pShippingAddress4
     *  String to use to update the field.
     */
    public void setShippingAddress4(String pShippingAddress4){
        this.mShippingAddress4 = pShippingAddress4;
        setDirty(true);
    }
    /**
     * Retrieves the ShippingAddress4 field.
     *
     * @return
     *  String containing the ShippingAddress4 field.
     */
    public String getShippingAddress4(){
        return mShippingAddress4;
    }

    /**
     * Sets the ShippingCity field.
     *
     * @param pShippingCity
     *  String to use to update the field.
     */
    public void setShippingCity(String pShippingCity){
        this.mShippingCity = pShippingCity;
        setDirty(true);
    }
    /**
     * Retrieves the ShippingCity field.
     *
     * @return
     *  String containing the ShippingCity field.
     */
    public String getShippingCity(){
        return mShippingCity;
    }

    /**
     * Sets the ShippingState field.
     *
     * @param pShippingState
     *  String to use to update the field.
     */
    public void setShippingState(String pShippingState){
        this.mShippingState = pShippingState;
        setDirty(true);
    }
    /**
     * Retrieves the ShippingState field.
     *
     * @return
     *  String containing the ShippingState field.
     */
    public String getShippingState(){
        return mShippingState;
    }

    /**
     * Sets the ShippingPostalCode field.
     *
     * @param pShippingPostalCode
     *  String to use to update the field.
     */
    public void setShippingPostalCode(String pShippingPostalCode){
        this.mShippingPostalCode = pShippingPostalCode;
        setDirty(true);
    }
    /**
     * Retrieves the ShippingPostalCode field.
     *
     * @return
     *  String containing the ShippingPostalCode field.
     */
    public String getShippingPostalCode(){
        return mShippingPostalCode;
    }

    /**
     * Sets the ShippingCountry field.
     *
     * @param pShippingCountry
     *  String to use to update the field.
     */
    public void setShippingCountry(String pShippingCountry){
        this.mShippingCountry = pShippingCountry;
        setDirty(true);
    }
    /**
     * Retrieves the ShippingCountry field.
     *
     * @return
     *  String containing the ShippingCountry field.
     */
    public String getShippingCountry(){
        return mShippingCountry;
    }

    /**
     * Sets the NetDue field.
     *
     * @param pNetDue
     *  java.math.BigDecimal to use to update the field.
     */
    public void setNetDue(java.math.BigDecimal pNetDue){
        this.mNetDue = pNetDue;
        setDirty(true);
    }
    /**
     * Retrieves the NetDue field.
     *
     * @return
     *  java.math.BigDecimal containing the NetDue field.
     */
    public java.math.BigDecimal getNetDue(){
        return mNetDue;
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
     * Sets the PaymentTermsCd field.
     *
     * @param pPaymentTermsCd
     *  String to use to update the field.
     */
    public void setPaymentTermsCd(String pPaymentTermsCd){
        this.mPaymentTermsCd = pPaymentTermsCd;
        setDirty(true);
    }
    /**
     * Retrieves the PaymentTermsCd field.
     *
     * @return
     *  String containing the PaymentTermsCd field.
     */
    public String getPaymentTermsCd(){
        return mPaymentTermsCd;
    }

    /**
     * Sets the CitStatusCd field.
     *
     * @param pCitStatusCd
     *  String to use to update the field.
     */
    public void setCitStatusCd(String pCitStatusCd){
        this.mCitStatusCd = pCitStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the CitStatusCd field.
     *
     * @return
     *  String containing the CitStatusCd field.
     */
    public String getCitStatusCd(){
        return mCitStatusCd;
    }

    /**
     * Sets the CitAssignmentNumber field.
     *
     * @param pCitAssignmentNumber
     *  int to use to update the field.
     */
    public void setCitAssignmentNumber(int pCitAssignmentNumber){
        this.mCitAssignmentNumber = pCitAssignmentNumber;
        setDirty(true);
    }
    /**
     * Retrieves the CitAssignmentNumber field.
     *
     * @return
     *  int containing the CitAssignmentNumber field.
     */
    public int getCitAssignmentNumber(){
        return mCitAssignmentNumber;
    }

    /**
     * Sets the CitTransactionDate field.
     *
     * @param pCitTransactionDate
     *  Date to use to update the field.
     */
    public void setCitTransactionDate(Date pCitTransactionDate){
        this.mCitTransactionDate = pCitTransactionDate;
        setDirty(true);
    }
    /**
     * Retrieves the CitTransactionDate field.
     *
     * @return
     *  Date containing the CitTransactionDate field.
     */
    public Date getCitTransactionDate(){
        return mCitTransactionDate;
    }

    /**
     * Sets the OriginalInvoiceNum field.
     *
     * @param pOriginalInvoiceNum
     *  String to use to update the field.
     */
    public void setOriginalInvoiceNum(String pOriginalInvoiceNum){
        this.mOriginalInvoiceNum = pOriginalInvoiceNum;
        setDirty(true);
    }
    /**
     * Retrieves the OriginalInvoiceNum field.
     *
     * @return
     *  String containing the OriginalInvoiceNum field.
     */
    public String getOriginalInvoiceNum(){
        return mOriginalInvoiceNum;
    }

    /**
     * Sets the InvoiceType field.
     *
     * @param pInvoiceType
     *  String to use to update the field.
     */
    public void setInvoiceType(String pInvoiceType){
        this.mInvoiceType = pInvoiceType;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceType field.
     *
     * @return
     *  String containing the InvoiceType field.
     */
    public String getInvoiceType(){
        return mInvoiceType;
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
     * Sets the FuelSurcharge field.
     *
     * @param pFuelSurcharge
     *  java.math.BigDecimal to use to update the field.
     */
    public void setFuelSurcharge(java.math.BigDecimal pFuelSurcharge){
        this.mFuelSurcharge = pFuelSurcharge;
        setDirty(true);
    }
    /**
     * Retrieves the FuelSurcharge field.
     *
     * @return
     *  java.math.BigDecimal containing the FuelSurcharge field.
     */
    public java.math.BigDecimal getFuelSurcharge(){
        return mFuelSurcharge;
    }


}
