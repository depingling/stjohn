/*
 * StoreSiteMgrAction.java
 *
 * Created on March 20, 2006, 6:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

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
import com.cleanwise.view.logic.StoreSiteMgrLogic;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.util.Utility;

/**
 *
 * @author Ykupershmidt
 */
public class StoreSiteMgrAction extends ActionSuper {


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
    HttpServletResponse response) throws IOException, ServletException {

    // Determine the store manager action to be performed
    String action = request.getParameter("action");
    String copyBudgetsAction = null;
    String budgetForSelectedYear = null;
    if (action == null) {
    	 copyBudgetsAction = request.getParameter("copyBudgetsFromPreviousYear");
    	 budgetForSelectedYear = request.getParameter("siteBudgetsForSelectedYear");
    	 if(copyBudgetsAction==null && budgetForSelectedYear==null) {
    		 action = "init";
    	 } else if(copyBudgetsAction!=null) {
    		 action = copyBudgetsAction;
    	 } else if(budgetForSelectedYear!=null) {
    		 action = budgetForSelectedYear;
    	 }
    }

    SessionTool st = new SessionTool(request);

    // Is there a currently logged on user?
    if (st.checkSession() == false) {
      return mapping.findForward("/userportal/logon");
    }

    MessageResources mr = getResources(request);

    // Get the form buttons as specified in the properties file.
    String saveStr = getMessage(mr, request, "global.action.label.save");
    String deleteStr = getMessage(mr, request, "global.action.label.delete");
    String searchStr = getMessage(mr, request, "global.action.label.search");
    String createStr = getMessage(mr, request, "admin.button.create");
    String cloneStr = getMessage(mr, request, "admin.button.createClone");

    // Determine if doing site configuration
    boolean siteConfig =  Utility.isTrue(request.getParameter("siteconfig"), false);

    // Process the action
    try {
      if (action.equals("init")) {
        if (siteConfig) {
          // we're doing site config
          StoreSiteMgrLogic.initConfig(request, form);
        } else {
          StoreSiteMgrLogic.init(request, form);
        }
        return (mapping.findForward("success"));
      } else if ("Return Selected".equals(action)) {
        String submitFormIdent = request.getParameter("jspSubmitIdent");
        if (submitFormIdent != null &&
            submitFormIdent.indexOf("#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_ACCOUNT_FORM) ==  0) {
          StoreSiteMgrLogic.setSiteAccountFields(request, form);
        }
        return (mapping.findForward("success"));
      } else if (action.equals(searchStr)) {
        if (siteConfig) {
          StoreSiteMgrLogic.searchConfig(request, form);
        } else {
          StoreSiteMgrLogic.search(request, form);
        }
        return (mapping.findForward("success"));
      } else if (action.equals(createStr)) {
        StoreSiteMgrLogic.addSite(request, form);
        return (mapping.findForward("sitedetail"));
      } else if (action.equals(cloneStr)) {
        StoreSiteMgrLogic.cloneSite(request, form);
        return (mapping.findForward("success"));
      } else if (action.equals("site_workflow")) {
        StoreSiteMgrLogic.fetchSiteWorkflow(request, form);
        return (mapping.findForward("success"));
      }
      else if(action.equals("Assign"))
      {
      ActionErrors ae = StoreSiteMgrLogic.linkSites(request, form);
       if(ae.size()>0)
       {
         saveErrors(request,ae);
       }
       return (mapping.findForward("success"));
      }
      else if(action.equals("sortWorkflows"))
      {
       StoreSiteMgrLogic.sortWorkflows(request,form);
       StoreSiteMgrLogic.setAssignedToSelectedWorkflowId(request,form);
       return (mapping.findForward("success"));
      }

      else if (action.equals("updatesite") ||
                 action.equals(saveStr)) {
        if (siteConfig) {
          StoreSiteMgrLogic.updateConfig(request, form);
          return (mapping.findForward("success"));
        } else {
          ActionErrors ae = StoreSiteMgrLogic.updateSite(request, form);
          if (ae.size() > 0) {
            saveErrors(request, ae);
          }
          return (mapping.findForward("sitedetail"));
        }
      } else if (action.equals("sitedetail")) {
        StoreSiteMgrLogic.getDetail(request, form);
        return (mapping.findForward("sitedetail"));
      } else if (action.equals("sitebudgets")) { 
    	  StoreSiteMgrLogic.resetSelectedFiscalYear(form);
    	  ActionErrors ae = StoreSiteMgrLogic.getBudgets(request, form);
    	  if (ae.size() > 0) {
              saveErrors(request, ae);
          } 
        return (mapping.findForward("sitebudgets"));
      } else if (action.equals("siteBudgetsForSelectedYear")){
    	  ActionErrors ae = StoreSiteMgrLogic.getBudgets(request, form);
    	  if (ae.size() > 0) {
              saveErrors(request, ae);
          } 
          return (mapping.findForward("sitebudgets"));
      }
      else if (action.equals("Set budgets") || action.equals("Set All Unlimited")) {
          ActionErrors ae = null;
          if(action.equals("Set All Unlimited")) {
              ae = StoreSiteMgrLogic.setAllUnlimited(request, form);
          }
          if(ae!=null && ae.size()>0) {
        	  saveErrors(request, ae);
          }else {
              ae = StoreSiteMgrLogic.setBudgets(request, form);
              if (ae.size() > 0) {
                  saveErrors(request, ae);
	          } else {
	              ae = StoreSiteMgrLogic.getBudgets(request, form);
	              if (ae.size() > 0) {
	                  saveErrors(request, ae);
	              }
	          }
          }
        return (mapping.findForward("sitebudgets"));
      } else if(action.equals("Copy Budgets From Previous Year")) { //4601
          ActionErrors ae = StoreSiteMgrLogic.copyBudgetsFromPreviousYear(request, form);
          if (ae.size() > 0) {
              saveErrors(request, ae);
          }
          StoreSiteMgrLogic.getBudgets(request, form);
          return (mapping.findForward("sitebudgets"));
      }
      else if (action.equals(deleteStr)) {
        ActionErrors ae = StoreSiteMgrLogic.delete(request, form);
        if (ae.size() > 0) {
          saveErrors(request, ae);
          return (mapping.findForward("sitedetail"));
        }
        return (mapping.findForward("main"));
      } else if (action.equals("sort")) {
        StoreSiteMgrLogic.sort(request, form);
        return (mapping.findForward("success"));
      } else if (action.equals("inventory_update")) {
        ActionErrors ae = StoreSiteMgrLogic.lookupInventoryData(request, form);
        if (ae.size() > 0) {
          saveErrors(request, ae);
        }
        return (mapping.findForward("success"));
      } else if (action.equals("update_site_inventory")) {
        ActionErrors ae = StoreSiteMgrLogic.updateInventoryData(request, form);
        if (ae.size() > 0) {
          saveErrors(request, ae);
        }
        return (mapping.findForward("success"));
      } else if (action.equals("update_site_controls")) {
        ActionErrors ae = StoreSiteMgrLogic.updateShoppingControls
                          (request, form);
        if (ae.size() > 0) {
          saveErrors(request, ae);
        }
        return (mapping.findForward("success"));
      } else if (action.equals("list.all.bsc")) {
        ActionErrors ae = StoreSiteMgrLogic.listAll(action, request, form);
        if (ae.size() > 0) {
          saveErrors(request, ae);
        }
        return (mapping.findForward("success"));
      } else if (action.equals("bscRelationships")) {
        ActionErrors ae = StoreSiteMgrLogic.listBscRelationships
                          (action, request, form);
        if (ae.size() > 0) {
          saveErrors(request, ae);
        }
        return (mapping.findForward("bscRelationships"));
      } else if (action.equals("bscdetail")) {
        return (mapping.findForward("bscdetail"));
      } else if (action.equals("bsc_modify") ||
                 action.equals("bsc_add")) {
        StoreSiteMgrLogic.updateBSC(request, form);
        return (mapping.findForward("bscdetail"));
      } else if (action.equals("saveFieldValues")) {
        StoreSiteMgrLogic.saveFieldValues(request, form);
        return (mapping.findForward("success"));
      } else {
        StoreSiteMgrLogic.init(request, form);
        return (mapping.findForward("success"));
      }
    } catch (Exception e) {
      e.printStackTrace();
      ActionErrors ae = new ActionErrors();
      ae.add(ActionErrors.GLOBAL_ERROR,
             new ActionError("error.genericError",
                             "Uncaught Exception: " + e.getMessage()));
      saveErrors(request, ae);
      return (mapping.findForward("failure"));
    }

  }

}


