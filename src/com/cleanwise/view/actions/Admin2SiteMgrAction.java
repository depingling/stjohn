package com.cleanwise.view.actions;

import com.cleanwise.view.forms.*;
import com.cleanwise.view.logic.Admin2SiteMgrLogic;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.SessionAttributes;
import org.apache.log4j.Logger;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class Admin2SiteMgrAction extends ActionSuper {

    private static final Logger log = Logger.getLogger(Admin2SiteMgrAction.class);

    private static final String ERROR = "error";
    private static final String SUCCESS = "success";

    private static final String INIT = "init";
    private static final String DETAIL = "sitedetail";
    private static final String CREATE = "create";
    private static final String INIT_CONFIG = "initConfig";
    private static final String SORT_WORKFLOW = "sortWorkflows";
    private static final String ASSIGN_WORKFLOW = "Assign";
    private static final String RETURN_SELECTED = "Return Selected";
    private static final String SEARCH = "search";

    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @param form     Description of Parameter
     * @return Description of the Returned Value
     * @throws java.io.IOException            if an input/output error occurs
     * @throws javax.servlet.ServletException if a servlet exception occurs
     */
    public ActionForward performSub(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws IOException, ServletException {


        log.info("performSub => BEGIN");

        // Determine the admin2.0 manager action to be performed
        String action = request.getParameter("action");
        if (action == null) {
            action = INIT;
        }

        log.info("performSub => action: " + action);

        SessionTool st = new SessionTool(request);
        if (!st.checkSession()) {
            logm("session timeout " + st.paramDebugString());
            return mapping.findForward(st.getLogonMapping());
        }

        ActionForward actionForward;
        try {
            actionForward = processAction(action, form, request, mapping, response);
        } catch (Exception e) {
            log.error("performSub => ERROR: " + e.getMessage(), e);
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            return mapping.findForward(Constants.ADMIN2_SYSTEM_ERROR);
        }
        log.info("performSub => END.");

        return actionForward;
    }

    private ActionForward processAction(String action,
                                        ActionForm form,
                                        HttpServletRequest request,
                                        ActionMapping mapping,
                                        HttpServletResponse response) throws Exception {

        String forwardPage = SUCCESS;
        log.info("processAction => action : " + action + " form : " + form + " mapping attribute : " + mapping.getAttribute());
        try {
            if (form instanceof Admin2SiteMgrForm) {
                forwardPage = processAction(action, (Admin2SiteMgrForm) form, request);
            } else if (form instanceof Admin2SiteDetailMgrForm) {
                forwardPage = processAction(action, (Admin2SiteDetailMgrForm) form, request);
            } else if (form instanceof Admin2SiteWorkflowMgrForm) {
                  forwardPage = processAction(action, (Admin2SiteWorkflowMgrForm) form, request);
            } else if (form instanceof Admin2SiteBudgetMgrForm) {
                  forwardPage = processAction(action, (Admin2SiteBudgetMgrForm) form, request);
            } else if (form instanceof Admin2SiteConfigMgrForm) {
                  forwardPage = processAction(action, (Admin2SiteConfigMgrForm) form, request);
            } else {
                log.info("processAction => Unknown form : " + form);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            return mapping.findForward(Constants.ADMIN2_SYSTEM_ERROR);
        }

        if (forwardPage == null) {
            return null;
        }

        log.info("processAction => Forward page :" + mapping.findForward(forwardPage).getPath());

        return mapping.findForward(forwardPage);
    }

    private String processAction(String pAction, Admin2SiteBudgetMgrForm pForm, HttpServletRequest request) throws Exception {

        MessageResources mr = getResources(request);

        String updateBudget = getMessage(mr, request, "admin2.button.updateBudgets");

        ActionErrors ae;
        if (INIT.equals(pAction)) {
            ae = Admin2SiteMgrLogic.getBudgets(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;
        } else if (pAction.equalsIgnoreCase(updateBudget)) {
            ae = Admin2SiteMgrLogic.updateBudgets(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;
        }

        return SUCCESS;
    }

    private String processAction(String pAction,
                                 Admin2SiteMgrForm pForm,
                                 HttpServletRequest request) throws Exception {

        MessageResources mr = getResources(request);

        String searchStr = getMessage(mr, request, "global.action.label.search");

        ActionErrors ae;
        if (INIT.equals(pAction)) {

            ae = Admin2SiteMgrLogic.init(pForm, request);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;

        } else if (searchStr.equals(pAction)) {

            ae = Admin2SiteMgrLogic.search(pForm, request);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;

        }
        return SUCCESS;
    }

    private String processAction(String pAction,
                                 Admin2SiteDetailMgrForm pForm,
                                 HttpServletRequest request) throws Exception {

        MessageResources mr = getResources(request);

        String saveStr = getMessage(mr, request, "global.action.label.save");
        String createCloneWithStr = getMessage(mr, request, "admin2.button.createCloneWith");
        String createCloneWithoutStr = getMessage(mr, request, "admin2.button.createCloneWithout");
        String deleteStr = getMessage(mr, request, "global.action.label.delete");

        ActionErrors ae;
        if (INIT.equals(pAction)) {

            ae = Admin2SiteMgrLogic.init(pForm, request);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;

        } else if (DETAIL.equals(pAction)) {

            ae = Admin2SiteMgrLogic.getDetail(pForm, request);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;

         } else if (saveStr.equals(pAction)) {

            ae = Admin2SiteMgrLogic.update(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;

        } else if (CREATE.equals(pAction)) {

            ae = Admin2SiteMgrLogic.create(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }

            return SUCCESS;

        } else if (createCloneWithStr.equals(pAction)) {
            ae = Admin2SiteMgrLogic.cloneSiteWithRelationships(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }

            return SUCCESS;

        } else if (createCloneWithoutStr.equals(pAction)) {
            ae = Admin2SiteMgrLogic.cloneSiteWithoutRelationships(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }

            return SUCCESS;

        } else if (deleteStr.equals(pAction)) {

            ae = Admin2SiteMgrLogic.delete(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }

            return SEARCH;

        } else if (RETURN_SELECTED.equals(pAction)) {
            String submitFormIdent = request.getParameter("jspSubmitIdent");
            if (submitFormIdent != null && submitFormIdent.indexOf("#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_ACCOUNT_FORM) == 0) {
                Admin2SiteMgrLogic.initAccountFields(request, pForm);
            }
            return SUCCESS;
        }

        return SUCCESS;

    }

    private String processAction(String pAction, Admin2SiteConfigMgrForm pForm, HttpServletRequest request) throws Exception {

        MessageResources mr = getResources(request);

        String searchConfig = getMessage(mr, request, "global.action.label.search");
        String saveSiteUsers = getMessage(mr, request, "admin2.button.saveSiteUsers");
        String saveSiteCatalog = getMessage(mr, request, "admin2.button.saveSiteCatalog");

        ActionErrors ae;
        if (INIT.equals(pAction)) {

            ae = Admin2SiteMgrLogic.init(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }

            return SUCCESS;

        } else if (INIT_CONFIG.equals(pAction)) {

            ae = Admin2SiteMgrLogic.initConfig(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }

            return SUCCESS;

        } else if (pAction.equals(searchConfig)) {

            ae = Admin2SiteMgrLogic.searchConfig(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }

            return SUCCESS;

        } else if (pAction.equals(saveSiteUsers)) {

            ae = Admin2SiteMgrLogic.updateSiteUserConfig(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }

            return SUCCESS;

        } else if (pAction.equals(saveSiteCatalog)) {

            ae = Admin2SiteMgrLogic.updateSiteCatalogConfig(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }

            return SUCCESS;
        }

        return SUCCESS;
    }

    private String processAction(String pAction,
                                 Admin2SiteWorkflowMgrForm pForm,
                                 HttpServletRequest request) throws Exception {

        MessageResources mr = getResources(request);
        String assignStr = getMessage(mr, request, "admin2.site.workflow.button.assign");

        ActionErrors ae;
        if (INIT.equals(pAction)) {

            ae = Admin2SiteMgrLogic.workflow(pForm, request);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;
       } else if(assignStr.equals(pAction)) {
           ae = Admin2SiteMgrLogic.linkSites(request, pForm);
           if(ae.size()>0)
           {
             saveErrors(request,ae);
             return ERROR;
           }
           return SUCCESS;

        } else if(SORT_WORKFLOW.equals(pAction))  {

          Admin2SiteMgrLogic.sortWorkflows(request,pForm);
          Admin2SiteMgrLogic.setAssignedToSelectedWorkflowId(request,pForm);
          return SUCCESS;
        }
        return SUCCESS;
    }


}
