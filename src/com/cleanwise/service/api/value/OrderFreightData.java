
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderFreightData
 * Description:  This is a ValueObject class wrapping the database table CLW_ORDER_FREIGHT.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.OrderFreightDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>OrderFreightData</code> is a ValueObject class wrapping of the database table CLW_ORDER_FREIGHT.
 */
public class OrderFreightData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -659709230962355670L;
    private int mOrderFreightId;// SQL type:NUMBER, not null
    private int mOrderId;// SQL type:NUMBER
    private int mBusEntityId;// SQL type:NUMBER
    private String mFreightTypeCd;// SQL type:VARCHAR2
    private String mShortDesc;// SQL type:VARCHAR2
    private java.math.BigDecimal mAmount;// SQL type:NUMBER
    private int mFreightHandlerId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public OrderFreightData ()
    {
        mFreightTypeCd = "";
        mShortDesc = "";
    }

    /**
     * Constructor.
     */
    public OrderFreightData(int parm1, int parm2, int parm3, String parm4, String parm5, java.math.BigDecimal parm6, int parm7)
    {
        mOrderFreightId = parm1;
        mOrderId = parm2;
        mBusEntityId = parm3;
        mFreightTypeCd = parm4;
        mShortDesc = parm5;
        mAmount = parm6;
        mFreightHandlerId = parm7;
        
    }

    /**
     * Creates a new OrderFreightData
     *
     * @return
     *  Newly initialized OrderFreightData object.
     */
    public static OrderFreightData createValue ()
    {
        OrderFreightData valueData = new OrderFreightData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderFreightData object
     */
    public String toString()
    {
        return "[" + "OrderFreightId=" + mOrderFreightId + ", OrderId=" + mOrderId + ", BusEntityId=" + mBusEntityId + ", FreightTypeCd=" + mFreightTypeCd + ", ShortDesc=" + mShortDesc + ", Amount=" + mAmount + ", FreightHandlerId=" + mFreightHandlerId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("OrderFreight");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mOrderFreightId));

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("FreightTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("Amount");
        node.appendChild(doc.createTextNode(String.valueOf(mAmount)));
        root.appendChild(node);

        node =  doc.createElement("FreightHandlerId");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightHandlerId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the OrderFreightId field is not cloned.
    *
    * @return OrderFreightData object
    */
    public Object clone(){
        OrderFreightData myClone = new OrderFreightData();
        
        myClone.mOrderId = mOrderId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mFreightTypeCd = mFreightTypeCd;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mAmount = mAmount;
        
        myClone.mFreightHandlerId = mFreightHandlerId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (OrderFreightDataAccess.ORDER_FREIGHT_ID.equals(pFieldName)) {
            return getOrderFreightId();
        } else if (OrderFreightDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (OrderFreightDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (OrderFreightDataAccess.FREIGHT_TYPE_CD.equals(pFieldName)) {
            return getFreightTypeCd();
        } else if (OrderFreightDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (OrderFreightDataAccess.AMOUNT.equals(pFieldName)) {
            return getAmount();
        } else if (OrderFreightDataAccess.FREIGHT_HANDLER_ID.equals(pFieldName)) {
            return getFreightHandlerId();
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
        return OrderFreightDataAccess.CLW_ORDER_FREIGHT;
    }

    
    /**
     * Sets the OrderFreightId field. This field is required to be set in the database.
     *
     * @param pOrderFreightId
     *  int to use to update the field.
     */
    public void setOrderFreightId(int pOrderFreightId){
        this.mOrderFreightId = pOrderFreightId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderFreightId field.
     *
     * @return
     *  int containing the OrderFreightId field.
     */
    public int getOrderFreightId(){
        return mOrderFreightId;
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
     * Sets the FreightTypeCd field.
     *
     * @param pFreightTypeCd
     *  String to use to update the field.
     */
    public void setFreightTypeCd(String pFreightTypeCd){
        this.mFreightTypeCd = pFreightTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the FreightTypeCd field.
     *
     * @return
     *  String containing the FreightTypeCd field.
     */
    public String getFreightTypeCd(){
        return mFreightTypeCd;
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
     * Sets the FreightHandlerId field.
     *
     * @param pFreightHandlerId
     *  int to use to update the field.
     */
    public void setFreightHandlerId(int pFreightHandlerId){
        this.mFreightHandlerId = pFreightHandlerId;
        setDirty(true);
    }
    /**
     * Retrieves the FreightHandlerId field.
     *
     * @return
     *  int containing the FreightHandlerId field.
     */
    public int getFreightHandlerId(){
        return mFreightHandlerId;
    }


}
