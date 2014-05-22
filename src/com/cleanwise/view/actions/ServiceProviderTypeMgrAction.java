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

import com.cleanwise.view.forms.ServiceProviderTypeMgrForm;

import com.cleanwise.view.logic.ServiceProviderTypeMgrLogic;
import com.cleanwise.view.utils.SessionTool;

/**
 * Implementation of <strong>Action</strong> that saves a new Account Service Provider or
 * updates an existing Account Service Provider.
 */
public final class ServiceProviderTypeMgrAction extends ActionSuper {

    @Override
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
        ServiceProviderTypeMgrForm sForm = (ServiceProviderTypeMgrForm) form;
        ActionMessages ae = null;
        
        try {
            if ("Add Service Provider Type".equals(action)) {
                ae = ServiceProviderTypeMgrLogic.addServiceProviderType(request, sForm);
            } else if ("Edit Service Provider Type".equals(action)) {
                ae = ServiceProviderTypeMgrLogic.editServiceProviderType(request, sForm);
            } else if ("Update Service Provider Type".equals(action)) {
                ae = ServiceProviderTypeMgrLogic.updateServiceProviderType(request, sForm);
            } else if ("Delete Service Provider Type".equals(action)) {
                ae = ServiceProviderTypeMgrLogic.deleteServiceProviderType(request, sForm);
            } else {
                ae = ServiceProviderTypeMgrLogic.init(request, sForm);
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
