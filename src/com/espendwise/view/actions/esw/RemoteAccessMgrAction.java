package com.espendwise.view.actions.esw;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.RemoteAccessMgrForm;
import com.espendwise.view.logic.esw.RemoteAccessMgrLogic;


public class RemoteAccessMgrAction extends EswAction {

    private static final Logger logger = Logger.getLogger(RemoteAccessMgrAction.class);

    public final static String ACTION_ATTR = "action";

    //actions
    public final static String CHANGE_SITE        = "modifyCurrentLocation";
    public final static String BAD_SESSION        = "badsession";
    public final static String NO_ACTION          = "noaction";
    public final static String ADMINISTRATION     = "administration";
    //public final static String SHOPPING           = "shopping";
    //public final static String VIEW_ORDER         = "viewOrder";
    public final static String CLOSE_SESSION      = "closeSession";
    public final static String KEEP_ALIVE_SESSION = "keepAliveSession";
    //public final static String REPORTING          = "reporting";
    public final static String REMOTE_HEADER_INFO = "remoteHeaderInfo";
    public static final String REMOTE_USER_INTERFACE_INFO = "remoteUserInterfaceInfo";
    public final static String REMOTE_CONFIGURATION_INFO = "remoteConfigurationInfo";
    public final static String STORE_ADMIN          = "storeAdmin";
    public final static String STORE_ADMIN_2          = "admin2.0";

    //forwards
    private static final String ACTION_ERROR_KEY    = "Action Error";
    private static final String INVALID_SESSION_KEY = "Invalid Session";
    private static final String REMOTE_ERROR        = "Remote-Error";

    private static final String REMOTE_SESSION_ID = "remote.session.id";
    
    //Constants for Remote Header info 
    private static final String REMOTE_HEADER_INFO_KEY = "remoteHeaderInfoKey";
    private static final String REMOTE_HEADER_INFO_VALUE = "remoteHeaderInfoValue";
    
    private static final String VIEW_CART_HTML 			= "viewCartHtml", 
    							CORPORATE_ORDER_HTML	= "corporateOrderHtml", 
    							CLASSIC_UI_HTML			= "classicUIHtml", 
    							HEADER_STYLE			= "headerStyle", 
    							FOOTER_STYLE			= "footerStyle",
    							SELECTED_MAIN_TAB		= "selectedMainTab";
    
    private static final String stJohnHeaderChunks[] = {VIEW_CART_HTML, CORPORATE_ORDER_HTML, 
    													CLASSIC_UI_HTML, HEADER_STYLE, 
    													FOOTER_STYLE,SELECTED_MAIN_TAB};
    
    private static final String BUILD_NUMBER 			= "buildNumber", 
    							BUILD_DATE				= "buildDate", 
    							DATABASE_USERNAME		= "databaseUsername", 
    							DATABASE_URL			= "databaseUrl",
    							BRANCH_NUMBER		    = "branchNumber";
    
    private static final String serverConfigurationData[] = {BUILD_NUMBER, BUILD_DATE, 
    														 DATABASE_USERNAME, DATABASE_URL, 
    														 BRANCH_NUMBER};
    
    @Override
	public ActionForward performAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
    	
    	ActionForward returnValue = null;
        
    	String operation =  request.getParameter(ACTION_ATTR);
    	if(!Utility.isSet(operation)) {
        	operation = NO_ACTION;
        }
    	
    	SessionTool st = new SessionTool(request);
        if(!st.checkSession()) {
        	// Log the user into the application if the operation is either shopping or viewOrder
	       	 if (isLogonAction(operation) && isValidRemoteSession(request)) {
	       		 ActionErrors errors = handleNeptuneLogOnRequest(request, response, form, mapping);
	       		 if (!errors.isEmpty()) {
	       			 createActionErrorResponse(response, errors);
	                    return null;
	                }
	       	 }
	       	 //configuration information doesn't require a user to be logged in
	       	 else if (!REMOTE_CONFIGURATION_INFO.equalsIgnoreCase(operation)) {
	       		 operation = "";
	       	 }
        }
        
        logger.info("===(((((((((((((Operation)))))))))))))==="+operation);
        
        if (NO_ACTION.equalsIgnoreCase(operation)) {
        	RemoteAccessMgrLogic.setRemoteVariablesIntoSession(request);
        	
        } else if (ADMINISTRATION.equalsIgnoreCase(operation)) {
        	returnValue = handleAdministrationRequest(request, response, form, mapping);
        	
        } else if(CHANGE_SITE.equalsIgnoreCase(operation)) {
        	handleShoppingRequest(request, response, form, mapping);
        	
        } else if(CLOSE_SESSION.equalsIgnoreCase(operation)) {
        	returnValue = handleCloseSessionRequest(request, response, form, mapping);
        } 
        else if(KEEP_ALIVE_SESSION.equalsIgnoreCase(operation)) {
        	returnValue = handleKeepAliveSessionRequest(request, response, form, mapping);
        } 
        else if(BAD_SESSION.equalsIgnoreCase(operation)) {
        	createSessionErrorResponse(response);
        } 
        else if (REMOTE_HEADER_INFO.equalsIgnoreCase(operation)) {
        	buildHeaderInfo(request,response);
        }
        else if (REMOTE_USER_INTERFACE_INFO.equalsIgnoreCase(operation)) {
        	buildUserInterfaceInfo(request,response);
        }
        else if (REMOTE_CONFIGURATION_INFO.equalsIgnoreCase(operation)) {
        	buildConfigurationInfo(request,response);
        }

        return returnValue;
	}

	/*
     * Private method to log on the user into the application.
     */
	private ActionErrors handleNeptuneLogOnRequest(HttpServletRequest pRequest,
			HttpServletResponse pResponse, ActionForm pForm, ActionMapping pMapping) {
    	
    	ActionErrors errors = RemoteAccessMgrLogic.remoteLogon(pResponse, pRequest);
    	String ui = Utility.strNN(ShopTool.getUserInterface(pRequest));
    	if(Constants.PORTAL_ESW.equals(ui)) {
    		String siteIdStr = pRequest.getParameter(RemoteAccessMgrLogic.SITE_ID);
        	if(errors!=null && errors.isEmpty() && Utility.parseInt(siteIdStr)>0) {
        		handleShoppingRequest(pRequest,pResponse,pForm,pMapping);
        	}
    	}
		
		return errors;
	}
	
	/*
     * Private method to determine what action forward should be returned after shopping Request.
     */
	private void handleShoppingRequest(HttpServletRequest pRequest,
			HttpServletResponse pResponse, ActionForm pForm, ActionMapping pMapping) {
		try {
			ActionErrors errors = RemoteAccessMgrLogic.initShopping(pResponse, pRequest, (RemoteAccessMgrForm)pForm);
			if (errors!=null && errors.size() > 0) {
	            createActionErrorResponse(pResponse, errors);
	        } else {
	        	//Enable logout link in StJoh Old UI when it is accessed through Neptune.
        		pRequest.getSession(false).setAttribute(Constants.CW_LOGOUT_ENABLED, Boolean.valueOf(Constants.TRUE));
	        }
			
			/*else {
	        	String returnValue = Utility.strNN(ShopTool.getUserInterface(pRequest));
	        	//If User is configured to New UI
	        	if(returnValue.equals(Constants.PORTAL_ESW)) {
	        		actionForward = pMapping.findForward(SHOPPING);
	        	} else {
	        		//Enable logout link in StJoh Old UI when it is accessed through Neptune.
	        		pRequest.getSession(false).setAttribute(Constants.CW_LOGOUT_ENABLED, Constants.TRUE);
	        		actionForward = pMapping.findForward(SHOPPING_OLD_UI);
	        	}
	        	
	        }*/
		} catch (Exception e) {
			logger.error("Error: An error has occured while accesshing shopping functionality "+e.getMessage());
		}
	}
	
	/*
     * Private method to determine what action forward should be returned after an administration request.
     */
	private ActionForward handleAdministrationRequest(HttpServletRequest request,
			HttpServletResponse response, ActionForm form, ActionMapping mapping) {
		ActionForward actionForward = null;
		try {
			ActionErrors errors = RemoteAccessMgrLogic.initAdministration(response, request, (RemoteAccessMgrForm) form);
			if (errors != null && errors.size() > 0) {
	            createActionErrorResponse(response, errors);
	        } 
			else {
		        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
		    	String userType = appUser.getUser().getUserTypeCd();
	            if (RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(userType)) {
	                return (mapping.findForward(STORE_ADMIN_2));
	            }
	            else {
	                return (mapping.findForward(STORE_ADMIN));
	            }
	        }
		} 
		catch (Exception e) {
			logger.error("Error: An error has occured while accesshing shopping functionality "+e.getMessage());
			ActionErrors errors = new ActionErrors();
            String errorMess = ClwI18nUtil.getMessage(request, "error.unExpectedError");
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            createActionErrorResponse(response, errors);
		}
		return actionForward;
	}
	
	/*
     * Private method to determine what action forward should be returned after keep alive session Request.
     */
	private ActionForward handleKeepAliveSessionRequest(
			HttpServletRequest pRequest, HttpServletResponse pResponse, ActionForm pForm, ActionMapping pMapping) {
		
		ActionErrors errors = RemoteAccessMgrLogic.keepAliveSession(pRequest, (RemoteAccessMgrForm)pForm);
        if (errors.size() > 0) {
            createActionErrorResponse(pResponse, errors);
        }
        logger.info(" Keep Alive Session Token has been received from Remote Server !!!!");
        
        return null;
	}

	/*
     * Private method to determine what action forward should be returned after close session Request.
     */
	private ActionForward handleCloseSessionRequest(
			HttpServletRequest pRequest, HttpServletResponse pResponse,ActionForm pForm, ActionMapping pMapping) {
		
		ActionErrors errors = RemoteAccessMgrLogic.closeSession(pRequest, (RemoteAccessMgrForm)pForm);
        if (errors.size() > 0) {
            createActionErrorResponse(pResponse, errors);
        }
        logger.info(" Remote Session has been Closed !!!!!!!!!!!!!!!!!! ");
        return null;
	}

	/*
     * Private method to create session action error response.
     */
    private void buildHeaderInfo(HttpServletRequest request,HttpServletResponse response) {

    	Map<String,String> headerChunksMap = prepareHeaderChunksMap(request);
    	
    	//Set StJohn Header Chunks information into the response.
    	response.setStatus(HttpServletResponse.SC_OK);
    	sendJSONResponse(response,headerChunksMap);
    }

	/*
     * Private method to create session action error response.
     */
    private void buildConfigurationInfo(HttpServletRequest request, HttpServletResponse response) {
    	Map<String,String> configurationData = prepareConfigurationData(request);
    	//Set the configuration information into the response.
    	response.setStatus(HttpServletResponse.SC_OK);
    	sendJSONResponse(response, configurationData);
    }
    
    /*
     * Prepares Map with needed data for remote headers.
     */
    private Map<String,String> prepareHeaderChunksMap(HttpServletRequest request) {
    	Map<String,String> infoMap = new HashMap<String,String>(stJohnHeaderChunks.length);
    	if(stJohnHeaderChunks.length>0) {
    		String key,value;
    		for (int ind = 0 ; ind < stJohnHeaderChunks.length; ind ++) {
    			key = stJohnHeaderChunks[ind];
    			value = "";
    			
    			if(key.equals(VIEW_CART_HTML)) {
    				//Get view cart HTML.
    				value = ShopTool.buildViewCartHTML(request);
    				
    			} else if (key.equals(CLASSIC_UI_HTML)) {
    				//Get Classic UI link HTML
    				value = ShopTool.buildClassicUIHTML(request);
    				
    			} else if (key.equals(CORPORATE_ORDER_HTML)) {
    				//Get Corporate Order textHTML
    				value = ShopTool.buildCorporateOrderHTML(request);
    				
    			} else if (key.equals(HEADER_STYLE)) {
    				//TODO: Get the Data for header Style
    				
    			} else if (key.equals(FOOTER_STYLE)) {
    				//TODO: Get the Data for header Style
    				
    			} else if (key.equals(SELECTED_MAIN_TAB)) {
    				value = Utility.getSessionDataUtil(request).getSelectedMainTab();
    			}
    			
    			infoMap.put(key,Utility.encodeForHTML(value));
    		}
    	}
    	return infoMap;
    }
    
    /*
     * Prepares Map with needed data for remote configuration data.
     */
    private Map<String,String> prepareConfigurationData(HttpServletRequest request) {
    	Map<String,String> returnValue = new HashMap<String,String>(serverConfigurationData.length);
    	if (serverConfigurationData.length > 0) {
    		String key,value;
    		for (int ind = 0; ind < serverConfigurationData.length; ind++) {
    			key = serverConfigurationData[ind];
    			value = "";
    			if (key.equals(BUILD_NUMBER)) {
    				value = System.getProperty("build.number.stjohn");
    			} 
    			if (key.equals(BRANCH_NUMBER)) {
    				value = System.getProperty("build.branch.stjohn");
    			}     			
    			else if (key.equals(BUILD_DATE)) {
    				value = System.getProperty("build.date.stjohn");
    			} 
    			else if (key.equals(DATABASE_USERNAME)) {
    				value = System.getProperty("database.username");
    			} 
    			else if (key.equals(DATABASE_URL)) {
    				value = System.getProperty("database.url");
    			}
    			returnValue.put(key,Utility.encodeForHTML(value));
    		}
    	}
    	return returnValue;
    }

	/*
     * Private method to retrieve user interface information.
     */
    private void buildUserInterfaceInfo(HttpServletRequest request,HttpServletResponse response) {
    	response.addHeader(REMOTE_HEADER_INFO_KEY, REMOTE_HEADER_INFO_VALUE);
    	
    	String returnValue = Utility.strNN(ShopTool.getUserInterface(request));
    	
    	//Set the user interface information into the response.
    	response.setStatus(HttpServletResponse.SC_OK);
    	StringBuilder jsonBuilder = new StringBuilder(500);
	    jsonBuilder.append("{\"key\":\"");
	    jsonBuilder.append("userInterface");
	    jsonBuilder.append("\",\"value\":\"");
	    jsonBuilder.append(Utility.escapeStringForJSON(returnValue));
	    jsonBuilder.append("\"}");
        response.setContentType(Constants.CONTENT_TYPE_JSON);
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding(Constants.UTF_8);
        try {
        	logger.info("-------------BEGIN: JSON-USER INTERFACE INFO---------------");
        	logger.info(jsonBuilder.toString());
        	logger.info("-------------END: JSON-USER INTERFACE INFO----------------");
 			response.getWriter().write(jsonBuilder.toString());
 		} catch (IOException e) {
 			logger.error("Error : While writing Remote User Interface Information to HTTP Servlet Response");
 		}
    }
    
	/**
	 * Checks whether Shop session is valid or not.
	 */
	private boolean isValidRemoteSession(HttpServletRequest request) {
    	String shopSessionID = request.getParameter(REMOTE_SESSION_ID);
    	String sessionID = request.getSession().getId();
        boolean invalidSession = !Utility.isSet(shopSessionID) || sessionID.equals(shopSessionID);
        logger.info("isInvalidSession()=> <ShopSessionID: " + shopSessionID + ", SessionID: " + sessionID + "> " + invalidSession);
        return invalidSession;
    }
    
	/**
	 * Checks whether Log On is required or not.
	 */
    private boolean isLogonAction(String operation) {
    	return ADMINISTRATION.equalsIgnoreCase(operation) || NO_ACTION.equalsIgnoreCase(operation);
    }

    /*
     * Private method to create action error response.
     */
    private void createActionErrorResponse(HttpServletResponse response, ActionErrors pErrs) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        createAeResponse(response, pErrs);
    }

    /*
     * Private method to create session action error response.
     */
    private void createSessionErrorResponse(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.addHeader(REMOTE_ERROR, INVALID_SESSION_KEY);
    }

    /**
     * Extracts Action Errors and create Error Response in the form of JSON Object
     * @param response - the <code>HttpServletResponse </code>
     * @param pErrs - <code>ActionErrors </code> object that is going to be extracted.
     * @throws IOException
     */
    private static void createAeResponse(HttpServletResponse response, ActionErrors pErrs) {

        int i = 0;

        response.addHeader(REMOTE_ERROR, ACTION_ERROR_KEY);

        String jsonStr = "[";

        for (Iterator iterProp = pErrs.properties(); iterProp.hasNext();) {

            String errKey = (String) iterProp.next();
            for (Iterator iterAE = pErrs.get(errKey); iterAE.hasNext();) {
                ActionError mess = (ActionError) iterAE.next();
                Object[] values = mess.getValues();
                for (Object value : values) {
                    if (value instanceof String) {
                        if (i > 0) {
                            jsonStr += ",";
                        }
                        jsonStr += "{\"errorType\":\"" + errKey + "\",\"errorValue\":\"" + value + "\"}";
                        i++;
                    }
                }
            }
        }

        jsonStr += "]";

        response.setContentType("json-comment-filtered");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");

        try {
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			logger.error("Error : While writing Action errors to HTTP Servlet Response");
		}

    }
    
    /**
     * Sets the needed info into the response header.
     * @param response - the <code>HttpServletResponse </code>
     * @param infoMap - Map that contains the info about the data that needs to be populated at remote header.
     */
    private static void sendJSONResponse(HttpServletResponse response, Map<String,String> infoMap) {
    	response.addHeader(REMOTE_HEADER_INFO_KEY, REMOTE_HEADER_INFO_VALUE);

    	int pairCount = 0;
    	StringBuilder jsonBuilder = new StringBuilder(500);
    	jsonBuilder.append("[");
         
        if(Utility.isSet(infoMap)) {
			for (Entry<String,String> entry : infoMap.entrySet()) {
			    logger.info(entry.getKey() + " - "+entry.getValue());
			    if(pairCount>0) {
			    	jsonBuilder.append(",");
			    }
			    jsonBuilder.append("{\"key\":\"" + entry.getKey() + "\",\"value\":\"" + entry.getValue() + "\"}");
			    pairCount ++;
			}
        }
        jsonBuilder.append("]");
         
        response.setContentType("json-comment-filtered");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");

        try {
        	logger.info("-------------BEGIN: JSON-HEADER INFO---------------");
        	logger.info(jsonBuilder.toString());
        	logger.info("-------------END: JSON-HEADER INFO----------------");
 			response.getWriter().write(jsonBuilder.toString());
 		} catch (IOException e) {
 			logger.error("Error : While writing Remote Header Information to HTTP Servlet Response");
 		}
    }

}
