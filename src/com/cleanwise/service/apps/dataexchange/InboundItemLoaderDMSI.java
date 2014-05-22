package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

import com.cleanwise.service.api.session.Manufacturer;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.Catalog;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.ItemInformation;
import com.cleanwise.service.api.APIAccess;

import com.cleanwise.service.apps.loaders.CSVFileParser;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;


import java.io.InputStream;
import java.util.*;

public class InboundItemLoaderDMSI extends InboundFlatFile {
	protected Logger log = Logger.getLogger(this.getClass());
    private CatalogInformation catalogInfoEjb;
    private Catalog catalogEjb;
    private ItemInformation iIEjb;
    private PropertyService property;
    static HashMap newProductData = new HashMap();
    static HashMap existingProductData = new HashMap();
    static final public String user = "itemLoader";
    static int storeId = 0;
    static int catalogId = 0;
	static final int SKU_MINIMUM = 10000;

    protected void processParsedObject(Object pParsedObject) throws Exception{
        ProductData pD = new ProductData();
        if(pParsedObject == null)
            log.error(
                "No parsed master item object present",
                new IllegalArgumentException("No parsed master item object present")
            );
        if (pParsedObject.getClass().getName() == ItemDMSIView.class.getName()){
            ItemDMSIView itemDMSIView = (ItemDMSIView)pParsedObject;

            Distributor distributorEjb =  APIAccess.getAPIAccess().getDistributorAPI();
            PropertyService propertyEjb = APIAccess.getAPIAccess().getPropertyServiceAPI();
            iIEjb = APIAccess.getAPIAccess().getItemInformationAPI();
            catalogEjb = APIAccess.getAPIAccess().getCatalogAPI();

            String distributorName = itemDMSIView.getDistributorName();

            distributorName = distributorName.trim();
            BusEntitySearchCriteria critDistr = new BusEntitySearchCriteria();

            ArrayList busEntityIds = null;
			try {
				busEntityIds = propertyEjb.getBusEntityVector(
				    RefCodeNames.PROPERTY_TYPE_CD.CUSTOMER_REFERENCE_CODE,
				    Integer.toString(itemDMSIView.getCustomerReferenceCode())
				);
			} catch (DataNotFoundException e1) {
	            log.error("Distributor not found by customer reference code " + itemDMSIView.getCustomerReferenceCode(), e1);
			}

            IdVector v = new IdVector();
            v.addAll(busEntityIds);
            critDistr.setDistributorBusEntityIds(v);
            ArrayList types = new ArrayList();
            types.add(RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
            critDistr.setSearchTypeCds(types);

            DistributorDataVector distributorDV = distributorEjb.getDistributorByCriteria(critDistr);
            DistributorData distributorD = null;
            int distributorsCount = 0;
            for (Iterator distributorsIterator = distributorDV.iterator(); distributorsIterator.hasNext(); distributorsCount++) {
            	if ( (distributorsIterator.next()) == null) {
            		break;
            	}
            }
            if (distributorsCount > 0) {
            	distributorD = (DistributorData) distributorDV.get(0);
            	if (distributorsCount > 1) {
             	    log.warn(
            	        "" + distributorsCount + " distributors found with a customer reference code " + itemDMSIView.getCustomerReferenceCode()
            	);
            	}
            } else {
            	if (distributorDV.isEmpty()) {
    	            log.error(
    	                "Distributor not found by customer reference code " + itemDMSIView.getCustomerReferenceCode(),
    	                new DataNotFoundException("Distributor not found"));
            	}
            }
            BusEntityDataVector bEDV = new BusEntityDataVector();
            bEDV.add(distributorD.getBusEntity());
            pD.setMappedDistributors(bEDV);

            TradingPartnerData partner = translator.getPartner();
            int tradingPartnerId = partner.getTradingPartnerId();
            TradingPartner tPEjb = APIAccess.getAPIAccess().getTradingPartnerAPI();
            Store storeEjb = APIAccess.getAPIAccess().getStoreAPI();

//            tradingPartnerId = 2351;
            int[] storeBusinessEntityIds = tPEjb.getBusEntityIds(tradingPartnerId, RefCodeNames.TRADING_PARTNER_ASSOC_CD.STORE);
            for (int i=0; i<storeBusinessEntityIds.length; i++) {
            	storeId = storeBusinessEntityIds[i];
            }

            catalogInfoEjb = APIAccess.getAPIAccess().getCatalogInformationAPI();
            IdVector itDV = iIEjb.getItemsCollectionByItemShortDescription(itemDMSIView.getCategory());

            CatalogDataVector storeCatalogsDataV = catalogInfoEjb.getSTORETypeCatalogsByStoreId(storeId);

            CatalogData storeCatalogD = null;
            IdVector storeCatalogIds = new IdVector();

            int catalogsCount = 0;
            for (Iterator catalogsIterator = storeCatalogsDataV.iterator(); catalogsIterator.hasNext(); catalogsCount++) {
            	if ( (catalogsIterator.next()) == null) {
            		break;
            	}
            }

            if (catalogsCount > 0) {
                storeCatalogD = (CatalogData) storeCatalogsDataV.get(0);
                storeCatalogIds.add(new Integer(storeCatalogD.getCatalogId()));
            }
            if (storeCatalogIds.isEmpty()) {

            } else {
                if (catalogsCount > 1) {
                	Exception moreThanOneStoreCatalogEx =
                	    new Exception(
                	        "" + catalogsCount + " STORE type catalogs found for the store with storeId: " + storeId
                	    );
                	log.error(
                	    "" + catalogsCount + " STORE type catalogs found for the store with storeId: " + storeId,
                	    moreThanOneStoreCatalogEx
                	);
                    throw moreThanOneStoreCatalogEx;
                } else {
                	catalogId = ((Integer) storeCatalogIds.get(0)).intValue();
                	log.info("CATALOG ID "+catalogId);
                }
            }
            ItemDataVector categoryItemsDataV = catalogInfoEjb.getCatalogCategories(storeCatalogIds);
            IdVector categoriesFound = new IdVector();
            Iterator itDVIterator = itDV.iterator();
            Iterator categDVI = categoryItemsDataV.iterator();
            int categoriesCount = 0;
            while (itDVIterator.hasNext()) {
            	int itId = ((Integer) itDVIterator.next()).intValue();
            	while (categDVI.hasNext()) {
                    int iId = ((ItemData) categDVI.next()).getItemId();
                    if (iId==itId) {
                        categoriesFound.add(new Integer(iId));
                        categoriesCount++;
                    }
            	}
            }
            int categoryId = 0;
            CatalogStructureData catalogStruc4NewCategory = null;
        	CatalogCategoryDataVector catalogCategDV = new CatalogCategoryDataVector();
            if (categoriesFound.isEmpty()) {
     /// create a category
            	CatalogCategoryData cCatD = catalogEjb.saveStoreCategory(
            			catalogId,
                        0,
                        itemDMSIView.getCategory(),
                        0,
                        user
                );
            	catalogCategDV.add(cCatD);
            	pD.setCatalogCategories(catalogCategDV);

            	cCatD.setCatalogCategoryLongDesc(cCatD.getCatalogCategoryShortDesc());
            	ItemData categoryItemData = cCatD.getItemData();
            	categoryItemData.setSkuNum(categoryItemData.getItemId() + SKU_MINIMUM);
            	catalogEjb.updateItem(categoryItemData);

            } else {
                if (categoriesCount > 1) {
                	Exception moreThanOneCategoriesEx =
                	    new Exception(
                	        "" + categoriesCount + " categories found for the name: " + itemDMSIView.getCategory()
                	    );
                	log.error(
                		"" + categoriesCount + " categories found for the name: " + itemDMSIView.getCategory(),
                	    moreThanOneCategoriesEx
                	);
                    throw moreThanOneCategoriesEx;
                } else {
                	categoryId = ((Integer) categoriesFound.get(0)).intValue();
                    CatalogStructureDataVector catalogSDV = catalogInfoEjb.getCatalogStructure(categoryId, catalogId);
                    if (!catalogSDV.isEmpty()) {
                    	pD.setCatalogStructure((CatalogStructureData) catalogSDV.get(0));
                    }
                    CatalogCategoryData cCatD = new CatalogCategoryData();
                    cCatD.setCatalogCategoryId(categoryId);
                    catalogCategDV.add(cCatD);
                    pD.setCatalogCategories(catalogCategDV);
                }
            }


            Manufacturer manufactureEjb =  APIAccess.getAPIAccess().getManufacturerAPI();
            catalogInfoEjb = APIAccess.getAPIAccess().getCatalogInformationAPI();
            catalogEjb = APIAccess.getAPIAccess().getCatalogAPI();

            if(partner == null) {
            	IllegalArgumentException tPEx = new IllegalArgumentException("Trading Partner ID cannot be determined");
            	log.error("Trading Partner ID cannot be determined", tPEx);
                throw tPEx;
            }


            String manufacturerName = itemDMSIView.getManufacturerName();
            if(manufacturerName == null || manufacturerName.trim().length()==0) {
            	DataNotFoundException manufacturerNotSpecifiedEx = new DataNotFoundException("Manufacturer is not specified.");
            	log.error("Manufacturer is not specified.", manufacturerNotSpecifiedEx);
                throw manufacturerNotSpecifiedEx;
            }
            manufacturerName = manufacturerName.trim();
            BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
            crit.setSearchNameType(EntitySearchCriteria.EXACT_MATCH);
            crit.setSearchName(manufacturerName);
            ManufacturerDataVector manufacturerDV = manufactureEjb.getManufacturerByCriteria(crit);
            ManufacturerData manufacturerD = null;
            int manufacturersCount = 0;
            for (Iterator manufacturersIterator = manufacturerDV.iterator(); manufacturersIterator.hasNext(); manufacturersCount++) {
            	if ( (manufacturersIterator.next()) == null) {
            		break;
            	}
            }

            if (manufacturersCount > 0) {
                manufacturerD = (ManufacturerData) manufacturerDV.get(0);
            }
            if (manufacturerD == null) {
            	DataNotFoundException manufacturerNotFoundEx = new DataNotFoundException("No manufacturer found.");
            	log.error("No manufacturer found.", manufacturerNotFoundEx);
                throw manufacturerNotFoundEx;
            }


            ItemData iData = new ItemData();
            iData.setShortDesc(itemDMSIView.getItemName());
            iData.setLongDesc(itemDMSIView.getItemName());
            iData.setItemTypeCd(RefCodeNames.ITEM_TYPE_CD.PRODUCT);
            iData.setItemStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
            pD.setItemData(iData);

            BusEntityData manufacturer = manufacturerD.getBusEntity();

            pD.setManufacturer(manufacturer);

            pD.setManuMapping(manufacturer, itemDMSIView.getManufacturerSku());

            pD.setPack(Integer.toString(itemDMSIView.getPack()));
            pD.setUom(itemDMSIView.getUOM());
            pD.setColor(itemDMSIView.getColor());

            pD.setDistributorSku(distributorD.getBusEntity().getBusEntityId(), itemDMSIView.getDistributorSku());

            pD.setDistributorPack(distributorD.getBusEntity().getBusEntityId(), Integer.toString(itemDMSIView.getDistributorPack()));

            pD.setDistributorUom(distributorD.getBusEntity().getBusEntityId(), itemDMSIView.getDistributorUOM());

            pD.setCostPrice(Double.valueOf(itemDMSIView.getPrice()).doubleValue());

////        Check if item already exists

            ItemMappingDataVector iMDV = iIEjb.getItemMapping(distributorD.getBusEntity().getBusEntityId(), itemDMSIView.getDistributorSku());
            int itemMappingCount = 0;
            for (Iterator iMDIterator = iMDV.iterator(); iMDIterator.hasNext(); itemMappingCount++) {
            	if ( (iMDIterator.next()) == null) {
            		break;
            	}
            }
            ArrayList hMKey = new ArrayList();
            hMKey.add(new Integer(itemDMSIView.getCustomerReferenceCode()));
            hMKey.add(itemDMSIView.getDistributorSku());
            if (newProductData.containsKey(hMKey)) {
          		Exception itemAddingEx = new Exception("The master item is already due to be added.");
 	            log.error(
 	                "The master item with customer reference code " + itemDMSIView.getCustomerReferenceCode() + " and distributor SKU " + itemDMSIView.getDistributorSku() + " is already due to be added.",
 	               itemAddingEx);
 	            throw itemAddingEx;
            } else {
            	 if (existingProductData.containsKey(hMKey)) {
               		Exception itemUpdatingEx = new Exception("The master item is already due to be updated.");
     	            log.error(
     	                "The master item with customer reference code " + itemDMSIView.getCustomerReferenceCode() + " and distributor SKU " + itemDMSIView.getDistributorSku() + " is already due to be updated.",
     	               itemUpdatingEx);
     	            throw itemUpdatingEx;
            	 }
            }

             if (iMDV.isEmpty()) {
             	newProductData.put(hMKey, pD);
             } else {
             	if (itemMappingCount > 1) {
             		Exception moreThan1ItemEx = new Exception("More than one master item found.");
     	            log.error(
     	                "" + itemMappingCount + "master items already exist for customer reference code " + itemDMSIView.getCustomerReferenceCode() + " and distributore SKU " + itemDMSIView.getDistributorSku(),
     	                moreThan1ItemEx);
     	            throw moreThan1ItemEx;
             	}
             	ItemMappingData mD = (ItemMappingData) iMDV.get(0);
             	pD.getItemData().setItemId(mD.getItemId());
             	pD.setSkuNum(mD.getItemId() + SKU_MINIMUM);
             	pD.getItemData().setAddDate(mD.getAddDate());
             	existingProductData.put(hMKey, pD);
             	ItemMappingData iMappD = (ItemMappingData) iMDV.get(0);
             }

            return;

        } else {
          log.error("InboundItemLoaderDMSI. Wrong type of object we are wait for parsing");
        }
      }

    public void translate(InputStream pIn, String pStreamType) throws Exception {
    	CSVFileParser parser = new CSVFileParser();
        parser.parse(pIn);
        parser.cleanUpResult();
        cleanUpLoader();
        parser.processParsedStrings(this);
        save();
    }

    private void save() {
    	try {
			iIEjb.addMasterItems(newProductData, user, storeId, catalogId);
		} catch (RemoteException ex) {
			log.error("addMasterItem failed.", ex);
		}
    	try {
			iIEjb.updateDMSIItems(existingProductData, user, storeId, catalogId);
		} catch (RemoteException ex) {
			log.error("updateMasterItems failed.", ex);
		}
    }

    private void cleanUpLoader() {
    	newProductData.clear();
    	existingProductData.clear();
        storeId = 0;
        catalogId = 0;
        return;
    }

}
