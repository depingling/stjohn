package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.*;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;
import java.util.Iterator;

/**
 * Title:        StoreServiceCatalogMgrForm
 * Description:  Form bean for the link management
 * Purpose:      Holds data
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         12.01.2007
 * Time:         23:11:45
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class StoreServiceCatalogMgrForm extends StorePortalBaseForm {

    private String[] mSelectedLines = new String[0];
    private String mAction = null;
    private String mLastLocateAction = null;
    private String mLastSortField = null;
    private boolean mNeedToSaveFl = false;

    private ServiceViewVector mServiceFilter;
    private CatalogDataVector mCatalogFilter;
    private DistributorDataVector mDistFilter;
    private AccountDataVector mAccountFilter;

    private ItemDataVector mCategories = new ItemDataVector();
    private String mStoreErpSystem = "";
    private CatalogCategoryDataVector mStoreCategories = new CatalogCategoryDataVector();

    private int mStoreCategoryId = 0;
    private String mDistName = "";
    private ServiceData currManagingService;
    private String categoryCostCenter;
    private ItemCatalogAggrViewVector mItemAggrVector = null;


    private boolean tickItemsToContract;
    private boolean tickItemsToCatalog;

    String mCategoryIdDummy = "";
    String mCostCenterIdDummy = "";
    DistributorData mDistIdDummy = null;
    String mCostDummy = "";
    String mPriceDummy = "";
    String mCatalogSkuNumDummy = "";
    String mDistSkuNumDummy = "";


    private boolean mShowDistSkuNumFl = false;
    private boolean mShowCustSkuNumFl = false;

     private boolean mDistSkuAutoFl = true;


    private boolean mShowDistSkuNumFlDef = false;
    private boolean mShowCustSkuNumFlDef = false;

    String mItemCpoId = "";
    String mCatalogCpoId = "";


    private boolean mAscSortOrderFl = true; //ascending/descending
    private boolean mControlMultipleCatalogFl=true;


    public String[] getSelectedLines() {
        return mSelectedLines;
    }

    public void setSelectedLines(String[] pVal) {
        mSelectedLines = pVal;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String pVal) {
        mAction = pVal;
    }

    public String getLastLocateAction() {
        return mLastLocateAction;
    }

    public void setLastLocateAction(String pVal) {
        mLastLocateAction = pVal;
    }

    public String getLastSortField() {
        return mLastSortField;
    }

    public void setLastSortField(String pVal) {
        mLastSortField = pVal;
    }

    public boolean getNeedToSaveFl() {
        return mNeedToSaveFl;
    }

    public void setNeedToSaveFl(boolean pVal) {
        mNeedToSaveFl = pVal;
    }

    public ServiceViewVector getServiceFilter() {
        return mServiceFilter;
    }

    public void setServiceFilter(ServiceViewVector pVal) {
        mServiceFilter = pVal;
    }

    public CatalogDataVector getCatalogFilter() {
        return mCatalogFilter;
    }

    public void setCatalogFilter(CatalogDataVector pVal) {
        mCatalogFilter = pVal;
    }

    public DistributorDataVector getDistFilter() {
        return mDistFilter;
    }

    public void setDistFilter(DistributorDataVector pVal) {
        mDistFilter = pVal;
    }

    public AccountDataVector getAccountFilter() {
        return mAccountFilter;
    }

    public void setAccountFilter(AccountDataVector pVal) {
        mAccountFilter = pVal;
    }

    public ItemDataVector getCategories() {
        return mCategories;
    }

    public void setCategories(ItemDataVector pVal) {
        mCategories = pVal;
    }

    public String getStoreErpSystem() {
        return mStoreErpSystem;
    }

    public void setStoreErpSystem(String pVal) {
        mStoreErpSystem = pVal;
    }

    public CatalogCategoryDataVector getStoreCategories() {
        return mStoreCategories;
    }

    public void setStoreCategories(CatalogCategoryDataVector pVal) {
        mStoreCategories = pVal;
    }

    public int getStoreCategoryId() {
        return mStoreCategoryId;
    }

    public void setStoreCategoryId(int pVal) {
        mStoreCategoryId = pVal;
    }

    public String getDistName() {
        return mDistName;
    }

    public void setDistName(String pVal) {
        mDistName = pVal;
    }

    public ServiceData getCurrManagingService() {
        return this.currManagingService;
    }

    public void setCurrManagingService(ServiceData currManagingService) {
        this.currManagingService = currManagingService;
    }

    public String getCategoryCostCenter() {
        return this.categoryCostCenter;
    }

    public void setCategoryCostCenter(String categoryCostCenter) {
        this.categoryCostCenter = categoryCostCenter;
    }

    public ItemCatalogAggrViewVector getItemAggrVector() {
        return mItemAggrVector;
    }

    public void setItemAggrVector(ItemCatalogAggrViewVector pVal) {
        mItemAggrVector = pVal;
    }

    //UiId
    //Select flag
    public void setSelectFl(int id, boolean pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return;
        icaVw.setSelectFl(pVal);
    }

    public boolean getSelectFl(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return false;
        return icaVw.getSelectFl();
    }

    public void setCategoryIdInp(int id, String pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return;
        icaVw.setCategoryIdInp(pVal);
    }

    public String getCategoryIdInp(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return "";
        return icaVw.getCategoryIdInp();
    }

    public void setCostCenterIdInp(int id, String pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return;
        icaVw.setCostCenterIdInp(pVal);
    }

    public String getCostCenterIdInp(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return "";
        return icaVw.getCostCenterIdInp();
    }

    public void setDistIdInp(int id, String pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return;
        icaVw.setDistIdInp(pVal);
    }

    public String getDistIdInp(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return "";
        return icaVw.getDistIdInp();
    }

    public void setCostInp(int id, String pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return;
        icaVw.setCostInp(pVal);
    }

    public String getCostInp(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return "";
        return icaVw.getCostInp();
    }

    public void setPriceInp(int id, String pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return;
        icaVw.setPriceInp(pVal);
    }

    public String getPriceInp(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return "";
        return icaVw.getPriceInp();
    }

    public void setBaseCostInp(int id, String pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return;
        icaVw.setBaseCostInp(pVal);
    }

    public String getBaseCostInp(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return "";
        return icaVw.getBaseCostInp();
    }

    public void setCatalogSkuNumInp(int id, String pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return;
        icaVw.setCatalogSkuNumInp(pVal);
    }

    public String getCatalogSkuNumInp(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return "";
        return icaVw.getCatalogSkuNumInp();
    }

    public void setDistSkuNumInp(int id, String pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return;
        icaVw.setDistSkuNumInp(pVal);
    }

    public String getDistSkuNumInp(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return "";
        return icaVw.getDistSkuNumInp();
    }

    public void setDistSkuPackInp(int id, String pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return;
        icaVw.setDistSkuPackInp(pVal);
    }

    public String getDistSkuPackInp(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return "";
        return icaVw.getDistSkuPackInp();
    }

    public void setDistSkuUomInp(int id, String pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return;
        icaVw.setDistSkuUomInp(pVal);
    }

    public String getDistSkuUomInp(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return "";
        return icaVw.getDistSkuUomInp();
    }

    public void setDistConversInp(int id, String pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return;
        icaVw.setDistConversInp(pVal);
    }

    public String getDistConversInp(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return "";
        return icaVw.getDistConversInp();
    }

    public void setDistSPLFlInp(int id, boolean pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return;
        icaVw.setDistSPLFlInp(pVal);
    }

    public boolean getDistSPLFlInp(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return false;
        return icaVw.getDistSPLFlInp();
    }


    public void setTaxExemptFlInp(int id, boolean pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return;
        icaVw.setTaxExemptFlInp(pVal);
    }

    public boolean getTaxExemptFlInp(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return false;
        return icaVw.getTaxExemptFlInp();
    }

    public void setGenManufIdInp(int id, String pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return;
        icaVw.setGenManufIdInp(pVal);
    }

    public String getGenManufIdInp(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return "";
        return icaVw.getGenManufIdInp();
    }

    public void setGenManufSkuNumInp(int id, String pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return;
        icaVw.setGenManufSkuNumInp(pVal);
    }

    public String getGenManufSkuNumInp(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return "";
        return icaVw.getGenManufSkuNumInp();
    }

    public void setCatalogFlInp(int id, boolean pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return;
        icaVw.setCatalogFlInp(pVal);
    }

    public boolean getCatalogFlInp(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return false;
        return icaVw.getCatalogFlInp();
    }

    public void setContractFlInp(int id, boolean pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return;
        icaVw.setContractFlInp(pVal);
    }

    public boolean getContractFlInp(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return false;
        return icaVw.getContractFlInp();
    }

    public void setOrderGuideFlInp(int id, boolean pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return;
        icaVw.setOrderGuideFlInp(pVal);
    }

    public boolean getOrderGuideFlInp(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return false;
        return icaVw.getOrderGuideFlInp();
    }

    private ItemCatalogAggrView getItemAggr(int id) {
        if (mItemAggrVector == null) return null;
        if (id < 0 || id > mItemAggrVector.size()) return null;
        return (ItemCatalogAggrView) mItemAggrVector.get(id);
    }

    public boolean isTickItemsToContract() {
        return this.tickItemsToContract;
    }

    public boolean getTickItemsToContract() {
        return this.tickItemsToContract;
    }

    public void setTickItemsToContract(boolean tickItemsToContract) {
        this.tickItemsToContract = tickItemsToContract;
    }

    public boolean isTickItemsToCatalog() {
        return this.tickItemsToCatalog;
    }

    public boolean getTickItemsToCatalog() {
        return this.tickItemsToCatalog;
    }

    public void setTickItemsToCatalog(boolean tickItemsToCatalog) {
        this.tickItemsToCatalog = tickItemsToCatalog;
    }


    public String getCategoryIdDummy() {
        return mCategoryIdDummy;
    }

    public void setCategoryIdDummy(String pVal) {
        mCategoryIdDummy = pVal;
    }

    public String getCostCenterIdDummy() {
        return mCostCenterIdDummy;
    }

    public void setCostCenterIdDummy(String pVal) {
        mCostCenterIdDummy = pVal;
    }

    public void setDistDummyConvert(DistributorDataVector dv) {

        if (dv != null && !dv.isEmpty()) {
            DistributorData d = (DistributorData) dv.get(0);
            setDistDummy(d);
        }
    }

    public DistributorData getDistDummy() {
        return mDistIdDummy;
    }

    public void setDistDummy(DistributorData pVal) {
        mDistIdDummy = pVal;
    }

    public String getCostDummy() {
        return mCostDummy;
    }

    public void setCostDummy(String pVal) {
        mCostDummy = pVal;
    }

    public String getPriceDummy() {
        return mPriceDummy;
    }

    public void setPriceDummy(String pVal) {
        mPriceDummy = pVal;
    }

    public String getCatalogSkuNumDummy() {
        return mCatalogSkuNumDummy;
    }

    public void setCatalogSkuNumDummy(String pVal) {
        mCatalogSkuNumDummy = pVal;
    }

    public String getDistSkuNumDummy() {
        return mDistSkuNumDummy;
    }

    public void setDistSkuNumDummy(String pVal) {
        mDistSkuNumDummy = pVal;
    }

    public boolean getShowDistSkuNumFl() {
        return mShowDistSkuNumFl;
    }

    public void setShowDistSkuNumFl(boolean pVal) {
        mShowDistSkuNumFl = pVal;
    }

    public boolean getShowCustSkuNumFl() {
        return mShowCustSkuNumFl;
    }

    public void setShowCustSkuNumFl(boolean pVal) {
        mShowCustSkuNumFl = pVal;
    }


    public boolean getShowDistSkuNumFlDef() {
        return mShowDistSkuNumFlDef;
    }

    public void setShowDistSkuNumFlDef(boolean pVal) {
        mShowDistSkuNumFlDef = pVal;
    }

    public boolean getShowCustSkuNumFlDef() {
        return mShowCustSkuNumFlDef;
    }

    public void setShowCustSkuNumFlDef(boolean pVal) {
        mShowCustSkuNumFlDef = pVal;
    }

    public boolean isDistSkuAutoFl() {
        return mDistSkuAutoFl;
    }

    public boolean getDistSkuAutoFl() {
        return mDistSkuAutoFl;
    }

    public void setDistSkuAutoFl(boolean DistSkuAutoFl) {
        mDistSkuAutoFl = DistSkuAutoFl;
    }

    public String getItemCpoId() {
        return mItemCpoId;
    }

    public void setItemCpoId(String pVal) {
        mItemCpoId = pVal;
    }

    public String getCatalogCpoId() {
        return mCatalogCpoId;
    }

    public void setCatalogCpoId(String pVal) {
        mCatalogCpoId = pVal;
    }

    public boolean getAscSortOrderFl() {
        return mAscSortOrderFl;
    }

    public void setAscSortOrderFl(boolean pVal) {
        mAscSortOrderFl = pVal;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        reset();
    }

    public void reset(ActionMapping mapping, ServletRequest request) {
        reset();
    }

    private void reset() {
        if (mItemAggrVector != null) {
            for (Iterator iter = mItemAggrVector.iterator(); iter.hasNext();) {
                ItemCatalogAggrView icVw = (ItemCatalogAggrView) iter.next();
                icVw.setSelectFl(false);
            }
        }
        mShowDistSkuNumFl = false;
        mShowCustSkuNumFl = false;
        tickItemsToCatalog = false;
        tickItemsToContract = false;
     }

    public boolean getControlMultipleCatalogFl() {
        return mControlMultipleCatalogFl;
    }

    public void setControlMultipleCatalogFl(boolean controlMultipleCatalogFl) {
        this.mControlMultipleCatalogFl = controlMultipleCatalogFl;
    }
}
