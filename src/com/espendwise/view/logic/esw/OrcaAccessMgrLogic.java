package com.espendwise.view.logic.esw;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.LdapItemData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.UserData;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.User;

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


public class OrcaAccessMgrLogic {

    private static final Logger log = Logger.getLogger(OrcaAccessMgrLogic.class);

    private static final String REMOTE_ACCESS_MGR_FORM = "esw.RemoteAccessMgrForm";
    private static final String ORDER_STATUS_ID = "OrderStatus.id";

    public static ActionErrors init(HttpServletRequest request, RemoteAccessMgrForm pForm) {
        log.info("init()=> BEGIN/END.");
        return new ActionErrors();
    }


    public static ActionErrors initShopping(HttpServletResponse response, HttpServletRequest request, RemoteAccessMgrForm pForm) throws Exception {

        log.info("initShopping()=> BEGIN");

        ActionErrors errors = new ActionErrors();

        SessionTool sessTool = new SessionTool(request);
        if (!sessTool.checkSession()) {
            log.info("initShopping()=> check session failed, init new session... ");
            errors = orcaLogon(response, request);
            if (!errors.isEmpty()) {
                log.info("initShopping()=> init session failed, return errors, errors: " + errors);
                return errors;
            }
        }

        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        if (!appUser.canMakePurchases()) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.unauthorizedAccess", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            log.info("initShopping()=> Unauthorized access, return errors... ");
            return errors;
        }

        HttpSession session = request.getSession();

        int lastSiteId = pForm == null ? 0 : pForm.getShopSiteId();
        int siteId = ShopTool.getCurrentSiteId(request);
        int userSiteId = Utility.parseInt(request.getParameter(Constants.USER_SITE_ID));

        log.info("initShopping()=> sessionID        :  " + session.getId());
        log.info("initShopping()=> creationTime     :  " + session.getCreationTime());
        log.info("initShopping()=> lastAccessedTime :  " + session.getLastAccessedTime());
        log.info("initShopping()=> lastSiteId       :  " + lastSiteId);
        log.info("initShopping()=> currenSiteId     :  " + siteId);
        log.info("initShopping()=> reqSiteId        :  " + userSiteId);

        if (!(userSiteId > 0)) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.invalidSite", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return errors;
        }

        log.info("initShopping()=> current site: " + siteId + ", prepeare site " + userSiteId + " for customer " + appUser.getUser().getUserId());
        if (lastSiteId != userSiteId) {

            log.info("initShopping()=> MsbLogic.siteShop...");

            if (!MsbLogic.siteShop(request, newForm(String.valueOf(userSiteId)))) {
                log.info("initShopping()=> site is not ready for purchases, return errors");
                String errorMess = ClwI18nUtil.getMessage(request, "login.errors.siteNotReadyForPurchases", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
                return errors;
            }

            log.info("initShopping()=> MsbLogic.init...");
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

        log.info("initShopping()=> Now, current site: " + siteId);

        String server = request.getParameter(Constants.ORCA_SERVER);
        String port = request.getParameter(Constants.ORCA_SERVER_PORT);
        String accessToken = request.getParameter(Constants.ORCA_ACCESS_TOKEN);
        String orcaCtx = request.getParameter(Constants.ORCA_CONTEXT);
        String backUri = request.getParameter(Constants.ORCA_URI_BACK);
        String serviceTicketDetailUri = request.getParameter(Constants.ORCA_URI_ST_DETAIL);

        log.info("initShopping()=> " + Constants.ORCA_SERVER        + ":  " + server);
        log.info("initShopping()=> " + Constants.ORCA_SERVER_PORT   + ":  " + port);
        log.info("initShopping()=> " + Constants.ORCA_ACCESS_TOKEN  + ":  " + accessToken);
        log.info("initShopping()=> " + Constants.ORCA_CONTEXT       + ":  " + orcaCtx);
        log.info("initShopping()=> " + Constants.ORCA_URI_BACK      + ":  " + backUri);
        log.info("initShopping()=> " + Constants.ORCA_URI_ST_DETAIL + ":  " + serviceTicketDetailUri);

        pForm = new RemoteAccessMgrForm();
        pForm.setShopSiteId(siteId);
        pForm.setServiceTicketNumbers(request.getParameter(Constants.SERVICE_TICKET_NUMBERS));
        pForm.setContext(orcaCtx);
        pForm.setBackUri(backUri);
        pForm.setServiceTicketDetailUri(serviceTicketDetailUri);

        log.info("initShopping()=> serviceTicketNumbers: " + pForm.getServiceTicketNumbers());

        //Neptune-StJohn Integration
        RemoteWebClient remoteWebClient = new RemoteWebClient(server, port == null ? null : Integer.parseInt(port), accessToken);
        SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
        sessionData.setRemoteAccess(true);
        sessionData.setServiceTicketNumbers(request.getParameter(Constants.SERVICE_TICKET_NUMBERS));
        sessionData.setRemoteWebClient(remoteWebClient);
        
        //Set Remote Access form in the session.
        session.setAttribute(REMOTE_ACCESS_MGR_FORM, pForm);
        
        /*session.setAttribute(Constants.ORCA_ACCESS, Boolean.TRUE);
        session.setAttribute(ORCA_ACCESS_MGR_FORM, pForm);
        session.setAttribute(Constants.ORCA_WEB_CLIENT, remoteWebClient);*/

        log.info("initShopping()=> END.");

        return errors;
    }

    private static ActionForm newForm(String pSiteId) {
        SiteMgrSearchForm siteSearchForm = new SiteMgrSearchForm();
        siteSearchForm.setSiteId(pSiteId);
        return siteSearchForm;
    }

    public static ActionErrors orcaLogon(HttpServletResponse response, HttpServletRequest request) {

        log.info("orcaLogon()=> BEGIN");

        ActionErrors errors = new ActionErrors();

        String userName = request.getParameter(Constants.USER_NAME);
        if (!Utility.isSet(userName)) {
            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.missingUsername", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
        }

        String password = request.getParameter(Constants.USER_PASSWORD);
        if (!Utility.isSet(password)) {
            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.missingPassword", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
        }

        String userSiteId = request.getParameter(Constants.USER_SITE_ID);
        if (!Utility.isSet(userSiteId)) {
            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.missingSiteId", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
        }

        if (Utility.parseInt(userSiteId) == 0) {
            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.invalidSiteId", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        LdapItemData userInfo = new LdapItemData();

        userInfo.setUserName(userName);
        userInfo.setPassword(password);

        errors = LogOnLogic.logOnUser(response, request, userInfo, Integer.parseInt(userSiteId));

        //initialize New UI defaults 
        if(errors.isEmpty()) {
        	errors = initEswDefaults(request,userSiteId);
        } else {
        	log.info("orcaLogon()=> Errors: " + errors + ", RETURN...");
        }
        
        log.info("orcaLogon()=> END.");

        return errors;

    }


    public static ActionErrors viewOrder(HttpServletResponse response, HttpServletRequest request, RemoteAccessMgrForm pForm) throws Exception {

        log.info("viewOrder()=> BEGIN");

        ActionErrors errs;

        try {

            errs = OrcaAccessMgrLogic.initShopping(response, request, pForm);
            if (errs.size() > 0) {
                clearSessionAttributes(request.getSession());
                return errs;
            }

        } catch (Exception e) {
            clearSessionAttributes(request.getSession());
            throw e;
        }

        HttpSession session = request.getSession();

        int orderId = Utility.parseInt(request.getParameter("orderId"));
        log.info("viewOrder()=> orderId: " + orderId);

        session.setAttribute(ORDER_STATUS_ID, String.valueOf(orderId));

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
       /* pSession.setAttribute(Constants.ORCA_ACCESS, null);
        pSession.setAttribute(ORCA_ACCESS_MGR_FORM, null);
        pSession.setAttribute(Constants.ORCA_WEB_CLIENT, null);*/
    }

    public static ActionErrors keepAliveSession(HttpServletRequest request, RemoteAccessMgrForm pForm) {

        log.info("keepAliveSession()=> BEGIN, SessionID: "+request.getSession().getId());

        ActionErrors errors = new ActionErrors();

        log.info("keepAliveSession()=> END.");

        return errors;

    }

    public static ActionErrors doShopping(HttpServletResponse response, HttpServletRequest request, RemoteAccessMgrForm pForm) throws Exception {

        ActionErrors errs;

        try {

            errs = OrcaAccessMgrLogic.initShopping(response, request, pForm);
            if (errs.size() > 0) {
                clearSessionAttributes(request.getSession());
                return errs;
            }

        } catch (Exception e) {
            clearSessionAttributes(request.getSession());
            throw e;
        }

        return errs;

    }
    
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
     /*
     * Private method to determine what action forward should be returned after a select location request.
     */

	public static ActionErrors changeLocation(HttpServletRequest request, int siteId) 
	throws Exception
	{
    	ActionErrors errors = new ActionErrors();
    	CleanwiseUser user = (CleanwiseUser)request.getSession().getAttribute(Constants.APP_USER);
        BusEntityData location = null;
		User userEjb = APIAccess.getAPIAccess().getUserAPI();
		if (userEjb.isSiteOfUser(siteId, user.getUserId())) {
			location = APIAccess.getAPIAccess().getSiteAPI().getSite(siteId).getBusEntity();
		} else {
            String errorMess = ClwI18nUtil.getMessage(request, "location.search.invalidSiteSpecified", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
			return errors;
		}
        if (location != null) {
            errors = DashboardLogic.setUserLocation(request, user, location);
           	String mobileClient = (String)request.getSession(false).getAttribute(Constants.MOBILE_CLIENT);
            if(Utility.isTrue(mobileClient)){
				if(errors.isEmpty()) {
					SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
					sessionDataUtil.setSpecifiedLocation(null);
				}
            }
    		if (errors != null && !errors.isEmpty()) {
    			return errors;
    		}
        }
        else {
            String errorMess = ClwI18nUtil.getMessage(request, "location.search.invalidSiteSpecified", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
			return errors;
		}
		return errors;
    }
}
