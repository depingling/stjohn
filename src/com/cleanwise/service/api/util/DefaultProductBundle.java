package com.cleanwise.service.api.util;

import com.cleanwise.service.api.value.*;

import java.util.Map;
import java.util.HashMap;

public class DefaultProductBundle implements ProductBundle {

    private String mStoreType;
    private int mSiteId;
    private Map<Integer, ProductData> mProducts;


    public DefaultProductBundle(String pStoreType, int pSiteId, ProductDataVector pProducts) {
        this.mStoreType = pStoreType;
        this.mSiteId = pSiteId;
        this.mProducts = new HashMap<Integer, ProductData>(pProducts.size());
        for (Object oProductData : pProducts) {
            ProductData productData = (ProductData) oProductData;
            this.mProducts.put(productData.getProductId(), productData);
        }
    }

    public PriceValue getPriceValue(ProductPriceView pProductPrice) {
        if (pProductPrice.getContractPrice() != null) {
            return new PriceValue(pProductPrice.getContractPrice(), PriceValue.PRICE_SOURCE_CODE.CONTRACT);
        } else {
            return new PriceValue(pProductPrice.getListPrice(), PriceValue.PRICE_SOURCE_CODE.META);
        }
    }

    public SkuValue getSkuValue(ProductSkuView pProductSku) {

        String storeTypeCd = Utility.strNN(getStoreType());

        if (Utility.isSet(storeTypeCd)) {

            int type = 0;
            if (storeTypeCd.equals(RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR)) {
                type = 1;
            }

            String custSku = pProductSku.getCustomerSkuNum();
            if (custSku != null && custSku.trim().length() > 0) {
                return new SkuValue(custSku, SkuValue.CATALOG_SKU);
            } else {
                switch (type) {
                    case 1:
                        if (pProductSku.getDistributorSkuNum() == null) {
                            //set the item to use the manufacturer sku as the default if the distributor sku is empty.
                            return new SkuValue(pProductSku.getManufacturerSkuNum(), SkuValue.MANUFACTURER_SKU);
                        } else {
                            return new SkuValue(pProductSku.getDistributorSkuNum(), SkuValue.DISTRIBUTOR_SKU);
                        }
                    case 2:
                        return new SkuValue(pProductSku.getManufacturerSkuNum(), SkuValue.MANUFACTURER_SKU);
                    default:
                        return new SkuValue(pProductSku.getStoreSkuNum(), SkuValue.CLW_SKU);

                }
            }
        } else {
            return null;
        }
    }

    public SkuValue getSkuValue(int pProductId) {

        Map<Integer, ProductData> produtcs = getProducts();

        ProductData product = produtcs.get(pProductId);
        if (product != null) {
            return getSkuValue(product.getProductSku());
        } else {
            return null;
        }
    }

    public PriceValue getPriceValue(int pProductId) {

        Map<Integer, ProductData> produtcs = getProducts();

        ProductData product = produtcs.get(pProductId);
        if (product != null) {
            return getPriceValue(product.getProductPrice());
        } else {
            return null;
        }
    }

    protected String getStoreType() {
        return mStoreType;
    }

    protected int geSiteId() {
        return mSiteId;
    }

    public Map<Integer, ProductData> getProducts() {
        return mProducts;
    }
}
