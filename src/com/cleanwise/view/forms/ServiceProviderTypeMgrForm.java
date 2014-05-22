package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.*;

import org.apache.struts.action.ActionForm;

public final class ServiceProviderTypeMgrForm extends ActionForm {
    private int storeId_ = 0;
    private int serviceProviderTypeId_ = 0;
    private String serviceProviderTypeName_ = "";
    private BusEntityDataVector serviceProviderTypes_ = new BusEntityDataVector();
    private BusEntityData serviceProviderTypeToEdit_ = null;

    public int getStoreId() {
        return storeId_;
    }

    public void setStoreId(int storeId) {
        storeId_ = storeId;
    }
    
    public int getServiceProviderTypeId() {
        return serviceProviderTypeId_;
    }

    public void setServiceProviderTypeId(int serviceProviderTypeId) {
        serviceProviderTypeId_ = serviceProviderTypeId;
    }

    public String getServiceProviderTypeName() {
        return serviceProviderTypeName_;
    }

    public void setServiceProviderTypeName(String serviceProviderTypeName) {
        serviceProviderTypeName_ = serviceProviderTypeName;
    }

    public BusEntityDataVector getServiceProviderTypes() {
        return serviceProviderTypes_;
    }

    public void setServiceProviderTypes(BusEntityDataVector serviceProviderTypes) {
        serviceProviderTypes_ = serviceProviderTypes;
    }

    public BusEntityData getServiceProviderTypeToEdit() {
        return serviceProviderTypeToEdit_;
    }

    public void setServiceProviderTypeToEdit(BusEntityData serviceProviderTypeToEdit) {
        serviceProviderTypeToEdit_ = serviceProviderTypeToEdit;
    }
}
