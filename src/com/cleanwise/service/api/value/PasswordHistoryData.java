
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PasswordHistoryData
 * Description:  This is a ValueObject class wrapping the database table CLW_PASSWORD_HISTORY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.PasswordHistoryDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>PasswordHistoryData</code> is a ValueObject class wrapping of the database table CLW_PASSWORD_HISTORY.
 */
public class PasswordHistoryData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mPasswordHistoryId;// SQL type:NUMBER, not null
    private int mUserId;// SQL type:NUMBER, not null
    private String mPassword;// SQL type:VARCHAR2
    private String mNeedInitialReset;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public PasswordHistoryData ()
    {
        mPassword = "";
        mNeedInitialReset = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public PasswordHistoryData(int parm1, int parm2, String parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8)
    {
        mPasswordHistoryId = parm1;
        mUserId = parm2;
        mPassword = parm3;
        mNeedInitialReset = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        
    }

    /**
     * Creates a new PasswordHistoryData
     *
     * @return
     *  Newly initialized PasswordHistoryData object.
     */
    public static PasswordHistoryData createValue ()
    {
        PasswordHistoryData valueData = new PasswordHistoryData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PasswordHistoryData object
     */
    public String toString()
    {
        return "[" + "PasswordHistoryId=" + mPasswordHistoryId + ", UserId=" + mUserId + ", Password=" + mPassword + ", NeedInitialReset=" + mNeedInitialReset + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("PasswordHistory");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mPasswordHistoryId));

        node =  doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node =  doc.createElement("Password");
        node.appendChild(doc.createTextNode(String.valueOf(mPassword)));
        root.appendChild(node);

        node =  doc.createElement("NeedInitialReset");
        node.appendChild(doc.createTextNode(String.valueOf(mNeedInitialReset)));
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
    * creates a clone of this object, the PasswordHistoryId field is not cloned.
    *
    * @return PasswordHistoryData object
    */
    public Object clone(){
        PasswordHistoryData myClone = new PasswordHistoryData();
        
        myClone.mUserId = mUserId;
        
        myClone.mPassword = mPassword;
        
        myClone.mNeedInitialReset = mNeedInitialReset;
        
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

        if (PasswordHistoryDataAccess.PASSWORD_HISTORY_ID.equals(pFieldName)) {
            return getPasswordHistoryId();
        } else if (PasswordHistoryDataAccess.USER_ID.equals(pFieldName)) {
            return getUserId();
        } else if (PasswordHistoryDataAccess.PASSWORD.equals(pFieldName)) {
            return getPassword();
        } else if (PasswordHistoryDataAccess.NEED_INITIAL_RESET.equals(pFieldName)) {
            return getNeedInitialReset();
        } else if (PasswordHistoryDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (PasswordHistoryDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (PasswordHistoryDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (PasswordHistoryDataAccess.MOD_BY.equals(pFieldName)) {
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
        return PasswordHistoryDataAccess.CLW_PASSWORD_HISTORY;
    }

    
    /**
     * Sets the PasswordHistoryId field. This field is required to be set in the database.
     *
     * @param pPasswordHistoryId
     *  int to use to update the field.
     */
    public void setPasswordHistoryId(int pPasswordHistoryId){
        this.mPasswordHistoryId = pPasswordHistoryId;
        setDirty(true);
    }
    /**
     * Retrieves the PasswordHistoryId field.
     *
     * @return
     *  int containing the PasswordHistoryId field.
     */
    public int getPasswordHistoryId(){
        return mPasswordHistoryId;
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
     * Sets the NeedInitialReset field.
     *
     * @param pNeedInitialReset
     *  String to use to update the field.
     */
    public void setNeedInitialReset(String pNeedInitialReset){
        this.mNeedInitialReset = pNeedInitialReset;
        setDirty(true);
    }
    /**
     * Retrieves the NeedInitialReset field.
     *
     * @return
     *  String containing the NeedInitialReset field.
     */
    public String getNeedInitialReset(){
        return mNeedInitialReset;
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
