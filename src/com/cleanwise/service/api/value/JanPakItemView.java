
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        JanPakItemView
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
 * <code>JanPakItemView</code> is a ViewObject class for UI.
 */
public class JanPakItemView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private String mDistSku;
    private String mDistItemDesc;
    private String mPriceLine;
    private String mManufSku;
    private String mManufName;
    private String mDistUom;
    private String mCategoryName;

    /**
     * Constructor.
     */
    public JanPakItemView ()
    {
        mDistSku = "";
        mDistItemDesc = "";
        mPriceLine = "";
        mManufSku = "";
        mManufName = "";
        mDistUom = "";
        mCategoryName = "";
    }

    /**
     * Constructor. 
     */
    public JanPakItemView(String parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7)
    {
        mDistSku = parm1;
        mDistItemDesc = parm2;
        mPriceLine = parm3;
        mManufSku = parm4;
        mManufName = parm5;
        mDistUom = parm6;
        mCategoryName = parm7;
        
    }

    /**
     * Creates a new JanPakItemView
     *
     * @return
     *  Newly initialized JanPakItemView object.
     */
    public static JanPakItemView createValue () 
    {
        JanPakItemView valueView = new JanPakItemView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this JanPakItemView object
     */
    public String toString()
    {
        return "[" + "DistSku=" + mDistSku + ", DistItemDesc=" + mDistItemDesc + ", PriceLine=" + mPriceLine + ", ManufSku=" + mManufSku + ", ManufName=" + mManufName + ", DistUom=" + mDistUom + ", CategoryName=" + mCategoryName + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("JanPakItem");
	root.setAttribute("Id", String.valueOf(mDistSku));

	Element node;

        node = doc.createElement("DistItemDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemDesc)));
        root.appendChild(node);

        node = doc.createElement("PriceLine");
        node.appendChild(doc.createTextNode(String.valueOf(mPriceLine)));
        root.appendChild(node);

        node = doc.createElement("ManufSku");
        node.appendChild(doc.createTextNode(String.valueOf(mManufSku)));
        root.appendChild(node);

        node = doc.createElement("ManufName");
        node.appendChild(doc.createTextNode(String.valueOf(mManufName)));
        root.appendChild(node);

        node = doc.createElement("DistUom");
        node.appendChild(doc.createTextNode(String.valueOf(mDistUom)));
        root.appendChild(node);

        node = doc.createElement("CategoryName");
        node.appendChild(doc.createTextNode(String.valueOf(mCategoryName)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public JanPakItemView copy()  {
      JanPakItemView obj = new JanPakItemView();
      obj.setDistSku(mDistSku);
      obj.setDistItemDesc(mDistItemDesc);
      obj.setPriceLine(mPriceLine);
      obj.setManufSku(mManufSku);
      obj.setManufName(mManufName);
      obj.setDistUom(mDistUom);
      obj.setCategoryName(mCategoryName);
      
      return obj;
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
     * Sets the DistItemDesc property.
     *
     * @param pDistItemDesc
     *  String to use to update the property.
     */
    public void setDistItemDesc(String pDistItemDesc){
        this.mDistItemDesc = pDistItemDesc;
    }
    /**
     * Retrieves the DistItemDesc property.
     *
     * @return
     *  String containing the DistItemDesc property.
     */
    public String getDistItemDesc(){
        return mDistItemDesc;
    }


    /**
     * Sets the PriceLine property.
     *
     * @param pPriceLine
     *  String to use to update the property.
     */
    public void setPriceLine(String pPriceLine){
        this.mPriceLine = pPriceLine;
    }
    /**
     * Retrieves the PriceLine property.
     *
     * @return
     *  String containing the PriceLine property.
     */
    public String getPriceLine(){
        return mPriceLine;
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


    
}
