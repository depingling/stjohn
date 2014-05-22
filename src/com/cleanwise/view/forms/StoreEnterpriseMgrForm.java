package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.StoreData;

public class StoreEnterpriseMgrForm extends StorePortalBaseForm {

    StoreData pEnterpriseStore;
    StoreData pManagedStore;

    public StoreData getEnterpriseStore() {
        return pEnterpriseStore;
    }

    public void setEnterpriseStore(StoreData enterpriseStore) {
        this.pEnterpriseStore = enterpriseStore;
    }

    public StoreData getManagedStore() {
        return pManagedStore;
    }

    public void setManagedStore(StoreData managedStore) {
        this.pManagedStore = managedStore;
    }
}
