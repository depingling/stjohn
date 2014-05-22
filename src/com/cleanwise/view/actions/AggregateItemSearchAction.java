/*
 * AggregateItemMgrAction.java
 *
 * Created on December 1, 2003, 5:03 PM
 */

package com.cleanwise.view.actions;
import com.cleanwise.service.api.util.RefCodeNames;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.logic.AggregateItemLogic;
import com.cleanwise.view.forms.AggregateItemMgrForm;
import com.cleanwise.service.api.value.ProductData;
/**
 *
 * @author  bstevens
 */
public class AggregateItemSearchAction extends ActionSuper {
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
        
        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
        
        MessageResources mr = getResources(request);
        String nextStr = getMessage(mr,request,"global.action.label.next");
        String backStr = getMessage(mr,request,"admin.button.back");
        String searchStr = getMessage(mr,request,"global.action.label.search");
        String lastStepStr = "last";
        //init the forwards according to the step in the wizard
        String successForward;
        String backForward;
        String failForward;
        String nextWizardStep;
        AggregateItemMgrForm sForm = (AggregateItemMgrForm) form;
        String currentWizardStep = sForm.getWizardStep();
        boolean catalogSearchOveride = sForm.isUsingCatalogSearch();
        if(currentWizardStep != null && currentWizardStep.length() > 0){
            nextWizardStep = currentWizardStep + "Next";
            failForward = currentWizardStep;
            if(catalogSearchOveride){
                backForward = "catalogselect";
            }else{
                backForward = currentWizardStep + "Back";
            }
            successForward = currentWizardStep;
        }else{
            backForward = "success";
            nextWizardStep = "success";
            failForward = "failure";
            successForward = "success";
        }

        // Process the action
        try {
            ActionErrors errors = new ActionErrors();
            if(action.equals("init")){
                AggregateItemLogic.init(request, form,false);
                successForward = "success";
                failForward = "failure";
            }else if(action.equals("catInit")){
                AggregateItemLogic.init(request, form,true);
                successForward = "catalogselect";
                failForward = "failure";
            }else if(action.equals(lastStepStr)){
                successForward = "last";
            }else if(action.equals(searchStr)){
                if(currentWizardStep.indexOf("store") > -1){
                    AggregateItemLogic.searchStores(request, form);
                }else if(currentWizardStep.indexOf("dist") > -1){
                    AggregateItemLogic.searchDistributors(request, form);
                }else if(currentWizardStep.indexOf("acc") > -1){
                    AggregateItemLogic.searchAccounts(request, form);
                }else if(currentWizardStep.indexOf("item") > -1){
                    AggregateItemLogic.searchItems(request, form);
                    if(sForm.getItems().size() == 1){
                        ProductData id =(ProductData) sForm.getItems().get(0);
                        sForm.setCurrManagingItem(id);
                        //request.setAttribute("itemId", Integer.toString(id.getItemData().getItemId()));
                        successForward = "detail";
                    }
                }else if(currentWizardStep.indexOf("cat") > -1){
                    AggregateItemLogic.searchCatalogs(request, form);
                }
            }else if(action.equals(nextStr)){
                AggregateItemLogic.saveSearchState(request, form);
                successForward = nextWizardStep;
            }else if(action.equals(backStr)){
                successForward = backForward;
            }
            
            String theForward;
            if(errors.size() > 0){
                saveErrors(request,errors);
                theForward = failForward;
            }else{
                theForward = successForward;
            }
            if(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
                theForward = theForward +"SA";
            }
            return (mapping.findForward(theForward));
        }catch (Exception e) {
            request.setAttribute("errorobject", e);
            ActionErrors ae = new ActionErrors();
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.genericError",e.getMessage()));
            saveErrors(request, ae);
            e.printStackTrace();
            if(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
                return (mapping.findForward(failForward+"SA"));
            }
            return (mapping.findForward(failForward));
        }
    }
}
