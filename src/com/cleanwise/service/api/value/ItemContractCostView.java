
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ItemContractCostView
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
 * <code>ItemContractCostView</code> is a ViewObject class for UI.
 */
public class ItemContractCostView
extends ValueObject
{
   
    private static final long serialVersionUID = -5840245556543135922L;
    private int mItemId;
    private int mDistId;
    private int mContractId;
    private int mCatalogId;
    private BigDecimal mItemCost;
    private String mDistDesc;
    private String mContractDesc;

    /**
     * Constructor.
     */
    public ItemContractCostView ()
    {
        mDistDesc = "";
        mContractDesc = "";
    }

    /**
     * Constructor. 
     */
    public ItemContractCostView(int parm1, int parm2, int parm3, int parm4, BigDecimal parm5, String parm6, String parm7)
    {
        mItemId = parm1;
        mDistId = parm2;
        mContractId = parm3;
        mCatalogId = parm4;
        mItemCost = parm5;
        mDistDesc = parm6;
        mContractDesc = parm7;
        
    }

    /**
     * Creates a new ItemContractCostView
     *
     * @return
     *  Newly initialized ItemContractCostView object.
     */
    public static ItemContractCostView createValue () 
    {
        ItemContractCostView valueView = new ItemContractCostView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ItemContractCostView object
     */
    public String toString()
    {
        return "[" + "ItemId=" + mItemId + ", DistId=" + mDistId + ", ContractId=" + mContractId + ", CatalogId=" + mCatalogId + ", ItemCost=" + mItemCost + ", DistDesc=" + mDistDesc + ", ContractDesc=" + mContractDesc + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ItemContractCost");
	root.setAttribute("Id", String.valueOf(mItemId));

	Element node;

        node = doc.createElement("DistId");
        node.appendChild(doc.createTextNode(String.valueOf(mDistId)));
        root.appendChild(node);

        node = doc.createElement("ContractId");
        node.appendChild(doc.createTextNode(String.valueOf(mContractId)));
        root.appendChild(node);

        node = doc.createElement("CatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogId)));
        root.appendChild(node);

        node = doc.createElement("ItemCost");
        node.appendChild(doc.createTextNode(String.valueOf(mItemCost)));
        root.appendChild(node);

        node = doc.createElement("DistDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mDistDesc)));
        root.appendChild(node);

        node = doc.createElement("ContractDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mContractDesc)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ItemContractCostView copy()  {
      ItemContractCostView obj = new ItemContractCostView();
      obj.setItemId(mItemId);
      obj.setDistId(mDistId);
      obj.setContractId(mContractId);
      obj.setCatalogId(mCatalogId);
      obj.setItemCost(mItemCost);
      obj.setDistDesc(mDistDesc);
      obj.setContractDesc(mContractDesc);
      
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
     * Sets the DistId property.
     *
     * @param pDistId
     *  int to use to update the property.
     */
    public void setDistId(int pDistId){
        this.mDistId = pDistId;
    }
    /**
     * Retrieves the DistId property.
     *
     * @return
     *  int containing the DistId property.
     */
    public int getDistId(){
        return mDistId;
    }


    /**
     * Sets the ContractId property.
     *
     * @param pContractId
     *  int to use to update the property.
     */
    public void setContractId(int pContractId){
        this.mContractId = pContractId;
    }
    /**
     * Retrieves the ContractId property.
     *
     * @return
     *  int containing the ContractId property.
     */
    public int getContractId(){
        return mContractId;
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
     * Sets the ItemCost property.
     *
     * @param pItemCost
     *  BigDecimal to use to update the property.
     */
    public void setItemCost(BigDecimal pItemCost){
        this.mItemCost = pItemCost;
    }
    /**
     * Retrieves the ItemCost property.
     *
     * @return
     *  BigDecimal containing the ItemCost property.
     */
    public BigDecimal getItemCost(){
        return mItemCost;
    }


    /**
     * Sets the DistDesc property.
     *
     * @param pDistDesc
     *  String to use to update the property.
     */
    public void setDistDesc(String pDistDesc){
        this.mDistDesc = pDistDesc;
    }
    /**
     * Retrieves the DistDesc property.
     *
     * @return
     *  String containing the DistDesc property.
     */
    public String getDistDesc(){
        return mDistDesc;
    }


    /**
     * Sets the ContractDesc property.
     *
     * @param pContractDesc
     *  String to use to update the property.
     */
    public void setContractDesc(String pContractDesc){
        this.mContractDesc = pContractDesc;
    }
    /**
     * Retrieves the ContractDesc property.
     *
     * @return
     *  String containing the ContractDesc property.
     */
    public String getContractDesc(){
        return mContractDesc;
    }


    
}
