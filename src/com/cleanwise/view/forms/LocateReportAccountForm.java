/*
 * LocateReportAccountForm.java
 *
 * Created on October, 2008
 */

package com.cleanwise.view.forms;

import java.util.Collection;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.*;
import com.cleanwise.view.utils.*;


public class LocateReportAccountForm extends LocateStoreBaseForm {

    private String _searchField = "";
    private String _searchType = "";
    private boolean _showInactiveFl = false;
    int[] _selected = new int[0];
    private GroupDataVector _accountGroups = null;
    private int _searchGroupId;
    private AccountUIViewVector _accounts = null;
    private AccountUIViewVector _accountsToReturn = null;
	private String _dataSourceType = "";

    public String getSearchField() {
        return _searchField;
    }

    public void setSearchField(String searchField) {
        _searchField = searchField;
    }

    public String getSearchType() {
        return _searchType;
    }

    public void setSearchType(String searchType) {
        _searchType = searchType;
    }

    public int getSearchGroupId() {
        return _searchGroupId;
    }

    public void setSearchGroupId(int searchGroupId) {
        _searchGroupId = searchGroupId;
    }

    public GroupDataVector getAccountGroups() {
        return _accountGroups;
    }

    public void setAccountGroups(GroupDataVector accountGroups) {
        _accountGroups = accountGroups;
    }

    public int[] getSelected() {
        return _selected;
    }

    public void setSelected(int[] selected) {
        _selected = selected;
    }

    public boolean getShowInactiveFl() {
        return _showInactiveFl;
    }

    public void setShowInactiveFl(boolean showInactiveFl) {
        _showInactiveFl = showInactiveFl;
    }

    public AccountUIViewVector getAccounts() {
        return _accounts;
    }

    public void setAccounts(AccountUIViewVector accounts) {
        _accounts = accounts;
    }

    public AccountUIViewVector getAccountsToReturn() {
        return _accountsToReturn;
    }

    public void setAccountsToReturn(AccountUIViewVector accountsToReturn) {
        _accountsToReturn = accountsToReturn;
    }
	
	
    public String getDataSourceType() {
        return _dataSourceType;
    }

    public void setDataSourceType(String dataSourceType) {
        _dataSourceType = dataSourceType;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        _selected = new int[0];
        _showInactiveFl = false;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        return null;
    }
	

}
