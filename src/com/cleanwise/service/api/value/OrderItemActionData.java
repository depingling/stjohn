
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderItemActionData
 * Description:  This is a ValueObject class wrapping the database table CLW_ORDER_ITEM_ACTION.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.OrderItemActionDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>OrderItemActionData</code> is a ValueObject class wrapping of the database table CLW_ORDER_ITEM_ACTION.
 */
public class OrderItemActionData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -6562197550951277162L;
    private int mOrderItemActionId;// SQL type:NUMBER, not null
    private int mOrderId;// SQL type:NUMBER, not null
    private int mOrderItemId;// SQL type:NUMBER, not null
    private String mAffectedOrderNum;// SQL type:VARCHAR2
    private String mAffectedSku;// SQL type:VARCHAR2
    private int mAffectedLineItem;// SQL type:NUMBER
    private String mActionCd;// SQL type:VARCHAR2
    private int mActualTransactionId;// SQL type:NUMBER
    private String mAffectedTable;// SQL type:VARCHAR2
    private int mAffectedId;// SQL type:NUMBER
    private java.math.BigDecimal mAmount;// SQL type:NUMBER
    private int mQuantity;// SQL type:NUMBER
    private String mComments;// SQL type:VARCHAR2
    private Date mActionDate;// SQL type:DATE
    private Date mActionTime;// SQL type:DATE
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2
    private String mStatus;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public OrderItemActionData ()
    {
        mAffectedOrderNum = "";
        mAffectedSku = "";
        mActionCd = "";
        mAffectedTable = "";
        mComments = "";
        mAddBy = "";
        mModBy = "";
        mStatus = "";
    }

    /**
     * Constructor.
     */
    public OrderItemActionData(int parm1, int parm2, int parm3, String parm4, String parm5, int parm6, String parm7, int parm8, String parm9, int parm10, java.math.BigDecimal parm11, int parm12, String parm13, Date parm14, Date parm15, Date parm16, String parm17, Date parm18, String parm19, String parm20)
    {
        mOrderItemActionId = parm1;
        mOrderId = parm2;
        mOrderItemId = parm3;
        mAffectedOrderNum = parm4;
        mAffectedSku = parm5;
        mAffectedLineItem = parm6;
        mActionCd = parm7;
        mActualTransactionId = parm8;
        mAffectedTable = parm9;
        mAffectedId = parm10;
        mAmount = parm11;
        mQuantity = parm12;
        mComments = parm13;
        mActionDate = parm14;
        mActionTime = parm15;
        mAddDate = parm16;
        mAddBy = parm17;
        mModDate = parm18;
        mModBy = parm19;
        mStatus = parm20;
        
    }

    /**
     * Creates a new OrderItemActionData
     *
     * @return
     *  Newly initialized OrderItemActionData object.
     */
    public static OrderItemActionData createValue ()
    {
        OrderItemActionData valueData = new OrderItemActionData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderItemActionData object
     */
    public String toString()
    {
        return "[" + "OrderItemActionId=" + mOrderItemActionId + ", OrderId=" + mOrderId + ", OrderItemId=" + mOrderItemId + ", AffectedOrderNum=" + mAffectedOrderNum + ", AffectedSku=" + mAffectedSku + ", AffectedLineItem=" + mAffectedLineItem + ", ActionCd=" + mActionCd + ", ActualTransactionId=" + mActualTransactionId + ", AffectedTable=" + mAffectedTable + ", AffectedId=" + mAffectedId + ", Amount=" + mAmount + ", Quantity=" + mQuantity + ", Comments=" + mComments + ", ActionDate=" + mActionDate + ", ActionTime=" + mActionTime + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", Status=" + mStatus + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("OrderItemAction");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mOrderItemActionId));

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node =  doc.createElement("OrderItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderItemId)));
        root.appendChild(node);

        node =  doc.createElement("AffectedOrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mAffectedOrderNum)));
        root.appendChild(node);

        node =  doc.createElement("AffectedSku");
        node.appendChild(doc.createTextNode(String.valueOf(mAffectedSku)));
        root.appendChild(node);

        node =  doc.createElement("AffectedLineItem");
        node.appendChild(doc.createTextNode(String.valueOf(mAffectedLineItem)));
        root.appendChild(node);

        node =  doc.createElement("ActionCd");
        node.appendChild(doc.createTextNode(String.valueOf(mActionCd)));
        root.appendChild(node);

        node =  doc.createElement("ActualTransactionId");
        node.appendChild(doc.createTextNode(String.valueOf(mActualTransactionId)));
        root.appendChild(node);

        node =  doc.createElement("AffectedTable");
        node.appendChild(doc.createTextNode(String.valueOf(mAffectedTable)));
        root.appendChild(node);

        node =  doc.createElement("AffectedId");
        node.appendChild(doc.createTextNode(String.valueOf(mAffectedId)));
        root.appendChild(node);

        node =  doc.createElement("Amount");
        node.appendChild(doc.createTextNode(String.valueOf(mAmount)));
        root.appendChild(node);

        node =  doc.createElement("Quantity");
        node.appendChild(doc.createTextNode(String.valueOf(mQuantity)));
        root.appendChild(node);

        node =  doc.createElement("Comments");
        node.appendChild(doc.createTextNode(String.valueOf(mComments)));
        root.appendChild(node);

        node =  doc.createElement("ActionDate");
        node.appendChild(doc.createTextNode(String.valueOf(mActionDate)));
        root.appendChild(node);

        node =  doc.createElement("ActionTime");
        node.appendChild(doc.createTextNode(String.valueOf(mActionTime)));
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

        node =  doc.createElement("Status");
        node.appendChild(doc.createTextNode(String.valueOf(mStatus)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the OrderItemActionId field is not cloned.
    *
    * @return OrderItemActionData object
    */
    public Object clone(){
        OrderItemActionData myClone = new OrderItemActionData();
        
        myClone.mOrderId = mOrderId;
        
        myClone.mOrderItemId = mOrderItemId;
        
        myClone.mAffectedOrderNum = mAffectedOrderNum;
        
        myClone.mAffectedSku = mAffectedSku;
        
        myClone.mAffectedLineItem = mAffectedLineItem;
        
        myClone.mActionCd = mActionCd;
        
        myClone.mActualTransactionId = mActualTransactionId;
        
        myClone.mAffectedTable = mAffectedTable;
        
        myClone.mAffectedId = mAffectedId;
        
        myClone.mAmount = mAmount;
        
        myClone.mQuantity = mQuantity;
        
        myClone.mComments = mComments;
        
        if(mActionDate != null){
                myClone.mActionDate = (Date) mActionDate.clone();
        }
        
        if(mActionTime != null){
                myClone.mActionTime = (Date) mActionTime.clone();
        }
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mStatus = mStatus;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (OrderItemActionDataAccess.ORDER_ITEM_ACTION_ID.equals(pFieldName)) {
            return getOrderItemActionId();
        } else if (OrderItemActionDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (OrderItemActionDataAccess.ORDER_ITEM_ID.equals(pFieldName)) {
            return getOrderItemId();
        } else if (OrderItemActionDataAccess.AFFECTED_ORDER_NUM.equals(pFieldName)) {
            return getAffectedOrderNum();
        } else if (OrderItemActionDataAccess.AFFECTED_SKU.equals(pFieldName)) {
            return getAffectedSku();
        } else if (OrderItemActionDataAccess.AFFECTED_LINE_ITEM.equals(pFieldName)) {
            return getAffectedLineItem();
        } else if (OrderItemActionDataAccess.ACTION_CD.equals(pFieldName)) {
            return getActionCd();
        } else if (OrderItemActionDataAccess.ACTUAL_TRANSACTION_ID.equals(pFieldName)) {
            return getActualTransactionId();
        } else if (OrderItemActionDataAccess.AFFECTED_TABLE.equals(pFieldName)) {
            return getAffectedTable();
        } else if (OrderItemActionDataAccess.AFFECTED_ID.equals(pFieldName)) {
            return getAffectedId();
        } else if (OrderItemActionDataAccess.AMOUNT.equals(pFieldName)) {
            return getAmount();
        } else if (OrderItemActionDataAccess.QUANTITY.equals(pFieldName)) {
            return getQuantity();
        } else if (OrderItemActionDataAccess.COMMENTS.equals(pFieldName)) {
            return getComments();
        } else if (OrderItemActionDataAccess.ACTION_DATE.equals(pFieldName)) {
            return getActionDate();
        } else if (OrderItemActionDataAccess.ACTION_TIME.equals(pFieldName)) {
            return getActionTime();
        } else if (OrderItemActionDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (OrderItemActionDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (OrderItemActionDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (OrderItemActionDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (OrderItemActionDataAccess.STATUS.equals(pFieldName)) {
            return getStatus();
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
        return OrderItemActionDataAccess.CLW_ORDER_ITEM_ACTION;
    }

    
    /**
     * Sets the OrderItemActionId field. This field is required to be set in the database.
     *
     * @param pOrderItemActionId
     *  int to use to update the field.
     */
    public void setOrderItemActionId(int pOrderItemActionId){
        this.mOrderItemActionId = pOrderItemActionId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderItemActionId field.
     *
     * @return
     *  int containing the OrderItemActionId field.
     */
    public int getOrderItemActionId(){
        return mOrderItemActionId;
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
     * Sets the OrderItemId field. This field is required to be set in the database.
     *
     * @param pOrderItemId
     *  int to use to update the field.
     */
    public void setOrderItemId(int pOrderItemId){
        this.mOrderItemId = pOrderItemId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderItemId field.
     *
     * @return
     *  int containing the OrderItemId field.
     */
    public int getOrderItemId(){
        return mOrderItemId;
    }

    /**
     * Sets the AffectedOrderNum field.
     *
     * @param pAffectedOrderNum
     *  String to use to update the field.
     */
    public void setAffectedOrderNum(String pAffectedOrderNum){
        this.mAffectedOrderNum = pAffectedOrderNum;
        setDirty(true);
    }
    /**
     * Retrieves the AffectedOrderNum field.
     *
     * @return
     *  String containing the AffectedOrderNum field.
     */
    public String getAffectedOrderNum(){
        return mAffectedOrderNum;
    }

    /**
     * Sets the AffectedSku field.
     *
     * @param pAffectedSku
     *  String to use to update the field.
     */
    public void setAffectedSku(String pAffectedSku){
        this.mAffectedSku = pAffectedSku;
        setDirty(true);
    }
    /**
     * Retrieves the AffectedSku field.
     *
     * @return
     *  String containing the AffectedSku field.
     */
    public String getAffectedSku(){
        return mAffectedSku;
    }

    /**
     * Sets the AffectedLineItem field.
     *
     * @param pAffectedLineItem
     *  int to use to update the field.
     */
    public void setAffectedLineItem(int pAffectedLineItem){
        this.mAffectedLineItem = pAffectedLineItem;
        setDirty(true);
    }
    /**
     * Retrieves the AffectedLineItem field.
     *
     * @return
     *  int containing the AffectedLineItem field.
     */
    public int getAffectedLineItem(){
        return mAffectedLineItem;
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

    /**
     * Sets the ActualTransactionId field.
     *
     * @param pActualTransactionId
     *  int to use to update the field.
     */
    public void setActualTransactionId(int pActualTransactionId){
        this.mActualTransactionId = pActualTransactionId;
        setDirty(true);
    }
    /**
     * Retrieves the ActualTransactionId field.
     *
     * @return
     *  int containing the ActualTransactionId field.
     */
    public int getActualTransactionId(){
        return mActualTransactionId;
    }

    /**
     * Sets the AffectedTable field.
     *
     * @param pAffectedTable
     *  String to use to update the field.
     */
    public void setAffectedTable(String pAffectedTable){
        this.mAffectedTable = pAffectedTable;
        setDirty(true);
    }
    /**
     * Retrieves the AffectedTable field.
     *
     * @return
     *  String containing the AffectedTable field.
     */
    public String getAffectedTable(){
        return mAffectedTable;
    }

    /**
     * Sets the AffectedId field.
     *
     * @param pAffectedId
     *  int to use to update the field.
     */
    public void setAffectedId(int pAffectedId){
        this.mAffectedId = pAffectedId;
        setDirty(true);
    }
    /**
     * Retrieves the AffectedId field.
     *
     * @return
     *  int containing the AffectedId field.
     */
    public int getAffectedId(){
        return mAffectedId;
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
     * Sets the Quantity field.
     *
     * @param pQuantity
     *  int to use to update the field.
     */
    public void setQuantity(int pQuantity){
        this.mQuantity = pQuantity;
        setDirty(true);
    }
    /**
     * Retrieves the Quantity field.
     *
     * @return
     *  int containing the Quantity field.
     */
    public int getQuantity(){
        return mQuantity;
    }

    /**
     * Sets the Comments field.
     *
     * @param pComments
     *  String to use to update the field.
     */
    public void setComments(String pComments){
        this.mComments = pComments;
        setDirty(true);
    }
    /**
     * Retrieves the Comments field.
     *
     * @return
     *  String containing the Comments field.
     */
    public String getComments(){
        return mComments;
    }

    /**
     * Sets the ActionDate field.
     *
     * @param pActionDate
     *  Date to use to update the field.
     */
    public void setActionDate(Date pActionDate){
        this.mActionDate = pActionDate;
        setDirty(true);
    }
    /**
     * Retrieves the ActionDate field.
     *
     * @return
     *  Date containing the ActionDate field.
     */
    public Date getActionDate(){
        return mActionDate;
    }

    /**
     * Sets the ActionTime field.
     *
     * @param pActionTime
     *  Date to use to update the field.
     */
    public void setActionTime(Date pActionTime){
        this.mActionTime = pActionTime;
        setDirty(true);
    }
    /**
     * Retrieves the ActionTime field.
     *
     * @return
     *  Date containing the ActionTime field.
     */
    public Date getActionTime(){
        return mActionTime;
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
     * Sets the Status field.
     *
     * @param pStatus
     *  String to use to update the field.
     */
    public void setStatus(String pStatus){
        this.mStatus = pStatus;
        setDirty(true);
    }
    /**
     * Retrieves the Status field.
     *
     * @return
     *  String containing the Status field.
     */
    public String getStatus(){
        return mStatus;
    }


}
