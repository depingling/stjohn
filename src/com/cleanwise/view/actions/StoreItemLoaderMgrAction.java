/*
 * StoreItemLoaderMgrAction.java
 *
 * Created on August 18, 2005, 9:54 AM
 */

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
import com.cleanwise.view.logic.StoreItemLoaderMgrLogic;
import com.cleanwise.view.forms.StoreItemLoaderMgrForm;
import com.cleanwise.view.logic.LocateStoreCatalogLogic;
import com.cleanwise.view.forms.LocateStoreCatalogForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;



/**
 *
 * @author Ykupershmidt
 */
public class StoreItemLoaderMgrAction  extends ActionSuper {
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
    StoreItemLoaderMgrForm sForm = (StoreItemLoaderMgrForm) form;

    // Get the action and the catalogId from the request.
    String action  = request.getParameter("action");
    sForm.setAction(action);

    String mappingAction = "success";
    ActionErrors ae = new ActionErrors();

    try {
      if("Locate Catalog".equals(action)) {
        //StoreItemCatalogMgrLogic.clearSelectBox(request,sForm);
      }
      if(action==null) {
         ae = StoreItemLoaderMgrLogic.init(request,sForm);
      }
      else if("Return Selected".equals(action)){
        //  String submitFormIdent = request.getParameter("jspSubmitIdent");
        //  if(submitFormIdent!=null &&  submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_ITEM_FORM)==0){
        //      ae = StoreItemCatalogMgrLogic.setItemFilter(request,sForm);
        //  }
        //  ae = StoreItemCatalogMgrLogic.loadAggrData (request,form);
        //  StoreItemCatalogMgrLogic.resetSelectBox(request,sForm);
      }
      else if(action.equals("Upload")) {
         ae = StoreItemLoaderMgrLogic.uploadFile(request,sForm);
      }
      else if(action.equals("Get Subtable")) {
         ae = StoreItemLoaderMgrLogic.getTable(request,sForm);
      }
      else if(action.equals("Full Table")) {
         ae = StoreItemLoaderMgrLogic.fullTable(request,sForm);
      }
      else if(action.equals("Save")) {
         ae = StoreItemLoaderMgrLogic.saveTable(request,sForm);
      }
      else if(action.equals("Show Matched")) {
         ae = StoreItemLoaderMgrLogic.showMatched(request,sForm);
      }
      else if(action.equals("Match")) {
         ae = StoreItemLoaderMgrLogic.saveTable(request,sForm);
        if(ae.size()==0) {
           ae = StoreItemLoaderMgrLogic.match(request,sForm);
        }
      }
      else if(action.equals("Search")) {
         ae = StoreItemLoaderMgrLogic.search(request,sForm);
      }
      else if(action.equals("edit")) {
         ae = StoreItemLoaderMgrLogic.edit(request,sForm);
      }
      else if(action.equals("Select")) {
         ae = StoreItemLoaderMgrLogic.reloadTable(request,sForm);
      }
      else if(action.equals("Select Update")) {
         ae = StoreItemLoaderMgrLogic.getToUpdate(request,sForm);
      }
      else if(action.equals("Update Values")) {
         ae = StoreItemLoaderMgrLogic.updateValues(request,sForm);
      }
      else if(action.equals("Cancel")) {
         ae = StoreItemLoaderMgrLogic.clearValues(request,sForm);
      }
      else if(action.equals("Reload")) {
         ae = StoreItemLoaderMgrLogic.reloadTable(request,sForm);
      }
      else if(action.equals("Assign Skus")) {
         ae = StoreItemLoaderMgrLogic.assignSkus(request,sForm);
      }

      else if(action.equals("Remove Assignment")) {
         ae = StoreItemLoaderMgrLogic.removeAssignment(request,sForm);
      }
      else if(action.equals("Create Skus")) {
         ae = StoreItemLoaderMgrLogic.createSkus(request,sForm);
      }
      else if(action.equals("Update Skus")) {
        ae = StoreItemLoaderMgrLogic.updateSkus(request,sForm);
      }
      else if(action.equals("editreturn")) {
         ae = StoreItemLoaderMgrLogic.updateSku(request,sForm);
      }
      else if(action.equals("edit-item")) {
         ae = StoreItemLoaderMgrLogic.editSku(request,sForm);
        if(ae.size()==0) {
          return (mapping.findForward("edit-item"));
        }
      }



    }

    // Catch all exceptions here.
    catch (Exception e) {
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
