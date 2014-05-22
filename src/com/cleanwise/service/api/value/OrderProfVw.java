
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        OrderProfVw
 * Description:  This is a ValueObject class wrapping the database table CLV_ORDER_PROF.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import org.w3c.dom.*;


/**
 * <code>OrderProfVw</code> is a ValueObject class wrapping of the database table CLV_ORDER_PROF.
 */
public class OrderProfVw extends ValueObject
{
    
    private String mCustomer;// SQL type:CHAR
    private String mCustName;// SQL type:VARCHAR2
    private java.math.BigDecimal mOrderNbr;// SQL type:NUMBER
    private Date mOrderDate;// SQL type:DATE
    private String mCustInvoicePrefix;// SQL type:CHAR
    private java.math.BigDecimal mCustInvoiceNum;// SQL type:NUMBER
    private Date mCustInvoiceDate;// SQL type:DATE
    private java.math.BigDecimal mCustTotalPrice;// SQL type:NUMBER
    private java.math.BigDecimal mCustGoods;// SQL type:NUMBER
    private java.math.BigDecimal mCustMisc;// SQL type:NUMBER
    private java.math.BigDecimal mCustTax;// SQL type:NUMBER
    private String mApprVendor;// SQL type:CHAR
    private String mVendorName;// SQL type:VARCHAR2
    private String mApprPoNumber;// SQL type:CHAR
    private String mVenInvoiceNum;// SQL type:CHAR
    private Date mVenInvoiceDate;// SQL type:DATE
    private java.math.BigDecimal mVenTotalCost;// SQL type:NUMBER
    private java.math.BigDecimal mVenGoodsCost;// SQL type:NUMBER
    private java.math.BigDecimal mVenAdditionalCharges;// SQL type:NUMBER
    private java.math.BigDecimal mVenTax;// SQL type:NUMBER 

    /**
     * Constructor.
     */
    private OrderProfVw ()
    {
        mCustomer = "";
        mCustName = "";
        mCustInvoicePrefix = "";
        mApprVendor = "";
        mVendorName = "";
        mApprPoNumber = "";
        mVenInvoiceNum = "";
    }

    /**
     * Constructor. 
     */
    public OrderProfVw(String parm1, String parm2, java.math.BigDecimal parm3, Date parm4, String parm5, java.math.BigDecimal parm6, Date parm7, java.math.BigDecimal parm8, java.math.BigDecimal parm9, java.math.BigDecimal parm10, java.math.BigDecimal parm11, String parm12, String parm13, String parm14, String parm15, Date parm16, java.math.BigDecimal parm17, java.math.BigDecimal parm18, java.math.BigDecimal parm19, java.math.BigDecimal parm20)
    {
        mCustomer = parm1;
        mCustName = parm2;
        mOrderNbr = parm3;
        mOrderDate = parm4;
        mCustInvoicePrefix = parm5;
        mCustInvoiceNum = parm6;
        mCustInvoiceDate = parm7;
        mCustTotalPrice = parm8;
        mCustGoods = parm9;
        mCustMisc = parm10;
        mCustTax = parm11;
        mApprVendor = parm12;
        mVendorName = parm13;
        mApprPoNumber = parm14;
        mVenInvoiceNum = parm15;
        mVenInvoiceDate = parm16;
        mVenTotalCost = parm17;
        mVenGoodsCost = parm18;
        mVenAdditionalCharges = parm19;
        mVenTax = parm20;
        
    }

    /**
     * Creates a new OrderProfVw
     *
     * @return
     *  Newly initialized OrderProfVw object.
     */
    public static OrderProfVw createValue () 
    {
        OrderProfVw valueData = new OrderProfVw();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this OrderProfVw object
     */
    public String toString()
    {
        return "[" + "Customer=" + mCustomer + ", CustName=" + mCustName + ", OrderNbr=" + mOrderNbr + ", OrderDate=" + mOrderDate + ", CustInvoicePrefix=" + mCustInvoicePrefix + ", CustInvoiceNum=" + mCustInvoiceNum + ", CustInvoiceDate=" + mCustInvoiceDate + ", CustTotalPrice=" + mCustTotalPrice + ", CustGoods=" + mCustGoods + ", CustMisc=" + mCustMisc + ", CustTax=" + mCustTax + ", ApprVendor=" + mApprVendor + ", VendorName=" + mVendorName + ", ApprPoNumber=" + mApprPoNumber + ", VenInvoiceNum=" + mVenInvoiceNum + ", VenInvoiceDate=" + mVenInvoiceDate + ", VenTotalCost=" + mVenTotalCost + ", VenGoodsCost=" + mVenGoodsCost + ", VenAdditionalCharges=" + mVenAdditionalCharges + ", VenTax=" + mVenTax + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("OrderProf");
	root.setAttribute("Id", String.valueOf(mCustomer));

	Element node;

        node = doc.createElement("CustName");
        node.appendChild(doc.createTextNode(String.valueOf(mCustName)));
        root.appendChild(node);

        node = doc.createElement("OrderNbr");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderNbr)));
        root.appendChild(node);

        node = doc.createElement("OrderDate");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderDate)));
        root.appendChild(node);

        node = doc.createElement("CustInvoicePrefix");
        node.appendChild(doc.createTextNode(String.valueOf(mCustInvoicePrefix)));
        root.appendChild(node);

        node = doc.createElement("CustInvoiceNum");
        node.appendChild(doc.createTextNode(String.valueOf(mCustInvoiceNum)));
        root.appendChild(node);

        node = doc.createElement("CustInvoiceDate");
        node.appendChild(doc.createTextNode(String.valueOf(mCustInvoiceDate)));
        root.appendChild(node);

        node = doc.createElement("CustTotalPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mCustTotalPrice)));
        root.appendChild(node);

        node = doc.createElement("CustGoods");
        node.appendChild(doc.createTextNode(String.valueOf(mCustGoods)));
        root.appendChild(node);

        node = doc.createElement("CustMisc");
        node.appendChild(doc.createTextNode(String.valueOf(mCustMisc)));
        root.appendChild(node);

        node = doc.createElement("CustTax");
        node.appendChild(doc.createTextNode(String.valueOf(mCustTax)));
        root.appendChild(node);

        node = doc.createElement("ApprVendor");
        node.appendChild(doc.createTextNode(String.valueOf(mApprVendor)));
        root.appendChild(node);

        node = doc.createElement("VendorName");
        node.appendChild(doc.createTextNode(String.valueOf(mVendorName)));
        root.appendChild(node);

        node = doc.createElement("ApprPoNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mApprPoNumber)));
        root.appendChild(node);

        node = doc.createElement("VenInvoiceNum");
        node.appendChild(doc.createTextNode(String.valueOf(mVenInvoiceNum)));
        root.appendChild(node);

        node = doc.createElement("VenInvoiceDate");
        node.appendChild(doc.createTextNode(String.valueOf(mVenInvoiceDate)));
        root.appendChild(node);

        node = doc.createElement("VenTotalCost");
        node.appendChild(doc.createTextNode(String.valueOf(mVenTotalCost)));
        root.appendChild(node);

        node = doc.createElement("VenGoodsCost");
        node.appendChild(doc.createTextNode(String.valueOf(mVenGoodsCost)));
        root.appendChild(node);

        node = doc.createElement("VenAdditionalCharges");
        node.appendChild(doc.createTextNode(String.valueOf(mVenAdditionalCharges)));
        root.appendChild(node);

        node = doc.createElement("VenTax");
        node.appendChild(doc.createTextNode(String.valueOf(mVenTax)));
        root.appendChild(node);

        return root;
    }


    
    /**
     * Sets the Customer field.
     *
     * @param pCustomer
     *  String to use to update the field.
     */
    public void setCustomer(String pCustomer){
        this.mCustomer = pCustomer;
        setDirty(true);
    }
    /**
     * Retrieves the Customer field.
     *
     * @return
     *  String containing the Customer field.
     */
    public String getCustomer(){
        return mCustomer;
    }

    /**
     * Sets the CustName field.
     *
     * @param pCustName
     *  String to use to update the field.
     */
    public void setCustName(String pCustName){
        this.mCustName = pCustName;
        setDirty(true);
    }
    /**
     * Retrieves the CustName field.
     *
     * @return
     *  String containing the CustName field.
     */
    public String getCustName(){
        return mCustName;
    }

    /**
     * Sets the OrderNbr field.
     *
     * @param pOrderNbr
     *  java.math.BigDecimal to use to update the field.
     */
    public void setOrderNbr(java.math.BigDecimal pOrderNbr){
        this.mOrderNbr = pOrderNbr;
        setDirty(true);
    }
    /**
     * Retrieves the OrderNbr field.
     *
     * @return
     *  java.math.BigDecimal containing the OrderNbr field.
     */
    public java.math.BigDecimal getOrderNbr(){
        return mOrderNbr;
    }

    /**
     * Sets the OrderDate field.
     *
     * @param pOrderDate
     *  Date to use to update the field.
     */
    public void setOrderDate(Date pOrderDate){
        this.mOrderDate = pOrderDate;
        setDirty(true);
    }
    /**
     * Retrieves the OrderDate field.
     *
     * @return
     *  Date containing the OrderDate field.
     */
    public Date getOrderDate(){
        return mOrderDate;
    }

    /**
     * Sets the CustInvoicePrefix field.
     *
     * @param pCustInvoicePrefix
     *  String to use to update the field.
     */
    public void setCustInvoicePrefix(String pCustInvoicePrefix){
        this.mCustInvoicePrefix = pCustInvoicePrefix;
        setDirty(true);
    }
    /**
     * Retrieves the CustInvoicePrefix field.
     *
     * @return
     *  String containing the CustInvoicePrefix field.
     */
    public String getCustInvoicePrefix(){
        return mCustInvoicePrefix;
    }

    /**
     * Sets the CustInvoiceNum field.
     *
     * @param pCustInvoiceNum
     *  java.math.BigDecimal to use to update the field.
     */
    public void setCustInvoiceNum(java.math.BigDecimal pCustInvoiceNum){
        this.mCustInvoiceNum = pCustInvoiceNum;
        setDirty(true);
    }
    /**
     * Retrieves the CustInvoiceNum field.
     *
     * @return
     *  java.math.BigDecimal containing the CustInvoiceNum field.
     */
    public java.math.BigDecimal getCustInvoiceNum(){
        return mCustInvoiceNum;
    }

    /**
     * Sets the CustInvoiceDate field.
     *
     * @param pCustInvoiceDate
     *  Date to use to update the field.
     */
    public void setCustInvoiceDate(Date pCustInvoiceDate){
        this.mCustInvoiceDate = pCustInvoiceDate;
        setDirty(true);
    }
    /**
     * Retrieves the CustInvoiceDate field.
     *
     * @return
     *  Date containing the CustInvoiceDate field.
     */
    public Date getCustInvoiceDate(){
        return mCustInvoiceDate;
    }

    /**
     * Sets the CustTotalPrice field.
     *
     * @param pCustTotalPrice
     *  java.math.BigDecimal to use to update the field.
     */
    public void setCustTotalPrice(java.math.BigDecimal pCustTotalPrice){
        this.mCustTotalPrice = pCustTotalPrice;
        setDirty(true);
    }
    /**
     * Retrieves the CustTotalPrice field.
     *
     * @return
     *  java.math.BigDecimal containing the CustTotalPrice field.
     */
    public java.math.BigDecimal getCustTotalPrice(){
        return mCustTotalPrice;
    }

    /**
     * Sets the CustGoods field.
     *
     * @param pCustGoods
     *  java.math.BigDecimal to use to update the field.
     */
    public void setCustGoods(java.math.BigDecimal pCustGoods){
        this.mCustGoods = pCustGoods;
        setDirty(true);
    }
    /**
     * Retrieves the CustGoods field.
     *
     * @return
     *  java.math.BigDecimal containing the CustGoods field.
     */
    public java.math.BigDecimal getCustGoods(){
        return mCustGoods;
    }

    /**
     * Sets the CustMisc field.
     *
     * @param pCustMisc
     *  java.math.BigDecimal to use to update the field.
     */
    public void setCustMisc(java.math.BigDecimal pCustMisc){
        this.mCustMisc = pCustMisc;
        setDirty(true);
    }
    /**
     * Retrieves the CustMisc field.
     *
     * @return
     *  java.math.BigDecimal containing the CustMisc field.
     */
    public java.math.BigDecimal getCustMisc(){
        return mCustMisc;
    }

    /**
     * Sets the CustTax field.
     *
     * @param pCustTax
     *  java.math.BigDecimal to use to update the field.
     */
    public void setCustTax(java.math.BigDecimal pCustTax){
        this.mCustTax = pCustTax;
        setDirty(true);
    }
    /**
     * Retrieves the CustTax field.
     *
     * @return
     *  java.math.BigDecimal containing the CustTax field.
     */
    public java.math.BigDecimal getCustTax(){
        return mCustTax;
    }

    /**
     * Sets the ApprVendor field.
     *
     * @param pApprVendor
     *  String to use to update the field.
     */
    public void setApprVendor(String pApprVendor){
        this.mApprVendor = pApprVendor;
        setDirty(true);
    }
    /**
     * Retrieves the ApprVendor field.
     *
     * @return
     *  String containing the ApprVendor field.
     */
    public String getApprVendor(){
        return mApprVendor;
    }

    /**
     * Sets the VendorName field.
     *
     * @param pVendorName
     *  String to use to update the field.
     */
    public void setVendorName(String pVendorName){
        this.mVendorName = pVendorName;
        setDirty(true);
    }
    /**
     * Retrieves the VendorName field.
     *
     * @return
     *  String containing the VendorName field.
     */
    public String getVendorName(){
        return mVendorName;
    }

    /**
     * Sets the ApprPoNumber field.
     *
     * @param pApprPoNumber
     *  String to use to update the field.
     */
    public void setApprPoNumber(String pApprPoNumber){
        this.mApprPoNumber = pApprPoNumber;
        setDirty(true);
    }
    /**
     * Retrieves the ApprPoNumber field.
     *
     * @return
     *  String containing the ApprPoNumber field.
     */
    public String getApprPoNumber(){
        return mApprPoNumber;
    }

    /**
     * Sets the VenInvoiceNum field.
     *
     * @param pVenInvoiceNum
     *  String to use to update the field.
     */
    public void setVenInvoiceNum(String pVenInvoiceNum){
        this.mVenInvoiceNum = pVenInvoiceNum;
        setDirty(true);
    }
    /**
     * Retrieves the VenInvoiceNum field.
     *
     * @return
     *  String containing the VenInvoiceNum field.
     */
    public String getVenInvoiceNum(){
        return mVenInvoiceNum;
    }

    /**
     * Sets the VenInvoiceDate field.
     *
     * @param pVenInvoiceDate
     *  Date to use to update the field.
     */
    public void setVenInvoiceDate(Date pVenInvoiceDate){
        this.mVenInvoiceDate = pVenInvoiceDate;
        setDirty(true);
    }
    /**
     * Retrieves the VenInvoiceDate field.
     *
     * @return
     *  Date containing the VenInvoiceDate field.
     */
    public Date getVenInvoiceDate(){
        return mVenInvoiceDate;
    }

    /**
     * Sets the VenTotalCost field.
     *
     * @param pVenTotalCost
     *  java.math.BigDecimal to use to update the field.
     */
    public void setVenTotalCost(java.math.BigDecimal pVenTotalCost){
        this.mVenTotalCost = pVenTotalCost;
        setDirty(true);
    }
    /**
     * Retrieves the VenTotalCost field.
     *
     * @return
     *  java.math.BigDecimal containing the VenTotalCost field.
     */
    public java.math.BigDecimal getVenTotalCost(){
        return mVenTotalCost;
    }

    /**
     * Sets the VenGoodsCost field.
     *
     * @param pVenGoodsCost
     *  java.math.BigDecimal to use to update the field.
     */
    public void setVenGoodsCost(java.math.BigDecimal pVenGoodsCost){
        this.mVenGoodsCost = pVenGoodsCost;
        setDirty(true);
    }
    /**
     * Retrieves the VenGoodsCost field.
     *
     * @return
     *  java.math.BigDecimal containing the VenGoodsCost field.
     */
    public java.math.BigDecimal getVenGoodsCost(){
        return mVenGoodsCost;
    }

    /**
     * Sets the VenAdditionalCharges field.
     *
     * @param pVenAdditionalCharges
     *  java.math.BigDecimal to use to update the field.
     */
    public void setVenAdditionalCharges(java.math.BigDecimal pVenAdditionalCharges){
        this.mVenAdditionalCharges = pVenAdditionalCharges;
        setDirty(true);
    }
    /**
     * Retrieves the VenAdditionalCharges field.
     *
     * @return
     *  java.math.BigDecimal containing the VenAdditionalCharges field.
     */
    public java.math.BigDecimal getVenAdditionalCharges(){
        return mVenAdditionalCharges;
    }

    /**
     * Sets the VenTax field.
     *
     * @param pVenTax
     *  java.math.BigDecimal to use to update the field.
     */
    public void setVenTax(java.math.BigDecimal pVenTax){
        this.mVenTax = pVenTax;
        setDirty(true);
    }
    /**
     * Retrieves the VenTax field.
     *
     * @return
     *  java.math.BigDecimal containing the VenTax field.
     */
    public java.math.BigDecimal getVenTax(){
        return mVenTax;
    }

    
}
