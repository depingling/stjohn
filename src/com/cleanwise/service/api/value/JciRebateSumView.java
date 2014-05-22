
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        JciRebateSumView
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
 * <code>JciRebateSumView</code> is a ViewObject class for UI.
 */
public class JciRebateSumView
extends ValueObject
{
   
    private static final long serialVersionUID = -5085081889473332883L;
    private int mGroupId;
    private String mAccountErpNum;
    private int mAccountId;
    private String mAccountName;
    private int mDistributorId;
    private String mSku;
    private int mItemId;
    private String mItemDesc;
    private int mManufId;
    private String mManufDesc;
    private String mManufErp;
    private BigDecimal mMinCustomerPrice;
    private BigDecimal mMaxCustomerPrice;
    private BigDecimal mAvgCustomerPrice;
    private BigDecimal mTotalBillPrice;
    private BigDecimal mTotalCreditPrice;
    private BigDecimal mTotalCustomerPrice;
    private BigDecimal mMinDistCost;
    private BigDecimal mMaxDistCost;
    private BigDecimal mAvgDistCost;
    private BigDecimal mTotalBillCost;
    private BigDecimal mTotalCreditCost;
    private BigDecimal mTotalDistCost;
    private BigDecimal mBaseCost;
    private BigDecimal mBaseResultCost;
    private BigDecimal mRebatePercent;
    private int mBillQty;
    private int mCreditQty;
    private int mQty;
    private BigDecimal mRebateBillBase;
    private BigDecimal mRebateCreditBase;
    private BigDecimal mRebateBase;
    private BigDecimal mBillRebate;
    private BigDecimal mCreditRebate;
    private BigDecimal mRebate;
    private String mCategory;
    private int mCategoryId;
    private String mMajorCategory;
    private Date mMinOrderDate;
    private Date mMaxOrderDate;
    private Date mBaseCostEffDate;
    private Date mBaseCostExpDate;

    /**
     * Constructor.
     */
    public JciRebateSumView ()
    {
        mAccountErpNum = "";
        mAccountName = "";
        mSku = "";
        mItemDesc = "";
        mManufDesc = "";
        mManufErp = "";
        mCategory = "";
        mMajorCategory = "";
    }

    /**
     * Constructor. 
     */
    public JciRebateSumView(int parm1, String parm2, int parm3, String parm4, int parm5, String parm6, int parm7, String parm8, int parm9, String parm10, String parm11, BigDecimal parm12, BigDecimal parm13, BigDecimal parm14, BigDecimal parm15, BigDecimal parm16, BigDecimal parm17, BigDecimal parm18, BigDecimal parm19, BigDecimal parm20, BigDecimal parm21, BigDecimal parm22, BigDecimal parm23, BigDecimal parm24, BigDecimal parm25, BigDecimal parm26, int parm27, int parm28, int parm29, BigDecimal parm30, BigDecimal parm31, BigDecimal parm32, BigDecimal parm33, BigDecimal parm34, BigDecimal parm35, String parm36, int parm37, String parm38, Date parm39, Date parm40, Date parm41, Date parm42)
    {
        mGroupId = parm1;
        mAccountErpNum = parm2;
        mAccountId = parm3;
        mAccountName = parm4;
        mDistributorId = parm5;
        mSku = parm6;
        mItemId = parm7;
        mItemDesc = parm8;
        mManufId = parm9;
        mManufDesc = parm10;
        mManufErp = parm11;
        mMinCustomerPrice = parm12;
        mMaxCustomerPrice = parm13;
        mAvgCustomerPrice = parm14;
        mTotalBillPrice = parm15;
        mTotalCreditPrice = parm16;
        mTotalCustomerPrice = parm17;
        mMinDistCost = parm18;
        mMaxDistCost = parm19;
        mAvgDistCost = parm20;
        mTotalBillCost = parm21;
        mTotalCreditCost = parm22;
        mTotalDistCost = parm23;
        mBaseCost = parm24;
        mBaseResultCost = parm25;
        mRebatePercent = parm26;
        mBillQty = parm27;
        mCreditQty = parm28;
        mQty = parm29;
        mRebateBillBase = parm30;
        mRebateCreditBase = parm31;
        mRebateBase = parm32;
        mBillRebate = parm33;
        mCreditRebate = parm34;
        mRebate = parm35;
        mCategory = parm36;
        mCategoryId = parm37;
        mMajorCategory = parm38;
        mMinOrderDate = parm39;
        mMaxOrderDate = parm40;
        mBaseCostEffDate = parm41;
        mBaseCostExpDate = parm42;
        
    }

    /**
     * Creates a new JciRebateSumView
     *
     * @return
     *  Newly initialized JciRebateSumView object.
     */
    public static JciRebateSumView createValue () 
    {
        JciRebateSumView valueView = new JciRebateSumView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this JciRebateSumView object
     */
    public String toString()
    {
        return "[" + "GroupId=" + mGroupId + ", AccountErpNum=" + mAccountErpNum + ", AccountId=" + mAccountId + ", AccountName=" + mAccountName + ", DistributorId=" + mDistributorId + ", Sku=" + mSku + ", ItemId=" + mItemId + ", ItemDesc=" + mItemDesc + ", ManufId=" + mManufId + ", ManufDesc=" + mManufDesc + ", ManufErp=" + mManufErp + ", MinCustomerPrice=" + mMinCustomerPrice + ", MaxCustomerPrice=" + mMaxCustomerPrice + ", AvgCustomerPrice=" + mAvgCustomerPrice + ", TotalBillPrice=" + mTotalBillPrice + ", TotalCreditPrice=" + mTotalCreditPrice + ", TotalCustomerPrice=" + mTotalCustomerPrice + ", MinDistCost=" + mMinDistCost + ", MaxDistCost=" + mMaxDistCost + ", AvgDistCost=" + mAvgDistCost + ", TotalBillCost=" + mTotalBillCost + ", TotalCreditCost=" + mTotalCreditCost + ", TotalDistCost=" + mTotalDistCost + ", BaseCost=" + mBaseCost + ", BaseResultCost=" + mBaseResultCost + ", RebatePercent=" + mRebatePercent + ", BillQty=" + mBillQty + ", CreditQty=" + mCreditQty + ", Qty=" + mQty + ", RebateBillBase=" + mRebateBillBase + ", RebateCreditBase=" + mRebateCreditBase + ", RebateBase=" + mRebateBase + ", BillRebate=" + mBillRebate + ", CreditRebate=" + mCreditRebate + ", Rebate=" + mRebate + ", Category=" + mCategory + ", CategoryId=" + mCategoryId + ", MajorCategory=" + mMajorCategory + ", MinOrderDate=" + mMinOrderDate + ", MaxOrderDate=" + mMaxOrderDate + ", BaseCostEffDate=" + mBaseCostEffDate + ", BaseCostExpDate=" + mBaseCostExpDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("JciRebateSum");
	root.setAttribute("Id", String.valueOf(mGroupId));

	Element node;

        node = doc.createElement("AccountErpNum");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountErpNum)));
        root.appendChild(node);

        node = doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node = doc.createElement("AccountName");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountName)));
        root.appendChild(node);

        node = doc.createElement("DistributorId");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorId)));
        root.appendChild(node);

        node = doc.createElement("Sku");
        node.appendChild(doc.createTextNode(String.valueOf(mSku)));
        root.appendChild(node);

        node = doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node = doc.createElement("ItemDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mItemDesc)));
        root.appendChild(node);

        node = doc.createElement("ManufId");
        node.appendChild(doc.createTextNode(String.valueOf(mManufId)));
        root.appendChild(node);

        node = doc.createElement("ManufDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mManufDesc)));
        root.appendChild(node);

        node = doc.createElement("ManufErp");
        node.appendChild(doc.createTextNode(String.valueOf(mManufErp)));
        root.appendChild(node);

        node = doc.createElement("MinCustomerPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mMinCustomerPrice)));
        root.appendChild(node);

        node = doc.createElement("MaxCustomerPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mMaxCustomerPrice)));
        root.appendChild(node);

        node = doc.createElement("AvgCustomerPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mAvgCustomerPrice)));
        root.appendChild(node);

        node = doc.createElement("TotalBillPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalBillPrice)));
        root.appendChild(node);

        node = doc.createElement("TotalCreditPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalCreditPrice)));
        root.appendChild(node);

        node = doc.createElement("TotalCustomerPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalCustomerPrice)));
        root.appendChild(node);

        node = doc.createElement("MinDistCost");
        node.appendChild(doc.createTextNode(String.valueOf(mMinDistCost)));
        root.appendChild(node);

        node = doc.createElement("MaxDistCost");
        node.appendChild(doc.createTextNode(String.valueOf(mMaxDistCost)));
        root.appendChild(node);

        node = doc.createElement("AvgDistCost");
        node.appendChild(doc.createTextNode(String.valueOf(mAvgDistCost)));
        root.appendChild(node);

        node = doc.createElement("TotalBillCost");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalBillCost)));
        root.appendChild(node);

        node = doc.createElement("TotalCreditCost");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalCreditCost)));
        root.appendChild(node);

        node = doc.createElement("TotalDistCost");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalDistCost)));
        root.appendChild(node);

        node = doc.createElement("BaseCost");
        node.appendChild(doc.createTextNode(String.valueOf(mBaseCost)));
        root.appendChild(node);

        node = doc.createElement("BaseResultCost");
        node.appendChild(doc.createTextNode(String.valueOf(mBaseResultCost)));
        root.appendChild(node);

        node = doc.createElement("RebatePercent");
        node.appendChild(doc.createTextNode(String.valueOf(mRebatePercent)));
        root.appendChild(node);

        node = doc.createElement("BillQty");
        node.appendChild(doc.createTextNode(String.valueOf(mBillQty)));
        root.appendChild(node);

        node = doc.createElement("CreditQty");
        node.appendChild(doc.createTextNode(String.valueOf(mCreditQty)));
        root.appendChild(node);

        node = doc.createElement("Qty");
        node.appendChild(doc.createTextNode(String.valueOf(mQty)));
        root.appendChild(node);

        node = doc.createElement("RebateBillBase");
        node.appendChild(doc.createTextNode(String.valueOf(mRebateBillBase)));
        root.appendChild(node);

        node = doc.createElement("RebateCreditBase");
        node.appendChild(doc.createTextNode(String.valueOf(mRebateCreditBase)));
        root.appendChild(node);

        node = doc.createElement("RebateBase");
        node.appendChild(doc.createTextNode(String.valueOf(mRebateBase)));
        root.appendChild(node);

        node = doc.createElement("BillRebate");
        node.appendChild(doc.createTextNode(String.valueOf(mBillRebate)));
        root.appendChild(node);

        node = doc.createElement("CreditRebate");
        node.appendChild(doc.createTextNode(String.valueOf(mCreditRebate)));
        root.appendChild(node);

        node = doc.createElement("Rebate");
        node.appendChild(doc.createTextNode(String.valueOf(mRebate)));
        root.appendChild(node);

        node = doc.createElement("Category");
        node.appendChild(doc.createTextNode(String.valueOf(mCategory)));
        root.appendChild(node);

        node = doc.createElement("CategoryId");
        node.appendChild(doc.createTextNode(String.valueOf(mCategoryId)));
        root.appendChild(node);

        node = doc.createElement("MajorCategory");
        node.appendChild(doc.createTextNode(String.valueOf(mMajorCategory)));
        root.appendChild(node);

        node = doc.createElement("MinOrderDate");
        node.appendChild(doc.createTextNode(String.valueOf(mMinOrderDate)));
        root.appendChild(node);

        node = doc.createElement("MaxOrderDate");
        node.appendChild(doc.createTextNode(String.valueOf(mMaxOrderDate)));
        root.appendChild(node);

        node = doc.createElement("BaseCostEffDate");
        node.appendChild(doc.createTextNode(String.valueOf(mBaseCostEffDate)));
        root.appendChild(node);

        node = doc.createElement("BaseCostExpDate");
        node.appendChild(doc.createTextNode(String.valueOf(mBaseCostExpDate)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public JciRebateSumView copy()  {
      JciRebateSumView obj = new JciRebateSumView();
      obj.setGroupId(mGroupId);
      obj.setAccountErpNum(mAccountErpNum);
      obj.setAccountId(mAccountId);
      obj.setAccountName(mAccountName);
      obj.setDistributorId(mDistributorId);
      obj.setSku(mSku);
      obj.setItemId(mItemId);
      obj.setItemDesc(mItemDesc);
      obj.setManufId(mManufId);
      obj.setManufDesc(mManufDesc);
      obj.setManufErp(mManufErp);
      obj.setMinCustomerPrice(mMinCustomerPrice);
      obj.setMaxCustomerPrice(mMaxCustomerPrice);
      obj.setAvgCustomerPrice(mAvgCustomerPrice);
      obj.setTotalBillPrice(mTotalBillPrice);
      obj.setTotalCreditPrice(mTotalCreditPrice);
      obj.setTotalCustomerPrice(mTotalCustomerPrice);
      obj.setMinDistCost(mMinDistCost);
      obj.setMaxDistCost(mMaxDistCost);
      obj.setAvgDistCost(mAvgDistCost);
      obj.setTotalBillCost(mTotalBillCost);
      obj.setTotalCreditCost(mTotalCreditCost);
      obj.setTotalDistCost(mTotalDistCost);
      obj.setBaseCost(mBaseCost);
      obj.setBaseResultCost(mBaseResultCost);
      obj.setRebatePercent(mRebatePercent);
      obj.setBillQty(mBillQty);
      obj.setCreditQty(mCreditQty);
      obj.setQty(mQty);
      obj.setRebateBillBase(mRebateBillBase);
      obj.setRebateCreditBase(mRebateCreditBase);
      obj.setRebateBase(mRebateBase);
      obj.setBillRebate(mBillRebate);
      obj.setCreditRebate(mCreditRebate);
      obj.setRebate(mRebate);
      obj.setCategory(mCategory);
      obj.setCategoryId(mCategoryId);
      obj.setMajorCategory(mMajorCategory);
      obj.setMinOrderDate(mMinOrderDate);
      obj.setMaxOrderDate(mMaxOrderDate);
      obj.setBaseCostEffDate(mBaseCostEffDate);
      obj.setBaseCostExpDate(mBaseCostExpDate);
      
      return obj;
    }

    
    /**
     * Sets the GroupId property.
     *
     * @param pGroupId
     *  int to use to update the property.
     */
    public void setGroupId(int pGroupId){
        this.mGroupId = pGroupId;
    }
    /**
     * Retrieves the GroupId property.
     *
     * @return
     *  int containing the GroupId property.
     */
    public int getGroupId(){
        return mGroupId;
    }


    /**
     * Sets the AccountErpNum property.
     *
     * @param pAccountErpNum
     *  String to use to update the property.
     */
    public void setAccountErpNum(String pAccountErpNum){
        this.mAccountErpNum = pAccountErpNum;
    }
    /**
     * Retrieves the AccountErpNum property.
     *
     * @return
     *  String containing the AccountErpNum property.
     */
    public String getAccountErpNum(){
        return mAccountErpNum;
    }


    /**
     * Sets the AccountId property.
     *
     * @param pAccountId
     *  int to use to update the property.
     */
    public void setAccountId(int pAccountId){
        this.mAccountId = pAccountId;
    }
    /**
     * Retrieves the AccountId property.
     *
     * @return
     *  int containing the AccountId property.
     */
    public int getAccountId(){
        return mAccountId;
    }


    /**
     * Sets the AccountName property.
     *
     * @param pAccountName
     *  String to use to update the property.
     */
    public void setAccountName(String pAccountName){
        this.mAccountName = pAccountName;
    }
    /**
     * Retrieves the AccountName property.
     *
     * @return
     *  String containing the AccountName property.
     */
    public String getAccountName(){
        return mAccountName;
    }


    /**
     * Sets the DistributorId property.
     *
     * @param pDistributorId
     *  int to use to update the property.
     */
    public void setDistributorId(int pDistributorId){
        this.mDistributorId = pDistributorId;
    }
    /**
     * Retrieves the DistributorId property.
     *
     * @return
     *  int containing the DistributorId property.
     */
    public int getDistributorId(){
        return mDistributorId;
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
     * Sets the ManufDesc property.
     *
     * @param pManufDesc
     *  String to use to update the property.
     */
    public void setManufDesc(String pManufDesc){
        this.mManufDesc = pManufDesc;
    }
    /**
     * Retrieves the ManufDesc property.
     *
     * @return
     *  String containing the ManufDesc property.
     */
    public String getManufDesc(){
        return mManufDesc;
    }


    /**
     * Sets the ManufErp property.
     *
     * @param pManufErp
     *  String to use to update the property.
     */
    public void setManufErp(String pManufErp){
        this.mManufErp = pManufErp;
    }
    /**
     * Retrieves the ManufErp property.
     *
     * @return
     *  String containing the ManufErp property.
     */
    public String getManufErp(){
        return mManufErp;
    }


    /**
     * Sets the MinCustomerPrice property.
     *
     * @param pMinCustomerPrice
     *  BigDecimal to use to update the property.
     */
    public void setMinCustomerPrice(BigDecimal pMinCustomerPrice){
        this.mMinCustomerPrice = pMinCustomerPrice;
    }
    /**
     * Retrieves the MinCustomerPrice property.
     *
     * @return
     *  BigDecimal containing the MinCustomerPrice property.
     */
    public BigDecimal getMinCustomerPrice(){
        return mMinCustomerPrice;
    }


    /**
     * Sets the MaxCustomerPrice property.
     *
     * @param pMaxCustomerPrice
     *  BigDecimal to use to update the property.
     */
    public void setMaxCustomerPrice(BigDecimal pMaxCustomerPrice){
        this.mMaxCustomerPrice = pMaxCustomerPrice;
    }
    /**
     * Retrieves the MaxCustomerPrice property.
     *
     * @return
     *  BigDecimal containing the MaxCustomerPrice property.
     */
    public BigDecimal getMaxCustomerPrice(){
        return mMaxCustomerPrice;
    }


    /**
     * Sets the AvgCustomerPrice property.
     *
     * @param pAvgCustomerPrice
     *  BigDecimal to use to update the property.
     */
    public void setAvgCustomerPrice(BigDecimal pAvgCustomerPrice){
        this.mAvgCustomerPrice = pAvgCustomerPrice;
    }
    /**
     * Retrieves the AvgCustomerPrice property.
     *
     * @return
     *  BigDecimal containing the AvgCustomerPrice property.
     */
    public BigDecimal getAvgCustomerPrice(){
        return mAvgCustomerPrice;
    }


    /**
     * Sets the TotalBillPrice property.
     *
     * @param pTotalBillPrice
     *  BigDecimal to use to update the property.
     */
    public void setTotalBillPrice(BigDecimal pTotalBillPrice){
        this.mTotalBillPrice = pTotalBillPrice;
    }
    /**
     * Retrieves the TotalBillPrice property.
     *
     * @return
     *  BigDecimal containing the TotalBillPrice property.
     */
    public BigDecimal getTotalBillPrice(){
        return mTotalBillPrice;
    }


    /**
     * Sets the TotalCreditPrice property.
     *
     * @param pTotalCreditPrice
     *  BigDecimal to use to update the property.
     */
    public void setTotalCreditPrice(BigDecimal pTotalCreditPrice){
        this.mTotalCreditPrice = pTotalCreditPrice;
    }
    /**
     * Retrieves the TotalCreditPrice property.
     *
     * @return
     *  BigDecimal containing the TotalCreditPrice property.
     */
    public BigDecimal getTotalCreditPrice(){
        return mTotalCreditPrice;
    }


    /**
     * Sets the TotalCustomerPrice property.
     *
     * @param pTotalCustomerPrice
     *  BigDecimal to use to update the property.
     */
    public void setTotalCustomerPrice(BigDecimal pTotalCustomerPrice){
        this.mTotalCustomerPrice = pTotalCustomerPrice;
    }
    /**
     * Retrieves the TotalCustomerPrice property.
     *
     * @return
     *  BigDecimal containing the TotalCustomerPrice property.
     */
    public BigDecimal getTotalCustomerPrice(){
        return mTotalCustomerPrice;
    }


    /**
     * Sets the MinDistCost property.
     *
     * @param pMinDistCost
     *  BigDecimal to use to update the property.
     */
    public void setMinDistCost(BigDecimal pMinDistCost){
        this.mMinDistCost = pMinDistCost;
    }
    /**
     * Retrieves the MinDistCost property.
     *
     * @return
     *  BigDecimal containing the MinDistCost property.
     */
    public BigDecimal getMinDistCost(){
        return mMinDistCost;
    }


    /**
     * Sets the MaxDistCost property.
     *
     * @param pMaxDistCost
     *  BigDecimal to use to update the property.
     */
    public void setMaxDistCost(BigDecimal pMaxDistCost){
        this.mMaxDistCost = pMaxDistCost;
    }
    /**
     * Retrieves the MaxDistCost property.
     *
     * @return
     *  BigDecimal containing the MaxDistCost property.
     */
    public BigDecimal getMaxDistCost(){
        return mMaxDistCost;
    }


    /**
     * Sets the AvgDistCost property.
     *
     * @param pAvgDistCost
     *  BigDecimal to use to update the property.
     */
    public void setAvgDistCost(BigDecimal pAvgDistCost){
        this.mAvgDistCost = pAvgDistCost;
    }
    /**
     * Retrieves the AvgDistCost property.
     *
     * @return
     *  BigDecimal containing the AvgDistCost property.
     */
    public BigDecimal getAvgDistCost(){
        return mAvgDistCost;
    }


    /**
     * Sets the TotalBillCost property.
     *
     * @param pTotalBillCost
     *  BigDecimal to use to update the property.
     */
    public void setTotalBillCost(BigDecimal pTotalBillCost){
        this.mTotalBillCost = pTotalBillCost;
    }
    /**
     * Retrieves the TotalBillCost property.
     *
     * @return
     *  BigDecimal containing the TotalBillCost property.
     */
    public BigDecimal getTotalBillCost(){
        return mTotalBillCost;
    }


    /**
     * Sets the TotalCreditCost property.
     *
     * @param pTotalCreditCost
     *  BigDecimal to use to update the property.
     */
    public void setTotalCreditCost(BigDecimal pTotalCreditCost){
        this.mTotalCreditCost = pTotalCreditCost;
    }
    /**
     * Retrieves the TotalCreditCost property.
     *
     * @return
     *  BigDecimal containing the TotalCreditCost property.
     */
    public BigDecimal getTotalCreditCost(){
        return mTotalCreditCost;
    }


    /**
     * Sets the TotalDistCost property.
     *
     * @param pTotalDistCost
     *  BigDecimal to use to update the property.
     */
    public void setTotalDistCost(BigDecimal pTotalDistCost){
        this.mTotalDistCost = pTotalDistCost;
    }
    /**
     * Retrieves the TotalDistCost property.
     *
     * @return
     *  BigDecimal containing the TotalDistCost property.
     */
    public BigDecimal getTotalDistCost(){
        return mTotalDistCost;
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
     * Sets the BaseResultCost property.
     *
     * @param pBaseResultCost
     *  BigDecimal to use to update the property.
     */
    public void setBaseResultCost(BigDecimal pBaseResultCost){
        this.mBaseResultCost = pBaseResultCost;
    }
    /**
     * Retrieves the BaseResultCost property.
     *
     * @return
     *  BigDecimal containing the BaseResultCost property.
     */
    public BigDecimal getBaseResultCost(){
        return mBaseResultCost;
    }


    /**
     * Sets the RebatePercent property.
     *
     * @param pRebatePercent
     *  BigDecimal to use to update the property.
     */
    public void setRebatePercent(BigDecimal pRebatePercent){
        this.mRebatePercent = pRebatePercent;
    }
    /**
     * Retrieves the RebatePercent property.
     *
     * @return
     *  BigDecimal containing the RebatePercent property.
     */
    public BigDecimal getRebatePercent(){
        return mRebatePercent;
    }


    /**
     * Sets the BillQty property.
     *
     * @param pBillQty
     *  int to use to update the property.
     */
    public void setBillQty(int pBillQty){
        this.mBillQty = pBillQty;
    }
    /**
     * Retrieves the BillQty property.
     *
     * @return
     *  int containing the BillQty property.
     */
    public int getBillQty(){
        return mBillQty;
    }


    /**
     * Sets the CreditQty property.
     *
     * @param pCreditQty
     *  int to use to update the property.
     */
    public void setCreditQty(int pCreditQty){
        this.mCreditQty = pCreditQty;
    }
    /**
     * Retrieves the CreditQty property.
     *
     * @return
     *  int containing the CreditQty property.
     */
    public int getCreditQty(){
        return mCreditQty;
    }


    /**
     * Sets the Qty property.
     *
     * @param pQty
     *  int to use to update the property.
     */
    public void setQty(int pQty){
        this.mQty = pQty;
    }
    /**
     * Retrieves the Qty property.
     *
     * @return
     *  int containing the Qty property.
     */
    public int getQty(){
        return mQty;
    }


    /**
     * Sets the RebateBillBase property.
     *
     * @param pRebateBillBase
     *  BigDecimal to use to update the property.
     */
    public void setRebateBillBase(BigDecimal pRebateBillBase){
        this.mRebateBillBase = pRebateBillBase;
    }
    /**
     * Retrieves the RebateBillBase property.
     *
     * @return
     *  BigDecimal containing the RebateBillBase property.
     */
    public BigDecimal getRebateBillBase(){
        return mRebateBillBase;
    }


    /**
     * Sets the RebateCreditBase property.
     *
     * @param pRebateCreditBase
     *  BigDecimal to use to update the property.
     */
    public void setRebateCreditBase(BigDecimal pRebateCreditBase){
        this.mRebateCreditBase = pRebateCreditBase;
    }
    /**
     * Retrieves the RebateCreditBase property.
     *
     * @return
     *  BigDecimal containing the RebateCreditBase property.
     */
    public BigDecimal getRebateCreditBase(){
        return mRebateCreditBase;
    }


    /**
     * Sets the RebateBase property.
     *
     * @param pRebateBase
     *  BigDecimal to use to update the property.
     */
    public void setRebateBase(BigDecimal pRebateBase){
        this.mRebateBase = pRebateBase;
    }
    /**
     * Retrieves the RebateBase property.
     *
     * @return
     *  BigDecimal containing the RebateBase property.
     */
    public BigDecimal getRebateBase(){
        return mRebateBase;
    }


    /**
     * Sets the BillRebate property.
     *
     * @param pBillRebate
     *  BigDecimal to use to update the property.
     */
    public void setBillRebate(BigDecimal pBillRebate){
        this.mBillRebate = pBillRebate;
    }
    /**
     * Retrieves the BillRebate property.
     *
     * @return
     *  BigDecimal containing the BillRebate property.
     */
    public BigDecimal getBillRebate(){
        return mBillRebate;
    }


    /**
     * Sets the CreditRebate property.
     *
     * @param pCreditRebate
     *  BigDecimal to use to update the property.
     */
    public void setCreditRebate(BigDecimal pCreditRebate){
        this.mCreditRebate = pCreditRebate;
    }
    /**
     * Retrieves the CreditRebate property.
     *
     * @return
     *  BigDecimal containing the CreditRebate property.
     */
    public BigDecimal getCreditRebate(){
        return mCreditRebate;
    }


    /**
     * Sets the Rebate property.
     *
     * @param pRebate
     *  BigDecimal to use to update the property.
     */
    public void setRebate(BigDecimal pRebate){
        this.mRebate = pRebate;
    }
    /**
     * Retrieves the Rebate property.
     *
     * @return
     *  BigDecimal containing the Rebate property.
     */
    public BigDecimal getRebate(){
        return mRebate;
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
     * Sets the MinOrderDate property.
     *
     * @param pMinOrderDate
     *  Date to use to update the property.
     */
    public void setMinOrderDate(Date pMinOrderDate){
        this.mMinOrderDate = pMinOrderDate;
    }
    /**
     * Retrieves the MinOrderDate property.
     *
     * @return
     *  Date containing the MinOrderDate property.
     */
    public Date getMinOrderDate(){
        return mMinOrderDate;
    }


    /**
     * Sets the MaxOrderDate property.
     *
     * @param pMaxOrderDate
     *  Date to use to update the property.
     */
    public void setMaxOrderDate(Date pMaxOrderDate){
        this.mMaxOrderDate = pMaxOrderDate;
    }
    /**
     * Retrieves the MaxOrderDate property.
     *
     * @return
     *  Date containing the MaxOrderDate property.
     */
    public Date getMaxOrderDate(){
        return mMaxOrderDate;
    }


    /**
     * Sets the BaseCostEffDate property.
     *
     * @param pBaseCostEffDate
     *  Date to use to update the property.
     */
    public void setBaseCostEffDate(Date pBaseCostEffDate){
        this.mBaseCostEffDate = pBaseCostEffDate;
    }
    /**
     * Retrieves the BaseCostEffDate property.
     *
     * @return
     *  Date containing the BaseCostEffDate property.
     */
    public Date getBaseCostEffDate(){
        return mBaseCostEffDate;
    }


    /**
     * Sets the BaseCostExpDate property.
     *
     * @param pBaseCostExpDate
     *  Date to use to update the property.
     */
    public void setBaseCostExpDate(Date pBaseCostExpDate){
        this.mBaseCostExpDate = pBaseCostExpDate;
    }
    /**
     * Retrieves the BaseCostExpDate property.
     *
     * @return
     *  Date containing the BaseCostExpDate property.
     */
    public Date getBaseCostExpDate(){
        return mBaseCostExpDate;
    }


    
}
