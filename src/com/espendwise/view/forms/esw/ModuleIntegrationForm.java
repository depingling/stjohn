/**
 * Title: ModuleIntegrationForm 
 * Description: This is the Struts ActionForm class handling integration between the various
 * eSpendwise modules (St. John, Orca, etc).
 *
 */

package com.espendwise.view.forms.esw;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * Implementation of <code>ActionForm</code> that handles eSpendwise module integration functionality.
 */
public final class ModuleIntegrationForm extends EswForm {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5875755674039294878L;

	/**
     * Reset all properties to their default values.
     * @param  mapping  The mapping used to select this instance
     * @param  request  The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	super.reset(mapping, request);
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

