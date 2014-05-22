package com.cleanwise.service.api.wrapper;

import com.cleanwise.service.api.value.PriceListDataVector;
import com.cleanwise.service.api.value.PriceListData;
import com.cleanwise.service.api.util.RefCodeNames;


public class SitePriceListWrapper {

    private  PriceListDataVector mSitePriceLists;

    public SitePriceListWrapper(PriceListDataVector pPriceLists) {
       this.mSitePriceLists  = pPriceLists;
    }

    public PriceListData getPriceList(int pRank) {
        for (Object oPriceListData : this.mSitePriceLists) {
            PriceListData priceListData = (PriceListData) oPriceListData;
            if (RefCodeNames.PRICE_LIST_TYPE_CD.PRICE_LIST.equals(priceListData.getPriceListTypeCd()) && priceListData.getRank() == pRank) {
                return priceListData;
            }
        }
        return null;
    }

    public PriceListDataVector getProprietaryLists() {
        PriceListDataVector list  = new PriceListDataVector();
        for (Object oPriceListData : this.mSitePriceLists) {
            PriceListData priceListData = (PriceListData) oPriceListData;
            if (RefCodeNames.PRICE_LIST_TYPE_CD.PROPRIETARY_LIST.equals(priceListData.getPriceListTypeCd())) {
               list.add(priceListData);
            }
        }
        return list;
    }
}
