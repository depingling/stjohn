
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        LocaleData
 * Description:  This is a ValueObject class wrapping the database table CLW_LOCALE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.LocaleDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>LocaleData</code> is a ValueObject class wrapping of the database table CLW_LOCALE.
 */
public class LocaleData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -354475515463390851L;
    private int mLocaleId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mCountryCd;// SQL type:VARCHAR2, not null
    private String mLanguageCd;// SQL type:VARCHAR2, not null
    private String mCurrencyCd;// SQL type:VARCHAR2, not null
    private String mDateFormatCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public LocaleData ()
    {
        mShortDesc = "";
        mCountryCd = "";
        mLanguageCd = "";
        mCurrencyCd = "";
        mDateFormatCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public LocaleData(int parm1, String parm2, String parm3, String parm4, String parm5, String parm6, Date parm7, String parm8, Date parm9, String parm10)
    {
        mLocaleId = parm1;
        mShortDesc = parm2;
        mCountryCd = parm3;
        mLanguageCd = parm4;
        mCurrencyCd = parm5;
        mDateFormatCd = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        
    }

    /**
     * Creates a new LocaleData
     *
     * @return
     *  Newly initialized LocaleData object.
     */
    public static LocaleData createValue ()
    {
        LocaleData valueData = new LocaleData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this LocaleData object
     */
    public String toString()
    {
        return "[" + "LocaleId=" + mLocaleId + ", ShortDesc=" + mShortDesc + ", CountryCd=" + mCountryCd + ", LanguageCd=" + mLanguageCd + ", CurrencyCd=" + mCurrencyCd + ", DateFormatCd=" + mDateFormatCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Locale");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mLocaleId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("CountryCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCountryCd)));
        root.appendChild(node);

        node =  doc.createElement("LanguageCd");
        node.appendChild(doc.createTextNode(String.valueOf(mLanguageCd)));
        root.appendChild(node);

        node =  doc.createElement("CurrencyCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCurrencyCd)));
        root.appendChild(node);

        node =  doc.createElement("DateFormatCd");
        node.appendChild(doc.createTextNode(String.valueOf(mDateFormatCd)));
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
    * creates a clone of this object, the LocaleId field is not cloned.
    *
    * @return LocaleData object
    */
    public Object clone(){
        LocaleData myClone = new LocaleData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mCountryCd = mCountryCd;
        
        myClone.mLanguageCd = mLanguageCd;
        
        myClone.mCurrencyCd = mCurrencyCd;
        
        myClone.mDateFormatCd = mDateFormatCd;
        
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

        if (LocaleDataAccess.LOCALE_ID.equals(pFieldName)) {
            return getLocaleId();
        } else if (LocaleDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (LocaleDataAccess.COUNTRY_CD.equals(pFieldName)) {
            return getCountryCd();
        } else if (LocaleDataAccess.LANGUAGE_CD.equals(pFieldName)) {
            return getLanguageCd();
        } else if (LocaleDataAccess.CURRENCY_CD.equals(pFieldName)) {
            return getCurrencyCd();
        } else if (LocaleDataAccess.DATE_FORMAT_CD.equals(pFieldName)) {
            return getDateFormatCd();
        } else if (LocaleDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (LocaleDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (LocaleDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (LocaleDataAccess.MOD_BY.equals(pFieldName)) {
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
        return LocaleDataAccess.CLW_LOCALE;
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
     * Sets the CountryCd field. This field is required to be set in the database.
     *
     * @param pCountryCd
     *  String to use to update the field.
     */
    public void setCountryCd(String pCountryCd){
        this.mCountryCd = pCountryCd;
        setDirty(true);
    }
    /**
     * Retrieves the CountryCd field.
     *
     * @return
     *  String containing the CountryCd field.
     */
    public String getCountryCd(){
        return mCountryCd;
    }

    /**
     * Sets the LanguageCd field. This field is required to be set in the database.
     *
     * @param pLanguageCd
     *  String to use to update the field.
     */
    public void setLanguageCd(String pLanguageCd){
        this.mLanguageCd = pLanguageCd;
        setDirty(true);
    }
    /**
     * Retrieves the LanguageCd field.
     *
     * @return
     *  String containing the LanguageCd field.
     */
    public String getLanguageCd(){
        return mLanguageCd;
    }

    /**
     * Sets the CurrencyCd field. This field is required to be set in the database.
     *
     * @param pCurrencyCd
     *  String to use to update the field.
     */
    public void setCurrencyCd(String pCurrencyCd){
        this.mCurrencyCd = pCurrencyCd;
        setDirty(true);
    }
    /**
     * Retrieves the CurrencyCd field.
     *
     * @return
     *  String containing the CurrencyCd field.
     */
    public String getCurrencyCd(){
        return mCurrencyCd;
    }

    /**
     * Sets the DateFormatCd field. This field is required to be set in the database.
     *
     * @param pDateFormatCd
     *  String to use to update the field.
     */
    public void setDateFormatCd(String pDateFormatCd){
        this.mDateFormatCd = pDateFormatCd;
        setDirty(true);
    }
    /**
     * Retrieves the DateFormatCd field.
     *
     * @return
     *  String containing the DateFormatCd field.
     */
    public String getDateFormatCd(){
        return mDateFormatCd;
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
