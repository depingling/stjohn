
package com.cleanwise.view.actions;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.cleanwise.view.logic.OrderOpLogic;
import com.cleanwise.view.utils.SessionTool;

/**
 * Implementation of <strong>Action</strong> that saves a new
 * order note or updates an existing order note.
 */
public final class OrderOpNoteAction extends ActionSuper {
    private static final Logger log = Logger.getLogger(OrderOpNoteAction.class);


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
    if (action == null) action = "view";

    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
        return mapping.findForward("/userportal/logon");
    }      
        
        
    String noteId = request.getParameter("id");
    if (noteId == null) noteId = "";
    
    String orderId = request.getParameter("orderid");
    if (orderId == null) orderId = "";

    String invoiceDistId = request.getParameter("invoiceDistId");
    if (invoiceDistId == null) invoiceDistId = "";

    String orderItemId = request.getParameter("itemid");
    if (orderItemId == null) orderItemId = "";
    
    String noteType = request.getParameter("type");
    if (noteType == null) noteType = "order";
    
    
    MessageResources mr = getResources(request);
        
    // Get the form buttons as specified in the properties file.
    
    String backStr = getMessage(mr,request,"admin.button.back");
    String saveStr = getMessage(mr,request,"global.action.label.save");
	
    log.info ("action is: " + action
		+ " parameters: " + st.paramDebugString() );
    try {

        // view an existing order.
        if (action.equals("view")) {
            OrderOpLogic.getOrderNotes(request, form, orderId, orderItemId, noteId);
            return (mapping.findForward("display"));
        }             
        else if (action.equals("add")) {
        	log.info ("note action: 1" );
            OrderOpLogic.addOrderNote(request, form, orderId, orderItemId, noteType);
            return (mapping.findForward("detail"));
        }                 
        else if (action.equals("addInvoiceDistNote")) {
	    	log.info ("note action: 2" );
            OrderOpLogic.addInvoiceDistNote(request, form, invoiceDistId, noteType);
            return (mapping.findForward("detail"));
        }                 
        
        else if (action.equals(backStr)) {
            return (mapping.findForward("close"));
        }      
	  
        else if (action.equals("saveNote") || action.equals(saveStr)){	    
	    
	    	log.info ("note action: 2.1" );
            ActionErrors ae = OrderOpLogic.saveNote(request, form);
	    	log.info ("note action: 2.2" );
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return (mapping.findForward("detail"));	
            }
            return (mapping.findForward("close"));	
        }
	              
        else {
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
