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
import com.cleanwise.view.logic.StoreDomainMgrLogic;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.util.Utility;


public class StoreDomainMgrAction extends ActionSuper {


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
    HttpServletResponse response) throws IOException, ServletException {

    // Determine the store manager action to be performed
    String action = request.getParameter("action");
    if (action == null) {
      action = "init";
    }

    SessionTool st = new SessionTool(request);

    // Is there a currently logged on user?
    if (st.checkSession() == false) {
      return mapping.findForward("/userportal/logon");
    }

    MessageResources mr = getResources(request);

    // Get the form buttons as specified in the properties file.
    String saveStr = getMessage(mr, request, "global.action.label.save");
    String deleteStr = getMessage(mr, request, "global.action.label.delete");
    String searchStr = getMessage(mr, request, "global.action.label.search");
    String createStr = getMessage(mr, request, "admin.button.create");
    String cloneStr = getMessage(mr, request, "admin.button.createClone");

    // Determine if doing domain configuration
    boolean domainConfig =  Utility.isTrue(request.getParameter("domainAdmConfiguration"), false);

    // Process the action
    try {
      if (action.equals("init")) {
        if (domainConfig) {
          // we're doing domain config
          //StoreDomainMgrLogic.initConfig(request, form);
        } else {
          //StoreDomainMgrLogic.init(request, form);
        }
        return (mapping.findForward("success"));
      } else if ("Return Selected".equals(action)) {
//        String submitFormIdent = request.getParameter("jspSubmitIdent");
//        if (submitFormIdent != null &&
//            submitFormIdent.indexOf("#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_ACCOUNT_FORM) ==  0) {
//          StoreDomainMgrLogic.setSiteAccountFields(request, form);
//        }
        return (mapping.findForward("success"));
      } else if (action.equals(searchStr)) {
        if (domainConfig) {
          StoreDomainMgrLogic.searchConfig(request, form);
        } else {
          StoreDomainMgrLogic.searchConfig(request, form);
        }
        return (mapping.findForward("success"));
      } else if (action.equals(createStr)) {
        StoreDomainMgrLogic.addSite(request, form);
        return (mapping.findForward("sitedetail"));
//      } else if (action.equals(cloneStr)) {
//        StoreDomainMgrLogic.cloneSite(request, form);
//        return (mapping.findForward("success"));
      }

      else if (action.equals("updatesite") ||
                 action.equals(saveStr)) {
        if (true) {
        	ActionErrors ae = StoreDomainMgrLogic.updateConfig(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
              }
        	return (mapping.findForward("success"));
        } else {
//          ActionErrors ae = StoreDomainMgrLogic.updateSite(request, form);
          return (mapping.findForward("sitedetail"));
        }
      } else if (action.equals("sitedetail")) {
//        StoreDomainMgrLogic.getDetail(request, form);
        return (mapping.findForward("sitedetail"));
      } else if (action.equals(deleteStr)) {
        ActionErrors ae = StoreDomainMgrLogic.delete(request, form);
        if (ae.size() > 0) {
          saveErrors(request, ae);
          return (mapping.findForward("sitedetail"));
        }
        return (mapping.findForward("main"));
      } else if (action.equals("sort")) {
        StoreDomainMgrLogic.sort(request, form);
        return (mapping.findForward("success"));

      } else if (action.equals("inventory_update")) {
        ActionErrors ae = StoreDomainMgrLogic.lookupInventoryData(request, form);
        if (ae.size() > 0) {
          saveErrors(request, ae);
        }
        return (mapping.findForward("success"));
      } else if (action.equals("update_site_inventory")) {
        ActionErrors ae = StoreDomainMgrLogic.updateInventoryData(request, form);
        if (ae.size() > 0) {
          saveErrors(request, ae);
        }
        return (mapping.findForward("success"));
      } else if (action.equals("update_site_controls")) {
        ActionErrors ae = StoreDomainMgrLogic.updateShoppingControls
                          (request, form);
        if (ae.size() > 0) {
          saveErrors(request, ae);
        }
        return (mapping.findForward("success"));
      } else if (action.equals("list.all.bsc")) {
        ActionErrors ae = StoreDomainMgrLogic.listAll(action, request, form);
        if (ae.size() > 0) {
          saveErrors(request, ae);
        }
        return (mapping.findForward("success"));

      } else if (action.equals("saveFieldValues")) {
//        StoreDomainMgrLogic.saveFieldValues(request, form);
        return (mapping.findForward("success"));
      } else {
        StoreDomainMgrLogic.init(request, form);
        return (mapping.findForward("success"));
      }
    } catch (Exception e) {
      e.printStackTrace();
      ActionErrors ae = new ActionErrors();
      ae.add(ActionErrors.GLOBAL_ERROR,
             new ActionError("error.genericError",
                             "Uncaught Exception: " + e.getMessage()));
      saveErrors(request, ae);
      return (mapping.findForward("failure"));
    }

  }

}


