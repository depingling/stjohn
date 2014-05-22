
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CatalogItemDescView
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
 * <code>CatalogItemDescView</code> is a ViewObject class for UI.
 */
public class CatalogItemDescView
extends ValueObject
{
   
    private static final long serialVersionUID = -5710025135370259459L;
    private int mStoreId;
    private int mCatalogId;
    private int mItemId;
    private int mCategoryId;
    private int mManufId;
    private String mName;
    private String mSku;
    private String mCategory;
    private String mSize;
    private String mPack;
    private String mUom;
    private String mColor;
    private String mManufName;
    private String mManufSku;
    private int mDistId;
    private String mDistName;
    private String mDistSku;
    private BigDecimal mCatalogPrice;
    private BigDecimal mDistCost;
    private BigDecimal mBaseCost;
    private boolean mSelected;

    /**
     * Constructor.
     */
    public CatalogItemDescView ()
    {
        mName = "";
        mSku = "";
        mCategory = "";
        mSize = "";
        mPack = "";
        mUom = "";
        mColor = "";
        mManufName = "";
        mManufSku = "";
        mDistName = "";
        mDistSku = "";
    }

    /**
     * Constructor. 
     */
    public CatalogItemDescView(int parm1, int parm2, int parm3, int parm4, int parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13, String parm14, int parm15, String parm16, String parm17, BigDecimal parm18, BigDecimal parm19, BigDecimal parm20, boolean parm21)
    {
        mStoreId = parm1;
        mCatalogId = parm2;
        mItemId = parm3;
        mCategoryId = parm4;
        mManufId = parm5;
        mName = parm6;
        mSku = parm7;
        mCategory = parm8;
        mSize = parm9;
        mPack = parm10;
        mUom = parm11;
        mColor = parm12;
        mManufName = parm13;
        mManufSku = parm14;
        mDistId = parm15;
        mDistName = parm16;
        mDistSku = parm17;
        mCatalogPrice = parm18;
        mDistCost = parm19;
        mBaseCost = parm20;
        mSelected = parm21;
        
    }

    /**
     * Creates a new CatalogItemDescView
     *
     * @return
     *  Newly initialized CatalogItemDescView object.
     */
    public static CatalogItemDescView createValue () 
    {
        CatalogItemDescView valueView = new CatalogItemDescView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CatalogItemDescView object
     */
    public String toString()
    {
        return "[" + "StoreId=" + mStoreId + ", CatalogId=" + mCatalogId + ", ItemId=" + mItemId + ", CategoryId=" + mCategoryId + ", ManufId=" + mManufId + ", Name=" + mName + ", Sku=" + mSku + ", Category=" + mCategory + ", Size=" + mSize + ", Pack=" + mPack + ", Uom=" + mUom + ", Color=" + mColor + ", ManufName=" + mManufName + ", ManufSku=" + mManufSku + ", DistId=" + mDistId + ", DistName=" + mDistName + ", DistSku=" + mDistSku + ", CatalogPrice=" + mCatalogPrice + ", DistCost=" + mDistCost + ", BaseCost=" + mBaseCost + ", Selected=" + mSelected + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("CatalogItemDesc");
	root.setAttribute("Id", String.valueOf(mStoreId));

	Element node;

        node = doc.createElement("CatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogId)));
        root.appendChild(node);

        node = doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node = doc.createElement("CategoryId");
        node.appendChild(doc.createTextNode(String.valueOf(mCategoryId)));
        root.appendChild(node);

        node = doc.createElement("ManufId");
        node.appendChild(doc.createTextNode(String.valueOf(mManufId)));
        root.appendChild(node);

        node = doc.createElement("Name");
        node.appendChild(doc.createTextNode(String.valueOf(mName)));
        root.appendChild(node);

        node = doc.createElement("Sku");
        node.appendChild(doc.createTextNode(String.valueOf(mSku)));
        root.appendChild(node);

        node = doc.createElement("Category");
        node.appendChild(doc.createTextNode(String.valueOf(mCategory)));
        root.appendChild(node);

        node = doc.createElement("Size");
        node.appendChild(doc.createTextNode(String.valueOf(mSize)));
        root.appendChild(node);

        node = doc.createElement("Pack");
        node.appendChild(doc.createTextNode(String.valueOf(mPack)));
        root.appendChild(node);

        node = doc.createElement("Uom");
        node.appendChild(doc.createTextNode(String.valueOf(mUom)));
        root.appendChild(node);

        node = doc.createElement("Color");
        node.appendChild(doc.createTextNode(String.valueOf(mColor)));
        root.appendChild(node);

        node = doc.createElement("ManufName");
        node.appendChild(doc.createTextNode(String.valueOf(mManufName)));
        root.appendChild(node);

        node = doc.createElement("ManufSku");
        node.appendChild(doc.createTextNode(String.valueOf(mManufSku)));
        root.appendChild(node);

        node = doc.createElement("DistId");
        node.appendChild(doc.createTextNode(String.valueOf(mDistId)));
        root.appendChild(node);

        node = doc.createElement("DistName");
        node.appendChild(doc.createTextNode(String.valueOf(mDistName)));
        root.appendChild(node);

        node = doc.createElement("DistSku");
        node.appendChild(doc.createTextNode(String.valueOf(mDistSku)));
        root.appendChild(node);

        node = doc.createElement("CatalogPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogPrice)));
        root.appendChild(node);

        node = doc.createElement("DistCost");
        node.appendChild(doc.createTextNode(String.valueOf(mDistCost)));
        root.appendChild(node);

        node = doc.createElement("BaseCost");
        node.appendChild(doc.createTextNode(String.valueOf(mBaseCost)));
        root.appendChild(node);

        node = doc.createElement("Selected");
        node.appendChild(doc.createTextNode(String.valueOf(mSelected)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public CatalogItemDescView copy()  {
      CatalogItemDescView obj = new CatalogItemDescView();
      obj.setStoreId(mStoreId);
      obj.setCatalogId(mCatalogId);
      obj.setItemId(mItemId);
      obj.setCategoryId(mCategoryId);
      obj.setManufId(mManufId);
      obj.setName(mName);
      obj.setSku(mSku);
      obj.setCategory(mCategory);
      obj.setSize(mSize);
      obj.setPack(mPack);
      obj.setUom(mUom);
      obj.setColor(mColor);
      obj.setManufName(mManufName);
      obj.setManufSku(mManufSku);
      obj.setDistId(mDistId);
      obj.setDistName(mDistName);
      obj.setDistSku(mDistSku);
      obj.setCatalogPrice(mCatalogPrice);
      obj.setDistCost(mDistCost);
      obj.setBaseCost(mBaseCost);
      obj.setSelected(mSelected);
      
      return obj;
    }

    
    /**
     * Sets the StoreId property.
     *
     * @param pStoreId
     *  int to use to update the property.
     */
    public void setStoreId(int pStoreId){
        this.mStoreId = pStoreId;
    }
    /**
     * Retrieves the StoreId property.
     *
     * @return
     *  int containing the StoreId property.
     */
    public int getStoreId(){
        return mStoreId;
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
     * Sets the CategoryId property.
     *
     * @param pCategoryId
     *  int to use to update the property.
     */
    public void setCategoryId(int pCategoryId){
        this.mCategoryId = pCategoryId;
    }
    /**
     * Retrieves the CategoryId property.
     *
     * @return
     *  int containing the CategoryId property.
     */
    public int getCategoryId(){
        return mCategoryId;
    }


    /**
     * Sets the ManufId property.
     *
     * @param pManufId
     *  int to use to update the property.
     */
    public void setManufId(int pManufId){
        this.mManufId = pManufId;
    }
    /**
     * Retrieves the ManufId property.
     *
     * @return
     *  int containing the ManufId property.
     */
    public int getManufId(){
        return mManufId;
    }


    /**
     * Sets the Name property.
     *
     * @param pName
     *  String to use to update the property.
     */
    public void setName(String pName){
        this.mName = pName;
    }
    /**
     * Retrieves the Name property.
     *
     * @return
     *  String containing the Name property.
     */
    public String getName(){
        return mName;
    }


    /**
     * Sets the Sku property.
     *
     * @param pSku
     *  String to use to update the property.
     */
    public void setSku(String pSku){
        this.mSku = pSku;
    }
    /**
     * Retrieves the Sku property.
     *
     * @return
     *  String containing the Sku property.
     */
    public String getSku(){
        return mSku;
    }


    /**
     * Sets the Category property.
     *
     * @param pCategory
     *  String to use to update the property.
     */
    public void setCategory(String pCategory){
        this.mCategory = pCategory;
    }
    /**
     * Retrieves the Category property.
     *
     * @return
     *  String containing the Category property.
     */
    public String getCategory(){
        return mCategory;
    }


    /**
     * Sets the Size property.
     *
     * @param pSize
     *  String to use to update the property.
     */
    public void setSize(String pSize){
        this.mSize = pSize;
    }
    /**
     * Retrieves the Size property.
     *
     * @return
     *  String containing the Size property.
     */
    public String getSize(){
        return mSize;
    }


    /**
     * Sets the Pack property.
     *
     * @param pPack
     *  String to use to update the property.
     */
    public void setPack(String pPack){
        this.mPack = pPack;
    }
    /**
     * Retrieves the Pack property.
     *
     * @return
     *  String containing the Pack property.
     */
    public String getPack(){
        return mPack;
    }


    /**
     * Sets the Uom property.
     *
     * @param pUom
     *  String to use to update the property.
     */
    public void setUom(String pUom){
        this.mUom = pUom;
    }
    /**
     * Retrieves the Uom property.
     *
     * @return
     *  String containing the Uom property.
     */
    public String getUom(){
        return mUom;
    }


    /**
     * Sets the Color property.
     *
     * @param pColor
     *  String to use to update the property.
     */
    public void setColor(String pColor){
        this.mColor = pColor;
    }
    /**
     * Retrieves the Color property.
     *
     * @return
     *  String containing the Color property.
     */
    public String getColor(){
        return mColor;
    }


    /**
     * Sets the ManufName property.
     *
     * @param pManufName
     *  String to use to update the property.
     */
    public void setManufName(String pManufName){
        this.mManufName = pManufName;
    }
    /**
     * Retrieves the ManufName property.
     *
     * @return
     *  String containing the ManufName property.
     */
    public String getManufName(){
        return mManufName;
    }


    /**
     * Sets the ManufSku property.
     *
     * @param pManufSku
     *  String to use to update the property.
     */
    public void setManufSku(String pManufSku){
        this.mManufSku = pManufSku;
    }
    /**
     * Retrieves the ManufSku property.
     *
     * @return
     *  String containing the ManufSku property.
     */
    public String getManufSku(){
        return mManufSku;
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
     * Sets the CatalogPrice property.
     *
     * @param pCatalogPrice
     *  BigDecimal to use to update the property.
     */
    public void setCatalogPrice(BigDecimal pCatalogPrice){
        this.mCatalogPrice = pCatalogPrice;
    }
    /**
     * Retrieves the CatalogPrice property.
     *
     * @return
     *  BigDecimal containing the CatalogPrice property.
     */
    public BigDecimal getCatalogPrice(){
        return mCatalogPrice;
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
     * Sets the BaseCost property.
     *
     * @param pBaseCost
     *  BigDecimal to use to update the property.
     */
    public void setBaseCost(BigDecimal pBaseCost){
        this.mBaseCost = pBaseCost;
    }
    /**
     * Retrieves the BaseCost property.
     *
     * @return
     *  BigDecimal containing the BaseCost property.
     */
    public BigDecimal getBaseCost(){
        return mBaseCost;
    }


    /**
     * Sets the Selected property.
     *
     * @param pSelected
     *  boolean to use to update the property.
     */
    public void setSelected(boolean pSelected){
        this.mSelected = pSelected;
    }
    /**
     * Retrieves the Selected property.
     *
     * @return
     *  boolean containing the Selected property.
     */
    public boolean getSelected(){
        return mSelected;
    }


    
}
