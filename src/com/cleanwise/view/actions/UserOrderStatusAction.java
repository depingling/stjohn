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

import com.cleanwise.view.logic.UserOrderStatusLogic;
import com.cleanwise.view.forms.UserOrderStatusForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

/**
 *  Implementation of <strong>Action</strong> that
 *  processes a user requests to hanle pending orders.
 *
 *@author     dvieira
 *@created    January 7, 2002
 */

public final class UserOrderStatusAction extends ActionSuper {

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

        if (reqAction.equals("init") || "order_status".equals(reqAction)) {
            request.getSession().setAttribute("sc_source",
                "search_site");
            try {
                UserOrderStatusLogic.listOrders(request, form);
            	    }
	    catch (Exception e) {
		System.err.println("List error: " + e);
	    }
        } else if (reqAction.equals("initXPEDXListOrders")) {
            ActionErrors ae;
            try {
                ae = UserOrderStatusLogic.initXPEDXListOrders(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return mapping.findForward("failure");
            }
        } else if (reqAction.equals("changeCountry")) {
            ActionErrors ae;
            try {
                ae = UserOrderStatusLogic.changeCountry(request, form, response);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return mapping.findForward("failure");
            }
            return null;
        } else if (reqAction.equals("changeState")) {
            ActionErrors ae;
            try {
                ae = UserOrderStatusLogic.changeState(request, form, response);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return mapping.findForward("failure");
            }
            return null;
        } else if (reqAction.equals("searchXPEDXOrders")) {
            ActionErrors ae;
            try {
                ae = UserOrderStatusLogic.searchXPEDXOrders(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return mapping.findForward("failure");
            }
            return (mapping.findForward("success"));

        } else if (reqAction.equals("searchRejectOrders")) {
            ActionErrors ae;
            try {
                ae = UserOrderStatusLogic.searchRejectOrders(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return mapping.findForward("failure");
            }
            return (mapping.findForward("success"));

        }  else if (reqAction.equals("searchApproveOrders")) { 
            ActionErrors ae;
            try {
                ae = UserOrderStatusLogic.searchApproveOrders(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return mapping.findForward("failure");
            }
            return (mapping.findForward("success"));

        } else if (reqAction.equals("changeSite")) {
            ActionErrors ae;
            try {
                ae = UserOrderStatusLogic.changeSite(request, form, response);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return mapping.findForward("failure");
            }
            return null;
        }
        else if (reqAction.equals("sort")) {
	    try {
		UserOrderStatusLogic.sort(request, form);
	    }
	    catch (Exception e) {
		System.err.println("Sort error: " + e);
	    }
        }
        else if ( reqAction.equals("search_all_sites_init")) {
            request.getSession().setAttribute("sc_source",
                "search_all_sites");
            ((UserOrderStatusForm)form).setResultList(null);
            return (mapping.findForward("success"));
        }
        else if (reqAction.equals("search") ||
			reqAction.equals("search_all_sites")
		) {
            ActionErrors ae = new ActionErrors();
            try {
                ae = UserOrderStatusLogic.search(request, form);
	    }
	    catch (Exception e) {
        e.printStackTrace();
	    }
                
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return (mapping.findForward("success"));
        }

        return (mapping.findForward("display"));
    }

}

