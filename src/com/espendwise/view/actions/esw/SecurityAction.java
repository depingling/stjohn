/**
 * Title: SecurityAction 
 * Description: This is the Struts Action class handling the ESW log on functionality.
 */

package com.espendwise.view.actions.esw;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.StoreMessageView;
import com.cleanwise.service.api.value.StoreMessageViewVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.apps.MessageService;
import com.cleanwise.service.crypto.BASE64Encoder;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.LogOnLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.SecurityForm;
import com.espendwise.view.logic.esw.SecurityLogic;

/**
 * Implementation of <code>Action</code> that handles log on functionality.
 */
public class SecurityAction extends EswAction {
    private static final Logger log = Logger.getLogger(SecurityAction.class);
    
    //constants to hold the various action mappings that can be returned by this action class.
    private static final String MAPPING_TRADITIONAL_UI = "traditionalUI";
    private static final String MAPPING_NEW_UI = "newUI";
    private static final String MAPPING_EXTERNAL_SYSTEM_FAILURE = "externalSystemFailure";
    private static final String MAPPING_PASSWORD_SENT = "passwordSent";
    private static final String MAPPING_USER_SEARCH = "userSearch";

    /* (non-Javadoc)
     * Enforce the usage of SSL for log on activity.
     * @see com.cleanwise.view.actions.ActionSuper#isRequiresConfidentialConnection()
     */
    protected boolean isRequiresConfidentialConnection(){
        return true;
    }

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

    	ActionForward returnValue = null;
        SecurityForm theForm = (SecurityForm)form;
        
    	//Determine the operation to perform.
        //STJ-4773 - if no operation has been specified but an access token has been specified, 
        //set the operation to be login
        String extAuth = request.getParameter("extAuth");
    	String operation = theForm.getOperation();
    	if (!Utility.isSet(operation) && Utility.isSet(theForm.getAccessToken())) {
    		operation = Constants.PARAMETER_OPERATION_VALUE_LOGIN;
    	}
    	
    	if(Utility.isTrue(extAuth)){
    		operation = Constants.PARAMETER_OPERATION_VALUE_SHOW_CONTENT;
    	}
    	
    	if (!Utility.isSet(operation)) {
        	//if a user name cookie was saved, populate the form with that value before
        	//presenting the log on page.  Also, since the user chose to be remembered the
        	//last time they logged in make the assumption that they wish to do so again
        	//and check the "Remember Me" check box
        	String username = getCookie(request, Constants.COOKIE_USERNAME);
        	if (Utility.isSet(username)) {
        		theForm.setUsername(username);
        		theForm.setRememberUserName(true);
        	}
            //if there are any errors in the session (which will be the case if the user tried to log into
            //the new UI but had not been configured for any functionality - see 
            //ModuleIntegrationAction.handleDetermineLandingPage), retrieve them and save them as errors in the 
            //request.  This is something of a hack, but these errors needed to be persisted across a redirect.
            Object errors = Utility.getSessionDataUtil(request).getErrors();
            if (errors != null && errors instanceof ActionErrors) {
          	  saveErrors(request, (ActionErrors)errors);
          	  //now that the errors are processed, blank them out
          	  Utility.getSessionDataUtil(request).setErrors(null);
            }
            returnValue = (new ActionForward(mapping.getInput()));
    	}
    	else if (Constants.PARAMETER_OPERATION_VALUE_SEND_PASSWORD.equalsIgnoreCase(operation)) {
	       	returnValue = handleSendPasswordRequest(request, response, theForm, mapping);
	        }
	    else if (Constants.PARAMETER_OPERATION_VALUE_LOGOUT.equalsIgnoreCase(operation)) {
	       	returnValue = handleLogoutRequest(request, response, mapping,false);
	        }
	    else if (Constants.PARAMETER_OPERATION_VALUE_LOGIN.equalsIgnoreCase(operation)) {
	       	returnValue = handleLoginRequest(request, response, theForm, mapping);
	        }
	    else if (Constants.PARAMETER_OPERATION_VALUE_PROXY_LOGIN.equalsIgnoreCase(operation)) {
	       	returnValue = handleProxyLoginRequest(request, response, theForm, mapping);
	        }
	    else if (Constants.PARAMETER_OPERATION_VALUE_USE_NEW_UI.equalsIgnoreCase(operation)) {
	       	returnValue = handleUseNewUIRequest(request, response, theForm, mapping);
	        }
	    else if (Constants.PARAMETER_OPERATION_VALUE_USE_CLASSIC_UI.equalsIgnoreCase(operation)) {
	       	returnValue = handleUseClassicUIRequest(request, response, theForm, mapping);
	        }
	    else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_CONTENT.equalsIgnoreCase(operation)) {
	       	returnValue = handleContentManagementRequest(request, response, theForm, mapping);
	        }
	    else if (Constants.PARAMETER_OPERATION_VALUE_END_SHOPPING.equalsIgnoreCase(operation)) {
	    	returnValue=mapping.findForward("customerSystemPostRedirect");
	        }
	    else {
	    	returnValue = handleUnknownOperation(request, response, theForm, mapping);
	    }
        return returnValue;
    }
    
    /*
     * Private method to determine what action forward should be returned after a log in request.
     */
    private ActionForward handleLoginRequest(HttpServletRequest request, HttpServletResponse response, 
    		SecurityForm form, ActionMapping mapping) {
    	
    	//save the origin URL parameter
    	String extAuth = request.getParameter("extAuth");
    	
    	String originUrl = form.getOriginURL();
    	String originUrlEncoded;
    	if (!Utility.isSet(originUrl)) {
    		originUrlEncoded = getCookie(request,Constants.ORIGIN_URL);
            if (Utility.isSet(originUrlEncoded)) {
            	try{
            		originUrl = java.net.URLDecoder.decode(originUrlEncoded, Constants.UTF_8);
    	    	}
            	catch (java.io.UnsupportedEncodingException e) {
    	     		originUrl = originUrlEncoded;
    	     		log.error("Caught exception trying to encode cookie value: "
    	     				+ originUrlEncoded + ", proceeding with it unencoded.");
            	}
            }
    	}
    	else {
    		try{
    			originUrlEncoded = java.net.URLEncoder.encode(originUrl, Constants.UTF_8);
    	    }
    		catch (java.io.UnsupportedEncodingException e) {
    			originUrlEncoded = originUrl;
    	    	log.error("Caught exception trying to encode cookie value: "
    	    			+ originUrl + ", proceeding with it unencoded.");
    	    }
    	}
    	if (Utility.isSet(originUrlEncoded)) {
    		SessionTool.setCookie(response, request, Constants.ORIGIN_URL,
    				originUrlEncoded, request.getContextPath());
    	}
    	
    	//Process this user sign-on.  If there were any errors return to the input page.
        ActionErrors ae = SecurityLogic.performLogOn(response, request, form);
        if (ae.size() > 0) {
        	//STJ-4326 - if there is a user already logged in, log them out (which will return them to
        	//the login page)
        	if ((new SessionTool(request)).checkSession()) {
        		return handleLogoutRequest(request, response, mapping,false);
        	}
            saveErrors(request, ae);
            return (new ActionForward(mapping.getInput()));
        }

        // handle the case where no errors occurred, but the user is not logged in yet.
        if (!(new SessionTool(request)).checkSession()) {
        	SecurityLogic.initAnonymousUser(request, response);
            if (Utility.isTrue((String)request.getSession(false).getAttribute(Constants.EXTERNAL_LOGON))){
                return mapping.findForward(MAPPING_EXTERNAL_SYSTEM_FAILURE);
            }
            if (isRedirectedOrigin(originUrl,request, response)) {
        		return null;
        	}
            else {
        		return (new ActionForward(mapping.getInput()));
        	}
        }else{
        	if(Utility.isTrue(extAuth)){
        		String externalUrl = request.getParameter("referer");
	        	String userName = ShopTool.getCustomContentUser(request);
	            
	            if (Utility.isSet(userName)) {
	            	
	        		try {
	        			String encryptedPasswd = URLEncoder.encode((encryptText(request,System.getProperty("cms.password"))),"UTF-8");

	            		Date currDate = new Date();
	            		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	            		String encryptedToken = URLEncoder.encode((encryptText(request, sdf.format(currDate))),"UTF-8");
	            		
	        			String url = response.encodeRedirectURL(externalUrl 
	        					+ "index.php?user=" + userName 
	        					+ "&passw=" + encryptedPasswd
	        					+ "&token=" + encryptedToken);
	        			response.sendRedirect(url);
	        			return null;
	        		} catch (Exception e) {
	        			e.printStackTrace();
	        		}
	            } 
        	}
        }
        
        ActionForward returnValue = determineDestination(request, response, originUrl, mapping, form);
        
        //if we are remaining in the new UI, perform some additional processing
        if (returnValue != null && returnValue.getName() != null &&
        		(Constants.GLOBAL_FORWARD_ESW_LANDING_PAGE.equals(returnValue.getName()) ||
        		 Constants.GLOBAL_FORWARD_ESW_DASHBOARD.equals(returnValue.getName()) ||
        		 Constants.GLOBAL_FORWARD_ESW_SHOPPING.equals(returnValue.getName()))) {
	        //update/create the cookie to handle the remember user name functionality
	    	Cookie cookie = new Cookie(Constants.COOKIE_USERNAME, null);
	    	//if the user chose to be remembered, do that
	        if (form.isRememberUserName()) {
	        	cookie.setValue(form.getUsername());
	        	//set the cookie to expire in one year
	        	cookie.setMaxAge(Constants.COOKIE_AGE_ONE_YEAR);
	        }
	        //otherwise remove any cookie containing their user name
	        else {
	        	cookie.setValue("");
	        	cookie.setMaxAge(0);
	        }
	        response.addCookie(cookie);
	        
	        //determine location information for the logged in user (i.e. the number of locations to
	        //which they have been configured, the selection of a default location if one has been
	        //specified, etc). If there were any errors return to the input page.
	        ae = SecurityLogic.initializeUserLocationInformation(request);
	        if (ae.size() > 0) {
	            saveErrors(request, ae);
	            //STJ-5913 - log the user out before returning them to the input page
	        	if ((new SessionTool(request)).checkSession()) {
	        		return handleLogoutRequest(request, response, mapping,false);
	        	}
	        }
	        
	        //retrieve any forced read messages for the user.
	        SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
    		try {
     			sessionDataUtil.setInterstitialMessages(MessageService.getMessagesForUser(ShopTool.getCurrentUser(request).getUserId(), true));
     			// check if need inform user password expiration
    	        StoreMessageView passwordExpiryMessage = getPasswordExpiryMessage(request);
    	        if (passwordExpiryMessage != null){
    	        	sessionDataUtil.getInterstitialMessages().add(0, passwordExpiryMessage);
    	        }
     			sessionDataUtil.setInterstitialMessageIndex(new Integer(0));
    			
    		}
    		catch (Exception e) {
    			log.error("Unexpected exception: ", e);
                String errorMess = ClwI18nUtil.getMessage(request, "error.unExpectedError", null);
                ActionErrors errors = new ActionErrors();
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
                saveErrors(request, errors);
            	return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR); 
    		}
        }
        return returnValue;
    }
    
    private StoreMessageView getPasswordExpiryMessage(HttpServletRequest request) throws Exception {
    	UserData user = ShopTool.getCurrentUser(request).getUser();
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);        
    	long expireInDays = factory.getUserAPI().getPasswordExpiryInDays(user.getUserId(), user.getUserTypeCd());	
		if (expireInDays > 0){
			StoreMessageView smView = StoreMessageView.createValue();
			smView.setStoreMessageId(-1);
			String messageKey = (expireInDays == 1) ? "login.message.password.expire.in.one.day" : "login.message.password.expire.in.days";
			smView.setMessageBody("<p align='center'><font color='red'>" + ClwI18nUtil.getMessage(request,messageKey,new Object[]{new Long(expireInDays)}) + "</font><br/></p>");
			smView.setMessageType(RefCodeNames.MESSAGE_TYPE_CD.FORCE_READ);
			return smView;
		}
		
		return null;
	}

	/*
     * Private method to determine what action forward should be returned after a proxy log 
     * in request.
     */
    private ActionForward handleProxyLoginRequest(HttpServletRequest request, HttpServletResponse response, 
    		SecurityForm form, ActionMapping mapping) {
    	
        //If there isn't a currently logged on user then go to the login page
        if (!new SessionTool(request).checkSession()) {
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_LOGON);
        }
        
    	//Process this simulated log in.  If there were any errors return to the user search page.
        ActionErrors errors = SecurityLogic.performProxyLogin(response, request, form);
        if (errors.size() > 0) {
        	//because we need to issue a redirect to return the user to the user search page (if 
        	//we don't then the links are all messed up), save the errors in the session (not the 
        	//request) so they will be available after the redirect
        	Utility.getSessionDataUtil(request).setErrors(errors);
        	return mapping.findForward(MAPPING_USER_SEARCH);
        }
        
        //make sure the proxy user is set up to use the new UI (at both the store and user level)
        //get the store designation
        ActionForward returnValue = determineDestination(request, response, StringUtils.EMPTY, mapping, form);
        //get the user designation
        boolean userHasSpecifiedClassicUI = false;
    	CleanwiseUser user = ShopTool.getCurrentUser(request);
        boolean isAuthorizedForFunction = user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.NEW_UI_ACCESS);
    	if (isAuthorizedForFunction) {
	        String userUIPreference = null;
	        try {
		        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);        
		    	userUIPreference = factory.getPropertyServiceAPI().getUserProperty(user.getUserId(), 
		    			RefCodeNames.PROPERTY_TYPE_CD.USER_UI_PREFERENCE);
	        }
	        catch (DataNotFoundException dnfe) {
	        	//nothing to do here - no user ui preference has been set
	        }
	        catch (Exception e) {
		  		log.error("Caught exception trying to determine user UI preference.");
	        }
	        userHasSpecifiedClassicUI = (Constants.PORTAL_CLASSIC.equals(userUIPreference));
	    }
        
        //if the proxy user is not a new UI user, return an error message.
        if (returnValue == null || 
        		!Constants.GLOBAL_FORWARD_ESW_LANDING_PAGE.equals(returnValue.getName()) ||
        		userHasSpecifiedClassicUI) {
            errors = new ActionErrors();
        	String errorMess = ClwI18nUtil.getMessage(request, "login.errors.proxyLoginUserNotANewUIUser");
        	errors.add("error", new ActionError("error.simpleError", errorMess));
            errors.add(restoreOriginalUser(ShopTool.getCurrentUser(request).getOriginalUser(),
            		request, response, form));
        	//because we need to issue a redirect to return the user to the user search page (if 
        	//we don't then the links are all messed up), save the errors in the session (not the 
        	//request) so they will be available after the redirect
        	Utility.getSessionDataUtil(request).setErrors(errors);
        	returnValue = mapping.findForward(MAPPING_USER_SEARCH);
        }
        else {
	        errors = SecurityLogic.initializeUserLocationInformation(request);
	        if (errors.size() > 0) {
	            errors.add(restoreOriginalUser(ShopTool.getCurrentUser(request).getOriginalUser(),
	            		request, response, form));
	        	//because we need to issue a redirect to return the user to the user search page (if 
	        	//we don't then the links are all messed up), save the errors in the session (not the 
	        	//request) so they will be available after the redirect
	        	Utility.getSessionDataUtil(request).setErrors(errors);
	        	returnValue = mapping.findForward(MAPPING_USER_SEARCH);
	        }
	        //STJ-4989 - in the case where an admin user is logged in as an MSB, we do not want to display the interstitial
	        //messages so give them non-null values that will not cause them to be displayed
	        Utility.getSessionDataUtil(request).setInterstitialMessageIndex(new Integer(0));
	        Utility.getSessionDataUtil(request).setInterstitialMessages(new StoreMessageViewVector());
        }
        
        return returnValue;
    }
    
    private ActionErrors restoreOriginalUser(CleanwiseUser originalUser, HttpServletRequest request, 
    		HttpServletResponse response, SecurityForm form) {
    	form.setUsername(originalUser.getUser().getUserName());
    	form.setPassword(originalUser.getUser().getPassword());
    	form.setPasswordHashed(true);
    	ActionErrors returnValue = SecurityLogic.processLogOn(response, request, form);
    	try {
    		LogOnLogic.switchUserStore(originalUser.getUserStore().getStoreId(),request);
    	}
    	catch (Exception e) {
        	String errorMess = ClwI18nUtil.getMessage(request, "login.errors.proxyLoginUnableToRestoreStore");
        	returnValue.add("error", new ActionError("error.simpleError", errorMess));
    		
    	}
    	return returnValue;
    }
    
    /*
     * Private method to determine what action forward should be returned after a successful log on.
     */
    private ActionForward determineDestination(HttpServletRequest request, HttpServletResponse response, 
    		String originUrl, ActionMapping mapping, SecurityForm form) {
        
        //determine where to send the user
        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        String userType = appUser.getUser().getUserTypeCd();

        if (Utility.isSet(userType)) {
        	//if the UI is not set to ESW user will be redirected to Login Page for mobile
        	String mobileClient = (String)request.getSession(false).getAttribute(Constants.MOBILE_CLIENT);
        	if (Utility.isSet(mobileClient) && mobileClient.equals(Constants.TRUE)) {
        		if (RefCodeNames.USER_TYPE_CD.MSB.equals(userType)) {
        			String continuationString = (String) request.getSession().getAttribute(Constants.CONTINUATION_STRING);
                    if (Utility.isSet(continuationString)) {
                    	ActionForward af = new ActionForward(continuationString, true);
                    	return af;
                    }
                    return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_DASHBOARD);
            	}
        		else {
        			//STJ-4917
        			ActionErrors errors = new ActionErrors();
        			String errorMess = ClwI18nUtil.getMessage(request, "login.errors.mobileLoginUserNotAMSB");
        			errors.add("error", new ActionError("error.simpleError", errorMess));
        			saveErrors(request, errors);
        			return (new ActionForward(mapping.getInput()));
        		}
        	}
        	//if the form has a specified destination, use it
        	if (Utility.isSet(form.getDestination())) {
        		//first see if the destination refers to a defined forward
        		ActionForward forward = mapping.findForward(form.getDestination());
        		//if not, create a dynamic forward
        		if (forward == null) {
        			forward = new ActionForward(form.getDestination());
        		}
        		return forward;
        	}
        	
        	//determine the user interface the user should be using
        	String userInterface = ShopTool.getUserInterface(request);
        	
        	if (!Constants.PORTAL_ESW.equals(userInterface)) {
        		return mapping.findForward(MAPPING_TRADITIONAL_UI);
        	}
        	
            String continuationString = (String) request.getSession().getAttribute(Constants.CONTINUATION_STRING);
            if (Utility.isSet(continuationString)) {
                ActionForward af = new ActionForward(continuationString, true);
                return af;
            }
            else {
                return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_LANDING_PAGE);
            }
        }
        else {
        	if (isRedirectedOrigin(originUrl,request, response)) {
        		return null;
        	}
        	else {
	            ActionErrors errors = new ActionErrors();
                String errorMessage = ClwI18nUtil.getMessage(request, "login.errors.wrongNamePassword", null);
                errors.add("Error", new ActionError("error.genericError", errorMessage));
	            saveErrors(request, errors);
	            return (new ActionForward(mapping.getInput()));
        	}
        }
    }
    
    /*
     * Private method to determine what action forward should be returned after a forgotten password request.
     */
    private ActionForward handleSendPasswordRequest(HttpServletRequest request, HttpServletResponse response, 
    		SecurityForm form, ActionMapping mapping) {
        
        //a username value is required
        String userName = form.getUsername();
        if (!Utility.isSet(userName)) {
            String[] args = new String[1];
            String userNameLabel = ClwI18nUtil.getMessage(request, "login.label.username", null);
            //trim off any ":" on the label
            if (Utility.isSet(userNameLabel) && userNameLabel.endsWith(":")) {
            	userNameLabel = userNameLabel.substring(0, userNameLabel.lastIndexOf(":"));
            }
        	args[0] = userNameLabel;
        	String errorMess = ClwI18nUtil.getMessage(request, "variable.required.error", args);
            ActionErrors errors = new ActionErrors();
        	errors.add("error", new ActionError("error.simpleError", errorMess));
            saveErrors(request, errors);
            return (new ActionForward(mapping.getInput()));
        }

        //only perform this action if there is no user logged into the session
        if ((new SessionTool(request)).checkSession()) {
            return (new ActionForward(mapping.getInput()));
        }

        // Generate a new password for the user and send an email.
        LogOnLogic.generateNewPassword(request, getResources(request), userName);
        //ignore errors from above for security reasons.  Do not give feedback to whether user exists or not
        //as it gives hackers the info that they found a valid user
        ActionMessages messages = new ActionMessages();
        String[] args = new String[1];
    	args[0]=Utility.encodeForHTML(userName);
    	String messageMess = ClwI18nUtil.getMessage(request, "login.text.passwordMessageSent", args);
    	messages.add("error", new ActionMessage("error.simpleError", messageMess));
        saveMessages(request, messages);
        return (mapping.findForward(MAPPING_PASSWORD_SENT));
    }
    
    private String encryptText(HttpServletRequest request, String val) throws Exception{

    	String keyString = System.getProperty("cms.sharedkey");
    	
    	byte[] raw = keyString.getBytes();
        SecretKey key = new SecretKeySpec(raw, "AES");
        
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        
        byte[] encodedVal = cipher.doFinal(val.getBytes());
        
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(encodedVal);
        
    }

    private ActionForward handleContentManagementRequest(HttpServletRequest request, HttpServletResponse response, 
    		SecurityForm form, ActionMapping mapping){
    	ActionForward returnValue = null;
    	CleanwiseUser appUser = ShopTool.getCurrentUser(request);
    	
    	if (!(new SessionTool(request)).checkSession()) {
    		returnValue = handleLoginRequest(request, response, form, mapping);
    	}else{
    		//save the origin URL parameter
        	String extAuth = request.getParameter("extAuth");
        	
        	String externalUrl = ShopTool.getCustomContentURL(request);
        	String userName = ShopTool.getCustomContentUser(request);
    	  	    
        	if (Utility.isSet(userName)) {
        		try {
            		String encryptedPasswd = URLEncoder.encode((encryptText(request,System.getProperty("cms.password"))),"UTF-8");

            		Date currDate = new Date();
            		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            		String encryptedToken = URLEncoder.encode((encryptText(request, sdf.format(currDate))),"UTF-8");
            		
        			String url = response.encodeRedirectURL(externalUrl 
        					+ "index.php?user=" + userName 
        					+ "&passw=" + encryptedPasswd
        					+ "&token=" + encryptedToken);
        			response.sendRedirect(url);
        			return null;
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	}else{
        		String errorMess = ClwI18nUtil.getMessage(request, "login.errors.notAuthorizedContentManagement");
        		ActionErrors errors = new ActionErrors();
        		errors.add("error", new ActionError("error.simpleError", errorMess));
        		saveErrors(request, errors);
        		return (new ActionForward(mapping.getInput()));
        	}
        	
    	}
    	return returnValue;
    }
    /*
     * Private method to determine what action forward should be returned after a log out request.
     */
    private ActionForward handleLogoutRequest(HttpServletRequest request, HttpServletResponse response, 
    		ActionMapping mapping, boolean switchingUI) {

	  		ActionForward returnValue = null;
    	//retrieve the current user before invalidating the session, so we can determine if they
    	//were logged in as a proxy user
        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        
        //retrieve any existing errors (either in the request or the session), so we can preserve them
        ActionMessages existingErrors = getErrors(request);
        existingErrors.add((ActionMessages)Utility.getSessionDataUtil(request).getErrors());
        
  	  	String originUrl = getCookie(request,Constants.ORIGIN_URL);
  	
  	  	if (request.isRequestedSessionIdValid() ) {
  	  		request.getSession().invalidate();
  	  	}

  	  	if (Utility.isSet(originUrl) && (!switchingUI)) {
  	  		try{
  	  			originUrl = java.net.URLDecoder.decode(originUrl, Constants.UTF_8);
  	  		}
  	  		catch (UnsupportedEncodingException e) {
  	  			log.error("Caught exception trying to decode cookie value: "
  	  					+ originUrl + ", proceeding with it unencoded.");
  	  		}
  	  		try {
  	  			response.sendRedirect(originUrl);
  	  		}
  	  		catch (IOException e) {
  	  			log.error("Caught exception trying to redirect to: "
  	  					+ originUrl + ", returning to application logoff mapping.");
  	  		}
  	  	}else{
  	  		LogOnLogic.initAnonymousUser(request,response);
  	    
  	    
  	  		//handle logout of a proxy user
  	  		if (appUser.getOriginalUser() != null) {
  	  			ActionErrors errors = restoreOriginalUser(appUser.getOriginalUser(), request, response, new SecurityForm());
  	  			errors.add(existingErrors);
  	  			//because we need to issue a redirect to return the user to the user search page (if 
  	  			//we don't then the links are all messed up), save the errors in the session (not the 
  	  			//request) so they will be available after the redirect
  	  			Utility.getSessionDataUtil(request).setErrors(errors);
  	  			returnValue = mapping.findForward(MAPPING_USER_SEARCH);
  	  		}
  	  		else {
  	  			//because we will be issuing a redirect, save any existing errors in the session (not the request)
  	  			//so they will be available after the redirect
  	  			Utility.getSessionDataUtil(request).setErrors(existingErrors);
  	  			returnValue = mapping.findForward(Constants.GLOBAL_FORWARD_ESW_LOGON);
  	  		}
  	  	}
  	    
  	    
  	  	return returnValue;
    }
    
    /*
     * Private method to determine what action forward should be returned after a use new UI request.
     */
    private ActionForward handleUseNewUIRequest(HttpServletRequest request, HttpServletResponse response, 
    		SecurityForm form, ActionMapping mapping) {
    	
        return handleUIRequest(request, response, form, mapping, Constants.PORTAL_ESW);
    }
    
    /*
     * Private method to determine what action forward should be returned after a use classic UI request.
     */
    private ActionForward handleUseClassicUIRequest(HttpServletRequest request, HttpServletResponse response, 
    		SecurityForm form, ActionMapping mapping) {

        return handleUIRequest(request, response, form, mapping, Constants.PORTAL_CLASSIC);
    }
    
    /*
     * Private method to determine what action forward should be returned after a UI selection request.
     */
    private ActionForward handleUIRequest(HttpServletRequest request, HttpServletResponse response, 
    		SecurityForm form, ActionMapping mapping, String ui) {
    	
        //If there isn't a currently logged on user then go to the login page
        if (!new SessionTool(request).checkSession()) {
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_LOGON);
        }
        
        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
        
        //if the user is not authorized to specify a UI preference, return an error
        boolean isAuthorizedForFunction = appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.NEW_UI_ACCESS);
    	if (!isAuthorizedForFunction) {
        	String errorMess = ClwI18nUtil.getMessage(request, "dashboard.error.notAuthorizedForUISpecification");
            ActionErrors errors = new ActionErrors();
        	errors.add("error", new ActionError("error.simpleError", errorMess));
            saveErrors(request, errors);
            //figure out where to return the user based on the requested UI.  If it is the new UI the
            //user must be in the old UI so return them to the landing page, and if it is the old UI
            //the user must be in the new UI so return them to the dashboard.  These are best guesses,
            //as there is no easy way to know from what screen the user actually invoked this 
            //functionality.  Note that this situation shouldn't occur since the UI only shows the
            //link to invoke this functionality if they are authorized for it, but it needs to be
            //handled
        	if (Constants.PORTAL_ESW.equals(ui)) {
        		return mapping.findForward(MAPPING_TRADITIONAL_UI);
        	}
        	else {
        		return mapping.findForward(MAPPING_NEW_UI);
        	}
    	}
        
        //store the selection of the specified ui so it can be referenced when the user next logs in
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        try {
        	factory.getPropertyServiceAPI().setUserProperty(appUser.getUser().getUserId(),
        		RefCodeNames.PROPERTY_TYPE_CD.USER_UI_PREFERENCE, ui);
        }
        catch (Exception e) {
	  		log.error("Caught exception trying to set user UI preference.");
        }
        
        //because of the differences between the old UI and the new UI in terms of data 
        //initialization and storage at log in, the best way to make sure that all necessary data
        //has been initialized correctly is to silently log the user out and then log them back in.
        //before logging the user out, retrieve any cXML login parameters so we can propagate them
        //when we switch UIs.
        String loginParameters = createLoginParameterString(request);
        handleLogoutRequest(request, response, mapping,true);
        ActionForward returnValue = null;
        if (Constants.PORTAL_CLASSIC.equalsIgnoreCase(ui)) {
    		StringBuilder forward = new StringBuilder(Constants.GLOBAL_FORWARD_LOGON);
    		forward.append(".do?j_username=");
    		forward.append(appUser.getUserName());
    		forward.append("&j_password=");
    		forward.append(appUser.getUser().getPassword());
    		forward.append("&passwordHashed=true");
    		forward.append("&forceRedirect=true");
    		if (Utility.isSet(loginParameters)) {
    			forward.append(loginParameters);
    		}
    		returnValue = new ActionForward(forward.toString());
        }
        else {
    		StringBuilder forward = new StringBuilder(Constants.GLOBAL_FORWARD_ESW_LOGON);
    		forward.append(".do?username=");
    		forward.append(appUser.getUserName());
    		forward.append("&password=");
    		forward.append(appUser.getUser().getPassword());
    		forward.append("&passwordHashed=true");
    		forward.append("&");
    		forward.append(Constants.PARAMETER_OPERATION);
    		forward.append("=");
    		forward.append(Constants.PARAMETER_OPERATION_VALUE_LOGIN);
    		//specify that the user be brought to the dashboard of the shopping tab after logging in, not to 
    		//their overall landing page.  If the user is configured for the Assets, Services, 
    		//and Shopping modules (and based on the current rules their landing page would 
    		//therefore be the Assets module) and navigates to the Shopping tab and clicks the 
    		//"Use Classic UI" link, if they click the "Use New UI" link after doing whatever they needed 
    		//to do in the old UI they should be returned to the Shopping tab, not the Assets tab.  We don't
    		//currently have a way to remember the specific subtab of the Shopping tab that should be selected,
    		//so we go to the dashboard by default.
    		forward.append("&");
    		forward.append(Constants.PARAMETER_DESTINATION);
    		forward.append("=");
    		forward.append(Constants.GLOBAL_FORWARD_ESW_DASHBOARD);
    		if (Utility.isSet(loginParameters)) {
    			forward.append(loginParameters);
    		}
    		returnValue = new ActionForward(forward.toString());
        }
        
        //JEE TODO - investigate using access tokens to implement this functionality instead of the hashed
        //password value.  Make sure the login code doesn't consider this an act of logging in from an
        //external system.  If this change is made and the user logged in via an access token, add code here 
        //to refresh the modDate of that token so it is valid when we log the user back in
        
        return returnValue;
    }
    
    /*
     * Method to create a string containing the various cXML login parameters and their values
     */
    private String createLoginParameterString(HttpServletRequest request) {
    	StringBuilder returnValue = new StringBuilder(50);
    	if (request != null) {
	    	HttpSession session = request.getSession();
	    	if (session != null) {
		    	String changeLocation = (String)session.getAttribute(Constants.CHANGE_LOCATION);
		    	appendParameter(returnValue, "&", Constants.CHANGE_LOCATION, changeLocation);
		    	String defaultLocation = (String)session.getAttribute(Constants.DEFAULT_SITE);
		    	appendParameter(returnValue, "&", Constants.DEFAULT_SITE, defaultLocation);
		    	Boolean logoutEnabled = (Boolean)session.getAttribute(Constants.CW_LOGOUT_ENABLED);
		    	if (logoutEnabled != null) {
		        	appendParameter(returnValue, "&", Constants.CW_LOGOUT_ENABLED, logoutEnabled.toString());
		    	}
		    	String customerSystemId = (String)session.getAttribute(Constants.CUSTOMER_SYSTEM_ID);
		    	appendParameter(returnValue, "&", Constants.CUSTOMER_SYSTEM_ID, customerSystemId);
		        String customerSystemUrl = (String)session.getAttribute(Constants.CUSTOMER_SYSTEM_URL);
		    	appendParameter(returnValue, "&", Constants.CUSTOMER_SYSTEM_URL, customerSystemUrl);
		        String uniqueName = (String)session.getAttribute(Constants.UNIQUE_NAME);
		    	appendParameter(returnValue, "&", Constants.UNIQUE_NAME, uniqueName);
	    	}
    	}
    	return returnValue.toString();
    }
    
    private void appendParameter(StringBuilder url, String appender, String parameterName, String parameterValue) {
    	if (Utility.isSet(parameterValue)) {
    		url.append(appender);
    		url.append(parameterName);
    		url.append("=");
    		url.append(parameterValue);
    	}    	
    }

    /*
     * Handles redirecting to an origin url.  Always assumes a bad log on attempt even if first visit 
     * to page as this is assumed to be posted from another url.
     * Current limitations are that the originating page must be an actual page so as to accept a 
     * query string.
     * @param originUrl the originURL as parsed from the request parameters (query string)
     * @param request the HTTP request we are processing.
     * @param response the response to write the redirect to.
     * @return returns true if redirect is performed, false if regular logic should be used.
     */
    private boolean isRedirectedOrigin(String originUrl, HttpServletRequest request,
    		HttpServletResponse response){
    	if(Utility.isSet(originUrl)){
    		
    		if(originUrl.indexOf("?") <= 0){
    			originUrl = originUrl + "?";
    		}else{
    			originUrl = originUrl + "&";
    		}
    		String mess = java.net.URLEncoder.encode(ClwI18nUtil.getMessage(request, "login.errors.wrongNamePassword", null));
    		originUrl = originUrl + Constants.ORIGIN_URL_MESSAGE + "=" + mess;
    		try{
    			response.sendRedirect(originUrl);
    			return true;
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}
    	return false;
    }
}

