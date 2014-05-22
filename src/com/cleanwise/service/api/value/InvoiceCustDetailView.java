
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InvoiceCustDetailView
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
 * <code>InvoiceCustDetailView</code> is a ViewObject class for UI.
 */
public class InvoiceCustDetailView
extends ValueObject
{
   
    private static final long serialVersionUID = -5641177466156734254L;
    private OrderItemData mOrderItemData;
    private InvoiceCustDetailData mInvoiceCustDetailData;

    /**
     * Constructor.
     */
    public InvoiceCustDetailView ()
    {
    }

    /**
     * Constructor. 
     */
    public InvoiceCustDetailView(OrderItemData parm1, InvoiceCustDetailData parm2)
    {
        mOrderItemData = parm1;
        mInvoiceCustDetailData = parm2;
        
    }

    /**
     * Creates a new InvoiceCustDetailView
     *
     * @return
     *  Newly initialized InvoiceCustDetailView object.
     */
    public static InvoiceCustDetailView createValue () 
    {
        InvoiceCustDetailView valueView = new InvoiceCustDetailView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InvoiceCustDetailView object
     */
    public String toString()
    {
        return "[" + "OrderItemData=" + mOrderItemData + ", InvoiceCustDetailData=" + mInvoiceCustDetailData + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("InvoiceCustDetail");
	root.setAttribute("Id", String.valueOf(mOrderItemData));

	Element node;

        node = doc.createElement("InvoiceCustDetailData");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceCustDetailData)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public InvoiceCustDetailView copy()  {
      InvoiceCustDetailView obj = new InvoiceCustDetailView();
      obj.setOrderItemData(mOrderItemData);
      obj.setInvoiceCustDetailData(mInvoiceCustDetailData);
      
      return obj;
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
     * Sets the InvoiceCustDetailData property.
     *
     * @param pInvoiceCustDetailData
     *  InvoiceCustDetailData to use to update the property.
     */
    public void setInvoiceCustDetailData(InvoiceCustDetailData pInvoiceCustDetailData){
        this.mInvoiceCustDetailData = pInvoiceCustDetailData;
    }
    /**
     * Retrieves the InvoiceCustDetailData property.
     *
     * @return
     *  InvoiceCustDetailData containing the InvoiceCustDetailData property.
     */
    public InvoiceCustDetailData getInvoiceCustDetailData(){
        return mInvoiceCustDetailData;
    }


    
}
