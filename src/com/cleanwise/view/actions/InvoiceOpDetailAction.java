
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
import com.cleanwise.view.logic.InvoiceOpLogic;
import com.cleanwise.view.logic.PurchaseOrderOpLogic;
import com.cleanwise.view.utils.*;

/**
 * Implementation of <strong>Action</strong> that saves a new
 * freightTable detail or updates an existing freightTable detail.
 */
public final class InvoiceOpDetailAction extends ActionSuper {
    
    
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
        if (action == null) action = "init";
        
        //this may be overidden latter at the jsp
        request.setAttribute("detailAction","view");
        
        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
            return mapping.findForward("/userportal/logon");
        }
        
        String orderStatusId = request.getParameter("id");
        if (orderStatusId == null) orderStatusId = "";
        
        if("view".equals(action) && ("".equals(orderStatusId) || "0".equals(orderStatusId))) {
            HttpSession session = request.getSession();
            orderStatusId = (String) session.getAttribute("OrderStatus.id");
            if(null == orderStatusId || "".equals(orderStatusId) || "0".equals(orderStatusId)) {
                return (mapping.findForward("list"));
            }
        }
        
        
        MessageResources mr = getResources(request);
        
        // Get the form buttons as specified in the properties file.
        
        String backStr = getMessage(mr,request,"admin.button.back");
        String saveStr = getMessage(mr,request,"global.action.label.save");
        String updateStr = getMessage(mr,request,"admin.button.submitUpdates");
        String searchStr = "Search Skus";
        String lookupStr = getMessage(mr,request,"invoice.button.lookupPo");
        
        try {
            if (action.equals("init")) {
                PurchaseOrderOpLogic.init(request, form);
                return (mapping.findForward("success"));
            }
            else if (action.equals("Search")) {
                ActionErrors ae = PurchaseOrderOpLogic.search(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("success"));
            }
            else if (action.equals("sort")) {
                PurchaseOrderOpLogic.sort(request, form);
                return (mapping.findForward("success"));
            }
            
            // view an existing order.
            else if (action.equals("view")) {
                String purchaseOrderStatusId = request.getParameter("id");
                if (purchaseOrderStatusId == null) purchaseOrderStatusId = "";
                if (purchaseOrderStatusId. trim().equals("")){
                    return (mapping.findForward("success"));
                }
                InvoiceOpLogic.getDetail(request, form, purchaseOrderStatusId,true);
                return (mapping.findForward("display"));
            }
            else if (action.equals("print")) {
                ActionErrors ae = PurchaseOrderOpLogic.printDetail(response,request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return null;
            }else if (action.equals("sortitems")) {
                InvoiceOpLogic.sortItems(request, form);
                return (mapping.findForward("display"));
            }else if (action.equals(backStr)) {
                return (mapping.findForward("list"));
            }else if (action.equals(searchStr)) {
                InvoiceOpLogic.searchDistSku(request, form);
                return (mapping.findForward("display"));
            }else if (action.equals(saveStr) || action.equals(updateStr)) {
                ActionErrors ae = InvoiceOpLogic.updateDistInvoice(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("display"));
                }else{
                    return (mapping.findForward("view"));
                }
            }else if (action.equals("saveNote") || action.equals(saveStr)){
                ActionErrors ae = InvoiceOpLogic.updateNote(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("list"));
            }else if (action.equals(lookupStr)){
                ActionErrors ae = InvoiceOpLogic.lookupByPurchaseOrder(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("list"));
                }
                return (mapping.findForward("display"));
            }else {
                return (mapping.findForward("list"));
            }
        }
        
        // Catch all exceptions here.
        catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorobject", e);
            ActionErrors ae = new ActionErrors();
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.genericError",e.getMessage()));
            saveErrors(request, ae);
            return (mapping.findForward("error"));
        }
    }
    
    
}
