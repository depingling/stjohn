
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderBatchLogData
 * Description:  This is a ValueObject class wrapping the database table CLW_ORDER_BATCH_LOG.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.OrderBatchLogDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>OrderBatchLogData</code> is a ValueObject class wrapping of the database table CLW_ORDER_BATCH_LOG.
 */
public class OrderBatchLogData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -2018674600931676156L;
    private int mOrderBatchLogId;// SQL type:NUMBER, not null
    private String mOrderBatchTypeCd;// SQL type:VARCHAR2, not null
    private String mOrderBatchStatusCd;// SQL type:VARCHAR2, not null
    private int mBusEntityId;// SQL type:NUMBER
    private String mOrderNum;// SQL type:VARCHAR2
    private Date mOrderForDate;// SQL type:DATE
    private Date mOrderDate;// SQL type:DATE
    private int mOrderSourceId;// SQL type:NUMBER
    private String mMessage;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public OrderBatchLogData ()
    {
        mOrderBatchTypeCd = "";
        mOrderBatchStatusCd = "";
        mOrderNum = "";
        mMessage = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public OrderBatchLogData(int parm1, String parm2, String parm3, int parm4, String parm5, Date parm6, Date parm7, int parm8, String parm9, Date parm10, String parm11, Date parm12, String parm13)
    {
        mOrderBatchLogId = parm1;
        mOrderBatchTypeCd = parm2;
        mOrderBatchStatusCd = parm3;
        mBusEntityId = parm4;
        mOrderNum = parm5;
        mOrderForDate = parm6;
        mOrderDate = parm7;
        mOrderSourceId = parm8;
        mMessage = parm9;
        mAddDate = parm10;
        mAddBy = parm11;
        mModDate = parm12;
        mModBy = parm13;
        
    }

    /**
     * Creates a new OrderBatchLogData
     *
     * @return
     *  Newly initialized OrderBatchLogData object.
     */
    public static OrderBatchLogData createValue ()
    {
        OrderBatchLogData valueData = new OrderBatchLogData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderBatchLogData object
     */
    public String toString()
    {
        return "[" + "OrderBatchLogId=" + mOrderBatchLogId + ", OrderBatchTypeCd=" + mOrderBatchTypeCd + ", OrderBatchStatusCd=" + mOrderBatchStatusCd + ", BusEntityId=" + mBusEntityId + ", OrderNum=" + mOrderNum + ", OrderForDate=" + mOrderForDate + ", OrderDate=" + mOrderDate + ", OrderSourceId=" + mOrderSourceId + ", Message=" + mMessage + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("OrderBatchLog");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mOrderBatchLogId));

        node =  doc.createElement("OrderBatchTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderBatchTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("OrderBatchStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderBatchStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("OrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderNum)));
        root.appendChild(node);

        node =  doc.createElement("OrderForDate");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderForDate)));
        root.appendChild(node);

        node =  doc.createElement("OrderDate");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderDate)));
        root.appendChild(node);

        node =  doc.createElement("OrderSourceId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderSourceId)));
        root.appendChild(node);

        node =  doc.createElement("Message");
        node.appendChild(doc.createTextNode(String.valueOf(mMessage)));
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
    * creates a clone of this object, the OrderBatchLogId field is not cloned.
    *
    * @return OrderBatchLogData object
    */
    public Object clone(){
        OrderBatchLogData myClone = new OrderBatchLogData();
        
        myClone.mOrderBatchTypeCd = mOrderBatchTypeCd;
        
        myClone.mOrderBatchStatusCd = mOrderBatchStatusCd;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mOrderNum = mOrderNum;
        
        if(mOrderForDate != null){
                myClone.mOrderForDate = (Date) mOrderForDate.clone();
        }
        
        if(mOrderDate != null){
                myClone.mOrderDate = (Date) mOrderDate.clone();
        }
        
        myClone.mOrderSourceId = mOrderSourceId;
        
        myClone.mMessage = mMessage;
        
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

        if (OrderBatchLogDataAccess.ORDER_BATCH_LOG_ID.equals(pFieldName)) {
            return getOrderBatchLogId();
        } else if (OrderBatchLogDataAccess.ORDER_BATCH_TYPE_CD.equals(pFieldName)) {
            return getOrderBatchTypeCd();
        } else if (OrderBatchLogDataAccess.ORDER_BATCH_STATUS_CD.equals(pFieldName)) {
            return getOrderBatchStatusCd();
        } else if (OrderBatchLogDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (OrderBatchLogDataAccess.ORDER_NUM.equals(pFieldName)) {
            return getOrderNum();
        } else if (OrderBatchLogDataAccess.ORDER_FOR_DATE.equals(pFieldName)) {
            return getOrderForDate();
        } else if (OrderBatchLogDataAccess.ORDER_DATE.equals(pFieldName)) {
            return getOrderDate();
        } else if (OrderBatchLogDataAccess.ORDER_SOURCE_ID.equals(pFieldName)) {
            return getOrderSourceId();
        } else if (OrderBatchLogDataAccess.MESSAGE.equals(pFieldName)) {
            return getMessage();
        } else if (OrderBatchLogDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (OrderBatchLogDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (OrderBatchLogDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (OrderBatchLogDataAccess.MOD_BY.equals(pFieldName)) {
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
        return OrderBatchLogDataAccess.CLW_ORDER_BATCH_LOG;
    }

    
    /**
     * Sets the OrderBatchLogId field. This field is required to be set in the database.
     *
     * @param pOrderBatchLogId
     *  int to use to update the field.
     */
    public void setOrderBatchLogId(int pOrderBatchLogId){
        this.mOrderBatchLogId = pOrderBatchLogId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderBatchLogId field.
     *
     * @return
     *  int containing the OrderBatchLogId field.
     */
    public int getOrderBatchLogId(){
        return mOrderBatchLogId;
    }

    /**
     * Sets the OrderBatchTypeCd field. This field is required to be set in the database.
     *
     * @param pOrderBatchTypeCd
     *  String to use to update the field.
     */
    public void setOrderBatchTypeCd(String pOrderBatchTypeCd){
        this.mOrderBatchTypeCd = pOrderBatchTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the OrderBatchTypeCd field.
     *
     * @return
     *  String containing the OrderBatchTypeCd field.
     */
    public String getOrderBatchTypeCd(){
        return mOrderBatchTypeCd;
    }

    /**
     * Sets the OrderBatchStatusCd field. This field is required to be set in the database.
     *
     * @param pOrderBatchStatusCd
     *  String to use to update the field.
     */
    public void setOrderBatchStatusCd(String pOrderBatchStatusCd){
        this.mOrderBatchStatusCd = pOrderBatchStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the OrderBatchStatusCd field.
     *
     * @return
     *  String containing the OrderBatchStatusCd field.
     */
    public String getOrderBatchStatusCd(){
        return mOrderBatchStatusCd;
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
     * Sets the OrderForDate field.
     *
     * @param pOrderForDate
     *  Date to use to update the field.
     */
    public void setOrderForDate(Date pOrderForDate){
        this.mOrderForDate = pOrderForDate;
        setDirty(true);
    }
    /**
     * Retrieves the OrderForDate field.
     *
     * @return
     *  Date containing the OrderForDate field.
     */
    public Date getOrderForDate(){
        return mOrderForDate;
    }

    /**
     * Sets the OrderDate field.
     *
     * @param pOrderDate
     *  Date to use to update the field.
     */
    public void setOrderDate(Date pOrderDate){
        this.mOrderDate = pOrderDate;
        setDirty(true);
    }
    /**
     * Retrieves the OrderDate field.
     *
     * @return
     *  Date containing the OrderDate field.
     */
    public Date getOrderDate(){
        return mOrderDate;
    }

    /**
     * Sets the OrderSourceId field.
     *
     * @param pOrderSourceId
     *  int to use to update the field.
     */
    public void setOrderSourceId(int pOrderSourceId){
        this.mOrderSourceId = pOrderSourceId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderSourceId field.
     *
     * @return
     *  int containing the OrderSourceId field.
     */
    public int getOrderSourceId(){
        return mOrderSourceId;
    }

    /**
     * Sets the Message field.
     *
     * @param pMessage
     *  String to use to update the field.
     */
    public void setMessage(String pMessage){
        this.mMessage = pMessage;
        setDirty(true);
    }
    /**
     * Retrieves the Message field.
     *
     * @return
     *  String containing the Message field.
     */
    public String getMessage(){
        return mMessage;
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
