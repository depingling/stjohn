
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        PricingListView
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
 * <code>PricingListView</code> is a ViewObject class for UI.
 */
public class PricingListView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private String mListName;
    private int mListId;
    private int mItemId;
    private int mRank;
    private String mDistSku;
    private String mDistUom;
    private BigDecimal mPrice;
    private String mCustSku;

    /**
     * Constructor.
     */
    public PricingListView ()
    {
        mListName = "";
        mDistSku = "";
        mDistUom = "";
        mCustSku = "";
    }

    /**
     * Constructor. 
     */
    public PricingListView(String parm1, int parm2, int parm3, int parm4, String parm5, String parm6, BigDecimal parm7, String parm8)
    {
        mListName = parm1;
        mListId = parm2;
        mItemId = parm3;
        mRank = parm4;
        mDistSku = parm5;
        mDistUom = parm6;
        mPrice = parm7;
        mCustSku = parm8;
        
    }

    /**
     * Creates a new PricingListView
     *
     * @return
     *  Newly initialized PricingListView object.
     */
    public static PricingListView createValue () 
    {
        PricingListView valueView = new PricingListView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this PricingListView object
     */
    public String toString()
    {
        return "[" + "ListName=" + mListName + ", ListId=" + mListId + ", ItemId=" + mItemId + ", Rank=" + mRank + ", DistSku=" + mDistSku + ", DistUom=" + mDistUom + ", Price=" + mPrice + ", CustSku=" + mCustSku + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("PricingList");
	root.setAttribute("Id", String.valueOf(mListName));

	Element node;

        node = doc.createElement("ListId");
        node.appendChild(doc.createTextNode(String.valueOf(mListId)));
        root.appendChild(node);

        node = doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node = doc.createElement("Rank");
        node.appendChild(doc.createTextNode(String.valueOf(mRank)));
        root.appendChild(node);

        node = doc.createElement("DistSku");
        node.appendChild(doc.createTextNode(String.valueOf(mDistSku)));
        root.appendChild(node);

        node = doc.createElement("DistUom");
        node.appendChild(doc.createTextNode(String.valueOf(mDistUom)));
        root.appendChild(node);

        node = doc.createElement("Price");
        node.appendChild(doc.createTextNode(String.valueOf(mPrice)));
        root.appendChild(node);

        node = doc.createElement("CustSku");
        node.appendChild(doc.createTextNode(String.valueOf(mCustSku)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public PricingListView copy()  {
      PricingListView obj = new PricingListView();
      obj.setListName(mListName);
      obj.setListId(mListId);
      obj.setItemId(mItemId);
      obj.setRank(mRank);
      obj.setDistSku(mDistSku);
      obj.setDistUom(mDistUom);
      obj.setPrice(mPrice);
      obj.setCustSku(mCustSku);
      
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
     * Sets the ListId property.
     *
     * @param pListId
     *  int to use to update the property.
     */
    public void setListId(int pListId){
        this.mListId = pListId;
    }
    /**
     * Retrieves the ListId property.
     *
     * @return
     *  int containing the ListId property.
     */
    public int getListId(){
        return mListId;
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
     * Sets the Rank property.
     *
     * @param pRank
     *  int to use to update the property.
     */
    public void setRank(int pRank){
        this.mRank = pRank;
    }
    /**
     * Retrieves the Rank property.
     *
     * @return
     *  int containing the Rank property.
     */
    public int getRank(){
        return mRank;
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
     * Sets the Price property.
     *
     * @param pPrice
     *  BigDecimal to use to update the property.
     */
    public void setPrice(BigDecimal pPrice){
        this.mPrice = pPrice;
    }
    /**
     * Retrieves the Price property.
     *
     * @return
     *  BigDecimal containing the Price property.
     */
    public BigDecimal getPrice(){
        return mPrice;
    }


    /**
     * Sets the CustSku property.
     *
     * @param pCustSku
     *  String to use to update the property.
     */
    public void setCustSku(String pCustSku){
        this.mCustSku = pCustSku;
    }
    /**
     * Retrieves the CustSku property.
     *
     * @return
     *  String containing the CustSku property.
     */
    public String getCustSku(){
        return mCustSku;
    }


    
}
