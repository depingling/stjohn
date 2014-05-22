
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InventoryItemsData
 * Description:  This is a ValueObject class wrapping the database table CLW_INVENTORY_ITEMS.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.InventoryItemsDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>InventoryItemsData</code> is a ValueObject class wrapping of the database table CLW_INVENTORY_ITEMS.
 */
public class InventoryItemsData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1887405223013987188L;
    private int mInventoryItemsId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private int mItemId;// SQL type:NUMBER, not null
    private String mStatusCd;// SQL type:VARCHAR2, not null
    private String mEnableAutoOrder;// SQL type:CHAR, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public InventoryItemsData ()
    {
        mStatusCd = "";
        mEnableAutoOrder = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public InventoryItemsData(int parm1, int parm2, int parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mInventoryItemsId = parm1;
        mBusEntityId = parm2;
        mItemId = parm3;
        mStatusCd = parm4;
        mEnableAutoOrder = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new InventoryItemsData
     *
     * @return
     *  Newly initialized InventoryItemsData object.
     */
    public static InventoryItemsData createValue ()
    {
        InventoryItemsData valueData = new InventoryItemsData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InventoryItemsData object
     */
    public String toString()
    {
        return "[" + "InventoryItemsId=" + mInventoryItemsId + ", BusEntityId=" + mBusEntityId + ", ItemId=" + mItemId + ", StatusCd=" + mStatusCd + ", EnableAutoOrder=" + mEnableAutoOrder + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("InventoryItems");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mInventoryItemsId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("StatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("EnableAutoOrder");
        node.appendChild(doc.createTextNode(String.valueOf(mEnableAutoOrder)));
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
    * creates a clone of this object, the InventoryItemsId field is not cloned.
    *
    * @return InventoryItemsData object
    */
    public Object clone(){
        InventoryItemsData myClone = new InventoryItemsData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mItemId = mItemId;
        
        myClone.mStatusCd = mStatusCd;
        
        myClone.mEnableAutoOrder = mEnableAutoOrder;
        
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

        if (InventoryItemsDataAccess.INVENTORY_ITEMS_ID.equals(pFieldName)) {
            return getInventoryItemsId();
        } else if (InventoryItemsDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (InventoryItemsDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (InventoryItemsDataAccess.STATUS_CD.equals(pFieldName)) {
            return getStatusCd();
        } else if (InventoryItemsDataAccess.ENABLE_AUTO_ORDER.equals(pFieldName)) {
            return getEnableAutoOrder();
        } else if (InventoryItemsDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (InventoryItemsDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (InventoryItemsDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (InventoryItemsDataAccess.MOD_BY.equals(pFieldName)) {
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
        return InventoryItemsDataAccess.CLW_INVENTORY_ITEMS;
    }

    
    /**
     * Sets the InventoryItemsId field. This field is required to be set in the database.
     *
     * @param pInventoryItemsId
     *  int to use to update the field.
     */
    public void setInventoryItemsId(int pInventoryItemsId){
        this.mInventoryItemsId = pInventoryItemsId;
        setDirty(true);
    }
    /**
     * Retrieves the InventoryItemsId field.
     *
     * @return
     *  int containing the InventoryItemsId field.
     */
    public int getInventoryItemsId(){
        return mInventoryItemsId;
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
     * Sets the StatusCd field. This field is required to be set in the database.
     *
     * @param pStatusCd
     *  String to use to update the field.
     */
    public void setStatusCd(String pStatusCd){
        this.mStatusCd = pStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the StatusCd field.
     *
     * @return
     *  String containing the StatusCd field.
     */
    public String getStatusCd(){
        return mStatusCd;
    }

    /**
     * Sets the EnableAutoOrder field. This field is required to be set in the database.
     *
     * @param pEnableAutoOrder
     *  String to use to update the field.
     */
    public void setEnableAutoOrder(String pEnableAutoOrder){
        this.mEnableAutoOrder = pEnableAutoOrder;
        setDirty(true);
    }
    /**
     * Retrieves the EnableAutoOrder field.
     *
     * @return
     *  String containing the EnableAutoOrder field.
     */
    public String getEnableAutoOrder(){
        return mEnableAutoOrder;
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
