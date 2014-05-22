
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ShoppingRestrictionsView
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
 * <code>ShoppingRestrictionsView</code> is a ViewObject class for UI.
 */
public class ShoppingRestrictionsView
extends ValueObject
{
   
    private static final long serialVersionUID = -7702309423048233045L;
    private int mShoppingControlId;
    private int mItemId;
    private int mAccountId;
    private int mSiteId;
    private String mItemSkuNum;
    private String mItemShortDesc;
    private String mItemSize;
    private String mItemUOM;
    private String mItemPack;
    private String mSiteMaxOrderQty;
    private String mAccountMaxOrderQty;
    private String mRestrictionDays;
    private String mControlStatusCd;
    private String mSiteControlModBy;
    private java.util.Date mSiteControlModDate;
    private String mAcctControlModBy;
    private java.util.Date mAcctControlModDate;
    private int mTag;

    /**
     * Constructor.
     */
    public ShoppingRestrictionsView ()
    {
        mItemSkuNum = "";
        mItemShortDesc = "";
        mItemSize = "";
        mItemUOM = "";
        mItemPack = "";
        mSiteMaxOrderQty = "";
        mAccountMaxOrderQty = "";
        mRestrictionDays = "";
        mControlStatusCd = "";
        mSiteControlModBy = "";
        mAcctControlModBy = "";
    }

    /**
     * Constructor. 
     */
    public ShoppingRestrictionsView(int parm1, int parm2, int parm3, int parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13, String parm14, java.util.Date parm15, String parm16, java.util.Date parm17, int parm18)
    {
        mShoppingControlId = parm1;
        mItemId = parm2;
        mAccountId = parm3;
        mSiteId = parm4;
        mItemSkuNum = parm5;
        mItemShortDesc = parm6;
        mItemSize = parm7;
        mItemUOM = parm8;
        mItemPack = parm9;
        mSiteMaxOrderQty = parm10;
        mAccountMaxOrderQty = parm11;
        mRestrictionDays = parm12;
        mControlStatusCd = parm13;
        mSiteControlModBy = parm14;
        mSiteControlModDate = parm15;
        mAcctControlModBy = parm16;
        mAcctControlModDate = parm17;
        mTag = parm18;
        
    }

    /**
     * Creates a new ShoppingRestrictionsView
     *
     * @return
     *  Newly initialized ShoppingRestrictionsView object.
     */
    public static ShoppingRestrictionsView createValue () 
    {
        ShoppingRestrictionsView valueView = new ShoppingRestrictionsView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ShoppingRestrictionsView object
     */
    public String toString()
    {
        return "[" + "ShoppingControlId=" + mShoppingControlId + ", ItemId=" + mItemId + ", AccountId=" + mAccountId + ", SiteId=" + mSiteId + ", ItemSkuNum=" + mItemSkuNum + ", ItemShortDesc=" + mItemShortDesc + ", ItemSize=" + mItemSize + ", ItemUOM=" + mItemUOM + ", ItemPack=" + mItemPack + ", SiteMaxOrderQty=" + mSiteMaxOrderQty + ", AccountMaxOrderQty=" + mAccountMaxOrderQty + ", RestrictionDays=" + mRestrictionDays + ", ControlStatusCd=" + mControlStatusCd + ", SiteControlModBy=" + mSiteControlModBy + ", SiteControlModDate=" + mSiteControlModDate + ", AcctControlModBy=" + mAcctControlModBy + ", AcctControlModDate=" + mAcctControlModDate + ", Tag=" + mTag + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ShoppingRestrictions");
	root.setAttribute("Id", String.valueOf(mShoppingControlId));

	Element node;

        node = doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node = doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node = doc.createElement("SiteId");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteId)));
        root.appendChild(node);

        node = doc.createElement("ItemSkuNum");
        node.appendChild(doc.createTextNode(String.valueOf(mItemSkuNum)));
        root.appendChild(node);

        node = doc.createElement("ItemShortDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mItemShortDesc)));
        root.appendChild(node);

        node = doc.createElement("ItemSize");
        node.appendChild(doc.createTextNode(String.valueOf(mItemSize)));
        root.appendChild(node);

        node = doc.createElement("ItemUOM");
        node.appendChild(doc.createTextNode(String.valueOf(mItemUOM)));
        root.appendChild(node);

        node = doc.createElement("ItemPack");
        node.appendChild(doc.createTextNode(String.valueOf(mItemPack)));
        root.appendChild(node);

        node = doc.createElement("SiteMaxOrderQty");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteMaxOrderQty)));
        root.appendChild(node);

        node = doc.createElement("AccountMaxOrderQty");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountMaxOrderQty)));
        root.appendChild(node);

        node = doc.createElement("RestrictionDays");
        node.appendChild(doc.createTextNode(String.valueOf(mRestrictionDays)));
        root.appendChild(node);

        node = doc.createElement("ControlStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mControlStatusCd)));
        root.appendChild(node);

        node = doc.createElement("SiteControlModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteControlModBy)));
        root.appendChild(node);

        node = doc.createElement("SiteControlModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mSiteControlModDate)));
        root.appendChild(node);

        node = doc.createElement("AcctControlModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAcctControlModBy)));
        root.appendChild(node);

        node = doc.createElement("AcctControlModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAcctControlModDate)));
        root.appendChild(node);

        node = doc.createElement("Tag");
        node.appendChild(doc.createTextNode(String.valueOf(mTag)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ShoppingRestrictionsView copy()  {
      ShoppingRestrictionsView obj = new ShoppingRestrictionsView();
      obj.setShoppingControlId(mShoppingControlId);
      obj.setItemId(mItemId);
      obj.setAccountId(mAccountId);
      obj.setSiteId(mSiteId);
      obj.setItemSkuNum(mItemSkuNum);
      obj.setItemShortDesc(mItemShortDesc);
      obj.setItemSize(mItemSize);
      obj.setItemUOM(mItemUOM);
      obj.setItemPack(mItemPack);
      obj.setSiteMaxOrderQty(mSiteMaxOrderQty);
      obj.setAccountMaxOrderQty(mAccountMaxOrderQty);
      obj.setRestrictionDays(mRestrictionDays);
      obj.setControlStatusCd(mControlStatusCd);
      obj.setSiteControlModBy(mSiteControlModBy);
      obj.setSiteControlModDate(mSiteControlModDate);
      obj.setAcctControlModBy(mAcctControlModBy);
      obj.setAcctControlModDate(mAcctControlModDate);
      obj.setTag(mTag);
      
      return obj;
    }

    
    /**
     * Sets the ShoppingControlId property.
     *
     * @param pShoppingControlId
     *  int to use to update the property.
     */
    public void setShoppingControlId(int pShoppingControlId){
        this.mShoppingControlId = pShoppingControlId;
    }
    /**
     * Retrieves the ShoppingControlId property.
     *
     * @return
     *  int containing the ShoppingControlId property.
     */
    public int getShoppingControlId(){
        return mShoppingControlId;
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
     * Sets the AccountId property.
     *
     * @param pAccountId
     *  int to use to update the property.
     */
    public void setAccountId(int pAccountId){
        this.mAccountId = pAccountId;
    }
    /**
     * Retrieves the AccountId property.
     *
     * @return
     *  int containing the AccountId property.
     */
    public int getAccountId(){
        return mAccountId;
    }


    /**
     * Sets the SiteId property.
     *
     * @param pSiteId
     *  int to use to update the property.
     */
    public void setSiteId(int pSiteId){
        this.mSiteId = pSiteId;
    }
    /**
     * Retrieves the SiteId property.
     *
     * @return
     *  int containing the SiteId property.
     */
    public int getSiteId(){
        return mSiteId;
    }


    /**
     * Sets the ItemSkuNum property.
     *
     * @param pItemSkuNum
     *  String to use to update the property.
     */
    public void setItemSkuNum(String pItemSkuNum){
        this.mItemSkuNum = pItemSkuNum;
    }
    /**
     * Retrieves the ItemSkuNum property.
     *
     * @return
     *  String containing the ItemSkuNum property.
     */
    public String getItemSkuNum(){
        return mItemSkuNum;
    }


    /**
     * Sets the ItemShortDesc property.
     *
     * @param pItemShortDesc
     *  String to use to update the property.
     */
    public void setItemShortDesc(String pItemShortDesc){
        this.mItemShortDesc = pItemShortDesc;
    }
    /**
     * Retrieves the ItemShortDesc property.
     *
     * @return
     *  String containing the ItemShortDesc property.
     */
    public String getItemShortDesc(){
        return mItemShortDesc;
    }


    /**
     * Sets the ItemSize property.
     *
     * @param pItemSize
     *  String to use to update the property.
     */
    public void setItemSize(String pItemSize){
        this.mItemSize = pItemSize;
    }
    /**
     * Retrieves the ItemSize property.
     *
     * @return
     *  String containing the ItemSize property.
     */
    public String getItemSize(){
        return mItemSize;
    }


    /**
     * Sets the ItemUOM property.
     *
     * @param pItemUOM
     *  String to use to update the property.
     */
    public void setItemUOM(String pItemUOM){
        this.mItemUOM = pItemUOM;
    }
    /**
     * Retrieves the ItemUOM property.
     *
     * @return
     *  String containing the ItemUOM property.
     */
    public String getItemUOM(){
        return mItemUOM;
    }


    /**
     * Sets the ItemPack property.
     *
     * @param pItemPack
     *  String to use to update the property.
     */
    public void setItemPack(String pItemPack){
        this.mItemPack = pItemPack;
    }
    /**
     * Retrieves the ItemPack property.
     *
     * @return
     *  String containing the ItemPack property.
     */
    public String getItemPack(){
        return mItemPack;
    }


    /**
     * Sets the SiteMaxOrderQty property.
     *
     * @param pSiteMaxOrderQty
     *  String to use to update the property.
     */
    public void setSiteMaxOrderQty(String pSiteMaxOrderQty){
        this.mSiteMaxOrderQty = pSiteMaxOrderQty;
    }
    /**
     * Retrieves the SiteMaxOrderQty property.
     *
     * @return
     *  String containing the SiteMaxOrderQty property.
     */
    public String getSiteMaxOrderQty(){
        return mSiteMaxOrderQty;
    }


    /**
     * Sets the AccountMaxOrderQty property.
     *
     * @param pAccountMaxOrderQty
     *  String to use to update the property.
     */
    public void setAccountMaxOrderQty(String pAccountMaxOrderQty){
        this.mAccountMaxOrderQty = pAccountMaxOrderQty;
    }
    /**
     * Retrieves the AccountMaxOrderQty property.
     *
     * @return
     *  String containing the AccountMaxOrderQty property.
     */
    public String getAccountMaxOrderQty(){
        return mAccountMaxOrderQty;
    }


    /**
     * Sets the RestrictionDays property.
     *
     * @param pRestrictionDays
     *  String to use to update the property.
     */
    public void setRestrictionDays(String pRestrictionDays){
        this.mRestrictionDays = pRestrictionDays;
    }
    /**
     * Retrieves the RestrictionDays property.
     *
     * @return
     *  String containing the RestrictionDays property.
     */
    public String getRestrictionDays(){
        return mRestrictionDays;
    }


    /**
     * Sets the ControlStatusCd property.
     *
     * @param pControlStatusCd
     *  String to use to update the property.
     */
    public void setControlStatusCd(String pControlStatusCd){
        this.mControlStatusCd = pControlStatusCd;
    }
    /**
     * Retrieves the ControlStatusCd property.
     *
     * @return
     *  String containing the ControlStatusCd property.
     */
    public String getControlStatusCd(){
        return mControlStatusCd;
    }


    /**
     * Sets the SiteControlModBy property.
     *
     * @param pSiteControlModBy
     *  String to use to update the property.
     */
    public void setSiteControlModBy(String pSiteControlModBy){
        this.mSiteControlModBy = pSiteControlModBy;
    }
    /**
     * Retrieves the SiteControlModBy property.
     *
     * @return
     *  String containing the SiteControlModBy property.
     */
    public String getSiteControlModBy(){
        return mSiteControlModBy;
    }


    /**
     * Sets the SiteControlModDate property.
     *
     * @param pSiteControlModDate
     *  java.util.Date to use to update the property.
     */
    public void setSiteControlModDate(java.util.Date pSiteControlModDate){
        this.mSiteControlModDate = pSiteControlModDate;
    }
    /**
     * Retrieves the SiteControlModDate property.
     *
     * @return
     *  java.util.Date containing the SiteControlModDate property.
     */
    public java.util.Date getSiteControlModDate(){
        return mSiteControlModDate;
    }


    /**
     * Sets the AcctControlModBy property.
     *
     * @param pAcctControlModBy
     *  String to use to update the property.
     */
    public void setAcctControlModBy(String pAcctControlModBy){
        this.mAcctControlModBy = pAcctControlModBy;
    }
    /**
     * Retrieves the AcctControlModBy property.
     *
     * @return
     *  String containing the AcctControlModBy property.
     */
    public String getAcctControlModBy(){
        return mAcctControlModBy;
    }


    /**
     * Sets the AcctControlModDate property.
     *
     * @param pAcctControlModDate
     *  java.util.Date to use to update the property.
     */
    public void setAcctControlModDate(java.util.Date pAcctControlModDate){
        this.mAcctControlModDate = pAcctControlModDate;
    }
    /**
     * Retrieves the AcctControlModDate property.
     *
     * @return
     *  java.util.Date containing the AcctControlModDate property.
     */
    public java.util.Date getAcctControlModDate(){
        return mAcctControlModDate;
    }


    /**
     * Sets the Tag property.
     *
     * @param pTag
     *  int to use to update the property.
     */
    public void setTag(int pTag){
        this.mTag = pTag;
    }
    /**
     * Retrieves the Tag property.
     *
     * @return
     *  int containing the Tag property.
     */
    public int getTag(){
        return mTag;
    }


    
}
