
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        EstimatorProdResultView
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
 * <code>EstimatorProdResultView</code> is a ViewObject class for UI.
 */
public class EstimatorProdResultView
extends ValueObject
{
   
    private static final long serialVersionUID = 5175907424544941543L;
    private int mEstimatorFacilityId;
    private int mItemId;
    private String mSkuNum;
    private String mCategory;
    private String mMajorCategory;
    private String mProductName;
    private String mEstimatorProductCd;
    private BigDecimal mUnitSize;
    private String mUnitCd;
    private int mPackQty;
    private BigDecimal mProductPrice;
    private BigDecimal mYearQty;
    private BigDecimal mYearPrice;
    private BigDecimal mAllFacilityYearQty;
    private BigDecimal mAllFacilityYearPrice;

    /**
     * Constructor.
     */
    public EstimatorProdResultView ()
    {
        mSkuNum = "";
        mCategory = "";
        mMajorCategory = "";
        mProductName = "";
        mEstimatorProductCd = "";
        mUnitCd = "";
    }

    /**
     * Constructor. 
     */
    public EstimatorProdResultView(int parm1, int parm2, String parm3, String parm4, String parm5, String parm6, String parm7, BigDecimal parm8, String parm9, int parm10, BigDecimal parm11, BigDecimal parm12, BigDecimal parm13, BigDecimal parm14, BigDecimal parm15)
    {
        mEstimatorFacilityId = parm1;
        mItemId = parm2;
        mSkuNum = parm3;
        mCategory = parm4;
        mMajorCategory = parm5;
        mProductName = parm6;
        mEstimatorProductCd = parm7;
        mUnitSize = parm8;
        mUnitCd = parm9;
        mPackQty = parm10;
        mProductPrice = parm11;
        mYearQty = parm12;
        mYearPrice = parm13;
        mAllFacilityYearQty = parm14;
        mAllFacilityYearPrice = parm15;
        
    }

    /**
     * Creates a new EstimatorProdResultView
     *
     * @return
     *  Newly initialized EstimatorProdResultView object.
     */
    public static EstimatorProdResultView createValue () 
    {
        EstimatorProdResultView valueView = new EstimatorProdResultView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this EstimatorProdResultView object
     */
    public String toString()
    {
        return "[" + "EstimatorFacilityId=" + mEstimatorFacilityId + ", ItemId=" + mItemId + ", SkuNum=" + mSkuNum + ", Category=" + mCategory + ", MajorCategory=" + mMajorCategory + ", ProductName=" + mProductName + ", EstimatorProductCd=" + mEstimatorProductCd + ", UnitSize=" + mUnitSize + ", UnitCd=" + mUnitCd + ", PackQty=" + mPackQty + ", ProductPrice=" + mProductPrice + ", YearQty=" + mYearQty + ", YearPrice=" + mYearPrice + ", AllFacilityYearQty=" + mAllFacilityYearQty + ", AllFacilityYearPrice=" + mAllFacilityYearPrice + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("EstimatorProdResult");
	root.setAttribute("Id", String.valueOf(mEstimatorFacilityId));

	Element node;

        node = doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node = doc.createElement("SkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSkuNum)));
        root.appendChild(node);

        node = doc.createElement("Category");
        node.appendChild(doc.createTextNode(String.valueOf(mCategory)));
        root.appendChild(node);

        node = doc.createElement("MajorCategory");
        node.appendChild(doc.createTextNode(String.valueOf(mMajorCategory)));
        root.appendChild(node);

        node = doc.createElement("ProductName");
        node.appendChild(doc.createTextNode(String.valueOf(mProductName)));
        root.appendChild(node);

        node = doc.createElement("EstimatorProductCd");
        node.appendChild(doc.createTextNode(String.valueOf(mEstimatorProductCd)));
        root.appendChild(node);

        node = doc.createElement("UnitSize");
        node.appendChild(doc.createTextNode(String.valueOf(mUnitSize)));
        root.appendChild(node);

        node = doc.createElement("UnitCd");
        node.appendChild(doc.createTextNode(String.valueOf(mUnitCd)));
        root.appendChild(node);

        node = doc.createElement("PackQty");
        node.appendChild(doc.createTextNode(String.valueOf(mPackQty)));
        root.appendChild(node);

        node = doc.createElement("ProductPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mProductPrice)));
        root.appendChild(node);

        node = doc.createElement("YearQty");
        node.appendChild(doc.createTextNode(String.valueOf(mYearQty)));
        root.appendChild(node);

        node = doc.createElement("YearPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mYearPrice)));
        root.appendChild(node);

        node = doc.createElement("AllFacilityYearQty");
        node.appendChild(doc.createTextNode(String.valueOf(mAllFacilityYearQty)));
        root.appendChild(node);

        node = doc.createElement("AllFacilityYearPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mAllFacilityYearPrice)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public EstimatorProdResultView copy()  {
      EstimatorProdResultView obj = new EstimatorProdResultView();
      obj.setEstimatorFacilityId(mEstimatorFacilityId);
      obj.setItemId(mItemId);
      obj.setSkuNum(mSkuNum);
      obj.setCategory(mCategory);
      obj.setMajorCategory(mMajorCategory);
      obj.setProductName(mProductName);
      obj.setEstimatorProductCd(mEstimatorProductCd);
      obj.setUnitSize(mUnitSize);
      obj.setUnitCd(mUnitCd);
      obj.setPackQty(mPackQty);
      obj.setProductPrice(mProductPrice);
      obj.setYearQty(mYearQty);
      obj.setYearPrice(mYearPrice);
      obj.setAllFacilityYearQty(mAllFacilityYearQty);
      obj.setAllFacilityYearPrice(mAllFacilityYearPrice);
      
      return obj;
    }

    
    /**
     * Sets the EstimatorFacilityId property.
     *
     * @param pEstimatorFacilityId
     *  int to use to update the property.
     */
    public void setEstimatorFacilityId(int pEstimatorFacilityId){
        this.mEstimatorFacilityId = pEstimatorFacilityId;
    }
    /**
     * Retrieves the EstimatorFacilityId property.
     *
     * @return
     *  int containing the EstimatorFacilityId property.
     */
    public int getEstimatorFacilityId(){
        return mEstimatorFacilityId;
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
     * Sets the SkuNum property.
     *
     * @param pSkuNum
     *  String to use to update the property.
     */
    public void setSkuNum(String pSkuNum){
        this.mSkuNum = pSkuNum;
    }
    /**
     * Retrieves the SkuNum property.
     *
     * @return
     *  String containing the SkuNum property.
     */
    public String getSkuNum(){
        return mSkuNum;
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
     * Sets the MajorCategory property.
     *
     * @param pMajorCategory
     *  String to use to update the property.
     */
    public void setMajorCategory(String pMajorCategory){
        this.mMajorCategory = pMajorCategory;
    }
    /**
     * Retrieves the MajorCategory property.
     *
     * @return
     *  String containing the MajorCategory property.
     */
    public String getMajorCategory(){
        return mMajorCategory;
    }


    /**
     * Sets the ProductName property.
     *
     * @param pProductName
     *  String to use to update the property.
     */
    public void setProductName(String pProductName){
        this.mProductName = pProductName;
    }
    /**
     * Retrieves the ProductName property.
     *
     * @return
     *  String containing the ProductName property.
     */
    public String getProductName(){
        return mProductName;
    }


    /**
     * Sets the EstimatorProductCd property.
     *
     * @param pEstimatorProductCd
     *  String to use to update the property.
     */
    public void setEstimatorProductCd(String pEstimatorProductCd){
        this.mEstimatorProductCd = pEstimatorProductCd;
    }
    /**
     * Retrieves the EstimatorProductCd property.
     *
     * @return
     *  String containing the EstimatorProductCd property.
     */
    public String getEstimatorProductCd(){
        return mEstimatorProductCd;
    }


    /**
     * Sets the UnitSize property.
     *
     * @param pUnitSize
     *  BigDecimal to use to update the property.
     */
    public void setUnitSize(BigDecimal pUnitSize){
        this.mUnitSize = pUnitSize;
    }
    /**
     * Retrieves the UnitSize property.
     *
     * @return
     *  BigDecimal containing the UnitSize property.
     */
    public BigDecimal getUnitSize(){
        return mUnitSize;
    }


    /**
     * Sets the UnitCd property.
     *
     * @param pUnitCd
     *  String to use to update the property.
     */
    public void setUnitCd(String pUnitCd){
        this.mUnitCd = pUnitCd;
    }
    /**
     * Retrieves the UnitCd property.
     *
     * @return
     *  String containing the UnitCd property.
     */
    public String getUnitCd(){
        return mUnitCd;
    }


    /**
     * Sets the PackQty property.
     *
     * @param pPackQty
     *  int to use to update the property.
     */
    public void setPackQty(int pPackQty){
        this.mPackQty = pPackQty;
    }
    /**
     * Retrieves the PackQty property.
     *
     * @return
     *  int containing the PackQty property.
     */
    public int getPackQty(){
        return mPackQty;
    }


    /**
     * Sets the ProductPrice property.
     *
     * @param pProductPrice
     *  BigDecimal to use to update the property.
     */
    public void setProductPrice(BigDecimal pProductPrice){
        this.mProductPrice = pProductPrice;
    }
    /**
     * Retrieves the ProductPrice property.
     *
     * @return
     *  BigDecimal containing the ProductPrice property.
     */
    public BigDecimal getProductPrice(){
        return mProductPrice;
    }


    /**
     * Sets the YearQty property.
     *
     * @param pYearQty
     *  BigDecimal to use to update the property.
     */
    public void setYearQty(BigDecimal pYearQty){
        this.mYearQty = pYearQty;
    }
    /**
     * Retrieves the YearQty property.
     *
     * @return
     *  BigDecimal containing the YearQty property.
     */
    public BigDecimal getYearQty(){
        return mYearQty;
    }


    /**
     * Sets the YearPrice property.
     *
     * @param pYearPrice
     *  BigDecimal to use to update the property.
     */
    public void setYearPrice(BigDecimal pYearPrice){
        this.mYearPrice = pYearPrice;
    }
    /**
     * Retrieves the YearPrice property.
     *
     * @return
     *  BigDecimal containing the YearPrice property.
     */
    public BigDecimal getYearPrice(){
        return mYearPrice;
    }


    /**
     * Sets the AllFacilityYearQty property.
     *
     * @param pAllFacilityYearQty
     *  BigDecimal to use to update the property.
     */
    public void setAllFacilityYearQty(BigDecimal pAllFacilityYearQty){
        this.mAllFacilityYearQty = pAllFacilityYearQty;
    }
    /**
     * Retrieves the AllFacilityYearQty property.
     *
     * @return
     *  BigDecimal containing the AllFacilityYearQty property.
     */
    public BigDecimal getAllFacilityYearQty(){
        return mAllFacilityYearQty;
    }


    /**
     * Sets the AllFacilityYearPrice property.
     *
     * @param pAllFacilityYearPrice
     *  BigDecimal to use to update the property.
     */
    public void setAllFacilityYearPrice(BigDecimal pAllFacilityYearPrice){
        this.mAllFacilityYearPrice = pAllFacilityYearPrice;
    }
    /**
     * Retrieves the AllFacilityYearPrice property.
     *
     * @return
     *  BigDecimal containing the AllFacilityYearPrice property.
     */
    public BigDecimal getAllFacilityYearPrice(){
        return mAllFacilityYearPrice;
    }


    
}
