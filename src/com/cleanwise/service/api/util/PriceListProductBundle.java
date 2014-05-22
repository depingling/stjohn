package com.cleanwise.service.api.util;

import com.cleanwise.service.api.value.*;


public class PriceListProductBundle extends DefaultProductBundle {

    public PriceListProductBundle(String pStoreType, int pSiteId, ProductDataVector pProducts) {
        super(pStoreType, pSiteId, pProducts);
    }

    public PriceValue getPriceValue(ProductPriceView pProductPrice) {
        if (pProductPrice.getPriceList1() != null) {
            return new PriceValue(pProductPrice.getPriceList1(), PriceValue.PRICE_SOURCE_CODE.PRICE_LIST_RANK_1);
        } else if (pProductPrice.getPriceList2() != null) {
            return new PriceValue(pProductPrice.getPriceList2(), PriceValue.PRICE_SOURCE_CODE.PRICE_LIST_RANK_2);
        } else {
            return super.getPriceValue(pProductPrice);
        }
    }

    public SkuValue getSkuValue(ProductSkuView pProductSku) {
        if (Utility.isSet(this.getStoreType())) {
            if (Utility.isSet(pProductSku.getPriceListRank1SkuNum())) {
                return new SkuValue(pProductSku.getPriceListRank1SkuNum(), SkuValue.PRICE_LIST_RANK_1);
            } else if (Utility.isSet(pProductSku.getPriceListRank2SkuNum())) {
                return new SkuValue(pProductSku.getPriceListRank2SkuNum(), SkuValue.PRICE_LIST_RANK_2);
            } else {
                return super.getSkuValue(pProductSku);
            }
        } else {
            return null;
        }
    }
}
