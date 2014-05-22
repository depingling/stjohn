
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
import com.cleanwise.view.logic.*;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.util.RefCodeNames;


/**
 *  Implementation of <strong>Action</strong> that processes the
 *  Account Workflow entries.
 *
 *@author     durval
 *@created    December 10, 2001
 */
public final class WorkflowMgrAction extends ActionBase {


    
    // ----------------------------------------------- Public Methods

    /**
     *  Process the specified HTTP request, and create the
     *  corresponding HTTP response (or forward to another web
     *  component that will create it).  Return an
     *  <code>ActionForward</code> instance describing where and how
     *  control should be forwarded, or <code>null</code> if the
     *  response has already been completed.
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
    public ActionForward performAction(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
    throws Exception {
        setFailForward("success");
        // Determine the account manager action to be performed
        String action = request.getParameter("action");
        if (action == null) {
            action = "init";
        }

        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
           return mapping.findForward("/userportal/logon");
        }   



        MessageResources mr = getResources(request);

        // Get the form buttons as specified in the properties file.
        String saveStr = getMessage(mr,request,"global.action.label.save");
        String deleteStr = getMessage(mr,request,"global.action.label.delete");
        String searchStr = getMessage(mr,request,"global.action.label.search");
        String viewallStr = getMessage(mr,request,"admin.button.viewall");
        String createStr = getMessage(mr,request,"admin.button.create");

        // Process the action
            ActionErrors ae = null;
            String forward;
            String failForward = null;
            if (action.equals("init")) {
                WorkflowMgrLogic.init(request, form);
               forward="success";
            }
            else if (action.equals("update_cw_sku_setting")) {
                WorkflowMgrLogic.setCWSkuCheck(request, form);
		// Get the new settings for the store.
                StoreMgrLogic.getDetail(request, 0);
                forward="success";
            }
            else if (action.equals("update_workflow") || action.equals(saveStr)) {
                ae = WorkflowMgrLogic.update(request, form);
                forward = "success";
            }
            else if (action.equals(deleteStr)) {
                ae = WorkflowMgrLogic.delete(request, form);
                failForward = "detail";
                forward = "main";
            }
            else if (action.equals(createStr)) {
                WorkflowMgrLogic.init(request, form);
                forward = "detail";
            }
            else if (action.equals("Add Rule") || action.equals("Save Rule") ) {
                ae = WorkflowMgrLogic.saveRule(request, form);
                    forward = "detail";
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.BREAK_POINT.equals(action))  {
                ae = WorkflowMgrLogic.breakPointRule(request, form);
                forward = "detail";
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_VELOCITY.equals(action))  {
                ae = WorkflowMgrLogic.orderVelocityRule(request, form);
                forward = "detail";
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_TOTAL.equals(action))  {
                ae = WorkflowMgrLogic.orderTotalRule(request, form);
                forward = "detail";
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_YTD.equals(action))  {
                ae = WorkflowMgrLogic.budgetYTDRule(request, form);
                forward = "detail";
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_REMAINING_PER_CC.equals(action))  {
                ae = WorkflowMgrLogic.budgetRemainingPerCCRule(request, form);
                forward = "detail";
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU.equals(action))  {
                ae = WorkflowMgrLogic.orderSkuRule(request, form);
                forward = "detail";
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU_QTY.equals(action))  {
                ae = WorkflowMgrLogic.orderSkuQtyRule(request, form);
                forward = "detail";
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.NON_ORDER_GUIDE_ITEM.equals(action))  {
                ae = WorkflowMgrLogic.nonOrderGuideItemRule(request, form);
                forward = "detail";
            }
            else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.EVERY_ORDER.equals(action))  {
                ae = WorkflowMgrLogic.everyOrderRule(request, form);
                forward = "detail";
            }
            else if (action.equals("rm_rule")) {
                ae = WorkflowMgrLogic.rmRule(request, form);
                forward = "detail";
            }
            else if (action.equals("mv_rule_up")) {
                ae = WorkflowMgrLogic.moveRuleUp(request, form);
                forward = "detail";
            }
            else if (action.equals("mv_rule_down")) {
                ae = WorkflowMgrLogic.moveRuleDown(request, form);
                forward = "detail";
            }
            else if (action.equals(searchStr)) {
                WorkflowMgrLogic.search(request, form);
                forward = "success";
            }
            else if (action.equals("detail")) {
                WorkflowMgrLogic.detail(request, form);
                forward = "detail";
            }
            else if (action.equals(viewallStr)) {
                WorkflowMgrLogic.listAll(request, form);
                forward = "success";
            }
            else if (action.equals("sort")) {
                WorkflowMgrLogic.sortWorkflows(request, form);
                forward = "success";
            }
            else if (action.equals("sites")) {
                WorkflowMgrLogic.linkSites(request, form);
                forward = "success";
            }
            else {
                WorkflowMgrLogic.init(request, form);
                forward = "success";
            }
            if(ae!=null && ae.size() > 0){
              saveErrors(request,ae);
                if(failForward == null){
                    failForward = forward;
                }
                return mapping.findForward(failForward);
            }
            return mapping.findForward(forward);
    }

}

