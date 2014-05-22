
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PriceListDetailData
 * Description:  This is a ValueObject class wrapping the database table CLW_PRICE_LIST_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.PriceListDetailDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>PriceListDetailData</code> is a ValueObject class wrapping of the database table CLW_PRICE_LIST_DETAIL.
 */
public class PriceListDetailData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -1L;
    private int mPriceListDetailId;// SQL type:NUMBER, not null
    private int mItemId;// SQL type:NUMBER, not null
    private int mPriceListId;// SQL type:NUMBER, not null
    private java.math.BigDecimal mPrice;// SQL type:NUMBER
    private String mCustomerSkuNum;// SQL type:VARCHAR2
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public PriceListDetailData ()
    {
        mCustomerSkuNum = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor.
     */
    public PriceListDetailData(int parm1, int parm2, int parm3, java.math.BigDecimal parm4, String parm5, Date parm6, String parm7, Date parm8, String parm9)
    {
        mPriceListDetailId = parm1;
        mItemId = parm2;
        mPriceListId = parm3;
        mPrice = parm4;
        mCustomerSkuNum = parm5;
        mAddDate = parm6;
        mAddBy = parm7;
        mModDate = parm8;
        mModBy = parm9;
        
    }

    /**
     * Creates a new PriceListDetailData
     *
     * @return
     *  Newly initialized PriceListDetailData object.
     */
    public static PriceListDetailData createValue ()
    {
        PriceListDetailData valueData = new PriceListDetailData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PriceListDetailData object
     */
    public String toString()
    {
        return "[" + "PriceListDetailId=" + mPriceListDetailId + ", ItemId=" + mItemId + ", PriceListId=" + mPriceListId + ", Price=" + mPrice + ", CustomerSkuNum=" + mCustomerSkuNum + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("PriceListDetail");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mPriceListDetailId));

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("PriceListId");
        node.appendChild(doc.createTextNode(String.valueOf(mPriceListId)));
        root.appendChild(node);

        node =  doc.createElement("Price");
        node.appendChild(doc.createTextNode(String.valueOf(mPrice)));
        root.appendChild(node);

        node =  doc.createElement("CustomerSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerSkuNum)));
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
    * creates a clone of this object, the PriceListDetailId field is not cloned.
    *
    * @return PriceListDetailData object
    */
    public Object clone(){
        PriceListDetailData myClone = new PriceListDetailData();
        
        myClone.mItemId = mItemId;
        
        myClone.mPriceListId = mPriceListId;
        
        myClone.mPrice = mPrice;
        
        myClone.mCustomerSkuNum = mCustomerSkuNum;
        
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

        if (PriceListDetailDataAccess.PRICE_LIST_DETAIL_ID.equals(pFieldName)) {
            return getPriceListDetailId();
        } else if (PriceListDetailDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (PriceListDetailDataAccess.PRICE_LIST_ID.equals(pFieldName)) {
            return getPriceListId();
        } else if (PriceListDetailDataAccess.PRICE.equals(pFieldName)) {
            return getPrice();
        } else if (PriceListDetailDataAccess.CUSTOMER_SKU_NUM.equals(pFieldName)) {
            return getCustomerSkuNum();
        } else if (PriceListDetailDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (PriceListDetailDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (PriceListDetailDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (PriceListDetailDataAccess.MOD_BY.equals(pFieldName)) {
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
        return PriceListDetailDataAccess.CLW_PRICE_LIST_DETAIL;
    }

    
    /**
     * Sets the PriceListDetailId field. This field is required to be set in the database.
     *
     * @param pPriceListDetailId
     *  int to use to update the field.
     */
    public void setPriceListDetailId(int pPriceListDetailId){
        this.mPriceListDetailId = pPriceListDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the PriceListDetailId field.
     *
     * @return
     *  int containing the PriceListDetailId field.
     */
    public int getPriceListDetailId(){
        return mPriceListDetailId;
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
     * Sets the PriceListId field. This field is required to be set in the database.
     *
     * @param pPriceListId
     *  int to use to update the field.
     */
    public void setPriceListId(int pPriceListId){
        this.mPriceListId = pPriceListId;
        setDirty(true);
    }
    /**
     * Retrieves the PriceListId field.
     *
     * @return
     *  int containing the PriceListId field.
     */
    public int getPriceListId(){
        return mPriceListId;
    }

    /**
     * Sets the Price field.
     *
     * @param pPrice
     *  java.math.BigDecimal to use to update the field.
     */
    public void setPrice(java.math.BigDecimal pPrice){
        this.mPrice = pPrice;
        setDirty(true);
    }
    /**
     * Retrieves the Price field.
     *
     * @return
     *  java.math.BigDecimal containing the Price field.
     */
    public java.math.BigDecimal getPrice(){
        return mPrice;
    }

    /**
     * Sets the CustomerSkuNum field.
     *
     * @param pCustomerSkuNum
     *  String to use to update the field.
     */
    public void setCustomerSkuNum(String pCustomerSkuNum){
        this.mCustomerSkuNum = pCustomerSkuNum;
        setDirty(true);
    }
    /**
     * Retrieves the CustomerSkuNum field.
     *
     * @return
     *  String containing the CustomerSkuNum field.
     */
    public String getCustomerSkuNum(){
        return mCustomerSkuNum;
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
