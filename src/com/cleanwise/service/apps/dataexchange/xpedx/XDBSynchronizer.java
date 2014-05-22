package com.cleanwise.service.apps.dataexchange.xpedx;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.CatalogAssocDataAccess;
import com.cleanwise.service.api.dao.CatalogDataAccess;
import com.cleanwise.service.api.dao.CatalogStructureDataAccess;
import com.cleanwise.service.api.dao.ContentDataAccess;
import com.cleanwise.service.api.dao.ContractDataAccess;
import com.cleanwise.service.api.dao.ContractItemDataAccess;
import com.cleanwise.service.api.dao.InventoryItemsDataAccess;
import com.cleanwise.service.api.dao.ItemAssocDataAccess;
import com.cleanwise.service.api.dao.ItemDataAccess;
import com.cleanwise.service.api.dao.ItemMappingDataAccess;
import com.cleanwise.service.api.dao.ItemMetaDataAccess;
import com.cleanwise.service.api.dao.ShoppingControlDataAccess;
import com.cleanwise.service.api.session.Manufacturer;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.IOUtilities;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.CatalogAssocData;
import com.cleanwise.service.api.value.CatalogAssocDataVector;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.CatalogStructureData;
import com.cleanwise.service.api.value.CatalogStructureDataVector;
import com.cleanwise.service.api.value.ContentData;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.ContractItemData;
import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.FreightTableData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.InventoryItemsData;
import com.cleanwise.service.api.value.ItemAssocData;
import com.cleanwise.service.api.value.ItemAssocDataVector;
import com.cleanwise.service.api.value.ItemData;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.ItemMappingData;
import com.cleanwise.service.api.value.ItemMappingDataVector;
import com.cleanwise.service.api.value.ItemMetaData;
import com.cleanwise.service.api.value.ItemMetaDataVector;
import com.cleanwise.service.api.value.ManufacturerData;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.ShoppingControlData;
import com.cleanwise.service.api.value.XpedxCatalogItemView;
import com.cleanwise.service.apps.dataexchange.InboundXpedxLoaderHelper;

public class XDBSynchronizer implements Serializable {

    private static final String UNKNOWN = "UNKNOWN";
    private static final String ZERO = "0";
    private static final String ITEM_IMAGE ="ItemImage";

    private static final Logger log = Logger.getLogger(XDBSynchronizer.class);

    public void synchronize(Connection pCon,
                            APIAccess pAPIAccess,
                            IXMasterData pIxMasterData,
                            AXMasterData pAxMasterData) throws Exception {

        log.debug("synchronize()=> BEGIN");

        List<Line<XpedxCatalogItemView>> lines = pIxMasterData.getGoodLines();

        Iterator<Line<XpedxCatalogItemView>> lineIt = lines.iterator();

        while (lineIt.hasNext()) {


            Line<XpedxCatalogItemView> line = lineIt.next();

            //log.debug("synchronize()=> LINE: " + line.getLine());

            try {

                XpedxCatalogItemView item = line.getItem();

                //log.debug("synchronize()=> ITEM: " + item);

                AccountReference accountReference = new AccountReference(item.getAccountNumber());
                DistributorReference distributorReference = new DistributorReference(item.getDistributor());
                ManufacturerReference manufacturerReference = new ManufacturerReference(item.getManufacturer());
                CatalogReference catalogReference = new CatalogReference(item.getAccountNumber(), item.getCatalogID());
                ItemReference itemReference = new ItemReference(item.getDistributor(), item.getDistSKU());

                MasterCustomerData masterCustomerData = getMasterCustomerData(pIxMasterData);
                MasterAppData masterAppData = getMasterAppData(pAxMasterData);

                AppAccountReferenceData appAccountReferenceMap = masterAppData.getAppAccountReferenceMap().get(accountReference);
                if (appAccountReferenceMap == null) {
                    throw new Exception("Not found account(s) for account reference: " + accountReference);
                }

                Map<Integer, AppAccountData> appAccoountMap = masterAppData.getAppAccountReferenceMap().get(accountReference).getAppAcountDataMap();
                Map<Integer, Integer> multiProductMap = masterAppData.getMultiProductsMap();
                Map<Integer, FreightTableData> freightTableMap = masterAppData.getFreightTablesMap();
                BusEntityData distributor = masterAppData.getAppDistributorMap().get(distributorReference);

                for (Map.Entry<Integer/*accoountID*/, AppAccountData> appAccountEntry : appAccoountMap.entrySet()) {

                    checkLine(item, masterAppData, distributorReference, accountReference, appAccountEntry.getKey());

                    Map<String, List<CostCenterData>> costCentersMap = masterAppData.getAppAccountReferenceMap().get(accountReference).getAppAcountDataMap().get(appAccountEntry.getKey()).getCostCentersMap();

                    Integer costCenterId = Utility.isSet(item.getCostCenter())
                            ? costCentersMap.get(item.getCostCenter()).get(0).getCostCenterId()
                            : null;

                    Integer multiProductId = Utility.isSet(item.getMultiProductID())
                            ? multiProductMap.get(Utility.parseInt(item.getMultiProductID()))
                            : null;

                    Integer freightTableId = Utility.isSet(item.getFreightTableID())
                            ? freightTableMap.get(Utility.parseInt(item.getFreightTableID())).getFreightTableId()
                            : null;

                    Boolean specialPermission = Utility.isSet(item.getSpecialPermission())
                            ? Boolean.parseBoolean(item.getSpecialPermission())
                            : null;

                    ///////////////////////////////////////////////////////////////////
                    StoreCatalogSynchronizeData storeCatalogSynchronizeData =
                            new StoreCatalogSynchronizeData(
                                    masterAppData,
                                    masterCustomerData.getStoreId()
                            );

                    if (!storeCatalogSynchronizeData.isSynchronized()) {
                        StoreCatalogDBAgent dbAgent = new StoreCatalogDBAgent(pCon, storeCatalogSynchronizeData);
                        dbAgent.doWork();
                    }

                    ////////////////////////////////////////////////////////////////////////
                    AccountCatalogSynchronizeData accountCatalogSynchronizeData =
                            new AccountCatalogSynchronizeData(
                                    masterAppData,
                                    accountReference,
                                    appAccountEntry.getKey());

                    if (!accountCatalogSynchronizeData.isSynchronized()) {
                        AccountCatalogDBAgent dbAgent = new AccountCatalogDBAgent(pCon, accountCatalogSynchronizeData);
                        dbAgent.doWork();
                    }

                    ////////////////////////////////////////////////////////////////////////
                    ManufacturerSynchronizeData manufacturerSynchronizeData =
                            new ManufacturerSynchronizeData(
                                    masterAppData,
                                    masterCustomerData.getStoreId(),
                                    manufacturerReference
                            );

                    if (!manufacturerSynchronizeData.isSynchronized()) {
                        DBWorker dbAgent = new ManufacturerDBAgent(pAPIAccess.getManufacturerAPI(), manufacturerSynchronizeData);
                        dbAgent.doWork();
                    }

                    ////////////////////////////////////////////////////////////////////////
                    ShoppingCatalogSynchronizeData catalogSynchronizeData =
                            new ShoppingCatalogSynchronizeData(
                                    masterAppData,
                                    masterCustomerData.getStoreId(),
                                    accountReference,
                                    catalogReference);

                    if (!catalogSynchronizeData.isSynchronized()) {
                        DBWorker dbAgent = new ShoppingCatalogDBAgent(pCon, catalogSynchronizeData);
                        dbAgent.doWork();
                    }

                    CatalogData catalog = masterAppData.getAppAccountReferenceMap().get(accountReference).getAppCatalogMap().get(catalogReference).getCatalog();

                    ////////////////////////////////////////////////////////////////////////
                    ContractSynchronizeData contractSynchronizeData =
                            new ContractSynchronizeData(
                                    masterAppData,
                                    catalog,
                                    item.getLocale(),
                                    freightTableId,
                                    accountReference,
                                    catalogReference);

                    if (!contractSynchronizeData.isSynchronized()) {
                        DBWorker dbAgent = new ContractDBAgent(pCon, contractSynchronizeData);
                        dbAgent.doWork();
                    }

                    ////////////////////////////////////////////////////////////////////////

                    Map<DistributorReference, BusEntityData> appDistributorMap = masterAppData.getAppDistributorMap();
                    Integer mainDistributor = masterAppData.getAppAccountReferenceMap().get(accountReference).getAppAcountDataMap().get(appAccountEntry.getKey()).getMainDistributorId();

                    CatalogAssocSynchronizeData catalogAssocSynchronizeData =
                            new CatalogAssocSynchronizeData(
                                    masterAppData,
                                    masterCustomerData.getStoreId(),
                                    catalog.getCatalogId(),
                                    RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE,
                                    accountReference,
                                    catalogReference);

                    if (!catalogAssocSynchronizeData.isSynchronized()) {
                        DBWorker dbAgent = new CatalogAssocDBAgent(pCon, catalogAssocSynchronizeData);
                        dbAgent.doWork();
                    }

                    catalogAssocSynchronizeData =
                            new CatalogAssocSynchronizeData(
                                    masterAppData,
                                    appDistributorMap.get(distributorReference).getBusEntityId(),
                                    catalog.getCatalogId(),
                                    RefCodeNames.CATALOG_ASSOC_CD.CATALOG_DISTRIBUTOR,
                                    accountReference,
                                    catalogReference);

                    if (!catalogAssocSynchronizeData.isSynchronized()) {
                        DBWorker dbAgent = new CatalogAssocDBAgent(pCon, catalogAssocSynchronizeData);
                        dbAgent.doWork();
                    }

                    catalogAssocSynchronizeData =
                            new AccountCatalogAssocSynchronizeData(
                                    masterAppData,
                                    appAccountEntry.getKey(),
                                    catalog.getCatalogId(),
                                    accountReference,
                                    catalogReference);

                    if (!catalogAssocSynchronizeData.isSynchronized()) {
                        DBWorker dbAgent = new CatalogAssocDBAgent(pCon, catalogAssocSynchronizeData);
                        dbAgent.doWork();
                    }

                    if (mainDistributor != null) {

                        catalogAssocSynchronizeData =
                                new CatalogAssocSynchronizeData(
                                        masterAppData,
                                        mainDistributor,
                                        catalog.getCatalogId(),
                                        RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR,
                                        accountReference,
                                        catalogReference);

                        if (!catalogAssocSynchronizeData.isSynchronized()) {
                            DBWorker dbAgent = new CatalogAssocDBAgent(pCon, catalogAssocSynchronizeData);
                            dbAgent.doWork();
                        }
                    }

                    List<String> itemCategories = XpedxLoaderAssist.getItemCategories(item);
                    List<CategoryReference> categoryReferencies = XpedxLoaderAssist.createCategoryReferences(item.getMasterCustomerName(), itemCategories);

                    for (int i = 0; i < categoryReferencies.size(); i++) {

                        CategoryReference categoryReference = categoryReferencies.get(i);

                        ////////////////////////////////////////////////////////////////////////
                        CategorySynchronizeData storeCategorySynchronizeData =
                                new StoreCategorySynchronizeData(
                                        masterAppData,
                                        masterAppData.getStoreCatalogId(),
                                        categoryReference);

                        if (!storeCategorySynchronizeData.isSynchronized()) {
                            DBWorker dbAgent = new CategoryDBAgent(pCon, storeCategorySynchronizeData);
                            dbAgent.doWork();
                        }

                        {  ////////////////////////////////////////////////////////////////////////
                            CatalogStructureSynchronizeData catalogCategorySynchronizeData =
                                    new StoreCatalogStructureSynchronizeData(
                                            masterAppData,
                                            storeCategorySynchronizeData.getValue(),
                                            RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY,
                                            masterAppData.getStoreCatalogId());

                            if (!catalogCategorySynchronizeData.isSynchronized()) {
                                DBWorker dbAgent = new CatalogStructureDBAgent(pCon, catalogCategorySynchronizeData);
                                dbAgent.doWork();
                            }

                        }


                        {
                            ////////////////////////////////////////////////////////////////////////

                            ////////////////////////////////////////////////////////////////////////
                            CatalogStructureSynchronizeData catalogCategorySynchronizeData =
                                    new AccountCatalogStructureSynchronizeData(
                                            masterAppData,
                                            storeCategorySynchronizeData.getValue(),
                                            RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY,
                                            costCenterId,
                                            accountCatalogSynchronizeData.getValue(),
                                            accountReference,
                                            appAccountEntry.getKey());

                            if (!catalogCategorySynchronizeData.isSynchronized()) {
                                DBWorker dbAgent = new CatalogStructureDBAgent(pCon, catalogCategorySynchronizeData);
                                dbAgent.doWork();
                            }

                        }

                        {
                            ////////////////////////////////////////////////////////////////////////
                            CatalogStructureSynchronizeData catalogCategorySynchronizeData =
                                    new ShoppingCatalogStructureSynchronizeData(
                                            masterAppData,
                                            storeCategorySynchronizeData.getValue(),
                                            RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY,
                                            costCenterId,
                                            catalog.getCatalogId(),
                                            accountReference,
                                            catalogReference);

                            if (!catalogCategorySynchronizeData.isSynchronized()) {
                                DBWorker dbAgent = new CatalogStructureDBAgent(pCon, catalogCategorySynchronizeData);
                                dbAgent.doWork();
                            }

                        }

                        if (i > 0) {

                            CategoryReference child = categoryReferencies.get(i);
                            CategoryReference parent = categoryReferencies.get(i - 1);

                            CategoryAssocSynchronizeData categoryAssocSynchronizeData =
                                    new CategoryAssocSynchronizeData(
                                            masterAppData,
                                            masterAppData.getStoreCatalogId(),
                                            child,
                                            parent);

                            if (!categoryAssocSynchronizeData.isSynchronized()) {
                                DBWorker dbAgent = new CategoryAssocDBAgent(pCon, categoryAssocSynchronizeData);
                                dbAgent.doWork();
                            }


                        }
                    }

                    /////////////////////////////////////////////////////////////////////
                    ItemSynchronizeData itemSynchronizeData =
                            new ItemSynchronizeData(
                                    masterAppData,
                                    item,
                                    distributor,
                                    itemReference);

                    if (!itemSynchronizeData.isSynchronized()) {
                        DBWorker dbAgent = new ItemDBAgent(pCon, itemSynchronizeData);
                        dbAgent.doWork();
                    }

                    /////////////////////////////////////////////////////////////////////

                    if (Utility.isSet(item.getImage())) {

                        ItemContentSynchronizeData itemContentSynchronizeData =
                                new ItemContentSynchronizeData(
                                        masterAppData,
                                        itemSynchronizeData.getValue(),
                                        item,
                                        masterAppData.getImageDataMap().get(item.getImage()),
                                        itemReference);


                        if (!itemContentSynchronizeData.isSynchronized()) {
                            DBWorker dbAgent = new ItemContentDBAgent(pCon, itemContentSynchronizeData);
                            dbAgent.doWork();
                        }
                    }

                    /////////////////////////////////////////////////////////////////////
                    ItemStoreSynchronizeData itemStoreSynchronizeData =
                            new ItemStoreSynchronizeData(
                                    masterAppData,
                                    masterCustomerData.getStoreId(),
                                    itemSynchronizeData.getValue(),
                                    itemReference);

                    if (!itemStoreSynchronizeData.isSynchronized()) {
                        DBWorker dbAgent = new ItemMappingDBAgent(pCon, itemStoreSynchronizeData);
                        dbAgent.doWork();
                    }

                    /////////////////////////////////////////////////////////////////////
                    ItemDistributorSynchronizeData itemDistributorSynchronizeData =
                            new ItemDistributorSynchronizeData(
                                    masterAppData,
                                    distributor,
                                    itemSynchronizeData.getValue(),
                                    item,
                                    itemReference);

                    if (!itemDistributorSynchronizeData.isSynchronized()) {
                        DBWorker dbAgent = new ItemMappingDBAgent(pCon, itemDistributorSynchronizeData);
                        dbAgent.doWork();
                    }

                    ///////////////////////////////////////////////////////////////////
                    if (Utility.isSet(manufacturerReference.getManufacturer())) {
                        /////////////////////////////////////////////////////////////////////
                        ItemManufacturerSynchronizeData itemManufacturerSynchronizeData =
                                new ItemManufacturerSynchronizeData(
                                        masterAppData,
                                        manufacturerSynchronizeData.getValue(),
                                        itemSynchronizeData.getValue(),
                                        item,
                                        itemReference);

                        if (!itemManufacturerSynchronizeData.isSynchronized()) {
                            DBWorker dbAgent = new ItemMappingDBAgent(pCon, itemManufacturerSynchronizeData);
                            dbAgent.doWork();
                        }
                    }

                    List<PairView> metaValueList = XpedxLoaderAssist.getMetaValueList(itemSynchronizeData.getValue(), item);
                    for (PairView metaValue : metaValueList) {
                        /////////////////////////////////////////////////////////////////////
                        ItemMetaSynchronizeData itemMetaSynchronizeData =
                                new ItemMetaSynchronizeData(
                                        masterAppData,
                                        itemSynchronizeData.getValue(),
                                        (String) metaValue.getObject1(),
                                        (String) metaValue.getObject2(),
                                        itemReference);

                        if (!itemMetaSynchronizeData.isSynchronized()) {
                            DBWorker dbAgent = new ItemMetaDBAgent(pCon, itemMetaSynchronizeData);
                            dbAgent.doWork();
                        }
                    }

                    ////////////////////////////////////////////////////////////////////////

                    {
                        CatalogStructureSynchronizeData catalogItemSynchronizeData =
                                new ProductStoreCatalogStructureSynchronizeData(
                                        masterAppData,
                                        itemSynchronizeData.getValue(),
                                        RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT,
                                        costCenterId,
                                        masterAppData.getStoreCatalogId(),
                                        multiProductId);

                        if (!catalogItemSynchronizeData.isSynchronized()) {
                            DBWorker dbAgent = new CatalogStructureDBAgent(pCon, catalogItemSynchronizeData);
                            dbAgent.doWork();
                        }
                    }

                    {

                        CatalogStructureSynchronizeData catalogItemSynchronizeData =
                                new ProductAccountCatalogStructureSynchronizeData(
                                        masterAppData,
                                        itemSynchronizeData.getValue(),
                                        item.getCustItemNum(),
                                        item.getCustItemDesc(),
                                        RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT,
                                        costCenterId,
                                        accountCatalogSynchronizeData.getValue(),
                                        multiProductId,
                                        specialPermission,
                                        accountReference,
                                        appAccountEntry.getKey());

                        if (!catalogItemSynchronizeData.isSynchronized()) {
                            DBWorker dbAgent = new CatalogStructureDBAgent(pCon, catalogItemSynchronizeData);
                            dbAgent.doWork();
                        }

                    }

                    {

                        CatalogStructureSynchronizeData catalogItemSynchronizeData =
                                new ProductShoppingCatalogStructureSynchronizeData(
                                        masterAppData,
                                        itemSynchronizeData.getValue(),
                                        item.getCustItemNum(),
                                        item.getCustItemDesc(),
                                        distributor.getBusEntityId(),
                                        RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT,
                                        costCenterId,
                                        catalog.getCatalogId(),
                                        multiProductId,
                                        accountReference,
                                        catalogReference);

                        if (!catalogItemSynchronizeData.isSynchronized()) {
                            DBWorker dbAgent = new CatalogStructureDBAgent(pCon, catalogItemSynchronizeData);
                            dbAgent.doWork();
                        }

                    }


                    CategoryReference categoryReferencie = categoryReferencies.get(categoryReferencies.size() - 1);
                    ItemData category = masterAppData.getAppStoreCategoryMap().get(categoryReferencie);

                    {


                        ItemAssocSynchronizeData itemAssocSynchrobizeData =
                                new StoreItemAssocSynchronizeData(
                                        masterAppData,
                                        itemReference,
                                        itemSynchronizeData.getValue().getItemId(),
                                        category.getItemId(),
                                        masterAppData.getStoreCatalogId(),
                                        RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

                        if (!itemAssocSynchrobizeData.isSynchronized()) {
                            DBWorker dbAgent = new ItemAssocDBAgent(pCon, itemAssocSynchrobizeData);

                            dbAgent.doWork();

                        }
                    }

                    {

                        //ItemData category = masterAppData.getAppAccountReferenceMap().get(accountReference).getAppAcountDataMap().get(appAccountEntry.getKey()).getAccountCategoryMap().get(categoryReferencie);

                        ItemAssocSynchronizeData itemAssocSynchrobizeData =
                                new AccountItemAssocSynchronizeData(
                                        masterAppData,
                                        itemSynchronizeData.getValue().getItemId(),
                                        category.getItemId(),
                                        accountCatalogSynchronizeData.getValue(),
                                        RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY,
                                        itemReference,
                                        appAccountEntry.getKey(),
                                        accountReference
                                );

                        if (!itemAssocSynchrobizeData.isSynchronized()) {
                            DBWorker dbAgent = new ItemAssocDBAgent(pCon, itemAssocSynchrobizeData);
                            dbAgent.doWork();

                        }
                    }

                    {

                        //ItemData category = masterAppData.getAppShoppingCategoryMap(accountReference, catalogReference).get(categoryReferencie);

                        ItemAssocSynchronizeData itemAssocSynchrobizeData =
                                new ShoppingItemAssocSynchronizeData(
                                        masterAppData,
                                        itemSynchronizeData.getValue().getItemId(),
                                        category.getItemId(),
                                        catalog.getCatalogId(),
                                        RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY,
                                        itemReference,
                                        accountReference,
                                        catalogReference
                                );

                        if (!itemAssocSynchrobizeData.isSynchronized()) {
                            DBWorker dbAgent = new ItemAssocDBAgent(pCon, itemAssocSynchrobizeData);
                            dbAgent.doWork();

                        }
                    }


                    ContractItemSynchronizeData contractItemSynchronizeData =
                            new ContractItemSynchronizeData(
                                    masterAppData,
                                    itemSynchronizeData.getValue(),
                                    item,
                                    contractSynchronizeData.getValue(),
                                    accountReference,
                                    catalogReference,
                                    itemReference
                            );

                    if (!contractItemSynchronizeData.isSynchronized()) {
                        DBWorker dbAgent = new ContractItemDBAgent(pCon, contractItemSynchronizeData);
                        dbAgent.doWork();

                    }


                    InventoryItemsSynchronizeData inventoryItemsSynchronizeData =
                            new InventoryItemsSynchronizeData(
                                    masterAppData,
                                    masterCustomerData,
                                    itemSynchronizeData.getValue(),
                                    item,
                                    accountReference,
                                    appAccountEntry.getKey(),
                                    itemReference
                            );

                    if (!inventoryItemsSynchronizeData.isSynchronized()) {
                        DBWorker dbAgent = new InventoryItemsDBAgent(pCon, inventoryItemsSynchronizeData);
                        dbAgent.doWork();

                    }


                    ShoppingControlsSynchronizeData shoppingControlsSynchronizeData =
                            new ShoppingControlsSynchronizeData(
                                    masterAppData,
                                    itemSynchronizeData.getValue(),
                                    item,
                                    accountReference,
                                    appAccountEntry.getKey(),
                                    itemReference
                            );

                    if (!shoppingControlsSynchronizeData.isSynchronized()) {
                        DBWorker dbAgent = new ShoppingControlsDBAgent(pCon, shoppingControlsSynchronizeData);
                        dbAgent.doWork();

                    }


                }

          } catch (Exception e) {
                e.printStackTrace();
                log.error("synchronize()=> ERROR: " + e.getMessage());
                pIxMasterData.addBadLine(line.getLine(), line.getItem(), Utility.getAsList(e.getMessage()));
                lineIt.remove();
            }

        }

        log.debug("synchronize()=> processed lines: " + pIxMasterData.getGoodLines().size());


        MasterAppData masterAppData = getMasterAppData(pAxMasterData);
        AXCatalogItemsData axCatalogItemsData = getAXCatalogItemsData(pIxMasterData, pAxMasterData);

        for (Map.Entry<AccountReference, Map<CatalogReference, Map<ItemReference, AXCatalogItemData>>> axAccountCatalogItemsEntry : axCatalogItemsData.entrySet()) {

            HashSet<Integer> cleanItems =  new HashSet<Integer>();

            for (Map.Entry<CatalogReference, Map<ItemReference, AXCatalogItemData>> axCatalogItemsEntry : axAccountCatalogItemsEntry.getValue().entrySet()) {

                if (pIxMasterData.isProcessed(axCatalogItemsEntry.getKey())) {
                    cleanItems.addAll(cleanShoppingCatalogStructure(pCon,
                            masterAppData,
                            axAccountCatalogItemsEntry.getKey(),
                            axCatalogItemsEntry.getKey(),
                            axCatalogItemsEntry.getValue()));
                } else {
                    log.debug("synchronize()=> SYNCHRONIZATION OF UNUSED ITEMS OF CATALOG '" + axCatalogItemsEntry.getKey() + "' " +
                            " HAS BEEN REJECTED BECAUSE NOT ALL CATALOG ITEMS" +
                            " HAS BEEN PROCESSED.");
                }

            }

            cleanAccountCatalogStructure(pCon,
                    masterAppData,
                    axAccountCatalogItemsEntry.getKey(),
                    cleanItems);

            /*  for (Map.Entry<CatalogReference, Map<ItemReference, AXCatalogItemData>> e : axAccountCatalogItemsEntry.getValue().entrySet()) {

                if (pIxMasterData.isProcessed(axAccountCatalogItemsEntry.getKey())) {

                }
            }*/

        }
        ////////////////////////////////////////////////////
        if (pIxMasterData.getBadLines().isEmpty()) {
          for (Map.Entry<AccountReference, Map<CatalogReference, Map<ItemReference, AXCatalogItemData>>> axAccountCatalogItemsEntry : axCatalogItemsData.entrySet()) {
            actualizationNewShoppingCatalogs(pCon,
                                             masterAppData,
                                             axAccountCatalogItemsEntry.getKey());
          }
        }
        log.debug("synchronize()=> END.");

    }


    public void cleanAccountCatalogStructure(Connection pCon,
                                             MasterAppData pMasterAppData,
                                             AccountReference pAccountRefernce,
                                             HashSet<Integer> pCleanItems) throws Exception {

        Set<Integer> accountIds = pMasterAppData.getAppAccountReferenceMap().get(pAccountRefernce).getAppAcountDataMap().keySet();

        log.debug("cleanAccountCatalogStructure()=> BEGIN");

        Map<Integer, List<ShoppingControlData>> shoppingControlsMapByAccount = XpedxLoaderAssist.getShoppingControlsByAccountId(pCon,
                accountIds,
                pCleanItems,
                false);

        Map<Integer, Map<Integer, InventoryItemsData>> inventoryItemsMapByAccount = XpedxLoaderAssist.getInventoryItemsByAccountId(pCon,
                accountIds,
                pCleanItems);

        for (Integer accountId : accountIds) {

            Set<Integer> shoppingCatalogIds = pMasterAppData.getAppAccountReferenceMap().get(pAccountRefernce).getAppAcountDataMap().get(accountId).getShoppingCatalogIdSet();
            DBCriteria itemCrit = new DBCriteria();

            itemCrit.addOneOf(CatalogStructureDataAccess.CATALOG_ID, new ArrayList<Integer>(shoppingCatalogIds));
            itemCrit.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

            IdVector allShoppingCatalogItems = CatalogStructureDataAccess.selectIdOnly(pCon, CatalogStructureDataAccess.ITEM_ID, itemCrit);

            HashSet<Integer> allShoppingCatalogItemSet = new HashSet<Integer>(allShoppingCatalogItems);

            {
                List<ShoppingControlData> shoppingControls = shoppingControlsMapByAccount.get(accountId);

                if (shoppingControls != null) {

                    Set<ShoppingControlData> toDelete = new HashSet<ShoppingControlData>();
                    for (ShoppingControlData entry : shoppingControls) {
                        if (!allShoppingCatalogItemSet.contains(entry.getItemId())) {
                            toDelete.add(entry);
                        }
                    }

                    if (!toDelete.isEmpty()) {

                        DBCriteria cr = new DBCriteria();
                        cr.addOneOf(ShoppingControlDataAccess.SHOPPING_CONTROL_ID, Utility.toIdVector(new ArrayList<ShoppingControlData>(toDelete)));
                        log.debug("cleanAccountCatalogStructure()=> DELETE ShoppingControlIds:  " + Utility.toIdVector(new ArrayList<ShoppingControlData>(toDelete)) +", ACCOUNT: "+accountId);
                        int n = ShoppingControlDataAccess.remove(pCon, cr);
                        log.debug("cleanAccountCatalogStructure()=> DELETED: " + n);
                    }
                }
            }

            {
                Map<Integer, InventoryItemsData> inventoryItemsDataMap = inventoryItemsMapByAccount.get(accountId);
                if (inventoryItemsDataMap != null) {

                    Set<InventoryItemsData> toDelete = new HashSet<InventoryItemsData>();
                    for (Map.Entry<Integer, InventoryItemsData> entry : inventoryItemsDataMap.entrySet()) {
                        if (!allShoppingCatalogItemSet.contains(entry.getValue().getItemId())) {
                            toDelete.add(entry.getValue());
                        }
                    }

                    if (!toDelete.isEmpty()) {

                        DBCriteria cr = new DBCriteria();
                        cr.addOneOf(InventoryItemsDataAccess.INVENTORY_ITEMS_ID, Utility.toIdVector(new ArrayList<InventoryItemsData>(toDelete)));
                        log.debug("cleanAccountCatalogStructure()=> DELETE InventoryItemsIds:  " + Utility.toIdVector(new ArrayList<InventoryItemsData>(toDelete))+", ACCOUNT: "+accountId);
                        int n = InventoryItemsDataAccess.remove(pCon, cr);
                        log.debug("cleanAccountCatalogStructure()=> DELETED :  " + n);
                    }
                }
            }

            Integer accountCatalog = pMasterAppData.getAppAccountReferenceMap().get(pAccountRefernce).getAppAcountDataMap().get(accountId).getAccountCatalog();
            if (accountCatalog != null && accountCatalog > 0) {
                log.debug("cleanAccountCatalogStructure()=> reset cost centers, AccountCatalogID: " + accountCatalog);
                APIAccess.getAPIAccess().getCatalogAPI().resetCostCenters(accountCatalog, IXCatalogItemLoader.IXCITEMLOADER);
            }


        }

        log.debug("cleanAccountCatalogStructure()=> END.");


    }


    private Set<Integer> cleanShoppingCatalogStructure(Connection pCon,
                                               MasterAppData pMasterAppData,
                                               AccountReference pAccountRefernce,
                                               CatalogReference pCatalogRefernce,
                                               Map<ItemReference, AXCatalogItemData> pCatalogItemsRefMap) throws Exception {
        CatalogData catalog = pMasterAppData.getAppAccountReferenceMap().get(pAccountRefernce).getAppCatalogMap().get(pCatalogRefernce).getCatalog();
        ContractData contract = pMasterAppData.getAppAccountReferenceMap().get(pAccountRefernce).getAppCatalogMap().get(pCatalogRefernce).getContract();

        Map<ItemReference, ItemAssocData> itemCategoryAssocMap;

        Set<Integer> processedCatalogProducts = new HashSet<Integer>();
        Set<Integer> processedCatalogCategories = new HashSet<Integer>();
        for (Map.Entry<ItemReference, AXCatalogItemData> axItemsEntry : pCatalogItemsRefMap.entrySet()) {
            processedCatalogProducts.add(axItemsEntry.getValue().getProductCatalogStructures().getItemId());
            for (CatalogStructureData cs : axItemsEntry.getValue().getCategoryCatalogStructures()) {
                processedCatalogCategories.add(cs.getItemId());
            }
        }

        AppCatalogData axCatalogData = pMasterAppData.getAppAccountReferenceMap().get(pAccountRefernce).getAppCatalogMap().get(pCatalogRefernce);

        Set<Integer> catalogProductsToDelete = new HashSet<Integer>();
        for (CatalogStructureData cs : axCatalogData.getAppShoppingCatalogProductMap().values()) {
            if (!processedCatalogProducts.contains(cs.getItemId())) {
                catalogProductsToDelete.add(cs.getItemId());
            }
        }

        Set<Integer> catalogCategoriesToDelete = new HashSet<Integer>();
        for (CatalogStructureData cs : axCatalogData.getAppShoppingCatalogCategoryMap().values()) {
            if (!processedCatalogCategories.contains(cs.getItemId())) {
                catalogCategoriesToDelete.add(cs.getItemId());
            }
        }

        log.debug("synchronize()=> PRODUCTS TO DELETE: " + catalogProductsToDelete + ", CATALOG_ID: " + catalog.getCatalogId());


        if (!catalogProductsToDelete.isEmpty()) {

            DBCriteria dbc = new DBCriteria();

            dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, new ArrayList<Integer>(catalogProductsToDelete));
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, catalog.getCatalogId());
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

            int n = CatalogStructureDataAccess.remove(pCon, dbc);

            log.debug("synchronize()=> PRODUCTS DELETED: " + n);


            for (Integer itemId : catalogProductsToDelete) {
                axCatalogData.getAppShoppingCatalogProductMap().remove(itemId);
            }


        }

        {
            DBCriteria dbc = new DBCriteria();

            if (!axCatalogData.getAppShoppingCatalogProductMap().isEmpty()) {
                dbc.addNotOneOf(ContractItemDataAccess.ITEM_ID, new ArrayList<Integer>(axCatalogData.getAppShoppingCatalogProductMap().keySet()));
            }
            dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, contract.getContractId());

            int n = ContractItemDataAccess.remove(pCon, dbc);
            Iterator<ContractItemData> it = axCatalogData.getAppContractItemsMap().values().iterator();
            while (it.hasNext()) {
                ContractItemData item = it.next();
                if (!axCatalogData.getAppShoppingCatalogProductMap().keySet().contains(item.getItemId())) {
                    it.remove();
                }
            }
            log.debug("synchronize()=> CONTRACT ITEMS DELETED: " + n + ", CONTRACT_ID: " + contract.getContractId());
        }


        {

            DBCriteria dbc = new DBCriteria();

            if (!axCatalogData.getAppShoppingCatalogProductMap().isEmpty()) {
                dbc.addNotOneOf(ItemAssocDataAccess.ITEM1_ID, new ArrayList<Integer>(axCatalogData.getAppShoppingCatalogProductMap().keySet()));
            }

            dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, catalog.getCatalogId());
            dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

            int n = ItemAssocDataAccess.remove(pCon, dbc);

            log.debug("synchronize()=> PRODUCTS ASSOC DELETED: " + n);

            itemCategoryAssocMap = axCatalogData.getAppShoppingItemCategoryAssocMap();

            Map<Integer/*itemId*/, ItemReference> itemCategoryAssocRefToDelete = new HashMap<Integer/*itemId*/, ItemReference/*itemRef*/>();
            for (Map.Entry<ItemReference, ItemAssocData> itemCategoryAssocEntry : itemCategoryAssocMap.entrySet()) {
                if (!axCatalogData.getAppShoppingCatalogProductMap().containsKey(itemCategoryAssocEntry.getValue().getItem1Id())) {
                    itemCategoryAssocRefToDelete.put(itemCategoryAssocEntry.getValue().getItem1Id(), itemCategoryAssocEntry.getKey());
                }
            }

            log.debug("synchronize()=> ITEM CATEGORY ASSOC REF TO DELETE: " + itemCategoryAssocRefToDelete);

            if (!itemCategoryAssocRefToDelete.isEmpty()) {

                for (ItemReference itemRef : itemCategoryAssocRefToDelete.values()) {
                    axCatalogData.getAppShoppingItemCategoryAssocMap().remove(itemRef);
                }

            }

        }

        log.debug("synchronize()=> CATEGORIES TO DELETE: " + catalogCategoriesToDelete + ", CATALOGID: " + catalog.getCatalogId());

        if (!catalogCategoriesToDelete.isEmpty()) {

            DBCriteria dbc = new DBCriteria();

            dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, new ArrayList<Integer>(catalogCategoriesToDelete));
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, catalog.getCatalogId());
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);

            int n = CatalogStructureDataAccess.remove(pCon, dbc);

            log.debug("synchronize()=> CATEGORY DELETED: " + n);

            for (Integer itemId : catalogCategoriesToDelete) {
                axCatalogData.getAppShoppingCatalogCategoryMap().remove(itemId);
            }
        }


        {
            DBCriteria dbc = new DBCriteria();

            if (!axCatalogData.getAppShoppingCatalogCategoryMap().isEmpty()) {
                dbc.addNotOneOf(ItemAssocDataAccess.ITEM2_ID, new ArrayList<Integer>(axCatalogData.getAppShoppingCatalogCategoryMap().keySet()));
            }
            dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, catalog.getCatalogId());
            dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);

            int n = ItemAssocDataAccess.remove(pCon, dbc);

            log.debug("synchronize()=> CATEGORY ASSOC DELETED: " + n);


            itemCategoryAssocMap = axCatalogData.getAppShoppingItemCategoryAssocMap();

            Map<Integer/*categoryId*/, Set<ItemReference>/*itemRefs*/> categoryItemAssocRefToDelete = new HashMap<Integer/*categoryId*/, Set<ItemReference>/*itemRefs*/>();
            for (Map.Entry<ItemReference, ItemAssocData> itemCategoryAssocEntry : itemCategoryAssocMap.entrySet()) {
                if (!axCatalogData.getAppShoppingCatalogCategoryMap().containsKey(itemCategoryAssocEntry.getValue().getItem2Id())) {
                    Set<ItemReference> itemRefs = categoryItemAssocRefToDelete.get(itemCategoryAssocEntry.getValue().getItem2Id());
                    if (itemRefs == null) {
                        itemRefs = new HashSet<ItemReference/*itemRefs*/>();
                        categoryItemAssocRefToDelete.put(itemCategoryAssocEntry.getValue().getItem2Id(), itemRefs);
                    }
                    itemRefs.add(itemCategoryAssocEntry.getKey());
                }
            }

            log.debug("synchronize()=> CATEGORY ITEM ASSOC REF TO DELETE: " + categoryItemAssocRefToDelete);

            if (!categoryItemAssocRefToDelete.isEmpty()) {

                for (Set<ItemReference> itemRefs : categoryItemAssocRefToDelete.values()) {
                    for (ItemReference itemRef : itemRefs) {
                        axCatalogData.getAppShoppingItemCategoryAssocMap().remove(itemRef);
                    }
                }

            }

        }

        return catalogProductsToDelete;


    }

    private AXCatalogItemsData getAXCatalogItemsData(IXMasterData pIxMasterData, AXMasterData pAxMasterData) {

        AXCatalogItemsData result = new AXCatalogItemsData();

        for (Line<XpedxCatalogItemView> line : pIxMasterData.getGoodLines()) {

            XpedxCatalogItemView item = line.getItem();

            AccountReference accountReference = new AccountReference(item.getAccountNumber());
            CatalogReference catalogReference = new CatalogReference(item.getAccountNumber(), item.getCatalogID());
            ItemReference itemReference = new ItemReference(item.getDistributor(), item.getDistSKU());

            List<String> itemCategories = XpedxLoaderAssist.getItemCategories(item);
            List<CategoryReference> categoryReferencies = XpedxLoaderAssist.createCategoryReferences(item.getMasterCustomerName(), itemCategories);

            MasterAppData masterAppData = getMasterAppData(pAxMasterData);

            Map<CatalogReference, Map<ItemReference, AXCatalogItemData>> catalogItemsMap = result.get(accountReference);
            if (catalogItemsMap == null) {
                catalogItemsMap = new HashMap<CatalogReference, Map<ItemReference, AXCatalogItemData>>();
                result.put(accountReference, catalogItemsMap);
            }

            Map<ItemReference, AXCatalogItemData> itemsMap = catalogItemsMap.get(catalogReference);
            if (itemsMap == null) {
                itemsMap = new HashMap<ItemReference, AXCatalogItemData>();
                catalogItemsMap.put(catalogReference, itemsMap);
            }


            CatalogData catalog = masterAppData.getAppAccountReferenceMap().get(accountReference).getAppCatalogMap().get(catalogReference).getCatalog();

            List<CatalogStructureData> catalogCategoryStructures = new ArrayList<CatalogStructureData>();

            for (CategoryReference categoryReference : categoryReferencies) {
                ItemData i = masterAppData.getAppStoreCategoryMap().get(categoryReference);
                CatalogStructureData catalogCategoeyStructure = masterAppData
                        .getAppAccountReferenceMap()
                        .get(accountReference)
                        .getAppCatalogMap()
                        .get(catalogReference).getAppShoppingCatalogCategoryMap()
                        .get(i.getItemId());
                if (catalogCategoeyStructure != null) {
                    catalogCategoryStructures.add(catalogCategoeyStructure);
                }
            }

            CategoryReference categoryReferencie = categoryReferencies.get(categoryReferencies.size() - 1);

            AXCatalogItemData processedItem = itemsMap.get(itemReference);
            ItemData itemData = masterAppData.getAppItemMap().get(itemReference).getItemData();
            ItemData category = masterAppData.getAppStoreCategoryMap().get(categoryReferencie);

            if (itemData != null) {

                CatalogStructureData catalogProductStructure = masterAppData
                        .getAppAccountReferenceMap()
                        .get(accountReference)
                        .getAppCatalogMap()
                        .get(catalogReference)
                        .getAppShoppingCatalogProductMap().get(itemData.getItemId());

                if (processedItem == null) {
                    processedItem = new AXCatalogItemData();
                    itemsMap.put(itemReference, processedItem);
                }

                processedItem.setCatalog(catalog);
                processedItem.setCategory(category);
                processedItem.setItem(itemData);
                processedItem.setCategoryCatalogStructures(catalogCategoryStructures);
                processedItem.setProductCatalogStructure(catalogProductStructure);
            }

        }

        return result;


    }

    private void checkLine(XpedxCatalogItemView pItem,
                           MasterAppData pMasterAppData,
                           DistributorReference pDistributorReference,
                           AccountReference pAccountReference,
                           Integer pAccountId) throws Exception {

        Map<String, List<CostCenterData>> costCentersMap = pMasterAppData
                .getAppAccountReferenceMap()
                .get(pAccountReference)
                .getAppAcountDataMap()
                .get(pAccountId).getCostCentersMap();

        Map<Integer, Integer> multiProductMap = pMasterAppData.getMultiProductsMap();
        Map<Integer, FreightTableData> freightTableMap = pMasterAppData.getFreightTablesMap();
        BusEntityData distributor = pMasterAppData.getAppDistributorMap().get(pDistributorReference);
        Map<String, Integer> localePriceDecimals = pMasterAppData.getLocalePriceDecimals();

        Integer costCenterId = Utility.isSet(pItem.getCostCenter())
                && costCentersMap.get(pItem.getCostCenter()) != null
                && !costCentersMap.get(pItem.getCostCenter()).isEmpty()
                ? costCentersMap.get(pItem.getCostCenter()).get(0).getCostCenterId()
                : null;

        Integer multiProductId = Utility.isSet(pItem.getMultiProductID())
                ? multiProductMap.get(Utility.parseInt(pItem.getMultiProductID()))
                : null;

        Integer freightTableId = Utility.isSet(pItem.getFreightTableID())
                && freightTableMap.get(Utility.parseInt(pItem.getFreightTableID())) != null
                ? freightTableMap.get(Utility.parseInt(pItem.getFreightTableID())).getFreightTableId()
                : null;

        if (distributor == null) {
            throw new Exception("Could not find distributor '" + pDistributorReference + "'");
        }

        if (Utility.isSet(pItem.getCostCenter()) && costCentersMap.get(pItem.getCostCenter()) != null && costCentersMap.get(pItem.getCostCenter()).size() > 1) {
            throw new Exception("Found duplicated  cost center '" + pItem.getCostCenter() + "' in the account ref: " + pAccountReference + "/" + pAccountId + "'");
        }

        if (Utility.isSet(pItem.getCostCenter()) && costCenterId == null) {
            throw new Exception("Could not find cost center name '" + pItem.getCostCenter() + "' in the account ref: " + pAccountReference + "/" + pAccountId + "'");
        }

        if (Utility.isSet(pItem.getMultiProductID()) && multiProductId == null) {
            throw new Exception("Could not find multi product id: '" + pItem.getMultiProductID() + "'  in the store: '" + pMasterAppData.getStoreId() + "'");
        }

        if (Utility.isSet(pItem.getFreightTableID()) && freightTableId == null) {
            throw new Exception("Could not find freight table id: '" + pItem.getFreightTableID() + "' in the store: '" + pMasterAppData.getStoreId() + "'");

        }


        if (!localePriceDecimals.keySet().contains(pItem.getLocale())) {
            throw new Exception("Wrong LocaleCD '" + pItem.getLocale() + "'");
        }

        Integer decimalPlaces = localePriceDecimals.get(pItem.getLocale());

        if (decimalPlaces != null && Utility.isSet(pItem.getCost())) {
            BigDecimal value = new BigDecimal(pItem.getCost());
            if (value.scale() > decimalPlaces) {
                throw new Exception("The Cost '" + pItem.getCost() + "' for item "
                        + new ItemReference(pItem.getDistributor(), pItem.getDistSKU())
                        + " has too many decimal points. It can only have "
                        + decimalPlaces + " decimal points for '"
                        + pItem.getLocale() + "' currency");
            }
        }

        if (decimalPlaces != null && Utility.isSet(pItem.getPrice())) {
            BigDecimal value = new BigDecimal(pItem.getPrice());
            if (value.scale() > decimalPlaces) {
                throw new Exception(("The Price '" + pItem.getPrice() + "' for item "
                        + new ItemReference(pItem.getDistributor(), pItem.getDistSKU())
                        + " has too many decimal points. It can only have "
                        + decimalPlaces + " decimal points for '"
                        + pItem.getLocale() + "' currency"));

            }
        }

        if (decimalPlaces != null && Utility.isSet(pItem.getListPrice())) {
            BigDecimal value = new BigDecimal(pItem.getListPrice());
            if (value.scale() > decimalPlaces) {
                throw new Exception(("The List Price '" + pItem.getListPrice() + "' for item "
                        + new ItemReference(pItem.getDistributor(), pItem.getDistSKU())
                        + " has too many decimal points. It can only have "
                        + decimalPlaces + " decimal points for '"
                        + pItem.getLocale() + "' currency"));
            }
        }


        if (Utility.isSet(pItem.getImage())
                && (pMasterAppData.getImageDataMap().get(pItem.getImage()) == null
                || pMasterAppData.getImageDataMap().get(pItem.getImage()).length == 0)) {
            throw new Exception("Can't get image from URL '" + pItem.getImage() + "'.");
        }
    }

    private MasterAppData getMasterAppData(AXMasterData pAxMasterData) {
        return pAxMasterData.getMasterAppDataList().get(0);
    }

    private MasterCustomerData getMasterCustomerData(IXMasterData pIxMasterData) {
        return pIxMasterData.getMasterCustomerDataList().get(0);
    }

    private void actualizationNewShoppingCatalogs(Connection mCon, MasterAppData pMasterAppData, AccountReference pAccountRefernce) throws Exception{
      log.debug("actualizationNewShoppingCatalogs()=> BEGIN");
      Set<Integer> accountIds = pMasterAppData.getAppAccountReferenceMap().get(pAccountRefernce).getAppAcountDataMap().keySet();
      PreparedStatement pstmt = null;
      for (Integer accountId : accountIds) {
           Set<Integer> shoppingCatalogIds = pMasterAppData.getAppAccountReferenceMap().get(pAccountRefernce).getAppAcountDataMap().get(accountId).getShoppingCatalogIdSet();
           String  shoppingCatalogIdsStr = IdVector.toCommaString(new ArrayList<Integer>(shoppingCatalogIds));

           DBCriteria crit = new DBCriteria();
           crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
           crit.addOneOf(CatalogAssocDataAccess.NEW_CATALOG_ID, new ArrayList<Integer>(shoppingCatalogIds));
           crit.addCondition(" EXISTS (SELECT 1 FROM CLW_CATALOG_STRUCTURE WHERE CATALOG_ID = NEW_CATALOG_ID) ");
           String where = crit.getSqlClause();

           String updateCatalogAssocSql = "UPDATE CLW_CATALOG_ASSOC "
                + " SET CATALOG_ID = NEW_CATALOG_ID, NEW_CATALOG_ID = null, MOD_BY='"+IXCatalogItemLoader.IXCITEMLOADER+"', MOD_DATE= ? "
                + " WHERE " + where;
           log.debug("actualizationNewShoppingCatalogs()=> updateCatalogAssocSql : "+updateCatalogAssocSql);

           pstmt = mCon.prepareStatement(updateCatalogAssocSql);
           pstmt.setTimestamp(1,DBAccess.toSQLTimestamp(new java.util.Date(System.currentTimeMillis())));
           int n = pstmt.executeUpdate();
           pstmt.close();
           log.debug("actualizationNewShoppingCatalogs()=> accountId= "+accountId + " ; REPLACED ASSOC : " + n);

       }

       log.debug("actualizationNewShoppingCatalogs()=> END");

    }


    public interface DBWorker extends Serializable {
        public void doWork() throws Exception;
    }

    public interface Sync<T> extends Serializable {
        public boolean isSynchronized();

        public void refresh(T pValue);

        public T getValue();
    }

    public abstract class SynchronizeData<T> implements Sync<T> {

        public MasterAppData mMasterData;

        public SynchronizeData(MasterAppData pMasterData) {
            this.mMasterData = pMasterData;
        }


        public MasterAppData getMasterData() {
            return mMasterData;
        }

        public void setMasterData(MasterAppData mMasterData) {
            this.mMasterData = mMasterData;
        }
    }

    public class StoreCatalogSynchronizeData extends SynchronizeData<Integer> {

        private int mStoreId;

        public StoreCatalogSynchronizeData(MasterAppData pMasterData, int pStoreId) {
            super(pMasterData);
            mStoreId = pStoreId;
        }

        public boolean isSynchronized() {
            return mMasterData.getStoreCatalogId() > 0;
        }

        public void refresh(Integer pValue) {
            mMasterData.setStoreCatalogId(pValue);
        }

        public Integer getValue() {
            return mMasterData.getStoreCatalogId();
        }

        public int getStoreId() {
            return mStoreId;
        }
    }

    public class StoreCatalogDBAgent implements DBWorker {

        private Logger log = Logger.getLogger(StoreCatalogDBAgent.class);

        private static final String STORE_CATALOG_ = "Store_Catalog_";

        private StoreCatalogSynchronizeData mSynchData;

        private Connection mCon;

        public StoreCatalogDBAgent(Connection pCon, StoreCatalogSynchronizeData pSynchData) {
            mCon = pCon;
            mSynchData = pSynchData;
        }

        public void doWork() throws Exception {
            if (!mSynchData.isSynchronized()) {
                int storeCatalogId = doSynchronize();
                mSynchData.refresh(storeCatalogId);
            }
        }

        private int doSynchronize() throws Exception {

            log.debug("doSynchronize()=> BEGIN");

            log.debug("doSynchronize()=> XXX StoreID: " + mSynchData.getStoreId());

            CatalogData catalog = CatalogData.createValue();

            catalog.setAddBy(IXCatalogItemLoader.IXCITEMLOADER);
            catalog.setModBy(IXCatalogItemLoader.IXCITEMLOADER);
            catalog.setCatalogStatusCd(RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
            catalog.setCatalogTypeCd(RefCodeNames.CATALOG_TYPE_CD.STORE);
            catalog.setShortDesc(STORE_CATALOG_ + mSynchData.getStoreId());

            catalog = CatalogDataAccess.insert(mCon, catalog);

            CatalogAssocData catalogAssoc = CatalogAssocData.createValue();
            catalogAssoc.setAddBy(IXCatalogItemLoader.IXCITEMLOADER);
            catalogAssoc.setModBy(IXCatalogItemLoader.IXCITEMLOADER);
            catalogAssoc.setBusEntityId(mSynchData.getStoreId());
            catalogAssoc.setCatalogId(catalog.getCatalogId());
            catalogAssoc.setCatalogAssocCd(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

            catalogAssoc = CatalogAssocDataAccess.insert(mCon, catalogAssoc);

            log.debug("doSynchronize()=> XXX New.CataloID: " + catalogAssoc.getCatalogId());

            log.debug("doSynchronize()=> END.");

            return catalogAssoc.getCatalogId();
        }

    }

    public class AccountCatalogSynchronizeData extends SynchronizeData<Integer> {

        private AccountReference mAccountReference;
        private Integer mAccountId;

        public AccountCatalogSynchronizeData(MasterAppData pMasterData,
                                             AccountReference pAccountReference,
                                             Integer pAccountId) {
            super(pMasterData);
            this.mAccountReference = pAccountReference;
            this.mAccountId = pAccountId;
        }

        public boolean isSynchronized() {
            return mMasterData.getAppAccountReferenceMap().containsKey(mAccountReference)
                    && mMasterData.getAppAccountReferenceMap().get(mAccountReference) != null
                    && mMasterData.getAppAccountReferenceMap().get(mAccountReference).getAppAcountDataMap().get(mAccountId) != null
                    && mMasterData.getAppAccountReferenceMap().get(mAccountReference).getAppAcountDataMap().get(mAccountId).getAccountCatalog() > 0;
        }

        public void refresh(Integer pValue) {

            AppAccountReferenceData accountRefData = mMasterData.getAppAccountReferenceMap().get(mAccountReference);
            if (accountRefData == null) {
                accountRefData = new AppAccountReferenceData();
                mMasterData.getAppAccountReferenceMap().put(mAccountReference, accountRefData);
            }

            AppAccountData appAccountData = accountRefData.getAppAcountDataMap().get(mAccountId);
            if (appAccountData == null) {
                appAccountData = new AppAccountData();
                accountRefData.getAppAcountDataMap().put(mAccountId, appAccountData);
            }
            appAccountData.setAccountCatalog(pValue);
        }

        public Integer getValue() {

            AppAccountReferenceData accountRefData = mMasterData.getAppAccountReferenceMap().get(mAccountReference);
            if (accountRefData == null) {
                accountRefData = new AppAccountReferenceData();
                mMasterData.getAppAccountReferenceMap().put(mAccountReference, accountRefData);
            }

            AppAccountData appAccountData = accountRefData.getAppAcountDataMap().get(mAccountId);
            if (appAccountData == null) {
                appAccountData = new AppAccountData();
                accountRefData.getAppAcountDataMap().put(mAccountId, appAccountData);
            }

            return appAccountData.getAccountCatalog() != null ? appAccountData.getAccountCatalog() : -1;
        }


        public AccountReference getAccountReference() {
            return mAccountReference;
        }

        public Integer getAccountId() {
            return mAccountId;
        }
    }

    public class AccountCatalogDBAgent implements DBWorker {

        private Logger log = Logger.getLogger(AccountCatalogDBAgent.class);

        private static final String MASTER = "MASTER";

        private AccountCatalogSynchronizeData mSynchData;

        private Connection mCon;

        public AccountCatalogDBAgent(Connection pCon, AccountCatalogSynchronizeData pSynchData) {
            mCon = pCon;
            mSynchData = pSynchData;
        }

        public void doWork() throws Exception {
            if (!mSynchData.isSynchronized()) {
                int catalog = doSynchronize();
                mSynchData.refresh(catalog);
            }
        }

        private int doSynchronize() throws Exception {

            log.debug("doSynchronize()=> BEGIN");

            log.debug("doSynchronize()=> XXX AccountID: " + mSynchData.getAccountId());

            BusEntityData account = BusEntityDataAccess.select(mCon, mSynchData.getAccountId());

            log.debug("doSynchronize()=> XXX New.CatalogName: " + account.getShortDesc() + MASTER);

            CatalogData catalog = CatalogData.createValue();

            catalog.setAddBy(IXCatalogItemLoader.IXCITEMLOADER);
            catalog.setModBy(IXCatalogItemLoader.IXCITEMLOADER);
            catalog.setCatalogStatusCd(RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
            catalog.setCatalogTypeCd(RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
            catalog.setShortDesc(account.getShortDesc() + MASTER);

            catalog = CatalogDataAccess.insert(mCon, catalog);

            CatalogAssocData catalogAssoc = CatalogAssocData.createValue();
            catalogAssoc.setAddBy(IXCatalogItemLoader.IXCITEMLOADER);
            catalogAssoc.setModBy(IXCatalogItemLoader.IXCITEMLOADER);
            catalogAssoc.setBusEntityId(mSynchData.getAccountId());
            catalogAssoc.setCatalogId(catalog.getCatalogId());
            catalogAssoc.setCatalogAssocCd(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);

            CatalogAssocDataAccess.insert(mCon, catalogAssoc);

            catalogAssoc = CatalogAssocData.createValue();
            catalogAssoc.setAddBy(IXCatalogItemLoader.IXCITEMLOADER);
            catalogAssoc.setModBy(IXCatalogItemLoader.IXCITEMLOADER);
            catalogAssoc.setBusEntityId(mSynchData.getAccountId());
            catalogAssoc.setCatalogId(catalog.getCatalogId());
            catalogAssoc.setCatalogAssocCd(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

            catalogAssoc = CatalogAssocDataAccess.insert(mCon, catalogAssoc);

            log.debug("doSynchronize()=> XXX New.CatalogID: " + catalogAssoc.getCatalogId());

            log.debug("doSynchronize()=> END.");

            return catalogAssoc.getCatalogId();

        }

    }

    public class ManufacturerSynchronizeData extends SynchronizeData<BusEntityData> {

        private Integer mStoreId;
        private ManufacturerReference mManufacturerReference;

        public ManufacturerSynchronizeData(MasterAppData pMasterData, Integer pStoreId, ManufacturerReference pManufacturerReference) {
            super(pMasterData);
            this.mStoreId = pStoreId;
            this.mManufacturerReference = pManufacturerReference;
        }

        public boolean isSynchronized() {
            return mMasterData.getAppManufacturerMap().containsKey(mManufacturerReference)
                    && mMasterData.getAppManufacturerMap().get(mManufacturerReference) != null
                    && mMasterData.getAppManufacturerMap().get(mManufacturerReference).getBusEntityId() > 0;
        }

        public void refresh(BusEntityData pValue) {
            mMasterData.getAppManufacturerMap().put(mManufacturerReference, pValue);

        }

        public BusEntityData getValue() {
            return mMasterData.getAppManufacturerMap().get(mManufacturerReference);
        }

        public Integer getStoreId() {
            return mStoreId;
        }

        public String getManufacturer() {
            return mManufacturerReference.getManufacturer();
        }
    }

    public class ManufacturerDBAgent implements DBWorker {

        private Logger log = Logger.getLogger(ManufacturerDBAgent.class);

        private ManufacturerSynchronizeData mSynchData;

        private Manufacturer mManufEjb;

        public ManufacturerDBAgent(Manufacturer pManufEjb, ManufacturerSynchronizeData pSynchData) {
            mManufEjb = pManufEjb;
            mSynchData = pSynchData;
        }

        public void doWork() throws Exception {
            if (!mSynchData.isSynchronized()) {
                BusEntityData manuf = doSynchronize();
                mSynchData.refresh(manuf);
            }
        }

        private BusEntityData doSynchronize() throws Exception {

            log.debug("doSynchronize()=> BEGIN");

            log.debug("doSynchronize()=> StoreID: " + mSynchData.getStoreId());
            log.debug("doSynchronize()=> Manuf: " + mSynchData.getManufacturer());

            BusEntityData beData = new BusEntityData();
            beData.setShortDesc(mSynchData.getManufacturer());
            beData.setBusEntityStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
            beData.setBusEntityTypeCd(RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);
            beData.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
            beData.setLocaleCd(RefCodeNames.LOCALE_CD.EN_US);
            beData.setAddBy(IXCatalogItemLoader.IXCITEMLOADER);
            beData.setModBy(IXCatalogItemLoader.IXCITEMLOADER);

            ManufacturerData manuf = new ManufacturerData(
                    beData,
                    mSynchData.getStoreId(),
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

            manuf = mManufEjb.addManufacturer(manuf);

            log.debug("doSynchronize()=> New.ManufID: " + manuf.getBusEntity().getBusEntityId());

            log.debug("doSynchronize()=> END.");

            return manuf.getBusEntity();

        }

    }

    public class ShoppingCatalogSynchronizeData extends SynchronizeData<CatalogData> {

        private Integer mStoreId;
        private CatalogReference mCatalogReference;
        private AccountReference mAccountReference;

        public ShoppingCatalogSynchronizeData(MasterAppData pMasterAppData,
                                              Integer pStoreId,
                                              AccountReference pAccountReference,
                                              CatalogReference pCatalogReference) {
            super(pMasterAppData);
            mStoreId = pStoreId;
            mCatalogReference = pCatalogReference;
            mAccountReference = pAccountReference;
         }


        public Integer getStoreId() {
            return mStoreId;
        }

        public void setStoreId(Integer pStoreId) {
            this.mStoreId = pStoreId;
        }

        public CatalogReference getCatalogReference() {
            return mCatalogReference;
        }

        public void setCatalogReference(CatalogReference pCatalogReference) {
            this.mCatalogReference = pCatalogReference;
        }

        public AccountReference getAccountReference() {
            return mAccountReference;
        }

        public boolean isSynchronized() {
            return mMasterData.getAppAccountReferenceMap().containsKey(mAccountReference)
                    && mMasterData.getAppAccountReferenceMap().get(mAccountReference) != null
                    && mMasterData.getAppAccountReferenceMap().get(mAccountReference).getAppCatalogMap().containsKey(mCatalogReference)
                    && mMasterData.getAppAccountReferenceMap().get(mAccountReference).getAppCatalogMap().get(mCatalogReference) != null
                    && mMasterData.getAppAccountReferenceMap().get(mAccountReference).getAppCatalogMap().get(mCatalogReference).getCatalog() != null;
        }

        public void refresh(CatalogData pValue) {
            mMasterData.getAppAccountReferenceMap().get(mAccountReference).getAppCatalogMap().get(mCatalogReference).setCatalog(pValue);
        }

        public CatalogData getValue() {
            return mMasterData.getAppAccountReferenceMap().get(mAccountReference).getAppCatalogMap().get(mCatalogReference).getCatalog();
        }

    }

    public class ShoppingCatalogDBAgent implements DBWorker {

        private Logger log = Logger.getLogger(ManufacturerDBAgent.class);

        private ShoppingCatalogSynchronizeData mSynchData;

        private Connection mCon;

        public ShoppingCatalogDBAgent(Connection pCon, ShoppingCatalogSynchronizeData pSynchData) {
            mCon = pCon;
            mSynchData = pSynchData;
        }

        public void doWork() throws Exception {
            if (!mSynchData.isSynchronized()) {
                CatalogData catalog = doSynchronize();
                mSynchData.refresh(catalog);
            }
        }

        private CatalogData doSynchronize() throws Exception {

            log.debug("doSynchronize()=> BEGIN");

            log.debug("doSynchronize()=> XXX StoreID: " + mSynchData.getStoreId());
            log.debug("doSynchronize()=> XXX AccountReference: " + mSynchData.getAccountReference());
            log.debug("doSynchronize()=> XXX CatalogReference: " + mSynchData.getCatalogReference());

            CatalogReference catalogRef = mSynchData.getCatalogReference();

            DBCriteria crit = new DBCriteria();

            crit.addJoinCondition(CatalogDataAccess.CATALOG_ID, CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ID);
            crit.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.BUS_ENTITY_ID, mSynchData.getStoreId());
            crit.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
            crit.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD, RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
            crit.addEqualTo(CatalogDataAccess.SHORT_DESC, catalogRef.getCatalogNumber());

            CatalogDataVector catalogs = CatalogDataAccess.select(mCon, crit);
            if (!catalogs.isEmpty()) {
                throw new Exception("Catalog with name '" + catalogRef.getCatalogNumber() + "' already exists, StoreID: " + mSynchData.getStoreId());
            }

            CatalogData catalogData = CatalogData.createValue();
            catalogData.setShortDesc(catalogRef.getCatalogNumber());
            catalogData.setLoaderField(catalogRef.getCatalogNumber());
            catalogData.setAddBy(IXCatalogItemLoader.IXCITEMLOADER);
            catalogData.setModBy(IXCatalogItemLoader.IXCITEMLOADER);
            catalogData.setCatalogTypeCd(RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
            catalogData.setCatalogStatusCd(RefCodeNames.STATUS_CD.ACTIVE);

            catalogData = CatalogDataAccess.insert(mCon, catalogData);

            log.debug("doSynchronize()=> XXX New.CatalogID: " + catalogData.getCatalogId());

            log.debug("doSynchronize()=> END.");

            return catalogData;

        }

    }

     public class AccountCatalogAssocSynchronizeData extends  CatalogAssocSynchronizeData{

         public AccountCatalogAssocSynchronizeData(MasterAppData pMasterAppData,
                                                   Integer pAccountId,
                                                   Integer pCatalogId,
                                                   AccountReference pAccountReference,
                                                   CatalogReference pCatalogReference) {
             super(pMasterAppData,
                     pAccountId,
                     pCatalogId,
                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT,
                     pAccountReference,
                     pCatalogReference);
         }

         public void refresh(CatalogAssocData pValue) {
             super.refresh(pValue);
             mMasterData
                     .getAppAccountReferenceMap()
                     .get(getAccountReference())
                     .getAppAcountDataMap()
                     .get(getBusEntityId())
                     .getShoppingCatalogIdSet()
                     .add(pValue.getCatalogId());


         }
     }

    public class CatalogAssocSynchronizeData extends SynchronizeData<CatalogAssocData> {

        private String mCatalogAssocTypeCd;
        private Integer mBusEntityId;
        private Integer mCatalogId;
        private CatalogReference mCatalogReference;
        private AccountReference mAccountReference;

        public CatalogAssocSynchronizeData(MasterAppData pMasterAppData,
                                           Integer pBusEntityId,
                                           Integer pCatalogId,
                                           String pCatalogAssocTypeCd,
                                           AccountReference pAccountReference,
                                           CatalogReference pCatalogReference) {
            super(pMasterAppData);
            mCatalogAssocTypeCd = pCatalogAssocTypeCd;
            mBusEntityId = pBusEntityId;
            mCatalogId = pCatalogId;
            mAccountReference = pAccountReference;
            mCatalogReference = pCatalogReference;
        }

        public boolean isSynchronized() {
            return mMasterData.getAppAccountReferenceMap().get(mAccountReference) != null
                    && mMasterData.getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppCatalogMap()
                    .get(mCatalogReference)
                    .getCatalogAssocMap().containsKey(mBusEntityId)
                    && mMasterData.getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppCatalogMap()
                    .get(mCatalogReference)
                    .getCatalogAssocMap().get(mBusEntityId)
                   .containsKey(mCatalogAssocTypeCd);
        }

        public void refresh(CatalogAssocData pValue) {
           Map<String, CatalogAssocData> val = new HashMap<String, CatalogAssocData>();
           val.put(mCatalogAssocTypeCd, pValue);
           mMasterData.getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppCatalogMap()
                    .get(mCatalogReference)
                    .getCatalogAssocMap()
                    .put(mBusEntityId, val);

        }

        public CatalogAssocData getValue() {
            return mMasterData.getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppCatalogMap()
                    .get(mCatalogReference)
                    .getCatalogAssocMap().get(mBusEntityId)
                    .get(mCatalogAssocTypeCd);
        }


        public String getCatalogAssocTypeCd() {
            return mCatalogAssocTypeCd;
        }

        public Integer getBusEntityId() {
            return mBusEntityId;
        }

        public Integer getCatalogId() {
            return mCatalogId;
        }

        public CatalogReference getCatalogReference() {
            return mCatalogReference;
        }


        public AccountReference getAccountReference() {
            return mAccountReference;
        }
    }

    public class CatalogAssocDBAgent implements DBWorker {

        private Logger log = Logger.getLogger(CatalogAssocDBAgent.class);

        private CatalogAssocSynchronizeData mSynchData;

        private Connection mCon;

        public CatalogAssocDBAgent(Connection pCon, CatalogAssocSynchronizeData pSynchData) {
            mCon = pCon;
            mSynchData = pSynchData;
        }

        public void doWork() throws Exception {
            if (!mSynchData.isSynchronized()) {
                CatalogAssocData catalogAssoc = doSynchronize();
                mSynchData.refresh(catalogAssoc);
            }
        }

        private CatalogAssocData doSynchronize() throws Exception {

            log.debug("doSynchronize()=> BEGIN");

            log.debug("doSynchronize()=> XXX BusEntityID: " + mSynchData.getBusEntityId());
            log.debug("doSynchronize()=> XXX AssocTypeCD: " + mSynchData.getCatalogAssocTypeCd());
            log.debug("doSynchronize()=> XXX AccountReference: " + mSynchData.getAccountReference());
            log.debug("doSynchronize()=> XXX CatalogReference: " + mSynchData.getCatalogReference());

            boolean isMainDistrAssoc = RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR.equals(mSynchData.getCatalogAssocTypeCd());
            DBCriteria crit = new DBCriteria();

            CatalogAssocData catalogAssocData = CatalogAssocData.createValue();
            if (!isMainDistrAssoc){
              crit.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, mSynchData.getBusEntityId());
            }
            crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, mSynchData.getCatalogAssocTypeCd());
            crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, mSynchData.getCatalogId());

            CatalogAssocDataVector caDV = CatalogAssocDataAccess.select(mCon, crit);

            if (caDV != null && caDV.size() > 0) {
                if ( isMainDistrAssoc && caDV.size() > 1){
                   throw new Exception("More than 1 Main Distributer exists for catalogId: " + mSynchData.getCatalogId()  );
                }
                catalogAssocData = (CatalogAssocData) caDV.get(0);
                catalogAssocData.setBusEntityId(mSynchData.getBusEntityId());
                catalogAssocData.setCatalogId(mSynchData.getCatalogId());
                catalogAssocData.setModBy(IXCatalogItemLoader.IXCITEMLOADER);
                CatalogAssocDataAccess.update(mCon, catalogAssocData);

            } else {

                catalogAssocData.setCatalogId(mSynchData.getCatalogId());
                catalogAssocData.setCatalogAssocCd(mSynchData.getCatalogAssocTypeCd());
                catalogAssocData.setBusEntityId(mSynchData.getBusEntityId());
                catalogAssocData.setAddBy(IXCatalogItemLoader.IXCITEMLOADER);
                catalogAssocData.setModBy(IXCatalogItemLoader.IXCITEMLOADER);
                catalogAssocData = CatalogAssocDataAccess.insert(mCon, catalogAssocData);
            }

            log.debug("doSynchronize()=> END.");

            return catalogAssocData;

        }

    }

    public class ContractSynchronizeData extends SynchronizeData<ContractData> {

        private String mLocale;
        private Integer mFreightTableId;
        private CatalogData mCatalog;
        private CatalogReference mCatalogReference;
        private AccountReference mAccountReference;

        public ContractSynchronizeData(MasterAppData pMasterAppData,
                                       CatalogData pCatalog,
                                       String pLocale,
                                       Integer pFreightTableId,
                                       AccountReference pAccountReference,
                                       CatalogReference pCatalogReference) {
            super(pMasterAppData);
            mCatalog = pCatalog;
            mLocale = pLocale;
            mFreightTableId = pFreightTableId;
            mCatalogReference = pCatalogReference;
            mAccountReference = pAccountReference;
        }

        public CatalogReference getCatalogReference() {
            return mCatalogReference;
        }

        public void setCatalogReference(CatalogReference pCatalogReference) {
            this.mCatalogReference = pCatalogReference;
        }

        public AccountReference getAccountReference() {
            return mAccountReference;
        }

        public boolean isSynchronized() {

            Map<AccountReference, AppAccountReferenceData> accountRefMap = mMasterData.getAppAccountReferenceMap();

            if (accountRefMap.get(mAccountReference).getAppCatalogMap().get(mCatalogReference).getContract() != null) {

                ContractData contract = accountRefMap.get(mAccountReference).getAppCatalogMap().get(mCatalogReference).getContract();

                boolean wasChanged = false;

                if (Utility.isSet(this.getLocale()) && Utility.isSet(contract.getLocaleCd()) && !getLocale().equals(contract.getLocaleCd())) {
//                    contract.setLocaleCd(getLocale());
                    wasChanged = true;
                }

                if (Utility.intNN(getFreightTableId()) != contract.getFreightTableId()) {
                    wasChanged = true;
                }

                return !wasChanged;

            }

            return false;

        }

        public void refresh(ContractData pValue) {
            mMasterData.getAppAccountReferenceMap().get(mAccountReference).getAppCatalogMap().get(mCatalogReference).setContract(pValue);
        }

        public ContractData getValue() {
            return mMasterData.getAppAccountReferenceMap().get(mAccountReference).getAppCatalogMap().get(mCatalogReference).getContract();
        }

        public String getLocale() {
            return mLocale;
        }

        public Integer getFreightTableId() {
            return mFreightTableId;
        }

        public CatalogData getCatalog() {
            return mCatalog;
        }
    }

    public class ContractDBAgent implements DBWorker {

        private Logger log = Logger.getLogger(ContractDBAgent.class);

        private ContractSynchronizeData mSynchData;

        private Connection mCon;

        public ContractDBAgent(Connection pCon, ContractSynchronizeData pSynchData) {
            mCon = pCon;
            mSynchData = pSynchData;
        }

        public void doWork() throws Exception {
            if (!mSynchData.isSynchronized()) {
                ContractData contract = doSynchronize();
                mSynchData.refresh(contract);
            }
        }

        private ContractData doSynchronize() throws Exception {

            log.debug("doSynchronize()=> BEGIN");

            log.debug("doSynchronize()=> XXX StoreID: " + mSynchData.getMasterData().getStoreId());
            log.debug("doSynchronize()=> XXX AccountReference: " + mSynchData.getAccountReference());
            log.debug("doSynchronize()=> XXX ContractReference: " + mSynchData.getCatalogReference());

            ContractData contractData = mSynchData.getValue();

            if (contractData == null) {

                contractData = ContractData.createValue();

                contractData.setEffDate(new Date());
                contractData.setCatalogId(mSynchData.getCatalog().getCatalogId());
                contractData.setLocaleCd(mSynchData.getLocale());
                contractData.setFreightTableId(Utility.intNN(mSynchData.getFreightTableId()));

                contractData.setAddBy(IXCatalogItemLoader.IXCITEMLOADER);
                contractData.setModBy(IXCatalogItemLoader.IXCITEMLOADER);
                contractData.setRefContractNum(ZERO);
                contractData.setShortDesc(mSynchData.getCatalog().getLoaderField());
                contractData.setContractStatusCd(RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
                contractData.setContractTypeCd(UNKNOWN);

                contractData = ContractDataAccess.insert(mCon, contractData);


            } else {

                boolean wasChanged = false;

                if (Utility.isSet(mSynchData.getLocale())
                        && Utility.isSet(contractData.getLocaleCd())
                        && !mSynchData.getLocale().equals(contractData.getLocaleCd())) {
                    contractData.setLocaleCd(mSynchData.getLocale());
                    wasChanged = true;
                }

                if (Utility.intNN(mSynchData.getFreightTableId()) != contractData.getFreightTableId()) {
                    contractData.setFreightTableId(Utility.intNN(mSynchData.getFreightTableId()));
                    wasChanged = true;
                }

                if (wasChanged) {
                    ContractDataAccess.update(mCon, contractData);
                }

            }

            log.debug("doSynchronize()=> END.");

            return contractData;

        }

    }

    public abstract class CategorySynchronizeData extends SynchronizeData<ItemData> {

        private Integer mStoreCatalogId;
        public CategoryReference mCategoryReference;

        public CategorySynchronizeData(MasterAppData pMasterAppData,
                                       Integer pCatalogId,
                                       CategoryReference pCategoryReference) {
            super(pMasterAppData);
            mStoreCatalogId = pCatalogId;
            this.mCategoryReference = pCategoryReference;
        }


        public String getName() {
            return mCategoryReference.getCategoryName();
        }

        public String getAdminName() {
            return mCategoryReference.getAdminName();
        }

        public Integer getStoreCatalogId() {
            return mStoreCatalogId;
        }
    }

    public class StoreCategorySynchronizeData extends CategorySynchronizeData {

        public StoreCategorySynchronizeData(MasterAppData pMasterAppData,
                                            Integer pCatalogId,
                                            CategoryReference pCategoryReference) {
            super(pMasterAppData, pCatalogId, pCategoryReference);
        }

        public boolean isSynchronized() {
            ItemData category = mMasterData.getAppStoreCategoryMap().get(mCategoryReference);
            return category != null && category.getItemId() > 0;
        }

        public void refresh(ItemData pValue) {
            mMasterData.getAppStoreCategoryMap().put(mCategoryReference, pValue);
        }

        public ItemData getValue() {
            return mMasterData.getAppStoreCategoryMap().get(mCategoryReference);
        }


    }

    public class CategoryDBAgent implements DBWorker {

        private Logger log = Logger.getLogger(CategoryDBAgent.class);

        private CategorySynchronizeData mSynchData;

        private Connection mCon;

        public CategoryDBAgent(Connection pCon, CategorySynchronizeData pSynchData) {
            mCon = pCon;
            mSynchData = pSynchData;
        }

        public void doWork() throws Exception {
            if (!mSynchData.isSynchronized()) {
                ItemData category = doSynchronize();
                mSynchData.refresh(category);
            }
        }

        private ItemData doSynchronize() throws Exception {

            log.debug("synchronize()=> BEGIN");

            log.debug("synchronize()=> XXX StoreID: " + mSynchData.getMasterData().getStoreId());
            log.debug("synchronize()=> XXX Categ.Name: " + mSynchData.getName());

            ItemData category;

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(ItemDataAccess.SHORT_DESC, mSynchData.getName());
            if (Utility.isSet(mSynchData.getAdminName())) {
                crit.addEqualTo(ItemDataAccess.LONG_DESC, mSynchData.getAdminName());
            }
            crit.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.CATEGORY);
            crit.addEqualTo(ItemDataAccess.ITEM_STATUS_CD, RefCodeNames.ITEM_STATUS_CD.ACTIVE);
            crit.addJoinCondition(ItemDataAccess.ITEM_ID, CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.ITEM_ID);
            crit.addJoinTableEqualTo(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE, CatalogStructureDataAccess.CATALOG_ID, mSynchData.getStoreCatalogId());

            ItemDataVector itemDataV = ItemDataAccess.select(mCon, crit);

            ItemData itemData = ItemData.createValue();
            if (itemDataV != null && itemDataV.size() > 0) {
                category = (ItemData) itemDataV.get(0);
            } else {
                itemData.setShortDesc(mSynchData.getName());
                itemData.setLongDesc(mSynchData.getAdminName());
                itemData.setItemTypeCd(RefCodeNames.ITEM_TYPE_CD.CATEGORY);
                itemData.setItemStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
                itemData.setAddBy(IXCatalogItemLoader.IXCITEMLOADER);
                itemData.setModBy(IXCatalogItemLoader.IXCITEMLOADER);
                category = ItemDataAccess.insert(mCon, itemData);
            }

            log.debug("synchronize()=> END, CategID: " + category.getItemId());

            return category;

        }

    }

    public abstract class CatalogStructureSynchronizeData extends SynchronizeData<CatalogStructureData> {

        public Integer mCatalogId;
        public Integer mItemId;
        public String mCatalogStructureCd;
        public String mItemSkuNum;
        public Integer mCostCenterId;
        public Integer mItemGroupId;
        public Boolean mSpecialPermission;
        private String mItemDesc;
        private Integer nDistributorId;


        protected CatalogStructureSynchronizeData(MasterAppData pMasterData,
                                                  Integer pItemId,
                                                  String pCatalogStructureCd,
                                                  Integer pCostCenterId,
                                                  Integer pCatalogId,
                                                  Integer pItemGroupId,
                                                  Boolean pSpecialPermission,
                                                  String pItemSkuNum,
                                                  String pItemDesc,
                                                  Integer pDistributorId) {
            super(pMasterData);
            this.mCatalogId = pCatalogId;
            this.mItemId = pItemId;
            this.mCatalogStructureCd = pCatalogStructureCd;
            this.mCostCenterId = pCostCenterId;
            this.mItemGroupId = pItemGroupId;
            this.mSpecialPermission = pSpecialPermission;
            this.mItemSkuNum = pItemSkuNum;
            this.mItemDesc = pItemDesc;
            this.nDistributorId = pDistributorId;
        }

        public Integer getCatalogId() {
            return mCatalogId;
        }

        public Integer getItemId() {
            return mItemId;
        }

        public String getCatalogStructureCd() {
            return mCatalogStructureCd;
        }

        public Integer getCostCenterId() {
            return mCostCenterId;
        }

        public Integer getItemGroupId() {
            return mItemGroupId;
        }

        public Boolean getSpecialPermission() {
            return mSpecialPermission;
        }


        public String getItemSkuNum() {
            return mItemSkuNum;
        }

        public String getItemDesc() {
            return mItemDesc;
        }

        public Integer getDistributorId() {
            return nDistributorId;
        }
    }

    public class StoreCatalogStructureSynchronizeData extends CatalogStructureSynchronizeData {

        public StoreCatalogStructureSynchronizeData(MasterAppData pMasterData,
                                                    ItemData pItemData,
                                                    String mCatalogStructureCd,
                                                    Integer mCatalogId) {
            super(pMasterData,
                    pItemData.getItemId(),
                    mCatalogStructureCd,
                    null,
                    mCatalogId,
                    null,
                    null,
                    null,
                    null,
                    null);
        }

        public boolean isSynchronized() {

            Map<Integer, CatalogStructureData> catCategoryMap = mMasterData.getAppStoreCatalogCategoryMap();
            if (catCategoryMap != null) {
                CatalogStructureData catalogCategory = catCategoryMap.get(mItemId);
                if (catalogCategory != null && catalogCategory.getCatalogStructureId() > 0) {
                    return true;
                }
            }
            return false;
        }

        public void refresh(CatalogStructureData pValue) {
            Map<Integer, CatalogStructureData> catCategoryMap = mMasterData.getAppStoreCatalogCategoryMap();
            if (catCategoryMap == null) {
                catCategoryMap = new HashMap<Integer, CatalogStructureData>();
                mMasterData.setAppStoreCatalogCategoryMap(catCategoryMap);
            }
            catCategoryMap.put(mItemId, pValue);
        }

        public CatalogStructureData getValue() {
            return mMasterData.getAppStoreCatalogCategoryMap().get(mItemId);
        }

    }

    public class AccountCatalogStructureSynchronizeData extends CatalogStructureSynchronizeData {

        private AccountReference mAccountReference;
        private Integer mAccountId;

        public AccountCatalogStructureSynchronizeData(MasterAppData pMasterData,
                                                      ItemData pItemData,
                                                      String pCatalogStructureCd,
                                                      Integer pCostCenterId,
                                                      Integer pCatalogId,
                                                      AccountReference pAccountReference,
                                                      Integer pAccountId) {
            super(pMasterData,
                    pItemData.getItemId(),
                    pCatalogStructureCd,
                    pCostCenterId,
                    pCatalogId,
                    null,
                    null,
                    null,
                    null,
                    null);
            mAccountReference = pAccountReference;
            mAccountId = pAccountId;
        }

        public boolean isSynchronized() {

            Map<Integer, CatalogStructureData> catCategoryMap = mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppAcountDataMap()
                    .get(mAccountId).getAppAccountCatalogCategoryMap();
            if (catCategoryMap != null) {
                CatalogStructureData catalogCategory = catCategoryMap.get(mItemId);
                if (catalogCategory != null
                        && catalogCategory.getCatalogStructureId() > 0
                        && catalogCategory.getCostCenterId() == Utility.intNN(getCostCenterId())) {
                    return true;
                }
            }
            return false;
        }

        public void refresh(CatalogStructureData pValue) {
            Map<Integer, CatalogStructureData> catCategoryMap = mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppAcountDataMap()
                    .get(mAccountId).getAppAccountCatalogCategoryMap();
            if (catCategoryMap == null) {
                catCategoryMap = new HashMap<Integer, CatalogStructureData>();
                mMasterData
                        .getAppAccountReferenceMap()
                        .get(mAccountReference)
                        .getAppAcountDataMap()
                        .get(mAccountId).setAppAccountCatalogCategoryMap(catCategoryMap);
            }
            catCategoryMap.put(mItemId, pValue);
        }

        public CatalogStructureData getValue() {
            return mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppAcountDataMap()
                    .get(mAccountId).getAppAccountCatalogCategoryMap().get(mItemId);
        }

    }

    public class ShoppingCatalogStructureSynchronizeData extends CatalogStructureSynchronizeData {

        private AccountReference mAccountReference;
        private CatalogReference mCatalogReference;

        public ShoppingCatalogStructureSynchronizeData(MasterAppData pMasterData,
                                                       ItemData pItemData,
                                                       String pCatalogStructureCd,
                                                       Integer pCostCenterId,
                                                       Integer pCatalogId,
                                                       AccountReference pAccountReference,
                                                       CatalogReference pCatalogReference) {
            super(pMasterData,
                    pItemData.getItemId(),
                    pCatalogStructureCd,
                    pCostCenterId,
                    pCatalogId,
                    null,
                    null,
                    null,
                    null,
                    null);

            mAccountReference = pAccountReference;
            mCatalogReference = pCatalogReference;
        }

        public boolean isSynchronized() {

            Map<Integer, CatalogStructureData> catCategoryMap = mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppCatalogMap()
                    .get(mCatalogReference)
                    .getAppShoppingCatalogCategoryMap();
            if (catCategoryMap != null) {
                CatalogStructureData catalogCategory = catCategoryMap.get(mItemId);
                if (catalogCategory != null
                        && catalogCategory.getCatalogStructureId() > 0
                        && catalogCategory.getCostCenterId() == Utility.intNN(getCostCenterId())) {
                    return true;
                }
            }
            return false;
        }

        public void refresh(CatalogStructureData pValue) {
            Map<Integer, CatalogStructureData> catCategoryMap = mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppCatalogMap()
                    .get(mCatalogReference)
                    .getAppShoppingCatalogCategoryMap();
            if (catCategoryMap == null) {
                catCategoryMap = new HashMap<Integer, CatalogStructureData>();
                mMasterData
                        .getAppAccountReferenceMap()
                        .get(mAccountReference)
                        .getAppCatalogMap()
                        .get(mCatalogReference).setAppShoppingCatalogCategoryMap(catCategoryMap);
            }
            catCategoryMap.put(mItemId, pValue);
        }

        public CatalogStructureData getValue() {
            return mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppCatalogMap()
                    .get(mCatalogReference)
                    .getAppShoppingCatalogCategoryMap()
                    .get(mItemId);
        }

    }

    public class CatalogStructureDBAgent implements DBWorker {

        private Logger log = Logger.getLogger(CatalogStructureDBAgent.class);

        private CatalogStructureSynchronizeData mSynchData;

        private Connection mCon;

        public CatalogStructureDBAgent(Connection pCon, CatalogStructureSynchronizeData pSynchData) {
            mCon = pCon;
            mSynchData = pSynchData;
        }

        public void doWork() throws Exception {
            if (!mSynchData.isSynchronized()) {
                CatalogStructureData catalogStructure = doSynchronize();
                mSynchData.refresh(catalogStructure);
            }
        }

        private CatalogStructureData doSynchronize() throws Exception {

            log.debug("doSynchronize()=> BEGIN");

            log.debug("doSynchronize()=> XXX StoreID   : " + mSynchData.getMasterData().getStoreId());
            log.debug("doSynchronize()=> XXX ItemID: " + mSynchData.getItemId());
            log.debug("doSynchronize()=> XXX CatalogStructureCD: " + mSynchData.getCatalogStructureCd());
            log.debug("doSynchronize()=> XXX CatalogID : " + mSynchData.getCatalogId());

            boolean wasChanged = false;

            CatalogStructureData catalogStructureData = CatalogStructureData.createValue();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, mSynchData.getCatalogId());
            crit.addEqualTo(CatalogStructureDataAccess.ITEM_ID, mSynchData.getItemId());
            crit.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, mSynchData.getCatalogStructureCd());

            CatalogStructureDataVector csDV = CatalogStructureDataAccess.select(mCon, crit);

            if (csDV != null && csDV.size() > 0) {
                catalogStructureData = (CatalogStructureData) csDV.get(0);
            } else {
                catalogStructureData.setCatalogId(mSynchData.getCatalogId());
                catalogStructureData.setCatalogStructureCd(mSynchData.getCatalogStructureCd());
                catalogStructureData.setItemId(mSynchData.getItemId());
                catalogStructureData.setAddBy(IXCatalogItemLoader.IXCITEMLOADER);
                catalogStructureData.setModBy(IXCatalogItemLoader.IXCITEMLOADER);
            }

            if (Utility.intNN(mSynchData.getCostCenterId()) != catalogStructureData.getCostCenterId()) {
                catalogStructureData.setCostCenterId(Utility.intNN(mSynchData.getCostCenterId()));
                wasChanged = true;
            }

            if (Utility.intNN(mSynchData.getItemGroupId()) != catalogStructureData.getItemGroupId()) {
                catalogStructureData.setItemGroupId(Utility.intNN(mSynchData.getItemGroupId()));
                wasChanged = true;
            }

            if (mSynchData.getSpecialPermission() == null && catalogStructureData.getSpecialPermission() != null) {
                catalogStructureData.setSpecialPermission(null);
                wasChanged = true;
            } else
            if (mSynchData.getSpecialPermission() == null && catalogStructureData.getSpecialPermission() == null) {

            } else
            if (mSynchData.getSpecialPermission() != Utility.isTrue(catalogStructureData.getSpecialPermission())) {
                catalogStructureData.setSpecialPermission(String.valueOf(mSynchData.getSpecialPermission()));
                wasChanged = true;
            }

            // will not save catalog product description, leave is null or as it is
            /*if (!Utility.strNN(mSynchData.getItemDesc()).equals(Utility.strNN(catalogStructureData.getShortDesc()))) {
                catalogStructureData.setShortDesc(mSynchData.getItemDesc());
                wasChanged = true;
            }*/

            if (!Utility.strNN(mSynchData.getItemSkuNum()).equals(Utility.strNN(catalogStructureData.getCustomerSkuNum()))) {
                catalogStructureData.setCustomerSkuNum(mSynchData.getItemSkuNum());
                wasChanged = true;
            }

            if (Utility.intNN(mSynchData.getDistributorId()) != catalogStructureData.getBusEntityId()) {
                catalogStructureData.setBusEntityId(Utility.intNN(mSynchData.getDistributorId()));
                wasChanged = true;
            }

            if (catalogStructureData.getCatalogStructureId() > 0 && wasChanged) {
                CatalogStructureDataAccess.update(mCon, catalogStructureData);
            } else if (catalogStructureData.getCatalogStructureId() <= 0) {
                catalogStructureData = CatalogStructureDataAccess.insert(mCon, catalogStructureData);
            }

            log.debug("doSynchronize()=> END.");

            return catalogStructureData;

        }
    }

    public class CategoryAssocSynchronizeData extends SynchronizeData<Integer> {

        private ItemData mChildId;
        private ItemData mParentId;
        private Integer mStoreCatalogId;

        public CategoryAssocSynchronizeData(MasterAppData pMasterAppData, Integer pStoreCatalogId, CategoryReference pChild, CategoryReference pParent) {
            super(pMasterAppData);
            this.mStoreCatalogId = pStoreCatalogId;
            this.mChildId = mMasterData.getAppStoreCategoryMap().get(pChild);
            this.mParentId = mMasterData.getAppStoreCategoryMap().get(pParent);
        }

        public boolean isSynchronized() {
            return mMasterData.getAppCategoryAssocMap().get(this.mChildId.getItemId()) != null
                    && mMasterData.getAppCategoryAssocMap().get(this.mChildId.getItemId()) == this.mParentId.getItemId();
        }

        public void refresh(Integer pValue) {
            mMasterData.getAppCategoryAssocMap().put(mChildId.getItemId(), pValue);
        }

        public Integer getValue() {
            return mMasterData.getAppCategoryAssocMap().get(this.mChildId.getItemId());
        }

        public Integer getParentId() {
            return mParentId.getItemId();
        }
        public Integer getStoreCatalogId() {
            return mStoreCatalogId;
        }

        public Integer getChildId() {
            return mChildId.getItemId();
        }
    }

    public class CategoryAssocDBAgent implements DBWorker {

        private Logger log = Logger.getLogger(CategoryAssocDBAgent.class);

        private CategoryAssocSynchronizeData mSynchData;

        private Connection mCon;

        public CategoryAssocDBAgent(Connection pCon, CategoryAssocSynchronizeData pSynchData) {
            mCon = pCon;
            mSynchData = pSynchData;
        }

        public void doWork() throws Exception {
            if (!mSynchData.isSynchronized()) {
                Integer parent = doSynchronize();
                mSynchData.refresh(parent);
            }
        }

        private Integer doSynchronize() throws Exception {

            log.debug("doSynchronize()=> BEGIN");

            ItemAssocData itemAssoc;

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(ItemAssocDataAccess.CATALOG_ID, mSynchData.getStoreCatalogId());
            crit.addEqualTo(ItemAssocDataAccess.ITEM1_ID, mSynchData.getChildId());

            ItemAssocDataVector iaDV = ItemAssocDataAccess.select(mCon, crit);
            if (iaDV.size() == 1) {
                itemAssoc = (ItemAssocData) iaDV.get(0);
                log.debug("doSynchronize()=> XXX category structure for catalog" + mSynchData.getStoreCatalogId() + " will be change");
                log.debug("doSynchronize()=> XXX parent for child " + mSynchData.getChildId() +
                        " will be replaced from " + itemAssoc.getItem2Id() +
                        " to " + mSynchData.getParentId());
                itemAssoc.setItem2Id(mSynchData.getParentId());
                itemAssoc.setModBy(IXCatalogItemLoader.IXCITEMLOADER);
                ItemAssocDataAccess.update(mCon, itemAssoc);
            } else if (iaDV.size() > 1) {
                throw new Exception("Found multiple parents for category: " + mSynchData.getChildId() + ", catalogID: " + mSynchData.getStoreCatalogId());
            } else {

                itemAssoc = ItemAssocData.createValue();
                itemAssoc.setAddBy(IXCatalogItemLoader.IXCITEMLOADER);
                itemAssoc.setModBy(IXCatalogItemLoader.IXCITEMLOADER);
                itemAssoc.setCatalogId(mSynchData.getStoreCatalogId());
                itemAssoc.setItem1Id(mSynchData.getChildId());
                itemAssoc.setItem2Id(mSynchData.getParentId());
                itemAssoc.setItemAssocCd(RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);

                log.debug("doSynchronize()=> XXX create category assoc: " + itemAssoc);
                itemAssoc = ItemAssocDataAccess.insert(mCon, itemAssoc);
            }

            log.debug("doSynchronize()=> END.");

            return itemAssoc.getItem2Id();

        }
    }

    public class ItemSynchronizeData extends SynchronizeData<ItemData> {

        private ItemReference mItemReference;
        private BusEntityData mDistributor;
        private XpedxCatalogItemView mInboundItem;

        public ItemSynchronizeData(MasterAppData pMasterData,
                                   XpedxCatalogItemView pInboundItem,
                                   BusEntityData pDistributor,
                                   ItemReference pItemReference) {
            super(pMasterData);
            this.mInboundItem = pInboundItem;
            this.mDistributor = pDistributor;
            this.mItemReference = pItemReference;
        }

        public boolean isSynchronized() {
            ItemData item = mMasterData
                    .getAppItemMap()
                    .get(mItemReference)
                    .getItemData();

            return item != null
                    && Utility.strNN(mInboundItem.getShortDescription()).equals(item.getShortDesc())
                    && Utility.strNN(mInboundItem.getLongDescription()).equals(item.getLongDesc());

        }

        public void refresh(ItemData pValue) {
            mMasterData
                    .getAppItemMap()
                    .get(mItemReference)
                    .setItemData(pValue);
        }

        public ItemData getValue() {
            return mMasterData
                    .getAppItemMap()
                    .get(mItemReference)
                    .getItemData();
        }

        public Integer getDistributorId() {
            return mDistributor.getBusEntityId();
        }


        public XpedxCatalogItemView getInboundItem() {
            return mInboundItem;
        }
    }

    public class ItemDBAgent implements DBWorker {

        private Logger log = Logger.getLogger(ItemDBAgent.class);

        private ItemSynchronizeData mSynchData;

        private Connection mCon;

        public ItemDBAgent(Connection pCon, ItemSynchronizeData pSynchData) {
            mCon = pCon;
            mSynchData = pSynchData;
        }

        public void doWork() throws Exception {
            if (!mSynchData.isSynchronized()) {
                ItemData item = doSynchronize();
                mSynchData.refresh(item);
            }
        }

        private ItemData doSynchronize() throws Exception {

            log.debug("doSynchronize()=> BEGIN");

            ItemData item = mSynchData.getValue();
            XpedxCatalogItemView inboundItem = mSynchData.getInboundItem();

            if (item == null) {

                DBCriteria cr = new DBCriteria();

                cr.addEqualTo(ItemDataAccess.ITEM_STATUS_CD, RefCodeNames.ITEM_STATUS_CD.ACTIVE);
                cr.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.PRODUCT);
                cr.addJoinTableEqualTo(ItemMappingDataAccess.CLW_ITEM_MAPPING, ItemMappingDataAccess.BUS_ENTITY_ID, mSynchData.getDistributorId());
                cr.addJoinTableEqualTo(ItemMappingDataAccess.CLW_ITEM_MAPPING, ItemMappingDataAccess.ITEM_NUM, inboundItem.getDistSKU());
                cr.addJoinCondition(ItemDataAccess.ITEM_ID, ItemMappingDataAccess.CLW_ITEM_MAPPING, ItemMappingDataAccess.ITEM_ID);

                ItemDataVector items = ItemDataAccess.select(mCon, cr);
                if (items.size() == 1) {
                    item = (ItemData) items.get(0);
                } else if (items.size() > 1) {
                    throw new Exception("Found duplicated item for reference: " + new ItemReference(inboundItem.getDistributor(), inboundItem.getDistSKU()));
                } else {

                    item = ItemData.createValue();

                    item.setItemStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
                    item.setItemTypeCd(RefCodeNames.ITEM_TYPE_CD.PRODUCT);
                    item.setLongDesc(inboundItem.getLongDescription());
                    item.setShortDesc(inboundItem.getShortDescription());
                    item.setEffDate(new Date());
                    item.setAddBy(IXCatalogItemLoader.IXCITEMLOADER);
                    item.setModBy(IXCatalogItemLoader.IXCITEMLOADER);

                    item = ItemDataAccess.insert(mCon, item);

                    item.setSkuNum(item.getItemId() + 10000);

                    ItemDataAccess.update(mCon, item);

                }

            }

            if (!Utility.strNN(inboundItem.getShortDescription()).equals(item.getShortDesc())
                    || !Utility.strNN(inboundItem.getLongDescription()).equals(item.getLongDesc())) {

                boolean wasChanged = false;

                if (Utility.isSet(inboundItem.getShortDescription())) {
                    wasChanged = true;
                    item.setShortDesc(inboundItem.getShortDescription());
                }

                if (Utility.isSet(inboundItem.getLongDescription())) {
                    wasChanged = true;
                    item.setLongDesc(inboundItem.getLongDescription());
                }

                if (wasChanged) {
                    ItemDataAccess.update(mCon, item);
                }
            }

            log.debug("doSynchronize()=> END.");


            return item;

        }

    }

    public abstract class ItemMappingSynchronizeData extends SynchronizeData<ItemMappingData> {


        public ItemMappingSynchronizeData(MasterAppData pMasterData) {
            super(pMasterData);
        }


        public abstract ItemData getItem();

        public abstract int getBusEntityId();

        public abstract String getItemMappingCd();

        public abstract String getSku();

        public abstract String getUom();

        public abstract String getPack();
    }

    public class ItemDistributorSynchronizeData extends ItemMappingSynchronizeData {

        public ItemReference mItemReference;
        public BusEntityData mBusEntity;
        public ItemData mItem;
        public XpedxCatalogItemView mInboundItem;

        public ItemDistributorSynchronizeData(MasterAppData pMasterData,
                                              BusEntityData pBusEntity,
                                              ItemData pItem,
                                              XpedxCatalogItemView pInboundItem,
                                              ItemReference pItemReference) {
            super(pMasterData);
            this.mBusEntity = pBusEntity;
            this.mItem = pItem;
            this.mInboundItem = pInboundItem;
            this.mItemReference = pItemReference;
        }


        public boolean isSynchronized() {

            ItemMappingData itemDistrMapping = mMasterData
                    .getAppItemMap()
                    .get(mItemReference)
                    .getDistributor();

            if (itemDistrMapping != null) {

                boolean wasChanged = false;

                if (!Utility.strNN(mInboundItem.getDistSKU()).equals(itemDistrMapping.getItemNum())) {
                    log.debug("isSynchronized()=> DistSKU chnaged");
                    wasChanged = true;
                }

                if (Utility.isSet(mInboundItem.getDistUOM())) {
                    if (!mInboundItem.getDistUOM().equals(itemDistrMapping.getItemUom())) {
                        log.debug("isSynchronized()=> DistUOM chnaged");
                        wasChanged = true;
                    }
                } else if (!Utility.strNN(itemDistrMapping.getItemUom()).equals(mInboundItem.getUOM())) {
                    log.debug("isSynchronized()=> DistUOM2 chnaged");
                    wasChanged = true;
                }

                if (Utility.isSet(mInboundItem.getDistPack())) {
                    if (!mInboundItem.getDistPack().equals(itemDistrMapping.getItemPack())) {
                        log.debug("isSynchronized()=> DistPack chnaged");
                        wasChanged = true;
                    }
                } else if (!Utility.strNN(itemDistrMapping.getItemPack()).equals(mInboundItem.getPack())) {
                    log.debug("isSynchronized()=> DistPack2 chnaged");
                    wasChanged = true;
                }

                return !wasChanged;

            }

            return false;

        }

        public void refresh(ItemMappingData pValue) {
            mMasterData.getAppItemMap()
                    .get(mItemReference)
                    .setDistributor(pValue);
        }

        public ItemMappingData getValue() {
            return mMasterData
                    .getAppItemMap()
                    .get(mItemReference).getDistributor();
        }

        public ItemData getItem() {
            return mItem;
        }

        public int getBusEntityId() {
            return mBusEntity.getBusEntityId();
        }

        public String getItemMappingCd() {
            return RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR;
        }

        public String getSku() {
            return mInboundItem.getDistSKU();
        }

        public String getUom() {
            return Utility.isSet(mInboundItem.getDistUOM())
                    ? mInboundItem.getDistUOM()
                    : mInboundItem.getUOM();
        }

        public String getPack() {
            return Utility.isSet(mInboundItem.getDistPack())
                    ? mInboundItem.getDistPack()
                    : mInboundItem.getPack();
        }

    }

    public class ItemManufacturerSynchronizeData extends ItemMappingSynchronizeData {

        private ItemReference mItemReference;
        private ItemData mItem;
        private BusEntityData mManufacturer;
        private XpedxCatalogItemView mInboundItem;


        public ItemManufacturerSynchronizeData(MasterAppData pMasterData,
                                               BusEntityData pManufacturer,
                                               ItemData pItem,
                                               XpedxCatalogItemView pInboundItem,
                                               ItemReference pItemReference) {
            super(pMasterData);
            this.mManufacturer = pManufacturer;
            this.mItem = pItem;
            this.mInboundItem = pInboundItem;
            this.mItemReference = pItemReference;
        }

        public boolean isSynchronized() {

            ItemMappingData itemManufMapping = mMasterData
                    .getAppItemMap()
                    .get(mItemReference)
                    .getManufacturer();

            if (itemManufMapping != null) {

                boolean wasChanged = false;
                if (!Utility.strNN(mInboundItem.getMfgSKU()).equals(itemManufMapping.getItemNum())) {
                    log.debug("isSynchronized()=> MfgSKU changed from  " + mInboundItem.getMfgSKU() + " to " + itemManufMapping.getItemNum());
                    wasChanged = true;
                }

                if (mManufacturer != null && mManufacturer.getBusEntityId() != itemManufMapping.getBusEntityId()) {
                    log.debug("isSynchronized()=> Manufacturer changed from  " + mManufacturer.getBusEntityId() + " to " + itemManufMapping.getBusEntityId());
                    wasChanged = true;
                }

                return !wasChanged;

            }

            return false;

        }

        public void refresh(ItemMappingData pValue) {
            mMasterData
                    .getAppItemMap()
                    .get(mItemReference)
                    .setManufacturer(pValue);
        }

        public ItemMappingData getValue() {
            return mMasterData
                    .getAppItemMap()
                    .get(mItemReference).getManufacturer();
        }

        public ItemData getItem() {
            return mItem;
        }

        public int getBusEntityId() {
            return mManufacturer.getBusEntityId();
        }

        public String getItemMappingCd() {
            return RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER;
        }

        public String getSku() {
            return mInboundItem.getMfgSKU();
        }

        public String getUom() {
            return null;
        }

        public String getPack() {
            return null;
        }


    }

    public class ItemStoreSynchronizeData extends ItemMappingSynchronizeData {

        private ItemReference mItemReference;
        private ItemData mItem;
        private Integer mStoreId;

        public ItemStoreSynchronizeData(MasterAppData pMasterData,
                                        Integer pStoreId,
                                        ItemData pItem,
                                        ItemReference pItemReference) {
            super(pMasterData);
            this.mStoreId = pStoreId;
            this.mItem = pItem;
            this.mItemReference = pItemReference;
        }

        public boolean isSynchronized() {

            ItemMappingData itemStoreMapping = mMasterData
                    .getAppItemMap()
                    .get(mItemReference)
                    .getStore();

            return itemStoreMapping != null;

        }

        public void refresh(ItemMappingData pValue) {
            mMasterData
                    .getAppItemMap()
                    .get(mItemReference)
                    .setStore(pValue);
        }

        public ItemMappingData getValue() {
            return mMasterData
                    .getAppItemMap()
                    .get(mItemReference).getManufacturer();
        }

        public ItemData getItem() {
            return mItem;
        }

        public int getBusEntityId() {
            return mStoreId;
        }

        public String getItemMappingCd() {
            return RefCodeNames.ITEM_MAPPING_CD.ITEM_STORE;
        }

        public String getSku() {
            return String.valueOf(mItem.getSkuNum());
        }

        public String getUom() {
            return null;
        }

        public String getPack() {
            return null;
        }


    }

    public class ItemMappingDBAgent implements DBWorker {

        private Logger log = Logger.getLogger(ItemMappingDBAgent.class);

        private ItemMappingSynchronizeData mSynchData;

        private Connection mCon;

        public ItemMappingDBAgent(Connection pCon, ItemMappingSynchronizeData pSynchData) {
            mCon = pCon;
            mSynchData = pSynchData;
        }

        public void doWork() throws Exception {
            if (!mSynchData.isSynchronized()) {
                ItemMappingData itemMapping = doSynchronize();
                mSynchData.refresh(itemMapping);
            }
        }

        private ItemMappingData doSynchronize() throws Exception {

            log.debug("doSynchronize()=> BEGIN");

            ItemData itemData = mSynchData.getItem();

            int busEntityId = mSynchData.getBusEntityId();

            String itemMappingCd = mSynchData.getItemMappingCd();
            String sku = mSynchData.getSku();
            String uom = mSynchData.getUom();
            String pack = mSynchData.getPack();

            log.debug("doSynchronize()=> XXX StoreID       : " + mSynchData.getMasterData().getStoreId());
            log.debug("doSynchronize()=> XXX ItemMappingCD : " + itemMappingCd);
            log.debug("doSynchronize()=> XXX BusEntityID   : " + busEntityId);
            log.debug("doSynchronize()=> XXX SKU           : " + sku);

            DBCriteria dbCriteria = new DBCriteria();
            dbCriteria.addEqualTo(ItemMappingDataAccess.ITEM_ID, itemData.getItemId());
            if (RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR.equals(itemMappingCd)) {
                dbCriteria.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID, busEntityId);
            }
            dbCriteria.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD, itemMappingCd);
            dbCriteria.addJoinCondition(ItemMappingDataAccess.ITEM_ID, ItemDataAccess.CLW_ITEM, ItemDataAccess.ITEM_ID);
            dbCriteria.addJoinTableEqualTo(ItemDataAccess.CLW_ITEM, ItemDataAccess.ITEM_STATUS_CD, RefCodeNames.ITEM_STATUS_CD.ACTIVE);

            ItemMappingDataVector itemMappings = ItemMappingDataAccess.select(mCon, dbCriteria);

            ItemMappingData itemMapping;

            if (!itemMappings.isEmpty()) {

                itemMapping = (ItemMappingData) itemMappings.remove(0);

                boolean wasChanged = false;

                if (!Utility.strNN(sku).equals(itemMapping.getItemNum())) {
                    itemMapping.setItemNum(sku);
                    wasChanged = true;
                }

                if (Utility.isSet(uom)) {
                    if (!uom.equals(itemMapping.getItemUom())) {
                        itemMapping.setItemUom(uom);
                        wasChanged = true;
                    }
                } else if (!Utility.strNN(itemMapping.getItemUom()).equals(uom)) {
                    itemMapping.setItemUom(uom);
                    wasChanged = true;
                }

                if (Utility.isSet(pack)) {
                    if (!pack.equals(itemMapping.getItemPack())) {
                        itemMapping.setItemPack(pack);
                        wasChanged = true;
                    }
                } else if (!Utility.strNN(itemMapping.getItemPack()).equals(pack)) {
                    itemMapping.setItemPack(pack);
                    wasChanged = true;
                }

                if (wasChanged) {
                    itemMapping.setModBy(IXCatalogItemLoader.IXCITEMLOADER);
                    log.debug("doSynchronize()=> XXX Updating...");
                    ItemMappingDataAccess.update(mCon, itemMapping);
                }

                if (itemMappings.size() > 0) {
                    dbCriteria = new DBCriteria();
                    dbCriteria.addOneOf(ItemMappingDataAccess.ITEM_MAPPING_ID, Utility.toIdVector(itemMappings));
                    log.debug("doSynchronize()=> XXX Removing duplicates...");
                    ItemMappingDataAccess.remove(mCon, dbCriteria);
                }


            } else {
                itemMapping = InboundXpedxLoaderHelper.createItemMapping(itemData.getItemId(),
                        busEntityId,
                        itemMappingCd,
                        sku,
                        pack,
                        uom,
                        IXCatalogItemLoader.IXCITEMLOADER);
                log.debug("doSynchronize()=> Inserting...");
                itemMapping = ItemMappingDataAccess.insert(mCon, itemMapping);
            }

            log.debug("doSynchronize()=> END.");

            return itemMapping;
        }

    }

    public class ItemMetaSynchronizeData extends SynchronizeData<ItemMetaData> {

        private ItemReference mItemReference;
        private String mMetaName;
        private String mMetaValue;
        private ItemData mItem;


        public ItemMetaSynchronizeData(MasterAppData pMasterData,
                                       ItemData pItem,
                                       String pNameValue,
                                       String pValue,
                                       ItemReference pItemReference) {
            super(pMasterData);
            this.mItem = pItem;
            this.mMetaName = pNameValue;
            this.mMetaValue = pValue;
            this.mItemReference = pItemReference;
        }

        public boolean isSynchronized() {
            ItemMetaData meta = getValue();
            return meta != null && mMetaValue.equals(meta.getValue());
        }

        public void refresh(ItemMetaData pValue) {
            mMasterData
                    .getAppItemMap()
                    .get(mItemReference)
                    .getMeta()
                    .put(pValue.getNameValue(), pValue);
        }

        public ItemMetaData getValue() {

            Map<String, ItemMetaData> meta = mMasterData
                    .getAppItemMap()
                    .get(mItemReference)
                    .getMeta();

            if (meta == null) {
                meta = new HashMap<String, ItemMetaData>();
                mMasterData
                        .getAppItemMap()
                        .get(mItemReference).setMeta(meta);
            }

            return meta.get(mMetaName);
        }


        public String getMetaName() {
            return mMetaName;
        }

        public String getMetaValue() {
            return mMetaValue;
        }

        public ItemData getItem() {
            return mItem;
        }
    }

    public class ItemMetaDBAgent implements DBWorker {

        private Logger log = Logger.getLogger(ItemMetaDBAgent.class);

        private ItemMetaSynchronizeData mSynchData;

        private Connection mCon;

        public ItemMetaDBAgent(Connection pCon, ItemMetaSynchronizeData pSynchData) {
            mCon = pCon;
            mSynchData = pSynchData;
        }

        public void doWork() throws Exception {
            if (!mSynchData.isSynchronized()) {
                ItemMetaData itemMeta = doSynchronize();
                mSynchData.refresh(itemMeta);
            }
        }

        private ItemMetaData doSynchronize() throws Exception {

            log.debug("doSynchronize()=> BEGIN");

            log.debug("doSynchronize()=> XXX ItemID: " + ", Name: " + mSynchData.getMetaName() + ", Value: " + mSynchData.getMetaValue());

            ItemMetaData meta = mSynchData.getValue();
            if (meta == null) {

                DBCriteria dbCriteria = new DBCriteria();
                dbCriteria.addEqualTo(ItemMetaDataAccess.ITEM_ID, mSynchData.getItem().getItemId());
                dbCriteria.addEqualTo(ItemMetaDataAccess.NAME_VALUE, mSynchData.getMetaName());

                ItemMetaDataVector metas = ItemMetaDataAccess.select(mCon, dbCriteria);
                if (metas.isEmpty()) {
                    meta = ItemMetaData.createValue();

                    meta.setItemId(mSynchData.getItem().getItemId());
                    meta.setNameValue(mSynchData.getMetaName());
                    meta.setValue(mSynchData.getMetaValue());
                    meta.setAddBy(IXCatalogItemLoader.IXCITEMLOADER);
                    meta.setModBy(IXCatalogItemLoader.IXCITEMLOADER);

                    meta = ItemMetaDataAccess.insert(mCon, meta);

                } else if (metas.size() == 1) {

                    meta = (ItemMetaData) metas.get(0);

                } else {
                    throw new Exception("Item meta has duplicate value." +
                            " ItemID:" + mSynchData.getItem().getItemId() +
                            ", Value: " + mSynchData.getMetaName() + "'");
                }

            }

            if (!Utility.strNN(mSynchData.getMetaValue()).equals(meta.getValue())) {
                meta.setValue(mSynchData.getMetaValue());
                meta.setModBy(IXCatalogItemLoader.IXCITEMLOADER);
                ItemMetaDataAccess.update(mCon, meta);
            }

            log.debug("doSynchronize()=> END.");
            return meta;
        }


    }

    public class ProductStoreCatalogStructureSynchronizeData extends CatalogStructureSynchronizeData {


        public ProductStoreCatalogStructureSynchronizeData(MasterAppData pMasterData,
                                                           ItemData pItemData,
                                                           String pCatalogStructureCd,
                                                           Integer pCostCenterId,
                                                           Integer pCatalogId,
                                                           Integer pItemGroupId) {
            super(pMasterData,
                    pItemData.getItemId(),
                    pCatalogStructureCd,
                    pCostCenterId,
                    pCatalogId,
                    pItemGroupId,
                    null,
                    String.valueOf(pItemData.getSkuNum()),
                    pItemData.getShortDesc(),
                    null);

        }

        public boolean isSynchronized() {

            Map<Integer, CatalogStructureData> catProductMap = mMasterData.getAppStoreCatalogProductMap();

            if (catProductMap != null) {
                CatalogStructureData catalogProduct = catProductMap.get(mItemId);
                if (catalogProduct != null && catalogProduct.getCatalogStructureId() > 0) {
                    if (catalogProduct.getCostCenterId() == Utility.intNN(getCostCenterId())
                            && catalogProduct.getItemGroupId() == Utility.intNN(getItemGroupId())
                            //&& Utility.strNN(getItemDesc()).equals(Utility.strNN(catalogProduct.getShortDesc()))
                            && Utility.strNN(getItemSkuNum()).equals(Utility.strNN(catalogProduct.getCustomerSkuNum()))) {
                        return true;
                    }

                }
            }
            return false;
        }

        public void refresh(CatalogStructureData pValue) {
            mMasterData.getAppStoreCatalogProductMap().put(mItemId, pValue);
        }

        public CatalogStructureData getValue() {
            return mMasterData
                    .getAppStoreCatalogProductMap()
                    .get(mItemId);
        }

    }

    public class ProductAccountCatalogStructureSynchronizeData extends CatalogStructureSynchronizeData {

        private AccountReference mAccountReference;
        private Integer mAccountId;

        public ProductAccountCatalogStructureSynchronizeData(MasterAppData pMasterData,
                                                             ItemData pItemData,
                                                             String pCustomerSkuNum,
                                                             String pCustomerDesc,
                                                             String pCatalogStructureCd,
                                                             Integer pCostCenterId,
                                                             Integer pCatalogId,
                                                             Integer pItemGroupId,
                                                             Boolean pSpecialPermission,
                                                             AccountReference pAccountReference,
                                                             Integer pAccountId) {
            super(pMasterData,
                    pItemData.getItemId(),
                    pCatalogStructureCd,
                    pCostCenterId,
                    pCatalogId,
                    pItemGroupId,
                    pSpecialPermission,
                    pCustomerSkuNum,
                    Utility.isSet(pCustomerDesc) ? pCustomerDesc : String.valueOf(pItemData.getShortDesc()),
                    null);

            mAccountReference = pAccountReference;
            mAccountId = pAccountId;
        }

        public boolean isSynchronized() {

            Map<Integer, CatalogStructureData> catProductMap = mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppAcountDataMap()
                    .get(mAccountId).getAppAccountCatalogProductMap();

            if (catProductMap != null) {

                CatalogStructureData catalogStructureData = catProductMap.get(mItemId);


                if (catalogStructureData != null && catalogStructureData.getCatalogStructureId() > 0) {

                    boolean wasChanged = false;

                    if (Utility.intNN(getCostCenterId()) != catalogStructureData.getCostCenterId()) {
                        wasChanged = true;
                    }

                    if (Utility.intNN(getItemGroupId()) != catalogStructureData.getItemGroupId()) {
                        wasChanged = true;
                    }

                    if (getSpecialPermission() == null && catalogStructureData.getSpecialPermission() != null) {
                        wasChanged = true;
                    } else if (getSpecialPermission() == null && catalogStructureData.getSpecialPermission() == null) {

                    } else if (getSpecialPermission() != Utility.isTrue(catalogStructureData.getSpecialPermission())) {
                        wasChanged = true;
                    }

                    if (!Utility.strNN(getItemDesc()).equals(Utility.strNN(catalogStructureData.getShortDesc()))) {
                        log.debug("isSynchronized() ShortDesc  Changed");
                        wasChanged = true;
                    }

                    if (!Utility.strNN(getItemSkuNum()).equals(Utility.strNN(catalogStructureData.getCustomerSkuNum()))) {
                        log.debug("isSynchronized() CustomerSkuNum  Changed");
                        wasChanged = true;
                    }


                    return !wasChanged;

                }
            }

            return false;
        }

        public void refresh(CatalogStructureData pValue) {
            mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppAcountDataMap()
                    .get(mAccountId)
                    .getAppAccountCatalogProductMap()
                    .put(mItemId, pValue);
        }

        public CatalogStructureData getValue() {
            return mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppAcountDataMap()
                    .get(mAccountId).getAppAccountCatalogProductMap()
                    .get(mItemId);
        }

    }

    public class ProductShoppingCatalogStructureSynchronizeData extends CatalogStructureSynchronizeData {

        private Logger log = Logger.getLogger(ProductShoppingCatalogStructureSynchronizeData.class);

        private AccountReference mAccountReference;
        private CatalogReference mCatalogReference;

        public ProductShoppingCatalogStructureSynchronizeData(MasterAppData pMasterData,
                                                              ItemData pItemData,
                                                              String pCustomerSkuNum,
                                                              String pCustomerDesc,
                                                              Integer pDistributiorId,
                                                              String pCatalogStructureCd,
                                                              Integer pCostCenterId,
                                                              Integer pCatalogId,
                                                              Integer pItemGroupId,
                                                              AccountReference pAccountReference,
                                                              CatalogReference pCatalogReference) {
            super(pMasterData,
                    pItemData.getItemId(),
                    pCatalogStructureCd,
                    pCostCenterId,
                    pCatalogId,
                    pItemGroupId,
                    null,
                    pCustomerSkuNum,
                    Utility.isSet(pCustomerDesc) ? pCustomerDesc : String.valueOf(pItemData.getShortDesc()),
                    pDistributiorId);

            mAccountReference = pAccountReference;
            mCatalogReference = pCatalogReference;
        }

        public boolean isSynchronized() {

            Map<Integer, CatalogStructureData> catProductMap = mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppCatalogMap()
                    .get(mCatalogReference)
                    .getAppShoppingCatalogProductMap();

            if (catProductMap != null) {

                CatalogStructureData catalogStructureData = catProductMap.get(mItemId);

                if (catalogStructureData != null && catalogStructureData.getCatalogStructureId() > 0) {

                    boolean wasChanged = false;

                    if (Utility.intNN(getCostCenterId()) != catalogStructureData.getCostCenterId()) {
                        log.debug("isSynchronized() CostCenter  Changed");
                        wasChanged = true;
                    }

                    if (Utility.intNN(getItemGroupId()) != catalogStructureData.getItemGroupId()) {
                        log.debug("isSynchronized() ItemGroup  Changed");
                        wasChanged = true;
                    }

                    if (!Utility.strNN(getItemDesc()).equals(Utility.strNN(catalogStructureData.getShortDesc()))) {
                        log.debug("isSynchronized() ShortDesc  Changed");
                        wasChanged = true;
                    }

                    if (!Utility.strNN(getItemSkuNum()).equals(Utility.strNN(catalogStructureData.getCustomerSkuNum()))) {
                        log.debug("isSynchronized() CustomerSkuNum  Changed");
                        wasChanged = true;
                    }

                    if (Utility.intNN(getDistributorId()) != catalogStructureData.getBusEntityId()) {
                        log.debug("isSynchronized() Distributor  Changed");
                        wasChanged = true;
                    }

                    return !wasChanged;

                }
            }

            return false;
        }

        public void refresh(CatalogStructureData pValue) {

            Map<Integer, CatalogStructureData> catProductMap = mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppCatalogMap()
                    .get(mCatalogReference)
                    .getAppShoppingCatalogProductMap();
            if (catProductMap == null) {
                catProductMap = new HashMap<Integer, CatalogStructureData>();
                mMasterData
                        .getAppAccountReferenceMap()
                        .get(mAccountReference)
                        .getAppCatalogMap()
                        .get(mCatalogReference)
                        .setAppShoppingCatalogProductMap(catProductMap);
            }

            catProductMap.put(mItemId, pValue);


        }

        public CatalogStructureData getValue() {
            return mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppCatalogMap()
                    .get(mCatalogReference)
                    .getAppShoppingCatalogProductMap()
                    .get(mItemId);
        }

    }

    public abstract class ItemAssocSynchronizeData extends SynchronizeData<ItemAssocData> {

        private Integer mItem1Id;
        private Integer mItem2Id;
        private Integer mCatalogId;
        private String mItemAssocCd;

        public ItemAssocSynchronizeData(MasterAppData pMasterData,
                                        Integer pItem1Id,
                                        Integer pItem2Id,
                                        Integer pCatalogId,
                                        String pItemAssocCd) {
            super(pMasterData);
            this.mItem1Id = pItem1Id;
            this.mItem2Id = pItem2Id;
            this.mCatalogId = pCatalogId;
            this.mItemAssocCd = pItemAssocCd;
        }

        public Integer getItem1Id() {
            return mItem1Id;
        }

        public Integer getItem2Id() {
            return mItem2Id;
        }

        public Integer getCatalogId() {
            return mCatalogId;
        }

        public String getItemAssocCd() {
            return mItemAssocCd;
        }
    }

    public class StoreItemAssocSynchronizeData extends ItemAssocSynchronizeData {

        private ItemReference mItemReference;

        public StoreItemAssocSynchronizeData(MasterAppData pMasterData,
                                             ItemReference pItemReference,
                                             Integer pItem1Id,
                                             Integer pItem2Id,
                                             Integer pCatalogId,
                                             String pItemAssocCd) {
            super(pMasterData, pItem1Id, pItem2Id, pCatalogId, pItemAssocCd);
            this.mItemReference = pItemReference;
        }

        public boolean isSynchronized() {
            ItemAssocData itemAssoc = getValue();
            return itemAssoc != null && itemAssoc.getItem2Id() == getItem2Id();
        }

        public void refresh(ItemAssocData pValue) {
            mMasterData.getAppStoreItemCategoryAssocMap().put(mItemReference, pValue);
        }

        public ItemAssocData getValue() {
            Map<ItemReference, ItemAssocData> map = mMasterData.getAppStoreItemCategoryAssocMap();
            if (map == null) {
                map = new HashMap<ItemReference, ItemAssocData>();
                mMasterData.setAppStoreItemCategoryAssocMap(map);
            }
            return map.get(mItemReference);
        }
    }

    public class AccountItemAssocSynchronizeData extends ItemAssocSynchronizeData {

        private ItemReference mItemReference;
        public AccountReference mAccountReference;
        public Integer mAccountId;

        public AccountItemAssocSynchronizeData(MasterAppData pMasterData,
                                               Integer pItem1Id,
                                               Integer pItem2Id,
                                               Integer pCatalogId,
                                               String pItemAssocCd,
                                               ItemReference pItemReference,
                                               Integer pAccountId,
                                               AccountReference pAccountReference) {
            super(pMasterData, pItem1Id, pItem2Id, pCatalogId, pItemAssocCd);
            this.mItemReference = pItemReference;
            this.mAccountReference = pAccountReference;
            mAccountId = pAccountId;
        }

        public boolean isSynchronized() {
            ItemAssocData itemAssoc = getValue();
            return itemAssoc != null && itemAssoc.getItem2Id() == getItem2Id();
        }

        public void refresh(ItemAssocData pValue) {
            mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppAcountDataMap()
                    .get(mAccountId).getAppAccountItemCategoryAssocMap().put(mItemReference, pValue);
        }

        public ItemAssocData getValue() {
            Map<ItemReference, ItemAssocData> map = mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppAcountDataMap()
                    .get(mAccountId).getAppAccountItemCategoryAssocMap();
            if (map == null) {
                map = new HashMap<ItemReference, ItemAssocData>();
                mMasterData
                        .getAppAccountReferenceMap()
                        .get(mAccountReference)
                        .getAppAcountDataMap()
                        .get(mAccountId).setAppAccountItemCategoryAssocMap(map);
            }
            return map.get(mItemReference);
        }
    }

    public class ShoppingItemAssocSynchronizeData extends ItemAssocSynchronizeData {

        private ItemReference mItemReference;
        public AccountReference mAccountReference;
        private CatalogReference mCatalogReference;

        public ShoppingItemAssocSynchronizeData(MasterAppData pMasterData,
                                                Integer pItem1Id,
                                                Integer pItem2Id,
                                                Integer pCatalogId,
                                                String pItemAssocCd,
                                                ItemReference pItemReference,
                                                AccountReference pAccountReference,
                                                CatalogReference pCatalogReference) {
            super(pMasterData, pItem1Id, pItem2Id, pCatalogId, pItemAssocCd);
            this.mItemReference = pItemReference;
            this.mAccountReference = pAccountReference;
            this.mCatalogReference = pCatalogReference;

        }

        public boolean isSynchronized() {
            ItemAssocData itemAssoc = getValue();
            return itemAssoc != null && itemAssoc.getItem2Id() == getItem2Id();
        }

        public void refresh(ItemAssocData pValue) {
            Map<ItemReference, ItemAssocData> map = mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppCatalogMap()
                    .get(mCatalogReference)
                    .getAppShoppingItemCategoryAssocMap();
            if (map == null) {
                map = new HashMap<ItemReference, ItemAssocData>();
                mMasterData
                        .getAppAccountReferenceMap()
                        .get(mAccountReference)
                        .getAppCatalogMap()
                        .get(mCatalogReference)
                        .setAppShoppingItemCategoryAssocMap(map);
            }
            map.put(mItemReference, pValue);
        }

        public ItemAssocData getValue() {
            Map<ItemReference, ItemAssocData> map = mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppCatalogMap()
                    .get(mCatalogReference).getAppShoppingItemCategoryAssocMap();
            if (map == null) {
                map = new HashMap<ItemReference, ItemAssocData>();
                mMasterData
                        .getAppAccountReferenceMap()
                        .get(mAccountReference)
                        .getAppCatalogMap()
                        .get(mCatalogReference).setAppShoppingItemCategoryAssocMap(map);
            }
            return map.get(mItemReference);
        }
    }

    public class ItemAssocDBAgent implements DBWorker {

        private Logger log = Logger.getLogger(ItemDBAgent.class);

        private ItemAssocSynchronizeData mSynchData;

        private Connection mCon;

        public ItemAssocDBAgent(Connection pCon, ItemAssocSynchronizeData pSynchData) {
            mCon = pCon;
            mSynchData = pSynchData;
        }

        public void doWork() throws Exception {
            if (!mSynchData.isSynchronized()) {
                ItemAssocData itemAssoc = doSynchronize();
                mSynchData.refresh(itemAssoc);
            }
        }

        private ItemAssocData doSynchronize() throws Exception {

            log.debug("doSynchronize()=> BEGIN");

            ItemAssocData itemAssoc = ItemAssocData.createValue();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(ItemAssocDataAccess.CATALOG_ID, mSynchData.getCatalogId());
            crit.addEqualTo(ItemAssocDataAccess.ITEM1_ID, mSynchData.getItem1Id());
            crit.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, mSynchData.getItemAssocCd());

            ItemAssocDataVector iaDV = ItemAssocDataAccess.select(mCon, crit);
            log.debug("doSynchronize()=> XXX pItem1:" + mSynchData.getItem1Id() + " pItem2: " + mSynchData.getItem2Id() + " in catalog " + mSynchData.getCatalogId());

            if (iaDV != null && iaDV.size() > 0) {

                itemAssoc = (ItemAssocData) iaDV.get(0);
                log.debug("doSynchronize()=> XXX replacing old category " + itemAssoc.getItem2Id() + " with new " + mSynchData.getItem2Id() + " in catalog " + mSynchData.getCatalogId());
                itemAssoc.setModBy(IXCatalogItemLoader.IXCITEMLOADER);
                itemAssoc.setItem2Id(mSynchData.getItem2Id());

                ItemAssocDataAccess.update(mCon, itemAssoc);

            } else {

                itemAssoc.setAddBy(IXCatalogItemLoader.IXCITEMLOADER);
                itemAssoc.setModBy(IXCatalogItemLoader.IXCITEMLOADER);
                itemAssoc.setCatalogId(mSynchData.getCatalogId());
                itemAssoc.setItem1Id(mSynchData.getItem1Id());
                itemAssoc.setItem2Id(mSynchData.getItem2Id());
                itemAssoc.setItemAssocCd(mSynchData.getItemAssocCd());

                itemAssoc = ItemAssocDataAccess.insert(mCon, itemAssoc);
            }

            log.debug("doSynchronize()=> END.");

            return itemAssoc;
        }

    }

    public class ContractItemSynchronizeData extends SynchronizeData<ContractItemData> {

        public AccountReference mAccountReference;
        private CatalogReference mCatalogReference;
        private ItemReference mItemReference;
        private ItemData mItem;
        private XpedxCatalogItemView mInboundItem;
        private ContractData mContract;


        public ContractItemSynchronizeData(MasterAppData pMasterData,
                                           ItemData pItem,
                                           XpedxCatalogItemView pInboundItem,
                                           ContractData pContract,
                                           AccountReference pAccountReference,
                                           CatalogReference pCatalogReference,
                                           ItemReference pItemReference) {
            super(pMasterData);
            this.mAccountReference = pAccountReference;
            this.mCatalogReference = pCatalogReference;
            this.mItemReference = pItemReference;
            this.mItem = pItem;
            this.mInboundItem = pInboundItem;
            this.mContract = pContract;


        }

        public boolean isSynchronized() {


            ContractItemData contractItemData = getValue();

            if (contractItemData != null) {

                boolean wasChanged = false;

                BigDecimal newPrice = new BigDecimal(mInboundItem.getPrice());
                if (newPrice.compareTo(contractItemData.getAmount()) != 0) {
                    wasChanged = true;
                }

                BigDecimal newCost = new BigDecimal(mInboundItem.getCost());
                if (newCost.compareTo(contractItemData.getDistCost()) != 0) {
                    wasChanged = true;
                }

                if (!Utility.strNN(mInboundItem.getServiceCode()).equals(Utility.strNN(contractItemData.getServiceFeeCode()))) {
                    wasChanged = true;
                }

                return !wasChanged;
            }

            return false;

        }

        public void refresh(ContractItemData pValue) {
            Map<ItemReference, ContractItemData> map = mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppCatalogMap()
                    .get(mCatalogReference)
                    .getAppContractItemsMap();
            if (map == null) {
                map = new HashMap<ItemReference, ContractItemData>();
                mMasterData
                        .getAppAccountReferenceMap()
                        .get(mAccountReference)
                        .getAppCatalogMap()
                        .get(mCatalogReference)
                        .setAppContractItemsMap(map);
            }
            map.put(mItemReference, pValue);
        }

        public ContractItemData getValue() {
            Map<ItemReference, ContractItemData> map = mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppCatalogMap()
                    .get(mCatalogReference)
                    .getAppContractItemsMap();
            if (map == null) {
                map = new HashMap<ItemReference, ContractItemData>();
                mMasterData
                        .getAppAccountReferenceMap()
                        .get(mAccountReference)
                        .getAppCatalogMap()
                        .get(mCatalogReference)
                        .setAppContractItemsMap(map);
            }
            return map.get(mItemReference);
        }

        public ItemData getItem() {
            return mItem;
        }

        public XpedxCatalogItemView getInboundItem() {
            return mInboundItem;
        }

        public ContractData getContract() {
            return mContract;
        }
    }

    public class ContractItemDBAgent implements DBWorker {

        private Logger log = Logger.getLogger(ContractDBAgent.class);

        private ContractItemSynchronizeData mSynchData;

        private Connection mCon;

        public ContractItemDBAgent(Connection pCon, ContractItemSynchronizeData pSynchData) {
            mCon = pCon;
            mSynchData = pSynchData;
        }

        public void doWork() throws Exception {
            if (!mSynchData.isSynchronized()) {
                ContractItemData item = doSynchronize();
                mSynchData.refresh(item);
            }
        }

        private ContractItemData doSynchronize() throws Exception {

            log.debug("doSynchronize()=> BEGIN");

            ItemData item = mSynchData.getItem();
            ContractData contract = mSynchData.getContract();
            XpedxCatalogItemView inboundItem = mSynchData.getInboundItem();

            log.debug("doSynchronize()=> XXX ItemID: " + item.getItemId());
            log.debug("doSynchronize()=> XXX ContractID: " + contract.getContractId());

            ContractItemData contractItemData = mSynchData.getValue();

            if (contractItemData == null) {

                contractItemData = ContractItemData.createValue();

                contractItemData.setContractId(contract.getContractId());
                contractItemData.setItemId(item.getItemId());
                contractItemData.setAmount(new BigDecimal(inboundItem.getPrice()));
                contractItemData.setDistCost(new BigDecimal(inboundItem.getCost()));
                contractItemData.setServiceFeeCode(inboundItem.getServiceCode().trim());
                contractItemData.setAddBy(IXCatalogItemLoader.IXCITEMLOADER);
                contractItemData.setModBy(IXCatalogItemLoader.IXCITEMLOADER);

                contractItemData = ContractItemDataAccess.insert(mCon, contractItemData);

            } else {

                boolean wasChanged = false;

                BigDecimal newPrice = new BigDecimal(inboundItem.getPrice());
                if (newPrice.compareTo(contractItemData.getAmount()) != 0) {
                    contractItemData.setAmount(newPrice);
                    wasChanged = true;
                }

                BigDecimal newCost = new BigDecimal(inboundItem.getCost());
                if (newCost.compareTo(contractItemData.getDistCost()) != 0) {
                    contractItemData.setDistCost(newCost);
                    wasChanged = true;
                }

                if (!Utility.strNN(inboundItem.getServiceCode()).equals(Utility.strNN(contractItemData.getServiceFeeCode()))) {
                    contractItemData.setServiceFeeCode(inboundItem.getServiceCode());
                    wasChanged = true;
                }

                if (wasChanged) {
                    ContractItemDataAccess.update(mCon, contractItemData);
                }
            }

            log.debug("doSynchronize()=> END.");

            return contractItemData;
        }

    }

    public class InventoryItemsSynchronizeData extends SynchronizeData<InventoryItemsData> {

        public AccountReference mAccountReference;
        private ItemReference mItemReference;
        private ItemData mItem;
        private XpedxCatalogItemView mInboundItem;
        private Integer mAccountId;
        private MasterCustomerData mMasterCustomerData;

        public InventoryItemsSynchronizeData(MasterAppData pMasterData,
                                             MasterCustomerData pMasterCustomerData,
                                             ItemData pItem,
                                             XpedxCatalogItemView pInboundItem,
                                             AccountReference pAccountReference,
                                             Integer pAccountId,
                                             ItemReference pItemReference) {
            super(pMasterData);
            this.mAccountReference = pAccountReference;
            this.mItemReference = pItemReference;
            this.mItem = pItem;
            this.mInboundItem = pInboundItem;
            this.mAccountId = pAccountId;
            this.mMasterCustomerData = pMasterCustomerData;

        }

        public boolean isSynchronized() {


            InventoryItemsData inventoryItemData = getValue();
            if (Utility.isTrue(mInboundItem.getInventoryItems())) {

                if (inventoryItemData != null) {

                    boolean wasChanged = false;

                    String autoOrderItem = Utility.isTrue(mInboundItem.getAutoOrderItem()) ? "Y" : "N";
                    if (!autoOrderItem.equals(inventoryItemData.getEnableAutoOrder())) {
                        wasChanged = true;
                    }

                    return !wasChanged;
                }
            } else {
                return !(inventoryItemData != null && inventoryItemData.getInventoryItemsId() > 0);

            }

            return false;

        }

        public void refresh(InventoryItemsData pValue) {

            Map<ItemReference, InventoryItemsData> map = mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppAcountDataMap()
                    .get(mAccountId)
                    .getInventoryItemsMap();

            if (map == null) {
                map = new HashMap<ItemReference, InventoryItemsData>();
                mMasterData
                        .getAppAccountReferenceMap()
                        .get(mAccountReference)
                        .getAppAcountDataMap()
                        .get(mAccountId)
                        .setInventoryItemsMap(map);
            }

            map.put(mItemReference, pValue);
        }

        public InventoryItemsData getValue() {

            Map<ItemReference, InventoryItemsData> map = mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppAcountDataMap()
                    .get(mAccountId)
                    .getInventoryItemsMap();

            if (map == null) {
                map = new HashMap<ItemReference, InventoryItemsData>();
                mMasterData
                        .getAppAccountReferenceMap()
                        .get(mAccountReference)
                        .getAppAcountDataMap()
                        .get(mAccountId).setInventoryItemsMap(map);
            }
            return map.get(mItemReference);
        }

        public ItemData getItem() {
            return mItem;
        }

        public XpedxCatalogItemView getInboundItem() {
            return mInboundItem;
        }

        public Integer getAccountId() {
            return mAccountId;
        }

        public Set<Integer> getShoppingCatalogIds() {
            return mMasterData.getAppAccountReferenceMap().get(mAccountReference).getAppAcountDataMap().get(mAccountId).getShoppingCatalogIdSet();
        }

        public Set<Integer> getCatalogIdsToRemove() {
            Set<Integer> catalogIdsToRemove = new HashSet<Integer>();
            Collection<InboundCatalogData> inboundCatalogs = mMasterCustomerData.getInboundAccountMap().get(mAccountReference).getInboundCatalogMap().values();
            for (InboundCatalogData inboundCatalogData : inboundCatalogs) {
                if (inboundCatalogData.getInboundItemMap().containsKey(mItemReference)) {
                    if (!inboundCatalogData.getInboundItemMap().get(mItemReference).getInventoryItems()) {
                        AppCatalogData catalog = mMasterData.getAppAccountReferenceMap().get(mAccountReference).getAppCatalogMap().get(new CatalogReference(mAccountReference.getAccountNamber(), inboundCatalogData.getLoaderField()));
                        if (catalog != null && catalog.getCatalog() != null) {
                            catalogIdsToRemove.add(catalog.getCatalog().getCatalogId());
                        }
                    }
                }
            }
            return catalogIdsToRemove;
        }
    }

    public class InventoryItemsDBAgent implements DBWorker {

        private Logger log = Logger.getLogger(InventoryItemsDBAgent.class);

        private InventoryItemsSynchronizeData mSynchData;

        private Connection mCon;

        public InventoryItemsDBAgent(Connection pCon, InventoryItemsSynchronizeData pSynchData) {
            mCon = pCon;
            mSynchData = pSynchData;
        }

        public void doWork() throws Exception {
            if (!mSynchData.isSynchronized()) {
                InventoryItemsData item = doSynchronize();
                mSynchData.refresh(item);
            }
        }

        private InventoryItemsData doSynchronize() throws Exception {

            log.debug("doSynchronize()=> BEGIN");

            ItemData item = mSynchData.getItem();
            Integer accountId = mSynchData.getAccountId();
            XpedxCatalogItemView inboundItem = mSynchData.getInboundItem();

            log.debug("doSynchronize()=> AccountID: " + accountId);
            log.debug("doSynchronize()=> ItemID: " + item.getItemId());

            InventoryItemsData invItemData = mSynchData.getValue();
            if (Utility.isTrue(inboundItem.getInventoryItems()) && invItemData == null) {

                invItemData = InventoryItemsData.createValue();
                invItemData.setBusEntityId(accountId);
                invItemData.setItemId(item.getItemId());
                invItemData.setEnableAutoOrder(Utility.isTrue(inboundItem.getAutoOrderItem()) ? "Y" : "N");
                invItemData.setStatusCd(RefCodeNames.SIMPLE_STATUS_CD.ACTIVE);
                invItemData.setAddBy(IXCatalogItemLoader.IXCITEMLOADER);
                invItemData.setModBy(IXCatalogItemLoader.IXCITEMLOADER);

                invItemData = InventoryItemsDataAccess.insert(mCon, invItemData);

            } else if (Utility.isTrue(inboundItem.getInventoryItems()) && invItemData != null) {


                boolean wasChanged = false;

                String autoOrderItem = Utility.isTrue(inboundItem.getAutoOrderItem()) ? "Y" : "N";
                if (!autoOrderItem.equals(invItemData.getEnableAutoOrder())) {
                    invItemData.setEnableAutoOrder(autoOrderItem);
                    wasChanged = true;
                }

                if (wasChanged) {
                    invItemData.setModBy(IXCatalogItemLoader.IXCITEMLOADER);
                    InventoryItemsDataAccess.update(mCon, invItemData);
                }

            } else if (!Utility.isTrue(inboundItem.getInventoryItems()) && invItemData != null) {

                Set<Integer> catalogIds = mSynchData.getCatalogIdsToRemove();

                log.debug("doSynchronize()=> NEED REMOVE FROM CatalogID(s): " + catalogIds);

                Set<Integer> existShoppingCatalogIds = mSynchData.getShoppingCatalogIds();

                log.debug("doSynchronize()=> All Shopping CatalogID(s): " + existShoppingCatalogIds);

                HashMap<Integer, Set<Integer>> catalogsByItem = XpedxLoaderAssist.getShoppingCatalogIdsByItem(mCon, existShoppingCatalogIds, invItemData.getItemId());
                Set<Integer> itemCatalogs = catalogsByItem.get(invItemData.getItemId());

                log.debug("doSynchronize()=> Contains In: " + itemCatalogs);

                if (itemCatalogs == null || catalogIds.containsAll(itemCatalogs)) {
                    log.debug("doSynchronize()=> REMOVE: " + invItemData);
                    InventoryItemsDataAccess.remove(mCon, invItemData.getInventoryItemsId());
                    invItemData = null;
                }

            }

            log.debug("doSynchronize()=> END.");

            return invItemData;

        }

    }

    public class ShoppingControlsSynchronizeData extends SynchronizeData<ShoppingControlData> {

        public AccountReference mAccountReference;
        private ItemReference mItemReference;
        private ItemData mItem;
        private XpedxCatalogItemView mInboundItem;
        private Integer mAccountId;

        public ShoppingControlsSynchronizeData(MasterAppData pMasterData,
                                               ItemData pItem,
                                               XpedxCatalogItemView pInboundItem,
                                               AccountReference pAccountReference,
                                               Integer pAccountId,
                                               ItemReference pItemReference) {
            super(pMasterData);
            this.mAccountReference = pAccountReference;
            this.mItemReference = pItemReference;
            this.mItem = pItem;
            this.mInboundItem = pInboundItem;
            this.mAccountId = pAccountId;

        }

        public boolean isSynchronized() {

            ShoppingControlData item = getValue();
            if (item != null) {


                boolean wasChanged = false;

                if (Utility.isSet(mInboundItem.getShoppingMaxQTY())) {

                    int restrictionDays = Utility.parseInt(mInboundItem.getShoppingRestrictionDays());
                    int maxOrderQty = Utility.parseInt(mInboundItem.getShoppingMaxQTY());

                    if (item.getRestrictionDays() != restrictionDays) {
                        wasChanged = true;
                    }

                    if (item.getMaxOrderQty() != maxOrderQty) {
                        wasChanged = true;
                    }
                } else {

                    if (item.getMaxOrderQty() > -1) {
                        wasChanged = true;
                    }

                    int shoppingRestrictionDays;
                    if (!Utility.isSet(mInboundItem.getShoppingRestrictionDays())) {
                        shoppingRestrictionDays = 1;
                    } else {
                        shoppingRestrictionDays = Utility.parseInt(mInboundItem.getShoppingMaxQTY());
                    }

                    if (item.getRestrictionDays() != shoppingRestrictionDays) {
                        wasChanged = true;
                    }
                }
                
                if(Utility.isSet(mInboundItem.getShoppingRestrictionsAction())){
                	if(!Utility.isSet(item.getActionCd())){
                		wasChanged = true;
                	}else if(!item.getActionCd().equalsIgnoreCase( mInboundItem.getShoppingRestrictionsAction())){
                		wasChanged = true;
                	}
                }else{
                	if(Utility.isSet(item.getActionCd())){
                		wasChanged = true;
                	}
                }

                return !wasChanged;
            }


            return false;

        }

        public void refresh(ShoppingControlData pValue) {

            Map<ItemReference, ShoppingControlData> map = mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppAcountDataMap()
                    .get(mAccountId)
                    .getShoppingControlsMap();

            if (map == null) {
                map = new HashMap<ItemReference, ShoppingControlData>();
                mMasterData
                        .getAppAccountReferenceMap()
                        .get(mAccountReference)
                        .getAppAcountDataMap()
                        .get(mAccountId)
                        .setShoppingControlsMap(map);
            }

            map.put(mItemReference, pValue);
        }

        public ShoppingControlData getValue() {

            Map<ItemReference, ShoppingControlData> map = mMasterData
                    .getAppAccountReferenceMap()
                    .get(mAccountReference)
                    .getAppAcountDataMap()
                    .get(mAccountId)
                    .getShoppingControlsMap();

            if (map == null) {
                map = new HashMap<ItemReference, ShoppingControlData>();
                mMasterData
                        .getAppAccountReferenceMap()
                        .get(mAccountReference)
                        .getAppAcountDataMap()
                        .get(mAccountId).setShoppingControlsMap(map);
            }
            return map.get(mItemReference);
        }

        public ItemData getItem() {
            return mItem;
        }

        public XpedxCatalogItemView getInboundItem() {
            return mInboundItem;
        }

        public Integer getAccountId() {
            return mAccountId;
        }

        public Set<Integer> getShoppingCatalogIds() {
            return mMasterData.getAppAccountReferenceMap().get(mAccountReference).getAppAcountDataMap().get(mAccountId).getShoppingCatalogIdSet();
        }

    }

    public class ShoppingControlsDBAgent implements DBWorker {

        private Logger log = Logger.getLogger(ShoppingControlsDBAgent.class);

        private ShoppingControlsSynchronizeData mSynchData;

        private Connection mCon;

        public ShoppingControlsDBAgent(Connection pCon, ShoppingControlsSynchronizeData pSynchData) {
            mCon = pCon;
            mSynchData = pSynchData;
        }

        public void doWork() throws Exception {
            if (!mSynchData.isSynchronized()) {
                ShoppingControlData item = doSynchronize();
                mSynchData.refresh(item);
            }
        }

        private ShoppingControlData doSynchronize() throws Exception {

            log.debug("oSynchronize()=> BEGIM");
            ItemData itemData = mSynchData.getItem();

            log.debug("oSynchronize()=> XXX ItemID: " + itemData.getItemId());

            Integer accountId = mSynchData.getAccountId();
            XpedxCatalogItemView inboundItem = mSynchData.getInboundItem();

            ShoppingControlData shoppingControlData = mSynchData.getValue();
            if (shoppingControlData == null) {

                shoppingControlData = ShoppingControlData.createValue();
                shoppingControlData.setAccountId(accountId);
                shoppingControlData.setItemId(itemData.getItemId());
                shoppingControlData.setControlStatusCd(RefCodeNames.SIMPLE_STATUS_CD.ACTIVE);

                if (Utility.isSet(inboundItem.getShoppingMaxQTY())) {
                    shoppingControlData.setRestrictionDays(Utility.parseInt(inboundItem.getShoppingRestrictionDays()));
                    shoppingControlData.setMaxOrderQty(Utility.parseInt(inboundItem.getShoppingMaxQTY()));
                } else {
                    shoppingControlData.setMaxOrderQty(-1);
                    if (!Utility.isSet(inboundItem.getShoppingRestrictionDays())) {
                        shoppingControlData.setRestrictionDays(1);
                    } else {
                        shoppingControlData.setRestrictionDays(Utility.parseInt(inboundItem.getShoppingRestrictionDays()));
                    }
                }

                shoppingControlData.setAddBy(IXCatalogItemLoader.IXCITEMLOADER);
                shoppingControlData.setModBy(IXCatalogItemLoader.IXCITEMLOADER);

                shoppingControlData = ShoppingControlDataAccess.insert(mCon, shoppingControlData);

            } else {

                boolean wasChanged = false;

                if (Utility.isSet(inboundItem.getShoppingMaxQTY())) {

                    int restrictionDays = Utility.parseInt(inboundItem.getShoppingRestrictionDays());
                    int maxOrderQty = Utility.parseInt(inboundItem.getShoppingMaxQTY());

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
                    if (!Utility.isSet(inboundItem.getShoppingRestrictionDays())) {
                        shoppingRestrictionDays = 1;
                    } else {
                        shoppingRestrictionDays = Utility.parseInt(inboundItem.getShoppingMaxQTY());
                    }

                    if (shoppingControlData.getRestrictionDays() != shoppingRestrictionDays) {
                        shoppingControlData.setRestrictionDays(shoppingRestrictionDays);
                        wasChanged = true;
                    }
                }
                
                if(Utility.isSet(inboundItem.getShoppingRestrictionsAction())){
                	if(!Utility.isSet(shoppingControlData.getActionCd())){
                		shoppingControlData.setActionCd(inboundItem.getShoppingRestrictionsAction().toUpperCase());
            			wasChanged = true;
                	}else{
                		if(!shoppingControlData.getActionCd().equalsIgnoreCase(inboundItem.getShoppingRestrictionsAction())){
                			shoppingControlData.setActionCd(inboundItem.getShoppingRestrictionsAction().toUpperCase());
                			wasChanged = true;
                		}
                	}
                }else{
                	if(Utility.isSet(shoppingControlData.getActionCd())){
                		shoppingControlData.setActionCd(null);
                		wasChanged = true;
                	}
                }
                
                if (wasChanged) {
                    shoppingControlData.setModBy(IXCatalogItemLoader.IXCITEMLOADER);
                    ShoppingControlDataAccess.update(mCon, shoppingControlData);
                }

            }

            log.debug("synchronize()=> END");

            return shoppingControlData;

        }

    }

    public class ItemContentSynchronizeData extends SynchronizeData<ContentData> {

        private ItemReference mItemReference;
        private XpedxCatalogItemView mInboundItem;
        private byte[] mContent;
        private ItemData mItem;

        public ItemContentSynchronizeData(MasterAppData pMasterData,
                                          ItemData pItem,
                                          XpedxCatalogItemView pInboundItem,
                                          byte[] pContent,
                                          ItemReference pItemReference) {
            super(pMasterData);
            this.mInboundItem = pInboundItem;
            this.mContent = pContent;
            this.mItemReference = pItemReference;
            this.mItem = pItem;
        }

        public boolean isSynchronized() {

            ContentData item = getValue();

            log.debug("ItemContentSynchronizeData()=>isSynchronized(): item = " + item);
            if (item != null) {
               log.debug("ItemContentSynchronizeData()=>isSynchronized(): item.getContentId() = " + item.getContentId());
               log.debug("ItemContentSynchronizeData()=>isSynchronized(): item.getBinaryData() = " + item.getBinaryData());
            }
            if (mInboundItem != null) {
               log.debug("ItemContentSynchronizeData()=>isSynchronized(): mInboundItem.getImage() = " + mInboundItem.getImage());
            }
            log.debug("ItemContentSynchronizeData()=>isSynchronized(): mContent = " + mContent);
            
            if (Utility.isSet(mInboundItem.getImage())) {
                if (item != null && item.getContentId() > 0) {
                	//we should support switching from DATABASE to E3 Storage and vise versa
                	//that's why we'll update CLW_CONTENT table
                	//when content WAS changed and when content WAS NOT changed
                	log.debug("isSynchronized()=> returning false");
                	return false;
                }
            } else {
                if (item == null || item.getBinaryData() == null) {
                	log.debug("item == null || item.getBinaryData() == null");
                    return true;
                }
            }
            return false;
        }

        public void refresh(ContentData pValue) {
            mMasterData
                    .getAppItemMap()
                    .get(mItemReference).setImage(pValue);
        }

        public ContentData getValue() {
            return mMasterData
                    .getAppItemMap()
                    .get(mItemReference)
                    .getImage();
        }

        public XpedxCatalogItemView getInboundItem() {
            return mInboundItem;
        }

        public byte[] getInboundContentData() {
            return mContent;
        }

        public ItemData getItem() {
            return mItem;
        }
    }

    public class ItemContentDBAgent implements DBWorker {

        private Logger log = Logger.getLogger(ItemContentDBAgent.class);

        private ItemContentSynchronizeData mSynchData;

        private Connection mCon;

        public ItemContentDBAgent(Connection pCon, ItemContentSynchronizeData pSynchData) {
            mCon = pCon;
            mSynchData = pSynchData;
        }

        public void doWork() throws Exception {
            if (!mSynchData.isSynchronized()) {
                ContentData itemContent = doSynchronize();
                mSynchData.refresh(itemContent);
            }
        }

        private ContentData doSynchronize() throws Exception {

            log.debug("ContentData doSynchronize()=> BEGIN");

            ContentData itemContent = mSynchData.getValue(); //existing content in the DB or E3 Storage

            log.debug("ContentData doSynchronize()=> itemContent = " + itemContent);
            
            XpedxCatalogItemView inboundItem = mSynchData.getInboundItem();
            ItemData itemData = mSynchData.getItem();

            log.debug("ContentData doSynchronize()=> XXX item : " + itemData.getItemId());
            log.debug("ContentData doSynchronize()=> XXX image: " + inboundItem.getImage());

            byte[] inboundContentData = mSynchData.getInboundContentData();
            if (Utility.isSet(inboundItem.getImage())) {
                if (inboundContentData == null) {
                    throw new Exception("ContentData is NULL");
                }
            }

            // based off the value of the "storage.system.item" system property:
            // either add binary data to CLW_CONTENT DB Table or add binary data to E3 Storage
            String storageType = Utility.strNN(System.getProperty("storage.system.item"));
            log.debug("ContentData doSynchronize(): storageType1 = " + storageType);
            if (storageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE)) {
            	storageType = RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE;
            } else {
            	storageType = RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE;
            }
            
            log.debug("ContentData doSynchronize(): storageType2 = " + storageType);
            log.debug("ContentData inboundItem.getImage() = " + inboundItem.getImage()); 
            
            APIAccess factory = APIAccess.getAPIAccess();
            
            if (storageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE)) {
            	
            	if (inboundContentData != null && itemContent == null) { 
            		itemContent = XpedxLoaderAssist.createImageContent(itemData.getItemId(),
                            inboundItem.getImage(),
                            inboundContentData,
                            IXCatalogItemLoader.IXCITEMLOADER); 
            		//currently E3 Storage API allows writing to E3 Storage from the file ONLY;
            		//download file from https address and store it in the file on the server
            		String imageUrl = inboundItem.getImage();
            		String basename = System.getProperty("webdeploy");
            		
        		    String pFullPath = basename + "/en/products/images/" + itemData.getItemId()+ IOUtilities.getFileExt(inboundItem.getImage()).toLowerCase();
        		    log.debug("ContentData pFullPath = " + pFullPath);
            		//File temp = File.createTempFile(itemData.getItemId(), IOUtilities.getFileExt(inboundItem.getImage()).toLowerCase(), pFullPath);
            	    //Delete temp file when program exits.
            	    //temp.deleteOnExit();
            		saveImage(imageUrl, pFullPath);
            		
            		factory.getContentAPI().addContentSaveImageE3Storage(IXCatalogItemLoader.IXCITEMLOADER, itemContent.getPath(), ITEM_IMAGE);                	                
            	} else if (itemContent != null && inboundContentData != null) {
                        itemContent.setBinaryData(inboundContentData);
                        itemContent.setModBy(IXCatalogItemLoader.IXCITEMLOADER);
                        factory.getContentAPI().addContentSaveImageE3Storage(IXCatalogItemLoader.IXCITEMLOADER, itemContent.getPath(), ITEM_IMAGE);
                } else if (itemContent != null) {
                    log.debug("ContentData doSynchronize()=> REMOVE (E3 Storage): " + itemContent);
                    ContentDataAccess.remove(mCon, itemContent.getContentId());
                    itemContent = null;
                }
            } else {
            	log.debug("ContentData: writing images to the Database");
                if (inboundContentData != null && itemContent == null) { 
                	log.debug("inboundContentData != null && itemContent == null");
                    itemContent = XpedxLoaderAssist.createImageContent(itemData.getItemId(),
                        inboundItem.getImage(),
                        inboundContentData,
                        IXCatalogItemLoader.IXCITEMLOADER);                
                    itemContent = ContentDataAccess.insert(mCon, itemContent);
                } else if (itemContent != null && inboundContentData != null) {
                	log.debug("itemContent != null && inboundContentData != null");
                    if (!Utility.byteArrayEqual(itemContent.getBinaryData(), inboundContentData)) {
                    	log.debug("!Utility.byteArrayEqual(itemContent.getBinaryData(), inboundContentData)");                        
                    	ContentDataAccess.remove(mCon, itemContent.getContentId());
                        itemContent = XpedxLoaderAssist.createImageContent(itemData.getItemId(),
                                inboundItem.getImage(),
                                inboundContentData,
                                IXCatalogItemLoader.IXCITEMLOADER);
                        itemContent = ContentDataAccess.insert(mCon, itemContent);                        
                    }
                } else if (itemContent != null) {
                    log.debug("ContentData doSynchronize()=> REMOVE: " + itemContent);
                    ContentDataAccess.remove(mCon, itemContent.getContentId());
                    itemContent = null;
                }
            }
            
            log.debug("ContentData doSynchronize()=> END.");


            return itemContent;

        }
        
        public void saveImage(String imageUrl, String destinationFile) throws IOException {
    		URL url = new URL(imageUrl);
    		InputStream is = url.openStream();
    		OutputStream os = new FileOutputStream(destinationFile);

    		byte[] b = new byte[2048];
    		int length;

    		while ((length = is.read(b)) != -1) {
    			os.write(b, 0, length);
    		}

    		is.close();
    		os.close();
    	}

    }

}
