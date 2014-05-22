
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
import com.cleanwise.view.forms.ReportLocateForm;
import com.cleanwise.view.logic.ReportLocateLogic;
import com.cleanwise.view.utils.*;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;



/**
 * Implementation of <strong>Action</strong> that processes the
 * ReportLocate page.
 */
public final class ReportLocateAction extends ActionSuper {
    
    
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
        ReportLocateForm pForm = (ReportLocateForm) form;
        
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "init";
        }

        // Process the f_action
        try {
            if (action.equals("init")){
                ReportLocateLogic.init(request, pForm);
            } else if(action.equals("Set Filter")) {
                ReportLocateLogic.setFilter(request, pForm);
            } else if(action.equals("Clear Filter")) {
                ReportLocateLogic.clearFilter(request, pForm);
            } else if(action.equals("sort")) {
                ReportLocateLogic.sort(request, pForm);
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
