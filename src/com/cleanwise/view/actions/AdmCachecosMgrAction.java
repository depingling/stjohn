package com.cleanwise.view.actions;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import java.io.IOException;

import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.forms.AdmCachecosMgrForm;
import com.cleanwise.view.logic.AdmCachecosMgrLogic;

/**
 * Title:        AdmCachecosMgrAction
 * Description:  Actions manager for the CACHECOS management in the ADMINPORTAL.
 * Purpose:      Class for calling logic methods  which  process the request.
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * Date:         14.01.2009
 * Time:         19:30:19
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class AdmCachecosMgrAction extends ActionSuper {

    private static final String FAILURE           = "failure";
    private static final String MAIN              = "main";
    private static final String SUCCESS           = "success";
    private static final String DISPLAY           = "display";
    private static final String ERROR             = "error";
    private static final String CACHE_DESCRIPTION = "description";
    private static final String CACHE_MANAGEMENT  = "management";

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

        // Determine the store manager action to be performed
        String action = request.getParameter("action");
        if (action == null) {
            action = "init";
        }

        SessionTool st = new SessionTool(request);
        if (!st.checkSession()) {
            logm("session timeout " + st.paramDebugString());
            return mapping.findForward(st.getLogonMapping());
        }

        ActionForward actionForward;
        try {
            actionForward = runWorkerForm(action, form, request, mapping, response);
        } catch (Exception e) {
            e.printStackTrace();
            actionForward = mapping.findForward(FAILURE);
        }


        return actionForward;
    }

    private ActionForward runWorkerForm(String action,
                                        ActionForm form,
                                        HttpServletRequest request,
                                        ActionMapping mapping,
                                        HttpServletResponse response) {

        String forward_page = MAIN;
        logm("action : " + action + " form : " + form + " mapping attribute : " + mapping.getAttribute());
        try {
            if (form instanceof AdmCachecosMgrForm)
                forward_page = admCachecosMgrFormWorker(action, (AdmCachecosMgrForm) form, request);
            else logm("The worker of the form can't be found.Unknown form : " + form);
        }
        catch (Exception e) {
            logm("ERROR");
            e.printStackTrace();
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            return mapping.findForward(ERROR);
        }

        if (forward_page == null) {
            return null;
        }

        logm("Forward page :" + mapping.findForward(forward_page).getPath());

        return mapping.findForward(forward_page);

    }


    private String admCachecosMgrFormWorker(String action, AdmCachecosMgrForm form, HttpServletRequest request) throws Exception {

        String init = "init";
        String cacheDescription = "description";
        String cacheManagement = "management";
        String executeOp = "executeOp";

        if (init.equals(action)) {
            ActionErrors ae = AdmCachecosMgrLogic.init(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return SUCCESS;
        } else if (cacheDescription.equals(action)) {
            ActionErrors ae = AdmCachecosMgrLogic.getCacheDescription(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return CACHE_DESCRIPTION;
        } else if (cacheManagement.equals(action)) {
            ActionErrors ae = AdmCachecosMgrLogic.initManagement(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return CACHE_MANAGEMENT;
        } else if (executeOp.equals(action)) {
            ActionErrors ae = AdmCachecosMgrLogic.executeOp(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return ERROR;
            }
            return CACHE_MANAGEMENT;
        } else {
            return DISPLAY;
        }

    }

}
