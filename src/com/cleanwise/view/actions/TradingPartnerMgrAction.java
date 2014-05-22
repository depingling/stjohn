 
package com.cleanwise.view.actions;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.logic.TradingPartnerMgrLogic;
import com.cleanwise.view.forms.TradingPartnerMgrForm;
import org.apache.struts.action.ActionErrors;

import org.apache.struts.util.MessageResources;


/**
 * Implementation of <strong>Action</strong> that processes the
 * Catalog manager page.
 */
public final class TradingPartnerMgrAction extends ActionBase {
    
    
    // ----------------------------------------------------- Public Methods
    
    
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
    public ActionForward performAction(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {
        
        
        TradingPartnerMgrForm theForm = (TradingPartnerMgrForm) form;
        String action = request.getParameter("action");
        ActionErrors ae = new ActionErrors();
        String forward = "display";
        if (action == null) action = "init";
        MessageResources resources = getResources(request);
        String backStr = getMessage(resources, request, "admin.button.back");
        String partnerTypeChange = request.getParameter("partnerTypeChange");
        if(partnerTypeChange!=null && partnerTypeChange.trim().length()>0) {
            ae = TradingPartnerMgrLogic.clearBusEntity(request, theForm);
            //must return hear or the content page will be cleared out and user will be
            //redirected to search screen
            return (mapping.findForward(forward));
        }
        String tradingTypeChange = request.getParameter("tradingTypeChange");
        if(tradingTypeChange!=null && tradingTypeChange.trim().length()>0) {
            if(!(ae.size() == 0)){
                saveErrors(request, ae);
            }
            TradingPartnerMgrLogic.initFormVariables(request,theForm);
            //must return hear or the content page will be cleared out and user will be
            //redirected to search screen
            return (mapping.findForward(forward));
        }
        
        // Process the action
        if (action.equals("init")) {
            theForm.setContentPage("tradingPartnerMgrBody.jsp");
            ae = TradingPartnerMgrLogic.init(request, theForm);
            //TradingPartnerMgrLogic.initProfileMapping(request, theForm);
        }
        else if (action.equals("Create New") || action.equals("createNew")) {
            theForm.setContentPage("tradingPartnerMgrDetail.jsp");
            ae = TradingPartnerMgrLogic.clearDetail(request, theForm);
        }
        else if (action.equals("Add Distributor")) {
            theForm.setContentPage("tradingPartnerMgrDetail.jsp");
            ae = TradingPartnerMgrLogic.addBusEntity(request, theForm);
        }
        else if (action.equals("Add Association")) {
            theForm.setContentPage("tradingPartnerMgrDetail.jsp");
            ae = TradingPartnerMgrLogic.addBusEntity(request, theForm);
        }
        else if (action.equals("delete_assoc")) {
            theForm.setContentPage("tradingPartnerMgrDetail.jsp");
            String busEntIdS = request.getParameter("busEntityId");
            theForm.setBusEntityIdToDelete(busEntIdS);
            ae = TradingPartnerMgrLogic.deleteBusEntity(request, theForm);
        }
        else if (action.equals("Save")) {
            theForm.setContentPage("tradingPartnerMgrDetail.jsp");
            ae = TradingPartnerMgrLogic.update(request, theForm);
        }
        else if (action.equals("Add Profile")) {
            theForm.setContentPage("tradingPartnerMgrDetail.jsp");
            ae = TradingPartnerMgrLogic.addProfile(request, theForm);
        }
        else if (action.equals("Update Profile")) {
            theForm.setContentPage("tradingPartnerMgrDetail.jsp");
            ae = TradingPartnerMgrLogic.updateProfile(request, theForm);
        }
        else if (action.equals("Define Data Exchange")) {
            theForm.setContentPage("tradingPartnerMgrDetail.jsp");
            ae = TradingPartnerMgrLogic.updateDataExchange(request, theForm);
            if(ae.size() == 0) {
                ae = TradingPartnerMgrLogic.getDetail(request, theForm);
            }
        }
        else if (action.equals("edit_profile")) {
            theForm.setContentPage("tradingPartnerMgrDetail.jsp");
            ae = TradingPartnerMgrLogic.getProfile(request, theForm);
        }
        else if (action.equals("delete_profile")) {
            theForm.setContentPage("tradingPartnerMgrDetail.jsp");
            ae = TradingPartnerMgrLogic.deleteProfile(request, theForm);
            if(ae.size() == 0){
                ae = TradingPartnerMgrLogic.getDetail(request, theForm);
            }
        }
        else if (action.equals("delete_data_exchange")) {
            theForm.setContentPage("tradingPartnerMgrDetail.jsp");
            ae = TradingPartnerMgrLogic.deleteDataExchange(request, theForm);
            if(ae.size() == 0){
                ae = TradingPartnerMgrLogic.getDetail(request, theForm);
            }
        }
        else if (action.equals("detail")) {
            theForm.setContentPage("tradingPartnerMgrDetail.jsp");
            ae = TradingPartnerMgrLogic.getDetail(request, theForm);
        }
        else if (action.equals("Search")) {
            theForm.setContentPage("tradingPartnerMgrBody.jsp");
            ae = TradingPartnerMgrLogic.search(request, theForm);
        }
        else if (action.equals("sort")) {
            theForm.setContentPage("tradingPartnerMgrBody.jsp");
            ae = TradingPartnerMgrLogic.sort(request, theForm);
        }
        else if (action.equals("Delete")) {
            theForm.setContentPage("tradingPartnerMgrBody.jsp");
            ae = TradingPartnerMgrLogic.delete(request, theForm);
            if(!(ae.size() == 0)) {
                theForm.setContentPage("tradingPartnerMgrDetail.jsp");
            }
        }
        else if (action.equals("edit_profile_mapping")) {
            ae = TradingPartnerMgrLogic.getProfileMapping(request, theForm);
            TradingPartnerMgrLogic.initProfileMapping(request, theForm);
        }
        else if (action.equals("Update Mappings")) {
            ae = TradingPartnerMgrLogic.updateProfileMapping(request, theForm);
            TradingPartnerMgrLogic.initProfileMapping(request, theForm);
        }
        else if (action.equals("Delete Mapping")) {
            TradingPartnerMgrLogic.deleteProfileMapping(request, theForm);
            TradingPartnerMgrLogic.initProfileMapping(request, theForm);
        }
        else if (action.equals("Add Mapping")) {
            ae = TradingPartnerMgrLogic.addBlankProfileMapping(request, theForm);
        }
        else if (action.equals(backStr)){
            forward = "back";
        }
        else if (action.equals("create_profile")){
            TradingPartnerMgrLogic.initNewProfile(request, theForm);
        }
        else if (action.equals("Import")){
        	ae = TradingPartnerMgrLogic.importTradingPartner(request, theForm);
        }
        else if (action.equals("Export")){
        	ae = TradingPartnerMgrLogic.exportTradingPartner(request, response, theForm);
            if (ae == null || ae.size() == 0)
            	return null;
        }
        TradingPartnerMgrLogic.initFormVariables(request,theForm);
        if(!(ae.size() == 0)) {
            saveErrors(request,ae);
        }
        
        
        return (mapping.findForward(forward));
    }
    
}
