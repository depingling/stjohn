package com.cleanwise.service.apps.dataexchange.xpedx;

import com.cleanwise.service.api.value.*;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.io.Serializable;


public class AppAccountData implements Serializable {

    private BusEntityData mAccount;
    private Integer mAccountCatalog;
    private Integer mMainDistributorId;
    private Map<String, Integer> mExistCategories;
    private Map<String, List<CostCenterData>> mCostCentersMap;
    private Map<Integer, CatalogStructureData> mAppAccountCatalogCategoryMap;
    private Map<Integer, CatalogStructureData> mAppAccountCatalogProductMap;
    private Map<ItemReference, ItemAssocData> mAppAccountItemCategoryAssocMap;
    private Map<ItemReference, InventoryItemsData> mInventoryItemsMap;
    private Set<Integer> mShoppingCatalogIdSet;
    private Map<ItemReference, ShoppingControlData> mShoppingControlsMap;

    public AppAccountData() {
    }

    public Integer getAccountCatalog() {
        return mAccountCatalog;
    }

    public void setAccountCatalog(Integer pAccountCatalog) {
        this.mAccountCatalog = pAccountCatalog;
    }


    public void setMainDistributorId(Integer pMainDistributorId) {
        this.mMainDistributorId = pMainDistributorId;
    }

    public Integer getMainDistributorId() {
        return mMainDistributorId;
    }

    public void setExistCategories(Map<String, Integer> pExistCategories) {
        this.mExistCategories = pExistCategories;
    }

    public Map<String, Integer> getExistCategories() {
        return mExistCategories;
    }

    public void setCostCentersMap(Map<String, List<CostCenterData>> pCostCentersMap) {
        this.mCostCentersMap = pCostCentersMap;
    }

    public Map<String, List<CostCenterData>> getCostCentersMap() {
        return mCostCentersMap;
    }

    public void setAccount(BusEntityData pAccount) {
        this.mAccount = pAccount;
    }

    public BusEntityData getAccount() {
        return mAccount;
    }

    public void setAppAccountCatalogCategoryMap(Map<Integer, CatalogStructureData> pAppAccountCatalogCategoryMap) {
        this.mAppAccountCatalogCategoryMap = pAppAccountCatalogCategoryMap;
    }

    public Map<Integer, CatalogStructureData> getAppAccountCatalogCategoryMap() {
        return mAppAccountCatalogCategoryMap;
    }

    public void setAppAccountCatalogProductMap(Map<Integer, CatalogStructureData> pAppAccountCatalogProductMap) {
        this.mAppAccountCatalogProductMap = pAppAccountCatalogProductMap;
    }

    public Map<Integer, CatalogStructureData> getAppAccountCatalogProductMap() {
        return mAppAccountCatalogProductMap;
    }

    public void setAppAccountItemCategoryAssocMap(Map<ItemReference, ItemAssocData> pAppAccountItemCategoryAssocMap) {
        this.mAppAccountItemCategoryAssocMap = pAppAccountItemCategoryAssocMap;
    }

    public Map<ItemReference, ItemAssocData> getAppAccountItemCategoryAssocMap() {
        return mAppAccountItemCategoryAssocMap;
    }

    public void setInventoryItemsMap(Map<ItemReference, InventoryItemsData> pInventoryItemsMap) {
        this.mInventoryItemsMap = pInventoryItemsMap;
    }

    public Map<ItemReference, InventoryItemsData> getInventoryItemsMap() {
        return mInventoryItemsMap;
    }

    public void setShoppingCatalogIdSet(Set<Integer> pShoppingCatalogIdSet) {
        this.mShoppingCatalogIdSet = pShoppingCatalogIdSet;
    }

    public Set<Integer> getShoppingCatalogIdSet() {
        return mShoppingCatalogIdSet;
    }

    public void setShoppingControlsMap(Map<ItemReference, ShoppingControlData> pShoppingControlsMap) {
        this.mShoppingControlsMap = pShoppingControlsMap;
    }

    public Map<ItemReference, ShoppingControlData> getShoppingControlsMap() {
        return mShoppingControlsMap;
    }
}

