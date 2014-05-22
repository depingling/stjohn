
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CatalogPropertyData
 * Description:  This is a ValueObject class wrapping the database table CLW_CATALOG_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.CatalogPropertyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>CatalogPropertyData</code> is a ValueObject class wrapping of the database table CLW_CATALOG_PROPERTY.
 */
public class CatalogPropertyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -7476335725388615452L;
    private int mCatalogPropertyId;// SQL type:NUMBER, not null
    private int mCatalogId;// SQL type:NUMBER
    private String mShortDesc;// SQL type:VARCHAR2
    private String mValue;// SQL type:VARCHAR2
    private String mLocaleCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private String mCatalogPropertyTypeCd;// SQL type:VARCHAR2, not null

    /**
     * Constructor.
     */
    public CatalogPropertyData ()
    {
        mShortDesc = "";
        mValue = "";
        mLocaleCd = "";
        mAddBy = "";
        mModBy = "";
        mCatalogPropertyTypeCd = "";
    }

    /**
     * Constructor.
     */
    public CatalogPropertyData(int parm1, int parm2, String parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9, String parm10)
    {
        mCatalogPropertyId = parm1;
        mCatalogId = parm2;
        mShortDesc = parm3;
        mValue = parm4;
        mLocaleCd = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        mCatalogPropertyTypeCd = parm10;
        
    }

    /**
     * Creates a new CatalogPropertyData
     *
     * @return
     *  Newly initialized CatalogPropertyData object.
     */
    public static CatalogPropertyData createValue ()
    {
        CatalogPropertyData valueData = new CatalogPropertyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CatalogPropertyData object
     */
    public String toString()
    {
        return "[" + "CatalogPropertyId=" + mCatalogPropertyId + ", CatalogId=" + mCatalogId + ", ShortDesc=" + mShortDesc + ", Value=" + mValue + ", LocaleCd=" + mLocaleCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", CatalogPropertyTypeCd=" + mCatalogPropertyTypeCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("CatalogProperty");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mCatalogPropertyId));

        node =  doc.createElement("CatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogId)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
        root.appendChild(node);

        node =  doc.createElement("LocaleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mLocaleCd)));
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

        node =  doc.createElement("CatalogPropertyTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogPropertyTypeCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the CatalogPropertyId field is not cloned.
    *
    * @return CatalogPropertyData object
    */
    public Object clone(){
        CatalogPropertyData myClone = new CatalogPropertyData();
        
        myClone.mCatalogId = mCatalogId;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mValue = mValue;
        
        myClone.mLocaleCd = mLocaleCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mCatalogPropertyTypeCd = mCatalogPropertyTypeCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (CatalogPropertyDataAccess.CATALOG_PROPERTY_ID.equals(pFieldName)) {
            return getCatalogPropertyId();
        } else if (CatalogPropertyDataAccess.CATALOG_ID.equals(pFieldName)) {
            return getCatalogId();
        } else if (CatalogPropertyDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (CatalogPropertyDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (CatalogPropertyDataAccess.LOCALE_CD.equals(pFieldName)) {
            return getLocaleCd();
        } else if (CatalogPropertyDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (CatalogPropertyDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (CatalogPropertyDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (CatalogPropertyDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (CatalogPropertyDataAccess.CATALOG_PROPERTY_TYPE_CD.equals(pFieldName)) {
            return getCatalogPropertyTypeCd();
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
        return CatalogPropertyDataAccess.CLW_CATALOG_PROPERTY;
    }

    
    /**
     * Sets the CatalogPropertyId field. This field is required to be set in the database.
     *
     * @param pCatalogPropertyId
     *  int to use to update the field.
     */
    public void setCatalogPropertyId(int pCatalogPropertyId){
        this.mCatalogPropertyId = pCatalogPropertyId;
        setDirty(true);
    }
    /**
     * Retrieves the CatalogPropertyId field.
     *
     * @return
     *  int containing the CatalogPropertyId field.
     */
    public int getCatalogPropertyId(){
        return mCatalogPropertyId;
    }

    /**
     * Sets the CatalogId field.
     *
     * @param pCatalogId
     *  int to use to update the field.
     */
    public void setCatalogId(int pCatalogId){
        this.mCatalogId = pCatalogId;
        setDirty(true);
    }
    /**
     * Retrieves the CatalogId field.
     *
     * @return
     *  int containing the CatalogId field.
     */
    public int getCatalogId(){
        return mCatalogId;
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
     * Sets the CatalogPropertyTypeCd field. This field is required to be set in the database.
     *
     * @param pCatalogPropertyTypeCd
     *  String to use to update the field.
     */
    public void setCatalogPropertyTypeCd(String pCatalogPropertyTypeCd){
        this.mCatalogPropertyTypeCd = pCatalogPropertyTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the CatalogPropertyTypeCd field.
     *
     * @return
     *  String containing the CatalogPropertyTypeCd field.
     */
    public String getCatalogPropertyTypeCd(){
        return mCatalogPropertyTypeCd;
    }


}
