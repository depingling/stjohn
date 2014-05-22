
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
import com.cleanwise.view.forms.ItemMgrCatalogForm;
import com.cleanwise.view.logic.ItemMgrCatalogLogic;

import java.util.Enumeration;
/**
 * Implementation of <strong>Action</strong> that saves a new
 * catalog detail or updates an existing catalog detail.
 */
public final class ItemMgrCatalogAction extends ActionSuper {


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

    ItemMgrCatalogForm sForm = (ItemMgrCatalogForm) form;
    try {

      //Edit existing item
      if(action.equals("edit")) {
        ActionErrors ae=ItemMgrCatalogLogic.initEdit(request,sForm);
        if(ae.size()>0) {
          saveErrors(request, ae);
          ActionForward af=mapping.findForward(sForm.getRetAction());
          String path = af.getPath();
          ActionForward af1=  new ActionForward(path);
          af1.setRedirect(af.getRedirect());
          return (af1);
        }
      }

      //Saves data to database and returns to previous page
      else if(action.equals("Save Item")) {
        ActionErrors ae = ItemMgrCatalogLogic.saveCatalogProduct(request,sForm);
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

    // Catch all exceptions here.
    } catch (Exception e) {
      request.setAttribute("errorobject", e);
      e.printStackTrace();
      return (mapping.findForward("error"));
    }

    return (mapping.findForward("init"));

/*
    <html:submit property="action" value="Delete Subtree"/>
    <html:submit property="action" value="Delete Node"/>
    <html:submit property="action" value="Add Category"/>
    <html:submit property="action" value="Copy To Edit"/>
    <html:submit property="action" value="Replace Category"/>
    <html:submit property="action" value="Move Subtree"/>
    <html:submit property="action" value="Copy Subtree"/>

*/
  }


}
