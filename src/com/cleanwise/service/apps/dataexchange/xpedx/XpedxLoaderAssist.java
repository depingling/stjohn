package com.cleanwise.service.apps.dataexchange.xpedx;

import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.apps.dataexchange.Translator;
import org.apache.log4j.Logger;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class XpedxLoaderAssist {

    protected static Logger log = Logger.getLogger( XpedxLoaderAssist.class);

    public static final String IMAGE_BASE_PATH = "/en/products/images/";
    private static final String X = "x";
    private static final String IMAGE = "Image";
    private static final String ITEM_IMAGE ="ItemImage";
    private static final String XSUITE_APP = "xsuite-app";
    private static final String YES = "YES";

    public static Map<AccountReference, Map<Integer/*Id*/, BusEntityData>> getAccountReferenceMap(Connection pCon,
                                                                                                  int pTradingPartnerId,
                                                                                                  Set<AccountReference> pAccountRefernces,
                                                                                                  List<String> pErrors) throws Exception {

        log.info("getAccountReferenceMap()=> BEGIN");
        Map<AccountReference/*AccountRefernces*/, Map<Integer/*Id*/, BusEntityData>> result = new HashMap<AccountReference/*AccountRefernces*/, Map<Integer/*Id*/, BusEntityData>>();

        List<String> accountRefernceNumbers = new ArrayList<String>();
        for (AccountReference accountRefernce : pAccountRefernces) {
            accountRefernceNumbers.add(accountRefernce.getAccountNamber());
        }

        DBCriteria tpAssocCriteria = new DBCriteria();

        tpAssocCriteria.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ID, pTradingPartnerId);
        tpAssocCriteria.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_CD, RefCodeNames.TRADING_PARTNER_ASSOC_CD.ACCOUNT);

        if (accountRefernceNumbers.size() > 0) {
            tpAssocCriteria.addOneOf(TradingPartnerAssocDataAccess.GROUP_SENDER_OVERRIDE, accountRefernceNumbers);
        }

        TradingPartnerAssocDataVector tpAssocList = TradingPartnerAssocDataAccess.select(pCon, tpAssocCriteria);

        Map<Integer, String> refMap = new HashMap<Integer, String>();
        for (Object oTpAssoc : tpAssocList) {
            TradingPartnerAssocData item = (TradingPartnerAssocData) oTpAssoc;
            if (refMap.containsKey(item.getBusEntityId())) {
                pErrors.add("Duplicated Account for reference number: " + item.getGroupSenderOverride());
            }
            refMap.put(item.getBusEntityId(), item.getGroupSenderOverride());
        }

        DBCriteria beCriteria = new DBCriteria();
        beCriteria.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, new ArrayList<Integer>(refMap.keySet()));
        beCriteria.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);

        BusEntityDataVector accounts = BusEntityDataAccess.select(pCon, beCriteria);

        for (Object oAccount : accounts) {
            BusEntityData account = (BusEntityData) oAccount;
            if (refMap.containsKey(account.getBusEntityId())) {
                String refNum = refMap.get(account.getBusEntityId());
                if (Utility.isSet(refNum)) {
                    Map<Integer, BusEntityData> accountsMap = result.get(new AccountReference(refNum));
                    if (accountsMap == null) {
                        accountsMap = new HashMap<Integer, BusEntityData>();
                        result.put(new AccountReference(refNum), accountsMap);
                    }
                    accountsMap.put(account.getBusEntityId(), account);
                }
            }
        }

        log.info("getAccountReferenceMap()=> END.");

        return result;
    }

    public static Map<DistributorReference, BusEntityData> getDitributorReferenceMap(Connection pCon,
                                                                                     int pTradingPartnerId,
                                                                                     Set<DistributorReference> pDistributorReferences,
                                                                                     List<String> pErrors) throws Exception {

        log.info("getDitributorReferenceMap()=> BEGIN");
        Map<DistributorReference, BusEntityData> result = new HashMap<DistributorReference, BusEntityData>();

        List<String> distrNames = new ArrayList<String>();
        for (DistributorReference distrRefernce : pDistributorReferences) {
            distrNames.add(distrRefernce.getDistributor());
        }

        BusEntityDataVector list = getTradingPartnerEntities(pCon,
                pTradingPartnerId,
                RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR,
                distrNames);

        for (Object o : list) {
            BusEntityData item = (BusEntityData) o;
            DistributorReference key = new DistributorReference(item.getShortDesc().trim());
            BusEntityData oldValue = result.get(key);
            if (oldValue != null) {
                pErrors.add("Was found duplicates for distributor name '" + key + "':" + oldValue.getBusEntityId() + " " + item.getBusEntityId());
            } else {
                result.put(key, item);
            }
        }
        log.info("getDitributorReferenceMap()=> END.");

        return result;
    }

    public static Map<ManufacturerReference, BusEntityData> getManufacturerReferenceMap(Connection pCon,
                                                                                                   Integer pStoreId,
                                                                                                   List<String> pErrors) throws Exception {

        log.info("getManufacturerReferenceMap()=> BEGIN");
        Map<ManufacturerReference, BusEntityData> result = new HashMap<ManufacturerReference, BusEntityData>();

        BusEntitySearchCriteria beSearchCriteria = new BusEntitySearchCriteria();
        beSearchCriteria.setStoreBusEntityIds(Utility.toIdVector(pStoreId));
        BusEntityDataVector list = BusEntityDAO.getBusEntityByCriteria(pCon, beSearchCriteria, RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);

        for (Object o : list) {
            BusEntityData item = (BusEntityData) o;
            ManufacturerReference key = new ManufacturerReference(item.getShortDesc().trim());
            BusEntityData oldValue = result.get(key);
            if (oldValue != null) {
                pErrors.add("Was found duplicates for manufacturer name '" + key + "':" + oldValue.getBusEntityId() + " " + item.getBusEntityId());
            } else {
                result.put(key, item);
            }
        }
        log.info("getManufacturerReferenceMap()=> END.");

        return result;
    }

    public static BusEntityDataVector getTradingPartnerEntities(Connection pCon,
                                                                int pTradingPartnerId,
                                                                String pBusEntityTypeCd,
                                                                List<String> pBusEntityNames) throws Exception {
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

        if (pBusEntityNames != null && !pBusEntityNames.isEmpty()) {
            dbCriteria.addOneOf(BusEntityDataAccess.SHORT_DESC, pBusEntityNames);
        }

        return BusEntityDataAccess.select(pCon, dbCriteria);

    }


    public static Map<CatalogReference, CatalogData> getCatalogReferenceMap(Connection pCon,
                                                                                       Integer pTradingPartnerId,
                                                                                       AccountReference pAccountReference,
                                                                                       Set<CatalogReference> pCatalogReferences,
                                                                                       List<String> pErrors) throws Exception {

        Map<CatalogReference, CatalogData> result = new HashMap<CatalogReference, CatalogData>();

        if (pCatalogReferences == null || pCatalogReferences.isEmpty()) {
            return result;
        }

        if (!Utility.isSet(pAccountReference.getAccountNamber())) {
            return result;
        }

        List<String> catalogLoaderFields = new ArrayList<String>();
        for (CatalogReference catref : pCatalogReferences) {
            catalogLoaderFields.add(catref.getCatalogNumber());
        }


        StringBuilder sql = new StringBuilder();
        DBCriteria inAccounts = new DBCriteria();
        DBCriteria inCatalogs = new DBCriteria();

        sql.append("SELECT DISTINCT t1.catalog_id AS catalog_id\n");
        sql.append("FROM clw_catalog t1\n");
        sql.append("INNER JOIN clw_catalog_assoc t2 ON t1.catalog_id  = t2.catalog_id\n");
        sql.append("    AND t2.catalog_assoc_cd = '" + RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT + "'\n");
        sql.append("INNER JOIN clw_trading_partner_assoc t3 " + " ON t2.bus_entity_id = t3.bus_entity_id\n");
        sql.append("    AND t3.trading_partner_id = ").append(pTradingPartnerId).append("\n");
        sql.append("    AND t3.trading_partner_assoc_cd = '" + RefCodeNames.TRADING_PARTNER_ASSOC_CD.ACCOUNT + "'\n");
        inAccounts.addEqualTo("trim(t3.group_sender_override)", pAccountReference.getAccountNamber());
        sql.append("    AND ").append(inAccounts.getWhereClause()).append("\n");
        inCatalogs.addOneOf("trim(t1.loader_field)", new ArrayList<String>(catalogLoaderFields));
        sql.append("WHERE ").append(inCatalogs.getWhereClause()).append("\n");

        log.info("getCatalogIdsByAccountCatalogRef SQL:\n" + sql);

        List<Integer> catalogIds = new ArrayList<Integer>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = pCon.createStatement();
            resultSet = statement.executeQuery(sql.toString());
            while (resultSet.next()) {
                int catalogId = resultSet.getInt("catalog_id");
                catalogIds.add(catalogId);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }

        DBCriteria dbCriteria = new DBCriteria();
        dbCriteria.addOneOf(CatalogDataAccess.CATALOG_ID, catalogIds);
        dbCriteria.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.SHOPPING);

        CatalogDataVector catalogs = CatalogDataAccess.select(pCon, dbCriteria);
        for (Object oCatalog : catalogs) {
            CatalogData catalog = (CatalogData) oCatalog;
            CatalogReference catref = new CatalogReference(pAccountReference.getAccountNamber(), catalog.getLoaderField());
            if (!result.containsKey(catref)) {
                result.put(catref, catalog);
            } else {
                pErrors.add("Was found duplicated catalogs for CatalogID '" + catalog.getLoaderField());
            }
        }

        return result;

    }

    public static  String getItemKey(String pDistributor, String pDistSKU) {
        return pDistributor + "_" + pDistSKU;
    }

   /* public static HashMap<String, InboundXpedxCatalogItemLoaderV2.LoaderProductData> getItemReferenceMap(Connection pCon,
                                                                                                         Set<String> pDistrReferenceNums,
                                                                                                         Map<String, BusEntityData> pDistributorReferenceMap,
                                                                                                         List<String> pErrors) throws Exception {


        HashMap<String, InboundXpedxCatalogItemLoaderV2.LoaderProductData> result = new HashMap<String, InboundXpedxCatalogItemLoaderV2.LoaderProductData>();

        HashSet<String> itemDistrNames = new HashSet<String>();
        HashSet<String> itemDistrSkus = new HashSet<String>();
        for (String itemReferenceNum : pDistrReferenceNums) {
            StringTokenizer st = new StringTokenizer(itemReferenceNum,"_");
            itemDistrNames.add((String) st.nextElement());
            itemDistrSkus.add((String) st.nextElement());
        }

        Map<Integer, String> distrMap = new HashMap<Integer, String>();
        for (Map.Entry<String, BusEntityData> entry : pDistributorReferenceMap.entrySet()) {
            if(itemDistrNames.contains(entry.getKey())){
                distrMap.put(entry.getValue().getBusEntityId(), entry.getKey());
            }
        }


        DBCriteria cr = new DBCriteria();
        cr.addOneOf(ItemMappingDataAccess.BUS_ENTITY_ID, new ArrayList<Integer>(distrMap.keySet()));
        cr.addOneOf(ItemMappingDataAccess.ITEM_NUM, new ArrayList<String>(itemDistrSkus));
        cr.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);

        ItemMappingDataVector list = ItemMappingDataAccess.select(pCon, cr);


        HashMap<Integer, String> itemMappingMap = new HashMap<Integer, String>();

        for (int i = 0; list != null && i < list.size(); i++) {

            ItemMappingData item = (ItemMappingData) list.get(i);

            int distId = item.getBusEntityId();

            String distName = distrMap.get(distId);
            if (distName == null) {
                pErrors.add("Not found distributor name by ID:" + item.getBusEntityId());
                continue;
            }

            String key = getItemKey(distName, item.getItemNum());
            String oldValue = itemMappingMap.put(item.getItemId(), key);
            if (oldValue != null) {
                pErrors.add("Was found duplicate for '" + distName + "':" + item.getItemNum());
            }
        }

        cr = new DBCriteria();
        cr.addOneOf(ItemDataAccess.ITEM_ID, new ArrayList<Integer>(itemMappingMap.keySet()));
        cr.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.PRODUCT);

        ItemDataVector items = ItemDataAccess.select(pCon, cr);
        for (Object oItem : items) {

            ItemData item = (ItemData) oItem;

            InboundXpedxCatalogItemLoaderV2.LoaderProductData loaderProductData = createLoaderProductData(pCon , item);
            String itemReferenceNum = itemMappingMap.get(item.getItemId());
            if (Utility.isSet(itemReferenceNum)) {
                result.put(itemReferenceNum, loaderProductData);
            }
        }

        return result;

    }

    private static InboundXpedxCatalogItemLoaderV2.LoaderProductData createLoaderProductData(Connection pCon, ItemData pItem) throws Exception {

        InboundXpedxCatalogItemLoaderV2.LoaderProductData lpd = new InboundXpedxCatalogItemLoaderV2.LoaderProductData();

        DBCriteria dbCriteria;

        dbCriteria = new DBCriteria();
        dbCriteria.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pItem);
        CatalogStructureDataVector catalogStructures = CatalogStructureDataAccess.select(pCon, dbCriteria);

        dbCriteria = new DBCriteria();
        dbCriteria.addEqualTo(ItemDataAccess.ITEM_ID, pItem.getItemId());
        ItemAssocDataVector itemAssocs = ItemAssocDataAccess.select(pCon, dbCriteria);

        dbCriteria = new DBCriteria();
        dbCriteria.addEqualTo(ItemMetaDataAccess.ITEM_ID, pItem.getItemId());
        ItemMetaDataVector itemMetas = ItemMetaDataAccess.select(pCon, dbCriteria);

        dbCriteria = new DBCriteria();
        dbCriteria.addEqualTo(ItemMappingDataAccess.ITEM_ID, pItem.getItemId());
        ItemMappingDataVector itemMappings = ItemMappingDataAccess.select(pCon, dbCriteria);

        dbCriteria = new DBCriteria();
        dbCriteria.addEqualTo(ContractItemDataAccess.ITEM_ID, pItem.getItemId());
        ContractItemDataVector contractItems = ContractItemDataAccess.select(pCon, dbCriteria);

        dbCriteria = new DBCriteria();
        dbCriteria.addEqualTo(InventoryItemsDataAccess.ITEM_ID, pItem.getItemId());
        InventoryItemsDataVector inventoryItems = InventoryItemsDataAccess.select(pCon, dbCriteria);

        dbCriteria = new DBCriteria();
        dbCriteria.addEqualTo(ShoppingControlDataAccess.ITEM_ID, pItem.getItemId());
        ShoppingControlDataVector shoppingControlItems = ShoppingControlDataAccess.select(pCon, dbCriteria);

        lpd.setItem(pItem);
        lpd.setCatalogStructures(catalogStructures);
        lpd.setContractItems(contractItems);
        lpd.setInventoryItems(inventoryItems);
        lpd.setItemAssocDataVector(itemAssocs);
        lpd.setItemMappingDataVector(itemMappings);
        lpd.setShoppingControls(shoppingControlItems);
        lpd.setItemMetaDataVector(itemMetas);

        return lpd;

    }*/



    public static HashMap<Integer, CatalogData> getStoreCatalogReferenceMap(Connection pCon, int pStoreId, List<String> pErrors) throws SQLException {

        HashMap<Integer, CatalogData> result = new HashMap<Integer, CatalogData>();

        DBCriteria cr = new DBCriteria();

        cr.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.STORE);
        cr.addJoinCondition(CatalogDataAccess.CATALOG_ID, CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ID);
        cr.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
        cr.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.BUS_ENTITY_ID, pStoreId);

        CatalogDataVector list = CatalogDataAccess.select(pCon, cr);

        if (list != null && list.size() == 1) {
            CatalogData catalog = (CatalogData) list.get(0);
            result.put(pStoreId, catalog);
            return result;
        } else if (list != null && list.size() > 1) {
            pErrors.add("Multiple store catalog found.StoreID: " + pStoreId);
        }

        return result;
    }

    public static HashMap<AccountReference, CatalogData> getAccountCatalogReferenceMap(Connection pCon,
                                                                                       Map<AccountReference, Map<Integer, BusEntityData>> pAccountReferenceMap,
                                                                                       List<String> pErrors) throws Exception {

        log.info("getAccountCatalogReferenceMap()=> BEGIN");
        HashMap<AccountReference, CatalogData> result = new HashMap<AccountReference, CatalogData>();

        Map<Integer, String> accountMap = new HashMap<Integer, String>();
        for (Map.Entry<AccountReference, Map<Integer, BusEntityData>> entry : pAccountReferenceMap.entrySet()) {
            for (Integer accountId : entry.getValue().keySet()) {
                accountMap.put(accountId, entry.getKey().getAccountNamber());
            }
        }

        DBCriteria criteria = new DBCriteria();
        criteria.addJoinTableEqualTo(CatalogDataAccess.CLW_CATALOG, CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
        criteria.addJoinCondition(CatalogAssocDataAccess.CATALOG_ID, CatalogDataAccess.CLW_CATALOG, CatalogDataAccess.CATALOG_ID);
        criteria.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
        criteria.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, new ArrayList<Integer>(accountMap.keySet()));

        CatalogAssocDataVector catalogAssocs = CatalogAssocDataAccess.select(pCon, criteria);

        HashMap<Integer, Set<Integer>> catalogAssocMap = new HashMap<Integer, Set<Integer>>();
        for (Object oCatalogAssoc : catalogAssocs) {
            CatalogAssocData catalogAssoc = (CatalogAssocData) oCatalogAssoc;
            if (catalogAssocMap.containsKey(catalogAssoc.getCatalogId())) {
                Set<Integer> accountIds = catalogAssocMap.get(catalogAssoc.getCatalogId());
                accountIds.add(catalogAssoc.getBusEntityId());
            } else {
                HashSet<Integer> accountIds = new HashSet<Integer>();
                accountIds.add(catalogAssoc.getBusEntityId());
                catalogAssocMap.put(catalogAssoc.getCatalogId(), accountIds);
            }
        }

        criteria = new DBCriteria();
        criteria.addOneOf(CatalogDataAccess.CATALOG_ID, new ArrayList<Integer>(catalogAssocMap.keySet()));
        criteria.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);

        CatalogDataVector catalogs = CatalogDataAccess.select(pCon, criteria);
        for (Object oCatalog : catalogs) {
            CatalogData catalog = (CatalogData) oCatalog;
            Set<Integer> accountIds = catalogAssocMap.get(catalog.getCatalogId());
            for (Integer accountId : accountIds) {
                AccountReference accountReferenceNum = new AccountReference(accountMap.get(accountId));
                CatalogData prevValue = result.put(accountReferenceNum, catalog);
                if (prevValue != null && prevValue.getCatalogId() != catalog.getCatalogId()) {
                    pErrors.add("Multiple account catalog found. AccountRefernceNumber: " + accountReferenceNum);
                }
            }
        }

        log.info("getAccountCatalogReferenceMap()=> END.");

        return result;
    }


    public static Map<AccountReference, Map<Integer/*AccountId*/, CatalogData>> getAccountCatalogsReferenceMap(Connection pCon,
                                                                                                              Map<AccountReference, Map<Integer, BusEntityData>> pAccountReferenceMap,
                                                                                                              List<String> pErrors) throws Exception {

        log.info("getAccountCatalogReferenceMap()=> BEGIN");
        Map<AccountReference, Map<Integer/*AccountId*/, CatalogData>> result = new HashMap<AccountReference, Map<Integer/*AccountId*/, CatalogData>>();

        Map<Integer, String> accountMap = new HashMap<Integer, String>();
        for (Map.Entry<AccountReference, Map<Integer, BusEntityData>> entry : pAccountReferenceMap.entrySet()) {
            for (Integer accountId : entry.getValue().keySet()) {
                accountMap.put(accountId, entry.getKey().getAccountNamber());
            }
        }

        DBCriteria criteria = new DBCriteria();
        criteria.addJoinTableEqualTo(CatalogDataAccess.CLW_CATALOG, CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
        criteria.addJoinCondition(CatalogDataAccess.CATALOG_ID, CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ID);
        criteria.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
        criteria.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, new ArrayList<Integer>(accountMap.keySet()));

        CatalogAssocDataVector catalogAssocs = CatalogAssocDataAccess.select(pCon, criteria);

        HashMap<Integer, Set<Integer>> catalogAssocMap = new HashMap<Integer, Set<Integer>>();
        for (Object oCatalogAssoc : catalogAssocs) {
            CatalogAssocData catalogAssoc = (CatalogAssocData) oCatalogAssoc;
            if (catalogAssocMap.containsKey(catalogAssoc.getCatalogId())) {
                Set<Integer> accountIds = catalogAssocMap.get(catalogAssoc.getCatalogId());
                accountIds.add(catalogAssoc.getBusEntityId());
            } else {
                HashSet<Integer> accountIds = new HashSet<Integer>();
                accountIds.add(catalogAssoc.getBusEntityId());
                catalogAssocMap.put(catalogAssoc.getCatalogId(), accountIds);
            }
        }

        criteria = new DBCriteria();
        criteria.addOneOf(CatalogDataAccess.CATALOG_ID, new ArrayList<Integer>(catalogAssocMap.keySet()));
        criteria.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);

        CatalogDataVector catalogs = CatalogDataAccess.select(pCon, criteria);
        for (Object oCatalog : catalogs) {
            CatalogData catalog = (CatalogData) oCatalog;
            Set<Integer> accountIds = catalogAssocMap.get(catalog.getCatalogId());
            for (Integer accountId : accountIds) {
                AccountReference accountReferenceNum = new AccountReference(accountMap.get(accountId));
                Map<Integer, CatalogData> accountCatalogMap = result.get(accountReferenceNum);
                if (accountCatalogMap == null) {
                    accountCatalogMap = new HashMap<Integer, CatalogData>();
                    result.put(accountReferenceNum, accountCatalogMap);
                }
                CatalogData prevValue = accountCatalogMap.put(accountId, catalog);
                if (prevValue != null && prevValue.getCatalogId() != catalog.getCatalogId()) {
                    pErrors.add("Multiple account catalog found. " +
                            "AccountRefernceNumber: " + accountReferenceNum + ", " +
                            "catalog:  " + catalog + ", prevValue:" + prevValue);
                }
            }
        }

        log.info("getAccountCatalogReferenceMap()=> END.");

        return result;
    }


    public static CatalogData createCatalogData(String pName, String pType, String pForUser) {

        CatalogData catalog = CatalogData.createValue();

        catalog.setAddBy(pForUser);
        catalog.setModBy(pForUser);
        catalog.setCatalogStatusCd(RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
        catalog.setCatalogTypeCd(pType);
        catalog.setShortDesc(pName);

        return catalog;
    }

    public static CatalogAssocData createCatalogAssocData(int pCatalogId,
                                                          Integer pBusEntityId,
                                                          String pCatalogAssocCd,
                                                          String pForUser) {
        CatalogAssocData catalogAssoc = CatalogAssocData.createValue();
        catalogAssoc.setCatalogId(pCatalogId);
        catalogAssoc.setBusEntityId(pBusEntityId);
        catalogAssoc.setCatalogAssocCd(pCatalogAssocCd);
        catalogAssoc.setAddBy(pForUser);
        catalogAssoc.setModBy(pForUser);

        return catalogAssoc;
    }

    public static CatalogAssocDataVector getCatalogAssoc(Connection pCon, int pCatalogId) throws Exception {
        DBCriteria dbCrit = new DBCriteria();
        dbCrit.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);
        return CatalogAssocDataAccess.select(pCon, dbCrit);
    }

    public static CatalogStructureDataVector getCatalogStructures(Connection pCon, int pCatalogId) throws Exception {
        DBCriteria dbCrit = new DBCriteria();
        dbCrit.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
        return CatalogStructureDataAccess.select(pCon, dbCrit);
    }

    public static ContractData createContractData(int pCatalogId,
                                                  String pShortDesc,
                                                  String pLocaleCd,
                                                  int pFreightTableId,
                                                  String pForUser) {

        ContractData contractData = ContractData.createValue();

        contractData.setEffDate(new Date());
        contractData.setCatalogId(pCatalogId);
        contractData.setLocaleCd(pLocaleCd);
        contractData.setFreightTableId(pFreightTableId);
        contractData.setAddBy(pForUser);
        contractData.setModBy(pForUser);
        contractData.setRefContractNum("0");
        contractData.setShortDesc(pShortDesc);
        contractData.setContractStatusCd(RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
        contractData.setContractTypeCd("UNKNOWN");

        return contractData;
    }

    public static int getStoreId(Connection pCon, Translator pTranslator) throws Exception {

        int tradingPartnerId = pTranslator.getPartner().getTradingPartnerId();
        int[] ids = pTranslator.getTradingPartnerBusEntityIds(tradingPartnerId, RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);

        List<Integer> distIds = new ArrayList<Integer>();
        for (int i = 0; ids != null && i < ids.length; i++) {
            distIds.add(ids[i]);
        }

        DBCriteria cr = new DBCriteria();
        cr.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.DISTRIBUTOR_STORE);
        cr.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, distIds);

        BusEntityAssocDataVector list = BusEntityAssocDataAccess.select(pCon, cr);
        Set<Integer> storeIds = new TreeSet<Integer>();
        for (int i = 0; list != null && i < list.size(); i++) {
            BusEntityAssocData item = (BusEntityAssocData) list.get(i);
            storeIds.add(item.getBusEntity2Id());
        }

        if (storeIds.size() == 0) {
            throw new Exception("Not found store for distributor(s):" + distIds);
        } else if (storeIds.size() > 1) {
            throw new Exception("Were found more than 1 store for distributor(s):" + distIds);
        } else {
            return storeIds.iterator().next();
        }
    }

    public static int getStoreCatalogId(Connection pCon, int pStoreId) throws Exception {

        DBCriteria cr = new DBCriteria();

        cr.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.STORE);
        cr.addJoinCondition(CatalogDataAccess.CATALOG_ID, CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ID);
        cr.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
        cr.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.BUS_ENTITY_ID, pStoreId);

        CatalogDataVector list = CatalogDataAccess.select(pCon, cr);

        if (list != null && list.size() > 0) {
            CatalogData item = (CatalogData) list.get(0);
            return item.getCatalogId();
        }
        return -1;
    }

    public static Integer toInteger(String pVal) {
        if (Utility.isSet(pVal)) {
            return new Integer(pVal);
        } else {
            return null;
        }
    }

    public static BigDecimal toBigDecimal(String pVal) {
        if (Utility.isSet(pVal)) {
            return new BigDecimal(pVal);
        } else {
            return null;
        }
    }

    public static Boolean toBoolean(String pVal) {
        if (Utility.isSet(pVal)) {
            return Boolean.valueOf(pVal);
        } else {
            return null;
        }
    }


    public static HashMap<String/*short desc*/, List<CostCenterData>> getAccountCostCentersMap(Connection pCon, int pAccCatalogId) throws SQLException {

        log.info("getAccountCostCentersMap()=> BEGIN");
        DBCriteria crit;

        crit = new DBCriteria();
        crit.addJoinTableEqualTo(CostCenterAssocDataAccess.CLW_COST_CENTER_ASSOC, CostCenterAssocDataAccess.CATALOG_ID, pAccCatalogId);
        crit.addJoinTableEqualTo(CostCenterAssocDataAccess.CLW_COST_CENTER_ASSOC, CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD, RefCodeNames.COST_CENTER_ASSOC_CD.COST_CENTER_ACCOUNT_CATALOG);
        crit.addJoinCondition(CostCenterDataAccess.COST_CENTER_ID, CostCenterAssocDataAccess.CLW_COST_CENTER_ASSOC, CostCenterAssocDataAccess.COST_CENTER_ID);

        HashMap<String, List<CostCenterData>> acctCostCentersMap = new HashMap<String, List<CostCenterData>>();
        CostCenterDataVector accCatalogCostCenters = CostCenterDataAccess.select(pCon, crit);
        for (Object oAcctCostCenter : accCatalogCostCenters) {
            CostCenterData acctCostCenter = ((CostCenterData) oAcctCostCenter);
            List<CostCenterData> costCenters = acctCostCentersMap.get(acctCostCenter.getShortDesc());
            if (costCenters == null) {
                costCenters = new ArrayList<CostCenterData>();
                acctCostCentersMap.put(acctCostCenter.getShortDesc(), costCenters);
            }
            costCenters.add(acctCostCenter);
        }
        log.info("getAccountCostCentersMap()=> END.");

        return acctCostCentersMap;
    }

    public static HashMap<Integer, Integer> getMultiPeoductsMap(Connection pCon, int pStoreCatalogId) throws SQLException {

        log.info("getMultiPeoductsMap()=> BEGIN");
        HashMap<Integer, Integer> multiProdsMap = new HashMap<Integer, Integer>();

        DBCriteria crit = new DBCriteria();

        crit.addJoinTableEqualTo(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.CATALOG_ID, pStoreCatalogId);
        crit.addJoinTableEqualTo(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_MULTI_PRODUCT);
        crit.addJoinCondition(ItemDataAccess.ITEM_ID, CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.ITEM_ID);
        crit.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.ITEM_GROUP);

        ItemDataVector multiProducts = ItemDataAccess.select(pCon, crit);

        if (!multiProducts.isEmpty()) {

            for (Object oMultiProduct : multiProducts) {
                ItemData item = (ItemData) oMultiProduct;
                multiProdsMap.put(item.getItemId(), item.getItemId());
            }

        }
        log.info("getMultiPeoductsMap()=> END.");

        return multiProdsMap;

    }


    public static Integer getMainDistributorId(Connection pCon, int pAccountCatalogId) throws SQLException {
        log.info("getMainDistributorId()=> BEGIN");

        DBCriteria crit = new DBCriteria();

        crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR);
        crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pAccountCatalogId);

        CatalogAssocDataVector caDV = CatalogAssocDataAccess.select(pCon, crit);

        if (caDV != null && caDV.size() > 0) {
            CatalogAssocData caD = (CatalogAssocData) caDV.get(0);
            return caD.getBusEntityId();
        }
        log.info("getMainDistributorId()=> END.");

        return null;
    }

    public static Map<String, Integer> getExistCategories(Connection pCon, int pCatalogId, Set<String> pCategoryNames) throws Exception {

        log.info("getExistCategories()=> BEGIN");
        Map<String, Integer> result = new TreeMap<String, Integer>();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT trim(t1.short_desc) AS category_name,t1.item_id AS category_id ");
        sql.append("FROM clw_item t1 INNER JOIN clw_catalog_structure t2 ON t1.item_id = t2.item_id AND t1.item_type_cd = '" + RefCodeNames.ITEM_TYPE_CD.CATEGORY + "'\n");
        sql.append("    AND t2.catalog_id = ");
        sql.append(pCatalogId);
        sql.append("\n");

        if (pCategoryNames != null && !pCategoryNames.isEmpty()) {
            DBCriteria inCategories = new DBCriteria();
            inCategories.addOneOf("trim(t1.short_desc)", new ArrayList<String>(pCategoryNames));
            sql.append("    AND ");
            sql.append(inCategories.getWhereClause());
            sql.append("\n");
        }

        Statement statement = null;
        ResultSet resultSet = null;

        log.debug("getExistCategories()=> SQL:\n" + sql);

        try {
            statement = pCon.createStatement();
            resultSet = statement.executeQuery(sql.toString());
            while (resultSet.next()) {
                String categoryName = resultSet.getString("category_name").trim();
                int categoryId = resultSet.getInt("category_id");
                result.put(categoryName, categoryId);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        log.info("getExistCategories()=> END.");
        return result;
    }

    public static Map<Integer, Map<CategoryReference, ItemData>> getCategoryMap(Connection pCon,  IdVector pCatalogIds) throws Exception {

        log.info("getCategoryMap()=> BEGIN");
        Map<Integer, Map<CategoryReference, ItemData>> result = new TreeMap<Integer, Map<CategoryReference, ItemData>>();

        DBCriteria cr = new DBCriteria();

        cr.addOneOf(CatalogStructureDataAccess.CATALOG_ID, pCatalogIds);
        cr.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);


        DBCriteria itemCriteria = new DBCriteria();

        itemCriteria.addOneOf(ItemDataAccess.ITEM_ID, CatalogStructureDataAccess.getSqlSelectIdOnly(CatalogStructureDataAccess.ITEM_ID, cr));
        itemCriteria.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.CATEGORY);

        log.info("getCategoryMap()=> " + cr.getWhereClause());

        Map<Integer, Set<Integer>> categorySet = new HashMap<Integer, Set<Integer>>();

        CatalogStructureDataVector list = CatalogStructureDataAccess.select(pCon, cr);
        for (int i = 0; list != null && i < list.size(); i++) {
            CatalogStructureData item = (CatalogStructureData) list.get(i);
            Set<Integer> val = categorySet.get(item.getCatalogId());
            if (val == null) {
                val = new HashSet<Integer>();
                categorySet.put(item.getCatalogId(), val);
            }
            val.add(item.getItemId());
        }

        ItemDataVector items = ItemDataAccess.select(pCon, itemCriteria);
        HashMap itemMap = Utility.toMap(items);

        for (Map.Entry<Integer, Set<Integer>> e : categorySet.entrySet()) {

            for (Integer categoryId : e.getValue()) {

                Map<CategoryReference, ItemData> categoryRefMap = result.get(e.getKey());
                if (categoryRefMap == null) {
                    categoryRefMap = new HashMap<CategoryReference, ItemData>();
                    result.put(e.getKey(), categoryRefMap);
                }

                ItemData item = (ItemData) itemMap.get(categoryId);

                CategoryReference categRef = new CategoryReference(
                        item.getShortDesc(),
                        item.getLongDesc());

                categoryRefMap.put(categRef, item);

            }
        }

        log.info("getCategoryMap()=> END.");

        return result;
    }


    public static Map<Integer, Map<Integer, Map<String, CatalogAssocData>>> getBusEntsCatalogAssocByCatalogId(Connection pCon,
                                                                                                 List pCatalogIds,
                                                                                                 List<String> pErrors) throws Exception {
        log.info("getBusEntsCatalogAssocByCatalogId()=> BEGIN");
        Map<Integer, Map<Integer, Map<String, CatalogAssocData>>> result = new HashMap<Integer, Map<Integer, Map<String, CatalogAssocData>>>();

        if (pCatalogIds != null && !pCatalogIds.isEmpty()) {

            DBCriteria cr = new DBCriteria();
            cr.addOneOf(CatalogAssocDataAccess.CATALOG_ID, pCatalogIds);

            CatalogAssocDataVector list = CatalogAssocDataAccess.select(pCon, cr);
            for (Object oList : list) {
                CatalogAssocData item = (CatalogAssocData) oList;
                int catalogId = item.getCatalogId();
                int busEntityId = item.getBusEntityId();
                String catalogAssocCd = item.getCatalogAssocCd();
                Map<Integer, Map<String, CatalogAssocData>> valMap = result.get(catalogId);
                if (valMap == null) {
                  valMap = new HashMap<Integer, Map<String, CatalogAssocData>>();
                  result.put(catalogId, valMap);
                }
                Map<String, CatalogAssocData> val = valMap.get(busEntityId);
                if (val == null) {
                  val = new HashMap<String, CatalogAssocData> ();
                  valMap.put(busEntityId, val);
                }

                if (val.containsKey(catalogAssocCd)) {
                  pErrors.add("Catalog: " + catalogId +
                              " already has assigned bus entity: " + busEntityId +
                              " with association code: " + catalogAssocCd);
                } else {
                  val.put(catalogAssocCd, item);
                }


            }
        }
        log.info("getBusEntsCatalogAssocByCatalogId()=> END.");
        return result;
    }


    public static Map<Integer, ContractData> getContracts(Connection pCon, List pCatalogIds) throws Exception {

        Map<Integer, ContractData> result = new TreeMap<Integer, ContractData>();

        DBCriteria cr = new DBCriteria();
        cr.addOneOf(ContractDataAccess.CATALOG_ID, pCatalogIds);

        ContractDataVector cdv = ContractDataAccess.select(pCon, cr);
        for (int i = 0; cdv != null && i < cdv.size(); i++) {
            ContractData contractData = (ContractData) cdv.get(i);
            result.put(contractData.getCatalogId(), contractData);
        }

        return result;
    }

    public static Map<Integer, Map<Integer, CatalogStructureData>> getCatalogStructuresByCatalog(Connection pCon,
                                                                                                 List pCatalogIds,
                                                                                                 List<String> pStructureCds,
                                                                                                 List<String> pErrors) throws Exception {

        log.info("getCatalogStructuresByCatalog()=> BEGIN");

        Map<Integer, Map<Integer, CatalogStructureData>> result = new TreeMap<Integer, Map<Integer, CatalogStructureData>>();

        DBCriteria cr = new DBCriteria();
        cr.addOneOf(CatalogStructureDataAccess.CATALOG_ID, pCatalogIds);
        cr.addOneOf(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, new ArrayList<String>(pStructureCds));

        log.info("getCatalogStructuresByCatalog()=> pCatalogIds: "+pCatalogIds);
        log.info("getCatalogStructuresByCatalog()=> pStructureCds: "+pStructureCds);

        CatalogStructureDataVector list = CatalogStructureDataAccess.select(pCon, cr);

        for (int i = 0; list != null && i < list.size(); i++) {
            CatalogStructureData item = (CatalogStructureData) list.get(i);

            int key = item.getCatalogId();
            Map<Integer, CatalogStructureData> val = result.get(key);
            if (val == null) {
                val = new TreeMap<Integer, CatalogStructureData>();
                result.put(key, val);
            }

            CatalogStructureData oldItem = val.put(item.getItemId(), item);
            if (oldItem != null) {
                pErrors.add("Was found duplicate catalog structure for catalog:"
                        + item.getCatalogId()
                        + " items:"
                        + oldItem.getItemId() + "," + item.getItemId());
            }
        }


        log.info("getCatalogStructuresByCatalog()=> END.");

        return result;

    }


    public static Map<Integer, Integer> getCategoryParentIdByChildId(Connection pCon, int pStoreCatalogId) throws Exception {

        log.info("getCategoryParentIdByChildId()=> BEGIN");
        Map<Integer, Integer> result = new TreeMap<Integer, Integer>();
        DBCriteria cr = new DBCriteria();
        cr.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pStoreCatalogId);
        cr.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);

        ItemAssocDataVector list = ItemAssocDataAccess.select(pCon, cr);
        for (int i = 0; list != null && i < list.size(); i++) {
            ItemAssocData item = (ItemAssocData) list.get(i);
            result.put(item.getItem1Id(), item.getItem2Id());
        }

        log.info("getCategoryParentIdByChildId()=> END.");
        return result;

    }


    public static Map<ItemReference, ItemData> getItemDataByDistItemRef(Connection pCon, Map<ItemReference, ItemMappingData> pItemDistMap) throws SQLException {

        log.info("getItemDataByDistItemRef()=> BEGIN");
        HashSet<Integer> itemIdSet = new HashSet<Integer>();
        for (ItemMappingData itemMapping : pItemDistMap.values()) {
            itemIdSet.add(itemMapping.getItemId());
        }

        Map<ItemReference, ItemData> result = new HashMap<ItemReference, ItemData>();

        if (!itemIdSet.isEmpty()) {

            DBCriteria crit = new DBCriteria();
            crit.addOneOf(ItemDataAccess.ITEM_ID, new ArrayList<Integer>(itemIdSet));

            ItemDataVector items = ItemDataAccess.select(pCon, crit);

            Map<Integer, ItemData> itemMap = new HashMap<Integer, ItemData>();
            for (int i = 0; items != null && i < items.size(); i++) {
                ItemData item = (ItemData) items.get(i);
                itemMap.put(item.getItemId(), item);
            }

            for (Map.Entry<ItemReference,ItemMappingData> entry : pItemDistMap.entrySet()) {
                result.put(entry.getKey(), itemMap.get(entry.getValue().getItemId()));
            }
        }
        log.info("getItemDataByDistItemRef()=> END.");

        return result;
    }


    public static Map<ItemReference, ItemMappingData> getItemDistributorMap(Connection pCon, Set<ItemReference> pDistItemRefs, Map<DistributorReference, BusEntityData> pDistRefernceByDistr, List<String> pErrors) throws SQLException {

        log.info("getItemDistributorMap()=> BEGIN");
        Map<ItemReference, ItemMappingData> result = new HashMap<ItemReference, ItemMappingData>();

        Set<Integer> distIds = new HashSet<Integer>();
        Set<String> itemNums = new HashSet<String>();
        for (ItemReference itemRef : pDistItemRefs) {
            DistributorReference distrRef = new DistributorReference(itemRef.getDistributor());
            if (pDistRefernceByDistr.get(distrRef) != null) {
                distIds.add(pDistRefernceByDistr.get(distrRef).getBusEntityId());
            }
            itemNums.add(itemRef.getDistSKU());
        }

        Map<Integer, DistributorReference> distrIdByDistRefernce = new HashMap<Integer, DistributorReference>();
        for (Map.Entry<DistributorReference, BusEntityData> eDistRefernce : pDistRefernceByDistr.entrySet()) {
            distrIdByDistRefernce.put(eDistRefernce.getValue().getBusEntityId(), eDistRefernce.getKey());
        }

        DBCriteria cr = new DBCriteria();
        cr.addOneOf(ItemMappingDataAccess.BUS_ENTITY_ID, new ArrayList<Integer>(distIds));
        cr.addOneOfIsolated(ItemMappingDataAccess.CLW_ITEM_MAPPING,ItemMappingDataAccess.ITEM_NUM, new ArrayList<String>(itemNums));
        cr.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
        cr.addJoinCondition(ItemMappingDataAccess.ITEM_ID, ItemDataAccess.CLW_ITEM, ItemDataAccess.ITEM_ID);
        cr.addJoinTableEqualTo(ItemDataAccess.CLW_ITEM, ItemDataAccess.ITEM_STATUS_CD, RefCodeNames.ITEM_STATUS_CD.ACTIVE);

        ItemMappingDataVector list = ItemMappingDataAccess.select(pCon, cr);

        Map<Integer, ItemReference> alreadyMapped = new TreeMap<Integer, ItemReference>();
        for (int i = 0; list != null && i < list.size(); i++) {

            ItemMappingData item = (ItemMappingData) list.get(i);

            int distId = item.getBusEntityId();

            DistributorReference distReference = distrIdByDistRefernce.get(distId);
            if (distReference == null) {
                pErrors.add("Not found distributor name by ID:" + item.getBusEntityId());
                continue;
            }

            ItemReference itemReference = new ItemReference(distReference.getDistributor(), item.getItemNum().trim());

            int value = item.getItemId();

            ItemMappingData oldValue = result.put(itemReference, item);
            if (oldValue != null && oldValue.getItemId() != value) {
                pErrors.add("Was found duplicate for '" + itemReference + "':" + oldValue + " " + value);
            }

            ItemReference existKey = alreadyMapped.get(value);
            if (existKey != null) {
                pErrors.add("Item " + value + " has duplicate mapping: " + existKey + "," + itemReference);
            }

            alreadyMapped.put(value, itemReference);

        }
        log.info("getItemDistributorMap()=> END.");


        return result;

    }


    public static Map<ItemReference, ItemMappingData> getItemManufacturerMap(Connection pCon,
                                                                                        Map<ItemReference, ItemMappingData> pItemDistrMap,
                                                                                        Map<ManufacturerReference, BusEntityData> pManuf,
                                                                                        List<String> pErrors) throws SQLException {

        log.info("getItemManufacturerMap()=> BEGIN");
        Map<ItemReference, ItemMappingData> result = new HashMap<ItemReference, ItemMappingData>();

        Map<Integer, ItemReference> itemRefMap = new HashMap<Integer, ItemReference>();
        for (Map.Entry<ItemReference, ItemMappingData> itemMapping : pItemDistrMap.entrySet()) {
            itemRefMap.put(itemMapping.getValue().getItemId(), itemMapping.getKey());
        }

        IdVector manufIds = Utility.toIdVector(new ArrayList<BusEntityData>(pManuf.values()));

        if (!manufIds.isEmpty() && !itemRefMap.keySet().isEmpty()) {

            Map<Integer, ManufacturerReference> manufIdByManufRefernce = new HashMap<Integer, ManufacturerReference>();
            for (Map.Entry<ManufacturerReference, BusEntityData> eManufRefernce : pManuf.entrySet()) {
                manufIdByManufRefernce.put(eManufRefernce.getValue().getBusEntityId(), eManufRefernce.getKey());
            }

            DBCriteria cr = new DBCriteria();

            cr.addOneOf(ItemMappingDataAccess.BUS_ENTITY_ID, manufIds);
            cr.addOneOfIsolated(ItemMappingDataAccess.CLW_ITEM_MAPPING,ItemMappingDataAccess.ITEM_ID, new ArrayList<Integer>(itemRefMap.keySet()));
            cr.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
            cr.addJoinCondition(ItemMappingDataAccess.ITEM_ID, ItemDataAccess.CLW_ITEM, ItemDataAccess.ITEM_ID);
            cr.addJoinTableEqualTo(ItemDataAccess.CLW_ITEM, ItemDataAccess.ITEM_STATUS_CD, RefCodeNames.ITEM_STATUS_CD.ACTIVE);


            ItemMappingDataVector list = ItemMappingDataAccess.select(pCon, cr);

            Set<Integer> alreadyMapped = new HashSet<Integer>();
            for (int i = 0; list != null && i < list.size(); i++) {

                ItemMappingData item = (ItemMappingData) list.get(i);

                int manufId = item.getBusEntityId();

                ManufacturerReference manufReference = manufIdByManufRefernce.get(manufId);
                if (manufReference == null) {
                    pErrors.add("Not found manufacturer name by ID:" + item.getBusEntityId());
                    continue;
                }

                int val = item.getItemId();
                ItemReference itemRef = itemRefMap.get(val);

                ItemMappingData oldValue = result.put(itemRef, item);
                if (oldValue != null && oldValue.getItemId() != val) {
                    pErrors.add("Was found duplicate for '" + itemRef + "':" + oldValue + " " + val);
                }

                if (alreadyMapped.contains(val)) {
                    pErrors.add("Item " + val + " has duplicate mapping: " + val + "," + manufId);
                }

                alreadyMapped.add(val);

            }
            log.info("getItemManufacturerMap()=> END.");

        }

        return result;

    }


    public static Map<ItemReference, ItemMappingData> getItemStoreMap(Connection pCon,
                                                                                 Integer pStoreId,
                                                                                 Map<ItemReference, ItemData> pItemRefs,
                                                                                 List<String> pErrors) throws SQLException {

        log.info("getItemStoreMap()=> BEGIN");
        Map<ItemReference, ItemMappingData> result = new HashMap<ItemReference, ItemMappingData>();

        HashMap<Integer, ItemReference> itemRefIdMap = new HashMap<Integer, ItemReference>();
        for (Map.Entry<ItemReference, ItemData> e : pItemRefs.entrySet()) {
            itemRefIdMap.put(e.getValue().getItemId(), e.getKey());
        }

        if (!itemRefIdMap.isEmpty()) {

            DBCriteria cr = new DBCriteria();
            cr.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID, pStoreId);
            cr.addOneOfIsolated(ItemMappingDataAccess.CLW_ITEM_MAPPING,ItemMappingDataAccess.ITEM_ID, new ArrayList<Integer>(itemRefIdMap.keySet()));
            cr.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_STORE);
            cr.addJoinCondition(ItemMappingDataAccess.ITEM_ID, ItemDataAccess.CLW_ITEM, ItemDataAccess.ITEM_ID);
            cr.addJoinTableEqualTo(ItemDataAccess.CLW_ITEM, ItemDataAccess.ITEM_STATUS_CD, RefCodeNames.ITEM_STATUS_CD.ACTIVE);

            ItemMappingDataVector list = ItemMappingDataAccess.select(pCon, cr);

            Set<Integer> alreadyMapped = new HashSet<Integer>();
            for (int i = 0; list != null && i < list.size(); i++) {

                ItemMappingData item = (ItemMappingData) list.get(i);

                int val = item.getItemId();
                int storeId = item.getBusEntityId();

                ItemReference itemRef = itemRefIdMap.get(item.getItemId());
                if (itemRef != null) {

                    ItemMappingData oldValue = result.put(itemRef, item);
                    if (oldValue != null && oldValue.getItemId() != val) {
                        pErrors.add("Was found duplicate for '" + itemRef + "':" + oldValue + " " + val);
                    }

                    if (alreadyMapped.contains(val)) {
                        pErrors.add("Item " + val + " has duplicate mapping: " + val + "," + storeId);
                    }

                    alreadyMapped.add(val);


                }

            }

        }
        log.info("getItemStoreMap()=> END.");

        return result;
    }

    public static Map<ItemReference, Map<String, ItemMetaData>> getItemMetaMap(Connection pCon,
                                                                                          Map<ItemReference, ItemData> pItemReferencesMap,
                                                                                          List<String> pErrors) throws SQLException {

        log.info("getItemMetaMap()=> BEGIN");
        Map<ItemReference, Map<String, ItemMetaData>> result = new HashMap<ItemReference, Map<String, ItemMetaData>>();

        Map<Integer, ItemReference> itemRefMap = new HashMap<Integer, ItemReference>();
        for (Map.Entry<ItemReference,ItemData> item : pItemReferencesMap.entrySet()) {
            itemRefMap.put(item.getValue().getItemId(), item.getKey());
        }

        if (!itemRefMap.isEmpty()) {

            DBCriteria dbCriteria = new DBCriteria();
            dbCriteria.addOneOf(ItemMetaDataAccess.ITEM_ID, new ArrayList<Integer>(itemRefMap.keySet()));

            ItemMetaDataVector itemMetas = ItemMetaDataAccess.select(pCon, dbCriteria);
            for (Object oItemMeta : itemMetas) {

                ItemMetaData itemMeta = (ItemMetaData) oItemMeta;

                ItemReference itemRef = itemRefMap.get(itemMeta.getItemId());

                Map<String, ItemMetaData> metaMap = result.get(itemRef);
                if (metaMap == null) {
                    metaMap = new HashMap<String, ItemMetaData>();
                    result.put(itemRef, metaMap);
                }

                ItemMetaData oldValue = metaMap.put(itemMeta.getNameValue(), itemMeta);
                if (oldValue != null) {
                    pErrors.add("Item meta has duplicate value. Item:" + itemRef + ", Value: " + itemMeta.getNameValue() + "'");
                }

            }

        }

        log.info("getItemMetaMap()=> END.");
        return result;

    }


    public static List<PairView> getMetaValueList(ItemData pItem, XpedxCatalogItemView pInboundItem) {

        List<PairView> metaValueList = new ArrayList<PairView>();

        if (Utility.isSet(pInboundItem.getHazmat())) {
            String value = YES.equalsIgnoreCase(pInboundItem.getHazmat()) ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
            metaValueList.add(new PairView(ProductData.HAZMAT, value));
        } else {
            metaValueList.add(new PairView(ProductData.HAZMAT, Boolean.FALSE.toString()));
        }

        if (Utility.isSet(pInboundItem.getImage())) {
            metaValueList.add(new PairView(ProductData.IMAGE, createImagePathForMapping(pItem.getItemId(), pInboundItem.getImage())));
        }

        if (Utility.isSet(pInboundItem.getColor())) {
            metaValueList.add(new PairView(ProductData.COLOR, String.valueOf(pInboundItem.getColor())));
        }

        if (Utility.isSet(pInboundItem.getPack())) {
            metaValueList.add(new PairView(ProductData.PACK, String.valueOf(pInboundItem.getPack())));
        }

        if (Utility.isSet(pInboundItem.getShippingCubicSize())) {
            metaValueList.add(new PairView(ProductData.CUBE_SIZE, String.valueOf(pInboundItem.getShippingCubicSize())));
        }

        if (Utility.isSet(pInboundItem.getShippingWeight())) {
            metaValueList.add(new PairView(ProductData.SHIP_WEIGHT, String.valueOf(pInboundItem.getShippingWeight())));
        }

        if (Utility.isSet(pInboundItem.getWeightUnit())) {
            metaValueList.add(new PairView(ProductData.WEIGHT_UNIT, String.valueOf(pInboundItem.getWeightUnit())));
        } else {
            metaValueList.add(new PairView(ProductData.WEIGHT_UNIT, RefCodeNames.WEIGHT_UNIT.OUNCE));
        }

        if (Utility.isSet(pInboundItem.getUOM())) {
            metaValueList.add(new PairView(ProductData.UOM, String.valueOf(pInboundItem.getUOM())));
        }

        if (Utility.isSet(pInboundItem.getListPrice())) {
            metaValueList.add(new PairView(ProductData.LIST_PRICE, String.valueOf(pInboundItem.getListPrice())));
        }

        if (Utility.isSet(pInboundItem.getProductUPC())) {
            metaValueList.add(new PairView(ProductData.UPC_NUM, String.valueOf(pInboundItem.getProductUPC())));
        }

        if (Utility.isSet(pInboundItem.getPackUPC())) {
            metaValueList.add(new PairView(ProductData.PKG_UPC_NUM, String.valueOf(pInboundItem.getPackUPC())));
        }

        if (Utility.isSet(pInboundItem.getSize())) {
            metaValueList.add(new PairView(ProductData.SIZE, String.valueOf(pInboundItem.getSize())));
        }
        
        if (Utility.isSet(pInboundItem.getUnspscCd())) {
            metaValueList.add(new PairView(ProductData.UNSPSC_CD, String.valueOf(pInboundItem.getUnspscCd())));
        }

        return metaValueList;
    }


      public static String createImagePathForMapping(int pItemId, String pImageUtl) {
        return new StringBuilder()
                .append(IMAGE_BASE_PATH)
                .append(pItemId)
                .append(IOUtilities.getFileExt(pImageUtl).toLowerCase()).toString();
    }

    private static String createImagePathForContent(int pItemId, String pImageUtl) {
        return new StringBuilder()
                .append(".")
                .append(IMAGE_BASE_PATH)
                .append(pItemId)
                .append(IOUtilities.getFileExt(pImageUtl).toLowerCase()).toString();
    }


    public static Map<Integer, Map<ItemReference, ItemAssocData>> getItemCategoryAssocMap(Connection pCon,
                                                                                                     List pCatalogIds,
                                                                                                     Map<ItemReference, ItemMappingData> pItemDistrMap,
                                                                                                     List<String> pErrors) throws Exception {

        log.info("getItemCategoryAssocMap()=> BEGIN");
        Map<Integer/*CatalkogID*/, Map<ItemReference, ItemAssocData>> result = new HashMap<Integer/*CatalkogID*/, Map<ItemReference, ItemAssocData>>();

        Map<Integer, ItemReference> itemRefMap = new HashMap<Integer, ItemReference>();
        for (Map.Entry<ItemReference, ItemMappingData> itemMapping : pItemDistrMap.entrySet()) {
            itemRefMap.put(itemMapping.getValue().getItemId(), itemMapping.getKey());
        }

        if (!itemRefMap.isEmpty() && !pCatalogIds.isEmpty()) {

            DBCriteria cr = new DBCriteria();

            cr.addOneOf(ItemAssocDataAccess.CATALOG_ID, pCatalogIds);
            cr.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
            cr.addOneOf(ItemAssocDataAccess.ITEM1_ID, new ArrayList<Integer>(itemRefMap.keySet()));

            ItemAssocDataVector list = ItemAssocDataAccess.select(pCon, cr);
            for (int i = 0; list != null && i < list.size(); i++) {

                ItemAssocData item = (ItemAssocData) list.get(i);

                Map<ItemReference, ItemAssocData> itemCategoryMap = result.get(item.getCatalogId());
                if (itemCategoryMap == null) {
                    itemCategoryMap = new HashMap<ItemReference, ItemAssocData>();
                    result.put(item.getCatalogId(), itemCategoryMap);
                }

                ItemAssocData oldValue = itemCategoryMap.put(itemRefMap.get(item.getItem1Id()), item);
                if (oldValue != null) {
                    pErrors.add("Item " + item.getItem1Id() + " already associated with category " + item.getItem2Id() + " for catalog " + item.getCatalogId());
                }


            }

        }
        log.info("getItemCategoryAssocMap()=> END.");

        return result;

    }


    public static Map<Integer, Map<ItemReference, ContractItemData>> getContractItemsByCatalogId(Connection pCon,
                                                                                           Map<Integer/*CatalogID*/, ContractData> pContractMap,
                                                                                           Map<ItemReference, ItemData> pItemReferencesMap,
                                                                                           List<String> pErrors) throws Exception {

        log.info("getContractItemsByCatalogId()=> BEGIN");
        Map<Integer, Map<ItemReference, ContractItemData>> result = new HashMap<Integer, Map<ItemReference, ContractItemData>>();

        Map<Integer/*ContractID*/,ContractData> contractMap = new HashMap<Integer,ContractData>();
        for(ContractData contract: pContractMap.values()){
             contractMap.put(contract.getContractId(),contract);
        }

        Map<Integer, ItemReference> itemRefMap = new HashMap<Integer, ItemReference>();
        for (Map.Entry<ItemReference, ItemData> item : pItemReferencesMap.entrySet()) {
            itemRefMap.put(item.getValue().getItemId(), item.getKey());
        }

        if (!contractMap.isEmpty() && !itemRefMap.isEmpty()) {

            DBCriteria cr = new DBCriteria();
            cr.addOneOf(ContractItemDataAccess.CONTRACT_ID, new ArrayList<Integer>(contractMap.keySet()));
            cr.addOneOf(ContractItemDataAccess.ITEM_ID, new ArrayList<Integer>(itemRefMap.keySet()));

            ContractItemDataVector list = ContractItemDataAccess.select(pCon, cr);

            for (int i = 0; list != null && i < list.size(); i++) {

                ContractItemData item = (ContractItemData) list.get(i);

                ContractData contract  = contractMap.get(item.getContractId());

                ItemReference ref = itemRefMap.get(item.getItemId());

                Map<ItemReference, ContractItemData> val = result.get(contract.getCatalogId());
                if (val == null) {
                    val = new HashMap<ItemReference, ContractItemData>();
                    result.put(contract.getCatalogId(), val);
                }

                ContractItemData oldItem = val.put(ref, item);
                if (oldItem != null) {
                    pErrors.add("Was found duplicate contract item for contract:"
                            + item.getContractId() + " items:"
                            + oldItem.getItemId() + "," + item.getItemId());
                }

            }
        }

        log.info("getContractItemsByCatalogId()=> END.");
        return result;
    }


    public static Map<Integer, Map<ItemReference, InventoryItemsData>> getInventoryItemsByAccountId(Connection pCon,
                                                                                                               Map<AccountReference, Map<Integer, BusEntityData>> pAccounRefMsp,
                                                                                                               Map<ItemReference, ItemData> pItemReferencesMap) throws Exception {


        log.info("getInventoryItemsByAccountId()=> BEGIN");
        Set<Integer> accountIds = new HashSet<Integer>();
        for (Map<Integer, BusEntityData> accountMap : pAccounRefMsp.values()) {
            accountIds.addAll(accountMap.keySet());
        }

        Map<Integer, ItemReference> itemRefMap = new HashMap<Integer, ItemReference>();
        for (Map.Entry<ItemReference, ItemData> item : pItemReferencesMap.entrySet()) {
            itemRefMap.put(item.getValue().getItemId(), item.getKey());
        }

        Map<Integer, Map<ItemReference, InventoryItemsData>> result = new HashMap<Integer, Map<ItemReference, InventoryItemsData>>();

        if (!accountIds.isEmpty() && !itemRefMap.isEmpty()) {

            DBCriteria dbCriteria = new DBCriteria();
            dbCriteria.addOneOf(InventoryItemsDataAccess.BUS_ENTITY_ID, new ArrayList<Integer>(accountIds));
            dbCriteria.addOneOf(InventoryItemsDataAccess.ITEM_ID, new ArrayList<Integer>(itemRefMap.keySet()));

            InventoryItemsDataVector items = InventoryItemsDataAccess.select(pCon, dbCriteria);
            for (Object oItem : items) {
                InventoryItemsData invItem = (InventoryItemsData) oItem;
                int accountId = invItem.getBusEntityId();
                ItemReference ref = itemRefMap.get(invItem.getItemId());
                if (!result.containsKey(accountId)) {
                    HashMap<ItemReference, InventoryItemsData> map = new HashMap<ItemReference, InventoryItemsData>();
                    map.put(ref, invItem);
                    result.put(accountId, map);
                } else {
                    Map<ItemReference, InventoryItemsData> map = result.get(accountId);
                    map.put(ref, invItem);
                }
            }
        }
        log.info("getInventoryItemsByAccountId()=> END.");

        return result;

    }

    public static HashMap<Integer, Set<Integer>> getShoppingCatalogIdsByItem(Connection pCon,
                                                                             Set<Integer> pExistsAccountShoppingCatalogIds,
                                                                             Integer pItemId) throws Exception {

        DBCriteria dbc = new DBCriteria();
        dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, new ArrayList<Integer>(pExistsAccountShoppingCatalogIds));
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
        dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pItemId);

        HashMap<Integer, Set<Integer>> shoppingCatalogIdsByItem = new HashMap<Integer, Set<Integer>>();

        CatalogStructureDataVector csList = CatalogStructureDataAccess.select(pCon, dbc);
        for (Object oCatalogStructure : csList) {
            CatalogStructureData catalogStructure = (CatalogStructureData) oCatalogStructure;
            Set<Integer> catalogIdSet = shoppingCatalogIdsByItem.get(catalogStructure.getItemId());
            if (catalogIdSet == null) {
                catalogIdSet = new HashSet<Integer>();
                shoppingCatalogIdsByItem.put(catalogStructure.getItemId(), catalogIdSet);
            }
            catalogIdSet.add(catalogStructure.getCatalogId());
        }

        return shoppingCatalogIdsByItem;
    }


    public static Set<Integer> getShoppingCatalogIdsFor(Connection pCon, int pAccountCatalogId) throws Exception {

        log.info("getShoppingCatalogIdsFor()=> BEGIN");
        DBCriteria accCrit = new DBCriteria();
        accCrit.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pAccountCatalogId);
        accCrit.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);

        IdVector busEntityIds = CatalogAssocDataAccess.selectIdOnly(pCon, CatalogAssocDataAccess.BUS_ENTITY_ID, accCrit);

        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
        crit.addJoinCondition(CatalogDataAccess.CATALOG_ID, CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ID);
        crit.addJoinTableOneOf(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.BUS_ENTITY_ID, busEntityIds);
        crit.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);



        CatalogDataVector catalogs = CatalogDataAccess.select(pCon, crit);

        log.info("getShoppingCatalogIdsFor()=> END, RETURN...");

        return new HashSet<Integer>(Utility.toIdVector(catalogs));

    }

    public static Map<Integer, Map<ItemReference, ShoppingControlData>> getShoppingControlsByAccountId(Connection pCon,
                                                                                                       Map<AccountReference, Map<Integer, BusEntityData>> pAccounRefMsp,
                                                                                                       Map<ItemReference, ItemData> pItemReferencesMap) throws Exception {

        log.info("getShoppingControlsByAccountId()=> BEGIN");
        Set<Integer> accountIds = new HashSet<Integer>();
        for (Map<Integer, BusEntityData> accountMap : pAccounRefMsp.values()) {
            accountIds.addAll(accountMap.keySet());
        }

        Map<Integer, ItemReference> itemRefMap = new HashMap<Integer, ItemReference>();
        for (Map.Entry<ItemReference, ItemData> item : pItemReferencesMap.entrySet()) {
            itemRefMap.put(item.getValue().getItemId(), item.getKey());
        }

        Map<Integer, Map<ItemReference, ShoppingControlData>> result = new HashMap<Integer, Map<ItemReference, ShoppingControlData>>();
        if (!accountIds.isEmpty() && !itemRefMap.isEmpty()) {

            DBCriteria dbCriteria = new DBCriteria();
            dbCriteria.addOneOf(ShoppingControlDataAccess.ACCOUNT_ID, new ArrayList<Integer>(accountIds));
            dbCriteria.addIsNullOr0(ShoppingControlDataAccess.SITE_ID);

            ShoppingControlDataVector items = ShoppingControlDataAccess.select(pCon, dbCriteria);
            for (Object oItem : items) {
                ShoppingControlData item = (ShoppingControlData) oItem;
                int accountId = item.getAccountId();
                ItemReference ref = itemRefMap.get(item.getItemId());
                if (!result.containsKey(accountId)) {
                    HashMap<ItemReference, ShoppingControlData> map = new HashMap<ItemReference, ShoppingControlData>();
                    map.put(ref, item);
                    result.put(accountId, map);
                } else {
                    Map<ItemReference, ShoppingControlData> map = result.get(item.getAccountId());
                    map.put(ref, item);
                }
            }
        }
        log.info("getShoppingControlsByAccountId()=> END.");

        return result;
    }


    public static Map<ItemReference, ContentData> getItemContent(Connection pCon,
                                                                            Map<ItemReference, ItemData> pItemDataByItemRef,
                                                                            Map<ItemReference, InboundItemData> pInboundDataByItemRef) throws Exception {

        log.info("getItemContent()=> BEGIN");
        HashMap<ItemReference, ContentData> result = new HashMap<ItemReference, ContentData>();

        Map<String, ItemReference> imgPathRefMap = new HashMap<String, ItemReference>();
        DBCriteria dbc;
        for (Map.Entry<ItemReference, InboundItemData> iData : pInboundDataByItemRef.entrySet()) {
            if (Utility.isSet(iData.getValue().getImage())) {
                ItemData itemData = pItemDataByItemRef.get(iData.getKey());
                if (itemData != null) {
                    String imagePath = createImagePathForContent(itemData.getItemId(), iData.getValue().getImage());
                    imgPathRefMap.put(imagePath, iData.getKey());
                }
            }
        }

        log.info("getItemContent()=> READING...Items count: "+imgPathRefMap.keySet().size());


        if (!imgPathRefMap.isEmpty()) {

            List<List> pkgs = Utility.createPackages(new ArrayList<String>(imgPathRefMap.keySet()), 500);

            for (List _package : pkgs) {

                dbc = new DBCriteria();

                dbc.addEqualTo(ContentDataAccess.SHORT_DESC, ITEM_IMAGE);
                dbc.addEqualTo(ContentDataAccess.CONTENT_TYPE_CD, IMAGE);
                dbc.addEqualTo(ContentDataAccess.CONTENT_USAGE_CD, ITEM_IMAGE);
                dbc.addEqualTo(ContentDataAccess.SOURCE_CD, XSUITE_APP);
                dbc.addOneOf(ContentDataAccess.PATH, _package);

                ContentDataVector images = ContentDataAccess.select(pCon, dbc);
                if (!images.isEmpty()) {
                    for (Object oImg : images) {

                        ContentData img = (ContentData) oImg;

                        ItemReference itemRef = imgPathRefMap.get(img.getPath());
                        if (itemRef != null) {
                            result.put(itemRef, img);
                        }
                    }
                }

            }
        }

        log.info("getItemContent()=> Map.Size: " + result.size());
        log.info("getItemContent()=> END.");

        return result;
    }

    public static ContentData createImageContent(int pItemId, String pPath, byte[] pImageData, String pForUser) {

        ContentData data = ContentData.createValue();

        data.setShortDesc(ITEM_IMAGE);
        data.setContentTypeCd(IMAGE);
        data.setContentStatusCd(RefCodeNames.SIMPLE_STATUS_CD.ACTIVE);
        data.setLocaleCd(X);
        data.setLanguageCd(X);
        data.setContentUsageCd(ITEM_IMAGE);
        data.setSourceCd(XSUITE_APP);
        data.setAddBy(pForUser);
        data.setModBy(pForUser);
        data.setPath(createImagePathForContent(pItemId, pPath));
        data.setBinaryData(pImageData);

        return data;
    }

    public static Map<Integer, FreightTableData> getFreightTables(Connection pCon, Integer pStoreId, Set<Integer> pFreightTableIds) throws Exception {

        log.info("getFreightTables()=> BEGIN");

        log.info("getFreightTables()=> FreightTableID(s): " + pFreightTableIds);
        log.info("getFreightTables()=> StoreID: " + pStoreId);

        Map<Integer, FreightTableData> result = new HashMap<Integer, FreightTableData>();

        DBCriteria cr = new DBCriteria();

        cr.addOneOf(FreightTableDataAccess.FREIGHT_TABLE_ID, new ArrayList<Integer>(pFreightTableIds));
        cr.addEqualTo(FreightTableDataAccess.STORE_ID, pStoreId);
        FreightTableDataVector list = FreightTableDataAccess.select(pCon, cr);
        for (int i = 0; list != null && i < list.size(); i++) {
            FreightTableData item = (FreightTableData) list.get(i);
            result.put(item.getFreightTableId(), item);
        }

        log.info("getFreightTables()=> Result.Size: "+result.size());

        log.info("getFreightTables()=> END.");

        return result;
    }


      public static Map<String, Integer> getPriceDecimalsMap(Connection pCon, Set<String> pLocales) throws Exception {

        log.info("getPriceDecimalsMap()=> BEGIN. pLocales: " + pLocales);

        Map<String, Integer> result = new HashMap<String, Integer>();

        DBCriteria crit = new DBCriteria();
        crit.addOneOf(CurrencyDataAccess.LOCALE, new ArrayList<String>(pLocales));

        CurrencyDataVector currDV = CurrencyDataAccess.select(pCon, crit);
        for (Object oCurr : currDV) {
            CurrencyData curr = (CurrencyData) oCurr;
            result.put(curr.getLocale(), curr.getDecimals());
        }

        log.info("getPriceDecimalsMap()=> END. result: " + result);

        return result;
    }



    public static List<CategoryReference> createCategoryReferences(String pMasterCustomerName, List<String> pItemCategories) {

        List<CategoryReference> list = new ArrayList<CategoryReference>();

        for (int i = 0; i < pItemCategories.size(); i++) {

            String category = pItemCategories.get(i);
            List<String> parents = new ArrayList<String>();
            for (int j = 0; j < i; j++) {
                parents.add(pItemCategories.get(j).trim());
            }

            list.add(new CategoryReference(pMasterCustomerName, category.trim(), parents));

        }

        return list;
    }

    public static List<String> getItemCategories(XpedxCatalogItemView pItem) {

        List<String> path = new ArrayList<String>();

        path.add(pItem.getCategory());

        if (Utility.isSet(pItem.getSubCat1())) {
            path.add(pItem.getSubCat1());
            if (Utility.isSet(pItem.getSubCat2())) {
                path.add(pItem.getSubCat2());
                if (Utility.isSet(pItem.getSubCat3())) {
                    path.add(pItem.getSubCat3());
                }
            }
        }

        return path;
    }

    public static Map<CategoryReference, Map<CategoryReference, Map<CategoryReference, Set<CategoryReference>>>> createsCategoryTree(List<Line<XpedxCatalogItemView>> pLine) {

        Map<CategoryReference, Map<CategoryReference, Map<CategoryReference, Set<CategoryReference>>>> categoriesTree;
        categoriesTree = new HashMap<CategoryReference, Map<CategoryReference, Map<CategoryReference, Set<CategoryReference>>>>();
        for (Line<XpedxCatalogItemView> line : pLine) {
            XpedxCatalogItemView item = line.getItem();
            add2CategoriesTree(categoriesTree, item, item.getMasterCustomerName());
        }
        return categoriesTree;
    }

    private static void add2CategoriesTree(Map<CategoryReference, Map<CategoryReference, Map<CategoryReference, Set<CategoryReference>>>> pCategoriesTree,
                                    XpedxCatalogItemView pItem,
                                    String pMasterCustomerName) {

        List<CategoryReference> categoryReference = createCategoryReferences(pMasterCustomerName, getItemCategories(pItem));

        CategoryReference category = null;
        CategoryReference category1 = null;
        CategoryReference category2 = null;
        CategoryReference category3 = null;

        if (categoryReference.size() > 0) {
            category = categoryReference.get(0);
        }

        if (categoryReference.size() > 1) {
            category1 = categoryReference.get(1);
        }

        if (categoryReference.size() > 2) {
            category2 = categoryReference.get(2);
        }

        if (categoryReference.size() > 3) {
            category3 = categoryReference.get(3);
        }

        if (category != null) {

            Map<CategoryReference, Map<CategoryReference, Set<CategoryReference>>> children = pCategoriesTree.get(category);
            if (children == null) {
                children = new TreeMap<CategoryReference, Map<CategoryReference, Set<CategoryReference>>>();
                pCategoriesTree.put(category, children);
            }

            if (category1 != null) {

                Map<CategoryReference, Set<CategoryReference>> children1 = children.get(category1);
                if (children1 == null) {
                    children1 = new TreeMap<CategoryReference, Set<CategoryReference>>();
                    children.put(category1, children1);
                }

                if (category2 != null) {

                    Set<CategoryReference> children2 = children1.get(category2);
                    if (children2 == null) {
                        children2 = new TreeSet<CategoryReference>();
                        children1.put(category2, children2);
                    }

                    if (category3 != null) {
                        children2.add(category3);
                    }
                }
            }
        }
    }
    public static Map<Integer, Map<Integer, ShoppingControlData>> getShoppingControlsByAccountId(Connection pCon, Collection<Integer> pAccounIds) throws Exception {

        Map<Integer, Map<Integer, ShoppingControlData>> result = new HashMap<Integer, Map<Integer, ShoppingControlData>>();

        DBCriteria dbCriteria = new DBCriteria();
        dbCriteria.addOneOf(ShoppingControlDataAccess.ACCOUNT_ID, new ArrayList<Integer>(pAccounIds));
        dbCriteria.addIsNullOr0(ShoppingControlDataAccess.SITE_ID);

        ShoppingControlDataVector items = ShoppingControlDataAccess.select(pCon, dbCriteria);
        for (Object oItem : items) {
            ShoppingControlData item = (ShoppingControlData) oItem;
            int accountId = item.getAccountId();
            if (!result.containsKey(accountId)) {
                HashMap<Integer, ShoppingControlData> map = new HashMap<Integer, ShoppingControlData>();
                map.put(item.getItemId(), item);
                result.put(accountId, map);
            } else {
                Map<Integer, ShoppingControlData> map = result.get(item.getAccountId());
                map.put(item.getItemId(), item);
            }
        }
        return result;
    }

    public static Map<Integer, List<ShoppingControlData>> getShoppingControlsByAccountId(Connection pCon,
                                                                                         Collection<Integer> pAccounIds,
                                                                                         Set<Integer> pItems,
                                                                                         boolean pSiteIsNullOr0) throws Exception {

        Map<Integer, List<ShoppingControlData>> result = new HashMap<Integer, List<ShoppingControlData>>();

        DBCriteria dbCriteria = new DBCriteria();
        dbCriteria.addOneOf(ShoppingControlDataAccess.ACCOUNT_ID, new ArrayList<Integer>(pAccounIds));
        if (pItems != null) {
            dbCriteria.addOneOf(ShoppingControlDataAccess.ITEM_ID, new ArrayList<Integer>(pItems));
        }
        if (pSiteIsNullOr0) {
            dbCriteria.addIsNullOr0(ShoppingControlDataAccess.SITE_ID);
        }

        ShoppingControlDataVector items = ShoppingControlDataAccess.select(pCon, dbCriteria);
        for (Object oItem : items) {
            ShoppingControlData item = (ShoppingControlData) oItem;
            int accountId = item.getAccountId();
            if (!result.containsKey(accountId)) {
                List<ShoppingControlData> list = new ArrayList<ShoppingControlData>();
                list.add(item);
                result.put(accountId, list);
            } else {
                List<ShoppingControlData> list = result.get(item.getAccountId());
                list.add(item);
                result.put(accountId, list);
            }
        }

        return result;
    }

    public static Map<Integer, Map<Integer, InventoryItemsData>> getInventoryItemsByAccountId(Connection pCon,
                                                                                              Collection<Integer> pAccountIds,
                                                                                              Set<Integer> pItems) throws Exception {

        Map<Integer, Map<Integer, InventoryItemsData>> result = new HashMap<Integer, Map<Integer, InventoryItemsData>>();

        DBCriteria dbCriteria = new DBCriteria();
        dbCriteria.addOneOf(InventoryItemsDataAccess.BUS_ENTITY_ID, new ArrayList<Integer>(pAccountIds));
        if (pItems != null) {
            dbCriteria.addOneOf(InventoryItemsDataAccess.ITEM_ID, new ArrayList<Integer>(pItems));
        }

        InventoryItemsDataVector items = InventoryItemsDataAccess.select(pCon, dbCriteria);
        for (Object oItem : items) {
            InventoryItemsData invItem = (InventoryItemsData) oItem;
            int accountId = invItem.getBusEntityId();
            if (!result.containsKey(accountId)) {
                HashMap<Integer, InventoryItemsData> map = new HashMap<Integer, InventoryItemsData>();
                map.put(invItem.getItemId(), invItem);
                result.put(accountId, map);
            } else {
                Map<Integer, InventoryItemsData> map = result.get(accountId);
                map.put(invItem.getItemId(), invItem);
            }
        }
        return result;
    }

}
