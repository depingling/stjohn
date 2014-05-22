package com.cleanwise.view.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.cleanwise.view.forms.AccountMgrServiceFeeForm;
import com.cleanwise.view.logic.AccountMgrServiceFeeLogic;
import com.cleanwise.view.utils.SessionTool;

public final class AccountMgrServiceFeeAction extends ActionSuper {
    public ActionForward performSub(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String action = request.getParameter("action");
        SessionTool st = new SessionTool(request);
        if (st.checkSession() == false) {
            return mapping.findForward("/userportal/logon");
        }
        AccountMgrServiceFeeForm sForm = (AccountMgrServiceFeeForm) form;
        ActionMessages ae = null;
        try {
            if ("update".equalsIgnoreCase(action)) {
                ae = AccountMgrServiceFeeLogic.update(request, sForm);
            } else {
                ae = AccountMgrServiceFeeLogic.init(request, sForm);
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
