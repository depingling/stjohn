/*
 * StorePortalImplForm.java
 *
 * Created on June 22, 2005, 5:23 PM
 */

package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.*;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author bstevens
 *
 */
public class LocateStoreBaseForm extends StorePortalBaseForm {

    LocateStoreAccountForm mLocateStoreAccountForm = null;
    LocateStoreCatalogForm mLocateStoreCatalogForm = null;
    LocateStoreItemForm mLocateStoreItemForm = null;
    LocateStoreDistForm mLocateStoreDistForm = null;
    LocateStoreManufForm mLocateStoreManufForm = null;
    LocateCatalogItemForm mLocateCatalogItemForm = null;
    LocateUploadItemForm mLocateUploadItemForm = null;
    LocateStoreUserForm mLocateStoreUserForm = null;
    LocateStoreFhForm mLocateStoreFhForm=null;
    LocateStoreServiceForm mLocateStoreServiceForm=null;
    LocateStoreAssetForm mLocateStoreAssetForm=null;
    LocateStoreSiteForm mLocateStoreSiteForm=null;
    LocateStoreOrderGuideForm mLocateStoreOrderGuideForm = null;
    LocateReportAccountForm mLocateReportAccountForm = null;
    LocateReportDistributorForm mLocateReportDistributorForm = null;
    LocateReportItemForm mLocateReportItemForm = null;
    ReportingLocateStoreSiteForm mReportingLocateStoreSiteForm = null;
    LocateStoreGroupForm mLocateStoreGroupForm = null;
    LocateReportCatalogForm mLocateReportCatalogForm = null;

    int mLevel = 0;
    boolean mLocateCatalogFl = false;
    boolean mLocateAccountFl = false;
    boolean mLocateItemFl = false;
    boolean mLocateDistFl = false;
    boolean mLocateFilterDistFl = false;
    boolean mLocateAssignDistFl = false;
    boolean mLocateManufFl = false;
    boolean mLocateFilterManufFl = false;
    boolean mLocateAssignManufFl = false;
    boolean mLocateCatalogItemFl = false;
    boolean mLocateUploadItemFl = false;
    boolean mLocateUserFl=false;
    boolean mLocateFhFl=false;
    boolean mLocateServiceFl=false;
    boolean mLocateAssetFl=false;
    boolean mLocateSiteFl=false;
    boolean mLocateOrderGuideFl = false;
    boolean mLocateReportAccountFl = false;
    boolean mLocateReportDistributorFl = false;
    boolean mLocateReportItemFl = false;
    boolean mReportingLocateStoreSiteFl = false;
    boolean mLocateStoreGroupFl = false;
    boolean mLocateReportCatalogFl = false;

    CatalogDataVector mCatalogFilter = null;
    AccountDataVector mAccountFilter = null;
    DistributorDataVector mDistFilter = null;
    ManufacturerDataVector mManufFilter = null;
    ItemViewVector mItemFilter = null;
    CatalogItemViewVector mCatalogItemFilter = null;
    UploadSkuViewVector mUploadItemFilter = null;
    UserDataVector mUserFilter=null;
    FreightHandlerViewVector mFhFilter=null;
    ServiceViewVector mServiceFilter=null;
    AssetViewVector mAssetFilter=null;
    SiteViewVector mSiteFilter=null;
    OrderGuideDescDataVector mOrderGuideFilter = null;
    AccountDataVector mReportAccountFilter = null;
    DistributorDataVector mReportDistributorFilter = null;
    GroupDataVector mStoreGroupFilter = null;

    /**
     * Holds value of property property.
     */
    private String property;

    /**
     * Holds value of property name.
     */
    private String name;

    /**
     * Holds value of property propertyAlt.
     */
    private String propertyAlt;

    public void setEmbeddedForm(Object pVal) {
        if(pVal instanceof LocateStoreAccountForm) {
            mLocateStoreAccountForm = (LocateStoreAccountForm) pVal;
        } else if (pVal instanceof LocateStoreCatalogForm){
            mLocateStoreCatalogForm = (LocateStoreCatalogForm) pVal;
        } else if (pVal instanceof LocateStoreItemForm){
            mLocateStoreItemForm = (LocateStoreItemForm) pVal;
        } else if (pVal instanceof LocateStoreDistForm){
            mLocateStoreDistForm = (LocateStoreDistForm) pVal;
        } else if (pVal instanceof LocateStoreManufForm){
            mLocateStoreManufForm = (LocateStoreManufForm) pVal;
        } else if (pVal instanceof LocateCatalogItemForm){
            mLocateCatalogItemForm = (LocateCatalogItemForm) pVal;
        } else if (pVal instanceof LocateUploadItemForm){
            mLocateUploadItemForm = (LocateUploadItemForm) pVal;
        } else if (pVal instanceof LocateStoreUserForm){
            mLocateStoreUserForm = (LocateStoreUserForm) pVal;
        } else if (pVal instanceof LocateStoreFhForm)
        {  mLocateStoreFhForm=(LocateStoreFhForm)pVal;
        } else if (pVal instanceof LocateStoreSiteForm)
        {  mLocateStoreSiteForm=(LocateStoreSiteForm)pVal;
        } else if (pVal instanceof LocateStoreServiceForm)
        {  mLocateStoreServiceForm=(LocateStoreServiceForm)pVal;
        } else if (pVal instanceof LocateStoreAssetForm)
        {  mLocateStoreAssetForm=(LocateStoreAssetForm)pVal;
        } else if (pVal instanceof LocateStoreOrderGuideForm) {  
            mLocateStoreOrderGuideForm = (LocateStoreOrderGuideForm)pVal;
        } else if (pVal instanceof LocateReportAccountForm) {  
            mLocateReportAccountForm = (LocateReportAccountForm)pVal;
        } else if (pVal instanceof LocateReportItemForm) {
            mLocateReportItemForm = (LocateReportItemForm)pVal;
        } else if (pVal instanceof ReportingLocateStoreSiteForm) {
            mReportingLocateStoreSiteForm = (ReportingLocateStoreSiteForm)pVal;
        } else if (pVal instanceof LocateStoreGroupForm) {
            mLocateStoreGroupForm = (LocateStoreGroupForm)pVal;
        } else if (pVal instanceof LocateReportDistributorForm) {
           mLocateReportDistributorForm = (LocateReportDistributorForm)pVal;
        } else if (pVal instanceof LocateReportCatalogForm) {
            mLocateReportCatalogForm = (LocateReportCatalogForm)pVal;
        }
    }

        //Embedded form
        public LocateStoreAccountForm getLocateStoreAccountForm() {return mLocateStoreAccountForm;}
        public void setLocateStoreAccountForm(LocateStoreAccountForm pVal) {mLocateStoreAccountForm = pVal;}

        public LocateStoreCatalogForm getLocateStoreCatalogForm() {return mLocateStoreCatalogForm;}
        public void setLocateStoreCatalogForm(LocateStoreCatalogForm pVal) {mLocateStoreCatalogForm = pVal;}

        public LocateStoreItemForm getLocateStoreItemForm() {return mLocateStoreItemForm;}
        public void setLocateStoreItemForm(LocateStoreItemForm pVal) {mLocateStoreItemForm = pVal;}

        public LocateStoreDistForm getLocateStoreDistForm() {return mLocateStoreDistForm;}
        public void setLocateStoreDistForm(LocateStoreDistForm pVal) {mLocateStoreDistForm = pVal;}

        public LocateStoreManufForm getLocateStoreManufForm() {return mLocateStoreManufForm;}
        public void setLocateStoreManufForm(LocateStoreManufForm pVal) {mLocateStoreManufForm = pVal;}

        public LocateCatalogItemForm getLocateCatalogItemForm() {return mLocateCatalogItemForm;}
        public void setLocateCatalogItemForm(LocateCatalogItemForm pVal) {mLocateCatalogItemForm = pVal;}

        public LocateUploadItemForm getLocateUploadItemForm() {return mLocateUploadItemForm;}
        public void setLocateUploadItemForm(LocateUploadItemForm pVal) {mLocateUploadItemForm = pVal;}

        public LocateStoreUserForm getLocateStoreUserForm() {return mLocateStoreUserForm;}
        public void setLocateStoreUserForm(LocateStoreUserForm mLocateStoreUserForm) {this.mLocateStoreUserForm = mLocateStoreUserForm;}

        public LocateStoreFhForm getLocateStoreFhForm() {   return mLocateStoreFhForm; }
        public void setLocateStoreFhForm(LocateStoreFhForm mLocateStoreFhForm) {  this.mLocateStoreFhForm = mLocateStoreFhForm;   }

        public LocateStoreServiceForm getLocateStoreServiceForm() {   return mLocateStoreServiceForm; }
        public void setLocateStoreServiceForm(LocateStoreServiceForm mLocateStoreServiceForm) {  this.mLocateStoreServiceForm = mLocateStoreServiceForm;   }

        public LocateStoreAssetForm getLocateStoreAssetForm() {   return mLocateStoreAssetForm; }
        public void setLocateStoreAssetForm(LocateStoreAssetForm mLocateStoreAssetForm) {  this.mLocateStoreAssetForm = mLocateStoreAssetForm;   }

        public LocateStoreSiteForm getLocateStoreSiteForm() { return mLocateStoreSiteForm; }
        public void setLocateStoreSiteForm(LocateStoreSiteForm mLocateStoreSiteForm) { this.mLocateStoreSiteForm = mLocateStoreSiteForm; }

        public LocateStoreOrderGuideForm getLocateStoreOrderGuideForm() { return mLocateStoreOrderGuideForm; }
        public void setLocateStoreOrderGuideForm(LocateStoreOrderGuideForm locateStoreOrderGuideForm) { mLocateStoreOrderGuideForm = locateStoreOrderGuideForm; }

        public LocateReportAccountForm getLocateReportAccountForm() { return mLocateReportAccountForm; }
        public void setLocateReportAccountForm(LocateReportAccountForm locateReportAccountForm) { mLocateReportAccountForm = locateReportAccountForm; }

        public LocateReportDistributorForm getLocateReportDistributorForm() { return mLocateReportDistributorForm; }
        public void setLocateReportDistributorForm(LocateReportDistributorForm locateReportDistributorForm) { mLocateReportDistributorForm = locateReportDistributorForm; }

        public LocateReportItemForm getLocateReportItemForm() { return mLocateReportItemForm; }
        public void setLocateReportItemForm(LocateReportItemForm locateReportItemForm) { mLocateReportItemForm = locateReportItemForm; }

        public ReportingLocateStoreSiteForm getReportingLocateStoreSiteForm() { return mReportingLocateStoreSiteForm; }
        public void setReportingLocateStoreSiteForm(ReportingLocateStoreSiteForm reportingLocateStoreSiteForm) { mReportingLocateStoreSiteForm = reportingLocateStoreSiteForm; }

        public LocateStoreGroupForm getLocateStoreGroupForm() { return mLocateStoreGroupForm; }
        public void setLocateStoreGroupForm(LocateStoreGroupForm locateStoreGroupForm) { mLocateStoreGroupForm = locateStoreGroupForm; }

        public LocateReportCatalogForm getLocateReportCatalogForm() { return mLocateReportCatalogForm; }
        public void setLocateReportCatalogForm(LocateReportCatalogForm locateReportCatalogForm) { mLocateReportCatalogForm = locateReportCatalogForm; }

       //-- Flags

        public boolean getLocateCatalogFl() {return mLocateCatalogFl;}
        public void setLocateCatalogFl(boolean pVal) {mLocateCatalogFl = pVal;}

        public boolean getLocateAccountFl() {return mLocateAccountFl;}
        public void setLocateAccountFl(boolean pVal) {mLocateAccountFl = pVal;}

        public boolean getLocateItemFl() {return mLocateItemFl;}
        public void setLocateItemFl(boolean pVal) {mLocateItemFl = pVal;}

        public boolean getLocateCatalogItemFl() {return mLocateCatalogItemFl;}
        public void setLocateCatalogItemFl(boolean pVal) {mLocateCatalogItemFl = pVal;}

        public boolean getLocateDistFl() {return mLocateDistFl;}
        public void setLocateDistFl(boolean pVal) {mLocateDistFl = pVal;}

        public boolean getLocateFilterDistFl() {return mLocateFilterDistFl;}
        public void setLocateFilterDistFl(boolean pVal) {mLocateFilterDistFl = pVal;}

        public boolean getLocateAssignDistFl() {return mLocateAssignDistFl;}
        public void setLocateAssignDistFl(boolean pVal) {mLocateAssignDistFl = pVal;}

        public boolean getLocateManufFl() {return mLocateManufFl;}
        public void setLocateManufFl(boolean pVal) {mLocateManufFl = pVal;}

        public boolean getLocateFilterManufFl() {return mLocateFilterManufFl;}
        public void setLocateFilterManufFl(boolean pVal) {mLocateFilterManufFl = pVal;}

        public boolean getLocateAssignManufFl() {return mLocateAssignManufFl;}
        public void setLocateAssignManufFl(boolean pVal) {mLocateAssignManufFl = pVal;}

        public boolean getLocateUploadItemFl() {return mLocateUploadItemFl;}
        public void setLocateUploadItemFl(boolean pVal) {mLocateUploadItemFl = pVal;}

        public boolean getLocateUserFl() {  return mLocateUserFl;}
        public void setLocateUserFl(boolean mLocateUserFl) {this.mLocateUserFl = mLocateUserFl;}

        public boolean getLocateFhFl() {return mLocateFhFl; }
        public void setLocateFhFl(boolean mLocateFhFl) { this.mLocateFhFl = mLocateFhFl; }

        public boolean getLocateServiceFl() {return mLocateServiceFl; }
        public void setLocateServiceFl(boolean mLocateServiceFl) { this.mLocateServiceFl = mLocateServiceFl; }

        public boolean getLocateAssetFl() {return mLocateAssetFl; }
        public void setLocateAssetFl(boolean mLocateAssetFl) { this.mLocateAssetFl = mLocateAssetFl; }

        public boolean getLocateSiteFl() { return mLocateSiteFl;  }
        public void setLocateSiteFl(boolean mLocateSiteF1) { this.mLocateSiteFl = mLocateSiteF1;  }

        public boolean getLocateOrderGuideFl() { return mLocateOrderGuideFl; }
        public void setLocateOrderGuideFl(boolean locateOrderGuideFl) { mLocateOrderGuideFl = locateOrderGuideFl; }

        public boolean getLocateReportAccountFl() { return mLocateReportAccountFl; }
        public void setLocateReportAccountFl(boolean locateReportAccountFl) { mLocateReportAccountFl = locateReportAccountFl; }

        public boolean getLocateReportDistributorFl() { return mLocateReportDistributorFl; }
        public void setLocateReportDistributorFl(boolean locateReportDistributorFl) { mLocateReportDistributorFl = locateReportDistributorFl; }

        public boolean getLocateReportItemFl() { return mLocateReportItemFl; }
        public void setLocateReportItemFl(boolean locateReportItemFl) { mLocateReportItemFl = locateReportItemFl; }

        public boolean getReportingLocateStoreSiteFl() { return mReportingLocateStoreSiteFl; }
        public void setReportingLocateStoreSiteFl(boolean reportingLocateStoreSiteFl) { mReportingLocateStoreSiteFl = reportingLocateStoreSiteFl; }

        public boolean getLocateStoreGroupFl() { return mLocateStoreGroupFl; }
        public void setLocateStoreGroupFl(boolean locateStoreGroupFl) { mLocateStoreGroupFl = locateStoreGroupFl; }
        
        public boolean getLocateReportCatalogFl() { return mLocateReportCatalogFl; }
        public void setLocateReportCatalogFl(boolean locateReportCatalogFl) { mLocateReportCatalogFl = locateReportCatalogFl; }

    //-- Filters

        public AccountDataVector getAccountFilter() {return mAccountFilter;}
        public void setAccountFilter(AccountDataVector pVal) {mAccountFilter = pVal;}

        public CatalogDataVector getCatalogFilter() {return mCatalogFilter;}
        public void setCatalogFilter(CatalogDataVector pVal) {mCatalogFilter = pVal;}

        public DistributorDataVector getDistFilter() {return mDistFilter;}
        public void setDistFilter(DistributorDataVector pVal) {mDistFilter = pVal;}

        public ManufacturerDataVector getManufFilter() {return mManufFilter;}
        public void setManufFilter(ManufacturerDataVector pVal) {mManufFilter = pVal;}

        public ItemViewVector getItemFilter() {return mItemFilter;}
        public void setItemFilter(ItemViewVector pVal) {mItemFilter = pVal;}

        public CatalogItemViewVector getCatalogItemFilter() {return mCatalogItemFilter;}
        public void setCatalogItemFilter(CatalogItemViewVector pVal) {mCatalogItemFilter = pVal;}

        public UploadSkuViewVector getUploadItemFilter() {return mUploadItemFilter;}
        public void setUploadItemFilter(UploadSkuViewVector pVal) {mUploadItemFilter = pVal;}

        public UserDataVector getUserFilter() { return mUserFilter;}
        public void setUserFilter(UserDataVector mUserFilter) { this.mUserFilter = mUserFilter; }

        public FreightHandlerViewVector getFhFilter() { return mFhFilter; }
        public void setFhFilter(FreightHandlerViewVector mFhFilter) { this.mFhFilter = mFhFilter;  }

        public ServiceViewVector getServiceFilter() { return mServiceFilter; }
        public void setServiceFilter(ServiceViewVector mServiceFilter) { this.mServiceFilter = mServiceFilter;  }

        public AssetViewVector getAssetFilter() { return mAssetFilter; }
        public void setAssetFilter(AssetViewVector mAssetFilter) { this.mAssetFilter = mAssetFilter;  }

        public SiteViewVector getSiteFilter() { return mSiteFilter; }
        public void setSiteFilter(SiteViewVector mSiteFilter) {this.mSiteFilter = mSiteFilter;}

        public OrderGuideDescDataVector getOrderGuideFilter() { return mOrderGuideFilter; }
        public void setOrderGuideFilter(OrderGuideDescDataVector orderGuideFilter) { mOrderGuideFilter = orderGuideFilter; }

        public AccountDataVector getReportAccountFilter() { return mReportAccountFilter; }
        public void setReportAccountFilter(AccountDataVector reportAccountFilter) { mReportAccountFilter = reportAccountFilter; }

        public DistributorDataVector getReportDistributorFilter() { return mReportDistributorFilter; }
        public void setReportDistributorFilter(DistributorDataVector reportDistributorFilter) { mReportDistributorFilter = reportDistributorFilter; }

        public GroupDataVector getStoreGroupFilter() { return mStoreGroupFilter; }
        public void setStoreGroupFilter(GroupDataVector storeGroupFilter) { mStoreGroupFilter = storeGroupFilter; }

    public int getLevel() {//return mLevel;
        return 1;
    }
        public void setLevel(int pVal) {mLevel = pVal;}

        /**
         * Getter for property property.
         * @return Value of property property.
         */
        public String getProperty() {

        return this.property;
    }

        /**
         * Setter for property property.
         * @param property New value of property property.
         */
        public void setProperty(String property) {

        this.property = property;
    }

        /**
         * Getter for property name.
         * @return Value of property name.
         */
        public String getName() {

        return this.name;
    }

        /**
         * Setter for property name.
         * @param name New value of property name.
         */
        public void setName(String name) {

        this.name = name;
    }

        /**
         * Getter for property propertyAlt.
         * @return Value of property propertyAlt.
         */
        public String getPropertyAlt() {

        return this.propertyAlt;
    }

        /**
         * Setter for property propertyAlt.
         * @param propertyAlt New value of property propertyAlt.
         */
        public void setPropertyAlt(String propertyAlt) {

        this.propertyAlt = propertyAlt;
    }
    }
