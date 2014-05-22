
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InvoiceCustView
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

import com.cleanwise.service.api.value.*;


/**
 * <code>InvoiceCustView</code> is a ViewObject class for UI.
 */
public class InvoiceCustView
extends ValueObject
{
   
    private static final long serialVersionUID = -1120961312122327943L;
    private InvoiceCustData mInvoiceCustData;
    private OrderData mOrderData;
    private InvoiceCustDetailDataVector mInvoiceCustDetailDataVector;
    private InvoiceCustInfoViewVector mInvoiceCustInfoViewVector;

    /**
     * Constructor.
     */
    public InvoiceCustView ()
    {
    }

    /**
     * Constructor. 
     */
    public InvoiceCustView(InvoiceCustData parm1, OrderData parm2, InvoiceCustDetailDataVector parm3, InvoiceCustInfoViewVector parm4)
    {
        mInvoiceCustData = parm1;
        mOrderData = parm2;
        mInvoiceCustDetailDataVector = parm3;
        mInvoiceCustInfoViewVector = parm4;
        
    }

    /**
     * Creates a new InvoiceCustView
     *
     * @return
     *  Newly initialized InvoiceCustView object.
     */
    public static InvoiceCustView createValue () 
    {
        InvoiceCustView valueView = new InvoiceCustView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InvoiceCustView object
     */
    public String toString()
    {
        return "[" + "InvoiceCustData=" + mInvoiceCustData + ", OrderData=" + mOrderData + ", InvoiceCustDetailDataVector=" + mInvoiceCustDetailDataVector + ", InvoiceCustInfoViewVector=" + mInvoiceCustInfoViewVector + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("InvoiceCust");
	root.setAttribute("Id", String.valueOf(mInvoiceCustData));

	Element node;

        node = doc.createElement("OrderData");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderData)));
        root.appendChild(node);

        node = doc.createElement("InvoiceCustDetailDataVector");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceCustDetailDataVector)));
        root.appendChild(node);

        node = doc.createElement("InvoiceCustInfoViewVector");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceCustInfoViewVector)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public InvoiceCustView copy()  {
      InvoiceCustView obj = new InvoiceCustView();
      obj.setInvoiceCustData(mInvoiceCustData);
      obj.setOrderData(mOrderData);
      obj.setInvoiceCustDetailDataVector(mInvoiceCustDetailDataVector);
      obj.setInvoiceCustInfoViewVector(mInvoiceCustInfoViewVector);
      
      return obj;
    }

    
    /**
     * Sets the InvoiceCustData property.
     *
     * @param pInvoiceCustData
     *  InvoiceCustData to use to update the property.
     */
    public void setInvoiceCustData(InvoiceCustData pInvoiceCustData){
        this.mInvoiceCustData = pInvoiceCustData;
    }
    /**
     * Retrieves the InvoiceCustData property.
     *
     * @return
     *  InvoiceCustData containing the InvoiceCustData property.
     */
    public InvoiceCustData getInvoiceCustData(){
        return mInvoiceCustData;
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
     * Sets the InvoiceCustDetailDataVector property.
     *
     * @param pInvoiceCustDetailDataVector
     *  InvoiceCustDetailDataVector to use to update the property.
     */
    public void setInvoiceCustDetailDataVector(InvoiceCustDetailDataVector pInvoiceCustDetailDataVector){
        this.mInvoiceCustDetailDataVector = pInvoiceCustDetailDataVector;
    }
    /**
     * Retrieves the InvoiceCustDetailDataVector property.
     *
     * @return
     *  InvoiceCustDetailDataVector containing the InvoiceCustDetailDataVector property.
     */
    public InvoiceCustDetailDataVector getInvoiceCustDetailDataVector(){
        return mInvoiceCustDetailDataVector;
    }


    /**
     * Sets the InvoiceCustInfoViewVector property.
     *
     * @param pInvoiceCustInfoViewVector
     *  InvoiceCustInfoViewVector to use to update the property.
     */
    public void setInvoiceCustInfoViewVector(InvoiceCustInfoViewVector pInvoiceCustInfoViewVector){
        this.mInvoiceCustInfoViewVector = pInvoiceCustInfoViewVector;
    }
    /**
     * Retrieves the InvoiceCustInfoViewVector property.
     *
     * @return
     *  InvoiceCustInfoViewVector containing the InvoiceCustInfoViewVector property.
     */
    public InvoiceCustInfoViewVector getInvoiceCustInfoViewVector(){
        return mInvoiceCustInfoViewVector;
    }


    
}
