
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        DistItemView
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
import java.math.BigDecimal;
import org.w3c.dom.*;

/**
 * <code>DistItemView</code> is a ViewObject class for UI.
 */
public class DistItemView
extends ValueObject
{
   
    private static final long serialVersionUID = -6255687882624587720L;
    private int mItemId;
    private int mItemMfgMappingId;
    private int mItemDistMappingId;
    private int mItemMfg1MappingId;
    private int mItemMappingAssocId;
    private int mSku;
    private String mName;
    private String mSize;
    private String mPack;
    private String mUom;
    private int mMfgId;
    private String mMfgName;
    private String mMfgItemSku;
    private int mDistId;
    private String mDistName;
    private String mDistItemSku;
    private String mDistItemPack;
    private String mDistItemUom;
    private int mMfg1Id;
    private String mMfg1Name;
    private String mMfg1ItemSku;
    private BigDecimal mDistUomConvMultiplier;
    private int mMult1Id;
    private String mMult1Name;

    /**
     * Constructor.
     */
    public DistItemView ()
    {
        mName = "";
        mSize = "";
        mPack = "";
        mUom = "";
        mMfgName = "";
        mMfgItemSku = "";
        mDistName = "";
        mDistItemSku = "";
        mDistItemPack = "";
        mDistItemUom = "";
        mMfg1Name = "";
        mMfg1ItemSku = "";
    }

    /**
     * Constructor. 
     */
    public DistItemView(int parm1, int parm2, int parm3, int parm4, int parm5, int parm6, String parm7, String parm8, String parm9, String parm10, int parm11, String parm12, String parm13, int parm14, String parm15, String parm16, String parm17, String parm18, int parm19, String parm20, String parm21, BigDecimal parm22, int parm23, String parm24)
    {
        mItemId = parm1;
        mItemMfgMappingId = parm2;
        mItemDistMappingId = parm3;
        mItemMfg1MappingId = parm4;
        mItemMappingAssocId = parm5;
        mSku = parm6;
        mName = parm7;
        mSize = parm8;
        mPack = parm9;
        mUom = parm10;
        mMfgId = parm11;
        mMfgName = parm12;
        mMfgItemSku = parm13;
        mDistId = parm14;
        mDistName = parm15;
        mDistItemSku = parm16;
        mDistItemPack = parm17;
        mDistItemUom = parm18;
        mMfg1Id = parm19;
        mMfg1Name = parm20;
        mMfg1ItemSku = parm21;
        mDistUomConvMultiplier = parm22;
        mMult1Id = parm23;
        mMult1Name = parm24;
    }

    /**
     * Creates a new DistItemView
     *
     * @return
     *  Newly initialized DistItemView object.
     */
    public static DistItemView createValue () 
    {
        DistItemView valueView = new DistItemView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this DistItemView object
     */
    public String toString()
    {
        return "[" + "ItemId=" + mItemId + ", ItemMfgMappingId=" + mItemMfgMappingId + ", ItemDistMappingId=" + mItemDistMappingId + ", ItemMfg1MappingId=" + mItemMfg1MappingId + ", ItemMappingAssocId=" + mItemMappingAssocId + ", Sku=" + mSku + ", Name=" + mName + ", Size=" + mSize + ", Pack=" + mPack + ", Uom=" + mUom + ", MfgId=" + mMfgId + ", MfgName=" + mMfgName + ", MfgItemSku=" + mMfgItemSku + ", DistId=" + mDistId + ", DistName=" + mDistName + ", DistItemSku=" + mDistItemSku + ", DistItemPack=" + mDistItemPack + ", DistItemUom=" + mDistItemUom + ", Mfg1Id=" + mMfg1Id + ", Mfg1Name=" + mMfg1Name + ", Mfg1ItemSku=" + mMfg1ItemSku + ", DistUomConvMultiplier=" + mDistUomConvMultiplier + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("DistItem");
	root.setAttribute("Id", String.valueOf(mItemId));

	Element node;

        node = doc.createElement("ItemMfgMappingId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMfgMappingId)));
        root.appendChild(node);

        node = doc.createElement("ItemDistMappingId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemDistMappingId)));
        root.appendChild(node);

        node = doc.createElement("ItemMfg1MappingId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMfg1MappingId)));
        root.appendChild(node);

        node = doc.createElement("ItemMappingAssocId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemMappingAssocId)));
        root.appendChild(node);

        node = doc.createElement("Sku");
        node.appendChild(doc.createTextNode(String.valueOf(mSku)));
        root.appendChild(node);

        node = doc.createElement("Name");
        node.appendChild(doc.createTextNode(String.valueOf(mName)));
        root.appendChild(node);

        node = doc.createElement("Size");
        node.appendChild(doc.createTextNode(String.valueOf(mSize)));
        root.appendChild(node);

        node = doc.createElement("Pack");
        node.appendChild(doc.createTextNode(String.valueOf(mPack)));
        root.appendChild(node);

        node = doc.createElement("Uom");
        node.appendChild(doc.createTextNode(String.valueOf(mUom)));
        root.appendChild(node);

        node = doc.createElement("MfgId");
        node.appendChild(doc.createTextNode(String.valueOf(mMfgId)));
        root.appendChild(node);

        node = doc.createElement("MfgName");
        node.appendChild(doc.createTextNode(String.valueOf(mMfgName)));
        root.appendChild(node);

        node = doc.createElement("MfgItemSku");
        node.appendChild(doc.createTextNode(String.valueOf(mMfgItemSku)));
        root.appendChild(node);

        node = doc.createElement("DistId");
        node.appendChild(doc.createTextNode(String.valueOf(mDistId)));
        root.appendChild(node);

        node = doc.createElement("DistName");
        node.appendChild(doc.createTextNode(String.valueOf(mDistName)));
        root.appendChild(node);

        node = doc.createElement("DistItemSku");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemSku)));
        root.appendChild(node);

        node = doc.createElement("DistItemPack");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemPack)));
        root.appendChild(node);

        node = doc.createElement("DistItemUom");
        node.appendChild(doc.createTextNode(String.valueOf(mDistItemUom)));
        root.appendChild(node);

        node = doc.createElement("Mfg1Id");
        node.appendChild(doc.createTextNode(String.valueOf(mMfg1Id)));
        root.appendChild(node);

        node = doc.createElement("Mfg1Name");
        node.appendChild(doc.createTextNode(String.valueOf(mMfg1Name)));
        root.appendChild(node);

        node = doc.createElement("Mult1Id");
        node.appendChild(doc.createTextNode(String.valueOf(mMult1Id)));
        root.appendChild(node);

        node = doc.createElement("Mult1Name");
        node.appendChild(doc.createTextNode(String.valueOf(mMult1Name)));
        root.appendChild(node);
        
        node = doc.createElement("Mfg1ItemSku");
        node.appendChild(doc.createTextNode(String.valueOf(mMfg1ItemSku)));
        root.appendChild(node);

        node = doc.createElement("DistUomConvMultiplier");
        node.appendChild(doc.createTextNode(String.valueOf(mDistUomConvMultiplier)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public DistItemView copy()  {
      DistItemView obj = new DistItemView();
      obj.setItemId(mItemId);
      obj.setItemMfgMappingId(mItemMfgMappingId);
      obj.setItemDistMappingId(mItemDistMappingId);
      obj.setItemMfg1MappingId(mItemMfg1MappingId);
      obj.setItemMappingAssocId(mItemMappingAssocId);
      obj.setSku(mSku);
      obj.setName(mName);
      obj.setSize(mSize);
      obj.setPack(mPack);
      obj.setUom(mUom);
      obj.setMfgId(mMfgId);
      obj.setMfgName(mMfgName);
      obj.setMfgItemSku(mMfgItemSku);
      obj.setDistId(mDistId);
      obj.setDistName(mDistName);
      obj.setDistItemSku(mDistItemSku);
      obj.setDistItemPack(mDistItemPack);
      obj.setDistItemUom(mDistItemUom);
      obj.setMfg1Id(mMfg1Id);
      obj.setMfg1Name(mMfg1Name);
      obj.setMfg1ItemSku(mMfg1ItemSku);
      obj.setDistUomConvMultiplier(mDistUomConvMultiplier);
      obj.setMult1Id(mMult1Id);
      obj.setMult1Name(mMult1Name);
      
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
     * Sets the ItemMfgMappingId property.
     *
     * @param pItemMfgMappingId
     *  int to use to update the property.
     */
    public void setItemMfgMappingId(int pItemMfgMappingId){
        this.mItemMfgMappingId = pItemMfgMappingId;
    }
    /**
     * Retrieves the ItemMfgMappingId property.
     *
     * @return
     *  int containing the ItemMfgMappingId property.
     */
    public int getItemMfgMappingId(){
        return mItemMfgMappingId;
    }


    /**
     * Sets the ItemDistMappingId property.
     *
     * @param pItemDistMappingId
     *  int to use to update the property.
     */
    public void setItemDistMappingId(int pItemDistMappingId){
        this.mItemDistMappingId = pItemDistMappingId;
    }
    /**
     * Retrieves the ItemDistMappingId property.
     *
     * @return
     *  int containing the ItemDistMappingId property.
     */
    public int getItemDistMappingId(){
        return mItemDistMappingId;
    }


    /**
     * Sets the ItemMfg1MappingId property.
     *
     * @param pItemMfg1MappingId
     *  int to use to update the property.
     */
    public void setItemMfg1MappingId(int pItemMfg1MappingId){
        this.mItemMfg1MappingId = pItemMfg1MappingId;
    }
    /**
     * Retrieves the ItemMfg1MappingId property.
     *
     * @return
     *  int containing the ItemMfg1MappingId property.
     */
    public int getItemMfg1MappingId(){
        return mItemMfg1MappingId;
    }


    /**
     * Sets the ItemMappingAssocId property.
     *
     * @param pItemMappingAssocId
     *  int to use to update the property.
     */
    public void setItemMappingAssocId(int pItemMappingAssocId){
        this.mItemMappingAssocId = pItemMappingAssocId;
    }
    /**
     * Retrieves the ItemMappingAssocId property.
     *
     * @return
     *  int containing the ItemMappingAssocId property.
     */
    public int getItemMappingAssocId(){
        return mItemMappingAssocId;
    }


    /**
     * Sets the Sku property.
     *
     * @param pSku
     *  int to use to update the property.
     */
    public void setSku(int pSku){
        this.mSku = pSku;
    }
    /**
     * Retrieves the Sku property.
     *
     * @return
     *  int containing the Sku property.
     */
    public int getSku(){
        return mSku;
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


    /**
     * Sets the Uom property.
     *
     * @param pUom
     *  String to use to update the property.
     */
    public void setUom(String pUom){
        this.mUom = pUom;
    }
    /**
     * Retrieves the Uom property.
     *
     * @return
     *  String containing the Uom property.
     */
    public String getUom(){
        return mUom;
    }


    /**
     * Sets the MfgId property.
     *
     * @param pMfgId
     *  int to use to update the property.
     */
    public void setMfgId(int pMfgId){
        this.mMfgId = pMfgId;
    }
    /**
     * Retrieves the MfgId property.
     *
     * @return
     *  int containing the MfgId property.
     */
    public int getMfgId(){
        return mMfgId;
    }


    /**
     * Sets the MfgName property.
     *
     * @param pMfgName
     *  String to use to update the property.
     */
    public void setMfgName(String pMfgName){
        this.mMfgName = pMfgName;
    }
    /**
     * Retrieves the MfgName property.
     *
     * @return
     *  String containing the MfgName property.
     */
    public String getMfgName(){
        return mMfgName;
    }


    /**
     * Sets the MfgItemSku property.
     *
     * @param pMfgItemSku
     *  String to use to update the property.
     */
    public void setMfgItemSku(String pMfgItemSku){
        this.mMfgItemSku = pMfgItemSku;
    }
    /**
     * Retrieves the MfgItemSku property.
     *
     * @return
     *  String containing the MfgItemSku property.
     */
    public String getMfgItemSku(){
        return mMfgItemSku;
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
     * Sets the DistName property.
     *
     * @param pDistName
     *  String to use to update the property.
     */
    public void setDistName(String pDistName){
        this.mDistName = pDistName;
    }
    /**
     * Retrieves the DistName property.
     *
     * @return
     *  String containing the DistName property.
     */
    public String getDistName(){
        return mDistName;
    }


    /**
     * Sets the DistItemSku property.
     *
     * @param pDistItemSku
     *  String to use to update the property.
     */
    public void setDistItemSku(String pDistItemSku){
        this.mDistItemSku = pDistItemSku;
    }
    /**
     * Retrieves the DistItemSku property.
     *
     * @return
     *  String containing the DistItemSku property.
     */
    public String getDistItemSku(){
        return mDistItemSku;
    }


    /**
     * Sets the DistItemPack property.
     *
     * @param pDistItemPack
     *  String to use to update the property.
     */
    public void setDistItemPack(String pDistItemPack){
        this.mDistItemPack = pDistItemPack;
    }
    /**
     * Retrieves the DistItemPack property.
     *
     * @return
     *  String containing the DistItemPack property.
     */
    public String getDistItemPack(){
        return mDistItemPack;
    }


    /**
     * Sets the DistItemUom property.
     *
     * @param pDistItemUom
     *  String to use to update the property.
     */
    public void setDistItemUom(String pDistItemUom){
        this.mDistItemUom = pDistItemUom;
    }
    /**
     * Retrieves the DistItemUom property.
     *
     * @return
     *  String containing the DistItemUom property.
     */
    public String getDistItemUom(){
        return mDistItemUom;
    }


    /**
     * Sets the Mfg1Id property.
     *
     * @param pMfg1Id
     *  int to use to update the property.
     */
    public void setMfg1Id(int pMfg1Id){
        this.mMfg1Id = pMfg1Id;
    }
    /**
     * Retrieves the Mfg1Id property.
     *
     * @return
     *  int containing the Mfg1Id property.
     */
    public int getMfg1Id(){
        return mMfg1Id;
    }


    /**
     * Sets the Mfg1Name property.
     *
     * @param pMfg1Name
     *  String to use to update the property.
     */
    public void setMfg1Name(String pMfg1Name){
        this.mMfg1Name = pMfg1Name;
    }
    /**
     * Retrieves the Mfg1Name property.
     *
     * @return
     *  String containing the Mfg1Name property.
     */
    public String getMfg1Name(){
        return mMfg1Name;
    }


    /**
     * Sets the Mfg1ItemSku property.
     *
     * @param pMfg1ItemSku
     *  String to use to update the property.
     */
    public void setMfg1ItemSku(String pMfg1ItemSku){
        this.mMfg1ItemSku = pMfg1ItemSku;
    }
    /**
     * Retrieves the Mfg1ItemSku property.
     *
     * @return
     *  String containing the Mfg1ItemSku property.
     */
    public String getMfg1ItemSku(){
        return mMfg1ItemSku;
    }


    /**
     * Sets the DistUomConvMultiplier property.
     *
     * @param pDistUomConvMultiplier
     *  BigDecimal to use to update the property.
     */
    public void setDistUomConvMultiplier(BigDecimal pDistUomConvMultiplier){
        this.mDistUomConvMultiplier = pDistUomConvMultiplier;
    }
    /**
     * Retrieves the DistUomConvMultiplier property.
     *
     * @return
     *  BigDecimal containing the DistUomConvMultiplier property.
     */
    public BigDecimal getDistUomConvMultiplier(){
        return mDistUomConvMultiplier;
    }


    public int getMult1Id() {
        return mMult1Id;
    }


    public void setMult1Id(int mult1Id) {
        this.mMult1Id = mult1Id;
    }


    public String getMult1Name() {
        return mMult1Name;
    }


    public void setMult1Name(String mult1Name) {
        this.mMult1Name = mult1Name;
    }


    
}
