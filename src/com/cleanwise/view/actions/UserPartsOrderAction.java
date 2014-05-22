package com.cleanwise.view.actions;

import com.cleanwise.view.logic.UserPartsOrderLogic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

/**
 *  Implementation of <strong>Action</strong> that
 *  processes a user requests to hanle pending orders.
 *
 *@author     alexxey
 *@created    May 6, 2008
 */

public final class UserPartsOrderAction extends ActionSuper {

    /**
     *  perform method for the action bean which allows users to view the informaiotn about the
     *  current Parts Order.
     *  
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
            reqAction = "view";
        }
        String retAction = "display";
        String orderIdstr = (String) request.getParameter("orderId");
        
	    try {
            // Is there a currently logged on user?
            HttpSession session = request.getSession();
            CleanwiseUser userLoggedIn = (CleanwiseUser)
                session.getAttribute(Constants.APP_USER);
            if ((session == null) || (userLoggedIn == null)) {
                return mapping.findForward("/userportal/logon");
            }        

            if (reqAction.equals("view")) {
                int orderId = Integer.parseInt(orderIdstr);
                UserPartsOrderLogic.showOrder(request, orderId);
                retAction = "display";
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            retAction="error";
        }
        
        ActionForward actionForward = mapping.findForward(retAction);
        
        navigateBreadCrumb(request, actionForward);
        
        return (actionForward);
    }
}
