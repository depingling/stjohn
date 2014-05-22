
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CurrencyData
 * Description:  This is a ValueObject class wrapping the database table CLW_CURRENCY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.CurrencyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>CurrencyData</code> is a ValueObject class wrapping of the database table CLW_CURRENCY.
 */
public class CurrencyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -3972558621780985667L;
    private int mCurrencyId;// SQL type:NUMBER, not null
    private String mLocale;// SQL type:VARCHAR2, not null
    private String mShortDesc;// SQL type:VARCHAR2
    private String mLocalCode;// SQL type:VARCHAR2
    private String mCurrencyPositionCd;// SQL type:VARCHAR2
    private int mDecimals;// SQL type:NUMBER, not null
    private String mGlobalCode;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public CurrencyData ()
    {
        mLocale = "";
        mShortDesc = "";
        mLocalCode = "";
        mCurrencyPositionCd = "";
        mGlobalCode = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public CurrencyData(int parm1, String parm2, String parm3, String parm4, String parm5, int parm6, String parm7, Date parm8, String parm9, Date parm10, String parm11)
    {
        mCurrencyId = parm1;
        mLocale = parm2;
        mShortDesc = parm3;
        mLocalCode = parm4;
        mCurrencyPositionCd = parm5;
        mDecimals = parm6;
        mGlobalCode = parm7;
        mAddDate = parm8;
        mAddBy = parm9;
        mModDate = parm10;
        mModBy = parm11;
        
    }

    /**
     * Creates a new CurrencyData
     *
     * @return
     *  Newly initialized CurrencyData object.
     */
    public static CurrencyData createValue ()
    {
        CurrencyData valueData = new CurrencyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CurrencyData object
     */
    public String toString()
    {
        return "[" + "CurrencyId=" + mCurrencyId + ", Locale=" + mLocale + ", ShortDesc=" + mShortDesc + ", LocalCode=" + mLocalCode + ", CurrencyPositionCd=" + mCurrencyPositionCd + ", Decimals=" + mDecimals + ", GlobalCode=" + mGlobalCode + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Currency");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mCurrencyId));

        node =  doc.createElement("Locale");
        node.appendChild(doc.createTextNode(String.valueOf(mLocale)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("LocalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mLocalCode)));
        root.appendChild(node);

        node =  doc.createElement("CurrencyPositionCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCurrencyPositionCd)));
        root.appendChild(node);

        node =  doc.createElement("Decimals");
        node.appendChild(doc.createTextNode(String.valueOf(mDecimals)));
        root.appendChild(node);

        node =  doc.createElement("GlobalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mGlobalCode)));
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
    * creates a clone of this object, the CurrencyId field is not cloned.
    *
    * @return CurrencyData object
    */
    public Object clone(){
        CurrencyData myClone = new CurrencyData();
        
        myClone.mLocale = mLocale;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mLocalCode = mLocalCode;
        
        myClone.mCurrencyPositionCd = mCurrencyPositionCd;
        
        myClone.mDecimals = mDecimals;
        
        myClone.mGlobalCode = mGlobalCode;
        
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

        if (CurrencyDataAccess.CURRENCY_ID.equals(pFieldName)) {
            return getCurrencyId();
        } else if (CurrencyDataAccess.LOCALE.equals(pFieldName)) {
            return getLocale();
        } else if (CurrencyDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (CurrencyDataAccess.LOCAL_CODE.equals(pFieldName)) {
            return getLocalCode();
        } else if (CurrencyDataAccess.CURRENCY_POSITION_CD.equals(pFieldName)) {
            return getCurrencyPositionCd();
        } else if (CurrencyDataAccess.DECIMALS.equals(pFieldName)) {
            return getDecimals();
        } else if (CurrencyDataAccess.GLOBAL_CODE.equals(pFieldName)) {
            return getGlobalCode();
        } else if (CurrencyDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (CurrencyDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (CurrencyDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (CurrencyDataAccess.MOD_BY.equals(pFieldName)) {
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
        return CurrencyDataAccess.CLW_CURRENCY;
    }

    
    /**
     * Sets the CurrencyId field. This field is required to be set in the database.
     *
     * @param pCurrencyId
     *  int to use to update the field.
     */
    public void setCurrencyId(int pCurrencyId){
        this.mCurrencyId = pCurrencyId;
        setDirty(true);
    }
    /**
     * Retrieves the CurrencyId field.
     *
     * @return
     *  int containing the CurrencyId field.
     */
    public int getCurrencyId(){
        return mCurrencyId;
    }

    /**
     * Sets the Locale field. This field is required to be set in the database.
     *
     * @param pLocale
     *  String to use to update the field.
     */
    public void setLocale(String pLocale){
        this.mLocale = pLocale;
        setDirty(true);
    }
    /**
     * Retrieves the Locale field.
     *
     * @return
     *  String containing the Locale field.
     */
    public String getLocale(){
        return mLocale;
    }

    /**
     * Sets the ShortDesc field.
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
     * Sets the LocalCode field.
     *
     * @param pLocalCode
     *  String to use to update the field.
     */
    public void setLocalCode(String pLocalCode){
        this.mLocalCode = pLocalCode;
        setDirty(true);
    }
    /**
     * Retrieves the LocalCode field.
     *
     * @return
     *  String containing the LocalCode field.
     */
    public String getLocalCode(){
        return mLocalCode;
    }

    /**
     * Sets the CurrencyPositionCd field.
     *
     * @param pCurrencyPositionCd
     *  String to use to update the field.
     */
    public void setCurrencyPositionCd(String pCurrencyPositionCd){
        this.mCurrencyPositionCd = pCurrencyPositionCd;
        setDirty(true);
    }
    /**
     * Retrieves the CurrencyPositionCd field.
     *
     * @return
     *  String containing the CurrencyPositionCd field.
     */
    public String getCurrencyPositionCd(){
        return mCurrencyPositionCd;
    }

    /**
     * Sets the Decimals field. This field is required to be set in the database.
     *
     * @param pDecimals
     *  int to use to update the field.
     */
    public void setDecimals(int pDecimals){
        this.mDecimals = pDecimals;
        setDirty(true);
    }
    /**
     * Retrieves the Decimals field.
     *
     * @return
     *  int containing the Decimals field.
     */
    public int getDecimals(){
        return mDecimals;
    }

    /**
     * Sets the GlobalCode field. This field is required to be set in the database.
     *
     * @param pGlobalCode
     *  String to use to update the field.
     */
    public void setGlobalCode(String pGlobalCode){
        this.mGlobalCode = pGlobalCode;
        setDirty(true);
    }
    /**
     * Retrieves the GlobalCode field.
     *
     * @return
     *  String containing the GlobalCode field.
     */
    public String getGlobalCode(){
        return mGlobalCode;
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
