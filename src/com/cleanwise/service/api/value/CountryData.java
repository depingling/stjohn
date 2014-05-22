
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CountryData
 * Description:  This is a ValueObject class wrapping the database table CLW_COUNTRY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.CountryDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>CountryData</code> is a ValueObject class wrapping of the database table CLW_COUNTRY.
 */
public class CountryData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 8754859292688253066L;
    private int mCountryId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mUiName;// SQL type:VARCHAR2, not null
    private String mCountryCode;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mLocaleCd;// SQL type:VARCHAR2
    private String mInputDateFormat;// SQL type:VARCHAR2
    private String mInputTimeFormat;// SQL type:VARCHAR2
    private String mInputDayMonthFormat;// SQL type:VARCHAR2
    private String mAddressFormat;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public CountryData ()
    {
        mShortDesc = "";
        mUiName = "";
        mCountryCode = "";
        mAddBy = "";
        mModBy = "";
        mLocaleCd = "";
        mInputDateFormat = "";
        mInputTimeFormat = "";
        mInputDayMonthFormat = "";
        mAddressFormat = "";
    }

    /**
     * Constructor.
     */
    public CountryData(int parm1, String parm2, String parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13)
    {
        mCountryId = parm1;
        mShortDesc = parm2;
        mUiName = parm3;
        mCountryCode = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        mLocaleCd = parm9;
        mInputDateFormat = parm10;
        mInputTimeFormat = parm11;
        mInputDayMonthFormat = parm12;
        mAddressFormat = parm13;
        
    }

    /**
     * Creates a new CountryData
     *
     * @return
     *  Newly initialized CountryData object.
     */
    public static CountryData createValue ()
    {
        CountryData valueData = new CountryData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CountryData object
     */
    public String toString()
    {
        return "[" + "CountryId=" + mCountryId + ", ShortDesc=" + mShortDesc + ", UiName=" + mUiName + ", CountryCode=" + mCountryCode + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", LocaleCd=" + mLocaleCd + ", InputDateFormat=" + mInputDateFormat + ", InputTimeFormat=" + mInputTimeFormat + ", InputDayMonthFormat=" + mInputDayMonthFormat + ", AddressFormat=" + mAddressFormat + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Country");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mCountryId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("UiName");
        node.appendChild(doc.createTextNode(String.valueOf(mUiName)));
        root.appendChild(node);

        node =  doc.createElement("CountryCode");
        node.appendChild(doc.createTextNode(String.valueOf(mCountryCode)));
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

        node =  doc.createElement("LocaleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mLocaleCd)));
        root.appendChild(node);

        node =  doc.createElement("InputDateFormat");
        node.appendChild(doc.createTextNode(String.valueOf(mInputDateFormat)));
        root.appendChild(node);

        node =  doc.createElement("InputTimeFormat");
        node.appendChild(doc.createTextNode(String.valueOf(mInputTimeFormat)));
        root.appendChild(node);

        node =  doc.createElement("InputDayMonthFormat");
        node.appendChild(doc.createTextNode(String.valueOf(mInputDayMonthFormat)));
        root.appendChild(node);

        node =  doc.createElement("AddressFormat");
        node.appendChild(doc.createTextNode(String.valueOf(mAddressFormat)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the CountryId field is not cloned.
    *
    * @return CountryData object
    */
    public Object clone(){
        CountryData myClone = new CountryData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mUiName = mUiName;
        
        myClone.mCountryCode = mCountryCode;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mLocaleCd = mLocaleCd;
        
        myClone.mInputDateFormat = mInputDateFormat;
        
        myClone.mInputTimeFormat = mInputTimeFormat;
        
        myClone.mInputDayMonthFormat = mInputDayMonthFormat;
        
        myClone.mAddressFormat = mAddressFormat;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (CountryDataAccess.COUNTRY_ID.equals(pFieldName)) {
            return getCountryId();
        } else if (CountryDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (CountryDataAccess.UI_NAME.equals(pFieldName)) {
            return getUiName();
        } else if (CountryDataAccess.COUNTRY_CODE.equals(pFieldName)) {
            return getCountryCode();
        } else if (CountryDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (CountryDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (CountryDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (CountryDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (CountryDataAccess.LOCALE_CD.equals(pFieldName)) {
            return getLocaleCd();
        } else if (CountryDataAccess.INPUT_DATE_FORMAT.equals(pFieldName)) {
            return getInputDateFormat();
        } else if (CountryDataAccess.INPUT_TIME_FORMAT.equals(pFieldName)) {
            return getInputTimeFormat();
        } else if (CountryDataAccess.INPUT_DAY_MONTH_FORMAT.equals(pFieldName)) {
            return getInputDayMonthFormat();
        } else if (CountryDataAccess.ADDRESS_FORMAT.equals(pFieldName)) {
            return getAddressFormat();
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
        return CountryDataAccess.CLW_COUNTRY;
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
     * Sets the UiName field. This field is required to be set in the database.
     *
     * @param pUiName
     *  String to use to update the field.
     */
    public void setUiName(String pUiName){
        this.mUiName = pUiName;
        setDirty(true);
    }
    /**
     * Retrieves the UiName field.
     *
     * @return
     *  String containing the UiName field.
     */
    public String getUiName(){
        return mUiName;
    }

    /**
     * Sets the CountryCode field. This field is required to be set in the database.
     *
     * @param pCountryCode
     *  String to use to update the field.
     */
    public void setCountryCode(String pCountryCode){
        this.mCountryCode = pCountryCode;
        setDirty(true);
    }
    /**
     * Retrieves the CountryCode field.
     *
     * @return
     *  String containing the CountryCode field.
     */
    public String getCountryCode(){
        return mCountryCode;
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
     * Sets the LocaleCd field.
     *
     * @param pLocaleCd
     *  String to use to update the field.
     */
    public void setLocaleCd(String pLocaleCd){
        this.mLocaleCd = pLocaleCd;
        setDirty(true);
    }
    /**
     * Retrieves the LocaleCd field.
     *
     * @return
     *  String containing the LocaleCd field.
     */
    public String getLocaleCd(){
        return mLocaleCd;
    }

    /**
     * Sets the InputDateFormat field.
     *
     * @param pInputDateFormat
     *  String to use to update the field.
     */
    public void setInputDateFormat(String pInputDateFormat){
        this.mInputDateFormat = pInputDateFormat;
        setDirty(true);
    }
    /**
     * Retrieves the InputDateFormat field.
     *
     * @return
     *  String containing the InputDateFormat field.
     */
    public String getInputDateFormat(){
        return mInputDateFormat;
    }

    /**
     * Sets the InputTimeFormat field.
     *
     * @param pInputTimeFormat
     *  String to use to update the field.
     */
    public void setInputTimeFormat(String pInputTimeFormat){
        this.mInputTimeFormat = pInputTimeFormat;
        setDirty(true);
    }
    /**
     * Retrieves the InputTimeFormat field.
     *
     * @return
     *  String containing the InputTimeFormat field.
     */
    public String getInputTimeFormat(){
        return mInputTimeFormat;
    }

    /**
     * Sets the InputDayMonthFormat field.
     *
     * @param pInputDayMonthFormat
     *  String to use to update the field.
     */
    public void setInputDayMonthFormat(String pInputDayMonthFormat){
        this.mInputDayMonthFormat = pInputDayMonthFormat;
        setDirty(true);
    }
    /**
     * Retrieves the InputDayMonthFormat field.
     *
     * @return
     *  String containing the InputDayMonthFormat field.
     */
    public String getInputDayMonthFormat(){
        return mInputDayMonthFormat;
    }

    /**
     * Sets the AddressFormat field.
     *
     * @param pAddressFormat
     *  String to use to update the field.
     */
    public void setAddressFormat(String pAddressFormat){
        this.mAddressFormat = pAddressFormat;
        setDirty(true);
    }
    /**
     * Retrieves the AddressFormat field.
     *
     * @return
     *  String containing the AddressFormat field.
     */
    public String getAddressFormat(){
        return mAddressFormat;
    }


}
