
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InvoiceCustCritView
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
 * <code>InvoiceCustCritView</code> is a ViewObject class for UI.
 */
public class InvoiceCustCritView
extends ValueObject
{
   
    private static final long serialVersionUID = -7781777848117504303L;
    private int mAccountId;
    private int mWebOrderNum;
    private int mStoreId;
    private java.util.Date mInvoiceDateRangeBegin;
    private java.util.Date mInvoiceDateRangeEnd;
    private String mInvoiceNumRangeBegin;
    private String mInvoiceNumRangeEnd;
    private int mInvoiceCustId;

    /**
     * Constructor.
     */
    public InvoiceCustCritView ()
    {
        mInvoiceNumRangeBegin = "";
        mInvoiceNumRangeEnd = "";
    }

    /**
     * Constructor. 
     */
    public InvoiceCustCritView(int parm1, int parm2, int parm3, java.util.Date parm4, java.util.Date parm5, String parm6, String parm7, int parm8)
    {
        mAccountId = parm1;
        mWebOrderNum = parm2;
        mStoreId = parm3;
        mInvoiceDateRangeBegin = parm4;
        mInvoiceDateRangeEnd = parm5;
        mInvoiceNumRangeBegin = parm6;
        mInvoiceNumRangeEnd = parm7;
        mInvoiceCustId = parm8;
        
    }

    /**
     * Creates a new InvoiceCustCritView
     *
     * @return
     *  Newly initialized InvoiceCustCritView object.
     */
    public static InvoiceCustCritView createValue () 
    {
        InvoiceCustCritView valueView = new InvoiceCustCritView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InvoiceCustCritView object
     */
    public String toString()
    {
        return "[" + "AccountId=" + mAccountId + ", WebOrderNum=" + mWebOrderNum + ", StoreId=" + mStoreId + ", InvoiceDateRangeBegin=" + mInvoiceDateRangeBegin + ", InvoiceDateRangeEnd=" + mInvoiceDateRangeEnd + ", InvoiceNumRangeBegin=" + mInvoiceNumRangeBegin + ", InvoiceNumRangeEnd=" + mInvoiceNumRangeEnd + ", InvoiceCustId=" + mInvoiceCustId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("InvoiceCustCrit");
	root.setAttribute("Id", String.valueOf(mAccountId));

	Element node;

        node = doc.createElement("WebOrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mWebOrderNum)));
        root.appendChild(node);

        node = doc.createElement("StoreId");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreId)));
        root.appendChild(node);

        node = doc.createElement("InvoiceDateRangeBegin");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceDateRangeBegin)));
        root.appendChild(node);

        node = doc.createElement("InvoiceDateRangeEnd");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceDateRangeEnd)));
        root.appendChild(node);

        node = doc.createElement("InvoiceNumRangeBegin");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceNumRangeBegin)));
        root.appendChild(node);

        node = doc.createElement("InvoiceNumRangeEnd");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceNumRangeEnd)));
        root.appendChild(node);

        node = doc.createElement("InvoiceCustId");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceCustId)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public InvoiceCustCritView copy()  {
      InvoiceCustCritView obj = new InvoiceCustCritView();
      obj.setAccountId(mAccountId);
      obj.setWebOrderNum(mWebOrderNum);
      obj.setStoreId(mStoreId);
      obj.setInvoiceDateRangeBegin(mInvoiceDateRangeBegin);
      obj.setInvoiceDateRangeEnd(mInvoiceDateRangeEnd);
      obj.setInvoiceNumRangeBegin(mInvoiceNumRangeBegin);
      obj.setInvoiceNumRangeEnd(mInvoiceNumRangeEnd);
      obj.setInvoiceCustId(mInvoiceCustId);
      
      return obj;
    }

    
    /**
     * Sets the AccountId property.
     *
     * @param pAccountId
     *  int to use to update the property.
     */
    public void setAccountId(int pAccountId){
        this.mAccountId = pAccountId;
    }
    /**
     * Retrieves the AccountId property.
     *
     * @return
     *  int containing the AccountId property.
     */
    public int getAccountId(){
        return mAccountId;
    }


    /**
     * Sets the WebOrderNum property.
     *
     * @param pWebOrderNum
     *  int to use to update the property.
     */
    public void setWebOrderNum(int pWebOrderNum){
        this.mWebOrderNum = pWebOrderNum;
    }
    /**
     * Retrieves the WebOrderNum property.
     *
     * @return
     *  int containing the WebOrderNum property.
     */
    public int getWebOrderNum(){
        return mWebOrderNum;
    }


    /**
     * Sets the StoreId property.
     *
     * @param pStoreId
     *  int to use to update the property.
     */
    public void setStoreId(int pStoreId){
        this.mStoreId = pStoreId;
    }
    /**
     * Retrieves the StoreId property.
     *
     * @return
     *  int containing the StoreId property.
     */
    public int getStoreId(){
        return mStoreId;
    }


    /**
     * Sets the InvoiceDateRangeBegin property.
     *
     * @param pInvoiceDateRangeBegin
     *  java.util.Date to use to update the property.
     */
    public void setInvoiceDateRangeBegin(java.util.Date pInvoiceDateRangeBegin){
        this.mInvoiceDateRangeBegin = pInvoiceDateRangeBegin;
    }
    /**
     * Retrieves the InvoiceDateRangeBegin property.
     *
     * @return
     *  java.util.Date containing the InvoiceDateRangeBegin property.
     */
    public java.util.Date getInvoiceDateRangeBegin(){
        return mInvoiceDateRangeBegin;
    }


    /**
     * Sets the InvoiceDateRangeEnd property.
     *
     * @param pInvoiceDateRangeEnd
     *  java.util.Date to use to update the property.
     */
    public void setInvoiceDateRangeEnd(java.util.Date pInvoiceDateRangeEnd){
        this.mInvoiceDateRangeEnd = pInvoiceDateRangeEnd;
    }
    /**
     * Retrieves the InvoiceDateRangeEnd property.
     *
     * @return
     *  java.util.Date containing the InvoiceDateRangeEnd property.
     */
    public java.util.Date getInvoiceDateRangeEnd(){
        return mInvoiceDateRangeEnd;
    }


    /**
     * Sets the InvoiceNumRangeBegin property.
     *
     * @param pInvoiceNumRangeBegin
     *  String to use to update the property.
     */
    public void setInvoiceNumRangeBegin(String pInvoiceNumRangeBegin){
        this.mInvoiceNumRangeBegin = pInvoiceNumRangeBegin;
    }
    /**
     * Retrieves the InvoiceNumRangeBegin property.
     *
     * @return
     *  String containing the InvoiceNumRangeBegin property.
     */
    public String getInvoiceNumRangeBegin(){
        return mInvoiceNumRangeBegin;
    }


    /**
     * Sets the InvoiceNumRangeEnd property.
     *
     * @param pInvoiceNumRangeEnd
     *  String to use to update the property.
     */
    public void setInvoiceNumRangeEnd(String pInvoiceNumRangeEnd){
        this.mInvoiceNumRangeEnd = pInvoiceNumRangeEnd;
    }
    /**
     * Retrieves the InvoiceNumRangeEnd property.
     *
     * @return
     *  String containing the InvoiceNumRangeEnd property.
     */
    public String getInvoiceNumRangeEnd(){
        return mInvoiceNumRangeEnd;
    }


    /**
     * Sets the InvoiceCustId property.
     *
     * @param pInvoiceCustId
     *  int to use to update the property.
     */
    public void setInvoiceCustId(int pInvoiceCustId){
        this.mInvoiceCustId = pInvoiceCustId;
    }
    /**
     * Retrieves the InvoiceCustId property.
     *
     * @return
     *  int containing the InvoiceCustId property.
     */
    public int getInvoiceCustId(){
        return mInvoiceCustId;
    }


    
}
