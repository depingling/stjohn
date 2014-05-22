/**
 *  Title: LogOnAction Description: This is the Struts Action class mapping the
 *  logon. Purpose: Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     Liang Li
 */

package com.cleanwise.view.actions;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.view.forms.LogOnForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.LogOnLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;

/**
 *  Implementation of <strong>Action</strong> that processes a user logon.
 *
 *@author     dvieira
 *@created    October 16, 2001
 */

public final class LogOnAction extends ActionSuper {
    private static final Logger log = Logger.getLogger(LogOnAction.class);

    protected boolean isRequiresConfidentialConnection(){
        return true;
    }

    private ActionForward redirectToSystemMessagePage(ActionMapping mapping,HttpServletRequest request, String defaultMapping){
        try{
            String systemMessage = null;
	  		CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
	  		if(appUser != null && appUser.getUserStore() != null && appUser.getUserStore().getStoreId() > 0){
	  			PropertyDataVector props = ((APIAccess)request.getSession().getAttribute(Constants.APIACCESS)).getPropertyServiceAPI().getProperties(0, appUser.getUserStore().getStoreId(), RefCodeNames.PROPERTY_TYPE_CD.SYSTEM_MESSAGE);
	  			if(props != null && props.size() > 0){
	  				systemMessage = ((PropertyData) props.get(0)).getValue();
	  			}
	  		}

            if(Utility.isSet(systemMessage)){
                String hasSeenSysMessage = "hasSeenSysMessage";
                if(request.getSession().getAttribute(hasSeenSysMessage)==null){
                    request.getSession().setAttribute(hasSeenSysMessage,"TRUE");
                    request.setAttribute("systemMessage",systemMessage);
                    return (mapping.findForward("systemMessage"));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return mapping.findForward(defaultMapping);
    }


    /**
     *  Process the specified HTTP request, and create the corresponding HTTP
     *  response (or forward to another web component that will create it).
     *  Return an <code>ActionForward</code> instance describing where and how
     *  control should be forwarded, or <code>null</code> if the response has
     *  already been completed.
     *
     *@param  mapping               the ActionMapping used to select this
     *      instance
     *@param  request               the HTTP request we are processing
     *@param  response              the HTTP response we are creating
     *@param  form                  Description of Parameter
     *@return                       Description of the Returned Value
     */
    public ActionForward performSub(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {

    	//save the origin url paramater
    	String originUrl = (String) request.getParameter(Constants.ORIGIN_URL);
    	String originUrlEncoded;
    	if(!Utility.isSet(originUrl)){
    		originUrlEncoded = getCookie(request,Constants.ORIGIN_URL);
                if(Utility.isSet(originUrlEncoded)){
    		  try{
    			originUrl = java.net.URLDecoder.decode(originUrlEncoded,"UTF-8");
    	    	  }catch(java.io.UnsupportedEncodingException e){
    	     		originUrl = originUrlEncoded;
			log.error("Caught exception trying to encode cookie value: "+originUrlEncoded+" proceeding with it unencoded");
    	          }
               }
    	}else{
    		try{
    			originUrlEncoded = java.net.URLEncoder.encode(originUrl,"UTF-8");
    	    }catch(java.io.UnsupportedEncodingException e){
    	    	originUrlEncoded = originUrl;
    	    	log.error("Caught exception trying to encode cookie value: "+originUrl+" proceeding with it unencoded");
    	    }
    	}
    	if(Utility.isSet(originUrlEncoded)){
    		try{
    			SessionTool.setCookie(response, request,Constants.ORIGIN_URL,originUrlEncoded,request.getContextPath());
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}
    	
    	// Process this user sign-on.
        ActionErrors ae;
        ae = LogOnLogic.mainLogOn(response,request, form);

        if (ae.size() > 0) {
            saveErrors(request, ae);
            log.info("Logon destination: " + mapping.getInput());
            return (new ActionForward(mapping.getInput()));
        }

        //this was not an error, but the user was not loged in either
        SessionTool st = new SessionTool(request);
        if ( st.checkSession() == false ) {
          LogOnLogic.initAnonymousUser(request,response);

            if(Utility.isTrue((String) request.getSession(false).getAttribute(Constants.EXTERNAL_LOGON))){
                return mapping.findForward("externalSystem");
            }
            if(isRedirectedOrigin(originUrl,request, response)){
        		return null;
        	}else{
        		boolean storeConfiguredForNewUi = false;
        		//determine the UI for which the store is configured
        		CleanwiseUser user = ShopTool.getCurrentUser(request);
        		if (user != null) {
        			storeConfiguredForNewUi = Constants.PORTAL_ESW.equalsIgnoreCase(Utility.getAlternatePortal(user.getUserStore())) ||
        									  Constants.PORTAL_ESW.equalsIgnoreCase(Utility.getAlternatePortal(user.getUserAccount()));
        		}
        		//STJ-4733 if the store is configured for the new UI and this is a GET request forward to the new UI,
        		//taking care to carry forward any request parameters.
        		if (storeConfiguredForNewUi && "GET".equalsIgnoreCase(request.getMethod())) {
            		StringBuilder forward = new StringBuilder(Constants.GLOBAL_FORWARD_ESW_LOGON);
            		forward.append(".do");
            		Map<String, String[]> parameterMap = request.getParameterMap();
            		if (Utility.isSet(parameterMap)) {
            			boolean firstParam = true;
            			Iterator<String> parameterIterator = parameterMap.keySet().iterator();
            			  while (parameterIterator.hasNext()) {
            				  String parameterName = (String) parameterIterator.next();
            				  String[] parameterValues = (String[]) parameterMap.get(parameterName);
            				  for (int i = 0; i < parameterValues.length; i++) {
            					  if (firstParam) {
            						  forward.append("?");
            						  firstParam = false;
            					  }
            					  else {
            						  forward.append("&");
            					  }
            					  forward.append(parameterName);
            					  forward.append("=");
            					  forward.append(parameterValues[i]);
            				  }
            			  }
            		}
            		ActionForward returnValue = new ActionForward(forward.toString());
            		returnValue.setRedirect(true);
        			return returnValue;
        		}
        		else {
        			return (new ActionForward(mapping.getInput()));
        		}
        	}
        }
        
        ActionForward returnValue = determineDestination(request, response, originUrl, mapping, form);
        LogOnForm logonForm = (LogOnForm)form;
        if (logonForm.isForceRedirect()) {
        	ActionForward redirectedReturnValue = new ActionForward(returnValue);
        	redirectedReturnValue.setRedirect(logonForm.isForceRedirect());
        	returnValue = redirectedReturnValue;
        }
        return returnValue;
    }
    
    private ActionForward determineDestination(HttpServletRequest request, HttpServletResponse response, 
    		String originUrl, ActionMapping mapping, ActionForm form) {
        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        String userType = appUser.getUser().getUserTypeCd();

        if (null != userType) {
        	
            //STJ-4642 - if the user is authorized to specify a UI preference and they have made such
            //a specification, respect that
            String userUIPreference = null;
            boolean isAuthorizedForFunction = appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.NEW_UI_ACCESS);
        	if (isAuthorizedForFunction) {
	            try {
	    	        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);        
	    	    	userUIPreference = factory.getPropertyServiceAPI().getUserProperty(appUser.getUserId(), 
	    	    			RefCodeNames.PROPERTY_TYPE_CD.USER_UI_PREFERENCE);
	            }
	            catch (DataNotFoundException dnfe) {
	            	//nothing to do here - no user ui preference has been set
	            }
	            catch (Exception e) {
	    	  		log.error("Caught exception trying to determine user UI preference.");
	            }
        	}
        	
        	//if the user has specified a preference of the new UI, or if the user is a multi-site buyer and the 
        	//store to which the user belongs is set up to use the new UI and the user hasn't specified a 
        	//preference of the old UI, send the user to the new UI.
        	if (Constants.PORTAL_ESW.equals(userUIPreference) ||
        			(!Constants.PORTAL_CLASSIC.equals(userUIPreference) &&
        					RefCodeNames.USER_TYPE_CD.MSB.equals(userType) &&
        					(Constants.PORTAL_ESW.equals(Utility.getAlternatePortal(appUser.getUserStore())) || 
        					 Constants.PORTAL_ESW.equals(Utility.getAlternatePortal(appUser.getUserAccount())) )		
        			)) {
        		LogOnForm logonForm = (LogOnForm) form;
        		StringBuilder forward = new StringBuilder(Constants.GLOBAL_FORWARD_ESW_LOGON);
        		forward.append(".do?username=");
        		forward.append(logonForm.getJ_username());
        		forward.append("&password=");
        		forward.append(logonForm.getJ_password());
        		forward.append("&");
        		forward.append(Constants.PARAMETER_OPERATION);
        		forward.append("=");
        		forward.append(Constants.PARAMETER_OPERATION_VALUE_LOGIN);
        		//specify that the user be brought to the new UI landing page after logging in.
        		forward.append("&");
        		forward.append(Constants.PARAMETER_DESTINATION);
        		forward.append("=");
        		forward.append(Constants.GLOBAL_FORWARD_ESW_LANDING_PAGE);
        		ActionForward returnValue = new ActionForward(forward.toString());
          	  	return returnValue;
        	}
        	
            if (RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(userType)) {


                if(appUser.isPresentationOnly()){
                  return (mapping.findForward("salesPresentation"));
                }
               if(request.getSession().getAttribute("continuationString") != null){
                  String s = (String) request.getSession().getAttribute("continuationString");
                  ActionForward af = new ActionForward(s, true);

                  return af;
              }
              return redirectToSystemMessagePage(mapping, request,"msbHome");
            }
            else if (RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType)) {
                return (mapping.findForward("storeAdmin"));
            }
            else if (RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userType)) {
                return (mapping.findForward("storeAdmin"));
            }
            else if (RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(userType)) {
                return (mapping.findForward("admin2.0"));
            }else if (RefCodeNames.USER_TYPE_CD.ESTORE_CLIENT.equals(userType)){
                return (mapping.findForward("estoreClientHome"));
            }else if (RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(userType)){
                return redirectToSystemMessagePage(mapping, request,"distributorHome");
            }else if (RefCodeNames.USER_TYPE_CD.SERVICE_PROVIDER.equals(userType)){
                return redirectToSystemMessagePage(mapping, request,"serviceproviderHome");
            }
            else if (RefCodeNames.USER_TYPE_CD.MSB.equals(userType)) {
                if(request.getSession().getAttribute("continuationString") != null){
                       String s = (String) request.getSession().getAttribute("continuationString");
                       ActionForward af = new ActionForward(s, true);

                       return af;
                }
                return redirectToSystemMessagePage(mapping, request,"msbHome");
            }
            else if (RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userType)||
                     RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userType)) {
                return (mapping.findForward("opConsole"));
            }
            else if (RefCodeNames.USER_TYPE_CD.REGISTRATION_USER.equals(userType)) {
                return (mapping.findForward("reg"));
            }
            else if (RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userType)) {
                return (mapping.findForward("reportingHome"));
            }
            else if (RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(userType)) {
                return (mapping.findForward("storeAdmin"));
            }
            else {
                ActionErrors errors = new ActionErrors();
                errors.add("unknownType", new ActionError("error.genericError", "Unknown user type: ", "" + userType, "", ""));
                saveErrors(request, errors);
                return (new ActionForward(mapping.getInput()));
            }
        }
        else {
        	if(isRedirectedOrigin(originUrl,request, response)){
        		return null;
        	}else{
	            ActionErrors errors = new ActionErrors();
	            errors.add("badlogon", new ActionError("logon.badlogon"));
	            saveErrors(request, errors);
	            return (new ActionForward(mapping.getInput()));
        	}
        }    	
    }

    /**
     * Handles redirecting to an orgin url.  Always assums a bad logon attempt even if first visit to page
     * as this is assumed to be posted from anouther url.
     * Current limitations are that the originating page must be an actual page so as to accept a query string.  Otherwise an un
     * @param originUrl the originURL as parsed from the request paramters (query string)
     * @param response the response to write the redirect to
     * @return returns true if redirect is performed, false if regular logic should be used.
     */
    private boolean isRedirectedOrigin(String originUrl,HttpServletRequest request,HttpServletResponse response){
    	if(Utility.isSet(originUrl)){
    		
    		if(originUrl.indexOf("?") <= 0){
    			originUrl = originUrl + "?";
    		}else{
    			originUrl = originUrl + "&";
    		}
    		String mess = ClwI18nUtil.getMessage(request, "logon.badlogon", null);
    		mess= java.net.URLEncoder.encode(mess);
    		originUrl = originUrl+Constants.ORIGIN_URL_MESSAGE + "=" + mess;
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

