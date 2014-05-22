
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PhoneData
 * Description:  This is a ValueObject class wrapping the database table CLW_PHONE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.PhoneDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>PhoneData</code> is a ValueObject class wrapping of the database table CLW_PHONE.
 */
public class PhoneData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 76844096298511910L;
    private int mPhoneId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER
    private int mUserId;// SQL type:NUMBER
    private String mPhoneCountryCd;// SQL type:VARCHAR2
    private String mPhoneAreaCode;// SQL type:VARCHAR2
    private String mPhoneNum;// SQL type:VARCHAR2
    private String mShortDesc;// SQL type:VARCHAR2
    private String mPhoneTypeCd;// SQL type:VARCHAR2, not null
    private String mPhoneStatusCd;// SQL type:VARCHAR2, not null
    private boolean mPrimaryInd;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mContactId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public PhoneData ()
    {
        mPhoneCountryCd = "";
        mPhoneAreaCode = "";
        mPhoneNum = "";
        mShortDesc = "";
        mPhoneTypeCd = "";
        mPhoneStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public PhoneData(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, boolean parm10, Date parm11, String parm12, Date parm13, String parm14, int parm15)
    {
        mPhoneId = parm1;
        mBusEntityId = parm2;
        mUserId = parm3;
        mPhoneCountryCd = parm4;
        mPhoneAreaCode = parm5;
        mPhoneNum = parm6;
        mShortDesc = parm7;
        mPhoneTypeCd = parm8;
        mPhoneStatusCd = parm9;
        mPrimaryInd = parm10;
        mAddDate = parm11;
        mAddBy = parm12;
        mModDate = parm13;
        mModBy = parm14;
        mContactId = parm15;
        
    }

    /**
     * Creates a new PhoneData
     *
     * @return
     *  Newly initialized PhoneData object.
     */
    public static PhoneData createValue ()
    {
        PhoneData valueData = new PhoneData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PhoneData object
     */
    public String toString()
    {
        return "[" + "PhoneId=" + mPhoneId + ", BusEntityId=" + mBusEntityId + ", UserId=" + mUserId + ", PhoneCountryCd=" + mPhoneCountryCd + ", PhoneAreaCode=" + mPhoneAreaCode + ", PhoneNum=" + mPhoneNum + ", ShortDesc=" + mShortDesc + ", PhoneTypeCd=" + mPhoneTypeCd + ", PhoneStatusCd=" + mPhoneStatusCd + ", PrimaryInd=" + mPrimaryInd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", ContactId=" + mContactId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Phone");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mPhoneId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node =  doc.createElement("PhoneCountryCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPhoneCountryCd)));
        root.appendChild(node);

        node =  doc.createElement("PhoneAreaCode");
        node.appendChild(doc.createTextNode(String.valueOf(mPhoneAreaCode)));
        root.appendChild(node);

        node =  doc.createElement("PhoneNum");
        node.appendChild(doc.createTextNode(String.valueOf(mPhoneNum)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("PhoneTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPhoneTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("PhoneStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mPhoneStatusCd)));
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

        node =  doc.createElement("ContactId");
        node.appendChild(doc.createTextNode(String.valueOf(mContactId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the PhoneId field is not cloned.
    *
    * @return PhoneData object
    */
    public Object clone(){
        PhoneData myClone = new PhoneData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mUserId = mUserId;
        
        myClone.mPhoneCountryCd = mPhoneCountryCd;
        
        myClone.mPhoneAreaCode = mPhoneAreaCode;
        
        myClone.mPhoneNum = mPhoneNum;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mPhoneTypeCd = mPhoneTypeCd;
        
        myClone.mPhoneStatusCd = mPhoneStatusCd;
        
        myClone.mPrimaryInd = mPrimaryInd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mContactId = mContactId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (PhoneDataAccess.PHONE_ID.equals(pFieldName)) {
            return getPhoneId();
        } else if (PhoneDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (PhoneDataAccess.USER_ID.equals(pFieldName)) {
            return getUserId();
        } else if (PhoneDataAccess.PHONE_COUNTRY_CD.equals(pFieldName)) {
            return getPhoneCountryCd();
        } else if (PhoneDataAccess.PHONE_AREA_CODE.equals(pFieldName)) {
            return getPhoneAreaCode();
        } else if (PhoneDataAccess.PHONE_NUM.equals(pFieldName)) {
            return getPhoneNum();
        } else if (PhoneDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (PhoneDataAccess.PHONE_TYPE_CD.equals(pFieldName)) {
            return getPhoneTypeCd();
        } else if (PhoneDataAccess.PHONE_STATUS_CD.equals(pFieldName)) {
            return getPhoneStatusCd();
        } else if (PhoneDataAccess.PRIMARY_IND.equals(pFieldName)) {
            return getPrimaryInd();
        } else if (PhoneDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (PhoneDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (PhoneDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (PhoneDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (PhoneDataAccess.CONTACT_ID.equals(pFieldName)) {
            return getContactId();
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
        return PhoneDataAccess.CLW_PHONE;
    }

    
    /**
     * Sets the PhoneId field. This field is required to be set in the database.
     *
     * @param pPhoneId
     *  int to use to update the field.
     */
    public void setPhoneId(int pPhoneId){
        this.mPhoneId = pPhoneId;
        setDirty(true);
    }
    /**
     * Retrieves the PhoneId field.
     *
     * @return
     *  int containing the PhoneId field.
     */
    public int getPhoneId(){
        return mPhoneId;
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
     * Sets the PhoneCountryCd field.
     *
     * @param pPhoneCountryCd
     *  String to use to update the field.
     */
    public void setPhoneCountryCd(String pPhoneCountryCd){
        this.mPhoneCountryCd = pPhoneCountryCd;
        setDirty(true);
    }
    /**
     * Retrieves the PhoneCountryCd field.
     *
     * @return
     *  String containing the PhoneCountryCd field.
     */
    public String getPhoneCountryCd(){
        return mPhoneCountryCd;
    }

    /**
     * Sets the PhoneAreaCode field.
     *
     * @param pPhoneAreaCode
     *  String to use to update the field.
     */
    public void setPhoneAreaCode(String pPhoneAreaCode){
        this.mPhoneAreaCode = pPhoneAreaCode;
        setDirty(true);
    }
    /**
     * Retrieves the PhoneAreaCode field.
     *
     * @return
     *  String containing the PhoneAreaCode field.
     */
    public String getPhoneAreaCode(){
        return mPhoneAreaCode;
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
     * Sets the PhoneTypeCd field. This field is required to be set in the database.
     *
     * @param pPhoneTypeCd
     *  String to use to update the field.
     */
    public void setPhoneTypeCd(String pPhoneTypeCd){
        this.mPhoneTypeCd = pPhoneTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the PhoneTypeCd field.
     *
     * @return
     *  String containing the PhoneTypeCd field.
     */
    public String getPhoneTypeCd(){
        return mPhoneTypeCd;
    }

    /**
     * Sets the PhoneStatusCd field. This field is required to be set in the database.
     *
     * @param pPhoneStatusCd
     *  String to use to update the field.
     */
    public void setPhoneStatusCd(String pPhoneStatusCd){
        this.mPhoneStatusCd = pPhoneStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the PhoneStatusCd field.
     *
     * @return
     *  String containing the PhoneStatusCd field.
     */
    public String getPhoneStatusCd(){
        return mPhoneStatusCd;
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

    /**
     * Sets the ContactId field.
     *
     * @param pContactId
     *  int to use to update the field.
     */
    public void setContactId(int pContactId){
        this.mContactId = pContactId;
        setDirty(true);
    }
    /**
     * Retrieves the ContactId field.
     *
     * @return
     *  int containing the ContactId field.
     */
    public int getContactId(){
        return mContactId;
    }


}
