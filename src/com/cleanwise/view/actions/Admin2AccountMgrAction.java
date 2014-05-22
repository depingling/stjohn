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
import org.apache.log4j.Logger;


import com.cleanwise.view.forms.Admin2AccountConfigurationForm;
import com.cleanwise.view.forms.Admin2AccountMgrDetailForm;
import com.cleanwise.view.forms.Admin2AccountMgrForm;
import com.cleanwise.view.logic.Admin2AccountConfigurationLogic;
import com.cleanwise.view.logic.Admin2AccountMgrLogic;
import com.cleanwise.view.utils.*;

public class Admin2AccountMgrAction extends ActionSuper {

    private static final Logger log = Logger.getLogger(Admin2AccountMgrAction.class);

    private static final String ERROR = "error";
    private static final String SUCCESS = "success";
    private static final String INIT = "init";
    private static final String DETAIL = "accountdetail";
    private static final String UNK_ACTION = "unkaction";
    private static final String REDIRECT_TO_DETAIL = "redirectToDetail";

    /* Process the specified HTTP request, and create the corresponding HTTP
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
            log.error(e.getMessage(), e);
            e.printStackTrace();
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

        String forwardPage;

        log.info("processAction => action : " + action + " form : " + form + " mapping attribute : " + mapping.getAttribute());
        if (form instanceof Admin2AccountMgrForm) {
            forwardPage = processAction(action, (Admin2AccountMgrForm) form, request);
        } else if (form instanceof Admin2AccountMgrDetailForm) {
            forwardPage = processAction(action, (Admin2AccountMgrDetailForm) form, request);
        } else if (form instanceof Admin2AccountConfigurationForm) {
            forwardPage = processAction(action, (Admin2AccountConfigurationForm) form, request);
        } else {
            forwardPage = ERROR;
            log.info("processAction => Unknown form : " + form);
        }

        if (forwardPage == null) {
            return null;
        }

        log.info("processAction => Forward page :" + mapping.findForward(forwardPage).getPath());

        return mapping.findForward(forwardPage);
    }

    private String processAction(String pAction, Admin2AccountMgrForm pForm, HttpServletRequest request) throws Exception {

        MessageResources mr = getResources(request);

        String searchStr = getMessage(mr, request, "global.action.label.search");
        String createStr = getMessage(mr, request, "global.action.label.create");
        
        ActionErrors ae;
        if (pAction.equals(searchStr)) {
            ae = Admin2AccountMgrLogic.search(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;
        } else if (pAction.equals(createStr)) {
            ae = Admin2AccountMgrLogic.addAccount(request, null);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return DETAIL;
        } else if (DETAIL.equals(pAction)) {
            ae = Admin2AccountMgrLogic.getDetail(request, null);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return DETAIL;
        } else if (INIT.equals(pAction)) {

            CleanwiseUser appUser = ShopTool.getCurrentUser(request);
            if (appUser.isaAccountAdmin()) {
                return REDIRECT_TO_DETAIL;
            }

            ae = Admin2AccountMgrLogic.init(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }

            return SUCCESS;

        }

        return UNK_ACTION;

    }

    private String processAction(String pAction, Admin2AccountMgrDetailForm pForm, HttpServletRequest request) throws Exception {
        MessageResources mr = getResources(request);

        // Get the form buttons as specified in the properties file.
        String saveStr = getMessage(mr, request, "global.action.label.save");
        String createStr = getMessage(mr, request, "global.action.label.create");
        String cloneStr = getMessage(mr, request, "admin2.button.createClone");
        String  editFiscalCalStr = "editFiscalCalendarData";
        String  saveFiscalCalStr = getMessage(mr,request,"admin2.button.saveFiscalCal");
        String  createFiscalCalStr = getMessage(mr,request,"admin2.button.createFiscalCal");
        if (DETAIL.equals(pAction)) {
            ActionErrors ae = Admin2AccountMgrLogic.getDetail(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;
        } else if (pAction.equals(saveStr)) {
            ActionErrors ae = Admin2AccountMgrLogic.updateAccount(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;
        } else if (pAction.equals(cloneStr)) {
            ActionErrors ae = Admin2AccountMgrLogic.cloneAccountData(request, pForm);
            log.info("processAction => ae: " + ae);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;
        } else if (pAction.equals(createStr)) {
            ActionErrors ae = Admin2AccountMgrLogic.addAccount(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;
        } else if (INIT.equals(pAction)) {
        	ActionErrors ae = Admin2AccountMgrLogic.init(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;
        }
        else if (editFiscalCalStr.equals(pAction)) {
            ActionErrors ae = Admin2AccountMgrLogic.editAccountFiscalCal(request, pForm);
            if(ae.size()>0) {
                saveErrors(request,ae);return SUCCESS;
            }  
        } else if (createFiscalCalStr.equals(pAction)) {
        	ActionErrors ae = Admin2AccountMgrLogic.createAccountFiscalCal(request, pForm);
            if(ae.size()>0) {
                saveErrors(request,ae);return SUCCESS;
            }  
        } else if ( saveFiscalCalStr.equals(pAction)){
        	ActionErrors ae = Admin2AccountMgrLogic.updateAccountFiscalCal(request, pForm);
            if(ae.size()>0) {
                saveErrors(request,ae);return SUCCESS;
            }
        }

        return SUCCESS;

    }

    private String processAction(String pAction, Admin2AccountConfigurationForm pForm, HttpServletRequest request) throws Exception {

        ActionErrors ae;

        MessageResources mr = getResources(request);
        String searchStr = getMessage(mr, request, "global.action.label.search");

        if (searchStr.equals(pAction)) {
            ae = Admin2AccountConfigurationLogic.search(pForm, request);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
        } else if (INIT.equals(pAction)) {
            ae = Admin2AccountConfigurationLogic.init(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
        } else {
            Admin2AccountConfigurationLogic.resetConfigs(pForm);
        }

        return SUCCESS;
    }

}

