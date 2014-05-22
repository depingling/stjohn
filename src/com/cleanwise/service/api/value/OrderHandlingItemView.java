
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderHandlingItemView
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
 * <code>OrderHandlingItemView</code> is a ViewObject class for UI.
 */
public class OrderHandlingItemView
extends ValueObject
{
   
    private static final long serialVersionUID = 4003613295796181050L;
    private int mItemId;
    private BigDecimal mPrice;
    private BigDecimal mWeight;
    private int mQty;

    /**
     * Constructor.
     */
    public OrderHandlingItemView ()
    {
    }

    /**
     * Constructor. 
     */
    public OrderHandlingItemView(int parm1, BigDecimal parm2, BigDecimal parm3, int parm4)
    {
        mItemId = parm1;
        mPrice = parm2;
        mWeight = parm3;
        mQty = parm4;
        
    }

    /**
     * Creates a new OrderHandlingItemView
     *
     * @return
     *  Newly initialized OrderHandlingItemView object.
     */
    public static OrderHandlingItemView createValue () 
    {
        OrderHandlingItemView valueView = new OrderHandlingItemView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderHandlingItemView object
     */
    public String toString()
    {
        return "[" + "ItemId=" + mItemId + ", Price=" + mPrice + ", Weight=" + mWeight + ", Qty=" + mQty + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("OrderHandlingItem");
	root.setAttribute("Id", String.valueOf(mItemId));

	Element node;

        node = doc.createElement("Price");
        node.appendChild(doc.createTextNode(String.valueOf(mPrice)));
        root.appendChild(node);

        node = doc.createElement("Weight");
        node.appendChild(doc.createTextNode(String.valueOf(mWeight)));
        root.appendChild(node);

        node = doc.createElement("Qty");
        node.appendChild(doc.createTextNode(String.valueOf(mQty)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public OrderHandlingItemView copy()  {
      OrderHandlingItemView obj = new OrderHandlingItemView();
      obj.setItemId(mItemId);
      obj.setPrice(mPrice);
      obj.setWeight(mWeight);
      obj.setQty(mQty);
      
      return obj;
    }

    
    /**
     * Sets the ItemId property.
     *
     * @param pItemId
     *  int to use to update the property.
     */
    public void setItemId(int pItemId){
        this.mItemId = pItemId;
    }
    /**
     * Retrieves the ItemId property.
     *
     * @return
     *  int containing the ItemId property.
     */
    public int getItemId(){
        return mItemId;
    }


    /**
     * Sets the Price property.
     *
     * @param pPrice
     *  BigDecimal to use to update the property.
     */
    public void setPrice(BigDecimal pPrice){
        this.mPrice = pPrice;
    }
    /**
     * Retrieves the Price property.
     *
     * @return
     *  BigDecimal containing the Price property.
     */
    public BigDecimal getPrice(){
        return mPrice;
    }


    /**
     * Sets the Weight property.
     *
     * @param pWeight
     *  BigDecimal to use to update the property.
     */
    public void setWeight(BigDecimal pWeight){
        this.mWeight = pWeight;
    }
    /**
     * Retrieves the Weight property.
     *
     * @return
     *  BigDecimal containing the Weight property.
     */
    public BigDecimal getWeight(){
        return mWeight;
    }


    /**
     * Sets the Qty property.
     *
     * @param pQty
     *  int to use to update the property.
     */
    public void setQty(int pQty){
        this.mQty = pQty;
    }
    /**
     * Retrieves the Qty property.
     *
     * @return
     *  int containing the Qty property.
     */
    public int getQty(){
        return mQty;
    }


    
}
