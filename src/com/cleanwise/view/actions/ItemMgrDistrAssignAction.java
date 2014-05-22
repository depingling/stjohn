
package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.logic.ItemMgrDistrAssignLogic;
import javax.servlet.http.HttpSession;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.*;

/**
 * Implementation of <strong>Action</strong> that processes the
 * Catalog manager page.
 */
public final class ItemMgrDistrAssignAction extends ActionSuper {


  // ----------------------------------------------------- Public Methods


  /**
   * Process the specified HTTP request, and create the corresponding HTTP
   * response (or forward to another web component that will create it).
   * Return an <code>ActionForward</code> instance describing where and how
   * control should be forwarded, or <code>null</code> if the response has
   * already been completed.
   *
   * @param mapping The ActionMapping used to select this instance
   * @param actionForm The optional ActionForm bean for this request (if any)
   * @param request The HTTP request we are processing
   * @param response The HTTP response we are creating
   *
   * @exception IOException if an input/output error occurs
   * @exception ServletException if a servlet exception occurs
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
                     
    // Determine the store manager action to be performed
    String action = request.getParameter("action");
    if (action == null) action = "init";
    // Process the action
    try {

      if (action.equals("init")) {
        ItemMgrDistrAssignLogic.init(request, form);
        return (mapping.findForward("success"));
      }
      else if (action.equals("Search")) {
        ItemMgrDistrAssignLogic.search(request, form);
        return (mapping.findForward("success"));
      }
      else if (action.equals("View All Distributors")) {
        ItemMgrDistrAssignLogic.searchAll(request, form);
        return (mapping.findForward("success"));
      }
      else if (action.equals("Add/Update Item Distributors")) {
        ItemMgrDistrAssignLogic.addUpdate(request, form);
        return (mapping.findForward("exit"));
      }
      else if (action.equals("goPage")) {
        ItemMgrDistrAssignLogic.goPage(request, form);
        return (mapping.findForward("success"));
      }
      else if (action.equals("exit")) {
        return (mapping.findForward("success"));
      }
      else {
        ItemMgrDistrAssignLogic.init(request, form);
        return (mapping.findForward("success"));
      }
    }
    catch (Exception e) {
        e.printStackTrace();
      return (mapping.findForward("failure"));
    }

  }

}
