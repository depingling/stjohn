/*
 * StoreFreightMgrAction.java
 *
 * Created on August 1, 2005, 2:44 PM
 */

package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.view.forms.StoreFreightMgrForm;
import com.cleanwise.view.forms.StoreItemOrderGuideMgrForm;
import com.cleanwise.view.logic.StoreFreightMgrLogic;
import com.cleanwise.view.logic.StoreItemCatalogMgrLogic;
import com.cleanwise.view.utils.*;


/**
 *
 * @author Ykupershmidt
 */
public class StoreFreightSearchMgrAction  extends ActionSuper {
  

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
    String action = request.getParameter("action");
    if (action == null) action = "init";

    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }   
    
    StoreFreightMgrForm sForm = (StoreFreightMgrForm) form;
    String mappingAction = "success";
    ActionErrors ae = new ActionErrors();
    // Process the action
    try {
      if (action.equals("init")) {
        StoreFreightMgrLogic.init(request, form);
        return (mapping.findForward("success"));
      }
      else if (action.equals("search")) {
        StoreFreightMgrLogic.search(request, form);
        return (mapping.findForward("success"));
      }
      else if (action.equals("sort")) {
        StoreFreightMgrLogic.sort(request, form);
        return (mapping.findForward("success"));
      }
      else if("Return Selected".equals(action)){
    	  String submitFormIdent = request.getParameter("jspSubmitIdent");
    	  if(submitFormIdent!=null &&  submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_DIST_FORM)==0){
        		  ae = StoreFreightMgrLogic.setDistFilter(request, sForm);
          }
    	  if(submitFormIdent!=null &&  submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_CATALOG_FORM)==0){  
    		  		ae = StoreFreightMgrLogic.setCatalogFilter(request, form);
    	  }
		  return (mapping.findForward("success"));
		  
	  }else if(action.equals("Clear Catalog Filter")){
		  ae = StoreFreightMgrLogic.clearCatalogFilter(request, form);
		  return (mapping.findForward("success"));
	  }else if(action.equals("Clear Distributor Filter")){
		  ae = StoreFreightMgrLogic.clearDistributorFilter(request, form);
		  return (mapping.findForward("success"));
	  }
      else {
        StoreFreightMgrLogic.init(request, form);
        return (mapping.findForward("success"));
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      return (mapping.findForward("failure"));
    }
  }
}
