
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        BuyListView
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
import java.math.*;
import org.w3c.dom.*;
/**
 * <code>BuyListView</code> is a ViewObject class for UI.
 */
public class BuyListView
extends ValueObject
{

    private static final long serialVersionUID = -1L;
    private String mListName;
    private int mCatalogId;
    private int mItemId;
    private String mDistSku;
    private String mDistUom;
    private BigDecimal mListPrice;

    /**
     * Constructor.
     */
    public BuyListView ()
    {
        mListName = "";
        mDistSku = "";
        mDistUom = "";
    }

    /**
     * Constructor.
     */
    public BuyListView(String parm1, int parm2, int parm3, String parm4, String parm5, BigDecimal parm6)
    {
        mListName = parm1;
        mCatalogId = parm2;
        mItemId = parm3;
        mDistSku = parm4;
        mDistUom = parm5;
        mListPrice = parm6;

    }

    /**
     * Creates a new BuyListView
     *
     * @return
     *  Newly initialized BuyListView object.
     */
    public static BuyListView createValue ()
    {
        BuyListView valueView = new BuyListView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this BuyListView object
     */
    public String toString()
    {
        return "[" + "ListName=" + mListName + ", CatalogId=" + mCatalogId + ", ItemId=" + mItemId + ", DistSku=" + mDistSku + ", DistUom=" + mDistUom + ", ListPrice=" + mListPrice + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("BuyList");
	root.setAttribute("Id", String.valueOf(mListName));

	Element node;

        node = doc.createElement("CatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogId)));
        root.appendChild(node);

        node = doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node = doc.createElement("DistSku");
        node.appendChild(doc.createTextNode(String.valueOf(mDistSku)));
        root.appendChild(node);

        node = doc.createElement("DistUom");
        node.appendChild(doc.createTextNode(String.valueOf(mDistUom)));
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
    public BuyListView copy()  {
      BuyListView obj = new BuyListView();
      obj.setListName(mListName);
      obj.setCatalogId(mCatalogId);
      obj.setItemId(mItemId);
      obj.setDistSku(mDistSku);
      obj.setDistUom(mDistUom);
      obj.setListPrice(mListPrice);

      return obj;
    }


    /**
     * Sets the ListName property.
     *
     * @param pListName
     *  String to use to update the property.
     */
    public void setListName(String pListName){
        this.mListName = pListName;
    }
    /**
     * Retrieves the ListName property.
     *
     * @return
     *  String containing the ListName property.
     */
    public String getListName(){
        return mListName;
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
