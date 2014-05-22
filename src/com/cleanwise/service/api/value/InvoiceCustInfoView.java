
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InvoiceCustInfoView
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
 * <code>InvoiceCustInfoView</code> is a ViewObject class for UI.
 */
public class InvoiceCustInfoView
extends ValueObject
{
   
    private static final long serialVersionUID = -8673749062755024042L;
    private String mInvoiceDetailStatusCd;
    private String mShipStatusCd;
    private int mLineNumber;
    private int mItemSkuNum;
    private String mItemShortDesc;
    private String mItemUom;
    private int mItemQuantity;
    private BigDecimal mCustContractPrice;
    private BigDecimal mLineTotal;
    private String mDistItemSkuNum;

    /**
     * Constructor.
     */
    public InvoiceCustInfoView ()
    {
        mInvoiceDetailStatusCd = "";
        mShipStatusCd = "";
        mItemShortDesc = "";
        mItemUom = "";
        mDistItemSkuNum = "";
    }

    /**
     * Constructor. 
     */
    public InvoiceCustInfoView(String parm1, String parm2, int parm3, int parm4, String parm5, String parm6, int parm7, BigDecimal parm8, BigDecimal parm9, String parm10)
    {
        mInvoiceDetailStatusCd = parm1;
        mShipStatusCd = parm2;
        mLineNumber = parm3;
        mItemSkuNum = parm4;
        mItemShortDesc = parm5;
        mItemUom = parm6;
        mItemQuantity = parm7;
        mCustContractPrice = parm8;
        mLineTotal = parm9;
        mDistItemSkuNum = parm10;
        
    }

    /**
     * Creates a new InvoiceCustInfoView
     *
     * @return
     *  Newly initialized InvoiceCustInfoView object.
     */
    public static InvoiceCustInfoView createValue () 
    {
        InvoiceCustInfoView valueView = new InvoiceCustInfoView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InvoiceCustInfoView object
     */
    public String toString()
    {
        return "[" + "InvoiceDetailStatusCd=" + mInvoiceDetailStatusCd + ", ShipStatusCd=" + mShipStatusCd + ", LineNumber=" + mLineNumber + ", ItemSkuNum=" + mItemSkuNum + ", ItemShortDesc=" + mItemShortDesc + ", ItemUom=" + mItemUom + ", ItemQuantity=" + mItemQuantity + ", CustContractPrice=" + mCustContractPrice + ", LineTotal=" + mLineTotal + ", DistItemSkuNum=" + mDistItemSkuNum + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("InvoiceCustInfo");
	root.setAttribute("Id", String.valueOf(mInvoiceDetailStatusCd));

	Element node;

        node = doc.createElement("ShipStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mShipStatusCd)));
        root.appendChild(node);

        node = doc.createElement("LineNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mLineNumber)));
        root.appendChild(node);

        node = doc.createElement("ItemSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mItemSkuNum)));
        root.appendChild(node);

        node = doc.createElement("ItemShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mItemShortDesc)));
        root.appendChild(node);

        node = doc.createElement("ItemUom");
        node.appendChild(doc.createTextNode(String.valueOf(mItemUom)));
        root.appendChild(node);

        node = doc.createElement("ItemQuantity");
        node.appendChild(doc.createTextNode(String.valueOf(mItemQuantity)));
        root.appendChild(node);

        node = doc.createElement("CustContractPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mCustContractPrice)));
        root.appendChild(node);

        node = doc.createElement("LineTotal");
        node.appendChild(doc.createTextNode(String.valueOf(mLineTotal)));
        root.appendChild(node);

        node = doc.createElement("DistItemSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemSkuNum)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public InvoiceCustInfoView copy()  {
      InvoiceCustInfoView obj = new InvoiceCustInfoView();
      obj.setInvoiceDetailStatusCd(mInvoiceDetailStatusCd);
      obj.setShipStatusCd(mShipStatusCd);
      obj.setLineNumber(mLineNumber);
      obj.setItemSkuNum(mItemSkuNum);
      obj.setItemShortDesc(mItemShortDesc);
      obj.setItemUom(mItemUom);
      obj.setItemQuantity(mItemQuantity);
      obj.setCustContractPrice(mCustContractPrice);
      obj.setLineTotal(mLineTotal);
      obj.setDistItemSkuNum(mDistItemSkuNum);
      
      return obj;
    }

    
    /**
     * Sets the InvoiceDetailStatusCd property.
     *
     * @param pInvoiceDetailStatusCd
     *  String to use to update the property.
     */
    public void setInvoiceDetailStatusCd(String pInvoiceDetailStatusCd){
        this.mInvoiceDetailStatusCd = pInvoiceDetailStatusCd;
    }
    /**
     * Retrieves the InvoiceDetailStatusCd property.
     *
     * @return
     *  String containing the InvoiceDetailStatusCd property.
     */
    public String getInvoiceDetailStatusCd(){
        return mInvoiceDetailStatusCd;
    }


    /**
     * Sets the ShipStatusCd property.
     *
     * @param pShipStatusCd
     *  String to use to update the property.
     */
    public void setShipStatusCd(String pShipStatusCd){
        this.mShipStatusCd = pShipStatusCd;
    }
    /**
     * Retrieves the ShipStatusCd property.
     *
     * @return
     *  String containing the ShipStatusCd property.
     */
    public String getShipStatusCd(){
        return mShipStatusCd;
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
     * Sets the ItemUom property.
     *
     * @param pItemUom
     *  String to use to update the property.
     */
    public void setItemUom(String pItemUom){
        this.mItemUom = pItemUom;
    }
    /**
     * Retrieves the ItemUom property.
     *
     * @return
     *  String containing the ItemUom property.
     */
    public String getItemUom(){
        return mItemUom;
    }


    /**
     * Sets the ItemQuantity property.
     *
     * @param pItemQuantity
     *  int to use to update the property.
     */
    public void setItemQuantity(int pItemQuantity){
        this.mItemQuantity = pItemQuantity;
    }
    /**
     * Retrieves the ItemQuantity property.
     *
     * @return
     *  int containing the ItemQuantity property.
     */
    public int getItemQuantity(){
        return mItemQuantity;
    }


    /**
     * Sets the CustContractPrice property.
     *
     * @param pCustContractPrice
     *  BigDecimal to use to update the property.
     */
    public void setCustContractPrice(BigDecimal pCustContractPrice){
        this.mCustContractPrice = pCustContractPrice;
    }
    /**
     * Retrieves the CustContractPrice property.
     *
     * @return
     *  BigDecimal containing the CustContractPrice property.
     */
    public BigDecimal getCustContractPrice(){
        return mCustContractPrice;
    }


    /**
     * Sets the LineTotal property.
     *
     * @param pLineTotal
     *  BigDecimal to use to update the property.
     */
    public void setLineTotal(BigDecimal pLineTotal){
        this.mLineTotal = pLineTotal;
    }
    /**
     * Retrieves the LineTotal property.
     *
     * @return
     *  BigDecimal containing the LineTotal property.
     */
    public BigDecimal getLineTotal(){
        return mLineTotal;
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


    
}
