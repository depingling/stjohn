package com.cleanwise.view.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.view.logic.OrderSearchLogic;
import com.cleanwise.view.forms.OrderSearchForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

/**
 *  Implementation of <strong>Action</strong> that
 *  processes a user requests to hanle orders.
 *
 *@author     alukovnikov
 */

public final class OrderSearchAction extends ActionSuper {

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
                OrderSearchLogic.listOrders(request, form);
            	    }
	    catch (Exception e) {
		System.err.println("List error: " + e);
	    }
        } else if ("pending_orders".equals(reqAction) == true) {
            ActionErrors ae = OrderSearchLogic.checkSubAction(request, form,
                    getResources(request));
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            request.getSession().setAttribute("sc_source",
                "pending_orders");
            try {
                OrderSearchLogic.listPendingOrders(request, form);
            } catch (Exception e) {
                e.printStackTrace();
                return mapping.findForward("failure");
            }
        } else if (reqAction.equals("initXPEDXListOrders")) {
            ActionErrors ae;
            try {
                ae = OrderSearchLogic.initXPEDXListOrders(request, form);
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
                ae = OrderSearchLogic.changeCountry(request, form, response);
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
                ae = OrderSearchLogic.changeState(request, form, response);
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
                ae = OrderSearchLogic.searchXPEDXOrders(request, form);
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
                ae = OrderSearchLogic.searchRejectOrders(request, form);
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
                ae = OrderSearchLogic.searchApproveOrders(request, form);
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
                ae = OrderSearchLogic.changeSite(request, form, response);
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
		OrderSearchLogic.sort(request, form);
	    }
	    catch (Exception e) {
		System.err.println("Sort error: " + e);
	    }
        }
        else if ( reqAction.equals("search_all_sites_init")) {
            request.getSession().setAttribute("sc_source",
                "search_all_sites");
            ((OrderSearchForm)form).setResultListAndShowSearchFields(null, true);
            return (mapping.findForward("success"));
        }
        else if (reqAction.equals("search") ||
			reqAction.equals("search_all_sites")
		) {
            ActionErrors ae = new ActionErrors();
            try {
                ae = OrderSearchLogic.search(request, form);
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
