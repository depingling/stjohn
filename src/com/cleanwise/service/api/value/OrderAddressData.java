
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderAddressData
 * Description:  This is a ValueObject class wrapping the database table CLW_ORDER_ADDRESS.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.OrderAddressDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>OrderAddressData</code> is a ValueObject class wrapping of the database table CLW_ORDER_ADDRESS.
 */
public class OrderAddressData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -2183027518829340211L;
    private int mOrderAddressId;// SQL type:NUMBER, not null
    private int mOrderId;// SQL type:NUMBER, not null
    private String mAddressTypeCd;// SQL type:VARCHAR2, not null
    private String mShortDesc;// SQL type:VARCHAR2
    private String mAddress1;// SQL type:VARCHAR2
    private String mAddress2;// SQL type:VARCHAR2
    private String mAddress3;// SQL type:VARCHAR2
    private String mAddress4;// SQL type:VARCHAR2
    private String mCity;// SQL type:VARCHAR2
    private String mStateProvinceCd;// SQL type:VARCHAR2
    private String mCountryCd;// SQL type:VARCHAR2
    private String mCountyCd;// SQL type:VARCHAR2
    private String mPostalCode;// SQL type:VARCHAR2
    private String mErpNum;// SQL type:VARCHAR2
    private String mEmailAddress;// SQL type:VARCHAR2
    private String mEmailTypeCd;// SQL type:VARCHAR2
    private String mPhoneNum;// SQL type:VARCHAR2
    private String mFaxPhoneNum;// SQL type:VARCHAR2
    private int mReturnRequestId;// SQL type:NUMBER
    private int mManifestItemId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public OrderAddressData ()
    {
        mAddressTypeCd = "";
        mShortDesc = "";
        mAddress1 = "";
        mAddress2 = "";
        mAddress3 = "";
        mAddress4 = "";
        mCity = "";
        mStateProvinceCd = "";
        mCountryCd = "";
        mCountyCd = "";
        mPostalCode = "";
        mErpNum = "";
        mEmailAddress = "";
        mEmailTypeCd = "";
        mPhoneNum = "";
        mFaxPhoneNum = "";
    }

    /**
     * Constructor.
     */
    public OrderAddressData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13, String parm14, String parm15, String parm16, String parm17, String parm18, int parm19, int parm20)
    {
        mOrderAddressId = parm1;
        mOrderId = parm2;
        mAddressTypeCd = parm3;
        mShortDesc = parm4;
        mAddress1 = parm5;
        mAddress2 = parm6;
        mAddress3 = parm7;
        mAddress4 = parm8;
        mCity = parm9;
        mStateProvinceCd = parm10;
        mCountryCd = parm11;
        mCountyCd = parm12;
        mPostalCode = parm13;
        mErpNum = parm14;
        mEmailAddress = parm15;
        mEmailTypeCd = parm16;
        mPhoneNum = parm17;
        mFaxPhoneNum = parm18;
        mReturnRequestId = parm19;
        mManifestItemId = parm20;
        
    }

    /**
     * Creates a new OrderAddressData
     *
     * @return
     *  Newly initialized OrderAddressData object.
     */
    public static OrderAddressData createValue ()
    {
        OrderAddressData valueData = new OrderAddressData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderAddressData object
     */
    public String toString()
    {
        return "[" + "OrderAddressId=" + mOrderAddressId + ", OrderId=" + mOrderId + ", AddressTypeCd=" + mAddressTypeCd + ", ShortDesc=" + mShortDesc + ", Address1=" + mAddress1 + ", Address2=" + mAddress2 + ", Address3=" + mAddress3 + ", Address4=" + mAddress4 + ", City=" + mCity + ", StateProvinceCd=" + mStateProvinceCd + ", CountryCd=" + mCountryCd + ", CountyCd=" + mCountyCd + ", PostalCode=" + mPostalCode + ", ErpNum=" + mErpNum + ", EmailAddress=" + mEmailAddress + ", EmailTypeCd=" + mEmailTypeCd + ", PhoneNum=" + mPhoneNum + ", FaxPhoneNum=" + mFaxPhoneNum + ", ReturnRequestId=" + mReturnRequestId + ", ManifestItemId=" + mManifestItemId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("OrderAddress");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mOrderAddressId));

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node =  doc.createElement("AddressTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mAddressTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
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

        node =  doc.createElement("CountyCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCountyCd)));
        root.appendChild(node);

        node =  doc.createElement("PostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mPostalCode)));
        root.appendChild(node);

        node =  doc.createElement("ErpNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpNum)));
        root.appendChild(node);

        node =  doc.createElement("EmailAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mEmailAddress)));
        root.appendChild(node);

        node =  doc.createElement("EmailTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mEmailTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("PhoneNum");
        node.appendChild(doc.createTextNode(String.valueOf(mPhoneNum)));
        root.appendChild(node);

        node =  doc.createElement("FaxPhoneNum");
        node.appendChild(doc.createTextNode(String.valueOf(mFaxPhoneNum)));
        root.appendChild(node);

        node =  doc.createElement("ReturnRequestId");
        node.appendChild(doc.createTextNode(String.valueOf(mReturnRequestId)));
        root.appendChild(node);

        node =  doc.createElement("ManifestItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mManifestItemId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the OrderAddressId field is not cloned.
    *
    * @return OrderAddressData object
    */
    public Object clone(){
        OrderAddressData myClone = new OrderAddressData();
        
        myClone.mOrderId = mOrderId;
        
        myClone.mAddressTypeCd = mAddressTypeCd;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mAddress1 = mAddress1;
        
        myClone.mAddress2 = mAddress2;
        
        myClone.mAddress3 = mAddress3;
        
        myClone.mAddress4 = mAddress4;
        
        myClone.mCity = mCity;
        
        myClone.mStateProvinceCd = mStateProvinceCd;
        
        myClone.mCountryCd = mCountryCd;
        
        myClone.mCountyCd = mCountyCd;
        
        myClone.mPostalCode = mPostalCode;
        
        myClone.mErpNum = mErpNum;
        
        myClone.mEmailAddress = mEmailAddress;
        
        myClone.mEmailTypeCd = mEmailTypeCd;
        
        myClone.mPhoneNum = mPhoneNum;
        
        myClone.mFaxPhoneNum = mFaxPhoneNum;
        
        myClone.mReturnRequestId = mReturnRequestId;
        
        myClone.mManifestItemId = mManifestItemId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (OrderAddressDataAccess.ORDER_ADDRESS_ID.equals(pFieldName)) {
            return getOrderAddressId();
        } else if (OrderAddressDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (OrderAddressDataAccess.ADDRESS_TYPE_CD.equals(pFieldName)) {
            return getAddressTypeCd();
        } else if (OrderAddressDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (OrderAddressDataAccess.ADDRESS1.equals(pFieldName)) {
            return getAddress1();
        } else if (OrderAddressDataAccess.ADDRESS2.equals(pFieldName)) {
            return getAddress2();
        } else if (OrderAddressDataAccess.ADDRESS3.equals(pFieldName)) {
            return getAddress3();
        } else if (OrderAddressDataAccess.ADDRESS4.equals(pFieldName)) {
            return getAddress4();
        } else if (OrderAddressDataAccess.CITY.equals(pFieldName)) {
            return getCity();
        } else if (OrderAddressDataAccess.STATE_PROVINCE_CD.equals(pFieldName)) {
            return getStateProvinceCd();
        } else if (OrderAddressDataAccess.COUNTRY_CD.equals(pFieldName)) {
            return getCountryCd();
        } else if (OrderAddressDataAccess.COUNTY_CD.equals(pFieldName)) {
            return getCountyCd();
        } else if (OrderAddressDataAccess.POSTAL_CODE.equals(pFieldName)) {
            return getPostalCode();
        } else if (OrderAddressDataAccess.ERP_NUM.equals(pFieldName)) {
            return getErpNum();
        } else if (OrderAddressDataAccess.EMAIL_ADDRESS.equals(pFieldName)) {
            return getEmailAddress();
        } else if (OrderAddressDataAccess.EMAIL_TYPE_CD.equals(pFieldName)) {
            return getEmailTypeCd();
        } else if (OrderAddressDataAccess.PHONE_NUM.equals(pFieldName)) {
            return getPhoneNum();
        } else if (OrderAddressDataAccess.FAX_PHONE_NUM.equals(pFieldName)) {
            return getFaxPhoneNum();
        } else if (OrderAddressDataAccess.RETURN_REQUEST_ID.equals(pFieldName)) {
            return getReturnRequestId();
        } else if (OrderAddressDataAccess.MANIFEST_ITEM_ID.equals(pFieldName)) {
            return getManifestItemId();
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
        return OrderAddressDataAccess.CLW_ORDER_ADDRESS;
    }

    
    /**
     * Sets the OrderAddressId field. This field is required to be set in the database.
     *
     * @param pOrderAddressId
     *  int to use to update the field.
     */
    public void setOrderAddressId(int pOrderAddressId){
        this.mOrderAddressId = pOrderAddressId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderAddressId field.
     *
     * @return
     *  int containing the OrderAddressId field.
     */
    public int getOrderAddressId(){
        return mOrderAddressId;
    }

    /**
     * Sets the OrderId field. This field is required to be set in the database.
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
     * Sets the AddressTypeCd field. This field is required to be set in the database.
     *
     * @param pAddressTypeCd
     *  String to use to update the field.
     */
    public void setAddressTypeCd(String pAddressTypeCd){
        this.mAddressTypeCd = pAddressTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the AddressTypeCd field.
     *
     * @return
     *  String containing the AddressTypeCd field.
     */
    public String getAddressTypeCd(){
        return mAddressTypeCd;
    }

    /**
     * Sets the ShortDesc field.
     *
     * @param pShortDesc
     *  String to use to update the field.
     */
    public void setShortDesc(String pShortDesc){
        this.mShortDesc = pShortDesc;
        setDirty(true);
    }
    /**
     * Retrieves the ShortDesc field.
     *
     * @return
     *  String containing the ShortDesc field.
     */
    public String getShortDesc(){
        return mShortDesc;
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
     * Sets the CountyCd field.
     *
     * @param pCountyCd
     *  String to use to update the field.
     */
    public void setCountyCd(String pCountyCd){
        this.mCountyCd = pCountyCd;
        setDirty(true);
    }
    /**
     * Retrieves the CountyCd field.
     *
     * @return
     *  String containing the CountyCd field.
     */
    public String getCountyCd(){
        return mCountyCd;
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
     * Sets the ErpNum field.
     *
     * @param pErpNum
     *  String to use to update the field.
     */
    public void setErpNum(String pErpNum){
        this.mErpNum = pErpNum;
        setDirty(true);
    }
    /**
     * Retrieves the ErpNum field.
     *
     * @return
     *  String containing the ErpNum field.
     */
    public String getErpNum(){
        return mErpNum;
    }

    /**
     * Sets the EmailAddress field.
     *
     * @param pEmailAddress
     *  String to use to update the field.
     */
    public void setEmailAddress(String pEmailAddress){
        this.mEmailAddress = pEmailAddress;
        setDirty(true);
    }
    /**
     * Retrieves the EmailAddress field.
     *
     * @return
     *  String containing the EmailAddress field.
     */
    public String getEmailAddress(){
        return mEmailAddress;
    }

    /**
     * Sets the EmailTypeCd field.
     *
     * @param pEmailTypeCd
     *  String to use to update the field.
     */
    public void setEmailTypeCd(String pEmailTypeCd){
        this.mEmailTypeCd = pEmailTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the EmailTypeCd field.
     *
     * @return
     *  String containing the EmailTypeCd field.
     */
    public String getEmailTypeCd(){
        return mEmailTypeCd;
    }

    /**
     * Sets the PhoneNum field.
     *
     * @param pPhoneNum
     *  String to use to update the field.
     */
    public void setPhoneNum(String pPhoneNum){
        this.mPhoneNum = pPhoneNum;
        setDirty(true);
    }
    /**
     * Retrieves the PhoneNum field.
     *
     * @return
     *  String containing the PhoneNum field.
     */
    public String getPhoneNum(){
        return mPhoneNum;
    }

    /**
     * Sets the FaxPhoneNum field.
     *
     * @param pFaxPhoneNum
     *  String to use to update the field.
     */
    public void setFaxPhoneNum(String pFaxPhoneNum){
        this.mFaxPhoneNum = pFaxPhoneNum;
        setDirty(true);
    }
    /**
     * Retrieves the FaxPhoneNum field.
     *
     * @return
     *  String containing the FaxPhoneNum field.
     */
    public String getFaxPhoneNum(){
        return mFaxPhoneNum;
    }

    /**
     * Sets the ReturnRequestId field.
     *
     * @param pReturnRequestId
     *  int to use to update the field.
     */
    public void setReturnRequestId(int pReturnRequestId){
        this.mReturnRequestId = pReturnRequestId;
        setDirty(true);
    }
    /**
     * Retrieves the ReturnRequestId field.
     *
     * @return
     *  int containing the ReturnRequestId field.
     */
    public int getReturnRequestId(){
        return mReturnRequestId;
    }

    /**
     * Sets the ManifestItemId field.
     *
     * @param pManifestItemId
     *  int to use to update the field.
     */
    public void setManifestItemId(int pManifestItemId){
        this.mManifestItemId = pManifestItemId;
        setDirty(true);
    }
    /**
     * Retrieves the ManifestItemId field.
     *
     * @return
     *  int containing the ManifestItemId field.
     */
    public int getManifestItemId(){
        return mManifestItemId;
    }


}
