
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
import com.cleanwise.view.logic.DistItemMgrLogic;
import com.cleanwise.view.logic.DistMgrLogic;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.*;

import java.util.Enumeration;
/**
 * Implementation of <strong>Action</strong> that saves a new
 * catalog detail or updates an existing catalog detail.
 */
public final class DistItemMgrAction extends ActionSuper {


    public ActionForward performSub(
				 ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
	throws IOException, ServletException {


	String action  = request.getParameter("action");
	HttpSession session = request.getSession();

	// Is there a currently logged on user?
	SessionTool st = new SessionTool(request);
	if ( st.checkSession() == false ) {
	    return mapping.findForward("/userportal/logon");
	}   
    

	ItemMgrSearchForm sForm = (ItemMgrSearchForm) form;
	if(action==null) { action = "init"; }
	try {
	    if(action.equals("init") ) {
		DistItemMgrLogic.init(request,sForm);
	    } 
	    else if(action.equals("Search") ) {
  	       ActionErrors ae = DistItemMgrLogic.search(request,sForm);
           if (ae.size() > 0) {
             saveErrors(request, ae);
             return (mapping.findForward("init"));
           }
	    }
            else if(action.equals("showItem")){
              //we link from the catalog master item screen directly to the distributor screen, so we must gather up the info a user would have gathered if they had done through the steps to get there manually.

              String querty = request.getParameter("distSku");
              String distId = request.getParameter("searchField");
              
              DistMgrSearchForm srchForm = new DistMgrSearchForm();
              srchForm.setSearchField(distId);
              srchForm.setSearchType("id");
              DistMgrLogic.search(request, srchForm); 

              DistMgrDetailForm dForm = new DistMgrDetailForm();
              dForm.setDistNumber(distId);              
              DistMgrLogic.getDetail(request, dForm);

              sForm.setDistributorId(distId);
              sForm.setSkuType("Distributor");
              sForm.setSkuTempl(querty);
              DistItemMgrLogic.init(request,sForm);
              DistItemMgrLogic.search(request, sForm);

            }
	    else if (action.equals("sort")) {
		DistItemMgrLogic.sort(request, sForm);
	    }
	    else if (action.equals("Update")) {
		ActionErrors ae = DistItemMgrLogic.updateItem(request, sForm);
		if ( ae.size() > 0 ) {
		    saveErrors(request, ae);
   return (mapping.findForward("init"));
		}
		DistItemMgrLogic.search(request,sForm);
	    }
	    else if (action.equals("Clear")) {
		ActionErrors ae = DistItemMgrLogic.clearItem(request, sForm);
		if ( ae.size() > 0 ) {
		    saveErrors(request, ae);
		    return (mapping.findForward("init"));
		}
		DistItemMgrLogic.search(request,sForm);
	    }

	} catch (Exception e) {
	    request.setAttribute("errorobject", e);
	    e.printStackTrace();
	    return (mapping.findForward("error"));
	}

	return (mapping.findForward("init"));
    }
}
