/**
 * Title: ErrorAction 
 * Description: This is the Struts Action class handling errors for which the Struts
 * framework cannot be employed (i.e. errors carried across redirects)
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

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.DocumentationForm;
import com.espendwise.view.forms.esw.ModuleIntegrationForm;

/**
 * Implementation of <code>Action</code> that handles log on functionality.
 */
public final class ErrorAction extends EswAction {
    private static final Logger log = Logger.getLogger(ErrorAction.class);
    
    //constants to hold the various action mappings that can be returned by this action class.
    private static final String MAPPING_ERROR = "error";

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
    	
        //if there are any errors in the session, retrieve them and save them as errors in the 
        //request.  This is something of a hack, but these errors needed to be persisted across a 
        //redirect.
        Object errors = Utility.getSessionDataUtil(request).getErrors();
        if (errors != null && errors instanceof ActionErrors) {
      	  saveErrors(request, (ActionErrors)errors);
      	  //now that the errors are processed, blank them out
      	  Utility.getSessionDataUtil(request).setErrors(null);
        }
    	returnValue = mapping.findForward(MAPPING_ERROR);
    	return returnValue;    
    }
    
}

