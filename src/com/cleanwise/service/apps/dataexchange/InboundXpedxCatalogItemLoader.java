package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.session.Catalog;
import com.cleanwise.service.api.session.Manufacturer;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.value.CurrencyData;
import com.cleanwise.service.apps.dataexchange.InboundXpedxLoaderHelper.ComparableList;
import com.cleanwise.service.apps.dataexchange.xpedx.IXCatalogItemLoader;
import org.apache.log4j.Logger;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.Map.Entry;

public class InboundXpedxCatalogItemLoader extends IXCatalogItemLoader {

/*
    protected Logger log = Logger.getLogger(this.getClass());

    private static final String LOADER_FMT = "Line {0} : {1}";
    private static final String YES = "YES";
    private static final String NO = "NO";
    private static final String UNKNOWN = "UNKNOWN";
    private static final String ZERO = "0";

    private final static String ADD_BY = "xpedxCatalogItemLoader";
    private final static Date ADD_DATE = new Date();

    private Map<String, HashMap<Integer, ItemData>> multiProdsByAcctRef = new HashMap<String, HashMap<Integer, ItemData>>();
    private Map<String, Map<String, Integer>> acctExistCategories = new HashMap<String, Map<String, Integer>>();
    private Map<String, Integer> itemDistMap = new HashMap<String, Integer>();
    private Map<Integer, Integer> acctCatalogs = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> existStoreCatalogCategoryRelations = new HashMap<Integer, Integer>();
    private HashMap<String, Map<String, List<CostCenterData>>> existCostCentersByAcctRef = new HashMap<String, Map<String, List<CostCenterData>>>();
    private Map<Integer, Integer> acctMainDist = new HashMap<Integer, Integer>();

    private final List<XpedxCatalogItemView> parsedDataList = new ArrayList<XpedxCatalogItemView>();

    private boolean headerWasLoad = false;
    private boolean isCategorySupportLevel4 = false;
    private boolean isIgnoreCategoryLevel4 = false;

    private static final Pattern urlPattern = Pattern.compile("^(ht|f)tp(s?)\\:\\/\\/[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*(:(0-9)*)*(\\/?)([a-zA-Z0-9\\-\\.\\?\\,\\'\\/\\\\\\+&amp;%\\$#_]*)?$");

    public InboundXpedxCatalogItemLoader() {
        super.setSepertorChar('|');
    }

    protected void init() {
        super.init();
        parsedDataList.clear();
        headerWasLoad = false;
        isCategorySupportLevel4 = true;
        isIgnoreCategoryLevel4 = false;

        log.info("init()=> headerWasLoad: " + headerWasLoad +
                ", isCategorySupportLevel4: " + isCategorySupportLevel4 +
                ", isIgnoreCategoryLevel4: " + isIgnoreCategoryLevel4);
    }

    *//**
     * Called when the object has successfully been parsed
     *//*
    protected void processParsedObject(Object pParsedObject) throws Exception {
        if (pParsedObject instanceof XpedxCatalogItemView) {
            if (headerWasLoad) {
                parsedDataList.add((XpedxCatalogItemView) pParsedObject);
            } else {
                headerWasLoad = true;
            }
        } else {
            throw new IllegalArgumentException("No parsed catalog item object present!");
        }
    }

    @Override
    protected void doPostProcessing() throws Exception {

        log.info("doPostProcessing()=> BEGIN");

        List<String> errors = new ArrayList<String>();

        log.info("doPostProcessing()=> Checking input data");
        checkInputValues(errors);
        if (!errors.isEmpty()) {
            for (String error : errors) {
                appendErrorMsgs(error);
            }
            log.info("doPostProcessing()=> Post-processing rejected, found " + errors.size() + " errors.");
            throw new Exception(super.getFormatedErrorMsgs());
        }

        ProcessData processData = prepareProcessData();

        Connection con = null;
        try {

            con = getConnection();

            int tradingPartnerId = getTranslator().getPartner().getTradingPartnerId();
            int storeId = getStoreId(con, tradingPartnerId);
            int storeCatalogId = getStoreCatalogId(con, storeId);

            existStoreCatalogCategoryRelations = InboundXpedxLoaderHelper.getCategoryParentIdByChildId(con, storeCatalogId);

            //Get accounts (KOHLSEDI,176650)
            Map<String, Integer> accountIdsByAccountRefMap = InboundXpedxLoaderHelper.getAccountIdsByAccountRef(con,
                    tradingPartnerId,
                    processData.getAccountRefs(),
                    processData.getErrors());

            Map<String, Integer> localePriceDecimals = InboundXpedxLoaderHelper.getPriceDecimalsMap(con, processData.getInboundLocales());

            log.info("doPostProcessing()=> accountIdsByAccountRef: " + accountIdsByAccountRefMap);
            int iii = 1;
            for (Map.Entry<String, Integer> es : accountIdsByAccountRefMap.entrySet()) {

                Map<String, Integer> accountIdsByAccountRef = new TreeMap<String, Integer>();
                String key = Utility.getFirstToken(es.getKey());
                accountIdsByAccountRef.put(key, es.getValue());

                log.info("doPostProcessing()=> looping through accountIdsByAccountRefMap " + iii++);
                log.info("doPostProcessing()=> accountIdsByAccountRef " + es.getKey() + ", value " + es.getValue());

                populateAccountInfo(con, storeCatalogId, accountIdsByAccountRef);

                if (processData.getAllDists() != null && processData.getAllDists().size() > 0) {

                    for (String distReference : processData.getAllDists()) {

                        if (!itemDistMap.containsKey(distReference)) {

                            DBCriteria crit = new DBCriteria();
                            crit.addEqualToIgnoreCase(BusEntityDataAccess.SHORT_DESC, distReference);
                            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
                            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);

                            IdVector distIdV = BusEntityDataAccess.selectIdOnly(con, BusEntityDataAccess.BUS_ENTITY_ID, crit);
                            if (distIdV != null && distIdV.size() > 0) {
                                log.info("doPostProcessing()=> Adding dist " + distIdV.get(0).toString());
                                itemDistMap.put(distReference, (Integer) distIdV.get(0));
                            }
                        }
                    }
                }

                //[cat_ref, acct_ref, cat_id]
                Map<ComparableList, Integer> catalogIdsByAccountCatalogRef =
                        InboundXpedxLoaderHelper.getCatalogIdsByAccountCatalogRef(
                                con,
                                tradingPartnerId,
                                processData.getAccountCatalogRefs(),
                                processData.getErrors());

                HashMap<String, Set<Integer>> catalogIdsByNameForStore = InboundXpedxLoaderHelper.getCatalogIdsByNameForStore(con, storeId);

                log.info("doPostProcessing()=> catalogIdsByAccountCatalogRef:" + catalogIdsByAccountCatalogRef);

                Map<String, Integer> distIdByName = InboundXpedxLoaderHelper.getDistIdsByName(con, tradingPartnerId, processData.getErrors());
                Map<String, Integer> manufIdByName = InboundXpedxLoaderHelper.getManufIdsByName(con, storeId, processData.getErrors());

                Map<String, List<Integer>> catalogDistIds = new TreeMap<String, List<Integer>>();
                for (Map.Entry<String, List<String>> e : processData.getCatalogDistRefs().entrySet()) {

                    String catRef = e.getKey();
                    List<String> dists = e.getValue();

                    List<Integer> allDistIds = new ArrayList<Integer>();

                    for (String dist : dists) {
                        Integer distId = distIdByName.get(dist);
                        allDistIds.add(distId);
                    }

                    catalogDistIds.put(catRef, allDistIds);
                }

                Map<Integer, String> distNameById = new TreeMap<Integer, String>();
                for (Map.Entry<String, Integer> e : distIdByName.entrySet()) {
                    distNameById.put(e.getValue(), e.getKey());
                }

                Set<ComparableList> distItemKeys = checkItemDuplicates(processData.getGroupedByAccountCatalog(),
                        distIdByName,
                        processData.getErrors());

                Map<ComparableList, Integer> itemIdsByDistrItemRef =
                        InboundXpedxLoaderHelper.getItemIdsByDistItemRef(con,
                                distItemKeys,
                                distNameById,
                                processData.getErrors());

                Map<ComparableList, ItemData> itemDataByDistItemRef =
                        InboundXpedxLoaderHelper.getItemDataByDistItemRef(con, itemIdsByDistrItemRef);

               Map<Integer, ContentData> itemContentMap  =
                       InboundXpedxLoaderHelper.getItemContent(con,
                               itemDataByDistItemRef,
                               parsedDataList);

                Map<Integer, FreightTableData> freightTablesMap = InboundXpedxLoaderHelper.getFreightTables(con, processData.getAllFreightTableIds());

                Map<Integer, Integer> storesByAccount =
                        InboundXpedxLoaderHelper.getStoreIdsByAccountId(con,
                                accountIdsByAccountRef.values(),
                                processData.getErrors());

                Set<ComparableList> catalogsForCreate = new TreeSet<ComparableList>();
                catalogsForCreate.addAll(processData.getAccountCatalogRefs());

                checkAccounts(accountIdsByAccountRefMap, processData.getAccountRefs(), processData.getErrors());
                checkLocales(processData.getGroupedByAccountCatalog(), localePriceDecimals);
                checkImage(processData.getImageUrls(),processData.getInboundImageDataMap(), processData.getErrors());
                checkExistCatalogs(accountIdsByAccountRef, catalogIdsByAccountCatalogRef, catalogsForCreate, catalogIdsByNameForStore, processData.getErrors());
                checkCategoriesTree(processData.getCategoriesTree(), processData.getErrors());
                checkExistItems(itemDataByDistItemRef, processData.getDistItemRefs());
                checkExistMultiProducts(key, processData.getMultiProductsByAcct(), processData.getErrors());
                checkExistFreightTables(freightTablesMap, processData.getFreightTableIdsByCatalogReference(), processData.getErrors());
                checkExistCostCenters(key, processData.getCostCenterNamesByAcctRef(), processData.getErrors());

                log.info("doPostProcessing()=> Getting exist contracts");
                Map<Integer, ContractData> contractsByCatalog = InboundXpedxLoaderHelper.getContracts(con, catalogIdsByAccountCatalogRef.values());

                Set<Integer> contractIds = new TreeSet<Integer>();
                for (ContractData contractData : contractsByCatalog.values()) {
                    contractIds.add(contractData.getContractId());
                }

                log.info("doPostProcessing()=> Getting allowed exist categories.");
                Map<Integer, Set<Integer>> categoryIdsByCatalogId = InboundXpedxLoaderHelper.getCategoryIdsByCatalogId(con, catalogIdsByAccountCatalogRef.values());

                log.info("doPostProcessing()=> Getting exist categories for store catalog.");
                Map<Integer, Set<Integer>> categoryIdsByStoreCatalogId = InboundXpedxLoaderHelper.getCategoryIdsByStoreCatalogId(con, storeCatalogId);

                categoryIdsByCatalogId.putAll(categoryIdsByStoreCatalogId);

                log.info("doPostProcessing()=> Getting exist categories for acct catalogs.");
                for (Entry<String, Integer> e4 : accountIdsByAccountRef.entrySet()) {
                    Integer thisAcctId = e4.getValue();
                    Integer thisAcctCatalogId = acctCatalogs.get(thisAcctId);
                    Map<Integer, Set<Integer>> categoryIdsByAcctCatalogId = InboundXpedxLoaderHelper.getCategoryIdsByStoreCatalogId(con, thisAcctCatalogId);
                    categoryIdsByCatalogId.putAll(categoryIdsByAcctCatalogId);
                }

                Map<Integer, Map<Integer, ContractItemData>> contractItemsByContract = InboundXpedxLoaderHelper.getContractItemsByContractId(con, contractIds, processData.getErrors());
                Map<Integer, Map<Integer, InventoryItemsData>> inventoryItemsByAccount = InboundXpedxLoaderHelper.getInventoryItemsByAccountId(con, accountIdsByAccountRef.values(), processData.getErrors());
                Map<Integer, Map<Integer, ShoppingControlData>> shoppingControlsByAccount = InboundXpedxLoaderHelper.getShoppingControlsByAccountId(con, accountIdsByAccountRef.values(), processData.getErrors());

                log.info("doPostProcessing()=> Collecting errors " + processData.getErrors().size());
                for (String error : processData.getErrors()) {
                    appendErrorMsgs(error);
                }

                String errorMessage = getFormatedErrorMsgs();
                if (Utility.isSet(errorMessage)) {
                    throw new Exception(errorMessage);
                }

                log.info("doPostProcessing()=> Not found errors, go next!");

                log.info("doPostProcessing()=> Synchronizing manufacturers");
                synchronizeManufacturers(storeId, processData.getAllManufacturers(), manufIdByName);

                log.info("doPostProcessing()=> storeCatalogId: " + storeCatalogId);
                if (storeCatalogId < 1) {
                    log.info("doPostProcessing()=> Synchronizing store catalog.");
                    storeCatalogId = syncronizeStoreCatalog(con, storeId);
                }

                Integer accountCatalogId = acctCatalogs.get(es.getValue());
                log.info("doPostProcessing()=> accountCatalogId: " + accountCatalogId);
                if (accountCatalogId == null || accountCatalogId < 1) {
                    log.info("doPostProcessing()=> Synchronizing account catalog.");
                    accountCatalogId = syncronizeAccountCatalog(con, es.getValue());
                    acctCatalogs.put(es.getValue(), accountCatalogId);
                }

                log.info("doPostProcessing()=> Synchronizing shopping catalogs.");
                synchronizeCatalogs(con,
                        catalogIdsByAccountCatalogRef,
                        catalogsForCreate,
                        accountIdsByAccountRef,
                        storesByAccount,
                        catalogDistIds,
                        processData.getErrors());


                log.info("doPostProcessing()=> Synchronizing categories with store catalog");
                //synch store catalog categories
                synchronizeCategories(con,
                        processData.getCategoriesTreeByAcct(),
                        processData.getCategoriesByAcct(),
                        storeCatalogId,
                        accountCatalogId,
                        categoryIdsByCatalogId);


                log.info("doPostProcessing()=> Synchronizing items.");
                synchronizeItems(con,
                        itemDataByDistItemRef,
                        storeId,
                        distIdByName,
                        manufIdByName,
                        itemContentMap,
                        processData.getInboundImageDataMap());

                log.info("doPostProcessing()=> Synchronizing multi products.");
                synchronizeMultiProducts(con, processData.getErrors());

                log.info("doPostProcessing()=> Synchronizing contracts.");
                for (Object aParsedDataList : parsedDataList) {
                    XpedxCatalogItemView parsedDataItem = (XpedxCatalogItemView) aParsedDataList;
                    synchronizeContract(con, parsedDataItem, catalogIdsByAccountCatalogRef, contractsByCatalog);
                }

                log.info("doPostProcessing()=> Getting exist catalog structures.");
                Map<Integer, Map<Integer, CatalogStructureData>> catalogStructuresByCatalog =
                        InboundXpedxLoaderHelper.getCatalogStructuresByCatalog(con,
                                catalogIdsByAccountCatalogRef.values(),
                                processData.getErrors());

                log.info("doPostProcessing()=> Synchronizing catalog structures.");
                synchronizeCatalogStructure(con,
                        catalogIdsByAccountCatalogRef,
                        itemDataByDistItemRef,
                        catalogStructuresByCatalog,
                        processData.getCostCenterNamesByAcctRef());

                Map<Integer, Map<Integer, Set<Integer>>> itemIdsByCategoryIdByCatalogId = InboundXpedxLoaderHelper.getItemIdsByCategoryIdByCatalogId(con, catalogIdsByAccountCatalogRef.values());

                log.info("doPostProcessing()=> Synchronizing catalog categories.");
                List<ItemAssocObj> itemAssocList = synchronizeCatalogCategories(con,
                        catalogIdsByAccountCatalogRef,
                        categoryIdsByCatalogId,
                        itemIdsByCategoryIdByCatalogId,
                        itemDataByDistItemRef,
                        processData.getCostCenterNamesByAcctRef());

                if (itemAssocList != null && itemAssocList.size() > 0) {
                    for (ItemAssocObj thisItemAssoc : itemAssocList) {
                        int item1_id = thisItemAssoc.getItem1Id();
                        int item2_id = thisItemAssoc.getItem2Id();
                        int cat_id = thisItemAssoc.getCatalogId();
                        createItemAssoc(con, item1_id, item2_id, cat_id, RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
                    }
                }

                log.info("doPostProcessing()=> Getting exist shopping catalogs.");
                Set<Integer> existsAccountShoppingCatalogIds = InboundXpedxLoaderHelper.getShoppingCatalogIdsFor(con, accountCatalogId);
                HashMap<Integer, Set<Integer>> existsShoppingCatalogIdsByItem = InboundXpedxLoaderHelper.getShoppingCatalogIdsByItem(con, existsAccountShoppingCatalogIds);

                log.info("doPostProcessing()=> Synchronizing contract items.");
                List<String> ciErrors = synchronizeContractItem(con,
                        catalogIdsByAccountCatalogRef,
                        itemDataByDistItemRef,
                        contractsByCatalog,
                        contractItemsByContract);
                log.info("doPostProcessing()=> ciErrors:" + ciErrors);

                log.info("doPostProcessing()=> Synchronizing inventory items.");
                List<String> iErrors = synchronizeInventoryItem(con,
                        catalogIdsByAccountCatalogRef,
                        existsShoppingCatalogIdsByItem,
                        itemDataByDistItemRef,
                        accountIdsByAccountRef,
                        inventoryItemsByAccount);
                log.info("doPostProcessing()=> iErrors:" + iErrors);

                log.info("doPostProcessing()=> Synchronizing shopping control items.");
                List<String> scErrors = synchronizeShoppingControlItem(con,
                        catalogIdsByAccountCatalogRef,
                        existsShoppingCatalogIdsByItem,
                        itemDataByDistItemRef,
                        accountIdsByAccountRef,
                        shoppingControlsByAccount);
                log.info("doPostProcessing()=> scErrors:" + scErrors);

                //   for (String error : ae) {
                //        appendErrorMsgs(error);
                ///     }

                String errorMess = getFormatedErrorMsgs();
                if (Utility.isSet(errorMess)) {
                    throw new Exception(errorMess);
                }

                log.info("doPostProcessing()=> Trying removing categories from catalogs.");

                removeUnusedShoppingCategories(con,
                        processData,
                        storeCatalogId,
                        accountCatalogId,
                        categoryIdsByCatalogId);

                synchronizeAccountCatalogStructure(con,
                        accountCatalogId,
                        itemDataByDistItemRef,
                        existsAccountShoppingCatalogIds,
                        new HashSet<Integer>(catalogIdsByAccountCatalogRef.values()),
                        processData.getCostCenterNamesByAcctRef(),
                        processData.getErrors());

                removeUnusedAccountCategories(con,
                        processData,
                        storeCatalogId,
                        accountCatalogId,
                        categoryIdsByCatalogId,
                        existsAccountShoppingCatalogIds);

                synchronizeAccountItemAssoc(con,
                        accountCatalogId,
                        existsAccountShoppingCatalogIds,
                        new HashSet<Integer>(catalogIdsByAccountCatalogRef.values())
                );

                synchronizeStoreCatalogStructure(con,
                        storeCatalogId,
                        accountCatalogId,
                        itemDataByDistItemRef);

                synchronizeStoreItemAssoc(con,
                        storeCatalogId,
                        accountCatalogId);

                Catalog catEjb = APIAccess.getAPIAccess().getCatalogAPI();
                //reset the cost centers
                log.info("doPostProcessing()=> reset the cost centers.");
                for (Entry<String, Integer> e4 : accountIdsByAccountRef.entrySet()) {
                    Integer thisAcctId = e4.getValue();
                    Integer thisAcctCatalogId = acctCatalogs.get(thisAcctId);
                    catEjb.resetCostCenters(thisAcctCatalogId, ADD_BY);
                }

            }
            log.info("doPostProcessing()=> Finished");
        } finally {
            closeConnection(con);
        }

        log.info("doPostProcessing()=> END.");

    }

    private void checkImage(Set<String> pImageUrls, Map<String, byte[]> pInboundImageDataMap, List<String> pErrors) {
        for (String url : pImageUrls) {
            if (pInboundImageDataMap.get(url) == null || pInboundImageDataMap.get(url).length == 0) {
                pErrors.add("Can't get image from URL '" + url + "'.");
            }
        }
    }

    private void synchronizeManufacturers(int pStoreId,
                                          Set<String> pInboundManufs,
                                          Map<String, Integer> pManufIdByName) throws Exception {

        Set<String> toCreate = new HashSet<String>();
        for (String inboundManuf : pInboundManufs) {
            if (pManufIdByName.get(inboundManuf) == null) {
                toCreate.add(inboundManuf);
            }
        }

        log.info("synchronizeManufacturers()=> toCreate: "+toCreate);

        Manufacturer manufEjb = APIAccess.getAPIAccess().getManufacturerAPI();

        for (String inboundManuf : toCreate) {

            BusEntityData beData = new BusEntityData();
            beData.setShortDesc(inboundManuf);
            beData.setBusEntityStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
            beData.setBusEntityTypeCd(RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);
            beData.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
            beData.setLocaleCd(RefCodeNames.LOCALE_CD.EN_US);
            beData.setAddBy(ADD_BY);
            beData.setModBy(ADD_BY);

            ManufacturerData manuf = new ManufacturerData(
                    beData,
                    pStoreId,
                    null,
                    null,
                    null,
                    null,
                    null, // pBusinessClass,
                    null, // PropertyData pWomanOwnedBusiness,
                    null, // PropertyData pMinorityOwnedBusiness,
                    null, // PropertyData pJWOD,
                    null, // PropertyData pOtherBusiness,
                    null, // PropertyDataVector pSpecializations,
                    null, // PropertyDataVector pMiscProperties,
                    null // PropertyData pOtherNames
            );

            manuf = manufEjb.addManufacturer(manuf);

            pManufIdByName.put(inboundManuf, manuf.getBusEntity().getBusEntityId());
        }
    }

    private void synchronizeStoreItemAssoc(Connection pCon,
                                           Integer pStoreCatalogId,
                                           Integer pAccountCatalogId) throws Exception {

        List<ItemAssocData> storeItemAssocForCreate = InboundXpedxLoaderHelper.getItemAssocForCreate(pCon,
                pStoreCatalogId,
                new HashSet(Utility.toIdVector(pAccountCatalogId)));


        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pStoreCatalogId);
        dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

        ItemAssocDataVector existProdAssoc = ItemAssocDataAccess.select(pCon, dbc);

        HashMap<Integer, List<ItemAssocData>> existProdAssocMap = new HashMap<Integer, List<ItemAssocData>>();
        for (Object oProdAssoc : existProdAssoc) {
            ItemAssocData prodAssoc = ((ItemAssocData) oProdAssoc);
            List<ItemAssocData> assocList = existProdAssocMap.get(prodAssoc.getItem1Id());
            if (assocList == null) {
                assocList = new ArrayList<ItemAssocData>();
                existProdAssocMap.put(prodAssoc.getItem1Id(), assocList);
            }
            assocList.add(prodAssoc);
        }

        for (ItemAssocData itemAssoc : storeItemAssocForCreate) {

            if (RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY.equals(itemAssoc.getItemAssocCd())) {

                List<ItemAssocData> existAssocList = existProdAssocMap.get(itemAssoc.getItem1Id());

                if (existAssocList == null || existAssocList.isEmpty()) {

                    itemAssoc.setAddBy(ADD_BY);
                    itemAssoc.setModBy(ADD_BY);

                    itemAssoc = ItemAssocDataAccess.insert(pCon, itemAssoc);

                    existAssocList = new ArrayList<ItemAssocData>();
                    existAssocList.add(itemAssoc);

                    existProdAssocMap.put(itemAssoc.getItem1Id(), existAssocList);

                } else {

                    ItemAssocData currentAssoc = existAssocList.get(0);
                    currentAssoc.setItem2Id(itemAssoc.getItem2Id());
                    itemAssoc.setModBy(ADD_BY);

                    ItemAssocDataAccess.update(pCon, currentAssoc);

                    int count = existAssocList.size();
                    while (count > 1) {
                        ItemAssocData o = existAssocList.remove(--count);
                        ItemAssocDataAccess.remove(pCon, o.getItemAssocId());
                    }

                }

            }

        }

        for (List<ItemAssocData> vals : existProdAssocMap.values()) {
            if (vals.size() > 1) {
                int count = vals.size();
                while (count > 1) {
                    ItemAssocData o = vals.remove(--count);
                    ItemAssocDataAccess.remove(pCon, o.getItemAssocId());
                }
            }
        }

    }

    private void synchronizeAccountItemAssoc(Connection pCon,
                                             Integer pAccountCatalogId,
                                             Set<Integer> pExistsAccountShoppingCatalogIds,
                                             HashSet<Integer> pProcessCatalogs) throws Exception {

        List<ItemAssocData> accItemAssocForCreate = InboundXpedxLoaderHelper.getItemAssocForCreate(pCon,
                pAccountCatalogId,
                pProcessCatalogs);

        for (ItemAssocData itemAssoc : accItemAssocForCreate) {

            if (RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY.equals(itemAssoc.getItemAssocCd())) {
                itemAssoc.setAddBy(ADD_BY);
                itemAssoc.setModBy(ADD_BY);
                itemAssoc = ItemAssocDataAccess.insert(pCon, itemAssoc);
            }

        }

        List<ItemAssocObj> accItemAssocForDelete = InboundXpedxLoaderHelper.getAssocItemIdsForDelete(pCon,
                pAccountCatalogId,
                pExistsAccountShoppingCatalogIds);

        for (ItemAssocObj obj : accItemAssocForDelete) {

            DBCriteria cr = new DBCriteria();
            cr.addEqualTo(ItemAssocDataAccess.CATALOG_ID, obj.getCatalogId());
            cr.addEqualTo(ItemAssocDataAccess.ITEM1_ID, obj.getItem1Id());
            cr.addEqualTo(ItemAssocDataAccess.ITEM2_ID, obj.getItem2Id());

            ItemAssocDataAccess.remove(pCon, cr);

        }

    }

    private void synchronizeAccountCatalogStructure(Connection pCon,
                                                    Integer pAccountCatalogId,
                                                    Map<ComparableList, ItemData> pItemDataByDistItemRef,
                                                    Set<Integer> pExistsAccountShoppingCatalogIds,
                                                    Set<Integer> pProcessCatalogIds,
                                                    Map<ComparableList, String> pCostCenterNamesByAcctRef,
                                                    List<String> pErrors) throws Exception {

        List<CatalogStructureData> catalogStructuresForCreate
                = InboundXpedxLoaderHelper.getCatalogStructuresForCreate(pCon,
                pAccountCatalogId,
                pProcessCatalogIds);

        for (CatalogStructureData cataloStructure : catalogStructuresForCreate) {

            if (RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY.equals(cataloStructure.getCatalogStructureCd())
                    || RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT.equals(cataloStructure.getCatalogStructureCd())) {

                cataloStructure.setAddBy(ADD_BY);
                cataloStructure.setModBy(ADD_BY);

                cataloStructure = CatalogStructureDataAccess.insert(pCon, cataloStructure);

            }

        }

        HashSet<Integer> itemsForDelete = InboundXpedxLoaderHelper.getAccountCatalogItemIdsForDelete(pCon,
                pAccountCatalogId,
                pExistsAccountShoppingCatalogIds);

        if (!itemsForDelete.isEmpty()) {

            DBCriteria cr = new DBCriteria();
            cr.addOneOf(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                    Utility.getAsList(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY,
                            RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT));
            cr.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pAccountCatalogId);
            cr.addOneOf(CatalogStructureDataAccess.ITEM_ID, new ArrayList<Integer>(itemsForDelete));

            log.info("synchronizeAccountCatalogStructure()=> Delete catalog structure from account catalog.");
            log.info("Delete items  " + itemsForDelete + " from catalog:" + pAccountCatalogId);

            CatalogStructureDataAccess.remove(pCon, cr);

            cr = new DBCriteria();
            cr.addOneOf(ItemAssocDataAccess.ITEM_ASSOC_CD, Utility.getAsList(RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY));
            cr.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pAccountCatalogId);
            cr.addOneOf(ItemAssocDataAccess.ITEM1_ID, new ArrayList<Integer>(itemsForDelete));

            ItemAssocDataAccess.remove(pCon, cr);

        }

        synchronizeAccountCatalogStructureData(pCon,
                pAccountCatalogId,
                pItemDataByDistItemRef,
                pCostCenterNamesByAcctRef,
                pErrors);


    }

    private void synchronizeAccountCatalogStructureData(Connection pCon,
                                                        Integer pAccountCatalogId,
                                                        Map<ComparableList, ItemData> pItemDataByDistItemRef,
                                                        Map<ComparableList, String> pCostCenterNamesByAcctRef,
                                                        List<String> pErrors) throws Exception {


        {
            Map<Integer, CatalogStructureData> itemsToUpdate = new HashMap<Integer, CatalogStructureData>();

            Map<Integer, Map<Integer, CatalogStructureData>> accCatProductStructuresByCatalog =
                    InboundXpedxLoaderHelper.getCatalogStructuresByCatalog(pCon,
                            Utility.toIdVector(pAccountCatalogId),
                            Utility.getAsList(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT),
                            pErrors);

            final int count = parsedDataList.size();

            for (int i = 0; i < count; i++) {

                XpedxCatalogItemView parsedDataItem = parsedDataList.get(i);

                String distReference = parsedDataItem.getDistributor().trim();
                String itemReference = parsedDataItem.getDistSKU().trim();

                ItemData itemData = pItemDataByDistItemRef.get(ComparableList.createValue(distReference, itemReference));
                if (itemData != null) {

                    Map<Integer, CatalogStructureData> catalogStructure = accCatProductStructuresByCatalog.get(pAccountCatalogId);

                    CatalogStructureData cs = catalogStructure.get(itemData.getItemId());

                    Map<String, List<CostCenterData>> costCenters = existCostCentersByAcctRef.get(parsedDataItem.getAccountNumber());

                    Integer costCenterId = null;
                    if (costCenters != null) {
                        String costCenter = pCostCenterNamesByAcctRef.get(
                                ComparableList.createValue(
                                        parsedDataItem.getAccountNumber(),
                                        parsedDataItem.getCategory()));
                        if (costCenter != null) {
                            costCenterId = costCenters.get(costCenter).get(0).getCostCenterId();
                        }
                    }


                    boolean wasChanged = false;

                    if (Utility.isSet(parsedDataItem.getSpecialPermission())) {
                        if (!parsedDataItem.getSpecialPermission().equalsIgnoreCase(cs.getSpecialPermission())) {
                            cs.setSpecialPermission(String.valueOf(Utility.isTrue(parsedDataItem.getSpecialPermission())));
                            wasChanged = true;
                        }
                    }

                    if (Utility.intNN(costCenterId) != cs.getCostCenterId()) {
                        cs.setCostCenterId(Utility.intNN(costCenterId));
                        wasChanged = true;
                    }

                    if (wasChanged) {
                        itemsToUpdate.put(itemData.getItemId(), cs);
                    }
                }
            }

            log.info("synchronizeAccountCatalogStructureData()=> Update " + itemsToUpdate.size() + " items from account catalog " + pAccountCatalogId);

            for (CatalogStructureData item : itemsToUpdate.values()) {
                item.setModBy(ADD_BY);
                CatalogStructureDataAccess.update(pCon, item);
            }

        }


        {
            HashMap<Integer, CatalogStructureData> itemsToUpdate = new HashMap<Integer, CatalogStructureData>();

            Map<Integer, Map<Integer, CatalogStructureData>> accCatCategoryStructuresByCatalog =
                    InboundXpedxLoaderHelper.getCatalogStructuresByCatalog(pCon,
                            Utility.toIdVector(pAccountCatalogId),
                            Utility.getAsList(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY),
                            pErrors);

            int count = parsedDataList.size();
            for (int i = 0; i < count; i++) {

                XpedxCatalogItemView parsedDataItem = parsedDataList.get(i);

                Map<String, Integer> acctCategories = acctExistCategories.get(parsedDataItem.getAccountNumber());
                if (acctCategories != null) {

                    Map<Integer, CatalogStructureData> catalogStructure = accCatCategoryStructuresByCatalog.get(pAccountCatalogId);

                    Integer categId;

                    if (Utility.isSet(parsedDataItem.getCategory())) {

                        categId = acctCategories.get(parsedDataItem.getCategory());
                        if (categId != null) {
                            CatalogStructureData cs = catalogStructure.get(categId);
                            if (cs != null) {
                                Map<String, List<CostCenterData>> costCenters = existCostCentersByAcctRef.get(parsedDataItem.getAccountNumber());
                                Integer costCenterId = null;
                                if (costCenters != null) {
                                    String costCenter = pCostCenterNamesByAcctRef.get(
                                            ComparableList.createValue(
                                                    parsedDataItem.getAccountNumber(),
                                                    parsedDataItem.getCategory()));
                                    if (costCenter != null) {
                                        costCenterId = costCenters.get(costCenter).get(0).getCostCenterId();
                                    }
                                }

                                boolean wasChanged = false;
                                if (Utility.intNN(costCenterId) != cs.getCostCenterId()) {
                                    cs.setCostCenterId(Utility.intNN(costCenterId));
                                    wasChanged = true;
                                }

                                if (wasChanged) {
                                    itemsToUpdate.put(categId, cs);
                                }
                            }
                        }

                        if (Utility.isSet(parsedDataItem.getSubCat1())) {

                            categId = acctCategories.get(parsedDataItem.getSubCat1());
                            if (categId != null) {
                                CatalogStructureData cs = catalogStructure.get(categId);
                                if (cs != null) {
                                    Map<String, List<CostCenterData>> costCenters = existCostCentersByAcctRef.get(parsedDataItem.getAccountNumber());
                                    Integer costCenterId = null;
                                    if (costCenters != null) {
                                        String costCenter = pCostCenterNamesByAcctRef.get(
                                                ComparableList.createValue(
                                                        parsedDataItem.getAccountNumber(),
                                                        parsedDataItem.getCategory()));
                                        if (costCenter != null) {
                                            costCenterId = costCenters.get(costCenter).get(0).getCostCenterId();
                                        }
                                    }

                                    boolean wasChanged = false;
                                    if (Utility.intNN(costCenterId) != cs.getCostCenterId()) {
                                        cs.setCostCenterId(Utility.intNN(costCenterId));
                                        wasChanged = true;
                                    }

                                    if (wasChanged) {
                                        itemsToUpdate.put(categId, cs);
                                    }
                                }
                            }

                            if (Utility.isSet(parsedDataItem.getSubCat2())) {

                                categId = acctCategories.get(parsedDataItem.getSubCat2());
                                if (categId != null) {
                                    CatalogStructureData cs = catalogStructure.get(categId);
                                    if (cs != null) {
                                        Map<String, List<CostCenterData>> costCenters = existCostCentersByAcctRef.get(parsedDataItem.getAccountNumber());
                                        Integer costCenterId = null;
                                        if (costCenters != null) {
                                            String costCenter = pCostCenterNamesByAcctRef.get(
                                                    ComparableList.createValue(
                                                            parsedDataItem.getAccountNumber(),
                                                            parsedDataItem.getCategory()));
                                            if (costCenter != null) {
                                                costCenterId = costCenters.get(costCenter).get(0).getCostCenterId();
                                            }
                                        }

                                        boolean wasChanged = false;
                                        if (Utility.intNN(costCenterId) != cs.getCostCenterId()) {
                                            cs.setCostCenterId(Utility.intNN(costCenterId));
                                            wasChanged = true;
                                        }

                                        if (wasChanged) {
                                            itemsToUpdate.put(categId, cs);
                                        }
                                    }
                                }

                                if (Utility.isSet(parsedDataItem.getSubCat3())) {

                                    categId = acctCategories.get(parsedDataItem.getSubCat3());
                                    if (categId != null) {
                                        CatalogStructureData cs = catalogStructure.get(categId);
                                        if (cs != null) {
                                            Map<String, List<CostCenterData>> costCenters = existCostCentersByAcctRef.get(parsedDataItem.getAccountNumber());
                                            Integer costCenterId = null;
                                            if (costCenters != null) {
                                                String costCenter = pCostCenterNamesByAcctRef.get(
                                                        ComparableList.createValue(
                                                                parsedDataItem.getAccountNumber(),
                                                                parsedDataItem.getCategory()));
                                                if (costCenter != null) {
                                                    costCenterId = costCenters.get(costCenter).get(0).getCostCenterId();
                                                }
                                            }

                                            boolean wasChanged = false;
                                            if (Utility.intNN(costCenterId) != cs.getCostCenterId()) {
                                                cs.setCostCenterId(Utility.intNN(costCenterId));
                                                wasChanged = true;
                                            }

                                            if (wasChanged) {
                                                itemsToUpdate.put(categId, cs);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }


            log.info("synchronizeAccountCatalogStructureData()=> Update " + itemsToUpdate.size() + " categories from account catalog " + pAccountCatalogId);

            for (CatalogStructureData item : itemsToUpdate.values()) {
                item.setModBy(ADD_BY);
                CatalogStructureDataAccess.update(pCon, item);
            }
        }

    }

    private void synchronizeStoreCatalogStructure(Connection pCon,
                                                  Integer pStoreCatalogId,
                                                  Integer pAccountCatalogId,
                                                  Map<ComparableList, ItemData> pItemDataByDistItemRef) throws Exception {

        List<CatalogStructureData> catalogStructuresForCreate
                = InboundXpedxLoaderHelper.getCatalogStructuresForCreate(pCon,
                pStoreCatalogId,
                new HashSet(Utility.toIdVector(pAccountCatalogId)));

        Collection<ItemData> items = pItemDataByDistItemRef.values();
        HashMap itemMap = Utility.toMap(new ArrayList<ItemData>(items));

        for (CatalogStructureData catalogStructure : catalogStructuresForCreate) {

            catalogStructure.setAddBy(ADD_BY);
            catalogStructure.setModBy(ADD_BY);
            if (RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT.equals(catalogStructure.getCatalogStructureCd())) {
                ItemData item = (ItemData) itemMap.get(catalogStructure.getItemId());
                if (item != null && item.getSkuNum() > 0) {
                    catalogStructure.setCustomerSkuNum(String.valueOf(item.getSkuNum()));
                }
            }
            catalogStructure.setCostCenterId(0);
            catalogStructure.setSpecialPermission(null);
            CatalogStructureDataAccess.insert(pCon, catalogStructure);
        }

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pStoreCatalogId);
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
        dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, new ArrayList<Integer>(itemMap.keySet()));

        CatalogStructureDataVector toUpdateSku = CatalogStructureDataAccess.select(pCon, dbc);
        for (Object oCatalogStructure : toUpdateSku) {

            CatalogStructureData catalogStructure = (CatalogStructureData) oCatalogStructure;

            ItemData item = (ItemData) itemMap.get(catalogStructure.getItemId());

            boolean wasChanged = false;

            if (!Utility.isSet(catalogStructure.getCustomerSkuNum()) && item != null && item.getSkuNum() > 0) {
                catalogStructure.setCustomerSkuNum(String.valueOf(item.getSkuNum()));
                wasChanged = true;
            }

            if (catalogStructure.getCostCenterId() != 0) {
                catalogStructure.setCostCenterId(0);
                wasChanged = true;
            }

            if (Utility.isSet(catalogStructure.getSpecialPermission())) {
                catalogStructure.setSpecialPermission(null);
                wasChanged = true;
            }

            if (wasChanged) {
                catalogStructure.setModBy(ADD_BY);
                CatalogStructureDataAccess.update(pCon, catalogStructure);
            }

        }

    }

    private void removeUnusedShoppingCategories(Connection pCon,
                                                ProcessData processData,
                                                int pStoreCatalogId,
                                                int pAccountCatalogId,
                                                Map<Integer, Set<Integer>> categoryIdsByCatalogId) throws Exception {

        Set<Integer> keys = categoryIdsByCatalogId.keySet();

        for (Integer key1 : keys) {

            if (key1 != pStoreCatalogId && key1 != pAccountCatalogId) {

                log.info("removeUnusedShoppingCategories()=> CATALOG: " + key1);

                Set<Integer> updatedCats = new TreeSet<Integer>();
                Set<Integer> allCats = categoryIdsByCatalogId.get(key1);//all categories of this catalog

                for (Integer thisCat : allCats) {
                    //find in acctExistCategories
                    for (Entry<String, Map<String, Integer>> en : acctExistCategories.entrySet()) {

                        Map<String, Integer> val = en.getValue();

                        for (Entry<String, Integer> en2 : val.entrySet()) {

                            Integer val2 = en2.getValue();
                            String keyVal = en2.getKey();//category name
                            Set<String> categories = processData.getCategoriesByAcct().get(en.getKey());
                            if (categories.contains(keyVal)) {
                                updatedCats.add(val2);
                            }

                        }
                    }

                }

                allCats.removeAll(updatedCats);
            }

        }

        for (Entry<Integer, Set<Integer>> e : categoryIdsByCatalogId.entrySet()) {
            Set<Integer> buffer = new HashSet<Integer>();
            if (e.getKey() != pStoreCatalogId && e.getKey() != pAccountCatalogId) {
                buffer.add(e.getKey());
                if (e.getValue() != null && e.getValue().size() > 0) {

                    InboundXpedxLoaderHelper.tryToRemoveCategoryFromShoppingCatalogs(pCon,
                            pStoreCatalogId,
                            buffer,
                            e.getValue());

                }
            }
        }
    }

    private void removeUnusedAccountCategories(Connection pCon,
                                               ProcessData processData,
                                               int pStoreCatalogId,
                                               int pAccountCatalogId,
                                               Map<Integer, Set<Integer>> categoryIdsByCatalogId,
                                               Set<Integer> existShoppingCatalogIds) throws Exception {

        Set<Integer> keys = categoryIdsByCatalogId.keySet();

        for (Integer key1 : keys) {


            if (key1 != pStoreCatalogId && key1 == pAccountCatalogId) {

                log.info("removeUnusedAccountCategories()=> CATALOG: " + key1);

                Set<Integer> updatedCats = new TreeSet<Integer>();
                Set<Integer> allCats = categoryIdsByCatalogId.get(key1);//all categories of this catalog

                for (Integer thisCat : allCats) {
                    //find in acctExistCategories
                    for (Entry<String, Map<String, Integer>> en : acctExistCategories.entrySet()) {

                        Map<String, Integer> val = en.getValue();
                        for (Entry<String, Integer> en2 : val.entrySet()) {

                            Integer val2 = en2.getValue();
                            String keyVal = en2.getKey();//category name
                            // log.info("doPostProcessing()=> category name: "+ keyVal);
                            Set<String> categories = processData.getCategoriesByAcct().get(en.getKey());
                            if (categories.contains(keyVal)) {
                                // log.info("doPostProcessing()=> add to updated cats "+ val2);
                                updatedCats.add(val2);
                            }

                        }
                    }

                }
                log.info("removeUnusedAccountCategories()=> updated cats " + updatedCats);
                allCats.removeAll(updatedCats);
                log.info("removeUnusedAccountCategories()=> cats to remove " + allCats);
            }

        }

        for (Entry<Integer, Set<Integer>> e : categoryIdsByCatalogId.entrySet()) {
            Set<Integer> buffer = new HashSet<Integer>();
            if (e.getKey() == pAccountCatalogId) {
                buffer.add(e.getKey());
                if (e.getValue() != null && e.getValue().size() > 0) {
                    InboundXpedxLoaderHelper.tryToRemoveCategoryFromAccountCatalogs(pCon,
                            pStoreCatalogId,
                            buffer,
                            e.getValue(),
                            existShoppingCatalogIds);
                }
            }
        }
    }

    private void synchronizeMultiProducts(Connection pCon, List<String> pErrors) throws Exception {

        final int count = parsedDataList.size();

        for (int i = 0; i < count; i++) {

            XpedxCatalogItemView parsedDataItem = parsedDataList.get(i);
            if (Utility.isSet(parsedDataItem.getMultiProductID()) && Utility.isSet(parsedDataItem.getMultiProductName())) {

                HashMap<Integer, ItemData> multiProductMap = multiProdsByAcctRef.get(parsedDataItem.getAccountNumber());

                if (multiProductMap != null) {
                    ItemData multiProduct = multiProductMap.get(new Integer(parsedDataItem.getMultiProductID()));
                    if (multiProduct != null) {
                        if (!parsedDataItem.getMultiProductName().equals(multiProduct.getShortDesc())) {
                            multiProduct.setShortDesc(parsedDataItem.getMultiProductName());
                            multiProduct.setModBy(ADD_BY);
                            ItemDataAccess.update(pCon, multiProduct);
                            multiProductMap.put(multiProduct.getItemId(), multiProduct);
                        }
                    }
                }
            }
        }

    }

    private List<String> synchronizeInventoryItem(Connection pCon,
                                                  Map<ComparableList, Integer> pCatalogIdsByAccountCatalogRef,
                                                  Map<Integer, Set<Integer>> pExistsShoppingCatalogIdsByItem,
                                                  Map<ComparableList, ItemData> pItemDataByDistItemRef,
                                                  Map<String, Integer> accountIdsByAccountRef,
                                                  Map<Integer, Map<Integer, InventoryItemsData>> pInventoryItemsByAccount) throws Exception {


        Map<Integer, Set<Integer>> inventoryItemForDelete = new TreeMap<Integer, Set<Integer>>();
        Set<String> existMapping = new HashSet<String>();

        log.info("Synchronizing inventory items, total items:" + parsedDataList.size());
        int prevStep = -1;
        final int count = parsedDataList.size();
        List<String> errorMess = new ArrayList<String>();

        for (int i = 0; i < count; i++) {
            int step = 100 * i / count;
            if (step > prevStep) {
                log.debug("processing step ... " + step + "%");
                prevStep = step;
            }

            XpedxCatalogItemView parsedDataItem = parsedDataList.get(i);

            ComparableList distItemRef = ComparableList.createValue(parsedDataItem.getDistributor(), parsedDataItem.getDistSKU());
            if (Utility.isTrue(parsedDataItem.getInventoryItems())) {

                int accountId = accountIdsByAccountRef.get(parsedDataItem.getAccountNumber());

                Map<Integer, InventoryItemsData> invItems = pInventoryItemsByAccount.get(accountId);
                if (invItems == null) {
                    invItems = new HashMap<Integer, InventoryItemsData>();
                    pInventoryItemsByAccount.put(accountId, invItems);
                }

                ItemData itemData = pItemDataByDistItemRef.get(distItemRef);
                if (itemData != null && itemData.getItemId() > 0) {

                    InventoryItemsData invItemData = invItems.get(itemData.getItemId());
                    if (invItemData == null) {

                        invItemData = InventoryItemsData.createValue();
                        invItemData.setBusEntityId(accountId);
                        invItemData.setItemId(itemData.getItemId());
                        invItemData.setEnableAutoOrder(Utility.isTrue(parsedDataItem.getAutoOrderItem()) ? "Y" : "N");
                        invItemData.setStatusCd(RefCodeNames.SIMPLE_STATUS_CD.ACTIVE);
                        invItemData.setAddBy(ADD_BY);
                        invItemData.setModBy(ADD_BY);
                        invItemData = InventoryItemsDataAccess.insert(pCon, invItemData);

                    } else {

                        boolean wasChanged = false;

                        String autoOrderItem = Utility.isTrue(parsedDataItem.getAutoOrderItem()) ? "Y" : "N";
                        if (!autoOrderItem.equals(invItemData.getEnableAutoOrder())) {
                            invItemData.setEnableAutoOrder(autoOrderItem);
                            wasChanged = true;
                        }

                        if (wasChanged) {
                            invItemData.setModBy(ADD_BY);
                            InventoryItemsDataAccess.update(pCon, invItemData);
                        }
                    }

                    invItems.put(itemData.getItemId(), invItemData);
                    existMapping.add(accountId + "_" + itemData.getItemId());

                } else {

                    log.warn("Item is not synchronized with :" + distItemRef);
                    errorMess.add("Item is not synchronized with " + distItemRef);

                }

            }
        }

        log.info("Preparing for delete...");
        for (Map<Integer, InventoryItemsData> ii : pInventoryItemsByAccount.values()) {
            for (InventoryItemsData i : ii.values()) {
                if (!existMapping.contains(i.getBusEntityId() + "_" + i.getItemId())) {
                    Set<Integer> catalogIds = new HashSet<Integer>(pCatalogIdsByAccountCatalogRef.values());
                    Set<Integer> inCatalogIds = pExistsShoppingCatalogIdsByItem.get(i.getItemId());
                    if (inCatalogIds==null || catalogIds.containsAll(inCatalogIds)) {
                        Set<Integer> itemIds = inventoryItemForDelete.get(i.getBusEntityId());
                        if (itemIds == null) {
                            itemIds = new TreeSet<Integer>();
                            inventoryItemForDelete.put(i.getBusEntityId(), itemIds);
                        }
                        itemIds.add(i.getItemId());
                    }
                }
            }
        }

        log.info("Delete inventory items from " + inventoryItemForDelete.keySet().size() + " accounts.");
        for (Map.Entry<Integer, Set<Integer>> e : inventoryItemForDelete.entrySet()) {

            log.info("Delete items  " + e.getValue() + " account:" + e.getKey());

            DBCriteria cr = new DBCriteria();
            cr.addEqualTo(InventoryItemsDataAccess.BUS_ENTITY_ID, e.getKey());
            cr.addOneOf(InventoryItemsDataAccess.ITEM_ID, new ArrayList<Integer>(e.getValue()));

            InventoryItemsDataAccess.remove(pCon, cr);
        }

        return errorMess;


    }

    private List<String> synchronizeShoppingControlItem(Connection pCon,
                                                        Map<ComparableList, Integer> pCatalogIdsByAccountCatalogRef,
                                                        Map<Integer, Set<Integer>> pExistsShoppingCatalogIdsByItem,
                                                        Map<ComparableList, ItemData> pItemDataByDistItemRef,
                                                        Map<String, Integer> accountIdsByAccountRef,
                                                        Map<Integer, Map<Integer, ShoppingControlData>> pShoppingControlItemsByAccount) throws Exception {


        Map<Integer, Set<Integer>> shoppingControlItemsForDelete = new TreeMap<Integer, Set<Integer>>();
        Set<String> existMapping = new HashSet<String>();

        log.info("Synchronizing shopping control items, total items:" + parsedDataList.size());
        int prevStep = -1;
        final int count = parsedDataList.size();
        List<String> errorMess = new ArrayList<String>();

        for (int i = 0; i < count; i++) {
            int step = 100 * i / count;
            if (step > prevStep) {
                log.debug("processing step ... " + step + "%");
                prevStep = step;
            }

            XpedxCatalogItemView parsedDataItem = parsedDataList.get(i);

            ComparableList distItemRef = ComparableList.createValue(parsedDataItem.getDistributor(), parsedDataItem.getDistSKU());
            int accountId = accountIdsByAccountRef.get(parsedDataItem.getAccountNumber());

            Map<Integer, ShoppingControlData> shoppingControlItems = pShoppingControlItemsByAccount.get(accountId);

            if (shoppingControlItems == null) {
                shoppingControlItems = new HashMap<Integer, ShoppingControlData>();
                pShoppingControlItemsByAccount.put(accountId, shoppingControlItems);
            }

            ItemData itemData = pItemDataByDistItemRef.get(distItemRef);

            if (itemData != null && itemData.getItemId() > 0) {

                ShoppingControlData shoppingControlData = shoppingControlItems.get(itemData.getItemId());

                if (shoppingControlData == null) {

                    shoppingControlData = ShoppingControlData.createValue();
                    shoppingControlData.setAccountId(accountId);
                    shoppingControlData.setItemId(itemData.getItemId());
                    shoppingControlData.setControlStatusCd(RefCodeNames.SIMPLE_STATUS_CD.ACTIVE);

                    if (Utility.isSet(parsedDataItem.getShoppingMaxQTY())) {
                        shoppingControlData.setRestrictionDays(Utility.parseInt(parsedDataItem.getShoppingRestrictionDays()));
                        shoppingControlData.setMaxOrderQty(Utility.parseInt(parsedDataItem.getShoppingMaxQTY()));
                    } else {
                        shoppingControlData.setMaxOrderQty(-1);
                        if (!Utility.isSet(parsedDataItem.getShoppingRestrictionDays())) {
                            shoppingControlData.setRestrictionDays(1);
                        } else {
                            shoppingControlData.setRestrictionDays(Utility.parseInt(parsedDataItem.getShoppingRestrictionDays()));
                        }
                    }

                    shoppingControlData.setAddBy(ADD_BY);
                    shoppingControlData.setModBy(ADD_BY);

                    shoppingControlData = ShoppingControlDataAccess.insert(pCon, shoppingControlData);

                } else {

                    boolean wasChanged = false;

                    if (Utility.isSet(parsedDataItem.getShoppingMaxQTY())) {

                        int restrictionDays = Utility.parseInt(parsedDataItem.getShoppingRestrictionDays());
                        int maxOrderQty = Utility.parseInt(parsedDataItem.getShoppingMaxQTY());

                        if (shoppingControlData.getRestrictionDays() != restrictionDays) {
                            shoppingControlData.setRestrictionDays(restrictionDays);
                            wasChanged = true;
                        }

                        if (shoppingControlData.getMaxOrderQty() != maxOrderQty) {
                            shoppingControlData.setMaxOrderQty(maxOrderQty);
                            wasChanged = true;
                        }
                    } else {

                        if (shoppingControlData.getMaxOrderQty() > -1) {
                            shoppingControlData.setMaxOrderQty(-1);
                            wasChanged = true;
                        }

                        int shoppingRestrictionDays;
                        if (!Utility.isSet(parsedDataItem.getShoppingRestrictionDays())) {
                            shoppingRestrictionDays = 1;
                        } else {
                            shoppingRestrictionDays = Utility.parseInt(parsedDataItem.getShoppingMaxQTY());
                        }

                        if (shoppingControlData.getRestrictionDays() != shoppingRestrictionDays) {
                            shoppingControlData.setRestrictionDays(shoppingRestrictionDays);
                            wasChanged = true;
                        }
                    }

                    if (wasChanged) {
                        shoppingControlData.setModBy(ADD_BY);
                        ShoppingControlDataAccess.update(pCon, shoppingControlData);
                    }

                }

                shoppingControlItems.put(itemData.getItemId(), shoppingControlData);
                existMapping.add(accountId + "_" + itemData.getItemId());

            } else {

                log.warn("Item is not synchronized with: " + distItemRef);
                errorMess.add("Item is not synchronized with: " + distItemRef);

            }

        }


        log.info("Preparing for delete...");

        for (Map<Integer, ShoppingControlData> ii : pShoppingControlItemsByAccount.values()) {
            for (ShoppingControlData i : ii.values()) {
                if (!existMapping.contains(i.getAccountId() + "_" + i.getItemId())) {
                    Set<Integer> catalogIds = new HashSet<Integer>(pCatalogIdsByAccountCatalogRef.values());
                    Set<Integer> inCatalogIds = pExistsShoppingCatalogIdsByItem.get(i.getItemId());
                    if (inCatalogIds == null || catalogIds.containsAll(inCatalogIds)) {
                        Set<Integer> itemIds = shoppingControlItemsForDelete.get(i.getAccountId());
                        if (itemIds == null) {
                            itemIds = new TreeSet<Integer>();
                            shoppingControlItemsForDelete.put(i.getAccountId(), itemIds);
                        }
                        itemIds.add(i.getItemId());
                    }
                }
            }
        }

        log.info("Delete shopping controls items from " + shoppingControlItemsForDelete.keySet().size() + " accounts.");
        for (Map.Entry<Integer, Set<Integer>> e : shoppingControlItemsForDelete.entrySet()) {

            log.info("Delete items  " + e.getValue() + " account: " + e.getKey());

            DBCriteria cr = new DBCriteria();
            cr.addEqualTo(ShoppingControlDataAccess.ACCOUNT_ID, e.getKey());
            cr.addOneOf(ShoppingControlDataAccess.ITEM_ID, new ArrayList<Integer>(e.getValue()));

            ShoppingControlDataAccess.remove(pCon, cr);
        }

        return errorMess;

    }


    private void synchronizeItems(Connection pCon,
                                  Map<ComparableList, ItemData> pItemDataByDistItemRef,
                                  int pStoreId,
                                  Map<String, Integer> pDistributorIdsByName,
                                  Map<String, Integer> pManufacturerIdsByName,
                                  Map<Integer, ContentData> pItemContentMap,
                                  Map<String, byte[]> pInboundImageDataMap) throws Exception {

        for (Object aParsedDataList : parsedDataList) {

            XpedxCatalogItemView parsedDataItem = (XpedxCatalogItemView) aParsedDataList;

            ComparableList distItemRef = ComparableList.createValue(parsedDataItem.getDistributor(), parsedDataItem.getDistSKU());

            int distributorId = pDistributorIdsByName.get(parsedDataItem.getDistributor());
            int manufacturerId = 0;
            if (Utility.isSet(parsedDataItem.getManufacturer())) {
                manufacturerId = Utility.intNN(pManufacturerIdsByName.get(parsedDataItem.getManufacturer()));
            }

            ItemData itemData = pItemDataByDistItemRef.get(distItemRef);
            if (itemData == null) {

                itemData = InboundXpedxLoaderHelper.createItemData(parsedDataItem, ADD_BY, ADD_DATE);
                itemData = ItemDataAccess.insert(pCon, itemData);

                itemData.setSkuNum(itemData.getItemId() + 10000);

                ItemDataAccess.update(pCon, itemData);

                byte[] imageData = null;
                if (Utility.isSet(parsedDataItem.getImage())) {
                    imageData = pInboundImageDataMap.get(parsedDataItem.getImage());
                }

                ContentData content;
                if (imageData != null) {
                    content = InboundXpedxLoaderHelper.createImageContent(itemData.getItemId(),
                            parsedDataItem.getImage(),
                            imageData,
                            ADD_BY);
                    content = ContentDataAccess.insert(pCon, content);
                    pItemContentMap.put(itemData.getItemId(), content);
                }

                ItemMetaDataVector itemMetas = InboundXpedxLoaderHelper.createItemMetas(itemData, parsedDataItem, ADD_BY);
                for (Object oItemMeta : itemMetas) {
                    ItemMetaData meta = (ItemMetaData) oItemMeta;
                    if (Utility.isSet(meta.getValue())) {
                        if (ProductData.IMAGE.equals(meta.getNameValue())) {
                            if (imageData != null) {
                                ItemMetaDataAccess.insert(pCon, meta);
                            }
                        } else {
                            ItemMetaDataAccess.insert(pCon, meta);
                        }
                    }
                }

                ItemMappingData itemMapping = InboundXpedxLoaderHelper.createItemMapping(itemData.getItemId(),
                        pStoreId,
                        RefCodeNames.ITEM_MAPPING_CD.ITEM_STORE,
                        String.valueOf(itemData.getSkuNum()),
                        parsedDataItem.getPack(),
                        parsedDataItem.getUOM(),
                        ADD_BY);
                ItemMappingDataAccess.insert(pCon, itemMapping);

                itemMapping = InboundXpedxLoaderHelper.createItemMapping(itemData.getItemId(),
                        distributorId,
                        RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR,
                        parsedDataItem.getDistSKU(),
                        Utility.isSet(parsedDataItem.getDistPack()) ? parsedDataItem.getDistPack() : parsedDataItem.getPack(),
                        Utility.isSet(parsedDataItem.getDistUOM()) ? parsedDataItem.getDistUOM() : parsedDataItem.getUOM(),
                        ADD_BY);

                ItemMappingDataAccess.insert(pCon, itemMapping);

                if (manufacturerId > 0) {
                    itemMapping = InboundXpedxLoaderHelper.createItemMapping(itemData.getItemId(),
                            manufacturerId,
                            RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER,
                            parsedDataItem.getMfgSKU(),
                            null,
                            null,
                            ADD_BY);
                    ItemMappingDataAccess.insert(pCon, itemMapping);
                }

                pItemDataByDistItemRef.put(distItemRef, itemData);

            } else {

                if (!Utility.strNN(parsedDataItem.getShortDescription()).equals(itemData.getShortDesc())
                        || !Utility.strNN(parsedDataItem.getLongDescription()).equals(itemData.getLongDesc())) {

                    boolean wasChanged = false;

                    if (Utility.isSet(parsedDataItem.getShortDescription())) {
                        wasChanged = true;
                        itemData.setShortDesc(parsedDataItem.getShortDescription());
                    }

                    if (Utility.isSet(parsedDataItem.getLongDescription())) {
                        wasChanged = true;
                        itemData.setLongDesc(parsedDataItem.getLongDescription());
                    }

                    if (wasChanged) {
                        ItemDataAccess.update(pCon, itemData);
                    }
                }

                byte[] imageData = null;
                if (Utility.isSet(parsedDataItem.getImage())) {
                    imageData = pInboundImageDataMap.get(parsedDataItem.getImage());
                }

                ContentData imageContent = pItemContentMap.get(itemData.getItemId());
                if (imageData != null && imageContent == null) {
                    imageContent = InboundXpedxLoaderHelper.createImageContent(itemData.getItemId(),
                            parsedDataItem.getImage(),
                            imageData,
                            ADD_BY);
                    imageContent = ContentDataAccess.insert(pCon, imageContent);
                    pItemContentMap.put(itemData.getItemId(), imageContent);
                } else if (imageContent != null && imageData != null) {
                    if (!Utility.byteArrayEqual(imageContent.getBinaryData(), imageData)) {
                        imageContent.setBinaryData(imageData);
                        imageContent.setModBy(ADD_BY);
                        ContentDataAccess.update(pCon, imageContent);
                    }
                }

                DBCriteria dbCriteria = new DBCriteria();
                dbCriteria.addEqualTo(ItemMetaDataAccess.ITEM_ID, itemData.getItemId());

                ItemMetaDataVector itemMetas = ItemMetaDataAccess.select(pCon, dbCriteria);

                ItemMetaData meta;

                if (Utility.isSet(parsedDataItem.getHazmat())) {
                    String value = YES.equalsIgnoreCase(parsedDataItem.getHazmat()) ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
                    meta = InboundXpedxLoaderHelper.getItemMeta(itemMetas, ProductData.HAZMAT);
                    if (meta == null) {
                        meta = InboundXpedxLoaderHelper.createItemMeta(itemData.getItemId(),
                                ProductData.HAZMAT,
                                value,
                                ADD_BY);
                        itemMetas.add(ItemMetaDataAccess.insert(pCon, meta));
                    } else {
                        if (!value.equals(meta.getValue())) {
                            meta.setValue(value);
                            meta.setModBy(ADD_BY);
                            ItemMetaDataAccess.update(pCon, meta);
                        }
                    }
                } else {
                    String value = Boolean.FALSE.toString();
                    meta = InboundXpedxLoaderHelper.getItemMeta(itemMetas, ProductData.HAZMAT);
                    if (meta == null) {
                        meta = InboundXpedxLoaderHelper.createItemMeta(itemData.getItemId(),
                                ProductData.HAZMAT,
                                value,
                                ADD_BY);
                        itemMetas.add(ItemMetaDataAccess.insert(pCon, meta));
                    } else {
                        if (!value.equals(meta.getValue())) {
                            meta.setValue(value);
                            meta.setModBy(ADD_BY);
                            ItemMetaDataAccess.update(pCon, meta);
                        }
                    }
                }

                if (Utility.isSet(parsedDataItem.getImage())) {
                    meta = InboundXpedxLoaderHelper.getItemMeta(itemMetas, ProductData.IMAGE);
                    if (meta == null) {
                        String imagePath = InboundXpedxLoaderHelper.createImagePathForMapping(itemData.getItemId(), parsedDataItem.getImage());
                        meta = InboundXpedxLoaderHelper.createItemMeta(itemData.getItemId(),
                                ProductData.IMAGE,
                                imagePath,
                                ADD_BY);
                        itemMetas.add(ItemMetaDataAccess.insert(pCon, meta));
                    } else {
                        String imagePath = InboundXpedxLoaderHelper.createImagePathForMapping(itemData.getItemId(), parsedDataItem.getImage());
                        if (!Utility.strNN(imagePath).equals(meta.getValue())) {
                            meta.setValue(imagePath);
                            meta.setModBy(ADD_BY);
                            ItemMetaDataAccess.update(pCon, meta);
                        }
                    }
                }

                if (Utility.isSet(parsedDataItem.getColor())) {
                    meta = InboundXpedxLoaderHelper.getItemMeta(itemMetas, ProductData.COLOR);
                    if (meta == null) {
                        meta = InboundXpedxLoaderHelper.createItemMeta(itemData.getItemId(),
                                ProductData.COLOR,
                                parsedDataItem.getColor(),
                                ADD_BY);
                        itemMetas.add(ItemMetaDataAccess.insert(pCon, meta));
                    } else {
                        if (!Utility.strNN(parsedDataItem.getColor()).equals(meta.getValue())) {
                            meta.setValue(parsedDataItem.getColor());
                            meta.setModBy(ADD_BY);
                            ItemMetaDataAccess.update(pCon, meta);
                        }
                    }
                }

                if (Utility.isSet(parsedDataItem.getPack())) {
                    meta = InboundXpedxLoaderHelper.getItemMeta(itemMetas, ProductData.PACK);
                    if (meta == null) {
                        meta = InboundXpedxLoaderHelper.createItemMeta(itemData.getItemId(),
                                ProductData.PACK,
                                parsedDataItem.getPack(),
                                ADD_BY);
                        itemMetas.add(ItemMetaDataAccess.insert(pCon, meta));
                    } else {
                        if (!Utility.strNN(parsedDataItem.getPack()).equals(meta.getValue())) {
                            meta.setValue(parsedDataItem.getPack());
                            meta.setModBy(ADD_BY);
                            ItemMetaDataAccess.update(pCon, meta);
                        }
                    }
                }

                if (Utility.isSet(parsedDataItem.getShippingCubicSize())) {
                    meta = InboundXpedxLoaderHelper.getItemMeta(itemMetas, ProductData.CUBE_SIZE);
                    if (meta == null) {
                        meta = InboundXpedxLoaderHelper.createItemMeta(itemData.getItemId(),
                                ProductData.CUBE_SIZE,
                                parsedDataItem.getShippingCubicSize(),
                                ADD_BY);
                        itemMetas.add(ItemMetaDataAccess.insert(pCon, meta));
                    } else {
                        if (!Utility.strNN(parsedDataItem.getShippingCubicSize()).equals(meta.getValue())) {
                            meta.setValue(parsedDataItem.getShippingCubicSize());
                            meta.setModBy(ADD_BY);
                            ItemMetaDataAccess.update(pCon, meta);
                        }
                    }
                }

                if (Utility.isSet(parsedDataItem.getShippingWeight())) {
                    meta = InboundXpedxLoaderHelper.getItemMeta(itemMetas, ProductData.SHIP_WEIGHT);
                    if (meta == null) {
                        meta = InboundXpedxLoaderHelper.createItemMeta(itemData.getItemId(),
                                ProductData.SHIP_WEIGHT,
                                parsedDataItem.getShippingWeight(),
                                ADD_BY);
                        itemMetas.add(ItemMetaDataAccess.insert(pCon, meta));
                    } else {
                        if (!Utility.strNN(parsedDataItem.getShippingWeight()).equals(meta.getValue())) {
                            meta.setValue(parsedDataItem.getShippingWeight());
                            meta.setModBy(ADD_BY);
                            ItemMetaDataAccess.update(pCon, meta);
                        }
                    }
                }

                if (Utility.isSet(parsedDataItem.getUOM())) {
                    meta = InboundXpedxLoaderHelper.getItemMeta(itemMetas, ProductData.UOM);
                    if (meta == null) {
                        meta = InboundXpedxLoaderHelper.createItemMeta(itemData.getItemId(),
                                ProductData.UOM,
                                parsedDataItem.getUOM(),
                                ADD_BY);
                        itemMetas.add(ItemMetaDataAccess.insert(pCon, meta));
                    } else {
                        if (!Utility.strNN(parsedDataItem.getUOM()).equals(meta.getValue())) {
                            meta.setValue(parsedDataItem.getUOM());
                            meta.setModBy(ADD_BY);
                            ItemMetaDataAccess.update(pCon, meta);
                        }
                    }
                }

                if (Utility.isSet(parsedDataItem.getListPrice())) {
                    meta = InboundXpedxLoaderHelper.getItemMeta(itemMetas, ProductData.LIST_PRICE);
                    if (meta == null) {
                        meta = InboundXpedxLoaderHelper.createItemMeta(itemData.getItemId(),
                                ProductData.LIST_PRICE,
                                parsedDataItem.getListPrice(),
                                ADD_BY);
                        itemMetas.add(ItemMetaDataAccess.insert(pCon, meta));
                    } else {
                        if (!Utility.strNN(parsedDataItem.getListPrice()).equals(meta.getValue())) {
                            meta.setValue(parsedDataItem.getListPrice());
                            meta.setModBy(ADD_BY);
                            ItemMetaDataAccess.update(pCon, meta);
                        }
                    }
                }

                if (Utility.isSet(parsedDataItem.getProductUPC())) {
                    meta = InboundXpedxLoaderHelper.getItemMeta(itemMetas, ProductData.UPC_NUM);
                    if (meta == null) {
                        meta = InboundXpedxLoaderHelper.createItemMeta(itemData.getItemId(),
                                ProductData.UPC_NUM,
                                parsedDataItem.getProductUPC(),
                                ADD_BY);
                        itemMetas.add(ItemMetaDataAccess.insert(pCon, meta));
                    } else {
                        if (!Utility.strNN(parsedDataItem.getProductUPC()).equals(meta.getValue())) {
                            meta.setValue(parsedDataItem.getProductUPC());
                            meta.setModBy(ADD_BY);
                            ItemMetaDataAccess.update(pCon, meta);
                        }
                    }

                }

                if (Utility.isSet(parsedDataItem.getPackUPC())) {
                    meta = InboundXpedxLoaderHelper.getItemMeta(itemMetas, ProductData.PKG_UPC_NUM);
                    if (meta == null) {
                        meta = InboundXpedxLoaderHelper.createItemMeta(itemData.getItemId(),
                                ProductData.PKG_UPC_NUM,
                                parsedDataItem.getPackUPC(),
                                ADD_BY);
                        itemMetas.add(ItemMetaDataAccess.insert(pCon, meta));
                    } else {
                        if (!Utility.strNN(parsedDataItem.getPackUPC()).equals(meta.getValue())) {
                            meta.setValue(parsedDataItem.getPackUPC());
                            meta.setModBy(ADD_BY);
                            ItemMetaDataAccess.update(pCon, meta);
                        }
                    }
                }

                if (Utility.isSet(parsedDataItem.getSize())) {
                    meta = InboundXpedxLoaderHelper.getItemMeta(itemMetas, ProductData.SIZE);
                    if (meta == null) {
                        meta = InboundXpedxLoaderHelper.createItemMeta(itemData.getItemId(),
                                ProductData.SIZE,
                                parsedDataItem.getSize(),
                                ADD_BY);
                        itemMetas.add(ItemMetaDataAccess.insert(pCon, meta));
                    } else {
                        if (!Utility.strNN(parsedDataItem.getSize()).equals(meta.getValue())) {
                            meta.setValue(parsedDataItem.getSize());
                            meta.setModBy(ADD_BY);
                            ItemMetaDataAccess.update(pCon, meta);
                        }
                    }

                }

                if (Utility.isSet(parsedDataItem.getWeightUnit())) {
                    meta = InboundXpedxLoaderHelper.getItemMeta(itemMetas, ProductData.WEIGHT_UNIT);
                    if (meta == null) {
                        meta = InboundXpedxLoaderHelper.createItemMeta(itemData.getItemId(),
                                ProductData.WEIGHT_UNIT,
                                parsedDataItem.getWeightUnit(),
                                ADD_BY);
                        itemMetas.add(ItemMetaDataAccess.insert(pCon, meta));
                    } else {
                        if (!Utility.strNN(parsedDataItem.getWeightUnit()).equals(meta.getValue())) {
                            meta.setValue(parsedDataItem.getWeightUnit());
                            meta.setModBy(ADD_BY);
                            ItemMetaDataAccess.update(pCon, meta);
                        }
                    }
                } else {
                    meta = InboundXpedxLoaderHelper.getItemMeta(itemMetas, ProductData.WEIGHT_UNIT);
                    if (meta == null) {
                        meta = InboundXpedxLoaderHelper.createItemMeta(itemData.getItemId(),
                                ProductData.WEIGHT_UNIT,
                                RefCodeNames.WEIGHT_UNIT.OUNCE,
                                ADD_BY);
                        itemMetas.add(ItemMetaDataAccess.insert(pCon, meta));
                    } else {
                        if (!RefCodeNames.WEIGHT_UNIT.OUNCE.equals(meta.getValue())) {
                            meta.setValue(RefCodeNames.WEIGHT_UNIT.OUNCE);
                            meta.setModBy(ADD_BY);
                            ItemMetaDataAccess.update(pCon, meta);
                        }
                    }
                }

                dbCriteria = new DBCriteria();
                dbCriteria.addEqualTo(ItemMappingDataAccess.ITEM_ID, itemData.getItemId());
                dbCriteria.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID, distributorId);
                dbCriteria.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);

                ItemMappingDataVector itemDistrMappings = ItemMappingDataAccess.select(pCon, dbCriteria);

                ItemMappingData itemDistrMapping;
                if (!itemDistrMappings.isEmpty()) {

                    itemDistrMapping = (ItemMappingData) itemDistrMappings.get(0);

                    boolean wasChanged = false;

                    if (!Utility.strNN(parsedDataItem.getDistSKU()).equals(itemDistrMapping.getItemNum())) {
                        itemDistrMapping.setItemNum(parsedDataItem.getDistSKU());
                        wasChanged = true;
                    }

                    if (Utility.isSet(parsedDataItem.getDistUOM())) {
                        if (!parsedDataItem.getDistUOM().equals(itemDistrMapping.getItemUom())) {
                            itemDistrMapping.setItemUom(parsedDataItem.getDistUOM());
                            wasChanged = true;
                        }
                    } else if (!Utility.strNN(itemDistrMapping.getItemUom()).equals(parsedDataItem.getUOM())) {
                        itemDistrMapping.setItemUom(parsedDataItem.getUOM());
                        wasChanged = true;
                    }

                    if (Utility.isSet(parsedDataItem.getDistPack())) {
                        if (!parsedDataItem.getDistPack().equals(itemDistrMapping.getItemPack())) {
                            itemDistrMapping.setItemPack(parsedDataItem.getDistPack());
                            wasChanged = true;
                        }
                    } else if (!Utility.strNN(itemDistrMapping.getItemPack()).equals(parsedDataItem.getPack())) {
                        itemDistrMapping.setItemPack(parsedDataItem.getPack());
                        wasChanged = true;
                    }

                    if (wasChanged) {
                        itemDistrMapping.setModBy(ADD_BY);
                        ItemMappingDataAccess.update(pCon, itemDistrMapping);
                    }

                } else {
                    itemDistrMapping = InboundXpedxLoaderHelper.createItemMapping(itemData.getItemId(),
                            distributorId,
                            RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR,
                            parsedDataItem.getDistSKU(),
                            parsedDataItem.getDistPack(),
                            parsedDataItem.getDistUOM(),
                            ADD_BY);
                    itemDistrMapping = ItemMappingDataAccess.insert(pCon, itemDistrMapping);
                    itemDistrMappings.add(itemDistrMapping);
                }

                if (manufacturerId > 0) {

                    dbCriteria = new DBCriteria();
                    dbCriteria.addEqualTo(ItemMappingDataAccess.ITEM_ID, itemData.getItemId());
                    dbCriteria.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);

                    ItemMappingDataVector itemManufMappings = ItemMappingDataAccess.select(pCon, dbCriteria);

                    ItemMappingData itemManufMapping;
                    if (!itemManufMappings.isEmpty()) {

                        itemManufMapping = (ItemMappingData) itemManufMappings.remove(0);

                        boolean wasChanged = false;
                        if (!Utility.strNN(parsedDataItem.getMfgSKU()).equals(itemManufMapping.getItemNum())) {
                            itemManufMapping.setItemNum(parsedDataItem.getMfgSKU());
                            wasChanged = true;
                        }

                        if (manufacturerId != itemManufMapping.getBusEntityId()) {
                            itemManufMapping.setBusEntityId(manufacturerId);
                            wasChanged = true;
                        }

                        if (wasChanged) {
                            itemManufMapping.setModBy(ADD_BY);
                            ItemMappingDataAccess.update(pCon, itemManufMapping);
                        }

                        if (itemManufMappings.size() > 0) {
                            dbCriteria = new DBCriteria();
                            dbCriteria.addOneOf(ItemMappingDataAccess.ITEM_MAPPING_ID, Utility.toIdVector(itemManufMappings));
                            ItemMappingDataAccess.remove(pCon, dbCriteria);
                        }

                    } else {
                        itemManufMapping = InboundXpedxLoaderHelper.createItemMapping(itemData.getItemId(),
                                manufacturerId,
                                RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER,
                                parsedDataItem.getMfgSKU(),
                                null,
                                null,
                                ADD_BY);
                        itemManufMapping = ItemMappingDataAccess.insert(pCon, itemManufMapping);
                        itemManufMappings.add(itemManufMapping);
                    }

                }
            }
        }
    }

    private void checkInputValues(List<String> pErrors) {

        List<String> errors = new ArrayList<String>();
        List<String> duplicateErrors = new ArrayList<String>();
        Set<ComparableList> existCatalogStructure = new HashSet<ComparableList>();

        for (int i = 0; i < parsedDataList.size(); i++) {

            XpedxCatalogItemView parsedDataItem = parsedDataList.get(i);

            String lineNum = String.valueOf(i + 1);

            if (!Utility.isSet(parsedDataItem.getAccountNumber())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Account Number' requires information"));
            }

            if (!Utility.isSet(parsedDataItem.getCatalogID())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Catalog ID' requires information"));

            }

            if (!Utility.isSet(parsedDataItem.getLocale())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Locale' requires information"));
            }

            if (!Utility.isSet(parsedDataItem.getDistSKU())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Dist SKU' requires information"));
            }

            if (!Utility.isSet(parsedDataItem.getMfgSKU())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Mfg SKU' requires information"));
            }

            if (!Utility.isSet(parsedDataItem.getManufacturer())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Manufacturer' requires information"));
            }

            if (!Utility.isSet(parsedDataItem.getDistributor())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Distributor' requires information"));
            }

            if (!Utility.isSet(parsedDataItem.getPack())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Pack' requires information"));
            }

            if (!Utility.isSet(parsedDataItem.getUOM())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'UOM' requires information"));
            }

            if (!Utility.isSet(parsedDataItem.getCost())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Cost' requires information"));
            }

            if (!Utility.isSet(parsedDataItem.getPrice())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Price' requires information"));
            }

            if (!Utility.isSet(parsedDataItem.getCategory())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Category' requires information"));
            }

            if (!Utility.isSet(parsedDataItem.getShortDescription())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Short Description' requires information"));
            }

            if (!Utility.isSet(parsedDataItem.getLongDescription())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Long Description' requires information"));
            }

            if (!Utility.isSet(parsedDataItem.getSize())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Size' requires information"));
            }

            if (Utility.isSet(parsedDataItem.getShoppingMaxQTY())
                    && !Utility.isSet(parsedDataItem.getShoppingRestrictionDays())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Shopping Restriction Days' requires information"));
            }

            if (!Utility.isSet(parsedDataItem.getInventoryItems())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Inventory Items' requires information"));
            }

            if (!Utility.isSet(parsedDataItem.getSpecialPermission())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Admin Only Item' requires information"));
            }

            if (!Utility.isSet(parsedDataItem.getAutoOrderItem())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Auto Order Item' requires information"));
            }

            if (Utility.isSet(parsedDataItem.getPrice())) {
                try {
                    new BigDecimal(parsedDataItem.getPrice());
                } catch (Exception e) {
                    errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Incorrect 'Price' field: " + parsedDataItem.getPrice()));
                }
            }

            if (Utility.isSet(parsedDataItem.getCost())) {
                try {
                    new BigDecimal(parsedDataItem.getCost());
                } catch (Exception e) {
                    errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Incorrect 'Cost' field: " + parsedDataItem.getCost()));
                }
            }

            if (Utility.isSet(parsedDataItem.getListPrice())) {
                try {
                    new BigDecimal(parsedDataItem.getListPrice());
                } catch (Exception e) {
                    errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Incorrect 'List Price' field: " + parsedDataItem.getListPrice()));
                }
            }

            if (Utility.isSet(parsedDataItem.getFreightTableID())) {
                try {
                    Integer.parseInt(parsedDataItem.getFreightTableID());
                } catch (Exception e) {
                    errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Incorrect 'Freight Table ID' field: " + parsedDataItem.getFreightTableID()));
                }
            }

            if (Utility.isSet(parsedDataItem.getShoppingMaxQTY())) {
                try {
                    Integer.parseInt(parsedDataItem.getShoppingMaxQTY());
                } catch (Exception e) {
                    errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Incorrect 'Shopping Max QTY' field: " + parsedDataItem.getShoppingMaxQTY()));
                }
            }

            if (Utility.isSet(parsedDataItem.getShoppingRestrictionDays())) {
                try {
                    Integer.parseInt(parsedDataItem.getShoppingRestrictionDays());
                } catch (Exception e) {
                    errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Incorrect 'Shopping Restriction Days' field: " + parsedDataItem.getShoppingRestrictionDays()));
                }
            }

            if (Utility.isSet(parsedDataItem.getMultiProductID())) {
                try {
                    Integer.parseInt(parsedDataItem.getMultiProductID());
                } catch (Exception e) {
                    errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Incorrect 'Multi Product ID' field: " + parsedDataItem.getMultiProductID()));
                }
            }

            if (Utility.isSet(parsedDataItem.getInventoryItems())) {
                try {
                    Boolean.parseBoolean(parsedDataItem.getInventoryItems());
                } catch (Exception e) {
                    errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Incorrect 'Inventory Items' field: " + parsedDataItem.getInventoryItems()));
                }
            }

            if (Utility.isSet(parsedDataItem.getSpecialPermission())) {
                try {
                    Boolean.parseBoolean(parsedDataItem.getSpecialPermission());
                } catch (Exception e) {
                    errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Incorrect 'Admin Only Item' field: " + parsedDataItem.getSpecialPermission()));
                }
            }

            if (Utility.isSet(parsedDataItem.getAutoOrderItem())) {
                try {
                    Boolean.parseBoolean(parsedDataItem.getAutoOrderItem());
                } catch (Exception e) {
                    errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Incorrect 'Auto Order Item' field: " + parsedDataItem.getAutoOrderItem()));
                }
            }

            if (Utility.isSet(parsedDataItem.getHazmat())) {
                if (!parsedDataItem.getHazmat().equalsIgnoreCase(YES)
                        && !parsedDataItem.getHazmat().equalsIgnoreCase(NO)) {
                    errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Incorrect 'Hazmat' field: " + parsedDataItem.getHazmat()));
                }
            }

            if(Utility.isSet(parsedDataItem.getImage())){
                if (!urlPattern.matcher(parsedDataItem.getImage()).matches()) {
                    errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Incorrect 'Image URL' format. Image URL: " + parsedDataItem.getImage()));
                }
            }

            String accountRef = parsedDataItem.getAccountNumber();
            String catalogRef = parsedDataItem.getCatalogID();
            String distRef = parsedDataItem.getDistributor();
            String itemRef = parsedDataItem.getDistSKU();

            if (Utility.isSet(accountRef)
                    && Utility.isSet(catalogRef)
                    && Utility.isSet(distRef)
                    && Utility.isSet(itemRef)) {

                ComparableList key = ComparableList.createValue(accountRef, catalogRef, distRef, itemRef);
                if (existCatalogStructure.contains(key)) {
                    duplicateErrors.add(MessageFormat.format(LOADER_FMT, lineNum, "Catalog Structure " + key + " already exist"));
                } else {
                    existCatalogStructure.add(key);
                }

            }

        }

        pErrors.addAll(errors);
        pErrors.addAll(duplicateErrors);

    }

    private void checkLocales(Map<ComparableList, List<XpedxCatalogItemView>> pGroup, Map<String, Integer> pLocalePriceDecimals) {

        ArrayList<String> errors = new ArrayList<String>();

        for (Map.Entry<ComparableList, List<XpedxCatalogItemView>> e : pGroup.entrySet()) {

            String locale = null;
            for (XpedxCatalogItemView parsedDataItem : e.getValue()) {
                if (locale == null) {
                    locale = parsedDataItem.getLocale();
                } else if (!(locale.equals(parsedDataItem.getLocale()))) {
                    errors.add("Wrong 'LocaleCD' in catalog group '" + e.getKey() + "'");
                    break;
                }
            }
        }

        if (errors.isEmpty()) {

            for (Map.Entry<ComparableList, List<XpedxCatalogItemView>> e : pGroup.entrySet()) {

                for (XpedxCatalogItemView parsedDataItem : e.getValue()) {

                    Integer decimalPlaces = pLocalePriceDecimals.get(parsedDataItem.getLocale());

                    if (decimalPlaces != null && Utility.isSet(parsedDataItem.getCost())) {
                        BigDecimal value = new BigDecimal(parsedDataItem.getCost());
                        if (value.scale() > decimalPlaces) {
                            errors.add("The Cost '" + parsedDataItem.getCost() + "' for item "
                                    + ComparableList.createValue(parsedDataItem.getAccountNumber(),
                                    parsedDataItem.getCatalogID(),
                                    parsedDataItem.getDistSKU())
                                    + " has too many decimal points. It can only have "
                                    + decimalPlaces + " decimal points for '"
                                    + parsedDataItem.getLocale() + "' currency");
                        }
                    }

                    if (decimalPlaces != null && Utility.isSet(parsedDataItem.getPrice())) {
                        BigDecimal value = new BigDecimal(parsedDataItem.getPrice());
                        if (value.scale() > decimalPlaces) {
                            errors.add("The Price '" + parsedDataItem.getPrice() + "' for item "
                                    + ComparableList.createValue(parsedDataItem.getAccountNumber(),
                                    parsedDataItem.getCatalogID(),
                                    parsedDataItem.getDistSKU())
                                    + " has too many decimal points. It can only have "
                                    + decimalPlaces
                                    + " decimal points for '"
                                    + parsedDataItem.getLocale() + "' currency");

                        }
                    }

                    if (decimalPlaces != null && Utility.isSet(parsedDataItem.getListPrice())) {
                        BigDecimal value = new BigDecimal(parsedDataItem.getListPrice());
                        if (value.scale() > decimalPlaces) {
                            errors.add("The List Price '" + parsedDataItem.getListPrice() + "' for item "
                                    + ComparableList.createValue(parsedDataItem.getAccountNumber(),
                                    parsedDataItem.getCatalogID(),
                                    parsedDataItem.getDistSKU())
                                    + " has too many decimal points. It can only have "
                                    + decimalPlaces + " decimal points for '"
                                    + parsedDataItem.getLocale() + "' currency");
                        }
                    }

                }
            }
        }

        if (!errors.isEmpty()) {
            for (String err : errors) {
                this.appendErrorMsgs(err);
            }
        }

    }

    private void checkExistCatalogs(Map<String, Integer> pAccountIdsByAccountRef,Map<ComparableList, Integer> pCatalogIdsByAccountCatalogRef,
                                    Set<ComparableList> pCatalogsForCreate,
                                    Map<String, Set<Integer>> pCatalogIdsByNameForStore,
                                    List<String> pErrors) {

        for (ComparableList key : pCatalogIdsByAccountCatalogRef.keySet()) {
            pCatalogsForCreate.remove(key);
        }

        Iterator<ComparableList> it = pCatalogsForCreate.iterator();
        while (it.hasNext()) {
            ComparableList catRef = (it.next());
            String accountRef = (String) catRef.get(0);
            if (!pAccountIdsByAccountRef.containsKey(accountRef)) {
                it.remove();
            }
        }


        for (Map.Entry<String, Set<Integer>> entry : pCatalogIdsByNameForStore.entrySet()) {
            if (entry.getValue().size() > 1) {
                pErrors.add("Found duplicated catalog with name: " + entry.getKey() + ", CatalogIDs: " + entry.getValue());
            }
        }

        log.info("Need create next catalogs:" + pCatalogsForCreate);

        for (ComparableList catalogRef : pCatalogsForCreate) {
            if (pCatalogIdsByNameForStore.containsKey(catalogRef.get(1))) {
                pErrors.add("Catalog with name '" + catalogRef.get(1) + "' already exists. CatalogIDs: " + pCatalogIdsByNameForStore.get(catalogRef.get(1)));
            }
        }

    }

    private Set<String> getCategoriesToCreate(String pAccountReferenceNumber, Set<String> pAcctCategories) {

        HashSet<String> toCreate = new HashSet<String>();

        Map<String, Integer> val1 = acctExistCategories.get(pAccountReferenceNumber);   //[cat_name, cat_id]

        for (String name : pAcctCategories) {
            if (!val1.keySet().contains(name)) {
                toCreate.add(name);
            }
        }

        return toCreate;
    }


    private void checkExistMultiProducts(String pAccountReference, Map<String, Set<String>> pMultiProducts, List<String> pErrors) {
        HashMap<Integer, ItemData> accountMultiProds = multiProdsByAcctRef.get(pAccountReference);
        Set<String> multiProductsToProces = pMultiProducts.get(pAccountReference);
        if (multiProductsToProces != null) {
            for (String multiProductID : multiProductsToProces) {
                if (accountMultiProds != null && !accountMultiProds.containsKey(new Integer(multiProductID))) {
                    log.info("Could not find multi product id: '"+multiProductID+" in the account ref: '" + pAccountReference );
                    pErrors.add("Could not find multi product id: '"+multiProductID+" in the account ref: '" + pAccountReference );
                }
            }
        }
    }

    private void checkExistCostCenters(String pAccountReference, Map<ComparableList, String> pCostCentersByAcct, List<String> pErrors) {
        log.info("doPostProcessing()=> checkExistCostCenters: " + pCostCentersByAcct);
        Map<String, List<CostCenterData>> existAcctCostCenterIds = existCostCentersByAcctRef.get(pAccountReference);
        for (Entry<ComparableList, String> entry : pCostCentersByAcct.entrySet()) {
            if (pAccountReference.equals(entry.getKey().get(0))) {
                String costCenter = entry.getValue();
                if (costCenter != null) {
                    if (existAcctCostCenterIds != null && !existAcctCostCenterIds.containsKey(costCenter)) {
                        log.info("Not found cost center for ref: '" + entry.getKey() + ", CostCenter: " + costCenter + "'");
                        pErrors.add("Not found cost center for ref: '" + entry.getKey() + ", CostCenter: " + costCenter + "'");
                    } else if (existAcctCostCenterIds != null && existAcctCostCenterIds.get(costCenter).size() > 1) {
                        log.info("Found duplicated  Cost Center for ref: '" + entry.getKey() + ", CostCenter: " + costCenter + "'");
                        pErrors.add("Found duplicated  Cost Center for ref: '" + entry.getKey() + ", CostCenter: " + costCenter + "'");
                    }
                }

            }
        }

    }

    private void checkExistItems(Map<ComparableList, ItemData> pItemDataByDistItemRef, Set<ComparableList> pDistItemRefs) {
        for (ComparableList i : pDistItemRefs) {
            ItemData val = pItemDataByDistItemRef.get(i);
            if (val == null) {
                log.info("Not found item for '" + i + "'");
            }
        }
    }

    private void checkExistFreightTables(Map<Integer, FreightTableData> pFreightTablesMap,
                                         HashMap<ComparableList, Set<Integer>> pFreightTablesIds,
                                         List<String> pErrors) {

        log.info("checkExistFreightTables()=> BEGIN");

        for (Entry<ComparableList, Set<Integer>> entry : pFreightTablesIds.entrySet()) {

            if (entry.getValue().size() > 1) {
                pErrors.add("Found duplicate FeightTableID: " + entry.getValue() + " in group[AccountRef, CatalogRef]: " + entry.getKey());
            }

            for (Integer i : entry.getValue()) {
                if (!pFreightTablesMap.containsKey(i)) {
                    appendErrorMsgs("Not found Freight Table for '" + i + "'.");
                }
            }
        }

        log.info("checkExistFreightTables()=> END.");

    }

    private void checkAccounts(Map<String, Integer> pAccountIdsByAccountRefMap,
                               Set<String> pAccountRefs,
                               List<String> pErrors) {

        for (String accountRef : pAccountRefs) {

            boolean found = false;

            for (String accountRefToken : pAccountIdsByAccountRefMap.keySet()) {
                String key = Utility.getFirstToken(accountRefToken);
                if (accountRef.equals(key)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                pErrors.add("Not found account for account reference:'" + accountRef + "'.");
            }
        }
    }

    private Set<ComparableList> checkItemDuplicates(Map<ComparableList, List<XpedxCatalogItemView>> pGroupedByAccountCatalog,
                                                    Map<String, Integer> pDistIdByName,
                                                    List<String> pErrors) {

        Set<ComparableList> result = new TreeSet<ComparableList>();
        Set<String> distNotFoundErrors = new TreeSet<String>();
        Set<String> duplicateErrors = new TreeSet<String>();

        for (Map.Entry<ComparableList, List<XpedxCatalogItemView>> e : pGroupedByAccountCatalog.entrySet()) {

            Set<ComparableList> buffer = new HashSet<ComparableList>();

            for (XpedxCatalogItemView item : e.getValue()) {

                String distReference = item.getDistributor().trim();
                String itemReference = item.getDistSKU().trim();

                ComparableList key = ComparableList.createValue(distReference, itemReference);
                if (buffer.contains(key)) {
                    duplicateErrors.add("Found duplicate item:" + key + " in group[AccountRef, CatalogRef]:" + e.getKey());
                }

                buffer.add(key);
                Integer distId = pDistIdByName.get(distReference);
                if (distId == null) {
                    distNotFoundErrors.add("Not found distributor for ref:'" + distReference + "'");
                } else {
                    key = ComparableList.createValue(distId, itemReference);
                    result.add(key);
                }
            }
        }

        for (String i : distNotFoundErrors) {
            pErrors.add(i);
        }

        for (String i : duplicateErrors) {
            pErrors.add(i);
        }

        return result;
    }

    private void synchronizeCatalogs(Connection pCon,
                                     Map<ComparableList, Integer> pCatalogsMap,
                                     Set<ComparableList> pCatalogsForCreate,
                                     Map<String, Integer> pAccountsMap,
                                     Map<Integer, Integer> pStoresByAccount,
                                     Map<String, List<Integer>> pCatalogDistIds,
                                     List<String> pErrors) throws Exception {

        log.info("synchronizeCatalogs()=> BEGIN");

        Map<ComparableList, Integer> createdCatalogsMap = InboundXpedxLoaderHelper.createCatalogs(pCon, pCatalogsForCreate, ADD_BY);
        pCatalogsMap.putAll(createdCatalogsMap);
        Map<Integer, Set<Integer>> busEntityIdsMappedByCatalogId = InboundXpedxLoaderHelper.getBusEntIdsByCatalogId(pCon, pCatalogsMap.values(), pErrors);

        InboundXpedxLoaderHelper.synchAllCatalogAssocs(pCon,
                ADD_BY,
                busEntityIdsMappedByCatalogId,
                pCatalogsMap,
                pStoresByAccount,
                pAccountsMap,
                acctMainDist,
                pCatalogDistIds);

        log.info("synchronizeCatalogs()=> END.");

    }

    private Map<String, Integer> synchronizeCategories(Connection pCon,
                                                       Map<String, Map<String, Map<String, Map<String, Set<String>>>>> pCategoriesTreeByAcct,
                                                       Map<String, Set<String>> pCategoriesByAcct,
                                                       int pStoreCatalogId,
                                                       int pAccountCatalogId,
                                                       Map<Integer, Set<Integer>> pCategoryIdsByCatalogId) throws Exception {

        log.info("synchronizeCategories()=> BEGIN");

        Map<String, Integer> result = new TreeMap<String, Integer>();
        for (Map.Entry<String, Set<String>> e1 : pCategoriesByAcct.entrySet()) {

            String acctRef = e1.getKey();

            Set<String> categoriesForCreate = getCategoriesToCreate(acctRef, e1.getValue());

            log.info("synchronizeCategories()=> categoriesForCreate: " + categoriesForCreate);

            for (String name : categoriesForCreate) {

                DBCriteria crit = new DBCriteria();
                crit.addEqualTo(ItemDataAccess.SHORT_DESC, name);
                crit.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.CATEGORY);
                crit.addEqualTo(ItemDataAccess.ITEM_STATUS_CD, RefCodeNames.ITEM_STATUS_CD.ACTIVE);
                crit.addJoinCondition(ItemDataAccess.ITEM_ID,
                        CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE,
                        CatalogStructureDataAccess.ITEM_ID);
                crit.addJoinTableEqualTo(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE,
                        CatalogStructureDataAccess.CATALOG_ID,
                        pStoreCatalogId);

                ItemDataVector itemDataV = ItemDataAccess.select(pCon, crit);

                ItemData itemData = ItemData.createValue();
                if (itemDataV != null && itemDataV.size() > 0) {
                    itemData = (ItemData) itemDataV.get(0);
                } else {
                    itemData.setShortDesc(name);
                    itemData.setItemTypeCd(RefCodeNames.ITEM_TYPE_CD.CATEGORY);
                    itemData.setItemStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
                    itemData.setAddBy(ADD_BY);
                    itemData.setModBy(ADD_BY);
                    itemData = ItemDataAccess.insert(pCon, itemData);
                }

                result.put(name, itemData.getItemId());

                if (acctExistCategories.containsKey(acctRef)) {
                    Map<String, Integer> pCategoriesMap = acctExistCategories.get(acctRef);
                    if (!pCategoriesMap.containsKey(name)) {
                        pCategoriesMap.put(name, itemData.getItemId());
                    }
                } else {
                    Map<String, Integer> pCategoriesMap = new HashMap<String, Integer>();
                    pCategoriesMap.put(name, itemData.getItemId());
                    acctExistCategories.put(acctRef, pCategoriesMap);
                }

                checkAndCreateIfNeedCatalogStructureForCategory(pCon,
                        pStoreCatalogId,
                        itemData.getItemId(),
                        null,
                        pCategoryIdsByCatalogId,
                        0,
                        acctRef);

                checkAndCreateIfNeedCatalogStructureForCategory(pCon,
                        pAccountCatalogId,
                        itemData.getItemId(),
                        null,
                        pCategoryIdsByCatalogId,
                        0,
                        acctRef);
            }

        }

        for (Map.Entry<String, Map<String, Map<String, Map<String, Set<String>>>>> categoryTree : pCategoriesTreeByAcct.entrySet()) {

            log.info("synchronizeCategories()=> creating item association");

            String acctRef = categoryTree.getKey();

            Map<String, Integer> pCategoriesMap = acctExistCategories.get(acctRef);
            Map<Integer, Integer> pCategoriesTreeRelation = existStoreCatalogCategoryRelations;

            Map<String, Map<String, Map<String, Set<String>>>> child1Tree = categoryTree.getValue();

            if (!child1Tree.isEmpty()) {

                for (Entry<String, Map<String, Map<String, Set<String>>>> child1TreeEntry : child1Tree.entrySet()) {

                    int category1 = 0;

                    if (pCategoriesMap.get(child1TreeEntry.getKey()) != null && pCategoriesMap.get(child1TreeEntry.getKey()) > 0) {
                        category1 = pCategoriesMap.get(child1TreeEntry.getKey());
                    } else {
                        log.warn("synchronizeCategories()=> Category '" + child1TreeEntry.getKey() + "' is not synchronized with: " + acctRef);
                    }

                    Map<String, Map<String, Set<String>>> child2Tree = child1TreeEntry.getValue();

                    for (Map.Entry<String, Map<String, Set<String>>> child2TreeEntry : child2Tree.entrySet()) {

                        int category2 = 0;

                        if (pCategoriesMap.get(child2TreeEntry.getKey()) != null && pCategoriesMap.get(child2TreeEntry.getKey()) > 0) {
                            category2 = pCategoriesMap.get(child2TreeEntry.getKey());
                        } else {
                            log.warn("synchronizeCategories()=> Category '" + child2TreeEntry.getKey() + "' is not synchronized with: " + acctRef);
                        }

                        if (category1 > 0 && category2 > 0) {
                            if (pCategoriesTreeRelation.get(category2) == null) {
                                createItemAssocWithStoreCatalog(pCon,
                                        category2,
                                        category1,
                                        pStoreCatalogId,
                                        RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);
                            } else if (pCategoriesTreeRelation.get(category2) != category1) {
                                ArrayList<String> errors = updateItemAssocWithStoreCatalog(pCon, pStoreCatalogId, category1, category2);
                                log.warn("synchronizeCategories()=> errors: " + errors);
                            } else {

                            }
                        }

                        Map<String, Set<String>> child3Tree = child2TreeEntry.getValue();

                        for (Map.Entry<String, Set<String>> child3TreeEntry : child3Tree.entrySet()) {

                            int category3 = 0;

                            if (pCategoriesMap.get(child3TreeEntry.getKey()) != null && pCategoriesMap.get(child3TreeEntry.getKey()) > 0) {
                                category3 = pCategoriesMap.get(child3TreeEntry.getKey());
                            } else {

                            }

                            if (category2 > 0 && category3 > 0) {
                                if (pCategoriesTreeRelation.get(category3) == null) {
                                    createItemAssocWithStoreCatalog(pCon,
                                            category3,
                                            category2,
                                            pStoreCatalogId,
                                            RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);
                                } else if (pCategoriesTreeRelation.get(category3) != category2) {
                                    ArrayList<String> errors = updateItemAssocWithStoreCatalog(pCon, pStoreCatalogId, category2, category3);
                                    log.warn("synchronizeCategories()=> errors: " + errors);
                                } else {
                                }
                            }

                            log.debug("synchronizeCategories()=> isCategorySupportLevel4: " + isCategorySupportLevel4);

                            if (isCategorySupportLevel4) {

                                for (String child4 : child3TreeEntry.getValue()) {
                                    int category4 = 0;

                                    if (pCategoriesMap.get(child4) != null && pCategoriesMap.get(child4) > 0) {
                                        category4 = pCategoriesMap.get(child4);
                                    } else {
                                        log.warn("synchronizeCategories()=> Category '" + child4 + "' is not synchronized with: " + acctRef);
                                    }

                                    if (category3 > 0 && category4 > 0) {
                                        if (pCategoriesTreeRelation.get(category4) == null) {
                                            createItemAssocWithStoreCatalog(pCon,
                                                    category4,
                                                    category3,
                                                    pStoreCatalogId,
                                                    RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);
                                        } else if (pCategoriesTreeRelation.get(category4) != category3) {
                                            ArrayList<String> errors = updateItemAssocWithStoreCatalog(pCon, pStoreCatalogId, category3, category4);
                                            log.warn("synchronizeCategories()=> errors: " + errors);
                                        } else {

                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

        log.info("synchronizeCategories()=> END.");

        return result;
    }

    private Integer checkAndCreateIfNeedCatalogStructureForCategory(Connection pCon,
                                                                    int pCatalogId,
                                                                    int pItemId,
                                                                    Integer pCostCenterId,
                                                                    Map<Integer, Set<Integer>> pCategoryIdsByCatalogId,
                                                                    int pDistId,
                                                                    String pAcctRef) throws Exception {

        Set<Integer> categoryIds = pCategoryIdsByCatalogId.get(pCatalogId);
        if (categoryIds == null) {
            categoryIds = new TreeSet<Integer>();
            pCategoryIdsByCatalogId.put(pCatalogId, categoryIds);
        }

        if (!categoryIds.contains(pItemId)) {

            boolean wasChanged = false;

            CatalogStructureData catalogStructureData = CatalogStructureData.createValue();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
            crit.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pItemId);
            crit.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

            CatalogStructureDataVector csDV = CatalogStructureDataAccess.select(pCon, crit);

            if (csDV != null && csDV.size() > 0) {
                catalogStructureData = (CatalogStructureData) csDV.get(0);
            } else {
                catalogStructureData.setCatalogId(pCatalogId);
                catalogStructureData.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);
                catalogStructureData.setItemId(pItemId);
                catalogStructureData.setAddBy(ADD_BY);
                catalogStructureData.setModBy(ADD_BY);
            }

            if (pCostCenterId != null && pCostCenterId != catalogStructureData.getCostCenterId()) {
                catalogStructureData.setCostCenterId(Utility.intNN(pCostCenterId));
                wasChanged = true;
            }

            if (catalogStructureData.getCatalogStructureId() > 0 && wasChanged) {
                CatalogStructureDataAccess.update(pCon, catalogStructureData);
            } else {
                catalogStructureData = CatalogStructureDataAccess.insert(pCon, catalogStructureData);
            }

            categoryIds.add(pItemId);

            return catalogStructureData.getCatalogStructureId();

        } else {

            boolean wasChanged = false;

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pCatalogId);
            crit.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pItemId);
            crit.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

            CatalogStructureDataVector csDV = CatalogStructureDataAccess.select(pCon, crit);

            if (csDV != null && csDV.size() > 0) {

                CatalogStructureData catalogStructureData = (CatalogStructureData) csDV.get(0);

                if (pCostCenterId != null && pCostCenterId != catalogStructureData.getCostCenterId()) {
                    catalogStructureData.setCostCenterId(Utility.intNN(pCostCenterId));
                    wasChanged = true;
                }

                if (catalogStructureData.getCatalogStructureId() > 0 && wasChanged) {
                    CatalogStructureDataAccess.update(pCon, catalogStructureData);
                }
            }
        }

        return null;
    }

    private ItemAssocData createItemAssoc(Connection pCon,
                                          int pItem1,
                                          int pItem2,
                                          int pCatalogId,
                                          String pItemAssocCd) throws Exception {

        //if record exists update else add new
        ItemAssocData itemAssoc = ItemAssocData.createValue();

        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);
        crit.addEqualTo(ItemAssocDataAccess.ITEM1_ID, pItem1);
        crit.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, pItemAssocCd);

        ItemAssocDataVector iaDV = ItemAssocDataAccess.select(pCon, crit);
        //log.info("createItemAssoc()=> pItem1:" + pItem1 + " pItem2: " + pItem2 + " in catalog " + pCatalogId);

        if (iaDV != null && iaDV.size() > 0) {

            itemAssoc = (ItemAssocData) iaDV.get(0);
            log.info("createItemAssoc()=> replacing old category " + itemAssoc.getItem2Id() + " with new " + pItem2 + " in catalog " + pCatalogId);
            itemAssoc.setModBy(ADD_BY);
            itemAssoc.setItem2Id(pItem2);

            ItemAssocDataAccess.update(pCon, itemAssoc);

        } else {

            itemAssoc.setAddBy(ADD_BY);
            itemAssoc.setModBy(ADD_BY);
            itemAssoc.setCatalogId(pCatalogId);
            itemAssoc.setItem1Id(pItem1);
            itemAssoc.setItem2Id(pItem2);
            itemAssoc.setItemAssocCd(pItemAssocCd);

            itemAssoc = ItemAssocDataAccess.insert(pCon, itemAssoc);
        }

        return itemAssoc;
    }

    private ItemAssocData createItemAssocWithStoreCatalog(Connection pCon,
                                                          int pItem1,
                                                          int pItem2,
                                                          int pCatalogId,
                                                          String pItemAssocCd) throws Exception {

        //if record exists skip else add new
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);
        crit.addEqualTo(ItemAssocDataAccess.ITEM1_ID, pItem1);

        ItemAssocDataVector iaDV = ItemAssocDataAccess.select(pCon, crit);
        if (iaDV != null && iaDV.size() > 0) {
            log.info("createItemAssocWithStoreCatalog()=> Skip adding new category " + pItem1 + " to store catalog " + pCatalogId);
            return (ItemAssocData) iaDV.get(0);
        }

        ItemAssocData itemAssoc = ItemAssocData.createValue();
        itemAssoc.setAddBy(ADD_BY);
        itemAssoc.setModBy(ADD_BY);
        itemAssoc.setCatalogId(pCatalogId);
        itemAssoc.setItem1Id(pItem1);
        itemAssoc.setItem2Id(pItem2);
        itemAssoc.setItemAssocCd(pItemAssocCd);

        itemAssoc = ItemAssocDataAccess.insert(pCon, itemAssoc);

        return itemAssoc;
    }

    private ArrayList<String> updateItemAssocWithStoreCatalog(Connection pCon, int pCatalogId, int pParentCategory, int pChildCategory) throws SQLException {

        ArrayList<String> errors = new ArrayList<String>();

        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pCatalogId);
        crit.addEqualTo(ItemAssocDataAccess.ITEM1_ID, pChildCategory);

        ItemAssocDataVector iaDV = ItemAssocDataAccess.select(pCon, crit);
        if (iaDV.size() == 1) {
            ItemAssocData itemAssoc = (ItemAssocData) iaDV.get(0);
            log.info("updateItemAssocWithStoreCatalog()=> category structure for catalog" + pCatalogId + " will be change");
            log.info("updateItemAssocWithStoreCatalog()=> parent for child " + pChildCategory +
                    " will be replaced from " + itemAssoc.getItem2Id() +
                    " to " + pParentCategory);
            itemAssoc.setItem2Id(pParentCategory);
            itemAssoc.setModBy(ADD_BY);
            ItemAssocDataAccess.update(pCon, itemAssoc);
        } else if (iaDV.size() > 1) {
            errors.add("Found multiple parents for category: " + pChildCategory + ", catalogID: " + pCatalogId);
        } else {
            errors.add("Not found parent for category: " + pChildCategory + ", catalogID: " + pCatalogId);
        }

        return errors;
    }

    private List<ItemAssocObj> synchronizeCatalogCategories(Connection pCon,
                                                            Map<ComparableList, Integer> pCatalogsMap,
                                                            Map<Integer, Set<Integer>> pCategoryIdsByCatalogId,
                                                            Map<Integer, Map<Integer, Set<Integer>>> pItemIdsByCategoryIdByCatalogId,
                                                            Map<ComparableList, ItemData> pItemDataByDistItemRef,
                                                            Map<ComparableList, String> pCostCenterNamesByAcctRef) throws Exception {

        final int count = parsedDataList.size();

        log.info("Synchronizing catalog categories, total items:" + count);
        int prevStep = -1;
        log.info("time now in 2 " + Calendar.getInstance().getTime());

        List<ItemAssocObj> itemAssocList = new ArrayList<ItemAssocObj>();
        for (int i = 0; i < count; i++) {
            int step = 100 * i / count;
            if (step > prevStep) {
                log.debug("processing step ... " + step + "%");
                prevStep = step;
            }

            XpedxCatalogItemView parsedDataItem = parsedDataList.get(i);

            ComparableList key = ComparableList.createValue(parsedDataItem.getAccountNumber().trim(), parsedDataItem.getCatalogID().trim());
            int catalogId = pCatalogsMap.get(key);

            ItemData itemData = pItemDataByDistItemRef.get(ComparableList.createValue(
                    parsedDataItem.getDistributor().trim(),
                    parsedDataItem.getDistSKU().trim()));

            Map<String, Integer> pExistCategories = acctExistCategories.get(parsedDataItem.getAccountNumber());

            int categoryId = 0;

            Set<String> categories = new TreeSet<String>();

            Map<String, List<CostCenterData>> costCenters = existCostCentersByAcctRef.get(parsedDataItem.getAccountNumber());
            Integer costCenterId = null;
            if (costCenters != null) {
                String costCenter = pCostCenterNamesByAcctRef.get(
                        ComparableList.createValue(
                                parsedDataItem.getAccountNumber(),
                                parsedDataItem.getCategory()));
                if (costCenter != null) {
                    costCenterId = costCenters.get(costCenter).get(0).getCostCenterId();
                }
            }

            if (Utility.isSet(parsedDataItem.getCategory())) {
                categories.add(parsedDataItem.getCategory());
                if (pExistCategories.get(parsedDataItem.getCategory().trim()) != null) {
                    categoryId = pExistCategories.get(parsedDataItem.getCategory().trim());
                }
            }

            if (Utility.isSet(parsedDataItem.getSubCat1().trim())) {
                categories.add(parsedDataItem.getSubCat1());
                if (pExistCategories.get(parsedDataItem.getSubCat1().trim()) != null) {
                    categoryId = pExistCategories.get(parsedDataItem.getSubCat1().trim());
                }
            }

            if (Utility.isSet(parsedDataItem.getSubCat2())) {
                categories.add(parsedDataItem.getSubCat2());
                if (pExistCategories.get(parsedDataItem.getSubCat2().trim()) != null) {
                    categoryId = pExistCategories.get(parsedDataItem.getSubCat2().trim());
                }
            }

            if (Utility.isSet(parsedDataItem.getSubCat3())) {
                categories.add(parsedDataItem.getSubCat3());
                if (pExistCategories.get(parsedDataItem.getSubCat3().trim()) != null) {
                    categoryId = pExistCategories.get(parsedDataItem.getSubCat3().trim());
                }
            }

            Map<Integer, Set<Integer>> itemIdsByCategoryId = pItemIdsByCategoryIdByCatalogId.get(catalogId);

            if (itemIdsByCategoryId == null) {
                itemIdsByCategoryId = new TreeMap<Integer, Set<Integer>>();
                pItemIdsByCategoryIdByCatalogId.put(catalogId, itemIdsByCategoryId);
            }

            Set<Integer> itemIds = itemIdsByCategoryId.get(categoryId);
            if (itemIds == null) {
                itemIds = new TreeSet<Integer>();
                itemIdsByCategoryId.put(categoryId, itemIds);
            }

            int distId = 0;
            if (Utility.isSet(parsedDataItem.getDistributor())) {
                String distReference = parsedDataItem.getDistributor();
                if (itemDistMap != null && itemDistMap.containsKey(distReference)) {
                    distId = (itemDistMap.get(distReference));
                }
            }

            for (String category : categories) {
                int thisCat = 0;

                if (pExistCategories.get(category) != null) {
                    thisCat = pExistCategories.get(category);
                }

                if (thisCat > 0) {
                    checkAndCreateIfNeedCatalogStructureForCategory(pCon,
                            catalogId,
                            thisCat,
                            costCenterId,
                            pCategoryIdsByCatalogId,
                            distId, parsedDataItem.getAccountNumber());
                }
            }

            if (itemData != null && itemData.getItemId() > 0) {

                if (!itemIds.contains(itemData.getItemId())) {

                    ItemAssocObj itemAssoc = new ItemAssocObj();

                    itemAssoc.setItem1Id(itemData.getItemId());
                    itemAssoc.setItem2Id(categoryId);
                    itemAssoc.setCatalogId(catalogId);
                    itemAssocList.add(itemAssoc);

                    itemIds.add(itemData.getItemId());
                }

            } else {

                log.warn("Item is not synchronized with " + ComparableList.createValue(
                        parsedDataItem.getDistributor().trim(),
                        parsedDataItem.getDistSKU().trim()));

            }

        }
        log.info("time now out 2 " + Calendar.getInstance().getTime());

        return itemAssocList;
    }

    private void synchronizeCatalogStructure(Connection pCon,
                                             Map<ComparableList, Integer> pCatalogIdsByAccountCatalogRef,
                                             Map<ComparableList, ItemData> pItemDataByDistItemRef,
                                             Map<Integer, Map<Integer, CatalogStructureData>> pCatalogStructuresByCatalog,
                                             Map<ComparableList, String> pCostCenterNamesByAcctRef) throws Exception {

        Map<Integer, Set<Integer>> catalogStructureForDelete = new TreeMap<Integer, Set<Integer>>();
        Set<String> existMapping = new HashSet<String>();

        log.info("Synchronizing catalog structures, total items:" + parsedDataList.size());

        int prevStep = -1;
        final int count = parsedDataList.size();

        log.info("time now in " + Calendar.getInstance().getTime());
        for (int i = 0; i < count; i++) {
            int step = 100 * i / count;
            if (step > prevStep) {
                log.debug("processing step ... " + step + "%");
                prevStep = step;
            }

            XpedxCatalogItemView parsedDataItem = parsedDataList.get(i);
            String distReference = parsedDataItem.getDistributor().trim();

            int distId = 0;
            if (itemDistMap != null && itemDistMap.containsKey(distReference)) {
                distId = itemDistMap.get(distReference);
            }

            String itemReference = parsedDataItem.getDistSKU().trim();
            String accountReference = parsedDataItem.getAccountNumber().trim();
            String catalogReference = parsedDataItem.getCatalogID().trim();

            Map<String, List<CostCenterData>> costCenters = existCostCentersByAcctRef.get(accountReference);
            Integer costCenterId = null;
            if (costCenters != null) {
                String costCenter = pCostCenterNamesByAcctRef.get(
                        ComparableList.createValue(
                                accountReference,
                                parsedDataItem.getCategory()));
                if (costCenter != null) {
                    costCenterId = costCenters.get(costCenter).get(0).getCostCenterId();
                }
            }

            ItemData itemData = pItemDataByDistItemRef.get(ComparableList.createValue(distReference, itemReference));
            int catalogId = pCatalogIdsByAccountCatalogRef.get(ComparableList.createValue(accountReference, catalogReference));

            if (itemData != null & itemData.getItemId() > 0) {

                Map<Integer, CatalogStructureData> val = pCatalogStructuresByCatalog.get(catalogId);

                if (val == null) {
                    val = new TreeMap<Integer, CatalogStructureData>();
                    pCatalogStructuresByCatalog.put(catalogId, val);
                }

                Integer itemGrpId = null;
                HashMap<Integer, ItemData> allMultiProds = multiProdsByAcctRef.get(accountReference);
                if (allMultiProds != null && allMultiProds.size() > 0) {
                    if (Utility.isSet(parsedDataItem.getMultiProductID())) {
                        ItemData multiProduct = allMultiProds.get(new Integer(parsedDataItem.getMultiProductID()));
                        if (multiProduct != null && multiProduct.getItemId() > 0) {
                            itemGrpId = multiProduct.getItemId();
                        } else {
                            log.warn("Multi Product is not synchronized with " + ComparableList.createValue(
                                    accountReference.trim(),
                                    parsedDataItem.getMultiProductID()));
                        }
                    }
                }

                CatalogStructureData catalogStructureData = val.get(itemData.getItemId());

                if (catalogStructureData == null) {
                    catalogStructureData = createCatalogStructure(catalogId,
                            distId,
                            itemData,
                            itemGrpId,
                            costCenterId,
                            parsedDataItem);
                    catalogStructureData.setAddBy(ADD_BY);
                    catalogStructureData.setModBy(ADD_BY);
                    catalogStructureData = CatalogStructureDataAccess.insert(pCon, catalogStructureData);
                } else {
                    boolean wasChanged = changeCatalogStructure(catalogStructureData,
                            parsedDataItem,
                            distId,
                            costCenterId,
                            itemGrpId);
                    if (wasChanged) {
                        catalogStructureData.setModBy(ADD_BY);
                        CatalogStructureDataAccess.update(pCon, catalogStructureData);
                    }
                }

                val.put(itemData.getItemId(), catalogStructureData);
                existMapping.add(catalogId + "_" + itemData.getItemId());

            } else {

                log.warn("Item is not synchronized with " + ComparableList.createValue(
                        parsedDataItem.getDistributor().trim(),
                        parsedDataItem.getDistSKU().trim()));

            }

        }

        log.info("time now out " + Calendar.getInstance().getTime());
        log.info("Preparing for delete...");
        for (Map<Integer, CatalogStructureData> ii : pCatalogStructuresByCatalog.values()) {
            for (CatalogStructureData i : ii.values()) {

                if (!existMapping.contains(i.getCatalogId() + "_" + i.getItemId())) {

                    Set<Integer> itemIds = catalogStructureForDelete.get(i.getCatalogId());
                    if (itemIds == null) {
                        itemIds = new TreeSet<Integer>();
                    }
                    itemIds.add(i.getItemId());
                    catalogStructureForDelete.put(i.getCatalogId(), itemIds);
                }
            }
        }

        log.info("Delete catalog structure from " + catalogStructureForDelete.keySet().size() + " catalogs.");
        for (Map.Entry<Integer, Set<Integer>> e : catalogStructureForDelete.entrySet()) {

            log.info("Delete items  " + e.getValue() + " from catalog:" + e.getKey());

            DBCriteria cr = new DBCriteria();
            cr.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
            cr.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, e.getKey());
            cr.addOneOf(CatalogStructureDataAccess.ITEM_ID, new ArrayList<Integer>(e.getValue()));

            CatalogStructureDataAccess.remove(pCon, cr);

            cr = new DBCriteria();
            cr.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
            cr.addEqualTo(ItemAssocDataAccess.CATALOG_ID, e.getKey());
            cr.addOneOf(ItemAssocDataAccess.ITEM1_ID, new ArrayList<Integer>(e.getValue()));

            ItemAssocDataAccess.remove(pCon, cr);

            Map<Integer, CatalogStructureData> val = pCatalogStructuresByCatalog.get(e.getKey());
            for (int itemId : e.getValue()) {
                val.remove(itemId);
            }

        }

    }


    private boolean changeCatalogStructure(CatalogStructureData catalogStructureData,
                                           XpedxCatalogItemView parsedDataItem,
                                           int distId,
                                           Integer pCostCenterId,
                                           Integer itemGrpId) {

       boolean wasChanged = false;


        if (distId > 0 && catalogStructureData.getBusEntityId() != distId) {
            catalogStructureData.setBusEntityId(distId);
            wasChanged = true;
        }

        if (Utility.intNN(pCostCenterId) != catalogStructureData.getCostCenterId()) {
            catalogStructureData.setCostCenterId(Utility.intNN(pCostCenterId));
            wasChanged = true;
        }

        if (Utility.intNN(itemGrpId) != catalogStructureData.getItemId()) {
            catalogStructureData.setItemGroupId(Utility.intNN(itemGrpId));
            wasChanged = true;
        }

        if (Utility.isSet(parsedDataItem.getCustItemDesc())) {
            if (!parsedDataItem.getCustItemDesc().equals(catalogStructureData.getShortDesc())) {
                catalogStructureData.setShortDesc(parsedDataItem.getCustItemDesc());
                wasChanged = true;
            }
        } else {
            if (!Utility.strNN(parsedDataItem.getShortDescription()).equals(Utility.strNN(catalogStructureData.getShortDesc()))) {
                catalogStructureData.setShortDesc(parsedDataItem.getShortDescription());
                wasChanged = true;
            }
        }


        if (!Utility.strNN(parsedDataItem.getCustItemNum()).equals(Utility.strNN(catalogStructureData.getCustomerSkuNum()))) {
            catalogStructureData.setCustomerSkuNum(parsedDataItem.getCustItemNum());
            wasChanged = true;
        }

        return wasChanged;
    }

    private CatalogStructureData createCatalogStructure(int catalogId,
                                                        int distId,
                                                        ItemData itemData,
                                                        Integer itemGrpId,
                                                        Integer pCostCenterId,
                                                        XpedxCatalogItemView pItem) {

        CatalogStructureData catalogStructureData = CatalogStructureData.createValue();

        catalogStructureData.setCatalogId(catalogId);
        catalogStructureData.setItemId(itemData.getItemId());
        catalogStructureData.setBusEntityId(distId);
        catalogStructureData.setItemGroupId(Utility.intNN(itemGrpId));
        catalogStructureData.setCostCenterId(Utility.intNN(pCostCenterId));
        catalogStructureData.setCustomerSkuNum(pItem.getCustItemNum());
        catalogStructureData.setShortDesc(Utility.isSet(pItem.getCustItemDesc()) ? pItem.getCustItemDesc() : pItem.getShortDescription());
        catalogStructureData.setStatusCd(RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE);
        catalogStructureData.setAddBy(ADD_BY);
        catalogStructureData.setModBy(ADD_BY);
        catalogStructureData.setCatalogStructureCd(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

        return catalogStructureData;
    }


    private void synchronizeContract(Connection con,
                                     XpedxCatalogItemView pParsedDataItem,
                                     Map<ComparableList, Integer> pCatalogIdsByAccountCatalogRef,
                                     Map<Integer, ContractData> pContractsByCatalog) throws Exception {

        ComparableList catalogReference = ComparableList.createValue(
                pParsedDataItem.getAccountNumber().trim(),
                pParsedDataItem.getCatalogID().trim());

        Integer key = pCatalogIdsByAccountCatalogRef.get(catalogReference);

        ContractData contractData = pContractsByCatalog.get(key);

        if (contractData == null) {

            contractData = ContractData.createValue();

            contractData.setEffDate(ADD_DATE);
            contractData.setCatalogId(key);
            contractData.setLocaleCd(pParsedDataItem.getLocale());
            if (Utility.isSet(pParsedDataItem.getFreightTableID())) {
                contractData.setFreightTableId(Utility.parseInt(pParsedDataItem.getFreightTableID()));
            }

            contractData.setAddBy(ADD_BY);
            contractData.setModBy(ADD_BY);
            contractData.setRefContractNum(ZERO);
            contractData.setShortDesc(pParsedDataItem.getCatalogID());
            contractData.setContractStatusCd(RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
            contractData.setContractTypeCd(UNKNOWN);

            contractData = ContractDataAccess.insert(con, contractData);

            pContractsByCatalog.put(key, contractData);
        } else {

            boolean wasChanged = false;

            contractData.setEffDate(ADD_DATE);
            if (Utility.isSet(pParsedDataItem.getLocale())
                    && Utility.isSet(contractData.getLocaleCd())
                    && !pParsedDataItem.getLocale().equals(contractData.getLocaleCd())) {
                contractData.setLocaleCd(pParsedDataItem.getLocale());
                wasChanged = true;
            }

            if (Utility.isSet(pParsedDataItem.getFreightTableID())) {
                if (!pParsedDataItem.getFreightTableID().equals(String.valueOf(contractData.getFreightTableId()))) {
                    contractData.setFreightTableId(Utility.parseInt(pParsedDataItem.getFreightTableID()));
                    wasChanged = true;
                }
            } else if (contractData.getFreightTableId() > 0) {
                contractData.setFreightTableId(0);
                wasChanged = true;
            }

            if (wasChanged) {
                ContractDataAccess.update(con, contractData);
            }

        }
    }

    private List<String> synchronizeContractItem(Connection pCon,
                                                 Map<ComparableList, Integer> pCatalogIdsByAccountCatalogRef,
                                                 Map<ComparableList, ItemData> pItemDataByDistItemRef,
                                                 Map<Integer, ContractData> pContractsByCatalog,
                                                 Map<Integer, Map<Integer, ContractItemData>> pContractItemsByContract) throws Exception {

        Map<Integer, Set<Integer>> contractItemForDelete = new TreeMap<Integer, Set<Integer>>();
        Set<String> existMapping = new HashSet<String>();
        log.info("Synchronizing contract items, total items:" + parsedDataList.size());
        int prevStep = -1;
        final int count = parsedDataList.size();
        List<String> errorMessLL = new ArrayList<String>();

        for (int i = 0; i < count; i++) {
            int step = 100 * i / count;
            if (step > prevStep) {
                log.debug("processing step ... " + step + "%");
                prevStep = step;
            }

            XpedxCatalogItemView parsedDataItem = parsedDataList.get(i);
            Integer catalogData =
                    pCatalogIdsByAccountCatalogRef.get(
                            ComparableList.createValue(
                                    parsedDataItem.getAccountNumber().trim(),
                                    parsedDataItem.getCatalogID().trim()
                            )
                    );

            ContractData contractData = pContractsByCatalog.get(catalogData);
            ItemData itemData =
                    pItemDataByDistItemRef.get(ComparableList.createValue(
                            parsedDataItem.getDistributor().trim(),
                            parsedDataItem.getDistSKU()
                    )
                    );

            int contractId = contractData.getContractId();
            //get locale and no of digits after decimal places allowed
            String catLocale = contractData.getLocaleCd();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(CurrencyDataAccess.LOCALE, catLocale);

            CurrencyDataVector currDV = CurrencyDataAccess.select(pCon, crit);

            CurrencyData curr = (CurrencyData) currDV.get(0);
            int decimalPlaces = curr.getDecimals();

            if (Utility.isSet(parsedDataItem.getPrice())) {
                BigDecimal priceBD = new BigDecimal(parsedDataItem.getPrice());
                //check decimal places
                if (priceBD.scale() > decimalPlaces) {
                    String errorMess = "The price for item " + parsedDataItem.getDistSKU() +
                            " has too many decimal points." +
                            " It can only have " + decimalPlaces +
                            " decimal points for this currency";
                    if (!errorMessLL.contains(errorMess)) {
                        errorMessLL.add(errorMess);
                    }
                }
            }

            if (Utility.isSet(parsedDataItem.getCost())) {
                BigDecimal costBD = new BigDecimal(parsedDataItem.getCost());
                //check decimal places
                if (costBD.scale() > decimalPlaces) {
                    String errorMess = "The cost for item " + parsedDataItem.getDistSKU() +
                            " has too many decimal points." +
                            " It can only have " + decimalPlaces +
                            " decimal points for this currency";
                    if (!errorMessLL.contains(errorMess)) {
                        errorMessLL.add(errorMess);
                    }
                }
            }

            if (errorMessLL.size() > 0) {
                log.info("synchronizeContractItem()=> errorMess:" + errorMessLL);
                return errorMessLL;
            }

            Map<Integer, ContractItemData> val = pContractItemsByContract.get(contractId);

            if (val == null) {
                val = new TreeMap<Integer, ContractItemData>();
                pContractItemsByContract.put(contractId, val);
            }


            if (itemData != null && itemData.getItemId() > 0) {

                ContractItemData contractItemData = val.get(itemData.getItemId());

                if (contractItemData == null) {

                    contractItemData = ContractItemData.createValue();

                    contractItemData.setContractId(contractId);
                    contractItemData.setItemId(itemData.getItemId());
                    contractItemData.setAmount(new BigDecimal(parsedDataItem.getPrice()));
                    contractItemData.setDistCost(new BigDecimal(parsedDataItem.getCost()));
                    contractItemData.setServiceFeeCode(parsedDataItem.getServiceCode().trim());
                    contractItemData.setAddBy(ADD_BY);
                    contractItemData.setModBy(ADD_BY);

                    contractItemData = ContractItemDataAccess.insert(pCon, contractItemData);

                } else {

                    boolean wasChanged = false;

                    BigDecimal newPrice = new BigDecimal(parsedDataItem.getPrice());
                    if (newPrice.compareTo(contractItemData.getAmount()) != 0) {
                        contractItemData.setAmount(newPrice);
                        wasChanged = true;
                    }

                    BigDecimal newCost = new BigDecimal(parsedDataItem.getCost());
                    if (newCost.compareTo(contractItemData.getDistCost()) != 0) {
                        contractItemData.setDistCost(newCost);
                        wasChanged = true;
                    }

                    if (Utility.isSet(parsedDataItem.getServiceCode())
                            && Utility.isSet(contractItemData.getServiceFeeCode())
                            && !parsedDataItem.getServiceCode().equals(contractItemData.getServiceFeeCode())) {
                        contractItemData.setServiceFeeCode(parsedDataItem.getServiceCode());
                        wasChanged = true;
                    }

                    if (wasChanged) {
                        ContractItemDataAccess.update(pCon, contractItemData);
                    }
                }

                val.put(itemData.getItemId(), contractItemData);
                existMapping.add(contractId + "_" + itemData.getItemId());

            } else {
                errorMessLL.add("Item is not synchronized with :" + ComparableList.createValue(
                        parsedDataItem.getDistributor().trim(),
                        parsedDataItem.getDistSKU()
                ));
            }
        }

        log.info("Preparing for delete...");
        for (Map<Integer, ContractItemData> ii : pContractItemsByContract.values()) {
            for (ContractItemData i : ii.values()) {
                if (!existMapping.contains(i.getContractId() + "_" + i.getItemId())) {
                    Set<Integer> itemIds = contractItemForDelete.get(i.getContractId());
                    if (itemIds == null) {
                        itemIds = new TreeSet<Integer>();
                        contractItemForDelete.put(i.getContractId(), itemIds);
                    }
                    itemIds.add(i.getItemId());
                }
            }
        }

        log.info("Delete contract items from " + contractItemForDelete.keySet().size() + " contracts.");
        for (Map.Entry<Integer, Set<Integer>> e : contractItemForDelete.entrySet()) {

            log.info("Delete items  " + e.getValue() + " from contract:" + e.getKey());

            DBCriteria cr = new DBCriteria();
            cr.addEqualTo(ContractItemDataAccess.CONTRACT_ID, e.getKey());
            cr.addOneOf(ContractItemDataAccess.ITEM_ID, new ArrayList<Integer>(e.getValue()));

            ContractItemDataAccess.remove(pCon, cr);
        }

        return errorMessLL;
    }

    private void add2CategoriesTree(Map<String, Map<String, Map<String, Set<String>>>> pCategoriesTree, XpedxCatalogItemView pParsedDataItem) {

        String category = pParsedDataItem.getCategory().trim();
        String category1 = pParsedDataItem.getSubCat1().trim();
        String category2 = pParsedDataItem.getSubCat2().trim();
        String category3 = pParsedDataItem.getSubCat3().trim();

        if (Utility.isSet(category)) {

            Map<String, Map<String, Set<String>>> children = pCategoriesTree.get(category);
            if (children == null) {
                children = new TreeMap<String, Map<String, Set<String>>>();
                pCategoriesTree.put(category, children);
            }

            if (Utility.isSet(category1)) {

                Map<String, Set<String>> children1 = children.get(category1);
                if (children1 == null) {
                    children1 = new TreeMap<String, Set<String>>();
                    children.put(category1, children1);
                }

                if (Utility.isSet(category2)) {

                    Set<String> children2 = children1.get(category2);
                    if (children2 == null) {
                        children2 = new TreeSet<String>();
                        children1.put(category2, children2);
                    }

                    if (Utility.isSet(category3) && !isIgnoreCategoryLevel4) {
                        children2.add(category3);
                    }
                }
            }
        }
    }

    private void checkCategoriesTree(Map<String, Map<String, Map<String, Set<String>>>> pCategoriesTree, List<String> pErrors) {

        log.info("checkCategoriesTree()=> BEGIN ");

        Map<String, String> level1 = new TreeMap<String, String>();
        Map<String, String> level2 = new TreeMap<String, String>();
        Map<String, String> level3 = new TreeMap<String, String>();

        for (Map.Entry<String, Map<String, Map<String, Set<String>>>> parentTree : pCategoriesTree.entrySet()) {

            String parent = parentTree.getKey();

            for (Map.Entry<String, Map<String, Set<String>>> children1Tree : parentTree.getValue().entrySet()) {

                String child1 = children1Tree.getKey();

                if (level1.get(child1) == null) {
                    level1.put(child1, parent);
                } else {
                    pErrors.add("Category '" + parent + "/" + child1 + "' already exists (" + level1.get(child1) + "/" + child1 + ").");
                }

                for (Map.Entry<String, Set<String>> children2Tree : children1Tree.getValue().entrySet()) {

                    String child2 = children2Tree.getKey();

                    if (level2.get(child2) == null) {
                        level2.put(child2, child1);
                    } else {
                        pErrors.add("Category '" + child1 + "/" + child2 + "' already exists (" + level2.get(child2) + "/" + child1 + ").");
                    }


                    if (isCategorySupportLevel4) {

                        for (String child3 : children2Tree.getValue()) {

                            if (level3.get(child3) == null) {
                                level3.put(child3, child2);
                            } else {
                                pErrors.add("Category '" + child2 + "/" + child3 + "' already exists (" + level3.get(child3) + "/" + child2 + ").");
                            }

                        }
                    } else if (!children2Tree.getValue().isEmpty()) {
                        pErrors.add("Category '" + child2 + "' contains category with level 4 (" + children2Tree.getValue() + ").");
                    }

                }

            }

        }
        log.info("checkCategoriesTree()=> END.");

    }

    private int getStoreCatalogId(Connection pCon, int pStoreId) throws Exception {

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

    private int getAcctCatalogId(Connection pCon, int pAcctId) throws Exception {

        DBCriteria cr = new DBCriteria();

        cr.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
        cr.addJoinCondition(CatalogDataAccess.CATALOG_ID, CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ID);
        cr.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
        cr.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.BUS_ENTITY_ID, pAcctId);

        CatalogDataVector list = CatalogDataAccess.select(pCon, cr);

        if (list != null && list.size() > 0) {
            CatalogData item = (CatalogData) list.get(0);
            return item.getCatalogId();
        }

        return -1;
    }

    private int syncronizeStoreCatalog(Connection pCon, int pStoreId) throws Exception {

        log.info("c> BEGIN");

        log.info("syncronizeStoreCatalog()=>  pStoreId: " + pStoreId);

        CatalogData catalog = CatalogData.createValue();

        catalog.setAddBy(ADD_BY);
        catalog.setModBy(ADD_BY);
        catalog.setCatalogStatusCd(RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
        catalog.setCatalogTypeCd(RefCodeNames.CATALOG_TYPE_CD.STORE);
        catalog.setShortDesc("Store_Catalog_" + pStoreId);

        catalog = CatalogDataAccess.insert(pCon, catalog);


        CatalogAssocData catalogAssoc = CatalogAssocData.createValue();
        catalogAssoc.setAddBy(ADD_BY);
        catalogAssoc.setModBy(ADD_BY);
        catalogAssoc.setBusEntityId(pStoreId);
        catalogAssoc.setCatalogId(catalog.getCatalogId());
        catalogAssoc.setCatalogAssocCd(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

        catalogAssoc = CatalogAssocDataAccess.insert(pCon, catalogAssoc);

        log.info("syncronizeStoreCatalog()=> new store catalog: " + catalog);

        log.info("syncronizeStoreCatalog()=> END.");

        return catalogAssoc.getCatalogId();
    }

    private int syncronizeAccountCatalog(Connection pCon, int pAccountId) throws Exception {

        log.info("syncronizeAccountCatalog()=> BEGIN");

        BusEntityData account = BusEntityDataAccess.select(pCon, pAccountId);

        log.info("syncronizeAccountCatalog()=> pAccountId: " + pAccountId);

        CatalogData catalog = CatalogData.createValue();

        catalog.setAddBy(ADD_BY);
        catalog.setModBy(ADD_BY);
        catalog.setCatalogStatusCd(RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
        catalog.setCatalogTypeCd(RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
        catalog.setShortDesc(account.getShortDesc() + " MASTER");

        catalog = CatalogDataAccess.insert(pCon, catalog);

        CatalogAssocData catalogAssoc = CatalogAssocData.createValue();
        catalogAssoc.setAddBy(ADD_BY);
        catalogAssoc.setModBy(ADD_BY);
        catalogAssoc.setBusEntityId(pAccountId);
        catalogAssoc.setCatalogId(catalog.getCatalogId());
        catalogAssoc.setCatalogAssocCd(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);

        CatalogAssocDataAccess.insert(pCon, catalogAssoc);

        catalogAssoc = CatalogAssocData.createValue();
        catalogAssoc.setAddBy(ADD_BY);
        catalogAssoc.setModBy(ADD_BY);
        catalogAssoc.setBusEntityId(pAccountId);
        catalogAssoc.setCatalogId(catalog.getCatalogId());
        catalogAssoc.setCatalogAssocCd(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

        CatalogAssocDataAccess.insert(pCon, catalogAssoc);

        log.info("ssyncronizeAccountCatalog()=> new account catalog: " + catalog);

        log.info("syncronizeAccountCatalog()=> END.");

        return catalogAssoc.getCatalogId();
    }

    private int getStoreId(Connection pCon, int tradingPartnerId) throws Exception {

        int[] ids = getTranslator().getTradingPartnerBusEntityIds(tradingPartnerId, RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);

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

    private void populateAccountInfo(Connection pCon, int pStoreCatalogId, Map<String, Integer> accountIdsByAccountRef) throws Exception {

        for (Map.Entry<String, Integer> en : accountIdsByAccountRef.entrySet()) {

            Integer acctId = en.getValue();
            String keyVal = en.getKey();

            int accCatalogId = getAcctCatalogId(pCon, acctId);

            acctCatalogs.put(acctId, accCatalogId);

            DBCriteria crit;

            crit = new DBCriteria();
            crit.addJoinTableEqualTo(CostCenterAssocDataAccess.CLW_COST_CENTER_ASSOC, CostCenterAssocDataAccess.CATALOG_ID, accCatalogId);
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
            existCostCentersByAcctRef.put(keyVal, acctCostCentersMap);

            crit = new DBCriteria();
            crit.addJoinTableEqualTo(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.CATALOG_ID, pStoreCatalogId);
            crit.addJoinTableEqualTo(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_MULTI_PRODUCT);
            crit.addJoinCondition(ItemDataAccess.ITEM_ID, CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.ITEM_ID);
            crit.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.ITEM_GROUP);

            ItemDataVector multiProducts = ItemDataAccess.select(pCon, crit);

            if (!multiProducts.isEmpty()) {

                HashMap<Integer, ItemData> allMultiProds = new HashMap<Integer, ItemData>();

                for (Object oMultiProduct : multiProducts) {
                    ItemData item = (ItemData) oMultiProduct;
                    allMultiProds.put(item.getItemId(), item);
                }

                multiProdsByAcctRef.put(keyVal, allMultiProds);
            }

            crit = new DBCriteria();
            crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, accCatalogId);
            crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR);

            CatalogAssocDataVector caDV = CatalogAssocDataAccess.select(pCon, crit);

            if (caDV != null && caDV.size() > 0) {
                CatalogAssocData caD = (CatalogAssocData) caDV.get(0);
                acctMainDist.put(acctId, caD.getBusEntityId());
            }

            Map<String, Integer> existCategories = InboundXpedxLoaderHelper.getExistCategories(pCon, accCatalogId, null);
            acctExistCategories.put(keyVal, existCategories);
        }

    }

    public class ProcessData {

        private Map<ComparableList, List<XpedxCatalogItemView>> groupedByAccountCatalog = new TreeMap<ComparableList, List<XpedxCatalogItemView>>();
        private Set<ComparableList> accountCatalogRefs = new TreeSet<ComparableList>();
        private Set<ComparableList> accountCatalogCategoryRefs = new TreeSet<ComparableList>();
        private Set<ComparableList> distItemRefs = new TreeSet<ComparableList>();
        private Set<String> accountRefs = new TreeSet<String>();
        private Map<String, Set<String>> categoriesByAcct = new TreeMap<String, Set<String>>();
        private Map<String, Set<String>> parentCategoriesByAcct = new TreeMap<String, Set<String>>();
        private Map<String, List<String>> catalogDistRefs = new TreeMap<String, List<String>>();
        private HashMap<ComparableList, Set<Integer>> freightTableIdsByCatalogReference = new HashMap<ComparableList, Set<Integer>>();
        private Set<Integer> allFreightTableIds = new HashSet<Integer>();
        private Set<String> allManufacturers = new HashSet<String>();
        private List<String> errors = new ArrayList<String>();
        private Map<String, Map<String, Map<String, Set<String>>>> categoriesTree = new TreeMap<String, Map<String, Map<String, Set<String>>>>();
        private Map<String, Map<String, Map<String, Map<String, Set<String>>>>> categoriesTreeByAcct = new TreeMap<String, Map<String, Map<String, Map<String, Set<String>>>>>();
        private List<String> allDists = new ArrayList<String>();
        private Map<String, Set<String>> multiProductsByAcct = new HashMap<String, Set<String>>();
        private Map<ComparableList, String> costCenterNamesByAcctRef = new HashMap<ComparableList, String>();
        private HashSet<String> inboundLocales = new HashSet<String>();
        private Map<String, byte[]> inboundImageDataMap = new HashMap<String, byte[]>();
        private Set<String> imageUrls = new HashSet<String>();

        public Map<ComparableList, List<XpedxCatalogItemView>> getGroupedByAccountCatalog() {
            return groupedByAccountCatalog;
        }

        public void setGroupedByAccountCatalog(Map<ComparableList, List<XpedxCatalogItemView>> groupedByAccountCatalog) {
            this.groupedByAccountCatalog = groupedByAccountCatalog;
        }

        public Set<ComparableList> getAccountCatalogCategoryRefs() {
            return accountCatalogCategoryRefs;
        }

        public void setAccountCatalogCategoryRefs(Set<ComparableList> accountCatalogCategoryRefs) {
            this.accountCatalogCategoryRefs = accountCatalogCategoryRefs;
        }

        public Set<ComparableList> getAccountCatalogRefs() {
            return accountCatalogRefs;
        }

        public void setAccountCatalogRefs(Set<ComparableList> accountCatalogRefs) {
            this.accountCatalogRefs = accountCatalogRefs;
        }

        public Set<String> getAccountRefs() {
            return accountRefs;
        }

        public void setAccountRefs(Set<String> accountRefs) {
            this.accountRefs = accountRefs;
        }

        public Set<ComparableList> getDistItemRefs() {
            return distItemRefs;
        }

        public void setDistItemRefs(Set<ComparableList> distItemRefs) {
            this.distItemRefs = distItemRefs;
        }

        public Map<String, Set<String>> getCategoriesByAcct() {
            return categoriesByAcct;
        }

        public void setCategoriesByAcct(Map<String, Set<String>> categoriesByAcct) {
            this.categoriesByAcct = categoriesByAcct;
        }

        public Map<String, List<String>> getCatalogDistRefs() {
            return catalogDistRefs;
        }

        public void setCatalogDistRefs(Map<String, List<String>> catalogDistRefs) {
            this.catalogDistRefs = catalogDistRefs;
        }

        public HashMap<ComparableList, Set<Integer>> getFreightTableIdsByCatalogReference() {
            return freightTableIdsByCatalogReference;
        }

        public void setFreightTableIdsByCatalogReference(HashMap<ComparableList, Set<Integer>> freightTableIdsByCatalogReference) {
            this.freightTableIdsByCatalogReference = freightTableIdsByCatalogReference;
        }

        public List<String> getErrors() {
            return errors;
        }

        public void setErrors(List<String> errors) {
            this.errors = errors;
        }

        public Map<String, Map<String, Map<String, Set<String>>>> getCategoriesTree() {
            return categoriesTree;
        }

        public void setCategoriesTree(Map<String, Map<String, Map<String, Set<String>>>> categoriesTree) {
            this.categoriesTree = categoriesTree;
        }

        public Map<String, Map<String, Map<String, Map<String, Set<String>>>>> getCategoriesTreeByAcct() {
            return categoriesTreeByAcct;
        }

        public void setCategoriesTreeByAcct(Map<String, Map<String, Map<String, Map<String, Set<String>>>>> categoriesTreeByAcct) {
            this.categoriesTreeByAcct = categoriesTreeByAcct;
        }

        public List<String> getAllDists() {
            return allDists;
        }

        public void setAllDists(List<String> allDists) {
            this.allDists = allDists;
        }

        public Map<String, Set<String>> getMultiProductsByAcct() {
            return multiProductsByAcct;
        }

        public void setMultiProductsByAcct(Map<String, Set<String>> multiProductsByAcct) {
            this.multiProductsByAcct = multiProductsByAcct;
        }

        public Map<ComparableList, String> getCostCenterNamesByAcctRef() {
            return costCenterNamesByAcctRef;
        }

        public void setCostCenterNamesByAcctRef(Map<ComparableList, String> costCenterNamesByAcctRef) {
            this.costCenterNamesByAcctRef = costCenterNamesByAcctRef;
        }

        public Set<Integer> getAllFreightTableIds() {
            return allFreightTableIds;
        }

        public void setAllFreightTableIds(Set<Integer> allFreightTableIds) {
            this.allFreightTableIds = allFreightTableIds;
        }

        public Set<String> getAllManufacturers() {
            return allManufacturers;
        }

        public void setAllManufacturers(Set<String> allManufacturers) {
            this.allManufacturers = allManufacturers;
        }

        public HashSet<String> getInboundLocales() {
            return inboundLocales;
        }

        public void setInboundLocales(HashSet<String> inboundLocales) {
            this.inboundLocales = inboundLocales;
        }
        public Map<String,byte[]> getInboundImageDataMap() {
            return inboundImageDataMap;
        }

        public void setInboundImageDataMap(Map<String, byte[]> inboundImageDataMap) {
            this.inboundImageDataMap = inboundImageDataMap;
        }

        public Set<String> getImageUrls() {
            return imageUrls;
        }

        public void setImageUrls(Set<String> imageUrls) {
            this.imageUrls = imageUrls;
        }
    }

    private ProcessData prepareProcessData() {

        ProcessData processData = new ProcessData();

        for (Object aParsedData : parsedDataList) {

            XpedxCatalogItemView parsedDataItem = (XpedxCatalogItemView) aParsedData;

            String accountReference = parsedDataItem.getAccountNumber().trim();
            String catalogReference = parsedDataItem.getCatalogID().trim();
            String distReference = parsedDataItem.getDistributor().trim();
            String itemReference = parsedDataItem.getDistSKU();
            String category = parsedDataItem.getCategory().trim();
            String category1 = parsedDataItem.getSubCat1().trim();
            String category2 = parsedDataItem.getSubCat2().trim();
            String category3 = parsedDataItem.getSubCat3().trim();

            ComparableList accountCatalogReference = ComparableList.createValue(accountReference, catalogReference);

            //KOHLSEDI, 1000
            if (Utility.isSet(distReference)) {

                if (!processData.getAllDists().contains(distReference)) {
                    processData.getAllDists().add(distReference);
                }

                if (processData.getCatalogDistRefs().containsKey(catalogReference)) {
                    List<String> dists = processData.getCatalogDistRefs().get(catalogReference);
                    dists.add(distReference);
                } else {
                    List<String> dists = new ArrayList<String>();
                    dists.add(distReference);
                    processData.getCatalogDistRefs().put(catalogReference, dists);
                }
            }

            Set<String> categories = new TreeSet<String>();
            if (Utility.isSet(category)) {
                processData.getAccountCatalogCategoryRefs().add(ComparableList.createValue(accountReference, catalogReference, category));
                categories.add(category);
            }

            if (Utility.isSet(category1)) {
                processData.getAccountCatalogCategoryRefs().add(ComparableList.createValue(accountReference, catalogReference, category1));
                categories.add(category1);
            }

            if (Utility.isSet(category2)) {
                processData.getAccountCatalogCategoryRefs().add(ComparableList.createValue(accountReference, catalogReference, category2));
                categories.add(category2);
            }

            if (Utility.isSet(category3) && !isIgnoreCategoryLevel4) {
                processData.getAccountCatalogCategoryRefs().add(ComparableList.createValue(accountReference, catalogReference, category3));
                categories.add(category3);
            }

            if (!processData.getCategoriesByAcct().containsKey(accountReference)) {
                processData.getCategoriesByAcct().put(accountReference, categories);
            } else {
                processData.getCategoriesByAcct().get(accountReference).addAll(categories);
            }

            add2CategoriesTree(processData.getCategoriesTree(), parsedDataItem);
            processData.getCategoriesTreeByAcct().put(accountReference, processData.getCategoriesTree());

            processData.getAccountRefs().add(accountReference);
            processData.getAccountCatalogRefs().add(accountCatalogReference);

            List<XpedxCatalogItemView> val = processData.getGroupedByAccountCatalog().get(accountCatalogReference);
            if (val == null) {
                val = new ArrayList<XpedxCatalogItemView>();
                processData.getGroupedByAccountCatalog().put(accountCatalogReference, val);
            }

            val.add(parsedDataItem);

            processData.getDistItemRefs().add(ComparableList.createValue(distReference, itemReference));

            if (Utility.isSet(parsedDataItem.getFreightTableID())) {

                Set<Integer> freightTableIds = processData.getFreightTableIdsByCatalogReference().get(accountCatalogReference);
                if (freightTableIds == null) {
                    freightTableIds = new HashSet<Integer>();
                    processData.getFreightTableIdsByCatalogReference().put(accountCatalogReference, freightTableIds);
                }

                int freightTableId = Utility.parseInt(parsedDataItem.getFreightTableID());
                if (freightTableId > 0) {
                    freightTableIds.add(freightTableId);
                    processData.getAllFreightTableIds().add(freightTableId);
                }

            }

            Set<String> multiProducts = processData.getMultiProductsByAcct().get(accountReference);
            if (multiProducts == null) {
                multiProducts = new HashSet<String>();
                processData.getMultiProductsByAcct().put(accountReference, multiProducts);
            }

            if (Utility.isSet(parsedDataItem.getMultiProductID())) {
                multiProducts.add(parsedDataItem.getMultiProductID());
            }

            if (Utility.isSet(parsedDataItem.getCostCenter()) && Utility.isSet(category)) {
                processData.getCostCenterNamesByAcctRef().put(ComparableList.createValue(accountReference, category), parsedDataItem.getCostCenter());
            }

            if (Utility.isSet(parsedDataItem.getManufacturer())) {
                processData.getAllManufacturers().add(parsedDataItem.getManufacturer());
            }

            processData.getInboundLocales().add(parsedDataItem.getLocale());
            if (Utility.isSet(parsedDataItem.getImage())) {
                processData.getImageUrls().add(parsedDataItem.getImage());
            }

        }

        List<URLResponseDataView> imageDataList = InboundXpedxLoaderHelper.readImage(processData.getImageUrls());
        for (URLResponseDataView o : imageDataList) {
            processData.getInboundImageDataMap().put(o.getURL(), o.getData());
        }

        return processData;
    } */

    public static class ItemAssocObj {
        int item1_id;
        int item2_id;
        int catalog_id;

        public ItemAssocObj() {
        }

        public int getItem1Id() {
            return item1_id;
        }

        public int getItem2Id() {
            return item2_id;
        }

        public int getCatalogId() {
            return catalog_id;
        }

        public void setItem1Id(int i1) {
            this.item1_id = i1;
        }

        public void setItem2Id(int i2) {
            this.item2_id = i2;
        }

        public void setCatalogId(int i3) {
            this.catalog_id = i3;
        }

        public String toString() {
            return "[item1_id=" + item1_id + ", item2_id=" + item2_id + ", catalog_id= " + catalog_id + "]";
        }

    }

}
