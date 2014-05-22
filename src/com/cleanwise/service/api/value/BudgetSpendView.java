
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        BudgetSpendView
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
import java.math.BigDecimal;
import org.w3c.dom.*;

/**
 * <code>BudgetSpendView</code> is a ViewObject class for UI.
 */
public class BudgetSpendView
extends ValueObject
{
   
    private static final long serialVersionUID = 7279215095886918105L;
    private int mBusEntityId;
    private String mBusEntityName;
    private String mCity;
    private String mState;
    private String mPostalCode;
    private String mBusEntityTypeCd;
    private int mCostCenterId;
    private String mCostCenterName;
    private int mBudgetYear;
    private int mBudgetPeriod;
    private int mCurrentBudgetPeriod;
    private int mCurrentBudgetYear;
    private String mCurrentBudgetPeriodStart;
    private String mCurrentBudgetPeriodEnd;
    private java.sql.Date mBudgetPeriodStartDate;
    private String mBudgetPeriodStart;
    private String mBudgetPeriodEnd;
    private BigDecimal mAmountSpent;
    private BigDecimal mAmountAllocated;
    private Integer mBudgetThresholdPercent;
    private String mBscName;
    private boolean mAllocateFreight;
    private String mCostCenterTaxType;
    private String mSiteReferenceNum;
    private boolean mHasGsfColumn;
    private String mGsfValue;
    private boolean mHasRankIndexColumn;
    private String mRankIndexValue;
    private String mSiteStatus;
    private String mExpDate;
    private int mNumberOfBudgetPeriods;
    private boolean mUnlimitedBudget;

    /**
     * Constructor.
     */
    public BudgetSpendView ()
    {
        mBusEntityName = "";
        mCity = "";
        mState = "";
        mPostalCode = "";
        mBusEntityTypeCd = "";
        mCostCenterName = "";
        mCurrentBudgetPeriodStart = "";
        mCurrentBudgetPeriodEnd = "";
        mBudgetPeriodStart = "";
        mBudgetPeriodEnd = "";
        mBscName = "";
        mCostCenterTaxType = "";
        mSiteReferenceNum = "";
        mGsfValue = "";
        mRankIndexValue = "";
        mSiteStatus = "";
        mExpDate = "";
    }

    /**
     * Constructor. 
     */
    public BudgetSpendView(int parm1, String parm2, String parm3, String parm4, String parm5, String parm6, int parm7, String parm8, int parm9, int parm10, int parm11, int parm12, String parm13, String parm14, java.sql.Date parm15, String parm16, String parm17, BigDecimal parm18, BigDecimal parm19, Integer parm20, String parm21, boolean parm22, String parm23, String parm24, boolean parm25, String parm26, boolean parm27, String parm28, String parm29, String parm30, int parm31, boolean parm32)
    {
        mBusEntityId = parm1;
        mBusEntityName = parm2;
        mCity = parm3;
        mState = parm4;
        mPostalCode = parm5;
        mBusEntityTypeCd = parm6;
        mCostCenterId = parm7;
        mCostCenterName = parm8;
        mBudgetYear = parm9;
        mBudgetPeriod = parm10;
        mCurrentBudgetPeriod = parm11;
        mCurrentBudgetYear = parm12;
        mCurrentBudgetPeriodStart = parm13;
        mCurrentBudgetPeriodEnd = parm14;
        mBudgetPeriodStartDate = parm15;
        mBudgetPeriodStart = parm16;
        mBudgetPeriodEnd = parm17;
        mAmountSpent = parm18;
        mAmountAllocated = parm19;
        mBudgetThresholdPercent = parm20;
        mBscName = parm21;
        mAllocateFreight = parm22;
        mCostCenterTaxType = parm23;
        mSiteReferenceNum = parm24;
        mHasGsfColumn = parm25;
        mGsfValue = parm26;
        mHasRankIndexColumn = parm27;
        mRankIndexValue = parm28;
        mSiteStatus = parm29;
        mExpDate = parm30;
        mNumberOfBudgetPeriods = parm31;
        mUnlimitedBudget = parm32;

    }

    /**
     * Creates a new BudgetSpendView
     *
     * @return
     *  Newly initialized BudgetSpendView object.
     */
    public static BudgetSpendView createValue () 
    {
        BudgetSpendView valueView = new BudgetSpendView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this BudgetSpendView object
     */
    public String toString()
    {
        return "[" + "BusEntityId=" + mBusEntityId + ", BusEntityName=" + mBusEntityName + ", City=" + mCity + ", State=" + mState + ", PostalCode=" + mPostalCode + ", BusEntityTypeCd=" + mBusEntityTypeCd + ", CostCenterId=" + mCostCenterId + ", CostCenterName=" + mCostCenterName + ", BudgetYear=" + mBudgetYear + ", BudgetPeriod=" + mBudgetPeriod + ", CurrentBudgetPeriod=" + mCurrentBudgetPeriod + ", CurrentBudgetYear=" + mCurrentBudgetYear + ", CurrentBudgetPeriodStart=" + mCurrentBudgetPeriodStart + ", CurrentBudgetPeriodEnd=" + mCurrentBudgetPeriodEnd + ", BudgetPeriodStartDate=" + mBudgetPeriodStartDate + ", BudgetPeriodStart=" + mBudgetPeriodStart + ", BudgetPeriodEnd=" + mBudgetPeriodEnd + ", AmountSpent=" + mAmountSpent + ", AmountAllocated=" + mAmountAllocated + ", BudgetThresholdPercent=" + mBudgetThresholdPercent + ", BscName=" + mBscName + ", AllocateFreight=" + mAllocateFreight + ", CostCenterTaxType=" + mCostCenterTaxType + ", SiteReferenceNum=" + mSiteReferenceNum + ", HasGsfColumn=" + mHasGsfColumn + ", GsfValue=" + mGsfValue + ", HasRankIndexColumn=" + mHasRankIndexColumn + ", RankIndexValue=" + mRankIndexValue + ", SiteStatus=" + mSiteStatus + ", ExpDate=" + mExpDate + ", NumberOfBudgetPeriods=" + mNumberOfBudgetPeriods + ", UnlimitedBudget=" + mUnlimitedBudget + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("BudgetSpend");
	root.setAttribute("Id", String.valueOf(mBusEntityId));

	Element node;

        node = doc.createElement("BusEntityName");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityName)));
        root.appendChild(node);

        node = doc.createElement("City");
        node.appendChild(doc.createTextNode(String.valueOf(mCity)));
        root.appendChild(node);

        node = doc.createElement("State");
        node.appendChild(doc.createTextNode(String.valueOf(mState)));
        root.appendChild(node);

        node = doc.createElement("PostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mPostalCode)));
        root.appendChild(node);

        node = doc.createElement("BusEntityTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityTypeCd)));
        root.appendChild(node);

        node = doc.createElement("CostCenterId");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterId)));
        root.appendChild(node);

        node = doc.createElement("CostCenterName");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterName)));
        root.appendChild(node);

        node = doc.createElement("BudgetYear");
        node.appendChild(doc.createTextNode(String.valueOf(mBudgetYear)));
        root.appendChild(node);

        node = doc.createElement("BudgetPeriod");
        node.appendChild(doc.createTextNode(String.valueOf(mBudgetPeriod)));
        root.appendChild(node);

        node = doc.createElement("CurrentBudgetPeriod");
        node.appendChild(doc.createTextNode(String.valueOf(mCurrentBudgetPeriod)));
        root.appendChild(node);

        node = doc.createElement("CurrentBudgetYear");
        node.appendChild(doc.createTextNode(String.valueOf(mCurrentBudgetYear)));
        root.appendChild(node);

        node = doc.createElement("CurrentBudgetPeriodStart");
        node.appendChild(doc.createTextNode(String.valueOf(mCurrentBudgetPeriodStart)));
        root.appendChild(node);

        node = doc.createElement("CurrentBudgetPeriodEnd");
        node.appendChild(doc.createTextNode(String.valueOf(mCurrentBudgetPeriodEnd)));
        root.appendChild(node);

        node = doc.createElement("BudgetPeriodStartDate");
        node.appendChild(doc.createTextNode(String.valueOf(mBudgetPeriodStartDate)));
        root.appendChild(node);

        node = doc.createElement("BudgetPeriodStart");
        node.appendChild(doc.createTextNode(String.valueOf(mBudgetPeriodStart)));
        root.appendChild(node);

        node = doc.createElement("BudgetPeriodEnd");
        node.appendChild(doc.createTextNode(String.valueOf(mBudgetPeriodEnd)));
        root.appendChild(node);

        node = doc.createElement("AmountSpent");
        node.appendChild(doc.createTextNode(String.valueOf(mAmountSpent)));
        root.appendChild(node);

        node = doc.createElement("AmountAllocated");
        node.appendChild(doc.createTextNode(String.valueOf(mAmountAllocated)));
        root.appendChild(node);

        node = doc.createElement("BudgetThresholdPercent");
        node.appendChild(doc.createTextNode(String.valueOf(mBudgetThresholdPercent)));
        root.appendChild(node);

        node = doc.createElement("BscName");
        node.appendChild(doc.createTextNode(String.valueOf(mBscName)));
        root.appendChild(node);

        node = doc.createElement("AllocateFreight");
        node.appendChild(doc.createTextNode(String.valueOf(mAllocateFreight)));
        root.appendChild(node);

        node = doc.createElement("CostCenterTaxType");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenterTaxType)));
        root.appendChild(node);

        node = doc.createElement("SiteReferenceNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteReferenceNum)));
        root.appendChild(node);

        node = doc.createElement("HasGsfColumn");
        node.appendChild(doc.createTextNode(String.valueOf(mHasGsfColumn)));
        root.appendChild(node);

        node = doc.createElement("GsfValue");
        node.appendChild(doc.createTextNode(String.valueOf(mGsfValue)));
        root.appendChild(node);

        node = doc.createElement("HasRankIndexColumn");
        node.appendChild(doc.createTextNode(String.valueOf(mHasRankIndexColumn)));
        root.appendChild(node);

        node = doc.createElement("RankIndexValue");
        node.appendChild(doc.createTextNode(String.valueOf(mRankIndexValue)));
        root.appendChild(node);

        node = doc.createElement("SiteStatus");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteStatus)));
        root.appendChild(node);

        node = doc.createElement("ExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mExpDate)));
        root.appendChild(node);

        node = doc.createElement("NumberOfBudgetPeriods");
        node.appendChild(doc.createTextNode(String.valueOf(mNumberOfBudgetPeriods)));
        root.appendChild(node);

        node = doc.createElement("UnlimitedBudget");
        node.appendChild(doc.createTextNode(String.valueOf(mUnlimitedBudget)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public BudgetSpendView copy()  {
      BudgetSpendView obj = new BudgetSpendView();
      obj.setBusEntityId(mBusEntityId);
      obj.setBusEntityName(mBusEntityName);
      obj.setCity(mCity);
      obj.setState(mState);
      obj.setPostalCode(mPostalCode);
      obj.setBusEntityTypeCd(mBusEntityTypeCd);
      obj.setCostCenterId(mCostCenterId);
      obj.setCostCenterName(mCostCenterName);
      obj.setBudgetYear(mBudgetYear);
      obj.setBudgetPeriod(mBudgetPeriod);
      obj.setCurrentBudgetPeriod(mCurrentBudgetPeriod);
      obj.setCurrentBudgetYear(mCurrentBudgetYear);
      obj.setCurrentBudgetPeriodStart(mCurrentBudgetPeriodStart);
      obj.setCurrentBudgetPeriodEnd(mCurrentBudgetPeriodEnd);
      obj.setBudgetPeriodStartDate(mBudgetPeriodStartDate);
      obj.setBudgetPeriodStart(mBudgetPeriodStart);
      obj.setBudgetPeriodEnd(mBudgetPeriodEnd);
      obj.setAmountSpent(mAmountSpent);
      obj.setAmountAllocated(mAmountAllocated);
      obj.setBudgetThresholdPercent(mBudgetThresholdPercent);
      obj.setBscName(mBscName);
      obj.setAllocateFreight(mAllocateFreight);
      obj.setCostCenterTaxType(mCostCenterTaxType);
      obj.setSiteReferenceNum(mSiteReferenceNum);
      obj.setHasGsfColumn(mHasGsfColumn);
      obj.setGsfValue(mGsfValue);
      obj.setHasRankIndexColumn(mHasRankIndexColumn);
      obj.setRankIndexValue(mRankIndexValue);
      obj.setSiteStatus(mSiteStatus);
      obj.setExpDate(mExpDate);
      obj.setNumberOfBudgetPeriods(mNumberOfBudgetPeriods);
      obj.setUnlimitedBudget(mUnlimitedBudget);

      
      return obj;
    }

    
    /**
     * Sets the BusEntityId property.
     *
     * @param pBusEntityId
     *  int to use to update the property.
     */
    public void setBusEntityId(int pBusEntityId){
        this.mBusEntityId = pBusEntityId;
    }
    /**
     * Retrieves the BusEntityId property.
     *
     * @return
     *  int containing the BusEntityId property.
     */
    public int getBusEntityId(){
        return mBusEntityId;
    }


    /**
     * Sets the BusEntityName property.
     *
     * @param pBusEntityName
     *  String to use to update the property.
     */
    public void setBusEntityName(String pBusEntityName){
        this.mBusEntityName = pBusEntityName;
    }
    /**
     * Retrieves the BusEntityName property.
     *
     * @return
     *  String containing the BusEntityName property.
     */
    public String getBusEntityName(){
        return mBusEntityName;
    }


    /**
     * Sets the City property.
     *
     * @param pCity
     *  String to use to update the property.
     */
    public void setCity(String pCity){
        this.mCity = pCity;
    }
    /**
     * Retrieves the City property.
     *
     * @return
     *  String containing the City property.
     */
    public String getCity(){
        return mCity;
    }


    /**
     * Sets the State property.
     *
     * @param pState
     *  String to use to update the property.
     */
    public void setState(String pState){
        this.mState = pState;
    }
    /**
     * Retrieves the State property.
     *
     * @return
     *  String containing the State property.
     */
    public String getState(){
        return mState;
    }


    /**
     * Sets the PostalCode property.
     *
     * @param pPostalCode
     *  String to use to update the property.
     */
    public void setPostalCode(String pPostalCode){
        this.mPostalCode = pPostalCode;
    }
    /**
     * Retrieves the PostalCode property.
     *
     * @return
     *  String containing the PostalCode property.
     */
    public String getPostalCode(){
        return mPostalCode;
    }


    /**
     * Sets the BusEntityTypeCd property.
     *
     * @param pBusEntityTypeCd
     *  String to use to update the property.
     */
    public void setBusEntityTypeCd(String pBusEntityTypeCd){
        this.mBusEntityTypeCd = pBusEntityTypeCd;
    }
    /**
     * Retrieves the BusEntityTypeCd property.
     *
     * @return
     *  String containing the BusEntityTypeCd property.
     */
    public String getBusEntityTypeCd(){
        return mBusEntityTypeCd;
    }


    /**
     * Sets the CostCenterId property.
     *
     * @param pCostCenterId
     *  int to use to update the property.
     */
    public void setCostCenterId(int pCostCenterId){
        this.mCostCenterId = pCostCenterId;
    }
    /**
     * Retrieves the CostCenterId property.
     *
     * @return
     *  int containing the CostCenterId property.
     */
    public int getCostCenterId(){
        return mCostCenterId;
    }


    /**
     * Sets the CostCenterName property.
     *
     * @param pCostCenterName
     *  String to use to update the property.
     */
    public void setCostCenterName(String pCostCenterName){
        this.mCostCenterName = pCostCenterName;
    }
    /**
     * Retrieves the CostCenterName property.
     *
     * @return
     *  String containing the CostCenterName property.
     */
    public String getCostCenterName(){
        return mCostCenterName;
    }


    /**
     * Sets the BudgetYear property.
     *
     * @param pBudgetYear
     *  int to use to update the property.
     */
    public void setBudgetYear(int pBudgetYear){
        this.mBudgetYear = pBudgetYear;
    }
    /**
     * Retrieves the BudgetYear property.
     *
     * @return
     *  int containing the BudgetYear property.
     */
    public int getBudgetYear(){
        return mBudgetYear;
    }


    /**
     * Sets the BudgetPeriod property.
     *
     * @param pBudgetPeriod
     *  int to use to update the property.
     */
    public void setBudgetPeriod(int pBudgetPeriod){
        this.mBudgetPeriod = pBudgetPeriod;
    }
    /**
     * Retrieves the BudgetPeriod property.
     *
     * @return
     *  int containing the BudgetPeriod property.
     */
    public int getBudgetPeriod(){
        return mBudgetPeriod;
    }


    /**
     * Sets the CurrentBudgetPeriod property.
     *
     * @param pCurrentBudgetPeriod
     *  int to use to update the property.
     */
    public void setCurrentBudgetPeriod(int pCurrentBudgetPeriod){
        this.mCurrentBudgetPeriod = pCurrentBudgetPeriod;
    }
    /**
     * Retrieves the CurrentBudgetPeriod property.
     *
     * @return
     *  int containing the CurrentBudgetPeriod property.
     */
    public int getCurrentBudgetPeriod(){
        return mCurrentBudgetPeriod;
    }


    /**
     * Sets the CurrentBudgetYear property.
     *
     * @param pCurrentBudgetYear
     *  int to use to update the property.
     */
    public void setCurrentBudgetYear(int pCurrentBudgetYear){
        this.mCurrentBudgetYear = pCurrentBudgetYear;
    }
    /**
     * Retrieves the CurrentBudgetYear property.
     *
     * @return
     *  int containing the CurrentBudgetYear property.
     */
    public int getCurrentBudgetYear(){
        return mCurrentBudgetYear;
    }


    /**
     * Sets the CurrentBudgetPeriodStart property.
     *
     * @param pCurrentBudgetPeriodStart
     *  String to use to update the property.
     */
    public void setCurrentBudgetPeriodStart(String pCurrentBudgetPeriodStart){
        this.mCurrentBudgetPeriodStart = pCurrentBudgetPeriodStart;
    }
    /**
     * Retrieves the CurrentBudgetPeriodStart property.
     *
     * @return
     *  String containing the CurrentBudgetPeriodStart property.
     */
    public String getCurrentBudgetPeriodStart(){
        return mCurrentBudgetPeriodStart;
    }


    /**
     * Sets the CurrentBudgetPeriodEnd property.
     *
     * @param pCurrentBudgetPeriodEnd
     *  String to use to update the property.
     */
    public void setCurrentBudgetPeriodEnd(String pCurrentBudgetPeriodEnd){
        this.mCurrentBudgetPeriodEnd = pCurrentBudgetPeriodEnd;
    }
    /**
     * Retrieves the CurrentBudgetPeriodEnd property.
     *
     * @return
     *  String containing the CurrentBudgetPeriodEnd property.
     */
    public String getCurrentBudgetPeriodEnd(){
        return mCurrentBudgetPeriodEnd;
    }


    /**
     * Sets the BudgetPeriodStartDate property.
     *
     * @param pBudgetPeriodStartDate
     *  java.sql.Date to use to update the property.
     */
    public void setBudgetPeriodStartDate(java.sql.Date pBudgetPeriodStartDate){
        this.mBudgetPeriodStartDate = pBudgetPeriodStartDate;
    }
    /**
     * Retrieves the BudgetPeriodStartDate property.
     *
     * @return
     *  java.sql.Date containing the BudgetPeriodStartDate property.
     */
    public java.sql.Date getBudgetPeriodStartDate(){
        return mBudgetPeriodStartDate;
    }


    /**
     * Sets the BudgetPeriodStart property.
     *
     * @param pBudgetPeriodStart
     *  String to use to update the property.
     */
    public void setBudgetPeriodStart(String pBudgetPeriodStart){
        this.mBudgetPeriodStart = pBudgetPeriodStart;
    }
    /**
     * Retrieves the BudgetPeriodStart property.
     *
     * @return
     *  String containing the BudgetPeriodStart property.
     */
    public String getBudgetPeriodStart(){
        return mBudgetPeriodStart;
    }


    /**
     * Sets the BudgetPeriodEnd property.
     *
     * @param pBudgetPeriodEnd
     *  String to use to update the property.
     */
    public void setBudgetPeriodEnd(String pBudgetPeriodEnd){
        this.mBudgetPeriodEnd = pBudgetPeriodEnd;
    }
    /**
     * Retrieves the BudgetPeriodEnd property.
     *
     * @return
     *  String containing the BudgetPeriodEnd property.
     */
    public String getBudgetPeriodEnd(){
        return mBudgetPeriodEnd;
    }


    /**
     * Sets the AmountSpent property.
     *
     * @param pAmountSpent
     *  BigDecimal to use to update the property.
     */
    public void setAmountSpent(BigDecimal pAmountSpent){
        this.mAmountSpent = pAmountSpent;
    }
    /**
     * Retrieves the AmountSpent property.
     *
     * @return
     *  BigDecimal containing the AmountSpent property.
     */
    public BigDecimal getAmountSpent(){
        return mAmountSpent;
    }


    /**
     * Sets the AmountAllocated property.
     *
     * @param pAmountAllocated
     *  BigDecimal to use to update the property.
     */
    public void setAmountAllocated(BigDecimal pAmountAllocated){
        this.mAmountAllocated = pAmountAllocated;
    }
    /**
     * Retrieves the AmountAllocated property.
     *
     * @return
     *  BigDecimal containing the AmountAllocated property.
     */
    public BigDecimal getAmountAllocated(){
        return mAmountAllocated;
    }


    /**
     * Sets the BudgetThresholdPercent property.
     *
     * @param pBudgetThresholdPercent
     *  Integer to use to update the property.
     */
    public void setBudgetThresholdPercent(Integer pBudgetThresholdPercent){
        this.mBudgetThresholdPercent = pBudgetThresholdPercent;
    }
    /**
     * Retrieves the BudgetThresholdPercent property.
     *
     * @return
     *  Integer containing the BudgetThresholdPercent property.
     */
    public Integer getBudgetThresholdPercent(){
        return mBudgetThresholdPercent;
    }


    /**
     * Sets the BscName property.
     *
     * @param pBscName
     *  String to use to update the property.
     */
    public void setBscName(String pBscName){
        this.mBscName = pBscName;
    }
    /**
     * Retrieves the BscName property.
     *
     * @return
     *  String containing the BscName property.
     */
    public String getBscName(){
        return mBscName;
    }


    /**
     * Sets the AllocateFreight property.
     *
     * @param pAllocateFreight
     *  boolean to use to update the property.
     */
    public void setAllocateFreight(boolean pAllocateFreight){
        this.mAllocateFreight = pAllocateFreight;
    }
    /**
     * Retrieves the AllocateFreight property.
     *
     * @return
     *  boolean containing the AllocateFreight property.
     */
    public boolean getAllocateFreight(){
        return mAllocateFreight;
    }


    /**
     * Sets the CostCenterTaxType property.
     *
     * @param pCostCenterTaxType
     *  String to use to update the property.
     */
    public void setCostCenterTaxType(String pCostCenterTaxType){
        this.mCostCenterTaxType = pCostCenterTaxType;
    }
    /**
     * Retrieves the CostCenterTaxType property.
     *
     * @return
     *  String containing the CostCenterTaxType property.
     */
    public String getCostCenterTaxType(){
        return mCostCenterTaxType;
    }


    /**
     * Sets the SiteReferenceNum property.
     *
     * @param pSiteReferenceNum
     *  String to use to update the property.
     */
    public void setSiteReferenceNum(String pSiteReferenceNum){
        this.mSiteReferenceNum = pSiteReferenceNum;
    }
    /**
     * Retrieves the SiteReferenceNum property.
     *
     * @return
     *  String containing the SiteReferenceNum property.
     */
    public String getSiteReferenceNum(){
        return mSiteReferenceNum;
    }


    /**
     * Sets the HasGsfColumn property.
     *
     * @param pHasGsfColumn
     *  boolean to use to update the property.
     */
    public void setHasGsfColumn(boolean pHasGsfColumn){
        this.mHasGsfColumn = pHasGsfColumn;
    }
    /**
     * Retrieves the HasGsfColumn property.
     *
     * @return
     *  boolean containing the HasGsfColumn property.
     */
    public boolean getHasGsfColumn(){
        return mHasGsfColumn;
    }


    /**
     * Sets the GsfValue property.
     *
     * @param pGsfValue
     *  String to use to update the property.
     */
    public void setGsfValue(String pGsfValue){
        this.mGsfValue = pGsfValue;
    }
    /**
     * Retrieves the GsfValue property.
     *
     * @return
     *  String containing the GsfValue property.
     */
    public String getGsfValue(){
        return mGsfValue;
    }


    /**
     * Sets the HasRankIndexColumn property.
     *
     * @param pHasRankIndexColumn
     *  boolean to use to update the property.
     */
    public void setHasRankIndexColumn(boolean pHasRankIndexColumn){
        this.mHasRankIndexColumn = pHasRankIndexColumn;
    }
    /**
     * Retrieves the HasRankIndexColumn property.
     *
     * @return
     *  boolean containing the HasRankIndexColumn property.
     */
    public boolean getHasRankIndexColumn(){
        return mHasRankIndexColumn;
    }


    /**
     * Sets the RankIndexValue property.
     *
     * @param pRankIndexValue
     *  String to use to update the property.
     */
    public void setRankIndexValue(String pRankIndexValue){
        this.mRankIndexValue = pRankIndexValue;
    }
    /**
     * Retrieves the RankIndexValue property.
     *
     * @return
     *  String containing the RankIndexValue property.
     */
    public String getRankIndexValue(){
        return mRankIndexValue;
    }


    /**
     * Sets the SiteStatus property.
     *
     * @param pSiteStatus
     *  String to use to update the property.
     */
    public void setSiteStatus(String pSiteStatus){
        this.mSiteStatus = pSiteStatus;
    }
    /**
     * Retrieves the SiteStatus property.
     *
     * @return
     *  String containing the SiteStatus property.
     */
    public String getSiteStatus(){
        return mSiteStatus;
    }


    /**
     * Sets the ExpDate property.
     *
     * @param pExpDate
     *  String to use to update the property.
     */
    public void setExpDate(String pExpDate){
        this.mExpDate = pExpDate;
    }
    /**
     * Retrieves the ExpDate property.
     *
     * @return
     *  String containing the ExpDate property.
     */
    public String getExpDate(){
        return mExpDate;
    }


    /**
     * Sets the NumberOfBudgetPeriods property.
     *
     * @param pNumberOfBudgetPeriods
     *  int to use to update the property.
     */
    public void setNumberOfBudgetPeriods(int pNumberOfBudgetPeriods){
        this.mNumberOfBudgetPeriods = pNumberOfBudgetPeriods;
    }
    /**
     * Retrieves the NumberOfBudgetPeriods property.
     *
     * @return
     *  int containing the NumberOfBudgetPeriods property.
     */
    public int getNumberOfBudgetPeriods(){
        return mNumberOfBudgetPeriods;
    }

         /**
          * Sets the UnlimitedBudget property.
          *
          * @param pUnlimitedBudget
          *  boolean to use to update the property.
          */
         public void setUnlimitedBudget(boolean pUnlimitedBudget){
             this.mUnlimitedBudget = pUnlimitedBudget;
         }
         /**
          * Retrieves the UnlimitedBudget property.
          *
          * @return
          *  boolean containing the UnlimitedBudget property.
          */
         public boolean getUnlimitedBudget(){
             return mUnlimitedBudget;
         }
     

    
}
