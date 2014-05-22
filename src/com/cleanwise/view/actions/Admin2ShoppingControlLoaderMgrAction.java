package com.cleanwise.view.actions;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.util.LocatePropertyNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.forms.Admin2ShoppingControlLoaderMgrForm;
import com.cleanwise.view.logic.Admin2ShoppingControlLoaderMgrLogic;
import com.cleanwise.view.logic.LocateReportAccountLogic;
import com.cleanwise.view.logic.LocateStoreAccountLogic;
import com.cleanwise.view.utils.RequestPropertyNames;
import com.cleanwise.view.utils.SessionTool;


/**
 *
 * @author veronika
 */
public class Admin2ShoppingControlLoaderMgrAction  extends ActionSuper {
  public ActionForward performSub(
                 ActionMapping mapping,
                 ActionForm form,
                 HttpServletRequest request,
                 HttpServletResponse response)
                 throws IOException, ServletException {
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }
    Admin2ShoppingControlLoaderMgrForm sForm = (Admin2ShoppingControlLoaderMgrForm) form;
    String action  = request.getParameter("action");
    if (action == null) {
        action = "init";
    }
    String mappingAction = "success";
    ActionErrors ae = new ActionErrors();
    try {
      if ("init".equals(action) || "Import".equals(action)) {
        Admin2ShoppingControlLoaderMgrLogic.init(sForm);
      } else if("Upload".equals(action)) {
        ae = Admin2ShoppingControlLoaderMgrLogic.uploadFile(request, sForm);
      } else if("Download Errors".equals(action)){
        ae = Admin2ShoppingControlLoaderMgrLogic.downloadErrors(request, response, sForm);
      } else if("Export".equals(action)) {
          Admin2ShoppingControlLoaderMgrLogic.init(sForm);
          sForm.setExportFlag(true);
          if (!st.getUserData().isaAccountAdmin()) {
              if (RequestPropertyNames.getIsLocateReportAccountRequest(request)) {
                  ae = LocateReportAccountLogic.processAction(request, sForm, LocatePropertyNames.INIT_SEARCH_ACTION);
                  if(ae.size() == 0) {
                      sForm.getLocateReportAccountForm().setLocateReportAccountFl(true);
                  }
              }
              else {
                  ae = LocateStoreAccountLogic.processAction(request, sForm, "initSearch");
                  if(ae.size() == 0) {
                      sForm.getLocateStoreAccountForm().setLocateAccountFl(true);
                  }
              }
          } else {
            ae = Admin2ShoppingControlLoaderMgrLogic.exportShoppingControls(request, response, sForm);    
          }
      } else if ("Return Selected".equals(action)) {
        sForm.setExportFlag(true);
        sForm.getLocateStoreAccountForm().setLocateAccountFl(true);
        ae = Admin2ShoppingControlLoaderMgrLogic.exportShoppingControls(request, response, sForm);
      } 

    } catch (Exception e) {
      request.setAttribute("errorobject", e);
      e.printStackTrace();
      return (mapping.findForward("error"));
    }
    if (ae.size() > 0) {
      saveErrors(request, ae);
      return (mapping.findForward("failure"));
    }
    return (mapping.findForward(mappingAction));

  }


}
