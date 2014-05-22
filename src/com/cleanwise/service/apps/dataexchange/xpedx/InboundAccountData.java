package com.cleanwise.service.apps.dataexchange.xpedx;

import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;


public class InboundAccountData implements Serializable {

        private String mAccountNumber;
        Map<CatalogReference, InboundCatalogData> mInboundCatalogMap;

        public InboundAccountData() {
            this.mInboundCatalogMap = new HashMap<CatalogReference, InboundCatalogData>();
        }

        public String getAccountNumber() {
            return mAccountNumber;
        }

        public void setAccountNumber(String pAccountNumber) {
            this.mAccountNumber = pAccountNumber;
        }

        public Map<CatalogReference, InboundCatalogData> getInboundCatalogMap() {
            return mInboundCatalogMap;
        }

        public void setInboundCatalogMap(Map<CatalogReference, InboundCatalogData> pInboundCatalogMap) {
            this.mInboundCatalogMap = pInboundCatalogMap;
        }

        public String toString() {
            return "[mAccountNumber: " + mAccountNumber + ", mInboundCatalogMap: " + mInboundCatalogMap + "]";
        }

    }
