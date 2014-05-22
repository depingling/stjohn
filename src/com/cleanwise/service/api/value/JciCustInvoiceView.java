
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        JciCustInvoiceView
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
 * <code>JciCustInvoiceView</code> is a ViewObject class for UI.
 */
public class JciCustInvoiceView
extends ValueObject
{
   
    private static final long serialVersionUID = 2307773785034670287L;
    private int mStoreId;
    private String mStoreName;
    private String mInvoiceNum;
    private String mInvoiceType;
    private Date mInvoiceDate;
    private String mCompany;
    private String mLocation;
    private String mWoNumber;
    private String mExpenseType;
    private String mCompWoFlag;
    private Date mCompWoDate;
    private String mServiceType;
    private int mLineNum;
    private BigDecimal mLineAmount;
    private int mQty;
    private String mItemName;
    private String mItemSku;
    private BigDecimal mInvoiceAmount;
    private BigDecimal mTax;
    private BigDecimal mFreight;

    /**
     * Constructor.
     */
    public JciCustInvoiceView ()
    {
        mStoreName = "";
        mInvoiceNum = "";
        mInvoiceType = "";
        mCompany = "";
        mLocation = "";
        mWoNumber = "";
        mExpenseType = "";
        mCompWoFlag = "";
        mServiceType = "";
        mItemName = "";
        mItemSku = "";
    }

    /**
     * Constructor. 
     */
    public JciCustInvoiceView(int parm1, String parm2, String parm3, String parm4, Date parm5, String parm6, String parm7, String parm8, String parm9, String parm10, Date parm11, String parm12, int parm13, BigDecimal parm14, int parm15, String parm16, String parm17, BigDecimal parm18, BigDecimal parm19, BigDecimal parm20)
    {
        mStoreId = parm1;
        mStoreName = parm2;
        mInvoiceNum = parm3;
        mInvoiceType = parm4;
        mInvoiceDate = parm5;
        mCompany = parm6;
        mLocation = parm7;
        mWoNumber = parm8;
        mExpenseType = parm9;
        mCompWoFlag = parm10;
        mCompWoDate = parm11;
        mServiceType = parm12;
        mLineNum = parm13;
        mLineAmount = parm14;
        mQty = parm15;
        mItemName = parm16;
        mItemSku = parm17;
        mInvoiceAmount = parm18;
        mTax = parm19;
        mFreight = parm20;
        
    }

    /**
     * Creates a new JciCustInvoiceView
     *
     * @return
     *  Newly initialized JciCustInvoiceView object.
     */
    public static JciCustInvoiceView createValue () 
    {
        JciCustInvoiceView valueView = new JciCustInvoiceView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this JciCustInvoiceView object
     */
    public String toString()
    {
        return "[" + "StoreId=" + mStoreId + ", StoreName=" + mStoreName + ", InvoiceNum=" + mInvoiceNum + ", InvoiceType=" + mInvoiceType + ", InvoiceDate=" + mInvoiceDate + ", Company=" + mCompany + ", Location=" + mLocation + ", WoNumber=" + mWoNumber + ", ExpenseType=" + mExpenseType + ", CompWoFlag=" + mCompWoFlag + ", CompWoDate=" + mCompWoDate + ", ServiceType=" + mServiceType + ", LineNum=" + mLineNum + ", LineAmount=" + mLineAmount + ", Qty=" + mQty + ", ItemName=" + mItemName + ", ItemSku=" + mItemSku + ", InvoiceAmount=" + mInvoiceAmount + ", Tax=" + mTax + ", Freight=" + mFreight + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("JciCustInvoice");
	root.setAttribute("Id", String.valueOf(mStoreId));

	Element node;

        node = doc.createElement("StoreName");
        node.appendChild(doc.createTextNode(String.valueOf(mStoreName)));
        root.appendChild(node);

        node = doc.createElement("InvoiceNum");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceNum)));
        root.appendChild(node);

        node = doc.createElement("InvoiceType");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceType)));
        root.appendChild(node);

        node = doc.createElement("InvoiceDate");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceDate)));
        root.appendChild(node);

        node = doc.createElement("Company");
        node.appendChild(doc.createTextNode(String.valueOf(mCompany)));
        root.appendChild(node);

        node = doc.createElement("Location");
        node.appendChild(doc.createTextNode(String.valueOf(mLocation)));
        root.appendChild(node);

        node = doc.createElement("WoNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mWoNumber)));
        root.appendChild(node);

        node = doc.createElement("ExpenseType");
        node.appendChild(doc.createTextNode(String.valueOf(mExpenseType)));
        root.appendChild(node);

        node = doc.createElement("CompWoFlag");
        node.appendChild(doc.createTextNode(String.valueOf(mCompWoFlag)));
        root.appendChild(node);

        node = doc.createElement("CompWoDate");
        node.appendChild(doc.createTextNode(String.valueOf(mCompWoDate)));
        root.appendChild(node);

        node = doc.createElement("ServiceType");
        node.appendChild(doc.createTextNode(String.valueOf(mServiceType)));
        root.appendChild(node);

        node = doc.createElement("LineNum");
        node.appendChild(doc.createTextNode(String.valueOf(mLineNum)));
        root.appendChild(node);

        node = doc.createElement("LineAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mLineAmount)));
        root.appendChild(node);

        node = doc.createElement("Qty");
        node.appendChild(doc.createTextNode(String.valueOf(mQty)));
        root.appendChild(node);

        node = doc.createElement("ItemName");
        node.appendChild(doc.createTextNode(String.valueOf(mItemName)));
        root.appendChild(node);

        node = doc.createElement("ItemSku");
        node.appendChild(doc.createTextNode(String.valueOf(mItemSku)));
        root.appendChild(node);

        node = doc.createElement("InvoiceAmount");
        node.appendChild(doc.createTextNode(String.valueOf(mInvoiceAmount)));
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
    public JciCustInvoiceView copy()  {
      JciCustInvoiceView obj = new JciCustInvoiceView();
      obj.setStoreId(mStoreId);
      obj.setStoreName(mStoreName);
      obj.setInvoiceNum(mInvoiceNum);
      obj.setInvoiceType(mInvoiceType);
      obj.setInvoiceDate(mInvoiceDate);
      obj.setCompany(mCompany);
      obj.setLocation(mLocation);
      obj.setWoNumber(mWoNumber);
      obj.setExpenseType(mExpenseType);
      obj.setCompWoFlag(mCompWoFlag);
      obj.setCompWoDate(mCompWoDate);
      obj.setServiceType(mServiceType);
      obj.setLineNum(mLineNum);
      obj.setLineAmount(mLineAmount);
      obj.setQty(mQty);
      obj.setItemName(mItemName);
      obj.setItemSku(mItemSku);
      obj.setInvoiceAmount(mInvoiceAmount);
      obj.setTax(mTax);
      obj.setFreight(mFreight);
      
      return obj;
    }

    
    /**
     * Sets the StoreId property.
     *
     * @param pStoreId
     *  int to use to update the property.
     */
    public void setStoreId(int pStoreId){
        this.mStoreId = pStoreId;
    }
    /**
     * Retrieves the StoreId property.
     *
     * @return
     *  int containing the StoreId property.
     */
    public int getStoreId(){
        return mStoreId;
    }


    /**
     * Sets the StoreName property.
     *
     * @param pStoreName
     *  String to use to update the property.
     */
    public void setStoreName(String pStoreName){
        this.mStoreName = pStoreName;
    }
    /**
     * Retrieves the StoreName property.
     *
     * @return
     *  String containing the StoreName property.
     */
    public String getStoreName(){
        return mStoreName;
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
     * Sets the InvoiceType property.
     *
     * @param pInvoiceType
     *  String to use to update the property.
     */
    public void setInvoiceType(String pInvoiceType){
        this.mInvoiceType = pInvoiceType;
    }
    /**
     * Retrieves the InvoiceType property.
     *
     * @return
     *  String containing the InvoiceType property.
     */
    public String getInvoiceType(){
        return mInvoiceType;
    }


    /**
     * Sets the InvoiceDate property.
     *
     * @param pInvoiceDate
     *  Date to use to update the property.
     */
    public void setInvoiceDate(Date pInvoiceDate){
        this.mInvoiceDate = pInvoiceDate;
    }
    /**
     * Retrieves the InvoiceDate property.
     *
     * @return
     *  Date containing the InvoiceDate property.
     */
    public Date getInvoiceDate(){
        return mInvoiceDate;
    }


    /**
     * Sets the Company property.
     *
     * @param pCompany
     *  String to use to update the property.
     */
    public void setCompany(String pCompany){
        this.mCompany = pCompany;
    }
    /**
     * Retrieves the Company property.
     *
     * @return
     *  String containing the Company property.
     */
    public String getCompany(){
        return mCompany;
    }


    /**
     * Sets the Location property.
     *
     * @param pLocation
     *  String to use to update the property.
     */
    public void setLocation(String pLocation){
        this.mLocation = pLocation;
    }
    /**
     * Retrieves the Location property.
     *
     * @return
     *  String containing the Location property.
     */
    public String getLocation(){
        return mLocation;
    }


    /**
     * Sets the WoNumber property.
     *
     * @param pWoNumber
     *  String to use to update the property.
     */
    public void setWoNumber(String pWoNumber){
        this.mWoNumber = pWoNumber;
    }
    /**
     * Retrieves the WoNumber property.
     *
     * @return
     *  String containing the WoNumber property.
     */
    public String getWoNumber(){
        return mWoNumber;
    }


    /**
     * Sets the ExpenseType property.
     *
     * @param pExpenseType
     *  String to use to update the property.
     */
    public void setExpenseType(String pExpenseType){
        this.mExpenseType = pExpenseType;
    }
    /**
     * Retrieves the ExpenseType property.
     *
     * @return
     *  String containing the ExpenseType property.
     */
    public String getExpenseType(){
        return mExpenseType;
    }


    /**
     * Sets the CompWoFlag property.
     *
     * @param pCompWoFlag
     *  String to use to update the property.
     */
    public void setCompWoFlag(String pCompWoFlag){
        this.mCompWoFlag = pCompWoFlag;
    }
    /**
     * Retrieves the CompWoFlag property.
     *
     * @return
     *  String containing the CompWoFlag property.
     */
    public String getCompWoFlag(){
        return mCompWoFlag;
    }


    /**
     * Sets the CompWoDate property.
     *
     * @param pCompWoDate
     *  Date to use to update the property.
     */
    public void setCompWoDate(Date pCompWoDate){
        this.mCompWoDate = pCompWoDate;
    }
    /**
     * Retrieves the CompWoDate property.
     *
     * @return
     *  Date containing the CompWoDate property.
     */
    public Date getCompWoDate(){
        return mCompWoDate;
    }


    /**
     * Sets the ServiceType property.
     *
     * @param pServiceType
     *  String to use to update the property.
     */
    public void setServiceType(String pServiceType){
        this.mServiceType = pServiceType;
    }
    /**
     * Retrieves the ServiceType property.
     *
     * @return
     *  String containing the ServiceType property.
     */
    public String getServiceType(){
        return mServiceType;
    }


    /**
     * Sets the LineNum property.
     *
     * @param pLineNum
     *  int to use to update the property.
     */
    public void setLineNum(int pLineNum){
        this.mLineNum = pLineNum;
    }
    /**
     * Retrieves the LineNum property.
     *
     * @return
     *  int containing the LineNum property.
     */
    public int getLineNum(){
        return mLineNum;
    }


    /**
     * Sets the LineAmount property.
     *
     * @param pLineAmount
     *  BigDecimal to use to update the property.
     */
    public void setLineAmount(BigDecimal pLineAmount){
        this.mLineAmount = pLineAmount;
    }
    /**
     * Retrieves the LineAmount property.
     *
     * @return
     *  BigDecimal containing the LineAmount property.
     */
    public BigDecimal getLineAmount(){
        return mLineAmount;
    }


    /**
     * Sets the Qty property.
     *
     * @param pQty
     *  int to use to update the property.
     */
    public void setQty(int pQty){
        this.mQty = pQty;
    }
    /**
     * Retrieves the Qty property.
     *
     * @return
     *  int containing the Qty property.
     */
    public int getQty(){
        return mQty;
    }


    /**
     * Sets the ItemName property.
     *
     * @param pItemName
     *  String to use to update the property.
     */
    public void setItemName(String pItemName){
        this.mItemName = pItemName;
    }
    /**
     * Retrieves the ItemName property.
     *
     * @return
     *  String containing the ItemName property.
     */
    public String getItemName(){
        return mItemName;
    }


    /**
     * Sets the ItemSku property.
     *
     * @param pItemSku
     *  String to use to update the property.
     */
    public void setItemSku(String pItemSku){
        this.mItemSku = pItemSku;
    }
    /**
     * Retrieves the ItemSku property.
     *
     * @return
     *  String containing the ItemSku property.
     */
    public String getItemSku(){
        return mItemSku;
    }


    /**
     * Sets the InvoiceAmount property.
     *
     * @param pInvoiceAmount
     *  BigDecimal to use to update the property.
     */
    public void setInvoiceAmount(BigDecimal pInvoiceAmount){
        this.mInvoiceAmount = pInvoiceAmount;
    }
    /**
     * Retrieves the InvoiceAmount property.
     *
     * @return
     *  BigDecimal containing the InvoiceAmount property.
     */
    public BigDecimal getInvoiceAmount(){
        return mInvoiceAmount;
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
