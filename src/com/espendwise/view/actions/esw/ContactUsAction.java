/**
 * Title: ContactUsAction 
 * Description: This is the Struts Action class handling the ESW contact us functionality.
 */

package com.espendwise.view.actions.esw;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.espendwise.view.forms.esw.ContactUsForm;

/**
 * Implementation of <code>Action</code> that handles log on functionality.
 */
public final class ContactUsAction extends EswAction {
    private static final Logger log = Logger.getLogger(ContactUsAction.class);
    
    //constants to hold the various action mappings that can be returned by this action class.
    private static final String MAPPING_CONTACT_US = "contactUs"; 

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
    public ActionForward performAction(ActionMapping mapping, ActionForm form,
            						HttpServletRequest request, HttpServletResponse response) {

        //If there isn't a currently logged on user then go to the login page
        if (!new SessionTool(request).checkSession()) {
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_LOGON);
        }
        
    	ActionForward returnValue = null;
        ContactUsForm theForm = (ContactUsForm)form;
        
        //intentionally do not set any active main or sub tabs.  
        //Because this functionality is outside the scope of any of our tabs, we 
        //don't want any of them to be selected.
        Utility.getSessionDataUtil(request).setSelectedMainTab(Constants.EMPTY);
        Utility.getSessionDataUtil(request).setSelectedSubTab(Constants.EMPTY);
        
    	String operation = theForm.getOperation();
    	if (!Utility.isSet(operation)) {
    		operation = Constants.PARAMETER_OPERATION_VALUE_INIT;
    	}

        if (Constants.PARAMETER_OPERATION_VALUE_INIT.equalsIgnoreCase(operation)) {
        	returnValue = handleInitializationRequest(request, response, theForm, mapping);
        }
        else {
        	returnValue = handleUnknownOperation(request, response, theForm, mapping);
        }
        
    	return returnValue;    
    }
    
    /*
     * Private method to determine what action forward should be returned after an initial contact us request.
     */
    private ActionForward handleInitializationRequest(HttpServletRequest request, HttpServletResponse response, 
    		ContactUsForm form, ActionMapping mapping) {
    	
    	//TODO - implement this method.
    	return mapping.findForward(MAPPING_CONTACT_US);    
    }
    
}

