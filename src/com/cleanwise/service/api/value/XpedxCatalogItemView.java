
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        XpedxCatalogItemView
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
 * <code>XpedxCatalogItemView</code> is a ViewObject class for UI.
 */
public class XpedxCatalogItemView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private String mMasterCustomerName;
    private String mAccountNumber;
    private String mCatalogID;
    private String mLocale;
    private String mDistSKU;
    private String mMfgSKU;
    private String mManufacturer;
    private String mDistributor;
    private String mDistPack;
    private String mDistUOM;
    private String mPack;
    private String mUOM;
    private String mCost;
    private String mPrice;
    private String mServiceCode;
    private String mCategory;
    private String mSubCat1;
    private String mSubCat2;
    private String mSubCat3;
    private String mCostCenter;
    private String mFreightTableID;
    private String mMultiProductID;
    private String mMultiProductName;
    private String mLongDescription;
    private String mShortDescription;
    private String mColor;
    private String mSize;
    private String mImage;
    private String mShoppingMaxQTY;
    private String mShoppingRestrictionDays;
    private String mInventoryItems;
    private String mAutoOrderItem;
    private String mProductUPC;
    private String mPackUPC;
    private String mShippingCubicSize;
    private String mShippingWeight;
    private String mWeightUnit;
    private String mListPrice;
    private String mHazmat;
    private String mSpecialPermission;
    private String mCustItemNum;
    private String mCustItemDesc;
    private String mShoppingRestrictionsAction;
    private String mUnspscCd;

    /**
     * Constructor.
     */
    public XpedxCatalogItemView ()
    {
        mMasterCustomerName = "";
        mAccountNumber = "";
        mCatalogID = "";
        mLocale = "";
        mDistSKU = "";
        mMfgSKU = "";
        mManufacturer = "";
        mDistributor = "";
        mDistPack = "";
        mDistUOM = "";
        mPack = "";
        mUOM = "";
        mCost = "";
        mPrice = "";
        mServiceCode = "";
        mCategory = "";
        mSubCat1 = "";
        mSubCat2 = "";
        mSubCat3 = "";
        mCostCenter = "";
        mFreightTableID = "";
        mMultiProductID = "";
        mMultiProductName = "";
        mLongDescription = "";
        mShortDescription = "";
        mColor = "";
        mSize = "";
        mImage = "";
        mShoppingMaxQTY = "";
        mShoppingRestrictionDays = "";
        mInventoryItems = "";
        mAutoOrderItem = "";
        mProductUPC = "";
        mPackUPC = "";
        mShippingCubicSize = "";
        mShippingWeight = "";
        mWeightUnit = "";
        mListPrice = "";
        mHazmat = "";
        mSpecialPermission = "";
        mCustItemNum = "";
        mCustItemDesc = "";
        mShoppingRestrictionsAction = "";
        mUnspscCd = "";
    }

    /**
     * Constructor. 
     */
    public XpedxCatalogItemView(String parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13, String parm14, String parm15, String parm16, String parm17, String parm18, String parm19, String parm20, String parm21, String parm22, String parm23, String parm24, String parm25, String parm26, String parm27, String parm28, String parm29, String parm30, String parm31, String parm32, String parm33, String parm34, String parm35, String parm36, String parm37, String parm38, String parm39, String parm40, String parm41, String parm42, String parm43, String parm44)
    {
        mMasterCustomerName = parm1;
        mAccountNumber = parm2;
        mCatalogID = parm3;
        mLocale = parm4;
        mDistSKU = parm5;
        mMfgSKU = parm6;
        mManufacturer = parm7;
        mDistributor = parm8;
        mDistPack = parm9;
        mDistUOM = parm10;
        mPack = parm11;
        mUOM = parm12;
        mCost = parm13;
        mPrice = parm14;
        mServiceCode = parm15;
        mCategory = parm16;
        mSubCat1 = parm17;
        mSubCat2 = parm18;
        mSubCat3 = parm19;
        mCostCenter = parm20;
        mFreightTableID = parm21;
        mMultiProductID = parm22;
        mMultiProductName = parm23;
        mLongDescription = parm24;
        mShortDescription = parm25;
        mColor = parm26;
        mSize = parm27;
        mImage = parm28;
        mShoppingMaxQTY = parm29;
        mShoppingRestrictionDays = parm30;
        mInventoryItems = parm31;
        mAutoOrderItem = parm32;
        mProductUPC = parm33;
        mPackUPC = parm34;
        mShippingCubicSize = parm35;
        mShippingWeight = parm36;
        mWeightUnit = parm37;
        mListPrice = parm38;
        mHazmat = parm39;
        mSpecialPermission = parm40;
        mCustItemNum = parm41;
        mCustItemDesc = parm42;
        mShoppingRestrictionsAction = parm43;
        mUnspscCd = parm44;
        
    }

    /**
     * Creates a new XpedxCatalogItemView
     *
     * @return
     *  Newly initialized XpedxCatalogItemView object.
     */
    public static XpedxCatalogItemView createValue () 
    {
        XpedxCatalogItemView valueView = new XpedxCatalogItemView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this XpedxCatalogItemView object
     */
    public String toString()
    {
        return "[" + "MasterCustomerName=" + mMasterCustomerName + ", AccountNumber=" + mAccountNumber + ", CatalogID=" + mCatalogID + ", Locale=" + mLocale + ", DistSKU=" + mDistSKU + ", MfgSKU=" + mMfgSKU + ", Manufacturer=" + mManufacturer + ", Distributor=" + mDistributor + ", DistPack=" + mDistPack + ", DistUOM=" + mDistUOM + ", Pack=" + mPack + ", UOM=" + mUOM + ", Cost=" + mCost + ", Price=" + mPrice + ", ServiceCode=" + mServiceCode + ", Category=" + mCategory + ", SubCat1=" + mSubCat1 + ", SubCat2=" + mSubCat2 + ", SubCat3=" + mSubCat3 + ", CostCenter=" + mCostCenter + ", FreightTableID=" + mFreightTableID + ", MultiProductID=" + mMultiProductID + ", MultiProductName=" + mMultiProductName + ", LongDescription=" + mLongDescription + ", ShortDescription=" + mShortDescription + ", Color=" + mColor + ", Size=" + mSize + ", Image=" + mImage + ", ShoppingMaxQTY=" + mShoppingMaxQTY + ", ShoppingRestrictionDays=" + mShoppingRestrictionDays + ", InventoryItems=" + mInventoryItems + ", AutoOrderItem=" + mAutoOrderItem + ", ProductUPC=" + mProductUPC + ", PackUPC=" + mPackUPC + ", ShippingCubicSize=" + mShippingCubicSize + ", ShippingWeight=" + mShippingWeight + ", WeightUnit=" + mWeightUnit + ", ListPrice=" + mListPrice + ", Hazmat=" + mHazmat + ", SpecialPermission=" + mSpecialPermission + ", CustItemNum=" + mCustItemNum + ", CustItemDesc=" + mCustItemDesc + ", ShoppingRestrictionsAction=" + mShoppingRestrictionsAction + ", UnspscCd=" + mUnspscCd + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("XpedxCatalogItem");
	root.setAttribute("Id", String.valueOf(mMasterCustomerName));

	Element node;

        node = doc.createElement("AccountNumber");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountNumber)));
        root.appendChild(node);

        node = doc.createElement("CatalogID");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogID)));
        root.appendChild(node);

        node = doc.createElement("Locale");
        node.appendChild(doc.createTextNode(String.valueOf(mLocale)));
        root.appendChild(node);

        node = doc.createElement("DistSKU");
        node.appendChild(doc.createTextNode(String.valueOf(mDistSKU)));
        root.appendChild(node);

        node = doc.createElement("MfgSKU");
        node.appendChild(doc.createTextNode(String.valueOf(mMfgSKU)));
        root.appendChild(node);

        node = doc.createElement("Manufacturer");
        node.appendChild(doc.createTextNode(String.valueOf(mManufacturer)));
        root.appendChild(node);

        node = doc.createElement("Distributor");
        node.appendChild(doc.createTextNode(String.valueOf(mDistributor)));
        root.appendChild(node);

        node = doc.createElement("DistPack");
        node.appendChild(doc.createTextNode(String.valueOf(mDistPack)));
        root.appendChild(node);

        node = doc.createElement("DistUOM");
        node.appendChild(doc.createTextNode(String.valueOf(mDistUOM)));
        root.appendChild(node);

        node = doc.createElement("Pack");
        node.appendChild(doc.createTextNode(String.valueOf(mPack)));
        root.appendChild(node);

        node = doc.createElement("UOM");
        node.appendChild(doc.createTextNode(String.valueOf(mUOM)));
        root.appendChild(node);

        node = doc.createElement("Cost");
        node.appendChild(doc.createTextNode(String.valueOf(mCost)));
        root.appendChild(node);

        node = doc.createElement("Price");
        node.appendChild(doc.createTextNode(String.valueOf(mPrice)));
        root.appendChild(node);

        node = doc.createElement("ServiceCode");
        node.appendChild(doc.createTextNode(String.valueOf(mServiceCode)));
        root.appendChild(node);

        node = doc.createElement("Category");
        node.appendChild(doc.createTextNode(String.valueOf(mCategory)));
        root.appendChild(node);

        node = doc.createElement("SubCat1");
        node.appendChild(doc.createTextNode(String.valueOf(mSubCat1)));
        root.appendChild(node);

        node = doc.createElement("SubCat2");
        node.appendChild(doc.createTextNode(String.valueOf(mSubCat2)));
        root.appendChild(node);

        node = doc.createElement("SubCat3");
        node.appendChild(doc.createTextNode(String.valueOf(mSubCat3)));
        root.appendChild(node);

        node = doc.createElement("CostCenter");
        node.appendChild(doc.createTextNode(String.valueOf(mCostCenter)));
        root.appendChild(node);

        node = doc.createElement("FreightTableID");
        node.appendChild(doc.createTextNode(String.valueOf(mFreightTableID)));
        root.appendChild(node);

        node = doc.createElement("MultiProductID");
        node.appendChild(doc.createTextNode(String.valueOf(mMultiProductID)));
        root.appendChild(node);

        node = doc.createElement("MultiProductName");
        node.appendChild(doc.createTextNode(String.valueOf(mMultiProductName)));
        root.appendChild(node);

        node = doc.createElement("LongDescription");
        node.appendChild(doc.createTextNode(String.valueOf(mLongDescription)));
        root.appendChild(node);

        node = doc.createElement("ShortDescription");
        node.appendChild(doc.createTextNode(String.valueOf(mShortDescription)));
        root.appendChild(node);

        node = doc.createElement("Color");
        node.appendChild(doc.createTextNode(String.valueOf(mColor)));
        root.appendChild(node);

        node = doc.createElement("Size");
        node.appendChild(doc.createTextNode(String.valueOf(mSize)));
        root.appendChild(node);

        node = doc.createElement("Image");
        node.appendChild(doc.createTextNode(String.valueOf(mImage)));
        root.appendChild(node);

        node = doc.createElement("ShoppingMaxQTY");
        node.appendChild(doc.createTextNode(String.valueOf(mShoppingMaxQTY)));
        root.appendChild(node);

        node = doc.createElement("ShoppingRestrictionDays");
        node.appendChild(doc.createTextNode(String.valueOf(mShoppingRestrictionDays)));
        root.appendChild(node);

        node = doc.createElement("InventoryItems");
        node.appendChild(doc.createTextNode(String.valueOf(mInventoryItems)));
        root.appendChild(node);

        node = doc.createElement("AutoOrderItem");
        node.appendChild(doc.createTextNode(String.valueOf(mAutoOrderItem)));
        root.appendChild(node);

        node = doc.createElement("ProductUPC");
        node.appendChild(doc.createTextNode(String.valueOf(mProductUPC)));
        root.appendChild(node);

        node = doc.createElement("PackUPC");
        node.appendChild(doc.createTextNode(String.valueOf(mPackUPC)));
        root.appendChild(node);

        node = doc.createElement("ShippingCubicSize");
        node.appendChild(doc.createTextNode(String.valueOf(mShippingCubicSize)));
        root.appendChild(node);

        node = doc.createElement("ShippingWeight");
        node.appendChild(doc.createTextNode(String.valueOf(mShippingWeight)));
        root.appendChild(node);

        node = doc.createElement("WeightUnit");
        node.appendChild(doc.createTextNode(String.valueOf(mWeightUnit)));
        root.appendChild(node);

        node = doc.createElement("ListPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mListPrice)));
        root.appendChild(node);

        node = doc.createElement("Hazmat");
        node.appendChild(doc.createTextNode(String.valueOf(mHazmat)));
        root.appendChild(node);

        node = doc.createElement("SpecialPermission");
        node.appendChild(doc.createTextNode(String.valueOf(mSpecialPermission)));
        root.appendChild(node);

        node = doc.createElement("CustItemNum");
        node.appendChild(doc.createTextNode(String.valueOf(mCustItemNum)));
        root.appendChild(node);

        node = doc.createElement("CustItemDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mCustItemDesc)));
        root.appendChild(node);

        node = doc.createElement("ShoppingRestrictionsAction");
        node.appendChild(doc.createTextNode(String.valueOf(mShoppingRestrictionsAction)));
        root.appendChild(node);

        node = doc.createElement("UnspscCd");
        node.appendChild(doc.createTextNode(String.valueOf(mUnspscCd)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public XpedxCatalogItemView copy()  {
      XpedxCatalogItemView obj = new XpedxCatalogItemView();
      obj.setMasterCustomerName(mMasterCustomerName);
      obj.setAccountNumber(mAccountNumber);
      obj.setCatalogID(mCatalogID);
      obj.setLocale(mLocale);
      obj.setDistSKU(mDistSKU);
      obj.setMfgSKU(mMfgSKU);
      obj.setManufacturer(mManufacturer);
      obj.setDistributor(mDistributor);
      obj.setDistPack(mDistPack);
      obj.setDistUOM(mDistUOM);
      obj.setPack(mPack);
      obj.setUOM(mUOM);
      obj.setCost(mCost);
      obj.setPrice(mPrice);
      obj.setServiceCode(mServiceCode);
      obj.setCategory(mCategory);
      obj.setSubCat1(mSubCat1);
      obj.setSubCat2(mSubCat2);
      obj.setSubCat3(mSubCat3);
      obj.setCostCenter(mCostCenter);
      obj.setFreightTableID(mFreightTableID);
      obj.setMultiProductID(mMultiProductID);
      obj.setMultiProductName(mMultiProductName);
      obj.setLongDescription(mLongDescription);
      obj.setShortDescription(mShortDescription);
      obj.setColor(mColor);
      obj.setSize(mSize);
      obj.setImage(mImage);
      obj.setShoppingMaxQTY(mShoppingMaxQTY);
      obj.setShoppingRestrictionDays(mShoppingRestrictionDays);
      obj.setInventoryItems(mInventoryItems);
      obj.setAutoOrderItem(mAutoOrderItem);
      obj.setProductUPC(mProductUPC);
      obj.setPackUPC(mPackUPC);
      obj.setShippingCubicSize(mShippingCubicSize);
      obj.setShippingWeight(mShippingWeight);
      obj.setWeightUnit(mWeightUnit);
      obj.setListPrice(mListPrice);
      obj.setHazmat(mHazmat);
      obj.setSpecialPermission(mSpecialPermission);
      obj.setCustItemNum(mCustItemNum);
      obj.setCustItemDesc(mCustItemDesc);
      obj.setShoppingRestrictionsAction(mShoppingRestrictionsAction);
      obj.setUnspscCd(mUnspscCd);
      
      return obj;
    }

    
    /**
     * Sets the MasterCustomerName property.
     *
     * @param pMasterCustomerName
     *  String to use to update the property.
     */
    public void setMasterCustomerName(String pMasterCustomerName){
        this.mMasterCustomerName = pMasterCustomerName;
    }
    /**
     * Retrieves the MasterCustomerName property.
     *
     * @return
     *  String containing the MasterCustomerName property.
     */
    public String getMasterCustomerName(){
        return mMasterCustomerName;
    }


    /**
     * Sets the AccountNumber property.
     *
     * @param pAccountNumber
     *  String to use to update the property.
     */
    public void setAccountNumber(String pAccountNumber){
        this.mAccountNumber = pAccountNumber;
    }
    /**
     * Retrieves the AccountNumber property.
     *
     * @return
     *  String containing the AccountNumber property.
     */
    public String getAccountNumber(){
        return mAccountNumber;
    }


    /**
     * Sets the CatalogID property.
     *
     * @param pCatalogID
     *  String to use to update the property.
     */
    public void setCatalogID(String pCatalogID){
        this.mCatalogID = pCatalogID;
    }
    /**
     * Retrieves the CatalogID property.
     *
     * @return
     *  String containing the CatalogID property.
     */
    public String getCatalogID(){
        return mCatalogID;
    }


    /**
     * Sets the Locale property.
     *
     * @param pLocale
     *  String to use to update the property.
     */
    public void setLocale(String pLocale){
        this.mLocale = pLocale;
    }
    /**
     * Retrieves the Locale property.
     *
     * @return
     *  String containing the Locale property.
     */
    public String getLocale(){
        return mLocale;
    }


    /**
     * Sets the DistSKU property.
     *
     * @param pDistSKU
     *  String to use to update the property.
     */
    public void setDistSKU(String pDistSKU){
        this.mDistSKU = pDistSKU;
    }
    /**
     * Retrieves the DistSKU property.
     *
     * @return
     *  String containing the DistSKU property.
     */
    public String getDistSKU(){
        return mDistSKU;
    }


    /**
     * Sets the MfgSKU property.
     *
     * @param pMfgSKU
     *  String to use to update the property.
     */
    public void setMfgSKU(String pMfgSKU){
        this.mMfgSKU = pMfgSKU;
    }
    /**
     * Retrieves the MfgSKU property.
     *
     * @return
     *  String containing the MfgSKU property.
     */
    public String getMfgSKU(){
        return mMfgSKU;
    }


    /**
     * Sets the Manufacturer property.
     *
     * @param pManufacturer
     *  String to use to update the property.
     */
    public void setManufacturer(String pManufacturer){
        this.mManufacturer = pManufacturer;
    }
    /**
     * Retrieves the Manufacturer property.
     *
     * @return
     *  String containing the Manufacturer property.
     */
    public String getManufacturer(){
        return mManufacturer;
    }


    /**
     * Sets the Distributor property.
     *
     * @param pDistributor
     *  String to use to update the property.
     */
    public void setDistributor(String pDistributor){
        this.mDistributor = pDistributor;
    }
    /**
     * Retrieves the Distributor property.
     *
     * @return
     *  String containing the Distributor property.
     */
    public String getDistributor(){
        return mDistributor;
    }


    /**
     * Sets the DistPack property.
     *
     * @param pDistPack
     *  String to use to update the property.
     */
    public void setDistPack(String pDistPack){
        this.mDistPack = pDistPack;
    }
    /**
     * Retrieves the DistPack property.
     *
     * @return
     *  String containing the DistPack property.
     */
    public String getDistPack(){
        return mDistPack;
    }


    /**
     * Sets the DistUOM property.
     *
     * @param pDistUOM
     *  String to use to update the property.
     */
    public void setDistUOM(String pDistUOM){
        this.mDistUOM = pDistUOM;
    }
    /**
     * Retrieves the DistUOM property.
     *
     * @return
     *  String containing the DistUOM property.
     */
    public String getDistUOM(){
        return mDistUOM;
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
     * Sets the Cost property.
     *
     * @param pCost
     *  String to use to update the property.
     */
    public void setCost(String pCost){
        this.mCost = pCost;
    }
    /**
     * Retrieves the Cost property.
     *
     * @return
     *  String containing the Cost property.
     */
    public String getCost(){
        return mCost;
    }


    /**
     * Sets the Price property.
     *
     * @param pPrice
     *  String to use to update the property.
     */
    public void setPrice(String pPrice){
        this.mPrice = pPrice;
    }
    /**
     * Retrieves the Price property.
     *
     * @return
     *  String containing the Price property.
     */
    public String getPrice(){
        return mPrice;
    }


    /**
     * Sets the ServiceCode property.
     *
     * @param pServiceCode
     *  String to use to update the property.
     */
    public void setServiceCode(String pServiceCode){
        this.mServiceCode = pServiceCode;
    }
    /**
     * Retrieves the ServiceCode property.
     *
     * @return
     *  String containing the ServiceCode property.
     */
    public String getServiceCode(){
        return mServiceCode;
    }


    /**
     * Sets the Category property.
     *
     * @param pCategory
     *  String to use to update the property.
     */
    public void setCategory(String pCategory){
        this.mCategory = pCategory;
    }
    /**
     * Retrieves the Category property.
     *
     * @return
     *  String containing the Category property.
     */
    public String getCategory(){
        return mCategory;
    }


    /**
     * Sets the SubCat1 property.
     *
     * @param pSubCat1
     *  String to use to update the property.
     */
    public void setSubCat1(String pSubCat1){
        this.mSubCat1 = pSubCat1;
    }
    /**
     * Retrieves the SubCat1 property.
     *
     * @return
     *  String containing the SubCat1 property.
     */
    public String getSubCat1(){
        return mSubCat1;
    }


    /**
     * Sets the SubCat2 property.
     *
     * @param pSubCat2
     *  String to use to update the property.
     */
    public void setSubCat2(String pSubCat2){
        this.mSubCat2 = pSubCat2;
    }
    /**
     * Retrieves the SubCat2 property.
     *
     * @return
     *  String containing the SubCat2 property.
     */
    public String getSubCat2(){
        return mSubCat2;
    }


    /**
     * Sets the SubCat3 property.
     *
     * @param pSubCat3
     *  String to use to update the property.
     */
    public void setSubCat3(String pSubCat3){
        this.mSubCat3 = pSubCat3;
    }
    /**
     * Retrieves the SubCat3 property.
     *
     * @return
     *  String containing the SubCat3 property.
     */
    public String getSubCat3(){
        return mSubCat3;
    }


    /**
     * Sets the CostCenter property.
     *
     * @param pCostCenter
     *  String to use to update the property.
     */
    public void setCostCenter(String pCostCenter){
        this.mCostCenter = pCostCenter;
    }
    /**
     * Retrieves the CostCenter property.
     *
     * @return
     *  String containing the CostCenter property.
     */
    public String getCostCenter(){
        return mCostCenter;
    }


    /**
     * Sets the FreightTableID property.
     *
     * @param pFreightTableID
     *  String to use to update the property.
     */
    public void setFreightTableID(String pFreightTableID){
        this.mFreightTableID = pFreightTableID;
    }
    /**
     * Retrieves the FreightTableID property.
     *
     * @return
     *  String containing the FreightTableID property.
     */
    public String getFreightTableID(){
        return mFreightTableID;
    }


    /**
     * Sets the MultiProductID property.
     *
     * @param pMultiProductID
     *  String to use to update the property.
     */
    public void setMultiProductID(String pMultiProductID){
        this.mMultiProductID = pMultiProductID;
    }
    /**
     * Retrieves the MultiProductID property.
     *
     * @return
     *  String containing the MultiProductID property.
     */
    public String getMultiProductID(){
        return mMultiProductID;
    }


    /**
     * Sets the MultiProductName property.
     *
     * @param pMultiProductName
     *  String to use to update the property.
     */
    public void setMultiProductName(String pMultiProductName){
        this.mMultiProductName = pMultiProductName;
    }
    /**
     * Retrieves the MultiProductName property.
     *
     * @return
     *  String containing the MultiProductName property.
     */
    public String getMultiProductName(){
        return mMultiProductName;
    }


    /**
     * Sets the LongDescription property.
     *
     * @param pLongDescription
     *  String to use to update the property.
     */
    public void setLongDescription(String pLongDescription){
        this.mLongDescription = pLongDescription;
    }
    /**
     * Retrieves the LongDescription property.
     *
     * @return
     *  String containing the LongDescription property.
     */
    public String getLongDescription(){
        return mLongDescription;
    }


    /**
     * Sets the ShortDescription property.
     *
     * @param pShortDescription
     *  String to use to update the property.
     */
    public void setShortDescription(String pShortDescription){
        this.mShortDescription = pShortDescription;
    }
    /**
     * Retrieves the ShortDescription property.
     *
     * @return
     *  String containing the ShortDescription property.
     */
    public String getShortDescription(){
        return mShortDescription;
    }


    /**
     * Sets the Color property.
     *
     * @param pColor
     *  String to use to update the property.
     */
    public void setColor(String pColor){
        this.mColor = pColor;
    }
    /**
     * Retrieves the Color property.
     *
     * @return
     *  String containing the Color property.
     */
    public String getColor(){
        return mColor;
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
     * Sets the Image property.
     *
     * @param pImage
     *  String to use to update the property.
     */
    public void setImage(String pImage){
        this.mImage = pImage;
    }
    /**
     * Retrieves the Image property.
     *
     * @return
     *  String containing the Image property.
     */
    public String getImage(){
        return mImage;
    }


    /**
     * Sets the ShoppingMaxQTY property.
     *
     * @param pShoppingMaxQTY
     *  String to use to update the property.
     */
    public void setShoppingMaxQTY(String pShoppingMaxQTY){
        this.mShoppingMaxQTY = pShoppingMaxQTY;
    }
    /**
     * Retrieves the ShoppingMaxQTY property.
     *
     * @return
     *  String containing the ShoppingMaxQTY property.
     */
    public String getShoppingMaxQTY(){
        return mShoppingMaxQTY;
    }


    /**
     * Sets the ShoppingRestrictionDays property.
     *
     * @param pShoppingRestrictionDays
     *  String to use to update the property.
     */
    public void setShoppingRestrictionDays(String pShoppingRestrictionDays){
        this.mShoppingRestrictionDays = pShoppingRestrictionDays;
    }
    /**
     * Retrieves the ShoppingRestrictionDays property.
     *
     * @return
     *  String containing the ShoppingRestrictionDays property.
     */
    public String getShoppingRestrictionDays(){
        return mShoppingRestrictionDays;
    }


    /**
     * Sets the InventoryItems property.
     *
     * @param pInventoryItems
     *  String to use to update the property.
     */
    public void setInventoryItems(String pInventoryItems){
        this.mInventoryItems = pInventoryItems;
    }
    /**
     * Retrieves the InventoryItems property.
     *
     * @return
     *  String containing the InventoryItems property.
     */
    public String getInventoryItems(){
        return mInventoryItems;
    }


    /**
     * Sets the AutoOrderItem property.
     *
     * @param pAutoOrderItem
     *  String to use to update the property.
     */
    public void setAutoOrderItem(String pAutoOrderItem){
        this.mAutoOrderItem = pAutoOrderItem;
    }
    /**
     * Retrieves the AutoOrderItem property.
     *
     * @return
     *  String containing the AutoOrderItem property.
     */
    public String getAutoOrderItem(){
        return mAutoOrderItem;
    }


    /**
     * Sets the ProductUPC property.
     *
     * @param pProductUPC
     *  String to use to update the property.
     */
    public void setProductUPC(String pProductUPC){
        this.mProductUPC = pProductUPC;
    }
    /**
     * Retrieves the ProductUPC property.
     *
     * @return
     *  String containing the ProductUPC property.
     */
    public String getProductUPC(){
        return mProductUPC;
    }


    /**
     * Sets the PackUPC property.
     *
     * @param pPackUPC
     *  String to use to update the property.
     */
    public void setPackUPC(String pPackUPC){
        this.mPackUPC = pPackUPC;
    }
    /**
     * Retrieves the PackUPC property.
     *
     * @return
     *  String containing the PackUPC property.
     */
    public String getPackUPC(){
        return mPackUPC;
    }


    /**
     * Sets the ShippingCubicSize property.
     *
     * @param pShippingCubicSize
     *  String to use to update the property.
     */
    public void setShippingCubicSize(String pShippingCubicSize){
        this.mShippingCubicSize = pShippingCubicSize;
    }
    /**
     * Retrieves the ShippingCubicSize property.
     *
     * @return
     *  String containing the ShippingCubicSize property.
     */
    public String getShippingCubicSize(){
        return mShippingCubicSize;
    }


    /**
     * Sets the ShippingWeight property.
     *
     * @param pShippingWeight
     *  String to use to update the property.
     */
    public void setShippingWeight(String pShippingWeight){
        this.mShippingWeight = pShippingWeight;
    }
    /**
     * Retrieves the ShippingWeight property.
     *
     * @return
     *  String containing the ShippingWeight property.
     */
    public String getShippingWeight(){
        return mShippingWeight;
    }


    /**
     * Sets the WeightUnit property.
     *
     * @param pWeightUnit
     *  String to use to update the property.
     */
    public void setWeightUnit(String pWeightUnit){
        this.mWeightUnit = pWeightUnit;
    }
    /**
     * Retrieves the WeightUnit property.
     *
     * @return
     *  String containing the WeightUnit property.
     */
    public String getWeightUnit(){
        return mWeightUnit;
    }


    /**
     * Sets the ListPrice property.
     *
     * @param pListPrice
     *  String to use to update the property.
     */
    public void setListPrice(String pListPrice){
        this.mListPrice = pListPrice;
    }
    /**
     * Retrieves the ListPrice property.
     *
     * @return
     *  String containing the ListPrice property.
     */
    public String getListPrice(){
        return mListPrice;
    }


    /**
     * Sets the Hazmat property.
     *
     * @param pHazmat
     *  String to use to update the property.
     */
    public void setHazmat(String pHazmat){
        this.mHazmat = pHazmat;
    }
    /**
     * Retrieves the Hazmat property.
     *
     * @return
     *  String containing the Hazmat property.
     */
    public String getHazmat(){
        return mHazmat;
    }


    /**
     * Sets the SpecialPermission property.
     *
     * @param pSpecialPermission
     *  String to use to update the property.
     */
    public void setSpecialPermission(String pSpecialPermission){
        this.mSpecialPermission = pSpecialPermission;
    }
    /**
     * Retrieves the SpecialPermission property.
     *
     * @return
     *  String containing the SpecialPermission property.
     */
    public String getSpecialPermission(){
        return mSpecialPermission;
    }


    /**
     * Sets the CustItemNum property.
     *
     * @param pCustItemNum
     *  String to use to update the property.
     */
    public void setCustItemNum(String pCustItemNum){
        this.mCustItemNum = pCustItemNum;
    }
    /**
     * Retrieves the CustItemNum property.
     *
     * @return
     *  String containing the CustItemNum property.
     */
    public String getCustItemNum(){
        return mCustItemNum;
    }


    /**
     * Sets the CustItemDesc property.
     *
     * @param pCustItemDesc
     *  String to use to update the property.
     */
    public void setCustItemDesc(String pCustItemDesc){
        this.mCustItemDesc = pCustItemDesc;
    }
    /**
     * Retrieves the CustItemDesc property.
     *
     * @return
     *  String containing the CustItemDesc property.
     */
    public String getCustItemDesc(){
        return mCustItemDesc;
    }


    /**
     * Sets the ShoppingRestrictionsAction property.
     *
     * @param pShoppingRestrictionsAction
     *  String to use to update the property.
     */
    public void setShoppingRestrictionsAction(String pShoppingRestrictionsAction){
        this.mShoppingRestrictionsAction = pShoppingRestrictionsAction;
    }
    /**
     * Retrieves the ShoppingRestrictionsAction property.
     *
     * @return
     *  String containing the ShoppingRestrictionsAction property.
     */
    public String getShoppingRestrictionsAction(){
        return mShoppingRestrictionsAction;
    }


    /**
     * Sets the UnspscCd property.
     *
     * @param pUnspscCd
     *  String to use to update the property.
     */
    public void setUnspscCd(String pUnspscCd){
        this.mUnspscCd = pUnspscCd;
    }
    /**
     * Retrieves the UnspscCd property.
     *
     * @return
     *  String containing the UnspscCd property.
     */
    public String getUnspscCd(){
        return mUnspscCd;
    }


    
}
