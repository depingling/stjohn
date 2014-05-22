package com.cleanwise.view.forms;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cleanwise.service.api.value.*;

public class ReportingLocateStoreSiteForm extends LocateStoreBaseForm {
    private String _searchField = "";

    private String _searchType = "";

    private int _userId = -1;

    private String _city = "";

    private String _state = "";

    private int _searchStoreId = -1;

    IdVector _accountIds = null;

    private boolean _showInactiveFl = false;

    int[] _selected = new int[0];

    String[] _selectedName = new String[0];

    SiteViewVector _sitesToReturn = null;

    SiteViewVector _sites = null;

    private int _mode = -1;

    private int _storeId = -1;

    boolean _singleSelect = true;

    private String _selectedIdsAsString = "";
  private String dataSourceType;

  public IdVector getAccountIds() {
        return _accountIds;
    }

    public void setAccountIds(IdVector _accountIds) {
        this._accountIds = _accountIds;
    }

    public String getCity() {
        return _city;
    }

    public void setCity(String _city) {
        this._city = _city;
    }

    public String getSearchField() {
        return _searchField;
    }

    public void setSearchField(String _searchField) {
        this._searchField = _searchField;
    }

    public String getSearchType() {
        return _searchType;
    }

    public void setSearchType(String _searchType) {
        this._searchType = _searchType;
    }

    public String[] getSelectedName() {
        return _selectedName;
    }

    public void setSelectedName(String[] _selectedName) {
        this._selectedName = _selectedName;
    }

    public int[] getSelected() {
        return _selected;
    }

    public void setSelected(int[] _selected) {
        this._selected = _selected;
    }

    public boolean getShowInactiveFl() {
        return _showInactiveFl;
    }

    public void setShowInactiveFl(boolean _showInactiveFl) {
        this._showInactiveFl = _showInactiveFl;
    }

    public SiteViewVector getSitesToReturn() {
        return _sitesToReturn;
    }

    public void setSitesToReturn(SiteViewVector _sitesToReturn) {
        this._sitesToReturn = _sitesToReturn;
    }

    public String getState() {
        return _state;
    }

    public void setState(String _state) {
        this._state = _state;
    }

    public int getUserId() {
        return _userId;
    }

    public void setUserId(int _userId) {
        this._userId = _userId;
    }

    public int getSearchStoreId() {
        return _searchStoreId;
    }

    public void setSearchStoreId(int _searchStoreId) {
        this._searchStoreId = _searchStoreId;
    }

    public SiteViewVector getSites() {
        return _sites;
    }

    public void setSites(SiteViewVector _sites) {
        this._sites = _sites;
    }

    public void setMode(int mode) {
        this._mode = mode;
    }

    public int getMode() {
        return _mode;
    }

    public void setStoreId(int storeId) {
        this._storeId = storeId;
    }

    public int getStoreId() {
        return _storeId;
    }

    public boolean getSingleSelect() {
        return _singleSelect;
    }

    public void setSingleSelect(boolean _singleSelect) {
        this._singleSelect = _singleSelect;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        _showInactiveFl = false;
        _selected = new int[0];
        _selectedName = new String[0];
    }

    public void setSelectedIdsAsString(String selectedIdsAsString) {
        this._selectedIdsAsString = selectedIdsAsString;
    }

    public String getSelectedIdsAsString() {
        return _selectedIdsAsString;
    }

  public void setDataSourceType(String dataSourceType) {
    this.dataSourceType = dataSourceType;
  }

  public String getDataSourceType() {
    return dataSourceType;
  }
}
