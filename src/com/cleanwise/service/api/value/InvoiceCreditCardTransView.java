
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InvoiceCreditCardTransView
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
 * <code>InvoiceCreditCardTransView</code> is a ViewObject class for UI.
 */
public class InvoiceCreditCardTransView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private String mInvoiceNum;
    private Date mAddDate;
    private String mTransactionTypeCd;
    private java.math.BigDecimal mAmount;
    private String mTransactionReference;
    private String mAuthCode;
    private int mInvoiceCustId;

    /**
     * Constructor.
     */
    public InvoiceCreditCardTransView ()
    {
        mInvoiceNum = "";
        mTransactionTypeCd = "";
        mTransactionReference = "";
        mAuthCode = "";
    }

    /**
     * Constructor. 
     */
    public InvoiceCreditCardTransView(String parm1, Date parm2, String parm3, java.math.BigDecimal parm4, String parm5, String parm6, int parm7)
    {
        mInvoiceNum = parm1;
        mAddDate = parm2;
        mTransactionTypeCd = parm3;
        mAmount = parm4;
        mTransactionReference = parm5;
        mAuthCode = parm6;
        mInvoiceCustId = parm7;
        
    }

    /**
     * Creates a new InvoiceCreditCardTransView
     *
     * @return
     *  Newly initialized InvoiceCreditCardTransView object.
     */
    public static InvoiceCreditCardTransView createValue () 
    {
        InvoiceCreditCardTransView valueView = new InvoiceCreditCardTransView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InvoiceCreditCardTransView object
     */
    public String toString()
    {
        return "[" + "InvoiceNum=" + mInvoiceNum + ", AddDate=" + mAddDate + ", TransactionTypeCd=" + mTransactionTypeCd + ", Amount=" + mAmount + ", TransactionReference=" + mTransactionReference + ", AuthCode=" + mAuthCode + ", InvoiceCustId=" + mInvoiceCustId + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("InvoiceCreditCardTrans");
	root.setAttribute("Id", String.valueOf(mInvoiceNum));

	Element node;

        node = doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node = doc.createElement("TransactionTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mTransactionTypeCd)));
        root.appendChild(node);

        node = doc.createElement("Amount");
        node.appendChild(doc.createTextNode(String.valueOf(mAmount)));
        root.appendChild(node);

        node = doc.createElement("TransactionReference");
        node.appendChild(doc.createTextNode(String.valueOf(mTransactionReference)));
        root.appendChild(node);

        node = doc.createElement("AuthCode");
        node.appendChild(doc.createTextNode(String.valueOf(mAuthCode)));
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
    public InvoiceCreditCardTransView copy()  {
      InvoiceCreditCardTransView obj = new InvoiceCreditCardTransView();
      obj.setInvoiceNum(mInvoiceNum);
      obj.setAddDate(mAddDate);
      obj.setTransactionTypeCd(mTransactionTypeCd);
      obj.setAmount(mAmount);
      obj.setTransactionReference(mTransactionReference);
      obj.setAuthCode(mAuthCode);
      obj.setInvoiceCustId(mInvoiceCustId);
      
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
     * Sets the AddDate property.
     *
     * @param pAddDate
     *  Date to use to update the property.
     */
    public void setAddDate(Date pAddDate){
        this.mAddDate = pAddDate;
    }
    /**
     * Retrieves the AddDate property.
     *
     * @return
     *  Date containing the AddDate property.
     */
    public Date getAddDate(){
        return mAddDate;
    }


    /**
     * Sets the TransactionTypeCd property.
     *
     * @param pTransactionTypeCd
     *  String to use to update the property.
     */
    public void setTransactionTypeCd(String pTransactionTypeCd){
        this.mTransactionTypeCd = pTransactionTypeCd;
    }
    /**
     * Retrieves the TransactionTypeCd property.
     *
     * @return
     *  String containing the TransactionTypeCd property.
     */
    public String getTransactionTypeCd(){
        return mTransactionTypeCd;
    }


    /**
     * Sets the Amount property.
     *
     * @param pAmount
     *  java.math.BigDecimal to use to update the property.
     */
    public void setAmount(java.math.BigDecimal pAmount){
        this.mAmount = pAmount;
    }
    /**
     * Retrieves the Amount property.
     *
     * @return
     *  java.math.BigDecimal containing the Amount property.
     */
    public java.math.BigDecimal getAmount(){
        return mAmount;
    }


    /**
     * Sets the TransactionReference property.
     *
     * @param pTransactionReference
     *  String to use to update the property.
     */
    public void setTransactionReference(String pTransactionReference){
        this.mTransactionReference = pTransactionReference;
    }
    /**
     * Retrieves the TransactionReference property.
     *
     * @return
     *  String containing the TransactionReference property.
     */
    public String getTransactionReference(){
        return mTransactionReference;
    }


    /**
     * Sets the AuthCode property.
     *
     * @param pAuthCode
     *  String to use to update the property.
     */
    public void setAuthCode(String pAuthCode){
        this.mAuthCode = pAuthCode;
    }
    /**
     * Retrieves the AuthCode property.
     *
     * @return
     *  String containing the AuthCode property.
     */
    public String getAuthCode(){
        return mAuthCode;
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
