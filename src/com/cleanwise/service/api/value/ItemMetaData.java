
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ItemMetaData
 * Description:  This is a ValueObject class wrapping the database table CLW_ITEM_META.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ItemMetaDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ItemMetaData</code> is a ValueObject class wrapping of the database table CLW_ITEM_META.
 */
public class ItemMetaData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -6954507472036786098L;
    private int mItemMetaId;// SQL type:NUMBER, not null
    private int mItemId;// SQL type:NUMBER, not null
    private int mValueId;// SQL type:NUMBER
    private String mNameValue;// SQL type:VARCHAR2, not null
    private String mValue;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ItemMetaData ()
    {
        mNameValue = "";
        mValue = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ItemMetaData(int parm1, int parm2, int parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mItemMetaId = parm1;
        mItemId = parm2;
        mValueId = parm3;
        mNameValue = parm4;
        mValue = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new ItemMetaData
     *
     * @return
     *  Newly initialized ItemMetaData object.
     */
    public static ItemMetaData createValue ()
    {
        ItemMetaData valueData = new ItemMetaData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ItemMetaData object
     */
    public String toString()
    {
        return "[" + "ItemMetaId=" + mItemMetaId + ", ItemId=" + mItemId + ", ValueId=" + mValueId + ", NameValue=" + mNameValue + ", Value=" + mValue + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ItemMeta");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mItemMetaId));

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("ValueId");
        node.appendChild(doc.createTextNode(String.valueOf(mValueId)));
        root.appendChild(node);

        node =  doc.createElement("NameValue");
        node.appendChild(doc.createTextNode(String.valueOf(mNameValue)));
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
    * creates a clone of this object, the ItemMetaId field is not cloned.
    *
    * @return ItemMetaData object
    */
    public Object clone(){
        ItemMetaData myClone = new ItemMetaData();
        
        myClone.mItemId = mItemId;
        
        myClone.mValueId = mValueId;
        
        myClone.mNameValue = mNameValue;
        
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

        if (ItemMetaDataAccess.ITEM_META_ID.equals(pFieldName)) {
            return getItemMetaId();
        } else if (ItemMetaDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (ItemMetaDataAccess.VALUE_ID.equals(pFieldName)) {
            return getValueId();
        } else if (ItemMetaDataAccess.NAME_VALUE.equals(pFieldName)) {
            return getNameValue();
        } else if (ItemMetaDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (ItemMetaDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ItemMetaDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ItemMetaDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ItemMetaDataAccess.MOD_BY.equals(pFieldName)) {
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
        return ItemMetaDataAccess.CLW_ITEM_META;
    }

    
    /**
     * Sets the ItemMetaId field. This field is required to be set in the database.
     *
     * @param pItemMetaId
     *  int to use to update the field.
     */
    public void setItemMetaId(int pItemMetaId){
        this.mItemMetaId = pItemMetaId;
        setDirty(true);
    }
    /**
     * Retrieves the ItemMetaId field.
     *
     * @return
     *  int containing the ItemMetaId field.
     */
    public int getItemMetaId(){
        return mItemMetaId;
    }

    /**
     * Sets the ItemId field. This field is required to be set in the database.
     *
     * @param pItemId
     *  int to use to update the field.
     */
    public void setItemId(int pItemId){
        this.mItemId = pItemId;
        setDirty(true);
    }
    /**
     * Retrieves the ItemId field.
     *
     * @return
     *  int containing the ItemId field.
     */
    public int getItemId(){
        return mItemId;
    }

    /**
     * Sets the ValueId field.
     *
     * @param pValueId
     *  int to use to update the field.
     */
    public void setValueId(int pValueId){
        this.mValueId = pValueId;
        setDirty(true);
    }
    /**
     * Retrieves the ValueId field.
     *
     * @return
     *  int containing the ValueId field.
     */
    public int getValueId(){
        return mValueId;
    }

    /**
     * Sets the NameValue field. This field is required to be set in the database.
     *
     * @param pNameValue
     *  String to use to update the field.
     */
    public void setNameValue(String pNameValue){
        this.mNameValue = pNameValue;
        setDirty(true);
    }
    /**
     * Retrieves the NameValue field.
     *
     * @return
     *  String containing the NameValue field.
     */
    public String getNameValue(){
        return mNameValue;
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


}
