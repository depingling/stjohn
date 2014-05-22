
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AssetPropertyData
 * Description:  This is a ValueObject class wrapping the database table CLW_ASSET_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.AssetPropertyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>AssetPropertyData</code> is a ValueObject class wrapping of the database table CLW_ASSET_PROPERTY.
 */
public class AssetPropertyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -7847825699366667390L;
    private int mAssetPropertyId;// SQL type:NUMBER, not null
    private int mAssetId;// SQL type:NUMBER, not null
    private String mAssetPropertyCd;// SQL type:VARCHAR2, not null
    private String mValue;// SQL type:VARCHAR2
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null

    /**
     * Constructor.
     */
    public AssetPropertyData ()
    {
        mAssetPropertyCd = "";
        mValue = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public AssetPropertyData(int parm1, int parm2, String parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8)
    {
        mAssetPropertyId = parm1;
        mAssetId = parm2;
        mAssetPropertyCd = parm3;
        mValue = parm4;
        mAddBy = parm5;
        mAddDate = parm6;
        mModBy = parm7;
        mModDate = parm8;
        
    }

    /**
     * Creates a new AssetPropertyData
     *
     * @return
     *  Newly initialized AssetPropertyData object.
     */
    public static AssetPropertyData createValue ()
    {
        AssetPropertyData valueData = new AssetPropertyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AssetPropertyData object
     */
    public String toString()
    {
        return "[" + "AssetPropertyId=" + mAssetPropertyId + ", AssetId=" + mAssetId + ", AssetPropertyCd=" + mAssetPropertyCd + ", Value=" + mValue + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("AssetProperty");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mAssetPropertyId));

        node =  doc.createElement("AssetId");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetId)));
        root.appendChild(node);

        node =  doc.createElement("AssetPropertyCd");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetPropertyCd)));
        root.appendChild(node);

        node =  doc.createElement("Value");
        node.appendChild(doc.createTextNode(String.valueOf(mValue)));
        root.appendChild(node);

        node =  doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node =  doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the AssetPropertyId field is not cloned.
    *
    * @return AssetPropertyData object
    */
    public Object clone(){
        AssetPropertyData myClone = new AssetPropertyData();
        
        myClone.mAssetId = mAssetId;
        
        myClone.mAssetPropertyCd = mAssetPropertyCd;
        
        myClone.mValue = mValue;
        
        myClone.mAddBy = mAddBy;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (AssetPropertyDataAccess.ASSET_PROPERTY_ID.equals(pFieldName)) {
            return getAssetPropertyId();
        } else if (AssetPropertyDataAccess.ASSET_ID.equals(pFieldName)) {
            return getAssetId();
        } else if (AssetPropertyDataAccess.ASSET_PROPERTY_CD.equals(pFieldName)) {
            return getAssetPropertyCd();
        } else if (AssetPropertyDataAccess.CLW_VALUE.equals(pFieldName)) {
            return getValue();
        } else if (AssetPropertyDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (AssetPropertyDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (AssetPropertyDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (AssetPropertyDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
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
        return AssetPropertyDataAccess.CLW_ASSET_PROPERTY;
    }

    
    /**
     * Sets the AssetPropertyId field. This field is required to be set in the database.
     *
     * @param pAssetPropertyId
     *  int to use to update the field.
     */
    public void setAssetPropertyId(int pAssetPropertyId){
        this.mAssetPropertyId = pAssetPropertyId;
        setDirty(true);
    }
    /**
     * Retrieves the AssetPropertyId field.
     *
     * @return
     *  int containing the AssetPropertyId field.
     */
    public int getAssetPropertyId(){
        return mAssetPropertyId;
    }

    /**
     * Sets the AssetId field. This field is required to be set in the database.
     *
     * @param pAssetId
     *  int to use to update the field.
     */
    public void setAssetId(int pAssetId){
        this.mAssetId = pAssetId;
        setDirty(true);
    }
    /**
     * Retrieves the AssetId field.
     *
     * @return
     *  int containing the AssetId field.
     */
    public int getAssetId(){
        return mAssetId;
    }

    /**
     * Sets the AssetPropertyCd field. This field is required to be set in the database.
     *
     * @param pAssetPropertyCd
     *  String to use to update the field.
     */
    public void setAssetPropertyCd(String pAssetPropertyCd){
        this.mAssetPropertyCd = pAssetPropertyCd;
        setDirty(true);
    }
    /**
     * Retrieves the AssetPropertyCd field.
     *
     * @return
     *  String containing the AssetPropertyCd field.
     */
    public String getAssetPropertyCd(){
        return mAssetPropertyCd;
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


}
