
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderMetaData
 * Description:  This is a ValueObject class wrapping the database table CLW_ORDER_META.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.OrderMetaDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>OrderMetaData</code> is a ValueObject class wrapping of the database table CLW_ORDER_META.
 */
public class OrderMetaData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -2269124957352997998L;
    private int mOrderMetaId;// SQL type:NUMBER, not null
    private int mOrderId;// SQL type:NUMBER, not null
    private String mName;// SQL type:VARCHAR2, not null
    private int mValueNum;// SQL type:NUMBER, not null
    private String mValue;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public OrderMetaData ()
    {
        mName = "";
        mValue = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public OrderMetaData(int parm1, int parm2, String parm3, int parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mOrderMetaId = parm1;
        mOrderId = parm2;
        mName = parm3;
        mValueNum = parm4;
        mValue = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new OrderMetaData
     *
     * @return
     *  Newly initialized OrderMetaData object.
     */
    public static OrderMetaData createValue ()
    {
        OrderMetaData valueData = new OrderMetaData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderMetaData object
     */
    public String toString()
    {
        return "[" + "OrderMetaId=" + mOrderMetaId + ", OrderId=" + mOrderId + ", Name=" + mName + ", ValueNum=" + mValueNum + ", Value=" + mValue + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("OrderMeta");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mOrderMetaId));

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node =  doc.createElement("Name");
        node.appendChild(doc.createTextNode(String.valueOf(mName)));
        root.appendChild(node);

        node =  doc.createElement("ValueNum");
        node.appendChild(doc.createTextNode(String.valueOf(mValueNum)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
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
    * creates a clone of this object, the OrderMetaId field is not cloned.
    *
    * @return OrderMetaData object
    */
    public Object clone(){
        OrderMetaData myClone = new OrderMetaData();
        
        myClone.mOrderId = mOrderId;
        
        myClone.mName = mName;
        
        myClone.mValueNum = mValueNum;
        
        myClone.mValue = mValue;
        
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

        if (OrderMetaDataAccess.ORDER_META_ID.equals(pFieldName)) {
            return getOrderMetaId();
        } else if (OrderMetaDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (OrderMetaDataAccess.NAME.equals(pFieldName)) {
            return getName();
        } else if (OrderMetaDataAccess.VALUE_NUM.equals(pFieldName)) {
            return getValueNum();
        } else if (OrderMetaDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (OrderMetaDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (OrderMetaDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (OrderMetaDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (OrderMetaDataAccess.MOD_BY.equals(pFieldName)) {
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
        return OrderMetaDataAccess.CLW_ORDER_META;
    }

    
    /**
     * Sets the OrderMetaId field. This field is required to be set in the database.
     *
     * @param pOrderMetaId
     *  int to use to update the field.
     */
    public void setOrderMetaId(int pOrderMetaId){
        this.mOrderMetaId = pOrderMetaId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderMetaId field.
     *
     * @return
     *  int containing the OrderMetaId field.
     */
    public int getOrderMetaId(){
        return mOrderMetaId;
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
     * Sets the Name field. This field is required to be set in the database.
     *
     * @param pName
     *  String to use to update the field.
     */
    public void setName(String pName){
        this.mName = pName;
        setDirty(true);
    }
    /**
     * Retrieves the Name field.
     *
     * @return
     *  String containing the Name field.
     */
    public String getName(){
        return mName;
    }

    /**
     * Sets the ValueNum field. This field is required to be set in the database.
     *
     * @param pValueNum
     *  int to use to update the field.
     */
    public void setValueNum(int pValueNum){
        this.mValueNum = pValueNum;
        setDirty(true);
    }
    /**
     * Retrieves the ValueNum field.
     *
     * @return
     *  int containing the ValueNum field.
     */
    public int getValueNum(){
        return mValueNum;
    }

    /**
     * Sets the Value field.
     *
     * @param pValue
     *  String to use to update the field.
     */
    public void setValue(String pValue){
        this.mValue = pValue;
        setDirty(true);
    }
    /**
     * Retrieves the Value field.
     *
     * @return
     *  String containing the Value field.
     */
    public String getValue(){
        return mValue;
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
