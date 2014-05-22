package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.ItemViewVector;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.AssetDataVector;
import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.view.utils.SelectableObjects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;

/**
 * Title:        StoreServiceMgrForm
 * Description:  Form bean for the  service management.
 * Purpose:      Holds data for the service management
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         08.01.2006
 * Time:         09:29:56
 *
 * @author       Alexander Chickin, TrinitySoft, Inc.
 */

public class StoreServiceMgrForm extends ActionForm {
    int mStoreCatalogId;
    private ItemDataVector mServicesCollection;
    private ItemData mService;
    private SelectableObjects mConfigResults;
    private int mCurrentServiceId;
    private boolean mShowConfiguredOnlyFl;
    private String mAssetSearchField;
    private String mAssetSearchType = "begins";
    private boolean mEditServiceFl;


    public int getStoreCatalogId() {
        return mStoreCatalogId;
    }

    public void setStoreCatalogId(int mStoreCatalogId) {
        this.mStoreCatalogId = mStoreCatalogId;
    }

    public ItemDataVector getServicesCollection() {
        return mServicesCollection;
    }

    public void setServicesCollection(ItemDataVector servicesCollection) {
        this.mServicesCollection = servicesCollection;
    }

    public ItemData getService() {
        return mService;
    }

    public void setService(ItemData mService) {
        this.mService = mService;
    }

    public void setConfigResults(SelectableObjects configResults) {
        this.mConfigResults = configResults;
    }

    public SelectableObjects getConfigResults() {
        return this.mConfigResults ;
    }

    public int getCurrentServiceId() {
        return mCurrentServiceId;
    }

    public void setCurrentServiceId(int currentServiceId) {
        this.mCurrentServiceId = currentServiceId;
    }
    public String getAssetSearchField() {
         return mAssetSearchField;
     }

     public void setAssetSearchField(String assetSearchField) {
         this.mAssetSearchField = assetSearchField;
     }

    public boolean getShowConfiguredOnlyFl() {
        return mShowConfiguredOnlyFl;
    }

    public void setShowConfiguredOnlyFl(boolean showConfiguredOnlyFl) {
        this.mShowConfiguredOnlyFl = showConfiguredOnlyFl;
    }

    public String getAssetSearchType() {
        return mAssetSearchType;
    }

    public void setAssetSearchType(String assetSearchType) {
        this.mAssetSearchType = assetSearchType;
    }

    public void setEditServiceFl(boolean flag)
    {
        this.mEditServiceFl=flag;
    }

    public boolean getEditServiceFl() {
        return mEditServiceFl;
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
}
