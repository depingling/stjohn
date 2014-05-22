package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.WarrantyData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.AssetWarrantyViewVector;
import com.cleanwise.view.utils.SelectableObjects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         27.11.2007
 * Time:         11:11:36
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public class UserWarrantyConfigMgrForm  extends ActionForm {
    private WarrantyData warrantyData;
    private boolean showConfiguredOnlyFl=false;
    private String assocType;
    private String searchField;
    private SelectableObjects configResults;
    private BusEntityData filterWarrantyProvider;
    private AssetWarrantyViewVector allOnlyConfigs;
    private String activeAssetCategoryId;
    private boolean manufFilter=true;

    public void setWarrantyData(WarrantyData warrantyData) {
        this.warrantyData = warrantyData;
    }


    public boolean getShowConfiguredOnlyFl() {
        return showConfiguredOnlyFl;
    }

    public void setShowConfiguredOnlyFl(boolean showConfiguredOnlyFl) {
        this.showConfiguredOnlyFl = showConfiguredOnlyFl;
    }

    public WarrantyData getWarrantyData() {
        return warrantyData;
    }

    public void setAssocType(String assocType) {
        this.assocType = assocType;
    }

    public String getAssocType() {
        return assocType;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public void setConfigResults(SelectableObjects configResults) {
        this.configResults = configResults;
    }

    public SelectableObjects getConfigResults() {
        return configResults;
    }

    public void setFilterWarrantyProvider(BusEntityData filterWarrantyProvider) {
        this.filterWarrantyProvider = filterWarrantyProvider;
    }

    public BusEntityData getFilterWarrantyProvider() {
        return filterWarrantyProvider;
    }

    public void setAllOnlyConfigs(AssetWarrantyViewVector allOnlyConfigs) {
        this.allOnlyConfigs = allOnlyConfigs;
    }

    public AssetWarrantyViewVector getAllOnlyConfigs() {
        return this.allOnlyConfigs;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        if(getConfigResults() != null){
            getConfigResults().handleStutsFormResetRequest();
        }

    }

    public void reset(ActionMapping mapping, ServletRequest request) {
        if(getConfigResults() != null){
            getConfigResults().handleStutsFormResetRequest();
        }
    }

    public String getActiveAssetCategoryId() {
        return activeAssetCategoryId;
    }

    public void setActiveAssetCategoryId(String activeAssetCategoryId) {
        this.activeAssetCategoryId = activeAssetCategoryId;
    }

    public boolean getManufFilter() {
        return manufFilter;
    }

    public void setManufFilter(boolean manufFilter) {
        this.manufFilter = manufFilter;
    }
}
