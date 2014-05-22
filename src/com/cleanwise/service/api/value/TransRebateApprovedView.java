
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        TransRebateApprovedView
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
 * <code>TransRebateApprovedView</code> is a ViewObject class for UI.
 */
public class TransRebateApprovedView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private int mTransRebateApprovedId;
    private String mRebateNumber;
    private String mConnectionStatus;
    private String mDistributorNumber;
    private String mContractNumber;
    private String mContractDesc;
    private String mProductSku;
    private String mProductDesc;
    private String mQuantity;
    private String mUom;
    private java.math.BigDecimal mRbtAmountAdj;
    private String mRebateTypeCode;
    private java.util.Date mRebateDate;
    private String mSalesOrg;
    private String mDistChannel;
    private String mDivision;
    private String mReason;
    private java.math.BigDecimal mUnitPrice;
    private String mDistProductSku;

    /**
     * Constructor.
     */
    public TransRebateApprovedView ()
    {
        mRebateNumber = "";
        mConnectionStatus = "";
        mDistributorNumber = "";
        mContractNumber = "";
        mContractDesc = "";
        mProductSku = "";
        mProductDesc = "";
        mQuantity = "";
        mUom = "";
        mRebateTypeCode = "";
        mSalesOrg = "";
        mDistChannel = "";
        mDivision = "";
        mReason = "";
        mDistProductSku = "";
    }

    /**
     * Constructor. 
     */
    public TransRebateApprovedView(int parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, java.math.BigDecimal parm11, String parm12, java.util.Date parm13, String parm14, String parm15, String parm16, String parm17, java.math.BigDecimal parm18, String parm19)
    {
        mTransRebateApprovedId = parm1;
        mRebateNumber = parm2;
        mConnectionStatus = parm3;
        mDistributorNumber = parm4;
        mContractNumber = parm5;
        mContractDesc = parm6;
        mProductSku = parm7;
        mProductDesc = parm8;
        mQuantity = parm9;
        mUom = parm10;
        mRbtAmountAdj = parm11;
        mRebateTypeCode = parm12;
        mRebateDate = parm13;
        mSalesOrg = parm14;
        mDistChannel = parm15;
        mDivision = parm16;
        mReason = parm17;
        mUnitPrice = parm18;
        mDistProductSku = parm19;
        
    }

    /**
     * Creates a new TransRebateApprovedView
     *
     * @return
     *  Newly initialized TransRebateApprovedView object.
     */
    public static TransRebateApprovedView createValue () 
    {
        TransRebateApprovedView valueView = new TransRebateApprovedView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this TransRebateApprovedView object
     */
    public String toString()
    {
        return "[" + "TransRebateApprovedId=" + mTransRebateApprovedId + ", RebateNumber=" + mRebateNumber + ", ConnectionStatus=" + mConnectionStatus + ", DistributorNumber=" + mDistributorNumber + ", ContractNumber=" + mContractNumber + ", ContractDesc=" + mContractDesc + ", ProductSku=" + mProductSku + ", ProductDesc=" + mProductDesc + ", Quantity=" + mQuantity + ", Uom=" + mUom + ", RbtAmountAdj=" + mRbtAmountAdj + ", RebateTypeCode=" + mRebateTypeCode + ", RebateDate=" + mRebateDate + ", SalesOrg=" + mSalesOrg + ", DistChannel=" + mDistChannel + ", Division=" + mDivision + ", Reason=" + mReason + ", UnitPrice=" + mUnitPrice + ", DistProductSku=" + mDistProductSku + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("TransRebateApproved");
	root.setAttribute("Id", String.valueOf(mTransRebateApprovedId));

	Element node;

        node = doc.createElement("RebateNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mRebateNumber)));
        root.appendChild(node);

        node = doc.createElement("ConnectionStatus");
        node.appendChild(doc.createTextNode(String.valueOf(mConnectionStatus)));
        root.appendChild(node);

        node = doc.createElement("DistributorNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorNumber)));
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

        node = doc.createElement("RbtAmountAdj");
        node.appendChild(doc.createTextNode(String.valueOf(mRbtAmountAdj)));
        root.appendChild(node);

        node = doc.createElement("RebateTypeCode");
        node.appendChild(doc.createTextNode(String.valueOf(mRebateTypeCode)));
        root.appendChild(node);

        node = doc.createElement("RebateDate");
        node.appendChild(doc.createTextNode(String.valueOf(mRebateDate)));
        root.appendChild(node);

        node = doc.createElement("SalesOrg");
        node.appendChild(doc.createTextNode(String.valueOf(mSalesOrg)));
        root.appendChild(node);

        node = doc.createElement("DistChannel");
        node.appendChild(doc.createTextNode(String.valueOf(mDistChannel)));
        root.appendChild(node);

        node = doc.createElement("Division");
        node.appendChild(doc.createTextNode(String.valueOf(mDivision)));
        root.appendChild(node);

        node = doc.createElement("Reason");
        node.appendChild(doc.createTextNode(String.valueOf(mReason)));
        root.appendChild(node);

        node = doc.createElement("UnitPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mUnitPrice)));
        root.appendChild(node);

        node = doc.createElement("DistProductSku");
        node.appendChild(doc.createTextNode(String.valueOf(mDistProductSku)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public TransRebateApprovedView copy()  {
      TransRebateApprovedView obj = new TransRebateApprovedView();
      obj.setTransRebateApprovedId(mTransRebateApprovedId);
      obj.setRebateNumber(mRebateNumber);
      obj.setConnectionStatus(mConnectionStatus);
      obj.setDistributorNumber(mDistributorNumber);
      obj.setContractNumber(mContractNumber);
      obj.setContractDesc(mContractDesc);
      obj.setProductSku(mProductSku);
      obj.setProductDesc(mProductDesc);
      obj.setQuantity(mQuantity);
      obj.setUom(mUom);
      obj.setRbtAmountAdj(mRbtAmountAdj);
      obj.setRebateTypeCode(mRebateTypeCode);
      obj.setRebateDate(mRebateDate);
      obj.setSalesOrg(mSalesOrg);
      obj.setDistChannel(mDistChannel);
      obj.setDivision(mDivision);
      obj.setReason(mReason);
      obj.setUnitPrice(mUnitPrice);
      obj.setDistProductSku(mDistProductSku);
      
      return obj;
    }

    
    /**
     * Sets the TransRebateApprovedId property.
     *
     * @param pTransRebateApprovedId
     *  int to use to update the property.
     */
    public void setTransRebateApprovedId(int pTransRebateApprovedId){
        this.mTransRebateApprovedId = pTransRebateApprovedId;
    }
    /**
     * Retrieves the TransRebateApprovedId property.
     *
     * @return
     *  int containing the TransRebateApprovedId property.
     */
    public int getTransRebateApprovedId(){
        return mTransRebateApprovedId;
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
     * Sets the DistributorNumber property.
     *
     * @param pDistributorNumber
     *  String to use to update the property.
     */
    public void setDistributorNumber(String pDistributorNumber){
        this.mDistributorNumber = pDistributorNumber;
    }
    /**
     * Retrieves the DistributorNumber property.
     *
     * @return
     *  String containing the DistributorNumber property.
     */
    public String getDistributorNumber(){
        return mDistributorNumber;
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
     * Sets the RbtAmountAdj property.
     *
     * @param pRbtAmountAdj
     *  java.math.BigDecimal to use to update the property.
     */
    public void setRbtAmountAdj(java.math.BigDecimal pRbtAmountAdj){
        this.mRbtAmountAdj = pRbtAmountAdj;
    }
    /**
     * Retrieves the RbtAmountAdj property.
     *
     * @return
     *  java.math.BigDecimal containing the RbtAmountAdj property.
     */
    public java.math.BigDecimal getRbtAmountAdj(){
        return mRbtAmountAdj;
    }


    /**
     * Sets the RebateTypeCode property.
     *
     * @param pRebateTypeCode
     *  String to use to update the property.
     */
    public void setRebateTypeCode(String pRebateTypeCode){
        this.mRebateTypeCode = pRebateTypeCode;
    }
    /**
     * Retrieves the RebateTypeCode property.
     *
     * @return
     *  String containing the RebateTypeCode property.
     */
    public String getRebateTypeCode(){
        return mRebateTypeCode;
    }


    /**
     * Sets the RebateDate property.
     *
     * @param pRebateDate
     *  java.util.Date to use to update the property.
     */
    public void setRebateDate(java.util.Date pRebateDate){
        this.mRebateDate = pRebateDate;
    }
    /**
     * Retrieves the RebateDate property.
     *
     * @return
     *  java.util.Date containing the RebateDate property.
     */
    public java.util.Date getRebateDate(){
        return mRebateDate;
    }


    /**
     * Sets the SalesOrg property.
     *
     * @param pSalesOrg
     *  String to use to update the property.
     */
    public void setSalesOrg(String pSalesOrg){
        this.mSalesOrg = pSalesOrg;
    }
    /**
     * Retrieves the SalesOrg property.
     *
     * @return
     *  String containing the SalesOrg property.
     */
    public String getSalesOrg(){
        return mSalesOrg;
    }


    /**
     * Sets the DistChannel property.
     *
     * @param pDistChannel
     *  String to use to update the property.
     */
    public void setDistChannel(String pDistChannel){
        this.mDistChannel = pDistChannel;
    }
    /**
     * Retrieves the DistChannel property.
     *
     * @return
     *  String containing the DistChannel property.
     */
    public String getDistChannel(){
        return mDistChannel;
    }


    /**
     * Sets the Division property.
     *
     * @param pDivision
     *  String to use to update the property.
     */
    public void setDivision(String pDivision){
        this.mDivision = pDivision;
    }
    /**
     * Retrieves the Division property.
     *
     * @return
     *  String containing the Division property.
     */
    public String getDivision(){
        return mDivision;
    }


    /**
     * Sets the Reason property.
     *
     * @param pReason
     *  String to use to update the property.
     */
    public void setReason(String pReason){
        this.mReason = pReason;
    }
    /**
     * Retrieves the Reason property.
     *
     * @return
     *  String containing the Reason property.
     */
    public String getReason(){
        return mReason;
    }


    /**
     * Sets the UnitPrice property.
     *
     * @param pUnitPrice
     *  java.math.BigDecimal to use to update the property.
     */
    public void setUnitPrice(java.math.BigDecimal pUnitPrice){
        this.mUnitPrice = pUnitPrice;
    }
    /**
     * Retrieves the UnitPrice property.
     *
     * @return
     *  java.math.BigDecimal containing the UnitPrice property.
     */
    public java.math.BigDecimal getUnitPrice(){
        return mUnitPrice;
    }


    /**
     * Sets the DistProductSku property.
     *
     * @param pDistProductSku
     *  String to use to update the property.
     */
    public void setDistProductSku(String pDistProductSku){
        this.mDistProductSku = pDistProductSku;
    }
    /**
     * Retrieves the DistProductSku property.
     *
     * @return
     *  String containing the DistProductSku property.
     */
    public String getDistProductSku(){
        return mDistProductSku;
    }


    
}
