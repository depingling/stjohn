
package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.logic.DeliveryScheduleMgrLogic;
import com.cleanwise.view.forms.DeliveryScheduleMgrForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;

/**
 * Implementation of <strong>Action</strong> that processes the
 * Catalog manager page.
 */
public final class DeliveryScheduleMgrAction extends ActionSuper {


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
    DeliveryScheduleMgrForm theForm = (DeliveryScheduleMgrForm) form;
    String action = request.getParameter("action");
    if (action == null) action = "init";

    // Is there a currently logged on user?
/*
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }   
*/
    String scheduleRuleChange = request.getParameter("scheduleRuleChange");
    if(scheduleRuleChange!=null && scheduleRuleChange.trim().length()>0) {
       return (mapping.findForward("success"));
    }

    // Process the action
    if (action.equals("init")) {
        theForm.setContentPage("deliveryScheduleMgrSearch.jsp");
        ActionErrors ae = DeliveryScheduleMgrLogic.initSearch(request, theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
          return (mapping.findForward("failure"));
        }
    }
    else if (action.equals("Create New")) {
        theForm.setContentPage("deliveryScheduleMgrDetail.jsp");
        ActionErrors ae = DeliveryScheduleMgrLogic.createNew(request, theForm);
        if(ae.size()>0) {
          theForm.setContentPage("deliveryScheduleMgrSearch.jsp");
          saveErrors(request,ae);
          return (mapping.findForward("failure"));
        }
    }
    else if (action.equals("Save")) {
        theForm.setContentPage("deliveryScheduleMgrDetail.jsp");
        ActionErrors ae = DeliveryScheduleMgrLogic.save(request, theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
          return (mapping.findForward("failure"));
        }
    }
    else if (action.equals("Save Configuration")) {
        ActionErrors ae = DeliveryScheduleMgrLogic.saveConfiguration(request, theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
          return (mapping.findForward("failure"));
        }
    }
    if (action.equals("View All")) {
        ActionErrors ae = DeliveryScheduleMgrLogic.viewAll(request, theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
          return (mapping.findForward("failure"));
        }
    }
    else if (action.equals("Search")) {
        String contantPage = theForm.getContentPage();
        if("deliveryScheduleMgrSearch.jsp".equals(contantPage)) {
           ActionErrors ae = DeliveryScheduleMgrLogic.searchSchedule(request, theForm);
           if(ae.size()>0) {
             saveErrors(request,ae);
             return (mapping.findForward("failure"));
           }
        }
        if("deliveryScheduleMgrDetail.jsp".equals(contantPage)) {
           ActionErrors ae = DeliveryScheduleMgrLogic.searchTerritory(request, theForm);
           if(ae.size()>0) {
             saveErrors(request,ae);
             return (mapping.findForward("failure"));
           }
        }        
    }
    else if (action.equals("detail")) {
        theForm.setContentPage("deliveryScheduleMgrDetail.jsp");
        ActionErrors ae = DeliveryScheduleMgrLogic.detail(request, theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
          return (mapping.findForward("failure"));
        }
    }
    else if (action.equals("Select All")) {
        ActionErrors ae = DeliveryScheduleMgrLogic.selectAll(request, theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
          return (mapping.findForward("failure"));
        }
    }
    else if (action.equals("Clear Selection")) {
        ActionErrors ae = DeliveryScheduleMgrLogic.clearSelected(request, theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
          return (mapping.findForward("failure"));
        }
    }
    else if (action.equals("sort")) {
        ActionErrors ae = DeliveryScheduleMgrLogic.sort(request, theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
          return (mapping.findForward("failure"));
        }
    }
    else if (action.equals("sortTerr")) {
        ActionErrors ae = DeliveryScheduleMgrLogic.sortTerr(request, theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
          return (mapping.findForward("failure"));
        }
    }
    else if (action.equals("Delete")) {
        theForm.setContentPage("deliveryScheduleMgrSearch.jsp");
        ActionErrors ae = DeliveryScheduleMgrLogic.delete(request, theForm);
        if(ae.size()>0) {
          theForm.setContentPage("deliveryScheduleMgrDetail.jsp");
          saveErrors(request,ae);
          return (mapping.findForward("failure"));
        }
    }
  return (mapping.findForward("success"));
  }

}
