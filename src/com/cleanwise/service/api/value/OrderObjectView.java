
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderObjectView
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
 * <code>OrderObjectView</code> is a ViewObject class for UI.
 */
public class OrderObjectView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private OrderData mOrder;
    private OrderItemDataVector mOrderItems;

    /**
     * Constructor.
     */
    public OrderObjectView ()
    {
    }

    /**
     * Constructor. 
     */
    public OrderObjectView(OrderData parm1, OrderItemDataVector parm2)
    {
        mOrder = parm1;
        mOrderItems = parm2;
        
    }

    /**
     * Creates a new OrderObjectView
     *
     * @return
     *  Newly initialized OrderObjectView object.
     */
    public static OrderObjectView createValue () 
    {
        OrderObjectView valueView = new OrderObjectView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderObjectView object
     */
    public String toString()
    {
        return "[" + "Order=" + mOrder + ", OrderItems=" + mOrderItems + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("OrderObject");
	root.setAttribute("Id", String.valueOf(mOrder));

	Element node;

        node = doc.createElement("OrderItems");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderItems)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public OrderObjectView copy()  {
      OrderObjectView obj = new OrderObjectView();
      obj.setOrder(mOrder);
      obj.setOrderItems(mOrderItems);
      
      return obj;
    }

    
    /**
     * Sets the Order property.
     *
     * @param pOrder
     *  OrderData to use to update the property.
     */
    public void setOrder(OrderData pOrder){
        this.mOrder = pOrder;
    }
    /**
     * Retrieves the Order property.
     *
     * @return
     *  OrderData containing the Order property.
     */
    public OrderData getOrder(){
        return mOrder;
    }


    /**
     * Sets the OrderItems property.
     *
     * @param pOrderItems
     *  OrderItemDataVector to use to update the property.
     */
    public void setOrderItems(OrderItemDataVector pOrderItems){
        this.mOrderItems = pOrderItems;
    }
    /**
     * Retrieves the OrderItems property.
     *
     * @return
     *  OrderItemDataVector containing the OrderItems property.
     */
    public OrderItemDataVector getOrderItems(){
        return mOrderItems;
    }


    
}
