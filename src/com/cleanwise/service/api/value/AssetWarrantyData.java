
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AssetWarrantyData
 * Description:  This is a ValueObject class wrapping the database table CLW_ASSET_WARRANTY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.AssetWarrantyDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>AssetWarrantyData</code> is a ValueObject class wrapping of the database table CLW_ASSET_WARRANTY.
 */
public class AssetWarrantyData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -5681408464589136133L;
    private int mAssetWarrantyId;// SQL type:NUMBER, not null
    private int mAssetId;// SQL type:NUMBER, not null
    private int mWarrantyId;// SQL type:NUMBER, not null
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public AssetWarrantyData ()
    {
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public AssetWarrantyData(int parm1, int parm2, int parm3, Date parm4, String parm5, Date parm6, String parm7)
    {
        mAssetWarrantyId = parm1;
        mAssetId = parm2;
        mWarrantyId = parm3;
        mAddDate = parm4;
        mAddBy = parm5;
        mModDate = parm6;
        mModBy = parm7;
        
    }

    /**
     * Creates a new AssetWarrantyData
     *
     * @return
     *  Newly initialized AssetWarrantyData object.
     */
    public static AssetWarrantyData createValue ()
    {
        AssetWarrantyData valueData = new AssetWarrantyData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AssetWarrantyData object
     */
    public String toString()
    {
        return "[" + "AssetWarrantyId=" + mAssetWarrantyId + ", AssetId=" + mAssetId + ", WarrantyId=" + mWarrantyId + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("AssetWarranty");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mAssetWarrantyId));

        node =  doc.createElement("AssetId");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetId)));
        root.appendChild(node);

        node =  doc.createElement("WarrantyId");
        node.appendChild(doc.createTextNode(String.valueOf(mWarrantyId)));
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
    * creates a clone of this object, the AssetWarrantyId field is not cloned.
    *
    * @return AssetWarrantyData object
    */
    public Object clone(){
        AssetWarrantyData myClone = new AssetWarrantyData();
        
        myClone.mAssetId = mAssetId;
        
        myClone.mWarrantyId = mWarrantyId;
        
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

        if (AssetWarrantyDataAccess.ASSET_WARRANTY_ID.equals(pFieldName)) {
            return getAssetWarrantyId();
        } else if (AssetWarrantyDataAccess.ASSET_ID.equals(pFieldName)) {
            return getAssetId();
        } else if (AssetWarrantyDataAccess.WARRANTY_ID.equals(pFieldName)) {
            return getWarrantyId();
        } else if (AssetWarrantyDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (AssetWarrantyDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (AssetWarrantyDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (AssetWarrantyDataAccess.MOD_BY.equals(pFieldName)) {
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
        return AssetWarrantyDataAccess.CLW_ASSET_WARRANTY;
    }

    
    /**
     * Sets the AssetWarrantyId field. This field is required to be set in the database.
     *
     * @param pAssetWarrantyId
     *  int to use to update the field.
     */
    public void setAssetWarrantyId(int pAssetWarrantyId){
        this.mAssetWarrantyId = pAssetWarrantyId;
        setDirty(true);
    }
    /**
     * Retrieves the AssetWarrantyId field.
     *
     * @return
     *  int containing the AssetWarrantyId field.
     */
    public int getAssetWarrantyId(){
        return mAssetWarrantyId;
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
     * Sets the WarrantyId field. This field is required to be set in the database.
     *
     * @param pWarrantyId
     *  int to use to update the field.
     */
    public void setWarrantyId(int pWarrantyId){
        this.mWarrantyId = pWarrantyId;
        setDirty(true);
    }
    /**
     * Retrieves the WarrantyId field.
     *
     * @return
     *  int containing the WarrantyId field.
     */
    public int getWarrantyId(){
        return mWarrantyId;
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
