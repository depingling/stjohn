package com.cleanwise.view.actions;


import com.cleanwise.view.forms.StoreServiceMgrForm;
import com.cleanwise.view.logic.StoreServiceMgrLogic;
import com.cleanwise.view.utils.SessionTool;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Title:        StoreServiceMgrAction
 * Description:  Actions manager for the service processing.
 * Purpose:      Class for calling logic methods  which  process the request
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         09.01.2007
 * Time:         15:27:16
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class StoreServiceMgrAction extends ActionSuper {
    private static final String FAILURE = "failure";
    private static final String MAIN = "main";
    private static final String SUCCESS = "success";
    private static final String DETAIL = "detail";

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
    public ActionForward performSub(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

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

        try {
            ActionForward actionForward;
            actionForward = runWorkerForm(action, form, request, mapping);
            return actionForward;
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward(FAILURE);
        }
    }

    private ActionForward runWorkerForm(String action, ActionForm form, HttpServletRequest request, ActionMapping mapping) throws Exception {

        String forward_page = MAIN;
        logm("action : " + action + " form : " + form + " mapping attribute : " + mapping.getAttribute());
        if (form instanceof StoreServiceMgrForm) forward_page = storeServiceMgrFormWorker(action, form, request);
        else logm("The worker of the form can't be found.Unknown form : " + form);
        logm("Forward page :" + mapping.findForward(forward_page).getPath());
        return mapping.findForward(forward_page);
    }

    private String storeServiceMgrFormWorker(String action, ActionForm form, HttpServletRequest request) throws Exception {
        ActionErrors ae;
        MessageResources mr = getResources(request);
        String initService = "initService";
        String createService = "Create Service";
        String save = getMessage(mr, request, "global.action.label.save");
        String editService = "Edit Service";
        String search = getMessage(mr, request, "global.action.label.search");
        String configUpdate="Configuration Update";
        if (action.equals(initService)) {

            ae = StoreServiceMgrLogic.init(request, (StoreServiceMgrForm) null);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(createService)) {

            ae = StoreServiceMgrLogic.createService(request, (StoreServiceMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(save)) {

            ae = StoreServiceMgrLogic.updateService(request, (StoreServiceMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(editService)) {

            ae = StoreServiceMgrLogic.editService(request, (StoreServiceMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(search)) {

            ae = StoreServiceMgrLogic.getServiceConfiguration(request, (StoreServiceMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else if (action.equals(configUpdate)) {

            ae = StoreServiceMgrLogic.updateServiceConfiguration(request, (StoreServiceMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }
        return SUCCESS;
    }

}
