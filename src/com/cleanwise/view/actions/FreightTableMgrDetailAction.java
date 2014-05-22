
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
import com.cleanwise.view.logic.FreightTableMgrLogic;
import com.cleanwise.view.utils.*;

/**
 * Implementation of <strong>Action</strong> that saves a new
 * freightTable detail or updates an existing freightTable detail.
 */
public final class FreightTableMgrDetailAction extends ActionSuper {


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
    if (action == null) action = "add";

    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }    
        
        
    String freightTableId = request.getParameter("id");
    if (freightTableId == null) freightTableId = "";
    
    if("edit".equals(action) && ("".equals(freightTableId) || "0".equals(freightTableId))) {
        HttpSession session = request.getSession();
        freightTableId = (String) session.getAttribute("FreightTable.id");
        if(null == freightTableId || "".equals(freightTableId) || "0".equals(freightTableId)) {
            action = "add";
        }
    }
    
    String change = request.getParameter("change");
    if(null != change && "type".equals(change)) {
        return (mapping.findForward("display"));
    }
    
    MessageResources mr = getResources(request);
        
    // Get the form buttons as specified in the properties file.
    
    String saveStr = getMessage(mr,request,"global.action.label.save");
    String deleteStr = getMessage(mr,request,"global.action.label.delete");
    String removeStr = getMessage(mr,request,"admin.button.remove");
    String addMoreStr = getMessage(mr,request,"admin.button.addMoreCriteria");
    
    try {

      // Add a new advisor.
      if (action.equals("add")) {
        FreightTableMgrLogic.addFreightTable(request, form);
        return (mapping.findForward("display"));
      }

      // Edit an existing freightTable.
      else if (action.equals("edit")) {
        FreightTableMgrLogic.editFreightTable(request, form, freightTableId);
        return (mapping.findForward("display"));
      }
      
      // Save the new or updated freightTable.
      else if (action.equals(saveStr)) {

        ActionErrors ae = FreightTableMgrLogic.saveFreightTable(request, form);
        if (ae.size() > 0 ) {
            saveErrors(request, ae);
            return (mapping.findForward("display"));            
        }
        
        String createfrom = request.getParameter("createfrom");
        if (null == createfrom || "".equals(createfrom)) {
            return (mapping.findForward("save"));
        }
        else {
            FreightTableMgrLogic.resetFromForm(request, form); 
            return (mapping.findForward(createfrom));
        }
      }      
       
      
      else if (action.equals(addMoreStr)) {
        FreightTableMgrLogic.addMoreCriteria(request, form);
        return (mapping.findForward("display"));
      }      

      
      // Users should never get here, but if they do, perform an add.
      else {
        FreightTableMgrLogic.addFreightTable(request, form);
        return (mapping.findForward("display"));
      }
    }

    // Catch all exceptions here.
    catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("errorobject", e);
      return (mapping.findForward("error"));
    }

  }


}
