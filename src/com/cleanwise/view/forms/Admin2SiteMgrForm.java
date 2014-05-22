package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.service.api.value.SiteViewVector;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Admin2SiteMgrForm extends StorePortalBaseForm {

    private boolean mInit;
    private int mManagedEntity;

    private String mSearchField = "";
    private String mSearchRefNum = "";
    private String mSearchType = "nameBegins";
    private String mSearchRefNumType = "nameBegins";
    private String mSearchCity = "";
    private String mSearchCounty = "";
    private String mSearchState = "";
    private String mSearchPostalCode = "";
    private boolean mShowInactiveFl = false;
    private AccountDataVector mAccountFilter;

    private SiteViewVector mSiteSearchResult;

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

    public String getSearchRefNum() {
        return mSearchRefNum;
    }

    public void setSearchRefNum(String pSearchRefNum) {
        this.mSearchRefNum = pSearchRefNum;
    }

    public String getSearchRefNumType() {
        return mSearchRefNumType;
    }

    public void setSearchRefNumType(String pSearchRefNumType) {
        this.mSearchRefNumType = pSearchRefNumType;
    }

    public String getSearchCity() {
        return mSearchCity;
    }

    public void setSearchCity(String pSearchCity) {
        this.mSearchCity = pSearchCity;
    }

    public String getSearchCounty() {
        return mSearchCounty;
    }

    public void setSearchCounty(String pSearchCounty) {
        this.mSearchCounty = pSearchCounty;
    }

    public String getSearchState() {
        return mSearchState;
    }

    public void setSearchState(String pSearchState) {
        this.mSearchState = pSearchState;
    }

    public String getSearchPostalCode() {
        return mSearchPostalCode;
    }

    public void setSearchPostalCode(String pSearchPostalCode) {
        this.mSearchPostalCode = pSearchPostalCode;
    }

    public boolean getShowInactiveFl() {
        return mShowInactiveFl;
    }

    public void setShowInactiveFl(boolean pShowInactiveFl) {
        this.mShowInactiveFl = pShowInactiveFl;
    }

    public AccountDataVector getAccountFilter() {
        return mAccountFilter;
    }

    public void setAccountFilter(AccountDataVector pAccountFilter) {
        this.mAccountFilter = pAccountFilter;
    }

     /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
         mSearchField      = "";
         mSearchRefNum     = "";
         mSearchCity       = "";
         mSearchCounty     = "";
         mSearchState      = "";
         mSearchPostalCode = "";
         mShowInactiveFl   = false;
    }

    public void setSiteSearchResult(SiteViewVector pSiteSearchResult) {
        this.mSiteSearchResult = pSiteSearchResult;
    }

    public SiteViewVector getSiteSearchResult() {
        return mSiteSearchResult;
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

    private void setManagedEntity(int pManagedEntity) {
        this.mManagedEntity = pManagedEntity;
    }

    public int getManagedEntity() {
        return mManagedEntity;
    }

}
