
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ContractItemProfitView
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
 * <code>ContractItemProfitView</code> is a ViewObject class for UI.
 */
public class ContractItemProfitView
extends ValueObject
{
   
    private static final long serialVersionUID = 6542912680795405213L;
    private int mItemId;
    private int mItemSku;
    private String mItemDesc;
    private String mItemUom;
    private String mItemPack;
    private String mItemSize;
    private String mItemColor;
    private BigDecimal mListPrice;
    private BigDecimal mCostPriceMFCP;
    private int mItemMfgId;
    private String mItemMfgName;
    private String mItemMfgSku;
    private int mItemMfg1Id;
    private String mItemMfg1Name;
    private String mItemMfg1Sku;
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
    private BigDecimal mDistBaseCost;
    private BigDecimal mPrice;
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
    private boolean mSPL;
    private java.util.Date mItemAddedDate;

    /**
     * Constructor.
     */
    public ContractItemProfitView ()
    {
        mItemDesc = "";
        mItemUom = "";
        mItemPack = "";
        mItemSize = "";
        mItemColor = "";
        mItemMfgName = "";
        mItemMfgSku = "";
        mItemMfg1Name = "";
        mItemMfg1Sku = "";
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
    }

    /**
     * Constructor. 
     */
    public ContractItemProfitView(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, String parm7, BigDecimal parm8, BigDecimal parm9, int parm10, String parm11, String parm12, int parm13, String parm14, String parm15, int parm16, String parm17, int parm18, String parm19, String parm20, String parm21, String parm22, String parm23, String parm24, BigDecimal parm25, BigDecimal parm26, BigDecimal parm27, String parm28, String parm29, BigDecimal parm30, BigDecimal parm31, Date parm32, int parm33, int parm34, BigDecimal parm35, BigDecimal parm36, String parm37, boolean parm38, java.util.Date parm39)
    {
        mItemId = parm1;
        mItemSku = parm2;
        mItemDesc = parm3;
        mItemUom = parm4;
        mItemPack = parm5;
        mItemSize = parm6;
        mItemColor = parm7;
        mListPrice = parm8;
        mCostPriceMFCP = parm9;
        mItemMfgId = parm10;
        mItemMfgName = parm11;
        mItemMfgSku = parm12;
        mItemMfg1Id = parm13;
        mItemMfg1Name = parm14;
        mItemMfg1Sku = parm15;
        mContractId = parm16;
        mContractName = parm17;
        mDistId = parm18;
        mDistName = parm19;
        mContrDistFl = parm20;
        mDistErp = parm21;
        mDistSku = parm22;
        mDistUom = parm23;
        mDistPack = parm24;
        mDistCost = parm25;
        mDistBaseCost = parm26;
        mPrice = parm27;
        mItemCustDesc = parm28;
        mItemCustSku = parm29;
        mPriceDiff = parm30;
        mPriceDiffPr = parm31;
        mLastOrderDate = parm32;
        mYtdQty = parm33;
        mYtdQtyMainDist = parm34;
        mYtdCostMainDist = parm35;
        mAvgCost = parm36;
        mCategoryName = parm37;
        mSPL = parm38;
        mItemAddedDate = parm39;
        
    }

    /**
     * Creates a new ContractItemProfitView
     *
     * @return
     *  Newly initialized ContractItemProfitView object.
     */
    public static ContractItemProfitView createValue () 
    {
        ContractItemProfitView valueView = new ContractItemProfitView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ContractItemProfitView object
     */
    public String toString()
    {
        return "[" + "ItemId=" + mItemId + ", ItemSku=" + mItemSku + ", ItemDesc=" + mItemDesc + ", ItemUom=" + mItemUom + ", ItemPack=" + mItemPack + ", ItemSize=" + mItemSize + ", ItemColor=" + mItemColor + ", ListPrice=" + mListPrice + ", CostPriceMFCP=" + mCostPriceMFCP + ", ItemMfgId=" + mItemMfgId + ", ItemMfgName=" + mItemMfgName + ", ItemMfgSku=" + mItemMfgSku + ", ItemMfg1Id=" + mItemMfg1Id + ", ItemMfg1Name=" + mItemMfg1Name + ", ItemMfg1Sku=" + mItemMfg1Sku + ", ContractId=" + mContractId + ", ContractName=" + mContractName + ", DistId=" + mDistId + ", DistName=" + mDistName + ", ContrDistFl=" + mContrDistFl + ", DistErp=" + mDistErp + ", DistSku=" + mDistSku + ", DistUom=" + mDistUom + ", DistPack=" + mDistPack + ", DistCost=" + mDistCost + ", DistBaseCost=" + mDistBaseCost + ", Price=" + mPrice + ", ItemCustDesc=" + mItemCustDesc + ", ItemCustSku=" + mItemCustSku + ", PriceDiff=" + mPriceDiff + ", PriceDiffPr=" + mPriceDiffPr + ", LastOrderDate=" + mLastOrderDate + ", YtdQty=" + mYtdQty + ", YtdQtyMainDist=" + mYtdQtyMainDist + ", YtdCostMainDist=" + mYtdCostMainDist + ", AvgCost=" + mAvgCost + ", CategoryName=" + mCategoryName + ", SPL=" + mSPL + ", ItemAddedDate=" + mItemAddedDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ContractItemProfit");
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

        node = doc.createElement("ListPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mListPrice)));
        root.appendChild(node);

        node = doc.createElement("CostPriceMFCP");
        node.appendChild(doc.createTextNode(String.valueOf(mCostPriceMFCP)));
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

        node = doc.createElement("ItemMfg1Id");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMfg1Id)));
        root.appendChild(node);

        node = doc.createElement("ItemMfg1Name");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMfg1Name)));
        root.appendChild(node);

        node = doc.createElement("ItemMfg1Sku");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMfg1Sku)));
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

        node = doc.createElement("DistBaseCost");
        node.appendChild(doc.createTextNode(String.valueOf(mDistBaseCost)));
        root.appendChild(node);

        node = doc.createElement("Price");
        node.appendChild(doc.createTextNode(String.valueOf(mPrice)));
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

        node = doc.createElement("SPL");
        node.appendChild(doc.createTextNode(String.valueOf(mSPL)));
        root.appendChild(node);

        node = doc.createElement("ItemAddedDate");
        node.appendChild(doc.createTextNode(String.valueOf(mItemAddedDate)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ContractItemProfitView copy()  {
      ContractItemProfitView obj = new ContractItemProfitView();
      obj.setItemId(mItemId);
      obj.setItemSku(mItemSku);
      obj.setItemDesc(mItemDesc);
      obj.setItemUom(mItemUom);
      obj.setItemPack(mItemPack);
      obj.setItemSize(mItemSize);
      obj.setItemColor(mItemColor);
      obj.setListPrice(mListPrice);
      obj.setCostPriceMFCP(mCostPriceMFCP);
      obj.setItemMfgId(mItemMfgId);
      obj.setItemMfgName(mItemMfgName);
      obj.setItemMfgSku(mItemMfgSku);
      obj.setItemMfg1Id(mItemMfg1Id);
      obj.setItemMfg1Name(mItemMfg1Name);
      obj.setItemMfg1Sku(mItemMfg1Sku);
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
      obj.setDistBaseCost(mDistBaseCost);
      obj.setPrice(mPrice);
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
      obj.setSPL(mSPL);
      obj.setItemAddedDate(mItemAddedDate);
      
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
     * Sets the CostPriceMFCP property.
     *
     * @param pCostPriceMFCP
     *  BigDecimal to use to update the property.
     */
    public void setCostPriceMFCP(BigDecimal pCostPriceMFCP){
        this.mCostPriceMFCP = pCostPriceMFCP;
    }
    /**
     * Retrieves the CostPriceMFCP property.
     *
     * @return
     *  BigDecimal containing the CostPriceMFCP property.
     */
    public BigDecimal getCostPriceMFCP(){
        return mCostPriceMFCP;
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
     * Sets the ItemMfg1Id property.
     *
     * @param pItemMfg1Id
     *  int to use to update the property.
     */
    public void setItemMfg1Id(int pItemMfg1Id){
        this.mItemMfg1Id = pItemMfg1Id;
    }
    /**
     * Retrieves the ItemMfg1Id property.
     *
     * @return
     *  int containing the ItemMfg1Id property.
     */
    public int getItemMfg1Id(){
        return mItemMfg1Id;
    }


    /**
     * Sets the ItemMfg1Name property.
     *
     * @param pItemMfg1Name
     *  String to use to update the property.
     */
    public void setItemMfg1Name(String pItemMfg1Name){
        this.mItemMfg1Name = pItemMfg1Name;
    }
    /**
     * Retrieves the ItemMfg1Name property.
     *
     * @return
     *  String containing the ItemMfg1Name property.
     */
    public String getItemMfg1Name(){
        return mItemMfg1Name;
    }


    /**
     * Sets the ItemMfg1Sku property.
     *
     * @param pItemMfg1Sku
     *  String to use to update the property.
     */
    public void setItemMfg1Sku(String pItemMfg1Sku){
        this.mItemMfg1Sku = pItemMfg1Sku;
    }
    /**
     * Retrieves the ItemMfg1Sku property.
     *
     * @return
     *  String containing the ItemMfg1Sku property.
     */
    public String getItemMfg1Sku(){
        return mItemMfg1Sku;
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
     * Sets the DistBaseCost property.
     *
     * @param pDistBaseCost
     *  BigDecimal to use to update the property.
     */
    public void setDistBaseCost(BigDecimal pDistBaseCost){
        this.mDistBaseCost = pDistBaseCost;
    }
    /**
     * Retrieves the DistBaseCost property.
     *
     * @return
     *  BigDecimal containing the DistBaseCost property.
     */
    public BigDecimal getDistBaseCost(){
        return mDistBaseCost;
    }


    /**
     * Sets the Price property.
     *
     * @param pPrice
     *  BigDecimal to use to update the property.
     */
    public void setPrice(BigDecimal pPrice){
        this.mPrice = pPrice;
    }
    /**
     * Retrieves the Price property.
     *
     * @return
     *  BigDecimal containing the Price property.
     */
    public BigDecimal getPrice(){
        return mPrice;
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
     * Sets the SPL property.
     *
     * @param pSPL
     *  boolean to use to update the property.
     */
    public void setSPL(boolean pSPL){
        this.mSPL = pSPL;
    }
    /**
     * Retrieves the SPL property.
     *
     * @return
     *  boolean containing the SPL property.
     */
    public boolean getSPL(){
        return mSPL;
    }


    /**
     * Sets the ItemAddedDate property.
     *
     * @param pItemAddedDate
     *  java.util.Date to use to update the property.
     */
    public void setItemAddedDate(java.util.Date pItemAddedDate){
        this.mItemAddedDate = pItemAddedDate;
    }
    /**
     * Retrieves the ItemAddedDate property.
     *
     * @return
     *  java.util.Date containing the ItemAddedDate property.
     */
    public java.util.Date getItemAddedDate(){
        return mItemAddedDate;
    }


    
}
