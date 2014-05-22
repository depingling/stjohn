package com.cleanwise.view.actions;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.MessageResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.forms.StoreGroupForm;
import com.cleanwise.view.logic.StoreGroupMgrLogic;

/**
 * @author Evgeny Vlasov
 * Date: 12/04/2006
 * Code: StoreGroupMgrConfigAction implements the actions needed
 * for group configuration in storeportal.
 */
public class StoreGroupMgrConfigAction extends ActionSuper {

    private static final String CLASS_NANE  = "StoreGroupMgrConfigAction";
    private String successForward           = "success";
    private String failForward              = "failure";

    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @throws java.io.IOException            if an input/output error occurs
     * @throws javax.servlet.ServletException if a servlet exception occurs
     */
    public ActionForward performSub(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        // Determine the store manager action to be performed
        String action = request.getParameter("action");
        if (action == null) {
            action = "init";
        }

        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if (!st.checkSession()) {
            return mapping.findForward("/userportal/logon");
        }

        MessageResources mr = getResources(request);
        String searchStr = getMessage(mr, request, "global.action.label.search");
        String updateStr = getMessage(mr, request, "admin.button.submitUpdates");
        
        StoreGroupForm grForm = (StoreGroupForm)form;
        
        // Process the action
        try {

            String initConfig = "initConfig";

            ActionErrors errors = new ActionErrors();
            if (action.equals(updateStr)) {
                errors = StoreGroupMgrLogic.updateGroupConfig(request, form);
               // errors.add(StoreGroupMgrLogic.searchGroupConfig(request, form));
            } else if (action.equals(searchStr)) {
                errors = StoreGroupMgrLogic.searchGroupConfig(request, form);
            } else if(action.equals(initConfig)){
//                grForm.setConfigResults(null);
                errors=StoreGroupMgrLogic.initGroupConfig(request,form);
            } else {
            	grForm.setConfigResults(null);
                StoreGroupMgrLogic.init(response, request, form);
            }

            if (errors.size() > 0) {
                saveErrors(request, errors);
                return (mapping.findForward(successForward));
            } else {
                return (mapping.findForward(failForward));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return (mapping.findForward(failForward));
        }

    }

}
