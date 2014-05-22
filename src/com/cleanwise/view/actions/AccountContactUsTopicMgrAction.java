package com.cleanwise.view.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.cleanwise.view.forms.AccountContactUsTopicMgrForm;
import com.cleanwise.view.logic.AccountContactUsTopicMgrLogic;
import com.cleanwise.view.utils.SessionTool;

/**
 * Implementation of <strong>Action</strong> that saves a new catalog detail or
 * updates an existing catalog detail.
 */
public final class AccountContactUsTopicMgrAction extends ActionSuper {

    public ActionForward performSub(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        SessionTool st = new SessionTool(request);
        if (st.checkSession() == false) {
            return mapping.findForward("/userportal/logon");
        }
        session.setAttribute("LAST_ACTION", action);
        AccountContactUsTopicMgrForm sForm = (AccountContactUsTopicMgrForm) form;
        ActionMessages ae = null;
        try {
            if ("Add Topic".equals(action)) {
                ae = AccountContactUsTopicMgrLogic.addTopic(request, sForm);
            } else if ("editTopic".equals(action)) {
                ae = AccountContactUsTopicMgrLogic.editTopic(request, sForm);
            } else if ("Update Topic".equals(action)) {
                ae = AccountContactUsTopicMgrLogic.updateTopic(request, sForm);
            } else if ("deleteTopic".equals(action)) {
                ae = AccountContactUsTopicMgrLogic.deleteTopic(request, sForm);
            } else {
                ae = AccountContactUsTopicMgrLogic.init(request, sForm);
            }
        } catch (Exception e) {
            request.setAttribute("errorobject", e);
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }
        if (ae != null && ae.size() > 0) {
            saveErrors(request, ae);
            return (mapping.findForward("failure"));
        }
        return (mapping.findForward("success"));
    }
}
