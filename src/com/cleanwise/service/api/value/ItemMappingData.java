
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ItemMappingData
 * Description:  This is a ValueObject class wrapping the database table CLW_ITEM_MAPPING.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ItemMappingDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ItemMappingData</code> is a ValueObject class wrapping of the database table CLW_ITEM_MAPPING.
 */
public class ItemMappingData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -5814145405625444190L;
    private int mItemMappingId;// SQL type:NUMBER, not null
    private int mItemId;// SQL type:NUMBER, not null
    private int mBusEntityId;// SQL type:NUMBER
    private String mItemNum;// SQL type:VARCHAR2
    private String mItemUom;// SQL type:VARCHAR2
    private String mItemPack;// SQL type:VARCHAR2
    private String mShortDesc;// SQL type:VARCHAR2
    private String mLongDesc;// SQL type:VARCHAR2
    private String mItemMappingCd;// SQL type:VARCHAR2, not null
    private Date mEffDate;// SQL type:DATE
    private Date mExpDate;// SQL type:DATE
    private String mStatusCd;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private java.math.BigDecimal mUomConvMultiplier;// SQL type:NUMBER
    private String mStandardProductList;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ItemMappingData ()
    {
        mItemNum = "";
        mItemUom = "";
        mItemPack = "";
        mShortDesc = "";
        mLongDesc = "";
        mItemMappingCd = "";
        mStatusCd = "";
        mAddBy = "";
        mModBy = "";
        mStandardProductList = "";
    }

    /**
     * Constructor.
     */
    public ItemMappingData(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, Date parm10, Date parm11, String parm12, Date parm13, String parm14, Date parm15, String parm16, java.math.BigDecimal parm17, String parm18)
    {
        mItemMappingId = parm1;
        mItemId = parm2;
        mBusEntityId = parm3;
        mItemNum = parm4;
        mItemUom = parm5;
        mItemPack = parm6;
        mShortDesc = parm7;
        mLongDesc = parm8;
        mItemMappingCd = parm9;
        mEffDate = parm10;
        mExpDate = parm11;
        mStatusCd = parm12;
        mAddDate = parm13;
        mAddBy = parm14;
        mModDate = parm15;
        mModBy = parm16;
        mUomConvMultiplier = parm17;
        mStandardProductList = parm18;
        
    }

    /**
     * Creates a new ItemMappingData
     *
     * @return
     *  Newly initialized ItemMappingData object.
     */
    public static ItemMappingData createValue ()
    {
        ItemMappingData valueData = new ItemMappingData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ItemMappingData object
     */
    public String toString()
    {
        return "[" + "ItemMappingId=" + mItemMappingId + ", ItemId=" + mItemId + ", BusEntityId=" + mBusEntityId + ", ItemNum=" + mItemNum + ", ItemUom=" + mItemUom + ", ItemPack=" + mItemPack + ", ShortDesc=" + mShortDesc + ", LongDesc=" + mLongDesc + ", ItemMappingCd=" + mItemMappingCd + ", EffDate=" + mEffDate + ", ExpDate=" + mExpDate + ", StatusCd=" + mStatusCd + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", UomConvMultiplier=" + mUomConvMultiplier + ", StandardProductList=" + mStandardProductList + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ItemMapping");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mItemMappingId));

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node =  doc.createElement("ItemNum");
        node.appendChild(doc.createTextNode(String.valueOf(mItemNum)));
        root.appendChild(node);

        node =  doc.createElement("ItemUom");
        node.appendChild(doc.createTextNode(String.valueOf(mItemUom)));
        root.appendChild(node);

        node =  doc.createElement("ItemPack");
        node.appendChild(doc.createTextNode(String.valueOf(mItemPack)));
        root.appendChild(node);

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("LongDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mLongDesc)));
        root.appendChild(node);

        node =  doc.createElement("ItemMappingCd");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMappingCd)));
        root.appendChild(node);

        node =  doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
        root.appendChild(node);

        node =  doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
        root.appendChild(node);

        node =  doc.createElement("StatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mStatusCd)));
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

        node =  doc.createElement("UomConvMultiplier");
        node.appendChild(doc.createTextNode(String.valueOf(mUomConvMultiplier)));
        root.appendChild(node);

        node =  doc.createElement("StandardProductList");
        node.appendChild(doc.createTextNode(String.valueOf(mStandardProductList)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the ItemMappingId field is not cloned.
    *
    * @return ItemMappingData object
    */
    public Object clone(){
        ItemMappingData myClone = new ItemMappingData();
        
        myClone.mItemId = mItemId;
        
        myClone.mBusEntityId = mBusEntityId;
        
        myClone.mItemNum = mItemNum;
        
        myClone.mItemUom = mItemUom;
        
        myClone.mItemPack = mItemPack;
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mLongDesc = mLongDesc;
        
        myClone.mItemMappingCd = mItemMappingCd;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
        if(mExpDate != null){
                myClone.mExpDate = (Date) mExpDate.clone();
        }
        
        myClone.mStatusCd = mStatusCd;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mUomConvMultiplier = mUomConvMultiplier;
        
        myClone.mStandardProductList = mStandardProductList;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (ItemMappingDataAccess.ITEM_MAPPING_ID.equals(pFieldName)) {
            return getItemMappingId();
        } else if (ItemMappingDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (ItemMappingDataAccess.BUS_ENTITY_ID.equals(pFieldName)) {
            return getBusEntityId();
        } else if (ItemMappingDataAccess.ITEM_NUM.equals(pFieldName)) {
            return getItemNum();
        } else if (ItemMappingDataAccess.ITEM_UOM.equals(pFieldName)) {
            return getItemUom();
        } else if (ItemMappingDataAccess.ITEM_PACK.equals(pFieldName)) {
            return getItemPack();
        } else if (ItemMappingDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (ItemMappingDataAccess.LONG_DESC.equals(pFieldName)) {
            return getLongDesc();
        } else if (ItemMappingDataAccess.ITEM_MAPPING_CD.equals(pFieldName)) {
            return getItemMappingCd();
        } else if (ItemMappingDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (ItemMappingDataAccess.EXP_DATE.equals(pFieldName)) {
            return getExpDate();
        } else if (ItemMappingDataAccess.STATUS_CD.equals(pFieldName)) {
            return getStatusCd();
        } else if (ItemMappingDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ItemMappingDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ItemMappingDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ItemMappingDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (ItemMappingDataAccess.UOM_CONV_MULTIPLIER.equals(pFieldName)) {
            return getUomConvMultiplier();
        } else if (ItemMappingDataAccess.STANDARD_PRODUCT_LIST.equals(pFieldName)) {
            return getStandardProductList();
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
        return ItemMappingDataAccess.CLW_ITEM_MAPPING;
    }

    
    /**
     * Sets the ItemMappingId field. This field is required to be set in the database.
     *
     * @param pItemMappingId
     *  int to use to update the field.
     */
    public void setItemMappingId(int pItemMappingId){
        this.mItemMappingId = pItemMappingId;
        setDirty(true);
    }
    /**
     * Retrieves the ItemMappingId field.
     *
     * @return
     *  int containing the ItemMappingId field.
     */
    public int getItemMappingId(){
        return mItemMappingId;
    }

    /**
     * Sets the ItemId field. This field is required to be set in the database.
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
     * Sets the ItemNum field.
     *
     * @param pItemNum
     *  String to use to update the field.
     */
    public void setItemNum(String pItemNum){
        this.mItemNum = pItemNum;
        setDirty(true);
    }
    /**
     * Retrieves the ItemNum field.
     *
     * @return
     *  String containing the ItemNum field.
     */
    public String getItemNum(){
        return mItemNum;
    }

    /**
     * Sets the ItemUom field.
     *
     * @param pItemUom
     *  String to use to update the field.
     */
    public void setItemUom(String pItemUom){
        this.mItemUom = pItemUom;
        setDirty(true);
    }
    /**
     * Retrieves the ItemUom field.
     *
     * @return
     *  String containing the ItemUom field.
     */
    public String getItemUom(){
        return mItemUom;
    }

    /**
     * Sets the ItemPack field.
     *
     * @param pItemPack
     *  String to use to update the field.
     */
    public void setItemPack(String pItemPack){
        this.mItemPack = pItemPack;
        setDirty(true);
    }
    /**
     * Retrieves the ItemPack field.
     *
     * @return
     *  String containing the ItemPack field.
     */
    public String getItemPack(){
        return mItemPack;
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
     * Sets the LongDesc field.
     *
     * @param pLongDesc
     *  String to use to update the field.
     */
    public void setLongDesc(String pLongDesc){
        this.mLongDesc = pLongDesc;
        setDirty(true);
    }
    /**
     * Retrieves the LongDesc field.
     *
     * @return
     *  String containing the LongDesc field.
     */
    public String getLongDesc(){
        return mLongDesc;
    }

    /**
     * Sets the ItemMappingCd field. This field is required to be set in the database.
     *
     * @param pItemMappingCd
     *  String to use to update the field.
     */
    public void setItemMappingCd(String pItemMappingCd){
        this.mItemMappingCd = pItemMappingCd;
        setDirty(true);
    }
    /**
     * Retrieves the ItemMappingCd field.
     *
     * @return
     *  String containing the ItemMappingCd field.
     */
    public String getItemMappingCd(){
        return mItemMappingCd;
    }

    /**
     * Sets the EffDate field.
     *
     * @param pEffDate
     *  Date to use to update the field.
     */
    public void setEffDate(Date pEffDate){
        this.mEffDate = pEffDate;
        setDirty(true);
    }
    /**
     * Retrieves the EffDate field.
     *
     * @return
     *  Date containing the EffDate field.
     */
    public Date getEffDate(){
        return mEffDate;
    }

    /**
     * Sets the ExpDate field.
     *
     * @param pExpDate
     *  Date to use to update the field.
     */
    public void setExpDate(Date pExpDate){
        this.mExpDate = pExpDate;
        setDirty(true);
    }
    /**
     * Retrieves the ExpDate field.
     *
     * @return
     *  Date containing the ExpDate field.
     */
    public Date getExpDate(){
        return mExpDate;
    }

    /**
     * Sets the StatusCd field.
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
     * Sets the UomConvMultiplier field.
     *
     * @param pUomConvMultiplier
     *  java.math.BigDecimal to use to update the field.
     */
    public void setUomConvMultiplier(java.math.BigDecimal pUomConvMultiplier){
        this.mUomConvMultiplier = pUomConvMultiplier;
        setDirty(true);
    }
    /**
     * Retrieves the UomConvMultiplier field.
     *
     * @return
     *  java.math.BigDecimal containing the UomConvMultiplier field.
     */
    public java.math.BigDecimal getUomConvMultiplier(){
        return mUomConvMultiplier;
    }

    /**
     * Sets the StandardProductList field.
     *
     * @param pStandardProductList
     *  String to use to update the field.
     */
    public void setStandardProductList(String pStandardProductList){
        this.mStandardProductList = pStandardProductList;
        setDirty(true);
    }
    /**
     * Retrieves the StandardProductList field.
     *
     * @return
     *  String containing the StandardProductList field.
     */
    public String getStandardProductList(){
        return mStandardProductList;
    }


}
