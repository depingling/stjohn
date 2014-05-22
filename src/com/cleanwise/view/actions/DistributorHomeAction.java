/*
 * DistributorHomeAction.java
 *
 * Created on October 11, 2002, 4:47 PM
 */

package com.cleanwise.view.actions;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.utils.*;

/**
 *
 * @author  bstevens
 */
public class DistributorHomeAction extends ActionSuper {
    
    /**
     *  Process the specified HTTP request, and create the corresponding HTTP
     *  response (or forward to another web component that will create it).
     *  Return an <code>ActionForward</code> instance describing where and how
     *  control should be forwarded, or <code>null</code> if the response has
     *  already been completed.
     *
     *@param  mapping               The ActionMapping used to select this
     *      instance
     *@param  request               The HTTP request we are processing
     *@param  response              The HTTP response we are creating
     *@param  form                  Description of Parameter
     *@return                       Description of the Returned Value
     *@exception  IOException       if an input/output error occurs
     *@exception  ServletException  if a servlet exception occurs
     */
    public ActionForward performSub(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws IOException, ServletException {
        
        // Determine the store manager action to be performed
        String action = request.getParameter("action");
        if (action == null) {
            action = "init";
        }
        action.trim();
        
        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
            return mapping.findForward("/userportal/logon");
        }
        
        MessageResources mr = getResources(request);
        try {
            ActionErrors ae = null;
            /*if (action.equals(searchStr)){
                //ae = PurchaseOrderOpLogic.searchPurchaseOrderTracker(request, form);
            }else if (action.equals(sortStr)){
                //PurchaseOrderOpLogic.sortPurchaseOrderTracker(request, form);
            }else if (action.equals(saveStr)){
                //PurchaseOrderOpLogic.savePurchaseOrderTrackerData(request, form);
            }else{
                //PurchaseOrderOpLogic.init(request, form);
            }*/
            if (ae!= null && ae.size() > 0) {
                saveErrors(request, ae);
                return (mapping.findForward("failure"));
            }
            return (mapping.findForward("success"));
        }
        catch (Exception e) {
            ActionErrors ae = new ActionErrors();
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError(e.getMessage()));
            e.printStackTrace();
            saveErrors(request, ae);
            return (mapping.findForward("failure"));
        }
    }
    
}
