
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InvoiceDistView
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
 * <code>InvoiceDistView</code> is a ViewObject class for UI.
 */
public class InvoiceDistView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private InvoiceDistData mInvoiceHeader;
    private InvoiceDistDetailDataVector mInvoiceLines;
    private OrderPropertyDataVector mOrderProperties;

    /**
     * Constructor.
     */
    public InvoiceDistView ()
    {
    }

    /**
     * Constructor. 
     */
    public InvoiceDistView(InvoiceDistData parm1, InvoiceDistDetailDataVector parm2, OrderPropertyDataVector parm3)
    {
        mInvoiceHeader = parm1;
        mInvoiceLines = parm2;
        mOrderProperties = parm3;
        
    }

    /**
     * Creates a new InvoiceDistView
     *
     * @return
     *  Newly initialized InvoiceDistView object.
     */
    public static InvoiceDistView createValue () 
    {
        InvoiceDistView valueView = new InvoiceDistView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InvoiceDistView object
     */
    public String toString()
    {
        return "[" + "InvoiceHeader=" + mInvoiceHeader + ", InvoiceLines=" + mInvoiceLines + ", OrderProperties=" + mOrderProperties + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("InvoiceDist");
	root.setAttribute("Id", String.valueOf(mInvoiceHeader));

	Element node;

        node = doc.createElement("InvoiceLines");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceLines)));
        root.appendChild(node);

        node = doc.createElement("OrderProperties");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderProperties)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public InvoiceDistView copy()  {
      InvoiceDistView obj = new InvoiceDistView();
      obj.setInvoiceHeader(mInvoiceHeader);
      obj.setInvoiceLines(mInvoiceLines);
      obj.setOrderProperties(mOrderProperties);
      
      return obj;
    }

    
    /**
     * Sets the InvoiceHeader property.
     *
     * @param pInvoiceHeader
     *  InvoiceDistData to use to update the property.
     */
    public void setInvoiceHeader(InvoiceDistData pInvoiceHeader){
        this.mInvoiceHeader = pInvoiceHeader;
    }
    /**
     * Retrieves the InvoiceHeader property.
     *
     * @return
     *  InvoiceDistData containing the InvoiceHeader property.
     */
    public InvoiceDistData getInvoiceHeader(){
        return mInvoiceHeader;
    }


    /**
     * Sets the InvoiceLines property.
     *
     * @param pInvoiceLines
     *  InvoiceDistDetailDataVector to use to update the property.
     */
    public void setInvoiceLines(InvoiceDistDetailDataVector pInvoiceLines){
        this.mInvoiceLines = pInvoiceLines;
    }
    /**
     * Retrieves the InvoiceLines property.
     *
     * @return
     *  InvoiceDistDetailDataVector containing the InvoiceLines property.
     */
    public InvoiceDistDetailDataVector getInvoiceLines(){
        return mInvoiceLines;
    }


    /**
     * Sets the OrderProperties property.
     *
     * @param pOrderProperties
     *  OrderPropertyDataVector to use to update the property.
     */
    public void setOrderProperties(OrderPropertyDataVector pOrderProperties){
        this.mOrderProperties = pOrderProperties;
    }
    /**
     * Retrieves the OrderProperties property.
     *
     * @return
     *  OrderPropertyDataVector containing the OrderProperties property.
     */
    public OrderPropertyDataVector getOrderProperties(){
        return mOrderProperties;
    }


    
}
