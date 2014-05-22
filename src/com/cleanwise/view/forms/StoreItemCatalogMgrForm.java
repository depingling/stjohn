/*
 * StoreItemCatalogMgrForm.java
 *
 * Created on May 12, 2005
 */

package com.cleanwise.view.forms;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;
import java.util.Iterator;
import java.util.HashMap;

/**
 *
 * @author  YKupershmidt
 */
public class StoreItemCatalogMgrForm extends StorePortalBaseForm {
    

    private StoreData store;

    //private int[] mSelectedCatalogIds = new int[0];
    private String[] mSelectedLines = new String[0];
    private String mAction = null;
    private String mLastLocateAction = null;
    private String mLastSortField = null;
    private boolean mNeedToSaveFl = false;
    
    private ItemViewVector mItemFilter;
    private CatalogDataVector mCatalogFilter;
    private DistributorDataVector mDistFilter;
    private ManufacturerDataVector mManufFilter;
    private AccountDataVector mAccountFilter;
    
    private ItemDataVector mCategories = new ItemDataVector();
    private String mStoreErpSystem = "";
    private CatalogCategoryDataVector mStoreCategories = new CatalogCategoryDataVector();
    
    private BusEntityDataVector mStoreManufBusEntitys = new BusEntityDataVector();
    private MultiproductViewVector mStoreMultiproducts = new MultiproductViewVector();
   
    private int mStoreCategoryId = 0;
    //private String mDistIdS = "";
    private String mDistName = "";
    //private SelectableObjects distributors;
    //private SelectableObjects accounts;
    
    //private List distributorsNewlySelected;
    
    //private SelectableObjects aggregateItems;
    private ProductData currManagingItem;
    //private String categoryAction;    
    private String categoryCostCenter;
    private ItemCatalogAggrViewVector mItemAggrVector = null;
    
    
    private boolean tickItemsToContract;
    private boolean tickItemsToCatalog;
    private boolean tickItemsToOrderGuide;

    //private SelectableObjects catalogs;
    //private List catalogsNewlySelected;
    
    //private boolean usingCatalogSearch;
    //private SelectableObjects stores;
    //private List storesNewlySelected;
    String mCategoryIdDummy = "";
    String mCostCenterIdDummy = "";
    DistributorData mDistIdDummy = null;
    String mCostDummy = "";
    String mPriceDummy = "";
    String mBaseCostDummy = "";
    String mCatalogSkuNumDummy = "";
    String mDistSkuNumDummy = "";
    String mDistSkuPackDummy = "";
    String mDistSkuUomDummy = "";
    String mDistConversDummy = "";
    String mGenManufIdDummy = "";
    String mGenManufSkuNumDummy = "";
    
    String mMultiproductIdDummy = "";
    String mMultiproductNameDummy = "";
    
    private boolean mShowBaseCostFl = false;
    private boolean mShowMultiproductsFl = false;
    private boolean mShowDistSkuNumFl = false;
    private boolean mShowCustSkuNumFl = false;
    private boolean mShowServiceFeeCodeFl = false;
    private boolean mShowDistUomPackFl = false;
    private boolean mShowGenManufFl = false;
    private boolean mShowGenManufSkuNumFl = false;
    private boolean mShowDistSplFl = false;
    private boolean mShowTaxExemptFl = false;
    private boolean mShowSpecialPermissionFl = false;
    private boolean mDistSkuAutoFl = true;
    
    private boolean mShowBaseCostFlDef = false;
    private boolean mShowMultiproductsFlDef = false;
    private boolean mShowDistSkuNumFlDef = false;
    private boolean mShowCustSkuNumFlDef = false;
    private boolean mShowServiceFeeCodeFlDef = false;
    private boolean mShowDistUomPackFlDef = false;
    private boolean mShowGenManufFlDef = false;
    private boolean mShowGenManufSkuNumFlDef = false;
    private boolean mShowDistSplFlDef = false;
    private boolean mShowTaxExemptFlDef = false;
    private boolean mShowSpecialPermissionFlDef = false;
    
    String mItemCpoId = "";
    String mCatalogCpoId = "";
    String mOgCpoId = "";
    OrderGuideDescDataVector mOrderGuides = null;
    int mSelectedOrderGuideId = 0;
    private boolean mAscSortOrderFl = true; //ascending/descending
    private boolean mControlMultipleCatalogFl=false;
    
    private boolean mShowDistCostFl = true;
    private boolean allowMixedCategoryAndItemUnderSameParent;


    public String[] getSelectedLines() {return mSelectedLines;}
    public void setSelectedLines(String[] pVal) {mSelectedLines = pVal;}

    //public int[] getSelectedCatalogIds() {return mSelectedCatalogIds;}
    //public void setSelectedCatalogIds(int[] pVal) {mSelectedCatalogIds = pVal;}

    public String getAction() {return mAction;}
    public void setAction(String pVal) {mAction = pVal;}
 
    public String getLastLocateAction() {return mLastLocateAction;}
    public void setLastLocateAction(String pVal) {mLastLocateAction = pVal;}
 
    public String getLastSortField() {return mLastSortField;}
    public void setLastSortField(String pVal) {mLastSortField = pVal;}

    public boolean getNeedToSaveFl() {return mNeedToSaveFl;}
    public void setNeedToSaveFl(boolean pVal) {mNeedToSaveFl = pVal;}
    
    public ItemViewVector getItemFilter() {return mItemFilter;}
    public void setItemFilter(ItemViewVector pVal) {mItemFilter = pVal;}
    
    public CatalogDataVector getCatalogFilter() {return mCatalogFilter;}
    public void setCatalogFilter(CatalogDataVector pVal) {mCatalogFilter = pVal;}
    
    public DistributorDataVector getDistFilter() {return mDistFilter;}
    public void setDistFilter(DistributorDataVector pVal) {mDistFilter = pVal;}
    
    public ManufacturerDataVector getManufFilter() {return mManufFilter;}
    public void setManufFilter(ManufacturerDataVector pVal) {mManufFilter = pVal;}
    
    public AccountDataVector getAccountFilter() {return mAccountFilter;}
    public void setAccountFilter(AccountDataVector pVal) {mAccountFilter = pVal;}
    
    

    public ItemDataVector getCategories() {return mCategories;}
    public void setCategories(ItemDataVector pVal) {mCategories = pVal;}

    public String getStoreErpSystem() {return mStoreErpSystem;}
    public void setStoreErpSystem(String pVal) {mStoreErpSystem = pVal;}

    public CatalogCategoryDataVector getStoreCategories() {return mStoreCategories;}
    public void setStoreCategories(CatalogCategoryDataVector pVal) {mStoreCategories = pVal;}

    public  BusEntityDataVector getStoreManufBusEntitys() {return mStoreManufBusEntitys;}
    public void setStoreManufBusEntitys( BusEntityDataVector pVal) {mStoreManufBusEntitys = pVal;}

    public int getStoreCategoryId() {return mStoreCategoryId;}
    public void setStoreCategoryId(int pVal) {mStoreCategoryId = pVal;}

    //public String getDistIdS() {return mDistIdS;}
    //public void setDistIdS(String pVal) {mDistIdS = pVal;}
    
    public String getDistName() {return mDistName;}
    public void setDistName(String pVal) {mDistName = pVal;}

    public ProductData getCurrManagingItem() {
        return this.currManagingItem;
    }
    
    public void setCurrManagingItem(ProductData currManagingItem) {
        this.currManagingItem = currManagingItem;
    }
        
    public String getCategoryCostCenter() {
        return this.categoryCostCenter;
    }
    
    public void setCategoryCostCenter(String categoryCostCenter) {
        this.categoryCostCenter = categoryCostCenter;
    }
    
    public ItemCatalogAggrViewVector getItemAggrVector() {return mItemAggrVector;}
    public void setItemAggrVector(ItemCatalogAggrViewVector pVal) {mItemAggrVector = pVal;}

    
   //UiId
    //Select flag
    public void setSelectFl(int id, boolean pVal) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return;
      icaVw.setSelectFl(pVal);
    }
    public boolean getSelectFl(int id) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return false;
      return icaVw.getSelectFl();
    }

    public void setCategoryIdInp(int id, String pVal) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return;
      icaVw.setCategoryIdInp(pVal);
    }
    public String getCategoryIdInp(int id) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return "";
      return icaVw.getCategoryIdInp();
    }
   
    public void setCostCenterIdInp(int id, String pVal) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return;
      icaVw.setCostCenterIdInp(pVal);
    }
    public String getCostCenterIdInp(int id) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return "";
      return icaVw.getCostCenterIdInp();
    }
   
    public void setDistIdInp(int id, String pVal) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return;
      icaVw.setDistIdInp(pVal);
    }
    public String getDistIdInp(int id) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return "";
      return icaVw.getDistIdInp();
    }
   
    public void setCostInp(int id, String pVal) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return;
      icaVw.setCostInp(pVal);
    }
    public String getCostInp(int id) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return "";
      return icaVw.getCostInp();
    }
   
    public void setPriceInp(int id, String pVal) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return;
      icaVw.setPriceInp(pVal);
    }
    public String getPriceInp(int id) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return "";
      return icaVw.getPriceInp();
    }
   
    public void setBaseCostInp(int id, String pVal) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return;
      icaVw.setBaseCostInp(pVal);
    }
    public String getBaseCostInp(int id) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return "";
      return icaVw.getBaseCostInp();
    }
   
    public void setCatalogSkuNumInp(int id, String pVal) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return;
      icaVw.setCatalogSkuNumInp(pVal);
    }
    public String getCatalogSkuNumInp(int id) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return "";
      return icaVw.getCatalogSkuNumInp();
    }
   
    public void setServiceFeeCodeInp(int id, String pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return;
        icaVw.setServiceFeeCodeInp(pVal);
    }

    public String getServiceFeeCodeInp(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if (icaVw == null) return "";
        return icaVw.getServiceFeeCodeInp();
    }
    
    public void setDistSkuNumInp(int id, String pVal) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return;
      icaVw.setDistSkuNumInp(pVal);
    }
    public String getDistSkuNumInp(int id) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return "";
      return icaVw.getDistSkuNumInp();
    }
   
    public void setDistSkuPackInp(int id, String pVal) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return;
      icaVw.setDistSkuPackInp(pVal);
    }
    public String getDistSkuPackInp(int id) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return "";
      return icaVw.getDistSkuPackInp();
    }
   
    public void setDistSkuUomInp(int id, String pVal) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return;
      icaVw.setDistSkuUomInp(pVal);
    }
    public String getDistSkuUomInp(int id) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return "";
      return icaVw.getDistSkuUomInp();
    }
   
    public void setDistConversInp(int id, String pVal) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return;
      icaVw.setDistConversInp(pVal);
    }
    public String getDistConversInp(int id) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return "";
      return icaVw.getDistConversInp();
    }
   
    public void setDistSPLFlInp(int id, boolean pVal) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return;
      icaVw.setDistSPLFlInp(pVal);
    }
    public boolean getDistSPLFlInp(int id) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return false;
      return icaVw.getDistSPLFlInp();
    }
    
    public void setTaxExemptFlInp(int id, boolean pVal) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return;
      icaVw.setTaxExemptFlInp(pVal);
    }

    public boolean getTaxExemptFlInp(int id) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return false;
      return icaVw.getTaxExemptFlInp();
    }

    public void setSpecialPermissionFlInp(int id, boolean pVal) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return;
      icaVw.setSpecialPermissionFlInp(pVal);
    }

    public boolean getSpecialPermissionFlInp(int id) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return false;
      return icaVw.getSpecialPermissionFlInp();
    }

    public void setGenManufIdInp(int id, String pVal) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return;
      icaVw.setGenManufIdInp(pVal);
    }
    public String getGenManufIdInp(int id) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return "";
      return icaVw.getGenManufIdInp();
    }

    public void setMultiproductIdInp(int id, String pVal) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if(icaVw==null) return;
        icaVw.setMultiproductIdInp(pVal);
    }
    
    public String getMultiproductIdInp(int id) {
        ItemCatalogAggrView icaVw = getItemAggr(id);
        if(icaVw==null) return "";
        return icaVw.getMultiproductIdInp();
    }    
    
    public void setGenManufSkuNumInp(int id, String pVal) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return;
      icaVw.setGenManufSkuNumInp(pVal);
    }
    public String getGenManufSkuNumInp(int id) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return "";
      return icaVw.getGenManufSkuNumInp();
    }
   
    public void setCatalogFlInp(int id, boolean pVal) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return;
      icaVw.setCatalogFlInp(pVal);
    }
    public boolean getCatalogFlInp(int id) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return false;
      return icaVw.getCatalogFlInp();
    }
   
    public void setContractFlInp(int id, boolean pVal) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return;
      icaVw.setContractFlInp(pVal);
    }
    public boolean getContractFlInp(int id) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return false;
      return icaVw.getContractFlInp();
    }

    public void setOrderGuideFlInp(int id, boolean pVal) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return;
      icaVw.setOrderGuideFlInp(pVal);
    }
    public boolean getOrderGuideFlInp(int id) {
      ItemCatalogAggrView icaVw = getItemAggr(id);
      if(icaVw==null) return false;
      return icaVw.getOrderGuideFlInp();
    }
    
    private ItemCatalogAggrView getItemAggr(int id) {
      if(mItemAggrVector==null) return null;
      if(id<0 || id>mItemAggrVector.size()) return null;
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
    
    public boolean isTickItemsToOrderGuide() {
        return this.tickItemsToOrderGuide;
    }
    public boolean getTickItemsToOrderGuide() {
        return this.tickItemsToOrderGuide;
    }
    
    public void setTickItemsToOrderGuide(boolean tickItemsToOrderGuide) {
        this.tickItemsToOrderGuide = tickItemsToOrderGuide;
    }
    
    public String getCategoryIdDummy() {return mCategoryIdDummy;}
    public void setCategoryIdDummy(String pVal) {mCategoryIdDummy = pVal;}

    public String getCostCenterIdDummy() {return mCostCenterIdDummy;}
    public void setCostCenterIdDummy(String pVal) {mCostCenterIdDummy = pVal;}

    public void setDistDummyConvert(DistributorDataVector dv){
        
        if(dv != null && !dv.isEmpty()){
            DistributorData d = (DistributorData) dv.get(0);
            setDistDummy(d);
        }
    }
    
    public DistributorData getDistDummy() {return mDistIdDummy;}
    public void setDistDummy(DistributorData pVal) {mDistIdDummy = pVal;}

    public String getCostDummy() {return mCostDummy;}
    public void setCostDummy(String pVal) {mCostDummy = pVal;}

    public String getPriceDummy() {return mPriceDummy;}
    public void setPriceDummy(String pVal) {mPriceDummy = pVal;}

    public String getBaseCostDummy() {return mBaseCostDummy;}
    public void setBaseCostDummy(String pVal) {mBaseCostDummy = pVal;}

    public String getCatalogSkuNumDummy() {return mCatalogSkuNumDummy;}
    public void setCatalogSkuNumDummy(String pVal) {mCatalogSkuNumDummy = pVal;}

    public String getDistSkuNumDummy() {return mDistSkuNumDummy;}
    public void setDistSkuNumDummy(String pVal) {mDistSkuNumDummy = pVal;}

    public String getDistSkuPackDummy() {return mDistSkuPackDummy;}
    public void setDistSkuPackDummy(String pVal) {mDistSkuPackDummy = pVal;}

    public String getDistSkuUomDummy() {return mDistSkuUomDummy;}
    public void setDistSkuUomDummy(String pVal) {mDistSkuUomDummy = pVal;}

    public String getDistConversDummy() {return mDistConversDummy;}
    public void setDistConversDummy(String pVal) {mDistConversDummy = pVal;}

    public String getGenManufIdDummy() {return mGenManufIdDummy;}
    public void setGenManufIdDummy(String pVal) {mGenManufIdDummy = pVal;}

    public String getGenManufSkuNumDummy() {return mGenManufSkuNumDummy;}
    public void setGenManufSkuNumDummy(String pVal) {mGenManufSkuNumDummy = pVal;}

    public boolean getShowBaseCostFl() {return mShowBaseCostFl;}
    public void setShowBaseCostFl(boolean pVal) {mShowBaseCostFl = pVal;}

    public boolean getShowDistSkuNumFl() {return mShowDistSkuNumFl;}
    public void setShowDistSkuNumFl(boolean pVal) {mShowDistSkuNumFl = pVal;}

    public boolean getShowCustSkuNumFl() {return mShowCustSkuNumFl;}
    public void setShowCustSkuNumFl(boolean pVal) {mShowCustSkuNumFl = pVal;}

    public boolean getShowServiceFeeCodeFl() {return mShowServiceFeeCodeFl;}
    public void setShowServiceFeeCodeFl(boolean pVal) {mShowServiceFeeCodeFl = pVal;}
    
    public boolean getShowDistUomPackFl() {return mShowDistUomPackFl;}
    public void setShowDistUomPackFl(boolean pVal) {mShowDistUomPackFl = pVal;}
    
    public boolean getShowGenManufFl() {return mShowGenManufFl;}
    public void setShowGenManufFl(boolean pVal) {mShowGenManufFl = pVal;}

    public boolean getShowGenManufSkuNumFl() {return mShowGenManufSkuNumFl;}
    public void setShowGenManufSkuNumFl(boolean pVal) {mShowGenManufSkuNumFl = pVal;}

    public boolean getShowBaseCostFlDef() {return mShowBaseCostFlDef;}
    public void setShowBaseCostFlDef(boolean pVal) {mShowBaseCostFlDef = pVal;}

    public boolean getShowDistSkuNumFlDef() {return mShowDistSkuNumFlDef;}
    public void setShowDistSkuNumFlDef(boolean pVal) {mShowDistSkuNumFlDef = pVal;}

    public boolean getShowCustSkuNumFlDef() {return mShowCustSkuNumFlDef;}
    public void setShowCustSkuNumFlDef(boolean pVal) {mShowCustSkuNumFlDef = pVal;}

    public boolean getShowServiceFeeCodeFlDef() {return mShowServiceFeeCodeFlDef;}
    public void setShowServiceFeeCodeFlDef(boolean pVal) {mShowServiceFeeCodeFlDef = pVal;}
    
    public boolean getShowDistUomPackFlDef() {return mShowDistUomPackFlDef;}
    public void setShowDistUomPackFlDef(boolean pVal) {mShowDistUomPackFlDef = pVal;}

    public boolean getShowGenManufFlDef() {return mShowGenManufFlDef;}
    public void setShowGenManufFlDef(boolean pVal) {mShowGenManufFlDef = pVal;}

    public boolean getShowGenManufSkuNumFlDef() {return mShowGenManufSkuNumFlDef;}
    public void setShowGenManufSkuNumFlDef(boolean pVal) {mShowGenManufSkuNumFlDef = pVal;}

    public boolean isShowDistSplFl() {return mShowDistSplFl;}
    public boolean getShowDistSplFl() {return mShowDistSplFl;}
    public void setShowDistSplFl(boolean showDistSplFl) {mShowDistSplFl = showDistSplFl; }

    public boolean isShowDistSplFlDef() {return mShowDistSplFlDef;}
    public boolean getShowDistSplFlDef() {return mShowDistSplFlDef;}
    public void setShowDistSplFlDef(boolean showDistSplFlDef) {mShowDistSplFlDef = showDistSplFlDef;}

    public boolean isShowTaxExemptFl() {return mShowTaxExemptFl;}
    public boolean getShowTaxExemptFl() {return mShowTaxExemptFl;}
    public void setShowTaxExemptFl(boolean showTaxExemptFl) {mShowTaxExemptFl = showTaxExemptFl; }

    public boolean isShowTaxExemptFlDef() {return mShowTaxExemptFlDef;}
    public boolean getShowTaxExemptFlDef() {return mShowTaxExemptFlDef;}
    public void setShowTaxExemptFlDef(boolean showTaxExemptFlDef) {mShowTaxExemptFlDef = showTaxExemptFlDef;}

    public boolean isShowSpecialPermissionFl() {return mShowSpecialPermissionFl;}
    public boolean getShowSpecialPermissionFl() {return mShowSpecialPermissionFl;}
    public void setShowSpecialPermissionFl(boolean showSpecialPermissionFl) {mShowSpecialPermissionFl = showSpecialPermissionFl; }

    public boolean isShowSpecialPermissionFlDef() {return mShowSpecialPermissionFlDef;}
    public boolean getShowSpecialPermissionFlDef() {return mShowSpecialPermissionFlDef;}
    public void setShowSpecialPermissionFlDef(boolean showSpecialPermissionFlDef) {mShowSpecialPermissionFlDef = showSpecialPermissionFlDef;}


    public boolean isDistSkuAutoFl() {return mDistSkuAutoFl;}
    public boolean getDistSkuAutoFl() {return mDistSkuAutoFl;}
    public void setDistSkuAutoFl(boolean DistSkuAutoFl) {mDistSkuAutoFl = DistSkuAutoFl; }
            
    public String getItemCpoId() {return mItemCpoId;}
    public void setItemCpoId(String pVal) {mItemCpoId = pVal;}

    public String getCatalogCpoId() {return mCatalogCpoId;}
    public void setCatalogCpoId(String pVal) {mCatalogCpoId = pVal;}
    
    public String getOgCpoId() {return mOgCpoId;}
    public void setOgCpoId(String pVal) {mOgCpoId = pVal;}
    
    public OrderGuideDescDataVector getOrderGuides() {return mOrderGuides;}
    public void setOrderGuides(OrderGuideDescDataVector pVal) {mOrderGuides = pVal;}

    public int getSelectedOrderGuideId() {return mSelectedOrderGuideId;}
    public void setSelectedOrderGuideId(int pVal) {mSelectedOrderGuideId = pVal;}

    public boolean getAscSortOrderFl() {return mAscSortOrderFl;}
    public void setAscSortOrderFl(boolean pVal) {mAscSortOrderFl = pVal;}
    
    public boolean getShowDistCostFl() {return mShowDistCostFl;} 
    public void setShowDistCostFl(boolean pVal) {mShowDistCostFl = pVal;} 
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        reset();
    }
    public void reset(ActionMapping mapping, ServletRequest request) {
       reset();
    }
    private void reset(){
      if(mItemAggrVector!=null) {
        for(Iterator iter=mItemAggrVector.iterator(); iter.hasNext();) {
          ItemCatalogAggrView icVw = (ItemCatalogAggrView) iter.next();
          icVw.setSelectFl(false);
          //if(isShowDistSplFl()){
          //  icVw.setDistSPLFlInp(false);
          //}
          //if(isShowTaxExemptFl()){
          //  icVw.setTaxExemptFlInp(false);
          //}          
        }
      }
      mShowBaseCostFl = false;
      mShowDistSkuNumFl = false;
      mShowCustSkuNumFl = false;
      mShowServiceFeeCodeFl = false;
      mShowDistUomPackFl = false;
      mShowGenManufFl = false;
      mShowGenManufSkuNumFl = false;
      mShowDistSplFl = false;
      mShowTaxExemptFl = false;
      mShowSpecialPermissionFl =false;
      
        tickItemsToCatalog = false;
        tickItemsToContract = false;
        tickItemsToOrderGuide = false;
    }


    public boolean getControlMultipleCatalogFl() {
        return mControlMultipleCatalogFl;
    }
    public void setControlMultipleCatalogFl(boolean flag) {
       this.mControlMultipleCatalogFl=flag;
    }

    public String getMultiproductIdDummy() {
        return mMultiproductIdDummy;
    }

    public String getMultiproductNameDummy() {
        return mMultiproductNameDummy;
    }
    
    public void setMultiproductIdDummy(String multiproductIdDummy) {
        mMultiproductIdDummy = multiproductIdDummy;
    }
    
    public void setMultiproductNameDummy(String multiproductNameDummy) {
        mMultiproductNameDummy = multiproductNameDummy;
    }
    
    /**
     * @return Returns the mStoreMultiproducts.
     */
    public MultiproductViewVector getStoreMultiproducts() {
        return mStoreMultiproducts;
    }
    /**
     * @param storeMultiproducts The mStoreMultiproducts to set.
     */
    public void setStoreMultiproducts(MultiproductViewVector storeMultiproducts) {
        mStoreMultiproducts = storeMultiproducts;
    }
    /**
     * @return Returns the mShowMultiproductsFl.
     */

    public boolean getShowMultiproductsFl() {
        return mShowMultiproductsFl;
    } 
    
    /**
     * @param showMultiproductsFl The mShowMultiproductsFl to set.
     */
    public void setShowMultiproductsFl(boolean showMultiproductsFl) {
        mShowMultiproductsFl = showMultiproductsFl;
    }
    /**
     * @return Returns the mShowMultiproductsFlDef.
     */
    public boolean getShowMultiproductsFlDef() {
        return mShowMultiproductsFlDef;
    }
    /**
     * @param showMultiproductsFlDef The mShowMultiproductsFlDef to set.
     */
    public void setShowMultiproductsFlDef(boolean showMultiproductsFlDef) {
        mShowMultiproductsFlDef = showMultiproductsFlDef;
    }

    public StoreData getStore() {
        return store;
    }

    public void setStore(StoreData pStore) {
        this.store = pStore;
    }
    
    public void setAllowMixedCategoryAndItemUnderSameParent(boolean pAllowMixedCategoryAndItemUnderSameParent) {
        this.allowMixedCategoryAndItemUnderSameParent = pAllowMixedCategoryAndItemUnderSameParent;
    }

    public boolean getAllowMixedCategoryAndItemUnderSameParent() {
        return allowMixedCategoryAndItemUnderSameParent;
    }
}


