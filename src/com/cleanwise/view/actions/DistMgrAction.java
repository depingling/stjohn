
package com.cleanwise.view.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.logic.DistMgrLogic;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.DistMgrDetailForm;
/**
 *  Implementation of <strong>Action</strong> that processes the Dist manager
 *  page.
 *
 *@author     dvieira
 *@created    August 9, 2001
 */
public final class DistMgrAction extends ActionSuper {

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
          if(form instanceof DistMgrDetailForm &&
            ((DistMgrDetailForm)form).getManufacturerId()!=0) {
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
        String deleteStr = getMessage(mr,request,"global.action.label.delete");
	String searchStr = getMessage(mr,request,"global.action.label.search");
	String viewallStr = getMessage(mr,request,"admin.button.viewall");
	String createStr = getMessage(mr,request,"admin.button.create");

	    // Process the action
        try {

            if (action.equals("init")) {
                DistMgrLogic.init(request, form);
                return (mapping.findForward("success"));
            }
            else if (action.equals(searchStr)) {
                DistMgrLogic.search(request, form);
                return (mapping.findForward("success"));
            }
            else if (action.equals(viewallStr)) {
                DistMgrLogic.getAll(request, form);
                return (mapping.findForward("success"));
            }
            else if (action.equals(createStr)) {
                DistMgrLogic.addDistributor(request, form);
                return (mapping.findForward("distdetail"));
            }
            else if (action.equals("updatedist") || action.equals(saveStr)) {
                DistMgrDetailForm sForm = (DistMgrDetailForm) form;
                int id = sForm.getIntId();
		        ActionErrors ae = DistMgrLogic.updateDistributor(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("distdetail"));
                }
                if(id > 0){
                    //exsiting distributor
                    return (mapping.findForward("distdetail"));
                }else{
                    //new distributor
                    return (mapping.findForward("distDetailCreateSuccess"));
                }
            }
            else if (action.equals("distdetail")) {
                DistMgrLogic.getDetail(request, form);                
                return (mapping.findForward("distdetail"));
            }
            else if (action.equals("edit_ship_from")) {
                return (mapping.findForward("distdetail"));
            }
            else if (action.equals(deleteStr)) {
                ActionErrors ae = 
		    DistMgrLogic.delete(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
		    return (mapping.findForward("distdetail"));
                }
                return (mapping.findForward("main"));
            }
	    else if (action.equals("Delete Ship From Address")) {
                ActionErrors ae = 
		    DistMgrLogic.deleteShipFromAddress(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
		return (mapping.findForward("distdetail"));
            }
	    else if (action.equals("Update Ship From Address")) {
		
                ActionErrors ae = 
		    DistMgrLogic.updateShipFromAddress(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
		return (mapping.findForward("distdetail"));
            }
	    else if (action.equals("Add Ship From Address")) {
                ActionErrors ae = 
		    DistMgrLogic.addShipFromAddress(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
		return (mapping.findForward("distdetail"));
            }
	    else if (action.equals("sort")) {
                DistMgrLogic.sort(request, form);
                return (mapping.findForward("success"));
            }
	    else if (action.equals("sortShipFrom")) {
                DistMgrLogic.sortShipFrom(request, form);
                return (mapping.findForward("success"));
            }
  	    else if (action.equals("Add Contact") ||
		     action.equals("Add Branch Contact")) {
		        ActionErrors ae =
		    DistMgrLogic.prepareAddContact(request, form);
		        if (ae.size() > 0) {
		            saveErrors(request, ae);
                }
		return (mapping.findForward("distdetail"));
            }
  	    else if (action.equals("Edit Contact") ||
		     action.equals("Edit Branch Contact") ) {
                ActionErrors ae = 
		    DistMgrLogic.prepareEditContact(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
		return (mapping.findForward("distdetail"));
            }
  	    else if (action.equals("Delete Contact") ||
		     action.equals("Delete Branch Contact") ) {
                ActionErrors ae = 
		    DistMgrLogic.deleteAdditionalContact(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
		return (mapping.findForward("distdetail"));
            }
  	    else if (action.equals("Save Contact") ||
		     action.equals("Save Branch Contact") ) {
                ActionErrors ae = 
		    DistMgrLogic.saveAdditionalContact(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
		return (mapping.findForward("distdetail"));                
            }
            else if (action.equals("AddPrimaryManufacturer")) {
                ActionErrors ae = 
		    DistMgrLogic.addPriamryManufacturer(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
		return (mapping.findForward("distdetail"));
            }
  	    else if (action.equals("DeletePrimaryManufacturer")) {
                ActionErrors ae = 
		    DistMgrLogic.deletePriamryManufacturer(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
		return (mapping.findForward("distdetail"));
            }
  	    else if (action.equals("Add Branch Address")) {
                ActionErrors ae = 
		    DistMgrLogic.prepareAddBranchAddress(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
		return (mapping.findForward("distdetail"));
            }
  	    else if (action.equals("Edit Branch Address")) {
                ActionErrors ae = 
		    DistMgrLogic.prepareEditBranchAddress(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
		return (mapping.findForward("distdetail"));
            }
  	    else if (action.equals("Delete Branch Address")) {
                ActionErrors ae = 
		    DistMgrLogic.deleteBranchAddress(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
		return (mapping.findForward("distdetail"));
            }
  	    else if (action.equals("Save Branch Address")) {
                ActionErrors ae = 
		    DistMgrLogic.saveBranchAddress(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
		return (mapping.findForward("distdetail"));                
            }
          else if (action.equals("listFreightHandlers")) {
                ActionErrors ae = DistMgrLogic.getAllFreightHandlers(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("success"));
            }
            else 
            if (action.equals("createFreightHandler")) {
                ActionErrors ae = DistMgrLogic.createNewFreightHandler(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("fhdetail"));
            }
            else 
            if (action.equals("saveFreightHandler")) {
                ActionErrors ae = DistMgrLogic.saveFreightHandler(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("fhdetail"));
            }
            else 
            if (action.equals("fhdetail")) {
                ActionErrors ae = DistMgrLogic.fetchFreightHandlerDetail(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("fhdetail"));
            }
            else {
                DistMgrLogic.init(request, form);
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

