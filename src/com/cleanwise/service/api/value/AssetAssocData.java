
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AssetAssocData
 * Description:  This is a ValueObject class wrapping the database table CLW_ASSET_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.AssetAssocDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>AssetAssocData</code> is a ValueObject class wrapping of the database table CLW_ASSET_ASSOC.
 */
public class AssetAssocData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1209177627874575284L;
    private int mAssetAssocId;// SQL type:NUMBER, not null
    private int mAssetId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER
    private String mAssetAssocCd;// SQL type:VARCHAR2
    private String mAssetAssocStatusCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mItemId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public AssetAssocData ()
    {
        mAssetAssocCd = "";
        mAssetAssocStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public AssetAssocData(int parm1, int parm2, int parm3, String parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9, int parm10)
    {
        mAssetAssocId = parm1;
        mAssetId = parm2;
        mBusEntityId = parm3;
        mAssetAssocCd = parm4;
        mAssetAssocStatusCd = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        mItemId = parm10;
        
    }

    /**
     * Creates a new AssetAssocData
     *
     * @return
     *  Newly initialized AssetAssocData object.
     */
    public static AssetAssocData createValue ()
    {
        AssetAssocData valueData = new AssetAssocData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AssetAssocData object
     */
    public String toString()
    {
        return "[" + "AssetAssocId=" + mAssetAssocId + ", AssetId=" + mAssetId + ", BusEntityId=" + mBusEntityId + ", AssetAssocCd=" + mAssetAssocCd + ", AssetAssocStatusCd=" + mAssetAssocStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", ItemId=" + mItemId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("AssetAssoc");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mAssetAssocId));

        node =  doc.createElement("AssetId");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("AssetAssocCd");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetAssocCd)));
        root.appendChild(node);

        node =  doc.createElement("AssetAssocStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetAssocStatusCd)));
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

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the AssetAssocId field is not cloned.
    *
    * @return AssetAssocData object
    */
    public Object clone(){
        AssetAssocData myClone = new AssetAssocData();
        
        myClone.mAssetId = mAssetId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mAssetAssocCd = mAssetAssocCd;
        
        myClone.mAssetAssocStatusCd = mAssetAssocStatusCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mItemId = mItemId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (AssetAssocDataAccess.ASSET_ASSOC_ID.equals(pFieldName)) {
            return getAssetAssocId();
        } else if (AssetAssocDataAccess.ASSET_ID.equals(pFieldName)) {
            return getAssetId();
        } else if (AssetAssocDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (AssetAssocDataAccess.ASSET_ASSOC_CD.equals(pFieldName)) {
            return getAssetAssocCd();
        } else if (AssetAssocDataAccess.ASSET_ASSOC_STATUS_CD.equals(pFieldName)) {
            return getAssetAssocStatusCd();
        } else if (AssetAssocDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (AssetAssocDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (AssetAssocDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (AssetAssocDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (AssetAssocDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
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
        return AssetAssocDataAccess.CLW_ASSET_ASSOC;
    }

    
    /**
     * Sets the AssetAssocId field. This field is required to be set in the database.
     *
     * @param pAssetAssocId
     *  int to use to update the field.
     */
    public void setAssetAssocId(int pAssetAssocId){
        this.mAssetAssocId = pAssetAssocId;
        setDirty(true);
    }
    /**
     * Retrieves the AssetAssocId field.
     *
     * @return
     *  int containing the AssetAssocId field.
     */
    public int getAssetAssocId(){
        return mAssetAssocId;
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
     * Sets the BusEntityId field.
     *
     * @param pBusEntityId
     *  int to use to update the field.
     */
    public void setBusEntityId(int pBusEntityId){
        this.mBusEntityId = pBusEntityId;
        setDirty(true);
    }
    /**
     * Retrieves the BusEntityId field.
     *
     * @return
     *  int containing the BusEntityId field.
     */
    public int getBusEntityId(){
        return mBusEntityId;
    }

    /**
     * Sets the AssetAssocCd field.
     *
     * @param pAssetAssocCd
     *  String to use to update the field.
     */
    public void setAssetAssocCd(String pAssetAssocCd){
        this.mAssetAssocCd = pAssetAssocCd;
        setDirty(true);
    }
    /**
     * Retrieves the AssetAssocCd field.
     *
     * @return
     *  String containing the AssetAssocCd field.
     */
    public String getAssetAssocCd(){
        return mAssetAssocCd;
    }

    /**
     * Sets the AssetAssocStatusCd field.
     *
     * @param pAssetAssocStatusCd
     *  String to use to update the field.
     */
    public void setAssetAssocStatusCd(String pAssetAssocStatusCd){
        this.mAssetAssocStatusCd = pAssetAssocStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the AssetAssocStatusCd field.
     *
     * @return
     *  String containing the AssetAssocStatusCd field.
     */
    public String getAssetAssocStatusCd(){
        return mAssetAssocStatusCd;
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
     * Sets the ItemId field.
     *
     * @param pItemId
     *  int to use to update the field.
     */
    public void setItemId(int pItemId){
        this.mItemId = pItemId;
        setDirty(true);
    }
    /**
     * Retrieves the ItemId field.
     *
     * @return
     *  int containing the ItemId field.
     */
    public int getItemId(){
        return mItemId;
    }


}
