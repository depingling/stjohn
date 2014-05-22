package com.cleanwise.service.apps.dataexchange.xpedx;

import com.cleanwise.service.api.util.Utility;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class MasterCustomerData implements Serializable {

        private String mMasterCustomerName;
        private int mStoreId;
        private int mTradingPartnerId;

        private HashMap<AccountReference/*accountNumer*/ , InboundAccountData> mInboundAccountMap;
        private HashMap<DistributorReference/*distributor*/, InboundDistributorData> mInboundDistributorMap;
        private HashMap<ManufacturerReference/*manufacturer*/, InboundManufacturerData> mInboundManufacturerMap;


        public MasterCustomerData(int pStoreId, int pTradingPartnerId) {
            this.mStoreId = pStoreId;
            this.mTradingPartnerId = pTradingPartnerId;
            this.mInboundAccountMap = new HashMap<AccountReference/*accountNumer*/ , InboundAccountData>();
            this.mInboundDistributorMap = new HashMap<DistributorReference/*distributor*/, InboundDistributorData>();
            this.mInboundManufacturerMap = new HashMap<ManufacturerReference/*manufacturer*/, InboundManufacturerData>();
        }

        public String getMasterCustomerName() {
            return mMasterCustomerName;
        }

        public void setMasterCustomerName(String pMasterCustomerName) {
            this.mMasterCustomerName = pMasterCustomerName;
        }

        public HashMap<AccountReference, InboundAccountData> getInboundAccountMap() {
            return mInboundAccountMap;
        }

        public void setInboundAccountMap(HashMap<AccountReference, InboundAccountData> pInboundAccountMap) {
            this.mInboundAccountMap = pInboundAccountMap;
        }

        public HashMap<DistributorReference, InboundDistributorData> getInboundDistributorMap() {
            return mInboundDistributorMap;
        }

        public void setInboundDistributorMap(HashMap<DistributorReference, InboundDistributorData> pInboundDistributorMap) {
            this.mInboundDistributorMap = pInboundDistributorMap;
        }

        public HashMap<ManufacturerReference, InboundManufacturerData> getInboundManufacturerMap() {
            return mInboundManufacturerMap;
        }

        public void setInboundManufacturerMap(HashMap<ManufacturerReference, InboundManufacturerData> pInboundManufacturerMap) {
            this.mInboundManufacturerMap = pInboundManufacturerMap;
        }


        public int getTradingPartnerId() {
            return mTradingPartnerId;
        }

        public void setTradingPartnerId(int pTradingPartnerId) {
            this.mTradingPartnerId = pTradingPartnerId;
        }

        public int getStoreId() {
            return mStoreId;
        }

        public void setStoreId(int pStoreId) {
            this.mStoreId = pStoreId;
        }


        public Set<CatalogReference> getCatalogLoaderFields(AccountReference pAccountReference) {
            Set<CatalogReference> catalogloaderFields = new HashSet<CatalogReference>();
            InboundAccountData iad = mInboundAccountMap.get(pAccountReference);
            if (iad != null) {
                catalogloaderFields.addAll(iad.getInboundCatalogMap().keySet());
            }
            return catalogloaderFields;
        }

        public String toString() {
            return "[mMasterCustomerName: " + mMasterCustomerName + ", mStoreId: " + mStoreId + ", mTradingPartnerId: " + mTradingPartnerId + ", mInboundAccountMap: " + mInboundAccountMap + ", mInboundDistributorMap: " + mInboundDistributorMap + ", mInboundManufacturerMap: " + mInboundManufacturerMap + "]";
        }

        public Set<ItemReference> getItemReferences() {
            Set<ItemReference> itemReferences = new HashSet<ItemReference>();
            for (InboundAccountData iAccountData : this.getInboundAccountMap().values()) {
                for (InboundCatalogData iCatalogData : iAccountData.getInboundCatalogMap().values()) {
                    for (InboundItemData iItemData : iCatalogData.getInboundItemMap().values()) {
                        itemReferences.add(new ItemReference(iItemData.getDistributor(), iItemData.getDistSKU()));
                    }
                }
            }
            return itemReferences;
        }

        public Set<ItemReference> getItemReferences(AccountReference pAccountRef, CatalogReference pCatRef) {
            return this.getInboundAccountMap().get(pAccountRef).getInboundCatalogMap().get(pCatRef).getInboundItemMap().keySet();
        }

        public Set<String> getImageUrls() {
            Set<String> urls = new HashSet<String>();
            for (InboundAccountData iAccountData : this.getInboundAccountMap().values()) {
                for (InboundCatalogData iCatalogData : iAccountData.getInboundCatalogMap().values()) {
                    for (InboundItemData iItemData : iCatalogData.getInboundItemMap().values()) {
                        if (Utility.isSet(iItemData.getImage())) {
                            urls.add(iItemData.getImage());
                        }
                    }
                }
            }
            return urls;
        }

        public Map<ItemReference, InboundItemData> getInboundItemsMap() {
            Map<ItemReference, InboundItemData> items = new HashMap<ItemReference, InboundItemData>();
            for (InboundAccountData iAccountData : this.getInboundAccountMap().values()) {
                for (InboundCatalogData iCatalogData : iAccountData.getInboundCatalogMap().values()) {
                    for (InboundItemData iItemData : iCatalogData.getInboundItemMap().values()) {
                        if (Utility.isSet(iItemData.getImage())) {
                            items.put(new ItemReference(iItemData.getDistributor(), iItemData.getDistSKU()), iItemData);
                        }
                    }
                }
            }
            return items;

        }

        public Set<Integer> getFreightTableIds() {
            Set<Integer> freightTableIds = new HashSet<Integer>();
            for (InboundAccountData iAccountData : this.getInboundAccountMap().values()) {
                for (InboundCatalogData iCatalogData : iAccountData.getInboundCatalogMap().values()) {
                    for (InboundItemData iItemData : iCatalogData.getInboundItemMap().values()) {
                        if (iItemData.getFreightTableID() != null && iItemData.getFreightTableID() > 0) {
                            freightTableIds.add(iItemData.getFreightTableID());
                        }
                    }
                }
            }
            return freightTableIds;
        }

        public Set<String> getLocales() {
            Set<String> locales = new HashSet<String>();
            for (InboundAccountData iAccountData : this.getInboundAccountMap().values()) {
                for (InboundCatalogData iCatalogData : iAccountData.getInboundCatalogMap().values()) {
                    locales.add(iCatalogData.getLocale());
                }
            }

            return locales;
        }

    }

