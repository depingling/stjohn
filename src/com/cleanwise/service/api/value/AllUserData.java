
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AllUserData
 * Description:  This is a ValueObject class wrapping the database table ESW_ALL_USER.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.AllUserDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>AllUserData</code> is a ValueObject class wrapping of the database table ESW_ALL_USER.
 */
public class AllUserData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 770654028782702017L;
    private int mAllUserId;// SQL type:NUMBER, not null
    private int mUserId;// SQL type:NUMBER, not null
    private String mUserName;// SQL type:VARCHAR2, not null
    private String mFirstName;// SQL type:VARCHAR2
    private String mLastName;// SQL type:VARCHAR2
    private String mPassword;// SQL type:VARCHAR2
    private Date mEffDate;// SQL type:DATE
    private Date mExpDate;// SQL type:DATE
    private String mUserStatusCd;// SQL type:VARCHAR2, not null
    private String mUserTypeCd;// SQL type:VARCHAR2, not null
    private int mDefaultStoreId;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public AllUserData ()
    {
        mUserName = "";
        mFirstName = "";
        mLastName = "";
        mPassword = "";
        mUserStatusCd = "";
        mUserTypeCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public AllUserData(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, Date parm7, Date parm8, String parm9, String parm10, int parm11, Date parm12, String parm13, Date parm14, String parm15)
    {
        mAllUserId = parm1;
        mUserId = parm2;
        mUserName = parm3;
        mFirstName = parm4;
        mLastName = parm5;
        mPassword = parm6;
        mEffDate = parm7;
        mExpDate = parm8;
        mUserStatusCd = parm9;
        mUserTypeCd = parm10;
        mDefaultStoreId = parm11;
        mAddDate = parm12;
        mAddBy = parm13;
        mModDate = parm14;
        mModBy = parm15;
        
    }

    /**
     * Creates a new AllUserData
     *
     * @return
     *  Newly initialized AllUserData object.
     */
    public static AllUserData createValue ()
    {
        AllUserData valueData = new AllUserData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AllUserData object
     */
    public String toString()
    {
        return "[" + "AllUserId=" + mAllUserId + ", UserId=" + mUserId + ", UserName=" + mUserName + ", FirstName=" + mFirstName + ", LastName=" + mLastName + ", Password=" + mPassword + ", EffDate=" + mEffDate + ", ExpDate=" + mExpDate + ", UserStatusCd=" + mUserStatusCd + ", UserTypeCd=" + mUserTypeCd + ", DefaultStoreId=" + mDefaultStoreId + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("AllUser");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mAllUserId));

        node =  doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node =  doc.createElement("UserName");
        node.appendChild(doc.createTextNode(String.valueOf(mUserName)));
        root.appendChild(node);

        node =  doc.createElement("FirstName");
        node.appendChild(doc.createTextNode(String.valueOf(mFirstName)));
        root.appendChild(node);

        node =  doc.createElement("LastName");
        node.appendChild(doc.createTextNode(String.valueOf(mLastName)));
        root.appendChild(node);

        node =  doc.createElement("Password");
        node.appendChild(doc.createTextNode(String.valueOf(mPassword)));
        root.appendChild(node);

        node =  doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
        root.appendChild(node);

        node =  doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
        root.appendChild(node);

        node =  doc.createElement("UserStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mUserStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("UserTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mUserTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("DefaultStoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mDefaultStoreId)));
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
    * creates a clone of this object, the AllUserId field is not cloned.
    *
    * @return AllUserData object
    */
    public Object clone(){
        AllUserData myClone = new AllUserData();
        
        myClone.mUserId = mUserId;
        
        myClone.mUserName = mUserName;
        
        myClone.mFirstName = mFirstName;
        
        myClone.mLastName = mLastName;
        
        myClone.mPassword = mPassword;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
        if(mExpDate != null){
                myClone.mExpDate = (Date) mExpDate.clone();
        }
        
        myClone.mUserStatusCd = mUserStatusCd;
        
        myClone.mUserTypeCd = mUserTypeCd;
        
        myClone.mDefaultStoreId = mDefaultStoreId;
        
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

        if (AllUserDataAccess.ALL_USER_ID.equals(pFieldName)) {
            return getAllUserId();
        } else if (AllUserDataAccess.USER_ID.equals(pFieldName)) {
            return getUserId();
        } else if (AllUserDataAccess.USER_NAME.equals(pFieldName)) {
            return getUserName();
        } else if (AllUserDataAccess.FIRST_NAME.equals(pFieldName)) {
            return getFirstName();
        } else if (AllUserDataAccess.LAST_NAME.equals(pFieldName)) {
            return getLastName();
        } else if (AllUserDataAccess.PASSWORD.equals(pFieldName)) {
            return getPassword();
        } else if (AllUserDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (AllUserDataAccess.EXP_DATE.equals(pFieldName)) {
            return getExpDate();
        } else if (AllUserDataAccess.USER_STATUS_CD.equals(pFieldName)) {
            return getUserStatusCd();
        } else if (AllUserDataAccess.USER_TYPE_CD.equals(pFieldName)) {
            return getUserTypeCd();
        } else if (AllUserDataAccess.DEFAULT_STORE_ID.equals(pFieldName)) {
            return getDefaultStoreId();
        } else if (AllUserDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (AllUserDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (AllUserDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (AllUserDataAccess.MOD_BY.equals(pFieldName)) {
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
        return AllUserDataAccess.ESW_ALL_USER;
    }

    
    /**
     * Sets the AllUserId field. This field is required to be set in the database.
     *
     * @param pAllUserId
     *  int to use to update the field.
     */
    public void setAllUserId(int pAllUserId){
        this.mAllUserId = pAllUserId;
        setDirty(true);
    }
    /**
     * Retrieves the AllUserId field.
     *
     * @return
     *  int containing the AllUserId field.
     */
    public int getAllUserId(){
        return mAllUserId;
    }

    /**
     * Sets the UserId field. This field is required to be set in the database.
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
     * Sets the UserName field. This field is required to be set in the database.
     *
     * @param pUserName
     *  String to use to update the field.
     */
    public void setUserName(String pUserName){
        this.mUserName = pUserName;
        setDirty(true);
    }
    /**
     * Retrieves the UserName field.
     *
     * @return
     *  String containing the UserName field.
     */
    public String getUserName(){
        return mUserName;
    }

    /**
     * Sets the FirstName field.
     *
     * @param pFirstName
     *  String to use to update the field.
     */
    public void setFirstName(String pFirstName){
        this.mFirstName = pFirstName;
        setDirty(true);
    }
    /**
     * Retrieves the FirstName field.
     *
     * @return
     *  String containing the FirstName field.
     */
    public String getFirstName(){
        return mFirstName;
    }

    /**
     * Sets the LastName field.
     *
     * @param pLastName
     *  String to use to update the field.
     */
    public void setLastName(String pLastName){
        this.mLastName = pLastName;
        setDirty(true);
    }
    /**
     * Retrieves the LastName field.
     *
     * @return
     *  String containing the LastName field.
     */
    public String getLastName(){
        return mLastName;
    }

    /**
     * Sets the Password field.
     *
     * @param pPassword
     *  String to use to update the field.
     */
    public void setPassword(String pPassword){
        this.mPassword = pPassword;
        setDirty(true);
    }
    /**
     * Retrieves the Password field.
     *
     * @return
     *  String containing the Password field.
     */
    public String getPassword(){
        return mPassword;
    }

    /**
     * Sets the EffDate field.
     *
     * @param pEffDate
     *  Date to use to update the field.
     */
    public void setEffDate(Date pEffDate){
        this.mEffDate = pEffDate;
        setDirty(true);
    }
    /**
     * Retrieves the EffDate field.
     *
     * @return
     *  Date containing the EffDate field.
     */
    public Date getEffDate(){
        return mEffDate;
    }

    /**
     * Sets the ExpDate field.
     *
     * @param pExpDate
     *  Date to use to update the field.
     */
    public void setExpDate(Date pExpDate){
        this.mExpDate = pExpDate;
        setDirty(true);
    }
    /**
     * Retrieves the ExpDate field.
     *
     * @return
     *  Date containing the ExpDate field.
     */
    public Date getExpDate(){
        return mExpDate;
    }

    /**
     * Sets the UserStatusCd field. This field is required to be set in the database.
     *
     * @param pUserStatusCd
     *  String to use to update the field.
     */
    public void setUserStatusCd(String pUserStatusCd){
        this.mUserStatusCd = pUserStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the UserStatusCd field.
     *
     * @return
     *  String containing the UserStatusCd field.
     */
    public String getUserStatusCd(){
        return mUserStatusCd;
    }

    /**
     * Sets the UserTypeCd field. This field is required to be set in the database.
     *
     * @param pUserTypeCd
     *  String to use to update the field.
     */
    public void setUserTypeCd(String pUserTypeCd){
        this.mUserTypeCd = pUserTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the UserTypeCd field.
     *
     * @return
     *  String containing the UserTypeCd field.
     */
    public String getUserTypeCd(){
        return mUserTypeCd;
    }

    /**
     * Sets the DefaultStoreId field.
     *
     * @param pDefaultStoreId
     *  int to use to update the field.
     */
    public void setDefaultStoreId(int pDefaultStoreId){
        this.mDefaultStoreId = pDefaultStoreId;
        setDirty(true);
    }
    /**
     * Retrieves the DefaultStoreId field.
     *
     * @return
     *  int containing the DefaultStoreId field.
     */
    public int getDefaultStoreId(){
        return mDefaultStoreId;
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
