
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        JdOrderStatusView
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

import java.math.BigDecimal;import java.util.Date;


/**
 * <code>JdOrderStatusView</code> is a ViewObject class for UI.
 */
public class JdOrderStatusView
extends ValueObject
{
   
    private static final long serialVersionUID = -4630014140112906655L;
    private int mOrderId;
    private Date mOrderDate;
    private String mCustomerPoNum;
    private String mOrderNum;
    private String mAccountNum;
    private String mCompany;
    private BigDecimal mTotalPrice;
    private BigDecimal mTotalWeight;

    /**
     * Constructor.
     */
    public JdOrderStatusView ()
    {
        mCustomerPoNum = "";
        mOrderNum = "";
        mAccountNum = "";
        mCompany = "";
    }

    /**
     * Constructor. 
     */
    public JdOrderStatusView(int parm1, Date parm2, String parm3, String parm4, String parm5, String parm6, BigDecimal parm7, BigDecimal parm8)
    {
        mOrderId = parm1;
        mOrderDate = parm2;
        mCustomerPoNum = parm3;
        mOrderNum = parm4;
        mAccountNum = parm5;
        mCompany = parm6;
        mTotalPrice = parm7;
        mTotalWeight = parm8;
        
    }

    /**
     * Creates a new JdOrderStatusView
     *
     * @return
     *  Newly initialized JdOrderStatusView object.
     */
    public static JdOrderStatusView createValue () 
    {
        JdOrderStatusView valueView = new JdOrderStatusView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this JdOrderStatusView object
     */
    public String toString()
    {
        return "[" + "OrderId=" + mOrderId + ", OrderDate=" + mOrderDate + ", CustomerPoNum=" + mCustomerPoNum + ", OrderNum=" + mOrderNum + ", AccountNum=" + mAccountNum + ", Company=" + mCompany + ", TotalPrice=" + mTotalPrice + ", TotalWeight=" + mTotalWeight + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("JdOrderStatus");
	root.setAttribute("Id", String.valueOf(mOrderId));

	Element node;

        node = doc.createElement("OrderDate");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderDate)));
        root.appendChild(node);

        node = doc.createElement("CustomerPoNum");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerPoNum)));
        root.appendChild(node);

        node = doc.createElement("OrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderNum)));
        root.appendChild(node);

        node = doc.createElement("AccountNum");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountNum)));
        root.appendChild(node);

        node = doc.createElement("Company");
        node.appendChild(doc.createTextNode(String.valueOf(mCompany)));
        root.appendChild(node);

        node = doc.createElement("TotalPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalPrice)));
        root.appendChild(node);

        node = doc.createElement("TotalWeight");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalWeight)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public JdOrderStatusView copy()  {
      JdOrderStatusView obj = new JdOrderStatusView();
      obj.setOrderId(mOrderId);
      obj.setOrderDate(mOrderDate);
      obj.setCustomerPoNum(mCustomerPoNum);
      obj.setOrderNum(mOrderNum);
      obj.setAccountNum(mAccountNum);
      obj.setCompany(mCompany);
      obj.setTotalPrice(mTotalPrice);
      obj.setTotalWeight(mTotalWeight);
      
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
     * Sets the CustomerPoNum property.
     *
     * @param pCustomerPoNum
     *  String to use to update the property.
     */
    public void setCustomerPoNum(String pCustomerPoNum){
        this.mCustomerPoNum = pCustomerPoNum;
    }
    /**
     * Retrieves the CustomerPoNum property.
     *
     * @return
     *  String containing the CustomerPoNum property.
     */
    public String getCustomerPoNum(){
        return mCustomerPoNum;
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
     * Sets the AccountNum property.
     *
     * @param pAccountNum
     *  String to use to update the property.
     */
    public void setAccountNum(String pAccountNum){
        this.mAccountNum = pAccountNum;
    }
    /**
     * Retrieves the AccountNum property.
     *
     * @return
     *  String containing the AccountNum property.
     */
    public String getAccountNum(){
        return mAccountNum;
    }


    /**
     * Sets the Company property.
     *
     * @param pCompany
     *  String to use to update the property.
     */
    public void setCompany(String pCompany){
        this.mCompany = pCompany;
    }
    /**
     * Retrieves the Company property.
     *
     * @return
     *  String containing the Company property.
     */
    public String getCompany(){
        return mCompany;
    }


    /**
     * Sets the TotalPrice property.
     *
     * @param pTotalPrice
     *  BigDecimal to use to update the property.
     */
    public void setTotalPrice(BigDecimal pTotalPrice){
        this.mTotalPrice = pTotalPrice;
    }
    /**
     * Retrieves the TotalPrice property.
     *
     * @return
     *  BigDecimal containing the TotalPrice property.
     */
    public BigDecimal getTotalPrice(){
        return mTotalPrice;
    }


    /**
     * Sets the TotalWeight property.
     *
     * @param pTotalWeight
     *  BigDecimal to use to update the property.
     */
    public void setTotalWeight(BigDecimal pTotalWeight){
        this.mTotalWeight = pTotalWeight;
    }
    /**
     * Retrieves the TotalWeight property.
     *
     * @return
     *  BigDecimal containing the TotalWeight property.
     */
    public BigDecimal getTotalWeight(){
        return mTotalWeight;
    }


    
}
