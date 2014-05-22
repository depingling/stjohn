
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderHandlingView
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
 * <code>OrderHandlingView</code> is a ViewObject class for UI.
 */
public class OrderHandlingView
extends ValueObject
{
   
    private static final long serialVersionUID = 500064552983363620L;
    private BigDecimal mTotalHandling;
    private BigDecimal mTotalFreight;
    private int mContractId;
    private int mSiteId;
    private int mAccountId;
    private BigDecimal mAmount;
    private BigDecimal mWeight;
    private OrderHandlingItemViewVector mItems;
    private OrderHandlingDetailViewVector mDetail;

    /**
     * Constructor.
     */
    public OrderHandlingView ()
    {
    }

    /**
     * Constructor. 
     */
    public OrderHandlingView(BigDecimal parm1, BigDecimal parm2, int parm3, int parm4, int parm5, BigDecimal parm6, BigDecimal parm7, OrderHandlingItemViewVector parm8, OrderHandlingDetailViewVector parm9)
    {
        mTotalHandling = parm1;
        mTotalFreight = parm2;
        mContractId = parm3;
        mSiteId = parm4;
        mAccountId = parm5;
        mAmount = parm6;
        mWeight = parm7;
        mItems = parm8;
        mDetail = parm9;
        
    }

    /**
     * Creates a new OrderHandlingView
     *
     * @return
     *  Newly initialized OrderHandlingView object.
     */
    public static OrderHandlingView createValue () 
    {
        OrderHandlingView valueView = new OrderHandlingView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderHandlingView object
     */
    public String toString()
    {
        return "[" + "TotalHandling=" + mTotalHandling + ", TotalFreight=" + mTotalFreight + ", ContractId=" + mContractId + ", SiteId=" + mSiteId + ", AccountId=" + mAccountId + ", Amount=" + mAmount + ", Weight=" + mWeight + ", Items=" + mItems + ", Detail=" + mDetail + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("OrderHandling");
	root.setAttribute("Id", String.valueOf(mTotalHandling));

	Element node;

        node = doc.createElement("TotalFreight");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalFreight)));
        root.appendChild(node);

        node = doc.createElement("ContractId");
        node.appendChild(doc.createTextNode(String.valueOf(mContractId)));
        root.appendChild(node);

        node = doc.createElement("SiteId");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteId)));
        root.appendChild(node);

        node = doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node = doc.createElement("Amount");
        node.appendChild(doc.createTextNode(String.valueOf(mAmount)));
        root.appendChild(node);

        node = doc.createElement("Weight");
        node.appendChild(doc.createTextNode(String.valueOf(mWeight)));
        root.appendChild(node);

        node = doc.createElement("Items");
        node.appendChild(doc.createTextNode(String.valueOf(mItems)));
        root.appendChild(node);

        node = doc.createElement("Detail");
        node.appendChild(doc.createTextNode(String.valueOf(mDetail)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public OrderHandlingView copy()  {
      OrderHandlingView obj = new OrderHandlingView();
      obj.setTotalHandling(mTotalHandling);
      obj.setTotalFreight(mTotalFreight);
      obj.setContractId(mContractId);
      obj.setSiteId(mSiteId);
      obj.setAccountId(mAccountId);
      obj.setAmount(mAmount);
      obj.setWeight(mWeight);
      obj.setItems(mItems);
      obj.setDetail(mDetail);
      
      return obj;
    }

    
    /**
     * Sets the TotalHandling property.
     *
     * @param pTotalHandling
     *  BigDecimal to use to update the property.
     */
    public void setTotalHandling(BigDecimal pTotalHandling){
        this.mTotalHandling = pTotalHandling;
    }
    /**
     * Retrieves the TotalHandling property.
     *
     * @return
     *  BigDecimal containing the TotalHandling property.
     */
    public BigDecimal getTotalHandling(){
        return mTotalHandling;
    }


    /**
     * Sets the TotalFreight property.
     *
     * @param pTotalFreight
     *  BigDecimal to use to update the property.
     */
    public void setTotalFreight(BigDecimal pTotalFreight){
        this.mTotalFreight = pTotalFreight;
    }
    /**
     * Retrieves the TotalFreight property.
     *
     * @return
     *  BigDecimal containing the TotalFreight property.
     */
    public BigDecimal getTotalFreight(){
        return mTotalFreight;
    }


    /**
     * Sets the ContractId property.
     *
     * @param pContractId
     *  int to use to update the property.
     */
    public void setContractId(int pContractId){
        this.mContractId = pContractId;
    }
    /**
     * Retrieves the ContractId property.
     *
     * @return
     *  int containing the ContractId property.
     */
    public int getContractId(){
        return mContractId;
    }


    /**
     * Sets the SiteId property.
     *
     * @param pSiteId
     *  int to use to update the property.
     */
    public void setSiteId(int pSiteId){
        this.mSiteId = pSiteId;
    }
    /**
     * Retrieves the SiteId property.
     *
     * @return
     *  int containing the SiteId property.
     */
    public int getSiteId(){
        return mSiteId;
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
     * Sets the Amount property.
     *
     * @param pAmount
     *  BigDecimal to use to update the property.
     */
    public void setAmount(BigDecimal pAmount){
        this.mAmount = pAmount;
    }
    /**
     * Retrieves the Amount property.
     *
     * @return
     *  BigDecimal containing the Amount property.
     */
    public BigDecimal getAmount(){
        return mAmount;
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
     * Sets the Items property.
     *
     * @param pItems
     *  OrderHandlingItemViewVector to use to update the property.
     */
    public void setItems(OrderHandlingItemViewVector pItems){
        this.mItems = pItems;
    }
    /**
     * Retrieves the Items property.
     *
     * @return
     *  OrderHandlingItemViewVector containing the Items property.
     */
    public OrderHandlingItemViewVector getItems(){
        return mItems;
    }


    /**
     * Sets the Detail property.
     *
     * @param pDetail
     *  OrderHandlingDetailViewVector to use to update the property.
     */
    public void setDetail(OrderHandlingDetailViewVector pDetail){
        this.mDetail = pDetail;
    }
    /**
     * Retrieves the Detail property.
     *
     * @return
     *  OrderHandlingDetailViewVector containing the Detail property.
     */
    public OrderHandlingDetailViewVector getDetail(){
        return mDetail;
    }


    
}
