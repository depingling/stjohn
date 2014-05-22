
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ShoppingControlData
 * Description:  This is a ValueObject class wrapping the database table CLW_SHOPPING_CONTROL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ShoppingControlDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ShoppingControlData</code> is a ValueObject class wrapping of the database table CLW_SHOPPING_CONTROL.
 */
public class ShoppingControlData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1668892192662932325L;
    private int mShoppingControlId;// SQL type:NUMBER, not null
    private int mSiteId;// SQL type:NUMBER
    private int mItemId;// SQL type:NUMBER
    private int mMaxOrderQty;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mControlStatusCd;// SQL type:VARCHAR2
    private int mAccountId;// SQL type:NUMBER
    private int mRestrictionDays;// SQL type:NUMBER
    private int mHistoryOrderQty;// SQL type:NUMBER
    private Date mExpDate;// SQL type:DATE
    private int mActualMaxQty;// SQL type:NUMBER
    private String mActionCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ShoppingControlData ()
    {
        mAddBy = "";
        mModBy = "";
        mControlStatusCd = "";
        mActionCd = "";
    }

    /**
     * Constructor.
     */
    public ShoppingControlData(int parm1, int parm2, int parm3, int parm4, Date parm5, String parm6, Date parm7, String parm8, String parm9, int parm10, int parm11, int parm12, Date parm13, int parm14, String parm15)
    {
        mShoppingControlId = parm1;
        mSiteId = parm2;
        mItemId = parm3;
        mMaxOrderQty = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        mControlStatusCd = parm9;
        mAccountId = parm10;
        mRestrictionDays = parm11;
        mHistoryOrderQty = parm12;
        mExpDate = parm13;
        mActualMaxQty = parm14;
        mActionCd = parm15;
        
    }

    /**
     * Creates a new ShoppingControlData
     *
     * @return
     *  Newly initialized ShoppingControlData object.
     */
    public static ShoppingControlData createValue ()
    {
        ShoppingControlData valueData = new ShoppingControlData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ShoppingControlData object
     */
    public String toString()
    {
        return "[" + "ShoppingControlId=" + mShoppingControlId + ", SiteId=" + mSiteId + ", ItemId=" + mItemId + ", MaxOrderQty=" + mMaxOrderQty + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", ControlStatusCd=" + mControlStatusCd + ", AccountId=" + mAccountId + ", RestrictionDays=" + mRestrictionDays + ", HistoryOrderQty=" + mHistoryOrderQty + ", ExpDate=" + mExpDate + ", ActualMaxQty=" + mActualMaxQty + ", ActionCd=" + mActionCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ShoppingControl");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mShoppingControlId));

        node =  doc.createElement("SiteId");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteId)));
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

        node =  doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node =  doc.createElement("RestrictionDays");
        node.appendChild(doc.createTextNode(String.valueOf(mRestrictionDays)));
        root.appendChild(node);

        node =  doc.createElement("HistoryOrderQty");
        node.appendChild(doc.createTextNode(String.valueOf(mHistoryOrderQty)));
        root.appendChild(node);

        node =  doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
        root.appendChild(node);

        node =  doc.createElement("ActualMaxQty");
        node.appendChild(doc.createTextNode(String.valueOf(mActualMaxQty)));
        root.appendChild(node);

        node =  doc.createElement("ActionCd");
        node.appendChild(doc.createTextNode(String.valueOf(mActionCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ShoppingControlId field is not cloned.
    *
    * @return ShoppingControlData object
    */
    public Object clone(){
        ShoppingControlData myClone = new ShoppingControlData();
        
        myClone.mSiteId = mSiteId;
        
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
        
        myClone.mAccountId = mAccountId;
        
        myClone.mRestrictionDays = mRestrictionDays;
        
        myClone.mHistoryOrderQty = mHistoryOrderQty;
        
        if(mExpDate != null){
                myClone.mExpDate = (Date) mExpDate.clone();
        }
        
        myClone.mActualMaxQty = mActualMaxQty;
        
        myClone.mActionCd = mActionCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ShoppingControlDataAccess.SHOPPING_CONTROL_ID.equals(pFieldName)) {
            return getShoppingControlId();
        } else if (ShoppingControlDataAccess.SITE_ID.equals(pFieldName)) {
            return getSiteId();
        } else if (ShoppingControlDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (ShoppingControlDataAccess.MAX_ORDER_QTY.equals(pFieldName)) {
            return getMaxOrderQty();
        } else if (ShoppingControlDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ShoppingControlDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ShoppingControlDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ShoppingControlDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (ShoppingControlDataAccess.CONTROL_STATUS_CD.equals(pFieldName)) {
            return getControlStatusCd();
        } else if (ShoppingControlDataAccess.ACCOUNT_ID.equals(pFieldName)) {
            return getAccountId();
        } else if (ShoppingControlDataAccess.RESTRICTION_DAYS.equals(pFieldName)) {
            return getRestrictionDays();
        } else if (ShoppingControlDataAccess.HISTORY_ORDER_QTY.equals(pFieldName)) {
            return getHistoryOrderQty();
        } else if (ShoppingControlDataAccess.EXP_DATE.equals(pFieldName)) {
            return getExpDate();
        } else if (ShoppingControlDataAccess.ACTUAL_MAX_QTY.equals(pFieldName)) {
            return getActualMaxQty();
        } else if (ShoppingControlDataAccess.ACTION_CD.equals(pFieldName)) {
            return getActionCd();
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
        return ShoppingControlDataAccess.CLW_SHOPPING_CONTROL;
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
     * Sets the SiteId field.
     *
     * @param pSiteId
     *  int to use to update the field.
     */
    public void setSiteId(int pSiteId){
        this.mSiteId = pSiteId;
        setDirty(true);
    }
    /**
     * Retrieves the SiteId field.
     *
     * @return
     *  int containing the SiteId field.
     */
    public int getSiteId(){
        return mSiteId;
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
     * Sets the AccountId field.
     *
     * @param pAccountId
     *  int to use to update the field.
     */
    public void setAccountId(int pAccountId){
        this.mAccountId = pAccountId;
        setDirty(true);
    }
    /**
     * Retrieves the AccountId field.
     *
     * @return
     *  int containing the AccountId field.
     */
    public int getAccountId(){
        return mAccountId;
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

    /**
     * Sets the HistoryOrderQty field.
     *
     * @param pHistoryOrderQty
     *  int to use to update the field.
     */
    public void setHistoryOrderQty(int pHistoryOrderQty){
        this.mHistoryOrderQty = pHistoryOrderQty;
        setDirty(true);
    }
    /**
     * Retrieves the HistoryOrderQty field.
     *
     * @return
     *  int containing the HistoryOrderQty field.
     */
    public int getHistoryOrderQty(){
        return mHistoryOrderQty;
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
     * Sets the ActualMaxQty field.
     *
     * @param pActualMaxQty
     *  int to use to update the field.
     */
    public void setActualMaxQty(int pActualMaxQty){
        this.mActualMaxQty = pActualMaxQty;
        setDirty(true);
    }
    /**
     * Retrieves the ActualMaxQty field.
     *
     * @return
     *  int containing the ActualMaxQty field.
     */
    public int getActualMaxQty(){
        return mActualMaxQty;
    }

    /**
     * Sets the ActionCd field.
     *
     * @param pActionCd
     *  String to use to update the field.
     */
    public void setActionCd(String pActionCd){
        this.mActionCd = pActionCd;
        setDirty(true);
    }
    /**
     * Retrieves the ActionCd field.
     *
     * @return
     *  String containing the ActionCd field.
     */
    public String getActionCd(){
        return mActionCd;
    }


}
