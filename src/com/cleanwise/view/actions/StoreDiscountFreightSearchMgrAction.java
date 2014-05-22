package com.cleanwise.view.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.logic.StoreDiscountFreightMgrLogic;
import com.cleanwise.view.logic.StoreFreightMgrLogic;
import com.cleanwise.view.logic.StoreItemCatalogMgrLogic;
import com.cleanwise.view.utils.*;


public class StoreDiscountFreightSearchMgrAction  extends ActionSuper {

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

        // Is there a currently logged on user?
        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
            return mapping.findForward("/userportal/logon");
        }

        // Process the action
        try {
            if (action.equals("init")) {
                StoreDiscountFreightMgrLogic.init(request, form);
                return (mapping.findForward("success"));
            }
            else if (action.equals("search")) {
                StoreDiscountFreightMgrLogic.search(request, form);
                return (mapping.findForward("success"));
            }
            else if (action.equals("sort")) {
                StoreDiscountFreightMgrLogic.sort(request, form);
                return (mapping.findForward("success"));
            }
            else if("Return Selected".equals(action)){
          	  String submitFormIdent = request.getParameter("jspSubmitIdent");
        	  if(submitFormIdent!=null &&  submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_DIST_FORM)==0){
        		  StoreDiscountFreightMgrLogic.setDistFilter(request, form);
              }
        	  if(submitFormIdent!=null &&  submitFormIdent.indexOf("#"+SessionAttributes.SEARCH_FORM.LOCATE_STORE_CATALOG_FORM)==0){  
        		  StoreDiscountFreightMgrLogic.setCatalogFilter(request, form);
        	  }
    		  return (mapping.findForward("success"));
    	  }
            else if(action.equals("Clear Catalog Filter")){
            	StoreDiscountFreightMgrLogic.clearCatalogFilter(request, form);
    		  return (mapping.findForward("success"));
            }else if(action.equals("Clear Distributor Filter")){
            	StoreDiscountFreightMgrLogic.clearDistributorFilter(request, form);
    		  return (mapping.findForward("success"));
            }
            else {
                StoreDiscountFreightMgrLogic.init(request, form);
                return (mapping.findForward("success"));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return (mapping.findForward("failure"));
        }
    }
}
