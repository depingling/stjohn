package com.cleanwise.view.actions;

import com.cleanwise.view.forms.OrcaAccessMgrForm;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.logic.OrcaAccessMgrLogic;
import com.cleanwise.service.api.util.Utility;
import org.apache.log4j.Logger;

import org.apache.struts.action.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;


public class OrcaAccessMgrAction extends ActionSuper {

    private static final Logger log = Logger.getLogger(OrcaAccessMgrAction.class);


    public final static String ACTION_ATTR = "action";

    //actions
    public final static String BAD_SESSION        = "badsession";
    public final static String NO_ACTION          = "noaction";
    public final static String SHOPPING           = "shopping";
    public final static String VIEW_ORDER         = "viewOrder";
    public final static String CLOSE_SESSION      = "closeSession";
    public final static String KEEP_ALIVE_SESSION = "keepAliveSession";

    //forwards
    private static final String ACTION_ERROR_KEY    = "Action Error";
    private static final String INVALID_SESSION_KEY = "Invalid Session";
    private static final String SHOP_ERROR          = "Shop-Error";

    private static final String SHOP_SESSION_ID = "shop.session.id";

    public ActionForward performSub(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ActionForward forward;

        SessionTool st = new SessionTool(request);

        if (!st.checkSession()) {

            if (isLogonAction(request)) {

                if (!isInvalidSession(request)) {

                    ActionErrors errors = OrcaAccessMgrLogic.orcaLogon(response, request);
                    if (!errors.isEmpty()) {
                        createActionErrorResponse(response, errors);
                        return null;
                    }

                } else {
                    log.info("processAction()=> session invalid");
                    createSessionErrorResponse(response);
                    return null;
                }

            } else {
                log.info("processAction()=> session invalid for action " + request.getParameter(ACTION_ATTR));
                createSessionErrorResponse(response);
                return null;
            }

        }

        String action = !st.checkSession() ? BAD_SESSION : request.getParameter(ACTION_ATTR);
        if (action == null) {
            action = NO_ACTION;
        }

        try {
            forward = processAction(action, form, request, mapping, response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return forward;

    }


    private boolean isInvalidSession(HttpServletRequest request) {
        boolean b = Utility.isSet(request.getParameter(SHOP_SESSION_ID)) && !request.getSession().getId().equals(request.getParameter(SHOP_SESSION_ID));
        log.info("isInvalidSession()=> <ShopSessionID: " + request.getParameter(SHOP_SESSION_ID) + ", SessionID: " + request.getSession().getId() + "> " + b);
        return b;
    }

    private boolean isLogonAction(HttpServletRequest request) {
        return SHOPPING.equals(request.getParameter(ACTION_ATTR))
                || VIEW_ORDER.equalsIgnoreCase(request.getParameter(ACTION_ATTR));
    }

    private ActionForward processAction(String action, ActionForm form, HttpServletRequest request, ActionMapping mapping, HttpServletResponse response) {

        log.info("processAction()=> action : " + action + " form : " + form + " mapping attribute : " + mapping.getAttribute());

        String forwardName = null;
        ActionForward actionForward = null;

        try {
            if (form instanceof OrcaAccessMgrForm) {
                forwardName = processAction(action, (OrcaAccessMgrForm) form, request, response);
            } else {
                log.info("processAction()=> Unknown form instance: " + form);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("ERROR");
            return null;
        }


        if (forwardName != null) {
            actionForward = mapping.findForward(forwardName);
        }

        log.info("processActionm()=> return ActionForward : " + actionForward + ", forward name : " + forwardName);

        return actionForward;

    }

    private String processAction(String pAction, OrcaAccessMgrForm pForm, HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (NO_ACTION.equalsIgnoreCase(pAction)) {

        } else if (BAD_SESSION.equalsIgnoreCase(pAction)) {

              createSessionErrorResponse(response);
              return null;

        } else if (SHOPPING.equalsIgnoreCase(pAction)) {

            ActionErrors errs = OrcaAccessMgrLogic.initShopping(response, request, pForm);
            if (errs.size() > 0) {
                createActionErrorResponse(response, errs);
                return null;
            }

            return SHOPPING;

        } else if (VIEW_ORDER.equalsIgnoreCase(pAction)) {

            ActionErrors errs = OrcaAccessMgrLogic.viewOrder(response, request, pForm);
            if (errs.size() > 0) {
                createActionErrorResponse(response, errs);
                return null;
            }

            return null;

        } else if (CLOSE_SESSION.equalsIgnoreCase(pAction)) {

            ActionErrors errs = OrcaAccessMgrLogic.closeSession(request, pForm);
            if (errs.size() > 0) {
                createActionErrorResponse(response, errs);
                return null;
            }

            return null;

        } else if (KEEP_ALIVE_SESSION.equalsIgnoreCase(pAction)) {

            ActionErrors errs = OrcaAccessMgrLogic.keepAliveSession(request, pForm);
            if (errs.size() > 0) {
                createActionErrorResponse(response, errs);
                return null;
            }

            return null;

        }

        return null;

    }

    private void createActionErrorResponse(HttpServletResponse response, ActionErrors pErrs) throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        createAeResponse(response, pErrs);
    }

    private void createSessionErrorResponse(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.addHeader(SHOP_ERROR, INVALID_SESSION_KEY);
    }

    public static void createAeResponse(HttpServletResponse response, ActionErrors pErrs) throws IOException {

        int i = 0;

        response.addHeader(SHOP_ERROR, ACTION_ERROR_KEY);

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
                        jsonStr += "{\"errType\":\"" + errKey + "\",\"errValue\":\"" + value + "\"}";
                        i++;
                    }
                }
            }
        }

        jsonStr += "]";

        response.setContentType("json-comment-filtered");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(jsonStr);

    }

}
