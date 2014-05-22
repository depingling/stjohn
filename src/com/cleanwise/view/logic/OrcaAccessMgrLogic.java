package com.cleanwise.view.logic;

import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.RemoteWebClient;
import com.cleanwise.service.api.value.LdapItemData;
import com.cleanwise.view.forms.OrcaAccessMgrForm;
import com.cleanwise.view.forms.SiteMgrSearchForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.*;
import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class OrcaAccessMgrLogic {

    private static final Logger log = Logger.getLogger(OrcaAccessMgrLogic.class);

    public static final String ORCA_ACCESS_MGR_FORM = "ORCA_ACCESS_MGR_FORM";
    //private static final String ORDER_STATUS_ID = "OrderStatus.id";

    public static ActionErrors init(HttpServletRequest request, OrcaAccessMgrForm pForm) {
        log.info("init()=> BEGIN/END.");
        return new ActionErrors();
    }


    public static ActionErrors initShopping(HttpServletResponse response, HttpServletRequest request, OrcaAccessMgrForm pForm) throws Exception {

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

        pForm = new OrcaAccessMgrForm();
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
        
        session.setAttribute(ORCA_ACCESS_MGR_FORM, pForm);
        /*session.setAttribute(Constants.ORCA_ACCESS, Boolean.TRUE);
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
        if (!errors.isEmpty()) {
            log.info("orcaLogon()=> Errors: " + errors + ", RETURN...");
            return errors;
        }

        log.info("orcaLogon()=> END.");

        return errors;

    }


    public static ActionErrors viewOrder(HttpServletResponse response, HttpServletRequest request, OrcaAccessMgrForm pForm) throws Exception {

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

        //session.setAttribute(ORDER_STATUS_ID, String.valueOf(orderId));
        
        //Set Order Id into session.
        SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
        sessionDataUtil.setOrderId(String.valueOf(orderId));

        log.info("viewOrder()=> END. ");

        return errs;
    }

    public static ActionErrors closeSession(HttpServletRequest request, OrcaAccessMgrForm pForm) {

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
        //pSession.setAttribute(Constants.ORCA_ACCESS, null);
        //pSession.setAttribute(ORCA_ACCESS_MGR_FORM, null);
        //pSession.setAttribute(Constants.ORCA_WEB_CLIENT, null);
    }

    public static ActionErrors keepAliveSession(HttpServletRequest request, OrcaAccessMgrForm pForm) {

        log.info("keepAliveSession()=> BEGIN, SessionID: "+request.getSession().getId());

        ActionErrors errors = new ActionErrors();

        log.info("keepAliveSession()=> END.");

        return errors;

    }

    public static ActionErrors doShopping(HttpServletResponse response, HttpServletRequest request, OrcaAccessMgrForm pForm) throws Exception {

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
}
