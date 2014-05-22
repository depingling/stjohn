
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ContractItemSubstView
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
 * <code>ContractItemSubstView</code> is a ViewObject class for UI.
 */
public class ContractItemSubstView
extends ValueObject
{
   
    private static final long serialVersionUID = 8423743644034543549L;
    private int mContractItemSubstId;
    private int mItemId;
    private int mItemSku;
    private String mItemDesc;
    private String mItemUom;
    private String mItemPack;
    private String mItemSize;
    private int mItemMfgId;
    private String mItemMfgName;
    private String mItemMfgSku;
    private int mContractId;
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
    private String mSustStatusCd;

    /**
     * Constructor.
     */
    public ContractItemSubstView ()
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
        mSustStatusCd = "";
    }

    /**
     * Constructor. 
     */
    public ContractItemSubstView(int parm1, int parm2, int parm3, String parm4, String parm5, String parm6, String parm7, int parm8, String parm9, String parm10, int parm11, String parm12, int parm13, int parm14, String parm15, String parm16, String parm17, String parm18, int parm19, String parm20, String parm21, String parm22)
    {
        mContractItemSubstId = parm1;
        mItemId = parm2;
        mItemSku = parm3;
        mItemDesc = parm4;
        mItemUom = parm5;
        mItemPack = parm6;
        mItemSize = parm7;
        mItemMfgId = parm8;
        mItemMfgName = parm9;
        mItemMfgSku = parm10;
        mContractId = parm11;
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
        mSustStatusCd = parm22;
        
    }

    /**
     * Creates a new ContractItemSubstView
     *
     * @return
     *  Newly initialized ContractItemSubstView object.
     */
    public static ContractItemSubstView createValue () 
    {
        ContractItemSubstView valueView = new ContractItemSubstView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ContractItemSubstView object
     */
    public String toString()
    {
        return "[" + "ContractItemSubstId=" + mContractItemSubstId + ", ItemId=" + mItemId + ", ItemSku=" + mItemSku + ", ItemDesc=" + mItemDesc + ", ItemUom=" + mItemUom + ", ItemPack=" + mItemPack + ", ItemSize=" + mItemSize + ", ItemMfgId=" + mItemMfgId + ", ItemMfgName=" + mItemMfgName + ", ItemMfgSku=" + mItemMfgSku + ", ContractId=" + mContractId + ", ContractName=" + mContractName + ", SubstItemId=" + mSubstItemId + ", SubstItemSku=" + mSubstItemSku + ", SubstItemDesc=" + mSubstItemDesc + ", SubstItemUom=" + mSubstItemUom + ", SubstItemPack=" + mSubstItemPack + ", SubstItemSize=" + mSubstItemSize + ", SubstItemMfgId=" + mSubstItemMfgId + ", SubstItemMfgName=" + mSubstItemMfgName + ", SubstItemMfgSku=" + mSubstItemMfgSku + ", SustStatusCd=" + mSustStatusCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("ContractItemSubst");
	root.setAttribute("Id", String.valueOf(mContractItemSubstId));

	Element node;

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

        node = doc.createElement("ContractId");
        node.appendChild(doc.createTextNode(String.valueOf(mContractId)));
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

        node = doc.createElement("SustStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mSustStatusCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ContractItemSubstView copy()  {
      ContractItemSubstView obj = new ContractItemSubstView();
      obj.setContractItemSubstId(mContractItemSubstId);
      obj.setItemId(mItemId);
      obj.setItemSku(mItemSku);
      obj.setItemDesc(mItemDesc);
      obj.setItemUom(mItemUom);
      obj.setItemPack(mItemPack);
      obj.setItemSize(mItemSize);
      obj.setItemMfgId(mItemMfgId);
      obj.setItemMfgName(mItemMfgName);
      obj.setItemMfgSku(mItemMfgSku);
      obj.setContractId(mContractId);
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
      obj.setSustStatusCd(mSustStatusCd);
      
      return obj;
    }

    
    /**
     * Sets the ContractItemSubstId property.
     *
     * @param pContractItemSubstId
     *  int to use to update the property.
     */
    public void setContractItemSubstId(int pContractItemSubstId){
        this.mContractItemSubstId = pContractItemSubstId;
    }
    /**
     * Retrieves the ContractItemSubstId property.
     *
     * @return
     *  int containing the ContractItemSubstId property.
     */
    public int getContractItemSubstId(){
        return mContractItemSubstId;
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
     * Sets the SustStatusCd property.
     *
     * @param pSustStatusCd
     *  String to use to update the property.
     */
    public void setSustStatusCd(String pSustStatusCd){
        this.mSustStatusCd = pSustStatusCd;
    }
    /**
     * Retrieves the SustStatusCd property.
     *
     * @return
     *  String containing the SustStatusCd property.
     */
    public String getSustStatusCd(){
        return mSustStatusCd;
    }


    
}
