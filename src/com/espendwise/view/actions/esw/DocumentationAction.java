/**
 * Title: DocumentationAction 
 * Description: This is the Struts Action class handling the ESW documentation functionality.
 */

package com.espendwise.view.actions.esw;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.espendwise.view.forms.esw.DocumentationForm;

/**
 * Implementation of <code>Action</code> that handles log on functionality.
 */
public final class DocumentationAction extends EswAction {
    private static final Logger log = Logger.getLogger(DocumentationAction.class);
    
    //constants to hold the various action mappings that can be returned by this action class.

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
        DocumentationForm theForm = (DocumentationForm)form;
        
        SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
        
    	//determine what action to perform
        //If an operation has been specified use it.
    	String operation = theForm.getOperation();
    	
        //If no operation was specified but there is a previously executed operation use it.
    	if (!Utility.isSet(operation)) {
    		operation = sessionDataUtil.getPreviousDocumentationAction();
    	}
    	
    	//If no operation was specified and there is no previous operation, default to the
    	//default operation
    	if (!Utility.isSet(operation)) {
    		//TODO - change this to be the default documentation action, whatever that is
    		operation = Constants.PARAMETER_OPERATION_VALUE_INIT;
    	}
    	
    	//trim whitespace from the operation
    	if (Utility.isSet(operation)) {
    		operation = operation.trim();
    	}
    	
    	//now that we've determined what action to take, take it
    	boolean rememberOperation = true;
    	
    	//TODO - implement logic here to handle the various operations that the user might
    	//specify, remembering to indicate if the operation should be remembered.  See the 
    	//OrdersAction class for an example
        returnValue = handleUnknownOperation(request, response, theForm, mapping);
        rememberOperation = false;
        
    	if(rememberOperation) {
    		sessionDataUtil.setPreviousDocumentationAction(operation);
    	}
        
        
    	return returnValue;    
    }
    
}

