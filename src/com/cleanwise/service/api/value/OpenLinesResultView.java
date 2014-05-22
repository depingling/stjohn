
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OpenLinesResultView
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

import java.math.BigDecimal;import java.util.Date;


/**
 * <code>OpenLinesResultView</code> is a ViewObject class for UI.
 */
public class OpenLinesResultView
extends ValueObject
{
   
    private static final long serialVersionUID = 6325658968951634802L;
    private String mAccountErpNum;
    private String mTargetFacility;
    private BigDecimal mGoods;
    private String mVendor;
    private String mVendorName;
    private Date mWebPoDate;
    private Date mPoDate;
    private String mItem;
    private BigDecimal mUnitCost;
    private String mWebPoNumber;
    private int mWebLineNumber;
    private String mPoNumber;
    private int mLineNumber;
    private int mOrderNumber;
    private String mVendorItem;
    private String mDescritption;
    private String mShipTo;
    private String mShipName;
    private String mState;
    private String mZipCode;
    private int mQuantity;
    private int mOpenQuantity;
    private BigDecimal mOpenCost;
    private BigDecimal mOpenPrice;
    private BigDecimal mUnitPrice;
    private Date mDeliveryDate;
    private Date mOrderDate;
    private Date mOrderTime;
    private int mVendorId;
    private int mSiteId;
    private int mScheduleId;
    private String mSiteSchedule;
    private java.util.List mExsistingVendorInvoicesAgainstPo;
    private String mBillingOnlyOrder;
    private String mOpenLineStatus;
    private String mOrderStatusCd;
    private int mOrderId;
    private java.util.Date mApprovedDate;
    private String mOrderSiteName;
    private String mAccountName;
    private String mOrderItemStatusCd;
    private String mFreightHandler;
    private int mOrderItemId;
    private int mSiteRank;
    private BigDecimal mDistUomConvCost;
    private int mDistUomConvQty;
    private int mDistUomConvOpenQty;
    private String mOutboundPoNum;

    /**
     * Constructor.
     */
    public OpenLinesResultView ()
    {
        mAccountErpNum = "";
        mTargetFacility = "";
        mVendor = "";
        mVendorName = "";
        mItem = "";
        mWebPoNumber = "";
        mPoNumber = "";
        mVendorItem = "";
        mDescritption = "";
        mShipTo = "";
        mShipName = "";
        mState = "";
        mZipCode = "";
        mSiteSchedule = "";
        mBillingOnlyOrder = "";
        mOpenLineStatus = "";
        mOrderStatusCd = "";
        mOrderSiteName = "";
        mAccountName = "";
        mOrderItemStatusCd = "";
        mFreightHandler = "";
        mOutboundPoNum = "";
    }

    /**
     * Constructor. 
     */
    public OpenLinesResultView(String parm1, String parm2, BigDecimal parm3, String parm4, String parm5, Date parm6, Date parm7, String parm8, BigDecimal parm9, String parm10, int parm11, String parm12, int parm13, int parm14, String parm15, String parm16, String parm17, String parm18, String parm19, String parm20, int parm21, int parm22, BigDecimal parm23, BigDecimal parm24, BigDecimal parm25, Date parm26, Date parm27, Date parm28, int parm29, int parm30, int parm31, String parm32, java.util.List parm33, String parm34, String parm35, String parm36, int parm37, java.util.Date parm38, String parm39, String parm40, String parm41, String parm42, int parm43, int parm44, BigDecimal parm45, int parm46, int parm47, String parm48)
    {
        mAccountErpNum = parm1;
        mTargetFacility = parm2;
        mGoods = parm3;
        mVendor = parm4;
        mVendorName = parm5;
        mWebPoDate = parm6;
        mPoDate = parm7;
        mItem = parm8;
        mUnitCost = parm9;
        mWebPoNumber = parm10;
        mWebLineNumber = parm11;
        mPoNumber = parm12;
        mLineNumber = parm13;
        mOrderNumber = parm14;
        mVendorItem = parm15;
        mDescritption = parm16;
        mShipTo = parm17;
        mShipName = parm18;
        mState = parm19;
        mZipCode = parm20;
        mQuantity = parm21;
        mOpenQuantity = parm22;
        mOpenCost = parm23;
        mOpenPrice = parm24;
        mUnitPrice = parm25;
        mDeliveryDate = parm26;
        mOrderDate = parm27;
        mOrderTime = parm28;
        mVendorId = parm29;
        mSiteId = parm30;
        mScheduleId = parm31;
        mSiteSchedule = parm32;
        mExsistingVendorInvoicesAgainstPo = parm33;
        mBillingOnlyOrder = parm34;
        mOpenLineStatus = parm35;
        mOrderStatusCd = parm36;
        mOrderId = parm37;
        mApprovedDate = parm38;
        mOrderSiteName = parm39;
        mAccountName = parm40;
        mOrderItemStatusCd = parm41;
        mFreightHandler = parm42;
        mOrderItemId = parm43;
        mSiteRank = parm44;
        mDistUomConvCost = parm45;
        mDistUomConvQty = parm46;
        mDistUomConvOpenQty = parm47;
        mOutboundPoNum = parm48;
        
    }

    /**
     * Creates a new OpenLinesResultView
     *
     * @return
     *  Newly initialized OpenLinesResultView object.
     */
    public static OpenLinesResultView createValue () 
    {
        OpenLinesResultView valueView = new OpenLinesResultView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OpenLinesResultView object
     */
    public String toString()
    {
        return "[" + "AccountErpNum=" + mAccountErpNum + ", TargetFacility=" + mTargetFacility + ", Goods=" + mGoods + ", Vendor=" + mVendor + ", VendorName=" + mVendorName + ", WebPoDate=" + mWebPoDate + ", PoDate=" + mPoDate + ", Item=" + mItem + ", UnitCost=" + mUnitCost + ", WebPoNumber=" + mWebPoNumber + ", WebLineNumber=" + mWebLineNumber + ", PoNumber=" + mPoNumber + ", LineNumber=" + mLineNumber + ", OrderNumber=" + mOrderNumber + ", VendorItem=" + mVendorItem + ", Descritption=" + mDescritption + ", ShipTo=" + mShipTo + ", ShipName=" + mShipName + ", State=" + mState + ", ZipCode=" + mZipCode + ", Quantity=" + mQuantity + ", OpenQuantity=" + mOpenQuantity + ", OpenCost=" + mOpenCost + ", OpenPrice=" + mOpenPrice + ", UnitPrice=" + mUnitPrice + ", DeliveryDate=" + mDeliveryDate + ", OrderDate=" + mOrderDate + ", OrderTime=" + mOrderTime + ", VendorId=" + mVendorId + ", SiteId=" + mSiteId + ", ScheduleId=" + mScheduleId + ", SiteSchedule=" + mSiteSchedule + ", ExsistingVendorInvoicesAgainstPo=" + mExsistingVendorInvoicesAgainstPo + ", BillingOnlyOrder=" + mBillingOnlyOrder + ", OpenLineStatus=" + mOpenLineStatus + ", OrderStatusCd=" + mOrderStatusCd + ", OrderId=" + mOrderId + ", ApprovedDate=" + mApprovedDate + ", OrderSiteName=" + mOrderSiteName + ", AccountName=" + mAccountName + ", OrderItemStatusCd=" + mOrderItemStatusCd + ", FreightHandler=" + mFreightHandler + ", OrderItemId=" + mOrderItemId + ", SiteRank=" + mSiteRank + ", DistUomConvCost=" + mDistUomConvCost + ", DistUomConvQty=" + mDistUomConvQty + ", DistUomConvOpenQty=" + mDistUomConvOpenQty + ", OutboundPoNum=" + mOutboundPoNum + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("OpenLinesResult");
	root.setAttribute("Id", String.valueOf(mAccountErpNum));

	Element node;

        node = doc.createElement("TargetFacility");
        node.appendChild(doc.createTextNode(String.valueOf(mTargetFacility)));
        root.appendChild(node);

        node = doc.createElement("Goods");
        node.appendChild(doc.createTextNode(String.valueOf(mGoods)));
        root.appendChild(node);

        node = doc.createElement("Vendor");
        node.appendChild(doc.createTextNode(String.valueOf(mVendor)));
        root.appendChild(node);

        node = doc.createElement("VendorName");
        node.appendChild(doc.createTextNode(String.valueOf(mVendorName)));
        root.appendChild(node);

        node = doc.createElement("WebPoDate");
        node.appendChild(doc.createTextNode(String.valueOf(mWebPoDate)));
        root.appendChild(node);

        node = doc.createElement("PoDate");
        node.appendChild(doc.createTextNode(String.valueOf(mPoDate)));
        root.appendChild(node);

        node = doc.createElement("Item");
        node.appendChild(doc.createTextNode(String.valueOf(mItem)));
        root.appendChild(node);

        node = doc.createElement("UnitCost");
        node.appendChild(doc.createTextNode(String.valueOf(mUnitCost)));
        root.appendChild(node);

        node = doc.createElement("WebPoNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mWebPoNumber)));
        root.appendChild(node);

        node = doc.createElement("WebLineNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mWebLineNumber)));
        root.appendChild(node);

        node = doc.createElement("PoNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mPoNumber)));
        root.appendChild(node);

        node = doc.createElement("LineNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mLineNumber)));
        root.appendChild(node);

        node = doc.createElement("OrderNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderNumber)));
        root.appendChild(node);

        node = doc.createElement("VendorItem");
        node.appendChild(doc.createTextNode(String.valueOf(mVendorItem)));
        root.appendChild(node);

        node = doc.createElement("Descritption");
        node.appendChild(doc.createTextNode(String.valueOf(mDescritption)));
        root.appendChild(node);

        node = doc.createElement("ShipTo");
        node.appendChild(doc.createTextNode(String.valueOf(mShipTo)));
        root.appendChild(node);

        node = doc.createElement("ShipName");
        node.appendChild(doc.createTextNode(String.valueOf(mShipName)));
        root.appendChild(node);

        node = doc.createElement("State");
        node.appendChild(doc.createTextNode(String.valueOf(mState)));
        root.appendChild(node);

        node = doc.createElement("ZipCode");
        node.appendChild(doc.createTextNode(String.valueOf(mZipCode)));
        root.appendChild(node);

        node = doc.createElement("Quantity");
        node.appendChild(doc.createTextNode(String.valueOf(mQuantity)));
        root.appendChild(node);

        node = doc.createElement("OpenQuantity");
        node.appendChild(doc.createTextNode(String.valueOf(mOpenQuantity)));
        root.appendChild(node);

        node = doc.createElement("OpenCost");
        node.appendChild(doc.createTextNode(String.valueOf(mOpenCost)));
        root.appendChild(node);

        node = doc.createElement("OpenPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mOpenPrice)));
        root.appendChild(node);

        node = doc.createElement("UnitPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mUnitPrice)));
        root.appendChild(node);

        node = doc.createElement("DeliveryDate");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryDate)));
        root.appendChild(node);

        node = doc.createElement("OrderDate");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderDate)));
        root.appendChild(node);

        node = doc.createElement("OrderTime");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderTime)));
        root.appendChild(node);

        node = doc.createElement("VendorId");
        node.appendChild(doc.createTextNode(String.valueOf(mVendorId)));
        root.appendChild(node);

        node = doc.createElement("SiteId");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteId)));
        root.appendChild(node);

        node = doc.createElement("ScheduleId");
        node.appendChild(doc.createTextNode(String.valueOf(mScheduleId)));
        root.appendChild(node);

        node = doc.createElement("SiteSchedule");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteSchedule)));
        root.appendChild(node);

        node = doc.createElement("ExsistingVendorInvoicesAgainstPo");
        node.appendChild(doc.createTextNode(String.valueOf(mExsistingVendorInvoicesAgainstPo)));
        root.appendChild(node);

        node = doc.createElement("BillingOnlyOrder");
        node.appendChild(doc.createTextNode(String.valueOf(mBillingOnlyOrder)));
        root.appendChild(node);

        node = doc.createElement("OpenLineStatus");
        node.appendChild(doc.createTextNode(String.valueOf(mOpenLineStatus)));
        root.appendChild(node);

        node = doc.createElement("OrderStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderStatusCd)));
        root.appendChild(node);

        node = doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node = doc.createElement("ApprovedDate");
        node.appendChild(doc.createTextNode(String.valueOf(mApprovedDate)));
        root.appendChild(node);

        node = doc.createElement("OrderSiteName");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderSiteName)));
        root.appendChild(node);

        node = doc.createElement("AccountName");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountName)));
        root.appendChild(node);

        node = doc.createElement("OrderItemStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderItemStatusCd)));
        root.appendChild(node);

        node = doc.createElement("FreightHandler");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightHandler)));
        root.appendChild(node);

        node = doc.createElement("OrderItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderItemId)));
        root.appendChild(node);

        node = doc.createElement("SiteRank");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteRank)));
        root.appendChild(node);

        node = doc.createElement("DistUomConvCost");
        node.appendChild(doc.createTextNode(String.valueOf(mDistUomConvCost)));
        root.appendChild(node);

        node = doc.createElement("DistUomConvQty");
        node.appendChild(doc.createTextNode(String.valueOf(mDistUomConvQty)));
        root.appendChild(node);

        node = doc.createElement("DistUomConvOpenQty");
        node.appendChild(doc.createTextNode(String.valueOf(mDistUomConvOpenQty)));
        root.appendChild(node);

        node = doc.createElement("OutboundPoNum");
        node.appendChild(doc.createTextNode(String.valueOf(mOutboundPoNum)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public OpenLinesResultView copy()  {
      OpenLinesResultView obj = new OpenLinesResultView();
      obj.setAccountErpNum(mAccountErpNum);
      obj.setTargetFacility(mTargetFacility);
      obj.setGoods(mGoods);
      obj.setVendor(mVendor);
      obj.setVendorName(mVendorName);
      obj.setWebPoDate(mWebPoDate);
      obj.setPoDate(mPoDate);
      obj.setItem(mItem);
      obj.setUnitCost(mUnitCost);
      obj.setWebPoNumber(mWebPoNumber);
      obj.setWebLineNumber(mWebLineNumber);
      obj.setPoNumber(mPoNumber);
      obj.setLineNumber(mLineNumber);
      obj.setOrderNumber(mOrderNumber);
      obj.setVendorItem(mVendorItem);
      obj.setDescritption(mDescritption);
      obj.setShipTo(mShipTo);
      obj.setShipName(mShipName);
      obj.setState(mState);
      obj.setZipCode(mZipCode);
      obj.setQuantity(mQuantity);
      obj.setOpenQuantity(mOpenQuantity);
      obj.setOpenCost(mOpenCost);
      obj.setOpenPrice(mOpenPrice);
      obj.setUnitPrice(mUnitPrice);
      obj.setDeliveryDate(mDeliveryDate);
      obj.setOrderDate(mOrderDate);
      obj.setOrderTime(mOrderTime);
      obj.setVendorId(mVendorId);
      obj.setSiteId(mSiteId);
      obj.setScheduleId(mScheduleId);
      obj.setSiteSchedule(mSiteSchedule);
      obj.setExsistingVendorInvoicesAgainstPo(mExsistingVendorInvoicesAgainstPo);
      obj.setBillingOnlyOrder(mBillingOnlyOrder);
      obj.setOpenLineStatus(mOpenLineStatus);
      obj.setOrderStatusCd(mOrderStatusCd);
      obj.setOrderId(mOrderId);
      obj.setApprovedDate(mApprovedDate);
      obj.setOrderSiteName(mOrderSiteName);
      obj.setAccountName(mAccountName);
      obj.setOrderItemStatusCd(mOrderItemStatusCd);
      obj.setFreightHandler(mFreightHandler);
      obj.setOrderItemId(mOrderItemId);
      obj.setSiteRank(mSiteRank);
      obj.setDistUomConvCost(mDistUomConvCost);
      obj.setDistUomConvQty(mDistUomConvQty);
      obj.setDistUomConvOpenQty(mDistUomConvOpenQty);
      obj.setOutboundPoNum(mOutboundPoNum);
      
      return obj;
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
     * Sets the TargetFacility property.
     *
     * @param pTargetFacility
     *  String to use to update the property.
     */
    public void setTargetFacility(String pTargetFacility){
        this.mTargetFacility = pTargetFacility;
    }
    /**
     * Retrieves the TargetFacility property.
     *
     * @return
     *  String containing the TargetFacility property.
     */
    public String getTargetFacility(){
        return mTargetFacility;
    }


    /**
     * Sets the Goods property.
     *
     * @param pGoods
     *  BigDecimal to use to update the property.
     */
    public void setGoods(BigDecimal pGoods){
        this.mGoods = pGoods;
    }
    /**
     * Retrieves the Goods property.
     *
     * @return
     *  BigDecimal containing the Goods property.
     */
    public BigDecimal getGoods(){
        return mGoods;
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
     * Sets the VendorName property.
     *
     * @param pVendorName
     *  String to use to update the property.
     */
    public void setVendorName(String pVendorName){
        this.mVendorName = pVendorName;
    }
    /**
     * Retrieves the VendorName property.
     *
     * @return
     *  String containing the VendorName property.
     */
    public String getVendorName(){
        return mVendorName;
    }


    /**
     * Sets the WebPoDate property.
     *
     * @param pWebPoDate
     *  Date to use to update the property.
     */
    public void setWebPoDate(Date pWebPoDate){
        this.mWebPoDate = pWebPoDate;
    }
    /**
     * Retrieves the WebPoDate property.
     *
     * @return
     *  Date containing the WebPoDate property.
     */
    public Date getWebPoDate(){
        return mWebPoDate;
    }


    /**
     * Sets the PoDate property.
     *
     * @param pPoDate
     *  Date to use to update the property.
     */
    public void setPoDate(Date pPoDate){
        this.mPoDate = pPoDate;
    }
    /**
     * Retrieves the PoDate property.
     *
     * @return
     *  Date containing the PoDate property.
     */
    public Date getPoDate(){
        return mPoDate;
    }


    /**
     * Sets the Item property.
     *
     * @param pItem
     *  String to use to update the property.
     */
    public void setItem(String pItem){
        this.mItem = pItem;
    }
    /**
     * Retrieves the Item property.
     *
     * @return
     *  String containing the Item property.
     */
    public String getItem(){
        return mItem;
    }


    /**
     * Sets the UnitCost property.
     *
     * @param pUnitCost
     *  BigDecimal to use to update the property.
     */
    public void setUnitCost(BigDecimal pUnitCost){
        this.mUnitCost = pUnitCost;
    }
    /**
     * Retrieves the UnitCost property.
     *
     * @return
     *  BigDecimal containing the UnitCost property.
     */
    public BigDecimal getUnitCost(){
        return mUnitCost;
    }


    /**
     * Sets the WebPoNumber property.
     *
     * @param pWebPoNumber
     *  String to use to update the property.
     */
    public void setWebPoNumber(String pWebPoNumber){
        this.mWebPoNumber = pWebPoNumber;
    }
    /**
     * Retrieves the WebPoNumber property.
     *
     * @return
     *  String containing the WebPoNumber property.
     */
    public String getWebPoNumber(){
        return mWebPoNumber;
    }


    /**
     * Sets the WebLineNumber property.
     *
     * @param pWebLineNumber
     *  int to use to update the property.
     */
    public void setWebLineNumber(int pWebLineNumber){
        this.mWebLineNumber = pWebLineNumber;
    }
    /**
     * Retrieves the WebLineNumber property.
     *
     * @return
     *  int containing the WebLineNumber property.
     */
    public int getWebLineNumber(){
        return mWebLineNumber;
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
     * Sets the LineNumber property.
     *
     * @param pLineNumber
     *  int to use to update the property.
     */
    public void setLineNumber(int pLineNumber){
        this.mLineNumber = pLineNumber;
    }
    /**
     * Retrieves the LineNumber property.
     *
     * @return
     *  int containing the LineNumber property.
     */
    public int getLineNumber(){
        return mLineNumber;
    }


    /**
     * Sets the OrderNumber property.
     *
     * @param pOrderNumber
     *  int to use to update the property.
     */
    public void setOrderNumber(int pOrderNumber){
        this.mOrderNumber = pOrderNumber;
    }
    /**
     * Retrieves the OrderNumber property.
     *
     * @return
     *  int containing the OrderNumber property.
     */
    public int getOrderNumber(){
        return mOrderNumber;
    }


    /**
     * Sets the VendorItem property.
     *
     * @param pVendorItem
     *  String to use to update the property.
     */
    public void setVendorItem(String pVendorItem){
        this.mVendorItem = pVendorItem;
    }
    /**
     * Retrieves the VendorItem property.
     *
     * @return
     *  String containing the VendorItem property.
     */
    public String getVendorItem(){
        return mVendorItem;
    }


    /**
     * Sets the Descritption property.
     *
     * @param pDescritption
     *  String to use to update the property.
     */
    public void setDescritption(String pDescritption){
        this.mDescritption = pDescritption;
    }
    /**
     * Retrieves the Descritption property.
     *
     * @return
     *  String containing the Descritption property.
     */
    public String getDescritption(){
        return mDescritption;
    }


    /**
     * Sets the ShipTo property.
     *
     * @param pShipTo
     *  String to use to update the property.
     */
    public void setShipTo(String pShipTo){
        this.mShipTo = pShipTo;
    }
    /**
     * Retrieves the ShipTo property.
     *
     * @return
     *  String containing the ShipTo property.
     */
    public String getShipTo(){
        return mShipTo;
    }


    /**
     * Sets the ShipName property.
     *
     * @param pShipName
     *  String to use to update the property.
     */
    public void setShipName(String pShipName){
        this.mShipName = pShipName;
    }
    /**
     * Retrieves the ShipName property.
     *
     * @return
     *  String containing the ShipName property.
     */
    public String getShipName(){
        return mShipName;
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
     * Sets the ZipCode property.
     *
     * @param pZipCode
     *  String to use to update the property.
     */
    public void setZipCode(String pZipCode){
        this.mZipCode = pZipCode;
    }
    /**
     * Retrieves the ZipCode property.
     *
     * @return
     *  String containing the ZipCode property.
     */
    public String getZipCode(){
        return mZipCode;
    }


    /**
     * Sets the Quantity property.
     *
     * @param pQuantity
     *  int to use to update the property.
     */
    public void setQuantity(int pQuantity){
        this.mQuantity = pQuantity;
    }
    /**
     * Retrieves the Quantity property.
     *
     * @return
     *  int containing the Quantity property.
     */
    public int getQuantity(){
        return mQuantity;
    }


    /**
     * Sets the OpenQuantity property.
     *
     * @param pOpenQuantity
     *  int to use to update the property.
     */
    public void setOpenQuantity(int pOpenQuantity){
        this.mOpenQuantity = pOpenQuantity;
    }
    /**
     * Retrieves the OpenQuantity property.
     *
     * @return
     *  int containing the OpenQuantity property.
     */
    public int getOpenQuantity(){
        return mOpenQuantity;
    }


    /**
     * Sets the OpenCost property.
     *
     * @param pOpenCost
     *  BigDecimal to use to update the property.
     */
    public void setOpenCost(BigDecimal pOpenCost){
        this.mOpenCost = pOpenCost;
    }
    /**
     * Retrieves the OpenCost property.
     *
     * @return
     *  BigDecimal containing the OpenCost property.
     */
    public BigDecimal getOpenCost(){
        return mOpenCost;
    }


    /**
     * Sets the OpenPrice property.
     *
     * @param pOpenPrice
     *  BigDecimal to use to update the property.
     */
    public void setOpenPrice(BigDecimal pOpenPrice){
        this.mOpenPrice = pOpenPrice;
    }
    /**
     * Retrieves the OpenPrice property.
     *
     * @return
     *  BigDecimal containing the OpenPrice property.
     */
    public BigDecimal getOpenPrice(){
        return mOpenPrice;
    }


    /**
     * Sets the UnitPrice property.
     *
     * @param pUnitPrice
     *  BigDecimal to use to update the property.
     */
    public void setUnitPrice(BigDecimal pUnitPrice){
        this.mUnitPrice = pUnitPrice;
    }
    /**
     * Retrieves the UnitPrice property.
     *
     * @return
     *  BigDecimal containing the UnitPrice property.
     */
    public BigDecimal getUnitPrice(){
        return mUnitPrice;
    }


    /**
     * Sets the DeliveryDate property.
     *
     * @param pDeliveryDate
     *  Date to use to update the property.
     */
    public void setDeliveryDate(Date pDeliveryDate){
        this.mDeliveryDate = pDeliveryDate;
    }
    /**
     * Retrieves the DeliveryDate property.
     *
     * @return
     *  Date containing the DeliveryDate property.
     */
    public Date getDeliveryDate(){
        return mDeliveryDate;
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
     * Sets the OrderTime property.
     *
     * @param pOrderTime
     *  Date to use to update the property.
     */
    public void setOrderTime(Date pOrderTime){
        this.mOrderTime = pOrderTime;
    }
    /**
     * Retrieves the OrderTime property.
     *
     * @return
     *  Date containing the OrderTime property.
     */
    public Date getOrderTime(){
        return mOrderTime;
    }


    /**
     * Sets the VendorId property.
     *
     * @param pVendorId
     *  int to use to update the property.
     */
    public void setVendorId(int pVendorId){
        this.mVendorId = pVendorId;
    }
    /**
     * Retrieves the VendorId property.
     *
     * @return
     *  int containing the VendorId property.
     */
    public int getVendorId(){
        return mVendorId;
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
     * Sets the ScheduleId property.
     *
     * @param pScheduleId
     *  int to use to update the property.
     */
    public void setScheduleId(int pScheduleId){
        this.mScheduleId = pScheduleId;
    }
    /**
     * Retrieves the ScheduleId property.
     *
     * @return
     *  int containing the ScheduleId property.
     */
    public int getScheduleId(){
        return mScheduleId;
    }


    /**
     * Sets the SiteSchedule property.
     *
     * @param pSiteSchedule
     *  String to use to update the property.
     */
    public void setSiteSchedule(String pSiteSchedule){
        this.mSiteSchedule = pSiteSchedule;
    }
    /**
     * Retrieves the SiteSchedule property.
     *
     * @return
     *  String containing the SiteSchedule property.
     */
    public String getSiteSchedule(){
        return mSiteSchedule;
    }


    /**
     * Sets the ExsistingVendorInvoicesAgainstPo property.
     *
     * @param pExsistingVendorInvoicesAgainstPo
     *  java.util.List to use to update the property.
     */
    public void setExsistingVendorInvoicesAgainstPo(java.util.List pExsistingVendorInvoicesAgainstPo){
        this.mExsistingVendorInvoicesAgainstPo = pExsistingVendorInvoicesAgainstPo;
    }
    /**
     * Retrieves the ExsistingVendorInvoicesAgainstPo property.
     *
     * @return
     *  java.util.List containing the ExsistingVendorInvoicesAgainstPo property.
     */
    public java.util.List getExsistingVendorInvoicesAgainstPo(){
        return mExsistingVendorInvoicesAgainstPo;
    }


    /**
     * Sets the BillingOnlyOrder property.
     *
     * @param pBillingOnlyOrder
     *  String to use to update the property.
     */
    public void setBillingOnlyOrder(String pBillingOnlyOrder){
        this.mBillingOnlyOrder = pBillingOnlyOrder;
    }
    /**
     * Retrieves the BillingOnlyOrder property.
     *
     * @return
     *  String containing the BillingOnlyOrder property.
     */
    public String getBillingOnlyOrder(){
        return mBillingOnlyOrder;
    }


    /**
     * Sets the OpenLineStatus property.
     *
     * @param pOpenLineStatus
     *  String to use to update the property.
     */
    public void setOpenLineStatus(String pOpenLineStatus){
        this.mOpenLineStatus = pOpenLineStatus;
    }
    /**
     * Retrieves the OpenLineStatus property.
     *
     * @return
     *  String containing the OpenLineStatus property.
     */
    public String getOpenLineStatus(){
        return mOpenLineStatus;
    }


    /**
     * Sets the OrderStatusCd property.
     *
     * @param pOrderStatusCd
     *  String to use to update the property.
     */
    public void setOrderStatusCd(String pOrderStatusCd){
        this.mOrderStatusCd = pOrderStatusCd;
    }
    /**
     * Retrieves the OrderStatusCd property.
     *
     * @return
     *  String containing the OrderStatusCd property.
     */
    public String getOrderStatusCd(){
        return mOrderStatusCd;
    }


    /**
     * Sets the OrderId property.
     *
     * @param pOrderId
     *  int to use to update the property.
     */
    public void setOrderId(int pOrderId){
        this.mOrderId = pOrderId;
    }
    /**
     * Retrieves the OrderId property.
     *
     * @return
     *  int containing the OrderId property.
     */
    public int getOrderId(){
        return mOrderId;
    }


    /**
     * Sets the ApprovedDate property.
     *
     * @param pApprovedDate
     *  java.util.Date to use to update the property.
     */
    public void setApprovedDate(java.util.Date pApprovedDate){
        this.mApprovedDate = pApprovedDate;
    }
    /**
     * Retrieves the ApprovedDate property.
     *
     * @return
     *  java.util.Date containing the ApprovedDate property.
     */
    public java.util.Date getApprovedDate(){
        return mApprovedDate;
    }


    /**
     * Sets the OrderSiteName property.
     *
     * @param pOrderSiteName
     *  String to use to update the property.
     */
    public void setOrderSiteName(String pOrderSiteName){
        this.mOrderSiteName = pOrderSiteName;
    }
    /**
     * Retrieves the OrderSiteName property.
     *
     * @return
     *  String containing the OrderSiteName property.
     */
    public String getOrderSiteName(){
        return mOrderSiteName;
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
     * Sets the OrderItemStatusCd property.
     *
     * @param pOrderItemStatusCd
     *  String to use to update the property.
     */
    public void setOrderItemStatusCd(String pOrderItemStatusCd){
        this.mOrderItemStatusCd = pOrderItemStatusCd;
    }
    /**
     * Retrieves the OrderItemStatusCd property.
     *
     * @return
     *  String containing the OrderItemStatusCd property.
     */
    public String getOrderItemStatusCd(){
        return mOrderItemStatusCd;
    }


    /**
     * Sets the FreightHandler property.
     *
     * @param pFreightHandler
     *  String to use to update the property.
     */
    public void setFreightHandler(String pFreightHandler){
        this.mFreightHandler = pFreightHandler;
    }
    /**
     * Retrieves the FreightHandler property.
     *
     * @return
     *  String containing the FreightHandler property.
     */
    public String getFreightHandler(){
        return mFreightHandler;
    }


    /**
     * Sets the OrderItemId property.
     *
     * @param pOrderItemId
     *  int to use to update the property.
     */
    public void setOrderItemId(int pOrderItemId){
        this.mOrderItemId = pOrderItemId;
    }
    /**
     * Retrieves the OrderItemId property.
     *
     * @return
     *  int containing the OrderItemId property.
     */
    public int getOrderItemId(){
        return mOrderItemId;
    }


    /**
     * Sets the SiteRank property.
     *
     * @param pSiteRank
     *  int to use to update the property.
     */
    public void setSiteRank(int pSiteRank){
        this.mSiteRank = pSiteRank;
    }
    /**
     * Retrieves the SiteRank property.
     *
     * @return
     *  int containing the SiteRank property.
     */
    public int getSiteRank(){
        return mSiteRank;
    }


    /**
     * Sets the DistUomConvCost property.
     *
     * @param pDistUomConvCost
     *  BigDecimal to use to update the property.
     */
    public void setDistUomConvCost(BigDecimal pDistUomConvCost){
        this.mDistUomConvCost = pDistUomConvCost;
    }
    /**
     * Retrieves the DistUomConvCost property.
     *
     * @return
     *  BigDecimal containing the DistUomConvCost property.
     */
    public BigDecimal getDistUomConvCost(){
        return mDistUomConvCost;
    }


    /**
     * Sets the DistUomConvQty property.
     *
     * @param pDistUomConvQty
     *  int to use to update the property.
     */
    public void setDistUomConvQty(int pDistUomConvQty){
        this.mDistUomConvQty = pDistUomConvQty;
    }
    /**
     * Retrieves the DistUomConvQty property.
     *
     * @return
     *  int containing the DistUomConvQty property.
     */
    public int getDistUomConvQty(){
        return mDistUomConvQty;
    }


    /**
     * Sets the DistUomConvOpenQty property.
     *
     * @param pDistUomConvOpenQty
     *  int to use to update the property.
     */
    public void setDistUomConvOpenQty(int pDistUomConvOpenQty){
        this.mDistUomConvOpenQty = pDistUomConvOpenQty;
    }
    /**
     * Retrieves the DistUomConvOpenQty property.
     *
     * @return
     *  int containing the DistUomConvOpenQty property.
     */
    public int getDistUomConvOpenQty(){
        return mDistUomConvOpenQty;
    }


    /**
     * Sets the OutboundPoNum property.
     *
     * @param pOutboundPoNum
     *  String to use to update the property.
     */
    public void setOutboundPoNum(String pOutboundPoNum){
        this.mOutboundPoNum = pOutboundPoNum;
    }
    /**
     * Retrieves the OutboundPoNum property.
     *
     * @return
     *  String containing the OutboundPoNum property.
     */
    public String getOutboundPoNum(){
        return mOutboundPoNum;
    }


    
}
