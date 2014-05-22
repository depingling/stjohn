
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        LanguageData
 * Description:  This is a ValueObject class wrapping the database table CLW_LANGUAGE.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.LanguageDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>LanguageData</code> is a ValueObject class wrapping of the database table CLW_LANGUAGE.
 */
public class LanguageData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1L;
    private int mLanguageId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mUiName;// SQL type:VARCHAR2, not null
    private String mLanguageCode;// SQL type:VARCHAR2, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mSupported;// SQL type:VARCHAR2
    private String mTranslatedName;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public LanguageData ()
    {
        mShortDesc = "";
        mUiName = "";
        mLanguageCode = "";
        mAddBy = "";
        mModBy = "";
        mSupported = "";
        mTranslatedName = "";
    }

    /**
     * Constructor.
     */
    public LanguageData(int parm1, String parm2, String parm3, String parm4, Date parm5, String parm6, Date parm7, String parm8, String parm9, String parm10)
    {
        mLanguageId = parm1;
        mShortDesc = parm2;
        mUiName = parm3;
        mLanguageCode = parm4;
        mAddDate = parm5;
        mAddBy = parm6;
        mModDate = parm7;
        mModBy = parm8;
        mSupported = parm9;
        mTranslatedName = parm10;
        
    }

    /**
     * Creates a new LanguageData
     *
     * @return
     *  Newly initialized LanguageData object.
     */
    public static LanguageData createValue ()
    {
        LanguageData valueData = new LanguageData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this LanguageData object
     */
    public String toString()
    {
        return "[" + "LanguageId=" + mLanguageId + ", ShortDesc=" + mShortDesc + ", UiName=" + mUiName + ", LanguageCode=" + mLanguageCode + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", Supported=" + mSupported + ", TranslatedName=" + mTranslatedName + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Language");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mLanguageId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("UiName");
        node.appendChild(doc.createTextNode(String.valueOf(mUiName)));
        root.appendChild(node);

        node =  doc.createElement("LanguageCode");
        node.appendChild(doc.createTextNode(String.valueOf(mLanguageCode)));
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

        node =  doc.createElement("Supported");
        node.appendChild(doc.createTextNode(String.valueOf(mSupported)));
        root.appendChild(node);

        node =  doc.createElement("TranslatedName");
        node.appendChild(doc.createTextNode(String.valueOf(mTranslatedName)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the LanguageId field is not cloned.
    *
    * @return LanguageData object
    */
    public Object clone(){
        LanguageData myClone = new LanguageData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mUiName = mUiName;
        
        myClone.mLanguageCode = mLanguageCode;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mSupported = mSupported;
        
        myClone.mTranslatedName = mTranslatedName;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (LanguageDataAccess.LANGUAGE_ID.equals(pFieldName)) {
            return getLanguageId();
        } else if (LanguageDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (LanguageDataAccess.UI_NAME.equals(pFieldName)) {
            return getUiName();
        } else if (LanguageDataAccess.LANGUAGE_CODE.equals(pFieldName)) {
            return getLanguageCode();
        } else if (LanguageDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (LanguageDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (LanguageDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (LanguageDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (LanguageDataAccess.SUPPORTED.equals(pFieldName)) {
            return getSupported();
        } else if (LanguageDataAccess.TRANSLATED_NAME.equals(pFieldName)) {
            return getTranslatedName();
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
        return LanguageDataAccess.CLW_LANGUAGE;
    }

    
    /**
     * Sets the LanguageId field. This field is required to be set in the database.
     *
     * @param pLanguageId
     *  int to use to update the field.
     */
    public void setLanguageId(int pLanguageId){
        this.mLanguageId = pLanguageId;
        setDirty(true);
    }
    /**
     * Retrieves the LanguageId field.
     *
     * @return
     *  int containing the LanguageId field.
     */
    public int getLanguageId(){
        return mLanguageId;
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
     * Sets the LanguageCode field. This field is required to be set in the database.
     *
     * @param pLanguageCode
     *  String to use to update the field.
     */
    public void setLanguageCode(String pLanguageCode){
        this.mLanguageCode = pLanguageCode;
        setDirty(true);
    }
    /**
     * Retrieves the LanguageCode field.
     *
     * @return
     *  String containing the LanguageCode field.
     */
    public String getLanguageCode(){
        return mLanguageCode;
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
     * Sets the Supported field.
     *
     * @param pSupported
     *  String to use to update the field.
     */
    public void setSupported(String pSupported){
        this.mSupported = pSupported;
        setDirty(true);
    }
    /**
     * Retrieves the Supported field.
     *
     * @return
     *  String containing the Supported field.
     */
    public String getSupported(){
        return mSupported;
    }

    /**
     * Sets the TranslatedName field.
     *
     * @param pTranslatedName
     *  String to use to update the field.
     */
    public void setTranslatedName(String pTranslatedName){
        this.mTranslatedName = pTranslatedName;
        setDirty(true);
    }
    /**
     * Retrieves the TranslatedName field.
     *
     * @return
     *  String containing the TranslatedName field.
     */
    public String getTranslatedName(){
        return mTranslatedName;
    }


}
