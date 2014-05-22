
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        SelfServiceOrderDescView
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
 * <code>SelfServiceOrderDescView</code> is a ViewObject class for UI.
 */
public class SelfServiceOrderDescView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private String mClwOrderNum;
    private BigDecimal mSubTotal;
    private BigDecimal mShippingCost;
    private BigDecimal mFuelSurcharge;
    private String mShipNotes;
    private SelfServiceOrderItemDescViewVector mItems;
    private OrderAddressData mShippingAddress;
    private OrderAddressData mBillingAddress;

    /**
     * Constructor.
     */
    public SelfServiceOrderDescView ()
    {
        mClwOrderNum = "";
        mShipNotes = "";
    }

    /**
     * Constructor. 
     */
    public SelfServiceOrderDescView(String parm1, BigDecimal parm2, BigDecimal parm3, BigDecimal parm4, String parm5, SelfServiceOrderItemDescViewVector parm6, OrderAddressData parm7, OrderAddressData parm8)
    {
        mClwOrderNum = parm1;
        mSubTotal = parm2;
        mShippingCost = parm3;
        mFuelSurcharge = parm4;
        mShipNotes = parm5;
        mItems = parm6;
        mShippingAddress = parm7;
        mBillingAddress = parm8;
        
    }

    /**
     * Creates a new SelfServiceOrderDescView
     *
     * @return
     *  Newly initialized SelfServiceOrderDescView object.
     */
    public static SelfServiceOrderDescView createValue () 
    {
        SelfServiceOrderDescView valueView = new SelfServiceOrderDescView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this SelfServiceOrderDescView object
     */
    public String toString()
    {
        return "[" + "ClwOrderNum=" + mClwOrderNum + ", SubTotal=" + mSubTotal + ", ShippingCost=" + mShippingCost + ", FuelSurcharge=" + mFuelSurcharge + ", ShipNotes=" + mShipNotes + ", Items=" + mItems + ", ShippingAddress=" + mShippingAddress + ", BillingAddress=" + mBillingAddress + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("SelfServiceOrderDesc");
	root.setAttribute("Id", String.valueOf(mClwOrderNum));

	Element node;

        node = doc.createElement("SubTotal");
        node.appendChild(doc.createTextNode(String.valueOf(mSubTotal)));
        root.appendChild(node);

        node = doc.createElement("ShippingCost");
        node.appendChild(doc.createTextNode(String.valueOf(mShippingCost)));
        root.appendChild(node);

        node = doc.createElement("FuelSurcharge");
        node.appendChild(doc.createTextNode(String.valueOf(mFuelSurcharge)));
        root.appendChild(node);

        node = doc.createElement("ShipNotes");
        node.appendChild(doc.createTextNode(String.valueOf(mShipNotes)));
        root.appendChild(node);

        node = doc.createElement("Items");
        node.appendChild(doc.createTextNode(String.valueOf(mItems)));
        root.appendChild(node);

        node = doc.createElement("ShippingAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mShippingAddress)));
        root.appendChild(node);

        node = doc.createElement("BillingAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mBillingAddress)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public SelfServiceOrderDescView copy()  {
      SelfServiceOrderDescView obj = new SelfServiceOrderDescView();
      obj.setClwOrderNum(mClwOrderNum);
      obj.setSubTotal(mSubTotal);
      obj.setShippingCost(mShippingCost);
      obj.setFuelSurcharge(mFuelSurcharge);
      obj.setShipNotes(mShipNotes);
      obj.setItems(mItems);
      obj.setShippingAddress(mShippingAddress);
      obj.setBillingAddress(mBillingAddress);
      
      return obj;
    }

    
    /**
     * Sets the ClwOrderNum property.
     *
     * @param pClwOrderNum
     *  String to use to update the property.
     */
    public void setClwOrderNum(String pClwOrderNum){
        this.mClwOrderNum = pClwOrderNum;
    }
    /**
     * Retrieves the ClwOrderNum property.
     *
     * @return
     *  String containing the ClwOrderNum property.
     */
    public String getClwOrderNum(){
        return mClwOrderNum;
    }


    /**
     * Sets the SubTotal property.
     *
     * @param pSubTotal
     *  BigDecimal to use to update the property.
     */
    public void setSubTotal(BigDecimal pSubTotal){
        this.mSubTotal = pSubTotal;
    }
    /**
     * Retrieves the SubTotal property.
     *
     * @return
     *  BigDecimal containing the SubTotal property.
     */
    public BigDecimal getSubTotal(){
        return mSubTotal;
    }


    /**
     * Sets the ShippingCost property.
     *
     * @param pShippingCost
     *  BigDecimal to use to update the property.
     */
    public void setShippingCost(BigDecimal pShippingCost){
        this.mShippingCost = pShippingCost;
    }
    /**
     * Retrieves the ShippingCost property.
     *
     * @return
     *  BigDecimal containing the ShippingCost property.
     */
    public BigDecimal getShippingCost(){
        return mShippingCost;
    }


    /**
     * Sets the FuelSurcharge property.
     *
     * @param pFuelSurcharge
     *  BigDecimal to use to update the property.
     */
    public void setFuelSurcharge(BigDecimal pFuelSurcharge){
        this.mFuelSurcharge = pFuelSurcharge;
    }
    /**
     * Retrieves the FuelSurcharge property.
     *
     * @return
     *  BigDecimal containing the FuelSurcharge property.
     */
    public BigDecimal getFuelSurcharge(){
        return mFuelSurcharge;
    }


    /**
     * Sets the ShipNotes property.
     *
     * @param pShipNotes
     *  String to use to update the property.
     */
    public void setShipNotes(String pShipNotes){
        this.mShipNotes = pShipNotes;
    }
    /**
     * Retrieves the ShipNotes property.
     *
     * @return
     *  String containing the ShipNotes property.
     */
    public String getShipNotes(){
        return mShipNotes;
    }


    /**
     * Sets the Items property.
     *
     * @param pItems
     *  SelfServiceOrderItemDescViewVector to use to update the property.
     */
    public void setItems(SelfServiceOrderItemDescViewVector pItems){
        this.mItems = pItems;
    }
    /**
     * Retrieves the Items property.
     *
     * @return
     *  SelfServiceOrderItemDescViewVector containing the Items property.
     */
    public SelfServiceOrderItemDescViewVector getItems(){
        return mItems;
    }


    /**
     * Sets the ShippingAddress property.
     *
     * @param pShippingAddress
     *  OrderAddressData to use to update the property.
     */
    public void setShippingAddress(OrderAddressData pShippingAddress){
        this.mShippingAddress = pShippingAddress;
    }
    /**
     * Retrieves the ShippingAddress property.
     *
     * @return
     *  OrderAddressData containing the ShippingAddress property.
     */
    public OrderAddressData getShippingAddress(){
        return mShippingAddress;
    }


    /**
     * Sets the BillingAddress property.
     *
     * @param pBillingAddress
     *  OrderAddressData to use to update the property.
     */
    public void setBillingAddress(OrderAddressData pBillingAddress){
        this.mBillingAddress = pBillingAddress;
    }
    /**
     * Retrieves the BillingAddress property.
     *
     * @return
     *  OrderAddressData containing the BillingAddress property.
     */
    public OrderAddressData getBillingAddress(){
        return mBillingAddress;
    }


    
}
