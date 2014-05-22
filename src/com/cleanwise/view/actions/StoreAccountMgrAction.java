/*
 * StoreAccountMgrAction.java
 *
 */

package com.cleanwise.view.actions;


import com.cleanwise.view.forms.*;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.SessionAttributes;
import com.cleanwise.view.logic.*;
import com.cleanwise.service.api.util.RefCodeNames;
import org.apache.struts.action.*;
import org.apache.struts.util.MessageResources;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import org.apache.log4j.Category;
/**
 *
 * @author Alexander Chickin
 * Mod by Evgeny Vlasov
 */
public class StoreAccountMgrAction  extends ActionSuper {
	static final Category log = Category.getInstance(StoreAccountMgrAction.class);
    private static final String SUCCESS = "success" ;
    private static final String FAILURE = "failure";
    private static final String MAIN    = "main" ;
    private static final String DETAIL  = "detail";

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

        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
            return mapping.findForward(st.getLogonMapping());
        }

        log.info("StoreAccountMgrAction: action="
                + action + " params="
                + st.paramDebugString());
        
        MessageResources mr = getResources(request);
        String copyBudgetToNextYear = getMessage(mr,request,"admin.button.copyBudgetToNextYear");

        try {
        	if ((form instanceof StoreAccountShoppingControlForm) && action.equals("getSiteControls")){
        		StoreAccountMgrLogic.getSiteControls(request, response, form);
        		return null;
        	}else if ((form instanceof StoreAccountShoppingControlForm) && action.equals("updateSiteControls")){
        		StoreAccountMgrLogic.updateSiteControls(request, response, form);
        		return null;
        	}else if ((form instanceof StoreAccountShoppingControlForm) && action.equals("deleteSiteControls")){
        		StoreAccountMgrLogic.deleteSiteControls(request, response, form);
        		return null;
        	}else if ((form instanceof StoreAccountShoppingControlForm) && action.equals("deleteAllSiteControls")){
        		StoreAccountMgrLogic.deleteAllSiteControls(request, response, form);
        		return null;
        	}else if ((form instanceof StoreAccountShoppingControlForm) && action.equals("clearItemMessages")){
        		StoreAccountMgrLogic.clearItemMessages(request, response, form);
        		return null;
        	}else if (form instanceof StoreAccountMgrDetailForm && copyBudgetToNextYear.trim().equalsIgnoreCase(action.trim())) {
        		ActionErrors  actionErrors = StoreAccountMgrLogic.copyBudgetToNextYear(form,request);
        		String forwardString = SUCCESS;
        		if(actionErrors.size()>0) {
        			saveErrors(request, actionErrors);
        			forwardString = FAILURE;
        		}
        		return mapping.findForward(forwardString);
        	}
        	else{
        		ActionForward  actionForward = callHandlerForm(action,form,request,mapping);
        		return actionForward;
        	}
        } catch(Exception e) {
        	e.printStackTrace();
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            return mapping.findForward("error");
        }


    }


    private ActionForward  callHandlerForm(String action,ActionForm form,HttpServletRequest request,ActionMapping mapping)  throws Exception
    {
    	log.debug("<============ StoreAccountMgrAction informs :");
    	log.debug("Form type :"+form);
    	log.debug("Action :"+action);
    	log.debug("Run action method.....");
        String forward_page="main";


        if (form instanceof StoreAccountMgrDetailForm) forward_page = accountDetailAction(action,form,request);
        else if (form instanceof StoreAccountMgrForm)  forward_page = mainAction(action,form,request);
        else if (form instanceof StoreAccountBillToForm)   forward_page =   biltoAction(action,form,request);
        else if (form instanceof StoreUIConfigForm)       forward_page =  uiConfigAction(action,form,request);
        else if (form instanceof StoreSiteFieldsConfigForm)   forward_page =      siteFieldConfigAction(action,form,request);
        else if (form instanceof StoreWorkflowMgrSearchForm)  forward_page=workflowMgrSearchAction(action,form,request);
        else if (form instanceof StoreWorkflowDetailForm)     forward_page=workflowDetailAction(action,form,request);
        else if (form instanceof StoreWorkflowSitesForm) forward_page=workflowSitesAction(action,form,request);
        else if (form instanceof StoreContractMgrSearchForm)  forward_page=contractMgrSearchAction(action,form,request);
        else if (form instanceof StoreFhMgrSearchForm)   forward_page=fhMgrSearchAction(action,form,request);
        else if (form instanceof StoreDistMgrSearchForm) forward_page=distMgrSearchAction(action,form,request);
        else if (form instanceof StoreAccountShoppingControlForm) forward_page= shoppingControlAction(action,form,request);
        else if (form instanceof StoreAccountConfigurationForm)  forward_page=configurationAction(action,form,request);
        log.debug("Ok!");
        log.debug("FORWARD_PAGE "+mapping.findForward(forward_page).getPath()+"==========>");

        return mapping.findForward(forward_page);
    }

    private String configurationAction(String action, ActionForm form, HttpServletRequest request) throws Exception {

        ActionErrors ae = new ActionErrors();
        MessageResources mr = getResources(request);
        String searchStr = getMessage(mr, request, "global.action.label.search");


        if (searchStr.equals(action)) {
            ae = StoreAccountConfigurationLogic.search((StoreAccountConfigurationForm) form, request);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
        } else {
            ae = StoreAccountConfigurationLogic.init(request);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
        }
        return SUCCESS;
    }



    private String shoppingControlAction(String action, ActionForm form, HttpServletRequest request) throws Exception {
        ActionErrors ae = null;
        if ( "getShoppingRestrictions".equals(action) ) {
            ae = StoreAccountMgrLogic.initShoppingControls(request, form);
        } else if ( "search_shopping_controls".equals(action) ) {
            ae = StoreAccountMgrLogic.lookupShoppingControls(request, form);
        } else if ( "update_shopping_controls".equals(action) ) {
            ae = StoreAccountMgrLogic.updateShoppingControls(request, form);
        } else {
            StoreAccountMgrLogic.init(request, form);
        }
        if (ae != null && ae.size() > 0) {
            saveErrors(request, ae);
        }
        return SUCCESS;
    }

    private String distMgrSearchAction(String action, ActionForm form, HttpServletRequest request) throws Exception {

        MessageResources mr = getResources(request);
        // Get the form buttons as specified in the properties file.
        String searchStr = getMessage(mr,request,"global.action.label.search");
        String viewallStr = getMessage(mr,request,"admin.button.viewall");
        if (action.equals(searchStr)) {
            StoreDistMgrLogic.search(request, form);
            StoreOrderProcLogic.setStatusLocateDistributor(request, form, StoreAccountMgrDetailForm.ORDER_PROCESSING_PAGE,LocateForm.SHOW_DISPLAY);
            return (("main"));
        }
        else if (action.equals(viewallStr)) {
            DistMgrLogic.getAll(request, form);
            StoreOrderProcLogic.setStatusLocateDistributor(request, form,StoreAccountMgrDetailForm.ORDER_PROCESSING_PAGE,LocateForm.SHOW_DISPLAY);
            return (("main"));
        } else if (action.equals("sort")) {
            DistMgrLogic.sort(request, form);
            StoreOrderProcLogic.setStatusLocateDistributor(request, form,StoreAccountMgrDetailForm.ORDER_PROCESSING_PAGE, LocateForm.SHOW_DISPLAY);
        }
        else if(action.equals("setSelect"))
        {
            StoreOrderProcLogic.setSelectDistributor(request, form,StoreAccountMgrDetailForm.ORDER_PROCESSING_PAGE);
        }

        return "main";
    }

    private String fhMgrSearchAction(String action, ActionForm form, HttpServletRequest request) throws Exception {
        String searchStr = getResources(request).getMessage("global.action.label.search");
        String viewallStr = getResources(request).getMessage("admin.button.viewall");
        String sortStr = "sort";
        if (action.equals(searchStr)){
            StoreDistMgrLogic.getFreightHandlers(request, (StoreFhMgrSearchForm)form,true);
            StoreOrderProcLogic.setStatusLocateFreightHandlers(request, form, StoreAccountMgrDetailForm.ORDER_PROCESSING_PAGE,LocateForm.SHOW_DISPLAY);

        }else if (action.equals(viewallStr)){
            StoreDistMgrLogic.getAllFreightHandlers(request, form);
            StoreOrderProcLogic.setStatusLocateFreightHandlers(request, form, StoreAccountMgrDetailForm.ORDER_PROCESSING_PAGE,LocateForm.SHOW_DISPLAY);

        }else if (action.equals(sortStr)){
            StoreDistMgrLogic.sort(request, form);
            StoreOrderProcLogic.setStatusLocateFreightHandlers(request, form, StoreAccountMgrDetailForm.ORDER_PROCESSING_PAGE,LocateForm.SHOW_DISPLAY);

        }
        else if(action.equals("setSelect"))
        {
            StoreOrderProcLogic.setSelectFreightHandlers(request, form,StoreAccountMgrDetailForm.ORDER_PROCESSING_PAGE);
        }
        return "main";
    }

    private String contractMgrSearchAction(String action, ActionForm form, HttpServletRequest request) throws Exception {
        MessageResources mr = getResources(request);
        // Get the form buttons as specified in the properties file.
        String searchStr = getMessage(mr,request,"global.action.label.search");
        String viewallStr = getMessage(mr,request,"admin.button.viewall");

        if (action.equals(searchStr) || action.equals("search")) {

            StoreContractMgrLogic.search(request, form);
            StoreOrderProcLogic.setStatusLocateContract(request, form,StoreAccountMgrDetailForm.ORDER_PROCESSING_PAGE,LocateForm.SHOW_DISPLAY);
            log.debug("======================"+"Search Contract "+"=============================");
            return MAIN;
        }
        else if (action.equals(viewallStr) || action.equals("viewall")) {
            StoreContractMgrLogic.searchAll(request, form);
            StoreOrderProcLogic.setStatusLocateContract(request, form,StoreAccountMgrDetailForm.ORDER_PROCESSING_PAGE,LocateForm.SHOW_DISPLAY);
            return MAIN;
        }
        else if (action.equals("sort")) {
            StoreContractMgrLogic.sort(request, form);
            StoreOrderProcLogic.setStatusLocateContract(request, form,StoreAccountMgrDetailForm.ORDER_PROCESSING_PAGE,LocateForm.SHOW_DISPLAY);
            return MAIN;
        }
        else if (action.equals("setSelect")) {
            StoreOrderProcLogic.setSelectContract(request, form,StoreAccountMgrDetailForm.ORDER_PROCESSING_PAGE);
            return MAIN;
        }
        return MAIN;
    }

    private String workflowSitesAction(String action, ActionForm form, HttpServletRequest request) throws Exception {
        MessageResources mr = getResources(request);
        // Get the form buttons as specified in the properties file.
        String saveStr = getMessage(mr,request,"global.action.label.save");
        String searchStr = getMessage(mr,request,"global.action.label.search");
        String viewallStr = getMessage(mr,request,"admin.button.viewall");
        String listcurrStr = getMessage(mr,request,"admin.button.listCurrentSites");
        String assignallStr = "assign_to_all_sites";

        // Process the action


        if (action.equals(searchStr) ||
                action.equals(viewallStr)) {
            StoreWorkflowMgrLogic.searchForSites(request, form);
            return SUCCESS;
        }
        else if (action.equals("sort")) {
            StoreWorkflowMgrLogic.sortSites(request, form);
            return SUCCESS;
        }
        else if (action.equals(saveStr)) {
            ActionErrors ae = StoreWorkflowMgrLogic.linkSites
                    (request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return SUCCESS;
        }
        else if (action.equals(assignallStr)) {
            ActionErrors ae = StoreWorkflowMgrLogic.assignAllSites
                    (request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return SUCCESS;
        }
        else if (action.equals(listcurrStr)) {
            ActionErrors ae = StoreWorkflowMgrLogic.fetchCurrentSites
                    (request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return SUCCESS;
        }
        else {
            StoreWorkflowMgrLogic.initSites(request, form);
            return SUCCESS;
        }


    }

    private String workflowDetailAction(String action, ActionForm form, HttpServletRequest request) throws Exception {



        StoreWorkflowDetailForm sForm = (StoreWorkflowDetailForm) form;
        MessageResources mr = getResources(request);
        // Get the form buttons as specified in the properties file.
        String saveStr = getMessage(mr,request,"global.action.label.save");
        String deleteStr = getMessage(mr,request,"global.action.label.delete");

        // WARNING...
        // not all actions of StoreWorkflowDetailForm are processed
        // in this class.
        StoreWorkflowMgrLogic.setLastAction(sForm,action);
        if (action.equals("init")) {
            StoreWorkflowMgrLogic.init(request, form);
            return SUCCESS;        }
        else if("Locate Distributor".equals(action))
        {
            StoreWorkflowMgrLogic.clearSelectBox(request,sForm);
            //sForm.setLastLocateAction(action);
            return DETAIL;
        }
        else if("Locate User".equals(action))
        {
            StoreWorkflowMgrLogic.clearSelectBox(request,sForm);
            return DETAIL;
        }
        else if("Return Selected".equals(action)){
            String submitFormIdent = request.getParameter("jspSubmitIdent");
            if(submitFormIdent!=null &&  submitFormIdent.indexOf("#"+ SessionAttributes.SEARCH_FORM.LOCATE_STORE_DIST_FORM)==0){
                //StoreWorkflowMgrLogic.setSelectedDist(request,sForm);
                return DETAIL;
            }
            else if(submitFormIdent!=null &&  submitFormIdent.indexOf("#"+ SessionAttributes.SEARCH_FORM.LOCATE_STORE_USER_FORM)==0)
            {
                StoreWorkflowMgrLogic.setSelectedEmailUser(request,sForm);
                return DETAIL;
            }
        }
        else if("Clear User Filter".equals(action)) {StoreWorkflowMgrLogic.resetEmailUser(request,sForm); return DETAIL;}
        else if ("Clear Distributor Filter".equals(action)) return DETAIL;
        else if("Cancel".equals(action)) return DETAIL;
        else if (action.equals("update_cw_sku_setting")) {
            ActionErrors ae= StoreWorkflowMgrLogic.setCWSkuCheck(request, form);
            // Get the new settings for the store.
            StoreMgrLogic.getDetail(request, 0);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }
        else if (action.equals("update_workflow") || action.equals(saveStr)) {
            ActionErrors ae=  StoreWorkflowMgrLogic.update(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return SUCCESS;
        }
        else if (action.equals(deleteStr)) {
            ActionErrors ae=  StoreWorkflowMgrLogic.delete(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return MAIN;
        }

        else if (action.equals("Add Rule") || action.equals("Save Rule") ) {
            ActionErrors ae= StoreWorkflowMgrLogic.saveRule(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }
        else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.BREAK_POINT.equals(action))  {
            ActionErrors ae= StoreWorkflowMgrLogic.breakPointRule(request, form);
            StoreWorkflowMgrLogic.setLastRuleAction(sForm,action);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }
        else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_VELOCITY.equals(action))  {
            ActionErrors ae=  StoreWorkflowMgrLogic.orderVelocityRule(request, form);
            StoreWorkflowMgrLogic.setLastRuleAction(sForm,action);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }
        else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_TOTAL.equals(action))  {
            ActionErrors ae=   StoreWorkflowMgrLogic.orderTotalRule(request, form);
            StoreWorkflowMgrLogic.setLastRuleAction(sForm,action);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.USER_LIMIT.equals(action)) {
            ActionErrors ae = StoreWorkflowMgrLogic.userLimitRule(request, form);
            StoreWorkflowMgrLogic.setLastRuleAction(sForm, action);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_BUDGET_PERIOD_CHANGED.equals(action)) {
            ActionErrors ae = StoreWorkflowMgrLogic.orderBudgetPeriodChanged(request, form);
            StoreWorkflowMgrLogic.setLastRuleAction(sForm, action);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_BUDGET.equals(action)) {
            ActionErrors ae = StoreWorkflowMgrLogic.workOrderBudgetRule(request, form);
            StoreWorkflowMgrLogic.setLastRuleAction(sForm, action);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_TOTAL.equals(action)) {
            ActionErrors ae = StoreWorkflowMgrLogic.workOrderTotalRule(request, form);
            StoreWorkflowMgrLogic.setLastRuleAction(sForm, action);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }
        else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_ANY.equals(action)) {
            ActionErrors ae = StoreWorkflowMgrLogic.workOrderAnyRule(request, form);
            StoreWorkflowMgrLogic.setLastRuleAction(sForm, action);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.WORK_ORDER_BUDGET_SPENDING_FOR_COST_CENTER.equals(action)) {
            ActionErrors ae = StoreWorkflowMgrLogic.workOrderBudgetSpendingForCostCenterRule(request, form);
            StoreWorkflowMgrLogic.setLastRuleAction(sForm, action);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        } else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_YTD.equals(action))  {
            ActionErrors ae=    StoreWorkflowMgrLogic.budgetYTDRule(request, form);
            StoreWorkflowMgrLogic.setLastRuleAction(sForm,action);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }
        else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.BUDGET_REMAINING_PER_CC.equals(action))  {
            ActionErrors ae=    StoreWorkflowMgrLogic.budgetRemainingPerCCRule(request, form);
            StoreWorkflowMgrLogic.setLastRuleAction(sForm,action);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }
        else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU.equals(action))  {
            ActionErrors ae=    StoreWorkflowMgrLogic.orderSkuRule(request, form);
            StoreWorkflowMgrLogic.setLastRuleAction(sForm,action);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }
        else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU_QTY.equals(action))  {
            ActionErrors ae=    StoreWorkflowMgrLogic.orderSkuQtyRule(request, form);
            StoreWorkflowMgrLogic.setLastRuleAction(sForm,action);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }
        else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.NON_ORDER_GUIDE_ITEM.equals(action))  {
            ActionErrors ae=    StoreWorkflowMgrLogic.nonOrderGuideItemRule(request, form);
            StoreWorkflowMgrLogic.setLastRuleAction(sForm,action);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }
        else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.RUSH_ORDER.equals(action)) {
            ActionErrors ae = StoreWorkflowMgrLogic.rushOrderRule(request, form);
            StoreWorkflowMgrLogic.setLastRuleAction(sForm, action);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;

        }
        else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ITEM_CATEGORY.equals(action)) {
            ActionErrors ae = StoreWorkflowMgrLogic.itemCategoryRule(request, form);
            StoreWorkflowMgrLogic.setLastRuleAction(sForm, action);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;

        }
        else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ITEM_PRICE.equals(action)) {
            ActionErrors ae = StoreWorkflowMgrLogic.itemPriceRule(request, form);
            StoreWorkflowMgrLogic.setLastRuleAction(sForm, action);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;

        }
        else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.FREIGHT_HANDLER.equals(action)) {
            ActionErrors ae = StoreWorkflowMgrLogic.freightHandlertRule(request, form);
            StoreWorkflowMgrLogic.setLastRuleAction(sForm, action);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }
        else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.EVERY_ORDER.equals(action))  {
            ActionErrors ae=    StoreWorkflowMgrLogic.everyOrderRule(request, form);
            StoreWorkflowMgrLogic.setLastRuleAction(sForm,action);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }
        else if (action.equals("rm_rule")) {
            ActionErrors ae=    StoreWorkflowMgrLogic.rmRule(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }
        else if (action.equals("mv_rule_up")) {
            ActionErrors ae=    StoreWorkflowMgrLogic.moveRuleUp(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }
        else if (action.equals("mv_rule_down")) {
            ActionErrors ae=    StoreWorkflowMgrLogic.moveRuleDown(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }

        else if (action.equals(DETAIL)) {
            ActionErrors ae=    StoreWorkflowMgrLogic.detail(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }

        else if (action.equals("sort")) {
            StoreWorkflowMgrLogic.sortWorkflows(request, form);
            return SUCCESS;
        }
        else if (action.equals("sites")) {

            ActionErrors ae=    StoreWorkflowMgrLogic.linkSites(request, form);
            return SUCCESS;
        } //STJ-5014: 
        else if (RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_EXCLUDED_FROM_BUDGET.equals(action)) {
            ActionErrors ae = StoreWorkflowMgrLogic.workOrderExcludedFromBudget(request, form);
            StoreWorkflowMgrLogic.setLastRuleAction(sForm, action);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }
        else {
            StoreWorkflowMgrLogic.init(request, form);
            return SUCCESS;
        }
        return SUCCESS;
    }

    private String workflowMgrSearchAction(String action, ActionForm form, HttpServletRequest request) throws Exception {

        MessageResources mr = getResources(request);
        // Get the form buttons as specified in the properties file.
        String searchStr = getMessage(mr,request,"global.action.label.search");
        String createStr = getMessage(mr,request,"admin.button.create");
        if (action.equals(createStr)) {
            StoreWorkflowMgrLogic.init(request, form);
            return DETAIL;
        }
        else if (action.equals(DETAIL)) {
            StoreWorkflowMgrLogic.detail(request, form);
            return  DETAIL;
        }
        else if (action.equals(searchStr)) {
            ActionErrors ae = StoreWorkflowMgrLogic.search(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return  SUCCESS;
        }
        else if (action.equals("initAccountAndGetWorkflow"))
        {
          ActionErrors ae = StoreWorkflowMgrLogic.initAccountAndGetWorkflow(request, form);
             if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            return DETAIL;
        }
        else {
            StoreWorkflowMgrLogic.init(request, form);
            return   SUCCESS;
        }

    }

    private String siteFieldConfigAction(String action, ActionForm form, HttpServletRequest request) throws Exception {

        MessageResources mr = getResources(request);
        // Get the form buttons as specified in the properties file.
        String saveStr = getMessage(mr,request,"global.action.label.save");
        // Process the action
        if (action.equals(saveStr))   StoreAccountConfigLogic.saveSiteFields(request, form);
        else   {
            StoreAccountConfigLogic.fetchSiteFields(request, form);
        }
        return SUCCESS;
    }

    private String uiConfigAction(String action, ActionForm form, HttpServletRequest request) throws Exception {

        MessageResources mr = getResources(request);
        // Get the form buttons as specified in the properties file.
        String saveStr = getMessage(mr,request,"global.action.label.save");
        String cancelStr = getMessage(mr,request,"global.action.label.cancel");
        String useStore = getMessage(mr,request,"admin.button.useStoreSettings");
        String previewStr = getMessage(mr,request,"admin.button.preview");
        String setUser = getMessage(mr,request,"user.setUser");

        // Process the action

        if (action.equals("localeChange")) {
            StoreUIConfigLogic.fetchUiSettings(request, form);
        }
        else if (action.equals(useStore)) {
            StoreUIConfigLogic.useStoreUiSettings(request, form);
        }
        else if (action.equals(cancelStr)) {
            StoreUIConfigLogic.fetchUiSettings(request, form);
        }
        else if (action.equals(previewStr)) {
            StoreUIConfigLogic.previewUiSettings(request, form);
            return "preview";
        }
        else if (action.equals(saveStr)) {
            StoreUIConfigLogic.saveUiSettings(request, form);
        }
        else if (action.equals(setUser)) {
        	ActionMessages errors = StoreUIConfigLogic.setUser(request, form);
			if (errors.size() > 0) {
				saveErrors(request, errors);
			}
		}
        else {
            StoreUIConfigLogic.init(request, form);
        }



        return SUCCESS;
    }

    private String biltoAction(String action,ActionForm form,HttpServletRequest request) throws Exception {

        if ("accountBillTos".equals(action)){
            ActionErrors ae=StoreAccountMgrLogic.getAccountBillTos(request,form);
            if(ae.size()>0) {
                saveErrors(request,ae);
                return FAILURE;
            }
        } else if (action.equals("addBillTo") ) {
            ActionErrors ae = StoreAccountMgrLogic.addBillTo(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
        } else if (action.equals("updateBillTo") ) {
            ActionErrors ae = StoreAccountMgrLogic.updateBillTo(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
        } else if (action.equals("detailBillTo") ) {
            ActionErrors ae =StoreAccountMgrLogic.getBillToDetail(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
        }
        else
        {
            ActionErrors ae=StoreAccountMgrLogic.getAccountBillTos(request,form);
            if(ae.size()>0) {
                saveErrors(request,ae);
                return FAILURE;
            }
        }

        return SUCCESS;
    }

    private String mainAction(String action, ActionForm form, HttpServletRequest request) throws Exception {

        MessageResources mr = getResources(request);
        String searchStr = getMessage(mr,request,"global.action.label.search");
        String createStr = getMessage(mr,request,"admin.button.create");

        ActionErrors ae;

        if (searchStr.equals(action)){
            ae = StoreAccountMgrLogic.accountSearch(request, form);
            if(ae.size()>0){
                saveErrors(request,ae);
            }
            return SUCCESS;
        } else if ( "accountdetail".equals(action)){
            ae = StoreAccountMgrLogic.getDetail(request, form);
            if(ae.size()>0) {
                saveErrors(request,ae);
            }
            return SUCCESS;
        }
        else if (action.equals(createStr)) {
            StoreAccountMgrLogic.addAccount(request, form);
            return DETAIL;
        }
        else
        {
            StoreAccountMgrLogic.init(request, form);
            return SUCCESS;
        }


    }

    private String accountDetailAction(String action, ActionForm form, HttpServletRequest request) throws Exception {

        MessageResources mr = getResources(request);

        // Get the form buttons as specified in the properties file.
        String deleteStr = getMessage(mr,request,"global.action.label.delete");
        String saveStr = getMessage(mr,request,"global.action.label.save");
        String savePipelineDataStr = getMessage(mr,request,"admin.button.saveAllPiplineData");
        String addOrderRouteStr = getMessage(mr,request,"admin.button.addOrderRoute");
        String checkOrderRouteStr = getMessage(mr,request,"admin.button.checkOrderRoute");
        String  saveFiscalCalStr = getMessage(mr,request,"admin.button.saveFiscalCal");
        String  createFiscalCalStr = getMessage(mr,request,"admin.button.createFiscalCal");
         String notesStr = getMessage(mr, request, "admin.button.notes");
         HttpSession session = request.getSession();
         session.setAttribute("ACCOUNT_DETAIL_FORM", form);

        if (action.equals("accountdetail")) {

            ActionErrors ae = StoreAccountMgrLogic.getDetail(request, form);
            if(ae.size()>0) {
                saveErrors(request,ae);
            }

        }
        if(action.equals("Clone")) {
           ActionErrors ae= StoreAccountMgrLogic.cloneAccountData(request,form);
            if (ae.size() > 0) {
               saveErrors(request, ae);
               return FAILURE;
            }
            return SUCCESS;
        }
        else if("Clear Distributor Filter".equals(action)) {
            StoreAccountMgrLogic.resetSelectedDistId(request,form);
            return SUCCESS;
        }
        else if("Clear Freight Handler Filter".equals(action)) {
            StoreAccountMgrLogic.resetSelectedFhId(request,form);
            return SUCCESS;
        }
        else if("Clear Catalog Filter".equals(action)) {
            StoreAccountMgrLogic.resetSelectedCatId(request,form);
            return SUCCESS;
        }
        else if("Return Selected".equals(action))

        {

            String submitFormIdent = request.getParameter("jspSubmitIdent");
            if(submitFormIdent!=null &&  submitFormIdent.indexOf("#"+ SessionAttributes.SEARCH_FORM.LOCATE_STORE_DIST_FORM)==0){
                StoreAccountMgrLogic.setSelectedDistrId(request,form);
                return SUCCESS;
            }
            else if(submitFormIdent!=null &&  submitFormIdent.indexOf("#"+ SessionAttributes.SEARCH_FORM.LOCATE_STORE_CATALOG_FORM)==0)
            {
                StoreAccountMgrLogic.setSelectedCatalogId(request,form);
                return SUCCESS;
            }
            else  if(submitFormIdent!=null &&  submitFormIdent.indexOf("#"+ SessionAttributes.SEARCH_FORM.LOCATE_STORE_FH_FORM)==0)
            {
                StoreAccountMgrLogic.setSelectedFhId(request,form);
                return SUCCESS;
            }



        }
        else if (action.equals(savePipelineDataStr) ||
                action.equals("update_zip_entry") ) {
            ActionErrors ae = StoreAccountMgrLogic.savePipelineData(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }

        }
        else if (action.equals(addOrderRouteStr)){
            ActionErrors ae = StoreAccountMgrLogic.addOrderRoute(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }

        }else if (action.equals(checkOrderRouteStr)){
            ActionErrors ae =  StoreAccountMgrLogic.testOrderRoute(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
        }
        else  if ( saveStr.equals(action)){
            ActionErrors er=  StoreAccountMgrLogic.updateAccount(request, form);
            if(er.size()>0){
                saveErrors(request,er);
                return FAILURE;
            }

        } else if (action.equals(deleteStr)) {
            ActionErrors ae = StoreAccountMgrLogic.delete(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
            StoreAccountMgrLogic.addAccount(request,form);
            return MAIN;
        } else if (action.equals("selectInventoryItems")) {
            ActionErrors ae =  StoreAccountMgrLogic.getInventoryItemsAvailable(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
        } else if (action.equals("Add to inventory.")) {
            ActionErrors ae =  StoreAccountMgrLogic.addInventoryItems(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
        } else if (action.equals("Remove from inventory")) {
            ActionErrors ae =   StoreAccountMgrLogic.removeInventoryItems(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
        } else if (action.equals("Enable auto order")) {
            ActionErrors ae =   StoreAccountMgrLogic.enableAutoOrderForInventoryItems(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
        } else if (action.equals("Disable auto order")) {
            ActionErrors ae =   StoreAccountMgrLogic.disableAutoOrderForInventoryItems(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
        }else if (action.equals("update_site_del_sched")) {
            ActionErrors ae  = StoreAccountMgrLogic.updateDeliverySchedule
                    (request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }

        }else if (action.equals("deliverySchedInit") ) {
            ActionErrors ae =
                    StoreAccountMgrLogic.initDeliverySchedules(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
                return FAILURE;
            }
        } else if ("editFiscalCalendarData".equals(action)) {
            ActionErrors ae = StoreAccountMgrLogic.editAccountFiscalCal(request, form);
            if(ae.size()>0) {
                saveErrors(request,ae);
            }  
        } else if (createFiscalCalStr.equals(action) || action.equals("setForAllFiscalYears")) {
            ActionErrors ae = StoreAccountMgrLogic.updateAccountFiscalCal(request, form, true);
            if(ae.size()>0) {
                saveErrors(request,ae);
            }  
        } else if ( saveFiscalCalStr.equals(action)){
            ActionErrors ae = StoreAccountMgrLogic.updateAccountFiscalCal(request, form, false);
            if(ae.size()>0) {
                saveErrors(request,ae);
            }
        }
//        else if ( notesStr.equals(action)){
//            ActionErrors ae = StoreAccountMgrLogic.updateAccountFiscalCal(request, form);
//            if(ae.size()>0) {
//                saveErrors(request,ae);
//            }
//        }

        else if (action.equals("sort")) {

            StoreAccountMgrLogic.sort(request, form);
            return  SUCCESS;
        }
        else        {
            StoreAccountMgrLogic.init(request, form);
        }
        return SUCCESS;
    }
}
