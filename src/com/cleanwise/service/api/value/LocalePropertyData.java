
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        LocalePropertyData
 * Description:  This is a ValueObject class wrapping the database table CLW_LOCALE_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.LocalePropertyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>LocalePropertyData</code> is a ValueObject class wrapping of the database table CLW_LOCALE_PROPERTY.
 */
public class LocalePropertyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -630121982563110815L;
    private int mLocalePropertyId;// SQL type:NUMBER, not null
    private int mLocaleId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mValue;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public LocalePropertyData ()
    {
        mShortDesc = "";
        mValue = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public LocalePropertyData(int parm1, int parm2, String parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8)
    {
        mLocalePropertyId = parm1;
        mLocaleId = parm2;
        mShortDesc = parm3;
        mValue = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        
    }

    /**
     * Creates a new LocalePropertyData
     *
     * @return
     *  Newly initialized LocalePropertyData object.
     */
    public static LocalePropertyData createValue ()
    {
        LocalePropertyData valueData = new LocalePropertyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this LocalePropertyData object
     */
    public String toString()
    {
        return "[" + "LocalePropertyId=" + mLocalePropertyId + ", LocaleId=" + mLocaleId + ", ShortDesc=" + mShortDesc + ", Value=" + mValue + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("LocaleProperty");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mLocalePropertyId));

        node =  doc.createElement("LocaleId");
        node.appendChild(doc.createTextNode(String.valueOf(mLocaleId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
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
    * creates a clone of this object, the LocalePropertyId field is not cloned.
    *
    * @return LocalePropertyData object
    */
    public Object clone(){
        LocalePropertyData myClone = new LocalePropertyData();
        
        myClone.mLocaleId = mLocaleId;
        
        myClone.mShortDesc = mShortDesc;
        
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

        if (LocalePropertyDataAccess.LOCALE_PROPERTY_ID.equals(pFieldName)) {
            return getLocalePropertyId();
        } else if (LocalePropertyDataAccess.LOCALE_ID.equals(pFieldName)) {
            return getLocaleId();
        } else if (LocalePropertyDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (LocalePropertyDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (LocalePropertyDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (LocalePropertyDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (LocalePropertyDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (LocalePropertyDataAccess.MOD_BY.equals(pFieldName)) {
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
        return LocalePropertyDataAccess.CLW_LOCALE_PROPERTY;
    }

    
    /**
     * Sets the LocalePropertyId field. This field is required to be set in the database.
     *
     * @param pLocalePropertyId
     *  int to use to update the field.
     */
    public void setLocalePropertyId(int pLocalePropertyId){
        this.mLocalePropertyId = pLocalePropertyId;
        setDirty(true);
    }
    /**
     * Retrieves the LocalePropertyId field.
     *
     * @return
     *  int containing the LocalePropertyId field.
     */
    public int getLocalePropertyId(){
        return mLocalePropertyId;
    }

    /**
     * Sets the LocaleId field. This field is required to be set in the database.
     *
     * @param pLocaleId
     *  int to use to update the field.
     */
    public void setLocaleId(int pLocaleId){
        this.mLocaleId = pLocaleId;
        setDirty(true);
    }
    /**
     * Retrieves the LocaleId field.
     *
     * @return
     *  int containing the LocaleId field.
     */
    public int getLocaleId(){
        return mLocaleId;
    }

    /**
     * Sets the ShortDesc field. This field is required to be set in the database.
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
