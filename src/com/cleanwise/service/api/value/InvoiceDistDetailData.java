
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InvoiceDistDetailData
 * Description:  This is a ValueObject class wrapping the database table CLW_INVOICE_DIST_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.InvoiceDistDetailDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>InvoiceDistDetailData</code> is a ValueObject class wrapping of the database table CLW_INVOICE_DIST_DETAIL.
 */
public class InvoiceDistDetailData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = -6548643520607157297L;
    private int mInvoiceDistDetailId;// SQL type:NUMBER, not null
    private int mInvoiceDistId;// SQL type:NUMBER, not null
    private int mInvoiceCustId;// SQL type:NUMBER
    private int mInvoiceCustDetailId;// SQL type:NUMBER
    private int mOrderItemId;// SQL type:NUMBER
    private int mErpPoLineNum;// SQL type:NUMBER
    private int mDistLineNumber;// SQL type:NUMBER
    private String mDistItemSkuNum;// SQL type:VARCHAR2
    private String mDistItemShortDesc;// SQL type:VARCHAR2
    private String mDistItemUom;// SQL type:VARCHAR2
    private String mDistItemPack;// SQL type:VARCHAR2
    private int mDistItemQuantity;// SQL type:NUMBER
    private int mItemSkuNum;// SQL type:NUMBER
    private String mItemShortDesc;// SQL type:VARCHAR2
    private String mItemUom;// SQL type:VARCHAR2
    private String mItemPack;// SQL type:VARCHAR2
    private int mItemQuantity;// SQL type:NUMBER
    private java.math.BigDecimal mLineTotal;// SQL type:NUMBER
    private java.math.BigDecimal mItemCost;// SQL type:NUMBER
    private java.math.BigDecimal mAdjustedCost;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2
    private java.math.BigDecimal mItemReceivedCost;// SQL type:NUMBER
    private int mDistItemQtyReceived;// SQL type:NUMBER
    private String mErpAccountCode;// SQL type:VARCHAR2
    private int mErpPoRefLineNum;// SQL type:NUMBER
    private String mInvoiceDistSkuNum;// SQL type:VARCHAR2
    private java.math.BigDecimal mDistIntoStockCost;// SQL type:NUMBER
    private String mShipStatusCd;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public InvoiceDistDetailData ()
    {
        mDistItemSkuNum = "";
        mDistItemShortDesc = "";
        mDistItemUom = "";
        mDistItemPack = "";
        mItemShortDesc = "";
        mItemUom = "";
        mItemPack = "";
        mAddBy = "";
        mModBy = "";
        mErpAccountCode = "";
        mInvoiceDistSkuNum = "";
        mShipStatusCd = "";
    }

    /**
     * Constructor.
     */
    public InvoiceDistDetailData(int parm1, int parm2, int parm3, int parm4, int parm5, int parm6, int parm7, String parm8, String parm9, String parm10, String parm11, int parm12, int parm13, String parm14, String parm15, String parm16, int parm17, java.math.BigDecimal parm18, java.math.BigDecimal parm19, java.math.BigDecimal parm20, Date parm21, String parm22, Date parm23, String parm24, java.math.BigDecimal parm25, int parm26, String parm27, int parm28, String parm29, java.math.BigDecimal parm30, String parm31)
    {
        mInvoiceDistDetailId = parm1;
        mInvoiceDistId = parm2;
        mInvoiceCustId = parm3;
        mInvoiceCustDetailId = parm4;
        mOrderItemId = parm5;
        mErpPoLineNum = parm6;
        mDistLineNumber = parm7;
        mDistItemSkuNum = parm8;
        mDistItemShortDesc = parm9;
        mDistItemUom = parm10;
        mDistItemPack = parm11;
        mDistItemQuantity = parm12;
        mItemSkuNum = parm13;
        mItemShortDesc = parm14;
        mItemUom = parm15;
        mItemPack = parm16;
        mItemQuantity = parm17;
        mLineTotal = parm18;
        mItemCost = parm19;
        mAdjustedCost = parm20;
        mAddDate = parm21;
        mAddBy = parm22;
        mModDate = parm23;
        mModBy = parm24;
        mItemReceivedCost = parm25;
        mDistItemQtyReceived = parm26;
        mErpAccountCode = parm27;
        mErpPoRefLineNum = parm28;
        mInvoiceDistSkuNum = parm29;
        mDistIntoStockCost = parm30;
        mShipStatusCd = parm31;
        
    }

    /**
     * Creates a new InvoiceDistDetailData
     *
     * @return
     *  Newly initialized InvoiceDistDetailData object.
     */
    public static InvoiceDistDetailData createValue ()
    {
        InvoiceDistDetailData valueData = new InvoiceDistDetailData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InvoiceDistDetailData object
     */
    public String toString()
    {
        return "[" + "InvoiceDistDetailId=" + mInvoiceDistDetailId + ", InvoiceDistId=" + mInvoiceDistId + ", InvoiceCustId=" + mInvoiceCustId + ", InvoiceCustDetailId=" + mInvoiceCustDetailId + ", OrderItemId=" + mOrderItemId + ", ErpPoLineNum=" + mErpPoLineNum + ", DistLineNumber=" + mDistLineNumber + ", DistItemSkuNum=" + mDistItemSkuNum + ", DistItemShortDesc=" + mDistItemShortDesc + ", DistItemUom=" + mDistItemUom + ", DistItemPack=" + mDistItemPack + ", DistItemQuantity=" + mDistItemQuantity + ", ItemSkuNum=" + mItemSkuNum + ", ItemShortDesc=" + mItemShortDesc + ", ItemUom=" + mItemUom + ", ItemPack=" + mItemPack + ", ItemQuantity=" + mItemQuantity + ", LineTotal=" + mLineTotal + ", ItemCost=" + mItemCost + ", AdjustedCost=" + mAdjustedCost + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", ItemReceivedCost=" + mItemReceivedCost + ", DistItemQtyReceived=" + mDistItemQtyReceived + ", ErpAccountCode=" + mErpAccountCode + ", ErpPoRefLineNum=" + mErpPoRefLineNum + ", InvoiceDistSkuNum=" + mInvoiceDistSkuNum + ", DistIntoStockCost=" + mDistIntoStockCost + ", ShipStatusCd=" + mShipStatusCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("InvoiceDistDetail");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mInvoiceDistDetailId));

        node =  doc.createElement("InvoiceDistId");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceDistId)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceCustId");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceCustId)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceCustDetailId");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceCustDetailId)));
        root.appendChild(node);

        node =  doc.createElement("OrderItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderItemId)));
        root.appendChild(node);

        node =  doc.createElement("ErpPoLineNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpPoLineNum)));
        root.appendChild(node);

        node =  doc.createElement("DistLineNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mDistLineNumber)));
        root.appendChild(node);

        node =  doc.createElement("DistItemSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemSkuNum)));
        root.appendChild(node);

        node =  doc.createElement("DistItemShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemShortDesc)));
        root.appendChild(node);

        node =  doc.createElement("DistItemUom");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemUom)));
        root.appendChild(node);

        node =  doc.createElement("DistItemPack");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemPack)));
        root.appendChild(node);

        node =  doc.createElement("DistItemQuantity");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemQuantity)));
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

        node =  doc.createElement("LineTotal");
        node.appendChild(doc.createTextNode(String.valueOf(mLineTotal)));
        root.appendChild(node);

        node =  doc.createElement("ItemCost");
        node.appendChild(doc.createTextNode(String.valueOf(mItemCost)));
        root.appendChild(node);

        node =  doc.createElement("AdjustedCost");
        node.appendChild(doc.createTextNode(String.valueOf(mAdjustedCost)));
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

        node =  doc.createElement("ItemReceivedCost");
        node.appendChild(doc.createTextNode(String.valueOf(mItemReceivedCost)));
        root.appendChild(node);

        node =  doc.createElement("DistItemQtyReceived");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemQtyReceived)));
        root.appendChild(node);

        node =  doc.createElement("ErpAccountCode");
        node.appendChild(doc.createTextNode(String.valueOf(mErpAccountCode)));
        root.appendChild(node);

        node =  doc.createElement("ErpPoRefLineNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpPoRefLineNum)));
        root.appendChild(node);

        node =  doc.createElement("InvoiceDistSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceDistSkuNum)));
        root.appendChild(node);

        node =  doc.createElement("DistIntoStockCost");
        node.appendChild(doc.createTextNode(String.valueOf(mDistIntoStockCost)));
        root.appendChild(node);

        node =  doc.createElement("ShipStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mShipStatusCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the InvoiceDistDetailId field is not cloned.
    *
    * @return InvoiceDistDetailData object
    */
    public Object clone(){
        InvoiceDistDetailData myClone = new InvoiceDistDetailData();
        
        myClone.mInvoiceDistId = mInvoiceDistId;
        
        myClone.mInvoiceCustId = mInvoiceCustId;
        
        myClone.mInvoiceCustDetailId = mInvoiceCustDetailId;
        
        myClone.mOrderItemId = mOrderItemId;
        
        myClone.mErpPoLineNum = mErpPoLineNum;
        
        myClone.mDistLineNumber = mDistLineNumber;
        
        myClone.mDistItemSkuNum = mDistItemSkuNum;
        
        myClone.mDistItemShortDesc = mDistItemShortDesc;
        
        myClone.mDistItemUom = mDistItemUom;
        
        myClone.mDistItemPack = mDistItemPack;
        
        myClone.mDistItemQuantity = mDistItemQuantity;
        
        myClone.mItemSkuNum = mItemSkuNum;
        
        myClone.mItemShortDesc = mItemShortDesc;
        
        myClone.mItemUom = mItemUom;
        
        myClone.mItemPack = mItemPack;
        
        myClone.mItemQuantity = mItemQuantity;
        
        myClone.mLineTotal = mLineTotal;
        
        myClone.mItemCost = mItemCost;
        
        myClone.mAdjustedCost = mAdjustedCost;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mItemReceivedCost = mItemReceivedCost;
        
        myClone.mDistItemQtyReceived = mDistItemQtyReceived;
        
        myClone.mErpAccountCode = mErpAccountCode;
        
        myClone.mErpPoRefLineNum = mErpPoRefLineNum;
        
        myClone.mInvoiceDistSkuNum = mInvoiceDistSkuNum;
        
        myClone.mDistIntoStockCost = mDistIntoStockCost;
        
        myClone.mShipStatusCd = mShipStatusCd;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (InvoiceDistDetailDataAccess.INVOICE_DIST_DETAIL_ID.equals(pFieldName)) {
            return getInvoiceDistDetailId();
        } else if (InvoiceDistDetailDataAccess.INVOICE_DIST_ID.equals(pFieldName)) {
            return getInvoiceDistId();
        } else if (InvoiceDistDetailDataAccess.INVOICE_CUST_ID.equals(pFieldName)) {
            return getInvoiceCustId();
        } else if (InvoiceDistDetailDataAccess.INVOICE_CUST_DETAIL_ID.equals(pFieldName)) {
            return getInvoiceCustDetailId();
        } else if (InvoiceDistDetailDataAccess.ORDER_ITEM_ID.equals(pFieldName)) {
            return getOrderItemId();
        } else if (InvoiceDistDetailDataAccess.ERP_PO_LINE_NUM.equals(pFieldName)) {
            return getErpPoLineNum();
        } else if (InvoiceDistDetailDataAccess.DIST_LINE_NUMBER.equals(pFieldName)) {
            return getDistLineNumber();
        } else if (InvoiceDistDetailDataAccess.DIST_ITEM_SKU_NUM.equals(pFieldName)) {
            return getDistItemSkuNum();
        } else if (InvoiceDistDetailDataAccess.DIST_ITEM_SHORT_DESC.equals(pFieldName)) {
            return getDistItemShortDesc();
        } else if (InvoiceDistDetailDataAccess.DIST_ITEM_UOM.equals(pFieldName)) {
            return getDistItemUom();
        } else if (InvoiceDistDetailDataAccess.DIST_ITEM_PACK.equals(pFieldName)) {
            return getDistItemPack();
        } else if (InvoiceDistDetailDataAccess.DIST_ITEM_QUANTITY.equals(pFieldName)) {
            return getDistItemQuantity();
        } else if (InvoiceDistDetailDataAccess.ITEM_SKU_NUM.equals(pFieldName)) {
            return getItemSkuNum();
        } else if (InvoiceDistDetailDataAccess.ITEM_SHORT_DESC.equals(pFieldName)) {
            return getItemShortDesc();
        } else if (InvoiceDistDetailDataAccess.ITEM_UOM.equals(pFieldName)) {
            return getItemUom();
        } else if (InvoiceDistDetailDataAccess.ITEM_PACK.equals(pFieldName)) {
            return getItemPack();
        } else if (InvoiceDistDetailDataAccess.ITEM_QUANTITY.equals(pFieldName)) {
            return getItemQuantity();
        } else if (InvoiceDistDetailDataAccess.LINE_TOTAL.equals(pFieldName)) {
            return getLineTotal();
        } else if (InvoiceDistDetailDataAccess.ITEM_COST.equals(pFieldName)) {
            return getItemCost();
        } else if (InvoiceDistDetailDataAccess.ADJUSTED_COST.equals(pFieldName)) {
            return getAdjustedCost();
        } else if (InvoiceDistDetailDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (InvoiceDistDetailDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (InvoiceDistDetailDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (InvoiceDistDetailDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (InvoiceDistDetailDataAccess.ITEM_RECEIVED_COST.equals(pFieldName)) {
            return getItemReceivedCost();
        } else if (InvoiceDistDetailDataAccess.DIST_ITEM_QTY_RECEIVED.equals(pFieldName)) {
            return getDistItemQtyReceived();
        } else if (InvoiceDistDetailDataAccess.ERP_ACCOUNT_CODE.equals(pFieldName)) {
            return getErpAccountCode();
        } else if (InvoiceDistDetailDataAccess.ERP_PO_REF_LINE_NUM.equals(pFieldName)) {
            return getErpPoRefLineNum();
        } else if (InvoiceDistDetailDataAccess.INVOICE_DIST_SKU_NUM.equals(pFieldName)) {
            return getInvoiceDistSkuNum();
        } else if (InvoiceDistDetailDataAccess.DIST_INTO_STOCK_COST.equals(pFieldName)) {
            return getDistIntoStockCost();
        } else if (InvoiceDistDetailDataAccess.SHIP_STATUS_CD.equals(pFieldName)) {
            return getShipStatusCd();
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
        return InvoiceDistDetailDataAccess.CLW_INVOICE_DIST_DETAIL;
    }

    
    /**
     * Sets the InvoiceDistDetailId field. This field is required to be set in the database.
     *
     * @param pInvoiceDistDetailId
     *  int to use to update the field.
     */
    public void setInvoiceDistDetailId(int pInvoiceDistDetailId){
        this.mInvoiceDistDetailId = pInvoiceDistDetailId;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceDistDetailId field.
     *
     * @return
     *  int containing the InvoiceDistDetailId field.
     */
    public int getInvoiceDistDetailId(){
        return mInvoiceDistDetailId;
    }

    /**
     * Sets the InvoiceDistId field. This field is required to be set in the database.
     *
     * @param pInvoiceDistId
     *  int to use to update the field.
     */
    public void setInvoiceDistId(int pInvoiceDistId){
        this.mInvoiceDistId = pInvoiceDistId;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceDistId field.
     *
     * @return
     *  int containing the InvoiceDistId field.
     */
    public int getInvoiceDistId(){
        return mInvoiceDistId;
    }

    /**
     * Sets the InvoiceCustId field.
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
     * Sets the InvoiceCustDetailId field.
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
     * Sets the ErpPoLineNum field.
     *
     * @param pErpPoLineNum
     *  int to use to update the field.
     */
    public void setErpPoLineNum(int pErpPoLineNum){
        this.mErpPoLineNum = pErpPoLineNum;
        setDirty(true);
    }
    /**
     * Retrieves the ErpPoLineNum field.
     *
     * @return
     *  int containing the ErpPoLineNum field.
     */
    public int getErpPoLineNum(){
        return mErpPoLineNum;
    }

    /**
     * Sets the DistLineNumber field.
     *
     * @param pDistLineNumber
     *  int to use to update the field.
     */
    public void setDistLineNumber(int pDistLineNumber){
        this.mDistLineNumber = pDistLineNumber;
        setDirty(true);
    }
    /**
     * Retrieves the DistLineNumber field.
     *
     * @return
     *  int containing the DistLineNumber field.
     */
    public int getDistLineNumber(){
        return mDistLineNumber;
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
     * Sets the DistItemShortDesc field.
     *
     * @param pDistItemShortDesc
     *  String to use to update the field.
     */
    public void setDistItemShortDesc(String pDistItemShortDesc){
        this.mDistItemShortDesc = pDistItemShortDesc;
        setDirty(true);
    }
    /**
     * Retrieves the DistItemShortDesc field.
     *
     * @return
     *  String containing the DistItemShortDesc field.
     */
    public String getDistItemShortDesc(){
        return mDistItemShortDesc;
    }

    /**
     * Sets the DistItemUom field.
     *
     * @param pDistItemUom
     *  String to use to update the field.
     */
    public void setDistItemUom(String pDistItemUom){
        this.mDistItemUom = pDistItemUom;
        setDirty(true);
    }
    /**
     * Retrieves the DistItemUom field.
     *
     * @return
     *  String containing the DistItemUom field.
     */
    public String getDistItemUom(){
        return mDistItemUom;
    }

    /**
     * Sets the DistItemPack field.
     *
     * @param pDistItemPack
     *  String to use to update the field.
     */
    public void setDistItemPack(String pDistItemPack){
        this.mDistItemPack = pDistItemPack;
        setDirty(true);
    }
    /**
     * Retrieves the DistItemPack field.
     *
     * @return
     *  String containing the DistItemPack field.
     */
    public String getDistItemPack(){
        return mDistItemPack;
    }

    /**
     * Sets the DistItemQuantity field.
     *
     * @param pDistItemQuantity
     *  int to use to update the field.
     */
    public void setDistItemQuantity(int pDistItemQuantity){
        this.mDistItemQuantity = pDistItemQuantity;
        setDirty(true);
    }
    /**
     * Retrieves the DistItemQuantity field.
     *
     * @return
     *  int containing the DistItemQuantity field.
     */
    public int getDistItemQuantity(){
        return mDistItemQuantity;
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
     * Sets the ItemCost field.
     *
     * @param pItemCost
     *  java.math.BigDecimal to use to update the field.
     */
    public void setItemCost(java.math.BigDecimal pItemCost){
        this.mItemCost = pItemCost;
        setDirty(true);
    }
    /**
     * Retrieves the ItemCost field.
     *
     * @return
     *  java.math.BigDecimal containing the ItemCost field.
     */
    public java.math.BigDecimal getItemCost(){
        return mItemCost;
    }

    /**
     * Sets the AdjustedCost field.
     *
     * @param pAdjustedCost
     *  java.math.BigDecimal to use to update the field.
     */
    public void setAdjustedCost(java.math.BigDecimal pAdjustedCost){
        this.mAdjustedCost = pAdjustedCost;
        setDirty(true);
    }
    /**
     * Retrieves the AdjustedCost field.
     *
     * @return
     *  java.math.BigDecimal containing the AdjustedCost field.
     */
    public java.math.BigDecimal getAdjustedCost(){
        return mAdjustedCost;
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
     * Sets the ItemReceivedCost field.
     *
     * @param pItemReceivedCost
     *  java.math.BigDecimal to use to update the field.
     */
    public void setItemReceivedCost(java.math.BigDecimal pItemReceivedCost){
        this.mItemReceivedCost = pItemReceivedCost;
        setDirty(true);
    }
    /**
     * Retrieves the ItemReceivedCost field.
     *
     * @return
     *  java.math.BigDecimal containing the ItemReceivedCost field.
     */
    public java.math.BigDecimal getItemReceivedCost(){
        return mItemReceivedCost;
    }

    /**
     * Sets the DistItemQtyReceived field.
     *
     * @param pDistItemQtyReceived
     *  int to use to update the field.
     */
    public void setDistItemQtyReceived(int pDistItemQtyReceived){
        this.mDistItemQtyReceived = pDistItemQtyReceived;
        setDirty(true);
    }
    /**
     * Retrieves the DistItemQtyReceived field.
     *
     * @return
     *  int containing the DistItemQtyReceived field.
     */
    public int getDistItemQtyReceived(){
        return mDistItemQtyReceived;
    }

    /**
     * Sets the ErpAccountCode field.
     *
     * @param pErpAccountCode
     *  String to use to update the field.
     */
    public void setErpAccountCode(String pErpAccountCode){
        this.mErpAccountCode = pErpAccountCode;
        setDirty(true);
    }
    /**
     * Retrieves the ErpAccountCode field.
     *
     * @return
     *  String containing the ErpAccountCode field.
     */
    public String getErpAccountCode(){
        return mErpAccountCode;
    }

    /**
     * Sets the ErpPoRefLineNum field.
     *
     * @param pErpPoRefLineNum
     *  int to use to update the field.
     */
    public void setErpPoRefLineNum(int pErpPoRefLineNum){
        this.mErpPoRefLineNum = pErpPoRefLineNum;
        setDirty(true);
    }
    /**
     * Retrieves the ErpPoRefLineNum field.
     *
     * @return
     *  int containing the ErpPoRefLineNum field.
     */
    public int getErpPoRefLineNum(){
        return mErpPoRefLineNum;
    }

    /**
     * Sets the InvoiceDistSkuNum field.
     *
     * @param pInvoiceDistSkuNum
     *  String to use to update the field.
     */
    public void setInvoiceDistSkuNum(String pInvoiceDistSkuNum){
        this.mInvoiceDistSkuNum = pInvoiceDistSkuNum;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceDistSkuNum field.
     *
     * @return
     *  String containing the InvoiceDistSkuNum field.
     */
    public String getInvoiceDistSkuNum(){
        return mInvoiceDistSkuNum;
    }

    /**
     * Sets the DistIntoStockCost field.
     *
     * @param pDistIntoStockCost
     *  java.math.BigDecimal to use to update the field.
     */
    public void setDistIntoStockCost(java.math.BigDecimal pDistIntoStockCost){
        this.mDistIntoStockCost = pDistIntoStockCost;
        setDirty(true);
    }
    /**
     * Retrieves the DistIntoStockCost field.
     *
     * @return
     *  java.math.BigDecimal containing the DistIntoStockCost field.
     */
    public java.math.BigDecimal getDistIntoStockCost(){
        return mDistIntoStockCost;
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


}
