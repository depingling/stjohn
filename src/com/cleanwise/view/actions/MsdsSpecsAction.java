
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
import com.cleanwise.view.forms.MsdsSpecsForm;
import com.cleanwise.view.logic.MsdsSpecsLogic;
import java.util.Iterator;
import java.util.Enumeration;
import com.cleanwise.view.utils.*;

/**
 * Implementation of <strong>Action</strong> that processes the
 * user shopping functions.
 *
 * @author durval
 */
public final class MsdsSpecsAction extends ActionSuper
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

    MsdsSpecsForm theForm = (MsdsSpecsForm) form;

    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }   

    ActionErrors ae = new ActionErrors();
    String retAction = "display";
    if (action==null || action.trim().length()==0) action = "init";
    try {
      if ( action.equals("init") ) {
        ae = MsdsSpecsLogic.init(request,theForm);
        if(ae.size()>0) {
           saveErrors(request,ae);
           retAction = "error";
        }
   	  }
      else if(action.equals("control")) {
        if("search".equals(command) || request.getParameter("search.x")!=null) {
          ae = MsdsSpecsLogic.search(request,theForm);
        }
        else {
          ae = MsdsSpecsLogic.sortCatalogItems(request,theForm);
        } 
        if(ae.size()>0) {
          saveErrors(request,ae);
        }
	  }
      else if (action.equals("addDoc")) {
        ae = MsdsSpecsLogic.addDoc(request,theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
        }else{
            ae = MsdsSpecsLogic.init(request,theForm);
            if(ae.size()>0) {
              saveErrors(request,ae);
            }
        }
	  }
      else if (action.equals("removeDoc")) {
        ae = MsdsSpecsLogic.removeDoc(request,theForm);
        if(ae.size()>0) {
          saveErrors(request,ae);
        }else{
            ae = MsdsSpecsLogic.init(request,theForm);
            if(ae.size()>0) {
              saveErrors(request,ae);
            }
        }
	  }
      else if (action.equals("goPage")) {
        ae = MsdsSpecsLogic.goPage(request,theForm);
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
    ActionForward af = mapping.findForward(retAction);
    return af;

  }
}
