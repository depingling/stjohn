
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        RemittanceDetailData
 * Description:  This is a ValueObject class wrapping the database table CLW_REMITTANCE_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.RemittanceDetailDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>RemittanceDetailData</code> is a ValueObject class wrapping of the database table CLW_REMITTANCE_DETAIL.
 */
public class RemittanceDetailData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 8570818751522710674L;
    private int mRemittanceDetailId;// SQL type:NUMBER, not null
    private String mSiteReference;// SQL type:VARCHAR2
    private String mInvoiceNumber;// SQL type:VARCHAR2
    private String mInvoiceType;// SQL type:VARCHAR2
    private java.math.BigDecimal mDiscountAmount;// SQL type:NUMBER
    private java.math.BigDecimal mNetAmount;// SQL type:NUMBER
    private java.math.BigDecimal mOrigInvoiceAmount;// SQL type:NUMBER
    private String mCustomerPoNumber;// SQL type:VARCHAR2
    private int mRemittanceId;// SQL type:NUMBER, not null
    private String mCustomerSupplierNumber;// SQL type:VARCHAR2
    private String mRemittanceDetailStatusCd;// SQL type:VARCHAR2
    private String mErpReferenceNumber;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mPaymentTypeCd;// SQL type:VARCHAR2
    private String mTransactionCd;// SQL type:VARCHAR2
    private String mReferenceInvoiceNumber;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public RemittanceDetailData ()
    {
        mSiteReference = "";
        mInvoiceNumber = "";
        mInvoiceType = "";
        mCustomerPoNumber = "";
        mCustomerSupplierNumber = "";
        mRemittanceDetailStatusCd = "";
        mErpReferenceNumber = "";
        mAddBy = "";
        mModBy = "";
        mPaymentTypeCd = "";
        mTransactionCd = "";
        mReferenceInvoiceNumber = "";
    }

    /**
     * Constructor.
     */
    public RemittanceDetailData(int parm1, String parm2, String parm3, String parm4, java.math.BigDecimal parm5, java.math.BigDecimal parm6, java.math.BigDecimal parm7, String parm8, int parm9, String parm10, String parm11, String parm12, Date parm13, String parm14, Date parm15, String parm16, String parm17, String parm18, String parm19)
    {
        mRemittanceDetailId = parm1;
        mSiteReference = parm2;
        mInvoiceNumber = parm3;
        mInvoiceType = parm4;
        mDiscountAmount = parm5;
        mNetAmount = parm6;
        mOrigInvoiceAmount = parm7;
        mCustomerPoNumber = parm8;
        mRemittanceId = parm9;
        mCustomerSupplierNumber = parm10;
        mRemittanceDetailStatusCd = parm11;
        mErpReferenceNumber = parm12;
        mAddDate = parm13;
        mAddBy = parm14;
        mModDate = parm15;
        mModBy = parm16;
        mPaymentTypeCd = parm17;
        mTransactionCd = parm18;
        mReferenceInvoiceNumber = parm19;
        
    }

    /**
     * Creates a new RemittanceDetailData
     *
     * @return
     *  Newly initialized RemittanceDetailData object.
     */
    public static RemittanceDetailData createValue ()
    {
        RemittanceDetailData valueData = new RemittanceDetailData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this RemittanceDetailData object
     */
    public String toString()
    {
        return "[" + "RemittanceDetailId=" + mRemittanceDetailId + ", SiteReference=" + mSiteReference + ", InvoiceNumber=" + mInvoiceNumber + ", InvoiceType=" + mInvoiceType + ", DiscountAmount=" + mDiscountAmount + ", NetAmount=" + mNetAmount + ", OrigInvoiceAmount=" + mOrigInvoiceAmount + ", CustomerPoNumber=" + mCustomerPoNumber + ", RemittanceId=" + mRemittanceId + ", CustomerSupplierNumber=" + mCustomerSupplierNumber + ", RemittanceDetailStatusCd=" + mRemittanceDetailStatusCd + ", ErpReferenceNumber=" + mErpReferenceNumber + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", PaymentTypeCd=" + mPaymentTypeCd + ", TransactionCd=" + mTransactionCd + ", ReferenceInvoiceNumber=" + mReferenceInvoiceNumber + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("RemittanceDetail");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mRemittanceDetailId));

        node =  doc.createElement("SiteReference");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteReference)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceNumber)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceType");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceType)));
        root.appendChild(node);

        node =  doc.createElement("DiscountAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mDiscountAmount)));
        root.appendChild(node);

        node =  doc.createElement("NetAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mNetAmount)));
        root.appendChild(node);

        node =  doc.createElement("OrigInvoiceAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mOrigInvoiceAmount)));
        root.appendChild(node);

        node =  doc.createElement("CustomerPoNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerPoNumber)));
        root.appendChild(node);

        node =  doc.createElement("RemittanceId");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceId)));
        root.appendChild(node);

        node =  doc.createElement("CustomerSupplierNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerSupplierNumber)));
        root.appendChild(node);

        node =  doc.createElement("RemittanceDetailStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceDetailStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("ErpReferenceNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mErpReferenceNumber)));
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

        node =  doc.createElement("PaymentTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPaymentTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("TransactionCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTransactionCd)));
        root.appendChild(node);

        node =  doc.createElement("ReferenceInvoiceNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mReferenceInvoiceNumber)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the RemittanceDetailId field is not cloned.
    *
    * @return RemittanceDetailData object
    */
    public Object clone(){
        RemittanceDetailData myClone = new RemittanceDetailData();
        
        myClone.mSiteReference = mSiteReference;
        
        myClone.mInvoiceNumber = mInvoiceNumber;
        
        myClone.mInvoiceType = mInvoiceType;
        
        myClone.mDiscountAmount = mDiscountAmount;
        
        myClone.mNetAmount = mNetAmount;
        
        myClone.mOrigInvoiceAmount = mOrigInvoiceAmount;
        
        myClone.mCustomerPoNumber = mCustomerPoNumber;
        
        myClone.mRemittanceId = mRemittanceId;
        
        myClone.mCustomerSupplierNumber = mCustomerSupplierNumber;
        
        myClone.mRemittanceDetailStatusCd = mRemittanceDetailStatusCd;
        
        myClone.mErpReferenceNumber = mErpReferenceNumber;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mPaymentTypeCd = mPaymentTypeCd;
        
        myClone.mTransactionCd = mTransactionCd;
        
        myClone.mReferenceInvoiceNumber = mReferenceInvoiceNumber;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (RemittanceDetailDataAccess.REMITTANCE_DETAIL_ID.equals(pFieldName)) {
            return getRemittanceDetailId();
        } else if (RemittanceDetailDataAccess.SITE_REFERENCE.equals(pFieldName)) {
            return getSiteReference();
        } else if (RemittanceDetailDataAccess.INVOICE_NUMBER.equals(pFieldName)) {
            return getInvoiceNumber();
        } else if (RemittanceDetailDataAccess.INVOICE_TYPE.equals(pFieldName)) {
            return getInvoiceType();
        } else if (RemittanceDetailDataAccess.DISCOUNT_AMOUNT.equals(pFieldName)) {
            return getDiscountAmount();
        } else if (RemittanceDetailDataAccess.NET_AMOUNT.equals(pFieldName)) {
            return getNetAmount();
        } else if (RemittanceDetailDataAccess.ORIG_INVOICE_AMOUNT.equals(pFieldName)) {
            return getOrigInvoiceAmount();
        } else if (RemittanceDetailDataAccess.CUSTOMER_PO_NUMBER.equals(pFieldName)) {
            return getCustomerPoNumber();
        } else if (RemittanceDetailDataAccess.REMITTANCE_ID.equals(pFieldName)) {
            return getRemittanceId();
        } else if (RemittanceDetailDataAccess.CUSTOMER_SUPPLIER_NUMBER.equals(pFieldName)) {
            return getCustomerSupplierNumber();
        } else if (RemittanceDetailDataAccess.REMITTANCE_DETAIL_STATUS_CD.equals(pFieldName)) {
            return getRemittanceDetailStatusCd();
        } else if (RemittanceDetailDataAccess.ERP_REFERENCE_NUMBER.equals(pFieldName)) {
            return getErpReferenceNumber();
        } else if (RemittanceDetailDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (RemittanceDetailDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (RemittanceDetailDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (RemittanceDetailDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (RemittanceDetailDataAccess.PAYMENT_TYPE_CD.equals(pFieldName)) {
            return getPaymentTypeCd();
        } else if (RemittanceDetailDataAccess.TRANSACTION_CD.equals(pFieldName)) {
            return getTransactionCd();
        } else if (RemittanceDetailDataAccess.REFERENCE_INVOICE_NUMBER.equals(pFieldName)) {
            return getReferenceInvoiceNumber();
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
        return RemittanceDetailDataAccess.CLW_REMITTANCE_DETAIL;
    }

    
    /**
     * Sets the RemittanceDetailId field. This field is required to be set in the database.
     *
     * @param pRemittanceDetailId
     *  int to use to update the field.
     */
    public void setRemittanceDetailId(int pRemittanceDetailId){
        this.mRemittanceDetailId = pRemittanceDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the RemittanceDetailId field.
     *
     * @return
     *  int containing the RemittanceDetailId field.
     */
    public int getRemittanceDetailId(){
        return mRemittanceDetailId;
    }

    /**
     * Sets the SiteReference field.
     *
     * @param pSiteReference
     *  String to use to update the field.
     */
    public void setSiteReference(String pSiteReference){
        this.mSiteReference = pSiteReference;
        setDirty(true);
    }
    /**
     * Retrieves the SiteReference field.
     *
     * @return
     *  String containing the SiteReference field.
     */
    public String getSiteReference(){
        return mSiteReference;
    }

    /**
     * Sets the InvoiceNumber field.
     *
     * @param pInvoiceNumber
     *  String to use to update the field.
     */
    public void setInvoiceNumber(String pInvoiceNumber){
        this.mInvoiceNumber = pInvoiceNumber;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceNumber field.
     *
     * @return
     *  String containing the InvoiceNumber field.
     */
    public String getInvoiceNumber(){
        return mInvoiceNumber;
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
     * Sets the DiscountAmount field.
     *
     * @param pDiscountAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setDiscountAmount(java.math.BigDecimal pDiscountAmount){
        this.mDiscountAmount = pDiscountAmount;
        setDirty(true);
    }
    /**
     * Retrieves the DiscountAmount field.
     *
     * @return
     *  java.math.BigDecimal containing the DiscountAmount field.
     */
    public java.math.BigDecimal getDiscountAmount(){
        return mDiscountAmount;
    }

    /**
     * Sets the NetAmount field.
     *
     * @param pNetAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setNetAmount(java.math.BigDecimal pNetAmount){
        this.mNetAmount = pNetAmount;
        setDirty(true);
    }
    /**
     * Retrieves the NetAmount field.
     *
     * @return
     *  java.math.BigDecimal containing the NetAmount field.
     */
    public java.math.BigDecimal getNetAmount(){
        return mNetAmount;
    }

    /**
     * Sets the OrigInvoiceAmount field.
     *
     * @param pOrigInvoiceAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setOrigInvoiceAmount(java.math.BigDecimal pOrigInvoiceAmount){
        this.mOrigInvoiceAmount = pOrigInvoiceAmount;
        setDirty(true);
    }
    /**
     * Retrieves the OrigInvoiceAmount field.
     *
     * @return
     *  java.math.BigDecimal containing the OrigInvoiceAmount field.
     */
    public java.math.BigDecimal getOrigInvoiceAmount(){
        return mOrigInvoiceAmount;
    }

    /**
     * Sets the CustomerPoNumber field.
     *
     * @param pCustomerPoNumber
     *  String to use to update the field.
     */
    public void setCustomerPoNumber(String pCustomerPoNumber){
        this.mCustomerPoNumber = pCustomerPoNumber;
        setDirty(true);
    }
    /**
     * Retrieves the CustomerPoNumber field.
     *
     * @return
     *  String containing the CustomerPoNumber field.
     */
    public String getCustomerPoNumber(){
        return mCustomerPoNumber;
    }

    /**
     * Sets the RemittanceId field. This field is required to be set in the database.
     *
     * @param pRemittanceId
     *  int to use to update the field.
     */
    public void setRemittanceId(int pRemittanceId){
        this.mRemittanceId = pRemittanceId;
        setDirty(true);
    }
    /**
     * Retrieves the RemittanceId field.
     *
     * @return
     *  int containing the RemittanceId field.
     */
    public int getRemittanceId(){
        return mRemittanceId;
    }

    /**
     * Sets the CustomerSupplierNumber field.
     *
     * @param pCustomerSupplierNumber
     *  String to use to update the field.
     */
    public void setCustomerSupplierNumber(String pCustomerSupplierNumber){
        this.mCustomerSupplierNumber = pCustomerSupplierNumber;
        setDirty(true);
    }
    /**
     * Retrieves the CustomerSupplierNumber field.
     *
     * @return
     *  String containing the CustomerSupplierNumber field.
     */
    public String getCustomerSupplierNumber(){
        return mCustomerSupplierNumber;
    }

    /**
     * Sets the RemittanceDetailStatusCd field.
     *
     * @param pRemittanceDetailStatusCd
     *  String to use to update the field.
     */
    public void setRemittanceDetailStatusCd(String pRemittanceDetailStatusCd){
        this.mRemittanceDetailStatusCd = pRemittanceDetailStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the RemittanceDetailStatusCd field.
     *
     * @return
     *  String containing the RemittanceDetailStatusCd field.
     */
    public String getRemittanceDetailStatusCd(){
        return mRemittanceDetailStatusCd;
    }

    /**
     * Sets the ErpReferenceNumber field.
     *
     * @param pErpReferenceNumber
     *  String to use to update the field.
     */
    public void setErpReferenceNumber(String pErpReferenceNumber){
        this.mErpReferenceNumber = pErpReferenceNumber;
        setDirty(true);
    }
    /**
     * Retrieves the ErpReferenceNumber field.
     *
     * @return
     *  String containing the ErpReferenceNumber field.
     */
    public String getErpReferenceNumber(){
        return mErpReferenceNumber;
    }

    /**
     * Sets the AddDate field. This field is required to be set in the database.
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
     * Sets the ModDate field. This field is required to be set in the database.
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
     * Sets the PaymentTypeCd field.
     *
     * @param pPaymentTypeCd
     *  String to use to update the field.
     */
    public void setPaymentTypeCd(String pPaymentTypeCd){
        this.mPaymentTypeCd = pPaymentTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the PaymentTypeCd field.
     *
     * @return
     *  String containing the PaymentTypeCd field.
     */
    public String getPaymentTypeCd(){
        return mPaymentTypeCd;
    }

    /**
     * Sets the TransactionCd field.
     *
     * @param pTransactionCd
     *  String to use to update the field.
     */
    public void setTransactionCd(String pTransactionCd){
        this.mTransactionCd = pTransactionCd;
        setDirty(true);
    }
    /**
     * Retrieves the TransactionCd field.
     *
     * @return
     *  String containing the TransactionCd field.
     */
    public String getTransactionCd(){
        return mTransactionCd;
    }

    /**
     * Sets the ReferenceInvoiceNumber field.
     *
     * @param pReferenceInvoiceNumber
     *  String to use to update the field.
     */
    public void setReferenceInvoiceNumber(String pReferenceInvoiceNumber){
        this.mReferenceInvoiceNumber = pReferenceInvoiceNumber;
        setDirty(true);
    }
    /**
     * Retrieves the ReferenceInvoiceNumber field.
     *
     * @return
     *  String containing the ReferenceInvoiceNumber field.
     */
    public String getReferenceInvoiceNumber(){
        return mReferenceInvoiceNumber;
    }


}
