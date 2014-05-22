
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PurchaseOrderStatusDescDataView
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
/**
 * <code>PurchaseOrderStatusDescDataView</code> is a ViewObject class for UI.
 */
public class PurchaseOrderStatusDescDataView
extends ValueObject
{
   
    private static final long serialVersionUID = -8464785104988893711L;
    private PurchaseOrderData mPurchaseOrderData;
    private ReturnRequestDataVector mReturnRequestDataVector;
    private BusEntityData mDistributorBusEntityData;
    private BusEntityData mAccountBusEntityData;
    private String mPoAccountName;
    private OrderData mOrderData;
    private OrderAddressData mShipToAddress;
    private OrderAddressData mBillToAddress;
    private FreightHandlerView mShipVia;
    private java.util.Date mDeliveryDate;
    private Boolean mRouted;
    private OrderFreightDataVector mOrderFreightDataVector;
    private ManifestItemViewVector mManifestItems;
    private ManifestItemDataVector mManifestDataItems;
    private InvoiceDistData mInvoiceDist;
    private InvoiceDistDetailDataVector mInvoiceDistDetailList;
    private java.math.BigDecimal mVendorRequestedFreight;
    private java.math.BigDecimal mVendorRequestedMiscCharges;
    private java.math.BigDecimal mVendorRequestedTax;
    private java.math.BigDecimal mVendorRequestedDiscount; //discount from invoice.
    private boolean mOrderReceived;
    private boolean mBillingOnlyOrder;
    private boolean mInvoiceApproved;
    private String mInvoiceApprovedBy;
    private java.util.Date mInvoiceApprovedDate;
    private OrderAddOnChargeDataVector mOrderAddOnChargeDataVector;

    /**
     * Constructor.
     */
    public PurchaseOrderStatusDescDataView ()
    {
        mPoAccountName = "";
        mInvoiceApprovedBy = "";
    }

    /**
     * Constructor. 
     */
    public PurchaseOrderStatusDescDataView(PurchaseOrderData parm1, ReturnRequestDataVector parm2, BusEntityData parm3, BusEntityData parm4, String parm5, OrderData parm6, OrderAddressData parm7, OrderAddressData parm8, FreightHandlerView parm9, java.util.Date parm10, Boolean parm11, OrderFreightDataVector parm12, ManifestItemViewVector parm13, ManifestItemDataVector parm14, InvoiceDistData parm15, InvoiceDistDetailDataVector parm16, java.math.BigDecimal parm17, java.math.BigDecimal parm18, java.math.BigDecimal parm19, boolean parm20, boolean parm21, boolean parm22, String parm23, java.util.Date parm24, OrderAddOnChargeDataVector parm25)
    {
        mPurchaseOrderData = parm1;
        mReturnRequestDataVector = parm2;
        mDistributorBusEntityData = parm3;
        mAccountBusEntityData = parm4;
        mPoAccountName = parm5;
        mOrderData = parm6;
        mShipToAddress = parm7;
        mBillToAddress = parm8;
        mShipVia = parm9;
        mDeliveryDate = parm10;
        mRouted = parm11;
        mOrderFreightDataVector = parm12;
        mManifestItems = parm13;
        mManifestDataItems = parm14;
        mInvoiceDist = parm15;
        mInvoiceDistDetailList = parm16;
        mVendorRequestedFreight = parm17;
        mVendorRequestedMiscCharges = parm18;
        mVendorRequestedTax = parm19;
        mOrderReceived = parm20;
        mBillingOnlyOrder = parm21;
        mInvoiceApproved = parm22;
        mInvoiceApprovedBy = parm23;
        mInvoiceApprovedDate = parm24;
        mOrderAddOnChargeDataVector = parm25;
        
    }

    /**
     * Creates a new PurchaseOrderStatusDescDataView
     *
     * @return
     *  Newly initialized PurchaseOrderStatusDescDataView object.
     */
    public static PurchaseOrderStatusDescDataView createValue () 
    {
        PurchaseOrderStatusDescDataView valueView = new PurchaseOrderStatusDescDataView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PurchaseOrderStatusDescDataView object
     */
    public String toString()
    {
        return "[" + "PurchaseOrderData=" + mPurchaseOrderData + ", ReturnRequestDataVector=" + mReturnRequestDataVector + ", DistributorBusEntityData=" + mDistributorBusEntityData + ", AccountBusEntityData=" + mAccountBusEntityData + ", PoAccountName=" + mPoAccountName + ", OrderData=" + mOrderData + ", ShipToAddress=" + mShipToAddress + ", BillToAddress=" + mBillToAddress + ", ShipVia=" + mShipVia + ", DeliveryDate=" + mDeliveryDate + ", Routed=" + mRouted + ", OrderFreightDataVector=" + mOrderFreightDataVector + ", ManifestItems=" + mManifestItems + ", ManifestDataItems=" + mManifestDataItems + ", InvoiceDist=" + mInvoiceDist + ", InvoiceDistDetailList=" + mInvoiceDistDetailList + ", VendorRequestedFreight=" + mVendorRequestedFreight + ", VendorRequestedMiscCharges=" + mVendorRequestedMiscCharges + ", VendorRequestedTax=" + mVendorRequestedTax + ", OrderReceived=" + mOrderReceived + ", BillingOnlyOrder=" + mBillingOnlyOrder + ", InvoiceApproved=" + mInvoiceApproved + ", InvoiceApprovedBy=" + mInvoiceApprovedBy + ", InvoiceApprovedDate=" + mInvoiceApprovedDate + ", OrderAddOnChargeDataVector=" + mOrderAddOnChargeDataVector + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("PurchaseOrderStatusDescData");
	root.setAttribute("Id", String.valueOf(mPurchaseOrderData));

	Element node;

        node = doc.createElement("ReturnRequestDataVector");
        node.appendChild(doc.createTextNode(String.valueOf(mReturnRequestDataVector)));
        root.appendChild(node);

        node = doc.createElement("DistributorBusEntityData");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorBusEntityData)));
        root.appendChild(node);

        node = doc.createElement("AccountBusEntityData");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountBusEntityData)));
        root.appendChild(node);

        node = doc.createElement("PoAccountName");
        node.appendChild(doc.createTextNode(String.valueOf(mPoAccountName)));
        root.appendChild(node);

        node = doc.createElement("OrderData");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderData)));
        root.appendChild(node);

        node = doc.createElement("ShipToAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mShipToAddress)));
        root.appendChild(node);

        node = doc.createElement("BillToAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mBillToAddress)));
        root.appendChild(node);

        node = doc.createElement("ShipVia");
        node.appendChild(doc.createTextNode(String.valueOf(mShipVia)));
        root.appendChild(node);

        node = doc.createElement("DeliveryDate");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryDate)));
        root.appendChild(node);

        node = doc.createElement("Routed");
        node.appendChild(doc.createTextNode(String.valueOf(mRouted)));
        root.appendChild(node);

        node = doc.createElement("OrderFreightDataVector");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderFreightDataVector)));
        root.appendChild(node);

        node = doc.createElement("ManifestItems");
        node.appendChild(doc.createTextNode(String.valueOf(mManifestItems)));
        root.appendChild(node);

        node = doc.createElement("ManifestDataItems");
        node.appendChild(doc.createTextNode(String.valueOf(mManifestDataItems)));
        root.appendChild(node);

        node = doc.createElement("InvoiceDist");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceDist)));
        root.appendChild(node);

        node = doc.createElement("InvoiceDistDetailList");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceDistDetailList)));
        root.appendChild(node);

        node = doc.createElement("VendorRequestedFreight");
        node.appendChild(doc.createTextNode(String.valueOf(mVendorRequestedFreight)));
        root.appendChild(node);

        node = doc.createElement("VendorRequestedMiscCharges");
        node.appendChild(doc.createTextNode(String.valueOf(mVendorRequestedMiscCharges)));
        root.appendChild(node);

        node = doc.createElement("VendorRequestedTax");
        node.appendChild(doc.createTextNode(String.valueOf(mVendorRequestedTax)));
        root.appendChild(node);

        node = doc.createElement("OrderReceived");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderReceived)));
        root.appendChild(node);

        node = doc.createElement("BillingOnlyOrder");
        node.appendChild(doc.createTextNode(String.valueOf(mBillingOnlyOrder)));
        root.appendChild(node);

        node = doc.createElement("InvoiceApproved");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceApproved)));
        root.appendChild(node);

        node = doc.createElement("InvoiceApprovedBy");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceApprovedBy)));
        root.appendChild(node);

        node = doc.createElement("InvoiceApprovedDate");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceApprovedDate)));
        root.appendChild(node);

        node = doc.createElement("OrderAddOnChargeDataVector");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderAddOnChargeDataVector)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public PurchaseOrderStatusDescDataView copy()  {
      PurchaseOrderStatusDescDataView obj = new PurchaseOrderStatusDescDataView();
      obj.setPurchaseOrderData(mPurchaseOrderData);
      obj.setReturnRequestDataVector(mReturnRequestDataVector);
      obj.setDistributorBusEntityData(mDistributorBusEntityData);
      obj.setAccountBusEntityData(mAccountBusEntityData);
      obj.setPoAccountName(mPoAccountName);
      obj.setOrderData(mOrderData);
      obj.setShipToAddress(mShipToAddress);
      obj.setBillToAddress(mBillToAddress);
      obj.setShipVia(mShipVia);
      obj.setDeliveryDate(mDeliveryDate);
      obj.setRouted(mRouted);
      obj.setOrderFreightDataVector(mOrderFreightDataVector);
      obj.setManifestItems(mManifestItems);
      obj.setManifestDataItems(mManifestDataItems);
      obj.setInvoiceDist(mInvoiceDist);
      obj.setInvoiceDistDetailList(mInvoiceDistDetailList);
      obj.setVendorRequestedFreight(mVendorRequestedFreight);
      obj.setVendorRequestedMiscCharges(mVendorRequestedMiscCharges);
      obj.setVendorRequestedTax(mVendorRequestedTax);
      obj.setOrderReceived(mOrderReceived);
      obj.setBillingOnlyOrder(mBillingOnlyOrder);
      obj.setInvoiceApproved(mInvoiceApproved);
      obj.setInvoiceApprovedBy(mInvoiceApprovedBy);
      obj.setInvoiceApprovedDate(mInvoiceApprovedDate);
      obj.setOrderAddOnChargeDataVector(mOrderAddOnChargeDataVector);
      
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
     * Sets the ReturnRequestDataVector property.
     *
     * @param pReturnRequestDataVector
     *  ReturnRequestDataVector to use to update the property.
     */
    public void setReturnRequestDataVector(ReturnRequestDataVector pReturnRequestDataVector){
        this.mReturnRequestDataVector = pReturnRequestDataVector;
    }
    /**
     * Retrieves the ReturnRequestDataVector property.
     *
     * @return
     *  ReturnRequestDataVector containing the ReturnRequestDataVector property.
     */
    public ReturnRequestDataVector getReturnRequestDataVector(){
        return mReturnRequestDataVector;
    }


    /**
     * Sets the DistributorBusEntityData property.
     *
     * @param pDistributorBusEntityData
     *  BusEntityData to use to update the property.
     */
    public void setDistributorBusEntityData(BusEntityData pDistributorBusEntityData){
        this.mDistributorBusEntityData = pDistributorBusEntityData;
    }
    /**
     * Retrieves the DistributorBusEntityData property.
     *
     * @return
     *  BusEntityData containing the DistributorBusEntityData property.
     */
    public BusEntityData getDistributorBusEntityData(){
        return mDistributorBusEntityData;
    }


    /**
     * Sets the AccountBusEntityData property.
     *
     * @param pAccountBusEntityData
     *  BusEntityData to use to update the property.
     */
    public void setAccountBusEntityData(BusEntityData pAccountBusEntityData){
        this.mAccountBusEntityData = pAccountBusEntityData;
    }
    /**
     * Retrieves the AccountBusEntityData property.
     *
     * @return
     *  BusEntityData containing the AccountBusEntityData property.
     */
    public BusEntityData getAccountBusEntityData(){
        return mAccountBusEntityData;
    }


    /**
     * Sets the PoAccountName property.
     *
     * @param pPoAccountName
     *  String to use to update the property.
     */
    public void setPoAccountName(String pPoAccountName){
        this.mPoAccountName = pPoAccountName;
    }
    /**
     * Retrieves the PoAccountName property.
     *
     * @return
     *  String containing the PoAccountName property.
     */
    public String getPoAccountName(){
        return mPoAccountName;
    }


    /**
     * Sets the OrderData property.
     *
     * @param pOrderData
     *  OrderData to use to update the property.
     */
    public void setOrderData(OrderData pOrderData){
        this.mOrderData = pOrderData;
    }
    /**
     * Retrieves the OrderData property.
     *
     * @return
     *  OrderData containing the OrderData property.
     */
    public OrderData getOrderData(){
        return mOrderData;
    }


    /**
     * Sets the ShipToAddress property.
     *
     * @param pShipToAddress
     *  OrderAddressData to use to update the property.
     */
    public void setShipToAddress(OrderAddressData pShipToAddress){
        this.mShipToAddress = pShipToAddress;
    }
    /**
     * Retrieves the ShipToAddress property.
     *
     * @return
     *  OrderAddressData containing the ShipToAddress property.
     */
    public OrderAddressData getShipToAddress(){
        return mShipToAddress;
    }


    /**
     * Sets the BillToAddress property.
     *
     * @param pBillToAddress
     *  OrderAddressData to use to update the property.
     */
    public void setBillToAddress(OrderAddressData pBillToAddress){
        this.mBillToAddress = pBillToAddress;
    }
    /**
     * Retrieves the BillToAddress property.
     *
     * @return
     *  OrderAddressData containing the BillToAddress property.
     */
    public OrderAddressData getBillToAddress(){
        return mBillToAddress;
    }


    /**
     * Sets the ShipVia property.
     *
     * @param pShipVia
     *  FreightHandlerView to use to update the property.
     */
    public void setShipVia(FreightHandlerView pShipVia){
        this.mShipVia = pShipVia;
    }
    /**
     * Retrieves the ShipVia property.
     *
     * @return
     *  FreightHandlerView containing the ShipVia property.
     */
    public FreightHandlerView getShipVia(){
        return mShipVia;
    }


    /**
     * Sets the DeliveryDate property.
     *
     * @param pDeliveryDate
     *  java.util.Date to use to update the property.
     */
    public void setDeliveryDate(java.util.Date pDeliveryDate){
        this.mDeliveryDate = pDeliveryDate;
    }
    /**
     * Retrieves the DeliveryDate property.
     *
     * @return
     *  java.util.Date containing the DeliveryDate property.
     */
    public java.util.Date getDeliveryDate(){
        return mDeliveryDate;
    }


    /**
     * Sets the Routed property.
     *
     * @param pRouted
     *  Boolean to use to update the property.
     */
    public void setRouted(Boolean pRouted){
        this.mRouted = pRouted;
    }
    /**
     * Retrieves the Routed property.
     *
     * @return
     *  Boolean containing the Routed property.
     */
    public Boolean getRouted(){
        return mRouted;
    }


    /**
     * Sets the OrderFreightDataVector property.
     *
     * @param pOrderFreightDataVector
     *  OrderFreightDataVector to use to update the property.
     */
    public void setOrderFreightDataVector(OrderFreightDataVector pOrderFreightDataVector){
        this.mOrderFreightDataVector = pOrderFreightDataVector;
    }
    /**
     * Retrieves the OrderFreightDataVector property.
     *
     * @return
     *  OrderFreightDataVector containing the OrderFreightDataVector property.
     */
    public OrderFreightDataVector getOrderFreightDataVector(){
        return mOrderFreightDataVector;
    }


    /**
     * Sets the ManifestItems property.
     *
     * @param pManifestItems
     *  ManifestItemViewVector to use to update the property.
     */
    public void setManifestItems(ManifestItemViewVector pManifestItems){
        this.mManifestItems = pManifestItems;
    }
    /**
     * Retrieves the ManifestItems property.
     *
     * @return
     *  ManifestItemViewVector containing the ManifestItems property.
     */
    public ManifestItemViewVector getManifestItems(){
        return mManifestItems;
    }


        /**
        *Index accessible getter for property ManifestItems.  This has the same effect as calling:
        *<code >
        *getManifestItems().get(pIndex);
        *</code>
        *But will create all necessary empty return types such that an array out of bounds exception
        *is not thrown, and this method can be utilized from jsp using the:
        *<code>
        *ManifestItemsElement[pIndex]
        *</code>
        *Syntax.
        *@param int the index you wish to access.
        *@returns ManifestItemView at the specified index.
        */
        public ManifestItemView getManifestItemsElement(int pIndex){
          while(pIndex >= mManifestItems.size()){
                mManifestItems.add(ManifestItemView.createValue());
          }
          return (ManifestItemView) mManifestItems.get(pIndex);
        }

        /**
        *Index accessible setter for property ManifestItems.  This has the same effect as calling:
        *<code >
        *getManifestItems().add(pIndex,pManifestItems);
        *</code>
        *But will create all necessary empty return types such that an array out of bounds exception
        *is not thrown, and this method can be utilized from jsp using the:
        *<code>
        *ManifestItemsElement[pIndex]
        *</code>
        *Syntax.
        *@param int the index you wish to access.
        *@param ManifestItemView new value of property.
        */
        public void setManifestItemsElement(int pIndex,ManifestItemView pManifestItems){
          while(pIndex > mManifestItems.size()){
                mManifestItems.add(ManifestItemView.createValue());
          }
          mManifestItems.add(pIndex,pManifestItems);
        }

    /**
     * Sets the ManifestDataItems property.
     *
     * @param pManifestDataItems
     *  ManifestItemDataVector to use to update the property.
     */
    public void setManifestDataItems(ManifestItemDataVector pManifestDataItems){
        this.mManifestDataItems = pManifestDataItems;
    }
    /**
     * Retrieves the ManifestDataItems property.
     *
     * @return
     *  ManifestItemDataVector containing the ManifestDataItems property.
     */
    public ManifestItemDataVector getManifestDataItems(){
        return mManifestDataItems;
    }


    /**
     * Sets the InvoiceDist property.
     *
     * @param pInvoiceDist
     *  InvoiceDistData to use to update the property.
     */
    public void setInvoiceDist(InvoiceDistData pInvoiceDist){
        this.mInvoiceDist = pInvoiceDist;
    }
    /**
     * Retrieves the InvoiceDist property.
     *
     * @return
     *  InvoiceDistData containing the InvoiceDist property.
     */
    public InvoiceDistData getInvoiceDist(){
        return mInvoiceDist;
    }


    /**
     * Sets the InvoiceDistDetailList property.
     *
     * @param pInvoiceDistDetailList
     *  InvoiceDistDetailDataVector to use to update the property.
     */
    public void setInvoiceDistDetailList(InvoiceDistDetailDataVector pInvoiceDistDetailList){
        this.mInvoiceDistDetailList = pInvoiceDistDetailList;
    }
    /**
     * Retrieves the InvoiceDistDetailList property.
     *
     * @return
     *  InvoiceDistDetailDataVector containing the InvoiceDistDetailList property.
     */
    public InvoiceDistDetailDataVector getInvoiceDistDetailList(){
        return mInvoiceDistDetailList;
    }


    /**
     * Sets the VendorRequestedFreight property.
     *
     * @param pVendorRequestedFreight
     *  java.math.BigDecimal to use to update the property.
     */
    public void setVendorRequestedFreight(java.math.BigDecimal pVendorRequestedFreight){
        this.mVendorRequestedFreight = pVendorRequestedFreight;
    }
    /**
     * Retrieves the VendorRequestedFreight property.
     *
     * @return
     *  java.math.BigDecimal containing the VendorRequestedFreight property.
     */
    public java.math.BigDecimal getVendorRequestedFreight(){
        return mVendorRequestedFreight;
    }


    /**
     * Sets the VendorRequestedMiscCharges property.
     *
     * @param pVendorRequestedMiscCharges
     *  java.math.BigDecimal to use to update the property.
     */
    public void setVendorRequestedMiscCharges(java.math.BigDecimal pVendorRequestedMiscCharges){
        this.mVendorRequestedMiscCharges = pVendorRequestedMiscCharges;
    }
    /**
     * Retrieves the VendorRequestedMiscCharges property.
     *
     * @return
     *  java.math.BigDecimal containing the VendorRequestedMiscCharges property.
     */
    public java.math.BigDecimal getVendorRequestedMiscCharges(){
        return mVendorRequestedMiscCharges;
    }


    /**
     * Sets the VendorRequestedTax property.
     *
     * @param pVendorRequestedTax
     *  java.math.BigDecimal to use to update the property.
     */
    public void setVendorRequestedTax(java.math.BigDecimal pVendorRequestedTax){
        this.mVendorRequestedTax = pVendorRequestedTax;
    }
    /**
     * Retrieves the VendorRequestedTax property.
     *
     * @return
     *  java.math.BigDecimal containing the VendorRequestedTax property.
     */
    public java.math.BigDecimal getVendorRequestedTax(){
        return mVendorRequestedTax;
    }

    /**
		 * @return the mVendorRequestedDiscount
		 */
		public java.math.BigDecimal getVendorRequestedDiscount() {
			return mVendorRequestedDiscount;
		}

		/**
		 * @param vendorRequestedDiscount the mVendorRequestedDiscount to set
		 */
		public void setVendorRequestedDiscount(
				java.math.BigDecimal vendorRequestedDiscount) {
			mVendorRequestedDiscount = vendorRequestedDiscount;
		}

		/**
     * Sets the OrderReceived property.
     *
     * @param pOrderReceived
     *  boolean to use to update the property.
     */
    public void setOrderReceived(boolean pOrderReceived){
        this.mOrderReceived = pOrderReceived;
    }
    /**
     * Retrieves the OrderReceived property.
     *
     * @return
     *  boolean containing the OrderReceived property.
     */
    public boolean getOrderReceived(){
        return mOrderReceived;
    }


    /**
     * Sets the BillingOnlyOrder property.
     *
     * @param pBillingOnlyOrder
     *  boolean to use to update the property.
     */
    public void setBillingOnlyOrder(boolean pBillingOnlyOrder){
        this.mBillingOnlyOrder = pBillingOnlyOrder;
    }
    /**
     * Retrieves the BillingOnlyOrder property.
     *
     * @return
     *  boolean containing the BillingOnlyOrder property.
     */
    public boolean getBillingOnlyOrder(){
        return mBillingOnlyOrder;
    }


    /**
     * Sets the InvoiceApproved property.
     *
     * @param pInvoiceApproved
     *  boolean to use to update the property.
     */
    public void setInvoiceApproved(boolean pInvoiceApproved){
        this.mInvoiceApproved = pInvoiceApproved;
    }
    /**
     * Retrieves the InvoiceApproved property.
     *
     * @return
     *  boolean containing the InvoiceApproved property.
     */
    public boolean getInvoiceApproved(){
        return mInvoiceApproved;
    }


    /**
     * Sets the InvoiceApprovedBy property.
     *
     * @param pInvoiceApprovedBy
     *  String to use to update the property.
     */
    public void setInvoiceApprovedBy(String pInvoiceApprovedBy){
        this.mInvoiceApprovedBy = pInvoiceApprovedBy;
    }
    /**
     * Retrieves the InvoiceApprovedBy property.
     *
     * @return
     *  String containing the InvoiceApprovedBy property.
     */
    public String getInvoiceApprovedBy(){
        return mInvoiceApprovedBy;
    }


    /**
     * Sets the InvoiceApprovedDate property.
     *
     * @param pInvoiceApprovedDate
     *  java.util.Date to use to update the property.
     */
    public void setInvoiceApprovedDate(java.util.Date pInvoiceApprovedDate){
        this.mInvoiceApprovedDate = pInvoiceApprovedDate;
    }
    /**
     * Retrieves the InvoiceApprovedDate property.
     *
     * @return
     *  java.util.Date containing the InvoiceApprovedDate property.
     */
    public java.util.Date getInvoiceApprovedDate(){
        return mInvoiceApprovedDate;
    }


    /**
     * Sets the OrderAddOnChargeDataVector property.
     *
     * @param pOrderAddOnChargeDataVector
     *  OrderAddOnChargeDataVector to use to update the property.
     */
    public void setOrderAddOnChargeDataVector(OrderAddOnChargeDataVector pOrderAddOnChargeDataVector){
        this.mOrderAddOnChargeDataVector = pOrderAddOnChargeDataVector;
    }
    /**
     * Retrieves the OrderAddOnChargeDataVector property.
     *
     * @return
     *  OrderAddOnChargeDataVector containing the OrderAddOnChargeDataVector property.
     */
    public OrderAddOnChargeDataVector getOrderAddOnChargeDataVector(){
        return mOrderAddOnChargeDataVector;
    }


    
}
