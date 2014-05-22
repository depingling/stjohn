package com.cleanwise.view.forms;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.service.api.value.AssetView;
import com.cleanwise.service.api.value.AssetViewVector;
import com.cleanwise.service.api.value.StagedAssetDataVector;
import java.util.HashMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;


/**
 * Title:        UserAssetMgrForm
 * Description:  Form bean for the asset management in the USERPORTAL.
 * Purpose:      Holds data for the asset management
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date:         29.12.2006
 * Time:         13:43:34
 * @author       Alexander Chickin, TrinitySoft, Inc.
 */
public class UserAssetMgrForm extends StorePortalBaseForm {


    public interface SEARCH_FIELD_TYPE {
        public static final String SERIAL_NUM = "SERIAL_NUM";
        public static final String MODEL_NUM  = "MODEL_NUM";
        public static final String ASSET_NAME = "ASSET_NAME";
        public static final String CATEGORY   = "CATEGORY";
    }

    //search
    private String mSearchField;/*asset value of search*/
    private String mNameSearchType = RefCodeNames.SEARCH_TYPE_CD.BEGINS;
    private String mModelSearchType = RefCodeNames.SEARCH_TYPE_CD.BEGINS;
    private String mManufSkuSearchType = RefCodeNames.SEARCH_TYPE_CD.BEGINS;
    private String mIdSearchType = RefCodeNames.SEARCH_TYPE_CD.BEGINS;
    private String mSearchSerial;
    private String mSearchNumber;
    private String mSearchModel;
    private String mSearchManufacturerId;
    private String mSearchManufacturerName;
    private String mSearchManufSku;
    private String mSearchCategoryId;
    private String mSearchCategoryName;
    private String mSearchAssetId;
    private String mAssetType;
    private String mSearchFieldType;/*field type of search*/
    private boolean mCurrentSiteOnlyFl; /*search only for the selected site*/
    private AssetViewVector mAssetSearchResult;/*search result*/
    private AssetViewVector mMasterAssetSearchResult;/*masterAsset search result*/
    private AssetViewVector mCategoryAssetSearchResult;/*categoryAsset search result*/
    private AssetViewVector mAssetMatchSearchResult;/*Assets to match with. search result*/
    private AssetViewVector mStagedAssetSearchResult;/*staged Master Asset search result*/
    private AssetView mStagedAssetMatch; /*Staged Asset to match*/
    private String mSiteSearchField;/*site_id  with which asset  is assigned */
    private boolean mShowInactive;
    private boolean mMatchSearch;
    private String mStagedSearchType = RefCodeNames.STAGED_ASSETS_SEARCH_TYPE_CD.NOT_MATCHED;
    public boolean init = false;
    private LocateStoreAccountForm mlocateStoreAccountForm = null;
    private AccountDataVector mfilterAccounts = null;
    
    
    public String getNameSearchType() {
        return mNameSearchType;
    }

    public void setNameSearchType(String nameSearchType) {
        this.mNameSearchType = nameSearchType;
    }

    public String getSearchField() {
        return mSearchField;
    }

    public void setSearchField(String searchField) {
        this.mSearchField = searchField;
    }

    public AssetViewVector getAssetSearchResult() {
        return mAssetSearchResult;
    }

    public void setAssetSearchResult(AssetViewVector assetSearchResult) {
        this.mAssetSearchResult = assetSearchResult;
    }

    public AssetViewVector getMasterAssetSearchResult() {
        return mMasterAssetSearchResult;
    }

    public void setMasterAssetSearchResult(AssetViewVector masterAssetSearchResult) {
        this.mMasterAssetSearchResult = masterAssetSearchResult;
    }

    public AssetViewVector getCategoryAssetSearchResult() {
        return mCategoryAssetSearchResult;
    }

    public void setCategoryAssetSearchResult(AssetViewVector categoryAssetSearchResult) {
        this.mCategoryAssetSearchResult = categoryAssetSearchResult;
    }
    
    public String getSiteSearchField() {
        return mSiteSearchField;
    }

    public void setSiteSearchField(String siteSearchField) {
        this.mSiteSearchField = siteSearchField;
    }

    public boolean getCurrentSiteOnlyFl() {
        return mCurrentSiteOnlyFl;
    }

    public void setCurrentSiteOnlyFl(boolean mCurrentSiteOnlyFl) {
        this.mCurrentSiteOnlyFl = mCurrentSiteOnlyFl;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        mCurrentSiteOnlyFl = false;
        mShowInactive = false;
        mMatchSearch = false;
        mSearchField = "";
        mSearchSerial = "";
        mSearchNumber = "";
        mSearchModel = "";
        mSearchManufSku = "";
        mNameSearchType = RefCodeNames.SEARCH_TYPE_CD.BEGINS;
        mModelSearchType = RefCodeNames.SEARCH_TYPE_CD.BEGINS;
        mManufSkuSearchType = RefCodeNames.SEARCH_TYPE_CD.BEGINS;
        mIdSearchType = RefCodeNames.SEARCH_TYPE_CD.BEGINS;
    }

    public void reset(ActionMapping mapping, ServletRequest request) {
        mCurrentSiteOnlyFl = false;
        mShowInactive = false;
        mMatchSearch = false;
        mSearchField = "";
        mSearchSerial = "";
        mSearchNumber = "";
        mSearchModel = "";
        mSearchManufSku = "";
        mNameSearchType = RefCodeNames.SEARCH_TYPE_CD.BEGINS;
        mModelSearchType = RefCodeNames.SEARCH_TYPE_CD.BEGINS;
        mManufSkuSearchType = RefCodeNames.SEARCH_TYPE_CD.BEGINS;
        mIdSearchType = RefCodeNames.SEARCH_TYPE_CD.BEGINS;
    }

    public String getSearchFieldType() {
        return mSearchFieldType;
    }

    public void setSearchFieldType(String mSearchFieldType) {
        this.mSearchFieldType = mSearchFieldType;
    }

    public void setAssetType(String assetType) {
        this.mAssetType = assetType;
    }

    public String getAssetType() {
        return mAssetType;
    }

    public String getSearchManufSku() {
        return mSearchManufSku;
    }

    public void setSearchManufSku(String searchManufSku) {
        this.mSearchManufSku = searchManufSku;
    }

    public String getSearchModel() {
        return mSearchModel;
    }

    public void setSearchModel(String searchModel) {
        this.mSearchModel = searchModel;
    }

    public String getSearchSerial() {
        return mSearchSerial;
    }

    public void setSearchSerial(String searchSerial) {
        this.mSearchSerial = searchSerial;
    }

    public String getSearchManufacturerId() {
        return mSearchManufacturerId;
    }

    public void setSearchManufacturerId(String searchManufacturerId) {
        this.mSearchManufacturerId = searchManufacturerId;
    }

    public String getSearchCategoryId() {
        return mSearchCategoryId;
    }

    public void setSearchCategoryId(String searchCategoryId) {
        this.mSearchCategoryId = searchCategoryId;
    }

    public String getSearchNumber() {
        return mSearchNumber;
    }

    public void setSearchNumber(String searchNumber) {
        this.mSearchNumber = searchNumber;
    }

    public String getSearchAssetId() {
        return mSearchAssetId;
    }

    public void setSearchAssetId(String searchAssetId) {
        this.mSearchAssetId = searchAssetId;
    }
    
    public boolean getShowInactive() {
        return mShowInactive;
    }

    public void setShowInactive(boolean showInactive) {
        this.mShowInactive = showInactive;
    }

    public LocateStoreAccountForm getLocateStoreAccountForm() {
        return mlocateStoreAccountForm;
    }

    public void setLocateStoreAccountForm(LocateStoreAccountForm locateStoreAccountForm) {
        this.mlocateStoreAccountForm = locateStoreAccountForm;
    }

    public AccountDataVector getFilterAccounts() {
        return mfilterAccounts;
    }

    public void setFilterAccounts(AccountDataVector filterAccounts) {
        this.mfilterAccounts = filterAccounts;
    }
    
    public void init() {
         init = true;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        init = false;
    }

    public boolean getMatchSearch() {
        return mMatchSearch;
    }

    public void setMatchSearch(boolean matchSearch) {
        this.mMatchSearch = matchSearch;
    }


    public AssetViewVector getStagedAssetSearchResult() {
        return mStagedAssetSearchResult;
    }

    public void setStagedAssetSearchResult(AssetViewVector stagedAssetSearchResult) {
        this.mStagedAssetSearchResult = stagedAssetSearchResult;
    }

    public String getIdSearchType() {
        return mIdSearchType;
    }

    public void setIdSearchType(String idSearchType) {
        this.mIdSearchType = idSearchType;
    }

    public String getManufSkuSearchType() {
        return mManufSkuSearchType;
    }

    public void setManufSkuSearchType(String manufSkuSearchType) {
        this.mManufSkuSearchType = manufSkuSearchType;
    }

    public String getModelSearchType() {
        return mModelSearchType;
    }

    public void setModelSearchType(String modelSearchType) {
        this.mModelSearchType = modelSearchType;
    }

    public AssetView getStagedAssetMatch() {
        return mStagedAssetMatch;
    }

    public void setStagedAssetMatch(AssetView stagedAssetMatch) {
        this.mStagedAssetMatch = stagedAssetMatch;
    }

    public AssetViewVector getAssetMatchSearchResult() {
        return mAssetMatchSearchResult;
    }

    public void setAssetMatchSearchResult(AssetViewVector assetMatchSearchResult) {
        this.mAssetMatchSearchResult = assetMatchSearchResult;
    }

    public String getStagedSearchType() {
        return mStagedSearchType;
    }

    public void setStagedSearchType(String stagedSearchType) {
        this.mStagedSearchType = stagedSearchType;
    }

    public String getSearchCategoryName() {
        return mSearchCategoryName;
    }

    public void setSearchCategoryName(String searchCategoryName) {
        this.mSearchCategoryName = searchCategoryName;
    }

    public String getSearchManufacturerName() {
        return mSearchManufacturerName;
    }

    public void setSearchManufacturerName(String searchManufacturerName) {
        this.mSearchManufacturerName = searchManufacturerName;
    }
    
}
