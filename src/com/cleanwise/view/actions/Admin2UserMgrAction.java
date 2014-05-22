package com.cleanwise.view.actions;


import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.MessageResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.forms.Admin2UserMgrForm;
import com.cleanwise.view.forms.Admin2UserDetailMgrForm;
import com.cleanwise.view.forms.Admin2UserProfileMgrForm;
import com.cleanwise.view.forms.Admin2UserConfigMgrForm;
import com.cleanwise.view.logic.Admin2UserMgrLogic;

import java.io.IOException;
import java.util.List;

/**
 * Date:         29.06.2009
 * Time:         13:19:03
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class Admin2UserMgrAction  extends ActionSuper {

    private static final Logger log = Logger.getLogger(Admin2UserMgrAction.class);

    private static final String ERROR = "error";
    private static final String SUCCESS = "success";

    public static final String INIT = "init";
    public static final String DETAIL = "userdetail";
    public static final String CHANGE_USER_TYPE = "changeUserType";
    public static final String INIT_CONFIG = "initConfig";
    public static final String SORT = "sort";

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
            if (form instanceof Admin2UserMgrForm) {
                forwardPage = processAction(action, (Admin2UserMgrForm) form, request);
            } else if (form instanceof Admin2UserDetailMgrForm) {
                forwardPage = processAction(action, (Admin2UserDetailMgrForm) form, request);
            } else if (form instanceof Admin2UserProfileMgrForm) {
                forwardPage = processAction(action, (Admin2UserProfileMgrForm) form, request);
            } else if (form instanceof Admin2UserConfigMgrForm) {
                forwardPage = processAction(action, (Admin2UserConfigMgrForm) form, request);
            } else {
                log.info("processAction => Unknown form : " + form);
            }
        }
        catch (Exception e) {
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

    private String processAction(String pAction, Admin2UserConfigMgrForm pForm, HttpServletRequest request) throws Exception {

        MessageResources mr = getResources(request);

        String searchConfig = getMessage(mr, request, "global.action.label.search");
        String saveUserAccounts = getMessage(mr, request, "admin2.button.saveUserAccounts");
        String saveUserSites = getMessage(mr, request, "admin2.button.saveUserSites");
        String configureAllSites = getMessage(mr, request, "admin2.user.config.accounts.text.configureAllSites");
        String configureAllAccounts = getMessage(mr, request, "admin2.user.config.accounts.text.configureAllAccounts");
        String saveUserGroups = getMessage(mr, request, "admin2.button.saveUserGroups");
        String saveUserDistr = getMessage(mr, request, "admin2.button.saveUserDistr");
        String saveUserServiceProvider = getMessage(mr, request, "admin2.button.saveUserServiceProviders");

        ActionErrors ae;
        if (INIT.equals(pAction)) {

            ae = Admin2UserMgrLogic.init(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }

            return SUCCESS;

        } else if (INIT_CONFIG.equals(pAction)) {

            ae = Admin2UserMgrLogic.initConfig(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }

            return SUCCESS;

        } else if (pAction.equals(searchConfig)) {

            ae = Admin2UserMgrLogic.searchConfig(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }

            return SUCCESS;

        } else if (pAction.equals(saveUserAccounts)) {

            ae = Admin2UserMgrLogic.updateAccountConfig(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }

            return SUCCESS;

        } else if (pAction.equals(configureAllSites)) {

            ae = Admin2UserMgrLogic.configureAllAccountSites(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }

            return SUCCESS;

        } else if (pAction.equals(configureAllAccounts)) {

            ae = Admin2UserMgrLogic.configureAllAccounts(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }

            return SUCCESS;

        } else if (pAction.equals(saveUserSites)) {

            ae = Admin2UserMgrLogic.updateSiteConfig(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }

            return SUCCESS;
        } else if (pAction.equals(saveUserGroups)) {

            ae = Admin2UserMgrLogic.updateGroupConfig(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }

            return SUCCESS;
        } else if (pAction.equals(saveUserDistr)) {

            ae = Admin2UserMgrLogic.updateUserDistConfig(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }

            return SUCCESS;
        } else if (pAction.equals(saveUserServiceProvider)) {

            ae = Admin2UserMgrLogic.updateServiceProviderConfig(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }

            return SUCCESS;
        }


        return SUCCESS;
    }

    private String processAction(String pAction, Admin2UserProfileMgrForm pForm, HttpServletRequest request) throws Exception {

        MessageResources mr = getResources(request);
        String saveStr = getMessage(mr, request, "global.action.label.save");

        ActionErrors ae;
        if (INIT.equals(pAction)) {

            ae = Admin2UserMgrLogic.profileDetail(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;

        } else if (pAction.equals(saveStr)) {

            ae = Admin2UserMgrLogic.updateProfile(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;

        }

        return SUCCESS;
    }

    private String processAction(String pAction,
                                 Admin2UserMgrForm pForm,
                                 HttpServletRequest request) throws Exception {

        MessageResources mr = getResources(request);

        String searchStr = getMessage(mr, request, "global.action.label.search");
        String createCloneStr = getMessage(mr,request,"admin2.button.createClone");
        String createStr = getMessage(mr,request,"global.action.label.create");

        ActionErrors ae;
        if (INIT.equals(pAction)) {

            ae = Admin2UserMgrLogic.init(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;

        } else if (pAction.equals(searchStr)) {

            ae = Admin2UserMgrLogic.search(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;

        } else if (SORT.equals(pAction)) {

            ae = Admin2UserMgrLogic.sort(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;

        } else if (pAction.equals(createStr)) {

            ae = Admin2UserMgrLogic.addUser(request);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return DETAIL;

        }  else if (pAction.equals(createCloneStr)) {

            ae = Admin2UserMgrLogic.clone(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return DETAIL;

        }

        return SUCCESS;
    }

    private String processAction(String pAction,
                                 Admin2UserDetailMgrForm pForm,
                                 HttpServletRequest request) throws Exception {

        MessageResources mr = getResources(request);
        String saveStr = getMessage(mr, request, "global.action.label.save");

        ActionErrors ae;
        if (INIT.equals(pAction)) {

            ae = Admin2UserMgrLogic.init(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;

        } else if (DETAIL.equals(pAction)) {

            ae = Admin2UserMgrLogic.detail(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;

        } else if (CHANGE_USER_TYPE.equals(pAction)) {

            ae = Admin2UserMgrLogic.changeUserType(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;

        } else if (pAction.equals(saveStr)) {

            ae = Admin2UserMgrLogic.updateUser(request, pForm);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;

        }
        return SUCCESS;

    }


}
