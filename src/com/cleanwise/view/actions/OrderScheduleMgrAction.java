
package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.logic.OrderScheduleMgrLogic;
import com.cleanwise.view.forms.OrderScheduleMgrForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;

/**
 * Implementation of <strong>Action</strong> that processes the
 * Catalog manager page.
 */
public final class OrderScheduleMgrAction extends ActionSuper {


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
    OrderScheduleMgrForm theForm = (OrderScheduleMgrForm) form;
    String action = request.getParameter("action");
    if (action == null) action = "init";

    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }   



    String scheduleTypeChange = request.getParameter("scheduleTypeChange");
    if(scheduleTypeChange!=null && scheduleTypeChange.trim().length()>0) {
       return (mapping.findForward("display"));
    }

    String userChange = request.getParameter("userChange");
    if(userChange!=null && userChange.trim().length()>0) {
      ActionErrors ae = OrderScheduleMgrLogic.changeUser(request, theForm);
       if(ae.size()>0) {
          saveErrors(request,ae);
       }
       return (mapping.findForward("display"));
    }


    // Process the action
    if (action.equals("init")) {
        theForm.setContentPage("orderScheduleMgrBody.jsp");
        ActionErrors ae = OrderScheduleMgrLogic.init(request, theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
          return (mapping.findForward("display"));
        }
    }
    else if (action.equals("Create New")) {
        theForm.setContentPage("orderScheduleMgrDetail.jsp");
        ActionErrors ae = OrderScheduleMgrLogic.createNew(request, theForm);
        if(ae.size()>0) {
          theForm.setContentPage("orderScheduleMgrBody.jsp");
          saveErrors(request,ae);
          return (mapping.findForward("display"));
        }
    }
    else if (action.equals("Save")) {
        theForm.setContentPage("orderScheduleMgrDetail.jsp");
        ActionErrors ae = OrderScheduleMgrLogic.save(request, theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
          return (mapping.findForward("display"));
        }
    }
    else if (action.equals("detail")) {
        theForm.setContentPage("orderScheduleMgrDetail.jsp");
        ActionErrors ae = OrderScheduleMgrLogic.detail(request, theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
          return (mapping.findForward("display"));
        }
    }
    else if (action.equals("Search")) {
        theForm.setContentPage("orderScheduleMgrBody.jsp");
        ActionErrors ae = OrderScheduleMgrLogic.search(request, theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
          return (mapping.findForward("display"));
        }
    }
    else if (action.equals("sort")) {
        theForm.setContentPage("orderScheduleMgrBody.jsp");
        ActionErrors ae = OrderScheduleMgrLogic.sort(request, theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
          return (mapping.findForward("display"));
        }
    }
    else if (action.equals("Delete")) {
        theForm.setContentPage("orderScheduleMgrBody.jsp");
        ActionErrors ae = OrderScheduleMgrLogic.delete(request, theForm);
        if(ae.size()>0) {
          theForm.setContentPage("orderScheduleMgrDetail.jsp");
          saveErrors(request,ae);
          return (mapping.findForward("display"));
        }
    }
    return (mapping.findForward("display"));
  }

}
