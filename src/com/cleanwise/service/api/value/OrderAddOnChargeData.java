
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderAddOnChargeData
 * Description:  This is a ValueObject class wrapping the database table CLW_ORDER_ADD_ON_CHARGE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.OrderAddOnChargeDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>OrderAddOnChargeData</code> is a ValueObject class wrapping of the database table CLW_ORDER_ADD_ON_CHARGE.
 */
public class OrderAddOnChargeData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1958L;
    private int mOrderAddOnChargeId;// SQL type:NUMBER, not null
    private int mOrderId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER, not null
    private int mPurchaseOrderId;// SQL type:NUMBER
    private String mDistFeeTypeCd;// SQL type:VARCHAR2
    private String mShortDesc;// SQL type:VARCHAR2
    private String mDistFeeChargeCd;// SQL type:VARCHAR2
    private java.math.BigDecimal mAmount;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public OrderAddOnChargeData ()
    {
        mDistFeeTypeCd = "";
        mShortDesc = "";
        mDistFeeChargeCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public OrderAddOnChargeData(int parm1, int parm2, int parm3, int parm4, String parm5, String parm6, String parm7, java.math.BigDecimal parm8, Date parm9, String parm10, Date parm11, String parm12)
    {
        mOrderAddOnChargeId = parm1;
        mOrderId = parm2;
        mBusEntityId = parm3;
        mPurchaseOrderId = parm4;
        mDistFeeTypeCd = parm5;
        mShortDesc = parm6;
        mDistFeeChargeCd = parm7;
        mAmount = parm8;
        mAddDate = parm9;
        mAddBy = parm10;
        mModDate = parm11;
        mModBy = parm12;
        
    }

    /**
     * Creates a new OrderAddOnChargeData
     *
     * @return
     *  Newly initialized OrderAddOnChargeData object.
     */
    public static OrderAddOnChargeData createValue ()
    {
        OrderAddOnChargeData valueData = new OrderAddOnChargeData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderAddOnChargeData object
     */
    public String toString()
    {
        return "[" + "OrderAddOnChargeId=" + mOrderAddOnChargeId + ", OrderId=" + mOrderId + ", BusEntityId=" + mBusEntityId + ", PurchaseOrderId=" + mPurchaseOrderId + ", DistFeeTypeCd=" + mDistFeeTypeCd + ", ShortDesc=" + mShortDesc + ", DistFeeChargeCd=" + mDistFeeChargeCd + ", Amount=" + mAmount + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("OrderAddOnCharge");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mOrderAddOnChargeId));

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("PurchaseOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mPurchaseOrderId)));
        root.appendChild(node);

        node =  doc.createElement("DistFeeTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mDistFeeTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("DistFeeChargeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mDistFeeChargeCd)));
        root.appendChild(node);

        node =  doc.createElement("Amount");
        node.appendChild(doc.createTextNode(String.valueOf(mAmount)));
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
    * creates a clone of this object, the OrderAddOnChargeId field is not cloned.
    *
    * @return OrderAddOnChargeData object
    */
    public Object clone(){
        OrderAddOnChargeData myClone = new OrderAddOnChargeData();
        
        myClone.mOrderId = mOrderId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mPurchaseOrderId = mPurchaseOrderId;
        
        myClone.mDistFeeTypeCd = mDistFeeTypeCd;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mDistFeeChargeCd = mDistFeeChargeCd;
        
        myClone.mAmount = mAmount;
        
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

        if (OrderAddOnChargeDataAccess.ORDER_ADD_ON_CHARGE_ID.equals(pFieldName)) {
            return getOrderAddOnChargeId();
        } else if (OrderAddOnChargeDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (OrderAddOnChargeDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (OrderAddOnChargeDataAccess.PURCHASE_ORDER_ID.equals(pFieldName)) {
            return getPurchaseOrderId();
        } else if (OrderAddOnChargeDataAccess.DIST_FEE_TYPE_CD.equals(pFieldName)) {
            return getDistFeeTypeCd();
        } else if (OrderAddOnChargeDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (OrderAddOnChargeDataAccess.DIST_FEE_CHARGE_CD.equals(pFieldName)) {
            return getDistFeeChargeCd();
        } else if (OrderAddOnChargeDataAccess.AMOUNT.equals(pFieldName)) {
            return getAmount();
        } else if (OrderAddOnChargeDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (OrderAddOnChargeDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (OrderAddOnChargeDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (OrderAddOnChargeDataAccess.MOD_BY.equals(pFieldName)) {
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
        return OrderAddOnChargeDataAccess.CLW_ORDER_ADD_ON_CHARGE;
    }

    
    /**
     * Sets the OrderAddOnChargeId field. This field is required to be set in the database.
     *
     * @param pOrderAddOnChargeId
     *  int to use to update the field.
     */
    public void setOrderAddOnChargeId(int pOrderAddOnChargeId){
        this.mOrderAddOnChargeId = pOrderAddOnChargeId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderAddOnChargeId field.
     *
     * @return
     *  int containing the OrderAddOnChargeId field.
     */
    public int getOrderAddOnChargeId(){
        return mOrderAddOnChargeId;
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
     * Sets the PurchaseOrderId field.
     *
     * @param pPurchaseOrderId
     *  int to use to update the field.
     */
    public void setPurchaseOrderId(int pPurchaseOrderId){
        this.mPurchaseOrderId = pPurchaseOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the PurchaseOrderId field.
     *
     * @return
     *  int containing the PurchaseOrderId field.
     */
    public int getPurchaseOrderId(){
        return mPurchaseOrderId;
    }

    /**
     * Sets the DistFeeTypeCd field.
     *
     * @param pDistFeeTypeCd
     *  String to use to update the field.
     */
    public void setDistFeeTypeCd(String pDistFeeTypeCd){
        this.mDistFeeTypeCd = pDistFeeTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the DistFeeTypeCd field.
     *
     * @return
     *  String containing the DistFeeTypeCd field.
     */
    public String getDistFeeTypeCd(){
        return mDistFeeTypeCd;
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
     * Sets the DistFeeChargeCd field.
     *
     * @param pDistFeeChargeCd
     *  String to use to update the field.
     */
    public void setDistFeeChargeCd(String pDistFeeChargeCd){
        this.mDistFeeChargeCd = pDistFeeChargeCd;
        setDirty(true);
    }
    /**
     * Retrieves the DistFeeChargeCd field.
     *
     * @return
     *  String containing the DistFeeChargeCd field.
     */
    public String getDistFeeChargeCd(){
        return mDistFeeChargeCd;
    }

    /**
     * Sets the Amount field.
     *
     * @param pAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setAmount(java.math.BigDecimal pAmount){
        this.mAmount = pAmount;
        setDirty(true);
    }
    /**
     * Retrieves the Amount field.
     *
     * @return
     *  java.math.BigDecimal containing the Amount field.
     */
    public java.math.BigDecimal getAmount(){
        return mAmount;
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
