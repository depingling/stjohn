package com.cleanwise.view.actions;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.logic.LogOnLogic;
import com.cleanwise.view.i18n.ClwI18nUtil;

/**
 *  Implementation of <strong>Action</strong> that processes a user request for
 *  a password email.
 *
 *@author     dvieira
 *@created    October 15, 2001
 */

public final class PwdAccessAction extends ActionSuper {

	protected boolean isRequiresConfidentialConnection(){
        return true;
    }
	
    /**
     *  perform method for the action bean which allows users the ability to
     *  request a new password email.
     *
     *@param  mapping   Description of Parameter
     *@param  form      Description of Parameter
     *@param  request   Description of Parameter
     *@param  response  Description of Parameter
     *@return           Description of the Returned Value
     */
    public ActionForward performSub
            (ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {
        String reqAction = (String) request.getParameter("action");

        if (reqAction == null) {
            reqAction = (String) request.getAttribute("action");
        }
        if (reqAction == null) {
            reqAction = "init";
        }

        String reqUser = (String) request.getParameter("userName");
        if (reqUser == null) {
            reqUser = (String) request.getAttribute("userName");
        }
        if (reqUser == null) {
            return (mapping.findForward("back_to_logon"));
        }else{        	reqUser = Utility.encodeForHTML(reqUser);        }

        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if (!reqAction.equals("send") && (st.checkSession() == false)) {
        	return (mapping.findForward("back_to_logon"));
        }
        if (reqAction.equals("init")) {
        	ActionErrors errors = new ActionErrors();
        	String[] args = new String[1];
        	args[0]=reqUser;
        	String errorMess = ClwI18nUtil.getMessage(request, "login.text.passwordMessageSent", args);
        	errors.add("error", new ActionError("error.simpleMsg", errorMess));
            saveErrors(request, errors);
            return (mapping.findForward("display"));
        }
        else if (reqAction.equals("send")) {
            // Generate a new password for the user. and send an email.
            LogOnLogic.generateNewPassword(request, getResources(request), reqUser);
            //ignore errors from above for security reasons.  Do not give feedback to whether user exists or not
            //as it gives hackers the info that they found a valid user
            ActionErrors errors = new ActionErrors();
            String[] args = new String[1];
        	args[0]=reqUser;
        	String errorMess = ClwI18nUtil.getMessage(request, "login.text.passwordMessageSent", args);
        	errors.add("error", new ActionError("error.simpleMsg", errorMess));
            saveErrors(request, errors);
            return (mapping.findForward("sent_email"));
        }
        return (mapping.findForward("back_to_logon"));
    }

}

