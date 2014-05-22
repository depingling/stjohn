
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ShoppingOptConfigData
 * Description:  This is a ValueObject class wrapping the database table CLW_SHOPPING_OPT_CONFIG.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ShoppingOptConfigDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ShoppingOptConfigData</code> is a ValueObject class wrapping of the database table CLW_SHOPPING_OPT_CONFIG.
 */
public class ShoppingOptConfigData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 7149411245459858380L;
    private int mShoppingOptConfigId;// SQL type:NUMBER, not null
    private int mAccountId;// SQL type:NUMBER
    private String mFieldName;// SQL type:VARCHAR2
    private String mFieldPrompt;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ShoppingOptConfigData ()
    {
        mFieldName = "";
        mFieldPrompt = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ShoppingOptConfigData(int parm1, int parm2, String parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8)
    {
        mShoppingOptConfigId = parm1;
        mAccountId = parm2;
        mFieldName = parm3;
        mFieldPrompt = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        
    }

    /**
     * Creates a new ShoppingOptConfigData
     *
     * @return
     *  Newly initialized ShoppingOptConfigData object.
     */
    public static ShoppingOptConfigData createValue ()
    {
        ShoppingOptConfigData valueData = new ShoppingOptConfigData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ShoppingOptConfigData object
     */
    public String toString()
    {
        return "[" + "ShoppingOptConfigId=" + mShoppingOptConfigId + ", AccountId=" + mAccountId + ", FieldName=" + mFieldName + ", FieldPrompt=" + mFieldPrompt + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ShoppingOptConfig");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mShoppingOptConfigId));

        node =  doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node =  doc.createElement("FieldName");
        node.appendChild(doc.createTextNode(String.valueOf(mFieldName)));
        root.appendChild(node);

        node =  doc.createElement("FieldPrompt");
        node.appendChild(doc.createTextNode(String.valueOf(mFieldPrompt)));
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
    * creates a clone of this object, the ShoppingOptConfigId field is not cloned.
    *
    * @return ShoppingOptConfigData object
    */
    public Object clone(){
        ShoppingOptConfigData myClone = new ShoppingOptConfigData();
        
        myClone.mAccountId = mAccountId;
        
        myClone.mFieldName = mFieldName;
        
        myClone.mFieldPrompt = mFieldPrompt;
        
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

        if (ShoppingOptConfigDataAccess.SHOPPING_OPT_CONFIG_ID.equals(pFieldName)) {
            return getShoppingOptConfigId();
        } else if (ShoppingOptConfigDataAccess.ACCOUNT_ID.equals(pFieldName)) {
            return getAccountId();
        } else if (ShoppingOptConfigDataAccess.FIELD_NAME.equals(pFieldName)) {
            return getFieldName();
        } else if (ShoppingOptConfigDataAccess.FIELD_PROMPT.equals(pFieldName)) {
            return getFieldPrompt();
        } else if (ShoppingOptConfigDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ShoppingOptConfigDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ShoppingOptConfigDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ShoppingOptConfigDataAccess.MOD_BY.equals(pFieldName)) {
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
        return ShoppingOptConfigDataAccess.CLW_SHOPPING_OPT_CONFIG;
    }

    
    /**
     * Sets the ShoppingOptConfigId field. This field is required to be set in the database.
     *
     * @param pShoppingOptConfigId
     *  int to use to update the field.
     */
    public void setShoppingOptConfigId(int pShoppingOptConfigId){
        this.mShoppingOptConfigId = pShoppingOptConfigId;
        setDirty(true);
    }
    /**
     * Retrieves the ShoppingOptConfigId field.
     *
     * @return
     *  int containing the ShoppingOptConfigId field.
     */
    public int getShoppingOptConfigId(){
        return mShoppingOptConfigId;
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
     * Sets the FieldPrompt field.
     *
     * @param pFieldPrompt
     *  String to use to update the field.
     */
    public void setFieldPrompt(String pFieldPrompt){
        this.mFieldPrompt = pFieldPrompt;
        setDirty(true);
    }
    /**
     * Retrieves the FieldPrompt field.
     *
     * @return
     *  String containing the FieldPrompt field.
     */
    public String getFieldPrompt(){
        return mFieldPrompt;
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
