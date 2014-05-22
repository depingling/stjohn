
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        MultiproductView
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
 * <code>MultiproductView</code> is a ViewObject class for UI.
 */
public class MultiproductView
extends ValueObject
{
   
    private static final long serialVersionUID = -6976601127425296853L;
    private ItemData mItemData;
    private int mMultiproductId;
    private String mMultiproductName;
    private int mCatalogId;
    private String mCatalogName;

    /**
     * Constructor.
     */
    public MultiproductView ()
    {
        mMultiproductName = "";
        mCatalogName = "";
    }

    /**
     * Constructor. 
     */
    public MultiproductView(ItemData parm1, int parm2, String parm3, int parm4, String parm5)
    {
        mItemData = parm1;
        mMultiproductId = parm2;
        mMultiproductName = parm3;
        mCatalogId = parm4;
        mCatalogName = parm5;
        
    }

    /**
     * Creates a new MultiproductView
     *
     * @return
     *  Newly initialized MultiproductView object.
     */
    public static MultiproductView createValue () 
    {
        MultiproductView valueView = new MultiproductView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this MultiproductView object
     */
    public String toString()
    {
        return "[" + "ItemData=" + mItemData + ", MultiproductId=" + mMultiproductId + ", MultiproductName=" + mMultiproductName + ", CatalogId=" + mCatalogId + ", CatalogName=" + mCatalogName + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("Multiproduct");
	root.setAttribute("Id", String.valueOf(mItemData));

	Element node;

        node = doc.createElement("MultiproductId");
        node.appendChild(doc.createTextNode(String.valueOf(mMultiproductId)));
        root.appendChild(node);

        node = doc.createElement("MultiproductName");
        node.appendChild(doc.createTextNode(String.valueOf(mMultiproductName)));
        root.appendChild(node);

        node = doc.createElement("CatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogId)));
        root.appendChild(node);

        node = doc.createElement("CatalogName");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogName)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public MultiproductView copy()  {
      MultiproductView obj = new MultiproductView();
      obj.setItemData(mItemData);
      obj.setMultiproductId(mMultiproductId);
      obj.setMultiproductName(mMultiproductName);
      obj.setCatalogId(mCatalogId);
      obj.setCatalogName(mCatalogName);
      
      return obj;
    }

    
    /**
     * Sets the ItemData property.
     *
     * @param pItemData
     *  ItemData to use to update the property.
     */
    public void setItemData(ItemData pItemData){
        this.mItemData = pItemData;
    }
    /**
     * Retrieves the ItemData property.
     *
     * @return
     *  ItemData containing the ItemData property.
     */
    public ItemData getItemData(){
        return mItemData;
    }


    /**
     * Sets the MultiproductId property.
     *
     * @param pMultiproductId
     *  int to use to update the property.
     */
    public void setMultiproductId(int pMultiproductId){
        this.mMultiproductId = pMultiproductId;
    }
    /**
     * Retrieves the MultiproductId property.
     *
     * @return
     *  int containing the MultiproductId property.
     */
    public int getMultiproductId(){
        return mMultiproductId;
    }


    /**
     * Sets the MultiproductName property.
     *
     * @param pMultiproductName
     *  String to use to update the property.
     */
    public void setMultiproductName(String pMultiproductName){
        this.mMultiproductName = pMultiproductName;
    }
    /**
     * Retrieves the MultiproductName property.
     *
     * @return
     *  String containing the MultiproductName property.
     */
    public String getMultiproductName(){
        return mMultiproductName;
    }


    /**
     * Sets the CatalogId property.
     *
     * @param pCatalogId
     *  int to use to update the property.
     */
    public void setCatalogId(int pCatalogId){
        this.mCatalogId = pCatalogId;
    }
    /**
     * Retrieves the CatalogId property.
     *
     * @return
     *  int containing the CatalogId property.
     */
    public int getCatalogId(){
        return mCatalogId;
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


    
}
