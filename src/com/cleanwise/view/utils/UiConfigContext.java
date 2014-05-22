package com.cleanwise.view.utils;

import com.cleanwise.service.api.value.GroupData;
import com.cleanwise.service.api.value.IdVector;


public class UiConfigContext {

    private GroupData mManagedGroup;
    private IdVector mAccessibleIds;

    public GroupData getManagedGroup() {
        return mManagedGroup;
    }

    public void setManagedGroup(GroupData pManagedGroup) {
        this.mManagedGroup = pManagedGroup;
    }

    public void setAccessibleIds(IdVector pAccessibleIds) {
        this.mAccessibleIds = pAccessibleIds;
    }

    public IdVector getAccessibleIds() {
        return mAccessibleIds;
    }
}
