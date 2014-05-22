
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
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.logic.PipelineMgrLogic;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.PipelineMgrForm;

/**
 * Implementation of <strong>Action</strong> that saves a new
 * freightTable detail or updates an existing freightTable detail.
 */
public final class PipelineMgrAction extends ActionSuper {


  // ------------------------------------------------------------ Public Methods


  /**
   * Process the specified HTTP request, and create the corresponding HTTP
   * response (or forward to another web component that will create it).
   * Return an <code>ActionForward</code> instance describing where and how
   * control should be forwarded, or <code>null</code> if the response has
   * already been completed.
   *
   * @param mapping
   *  The ActionMapping used to select this instance
   * @param actionForm
   *  The optional ActionForm bean for this request (if any)
   * @param request
   *  The HTTP request we are processing
   * @param response
   *  The HTTP response we are creating
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

    // Get the action and the freightTableId from the request.
    String action  = request.getParameter("action");
    String action1  = request.getParameter("action1");
    PipelineMgrForm pForm = (PipelineMgrForm) form;
    if(action==null && "Search".equals(action1)) {
      action = "Search";
    }
    pForm.setAction(action);
    ActionErrors ae = new ActionErrors();
    try {
      if (action==null || action.equals("init")) {
        ae = PipelineMgrLogic.init(request, form);
      } else if(action.equals("Search")) {
        ae = PipelineMgrLogic.search(request, form);
      } else if(action.equals("Copy Selected")) {
        ae = PipelineMgrLogic.copy(request, form);
      } else if(action.equals("Paste")) {
        ae = PipelineMgrLogic.paste(request, form);
      } else if(action.equals("Save")) {
        ae = PipelineMgrLogic.save(request, form);
      } else if(action.equals("Delete Selected")) {
        ae = PipelineMgrLogic.delete(request, form);
      } else if(action.equals("New")) {
        ae = PipelineMgrLogic.createNew(request, form);
      } else {
        return (mapping.findForward("failure"));
      }
      if(ae.size()>0) {
        saveErrors(request, ae);
        return (mapping.findForward("failure"));     
      } 

      return (mapping.findForward("success"));
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("errorobject", e);
      return (mapping.findForward("error"));
    }
  }

  
}

