package com.cleanwise.view.actions;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.MessageResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cleanwise.view.logic.StoreCostCenterMgrLogic;
import com.cleanwise.view.forms.StoreCostCenterMgrForm;
import com.cleanwise.view.utils.SessionAttributes;

/**
 */
public class StoreCostCenterBudgetMgrAction extends ActionBase {

    public ActionForward performAction(ActionMapping mapping,
                                       ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {

        // Determine the cost center manager action to be performed
        String action = request.getParameter("action");

        if (action == null) {
            action = "init";
        }

        MessageResources mr = getResources(request);

        // Get the form buttons as specified in the properties file.
        String saveBudgetStr = getMessage(mr, request, "costcenter.button.savebudgets");
        // Process the action
        if (action.equals("init")) {

            ActionErrors ae = StoreCostCenterMgrLogic.initBudgetConfig(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return (mapping.findForward("success"));

        } else if (action.equals("budgetConfig")) {

            ActionErrors ae = StoreCostCenterMgrLogic.getBudgetConfig(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return (mapping.findForward("success"));

        } else if ("Return Selected".equals(action)) {

            String submitFormIdent = request.getParameter("jspSubmitIdent");
            if (submitFormIdent != null && submitFormIdent.indexOf("#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_ACCOUNT_FORM) == 0) {
                ActionErrors ae = StoreCostCenterMgrLogic.getAccountBudgets((StoreCostCenterMgrForm) form, null);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
            } else if (submitFormIdent != null && submitFormIdent.indexOf("#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_SITE_FORM) == 0) {
                ActionErrors ae = StoreCostCenterMgrLogic.getBudgetConfig(request, form);
                if (ae.size() > 0) {
                    saveErrors(request, ae);
                }
                StoreCostCenterMgrLogic.removeAccountFilterOfSite((StoreCostCenterMgrForm) form);
            }
            return (mapping.findForward("success"));

        } else if ("Locate Site".equals(action)) {

            StoreCostCenterMgrLogic.addAccountFilterToSite((StoreCostCenterMgrForm) form);
            return (mapping.findForward("success"));

        } else if (action.equals(saveBudgetStr)) {

            ActionErrors ae = StoreCostCenterMgrLogic.updateBudgets(request, (StoreCostCenterMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return (mapping.findForward("success"));

        } else if (action.equals("changeBudgetAccount")) {

            StoreCostCenterMgrLogic.changeBudgetAccount(request, (StoreCostCenterMgrForm) form);
            return (mapping.findForward("success"));

        } else if (action.equals("changeBudgetSite")) {

            ActionErrors ae = StoreCostCenterMgrLogic.changeBudgetSite(request, (StoreCostCenterMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return (mapping.findForward("success"));

        } else if (action.equals("changeBudgetType")) {

            ActionErrors ae = StoreCostCenterMgrLogic.changeBudgetType(request, (StoreCostCenterMgrForm) form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return (mapping.findForward("success"));

        } else {
            return (mapping.findForward("success"));
        }
    }
}
