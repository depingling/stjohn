
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
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.logic.ItemSubstMgrAddLogic;
import com.cleanwise.view.utils.*;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.forms.ItemSubstMgrAddForm;
import com.cleanwise.view.forms.ContractMgrDetailForm;
import java.util.Enumeration;


/**
 * Implementation of <strong>Action</strong> that saves a new
 * catalog detail or updates an existing catalog detail.
 */
public final class ItemSubstMgrAddAction extends ActionSuper {


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

    ItemSubstMgrAddForm sForm = (ItemSubstMgrAddForm) form;
    // Get the action and the catalogId from the request.
    String action  = request.getParameter("action");
    if(action==null && sForm.getSelectedItemIdS()!=null) {
      action = "Return Selected";
    }
    HttpSession session = request.getSession();

    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }   

    try {
      // null
      if(action==null) {
        ItemSubstMgrAddLogic.init(request,sForm);
      //Search for Items
      } else if(action.equals("Search")) {
        ItemSubstMgrAddLogic.searchForItem(request,sForm);
      }
      //Add to catalog
      else if(action.equals("Return Selected")) {
        ActionErrors ae = ItemSubstMgrAddLogic.returnSelected (request, sForm);
        if(ae.size()>0) {
          saveErrors(request, ae);  
          return (mapping.findForward("error"));
        }
        return (mapping.findForward("exit"));
      }
      //Go to selected page
      else if (action.equals("goPage")) {
        ItemSubstMgrAddLogic.goPage(request, sForm);
        return (mapping.findForward("init"));
      }
    // Catch all exceptions here.
    } catch (Exception e) {
      request.setAttribute("errorobject", e);
      e.printStackTrace();
      return (mapping.findForward("error"));
    }
    return (mapping.findForward("init"));
  }


}
