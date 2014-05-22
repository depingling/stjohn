package com.cleanwise.view.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.logic.InboundFilesMgrLogic;

public class InboundFilesMgrAction extends ActionSuper {

    public final static String ACTION = "action";
    public final static String ACTION_INIT = "init";
    public final static String ACTION_SEARCH = "search";
    public final static String ACTION_DOWNLOAD = "download";

    public ActionForward performSub(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter(ACTION);
        String successForward = ACTION_INIT;
        String failForward = ACTION_INIT;

        if (action == null) {
            action = ACTION_INIT;
        }
        try {
            ActionErrors errors = new ActionErrors();
            if (ACTION_SEARCH.equals(action)) {
                successForward = ACTION_SEARCH;
                failForward = ACTION_SEARCH;
                ActionErrors operationErrors = 
                    InboundFilesMgrLogic.search(response, request, form);
                if (operationErrors != null && operationErrors.size() > 0) {
                    errors.add(operationErrors);
                }
            } else if (ACTION_DOWNLOAD.equals(action)) {
                successForward = ACTION_SEARCH;
                failForward = ACTION_SEARCH;
                ActionErrors operationErrors = 
                    InboundFilesMgrLogic.downladInboundFile(response, request, form);
                if (operationErrors != null && operationErrors.size() > 0) {
                    errors.add(operationErrors);
                }
                if (errors.size() == 0) {
                    return null;
                }
            }
            ActionErrors operationErrors = 
                InboundFilesMgrLogic.init(response, request, form);
            if (operationErrors != null && operationErrors.size() > 0) {
                errors.add(operationErrors);
            }
            if (errors.size() > 0) {
                saveErrors(request, errors);
                return (mapping.findForward(failForward));
            } else {
                return (mapping.findForward(successForward));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return (mapping.findForward(failForward));
        }
    }
}
