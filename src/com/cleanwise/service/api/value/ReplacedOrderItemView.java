
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ReplacedOrderItemView
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
 * <code>ReplacedOrderItemView</code> is a ViewObject class for UI.
 */
public class ReplacedOrderItemView
extends ValueObject
{
   
    private static final long serialVersionUID = 5207161905831531031L;
    private int mOrderItemId;
    private int mOrderId;
    private int mItemId;
    private int mItemSkuNum;
    private String mCustItemSkuNum;
    private String mItemShortDesc;
    private String mCustItemShortDesc;
    private String mOrderItemStatusCd;
    private String mManuItemSkuNum;
    private String mDistItemSkuNum;
    private int mQuantity;
    private String mQuantityS;

    /**
     * Constructor.
     */
    public ReplacedOrderItemView ()
    {
        mCustItemSkuNum = "";
        mItemShortDesc = "";
        mCustItemShortDesc = "";
        mOrderItemStatusCd = "";
        mManuItemSkuNum = "";
        mDistItemSkuNum = "";
        mQuantityS = "";
    }

    /**
     * Constructor. 
     */
    public ReplacedOrderItemView(int parm1, int parm2, int parm3, int parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, int parm11, String parm12)
    {
        mOrderItemId = parm1;
        mOrderId = parm2;
        mItemId = parm3;
        mItemSkuNum = parm4;
        mCustItemSkuNum = parm5;
        mItemShortDesc = parm6;
        mCustItemShortDesc = parm7;
        mOrderItemStatusCd = parm8;
        mManuItemSkuNum = parm9;
        mDistItemSkuNum = parm10;
        mQuantity = parm11;
        mQuantityS = parm12;
        
    }

    /**
     * Creates a new ReplacedOrderItemView
     *
     * @return
     *  Newly initialized ReplacedOrderItemView object.
     */
    public static ReplacedOrderItemView createValue () 
    {
        ReplacedOrderItemView valueView = new ReplacedOrderItemView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ReplacedOrderItemView object
     */
    public String toString()
    {
        return "[" + "OrderItemId=" + mOrderItemId + ", OrderId=" + mOrderId + ", ItemId=" + mItemId + ", ItemSkuNum=" + mItemSkuNum + ", CustItemSkuNum=" + mCustItemSkuNum + ", ItemShortDesc=" + mItemShortDesc + ", CustItemShortDesc=" + mCustItemShortDesc + ", OrderItemStatusCd=" + mOrderItemStatusCd + ", ManuItemSkuNum=" + mManuItemSkuNum + ", DistItemSkuNum=" + mDistItemSkuNum + ", Quantity=" + mQuantity + ", QuantityS=" + mQuantityS + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ReplacedOrderItem");
	root.setAttribute("Id", String.valueOf(mOrderItemId));

	Element node;

        node = doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node = doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node = doc.createElement("ItemSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mItemSkuNum)));
        root.appendChild(node);

        node = doc.createElement("CustItemSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mCustItemSkuNum)));
        root.appendChild(node);

        node = doc.createElement("ItemShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mItemShortDesc)));
        root.appendChild(node);

        node = doc.createElement("CustItemShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mCustItemShortDesc)));
        root.appendChild(node);

        node = doc.createElement("OrderItemStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderItemStatusCd)));
        root.appendChild(node);

        node = doc.createElement("ManuItemSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mManuItemSkuNum)));
        root.appendChild(node);

        node = doc.createElement("DistItemSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemSkuNum)));
        root.appendChild(node);

        node = doc.createElement("Quantity");
        node.appendChild(doc.createTextNode(String.valueOf(mQuantity)));
        root.appendChild(node);

        node = doc.createElement("QuantityS");
        node.appendChild(doc.createTextNode(String.valueOf(mQuantityS)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ReplacedOrderItemView copy()  {
      ReplacedOrderItemView obj = new ReplacedOrderItemView();
      obj.setOrderItemId(mOrderItemId);
      obj.setOrderId(mOrderId);
      obj.setItemId(mItemId);
      obj.setItemSkuNum(mItemSkuNum);
      obj.setCustItemSkuNum(mCustItemSkuNum);
      obj.setItemShortDesc(mItemShortDesc);
      obj.setCustItemShortDesc(mCustItemShortDesc);
      obj.setOrderItemStatusCd(mOrderItemStatusCd);
      obj.setManuItemSkuNum(mManuItemSkuNum);
      obj.setDistItemSkuNum(mDistItemSkuNum);
      obj.setQuantity(mQuantity);
      obj.setQuantityS(mQuantityS);
      
      return obj;
    }

    
    /**
     * Sets the OrderItemId property.
     *
     * @param pOrderItemId
     *  int to use to update the property.
     */
    public void setOrderItemId(int pOrderItemId){
        this.mOrderItemId = pOrderItemId;
    }
    /**
     * Retrieves the OrderItemId property.
     *
     * @return
     *  int containing the OrderItemId property.
     */
    public int getOrderItemId(){
        return mOrderItemId;
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
     * Sets the ItemSkuNum property.
     *
     * @param pItemSkuNum
     *  int to use to update the property.
     */
    public void setItemSkuNum(int pItemSkuNum){
        this.mItemSkuNum = pItemSkuNum;
    }
    /**
     * Retrieves the ItemSkuNum property.
     *
     * @return
     *  int containing the ItemSkuNum property.
     */
    public int getItemSkuNum(){
        return mItemSkuNum;
    }


    /**
     * Sets the CustItemSkuNum property.
     *
     * @param pCustItemSkuNum
     *  String to use to update the property.
     */
    public void setCustItemSkuNum(String pCustItemSkuNum){
        this.mCustItemSkuNum = pCustItemSkuNum;
    }
    /**
     * Retrieves the CustItemSkuNum property.
     *
     * @return
     *  String containing the CustItemSkuNum property.
     */
    public String getCustItemSkuNum(){
        return mCustItemSkuNum;
    }


    /**
     * Sets the ItemShortDesc property.
     *
     * @param pItemShortDesc
     *  String to use to update the property.
     */
    public void setItemShortDesc(String pItemShortDesc){
        this.mItemShortDesc = pItemShortDesc;
    }
    /**
     * Retrieves the ItemShortDesc property.
     *
     * @return
     *  String containing the ItemShortDesc property.
     */
    public String getItemShortDesc(){
        return mItemShortDesc;
    }


    /**
     * Sets the CustItemShortDesc property.
     *
     * @param pCustItemShortDesc
     *  String to use to update the property.
     */
    public void setCustItemShortDesc(String pCustItemShortDesc){
        this.mCustItemShortDesc = pCustItemShortDesc;
    }
    /**
     * Retrieves the CustItemShortDesc property.
     *
     * @return
     *  String containing the CustItemShortDesc property.
     */
    public String getCustItemShortDesc(){
        return mCustItemShortDesc;
    }


    /**
     * Sets the OrderItemStatusCd property.
     *
     * @param pOrderItemStatusCd
     *  String to use to update the property.
     */
    public void setOrderItemStatusCd(String pOrderItemStatusCd){
        this.mOrderItemStatusCd = pOrderItemStatusCd;
    }
    /**
     * Retrieves the OrderItemStatusCd property.
     *
     * @return
     *  String containing the OrderItemStatusCd property.
     */
    public String getOrderItemStatusCd(){
        return mOrderItemStatusCd;
    }


    /**
     * Sets the ManuItemSkuNum property.
     *
     * @param pManuItemSkuNum
     *  String to use to update the property.
     */
    public void setManuItemSkuNum(String pManuItemSkuNum){
        this.mManuItemSkuNum = pManuItemSkuNum;
    }
    /**
     * Retrieves the ManuItemSkuNum property.
     *
     * @return
     *  String containing the ManuItemSkuNum property.
     */
    public String getManuItemSkuNum(){
        return mManuItemSkuNum;
    }


    /**
     * Sets the DistItemSkuNum property.
     *
     * @param pDistItemSkuNum
     *  String to use to update the property.
     */
    public void setDistItemSkuNum(String pDistItemSkuNum){
        this.mDistItemSkuNum = pDistItemSkuNum;
    }
    /**
     * Retrieves the DistItemSkuNum property.
     *
     * @return
     *  String containing the DistItemSkuNum property.
     */
    public String getDistItemSkuNum(){
        return mDistItemSkuNum;
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
     * Sets the QuantityS property.
     *
     * @param pQuantityS
     *  String to use to update the property.
     */
    public void setQuantityS(String pQuantityS){
        this.mQuantityS = pQuantityS;
    }
    /**
     * Retrieves the QuantityS property.
     *
     * @return
     *  String containing the QuantityS property.
     */
    public String getQuantityS(){
        return mQuantityS;
    }


    
}
