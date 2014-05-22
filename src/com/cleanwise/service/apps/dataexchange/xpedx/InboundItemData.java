package com.cleanwise.service.apps.dataexchange.xpedx;

import java.math.BigDecimal;
import java.io.Serializable;

public class InboundItemData implements Serializable {

    private String mDistSKU;
    private String mMfgSKU;
    private String mManufacturer;
    private String mDistributor;
    private String mDistPack;
    private String mDistUOM;
    private String mPack;
    private String mUOM;
    private BigDecimal mCost;
    private BigDecimal mPrice;
    private String mServiceCode;
    private String mCategory;
    private String mSubCat1;
    private String mSubCat2;
    private String mSubCat3;
    private String mCostCenter;
    private Integer mFreightTableID;
    private Integer mMultiProductID;
    private String mMultiProductName;
    private String mShortDescription;
    private String mLongDescription;
    private String mColor;
    private String mSize;
    private String mImage;
    private Integer mShoppingMaxQTY;
    private Integer mShoppingRestrictionDays;
    private Boolean mInventoryItems;
    private Boolean mAutoOrderItem;
    private String mProductUPC;
    private String mPackUPC;
    private String mShippingCubicSize;
    private String mShippingWeight;
    private String mWeightUnit;
    private String mListPrice;
    private Boolean mHazmat;
    private Boolean mSpecialPermission;
    private String mCustItemDesc;
    private String mCustItemNum;
    private String mShoppingRestrictionsAction;
    private String mUnspscCd;

    /**
	 * @return the mShoppingRestrictionsAction
	 */
	public final String getShoppingRestrictionsAction() {
		return mShoppingRestrictionsAction;
	}

	/**
	 * @param mShoppingRestrictionsAction the mShoppingRestrictionsAction to set
	 */
	public final void setShoppingRestrictionsAction(String mShoppingRestrictionsAction) {
		this.mShoppingRestrictionsAction = mShoppingRestrictionsAction;
	}

	public String getDistSKU() {
        return mDistSKU;
    }

    public void setDistSKU(String pDistSKU) {
        this.mDistSKU = pDistSKU;
    }

    public String getMfgSKU() {
        return mMfgSKU;
    }

    public void setMfgSKU(String pMfgSKU) {
        this.mMfgSKU = pMfgSKU;
    }

    public String getManufacturer() {
        return mManufacturer;
    }

    public void setManufacturer(String pManufacturer) {
        this.mManufacturer = pManufacturer;
    }

    public String getDistPack() {
        return mDistPack;
    }

    public void setDistPack(String pDistPack) {
        this.mDistPack = pDistPack;
    }

    public String getDistributor() {
        return mDistributor;
    }

    public void setDistributor(String pDistributor) {
        this.mDistributor = pDistributor;
    }

    public String getDistUOM() {
        return mDistUOM;
    }

    public void setDistUOM(String pDistUOM) {
        this.mDistUOM = pDistUOM;
    }

    public String getPack() {
        return mPack;
    }

    public void setPack(String pPack) {
        this.mPack = pPack;
    }

    public String getUOM() {
        return mUOM;
    }

    public void setUOM(String pUOM) {
        this.mUOM = pUOM;
    }

    public BigDecimal getCost() {
        return mCost;
    }

    public void setCost(BigDecimal pCost) {
        this.mCost = pCost;
    }

    public BigDecimal getPrice() {
        return mPrice;
    }

    public void setPrice(BigDecimal pPrice) {
        this.mPrice = pPrice;
    }

    public String getServiceCode() {
        return mServiceCode;
    }

    public void setServiceCode(String pServiceCode) {
        this.mServiceCode = pServiceCode;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String pCategory) {
        this.mCategory = pCategory;
    }

    public String getSubCat1() {
        return mSubCat1;
    }

    public void setSubCat1(String pSubCat1) {
        this.mSubCat1 = pSubCat1;
    }

    public String getSubCat2() {
        return mSubCat2;
    }

    public void setSubCat2(String pSubCat2) {
        this.mSubCat2 = pSubCat2;
    }

    public String getSubCat3() {
        return mSubCat3;
    }

    public void setSubCat3(String pSubCat3) {
        this.mSubCat3 = pSubCat3;
    }

    public String getCostCenter() {
        return mCostCenter;
    }

    public void setCostCenter(String pCostCenter) {
        this.mCostCenter = pCostCenter;
    }

    public Integer getFreightTableID() {
        return mFreightTableID;
    }

    public void setFreightTableID(Integer pFreightTableID) {
        this.mFreightTableID = pFreightTableID;
    }

    public void setMultiProductID(Integer pMultiProductID) {
        this.mMultiProductID = pMultiProductID;
    }

    public Integer getMultiProductID() {
        return this.mMultiProductID;
    }

    public String getMultiProductName() {
        return mMultiProductName;
    }

    public void setMultiProductName(String pMultiProductName) {
        this.mMultiProductName = pMultiProductName;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setShortDescription(String pShortDescription) {
        this.mShortDescription = pShortDescription;
    }

    public String getLongDescription() {
        return mLongDescription;
    }

    public void setLongDescription(String pLongDescription) {
        this.mLongDescription = pLongDescription;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String pColor) {
        this.mColor = pColor;
    }

    public String getSize() {
        return mSize;
    }

    public void setSize(String pSize) {
        this.mSize = mSize;
    }

    public Integer getShoppingMaxQTY() {
        return mShoppingMaxQTY;
    }

    public void setShoppingMaxQTY(Integer pShoppingMaxQTY) {
        this.mShoppingMaxQTY = pShoppingMaxQTY;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String pImage) {
        this.mImage = pImage;
    }

    public Integer getShoppingRestrictionDays() {
        return mShoppingRestrictionDays;
    }

    public void setShoppingRestrictionDays(Integer pShoppingRestrictionDays) {
        this.mShoppingRestrictionDays = pShoppingRestrictionDays;
    }

    public Boolean getInventoryItems() {
        return mInventoryItems;
    }

    public void setInventoryItems(Boolean pInventoryItems) {
        this.mInventoryItems = pInventoryItems;
    }

    public Boolean getAutoOrderItem() {
        return mAutoOrderItem;
    }

    public void setAutoOrderItem(Boolean pAutoOrderItem) {
        this.mAutoOrderItem = pAutoOrderItem;
    }

    public String getProductUPC() {
        return mProductUPC;
    }

    public void setProductUPC(String pProductUPC) {
        this.mProductUPC = pProductUPC;
    }

    public String getPackUPC() {
        return mPackUPC;
    }

    public void setPackUPC(String pPackUPC) {
        this.mPackUPC = pPackUPC;
    }

    public String getShippingCubicSize() {
        return mShippingCubicSize;
    }

    public void setShippingCubicSize(String pShippingCubicSize) {
        this.mShippingCubicSize = pShippingCubicSize;
    }

    public String getShippingWeight() {
        return mShippingWeight;
    }

    public void setShippingWeight(String pShippingWeight) {
        this.mShippingWeight = pShippingWeight;
    }

    public String getWeightUnit() {
        return mWeightUnit;
    }

    public void setWeightUnit(String pWeightUnit) {
        this.mWeightUnit = pWeightUnit;
    }

    public String getListPrice() {
        return mListPrice;
    }

    public void setListPrice(String pListPrice) {
        this.mListPrice = pListPrice;
    }

    public Boolean getHazmat() {
        return mHazmat;
    }

    public void setHazmat(Boolean pHazmat) {
        this.mHazmat = pHazmat;
    }

    public void setSpecialPermission(Boolean pSpecialPermission) {
        this.mSpecialPermission = pSpecialPermission;
    }

    public Boolean getSpecialPermission() {
        return mSpecialPermission;
    }

    public void setCustItemDesc(String pCustItemDesc) {
        this.mCustItemDesc = pCustItemDesc;
    }

    public String getCustItemDesc() {
        return mCustItemDesc;
    }

    public void setCustItemNum(String pCustItemNum) {
        this.mCustItemNum = pCustItemNum;
    }

    public String getCustItemNum() {
        return mCustItemNum;
    }

    public void setUnspscCd(String pUnspscCd){
        this.mUnspscCd = pUnspscCd;
    }
    
    public String getUnspscCd(){
        return mUnspscCd;
    }

    public String toString() {
        return "[\n" +
                "DistSKU=" + mDistSKU + ", MfgSKU=" + mMfgSKU + "," +
                "\nManufacturer=" + mManufacturer + "," +
                "\nDistributor=" + mDistributor + "," +
                "\nDistPack=" + mDistPack + "," +
                "\nDistUOM=" + mDistUOM + "," +
                "\nPack=" + mPack + "," +
                "\nUOM=" + mUOM + "," +
                "\nCost=" + mCost + "," +
                "\nPrice=" + mPrice + "," +
                "\nServiceCode=" + mServiceCode + "," +
                "\nCategory=" + mCategory + "," +
                "\nSubCat1=" + mSubCat1 + "," +
                "\nSubCat2=" + mSubCat2 + "," +
                "\nSubCat3=" + mSubCat3 + "," +
                "\nCostCenter=" + mCostCenter + "," +
                "\nFreightTableID=" + mFreightTableID + "," +
                "\nMultiProductID=" + mMultiProductID + "," +
                "\nMultiProductName=" + mMultiProductName + "," +
                "\nLongDescription=" + mLongDescription + "," +
                "\nShortDescription=" + mShortDescription + "," +
                "\nColor=" + mColor + "," +
                "\nSize=" + mSize + "," +
                "\nImage=" + mImage + "," +
                "\nShoppingMaxQTY=" + mShoppingMaxQTY + "," +
                "\nShoppingRestrictionDays=" + mShoppingRestrictionDays + "," +
                "\nInventoryItems=" + mInventoryItems + "," +
                "\nAutoOrderItem=" + mAutoOrderItem + "," +
                "\nProductUPC=" + mProductUPC + "," +
                "\nPackUPC=" + mPackUPC + "," +
                "\nShippingCubicSize=" + mShippingCubicSize + "," +
                "\nShippingWeight=" + mShippingWeight + "," +
                "\nWeightUnit=" + mWeightUnit + "," +
                "\nListPrice=" + mListPrice + "," +
                "\nHazmat=" + mHazmat + "," +
                "\nSpecialPermission=" + mSpecialPermission + "," +
                "\nCustItemDesc=" + mCustItemDesc + "," +
                "\nCustItemNum=" + mCustItemNum + "," +
                "\nShoppingRestrictionsAction=" + mShoppingRestrictionsAction +
                "\nUnspscCd=" + mUnspscCd +
                "\n]";
    }
}
