
package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.logic.PurchaseOrderOpLogic;
import com.cleanwise.view.utils.*;

/**
 * Implementation of <strong>Action</strong> that processes the
 * PurchaseOrderOp manager page.
 */
public final class PurchaseOrderDistributorSearchAction extends ActionSuper {
    
    
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
        // Determine the store manager action to be performed
        String action = request.getParameter("action");
        if (action == null) action = "init";
        
        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
            return mapping.findForward("/userportal/logon");
        }
        
        String initStr = "init";
        String searchStr = "Search";
        String sortStr = "sort";
        String printLableStr = getResources(request).getMessage("distributor.button.print.label");
        String next = getResources(request).getMessage("global.action.label.next");
        String pendingManifest = getResources(request).getMessage("distributor.button.pending.manifest");
        String manifestComplete = getResources(request).getMessage("distributor.button.manifesting.complete");
        
        // Process the action
        try {
            ActionErrors ae = null;
            if (action.equals(initStr)) {
                PurchaseOrderOpLogic.init(request, form);
            }else if (action.equals(searchStr)) {
                ae = PurchaseOrderOpLogic.searchPurchaseOrdersDistributorRestricted(request, form);
            }else if (action.equals(pendingManifest)){
                ae = PurchaseOrderOpLogic.fetchAllPOsPendingManifestDistributorRestricted(request, form);
            }else if (action.equals(sortStr)) {
                PurchaseOrderOpLogic.sort(request, form);
            }else if (action.equals(manifestComplete)) {
                ae = PurchaseOrderOpLogic.processManifestCompleteRequest(request);
            }else if (action.equals(next)) {
                ae = PurchaseOrderOpLogic.initManifestDataEntry(request, form);
                if (ae.size() == 0) {
                    //for now skip the manifest data entry step
                    ae = PurchaseOrderOpLogic.printManifestLabels(request, response, form);
                    if (ae.size() == 0) {
                        return null;
                    }
                }
            }else if (action.equals(printLableStr) || action.equals(next)){
                ae = PurchaseOrderOpLogic.printManifestLabels(request, response, form);
                //printing manifest labels closes and commits the output stream
                if (ae.size() == 0) {
                    return null;
                }
            }else {
                PurchaseOrderOpLogic.init(request, form);
            }
            
            if (ae!=null && ae.size() > 0) {
                saveErrors(request, ae);
                return (mapping.findForward("failure"));
            }
            return (mapping.findForward("success"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return (mapping.findForward("failure"));
        }
    }
}
