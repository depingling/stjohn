package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.UserData;
import com.cleanwise.view.utils.SelectableObjects;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Title:        Admin2UserConfigMgrForm
 * Description:  Form bean
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * Date:         05.08.2009
 * Time:         14:56:41
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class Admin2UserConfigMgrForm extends ActionForm {

    private boolean init = false;

    public static interface CONF_FUNUNCTION {
        public static final String ACCOUNTS          = "Accounts";
        public static final String DISTRIBUTORS      = "Distributors";
        public static final String SERVICE_PROVIDERS = "Service Providers";
        public static final String GROUPS            = "Groups";
        public static final String SITES             = "Sites";
        public static final String PERMISSIONS       = "Permissions";
        public static final String CATALOGS          = "Catalogs";
        public static final String ORDER_GUIDES      = "Order Guides";
    }

    private String confSearchField;
    private String confSearchType;
    private boolean conifiguredOnlyFl;
    private boolean removeSiteAssocFl;

    private String searchRefNum;
    private String confCity;
    private String confState;
    private String searchRefNumType;     

    private UserData user;

    private String confFunction;
    private SelectableObjects config;     

    public void init() {
        this.init = true;
    }

    public boolean isInit() {
        return init;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public UserData getUser() {
        return user;
    }

    public String getConfFunction() {
        return confFunction;
    }

    public void setConfFunction(String confFunction) {
        this.confFunction = confFunction;
    }

    public String getConfSearchField() {
        return confSearchField;
    }

    public void setConfSearchField(String confSearchField) {
        this.confSearchField = confSearchField;
    }

    public String getConfSearchType() {
        return confSearchType;
    }

    public void setConfSearchType(String confSearchType) {
        this.confSearchType = confSearchType;
    }

    public boolean getConifiguredOnlyFl() {
        return conifiguredOnlyFl;
    }

    public void setConifiguredOnlyFl(boolean conifiguredOnlyFl) {
        this.conifiguredOnlyFl = conifiguredOnlyFl;
    }

    public boolean getRemoveSiteAssocFl() {
        return removeSiteAssocFl;
    }

    public void setRemoveSiteAssocFl(boolean removeSiteAssocFl) {
        this.removeSiteAssocFl = removeSiteAssocFl;
    }

    public SelectableObjects getConfig() {
        return config;
    }

    public void setConfig(SelectableObjects config) {
        this.config = config;
    }
     public String getSearchRefNum() {
        return searchRefNum;
    }

    public String getConfCity() {
        return confCity;
    }

    public String getConfState() {
        return confState;
    }

    public String getSearchRefNumType() {
        return searchRefNumType;
    }

    public void setConfCity(String confCity) {
        this.confCity = confCity;
    }

    public void setConfState(String confState) {
        this.confState = confState;
    }

    public void setSearchRefNum(String searchRefNum) {
        this.searchRefNum = searchRefNum;
    }

    public void setSearchRefNumType(String searchRefNumType) {
        this.searchRefNumType = searchRefNumType;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {

        if (getConfig() != null) {
            getConfig().handleStutsFormResetRequest();
        }
      
        this.conifiguredOnlyFl = false;
        this.removeSiteAssocFl = false;

    }
    private java.util.ArrayList mUserGroupsReport = null;
    public java.util.ArrayList getGroupsReport() {
        if ( null == mUserGroupsReport ) {
          mUserGroupsReport = new java.util.ArrayList();
        }
        return mUserGroupsReport;
      }
      public void setGroupsReport(java.util.ArrayList v) {
        mUserGroupsReport = v;
      }

}
