package com.cleanwise.service.apps.dataexchange.xpedx;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.apps.dataexchange.InboundFlatFile;
import com.cleanwise.service.apps.dataexchange.InboundXpedxLoaderHelper;
import com.cleanwise.service.apps.dataexchange.Translator;
import org.apache.log4j.Logger;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Pattern;


public class IXCatalogItemLoader extends InboundFlatFile implements Serializable {

    private static final Logger log = Logger.getLogger(IXCatalogItemLoader.class);

    public List<XpedxCatalogItemView> parsedDataList = new ArrayList<XpedxCatalogItemView>();
    public boolean headerWasLoad = false;

    public static final String LOADER_FMT = "Line {0} : {1}";

    private static final String YES = "YES";
    private static final String NO = "NO";


    public static final String N3TAB = "\n\t\t\t";

    public final static String IXCITEMLOADER = "CatalogItemLoader";

    private static final Pattern urlPattern = Pattern.compile("^(ht|f)tp(s?)\\:\\/\\/[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*(:(0-9)*)*(\\/?)([a-zA-Z0-9\\-\\.\\?\\,\\'\\/\\\\\\+&amp;%\\$#_]*)?$");

    private static final int MAX_ITEM_SHORT_DESC_LENGTH = 255;
    private static final int MAX_ITEM_LONG_DESC_LENGTH = 3000;
    private static final int MAX_SHOPPING_RESTRICTIONS_ACTION_LENGTH = 255;
    private static final int MAX_UNSPSC_CD_LENGTH = 10;

    public void init() {
        super.init();
        parsedDataList.clear();
        headerWasLoad = false;
    }

    public IXCatalogItemLoader() {
        super.setSepertorChar('|');
    }

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
        Connection con = null;
        try {

            APIAccess factory = APIAccess.getAPIAccess();
            con = getConnection();

            IXMasterData ixMasterData = getIXMasterData(con);
            if (ixMasterData.hasErrors()) {
                for (String error : ixMasterData.getErrors()) {
                    appendErrorMsgs(error);
                }
                log.info("doPostProcessing()=> Post-processing rejected, found " + ixMasterData.getErrors().size() + " errors.");
                throw new Exception(super.getFormatedErrorMsgs());
            }

            log.info("doPostProcessing()=> No Errors, Go Next");

            AXMasterData axMasterData = getAXMasterData(con, ixMasterData);
            if (axMasterData.hasErrors()) {
                for (String error : axMasterData.getErrors()) {
                    appendErrorMsgs(error);
                }
                log.info("doPostProcessing()=> Post-processing rejected, found " + axMasterData.getErrors().size() + " errors.");
                throw new Exception(super.getFormatedErrorMsgs());
            }

            log.info("doPostProcessing()=> No Errors, Go Next");

            XDBSynchronizer synchronizer = new XDBSynchronizer();
            synchronizer.synchronize(con, factory, ixMasterData, axMasterData);

            if (!ixMasterData.getBadLines().isEmpty()) {
                log.info("doPostProcessing()=> found " + ixMasterData.getBadLines().size() + " bad lines.");
                for (Line<XpedxCatalogItemView> badLine : ixMasterData.getBadLines()) {
                    for (String error : badLine.getErrors()) {
                        appendErrorMsgs(MessageFormat.format(LOADER_FMT, String.valueOf(badLine.getLine()), error));
                    }
                }
                throw new Exception(super.getFormatedErrorMsgs());
            }

        } finally {
            closeConnection(con);
        }

        log.info("doPostProcessing()=> END.");

    }

    private AXMasterData getAXMasterData(Connection pCon, IXMasterData pIxMasterData) throws Exception {

        log.info("getAXMasterData()=> BEGIN");
        AXMasterData axMasterData = new AXMasterData();

        for (MasterCustomerData masterCustomerData : pIxMasterData.getMasterCustomerDataList()) {

            MasterAppData masterAppData = new MasterAppData();

            int storeCatalogId = XpedxLoaderAssist.getStoreCatalogId(pCon, masterCustomerData.getStoreId());

            List<URLResponseDataView> imageDataList = InboundXpedxLoaderHelper.readImage(masterCustomerData.getImageUrls());
            for (URLResponseDataView o : imageDataList) {
                masterAppData.getImageDataMap().put(o.getURL(), o.getData());
            }

            Set<AccountReference> accountReferences = masterCustomerData.getInboundAccountMap().keySet();
            Map<AccountReference, Map<Integer, BusEntityData>> accountReferenceMap =
                    XpedxLoaderAssist.getAccountReferenceMap(pCon,
                            masterCustomerData.getTradingPartnerId(),
                            accountReferences,
                            axMasterData.getErrors());

            HashMap<AccountReference, CatalogData> accountCatalogReferenceMap =
                    XpedxLoaderAssist.getAccountCatalogReferenceMap(pCon,
                            accountReferenceMap,
                            axMasterData.getErrors());


            Set<DistributorReference> distributorReferences = masterCustomerData.getInboundDistributorMap().keySet();
            Map<DistributorReference, BusEntityData> distributorReferenceMap =
                    XpedxLoaderAssist.getDitributorReferenceMap(pCon,
                            masterCustomerData.getTradingPartnerId(),
                            distributorReferences,
                            axMasterData.getErrors());

            Map<ManufacturerReference, BusEntityData> manufacturerReferenceMap =
                    XpedxLoaderAssist.getManufacturerReferenceMap(pCon,
                            masterCustomerData.getStoreId(),
                            axMasterData.getErrors());

            Set<Integer> freightTableIds = masterCustomerData.getFreightTableIds();
            Map<Integer, FreightTableData> freightTablesMap = XpedxLoaderAssist.getFreightTables(pCon, masterCustomerData.getStoreId(), freightTableIds);

            Set<String> locales = masterCustomerData.getLocales();
            Map<String, Integer> localePriceDecimals = XpedxLoaderAssist.getPriceDecimalsMap(pCon, locales);


            masterAppData.setFreightTablesMap(freightTablesMap);
            masterAppData.setLocalePriceDecimals(localePriceDecimals);

            Set<ItemReference> itemReferences = masterCustomerData.getItemReferences();

            Map<ItemReference, ItemMappingData> itemDistributorMap = XpedxLoaderAssist.getItemDistributorMap(pCon, itemReferences, distributorReferenceMap, axMasterData.getErrors());
            Map<ItemReference, ItemMappingData> itemManufMap = XpedxLoaderAssist.getItemManufacturerMap(pCon, itemDistributorMap, manufacturerReferenceMap, axMasterData.getErrors());

            Map<ItemReference, ItemData> itemReferencesMap = XpedxLoaderAssist.getItemDataByDistItemRef(pCon, itemDistributorMap);

            Map<ItemReference, ItemMappingData> itemStoreMap = XpedxLoaderAssist.getItemStoreMap(pCon, masterCustomerData.getStoreId(), itemReferencesMap, axMasterData.getErrors());

            Map<ItemReference, Map<String, ItemMetaData>> itemMetaMap = XpedxLoaderAssist.getItemMetaMap(pCon, itemReferencesMap, axMasterData.getErrors());
            Map<Integer, Map<ItemReference, InventoryItemsData>> inventoryItemsMap = XpedxLoaderAssist.getInventoryItemsByAccountId(pCon, accountReferenceMap, itemReferencesMap);
            Map<Integer, Map<ItemReference, ShoppingControlData>> shoppingControlsMap = XpedxLoaderAssist.getShoppingControlsByAccountId(pCon, accountReferenceMap, itemReferencesMap);
            Map<ItemReference, ContentData> itemContentMap = XpedxLoaderAssist.getItemContent(pCon, itemReferencesMap, masterCustomerData.getInboundItemsMap());

            if (storeCatalogId > 0) {

                Map<Integer, Map<CategoryReference, ItemData>> storeCategoryMap = XpedxLoaderAssist.getCategoryMap(pCon, Utility.toIdVector(storeCatalogId));
                masterAppData.setAppStoreCategoryMap(storeCategoryMap.get(storeCatalogId));

                Map<Integer, Integer> multiProductsMap = XpedxLoaderAssist.getMultiPeoductsMap(pCon, storeCatalogId);
                masterAppData.setMultiProductsMap(multiProductsMap);

                Map<Integer, Map<Integer, CatalogStructureData>> appStoreCatalogCategoryMap = XpedxLoaderAssist.getCatalogStructuresByCatalog(pCon,
                        Utility.toIdVector(storeCatalogId),
                        Utility.getAsList(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY),
                        axMasterData.getErrors());
                masterAppData.setAppStoreCatalogCategoryMap(appStoreCatalogCategoryMap.get(storeCatalogId));

                Map<Integer, Integer> categoryAssocMap = XpedxLoaderAssist.getCategoryParentIdByChildId(pCon, storeCatalogId);
                masterAppData.setAppCategoryAssocMap(categoryAssocMap);

                Map<Integer, Map<Integer, CatalogStructureData>> appStoreCatalogProductMap = XpedxLoaderAssist.getCatalogStructuresByCatalog(pCon,
                        Utility.toIdVector(storeCatalogId),
                        Utility.getAsList(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT),
                        axMasterData.getErrors());
                masterAppData.setAppStoreCatalogProductMap(appStoreCatalogProductMap.get(storeCatalogId));

                Map<Integer, Map<ItemReference, ItemAssocData>> appStoreItemCategoryAssocMap = XpedxLoaderAssist.getItemCategoryAssocMap(pCon,
                        Utility.toIdVector(storeCatalogId),
                        itemDistributorMap,
                        axMasterData.getErrors());

                masterAppData.setAppStoreItemCategoryAssocMap(appStoreItemCategoryAssocMap.get(storeCatalogId));

            }

            for (Map.Entry<AccountReference, Map<Integer, BusEntityData>> eAccount : accountReferenceMap.entrySet()) {

                Map<Integer, BusEntityData> accounts = eAccount.getValue();

                for (BusEntityData account : accounts.values()) {

                    AppAccountReferenceData appAccountReferenceData = masterAppData.getAppAccountReferenceMap().get(eAccount.getKey());
                    if (appAccountReferenceData == null) {
                        appAccountReferenceData = new AppAccountReferenceData();
                        masterAppData.getAppAccountReferenceMap().put(eAccount.getKey(), appAccountReferenceData);
                    }

                    AppAccountData appAccountData = appAccountReferenceData.getAppAcountDataMap().get(account.getBusEntityId());
                    if (appAccountData == null) {
                        appAccountData = new AppAccountData();
                        appAccountReferenceData.getAppAcountDataMap().put(account.getBusEntityId(), appAccountData);
                    }

                    Integer mainDistributorId = null;
                    Map<String, List<CostCenterData>> costCentersMap = new HashMap<String, List<CostCenterData>>();
                    Map<String, Integer> existCategories = new HashMap<String, Integer>();
                    Map<Integer, CatalogStructureData> appAccountCatalogCategoryMap = new HashMap<Integer, CatalogStructureData>();
                    Map<Integer, CatalogStructureData> appAccountCatalogProductMap = new HashMap<Integer, CatalogStructureData>();
                    Map<ItemReference, ItemAssocData> appAccountItemCategoryAssocMap = new HashMap<ItemReference, ItemAssocData>();
                    Set<Integer> shoppingCatalogIds = new HashSet<Integer>();

                    CatalogData accountCatalog = accountCatalogReferenceMap.get(eAccount.getKey());
                    if (accountCatalog != null) {

                        costCentersMap = XpedxLoaderAssist.getAccountCostCentersMap(pCon, accountCatalog.getCatalogId());
                        mainDistributorId = XpedxLoaderAssist.getMainDistributorId(pCon, accountCatalog.getCatalogId());
                        existCategories = XpedxLoaderAssist.getExistCategories(pCon, accountCatalog.getCatalogId(), null);

                        appAccountCatalogCategoryMap = XpedxLoaderAssist.getCatalogStructuresByCatalog(pCon,
                                Utility.toIdVector(accountCatalog.getCatalogId()),
                                Utility.getAsList(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY),
                                axMasterData.getErrors()).get(accountCatalog.getCatalogId());

                        appAccountCatalogProductMap = XpedxLoaderAssist.getCatalogStructuresByCatalog(pCon,
                                Utility.toIdVector(accountCatalog.getCatalogId()),
                                Utility.getAsList(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT),
                                axMasterData.getErrors()).get(accountCatalog.getCatalogId());

                        appAccountItemCategoryAssocMap = XpedxLoaderAssist.getItemCategoryAssocMap(pCon,
                                Utility.toIdVector(accountCatalog.getCatalogId()),
                                itemDistributorMap,
                                axMasterData.getErrors()).get(accountCatalog.getCatalogId());
                        
                        if (appAccountCatalogCategoryMap == null)
                        	appAccountCatalogCategoryMap = new HashMap<Integer, CatalogStructureData>();
                        if (appAccountCatalogProductMap == null)
                        	appAccountCatalogProductMap = new HashMap<Integer, CatalogStructureData>();
                        if (appAccountItemCategoryAssocMap == null)
                        	appAccountItemCategoryAssocMap = new HashMap<ItemReference, ItemAssocData>();

                        shoppingCatalogIds = XpedxLoaderAssist.getShoppingCatalogIdsFor(pCon, accountCatalog.getCatalogId());

                    }

                    Set<CatalogReference> catalogReferences = masterCustomerData.getCatalogLoaderFields(eAccount.getKey());
                    if (!catalogReferences.isEmpty() && appAccountReferenceData.getAppCatalogMap().isEmpty()) {

                        Map<CatalogReference, CatalogData> catalogReferenceMap =
                                XpedxLoaderAssist.getCatalogReferenceMap(pCon,
                                        masterCustomerData.getTradingPartnerId(),
                                        eAccount.getKey(),
                                        catalogReferences,
                                        axMasterData.getErrors());

                        Map<Integer, Map<Integer, Map<String, CatalogAssocData>>> catalogAssocMap = XpedxLoaderAssist.getBusEntsCatalogAssocByCatalogId(pCon,
                                Utility.toIdVector(new ArrayList<CatalogData>(catalogReferenceMap.values())),
                                axMasterData.getErrors());

                        IdVector catalogIds = Utility.toIdVector(new ArrayList<CatalogData>(catalogReferenceMap.values()));

                        Map<Integer, ContractData> contractMap = XpedxLoaderAssist.getContracts(pCon, catalogIds);

                        Map<Integer, Map<Integer, CatalogStructureData>> appShoppingCatalogCategoryMap = XpedxLoaderAssist.getCatalogStructuresByCatalog(pCon,
                                catalogIds,
                                Utility.getAsList(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY),
                                axMasterData.getErrors());

                        Map<Integer, Map<Integer, CatalogStructureData>> appShoppingCatalogProductMap = XpedxLoaderAssist.getCatalogStructuresByCatalog(pCon,
                                catalogIds,
                                Utility.getAsList(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT),
                                axMasterData.getErrors());

                        Map<Integer, Map<ItemReference, ItemAssocData>> itemCategoryAssocMap = XpedxLoaderAssist.getItemCategoryAssocMap(pCon,
                                catalogIds,
                                itemDistributorMap,
                                axMasterData.getErrors());

                        Map<Integer, Map<ItemReference, ContractItemData>> contractItemsMap =
                                XpedxLoaderAssist.getContractItemsByCatalogId(pCon,
                                        contractMap,
                                        itemReferencesMap,
                                        axMasterData.getErrors());

                        Map<CatalogReference, AppCatalogData> appCatalogDataMap = appAccountReferenceData.getAppCatalogMap();
                        for (CatalogReference catRef : catalogReferences) {

                            AppCatalogData appCatalogData = appCatalogDataMap.get(catRef);
                            if (appCatalogData == null) {
                                appCatalogData = new AppCatalogData();
                                appCatalogDataMap.put(catRef, appCatalogData);
                            }

                            CatalogData catalog = catalogReferenceMap.get(catRef);
                            if (catalog != null) {
                                appCatalogData.setCatalog(catalog);
                                appCatalogData.setCatalogAssocMap(catalogAssocMap.get(catalog.getCatalogId()));
                                appCatalogData.setContract(contractMap.get(catalog.getCatalogId()));
                                appCatalogData.setAppShoppingCatalogCategoryMap(appShoppingCatalogCategoryMap.get(catalog.getCatalogId()));
                                appCatalogData.setAppShoppingCatalogProductMap(appShoppingCatalogProductMap.get(catalog.getCatalogId()));
                                appCatalogData.setAppShoppingItemCategoryAssocMap(itemCategoryAssocMap.get(catalog.getCatalogId()));
                                appCatalogData.setAppContractItemsMap(contractItemsMap.get(catalog.getCatalogId()));
                            }

                            Set<ItemReference> inbountdItemReferences = masterCustomerData.getItemReferences(eAccount.getKey(), catRef);
                            for (ItemReference itemRef : inbountdItemReferences) {
                                AppItemData appItemData = new AppItemData();
                                appItemData.setItemData(itemReferencesMap.get(itemRef));
                                appItemData.setDistributor(itemDistributorMap.get(itemRef));
                                appItemData.setStore(itemStoreMap.get(itemRef));
                                appItemData.setManufacturer(itemManufMap.get(itemRef));
                                appItemData.setMeta(itemMetaMap.get(itemRef));
                                appItemData.setImage(itemContentMap.get(itemRef));
                                masterAppData.getAppItemMap().put(itemRef, appItemData);
                            }

                        }
                    }

                    appAccountData.setAccount(account);
                    appAccountData.setAccountCatalog(accountCatalog != null ? accountCatalog.getCatalogId() : -1);
                    appAccountData.setMainDistributorId(mainDistributorId);
                    appAccountData.setExistCategories(existCategories);
                    appAccountData.setCostCentersMap(costCentersMap);
                    appAccountData.setAppAccountCatalogCategoryMap(appAccountCatalogCategoryMap);
                    appAccountData.setAppAccountCatalogProductMap(appAccountCatalogProductMap);
                    appAccountData.setAppAccountItemCategoryAssocMap(appAccountItemCategoryAssocMap);
                    appAccountData.setInventoryItemsMap(inventoryItemsMap.get(account.getBusEntityId()));
                    appAccountData.setShoppingControlsMap(shoppingControlsMap.get(account.getBusEntityId()));
                    appAccountData.setShoppingCatalogIdSet(shoppingCatalogIds);
                }
            }

            masterAppData.setStoreCatalogId(storeCatalogId);
            masterAppData.setStoreId(masterCustomerData.getStoreId());
            masterAppData.setTradingPartnerId(masterCustomerData.getTradingPartnerId());
            masterAppData.setAppManufacturerMap(manufacturerReferenceMap);
            masterAppData.setAppDistributorMap(distributorReferenceMap);

            axMasterData.getMasterAppDataList().add(masterAppData);

        }
        log.info("getAXMasterData()=> END.");

        return axMasterData;
    }

    public IXMasterData getIXMasterData(Connection pCon) throws Exception {

        log.debug("getIXMasterData()=> BEGIN");
        log.debug("getIXMasterDatat()=> loader implementation ignore the master customer field!");

        IXMasterData ixMasterData = new IXMasterData();

        Translator translator = getTranslator();
        TradingPartnerData partner = translator.getPartner();

        int storeId = XpedxLoaderAssist.getStoreId(pCon, translator);

        MasterCustomerData masterCustonmerData = new MasterCustomerData(storeId, partner.getTradingPartnerId());

        int line = 0;
        for (XpedxCatalogItemView parsedDataItem : parsedDataList) {

            ++line;

            AccountReference accountReference = new AccountReference(parsedDataItem.getAccountNumber());
            DistributorReference distributorReference = new DistributorReference(parsedDataItem.getDistributor());
            ManufacturerReference manufacturerReference = new ManufacturerReference(parsedDataItem.getManufacturer());
            CatalogReference catalogReference = new CatalogReference(parsedDataItem.getAccountNumber(), parsedDataItem.getCatalogID());
            ItemReference itemReference = new ItemReference(parsedDataItem.getDistributor(), parsedDataItem.getDistSKU());

            log.debug("getIXMasterDatat()=> LINE: " + line + "" +
                    N3TAB + "    XXX      MaserCustomer: " + parsedDataItem.getMasterCustomerName() + "" +
                    N3TAB + "    XXX      AccountNumber: " + parsedDataItem.getAccountNumber() +
                    N3TAB + "    XXX      Distributor: " + parsedDataItem.getDistributor() +
                    N3TAB + "    XXX      Manufacturer: " + parsedDataItem.getManufacturer() +
                    N3TAB + "    XXX      CatalogID: " + parsedDataItem.getCatalogID() +
                    N3TAB + "    XXX      accountReference: " + accountReference + "" +
                    N3TAB + "    XXX      distributorReference: " + distributorReference +
                    N3TAB + "    XXX      manufacturerReference: " + manufacturerReference +
                    N3TAB + "    XXX      catalogReference: " + catalogReference +
                    N3TAB + "    XXX      itemReference: " + itemReference);

            try {

                ixMasterData.addInboundLine(line, parsedDataItem);

                List<String> lineErrors = checkLine(parsedDataItem);
                if (lineErrors.isEmpty()) {

                    if (!masterCustonmerData.getInboundAccountMap().containsKey(accountReference)) {
                        InboundAccountData inAccData = new InboundAccountData();
                        inAccData.setAccountNumber(parsedDataItem.getAccountNumber());
                        masterCustonmerData.getInboundAccountMap().put(accountReference, inAccData);
                    }

                    if (!masterCustonmerData.getInboundDistributorMap().containsKey(distributorReference)) {
                        InboundDistributorData inDistrData = new InboundDistributorData();
                        inDistrData.setDistributorName(parsedDataItem.getDistributor());
                        masterCustonmerData.getInboundDistributorMap().put(distributorReference, inDistrData);
                    }

                    if (!masterCustonmerData.getInboundManufacturerMap().containsKey(manufacturerReference)) {
                        InboundManufacturerData inManufData = new InboundManufacturerData();
                        inManufData.setManufacturerName(parsedDataItem.getManufacturer());
                        masterCustonmerData.getInboundManufacturerMap().put(manufacturerReference, inManufData);
                    }

                    InboundAccountData inAccData = masterCustonmerData.getInboundAccountMap().get(accountReference);
                    if (!inAccData.getInboundCatalogMap().containsKey(catalogReference)) {
                        InboundCatalogData inCatalogData = new InboundCatalogData();
                        inCatalogData.setLocale(parsedDataItem.getLocale());
                        inCatalogData.setLoaderField(parsedDataItem.getCatalogID());
                        inAccData.getInboundCatalogMap().put(catalogReference, inCatalogData);
                    }

                    InboundCatalogData inCatalogData = inAccData.getInboundCatalogMap().get(catalogReference);

                    if (!inCatalogData.getInboundItemMap().containsKey(itemReference)) {

                        InboundItemData inItemData = new InboundItemData();

                        inItemData.setAutoOrderItem(XpedxLoaderAssist.toBoolean(parsedDataItem.getAutoOrderItem()));
                        inItemData.setCategory(parsedDataItem.getCategory());
                        inItemData.setColor(parsedDataItem.getColor());
                        inItemData.setCost(XpedxLoaderAssist.toBigDecimal(parsedDataItem.getCost()));
                        inItemData.setCostCenter(parsedDataItem.getCostCenter());
                        inItemData.setDistPack(parsedDataItem.getDistPack());
                        inItemData.setDistributor(parsedDataItem.getDistributor());
                        inItemData.setDistSKU(parsedDataItem.getDistSKU());
                        inItemData.setDistUOM(parsedDataItem.getDistUOM());
                        inItemData.setFreightTableID(XpedxLoaderAssist.toInteger(parsedDataItem.getFreightTableID()));
                        inItemData.setHazmat(XpedxLoaderAssist.toBoolean(parsedDataItem.getHazmat()));
                        inItemData.setImage(parsedDataItem.getImage());
                        inItemData.setInventoryItems(XpedxLoaderAssist.toBoolean(parsedDataItem.getInventoryItems()));
                        inItemData.setListPrice(parsedDataItem.getListPrice());
                        inItemData.setLongDescription(parsedDataItem.getLongDescription());
                        inItemData.setManufacturer(parsedDataItem.getManufacturer());
                        inItemData.setMfgSKU(parsedDataItem.getMfgSKU());
                        inItemData.setPack(parsedDataItem.getPack());
                        inItemData.setPackUPC(parsedDataItem.getPackUPC());
                        inItemData.setPrice(XpedxLoaderAssist.toBigDecimal(parsedDataItem.getPrice()));
                        inItemData.setProductUPC(parsedDataItem.getProductUPC());
                        inItemData.setServiceCode(parsedDataItem.getServiceCode());
                        inItemData.setShippingCubicSize(parsedDataItem.getShippingCubicSize());
                        inItemData.setShippingWeight(parsedDataItem.getShippingWeight());
                        inItemData.setShoppingMaxQTY(XpedxLoaderAssist.toInteger(parsedDataItem.getShoppingMaxQTY()));
                        inItemData.setShoppingRestrictionDays(XpedxLoaderAssist.toInteger(parsedDataItem.getShoppingRestrictionDays()));
                        inItemData.setShortDescription(parsedDataItem.getShortDescription());
                        inItemData.setSize(parsedDataItem.getSize());
                        inItemData.setSubCat1(parsedDataItem.getSubCat1());
                        inItemData.setSubCat2(parsedDataItem.getSubCat2());
                        inItemData.setSubCat3(parsedDataItem.getSubCat3());
                        inItemData.setCustItemDesc(parsedDataItem.getCustItemDesc());
                        inItemData.setCustItemNum(parsedDataItem.getCustItemNum());
                        inItemData.setSpecialPermission(XpedxLoaderAssist.toBoolean(parsedDataItem.getSpecialPermission()));
                        inItemData.setShoppingRestrictionsAction(parsedDataItem.getShoppingRestrictionsAction());
                        inItemData.setUnspscCd(parsedDataItem.getUnspscCd());
                        inCatalogData.getInboundItemMap().put(itemReference, inItemData);

                        ixMasterData.addGoodLine(line, parsedDataItem);

                    } else {
                        String logMessage = "Found duplicated item in catalog { \n" +
                                " MasterCustomerName: " + parsedDataItem.getMasterCustomerName() +
                                ", AccountNimber:" + parsedDataItem.getAccountNumber() +
                                ", CatalogID:" + parsedDataItem.getCatalogID() +
                                ", Distributor:" + parsedDataItem.getDistributor() + "\n }";
                        log.debug("getIXMasterData()=> " + MessageFormat.format(LOADER_FMT, String.valueOf(line), logMessage));
                        ixMasterData.addBadLine(line, parsedDataItem, Utility.getAsList(logMessage));
                    }

                } else {
                    ixMasterData.addBadLine(line, parsedDataItem, lineErrors);
                }


            } catch (Exception e) {
                log.error("getIXMasterData()=> ERROR: " + e.getMessage(), e);
                ixMasterData.addBadLine(line, parsedDataItem, Utility.getAsList(e.getMessage()));
            }

            log.debug("getIXMasterData()=> GO NEXT");

        }


        Map<CategoryReference,
                Map<CategoryReference,
                        Map<CategoryReference,
                                Set<CategoryReference>>>> categoriesTree;

        categoriesTree = XpedxLoaderAssist.createsCategoryTree(ixMasterData.getGoodLines());

        ArrayList<String> errors = checkCategoriesTree(categoriesTree);
        if (!errors.isEmpty()) {
            ixMasterData.getErrors().addAll(errors);
        }

        ixMasterData.add(masterCustonmerData);

        log.debug("getIXMasterData()=> END, Line.Proc: " + line);

        return ixMasterData;

    }

    private List<String> checkLine(XpedxCatalogItemView pItem) {

        List<String> errors = new ArrayList<String>();

        if (!Utility.isSet(pItem.getMasterCustomerName())) {
            errors.add("Field 'Master Customer Name' requires information");
        }

        if (!Utility.isSet(pItem.getAccountNumber())) {
            errors.add("Field 'Account Number' requires information");
        }

        if (!Utility.isSet(pItem.getCatalogID())) {
            errors.add("Field 'Catalog ID' requires information");

        }

        if (!Utility.isSet(pItem.getLocale())) {
            errors.add("Field 'Locale' requires information");
        }

        if (!Utility.isSet(pItem.getDistSKU())) {
            errors.add("Field 'Dist SKU' requires information");
        }

        if (!Utility.isSet(pItem.getMfgSKU())) {
            errors.add("Field 'Mfg SKU' requires information");
        }

        if (!Utility.isSet(pItem.getManufacturer())) {
            errors.add("Field 'Manufacturer' requires information");
        }

        if (!Utility.isSet(pItem.getDistributor())) {
            errors.add("Field 'Distributor' requires information");
        }

        if (!Utility.isSet(pItem.getPack())) {
            errors.add("Field 'Pack' requires information");
        }

        if (!Utility.isSet(pItem.getUOM())) {
            errors.add("Field 'UOM' requires information");
        }

        if (!Utility.isSet(pItem.getCost())) {
            errors.add("Field 'Cost' requires information");
        }

        if (!Utility.isSet(pItem.getPrice())) {
            errors.add("Field 'Price' requires information");
        }

        if (!Utility.isSet(pItem.getCategory())) {
            errors.add("Field 'Category' requires information");
        }

        if (!Utility.isSet(pItem.getShortDescription())) {
            errors.add("Field 'Short Description' requires information");
        } else if (pItem.getShortDescription().length() > MAX_ITEM_SHORT_DESC_LENGTH) {
            errors.add("Field 'Short Description' is too long. Max.Length - " + MAX_ITEM_SHORT_DESC_LENGTH + " symbols, Curr.Length - " + pItem.getShortDescription().length());
        }

        if (!Utility.isSet(pItem.getLongDescription())) {
            errors.add("Field 'Long Description' requires information");
        } else if (pItem.getLongDescription().length() > MAX_ITEM_LONG_DESC_LENGTH) {
            errors.add("Field 'Long Description' is too long. Max.Length - " + MAX_ITEM_LONG_DESC_LENGTH + " symbols, Curr.Length - " + pItem.getLongDescription().length());
        }


        if (!Utility.isSet(pItem.getSize())) {
            errors.add("Field 'Size' requires information");
        }

        if (Utility.isSet(pItem.getShoppingMaxQTY())
                && !Utility.isSet(pItem.getShoppingRestrictionDays())) {
            errors.add("Field 'Shopping Restriction Days' requires information");
        }
        
        if (Utility.isSet(pItem.getShoppingMaxQTY())
                && !Utility.isSet(pItem.getShoppingRestrictionsAction())) {
            errors.add("Field 'Shopping Restriction Action' requires information");
        }

        if (Utility.isSet(pItem.getShoppingRestrictionsAction()) && pItem.getShoppingRestrictionsAction().length() > MAX_SHOPPING_RESTRICTIONS_ACTION_LENGTH){
            errors.add("Field 'Shopping Action' is too long. Max.Length - " + MAX_SHOPPING_RESTRICTIONS_ACTION_LENGTH + " symbols, Curr.Length - " + pItem.getShoppingRestrictionsAction().length());
        }
        
        if (!Utility.isSet(pItem.getInventoryItems())) {
            errors.add("Field 'Inventory Items' requires information");
        }

        if (!Utility.isSet(pItem.getSpecialPermission())) {
            errors.add("Field 'Admin Only Item' requires information");
        }

        if (!Utility.isSet(pItem.getAutoOrderItem())) {
            errors.add("Field 'Auto Order Item' requires information");
        }

        if (Utility.isSet(pItem.getPrice())) {
            try {
                new BigDecimal(pItem.getPrice());
            } catch (Exception e) {
                errors.add("Incorrect 'Price' field: " + pItem.getPrice());
            }
        }

        if (Utility.isSet(pItem.getCost())) {
            try {
                new BigDecimal(pItem.getCost());
            } catch (Exception e) {
                errors.add("Incorrect 'Cost' field: " + pItem.getCost());
            }
        }

        if (Utility.isSet(pItem.getListPrice())) {
            try {
                new BigDecimal(pItem.getListPrice());
            } catch (Exception e) {
                errors.add("Incorrect 'List Price' field: " + pItem.getListPrice());
            }
        }

        if (Utility.isSet(pItem.getFreightTableID())) {
            try {
                Integer.parseInt(pItem.getFreightTableID());
            } catch (Exception e) {
                errors.add("Incorrect 'Freight Table ID' field: " + pItem.getFreightTableID());
            }
        }

        if (Utility.isSet(pItem.getShoppingMaxQTY())) {
            try {
                Integer.parseInt(pItem.getShoppingMaxQTY());
            } catch (Exception e) {
                errors.add("Incorrect 'Shopping Max QTY' field: " + pItem.getShoppingMaxQTY());
            }
        }

        if (Utility.isSet(pItem.getShoppingRestrictionDays())) {
            try {
                Integer.parseInt(pItem.getShoppingRestrictionDays());
            } catch (Exception e) {
                errors.add("Incorrect 'Shopping Restriction Days' field: " + pItem.getShoppingRestrictionDays());
            }
        }

        if (Utility.isSet(pItem.getMultiProductID())) {
            try {
                Integer.parseInt(pItem.getMultiProductID());
            } catch (Exception e) {
                errors.add("Incorrect 'Multi Product ID' field: " + pItem.getMultiProductID());
            }
        }

        if (Utility.isSet(pItem.getInventoryItems())) {
            try {
                Boolean.parseBoolean(pItem.getInventoryItems());
            } catch (Exception e) {
                errors.add("Incorrect 'Inventory Items' field: " + pItem.getInventoryItems());
            }
        }

        if (Utility.isSet(pItem.getSpecialPermission())) {
            try {
                Boolean.parseBoolean(pItem.getSpecialPermission());
            } catch (Exception e) {
                errors.add("Incorrect 'Admin Only Item' field: " + pItem.getSpecialPermission());
            }
        }

        if (Utility.isSet(pItem.getAutoOrderItem())) {
            try {
                Boolean.parseBoolean(pItem.getAutoOrderItem());
            } catch (Exception e) {
                errors.add("Incorrect 'Auto Order Item' field: " + pItem.getAutoOrderItem());
            }
        }

        if (Utility.isSet(pItem.getHazmat())) {
            if (!pItem.getHazmat().equalsIgnoreCase(YES)
                    && !pItem.getHazmat().equalsIgnoreCase(NO)) {
                errors.add("Incorrect 'Hazmat' field: " + pItem.getHazmat());
            }
        }

        if (Utility.isSet(pItem.getImage())) {
            if (!urlPattern.matcher(pItem.getImage()).matches()) {
                errors.add("Incorrect 'Image URL' format. Image URL: " + pItem.getImage());
            }
        }
        
        if (Utility.isSet(pItem.getUnspscCd()) && pItem.getUnspscCd().length() > MAX_UNSPSC_CD_LENGTH) {
            errors.add("Field 'UNSPSC CD' is too long. Max.Length - " + MAX_UNSPSC_CD_LENGTH + " symbols, Curr.Length - " + pItem.getUnspscCd().length());
        }

        return errors;

    }

    private ArrayList<String> checkCategoriesTree(Map<CategoryReference, Map<CategoryReference, Map<CategoryReference, Set<CategoryReference>>>> pCategoriesTree) {

        log.info("checkCategoriesTree()=> BEGIN ");

        ArrayList<String> errors = new ArrayList<String>();

        Map<CategoryReference, CategoryReference> level1 = new TreeMap<CategoryReference, CategoryReference>();
        Map<CategoryReference, CategoryReference> level2 = new TreeMap<CategoryReference, CategoryReference>();
        Map<CategoryReference, CategoryReference> level3 = new TreeMap<CategoryReference, CategoryReference>();

        for (Map.Entry<CategoryReference, Map<CategoryReference, Map<CategoryReference, Set<CategoryReference>>>> parentTree : pCategoriesTree.entrySet()) {

            CategoryReference parent = parentTree.getKey();

            for (Map.Entry<CategoryReference, Map<CategoryReference, Set<CategoryReference>>> children1Tree : parentTree.getValue().entrySet()) {

                CategoryReference child1 = children1Tree.getKey();

                if (level1.get(child1) == null) {
                    level1.put(child1, parent);
                } else {
                    errors.add("Category '" + parent + "/" + child1 + "' already exists (" + level1.get(child1) + "/" + child1 + ").");
                }

                for (Map.Entry<CategoryReference, Set<CategoryReference>> children2Tree : children1Tree.getValue().entrySet()) {

                    CategoryReference child2 = children2Tree.getKey();

                    if (level2.get(child2) == null) {
                        level2.put(child2, child1);
                    } else {
                        errors.add("Category '" + child1 + "/" + child2 + "' already exists (" + level2.get(child2) + "/" + child1 + ").");
                    }


                    for (CategoryReference child3 : children2Tree.getValue()) {

                        if (level3.get(child3) == null) {
                            level3.put(child3, child2);
                        } else {
                            errors.add("Category '" + child2 + "/" + child3 + "' already exists (" + level3.get(child3) + "/" + child2 + ").");
                        }

                    }


                }

            }

        }

        log.info("checkCategoriesTree()=> END.");

        return errors;
    }


}
