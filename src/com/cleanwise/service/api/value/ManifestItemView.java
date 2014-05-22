
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ManifestItemView
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
 * <code>ManifestItemView</code> is a ViewObject class for UI.
 */
public class ManifestItemView
extends ValueObject
{
   
    private static final long serialVersionUID = -1629981159417798256L;
    private ManifestItemData mManifestItem;
    private String mCubicSizeString;
    private String mWeightString;
    private String mShipToPostalCode;
    private OrderAddressData mReturnAddress;
    private String mDistributionCenterId;
    private PurchaseOrderData mPurchaseOrderData;
    private ReturnRequestDataVector mReturnRequestDataVector;
    private BusEntityData mDistributorBusEntityData;
    private BusEntityData mAccountBusEntityData;
    private OrderData mOrderData;
    private OrderAddressData mShipToAddress;
    private FreightHandlerView mShipVia;
    private java.util.Date mDeliveryDate;

    /**
     * Constructor.
     */
    public ManifestItemView ()
    {
                mManifestItem = ManifestItemData.createValue();
        
        mCubicSizeString = "";
        mWeightString = "";
        mShipToPostalCode = "";
        mDistributionCenterId = "";
    }

    /**
     * Constructor. 
     */
    public ManifestItemView(ManifestItemData parm1, String parm2, String parm3, String parm4, OrderAddressData parm5, String parm6, PurchaseOrderData parm7, ReturnRequestDataVector parm8, BusEntityData parm9, BusEntityData parm10, OrderData parm11, OrderAddressData parm12, FreightHandlerView parm13, java.util.Date parm14)
    {
        mManifestItem = parm1;
        mCubicSizeString = parm2;
        mWeightString = parm3;
        mShipToPostalCode = parm4;
        mReturnAddress = parm5;
        mDistributionCenterId = parm6;
        mPurchaseOrderData = parm7;
        mReturnRequestDataVector = parm8;
        mDistributorBusEntityData = parm9;
        mAccountBusEntityData = parm10;
        mOrderData = parm11;
        mShipToAddress = parm12;
        mShipVia = parm13;
        mDeliveryDate = parm14;
        
    }

    /**
     * Creates a new ManifestItemView
     *
     * @return
     *  Newly initialized ManifestItemView object.
     */
    public static ManifestItemView createValue () 
    {
        ManifestItemView valueView = new ManifestItemView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ManifestItemView object
     */
    public String toString()
    {
        return "[" + "ManifestItem=" + mManifestItem + ", CubicSizeString=" + mCubicSizeString + ", WeightString=" + mWeightString + ", ShipToPostalCode=" + mShipToPostalCode + ", ReturnAddress=" + mReturnAddress + ", DistributionCenterId=" + mDistributionCenterId + ", PurchaseOrderData=" + mPurchaseOrderData + ", ReturnRequestDataVector=" + mReturnRequestDataVector + ", DistributorBusEntityData=" + mDistributorBusEntityData + ", AccountBusEntityData=" + mAccountBusEntityData + ", OrderData=" + mOrderData + ", ShipToAddress=" + mShipToAddress + ", ShipVia=" + mShipVia + ", DeliveryDate=" + mDeliveryDate + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ManifestItem");
	root.setAttribute("Id", String.valueOf(mManifestItem));

	Element node;

        node = doc.createElement("CubicSizeString");
        node.appendChild(doc.createTextNode(String.valueOf(mCubicSizeString)));
        root.appendChild(node);

        node = doc.createElement("WeightString");
        node.appendChild(doc.createTextNode(String.valueOf(mWeightString)));
        root.appendChild(node);

        node = doc.createElement("ShipToPostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mShipToPostalCode)));
        root.appendChild(node);

        node = doc.createElement("ReturnAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mReturnAddress)));
        root.appendChild(node);

        node = doc.createElement("DistributionCenterId");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributionCenterId)));
        root.appendChild(node);

        node = doc.createElement("PurchaseOrderData");
        node.appendChild(doc.createTextNode(String.valueOf(mPurchaseOrderData)));
        root.appendChild(node);

        node = doc.createElement("ReturnRequestDataVector");
        node.appendChild(doc.createTextNode(String.valueOf(mReturnRequestDataVector)));
        root.appendChild(node);

        node = doc.createElement("DistributorBusEntityData");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorBusEntityData)));
        root.appendChild(node);

        node = doc.createElement("AccountBusEntityData");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountBusEntityData)));
        root.appendChild(node);

        node = doc.createElement("OrderData");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderData)));
        root.appendChild(node);

        node = doc.createElement("ShipToAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mShipToAddress)));
        root.appendChild(node);

        node = doc.createElement("ShipVia");
        node.appendChild(doc.createTextNode(String.valueOf(mShipVia)));
        root.appendChild(node);

        node = doc.createElement("DeliveryDate");
        node.appendChild(doc.createTextNode(String.valueOf(mDeliveryDate)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ManifestItemView copy()  {
      ManifestItemView obj = new ManifestItemView();
      obj.setManifestItem(mManifestItem);
      obj.setCubicSizeString(mCubicSizeString);
      obj.setWeightString(mWeightString);
      obj.setShipToPostalCode(mShipToPostalCode);
      obj.setReturnAddress(mReturnAddress);
      obj.setDistributionCenterId(mDistributionCenterId);
      obj.setPurchaseOrderData(mPurchaseOrderData);
      obj.setReturnRequestDataVector(mReturnRequestDataVector);
      obj.setDistributorBusEntityData(mDistributorBusEntityData);
      obj.setAccountBusEntityData(mAccountBusEntityData);
      obj.setOrderData(mOrderData);
      obj.setShipToAddress(mShipToAddress);
      obj.setShipVia(mShipVia);
      obj.setDeliveryDate(mDeliveryDate);
      
      return obj;
    }

    
    /**
     * Sets the ManifestItem property.
     *
     * @param pManifestItem
     *  ManifestItemData to use to update the property.
     */
    public void setManifestItem(ManifestItemData pManifestItem){
        this.mManifestItem = pManifestItem;
    }
    /**
     * Retrieves the ManifestItem property.
     *
     * @return
     *  ManifestItemData containing the ManifestItem property.
     */
    public ManifestItemData getManifestItem(){
        return mManifestItem;
    }


    /**
     * Sets the CubicSizeString property.
     *
     * @param pCubicSizeString
     *  String to use to update the property.
     */
    public void setCubicSizeString(String pCubicSizeString){
        this.mCubicSizeString = pCubicSizeString;
    }
    /**
     * Retrieves the CubicSizeString property.
     *
     * @return
     *  String containing the CubicSizeString property.
     */
    public String getCubicSizeString(){
        return mCubicSizeString;
    }


    /**
     * Sets the WeightString property.
     *
     * @param pWeightString
     *  String to use to update the property.
     */
    public void setWeightString(String pWeightString){
        this.mWeightString = pWeightString;
    }
    /**
     * Retrieves the WeightString property.
     *
     * @return
     *  String containing the WeightString property.
     */
    public String getWeightString(){
        return mWeightString;
    }


    /**
     * Sets the ShipToPostalCode property.
     *
     * @param pShipToPostalCode
     *  String to use to update the property.
     */
    public void setShipToPostalCode(String pShipToPostalCode){
        this.mShipToPostalCode = pShipToPostalCode;
    }
    /**
     * Retrieves the ShipToPostalCode property.
     *
     * @return
     *  String containing the ShipToPostalCode property.
     */
    public String getShipToPostalCode(){
        return mShipToPostalCode;
    }


    /**
     * Sets the ReturnAddress property.
     *
     * @param pReturnAddress
     *  OrderAddressData to use to update the property.
     */
    public void setReturnAddress(OrderAddressData pReturnAddress){
        this.mReturnAddress = pReturnAddress;
    }
    /**
     * Retrieves the ReturnAddress property.
     *
     * @return
     *  OrderAddressData containing the ReturnAddress property.
     */
    public OrderAddressData getReturnAddress(){
        return mReturnAddress;
    }


    /**
     * Sets the DistributionCenterId property.
     *
     * @param pDistributionCenterId
     *  String to use to update the property.
     */
    public void setDistributionCenterId(String pDistributionCenterId){
        this.mDistributionCenterId = pDistributionCenterId;
    }
    /**
     * Retrieves the DistributionCenterId property.
     *
     * @return
     *  String containing the DistributionCenterId property.
     */
    public String getDistributionCenterId(){
        return mDistributionCenterId;
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


    
}
