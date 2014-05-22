
package com.cleanwise.view.actions;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.logic.UserMgrLogic;
import com.cleanwise.view.logic.SiteMgrLogic;
import com.cleanwise.view.forms.UserMgrDetailForm;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.util.*;
import javax.servlet.http.HttpSession;


/**
 * Implementation of <strong>Action</strong> that processes the
 * user manager page.
 * @author <a href="mailto:dvieira@DVIEIRA"></a>
 */
public final class UserMgrAction extends ActionSuper {
    
    
    // ------------------------------------------------------------ Public Methods
    
    
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form an <code>ActionForm</code> value
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @return an <code>ActionForward</code> value
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet exception occurs
     */
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
        String viewallStr = getMessage(mr,request,"admin.button.viewall");
        String createStr = getMessage(mr,request,"admin.button.create");
        String saveStr = getMessage(mr,request,"global.action.label.save"),
	    saveUserDetail = getMessage(mr,request,"admin.button.saveUserDetail"),
	    saveUserSites = getMessage(mr,request,"admin.button.saveUserSites"),
	    saveUserPermissions = getMessage(mr,request,"admin.button.saveUserPermissions"),
	    saveUserGroups = getMessage(mr,request,"admin.button.saveUserGroups"),
	    updatePassword   = getMessage(mr,request,"admin.button.updatePassword"),
	    updateToInactive = getMessage(mr,request,"admin.button.setToInactive"),
	    updateToActive   = getMessage(mr,request,"admin.button.setToActive")
	    ;

        String createCloneStr = getMessage(mr,request,"admin.button.createClone");
        String configureAllStr = getMessage(mr,request,"admin.button.configureAll"),
            addAccountStr = getMessage(mr,request,"admin.button.addAccount");
        
        // Determine if doing site configuration
        boolean siteConfig = false;
        String siteConfigS = request.getParameter("siteconfig");
        if (siteConfigS != null && siteConfigS.equals("true")) {
            siteConfig = true;
        }
        String gotoString = request.getParameter("goto");
        
        // Determine if doing distributor configuration
        boolean distConfig = false;
        String distConfigS = request.getParameter("distributorconfig");
        if (distConfigS != null && distConfigS.equals("true")) {
            distConfig = true;
        }
        
        String change = request.getParameter("change");
        if(null != change && "type".equals(change)) {
            return (mapping.findForward("userdetail"));
        }
        
        // Process the action
        try {
            
            if (action.equals("init")) {
                if (siteConfig) {
                    // we're doing site config for the particular user
                    UserMgrLogic.initSiteConfig(request, form);
                } else {
                    UserMgrLogic.init(request, form);
                }
                return (mapping.findForward("success"));
            }
            else if (action.equals("Search Configured Sites")) {
              ActionErrors ae = UserMgrLogic.searchUserSiteConfig(request, form);
              if(ae.size()>0) {
                 saveErrors(request, ae);
              }
              return (mapping.findForward("success"));
            }            
            else if (action.equals(searchStr)) {
                if (siteConfig) {
                    // we're only searching for sites that are or can
                    // be configured for the particular user
                    ActionErrors ae = UserMgrLogic.searchSiteConfig(request, form);
                    if(ae.size()>0) {
                      saveErrors(request, ae);
                    }
                } else if(distConfig){
                    // we're only searching for distributor that are or can
                    // be configured for the particular user
                    UserMgrLogic.searchDistributorConfig(request, form, false);
                } else {
                    // general search
                    UserMgrLogic.search(request, form);
                }
                return (mapping.findForward("success"));
            }
            else if (action.equals(configureAllStr)){
                ActionErrors ae = UserMgrLogic.configureAllSites(request, form);
                if(ae.size()>0){
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
                return (mapping.findForward("success"));
            }
            else if (action.equals(addAccountStr)){
                ActionErrors ae = UserMgrLogic.addAccountToUser(request, form);
                if(ae.size()>0){
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
                return (mapping.findForward("success"));
            }
            else if (action.equals("rmAccount")){
                ActionErrors ae = UserMgrLogic.removeAccountFromUser(request, form);
                if(ae.size()>0){
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
                return (mapping.findForward("success"));
            }
            else if (action.equals(createCloneStr)) {
                ActionErrors ae = UserMgrLogic.clone(request, form);
                if(ae.size()>0) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
                return (mapping.findForward("userdetail"));
            }
            
            else if (action.equals("userdetail")) {
                UserMgrLogic.getUserDetail(request, form);
                return (mapping.findForward("userdetail"));
            }
            
            else if (action.equals(createStr)) {
                UserMgrLogic.addUser(request, form);
                return (mapping.findForward("userdetail"));
            }
            else if (   action.equals(updatePassword) ) {
		ActionErrors ae = UserMgrLogic.updatePassword(request, form);
		if (ae.size() > 0 ) {
		    saveErrors(request, ae);
		}
                return (mapping.findForward("success"));
	    }
            else if (   action.equals(updateToInactive) ) {
		ActionErrors ae = UserMgrLogic.updateStatus
		    (request, form,
		     RefCodeNames.USER_STATUS_CD.INACTIVE);
		if (ae.size() > 0 ) {
		    saveErrors(request, ae);
		}
                return (mapping.findForward("success"));
	    }
            else if (   action.equals(updateToActive) ) {
		ActionErrors ae = UserMgrLogic.updateStatus
		    (request, form,
		     RefCodeNames.USER_STATUS_CD.ACTIVE);
		if (ae.size() > 0 ) {
		    saveErrors(request, ae);
		}
                return (mapping.findForward("success"));
	    }
            else if ( action.equals(saveUserGroups) ) {
                ActionErrors ae = UserMgrLogic.updateUserGroups
                    (request, form);
                if (ae.size() > 0 ) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("userconfig"));
	    }
            else if (   action.equals(saveStr) 
		     || action.equals(saveUserDetail) 
		     || action.equals(saveUserSites)
		     ) {
                if (siteConfig || action.equals(saveUserSites) ) {
                    // we're doing site config for the particular user
                    UserMgrLogic.updateSiteConfig(request, form);
                    return (mapping.findForward("success"));
                } else if(distConfig){
                    // we're doing distibutor config for the particular user
                    UserMgrLogic.updateDistributorConfig(request, form);
                    return (mapping.findForward("success"));
                } else {
                    if(form instanceof UserMgrDetailForm) {
                       ActionErrors ae = ((UserMgrDetailForm) form).validate(mapping, request);
                       if(ae!=null && ae.size()>0) {
                         saveErrors(request,ae);
                         return (mapping.findForward("userdetail"));
                       }
                    }
                    ActionErrors ae = UserMgrLogic.updateUser(request, form);
                    if (ae.size() > 0 ) {
                        saveErrors(request, ae);
                    }
                    if ( null != gotoString && gotoString.equals("userconfig") )
                    {
                        return (mapping.findForward("userconfig"));
                    }
                    return (mapping.findForward("userdetail"));
                }
            }
            else if (action.equals("update_account_rights")
		     || action.equals(saveUserPermissions) 
		     ) {
                ActionErrors ae = UserMgrLogic.updateUserAccountRights
                    (request, form);
                if (ae.size() > 0 ) {
                    saveErrors(request, ae);
                }
                return (mapping.findForward("userconfig"));
            }
            else if (action.equals(viewallStr)) {
                if (siteConfig) {
                    // we're only viewing for sites that are or can
                    // be configured for the particular user
                    UserMgrLogic.getAllSiteConfig(request, form);
                } else if(distConfig){
                    // we're only viewing for distributor that are or can
                    // be configured for the particular user
                    UserMgrLogic.searchDistributorConfig(request, form, true);
                } else {
                    UserMgrLogic.getAll(request, form);
                }
                return (mapping.findForward("success"));
            }
            else if (action.equals("customer_profile") ) {
                String uidStr = (String)
                request.getSession().getAttribute(Constants.USER_ID);
                if ( uidStr == null ) {
                    uidStr = "0";
                }
                int uid = Integer.parseInt(uidStr);
                UserMgrLogic.getUserDetailById(request, uid);
                SiteMgrLogic.lookupInventoryData(request, null);
                SiteMgrLogic.lookupShoppingControls(request, null);

                if (form instanceof UserMgrDetailForm) {
                    SiteMgrLogic.putShoppingControlItems2Form(request, (UserMgrDetailForm)form);
                }

                return (mapping.findForward("success"));
            }
            else if (action.equals("shopping_control") ) {
                    String uidStr = (String)
                    request.getSession().getAttribute(Constants.USER_ID);
                    if ( uidStr == null ) {
                        uidStr = "0";
                    }
                    int uid = Integer.parseInt(uidStr);
                    UserMgrLogic.getUserDetailById(request, uid);
                    SiteMgrLogic.lookupInventoryData(request, null);
                    SiteMgrLogic.lookupShoppingControls(request, null);

                    if (form instanceof UserMgrDetailForm) {
                        SiteMgrLogic.putShoppingControlItems2Form(request, (UserMgrDetailForm)form);
                    }

                    return (mapping.findForward("success"));                
            } else if (action.equals("sp_user_profile") ) {
                String uidStr = (String)
                request.getSession().getAttribute(Constants.USER_ID);
                if ( uidStr == null ) {
                    uidStr = "0";
                }
                int uid = Integer.parseInt(uidStr);
                UserMgrLogic.getUserDetailById(request, uid);

                return (mapping.findForward("success"));
            }
            else if (action.equals("update_customer_profile")) {
                ActionErrors ae = UserMgrLogic.updateCustomerInfo(request, form);
                if (ae.size() > 0 ) {
                    saveErrors(request, ae);
                    return (mapping.findForward("failure"));
                }
                return (mapping.findForward("updated"));
            }
            else if (action.equals("sort")) {
                if (siteConfig) {
                    // we're doing site config for the particular user
                    UserMgrLogic.sortSiteConfig(request, form);
                } else if(distConfig) {
                    // we're doing distributor config for the particular user
                    UserMgrLogic.sortDistributorConfig(request, form);
                } else {
                    UserMgrLogic.sort(request, form);
                }
                return (mapping.findForward("success"));
            }else {
                UserMgrLogic.init(request, form);
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
