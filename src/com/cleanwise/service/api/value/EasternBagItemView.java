
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        EasternBagItemView
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
 * <code>EasternBagItemView</code> is a ViewObject class for UI.
 */
public class EasternBagItemView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private String mEffDate;
    private String mRefNumber;
    private String mDistUom;
    private String mCategoryName;
    private String mManufSku;
    private String mDistSku;
    private String mCustomerSku;
    private String mManufName;
    private String mDistItemDesc1;
    private String mDistItemDesc2;
    private String mPack;
    private BigDecimal mListPrice;

    /**
     * Constructor.
     */
    public EasternBagItemView ()
    {
        mEffDate = "";
        mRefNumber = "";
        mDistUom = "";
        mCategoryName = "";
        mManufSku = "";
        mDistSku = "";
        mCustomerSku = "";
        mManufName = "";
        mDistItemDesc1 = "";
        mDistItemDesc2 = "";
        mPack = "";
    }

    /**
     * Constructor. 
     */
    public EasternBagItemView(String parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, BigDecimal parm12)
    {
        mEffDate = parm1;
        mRefNumber = parm2;
        mDistUom = parm3;
        mCategoryName = parm4;
        mManufSku = parm5;
        mDistSku = parm6;
        mCustomerSku = parm7;
        mManufName = parm8;
        mDistItemDesc1 = parm9;
        mDistItemDesc2 = parm10;
        mPack = parm11;
        mListPrice = parm12;
        
    }

    /**
     * Creates a new EasternBagItemView
     *
     * @return
     *  Newly initialized EasternBagItemView object.
     */
    public static EasternBagItemView createValue () 
    {
        EasternBagItemView valueView = new EasternBagItemView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this EasternBagItemView object
     */
    public String toString()
    {
        return "[" + "EffDate=" + mEffDate + ", RefNumber=" + mRefNumber + ", DistUom=" + mDistUom + ", CategoryName=" + mCategoryName + ", ManufSku=" + mManufSku + ", DistSku=" + mDistSku + ", CustomerSku=" + mCustomerSku + ", ManufName=" + mManufName + ", DistItemDesc1=" + mDistItemDesc1 + ", DistItemDesc2=" + mDistItemDesc2 + ", Pack=" + mPack + ", ListPrice=" + mListPrice + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("EasternBagItem");
	root.setAttribute("Id", String.valueOf(mEffDate));

	Element node;

        node = doc.createElement("RefNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mRefNumber)));
        root.appendChild(node);

        node = doc.createElement("DistUom");
        node.appendChild(doc.createTextNode(String.valueOf(mDistUom)));
        root.appendChild(node);

        node = doc.createElement("CategoryName");
        node.appendChild(doc.createTextNode(String.valueOf(mCategoryName)));
        root.appendChild(node);

        node = doc.createElement("ManufSku");
        node.appendChild(doc.createTextNode(String.valueOf(mManufSku)));
        root.appendChild(node);

        node = doc.createElement("DistSku");
        node.appendChild(doc.createTextNode(String.valueOf(mDistSku)));
        root.appendChild(node);

        node = doc.createElement("CustomerSku");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerSku)));
        root.appendChild(node);

        node = doc.createElement("ManufName");
        node.appendChild(doc.createTextNode(String.valueOf(mManufName)));
        root.appendChild(node);

        node = doc.createElement("DistItemDesc1");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemDesc1)));
        root.appendChild(node);

        node = doc.createElement("DistItemDesc2");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemDesc2)));
        root.appendChild(node);

        node = doc.createElement("Pack");
        node.appendChild(doc.createTextNode(String.valueOf(mPack)));
        root.appendChild(node);

        node = doc.createElement("ListPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mListPrice)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public EasternBagItemView copy()  {
      EasternBagItemView obj = new EasternBagItemView();
      obj.setEffDate(mEffDate);
      obj.setRefNumber(mRefNumber);
      obj.setDistUom(mDistUom);
      obj.setCategoryName(mCategoryName);
      obj.setManufSku(mManufSku);
      obj.setDistSku(mDistSku);
      obj.setCustomerSku(mCustomerSku);
      obj.setManufName(mManufName);
      obj.setDistItemDesc1(mDistItemDesc1);
      obj.setDistItemDesc2(mDistItemDesc2);
      obj.setPack(mPack);
      obj.setListPrice(mListPrice);
      
      return obj;
    }

    
    /**
     * Sets the EffDate property.
     *
     * @param pEffDate
     *  String to use to update the property.
     */
    public void setEffDate(String pEffDate){
        this.mEffDate = pEffDate;
    }
    /**
     * Retrieves the EffDate property.
     *
     * @return
     *  String containing the EffDate property.
     */
    public String getEffDate(){
        return mEffDate;
    }


    /**
     * Sets the RefNumber property.
     *
     * @param pRefNumber
     *  String to use to update the property.
     */
    public void setRefNumber(String pRefNumber){
        this.mRefNumber = pRefNumber;
    }
    /**
     * Retrieves the RefNumber property.
     *
     * @return
     *  String containing the RefNumber property.
     */
    public String getRefNumber(){
        return mRefNumber;
    }


    /**
     * Sets the DistUom property.
     *
     * @param pDistUom
     *  String to use to update the property.
     */
    public void setDistUom(String pDistUom){
        this.mDistUom = pDistUom;
    }
    /**
     * Retrieves the DistUom property.
     *
     * @return
     *  String containing the DistUom property.
     */
    public String getDistUom(){
        return mDistUom;
    }


    /**
     * Sets the CategoryName property.
     *
     * @param pCategoryName
     *  String to use to update the property.
     */
    public void setCategoryName(String pCategoryName){
        this.mCategoryName = pCategoryName;
    }
    /**
     * Retrieves the CategoryName property.
     *
     * @return
     *  String containing the CategoryName property.
     */
    public String getCategoryName(){
        return mCategoryName;
    }


    /**
     * Sets the ManufSku property.
     *
     * @param pManufSku
     *  String to use to update the property.
     */
    public void setManufSku(String pManufSku){
        this.mManufSku = pManufSku;
    }
    /**
     * Retrieves the ManufSku property.
     *
     * @return
     *  String containing the ManufSku property.
     */
    public String getManufSku(){
        return mManufSku;
    }


    /**
     * Sets the DistSku property.
     *
     * @param pDistSku
     *  String to use to update the property.
     */
    public void setDistSku(String pDistSku){
        this.mDistSku = pDistSku;
    }
    /**
     * Retrieves the DistSku property.
     *
     * @return
     *  String containing the DistSku property.
     */
    public String getDistSku(){
        return mDistSku;
    }


    /**
     * Sets the CustomerSku property.
     *
     * @param pCustomerSku
     *  String to use to update the property.
     */
    public void setCustomerSku(String pCustomerSku){
        this.mCustomerSku = pCustomerSku;
    }
    /**
     * Retrieves the CustomerSku property.
     *
     * @return
     *  String containing the CustomerSku property.
     */
    public String getCustomerSku(){
        return mCustomerSku;
    }


    /**
     * Sets the ManufName property.
     *
     * @param pManufName
     *  String to use to update the property.
     */
    public void setManufName(String pManufName){
        this.mManufName = pManufName;
    }
    /**
     * Retrieves the ManufName property.
     *
     * @return
     *  String containing the ManufName property.
     */
    public String getManufName(){
        return mManufName;
    }


    /**
     * Sets the DistItemDesc1 property.
     *
     * @param pDistItemDesc1
     *  String to use to update the property.
     */
    public void setDistItemDesc1(String pDistItemDesc1){
        this.mDistItemDesc1 = pDistItemDesc1;
    }
    /**
     * Retrieves the DistItemDesc1 property.
     *
     * @return
     *  String containing the DistItemDesc1 property.
     */
    public String getDistItemDesc1(){
        return mDistItemDesc1;
    }


    /**
     * Sets the DistItemDesc2 property.
     *
     * @param pDistItemDesc2
     *  String to use to update the property.
     */
    public void setDistItemDesc2(String pDistItemDesc2){
        this.mDistItemDesc2 = pDistItemDesc2;
    }
    /**
     * Retrieves the DistItemDesc2 property.
     *
     * @return
     *  String containing the DistItemDesc2 property.
     */
    public String getDistItemDesc2(){
        return mDistItemDesc2;
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
     * Sets the ListPrice property.
     *
     * @param pListPrice
     *  BigDecimal to use to update the property.
     */
    public void setListPrice(BigDecimal pListPrice){
        this.mListPrice = pListPrice;
    }
    /**
     * Retrieves the ListPrice property.
     *
     * @return
     *  BigDecimal containing the ListPrice property.
     */
    public BigDecimal getListPrice(){
        return mListPrice;
    }


    
}
