
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
import com.cleanwise.view.logic.CatalogMgrLogic;
import com.cleanwise.view.forms.CatalogMgrDetailForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;


/**
 * Implementation of <strong>Action</strong> that saves a new
 * catalog detail or updates an existing catalog detail.
 */
public final class CatalogMgrDetailAction extends ActionSuper {


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

    HttpSession session = request.getSession();
    CatalogMgrDetailForm detailForm = 
	(CatalogMgrDetailForm) session.getAttribute("CATALOG_DETAIL_FORM");
                     
    // Get the action and the catalogId from the request.
    String action  = request.getParameter("action");
    if (action == null) action = "add";
    String catalogId = request.getParameter("id");
    if (catalogId == null) catalogId = "";

    try {

      // Add a new catalog.
      if (action.equals("add")) {
        CatalogMgrLogic.addCatalog(request, form);
        return (mapping.findForward("display"));
      }

      // Edit an existing catalog.
      else if (action.equals("edit")) {
        CatalogMgrLogic.editCatalog(request, form, catalogId);
        return (mapping.findForward("display"));
      }

      // Edit catalog structure
      else if (action.equals("Edit Catalog Structure")) {        
        session.setAttribute("CATALOG_ID",new Integer(25));
        return (mapping.findForward("structure"));
      }


      // Delete catalog
      else if (action.equals("Delete Catalog")) {
        CatalogMgrLogic.deleteCatalog(request, form);
        return (mapping.findForward("return"));
      }

      // Save the new or updated catalog.
      else if (action.equals("Save Catalog")) {
        ActionErrors ae = CatalogMgrLogic.saveCatalog(request, form);
        if(ae.size()>0) {
          saveErrors(request, ae);
          return (mapping.findForward("failure"));
        }
        return (mapping.findForward("save"));
      }
      // Validate the catalog.
      else if (action.equals("Validate Catalog")) {
        ActionErrors ae = CatalogMgrLogic.validateCatalog(request, form);
        if(ae.size()>0) {
          saveErrors(request, ae);
          return (mapping.findForward("failure"));
        }
        return (mapping.findForward("validate"));
      }        

      else if (action.equals("Create Catalog")) {
        ActionErrors ae = CatalogMgrLogic.saveCatalog(request, form);
        if(ae.size()>0) {
          saveErrors(request, ae);
          return (new ActionForward(mapping.getInput()));
        }
        ActionForward af=mapping.findForward("edit");
        String path = af.getPath();
        CatalogMgrDetailForm sForm = (CatalogMgrDetailForm) form;
        path +="&id="+sForm.getDetail().getCatalogId();
        ActionForward af1=  new ActionForward(path);
        af1.setRedirect(af.getRedirect());
        return (af1);
      }

      // Sort the item results
      else if (action.equals("sort")) {	            
          CatalogMgrLogic.sort(request, detailForm);
          return (mapping.findForward("display"));
      }


      // Users should never get here, but if they do, perform an add.
      else {
        CatalogMgrLogic.addCatalog(request, form);
        return (mapping.findForward("display"));
      }

    }

    // Catch all exceptions here.
    catch (Exception e) {
      request.setAttribute("errorobject", e);
      return (mapping.findForward("error"));
    }

  }


}
