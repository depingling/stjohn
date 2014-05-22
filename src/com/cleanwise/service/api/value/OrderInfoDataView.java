
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderInfoDataView
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
 * <code>OrderInfoDataView</code> is a ViewObject class for UI.
 */
public class OrderInfoDataView
extends ValueObject
{
   
    private static final long serialVersionUID = -4444529609334459091L;
    private OrderInfoView mOrderInfo;
    private AddressInfoView mShippingAddress;
    private AddressInfoView mBillingAddress;
    private ItemInfoViewVector mItems;
    private OrderPropertyDataVector mInternalComments;

    /**
     * Constructor.
     */
    public OrderInfoDataView ()
    {
    }

    /**
     * Constructor. 
     */
    public OrderInfoDataView(OrderInfoView parm1, AddressInfoView parm2, AddressInfoView parm3, ItemInfoViewVector parm4, OrderPropertyDataVector parm5)
    {
        mOrderInfo = parm1;
        mShippingAddress = parm2;
        mBillingAddress = parm3;
        mItems = parm4;
        mInternalComments = parm5;
        
    }

    /**
     * Creates a new OrderInfoDataView
     *
     * @return
     *  Newly initialized OrderInfoDataView object.
     */
    public static OrderInfoDataView createValue () 
    {
        OrderInfoDataView valueView = new OrderInfoDataView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderInfoDataView object
     */
    public String toString()
    {
        return "[" + "OrderInfo=" + mOrderInfo + ", ShippingAddress=" + mShippingAddress + ", BillingAddress=" + mBillingAddress + ", Items=" + mItems + ", InternalComments=" + mInternalComments + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("OrderInfoData");
	root.setAttribute("Id", String.valueOf(mOrderInfo));

	Element node;

        node = doc.createElement("ShippingAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mShippingAddress)));
        root.appendChild(node);

        node = doc.createElement("BillingAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mBillingAddress)));
        root.appendChild(node);

        node = doc.createElement("Items");
        node.appendChild(doc.createTextNode(String.valueOf(mItems)));
        root.appendChild(node);

        node = doc.createElement("InternalComments");
        node.appendChild(doc.createTextNode(String.valueOf(mInternalComments)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public OrderInfoDataView copy()  {
      OrderInfoDataView obj = new OrderInfoDataView();
      obj.setOrderInfo(mOrderInfo);
      obj.setShippingAddress(mShippingAddress);
      obj.setBillingAddress(mBillingAddress);
      obj.setItems(mItems);
      obj.setInternalComments(mInternalComments);
      
      return obj;
    }

    
    /**
     * Sets the OrderInfo property.
     *
     * @param pOrderInfo
     *  OrderInfoView to use to update the property.
     */
    public void setOrderInfo(OrderInfoView pOrderInfo){
        this.mOrderInfo = pOrderInfo;
    }
    /**
     * Retrieves the OrderInfo property.
     *
     * @return
     *  OrderInfoView containing the OrderInfo property.
     */
    public OrderInfoView getOrderInfo(){
        return mOrderInfo;
    }


    /**
     * Sets the ShippingAddress property.
     *
     * @param pShippingAddress
     *  AddressInfoView to use to update the property.
     */
    public void setShippingAddress(AddressInfoView pShippingAddress){
        this.mShippingAddress = pShippingAddress;
    }
    /**
     * Retrieves the ShippingAddress property.
     *
     * @return
     *  AddressInfoView containing the ShippingAddress property.
     */
    public AddressInfoView getShippingAddress(){
        return mShippingAddress;
    }


    /**
     * Sets the BillingAddress property.
     *
     * @param pBillingAddress
     *  AddressInfoView to use to update the property.
     */
    public void setBillingAddress(AddressInfoView pBillingAddress){
        this.mBillingAddress = pBillingAddress;
    }
    /**
     * Retrieves the BillingAddress property.
     *
     * @return
     *  AddressInfoView containing the BillingAddress property.
     */
    public AddressInfoView getBillingAddress(){
        return mBillingAddress;
    }


    /**
     * Sets the Items property.
     *
     * @param pItems
     *  ItemInfoViewVector to use to update the property.
     */
    public void setItems(ItemInfoViewVector pItems){
        this.mItems = pItems;
    }
    /**
     * Retrieves the Items property.
     *
     * @return
     *  ItemInfoViewVector containing the Items property.
     */
    public ItemInfoViewVector getItems(){
        return mItems;
    }


    /**
     * Sets the InternalComments property.
     *
     * @param pInternalComments
     *  OrderPropertyDataVector to use to update the property.
     */
    public void setInternalComments(OrderPropertyDataVector pInternalComments){
        this.mInternalComments = pInternalComments;
    }
    /**
     * Retrieves the InternalComments property.
     *
     * @return
     *  OrderPropertyDataVector containing the InternalComments property.
     */
    public OrderPropertyDataVector getInternalComments(){
        return mInternalComments;
    }


    
}
