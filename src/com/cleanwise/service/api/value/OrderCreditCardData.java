
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderCreditCardData
 * Description:  This is a ValueObject class wrapping the database table CLW_ORDER_CREDIT_CARD.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.OrderCreditCardDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>OrderCreditCardData</code> is a ValueObject class wrapping of the database table CLW_ORDER_CREDIT_CARD.
 */
public class OrderCreditCardData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -6570371762104187101L;
    private int mOrderCreditCardId;// SQL type:NUMBER, not null
    private int mOrderId;// SQL type:NUMBER
    private String mEncryptionAlgorithm;// SQL type:VARCHAR2
    private String mEncryptionAlias;// SQL type:VARCHAR2
    private String mEncryptedCreditCardNumber;// SQL type:VARCHAR2
    private String mCreditCardNumberDisplay;// SQL type:VARCHAR2
    private String mCreditCardType;// SQL type:VARCHAR2
    private String mHashAlgorithm;// SQL type:VARCHAR2
    private String mHashedCreditCardNumber;// SQL type:VARCHAR2
    private int mExpMonth;// SQL type:NUMBER
    private int mExpYear;// SQL type:NUMBER
    private String mName;// SQL type:VARCHAR2
    private String mAddress1;// SQL type:VARCHAR2
    private String mAddress2;// SQL type:VARCHAR2
    private String mAddress3;// SQL type:VARCHAR2
    private String mAddress4;// SQL type:VARCHAR2
    private String mCity;// SQL type:VARCHAR2
    private String mStateProvinceCd;// SQL type:VARCHAR2
    private String mCountryCd;// SQL type:VARCHAR2
    private String mPostalCode;// SQL type:VARCHAR2
    private String mAuthStatusCd;// SQL type:VARCHAR2
    private String mAuthAddressStatusCd;// SQL type:VARCHAR2
    private String mCurrency;// SQL type:VARCHAR2
    private String mAvsStatus;// SQL type:VARCHAR2
    private String mAvsAddress;// SQL type:VARCHAR2
    private String mAvsZipCode;// SQL type:VARCHAR2
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE

    /**
     * Constructor.
     */
    public OrderCreditCardData ()
    {
        mEncryptionAlgorithm = "";
        mEncryptionAlias = "";
        mEncryptedCreditCardNumber = "";
        mCreditCardNumberDisplay = "";
        mCreditCardType = "";
        mHashAlgorithm = "";
        mHashedCreditCardNumber = "";
        mName = "";
        mAddress1 = "";
        mAddress2 = "";
        mAddress3 = "";
        mAddress4 = "";
        mCity = "";
        mStateProvinceCd = "";
        mCountryCd = "";
        mPostalCode = "";
        mAuthStatusCd = "";
        mAuthAddressStatusCd = "";
        mCurrency = "";
        mAvsStatus = "";
        mAvsAddress = "";
        mAvsZipCode = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public OrderCreditCardData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, int parm10, int parm11, String parm12, String parm13, String parm14, String parm15, String parm16, String parm17, String parm18, String parm19, String parm20, String parm21, String parm22, String parm23, String parm24, String parm25, String parm26, String parm27, Date parm28, String parm29, Date parm30)
    {
        mOrderCreditCardId = parm1;
        mOrderId = parm2;
        mEncryptionAlgorithm = parm3;
        mEncryptionAlias = parm4;
        mEncryptedCreditCardNumber = parm5;
        mCreditCardNumberDisplay = parm6;
        mCreditCardType = parm7;
        mHashAlgorithm = parm8;
        mHashedCreditCardNumber = parm9;
        mExpMonth = parm10;
        mExpYear = parm11;
        mName = parm12;
        mAddress1 = parm13;
        mAddress2 = parm14;
        mAddress3 = parm15;
        mAddress4 = parm16;
        mCity = parm17;
        mStateProvinceCd = parm18;
        mCountryCd = parm19;
        mPostalCode = parm20;
        mAuthStatusCd = parm21;
        mAuthAddressStatusCd = parm22;
        mCurrency = parm23;
        mAvsStatus = parm24;
        mAvsAddress = parm25;
        mAvsZipCode = parm26;
        mAddBy = parm27;
        mAddDate = parm28;
        mModBy = parm29;
        mModDate = parm30;
        
    }

    /**
     * Creates a new OrderCreditCardData
     *
     * @return
     *  Newly initialized OrderCreditCardData object.
     */
    public static OrderCreditCardData createValue ()
    {
        OrderCreditCardData valueData = new OrderCreditCardData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderCreditCardData object
     */
    public String toString()
    {
        return "[" + "OrderCreditCardId=" + mOrderCreditCardId + ", OrderId=" + mOrderId + ", EncryptionAlgorithm=" + mEncryptionAlgorithm + ", EncryptionAlias=" + mEncryptionAlias + ", EncryptedCreditCardNumber=" + mEncryptedCreditCardNumber + ", CreditCardNumberDisplay=" + mCreditCardNumberDisplay + ", CreditCardType=" + mCreditCardType + ", HashAlgorithm=" + mHashAlgorithm + ", HashedCreditCardNumber=" + mHashedCreditCardNumber + ", ExpMonth=" + mExpMonth + ", ExpYear=" + mExpYear + ", Name=" + mName + ", Address1=" + mAddress1 + ", Address2=" + mAddress2 + ", Address3=" + mAddress3 + ", Address4=" + mAddress4 + ", City=" + mCity + ", StateProvinceCd=" + mStateProvinceCd + ", CountryCd=" + mCountryCd + ", PostalCode=" + mPostalCode + ", AuthStatusCd=" + mAuthStatusCd + ", AuthAddressStatusCd=" + mAuthAddressStatusCd + ", Currency=" + mCurrency + ", AvsStatus=" + mAvsStatus + ", AvsAddress=" + mAvsAddress + ", AvsZipCode=" + mAvsZipCode + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("OrderCreditCard");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mOrderCreditCardId));

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node =  doc.createElement("EncryptionAlgorithm");
        node.appendChild(doc.createTextNode(String.valueOf(mEncryptionAlgorithm)));
        root.appendChild(node);

        node =  doc.createElement("EncryptionAlias");
        node.appendChild(doc.createTextNode(String.valueOf(mEncryptionAlias)));
        root.appendChild(node);

        node =  doc.createElement("EncryptedCreditCardNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mEncryptedCreditCardNumber)));
        root.appendChild(node);

        node =  doc.createElement("CreditCardNumberDisplay");
        node.appendChild(doc.createTextNode(String.valueOf(mCreditCardNumberDisplay)));
        root.appendChild(node);

        node =  doc.createElement("CreditCardType");
        node.appendChild(doc.createTextNode(String.valueOf(mCreditCardType)));
        root.appendChild(node);

        node =  doc.createElement("HashAlgorithm");
        node.appendChild(doc.createTextNode(String.valueOf(mHashAlgorithm)));
        root.appendChild(node);

        node =  doc.createElement("HashedCreditCardNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mHashedCreditCardNumber)));
        root.appendChild(node);

        node =  doc.createElement("ExpMonth");
        node.appendChild(doc.createTextNode(String.valueOf(mExpMonth)));
        root.appendChild(node);

        node =  doc.createElement("ExpYear");
        node.appendChild(doc.createTextNode(String.valueOf(mExpYear)));
        root.appendChild(node);

        node =  doc.createElement("Name");
        node.appendChild(doc.createTextNode(String.valueOf(mName)));
        root.appendChild(node);

        node =  doc.createElement("Address1");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress1)));
        root.appendChild(node);

        node =  doc.createElement("Address2");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress2)));
        root.appendChild(node);

        node =  doc.createElement("Address3");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress3)));
        root.appendChild(node);

        node =  doc.createElement("Address4");
        node.appendChild(doc.createTextNode(String.valueOf(mAddress4)));
        root.appendChild(node);

        node =  doc.createElement("City");
        node.appendChild(doc.createTextNode(String.valueOf(mCity)));
        root.appendChild(node);

        node =  doc.createElement("StateProvinceCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStateProvinceCd)));
        root.appendChild(node);

        node =  doc.createElement("CountryCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCountryCd)));
        root.appendChild(node);

        node =  doc.createElement("PostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mPostalCode)));
        root.appendChild(node);

        node =  doc.createElement("AuthStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mAuthStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("AuthAddressStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mAuthAddressStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("Currency");
        node.appendChild(doc.createTextNode(String.valueOf(mCurrency)));
        root.appendChild(node);

        node =  doc.createElement("AvsStatus");
        node.appendChild(doc.createTextNode(String.valueOf(mAvsStatus)));
        root.appendChild(node);

        node =  doc.createElement("AvsAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mAvsAddress)));
        root.appendChild(node);

        node =  doc.createElement("AvsZipCode");
        node.appendChild(doc.createTextNode(String.valueOf(mAvsZipCode)));
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
    * creates a clone of this object, the OrderCreditCardId field is not cloned.
    *
    * @return OrderCreditCardData object
    */
    public Object clone(){
        OrderCreditCardData myClone = new OrderCreditCardData();
        
        myClone.mOrderId = mOrderId;
        
        myClone.mEncryptionAlgorithm = mEncryptionAlgorithm;
        
        myClone.mEncryptionAlias = mEncryptionAlias;
        
        myClone.mEncryptedCreditCardNumber = mEncryptedCreditCardNumber;
        
        myClone.mCreditCardNumberDisplay = mCreditCardNumberDisplay;
        
        myClone.mCreditCardType = mCreditCardType;
        
        myClone.mHashAlgorithm = mHashAlgorithm;
        
        myClone.mHashedCreditCardNumber = mHashedCreditCardNumber;
        
        myClone.mExpMonth = mExpMonth;
        
        myClone.mExpYear = mExpYear;
        
        myClone.mName = mName;
        
        myClone.mAddress1 = mAddress1;
        
        myClone.mAddress2 = mAddress2;
        
        myClone.mAddress3 = mAddress3;
        
        myClone.mAddress4 = mAddress4;
        
        myClone.mCity = mCity;
        
        myClone.mStateProvinceCd = mStateProvinceCd;
        
        myClone.mCountryCd = mCountryCd;
        
        myClone.mPostalCode = mPostalCode;
        
        myClone.mAuthStatusCd = mAuthStatusCd;
        
        myClone.mAuthAddressStatusCd = mAuthAddressStatusCd;
        
        myClone.mCurrency = mCurrency;
        
        myClone.mAvsStatus = mAvsStatus;
        
        myClone.mAvsAddress = mAvsAddress;
        
        myClone.mAvsZipCode = mAvsZipCode;
        
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

        if (OrderCreditCardDataAccess.ORDER_CREDIT_CARD_ID.equals(pFieldName)) {
            return getOrderCreditCardId();
        } else if (OrderCreditCardDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (OrderCreditCardDataAccess.ENCRYPTION_ALGORITHM.equals(pFieldName)) {
            return getEncryptionAlgorithm();
        } else if (OrderCreditCardDataAccess.ENCRYPTION_ALIAS.equals(pFieldName)) {
            return getEncryptionAlias();
        } else if (OrderCreditCardDataAccess.ENCRYPTED_CREDIT_CARD_NUMBER.equals(pFieldName)) {
            return getEncryptedCreditCardNumber();
        } else if (OrderCreditCardDataAccess.CREDIT_CARD_NUMBER_DISPLAY.equals(pFieldName)) {
            return getCreditCardNumberDisplay();
        } else if (OrderCreditCardDataAccess.CREDIT_CARD_TYPE.equals(pFieldName)) {
            return getCreditCardType();
        } else if (OrderCreditCardDataAccess.HASH_ALGORITHM.equals(pFieldName)) {
            return getHashAlgorithm();
        } else if (OrderCreditCardDataAccess.HASHED_CREDIT_CARD_NUMBER.equals(pFieldName)) {
            return getHashedCreditCardNumber();
        } else if (OrderCreditCardDataAccess.EXP_MONTH.equals(pFieldName)) {
            return getExpMonth();
        } else if (OrderCreditCardDataAccess.EXP_YEAR.equals(pFieldName)) {
            return getExpYear();
        } else if (OrderCreditCardDataAccess.NAME.equals(pFieldName)) {
            return getName();
        } else if (OrderCreditCardDataAccess.ADDRESS1.equals(pFieldName)) {
            return getAddress1();
        } else if (OrderCreditCardDataAccess.ADDRESS2.equals(pFieldName)) {
            return getAddress2();
        } else if (OrderCreditCardDataAccess.ADDRESS3.equals(pFieldName)) {
            return getAddress3();
        } else if (OrderCreditCardDataAccess.ADDRESS4.equals(pFieldName)) {
            return getAddress4();
        } else if (OrderCreditCardDataAccess.CITY.equals(pFieldName)) {
            return getCity();
        } else if (OrderCreditCardDataAccess.STATE_PROVINCE_CD.equals(pFieldName)) {
            return getStateProvinceCd();
        } else if (OrderCreditCardDataAccess.COUNTRY_CD.equals(pFieldName)) {
            return getCountryCd();
        } else if (OrderCreditCardDataAccess.POSTAL_CODE.equals(pFieldName)) {
            return getPostalCode();
        } else if (OrderCreditCardDataAccess.AUTH_STATUS_CD.equals(pFieldName)) {
            return getAuthStatusCd();
        } else if (OrderCreditCardDataAccess.AUTH_ADDRESS_STATUS_CD.equals(pFieldName)) {
            return getAuthAddressStatusCd();
        } else if (OrderCreditCardDataAccess.CURRENCY.equals(pFieldName)) {
            return getCurrency();
        } else if (OrderCreditCardDataAccess.AVS_STATUS.equals(pFieldName)) {
            return getAvsStatus();
        } else if (OrderCreditCardDataAccess.AVS_ADDRESS.equals(pFieldName)) {
            return getAvsAddress();
        } else if (OrderCreditCardDataAccess.AVS_ZIP_CODE.equals(pFieldName)) {
            return getAvsZipCode();
        } else if (OrderCreditCardDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (OrderCreditCardDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (OrderCreditCardDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (OrderCreditCardDataAccess.MOD_DATE.equals(pFieldName)) {
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
        return OrderCreditCardDataAccess.CLW_ORDER_CREDIT_CARD;
    }

    
    /**
     * Sets the OrderCreditCardId field. This field is required to be set in the database.
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
     * Sets the EncryptionAlgorithm field.
     *
     * @param pEncryptionAlgorithm
     *  String to use to update the field.
     */
    public void setEncryptionAlgorithm(String pEncryptionAlgorithm){
        this.mEncryptionAlgorithm = pEncryptionAlgorithm;
        setDirty(true);
    }
    /**
     * Retrieves the EncryptionAlgorithm field.
     *
     * @return
     *  String containing the EncryptionAlgorithm field.
     */
    public String getEncryptionAlgorithm(){
        return mEncryptionAlgorithm;
    }

    /**
     * Sets the EncryptionAlias field.
     *
     * @param pEncryptionAlias
     *  String to use to update the field.
     */
    public void setEncryptionAlias(String pEncryptionAlias){
        this.mEncryptionAlias = pEncryptionAlias;
        setDirty(true);
    }
    /**
     * Retrieves the EncryptionAlias field.
     *
     * @return
     *  String containing the EncryptionAlias field.
     */
    public String getEncryptionAlias(){
        return mEncryptionAlias;
    }

    /**
     * Sets the EncryptedCreditCardNumber field.
     *
     * @param pEncryptedCreditCardNumber
     *  String to use to update the field.
     */
    public void setEncryptedCreditCardNumber(String pEncryptedCreditCardNumber){
        this.mEncryptedCreditCardNumber = pEncryptedCreditCardNumber;
        setDirty(true);
    }
    /**
     * Retrieves the EncryptedCreditCardNumber field.
     *
     * @return
     *  String containing the EncryptedCreditCardNumber field.
     */
    public String getEncryptedCreditCardNumber(){
        return mEncryptedCreditCardNumber;
    }

    /**
     * Sets the CreditCardNumberDisplay field.
     *
     * @param pCreditCardNumberDisplay
     *  String to use to update the field.
     */
    public void setCreditCardNumberDisplay(String pCreditCardNumberDisplay){
        this.mCreditCardNumberDisplay = pCreditCardNumberDisplay;
        setDirty(true);
    }
    /**
     * Retrieves the CreditCardNumberDisplay field.
     *
     * @return
     *  String containing the CreditCardNumberDisplay field.
     */
    public String getCreditCardNumberDisplay(){
        return mCreditCardNumberDisplay;
    }

    /**
     * Sets the CreditCardType field.
     *
     * @param pCreditCardType
     *  String to use to update the field.
     */
    public void setCreditCardType(String pCreditCardType){
        this.mCreditCardType = pCreditCardType;
        setDirty(true);
    }
    /**
     * Retrieves the CreditCardType field.
     *
     * @return
     *  String containing the CreditCardType field.
     */
    public String getCreditCardType(){
        return mCreditCardType;
    }

    /**
     * Sets the HashAlgorithm field.
     *
     * @param pHashAlgorithm
     *  String to use to update the field.
     */
    public void setHashAlgorithm(String pHashAlgorithm){
        this.mHashAlgorithm = pHashAlgorithm;
        setDirty(true);
    }
    /**
     * Retrieves the HashAlgorithm field.
     *
     * @return
     *  String containing the HashAlgorithm field.
     */
    public String getHashAlgorithm(){
        return mHashAlgorithm;
    }

    /**
     * Sets the HashedCreditCardNumber field.
     *
     * @param pHashedCreditCardNumber
     *  String to use to update the field.
     */
    public void setHashedCreditCardNumber(String pHashedCreditCardNumber){
        this.mHashedCreditCardNumber = pHashedCreditCardNumber;
        setDirty(true);
    }
    /**
     * Retrieves the HashedCreditCardNumber field.
     *
     * @return
     *  String containing the HashedCreditCardNumber field.
     */
    public String getHashedCreditCardNumber(){
        return mHashedCreditCardNumber;
    }

    /**
     * Sets the ExpMonth field.
     *
     * @param pExpMonth
     *  int to use to update the field.
     */
    public void setExpMonth(int pExpMonth){
        this.mExpMonth = pExpMonth;
        setDirty(true);
    }
    /**
     * Retrieves the ExpMonth field.
     *
     * @return
     *  int containing the ExpMonth field.
     */
    public int getExpMonth(){
        return mExpMonth;
    }

    /**
     * Sets the ExpYear field.
     *
     * @param pExpYear
     *  int to use to update the field.
     */
    public void setExpYear(int pExpYear){
        this.mExpYear = pExpYear;
        setDirty(true);
    }
    /**
     * Retrieves the ExpYear field.
     *
     * @return
     *  int containing the ExpYear field.
     */
    public int getExpYear(){
        return mExpYear;
    }

    /**
     * Sets the Name field.
     *
     * @param pName
     *  String to use to update the field.
     */
    public void setName(String pName){
        this.mName = pName;
        setDirty(true);
    }
    /**
     * Retrieves the Name field.
     *
     * @return
     *  String containing the Name field.
     */
    public String getName(){
        return mName;
    }

    /**
     * Sets the Address1 field.
     *
     * @param pAddress1
     *  String to use to update the field.
     */
    public void setAddress1(String pAddress1){
        this.mAddress1 = pAddress1;
        setDirty(true);
    }
    /**
     * Retrieves the Address1 field.
     *
     * @return
     *  String containing the Address1 field.
     */
    public String getAddress1(){
        return mAddress1;
    }

    /**
     * Sets the Address2 field.
     *
     * @param pAddress2
     *  String to use to update the field.
     */
    public void setAddress2(String pAddress2){
        this.mAddress2 = pAddress2;
        setDirty(true);
    }
    /**
     * Retrieves the Address2 field.
     *
     * @return
     *  String containing the Address2 field.
     */
    public String getAddress2(){
        return mAddress2;
    }

    /**
     * Sets the Address3 field.
     *
     * @param pAddress3
     *  String to use to update the field.
     */
    public void setAddress3(String pAddress3){
        this.mAddress3 = pAddress3;
        setDirty(true);
    }
    /**
     * Retrieves the Address3 field.
     *
     * @return
     *  String containing the Address3 field.
     */
    public String getAddress3(){
        return mAddress3;
    }

    /**
     * Sets the Address4 field.
     *
     * @param pAddress4
     *  String to use to update the field.
     */
    public void setAddress4(String pAddress4){
        this.mAddress4 = pAddress4;
        setDirty(true);
    }
    /**
     * Retrieves the Address4 field.
     *
     * @return
     *  String containing the Address4 field.
     */
    public String getAddress4(){
        return mAddress4;
    }

    /**
     * Sets the City field.
     *
     * @param pCity
     *  String to use to update the field.
     */
    public void setCity(String pCity){
        this.mCity = pCity;
        setDirty(true);
    }
    /**
     * Retrieves the City field.
     *
     * @return
     *  String containing the City field.
     */
    public String getCity(){
        return mCity;
    }

    /**
     * Sets the StateProvinceCd field.
     *
     * @param pStateProvinceCd
     *  String to use to update the field.
     */
    public void setStateProvinceCd(String pStateProvinceCd){
        this.mStateProvinceCd = pStateProvinceCd;
        setDirty(true);
    }
    /**
     * Retrieves the StateProvinceCd field.
     *
     * @return
     *  String containing the StateProvinceCd field.
     */
    public String getStateProvinceCd(){
        return mStateProvinceCd;
    }

    /**
     * Sets the CountryCd field.
     *
     * @param pCountryCd
     *  String to use to update the field.
     */
    public void setCountryCd(String pCountryCd){
        this.mCountryCd = pCountryCd;
        setDirty(true);
    }
    /**
     * Retrieves the CountryCd field.
     *
     * @return
     *  String containing the CountryCd field.
     */
    public String getCountryCd(){
        return mCountryCd;
    }

    /**
     * Sets the PostalCode field.
     *
     * @param pPostalCode
     *  String to use to update the field.
     */
    public void setPostalCode(String pPostalCode){
        this.mPostalCode = pPostalCode;
        setDirty(true);
    }
    /**
     * Retrieves the PostalCode field.
     *
     * @return
     *  String containing the PostalCode field.
     */
    public String getPostalCode(){
        return mPostalCode;
    }

    /**
     * Sets the AuthStatusCd field.
     *
     * @param pAuthStatusCd
     *  String to use to update the field.
     */
    public void setAuthStatusCd(String pAuthStatusCd){
        this.mAuthStatusCd = pAuthStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the AuthStatusCd field.
     *
     * @return
     *  String containing the AuthStatusCd field.
     */
    public String getAuthStatusCd(){
        return mAuthStatusCd;
    }

    /**
     * Sets the AuthAddressStatusCd field.
     *
     * @param pAuthAddressStatusCd
     *  String to use to update the field.
     */
    public void setAuthAddressStatusCd(String pAuthAddressStatusCd){
        this.mAuthAddressStatusCd = pAuthAddressStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the AuthAddressStatusCd field.
     *
     * @return
     *  String containing the AuthAddressStatusCd field.
     */
    public String getAuthAddressStatusCd(){
        return mAuthAddressStatusCd;
    }

    /**
     * Sets the Currency field.
     *
     * @param pCurrency
     *  String to use to update the field.
     */
    public void setCurrency(String pCurrency){
        this.mCurrency = pCurrency;
        setDirty(true);
    }
    /**
     * Retrieves the Currency field.
     *
     * @return
     *  String containing the Currency field.
     */
    public String getCurrency(){
        return mCurrency;
    }

    /**
     * Sets the AvsStatus field.
     *
     * @param pAvsStatus
     *  String to use to update the field.
     */
    public void setAvsStatus(String pAvsStatus){
        this.mAvsStatus = pAvsStatus;
        setDirty(true);
    }
    /**
     * Retrieves the AvsStatus field.
     *
     * @return
     *  String containing the AvsStatus field.
     */
    public String getAvsStatus(){
        return mAvsStatus;
    }

    /**
     * Sets the AvsAddress field.
     *
     * @param pAvsAddress
     *  String to use to update the field.
     */
    public void setAvsAddress(String pAvsAddress){
        this.mAvsAddress = pAvsAddress;
        setDirty(true);
    }
    /**
     * Retrieves the AvsAddress field.
     *
     * @return
     *  String containing the AvsAddress field.
     */
    public String getAvsAddress(){
        return mAvsAddress;
    }

    /**
     * Sets the AvsZipCode field.
     *
     * @param pAvsZipCode
     *  String to use to update the field.
     */
    public void setAvsZipCode(String pAvsZipCode){
        this.mAvsZipCode = pAvsZipCode;
        setDirty(true);
    }
    /**
     * Retrieves the AvsZipCode field.
     *
     * @return
     *  String containing the AvsZipCode field.
     */
    public String getAvsZipCode(){
        return mAvsZipCode;
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
