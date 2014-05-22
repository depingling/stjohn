
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CountryPropertyData
 * Description:  This is a ValueObject class wrapping the database table CLW_COUNTRY_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.CountryPropertyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>CountryPropertyData</code> is a ValueObject class wrapping of the database table CLW_COUNTRY_PROPERTY.
 */
public class CountryPropertyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1652415515457096015L;
    private int mCountryPropertyId;// SQL type:NUMBER, not null
    private int mCountryId;// SQL type:NUMBER, not null
    private String mCountryPropertyCd;// SQL type:VARCHAR2, not null
    private String mValue;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public CountryPropertyData ()
    {
        mCountryPropertyCd = "";
        mValue = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public CountryPropertyData(int parm1, int parm2, String parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8)
    {
        mCountryPropertyId = parm1;
        mCountryId = parm2;
        mCountryPropertyCd = parm3;
        mValue = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        
    }

    /**
     * Creates a new CountryPropertyData
     *
     * @return
     *  Newly initialized CountryPropertyData object.
     */
    public static CountryPropertyData createValue ()
    {
        CountryPropertyData valueData = new CountryPropertyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CountryPropertyData object
     */
    public String toString()
    {
        return "[" + "CountryPropertyId=" + mCountryPropertyId + ", CountryId=" + mCountryId + ", CountryPropertyCd=" + mCountryPropertyCd + ", Value=" + mValue + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("CountryProperty");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mCountryPropertyId));

        node =  doc.createElement("CountryId");
        node.appendChild(doc.createTextNode(String.valueOf(mCountryId)));
        root.appendChild(node);

        node =  doc.createElement("CountryPropertyCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCountryPropertyCd)));
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
    * creates a clone of this object, the CountryPropertyId field is not cloned.
    *
    * @return CountryPropertyData object
    */
    public Object clone(){
        CountryPropertyData myClone = new CountryPropertyData();
        
        myClone.mCountryId = mCountryId;
        
        myClone.mCountryPropertyCd = mCountryPropertyCd;
        
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

        if (CountryPropertyDataAccess.COUNTRY_PROPERTY_ID.equals(pFieldName)) {
            return getCountryPropertyId();
        } else if (CountryPropertyDataAccess.COUNTRY_ID.equals(pFieldName)) {
            return getCountryId();
        } else if (CountryPropertyDataAccess.COUNTRY_PROPERTY_CD.equals(pFieldName)) {
            return getCountryPropertyCd();
        } else if (CountryPropertyDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (CountryPropertyDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (CountryPropertyDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (CountryPropertyDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (CountryPropertyDataAccess.MOD_BY.equals(pFieldName)) {
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
        return CountryPropertyDataAccess.CLW_COUNTRY_PROPERTY;
    }

    
    /**
     * Sets the CountryPropertyId field. This field is required to be set in the database.
     *
     * @param pCountryPropertyId
     *  int to use to update the field.
     */
    public void setCountryPropertyId(int pCountryPropertyId){
        this.mCountryPropertyId = pCountryPropertyId;
        setDirty(true);
    }
    /**
     * Retrieves the CountryPropertyId field.
     *
     * @return
     *  int containing the CountryPropertyId field.
     */
    public int getCountryPropertyId(){
        return mCountryPropertyId;
    }

    /**
     * Sets the CountryId field. This field is required to be set in the database.
     *
     * @param pCountryId
     *  int to use to update the field.
     */
    public void setCountryId(int pCountryId){
        this.mCountryId = pCountryId;
        setDirty(true);
    }
    /**
     * Retrieves the CountryId field.
     *
     * @return
     *  int containing the CountryId field.
     */
    public int getCountryId(){
        return mCountryId;
    }

    /**
     * Sets the CountryPropertyCd field. This field is required to be set in the database.
     *
     * @param pCountryPropertyCd
     *  String to use to update the field.
     */
    public void setCountryPropertyCd(String pCountryPropertyCd){
        this.mCountryPropertyCd = pCountryPropertyCd;
        setDirty(true);
    }
    /**
     * Retrieves the CountryPropertyCd field.
     *
     * @return
     *  String containing the CountryPropertyCd field.
     */
    public String getCountryPropertyCd(){
        return mCountryPropertyCd;
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
