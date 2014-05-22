
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InventoryOrderQtyData
 * Description:  This is a ValueObject class wrapping the database table CLW_INVENTORY_ORDER_QTY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.InventoryOrderQtyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>InventoryOrderQtyData</code> is a ValueObject class wrapping of the database table CLW_INVENTORY_ORDER_QTY.
 */
public class InventoryOrderQtyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1L;
    private int mInventoryOrderQtyId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private int mItemId;// SQL type:NUMBER, not null
    private String mItemType;// SQL type:VARCHAR2
    private int mOrderId;// SQL type:NUMBER
    private int mPar;// SQL type:NUMBER, not null
    private String mQtyOnHand;// SQL type:VARCHAR2
    private String mInventoryQty;// SQL type:VARCHAR2
    private int mOrderQty;// SQL type:NUMBER, not null
    private String mAutoOrderApplied;// SQL type:CHAR
    private String mEnableAutoOrder;// SQL type:CHAR
    private java.math.BigDecimal mAutoOrderFactor;// SQL type:NUMBER
    private Date mCutoffDate;// SQL type:DATE, not null
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2
    private java.math.BigDecimal mPrice;// SQL type:NUMBER
    private String mDistItemNum;// SQL type:VARCHAR2
    private String mCategory;// SQL type:VARCHAR2
    private String mCostCenter;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public InventoryOrderQtyData ()
    {
        mItemType = "";
        mQtyOnHand = "";
        mInventoryQty = "";
        mAutoOrderApplied = "";
        mEnableAutoOrder = "";
        mAddBy = "";
        mModBy = "";
        mDistItemNum = "";
        mCategory = "";
        mCostCenter = "";
    }

    /**
     * Constructor.
     */
    public InventoryOrderQtyData(int parm1, int parm2, int parm3, String parm4, int parm5, int parm6, String parm7, String parm8, int parm9, String parm10, String parm11, java.math.BigDecimal parm12, Date parm13, Date parm14, String parm15, Date parm16, String parm17, java.math.BigDecimal parm18, String parm19, String parm20, String parm21)
    {
        mInventoryOrderQtyId = parm1;
        mBusEntityId = parm2;
        mItemId = parm3;
        mItemType = parm4;
        mOrderId = parm5;
        mPar = parm6;
        mQtyOnHand = parm7;
        mInventoryQty = parm8;
        mOrderQty = parm9;
        mAutoOrderApplied = parm10;
        mEnableAutoOrder = parm11;
        mAutoOrderFactor = parm12;
        mCutoffDate = parm13;
        mAddDate = parm14;
        mAddBy = parm15;
        mModDate = parm16;
        mModBy = parm17;
        mPrice = parm18;
        mDistItemNum = parm19;
        mCategory = parm20;
        mCostCenter = parm21;
        
    }

    /**
     * Creates a new InventoryOrderQtyData
     *
     * @return
     *  Newly initialized InventoryOrderQtyData object.
     */
    public static InventoryOrderQtyData createValue ()
    {
        InventoryOrderQtyData valueData = new InventoryOrderQtyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InventoryOrderQtyData object
     */
    public String toString()
    {
        return "[" + "InventoryOrderQtyId=" + mInventoryOrderQtyId + ", BusEntityId=" + mBusEntityId + ", ItemId=" + mItemId + ", ItemType=" + mItemType + ", OrderId=" + mOrderId + ", Par=" + mPar + ", QtyOnHand=" + mQtyOnHand + ", InventoryQty=" + mInventoryQty + ", OrderQty=" + mOrderQty + ", AutoOrderApplied=" + mAutoOrderApplied + ", EnableAutoOrder=" + mEnableAutoOrder + ", AutoOrderFactor=" + mAutoOrderFactor + ", CutoffDate=" + mCutoffDate + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", Price=" + mPrice + ", DistItemNum=" + mDistItemNum + ", Category=" + mCategory + ", CostCenter=" + mCostCenter + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("InventoryOrderQty");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mInventoryOrderQtyId));

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("ItemType");
        node.appendChild(doc.createTextNode(String.valueOf(mItemType)));
        root.appendChild(node);

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node =  doc.createElement("Par");
        node.appendChild(doc.createTextNode(String.valueOf(mPar)));
        root.appendChild(node);

        node =  doc.createElement("QtyOnHand");
        node.appendChild(doc.createTextNode(String.valueOf(mQtyOnHand)));
        root.appendChild(node);

        node =  doc.createElement("InventoryQty");
        node.appendChild(doc.createTextNode(String.valueOf(mInventoryQty)));
        root.appendChild(node);

        node =  doc.createElement("OrderQty");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderQty)));
        root.appendChild(node);

        node =  doc.createElement("AutoOrderApplied");
        node.appendChild(doc.createTextNode(String.valueOf(mAutoOrderApplied)));
        root.appendChild(node);

        node =  doc.createElement("EnableAutoOrder");
        node.appendChild(doc.createTextNode(String.valueOf(mEnableAutoOrder)));
        root.appendChild(node);

        node =  doc.createElement("AutoOrderFactor");
        node.appendChild(doc.createTextNode(String.valueOf(mAutoOrderFactor)));
        root.appendChild(node);

        node =  doc.createElement("CutoffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mCutoffDate)));
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

        node =  doc.createElement("Price");
        node.appendChild(doc.createTextNode(String.valueOf(mPrice)));
        root.appendChild(node);

        node =  doc.createElement("DistItemNum");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemNum)));
        root.appendChild(node);

        node =  doc.createElement("Category");
        node.appendChild(doc.createTextNode(String.valueOf(mCategory)));
        root.appendChild(node);

        node =  doc.createElement("CostCenter");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenter)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the InventoryOrderQtyId field is not cloned.
    *
    * @return InventoryOrderQtyData object
    */
    public Object clone(){
        InventoryOrderQtyData myClone = new InventoryOrderQtyData();
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mItemId = mItemId;
        
        myClone.mItemType = mItemType;
        
        myClone.mOrderId = mOrderId;
        
        myClone.mPar = mPar;
        
        myClone.mQtyOnHand = mQtyOnHand;
        
        myClone.mInventoryQty = mInventoryQty;
        
        myClone.mOrderQty = mOrderQty;
        
        myClone.mAutoOrderApplied = mAutoOrderApplied;
        
        myClone.mEnableAutoOrder = mEnableAutoOrder;
        
        myClone.mAutoOrderFactor = mAutoOrderFactor;
        
        if(mCutoffDate != null){
                myClone.mCutoffDate = (Date) mCutoffDate.clone();
        }
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mPrice = mPrice;
        
        myClone.mDistItemNum = mDistItemNum;
        
        myClone.mCategory = mCategory;
        
        myClone.mCostCenter = mCostCenter;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (InventoryOrderQtyDataAccess.INVENTORY_ORDER_QTY_ID.equals(pFieldName)) {
            return getInventoryOrderQtyId();
        } else if (InventoryOrderQtyDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (InventoryOrderQtyDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (InventoryOrderQtyDataAccess.ITEM_TYPE.equals(pFieldName)) {
            return getItemType();
        } else if (InventoryOrderQtyDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (InventoryOrderQtyDataAccess.PAR.equals(pFieldName)) {
            return getPar();
        } else if (InventoryOrderQtyDataAccess.QTY_ON_HAND.equals(pFieldName)) {
            return getQtyOnHand();
        } else if (InventoryOrderQtyDataAccess.INVENTORY_QTY.equals(pFieldName)) {
            return getInventoryQty();
        } else if (InventoryOrderQtyDataAccess.ORDER_QTY.equals(pFieldName)) {
            return getOrderQty();
        } else if (InventoryOrderQtyDataAccess.AUTO_ORDER_APPLIED.equals(pFieldName)) {
            return getAutoOrderApplied();
        } else if (InventoryOrderQtyDataAccess.ENABLE_AUTO_ORDER.equals(pFieldName)) {
            return getEnableAutoOrder();
        } else if (InventoryOrderQtyDataAccess.AUTO_ORDER_FACTOR.equals(pFieldName)) {
            return getAutoOrderFactor();
        } else if (InventoryOrderQtyDataAccess.CUTOFF_DATE.equals(pFieldName)) {
            return getCutoffDate();
        } else if (InventoryOrderQtyDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (InventoryOrderQtyDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (InventoryOrderQtyDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (InventoryOrderQtyDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (InventoryOrderQtyDataAccess.PRICE.equals(pFieldName)) {
            return getPrice();
        } else if (InventoryOrderQtyDataAccess.DIST_ITEM_NUM.equals(pFieldName)) {
            return getDistItemNum();
        } else if (InventoryOrderQtyDataAccess.CATEGORY.equals(pFieldName)) {
            return getCategory();
        } else if (InventoryOrderQtyDataAccess.COST_CENTER.equals(pFieldName)) {
            return getCostCenter();
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
        return InventoryOrderQtyDataAccess.CLW_INVENTORY_ORDER_QTY;
    }

    
    /**
     * Sets the InventoryOrderQtyId field. This field is required to be set in the database.
     *
     * @param pInventoryOrderQtyId
     *  int to use to update the field.
     */
    public void setInventoryOrderQtyId(int pInventoryOrderQtyId){
        this.mInventoryOrderQtyId = pInventoryOrderQtyId;
        setDirty(true);
    }
    /**
     * Retrieves the InventoryOrderQtyId field.
     *
     * @return
     *  int containing the InventoryOrderQtyId field.
     */
    public int getInventoryOrderQtyId(){
        return mInventoryOrderQtyId;
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
     * Sets the ItemType field.
     *
     * @param pItemType
     *  String to use to update the field.
     */
    public void setItemType(String pItemType){
        this.mItemType = pItemType;
        setDirty(true);
    }
    /**
     * Retrieves the ItemType field.
     *
     * @return
     *  String containing the ItemType field.
     */
    public String getItemType(){
        return mItemType;
    }

    /**
     * Sets the OrderId field.
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
     * Sets the Par field. This field is required to be set in the database.
     *
     * @param pPar
     *  int to use to update the field.
     */
    public void setPar(int pPar){
        this.mPar = pPar;
        setDirty(true);
    }
    /**
     * Retrieves the Par field.
     *
     * @return
     *  int containing the Par field.
     */
    public int getPar(){
        return mPar;
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
     * Sets the InventoryQty field.
     *
     * @param pInventoryQty
     *  String to use to update the field.
     */
    public void setInventoryQty(String pInventoryQty){
        this.mInventoryQty = pInventoryQty;
        setDirty(true);
    }
    /**
     * Retrieves the InventoryQty field.
     *
     * @return
     *  String containing the InventoryQty field.
     */
    public String getInventoryQty(){
        return mInventoryQty;
    }

    /**
     * Sets the OrderQty field. This field is required to be set in the database.
     *
     * @param pOrderQty
     *  int to use to update the field.
     */
    public void setOrderQty(int pOrderQty){
        this.mOrderQty = pOrderQty;
        setDirty(true);
    }
    /**
     * Retrieves the OrderQty field.
     *
     * @return
     *  int containing the OrderQty field.
     */
    public int getOrderQty(){
        return mOrderQty;
    }

    /**
     * Sets the AutoOrderApplied field.
     *
     * @param pAutoOrderApplied
     *  String to use to update the field.
     */
    public void setAutoOrderApplied(String pAutoOrderApplied){
        this.mAutoOrderApplied = pAutoOrderApplied;
        setDirty(true);
    }
    /**
     * Retrieves the AutoOrderApplied field.
     *
     * @return
     *  String containing the AutoOrderApplied field.
     */
    public String getAutoOrderApplied(){
        return mAutoOrderApplied;
    }

    /**
     * Sets the EnableAutoOrder field.
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
     * Sets the AutoOrderFactor field.
     *
     * @param pAutoOrderFactor
     *  java.math.BigDecimal to use to update the field.
     */
    public void setAutoOrderFactor(java.math.BigDecimal pAutoOrderFactor){
        this.mAutoOrderFactor = pAutoOrderFactor;
        setDirty(true);
    }
    /**
     * Retrieves the AutoOrderFactor field.
     *
     * @return
     *  java.math.BigDecimal containing the AutoOrderFactor field.
     */
    public java.math.BigDecimal getAutoOrderFactor(){
        return mAutoOrderFactor;
    }

    /**
     * Sets the CutoffDate field. This field is required to be set in the database.
     *
     * @param pCutoffDate
     *  Date to use to update the field.
     */
    public void setCutoffDate(Date pCutoffDate){
        this.mCutoffDate = pCutoffDate;
        setDirty(true);
    }
    /**
     * Retrieves the CutoffDate field.
     *
     * @return
     *  Date containing the CutoffDate field.
     */
    public Date getCutoffDate(){
        return mCutoffDate;
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
     * Sets the Price field.
     *
     * @param pPrice
     *  java.math.BigDecimal to use to update the field.
     */
    public void setPrice(java.math.BigDecimal pPrice){
        this.mPrice = pPrice;
        setDirty(true);
    }
    /**
     * Retrieves the Price field.
     *
     * @return
     *  java.math.BigDecimal containing the Price field.
     */
    public java.math.BigDecimal getPrice(){
        return mPrice;
    }

    /**
     * Sets the DistItemNum field.
     *
     * @param pDistItemNum
     *  String to use to update the field.
     */
    public void setDistItemNum(String pDistItemNum){
        this.mDistItemNum = pDistItemNum;
        setDirty(true);
    }
    /**
     * Retrieves the DistItemNum field.
     *
     * @return
     *  String containing the DistItemNum field.
     */
    public String getDistItemNum(){
        return mDistItemNum;
    }

    /**
     * Sets the Category field.
     *
     * @param pCategory
     *  String to use to update the field.
     */
    public void setCategory(String pCategory){
        this.mCategory = pCategory;
        setDirty(true);
    }
    /**
     * Retrieves the Category field.
     *
     * @return
     *  String containing the Category field.
     */
    public String getCategory(){
        return mCategory;
    }

    /**
     * Sets the CostCenter field.
     *
     * @param pCostCenter
     *  String to use to update the field.
     */
    public void setCostCenter(String pCostCenter){
        this.mCostCenter = pCostCenter;
        setDirty(true);
    }
    /**
     * Retrieves the CostCenter field.
     *
     * @return
     *  String containing the CostCenter field.
     */
    public String getCostCenter(){
        return mCostCenter;
    }


}
