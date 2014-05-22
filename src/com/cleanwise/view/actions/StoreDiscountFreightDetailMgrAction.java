package com.cleanwise.view.actions;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.LocatePropertyNames;
import com.cleanwise.view.forms.StoreDiscountFreightMgrForm;
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
import com.cleanwise.view.logic.StoreDiscountFreightMgrLogic;
import com.cleanwise.view.utils.*;


public class StoreDiscountFreightDetailMgrAction  extends ActionSuper {

    public ActionForward performSub(
                 ActionMapping mapping,
                 ActionForm form,
                 HttpServletRequest request,
                 HttpServletResponse response)
                 throws IOException, ServletException {

        // Get the action and the freightTableId from the request.
        String action = request.getParameter("action");
        if (action == null) {
            action = "add";
        }

        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if (st.checkSession() == false) {
            return mapping.findForward("/userportal/logon");
        }

        String freightTableId = request.getParameter("id");
        if (freightTableId == null) {
            freightTableId = "";
        }

        String delLineStr = "delLine";
        StoreDiscountFreightMgrForm sform = (StoreDiscountFreightMgrForm) form;
        if (Utility.isSet(sform.getActionOveride())) {
            action = sform.getActionOveride();
            sform.setActionOveride(null);
        }

        if ("edit".equals(action) && ("".equals(freightTableId) || "0".equals(freightTableId))) {
            HttpSession session = request.getSession();
            freightTableId = (String) session.getAttribute("FreightTable.id");
            if (null == freightTableId || "".equals(freightTableId) || "0".equals(freightTableId)) {
                action = "add";
            }
        }

        String change = request.getParameter("change");
        if(null != change && "type".equals(change)) {
            return (mapping.findForward("display"));
        }

        MessageResources mr = getResources(request);

        // Get the form buttons as specified in the properties file.
        String saveStr = getMessage(mr,request,"global.action.label.save");
        String addMoreStr = getMessage(mr,request,"admin.button.addMoreCriteria");

        try {
            // Add a new advisor.
            if (action.equals("add")) {
                StoreDiscountFreightMgrLogic.addFreightTable(request, form);
                return (mapping.findForward("display"));
            }
            // Edit an existing freightTable.
            else if (action.equals("edit")) {
                StoreDiscountFreightMgrLogic.editFreightTable(request, form, freightTableId);
                return (mapping.findForward("display"));
            }
            else if (action.equals("Reload")) {
                StoreDiscountFreightMgrLogic.reloadFreightTable(request, form);
                return (mapping.findForward("display"));
            }
            // Save the new or updated freightTable.
            else if (action.equals(saveStr)) {
                ActionErrors ae = StoreDiscountFreightMgrLogic.saveFreightTable(request, form);
                if (ae.size() > 0 ) {
                    saveErrors(request, ae);
                    return (mapping.findForward("display"));
                }
            }      
            else if (action.equals(addMoreStr)) {
                StoreDiscountFreightMgrLogic.addMoreCriteria(request, form);
                return (mapping.findForward("display"));
            }
            else if (action.equals(delLineStr)) {
                StoreDiscountFreightMgrLogic.deleteCriteria(request, form);
                return (mapping.findForward("display"));
            }
            else if (action.equalsIgnoreCase("Locate Distributor")
                || action.equalsIgnoreCase("Clear Distributor Filter")
                || action.equalsIgnoreCase("Return Selected")
                || action.equalsIgnoreCase("Cancel")) {
                // do nothing because the actions of LocateStoreDistributor are processed 
                // in ActionSuper.          
            }
            else if (action.equalsIgnoreCase(LocatePropertyNames.LOCATE_STORE_CATALOG_ACTION)) {
                // do nothing because the actions of LocateStoreCatalog are processed 
                // in ActionSuper.
            }
            else if (action.equalsIgnoreCase(LocatePropertyNames.CLEAR_STORE_CATALOG_FILTER_ACTION)) {
                StoreDiscountFreightMgrLogic.clearStoreCatalogFilter(request, form);
            }
            // Users should never get here, but if they do, perform an add.
            else {
                StoreDiscountFreightMgrLogic.addFreightTable(request, form);
                return (mapping.findForward("display"));
            }
            return (mapping.findForward("display"));
        }
        // Catch all exceptions here.
        catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("errorobject", ex);
            return (mapping.findForward("error"));
        }
    }
}
