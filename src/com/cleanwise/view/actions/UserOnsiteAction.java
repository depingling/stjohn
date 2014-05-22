
package com.cleanwise.view.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.utils.Constants;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.forms.UserShopForm;
import com.cleanwise.view.forms.OnsiteServicesForm;
import com.cleanwise.view.logic.UserShopLogic;
import java.util.Iterator;
import java.util.Enumeration;
import com.cleanwise.view.utils.*;


/**
 * Implementation of <strong>Action</strong> that processes the
 * user shopping functions.
 *
 * @author durval
 */
public final class UserOnsiteAction extends ActionSuper
{

  // ---------------------------------------------------- Public Methods


  /**
   * Process the specified HTTP request, and create the corresponding HTTP
   * response (or forward to another web component that will create it).
   * Return an <code>ActionForward</code> instance describing where and how
   * control should be forwarded, or <code>null</code> if the response has
   * already been completed.
   *
   * @param mapping The ActionMapping used to select this instance
   * @param form an <code>ActionForm</code> value
   * @param request The HTTP request we are processing
   * @param response The HTTP response we are creating
   *
   * @return an <code>ActionForward</code> value
   * @exception IOException if an input/output error occurs
   * @exception ServletException if a servlet exception occurs
   */
  public ActionForward performSub(
			       ActionMapping mapping,
			       ActionForm form,
			       HttpServletRequest request,
			       HttpServletResponse response)
    throws IOException, ServletException
  {

    String action = (String) request.getParameter("action");

OnsiteServicesForm theForm =  (OnsiteServicesForm) form;

    if (action==null || action.trim().length()==0) {
	    action = "init";
	}

    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }   
	
    try {
      if ( action.equals("init") ||
       action.equals("onsite_services")) {
        UserShopLogic.initOnsiteServices(request, theForm);
        return mapping.findForward("display");
	  }
      else if (action.equals("send_onsite_service_request")) {
        ActionErrors ae = UserShopLogic.onsiteServices(request,theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
        }
        return mapping.findForward("display");
	  }
      else{
        ActionErrors ae = new ActionErrors();
        ae.add("error",new ActionError("error.systemError","Unknown action: ["+Utility.encodeForHTML(action)+"]"));
        saveErrors(request,ae);
        return mapping.findForward("display");
	  }
    }
    catch ( Exception e ) {
      e.printStackTrace();
      request.setAttribute(Constants.EXCEPTION_OBJECT, e);
      return mapping.findForward("error");
    }
  }
}
