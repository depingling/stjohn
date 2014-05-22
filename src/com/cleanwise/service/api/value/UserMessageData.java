
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UserMessageData
 * Description:  This is a ValueObject class wrapping the database table CLW_USER_MESSAGE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.UserMessageDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>UserMessageData</code> is a ValueObject class wrapping of the database table CLW_USER_MESSAGE.
 */
public class UserMessageData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 3331004386957082525L;
    private int mUserMessageId;// SQL type:NUMBER, not null
    private int mUserId;// SQL type:NUMBER, not null
    private int mStoreMessageId;// SQL type:NUMBER, not null
    private Date mReadDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null

    /**
     * Constructor.
     */
    public UserMessageData ()
    {
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public UserMessageData(int parm1, int parm2, int parm3, Date parm4, String parm5, Date parm6, String parm7, Date parm8)
    {
        mUserMessageId = parm1;
        mUserId = parm2;
        mStoreMessageId = parm3;
        mReadDate = parm4;
        mAddBy = parm5;
        mAddDate = parm6;
        mModBy = parm7;
        mModDate = parm8;
        
    }

    /**
     * Creates a new UserMessageData
     *
     * @return
     *  Newly initialized UserMessageData object.
     */
    public static UserMessageData createValue ()
    {
        UserMessageData valueData = new UserMessageData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UserMessageData object
     */
    public String toString()
    {
        return "[" + "UserMessageId=" + mUserMessageId + ", UserId=" + mUserId + ", StoreMessageId=" + mStoreMessageId + ", ReadDate=" + mReadDate + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("UserMessage");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mUserMessageId));

        node = doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node = doc.createElement("StoreMessageId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreMessageId)));
        root.appendChild(node);

        node = doc.createElement("ReadDate");
        node.appendChild(doc.createTextNode(String.valueOf(mReadDate)));
        root.appendChild(node);

        node = doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node = doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node = doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node = doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the UserMessageId field is not cloned.
    *
    * @return UserMessageData object
    */
    public Object clone(){
        UserMessageData myClone = new UserMessageData();
        
        myClone.mUserId = mUserId;
        
        myClone.mStoreMessageId = mStoreMessageId;
        
        if(mReadDate != null){
                myClone.mReadDate = (Date) mReadDate.clone();
        }
        
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

        if (UserMessageDataAccess.USER_MESSAGE_ID.equals(pFieldName)) {
            return getUserMessageId();
        } else if (UserMessageDataAccess.USER_ID.equals(pFieldName)) {
            return getUserId();
        } else if (UserMessageDataAccess.STORE_MESSAGE_ID.equals(pFieldName)) {
            return getStoreMessageId();
        } else if (UserMessageDataAccess.READ_DATE.equals(pFieldName)) {
            return getReadDate();
        } else if (UserMessageDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (UserMessageDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (UserMessageDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (UserMessageDataAccess.MOD_DATE.equals(pFieldName)) {
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
        return UserMessageDataAccess.CLW_USER_MESSAGE;
    }

    
    /**
     * Sets the UserMessageId field. This field is required to be set in the database.
     *
     * @param pUserMessageId
     *  int to use to update the field.
     */
    public void setUserMessageId(int pUserMessageId){
        this.mUserMessageId = pUserMessageId;
        setDirty(true);
    }
    /**
     * Retrieves the UserMessageId field.
     *
     * @return
     *  int containing the UserMessageId field.
     */
    public int getUserMessageId(){
        return mUserMessageId;
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
     * Sets the StoreMessageId field. This field is required to be set in the database.
     *
     * @param pStoreMessageId
     *  int to use to update the field.
     */
    public void setStoreMessageId(int pStoreMessageId){
        this.mStoreMessageId = pStoreMessageId;
        setDirty(true);
    }
    /**
     * Retrieves the StoreMessageId field.
     *
     * @return
     *  int containing the StoreMessageId field.
     */
    public int getStoreMessageId(){
        return mStoreMessageId;
    }

    /**
     * Sets the ReadDate field. This field is required to be set in the database.
     *
     * @param pReadDate
     *  Date to use to update the field.
     */
    public void setReadDate(Date pReadDate){
        this.mReadDate = pReadDate;
        setDirty(true);
    }
    /**
     * Retrieves the ReadDate field.
     *
     * @return
     *  Date containing the ReadDate field.
     */
    public Date getReadDate(){
        return mReadDate;
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


}
