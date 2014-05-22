package com.cleanwise.service.api.meta;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.ItemMappingDataAccess;
import com.cleanwise.service.api.dao.CatalogStructureDataAccess;
import com.cleanwise.service.api.dao.ItemMetaDataAccess;
import com.cleanwise.service.api.dao.CatalogDataAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.cachecos.*;
import com.cleanwise.service.api.cachecos.VarMeta;
import com.cleanwise.service.api.cachecos.TableField;

public class ProductDataMeta extends ObjectMeta {

    public ProductDataMeta(ProductData pData) {

        super();

        {

            VarMeta itemDVarMeta = new VarMeta();

            itemDVarMeta.add(new ItemDataMeta(pData.getItemData()));
            itemDVarMeta.add(new CatalogStructureDataMeta(new TableField(CatalogStructureDataAccess.ITEM_ID, pData.getItemData().getItemId())));
            if (pData.getCatalogStructure() != null) {
                itemDVarMeta.add(new CatalogDataMeta(new TableField(CatalogDataAccess.CATALOG_ID, pData.getCatalogStructure().getCatalogId())));
            }
            add(itemDVarMeta);
        }

        {
            VarMeta manuBusEntityVarMeta = new VarMeta();

            manuBusEntityVarMeta.add(new BusEntityDataMeta(pData.getManufacturer()));
            if (pData.getManuMapping() != null) {
                manuBusEntityVarMeta.add(new ItemMappingDataMeta(new TableField(ItemMappingDataAccess.BUS_ENTITY_ID, pData.getManuMapping().getBusEntityId()),
                        new TableField(ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER)));
            }
            add(manuBusEntityVarMeta);
        }

        {
            VarMeta manuMappingDVarMeta = new VarMeta();
            manuMappingDVarMeta.add(new ItemMappingDataMeta(pData.getManuMapping()));
            add(manuMappingDVarMeta);
        }

        {
            VarMeta catalogStructureDVarMeta = new VarMeta();
            catalogStructureDVarMeta.add(new CatalogStructureDataMeta(pData.getCatalogStructure()));
            add(catalogStructureDVarMeta);
        }

        {
            VarMeta distrBusEntityDVarMeta = new VarMeta();
            distrBusEntityDVarMeta.add(new BusEntityDataMeta(pData.getCatalogDistributor()));
            if (pData.getCatalogDistrMapping() != null) {
                distrBusEntityDVarMeta.add(new ItemMappingDataMeta(new TableField(ItemMappingDataAccess.BUS_ENTITY_ID, pData.getCatalogDistrMapping().getBusEntityId()),
                        new TableField(ItemMappingDataAccess.ITEM_MAPPING_CD, RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR)));
            }
            add(distrBusEntityDVarMeta);
        }

        {
            VarMeta distrMappingDVarMeta = new VarMeta();
            distrMappingDVarMeta.add(new ItemMappingDataMeta(pData.getCatalogDistrMapping()));
            add(distrMappingDVarMeta);
        }

        {
            VarMeta distrBusEntityVarMeta = new VarMeta();
            for (Object oDistributor : pData.getMappedDistributors()) {
                BusEntityData distr = (BusEntityData) oDistributor;
                distrBusEntityVarMeta.add(new BusEntityDataMeta(distr));
            }
            add(distrBusEntityVarMeta);
        }

        {
            VarMeta distrMappingVarMeta = new VarMeta();
            for (Object oDistrMapping : pData.getDistributorMappings()) {
                ItemMappingData distrMapping = (ItemMappingData) oDistrMapping;
                distrMappingVarMeta.add(new ItemMappingDataMeta(distrMapping));
            }
            add(distrMappingVarMeta);
        }

        {
            VarMeta catalogCategoryVarMeta = new VarMeta();
            for (Object oCatalogCategory : pData.getCatalogCategories()) {
                CatalogCategoryData catalogCategory = (CatalogCategoryData) oCatalogCategory;
                catalogCategoryVarMeta.add(new ItemDataMeta(pData.getItemData()));
                if (catalogCategory.getChildCategories() != null) {
                    for (Object oChildItem : catalogCategory.getChildCategories()) {
                        catalogCategoryVarMeta.add(new ItemDataMeta((ItemData) oChildItem));
                    }
                }
            }
            add(catalogCategoryVarMeta);
        }

        {
            VarMeta certifiedCompaniesVarMeta = new VarMeta();
            for (Object oCertComp : pData.getCertifiedCompanies()) {
                certifiedCompaniesVarMeta.add(new ItemMappingDataMeta((ItemMappingData) oCertComp));
            }
            add(certifiedCompaniesVarMeta);
        }

        {
            VarMeta certCompaniesBusEntityVarMeta = new VarMeta();
            for (Object oCertComp : pData.getCertCompaniesBusEntityDV()) {
                certCompaniesBusEntityVarMeta.add(new BusEntityDataMeta((BusEntityData) oCertComp));
            }
            add(certCompaniesBusEntityVarMeta);
        }

        {
            VarMeta itemMetaHVarMeta = new VarMeta();
            itemMetaHVarMeta.add(new ItemMetaDataMeta(new TableField(ItemMetaDataAccess.ITEM_ID, pData.getItemData().getItemId())));
            add(itemMetaHVarMeta);
        }

    }
}
