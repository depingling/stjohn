package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.logic.Admin2SiteOrderGuideLogic;
import com.cleanwise.view.utils.*;

/**
 * Implementation of <strong>Action</strong> that processes the
 * StoreSiteOrderGuide manager page.
 */
public class Admin2SiteOrderGuideAction extends ActionBase {


  // -------------------------------------------------- Public Methods


  /**
   * Process the specified HTTP request, and create the corresponding
   * HTTP response (or forward to another web component that will
   * create it).  Return an <code>ActionForward</code> instance
   * describing where and how control should be forwarded, or
   * <code>null</code> if the response has already been completed.
   *
   * @param mapping The ActionMapping used to select this instance
   * @param actionForm The optional ActionForm bean for this request (if any)
   * @param request The HTTP request we are processing
   * @param response The HTTP response we are creating
   *
   * @exception IOException if an input/output error occurs
   * @exception ServletException if a servlet exception occurs
   */
  public ActionForward performAction(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws Exception {

    // Determine the store manager action to be performed
    String action = request.getParameter("action");
    if (action == null) action = "init";
    MessageResources mr = getResources(request);

    // Get the form buttons as specified in the properties file.
    String saveStr = getMessage(mr, request, "global.action.label.save");
    String createStr = getMessage(mr, request, "admin.button.create");
    String deleteStr = getMessage(mr, request, "global.action.label.delete");
    String removeStr = getMessage(mr, request, "admin.button.remove");
    String findItemsStr = getMessage(mr, request, "admin.button.findItems");
    String addItemsStr = getMessage(mr, request, "admin.button.chose");
    String returnSelectedStr = getMessage(mr,request, "admin.button.returnSelected");

    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if (st.checkSession() == false) {
      return mapping.findForward("/userportal/logon");
    }
    // Process the action
    ActionErrors ae = new ActionErrors();
    String forward = "success";
    if (action.equals("init")) {
      ae = Admin2SiteOrderGuideLogic.init(request, form);
    } else if (action.equals("detail")) {
      Admin2SiteOrderGuideLogic.getDetail(request, form);
      forward = "detail";
    } else if (action.equals(createStr)) {
      ae = Admin2SiteOrderGuideLogic.addOrderGuide(request, form);
      if (ae.size() > 0) {
        forward = "main";
      } else {
        forward = "new";
      }
    } else if (action.equals(saveStr)) {
      ae = Admin2SiteOrderGuideLogic.update(request, form);
      forward = "detail";
    } else if (action.equals(deleteStr)) {
      Admin2SiteOrderGuideLogic.delete(request, form);
      ae = Admin2SiteOrderGuideLogic.init(request, form);
      forward = "main";
    }
    else if (action.equals(removeStr)) {
      Admin2SiteOrderGuideLogic.removeItems(request, form);
      forward = "detail";
    }
    else if (action.equals(findItemsStr)) {
      Admin2SiteOrderGuideLogic.findItems(request, form);
      forward = "finditems";
    }
    else if (action.equals(addItemsStr)) {
      Admin2SiteOrderGuideLogic.addItems(request, form);
      Admin2SiteOrderGuideLogic.getDetail(request, form);
      forward = "detail";
    }
    else if (action.equals("sort")) {
      Admin2SiteOrderGuideLogic.sort(request, form);
    }
    else if (action.equals("sortitems")) {
      Admin2SiteOrderGuideLogic.sortItems(request, form);
    }
    else if (action.equals("sortfinditems")) {
      Admin2SiteOrderGuideLogic.sortItems(request, form);
      forward = "finditems";
    }
    else if (action.equals(returnSelectedStr)) {
      Admin2SiteOrderGuideLogic.addNewItems(request, form);
      Admin2SiteOrderGuideLogic.getDetail(request, form);
    }
    if (ae.size() > 0) {
      saveErrors(request, ae);
    }
    return mapping.findForward(forward);
  }

}
