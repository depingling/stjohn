package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;

import java.util.Iterator;

public final class AccountMgrCheckoutDataForm extends ActionForm {
    private int accountId = 0;
    private String accountName = "";
    private BusEntityFieldsData mConfig = new BusEntityFieldsData();

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

   public  BusEntityFieldsData getConfig() {
       return mConfig;
   }

   public void setConfig(BusEntityFieldsData v) {
       mConfig = v;
   }

}
