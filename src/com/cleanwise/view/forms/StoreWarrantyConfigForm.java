package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.SelectableObjects;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;

public class StoreWarrantyConfigForm extends StorePortalBaseForm{

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
