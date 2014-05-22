package com.cleanwise.service.apps.dataexchange.xpedx;

import com.cleanwise.service.api.value.*;

import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;


public class AppCatalogData implements Serializable {

      private CatalogData mCatalog;
      private Map<Integer/*busEntityId*/, Map<String/*catalogAssocCd*/, CatalogAssocData>> mCatalogAssocMap;

      private ContractData mContract;

      private Map<Integer, CatalogStructureData> mAppShoppingCatalogCategoryMap;
      private Map<Integer, CatalogStructureData> mAppShoppingCatalogProductMap;
      private Map<ItemReference, ItemAssocData> mAppShoppingItemCategoryAssocMap;
      private Map<ItemReference, ContractItemData> mAppContractItemsMap;

      public AppCatalogData() {
          this.mCatalogAssocMap = new HashMap<Integer/*busEntityId*/, Map<String/*catalogAssocCd*/, CatalogAssocData>>();
          this.mAppShoppingCatalogProductMap = new HashMap<Integer, CatalogStructureData>();
          this.mAppShoppingItemCategoryAssocMap = new HashMap<ItemReference, ItemAssocData>();
          this.mAppContractItemsMap = new HashMap<ItemReference, ContractItemData>();
      }

      public Map<Integer/*busEntityId*/, Map<String/*catalogAssocCd*/, CatalogAssocData>> getCatalogAssocMap() {
          return mCatalogAssocMap;
      }

      public void setCatalogAssocMap(Map<Integer/*busEntityId*/, Map<String/*catalogAssocCd*/, CatalogAssocData>> pCatalogAssocMap) {
          this.mCatalogAssocMap = pCatalogAssocMap;
      }

      public CatalogData getCatalog() {
          return mCatalog;
      }

      public void setCatalog(CatalogData pCatalog) {
          this.mCatalog = pCatalog;
      }

      public ContractData getContract() {
          return mContract;
      }

      public void setContract(ContractData pContract) {
          this.mContract = pContract;
      }

      public void setAppShoppingCatalogCategoryMap(Map<Integer, CatalogStructureData> pAppShoppingCatalogCategoryMap) {
          this.mAppShoppingCatalogCategoryMap = pAppShoppingCatalogCategoryMap;
      }

      public Map<Integer, CatalogStructureData> getAppShoppingCatalogCategoryMap() {
          return mAppShoppingCatalogCategoryMap;
      }

      public void setAppShoppingCatalogProductMap(Map<Integer, CatalogStructureData> pAppShoppingCatalogProductMap) {
          this.mAppShoppingCatalogProductMap = pAppShoppingCatalogProductMap;
      }

      public Map<Integer, CatalogStructureData> getAppShoppingCatalogProductMap() {
          return mAppShoppingCatalogProductMap;
      }

      public void setAppShoppingItemCategoryAssocMap(Map<ItemReference, ItemAssocData> pAppShoppingItemCategoryAssocMap) {
          this.mAppShoppingItemCategoryAssocMap = pAppShoppingItemCategoryAssocMap;
      }

      public Map<ItemReference, ItemAssocData> getAppShoppingItemCategoryAssocMap() {
          return mAppShoppingItemCategoryAssocMap;
      }

      public void setAppContractItemsMap(Map<ItemReference, ContractItemData> pAppContractItemsMap) {
          this.mAppContractItemsMap = pAppContractItemsMap;
      }

      public Map<ItemReference, ContractItemData> getAppContractItemsMap() {
          return mAppContractItemsMap;
      }
  }

