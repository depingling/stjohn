package com.cleanwise.service.apps.dataexchange.xpedx;

import com.cleanwise.service.api.value.*;

import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;


public class MasterAppData implements Serializable {

    private int mStoreCatalogId;
    private int mStoreId;
    private int mTradingPartnerId;

    private Map<ItemReference/*Disttributor_DistSKU*/, AppItemData> mAppItemMap;

    private Map<AccountReference/*accountNumer*/ , AppAccountReferenceData> mAppAccountRefMap;
    private Map<ManufacturerReference, BusEntityData> mAppManufacturerMap;
    private Map<DistributorReference, BusEntityData> mAppDistributorMap;
    private Map<CategoryReference, ItemData> mAppStoreCategoryMap;
    private Map<Integer, Integer> mMultiProductsMap;
    private Map<Integer, CatalogStructureData> mAppStoreCatalogCategoryMap;
    private Map<Integer, Integer> mAppCategoryAssocMap;
    private Map<Integer, CatalogStructureData> mAppStoreCatalogProductMap;
    private Map<ItemReference, ItemAssocData> mAppStoreItemCategoryAssocMap;
    private Map<String, byte[]> mImageDataMap;
    private Map<Integer, FreightTableData> mFreightTablesMap;
    private Map<String, Integer> mLocalePriceDecimals;

    public MasterAppData() {

        this.mAppAccountRefMap = new HashMap<AccountReference/*accountNumer*/ , AppAccountReferenceData>();
        this.mAppManufacturerMap = new HashMap<ManufacturerReference, BusEntityData>();
        this.mAppDistributorMap = new HashMap<DistributorReference, BusEntityData>();
        this.mAppStoreCategoryMap = new HashMap<CategoryReference, ItemData>();
        this.mMultiProductsMap = new HashMap<Integer, Integer>();
        this.mAppStoreCatalogCategoryMap = new HashMap<Integer, CatalogStructureData>();
        this.mAppCategoryAssocMap = new HashMap<Integer, Integer>();
        this.mAppStoreCatalogProductMap = new HashMap<Integer, CatalogStructureData>();
        this.mAppStoreItemCategoryAssocMap = new HashMap<ItemReference, ItemAssocData>();
        this.mImageDataMap = new HashMap<String, byte[]>();
        this.mAppItemMap = new HashMap<ItemReference/*Disttributor_DistSKU*/, AppItemData>();
        this.mFreightTablesMap = new HashMap<Integer, FreightTableData>();
        this.mLocalePriceDecimals = new HashMap<String, Integer>();

    }

    public int getStoreCatalogId() {
        return mStoreCatalogId;
    }

    public void setStoreCatalogId(int mStoreCatalogId) {
        this.mStoreCatalogId = mStoreCatalogId;
    }

    public int getTradingPartnerId() {
        return mTradingPartnerId;
    }

    public void setTradingPartnerId(int pTradingPartnerId) {
        this.mTradingPartnerId = pTradingPartnerId;
    }

    public int getStoreId() {
        return mStoreId;
    }

    public void setStoreId(int pStoreId) {
        this.mStoreId = pStoreId;
    }

    public Map<AccountReference/*accountNumer*/ , AppAccountReferenceData> getAppAccountReferenceMap() {
        return mAppAccountRefMap;
    }

    public void setAppAccountReferenceMap(Map<AccountReference/*accountNumer*/ , AppAccountReferenceData> pAppAccountMap) {
        this.mAppAccountRefMap = pAppAccountMap;
    }

    public Map<ManufacturerReference, BusEntityData> getAppManufacturerMap() {
        return mAppManufacturerMap;
    }

    public void setAppManufacturerMap(Map<ManufacturerReference, BusEntityData> pAppManufacturerMap) {
        this.mAppManufacturerMap = pAppManufacturerMap;
    }

    public Map<DistributorReference, BusEntityData> getAppDistributorMap() {
        return mAppDistributorMap;
    }

    public void setAppDistributorMap(Map<DistributorReference, BusEntityData> pAppDistributorMap) {
        this.mAppDistributorMap = pAppDistributorMap;
    }

    public void setAppStoreCategoryMap(Map<CategoryReference, ItemData> pAppStoreCategoryMap) {
        this.mAppStoreCategoryMap = pAppStoreCategoryMap;
    }

    public Map<CategoryReference, ItemData> getAppStoreCategoryMap() {
        return mAppStoreCategoryMap;
    }


    public void setMultiProductsMap(Map<Integer, Integer> pMultiProductsMap) {
        this.mMultiProductsMap = pMultiProductsMap;
    }

    public Map<Integer, Integer> getMultiProductsMap() {
        return mMultiProductsMap;
    }

    public void setAppStoreCatalogCategoryMap(Map<Integer, CatalogStructureData> pAppStoreCatalogCategoryMap) {
        this.mAppStoreCatalogCategoryMap = pAppStoreCatalogCategoryMap;
    }

    public Map<Integer, CatalogStructureData> getAppStoreCatalogCategoryMap() {
        return mAppStoreCatalogCategoryMap;
    }

    public void setAppCategoryAssocMap(Map<Integer, Integer> pAppCategoryAssocMap) {
        this.mAppCategoryAssocMap = pAppCategoryAssocMap;
    }

    public Map<Integer, Integer> getAppCategoryAssocMap() {
        return mAppCategoryAssocMap;
    }

    public void setAppStoreCatalogProductMap(Map<Integer, CatalogStructureData> pAppStoreCatalogProductMap) {
        this.mAppStoreCatalogProductMap = pAppStoreCatalogProductMap;
    }

    public Map<Integer, CatalogStructureData> getAppStoreCatalogProductMap() {
        return mAppStoreCatalogProductMap;
    }

    public void setAppStoreItemCategoryAssocMap(Map<ItemReference, ItemAssocData> pAppStoreItemCategoryAssocMap) {
        this.mAppStoreItemCategoryAssocMap = pAppStoreItemCategoryAssocMap;
    }

    public Map<ItemReference, ItemAssocData> getAppStoreItemCategoryAssocMap() {
        return mAppStoreItemCategoryAssocMap;
    }

    public Map<String, byte[]> getImageDataMap() {
        return mImageDataMap;
    }

    public void setmImageDataMap(Map<String, byte[]> pImageDataMap) {
        this.mImageDataMap = pImageDataMap;
    }

    public Map<ItemReference, AppItemData> getAppItemMap() {
        return mAppItemMap;
    }

    public void setAppItemMap(Map<ItemReference, AppItemData> pAppItemMap) {
        this.mAppItemMap = pAppItemMap;
    }

    public void setFreightTablesMap(Map<Integer, FreightTableData> pFreightTablesMap) {
        this.mFreightTablesMap = pFreightTablesMap;
    }

    public Map<Integer, FreightTableData> getFreightTablesMap() {
        return mFreightTablesMap;
    }

    public void setLocalePriceDecimals(Map<String, Integer> pLocalePriceDecimals) {
        this.mLocalePriceDecimals = pLocalePriceDecimals;
    }

    public Map<String, Integer> getLocalePriceDecimals() {
        return mLocalePriceDecimals;
    }
}
