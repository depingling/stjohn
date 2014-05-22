
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PreOrderItemData
 * Description:  This is a ValueObject class wrapping the database table CLW_PRE_ORDER_ITEM.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.PreOrderItemDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>PreOrderItemData</code> is a ValueObject class wrapping of the database table CLW_PRE_ORDER_ITEM.
 */
public class PreOrderItemData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 1979887002669661525L;
    private int mPreOrderItemId;// SQL type:NUMBER, not null
    private int mPreOrderId;// SQL type:NUMBER, not null
    private int mItemId;// SQL type:NUMBER
    private int mLineNumber;// SQL type:NUMBER
    private int mQuantity;// SQL type:NUMBER
    private String mCustomerUom;// SQL type:VARCHAR2
    private String mCustomerSku;// SQL type:VARCHAR2
    private String mCustomerProductDesc;// SQL type:VARCHAR2
    private String mCustomerPack;// SQL type:VARCHAR2
    private String mAsInventoryItem;// SQL type:VARCHAR2
    private String mSaleTypeCd;// SQL type:VARCHAR2
    private java.math.BigDecimal mPrice;// SQL type:NUMBER
    private String mInventoryQtyOnHand;// SQL type:VARCHAR2
    private int mInventoryParValue;// SQL type:NUMBER
    private int mAssetId;// SQL type:NUMBER
    private int mDistributorId;// SQL type:NUMBER
    private String mOrderItemActionCd;// SQL type:VARCHAR2
    private String mTaxExempt;// SQL type:VARCHAR2
    private java.math.BigDecimal mTaxAmount;// SQL type:NUMBER

    /**
     * Constructor.
     */
    public PreOrderItemData ()
    {
        mCustomerUom = "";
        mCustomerSku = "";
        mCustomerProductDesc = "";
        mCustomerPack = "";
        mAsInventoryItem = "";
        mSaleTypeCd = "";
        mInventoryQtyOnHand = "";
        mOrderItemActionCd = "";
        mTaxExempt = "";
    }

    /**
     * Constructor.
     */
    public PreOrderItemData(int parm1, int parm2, int parm3, int parm4, int parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, java.math.BigDecimal parm12, String parm13, int parm14, int parm15, int parm16, String parm17, String parm18, java.math.BigDecimal parm19)
    {
        mPreOrderItemId = parm1;
        mPreOrderId = parm2;
        mItemId = parm3;
        mLineNumber = parm4;
        mQuantity = parm5;
        mCustomerUom = parm6;
        mCustomerSku = parm7;
        mCustomerProductDesc = parm8;
        mCustomerPack = parm9;
        mAsInventoryItem = parm10;
        mSaleTypeCd = parm11;
        mPrice = parm12;
        mInventoryQtyOnHand = parm13;
        mInventoryParValue = parm14;
        mAssetId = parm15;
        mDistributorId = parm16;
        mOrderItemActionCd = parm17;
        mTaxExempt = parm18;
        mTaxAmount = parm19;
        
    }

    /**
     * Creates a new PreOrderItemData
     *
     * @return
     *  Newly initialized PreOrderItemData object.
     */
    public static PreOrderItemData createValue ()
    {
        PreOrderItemData valueData = new PreOrderItemData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PreOrderItemData object
     */
    public String toString()
    {
        return "[" + "PreOrderItemId=" + mPreOrderItemId + ", PreOrderId=" + mPreOrderId + ", ItemId=" + mItemId + ", LineNumber=" + mLineNumber + ", Quantity=" + mQuantity + ", CustomerUom=" + mCustomerUom + ", CustomerSku=" + mCustomerSku + ", CustomerProductDesc=" + mCustomerProductDesc + ", CustomerPack=" + mCustomerPack + ", AsInventoryItem=" + mAsInventoryItem + ", SaleTypeCd=" + mSaleTypeCd + ", Price=" + mPrice + ", InventoryQtyOnHand=" + mInventoryQtyOnHand + ", InventoryParValue=" + mInventoryParValue + ", AssetId=" + mAssetId + ", DistributorId=" + mDistributorId + ", OrderItemActionCd=" + mOrderItemActionCd + ", TaxExempt=" + mTaxExempt + ", TaxAmount=" + mTaxAmount + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("PreOrderItem");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mPreOrderItemId));

        node =  doc.createElement("PreOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mPreOrderId)));
        root.appendChild(node);

        node =  doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node =  doc.createElement("LineNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mLineNumber)));
        root.appendChild(node);

        node =  doc.createElement("Quantity");
        node.appendChild(doc.createTextNode(String.valueOf(mQuantity)));
        root.appendChild(node);

        node =  doc.createElement("CustomerUom");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerUom)));
        root.appendChild(node);

        node =  doc.createElement("CustomerSku");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerSku)));
        root.appendChild(node);

        node =  doc.createElement("CustomerProductDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerProductDesc)));
        root.appendChild(node);

        node =  doc.createElement("CustomerPack");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerPack)));
        root.appendChild(node);

        node =  doc.createElement("AsInventoryItem");
        node.appendChild(doc.createTextNode(String.valueOf(mAsInventoryItem)));
        root.appendChild(node);

        node =  doc.createElement("SaleTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mSaleTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("Price");
        node.appendChild(doc.createTextNode(String.valueOf(mPrice)));
        root.appendChild(node);

        node =  doc.createElement("InventoryQtyOnHand");
        node.appendChild(doc.createTextNode(String.valueOf(mInventoryQtyOnHand)));
        root.appendChild(node);

        node =  doc.createElement("InventoryParValue");
        node.appendChild(doc.createTextNode(String.valueOf(mInventoryParValue)));
        root.appendChild(node);

        node =  doc.createElement("AssetId");
        node.appendChild(doc.createTextNode(String.valueOf(mAssetId)));
        root.appendChild(node);

        node =  doc.createElement("DistributorId");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorId)));
        root.appendChild(node);

        node =  doc.createElement("OrderItemActionCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderItemActionCd)));
        root.appendChild(node);

        node =  doc.createElement("TaxExempt");
        node.appendChild(doc.createTextNode(String.valueOf(mTaxExempt)));
        root.appendChild(node);

        node =  doc.createElement("TaxAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mTaxAmount)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the PreOrderItemId field is not cloned.
    *
    * @return PreOrderItemData object
    */
    public Object clone(){
        PreOrderItemData myClone = new PreOrderItemData();
        
        myClone.mPreOrderId = mPreOrderId;
        
        myClone.mItemId = mItemId;
        
        myClone.mLineNumber = mLineNumber;
        
        myClone.mQuantity = mQuantity;
        
        myClone.mCustomerUom = mCustomerUom;
        
        myClone.mCustomerSku = mCustomerSku;
        
        myClone.mCustomerProductDesc = mCustomerProductDesc;
        
        myClone.mCustomerPack = mCustomerPack;
        
        myClone.mAsInventoryItem = mAsInventoryItem;
        
        myClone.mSaleTypeCd = mSaleTypeCd;
        
        myClone.mPrice = mPrice;
        
        myClone.mInventoryQtyOnHand = mInventoryQtyOnHand;
        
        myClone.mInventoryParValue = mInventoryParValue;
        
        myClone.mAssetId = mAssetId;
        
        myClone.mDistributorId = mDistributorId;
        
        myClone.mOrderItemActionCd = mOrderItemActionCd;
        
        myClone.mTaxExempt = mTaxExempt;
        
        myClone.mTaxAmount = mTaxAmount;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (PreOrderItemDataAccess.PRE_ORDER_ITEM_ID.equals(pFieldName)) {
            return getPreOrderItemId();
        } else if (PreOrderItemDataAccess.PRE_ORDER_ID.equals(pFieldName)) {
            return getPreOrderId();
        } else if (PreOrderItemDataAccess.ITEM_ID.equals(pFieldName)) {
            return getItemId();
        } else if (PreOrderItemDataAccess.LINE_NUMBER.equals(pFieldName)) {
            return getLineNumber();
        } else if (PreOrderItemDataAccess.QUANTITY.equals(pFieldName)) {
            return getQuantity();
        } else if (PreOrderItemDataAccess.CUSTOMER_UOM.equals(pFieldName)) {
            return getCustomerUom();
        } else if (PreOrderItemDataAccess.CUSTOMER_SKU.equals(pFieldName)) {
            return getCustomerSku();
        } else if (PreOrderItemDataAccess.CUSTOMER_PRODUCT_DESC.equals(pFieldName)) {
            return getCustomerProductDesc();
        } else if (PreOrderItemDataAccess.CUSTOMER_PACK.equals(pFieldName)) {
            return getCustomerPack();
        } else if (PreOrderItemDataAccess.AS_INVENTORY_ITEM.equals(pFieldName)) {
            return getAsInventoryItem();
        } else if (PreOrderItemDataAccess.SALE_TYPE_CD.equals(pFieldName)) {
            return getSaleTypeCd();
        } else if (PreOrderItemDataAccess.PRICE.equals(pFieldName)) {
            return getPrice();
        } else if (PreOrderItemDataAccess.INVENTORY_QTY_ON_HAND.equals(pFieldName)) {
            return getInventoryQtyOnHand();
        } else if (PreOrderItemDataAccess.INVENTORY_PAR_VALUE.equals(pFieldName)) {
            return getInventoryParValue();
        } else if (PreOrderItemDataAccess.ASSET_ID.equals(pFieldName)) {
            return getAssetId();
        } else if (PreOrderItemDataAccess.DISTRIBUTOR_ID.equals(pFieldName)) {
            return getDistributorId();
        } else if (PreOrderItemDataAccess.ORDER_ITEM_ACTION_CD.equals(pFieldName)) {
            return getOrderItemActionCd();
        } else if (PreOrderItemDataAccess.TAX_EXEMPT.equals(pFieldName)) {
            return getTaxExempt();
        } else if (PreOrderItemDataAccess.TAX_AMOUNT.equals(pFieldName)) {
            return getTaxAmount();
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
        return PreOrderItemDataAccess.CLW_PRE_ORDER_ITEM;
    }

    
    /**
     * Sets the PreOrderItemId field. This field is required to be set in the database.
     *
     * @param pPreOrderItemId
     *  int to use to update the field.
     */
    public void setPreOrderItemId(int pPreOrderItemId){
        this.mPreOrderItemId = pPreOrderItemId;
        setDirty(true);
    }
    /**
     * Retrieves the PreOrderItemId field.
     *
     * @return
     *  int containing the PreOrderItemId field.
     */
    public int getPreOrderItemId(){
        return mPreOrderItemId;
    }

    /**
     * Sets the PreOrderId field. This field is required to be set in the database.
     *
     * @param pPreOrderId
     *  int to use to update the field.
     */
    public void setPreOrderId(int pPreOrderId){
        this.mPreOrderId = pPreOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the PreOrderId field.
     *
     * @return
     *  int containing the PreOrderId field.
     */
    public int getPreOrderId(){
        return mPreOrderId;
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
     * Sets the Quantity field.
     *
     * @param pQuantity
     *  int to use to update the field.
     */
    public void setQuantity(int pQuantity){
        this.mQuantity = pQuantity;
        setDirty(true);
    }
    /**
     * Retrieves the Quantity field.
     *
     * @return
     *  int containing the Quantity field.
     */
    public int getQuantity(){
        return mQuantity;
    }

    /**
     * Sets the CustomerUom field.
     *
     * @param pCustomerUom
     *  String to use to update the field.
     */
    public void setCustomerUom(String pCustomerUom){
        this.mCustomerUom = pCustomerUom;
        setDirty(true);
    }
    /**
     * Retrieves the CustomerUom field.
     *
     * @return
     *  String containing the CustomerUom field.
     */
    public String getCustomerUom(){
        return mCustomerUom;
    }

    /**
     * Sets the CustomerSku field.
     *
     * @param pCustomerSku
     *  String to use to update the field.
     */
    public void setCustomerSku(String pCustomerSku){
        this.mCustomerSku = pCustomerSku;
        setDirty(true);
    }
    /**
     * Retrieves the CustomerSku field.
     *
     * @return
     *  String containing the CustomerSku field.
     */
    public String getCustomerSku(){
        return mCustomerSku;
    }

    /**
     * Sets the CustomerProductDesc field.
     *
     * @param pCustomerProductDesc
     *  String to use to update the field.
     */
    public void setCustomerProductDesc(String pCustomerProductDesc){
        this.mCustomerProductDesc = pCustomerProductDesc;
        setDirty(true);
    }
    /**
     * Retrieves the CustomerProductDesc field.
     *
     * @return
     *  String containing the CustomerProductDesc field.
     */
    public String getCustomerProductDesc(){
        return mCustomerProductDesc;
    }

    /**
     * Sets the CustomerPack field.
     *
     * @param pCustomerPack
     *  String to use to update the field.
     */
    public void setCustomerPack(String pCustomerPack){
        this.mCustomerPack = pCustomerPack;
        setDirty(true);
    }
    /**
     * Retrieves the CustomerPack field.
     *
     * @return
     *  String containing the CustomerPack field.
     */
    public String getCustomerPack(){
        return mCustomerPack;
    }

    /**
     * Sets the AsInventoryItem field.
     *
     * @param pAsInventoryItem
     *  String to use to update the field.
     */
    public void setAsInventoryItem(String pAsInventoryItem){
        this.mAsInventoryItem = pAsInventoryItem;
        setDirty(true);
    }
    /**
     * Retrieves the AsInventoryItem field.
     *
     * @return
     *  String containing the AsInventoryItem field.
     */
    public String getAsInventoryItem(){
        return mAsInventoryItem;
    }

    /**
     * Sets the SaleTypeCd field.
     *
     * @param pSaleTypeCd
     *  String to use to update the field.
     */
    public void setSaleTypeCd(String pSaleTypeCd){
        this.mSaleTypeCd = pSaleTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the SaleTypeCd field.
     *
     * @return
     *  String containing the SaleTypeCd field.
     */
    public String getSaleTypeCd(){
        return mSaleTypeCd;
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
     * Sets the InventoryQtyOnHand field.
     *
     * @param pInventoryQtyOnHand
     *  String to use to update the field.
     */
    public void setInventoryQtyOnHand(String pInventoryQtyOnHand){
        this.mInventoryQtyOnHand = pInventoryQtyOnHand;
        setDirty(true);
    }
    /**
     * Retrieves the InventoryQtyOnHand field.
     *
     * @return
     *  String containing the InventoryQtyOnHand field.
     */
    public String getInventoryQtyOnHand(){
        return mInventoryQtyOnHand;
    }

    /**
     * Sets the InventoryParValue field.
     *
     * @param pInventoryParValue
     *  int to use to update the field.
     */
    public void setInventoryParValue(int pInventoryParValue){
        this.mInventoryParValue = pInventoryParValue;
        setDirty(true);
    }
    /**
     * Retrieves the InventoryParValue field.
     *
     * @return
     *  int containing the InventoryParValue field.
     */
    public int getInventoryParValue(){
        return mInventoryParValue;
    }

    /**
     * Sets the AssetId field.
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
     * Sets the DistributorId field.
     *
     * @param pDistributorId
     *  int to use to update the field.
     */
    public void setDistributorId(int pDistributorId){
        this.mDistributorId = pDistributorId;
        setDirty(true);
    }
    /**
     * Retrieves the DistributorId field.
     *
     * @return
     *  int containing the DistributorId field.
     */
    public int getDistributorId(){
        return mDistributorId;
    }

    /**
     * Sets the OrderItemActionCd field.
     *
     * @param pOrderItemActionCd
     *  String to use to update the field.
     */
    public void setOrderItemActionCd(String pOrderItemActionCd){
        this.mOrderItemActionCd = pOrderItemActionCd;
        setDirty(true);
    }
    /**
     * Retrieves the OrderItemActionCd field.
     *
     * @return
     *  String containing the OrderItemActionCd field.
     */
    public String getOrderItemActionCd(){
        return mOrderItemActionCd;
    }

    /**
     * Sets the TaxExempt field.
     *
     * @param pTaxExempt
     *  String to use to update the field.
     */
    public void setTaxExempt(String pTaxExempt){
        this.mTaxExempt = pTaxExempt;
        setDirty(true);
    }
    /**
     * Retrieves the TaxExempt field.
     *
     * @return
     *  String containing the TaxExempt field.
     */
    public String getTaxExempt(){
        return mTaxExempt;
    }

    /**
     * Sets the TaxAmount field.
     *
     * @param pTaxAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setTaxAmount(java.math.BigDecimal pTaxAmount){
        this.mTaxAmount = pTaxAmount;
        setDirty(true);
    }
    /**
     * Retrieves the TaxAmount field.
     *
     * @return
     *  java.math.BigDecimal containing the TaxAmount field.
     */
    public java.math.BigDecimal getTaxAmount(){
        return mTaxAmount;
    }


}
