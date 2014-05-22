
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ItemSubstitutionData
 * Description:  This is a ValueObject class wrapping the database table CLW_ITEM_SUBSTITUTION.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.ItemSubstitutionDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>ItemSubstitutionData</code> is a ValueObject class wrapping of the database table CLW_ITEM_SUBSTITUTION.
 */
public class ItemSubstitutionData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 5331562793817579113L;
    private int mItemSubstitutionId;// SQL type:NUMBER, not null
    private int mOrderId;// SQL type:NUMBER, not null
    private int mOrderItemId;// SQL type:NUMBER, not null
    private int mItemSkuNum;// SQL type:NUMBER
    private String mDistItemSkuNum;// SQL type:VARCHAR2
    private String mManuItemSkuNum;// SQL type:VARCHAR2
    private String mItemShortDesc;// SQL type:VARCHAR2
    private String mItemUom;// SQL type:VARCHAR2
    private String mItemPack;// SQL type:VARCHAR2
    private int mItemQuantity;// SQL type:NUMBER
    private java.math.BigDecimal mItemDistCost;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public ItemSubstitutionData ()
    {
        mDistItemSkuNum = "";
        mManuItemSkuNum = "";
        mItemShortDesc = "";
        mItemUom = "";
        mItemPack = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public ItemSubstitutionData(int parm1, int parm2, int parm3, int parm4, String parm5, String parm6, String parm7, String parm8, String parm9, int parm10, java.math.BigDecimal parm11, Date parm12, String parm13, Date parm14, String parm15)
    {
        mItemSubstitutionId = parm1;
        mOrderId = parm2;
        mOrderItemId = parm3;
        mItemSkuNum = parm4;
        mDistItemSkuNum = parm5;
        mManuItemSkuNum = parm6;
        mItemShortDesc = parm7;
        mItemUom = parm8;
        mItemPack = parm9;
        mItemQuantity = parm10;
        mItemDistCost = parm11;
        mAddDate = parm12;
        mAddBy = parm13;
        mModDate = parm14;
        mModBy = parm15;
        
    }

    /**
     * Creates a new ItemSubstitutionData
     *
     * @return
     *  Newly initialized ItemSubstitutionData object.
     */
    public static ItemSubstitutionData createValue ()
    {
        ItemSubstitutionData valueData = new ItemSubstitutionData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ItemSubstitutionData object
     */
    public String toString()
    {
        return "[" + "ItemSubstitutionId=" + mItemSubstitutionId + ", OrderId=" + mOrderId + ", OrderItemId=" + mOrderItemId + ", ItemSkuNum=" + mItemSkuNum + ", DistItemSkuNum=" + mDistItemSkuNum + ", ManuItemSkuNum=" + mManuItemSkuNum + ", ItemShortDesc=" + mItemShortDesc + ", ItemUom=" + mItemUom + ", ItemPack=" + mItemPack + ", ItemQuantity=" + mItemQuantity + ", ItemDistCost=" + mItemDistCost + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("ItemSubstitution");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mItemSubstitutionId));

        node =  doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node =  doc.createElement("OrderItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderItemId)));
        root.appendChild(node);

        node =  doc.createElement("ItemSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mItemSkuNum)));
        root.appendChild(node);

        node =  doc.createElement("DistItemSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemSkuNum)));
        root.appendChild(node);

        node =  doc.createElement("ManuItemSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mManuItemSkuNum)));
        root.appendChild(node);

        node =  doc.createElement("ItemShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mItemShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("ItemUom");
        node.appendChild(doc.createTextNode(String.valueOf(mItemUom)));
        root.appendChild(node);

        node =  doc.createElement("ItemPack");
        node.appendChild(doc.createTextNode(String.valueOf(mItemPack)));
        root.appendChild(node);

        node =  doc.createElement("ItemQuantity");
        node.appendChild(doc.createTextNode(String.valueOf(mItemQuantity)));
        root.appendChild(node);

        node =  doc.createElement("ItemDistCost");
        node.appendChild(doc.createTextNode(String.valueOf(mItemDistCost)));
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
    * creates a clone of this object, the ItemSubstitutionId field is not cloned.
    *
    * @return ItemSubstitutionData object
    */
    public Object clone(){
        ItemSubstitutionData myClone = new ItemSubstitutionData();
        
        myClone.mOrderId = mOrderId;
        
        myClone.mOrderItemId = mOrderItemId;
        
        myClone.mItemSkuNum = mItemSkuNum;
        
        myClone.mDistItemSkuNum = mDistItemSkuNum;
        
        myClone.mManuItemSkuNum = mManuItemSkuNum;
        
        myClone.mItemShortDesc = mItemShortDesc;
        
        myClone.mItemUom = mItemUom;
        
        myClone.mItemPack = mItemPack;
        
        myClone.mItemQuantity = mItemQuantity;
        
        myClone.mItemDistCost = mItemDistCost;
        
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

        if (ItemSubstitutionDataAccess.ITEM_SUBSTITUTION_ID.equals(pFieldName)) {
            return getItemSubstitutionId();
        } else if (ItemSubstitutionDataAccess.ORDER_ID.equals(pFieldName)) {
            return getOrderId();
        } else if (ItemSubstitutionDataAccess.ORDER_ITEM_ID.equals(pFieldName)) {
            return getOrderItemId();
        } else if (ItemSubstitutionDataAccess.ITEM_SKU_NUM.equals(pFieldName)) {
            return getItemSkuNum();
        } else if (ItemSubstitutionDataAccess.DIST_ITEM_SKU_NUM.equals(pFieldName)) {
            return getDistItemSkuNum();
        } else if (ItemSubstitutionDataAccess.MANU_ITEM_SKU_NUM.equals(pFieldName)) {
            return getManuItemSkuNum();
        } else if (ItemSubstitutionDataAccess.ITEM_SHORT_DESC.equals(pFieldName)) {
            return getItemShortDesc();
        } else if (ItemSubstitutionDataAccess.ITEM_UOM.equals(pFieldName)) {
            return getItemUom();
        } else if (ItemSubstitutionDataAccess.ITEM_PACK.equals(pFieldName)) {
            return getItemPack();
        } else if (ItemSubstitutionDataAccess.ITEM_QUANTITY.equals(pFieldName)) {
            return getItemQuantity();
        } else if (ItemSubstitutionDataAccess.ITEM_DIST_COST.equals(pFieldName)) {
            return getItemDistCost();
        } else if (ItemSubstitutionDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (ItemSubstitutionDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (ItemSubstitutionDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (ItemSubstitutionDataAccess.MOD_BY.equals(pFieldName)) {
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
        return ItemSubstitutionDataAccess.CLW_ITEM_SUBSTITUTION;
    }

    
    /**
     * Sets the ItemSubstitutionId field. This field is required to be set in the database.
     *
     * @param pItemSubstitutionId
     *  int to use to update the field.
     */
    public void setItemSubstitutionId(int pItemSubstitutionId){
        this.mItemSubstitutionId = pItemSubstitutionId;
        setDirty(true);
    }
    /**
     * Retrieves the ItemSubstitutionId field.
     *
     * @return
     *  int containing the ItemSubstitutionId field.
     */
    public int getItemSubstitutionId(){
        return mItemSubstitutionId;
    }

    /**
     * Sets the OrderId field. This field is required to be set in the database.
     *
     * @param pOrderId
     *  int to use to update the field.
     */
    public void setOrderId(int pOrderId){
        this.mOrderId = pOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderId field.
     *
     * @return
     *  int containing the OrderId field.
     */
    public int getOrderId(){
        return mOrderId;
    }

    /**
     * Sets the OrderItemId field. This field is required to be set in the database.
     *
     * @param pOrderItemId
     *  int to use to update the field.
     */
    public void setOrderItemId(int pOrderItemId){
        this.mOrderItemId = pOrderItemId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderItemId field.
     *
     * @return
     *  int containing the OrderItemId field.
     */
    public int getOrderItemId(){
        return mOrderItemId;
    }

    /**
     * Sets the ItemSkuNum field.
     *
     * @param pItemSkuNum
     *  int to use to update the field.
     */
    public void setItemSkuNum(int pItemSkuNum){
        this.mItemSkuNum = pItemSkuNum;
        setDirty(true);
    }
    /**
     * Retrieves the ItemSkuNum field.
     *
     * @return
     *  int containing the ItemSkuNum field.
     */
    public int getItemSkuNum(){
        return mItemSkuNum;
    }

    /**
     * Sets the DistItemSkuNum field.
     *
     * @param pDistItemSkuNum
     *  String to use to update the field.
     */
    public void setDistItemSkuNum(String pDistItemSkuNum){
        this.mDistItemSkuNum = pDistItemSkuNum;
        setDirty(true);
    }
    /**
     * Retrieves the DistItemSkuNum field.
     *
     * @return
     *  String containing the DistItemSkuNum field.
     */
    public String getDistItemSkuNum(){
        return mDistItemSkuNum;
    }

    /**
     * Sets the ManuItemSkuNum field.
     *
     * @param pManuItemSkuNum
     *  String to use to update the field.
     */
    public void setManuItemSkuNum(String pManuItemSkuNum){
        this.mManuItemSkuNum = pManuItemSkuNum;
        setDirty(true);
    }
    /**
     * Retrieves the ManuItemSkuNum field.
     *
     * @return
     *  String containing the ManuItemSkuNum field.
     */
    public String getManuItemSkuNum(){
        return mManuItemSkuNum;
    }

    /**
     * Sets the ItemShortDesc field.
     *
     * @param pItemShortDesc
     *  String to use to update the field.
     */
    public void setItemShortDesc(String pItemShortDesc){
        this.mItemShortDesc = pItemShortDesc;
        setDirty(true);
    }
    /**
     * Retrieves the ItemShortDesc field.
     *
     * @return
     *  String containing the ItemShortDesc field.
     */
    public String getItemShortDesc(){
        return mItemShortDesc;
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
     * Sets the ItemQuantity field.
     *
     * @param pItemQuantity
     *  int to use to update the field.
     */
    public void setItemQuantity(int pItemQuantity){
        this.mItemQuantity = pItemQuantity;
        setDirty(true);
    }
    /**
     * Retrieves the ItemQuantity field.
     *
     * @return
     *  int containing the ItemQuantity field.
     */
    public int getItemQuantity(){
        return mItemQuantity;
    }

    /**
     * Sets the ItemDistCost field.
     *
     * @param pItemDistCost
     *  java.math.BigDecimal to use to update the field.
     */
    public void setItemDistCost(java.math.BigDecimal pItemDistCost){
        this.mItemDistCost = pItemDistCost;
        setDirty(true);
    }
    /**
     * Retrieves the ItemDistCost field.
     *
     * @return
     *  java.math.BigDecimal containing the ItemDistCost field.
     */
    public java.math.BigDecimal getItemDistCost(){
        return mItemDistCost;
    }

    /**
     * Sets the AddDate field.
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
     * Sets the ModDate field.
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
