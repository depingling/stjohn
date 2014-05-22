
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderBusEntityData
 * Description:  This is a ValueObject class wrapping the database table CLW_ORDER_BUS_ENTITY.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

import org.w3c.dom.*;

/**
 * <code>OrderBusEntityData</code> is a ValueObject class wrapping of the database table CLW_ORDER_BUS_ENTITY.
 */
public class OrderBusEntityData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 808399902640861387L;
    
    private int mOrderBusEntityId;// SQL type:NUMBER, not null
    private int mOrderId;// SQL type:NUMBER
    private int mBusEntityId;// SQL type:NUMBER
    private String mShortDesc;// SQL type:VARCHAR2
    private String mLongDesc;// SQL type:VARCHAR2
    private java.math.BigDecimal mBudget;// SQL type:NUMBER
    private java.math.BigDecimal mActualToDate;// SQL type:NUMBER 

    /**
     * Constructor.
     */
    private OrderBusEntityData ()
    {
        mShortDesc = "";
        mLongDesc = "";
    }

    /**
     * Constructor. 
     */
    public OrderBusEntityData(int parm1, int parm2, int parm3, String parm4, String parm5, java.math.BigDecimal parm6, java.math.BigDecimal parm7)
    {
        mOrderBusEntityId = parm1;
        mOrderId = parm2;
        mBusEntityId = parm3;
        mShortDesc = parm4;
        mLongDesc = parm5;
        mBudget = parm6;
        mActualToDate = parm7;
        
    }

    /**
     * Creates a new OrderBusEntityData
     *
     * @return
     *  Newly initialized OrderBusEntityData object.
     */
    public static OrderBusEntityData createValue () 
    {
        OrderBusEntityData valueData = new OrderBusEntityData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderBusEntityData object
     */
    public String toString()
    {
        return "[" + "OrderBusEntityId=" + mOrderBusEntityId + ", OrderId=" + mOrderId + ", BusEntityId=" + mBusEntityId + ", ShortDesc=" + mShortDesc + ", LongDesc=" + mLongDesc + ", Budget=" + mBudget + ", ActualToDate=" + mActualToDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("OrderBusEntity");
	root.setAttribute("Id", String.valueOf(mOrderBusEntityId));

	Element node;

        node = doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node = doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node = doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node = doc.createElement("LongDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mLongDesc)));
        root.appendChild(node);

        node = doc.createElement("Budget");
        node.appendChild(doc.createTextNode(String.valueOf(mBudget)));
        root.appendChild(node);

        node = doc.createElement("ActualToDate");
        node.appendChild(doc.createTextNode(String.valueOf(mActualToDate)));
        root.appendChild(node);

        return root;
    }
    
    /**
     * Sets the OrderBusEntityId field. This field is required to be set in the database.
     *
     * @param pOrderBusEntityId
     *  int to use to update the field.
     */
    public void setOrderBusEntityId(int pOrderBusEntityId){
        this.mOrderBusEntityId = pOrderBusEntityId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderBusEntityId field.
     *
     * @return
     *  int containing the OrderBusEntityId field.
     */
    public int getOrderBusEntityId(){
        return mOrderBusEntityId;
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
     * Sets the LongDesc field.
     *
     * @param pLongDesc
     *  String to use to update the field.
     */
    public void setLongDesc(String pLongDesc){
        this.mLongDesc = pLongDesc;
        setDirty(true);
    }
    /**
     * Retrieves the LongDesc field.
     *
     * @return
     *  String containing the LongDesc field.
     */
    public String getLongDesc(){
        return mLongDesc;
    }

    /**
     * Sets the Budget field.
     *
     * @param pBudget
     *  java.math.BigDecimal to use to update the field.
     */
    public void setBudget(java.math.BigDecimal pBudget){
        this.mBudget = pBudget;
        setDirty(true);
    }
    /**
     * Retrieves the Budget field.
     *
     * @return
     *  java.math.BigDecimal containing the Budget field.
     */
    public java.math.BigDecimal getBudget(){
        return mBudget;
    }

    /**
     * Sets the ActualToDate field.
     *
     * @param pActualToDate
     *  java.math.BigDecimal to use to update the field.
     */
    public void setActualToDate(java.math.BigDecimal pActualToDate){
        this.mActualToDate = pActualToDate;
        setDirty(true);
    }
    /**
     * Retrieves the ActualToDate field.
     *
     * @return
     *  java.math.BigDecimal containing the ActualToDate field.
     */
    public java.math.BigDecimal getActualToDate(){
        return mActualToDate;
    }

    
}
