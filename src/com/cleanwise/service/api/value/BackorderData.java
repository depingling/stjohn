
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        BackorderData
 * Description:  This is a ValueObject class wrapping the database table CLW_BACKORDER.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.BackorderDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>BackorderData</code> is a ValueObject class wrapping of the database table CLW_BACKORDER.
 */
public class BackorderData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mBackorderId;// SQL type:NUMBER, not null
    private String mLocation;// SQL type:VARCHAR2
    private String mPoNum;// SQL type:VARCHAR2, not null
    private int mItemNum;// SQL type:NUMBER, not null
    private String mItemStatus;// SQL type:VARCHAR2
    private int mBackorderQty;// SQL type:NUMBER
    private Date mEstInStock;// SQL type:DATE
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mOrderEntryDate;// SQL type:DATE
    private String mOrderBranchCode;// SQL type:VARCHAR2
    private String mOrderNum;// SQL type:VARCHAR2
    private int mOrderDistributionNum;// SQL type:NUMBER
    private int mOrderShipmentNum;// SQL type:NUMBER
    private String mShipBranchCode;// SQL type:VARCHAR2
    private String mItemDesc;// SQL type:VARCHAR2
    private String mItemDesc2;// SQL type:VARCHAR2
    private String mUom;// SQL type:VARCHAR2
    private String mCustIndicatorCode;// SQL type:VARCHAR2
    private int mOrderQty;// SQL type:NUMBER
    private int mShipQty;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public BackorderData ()
    {
        mLocation = "";
        mPoNum = "";
        mItemStatus = "";
        mAddBy = "";
        mModBy = "";
        mOrderBranchCode = "";
        mOrderNum = "";
        mShipBranchCode = "";
        mItemDesc = "";
        mItemDesc2 = "";
        mUom = "";
        mCustIndicatorCode = "";
    }

    /**
     * Constructor.
     */
    public BackorderData(int parm1, String parm2, String parm3, int parm4, String parm5, int parm6, Date parm7, Date parm8, String parm9, Date parm10, String parm11, Date parm12, String parm13, String parm14, int parm15, int parm16, String parm17, String parm18, String parm19, String parm20, String parm21, int parm22, int parm23)
    {
        mBackorderId = parm1;
        mLocation = parm2;
        mPoNum = parm3;
        mItemNum = parm4;
        mItemStatus = parm5;
        mBackorderQty = parm6;
        mEstInStock = parm7;
        mAddDate = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        mOrderEntryDate = parm12;
        mOrderBranchCode = parm13;
        mOrderNum = parm14;
        mOrderDistributionNum = parm15;
        mOrderShipmentNum = parm16;
        mShipBranchCode = parm17;
        mItemDesc = parm18;
        mItemDesc2 = parm19;
        mUom = parm20;
        mCustIndicatorCode = parm21;
        mOrderQty = parm22;
        mShipQty = parm23;
        
    }

    /**
     * Creates a new BackorderData
     *
     * @return
     *  Newly initialized BackorderData object.
     */
    public static BackorderData createValue ()
    {
        BackorderData valueData = new BackorderData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this BackorderData object
     */
    public String toString()
    {
        return "[" + "BackorderId=" + mBackorderId + ", Location=" + mLocation + ", PoNum=" + mPoNum + ", ItemNum=" + mItemNum + ", ItemStatus=" + mItemStatus + ", BackorderQty=" + mBackorderQty + ", EstInStock=" + mEstInStock + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", OrderEntryDate=" + mOrderEntryDate + ", OrderBranchCode=" + mOrderBranchCode + ", OrderNum=" + mOrderNum + ", OrderDistributionNum=" + mOrderDistributionNum + ", OrderShipmentNum=" + mOrderShipmentNum + ", ShipBranchCode=" + mShipBranchCode + ", ItemDesc=" + mItemDesc + ", ItemDesc2=" + mItemDesc2 + ", Uom=" + mUom + ", CustIndicatorCode=" + mCustIndicatorCode + ", OrderQty=" + mOrderQty + ", ShipQty=" + mShipQty + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Backorder");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mBackorderId));

        node =  doc.createElement("Location");
        node.appendChild(doc.createTextNode(String.valueOf(mLocation)));
        root.appendChild(node);

        node =  doc.createElement("PoNum");
        node.appendChild(doc.createTextNode(String.valueOf(mPoNum)));
        root.appendChild(node);

        node =  doc.createElement("ItemNum");
        node.appendChild(doc.createTextNode(String.valueOf(mItemNum)));
        root.appendChild(node);

        node =  doc.createElement("ItemStatus");
        node.appendChild(doc.createTextNode(String.valueOf(mItemStatus)));
        root.appendChild(node);

        node =  doc.createElement("BackorderQty");
        node.appendChild(doc.createTextNode(String.valueOf(mBackorderQty)));
        root.appendChild(node);

        node =  doc.createElement("EstInStock");
        node.appendChild(doc.createTextNode(String.valueOf(mEstInStock)));
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

        node =  doc.createElement("OrderEntryDate");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderEntryDate)));
        root.appendChild(node);

        node =  doc.createElement("OrderBranchCode");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderBranchCode)));
        root.appendChild(node);

        node =  doc.createElement("OrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderNum)));
        root.appendChild(node);

        node =  doc.createElement("OrderDistributionNum");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderDistributionNum)));
        root.appendChild(node);

        node =  doc.createElement("OrderShipmentNum");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderShipmentNum)));
        root.appendChild(node);

        node =  doc.createElement("ShipBranchCode");
        node.appendChild(doc.createTextNode(String.valueOf(mShipBranchCode)));
        root.appendChild(node);

        node =  doc.createElement("ItemDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mItemDesc)));
        root.appendChild(node);

        node =  doc.createElement("ItemDesc2");
        node.appendChild(doc.createTextNode(String.valueOf(mItemDesc2)));
        root.appendChild(node);

        node =  doc.createElement("Uom");
        node.appendChild(doc.createTextNode(String.valueOf(mUom)));
        root.appendChild(node);

        node =  doc.createElement("CustIndicatorCode");
        node.appendChild(doc.createTextNode(String.valueOf(mCustIndicatorCode)));
        root.appendChild(node);

        node =  doc.createElement("OrderQty");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderQty)));
        root.appendChild(node);

        node =  doc.createElement("ShipQty");
        node.appendChild(doc.createTextNode(String.valueOf(mShipQty)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the BackorderId field is not cloned.
    *
    * @return BackorderData object
    */
    public Object clone(){
        BackorderData myClone = new BackorderData();
        
        myClone.mLocation = mLocation;
        
        myClone.mPoNum = mPoNum;
        
        myClone.mItemNum = mItemNum;
        
        myClone.mItemStatus = mItemStatus;
        
        myClone.mBackorderQty = mBackorderQty;
        
        if(mEstInStock != null){
                myClone.mEstInStock = (Date) mEstInStock.clone();
        }
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mOrderEntryDate != null){
                myClone.mOrderEntryDate = (Date) mOrderEntryDate.clone();
        }
        
        myClone.mOrderBranchCode = mOrderBranchCode;
        
        myClone.mOrderNum = mOrderNum;
        
        myClone.mOrderDistributionNum = mOrderDistributionNum;
        
        myClone.mOrderShipmentNum = mOrderShipmentNum;
        
        myClone.mShipBranchCode = mShipBranchCode;
        
        myClone.mItemDesc = mItemDesc;
        
        myClone.mItemDesc2 = mItemDesc2;
        
        myClone.mUom = mUom;
        
        myClone.mCustIndicatorCode = mCustIndicatorCode;
        
        myClone.mOrderQty = mOrderQty;
        
        myClone.mShipQty = mShipQty;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (BackorderDataAccess.BACKORDER_ID.equals(pFieldName)) {
            return getBackorderId();
        } else if (BackorderDataAccess.LOCATION.equals(pFieldName)) {
            return getLocation();
        } else if (BackorderDataAccess.PO_NUM.equals(pFieldName)) {
            return getPoNum();
        } else if (BackorderDataAccess.ITEM_NUM.equals(pFieldName)) {
            return getItemNum();
        } else if (BackorderDataAccess.ITEM_STATUS.equals(pFieldName)) {
            return getItemStatus();
        } else if (BackorderDataAccess.BACKORDER_QTY.equals(pFieldName)) {
            return getBackorderQty();
        } else if (BackorderDataAccess.EST_IN_STOCK.equals(pFieldName)) {
            return getEstInStock();
        } else if (BackorderDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (BackorderDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (BackorderDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (BackorderDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (BackorderDataAccess.ORDER_ENTRY_DATE.equals(pFieldName)) {
            return getOrderEntryDate();
        } else if (BackorderDataAccess.ORDER_BRANCH_CODE.equals(pFieldName)) {
            return getOrderBranchCode();
        } else if (BackorderDataAccess.ORDER_NUM.equals(pFieldName)) {
            return getOrderNum();
        } else if (BackorderDataAccess.ORDER_DISTRIBUTION_NUM.equals(pFieldName)) {
            return getOrderDistributionNum();
        } else if (BackorderDataAccess.ORDER_SHIPMENT_NUM.equals(pFieldName)) {
            return getOrderShipmentNum();
        } else if (BackorderDataAccess.SHIP_BRANCH_CODE.equals(pFieldName)) {
            return getShipBranchCode();
        } else if (BackorderDataAccess.ITEM_DESC.equals(pFieldName)) {
            return getItemDesc();
        } else if (BackorderDataAccess.ITEM_DESC2.equals(pFieldName)) {
            return getItemDesc2();
        } else if (BackorderDataAccess.UOM.equals(pFieldName)) {
            return getUom();
        } else if (BackorderDataAccess.CUST_INDICATOR_CODE.equals(pFieldName)) {
            return getCustIndicatorCode();
        } else if (BackorderDataAccess.ORDER_QTY.equals(pFieldName)) {
            return getOrderQty();
        } else if (BackorderDataAccess.SHIP_QTY.equals(pFieldName)) {
            return getShipQty();
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
        return BackorderDataAccess.CLW_BACKORDER;
    }

    
    /**
     * Sets the BackorderId field. This field is required to be set in the database.
     *
     * @param pBackorderId
     *  int to use to update the field.
     */
    public void setBackorderId(int pBackorderId){
        this.mBackorderId = pBackorderId;
        setDirty(true);
    }
    /**
     * Retrieves the BackorderId field.
     *
     * @return
     *  int containing the BackorderId field.
     */
    public int getBackorderId(){
        return mBackorderId;
    }

    /**
     * Sets the Location field.
     *
     * @param pLocation
     *  String to use to update the field.
     */
    public void setLocation(String pLocation){
        this.mLocation = pLocation;
        setDirty(true);
    }
    /**
     * Retrieves the Location field.
     *
     * @return
     *  String containing the Location field.
     */
    public String getLocation(){
        return mLocation;
    }

    /**
     * Sets the PoNum field. This field is required to be set in the database.
     *
     * @param pPoNum
     *  String to use to update the field.
     */
    public void setPoNum(String pPoNum){
        this.mPoNum = pPoNum;
        setDirty(true);
    }
    /**
     * Retrieves the PoNum field.
     *
     * @return
     *  String containing the PoNum field.
     */
    public String getPoNum(){
        return mPoNum;
    }

    /**
     * Sets the ItemNum field. This field is required to be set in the database.
     *
     * @param pItemNum
     *  int to use to update the field.
     */
    public void setItemNum(int pItemNum){
        this.mItemNum = pItemNum;
        setDirty(true);
    }
    /**
     * Retrieves the ItemNum field.
     *
     * @return
     *  int containing the ItemNum field.
     */
    public int getItemNum(){
        return mItemNum;
    }

    /**
     * Sets the ItemStatus field.
     *
     * @param pItemStatus
     *  String to use to update the field.
     */
    public void setItemStatus(String pItemStatus){
        this.mItemStatus = pItemStatus;
        setDirty(true);
    }
    /**
     * Retrieves the ItemStatus field.
     *
     * @return
     *  String containing the ItemStatus field.
     */
    public String getItemStatus(){
        return mItemStatus;
    }

    /**
     * Sets the BackorderQty field.
     *
     * @param pBackorderQty
     *  int to use to update the field.
     */
    public void setBackorderQty(int pBackorderQty){
        this.mBackorderQty = pBackorderQty;
        setDirty(true);
    }
    /**
     * Retrieves the BackorderQty field.
     *
     * @return
     *  int containing the BackorderQty field.
     */
    public int getBackorderQty(){
        return mBackorderQty;
    }

    /**
     * Sets the EstInStock field.
     *
     * @param pEstInStock
     *  Date to use to update the field.
     */
    public void setEstInStock(Date pEstInStock){
        this.mEstInStock = pEstInStock;
        setDirty(true);
    }
    /**
     * Retrieves the EstInStock field.
     *
     * @return
     *  Date containing the EstInStock field.
     */
    public Date getEstInStock(){
        return mEstInStock;
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
     * Sets the OrderEntryDate field.
     *
     * @param pOrderEntryDate
     *  Date to use to update the field.
     */
    public void setOrderEntryDate(Date pOrderEntryDate){
        this.mOrderEntryDate = pOrderEntryDate;
        setDirty(true);
    }
    /**
     * Retrieves the OrderEntryDate field.
     *
     * @return
     *  Date containing the OrderEntryDate field.
     */
    public Date getOrderEntryDate(){
        return mOrderEntryDate;
    }

    /**
     * Sets the OrderBranchCode field.
     *
     * @param pOrderBranchCode
     *  String to use to update the field.
     */
    public void setOrderBranchCode(String pOrderBranchCode){
        this.mOrderBranchCode = pOrderBranchCode;
        setDirty(true);
    }
    /**
     * Retrieves the OrderBranchCode field.
     *
     * @return
     *  String containing the OrderBranchCode field.
     */
    public String getOrderBranchCode(){
        return mOrderBranchCode;
    }

    /**
     * Sets the OrderNum field.
     *
     * @param pOrderNum
     *  String to use to update the field.
     */
    public void setOrderNum(String pOrderNum){
        this.mOrderNum = pOrderNum;
        setDirty(true);
    }
    /**
     * Retrieves the OrderNum field.
     *
     * @return
     *  String containing the OrderNum field.
     */
    public String getOrderNum(){
        return mOrderNum;
    }

    /**
     * Sets the OrderDistributionNum field.
     *
     * @param pOrderDistributionNum
     *  int to use to update the field.
     */
    public void setOrderDistributionNum(int pOrderDistributionNum){
        this.mOrderDistributionNum = pOrderDistributionNum;
        setDirty(true);
    }
    /**
     * Retrieves the OrderDistributionNum field.
     *
     * @return
     *  int containing the OrderDistributionNum field.
     */
    public int getOrderDistributionNum(){
        return mOrderDistributionNum;
    }

    /**
     * Sets the OrderShipmentNum field.
     *
     * @param pOrderShipmentNum
     *  int to use to update the field.
     */
    public void setOrderShipmentNum(int pOrderShipmentNum){
        this.mOrderShipmentNum = pOrderShipmentNum;
        setDirty(true);
    }
    /**
     * Retrieves the OrderShipmentNum field.
     *
     * @return
     *  int containing the OrderShipmentNum field.
     */
    public int getOrderShipmentNum(){
        return mOrderShipmentNum;
    }

    /**
     * Sets the ShipBranchCode field.
     *
     * @param pShipBranchCode
     *  String to use to update the field.
     */
    public void setShipBranchCode(String pShipBranchCode){
        this.mShipBranchCode = pShipBranchCode;
        setDirty(true);
    }
    /**
     * Retrieves the ShipBranchCode field.
     *
     * @return
     *  String containing the ShipBranchCode field.
     */
    public String getShipBranchCode(){
        return mShipBranchCode;
    }

    /**
     * Sets the ItemDesc field.
     *
     * @param pItemDesc
     *  String to use to update the field.
     */
    public void setItemDesc(String pItemDesc){
        this.mItemDesc = pItemDesc;
        setDirty(true);
    }
    /**
     * Retrieves the ItemDesc field.
     *
     * @return
     *  String containing the ItemDesc field.
     */
    public String getItemDesc(){
        return mItemDesc;
    }

    /**
     * Sets the ItemDesc2 field.
     *
     * @param pItemDesc2
     *  String to use to update the field.
     */
    public void setItemDesc2(String pItemDesc2){
        this.mItemDesc2 = pItemDesc2;
        setDirty(true);
    }
    /**
     * Retrieves the ItemDesc2 field.
     *
     * @return
     *  String containing the ItemDesc2 field.
     */
    public String getItemDesc2(){
        return mItemDesc2;
    }

    /**
     * Sets the Uom field.
     *
     * @param pUom
     *  String to use to update the field.
     */
    public void setUom(String pUom){
        this.mUom = pUom;
        setDirty(true);
    }
    /**
     * Retrieves the Uom field.
     *
     * @return
     *  String containing the Uom field.
     */
    public String getUom(){
        return mUom;
    }

    /**
     * Sets the CustIndicatorCode field.
     *
     * @param pCustIndicatorCode
     *  String to use to update the field.
     */
    public void setCustIndicatorCode(String pCustIndicatorCode){
        this.mCustIndicatorCode = pCustIndicatorCode;
        setDirty(true);
    }
    /**
     * Retrieves the CustIndicatorCode field.
     *
     * @return
     *  String containing the CustIndicatorCode field.
     */
    public String getCustIndicatorCode(){
        return mCustIndicatorCode;
    }

    /**
     * Sets the OrderQty field.
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
     * Sets the ShipQty field.
     *
     * @param pShipQty
     *  int to use to update the field.
     */
    public void setShipQty(int pShipQty){
        this.mShipQty = pShipQty;
        setDirty(true);
    }
    /**
     * Retrieves the ShipQty field.
     *
     * @return
     *  int containing the ShipQty field.
     */
    public int getShipQty(){
        return mShipQty;
    }


}
