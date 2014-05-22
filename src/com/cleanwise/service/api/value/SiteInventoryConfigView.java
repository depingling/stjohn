
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        SiteInventoryConfigView
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
 * <code>SiteInventoryConfigView</code> is a ViewObject class for UI.
 */
public class SiteInventoryConfigView
extends ValueObject
{
   
    private static final long serialVersionUID = 8248725930463368140L;
    private int mSiteId;
    private int mItemId;
    private int mItemSku;
    private String mActualSku;
    private String mItemDesc;
    private String mItemUom;
    private String mItemPack;
    private HashMap mParValues;
    private String mQtyOnHand;
    private String mOrderQty;
    private int mSumOfAllParValues;
    private Date mModDate;
    private String mModBy;
    private String mAutoOrderItem;
    private ProductData mProductData;
    private String mInitialQtyOnHand;

    /**
     * Constructor.
     */
    public SiteInventoryConfigView ()
    {
        mActualSku = "";
        mItemDesc = "";
        mItemUom = "";
        mItemPack = "";
        mQtyOnHand = "";
        mOrderQty = "";
        mModBy = "";
        mAutoOrderItem = "";
        mInitialQtyOnHand = "";
    }

    /**
     * Constructor. 
     */
    public SiteInventoryConfigView(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, String parm7, HashMap parm8, String parm9, String parm10, int parm11, Date parm12, String parm13, String parm14, ProductData parm15, String parm16)
    {
        mSiteId = parm1;
        mItemId = parm2;
        mItemSku = parm3;
        mActualSku = parm4;
        mItemDesc = parm5;
        mItemUom = parm6;
        mItemPack = parm7;
        mParValues = parm8;
        mQtyOnHand = parm9;
        mOrderQty = parm10;
        mSumOfAllParValues = parm11;
        mModDate = parm12;
        mModBy = parm13;
        mAutoOrderItem = parm14;
        mProductData = parm15;
        mInitialQtyOnHand = parm16;
        
    }

    /**
     * Creates a new SiteInventoryConfigView
     *
     * @return
     *  Newly initialized SiteInventoryConfigView object.
     */
    public static SiteInventoryConfigView createValue () 
    {
        SiteInventoryConfigView valueView = new SiteInventoryConfigView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this SiteInventoryConfigView object
     */
    public String toString()
    {
        return "[" + "SiteId=" + mSiteId + ", ItemId=" + mItemId + ", ItemSku=" + mItemSku + ", ActualSku=" + mActualSku + ", ItemDesc=" + mItemDesc + ", ItemUom=" + mItemUom + ", ItemPack=" + mItemPack + ", ParValues=" + mParValues + ", QtyOnHand=" + mQtyOnHand + ", OrderQty=" + mOrderQty + ", SumOfAllParValues=" + mSumOfAllParValues + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", AutoOrderItem=" + mAutoOrderItem + ", ProductData=" + mProductData + ", InitialQtyOnHand=" + mInitialQtyOnHand + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("SiteInventoryConfig");
	root.setAttribute("Id", String.valueOf(mSiteId));

	Element node;

        node = doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node = doc.createElement("ItemSku");
        node.appendChild(doc.createTextNode(String.valueOf(mItemSku)));
        root.appendChild(node);

        node = doc.createElement("ActualSku");
        node.appendChild(doc.createTextNode(String.valueOf(mActualSku)));
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

        node = doc.createElement("ParValues");
        node.appendChild(doc.createTextNode(String.valueOf(mParValues)));
        root.appendChild(node);

        node = doc.createElement("QtyOnHand");
        node.appendChild(doc.createTextNode(String.valueOf(mQtyOnHand)));
        root.appendChild(node);

        node = doc.createElement("OrderQty");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderQty)));
        root.appendChild(node);

        node = doc.createElement("SumOfAllParValues");
        node.appendChild(doc.createTextNode(String.valueOf(mSumOfAllParValues)));
        root.appendChild(node);

        node = doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        node = doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node = doc.createElement("AutoOrderItem");
        node.appendChild(doc.createTextNode(String.valueOf(mAutoOrderItem)));
        root.appendChild(node);

        node = doc.createElement("ProductData");
        node.appendChild(doc.createTextNode(String.valueOf(mProductData)));
        root.appendChild(node);

        node = doc.createElement("InitialQtyOnHand");
        node.appendChild(doc.createTextNode(String.valueOf(mInitialQtyOnHand)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public SiteInventoryConfigView copy()  {
      SiteInventoryConfigView obj = new SiteInventoryConfigView();
      obj.setSiteId(mSiteId);
      obj.setItemId(mItemId);
      obj.setItemSku(mItemSku);
      obj.setActualSku(mActualSku);
      obj.setItemDesc(mItemDesc);
      obj.setItemUom(mItemUom);
      obj.setItemPack(mItemPack);
      obj.setParValues(mParValues);
      obj.setQtyOnHand(mQtyOnHand);
      obj.setOrderQty(mOrderQty);
      obj.setSumOfAllParValues(mSumOfAllParValues);
      obj.setModDate(mModDate);
      obj.setModBy(mModBy);
      obj.setAutoOrderItem(mAutoOrderItem);
      obj.setProductData(mProductData);
      obj.setInitialQtyOnHand(mInitialQtyOnHand);
      
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
     * Sets the ActualSku property.
     *
     * @param pActualSku
     *  String to use to update the property.
     */
    public void setActualSku(String pActualSku){
        this.mActualSku = pActualSku;
    }
    /**
     * Retrieves the ActualSku property.
     *
     * @return
     *  String containing the ActualSku property.
     */
    public String getActualSku(){
        return mActualSku;
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
     * Sets the ParValues property.
     *
     * @param pParValues
     *  HashMap to use to update the property.
     */
    public void setParValues(HashMap pParValues){
        this.mParValues = pParValues;
    }
    /**
     * Retrieves the ParValues property.
     *
     * @return
     *  HashMap containing the ParValues property.
     */
    public HashMap getParValues(){
        return mParValues;
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
     * Sets the ModDate property.
     *
     * @param pModDate
     *  Date to use to update the property.
     */
    public void setModDate(Date pModDate){
        this.mModDate = pModDate;
    }
    /**
     * Retrieves the ModDate property.
     *
     * @return
     *  Date containing the ModDate property.
     */
    public Date getModDate(){
        return mModDate;
    }


    /**
     * Sets the ModBy property.
     *
     * @param pModBy
     *  String to use to update the property.
     */
    public void setModBy(String pModBy){
        this.mModBy = pModBy;
    }
    /**
     * Retrieves the ModBy property.
     *
     * @return
     *  String containing the ModBy property.
     */
    public String getModBy(){
        return mModBy;
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
     * Sets the InitialQtyOnHand property.
     *
     * @param pInitialQtyOnHand
     *  String to use to update the property.
     */
    public void setInitialQtyOnHand(String pInitialQtyOnHand){
        this.mInitialQtyOnHand = pInitialQtyOnHand;
    }
    /**
     * Retrieves the InitialQtyOnHand property.
     *
     * @return
     *  String containing the InitialQtyOnHand property.
     */
    public String getInitialQtyOnHand(){
        return mInitialQtyOnHand;
    }


    
}
