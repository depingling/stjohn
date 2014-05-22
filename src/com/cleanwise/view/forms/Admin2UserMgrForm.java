package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Company:      CleanWise, Inc.
 * Date:         29.06.2009
 * Time:         13:21:55
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class Admin2UserMgrForm extends StorePortalBaseForm {


    boolean mInit = false;
    private int managedEntity = 0;

    private String mSearchField = "";
    private String mSearchType = "nameBegins";
    private boolean mSearchShowInactiveFl = false;

    private String mFirstName = "";
    private String mLastName  = "";
    private String mUserType  = "";

    private int mSelectedId = 0;

    private AccountDataVector mAccountFilter;

    private UserDataVector mSearchResult;

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

    private int getManagedEntity() {
        return managedEntity;
    }

    private void setManagedEntity(int managedEntity) {
        this.managedEntity = managedEntity;
    }

    public String getSearchField() {
        return mSearchField;
    }

    public void setSearchField(String pSearchField) {
        this.mSearchField = pSearchField;
    }

    public String getSearchType() {
        return mSearchType;
    }

    public void setSearchType(String pSearchType) {
        this.mSearchType = pSearchType;
    }

    public boolean getSearchShowInactiveFl() {
        return mSearchShowInactiveFl;
    }

    public void setSearchShowInactiveFl(boolean pSearchShowInactiveFl) {
        this.mSearchShowInactiveFl = pSearchShowInactiveFl;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String pFirstName) {
        this.mFirstName = pFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String pLastName) {
        this.mLastName = pLastName;
    }

    public String getUserType() {
        return mUserType;
    }

    public void setUserType(String pUserType) {
        this.mUserType = pUserType;
    }

    public AccountDataVector getAccountFilter() {
        return mAccountFilter;
    }

    public void setAccountFilter(AccountDataVector pAccountFilter) {
        this.mAccountFilter = pAccountFilter;
    }

    public UserDataVector getSearchResult() {
        return mSearchResult;
    }

    public void setSearchResult(UserDataVector pSearchResult) {
        this.mSearchResult = pSearchResult;
    }

    public int getSelectedId() {
        return mSelectedId;
    }

    public void setSelectedId(int pSelectedId) {
        this.mSelectedId = pSelectedId;
    }
}


