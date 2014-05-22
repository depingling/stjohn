/*
 * ReturnsOpAction.java
 *
 * Created on January 17, 2003, 1:18 PM
 */

package com.cleanwise.view.actions;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.*;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.logic.PurchaseOrderOpLogic;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.view.forms.PurchaseOrderOpDetailForm;
/**
 *
 * @author  bstevens
 */
public class ReturnsOpAction extends ActionSuper {
    
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
        //get the po id
        String purchaseOrderStatusId = request.getParameter("id");
        if (purchaseOrderStatusId == null) purchaseOrderStatusId = "";
        if("view".equals(action) && ("".equals(purchaseOrderStatusId) || "0".equals(purchaseOrderStatusId))) {
            HttpSession session = request.getSession();
            purchaseOrderStatusId = (String) session.getAttribute("purchaseOrderStatus.id");
        }
        //get the return id
        String returnRequestId = request.getParameter("returnReqId");
        if (returnRequestId == null) returnRequestId = "";
        if("view".equals(action) && ("".equals(returnRequestId) || "0".equals(returnRequestId))) {
            HttpSession session = request.getSession();
            returnRequestId = (String) session.getAttribute("returnRequest.id");
        }
        //if neither id was supplied go back to the list page
        //if((null == returnRequestId || "".equals(returnRequestId) || "0".equals(returnRequestId))
        //  &&(null == purchaseOrderStatusId || "".equals(purchaseOrderStatusId) || "0".equals(purchaseOrderStatusId))) {
         //       return (mapping.findForward("list"));
        //}
        
        MessageResources mr = getResources(request);
        // Get the form buttons as specified in the properties file.
        String backStr = getMessage(mr,request,"admin.button.back");
        String createStr = getMessage(mr,request,"admin.button.create");
        String updateStr = getMessage(mr,request,"admin.button.submitUpdates");
        
        String viewStr = "view";
        String sortItemsStr = "sortitems";
        //String printStr = "print";
        String printReturnStr = "printReturn";
        //String saveNoteStr = "saveNote";
        String createReturn = "createReturn";
        String createFailed = "createFailed";
        
        try {
            PurchaseOrderOpLogic.initConstantList(request);
            // view an existing order.
            if (action.equals(viewStr)) {
                ActionErrors ae = PurchaseOrderOpLogic.getReturnRequestDetail(request, form,returnRequestId);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("display"));
            }

            //indicates we want to start a new return
            if (action.equals(createReturn)) {
                
                ActionErrors ae = PurchaseOrderOpLogic.getPurchaseOrderStatusDetail(request, form, purchaseOrderStatusId);
                //****VERY IMPORTANT, This method must be called *AFTER* the poform is initialized as
                //it uses the data in that form to initialize the return request
                PurchaseOrderOpLogic.initEmptyReturnRequestDetail(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                //PurchaseOrderOpLogic.getReturnRequestDetail(request, form,returnRequestId);
                return (mapping.findForward("display"));
            }
            //indicates for us to save the return
            if (action.equals(createStr)) {
                ActionErrors ae = PurchaseOrderOpLogic.saveReturnRequest(request, form, true);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    request.setAttribute("returns.new", "true");
                    return (mapping.findForward("createFailed"));
                }else{
                    return (mapping.findForward("success"));
                }
            }
            
            if (action.equals(createFailed)) {
                return (mapping.findForward("display"));
            }
            
            if (action.equals(updateStr)) {
                ActionErrors ae = PurchaseOrderOpLogic.saveReturnRequest(request, form, false);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("display"));
                }else{
                    return (mapping.findForward("success"));
                }
            }
            
            else if (action.equals(sortItemsStr)) {
                ActionErrors ae = PurchaseOrderOpLogic.sortItems(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("display"));
            }
            
            /*else if (action.equals(printStr)) {
                ActionErrors ae = PurchaseOrderOpLogic.printDetail(response,request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return null;
            }*/
            
            else if (action.equals(printReturnStr)) {
                ActionErrors ae = PurchaseOrderOpLogic.printReturnDetail(response,request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return null;
            }
            
            else if (action.equals(backStr)) {
                return (mapping.findForward("list"));
            }
            
            
            /*else if (action.equals(saveNoteStr)){
                ActionErrors ae = PurchaseOrderOpLogic.updateNote(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("list"));
            }*/
            
            else {
                return (mapping.findForward("list"));
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
