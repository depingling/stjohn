/**
 * Title: ReportingForm 
 * Description: This is the Struts ActionForm class handling the ESW reporting functionality.
 *
 */

package com.espendwise.view.forms.esw;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import com.cleanwise.service.api.dto.ReportingDto;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.forms.CustAcctMgtReportForm;

/**
 * Implementation of <code>ActionForm</code> that handles reporting functionality.
 */
public final class ReportingForm extends EswForm {

	private static final long serialVersionUID = 1L;
	
	private List<LabelValueBean> _fiscalPeriodFilterChoices;
	private List<LabelValueBean> _locationFilterChoices;
	private List<LabelValueBean> _dateRangeFieldChoices;
	private ReportingDto _ordersGlanceReportingInfo;
	private ReportingDto _budgetsGlanceReportingInfo;
	
	//Being: Standard (Misc) Reports
	private CustAcctMgtReportForm _customerReportingForm;
	private String _reportResultsSubTab;
	private long _reportEncodedId; 
	//End: Standard (Misc) Reports
	

	/**
	 * 
	 * @return dateRangeFieldChoices
	 */
	public List<LabelValueBean> getDateRangeFieldChoices() {
		return _dateRangeFieldChoices;
	}

	/**
	 * 
	 * @param dateRangeFieldChoices 
	 */
	public void setDateRangeFieldChoices(
			List<LabelValueBean> dateRangeFieldChoices) {
		_dateRangeFieldChoices = dateRangeFieldChoices;
	}
	
	/**
	 * @return the _fiscalPeriodFilterChoices
	 */
	public final List<LabelValueBean> getFiscalPeriodFilterChoices() {
		return _fiscalPeriodFilterChoices;
	}

	/**
	 * @param fiscalPeriodFilterChoices the _fiscalPeriodFilterChoices to set
	 */
	public final void setFiscalPeriodFilterChoices(
			List<LabelValueBean> fiscalPeriodFilterChoices) {
		_fiscalPeriodFilterChoices = fiscalPeriodFilterChoices;
	}

	
	/**
	 * @return the _locationFilterChoices
	 */
	public final List<LabelValueBean> getLocationFilterChoices() {
		return _locationFilterChoices;
	}

	/**
	 * @param locationFilterChoices the _locationFilterChoices to set
	 */
	public final void setLocationFilterChoices(
			List<LabelValueBean> locationFilterChoices) {
		_locationFilterChoices = locationFilterChoices;
	}

	/**
	 * @return the _ordersGlanceReportingInfo
	 */
	public final ReportingDto getOrdersGlanceReportingInfo() {
		if(_ordersGlanceReportingInfo==null) {
			_ordersGlanceReportingInfo = new ReportingDto();
		}
		return _ordersGlanceReportingInfo;
	}

	/**
	 * @param ordersGlanceReportingInfo the _ordersGlanceReportingInfo to set
	 */
	public final void setOrdersGlanceReportingInfo(
			ReportingDto ordersGlanceReportingInfo) {
		_ordersGlanceReportingInfo = ordersGlanceReportingInfo;
	}

	/**
	 * @return the _budgetsGlanceReportingInfo
	 */
	public final ReportingDto getBudgetsGlanceReportingInfo() {
		if(_budgetsGlanceReportingInfo==null) {
			_budgetsGlanceReportingInfo = new ReportingDto();
		}
		return _budgetsGlanceReportingInfo;
	}

	/**
	 * @param budgetsGlanceReportingInfo the _budgetsGlanceReportingInfo to set
	 */
	public final void setBudgetsGlanceReportingInfo(
			ReportingDto budgetsGlanceReportingInfo) {
		_budgetsGlanceReportingInfo = budgetsGlanceReportingInfo;
	}

	/**
	 * @return the customerReportingForm
	 */
	public CustAcctMgtReportForm getCustomerReportingForm() {
		if(_customerReportingForm==null) {
			_customerReportingForm = new CustAcctMgtReportForm();
		}
		return _customerReportingForm;
	}

	/**
	 * @param pCustomerReportingForm the customerReportingForm to set
	 */
	public void setCustomerReportingForm(
			CustAcctMgtReportForm pCustomerReportingForm) {
		_customerReportingForm = pCustomerReportingForm;
	}
	
	

	/**
	 * @return the reportResultsSubTab
	 */
	public String getReportResultsSubTab() {
		return _reportResultsSubTab;
	}

	/**
	 * @param pReportResultsSubTab the reportResultsSubTab to set
	 */
	public void setReportResultsSubTab(String pReportResultsSubTab) {
		_reportResultsSubTab = pReportResultsSubTab;
	}

	
	public long getReportEncodedId() {
		return _reportEncodedId;
	}

	public void setReportEncodedId(long pReportEncodedId) {
		_reportEncodedId = pReportEncodedId;
	}

	/**
     * Reset all properties to their default values.
     * @param  mapping  The mapping used to select this instance
     * @param  request  The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	super.reset(mapping, request);
    	
    	//Get needed forms from the session.
    	SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		if(sessionDataUtil.getCustomerReportingForm()==null) {
    		_customerReportingForm = getCustomerReportingForm();
    		sessionDataUtil.setCustomerReportingForm(_customerReportingForm);
    	} else {
    		setCustomerReportingForm(sessionDataUtil.getCustomerReportingForm());
    	}
		
    }

    /**
     * Validate the properties that have been set from this HTTP request, and
     * return an <code>ActionErrors</code> object that encapsulates any
     * validation errors that have been found. If no errors are found, return
     * <code>null</code> or an <code>ActionErrors</code> object with no
     * recorded error messages.
     *@param  mapping  The mapping used to select this instance
     *@param  request  The servlet request we are processing
     *@return          Description of the Returned Value
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    	//no validation is performed at the form level, so return null.
    	ActionErrors returnValue = null;
        return returnValue;
    }

}

