
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CreditCardTransData
 * Description:  This is a ValueObject class wrapping the database table CLW_CREDIT_CARD_TRANS.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.CreditCardTransDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>CreditCardTransData</code> is a ValueObject class wrapping of the database table CLW_CREDIT_CARD_TRANS.
 */
public class CreditCardTransData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 3238954792408678508L;
    private int mCreditCardTransId;// SQL type:NUMBER, not null
    private int mOrderCreditCardId;// SQL type:NUMBER
    private int mInvoiceCustId;// SQL type:NUMBER
    private String mTransactionTypeCd;// SQL type:VARCHAR2
    private java.math.BigDecimal mAmount;// SQL type:NUMBER
    private String mTransactionReference;// SQL type:VARCHAR2
    private String mAuthCode;// SQL type:VARCHAR2
    private String mPaymetricResponseCode;// SQL type:VARCHAR2
    private Date mPaymetricAuthDate;// SQL type:DATE
    private Date mPaymetricAuthTime;// SQL type:DATE
    private String mPaymetricTransactionId;// SQL type:VARCHAR2
    private String mPaymetricAuthMessage;// SQL type:VARCHAR2
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE

    /**
     * Constructor.
     */
    public CreditCardTransData ()
    {
        mTransactionTypeCd = "";
        mTransactionReference = "";
        mAuthCode = "";
        mPaymetricResponseCode = "";
        mPaymetricTransactionId = "";
        mPaymetricAuthMessage = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public CreditCardTransData(int parm1, int parm2, int parm3, String parm4, java.math.BigDecimal parm5, String parm6, String parm7, String parm8, Date parm9, Date parm10, String parm11, String parm12, String parm13, Date parm14, String parm15, Date parm16)
    {
        mCreditCardTransId = parm1;
        mOrderCreditCardId = parm2;
        mInvoiceCustId = parm3;
        mTransactionTypeCd = parm4;
        mAmount = parm5;
        mTransactionReference = parm6;
        mAuthCode = parm7;
        mPaymetricResponseCode = parm8;
        mPaymetricAuthDate = parm9;
        mPaymetricAuthTime = parm10;
        mPaymetricTransactionId = parm11;
        mPaymetricAuthMessage = parm12;
        mAddBy = parm13;
        mAddDate = parm14;
        mModBy = parm15;
        mModDate = parm16;
        
    }

    /**
     * Creates a new CreditCardTransData
     *
     * @return
     *  Newly initialized CreditCardTransData object.
     */
    public static CreditCardTransData createValue ()
    {
        CreditCardTransData valueData = new CreditCardTransData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CreditCardTransData object
     */
    public String toString()
    {
        return "[" + "CreditCardTransId=" + mCreditCardTransId + ", OrderCreditCardId=" + mOrderCreditCardId + ", InvoiceCustId=" + mInvoiceCustId + ", TransactionTypeCd=" + mTransactionTypeCd + ", Amount=" + mAmount + ", TransactionReference=" + mTransactionReference + ", AuthCode=" + mAuthCode + ", PaymetricResponseCode=" + mPaymetricResponseCode + ", PaymetricAuthDate=" + mPaymetricAuthDate + ", PaymetricAuthTime=" + mPaymetricAuthTime + ", PaymetricTransactionId=" + mPaymetricTransactionId + ", PaymetricAuthMessage=" + mPaymetricAuthMessage + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("CreditCardTrans");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mCreditCardTransId));

        node =  doc.createElement("OrderCreditCardId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderCreditCardId)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceCustId");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceCustId)));
        root.appendChild(node);

        node =  doc.createElement("TransactionTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTransactionTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("Amount");
        node.appendChild(doc.createTextNode(String.valueOf(mAmount)));
        root.appendChild(node);

        node =  doc.createElement("TransactionReference");
        node.appendChild(doc.createTextNode(String.valueOf(mTransactionReference)));
        root.appendChild(node);

        node =  doc.createElement("AuthCode");
        node.appendChild(doc.createTextNode(String.valueOf(mAuthCode)));
        root.appendChild(node);

        node =  doc.createElement("PaymetricResponseCode");
        node.appendChild(doc.createTextNode(String.valueOf(mPaymetricResponseCode)));
        root.appendChild(node);

        node =  doc.createElement("PaymetricAuthDate");
        node.appendChild(doc.createTextNode(String.valueOf(mPaymetricAuthDate)));
        root.appendChild(node);

        node =  doc.createElement("PaymetricAuthTime");
        node.appendChild(doc.createTextNode(String.valueOf(mPaymetricAuthTime)));
        root.appendChild(node);

        node =  doc.createElement("PaymetricTransactionId");
        node.appendChild(doc.createTextNode(String.valueOf(mPaymetricTransactionId)));
        root.appendChild(node);

        node =  doc.createElement("PaymetricAuthMessage");
        node.appendChild(doc.createTextNode(String.valueOf(mPaymetricAuthMessage)));
        root.appendChild(node);

        node =  doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node =  doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the CreditCardTransId field is not cloned.
    *
    * @return CreditCardTransData object
    */
    public Object clone(){
        CreditCardTransData myClone = new CreditCardTransData();
        
        myClone.mOrderCreditCardId = mOrderCreditCardId;
        
        myClone.mInvoiceCustId = mInvoiceCustId;
        
        myClone.mTransactionTypeCd = mTransactionTypeCd;
        
        myClone.mAmount = mAmount;
        
        myClone.mTransactionReference = mTransactionReference;
        
        myClone.mAuthCode = mAuthCode;
        
        myClone.mPaymetricResponseCode = mPaymetricResponseCode;
        
        if(mPaymetricAuthDate != null){
                myClone.mPaymetricAuthDate = (Date) mPaymetricAuthDate.clone();
        }
        
        if(mPaymetricAuthTime != null){
                myClone.mPaymetricAuthTime = (Date) mPaymetricAuthTime.clone();
        }
        
        myClone.mPaymetricTransactionId = mPaymetricTransactionId;
        
        myClone.mPaymetricAuthMessage = mPaymetricAuthMessage;
        
        myClone.mAddBy = mAddBy;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (CreditCardTransDataAccess.CREDIT_CARD_TRANS_ID.equals(pFieldName)) {
            return getCreditCardTransId();
        } else if (CreditCardTransDataAccess.ORDER_CREDIT_CARD_ID.equals(pFieldName)) {
            return getOrderCreditCardId();
        } else if (CreditCardTransDataAccess.INVOICE_CUST_ID.equals(pFieldName)) {
            return getInvoiceCustId();
        } else if (CreditCardTransDataAccess.TRANSACTION_TYPE_CD.equals(pFieldName)) {
            return getTransactionTypeCd();
        } else if (CreditCardTransDataAccess.AMOUNT.equals(pFieldName)) {
            return getAmount();
        } else if (CreditCardTransDataAccess.TRANSACTION_REFERENCE.equals(pFieldName)) {
            return getTransactionReference();
        } else if (CreditCardTransDataAccess.AUTH_CODE.equals(pFieldName)) {
            return getAuthCode();
        } else if (CreditCardTransDataAccess.PAYMETRIC_RESPONSE_CODE.equals(pFieldName)) {
            return getPaymetricResponseCode();
        } else if (CreditCardTransDataAccess.PAYMETRIC_AUTH_DATE.equals(pFieldName)) {
            return getPaymetricAuthDate();
        } else if (CreditCardTransDataAccess.PAYMETRIC_AUTH_TIME.equals(pFieldName)) {
            return getPaymetricAuthTime();
        } else if (CreditCardTransDataAccess.PAYMETRIC_TRANSACTION_ID.equals(pFieldName)) {
            return getPaymetricTransactionId();
        } else if (CreditCardTransDataAccess.PAYMETRIC_AUTH_MESSAGE.equals(pFieldName)) {
            return getPaymetricAuthMessage();
        } else if (CreditCardTransDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (CreditCardTransDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (CreditCardTransDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (CreditCardTransDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
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
        return CreditCardTransDataAccess.CLW_CREDIT_CARD_TRANS;
    }

    
    /**
     * Sets the CreditCardTransId field. This field is required to be set in the database.
     *
     * @param pCreditCardTransId
     *  int to use to update the field.
     */
    public void setCreditCardTransId(int pCreditCardTransId){
        this.mCreditCardTransId = pCreditCardTransId;
        setDirty(true);
    }
    /**
     * Retrieves the CreditCardTransId field.
     *
     * @return
     *  int containing the CreditCardTransId field.
     */
    public int getCreditCardTransId(){
        return mCreditCardTransId;
    }

    /**
     * Sets the OrderCreditCardId field.
     *
     * @param pOrderCreditCardId
     *  int to use to update the field.
     */
    public void setOrderCreditCardId(int pOrderCreditCardId){
        this.mOrderCreditCardId = pOrderCreditCardId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderCreditCardId field.
     *
     * @return
     *  int containing the OrderCreditCardId field.
     */
    public int getOrderCreditCardId(){
        return mOrderCreditCardId;
    }

    /**
     * Sets the InvoiceCustId field.
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
     * Sets the TransactionTypeCd field.
     *
     * @param pTransactionTypeCd
     *  String to use to update the field.
     */
    public void setTransactionTypeCd(String pTransactionTypeCd){
        this.mTransactionTypeCd = pTransactionTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the TransactionTypeCd field.
     *
     * @return
     *  String containing the TransactionTypeCd field.
     */
    public String getTransactionTypeCd(){
        return mTransactionTypeCd;
    }

    /**
     * Sets the Amount field.
     *
     * @param pAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setAmount(java.math.BigDecimal pAmount){
        this.mAmount = pAmount;
        setDirty(true);
    }
    /**
     * Retrieves the Amount field.
     *
     * @return
     *  java.math.BigDecimal containing the Amount field.
     */
    public java.math.BigDecimal getAmount(){
        return mAmount;
    }

    /**
     * Sets the TransactionReference field.
     *
     * @param pTransactionReference
     *  String to use to update the field.
     */
    public void setTransactionReference(String pTransactionReference){
        this.mTransactionReference = pTransactionReference;
        setDirty(true);
    }
    /**
     * Retrieves the TransactionReference field.
     *
     * @return
     *  String containing the TransactionReference field.
     */
    public String getTransactionReference(){
        return mTransactionReference;
    }

    /**
     * Sets the AuthCode field.
     *
     * @param pAuthCode
     *  String to use to update the field.
     */
    public void setAuthCode(String pAuthCode){
        this.mAuthCode = pAuthCode;
        setDirty(true);
    }
    /**
     * Retrieves the AuthCode field.
     *
     * @return
     *  String containing the AuthCode field.
     */
    public String getAuthCode(){
        return mAuthCode;
    }

    /**
     * Sets the PaymetricResponseCode field.
     *
     * @param pPaymetricResponseCode
     *  String to use to update the field.
     */
    public void setPaymetricResponseCode(String pPaymetricResponseCode){
        this.mPaymetricResponseCode = pPaymetricResponseCode;
        setDirty(true);
    }
    /**
     * Retrieves the PaymetricResponseCode field.
     *
     * @return
     *  String containing the PaymetricResponseCode field.
     */
    public String getPaymetricResponseCode(){
        return mPaymetricResponseCode;
    }

    /**
     * Sets the PaymetricAuthDate field.
     *
     * @param pPaymetricAuthDate
     *  Date to use to update the field.
     */
    public void setPaymetricAuthDate(Date pPaymetricAuthDate){
        this.mPaymetricAuthDate = pPaymetricAuthDate;
        setDirty(true);
    }
    /**
     * Retrieves the PaymetricAuthDate field.
     *
     * @return
     *  Date containing the PaymetricAuthDate field.
     */
    public Date getPaymetricAuthDate(){
        return mPaymetricAuthDate;
    }

    /**
     * Sets the PaymetricAuthTime field.
     *
     * @param pPaymetricAuthTime
     *  Date to use to update the field.
     */
    public void setPaymetricAuthTime(Date pPaymetricAuthTime){
        this.mPaymetricAuthTime = pPaymetricAuthTime;
        setDirty(true);
    }
    /**
     * Retrieves the PaymetricAuthTime field.
     *
     * @return
     *  Date containing the PaymetricAuthTime field.
     */
    public Date getPaymetricAuthTime(){
        return mPaymetricAuthTime;
    }

    /**
     * Sets the PaymetricTransactionId field.
     *
     * @param pPaymetricTransactionId
     *  String to use to update the field.
     */
    public void setPaymetricTransactionId(String pPaymetricTransactionId){
        this.mPaymetricTransactionId = pPaymetricTransactionId;
        setDirty(true);
    }
    /**
     * Retrieves the PaymetricTransactionId field.
     *
     * @return
     *  String containing the PaymetricTransactionId field.
     */
    public String getPaymetricTransactionId(){
        return mPaymetricTransactionId;
    }

    /**
     * Sets the PaymetricAuthMessage field.
     *
     * @param pPaymetricAuthMessage
     *  String to use to update the field.
     */
    public void setPaymetricAuthMessage(String pPaymetricAuthMessage){
        this.mPaymetricAuthMessage = pPaymetricAuthMessage;
        setDirty(true);
    }
    /**
     * Retrieves the PaymetricAuthMessage field.
     *
     * @return
     *  String containing the PaymetricAuthMessage field.
     */
    public String getPaymetricAuthMessage(){
        return mPaymetricAuthMessage;
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


}
