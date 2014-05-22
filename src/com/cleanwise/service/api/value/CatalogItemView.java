
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        CatalogItemView
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
 * <code>CatalogItemView</code> is a ViewObject class for UI.
 */
public class CatalogItemView
extends ValueObject
{
   
    private static final long serialVersionUID = -8376743377325172380L;
    private int mItemId;
    private String mName;
    private int mSkuNum;

    /**
     * Constructor.
     */
    public CatalogItemView ()
    {
        mName = "";
    }

    /**
     * Constructor. 
     */
    public CatalogItemView(int parm1, String parm2, int parm3)
    {
        mItemId = parm1;
        mName = parm2;
        mSkuNum = parm3;
        
    }

    /**
     * Creates a new CatalogItemView
     *
     * @return
     *  Newly initialized CatalogItemView object.
     */
    public static CatalogItemView createValue () 
    {
        CatalogItemView valueView = new CatalogItemView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this CatalogItemView object
     */
    public String toString()
    {
        return "[" + "ItemId=" + mItemId + ", Name=" + mName + ", SkuNum=" + mSkuNum + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("CatalogItem");
	root.setAttribute("Id", String.valueOf(mItemId));

	Element node;

        node = doc.createElement("Name");
        node.appendChild(doc.createTextNode(String.valueOf(mName)));
        root.appendChild(node);

        node = doc.createElement("SkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSkuNum)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public CatalogItemView copy()  {
      CatalogItemView obj = new CatalogItemView();
      obj.setItemId(mItemId);
      obj.setName(mName);
      obj.setSkuNum(mSkuNum);
      
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
     * Sets the Name property.
     *
     * @param pName
     *  String to use to update the property.
     */
    public void setName(String pName){
        this.mName = pName;
    }
    /**
     * Retrieves the Name property.
     *
     * @return
     *  String containing the Name property.
     */
    public String getName(){
        return mName;
    }


    /**
     * Sets the SkuNum property.
     *
     * @param pSkuNum
     *  int to use to update the property.
     */
    public void setSkuNum(int pSkuNum){
        this.mSkuNum = pSkuNum;
    }
    /**
     * Retrieves the SkuNum property.
     *
     * @return
     *  int containing the SkuNum property.
     */
    public int getSkuNum(){
        return mSkuNum;
    }


    
}
