package com.cleanwise.service.apps.dataexchange.xpedx;

import com.cleanwise.service.api.value.CatalogStructureData;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.ItemData;

import java.util.List;
import java.io.Serializable;

public class AXCatalogItemData implements Serializable {

      private ItemData mItem;
      private ItemData mCategory;
      private CatalogStructureData mProductCatalogStructure;
      private List<CatalogStructureData> mCategoryCatalogStructures;
      private CatalogData mCatalog;

      public ItemData getCategory() {
          return mCategory;
      }

      public void setCategory(ItemData pCategory) {
          this.mCategory = pCategory;
      }

      public ItemData getItem() {
          return mItem;
      }

      public void setItem(ItemData pItem) {
          this.mItem = mItem;
      }

      public CatalogStructureData getProductCatalogStructures() {
          return mProductCatalogStructure;
      }

      public void setProductCatalogStructure(CatalogStructureData pProductCatalogStructure) {
          this.mProductCatalogStructure = pProductCatalogStructure;
      }

      public List<CatalogStructureData> getCategoryCatalogStructures() {
          return mCategoryCatalogStructures;
      }

      public void setCategoryCatalogStructures(List<CatalogStructureData> pCategoryCatalogStructures) {
          this.mCategoryCatalogStructures = pCategoryCatalogStructures;
      }

      public void setCatalog(CatalogData pCatalog) {
          this.mCatalog = pCatalog;
      }

      public CatalogData getCatalog() {
          return mCatalog;
      }
  }
