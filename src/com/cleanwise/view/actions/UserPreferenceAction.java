
package com.cleanwise.view.actions;

import com.cleanwise.service.api.APIAccess;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import javax.servlet.http.HttpSession;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.*;

/**
 * Handles setting transparent user options.  Action is taken and user is returned to the same page that they started in.
 */
public final class UserPreferenceAction extends ActionBase {

    // ----------------------------------------------------- Public Methods

    /**
     *  Process the specified HTTP request, and create the corresponding HTTP
     *  response (or forward to another web component that will create it).
     *  Return an <code>ActionForward</code> instance describing where and how
     *  control should be forwarded, or <code>null</code> if the response has
     *  already been completed.
     *
     *@param  mapping               The ActionMapping used to select this
     *      instance
     *@param  request               The HTTP request we are processing
     *@param  response              The HTTP response we are creating
     *@param  form                  Description of Parameter
     *@return                       Description of the Returned Value
     *@exception  IOException       if an input/output error occurs
     *@exception  ServletException  if a servlet exception occurs
     */
    public ActionForward performAction(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
             throws Exception {

        
                 
        String action  = request.getParameter("action");
        if("setPref".equals(action)){
            String prefName=request.getParameter("name");
            String prefValue=request.getParameter("value");
            APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
            CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
            factory.getPropertyServiceAPI().setUserProperty(appUser.getUser().getUserId(),prefName,prefValue);
            appUser.getUserProperties().setProperty(prefName,prefValue);
            String backTo = request.getParameter("path");
            response.sendRedirect(backTo);
            return null;
        }
        return null;
    }

}

