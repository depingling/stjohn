
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        StoreMessageAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_STORE_MESSAGE_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.StoreMessageAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>StoreMessageAssocData</code> is a ValueObject class wrapping of the database table CLW_STORE_MESSAGE_ASSOC.
 */
public class StoreMessageAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 6781004386957082525L;
    private int mStoreMessageAssocId;// SQL type:NUMBER, not null
    private int mStoreMessageId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private String mStoreMessageAssocCd;// SQL type:VARCHAR2
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null

    /**
     * Constructor.
     */
    public StoreMessageAssocData ()
    {
        mStoreMessageAssocCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public StoreMessageAssocData(int parm1, int parm2, int parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8)
    {
        mStoreMessageAssocId = parm1;
        mStoreMessageId = parm2;
        mBusEntityId = parm3;
        mStoreMessageAssocCd = parm4;
        mAddBy = parm5;
        mAddDate = parm6;
        mModBy = parm7;
        mModDate = parm8;
        
    }

    /**
     * Creates a new StoreMessageAssocData
     *
     * @return
     *  Newly initialized StoreMessageAssocData object.
     */
    public static StoreMessageAssocData createValue ()
    {
        StoreMessageAssocData valueData = new StoreMessageAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this StoreMessageAssocData object
     */
    public String toString()
    {
        return "[" + "StoreMessageAssocId=" + mStoreMessageAssocId + ", StoreMessageId=" + mStoreMessageId + ", BusEntityId=" + mBusEntityId + ", StoreMessageAssocCd=" + mStoreMessageAssocCd + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("StoreMessageAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mStoreMessageAssocId));

        node =  doc.createElement("StoreMessageId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreMessageId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("StoreMessageAssocCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreMessageAssocCd)));
        root.appendChild(node);

        node =  doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node =  doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the StoreMessageAssocId field is not cloned.
    *
    * @return StoreMessageAssocData object
    */
    public Object clone(){
        StoreMessageAssocData myClone = new StoreMessageAssocData();
        
        myClone.mStoreMessageId = mStoreMessageId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mStoreMessageAssocCd = mStoreMessageAssocCd;
        
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

        if (StoreMessageAssocDataAccess.STORE_MESSAGE_ASSOC_ID.equals(pFieldName)) {
            return getStoreMessageAssocId();
        } else if (StoreMessageAssocDataAccess.STORE_MESSAGE_ID.equals(pFieldName)) {
            return getStoreMessageId();
        } else if (StoreMessageAssocDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (StoreMessageAssocDataAccess.STORE_MESSAGE_ASSOC_CD.equals(pFieldName)) {
            return getStoreMessageAssocCd();
        } else if (StoreMessageAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (StoreMessageAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (StoreMessageAssocDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (StoreMessageAssocDataAccess.MOD_DATE.equals(pFieldName)) {
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
        return StoreMessageAssocDataAccess.CLW_STORE_MESSAGE_ASSOC;
    }

    
    /**
     * Sets the StoreMessageAssocId field. This field is required to be set in the database.
     *
     * @param pStoreMessageAssocId
     *  int to use to update the field.
     */
    public void setStoreMessageAssocId(int pStoreMessageAssocId){
        this.mStoreMessageAssocId = pStoreMessageAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the StoreMessageAssocId field.
     *
     * @return
     *  int containing the StoreMessageAssocId field.
     */
    public int getStoreMessageAssocId(){
        return mStoreMessageAssocId;
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
     * Sets the StoreMessageAssocCd field.
     *
     * @param pStoreMessageAssocCd
     *  String to use to update the field.
     */
    public void setStoreMessageAssocCd(String pStoreMessageAssocCd){
        this.mStoreMessageAssocCd = pStoreMessageAssocCd;
        setDirty(true);
    }
    /**
     * Retrieves the StoreMessageAssocCd field.
     *
     * @return
     *  String containing the StoreMessageAssocCd field.
     */
    public String getStoreMessageAssocCd(){
        return mStoreMessageAssocCd;
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
