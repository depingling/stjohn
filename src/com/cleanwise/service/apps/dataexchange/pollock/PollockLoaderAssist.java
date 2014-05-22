package com.cleanwise.service.apps.dataexchange.pollock;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.session.OrderGuide;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import org.apache.log4j.Logger;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;


public class PollockLoaderAssist {

    protected static Logger log = Logger.getLogger(PollockLoaderAssist.class);

    private static final String SITE_OF_ACCOUNT = "SITE_OF_ACCOUNT";
    private static final String ACCOUNT_OF_STORE = "ACCOUNT_OF_STORE";
    private static final String SPACE = " ";

    public static Map<String/*AccountNumber*/, Set<Integer/*Id*/>> defineAccountId(Connection pCon, int pStoreId, Set<String> /*AccountNumber*/ pAccountNumbers) throws Exception {

        log.info("defineAccountId()=> BEGIN");

        Map<String/*AccountNumber*/, Set<Integer/*Id*/>> result = new HashMap<String/*AccountNumber*/, Set<Integer/*Id*/>>();

        DBCriteria beCriteria = new DBCriteria();

        beCriteria.addOneOf(PropertyDataAccess.CLW_VALUE, new ArrayList<String>(pAccountNumbers));
        beCriteria.addNotEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD, RefCodeNames.PROPERTY_STATUS_CD.INACTIVE);
        beCriteria.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCT_REF_NUM);

        beCriteria.addJoinCondition(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY1_ID, BusEntityDataAccess.CLW_BUS_ENTITY, BusEntityDataAccess.BUS_ENTITY_ID);
        beCriteria.addJoinCondition(PropertyDataAccess.CLW_PROPERTY, PropertyDataAccess.BUS_ENTITY_ID, BusEntityDataAccess.CLW_BUS_ENTITY, BusEntityDataAccess.BUS_ENTITY_ID);

        beCriteria.addJoinTableEqualTo(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
        beCriteria.addJoinTableEqualTo(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY2_ID, pStoreId);
        beCriteria.addJoinTableEqualTo(BusEntityDataAccess.CLW_BUS_ENTITY, BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);

        PropertyDataVector accountRefProps = PropertyDataAccess.select(pCon, beCriteria);

        for (Object oProp : accountRefProps) {

            PropertyData prop = (PropertyData) oProp;

            Set<Integer> accountIds = result.get(prop.getValue());
            if (accountIds == null) {
                accountIds = new HashSet<Integer>();
                result.put(prop.getValue(), accountIds);
            }

            accountIds.add(prop.getBusEntityId());

        }

        log.info("defineAccountId()=> END.");

        return result;
    }


    public static Map<String, Set<Integer>> defineSiteId(Connection pCon, int pAccountId, Set<String> pSiteRefs) throws SQLException {

        log.info("defineSiteId()=> BEGIN");

        Map<String/*SiteNumber*/, Set<Integer/*Id*/>> result = new HashMap<String/*SiteNumber*/, Set<Integer/*Id*/>>();

        DBCriteria dbc = new DBCriteria();

        dbc.addOneOf(PropertyDataAccess.CLW_VALUE, new ArrayList<String>(pSiteRefs));
        dbc.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD, RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
//        dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.EXTRA);
        dbc.addEqualTo(PropertyDataAccess.SHORT_DESC, RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER);

        dbc.addJoinCondition(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY1_ID, BusEntityDataAccess.CLW_BUS_ENTITY, BusEntityDataAccess.BUS_ENTITY_ID);
        dbc.addJoinCondition(PropertyDataAccess.CLW_PROPERTY, PropertyDataAccess.BUS_ENTITY_ID, BusEntityDataAccess.CLW_BUS_ENTITY, BusEntityDataAccess.BUS_ENTITY_ID);

        dbc.addJoinTableEqualTo(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
        dbc.addJoinTableEqualTo(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY2_ID, pAccountId);
        dbc.addJoinTableEqualTo(BusEntityDataAccess.CLW_BUS_ENTITY, BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);

        PropertyDataVector siteRefProps = PropertyDataAccess.select(pCon, dbc);

        for (Object oProp : siteRefProps) {

            PropertyData prop = (PropertyData) oProp;

            Set<Integer> siteIds = result.get(prop.getValue());
            if (siteIds == null) {
                siteIds = new HashSet<Integer>();
                result.put(prop.getValue(), siteIds);
            }

            siteIds.add(prop.getBusEntityId());

        }

        log.info("defineSiteId()=> END.");

        return result;
    }

    public static Map<Integer, Map<String, Map<Integer, List<OrderGuideStructureData>>>> defineOrderGuides(Connection pCon, Set<Integer> pBusEntityIds, Set<String> pOrderGuideNames) throws SQLException {

        log.info("defineOrderGuides()=> BEGIN");

        log.info("defineOrderGuides()=> pBusEntityIds: " + pBusEntityIds);
        log.info("defineOrderGuides()=> pOrderGuideNames: " + pOrderGuideNames);

        Map<Integer, Map<String, Map<Integer, List<OrderGuideStructureData>>>> result = new HashMap<Integer, Map<String, Map<Integer, List<OrderGuideStructureData>>>>();

        if (!pBusEntityIds.isEmpty() && !pOrderGuideNames.isEmpty()) {

            DBCriteria dbc = new DBCriteria();

            dbc.addOneOf(OrderGuideDataAccess.BUS_ENTITY_ID, new ArrayList<Integer>(pBusEntityIds));
            dbc.addOneOf(OrderGuideDataAccess.SHORT_DESC, new ArrayList<String>(pOrderGuideNames));
            dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, RefCodeNames.ORDER_GUIDE_TYPE_CD.CUSTOM_ORDER_GUIDE);

            OrderGuideDataVector orderGuides = OrderGuideDataAccess.select(pCon, dbc);

            IdVector orderGuideIds = Utility.toIdVector(orderGuides);

            dbc = new DBCriteria();
            dbc.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, orderGuideIds);

            OrderGuideStructureDataVector orderGuidesItems = OrderGuideStructureDataAccess.select(pCon, dbc);

            Map<Integer, List<OrderGuideStructureData>> orderGuideItems = new HashMap<Integer, List<OrderGuideStructureData>>();
            for (Object oOrderGuideItem : orderGuidesItems) {
                OrderGuideStructureData orderGuideItem = (OrderGuideStructureData) oOrderGuideItem;
                List<OrderGuideStructureData> items = orderGuideItems.get(orderGuideItem.getOrderGuideId());
                if (items == null) {
                    items = new ArrayList<OrderGuideStructureData>();
                    orderGuideItems.put(orderGuideItem.getOrderGuideId(), items);
                }
                items.add(orderGuideItem);
            }

            for (Object oOrderGuide : orderGuides) {
                OrderGuideData orderGuide = (OrderGuideData) oOrderGuide;

                Map<String, Map<Integer, List<OrderGuideStructureData>>> orderGuideMap = result.get(orderGuide.getBusEntityId());
                if (orderGuideMap == null) {
                    orderGuideMap = new HashMap<String, Map<Integer, List<OrderGuideStructureData>>>();
                    result.put(orderGuide.getBusEntityId(), orderGuideMap);
                }

                Map<Integer, List<OrderGuideStructureData>> beOrderGuide = orderGuideMap.get(orderGuide.getShortDesc());
                if (beOrderGuide == null) {
                    beOrderGuide = new HashMap<Integer, List<OrderGuideStructureData>>();
                    orderGuideMap.put(orderGuide.getShortDesc(), beOrderGuide);
                }
                List<OrderGuideStructureData> details = orderGuideItems.get(orderGuide.getOrderGuideId());
                beOrderGuide.put(orderGuide.getOrderGuideId(), details == null ? new ArrayList<OrderGuideStructureData>() : details);
            }
        }

        log.info("defineOrderGuides()=> END. Ret.Size: "+result.size());

        return result;

    }

    public static Map<Integer, Map<String, Map<Integer, OrderGuideData>>> defineOrderGuidesVer2(Connection pCon, Set<Integer> pBusEntityIds, Set<String> pOrderGuideNames) throws SQLException {

        log.info("defineOrderGuidesVer2()=> BEGIN");

        log.info("defineOrderGuidesVer2()=> pBusEntityIds: " + pBusEntityIds);
        log.info("defineOrderGuidesVer2()=> pOrderGuideNames: " + pOrderGuideNames);

        Map<Integer/*BusEntityId*/, Map<String/*ShortDesc*/, Map<Integer, OrderGuideData>>> result = new HashMap<Integer, Map<String, Map<Integer, OrderGuideData>>>();

        if (!pBusEntityIds.isEmpty() && !pOrderGuideNames.isEmpty()) {

            DBCriteria dbc = new DBCriteria();

            dbc.addOneOf(OrderGuideDataAccess.BUS_ENTITY_ID, new ArrayList<Integer>(pBusEntityIds));
            dbc.addOneOf(OrderGuideDataAccess.SHORT_DESC, new ArrayList<String>(pOrderGuideNames));
            dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, RefCodeNames.ORDER_GUIDE_TYPE_CD.CUSTOM_ORDER_GUIDE);

            OrderGuideDataVector orderGuides = OrderGuideDataAccess.select(pCon, dbc);

            for (Object oOrderGuide : orderGuides) {

                OrderGuideData orderGuide = (OrderGuideData) oOrderGuide;

                Map<String, Map<Integer, OrderGuideData>> orderGuideMap = result.get(orderGuide.getBusEntityId());
                if (orderGuideMap == null) {
                    orderGuideMap = new HashMap<String, Map<Integer, OrderGuideData>>();
                    result.put(orderGuide.getBusEntityId(), orderGuideMap);
                }

                Map<Integer, OrderGuideData> beOrderGuide = orderGuideMap.get(orderGuide.getShortDesc());
                if (beOrderGuide == null) {
                    beOrderGuide = new HashMap<Integer, OrderGuideData>();
                    orderGuideMap.put(orderGuide.getShortDesc(), beOrderGuide);
                }
                beOrderGuide.put(orderGuide.getOrderGuideId(), orderGuide);

            }
        }

        log.info("defineOrderGuidesVer2()=> END. Ret.Size: " + result.size());

        return result;

    }

    public static Map<String, Set<Integer>> defineItemId(Connection pCon, Integer pDistrId, Set<String> pDistrSkus) throws SQLException {

        log.info("defineItemId()=> BEGIN");
        Map<String, Set<Integer>> result = new HashMap<String, Set<Integer>>();

        DBCriteria cr = new DBCriteria();
        cr.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID, pDistrId);
        cr.addOneOfIsolated(ItemMappingDataAccess.CLW_ITEM_MAPPING,ItemMappingDataAccess.ITEM_NUM, new ArrayList<String>(pDistrSkus));
        cr.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
        cr.addJoinCondition(ItemMappingDataAccess.ITEM_ID, ItemDataAccess.CLW_ITEM, ItemDataAccess.ITEM_ID);
        cr.addJoinTableEqualTo(ItemDataAccess.CLW_ITEM, ItemDataAccess.ITEM_STATUS_CD, RefCodeNames.ITEM_STATUS_CD.ACTIVE);

        ItemMappingDataVector list = ItemMappingDataAccess.select(pCon, cr);

        for (int i = 0; list != null && i < list.size(); i++) {

            ItemMappingData item = (ItemMappingData) list.get(i);

            String itemReference = item.getItemNum().trim();

            Set<Integer> itemIdSet = result.get(itemReference);
            if (itemIdSet == null) {
                itemIdSet = new HashSet<Integer>();
                result.put(itemReference, itemIdSet);
            }
            itemIdSet.add(item.getItemId());

        }
        log.info("defineItemId()=> END.");

        return result;

    }

    public static BusEntityDataVector defineDistributor(Connection pCon, int pStoreId) throws Exception {

        BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
        crit.setStoreBusEntityIds(Utility.toIdVector(pStoreId));
        crit.setSearchForInactive(false);

        return BusEntityDAO.getBusEntityByCriteria(pCon, crit, RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
    }

    public static BusEntityDataVector getTradingPartnerEntities(Connection pCon, int pTradingPartnerId, String pBusEntityTypeCd) throws Exception {

        DBCriteria dbCriteria = new DBCriteria();

        dbCriteria.addJoinCondition(BusEntityDataAccess.CLW_BUS_ENTITY,
                BusEntityDataAccess.BUS_ENTITY_ID,
                TradingPartnerAssocDataAccess.CLW_TRADING_PARTNER_ASSOC,
                TradingPartnerAssocDataAccess.BUS_ENTITY_ID);

        dbCriteria.addJoinTableEqualTo(
                TradingPartnerAssocDataAccess.CLW_TRADING_PARTNER_ASSOC,
                TradingPartnerAssocDataAccess.TRADING_PARTNER_ID,
                pTradingPartnerId);

        if (Utility.isSet(pBusEntityTypeCd)) {
            dbCriteria.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, pBusEntityTypeCd);
        }

        return BusEntityDataAccess.select(pCon, dbCriteria);

    }

    public static List<Integer> getOrderGuideIds(Connection pCon, int pStoreId, String pOrderGuideName) throws Exception {

        List<Integer> ids = new ArrayList<Integer>();

        DBCriteria dbc = new DBCriteria();

        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pStoreId);

        String accountReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);

        dbc = new DBCriteria();
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
        dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, accountReq);

        String siteReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, dbc);

        dbc = new DBCriteria();
        dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, RefCodeNames.ORDER_GUIDE_TYPE_CD.CUSTOM_ORDER_GUIDE);
        dbc.addEqualTo(OrderGuideDataAccess.SHORT_DESC,  pOrderGuideName);
        dbc.addOneOf(OrderGuideDataAccess.BUS_ENTITY_ID,  accountReq);

        ids.addAll(OrderGuideDataAccess.selectIdOnly(pCon, dbc));

        dbc = new DBCriteria();
        dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, RefCodeNames.ORDER_GUIDE_TYPE_CD.CUSTOM_ORDER_GUIDE);
        dbc.addEqualTo(OrderGuideDataAccess.SHORT_DESC,  pOrderGuideName);
        dbc.addOneOf(OrderGuideDataAccess.BUS_ENTITY_ID,  siteReq);

        ids.addAll(OrderGuideDataAccess.selectIdOnly(pCon, dbc));

        return ids;
    }

    public static OrderGuideData getStoreOrderGuide(Connection pCon, int pStoreId, String pOrderGuideName) throws Exception {

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, RefCodeNames.ORDER_GUIDE_TYPE_CD.CUSTOM_ORDER_GUIDE);
        dbc.addEqualTo(OrderGuideDataAccess.SHORT_DESC, pOrderGuideName);
        dbc.addEqualTo(OrderGuideDataAccess.BUS_ENTITY_ID, pStoreId);

        OrderGuideDataVector storeOrderGuides = OrderGuideDataAccess.select(pCon, dbc);
        if (storeOrderGuides.isEmpty()) {
            return null;
        } else {
            return (OrderGuideData) storeOrderGuides.get(0);
        }
    }

    public static int[] deleteOrderGuides(List pOrderGuidesToDelete) throws Exception {
        OrderGuide orderGuideEjb = APIAccess.getAPIAccess().getOrderGuideAPI();
        return orderGuideEjb.removeOrderGuides(pOrderGuidesToDelete);
    }

     public static OrderGuideData createOrderGuideVer2(String pOrderGuideName, Integer pBusEntityId, String pUser) throws Exception {

        OrderGuideData orderGuide = new OrderGuideData();

        orderGuide.setBusEntityId(pBusEntityId);
        orderGuide.setShortDesc(pOrderGuideName);
        orderGuide.setAddBy(pUser);
        orderGuide.setModBy(pUser);
        orderGuide.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.CUSTOM_ORDER_GUIDE);

        OrderGuide orderGuideEjb = APIAccess.getAPIAccess().getOrderGuideAPI();
        orderGuide = orderGuideEjb.updateOrderGuideData(orderGuide);

        return orderGuide;
    }

    public static int createOrderGuide(String pOrderGuideName, Integer pBusEntityId, String pUser) throws Exception {

        OrderGuideData orderGuide = new OrderGuideData();

        orderGuide.setBusEntityId(pBusEntityId);
        orderGuide.setShortDesc(pOrderGuideName);
        orderGuide.setAddBy(pUser);
        orderGuide.setModBy(pUser);
        orderGuide.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.CUSTOM_ORDER_GUIDE);

        OrderGuide orderGuideEjb = APIAccess.getAPIAccess().getOrderGuideAPI();
        orderGuide = orderGuideEjb.updateOrderGuideData(orderGuide);

        return orderGuide.getOrderGuideId();
    }

    public static OrderGuideData updateOrderGuide(OrderGuideData pOrderGuide, String pUser) throws Exception {

        pOrderGuide.setModBy(pUser);
        OrderGuide orderGuideEjb = APIAccess.getAPIAccess().getOrderGuideAPI();
        pOrderGuide = orderGuideEjb.updateOrderGuideData(pOrderGuide);

        return pOrderGuide;
    }

    public static int deleteOrderGuideItems(IdVector pOrderGuideStructureIds) throws Exception {
        OrderGuide orderGuideEjb = APIAccess.getAPIAccess().getOrderGuideAPI();
        return orderGuideEjb.removeOrderGuideStructureItems(pOrderGuideStructureIds);
    }

    public static int deleteOrderGuideItems(Connection pCon, List pOrderGuideIds, List<Integer> pItemIds) throws Exception {
        DBCriteria dbc = new DBCriteria();
        dbc.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, pOrderGuideIds);
        dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID, pItemIds);
        return OrderGuideStructureDataAccess.remove(pCon, dbc);
    }

    public static OrderGuideStructureData createOrderGuideItem(Integer pItemId,
                                                               String pCategoryName,
                                                               Integer pSortOrder) {

        OrderGuideStructureData ogsd = OrderGuideStructureData.createValue();

        ogsd.setItemId(pItemId);
        ogsd.setSortOrder(pSortOrder);
        ogsd.setCustCategory(pCategoryName);

        return ogsd;

    }

    public static int[] createAndUpdateOrderGuideItems(Connection pCon,
                                                       List pOrderGuideIds,
                                                       Map<Integer, IPollockOrderGuideLoaderImpl.IPollockCustomOrderGuideDetail> pItems,
                                                       String pUserName) throws SQLException {

        int nu = 0;
        int nc = 0;
        int processed = 0;

        log.info("createAndUpdateOrderGuideItems()=> BEGIN");

        if (pItems != null && !pItems.isEmpty()) {

            log.info("createAndUpdateOrderGuideItems()=>" +
                    " pItems.Size: " + pItems.size() +
                    ", pOrderGuides.Size: " + pOrderGuideIds == null ? 0 : pOrderGuideIds.size()
            );

            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, pOrderGuideIds);
            dbc.addOneOf(OrderGuideStructureDataAccess.ITEM_ID, new ArrayList<Integer>(pItems.keySet()));

            OrderGuideStructureDataVector existingList = OrderGuideStructureDataAccess.select(pCon, dbc);

            Map<Integer, Set<Integer>> existingOgiMap = new HashMap<Integer, Set<Integer>>();
            for (Object oExistItem : existingList) {
                OrderGuideStructureData existItem = (OrderGuideStructureData) oExistItem;
                Set<Integer> ogIdSet = existingOgiMap.get(existItem.getItemId());
                if (ogIdSet == null) {
                    ogIdSet = new HashSet<Integer>();
                    existingOgiMap.put(existItem.getItemId(), ogIdSet);
                }
                ogIdSet.add(existItem.getOrderGuideId());
            }


            //update

            for (Object oItemId : pItems.keySet()) {

                Integer itemId = (Integer) oItemId;
                IPollockOrderGuideLoaderImpl.IPollockCustomOrderGuideDetail item = pItems.get(itemId);

                Set<Integer> ogIdSet = existingOgiMap.get(itemId);

                if (ogIdSet != null && !ogIdSet.isEmpty()) {
                    String updateSQL = "UPDATE CLW_ORDER_GUIDE_STRUCTURE SET " +
                            "CUST_CATEGORY = ?," +
                            " SORT_ORDER = ?," +
                            " MOD_DATE = ?," +
                            " MOD_BY = ?" +
                            " WHERE ORDER_GUIDE_ID IN (" + Utility.toCommaSting(ogIdSet) + ")" +
                            " AND ITEM_ID = ?" +
                            " AND (CUST_CATEGORY != ? OR SORT_ORDER != ? )";

                    PreparedStatement pstmt = pCon.prepareStatement(updateSQL);

                    int i = 1;

                    pstmt.setString(i++, item.getCategoryName());
                    pstmt.setInt(i++, item.getSequenceNumber());
                    pstmt.setTimestamp(i++, DBAccess.toSQLTimestamp(new Date()));
                    pstmt.setString(i++, pUserName);

                    pstmt.setInt(i++, itemId);
                    pstmt.setString(i++, item.getCategoryName());
                    pstmt.setInt(i, item.getSequenceNumber());

                    nu += pstmt.executeUpdate();
                    processed++;
                    pstmt.close();
                }

            }

            log.info("createAndUpdateOrderGuideItems()=> UPDATE: item processed = " + processed);

            //insert
            Map<Integer, Set<Integer>> toCreareOgiMap = new HashMap<Integer, Set<Integer>>();
            for (Integer itemId : pItems.keySet()) {

                Set<Integer> orderGuideIds = existingOgiMap.get(itemId);

                if (orderGuideIds != null && !orderGuideIds.isEmpty()) {

                    if (!orderGuideIds.containsAll(pOrderGuideIds)) {
                        for (Object oOrderGuideId : pOrderGuideIds) {
                            Integer orderGuideId = (Integer) oOrderGuideId;
                            if (!orderGuideIds.contains(orderGuideId)) {
                                Set<Integer> ogIdSet = toCreareOgiMap.get(itemId);
                                if (ogIdSet == null) {
                                    ogIdSet = new HashSet<Integer>();
                                    toCreareOgiMap.put(itemId, ogIdSet);
                                }
                                ogIdSet.add(orderGuideId);
                            }
                        }
                    }
                } else {

                    for (Object oOrderGuideId : pOrderGuideIds) {
                        Integer orderGuideId = (Integer) oOrderGuideId;
                        Set<Integer> ogIdSet = toCreareOgiMap.get(itemId);
                        if (ogIdSet == null) {
                            ogIdSet = new HashSet<Integer>();
                            toCreareOgiMap.put(itemId, ogIdSet);
                        }
                        ogIdSet.add(orderGuideId);
                    }
                }
            }

            String insertSQL = "INSERT INTO CLW_ORDER_GUIDE_STRUCTURE (" +
                    "ORDER_GUIDE_STRUCTURE_ID," +
                    "ORDER_GUIDE_ID," +
                    "ITEM_ID," +
                    "CATEGORY_ITEM_ID," +
                    "CUST_CATEGORY," +
                    "QUANTITY," +
                    "SORT_ORDER," +
                    "ADD_DATE," +
                    "ADD_BY," +
                    "MOD_DATE," +
                    "MOD_BY) " +
                    "VALUES(CLW_ORDER_GUIDE_STRUCTURE_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pstmt = pCon.prepareStatement(insertSQL);

            processed = 0;
            for (Integer itemId : pItems.keySet()) {

                Set<Integer> orderGuideIds = toCreareOgiMap.get(itemId);
                IPollockOrderGuideLoaderImpl.IPollockCustomOrderGuideDetail item = pItems.get(itemId);

                if (item != null && orderGuideIds != null && !orderGuideIds.isEmpty()) {

                    for (Integer orderGuideId : orderGuideIds) {

                        int i = 1;

                        java.util.Date current = new java.util.Date(System.currentTimeMillis());

                        pstmt.setInt(i++, orderGuideId);
                        pstmt.setInt(i++, itemId);
                        pstmt.setInt(i++, Utility.intNN(null));
                        pstmt.setString(i++, item.getCategoryName());
                        pstmt.setInt(i++, Utility.intNN(null));
                        pstmt.setInt(i++, item.getSequenceNumber());
                        pstmt.setTimestamp(i++, DBAccess.toSQLTimestamp(current));
                        pstmt.setString(i++, pUserName);
                        pstmt.setTimestamp(i++, DBAccess.toSQLTimestamp(current));
                        pstmt.setString(i, pUserName);

                        processed++;
                        if (processed % 10000 == 0) {
                            log.info("createAndUpdateOrderGuideItems()=> Calling execute batch at 10000 records");
                            int[] na = pstmt.executeBatch();
                            nc += na.length;
                        }

                        pstmt.addBatch();
                    }
                }
            }

            int[] na = pstmt.executeBatch();
            nc += na.length;
            pstmt.close();

            log.info("createAndUpdateOrderGuideItems()=> INSERT: item processed = " + processed);
        }

        log.info("createAndUpdateOrderGuideItems()=> END.");

        return new int[]{nu, nc};
    }


    public static List<OrderGuideStructureData> addNewItems(Integer pOrderGuideId,
                                                            List<OrderGuideStructureData> pItems,
                                                            String pUser) throws Exception {
        return updateOrderGuideItems(pOrderGuideId, pItems, pUser);
    }

    public static List<OrderGuideStructureData> updateOrderGuideItems(Integer pOrderGuideId,
                                                                      List<OrderGuideStructureData> pItems,
                                                                      String pUser) throws Exception {
        OrderGuide orderGuideEjb = APIAccess.getAPIAccess().getOrderGuideAPI();
        return orderGuideEjb.updateItems(pOrderGuideId, pItems, pUser);

    }

    public static int cloneOrderGuideItems(Connection pCon, int pTemplateId, int pCloneId, String pUser) throws SQLException {

        int n = 0;

        log.info("cloneOrderGuideItems()=> BEGIN, pTemplateId: " + pTemplateId + ", pCloneId: " + pCloneId);

        if (pTemplateId > 0 && pCloneId > 0) {

            String sql = "INSERT INTO CLW_ORDER_GUIDE_STRUCTURE " +
                    "(ORDER_GUIDE_STRUCTURE_ID," +
                    "ORDER_GUIDE_ID," +
                    "ITEM_ID," +
                    "CATEGORY_ITEM_ID," +
                    "CUST_CATEGORY,"+
                    "QUANTITY,"+
                    "SORT_ORDER," +
                    "ADD_DATE," +
                    "ADD_BY," +
                    "MOD_DATE," +
                    "MOD_BY) SELECT CLW_ORDER_GUIDE_STRUCTURE_SEQ.NEXTVAL, ?, ITEM_ID, 0, CUST_CATEGORY, 0, SORT_ORDER, ?, ?, ?, ?  FROM CLW_ORDER_GUIDE_STRUCTURE  WHERE ORDER_GUIDE_ID = ?  ";

            PreparedStatement pstmt = null;
            try {

                log.info("cloneOrderGuideItems()=> SQL: " + sql);

                pstmt = pCon.prepareStatement(sql);
                int i = 1;

                java.util.Date current = new java.util.Date(System.currentTimeMillis());

                pstmt.setInt(i++, pCloneId);
                pstmt.setTimestamp(i++, DBAccess.toSQLTimestamp(current));
                pstmt.setString(i++, pUser);
                pstmt.setTimestamp(i++, DBAccess.toSQLTimestamp(current));
                pstmt.setString(i++, pUser);

                pstmt.setInt(i, pTemplateId);

                n = pstmt.executeUpdate();

            } finally {
                if (pstmt != null) {
                    pstmt.close();
                }
            }
        }

        log.info("cloneOrderGuideItems()=> " + n + " row(s) inserted");
        log.info("cloneOrderGuideItems()=> END.");

        return n;
    }

    public static int deleteOrderGuideItems(Connection pCon, int pStoreOrderGuideId, List pCustOrderGuideIds) throws SQLException {

        DBCriteria dbc;
        int n;

        log.info("deleteOrderGuideItems()=> BEGIN.");

        log.debug("deleteOrderGuideItems()=> pStoreOrderGuideId: " + pStoreOrderGuideId + ",  pCustOrderGuides: " + pCustOrderGuideIds);

        dbc = new DBCriteria();
        dbc.addEqualTo(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, pStoreOrderGuideId);

        String storeOgiSql = OrderGuideStructureDataAccess.getSqlSelectIdOnly(OrderGuideStructureDataAccess.ITEM_ID, dbc);

        dbc = new DBCriteria();
        dbc.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID,  pCustOrderGuideIds);
        dbc.addNotOneOf(OrderGuideStructureDataAccess.ITEM_ID, storeOgiSql);

        n = OrderGuideStructureDataAccess.remove(pCon, dbc);

        log.info("deleteOrderGuideItems()=> " + n + " row(s) deleted");
        log.info("deleteOrderGuideItems()=> END.");

        return n;
    }

    public static int updateOrderGuideItems(Connection pCon, int pStoreOrderGuideId, List pCustOrderGuideIds, String pUser) throws SQLException {

        int n = 0;

        log.info("updateOrderGuideItems()=> BEGIN.");

        log.debug("updateOrderGuideItems()=> pStoreOrderGuideId: " + pStoreOrderGuideId + ", pCustOrderGuides: " + pCustOrderGuideIds);

        if (pStoreOrderGuideId > 0 && pCustOrderGuideIds != null && !pCustOrderGuideIds.isEmpty()) {

            String sql = "UPDATE CLW_ORDER_GUIDE_STRUCTURE T1 " +
                    "SET (T1.CUST_CATEGORY,T1.SORT_ORDER,T1.MOD_DATE,T1.MOD_BY)  = " +
                    "(SELECT CUST_CATEGORY, SORT_ORDER, ?, ? FROM CLW_ORDER_GUIDE_STRUCTURE T2 WHERE T2.ITEM_ID = T1.ITEM_ID AND T2.ORDER_GUIDE_ID = ?) " +
                    "WHERE T1.ITEM_ID IN (SELECT ITEM_ID FROM CLW_ORDER_GUIDE_STRUCTURE WHERE ORDER_GUIDE_ID = ? " +
                    "AND ITEM_ID = T1.ITEM_ID " +
                    "AND (CUST_CATEGORY != T1.CUST_CATEGORY OR SORT_ORDER != T1.SORT_ORDER)) " +
                    "AND T1.ORDER_GUIDE_ID IN (" + Utility.toCommaSting(pCustOrderGuideIds) + ")";

            PreparedStatement pstmt = null;
            try {

                log.info("updateOrderGuideItems()=> SQL: " + sql);

                pstmt = pCon.prepareStatement(sql);
                int i = 1;

                java.util.Date current = new java.util.Date(System.currentTimeMillis());

                pstmt.setTimestamp(i++, DBAccess.toSQLTimestamp(current));
                pstmt.setString(i++, pUser);
                pstmt.setInt(i++, pStoreOrderGuideId);
                pstmt.setInt(i, pStoreOrderGuideId);

                n = pstmt.executeUpdate();

            } finally {
                if (pstmt != null) {
                    pstmt.close();
                }
            }
        }

        log.info("updateOrderGuideItems()=> " + n + " row(s) updated");
        log.info("updateOrderGuideItems()=> END.");

        return n;
    }


    public static int createOrderGuideItems(Connection pCon, int pStoreOrderGuideId, List pCustOrderGuideIds, String pUser) throws SQLException {

        int n = 0;

        log.info("createOrderGuideItems()=> BEGIN");

        log.debug("createOrderGuideItems()=> pStoreOrderGuideId: " + pStoreOrderGuideId + ", pCustOrderGuides: " + pCustOrderGuideIds);

        if (pStoreOrderGuideId > 0 && pCustOrderGuideIds != null && !pCustOrderGuideIds.isEmpty()) {

            String sql = "INSERT INTO CLW_ORDER_GUIDE_STRUCTURE T1\n" +
                    " (ORDER_GUIDE_STRUCTURE_ID,\n" +
                    " ORDER_GUIDE_ID,\n" +
                    " ITEM_ID,\n" +
                    " CATEGORY_ITEM_ID,\n" +
                    " CUST_CATEGORY,\n" +
                    " QUANTITY,\n" +
                    " SORT_ORDER,\n" +
                    " ADD_DATE,\n" +
                    " ADD_BY,\n" +
                    " MOD_DATE,\n" +
                    " MOD_BY)\n" +
                    " SELECT CLW_ORDER_GUIDE_STRUCTURE_SEQ.NEXTVAL, STORE_OGI.CUST_OG_ID, STORE_OGI.ITEM_ID, 0, STORE_OGI.CUST_CATEGORY, 0, STORE_OGI.SORT_ORDER, ?, ?, ?, ?  \n" +
                    " FROM (SELECT I.ITEM_ID, I.ORDER_GUIDE_ID, OG_INF.CUST_OG_ID, I.CUST_CATEGORY, I.SORT_ORDER  FROM CLW_ORDER_GUIDE_STRUCTURE I," +
                    " (SELECT ? STORE_OG_ID, ORDER_GUIDE_ID CUST_OG_ID FROM CLW_ORDER_GUIDE OG" +
                    " WHERE OG.ORDER_GUIDE_ID IN (" + Utility.toCommaSting(pCustOrderGuideIds) + ")) OG_INF WHERE OG_INF.STORE_OG_ID = I.ORDER_GUIDE_ID) STORE_OGI" +
                    " LEFT JOIN  CLW_ORDER_GUIDE_STRUCTURE CUST_OGI ON CUST_OGI.ORDER_GUIDE_ID = STORE_OGI.CUST_OG_ID AND CUST_OGI.ITEM_ID = STORE_OGI.ITEM_ID" +
                    " WHERE CUST_OGI.ITEM_ID IS NULL";

            PreparedStatement pstmt = null;
            try {

                log.info("createOrderGuideItems()=> SQL: " + sql);

                pstmt = pCon.prepareStatement(sql);
                int i = 1;

                java.util.Date current = new java.util.Date(System.currentTimeMillis());

                pstmt.setTimestamp(i++, DBAccess.toSQLTimestamp(current));
                pstmt.setString(i++, pUser);
                pstmt.setTimestamp(i++, DBAccess.toSQLTimestamp(current));
                pstmt.setString(i++, pUser);

                pstmt.setInt(i, pStoreOrderGuideId);

                n = pstmt.executeUpdate();

            } finally {
                if (pstmt != null) {
                    pstmt.close();
                }
            }
        }

        log.info("createOrderGuideItems()=> " + n + " row(s) inserted");
        log.info("createOrderGuideItems()=> END.");

        return n;
    }


    public static Map<String, Set<Integer>> defineSiteId(Connection pCon, int pId, String pScope, Set<String> pSiteRefs) throws SQLException {

        log.info("defineSiteId()=> BEGIN, ID: " + pId + ", pScope: " + pScope);

        if (RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT.equalsIgnoreCase(pScope)) {
            return defineSiteId(pCon, pId, pSiteRefs);
        }


        Map<String/*SiteNumber*/, Set<Integer/*Id*/>> result = new HashMap<String/*SiteNumber*/, Set<Integer/*Id*/>>();

        DBCriteria dbc = new DBCriteria();
        DBCriteria joinDbc = new DBCriteria();


        dbc.addJoinTable(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + SPACE + ACCOUNT_OF_STORE);
        dbc.addJoinTable(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + SPACE + SITE_OF_ACCOUNT);

        dbc.addOneOfIsolated(PropertyDataAccess.CLW_PROPERTY,PropertyDataAccess.CLW_VALUE, new ArrayList<String>(pSiteRefs));
        dbc.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD, RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
        dbc.addEqualTo(PropertyDataAccess.SHORT_DESC, RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER);

        dbc.addJoinCondition(PropertyDataAccess.CLW_PROPERTY, PropertyDataAccess.BUS_ENTITY_ID, BusEntityDataAccess.CLW_BUS_ENTITY, BusEntityDataAccess.BUS_ENTITY_ID);
        dbc.addJoinTableEqualTo(BusEntityDataAccess.CLW_BUS_ENTITY, BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);

        joinDbc.addCondition(BusEntityDataAccess.CLW_BUS_ENTITY + "." + BusEntityDataAccess.BUS_ENTITY_ID + "=" + SITE_OF_ACCOUNT + "." + BusEntityAssocDataAccess.BUS_ENTITY1_ID);
        joinDbc.addCondition(SITE_OF_ACCOUNT + "." + BusEntityAssocDataAccess.BUS_ENTITY2_ID + "=" + ACCOUNT_OF_STORE + "." + BusEntityAssocDataAccess.BUS_ENTITY1_ID);
        joinDbc.addJoinTableEqualTo(SITE_OF_ACCOUNT, BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
        joinDbc.addJoinTableEqualTo(ACCOUNT_OF_STORE, BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
        joinDbc.addJoinTableEqualTo(ACCOUNT_OF_STORE, BusEntityAssocDataAccess.BUS_ENTITY2_ID, pId);

        dbc.addIsolatedCriterita(joinDbc);

        PropertyDataVector siteRefProps = PropertyDataAccess.select(pCon, dbc);

        for (Object oProp : siteRefProps) {

            PropertyData prop = (PropertyData) oProp;

            Set<Integer> siteIds = result.get(prop.getValue());
            if (siteIds == null) {
                siteIds = new HashSet<Integer>();
                result.put(prop.getValue(), siteIds);
            }

            siteIds.add(prop.getBusEntityId());

        }

        log.info("defineSiteId()=> END.");

        return result;
    }


}


