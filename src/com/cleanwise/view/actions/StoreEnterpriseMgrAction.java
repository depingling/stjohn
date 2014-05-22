package com.cleanwise.view.actions;

import com.cleanwise.view.forms.StoreEnterpriseMgrForm;
import com.cleanwise.view.forms.StoreEnterpriseMfgForm;
import com.cleanwise.view.logic.StoreEnterpriseMgrLogic;
import com.cleanwise.view.utils.SessionTool;
import org.apache.struts.action.*;
import org.apache.struts.util.MessageResources;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StoreEnterpriseMgrAction extends ActionSuper {

    private static final String FAILURE = "failure";
    private static final String MAIN = "main";
    private static final String SUCCESS = "success";

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
            actionForward = runWorkerForm(action, form, request, mapping,response);
            return actionForward;
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward(FAILURE);
        }


    }

    private ActionForward runWorkerForm(String action, ActionForm form, HttpServletRequest request, ActionMapping mapping,HttpServletResponse response) throws Exception {

        String forward_page = MAIN;
        logm("action : " + action + " form : " + form + " mapping attribute : " + mapping.getAttribute());
        if (form instanceof StoreEnterpriseMgrForm) forward_page = storeEnterpriseMgrFormWorker(action, form, request);
        else if  (form instanceof StoreEnterpriseMfgForm) forward_page = storeEnterpriseMfgFormWorker(action, form, request);

        else logm("The worker of the form can't be found.Unknown form : " + form);

        logm("Forward page :" + mapping.findForward(forward_page).getPath());
        return mapping.findForward(forward_page);
    }

    private String storeEnterpriseMfgFormWorker(String action, ActionForm form, HttpServletRequest request) throws Exception {
        ActionErrors ae;
        MessageResources mr = getResources(request);

        String init = "init";
        String addEnterpriseMfgAssoc = getMessage(mr, request,"admin.button.add");
        String dropEnterpriseMfgAssoc = getMessage(mr, request,"admin.button.drop");
        String save = getMessage(mr, request,"global.action.label.save");

        if (init.equals(action)) {
            ae = StoreEnterpriseMgrLogic.init(request, (StoreEnterpriseMfgForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else  if (addEnterpriseMfgAssoc.equals(action)) {
            ae = StoreEnterpriseMgrLogic.addEnterpriseMfgAssoc(request, (StoreEnterpriseMfgForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        } else  if (dropEnterpriseMfgAssoc.equals(action)) {
            ae = StoreEnterpriseMgrLogic.dropEnterpriseMfgAssoc(request, (StoreEnterpriseMfgForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }   else  if (save.equals(action)) {
            ae = StoreEnterpriseMgrLogic.updateEnterpriseMfgAssoc(request, (StoreEnterpriseMfgForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }

        return MAIN;

    }

    private String storeEnterpriseMgrFormWorker(String action, ActionForm form, HttpServletRequest request) throws Exception {

        ActionErrors ae;
        MessageResources mr = getResources(request);

        String init = "init";

        if (init.equals(action)) {
            ae = StoreEnterpriseMgrLogic.init(request, (StoreEnterpriseMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }

        return MAIN;
    }

}
