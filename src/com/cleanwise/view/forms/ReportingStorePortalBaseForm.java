package com.cleanwise.view.forms;

import org.apache.struts.validator.ValidatorForm;

public class ReportingStorePortalBaseForm extends ValidatorForm {
    private ReportingLocateStoreSiteForm reportingLocateStoreSiteForm;

    public ReportingLocateStoreSiteForm getReportingLocateStoreSiteForm() {
        return reportingLocateStoreSiteForm;
    }

    public void setReportingLocateStoreSiteForm(
            ReportingLocateStoreSiteForm reportingLocateStoreSiteForm) {
        this.reportingLocateStoreSiteForm = reportingLocateStoreSiteForm;
    }
}
