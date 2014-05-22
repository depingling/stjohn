
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ShoppingControlV2Data
 * Description:  This is a ValueObject class wrapping the database table CLW_SHOPPING_CONTROL_V2.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ShoppingControlV2DataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ShoppingControlV2Data</code> is a ValueObject class wrapping of the database table CLW_SHOPPING_CONTROL_V2.
 */
public class ShoppingControlV2Data extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1959L;
    private int mShoppingControlId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER
    private int mItemId;// SQL type:NUMBER
    private int mMaxOrderQty;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mControlStatusCd;// SQL type:VARCHAR2
    private int mRestrictionDays;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public ShoppingControlV2Data ()
    {
        mAddBy = "";
        mModBy = "";
        mControlStatusCd = "";
    }

    /**
     * Constructor.
     */
    public ShoppingControlV2Data(int parm1, int parm2, int parm3, int parm4, Date parm5, String parm6, Date parm7, String parm8, String parm9, int parm10)
    {
        mShoppingControlId = parm1;
        mBusEntityId = parm2;
        mItemId = parm3;
        mMaxOrderQty = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        mControlStatusCd = parm9;
        mRestrictionDays = parm10;
        
    }

    /**
     * Creates a new ShoppingControlV2Data
     *
     * @return
     *  Newly initialized ShoppingControlV2Data object.
     */
    public static ShoppingControlV2Data createValue ()
    {
        ShoppingControlV2Data valueData = new ShoppingControlV2Data();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ShoppingControlV2Data object
     */
    public String toString()
    {
        return "[" + "ShoppingControlId=" + mShoppingControlId + ", BusEntityId=" + mBusEntityId + ", ItemId=" + mItemId + ", MaxOrderQty=" + mMaxOrderQty + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", ControlStatusCd=" + mControlStatusCd + ", RestrictionDays=" + mRestrictionDays + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ShoppingControlV2");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mShoppingControlId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("MaxOrderQty");
        node.appendChild(doc.createTextNode(String.valueOf(mMaxOrderQty)));
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

        node =  doc.createElement("ControlStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mControlStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("RestrictionDays");
        node.appendChild(doc.createTextNode(String.valueOf(mRestrictionDays)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ShoppingControlV2Id field is not cloned.
    *
    * @return ShoppingControlV2Data object
    */
    public Object clone(){
        ShoppingControlV2Data myClone = new ShoppingControlV2Data();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mItemId = mItemId;
        
        myClone.mMaxOrderQty = mMaxOrderQty;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mControlStatusCd = mControlStatusCd;
        
        myClone.mRestrictionDays = mRestrictionDays;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ShoppingControlV2DataAccess.SHOPPING_CONTROL_ID.equals(pFieldName)) {
            return getShoppingControlId();
        } else if (ShoppingControlV2DataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (ShoppingControlV2DataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (ShoppingControlV2DataAccess.MAX_ORDER_QTY.equals(pFieldName)) {
            return getMaxOrderQty();
        } else if (ShoppingControlV2DataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ShoppingControlV2DataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ShoppingControlV2DataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ShoppingControlV2DataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (ShoppingControlV2DataAccess.CONTROL_STATUS_CD.equals(pFieldName)) {
            return getControlStatusCd();
        } else if (ShoppingControlV2DataAccess.RESTRICTION_DAYS.equals(pFieldName)) {
            return getRestrictionDays();
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
        return ShoppingControlV2DataAccess.CLW_SHOPPING_CONTROL_V2;
    }

    
    /**
     * Sets the ShoppingControlId field. This field is required to be set in the database.
     *
     * @param pShoppingControlId
     *  int to use to update the field.
     */
    public void setShoppingControlId(int pShoppingControlId){
        this.mShoppingControlId = pShoppingControlId;
        setDirty(true);
    }
    /**
     * Retrieves the ShoppingControlId field.
     *
     * @return
     *  int containing the ShoppingControlId field.
     */
    public int getShoppingControlId(){
        return mShoppingControlId;
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
     * Sets the ItemId field.
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
     * Sets the MaxOrderQty field.
     *
     * @param pMaxOrderQty
     *  int to use to update the field.
     */
    public void setMaxOrderQty(int pMaxOrderQty){
        this.mMaxOrderQty = pMaxOrderQty;
        setDirty(true);
    }
    /**
     * Retrieves the MaxOrderQty field.
     *
     * @return
     *  int containing the MaxOrderQty field.
     */
    public int getMaxOrderQty(){
        return mMaxOrderQty;
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
     * Sets the ControlStatusCd field.
     *
     * @param pControlStatusCd
     *  String to use to update the field.
     */
    public void setControlStatusCd(String pControlStatusCd){
        this.mControlStatusCd = pControlStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the ControlStatusCd field.
     *
     * @return
     *  String containing the ControlStatusCd field.
     */
    public String getControlStatusCd(){
        return mControlStatusCd;
    }

    /**
     * Sets the RestrictionDays field.
     *
     * @param pRestrictionDays
     *  int to use to update the field.
     */
    public void setRestrictionDays(int pRestrictionDays){
        this.mRestrictionDays = pRestrictionDays;
        setDirty(true);
    }
    /**
     * Retrieves the RestrictionDays field.
     *
     * @return
     *  int containing the RestrictionDays field.
     */
    public int getRestrictionDays(){
        return mRestrictionDays;
    }


}
