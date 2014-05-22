
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InvoiceCustDetailData
 * Description:  This is a ValueObject class wrapping the database table CLW_INVOICE_CUST_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.InvoiceCustDetailDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>InvoiceCustDetailData</code> is a ValueObject class wrapping of the database table CLW_INVOICE_CUST_DETAIL.
 */
public class InvoiceCustDetailData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 4811457931897783098L;
    private int mInvoiceCustDetailId;// SQL type:NUMBER, not null
    private int mInvoiceCustId;// SQL type:NUMBER, not null
    private String mInvoiceDetailStatusCd;// SQL type:VARCHAR2
    private int mOrderItemId;// SQL type:NUMBER
    private int mLineNumber;// SQL type:NUMBER
    private int mItemSkuNum;// SQL type:NUMBER
    private String mItemShortDesc;// SQL type:VARCHAR2
    private String mItemUom;// SQL type:VARCHAR2
    private String mItemPack;// SQL type:VARCHAR2
    private int mItemQuantity;// SQL type:NUMBER
    private java.math.BigDecimal mCustContractPrice;// SQL type:NUMBER
    private java.math.BigDecimal mLineTotal;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2
    private String mShipStatusCd;// SQL type:VARCHAR2
    private String mRebateStatusCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public InvoiceCustDetailData ()
    {
        mInvoiceDetailStatusCd = "";
        mItemShortDesc = "";
        mItemUom = "";
        mItemPack = "";
        mAddBy = "";
        mModBy = "";
        mShipStatusCd = "";
        mRebateStatusCd = "";
    }

    /**
     * Constructor.
     */
    public InvoiceCustDetailData(int parm1, int parm2, String parm3, int parm4, int parm5, int parm6, String parm7, String parm8, String parm9, int parm10, java.math.BigDecimal parm11, java.math.BigDecimal parm12, Date parm13, String parm14, Date parm15, String parm16, String parm17, String parm18)
    {
        mInvoiceCustDetailId = parm1;
        mInvoiceCustId = parm2;
        mInvoiceDetailStatusCd = parm3;
        mOrderItemId = parm4;
        mLineNumber = parm5;
        mItemSkuNum = parm6;
        mItemShortDesc = parm7;
        mItemUom = parm8;
        mItemPack = parm9;
        mItemQuantity = parm10;
        mCustContractPrice = parm11;
        mLineTotal = parm12;
        mAddDate = parm13;
        mAddBy = parm14;
        mModDate = parm15;
        mModBy = parm16;
        mShipStatusCd = parm17;
        mRebateStatusCd = parm18;
        
    }

    /**
     * Creates a new InvoiceCustDetailData
     *
     * @return
     *  Newly initialized InvoiceCustDetailData object.
     */
    public static InvoiceCustDetailData createValue ()
    {
        InvoiceCustDetailData valueData = new InvoiceCustDetailData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InvoiceCustDetailData object
     */
    public String toString()
    {
        return "[" + "InvoiceCustDetailId=" + mInvoiceCustDetailId + ", InvoiceCustId=" + mInvoiceCustId + ", InvoiceDetailStatusCd=" + mInvoiceDetailStatusCd + ", OrderItemId=" + mOrderItemId + ", LineNumber=" + mLineNumber + ", ItemSkuNum=" + mItemSkuNum + ", ItemShortDesc=" + mItemShortDesc + ", ItemUom=" + mItemUom + ", ItemPack=" + mItemPack + ", ItemQuantity=" + mItemQuantity + ", CustContractPrice=" + mCustContractPrice + ", LineTotal=" + mLineTotal + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", ShipStatusCd=" + mShipStatusCd + ", RebateStatusCd=" + mRebateStatusCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("InvoiceCustDetail");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mInvoiceCustDetailId));

        node =  doc.createElement("InvoiceCustId");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceCustId)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceDetailStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceDetailStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("OrderItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderItemId)));
        root.appendChild(node);

        node =  doc.createElement("LineNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mLineNumber)));
        root.appendChild(node);

        node =  doc.createElement("ItemSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mItemSkuNum)));
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

        node =  doc.createElement("CustContractPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mCustContractPrice)));
        root.appendChild(node);

        node =  doc.createElement("LineTotal");
        node.appendChild(doc.createTextNode(String.valueOf(mLineTotal)));
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

        node =  doc.createElement("ShipStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mShipStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("RebateStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mRebateStatusCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the InvoiceCustDetailId field is not cloned.
    *
    * @return InvoiceCustDetailData object
    */
    public Object clone(){
        InvoiceCustDetailData myClone = new InvoiceCustDetailData();
        
        myClone.mInvoiceCustId = mInvoiceCustId;
        
        myClone.mInvoiceDetailStatusCd = mInvoiceDetailStatusCd;
        
        myClone.mOrderItemId = mOrderItemId;
        
        myClone.mLineNumber = mLineNumber;
        
        myClone.mItemSkuNum = mItemSkuNum;
        
        myClone.mItemShortDesc = mItemShortDesc;
        
        myClone.mItemUom = mItemUom;
        
        myClone.mItemPack = mItemPack;
        
        myClone.mItemQuantity = mItemQuantity;
        
        myClone.mCustContractPrice = mCustContractPrice;
        
        myClone.mLineTotal = mLineTotal;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mShipStatusCd = mShipStatusCd;
        
        myClone.mRebateStatusCd = mRebateStatusCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (InvoiceCustDetailDataAccess.INVOICE_CUST_DETAIL_ID.equals(pFieldName)) {
            return getInvoiceCustDetailId();
        } else if (InvoiceCustDetailDataAccess.INVOICE_CUST_ID.equals(pFieldName)) {
            return getInvoiceCustId();
        } else if (InvoiceCustDetailDataAccess.INVOICE_DETAIL_STATUS_CD.equals(pFieldName)) {
            return getInvoiceDetailStatusCd();
        } else if (InvoiceCustDetailDataAccess.ORDER_ITEM_ID.equals(pFieldName)) {
            return getOrderItemId();
        } else if (InvoiceCustDetailDataAccess.LINE_NUMBER.equals(pFieldName)) {
            return getLineNumber();
        } else if (InvoiceCustDetailDataAccess.ITEM_SKU_NUM.equals(pFieldName)) {
            return getItemSkuNum();
        } else if (InvoiceCustDetailDataAccess.ITEM_SHORT_DESC.equals(pFieldName)) {
            return getItemShortDesc();
        } else if (InvoiceCustDetailDataAccess.ITEM_UOM.equals(pFieldName)) {
            return getItemUom();
        } else if (InvoiceCustDetailDataAccess.ITEM_PACK.equals(pFieldName)) {
            return getItemPack();
        } else if (InvoiceCustDetailDataAccess.ITEM_QUANTITY.equals(pFieldName)) {
            return getItemQuantity();
        } else if (InvoiceCustDetailDataAccess.CUST_CONTRACT_PRICE.equals(pFieldName)) {
            return getCustContractPrice();
        } else if (InvoiceCustDetailDataAccess.LINE_TOTAL.equals(pFieldName)) {
            return getLineTotal();
        } else if (InvoiceCustDetailDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (InvoiceCustDetailDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (InvoiceCustDetailDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (InvoiceCustDetailDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (InvoiceCustDetailDataAccess.SHIP_STATUS_CD.equals(pFieldName)) {
            return getShipStatusCd();
        } else if (InvoiceCustDetailDataAccess.REBATE_STATUS_CD.equals(pFieldName)) {
            return getRebateStatusCd();
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
        return InvoiceCustDetailDataAccess.CLW_INVOICE_CUST_DETAIL;
    }

    
    /**
     * Sets the InvoiceCustDetailId field. This field is required to be set in the database.
     *
     * @param pInvoiceCustDetailId
     *  int to use to update the field.
     */
    public void setInvoiceCustDetailId(int pInvoiceCustDetailId){
        this.mInvoiceCustDetailId = pInvoiceCustDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceCustDetailId field.
     *
     * @return
     *  int containing the InvoiceCustDetailId field.
     */
    public int getInvoiceCustDetailId(){
        return mInvoiceCustDetailId;
    }

    /**
     * Sets the InvoiceCustId field. This field is required to be set in the database.
     *
     * @param pInvoiceCustId
     *  int to use to update the field.
     */
    public void setInvoiceCustId(int pInvoiceCustId){
        this.mInvoiceCustId = pInvoiceCustId;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceCustId field.
     *
     * @return
     *  int containing the InvoiceCustId field.
     */
    public int getInvoiceCustId(){
        return mInvoiceCustId;
    }

    /**
     * Sets the InvoiceDetailStatusCd field.
     *
     * @param pInvoiceDetailStatusCd
     *  String to use to update the field.
     */
    public void setInvoiceDetailStatusCd(String pInvoiceDetailStatusCd){
        this.mInvoiceDetailStatusCd = pInvoiceDetailStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceDetailStatusCd field.
     *
     * @return
     *  String containing the InvoiceDetailStatusCd field.
     */
    public String getInvoiceDetailStatusCd(){
        return mInvoiceDetailStatusCd;
    }

    /**
     * Sets the OrderItemId field.
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
     * Sets the LineNumber field.
     *
     * @param pLineNumber
     *  int to use to update the field.
     */
    public void setLineNumber(int pLineNumber){
        this.mLineNumber = pLineNumber;
        setDirty(true);
    }
    /**
     * Retrieves the LineNumber field.
     *
     * @return
     *  int containing the LineNumber field.
     */
    public int getLineNumber(){
        return mLineNumber;
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
     * Sets the CustContractPrice field.
     *
     * @param pCustContractPrice
     *  java.math.BigDecimal to use to update the field.
     */
    public void setCustContractPrice(java.math.BigDecimal pCustContractPrice){
        this.mCustContractPrice = pCustContractPrice;
        setDirty(true);
    }
    /**
     * Retrieves the CustContractPrice field.
     *
     * @return
     *  java.math.BigDecimal containing the CustContractPrice field.
     */
    public java.math.BigDecimal getCustContractPrice(){
        return mCustContractPrice;
    }

    /**
     * Sets the LineTotal field.
     *
     * @param pLineTotal
     *  java.math.BigDecimal to use to update the field.
     */
    public void setLineTotal(java.math.BigDecimal pLineTotal){
        this.mLineTotal = pLineTotal;
        setDirty(true);
    }
    /**
     * Retrieves the LineTotal field.
     *
     * @return
     *  java.math.BigDecimal containing the LineTotal field.
     */
    public java.math.BigDecimal getLineTotal(){
        return mLineTotal;
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

    /**
     * Sets the ShipStatusCd field.
     *
     * @param pShipStatusCd
     *  String to use to update the field.
     */
    public void setShipStatusCd(String pShipStatusCd){
        this.mShipStatusCd = pShipStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the ShipStatusCd field.
     *
     * @return
     *  String containing the ShipStatusCd field.
     */
    public String getShipStatusCd(){
        return mShipStatusCd;
    }

    /**
     * Sets the RebateStatusCd field.
     *
     * @param pRebateStatusCd
     *  String to use to update the field.
     */
    public void setRebateStatusCd(String pRebateStatusCd){
        this.mRebateStatusCd = pRebateStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the RebateStatusCd field.
     *
     * @return
     *  String containing the RebateStatusCd field.
     */
    public String getRebateStatusCd(){
        return mRebateStatusCd;
    }


}
