/*
 * StorePortalForm.java
 *
 * Created on May 5, 2005, 5:02 PM
 */

package com.cleanwise.view.forms;

import java.util.HashMap;


/**
 *
 * @author Ykupershmidt
 */
public interface StorePortalForm {
    public static final Integer FORM_VAR_ALT_LOCATE = new Integer(1);
    public LocateStoreAccountForm getLocateStoreAccountForm();
    public void setLocateStoreAccountForm(LocateStoreAccountForm locateStoreAccountForm);
    public LocateStoreCatalogForm getLocateStoreCatalogForm();
    public void setLocateStoreCatalogForm(LocateStoreCatalogForm locateStoreCatalogForm);
    public LocateStoreDistForm getLocateStoreDistForm();
    public void setLocateStoreDistForm(LocateStoreDistForm locateStoreDistForm);
    public LocateStoreManufForm getLocateStoreManufForm();
    public void setLocateStoreManufForm(LocateStoreManufForm locateStoreManufForm);
    public LocateStoreItemForm getLocateStoreItemForm();
    public void setLocateStoreItemForm(LocateStoreItemForm locateStoreItemForm);
    public LocateCatalogItemForm getLocateCatalogItemForm();
    public void setLocateCatalogItemForm(LocateCatalogItemForm locateStoreItemForm);
    public LocateUploadItemForm getLocateUploadItemForm();
    public void setLocateUploadItemForm(LocateUploadItemForm locateUploadItemForm);
    public HashMap getFormVars();
    public void setFormVars(HashMap formVars) ;
    public LocateStoreUserForm getLocateStoreUserForm();
    public void setLocateStoreUserForm(LocateStoreUserForm pForm);
    public LocateStoreFhForm getLocateStoreFhForm();
    public void setLocateStoreFhForm(LocateStoreFhForm pForm);
    public LocateStoreServiceForm getLocateStoreServiceForm();
    public void setLocateStoreServiceForm(LocateStoreServiceForm pForm);
    public LocateStoreSiteForm getLocateStoreSiteForm();
    public void setLocateStoreSiteForm(LocateStoreSiteForm pForm);
    public LocateStoreAssetForm getLocateStoreAssetForm();
    public void setLocateStoreAssetForm(LocateStoreAssetForm pForm);    
    public LocateStoreOrderGuideForm getLocateStoreOrderGuideForm();
    public void setLocateStoreOrderGuideForm(LocateStoreOrderGuideForm pForm);
    public LocateReportAccountForm getLocateReportAccountForm();
    public void setLocateReportAccountForm(LocateReportAccountForm pForm);
    public LocateReportItemForm getLocateReportItemForm();
    public void setLocateReportItemForm(LocateReportItemForm pForm);
    public ReportingLocateStoreSiteForm getReportingLocateStoreSiteForm();
    public void setReportingLocateStoreSiteForm(ReportingLocateStoreSiteForm pForm);
    public String getReturnLocateTypeCd();
    public void setReturnLocateTypeCd(String pVal);
    public LocateStoreGroupForm getLocateStoreGroupForm();
    public void setLocateStoreGroupForm(LocateStoreGroupForm pForm);
    public LocateReportDistributorForm getLocateReportDistributorForm();
    public void setLocateReportDistributorForm(LocateReportDistributorForm pForm);
    public LocateReportCatalogForm getLocateReportCatalogForm();
    public void setLocateReportCatalogForm(LocateReportCatalogForm pForm);    
}
