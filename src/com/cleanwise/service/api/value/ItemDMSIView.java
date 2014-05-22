
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ItemDMSIView
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
 * <code>ItemDMSIView</code> is a ViewObject class for UI.
 */
public class ItemDMSIView
extends ValueObject
{
   
    private static final long serialVersionUID = -6695301648752772305L;
    private String mItemName;
    private String mManufacturerName;
    private String mManufacturerSku;
    private int mPack;
    private String mUOM;
    private String mColor;
    private int mCustomerReferenceCode;
    private String mDistributorName;
    private String mDistributorSku;
    private int mDistributorPack;
    private String mDistributorUOM;
    private String mPrice;
    private String mCategory;

    /**
     * Constructor.
     */
    public ItemDMSIView ()
    {
        mItemName = "";
        mManufacturerName = "";
        mManufacturerSku = "";
        mUOM = "";
        mColor = "";
        mDistributorName = "";
        mDistributorSku = "";
        mDistributorUOM = "";
        mPrice = "";
        mCategory = "";
    }

    /**
     * Constructor. 
     */
    public ItemDMSIView(String parm1, String parm2, String parm3, int parm4, String parm5, String parm6, int parm7, String parm8, String parm9, int parm10, String parm11, String parm12, String parm13)
    {
        mItemName = parm1;
        mManufacturerName = parm2;
        mManufacturerSku = parm3;
        mPack = parm4;
        mUOM = parm5;
        mColor = parm6;
        mCustomerReferenceCode = parm7;
        mDistributorName = parm8;
        mDistributorSku = parm9;
        mDistributorPack = parm10;
        mDistributorUOM = parm11;
        mPrice = parm12;
        mCategory = parm13;
        
    }

    /**
     * Creates a new ItemDMSIView
     *
     * @return
     *  Newly initialized ItemDMSIView object.
     */
    public static ItemDMSIView createValue () 
    {
        ItemDMSIView valueView = new ItemDMSIView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ItemDMSIView object
     */
    public String toString()
    {
        return "[" + "ItemName=" + mItemName + ", ManufacturerName=" + mManufacturerName + ", ManufacturerSku=" + mManufacturerSku + ", Pack=" + mPack + ", UOM=" + mUOM + ", Color=" + mColor + ", CustomerReferenceCode=" + mCustomerReferenceCode + ", DistributorName=" + mDistributorName + ", DistributorSku=" + mDistributorSku + ", DistributorPack=" + mDistributorPack + ", DistributorUOM=" + mDistributorUOM + ", Price=" + mPrice + ", Category=" + mCategory + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ItemDMSI");
	root.setAttribute("Id", String.valueOf(mItemName));

	Element node;

        node = doc.createElement("ManufacturerName");
        node.appendChild(doc.createTextNode(String.valueOf(mManufacturerName)));
        root.appendChild(node);

        node = doc.createElement("ManufacturerSku");
        node.appendChild(doc.createTextNode(String.valueOf(mManufacturerSku)));
        root.appendChild(node);

        node = doc.createElement("Pack");
        node.appendChild(doc.createTextNode(String.valueOf(mPack)));
        root.appendChild(node);

        node = doc.createElement("UOM");
        node.appendChild(doc.createTextNode(String.valueOf(mUOM)));
        root.appendChild(node);

        node = doc.createElement("Color");
        node.appendChild(doc.createTextNode(String.valueOf(mColor)));
        root.appendChild(node);

        node = doc.createElement("CustomerReferenceCode");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerReferenceCode)));
        root.appendChild(node);

        node = doc.createElement("DistributorName");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorName)));
        root.appendChild(node);

        node = doc.createElement("DistributorSku");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorSku)));
        root.appendChild(node);

        node = doc.createElement("DistributorPack");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorPack)));
        root.appendChild(node);

        node = doc.createElement("DistributorUOM");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributorUOM)));
        root.appendChild(node);

        node = doc.createElement("Price");
        node.appendChild(doc.createTextNode(String.valueOf(mPrice)));
        root.appendChild(node);

        node = doc.createElement("Category");
        node.appendChild(doc.createTextNode(String.valueOf(mCategory)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ItemDMSIView copy()  {
      ItemDMSIView obj = new ItemDMSIView();
      obj.setItemName(mItemName);
      obj.setManufacturerName(mManufacturerName);
      obj.setManufacturerSku(mManufacturerSku);
      obj.setPack(mPack);
      obj.setUOM(mUOM);
      obj.setColor(mColor);
      obj.setCustomerReferenceCode(mCustomerReferenceCode);
      obj.setDistributorName(mDistributorName);
      obj.setDistributorSku(mDistributorSku);
      obj.setDistributorPack(mDistributorPack);
      obj.setDistributorUOM(mDistributorUOM);
      obj.setPrice(mPrice);
      obj.setCategory(mCategory);
      
      return obj;
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
     * Sets the ManufacturerName property.
     *
     * @param pManufacturerName
     *  String to use to update the property.
     */
    public void setManufacturerName(String pManufacturerName){
        this.mManufacturerName = pManufacturerName;
    }
    /**
     * Retrieves the ManufacturerName property.
     *
     * @return
     *  String containing the ManufacturerName property.
     */
    public String getManufacturerName(){
        return mManufacturerName;
    }


    /**
     * Sets the ManufacturerSku property.
     *
     * @param pManufacturerSku
     *  String to use to update the property.
     */
    public void setManufacturerSku(String pManufacturerSku){
        this.mManufacturerSku = pManufacturerSku;
    }
    /**
     * Retrieves the ManufacturerSku property.
     *
     * @return
     *  String containing the ManufacturerSku property.
     */
    public String getManufacturerSku(){
        return mManufacturerSku;
    }


    /**
     * Sets the Pack property.
     *
     * @param pPack
     *  int to use to update the property.
     */
    public void setPack(int pPack){
        this.mPack = pPack;
    }
    /**
     * Retrieves the Pack property.
     *
     * @return
     *  int containing the Pack property.
     */
    public int getPack(){
        return mPack;
    }


    /**
     * Sets the UOM property.
     *
     * @param pUOM
     *  String to use to update the property.
     */
    public void setUOM(String pUOM){
        this.mUOM = pUOM;
    }
    /**
     * Retrieves the UOM property.
     *
     * @return
     *  String containing the UOM property.
     */
    public String getUOM(){
        return mUOM;
    }


    /**
     * Sets the Color property.
     *
     * @param pColor
     *  String to use to update the property.
     */
    public void setColor(String pColor){
        this.mColor = pColor;
    }
    /**
     * Retrieves the Color property.
     *
     * @return
     *  String containing the Color property.
     */
    public String getColor(){
        return mColor;
    }


    /**
     * Sets the CustomerReferenceCode property.
     *
     * @param pCustomerReferenceCode
     *  int to use to update the property.
     */
    public void setCustomerReferenceCode(int pCustomerReferenceCode){
        this.mCustomerReferenceCode = pCustomerReferenceCode;
    }
    /**
     * Retrieves the CustomerReferenceCode property.
     *
     * @return
     *  int containing the CustomerReferenceCode property.
     */
    public int getCustomerReferenceCode(){
        return mCustomerReferenceCode;
    }


    /**
     * Sets the DistributorName property.
     *
     * @param pDistributorName
     *  String to use to update the property.
     */
    public void setDistributorName(String pDistributorName){
        this.mDistributorName = pDistributorName;
    }
    /**
     * Retrieves the DistributorName property.
     *
     * @return
     *  String containing the DistributorName property.
     */
    public String getDistributorName(){
        return mDistributorName;
    }


    /**
     * Sets the DistributorSku property.
     *
     * @param pDistributorSku
     *  String to use to update the property.
     */
    public void setDistributorSku(String pDistributorSku){
        this.mDistributorSku = pDistributorSku;
    }
    /**
     * Retrieves the DistributorSku property.
     *
     * @return
     *  String containing the DistributorSku property.
     */
    public String getDistributorSku(){
        return mDistributorSku;
    }


    /**
     * Sets the DistributorPack property.
     *
     * @param pDistributorPack
     *  int to use to update the property.
     */
    public void setDistributorPack(int pDistributorPack){
        this.mDistributorPack = pDistributorPack;
    }
    /**
     * Retrieves the DistributorPack property.
     *
     * @return
     *  int containing the DistributorPack property.
     */
    public int getDistributorPack(){
        return mDistributorPack;
    }


    /**
     * Sets the DistributorUOM property.
     *
     * @param pDistributorUOM
     *  String to use to update the property.
     */
    public void setDistributorUOM(String pDistributorUOM){
        this.mDistributorUOM = pDistributorUOM;
    }
    /**
     * Retrieves the DistributorUOM property.
     *
     * @return
     *  String containing the DistributorUOM property.
     */
    public String getDistributorUOM(){
        return mDistributorUOM;
    }


    /**
     * Sets the Price property.
     *
     * @param pPrice
     *  String to use to update the property.
     */
    public void setPrice(String pPrice){
        this.mPrice = pPrice;
    }
    /**
     * Retrieves the Price property.
     *
     * @return
     *  String containing the Price property.
     */
    public String getPrice(){
        return mPrice;
    }


    /**
     * Sets the Category property.
     *
     * @param pCategory
     *  String to use to update the property.
     */
    public void setCategory(String pCategory){
        this.mCategory = pCategory;
    }
    /**
     * Retrieves the Category property.
     *
     * @return
     *  String containing the Category property.
     */
    public String getCategory(){
        return mCategory;
    }


    
}
