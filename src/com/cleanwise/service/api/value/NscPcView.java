
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        NscPcView
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
 * <code>NscPcView</code> is a ViewObject class for UI.
 */
public class NscPcView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private String mNscItemNumber;
    private String mManufacturer;
    private String mMfgSku;
    private String mDefaultUom;
    private String mListPrice1;
    private String mItemDescription1;
    private String mItemDescription2;
    private String mCompany;
    private String mExtendetItemDescription;
    private String mStandartPack;
    private String mCatalogName;
    private String mCategory;
    private String mNscSku;
    private String mUpc;
    private String mCustomerItemNumber;
    private String mDistItemNumber;

    /**
     * Constructor.
     */
    public NscPcView ()
    {
        mNscItemNumber = "";
        mManufacturer = "";
        mMfgSku = "";
        mDefaultUom = "";
        mListPrice1 = "";
        mItemDescription1 = "";
        mItemDescription2 = "";
        mCompany = "";
        mExtendetItemDescription = "";
        mStandartPack = "";
        mCatalogName = "";
        mCategory = "";
        mNscSku = "";
        mUpc = "";
        mCustomerItemNumber = "";
        mDistItemNumber = "";
    }

    /**
     * Constructor. 
     */
    public NscPcView(String parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13, String parm14, String parm15, String parm16)
    {
        mNscItemNumber = parm1;
        mManufacturer = parm2;
        mMfgSku = parm3;
        mDefaultUom = parm4;
        mListPrice1 = parm5;
        mItemDescription1 = parm6;
        mItemDescription2 = parm7;
        mCompany = parm8;
        mExtendetItemDescription = parm9;
        mStandartPack = parm10;
        mCatalogName = parm11;
        mCategory = parm12;
        mNscSku = parm13;
        mUpc = parm14;
        mCustomerItemNumber = parm15;
        mDistItemNumber = parm16;
        
    }

    /**
     * Creates a new NscPcView
     *
     * @return
     *  Newly initialized NscPcView object.
     */
    public static NscPcView createValue () 
    {
        NscPcView valueView = new NscPcView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this NscPcView object
     */
    public String toString()
    {
        return "[" + "NscItemNumber=" + mNscItemNumber + ", Manufacturer=" + mManufacturer + ", MfgSku=" + mMfgSku + ", DefaultUom=" + mDefaultUom + ", ListPrice1=" + mListPrice1 + ", ItemDescription1=" + mItemDescription1 + ", ItemDescription2=" + mItemDescription2 + ", Company=" + mCompany + ", ExtendetItemDescription=" + mExtendetItemDescription + ", StandartPack=" + mStandartPack + ", CatalogName=" + mCatalogName + ", Category=" + mCategory + ", NscSku=" + mNscSku + ", Upc=" + mUpc + ", CustomerItemNumber=" + mCustomerItemNumber + ", DistItemNumber=" + mDistItemNumber + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("NscPc");
	root.setAttribute("Id", String.valueOf(mNscItemNumber));

	Element node;

        node = doc.createElement("Manufacturer");
        node.appendChild(doc.createTextNode(String.valueOf(mManufacturer)));
        root.appendChild(node);

        node = doc.createElement("MfgSku");
        node.appendChild(doc.createTextNode(String.valueOf(mMfgSku)));
        root.appendChild(node);

        node = doc.createElement("DefaultUom");
        node.appendChild(doc.createTextNode(String.valueOf(mDefaultUom)));
        root.appendChild(node);

        node = doc.createElement("ListPrice1");
        node.appendChild(doc.createTextNode(String.valueOf(mListPrice1)));
        root.appendChild(node);

        node = doc.createElement("ItemDescription1");
        node.appendChild(doc.createTextNode(String.valueOf(mItemDescription1)));
        root.appendChild(node);

        node = doc.createElement("ItemDescription2");
        node.appendChild(doc.createTextNode(String.valueOf(mItemDescription2)));
        root.appendChild(node);

        node = doc.createElement("Company");
        node.appendChild(doc.createTextNode(String.valueOf(mCompany)));
        root.appendChild(node);

        node = doc.createElement("ExtendetItemDescription");
        node.appendChild(doc.createTextNode(String.valueOf(mExtendetItemDescription)));
        root.appendChild(node);

        node = doc.createElement("StandartPack");
        node.appendChild(doc.createTextNode(String.valueOf(mStandartPack)));
        root.appendChild(node);

        node = doc.createElement("CatalogName");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogName)));
        root.appendChild(node);

        node = doc.createElement("Category");
        node.appendChild(doc.createTextNode(String.valueOf(mCategory)));
        root.appendChild(node);

        node = doc.createElement("NscSku");
        node.appendChild(doc.createTextNode(String.valueOf(mNscSku)));
        root.appendChild(node);

        node = doc.createElement("Upc");
        node.appendChild(doc.createTextNode(String.valueOf(mUpc)));
        root.appendChild(node);

        node = doc.createElement("CustomerItemNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mCustomerItemNumber)));
        root.appendChild(node);

        node = doc.createElement("DistItemNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemNumber)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public NscPcView copy()  {
      NscPcView obj = new NscPcView();
      obj.setNscItemNumber(mNscItemNumber);
      obj.setManufacturer(mManufacturer);
      obj.setMfgSku(mMfgSku);
      obj.setDefaultUom(mDefaultUom);
      obj.setListPrice1(mListPrice1);
      obj.setItemDescription1(mItemDescription1);
      obj.setItemDescription2(mItemDescription2);
      obj.setCompany(mCompany);
      obj.setExtendetItemDescription(mExtendetItemDescription);
      obj.setStandartPack(mStandartPack);
      obj.setCatalogName(mCatalogName);
      obj.setCategory(mCategory);
      obj.setNscSku(mNscSku);
      obj.setUpc(mUpc);
      obj.setCustomerItemNumber(mCustomerItemNumber);
      obj.setDistItemNumber(mDistItemNumber);
      
      return obj;
    }

    
    /**
     * Sets the NscItemNumber property.
     *
     * @param pNscItemNumber
     *  String to use to update the property.
     */
    public void setNscItemNumber(String pNscItemNumber){
        this.mNscItemNumber = pNscItemNumber;
    }
    /**
     * Retrieves the NscItemNumber property.
     *
     * @return
     *  String containing the NscItemNumber property.
     */
    public String getNscItemNumber(){
        return mNscItemNumber;
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
     * Sets the MfgSku property.
     *
     * @param pMfgSku
     *  String to use to update the property.
     */
    public void setMfgSku(String pMfgSku){
        this.mMfgSku = pMfgSku;
    }
    /**
     * Retrieves the MfgSku property.
     *
     * @return
     *  String containing the MfgSku property.
     */
    public String getMfgSku(){
        return mMfgSku;
    }


    /**
     * Sets the DefaultUom property.
     *
     * @param pDefaultUom
     *  String to use to update the property.
     */
    public void setDefaultUom(String pDefaultUom){
        this.mDefaultUom = pDefaultUom;
    }
    /**
     * Retrieves the DefaultUom property.
     *
     * @return
     *  String containing the DefaultUom property.
     */
    public String getDefaultUom(){
        return mDefaultUom;
    }


    /**
     * Sets the ListPrice1 property.
     *
     * @param pListPrice1
     *  String to use to update the property.
     */
    public void setListPrice1(String pListPrice1){
        this.mListPrice1 = pListPrice1;
    }
    /**
     * Retrieves the ListPrice1 property.
     *
     * @return
     *  String containing the ListPrice1 property.
     */
    public String getListPrice1(){
        return mListPrice1;
    }


    /**
     * Sets the ItemDescription1 property.
     *
     * @param pItemDescription1
     *  String to use to update the property.
     */
    public void setItemDescription1(String pItemDescription1){
        this.mItemDescription1 = pItemDescription1;
    }
    /**
     * Retrieves the ItemDescription1 property.
     *
     * @return
     *  String containing the ItemDescription1 property.
     */
    public String getItemDescription1(){
        return mItemDescription1;
    }


    /**
     * Sets the ItemDescription2 property.
     *
     * @param pItemDescription2
     *  String to use to update the property.
     */
    public void setItemDescription2(String pItemDescription2){
        this.mItemDescription2 = pItemDescription2;
    }
    /**
     * Retrieves the ItemDescription2 property.
     *
     * @return
     *  String containing the ItemDescription2 property.
     */
    public String getItemDescription2(){
        return mItemDescription2;
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
     * Sets the ExtendetItemDescription property.
     *
     * @param pExtendetItemDescription
     *  String to use to update the property.
     */
    public void setExtendetItemDescription(String pExtendetItemDescription){
        this.mExtendetItemDescription = pExtendetItemDescription;
    }
    /**
     * Retrieves the ExtendetItemDescription property.
     *
     * @return
     *  String containing the ExtendetItemDescription property.
     */
    public String getExtendetItemDescription(){
        return mExtendetItemDescription;
    }


    /**
     * Sets the StandartPack property.
     *
     * @param pStandartPack
     *  String to use to update the property.
     */
    public void setStandartPack(String pStandartPack){
        this.mStandartPack = pStandartPack;
    }
    /**
     * Retrieves the StandartPack property.
     *
     * @return
     *  String containing the StandartPack property.
     */
    public String getStandartPack(){
        return mStandartPack;
    }


    /**
     * Sets the CatalogName property.
     *
     * @param pCatalogName
     *  String to use to update the property.
     */
    public void setCatalogName(String pCatalogName){
        this.mCatalogName = pCatalogName;
    }
    /**
     * Retrieves the CatalogName property.
     *
     * @return
     *  String containing the CatalogName property.
     */
    public String getCatalogName(){
        return mCatalogName;
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


    /**
     * Sets the NscSku property.
     *
     * @param pNscSku
     *  String to use to update the property.
     */
    public void setNscSku(String pNscSku){
        this.mNscSku = pNscSku;
    }
    /**
     * Retrieves the NscSku property.
     *
     * @return
     *  String containing the NscSku property.
     */
    public String getNscSku(){
        return mNscSku;
    }


    /**
     * Sets the Upc property.
     *
     * @param pUpc
     *  String to use to update the property.
     */
    public void setUpc(String pUpc){
        this.mUpc = pUpc;
    }
    /**
     * Retrieves the Upc property.
     *
     * @return
     *  String containing the Upc property.
     */
    public String getUpc(){
        return mUpc;
    }


    /**
     * Sets the CustomerItemNumber property.
     *
     * @param pCustomerItemNumber
     *  String to use to update the property.
     */
    public void setCustomerItemNumber(String pCustomerItemNumber){
        this.mCustomerItemNumber = pCustomerItemNumber;
    }
    /**
     * Retrieves the CustomerItemNumber property.
     *
     * @return
     *  String containing the CustomerItemNumber property.
     */
    public String getCustomerItemNumber(){
        return mCustomerItemNumber;
    }


    /**
     * Sets the DistItemNumber property.
     *
     * @param pDistItemNumber
     *  String to use to update the property.
     */
    public void setDistItemNumber(String pDistItemNumber){
        this.mDistItemNumber = pDistItemNumber;
    }
    /**
     * Retrieves the DistItemNumber property.
     *
     * @return
     *  String containing the DistItemNumber property.
     */
    public String getDistItemNumber(){
        return mDistItemNumber;
    }


    
}
