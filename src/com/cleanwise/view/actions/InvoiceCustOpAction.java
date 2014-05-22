/*
 * InvoiceCustOpAction.java
 *
 * Created on July 25, 2003, 10:06 PM
 */

package com.cleanwise.view.actions;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.cleanwise.view.utils.*;
import java.io.IOException;
import com.cleanwise.view.logic.InvoiceOpLogic;

/**
 *
 * @author  bstevens
 */
public class InvoiceCustOpAction extends ActionSuper {
    
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
        if (action == null) action = "init";
        
        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
            return mapping.findForward("/userportal/logon");
        }
        
        MessageResources mr = getResources(request);
        String printStr = "print";
        String viewStr = "view";
        String searchStr = "Search";
        String printAll = "printAll";
        ActionErrors ae=null;
        try {
            if (action.equals("init")) {
                
            }else if (action.equals(searchStr)) {
                ae = InvoiceOpLogic.searchCustomerInvoices(request, form);
            }else if (action.equals(viewStr)) {
                ae = InvoiceOpLogic.fetchCustomerInvoice(request, form);
            }else if (action.equals(printStr)){
                ae = InvoiceOpLogic.printCustomerInvoice(request, response, form);
                //if there were no problems do not trry and route the request any further, as printing
                //has closed and commited the request.
                if(ae == null || ae.size() == 0){
                    return null;
                }
            }else if(action.equals(printAll)){
                ae = InvoiceOpLogic.printAllCustomerInvoice(request, response, form);
                //if there were no problems do not trry and route the request any further, as printing
                //has closed and commited the request.
                if(ae == null || ae.size() == 0){
                    return null;
                }
            }
            
            if (ae!=null && ae.size() > 0) {
                saveErrors(request, ae);
                return (mapping.findForward("failure"));
            }
            return (mapping.findForward("success"));
            
            // Catch all exceptions here.
        }catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorobject", e);
            ae = new ActionErrors();
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.genericError",e.getMessage()));
            saveErrors(request, ae);
            return (mapping.findForward("failure"));
        }
    }
    
}
