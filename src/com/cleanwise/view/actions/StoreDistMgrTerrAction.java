
package com.cleanwise.view.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.logic.StoreDistMgrTerrLogic;
import com.cleanwise.view.utils.*;

/**
 *  Implementation of <strong>Action</strong> that processes the Store manager
 *  page.
 *
 *@author     tbesser
 *@created    August 15, 2001
 */
public final class StoreDistMgrTerrAction extends ActionSuper {

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
    public ActionForward performSub(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
             throws IOException, ServletException {


        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
          return mapping.findForward("/userportal/logon");
        }

        ActionErrors ae = new ActionErrors();
        // Determine the store manager action to be performed
        String action = request.getParameter("action");

        if (action == null) {
            action = "init";
        }

        // Process the action
        try {
            if (action.equals("init")) {
   		        StoreDistMgrTerrLogic.init(request, form);
                return (mapping.findForward("success"));
            }
            if (action.equals("Search")) {
   		       ae = StoreDistMgrTerrLogic.search(request, form);
               if(ae.size()>0) {
                 saveErrors(request,ae);
               }
            }
            if (action.equals("Save")) {
   		       ae = StoreDistMgrTerrLogic.save(request, form);
               if(ae.size()>0) {
                 saveErrors(request,ae);
               }
            }
            if (action.equals("sort")) {
   		       ae = StoreDistMgrTerrLogic.sort(request, form);
               if(ae.size()>0) {
                 saveErrors(request,ae);
               }
            }
            if (action.equals("Select All")) {
   		       ae = StoreDistMgrTerrLogic.selectAll(request, form);
               if(ae.size()>0) {
                 saveErrors(request,ae);
               }
            }
            if (action.equals("Clear Selection")) {
   		       ae = StoreDistMgrTerrLogic.clearSelection(request, form);
               if(ae.size()>0) {
                 saveErrors(request,ae);
               }
            }

        }
        catch (Exception e) {
          e.printStackTrace();
          ae.add("error",new ActionError("error.simpleGenericError","System error: "+e.getMessage()));
          saveErrors(request,ae);
          return (mapping.findForward("failure"));
        }
        return (mapping.findForward("success"));

    }

}

