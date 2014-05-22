
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
import com.cleanwise.view.logic.PurchaseOrderOpLogic;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.util.RefCodeNames;

public final class PurchaseOrderOpDetailAction extends ActionSuper {
    
    
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
        
        
        String purchaseOrderStatusId = request.getParameter("id");
        if (purchaseOrderStatusId == null) purchaseOrderStatusId = "";
        
        if("view".equals(action) && ("".equals(purchaseOrderStatusId) || "0".equals(purchaseOrderStatusId))) {
            HttpSession session = request.getSession();
            purchaseOrderStatusId = (String) session.getAttribute("purchaseOrderStatus.id");
            if(null == purchaseOrderStatusId || "".equals(purchaseOrderStatusId) || "0".equals(purchaseOrderStatusId)) {
                return (mapping.findForward("list"));
            }
        }
        
        
        MessageResources mr = getResources(request);
        
        // Get the form buttons as specified in the properties file.
        
        String backStr = getMessage(mr,request,"admin.button.back");
        String saveStr = getMessage(mr,request,"global.action.label.save");
        String ackPoStr = "Acknowledge Po";
        String viewStr = "view";
        String setShipStatusAllStr = "Set Shipping Status For All";
        String setTargetShipDteAllStr = "Set Target Ship Date For All";
        String setOpenLineStatusAllStr = "Set Open Line Status For All";
        String savePoItemsStr = "savePoItems";
        String deleteOrderItemActionsStr = getMessage(mr,request,"button.delete.order.item.actions");
        String sortItemsStr = "sortitems";
        String printStr = "print";
        String saveNoteStr = "saveNote";
        
        
        try {
            ActionErrors ae = null;
            String forward = "display";
            // view an existing order.
            if (action.equals(viewStr)) {
                ae = PurchaseOrderOpLogic.getPurchaseOrderStatusDetail(request, form, purchaseOrderStatusId);
            }else if (action.equals(ackPoStr)) {
                ae = PurchaseOrderOpLogic.updateStatus(request, form);
            }else if (action.equals(setShipStatusAllStr)) {
                ae = PurchaseOrderOpLogic.updateItemsShippingStatus(request, form);
            }else if (action.equals(setTargetShipDteAllStr)) {
                ae = PurchaseOrderOpLogic.updateItemsTargetShipDate(request, form);
            }else if (action.equals(setOpenLineStatusAllStr)) {
                ae = PurchaseOrderOpLogic.updateItemsOpenPoLineStatus(request, form);
            }else if (action.equals(savePoItemsStr) || action.equals(saveStr)) {
                ae = PurchaseOrderOpLogic.updatePoItems(request, form);
            }else if (action.equals(sortItemsStr)) {
                ae = PurchaseOrderOpLogic.sortItems(request, form);
            }else if (action.equals(deleteOrderItemActionsStr)){
                ae = PurchaseOrderOpLogic.deleteOrderItemActions(request,form);
            }else if (action.equals(printStr)) {
                ae = PurchaseOrderOpLogic.printDetail(response,request, form);
                //printing commits the request
                if (ae.size() == 0) {
                    return null;
                }
            }else if (action.equals(backStr)) {
                forward = "list";
            }else {
                forward = "list";
            }
            if (ae != null && ae.size() > 0) {
                saveErrors(request, ae);
            }
            return (mapping.findForward(forward));
        }
        
        // Catch all exceptions here.
        catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorobject", e);
            return (mapping.findForward("error"));
        }
    }
}
