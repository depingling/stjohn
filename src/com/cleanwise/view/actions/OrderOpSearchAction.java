
package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.logic.OrderOpLogic;
import com.cleanwise.view.utils.*;

/**
 * Implementation of <strong>Action</strong> that processes the
 * OrderOp manager page.
 */
public final class OrderOpSearchAction extends ActionSuper {


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

    // Determine the store manager action to be performed
    String action = request.getParameter("action");
    if (action == null) action = "init";

    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }   

    // Process the action
    try {

      if (action.equals("init")) {
        OrderOpLogic.init(request, form);
        return (mapping.findForward("success"));
      }
      else if (action.equals("search")) {
        ActionErrors ae = OrderOpLogic.search(request, form);
        if (ae.size() > 0) {
            saveErrors(request, ae);
        }
        return (mapping.findForward("success"));
      }
      else if (action.equals("viewall")) {
        OrderOpLogic.searchAll(request, form);
        return (mapping.findForward("success"));
      }
      else if (action.equals("sort")) {
	  OrderOpLogic.sort(request, form);
	  return (mapping.findForward("success"));
      }
      else {
        OrderOpLogic.init(request, form);
        return (mapping.findForward("success"));
      }
    }
    catch (Exception e) {
        e.printStackTrace();
        ActionErrors ae = new ActionErrors();
        ae.add("orderOpSearch", new ActionError("error.systemError",e.getMessage()));
        saveErrors(request, ae);
        return (mapping.findForward("failure"));
    }

  }

}
