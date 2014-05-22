
package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.logic.StoreDistItemMgrLogic;
import com.cleanwise.view.logic.StoreDistMgrLogic;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.*;
import org.apache.struts.util.MessageResources;

/**
 * Implementation of <strong>Action</strong> for Store Administrator
 * Distributor Item Edit page.
 */
public final class StoreDistItemMgrAction extends ActionSuper {


public ActionForward performSub(
         ActionMapping mapping,
         ActionForm form,
         HttpServletRequest request,
         HttpServletResponse response)
  throws IOException, ServletException {


  String action  = request.getParameter("action");

  // Is there a currently logged on user?
  SessionTool st = new SessionTool(request);
  if ( st.checkSession() == false ) {
    return mapping.findForward("/userportal/logon");
  }

  StoreItemMgrSearchForm sForm = (StoreItemMgrSearchForm) form;
  if (action == null) {
    action = "init";
  }

  try {
    if (action.equals("init") ) {
      StoreDistItemMgrLogic.init(request, sForm);
    }
    else if (action.equals("SearchItem")) {
      ActionErrors ae = StoreDistItemMgrLogic.search(request, sForm);
      if (ae.size() > 0) {
        saveErrors(request, ae);
        return (mapping.findForward("init"));
      }
    }
    else if(action.equals("showItem")){
      // we link from the catalog master item screen directly to the distributor screen,
      // so we must gather up the info a user would have gathered if they had done through the steps
      // to get there manually.

      String querty = request.getParameter("distSku");
      String distId = request.getParameter("searchField");

      StoreDistMgrSearchForm srchForm = new StoreDistMgrSearchForm();
      srchForm.setSearchField(distId);
      srchForm.setSearchType("id");
      StoreDistMgrLogic.search(request, srchForm);

      StoreDistMgrDetailForm dForm = new StoreDistMgrDetailForm();
      dForm.setDistNumber(distId);
      StoreDistMgrLogic.getDetail(request, dForm);

      sForm.setDistributorId(distId);
      sForm.setSkuType("Distributor");
      sForm.setSkuTempl(querty);
      StoreDistItemMgrLogic.init(request,sForm);
      StoreDistItemMgrLogic.search(request, sForm);

    }
    else if (action.equals("sort")) {
      StoreDistItemMgrLogic.sort(request, sForm);
    }
    else if (action.equals("Update")) {
      ActionErrors ae = StoreDistItemMgrLogic.updateItem(request, sForm);
      if ( ae.size() > 0 ) {
        saveErrors(request, ae);
        return (mapping.findForward("init"));
      }
      StoreDistItemMgrLogic.search(request,sForm);
    }
    else if (action.equals("Clear")) {
      ActionErrors ae = StoreDistItemMgrLogic.clearItem(request, sForm);
      if ( ae.size() > 0 ) {
        saveErrors(request, ae);
        return (mapping.findForward("init"));
      }
      StoreDistItemMgrLogic.search(request,sForm);
    }
    else if("Return Selected".equals(action)){
      String submitFormIdent = request.getParameter("jspSubmitIdent");
      if(submitFormIdent!=null &&
         submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_MANUF_FORM)==0){
      StoreDistItemMgrLogic.setSelectedManufacture(request, sForm);
      }else
      {
       StoreDistItemMgrLogic.closeLocateManufForm(request, sForm);
      }
        return (mapping.findForward("success"));
    }
    else if("Loc".equals(action) || "Locate Manufacturer".equals(action)){

     sForm.setReturnFormNum(request.getParameter("formNum"));
     sForm.setFeedField(request.getParameter("feedField"));
     sForm.setFieldDesc(request.getParameter("fieldDesc"));
   }
  } catch (Exception e) {
    request.setAttribute("errorobject", e);
    e.printStackTrace();
    return (mapping.findForward("error"));
  }
  return (mapping.findForward("init"));
}

}
