
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ShoppingOptionsData
 * Description:  This is a ValueObject class wrapping the database table CLW_SHOPPING_OPTIONS.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ShoppingOptionsDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ShoppingOptionsData</code> is a ValueObject class wrapping of the database table CLW_SHOPPING_OPTIONS.
 */
public class ShoppingOptionsData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -5924544900124789715L;
    private int mShoppingOptionsId;// SQL type:NUMBER, not null
    private int mAccountId;// SQL type:NUMBER
    private String mFieldName;// SQL type:VARCHAR2
    private String mFieldValue;// SQL type:VARCHAR2
    private String mOptionValue;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ShoppingOptionsData ()
    {
        mFieldName = "";
        mFieldValue = "";
        mOptionValue = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ShoppingOptionsData(int parm1, int parm2, String parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mShoppingOptionsId = parm1;
        mAccountId = parm2;
        mFieldName = parm3;
        mFieldValue = parm4;
        mOptionValue = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new ShoppingOptionsData
     *
     * @return
     *  Newly initialized ShoppingOptionsData object.
     */
    public static ShoppingOptionsData createValue ()
    {
        ShoppingOptionsData valueData = new ShoppingOptionsData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ShoppingOptionsData object
     */
    public String toString()
    {
        return "[" + "ShoppingOptionsId=" + mShoppingOptionsId + ", AccountId=" + mAccountId + ", FieldName=" + mFieldName + ", FieldValue=" + mFieldValue + ", OptionValue=" + mOptionValue + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ShoppingOptions");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mShoppingOptionsId));

        node =  doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node =  doc.createElement("FieldName");
        node.appendChild(doc.createTextNode(String.valueOf(mFieldName)));
        root.appendChild(node);

        node =  doc.createElement("FieldValue");
        node.appendChild(doc.createTextNode(String.valueOf(mFieldValue)));
        root.appendChild(node);

        node =  doc.createElement("OptionValue");
        node.appendChild(doc.createTextNode(String.valueOf(mOptionValue)));
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
    * creates a clone of this object, the ShoppingOptionsId field is not cloned.
    *
    * @return ShoppingOptionsData object
    */
    public Object clone(){
        ShoppingOptionsData myClone = new ShoppingOptionsData();
        
        myClone.mAccountId = mAccountId;
        
        myClone.mFieldName = mFieldName;
        
        myClone.mFieldValue = mFieldValue;
        
        myClone.mOptionValue = mOptionValue;
        
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

        if (ShoppingOptionsDataAccess.SHOPPING_OPTIONS_ID.equals(pFieldName)) {
            return getShoppingOptionsId();
        } else if (ShoppingOptionsDataAccess.ACCOUNT_ID.equals(pFieldName)) {
            return getAccountId();
        } else if (ShoppingOptionsDataAccess.FIELD_NAME.equals(pFieldName)) {
            return getFieldName();
        } else if (ShoppingOptionsDataAccess.FIELD_VALUE.equals(pFieldName)) {
            return getFieldValue();
        } else if (ShoppingOptionsDataAccess.OPTION_VALUE.equals(pFieldName)) {
            return getOptionValue();
        } else if (ShoppingOptionsDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ShoppingOptionsDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ShoppingOptionsDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ShoppingOptionsDataAccess.MOD_BY.equals(pFieldName)) {
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
        return ShoppingOptionsDataAccess.CLW_SHOPPING_OPTIONS;
    }

    
    /**
     * Sets the ShoppingOptionsId field. This field is required to be set in the database.
     *
     * @param pShoppingOptionsId
     *  int to use to update the field.
     */
    public void setShoppingOptionsId(int pShoppingOptionsId){
        this.mShoppingOptionsId = pShoppingOptionsId;
        setDirty(true);
    }
    /**
     * Retrieves the ShoppingOptionsId field.
     *
     * @return
     *  int containing the ShoppingOptionsId field.
     */
    public int getShoppingOptionsId(){
        return mShoppingOptionsId;
    }

    /**
     * Sets the AccountId field.
     *
     * @param pAccountId
     *  int to use to update the field.
     */
    public void setAccountId(int pAccountId){
        this.mAccountId = pAccountId;
        setDirty(true);
    }
    /**
     * Retrieves the AccountId field.
     *
     * @return
     *  int containing the AccountId field.
     */
    public int getAccountId(){
        return mAccountId;
    }

    /**
     * Sets the FieldName field.
     *
     * @param pFieldName
     *  String to use to update the field.
     */
    public void setFieldName(String pFieldName){
        this.mFieldName = pFieldName;
        setDirty(true);
    }
    /**
     * Retrieves the FieldName field.
     *
     * @return
     *  String containing the FieldName field.
     */
    public String getFieldName(){
        return mFieldName;
    }

    /**
     * Sets the FieldValue field.
     *
     * @param pFieldValue
     *  String to use to update the field.
     */
    public void setFieldValue(String pFieldValue){
        this.mFieldValue = pFieldValue;
        setDirty(true);
    }
    /**
     * Retrieves the FieldValue field.
     *
     * @return
     *  String containing the FieldValue field.
     */
    public String getFieldValue(){
        return mFieldValue;
    }

    /**
     * Sets the OptionValue field.
     *
     * @param pOptionValue
     *  String to use to update the field.
     */
    public void setOptionValue(String pOptionValue){
        this.mOptionValue = pOptionValue;
        setDirty(true);
    }
    /**
     * Retrieves the OptionValue field.
     *
     * @return
     *  String containing the OptionValue field.
     */
    public String getOptionValue(){
        return mOptionValue;
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
