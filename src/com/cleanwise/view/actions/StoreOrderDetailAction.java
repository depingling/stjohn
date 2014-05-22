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
import com.cleanwise.view.logic.StoreOrderLogic;
import com.cleanwise.view.forms.StoreOrderDetailForm;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.SiteView;
import com.cleanwise.service.api.value.SiteViewVector;

/**
 * Implementation of <strong>Action</strong> that saves a new
 * freightTable detail or updates an existing freightTable detail.
 */
public final class StoreOrderDetailAction extends ActionSuper {


    // ---------------------------------------------------- Public Methods


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
        logm(" VVVVVVVVVVVVVVVVVVVVV 0");
        // Get the action and the freightTableId from the request.
        String action  = request.getParameter("action");
    StoreOrderDetailForm detailForm = (StoreOrderDetailForm) form;
    detailForm.setLastAction(action);

    String orderItemIdToView = null;
    if ((action == null) || (action.length() < 1) || ("".equals(action)) ) {
    	orderItemIdToView = detailForm.getOrderItemIdToView();
        if ((orderItemIdToView != null) || (orderItemIdToView.length() > 0) ) {
        	action = "viewOrderItemDetail";
        }
    }
    if ((action == null) || (action.length() < 1) || ("".equals(action)) ) {
    		action = "view";
    }

    logm(action);
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
                detailForm.setFullControlFl(false);
                return (mapping.findForward("list"));
            }
        }


        MessageResources mr = getResources(request);

        // Get the form buttons as specified in the properties file.

        String backStr = getMessage(mr,request,"admin.button.back");
        String saveStr = getMessage(mr,request,"global.action.label.save");
        String approveStr = getMessage(mr,request,"admin.button.approveOrder");
  String updateItemsStr = getMessage(mr,request,"admin.button.updateOrderItems");
  String cancelOrder = getMessage(mr,request,"admin.button.cancelOrder");
  String cancelStr = getMessage(mr,request,"admin.button.cancelSelected");
        String rejectStr = getMessage(mr,request,"admin.button.rejectOrder");
        String mvToPendingReviewStr = getMessage(mr,request,"admin.button.moveOrderToPendingReview");
        String checkStr = getMessage(mr,request,"admin.button.checkContract");
        String moveStr = getMessage(mr,request,"admin.button.moveOrder");
        String moveSiteStr = getMessage(mr,request,"admin.button.moveOrderAndSite");
        String addStr = getMessage(mr,request,"admin.button.addItem");
        String reorderStr = getMessage(mr,request,"global.action.label.reorder");
  String addCustomerCommentStr = getMessage(mr,request,"button.addCustomerComment");
  String refreshStr = getMessage(mr,request,"button.refresh");
  String receiveUpdateStr = getMessage(mr,request,"button.update.receiving");
  String dontSendToDistributorStr = getMessage(mr,request,"admin.button.dontSendToDistributor");

        logm(" action is: " + action);

        try {

            // view an existing order.
            if (action.equals("view") || action.equals(refreshStr) ||
          action.equals("button.refresh")) {
            	ActionErrors ae = StoreOrderLogic.getOrderStatusDetail(request, form, orderStatusId);
            	if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("list"));
                }
                detailForm.setFullControlFl(true);
                return (mapping.findForward("display"));
           } else if ("Locate Site".equals(action)
                    || "Search".equals(action)
                    || "Cancel".equals(action)) {
                StoreOrderLogic.restoreCheckBoxesState(form);
                return (mapping.findForward("display"));
            } else if (action.equals("Return Selected")) {
                String submitFormIdent = request.getParameter("jspSubmitIdent");
                StoreOrderLogic.restoreCheckBoxesState(form);
                if (("#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_SITE_FORM).equals(submitFormIdent)) {
                   SiteViewVector sites =  detailForm.getLocateStoreSiteForm().getSitesToReturn();
                   if (sites != null && sites.size() > 0) {
                       SiteView site = (SiteView) sites.get(0);
                       detailForm.setSiteId("" + site.getId());
                   } else {
                       detailForm.setSiteId("");
                   }
                }
                return (mapping.findForward("display"));
            }
            else if (action.equals("Full Control")) {
                detailForm.setFullControlFl(true);
                return (mapping.findForward("display"));
            }
            else if (action.equals("sortitems")) {
                StoreOrderLogic.sortItems(request, form);
                return (mapping.findForward("display"));
            }

            else if (action.equals(backStr) || action.equals("admin.button.back")) {
                return (mapping.findForward("list"));
            }

            else if (action.equals(checkStr) || action.equals("admin.button.checkContract")) {
                ActionErrors ae = StoreOrderLogic.checkContractForPendingApprovalOrder2(request, form, orderStatusId);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("display"));
                }
                else {
                    return (mapping.findForward("display"));
                }
            }

            else if (action.equals(approveStr) || action.equals("admin.button.approveOrder")) {
                ActionErrors ae =
                    StoreOrderLogic.approvePendingApprovalOrder(request, form, orderStatusId);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("display"));
                }
                else {
                    return (mapping.findForward("detail"));
                }
            }

            else if (action.equals(rejectStr) || action.equals("admin.button.rejectOrder")) {
                ActionErrors ae =
                    StoreOrderLogic.changePendingApprovalOrderStatus(request, form, orderStatusId, RefCodeNames.ORDER_STATUS_CD.REJECTED);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("display"));
                }
                else {
                    return (mapping.findForward("detail"));
                }
            }
            else if (action.equals(mvToPendingReviewStr) ||
              action.equals("admin.button.moveOrderToPendingReview")) {
                ActionErrors ae =
                    StoreOrderLogic.changePendingApprovalOrderStatus
                    (request,form,
                     orderStatusId,
                     RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("display"));
                }
                else {
                    return (mapping.findForward("detail"));
                }
            }
            else if (action.equals(moveStr) || action.equals("admin.button.moveOrder")) {
                logm("calling StoreOrderLogic.updateOrder");

                ActionErrors ae = StoreOrderLogic.updateOrder(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    logm("calling StoreOrderLogic.updateOrder, return display");
                    return (mapping.findForward("display"));
                }
                else {
                    logm("calling StoreOrderLogic.updateOrder, return detail");
                    return (mapping.findForward("detail"));
                }
            }

            else if (action.equals(moveSiteStr) || action.equals("admin.button.moveOrderAndSite")) {
                ActionErrors ae = StoreOrderLogic.updateOrder(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("display"));
                }
                else {
                    ae = StoreOrderLogic.moveSiteContract(request, form);
                    if (ae.size() > 0) {
                        saveErrors(request, ae);
                        return (mapping.findForward("display"));
                    }
                    else {
                        return (mapping.findForward("detail"));
                    }
                }
            }
            else if (action.equals(cancelOrder) || action.equals("admin.button.cancelOrder")) {
                ActionErrors ae = StoreOrderLogic.cancelOrder(request, form, orderStatusId);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("display"));
                }
                return (mapping.findForward("detail"));
            }
            else if (action.equals(updateItemsStr) ||
              action.equals("admin.button.updateOrderItems")) {
                ActionErrors ae = StoreOrderLogic.updateCostQty(request, form, orderStatusId);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("display"));
            }
            else if (action.equals("update_shipping") ) {
                ActionErrors ae = StoreOrderLogic.updateShipping(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                } else {
                    // refresh the order information.
                    StoreOrderLogic.getOrderStatusDetail(request, form, orderStatusId);
                }
                return (mapping.findForward("display"));
            }
            else if (action.equals(dontSendToDistributorStr) ||
              action.equals("admin.button.dontSendToDistributor")) {
                ActionErrors ae = StoreOrderLogic.dontSendToDistributor(request, form, orderStatusId);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("display"));
            }

            else if (action.equals(cancelStr) || action.equals("admin.button.cancelSelected")) {
                ActionErrors ae = StoreOrderLogic.cancelSelectedOrderItems(request, form, orderStatusId);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("detail"));
                }
                else {
                    return (mapping.findForward("detail"));
                }
            }

            else if (action.equals(reorderStr) || action.equals("global.action.label.reorder")) {
                try {
                    ActionErrors ae = StoreOrderLogic.reorder(request, form);
                    if (ae.size() > 0) {
                        saveErrors(request, ae);
                        return (mapping.findForward("display"));
                    }
                } catch (Exception e) {
                    logm("reorder error " + e);
                }

                return (mapping.findForward("shopping_cart"));
            }

            else if (action.equals(addStr) || action.equals("admin.button.addItem")) {
                ActionErrors ae = StoreOrderLogic.addOrderItems(request, form, orderStatusId);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("display"));
                }
                else {
                    return (mapping.findForward("display"));
                }
            }

            else if (action.equals("saveNote") || action.equals(saveStr) ||
              action.equals("global.action.label.save")){

                ActionErrors ae = StoreOrderLogic.updateNote(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("list"));

            }else if (action.equals(receiveUpdateStr) || action.equals("button.update.receiving")){
                ActionErrors ae = StoreOrderLogic.updateReceivedItems(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }else{

                        StoreOrderLogic.getOrderStatusDetail(request, form, orderStatusId);
                }
                return (mapping.findForward("display"));
   }else if (action.equals(addCustomerCommentStr) || action.equals("button.addCustomerComment")){
                ActionErrors ae = StoreOrderLogic.updateCustomerNote(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("display"));
   } else if(action.equals("siteNotes")) {
          return (mapping.findForward("siteNotes"));
        }
        else if(action.equals("addSiteNoteLine")) {
          return (mapping.findForward("siteNotes"));
        }
        else if(action.equals("addSiteNote")) {
          return (mapping.findForward("siteNotes"));
        }
        else if(action.equals("Save Line")) {
          ActionErrors ae = StoreOrderLogic.saveSiteNoteLine(request, form);
          if (ae.size() > 0) {
              saveErrors(request, ae);
          }
          return (mapping.findForward("siteNotes"));
        }
        else if(action.equals("Add Note")) {
          ActionErrors ae = StoreOrderLogic.addSiteNote(request, form);
          if (ae.size() > 0) {
              saveErrors(request, ae);
          }
          return (mapping.findForward("siteNotes"));
        }
        else if(action.equals("viewOrderItemDetail")){
        	request.getSession().setAttribute("orderItemIdToView",orderItemIdToView );
        	return (mapping.findForward("orderItemDetail"));
        }
        else {
                return (mapping.findForward("list"));
            }
        }


        // Catch all exceptions here.
        catch (Exception e) {
            e.printStackTrace();
            return (mapping.findForward("error"));
        }
        finally{
           if(!"Full Control".equals(action)) {
             detailForm.setFullControlFl(false);
           }
        }
    }


}
