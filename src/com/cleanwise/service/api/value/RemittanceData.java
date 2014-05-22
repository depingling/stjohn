
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        RemittanceData
 * Description:  This is a ValueObject class wrapping the database table CLW_REMITTANCE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.RemittanceDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>RemittanceData</code> is a ValueObject class wrapping of the database table CLW_REMITTANCE.
 */
public class RemittanceData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1313356444318028132L;
    private int mRemittanceId;// SQL type:NUMBER, not null
    private int mStoreId;// SQL type:NUMBER, not null
    private String mHandlingCode;// SQL type:VARCHAR2
    private java.math.BigDecimal mTotalPaymentAmount;// SQL type:NUMBER
    private String mCreditDebit;// SQL type:VARCHAR2
    private String mPaymentType;// SQL type:VARCHAR2
    private String mPayerBank;// SQL type:VARCHAR2
    private String mPayerBankAccount;// SQL type:VARCHAR2
    private String mPayerId;// SQL type:VARCHAR2
    private String mPayeeBank;// SQL type:VARCHAR2
    private String mPayeeBankAccount;// SQL type:VARCHAR2
    private Date mPaymentPostDate;// SQL type:DATE
    private String mPaymentReferenceNumberType;// SQL type:VARCHAR2
    private String mPaymentReferenceNumber;// SQL type:VARCHAR2
    private Date mCheckDate;// SQL type:DATE
    private String mPayeeErpAccount;// SQL type:VARCHAR2
    private Date mTransactionDate;// SQL type:DATE
    private String mTransactionReference;// SQL type:VARCHAR2
    private String mRemittanceStatusCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2, not null
    private String mTransactionCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public RemittanceData ()
    {
        mHandlingCode = "";
        mCreditDebit = "";
        mPaymentType = "";
        mPayerBank = "";
        mPayerBankAccount = "";
        mPayerId = "";
        mPayeeBank = "";
        mPayeeBankAccount = "";
        mPaymentReferenceNumberType = "";
        mPaymentReferenceNumber = "";
        mPayeeErpAccount = "";
        mTransactionReference = "";
        mRemittanceStatusCd = "";
        mAddBy = "";
        mModBy = "";
        mTransactionCd = "";
    }

    /**
     * Constructor.
     */
    public RemittanceData(int parm1, int parm2, String parm3, java.math.BigDecimal parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, Date parm12, String parm13, String parm14, Date parm15, String parm16, Date parm17, String parm18, String parm19, Date parm20, String parm21, Date parm22, String parm23, String parm24)
    {
        mRemittanceId = parm1;
        mStoreId = parm2;
        mHandlingCode = parm3;
        mTotalPaymentAmount = parm4;
        mCreditDebit = parm5;
        mPaymentType = parm6;
        mPayerBank = parm7;
        mPayerBankAccount = parm8;
        mPayerId = parm9;
        mPayeeBank = parm10;
        mPayeeBankAccount = parm11;
        mPaymentPostDate = parm12;
        mPaymentReferenceNumberType = parm13;
        mPaymentReferenceNumber = parm14;
        mCheckDate = parm15;
        mPayeeErpAccount = parm16;
        mTransactionDate = parm17;
        mTransactionReference = parm18;
        mRemittanceStatusCd = parm19;
        mAddDate = parm20;
        mAddBy = parm21;
        mModDate = parm22;
        mModBy = parm23;
        mTransactionCd = parm24;
        
    }

    /**
     * Creates a new RemittanceData
     *
     * @return
     *  Newly initialized RemittanceData object.
     */
    public static RemittanceData createValue ()
    {
        RemittanceData valueData = new RemittanceData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this RemittanceData object
     */
    public String toString()
    {
        return "[" + "RemittanceId=" + mRemittanceId + ", StoreId=" + mStoreId + ", HandlingCode=" + mHandlingCode + ", TotalPaymentAmount=" + mTotalPaymentAmount + ", CreditDebit=" + mCreditDebit + ", PaymentType=" + mPaymentType + ", PayerBank=" + mPayerBank + ", PayerBankAccount=" + mPayerBankAccount + ", PayerId=" + mPayerId + ", PayeeBank=" + mPayeeBank + ", PayeeBankAccount=" + mPayeeBankAccount + ", PaymentPostDate=" + mPaymentPostDate + ", PaymentReferenceNumberType=" + mPaymentReferenceNumberType + ", PaymentReferenceNumber=" + mPaymentReferenceNumber + ", CheckDate=" + mCheckDate + ", PayeeErpAccount=" + mPayeeErpAccount + ", TransactionDate=" + mTransactionDate + ", TransactionReference=" + mTransactionReference + ", RemittanceStatusCd=" + mRemittanceStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", TransactionCd=" + mTransactionCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Remittance");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mRemittanceId));

        node =  doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        node =  doc.createElement("HandlingCode");
        node.appendChild(doc.createTextNode(String.valueOf(mHandlingCode)));
        root.appendChild(node);

        node =  doc.createElement("TotalPaymentAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalPaymentAmount)));
        root.appendChild(node);

        node =  doc.createElement("CreditDebit");
        node.appendChild(doc.createTextNode(String.valueOf(mCreditDebit)));
        root.appendChild(node);

        node =  doc.createElement("PaymentType");
        node.appendChild(doc.createTextNode(String.valueOf(mPaymentType)));
        root.appendChild(node);

        node =  doc.createElement("PayerBank");
        node.appendChild(doc.createTextNode(String.valueOf(mPayerBank)));
        root.appendChild(node);

        node =  doc.createElement("PayerBankAccount");
        node.appendChild(doc.createTextNode(String.valueOf(mPayerBankAccount)));
        root.appendChild(node);

        node =  doc.createElement("PayerId");
        node.appendChild(doc.createTextNode(String.valueOf(mPayerId)));
        root.appendChild(node);

        node =  doc.createElement("PayeeBank");
        node.appendChild(doc.createTextNode(String.valueOf(mPayeeBank)));
        root.appendChild(node);

        node =  doc.createElement("PayeeBankAccount");
        node.appendChild(doc.createTextNode(String.valueOf(mPayeeBankAccount)));
        root.appendChild(node);

        node =  doc.createElement("PaymentPostDate");
        node.appendChild(doc.createTextNode(String.valueOf(mPaymentPostDate)));
        root.appendChild(node);

        node =  doc.createElement("PaymentReferenceNumberType");
        node.appendChild(doc.createTextNode(String.valueOf(mPaymentReferenceNumberType)));
        root.appendChild(node);

        node =  doc.createElement("PaymentReferenceNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mPaymentReferenceNumber)));
        root.appendChild(node);

        node =  doc.createElement("CheckDate");
        node.appendChild(doc.createTextNode(String.valueOf(mCheckDate)));
        root.appendChild(node);

        node =  doc.createElement("PayeeErpAccount");
        node.appendChild(doc.createTextNode(String.valueOf(mPayeeErpAccount)));
        root.appendChild(node);

        node =  doc.createElement("TransactionDate");
        node.appendChild(doc.createTextNode(String.valueOf(mTransactionDate)));
        root.appendChild(node);

        node =  doc.createElement("TransactionReference");
        node.appendChild(doc.createTextNode(String.valueOf(mTransactionReference)));
        root.appendChild(node);

        node =  doc.createElement("RemittanceStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mRemittanceStatusCd)));
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

        node =  doc.createElement("TransactionCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTransactionCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the RemittanceId field is not cloned.
    *
    * @return RemittanceData object
    */
    public Object clone(){
        RemittanceData myClone = new RemittanceData();
        
        myClone.mStoreId = mStoreId;
        
        myClone.mHandlingCode = mHandlingCode;
        
        myClone.mTotalPaymentAmount = mTotalPaymentAmount;
        
        myClone.mCreditDebit = mCreditDebit;
        
        myClone.mPaymentType = mPaymentType;
        
        myClone.mPayerBank = mPayerBank;
        
        myClone.mPayerBankAccount = mPayerBankAccount;
        
        myClone.mPayerId = mPayerId;
        
        myClone.mPayeeBank = mPayeeBank;
        
        myClone.mPayeeBankAccount = mPayeeBankAccount;
        
        if(mPaymentPostDate != null){
                myClone.mPaymentPostDate = (Date) mPaymentPostDate.clone();
        }
        
        myClone.mPaymentReferenceNumberType = mPaymentReferenceNumberType;
        
        myClone.mPaymentReferenceNumber = mPaymentReferenceNumber;
        
        if(mCheckDate != null){
                myClone.mCheckDate = (Date) mCheckDate.clone();
        }
        
        myClone.mPayeeErpAccount = mPayeeErpAccount;
        
        if(mTransactionDate != null){
                myClone.mTransactionDate = (Date) mTransactionDate.clone();
        }
        
        myClone.mTransactionReference = mTransactionReference;
        
        myClone.mRemittanceStatusCd = mRemittanceStatusCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mTransactionCd = mTransactionCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (RemittanceDataAccess.REMITTANCE_ID.equals(pFieldName)) {
            return getRemittanceId();
        } else if (RemittanceDataAccess.STORE_ID.equals(pFieldName)) {
            return getStoreId();
        } else if (RemittanceDataAccess.HANDLING_CODE.equals(pFieldName)) {
            return getHandlingCode();
        } else if (RemittanceDataAccess.TOTAL_PAYMENT_AMOUNT.equals(pFieldName)) {
            return getTotalPaymentAmount();
        } else if (RemittanceDataAccess.CREDIT_DEBIT.equals(pFieldName)) {
            return getCreditDebit();
        } else if (RemittanceDataAccess.PAYMENT_TYPE.equals(pFieldName)) {
            return getPaymentType();
        } else if (RemittanceDataAccess.PAYER_BANK.equals(pFieldName)) {
            return getPayerBank();
        } else if (RemittanceDataAccess.PAYER_BANK_ACCOUNT.equals(pFieldName)) {
            return getPayerBankAccount();
        } else if (RemittanceDataAccess.PAYER_ID.equals(pFieldName)) {
            return getPayerId();
        } else if (RemittanceDataAccess.PAYEE_BANK.equals(pFieldName)) {
            return getPayeeBank();
        } else if (RemittanceDataAccess.PAYEE_BANK_ACCOUNT.equals(pFieldName)) {
            return getPayeeBankAccount();
        } else if (RemittanceDataAccess.PAYMENT_POST_DATE.equals(pFieldName)) {
            return getPaymentPostDate();
        } else if (RemittanceDataAccess.PAYMENT_REFERENCE_NUMBER_TYPE.equals(pFieldName)) {
            return getPaymentReferenceNumberType();
        } else if (RemittanceDataAccess.PAYMENT_REFERENCE_NUMBER.equals(pFieldName)) {
            return getPaymentReferenceNumber();
        } else if (RemittanceDataAccess.CHECK_DATE.equals(pFieldName)) {
            return getCheckDate();
        } else if (RemittanceDataAccess.PAYEE_ERP_ACCOUNT.equals(pFieldName)) {
            return getPayeeErpAccount();
        } else if (RemittanceDataAccess.TRANSACTION_DATE.equals(pFieldName)) {
            return getTransactionDate();
        } else if (RemittanceDataAccess.TRANSACTION_REFERENCE.equals(pFieldName)) {
            return getTransactionReference();
        } else if (RemittanceDataAccess.REMITTANCE_STATUS_CD.equals(pFieldName)) {
            return getRemittanceStatusCd();
        } else if (RemittanceDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (RemittanceDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (RemittanceDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (RemittanceDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (RemittanceDataAccess.TRANSACTION_CD.equals(pFieldName)) {
            return getTransactionCd();
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
        return RemittanceDataAccess.CLW_REMITTANCE;
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
     * Sets the StoreId field. This field is required to be set in the database.
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
     * Sets the HandlingCode field.
     *
     * @param pHandlingCode
     *  String to use to update the field.
     */
    public void setHandlingCode(String pHandlingCode){
        this.mHandlingCode = pHandlingCode;
        setDirty(true);
    }
    /**
     * Retrieves the HandlingCode field.
     *
     * @return
     *  String containing the HandlingCode field.
     */
    public String getHandlingCode(){
        return mHandlingCode;
    }

    /**
     * Sets the TotalPaymentAmount field.
     *
     * @param pTotalPaymentAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setTotalPaymentAmount(java.math.BigDecimal pTotalPaymentAmount){
        this.mTotalPaymentAmount = pTotalPaymentAmount;
        setDirty(true);
    }
    /**
     * Retrieves the TotalPaymentAmount field.
     *
     * @return
     *  java.math.BigDecimal containing the TotalPaymentAmount field.
     */
    public java.math.BigDecimal getTotalPaymentAmount(){
        return mTotalPaymentAmount;
    }

    /**
     * Sets the CreditDebit field.
     *
     * @param pCreditDebit
     *  String to use to update the field.
     */
    public void setCreditDebit(String pCreditDebit){
        this.mCreditDebit = pCreditDebit;
        setDirty(true);
    }
    /**
     * Retrieves the CreditDebit field.
     *
     * @return
     *  String containing the CreditDebit field.
     */
    public String getCreditDebit(){
        return mCreditDebit;
    }

    /**
     * Sets the PaymentType field.
     *
     * @param pPaymentType
     *  String to use to update the field.
     */
    public void setPaymentType(String pPaymentType){
        this.mPaymentType = pPaymentType;
        setDirty(true);
    }
    /**
     * Retrieves the PaymentType field.
     *
     * @return
     *  String containing the PaymentType field.
     */
    public String getPaymentType(){
        return mPaymentType;
    }

    /**
     * Sets the PayerBank field.
     *
     * @param pPayerBank
     *  String to use to update the field.
     */
    public void setPayerBank(String pPayerBank){
        this.mPayerBank = pPayerBank;
        setDirty(true);
    }
    /**
     * Retrieves the PayerBank field.
     *
     * @return
     *  String containing the PayerBank field.
     */
    public String getPayerBank(){
        return mPayerBank;
    }

    /**
     * Sets the PayerBankAccount field.
     *
     * @param pPayerBankAccount
     *  String to use to update the field.
     */
    public void setPayerBankAccount(String pPayerBankAccount){
        this.mPayerBankAccount = pPayerBankAccount;
        setDirty(true);
    }
    /**
     * Retrieves the PayerBankAccount field.
     *
     * @return
     *  String containing the PayerBankAccount field.
     */
    public String getPayerBankAccount(){
        return mPayerBankAccount;
    }

    /**
     * Sets the PayerId field.
     *
     * @param pPayerId
     *  String to use to update the field.
     */
    public void setPayerId(String pPayerId){
        this.mPayerId = pPayerId;
        setDirty(true);
    }
    /**
     * Retrieves the PayerId field.
     *
     * @return
     *  String containing the PayerId field.
     */
    public String getPayerId(){
        return mPayerId;
    }

    /**
     * Sets the PayeeBank field.
     *
     * @param pPayeeBank
     *  String to use to update the field.
     */
    public void setPayeeBank(String pPayeeBank){
        this.mPayeeBank = pPayeeBank;
        setDirty(true);
    }
    /**
     * Retrieves the PayeeBank field.
     *
     * @return
     *  String containing the PayeeBank field.
     */
    public String getPayeeBank(){
        return mPayeeBank;
    }

    /**
     * Sets the PayeeBankAccount field.
     *
     * @param pPayeeBankAccount
     *  String to use to update the field.
     */
    public void setPayeeBankAccount(String pPayeeBankAccount){
        this.mPayeeBankAccount = pPayeeBankAccount;
        setDirty(true);
    }
    /**
     * Retrieves the PayeeBankAccount field.
     *
     * @return
     *  String containing the PayeeBankAccount field.
     */
    public String getPayeeBankAccount(){
        return mPayeeBankAccount;
    }

    /**
     * Sets the PaymentPostDate field.
     *
     * @param pPaymentPostDate
     *  Date to use to update the field.
     */
    public void setPaymentPostDate(Date pPaymentPostDate){
        this.mPaymentPostDate = pPaymentPostDate;
        setDirty(true);
    }
    /**
     * Retrieves the PaymentPostDate field.
     *
     * @return
     *  Date containing the PaymentPostDate field.
     */
    public Date getPaymentPostDate(){
        return mPaymentPostDate;
    }

    /**
     * Sets the PaymentReferenceNumberType field.
     *
     * @param pPaymentReferenceNumberType
     *  String to use to update the field.
     */
    public void setPaymentReferenceNumberType(String pPaymentReferenceNumberType){
        this.mPaymentReferenceNumberType = pPaymentReferenceNumberType;
        setDirty(true);
    }
    /**
     * Retrieves the PaymentReferenceNumberType field.
     *
     * @return
     *  String containing the PaymentReferenceNumberType field.
     */
    public String getPaymentReferenceNumberType(){
        return mPaymentReferenceNumberType;
    }

    /**
     * Sets the PaymentReferenceNumber field.
     *
     * @param pPaymentReferenceNumber
     *  String to use to update the field.
     */
    public void setPaymentReferenceNumber(String pPaymentReferenceNumber){
        this.mPaymentReferenceNumber = pPaymentReferenceNumber;
        setDirty(true);
    }
    /**
     * Retrieves the PaymentReferenceNumber field.
     *
     * @return
     *  String containing the PaymentReferenceNumber field.
     */
    public String getPaymentReferenceNumber(){
        return mPaymentReferenceNumber;
    }

    /**
     * Sets the CheckDate field.
     *
     * @param pCheckDate
     *  Date to use to update the field.
     */
    public void setCheckDate(Date pCheckDate){
        this.mCheckDate = pCheckDate;
        setDirty(true);
    }
    /**
     * Retrieves the CheckDate field.
     *
     * @return
     *  Date containing the CheckDate field.
     */
    public Date getCheckDate(){
        return mCheckDate;
    }

    /**
     * Sets the PayeeErpAccount field.
     *
     * @param pPayeeErpAccount
     *  String to use to update the field.
     */
    public void setPayeeErpAccount(String pPayeeErpAccount){
        this.mPayeeErpAccount = pPayeeErpAccount;
        setDirty(true);
    }
    /**
     * Retrieves the PayeeErpAccount field.
     *
     * @return
     *  String containing the PayeeErpAccount field.
     */
    public String getPayeeErpAccount(){
        return mPayeeErpAccount;
    }

    /**
     * Sets the TransactionDate field.
     *
     * @param pTransactionDate
     *  Date to use to update the field.
     */
    public void setTransactionDate(Date pTransactionDate){
        this.mTransactionDate = pTransactionDate;
        setDirty(true);
    }
    /**
     * Retrieves the TransactionDate field.
     *
     * @return
     *  Date containing the TransactionDate field.
     */
    public Date getTransactionDate(){
        return mTransactionDate;
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
     * Sets the RemittanceStatusCd field.
     *
     * @param pRemittanceStatusCd
     *  String to use to update the field.
     */
    public void setRemittanceStatusCd(String pRemittanceStatusCd){
        this.mRemittanceStatusCd = pRemittanceStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the RemittanceStatusCd field.
     *
     * @return
     *  String containing the RemittanceStatusCd field.
     */
    public String getRemittanceStatusCd(){
        return mRemittanceStatusCd;
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
     * Sets the ModBy field. This field is required to be set in the database.
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


}
