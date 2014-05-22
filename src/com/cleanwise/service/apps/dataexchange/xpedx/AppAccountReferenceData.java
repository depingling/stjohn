package com.cleanwise.service.apps.dataexchange.xpedx;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

public class AppAccountReferenceData implements Serializable {

      private String mAccountNumber;

      private Map<Integer/*AccountID*/, AppAccountData> mAppAcountDataMap;
      private Map<CatalogReference, AppCatalogData> mAppCatalogMap;

      public AppAccountReferenceData() {
          this.mAppAcountDataMap = new HashMap<Integer/*AccountID*/, AppAccountData>();
          this.mAppCatalogMap = new HashMap<CatalogReference, AppCatalogData>();
      }

      public String getAccountNumber() {
          return mAccountNumber;
      }

      public void setAccountNumber(String pAccountNumber) {
          this.mAccountNumber = pAccountNumber;
      }


      public Map<CatalogReference, AppCatalogData> getAppCatalogMap() {
          return mAppCatalogMap;
      }

      public void setAppCatalogMap(Map<CatalogReference, AppCatalogData> pAppCatalogMap) {
          this.mAppCatalogMap = pAppCatalogMap;
      }


      public Map<Integer/*AccountID*/, AppAccountData> getAppAcountDataMap() {
          return mAppAcountDataMap;
      }

      public void setAppAcountDataMap(Map<Integer/*AccountID*/, AppAccountData> pAppAcountDataMap) {
          this.mAppAcountDataMap = pAppAcountDataMap;
      }
  }

