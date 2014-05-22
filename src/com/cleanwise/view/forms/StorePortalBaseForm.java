/*
 * StorePortalBaseForm.java
 *
 * Created on June 27, 2005, 4:33 PM
 */

package com.cleanwise.view.forms;

import java.util.HashMap;

/**
 *
 * @author bstevens
 */
public class StorePortalBaseForm extends Base2DetailForm implements StorePortalForm{
    /**
     * Holds value of property locateStoreAccountForm.
     */
    private LocateStoreAccountForm locateStoreAccountForm;

    /**
     * Holds value of property locateStoreCatalogForm.
     */
    private LocateStoreCatalogForm locateStoreCatalogForm;

    /**
     * Holds value of property locateStoreDistForm.
     */
    private LocateStoreDistForm locateStoreDistForm;

    /**
     * Holds value of property locateStoreItemForm.
     */
    private LocateStoreItemForm locateStoreItemForm;

    private LocateCatalogItemForm mLocateCatalogItemForm;
    private LocateUploadItemForm mLocateUploadItemForm;
    private LocateStoreManufForm locateStoreManufForm;
    private LocateStoreUserForm locateStoreUserForm;
    private LocateStoreFhForm locateStoreFhForm;
    private LocateStoreServiceForm locateStoreServiceForm;
    private LocateStoreAssetForm locateStoreAssetForm;
    private LocateStoreSiteForm locateStoreSiteForm;
    private LocateStoreOrderGuideForm _locateStoreOrderGuideForm;
    private LocateReportAccountForm _locateReportAccountForm;
    private LocateReportDistributorForm _locateReportDistributorForm;
    private LocateReportItemForm _locateReportItemForm;
    private ReportingLocateStoreSiteForm _reportingLocateStoreSiteForm;
    private LocateStoreGroupForm _locateStoreGroupForm;
    private LocateReportCatalogForm _locateReportCatalogForm;
    private String returnLocateTypeCd = null;

    /**
     * Holds value of property formVars.
     */
    private HashMap formVars;

    //--------------------- Methods
    
    /**
     * Getter for property locateStoreAccountForm.
     * @return Value of property locateStoreAccountForm.
     */
    public LocateStoreAccountForm getLocateStoreAccountForm() {

        return this.locateStoreAccountForm;
    }

    /**
     * Setter for property locateStoreAccountForm.
     * @param locateStoreAccountForm New value of property locateStoreAccountForm.
     */
    public void setLocateStoreAccountForm(LocateStoreAccountForm locateStoreAccountForm) {

        this.locateStoreAccountForm = locateStoreAccountForm;
    }

    /**
     * Getter for property locateStoreCatalogForm.
     * @return Value of property locateStoreCatalogForm.
     */
    public LocateStoreCatalogForm getLocateStoreCatalogForm() {

        return this.locateStoreCatalogForm;
    }

    /**
     * Setter for property locateStoreCatalogForm.
     * @param locateStoreCatalogForm New value of property locateStoreCatalogForm.
     */
    public void setLocateStoreCatalogForm(LocateStoreCatalogForm locateStoreCatalogForm) {

        this.locateStoreCatalogForm = locateStoreCatalogForm;
    }

    /**
     * Getter for property locateStoreDistForm.
     * @return Value of property locateStoreDistForm.
     */
    public LocateStoreDistForm getLocateStoreDistForm() {

        return this.locateStoreDistForm;
    }

    /**
     * Setter for property locateStoreDistForm.
     * @param locateStoreDistForm New value of property locateStoreDistForm.
     */
    public void setLocateStoreDistForm(LocateStoreDistForm locateStoreDistForm) {

        this.locateStoreDistForm = locateStoreDistForm;
    }

    /**
     * Getter for property locateStoreManufForm.
     * @return Value of property locateStoreManufForm.
     */
    public LocateStoreManufForm getLocateStoreManufForm() {

        return this.locateStoreManufForm;
    }

    /**
     * Setter for property locateStoreManufForm.
     * @param locateStoreManufForm New value of property locateStoreManufForm.
     */
    public void setLocateStoreManufForm(LocateStoreManufForm locateStoreManufForm) {

        this.locateStoreManufForm = locateStoreManufForm;
    }

    /**
     * Getter for property locateStoreItemForm.
     * @return Value of property locateStoreItemForm.
     */
    public LocateStoreItemForm getLocateStoreItemForm() {

        return this.locateStoreItemForm;
    }

    /**
     * Setter for property locateStoreItemForm.
     * @param locateStoreItemForm New value of property locateStoreItemForm.
     */
    public void setLocateStoreItemForm(LocateStoreItemForm locateStoreItemForm) {

        this.locateStoreItemForm = locateStoreItemForm;
    }

    public LocateCatalogItemForm getLocateCatalogItemForm() {return mLocateCatalogItemForm;}
    public void setLocateCatalogItemForm(LocateCatalogItemForm pVal) {mLocateCatalogItemForm = pVal;}

    public LocateUploadItemForm getLocateUploadItemForm() {return mLocateUploadItemForm;}
    public void setLocateUploadItemForm(LocateUploadItemForm pVal) {mLocateUploadItemForm = pVal;}

    public LocateStoreUserForm getLocateStoreUserForm() {return locateStoreUserForm;}
    public void setLocateStoreUserForm(LocateStoreUserForm locateStoreUserForm) {this.locateStoreUserForm = locateStoreUserForm;}

    public LocateStoreFhForm getLocateStoreFhForm() { return locateStoreFhForm; }
    public void setLocateStoreFhForm(LocateStoreFhForm locateStoreFhForm) { this.locateStoreFhForm = locateStoreFhForm; }

    public LocateStoreServiceForm getLocateStoreServiceForm() { return locateStoreServiceForm; }
    public void setLocateStoreServiceForm(LocateStoreServiceForm locateStoreServiceForm) { this.locateStoreServiceForm = locateStoreServiceForm; }

    public LocateStoreAssetForm getLocateStoreAssetForm() { return locateStoreAssetForm; }
    public void setLocateStoreAssetForm(LocateStoreAssetForm locateStoreAssetForm) { this.locateStoreAssetForm = locateStoreAssetForm; }

    public LocateStoreSiteForm getLocateStoreSiteForm() { return locateStoreSiteForm; }
    public void setLocateStoreSiteForm(LocateStoreSiteForm locateStoreSiteForm) {  this.locateStoreSiteForm = locateStoreSiteForm; }

    public LocateStoreOrderGuideForm getLocateStoreOrderGuideForm() { return _locateStoreOrderGuideForm; };
    public void setLocateStoreOrderGuideForm(LocateStoreOrderGuideForm locateStoreOrderGuideForm) { _locateStoreOrderGuideForm = locateStoreOrderGuideForm; };

    public LocateReportAccountForm getLocateReportAccountForm() { return _locateReportAccountForm; };
    public void setLocateReportAccountForm(LocateReportAccountForm locateReportAccountForm) { _locateReportAccountForm = locateReportAccountForm; };

    public LocateReportDistributorForm getLocateReportDistributorForm() { return _locateReportDistributorForm; };
    public void setLocateReportDistributorForm(LocateReportDistributorForm locateReportDistributorForm) { _locateReportDistributorForm = locateReportDistributorForm; };

    public LocateReportItemForm getLocateReportItemForm() { return _locateReportItemForm; };
    public void setLocateReportItemForm(LocateReportItemForm locateReportItemForm) { _locateReportItemForm = locateReportItemForm; };

    public ReportingLocateStoreSiteForm getReportingLocateStoreSiteForm() { return _reportingLocateStoreSiteForm; };
    public void setReportingLocateStoreSiteForm(ReportingLocateStoreSiteForm reportingLocateStoreSiteForm) { _reportingLocateStoreSiteForm = reportingLocateStoreSiteForm; };

    public LocateStoreGroupForm getLocateStoreGroupForm() {return _locateStoreGroupForm;};
    public void setLocateStoreGroupForm(LocateStoreGroupForm locateStoreGroupForm) {_locateStoreGroupForm = locateStoreGroupForm;};

    public LocateReportCatalogForm getLocateReportCatalogForm() {return _locateReportCatalogForm;}
	public void setLocateReportCatalogForm(LocateReportCatalogForm locateReportCatalogForm) {_locateReportCatalogForm = locateReportCatalogForm;}

    /**
     * Getter for property formVars.
     * @return Value of property formVars.
     */
    public HashMap getFormVars() {

        return this.formVars;
    }

    /**
     * Setter for property formVars.
     * @param formVars New value of property formVars.
     */
    public void setFormVars(HashMap formVars) {

        this.formVars = formVars;
    }

    public String getReturnLocateTypeCd() { return returnLocateTypeCd; }
    public void setReturnLocateTypeCd(String pVal) {returnLocateTypeCd = pVal;}


}
