
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
import com.cleanwise.view.logic.KnowledgeOpLogic;
import com.cleanwise.view.utils.*;

/**
 * Implementation of <strong>Action</strong> that saves a new
 * KnowledgeContentDetail or updates an existing KnowledgeContentDetail.
 */
public final class KnowledgeOpContentDetailAction extends ActionSuper {


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

    // Get the action and the KnowledgeId from the request.
    String action  = request.getParameter("action");
    if (action == null) action = "add";
    
    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }      
        
    String knowledgeId = request.getParameter("knowledgeid");
    if (knowledgeId == null) knowledgeId = "";
    
    String contentId = request.getParameter("id");
    if (contentId == null) contentId = "";
    
    MessageResources mr = getResources(request);
        
    // Get the form buttons as specified in the properties file.    
    String saveStr = getMessage(mr,request,"global.action.label.save");
    String backStr = getMessage(mr,request,"admin.button.back");
    String addContentStr = getMessage(mr,request,"admin.button.addContent");    
    
    if( ("edit".equals(action) || "view".equals(action)) && ("".equals(contentId) || "0".equals(contentId))) {
        action = "add";
    }
    
    if("".equals(knowledgeId) || "0".equals(knowledgeId) ) {
        HttpSession session = request.getSession();
        knowledgeId = (String) session.getAttribute("Knowledge.id");
    }
    if(null == knowledgeId || "".equals(knowledgeId) || "0".equals(knowledgeId)) {
        action = backStr;
    }
        
    try {

      // Add a new advisor.
      if (action.equals("add")) {
        KnowledgeOpLogic.addKnowledgeContent(request, form, knowledgeId);
        return (mapping.findForward("display"));
      }

      // Edit an existing knowledgeDetail.
      else if (action.equals("edit") || action.equals("view") ) {
        KnowledgeOpLogic.editKnowledgeContent(request, form, knowledgeId, contentId);  
        return (mapping.findForward("display"));
      }
      
      // Save the new or updated knowledgeDetail.
      else if (action.equals(saveStr)) {

        ActionErrors ae = KnowledgeOpLogic.saveKnowledgeContent(request, form);
        if (ae.size() > 0 ) {
            saveErrors(request, ae);
            return (mapping.findForward("display"));            
        }
        else {
            return (mapping.findForward("detail"));           
        }
      }      
             
      else if (action.equals(backStr)) {
        return (mapping.findForward("detail"));
      }      
      
      // Users should never get here, but if they do, perform an add.
      else {
        KnowledgeOpLogic.addKnowledgeDetail(request, form);
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
