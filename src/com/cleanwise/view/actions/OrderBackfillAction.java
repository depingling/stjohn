
package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.logic.OrderBackfillLogic;
import com.cleanwise.view.forms.OrderBackfillForm;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.util.RefCodeNames;

/**
 * Implementation of <strong>Action</strong> that saves a new
 * freightTable detail or updates an existing freightTable detail.
 */
public final class OrderBackfillAction extends ActionSuper {


  // ------------------------------------------------------------ Public Methods


  /**
   * Process the specified HTTP request, and create the corresponding HTTP
   * response (or forward to another web component that will create it).
   * Return an <code>ActionForward</code> instance describing where and how
   * control should be forwarded, or <code>null</code> if the response has
   * already been completed.
   *
   * @param mapping
   *  The ActionMapping used to select this instance
   * @param actionForm
   *  The optional ActionForm bean for this request (if any)
   * @param request
   *  The HTTP request we are processing
   * @param response
   *  The HTTP response we are creating
   *
   * @exception IOException
   *  if an input/output error occurs
   * @exception ServletException
   *  if a servlet exception occurs
   */
  public ActionForward performSub(
                 ActionMapping mapping,
                 ActionForm form,
                 HttpServletRequest request,
                 HttpServletResponse response)
                 throws IOException, ServletException {

    // Get the action and the freightTableId from the request.
    String action  = request.getParameter("action");
    OrderBackfillForm sForm = (OrderBackfillForm) form;
    if (action == null) {
      if(sForm.getAllItemsStatus()==null || sForm.getAllItemsStatus().trim().length()==0) {
        action = "view";
      } else {
        action = "status";
      }
    }

    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }      
        
        
    String orderStatusId = request.getParameter("id");
    if (orderStatusId == null) orderStatusId = "";

    MessageResources mr = getResources(request);

    // Get the form buttons as specified in the properties file.
    try {
      // view
      if (action.equals("view")) {
        ActionErrors ae =
           OrderBackfillLogic.getOrderStatusDetail(request, form, orderStatusId);
        if (ae.size() > 0) {
          saveErrors(request, ae);
        }
        return (mapping.findForward("display"));
      }
      // select
      if (action.equals("Select")) {
        ActionErrors ae =
           OrderBackfillLogic.selectOrder(request, form);
        if (ae.size() > 0) {
          saveErrors(request, ae);
        }
        return (mapping.findForward("display"));
      }
      // status
      if (action.equals("status")) {
        OrderBackfillLogic.setItemStatus(request, form);
        return (mapping.findForward("display"));
      }

      if (action.equals("Update")) {
        ActionErrors ae =
           OrderBackfillLogic.updateOrder(request, form);
        if (ae.size() > 0) {
          saveErrors(request, ae);
        }
        return (mapping.findForward("display"));
      }
      if (action.equals("Create")) {
        ActionErrors ae =
           OrderBackfillLogic.createOrder(request, form);
        if (ae.size() > 0) {
          saveErrors(request, ae);
        }
        return (mapping.findForward("display"));
      }
      else if (action.equals("sortitems")) {
        OrderBackfillLogic.sortItems(request, form);
        return (mapping.findForward("display"));
      }

      else {
        return (mapping.findForward("list"));
      }
    }

    // Catch all exceptions here.
    catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("errorobject", e);
      return (mapping.findForward("error"));
    }
  }

  
}
