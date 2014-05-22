package com.cleanwise.service.api.dao;

import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.PriceListData;
import com.cleanwise.service.api.value.PriceListDataVector;
import com.cleanwise.service.api.value.PriceListDetailDataVector;
import com.cleanwise.service.api.value.SitePriceListView;
import com.cleanwise.service.api.wrapper.SitePriceListWrapper;

import java.sql.Connection;


public class PriceListDAO {

    public static SitePriceListView getSitePriceListView(Connection pCon, int pSiteId) throws Exception {

        SitePriceListView sitePriceLists = new SitePriceListView();

        DBCriteria dbc = new DBCriteria();

        dbc.addEqualTo(PriceListDataAccess.PRICE_LIST_STATUS_CD, RefCodeNames.PRICE_LIST_STATUS_CD.ACTIVE);
        dbc.addJoinTableEqualTo(PriceListAssocDataAccess.CLW_PRICE_LIST_ASSOC, PriceListAssocDataAccess.BUS_ENTITY_ID, pSiteId);
        dbc.addJoinCondition(PriceListDataAccess.PRICE_LIST_ID, PriceListAssocDataAccess.CLW_PRICE_LIST_ASSOC, PriceListAssocDataAccess.PRICE_LIST_ID);

        PriceListDataVector priceLists = PriceListDataAccess.select(pCon, dbc);

        SitePriceListWrapper wrapper = new SitePriceListWrapper(priceLists);

        sitePriceLists.setPriceList1(wrapper.getPriceList(1));
        sitePriceLists.setPriceList2(wrapper.getPriceList(2));
        sitePriceLists.setProprietaryLists(wrapper.getProprietaryLists());

        return sitePriceLists;

    }

    private static PriceListDataVector getPriceList(Connection pCon, int pSiteId, String pTypeCd, Integer pRank, String pStatus) throws Exception {

        DBCriteria dbc = new DBCriteria();

        if (Utility.isSet(pStatus)) {
            dbc.addEqualTo(PriceListDataAccess.PRICE_LIST_STATUS_CD, pStatus);
        }

       if (Utility.isSet(pTypeCd)) {
            dbc.addEqualTo(PriceListDataAccess.PRICE_LIST_TYPE_CD, pTypeCd);
        }

        if (pRank != null) {
            dbc.addEqualTo(PriceListDataAccess.RANK, pRank);
        }

        dbc.addJoinTableEqualTo(PriceListAssocDataAccess.CLW_PRICE_LIST_ASSOC, PriceListAssocDataAccess.BUS_ENTITY_ID, pSiteId);
        dbc.addJoinTableEqualTo(PriceListAssocDataAccess.CLW_PRICE_LIST_ASSOC, PriceListAssocDataAccess.PRICE_LIST_ASSOC_CD, RefCodeNames.PRICE_LIST_ASSOC_CD.PRICE_LIST_SITE);
        dbc.addJoinCondition(PriceListDataAccess.PRICE_LIST_ID, PriceListAssocDataAccess.CLW_PRICE_LIST_ASSOC, PriceListAssocDataAccess.PRICE_LIST_ID);

        return PriceListDataAccess.select(pCon, dbc);

    }

    public static PriceListData getActivePriceList(Connection pCon, int pSiteId, Integer pRank) throws Exception {
        PriceListDataVector priceLists = getPriceList(pCon,
                pSiteId,
                RefCodeNames.PRICE_LIST_TYPE_CD.PRICE_LIST,
                pRank,
                RefCodeNames.PRICE_LIST_STATUS_CD.ACTIVE);
        if (!priceLists.isEmpty()) {
            return (PriceListData) priceLists.get(0);
        } else {
            return null;
        }
    }

    public static PriceListDetailDataVector getPriceListItems(Connection pCon, int pPriceListId) throws Exception {
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(PriceListDetailDataAccess.PRICE_LIST_ID,pPriceListId);
        return  PriceListDetailDataAccess.select(pCon, dbc);
    }


}
