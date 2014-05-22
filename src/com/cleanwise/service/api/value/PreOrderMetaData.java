
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PreOrderMetaData
 * Description:  This is a ValueObject class wrapping the database table CLW_PRE_ORDER_META.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.PreOrderMetaDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>PreOrderMetaData</code> is a ValueObject class wrapping of the database table CLW_PRE_ORDER_META.
 */
public class PreOrderMetaData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1L;
    private int mPreOrderMetaId;// SQL type:NUMBER, not null
    private int mPreOrderId;// SQL type:NUMBER, not null
    private String mName;// SQL type:VARCHAR2, not null
    private int mValueNum;// SQL type:NUMBER, not null
    private String mValue;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mPreOrderItemId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public PreOrderMetaData ()
    {
        mName = "";
        mValue = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public PreOrderMetaData(int parm1, int parm2, String parm3, int parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9, int parm10)
    {
        mPreOrderMetaId = parm1;
        mPreOrderId = parm2;
        mName = parm3;
        mValueNum = parm4;
        mValue = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        mPreOrderItemId = parm10;
        
    }

    /**
     * Creates a new PreOrderMetaData
     *
     * @return
     *  Newly initialized PreOrderMetaData object.
     */
    public static PreOrderMetaData createValue ()
    {
        PreOrderMetaData valueData = new PreOrderMetaData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PreOrderMetaData object
     */
    public String toString()
    {
        return "[" + "PreOrderMetaId=" + mPreOrderMetaId + ", PreOrderId=" + mPreOrderId + ", Name=" + mName + ", ValueNum=" + mValueNum + ", Value=" + mValue + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", PreOrderItemId=" + mPreOrderItemId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("PreOrderMeta");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mPreOrderMetaId));

        node =  doc.createElement("PreOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mPreOrderId)));
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

        node =  doc.createElement("PreOrderItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mPreOrderItemId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the PreOrderMetaId field is not cloned.
    *
    * @return PreOrderMetaData object
    */
    public Object clone(){
        PreOrderMetaData myClone = new PreOrderMetaData();
        
        myClone.mPreOrderId = mPreOrderId;
        
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
        
        myClone.mPreOrderItemId = mPreOrderItemId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (PreOrderMetaDataAccess.PRE_ORDER_META_ID.equals(pFieldName)) {
            return getPreOrderMetaId();
        } else if (PreOrderMetaDataAccess.PRE_ORDER_ID.equals(pFieldName)) {
            return getPreOrderId();
        } else if (PreOrderMetaDataAccess.NAME.equals(pFieldName)) {
            return getName();
        } else if (PreOrderMetaDataAccess.VALUE_NUM.equals(pFieldName)) {
            return getValueNum();
        } else if (PreOrderMetaDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (PreOrderMetaDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (PreOrderMetaDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (PreOrderMetaDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (PreOrderMetaDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (PreOrderMetaDataAccess.PRE_ORDER_ITEM_ID.equals(pFieldName)) {
            return getPreOrderItemId();
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
        return PreOrderMetaDataAccess.CLW_PRE_ORDER_META;
    }

    
    /**
     * Sets the PreOrderMetaId field. This field is required to be set in the database.
     *
     * @param pPreOrderMetaId
     *  int to use to update the field.
     */
    public void setPreOrderMetaId(int pPreOrderMetaId){
        this.mPreOrderMetaId = pPreOrderMetaId;
        setDirty(true);
    }
    /**
     * Retrieves the PreOrderMetaId field.
     *
     * @return
     *  int containing the PreOrderMetaId field.
     */
    public int getPreOrderMetaId(){
        return mPreOrderMetaId;
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
     * Sets the Value field. This field is required to be set in the database.
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

    /**
     * Sets the PreOrderItemId field.
     *
     * @param pPreOrderItemId
     *  int to use to update the field.
     */
    public void setPreOrderItemId(int pPreOrderItemId){
        this.mPreOrderItemId = pPreOrderItemId;
        setDirty(true);
    }
    /**
     * Retrieves the PreOrderItemId field.
     *
     * @return
     *  int containing the PreOrderItemId field.
     */
    public int getPreOrderItemId(){
        return mPreOrderItemId;
    }


}
