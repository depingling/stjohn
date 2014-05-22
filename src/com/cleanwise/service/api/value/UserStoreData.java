
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UserStoreData
 * Description:  This is a ValueObject class wrapping the database table ESW_USER_STORE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.UserStoreDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>UserStoreData</code> is a ValueObject class wrapping of the database table ESW_USER_STORE.
 */
public class UserStoreData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 8116864918440830884L;
    private int mUserStoreId;// SQL type:NUMBER, not null
    private int mAllUserId;// SQL type:NUMBER, not null
    private int mAllStoreId;// SQL type:NUMBER, not null
    private Date mLastLoginDate;// SQL type:DATE
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mLocaleCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public UserStoreData ()
    {
        mAddBy = "";
        mModBy = "";
        mLocaleCd = "";
    }

    /**
     * Constructor.
     */
    public UserStoreData(int parm1, int parm2, int parm3, Date parm4, Date parm5, String parm6, Date parm7, String parm8, String parm9)
    {
        mUserStoreId = parm1;
        mAllUserId = parm2;
        mAllStoreId = parm3;
        mLastLoginDate = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        mLocaleCd = parm9;
        
    }

    /**
     * Creates a new UserStoreData
     *
     * @return
     *  Newly initialized UserStoreData object.
     */
    public static UserStoreData createValue ()
    {
        UserStoreData valueData = new UserStoreData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UserStoreData object
     */
    public String toString()
    {
        return "[" + "UserStoreId=" + mUserStoreId + ", AllUserId=" + mAllUserId + ", AllStoreId=" + mAllStoreId + ", LastLoginDate=" + mLastLoginDate + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", LocaleCd=" + mLocaleCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("UserStore");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mUserStoreId));

        node =  doc.createElement("AllUserId");
        node.appendChild(doc.createTextNode(String.valueOf(mAllUserId)));
        root.appendChild(node);

        node =  doc.createElement("AllStoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mAllStoreId)));
        root.appendChild(node);

        node =  doc.createElement("LastLoginDate");
        node.appendChild(doc.createTextNode(String.valueOf(mLastLoginDate)));
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

        node =  doc.createElement("LocaleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mLocaleCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the UserStoreId field is not cloned.
    *
    * @return UserStoreData object
    */
    public Object clone(){
        UserStoreData myClone = new UserStoreData();
        
        myClone.mAllUserId = mAllUserId;
        
        myClone.mAllStoreId = mAllStoreId;
        
        if(mLastLoginDate != null){
                myClone.mLastLoginDate = (Date) mLastLoginDate.clone();
        }
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mLocaleCd = mLocaleCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (UserStoreDataAccess.USER_STORE_ID.equals(pFieldName)) {
            return getUserStoreId();
        } else if (UserStoreDataAccess.ALL_USER_ID.equals(pFieldName)) {
            return getAllUserId();
        } else if (UserStoreDataAccess.ALL_STORE_ID.equals(pFieldName)) {
            return getAllStoreId();
        } else if (UserStoreDataAccess.LAST_LOGIN_DATE.equals(pFieldName)) {
            return getLastLoginDate();
        } else if (UserStoreDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (UserStoreDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (UserStoreDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (UserStoreDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (UserStoreDataAccess.LOCALE_CD.equals(pFieldName)) {
            return getLocaleCd();
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
        return UserStoreDataAccess.ESW_USER_STORE;
    }

    
    /**
     * Sets the UserStoreId field. This field is required to be set in the database.
     *
     * @param pUserStoreId
     *  int to use to update the field.
     */
    public void setUserStoreId(int pUserStoreId){
        this.mUserStoreId = pUserStoreId;
        setDirty(true);
    }
    /**
     * Retrieves the UserStoreId field.
     *
     * @return
     *  int containing the UserStoreId field.
     */
    public int getUserStoreId(){
        return mUserStoreId;
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
     * Sets the AllStoreId field. This field is required to be set in the database.
     *
     * @param pAllStoreId
     *  int to use to update the field.
     */
    public void setAllStoreId(int pAllStoreId){
        this.mAllStoreId = pAllStoreId;
        setDirty(true);
    }
    /**
     * Retrieves the AllStoreId field.
     *
     * @return
     *  int containing the AllStoreId field.
     */
    public int getAllStoreId(){
        return mAllStoreId;
    }

    /**
     * Sets the LastLoginDate field.
     *
     * @param pLastLoginDate
     *  Date to use to update the field.
     */
    public void setLastLoginDate(Date pLastLoginDate){
        this.mLastLoginDate = pLastLoginDate;
        setDirty(true);
    }
    /**
     * Retrieves the LastLoginDate field.
     *
     * @return
     *  Date containing the LastLoginDate field.
     */
    public Date getLastLoginDate(){
        return mLastLoginDate;
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
     * Sets the LocaleCd field.
     *
     * @param pLocaleCd
     *  String to use to update the field.
     */
    public void setLocaleCd(String pLocaleCd){
        this.mLocaleCd = pLocaleCd;
        setDirty(true);
    }
    /**
     * Retrieves the LocaleCd field.
     *
     * @return
     *  String containing the LocaleCd field.
     */
    public String getLocaleCd(){
        return mLocaleCd;
    }


}
