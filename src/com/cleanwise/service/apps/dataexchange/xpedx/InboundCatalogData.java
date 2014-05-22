package com.cleanwise.service.apps.dataexchange.xpedx;

import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

public class InboundCatalogData implements Serializable {

    private String mLoaderField;
    private String mLocale;
    private Integer mFreightTableId;
    private Map<ItemReference/*Disttributor_DistSKU*/, InboundItemData> mInboundItemMap;

    public InboundCatalogData() {
        this.mInboundItemMap = new HashMap<ItemReference/*Disttributor_DistSKU*/, InboundItemData>();
    }

    public String getLoaderField() {
        return mLoaderField;
    }

    public void setLoaderField(String loaderField) {
        this.mLoaderField = loaderField;
    }

    public String getLocale() {
        return mLocale;
    }

    public void setLocale(String locale) {
        this.mLocale = locale;
    }

    public Map<ItemReference, InboundItemData> getInboundItemMap() {
        return mInboundItemMap;
    }

    public void setInboundItemMap(Map<ItemReference, InboundItemData> pInboundItemMap) {
        this.mInboundItemMap = pInboundItemMap;
    }

    public Integer getFreightTableId() {
        return mFreightTableId;
    }

    public void setFreightTableId(Integer freightTableId) {
        this.mFreightTableId = freightTableId;
    }

}
