
package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.logic.CorporateScheduleMgrLogic;
import com.cleanwise.view.forms.CorporateScheduleMgrForm;
import com.cleanwise.view.utils.Constants;
import org.apache.struts.action.ActionErrors;

/**
 * Implementation of <strong>Action</strong> that processes the
 * Store Distributor Delivery Shedule manager page.
 */
public final class CorporateScheduleMgrAction extends ActionSuper {

    private static final String className = "CorporateScheduleMgrAction";

    // ----------------------------------------------------- Public Methods


    /**
     */
    public ActionForward performSub(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        try {

            // Determine the schedule manager action to be performed
        	CorporateScheduleMgrForm theForm = (CorporateScheduleMgrForm) form;
            String action = request.getParameter("action");
            
            String configFunction = request.getParameter("confFunction");
            boolean siteConfig = false;
            if ("true".equals(request.getParameter("siteconfig")) ||
                "siteConfig".equals(configFunction)) {
              siteConfig = true;
            }
            boolean acctConfig = false;
            if ("true".equals(request.getParameter("acctconfig"))|| 
                "acctConfig".equals(configFunction)) {
              acctConfig = true;
            }

            if (action == null){
                action = "init";
            }
            String command = request.getParameter("command");

            String scheduleRuleChange = request.getParameter("scheduleRuleChange");
            if(scheduleRuleChange!=null && scheduleRuleChange.trim().length()>0) {
                return (mapping.findForward("success"));
            }

            // Process the action
            if (action.equals("init")) {
            	if("true".equals(request.getParameter("initconfig"))) {
            		CorporateScheduleMgrLogic.initConfig(request, theForm);
            	} else if (siteConfig) {
            		CorporateScheduleMgrLogic.initSiteConfig(request, theForm);
            	} else if(acctConfig) {
            		CorporateScheduleMgrLogic.initAcctConfig(request, theForm);
            	} else {           
            		ActionErrors ae = CorporateScheduleMgrLogic.initSearch(request, theForm);
            		if(ae.size()>0) {
            			saveErrors(request, ae);
            			return (mapping.findForward("failure"));
            		}
            	}
            }
            else if (action.equals("Search")) {
            	if (siteConfig) {
            		ActionErrors ae = CorporateScheduleMgrLogic.searchSitesToConfig(request, theForm);
            		if(ae.size()>0) {
            			saveErrors(request, ae);
            		}
            	} else if(acctConfig){
            		ActionErrors ae = CorporateScheduleMgrLogic.searchAcctToConfig(request, theForm);
            		if(ae.size()>0) {
            			saveErrors(request, ae);
            		}
            	} else {
            		ActionErrors ae = CorporateScheduleMgrLogic.searchSchedule(request, theForm);
            		if(ae.size()>0) {
            			saveErrors(request, ae);
            			return (mapping.findForward("failure"));
            		}
            	}
            }
            else if (action.equals("Create New")) {
                ActionErrors ae = CorporateScheduleMgrLogic.createNew(request, theForm);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                } else {
                    return (mapping.findForward("createnew"));
                }
            }
            else if (action.equals("Save")) {
                ActionErrors ae = theForm.validate(mapping, request);
                if(ae!=null && ae.size()>0) {
                  saveErrors(request,ae);
                  return (mapping.findForward("failure"));
                }
                ae = CorporateScheduleMgrLogic.save(request, theForm);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
            }
            else if (action.equals("Find Accounts")) {
                ActionErrors ae = CorporateScheduleMgrLogic.accountSearch(request, theForm);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
            }
            else if (action.equals("Save Accounts")) {
                ActionErrors ae = CorporateScheduleMgrLogic.updateAcctConfigured(request, theForm);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
            }

            else if (action.equals("detail")) {
                ActionErrors ae = CorporateScheduleMgrLogic.detail(request, theForm);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
            }
            else if (action.equals("sort")) {
                ActionErrors ae = CorporateScheduleMgrLogic.sort(request, theForm);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
            }
            else if (action.equals("Delete")) {
                ActionErrors ae = CorporateScheduleMgrLogic.delete(request, theForm);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
                return (mapping.findForward("deletesuccess"));
            }
            else if (action.equals("Save Schedule Accounts")) {
                ActionErrors ae = CorporateScheduleMgrLogic.updateAccountConfig(request, theForm);        
                if (ae.size() > 0 ) {
                  saveErrors(request, ae);
                }              
            }
            else if (action.equals("Save Schedule Sites")) {
                ActionErrors ae = CorporateScheduleMgrLogic.updateSiteConfig(request, theForm);        
                if (ae.size() > 0 ) {
                  saveErrors(request, ae);
                }              
            }
            else if (action.equals("Configure All Accounts") && acctConfig){
            	ActionErrors ae = CorporateScheduleMgrLogic.configureAllAccounts(request, theForm);
            	if(ae.size()>0){
            		saveErrors(request, ae);
            		return (mapping.findForward("failure"));
            	}
            } else if (action.equals("Configure All Sites") && acctConfig){
            	ActionErrors ae = CorporateScheduleMgrLogic.configureAllAccountSites(request, theForm);
            	if(ae.size()>0){
            		saveErrors(request, ae);
            		return (mapping.findForward("failure"));
            	}
            }
            return (mapping.findForward("success"));
        } catch ( Exception e ) {
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            e.printStackTrace();
            return mapping.findForward("error");
        }
    }

}
