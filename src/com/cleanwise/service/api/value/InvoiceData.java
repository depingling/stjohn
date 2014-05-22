
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        InvoiceData
 * Description:  This is a ValueObject class wrapping the database table CLW_INVOICE.
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
 * <code>InvoiceData</code> is a ValueObject class wrapping of the database table CLW_INVOICE.
 */
public class InvoiceData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 576607430840785453L;
    
    private int mInvoiceId;// SQL type:NUMBER, not null
    private int mOrderId;// SQL type:NUMBER
    private int mShippingHistoryId;// SQL type:NUMBER
    private String mInvoiceNum;// SQL type:VARCHAR2
    private Date mInvoiceDate;// SQL type:DATE
    private String mErpPoNum;// SQL type:VARCHAR2
    private String mErpOrderNum;// SQL type:VARCHAR2
    private String mRefOrderNum;// SQL type:VARCHAR2
    private String mDistributorShipmentId;// SQL type:VARCHAR2
    private String mBillToName;// SQL type:VARCHAR2
    private String mBillToAddress1;// SQL type:VARCHAR2
    private String mBillToAddress2;// SQL type:VARCHAR2
    private String mBillToAddress3;// SQL type:VARCHAR2
    private String mBillToAddress4;// SQL type:VARCHAR2
    private String mBillToCity;// SQL type:VARCHAR2
    private String mBillToState;// SQL type:VARCHAR2
    private String mBillToPostalCode;// SQL type:VARCHAR2
    private String mBillToCountry;// SQL type:VARCHAR2
    private java.math.BigDecimal mGrossAmount;// SQL type:NUMBER
    private java.math.BigDecimal mFreightAmount;// SQL type:NUMBER
    private java.math.BigDecimal mTaxAmount;// SQL type:NUMBER
    private java.math.BigDecimal mOtherAmount;// SQL type:NUMBER
    private java.math.BigDecimal mTotalAmount;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE
    private String mModBy;// SQL type:VARCHAR2 

    /**
     * Constructor.
     */
    private InvoiceData ()
    {
        mInvoiceNum = "";
        mErpPoNum = "";
        mErpOrderNum = "";
        mRefOrderNum = "";
        mDistributorShipmentId = "";
        mBillToName = "";
        mBillToAddress1 = "";
        mBillToAddress2 = "";
        mBillToAddress3 = "";
        mBillToAddress4 = "";
        mBillToCity = "";
        mBillToState = "";
        mBillToPostalCode = "";
        mBillToCountry = "";
        mAddBy = "";
        mModBy = "";
    }

    /**
     * Constructor. 
     */
    public InvoiceData(int parm1, int parm2, int parm3, String parm4, Date parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13, String parm14, String parm15, String parm16, String parm17, String parm18, java.math.BigDecimal parm19, java.math.BigDecimal parm20, java.math.BigDecimal parm21, java.math.BigDecimal parm22, java.math.BigDecimal parm23, Date parm24, String parm25, Date parm26, String parm27)
    {
        mInvoiceId = parm1;
        mOrderId = parm2;
        mShippingHistoryId = parm3;
        mInvoiceNum = parm4;
        mInvoiceDate = parm5;
        mErpPoNum = parm6;
        mErpOrderNum = parm7;
        mRefOrderNum = parm8;
        mDistributorShipmentId = parm9;
        mBillToName = parm10;
        mBillToAddress1 = parm11;
        mBillToAddress2 = parm12;
        mBillToAddress3 = parm13;
        mBillToAddress4 = parm14;
        mBillToCity = parm15;
        mBillToState = parm16;
        mBillToPostalCode = parm17;
        mBillToCountry = parm18;
        mGrossAmount = parm19;
        mFreightAmount = parm20;
        mTaxAmount = parm21;
        mOtherAmount = parm22;
        mTotalAmount = parm23;
        mAddDate = parm24;
        mAddBy = parm25;
        mModDate = parm26;
        mModBy = parm27;
        
    }

    /**
     * Creates a new InvoiceData
     *
     * @return
     *  Newly initialized InvoiceData object.
     */
    public static InvoiceData createValue () 
    {
        InvoiceData valueData = new InvoiceData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this InvoiceData object
     */
    public String toString()
    {
        return "[" + "InvoiceId=" + mInvoiceId + ", OrderId=" + mOrderId + ", ShippingHistoryId=" + mShippingHistoryId + ", InvoiceNum=" + mInvoiceNum + ", InvoiceDate=" + mInvoiceDate + ", ErpPoNum=" + mErpPoNum + ", ErpOrderNum=" + mErpOrderNum + ", RefOrderNum=" + mRefOrderNum + ", DistributorShipmentId=" + mDistributorShipmentId + ", BillToName=" + mBillToName + ", BillToAddress1=" + mBillToAddress1 + ", BillToAddress2=" + mBillToAddress2 + ", BillToAddress3=" + mBillToAddress3 + ", BillToAddress4=" + mBillToAddress4 + ", BillToCity=" + mBillToCity + ", BillToState=" + mBillToState + ", BillToPostalCode=" + mBillToPostalCode + ", BillToCountry=" + mBillToCountry + ", GrossAmount=" + mGrossAmount + ", FreightAmount=" + mFreightAmount + ", TaxAmount=" + mTaxAmount + ", OtherAmount=" + mOtherAmount + ", TotalAmount=" + mTotalAmount + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("Invoice");
	root.setAttribute("Id", String.valueOf(mInvoiceId));

	Element node;

        node = doc.createElement("OrderId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderId)));
        root.appendChild(node);

        node = doc.createElement("ShippingHistoryId");
        node.appendChild(doc.createTextNode(String.valueOf(mShippingHistoryId)));
        root.appendChild(node);

        node = doc.createElement("InvoiceNum");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceNum)));
        root.appendChild(node);

        node = doc.createElement("InvoiceDate");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceDate)));
        root.appendChild(node);

        node = doc.createElement("ErpPoNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpPoNum)));
        root.appendChild(node);

        node = doc.createElement("ErpOrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mErpOrderNum)));
        root.appendChild(node);

        node = doc.createElement("RefOrderNum");
        node.appendChild(doc.createTextNode(String.valueOf(mRefOrderNum)));
        root.appendChild(node);

        node = doc.createElement("DistributorShipmentId");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorShipmentId)));
        root.appendChild(node);

        node = doc.createElement("BillToName");
        node.appendChild(doc.createTextNode(String.valueOf(mBillToName)));
        root.appendChild(node);

        node = doc.createElement("BillToAddress1");
        node.appendChild(doc.createTextNode(String.valueOf(mBillToAddress1)));
        root.appendChild(node);

        node = doc.createElement("BillToAddress2");
        node.appendChild(doc.createTextNode(String.valueOf(mBillToAddress2)));
        root.appendChild(node);

        node = doc.createElement("BillToAddress3");
        node.appendChild(doc.createTextNode(String.valueOf(mBillToAddress3)));
        root.appendChild(node);

        node = doc.createElement("BillToAddress4");
        node.appendChild(doc.createTextNode(String.valueOf(mBillToAddress4)));
        root.appendChild(node);

        node = doc.createElement("BillToCity");
        node.appendChild(doc.createTextNode(String.valueOf(mBillToCity)));
        root.appendChild(node);

        node = doc.createElement("BillToState");
        node.appendChild(doc.createTextNode(String.valueOf(mBillToState)));
        root.appendChild(node);

        node = doc.createElement("BillToPostalCode");
        node.appendChild(doc.createTextNode(String.valueOf(mBillToPostalCode)));
        root.appendChild(node);

        node = doc.createElement("BillToCountry");
        node.appendChild(doc.createTextNode(String.valueOf(mBillToCountry)));
        root.appendChild(node);

        node = doc.createElement("GrossAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mGrossAmount)));
        root.appendChild(node);

        node = doc.createElement("FreightAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightAmount)));
        root.appendChild(node);

        node = doc.createElement("TaxAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mTaxAmount)));
        root.appendChild(node);

        node = doc.createElement("OtherAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mOtherAmount)));
        root.appendChild(node);

        node = doc.createElement("TotalAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mTotalAmount)));
        root.appendChild(node);

        node = doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node = doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node = doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        node = doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        return root;
    }
    
    /**
     * Sets the InvoiceId field. This field is required to be set in the database.
     *
     * @param pInvoiceId
     *  int to use to update the field.
     */
    public void setInvoiceId(int pInvoiceId){
        this.mInvoiceId = pInvoiceId;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceId field.
     *
     * @return
     *  int containing the InvoiceId field.
     */
    public int getInvoiceId(){
        return mInvoiceId;
    }

    /**
     * Sets the OrderId field.
     *
     * @param pOrderId
     *  int to use to update the field.
     */
    public void setOrderId(int pOrderId){
        this.mOrderId = pOrderId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderId field.
     *
     * @return
     *  int containing the OrderId field.
     */
    public int getOrderId(){
        return mOrderId;
    }

    /**
     * Sets the ShippingHistoryId field.
     *
     * @param pShippingHistoryId
     *  int to use to update the field.
     */
    public void setShippingHistoryId(int pShippingHistoryId){
        this.mShippingHistoryId = pShippingHistoryId;
        setDirty(true);
    }
    /**
     * Retrieves the ShippingHistoryId field.
     *
     * @return
     *  int containing the ShippingHistoryId field.
     */
    public int getShippingHistoryId(){
        return mShippingHistoryId;
    }

    /**
     * Sets the InvoiceNum field.
     *
     * @param pInvoiceNum
     *  String to use to update the field.
     */
    public void setInvoiceNum(String pInvoiceNum){
        this.mInvoiceNum = pInvoiceNum;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceNum field.
     *
     * @return
     *  String containing the InvoiceNum field.
     */
    public String getInvoiceNum(){
        return mInvoiceNum;
    }

    /**
     * Sets the InvoiceDate field.
     *
     * @param pInvoiceDate
     *  Date to use to update the field.
     */
    public void setInvoiceDate(Date pInvoiceDate){
        this.mInvoiceDate = pInvoiceDate;
        setDirty(true);
    }
    /**
     * Retrieves the InvoiceDate field.
     *
     * @return
     *  Date containing the InvoiceDate field.
     */
    public Date getInvoiceDate(){
        return mInvoiceDate;
    }

    /**
     * Sets the ErpPoNum field.
     *
     * @param pErpPoNum
     *  String to use to update the field.
     */
    public void setErpPoNum(String pErpPoNum){
        this.mErpPoNum = pErpPoNum;
        setDirty(true);
    }
    /**
     * Retrieves the ErpPoNum field.
     *
     * @return
     *  String containing the ErpPoNum field.
     */
    public String getErpPoNum(){
        return mErpPoNum;
    }

    /**
     * Sets the ErpOrderNum field.
     *
     * @param pErpOrderNum
     *  String to use to update the field.
     */
    public void setErpOrderNum(String pErpOrderNum){
        this.mErpOrderNum = pErpOrderNum;
        setDirty(true);
    }
    /**
     * Retrieves the ErpOrderNum field.
     *
     * @return
     *  String containing the ErpOrderNum field.
     */
    public String getErpOrderNum(){
        return mErpOrderNum;
    }

    /**
     * Sets the RefOrderNum field.
     *
     * @param pRefOrderNum
     *  String to use to update the field.
     */
    public void setRefOrderNum(String pRefOrderNum){
        this.mRefOrderNum = pRefOrderNum;
        setDirty(true);
    }
    /**
     * Retrieves the RefOrderNum field.
     *
     * @return
     *  String containing the RefOrderNum field.
     */
    public String getRefOrderNum(){
        return mRefOrderNum;
    }

    /**
     * Sets the DistributorShipmentId field.
     *
     * @param pDistributorShipmentId
     *  String to use to update the field.
     */
    public void setDistributorShipmentId(String pDistributorShipmentId){
        this.mDistributorShipmentId = pDistributorShipmentId;
        setDirty(true);
    }
    /**
     * Retrieves the DistributorShipmentId field.
     *
     * @return
     *  String containing the DistributorShipmentId field.
     */
    public String getDistributorShipmentId(){
        return mDistributorShipmentId;
    }

    /**
     * Sets the BillToName field.
     *
     * @param pBillToName
     *  String to use to update the field.
     */
    public void setBillToName(String pBillToName){
        this.mBillToName = pBillToName;
        setDirty(true);
    }
    /**
     * Retrieves the BillToName field.
     *
     * @return
     *  String containing the BillToName field.
     */
    public String getBillToName(){
        return mBillToName;
    }

    /**
     * Sets the BillToAddress1 field.
     *
     * @param pBillToAddress1
     *  String to use to update the field.
     */
    public void setBillToAddress1(String pBillToAddress1){
        this.mBillToAddress1 = pBillToAddress1;
        setDirty(true);
    }
    /**
     * Retrieves the BillToAddress1 field.
     *
     * @return
     *  String containing the BillToAddress1 field.
     */
    public String getBillToAddress1(){
        return mBillToAddress1;
    }

    /**
     * Sets the BillToAddress2 field.
     *
     * @param pBillToAddress2
     *  String to use to update the field.
     */
    public void setBillToAddress2(String pBillToAddress2){
        this.mBillToAddress2 = pBillToAddress2;
        setDirty(true);
    }
    /**
     * Retrieves the BillToAddress2 field.
     *
     * @return
     *  String containing the BillToAddress2 field.
     */
    public String getBillToAddress2(){
        return mBillToAddress2;
    }

    /**
     * Sets the BillToAddress3 field.
     *
     * @param pBillToAddress3
     *  String to use to update the field.
     */
    public void setBillToAddress3(String pBillToAddress3){
        this.mBillToAddress3 = pBillToAddress3;
        setDirty(true);
    }
    /**
     * Retrieves the BillToAddress3 field.
     *
     * @return
     *  String containing the BillToAddress3 field.
     */
    public String getBillToAddress3(){
        return mBillToAddress3;
    }

    /**
     * Sets the BillToAddress4 field.
     *
     * @param pBillToAddress4
     *  String to use to update the field.
     */
    public void setBillToAddress4(String pBillToAddress4){
        this.mBillToAddress4 = pBillToAddress4;
        setDirty(true);
    }
    /**
     * Retrieves the BillToAddress4 field.
     *
     * @return
     *  String containing the BillToAddress4 field.
     */
    public String getBillToAddress4(){
        return mBillToAddress4;
    }

    /**
     * Sets the BillToCity field.
     *
     * @param pBillToCity
     *  String to use to update the field.
     */
    public void setBillToCity(String pBillToCity){
        this.mBillToCity = pBillToCity;
        setDirty(true);
    }
    /**
     * Retrieves the BillToCity field.
     *
     * @return
     *  String containing the BillToCity field.
     */
    public String getBillToCity(){
        return mBillToCity;
    }

    /**
     * Sets the BillToState field.
     *
     * @param pBillToState
     *  String to use to update the field.
     */
    public void setBillToState(String pBillToState){
        this.mBillToState = pBillToState;
        setDirty(true);
    }
    /**
     * Retrieves the BillToState field.
     *
     * @return
     *  String containing the BillToState field.
     */
    public String getBillToState(){
        return mBillToState;
    }

    /**
     * Sets the BillToPostalCode field.
     *
     * @param pBillToPostalCode
     *  String to use to update the field.
     */
    public void setBillToPostalCode(String pBillToPostalCode){
        this.mBillToPostalCode = pBillToPostalCode;
        setDirty(true);
    }
    /**
     * Retrieves the BillToPostalCode field.
     *
     * @return
     *  String containing the BillToPostalCode field.
     */
    public String getBillToPostalCode(){
        return mBillToPostalCode;
    }

    /**
     * Sets the BillToCountry field.
     *
     * @param pBillToCountry
     *  String to use to update the field.
     */
    public void setBillToCountry(String pBillToCountry){
        this.mBillToCountry = pBillToCountry;
        setDirty(true);
    }
    /**
     * Retrieves the BillToCountry field.
     *
     * @return
     *  String containing the BillToCountry field.
     */
    public String getBillToCountry(){
        return mBillToCountry;
    }

    /**
     * Sets the GrossAmount field.
     *
     * @param pGrossAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setGrossAmount(java.math.BigDecimal pGrossAmount){
        this.mGrossAmount = pGrossAmount;
        setDirty(true);
    }
    /**
     * Retrieves the GrossAmount field.
     *
     * @return
     *  java.math.BigDecimal containing the GrossAmount field.
     */
    public java.math.BigDecimal getGrossAmount(){
        return mGrossAmount;
    }

    /**
     * Sets the FreightAmount field.
     *
     * @param pFreightAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setFreightAmount(java.math.BigDecimal pFreightAmount){
        this.mFreightAmount = pFreightAmount;
        setDirty(true);
    }
    /**
     * Retrieves the FreightAmount field.
     *
     * @return
     *  java.math.BigDecimal containing the FreightAmount field.
     */
    public java.math.BigDecimal getFreightAmount(){
        return mFreightAmount;
    }

    /**
     * Sets the TaxAmount field.
     *
     * @param pTaxAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setTaxAmount(java.math.BigDecimal pTaxAmount){
        this.mTaxAmount = pTaxAmount;
        setDirty(true);
    }
    /**
     * Retrieves the TaxAmount field.
     *
     * @return
     *  java.math.BigDecimal containing the TaxAmount field.
     */
    public java.math.BigDecimal getTaxAmount(){
        return mTaxAmount;
    }

    /**
     * Sets the OtherAmount field.
     *
     * @param pOtherAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setOtherAmount(java.math.BigDecimal pOtherAmount){
        this.mOtherAmount = pOtherAmount;
        setDirty(true);
    }
    /**
     * Retrieves the OtherAmount field.
     *
     * @return
     *  java.math.BigDecimal containing the OtherAmount field.
     */
    public java.math.BigDecimal getOtherAmount(){
        return mOtherAmount;
    }

    /**
     * Sets the TotalAmount field.
     *
     * @param pTotalAmount
     *  java.math.BigDecimal to use to update the field.
     */
    public void setTotalAmount(java.math.BigDecimal pTotalAmount){
        this.mTotalAmount = pTotalAmount;
        setDirty(true);
    }
    /**
     * Retrieves the TotalAmount field.
     *
     * @return
     *  java.math.BigDecimal containing the TotalAmount field.
     */
    public java.math.BigDecimal getTotalAmount(){
        return mTotalAmount;
    }

    /**
     * Sets the AddDate field.
     *
     * @param pAddDate
     *  Date to use to update the field.
     */
    public void setAddDate(Date pAddDate){
        this.mAddDate = pAddDate;
        setDirty(true);
    }
    /**
     * Retrieves the AddDate field.
     *
     * @return
     *  Date containing the AddDate field.
     */
    public Date getAddDate(){
        return mAddDate;
    }

    /**
     * Sets the AddBy field.
     *
     * @param pAddBy
     *  String to use to update the field.
     */
    public void setAddBy(String pAddBy){
        this.mAddBy = pAddBy;
        setDirty(true);
    }
    /**
     * Retrieves the AddBy field.
     *
     * @return
     *  String containing the AddBy field.
     */
    public String getAddBy(){
        return mAddBy;
    }

    /**
     * Sets the ModDate field.
     *
     * @param pModDate
     *  Date to use to update the field.
     */
    public void setModDate(Date pModDate){
        this.mModDate = pModDate;
        setDirty(true);
    }
    /**
     * Retrieves the ModDate field.
     *
     * @return
     *  Date containing the ModDate field.
     */
    public Date getModDate(){
        return mModDate;
    }

    /**
     * Sets the ModBy field.
     *
     * @param pModBy
     *  String to use to update the field.
     */
    public void setModBy(String pModBy){
        this.mModBy = pModBy;
        setDirty(true);
    }
    /**
     * Retrieves the ModBy field.
     *
     * @return
     *  String containing the ModBy field.
     */
    public String getModBy(){
        return mModBy;
    }

    
}
