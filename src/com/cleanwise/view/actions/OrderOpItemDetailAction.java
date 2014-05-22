
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
import com.cleanwise.view.logic.OrderOpLogic;
import com.cleanwise.view.utils.*;

/**
 * Implementation of <strong>Action</strong> that displays an 
 * ItemStatus Detail 
 */
public final class OrderOpItemDetailAction extends ActionSuper {


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
        
        
    String orderItemStatusId = request.getParameter("id");
    if (orderItemStatusId == null) orderItemStatusId = "";
    
    if("view".equals(action) && ("".equals(orderItemStatusId) || "0".equals(orderItemStatusId))) {
        HttpSession session = request.getSession();
        orderItemStatusId = (String) session.getAttribute("OrderItemStatus.id");
        if(null == orderItemStatusId || "".equals(orderItemStatusId) || "0".equals(orderItemStatusId)) {
            return (mapping.findForward("previous"));            
        }
    }
    
    
    MessageResources mr = getResources(request);
        
    // Get the form buttons as specified in the properties file.
    
    String backStr = getMessage(mr,request,"admin.button.back");
    
    try {

      // view an existing order.
      if (action.equals("view")) {
        OrderOpLogic.getOrderItemDetail(request, form, orderItemStatusId);
        return (mapping.findForward("display"));
      }             
    
      else if (action.equals("sortitemdetails")) {
        OrderOpLogic.sortItemDetails(request, form);
        return (mapping.findForward("display"));
      }
      
      else if (action.equals(backStr)) {
        String fromPage = (String)request.getParameter("fromPage");   
        if (null == fromPage) {
            fromPage = new String(""); 
        }
        if ("order".equals(fromPage)) {
            return (mapping.findForward("order"));
        }
        else if ("invoice".equals(fromPage)) {
            return (mapping.findForward("invoice"));
        }
        else if ("purchaseOrder".equals(fromPage)) {
            return (mapping.findForward("purchaseOrder"));
        }
        else {
            return (mapping.findForward("previous"));
        }
      }      
      
      else {
        return (mapping.findForward("previous"));
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
