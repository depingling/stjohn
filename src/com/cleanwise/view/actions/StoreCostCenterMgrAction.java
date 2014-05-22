/*
 * StoreCostCenterMgrAction.java
 *
 * Created on June 15, 2005, 5:32 PM
 */
package com.cleanwise.view.actions;

import com.cleanwise.view.logic.StoreCostCenterMgrLogic;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.service.api.value.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 *
 * @author Ykupershmidt
 */
public class StoreCostCenterMgrAction 
        extends ActionBase {
    public ActionForward performAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {        
        
        // Determine the cost center manager action to be performed
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "init";
        }
        boolean isPollock = false;
        CleanwiseUser appUser = ShopTool.getCurrentUser(request.getSession());
		StoreData currentStore = appUser.getUserStore();
		String storeName = currentStore.getBusEntity().getShortDesc();
		if("pollock".equalsIgnoreCase(storeName)) isPollock = true;
		
        MessageResources mr = getResources(request);
        
        // Get the form buttons as specified in the properties file.
        String saveStr = getMessage(mr,request,"global.action.label.save");
        String deleteStr = getMessage(mr,request,"global.action.label.delete");
        String searchStr = getMessage(mr,request,"global.action.label.search");
        String createStr = getMessage(mr,request,"admin.button.create");
        String saveBudgetStr = getMessage(mr,request,"costcenter.button.savebudgets");
        
        // Process the action
        boolean configFl = 
             ("true".equals(request.getParameter("costCenterConfig")))?true:false;
        if (action.equals("init")) {
		    if(isPollock) {
				StoreCostCenterMgrLogic.initPollock(request, form);
			} else {
				StoreCostCenterMgrLogic.init(request, form);
			}
            
            return (mapping.findForward("success"));
        } else if (action.equals(searchStr)) {
           if(configFl) {
             ActionErrors ae = StoreCostCenterMgrLogic.searchConfCatalog(request, form);
             if(ae.size()>0) {
                saveErrors(request,ae);              
             }
             return (mapping.findForward("success"));
           } else {
             ActionErrors ae = StoreCostCenterMgrLogic.search(request, form);
             if(ae.size()>0) {
                saveErrors(request,ae);              
             }
             return (mapping.findForward("success"));
           }
        } else if (action.equals("reset_item_cost_centers")) {
			if(isPollock) {
				StoreCostCenterMgrLogic.resetCostCentersPollock(request, form);
			} else {
				StoreCostCenterMgrLogic.resetCostCenters(request, form);
			}            
            return (mapping.findForward("success"));
        } else if (action.equals("new")) {
            ActionErrors ae = StoreCostCenterMgrLogic.addCostCenter(request, form);
            if(ae.size()>0) {
              saveErrors(request,ae);              
              return (mapping.findForward("main"));
            }
            return (mapping.findForward("success"));
        } else if (action.equals("updatecostcenter") || action.equals(saveStr)) {
          if(configFl) {
            ActionErrors ae = StoreCostCenterMgrLogic.saveCostCenterAssoc(request,form);            
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return (mapping.findForward("success"));
          } else {
            ActionErrors ae = StoreCostCenterMgrLogic.updateCostCenter(request,form);            
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return (mapping.findForward("success"));
          }
        } else if (action.equals(saveBudgetStr)) {
            
            ActionErrors ae = StoreCostCenterMgrLogic.updateBudgets(request,
                    form);
            
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }           
            return (mapping.findForward("success"));
        
        } else if (action.equals("costcenterdetail")) {
		    if(isPollock) {
				StoreCostCenterMgrLogic.initPollock(request, form);
			} else {
				StoreCostCenterMgrLogic.init(request, form);
			}
            StoreCostCenterMgrLogic.getDetail(request, form);            
            return (mapping.findForward("success"));

        } else if (action.equals("changeBudgetAccount")) {
            StoreCostCenterMgrLogic.changeBudgetAccount(request, form);            
            return (mapping.findForward("success"));

        } else if (action.equals(deleteStr)) {            
            ActionErrors ae = StoreCostCenterMgrLogic.delete(request, form);
            
            if (ae.size() > 0) {
                saveErrors(request, ae);                
                return (mapping.findForward("success"));
            }           
            return (mapping.findForward("main"));
       
        } else if (action.equals("sort")) {
           if(configFl) {
             StoreCostCenterMgrLogic.sortConfig(request, form);
           } else {
             StoreCostCenterMgrLogic.sort(request, form);
           } 
            return (mapping.findForward("success"));
        } else if (action.equals("setCategoryCostCenter")) {
            // Update the cost center for the category specified.
			if(isPollock) {
				ActionErrors ae = StoreCostCenterMgrLogic.updateCategoryCostCenterPollock(request, form);
				if (ae.size() > 0) {
					saveErrors(request, ae);
				}
				// Refresh the session information.
				StoreCostCenterMgrLogic.initPollock(request, form);
			} else {
                ActionErrors ae = StoreCostCenterMgrLogic.updateCategoryCostCenter(request, form);
				if (ae.size() > 0) {
					saveErrors(request, ae);
				}
				// Refresh the session information.
				StoreCostCenterMgrLogic.init(request, form);
			}
            return (mapping.findForward("success"));
        } else if (action.equals("setFreightCostCenter")) {
            ActionErrors ae = StoreCostCenterMgrLogic.updateCostCenterList(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }
            return (mapping.findForward("success"));
        } else if (action.equals("updateBudgetThreshold")) {
            ActionErrors ae = StoreCostCenterMgrLogic.updateBudgetThreshold(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);                
            }
            return (mapping.findForward("success")); 
        } else if (action.equals("changeBudgetType")) {
            StoreCostCenterMgrLogic.changeBudgetType(request, form);
            return (mapping.findForward("success"));
        } else if (action.equals("allSites")) {
            StoreCostCenterMgrLogic.getAllSites(request, form);            
            return (mapping.findForward("success"));

        } else if (action.equals("searchSites")) {
            ActionErrors ae = StoreCostCenterMgrLogic.searchSites(request, form);            
            if (ae.size() > 0) {
                saveErrors(request, ae);                
            }           
            return (mapping.findForward("success"));
        } else if (action.equals("Return Selected")) {
            ActionErrors ae = StoreCostCenterMgrLogic.setCatalogFilter(request, form);            
            if (ae.size() > 0) {
                saveErrors(request, ae);                
            }           
            return (mapping.findForward("success"));
            
        } else if (action.equals("Clear Catalog Filter")) {
            ActionErrors ae = StoreCostCenterMgrLogic.clearCatalogFilter(request, form);            
            if (ae.size() > 0) {
                saveErrors(request, ae);                
            }           
            return (mapping.findForward("success"));
        } else if (action.equals("edit")) {
            ActionErrors ae = StoreCostCenterMgrLogic.editCostCenter(request, form);            
            if (ae.size() > 0) {
                saveErrors(request, ae);                
            }           
            return (mapping.findForward("success"));
        } else if (action.equals("Create New")) {
            ActionErrors ae = StoreCostCenterMgrLogic.addCostCenter(request, form);            
            if (ae.size() > 0) {
                saveErrors(request, ae);                
            }           
            return (mapping.findForward("success"));
        } else if (action.equals("searchInit")) {
            ActionErrors ae = StoreCostCenterMgrLogic.searchInit(request, form);            
            if (ae.size() > 0) {
                saveErrors(request, ae);                
            }           
            return (mapping.findForward("success"));
        } else if (action.equals("configInit")) {
            ActionErrors ae = StoreCostCenterMgrLogic.configInit(request, form);            
            if (ae.size() > 0) {
                saveErrors(request, ae);                
            }           
            return (mapping.findForward("success"));            
        } else {
            //StoreCostCenterMgrLogic.init(request, form);
            
            return (mapping.findForward("success"));
        }
        
    }
  
}
