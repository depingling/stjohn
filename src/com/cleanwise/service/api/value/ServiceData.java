package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.ValueObject;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * Date:         13.01.2007
 * Time:         14:01:32
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class ServiceData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 4102267444670131264L;

    public ItemData mItemData;
    public Hashtable mItemMeta;
    public BusEntityDataVector mDistrBusEntityDV = new BusEntityDataVector();
    public ItemMappingDataVector mDistrMappingDV = new ItemMappingDataVector();
    public CatalogCategoryDataVector mCatalogCategoryDV;
    private BusEntityData mCatalogDistributor;
    private CatalogStructureData _catalogStructureData;
    private int mCostCenterId = 0;
    private String mCostCenterName = "";


    public interface ITEM_META {
       public static final String LIST_PRICE ="LIST_PRICE" ;
    }

    public static ServiceData createValue() {
      return new ServiceData();
     }


    public ItemData getItemData() {
        return mItemData;
    }

    public void setItemData(ItemData itemD) {
        this.mItemData = itemD;
    }

    public Hashtable getItemMeta() {
        return mItemMeta;
    }

    public void setItemMeta(Hashtable itemMeta) {
        this.mItemMeta = itemMeta;
    }

    public BusEntityDataVector getDistrBusEntityDV() {
        return mDistrBusEntityDV;
    }

    public void setDistrBusEntityDV(BusEntityDataVector distrBusEntityDV) {
        this.mDistrBusEntityDV = distrBusEntityDV;
    }

    public ItemMappingDataVector getDistrMappingDV() {
        return mDistrMappingDV;
    }

    public void setDistrMappingDV(ItemMappingDataVector distrMappingDV) {
        this.mDistrMappingDV = distrMappingDV;
    }

    public CatalogCategoryDataVector getCatalogCategoryDV() {
        return mCatalogCategoryDV;
    }

    public void setCatalogCategoryDV(CatalogCategoryDataVector catalogCategoryDV) {
        this.mCatalogCategoryDV = catalogCategoryDV;
    }

    public void setCatalogDistributor(BusEntityData catalogDistributor) {
        this.mCatalogDistributor = catalogDistributor;
    }

    public BusEntityData getCatalogDistributor() {
        return mCatalogDistributor;
    }


    public void setCatalogStructureData(CatalogStructureData catalogStructureData) {
        this._catalogStructureData = catalogStructureData;
    }

    public CatalogStructureData getCatalogStructureData() {
        return _catalogStructureData;
    }


    public void setCostCenterId(int v) {
        mCostCenterId = v;
    }

    public int getCostCenterId() {

        return mCostCenterId;
    }

    /**
     * Get the value of CostCenterName.
     *
     * @return value of CostCenterName.
     */
    public String getCostCenterName() {
        return mCostCenterName;
    }

    /**
     * Set the value of CostCenterName.
     *
     * @param v Value to assign to CostCenterName.
     */
    public void setCostCenterName(String v) {
        this.mCostCenterName = v;
    }

}
