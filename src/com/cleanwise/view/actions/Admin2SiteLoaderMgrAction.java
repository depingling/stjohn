package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.forms.Admin2SiteLoaderMgrForm;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.logic.Admin2SiteLoaderMgrLogic;
import com.cleanwise.view.logic.LocateReportAccountLogic;
import com.cleanwise.view.logic.LocateStoreAccountLogic;
import com.cleanwise.service.api.util.LocatePropertyNames;
import com.cleanwise.service.api.util.RefCodeNames;


/**
 *
 * @author veronika
 */
public class Admin2SiteLoaderMgrAction  extends ActionSuper {
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
    Admin2SiteLoaderMgrForm sForm = (Admin2SiteLoaderMgrForm) form;
    String action  = request.getParameter("action");
    if (action == null) {
        action = "init";
    }
    String mappingAction = "success";
    ActionErrors ae = new ActionErrors();
    try {
      if ("init".equals(action) || "Import".equals(action)) {
        Admin2SiteLoaderMgrLogic.init(sForm);
      } else if("Upload".equals(action)) {
        ae = Admin2SiteLoaderMgrLogic.uploadFile(request, sForm);
      } else if("Download Errors".equals(action)){
        ae = Admin2SiteLoaderMgrLogic.downloadErrors(request, response, sForm);
      } else if("Export Results".equals(action)){
        ae = Admin2SiteLoaderMgrLogic.exportResults(request, response, sForm);
      } else if("Export Template".equals(action)){
        ae = Admin2SiteLoaderMgrLogic.exportTemplate(request, response, sForm);
      } else if("Export".equals(action)) {
          Admin2SiteLoaderMgrLogic.init(sForm);
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
            ae = Admin2SiteLoaderMgrLogic.exportSites(request, response, sForm);
          }
      } else if ("Return Selected".equals(action)) {
        sForm.setExportFlag(true);
        ae = Admin2SiteLoaderMgrLogic.exportSites(request, response, sForm);
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
