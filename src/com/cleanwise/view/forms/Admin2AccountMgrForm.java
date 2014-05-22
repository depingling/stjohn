package com.cleanwise.view.forms;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.ProfileViewContainer;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;


public class Admin2AccountMgrForm extends StorePortalBaseForm {

    private boolean mInit;
    private int mManagedEntity;

    private String _searchField = "";
    private String _searchType = Constants.NAME_BEGINS;

    private String mSearchGroupId = "";
    private boolean _showInactiveFl = false;

    private GroupDataVector mAccountGroups = null;
    private AccountSearchResultViewVector mSearchResult;

    public void setSearchField(String pVal) {
        this._searchField = pVal;
    }

    public String getSearchField() {
        return (this._searchField);
    }

    public void setSearchType(String pVal) {
        this._searchType = pVal;
    }

    public String getSearchType() {
        return (this._searchType);
    }

    public void setShowInactiveFl(boolean pVal) {
        _showInactiveFl = pVal;
    }

    public boolean getShowInactiveFl() {
        return _showInactiveFl;
    }

    /**
     * <code>reset</code> method, set the search fiels to null.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        _searchField = "";
        _showInactiveFl = false;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        return null;
    }

    public GroupDataVector getAccountGroups() {
        return mAccountGroups;
    }

    public void setAccountGroups(GroupDataVector v) {
        mAccountGroups = v;
    }

    public String getSearchGroupId() {
        return mSearchGroupId;
    }

    public void setSearchGroupId(String v) {
        mSearchGroupId = v;
    }


    public boolean isInit(HttpServletRequest request) {

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        if (appUser.isaAccountAdmin()) {
            return mInit && getManagedEntity() == appUser.getUserAccount().getAccountId();
        } else if (appUser.isaAdmin()) {
            return mInit && getManagedEntity() == (appUser.getUserStore().getStoreId());
        }

        return false;
    }

    public void init(HttpServletRequest request) {
        mInit = false;
        setManagedEntity(0);

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        if (appUser.isaAccountAdmin() && appUser.getUserAccount().getAccountId() > 0) {
            setManagedEntity(appUser.getUserAccount().getAccountId());
            mInit = true;
        } else if (appUser.isaAdmin() && appUser.getUserStore().getStoreId() > 0) {
            setManagedEntity(appUser.getUserStore().getStoreId());
            mInit = true;
        }
    }

    public void setManagedEntity(int pManagedEntity) {
        this.mManagedEntity = pManagedEntity;
    }

    public int getManagedEntity() {
        return mManagedEntity;
    }

    public void setSearchResult(AccountSearchResultViewVector pSearchResult) {
        this.mSearchResult = pSearchResult;
    }


    public AccountSearchResultViewVector getSearchResult() {
        return mSearchResult;
    }
}


