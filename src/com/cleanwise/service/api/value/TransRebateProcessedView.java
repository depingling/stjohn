
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        TransRebateProcessedView
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
 * <code>TransRebateProcessedView</code> is a ViewObject class for UI.
 */
public class TransRebateProcessedView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private int mTransRebateProcessedId;
    private String mPaymentMethod;
    private String mInvoiceNumber;
    private String mRebateNumber;
    private String mCustomerRefNumber;
    private String mConnectionStatus;
    private String mContractNumber;
    private String mContractDesc;
    private String mProductSku;
    private String mProductDesc;
    private String mQuantity;
    private String mUom;
    private java.math.BigDecimal mRebatePaid;

    /**
     * Constructor.
     */
    public TransRebateProcessedView ()
    {
        mPaymentMethod = "";
        mInvoiceNumber = "";
        mRebateNumber = "";
        mCustomerRefNumber = "";
        mConnectionStatus = "";
        mContractNumber = "";
        mContractDesc = "";
        mProductSku = "";
        mProductDesc = "";
        mQuantity = "";
        mUom = "";
    }

    /**
     * Constructor. 
     */
    public TransRebateProcessedView(int parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12, java.math.BigDecimal parm13)
    {
        mTransRebateProcessedId = parm1;
        mPaymentMethod = parm2;
        mInvoiceNumber = parm3;
        mRebateNumber = parm4;
        mCustomerRefNumber = parm5;
        mConnectionStatus = parm6;
        mContractNumber = parm7;
        mContractDesc = parm8;
        mProductSku = parm9;
        mProductDesc = parm10;
        mQuantity = parm11;
        mUom = parm12;
        mRebatePaid = parm13;
        
    }

    /**
     * Creates a new TransRebateProcessedView
     *
     * @return
     *  Newly initialized TransRebateProcessedView object.
     */
    public static TransRebateProcessedView createValue () 
    {
        TransRebateProcessedView valueView = new TransRebateProcessedView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this TransRebateProcessedView object
     */
    public String toString()
    {
        return "[" + "TransRebateProcessedId=" + mTransRebateProcessedId + ", PaymentMethod=" + mPaymentMethod + ", InvoiceNumber=" + mInvoiceNumber + ", RebateNumber=" + mRebateNumber + ", CustomerRefNumber=" + mCustomerRefNumber + ", ConnectionStatus=" + mConnectionStatus + ", ContractNumber=" + mContractNumber + ", ContractDesc=" + mContractDesc + ", ProductSku=" + mProductSku + ", ProductDesc=" + mProductDesc + ", Quantity=" + mQuantity + ", Uom=" + mUom + ", RebatePaid=" + mRebatePaid + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("TransRebateProcessed");
	root.setAttribute("Id", String.valueOf(mTransRebateProcessedId));

	Element node;

        node = doc.createElement("PaymentMethod");
        node.appendChild(doc.createTextNode(String.valueOf(mPaymentMethod)));
        root.appendChild(node);

        node = doc.createElement("InvoiceNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceNumber)));
        root.appendChild(node);

        node = doc.createElement("RebateNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mRebateNumber)));
        root.appendChild(node);

        node = doc.createElement("CustomerRefNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerRefNumber)));
        root.appendChild(node);

        node = doc.createElement("ConnectionStatus");
        node.appendChild(doc.createTextNode(String.valueOf(mConnectionStatus)));
        root.appendChild(node);

        node = doc.createElement("ContractNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mContractNumber)));
        root.appendChild(node);

        node = doc.createElement("ContractDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mContractDesc)));
        root.appendChild(node);

        node = doc.createElement("ProductSku");
        node.appendChild(doc.createTextNode(String.valueOf(mProductSku)));
        root.appendChild(node);

        node = doc.createElement("ProductDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mProductDesc)));
        root.appendChild(node);

        node = doc.createElement("Quantity");
        node.appendChild(doc.createTextNode(String.valueOf(mQuantity)));
        root.appendChild(node);

        node = doc.createElement("Uom");
        node.appendChild(doc.createTextNode(String.valueOf(mUom)));
        root.appendChild(node);

        node = doc.createElement("RebatePaid");
        node.appendChild(doc.createTextNode(String.valueOf(mRebatePaid)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public TransRebateProcessedView copy()  {
      TransRebateProcessedView obj = new TransRebateProcessedView();
      obj.setTransRebateProcessedId(mTransRebateProcessedId);
      obj.setPaymentMethod(mPaymentMethod);
      obj.setInvoiceNumber(mInvoiceNumber);
      obj.setRebateNumber(mRebateNumber);
      obj.setCustomerRefNumber(mCustomerRefNumber);
      obj.setConnectionStatus(mConnectionStatus);
      obj.setContractNumber(mContractNumber);
      obj.setContractDesc(mContractDesc);
      obj.setProductSku(mProductSku);
      obj.setProductDesc(mProductDesc);
      obj.setQuantity(mQuantity);
      obj.setUom(mUom);
      obj.setRebatePaid(mRebatePaid);
      
      return obj;
    }

    
    /**
     * Sets the TransRebateProcessedId property.
     *
     * @param pTransRebateProcessedId
     *  int to use to update the property.
     */
    public void setTransRebateProcessedId(int pTransRebateProcessedId){
        this.mTransRebateProcessedId = pTransRebateProcessedId;
    }
    /**
     * Retrieves the TransRebateProcessedId property.
     *
     * @return
     *  int containing the TransRebateProcessedId property.
     */
    public int getTransRebateProcessedId(){
        return mTransRebateProcessedId;
    }


    /**
     * Sets the PaymentMethod property.
     *
     * @param pPaymentMethod
     *  String to use to update the property.
     */
    public void setPaymentMethod(String pPaymentMethod){
        this.mPaymentMethod = pPaymentMethod;
    }
    /**
     * Retrieves the PaymentMethod property.
     *
     * @return
     *  String containing the PaymentMethod property.
     */
    public String getPaymentMethod(){
        return mPaymentMethod;
    }


    /**
     * Sets the InvoiceNumber property.
     *
     * @param pInvoiceNumber
     *  String to use to update the property.
     */
    public void setInvoiceNumber(String pInvoiceNumber){
        this.mInvoiceNumber = pInvoiceNumber;
    }
    /**
     * Retrieves the InvoiceNumber property.
     *
     * @return
     *  String containing the InvoiceNumber property.
     */
    public String getInvoiceNumber(){
        return mInvoiceNumber;
    }


    /**
     * Sets the RebateNumber property.
     *
     * @param pRebateNumber
     *  String to use to update the property.
     */
    public void setRebateNumber(String pRebateNumber){
        this.mRebateNumber = pRebateNumber;
    }
    /**
     * Retrieves the RebateNumber property.
     *
     * @return
     *  String containing the RebateNumber property.
     */
    public String getRebateNumber(){
        return mRebateNumber;
    }


    /**
     * Sets the CustomerRefNumber property.
     *
     * @param pCustomerRefNumber
     *  String to use to update the property.
     */
    public void setCustomerRefNumber(String pCustomerRefNumber){
        this.mCustomerRefNumber = pCustomerRefNumber;
    }
    /**
     * Retrieves the CustomerRefNumber property.
     *
     * @return
     *  String containing the CustomerRefNumber property.
     */
    public String getCustomerRefNumber(){
        return mCustomerRefNumber;
    }


    /**
     * Sets the ConnectionStatus property.
     *
     * @param pConnectionStatus
     *  String to use to update the property.
     */
    public void setConnectionStatus(String pConnectionStatus){
        this.mConnectionStatus = pConnectionStatus;
    }
    /**
     * Retrieves the ConnectionStatus property.
     *
     * @return
     *  String containing the ConnectionStatus property.
     */
    public String getConnectionStatus(){
        return mConnectionStatus;
    }


    /**
     * Sets the ContractNumber property.
     *
     * @param pContractNumber
     *  String to use to update the property.
     */
    public void setContractNumber(String pContractNumber){
        this.mContractNumber = pContractNumber;
    }
    /**
     * Retrieves the ContractNumber property.
     *
     * @return
     *  String containing the ContractNumber property.
     */
    public String getContractNumber(){
        return mContractNumber;
    }


    /**
     * Sets the ContractDesc property.
     *
     * @param pContractDesc
     *  String to use to update the property.
     */
    public void setContractDesc(String pContractDesc){
        this.mContractDesc = pContractDesc;
    }
    /**
     * Retrieves the ContractDesc property.
     *
     * @return
     *  String containing the ContractDesc property.
     */
    public String getContractDesc(){
        return mContractDesc;
    }


    /**
     * Sets the ProductSku property.
     *
     * @param pProductSku
     *  String to use to update the property.
     */
    public void setProductSku(String pProductSku){
        this.mProductSku = pProductSku;
    }
    /**
     * Retrieves the ProductSku property.
     *
     * @return
     *  String containing the ProductSku property.
     */
    public String getProductSku(){
        return mProductSku;
    }


    /**
     * Sets the ProductDesc property.
     *
     * @param pProductDesc
     *  String to use to update the property.
     */
    public void setProductDesc(String pProductDesc){
        this.mProductDesc = pProductDesc;
    }
    /**
     * Retrieves the ProductDesc property.
     *
     * @return
     *  String containing the ProductDesc property.
     */
    public String getProductDesc(){
        return mProductDesc;
    }


    /**
     * Sets the Quantity property.
     *
     * @param pQuantity
     *  String to use to update the property.
     */
    public void setQuantity(String pQuantity){
        this.mQuantity = pQuantity;
    }
    /**
     * Retrieves the Quantity property.
     *
     * @return
     *  String containing the Quantity property.
     */
    public String getQuantity(){
        return mQuantity;
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
     * Sets the RebatePaid property.
     *
     * @param pRebatePaid
     *  java.math.BigDecimal to use to update the property.
     */
    public void setRebatePaid(java.math.BigDecimal pRebatePaid){
        this.mRebatePaid = pRebatePaid;
    }
    /**
     * Retrieves the RebatePaid property.
     *
     * @return
     *  java.math.BigDecimal containing the RebatePaid property.
     */
    public java.math.BigDecimal getRebatePaid(){
        return mRebatePaid;
    }


    
}
