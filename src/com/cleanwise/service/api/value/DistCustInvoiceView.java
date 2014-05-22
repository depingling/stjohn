
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        DistCustInvoiceView
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
 * <code>DistCustInvoiceView</code> is a ViewObject class for UI.
 */
public class DistCustInvoiceView
extends ValueObject
{
   
    private static final long serialVersionUID = -2959647341068605932L;
    private String mCustomer;
    private int mAccountId;
    private String mAccountName;
    private String mCustName;
    private int mSiteId;
    private String mSiteName;
    private String mCity;
    private String mState;
    private int mShipTo;
    private String mShipToAddr;
    private int mOrderNbr;
    private java.util.Date mOrderDate;
    private String mCustInvoicePrefix;
    private BigDecimal mCustInvoiceNum;
    private java.util.Date mCustInvoiceDate;
    private String mCustInvoiceType;
    private String mCustInvoiceSource;
    private BigDecimal mCustTotalPrice;
    private BigDecimal mCustGoods;
    private BigDecimal mCustMisc;
    private BigDecimal mCustTax;
    private BigDecimal mAmt5030;
    private String mVendor;
    private String mVenName;
    private String mPoNumber;
    private String mVenInvoiceNum;
    private java.util.Date mVenInvoiceDate;
    private BigDecimal mVenTotalCost;
    private BigDecimal mVenGoodsCost;
    private BigDecimal mVenAdditionalCharges;
    private BigDecimal mVenTax;
    private String mCustItems;
    private String mVenItems;
    private BigDecimal mCustCommonPrice;
    private BigDecimal mVenCommonCost;
    private int mCustItemInd1;
    private int mCustItemInd2;
    private int mCustJoinCount;
    private int mVenItemInd1;
    private int mVenItemInd2;
    private int mVenJoinCount;
    private BigDecimal mOrderCustTotal;
    private BigDecimal mOrderVenTotal;
    private BigDecimal mOrderGM;
    private BigDecimal mOrderGMPr;
    private BigDecimal mPartialDiff;
    private BigDecimal mPartialDiffPr;
    private BigDecimal mVenCredit;

    /**
     * Constructor.
     */
    public DistCustInvoiceView ()
    {
        mCustomer = "";
        mAccountName = "";
        mCustName = "";
        mSiteName = "";
        mCity = "";
        mState = "";
        mShipToAddr = "";
        mCustInvoicePrefix = "";
        mCustInvoiceType = "";
        mCustInvoiceSource = "";
        mVendor = "";
        mVenName = "";
        mPoNumber = "";
        mVenInvoiceNum = "";
        mCustItems = "";
        mVenItems = "";
    }

    /**
     * Constructor. 
     */
    public DistCustInvoiceView(String parm1, int parm2, String parm3, String parm4, int parm5, String parm6, String parm7, String parm8, int parm9, String parm10, int parm11, java.util.Date parm12, String parm13, BigDecimal parm14, java.util.Date parm15, String parm16, String parm17, BigDecimal parm18, BigDecimal parm19, BigDecimal parm20, BigDecimal parm21, BigDecimal parm22, String parm23, String parm24, String parm25, String parm26, java.util.Date parm27, BigDecimal parm28, BigDecimal parm29, BigDecimal parm30, BigDecimal parm31, String parm32, String parm33, BigDecimal parm34, BigDecimal parm35, int parm36, int parm37, int parm38, int parm39, int parm40, int parm41, BigDecimal parm42, BigDecimal parm43, BigDecimal parm44, BigDecimal parm45, BigDecimal parm46, BigDecimal parm47, BigDecimal parm48)
    {
        mCustomer = parm1;
        mAccountId = parm2;
        mAccountName = parm3;
        mCustName = parm4;
        mSiteId = parm5;
        mSiteName = parm6;
        mCity = parm7;
        mState = parm8;
        mShipTo = parm9;
        mShipToAddr = parm10;
        mOrderNbr = parm11;
        mOrderDate = parm12;
        mCustInvoicePrefix = parm13;
        mCustInvoiceNum = parm14;
        mCustInvoiceDate = parm15;
        mCustInvoiceType = parm16;
        mCustInvoiceSource = parm17;
        mCustTotalPrice = parm18;
        mCustGoods = parm19;
        mCustMisc = parm20;
        mCustTax = parm21;
        mAmt5030 = parm22;
        mVendor = parm23;
        mVenName = parm24;
        mPoNumber = parm25;
        mVenInvoiceNum = parm26;
        mVenInvoiceDate = parm27;
        mVenTotalCost = parm28;
        mVenGoodsCost = parm29;
        mVenAdditionalCharges = parm30;
        mVenTax = parm31;
        mCustItems = parm32;
        mVenItems = parm33;
        mCustCommonPrice = parm34;
        mVenCommonCost = parm35;
        mCustItemInd1 = parm36;
        mCustItemInd2 = parm37;
        mCustJoinCount = parm38;
        mVenItemInd1 = parm39;
        mVenItemInd2 = parm40;
        mVenJoinCount = parm41;
        mOrderCustTotal = parm42;
        mOrderVenTotal = parm43;
        mOrderGM = parm44;
        mOrderGMPr = parm45;
        mPartialDiff = parm46;
        mPartialDiffPr = parm47;
        mVenCredit = parm48;
        
    }

    /**
     * Creates a new DistCustInvoiceView
     *
     * @return
     *  Newly initialized DistCustInvoiceView object.
     */
    public static DistCustInvoiceView createValue () 
    {
        DistCustInvoiceView valueView = new DistCustInvoiceView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this DistCustInvoiceView object
     */
    public String toString()
    {
        return "[" + "Customer=" + mCustomer + ", AccountId=" + mAccountId + ", AccountName=" + mAccountName + ", CustName=" + mCustName + ", SiteId=" + mSiteId + ", SiteName=" + mSiteName + ", City=" + mCity + ", State=" + mState + ", ShipTo=" + mShipTo + ", ShipToAddr=" + mShipToAddr + ", OrderNbr=" + mOrderNbr + ", OrderDate=" + mOrderDate + ", CustInvoicePrefix=" + mCustInvoicePrefix + ", CustInvoiceNum=" + mCustInvoiceNum + ", CustInvoiceDate=" + mCustInvoiceDate + ", CustInvoiceType=" + mCustInvoiceType + ", CustInvoiceSource=" + mCustInvoiceSource + ", CustTotalPrice=" + mCustTotalPrice + ", CustGoods=" + mCustGoods + ", CustMisc=" + mCustMisc + ", CustTax=" + mCustTax + ", Amt5030=" + mAmt5030 + ", Vendor=" + mVendor + ", VenName=" + mVenName + ", PoNumber=" + mPoNumber + ", VenInvoiceNum=" + mVenInvoiceNum + ", VenInvoiceDate=" + mVenInvoiceDate + ", VenTotalCost=" + mVenTotalCost + ", VenGoodsCost=" + mVenGoodsCost + ", VenAdditionalCharges=" + mVenAdditionalCharges + ", VenTax=" + mVenTax + ", CustItems=" + mCustItems + ", VenItems=" + mVenItems + ", CustCommonPrice=" + mCustCommonPrice + ", VenCommonCost=" + mVenCommonCost + ", CustItemInd1=" + mCustItemInd1 + ", CustItemInd2=" + mCustItemInd2 + ", CustJoinCount=" + mCustJoinCount + ", VenItemInd1=" + mVenItemInd1 + ", VenItemInd2=" + mVenItemInd2 + ", VenJoinCount=" + mVenJoinCount + ", OrderCustTotal=" + mOrderCustTotal + ", OrderVenTotal=" + mOrderVenTotal + ", OrderGM=" + mOrderGM + ", OrderGMPr=" + mOrderGMPr + ", PartialDiff=" + mPartialDiff + ", PartialDiffPr=" + mPartialDiffPr + ", VenCredit=" + mVenCredit + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("DistCustInvoice");
	root.setAttribute("Id", String.valueOf(mCustomer));

	Element node;

        node = doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node = doc.createElement("AccountName");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountName)));
        root.appendChild(node);

        node = doc.createElement("CustName");
        node.appendChild(doc.createTextNode(String.valueOf(mCustName)));
        root.appendChild(node);

        node = doc.createElement("SiteId");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteId)));
        root.appendChild(node);

        node = doc.createElement("SiteName");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteName)));
        root.appendChild(node);

        node = doc.createElement("City");
        node.appendChild(doc.createTextNode(String.valueOf(mCity)));
        root.appendChild(node);

        node = doc.createElement("State");
        node.appendChild(doc.createTextNode(String.valueOf(mState)));
        root.appendChild(node);

        node = doc.createElement("ShipTo");
        node.appendChild(doc.createTextNode(String.valueOf(mShipTo)));
        root.appendChild(node);

        node = doc.createElement("ShipToAddr");
        node.appendChild(doc.createTextNode(String.valueOf(mShipToAddr)));
        root.appendChild(node);

        node = doc.createElement("OrderNbr");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderNbr)));
        root.appendChild(node);

        node = doc.createElement("OrderDate");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderDate)));
        root.appendChild(node);

        node = doc.createElement("CustInvoicePrefix");
        node.appendChild(doc.createTextNode(String.valueOf(mCustInvoicePrefix)));
        root.appendChild(node);

        node = doc.createElement("CustInvoiceNum");
        node.appendChild(doc.createTextNode(String.valueOf(mCustInvoiceNum)));
        root.appendChild(node);

        node = doc.createElement("CustInvoiceDate");
        node.appendChild(doc.createTextNode(String.valueOf(mCustInvoiceDate)));
        root.appendChild(node);

        node = doc.createElement("CustInvoiceType");
        node.appendChild(doc.createTextNode(String.valueOf(mCustInvoiceType)));
        root.appendChild(node);

        node = doc.createElement("CustInvoiceSource");
        node.appendChild(doc.createTextNode(String.valueOf(mCustInvoiceSource)));
        root.appendChild(node);

        node = doc.createElement("CustTotalPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mCustTotalPrice)));
        root.appendChild(node);

        node = doc.createElement("CustGoods");
        node.appendChild(doc.createTextNode(String.valueOf(mCustGoods)));
        root.appendChild(node);

        node = doc.createElement("CustMisc");
        node.appendChild(doc.createTextNode(String.valueOf(mCustMisc)));
        root.appendChild(node);

        node = doc.createElement("CustTax");
        node.appendChild(doc.createTextNode(String.valueOf(mCustTax)));
        root.appendChild(node);

        node = doc.createElement("Amt5030");
        node.appendChild(doc.createTextNode(String.valueOf(mAmt5030)));
        root.appendChild(node);

        node = doc.createElement("Vendor");
        node.appendChild(doc.createTextNode(String.valueOf(mVendor)));
        root.appendChild(node);

        node = doc.createElement("VenName");
        node.appendChild(doc.createTextNode(String.valueOf(mVenName)));
        root.appendChild(node);

        node = doc.createElement("PoNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mPoNumber)));
        root.appendChild(node);

        node = doc.createElement("VenInvoiceNum");
        node.appendChild(doc.createTextNode(String.valueOf(mVenInvoiceNum)));
        root.appendChild(node);

        node = doc.createElement("VenInvoiceDate");
        node.appendChild(doc.createTextNode(String.valueOf(mVenInvoiceDate)));
        root.appendChild(node);

        node = doc.createElement("VenTotalCost");
        node.appendChild(doc.createTextNode(String.valueOf(mVenTotalCost)));
        root.appendChild(node);

        node = doc.createElement("VenGoodsCost");
        node.appendChild(doc.createTextNode(String.valueOf(mVenGoodsCost)));
        root.appendChild(node);

        node = doc.createElement("VenAdditionalCharges");
        node.appendChild(doc.createTextNode(String.valueOf(mVenAdditionalCharges)));
        root.appendChild(node);

        node = doc.createElement("VenTax");
        node.appendChild(doc.createTextNode(String.valueOf(mVenTax)));
        root.appendChild(node);

        node = doc.createElement("CustItems");
        node.appendChild(doc.createTextNode(String.valueOf(mCustItems)));
        root.appendChild(node);

        node = doc.createElement("VenItems");
        node.appendChild(doc.createTextNode(String.valueOf(mVenItems)));
        root.appendChild(node);

        node = doc.createElement("CustCommonPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mCustCommonPrice)));
        root.appendChild(node);

        node = doc.createElement("VenCommonCost");
        node.appendChild(doc.createTextNode(String.valueOf(mVenCommonCost)));
        root.appendChild(node);

        node = doc.createElement("CustItemInd1");
        node.appendChild(doc.createTextNode(String.valueOf(mCustItemInd1)));
        root.appendChild(node);

        node = doc.createElement("CustItemInd2");
        node.appendChild(doc.createTextNode(String.valueOf(mCustItemInd2)));
        root.appendChild(node);

        node = doc.createElement("CustJoinCount");
        node.appendChild(doc.createTextNode(String.valueOf(mCustJoinCount)));
        root.appendChild(node);

        node = doc.createElement("VenItemInd1");
        node.appendChild(doc.createTextNode(String.valueOf(mVenItemInd1)));
        root.appendChild(node);

        node = doc.createElement("VenItemInd2");
        node.appendChild(doc.createTextNode(String.valueOf(mVenItemInd2)));
        root.appendChild(node);

        node = doc.createElement("VenJoinCount");
        node.appendChild(doc.createTextNode(String.valueOf(mVenJoinCount)));
        root.appendChild(node);

        node = doc.createElement("OrderCustTotal");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderCustTotal)));
        root.appendChild(node);

        node = doc.createElement("OrderVenTotal");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderVenTotal)));
        root.appendChild(node);

        node = doc.createElement("OrderGM");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderGM)));
        root.appendChild(node);

        node = doc.createElement("OrderGMPr");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderGMPr)));
        root.appendChild(node);

        node = doc.createElement("PartialDiff");
        node.appendChild(doc.createTextNode(String.valueOf(mPartialDiff)));
        root.appendChild(node);

        node = doc.createElement("PartialDiffPr");
        node.appendChild(doc.createTextNode(String.valueOf(mPartialDiffPr)));
        root.appendChild(node);

        node = doc.createElement("VenCredit");
        node.appendChild(doc.createTextNode(String.valueOf(mVenCredit)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public DistCustInvoiceView copy()  {
      DistCustInvoiceView obj = new DistCustInvoiceView();
      obj.setCustomer(mCustomer);
      obj.setAccountId(mAccountId);
      obj.setAccountName(mAccountName);
      obj.setCustName(mCustName);
      obj.setSiteId(mSiteId);
      obj.setSiteName(mSiteName);
      obj.setCity(mCity);
      obj.setState(mState);
      obj.setShipTo(mShipTo);
      obj.setShipToAddr(mShipToAddr);
      obj.setOrderNbr(mOrderNbr);
      obj.setOrderDate(mOrderDate);
      obj.setCustInvoicePrefix(mCustInvoicePrefix);
      obj.setCustInvoiceNum(mCustInvoiceNum);
      obj.setCustInvoiceDate(mCustInvoiceDate);
      obj.setCustInvoiceType(mCustInvoiceType);
      obj.setCustInvoiceSource(mCustInvoiceSource);
      obj.setCustTotalPrice(mCustTotalPrice);
      obj.setCustGoods(mCustGoods);
      obj.setCustMisc(mCustMisc);
      obj.setCustTax(mCustTax);
      obj.setAmt5030(mAmt5030);
      obj.setVendor(mVendor);
      obj.setVenName(mVenName);
      obj.setPoNumber(mPoNumber);
      obj.setVenInvoiceNum(mVenInvoiceNum);
      obj.setVenInvoiceDate(mVenInvoiceDate);
      obj.setVenTotalCost(mVenTotalCost);
      obj.setVenGoodsCost(mVenGoodsCost);
      obj.setVenAdditionalCharges(mVenAdditionalCharges);
      obj.setVenTax(mVenTax);
      obj.setCustItems(mCustItems);
      obj.setVenItems(mVenItems);
      obj.setCustCommonPrice(mCustCommonPrice);
      obj.setVenCommonCost(mVenCommonCost);
      obj.setCustItemInd1(mCustItemInd1);
      obj.setCustItemInd2(mCustItemInd2);
      obj.setCustJoinCount(mCustJoinCount);
      obj.setVenItemInd1(mVenItemInd1);
      obj.setVenItemInd2(mVenItemInd2);
      obj.setVenJoinCount(mVenJoinCount);
      obj.setOrderCustTotal(mOrderCustTotal);
      obj.setOrderVenTotal(mOrderVenTotal);
      obj.setOrderGM(mOrderGM);
      obj.setOrderGMPr(mOrderGMPr);
      obj.setPartialDiff(mPartialDiff);
      obj.setPartialDiffPr(mPartialDiffPr);
      obj.setVenCredit(mVenCredit);
      
      return obj;
    }

    
    /**
     * Sets the Customer property.
     *
     * @param pCustomer
     *  String to use to update the property.
     */
    public void setCustomer(String pCustomer){
        this.mCustomer = pCustomer;
    }
    /**
     * Retrieves the Customer property.
     *
     * @return
     *  String containing the Customer property.
     */
    public String getCustomer(){
        return mCustomer;
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
     * Sets the CustName property.
     *
     * @param pCustName
     *  String to use to update the property.
     */
    public void setCustName(String pCustName){
        this.mCustName = pCustName;
    }
    /**
     * Retrieves the CustName property.
     *
     * @return
     *  String containing the CustName property.
     */
    public String getCustName(){
        return mCustName;
    }


    /**
     * Sets the SiteId property.
     *
     * @param pSiteId
     *  int to use to update the property.
     */
    public void setSiteId(int pSiteId){
        this.mSiteId = pSiteId;
    }
    /**
     * Retrieves the SiteId property.
     *
     * @return
     *  int containing the SiteId property.
     */
    public int getSiteId(){
        return mSiteId;
    }


    /**
     * Sets the SiteName property.
     *
     * @param pSiteName
     *  String to use to update the property.
     */
    public void setSiteName(String pSiteName){
        this.mSiteName = pSiteName;
    }
    /**
     * Retrieves the SiteName property.
     *
     * @return
     *  String containing the SiteName property.
     */
    public String getSiteName(){
        return mSiteName;
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
     * Sets the ShipTo property.
     *
     * @param pShipTo
     *  int to use to update the property.
     */
    public void setShipTo(int pShipTo){
        this.mShipTo = pShipTo;
    }
    /**
     * Retrieves the ShipTo property.
     *
     * @return
     *  int containing the ShipTo property.
     */
    public int getShipTo(){
        return mShipTo;
    }


    /**
     * Sets the ShipToAddr property.
     *
     * @param pShipToAddr
     *  String to use to update the property.
     */
    public void setShipToAddr(String pShipToAddr){
        this.mShipToAddr = pShipToAddr;
    }
    /**
     * Retrieves the ShipToAddr property.
     *
     * @return
     *  String containing the ShipToAddr property.
     */
    public String getShipToAddr(){
        return mShipToAddr;
    }


    /**
     * Sets the OrderNbr property.
     *
     * @param pOrderNbr
     *  int to use to update the property.
     */
    public void setOrderNbr(int pOrderNbr){
        this.mOrderNbr = pOrderNbr;
    }
    /**
     * Retrieves the OrderNbr property.
     *
     * @return
     *  int containing the OrderNbr property.
     */
    public int getOrderNbr(){
        return mOrderNbr;
    }


    /**
     * Sets the OrderDate property.
     *
     * @param pOrderDate
     *  java.util.Date to use to update the property.
     */
    public void setOrderDate(java.util.Date pOrderDate){
        this.mOrderDate = pOrderDate;
    }
    /**
     * Retrieves the OrderDate property.
     *
     * @return
     *  java.util.Date containing the OrderDate property.
     */
    public java.util.Date getOrderDate(){
        return mOrderDate;
    }


    /**
     * Sets the CustInvoicePrefix property.
     *
     * @param pCustInvoicePrefix
     *  String to use to update the property.
     */
    public void setCustInvoicePrefix(String pCustInvoicePrefix){
        this.mCustInvoicePrefix = pCustInvoicePrefix;
    }
    /**
     * Retrieves the CustInvoicePrefix property.
     *
     * @return
     *  String containing the CustInvoicePrefix property.
     */
    public String getCustInvoicePrefix(){
        return mCustInvoicePrefix;
    }


    /**
     * Sets the CustInvoiceNum property.
     *
     * @param pCustInvoiceNum
     *  BigDecimal to use to update the property.
     */
    public void setCustInvoiceNum(BigDecimal pCustInvoiceNum){
        this.mCustInvoiceNum = pCustInvoiceNum;
    }
    /**
     * Retrieves the CustInvoiceNum property.
     *
     * @return
     *  BigDecimal containing the CustInvoiceNum property.
     */
    public BigDecimal getCustInvoiceNum(){
        return mCustInvoiceNum;
    }


    /**
     * Sets the CustInvoiceDate property.
     *
     * @param pCustInvoiceDate
     *  java.util.Date to use to update the property.
     */
    public void setCustInvoiceDate(java.util.Date pCustInvoiceDate){
        this.mCustInvoiceDate = pCustInvoiceDate;
    }
    /**
     * Retrieves the CustInvoiceDate property.
     *
     * @return
     *  java.util.Date containing the CustInvoiceDate property.
     */
    public java.util.Date getCustInvoiceDate(){
        return mCustInvoiceDate;
    }


    /**
     * Sets the CustInvoiceType property.
     *
     * @param pCustInvoiceType
     *  String to use to update the property.
     */
    public void setCustInvoiceType(String pCustInvoiceType){
        this.mCustInvoiceType = pCustInvoiceType;
    }
    /**
     * Retrieves the CustInvoiceType property.
     *
     * @return
     *  String containing the CustInvoiceType property.
     */
    public String getCustInvoiceType(){
        return mCustInvoiceType;
    }


    /**
     * Sets the CustInvoiceSource property.
     *
     * @param pCustInvoiceSource
     *  String to use to update the property.
     */
    public void setCustInvoiceSource(String pCustInvoiceSource){
        this.mCustInvoiceSource = pCustInvoiceSource;
    }
    /**
     * Retrieves the CustInvoiceSource property.
     *
     * @return
     *  String containing the CustInvoiceSource property.
     */
    public String getCustInvoiceSource(){
        return mCustInvoiceSource;
    }


    /**
     * Sets the CustTotalPrice property.
     *
     * @param pCustTotalPrice
     *  BigDecimal to use to update the property.
     */
    public void setCustTotalPrice(BigDecimal pCustTotalPrice){
        this.mCustTotalPrice = pCustTotalPrice;
    }
    /**
     * Retrieves the CustTotalPrice property.
     *
     * @return
     *  BigDecimal containing the CustTotalPrice property.
     */
    public BigDecimal getCustTotalPrice(){
        return mCustTotalPrice;
    }


    /**
     * Sets the CustGoods property.
     *
     * @param pCustGoods
     *  BigDecimal to use to update the property.
     */
    public void setCustGoods(BigDecimal pCustGoods){
        this.mCustGoods = pCustGoods;
    }
    /**
     * Retrieves the CustGoods property.
     *
     * @return
     *  BigDecimal containing the CustGoods property.
     */
    public BigDecimal getCustGoods(){
        return mCustGoods;
    }


    /**
     * Sets the CustMisc property.
     *
     * @param pCustMisc
     *  BigDecimal to use to update the property.
     */
    public void setCustMisc(BigDecimal pCustMisc){
        this.mCustMisc = pCustMisc;
    }
    /**
     * Retrieves the CustMisc property.
     *
     * @return
     *  BigDecimal containing the CustMisc property.
     */
    public BigDecimal getCustMisc(){
        return mCustMisc;
    }


    /**
     * Sets the CustTax property.
     *
     * @param pCustTax
     *  BigDecimal to use to update the property.
     */
    public void setCustTax(BigDecimal pCustTax){
        this.mCustTax = pCustTax;
    }
    /**
     * Retrieves the CustTax property.
     *
     * @return
     *  BigDecimal containing the CustTax property.
     */
    public BigDecimal getCustTax(){
        return mCustTax;
    }


    /**
     * Sets the Amt5030 property.
     *
     * @param pAmt5030
     *  BigDecimal to use to update the property.
     */
    public void setAmt5030(BigDecimal pAmt5030){
        this.mAmt5030 = pAmt5030;
    }
    /**
     * Retrieves the Amt5030 property.
     *
     * @return
     *  BigDecimal containing the Amt5030 property.
     */
    public BigDecimal getAmt5030(){
        return mAmt5030;
    }


    /**
     * Sets the Vendor property.
     *
     * @param pVendor
     *  String to use to update the property.
     */
    public void setVendor(String pVendor){
        this.mVendor = pVendor;
    }
    /**
     * Retrieves the Vendor property.
     *
     * @return
     *  String containing the Vendor property.
     */
    public String getVendor(){
        return mVendor;
    }


    /**
     * Sets the VenName property.
     *
     * @param pVenName
     *  String to use to update the property.
     */
    public void setVenName(String pVenName){
        this.mVenName = pVenName;
    }
    /**
     * Retrieves the VenName property.
     *
     * @return
     *  String containing the VenName property.
     */
    public String getVenName(){
        return mVenName;
    }


    /**
     * Sets the PoNumber property.
     *
     * @param pPoNumber
     *  String to use to update the property.
     */
    public void setPoNumber(String pPoNumber){
        this.mPoNumber = pPoNumber;
    }
    /**
     * Retrieves the PoNumber property.
     *
     * @return
     *  String containing the PoNumber property.
     */
    public String getPoNumber(){
        return mPoNumber;
    }


    /**
     * Sets the VenInvoiceNum property.
     *
     * @param pVenInvoiceNum
     *  String to use to update the property.
     */
    public void setVenInvoiceNum(String pVenInvoiceNum){
        this.mVenInvoiceNum = pVenInvoiceNum;
    }
    /**
     * Retrieves the VenInvoiceNum property.
     *
     * @return
     *  String containing the VenInvoiceNum property.
     */
    public String getVenInvoiceNum(){
        return mVenInvoiceNum;
    }


    /**
     * Sets the VenInvoiceDate property.
     *
     * @param pVenInvoiceDate
     *  java.util.Date to use to update the property.
     */
    public void setVenInvoiceDate(java.util.Date pVenInvoiceDate){
        this.mVenInvoiceDate = pVenInvoiceDate;
    }
    /**
     * Retrieves the VenInvoiceDate property.
     *
     * @return
     *  java.util.Date containing the VenInvoiceDate property.
     */
    public java.util.Date getVenInvoiceDate(){
        return mVenInvoiceDate;
    }


    /**
     * Sets the VenTotalCost property.
     *
     * @param pVenTotalCost
     *  BigDecimal to use to update the property.
     */
    public void setVenTotalCost(BigDecimal pVenTotalCost){
        this.mVenTotalCost = pVenTotalCost;
    }
    /**
     * Retrieves the VenTotalCost property.
     *
     * @return
     *  BigDecimal containing the VenTotalCost property.
     */
    public BigDecimal getVenTotalCost(){
        return mVenTotalCost;
    }


    /**
     * Sets the VenGoodsCost property.
     *
     * @param pVenGoodsCost
     *  BigDecimal to use to update the property.
     */
    public void setVenGoodsCost(BigDecimal pVenGoodsCost){
        this.mVenGoodsCost = pVenGoodsCost;
    }
    /**
     * Retrieves the VenGoodsCost property.
     *
     * @return
     *  BigDecimal containing the VenGoodsCost property.
     */
    public BigDecimal getVenGoodsCost(){
        return mVenGoodsCost;
    }


    /**
     * Sets the VenAdditionalCharges property.
     *
     * @param pVenAdditionalCharges
     *  BigDecimal to use to update the property.
     */
    public void setVenAdditionalCharges(BigDecimal pVenAdditionalCharges){
        this.mVenAdditionalCharges = pVenAdditionalCharges;
    }
    /**
     * Retrieves the VenAdditionalCharges property.
     *
     * @return
     *  BigDecimal containing the VenAdditionalCharges property.
     */
    public BigDecimal getVenAdditionalCharges(){
        return mVenAdditionalCharges;
    }


    /**
     * Sets the VenTax property.
     *
     * @param pVenTax
     *  BigDecimal to use to update the property.
     */
    public void setVenTax(BigDecimal pVenTax){
        this.mVenTax = pVenTax;
    }
    /**
     * Retrieves the VenTax property.
     *
     * @return
     *  BigDecimal containing the VenTax property.
     */
    public BigDecimal getVenTax(){
        return mVenTax;
    }


    /**
     * Sets the CustItems property.
     *
     * @param pCustItems
     *  String to use to update the property.
     */
    public void setCustItems(String pCustItems){
        this.mCustItems = pCustItems;
    }
    /**
     * Retrieves the CustItems property.
     *
     * @return
     *  String containing the CustItems property.
     */
    public String getCustItems(){
        return mCustItems;
    }


    /**
     * Sets the VenItems property.
     *
     * @param pVenItems
     *  String to use to update the property.
     */
    public void setVenItems(String pVenItems){
        this.mVenItems = pVenItems;
    }
    /**
     * Retrieves the VenItems property.
     *
     * @return
     *  String containing the VenItems property.
     */
    public String getVenItems(){
        return mVenItems;
    }


    /**
     * Sets the CustCommonPrice property.
     *
     * @param pCustCommonPrice
     *  BigDecimal to use to update the property.
     */
    public void setCustCommonPrice(BigDecimal pCustCommonPrice){
        this.mCustCommonPrice = pCustCommonPrice;
    }
    /**
     * Retrieves the CustCommonPrice property.
     *
     * @return
     *  BigDecimal containing the CustCommonPrice property.
     */
    public BigDecimal getCustCommonPrice(){
        return mCustCommonPrice;
    }


    /**
     * Sets the VenCommonCost property.
     *
     * @param pVenCommonCost
     *  BigDecimal to use to update the property.
     */
    public void setVenCommonCost(BigDecimal pVenCommonCost){
        this.mVenCommonCost = pVenCommonCost;
    }
    /**
     * Retrieves the VenCommonCost property.
     *
     * @return
     *  BigDecimal containing the VenCommonCost property.
     */
    public BigDecimal getVenCommonCost(){
        return mVenCommonCost;
    }


    /**
     * Sets the CustItemInd1 property.
     *
     * @param pCustItemInd1
     *  int to use to update the property.
     */
    public void setCustItemInd1(int pCustItemInd1){
        this.mCustItemInd1 = pCustItemInd1;
    }
    /**
     * Retrieves the CustItemInd1 property.
     *
     * @return
     *  int containing the CustItemInd1 property.
     */
    public int getCustItemInd1(){
        return mCustItemInd1;
    }


    /**
     * Sets the CustItemInd2 property.
     *
     * @param pCustItemInd2
     *  int to use to update the property.
     */
    public void setCustItemInd2(int pCustItemInd2){
        this.mCustItemInd2 = pCustItemInd2;
    }
    /**
     * Retrieves the CustItemInd2 property.
     *
     * @return
     *  int containing the CustItemInd2 property.
     */
    public int getCustItemInd2(){
        return mCustItemInd2;
    }


    /**
     * Sets the CustJoinCount property.
     *
     * @param pCustJoinCount
     *  int to use to update the property.
     */
    public void setCustJoinCount(int pCustJoinCount){
        this.mCustJoinCount = pCustJoinCount;
    }
    /**
     * Retrieves the CustJoinCount property.
     *
     * @return
     *  int containing the CustJoinCount property.
     */
    public int getCustJoinCount(){
        return mCustJoinCount;
    }


    /**
     * Sets the VenItemInd1 property.
     *
     * @param pVenItemInd1
     *  int to use to update the property.
     */
    public void setVenItemInd1(int pVenItemInd1){
        this.mVenItemInd1 = pVenItemInd1;
    }
    /**
     * Retrieves the VenItemInd1 property.
     *
     * @return
     *  int containing the VenItemInd1 property.
     */
    public int getVenItemInd1(){
        return mVenItemInd1;
    }


    /**
     * Sets the VenItemInd2 property.
     *
     * @param pVenItemInd2
     *  int to use to update the property.
     */
    public void setVenItemInd2(int pVenItemInd2){
        this.mVenItemInd2 = pVenItemInd2;
    }
    /**
     * Retrieves the VenItemInd2 property.
     *
     * @return
     *  int containing the VenItemInd2 property.
     */
    public int getVenItemInd2(){
        return mVenItemInd2;
    }


    /**
     * Sets the VenJoinCount property.
     *
     * @param pVenJoinCount
     *  int to use to update the property.
     */
    public void setVenJoinCount(int pVenJoinCount){
        this.mVenJoinCount = pVenJoinCount;
    }
    /**
     * Retrieves the VenJoinCount property.
     *
     * @return
     *  int containing the VenJoinCount property.
     */
    public int getVenJoinCount(){
        return mVenJoinCount;
    }


    /**
     * Sets the OrderCustTotal property.
     *
     * @param pOrderCustTotal
     *  BigDecimal to use to update the property.
     */
    public void setOrderCustTotal(BigDecimal pOrderCustTotal){
        this.mOrderCustTotal = pOrderCustTotal;
    }
    /**
     * Retrieves the OrderCustTotal property.
     *
     * @return
     *  BigDecimal containing the OrderCustTotal property.
     */
    public BigDecimal getOrderCustTotal(){
        return mOrderCustTotal;
    }


    /**
     * Sets the OrderVenTotal property.
     *
     * @param pOrderVenTotal
     *  BigDecimal to use to update the property.
     */
    public void setOrderVenTotal(BigDecimal pOrderVenTotal){
        this.mOrderVenTotal = pOrderVenTotal;
    }
    /**
     * Retrieves the OrderVenTotal property.
     *
     * @return
     *  BigDecimal containing the OrderVenTotal property.
     */
    public BigDecimal getOrderVenTotal(){
        return mOrderVenTotal;
    }


    /**
     * Sets the OrderGM property.
     *
     * @param pOrderGM
     *  BigDecimal to use to update the property.
     */
    public void setOrderGM(BigDecimal pOrderGM){
        this.mOrderGM = pOrderGM;
    }
    /**
     * Retrieves the OrderGM property.
     *
     * @return
     *  BigDecimal containing the OrderGM property.
     */
    public BigDecimal getOrderGM(){
        return mOrderGM;
    }


    /**
     * Sets the OrderGMPr property.
     *
     * @param pOrderGMPr
     *  BigDecimal to use to update the property.
     */
    public void setOrderGMPr(BigDecimal pOrderGMPr){
        this.mOrderGMPr = pOrderGMPr;
    }
    /**
     * Retrieves the OrderGMPr property.
     *
     * @return
     *  BigDecimal containing the OrderGMPr property.
     */
    public BigDecimal getOrderGMPr(){
        return mOrderGMPr;
    }


    /**
     * Sets the PartialDiff property.
     *
     * @param pPartialDiff
     *  BigDecimal to use to update the property.
     */
    public void setPartialDiff(BigDecimal pPartialDiff){
        this.mPartialDiff = pPartialDiff;
    }
    /**
     * Retrieves the PartialDiff property.
     *
     * @return
     *  BigDecimal containing the PartialDiff property.
     */
    public BigDecimal getPartialDiff(){
        return mPartialDiff;
    }


    /**
     * Sets the PartialDiffPr property.
     *
     * @param pPartialDiffPr
     *  BigDecimal to use to update the property.
     */
    public void setPartialDiffPr(BigDecimal pPartialDiffPr){
        this.mPartialDiffPr = pPartialDiffPr;
    }
    /**
     * Retrieves the PartialDiffPr property.
     *
     * @return
     *  BigDecimal containing the PartialDiffPr property.
     */
    public BigDecimal getPartialDiffPr(){
        return mPartialDiffPr;
    }


    /**
     * Sets the VenCredit property.
     *
     * @param pVenCredit
     *  BigDecimal to use to update the property.
     */
    public void setVenCredit(BigDecimal pVenCredit){
        this.mVenCredit = pVenCredit;
    }
    /**
     * Retrieves the VenCredit property.
     *
     * @return
     *  BigDecimal containing the VenCredit property.
     */
    public BigDecimal getVenCredit(){
        return mVenCredit;
    }


    
}
