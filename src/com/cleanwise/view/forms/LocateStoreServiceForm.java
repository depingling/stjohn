package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.FreightTableDataVector;
import com.cleanwise.service.api.value.FreightHandlerViewVector;
import com.cleanwise.service.api.value.ServiceViewVector;
import com.cleanwise.service.api.value.IdVector;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;


/**
 * Title:        LocateStoreServiceForm
 * Description:  Form bean.
 * Purpose:      Holds data for the service search
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         10.01.2007
 * Time:         18:53:28
 * @author       Alexander Chickin, TrinitySoft, Inc.
 */
public class LocateStoreServiceForm  extends LocateStoreBaseForm{
    private String _searchField = "";
    private String _searchType = "";
    private boolean _showInactiveFl = false;
    int[] _selected = new int[0];
    ServiceViewVector  _service=null;
    ServiceViewVector _serviceToReturn=null;
    private int _searchStoreId=-1;
    private boolean _searchInSelectedCatalogs=false;
    private String _categoryTempl=null;
    private String _selectedCatalogList=null;

    public ServiceViewVector getService() {
        return _service;
    }

    public void setService(ServiceViewVector service) {
        this._service = service;
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

    public ServiceViewVector getServiceToReturn() {
        return _serviceToReturn;
    }

    public void setServiceToReturn(ServiceViewVector serviceToReturn) {
        this._serviceToReturn = serviceToReturn;
    }

    public void reset(ActionMapping mapping, ServletRequest request) {
        _selected = new int[0];
        _showInactiveFl=false;
        _searchInSelectedCatalogs=false;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        _selected = new int[0];
        _showInactiveFl=false;
        _searchInSelectedCatalogs=false;
    }

    public void setSearchInSelectedCatalogs(boolean searchInSelectedCatalogs) {
        this._searchInSelectedCatalogs = searchInSelectedCatalogs;
    }

    public boolean getSearchInSelectedCatalogs() {
        return _searchInSelectedCatalogs;
    }

    public String getCategoryTempl() {
        return _categoryTempl;
    }

    public void setCategoryTempl(String categoryTempl) {
        this._categoryTempl = categoryTempl;
    }

    public String getSelectedCatalogList() {
        return _selectedCatalogList;
    }

    public void setSelectedCatalogList(String selectedCatalogList) {
        this._selectedCatalogList = selectedCatalogList;
    }
}
