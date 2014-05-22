
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
import com.cleanwise.view.logic.OrderOpLogic;
import com.cleanwise.view.forms.OrderOpDetailForm;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.util.RefCodeNames;

/**
 * Implementation of <strong>Action</strong> that saves a new
 * freightTable detail or updates an existing freightTable detail.
 */
public final class OrderOpDetailAction extends ActionSuper {


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
    OrderOpDetailForm detailForm = (OrderOpDetailForm) form;
    detailForm.setLastAction(action);

	if (action == null) action = "view";
    logm(action);
	// Is there a currently logged on user?
	SessionTool st = new SessionTool(request);
	if ( st.checkSession() == false ) {
	    return mapping.findForward("/userportal/logon");
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
	String cancelAndReorderStr = getMessage(mr,request,"admin.button.cancelAndReorder");
  String addCustomerCommentStr = getMessage(mr,request,"button.addCustomerComment");
  String bindToWorkOrderStr = getMessage(mr,request,"button.bindToWorkOrder");
  String refreshStr = getMessage(mr,request,"button.refresh");
  String receiveUpdateStr = getMessage(mr,request,"button.update.receiving");
  String dontSendToDistributorStr = getMessage(mr,request,"admin.button.dontSendToDistributor");
   String xpedxProcess = "xpedxProcess";

	logm(" action is: " + action);

	try {
		String orderStatusId = request.getParameter("id");
		if (orderStatusId == null) orderStatusId = "";

		if("view".equals(action) && ("".equals(orderStatusId) || "0".equals(orderStatusId))) {
			String orderNum = request.getParameter("orderNum");
			if ( orderNum!= null){
				orderStatusId = OrderOpLogic.getOrderIdByWebOrderNum(request, orderNum, detailForm.getSite().getSiteId());
			}else{
			    HttpSession session = request.getSession();
		
			    orderStatusId = (String) session.getAttribute("OrderStatus.id");
			    if(null == orderStatusId || "".equals(orderStatusId) || "0".equals(orderStatusId)) {
		                detailForm.setFullControlFl(false);
				return (mapping.findForward("list"));
			    }
			}
		}
	    // view an existing order.
	    if (action.equals("view") || action.equals(refreshStr) ||
          action.equals("button.refresh")) {

	    ActionErrors ae = OrderOpLogic.getOrderStatusDetail(request, form, orderStatusId);
		if (ae.size() > 0) {
            saveErrors(request, ae);
            return (mapping.findForward("list"));
        }
		return (mapping.findForward("display"));
	    }
            else if (action.equals("Full Control")) {
                detailForm.setNewContractIdS("");
                detailForm.setFullControlFl(true);
		return (mapping.findForward("display"));
	    }
	    else if (action.equals("sortitems")) {
		OrderOpLogic.sortItems(request, form);
		return (mapping.findForward("display"));
	    }

	    else if (action.equals(backStr) || action.equals("admin.button.back")) {
		return (mapping.findForward("list"));
	    }

	    else if (action.equals(checkStr) || action.equals("admin.button.checkContract")) {
		ActionErrors ae = OrderOpLogic.checkContractForPendingApprovalOrder2(request, form, orderStatusId);
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
		    OrderOpLogic.approvePendingApprovalOrder(request, form, orderStatusId);
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
		    OrderOpLogic.changePendingApprovalOrderStatus(request, form, orderStatusId, RefCodeNames.ORDER_STATUS_CD.REJECTED);
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
		    OrderOpLogic.changePendingApprovalOrderStatus
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
		logm("calling OrderOpLogic.updatePendingApprovalOrder2");

		ActionErrors ae = OrderOpLogic.updatePendingApprovalOrder2(request, form, orderStatusId);
		if (ae.size() > 0) {
		    saveErrors(request, ae);
		    logm("calling OrderOpLogic.updatePendingApprovalOrder2, return display");

		    return (mapping.findForward("display"));
		}
		else {
		    logm("calling OrderOpLogic.updatePendingApprovalOrder2, return detail");
		    return (mapping.findForward("detail"));
		}
	    }

	    else if (action.equals(moveSiteStr) || action.equals("admin.button.moveOrderAndSite")) {
		ActionErrors ae = OrderOpLogic.updatePendingApprovalOrder2(request, form, orderStatusId);
		if (ae.size() > 0) {
		    saveErrors(request, ae);
		    return (mapping.findForward("display"));
		}
		else {
		    ae = OrderOpLogic.moveSiteContract(request, form);
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
		ActionErrors ae = OrderOpLogic.cancelOrder(request, form, orderStatusId);
		if (ae.size() > 0) {
		    saveErrors(request, ae);
		}
	        return (mapping.findForward("display"));
	    }
	    else if (action.equals(updateItemsStr) ||
              action.equals("admin.button.updateOrderItems")) {
		ActionErrors ae = OrderOpLogic.updateCostQty(request, form, orderStatusId);
		if (ae.size() > 0) {
                    saveErrors(request, ae);
		}
	        return (mapping.findForward("display"));
	    }
	    else if (action.equals("update_shipping") ) {
		ActionErrors ae = OrderOpLogic.updateShipping(request, form);
		if (ae.size() > 0) {
                    saveErrors(request, ae);
		} else {
		    // refresh the order information.
		    OrderOpLogic.getOrderStatusDetail(request, form, orderStatusId);
		}
	        return (mapping.findForward("display"));
	    }
            else if (action.equals(dontSendToDistributorStr) ||
              action.equals("admin.button.dontSendToDistributor")) {
		ActionErrors ae = OrderOpLogic.dontSendToDistributor(request, form, orderStatusId);
		if (ae.size() > 0) {
                    saveErrors(request, ae);
		}
	        return (mapping.findForward("display"));
	    }

	    else if (action.equals(cancelStr) || action.equals("admin.button.cancelSelected")) {
		ActionErrors ae = OrderOpLogic.cancelSelectedOrderItems(request, form, orderStatusId);
		if (ae.size() > 0) {
		    saveErrors(request, ae);
		    return (mapping.findForward("display"));
		}
		else {
		    return (mapping.findForward("detail"));
		}
	    }

	    else if (action.equals(cancelAndReorderStr) || action.equals("admin.button.cancelAndReorder")) {
		try {

		    ActionErrors ae = OrderOpLogic.opReorder(request, form);
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
		ActionErrors ae = OrderOpLogic.addOrderItems(request, form, orderStatusId);
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

		ActionErrors ae = OrderOpLogic.updateNote(request, form);
		if (ae.size() > 0) {
		    saveErrors(request, ae);
		}
		return (mapping.findForward("list"));

	    }else if (action.equals(receiveUpdateStr) || action.equals("button.update.receiving")){
                ActionErrors ae = OrderOpLogic.updateReceivedItems(request, form);
		if (ae.size() > 0) {
		    saveErrors(request, ae);
		}else{

			OrderOpLogic.getOrderStatusDetail(request, form, orderStatusId);
		}
		return (mapping.findForward("display"));
   }else if (action.equals(addCustomerCommentStr) || action.equals("button.addCustomerComment")){
                ActionErrors ae = OrderOpLogic.updateCustomerNote(request, form);
		if (ae.size() > 0) {
		    saveErrors(request, ae);
		}
		return (mapping.findForward("display"));
        } else if (action.equals(bindToWorkOrderStr) || action.equals("button.bindToWorkOrder")) {
            ActionErrors ae = OrderOpLogic.bindToWorkOrder(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
        return (mapping.findForward("display"));
        }
        else if(action.equals("siteNotes")) {
          return (mapping.findForward("siteNotes"));
        }
        else if(action.equals("addSiteNoteLine")) {
          return (mapping.findForward("siteNotes"));
        }
        else if(action.equals("addSiteNote")) {
          return (mapping.findForward("siteNotes"));
        }
        else if(action.equals("Save Line")) {
          ActionErrors ae = OrderOpLogic.saveSiteNoteLine(request, form);
          if (ae.size() > 0) {
              saveErrors(request, ae);
          }
          return (mapping.findForward("siteNotes"));
        }
        else if(action.equals("Add Note")) {
          ActionErrors ae = OrderOpLogic.addSiteNote(request, form);
          if (ae.size() > 0) {
              saveErrors(request, ae);
          }
          return (mapping.findForward("siteNotes"));
        }
        else if(action.equals("ResubmitToErp")) {
            ActionErrors ae = OrderOpLogic.resubmitToErp(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return (mapping.findForward("display"));
        } else if (action.equals(xpedxProcess)) {
            logm(" xpedxProcess => begin.command " + request.getParameter("command"));
            String command = request.getParameter("command");
            if ("approve".equals(command)) {
                ActionErrors ae = OrderOpLogic.approveOrder(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("display"));
                }
                return (mapping.findForward("pendingApprovalOrders"));
            } else if ("reject".equals(command)) {
                ActionErrors ae = OrderOpLogic.rejectOrder(request, form, mr);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("display"));
                }
                return (mapping.findForward("pendingApprovalOrders"));
            } else if ("modify".equals(command)) {
                ActionErrors ae = OrderOpLogic.modifyOrder(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("display"));
                }
                return (mapping.findForward("shopping_cart"));
            } else if ("reorder".equals(command)) {
                ActionErrors ae = OrderOpLogic.reorder(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("display"));
                }
                return (mapping.findForward("shopping_cart"));
            } else if ("goToOrderLocation".equals(command)) {
                ActionErrors ae = OrderOpLogic.goToOrderLocation(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("display"));
            } else if ("addCustomerComment".equals(command)){
                ActionErrors ae = OrderOpLogic.updateCustomerNote(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("display"));
            } else if ("addTrackingNumber".equals(command)){
                ActionErrors ae = OrderOpLogic.addShippedTrackingNum(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }else{
                	// refresh the order information.
        		    OrderOpLogic.getOrderStatusDetail(request, form, orderStatusId);
                }
                return (mapping.findForward("display"));
            } else {
                return (mapping.findForward("display"));
            }
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
