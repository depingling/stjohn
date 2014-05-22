/**
 * Title: UserProfileAction 
 * Description: This is the Struts Action class handling the ESW user profile functionality.
 */

package com.espendwise.view.actions.esw;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.espendwise.view.forms.esw.UserProfileForm;
import com.espendwise.view.logic.esw.UserProfileLogic;

/**
 * Implementation of <code>Action</code> that handles log on functionality.
 */
public final class UserProfileAction extends EswAction {
    private static final Logger log = Logger.getLogger(UserProfileAction.class);
    
    //constants to hold the various action mappings that can be returned by this action class.
    private static final String MAPPING_PROFILE = "userProfile"; 
    private static final String MAPPING_CHANGE_PASSWORD = "changePassword"; 
    private static final String MAPPING_RESET_PASSWORD = "resetPassword"; 

    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     * @param  mapping      the ActionMapping used to select this instance.
     * @param  form         the ActionForm containing the data.
     * @param  request      the HTTP request we are processing.
     * @param  response     the HTTP response we are creating.
     * @return              an ActionForward describing the component that should receive control.
     */
    public ActionForward performAction(ActionMapping mapping, ActionForm form,
            						HttpServletRequest request, HttpServletResponse response) {

        //If there isn't a currently logged on user then go to the login page
        if (!new SessionTool(request).checkSession()) {
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_LOGON);
        }
        
    	ActionForward returnValue = null;
        UserProfileForm theForm = (UserProfileForm)form;
        
        //intentionally do not set any active main or sub tabs.  
        //Because this functionality is outside the scope of any of our tabs, we 
        //don't want any of them to be selected.
        Utility.getSessionDataUtil(request).setSelectedMainTab(Constants.EMPTY);
        Utility.getSessionDataUtil(request).setSelectedSubTab(Constants.EMPTY);
        
    	String operation = theForm.getOperation();
    	
    	if (!Utility.isSet(operation)) {
    		operation = Constants.PARAMETER_OPERATION_VALUE_INIT;
    	}

        if (Constants.PARAMETER_OPERATION_VALUE_INIT.equalsIgnoreCase(operation)) {
        	returnValue = handleInitializationRequest(request, response, theForm, mapping);
        }else if (Constants.PARAMETER_OPERATION_VALUE_USER_PROFILE.equalsIgnoreCase(operation)) {
        	returnValue = getUserProfile(request, response, theForm, mapping);
        	
        }else if (Constants.PARAMETER_OPERATION_VALUE_UPDATE_USER_PROFILE.equalsIgnoreCase(operation)) {      	
        	returnValue = updateUserProfile(request, response, theForm, mapping);
        	
        }else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_CHANGE_PASSWORD.equalsIgnoreCase(operation)) {
        	returnValue = mapping.findForward(MAPPING_CHANGE_PASSWORD); 
        	
        }else if (Constants.PARAMETER_OPERATION_VALUE_UPDATE_PASSWORD.equalsIgnoreCase(operation)) {      	
        	returnValue = updatePassword(request, response, theForm, mapping);
        	
        }else if (Constants.PARAMETER_OPERATION_VALUE_CANCEL_PASSWORD.equalsIgnoreCase(operation)) {      	
        	request.setAttribute(Constants.PARAMETER_CONFIRMATION, ClwI18nUtil.getMessage(request,"shop.userProfile.noChanges", null));
        	returnValue = mapping.findForward(MAPPING_PROFILE); 
        	
        }else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_RESET_PASSWORD.equalsIgnoreCase(operation)) {
        	Object errors = Utility.getSessionDataUtil(request).getErrors();
            if (errors != null && errors instanceof ActionErrors) {
          	  saveErrors(request, (ActionErrors)errors);
          	  //now that the errors are processed, blank them out
          	  Utility.getSessionDataUtil(request).setErrors(null);
            }
        	returnValue = mapping.findForward(MAPPING_RESET_PASSWORD); 
        	
        }else if (Constants.PARAMETER_OPERATION_VALUE_RESET_PASSWORD.equalsIgnoreCase(operation)) {      	
        	returnValue = resetPassword(request, response, theForm, mapping);
        	
        }else {
        	returnValue = handleUnknownOperation(request, response, theForm, mapping);
        }
        
    	return returnValue;    
    }
    
    /*
     * Private method to determine what action forward should be returned after an initial user profile request.
     */
    private ActionForward handleInitializationRequest(HttpServletRequest request, HttpServletResponse response, 
    		UserProfileForm form, ActionMapping mapping) {
    	
    	//TODO - implement this method.
    	return mapping.findForward(MAPPING_PROFILE);    
    }
    
    private ActionForward getUserProfile(HttpServletRequest pRequest, HttpServletResponse pResponse, 
    		UserProfileForm pForm, ActionMapping pMapping){
    	
    	ActionErrors errors = new ActionErrors();
    	String uidStr = (String)pRequest.getSession().getAttribute(Constants.USER_ID);
        if ( uidStr == null ) {
            uidStr = "0";
        }
        int uId = Integer.parseInt(uidStr);
        
        errors = UserProfileLogic.getUserDetailById(pRequest, uId, pForm);
        
        if(errors!=null && errors.size() > 0){
        	saveErrors(pRequest,errors);
        	return pMapping.findForward(MAPPING_PROFILE); 
        }
        
        return pMapping.findForward(MAPPING_PROFILE); 
    }
    
    private ActionForward updateUserProfile(HttpServletRequest pRequest, HttpServletResponse pResponse, 
    		UserProfileForm pForm, ActionMapping pMapping){
    	
        ActionErrors errors = UserProfileLogic.updateUserDetail(pRequest, pForm);
      
        if (errors!=null && errors.size() > 0) {
            saveErrors(pRequest, errors);
            return pMapping.findForward(MAPPING_PROFILE);
        }
        ActionMessages messages = new ActionMessages();
    	String message = ClwI18nUtil.getMessage(pRequest,"shop.userProfile.profileUpdated", null);
    	messages.add("message", new ActionMessage("message.simpleMessage", message));
    	saveMessages(pRequest, messages);
        
        return pMapping.findForward(MAPPING_PROFILE); 
    }
    
    private ActionForward updatePassword(HttpServletRequest pRequest, HttpServletResponse pResponse, 
    		UserProfileForm pForm, ActionMapping pMapping){
    	
    	ActionErrors errors = UserProfileLogic.updatePassword(pRequest, pForm);
        
        if (errors.size() > 0) {
            saveErrors(pRequest, errors);
            return pMapping.findForward(MAPPING_CHANGE_PASSWORD); 
        }

        ActionMessages messages = new ActionMessages();
    	String message = ClwI18nUtil.getMessage(pRequest,"shop.userProfile.passwordUpdated", null);
    	messages.add("message", new ActionMessage("message.simpleMessage", message));
    	saveMessages(pRequest, messages);
    	return pMapping.findForward(MAPPING_CHANGE_PASSWORD); 
    }
    
    private ActionForward resetPassword(HttpServletRequest pRequest, HttpServletResponse pResponse, 
    		UserProfileForm pForm, ActionMapping pMapping){
    	ActionErrors errors = UserProfileLogic.updatePassword(pRequest, pForm);
        
        if (errors.size() > 0) {
            saveErrors(pRequest, errors);
            return pMapping.findForward(MAPPING_RESET_PASSWORD); 
        }

        ActionMessages messages = new ActionMessages();
    	String message = ClwI18nUtil.getMessage(pRequest,"shop.userProfile.passwordHasBeenReset", null);
    	messages.add("message", new ActionMessage("message.simpleMessage", message));
    	saveMessages(pRequest, messages);
    	return pMapping.findForward(Constants.GLOBAL_FORWARD_ESW_LANDING_PAGE);
    }    
}

