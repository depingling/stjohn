
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PaperPlusApplView
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
 * <code>PaperPlusApplView</code> is a ViewObject class for UI.
 */
public class PaperPlusApplView
extends ValueObject
{
   
    private static final long serialVersionUID = 4028167420975310745L;
    private int mProdApplId;
    private int mEstimatorFacilityId;
    private int mItemId;
    private String mSkuNum;
    private String mProductName;
    private String mEstimatorProductCd;
    private BigDecimal mUnitSize;
    private String mUnitCd;
    private int mPackQty;
    private BigDecimal mProductPrice;
    private String mUsageRate;
    private String mDefaultUsageRate;
    private String mUnitCdNumerator;
    private String mUnitCdDenominator;
    private String mAppearanceStandardCd;
    private String mLinerSizeCd;
    private String mApplyFl;
    private int mPoolQty;
    private BigDecimal mYearQty;
    private BigDecimal mYearPrice;
    private BigDecimal mAllFacilityYearQty;
    private BigDecimal mAllFacilityYearPrice;

    /**
     * Constructor.
     */
    public PaperPlusApplView ()
    {
        mSkuNum = "";
        mProductName = "";
        mEstimatorProductCd = "";
        mUnitCd = "";
        mUsageRate = "";
        mDefaultUsageRate = "";
        mUnitCdNumerator = "";
        mUnitCdDenominator = "";
        mAppearanceStandardCd = "";
        mLinerSizeCd = "";
        mApplyFl = "";
    }

    /**
     * Constructor. 
     */
    public PaperPlusApplView(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, BigDecimal parm7, String parm8, int parm9, BigDecimal parm10, String parm11, String parm12, String parm13, String parm14, String parm15, String parm16, String parm17, int parm18, BigDecimal parm19, BigDecimal parm20, BigDecimal parm21, BigDecimal parm22)
    {
        mProdApplId = parm1;
        mEstimatorFacilityId = parm2;
        mItemId = parm3;
        mSkuNum = parm4;
        mProductName = parm5;
        mEstimatorProductCd = parm6;
        mUnitSize = parm7;
        mUnitCd = parm8;
        mPackQty = parm9;
        mProductPrice = parm10;
        mUsageRate = parm11;
        mDefaultUsageRate = parm12;
        mUnitCdNumerator = parm13;
        mUnitCdDenominator = parm14;
        mAppearanceStandardCd = parm15;
        mLinerSizeCd = parm16;
        mApplyFl = parm17;
        mPoolQty = parm18;
        mYearQty = parm19;
        mYearPrice = parm20;
        mAllFacilityYearQty = parm21;
        mAllFacilityYearPrice = parm22;
        
    }

    /**
     * Creates a new PaperPlusApplView
     *
     * @return
     *  Newly initialized PaperPlusApplView object.
     */
    public static PaperPlusApplView createValue () 
    {
        PaperPlusApplView valueView = new PaperPlusApplView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PaperPlusApplView object
     */
    public String toString()
    {
        return "[" + "ProdApplId=" + mProdApplId + ", EstimatorFacilityId=" + mEstimatorFacilityId + ", ItemId=" + mItemId + ", SkuNum=" + mSkuNum + ", ProductName=" + mProductName + ", EstimatorProductCd=" + mEstimatorProductCd + ", UnitSize=" + mUnitSize + ", UnitCd=" + mUnitCd + ", PackQty=" + mPackQty + ", ProductPrice=" + mProductPrice + ", UsageRate=" + mUsageRate + ", DefaultUsageRate=" + mDefaultUsageRate + ", UnitCdNumerator=" + mUnitCdNumerator + ", UnitCdDenominator=" + mUnitCdDenominator + ", AppearanceStandardCd=" + mAppearanceStandardCd + ", LinerSizeCd=" + mLinerSizeCd + ", ApplyFl=" + mApplyFl + ", PoolQty=" + mPoolQty + ", YearQty=" + mYearQty + ", YearPrice=" + mYearPrice + ", AllFacilityYearQty=" + mAllFacilityYearQty + ", AllFacilityYearPrice=" + mAllFacilityYearPrice + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("PaperPlusAppl");
	root.setAttribute("Id", String.valueOf(mProdApplId));

	Element node;

        node = doc.createElement("EstimatorFacilityId");
        node.appendChild(doc.createTextNode(String.valueOf(mEstimatorFacilityId)));
        root.appendChild(node);

        node = doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node = doc.createElement("SkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSkuNum)));
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

        node = doc.createElement("UsageRate");
        node.appendChild(doc.createTextNode(String.valueOf(mUsageRate)));
        root.appendChild(node);

        node = doc.createElement("DefaultUsageRate");
        node.appendChild(doc.createTextNode(String.valueOf(mDefaultUsageRate)));
        root.appendChild(node);

        node = doc.createElement("UnitCdNumerator");
        node.appendChild(doc.createTextNode(String.valueOf(mUnitCdNumerator)));
        root.appendChild(node);

        node = doc.createElement("UnitCdDenominator");
        node.appendChild(doc.createTextNode(String.valueOf(mUnitCdDenominator)));
        root.appendChild(node);

        node = doc.createElement("AppearanceStandardCd");
        node.appendChild(doc.createTextNode(String.valueOf(mAppearanceStandardCd)));
        root.appendChild(node);

        node = doc.createElement("LinerSizeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mLinerSizeCd)));
        root.appendChild(node);

        node = doc.createElement("ApplyFl");
        node.appendChild(doc.createTextNode(String.valueOf(mApplyFl)));
        root.appendChild(node);

        node = doc.createElement("PoolQty");
        node.appendChild(doc.createTextNode(String.valueOf(mPoolQty)));
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
    public PaperPlusApplView copy()  {
      PaperPlusApplView obj = new PaperPlusApplView();
      obj.setProdApplId(mProdApplId);
      obj.setEstimatorFacilityId(mEstimatorFacilityId);
      obj.setItemId(mItemId);
      obj.setSkuNum(mSkuNum);
      obj.setProductName(mProductName);
      obj.setEstimatorProductCd(mEstimatorProductCd);
      obj.setUnitSize(mUnitSize);
      obj.setUnitCd(mUnitCd);
      obj.setPackQty(mPackQty);
      obj.setProductPrice(mProductPrice);
      obj.setUsageRate(mUsageRate);
      obj.setDefaultUsageRate(mDefaultUsageRate);
      obj.setUnitCdNumerator(mUnitCdNumerator);
      obj.setUnitCdDenominator(mUnitCdDenominator);
      obj.setAppearanceStandardCd(mAppearanceStandardCd);
      obj.setLinerSizeCd(mLinerSizeCd);
      obj.setApplyFl(mApplyFl);
      obj.setPoolQty(mPoolQty);
      obj.setYearQty(mYearQty);
      obj.setYearPrice(mYearPrice);
      obj.setAllFacilityYearQty(mAllFacilityYearQty);
      obj.setAllFacilityYearPrice(mAllFacilityYearPrice);
      
      return obj;
    }

    
    /**
     * Sets the ProdApplId property.
     *
     * @param pProdApplId
     *  int to use to update the property.
     */
    public void setProdApplId(int pProdApplId){
        this.mProdApplId = pProdApplId;
    }
    /**
     * Retrieves the ProdApplId property.
     *
     * @return
     *  int containing the ProdApplId property.
     */
    public int getProdApplId(){
        return mProdApplId;
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
     * Sets the DefaultUsageRate property.
     *
     * @param pDefaultUsageRate
     *  String to use to update the property.
     */
    public void setDefaultUsageRate(String pDefaultUsageRate){
        this.mDefaultUsageRate = pDefaultUsageRate;
    }
    /**
     * Retrieves the DefaultUsageRate property.
     *
     * @return
     *  String containing the DefaultUsageRate property.
     */
    public String getDefaultUsageRate(){
        return mDefaultUsageRate;
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
     * Sets the AppearanceStandardCd property.
     *
     * @param pAppearanceStandardCd
     *  String to use to update the property.
     */
    public void setAppearanceStandardCd(String pAppearanceStandardCd){
        this.mAppearanceStandardCd = pAppearanceStandardCd;
    }
    /**
     * Retrieves the AppearanceStandardCd property.
     *
     * @return
     *  String containing the AppearanceStandardCd property.
     */
    public String getAppearanceStandardCd(){
        return mAppearanceStandardCd;
    }


    /**
     * Sets the LinerSizeCd property.
     *
     * @param pLinerSizeCd
     *  String to use to update the property.
     */
    public void setLinerSizeCd(String pLinerSizeCd){
        this.mLinerSizeCd = pLinerSizeCd;
    }
    /**
     * Retrieves the LinerSizeCd property.
     *
     * @return
     *  String containing the LinerSizeCd property.
     */
    public String getLinerSizeCd(){
        return mLinerSizeCd;
    }


    /**
     * Sets the ApplyFl property.
     *
     * @param pApplyFl
     *  String to use to update the property.
     */
    public void setApplyFl(String pApplyFl){
        this.mApplyFl = pApplyFl;
    }
    /**
     * Retrieves the ApplyFl property.
     *
     * @return
     *  String containing the ApplyFl property.
     */
    public String getApplyFl(){
        return mApplyFl;
    }


    /**
     * Sets the PoolQty property.
     *
     * @param pPoolQty
     *  int to use to update the property.
     */
    public void setPoolQty(int pPoolQty){
        this.mPoolQty = pPoolQty;
    }
    /**
     * Retrieves the PoolQty property.
     *
     * @return
     *  int containing the PoolQty property.
     */
    public int getPoolQty(){
        return mPoolQty;
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
