
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
import com.cleanwise.view.logic.StoreCatalogMgrLogic;
import com.cleanwise.view.forms.StoreCatalogMgrForm;
import com.cleanwise.view.logic.LocateStoreAccountLogic;
import com.cleanwise.view.forms.LocateStoreAccountForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;


public final class StoreCatalogMgrAction extends ActionSuper {


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
    StoreCatalogMgrForm sForm = (StoreCatalogMgrForm) form;

    org.apache.struts.action.ActionMapping mActionConfig = 
        (org.apache.struts.action.ActionMapping) request.getAttribute("org.apache.struts.action.mapping.instance");
    String mappingPath = mActionConfig.getPath();
    if(mappingPath.indexOf("storecatalogdet")>=0) {
      sForm.setCatalogProcessFl(true);
    } else {
      sForm.setCatalogProcessFl(false);
    }

 
    // Get the action and the catalogId from the request.
    String action  = request.getParameter("action");
    sForm.setAction(action);

    String mappingAction = "success";
    ActionErrors ae = new ActionErrors();
    
      
     try{
      if(action==null) {
     	  ae = StoreCatalogMgrLogic.init(request,sForm);
      } 
      else if(action.equals("Search")) {
     	  ae = StoreCatalogMgrLogic.search(request,sForm);
      }      
      else if(action.equals("sort")) {
     	  StoreCatalogMgrLogic.sort(request,sForm);
      }      
      else if(action.equals("Set Catalog Filter")) {
     	  ae = StoreCatalogMgrLogic.setCatalogFilter(request,sForm);
      }
      else if(action.equals("edit")) {
     	  ae = StoreCatalogMgrLogic.editCatalog(request,sForm);
      }
      else if(action.equals("Save Catalog")) {
     	  ae = StoreCatalogMgrLogic.saveCatalog(request,sForm);
      }
      else if(action.equals("Create Catalog")) {
     	  ae = StoreCatalogMgrLogic.saveCatalog(request,sForm);
      }
      else if(action.equals("Create New")) {
     	  ae = StoreCatalogMgrLogic.addCatalog(request,sForm);
      }
      else if(action.equals("Clone Catalog")) {
     	  ae = StoreCatalogMgrLogic.cloneCatalog(request,sForm);
      }
      else if(action.equals("Add Order Guide")) {
     	  ae = StoreCatalogMgrLogic.addOrderGuide(request,sForm);
      }
      else if(action.equals("Delete Catalog")) {
     	  ae = StoreCatalogMgrLogic.deleteCatalog(request,sForm);
        if(ae.size()==0) {
          mappingAction = "deleted";
        }
      }

    }catch (Exception e) {
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
