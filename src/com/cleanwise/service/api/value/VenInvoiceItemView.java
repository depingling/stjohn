
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        VenInvoiceItemView
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
 * <code>VenInvoiceItemView</code> is a ViewObject class for UI.
 */
public class VenInvoiceItemView
extends ValueObject
{
   
    private static final long serialVersionUID = 1026083093110877264L;
    private String mVendor;
    private int mOrderNbr;
    private String mVenInvoiceNum;
    private String mItem;
    private int mQty;
    private BigDecimal mLineAmt;
    private BigDecimal mItemCost;
    private int mResidual;

    /**
     * Constructor.
     */
    public VenInvoiceItemView ()
    {
        mVendor = "";
        mVenInvoiceNum = "";
        mItem = "";
    }

    /**
     * Constructor. 
     */
    public VenInvoiceItemView(String parm1, int parm2, String parm3, String parm4, int parm5, BigDecimal parm6, BigDecimal parm7, int parm8)
    {
        mVendor = parm1;
        mOrderNbr = parm2;
        mVenInvoiceNum = parm3;
        mItem = parm4;
        mQty = parm5;
        mLineAmt = parm6;
        mItemCost = parm7;
        mResidual = parm8;
        
    }

    /**
     * Creates a new VenInvoiceItemView
     *
     * @return
     *  Newly initialized VenInvoiceItemView object.
     */
    public static VenInvoiceItemView createValue () 
    {
        VenInvoiceItemView valueView = new VenInvoiceItemView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this VenInvoiceItemView object
     */
    public String toString()
    {
        return "[" + "Vendor=" + mVendor + ", OrderNbr=" + mOrderNbr + ", VenInvoiceNum=" + mVenInvoiceNum + ", Item=" + mItem + ", Qty=" + mQty + ", LineAmt=" + mLineAmt + ", ItemCost=" + mItemCost + ", Residual=" + mResidual + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("VenInvoiceItem");
	root.setAttribute("Id", String.valueOf(mVendor));

	Element node;

        node = doc.createElement("OrderNbr");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderNbr)));
        root.appendChild(node);

        node = doc.createElement("VenInvoiceNum");
        node.appendChild(doc.createTextNode(String.valueOf(mVenInvoiceNum)));
        root.appendChild(node);

        node = doc.createElement("Item");
        node.appendChild(doc.createTextNode(String.valueOf(mItem)));
        root.appendChild(node);

        node = doc.createElement("Qty");
        node.appendChild(doc.createTextNode(String.valueOf(mQty)));
        root.appendChild(node);

        node = doc.createElement("LineAmt");
        node.appendChild(doc.createTextNode(String.valueOf(mLineAmt)));
        root.appendChild(node);

        node = doc.createElement("ItemCost");
        node.appendChild(doc.createTextNode(String.valueOf(mItemCost)));
        root.appendChild(node);

        node = doc.createElement("Residual");
        node.appendChild(doc.createTextNode(String.valueOf(mResidual)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public VenInvoiceItemView copy()  {
      VenInvoiceItemView obj = new VenInvoiceItemView();
      obj.setVendor(mVendor);
      obj.setOrderNbr(mOrderNbr);
      obj.setVenInvoiceNum(mVenInvoiceNum);
      obj.setItem(mItem);
      obj.setQty(mQty);
      obj.setLineAmt(mLineAmt);
      obj.setItemCost(mItemCost);
      obj.setResidual(mResidual);
      
      return obj;
    }

    
    /**
     * Sets the Vendor property.
     *
     * @param pVendor
     *  String to use to update the property.
     */
    public void setVendor(String pVendor){
        this.mVendor = pVendor;
    }
    /**
     * Retrieves the Vendor property.
     *
     * @return
     *  String containing the Vendor property.
     */
    public String getVendor(){
        return mVendor;
    }


    /**
     * Sets the OrderNbr property.
     *
     * @param pOrderNbr
     *  int to use to update the property.
     */
    public void setOrderNbr(int pOrderNbr){
        this.mOrderNbr = pOrderNbr;
    }
    /**
     * Retrieves the OrderNbr property.
     *
     * @return
     *  int containing the OrderNbr property.
     */
    public int getOrderNbr(){
        return mOrderNbr;
    }


    /**
     * Sets the VenInvoiceNum property.
     *
     * @param pVenInvoiceNum
     *  String to use to update the property.
     */
    public void setVenInvoiceNum(String pVenInvoiceNum){
        this.mVenInvoiceNum = pVenInvoiceNum;
    }
    /**
     * Retrieves the VenInvoiceNum property.
     *
     * @return
     *  String containing the VenInvoiceNum property.
     */
    public String getVenInvoiceNum(){
        return mVenInvoiceNum;
    }


    /**
     * Sets the Item property.
     *
     * @param pItem
     *  String to use to update the property.
     */
    public void setItem(String pItem){
        this.mItem = pItem;
    }
    /**
     * Retrieves the Item property.
     *
     * @return
     *  String containing the Item property.
     */
    public String getItem(){
        return mItem;
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


    /**
     * Sets the LineAmt property.
     *
     * @param pLineAmt
     *  BigDecimal to use to update the property.
     */
    public void setLineAmt(BigDecimal pLineAmt){
        this.mLineAmt = pLineAmt;
    }
    /**
     * Retrieves the LineAmt property.
     *
     * @return
     *  BigDecimal containing the LineAmt property.
     */
    public BigDecimal getLineAmt(){
        return mLineAmt;
    }


    /**
     * Sets the ItemCost property.
     *
     * @param pItemCost
     *  BigDecimal to use to update the property.
     */
    public void setItemCost(BigDecimal pItemCost){
        this.mItemCost = pItemCost;
    }
    /**
     * Retrieves the ItemCost property.
     *
     * @return
     *  BigDecimal containing the ItemCost property.
     */
    public BigDecimal getItemCost(){
        return mItemCost;
    }


    /**
     * Sets the Residual property.
     *
     * @param pResidual
     *  int to use to update the property.
     */
    public void setResidual(int pResidual){
        this.mResidual = pResidual;
    }
    /**
     * Retrieves the Residual property.
     *
     * @return
     *  int containing the Residual property.
     */
    public int getResidual(){
        return mResidual;
    }


    
}
