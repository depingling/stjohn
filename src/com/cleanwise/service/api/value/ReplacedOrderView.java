
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ReplacedOrderView
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
 * <code>ReplacedOrderView</code> is a ViewObject class for UI.
 */
public class ReplacedOrderView
extends ValueObject
{
   
    private static final long serialVersionUID = -9008813123091417212L;
    private int mOrderId;
    private String mOrderNum;
    private String mRefOrderNum;
    private String mRequestPoNum;
    private Date mOrderDate;
    private String mOrderSiteName;
    private ReplacedOrderItemViewVector mItems;

    /**
     * Constructor.
     */
    public ReplacedOrderView ()
    {
        mOrderNum = "";
        mRefOrderNum = "";
        mRequestPoNum = "";
        mOrderSiteName = "";
    }

    /**
     * Constructor. 
     */
    public ReplacedOrderView(int parm1, String parm2, String parm3, String parm4, Date parm5, String parm6, ReplacedOrderItemViewVector parm7)
    {
        mOrderId = parm1;
        mOrderNum = parm2;
        mRefOrderNum = parm3;
        mRequestPoNum = parm4;
        mOrderDate = parm5;
        mOrderSiteName = parm6;
        mItems = parm7;
        
    }

    /**
     * Creates a new ReplacedOrderView
     *
     * @return
     *  Newly initialized ReplacedOrderView object.
     */
    public static ReplacedOrderView createValue () 
    {
        ReplacedOrderView valueView = new ReplacedOrderView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ReplacedOrderView object
     */
    public String toString()
    {
        return "[" + "OrderId=" + mOrderId + ", OrderNum=" + mOrderNum + ", RefOrderNum=" + mRefOrderNum + ", RequestPoNum=" + mRequestPoNum + ", OrderDate=" + mOrderDate + ", OrderSiteName=" + mOrderSiteName + ", Items=" + mItems + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ReplacedOrder");
	root.setAttribute("Id", String.valueOf(mOrderId));

	Element node;

        node = doc.createElement("OrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderNum)));
        root.appendChild(node);

        node = doc.createElement("RefOrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mRefOrderNum)));
        root.appendChild(node);

        node = doc.createElement("RequestPoNum");
        node.appendChild(doc.createTextNode(String.valueOf(mRequestPoNum)));
        root.appendChild(node);

        node = doc.createElement("OrderDate");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderDate)));
        root.appendChild(node);

        node = doc.createElement("OrderSiteName");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderSiteName)));
        root.appendChild(node);

        node = doc.createElement("Items");
        node.appendChild(doc.createTextNode(String.valueOf(mItems)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ReplacedOrderView copy()  {
      ReplacedOrderView obj = new ReplacedOrderView();
      obj.setOrderId(mOrderId);
      obj.setOrderNum(mOrderNum);
      obj.setRefOrderNum(mRefOrderNum);
      obj.setRequestPoNum(mRequestPoNum);
      obj.setOrderDate(mOrderDate);
      obj.setOrderSiteName(mOrderSiteName);
      obj.setItems(mItems);
      
      return obj;
    }

    
    /**
     * Sets the OrderId property.
     *
     * @param pOrderId
     *  int to use to update the property.
     */
    public void setOrderId(int pOrderId){
        this.mOrderId = pOrderId;
    }
    /**
     * Retrieves the OrderId property.
     *
     * @return
     *  int containing the OrderId property.
     */
    public int getOrderId(){
        return mOrderId;
    }


    /**
     * Sets the OrderNum property.
     *
     * @param pOrderNum
     *  String to use to update the property.
     */
    public void setOrderNum(String pOrderNum){
        this.mOrderNum = pOrderNum;
    }
    /**
     * Retrieves the OrderNum property.
     *
     * @return
     *  String containing the OrderNum property.
     */
    public String getOrderNum(){
        return mOrderNum;
    }


    /**
     * Sets the RefOrderNum property.
     *
     * @param pRefOrderNum
     *  String to use to update the property.
     */
    public void setRefOrderNum(String pRefOrderNum){
        this.mRefOrderNum = pRefOrderNum;
    }
    /**
     * Retrieves the RefOrderNum property.
     *
     * @return
     *  String containing the RefOrderNum property.
     */
    public String getRefOrderNum(){
        return mRefOrderNum;
    }


    /**
     * Sets the RequestPoNum property.
     *
     * @param pRequestPoNum
     *  String to use to update the property.
     */
    public void setRequestPoNum(String pRequestPoNum){
        this.mRequestPoNum = pRequestPoNum;
    }
    /**
     * Retrieves the RequestPoNum property.
     *
     * @return
     *  String containing the RequestPoNum property.
     */
    public String getRequestPoNum(){
        return mRequestPoNum;
    }


    /**
     * Sets the OrderDate property.
     *
     * @param pOrderDate
     *  Date to use to update the property.
     */
    public void setOrderDate(Date pOrderDate){
        this.mOrderDate = pOrderDate;
    }
    /**
     * Retrieves the OrderDate property.
     *
     * @return
     *  Date containing the OrderDate property.
     */
    public Date getOrderDate(){
        return mOrderDate;
    }


    /**
     * Sets the OrderSiteName property.
     *
     * @param pOrderSiteName
     *  String to use to update the property.
     */
    public void setOrderSiteName(String pOrderSiteName){
        this.mOrderSiteName = pOrderSiteName;
    }
    /**
     * Retrieves the OrderSiteName property.
     *
     * @return
     *  String containing the OrderSiteName property.
     */
    public String getOrderSiteName(){
        return mOrderSiteName;
    }


    /**
     * Sets the Items property.
     *
     * @param pItems
     *  ReplacedOrderItemViewVector to use to update the property.
     */
    public void setItems(ReplacedOrderItemViewVector pItems){
        this.mItems = pItems;
    }
    /**
     * Retrieves the Items property.
     *
     * @return
     *  ReplacedOrderItemViewVector containing the Items property.
     */
    public ReplacedOrderItemViewVector getItems(){
        return mItems;
    }


    
}
