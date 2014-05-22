/**
 * Title:        LogOffAction
 * Description:  This is the Struts Action class mapping the logoff.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li
 */

package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.apache.log4j.Logger;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.logic.LogOnLogic;


/**
 * Implementation of <strong>Action</strong> that processes a
 * user logoff.
 */

public final class LogOffAction extends ActionSuper {
        private static final Logger log = Logger.getLogger(LogOffAction.class);


  // ------------------------------------------------------------ Public Methods


  /**
   * Process the specified HTTP request, and create the corresponding HTTP
   * response (or forward to another web component that will create it).
   * Return an <code>ActionForward</code> instance describing where and how
   * control should be forwarded, or <code>null</code> if the response has
   * already been completed.
   *
   * @param mapping The ActionMapping used to select this instance
   * @param actionForm The optional ActionForm bean for this request (if any)
   * @param request The HTTP request we are processing
   * @param response The HTTP response we are creating
   *
   * @exception IOException if an input/output error occurs
   * @exception ServletException if a servlet exception occurs
   */
  public ActionForward performSub(
                 ActionMapping mapping,
                 ActionForm form,
                 HttpServletRequest request,
                 HttpServletResponse response)
                 throws IOException, ServletException {

	  HttpSession session = request.getSession();
	  String originUrl = getCookie(request,Constants.ORIGIN_URL);
	
    // Process this user logoff
    if ( request.isRequestedSessionIdValid() ) {
      session = request.getSession();
      session.invalidate();
    }
    log.info("originUrl = "+originUrl);
    if(Utility.isSet(originUrl)){
    	try{
			originUrl = java.net.URLDecoder.decode(originUrl,"UTF-8");
	    }catch(java.io.UnsupportedEncodingException e){
	    	log.error("Caught exception trying to decode cookie value: "+originUrl+" proceeding with it unencoded");
	    }
    	response.sendRedirect(originUrl);
    	return null;
	}
    LogOnLogic.initAnonymousUser(request,response);
    // Forward control to the specified success URI
    return (mapping.findForward("success"));

  }


}
