
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ContractItemPriceView
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
 * <code>ContractItemPriceView</code> is a ViewObject class for UI.
 */
public class ContractItemPriceView
extends ValueObject
{
   
    private static final long serialVersionUID = 3239241840915076251L;
    private int mItemId;
    private int mItemSku;
    private String mItemDesc;
    private String mItemUom;
    private String mItemPack;
    private String mItemSize;
    private int mItemMfgId;
    private String mItemMfgName;
    private String mItemMfgSku;
    private int mContractId;
    private String mContractName;
    private int mDistId;
    private String mDistName;
    private BigDecimal mDistCost;
    private BigDecimal mPrice;
    private String mItemCustDesc;
    private String mItemCustSku;

    /**
     * Constructor.
     */
    public ContractItemPriceView ()
    {
        mItemDesc = "";
        mItemUom = "";
        mItemPack = "";
        mItemSize = "";
        mItemMfgName = "";
        mItemMfgSku = "";
        mContractName = "";
        mDistName = "";
        mItemCustDesc = "";
        mItemCustSku = "";
    }

    /**
     * Constructor. 
     */
    public ContractItemPriceView(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, int parm7, String parm8, String parm9, int parm10, String parm11, int parm12, String parm13, BigDecimal parm14, BigDecimal parm15, String parm16, String parm17)
    {
        mItemId = parm1;
        mItemSku = parm2;
        mItemDesc = parm3;
        mItemUom = parm4;
        mItemPack = parm5;
        mItemSize = parm6;
        mItemMfgId = parm7;
        mItemMfgName = parm8;
        mItemMfgSku = parm9;
        mContractId = parm10;
        mContractName = parm11;
        mDistId = parm12;
        mDistName = parm13;
        mDistCost = parm14;
        mPrice = parm15;
        mItemCustDesc = parm16;
        mItemCustSku = parm17;
        
    }

    /**
     * Creates a new ContractItemPriceView
     *
     * @return
     *  Newly initialized ContractItemPriceView object.
     */
    public static ContractItemPriceView createValue () 
    {
        ContractItemPriceView valueView = new ContractItemPriceView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ContractItemPriceView object
     */
    public String toString()
    {
        return "[" + "ItemId=" + mItemId + ", ItemSku=" + mItemSku + ", ItemDesc=" + mItemDesc + ", ItemUom=" + mItemUom + ", ItemPack=" + mItemPack + ", ItemSize=" + mItemSize + ", ItemMfgId=" + mItemMfgId + ", ItemMfgName=" + mItemMfgName + ", ItemMfgSku=" + mItemMfgSku + ", ContractId=" + mContractId + ", ContractName=" + mContractName + ", DistId=" + mDistId + ", DistName=" + mDistName + ", DistCost=" + mDistCost + ", Price=" + mPrice + ", ItemCustDesc=" + mItemCustDesc + ", ItemCustSku=" + mItemCustSku + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ContractItemPrice");
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

        node = doc.createElement("ItemMfgId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMfgId)));
        root.appendChild(node);

        node = doc.createElement("ItemMfgName");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMfgName)));
        root.appendChild(node);

        node = doc.createElement("ItemMfgSku");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMfgSku)));
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

        node = doc.createElement("DistCost");
        node.appendChild(doc.createTextNode(String.valueOf(mDistCost)));
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

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ContractItemPriceView copy()  {
      ContractItemPriceView obj = new ContractItemPriceView();
      obj.setItemId(mItemId);
      obj.setItemSku(mItemSku);
      obj.setItemDesc(mItemDesc);
      obj.setItemUom(mItemUom);
      obj.setItemPack(mItemPack);
      obj.setItemSize(mItemSize);
      obj.setItemMfgId(mItemMfgId);
      obj.setItemMfgName(mItemMfgName);
      obj.setItemMfgSku(mItemMfgSku);
      obj.setContractId(mContractId);
      obj.setContractName(mContractName);
      obj.setDistId(mDistId);
      obj.setDistName(mDistName);
      obj.setDistCost(mDistCost);
      obj.setPrice(mPrice);
      obj.setItemCustDesc(mItemCustDesc);
      obj.setItemCustSku(mItemCustSku);
      
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


    
}
