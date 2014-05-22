package com.cleanwise.service.api.value;

/**
 * Title:        ServiceSearchCriteria
 * Description:  search criteria
 * Purpose:      specialized search of service
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date:         20.11.2006
 * Time:         14:00:00
 * @author       Alexander Chickin, TrinitySoft, Inc.
 */
public class ServiceSearchCriteria implements java.io.Serializable {
    private int mServiceId=-1;
    private String mServiceName=null;
    private IdVector mCatalogIds=null;
    private String mCatalogTypeCd=null;
    private int mStoreId=-1;
    private String mSearchNameTypeCd=null;
    private String mCategory=null;
    private String mSearchCategoryTypeCd=null;
    private String mServiceStatusCd=null;
    private boolean mServiceShowInactive=false;

    public int getServiceId() {
        return mServiceId;
    }

    public void seServiceId(int mServiceId) {
        this.mServiceId = mServiceId;
    }

    public String getServiceName() {
        return mServiceName;
    }

    public void setServiceName(String mServiceName) {
        this.mServiceName = mServiceName;
    }

    public IdVector getCatalogIds() {
        return mCatalogIds;
    }

    public void setCatalogIds(IdVector mCatalogIds) {
        this.mCatalogIds = mCatalogIds;
    }

    public String getCatalogTypeCd() {
        return mCatalogTypeCd;
    }

    public void setCatalogTypeCd(String mCatalogTypeCd) {
        this.mCatalogTypeCd = mCatalogTypeCd;
    }

    public int getStoreId() {
        return mStoreId;
    }

    public void setStoreId(int mStoreIds) {
        this.mStoreId = mStoreIds;
    }

    public String getSearchNameTypeCd() {
        return mSearchNameTypeCd;
    }

    public void setSearchNameTypeCd(String searchNameTypeCd) {
        this.mSearchNameTypeCd = searchNameTypeCd;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        this.mCategory = category;
    }

    public String getSearchCategoryTypeCd() {
        return mSearchCategoryTypeCd;
    }

    public void setSearchCategoryTypeCd(String searchCategoryTypeCd) {
        this.mSearchCategoryTypeCd = searchCategoryTypeCd;
    }

    public String getServiceStatusCd() {
        return mServiceStatusCd;
    }

    public void setServiceStatusCd(String serviceStatusCd) {
        this.mServiceStatusCd = serviceStatusCd;
    }

    public boolean getServiceShowInactive() {
        return mServiceShowInactive;
    }

   public void setServiceShowInactive(boolean serviceShowInactive) {
        this.mServiceShowInactive = serviceShowInactive;
    }
}
