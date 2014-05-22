
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CityPostalCodeData
 * Description:  This is a ValueObject class wrapping the database table CLW_CITY_POSTAL_CODE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.CityPostalCodeDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>CityPostalCodeData</code> is a ValueObject class wrapping of the database table CLW_CITY_POSTAL_CODE.
 */
public class CityPostalCodeData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -8569425874542301336L;
    private String mCity;// SQL type:VARCHAR2, not null
    private String mStateProvinceCd;// SQL type:VARCHAR2, not null
    private String mStateProvinceNam;// SQL type:VARCHAR2, not null
    private String mPostalCode;// SQL type:VARCHAR2, not null
    private String mCountyCd;// SQL type:VARCHAR2
    private String mCountryCd;// SQL type:VARCHAR2
    private String mEntryType;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mCityPostalCodeId;// SQL type:NUMBER, not null
    private java.math.BigDecimal mTaxRate;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public CityPostalCodeData ()
    {
        mCity = "";
        mStateProvinceCd = "";
        mStateProvinceNam = "";
        mPostalCode = "";
        mCountyCd = "";
        mCountryCd = "";
        mEntryType = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public CityPostalCodeData(String parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, Date parm8, String parm9, Date parm10, String parm11, int parm12, java.math.BigDecimal parm13)
    {
        mCity = parm1;
        mStateProvinceCd = parm2;
        mStateProvinceNam = parm3;
        mPostalCode = parm4;
        mCountyCd = parm5;
        mCountryCd = parm6;
        mEntryType = parm7;
        mAddDate = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        mCityPostalCodeId = parm12;
        mTaxRate = parm13;
        
    }

    /**
     * Creates a new CityPostalCodeData
     *
     * @return
     *  Newly initialized CityPostalCodeData object.
     */
    public static CityPostalCodeData createValue ()
    {
        CityPostalCodeData valueData = new CityPostalCodeData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CityPostalCodeData object
     */
    public String toString()
    {
        return "[" + "City=" + mCity + ", StateProvinceCd=" + mStateProvinceCd + ", StateProvinceNam=" + mStateProvinceNam + ", PostalCode=" + mPostalCode + ", CountyCd=" + mCountyCd + ", CountryCd=" + mCountryCd + ", EntryType=" + mEntryType + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", CityPostalCodeId=" + mCityPostalCodeId + ", TaxRate=" + mTaxRate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("CityPostalCode");
        
        Element node;

        node =  doc.createElement("City");
        node.appendChild(doc.createTextNode(String.valueOf(mCity)));
        root.appendChild(node);

        node =  doc.createElement("StateProvinceCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStateProvinceCd)));
        root.appendChild(node);

        node =  doc.createElement("StateProvinceNam");
        node.appendChild(doc.createTextNode(String.valueOf(mStateProvinceNam)));
        root.appendChild(node);

        node =  doc.createElement("PostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mPostalCode)));
        root.appendChild(node);

        node =  doc.createElement("CountyCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCountyCd)));
        root.appendChild(node);

        node =  doc.createElement("CountryCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCountryCd)));
        root.appendChild(node);

        node =  doc.createElement("EntryType");
        node.appendChild(doc.createTextNode(String.valueOf(mEntryType)));
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

        root.setAttribute("Id", String.valueOf(mCityPostalCodeId));

        node =  doc.createElement("TaxRate");
        node.appendChild(doc.createTextNode(String.valueOf(mTaxRate)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the CityPostalCodeId field is not cloned.
    *
    * @return CityPostalCodeData object
    */
    public Object clone(){
        CityPostalCodeData myClone = new CityPostalCodeData();
        
        myClone.mCity = mCity;
        
        myClone.mStateProvinceCd = mStateProvinceCd;
        
        myClone.mStateProvinceNam = mStateProvinceNam;
        
        myClone.mPostalCode = mPostalCode;
        
        myClone.mCountyCd = mCountyCd;
        
        myClone.mCountryCd = mCountryCd;
        
        myClone.mEntryType = mEntryType;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mTaxRate = mTaxRate;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (CityPostalCodeDataAccess.CITY.equals(pFieldName)) {
            return getCity();
        } else if (CityPostalCodeDataAccess.STATE_PROVINCE_CD.equals(pFieldName)) {
            return getStateProvinceCd();
        } else if (CityPostalCodeDataAccess.STATE_PROVINCE_NAM.equals(pFieldName)) {
            return getStateProvinceNam();
        } else if (CityPostalCodeDataAccess.POSTAL_CODE.equals(pFieldName)) {
            return getPostalCode();
        } else if (CityPostalCodeDataAccess.COUNTY_CD.equals(pFieldName)) {
            return getCountyCd();
        } else if (CityPostalCodeDataAccess.COUNTRY_CD.equals(pFieldName)) {
            return getCountryCd();
        } else if (CityPostalCodeDataAccess.ENTRY_TYPE.equals(pFieldName)) {
            return getEntryType();
        } else if (CityPostalCodeDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (CityPostalCodeDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (CityPostalCodeDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (CityPostalCodeDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (CityPostalCodeDataAccess.CITY_POSTAL_CODE_ID.equals(pFieldName)) {
            return getCityPostalCodeId();
        } else if (CityPostalCodeDataAccess.TAX_RATE.equals(pFieldName)) {
            return getTaxRate();
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
        return CityPostalCodeDataAccess.CLW_CITY_POSTAL_CODE;
    }

    
    /**
     * Sets the City field. This field is required to be set in the database.
     *
     * @param pCity
     *  String to use to update the field.
     */
    public void setCity(String pCity){
        this.mCity = pCity;
        setDirty(true);
    }
    /**
     * Retrieves the City field.
     *
     * @return
     *  String containing the City field.
     */
    public String getCity(){
        return mCity;
    }

    /**
     * Sets the StateProvinceCd field. This field is required to be set in the database.
     *
     * @param pStateProvinceCd
     *  String to use to update the field.
     */
    public void setStateProvinceCd(String pStateProvinceCd){
        this.mStateProvinceCd = pStateProvinceCd;
        setDirty(true);
    }
    /**
     * Retrieves the StateProvinceCd field.
     *
     * @return
     *  String containing the StateProvinceCd field.
     */
    public String getStateProvinceCd(){
        return mStateProvinceCd;
    }

    /**
     * Sets the StateProvinceNam field. This field is required to be set in the database.
     *
     * @param pStateProvinceNam
     *  String to use to update the field.
     */
    public void setStateProvinceNam(String pStateProvinceNam){
        this.mStateProvinceNam = pStateProvinceNam;
        setDirty(true);
    }
    /**
     * Retrieves the StateProvinceNam field.
     *
     * @return
     *  String containing the StateProvinceNam field.
     */
    public String getStateProvinceNam(){
        return mStateProvinceNam;
    }

    /**
     * Sets the PostalCode field. This field is required to be set in the database.
     *
     * @param pPostalCode
     *  String to use to update the field.
     */
    public void setPostalCode(String pPostalCode){
        this.mPostalCode = pPostalCode;
        setDirty(true);
    }
    /**
     * Retrieves the PostalCode field.
     *
     * @return
     *  String containing the PostalCode field.
     */
    public String getPostalCode(){
        return mPostalCode;
    }

    /**
     * Sets the CountyCd field.
     *
     * @param pCountyCd
     *  String to use to update the field.
     */
    public void setCountyCd(String pCountyCd){
        this.mCountyCd = pCountyCd;
        setDirty(true);
    }
    /**
     * Retrieves the CountyCd field.
     *
     * @return
     *  String containing the CountyCd field.
     */
    public String getCountyCd(){
        return mCountyCd;
    }

    /**
     * Sets the CountryCd field.
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
     * Sets the EntryType field. This field is required to be set in the database.
     *
     * @param pEntryType
     *  String to use to update the field.
     */
    public void setEntryType(String pEntryType){
        this.mEntryType = pEntryType;
        setDirty(true);
    }
    /**
     * Retrieves the EntryType field.
     *
     * @return
     *  String containing the EntryType field.
     */
    public String getEntryType(){
        return mEntryType;
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
     * Sets the CityPostalCodeId field. This field is required to be set in the database.
     *
     * @param pCityPostalCodeId
     *  int to use to update the field.
     */
    public void setCityPostalCodeId(int pCityPostalCodeId){
        this.mCityPostalCodeId = pCityPostalCodeId;
        setDirty(true);
    }
    /**
     * Retrieves the CityPostalCodeId field.
     *
     * @return
     *  int containing the CityPostalCodeId field.
     */
    public int getCityPostalCodeId(){
        return mCityPostalCodeId;
    }

    /**
     * Sets the TaxRate field.
     *
     * @param pTaxRate
     *  java.math.BigDecimal to use to update the field.
     */
    public void setTaxRate(java.math.BigDecimal pTaxRate){
        this.mTaxRate = pTaxRate;
        setDirty(true);
    }
    /**
     * Retrieves the TaxRate field.
     *
     * @return
     *  java.math.BigDecimal containing the TaxRate field.
     */
    public java.math.BigDecimal getTaxRate(){
        return mTaxRate;
    }


}
