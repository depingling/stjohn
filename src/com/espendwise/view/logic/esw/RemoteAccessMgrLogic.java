package com.espendwise.view.logic.esw;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.LdapItemData;
import com.cleanwise.service.apps.MessageService;
import com.cleanwise.view.forms.SiteMgrSearchForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.LogOnLogic;
import com.cleanwise.view.logic.MsbLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.RemoteWebClient;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.RemoteAccessMgrForm;


public class RemoteAccessMgrLogic {

    private static final Logger log = Logger.getLogger(RemoteAccessMgrLogic.class);

    private static final String REMOTE_ACCESS_MGR_FORM = "esw.RemoteAccessMgrForm";
    private static final String ORDER_ID = "orderId";
    
    private static final String SERVER = "neptune.server.name";
    private static final String SERVER_PORT = "neptune.server.port";
    private static final String CONTEXT = "neptune.context";
    private static final String ACCESS_TOKEN = "neptune.accessToken";
    //private static final String SHOP_ACCESS_TOKEN = "shop.access.token";
    private static final String URI_BACK = "neptune.uri.back";
    private static final String URI_ST_DETAIL = "neptune.uri.st.detail";
    
    private static final String CONTENT_ONLY = "remote.content.only";
    
    private static final String LOGIN_USER_NAME = "remoteLoginUserName";
    private static final String LOGIN_PASSWORD = "remoteLoginUserPassword";
    private static final String LOGIN_ACCESS_TOKEN = "remoteLoginAccessToken";
    public static final String SITE_ID = "siteId";
    private static final String SERVICE_TICKET_NUMBERS = "serviceTicketNumbers";

    public static ActionErrors initAdministration(HttpServletResponse response, HttpServletRequest request, RemoteAccessMgrForm pForm) throws Exception {

        log.info("initAdministration()=> BEGIN");
        ActionErrors errors = new ActionErrors();
        SessionTool sessTool = new SessionTool(request);
        if (!sessTool.checkSession()) {
            log.info("initAdministration()=> check session failed, init new session... ");
            errors = remoteLogon(response, request);
            if (!errors.isEmpty()) {
                log.info("initAdministration()=> init session failed, return errors, errors: " + errors);
                return errors;
            }
        }
        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
    	String userType = appUser.getUser().getUserTypeCd();
        if (!RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(userType) &&
        		!RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType) &&
        		!RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userType) &&
        		!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(userType)) {
            String errorMess = ClwI18nUtil.getMessage(request, "error.notAuthorizedForFunctionality", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            log.info("initAdministration()=> Unauthorized access, return errors... ");
            return errors;
        }
        return init(request,pForm);
    }

    public static ActionErrors initShopping(HttpServletResponse response, HttpServletRequest request, RemoteAccessMgrForm pForm) throws Exception {

        log.info("initShopping()=> BEGIN");

        ActionErrors errors = new ActionErrors();

        SessionTool sessTool = new SessionTool(request);
        if (!sessTool.checkSession()) {
            log.info("initShopping()=> check session failed, init new session... ");
            errors = remoteLogon(response, request);
            if (!errors.isEmpty()) {
                log.info("initShopping()=> init session failed, return errors, errors: " + errors);
                return errors;
            }
        }

        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        if (!appUser.canMakePurchases()) {
            String errorMess = ClwI18nUtil.getMessage(request, "error.notAuthorizedForFunctionality", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            log.info("initShopping()=> Unauthorized access, return errors... ");
            return errors;
        }

        return init(request,pForm);
    }
    
    /*public static ActionErrors initReporting(HttpServletResponse response, HttpServletRequest request, RemoteAccessMgrForm pForm) throws Exception {

        log.info("initReporting()=> BEGIN");

        ActionErrors errors = new ActionErrors();

        SessionTool sessTool = new SessionTool(request);
        if (!sessTool.checkSession()) {
            log.info("initReporting()=> check session failed, init new session... ");
            errors = remoteLogon(response, request);
            if (!errors.isEmpty()) {
                log.info("initReporting()=> init session failed, return errors, errors: " + errors);
                return errors;
            }
        }

        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        if (appUser.isNoReporting()) {
            String errorMess = ClwI18nUtil.getMessage(request, "error.notAuthorizedForFunctionality", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            log.info("initShopping()=> Unauthorized access, return errors... ");
            return errors;
        }

       return init(request,pForm);
        
    }*/

    public static ActionErrors init(HttpServletRequest request, RemoteAccessMgrForm pForm ) throws Exception {
    	ActionErrors errors = new ActionErrors();
    	
    	HttpSession session = request.getSession();
    	int lastSiteId = pForm == null ? 0 : pForm.getShopSiteId();
    	String siteIdStr = request.getParameter(SITE_ID);
    	int siteId = -1;
        //If Site id is set validate the site and select it
        if(Utility.isSet(siteIdStr) && Utility.parseInt(siteIdStr) != -1) {
        	 siteId = ShopTool.getCurrentSiteId(request);
        	 int userSiteId = Utility.parseInt(siteIdStr);
        	 
        	 log.info("init()=> sessionID        :  " + session.getId());
             log.info("init()=> creationTime     :  " + session.getCreationTime());
             log.info("init()=> lastAccessedTime :  " + session.getLastAccessedTime());
             log.info("init()=> lastSiteId       :  " + lastSiteId);
             log.info("init()=> currenSiteId     :  " + siteId);
             log.info("init()=> reqSiteId        :  " + userSiteId);
             
             if (!(userSiteId > 0)) {
                 String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.invalidSite", null);
                 errors.add("error", new ActionError("error.simpleGenericError", errorMess));
                 return errors;
             }

             CleanwiseUser appUser = ShopTool.getCurrentUser(request);
             log.info("init()=> current site: " + siteId + ", prepeare site " + userSiteId + " for customer " + appUser.getUser().getUserId());
             if (lastSiteId != userSiteId) {

                 log.info("init()=> MsbLogic.siteShop...");

                 if (!MsbLogic.siteShop(request, newForm(String.valueOf(userSiteId)))) {
                     log.info("init()=> site is not ready for purchases, return errors");
                     String errorMess = ClwI18nUtil.getMessage(request, "login.errors.siteNotReadyForPurchases", null);
                     errors.add("error", new ActionError("error.simpleGenericError", errorMess));
                     return errors;
                 }

                 log.info("init()=> MsbLogic.init...");
                 errors = MsbLogic.init(request, ((ActionForm) null));
                 if (!errors.isEmpty()) {
                     return errors;
                 }
             }
             
             siteId = ShopTool.getCurrentSiteId(request);
             if (!(siteId > 0)) {
                 String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.invalidSite", null);
                 errors.add("error", new ActionError("error.simpleGenericError", errorMess));
                 return errors;
             }

             log.info("init()=> Now, current site: " + siteId);
        }
        
        //Set session variables.
        setRemoteVariablesIntoSession(request, siteId);
        
        log.info("init()=> END.");

        return errors;
    }
    
    private static ActionForm newForm(String pSiteId) {
        SiteMgrSearchForm siteSearchForm = new SiteMgrSearchForm();
        siteSearchForm.setSiteId(pSiteId);
        return siteSearchForm;
    }

    public static ActionErrors remoteLogon(HttpServletResponse response, HttpServletRequest request) {

        log.info("Remote LogOn ()=> BEGIN");

        ActionErrors errors = new ActionErrors();

        //either a username/password pair or an access token must have been sent
        String userName = Utility.safeTrim(request.getParameter(LOGIN_USER_NAME));
        String password = Utility.safeTrim(request.getParameter(LOGIN_PASSWORD));
        String accessToken = Utility.safeTrim(request.getParameter(LOGIN_ACCESS_TOKEN));
        
        if (!Utility.isSet(accessToken)) {
	        if (!Utility.isSet(userName)) {
	            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.missingUsername", null);
	            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
	        }
	        if (!Utility.isSet(password)) {
	            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.missingPassword", null);
	            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
	        }
        }
        if (!errors.isEmpty()) {
            return errors;
        }

        LdapItemData userInfo = new LdapItemData();
        userInfo.setUserName(userName);
        userInfo.setPassword(password);
        userInfo.setAccessToken(accessToken);
        Integer locationId = null;
        String userSiteId = request.getParameter(SITE_ID);
        if (Utility.isSet(userSiteId)) {
        	try {
        		locationId = Integer.parseInt(userSiteId);
        	}
        	catch (NumberFormatException nfe) {
        		//nothing to do here - if a valid location id is necessary for the user it will be
        		//determined below
        	}
        }
        errors = LogOnLogic.logOnUser(response, request, userInfo, locationId);
        
        //if the user is not an administrator, make sure a location was specified
        CleanwiseUser appUser = (CleanwiseUser)request.getSession().getAttribute(Constants.APP_USER);
        if (appUser != null) {
        	String userType = appUser.getUser().getUserTypeCd();
            if (!RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(userType) &&
            		!RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userType) &&
            		!RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userType) &&
            		!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(userType)) {
                if (!Utility.isSet(userSiteId)) {
                    String errorMess = ClwI18nUtil.getMessage(request, "login.errors.missingSiteId", null);
                    errors.add("error", new ActionError("error.simpleGenericError", errorMess));
                }

                if (Utility.parseInt(userSiteId) == 0) {
                    String errorMess = ClwI18nUtil.getMessage(request, "login.errors.invalidSiteId", null);
                    errors.add("error", new ActionError("error.simpleGenericError", errorMess));
                }
                //initialize New UI defaults 
                if (errors.isEmpty()) {
                	errors = initEswDefaults(request,userSiteId);
                }
            }
      }
        log.info("Remote LogOn ()=> END.");
        return errors;
    }


    public static ActionErrors viewOrder(HttpServletResponse response, HttpServletRequest request, RemoteAccessMgrForm pForm) throws Exception {

        log.info("viewOrder()=> BEGIN");

        ActionErrors errs;

        try {

            errs = RemoteAccessMgrLogic.initShopping(response, request, pForm);
            if (errs.size() > 0) {
                clearSessionAttributes(request.getSession());
                return errs;
            }

        } catch (Exception e) {
            clearSessionAttributes(request.getSession());
            throw e;
        }

        int orderId = Utility.parseInt(request.getParameter(ORDER_ID));
        log.info("viewOrder()=> orderId: " + orderId);

        //Set Order Id into session.
        SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
        sessionDataUtil.setOrderId(String.valueOf(orderId));

        log.info("viewOrder()=> END. ");

        return errs;
    }

    public static ActionErrors closeSession(HttpServletRequest request, RemoteAccessMgrForm pForm) {

        log.info("closeSession()=> BEGIN, pForm: " + pForm);
        HttpSession session = request.getSession();

        ActionErrors errors = new ActionErrors();

        SessionTool sessTool = new SessionTool(request);
        if (sessTool.checkSession()) {
            clearSessionAttributes(session);
            session.invalidate();
        }

        log.info("closeSession()=> END.");

        return errors;

    }

    private static void clearSessionAttributes(HttpSession pSession) {
    	SessionDataUtil sessionData = Utility.getSessionDataUtil(pSession);
    	sessionData.setRemoteAccess(false);
    	sessionData.setRemoteWebClient(null);
    }

    public static ActionErrors keepAliveSession(HttpServletRequest request, RemoteAccessMgrForm pForm) {

        log.info("keepAliveSession()=> BEGIN, SessionID: "+request.getSession().getId());

        ActionErrors errors = new ActionErrors();

        log.info("keepAliveSession()=> END.");

        return errors;

    }

    /**
     * Initialize New UI Defaults
     * @param request - the <code>HttpServletRequest</code>
     * @param siteId - current site Id
     * @return - Action Errors if any
     */
    private static ActionErrors initEswDefaults(HttpServletRequest request,String siteId) {
    	ActionErrors errors = new ActionErrors();
    	
    	request.getSession().setAttribute(Constants.DEFAULT_SITE,siteId);
    	errors = SecurityLogic.initializeUserLocationInformation(request);
    	
    	//retrieve any forced read messages for the user.
        SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		try {
			sessionDataUtil.setRemoteAccess(true);
			DashboardLogic.initEswDefaultValues(request);
			sessionDataUtil.setInterstitialMessages(MessageService.getMessagesForUser(ShopTool.getCurrentUser(request).getUserId(), true));
			sessionDataUtil.setInterstitialMessageIndex(new Integer(0));
		}
		catch (Exception e) {
			log.error("Unexpected exception: ", e);
            String errorMess = ClwI18nUtil.getMessage(request, "error.unExpectedError", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
		}
		
    	return errors;
    }
    
   
    public static void setRemoteVariablesIntoSession(HttpServletRequest request) {
    	int siteId = Utility.parseInt(request.getParameter(SITE_ID));
    	setRemoteVariablesIntoSession(request,siteId);
    }
    
    /*
     * Sets session variables that are needed for remote communication.
     */
    public static void setRemoteVariablesIntoSession(HttpServletRequest request, int siteId ) {
   	   //Initialize Remote Server details
       String server = request.getParameter(SERVER);
       String port = request.getParameter(SERVER_PORT);
       String neptuneCtx = request.getParameter(CONTEXT);
       String backUri = request.getParameter(URI_BACK);
       String serviceTicketDetailUri = request.getParameter(URI_ST_DETAIL);
       String accessToken = Utility.safeTrim(request.getParameter(LOGIN_ACCESS_TOKEN));
       String serviceTicketNumbers = request.getParameter(SERVICE_TICKET_NUMBERS);
       String orderIdStr = request.getParameter(ORDER_ID);

       log.info("setRemoteVariablesIntoSession()=> " + SERVER        + ":  " + server);
       log.info("setRemoteVariablesIntoSession()=> " + SERVER_PORT   + ":  " + port);
       log.info("setRemoteVariablesIntoSession()=> " + ACCESS_TOKEN  + ":  " + accessToken);
       log.info("setRemoteVariablesIntoSession()=> " + CONTEXT       + ":  " + neptuneCtx);
       log.info("setRemoteVariablesIntoSession()=> " + URI_BACK      + ":  " + backUri);
       log.info("setRemoteVariablesIntoSession()=> " + URI_ST_DETAIL + ":  " + serviceTicketDetailUri);
       log.info("setRemoteVariablesIntoSession()=> " + SERVICE_TICKET_NUMBERS + ":  " + serviceTicketNumbers);
       log.info("setRemoteVariablesIntoSession()=> " + ORDER_ID 	 + ":  " + orderIdStr);

       RemoteAccessMgrForm pForm = new RemoteAccessMgrForm();
       pForm.setShopSiteId(siteId);
       pForm.setServiceTicketNumbers(serviceTicketNumbers);
       pForm.setContext(neptuneCtx);
       pForm.setBackUri(backUri);
       pForm.setServiceTicketDetailUri(serviceTicketDetailUri);

       SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
       sessionData.setRemoteAccess(true);
       sessionData.setRemoteWebClient(new RemoteWebClient(server, port == null ? null : Integer.parseInt(port), accessToken));
       sessionData.setServiceTicketNumbers(request.getParameter(SERVICE_TICKET_NUMBERS));
       
       sessionData.setContentOnly(Boolean.valueOf(request.getParameter(CONTENT_ONLY)));
       sessionData.setOrderId(Utility.safeTrim(orderIdStr));
       
       //Set Remote Access form in the session.
       HttpSession session = request.getSession();
       session.setAttribute(REMOTE_ACCESS_MGR_FORM, pForm);
   }
}
