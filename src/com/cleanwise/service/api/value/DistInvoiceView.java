
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        DistInvoiceView
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
 * <code>DistInvoiceView</code> is a ViewObject class for UI.
 */
public class DistInvoiceView
extends ValueObject
{
   
    private static final long serialVersionUID = -4200021606190762388L;
    private String mCustomer;
    private String mCustName;
    private int mShipTo;
    private String mShipToAddr;
    private int mOrderNbr;
    private java.util.Date mOrderDate;
    private String mVendor;
    private String mVenName;
    private String mPoNumber;
    private String mVenInvoiceNum;
    private java.util.Date mVenInvoiceDate;
    private BigDecimal mVenTotalCost;
    private BigDecimal mVenGoodsCost;
    private BigDecimal mOrderGoodsPrice;
    private BigDecimal mVenAdditionalCharges;
    private BigDecimal mVenTax;
    private String mVenItems;
    private int mCatalogId;
    private int mContractId;
    private String mContractName;
    private BigDecimal mContractPrice;
    private BigDecimal mMargin;
    private BigDecimal mMarginPr;

    /**
     * Constructor.
     */
    public DistInvoiceView ()
    {
        mCustomer = "";
        mCustName = "";
        mShipToAddr = "";
        mVendor = "";
        mVenName = "";
        mPoNumber = "";
        mVenInvoiceNum = "";
        mVenItems = "";
        mContractName = "";
    }

    /**
     * Constructor. 
     */
    public DistInvoiceView(String parm1, String parm2, int parm3, String parm4, int parm5, java.util.Date parm6, String parm7, String parm8, String parm9, String parm10, java.util.Date parm11, BigDecimal parm12, BigDecimal parm13, BigDecimal parm14, BigDecimal parm15, BigDecimal parm16, String parm17, int parm18, int parm19, String parm20, BigDecimal parm21, BigDecimal parm22, BigDecimal parm23)
    {
        mCustomer = parm1;
        mCustName = parm2;
        mShipTo = parm3;
        mShipToAddr = parm4;
        mOrderNbr = parm5;
        mOrderDate = parm6;
        mVendor = parm7;
        mVenName = parm8;
        mPoNumber = parm9;
        mVenInvoiceNum = parm10;
        mVenInvoiceDate = parm11;
        mVenTotalCost = parm12;
        mVenGoodsCost = parm13;
        mOrderGoodsPrice = parm14;
        mVenAdditionalCharges = parm15;
        mVenTax = parm16;
        mVenItems = parm17;
        mCatalogId = parm18;
        mContractId = parm19;
        mContractName = parm20;
        mContractPrice = parm21;
        mMargin = parm22;
        mMarginPr = parm23;
        
    }

    /**
     * Creates a new DistInvoiceView
     *
     * @return
     *  Newly initialized DistInvoiceView object.
     */
    public static DistInvoiceView createValue () 
    {
        DistInvoiceView valueView = new DistInvoiceView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this DistInvoiceView object
     */
    public String toString()
    {
        return "[" + "Customer=" + mCustomer + ", CustName=" + mCustName + ", ShipTo=" + mShipTo + ", ShipToAddr=" + mShipToAddr + ", OrderNbr=" + mOrderNbr + ", OrderDate=" + mOrderDate + ", Vendor=" + mVendor + ", VenName=" + mVenName + ", PoNumber=" + mPoNumber + ", VenInvoiceNum=" + mVenInvoiceNum + ", VenInvoiceDate=" + mVenInvoiceDate + ", VenTotalCost=" + mVenTotalCost + ", VenGoodsCost=" + mVenGoodsCost + ", OrderGoodsPrice=" + mOrderGoodsPrice + ", VenAdditionalCharges=" + mVenAdditionalCharges + ", VenTax=" + mVenTax + ", VenItems=" + mVenItems + ", CatalogId=" + mCatalogId + ", ContractId=" + mContractId + ", ContractName=" + mContractName + ", ContractPrice=" + mContractPrice + ", Margin=" + mMargin + ", MarginPr=" + mMarginPr + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("DistInvoice");
	root.setAttribute("Id", String.valueOf(mCustomer));

	Element node;

        node = doc.createElement("CustName");
        node.appendChild(doc.createTextNode(String.valueOf(mCustName)));
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

        node = doc.createElement("OrderGoodsPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderGoodsPrice)));
        root.appendChild(node);

        node = doc.createElement("VenAdditionalCharges");
        node.appendChild(doc.createTextNode(String.valueOf(mVenAdditionalCharges)));
        root.appendChild(node);

        node = doc.createElement("VenTax");
        node.appendChild(doc.createTextNode(String.valueOf(mVenTax)));
        root.appendChild(node);

        node = doc.createElement("VenItems");
        node.appendChild(doc.createTextNode(String.valueOf(mVenItems)));
        root.appendChild(node);

        node = doc.createElement("CatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogId)));
        root.appendChild(node);

        node = doc.createElement("ContractId");
        node.appendChild(doc.createTextNode(String.valueOf(mContractId)));
        root.appendChild(node);

        node = doc.createElement("ContractName");
        node.appendChild(doc.createTextNode(String.valueOf(mContractName)));
        root.appendChild(node);

        node = doc.createElement("ContractPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mContractPrice)));
        root.appendChild(node);

        node = doc.createElement("Margin");
        node.appendChild(doc.createTextNode(String.valueOf(mMargin)));
        root.appendChild(node);

        node = doc.createElement("MarginPr");
        node.appendChild(doc.createTextNode(String.valueOf(mMarginPr)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public DistInvoiceView copy()  {
      DistInvoiceView obj = new DistInvoiceView();
      obj.setCustomer(mCustomer);
      obj.setCustName(mCustName);
      obj.setShipTo(mShipTo);
      obj.setShipToAddr(mShipToAddr);
      obj.setOrderNbr(mOrderNbr);
      obj.setOrderDate(mOrderDate);
      obj.setVendor(mVendor);
      obj.setVenName(mVenName);
      obj.setPoNumber(mPoNumber);
      obj.setVenInvoiceNum(mVenInvoiceNum);
      obj.setVenInvoiceDate(mVenInvoiceDate);
      obj.setVenTotalCost(mVenTotalCost);
      obj.setVenGoodsCost(mVenGoodsCost);
      obj.setOrderGoodsPrice(mOrderGoodsPrice);
      obj.setVenAdditionalCharges(mVenAdditionalCharges);
      obj.setVenTax(mVenTax);
      obj.setVenItems(mVenItems);
      obj.setCatalogId(mCatalogId);
      obj.setContractId(mContractId);
      obj.setContractName(mContractName);
      obj.setContractPrice(mContractPrice);
      obj.setMargin(mMargin);
      obj.setMarginPr(mMarginPr);
      
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
     * Sets the OrderGoodsPrice property.
     *
     * @param pOrderGoodsPrice
     *  BigDecimal to use to update the property.
     */
    public void setOrderGoodsPrice(BigDecimal pOrderGoodsPrice){
        this.mOrderGoodsPrice = pOrderGoodsPrice;
    }
    /**
     * Retrieves the OrderGoodsPrice property.
     *
     * @return
     *  BigDecimal containing the OrderGoodsPrice property.
     */
    public BigDecimal getOrderGoodsPrice(){
        return mOrderGoodsPrice;
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
     * Sets the ContractId property.
     *
     * @param pContractId
     *  int to use to update the property.
     */
    public void setContractId(int pContractId){
        this.mContractId = pContractId;
    }
    /**
     * Retrieves the ContractId property.
     *
     * @return
     *  int containing the ContractId property.
     */
    public int getContractId(){
        return mContractId;
    }


    /**
     * Sets the ContractName property.
     *
     * @param pContractName
     *  String to use to update the property.
     */
    public void setContractName(String pContractName){
        this.mContractName = pContractName;
    }
    /**
     * Retrieves the ContractName property.
     *
     * @return
     *  String containing the ContractName property.
     */
    public String getContractName(){
        return mContractName;
    }


    /**
     * Sets the ContractPrice property.
     *
     * @param pContractPrice
     *  BigDecimal to use to update the property.
     */
    public void setContractPrice(BigDecimal pContractPrice){
        this.mContractPrice = pContractPrice;
    }
    /**
     * Retrieves the ContractPrice property.
     *
     * @return
     *  BigDecimal containing the ContractPrice property.
     */
    public BigDecimal getContractPrice(){
        return mContractPrice;
    }


    /**
     * Sets the Margin property.
     *
     * @param pMargin
     *  BigDecimal to use to update the property.
     */
    public void setMargin(BigDecimal pMargin){
        this.mMargin = pMargin;
    }
    /**
     * Retrieves the Margin property.
     *
     * @return
     *  BigDecimal containing the Margin property.
     */
    public BigDecimal getMargin(){
        return mMargin;
    }


    /**
     * Sets the MarginPr property.
     *
     * @param pMarginPr
     *  BigDecimal to use to update the property.
     */
    public void setMarginPr(BigDecimal pMarginPr){
        this.mMarginPr = pMarginPr;
    }
    /**
     * Retrieves the MarginPr property.
     *
     * @return
     *  BigDecimal containing the MarginPr property.
     */
    public BigDecimal getMarginPr(){
        return mMarginPr;
    }


    
}
