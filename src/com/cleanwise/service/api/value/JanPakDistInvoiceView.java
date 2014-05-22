
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        JanPakDistInvoiceView
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
 * <code>JanPakDistInvoiceView</code> is a ViewObject class for UI.
 */
public class JanPakDistInvoiceView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private String mInvoiceNum;
    private java.util.Date mInvoiceDate;
    private String mBranch;
    private String mDistSku;
    private String mDistUom;
    private int mQuantity;
    private BigDecimal mPrice;
    private BigDecimal mCost;
    private String mRepNum;
    private String mRepName;
    private String mBillNum;
    private String mShipNum;
    private String mCustPoNum;
    private BigDecimal mTax;
    private BigDecimal mFreight;

    /**
     * Constructor.
     */
    public JanPakDistInvoiceView ()
    {
        mInvoiceNum = "";
        mBranch = "";
        mDistSku = "";
        mDistUom = "";
        mRepNum = "";
        mRepName = "";
        mBillNum = "";
        mShipNum = "";
        mCustPoNum = "";
    }

    /**
     * Constructor. 
     */
    public JanPakDistInvoiceView(String parm1, java.util.Date parm2, String parm3, String parm4, String parm5, int parm6, BigDecimal parm7, BigDecimal parm8, String parm9, String parm10, String parm11, String parm12, String parm13, BigDecimal parm14, BigDecimal parm15)
    {
        mInvoiceNum = parm1;
        mInvoiceDate = parm2;
        mBranch = parm3;
        mDistSku = parm4;
        mDistUom = parm5;
        mQuantity = parm6;
        mPrice = parm7;
        mCost = parm8;
        mRepNum = parm9;
        mRepName = parm10;
        mBillNum = parm11;
        mShipNum = parm12;
        mCustPoNum = parm13;
        mTax = parm14;
        mFreight = parm15;
        
    }

    /**
     * Creates a new JanPakDistInvoiceView
     *
     * @return
     *  Newly initialized JanPakDistInvoiceView object.
     */
    public static JanPakDistInvoiceView createValue () 
    {
        JanPakDistInvoiceView valueView = new JanPakDistInvoiceView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this JanPakDistInvoiceView object
     */
    public String toString()
    {
        return "[" + "InvoiceNum=" + mInvoiceNum + ", InvoiceDate=" + mInvoiceDate + ", Branch=" + mBranch + ", DistSku=" + mDistSku + ", DistUom=" + mDistUom + ", Quantity=" + mQuantity + ", Price=" + mPrice + ", Cost=" + mCost + ", RepNum=" + mRepNum + ", RepName=" + mRepName + ", BillNum=" + mBillNum + ", ShipNum=" + mShipNum + ", CustPoNum=" + mCustPoNum + ", Tax=" + mTax + ", Freight=" + mFreight + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("JanPakDistInvoice");
	root.setAttribute("Id", String.valueOf(mInvoiceNum));

	Element node;

        node = doc.createElement("InvoiceDate");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceDate)));
        root.appendChild(node);

        node = doc.createElement("Branch");
        node.appendChild(doc.createTextNode(String.valueOf(mBranch)));
        root.appendChild(node);

        node = doc.createElement("DistSku");
        node.appendChild(doc.createTextNode(String.valueOf(mDistSku)));
        root.appendChild(node);

        node = doc.createElement("DistUom");
        node.appendChild(doc.createTextNode(String.valueOf(mDistUom)));
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

        node = doc.createElement("RepNum");
        node.appendChild(doc.createTextNode(String.valueOf(mRepNum)));
        root.appendChild(node);

        node = doc.createElement("RepName");
        node.appendChild(doc.createTextNode(String.valueOf(mRepName)));
        root.appendChild(node);

        node = doc.createElement("BillNum");
        node.appendChild(doc.createTextNode(String.valueOf(mBillNum)));
        root.appendChild(node);

        node = doc.createElement("ShipNum");
        node.appendChild(doc.createTextNode(String.valueOf(mShipNum)));
        root.appendChild(node);

        node = doc.createElement("CustPoNum");
        node.appendChild(doc.createTextNode(String.valueOf(mCustPoNum)));
        root.appendChild(node);

        node = doc.createElement("Tax");
        node.appendChild(doc.createTextNode(String.valueOf(mTax)));
        root.appendChild(node);

        node = doc.createElement("Freight");
        node.appendChild(doc.createTextNode(String.valueOf(mFreight)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public JanPakDistInvoiceView copy()  {
      JanPakDistInvoiceView obj = new JanPakDistInvoiceView();
      obj.setInvoiceNum(mInvoiceNum);
      obj.setInvoiceDate(mInvoiceDate);
      obj.setBranch(mBranch);
      obj.setDistSku(mDistSku);
      obj.setDistUom(mDistUom);
      obj.setQuantity(mQuantity);
      obj.setPrice(mPrice);
      obj.setCost(mCost);
      obj.setRepNum(mRepNum);
      obj.setRepName(mRepName);
      obj.setBillNum(mBillNum);
      obj.setShipNum(mShipNum);
      obj.setCustPoNum(mCustPoNum);
      obj.setTax(mTax);
      obj.setFreight(mFreight);
      
      return obj;
    }

    
    /**
     * Sets the InvoiceNum property.
     *
     * @param pInvoiceNum
     *  String to use to update the property.
     */
    public void setInvoiceNum(String pInvoiceNum){
        this.mInvoiceNum = pInvoiceNum;
    }
    /**
     * Retrieves the InvoiceNum property.
     *
     * @return
     *  String containing the InvoiceNum property.
     */
    public String getInvoiceNum(){
        return mInvoiceNum;
    }


    /**
     * Sets the InvoiceDate property.
     *
     * @param pInvoiceDate
     *  java.util.Date to use to update the property.
     */
    public void setInvoiceDate(java.util.Date pInvoiceDate){
        this.mInvoiceDate = pInvoiceDate;
    }
    /**
     * Retrieves the InvoiceDate property.
     *
     * @return
     *  java.util.Date containing the InvoiceDate property.
     */
    public java.util.Date getInvoiceDate(){
        return mInvoiceDate;
    }


    /**
     * Sets the Branch property.
     *
     * @param pBranch
     *  String to use to update the property.
     */
    public void setBranch(String pBranch){
        this.mBranch = pBranch;
    }
    /**
     * Retrieves the Branch property.
     *
     * @return
     *  String containing the Branch property.
     */
    public String getBranch(){
        return mBranch;
    }


    /**
     * Sets the DistSku property.
     *
     * @param pDistSku
     *  String to use to update the property.
     */
    public void setDistSku(String pDistSku){
        this.mDistSku = pDistSku;
    }
    /**
     * Retrieves the DistSku property.
     *
     * @return
     *  String containing the DistSku property.
     */
    public String getDistSku(){
        return mDistSku;
    }


    /**
     * Sets the DistUom property.
     *
     * @param pDistUom
     *  String to use to update the property.
     */
    public void setDistUom(String pDistUom){
        this.mDistUom = pDistUom;
    }
    /**
     * Retrieves the DistUom property.
     *
     * @return
     *  String containing the DistUom property.
     */
    public String getDistUom(){
        return mDistUom;
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
     * Sets the RepNum property.
     *
     * @param pRepNum
     *  String to use to update the property.
     */
    public void setRepNum(String pRepNum){
        this.mRepNum = pRepNum;
    }
    /**
     * Retrieves the RepNum property.
     *
     * @return
     *  String containing the RepNum property.
     */
    public String getRepNum(){
        return mRepNum;
    }


    /**
     * Sets the RepName property.
     *
     * @param pRepName
     *  String to use to update the property.
     */
    public void setRepName(String pRepName){
        this.mRepName = pRepName;
    }
    /**
     * Retrieves the RepName property.
     *
     * @return
     *  String containing the RepName property.
     */
    public String getRepName(){
        return mRepName;
    }


    /**
     * Sets the BillNum property.
     *
     * @param pBillNum
     *  String to use to update the property.
     */
    public void setBillNum(String pBillNum){
        this.mBillNum = pBillNum;
    }
    /**
     * Retrieves the BillNum property.
     *
     * @return
     *  String containing the BillNum property.
     */
    public String getBillNum(){
        return mBillNum;
    }


    /**
     * Sets the ShipNum property.
     *
     * @param pShipNum
     *  String to use to update the property.
     */
    public void setShipNum(String pShipNum){
        this.mShipNum = pShipNum;
    }
    /**
     * Retrieves the ShipNum property.
     *
     * @return
     *  String containing the ShipNum property.
     */
    public String getShipNum(){
        return mShipNum;
    }


    /**
     * Sets the CustPoNum property.
     *
     * @param pCustPoNum
     *  String to use to update the property.
     */
    public void setCustPoNum(String pCustPoNum){
        this.mCustPoNum = pCustPoNum;
    }
    /**
     * Retrieves the CustPoNum property.
     *
     * @return
     *  String containing the CustPoNum property.
     */
    public String getCustPoNum(){
        return mCustPoNum;
    }


    /**
     * Sets the Tax property.
     *
     * @param pTax
     *  BigDecimal to use to update the property.
     */
    public void setTax(BigDecimal pTax){
        this.mTax = pTax;
    }
    /**
     * Retrieves the Tax property.
     *
     * @return
     *  BigDecimal containing the Tax property.
     */
    public BigDecimal getTax(){
        return mTax;
    }


    /**
     * Sets the Freight property.
     *
     * @param pFreight
     *  BigDecimal to use to update the property.
     */
    public void setFreight(BigDecimal pFreight){
        this.mFreight = pFreight;
    }
    /**
     * Retrieves the Freight property.
     *
     * @return
     *  BigDecimal containing the Freight property.
     */
    public BigDecimal getFreight(){
        return mFreight;
    }


    
}
