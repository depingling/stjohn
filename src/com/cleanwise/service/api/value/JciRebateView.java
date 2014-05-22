
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        JciRebateView
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
 * <code>JciRebateView</code> is a ViewObject class for UI.
 */
public class JciRebateView
extends ValueObject
{
   
    private static final long serialVersionUID = -4319187972420976997L;
    private int mGroupId;
    private String mAccountErpNum;
    private int mAccountId;
    private String mAccountName;
    private int mDistributorId;
    private String mInvoicePrefix;
    private int mInvoiceNum;
    private Date mInvoiceDate;
    private int mErpOrderNum;
    private Date mOrderDate;
    private String mInvoiceType;
    private String mInvoiceSource;
    private String mSku;
    private int mItemId;
    private String mItemDesc;
    private int mManufId;
    private String mManufDesc;
    private String mManufErp;
    private BigDecimal mCustomerPrice;
    private BigDecimal mDistCost;
    private BigDecimal mBaseCost;
    private BigDecimal mRebatePercent;
    private int mQty;
    private BigDecimal mRebate;
    private String mCategory;
    private int mCategoryId;
    private String mMajorCategory;
    private String mOrigInvoicePrefix;
    private int mOrigInvoiceNum;
    private Date mBaseCostEffDate;
    private Date mBaseCostExpDate;

    /**
     * Constructor.
     */
    public JciRebateView ()
    {
        mAccountErpNum = "";
        mAccountName = "";
        mInvoicePrefix = "";
        mInvoiceType = "";
        mInvoiceSource = "";
        mSku = "";
        mItemDesc = "";
        mManufDesc = "";
        mManufErp = "";
        mCategory = "";
        mMajorCategory = "";
        mOrigInvoicePrefix = "";
    }

    /**
     * Constructor. 
     */
    public JciRebateView(int parm1, String parm2, int parm3, String parm4, int parm5, String parm6, int parm7, Date parm8, int parm9, Date parm10, String parm11, String parm12, String parm13, int parm14, String parm15, int parm16, String parm17, String parm18, BigDecimal parm19, BigDecimal parm20, BigDecimal parm21, BigDecimal parm22, int parm23, BigDecimal parm24, String parm25, int parm26, String parm27, String parm28, int parm29, Date parm30, Date parm31)
    {
        mGroupId = parm1;
        mAccountErpNum = parm2;
        mAccountId = parm3;
        mAccountName = parm4;
        mDistributorId = parm5;
        mInvoicePrefix = parm6;
        mInvoiceNum = parm7;
        mInvoiceDate = parm8;
        mErpOrderNum = parm9;
        mOrderDate = parm10;
        mInvoiceType = parm11;
        mInvoiceSource = parm12;
        mSku = parm13;
        mItemId = parm14;
        mItemDesc = parm15;
        mManufId = parm16;
        mManufDesc = parm17;
        mManufErp = parm18;
        mCustomerPrice = parm19;
        mDistCost = parm20;
        mBaseCost = parm21;
        mRebatePercent = parm22;
        mQty = parm23;
        mRebate = parm24;
        mCategory = parm25;
        mCategoryId = parm26;
        mMajorCategory = parm27;
        mOrigInvoicePrefix = parm28;
        mOrigInvoiceNum = parm29;
        mBaseCostEffDate = parm30;
        mBaseCostExpDate = parm31;
        
    }

    /**
     * Creates a new JciRebateView
     *
     * @return
     *  Newly initialized JciRebateView object.
     */
    public static JciRebateView createValue () 
    {
        JciRebateView valueView = new JciRebateView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this JciRebateView object
     */
    public String toString()
    {
        return "[" + "GroupId=" + mGroupId + ", AccountErpNum=" + mAccountErpNum + ", AccountId=" + mAccountId + ", AccountName=" + mAccountName + ", DistributorId=" + mDistributorId + ", InvoicePrefix=" + mInvoicePrefix + ", InvoiceNum=" + mInvoiceNum + ", InvoiceDate=" + mInvoiceDate + ", ErpOrderNum=" + mErpOrderNum + ", OrderDate=" + mOrderDate + ", InvoiceType=" + mInvoiceType + ", InvoiceSource=" + mInvoiceSource + ", Sku=" + mSku + ", ItemId=" + mItemId + ", ItemDesc=" + mItemDesc + ", ManufId=" + mManufId + ", ManufDesc=" + mManufDesc + ", ManufErp=" + mManufErp + ", CustomerPrice=" + mCustomerPrice + ", DistCost=" + mDistCost + ", BaseCost=" + mBaseCost + ", RebatePercent=" + mRebatePercent + ", Qty=" + mQty + ", Rebate=" + mRebate + ", Category=" + mCategory + ", CategoryId=" + mCategoryId + ", MajorCategory=" + mMajorCategory + ", OrigInvoicePrefix=" + mOrigInvoicePrefix + ", OrigInvoiceNum=" + mOrigInvoiceNum + ", BaseCostEffDate=" + mBaseCostEffDate + ", BaseCostExpDate=" + mBaseCostExpDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("JciRebate");
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

        node = doc.createElement("InvoicePrefix");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoicePrefix)));
        root.appendChild(node);

        node = doc.createElement("InvoiceNum");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceNum)));
        root.appendChild(node);

        node = doc.createElement("InvoiceDate");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceDate)));
        root.appendChild(node);

        node = doc.createElement("ErpOrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpOrderNum)));
        root.appendChild(node);

        node = doc.createElement("OrderDate");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderDate)));
        root.appendChild(node);

        node = doc.createElement("InvoiceType");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceType)));
        root.appendChild(node);

        node = doc.createElement("InvoiceSource");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceSource)));
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

        node = doc.createElement("CustomerPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerPrice)));
        root.appendChild(node);

        node = doc.createElement("DistCost");
        node.appendChild(doc.createTextNode(String.valueOf(mDistCost)));
        root.appendChild(node);

        node = doc.createElement("BaseCost");
        node.appendChild(doc.createTextNode(String.valueOf(mBaseCost)));
        root.appendChild(node);

        node = doc.createElement("RebatePercent");
        node.appendChild(doc.createTextNode(String.valueOf(mRebatePercent)));
        root.appendChild(node);

        node = doc.createElement("Qty");
        node.appendChild(doc.createTextNode(String.valueOf(mQty)));
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

        node = doc.createElement("OrigInvoicePrefix");
        node.appendChild(doc.createTextNode(String.valueOf(mOrigInvoicePrefix)));
        root.appendChild(node);

        node = doc.createElement("OrigInvoiceNum");
        node.appendChild(doc.createTextNode(String.valueOf(mOrigInvoiceNum)));
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
    public JciRebateView copy()  {
      JciRebateView obj = new JciRebateView();
      obj.setGroupId(mGroupId);
      obj.setAccountErpNum(mAccountErpNum);
      obj.setAccountId(mAccountId);
      obj.setAccountName(mAccountName);
      obj.setDistributorId(mDistributorId);
      obj.setInvoicePrefix(mInvoicePrefix);
      obj.setInvoiceNum(mInvoiceNum);
      obj.setInvoiceDate(mInvoiceDate);
      obj.setErpOrderNum(mErpOrderNum);
      obj.setOrderDate(mOrderDate);
      obj.setInvoiceType(mInvoiceType);
      obj.setInvoiceSource(mInvoiceSource);
      obj.setSku(mSku);
      obj.setItemId(mItemId);
      obj.setItemDesc(mItemDesc);
      obj.setManufId(mManufId);
      obj.setManufDesc(mManufDesc);
      obj.setManufErp(mManufErp);
      obj.setCustomerPrice(mCustomerPrice);
      obj.setDistCost(mDistCost);
      obj.setBaseCost(mBaseCost);
      obj.setRebatePercent(mRebatePercent);
      obj.setQty(mQty);
      obj.setRebate(mRebate);
      obj.setCategory(mCategory);
      obj.setCategoryId(mCategoryId);
      obj.setMajorCategory(mMajorCategory);
      obj.setOrigInvoicePrefix(mOrigInvoicePrefix);
      obj.setOrigInvoiceNum(mOrigInvoiceNum);
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
     * Sets the InvoicePrefix property.
     *
     * @param pInvoicePrefix
     *  String to use to update the property.
     */
    public void setInvoicePrefix(String pInvoicePrefix){
        this.mInvoicePrefix = pInvoicePrefix;
    }
    /**
     * Retrieves the InvoicePrefix property.
     *
     * @return
     *  String containing the InvoicePrefix property.
     */
    public String getInvoicePrefix(){
        return mInvoicePrefix;
    }


    /**
     * Sets the InvoiceNum property.
     *
     * @param pInvoiceNum
     *  int to use to update the property.
     */
    public void setInvoiceNum(int pInvoiceNum){
        this.mInvoiceNum = pInvoiceNum;
    }
    /**
     * Retrieves the InvoiceNum property.
     *
     * @return
     *  int containing the InvoiceNum property.
     */
    public int getInvoiceNum(){
        return mInvoiceNum;
    }


    /**
     * Sets the InvoiceDate property.
     *
     * @param pInvoiceDate
     *  Date to use to update the property.
     */
    public void setInvoiceDate(Date pInvoiceDate){
        this.mInvoiceDate = pInvoiceDate;
    }
    /**
     * Retrieves the InvoiceDate property.
     *
     * @return
     *  Date containing the InvoiceDate property.
     */
    public Date getInvoiceDate(){
        return mInvoiceDate;
    }


    /**
     * Sets the ErpOrderNum property.
     *
     * @param pErpOrderNum
     *  int to use to update the property.
     */
    public void setErpOrderNum(int pErpOrderNum){
        this.mErpOrderNum = pErpOrderNum;
    }
    /**
     * Retrieves the ErpOrderNum property.
     *
     * @return
     *  int containing the ErpOrderNum property.
     */
    public int getErpOrderNum(){
        return mErpOrderNum;
    }


    /**
     * Sets the OrderDate property.
     *
     * @param pOrderDate
     *  Date to use to update the property.
     */
    public void setOrderDate(Date pOrderDate){
        this.mOrderDate = pOrderDate;
    }
    /**
     * Retrieves the OrderDate property.
     *
     * @return
     *  Date containing the OrderDate property.
     */
    public Date getOrderDate(){
        return mOrderDate;
    }


    /**
     * Sets the InvoiceType property.
     *
     * @param pInvoiceType
     *  String to use to update the property.
     */
    public void setInvoiceType(String pInvoiceType){
        this.mInvoiceType = pInvoiceType;
    }
    /**
     * Retrieves the InvoiceType property.
     *
     * @return
     *  String containing the InvoiceType property.
     */
    public String getInvoiceType(){
        return mInvoiceType;
    }


    /**
     * Sets the InvoiceSource property.
     *
     * @param pInvoiceSource
     *  String to use to update the property.
     */
    public void setInvoiceSource(String pInvoiceSource){
        this.mInvoiceSource = pInvoiceSource;
    }
    /**
     * Retrieves the InvoiceSource property.
     *
     * @return
     *  String containing the InvoiceSource property.
     */
    public String getInvoiceSource(){
        return mInvoiceSource;
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
     * Sets the CustomerPrice property.
     *
     * @param pCustomerPrice
     *  BigDecimal to use to update the property.
     */
    public void setCustomerPrice(BigDecimal pCustomerPrice){
        this.mCustomerPrice = pCustomerPrice;
    }
    /**
     * Retrieves the CustomerPrice property.
     *
     * @return
     *  BigDecimal containing the CustomerPrice property.
     */
    public BigDecimal getCustomerPrice(){
        return mCustomerPrice;
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
     * Sets the OrigInvoicePrefix property.
     *
     * @param pOrigInvoicePrefix
     *  String to use to update the property.
     */
    public void setOrigInvoicePrefix(String pOrigInvoicePrefix){
        this.mOrigInvoicePrefix = pOrigInvoicePrefix;
    }
    /**
     * Retrieves the OrigInvoicePrefix property.
     *
     * @return
     *  String containing the OrigInvoicePrefix property.
     */
    public String getOrigInvoicePrefix(){
        return mOrigInvoicePrefix;
    }


    /**
     * Sets the OrigInvoiceNum property.
     *
     * @param pOrigInvoiceNum
     *  int to use to update the property.
     */
    public void setOrigInvoiceNum(int pOrigInvoiceNum){
        this.mOrigInvoiceNum = pOrigInvoiceNum;
    }
    /**
     * Retrieves the OrigInvoiceNum property.
     *
     * @return
     *  int containing the OrigInvoiceNum property.
     */
    public int getOrigInvoiceNum(){
        return mOrigInvoiceNum;
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
