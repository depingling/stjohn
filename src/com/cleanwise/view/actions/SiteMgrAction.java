
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
import com.cleanwise.view.logic.SiteMgrLogic;
import com.cleanwise.view.utils.*;


/**
 *  Implementation of <strong>Action</strong> that processes the Site manager
 *  page.
 *
 *@author     dvieira
 *@created    August 15, 2001
 */
public final class SiteMgrAction extends ActionSuper {

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
            action = "init";
        }

        SessionTool st = new SessionTool(request);

        // Is there a currently logged on user?
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

        // Determine if doing site configuration
        boolean siteConfig = false;
        String siteConfigS = request.getParameter("siteconfig");
        if (siteConfigS != null && siteConfigS.equals("true")) {
            siteConfig = true;
        }

        // Process the action
        try {

            if (action.equals("init")) {
                if (siteConfig) {
                    // we're doing site config
                    SiteMgrLogic.initConfig(request, form);
                } else {
                    SiteMgrLogic.init(request, form);
                }
                return (mapping.findForward("success"));
            }
            else if (action.equals(searchStr)) {
                if (siteConfig) {
                    SiteMgrLogic.searchConfig(request, form);
                } else {
                    SiteMgrLogic.search(request, form);
                }
                return (mapping.findForward("success"));
            }
            else if (action.equals(viewallStr)) {
                if (siteConfig) {
                    SiteMgrLogic.getAllConfig(request, form);
                } else {
                    SiteMgrLogic.getAll(request, form);
                }
                return (mapping.findForward("success"));
            }
            else if (action.equals(createStr)) {
                SiteMgrLogic.addSite(request, form);
                return (mapping.findForward("sitedetail"));
            }
            else if (action.equals("site_workflow")) {
                SiteMgrLogic.fetchSiteWorkflow(request, form);
                return (mapping.findForward("success"));
            }
            else if (action.equals("updatesite") ||
                     action.equals(saveStr)) {

                if (siteConfig) {
                    SiteMgrLogic.updateConfig(request, form);
                    return (mapping.findForward("success"));
                } else {
                    ActionErrors ae = SiteMgrLogic.updateSite(request, form);
                    if (ae.size() > 0) {
                        saveErrors(request, ae);
                    }
                    return (mapping.findForward("sitedetail"));
                }
            }
            else if (action.equals("sitedetail")) {
                SiteMgrLogic.getDetail(request, form);
                return (mapping.findForward("sitedetail"));
            }
            else if (action.equals("sitebudgets")) {
                SiteMgrLogic.getBudgets(request, form);
                return (mapping.findForward("sitebudgets"));
            }
            else if (action.equals("Set budgets")) {
		        SiteMgrLogic.setBudgets(request, form);
		        SiteMgrLogic.getBudgets(request, form);
		        return (mapping.findForward("sitebudgets"));
            }
            else if (action.equals(deleteStr)) {
                ActionErrors ae = SiteMgrLogic.delete(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("sitedetail"));
                }
                return (mapping.findForward("main"));
            }
            else if (action.equals("sort")) {
                SiteMgrLogic.sort(request, form);
                return (mapping.findForward("success"));
            }
            else if (action.equals("inventory_update")) {
                ActionErrors ae = SiteMgrLogic.lookupInventoryData(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("success"));
            }
            else if (action.equals("update_site_inventory") ) {
                ActionErrors ae = SiteMgrLogic.updateInventoryData(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("success"));
            }
            else if (action.equals("update_site_controls") ) {
                ActionErrors ae = SiteMgrLogic.updateShoppingRestrictions(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("success"));
            }
            else if (action.equals("list.all.bsc") ) {
                ActionErrors ae = SiteMgrLogic.listAll(action, request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("success"));
            }
            else if (action.equals("bscRelationships"))
            {
                ActionErrors ae = SiteMgrLogic.listBscRelationships
                    (action, request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("bscRelationships"));
            }
            else if (action.equals("bscdetail"))
            {
                return (mapping.findForward("bscdetail"));
            }
            else if (action.equals("bsc_modify") ||
                action.equals("bsc_add"))
            {
                SiteMgrLogic.updateBSC(request, form);
                return (mapping.findForward("bscdetail"));
            }
            else if (action.equals("saveFieldValues"))
            {
                SiteMgrLogic.saveFieldValues(request, form);
		return (mapping.findForward("success"));
            }
            else {
                SiteMgrLogic.init(request, form);
                return (mapping.findForward("success"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            ActionErrors ae = new ActionErrors();
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.genericError","Uncaught Exception: "+e.getMessage()));
            saveErrors(request,ae);
            return (mapping.findForward("failure"));
        }

    }

}

