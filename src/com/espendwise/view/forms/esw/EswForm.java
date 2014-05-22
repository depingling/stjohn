/**
 * Title: EswForm 
 * Description: This is the Struts ActionForm class that is the superclass of all forms
 * 	handling the ESW functionality.
 */

package com.espendwise.view.forms.esw;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import com.cleanwise.service.api.dto.LocationBudgetChartDto;
import com.cleanwise.service.api.value.StoreMessageView;
import com.cleanwise.service.api.value.StoreMessageViewVector;

/**
 * Implementation of <code>ActionForm</code> that is the superclass of all forms
 * 	handling the ESW functionality.
 */
public abstract class EswForm extends ActionForm {

	private static final long serialVersionUID = -7593227990856445859L;
	private String _operation = "";
	private List<LabelValueBean> _productSearchFieldChoices;
	private LocationBudgetChartDto _locationBudgetChartDto;	
	private StoreMessageViewVector _messages;
	private StoreMessageView _currentMessage;
	private String _referer = "";
	
	/**
	 * @return the referer
	 */
	public final String getReferer() {
		return _referer;
	}

	/**
	 * @param referer the referer to set
	 */
	public final void setReferer(String referer) {
		_referer = referer;
	}


	/**
	 * @return the operation
	 */
	public String getOperation() {
		return _operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		_operation = operation;
	}
	
	/**
	 * @return the productSearchFieldChoices
	 */
	public List<LabelValueBean> getProductSearchFieldChoices() {
		return _productSearchFieldChoices;
	}

	/**
	 * @param productSearchFieldChoices the productSearchFieldChoices to set
	 */
	public void setProductSearchFieldChoices(
			List<LabelValueBean> productSearchFieldChoices) {
		_productSearchFieldChoices = productSearchFieldChoices;
	}

	/**
	 * @return the messages
	 */
	public StoreMessageViewVector getMessages() {
		if (_messages == null) {
			_messages = new StoreMessageViewVector();
		}
		return _messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(StoreMessageViewVector messages) {
		_messages = messages;
	}

	/**
	 * @return the currentMessage
	 */
	public StoreMessageView getCurrentMessage() {
		if (_currentMessage == null) {
			_currentMessage = new StoreMessageView();
		}
		return _currentMessage;
	}

	/**
	 * @param currentMessage the currentMessage to set
	 */
	public void setCurrentMessage(StoreMessageView currentMessage) {
		_currentMessage = currentMessage;
	}

	/**
     * Reset all properties to their default values.
     *@param  mapping  The mapping used to select this instance
     *@param  request  The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        _operation = "";
        _productSearchFieldChoices = null;
        _locationBudgetChartDto = null;
    	_messages = null;
    	_currentMessage = null;
    }

	/**
	 * @return the locationBudgetChartDto
	 */
    public LocationBudgetChartDto getLocationBudgetChartDto() {
    	if (_locationBudgetChartDto == null) {
    	_locationBudgetChartDto = new LocationBudgetChartDto();
    	}
    	return _locationBudgetChartDto;
    	}

	/**
	 * @param locationBudgetChartDto the locationBudgetChartDto to set
	 */
	public void setLocationBudgetChartDto(LocationBudgetChartDto locationBudgetChartDto) {
		_locationBudgetChartDto = locationBudgetChartDto;
	}
    
    /**
     * Validate the properties that have been set from this HTTP request, and
     * return an <code>ActionErrors</code> object that encapsulates any
     * validation errors that have been found. If no errors are found, return
     * <code>null</code> or an <code>ActionErrors</code> object with no
     * recorded error messages.
     * @param  mapping  The mapping used to select this instance
     * @param  request  The servlet request we are processing
     * @return          Description of the Returned Value
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    	//no validation is performed at the form level, so return null.
    	ActionErrors returnValue = null;
        return returnValue;
    }

}

