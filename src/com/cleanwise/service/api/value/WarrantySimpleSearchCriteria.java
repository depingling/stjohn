package com.cleanwise.service.api.value;

/**
 * Title:        WarrantySimpleSearchCriteria
 * Description:  search criteria.
 * Purpose:      specialized search of warranty.
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         24.09.2007
 * Time:         12:14:30
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */

public class WarrantySimpleSearchCriteria  implements java.io.Serializable {

    int warrantyId;
    String warrantyNumber;
    String warrantyName;
    String assetName;
    String assetNumber;
    String searchType;
    private IdVector storeIds;
    private String warrantyProvider;
    private String serviceProvider;


    public int getWarrantyId() {
        return warrantyId;
    }

    public void setWarrantyId(int warrantyId) {
        this.warrantyId = warrantyId;
    }

    public String getWarrantyNumber() {
        return warrantyNumber;
    }

    public void setWarrantyNumber(String warrantyNumber) {
        this.warrantyNumber = warrantyNumber;
    }

    public String getWarrantyName() {
        return warrantyName;
    }

    public void setWarrantyName(String warrantyName) {
        this.warrantyName = warrantyName;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public void setStoreIds(IdVector storeIds) {
        this.storeIds = storeIds;
    }

    public IdVector getStoreIds() {
        return storeIds;
    }

    public void setWarrantyProvider(String providerName) {
        this.warrantyProvider=providerName;
    }

    public String getWarrantyProvider() {
        return warrantyProvider;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
}
