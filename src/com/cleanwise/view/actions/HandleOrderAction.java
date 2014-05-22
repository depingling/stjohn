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
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.CheckoutForm;
import com.cleanwise.view.forms.OrderOpDetailForm;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.logic.HandleOrderLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

/**
 *  Implementation of <strong>Action</strong> that
 *  processes a user requests to hanle pending orders.
 *
 *@author     dvieira
 *@created    January 7, 2002
 */

public final class HandleOrderAction extends ActionSuper {

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
            reqAction = "view";
        }
  
        CheckoutForm theForm = (CheckoutForm)request.getSession().getAttribute("CheckoutForm");
        //if a user comes into the site from a notification email we sent, we have to capture the address
 
        String p = request.getRequestURI();
        String pg = "";
        for(int k=10; k<p.length(); k++){

           pg += p.charAt(k);

        }
        pg += "?"+request.getQueryString();


        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {          
           //capture the order status url if there is one           
           request.getSession().setAttribute("continuationString", pg);

           return mapping.findForward("/userportal/logon");
        }   

        ActionErrors ae = new ActionErrors();
        String retAction = "display";

        String oidstr = (String) request.getParameter("orderId");
        if (oidstr == null) {
	    return (mapping.findForward("display"));
        }

        try {
        if (reqAction.equals("view") ) {
            HandleOrderLogic.viewOrder
		(request, Integer.parseInt(oidstr) );
        }
        
        else if (reqAction.equals("reject") ) {
            HandleOrderLogic.rejectOrder
		(request, Integer.parseInt(oidstr), 
		 getResources(request));
        }
        else if (reqAction.equalsIgnoreCase("approve") ) {
          ae = HandleOrderLogic.approveOrder
		((OrderOpDetailForm) form, request, Integer.parseInt(oidstr) );        
           if(ae.size()>0) {
             saveErrors(request,ae);
           }
        }
        else if (reqAction.equals("ApproveOn") ) {
           ae = HandleOrderLogic.approveOrder
		((OrderOpDetailForm)  form, request, Integer.parseInt(oidstr) );
           if(ae.size()>0) {
             saveErrors(request,ae);
           }
        }
        else if (reqAction.equals("addComment") ) {
           ae = HandleOrderLogic.addComment
		(request, Integer.parseInt(oidstr) );
           if(ae.size()>0) {
             saveErrors(request,ae);
           }
        }
        else if (reqAction.equals("printPdf") ) {
          ae = HandleOrderLogic.printDetail(response,request,theForm,Integer.parseInt(oidstr) );
          if(ae.size()>0) {
            saveErrors(request,ae);
          }
          else if(ae == null || ae.size() == 0)
          {
            return null;
          }
        }
        else if (reqAction.equals("orderStatus") ) {
          ae = HandleOrderLogic.printOrderStatus(response,request,form);
          if(ae.size()>0) {
            saveErrors(request,ae);
          } else {
            return null;
          }
        }
	    else if (reqAction.equals("modify")) {
		    ae = HandleOrderLogic.editOrder
            (request, Integer.parseInt(oidstr) );
		   if(ae.size()>0) {
            saveErrors(request,ae);
          } else { 
            return (mapping.findForward("shopping_cart"));
          }
	    }      
	    else if (reqAction.equals("reorder")) {
		    ae = HandleOrderLogic.copyOrderToCart
            (request, Integer.parseInt(oidstr) );
		   if(ae.size()>0) {
            saveErrors(request,ae);
          } else { 
            return (mapping.findForward("shopping_cart"));
          }
	    }
        else if (reqAction.equals("consolidate") ) {
           ae = HandleOrderLogic.consolidate(request, Integer.parseInt(oidstr) );
 		   if(ae.size()>0) {
             saveErrors(request,ae);
           } else {
             retAction = "pendingOrders";
           }
        }
        else if (reqAction.equals("deconsolidate") ) {
           ae = HandleOrderLogic.deconsolidate(request, Integer.parseInt(oidstr) );
 		   if(ae.size()>0) {
             saveErrors(request,ae);
           } else {
             retAction = "pendingOrders";
           }
        }
        else if (reqAction.equals("goToOrderLocation") ) {
           ae = HandleOrderLogic.goToOrderLocation(request, Integer.parseInt(oidstr) );
 		   if(ae.size()>0) {
             saveErrors(request,ae);
           }
        }
        }
        catch ( Exception e ) {
            e.printStackTrace();
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            retAction="error";
        }

        return mapping.findForward(retAction);
    }

}

