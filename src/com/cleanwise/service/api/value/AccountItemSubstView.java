
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AccountItemSubstView
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

import java.util.Vector;


/**
 * <code>AccountItemSubstView</code> is a ViewObject class for UI.
 */
public class AccountItemSubstView
extends ValueObject
{
   
    private static final long serialVersionUID = -602282054986181057L;
    private int mItemSubstitutionDefId;
    private int mAccountId;
    private int mItemId;
    private int mItemSku;
    private String mItemDesc;
    private String mItemUom;
    private String mItemPack;
    private String mItemSize;
    private int mItemMfgId;
    private String mItemMfgName;
    private String mItemMfgSku;
    private String mContractName;
    private int mSubstItemId;
    private int mSubstItemSku;
    private String mSubstItemDesc;
    private String mSubstItemUom;
    private String mSubstItemPack;
    private String mSubstItemSize;
    private int mSubstItemMfgId;
    private String mSubstItemMfgName;
    private String mSubstItemMfgSku;
    private String mSubstStatusCd;
    private java.math.BigDecimal mUomConversionFactor;

    /**
     * Constructor.
     */
    public AccountItemSubstView ()
    {
        mItemDesc = "";
        mItemUom = "";
        mItemPack = "";
        mItemSize = "";
        mItemMfgName = "";
        mItemMfgSku = "";
        mContractName = "";
        mSubstItemDesc = "";
        mSubstItemUom = "";
        mSubstItemPack = "";
        mSubstItemSize = "";
        mSubstItemMfgName = "";
        mSubstItemMfgSku = "";
        mSubstStatusCd = "";
    }

    /**
     * Constructor. 
     */
    public AccountItemSubstView(int parm1, int parm2, int parm3, int parm4, String parm5, String parm6, String parm7, String parm8, int parm9, String parm10, String parm11, String parm12, int parm13, int parm14, String parm15, String parm16, String parm17, String parm18, int parm19, String parm20, String parm21, String parm22, java.math.BigDecimal parm23)
    {
        mItemSubstitutionDefId = parm1;
        mAccountId = parm2;
        mItemId = parm3;
        mItemSku = parm4;
        mItemDesc = parm5;
        mItemUom = parm6;
        mItemPack = parm7;
        mItemSize = parm8;
        mItemMfgId = parm9;
        mItemMfgName = parm10;
        mItemMfgSku = parm11;
        mContractName = parm12;
        mSubstItemId = parm13;
        mSubstItemSku = parm14;
        mSubstItemDesc = parm15;
        mSubstItemUom = parm16;
        mSubstItemPack = parm17;
        mSubstItemSize = parm18;
        mSubstItemMfgId = parm19;
        mSubstItemMfgName = parm20;
        mSubstItemMfgSku = parm21;
        mSubstStatusCd = parm22;
        mUomConversionFactor = parm23;
        
    }

    /**
     * Creates a new AccountItemSubstView
     *
     * @return
     *  Newly initialized AccountItemSubstView object.
     */
    public static AccountItemSubstView createValue () 
    {
        AccountItemSubstView valueView = new AccountItemSubstView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AccountItemSubstView object
     */
    public String toString()
    {
        return "[" + "ItemSubstitutionDefId=" + mItemSubstitutionDefId + ", AccountId=" + mAccountId + ", ItemId=" + mItemId + ", ItemSku=" + mItemSku + ", ItemDesc=" + mItemDesc + ", ItemUom=" + mItemUom + ", ItemPack=" + mItemPack + ", ItemSize=" + mItemSize + ", ItemMfgId=" + mItemMfgId + ", ItemMfgName=" + mItemMfgName + ", ItemMfgSku=" + mItemMfgSku + ", ContractName=" + mContractName + ", SubstItemId=" + mSubstItemId + ", SubstItemSku=" + mSubstItemSku + ", SubstItemDesc=" + mSubstItemDesc + ", SubstItemUom=" + mSubstItemUom + ", SubstItemPack=" + mSubstItemPack + ", SubstItemSize=" + mSubstItemSize + ", SubstItemMfgId=" + mSubstItemMfgId + ", SubstItemMfgName=" + mSubstItemMfgName + ", SubstItemMfgSku=" + mSubstItemMfgSku + ", SubstStatusCd=" + mSubstStatusCd + ", UomConversionFactor=" + mUomConversionFactor + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("AccountItemSubst");
	root.setAttribute("Id", String.valueOf(mItemSubstitutionDefId));

	Element node;

        node = doc.createElement("AccountId");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountId)));
        root.appendChild(node);

        node = doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node = doc.createElement("ItemSku");
        node.appendChild(doc.createTextNode(String.valueOf(mItemSku)));
        root.appendChild(node);

        node = doc.createElement("ItemDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mItemDesc)));
        root.appendChild(node);

        node = doc.createElement("ItemUom");
        node.appendChild(doc.createTextNode(String.valueOf(mItemUom)));
        root.appendChild(node);

        node = doc.createElement("ItemPack");
        node.appendChild(doc.createTextNode(String.valueOf(mItemPack)));
        root.appendChild(node);

        node = doc.createElement("ItemSize");
        node.appendChild(doc.createTextNode(String.valueOf(mItemSize)));
        root.appendChild(node);

        node = doc.createElement("ItemMfgId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMfgId)));
        root.appendChild(node);

        node = doc.createElement("ItemMfgName");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMfgName)));
        root.appendChild(node);

        node = doc.createElement("ItemMfgSku");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMfgSku)));
        root.appendChild(node);

        node = doc.createElement("ContractName");
        node.appendChild(doc.createTextNode(String.valueOf(mContractName)));
        root.appendChild(node);

        node = doc.createElement("SubstItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mSubstItemId)));
        root.appendChild(node);

        node = doc.createElement("SubstItemSku");
        node.appendChild(doc.createTextNode(String.valueOf(mSubstItemSku)));
        root.appendChild(node);

        node = doc.createElement("SubstItemDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mSubstItemDesc)));
        root.appendChild(node);

        node = doc.createElement("SubstItemUom");
        node.appendChild(doc.createTextNode(String.valueOf(mSubstItemUom)));
        root.appendChild(node);

        node = doc.createElement("SubstItemPack");
        node.appendChild(doc.createTextNode(String.valueOf(mSubstItemPack)));
        root.appendChild(node);

        node = doc.createElement("SubstItemSize");
        node.appendChild(doc.createTextNode(String.valueOf(mSubstItemSize)));
        root.appendChild(node);

        node = doc.createElement("SubstItemMfgId");
        node.appendChild(doc.createTextNode(String.valueOf(mSubstItemMfgId)));
        root.appendChild(node);

        node = doc.createElement("SubstItemMfgName");
        node.appendChild(doc.createTextNode(String.valueOf(mSubstItemMfgName)));
        root.appendChild(node);

        node = doc.createElement("SubstItemMfgSku");
        node.appendChild(doc.createTextNode(String.valueOf(mSubstItemMfgSku)));
        root.appendChild(node);

        node = doc.createElement("SubstStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mSubstStatusCd)));
        root.appendChild(node);

        node = doc.createElement("UomConversionFactor");
        node.appendChild(doc.createTextNode(String.valueOf(mUomConversionFactor)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public AccountItemSubstView copy()  {
      AccountItemSubstView obj = new AccountItemSubstView();
      obj.setItemSubstitutionDefId(mItemSubstitutionDefId);
      obj.setAccountId(mAccountId);
      obj.setItemId(mItemId);
      obj.setItemSku(mItemSku);
      obj.setItemDesc(mItemDesc);
      obj.setItemUom(mItemUom);
      obj.setItemPack(mItemPack);
      obj.setItemSize(mItemSize);
      obj.setItemMfgId(mItemMfgId);
      obj.setItemMfgName(mItemMfgName);
      obj.setItemMfgSku(mItemMfgSku);
      obj.setContractName(mContractName);
      obj.setSubstItemId(mSubstItemId);
      obj.setSubstItemSku(mSubstItemSku);
      obj.setSubstItemDesc(mSubstItemDesc);
      obj.setSubstItemUom(mSubstItemUom);
      obj.setSubstItemPack(mSubstItemPack);
      obj.setSubstItemSize(mSubstItemSize);
      obj.setSubstItemMfgId(mSubstItemMfgId);
      obj.setSubstItemMfgName(mSubstItemMfgName);
      obj.setSubstItemMfgSku(mSubstItemMfgSku);
      obj.setSubstStatusCd(mSubstStatusCd);
      obj.setUomConversionFactor(mUomConversionFactor);
      
      return obj;
    }

    
    /**
     * Sets the ItemSubstitutionDefId property.
     *
     * @param pItemSubstitutionDefId
     *  int to use to update the property.
     */
    public void setItemSubstitutionDefId(int pItemSubstitutionDefId){
        this.mItemSubstitutionDefId = pItemSubstitutionDefId;
    }
    /**
     * Retrieves the ItemSubstitutionDefId property.
     *
     * @return
     *  int containing the ItemSubstitutionDefId property.
     */
    public int getItemSubstitutionDefId(){
        return mItemSubstitutionDefId;
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
     * Sets the ItemSku property.
     *
     * @param pItemSku
     *  int to use to update the property.
     */
    public void setItemSku(int pItemSku){
        this.mItemSku = pItemSku;
    }
    /**
     * Retrieves the ItemSku property.
     *
     * @return
     *  int containing the ItemSku property.
     */
    public int getItemSku(){
        return mItemSku;
    }


    /**
     * Sets the ItemDesc property.
     *
     * @param pItemDesc
     *  String to use to update the property.
     */
    public void setItemDesc(String pItemDesc){
        this.mItemDesc = pItemDesc;
    }
    /**
     * Retrieves the ItemDesc property.
     *
     * @return
     *  String containing the ItemDesc property.
     */
    public String getItemDesc(){
        return mItemDesc;
    }


    /**
     * Sets the ItemUom property.
     *
     * @param pItemUom
     *  String to use to update the property.
     */
    public void setItemUom(String pItemUom){
        this.mItemUom = pItemUom;
    }
    /**
     * Retrieves the ItemUom property.
     *
     * @return
     *  String containing the ItemUom property.
     */
    public String getItemUom(){
        return mItemUom;
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
     * Sets the ItemMfgId property.
     *
     * @param pItemMfgId
     *  int to use to update the property.
     */
    public void setItemMfgId(int pItemMfgId){
        this.mItemMfgId = pItemMfgId;
    }
    /**
     * Retrieves the ItemMfgId property.
     *
     * @return
     *  int containing the ItemMfgId property.
     */
    public int getItemMfgId(){
        return mItemMfgId;
    }


    /**
     * Sets the ItemMfgName property.
     *
     * @param pItemMfgName
     *  String to use to update the property.
     */
    public void setItemMfgName(String pItemMfgName){
        this.mItemMfgName = pItemMfgName;
    }
    /**
     * Retrieves the ItemMfgName property.
     *
     * @return
     *  String containing the ItemMfgName property.
     */
    public String getItemMfgName(){
        return mItemMfgName;
    }


    /**
     * Sets the ItemMfgSku property.
     *
     * @param pItemMfgSku
     *  String to use to update the property.
     */
    public void setItemMfgSku(String pItemMfgSku){
        this.mItemMfgSku = pItemMfgSku;
    }
    /**
     * Retrieves the ItemMfgSku property.
     *
     * @return
     *  String containing the ItemMfgSku property.
     */
    public String getItemMfgSku(){
        return mItemMfgSku;
    }


    /**
     * Sets the ContractName property.
     *
     * @param pContractName
     *  String to use to update the property.
     */
    public void setContractName(String pContractName){
        this.mContractName = pContractName;
    }
    /**
     * Retrieves the ContractName property.
     *
     * @return
     *  String containing the ContractName property.
     */
    public String getContractName(){
        return mContractName;
    }


    /**
     * Sets the SubstItemId property.
     *
     * @param pSubstItemId
     *  int to use to update the property.
     */
    public void setSubstItemId(int pSubstItemId){
        this.mSubstItemId = pSubstItemId;
    }
    /**
     * Retrieves the SubstItemId property.
     *
     * @return
     *  int containing the SubstItemId property.
     */
    public int getSubstItemId(){
        return mSubstItemId;
    }


    /**
     * Sets the SubstItemSku property.
     *
     * @param pSubstItemSku
     *  int to use to update the property.
     */
    public void setSubstItemSku(int pSubstItemSku){
        this.mSubstItemSku = pSubstItemSku;
    }
    /**
     * Retrieves the SubstItemSku property.
     *
     * @return
     *  int containing the SubstItemSku property.
     */
    public int getSubstItemSku(){
        return mSubstItemSku;
    }


    /**
     * Sets the SubstItemDesc property.
     *
     * @param pSubstItemDesc
     *  String to use to update the property.
     */
    public void setSubstItemDesc(String pSubstItemDesc){
        this.mSubstItemDesc = pSubstItemDesc;
    }
    /**
     * Retrieves the SubstItemDesc property.
     *
     * @return
     *  String containing the SubstItemDesc property.
     */
    public String getSubstItemDesc(){
        return mSubstItemDesc;
    }


    /**
     * Sets the SubstItemUom property.
     *
     * @param pSubstItemUom
     *  String to use to update the property.
     */
    public void setSubstItemUom(String pSubstItemUom){
        this.mSubstItemUom = pSubstItemUom;
    }
    /**
     * Retrieves the SubstItemUom property.
     *
     * @return
     *  String containing the SubstItemUom property.
     */
    public String getSubstItemUom(){
        return mSubstItemUom;
    }


    /**
     * Sets the SubstItemPack property.
     *
     * @param pSubstItemPack
     *  String to use to update the property.
     */
    public void setSubstItemPack(String pSubstItemPack){
        this.mSubstItemPack = pSubstItemPack;
    }
    /**
     * Retrieves the SubstItemPack property.
     *
     * @return
     *  String containing the SubstItemPack property.
     */
    public String getSubstItemPack(){
        return mSubstItemPack;
    }


    /**
     * Sets the SubstItemSize property.
     *
     * @param pSubstItemSize
     *  String to use to update the property.
     */
    public void setSubstItemSize(String pSubstItemSize){
        this.mSubstItemSize = pSubstItemSize;
    }
    /**
     * Retrieves the SubstItemSize property.
     *
     * @return
     *  String containing the SubstItemSize property.
     */
    public String getSubstItemSize(){
        return mSubstItemSize;
    }


    /**
     * Sets the SubstItemMfgId property.
     *
     * @param pSubstItemMfgId
     *  int to use to update the property.
     */
    public void setSubstItemMfgId(int pSubstItemMfgId){
        this.mSubstItemMfgId = pSubstItemMfgId;
    }
    /**
     * Retrieves the SubstItemMfgId property.
     *
     * @return
     *  int containing the SubstItemMfgId property.
     */
    public int getSubstItemMfgId(){
        return mSubstItemMfgId;
    }


    /**
     * Sets the SubstItemMfgName property.
     *
     * @param pSubstItemMfgName
     *  String to use to update the property.
     */
    public void setSubstItemMfgName(String pSubstItemMfgName){
        this.mSubstItemMfgName = pSubstItemMfgName;
    }
    /**
     * Retrieves the SubstItemMfgName property.
     *
     * @return
     *  String containing the SubstItemMfgName property.
     */
    public String getSubstItemMfgName(){
        return mSubstItemMfgName;
    }


    /**
     * Sets the SubstItemMfgSku property.
     *
     * @param pSubstItemMfgSku
     *  String to use to update the property.
     */
    public void setSubstItemMfgSku(String pSubstItemMfgSku){
        this.mSubstItemMfgSku = pSubstItemMfgSku;
    }
    /**
     * Retrieves the SubstItemMfgSku property.
     *
     * @return
     *  String containing the SubstItemMfgSku property.
     */
    public String getSubstItemMfgSku(){
        return mSubstItemMfgSku;
    }


    /**
     * Sets the SubstStatusCd property.
     *
     * @param pSubstStatusCd
     *  String to use to update the property.
     */
    public void setSubstStatusCd(String pSubstStatusCd){
        this.mSubstStatusCd = pSubstStatusCd;
    }
    /**
     * Retrieves the SubstStatusCd property.
     *
     * @return
     *  String containing the SubstStatusCd property.
     */
    public String getSubstStatusCd(){
        return mSubstStatusCd;
    }


    /**
     * Sets the UomConversionFactor property.
     *
     * @param pUomConversionFactor
     *  java.math.BigDecimal to use to update the property.
     */
    public void setUomConversionFactor(java.math.BigDecimal pUomConversionFactor){
        this.mUomConversionFactor = pUomConversionFactor;
    }
    /**
     * Retrieves the UomConversionFactor property.
     *
     * @return
     *  java.math.BigDecimal containing the UomConversionFactor property.
     */
    public java.math.BigDecimal getUomConversionFactor(){
        return mUomConversionFactor;
    }


    
}
