/**
 * Title: ErrorForm 
 * Description: This is the Struts ActionForm class handling errors for which the Struts
 * framework cannot be employed (i.e. errors carried across redirects).
 *
 */

package com.espendwise.view.forms.esw;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * Implementation of <code>ActionForm</code> that handles eSpendwise module integration functionality.
 */
public final class ErrorForm extends EswForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7448514167542928132L;

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

