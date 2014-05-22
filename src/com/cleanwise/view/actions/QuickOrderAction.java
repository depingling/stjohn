
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
import com.cleanwise.view.forms.QuickOrderForm;
import com.cleanwise.view.logic.QuickOrderLogic;
import java.util.Iterator;
import java.util.Enumeration;
import com.cleanwise.view.utils.*;


/**
 * Implementation of <strong>Action</strong> that processes the
 * user shopping functions.
 *
 * @author durval
 */
public final class QuickOrderAction extends ActionSuper
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
    QuickOrderForm theForm = (QuickOrderForm) form;
    ActionErrors ae = new ActionErrors();
    String retAction = "display";

    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }

    if (action==null || action.trim().length()==0) action = "init";
    try {
      if ( action.equals("quickOrderInit") ) {
        ae = QuickOrderLogic.init(request,theForm);
        if(ae.size()>0) {
           saveErrors(request,ae);
//           retAction = "userHome";
        }
	  }
      else if (action.equals("quickOrderSubmit")) {
        if("addToCart".equals(command) ||
            request.getParameter("addToCart.x")!=null) {
          ae = QuickOrderLogic.addToCart(request,theForm);
        } else if("qo_addToModInvCart".equals(command) ||
            request.getParameter("qo_addToModInvCart.x")!=null) {
          ae = QuickOrderLogic.addToInventoryCart(request,theForm);
        } else if("expressCheckout".equalsIgnoreCase(command) ||
                   request.getParameter("expressCheckOut.x")!=null) {
          ae = QuickOrderLogic.addToCart(request,theForm);
          if(ae.size()==0 && !theForm.getDuplicationFlag()) {
            return mapping.findForward("checkout");
          }
        }
        if(ae.size()>0) {
          saveErrors(request,ae);
        }
	  }
      else if (action.equals("quickOrderResolveMfg")) {
    	  if("qo_addToModInvCart".equals(command)){
    		  ae = QuickOrderLogic.resolveMfg(request,theForm,true);
    	  }else if ("addToCart".equals(command)){
    		  ae = QuickOrderLogic.resolveMfg(request,theForm,false);
    	  }
        if(ae.size()>0) {
          saveErrors(request,ae);
        }
      }
      else if (action.equals("quickOrderClear")) {
        ae = QuickOrderLogic.clear(request,theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
        }
	  }
      else if (action.equals("addToNewXpedxCart")) {
        ae = QuickOrderLogic.addToNewXpedxCart(request,theForm);
        if(ae.size()>0) {
            saveErrors(request,ae);
        } else {
            theForm.clear();
            theForm.setIncPageSize(0);
            theForm.init();
        }
        return mapping.findForward("userHome");
      }
      else if (action.equals("addToNewXpedxInvCart")) {
        ae = QuickOrderLogic.addToNewXpedxInvCart(request,theForm);
        if(ae.size()>0) {
            saveErrors(request,ae);
        } else {
            theForm.clear();
            theForm.setIncPageSize(0);
            theForm.init();
        }
        return mapping.findForward("userHome");
      }
      else if (action.equals("addMoreFields")) {
          ae = QuickOrderLogic.addMoreFields(request,theForm);
          return mapping.findForward("userHome");
      }
      else if (action.equals("addToCartCheckout")) {
          ae = QuickOrderLogic.addToCart(request,theForm);
          if(ae.size()>0) {
              saveErrors(request,ae);
              return mapping.findForward("userHome");
          } else {
              theForm.clear();
              theForm.setIncPageSize(0);
              theForm.init();
              return mapping.findForward("checkout");
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
