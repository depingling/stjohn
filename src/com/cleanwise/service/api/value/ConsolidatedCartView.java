
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ConsolidatedCartView
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
 * <code>ConsolidatedCartView</code> is a ViewObject class for UI.
 */
public class ConsolidatedCartView
extends ShoppingCartData //ValueObject
{
   
    private static final long serialVersionUID = 2815750658611937989L;
    private ArrayList mOrders;

    /**
     * Constructor.
     */
    public ConsolidatedCartView ()
    {
    }

    /**
     * Constructor. 
     */
    public ConsolidatedCartView(ArrayList parm1)
    {
        mOrders = parm1;
        
    }

    /**
     * Creates a new ConsolidatedCartView
     *
     * @return
     *  Newly initialized ConsolidatedCartView object.
     */
    public static ConsolidatedCartView createValue () 
    {
        ConsolidatedCartView valueView = new ConsolidatedCartView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ConsolidatedCartView object
     */
    public String toString()
    {
        return "[" + "Orders=" + mOrders + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ConsolidatedCart");
	root.setAttribute("Id", String.valueOf(mOrders));

	Element node;

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ConsolidatedCartView copy()  {
      ConsolidatedCartView obj = new ConsolidatedCartView();
      obj.setOrders(mOrders);
      
      return obj;
    }

    
    /**
     * Sets the Orders property.
     *
     * @param pOrders
     *  ArrayList to use to update the property.
     */
    public void setOrders(ArrayList pOrders){
        this.mOrders = pOrders;
    }
    /**
     * Retrieves the Orders property.
     *
     * @return
     *  ArrayList containing the Orders property.
     */
    public ArrayList getOrders(){
        return mOrders;
    }


    
}
