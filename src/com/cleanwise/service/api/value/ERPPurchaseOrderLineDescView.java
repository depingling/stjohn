
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ERPPurchaseOrderLineDescView
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
 * <code>ERPPurchaseOrderLineDescView</code> is a ViewObject class for UI.
 */
public class ERPPurchaseOrderLineDescView
extends ValueObject
{
   
    private static final long serialVersionUID = 1084524138653406207L;
    private PurchaseOrderData mPurchaseOrderData;
    private OrderItemData mOrderItemData;
    private String mErpPoNum;
    private String mErpOrderNum;
    private String mErpOrderRefNum;
    private String mErpSkuNum;
    private int mErpPoLineNum;
    private int mErpOrderedQty;
    private int mErpShippedQty;
    private int mErpOpenQty;
    private BigDecimal mErpOpenAmount;
    private String mDistributorName;
    private String mAccountName;
    private String mErpItemDescription;
    private java.util.Date mErpPoDate;
    private BigDecimal mErpUnitCost;
    private OrderPropertyDataVector mOrderItemNotes;
    private boolean mHasNote;
    private String mOpenLineStatusCd;
    private String mShipCity;
    private String mShipState;
    private String mShipPostalCode;
    private String mShipCountry;

    /**
     * Constructor.
     */
    public ERPPurchaseOrderLineDescView ()
    {
        mErpPoNum = "";
        mErpOrderNum = "";
        mErpOrderRefNum = "";
        mErpSkuNum = "";
        mDistributorName = "";
        mAccountName = "";
        mErpItemDescription = "";
        mOpenLineStatusCd = "";
        mShipCity = "";
        mShipState = "";
        mShipPostalCode = "";
        mShipCountry = "";
    }

    /**
     * Constructor. 
     */
    public ERPPurchaseOrderLineDescView(PurchaseOrderData parm1, OrderItemData parm2, String parm3, String parm4, String parm5, String parm6, int parm7, int parm8, int parm9, int parm10, BigDecimal parm11, String parm12, String parm13, String parm14, java.util.Date parm15, BigDecimal parm16, OrderPropertyDataVector parm17, boolean parm18, String parm19, String parm20, String parm21, String parm22, String parm23)
    {
        mPurchaseOrderData = parm1;
        mOrderItemData = parm2;
        mErpPoNum = parm3;
        mErpOrderNum = parm4;
        mErpOrderRefNum = parm5;
        mErpSkuNum = parm6;
        mErpPoLineNum = parm7;
        mErpOrderedQty = parm8;
        mErpShippedQty = parm9;
        mErpOpenQty = parm10;
        mErpOpenAmount = parm11;
        mDistributorName = parm12;
        mAccountName = parm13;
        mErpItemDescription = parm14;
        mErpPoDate = parm15;
        mErpUnitCost = parm16;
        mOrderItemNotes = parm17;
        mHasNote = parm18;
        mOpenLineStatusCd = parm19;
        mShipCity = parm20;
        mShipState = parm21;
        mShipPostalCode = parm22;
        mShipCountry = parm23;
        
    }

    /**
     * Creates a new ERPPurchaseOrderLineDescView
     *
     * @return
     *  Newly initialized ERPPurchaseOrderLineDescView object.
     */
    public static ERPPurchaseOrderLineDescView createValue () 
    {
        ERPPurchaseOrderLineDescView valueView = new ERPPurchaseOrderLineDescView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ERPPurchaseOrderLineDescView object
     */
    public String toString()
    {
        return "[" + "PurchaseOrderData=" + mPurchaseOrderData + ", OrderItemData=" + mOrderItemData + ", ErpPoNum=" + mErpPoNum + ", ErpOrderNum=" + mErpOrderNum + ", ErpOrderRefNum=" + mErpOrderRefNum + ", ErpSkuNum=" + mErpSkuNum + ", ErpPoLineNum=" + mErpPoLineNum + ", ErpOrderedQty=" + mErpOrderedQty + ", ErpShippedQty=" + mErpShippedQty + ", ErpOpenQty=" + mErpOpenQty + ", ErpOpenAmount=" + mErpOpenAmount + ", DistributorName=" + mDistributorName + ", AccountName=" + mAccountName + ", ErpItemDescription=" + mErpItemDescription + ", ErpPoDate=" + mErpPoDate + ", ErpUnitCost=" + mErpUnitCost + ", OrderItemNotes=" + mOrderItemNotes + ", HasNote=" + mHasNote + ", OpenLineStatusCd=" + mOpenLineStatusCd + ", ShipCity=" + mShipCity + ", ShipState=" + mShipState + ", ShipPostalCode=" + mShipPostalCode + ", ShipCountry=" + mShipCountry + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ERPPurchaseOrderLineDesc");
	root.setAttribute("Id", String.valueOf(mPurchaseOrderData));

	Element node;

        node = doc.createElement("OrderItemData");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderItemData)));
        root.appendChild(node);

        node = doc.createElement("ErpPoNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpPoNum)));
        root.appendChild(node);

        node = doc.createElement("ErpOrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpOrderNum)));
        root.appendChild(node);

        node = doc.createElement("ErpOrderRefNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpOrderRefNum)));
        root.appendChild(node);

        node = doc.createElement("ErpSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpSkuNum)));
        root.appendChild(node);

        node = doc.createElement("ErpPoLineNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpPoLineNum)));
        root.appendChild(node);

        node = doc.createElement("ErpOrderedQty");
        node.appendChild(doc.createTextNode(String.valueOf(mErpOrderedQty)));
        root.appendChild(node);

        node = doc.createElement("ErpShippedQty");
        node.appendChild(doc.createTextNode(String.valueOf(mErpShippedQty)));
        root.appendChild(node);

        node = doc.createElement("ErpOpenQty");
        node.appendChild(doc.createTextNode(String.valueOf(mErpOpenQty)));
        root.appendChild(node);

        node = doc.createElement("ErpOpenAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mErpOpenAmount)));
        root.appendChild(node);

        node = doc.createElement("DistributorName");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorName)));
        root.appendChild(node);

        node = doc.createElement("AccountName");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountName)));
        root.appendChild(node);

        node = doc.createElement("ErpItemDescription");
        node.appendChild(doc.createTextNode(String.valueOf(mErpItemDescription)));
        root.appendChild(node);

        node = doc.createElement("ErpPoDate");
        node.appendChild(doc.createTextNode(String.valueOf(mErpPoDate)));
        root.appendChild(node);

        node = doc.createElement("ErpUnitCost");
        node.appendChild(doc.createTextNode(String.valueOf(mErpUnitCost)));
        root.appendChild(node);

        node = doc.createElement("OrderItemNotes");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderItemNotes)));
        root.appendChild(node);

        node = doc.createElement("HasNote");
        node.appendChild(doc.createTextNode(String.valueOf(mHasNote)));
        root.appendChild(node);

        node = doc.createElement("OpenLineStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOpenLineStatusCd)));
        root.appendChild(node);

        node = doc.createElement("ShipCity");
        node.appendChild(doc.createTextNode(String.valueOf(mShipCity)));
        root.appendChild(node);

        node = doc.createElement("ShipState");
        node.appendChild(doc.createTextNode(String.valueOf(mShipState)));
        root.appendChild(node);

        node = doc.createElement("ShipPostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mShipPostalCode)));
        root.appendChild(node);

        node = doc.createElement("ShipCountry");
        node.appendChild(doc.createTextNode(String.valueOf(mShipCountry)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ERPPurchaseOrderLineDescView copy()  {
      ERPPurchaseOrderLineDescView obj = new ERPPurchaseOrderLineDescView();
      obj.setPurchaseOrderData(mPurchaseOrderData);
      obj.setOrderItemData(mOrderItemData);
      obj.setErpPoNum(mErpPoNum);
      obj.setErpOrderNum(mErpOrderNum);
      obj.setErpOrderRefNum(mErpOrderRefNum);
      obj.setErpSkuNum(mErpSkuNum);
      obj.setErpPoLineNum(mErpPoLineNum);
      obj.setErpOrderedQty(mErpOrderedQty);
      obj.setErpShippedQty(mErpShippedQty);
      obj.setErpOpenQty(mErpOpenQty);
      obj.setErpOpenAmount(mErpOpenAmount);
      obj.setDistributorName(mDistributorName);
      obj.setAccountName(mAccountName);
      obj.setErpItemDescription(mErpItemDescription);
      obj.setErpPoDate(mErpPoDate);
      obj.setErpUnitCost(mErpUnitCost);
      obj.setOrderItemNotes(mOrderItemNotes);
      obj.setHasNote(mHasNote);
      obj.setOpenLineStatusCd(mOpenLineStatusCd);
      obj.setShipCity(mShipCity);
      obj.setShipState(mShipState);
      obj.setShipPostalCode(mShipPostalCode);
      obj.setShipCountry(mShipCountry);
      
      return obj;
    }

    
    /**
     * Sets the PurchaseOrderData property.
     *
     * @param pPurchaseOrderData
     *  PurchaseOrderData to use to update the property.
     */
    public void setPurchaseOrderData(PurchaseOrderData pPurchaseOrderData){
        this.mPurchaseOrderData = pPurchaseOrderData;
    }
    /**
     * Retrieves the PurchaseOrderData property.
     *
     * @return
     *  PurchaseOrderData containing the PurchaseOrderData property.
     */
    public PurchaseOrderData getPurchaseOrderData(){
        return mPurchaseOrderData;
    }


    /**
     * Sets the OrderItemData property.
     *
     * @param pOrderItemData
     *  OrderItemData to use to update the property.
     */
    public void setOrderItemData(OrderItemData pOrderItemData){
        this.mOrderItemData = pOrderItemData;
    }
    /**
     * Retrieves the OrderItemData property.
     *
     * @return
     *  OrderItemData containing the OrderItemData property.
     */
    public OrderItemData getOrderItemData(){
        return mOrderItemData;
    }


    /**
     * Sets the ErpPoNum property.
     *
     * @param pErpPoNum
     *  String to use to update the property.
     */
    public void setErpPoNum(String pErpPoNum){
        this.mErpPoNum = pErpPoNum;
    }
    /**
     * Retrieves the ErpPoNum property.
     *
     * @return
     *  String containing the ErpPoNum property.
     */
    public String getErpPoNum(){
        return mErpPoNum;
    }


    /**
     * Sets the ErpOrderNum property.
     *
     * @param pErpOrderNum
     *  String to use to update the property.
     */
    public void setErpOrderNum(String pErpOrderNum){
        this.mErpOrderNum = pErpOrderNum;
    }
    /**
     * Retrieves the ErpOrderNum property.
     *
     * @return
     *  String containing the ErpOrderNum property.
     */
    public String getErpOrderNum(){
        return mErpOrderNum;
    }


    /**
     * Sets the ErpOrderRefNum property.
     *
     * @param pErpOrderRefNum
     *  String to use to update the property.
     */
    public void setErpOrderRefNum(String pErpOrderRefNum){
        this.mErpOrderRefNum = pErpOrderRefNum;
    }
    /**
     * Retrieves the ErpOrderRefNum property.
     *
     * @return
     *  String containing the ErpOrderRefNum property.
     */
    public String getErpOrderRefNum(){
        return mErpOrderRefNum;
    }


    /**
     * Sets the ErpSkuNum property.
     *
     * @param pErpSkuNum
     *  String to use to update the property.
     */
    public void setErpSkuNum(String pErpSkuNum){
        this.mErpSkuNum = pErpSkuNum;
    }
    /**
     * Retrieves the ErpSkuNum property.
     *
     * @return
     *  String containing the ErpSkuNum property.
     */
    public String getErpSkuNum(){
        return mErpSkuNum;
    }


    /**
     * Sets the ErpPoLineNum property.
     *
     * @param pErpPoLineNum
     *  int to use to update the property.
     */
    public void setErpPoLineNum(int pErpPoLineNum){
        this.mErpPoLineNum = pErpPoLineNum;
    }
    /**
     * Retrieves the ErpPoLineNum property.
     *
     * @return
     *  int containing the ErpPoLineNum property.
     */
    public int getErpPoLineNum(){
        return mErpPoLineNum;
    }


    /**
     * Sets the ErpOrderedQty property.
     *
     * @param pErpOrderedQty
     *  int to use to update the property.
     */
    public void setErpOrderedQty(int pErpOrderedQty){
        this.mErpOrderedQty = pErpOrderedQty;
    }
    /**
     * Retrieves the ErpOrderedQty property.
     *
     * @return
     *  int containing the ErpOrderedQty property.
     */
    public int getErpOrderedQty(){
        return mErpOrderedQty;
    }


    /**
     * Sets the ErpShippedQty property.
     *
     * @param pErpShippedQty
     *  int to use to update the property.
     */
    public void setErpShippedQty(int pErpShippedQty){
        this.mErpShippedQty = pErpShippedQty;
    }
    /**
     * Retrieves the ErpShippedQty property.
     *
     * @return
     *  int containing the ErpShippedQty property.
     */
    public int getErpShippedQty(){
        return mErpShippedQty;
    }


    /**
     * Sets the ErpOpenQty property.
     *
     * @param pErpOpenQty
     *  int to use to update the property.
     */
    public void setErpOpenQty(int pErpOpenQty){
        this.mErpOpenQty = pErpOpenQty;
    }
    /**
     * Retrieves the ErpOpenQty property.
     *
     * @return
     *  int containing the ErpOpenQty property.
     */
    public int getErpOpenQty(){
        return mErpOpenQty;
    }


    /**
     * Sets the ErpOpenAmount property.
     *
     * @param pErpOpenAmount
     *  BigDecimal to use to update the property.
     */
    public void setErpOpenAmount(BigDecimal pErpOpenAmount){
        this.mErpOpenAmount = pErpOpenAmount;
    }
    /**
     * Retrieves the ErpOpenAmount property.
     *
     * @return
     *  BigDecimal containing the ErpOpenAmount property.
     */
    public BigDecimal getErpOpenAmount(){
        return mErpOpenAmount;
    }


    /**
     * Sets the DistributorName property.
     *
     * @param pDistributorName
     *  String to use to update the property.
     */
    public void setDistributorName(String pDistributorName){
        this.mDistributorName = pDistributorName;
    }
    /**
     * Retrieves the DistributorName property.
     *
     * @return
     *  String containing the DistributorName property.
     */
    public String getDistributorName(){
        return mDistributorName;
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
     * Sets the ErpItemDescription property.
     *
     * @param pErpItemDescription
     *  String to use to update the property.
     */
    public void setErpItemDescription(String pErpItemDescription){
        this.mErpItemDescription = pErpItemDescription;
    }
    /**
     * Retrieves the ErpItemDescription property.
     *
     * @return
     *  String containing the ErpItemDescription property.
     */
    public String getErpItemDescription(){
        return mErpItemDescription;
    }


    /**
     * Sets the ErpPoDate property.
     *
     * @param pErpPoDate
     *  java.util.Date to use to update the property.
     */
    public void setErpPoDate(java.util.Date pErpPoDate){
        this.mErpPoDate = pErpPoDate;
    }
    /**
     * Retrieves the ErpPoDate property.
     *
     * @return
     *  java.util.Date containing the ErpPoDate property.
     */
    public java.util.Date getErpPoDate(){
        return mErpPoDate;
    }


    /**
     * Sets the ErpUnitCost property.
     *
     * @param pErpUnitCost
     *  BigDecimal to use to update the property.
     */
    public void setErpUnitCost(BigDecimal pErpUnitCost){
        this.mErpUnitCost = pErpUnitCost;
    }
    /**
     * Retrieves the ErpUnitCost property.
     *
     * @return
     *  BigDecimal containing the ErpUnitCost property.
     */
    public BigDecimal getErpUnitCost(){
        return mErpUnitCost;
    }


    /**
     * Sets the OrderItemNotes property.
     *
     * @param pOrderItemNotes
     *  OrderPropertyDataVector to use to update the property.
     */
    public void setOrderItemNotes(OrderPropertyDataVector pOrderItemNotes){
        this.mOrderItemNotes = pOrderItemNotes;
    }
    /**
     * Retrieves the OrderItemNotes property.
     *
     * @return
     *  OrderPropertyDataVector containing the OrderItemNotes property.
     */
    public OrderPropertyDataVector getOrderItemNotes(){
        return mOrderItemNotes;
    }


    /**
     * Sets the HasNote property.
     *
     * @param pHasNote
     *  boolean to use to update the property.
     */
    public void setHasNote(boolean pHasNote){
        this.mHasNote = pHasNote;
    }
    /**
     * Retrieves the HasNote property.
     *
     * @return
     *  boolean containing the HasNote property.
     */
    public boolean getHasNote(){
        return mHasNote;
    }


    /**
     * Sets the OpenLineStatusCd property.
     *
     * @param pOpenLineStatusCd
     *  String to use to update the property.
     */
    public void setOpenLineStatusCd(String pOpenLineStatusCd){
        this.mOpenLineStatusCd = pOpenLineStatusCd;
    }
    /**
     * Retrieves the OpenLineStatusCd property.
     *
     * @return
     *  String containing the OpenLineStatusCd property.
     */
    public String getOpenLineStatusCd(){
        return mOpenLineStatusCd;
    }


    /**
     * Sets the ShipCity property.
     *
     * @param pShipCity
     *  String to use to update the property.
     */
    public void setShipCity(String pShipCity){
        this.mShipCity = pShipCity;
    }
    /**
     * Retrieves the ShipCity property.
     *
     * @return
     *  String containing the ShipCity property.
     */
    public String getShipCity(){
        return mShipCity;
    }


    /**
     * Sets the ShipState property.
     *
     * @param pShipState
     *  String to use to update the property.
     */
    public void setShipState(String pShipState){
        this.mShipState = pShipState;
    }
    /**
     * Retrieves the ShipState property.
     *
     * @return
     *  String containing the ShipState property.
     */
    public String getShipState(){
        return mShipState;
    }


    /**
     * Sets the ShipPostalCode property.
     *
     * @param pShipPostalCode
     *  String to use to update the property.
     */
    public void setShipPostalCode(String pShipPostalCode){
        this.mShipPostalCode = pShipPostalCode;
    }
    /**
     * Retrieves the ShipPostalCode property.
     *
     * @return
     *  String containing the ShipPostalCode property.
     */
    public String getShipPostalCode(){
        return mShipPostalCode;
    }


    /**
     * Sets the ShipCountry property.
     *
     * @param pShipCountry
     *  String to use to update the property.
     */
    public void setShipCountry(String pShipCountry){
        this.mShipCountry = pShipCountry;
    }
    /**
     * Retrieves the ShipCountry property.
     *
     * @return
     *  String containing the ShipCountry property.
     */
    public String getShipCountry(){
        return mShipCountry;
    }


    
}
