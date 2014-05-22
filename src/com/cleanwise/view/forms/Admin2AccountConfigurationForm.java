package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.value.SiteViewVector;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.OrderGuideDescDataVector;
import com.cleanwise.service.api.value.UserDataVector;

public class Admin2AccountConfigurationForm extends ActionForm {


    public static interface CONF_FUNUNCTION {
        public static final String SITES = "sites";
        public static final String CATALOGS = "catalogs";
        public static final String ORDER_GUIDES = "orderguides";
        public static final String USERS = "users";
    }

    private int mAccountId;

    private String mSearchForType = "";
    private String mSearchForName = "";
    private String mSearchType = Constants.NAME_BEGINS;
    private String mCity = "";
    private String mState = "";
    private String mFirstName = "";
    private String mLastName = "";
    private String mUserType = "";
    private boolean mShowInactiveFl = false;

    private SiteViewVector mSiteConfigs;
    private CatalogDataVector mCatalogConfigs;
    private OrderGuideDescDataVector mOrderGuideConfigs;
    private UserDataVector mUserConfigs;

    public String getCity() {
        return mCity;
    }

    public void setCity(String pCity) {
        this.mCity = pCity;
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

    public String getSearchForName() {
        return mSearchForName;
    }

    public void setSearchForName(String pSearchForName) {
        this.mSearchForName = pSearchForName;
    }

    public String getSearchForType() {
        return mSearchForType;
    }

    public void setSearchForType(String pSearchForType) {
        this.mSearchForType = pSearchForType;
    }

    public String getSearchType() {
        return mSearchType;
    }

    public void setSearchType(String pSearchType) {
        this.mSearchType = pSearchType;
    }

    public boolean getShowInactiveFl() {
        return mShowInactiveFl;
    }

    public void setShowInactiveFl(boolean pShowInactiveFl) {
        this.mShowInactiveFl = pShowInactiveFl;
    }

    public String getState() {
        return mState;
    }

    public void setState(String pState) {
        this.mState = pState;
    }

    public String getUserType() {
        return mUserType;
    }

    public void setUserType(String pUserType) {
        this.mUserType = pUserType;
    }

    public void reset(ActionMapping pActionMapping, HttpServletRequest pRequest) {
        this.mShowInactiveFl = false;
    }

    public void setAccountId(int pAccountId) {
        this.mAccountId = pAccountId;
    }

    public int getAccountId() {
        return mAccountId;
    }

    public void setSiteConfigs(SiteViewVector pSiteConfigs) {
        this.mSiteConfigs = pSiteConfigs;
    }

    public SiteViewVector getSiteConfigs() {
        return mSiteConfigs;
    }

    public void setCatalogConfigs(CatalogDataVector pCatalogConfigs) {
        this.mCatalogConfigs = pCatalogConfigs;
    }

    public CatalogDataVector getCatalogConfigs() {
        return mCatalogConfigs;
    }

    public void setOrderGuideConfigs(OrderGuideDescDataVector pOrderGuideConfigs) {
        this.mOrderGuideConfigs = pOrderGuideConfigs;
    }

    public OrderGuideDescDataVector getOrderGuideConfigs() {
        return mOrderGuideConfigs;
    }

    public void setUserConfigs(UserDataVector pUserConfigs) {
        this.mUserConfigs = pUserConfigs;
    }

    public UserDataVector getUserConfigs() {
        return mUserConfigs;
    }
}

