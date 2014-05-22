


package com.cleanwise.service.api.value;

/**
 * Title:        ShoppingControlItemView
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
 * <code>ShoppingControlItemView</code> is a ViewObject class for UI.
 */
public class ShoppingControlItemView
extends ValueObject
{

    private static final long serialVersionUID = -2423356624291568560L;
    private ShoppingControlData mShoppingControlData;
    private String mSkuNum;
    private String mShortDesc;
    private String mSize;
    private String mUOM;
    private String mPack;

    /**
     * Constructor.
     */
    public ShoppingControlItemView ()
    {
        mSkuNum = "";
        mShortDesc = "";
        mSize = "";
        mUOM = "";
        mPack = "";
        setDirty(false);
    }

    /**
     * Constructor.
     */
    public ShoppingControlItemView(ShoppingControlData parm1, String parm2, String parm3, String parm4, String parm5, String parm6)
    {
        setShoppingControlData(parm1);
        mSkuNum = parm2;
        mShortDesc = parm3;
        mSize = parm4;
        mUOM = parm5;
        mPack = parm6;
        setDirty(false);
    }

    /**
     * Creates a new ShoppingControlItemView
     *
     * @return
     *  Newly initialized ShoppingControlItemView object.
     */
    public static ShoppingControlItemView createValue ()
    {
        ShoppingControlItemView valueView = new ShoppingControlItemView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ShoppingControlItemView object
     */
    public String toString()
    {
        return "[" + "ShoppingControlData=" + mShoppingControlData + ", SkuNum=" + mSkuNum + ", ShortDesc=" + mShortDesc + ", Size=" + mSize + ", UOM=" + mUOM + ", Pack=" + mPack + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ShoppingControlItem");
	root.setAttribute("Id", String.valueOf(mShoppingControlData));

	Element node;

        node = doc.createElement("SkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mSkuNum)));
        root.appendChild(node);

        node = doc.createElement("ShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDesc)));
        root.appendChild(node);

        node = doc.createElement("Size");
        node.appendChild(doc.createTextNode(String.valueOf(mSize)));
        root.appendChild(node);

        node = doc.createElement("UOM");
        node.appendChild(doc.createTextNode(String.valueOf(mUOM)));
        root.appendChild(node);

        node = doc.createElement("Pack");
        node.appendChild(doc.createTextNode(String.valueOf(mPack)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ShoppingControlItemView copy()  {
      ShoppingControlItemView obj = new ShoppingControlItemView();
      obj.setShoppingControlData(mShoppingControlData);
      obj.setSkuNum(mSkuNum);
      obj.setItemId(itemId);
      obj.setShortDesc(mShortDesc);
      obj.setSize(mSize);
      obj.setUOM(mUOM);
      obj.setPack(mPack);
      return obj;
    }


    /**
     * Sets the ShoppingControlData property.
     *
     * @param pShoppingControlData
     *  ShoppingControlData to use to update the property.
     */
    public void setShoppingControlData(ShoppingControlData pShoppingControlData){
        this.mShoppingControlData = pShoppingControlData;
        if(pShoppingControlData != null){
            if (pShoppingControlData.getMaxOrderQty() > 0
                    || (pShoppingControlData.getMaxOrderQty() == 0 && pShoppingControlData
                            .getShoppingControlId() > 0)) {
        		this.setMaxOrderQty(Integer.toString(pShoppingControlData.getMaxOrderQty()));
        	}else if (pShoppingControlData.getMaxOrderQty() < 0){
        		this.setMaxOrderQty("*");
        	}
            if (pShoppingControlData.getRestrictionDays() > 0
                    || (pShoppingControlData.getRestrictionDays() == 0 && pShoppingControlData
                            .getShoppingControlId() > 0)) {
        		this.setRestrictionDays(Integer.toString(pShoppingControlData.getRestrictionDays()));
        	}else if (pShoppingControlData.getRestrictionDays() < 0){
        		this.setRestrictionDays("*");
        	}
        	this.itemId = pShoppingControlData.getItemId();
        }

    }
    /**
     * Retrieves the ShoppingControlData property.
     *
     * @return
     *  ShoppingControlData containing the ShoppingControlData property.
     */
    public ShoppingControlData getShoppingControlData(){
        return mShoppingControlData;
    }


    /**
     * Sets the SkuNum property.
     *
     * @param pSkuNum
     *  String to use to update the property.
     */
    public void setSkuNum(String pSkuNum){
        this.mSkuNum = pSkuNum;
    }
    /**
     * Retrieves the SkuNum property.
     *
     * @return
     *  String containing the SkuNum property.
     */
    public String getSkuNum(){
        return mSkuNum;
    }


    /**
     * Sets the ShortDesc property.
     *
     * @param pShortDesc
     *  String to use to update the property.
     */
    public void setShortDesc(String pShortDesc){
        this.mShortDesc = pShortDesc;
    }
    /**
     * Retrieves the ShortDesc property.
     *
     * @return
     *  String containing the ShortDesc property.
     */
    public String getShortDesc(){
        return mShortDesc;
    }


    /**
     * Sets the Size property.
     *
     * @param pSize
     *  String to use to update the property.
     */
    public void setSize(String pSize){
        this.mSize = pSize;
    }
    /**
     * Retrieves the Size property.
     *
     * @return
     *  String containing the Size property.
     */
    public String getSize(){
        return mSize;
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


    int itemId;
    
    private String maxOrderQty="";
    public String getMaxOrderQty(){
    	return maxOrderQty;
    }

	public void setMaxOrderQty(String maxOrderQty) {
		if(maxOrderQty == null){
			maxOrderQty = "";
		}
		maxOrderQty = maxOrderQty.trim();
		if(!this.maxOrderQty.equals(maxOrderQty)){
			setDirty(true);
			this.maxOrderQty = maxOrderQty;
		}
	}

	private String restrictionDays="";

	public String getRestrictionDays() {
		if(restrictionDays == null){
			restrictionDays = "";
		}
		restrictionDays = restrictionDays.trim();
		return restrictionDays;
	}

	public void setRestrictionDays(String restrictionDays) {
		if(!this.restrictionDays.equals(restrictionDays)){
			setDirty(true);
			this.restrictionDays = restrictionDays;
		}

	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

}
