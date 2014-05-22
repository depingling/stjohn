/*
 * LocateUploadItemForm.java
 *
 * Created on October 13, 2005, 12:23 PM
 *
 */

package com.cleanwise.view.forms;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.SelectableObjects;
import org.apache.struts.upload.FormFile;
import com.cleanwise.service.api.value.*;
import java.util.List;
import java.util.Iterator;
import java.util.HashMap;


/**
 *
 * @author Ykupershmidt
 */
public class LocateUploadItemForm   extends LocateStoreBaseForm {
    private StorePortalForm mBaseForm = null;  

    public static final int SPL_FILTER_ANY = 0;
    public static final int SPL_FILTER_YES = 1;
    public static final int SPL_FILTER_NO = 2;
    private int mSplFilter = 0;

    private UploadDataVector mTableList = null;
    private int mUploadId = 0;
    private UploadData mUploadTable = null;
    
    private boolean mAddToOrderGuideFl = false;
    private boolean mUpdateCategoryFl = false;
    private boolean mUpdateDistSkuFl = false;
    private boolean mUpdateDistUomPackFl = false;
    private boolean mUpdateDistCostFl = false;
    private boolean mUpdateServiceFeeCodeFl = false;
    private boolean mUpdateCatalogPriceFl = false;
    private boolean mUpdateSplFl = false;

    private boolean mUpdateBaseCostFl = false;
    private boolean mUpdateGenManufNameFl = false;
    private boolean mUpdateGenManufSkuFl = false;
    private boolean mUpdateTaxExemptFl = false;
    private boolean mUpdateSpecialPermissionFl = false;
    private boolean mUpdateCustomerSkuFl = false;
    
    private int mUpdateInsert = 0;

    private boolean mRetAddToOrderGuideFl = false;
    private boolean mRetUpdateCategoryFl = false;
    private boolean mRetUpdateDistSkuFl = false;
    private boolean mRetUpdateDistUomPackFl = false;
    private boolean mRetUpdateDistCostFl = false;
    private boolean mRetUpdateServiceFeeCodeFl = false;
    private boolean mRetUpdateCatalogPriceFl = false;
    private boolean mRetUpdateSplFl = false;

    private boolean mRetUpdateBaseCostFl = false;
    private boolean mRetUpdateGenManufNameFl = false;
    private boolean mRetUpdateGenManufSkuFl = false;
    private boolean mRetUpdateTaxExemptFl = false;
    private boolean mRetUpdateSpecialPermissionFl = false;
    private boolean mRetUpdateCustomerSkuFl = false;
    
    private int mRetUpdateInsert = 0;
  
    public static final int UPDATE_ONLY = 0;
    public static final int INSERT_ONLY = 1;
    public static final int INSERT_UPDATE = 2;

    private int mDistProc = 0;
    public static final int DISTRIBUTOR_EXCEPTION = 0;
    public static final int DISTRIBUTOR_MATCH = 1;
    public static final int DISTRIBUTOR_IGNORE = 2;
    public static final int DISTRIBUTOR_ASSIGN = 3;

    private boolean mUseOnlyIgnoreDistributorMatchingFl = false;

    private boolean mCatalogPriceFl = false;
    private boolean mCategoryFl = false;
    private boolean mDistCostFl = false;
    private boolean mServiceFeeCodeFl = false;
    private boolean mDistNameFl = false;
    private boolean mDistPackFl = false;
    private boolean mDistSkuFl = false;
    private boolean mDistUomFl = false;
    private boolean mManufNameFl = false;
    private boolean mManufSkuFl = false;
    private boolean mMfcpFl = false;
    private boolean mNsnFl = false;
    private boolean mPsnFl = false;
    private boolean mShortDescFl = false;
    private boolean mSkuNumFl = false;
    private boolean mSkuPackFl = false;
    private boolean mSkuSizeFl = false;
    private boolean mSkuUomFl = false;
    private boolean mSplFl = false;
    private boolean mUnspscCodeFl = false;
    
    private boolean mBaseCostFl = false;
    private boolean mGenManufNameFl = false;
    private boolean mGenManufSkuFl = false;
    private boolean mDistUomMultFl = false;
    private boolean mTaxExemptFl = false;
    private boolean mSpecialPermissionFl = false;
   private boolean mCustomerSkuFl = false;
    
    private boolean mShowDistCostFl = false; //SVC
    
    private UploadSkuViewVector mUploadSkus = null;
    private UploadSkuViewVector mUploadSkusToReturn = null;
     
    public StorePortalForm getBaseForm() { return mBaseForm;}
    public void setBaseForm(StorePortalForm pBaseForm) {mBaseForm = pBaseForm;}

    public int getSplFilter() { return mSplFilter;}
    public void setSplFilter(int pSplFilter) {mSplFilter = pSplFilter;}

    public UploadDataVector getTableList() { return mTableList;}
    public void setTableList(UploadDataVector pTableList) {mTableList = pTableList;}

    public int getUploadId() { return mUploadId;}
    public void setUploadId(int pUploadId) {mUploadId = pUploadId;}

    public UploadData getUploadTable() { return mUploadTable;}
    public void setUploadTable(UploadData pUploadTable) {mUploadTable = pUploadTable;}

    public boolean getAddToOrderGuideFl() { return mAddToOrderGuideFl;}
    public void setAddToOrderGuideFl(boolean pAddToOrderGuideFl) {
      mAddToOrderGuideFl = pAddToOrderGuideFl;
      mRetAddToOrderGuideFl = pAddToOrderGuideFl;
    }
    public boolean getRetAddToOrderGuideFl() { return mRetAddToOrderGuideFl;}

    public boolean getUpdateCategoryFl() { return mUpdateCategoryFl;}
    public void setUpdateCategoryFl(boolean pUpdateCategoryFl) {
      mUpdateCategoryFl = pUpdateCategoryFl;
      mRetUpdateCategoryFl = pUpdateCategoryFl;
    }
    public boolean getRetUpdateCategoryFl() { return mRetUpdateCategoryFl;}

    public boolean getUpdateDistSkuFl() { return mUpdateDistSkuFl;}
    public void setUpdateDistSkuFl(boolean pUpdateDistSkuFl) {
      mUpdateDistSkuFl = pUpdateDistSkuFl;
      mRetUpdateDistSkuFl = pUpdateDistSkuFl;
    }
    public boolean getRetUpdateDistSkuFl() { return mRetUpdateDistSkuFl;}

    public boolean getUpdateDistUomPackFl() { return mUpdateDistUomPackFl;}
    public void setUpdateDistUomPackFl(boolean pUpdateDistUomPackFl) {
      mUpdateDistUomPackFl = pUpdateDistUomPackFl;
      mRetUpdateDistUomPackFl = pUpdateDistUomPackFl;
    }
    public boolean getRetUpdateDistUomPackFl() { return mRetUpdateDistUomPackFl;}

    public boolean getUpdateDistCostFl() { return mUpdateDistCostFl;}
    public void setUpdateDistCostFl(boolean pUpdateDistCostFl) {
      mUpdateDistCostFl = pUpdateDistCostFl;
      mRetUpdateDistCostFl = pUpdateDistCostFl;
    }
    public boolean getRetUpdateDistCostFl() { return mRetUpdateDistCostFl;}

    public boolean getUpdateServiceFeeCodeFl() { return mUpdateServiceFeeCodeFl;}
    public void setUpdateServiceFeeCodeFl(boolean pUpdateServiceFeeCodeFl) {
      mUpdateServiceFeeCodeFl = pUpdateServiceFeeCodeFl;
      mRetUpdateServiceFeeCodeFl = pUpdateServiceFeeCodeFl;
    }
    public boolean getRetUpdateServiceFeeCodeFl() { return mRetUpdateServiceFeeCodeFl;}
    
    public boolean getUpdateCatalogPriceFl() { return mUpdateCatalogPriceFl;}
    public void setUpdateCatalogPriceFl(boolean pUpdateCatalogPriceFl) {
      mUpdateCatalogPriceFl = pUpdateCatalogPriceFl;
      mRetUpdateCatalogPriceFl = pUpdateCatalogPriceFl;
    }
    public boolean getRetUpdateCatalogPriceFl() { return mRetUpdateCatalogPriceFl;}

    public boolean getUpdateSplFl() { return mUpdateSplFl;}
    public void setUpdateSplFl(boolean pUpdateSplFl) {
      mUpdateSplFl = pUpdateSplFl;
      mRetUpdateSplFl = pUpdateSplFl;
    }
    public boolean getRetUpdateSplFl() { return mRetUpdateSplFl;}

    public boolean getUpdateBaseCostFl() { return mUpdateBaseCostFl;}
    public void setUpdateBaseCostFl(boolean pUpdateBaseCostFl) {
      mUpdateBaseCostFl = pUpdateBaseCostFl;
      mRetUpdateBaseCostFl = pUpdateBaseCostFl;
    }
    public boolean getRetUpdateBaseCostFl() { return mRetUpdateBaseCostFl;}

    public boolean getUpdateGenManufNameFl() { return mUpdateGenManufNameFl;}
    public void setUpdateGenManufNameFl(boolean pUpdateGenManufNameFl) {
      mUpdateGenManufNameFl = pUpdateGenManufNameFl;
      mRetUpdateGenManufNameFl = pUpdateGenManufNameFl;
    }
    public boolean getRetUpdateGenManufNameFl() { return mRetUpdateGenManufNameFl;}

    public boolean getUpdateGenManufSkuFl() { return mUpdateGenManufSkuFl;}
    public void setUpdateGenManufSkuFl(boolean pUpdateGenManufSkuFl) {
      mUpdateGenManufSkuFl = pUpdateGenManufSkuFl;
      mRetUpdateGenManufSkuFl = pUpdateGenManufSkuFl;
    }
    public boolean getRetUpdateGenManufSkuFl() { return mRetUpdateGenManufSkuFl;}

    public boolean getUpdateTaxExemptFl() { return mUpdateTaxExemptFl;}
    public void setUpdateTaxExemptFl(boolean pUpdateTaxExemptFl) {
      mUpdateTaxExemptFl = pUpdateTaxExemptFl;
      mRetUpdateTaxExemptFl = pUpdateTaxExemptFl;
    }
    public boolean getRetUpdateTaxExemptFl() { return mRetUpdateTaxExemptFl;}


    public boolean getUpdateSpecialPermissionFl() { return mUpdateSpecialPermissionFl;  }
    public boolean getRetUpdateSpecialPermissionFl() { return mRetUpdateSpecialPermissionFl; }
    public void setUpdateSpecialPermissionFl(boolean pUpdateSpecialPermissionFl) {
        mUpdateSpecialPermissionFl = pUpdateSpecialPermissionFl;
        mRetUpdateSpecialPermissionFl = pUpdateSpecialPermissionFl;
    }

    public boolean getUpdateCustomerSkuFl() { return mUpdateCustomerSkuFl;}
    public void setUpdateCustomerSkuFl(boolean pUpdateCustomerSkuFl) {
        mUpdateCustomerSkuFl = pUpdateCustomerSkuFl;
        mRetUpdateCustomerSkuFl = pUpdateCustomerSkuFl;
    }
    public boolean getRetUpdateCustomerSkuFl() { return mRetUpdateCustomerSkuFl;}
    
    
    public int getUpdateInsert() { return mUpdateInsert;}
    public void setUpdateInsert(int pUpdateInsert) {mUpdateInsert = pUpdateInsert;}

    public int getDistProc() { return mDistProc;}
    public void setDistProc(int pDistProc) {mDistProc = pDistProc;}

    public UploadSkuViewVector getUploadSkus() { return mUploadSkus;}
    public void setUploadSkus(UploadSkuViewVector pUploadSkus) {mUploadSkus = pUploadSkus;}
    public boolean getSkuSelectFlag(int ii) {
      if(mUploadSkus==null || mUploadSkus.size()<ii) {
        return false;
      }
      UploadSkuView usVw = (UploadSkuView) mUploadSkus.get(ii);
      return usVw.getSelectFlag();
    }
    public void setSkuSelectFlag(int ii, boolean val) {
      if(mUploadSkus==null || mUploadSkus.size()<ii) {
        return;
      }
      UploadSkuView usVw = (UploadSkuView) mUploadSkus.get(ii);
      usVw.setSelectFlag(val);
      return;
    }

    public UploadSkuViewVector getUploadSkusToReturn() { return mUploadSkusToReturn;}
    public void setUploadSkusToReturn(UploadSkuViewVector pUploadSkusToReturn) 
                                  {mUploadSkusToReturn = pUploadSkusToReturn;}
    
    public boolean getCatalogPriceFl() { return mCatalogPriceFl;}
    public void setCatalogPriceFl(boolean pCatalogPriceFl) {mCatalogPriceFl = pCatalogPriceFl;}

    public boolean getCategoryFl() { return mCategoryFl;}
    public void setCategoryFl(boolean pCategoryFl) {mCategoryFl = pCategoryFl;}

    public boolean getDistCostFl() { return mDistCostFl;}
    public void setDistCostFl(boolean pDistCostFl) {mDistCostFl = pDistCostFl;}

    public boolean getServiceFeeCodeFl() { return mServiceFeeCodeFl;}
    public void setServiceFeeCodeFl(boolean pServiceFeeCodeFl) {mServiceFeeCodeFl = pServiceFeeCodeFl;}
    
    public boolean getDistNameFl() { return mDistNameFl;}
    public void setDistNameFl(boolean pDistNameFl) {mDistNameFl = pDistNameFl;}

    public boolean getDistPackFl() { return mDistPackFl;}
    public void setDistPackFl(boolean pDistPackFl) {mDistPackFl = pDistPackFl;}

    public boolean getDistSkuFl() { return mDistSkuFl;}
    public void setDistSkuFl(boolean pDistSkuFl) {mDistSkuFl = pDistSkuFl;}

    public boolean getDistUomFl() { return mDistUomFl;}
    public void setDistUomFl(boolean pDistUomFl) {mDistUomFl = pDistUomFl;}

    public boolean getManufNameFl() { return mManufNameFl;}
    public void setManufNameFl(boolean pManufNameFl) {mManufNameFl = pManufNameFl;}

    public boolean getManufSkuFl() { return mManufSkuFl;}
    public void setManufSkuFl(boolean pManufSkuFl) {mManufSkuFl = pManufSkuFl;}

    public boolean getMfcpFl() { return mMfcpFl;}
    public void setMfcpFl(boolean pMfcpFl) {mMfcpFl = pMfcpFl;}

    public boolean getNsnFl() { return mNsnFl;}
    public void setNsnFl(boolean pNsnFl) {mNsnFl = pNsnFl;}

    public boolean getPsnFl() { return mPsnFl;}
    public void setPsnFl(boolean pPsnFl) {mPsnFl = pPsnFl;}

    public boolean getShortDescFl() { return mShortDescFl;}
    public void setShortDescFl(boolean pShortDescFl) {mShortDescFl = pShortDescFl;}

    public boolean getSkuNumFl() { return mSkuNumFl;}
    public void setSkuNumFl(boolean pSkuNumFl) {mSkuNumFl = pSkuNumFl;}

    public boolean getSkuPackFl() { return mSkuPackFl;}
    public void setSkuPackFl(boolean pSkuPackFl) {mSkuPackFl = pSkuPackFl;}

    public boolean getSkuSizeFl() { return mSkuSizeFl;}
    public void setSkuSizeFl(boolean pSkuSizeFl) {mSkuSizeFl = pSkuSizeFl;}

    public boolean getSkuUomFl() { return mSkuUomFl;}
    public void setSkuUomFl(boolean pSkuUomFl) {mSkuUomFl = pSkuUomFl;}

    public boolean getSplFl() { return mSplFl;}
    public void setSplFl(boolean pSplFl) {mSplFl = pSplFl;}

    public boolean getUnspscCodeFl() { return mUnspscCodeFl;}
    public void setUnspscCodeFl(boolean pUnspscCodeFl) {mUnspscCodeFl = pUnspscCodeFl;}

    public boolean getBaseCostFl() { return mBaseCostFl;}
    public void setBaseCostFl(boolean pBaseCostFl) {mBaseCostFl = pBaseCostFl;}

    public boolean getGenManufNameFl() { return mGenManufNameFl;}
    public void setGenManufNameFl(boolean pGenManufNameFl) {mGenManufNameFl = pGenManufNameFl;}

    public boolean getGenManufSkuFl() { return mGenManufSkuFl;}
    public void setGenManufSkuFl(boolean pGenManufSkuFl) {mGenManufSkuFl = pGenManufSkuFl;}

    public boolean getDistUomMultFl() { return mDistUomMultFl;}
    public void setDistUomMultFl(boolean pDistUomMultFl) {mDistUomMultFl = pDistUomMultFl;}

    public boolean getTaxExemptFl() { return mTaxExemptFl;}
    public void setTaxExemptFl(boolean pTaxExemptFl) {mTaxExemptFl = pTaxExemptFl;}

    public boolean getSpecialPermissionFl() { return mSpecialPermissionFl;}
    public void setSpecialPermissionFl(boolean pSpecialPermissionFl) {mSpecialPermissionFl = pSpecialPermissionFl;}

    public boolean getCustomerSkuFl() { return mCustomerSkuFl;}
    public void setCustomerSkuFl(boolean pCustomerSkuFl) {mCustomerSkuFl = pCustomerSkuFl;}

    public boolean getUseOnlyIgnoreDistributorMatchingFl() {return mUseOnlyIgnoreDistributorMatchingFl;}
    public void setUseOnlyIgnoreDistributorMatchingFl(boolean flag) {mUseOnlyIgnoreDistributorMatchingFl = flag;}

    public boolean getShowDistCostFl() {return mShowDistCostFl;} //SVC
    public void setShowDistCostFl(boolean pVal) {mShowDistCostFl = pVal;} //SVC
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
      if(mUploadSkus!=null) {
        for(Iterator iter=mUploadSkus.iterator(); iter.hasNext();) {
          UploadSkuView usVw = (UploadSkuView) iter.next();
          usVw.setSelectFlag(false);
        }
      }
      mRetAddToOrderGuideFl = mAddToOrderGuideFl; 
      mAddToOrderGuideFl = false;
      mRetUpdateCategoryFl = mUpdateCategoryFl;
      mUpdateCategoryFl = false;
      mRetUpdateDistSkuFl = mUpdateDistSkuFl;
      mUpdateDistSkuFl = false;
      mRetUpdateDistUomPackFl = mUpdateDistUomPackFl;
      mUpdateDistUomPackFl = false;
      mRetUpdateDistCostFl = mUpdateDistCostFl;
      mUpdateDistCostFl = false;
      mRetUpdateServiceFeeCodeFl = mUpdateServiceFeeCodeFl;
      mUpdateServiceFeeCodeFl = false;
      mRetUpdateCatalogPriceFl = mUpdateCatalogPriceFl;
      mUpdateCatalogPriceFl = false;
      mRetUpdateSplFl = mUpdateSplFl;
      mUpdateSplFl = false;
      mRetUpdateBaseCostFl = mUpdateBaseCostFl;
      mUpdateBaseCostFl = false;
      mRetUpdateGenManufNameFl = mUpdateGenManufNameFl;
      mUpdateGenManufNameFl = false;
      mRetUpdateGenManufSkuFl = mUpdateGenManufSkuFl;
      mUpdateGenManufSkuFl = false;
      mRetUpdateTaxExemptFl = mUpdateTaxExemptFl;
      mUpdateTaxExemptFl = false;
      mRetUpdateSpecialPermissionFl = mUpdateSpecialPermissionFl;
      mUpdateSpecialPermissionFl = false;
      mRetUpdateCustomerSkuFl = mUpdateCustomerSkuFl;
      mUpdateCustomerSkuFl = false;
    }

}


 

