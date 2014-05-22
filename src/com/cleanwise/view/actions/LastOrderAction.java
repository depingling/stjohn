
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
import com.cleanwise.view.forms.LastOrderForm;
import com.cleanwise.view.logic.LastOrderLogic;
import java.util.Iterator;
import java.util.Enumeration;
import com.cleanwise.view.utils.*;


/**
 * Implementation of <strong>Action</strong> that processes the
 * user shopping functions.
 *
 * @author durval
 */
public final class LastOrderAction extends ActionSuper
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

    String action = request.getParameter("action");
    String command = request.getParameter("command");
    LastOrderForm theForm = (LastOrderForm) form;
    ActionErrors ae = new ActionErrors();
    String retAction = "display";
   
    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }   


    if (action==null || action.trim().length()==0) action = "init";
    try {
      if ( action.equals("lastOrderInit") ) {
        ae = LastOrderLogic.init(request,theForm);
        if(ae.size()>0) {
           saveErrors(request,ae);
        }
      }
      else if (action.equals("lastOrderSubmit")) {
        if("addToCart".equals(command) ||
           request.getParameter("addToCart.x")!=null) {
          ae = LastOrderLogic.addToCart(request,theForm);
        }
        else if("lot_addToModInvCart".equals(command) ||
           request.getParameter("lot_addToModInvCart.x")!=null) {
          ae = LastOrderLogic.addToInventoryCart(request,theForm);
        }
        else if("recalc".equals(command) ||
                request.getParameter("recalc.x")!=null) {
          ae = LastOrderLogic.recalculate(request,theForm);
        }
        else{
          ae = LastOrderLogic.sort(request,theForm);
        }
        if(ae.size()>0) {
          saveErrors(request,ae);
        }
	  }
      else if (action.equals("lastOrderClear")) {
        ae = LastOrderLogic.clear(request,theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
        }
	  }
      else{
        ae.add("error",new ActionError("error.systemError","Unknown action: ["+Utility.encodeForHTML(action)+"]"));
        saveErrors(request,ae);
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
