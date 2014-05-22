
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UserAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_USER_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.UserAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>UserAssocData</code> is a ValueObject class wrapping of the database table CLW_USER_ASSOC.
 */
public class UserAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -8354316348277710756L;
    private int mUserAssocId;// SQL type:NUMBER, not null
    private int mUserId;// SQL type:NUMBER
    private int mBusEntityId;// SQL type:NUMBER
    private String mUserAssocCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mLastUserVisitDateTime;// SQL type:DATE

    /**
     * Constructor.
     */
    public UserAssocData ()
    {
        mUserAssocCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public UserAssocData(int parm1, int parm2, int parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8, Date parm9)
    {
        mUserAssocId = parm1;
        mUserId = parm2;
        mBusEntityId = parm3;
        mUserAssocCd = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        mLastUserVisitDateTime = parm9;
        
    }

    /**
     * Creates a new UserAssocData
     *
     * @return
     *  Newly initialized UserAssocData object.
     */
    public static UserAssocData createValue ()
    {
        UserAssocData valueData = new UserAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UserAssocData object
     */
    public String toString()
    {
        return "[" + "UserAssocId=" + mUserAssocId + ", UserId=" + mUserId + ", BusEntityId=" + mBusEntityId + ", UserAssocCd=" + mUserAssocCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", LastUserVisitDateTime=" + mLastUserVisitDateTime + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("UserAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mUserAssocId));

        node =  doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("UserAssocCd");
        node.appendChild(doc.createTextNode(String.valueOf(mUserAssocCd)));
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

        node =  doc.createElement("LastUserVisitDateTime");
        node.appendChild(doc.createTextNode(String.valueOf(mLastUserVisitDateTime)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the UserAssocId field is not cloned.
    *
    * @return UserAssocData object
    */
    public Object clone(){
        UserAssocData myClone = new UserAssocData();
        
        myClone.mUserId = mUserId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mUserAssocCd = mUserAssocCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mLastUserVisitDateTime != null){
                myClone.mLastUserVisitDateTime = (Date) mLastUserVisitDateTime.clone();
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

        if (UserAssocDataAccess.USER_ASSOC_ID.equals(pFieldName)) {
            return getUserAssocId();
        } else if (UserAssocDataAccess.USER_ID.equals(pFieldName)) {
            return getUserId();
        } else if (UserAssocDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (UserAssocDataAccess.USER_ASSOC_CD.equals(pFieldName)) {
            return getUserAssocCd();
        } else if (UserAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (UserAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (UserAssocDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (UserAssocDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (UserAssocDataAccess.LAST_USER_VISIT_DATE_TIME.equals(pFieldName)) {
            return getLastUserVisitDateTime();
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
        return UserAssocDataAccess.CLW_USER_ASSOC;
    }

    
    /**
     * Sets the UserAssocId field. This field is required to be set in the database.
     *
     * @param pUserAssocId
     *  int to use to update the field.
     */
    public void setUserAssocId(int pUserAssocId){
        this.mUserAssocId = pUserAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the UserAssocId field.
     *
     * @return
     *  int containing the UserAssocId field.
     */
    public int getUserAssocId(){
        return mUserAssocId;
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
     * Sets the UserAssocCd field. This field is required to be set in the database.
     *
     * @param pUserAssocCd
     *  String to use to update the field.
     */
    public void setUserAssocCd(String pUserAssocCd){
        this.mUserAssocCd = pUserAssocCd;
        setDirty(true);
    }
    /**
     * Retrieves the UserAssocCd field.
     *
     * @return
     *  String containing the UserAssocCd field.
     */
    public String getUserAssocCd(){
        return mUserAssocCd;
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
     * Sets the LastUserVisitDateTime field.
     *
     * @param pLastUserVisitDateTime
     *  Date to use to update the field.
     */
    public void setLastUserVisitDateTime(Date pLastUserVisitDateTime){
        this.mLastUserVisitDateTime = pLastUserVisitDateTime;
        setDirty(true);
    }
    /**
     * Retrieves the LastUserVisitDateTime field.
     *
     * @return
     *  Date containing the LastUserVisitDateTime field.
     */
    public Date getLastUserVisitDateTime(){
        return mLastUserVisitDateTime;
    }


}
