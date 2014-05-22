package com.cleanwise.view.actions;

import com.cleanwise.view.logic.AccountMgrLogic;
import com.cleanwise.view.utils.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;


/**
 *  Implementation of <strong>Action</strong> that processes the Account manager
 *  page.
 *
 *@author     tbesser
 *@created    August 23, 2001
 */
public final class AccountMgrAction
    extends ActionSuper {

    // ----------------------------------------------------- Public Methods

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
    public ActionForward performSub(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response)
                          throws IOException, ServletException {

        // Determine the account manager action to be performed
        String action = request.getParameter("action");

        if (action == null) {
            action = "init";
        }

        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);

        if (st.checkSession() == false) {

            return mapping.findForward("/userportal/logon");
        }

        MessageResources mr = getResources(request);

        // Get the form buttons as specified in the properties file.
        String saveStr = getMessage(mr,request,"global.action.label.save");
        String deleteStr = getMessage(mr,request,"global.action.label.delete");
        String searchStr = getMessage(mr,request,"global.action.label.search");
        String viewallStr = getMessage(mr,request,"admin.button.viewall");
        String createStr = getMessage(mr,request,"admin.button.create");
        String savePipelineDataStr = getMessage(mr,request,"admin.button.saveAllPiplineData");
        String addOrderRouteStr = getMessage(mr,request,"admin.button.addOrderRoute");
        String checkOrderRouteStr = getMessage(mr,request,"admin.button.checkOrderRoute");

        // Process the action
        try {

            if (action.equals("init")) {
                AccountMgrLogic.init(request, form);

                return (mapping.findForward("success"));
            } else if (action.equals(searchStr)) {
                //AccountMgrLogic.search(request, form);
            	AccountMgrLogic.accountSearch(request, form);

                return (mapping.findForward("success"));
            } else if (action.equals("changeOrderBillTo")) {
                AccountMgrLogic.changeOrderBillTo(request, form);

                return (mapping.findForward("success"));
            } else if (action.equals(viewallStr)) {
                //AccountMgrLogic.getAll(request, form);
            	request.setAttribute("viewAll", "yes");
            	AccountMgrLogic.accountSearch(request, form);
            	
                return (mapping.findForward("success"));
            } else if (action.equals(savePipelineDataStr) ||
		       action.equals("update_zip_entry") ) {
                ActionErrors ae = AccountMgrLogic.savePipelineData(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("success"));
            } else if (action.equals("deliverySchedInit") ) {
                ActionErrors ae = 
                    AccountMgrLogic.initDeliverySchedules(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("success"));
            } else if (action.equals("accountBillTos") ) {
                ActionErrors ae = 
                    AccountMgrLogic.getAccountBillTos(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("success"));
            } else if (action.equals("addBillTo") ) {
                ActionErrors ae = 
                    AccountMgrLogic.addBillTo(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("success"));
            } else if (action.equals("updateBillTo") ) {
                ActionErrors ae = 
                    AccountMgrLogic.updateBillTo(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("success"));
            } else if (action.equals("billtodetail") ) {
                ActionErrors ae = 
                    AccountMgrLogic.getBillToDetail(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("success"));
            }else if (action.equals(addOrderRouteStr)){
                ActionErrors ae = AccountMgrLogic.addOrderRoute(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("success"));
            }else if (action.equals(checkOrderRouteStr)){
                AccountMgrLogic.testOrderRoute(request, form);
                return (mapping.findForward("success"));
            } else if (action.equals(createStr)) {
                AccountMgrLogic.addAccount(request, form);

                return (mapping.findForward("accountdetail"));
            } else if (action.equals("updateaccount") || 
                       action.equals(saveStr)) {

                ActionErrors ae = AccountMgrLogic.updateAccount(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }

                return (mapping.findForward("accountdetail"));
            } else if (action.equals("accountdetail")) {
                AccountMgrLogic.getDetail(request, form);

                return (mapping.findForward("accountdetail"));
            } else if (action.equals(deleteStr)) {

                ActionErrors ae = AccountMgrLogic.delete(request, form);

                if (ae.size() > 0) {
                    saveErrors(request, ae);

                    return (mapping.findForward("accountdetail"));
                }

                return (mapping.findForward("main"));
            } else if (action.equals("sort")) {
                AccountMgrLogic.sort(request, form);

                return (mapping.findForward("success"));
                
            } else if (action.equals("update_site_del_sched")) {
                ActionErrors ae = AccountMgrLogic.updateDeliverySchedule
                    (request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("update"));
            } else if (action.equals("selectInventoryItems")) {
                AccountMgrLogic.getInventoryItemsAvailable(request, form);
                return (mapping.findForward("success"));
            } else if (action.equals("Add to inventory.")) {
                AccountMgrLogic.addInventoryItems(request, form);
                return (mapping.findForward("success"));
            } else if (action.equals("Remove from inventory")) {
                AccountMgrLogic.removeInventoryItems(request, form);
                return (mapping.findForward("success"));
            } else if (action.equals("Enable auto order")) {
                AccountMgrLogic.enableAutoOrderForInventoryItems(request, form);
                return (mapping.findForward("success"));
            } else if (action.equals("Disable auto order")) {
                AccountMgrLogic.disableAutoOrderForInventoryItems(request, form);
                return (mapping.findForward("success"));
            } else {
                AccountMgrLogic.init(request, form);

                return (mapping.findForward("success"));
            }
        } catch (Exception e) {
            //save the error
            e.printStackTrace();
            ActionErrors ae = new ActionErrors();
            ae.add("",new ActionError(e.getMessage()));
            saveErrors(request, ae);
            return (mapping.findForward("failure"));
        }
    }
}
