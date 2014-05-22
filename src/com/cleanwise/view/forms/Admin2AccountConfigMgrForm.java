package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.SelectableObjects;
import com.cleanwise.service.api.value.BusEntityData;


import javax.servlet.http.HttpServletRequest;


public class Admin2AccountConfigMgrForm extends ActionForm {

    public static interface CONF_FUNCTION {
        public static final String USERS = "Users";
        public static final String CATALOG = "Catalog";
        public static final String DISTRIBUTOR_SCHEDULE = "Distributor Schedule";
    }

    private boolean init = false;

    private String confFunction;

    private BusEntityData site;
    private BusEntityData account;
    private BusEntityData store;

    private SelectableObjects config;
    private String catalogId;
    private String oldCatalogId;

    private String confSearchField;
    private String confSearchType;
    private boolean conifiguredOnlyFl;



    public void setAccount(BusEntityData account) {
        this.account = account;
    }


    public BusEntityData getAccount() {
        return account;
    }

    public void setStore(BusEntityData store) {
        this.store = store;
    }

    public BusEntityData getStore() {
        return store;
    }

        public void setSite(BusEntityData site) {
        this.site = site;
    }

    public BusEntityData getSite() {
        return site;
    }

    public void init(){
        init = true;
    }

    public boolean isInit() {
       return init;
    }

    public void setConfFunction(String confFunction) {
        this.confFunction = confFunction;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public void setConfig(SelectableObjects config) {
        this.config = config;
    }

    public SelectableObjects getConfig() {
        return config;
    }
    
    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getOldCatalogId() {
        return oldCatalogId;
    }

    public void setOldCatalogId(String oldCatalogId) {
        this.oldCatalogId = oldCatalogId;
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

    public String getConfFunction() {
        return confFunction;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {

        if (getConfig() != null) {
            getConfig().handleStutsFormResetRequest();
        }

        this.conifiguredOnlyFl = false;

    }
}
