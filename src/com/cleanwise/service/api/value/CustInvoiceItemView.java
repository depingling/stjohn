
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CustInvoiceItemView
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
 * <code>CustInvoiceItemView</code> is a ViewObject class for UI.
 */
public class CustInvoiceItemView
extends ValueObject
{
   
    private static final long serialVersionUID = -4704612720687071664L;
    private String mCustomer;
    private int mOrderNbr;
    private String mCustInvoicePrefix;
    private BigDecimal mCustInvoiceNum;
    private String mItem;
    private BigDecimal mLineAmt;
    private int mQty;
    private BigDecimal mItemPrice;
    private int mResidual;

    /**
     * Constructor.
     */
    public CustInvoiceItemView ()
    {
        mCustomer = "";
        mCustInvoicePrefix = "";
        mItem = "";
    }

    /**
     * Constructor. 
     */
    public CustInvoiceItemView(String parm1, int parm2, String parm3, BigDecimal parm4, String parm5, BigDecimal parm6, int parm7, BigDecimal parm8, int parm9)
    {
        mCustomer = parm1;
        mOrderNbr = parm2;
        mCustInvoicePrefix = parm3;
        mCustInvoiceNum = parm4;
        mItem = parm5;
        mLineAmt = parm6;
        mQty = parm7;
        mItemPrice = parm8;
        mResidual = parm9;
        
    }

    /**
     * Creates a new CustInvoiceItemView
     *
     * @return
     *  Newly initialized CustInvoiceItemView object.
     */
    public static CustInvoiceItemView createValue () 
    {
        CustInvoiceItemView valueView = new CustInvoiceItemView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CustInvoiceItemView object
     */
    public String toString()
    {
        return "[" + "Customer=" + mCustomer + ", OrderNbr=" + mOrderNbr + ", CustInvoicePrefix=" + mCustInvoicePrefix + ", CustInvoiceNum=" + mCustInvoiceNum + ", Item=" + mItem + ", LineAmt=" + mLineAmt + ", Qty=" + mQty + ", ItemPrice=" + mItemPrice + ", Residual=" + mResidual + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("CustInvoiceItem");
	root.setAttribute("Id", String.valueOf(mCustomer));

	Element node;

        node = doc.createElement("OrderNbr");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderNbr)));
        root.appendChild(node);

        node = doc.createElement("CustInvoicePrefix");
        node.appendChild(doc.createTextNode(String.valueOf(mCustInvoicePrefix)));
        root.appendChild(node);

        node = doc.createElement("CustInvoiceNum");
        node.appendChild(doc.createTextNode(String.valueOf(mCustInvoiceNum)));
        root.appendChild(node);

        node = doc.createElement("Item");
        node.appendChild(doc.createTextNode(String.valueOf(mItem)));
        root.appendChild(node);

        node = doc.createElement("LineAmt");
        node.appendChild(doc.createTextNode(String.valueOf(mLineAmt)));
        root.appendChild(node);

        node = doc.createElement("Qty");
        node.appendChild(doc.createTextNode(String.valueOf(mQty)));
        root.appendChild(node);

        node = doc.createElement("ItemPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mItemPrice)));
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
    public CustInvoiceItemView copy()  {
      CustInvoiceItemView obj = new CustInvoiceItemView();
      obj.setCustomer(mCustomer);
      obj.setOrderNbr(mOrderNbr);
      obj.setCustInvoicePrefix(mCustInvoicePrefix);
      obj.setCustInvoiceNum(mCustInvoiceNum);
      obj.setItem(mItem);
      obj.setLineAmt(mLineAmt);
      obj.setQty(mQty);
      obj.setItemPrice(mItemPrice);
      obj.setResidual(mResidual);
      
      return obj;
    }

    
    /**
     * Sets the Customer property.
     *
     * @param pCustomer
     *  String to use to update the property.
     */
    public void setCustomer(String pCustomer){
        this.mCustomer = pCustomer;
    }
    /**
     * Retrieves the Customer property.
     *
     * @return
     *  String containing the Customer property.
     */
    public String getCustomer(){
        return mCustomer;
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
     * Sets the CustInvoicePrefix property.
     *
     * @param pCustInvoicePrefix
     *  String to use to update the property.
     */
    public void setCustInvoicePrefix(String pCustInvoicePrefix){
        this.mCustInvoicePrefix = pCustInvoicePrefix;
    }
    /**
     * Retrieves the CustInvoicePrefix property.
     *
     * @return
     *  String containing the CustInvoicePrefix property.
     */
    public String getCustInvoicePrefix(){
        return mCustInvoicePrefix;
    }


    /**
     * Sets the CustInvoiceNum property.
     *
     * @param pCustInvoiceNum
     *  BigDecimal to use to update the property.
     */
    public void setCustInvoiceNum(BigDecimal pCustInvoiceNum){
        this.mCustInvoiceNum = pCustInvoiceNum;
    }
    /**
     * Retrieves the CustInvoiceNum property.
     *
     * @return
     *  BigDecimal containing the CustInvoiceNum property.
     */
    public BigDecimal getCustInvoiceNum(){
        return mCustInvoiceNum;
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
     * Sets the ItemPrice property.
     *
     * @param pItemPrice
     *  BigDecimal to use to update the property.
     */
    public void setItemPrice(BigDecimal pItemPrice){
        this.mItemPrice = pItemPrice;
    }
    /**
     * Retrieves the ItemPrice property.
     *
     * @return
     *  BigDecimal containing the ItemPrice property.
     */
    public BigDecimal getItemPrice(){
        return mItemPrice;
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
