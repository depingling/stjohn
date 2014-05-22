
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        SiteInventoryInfoView
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
 * <code>SiteInventoryInfoView</code> is a ViewObject class for UI.
 */
public class SiteInventoryInfoView
extends ValueObject
{
   
    private static final long serialVersionUID = -5713844515447227947L;
    private int mSiteId;
    private int mItemId;
    private int mItemSku;
    private String mItemDesc;
    private String mItemUom;
    private String mItemPack;
    private int mParValue;
    private int mSumOfAllParValues;
    private String mQtyOnHand;
    private String mUpdateUser;
    private String mAutoOrderItem;
    private ProductData mProductData;
    private String mOrderQty;

    /**
     * Constructor.
     */
    public SiteInventoryInfoView ()
    {
        mItemDesc = "";
        mItemUom = "";
        mItemPack = "";
        mQtyOnHand = "";
        mUpdateUser = "";
        mAutoOrderItem = "";
        mOrderQty = "";
    }

    /**
     * Constructor. 
     */
    public SiteInventoryInfoView(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, int parm7, int parm8, String parm9, String parm10, String parm11, ProductData parm12, String parm13)
    {
        mSiteId = parm1;
        mItemId = parm2;
        mItemSku = parm3;
        mItemDesc = parm4;
        mItemUom = parm5;
        mItemPack = parm6;
        mParValue = parm7;
        mSumOfAllParValues = parm8;
        mQtyOnHand = parm9;
        mUpdateUser = parm10;
        mAutoOrderItem = parm11;
        mProductData = parm12;
        mOrderQty = parm13;
        
    }

    /**
     * Creates a new SiteInventoryInfoView
     *
     * @return
     *  Newly initialized SiteInventoryInfoView object.
     */
    public static SiteInventoryInfoView createValue () 
    {
        SiteInventoryInfoView valueView = new SiteInventoryInfoView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this SiteInventoryInfoView object
     */
    public String toString()
    {
        return "[" + "SiteId=" + mSiteId + ", ItemId=" + mItemId + ", ItemSku=" + mItemSku + ", ItemDesc=" + mItemDesc + ", ItemUom=" + mItemUom + ", ItemPack=" + mItemPack + ", ParValue=" + mParValue + ", SumOfAllParValues=" + mSumOfAllParValues + ", QtyOnHand=" + mQtyOnHand + ", UpdateUser=" + mUpdateUser + ", AutoOrderItem=" + mAutoOrderItem + ", ProductData=" + mProductData + ", OrderQty=" + mOrderQty + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("SiteInventoryInfo");
	root.setAttribute("Id", String.valueOf(mSiteId));

	Element node;

        node = doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node = doc.createElement("ItemSku");
        node.appendChild(doc.createTextNode(String.valueOf(mItemSku)));
        root.appendChild(node);

        node = doc.createElement("ItemDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mItemDesc)));
        root.appendChild(node);

        node = doc.createElement("ItemUom");
        node.appendChild(doc.createTextNode(String.valueOf(mItemUom)));
        root.appendChild(node);

        node = doc.createElement("ItemPack");
        node.appendChild(doc.createTextNode(String.valueOf(mItemPack)));
        root.appendChild(node);

        node = doc.createElement("ParValue");
        node.appendChild(doc.createTextNode(String.valueOf(mParValue)));
        root.appendChild(node);

        node = doc.createElement("SumOfAllParValues");
        node.appendChild(doc.createTextNode(String.valueOf(mSumOfAllParValues)));
        root.appendChild(node);

        node = doc.createElement("QtyOnHand");
        node.appendChild(doc.createTextNode(String.valueOf(mQtyOnHand)));
        root.appendChild(node);

        node = doc.createElement("UpdateUser");
        node.appendChild(doc.createTextNode(String.valueOf(mUpdateUser)));
        root.appendChild(node);

        node = doc.createElement("AutoOrderItem");
        node.appendChild(doc.createTextNode(String.valueOf(mAutoOrderItem)));
        root.appendChild(node);

        node = doc.createElement("ProductData");
        node.appendChild(doc.createTextNode(String.valueOf(mProductData)));
        root.appendChild(node);

        node = doc.createElement("OrderQty");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderQty)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public SiteInventoryInfoView copy()  {
      SiteInventoryInfoView obj = new SiteInventoryInfoView();
      obj.setSiteId(mSiteId);
      obj.setItemId(mItemId);
      obj.setItemSku(mItemSku);
      obj.setItemDesc(mItemDesc);
      obj.setItemUom(mItemUom);
      obj.setItemPack(mItemPack);
      obj.setParValue(mParValue);
      obj.setSumOfAllParValues(mSumOfAllParValues);
      obj.setQtyOnHand(mQtyOnHand);
      obj.setUpdateUser(mUpdateUser);
      obj.setAutoOrderItem(mAutoOrderItem);
      obj.setProductData(mProductData);
      obj.setOrderQty(mOrderQty);
      
      return obj;
    }

    
    /**
     * Sets the SiteId property.
     *
     * @param pSiteId
     *  int to use to update the property.
     */
    public void setSiteId(int pSiteId){
        this.mSiteId = pSiteId;
    }
    /**
     * Retrieves the SiteId property.
     *
     * @return
     *  int containing the SiteId property.
     */
    public int getSiteId(){
        return mSiteId;
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
     * Sets the ItemSku property.
     *
     * @param pItemSku
     *  int to use to update the property.
     */
    public void setItemSku(int pItemSku){
        this.mItemSku = pItemSku;
    }
    /**
     * Retrieves the ItemSku property.
     *
     * @return
     *  int containing the ItemSku property.
     */
    public int getItemSku(){
        return mItemSku;
    }


    /**
     * Sets the ItemDesc property.
     *
     * @param pItemDesc
     *  String to use to update the property.
     */
    public void setItemDesc(String pItemDesc){
        this.mItemDesc = pItemDesc;
    }
    /**
     * Retrieves the ItemDesc property.
     *
     * @return
     *  String containing the ItemDesc property.
     */
    public String getItemDesc(){
        return mItemDesc;
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
     * Sets the ItemPack property.
     *
     * @param pItemPack
     *  String to use to update the property.
     */
    public void setItemPack(String pItemPack){
        this.mItemPack = pItemPack;
    }
    /**
     * Retrieves the ItemPack property.
     *
     * @return
     *  String containing the ItemPack property.
     */
    public String getItemPack(){
        return mItemPack;
    }


    /**
     * Sets the ParValue property.
     *
     * @param pParValue
     *  int to use to update the property.
     */
    public void setParValue(int pParValue){
        this.mParValue = pParValue;
    }
    /**
     * Retrieves the ParValue property.
     *
     * @return
     *  int containing the ParValue property.
     */
    public int getParValue(){
        return mParValue;
    }


    /**
     * Sets the SumOfAllParValues property.
     *
     * @param pSumOfAllParValues
     *  int to use to update the property.
     */
    public void setSumOfAllParValues(int pSumOfAllParValues){
        this.mSumOfAllParValues = pSumOfAllParValues;
    }
    /**
     * Retrieves the SumOfAllParValues property.
     *
     * @return
     *  int containing the SumOfAllParValues property.
     */
    public int getSumOfAllParValues(){
        return mSumOfAllParValues;
    }


    /**
     * Sets the QtyOnHand property.
     *
     * @param pQtyOnHand
     *  String to use to update the property.
     */
    public void setQtyOnHand(String pQtyOnHand){
        this.mQtyOnHand = pQtyOnHand;
    }
    /**
     * Retrieves the QtyOnHand property.
     *
     * @return
     *  String containing the QtyOnHand property.
     */
    public String getQtyOnHand(){
        return mQtyOnHand;
    }


    /**
     * Sets the UpdateUser property.
     *
     * @param pUpdateUser
     *  String to use to update the property.
     */
    public void setUpdateUser(String pUpdateUser){
        this.mUpdateUser = pUpdateUser;
    }
    /**
     * Retrieves the UpdateUser property.
     *
     * @return
     *  String containing the UpdateUser property.
     */
    public String getUpdateUser(){
        return mUpdateUser;
    }


    /**
     * Sets the AutoOrderItem property.
     *
     * @param pAutoOrderItem
     *  String to use to update the property.
     */
    public void setAutoOrderItem(String pAutoOrderItem){
        this.mAutoOrderItem = pAutoOrderItem;
    }
    /**
     * Retrieves the AutoOrderItem property.
     *
     * @return
     *  String containing the AutoOrderItem property.
     */
    public String getAutoOrderItem(){
        return mAutoOrderItem;
    }


    /**
     * Sets the ProductData property.
     *
     * @param pProductData
     *  ProductData to use to update the property.
     */
    public void setProductData(ProductData pProductData){
        this.mProductData = pProductData;
    }
    /**
     * Retrieves the ProductData property.
     *
     * @return
     *  ProductData containing the ProductData property.
     */
    public ProductData getProductData(){
        return mProductData;
    }


    /**
     * Sets the OrderQty property.
     *
     * @param pOrderQty
     *  String to use to update the property.
     */
    public void setOrderQty(String pOrderQty){
        this.mOrderQty = pOrderQty;
    }
    /**
     * Retrieves the OrderQty property.
     *
     * @return
     *  String containing the OrderQty property.
     */
    public String getOrderQty(){
        return mOrderQty;
    }


    
}
