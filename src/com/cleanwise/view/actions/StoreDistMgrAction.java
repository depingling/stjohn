
package com.cleanwise.view.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.cleanwise.view.forms.StoreDistMgrDetailForm;
import com.cleanwise.view.forms.StoreDistMgrSearchForm;
import com.cleanwise.view.logic.StoreDistMgrLogic;
import com.cleanwise.view.utils.SessionAttributes;
import com.cleanwise.view.utils.SessionTool;
/**
 *  Implementation of <strong>Action</strong> that processes the Dist manager
 *  page.
 *
 *@author     Veronika Denega
 */
public final class StoreDistMgrAction extends ActionSuper {

    // ----------------------------------------------------- Public Methods

    /**
     *  Process the specified HTTP request, and create the corresponding HTTP
     *  response (or forward to another web component that will create it).
     *  Return an <code>ActionForward</code> instance describing where and how
     *  control should be forwarded, or <code>null</code> if the response has
     *  already been completed.
     *
     *@param  mapping               The ActionMapping used to select this
     *      instance
     *@param  request               The HTTP request we are processing
     *@param  response              The HTTP response we are creating
     *@param  form                  Description of Parameter
     *@return                       Description of the Returned Value
     *@exception  IOException       if an input/output error occurs
     *@exception  ServletException  if a servlet exception occurs
     */
    public ActionForward performSub(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
             throws IOException, ServletException {
        // Determine the store manager action to be performed
        String action = request.getParameter("action");
        if (action == null) {
          if(form instanceof StoreDistMgrDetailForm &&
            ((StoreDistMgrDetailForm)form).getManufacturerId()!=0) {
            action = "AddPrimaryManufacturer";
          } else {
            action = "init";
          }
        }
        action.trim();

        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
          return mapping.findForward("/userportal/logon");
        }

        MessageResources mr = getResources(request);

        // Get the form buttons as specified in the properties file.
        String saveStr = getMessage(mr,request,"global.action.label.save");
      //  String saveConfig = getMessage(mr,request,"admin.button.saveStoreDistConfig");
        String deleteStr = getMessage(mr,request,"global.action.label.delete");
        String searchStr = getMessage(mr,request,"global.action.label.search");
        String createStr = getMessage(mr,request,"admin.button.create");

        // Process the action
        try {

            if (action.equals("init")) {
                StoreDistMgrLogic.init(request, form);
                return (mapping.findForward("success"));
            }
            else if (action.equals(searchStr)) {
                StoreDistMgrLogic.search(request, form);
                return (mapping.findForward("success"));
            }
            else if (action.equals(createStr)) {
                StoreDistMgrLogic.addDistributor(request, form);
                return (mapping.findForward("distdetail"));
            }
            else if (action.equals("updatedist") || action.equals(saveStr)) {
                StoreDistMgrDetailForm sForm = (StoreDistMgrDetailForm) form;
                int id = sForm.getIntId();
                ActionErrors ae = StoreDistMgrLogic.updateDistributor(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("distdetail"));
                }
                if(id > 0){
                    //exsiting distributor
                    return (mapping.findForward("distdetail"));
                }else{
                    //new distributor
                    ActionForward forward = new ActionForward(mapping.findForward("distDetailCreateSuccess"));
                    forward.setPath(forward.getPath() + "&searchField=" + sForm.getId());
                    return forward;
                }
            }
/*            else if (action.equals(saveConfig)) {
                ActionErrors ae = StoreDistMgrLogic.updateDistributorConfiguration(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("distconf"));
            }*/
            else if (action.equals("distdetail")) {
                ActionErrors ae=  StoreDistMgrLogic.getDetail(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("distdetail"));
                }
                return (mapping.findForward("distdetail"));
            }
            else if (action.equals("edit_ship_from")) {
                return (mapping.findForward("distdetail"));
            }
            else if (action.equals(deleteStr)) {
                ActionErrors ae = StoreDistMgrLogic.delete(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("distdetail"));
                }
                return (mapping.findForward("main"));
            }
            else if (action.equals("Delete Ship From Address")) {
                ActionErrors ae =  StoreDistMgrLogic.deleteShipFromAddress(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("distdetail"));
            }
            else if (action.equals("Update Ship From Address")) {
              ActionErrors ae =  StoreDistMgrLogic.updateShipFromAddress(request, form);
              if (ae.size() > 0) {
                    saveErrors(request, ae);
              }
              return (mapping.findForward("distdetail"));
            }
            else if (action.equals("Add Ship From Address")) {
                ActionErrors ae =  StoreDistMgrLogic.addShipFromAddress(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("distdetail"));
            }
            else if (action.equals("sort")) {
                StoreDistMgrLogic.sort(request, form);
                return (mapping.findForward("success"));
            }
            else if (action.equals("sortShipFrom")) {
                StoreDistMgrLogic.sortShipFrom(request, form);
                return (mapping.findForward("success"));
            }
            else if (action.equals("Add Contact") || action.equals("Add Branch Contact")) {
                ActionErrors ae =  StoreDistMgrLogic.prepareAddContact(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("distdetail"));
            }
            else if (action.equals("Edit Contact") || action.equals("Edit Branch Contact") ) {
                ActionErrors ae =   StoreDistMgrLogic.prepareEditContact(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("distdetail"));
            }
            else if (action.equals("Delete Contact") || action.equals("Delete Branch Contact") ) {
                ActionErrors ae = StoreDistMgrLogic.deleteAdditionalContact(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("distdetail"));
            }
            else if (action.equals("Save Contact") || action.equals("Save Branch Contact") ) {
                ActionErrors ae = StoreDistMgrLogic.saveAdditionalContact(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("distdetail"));
            }
            else if (action.equals("AddPrimaryManufacturer")) {
                ActionErrors ae =  StoreDistMgrLogic.addPriamryManufacturer(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("distconf"));
            }

            else if("Return Selected".equals(action)){
                String submitFormIdent = request.getParameter("jspSubmitIdent");
                if (form instanceof StoreDistMgrDetailForm) {
                    if(submitFormIdent!=null &&  submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_MANUF_FORM)==0){
                        ActionErrors ae = StoreDistMgrLogic.addPriamryManufacturer(request, form);
                    }
                    StoreDistMgrLogic.resetSelectBox(request, form);
                    return (mapping.findForward("distconf"));
                } else if (form instanceof StoreDistMgrSearchForm) {
                    if(submitFormIdent!=null &&  submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_GROUP_FORM)==0){
                        ActionErrors ae = StoreDistMgrLogic.returnSelectedGroups(request, form);
                    }
                    return (mapping.findForward("success"));
                }
                return (mapping.findForward("success"));
            }

            else if (action.equals("DeletePrimaryManufacturer")) {
                ActionErrors ae =  StoreDistMgrLogic.deletePriamryManufacturer(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("distconf"));
            }
            else if (action.equals("Add Branch Address")) {
                ActionErrors ae = StoreDistMgrLogic.prepareAddBranchAddress(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("distdetail"));
            }
            else if (action.equals("Edit Branch Address")) {
                ActionErrors ae =  StoreDistMgrLogic.prepareEditBranchAddress(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("distdetail"));
            }
            else if (action.equals("Delete Branch Address")) {
                ActionErrors ae =  StoreDistMgrLogic.deleteBranchAddress(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("distdetail"));
            }
            else if (action.equals("Save Branch Address")) {
                ActionErrors ae = StoreDistMgrLogic.saveBranchAddress(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("distdetail"));
            }
            else if (action.equals("listFreightHandlers")) {
                ActionErrors ae = StoreDistMgrLogic.getAllFreightHandlers(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("success"));
            }
            else
            if (action.equals("createFreightHandler")) {
                ActionErrors ae = StoreDistMgrLogic.createNewFreightHandler(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("fhdetail"));
            }
            else
            if (action.equals("saveFreightHandler")) {
                ActionErrors ae = StoreDistMgrLogic.saveFreightHandler(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("fhdetail"));
            }
            else
            if (action.equals("fhdetail")) {
                ActionErrors ae = StoreDistMgrLogic.fetchFreightHandlerDetail(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("fhdetail"));
            }
            else {
                StoreDistMgrLogic.init(request, form);
                return (mapping.findForward("success"));
            }
        }
        catch (Exception e) {
          e.printStackTrace();
            if(e instanceof IOException) {
               throw (IOException)e;
            }else if(e instanceof ServletException ) {
               throw (ServletException) e;
            } else {
               throw new ServletException(e.getMessage());
            }
            //return (mapping.findForward("failure"));
        }

    }

}

