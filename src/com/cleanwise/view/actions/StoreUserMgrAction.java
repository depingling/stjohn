/*
 * StoreUserMgrAction.java
 *
 * Created on May 1, 2006, 12:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.cleanwise.view.actions;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.forms.StoreUserMgrForm;
import com.cleanwise.view.logic.SiteMgrLogic;
import com.cleanwise.view.logic.StoreUserMgrLogic;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;

/**
 *
 * @author Ykupershmidt
 */
public class StoreUserMgrAction  extends ActionSuper {
	
    private static final Logger log = Logger.getLogger(StoreUserMgrAction.class);
  
  public ActionForward performSub(
          ActionMapping mapping,
          ActionForm form,
          HttpServletRequest request,
          HttpServletResponse response)
          throws IOException, ServletException {
    
    // Is there a currently logged on user?
    SessionTool st = new SessionTool(request);
    if ( st.checkSession() == false ) {
      return mapping.findForward("/userportal/logon");
    }
    
    // Determine the user manager action to be performed
    String action = request.getParameter("action");
    if (action == null) {
      action = "init";
    }
    MessageResources mr = getResources(request);
    
    // Get the form buttons as specified in the properties file.
    String searchStr = getMessage(mr,request,"global.action.label.search");
    String createStr = getMessage(mr,request,"admin.button.create");
    String saveStr = getMessage(mr,request,"global.action.label.save"),
            saveUserDetail = getMessage(mr,request,"admin.button.saveUserDetail"),
            saveUserSites = getMessage(mr,request,"admin.button.saveUserSites"),
            saveUserAccounts = getMessage(mr,request,"admin.button.saveUserAccounts"),
            saveUserPermissions = getMessage(mr,request,"admin.button.saveUserPermissions"),
            saveUserGroups = getMessage(mr,request,"admin.button.saveUserGroups"),
            updatePassword   = getMessage(mr,request,"admin.button.updatePassword"),
            updateToInactive = getMessage(mr,request,"admin.button.setToInactive"),
            updateToActive   = getMessage(mr,request,"admin.button.setToActive")
            ;
    
    String createCloneStr = getMessage(mr,request,"admin.button.createClone");    
    
    String configFunction = request.getParameter("confFunction");
    boolean siteConfig = false;
    if ("true".equals(request.getParameter("siteconfig")) ||
        "siteConfig".equals(configFunction)) {
      siteConfig = true;
    }
    boolean acctConfig = false;
    if ("true".equals(request.getParameter("acctconfig"))|| 
        "acctConfig".equals(configFunction)) {
      acctConfig = true;
    }
    boolean catalogConfig = false;
    if ("true".equals(request.getParameter("catalogconfig"))|| 
        "catalogConfig".equals(configFunction)) {
      catalogConfig = true;
    }
    boolean ogConfig = false;
    if ("true".equals(request.getParameter("ogconfig"))|| 
        "ogConfig".equals(configFunction)) {
      ogConfig = true;
    }
    boolean groupConfig = false;
    if ("true".equals(request.getParameter("groupconfig"))|| 
        "groupConfig".equals(configFunction)) {
      groupConfig = true;
    }
    String gotoString = request.getParameter("goto");
    boolean permConfig = false;
    if ("true".equals(request.getParameter("permconfig"))|| 
        "permConfig".equals(configFunction)) {
      permConfig = true;
    }
    
    // Determine if doing distributor configuration
    boolean distConfig = false;
    String distConfigS = request.getParameter("distconfig");
    if (distConfigS != null && distConfigS.equals("true")) {
      distConfig = true;
    }
    
    // Determine if doing service provider configuration
    boolean spConfig = false;
    String spConfigS = request.getParameter("spconfig");
    if (spConfigS != null && spConfigS.equals("true")) {
      spConfig = true;
    }
    
    String change = request.getParameter("change");
    if(null != change && "type".equals(change)) {
      return (mapping.findForward("userdetail"));
    }
    
    // Process the action
    try {
      
      if (action.equals("init")) {
        if("true".equals(request.getParameter("initconfig"))) {
          StoreUserMgrLogic.initConfig(request, form);
        }
        else if (siteConfig) {
          StoreUserMgrLogic.initSiteConfig(request, form);
        } else if(acctConfig) {
          StoreUserMgrLogic.initAcctConfig(request, form);
        } else if(permConfig) {
          StoreUserMgrLogic.initPermConfig(request, form);
        } else if(groupConfig) {
          StoreUserMgrLogic.initGroupConfig(request, form);
        } else if(distConfig) {
          StoreUserMgrLogic.initDistConfig(request, form);
        } else if(spConfig) {
          StoreUserMgrLogic.initServiceProviderConfig(request, form);
        } else if(catalogConfig) {
          StoreUserMgrLogic.initCatalogConfig(request, form);
        } else if(ogConfig) {
          StoreUserMgrLogic.initOgConfig(request, form);
        } else {
          StoreUserMgrLogic.init(request, form);
          //if there are any errors in the session (which will be the case if there were
          //errors logging into the new ui as a proxy user - see SecurityAction.handleProxyLoginRequest),
          //retrieve them and save them as errors in the request.  This is something of a hack, but these
          //errors needed to be persisted across a redirect.
          Object errors = Utility.getSessionDataUtil(request).getErrors();
          if (errors != null && errors instanceof ActionErrors) {
        	  saveErrors(request, (ActionErrors)errors);
        	  //now that the errors are processed, blank them out
        	  Utility.getSessionDataUtil(request).setErrors(null);
          }
        }
        return (mapping.findForward("success"));

      } else if (action.equals(searchStr)) {
        if (siteConfig) {
          ActionErrors ae = StoreUserMgrLogic.searchSitesToConfig(request, form);
          if(ae.size()>0) {
            saveErrors(request, ae);
          }
        } else if(acctConfig){
          ActionErrors ae = StoreUserMgrLogic.searchAcctToConfig(request, form);
          if(ae.size()>0) {
            saveErrors(request, ae);
          }
        } else if(permConfig){
          ActionErrors ae = StoreUserMgrLogic.getUserPermissions(request, form);
          if(ae.size()>0) {
            saveErrors(request, ae);
          }
        } else if(distConfig){
          ActionErrors ae = StoreUserMgrLogic.searchDistToConfig(request, form);
          if(ae.size()>0) {
            saveErrors(request, ae);
          }
        } else if(spConfig){
          ActionErrors ae = StoreUserMgrLogic.searchServiceProvidersToConfig(request, form);
          if(ae.size()>0) {
            saveErrors(request, ae);
          }
        } else if(catalogConfig){
          ActionErrors ae = StoreUserMgrLogic.searchCatalogConfig(request, form);
          if(ae.size()>0) {
            saveErrors(request, ae);
          }
        } else if(ogConfig){
          ActionErrors ae = StoreUserMgrLogic.searchOgConfig(request, form);
          if(ae.size()>0) {
            saveErrors(request, ae);
          }
        } else if(groupConfig){
            ActionErrors ae = StoreUserMgrLogic.searchGroupsToConfig(request, form);
            if(ae.size()>0) {
              saveErrors(request, ae);
            }
        } else {
          // general search
          StoreUserMgrLogic.search(request, form);
        }
        return (mapping.findForward("success"));
      } else if (action.equals("Configure All Accounts") && acctConfig){
        ActionErrors ae = StoreUserMgrLogic.configureAllAccounts(request, form);
        if(ae.size()>0){
          saveErrors(request, ae);
          return (mapping.findForward("failure"));
        }
        return (mapping.findForward("success"));
      } else if (action.equals("Configure All Sites") && acctConfig){
        ActionErrors ae = StoreUserMgrLogic.configureAllAccountSites(request, form);
        if(ae.size()>0){
          saveErrors(request, ae);
          return (mapping.findForward("failure"));
        }
        return (mapping.findForward("success"));
      } else if (action.equals("showWholeAcctList")) {
        ActionErrors ae = StoreUserMgrLogic.setWholeAcctList(request,form);
        if(ae.size()>0){
          saveErrors(request, ae);
          return (mapping.findForward("failure"));
        }
        return (mapping.findForward("success"));
      } else if (action.equals(createCloneStr)) {
        ActionErrors ae = StoreUserMgrLogic.clone(request, form);
        if(ae.size()>0) {
          saveErrors(request, ae);
          return (mapping.findForward("failure"));
        }
        return (mapping.findForward("userdetail"));
      }
      
      else if (action.equals("userdetail")) {
        ActionErrors ae=StoreUserMgrLogic.getUserDetail(request, form);
        if(ae.size()>0)
        {
         saveErrors(request,ae);
         return (mapping.findForward("failure"));
        }
        return (mapping.findForward("userdetail"));
      }
      
      else if (action.equals("returnToDetailPage")) {
        StoreUserMgrLogic.getUserDetail(request, form);
        return (mapping.findForward("userdetail"));
      }
      
      else if (action.equals(createStr)) {
        StoreUserMgrLogic.addUser(request, form);
        return (mapping.findForward("userdetail"));
      } else if (   action.equals(updatePassword) ) {
        ActionErrors ae = StoreUserMgrLogic.updatePassword(request, form);
        if (ae.size() > 0 ) {
          saveErrors(request, ae);
        }
        return (mapping.findForward("success"));
      } else if (   action.equals(updateToInactive) ) {
        ActionErrors ae = StoreUserMgrLogic.updateStatus
                (request, form,
                RefCodeNames.USER_STATUS_CD.INACTIVE);
        if (ae.size() > 0 ) {
          saveErrors(request, ae);
        }
        return (mapping.findForward("success"));
      } else if (   action.equals(updateToActive) ) {
        ActionErrors ae = StoreUserMgrLogic.updateStatus
                (request, form,
                RefCodeNames.USER_STATUS_CD.ACTIVE);
        if (ae.size() > 0 ) {
          saveErrors(request, ae);
        }
        return (mapping.findForward("success"));
      } else if ( action.equals(saveUserGroups) ) {
        ActionErrors ae = StoreUserMgrLogic.updateUserGroups
                (request, form);
        if (ae.size() > 0 ) {
          saveErrors(request, ae);
        }
        return (mapping.findForward("success"));
      } else if (   action.equals(saveStr)
      || action.equals(saveUserDetail)
      || action.equals(saveUserSites)
      ) {
        if (siteConfig || action.equals(saveUserSites) ) {
          // we're doing site config for the particular user
        	ActionErrors ae = StoreUserMgrLogic.updateSiteConfig(request, form);
        	if (ae.size() > 0 ) {
        		saveErrors(request, ae);
        	}
          return (mapping.findForward("success"));
        } else if(distConfig){
          // we're doing distibutor config for the particular user
          StoreUserMgrLogic.updateDistributorConfig(request, form);
          return (mapping.findForward("success"));
        } else if(spConfig){
          // we're doing service provider config for the particular user
          StoreUserMgrLogic.updateServiceProviderConfig(request, form);
          return (mapping.findForward("success"));
        } else {
          if(form instanceof StoreUserMgrForm) {
            ActionErrors ae = ((StoreUserMgrForm) form).validate(mapping, request);
            if(ae!=null && ae.size()>0) {
              saveErrors(request,ae);
              return (mapping.findForward("userdetail"));
            }
          }
          ActionErrors ae;
          if ("yes".equals(System.getProperty("multi.store.db"))) { //Multiple DB schemas are defined
        	  ae = StoreUserMgrLogic.updateUserMultiStoreDb(request, form);
          } else {
              ae = StoreUserMgrLogic.updateUser(request, form);
          }
          if (ae.size() > 0 ) {
            saveErrors(request, ae);
          }
          if ( null != gotoString && gotoString.equals("userconfig") ) {
            return (mapping.findForward("userconfig"));
          }
          return (mapping.findForward("userdetail"));
        }
      } else if (action.equals(saveUserAccounts)) {
        ActionErrors ae = StoreUserMgrLogic.updateAccountConfig(request, form);        
        if (ae.size() > 0 ) {
          saveErrors(request, ae);
        }
        return (mapping.findForward("success"));
      } else if (action.equals("update_account_rights")
      || action.equals(saveUserPermissions)
      ) {
        ActionErrors ae = StoreUserMgrLogic.updateUserAccountRights
                (request, form);
        if (ae.size() > 0 ) {
          saveErrors(request, ae);
        }
        return (mapping.findForward("success"));
      } else if (action.equals("customer_profile") ) {
        String uidStr = (String)
        request.getSession().getAttribute(Constants.USER_ID);
        if ( uidStr == null ) {
          uidStr = "0";
        }
        int uid = Integer.parseInt(uidStr);
        StoreUserMgrLogic.getUserDetailById(request,(StoreUserMgrForm) form, uid);
        log.info("customer_profile, SiteMgrLogic.lookupInventoryData");
        SiteMgrLogic.lookupInventoryData(request, null);
        log.info("customer_profile, SiteMgrLogic.lookupShoppingControls");
        SiteMgrLogic.lookupShoppingControls(request, null);
        
        return (mapping.findForward("success"));
      } else if (action.equals("update_customer_profile")) {
        ActionErrors ae = StoreUserMgrLogic.updateCustomerInfo(request, form);
        if (ae.size() > 0 ) {
          saveErrors(request, ae);
          return (mapping.findForward("failure"));
        }
        return (mapping.findForward("updated"));
      } else if (action.equals("sort")) {
        if (siteConfig) {
          StoreUserMgrLogic.sortSiteConfig(request, form);
        } else if(acctConfig) {
          StoreUserMgrLogic.sortAcctConfig(request, form);
        } else if(distConfig) {
          StoreUserMgrLogic.sortDistributorConfig(request, form);
        } else if(spConfig) {
          StoreUserMgrLogic.sortServiceProviderConfig(request, form);
        } else if(catalogConfig) {
          StoreUserMgrLogic.sortCatalogConfig(request, form);
        } else if(ogConfig) {
          StoreUserMgrLogic.sortOgConfig(request, form);
        } else {
          StoreUserMgrLogic.sort(request, form);
        }
        return (mapping.findForward("success"));
      }else {
        StoreUserMgrLogic.init(request, form);
        return (mapping.findForward("success"));
      }
    }catch (Exception e) {
      e.printStackTrace();
      ActionErrors ae = new ActionErrors();
      ae.add("user", new ActionError("error.systemError",e.getMessage()));
      saveErrors(request, ae);
      return (mapping.findForward("failure"));
    }
  }
}


