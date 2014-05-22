
package com.cleanwise.view.actions;

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
import com.cleanwise.view.logic.StoreMgrLogic;
import com.cleanwise.view.utils.*;


/**
 *  Implementation of <strong>Action</strong> that processes the Account
 *  Configuration page.
 *
 *@author     tbesser
 *@created    August 23, 2001
 */
public final class AccountFieldsConfigAction extends ActionBase {

    // ----------------------------------------------- Public Methods

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
    public ActionForward performAction(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
             throws Exception {

        String action = request.getParameter("action");
        if (action == null) {
            action = "init";
        }
        MessageResources mr = getResources(request);

        // Get the form buttons as specified in the properties file.
        String saveStr = getMessage(mr,request,"global.action.label.save");

        // Process the action
        if (action.equals("init")) {
            StoreMgrLogic.fetchAccountFields(request, form);
            return (mapping.findForward("success"));
        }
        else if (action.equals(saveStr)) {
            StoreMgrLogic.saveAccountFields(request, form);
            return (mapping.findForward("success"));
        }

        return (mapping.findForward("failure"));
    }

}



