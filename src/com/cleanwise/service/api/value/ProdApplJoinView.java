
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ProdApplJoinView
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




/**
 * <code>ProdApplJoinView</code> is a ViewObject class for UI.
 */
public class ProdApplJoinView
extends ValueObject
{
   
    private static final long serialVersionUID = -4945960637663729974L;
    private ProdApplData mProdAppl;
    private ProductUomPackData mProductUomPack;
    private ItemData mItem;
    private String mItemCategory;
    private String mDilutionRate;
    private String mUsageRate;
    private String mUnitCdNumerator;
    private String mUnitCdDenominator;
    private String mUnitCdDenominator1;
    private String mSharingPercent;

    /**
     * Constructor.
     */
    public ProdApplJoinView ()
    {
        mItemCategory = "";
        mDilutionRate = "";
        mUsageRate = "";
        mUnitCdNumerator = "";
        mUnitCdDenominator = "";
        mUnitCdDenominator1 = "";
        mSharingPercent = "";
    }

    /**
     * Constructor. 
     */
    public ProdApplJoinView(ProdApplData parm1, ProductUomPackData parm2, ItemData parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10)
    {
        mProdAppl = parm1;
        mProductUomPack = parm2;
        mItem = parm3;
        mItemCategory = parm4;
        mDilutionRate = parm5;
        mUsageRate = parm6;
        mUnitCdNumerator = parm7;
        mUnitCdDenominator = parm8;
        mUnitCdDenominator1 = parm9;
        mSharingPercent = parm10;
        
    }

    /**
     * Creates a new ProdApplJoinView
     *
     * @return
     *  Newly initialized ProdApplJoinView object.
     */
    public static ProdApplJoinView createValue () 
    {
        ProdApplJoinView valueView = new ProdApplJoinView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ProdApplJoinView object
     */
    public String toString()
    {
        return "[" + "ProdAppl=" + mProdAppl + ", ProductUomPack=" + mProductUomPack + ", Item=" + mItem + ", ItemCategory=" + mItemCategory + ", DilutionRate=" + mDilutionRate + ", UsageRate=" + mUsageRate + ", UnitCdNumerator=" + mUnitCdNumerator + ", UnitCdDenominator=" + mUnitCdDenominator + ", UnitCdDenominator1=" + mUnitCdDenominator1 + ", SharingPercent=" + mSharingPercent + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ProdApplJoin");
	root.setAttribute("Id", String.valueOf(mProdAppl));

	Element node;

        node = doc.createElement("ProductUomPack");
        node.appendChild(doc.createTextNode(String.valueOf(mProductUomPack)));
        root.appendChild(node);

        node = doc.createElement("Item");
        node.appendChild(doc.createTextNode(String.valueOf(mItem)));
        root.appendChild(node);

        node = doc.createElement("ItemCategory");
        node.appendChild(doc.createTextNode(String.valueOf(mItemCategory)));
        root.appendChild(node);

        node = doc.createElement("DilutionRate");
        node.appendChild(doc.createTextNode(String.valueOf(mDilutionRate)));
        root.appendChild(node);

        node = doc.createElement("UsageRate");
        node.appendChild(doc.createTextNode(String.valueOf(mUsageRate)));
        root.appendChild(node);

        node = doc.createElement("UnitCdNumerator");
        node.appendChild(doc.createTextNode(String.valueOf(mUnitCdNumerator)));
        root.appendChild(node);

        node = doc.createElement("UnitCdDenominator");
        node.appendChild(doc.createTextNode(String.valueOf(mUnitCdDenominator)));
        root.appendChild(node);

        node = doc.createElement("UnitCdDenominator1");
        node.appendChild(doc.createTextNode(String.valueOf(mUnitCdDenominator1)));
        root.appendChild(node);

        node = doc.createElement("SharingPercent");
        node.appendChild(doc.createTextNode(String.valueOf(mSharingPercent)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ProdApplJoinView copy()  {
      ProdApplJoinView obj = new ProdApplJoinView();
      obj.setProdAppl(mProdAppl);
      obj.setProductUomPack(mProductUomPack);
      obj.setItem(mItem);
      obj.setItemCategory(mItemCategory);
      obj.setDilutionRate(mDilutionRate);
      obj.setUsageRate(mUsageRate);
      obj.setUnitCdNumerator(mUnitCdNumerator);
      obj.setUnitCdDenominator(mUnitCdDenominator);
      obj.setUnitCdDenominator1(mUnitCdDenominator1);
      obj.setSharingPercent(mSharingPercent);
      
      return obj;
    }

    
    /**
     * Sets the ProdAppl property.
     *
     * @param pProdAppl
     *  ProdApplData to use to update the property.
     */
    public void setProdAppl(ProdApplData pProdAppl){
        this.mProdAppl = pProdAppl;
    }
    /**
     * Retrieves the ProdAppl property.
     *
     * @return
     *  ProdApplData containing the ProdAppl property.
     */
    public ProdApplData getProdAppl(){
        return mProdAppl;
    }


    /**
     * Sets the ProductUomPack property.
     *
     * @param pProductUomPack
     *  ProductUomPackData to use to update the property.
     */
    public void setProductUomPack(ProductUomPackData pProductUomPack){
        this.mProductUomPack = pProductUomPack;
    }
    /**
     * Retrieves the ProductUomPack property.
     *
     * @return
     *  ProductUomPackData containing the ProductUomPack property.
     */
    public ProductUomPackData getProductUomPack(){
        return mProductUomPack;
    }


    /**
     * Sets the Item property.
     *
     * @param pItem
     *  ItemData to use to update the property.
     */
    public void setItem(ItemData pItem){
        this.mItem = pItem;
    }
    /**
     * Retrieves the Item property.
     *
     * @return
     *  ItemData containing the Item property.
     */
    public ItemData getItem(){
        return mItem;
    }


    /**
     * Sets the ItemCategory property.
     *
     * @param pItemCategory
     *  String to use to update the property.
     */
    public void setItemCategory(String pItemCategory){
        this.mItemCategory = pItemCategory;
    }
    /**
     * Retrieves the ItemCategory property.
     *
     * @return
     *  String containing the ItemCategory property.
     */
    public String getItemCategory(){
        return mItemCategory;
    }


    /**
     * Sets the DilutionRate property.
     *
     * @param pDilutionRate
     *  String to use to update the property.
     */
    public void setDilutionRate(String pDilutionRate){
        this.mDilutionRate = pDilutionRate;
    }
    /**
     * Retrieves the DilutionRate property.
     *
     * @return
     *  String containing the DilutionRate property.
     */
    public String getDilutionRate(){
        return mDilutionRate;
    }


    /**
     * Sets the UsageRate property.
     *
     * @param pUsageRate
     *  String to use to update the property.
     */
    public void setUsageRate(String pUsageRate){
        this.mUsageRate = pUsageRate;
    }
    /**
     * Retrieves the UsageRate property.
     *
     * @return
     *  String containing the UsageRate property.
     */
    public String getUsageRate(){
        return mUsageRate;
    }


    /**
     * Sets the UnitCdNumerator property.
     *
     * @param pUnitCdNumerator
     *  String to use to update the property.
     */
    public void setUnitCdNumerator(String pUnitCdNumerator){
        this.mUnitCdNumerator = pUnitCdNumerator;
    }
    /**
     * Retrieves the UnitCdNumerator property.
     *
     * @return
     *  String containing the UnitCdNumerator property.
     */
    public String getUnitCdNumerator(){
        return mUnitCdNumerator;
    }


    /**
     * Sets the UnitCdDenominator property.
     *
     * @param pUnitCdDenominator
     *  String to use to update the property.
     */
    public void setUnitCdDenominator(String pUnitCdDenominator){
        this.mUnitCdDenominator = pUnitCdDenominator;
    }
    /**
     * Retrieves the UnitCdDenominator property.
     *
     * @return
     *  String containing the UnitCdDenominator property.
     */
    public String getUnitCdDenominator(){
        return mUnitCdDenominator;
    }


    /**
     * Sets the UnitCdDenominator1 property.
     *
     * @param pUnitCdDenominator1
     *  String to use to update the property.
     */
    public void setUnitCdDenominator1(String pUnitCdDenominator1){
        this.mUnitCdDenominator1 = pUnitCdDenominator1;
    }
    /**
     * Retrieves the UnitCdDenominator1 property.
     *
     * @return
     *  String containing the UnitCdDenominator1 property.
     */
    public String getUnitCdDenominator1(){
        return mUnitCdDenominator1;
    }


    /**
     * Sets the SharingPercent property.
     *
     * @param pSharingPercent
     *  String to use to update the property.
     */
    public void setSharingPercent(String pSharingPercent){
        this.mSharingPercent = pSharingPercent;
    }
    /**
     * Retrieves the SharingPercent property.
     *
     * @return
     *  String containing the SharingPercent property.
     */
    public String getSharingPercent(){
        return mSharingPercent;
    }


    
}
