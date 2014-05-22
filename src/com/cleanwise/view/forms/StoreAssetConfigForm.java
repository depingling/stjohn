package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.AssetDetailViewVector;
import com.cleanwise.service.api.value.PairViewVector;
import com.cleanwise.service.api.value.AssetDetailView;
import com.cleanwise.view.utils.SelectableObjects;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;

/**
 * Title:        StoreAssetConfigForm
 * Description:  Form bean for the  asset assoc management.
 * Purpose:      Holds data for the asset assoc  management
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date:         20.11.2006
 * Time:         8:32:11
 * @author       Alexander Chickin, TrinitySoft, Inc.
 */

public class StoreAssetConfigForm extends StorePortalBaseForm{

   
   private String mAssocType;
   private AssetDetailView mAssetDetailView;
   private int mAssetId;
   private String mSiteSearchField;
   private SelectableObjects mConfigResults;
   private boolean mShowConfiguredOnlyFl=false;
   private String mAssetType;
   private int mAssetLocation;

    public String getSiteSearchField() {
        return mSiteSearchField;
    }

    public void setSiteSearchField(String mSiteSearchField) {
        this.mSiteSearchField = mSiteSearchField;
    }


    public String getAssocType() {
        return mAssocType;
    }

    public void setAssocType(String assocType) {
        this.mAssocType = assocType;
    }

    public AssetDetailView getAssetDetailView() {
        return mAssetDetailView;
    }

    public void setAssetDetailView(AssetDetailView assetDetailView) {
        this.mAssetDetailView = assetDetailView;
    }

    public int getAssetId() {
        return mAssetId;
    }

    public void setAssetId(int assetId) {
        this.mAssetId = assetId;
    }

    public void setConfigResults(SelectableObjects configResult) {
        this.mConfigResults = configResult;
    }

    public SelectableObjects getConfigResults() {
        return mConfigResults;
    }

    public boolean getShowConfiguredOnlyFl() {
        return mShowConfiguredOnlyFl;
    }

    public void setShowConfiguredOnlyFl(boolean flag) {
        this.mShowConfiguredOnlyFl = flag;
    }
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        if(getConfigResults() != null){
            getConfigResults().handleStutsFormResetRequest();
        }
        this.mShowConfiguredOnlyFl=false;
    }

    public void reset(ActionMapping mapping, ServletRequest request) {
        if(getConfigResults() != null){
            getConfigResults().handleStutsFormResetRequest();
        }
        this.mShowConfiguredOnlyFl=false;
    }

    public void setAssetType(String assetType) {
        this.mAssetType = assetType;
    }

    public String getAssetType() {
        return mAssetType;
    }

    public int getAssetLocation() {
        return mAssetLocation;
    }

    public void setAssetLocation(int siteId) {
        this.mAssetLocation = siteId;
    }
}
