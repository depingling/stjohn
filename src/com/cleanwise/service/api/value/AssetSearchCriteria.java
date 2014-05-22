package com.cleanwise.service.api.value;

import java.util.HashMap;

/**
 * Title:        AssetSearchCriteria
 * Description:  search criteria
 * Purpose:      specialized search of asset
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date:         20.11.2006
 * Time:         14:00:00
 * @author       Alexander Chickin, TrinitySoft, Inc.
 */
public class AssetSearchCriteria implements java.io.Serializable {

    private IdVector mStoreIds=null;
    private IdVector mAccountIds=null;
    private IdVector mSiteIds=null;
    private IdVector mBusEntityIds=null;
    private int mUserId=-1;
    private String mUserTypeCd=null;
    private int mAssetId=-1;
    private String mAssetName=null;
    private String mStatusCd=null;
    private String mSerialNumber=null;
    private String mAssetNumber=null;
    private String mSearchNameTypeCd=null;
    private String mSearchNumberTypeCd=null;
    private String mSearchModelTypeCd=null;
    private String mSearchManufSkuTypeCd=null;
    private String mSearchIdTypeCd=null;
    private boolean mShowInactive;
    private String mStagedSearchType;
    private String mAssocTypeCd=null;
    private String mAssetTypeCd=null;
    private int mParentId=-1;
    private IdVector mParentIds=null;
    private IdVector mServiceIds=null;
    private HashMap mAssetAssocCds=new HashMap();
    private IdVector mAssetIds=null;
    private IdVector mWarrantyIds;
    private String mModelNumber;
    private String mManufName;
    private String[] mManufOtherNames;
    private int mManufId;
    private String mManufSku;
    private String mCategoryName;
    private int mMasterAssetId=-1;
    private PairView mOrderBy;
    private boolean mIgnoreCase;
	private boolean userAuthorizedForAssetWOViewAllForStore = false;

	public IdVector getStoreIds() {
        return mStoreIds;
    }

    public void setStoreIds(IdVector mStoreIds) {
        this.mStoreIds = mStoreIds;
    }

    public IdVector getAccountIds() {
        return mAccountIds;
    }

    public void setAccountIds(IdVector accountIds) {
        this.mAccountIds = accountIds;
    }
    
    public IdVector getSiteIds() {
        return mSiteIds;
    }

    public void setSiteIds(IdVector mSiteIds) {
        this.mSiteIds = mSiteIds;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public int getAssetId() {
        return mAssetId;
    }

    public void setAssetId(int mAssetId) {
        this.mAssetId = mAssetId;
    }

    public String getAssetName() {
        return mAssetName;
    }

    public void setAssetName(String mAssetName) {
        this.mAssetName = mAssetName;
    }

    public String getStatusCd() {
        return mStatusCd;
    }

    public void setStatusCd(String mStatusCd) {
        this.mStatusCd = mStatusCd;
    }

    public String getSerialNumber() {
        return mSerialNumber;
    }

    public void setSerialNumber(String mSerialNumber) {
        this.mSerialNumber = mSerialNumber;
    }

    public String getAssetNumber() {
        return mAssetNumber;
    }

    public void setAssetNumber(String mAssetNumber) {
        this.mAssetNumber = mAssetNumber;
    }


    public boolean getShowInactive() {
        return mShowInactive;
    }

    public void setShowInactive(boolean mShowInactive) {
        this.mShowInactive = mShowInactive;
    }

    public String getSearchNameTypeCd() {
        return mSearchNameTypeCd;
    }

    public void setSearchNameTypeCd(String searchTypeCd) {
        this.mSearchNameTypeCd = searchTypeCd;
    }

    public String getSearchNumberTypeCd() {
        return mSearchNumberTypeCd;
    }

    public void setSearchNumberTypeCd(String searchNumberTypeCd) {
        this.mSearchNumberTypeCd = searchNumberTypeCd;
    }
    
    public String getAssocTypeCd() {
        return mAssocTypeCd;
    }

    public void setAssocTypeCd(String assocTypeCd) {
        this.mAssocTypeCd=assocTypeCd;
    }

    public IdVector getBusEntityIds() {
        return mBusEntityIds;
    }

    public void setBusEntityIds(IdVector mBusEntityIds) {
        this.mBusEntityIds = mBusEntityIds;
    }


    public String getUserTypeCd() {
        return mUserTypeCd;
    }

    public void setUserTypeCd(String mUserTypeCd) {
        this.mUserTypeCd = mUserTypeCd;
    }

    public int getParentId() {
        return mParentId;
    }

    public void setParentId(int parentId) {
        this.mParentId = parentId;
    }

    public String getAssetTypeCd() {
        return mAssetTypeCd;
    }

    public void setAssetTypeCd(String assetTypeCd) {
        this.mAssetTypeCd = assetTypeCd;
    }

    public IdVector getServiceIds() {
        return mServiceIds;
    }

    public void setServiceIds(IdVector serviceIds) {
        this.mServiceIds = serviceIds;
    }


    public HashMap getAssetAssocCds() {
        return mAssetAssocCds;
    }

    public void setAssetAssocCds(HashMap assetAssocCds) {
        this.mAssetAssocCds = assetAssocCds;
    }

    public IdVector getAssetIds() {
        return mAssetIds;
    }

    public void setAssetIds(IdVector assetIds) {
        this.mAssetIds = assetIds;
    }

    public void setWarrantyIds(IdVector warrantyIds) {
        this.mWarrantyIds = warrantyIds;
    }

    public IdVector getWarrantyIds() {
        return mWarrantyIds;
    }

    public void setModelNumber(String modelNumber) {
        this.mModelNumber = modelNumber;
    }

    public String getModelNumber() {
        return mModelNumber;
    }

    public void setManufName(String manufName) {
        this.mManufName = manufName;
    }

    public String getManufName() {
        return mManufName;
    }

    public PairView getOrderBy() {
        return mOrderBy;
    }

    public void setOrderBy(PairView orderBy) {
        this.mOrderBy = orderBy;
    }
    
    public IdVector getParentIds() {
        return mParentIds;
    }

    public void setParentIds(IdVector mParentIds) {
        this.mParentIds = mParentIds;
    }

    public int getManufId() {
        return mManufId;
    }

    public void setManufId(int manufId) {
        this.mManufId = manufId;
    }

    public String getManufSku() {
        return mManufSku;
    }

    public int getMasterAssetId() {
        return mMasterAssetId;
    }

    public void setMasterAssetId(int masterAssetId) {
        this.mMasterAssetId = masterAssetId;
    }
    
    public void setManufSku(String manufSku) {
        this.mManufSku = manufSku;
    }
    
    public boolean getIgnoreCase() {
        return mIgnoreCase;
    }

    public void setIgnoreCase(boolean mIgnoreCase) {
        this.mIgnoreCase = mIgnoreCase;
    }

    public boolean isUserAuthorizedForAssetWOViewAllForStore() {
        return userAuthorizedForAssetWOViewAllForStore;
    }
    public void setUserAuthorizedForAssetWOViewAllForStore (
                              boolean userAuthorizedForAssetWOViewAllForStore) {
        this.userAuthorizedForAssetWOViewAllForStore = userAuthorizedForAssetWOViewAllForStore;
    }

    public String getStagedSearchType() {
        return mStagedSearchType;
    }

    public void setStagedSearchType(String stagedSearchType) {
        this.mStagedSearchType = stagedSearchType;
    }

    public String getSearchIdTypeCd() {
        return mSearchIdTypeCd;
    }

    public void setSearchIdTypeCd(String searchIdTypeCd) {
        this.mSearchIdTypeCd = searchIdTypeCd;
    }

    public String getSearchManufSkuTypeCd() {
        return mSearchManufSkuTypeCd;
    }

    public void setSearchManufSkuTypeCd(String searchManufSkuTypeCd) {
        this.mSearchManufSkuTypeCd = searchManufSkuTypeCd;
    }

    public String getSearchModelTypeCd() {
        return mSearchModelTypeCd;
    }

    public void setSearchModelTypeCd(String searchModelTypeCd) {
        this.mSearchModelTypeCd = searchModelTypeCd;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(String categoryName) {
        this.mCategoryName = categoryName;
    }

    public String[] getManufOtherNames() {
        return mManufOtherNames;
    }

    public void setManufOtherNames(String[] manufOtherNames) {
        this.mManufOtherNames = manufOtherNames;
    }

}
