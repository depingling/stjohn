
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderProcessingData
 * Description:  This is a ValueObject class wrapping the database table CLW_ORDER_PROCESSING.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.OrderProcessingDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>OrderProcessingData</code> is a ValueObject class wrapping of the database table CLW_ORDER_PROCESSING.
 */
public class OrderProcessingData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 6858364058185425038L;
    private int mOrderProcessingId;// SQL type:NUMBER, not null
    private int mOrderId;// SQL type:NUMBER, not null
    private int mOrderItemId;// SQL type:NUMBER, not null
    private int mInvoiceId;// SQL type:NUMBER
    private Date mOrderProcessingDate;// SQL type:DATE
    private Date mOrderProcessingTime;// SQL type:DATE
    private int mActualTransactionId;// SQL type:NUMBER
    private String mTransactionCd;// SQL type:VARCHAR2
    private String mOrderProcessingStatusCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public OrderProcessingData ()
    {
        mTransactionCd = "";
        mOrderProcessingStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public OrderProcessingData(int parm1, int parm2, int parm3, int parm4, Date parm5, Date parm6, int parm7, String parm8, String parm9, Date parm10, String parm11, Date parm12, String parm13)
    {
        mOrderProcessingId = parm1;
        mOrderId = parm2;
        mOrderItemId = parm3;
        mInvoiceId = parm4;
        mOrderProcessingDate = parm5;
        mOrderProcessingTime = parm6;
        mActualTransactionId = parm7;
        mTransactionCd = parm8;
        mOrderProcessingStatusCd = parm9;
        mAddDate = parm10;
        mAddBy = parm11;
        mModDate = parm12;
        mModBy = parm13;
        
    }

    /**
     * Creates a new OrderProcessingData
     *
     * @return
     *  Newly initialized OrderProcessingData object.
     */
    public static OrderProcessingData createValue ()
    {
        OrderProcessingData valueData = new OrderProcessingData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderProcessingData object
     */
    public String toString()
    {
        return "[" + "OrderProcessingId=" + mOrderProcessingId + ", OrderId=" + mOrderId + ", OrderItemId=" + mOrderItemId + ", InvoiceId=" + mInvoiceId + ", OrderProcessingDate=" + mOrderProcessingDate + ", OrderProcessingTime=" + mOrderProcessingTime + ", ActualTransactionId=" + mActualTransactionId + ", TransactionCd=" + mTransactionCd + ", OrderProcessingStatusCd=" + mOrderProcessingStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("OrderProcessing");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mOrderProcessingId));

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node =  doc.createElement("OrderItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderItemId)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceId");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceId)));
        root.appendChild(node);

        node =  doc.createElement("OrderProcessingDate");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderProcessingDate)));
        root.appendChild(node);

        node =  doc.createElement("OrderProcessingTime");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderProcessingTime)));
        root.appendChild(node);

        node =  doc.createElement("ActualTransactionId");
        node.appendChild(doc.createTextNode(String.valueOf(mActualTransactionId)));
        root.appendChild(node);

        node =  doc.createElement("TransactionCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTransactionCd)));
        root.appendChild(node);

        node =  doc.createElement("OrderProcessingStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderProcessingStatusCd)));
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
    * creates a clone of this object, the OrderProcessingId field is not cloned.
    *
    * @return OrderProcessingData object
    */
    public Object clone(){
        OrderProcessingData myClone = new OrderProcessingData();
        
        myClone.mOrderId = mOrderId;
        
        myClone.mOrderItemId = mOrderItemId;
        
        myClone.mInvoiceId = mInvoiceId;
        
        if(mOrderProcessingDate != null){
                myClone.mOrderProcessingDate = (Date) mOrderProcessingDate.clone();
        }
        
        if(mOrderProcessingTime != null){
                myClone.mOrderProcessingTime = (Date) mOrderProcessingTime.clone();
        }
        
        myClone.mActualTransactionId = mActualTransactionId;
        
        myClone.mTransactionCd = mTransactionCd;
        
        myClone.mOrderProcessingStatusCd = mOrderProcessingStatusCd;
        
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

        if (OrderProcessingDataAccess.ORDER_PROCESSING_ID.equals(pFieldName)) {
            return getOrderProcessingId();
        } else if (OrderProcessingDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (OrderProcessingDataAccess.ORDER_ITEM_ID.equals(pFieldName)) {
            return getOrderItemId();
        } else if (OrderProcessingDataAccess.INVOICE_ID.equals(pFieldName)) {
            return getInvoiceId();
        } else if (OrderProcessingDataAccess.ORDER_PROCESSING_DATE.equals(pFieldName)) {
            return getOrderProcessingDate();
        } else if (OrderProcessingDataAccess.ORDER_PROCESSING_TIME.equals(pFieldName)) {
            return getOrderProcessingTime();
        } else if (OrderProcessingDataAccess.ACTUAL_TRANSACTION_ID.equals(pFieldName)) {
            return getActualTransactionId();
        } else if (OrderProcessingDataAccess.TRANSACTION_CD.equals(pFieldName)) {
            return getTransactionCd();
        } else if (OrderProcessingDataAccess.ORDER_PROCESSING_STATUS_CD.equals(pFieldName)) {
            return getOrderProcessingStatusCd();
        } else if (OrderProcessingDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (OrderProcessingDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (OrderProcessingDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (OrderProcessingDataAccess.MOD_BY.equals(pFieldName)) {
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
        return OrderProcessingDataAccess.CLW_ORDER_PROCESSING;
    }

    
    /**
     * Sets the OrderProcessingId field. This field is required to be set in the database.
     *
     * @param pOrderProcessingId
     *  int to use to update the field.
     */
    public void setOrderProcessingId(int pOrderProcessingId){
        this.mOrderProcessingId = pOrderProcessingId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderProcessingId field.
     *
     * @return
     *  int containing the OrderProcessingId field.
     */
    public int getOrderProcessingId(){
        return mOrderProcessingId;
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
     * Sets the InvoiceId field.
     *
     * @param pInvoiceId
     *  int to use to update the field.
     */
    public void setInvoiceId(int pInvoiceId){
        this.mInvoiceId = pInvoiceId;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceId field.
     *
     * @return
     *  int containing the InvoiceId field.
     */
    public int getInvoiceId(){
        return mInvoiceId;
    }

    /**
     * Sets the OrderProcessingDate field.
     *
     * @param pOrderProcessingDate
     *  Date to use to update the field.
     */
    public void setOrderProcessingDate(Date pOrderProcessingDate){
        this.mOrderProcessingDate = pOrderProcessingDate;
        setDirty(true);
    }
    /**
     * Retrieves the OrderProcessingDate field.
     *
     * @return
     *  Date containing the OrderProcessingDate field.
     */
    public Date getOrderProcessingDate(){
        return mOrderProcessingDate;
    }

    /**
     * Sets the OrderProcessingTime field.
     *
     * @param pOrderProcessingTime
     *  Date to use to update the field.
     */
    public void setOrderProcessingTime(Date pOrderProcessingTime){
        this.mOrderProcessingTime = pOrderProcessingTime;
        setDirty(true);
    }
    /**
     * Retrieves the OrderProcessingTime field.
     *
     * @return
     *  Date containing the OrderProcessingTime field.
     */
    public Date getOrderProcessingTime(){
        return mOrderProcessingTime;
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
     * Sets the TransactionCd field.
     *
     * @param pTransactionCd
     *  String to use to update the field.
     */
    public void setTransactionCd(String pTransactionCd){
        this.mTransactionCd = pTransactionCd;
        setDirty(true);
    }
    /**
     * Retrieves the TransactionCd field.
     *
     * @return
     *  String containing the TransactionCd field.
     */
    public String getTransactionCd(){
        return mTransactionCd;
    }

    /**
     * Sets the OrderProcessingStatusCd field.
     *
     * @param pOrderProcessingStatusCd
     *  String to use to update the field.
     */
    public void setOrderProcessingStatusCd(String pOrderProcessingStatusCd){
        this.mOrderProcessingStatusCd = pOrderProcessingStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the OrderProcessingStatusCd field.
     *
     * @return
     *  String containing the OrderProcessingStatusCd field.
     */
    public String getOrderProcessingStatusCd(){
        return mOrderProcessingStatusCd;
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


}
