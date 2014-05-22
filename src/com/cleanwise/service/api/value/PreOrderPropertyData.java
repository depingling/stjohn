
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PreOrderPropertyData
 * Description:  This is a ValueObject class wrapping the database table CLW_PRE_ORDER_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.PreOrderPropertyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>PreOrderPropertyData</code> is a ValueObject class wrapping of the database table CLW_PRE_ORDER_PROPERTY.
 */
public class PreOrderPropertyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 5781061402004592946L;
    private int mPreOrderPropertyId;// SQL type:NUMBER, not null
    private int mPreOrderId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2
    private String mValue;// SQL type:VARCHAR2
    private String mOrderPropertyTypeCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public PreOrderPropertyData ()
    {
        mShortDesc = "";
        mValue = "";
        mOrderPropertyTypeCd = "";
    }

    /**
     * Constructor.
     */
    public PreOrderPropertyData(int parm1, int parm2, String parm3, String parm4, String parm5)
    {
        mPreOrderPropertyId = parm1;
        mPreOrderId = parm2;
        mShortDesc = parm3;
        mValue = parm4;
        mOrderPropertyTypeCd = parm5;
        
    }

    /**
     * Creates a new PreOrderPropertyData
     *
     * @return
     *  Newly initialized PreOrderPropertyData object.
     */
    public static PreOrderPropertyData createValue ()
    {
        PreOrderPropertyData valueData = new PreOrderPropertyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PreOrderPropertyData object
     */
    public String toString()
    {
        return "[" + "PreOrderPropertyId=" + mPreOrderPropertyId + ", PreOrderId=" + mPreOrderId + ", ShortDesc=" + mShortDesc + ", Value=" + mValue + ", OrderPropertyTypeCd=" + mOrderPropertyTypeCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("PreOrderProperty");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mPreOrderPropertyId));

        node =  doc.createElement("PreOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mPreOrderId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
        root.appendChild(node);

        node =  doc.createElement("OrderPropertyTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderPropertyTypeCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the PreOrderPropertyId field is not cloned.
    *
    * @return PreOrderPropertyData object
    */
    public Object clone(){
        PreOrderPropertyData myClone = new PreOrderPropertyData();
        
        myClone.mPreOrderId = mPreOrderId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mValue = mValue;
        
        myClone.mOrderPropertyTypeCd = mOrderPropertyTypeCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (PreOrderPropertyDataAccess.PRE_ORDER_PROPERTY_ID.equals(pFieldName)) {
            return getPreOrderPropertyId();
        } else if (PreOrderPropertyDataAccess.PRE_ORDER_ID.equals(pFieldName)) {
            return getPreOrderId();
        } else if (PreOrderPropertyDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (PreOrderPropertyDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (PreOrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD.equals(pFieldName)) {
            return getOrderPropertyTypeCd();
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
        return PreOrderPropertyDataAccess.CLW_PRE_ORDER_PROPERTY;
    }

    
    /**
     * Sets the PreOrderPropertyId field. This field is required to be set in the database.
     *
     * @param pPreOrderPropertyId
     *  int to use to update the field.
     */
    public void setPreOrderPropertyId(int pPreOrderPropertyId){
        this.mPreOrderPropertyId = pPreOrderPropertyId;
        setDirty(true);
    }
    /**
     * Retrieves the PreOrderPropertyId field.
     *
     * @return
     *  int containing the PreOrderPropertyId field.
     */
    public int getPreOrderPropertyId(){
        return mPreOrderPropertyId;
    }

    /**
     * Sets the PreOrderId field. This field is required to be set in the database.
     *
     * @param pPreOrderId
     *  int to use to update the field.
     */
    public void setPreOrderId(int pPreOrderId){
        this.mPreOrderId = pPreOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the PreOrderId field.
     *
     * @return
     *  int containing the PreOrderId field.
     */
    public int getPreOrderId(){
        return mPreOrderId;
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
     * Sets the OrderPropertyTypeCd field.
     *
     * @param pOrderPropertyTypeCd
     *  String to use to update the field.
     */
    public void setOrderPropertyTypeCd(String pOrderPropertyTypeCd){
        this.mOrderPropertyTypeCd = pOrderPropertyTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the OrderPropertyTypeCd field.
     *
     * @return
     *  String containing the OrderPropertyTypeCd field.
     */
    public String getOrderPropertyTypeCd(){
        return mOrderPropertyTypeCd;
    }


}
