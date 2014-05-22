
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
import com.cleanwise.view.forms.GenericReportForm;
import com.cleanwise.view.logic.GenericReportLogic;
import com.cleanwise.view.utils.*;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;



/**
 * Implementation of <strong>Action</strong> that processes the
 * OrderOp manager page.
 */
public final class GenericReportAction extends ActionSuper {
    
    
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
        GenericReportForm pForm = (GenericReportForm) form;
        
        String action = request.getParameter("action");
        String setFilter = request.getParameter("setFilter");
        if (action == null) {
          if("clearFilter".equals(setFilter)) {
            action = "Clear Filter";
          } else if("setArchiveFilter".equals(setFilter)) {
            action = "Set Archive Filter";
          } else if("clearArchiveFilter".equals(setFilter)) {
            action = "Clear Archive Filter";
          } else {
            action = "init";
          }
          
        }
        request.setAttribute("action", action);
        // Process the f_action
        try {
            if (action.equals("init")){
                GenericReportLogic.init(request, pForm);
            } else if(action.equals("Set Filter")) {
                GenericReportLogic.setFilter(request, pForm);
            } else if(action.equals("Clear Filter")) {
                GenericReportLogic.clearFilter(request, pForm);
            } else if(action.equals("sort")) {
                GenericReportLogic.sort(request, pForm);
            } else if(action.equals("report")) {
                GenericReportLogic.loadReport(request, pForm);
            } else if(action.equals("Save")) {
                ActionErrors ae = GenericReportLogic.saveReport(request, pForm);
                if(ae.size()>0) {
                  saveErrors(request,ae);
                  return  (mapping.findForward("failure"));
                }
            } else if(action.equals("Clone")) {
                ActionErrors ae = GenericReportLogic.cloneReport(request, pForm);
                if(ae.size()>0) {
                  saveErrors(request,ae);
                  return  (mapping.findForward("failure"));
                }
            } else if(action.equals("Delete")) {
                ActionErrors ae = GenericReportLogic.deleteReport(request, pForm);
                if(ae.size()>0) {
                  saveErrors(request,ae);
                  return  (mapping.findForward("failure"));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            //return (mapping.findForward("failure"));
            throw new ServletException(e.getMessage());
        }
        return (mapping.findForward("success"));
    }
    /*
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
     */
}
