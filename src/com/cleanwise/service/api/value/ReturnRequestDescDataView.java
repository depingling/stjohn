
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ReturnRequestDescDataView
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
 * <code>ReturnRequestDescDataView</code> is a ViewObject class for UI.
 */
public class ReturnRequestDescDataView
extends ValueObject
{
   
    private static final long serialVersionUID = -6480349102043107776L;
    private ReturnRequestData mReturnRequestData;
    private OrderItemDescDataVector mReturnRequestOrderItemDescDataVector;
    private OrderAddressData mPickupAddress;

    /**
     * Constructor.
     */
    public ReturnRequestDescDataView ()
    {
    }

    /**
     * Constructor. 
     */
    public ReturnRequestDescDataView(ReturnRequestData parm1, OrderItemDescDataVector parm2, OrderAddressData parm3)
    {
        mReturnRequestData = parm1;
        mReturnRequestOrderItemDescDataVector = parm2;
        mPickupAddress = parm3;
        
    }

    /**
     * Creates a new ReturnRequestDescDataView
     *
     * @return
     *  Newly initialized ReturnRequestDescDataView object.
     */
    public static ReturnRequestDescDataView createValue () 
    {
        ReturnRequestDescDataView valueView = new ReturnRequestDescDataView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ReturnRequestDescDataView object
     */
    public String toString()
    {
        return "[" + "ReturnRequestData=" + mReturnRequestData + ", ReturnRequestOrderItemDescDataVector=" + mReturnRequestOrderItemDescDataVector + ", PickupAddress=" + mPickupAddress + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ReturnRequestDescData");
	root.setAttribute("Id", String.valueOf(mReturnRequestData));

	Element node;

        node = doc.createElement("ReturnRequestOrderItemDescDataVector");
        node.appendChild(doc.createTextNode(String.valueOf(mReturnRequestOrderItemDescDataVector)));
        root.appendChild(node);

        node = doc.createElement("PickupAddress");
        node.appendChild(doc.createTextNode(String.valueOf(mPickupAddress)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ReturnRequestDescDataView copy()  {
      ReturnRequestDescDataView obj = new ReturnRequestDescDataView();
      obj.setReturnRequestData(mReturnRequestData);
      obj.setReturnRequestOrderItemDescDataVector(mReturnRequestOrderItemDescDataVector);
      obj.setPickupAddress(mPickupAddress);
      
      return obj;
    }

    
    /**
     * Sets the ReturnRequestData property.
     *
     * @param pReturnRequestData
     *  ReturnRequestData to use to update the property.
     */
    public void setReturnRequestData(ReturnRequestData pReturnRequestData){
        this.mReturnRequestData = pReturnRequestData;
    }
    /**
     * Retrieves the ReturnRequestData property.
     *
     * @return
     *  ReturnRequestData containing the ReturnRequestData property.
     */
    public ReturnRequestData getReturnRequestData(){
        return mReturnRequestData;
    }


    /**
     * Sets the ReturnRequestOrderItemDescDataVector property.
     *
     * @param pReturnRequestOrderItemDescDataVector
     *  OrderItemDescDataVector to use to update the property.
     */
    public void setReturnRequestOrderItemDescDataVector(OrderItemDescDataVector pReturnRequestOrderItemDescDataVector){
        this.mReturnRequestOrderItemDescDataVector = pReturnRequestOrderItemDescDataVector;
    }
    /**
     * Retrieves the ReturnRequestOrderItemDescDataVector property.
     *
     * @return
     *  OrderItemDescDataVector containing the ReturnRequestOrderItemDescDataVector property.
     */
    public OrderItemDescDataVector getReturnRequestOrderItemDescDataVector(){
        return mReturnRequestOrderItemDescDataVector;
    }


    /**
     * Sets the PickupAddress property.
     *
     * @param pPickupAddress
     *  OrderAddressData to use to update the property.
     */
    public void setPickupAddress(OrderAddressData pPickupAddress){
        this.mPickupAddress = pPickupAddress;
    }
    /**
     * Retrieves the PickupAddress property.
     *
     * @return
     *  OrderAddressData containing the PickupAddress property.
     */
    public OrderAddressData getPickupAddress(){
        return mPickupAddress;
    }


    
}
