/*

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
import com.cleanwise.view.logic.CostCenterMgrLogic;
import javax.servlet.http.HttpSession;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.*;

*/
/**
 *  Implementation of <strong>Action</strong> that processes the Account manager
 *  page.
 *
 *@author     tbesser
 *@created    August 27, 2001
 */
/*
public final class CostCenterMgrDetailAction extends ActionBase {

    // ----------------------------------------------------- Public Methods

    */
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
/*
    public ActionForward performAction(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
             throws Exception {

        
                 
        // Determine the cost center manager action to be performed
        String action = request.getParameter("action");
        if (action == null) {
            action = "init";
        }
        MessageResources mr = getResources(request);

        // Get the form buttons as specified in the properties file.
        String saveStr = getMessage(mr,request,"global.action.label.save");
        String deleteStr = getMessage(mr,request,"global.action.label.delete");
	String searchStr = getMessage(mr,request,"global.action.label.search");
	String viewallStr = getMessage(mr,request,"admin.button.viewall");
	String createStr = getMessage(mr,request,"admin.button.create");
	String saveBudgetStr = getMessage(mr,request,"costcenter.button.savebudgets");

        // Process the action
        
        if (action.equals("init")) {
            CostCenterMgrLogic.init(request, form);
            return (mapping.findForward("success"));
        }
        else if (action.equals(searchStr)) {
            CostCenterMgrLogic.searchSites(request, form);
            return (mapping.findForward("success"));
        }
        else if (action.equals(viewallStr) || action.equals("viewall")) {
            CostCenterMgrLogic.getAllSites(request, form);
            return (mapping.findForward("success"));
        }
        else if (action.equals(createStr)) {
            CostCenterMgrLogic.addCostCenter(request, form);
            return (mapping.findForward("costcenterdetail"));
        }
        else if (action.equals("updatecostcenter") ||
                action.equals(saveStr)) {

            ActionErrors ae = 
                CostCenterMgrLogic.updateCostCenter(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return (mapping.findForward("costcenterdetail"));
        }
        else if (action.equals(saveBudgetStr)) {
            ActionErrors ae = 
                CostCenterMgrLogic.updateBudgets(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return (mapping.findForward("costcenterdetail"));
        }
        else if (action.equals("costcenterdetail")) {
            CostCenterMgrLogic.getDetail(request, form);
            return (mapping.findForward("costcenterdetail"));
        }
        else if (action.equals(deleteStr)) {
            ActionErrors ae = 
                CostCenterMgrLogic.delete(request, form);
            CostCenterMgrLogic.init(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return (mapping.findForward("costcenterdetail"));
            }
            return (mapping.findForward("main"));
        }
        else if (action.equals("sort")) {
            CostCenterMgrLogic.sort(request, form);
            return (mapping.findForward("success"));
        }
        else {
            CostCenterMgrLogic.init(request, form);
            return (mapping.findForward("success"));
        }


    }

}

*/
