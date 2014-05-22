package com.cleanwise.view.actions;

import org.apache.struts.action.*;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;

import com.cleanwise.view.logic.Admin2HomeMgrLogic;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.forms.Admin2HomeMgrForm;

import java.io.IOException;

/**
 * Title:        Admin2HomeMgrAction
 * Description:  Action manager
 * Purpose:      Class for calling logic methods  which  process the request.
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * Date:         25.05.2009
 * Time:         13:06:14
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class Admin2HomeMgrAction extends ActionSuper {

    private static final Logger log = Logger.getLogger(Admin2HomeMgrAction.class);

    private static final String ERROR = "error";
    private static final String SUCCESS = "success";

    private static final String INIT = "init";
    private static final String CHANGE_ENTITY = "changeEntity";

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
        ActionErrors ae;
        if (INIT.equals(action)) {

            ae = Admin2HomeMgrLogic.initUserHome((Admin2HomeMgrForm) form, request);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return mapping.findForward(ERROR);
            }
            return mapping.findForward(SUCCESS);

        } else if (CHANGE_ENTITY.equals(action)) {

            ae = Admin2HomeMgrLogic.changeEntity((Admin2HomeMgrForm) form, request);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return mapping.findForward(ERROR);
            }
            return mapping.findForward(SUCCESS);

        }

        return mapping.findForward(SUCCESS);

    }

}
