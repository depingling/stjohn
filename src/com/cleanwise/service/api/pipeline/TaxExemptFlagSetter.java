package com.cleanwise.service.api.pipeline;

import java.sql.Connection;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.cachecos.CacheKey;
import com.cleanwise.service.api.cachecos.Cachecos;
import com.cleanwise.service.api.cachecos.CachecosManager;
import com.cleanwise.service.api.cachecos.ObjectMeta;
import com.cleanwise.service.api.cachecos.TableField;
import com.cleanwise.service.api.cachecos.VarMeta;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.CatalogStructureDataAccess;
import com.cleanwise.service.api.dao.ItemMappingDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.meta.PropertyDataMeta;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CatalogStructureData;
import com.cleanwise.service.api.value.CatalogStructureDataVector;
import com.cleanwise.service.api.value.ItemMappingData;
import com.cleanwise.service.api.value.ItemMappingDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.PairViewVector;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.SiteData;
import org.apache.log4j.*;
/**
 * Title:  TaxExemptFlagSetter
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         17.04.2007
 * Time:         14:08:33
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class TaxExemptFlagSetter implements OrderPipeline {

    private final static Logger log = Logger.getLogger(TaxExemptFlagSetter.class);

    public OrderPipelineBaton process(OrderPipelineBaton pBaton, OrderPipelineActor pActor, Connection pCon, APIAccess pFactory) throws PipelineException {
        {

            try {

                OrderData order = pBaton.getOrderData();
                boolean historicalOrder = pBaton.isHistoricalOrderPreOrderProps();
                if (order == null) {
                    throw new Exception("order is null");
                }

                //gets siteId
                int siteId = order.getSiteId();
                if (siteId <= 0) {
                    throw new Exception("unknown site identifier");
                }

                //gets accountId
                int accountId = order.getAccountId();
                if (accountId <= 0) {
                    throw new Exception("unknown account identifier");
                }

                //gets storeId
                int storeId = order.getStoreId();
                if (storeId <= 0) {
                    Store storeEjb = pFactory.getStoreAPI();
                    try {
                        storeId = storeEjb.getStoreIdByAccount(accountId);
                    } catch (DataNotFoundException e) {
                        log.info("process => " + e.getMessage());
                    }
                }

                //gets cache manager
                CachecosManager cacheManager = Cachecos.getCachecosManager();

                //gets taxable flag for order                   
                Order orderEjb = pFactory.getOrderAPI();
                boolean orderTaxableFl = isTaxableOrder(orderEjb, cacheManager, storeId, accountId, siteId);           

                //gets catalogId
                int catalogId = getSiteCatalogId(siteId, pFactory, cacheManager);

                OrderItemDataVector items = pBaton.getOrderItemDataVector();
                if (items == null) {
                    throw new Exception("item collection is null");
                }

                for (Object oItem : items) {

                    OrderItemData item = (OrderItemData) oItem;
                    
                    try {

                        CatalogStructureData csd = null;

                        //search in cache 
                        if (cacheManager != null && cacheManager.isStarted()) {
                            CacheKey productKey = ProductDAO.getCacheKey(catalogId, item.getItemId());
                            try {
                                ProductData product = (ProductData) cacheManager.get(productKey);
                                if (product != null) {
                                    csd = product.getCatalogStructure();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        // if data not found in cache
                        if (csd == null) { 
                        	DBCriteria dbc = new DBCriteria();
                        	dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, catalogId);
                        	dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, item.getItemId());
                        	CatalogStructureDataVector catalogStructureDV = CatalogStructureDataAccess.select(pCon, dbc);
	                        //bug # 4859: Commented and added to show appropriate error messages when the items are not in catalog.
	                        /*if (catalogStructureDV == null || (catalogStructureDV.size() > 1 || catalogStructureDV.size() == 0)) {
	                            throw new Exception("Catalog structure data is not correct for item: "+item.getItemShortDesc()+" item id: "+item.getItemId()+" and catalog: "+catalogId);
	                        }*/
                        if( catalogStructureDV==null || catalogStructureDV.size() == 0) {
                                throw new Exception("Item: "+item.getItemShortDesc()+" item id: "+item.getItemId()+" is not in catalog: "+catalogId);
                          } else if(catalogStructureDV.size()>1) {
                            throw new Exception("Catalog structure data is not correct for item: "+item.getItemShortDesc()+" item id: "+item.getItemId()+" and catalog: "+catalogId);
                          }
                        	csd = (CatalogStructureData) catalogStructureDV.get(0);
                        	DBCriteria dbCriteria = new DBCriteria();
                        	dbCriteria.addEqualTo(ItemMappingDataAccess.ITEM_ID, item.getItemId());
                        	dbCriteria.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID, csd.getBusEntityId());
                        	ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(pCon, dbCriteria);
	                        if(itemMappingDV.size()>0) {
	                              ItemMappingData  itemMapping = (ItemMappingData)itemMappingDV.get(0);
	                              if(itemMapping.getItemNum()==null || itemMapping.getItemNum().trim().equals("")) {
	                                  throw new Exception("Item: "+item.getItemShortDesc()+" item id: "+item.getItemId()+" is missing distributor item num for distributor id "+item.getDistErpNum());
	                              }
	                        }
                        }

                        if(Utility.isSet(item.getTaxExempt())){
                        	
                        	String isTaxExempt = item.getTaxExempt();
                        	if(isTaxExempt.equals("F")){
                        		item.setTaxExempt(RefCodeNames.ORDER_ITEM_TAX_EXEMPT_CD.FALSE);
                        	}else if(isTaxExempt.equals("T")){
                        		item.setTaxExempt(RefCodeNames.ORDER_ITEM_TAX_EXEMPT_CD.TRUE);
                        	}
                        	
                        }else{
                        	boolean itemIaxableFl = !Utility.isTrue(csd.getTaxExempt());
                       
                        	boolean orderItemTaxExempt = !orderTaxableFl || !itemIaxableFl;

                        	item.setTaxExempt(orderItemTaxExempt ? RefCodeNames.ORDER_ITEM_TAX_EXEMPT_CD.TRUE
                        			: RefCodeNames.ORDER_ITEM_TAX_EXEMPT_CD.FALSE);

                        }
                    } catch (Exception e) {
                        if (historicalOrder) {
                            //default to taxable (arbitrarily selected) for historical orders
                            item.setTaxExempt(RefCodeNames.ORDER_ITEM_TAX_EXEMPT_CD.FALSE);
                        } else {
                            e.printStackTrace();
                            pBaton.addError(pCon, OrderPipelineBaton.ERROR_GETTING_TAX_EXEMPT_FLAG,
                                    null, RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW,
                                    0, 0, "pipeline.message.errorItemNotFoundTaxExemptFlag",
                                    "" + item.getItemId(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                pBaton.addError(pCon, OrderPipelineBaton.ERROR_GETTING_TAX_EXEMPT_FLAG,
                        null, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,
                        0, 0, "pipeline.message.exception",
                        e.getMessage(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);

            } finally {
                pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
            }
            return pBaton;
        }
    }

    private boolean isTaxableOrder(Order orderEjb, CachecosManager cacheManager, int storeId, int accountId, int siteId) throws Exception {
        Boolean isTaxable = null;

        if (cacheManager != null && cacheManager.isStarted()) {
            try {
                PairViewVector keyParam = new PairViewVector();
                keyParam.add(new PairView("Store", storeId));
                keyParam.add(new PairView("Account", accountId));
                keyParam.add(new PairView("Site", siteId));
                CacheKey cacheKey = new CacheKey("TaxExemptFlag", keyParam);
                isTaxable = (Boolean) cacheManager.get(cacheKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (isTaxable == null) {

            isTaxable = orderEjb.isTaxableOrder(storeId, accountId, siteId);

            if (cacheManager != null && cacheManager.isStarted()) {

                CacheKey cacheKey;
                try {

                    PairViewVector keyParam = new PairViewVector();
                    keyParam.add(new PairView("Store", storeId));
                    keyParam.add(new PairView("Account", accountId));
                    keyParam.add(new PairView("Site", siteId));
                    cacheKey = new CacheKey("TaxExemptFlag", keyParam);

                    ObjectMeta taxableIndicatorMeta = new ObjectMeta();
                    VarMeta taxableIndicatorVarMeta = new VarMeta();

                    PropertyDataMeta storeTaxableIndicatorMeta = new PropertyDataMeta(new TableField(PropertyDataAccess.BUS_ENTITY_ID, storeId), new TableField(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR));
                    PropertyDataMeta accountTaxableIndicatorMeta = new PropertyDataMeta(new TableField(PropertyDataAccess.BUS_ENTITY_ID, accountId), new TableField(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR));
                    PropertyDataMeta siteTaxableIndicatorMeta = new PropertyDataMeta(new TableField(PropertyDataAccess.BUS_ENTITY_ID, siteId), new TableField(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR));

                    taxableIndicatorVarMeta.add(storeTaxableIndicatorMeta);
                    taxableIndicatorVarMeta.add(accountTaxableIndicatorMeta);
                    taxableIndicatorVarMeta.add(siteTaxableIndicatorMeta);

                    taxableIndicatorMeta.add(taxableIndicatorVarMeta);

                    cacheManager.put(cacheKey, isTaxable, taxableIndicatorMeta);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        return isTaxable;

    }

    private int getSiteCatalogId(int pSiteId, APIAccess pFactory, CachecosManager cacheManager) throws Exception {

        int siteCatalogId = 0;

        if (cacheManager != null && cacheManager.isStarted()) {
            try {
                CacheKey cacheKey = BusEntityDAO.getSiteCacheKey(pSiteId);
                SiteData site = (SiteData) cacheManager.get(cacheKey);
                if (site != null) {
                    siteCatalogId = site.getSiteCatalogId();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (siteCatalogId == 0) {
            CatalogInformation catEjb = pFactory.getCatalogInformationAPI();
            CatalogData catData = catEjb.getSiteCatalog(pSiteId);
            siteCatalogId = catData.getCatalogId();
        }

        return siteCatalogId;
    }

}
