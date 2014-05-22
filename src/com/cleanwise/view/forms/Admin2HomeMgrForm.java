package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntityData;

/**
 * Title:        Admin2HomeMgrForm
 * Description:  Form bean
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * Date:         25.05.2009
 * Time:         13:48:49
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class Admin2HomeMgrForm extends ActionForm {

    private BusEntityDataVector mManagedEntities;
    private BusEntityData currentStore;
    private BusEntityData currentAccount;

    public void setManagedEntities(BusEntityDataVector pManagedEntities) {
        this.mManagedEntities = pManagedEntities;
    }

    public BusEntityDataVector getManagedEntities() {
        return mManagedEntities;
    }

    public void setCurrentStore(BusEntityData currentStore) {
        this.currentStore = currentStore;
    }

    public BusEntityData getCurrentStore() {
        return currentStore;
    }

    public void setCurrentAccount(BusEntityData currentAccount) {
        this.currentAccount = currentAccount;
    }

    public BusEntityData getCurrentAccount() {
        return currentAccount;
    }
}
