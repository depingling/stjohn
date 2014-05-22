package com.cleanwise.service.apps.dataexchange.xpedx;

import com.cleanwise.service.api.value.ContentData;
import com.cleanwise.service.api.value.ItemMappingData;
import com.cleanwise.service.api.value.ItemMetaData;
import com.cleanwise.service.api.value.ItemData;

import java.util.Map;
import java.io.Serializable;


public class AppItemData implements Serializable {

    private ItemData mItemData;
    private Map<String, ItemMetaData> mMeta;
    private ItemMappingData mDistributor;
    private ItemMappingData mManufacturer;
    private ContentData mImage;
    private ItemMappingData mStore;

    public ItemData getItemData() {
        return mItemData;
    }

    public void setItemData(ItemData pItemData) {
        this.mItemData = pItemData;
    }

    public Map<String, ItemMetaData> getMeta() {
        return mMeta;
    }

    public void setMeta(Map<String, ItemMetaData> pMeta) {
        this.mMeta = pMeta;
    }

    public ItemMappingData getDistributor() {
        return mDistributor;
    }

    public void setDistributor(ItemMappingData pDistributor) {
        this.mDistributor = pDistributor;
    }

    public ItemMappingData getManufacturer() {
        return mManufacturer;
    }

    public void setManufacturer(ItemMappingData pManufacturer) {
        this.mManufacturer = pManufacturer;
    }

    public void setImage(ContentData pImage) {
        this.mImage = pImage;
    }

    public ContentData getImage() {
        return mImage;
    }

    public ItemMappingData getStore() {
        return mStore;
    }

    public void setStore(ItemMappingData pStore) {
        this.mStore = pStore;
    }
}

