
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        JanitorClosetData
 * Description:  This is a ValueObject class wrapping the database table CLW_JANITOR_CLOSET.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.JanitorClosetDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>JanitorClosetData</code> is a ValueObject class wrapping of the database table CLW_JANITOR_CLOSET.
 */
public class JanitorClosetData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -2866904966377600189L;
    private int mJanitorClosetId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private int mUserId;// SQL type:NUMBER, not null
    private int mItemId;// SQL type:NUMBER, not null
    private int mOrderId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public JanitorClosetData ()
    {
        mShortDesc = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public JanitorClosetData(int parm1, int parm2, int parm3, int parm4, int parm5, String parm6, Date parm7, String parm8, Date parm9, String parm10)
    {
        mJanitorClosetId = parm1;
        mBusEntityId = parm2;
        mUserId = parm3;
        mItemId = parm4;
        mOrderId = parm5;
        mShortDesc = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        
    }

    /**
     * Creates a new JanitorClosetData
     *
     * @return
     *  Newly initialized JanitorClosetData object.
     */
    public static JanitorClosetData createValue ()
    {
        JanitorClosetData valueData = new JanitorClosetData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this JanitorClosetData object
     */
    public String toString()
    {
        return "[" + "JanitorClosetId=" + mJanitorClosetId + ", BusEntityId=" + mBusEntityId + ", UserId=" + mUserId + ", ItemId=" + mItemId + ", OrderId=" + mOrderId + ", ShortDesc=" + mShortDesc + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("JanitorCloset");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mJanitorClosetId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("UserId");
        node.appendChild(doc.createTextNode(String.valueOf(mUserId)));
        root.appendChild(node);

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
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
    * creates a clone of this object, the JanitorClosetId field is not cloned.
    *
    * @return JanitorClosetData object
    */
    public Object clone(){
        JanitorClosetData myClone = new JanitorClosetData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mUserId = mUserId;
        
        myClone.mItemId = mItemId;
        
        myClone.mOrderId = mOrderId;
        
        myClone.mShortDesc = mShortDesc;
        
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

        if (JanitorClosetDataAccess.JANITOR_CLOSET_ID.equals(pFieldName)) {
            return getJanitorClosetId();
        } else if (JanitorClosetDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (JanitorClosetDataAccess.USER_ID.equals(pFieldName)) {
            return getUserId();
        } else if (JanitorClosetDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (JanitorClosetDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (JanitorClosetDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (JanitorClosetDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (JanitorClosetDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (JanitorClosetDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (JanitorClosetDataAccess.MOD_BY.equals(pFieldName)) {
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
        return JanitorClosetDataAccess.CLW_JANITOR_CLOSET;
    }

    
    /**
     * Sets the JanitorClosetId field. This field is required to be set in the database.
     *
     * @param pJanitorClosetId
     *  int to use to update the field.
     */
    public void setJanitorClosetId(int pJanitorClosetId){
        this.mJanitorClosetId = pJanitorClosetId;
        setDirty(true);
    }
    /**
     * Retrieves the JanitorClosetId field.
     *
     * @return
     *  int containing the JanitorClosetId field.
     */
    public int getJanitorClosetId(){
        return mJanitorClosetId;
    }

    /**
     * Sets the BusEntityId field. This field is required to be set in the database.
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
     * Sets the ItemId field. This field is required to be set in the database.
     *
     * @param pItemId
     *  int to use to update the field.
     */
    public void setItemId(int pItemId){
        this.mItemId = pItemId;
        setDirty(true);
    }
    /**
     * Retrieves the ItemId field.
     *
     * @return
     *  int containing the ItemId field.
     */
    public int getItemId(){
        return mItemId;
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
