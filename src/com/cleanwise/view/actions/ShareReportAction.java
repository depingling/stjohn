
package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.forms.ShareReportForm;
import com.cleanwise.view.logic.ShareReportLogic;
import com.cleanwise.view.utils.*;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import com.cleanwise.service.api.util.Utility;


/**
 * Implementation of <strong>Action</strong> that processes the
 * OrderOp manager page.
 */
public final class ShareReportAction extends ActionSuper {
    
    
    // ----------------------------------------------------- Public Methods
    
    
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
        
        // Determine the store manager f_action to be performed
        ShareReportForm pForm = (ShareReportForm) form;
        
        String action = request.getParameter("action");

        if (action == null) {
          String userToAdd = pForm.getUserToAdd();
          String groupToAdd = pForm.getGroupToAdd();
          if(Utility.isSet(userToAdd)) {
            action = "addUser";
          } else if(Utility.isSet(groupToAdd)) {
            action = "addGroup";
          } else {
            action = "init";
          }
        }

        // Process the f_action
        try {
            if (action.equals("init")){
              ShareReportLogic.init(request, pForm);
            } else if(action.equals("addUser")) {
              ShareReportLogic.addUser(request, pForm);
            } else if(action.equals("addGroup")) {
              ShareReportLogic.addGroup(request, pForm);
            } else if(action.equals("Remove Selected Users")) {
              ShareReportLogic.delUser(request, pForm);
            } else if(action.equals("Remove Selected Groups")) {
              ShareReportLogic.delGroup(request, pForm);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return (mapping.findForward("failure"));
        }
        return (mapping.findForward("success"));
    }
    
    String makeMessageString(HttpServletRequest request, ActionErrors ae) {
      org.apache.struts.util.MessageResources mr = getResources(request);
      String errorMessage = "";
      java.util.Iterator iter = ae.get();
      while (iter.hasNext()) {
        ActionError err = (ActionError) iter.next();
        String mess = getMessage(mr,request,err.getKey(),err.getValues());
        if(errorMessage.length()>0) errorMessage += "   "; //Character.LINE_SEPARATOR;        
        errorMessage += mess;
      }
      request.setAttribute("errorMessage", errorMessage);
      return errorMessage;
    }
}
