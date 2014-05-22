
package com.cleanwise.view.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.view.logic.StoreSvcProviderMgrLogic;
import com.cleanwise.view.utils.*;
import javax.servlet.http.HttpSession;


public final class StoreSvcProviderMgrAction extends ActionSuper {

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
      HttpServletResponse response) throws IOException, ServletException {
      
      HttpSession session = request.getSession();  
      // Determine the store manager action to be performed
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

      String configureAllStr = getMessage(mr,request,"admin.button.configureAll"),
      addAccountStr = getMessage(mr,request,"admin.button.addAccount");


		String configFunction = request.getParameter("confFunction");

		boolean acctConfig = false;
		if ("true".equals(request.getParameter("acctconfig"))|| 
		    "acctConfig".equals(configFunction)) {
                    acctConfig = true;
		}
                boolean siteConfig = false;
                if ("true".equals(request.getParameter("siteconfig"))|| 
		    "siteConfig".equals(configFunction)) {
                    siteConfig = true;
		}
      
      // Get the form buttons as specified in the properties file.
      String deleteStr = getMessage(mr, request, "global.action.label.delete");

      String searchStr = getMessage(mr,request,"global.action.label.search");
      String viewallStr = getMessage(mr,request,"admin.button.viewall");
      String createStr = getMessage(mr,request,"admin.button.create");
      String saveStr = getMessage(mr,request,"global.action.label.save"),
             saveUserDetail = getMessage(mr,request,"admin.button.saveUserDetail"),
             saveServiceProviderSites = getMessage(mr,request,"admin.button.saveServiceProviderSites"),
             saveServiceProviderAccounts = getMessage(mr,request,"admin.button.saveServiceProviderAccounts"),
             saveUserPermissions = getMessage(mr,request,"admin.button.saveUserPermissions"),
             saveUserGroups = getMessage(mr,request,"admin.button.saveUserGroups"),
             updatePassword   = getMessage(mr,request,"admin.button.updatePassword"),
             updateToInactive = getMessage(mr,request,"admin.button.setToInactive"),
             updateToActive   = getMessage(mr,request,"admin.button.setToActive")
              ;
      
      String createCloneStr = getMessage(mr,request,"admin.button.createClone");
//      String configureAllStr = getMessage(mr,request,"admin.button.configureAll"),
//              addAccountStr = getMessage(mr,request,"admin.button.addAccount");      
      
      // Process the action
      try {

        if (action.equals("init")) {
            if("true".equals(request.getParameter("initconfig"))) {
                StoreSvcProviderMgrLogic.initConfig(request, form);
              } else {
            	  StoreSvcProviderMgrLogic.init(request, form);
              }
          return (mapping.findForward("success"));
        } else if (action.equals(searchStr)) {
            if (acctConfig) {
                ActionErrors ae = StoreSvcProviderMgrLogic.searchAcctToConfig(request, form);
                if(ae.size()>0) {
                  saveErrors(request, ae);
                }
            } if (siteConfig) {
                ActionErrors ae = StoreSvcProviderMgrLogic.searchSitesToConfig(request, form);
                if(ae.size()>0) {
                  saveErrors(request, ae);
                }
            } else if (!acctConfig && !siteConfig) {
                // general search
                StoreSvcProviderMgrLogic.search(request, form);
            }        	
          return (mapping.findForward("success"));
        } else if (action.equals(createStr)) {
            StoreSvcProviderMgrLogic.addServiceProvider(request, form);
          return (mapping.findForward("serviceproviderdetail"));
          
        } else if (action.equals(saveStr)) {
            ActionErrors ae = StoreSvcProviderMgrLogic.updateServiceProvider(request, form);
            if (ae.size() > 0 ) {
                saveErrors(request, ae);
            }
          return (mapping.findForward("serviceproviderdetail"));
          
        } else if (   action.equals(updateToActive) ) {
              ActionErrors ae = StoreSvcProviderMgrLogic.updateStatus
                      (request, form,
                      RefCodeNames.USER_STATUS_CD.ACTIVE);
              if (ae.size() > 0 ) {
                saveErrors(request, ae);
              }
              return (mapping.findForward("success"));          
          } else if (action.equals(saveServiceProviderAccounts)) {
              ActionErrors ae = StoreSvcProviderMgrLogic.updateAccountConfig(request, form);        
              if (ae.size() > 0 ) {
                saveErrors(request, ae);
              }
              return (mapping.findForward("success"));          
        } else if (action.equals(saveServiceProviderSites)) {
              ActionErrors ae = StoreSvcProviderMgrLogic.updateSiteConfig(request, form);        
              if (ae.size() > 0 ) {
                saveErrors(request, ae);
              }
              return (mapping.findForward("success"));          
        } else if (action.equals("serviceproviderdetail")) {
            if ( session.getAttribute("SvcProv.status.vector") == null ||
                 session.getAttribute("SvcProv.type.vector") == null ||
                 session.getAttribute("countries.vector") == null ) {
                StoreSvcProviderMgrLogic.init(request, form);
            }  
            ActionErrors ae = StoreSvcProviderMgrLogic.getDetail(request, form);
            if (ae.size() > 0) {
                saveErrors(request, ae);
            }  
            
          return (mapping.findForward("serviceproviderdetail"));
        } else if (action.equals(deleteStr)) {
          ActionErrors ae =
            StoreSvcProviderMgrLogic.delete(request, form);
          if (ae.size() > 0) {
            saveErrors(request, ae);
            return (mapping.findForward("serviceproviderdetail"));
          }
          return (mapping.findForward("main"));
        } else if (action.equals("sort")) {
            if(acctConfig) {
                StoreSvcProviderMgrLogic.sortAcctConfig(request, form);
            } else {
                StoreSvcProviderMgrLogic.sort(request, form);
            }
            return (mapping.findForward("success"));

        } else {
            StoreSvcProviderMgrLogic.init(request, form);
          return (mapping.findForward("success"));
        }
      } catch (Exception e) {
        e.printStackTrace();
        return (mapping.findForward("failure"));
      }

    }

}

