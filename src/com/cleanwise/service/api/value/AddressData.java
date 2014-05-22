
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AddressData
 * Description:  This is a ValueObject class wrapping the database table CLW_ADDRESS.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.AddressDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>AddressData</code> is a ValueObject class wrapping of the database table CLW_ADDRESS.
 */
public class AddressData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -7774024263382877637L;
    private int mAddressId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER
    private int mUserId;// SQL type:NUMBER
    private String mName1;// SQL type:VARCHAR2
    private String mName2;// SQL type:VARCHAR2
    private String mAddress1;// SQL type:VARCHAR2
    private String mAddress2;// SQL type:VARCHAR2
    private String mAddress3;// SQL type:VARCHAR2
    private String mAddress4;// SQL type:VARCHAR2
    private String mCity;// SQL type:VARCHAR2
    private String mStateProvinceCd;// SQL type:VARCHAR2
    private String mCountryCd;// SQL type:VARCHAR2
    private String mCountyCd;// SQL type:VARCHAR2
    private String mPostalCode;// SQL type:VARCHAR2
    private String mAddressStatusCd;// SQL type:VARCHAR2, not null
    private String mAddressTypeCd;// SQL type:VARCHAR2, not null
    private boolean mPrimaryInd;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public AddressData ()
    {
        mName1 = "";
        mName2 = "";
        mAddress1 = "";
        mAddress2 = "";
        mAddress3 = "";
        mAddress4 = "";
        mCity = "";
        mStateProvinceCd = "";
        mCountryCd = "";
        mCountyCd = "";
        mPostalCode = "";
        mAddressStatusCd = "";
        mAddressTypeCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public AddressData(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13, String parm14, String parm15, String parm16, boolean parm17, Date parm18, String parm19, Date parm20, String parm21)
    {
        mAddressId = parm1;
        mBusEntityId = parm2;
        mUserId = parm3;
        mName1 = parm4;
        mName2 = parm5;
        mAddress1 = parm6;
        mAddress2 = parm7;
        mAddress3 = parm8;
        mAddress4 = parm9;
        mCity = parm10;
        mStateProvinceCd = parm11;
        mCountryCd = parm12;
        mCountyCd = parm13;
        mPostalCode = parm14;
        mAddressStatusCd = parm15;
        mAddressTypeCd = parm16;
        mPrimaryInd = parm17;
        mAddDate = parm18;
        mAddBy = parm19;
        mModDate = parm20;
        mModBy = parm21;
        
    }

    /**
     * Creates a new AddressData
     *
     * @return
     *  Newly initialized AddressData object.
     */
    public static AddressData createValue ()
    {
        AddressData valueData = new AddressData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AddressData object
     */
    public String toString()
    {
        return "[" + "AddressId=" + mAddressId + ", BusEntityId=" + mBusEntityId + ", UserId=" + mUserId + ", Name1=" + mName1 + ", Name2=" + mName2 + ", Address1=" + mAddress1 + ", Address2=" + mAddress2 + ", Address3=" + mAddress3 + ", Address4=" + mAddress4 + ", City=" + mCity + ", StateProvinceCd=" + mStateProvinceCd + ", CountryCd=" + mCountryCd + ", CountyCd=" + mCountyCd + ", PostalCode=" + mPostalCode + ", AddressStatusCd=" + mAddressStatusCd + ", AddressTypeCd=" + mAddressTypeCd + ", PrimaryInd=" + mPrimaryInd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.getDocumentElement();
        
        Element node;

        root.setAttribute("Id", String.valueOf(mAddressId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node =  doc.createElement("Name1");
        node.appendChild(doc.createTextNode(String.valueOf(mName1)));
        root.appendChild(node);

        node =  doc.createElement("Name2");
        node.appendChild(doc.createTextNode(String.valueOf(mName2)));
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

        node =  doc.createElement("AddressStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mAddressStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("AddressTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mAddressTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("PrimaryInd");
        node.appendChild(doc.createTextNode(String.valueOf(mPrimaryInd)));
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

        return root;
    }

    /**
    * creates a clone of this object, the AddressId field is not cloned.
    *
    * @return AddressData object
    */
    public Object clone(){
        AddressData myClone = new AddressData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mUserId = mUserId;
        
        myClone.mName1 = mName1;
        
        myClone.mName2 = mName2;
        
        myClone.mAddress1 = mAddress1;
        
        myClone.mAddress2 = mAddress2;
        
        myClone.mAddress3 = mAddress3;
        
        myClone.mAddress4 = mAddress4;
        
        myClone.mCity = mCity;
        
        myClone.mStateProvinceCd = mStateProvinceCd;
        
        myClone.mCountryCd = mCountryCd;
        
        myClone.mCountyCd = mCountyCd;
        
        myClone.mPostalCode = mPostalCode;
        
        myClone.mAddressStatusCd = mAddressStatusCd;
        
        myClone.mAddressTypeCd = mAddressTypeCd;
        
        myClone.mPrimaryInd = mPrimaryInd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (AddressDataAccess.ADDRESS_ID.equals(pFieldName)) {
            return getAddressId();
        } else if (AddressDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (AddressDataAccess.USER_ID.equals(pFieldName)) {
            return getUserId();
        } else if (AddressDataAccess.NAME1.equals(pFieldName)) {
            return getName1();
        } else if (AddressDataAccess.NAME2.equals(pFieldName)) {
            return getName2();
        } else if (AddressDataAccess.ADDRESS1.equals(pFieldName)) {
            return getAddress1();
        } else if (AddressDataAccess.ADDRESS2.equals(pFieldName)) {
            return getAddress2();
        } else if (AddressDataAccess.ADDRESS3.equals(pFieldName)) {
            return getAddress3();
        } else if (AddressDataAccess.ADDRESS4.equals(pFieldName)) {
            return getAddress4();
        } else if (AddressDataAccess.CITY.equals(pFieldName)) {
            return getCity();
        } else if (AddressDataAccess.STATE_PROVINCE_CD.equals(pFieldName)) {
            return getStateProvinceCd();
        } else if (AddressDataAccess.COUNTRY_CD.equals(pFieldName)) {
            return getCountryCd();
        } else if (AddressDataAccess.COUNTY_CD.equals(pFieldName)) {
            return getCountyCd();
        } else if (AddressDataAccess.POSTAL_CODE.equals(pFieldName)) {
            return getPostalCode();
        } else if (AddressDataAccess.ADDRESS_STATUS_CD.equals(pFieldName)) {
            return getAddressStatusCd();
        } else if (AddressDataAccess.ADDRESS_TYPE_CD.equals(pFieldName)) {
            return getAddressTypeCd();
        } else if (AddressDataAccess.PRIMARY_IND.equals(pFieldName)) {
            return getPrimaryInd();
        } else if (AddressDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (AddressDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (AddressDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (AddressDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
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
        return AddressDataAccess.CLW_ADDRESS;
    }

    
    /**
     * Sets the AddressId field. This field is required to be set in the database.
     *
     * @param pAddressId
     *  int to use to update the field.
     */
    public void setAddressId(int pAddressId){
        this.mAddressId = pAddressId;
        setDirty(true);
    }
    /**
     * Retrieves the AddressId field.
     *
     * @return
     *  int containing the AddressId field.
     */
    public int getAddressId(){
        return mAddressId;
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
     * Sets the UserId field.
     *
     * @param pUserId
     *  int to use to update the field.
     */
    public void setUserId(int pUserId){
        this.mUserId = pUserId;
        setDirty(true);
    }
    /**
     * Retrieves the UserId field.
     *
     * @return
     *  int containing the UserId field.
     */
    public int getUserId(){
        return mUserId;
    }

    /**
     * Sets the Name1 field.
     *
     * @param pName1
     *  String to use to update the field.
     */
    public void setName1(String pName1){
        this.mName1 = pName1;
        setDirty(true);
    }
    /**
     * Retrieves the Name1 field.
     *
     * @return
     *  String containing the Name1 field.
     */
    public String getName1(){
        return mName1;
    }

    /**
     * Sets the Name2 field.
     *
     * @param pName2
     *  String to use to update the field.
     */
    public void setName2(String pName2){
        this.mName2 = pName2;
        setDirty(true);
    }
    /**
     * Retrieves the Name2 field.
     *
     * @return
     *  String containing the Name2 field.
     */
    public String getName2(){
        return mName2;
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
     * Sets the AddressStatusCd field. This field is required to be set in the database.
     *
     * @param pAddressStatusCd
     *  String to use to update the field.
     */
    public void setAddressStatusCd(String pAddressStatusCd){
        this.mAddressStatusCd = pAddressStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the AddressStatusCd field.
     *
     * @return
     *  String containing the AddressStatusCd field.
     */
    public String getAddressStatusCd(){
        return mAddressStatusCd;
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
     * Sets the PrimaryInd field.
     *
     * @param pPrimaryInd
     *  boolean to use to update the field.
     */
    public void setPrimaryInd(boolean pPrimaryInd){
        this.mPrimaryInd = pPrimaryInd;
        setDirty(true);
    }
    /**
     * Retrieves the PrimaryInd field.
     *
     * @return
     *  boolean containing the PrimaryInd field.
     */
    public boolean getPrimaryInd(){
        return mPrimaryInd;
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


}
