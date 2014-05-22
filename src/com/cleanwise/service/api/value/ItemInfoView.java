
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ItemInfoView
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
 * <code>ItemInfoView</code> is a ViewObject class for UI.
 */
public class ItemInfoView
extends ValueObject
{
   
    private static final long serialVersionUID = -251960133725875155L;
    private int mItemId;
    private int mOrderItemId;
    private int mPoLineNum;
    private String mSkuNum;
    private String mCustSkuNum;
    private String mItemName;
    private String mItemSize;
    private String mUom;
    private String mPack;
    private BigDecimal mCustCost;
    private BigDecimal mCost;
    private BigDecimal mQty;
    private String mManufacturer;
    private BigDecimal mServiceFee;

    /**
     * Constructor.
     */
    public ItemInfoView ()
    {
        mSkuNum = "";
        mCustSkuNum = "";
        mItemName = "";
        mItemSize = "";
        mUom = "";
        mPack = "";
        mManufacturer = "";
    }

    /**
     * Constructor. 
     */
    public ItemInfoView(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, BigDecimal parm10, BigDecimal parm11, BigDecimal parm12, String parm13, BigDecimal parm14)
    {
        mItemId = parm1;
        mOrderItemId = parm2;
        mPoLineNum = parm3;
        mSkuNum = parm4;
        mCustSkuNum = parm5;
        mItemName = parm6;
        mItemSize = parm7;
        mUom = parm8;
        mPack = parm9;
        mCustCost = parm10;
        mCost = parm11;
        mQty = parm12;
        mManufacturer = parm13;
        mServiceFee = parm14;
        
    }

    /**
     * Creates a new ItemInfoView
     *
     * @return
     *  Newly initialized ItemInfoView object.
     */
    public static ItemInfoView createValue () 
    {
        ItemInfoView valueView = new ItemInfoView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ItemInfoView object
     */
    public String toString()
    {
        return "[" + "ItemId=" + mItemId + ", OrderItemId=" + mOrderItemId + ", PoLineNum=" + mPoLineNum + ", SkuNum=" + mSkuNum + ", CustSkuNum=" + mCustSkuNum + ", ItemName=" + mItemName + ", ItemSize=" + mItemSize + ", Uom=" + mUom + ", Pack=" + mPack + ", CustCost=" + mCustCost + ", Cost=" + mCost + ", Qty=" + mQty + ", Manufacturer=" + mManufacturer + ", ServiceFee=" + mServiceFee + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ItemInfo");
	root.setAttribute("Id", String.valueOf(mItemId));

	Element node;

        node = doc.createElement("OrderItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderItemId)));
        root.appendChild(node);

        node = doc.createElement("PoLineNum");
        node.appendChild(doc.createTextNode(String.valueOf(mPoLineNum)));
        root.appendChild(node);

        node = doc.createElement("SkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSkuNum)));
        root.appendChild(node);

        node = doc.createElement("CustSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mCustSkuNum)));
        root.appendChild(node);

        node = doc.createElement("ItemName");
        node.appendChild(doc.createTextNode(String.valueOf(mItemName)));
        root.appendChild(node);

        node = doc.createElement("ItemSize");
        node.appendChild(doc.createTextNode(String.valueOf(mItemSize)));
        root.appendChild(node);

        node = doc.createElement("Uom");
        node.appendChild(doc.createTextNode(String.valueOf(mUom)));
        root.appendChild(node);

        node = doc.createElement("Pack");
        node.appendChild(doc.createTextNode(String.valueOf(mPack)));
        root.appendChild(node);

        node = doc.createElement("CustCost");
        node.appendChild(doc.createTextNode(String.valueOf(mCustCost)));
        root.appendChild(node);

        node = doc.createElement("Cost");
        node.appendChild(doc.createTextNode(String.valueOf(mCost)));
        root.appendChild(node);

        node = doc.createElement("Qty");
        node.appendChild(doc.createTextNode(String.valueOf(mQty)));
        root.appendChild(node);

        node = doc.createElement("Manufacturer");
        node.appendChild(doc.createTextNode(String.valueOf(mManufacturer)));
        root.appendChild(node);

        node = doc.createElement("ServiceFee");
        node.appendChild(doc.createTextNode(String.valueOf(mServiceFee)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ItemInfoView copy()  {
      ItemInfoView obj = new ItemInfoView();
      obj.setItemId(mItemId);
      obj.setOrderItemId(mOrderItemId);
      obj.setPoLineNum(mPoLineNum);
      obj.setSkuNum(mSkuNum);
      obj.setCustSkuNum(mCustSkuNum);
      obj.setItemName(mItemName);
      obj.setItemSize(mItemSize);
      obj.setUom(mUom);
      obj.setPack(mPack);
      obj.setCustCost(mCustCost);
      obj.setCost(mCost);
      obj.setQty(mQty);
      obj.setManufacturer(mManufacturer);
      obj.setServiceFee(mServiceFee);
      
      return obj;
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
     * Sets the PoLineNum property.
     *
     * @param pPoLineNum
     *  int to use to update the property.
     */
    public void setPoLineNum(int pPoLineNum){
        this.mPoLineNum = pPoLineNum;
    }
    /**
     * Retrieves the PoLineNum property.
     *
     * @return
     *  int containing the PoLineNum property.
     */
    public int getPoLineNum(){
        return mPoLineNum;
    }


    /**
     * Sets the SkuNum property.
     *
     * @param pSkuNum
     *  String to use to update the property.
     */
    public void setSkuNum(String pSkuNum){
        this.mSkuNum = pSkuNum;
    }
    /**
     * Retrieves the SkuNum property.
     *
     * @return
     *  String containing the SkuNum property.
     */
    public String getSkuNum(){
        return mSkuNum;
    }


    /**
     * Sets the CustSkuNum property.
     *
     * @param pCustSkuNum
     *  String to use to update the property.
     */
    public void setCustSkuNum(String pCustSkuNum){
        this.mCustSkuNum = pCustSkuNum;
    }
    /**
     * Retrieves the CustSkuNum property.
     *
     * @return
     *  String containing the CustSkuNum property.
     */
    public String getCustSkuNum(){
        return mCustSkuNum;
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
     * Sets the ItemSize property.
     *
     * @param pItemSize
     *  String to use to update the property.
     */
    public void setItemSize(String pItemSize){
        this.mItemSize = pItemSize;
    }
    /**
     * Retrieves the ItemSize property.
     *
     * @return
     *  String containing the ItemSize property.
     */
    public String getItemSize(){
        return mItemSize;
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
     * Sets the CustCost property.
     *
     * @param pCustCost
     *  BigDecimal to use to update the property.
     */
    public void setCustCost(BigDecimal pCustCost){
        this.mCustCost = pCustCost;
    }
    /**
     * Retrieves the CustCost property.
     *
     * @return
     *  BigDecimal containing the CustCost property.
     */
    public BigDecimal getCustCost(){
        return mCustCost;
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
     * Sets the Qty property.
     *
     * @param pQty
     *  BigDecimal to use to update the property.
     */
    public void setQty(BigDecimal pQty){
        this.mQty = pQty;
    }
    /**
     * Retrieves the Qty property.
     *
     * @return
     *  BigDecimal containing the Qty property.
     */
    public BigDecimal getQty(){
        return mQty;
    }


    /**
     * Sets the Manufacturer property.
     *
     * @param pManufacturer
     *  String to use to update the property.
     */
    public void setManufacturer(String pManufacturer){
        this.mManufacturer = pManufacturer;
    }
    /**
     * Retrieves the Manufacturer property.
     *
     * @return
     *  String containing the Manufacturer property.
     */
    public String getManufacturer(){
        return mManufacturer;
    }


    /**
     * Sets the ServiceFee property.
     *
     * @param pServiceFee
     *  BigDecimal to use to update the property.
     */
    public void setServiceFee(BigDecimal pServiceFee){
        this.mServiceFee = pServiceFee;
    }
    /**
     * Retrieves the ServiceFee property.
     *
     * @return
     *  BigDecimal containing the ServiceFee property.
     */
    public BigDecimal getServiceFee(){
        return mServiceFee;
    }


    
}
