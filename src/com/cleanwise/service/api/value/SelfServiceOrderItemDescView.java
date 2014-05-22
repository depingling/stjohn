
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        SelfServiceOrderItemDescView
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
 * <code>SelfServiceOrderItemDescView</code> is a ViewObject class for UI.
 */
public class SelfServiceOrderItemDescView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private OrderItemData mOrderItem;
    private int mSkuNum;
    private String mShortDesc;
    private int mLineNumber;
    private String mUom;
    private String mPack;
    private int mQuantity;
    private BigDecimal mPrice;
    private BigDecimal mCost;
    private BigDecimal mTotal;

    /**
     * Constructor.
     */
    public SelfServiceOrderItemDescView ()
    {
        mShortDesc = "";
        mUom = "";
        mPack = "";
    }

    /**
     * Constructor. 
     */
    public SelfServiceOrderItemDescView(OrderItemData parm1, int parm2, String parm3, int parm4, String parm5, String parm6, int parm7, BigDecimal parm8, BigDecimal parm9, BigDecimal parm10)
    {
        mOrderItem = parm1;
        mSkuNum = parm2;
        mShortDesc = parm3;
        mLineNumber = parm4;
        mUom = parm5;
        mPack = parm6;
        mQuantity = parm7;
        mPrice = parm8;
        mCost = parm9;
        mTotal = parm10;
        
    }

    /**
     * Creates a new SelfServiceOrderItemDescView
     *
     * @return
     *  Newly initialized SelfServiceOrderItemDescView object.
     */
    public static SelfServiceOrderItemDescView createValue () 
    {
        SelfServiceOrderItemDescView valueView = new SelfServiceOrderItemDescView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this SelfServiceOrderItemDescView object
     */
    public String toString()
    {
        return "[" + "OrderItem=" + mOrderItem + ", SkuNum=" + mSkuNum + ", ShortDesc=" + mShortDesc + ", LineNumber=" + mLineNumber + ", Uom=" + mUom + ", Pack=" + mPack + ", Quantity=" + mQuantity + ", Price=" + mPrice + ", Cost=" + mCost + ", Total=" + mTotal + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("SelfServiceOrderItemDesc");
	root.setAttribute("Id", String.valueOf(mOrderItem));

	Element node;

        node = doc.createElement("SkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSkuNum)));
        root.appendChild(node);

        node = doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node = doc.createElement("LineNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mLineNumber)));
        root.appendChild(node);

        node = doc.createElement("Uom");
        node.appendChild(doc.createTextNode(String.valueOf(mUom)));
        root.appendChild(node);

        node = doc.createElement("Pack");
        node.appendChild(doc.createTextNode(String.valueOf(mPack)));
        root.appendChild(node);

        node = doc.createElement("Quantity");
        node.appendChild(doc.createTextNode(String.valueOf(mQuantity)));
        root.appendChild(node);

        node = doc.createElement("Price");
        node.appendChild(doc.createTextNode(String.valueOf(mPrice)));
        root.appendChild(node);

        node = doc.createElement("Cost");
        node.appendChild(doc.createTextNode(String.valueOf(mCost)));
        root.appendChild(node);

        node = doc.createElement("Total");
        node.appendChild(doc.createTextNode(String.valueOf(mTotal)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public SelfServiceOrderItemDescView copy()  {
      SelfServiceOrderItemDescView obj = new SelfServiceOrderItemDescView();
      obj.setOrderItem(mOrderItem);
      obj.setSkuNum(mSkuNum);
      obj.setShortDesc(mShortDesc);
      obj.setLineNumber(mLineNumber);
      obj.setUom(mUom);
      obj.setPack(mPack);
      obj.setQuantity(mQuantity);
      obj.setPrice(mPrice);
      obj.setCost(mCost);
      obj.setTotal(mTotal);
      
      return obj;
    }

    
    /**
     * Sets the OrderItem property.
     *
     * @param pOrderItem
     *  OrderItemData to use to update the property.
     */
    public void setOrderItem(OrderItemData pOrderItem){
        this.mOrderItem = pOrderItem;
    }
    /**
     * Retrieves the OrderItem property.
     *
     * @return
     *  OrderItemData containing the OrderItem property.
     */
    public OrderItemData getOrderItem(){
        return mOrderItem;
    }


    /**
     * Sets the SkuNum property.
     *
     * @param pSkuNum
     *  int to use to update the property.
     */
    public void setSkuNum(int pSkuNum){
        this.mSkuNum = pSkuNum;
    }
    /**
     * Retrieves the SkuNum property.
     *
     * @return
     *  int containing the SkuNum property.
     */
    public int getSkuNum(){
        return mSkuNum;
    }


    /**
     * Sets the ShortDesc property.
     *
     * @param pShortDesc
     *  String to use to update the property.
     */
    public void setShortDesc(String pShortDesc){
        this.mShortDesc = pShortDesc;
    }
    /**
     * Retrieves the ShortDesc property.
     *
     * @return
     *  String containing the ShortDesc property.
     */
    public String getShortDesc(){
        return mShortDesc;
    }


    /**
     * Sets the LineNumber property.
     *
     * @param pLineNumber
     *  int to use to update the property.
     */
    public void setLineNumber(int pLineNumber){
        this.mLineNumber = pLineNumber;
    }
    /**
     * Retrieves the LineNumber property.
     *
     * @return
     *  int containing the LineNumber property.
     */
    public int getLineNumber(){
        return mLineNumber;
    }


    /**
     * Sets the Uom property.
     *
     * @param pUom
     *  String to use to update the property.
     */
    public void setUom(String pUom){
        this.mUom = pUom;
    }
    /**
     * Retrieves the Uom property.
     *
     * @return
     *  String containing the Uom property.
     */
    public String getUom(){
        return mUom;
    }


    /**
     * Sets the Pack property.
     *
     * @param pPack
     *  String to use to update the property.
     */
    public void setPack(String pPack){
        this.mPack = pPack;
    }
    /**
     * Retrieves the Pack property.
     *
     * @return
     *  String containing the Pack property.
     */
    public String getPack(){
        return mPack;
    }


    /**
     * Sets the Quantity property.
     *
     * @param pQuantity
     *  int to use to update the property.
     */
    public void setQuantity(int pQuantity){
        this.mQuantity = pQuantity;
    }
    /**
     * Retrieves the Quantity property.
     *
     * @return
     *  int containing the Quantity property.
     */
    public int getQuantity(){
        return mQuantity;
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
     * Sets the Cost property.
     *
     * @param pCost
     *  BigDecimal to use to update the property.
     */
    public void setCost(BigDecimal pCost){
        this.mCost = pCost;
    }
    /**
     * Retrieves the Cost property.
     *
     * @return
     *  BigDecimal containing the Cost property.
     */
    public BigDecimal getCost(){
        return mCost;
    }


    /**
     * Sets the Total property.
     *
     * @param pTotal
     *  BigDecimal to use to update the property.
     */
    public void setTotal(BigDecimal pTotal){
        this.mTotal = pTotal;
    }
    /**
     * Retrieves the Total property.
     *
     * @return
     *  BigDecimal containing the Total property.
     */
    public BigDecimal getTotal(){
        return mTotal;
    }


    
}
