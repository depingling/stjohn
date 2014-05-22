/*
 * SessionAttributes.java
 *
 * Created on April 28, 2005, 11:07 AM
 */

package com.cleanwise.view.utils;

/**
 *
 * @author Ykupershmidt
 */
public interface SessionAttributes {
  public interface USER {
    public final String APP_USER = Constants.APP_USER; //CleanwiseUser
  }
  public interface SEARCH_FORM {
    public final String LOCATE_STORE_ACCOUNT_FORM = "LocateStoreAccountForm"; //LocateStoreAccountForm
    public final String LOCATE_STORE_DIST_FORM = "LocateStoreDistForm"; //LocateStoreDistForm
    public final String LOCATE_STORE_MANUF_FORM = "LocateStoreManufForm"; //LocateStoreManufForm
    public final String LOCATE_STORE_CATALOG_FORM = "LocateStoreCatalogForm"; //LocateStoreCatalogForm
    public final String LOCATE_STORE_ITEM_FORM = "LocateStoreItemForm"; //LocateStoreItemForm
    public final String LOCATE_UPLOAD_ITEM_FORM = "LocateUploadItemForm"; //LocateUploadItemForm
    public final String LOCATE_STORE_USER_FORM = "LocateStoreUserForm"; //LocateStoreUserForm  
    public final String LOCATE_STORE_FH_FORM="LocateStoreFhForm";//LocateStoreFhForm
    public final String LOCATE_STORE_SITE_FORM="LocateStoreSiteForm";//LocateStoreSiteForm
    public final String LOCATE_STORE_SERVICE_FORM="LocateStoreServiceForm";//LocateStoreServiceForm;
    public final String LOCATE_STORE_ASSET_FORM="LocateStoreAssetForm";//LocateStoreAssetForm;  
    public final String LOCATE_STORE_ORDER_GUIDE_FORM="LocateStoreOrderGuideForm";//LocateStoreOrderGuideForm
    public final String LOCATE_REPORT_ACCOUNT_FORM="LocateReportAccountForm";//LocateReportAccountForm
    public final String LOCATE_REPORT_DISTRIBUTOR_FORM="LocateReportDistributorForm";//LocateReportDistributorForm
    public final String LOCATE_REPORT_ITEM_FORM="LocateReportItemForm";//LocateReportItemForm
    public final String LOCATE_REPORT_SITE_FORM="ReportingLocateStoreSiteForm";//ReportingLocateStoreSiteForm
    public final String LOCATE_STORE_GROUP_FORM="LocateStoreGroupForm";//LocateStoreGroupForm
    public final String LOCATE_REPORT_CATALOG_FORM="LocateReportCatalogForm";//LocateReportCatalogForm
  }
  public interface SEARCH_RESULT {
    public final String STORE_ACCOUNTS = "StoreAccounts"; //AccountDataVector
    public final String REPORT_ACCOUNTS = "ReportAccounts"; //AccountDataVector
    public final String REPORT_ITEMS = "ReportItems"; //ItemViewVector
    public final String REPORT_DISTRIBUTORS = "ReportDistributors"; //DistributorDataVector
    public final String REPORT_CATALOGS = "ReportCatalogs"; //CatalogDataVector
  }

  public interface ITEM_MANAGER_PAGE {
    public final String CURRENT_PAGE = "item_manage_current_page"; //"cat-item", "itemcontract";
  }

    public interface ORDER_GUIDE {
        public final String ALL_ORDER_GUIDES = "allOrderGuides.vector";
    }
}
