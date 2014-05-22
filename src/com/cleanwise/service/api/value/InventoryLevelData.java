
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InventoryLevelData
 * Description:  This is a ValueObject class wrapping the database table CLW_INVENTORY_LEVEL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.InventoryLevelDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>InventoryLevelData</code> is a ValueObject class wrapping of the database table CLW_INVENTORY_LEVEL.
 */
public class InventoryLevelData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 36868969059093585L;
    private int mInventoryLevelId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private int mItemId;// SQL type:NUMBER, not null
    private String mQtyOnHand;// SQL type:CHAR
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2
    private Date mParsModDate;// SQL type:DATE
    private String mParsModBy;// SQL type:VARCHAR2
    private String mOrderQty;// SQL type:VARCHAR2
    private String mInitialQtyOnHand;// SQL type:CHAR

    /**
     * Constructor.
     */
    public InventoryLevelData ()
    {
        mQtyOnHand = "";
        mAddBy = "";
        mModBy = "";
        mParsModBy = "";
        mOrderQty = "";
        mInitialQtyOnHand = "";
    }

    /**
     * Constructor.
     */
    public InventoryLevelData(int parm1, int parm2, int parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8, Date parm9, String parm10, String parm11, String parm12)
    {
        mInventoryLevelId = parm1;
        mBusEntityId = parm2;
        mItemId = parm3;
        mQtyOnHand = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        mParsModDate = parm9;
        mParsModBy = parm10;
        mOrderQty = parm11;
        mInitialQtyOnHand = parm12;
        
    }

    /**
     * Creates a new InventoryLevelData
     *
     * @return
     *  Newly initialized InventoryLevelData object.
     */
    public static InventoryLevelData createValue ()
    {
        InventoryLevelData valueData = new InventoryLevelData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InventoryLevelData object
     */
    public String toString()
    {
        return "[" + "InventoryLevelId=" + mInventoryLevelId + ", BusEntityId=" + mBusEntityId + ", ItemId=" + mItemId + ", QtyOnHand=" + mQtyOnHand + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", ParsModDate=" + mParsModDate + ", ParsModBy=" + mParsModBy + ", OrderQty=" + mOrderQty + ", InitialQtyOnHand=" + mInitialQtyOnHand + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("InventoryLevel");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mInventoryLevelId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("QtyOnHand");
        node.appendChild(doc.createTextNode(String.valueOf(mQtyOnHand)));
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

        node =  doc.createElement("ParsModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mParsModDate)));
        root.appendChild(node);

        node =  doc.createElement("ParsModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mParsModBy)));
        root.appendChild(node);

        node =  doc.createElement("OrderQty");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderQty)));
        root.appendChild(node);

        node =  doc.createElement("InitialQtyOnHand");
        node.appendChild(doc.createTextNode(String.valueOf(mInitialQtyOnHand)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the InventoryLevelId field is not cloned.
    *
    * @return InventoryLevelData object
    */
    public Object clone(){
        InventoryLevelData myClone = new InventoryLevelData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mItemId = mItemId;
        
        myClone.mQtyOnHand = mQtyOnHand;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mParsModDate != null){
                myClone.mParsModDate = (Date) mParsModDate.clone();
        }
        
        myClone.mParsModBy = mParsModBy;
        
        myClone.mOrderQty = mOrderQty;
        
        myClone.mInitialQtyOnHand = mInitialQtyOnHand;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (InventoryLevelDataAccess.INVENTORY_LEVEL_ID.equals(pFieldName)) {
            return getInventoryLevelId();
        } else if (InventoryLevelDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (InventoryLevelDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (InventoryLevelDataAccess.QTY_ON_HAND.equals(pFieldName)) {
            return getQtyOnHand();
        } else if (InventoryLevelDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (InventoryLevelDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (InventoryLevelDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (InventoryLevelDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (InventoryLevelDataAccess.PARS_MOD_DATE.equals(pFieldName)) {
            return getParsModDate();
        } else if (InventoryLevelDataAccess.PARS_MOD_BY.equals(pFieldName)) {
            return getParsModBy();
        } else if (InventoryLevelDataAccess.ORDER_QTY.equals(pFieldName)) {
            return getOrderQty();
        } else if (InventoryLevelDataAccess.INITIAL_QTY_ON_HAND.equals(pFieldName)) {
            return getInitialQtyOnHand();
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
        return InventoryLevelDataAccess.CLW_INVENTORY_LEVEL;
    }

    
    /**
     * Sets the InventoryLevelId field. This field is required to be set in the database.
     *
     * @param pInventoryLevelId
     *  int to use to update the field.
     */
    public void setInventoryLevelId(int pInventoryLevelId){
        this.mInventoryLevelId = pInventoryLevelId;
        setDirty(true);
    }
    /**
     * Retrieves the InventoryLevelId field.
     *
     * @return
     *  int containing the InventoryLevelId field.
     */
    public int getInventoryLevelId(){
        return mInventoryLevelId;
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
     * Sets the QtyOnHand field.
     *
     * @param pQtyOnHand
     *  String to use to update the field.
     */
    public void setQtyOnHand(String pQtyOnHand){
        this.mQtyOnHand = pQtyOnHand;
        setDirty(true);
    }
    /**
     * Retrieves the QtyOnHand field.
     *
     * @return
     *  String containing the QtyOnHand field.
     */
    public String getQtyOnHand(){
        return mQtyOnHand;
    }

    /**
     * Sets the AddDate field.
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
     * Sets the ModDate field.
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
     * Sets the ParsModDate field.
     *
     * @param pParsModDate
     *  Date to use to update the field.
     */
    public void setParsModDate(Date pParsModDate){
        this.mParsModDate = pParsModDate;
        setDirty(true);
    }
    /**
     * Retrieves the ParsModDate field.
     *
     * @return
     *  Date containing the ParsModDate field.
     */
    public Date getParsModDate(){
        return mParsModDate;
    }

    /**
     * Sets the ParsModBy field.
     *
     * @param pParsModBy
     *  String to use to update the field.
     */
    public void setParsModBy(String pParsModBy){
        this.mParsModBy = pParsModBy;
        setDirty(true);
    }
    /**
     * Retrieves the ParsModBy field.
     *
     * @return
     *  String containing the ParsModBy field.
     */
    public String getParsModBy(){
        return mParsModBy;
    }

    /**
     * Sets the OrderQty field.
     *
     * @param pOrderQty
     *  String to use to update the field.
     */
    public void setOrderQty(String pOrderQty){
        this.mOrderQty = pOrderQty;
        setDirty(true);
    }
    /**
     * Retrieves the OrderQty field.
     *
     * @return
     *  String containing the OrderQty field.
     */
    public String getOrderQty(){
        return mOrderQty;
    }

    /**
     * Sets the InitialQtyOnHand field.
     *
     * @param pInitialQtyOnHand
     *  String to use to update the field.
     */
    public void setInitialQtyOnHand(String pInitialQtyOnHand){
        this.mInitialQtyOnHand = pInitialQtyOnHand;
        setDirty(true);
    }
    /**
     * Retrieves the InitialQtyOnHand field.
     *
     * @return
     *  String containing the InitialQtyOnHand field.
     */
    public String getInitialQtyOnHand(){
        return mInitialQtyOnHand;
    }


}
