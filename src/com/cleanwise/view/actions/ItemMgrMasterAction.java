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
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.ItemMgrMasterForm;
import com.cleanwise.view.logic.ItemMgrMasterLogic;

import java.util.Enumeration;
/**
 * Implementation of <strong>Action</strong> that saves a new
 * catalog detail or updates an existing catalog detail.
 */
public final class ItemMgrMasterAction extends ActionSuper {


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


    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {      
        return mapping.findForward("/userportal/logon");
    }    

    // Get the action and the catalogId from the request.
    String action  = request.getParameter("action");
    HttpSession session = request.getSession();

    ItemMgrMasterForm sForm = (ItemMgrMasterForm) form;
    String outService = sForm.getOutServiceName();
    sForm.setOutServiceName("");
    try {

      //First start (init)
      if(action==null) {
        if(outService!=null && outService.equalsIgnoreCase("distributorAssign")) {
          ActionErrors ae = ItemMgrMasterLogic.addFromLookup(request,sForm);
          if (ae.size() > 0) saveErrors(request, ae);
        } else {
          ItemMgrMasterLogic.initNew(request,sForm);
        }
      }

      else if(action.equals("create")) {
        ItemMgrMasterLogic.initNew(request,sForm);
      }

      //Edit existing item
      else if(action.equals("edit")) {
        ActionErrors ae=ItemMgrMasterLogic.initEdit(request,sForm);
        if(ae.size()>0) {
          saveErrors(request, ae);
          ActionForward af=mapping.findForward(sForm.getRetAction());
          String path = af.getPath();
          ActionForward af1=  new ActionForward(path);
          af1.setRedirect(af.getRedirect());
          return (af1);
        }
      }
      //Clone existing item
      else if(action.equals("clone")) {
        ActionErrors ae=ItemMgrMasterLogic.initClone(request,sForm);
        if(ae.size()>0) {
          saveErrors(request, ae);
          ActionForward af=mapping.findForward(sForm.getRetAction());
          String path = af.getPath();
          ActionForward af1=  new ActionForward(path);
          af1.setRedirect(af.getRedirect());
          return (af1);
        }
      }

      //Add distributor to list of distributors
      else if(action.equals("Add/Update Distributor")) {
        ActionErrors ae = ItemMgrMasterLogic.addDistributor(request,sForm);
		if (ae.size() > 0) saveErrors(request, ae);
      }

      //Remove marked distributors from the list of distributors
      else if(action.equals("Remove Distributor")) {
        ActionErrors ae = ItemMgrMasterLogic.removeDistributor(request,sForm);
		if (ae.size() > 0) saveErrors(request, ae);
      }

      //Remove marked distributors from the list of distributors
      else if(action.equals("submit")) {
        ActionErrors ae = ItemMgrMasterLogic.addFromLookup(request,sForm);
		if (ae.size() > 0) saveErrors(request, ae);
      }

      //Saves data to database and returns to previous page
      else if(action.equals("Save Item")) {
        ActionErrors ae = ItemMgrMasterLogic.saveMasterProduct(request,sForm);
		if (ae.size() > 0) {saveErrors(request, ae); return new ActionForward(mapping.getInput());}
        else {
          ActionForward af=mapping.findForward(sForm.getRetAction());
          String path = af.getPath();
          path +="&itemId="+sForm.getProduct().getProductId();
          ActionForward af1=  new ActionForward(path);
          af1.setRedirect(af.getRedirect());
          return (af1);
        }
      }

      else if (action.equals("Store Item Data")) {
         return (mapping.findForward("ext"));
      }
      else if (action.equals("Submit Store Data")) {
         ActionErrors ae = ItemMgrMasterLogic.verifyStoreData(request,sForm);
         if(ae.size()>0) {
            saveErrors(request, ae);
            return (mapping.findForward("ext"));
         }
         return (mapping.findForward("main"));
      }
      //Navigate through distributor list
      else if (action.equals("goPage")) {
        ItemMgrMasterLogic.goPage(request, sForm);
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
