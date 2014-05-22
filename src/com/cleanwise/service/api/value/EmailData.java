
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        EmailData
 * Description:  This is a ValueObject class wrapping the database table CLW_EMAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.EmailDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>EmailData</code> is a ValueObject class wrapping of the database table CLW_EMAIL.
 */
public class EmailData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -9193663064640827311L;
    private int mEmailId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER
    private int mUserId;// SQL type:NUMBER
    private String mShortDesc;// SQL type:VARCHAR2
    private String mEmailTypeCd;// SQL type:VARCHAR2, not null
    private String mEmailStatusCd;// SQL type:VARCHAR2, not null
    private String mEmailAddress;// SQL type:VARCHAR2
    private boolean mPrimaryInd;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mContactId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public EmailData ()
    {
        mShortDesc = "";
        mEmailTypeCd = "";
        mEmailStatusCd = "";
        mEmailAddress = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public EmailData(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, String parm7, boolean parm8, Date parm9, String parm10, Date parm11, String parm12, int parm13)
    {
        mEmailId = parm1;
        mBusEntityId = parm2;
        mUserId = parm3;
        mShortDesc = parm4;
        mEmailTypeCd = parm5;
        mEmailStatusCd = parm6;
        mEmailAddress = parm7;
        mPrimaryInd = parm8;
        mAddDate = parm9;
        mAddBy = parm10;
        mModDate = parm11;
        mModBy = parm12;
        mContactId = parm13;
        
    }

    /**
     * Creates a new EmailData
     *
     * @return
     *  Newly initialized EmailData object.
     */
    public static EmailData createValue ()
    {
        EmailData valueData = new EmailData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this EmailData object
     */
    public String toString()
    {
        return "[" + "EmailId=" + mEmailId + ", BusEntityId=" + mBusEntityId + ", UserId=" + mUserId + ", ShortDesc=" + mShortDesc + ", EmailTypeCd=" + mEmailTypeCd + ", EmailStatusCd=" + mEmailStatusCd + ", EmailAddress=" + mEmailAddress + ", PrimaryInd=" + mPrimaryInd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", ContactId=" + mContactId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Email");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mEmailId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("EmailTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mEmailTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("EmailStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mEmailStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("EmailAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mEmailAddress)));
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
    * creates a clone of this object, the EmailId field is not cloned.
    *
    * @return EmailData object
    */
    public Object clone(){
        EmailData myClone = new EmailData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mUserId = mUserId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mEmailTypeCd = mEmailTypeCd;
        
        myClone.mEmailStatusCd = mEmailStatusCd;
        
        myClone.mEmailAddress = mEmailAddress;
        
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

        if (EmailDataAccess.EMAIL_ID.equals(pFieldName)) {
            return getEmailId();
        } else if (EmailDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (EmailDataAccess.USER_ID.equals(pFieldName)) {
            return getUserId();
        } else if (EmailDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (EmailDataAccess.EMAIL_TYPE_CD.equals(pFieldName)) {
            return getEmailTypeCd();
        } else if (EmailDataAccess.EMAIL_STATUS_CD.equals(pFieldName)) {
            return getEmailStatusCd();
        } else if (EmailDataAccess.EMAIL_ADDRESS.equals(pFieldName)) {
            return getEmailAddress();
        } else if (EmailDataAccess.PRIMARY_IND.equals(pFieldName)) {
            return getPrimaryInd();
        } else if (EmailDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (EmailDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (EmailDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (EmailDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (EmailDataAccess.CONTACT_ID.equals(pFieldName)) {
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
        return EmailDataAccess.CLW_EMAIL;
    }

    
    /**
     * Sets the EmailId field. This field is required to be set in the database.
     *
     * @param pEmailId
     *  int to use to update the field.
     */
    public void setEmailId(int pEmailId){
        this.mEmailId = pEmailId;
        setDirty(true);
    }
    /**
     * Retrieves the EmailId field.
     *
     * @return
     *  int containing the EmailId field.
     */
    public int getEmailId(){
        return mEmailId;
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
     * Sets the EmailTypeCd field. This field is required to be set in the database.
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
     * Sets the EmailStatusCd field. This field is required to be set in the database.
     *
     * @param pEmailStatusCd
     *  String to use to update the field.
     */
    public void setEmailStatusCd(String pEmailStatusCd){
        this.mEmailStatusCd = pEmailStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the EmailStatusCd field.
     *
     * @return
     *  String containing the EmailStatusCd field.
     */
    public String getEmailStatusCd(){
        return mEmailStatusCd;
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
