package com.cleanwise.view.forms;

import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.AssetViewVector;
import com.cleanwise.service.api.util.RefCodeNames;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Title:        LocateStoreAssetForm
 * Description:  Form bean.
 * Purpose:      Holds data for the asset search
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * Date:         01.10.2007
 * Time:         13:16:36
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */

public class LocateStoreAssetForm extends LocateStoreBaseForm{

    private String _searchField = "";
    private String _searchType = RefCodeNames.SEARCH_TYPE_CD.BEGINS;
    private String _searchFieldAssetNumber="";
    private boolean _showInactiveFl = false;
    int[] _selected = new int[0];
    AssetViewVector  _assets=null;
    AssetViewVector _assetToReturn=null;
    private int _searchStoreId=-1;
    private String _warrantyIds;

    public AssetViewVector getAssets() {
        return _assets;
    }

    public void setAssets(AssetViewVector assets) {
        this._assets = assets;
    }

    public String getSearchField() {
        return _searchField;
    }

    public void setSearchField(String searchField) {
        this._searchField = searchField;
    }

    public int getSearchStoreId() {
        return _searchStoreId;
    }

    public void setSearchStoreId(int searchStoreId) {
        this._searchStoreId = searchStoreId;
    }

    public String getSearchType() {
        return _searchType;
    }

    public void setSearchType(String searchType) {
        this._searchType = searchType;
    }

    public int[] getSelected() {
        return _selected;
    }

    public void setSelected(int[] selected) {
        this._selected = selected;
    }

    public boolean getShowInactiveFl() {
        return _showInactiveFl;
    }

    public void setShowInactiveFl(boolean showInactiveFl) {
        this._showInactiveFl = showInactiveFl;
    }

    public AssetViewVector getAssetToReturn() {
        return _assetToReturn;
    }

    public void setAssetToReturn(AssetViewVector assetToReturn) {
        this._assetToReturn = assetToReturn;
    }

    public void reset(ActionMapping mapping, ServletRequest request) {
        _selected = new int[0];
        _showInactiveFl=false;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        _selected = new int[0];
        _showInactiveFl=false;
   }

    public String getSearchFieldAssetNumber() {
        return _searchFieldAssetNumber;
    }

    public void setSearchFieldAssetNumber(String searchFieldAssetNumber) {
        this._searchFieldAssetNumber = searchFieldAssetNumber;
    }

    public String getWarrantyIds() {
        return _warrantyIds;
    }

    public void setWarrantyIds(String warrantyIds) {
        this._warrantyIds = warrantyIds;
    }
}
