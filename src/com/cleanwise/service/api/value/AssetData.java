
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AssetData
 * Description:  This is a ValueObject class wrapping the database table CLW_ASSET.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.AssetDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>AssetData</code> is a ValueObject class wrapping of the database table CLW_ASSET.
 */
public class AssetData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -8046228430174783619L;
    private int mAssetId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2, not null
    private String mAssetTypeCd;// SQL type:VARCHAR2, not null
    private int mParentId;// SQL type:NUMBER
    private String mStatusCd;// SQL type:VARCHAR2, not null
    private String mSerialNum;// SQL type:VARCHAR2
    private String mAssetNum;// SQL type:VARCHAR2
    private int mManufId;// SQL type:NUMBER
    private String mManufName;// SQL type:VARCHAR2
    private String mManufSku;// SQL type:VARCHAR2
    private String mManufTypeCd;// SQL type:VARCHAR2
    private String mAddBy;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModelNumber;// SQL type:VARCHAR2
    private int mMasterAssetId;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public AssetData ()
    {
        mShortDesc = "";
        mAssetTypeCd = "";
        mStatusCd = "";
        mSerialNum = "";
        mAssetNum = "";
        mManufName = "";
        mManufSku = "";
        mManufTypeCd = "";
        mAddBy = "";
        mModBy = "";
        mModelNumber = "";
    }

    /**
     * Constructor.
     */
    public AssetData(int parm1, String parm2, String parm3, int parm4, String parm5, String parm6, String parm7, int parm8, String parm9, String parm10, String parm11, String parm12, Date parm13, String parm14, Date parm15, String parm16, int parm17)
    {
        mAssetId = parm1;
        mShortDesc = parm2;
        mAssetTypeCd = parm3;
        mParentId = parm4;
        mStatusCd = parm5;
        mSerialNum = parm6;
        mAssetNum = parm7;
        mManufId = parm8;
        mManufName = parm9;
        mManufSku = parm10;
        mManufTypeCd = parm11;
        mAddBy = parm12;
        mAddDate = parm13;
        mModBy = parm14;
        mModDate = parm15;
        mModelNumber = parm16;
        mMasterAssetId = parm17;
        
    }

    /**
     * Creates a new AssetData
     *
     * @return
     *  Newly initialized AssetData object.
     */
    public static AssetData createValue ()
    {
        AssetData valueData = new AssetData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AssetData object
     */
    public String toString()
    {
        return "[" + "AssetId=" + mAssetId + ", ShortDesc=" + mShortDesc + ", AssetTypeCd=" + mAssetTypeCd + ", ParentId=" + mParentId + ", StatusCd=" + mStatusCd + ", SerialNum=" + mSerialNum + ", AssetNum=" + mAssetNum + ", ManufId=" + mManufId + ", ManufName=" + mManufName + ", ManufSku=" + mManufSku + ", ManufTypeCd=" + mManufTypeCd + ", AddBy=" + mAddBy + ", AddDate=" + mAddDate + ", ModBy=" + mModBy + ", ModDate=" + mModDate + ", ModelNumber=" + mModelNumber + ", MasterAssetId=" + mMasterAssetId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Asset");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mAssetId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("AssetTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("ParentId");
        node.appendChild(doc.createTextNode(String.valueOf(mParentId)));
        root.appendChild(node);

        node =  doc.createElement("StatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("SerialNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSerialNum)));
        root.appendChild(node);

        node =  doc.createElement("AssetNum");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetNum)));
        root.appendChild(node);

        node =  doc.createElement("ManufId");
        node.appendChild(doc.createTextNode(String.valueOf(mManufId)));
        root.appendChild(node);

        node =  doc.createElement("ManufName");
        node.appendChild(doc.createTextNode(String.valueOf(mManufName)));
        root.appendChild(node);

        node =  doc.createElement("ManufSku");
        node.appendChild(doc.createTextNode(String.valueOf(mManufSku)));
        root.appendChild(node);

        node =  doc.createElement("ManufTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mManufTypeCd)));
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

        node =  doc.createElement("ModelNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mModelNumber)));
        root.appendChild(node);

        node =  doc.createElement("MasterAssetId");
        node.appendChild(doc.createTextNode(String.valueOf(mMasterAssetId)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the AssetId field is not cloned.
    *
    * @return AssetData object
    */
    public Object clone(){
        AssetData myClone = new AssetData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mAssetTypeCd = mAssetTypeCd;
        
        myClone.mParentId = mParentId;
        
        myClone.mStatusCd = mStatusCd;
        
        myClone.mSerialNum = mSerialNum;
        
        myClone.mAssetNum = mAssetNum;
        
        myClone.mManufId = mManufId;
        
        myClone.mManufName = mManufName;
        
        myClone.mManufSku = mManufSku;
        
        myClone.mManufTypeCd = mManufTypeCd;
        
        myClone.mAddBy = mAddBy;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModelNumber = mModelNumber;
        
        myClone.mMasterAssetId = mMasterAssetId;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (AssetDataAccess.ASSET_ID.equals(pFieldName)) {
            return getAssetId();
        } else if (AssetDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (AssetDataAccess.ASSET_TYPE_CD.equals(pFieldName)) {
            return getAssetTypeCd();
        } else if (AssetDataAccess.PARENT_ID.equals(pFieldName)) {
            return getParentId();
        } else if (AssetDataAccess.STATUS_CD.equals(pFieldName)) {
            return getStatusCd();
        } else if (AssetDataAccess.SERIAL_NUM.equals(pFieldName)) {
            return getSerialNum();
        } else if (AssetDataAccess.ASSET_NUM.equals(pFieldName)) {
            return getAssetNum();
        } else if (AssetDataAccess.MANUF_ID.equals(pFieldName)) {
            return getManufId();
        } else if (AssetDataAccess.MANUF_NAME.equals(pFieldName)) {
            return getManufName();
        } else if (AssetDataAccess.MANUF_SKU.equals(pFieldName)) {
            return getManufSku();
        } else if (AssetDataAccess.MANUF_TYPE_CD.equals(pFieldName)) {
            return getManufTypeCd();
        } else if (AssetDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (AssetDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (AssetDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (AssetDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (AssetDataAccess.MODEL_NUMBER.equals(pFieldName)) {
            return getModelNumber();
        } else if (AssetDataAccess.MASTER_ASSET_ID.equals(pFieldName)) {
            return getMasterAssetId();
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
        return AssetDataAccess.CLW_ASSET;
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
     * Sets the AssetTypeCd field. This field is required to be set in the database.
     *
     * @param pAssetTypeCd
     *  String to use to update the field.
     */
    public void setAssetTypeCd(String pAssetTypeCd){
        this.mAssetTypeCd = pAssetTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the AssetTypeCd field.
     *
     * @return
     *  String containing the AssetTypeCd field.
     */
    public String getAssetTypeCd(){
        return mAssetTypeCd;
    }

    /**
     * Sets the ParentId field.
     *
     * @param pParentId
     *  int to use to update the field.
     */
    public void setParentId(int pParentId){
        this.mParentId = pParentId;
        setDirty(true);
    }
    /**
     * Retrieves the ParentId field.
     *
     * @return
     *  int containing the ParentId field.
     */
    public int getParentId(){
        return mParentId;
    }

    /**
     * Sets the StatusCd field. This field is required to be set in the database.
     *
     * @param pStatusCd
     *  String to use to update the field.
     */
    public void setStatusCd(String pStatusCd){
        this.mStatusCd = pStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the StatusCd field.
     *
     * @return
     *  String containing the StatusCd field.
     */
    public String getStatusCd(){
        return mStatusCd;
    }

    /**
     * Sets the SerialNum field.
     *
     * @param pSerialNum
     *  String to use to update the field.
     */
    public void setSerialNum(String pSerialNum){
        this.mSerialNum = pSerialNum;
        setDirty(true);
    }
    /**
     * Retrieves the SerialNum field.
     *
     * @return
     *  String containing the SerialNum field.
     */
    public String getSerialNum(){
        return mSerialNum;
    }

    /**
     * Sets the AssetNum field.
     *
     * @param pAssetNum
     *  String to use to update the field.
     */
    public void setAssetNum(String pAssetNum){
        this.mAssetNum = pAssetNum;
        setDirty(true);
    }
    /**
     * Retrieves the AssetNum field.
     *
     * @return
     *  String containing the AssetNum field.
     */
    public String getAssetNum(){
        return mAssetNum;
    }

    /**
     * Sets the ManufId field.
     *
     * @param pManufId
     *  int to use to update the field.
     */
    public void setManufId(int pManufId){
        this.mManufId = pManufId;
        setDirty(true);
    }
    /**
     * Retrieves the ManufId field.
     *
     * @return
     *  int containing the ManufId field.
     */
    public int getManufId(){
        return mManufId;
    }

    /**
     * Sets the ManufName field.
     *
     * @param pManufName
     *  String to use to update the field.
     */
    public void setManufName(String pManufName){
        this.mManufName = pManufName;
        setDirty(true);
    }
    /**
     * Retrieves the ManufName field.
     *
     * @return
     *  String containing the ManufName field.
     */
    public String getManufName(){
        return mManufName;
    }

    /**
     * Sets the ManufSku field.
     *
     * @param pManufSku
     *  String to use to update the field.
     */
    public void setManufSku(String pManufSku){
        this.mManufSku = pManufSku;
        setDirty(true);
    }
    /**
     * Retrieves the ManufSku field.
     *
     * @return
     *  String containing the ManufSku field.
     */
    public String getManufSku(){
        return mManufSku;
    }

    /**
     * Sets the ManufTypeCd field.
     *
     * @param pManufTypeCd
     *  String to use to update the field.
     */
    public void setManufTypeCd(String pManufTypeCd){
        this.mManufTypeCd = pManufTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the ManufTypeCd field.
     *
     * @return
     *  String containing the ManufTypeCd field.
     */
    public String getManufTypeCd(){
        return mManufTypeCd;
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

    /**
     * Sets the ModelNumber field.
     *
     * @param pModelNumber
     *  String to use to update the field.
     */
    public void setModelNumber(String pModelNumber){
        this.mModelNumber = pModelNumber;
        setDirty(true);
    }
    /**
     * Retrieves the ModelNumber field.
     *
     * @return
     *  String containing the ModelNumber field.
     */
    public String getModelNumber(){
        return mModelNumber;
    }

    /**
     * Sets the MasterAssetId field.
     *
     * @param pMasterAssetId
     *  int to use to update the field.
     */
    public void setMasterAssetId(int pMasterAssetId){
        this.mMasterAssetId = pMasterAssetId;
        setDirty(true);
    }
    /**
     * Retrieves the MasterAssetId field.
     *
     * @return
     *  int containing the MasterAssetId field.
     */
    public int getMasterAssetId(){
        return mMasterAssetId;
    }


}
