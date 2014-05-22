package com.cleanwise.view.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.value.*;

import com.cleanwise.view.logic.UserPendingOrdersLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

/**
 *  Implementation of <strong>Action</strong> that
 *  processes a user requests to hanle pending orders.
 *
 *@author     dvieira
 *@created    January 7, 2002
 */

public final class UserPendingOrdersAction extends ActionSuper {

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
	    // Is there a currently logged on user?
        HttpSession currentSession = request.getSession();
	CleanwiseUser userLoggedIn = (CleanwiseUser)
	    currentSession.getAttribute(Constants.APP_USER);
        if ((currentSession == null) || (userLoggedIn == null)) {
            return mapping.findForward("/userportal/logon");
        }        

        if (reqAction.equals("init")) {
            UserPendingOrdersLogic.listOrders(request, userLoggedIn);
        }
        else if (reqAction.equals("sort")) {
	    try {
		UserPendingOrdersLogic.sort(request);
	    }
	    catch (Exception e) {
		System.err.println("Sort error: " + e);
	    }
        }

        return (mapping.findForward("display"));
    }

}

