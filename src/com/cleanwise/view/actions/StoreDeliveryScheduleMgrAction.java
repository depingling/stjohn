
package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.logic.StoreDeliveryScheduleMgrLogic;
import com.cleanwise.view.forms.StoreDeliveryScheduleMgrForm;
import com.cleanwise.view.utils.Constants;
import org.apache.struts.action.ActionErrors;

/**
 * Implementation of <strong>Action</strong> that processes the
 * Store Distributor Delivery Shedule manager page.
 */
public final class StoreDeliveryScheduleMgrAction extends ActionSuper {

    private static final String className = "StoreDeliveryScheduleMgrAction";

    // ----------------------------------------------------- Public Methods


    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
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
        try {

            // Determine the store manager action to be performed
            StoreDeliveryScheduleMgrForm theForm = (StoreDeliveryScheduleMgrForm) form;
            String action = request.getParameter("action");

            if (action == null){
                action = "init";
            }
            String command = request.getParameter("command");

            String scheduleRuleChange = request.getParameter("scheduleRuleChange");
            if(scheduleRuleChange!=null && scheduleRuleChange.trim().length()>0) {
                return (mapping.findForward("success"));
            }

            // Process the action
            if (action.equals("init")) {
                theForm.setContentPage("dlScheduleMgrSearch.jsp");
                ActionErrors ae = StoreDeliveryScheduleMgrLogic.initSearch(request, theForm);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
            }
            else if (action.equals("Create New")) {
                theForm.setContentPage("dlScheduleMgrDetail.jsp");
                ActionErrors ae = StoreDeliveryScheduleMgrLogic.createNew(request, theForm);
                if(ae.size()>0) {
                    theForm.setContentPage("dlScheduleMgrSearch.jsp");
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                } else {
                    return (mapping.findForward("createnew"));
                }
            }
            else if (action.equals("Save")) {
                theForm.setContentPage("dlScheduleMgrDetail.jsp");
                ActionErrors ae = StoreDeliveryScheduleMgrLogic.save(request, theForm);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
            }
            else if (action.equals("Save Configuration")) {
                ActionErrors ae = StoreDeliveryScheduleMgrLogic.saveConfiguration(request, theForm);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
            }
            else if (action.equals("Search")&&(!"Other".equals(theForm.getConfigType()))) {
                String contantPage = theForm.getContentPage();
                ActionErrors ae = new ActionErrors();
                if("dlScheduleMgrSearch.jsp".equals(contantPage)) {
                    StoreDeliveryScheduleMgrLogic.initSearch(request, theForm);
                    ae = StoreDeliveryScheduleMgrLogic.searchSchedule(request, theForm);
                }
                if("dlScheduleMgrDetail.jsp".equals(contantPage)) {
                    StoreDeliveryScheduleMgrLogic.initSearch(request, theForm);
                    ae = StoreDeliveryScheduleMgrLogic.searchTerritory(request, theForm);
                }
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
            }
            else if (action.equals("Search")&&"Other".equals(theForm.getConfigType())) {
                String contantPage = theForm.getContentPage();
                ActionErrors ae = new ActionErrors();
                if("dlScheduleMgrDetail.jsp".equals(contantPage)) {
                    ae = StoreDeliveryScheduleMgrLogic.searchTerritory(request, theForm);
                }
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
            }
            else if (action.equals("Add Code")) {
                String contantPage = theForm.getContentPage();
                ActionErrors ae = new ActionErrors();

                if("dlScheduleMgrDetail.jsp".equals(contantPage)) {                    
                    ae = StoreDeliveryScheduleMgrLogic.addZipCode(request, theForm);
                    if(ae.size()>0) {
                        saveErrors(request, ae);
                        return (mapping.findForward("failure"));
                    }
                    ae = StoreDeliveryScheduleMgrLogic.initSearch(request, theForm);
                }

                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
            }

            else if (action.equals("Find Accounts")) {
                ActionErrors ae = StoreDeliveryScheduleMgrLogic.accountSearch(request, theForm);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
            }
            else if (action.equals("Save Accounts")) {
                ActionErrors ae = StoreDeliveryScheduleMgrLogic.updateAcctConfigured(request, theForm);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
            }

            else if (action.equals("unknown") && "setConfigFunc".equals(command)) {
                ActionErrors ae = StoreDeliveryScheduleMgrLogic.selectConfigFunc(request, theForm);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
            }

            else if (action.equals("unknown") && "setConfigType".equals(command)) {
                ActionErrors ae = StoreDeliveryScheduleMgrLogic.initSearch(request, theForm);                
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
                ae = StoreDeliveryScheduleMgrLogic.selectConfigType(request, theForm);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
            }

            else if (action.equals("detail")) {
                theForm.setContentPage("dlScheduleMgrDetail.jsp");
                ActionErrors ae = StoreDeliveryScheduleMgrLogic.detail(request, theForm);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
            }
            else if (action.equals("Select All")) {
                ActionErrors ae = StoreDeliveryScheduleMgrLogic.selectAll(request, theForm);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
            }
            else if (action.equals("Clear Selection")) {
                ActionErrors ae = StoreDeliveryScheduleMgrLogic.clearSelected(request, theForm);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
            }
            else if (action.equals("sort")) {
                ActionErrors ae = StoreDeliveryScheduleMgrLogic.sort(request, theForm);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
            }
            else if (action.equals("sortTerr")) {
                ActionErrors ae = StoreDeliveryScheduleMgrLogic.sortTerr(request, theForm);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
            }
            else if (action.equals("Delete")) {
                theForm.setContentPage("dlScheduleMgrSearch.jsp");
                ActionErrors ae = StoreDeliveryScheduleMgrLogic.delete(request, theForm);
                if(ae.size()>0) {
                    theForm.setContentPage("dlScheduleMgrDetail.jsp");
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
                return (mapping.findForward("deletesuccess"));
            }
            return (mapping.findForward("success"));

        } catch ( Exception e ) {
            request.setAttribute(Constants.EXCEPTION_OBJECT, e);
            e.printStackTrace();
            return mapping.findForward("error");
        }
    }

}
