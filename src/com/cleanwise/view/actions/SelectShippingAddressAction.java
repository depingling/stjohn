/**
 * Title:        SelectShippingAddressAction
 * Description:  This is the Struts Action class mapping the selectshippingaddress.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li
 */

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
import com.cleanwise.service.api.*;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.logic.LogOnLogic;
import com.cleanwise.view.forms.SelectShippingAddressForm;
import com.cleanwise.view.logic.SelectShippingAddressLogic;
import com.cleanwise.view.utils.*;


/**
 * Implementation of <strong>Action</strong> that processes the
 * select shipping address page.
 */

public final class SelectShippingAddressAction extends ActionSuper {


  // ------------------------------------------------------------ Public Methods


  /**
   * Process the specified HTTP request, and create the corresponding HTTP
   * response (or forward to another web component that will create it).
   * Return an <code>ActionForward</code> instance describing where and how
   * control should be forwarded, or <code>null</code> if the response has
   * already been completed.
   *
   * @param mapping
   *  the ActionMapping used to select this instance
   * @param actionForm
   *  the optional ActionForm bean for this request (if any)
   * @param request
   *  the HTTP request we are processing
   * @param response
   *  the HTTP response we are creating
   *
   * @exception IOException
   *  if an input/output error occurs
   * @exception ServletException
   *  if a servlet exception occurs
   */
  public ActionForward performSub(
                 ActionMapping mapping,
                 ActionForm form,
                 HttpServletRequest request,
                 HttpServletResponse response)
                 throws IOException, ServletException {

    String action = (String)request.getParameter("action");
    SelectShippingAddressForm theForm = (SelectShippingAddressForm)form;
    if (action == null) action = "init";

    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }   


    HttpSession session = request.getSession();
    // Process the action
    try {
      ActionErrors ae;
      if (action.equals("init")) {
         ae = SelectShippingAddressLogic.init(request,theForm);
         if(ae.size()>0) {
           saveErrors(request, ae);
         }
         return (new ActionForward(mapping.getInput()));
      }

      else if (action.equals("select") ) {
         ae = SelectShippingAddressLogic.init(request,theForm);
         if(ae.size()>0) {
           saveErrors(request, ae);
           return (new ActionForward(mapping.getInput()));
         }
         ae = SelectShippingAddressLogic.select(request,theForm);
         if(ae.size()>0) {
           saveErrors(request, ae);
           return (new ActionForward(mapping.getInput()));
         }

         return (mapping.findForward("success"));
      }

      else if (action.equals("submit")) {

        if(request.getSession().getAttribute("continuationString") != null){
            String s = (String) request.getSession().getAttribute("continuationString");
            ActionForward af = new ActionForward(s, true);
                       
            return af;
        }
        return (mapping.findForward("user"));
      }

      return (mapping.findForward("failure"));

    }

    catch (Exception e) {
      e.printStackTrace();
      return (mapping.findForward("failure"));
    }


  }


}
