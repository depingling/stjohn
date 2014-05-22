
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PostalCodeData
 * Description:  This is a ValueObject class wrapping the database table CLW_POSTAL_CODE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.PostalCodeDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>PostalCodeData</code> is a ValueObject class wrapping of the database table CLW_POSTAL_CODE.
 */
public class PostalCodeData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 8457472217628595789L;
    private int mPostalCodeId;// SQL type:NUMBER, not null
    private String mPostalCode;// SQL type:VARCHAR2, not null
    private String mCountyCd;// SQL type:VARCHAR2
    private String mStateProvinceCd;// SQL type:VARCHAR2, not null
    private String mStateProvinceNam;// SQL type:VARCHAR2, not null
    private String mCountryCd;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public PostalCodeData ()
    {
        mPostalCode = "";
        mCountyCd = "";
        mStateProvinceCd = "";
        mStateProvinceNam = "";
        mCountryCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public PostalCodeData(int parm1, String parm2, String parm3, String parm4, String parm5, String parm6, Date parm7, String parm8, Date parm9, String parm10)
    {
        mPostalCodeId = parm1;
        mPostalCode = parm2;
        mCountyCd = parm3;
        mStateProvinceCd = parm4;
        mStateProvinceNam = parm5;
        mCountryCd = parm6;
        mAddDate = parm7;
        mAddBy = parm8;
        mModDate = parm9;
        mModBy = parm10;
        
    }

    /**
     * Creates a new PostalCodeData
     *
     * @return
     *  Newly initialized PostalCodeData object.
     */
    public static PostalCodeData createValue ()
    {
        PostalCodeData valueData = new PostalCodeData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PostalCodeData object
     */
    public String toString()
    {
        return "[" + "PostalCodeId=" + mPostalCodeId + ", PostalCode=" + mPostalCode + ", CountyCd=" + mCountyCd + ", StateProvinceCd=" + mStateProvinceCd + ", StateProvinceNam=" + mStateProvinceNam + ", CountryCd=" + mCountryCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("PostalCode");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mPostalCodeId));

        node =  doc.createElement("PostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mPostalCode)));
        root.appendChild(node);

        node =  doc.createElement("CountyCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCountyCd)));
        root.appendChild(node);

        node =  doc.createElement("StateProvinceCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStateProvinceCd)));
        root.appendChild(node);

        node =  doc.createElement("StateProvinceNam");
        node.appendChild(doc.createTextNode(String.valueOf(mStateProvinceNam)));
        root.appendChild(node);

        node =  doc.createElement("CountryCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCountryCd)));
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
    * creates a clone of this object, the PostalCodeId field is not cloned.
    *
    * @return PostalCodeData object
    */
    public Object clone(){
        PostalCodeData myClone = new PostalCodeData();
        
        myClone.mPostalCode = mPostalCode;
        
        myClone.mCountyCd = mCountyCd;
        
        myClone.mStateProvinceCd = mStateProvinceCd;
        
        myClone.mStateProvinceNam = mStateProvinceNam;
        
        myClone.mCountryCd = mCountryCd;
        
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

        if (PostalCodeDataAccess.POSTAL_CODE_ID.equals(pFieldName)) {
            return getPostalCodeId();
        } else if (PostalCodeDataAccess.POSTAL_CODE.equals(pFieldName)) {
            return getPostalCode();
        } else if (PostalCodeDataAccess.COUNTY_CD.equals(pFieldName)) {
            return getCountyCd();
        } else if (PostalCodeDataAccess.STATE_PROVINCE_CD.equals(pFieldName)) {
            return getStateProvinceCd();
        } else if (PostalCodeDataAccess.STATE_PROVINCE_NAM.equals(pFieldName)) {
            return getStateProvinceNam();
        } else if (PostalCodeDataAccess.COUNTRY_CD.equals(pFieldName)) {
            return getCountryCd();
        } else if (PostalCodeDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (PostalCodeDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (PostalCodeDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (PostalCodeDataAccess.MOD_BY.equals(pFieldName)) {
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
        return PostalCodeDataAccess.CLW_POSTAL_CODE;
    }

    
    /**
     * Sets the PostalCodeId field. This field is required to be set in the database.
     *
     * @param pPostalCodeId
     *  int to use to update the field.
     */
    public void setPostalCodeId(int pPostalCodeId){
        this.mPostalCodeId = pPostalCodeId;
        setDirty(true);
    }
    /**
     * Retrieves the PostalCodeId field.
     *
     * @return
     *  int containing the PostalCodeId field.
     */
    public int getPostalCodeId(){
        return mPostalCodeId;
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
