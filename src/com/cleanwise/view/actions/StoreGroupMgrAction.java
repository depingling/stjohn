package com.cleanwise.view.actions;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.MessageResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import java.io.*;

import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.logic.StoreGroupMgrLogic;
import com.cleanwise.view.forms.StoreGroupForm;
import com.cleanwise.service.api.value.GroupData;

/**
 * @author Evgeny Vlasov
 * Date: 12/04/2006
 * Code: StoreGroupMgrAction implements the actions needed 
 * for groups in storeportal.
 */
public class StoreGroupMgrAction extends ActionSuper {

    private static String className = "StoreGroupMgrAction";
    private String successForward   = "success";
    private String failForward      = "failure";

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
        if (action == null){
            action = "init";
        }

        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if (!st.checkSession()) {
            return mapping.findForward("/userportal/logon");
        }

        MessageResources mr = getResources(request);

        String searchStr = getMessage(mr, request, "global.action.label.search");
        String viewAllStr = getMessage(mr, request, "admin.button.viewall");
        String createNewStr = getMessage(mr, request, "admin.button.create");
        String addUpdStr = getMessage(mr, request, "admin.button.submitUpdates");
        String reportsStr = "reports";
        String viewDetailStr = "viewDetail";
        String reportOfUserUpdate = "report.user.update";
        String functionOfUserUpdate = "function.user.update";
        String initFunctions="initFunctions";
        String initReports="initReports";

        successForward = "success";
        failForward    = "failure";
        
        // Process the action
        try {

            ActionErrors errors = new ActionErrors();

            if (action.equals(addUpdStr)) {

                successForward = "groupdetail";
                failForward = "groupdetail";
                errors = StoreGroupMgrLogic.addUpdateGroup(response, request, form);

            } else if (action.equals(searchStr)) {

                errors = StoreGroupMgrLogic.search(response, request, form);

            } else if (action.equals(viewAllStr)) {

                errors = StoreGroupMgrLogic.viewAll(response, request, form);

            } else if (action.equals(viewDetailStr)) {

                successForward = "groupdetail";
                failForward = "groupdetail";
                errors = StoreGroupMgrLogic.getGroupDetail(response, request, form);

            } else if (action.equals(createNewStr)) {

                successForward = "groupdetail";
                failForward = "groupdetail";
                errors = StoreGroupMgrLogic.initEmptyGroup(response, request, form);

            } else if (action.equals(reportsStr)) {

                successForward = "report";
                failForward = "report";

            }else if (action.equals(reportOfUserUpdate) || action.equals(functionOfUserUpdate)) {

                errors = StoreGroupMgrLogic.updateSupplementaryUserGroup(request, form, action.equals(reportOfUserUpdate));
                errors.add(StoreGroupMgrLogic.getFunctionsOrReports(request,form, action.equals(reportOfUserUpdate)));

            } else if(action.equals(initFunctions)||action.equals(initReports)){

                errors = StoreGroupMgrLogic.getFunctionsOrReports(request,form, action.equals(initReports));

            } else if (action.trim().equalsIgnoreCase("init")){
                StoreGroupMgrLogic.resetForm(request);
            }

            StoreGroupMgrLogic.init(response, request, form);

            if (errors.size() > 0) {                
                saveErrors(request, errors);
                return (mapping.findForward(failForward));
            } else {
                return (mapping.findForward(successForward));
            }

        } catch (Exception e) {
            error("successForward = " + successForward + "failForward = " + failForward, e);
            return (mapping.findForward(failForward));
        }

    }

    /**
     * Error logging
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
     */
    private void error(String message, Exception e){

        String errorMessage;
        StringWriter wr = new StringWriter();
        PrintWriter prW = new PrintWriter(wr);
        e.printStackTrace(prW);
        errorMessage = wr.getBuffer().toString();
    }
}
