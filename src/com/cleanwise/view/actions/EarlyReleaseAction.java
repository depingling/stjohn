package com.cleanwise.view.actions;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.forms.CheckoutForm;
import com.cleanwise.view.logic.CheckoutLogic;
import com.cleanwise.view.utils.SessionTool;
import org.apache.struts.action.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class EarlyReleaseAction extends ActionSuper{

    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String DISPLAY = "display";
    private static final String INIT    = "init";
    private static final String ERROR   = "error";

    // ----------------------------------------------------- Public Methods

    /**
     *  Process the specified HTTP request, and create the corresponding HTTP
     *  response (or forward to another web component that will create it).
     *  Return an <code>ActionForward</code> instance describing where and how
     *  control should be forwarded, or <code>null</code> if the response has
     *  already been completed.
     *
     *@param  mapping               The ActionMapping used to select this instance
     *@param  request               The HTTP request we are processing
     *@param  response              The HTTP response we are creating
     *@param  form                  Description of Parameter
     *@return                       Description of the Returned Value
     *@exception java.io.IOException       if an input/output error occurs
     *@exception javax.servlet.ServletException  if a servlet exception occurs
     */
    public ActionForward performSub(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response)
                                    throws IOException, ServletException {

        // Determine the store manager action to be performed
        String action = request.getParameter("action");
        if (action == null) {
            action = INIT;
        }

        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
            return mapping.findForward(st.getLogonMapping());
        }

        try {
            ActionForward  actionForward = callHandlerForm(action,form,request,mapping);
            return actionForward;
        } catch(Exception e) {
            e.printStackTrace();
            return mapping.findForward(ERROR);
        }
    }


    private ActionForward  callHandlerForm(String action,ActionForm form,HttpServletRequest request,ActionMapping mapping)  throws Exception   {

        String forward_page=DISPLAY;
        if(form instanceof CheckoutForm) forward_page = checkoutAction(action,request,(CheckoutForm)form);

        return mapping.findForward(forward_page);
    }

    private String checkoutAction(String action, HttpServletRequest request, CheckoutForm form) throws Exception {

        if (INIT.equals(action) || "confirmEarlyRelease".equals(action)) {
            ActionErrors ae = CheckoutLogic.confirmEarlyRelease(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return SUCCESS;
        } else if ("earlyrelease".equals(action)) {
            ActionErrors ae = CheckoutLogic.earlyRelease(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return SUCCESS;
        } else {
            ActionErrors ae = new ActionErrors();
            ae.add("error", new ActionError("error.systemError", "Unknown action: ["+Utility.encodeForHTML(action)+"]"));
            saveErrors(request, ae);
            return DISPLAY;
        }
    }


}
