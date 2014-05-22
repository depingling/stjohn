
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ItemData
 * Description:  This is a ValueObject class wrapping the database table CLW_ITEM.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ItemDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ItemData</code> is a ValueObject class wrapping of the database table CLW_ITEM.
 */
public class ItemData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 6695301648752773505L;
    private int mItemId;// SQL type:NUMBER, not null
    private String mShortDesc;// SQL type:VARCHAR2
    private int mSkuNum;// SQL type:NUMBER, not null
    private String mLongDesc;// SQL type:VARCHAR2
    private Date mEffDate;// SQL type:DATE
    private Date mExpDate;// SQL type:DATE
    private String mItemTypeCd;// SQL type:VARCHAR2, not null
    private String mItemStatusCd;// SQL type:VARCHAR2, not null
    private int mItemOrderNum;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ItemData ()
    {
        mShortDesc = "";
        mLongDesc = "";
        mItemTypeCd = "";
        mItemStatusCd = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ItemData(int parm1, String parm2, int parm3, String parm4, Date parm5, Date parm6, String parm7, String parm8, int parm9, Date parm10, String parm11, Date parm12, String parm13)
    {
        mItemId = parm1;
        mShortDesc = parm2;
        mSkuNum = parm3;
        mLongDesc = parm4;
        mEffDate = parm5;
        mExpDate = parm6;
        mItemTypeCd = parm7;
        mItemStatusCd = parm8;
        mItemOrderNum = parm9;
        mAddDate = parm10;
        mAddBy = parm11;
        mModDate = parm12;
        mModBy = parm13;
        
    }

    /**
     * Creates a new ItemData
     *
     * @return
     *  Newly initialized ItemData object.
     */
    public static ItemData createValue ()
    {
        ItemData valueData = new ItemData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ItemData object
     */
    public String toString()
    {
        return "[" + "ItemId=" + mItemId + ", ShortDesc=" + mShortDesc + ", SkuNum=" + mSkuNum + ", LongDesc=" + mLongDesc + ", EffDate=" + mEffDate + ", ExpDate=" + mExpDate + ", ItemTypeCd=" + mItemTypeCd + ", ItemStatusCd=" + mItemStatusCd + ", ItemOrderNum=" + mItemOrderNum + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("Item");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mItemId));

        node =  doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("SkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSkuNum)));
        root.appendChild(node);

        node =  doc.createElement("LongDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mLongDesc)));
        root.appendChild(node);

        node =  doc.createElement("EffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mEffDate)));
        root.appendChild(node);

        node =  doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
        root.appendChild(node);

        node =  doc.createElement("ItemTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mItemTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("ItemStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mItemStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("ItemOrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mItemOrderNum)));
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
    * creates a clone of this object, the ItemId field is not cloned.
    *
    * @return ItemData object
    */
    public Object clone(){
        ItemData myClone = new ItemData();
        
        myClone.mShortDesc = mShortDesc;
        
        myClone.mSkuNum = mSkuNum;
        
        myClone.mLongDesc = mLongDesc;
        
        if(mEffDate != null){
                myClone.mEffDate = (Date) mEffDate.clone();
        }
        
        if(mExpDate != null){
                myClone.mExpDate = (Date) mExpDate.clone();
        }
        
        myClone.mItemTypeCd = mItemTypeCd;
        
        myClone.mItemStatusCd = mItemStatusCd;
        
        myClone.mItemOrderNum = mItemOrderNum;
        
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

        if (ItemDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (ItemDataAccess.SHORT_DESC.equals(pFieldName)) {
            return getShortDesc();
        } else if (ItemDataAccess.SKU_NUM.equals(pFieldName)) {
            return getSkuNum();
        } else if (ItemDataAccess.LONG_DESC.equals(pFieldName)) {
            return getLongDesc();
        } else if (ItemDataAccess.EFF_DATE.equals(pFieldName)) {
            return getEffDate();
        } else if (ItemDataAccess.EXP_DATE.equals(pFieldName)) {
            return getExpDate();
        } else if (ItemDataAccess.ITEM_TYPE_CD.equals(pFieldName)) {
            return getItemTypeCd();
        } else if (ItemDataAccess.ITEM_STATUS_CD.equals(pFieldName)) {
            return getItemStatusCd();
        } else if (ItemDataAccess.ITEM_ORDER_NUM.equals(pFieldName)) {
            return getItemOrderNum();
        } else if (ItemDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ItemDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ItemDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ItemDataAccess.MOD_BY.equals(pFieldName)) {
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
        return ItemDataAccess.CLW_ITEM;
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
     * Sets the SkuNum field. This field is required to be set in the database.
     *
     * @param pSkuNum
     *  int to use to update the field.
     */
    public void setSkuNum(int pSkuNum){
        this.mSkuNum = pSkuNum;
        setDirty(true);
    }
    /**
     * Retrieves the SkuNum field.
     *
     * @return
     *  int containing the SkuNum field.
     */
    public int getSkuNum(){
        return mSkuNum;
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
     * Sets the ItemTypeCd field. This field is required to be set in the database.
     *
     * @param pItemTypeCd
     *  String to use to update the field.
     */
    public void setItemTypeCd(String pItemTypeCd){
        this.mItemTypeCd = pItemTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the ItemTypeCd field.
     *
     * @return
     *  String containing the ItemTypeCd field.
     */
    public String getItemTypeCd(){
        return mItemTypeCd;
    }

    /**
     * Sets the ItemStatusCd field. This field is required to be set in the database.
     *
     * @param pItemStatusCd
     *  String to use to update the field.
     */
    public void setItemStatusCd(String pItemStatusCd){
        this.mItemStatusCd = pItemStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the ItemStatusCd field.
     *
     * @return
     *  String containing the ItemStatusCd field.
     */
    public String getItemStatusCd(){
        return mItemStatusCd;
    }

    /**
     * Sets the ItemOrderNum field.
     *
     * @param pItemOrderNum
     *  int to use to update the field.
     */
    public void setItemOrderNum(int pItemOrderNum){
        this.mItemOrderNum = pItemOrderNum;
        setDirty(true);
    }
    /**
     * Retrieves the ItemOrderNum field.
     *
     * @return
     *  int containing the ItemOrderNum field.
     */
    public int getItemOrderNum(){
        return mItemOrderNum;
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
