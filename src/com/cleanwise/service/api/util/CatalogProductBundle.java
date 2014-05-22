package com.cleanwise.service.api.util;

import com.cleanwise.service.api.value.ProductDataVector;

public class CatalogProductBundle extends PriceListProductBundle {

    public CatalogProductBundle(String pStoreType, int pSiteId, ProductDataVector pProducts) {
        super(pStoreType, pSiteId, pProducts);
    }
}
