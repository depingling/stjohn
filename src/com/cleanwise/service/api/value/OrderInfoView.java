
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderInfoView
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
 * <code>OrderInfoView</code> is a ViewObject class for UI.
 */
public class OrderInfoView
extends ValueObject
{
   
    private static final long serialVersionUID = -8927898769886029609L;
    private int mOrderId;
    private int mStoreId;
    private int mAccountId;
    private int mSiteId;
    private String mOrderNum;
    private Date mOrderDate;
    private String mPoNumber;
    private String mSource;
    private String mContactName;
    private String mContactPhone;
    private String mContactEmail;
    private String mPlacedBy;
    private String mComments;
    private BigDecimal mSubtotal;
    private BigDecimal mFreight;
    private BigDecimal mMiscCharge;
    private BigDecimal mTax;
    private BigDecimal mToatal;
    private int mRefOrderId;
    private String mRefOrderNum;
    private String mOrderStatusCd;
    private String mOrderTypeCd;
    private String mInternalComments;
    private String mLocaleCd;
    private String mOrderSiteName;
    private BigDecimal mDiscount;

    /**
     * Constructor.
     */
    public OrderInfoView ()
    {
        mOrderNum = "";
        mPoNumber = "";
        mSource = "";
        mContactName = "";
        mContactPhone = "";
        mContactEmail = "";
        mPlacedBy = "";
        mComments = "";
        mRefOrderNum = "";
        mOrderStatusCd = "";
        mOrderTypeCd = "";
        mInternalComments = "";
        mLocaleCd = "";
        mOrderSiteName = "";
    }

    /**
     * Constructor. 
     */
    public OrderInfoView(int parm1, int parm2, int parm3, int parm4, String parm5, Date parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13, BigDecimal parm14, BigDecimal parm15, BigDecimal parm16, BigDecimal parm17, BigDecimal parm18, int parm19, String parm20, String parm21, String parm22, String parm23, String parm24, String parm25, BigDecimal parm26)
    {
        mOrderId = parm1;
        mStoreId = parm2;
        mAccountId = parm3;
        mSiteId = parm4;
        mOrderNum = parm5;
        mOrderDate = parm6;
        mPoNumber = parm7;
        mSource = parm8;
        mContactName = parm9;
        mContactPhone = parm10;
        mContactEmail = parm11;
        mPlacedBy = parm12;
        mComments = parm13;
        mSubtotal = parm14;
        mFreight = parm15;
        mMiscCharge = parm16;
        mTax = parm17;
        mToatal = parm18;
        mRefOrderId = parm19;
        mRefOrderNum = parm20;
        mOrderStatusCd = parm21;
        mOrderTypeCd = parm22;
        mInternalComments = parm23;
        mLocaleCd = parm24;
        mOrderSiteName = parm25;
        mDiscount = parm26;
        
    }

    /**
     * Creates a new OrderInfoView
     *
     * @return
     *  Newly initialized OrderInfoView object.
     */
    public static OrderInfoView createValue () 
    {
        OrderInfoView valueView = new OrderInfoView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderInfoView object
     */
    public String toString()
    {
        return "[" + "OrderId=" + mOrderId + ", StoreId=" + mStoreId + ", AccountId=" + mAccountId + ", SiteId=" + mSiteId + ", OrderNum=" + mOrderNum + ", OrderDate=" + mOrderDate + ", PoNumber=" + mPoNumber + ", Source=" + mSource + ", ContactName=" + mContactName + ", ContactPhone=" + mContactPhone + ", ContactEmail=" + mContactEmail + ", PlacedBy=" + mPlacedBy + ", Comments=" + mComments + ", Subtotal=" + mSubtotal + ", Freight=" + mFreight + ", MiscCharge=" + mMiscCharge + ", Tax=" + mTax + ", Toatal=" + mToatal + ", RefOrderId=" + mRefOrderId + ", RefOrderNum=" + mRefOrderNum + ", OrderStatusCd=" + mOrderStatusCd + ", OrderTypeCd=" + mOrderTypeCd + ", InternalComments=" + mInternalComments + ", LocaleCd=" + mLocaleCd + ", OrderSiteName=" + mOrderSiteName + ", Discount=" + mDiscount + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("OrderInfo");
	root.setAttribute("Id", String.valueOf(mOrderId));

	Element node;

        node = doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        node = doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node = doc.createElement("SiteId");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteId)));
        root.appendChild(node);

        node = doc.createElement("OrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderNum)));
        root.appendChild(node);

        node = doc.createElement("OrderDate");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderDate)));
        root.appendChild(node);

        node = doc.createElement("PoNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mPoNumber)));
        root.appendChild(node);

        node = doc.createElement("Source");
        node.appendChild(doc.createTextNode(String.valueOf(mSource)));
        root.appendChild(node);

        node = doc.createElement("ContactName");
        node.appendChild(doc.createTextNode(String.valueOf(mContactName)));
        root.appendChild(node);

        node = doc.createElement("ContactPhone");
        node.appendChild(doc.createTextNode(String.valueOf(mContactPhone)));
        root.appendChild(node);

        node = doc.createElement("ContactEmail");
        node.appendChild(doc.createTextNode(String.valueOf(mContactEmail)));
        root.appendChild(node);

        node = doc.createElement("PlacedBy");
        node.appendChild(doc.createTextNode(String.valueOf(mPlacedBy)));
        root.appendChild(node);

        node = doc.createElement("Comments");
        node.appendChild(doc.createTextNode(String.valueOf(mComments)));
        root.appendChild(node);

        node = doc.createElement("Subtotal");
        node.appendChild(doc.createTextNode(String.valueOf(mSubtotal)));
        root.appendChild(node);

        node = doc.createElement("Freight");
        node.appendChild(doc.createTextNode(String.valueOf(mFreight)));
        root.appendChild(node);

        node = doc.createElement("MiscCharge");
        node.appendChild(doc.createTextNode(String.valueOf(mMiscCharge)));
        root.appendChild(node);

        node = doc.createElement("Tax");
        node.appendChild(doc.createTextNode(String.valueOf(mTax)));
        root.appendChild(node);

        node = doc.createElement("Toatal");
        node.appendChild(doc.createTextNode(String.valueOf(mToatal)));
        root.appendChild(node);

        node = doc.createElement("RefOrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mRefOrderId)));
        root.appendChild(node);

        node = doc.createElement("RefOrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mRefOrderNum)));
        root.appendChild(node);

        node = doc.createElement("OrderStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderStatusCd)));
        root.appendChild(node);

        node = doc.createElement("OrderTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderTypeCd)));
        root.appendChild(node);

        node = doc.createElement("InternalComments");
        node.appendChild(doc.createTextNode(String.valueOf(mInternalComments)));
        root.appendChild(node);

        node = doc.createElement("LocaleCd");
        node.appendChild(doc.createTextNode(String.valueOf(mLocaleCd)));
        root.appendChild(node);

        node = doc.createElement("OrderSiteName");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderSiteName)));
        root.appendChild(node);

        node = doc.createElement("Discount");
        node.appendChild(doc.createTextNode(String.valueOf(mDiscount)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public OrderInfoView copy()  {
      OrderInfoView obj = new OrderInfoView();
      obj.setOrderId(mOrderId);
      obj.setStoreId(mStoreId);
      obj.setAccountId(mAccountId);
      obj.setSiteId(mSiteId);
      obj.setOrderNum(mOrderNum);
      obj.setOrderDate(mOrderDate);
      obj.setPoNumber(mPoNumber);
      obj.setSource(mSource);
      obj.setContactName(mContactName);
      obj.setContactPhone(mContactPhone);
      obj.setContactEmail(mContactEmail);
      obj.setPlacedBy(mPlacedBy);
      obj.setComments(mComments);
      obj.setSubtotal(mSubtotal);
      obj.setFreight(mFreight);
      obj.setMiscCharge(mMiscCharge);
      obj.setTax(mTax);
      obj.setToatal(mToatal);
      obj.setRefOrderId(mRefOrderId);
      obj.setRefOrderNum(mRefOrderNum);
      obj.setOrderStatusCd(mOrderStatusCd);
      obj.setOrderTypeCd(mOrderTypeCd);
      obj.setInternalComments(mInternalComments);
      obj.setLocaleCd(mLocaleCd);
      obj.setOrderSiteName(mOrderSiteName);
      obj.setDiscount(mDiscount);
      
      return obj;
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
     * Sets the OrderNum property.
     *
     * @param pOrderNum
     *  String to use to update the property.
     */
    public void setOrderNum(String pOrderNum){
        this.mOrderNum = pOrderNum;
    }
    /**
     * Retrieves the OrderNum property.
     *
     * @return
     *  String containing the OrderNum property.
     */
    public String getOrderNum(){
        return mOrderNum;
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
     * Sets the Source property.
     *
     * @param pSource
     *  String to use to update the property.
     */
    public void setSource(String pSource){
        this.mSource = pSource;
    }
    /**
     * Retrieves the Source property.
     *
     * @return
     *  String containing the Source property.
     */
    public String getSource(){
        return mSource;
    }


    /**
     * Sets the ContactName property.
     *
     * @param pContactName
     *  String to use to update the property.
     */
    public void setContactName(String pContactName){
        this.mContactName = pContactName;
    }
    /**
     * Retrieves the ContactName property.
     *
     * @return
     *  String containing the ContactName property.
     */
    public String getContactName(){
        return mContactName;
    }


    /**
     * Sets the ContactPhone property.
     *
     * @param pContactPhone
     *  String to use to update the property.
     */
    public void setContactPhone(String pContactPhone){
        this.mContactPhone = pContactPhone;
    }
    /**
     * Retrieves the ContactPhone property.
     *
     * @return
     *  String containing the ContactPhone property.
     */
    public String getContactPhone(){
        return mContactPhone;
    }


    /**
     * Sets the ContactEmail property.
     *
     * @param pContactEmail
     *  String to use to update the property.
     */
    public void setContactEmail(String pContactEmail){
        this.mContactEmail = pContactEmail;
    }
    /**
     * Retrieves the ContactEmail property.
     *
     * @return
     *  String containing the ContactEmail property.
     */
    public String getContactEmail(){
        return mContactEmail;
    }


    /**
     * Sets the PlacedBy property.
     *
     * @param pPlacedBy
     *  String to use to update the property.
     */
    public void setPlacedBy(String pPlacedBy){
        this.mPlacedBy = pPlacedBy;
    }
    /**
     * Retrieves the PlacedBy property.
     *
     * @return
     *  String containing the PlacedBy property.
     */
    public String getPlacedBy(){
        return mPlacedBy;
    }


    /**
     * Sets the Comments property.
     *
     * @param pComments
     *  String to use to update the property.
     */
    public void setComments(String pComments){
        this.mComments = pComments;
    }
    /**
     * Retrieves the Comments property.
     *
     * @return
     *  String containing the Comments property.
     */
    public String getComments(){
        return mComments;
    }


    /**
     * Sets the Subtotal property.
     *
     * @param pSubtotal
     *  BigDecimal to use to update the property.
     */
    public void setSubtotal(BigDecimal pSubtotal){
        this.mSubtotal = pSubtotal;
    }
    /**
     * Retrieves the Subtotal property.
     *
     * @return
     *  BigDecimal containing the Subtotal property.
     */
    public BigDecimal getSubtotal(){
        return mSubtotal;
    }


    /**
     * Sets the Freight property.
     *
     * @param pFreight
     *  BigDecimal to use to update the property.
     */
    public void setFreight(BigDecimal pFreight){
        this.mFreight = pFreight;
    }
    /**
     * Retrieves the Freight property.
     *
     * @return
     *  BigDecimal containing the Freight property.
     */
    public BigDecimal getFreight(){
        return mFreight;
    }


    /**
     * Sets the MiscCharge property.
     *
     * @param pMiscCharge
     *  BigDecimal to use to update the property.
     */
    public void setMiscCharge(BigDecimal pMiscCharge){
        this.mMiscCharge = pMiscCharge;
    }
    /**
     * Retrieves the MiscCharge property.
     *
     * @return
     *  BigDecimal containing the MiscCharge property.
     */
    public BigDecimal getMiscCharge(){
        return mMiscCharge;
    }


    /**
     * Sets the Tax property.
     *
     * @param pTax
     *  BigDecimal to use to update the property.
     */
    public void setTax(BigDecimal pTax){
        this.mTax = pTax;
    }
    /**
     * Retrieves the Tax property.
     *
     * @return
     *  BigDecimal containing the Tax property.
     */
    public BigDecimal getTax(){
        return mTax;
    }


    /**
     * Sets the Toatal property.
     *
     * @param pToatal
     *  BigDecimal to use to update the property.
     */
    public void setToatal(BigDecimal pToatal){
        this.mToatal = pToatal;
    }
    /**
     * Retrieves the Toatal property.
     *
     * @return
     *  BigDecimal containing the Toatal property.
     */
    public BigDecimal getToatal(){
        return mToatal;
    }


    /**
     * Sets the RefOrderId property.
     *
     * @param pRefOrderId
     *  int to use to update the property.
     */
    public void setRefOrderId(int pRefOrderId){
        this.mRefOrderId = pRefOrderId;
    }
    /**
     * Retrieves the RefOrderId property.
     *
     * @return
     *  int containing the RefOrderId property.
     */
    public int getRefOrderId(){
        return mRefOrderId;
    }


    /**
     * Sets the RefOrderNum property.
     *
     * @param pRefOrderNum
     *  String to use to update the property.
     */
    public void setRefOrderNum(String pRefOrderNum){
        this.mRefOrderNum = pRefOrderNum;
    }
    /**
     * Retrieves the RefOrderNum property.
     *
     * @return
     *  String containing the RefOrderNum property.
     */
    public String getRefOrderNum(){
        return mRefOrderNum;
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
     * Sets the OrderTypeCd property.
     *
     * @param pOrderTypeCd
     *  String to use to update the property.
     */
    public void setOrderTypeCd(String pOrderTypeCd){
        this.mOrderTypeCd = pOrderTypeCd;
    }
    /**
     * Retrieves the OrderTypeCd property.
     *
     * @return
     *  String containing the OrderTypeCd property.
     */
    public String getOrderTypeCd(){
        return mOrderTypeCd;
    }


    /**
     * Sets the InternalComments property.
     *
     * @param pInternalComments
     *  String to use to update the property.
     */
    public void setInternalComments(String pInternalComments){
        this.mInternalComments = pInternalComments;
    }
    /**
     * Retrieves the InternalComments property.
     *
     * @return
     *  String containing the InternalComments property.
     */
    public String getInternalComments(){
        return mInternalComments;
    }


    /**
     * Sets the LocaleCd property.
     *
     * @param pLocaleCd
     *  String to use to update the property.
     */
    public void setLocaleCd(String pLocaleCd){
        this.mLocaleCd = pLocaleCd;
    }
    /**
     * Retrieves the LocaleCd property.
     *
     * @return
     *  String containing the LocaleCd property.
     */
    public String getLocaleCd(){
        return mLocaleCd;
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
     * Sets the Discount property.
     *
     * @param pDiscount
     *  BigDecimal to use to update the property.
     */
    public void setDiscount(BigDecimal pDiscount){
        this.mDiscount = pDiscount;
    }
    /**
     * Retrieves the Discount property.
     *
     * @return
     *  BigDecimal containing the Discount property.
     */
    public BigDecimal getDiscount(){
        return mDiscount;
    }


    
}
