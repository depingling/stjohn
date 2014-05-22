package com.cleanwise.view.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.cleanwise.view.forms.AccountMgrCheckoutDataForm;
import com.cleanwise.view.logic.AccountMgrCheckoutDataLogic;
import com.cleanwise.view.utils.SessionTool;

public final class AccountMgrCheckoutDataAction extends ActionSuper {
    public ActionForward performSub(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String action = request.getParameter("action");
        MessageResources mr = getResources(request);
        // Get the form buttons as specified in the properties file.
        String saveStr = getMessage(mr,request,"global.action.label.save");
        
        SessionTool st = new SessionTool(request);
        if (st.checkSession() == false) {
            return mapping.findForward("/userportal/logon");
        }
        AccountMgrCheckoutDataForm sForm = (AccountMgrCheckoutDataForm) form;
        ActionMessages ae = null;
        try {
        	if (saveStr.equals(action)) {            	
                ae = AccountMgrCheckoutDataLogic.saveCheckoutFields(request, sForm);
            } else {
                ae = AccountMgrCheckoutDataLogic.init(request, sForm);
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
