package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.FiscalPeriodView;
import org.apache.struts.action.ActionForm;

import java.util.ArrayList;


public class Admin2SiteBudgetMgrForm extends ActionForm {

    private int siteId;
    private String siteName;
    private int accountId;
    private String accountName;
    private boolean init = false;
    private ArrayList siteBudgetList;
    private FiscalPeriodView fiscalInfo;

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void init() {
        init = true;
    }

    public boolean isInit() {
        return init;
    }


    public int getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public int getSiteId() {
        return siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteBudgetList(ArrayList siteBudgetList) {
        this.siteBudgetList = siteBudgetList;
    }

    public void setFiscalInfo(FiscalPeriodView fiscalInfo) {
        this.fiscalInfo = fiscalInfo;
    }

    public FiscalPeriodView getFiscalInfo() {
        return fiscalInfo;
    }

    public ArrayList getSiteBudgetList() {
        return siteBudgetList;
    }
}
