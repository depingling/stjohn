/**
 * Title: ContactUsAction 
 * Description: This is the Struts Action class handling the ESW contact us functionality.
 */

package com.espendwise.view.actions.esw;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.view.actions.ActionSuper;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.Constants;
import com.espendwise.view.forms.esw.EswForm;

/**
 * Implementation of <code>Action</code> that is the superclass of all ESW actions.
 */
public abstract class EswAction extends ActionSuper {
	
    private static final Logger log = Logger.getLogger(EswAction.class);
    
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     * @param  mapping      the ActionMapping used to select this instance.
     * @param  form         the ActionForm containing the data.
     * @param  request      the HTTP request we are processing.
     * @param  response     the HTTP response we are creating.
     * @return              an ActionForward describing the component that should receive control.
     */
    public final ActionForward performSub(ActionMapping mapping, ActionForm form,
            						HttpServletRequest request, HttpServletResponse response) {
    	request.setAttribute(Constants.ACTION_FORM, form);
    	return performAction(mapping, form, request, response);
    }
    
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     * @param  mapping      the ActionMapping used to select this instance.
     * @param  form         the ActionForm containing the data.
     * @param  request      the HTTP request we are processing.
     * @param  response     the HTTP response we are creating.
     * @return              an ActionForward describing the component that should receive control.
     */
    public abstract ActionForward performAction(ActionMapping mapping, ActionForm form,
            						HttpServletRequest request, HttpServletResponse response);
    
    /**
     * Method to determine what action forward should be returned after an unknown operation has been specified.
     * @param  request      the HTTP request we are processing.
     * @param  response     the HTTP response we are creating.
     * @param  form         the EswForm containing the data.
     * @param  mapping      the ActionMapping used to select this instance.
     * @return              an ActionForward describing the component that should receive control.
     */
    ActionForward handleUnknownOperation(HttpServletRequest request, HttpServletResponse response, 
    		EswForm form, ActionMapping mapping) {
    	
    	log.info("Unknown operation encountered: " + form.getOperation());
        ActionErrors errors = new ActionErrors();
        String errorMess = ClwI18nUtil.getMessage(request, "error.functionalityNotImplemented", null);
        errors.add("error", new ActionError("error.simpleGenericError", errorMess));
        saveErrors(request, errors);
    	return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);    
    }
    
}

