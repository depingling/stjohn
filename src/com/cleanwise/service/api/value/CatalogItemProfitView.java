
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CatalogItemProfitView
 * Description:  This is a ViewObject class for UI.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ViewObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import org.w3c.dom.*;

import java.math.BigDecimal;


/**
 * <code>CatalogItemProfitView</code> is a ViewObject class for UI.
 */
public class CatalogItemProfitView
extends ValueObject
{
   
    private static final long serialVersionUID = -1446560625158759768L;
    private int mItemId;
    private int mItemSku;
    private String mItemDesc;
    private String mItemUom;
    private String mItemPack;
    private String mItemSize;
    private String mItemColor;
    private String mOtherDesc;
    private BigDecimal mListPrice;
    private int mItemMfgId;
    private String mItemMfgName;
    private String mItemMfgSku;
    private int mCatalogId;
    private String mCatalogName;
    private int mContractId;
    private String mContractName;
    private int mDistId;
    private String mDistName;
    private String mContrDistFl;
    private String mDistErp;
    private String mDistSku;
    private String mDistUom;
    private String mDistPack;
    private BigDecimal mDistCost;
    private BigDecimal mContractPrice;
    private String mItemCustDesc;
    private String mItemCustSku;
    private BigDecimal mPriceDiff;
    private BigDecimal mPriceDiffPr;
    private Date mLastOrderDate;
    private int mYtdQty;
    private int mYtdQtyMainDist;
    private BigDecimal mYtdCostMainDist;
    private BigDecimal mAvgCost;
    private String mCategoryName;
    private String mStandardProductList;

    /**
     * Constructor.
     */
    public CatalogItemProfitView ()
    {
        mItemDesc = "";
        mItemUom = "";
        mItemPack = "";
        mItemSize = "";
        mItemColor = "";
        mOtherDesc = "";
        mItemMfgName = "";
        mItemMfgSku = "";
        mCatalogName = "";
        mContractName = "";
        mDistName = "";
        mContrDistFl = "";
        mDistErp = "";
        mDistSku = "";
        mDistUom = "";
        mDistPack = "";
        mItemCustDesc = "";
        mItemCustSku = "";
        mCategoryName = "";
        mStandardProductList = "";
    }

    /**
     * Constructor. 
     */
    public CatalogItemProfitView(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, BigDecimal parm9, int parm10, String parm11, String parm12, int parm13, String parm14, int parm15, String parm16, int parm17, String parm18, String parm19, String parm20, String parm21, String parm22, String parm23, BigDecimal parm24, BigDecimal parm25, String parm26, String parm27, BigDecimal parm28, BigDecimal parm29, Date parm30, int parm31, int parm32, BigDecimal parm33, BigDecimal parm34, String parm35, String parm36)
    {
        mItemId = parm1;
        mItemSku = parm2;
        mItemDesc = parm3;
        mItemUom = parm4;
        mItemPack = parm5;
        mItemSize = parm6;
        mItemColor = parm7;
        mOtherDesc = parm8;
        mListPrice = parm9;
        mItemMfgId = parm10;
        mItemMfgName = parm11;
        mItemMfgSku = parm12;
        mCatalogId = parm13;
        mCatalogName = parm14;
        mContractId = parm15;
        mContractName = parm16;
        mDistId = parm17;
        mDistName = parm18;
        mContrDistFl = parm19;
        mDistErp = parm20;
        mDistSku = parm21;
        mDistUom = parm22;
        mDistPack = parm23;
        mDistCost = parm24;
        mContractPrice = parm25;
        mItemCustDesc = parm26;
        mItemCustSku = parm27;
        mPriceDiff = parm28;
        mPriceDiffPr = parm29;
        mLastOrderDate = parm30;
        mYtdQty = parm31;
        mYtdQtyMainDist = parm32;
        mYtdCostMainDist = parm33;
        mAvgCost = parm34;
        mCategoryName = parm35;
        mStandardProductList = parm36;
        
    }

    /**
     * Creates a new CatalogItemProfitView
     *
     * @return
     *  Newly initialized CatalogItemProfitView object.
     */
    public static CatalogItemProfitView createValue () 
    {
        CatalogItemProfitView valueView = new CatalogItemProfitView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CatalogItemProfitView object
     */
    public String toString()
    {
        return "[" + "ItemId=" + mItemId + ", ItemSku=" + mItemSku + ", ItemDesc=" + mItemDesc + ", ItemUom=" + mItemUom + ", ItemPack=" + mItemPack + ", ItemSize=" + mItemSize + ", ItemColor=" + mItemColor + ", OtherDesc=" + mOtherDesc + ", ListPrice=" + mListPrice + ", ItemMfgId=" + mItemMfgId + ", ItemMfgName=" + mItemMfgName + ", ItemMfgSku=" + mItemMfgSku + ", CatalogId=" + mCatalogId + ", CatalogName=" + mCatalogName + ", ContractId=" + mContractId + ", ContractName=" + mContractName + ", DistId=" + mDistId + ", DistName=" + mDistName + ", ContrDistFl=" + mContrDistFl + ", DistErp=" + mDistErp + ", DistSku=" + mDistSku + ", DistUom=" + mDistUom + ", DistPack=" + mDistPack + ", DistCost=" + mDistCost + ", ContractPrice=" + mContractPrice + ", ItemCustDesc=" + mItemCustDesc + ", ItemCustSku=" + mItemCustSku + ", PriceDiff=" + mPriceDiff + ", PriceDiffPr=" + mPriceDiffPr + ", LastOrderDate=" + mLastOrderDate + ", YtdQty=" + mYtdQty + ", YtdQtyMainDist=" + mYtdQtyMainDist + ", YtdCostMainDist=" + mYtdCostMainDist + ", AvgCost=" + mAvgCost + ", CategoryName=" + mCategoryName + ", StandardProductList=" + mStandardProductList + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("CatalogItemProfit");
	root.setAttribute("Id", String.valueOf(mItemId));

	Element node;

        node = doc.createElement("ItemSku");
        node.appendChild(doc.createTextNode(String.valueOf(mItemSku)));
        root.appendChild(node);

        node = doc.createElement("ItemDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mItemDesc)));
        root.appendChild(node);

        node = doc.createElement("ItemUom");
        node.appendChild(doc.createTextNode(String.valueOf(mItemUom)));
        root.appendChild(node);

        node = doc.createElement("ItemPack");
        node.appendChild(doc.createTextNode(String.valueOf(mItemPack)));
        root.appendChild(node);

        node = doc.createElement("ItemSize");
        node.appendChild(doc.createTextNode(String.valueOf(mItemSize)));
        root.appendChild(node);

        node = doc.createElement("ItemColor");
        node.appendChild(doc.createTextNode(String.valueOf(mItemColor)));
        root.appendChild(node);

        node = doc.createElement("OtherDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mOtherDesc)));
        root.appendChild(node);

        node = doc.createElement("ListPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mListPrice)));
        root.appendChild(node);

        node = doc.createElement("ItemMfgId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMfgId)));
        root.appendChild(node);

        node = doc.createElement("ItemMfgName");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMfgName)));
        root.appendChild(node);

        node = doc.createElement("ItemMfgSku");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMfgSku)));
        root.appendChild(node);

        node = doc.createElement("CatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogId)));
        root.appendChild(node);

        node = doc.createElement("CatalogName");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogName)));
        root.appendChild(node);

        node = doc.createElement("ContractId");
        node.appendChild(doc.createTextNode(String.valueOf(mContractId)));
        root.appendChild(node);

        node = doc.createElement("ContractName");
        node.appendChild(doc.createTextNode(String.valueOf(mContractName)));
        root.appendChild(node);

        node = doc.createElement("DistId");
        node.appendChild(doc.createTextNode(String.valueOf(mDistId)));
        root.appendChild(node);

        node = doc.createElement("DistName");
        node.appendChild(doc.createTextNode(String.valueOf(mDistName)));
        root.appendChild(node);

        node = doc.createElement("ContrDistFl");
        node.appendChild(doc.createTextNode(String.valueOf(mContrDistFl)));
        root.appendChild(node);

        node = doc.createElement("DistErp");
        node.appendChild(doc.createTextNode(String.valueOf(mDistErp)));
        root.appendChild(node);

        node = doc.createElement("DistSku");
        node.appendChild(doc.createTextNode(String.valueOf(mDistSku)));
        root.appendChild(node);

        node = doc.createElement("DistUom");
        node.appendChild(doc.createTextNode(String.valueOf(mDistUom)));
        root.appendChild(node);

        node = doc.createElement("DistPack");
        node.appendChild(doc.createTextNode(String.valueOf(mDistPack)));
        root.appendChild(node);

        node = doc.createElement("DistCost");
        node.appendChild(doc.createTextNode(String.valueOf(mDistCost)));
        root.appendChild(node);

        node = doc.createElement("ContractPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mContractPrice)));
        root.appendChild(node);

        node = doc.createElement("ItemCustDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mItemCustDesc)));
        root.appendChild(node);

        node = doc.createElement("ItemCustSku");
        node.appendChild(doc.createTextNode(String.valueOf(mItemCustSku)));
        root.appendChild(node);

        node = doc.createElement("PriceDiff");
        node.appendChild(doc.createTextNode(String.valueOf(mPriceDiff)));
        root.appendChild(node);

        node = doc.createElement("PriceDiffPr");
        node.appendChild(doc.createTextNode(String.valueOf(mPriceDiffPr)));
        root.appendChild(node);

        node = doc.createElement("LastOrderDate");
        node.appendChild(doc.createTextNode(String.valueOf(mLastOrderDate)));
        root.appendChild(node);

        node = doc.createElement("YtdQty");
        node.appendChild(doc.createTextNode(String.valueOf(mYtdQty)));
        root.appendChild(node);

        node = doc.createElement("YtdQtyMainDist");
        node.appendChild(doc.createTextNode(String.valueOf(mYtdQtyMainDist)));
        root.appendChild(node);

        node = doc.createElement("YtdCostMainDist");
        node.appendChild(doc.createTextNode(String.valueOf(mYtdCostMainDist)));
        root.appendChild(node);

        node = doc.createElement("AvgCost");
        node.appendChild(doc.createTextNode(String.valueOf(mAvgCost)));
        root.appendChild(node);

        node = doc.createElement("CategoryName");
        node.appendChild(doc.createTextNode(String.valueOf(mCategoryName)));
        root.appendChild(node);

        node = doc.createElement("StandardProductList");
        node.appendChild(doc.createTextNode(String.valueOf(mStandardProductList)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public CatalogItemProfitView copy()  {
      CatalogItemProfitView obj = new CatalogItemProfitView();
      obj.setItemId(mItemId);
      obj.setItemSku(mItemSku);
      obj.setItemDesc(mItemDesc);
      obj.setItemUom(mItemUom);
      obj.setItemPack(mItemPack);
      obj.setItemSize(mItemSize);
      obj.setItemColor(mItemColor);
      obj.setOtherDesc(mOtherDesc);
      obj.setListPrice(mListPrice);
      obj.setItemMfgId(mItemMfgId);
      obj.setItemMfgName(mItemMfgName);
      obj.setItemMfgSku(mItemMfgSku);
      obj.setCatalogId(mCatalogId);
      obj.setCatalogName(mCatalogName);
      obj.setContractId(mContractId);
      obj.setContractName(mContractName);
      obj.setDistId(mDistId);
      obj.setDistName(mDistName);
      obj.setContrDistFl(mContrDistFl);
      obj.setDistErp(mDistErp);
      obj.setDistSku(mDistSku);
      obj.setDistUom(mDistUom);
      obj.setDistPack(mDistPack);
      obj.setDistCost(mDistCost);
      obj.setContractPrice(mContractPrice);
      obj.setItemCustDesc(mItemCustDesc);
      obj.setItemCustSku(mItemCustSku);
      obj.setPriceDiff(mPriceDiff);
      obj.setPriceDiffPr(mPriceDiffPr);
      obj.setLastOrderDate(mLastOrderDate);
      obj.setYtdQty(mYtdQty);
      obj.setYtdQtyMainDist(mYtdQtyMainDist);
      obj.setYtdCostMainDist(mYtdCostMainDist);
      obj.setAvgCost(mAvgCost);
      obj.setCategoryName(mCategoryName);
      obj.setStandardProductList(mStandardProductList);
      
      return obj;
    }

    
    /**
     * Sets the ItemId property.
     *
     * @param pItemId
     *  int to use to update the property.
     */
    public void setItemId(int pItemId){
        this.mItemId = pItemId;
    }
    /**
     * Retrieves the ItemId property.
     *
     * @return
     *  int containing the ItemId property.
     */
    public int getItemId(){
        return mItemId;
    }


    /**
     * Sets the ItemSku property.
     *
     * @param pItemSku
     *  int to use to update the property.
     */
    public void setItemSku(int pItemSku){
        this.mItemSku = pItemSku;
    }
    /**
     * Retrieves the ItemSku property.
     *
     * @return
     *  int containing the ItemSku property.
     */
    public int getItemSku(){
        return mItemSku;
    }


    /**
     * Sets the ItemDesc property.
     *
     * @param pItemDesc
     *  String to use to update the property.
     */
    public void setItemDesc(String pItemDesc){
        this.mItemDesc = pItemDesc;
    }
    /**
     * Retrieves the ItemDesc property.
     *
     * @return
     *  String containing the ItemDesc property.
     */
    public String getItemDesc(){
        return mItemDesc;
    }


    /**
     * Sets the ItemUom property.
     *
     * @param pItemUom
     *  String to use to update the property.
     */
    public void setItemUom(String pItemUom){
        this.mItemUom = pItemUom;
    }
    /**
     * Retrieves the ItemUom property.
     *
     * @return
     *  String containing the ItemUom property.
     */
    public String getItemUom(){
        return mItemUom;
    }


    /**
     * Sets the ItemPack property.
     *
     * @param pItemPack
     *  String to use to update the property.
     */
    public void setItemPack(String pItemPack){
        this.mItemPack = pItemPack;
    }
    /**
     * Retrieves the ItemPack property.
     *
     * @return
     *  String containing the ItemPack property.
     */
    public String getItemPack(){
        return mItemPack;
    }


    /**
     * Sets the ItemSize property.
     *
     * @param pItemSize
     *  String to use to update the property.
     */
    public void setItemSize(String pItemSize){
        this.mItemSize = pItemSize;
    }
    /**
     * Retrieves the ItemSize property.
     *
     * @return
     *  String containing the ItemSize property.
     */
    public String getItemSize(){
        return mItemSize;
    }


    /**
     * Sets the ItemColor property.
     *
     * @param pItemColor
     *  String to use to update the property.
     */
    public void setItemColor(String pItemColor){
        this.mItemColor = pItemColor;
    }
    /**
     * Retrieves the ItemColor property.
     *
     * @return
     *  String containing the ItemColor property.
     */
    public String getItemColor(){
        return mItemColor;
    }


    /**
     * Sets the OtherDesc property.
     *
     * @param pOtherDesc
     *  String to use to update the property.
     */
    public void setOtherDesc(String pOtherDesc){
        this.mOtherDesc = pOtherDesc;
    }
    /**
     * Retrieves the OtherDesc property.
     *
     * @return
     *  String containing the OtherDesc property.
     */
    public String getOtherDesc(){
        return mOtherDesc;
    }


    /**
     * Sets the ListPrice property.
     *
     * @param pListPrice
     *  BigDecimal to use to update the property.
     */
    public void setListPrice(BigDecimal pListPrice){
        this.mListPrice = pListPrice;
    }
    /**
     * Retrieves the ListPrice property.
     *
     * @return
     *  BigDecimal containing the ListPrice property.
     */
    public BigDecimal getListPrice(){
        return mListPrice;
    }


    /**
     * Sets the ItemMfgId property.
     *
     * @param pItemMfgId
     *  int to use to update the property.
     */
    public void setItemMfgId(int pItemMfgId){
        this.mItemMfgId = pItemMfgId;
    }
    /**
     * Retrieves the ItemMfgId property.
     *
     * @return
     *  int containing the ItemMfgId property.
     */
    public int getItemMfgId(){
        return mItemMfgId;
    }


    /**
     * Sets the ItemMfgName property.
     *
     * @param pItemMfgName
     *  String to use to update the property.
     */
    public void setItemMfgName(String pItemMfgName){
        this.mItemMfgName = pItemMfgName;
    }
    /**
     * Retrieves the ItemMfgName property.
     *
     * @return
     *  String containing the ItemMfgName property.
     */
    public String getItemMfgName(){
        return mItemMfgName;
    }


    /**
     * Sets the ItemMfgSku property.
     *
     * @param pItemMfgSku
     *  String to use to update the property.
     */
    public void setItemMfgSku(String pItemMfgSku){
        this.mItemMfgSku = pItemMfgSku;
    }
    /**
     * Retrieves the ItemMfgSku property.
     *
     * @return
     *  String containing the ItemMfgSku property.
     */
    public String getItemMfgSku(){
        return mItemMfgSku;
    }


    /**
     * Sets the CatalogId property.
     *
     * @param pCatalogId
     *  int to use to update the property.
     */
    public void setCatalogId(int pCatalogId){
        this.mCatalogId = pCatalogId;
    }
    /**
     * Retrieves the CatalogId property.
     *
     * @return
     *  int containing the CatalogId property.
     */
    public int getCatalogId(){
        return mCatalogId;
    }


    /**
     * Sets the CatalogName property.
     *
     * @param pCatalogName
     *  String to use to update the property.
     */
    public void setCatalogName(String pCatalogName){
        this.mCatalogName = pCatalogName;
    }
    /**
     * Retrieves the CatalogName property.
     *
     * @return
     *  String containing the CatalogName property.
     */
    public String getCatalogName(){
        return mCatalogName;
    }


    /**
     * Sets the ContractId property.
     *
     * @param pContractId
     *  int to use to update the property.
     */
    public void setContractId(int pContractId){
        this.mContractId = pContractId;
    }
    /**
     * Retrieves the ContractId property.
     *
     * @return
     *  int containing the ContractId property.
     */
    public int getContractId(){
        return mContractId;
    }


    /**
     * Sets the ContractName property.
     *
     * @param pContractName
     *  String to use to update the property.
     */
    public void setContractName(String pContractName){
        this.mContractName = pContractName;
    }
    /**
     * Retrieves the ContractName property.
     *
     * @return
     *  String containing the ContractName property.
     */
    public String getContractName(){
        return mContractName;
    }


    /**
     * Sets the DistId property.
     *
     * @param pDistId
     *  int to use to update the property.
     */
    public void setDistId(int pDistId){
        this.mDistId = pDistId;
    }
    /**
     * Retrieves the DistId property.
     *
     * @return
     *  int containing the DistId property.
     */
    public int getDistId(){
        return mDistId;
    }


    /**
     * Sets the DistName property.
     *
     * @param pDistName
     *  String to use to update the property.
     */
    public void setDistName(String pDistName){
        this.mDistName = pDistName;
    }
    /**
     * Retrieves the DistName property.
     *
     * @return
     *  String containing the DistName property.
     */
    public String getDistName(){
        return mDistName;
    }


    /**
     * Sets the ContrDistFl property.
     *
     * @param pContrDistFl
     *  String to use to update the property.
     */
    public void setContrDistFl(String pContrDistFl){
        this.mContrDistFl = pContrDistFl;
    }
    /**
     * Retrieves the ContrDistFl property.
     *
     * @return
     *  String containing the ContrDistFl property.
     */
    public String getContrDistFl(){
        return mContrDistFl;
    }


    /**
     * Sets the DistErp property.
     *
     * @param pDistErp
     *  String to use to update the property.
     */
    public void setDistErp(String pDistErp){
        this.mDistErp = pDistErp;
    }
    /**
     * Retrieves the DistErp property.
     *
     * @return
     *  String containing the DistErp property.
     */
    public String getDistErp(){
        return mDistErp;
    }


    /**
     * Sets the DistSku property.
     *
     * @param pDistSku
     *  String to use to update the property.
     */
    public void setDistSku(String pDistSku){
        this.mDistSku = pDistSku;
    }
    /**
     * Retrieves the DistSku property.
     *
     * @return
     *  String containing the DistSku property.
     */
    public String getDistSku(){
        return mDistSku;
    }


    /**
     * Sets the DistUom property.
     *
     * @param pDistUom
     *  String to use to update the property.
     */
    public void setDistUom(String pDistUom){
        this.mDistUom = pDistUom;
    }
    /**
     * Retrieves the DistUom property.
     *
     * @return
     *  String containing the DistUom property.
     */
    public String getDistUom(){
        return mDistUom;
    }


    /**
     * Sets the DistPack property.
     *
     * @param pDistPack
     *  String to use to update the property.
     */
    public void setDistPack(String pDistPack){
        this.mDistPack = pDistPack;
    }
    /**
     * Retrieves the DistPack property.
     *
     * @return
     *  String containing the DistPack property.
     */
    public String getDistPack(){
        return mDistPack;
    }


    /**
     * Sets the DistCost property.
     *
     * @param pDistCost
     *  BigDecimal to use to update the property.
     */
    public void setDistCost(BigDecimal pDistCost){
        this.mDistCost = pDistCost;
    }
    /**
     * Retrieves the DistCost property.
     *
     * @return
     *  BigDecimal containing the DistCost property.
     */
    public BigDecimal getDistCost(){
        return mDistCost;
    }


    /**
     * Sets the ContractPrice property.
     *
     * @param pContractPrice
     *  BigDecimal to use to update the property.
     */
    public void setContractPrice(BigDecimal pContractPrice){
        this.mContractPrice = pContractPrice;
    }
    /**
     * Retrieves the ContractPrice property.
     *
     * @return
     *  BigDecimal containing the ContractPrice property.
     */
    public BigDecimal getContractPrice(){
        return mContractPrice;
    }


    /**
     * Sets the ItemCustDesc property.
     *
     * @param pItemCustDesc
     *  String to use to update the property.
     */
    public void setItemCustDesc(String pItemCustDesc){
        this.mItemCustDesc = pItemCustDesc;
    }
    /**
     * Retrieves the ItemCustDesc property.
     *
     * @return
     *  String containing the ItemCustDesc property.
     */
    public String getItemCustDesc(){
        return mItemCustDesc;
    }


    /**
     * Sets the ItemCustSku property.
     *
     * @param pItemCustSku
     *  String to use to update the property.
     */
    public void setItemCustSku(String pItemCustSku){
        this.mItemCustSku = pItemCustSku;
    }
    /**
     * Retrieves the ItemCustSku property.
     *
     * @return
     *  String containing the ItemCustSku property.
     */
    public String getItemCustSku(){
        return mItemCustSku;
    }


    /**
     * Sets the PriceDiff property.
     *
     * @param pPriceDiff
     *  BigDecimal to use to update the property.
     */
    public void setPriceDiff(BigDecimal pPriceDiff){
        this.mPriceDiff = pPriceDiff;
    }
    /**
     * Retrieves the PriceDiff property.
     *
     * @return
     *  BigDecimal containing the PriceDiff property.
     */
    public BigDecimal getPriceDiff(){
        return mPriceDiff;
    }


    /**
     * Sets the PriceDiffPr property.
     *
     * @param pPriceDiffPr
     *  BigDecimal to use to update the property.
     */
    public void setPriceDiffPr(BigDecimal pPriceDiffPr){
        this.mPriceDiffPr = pPriceDiffPr;
    }
    /**
     * Retrieves the PriceDiffPr property.
     *
     * @return
     *  BigDecimal containing the PriceDiffPr property.
     */
    public BigDecimal getPriceDiffPr(){
        return mPriceDiffPr;
    }


    /**
     * Sets the LastOrderDate property.
     *
     * @param pLastOrderDate
     *  Date to use to update the property.
     */
    public void setLastOrderDate(Date pLastOrderDate){
        this.mLastOrderDate = pLastOrderDate;
    }
    /**
     * Retrieves the LastOrderDate property.
     *
     * @return
     *  Date containing the LastOrderDate property.
     */
    public Date getLastOrderDate(){
        return mLastOrderDate;
    }


    /**
     * Sets the YtdQty property.
     *
     * @param pYtdQty
     *  int to use to update the property.
     */
    public void setYtdQty(int pYtdQty){
        this.mYtdQty = pYtdQty;
    }
    /**
     * Retrieves the YtdQty property.
     *
     * @return
     *  int containing the YtdQty property.
     */
    public int getYtdQty(){
        return mYtdQty;
    }


    /**
     * Sets the YtdQtyMainDist property.
     *
     * @param pYtdQtyMainDist
     *  int to use to update the property.
     */
    public void setYtdQtyMainDist(int pYtdQtyMainDist){
        this.mYtdQtyMainDist = pYtdQtyMainDist;
    }
    /**
     * Retrieves the YtdQtyMainDist property.
     *
     * @return
     *  int containing the YtdQtyMainDist property.
     */
    public int getYtdQtyMainDist(){
        return mYtdQtyMainDist;
    }


    /**
     * Sets the YtdCostMainDist property.
     *
     * @param pYtdCostMainDist
     *  BigDecimal to use to update the property.
     */
    public void setYtdCostMainDist(BigDecimal pYtdCostMainDist){
        this.mYtdCostMainDist = pYtdCostMainDist;
    }
    /**
     * Retrieves the YtdCostMainDist property.
     *
     * @return
     *  BigDecimal containing the YtdCostMainDist property.
     */
    public BigDecimal getYtdCostMainDist(){
        return mYtdCostMainDist;
    }


    /**
     * Sets the AvgCost property.
     *
     * @param pAvgCost
     *  BigDecimal to use to update the property.
     */
    public void setAvgCost(BigDecimal pAvgCost){
        this.mAvgCost = pAvgCost;
    }
    /**
     * Retrieves the AvgCost property.
     *
     * @return
     *  BigDecimal containing the AvgCost property.
     */
    public BigDecimal getAvgCost(){
        return mAvgCost;
    }


    /**
     * Sets the CategoryName property.
     *
     * @param pCategoryName
     *  String to use to update the property.
     */
    public void setCategoryName(String pCategoryName){
        this.mCategoryName = pCategoryName;
    }
    /**
     * Retrieves the CategoryName property.
     *
     * @return
     *  String containing the CategoryName property.
     */
    public String getCategoryName(){
        return mCategoryName;
    }


    /**
     * Sets the StandardProductList property.
     *
     * @param pStandardProductList
     *  String to use to update the property.
     */
    public void setStandardProductList(String pStandardProductList){
        this.mStandardProductList = pStandardProductList;
    }
    /**
     * Retrieves the StandardProductList property.
     *
     * @return
     *  String containing the StandardProductList property.
     */
    public String getStandardProductList(){
        return mStandardProductList;
    }


    
}
