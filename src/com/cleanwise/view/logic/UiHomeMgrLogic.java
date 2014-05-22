package com.cleanwise.view.logic;

import com.cleanwise.view.forms.UiHomeMgrForm;
import com.cleanwise.view.forms.AnalyticReportForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.UiConfigContext;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.Group;
import com.cleanwise.service.api.session.DWOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.log4j.Logger;


import java.util.List;


public class UiHomeMgrLogic {

    private static final Logger log = Logger.getLogger(UiHomeMgrLogic.class);

    private static final String UI_HOME_MGR_FORM = "UI_HOME_MGR_FORM";

    public static ActionErrors init(UiHomeMgrForm pForm, HttpServletRequest request) throws Exception {

        log.info("init => BEGIN");

        ActionErrors ae = checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();

        pForm = (UiHomeMgrForm)session.getAttribute(UI_HOME_MGR_FORM);
        if (pForm == null) {
            pForm = new UiHomeMgrForm();
        }
        pForm.setAssocName("");

        UiConfigContext uiConfigContext = (UiConfigContext)session.getAttribute(Constants.UI_CONFIG_CONTEXT);
        if (uiConfigContext == null) {
            uiConfigContext = new UiConfigContext();
        }
        session.setAttribute(Constants.UI_CONFIG_CONTEXT, uiConfigContext);
        session.setAttribute(UI_HOME_MGR_FORM, pForm);

        if (pForm.getManagedGroups() == null || pForm.getManagedGroups().isEmpty()) {
            APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
            Group groupEjb = factory.getGroupAPI();

            UiGroupDataViewVector uiGroups = groupEjb.getUiGroupDataViewVector();
            pForm.setManagedGroups(uiGroups);

            if (!pForm.getManagedGroups().isEmpty()) {
                selectGroup(request, (UiGroupDataView) pForm.getManagedGroups().get(0), pForm);
            }
        }


        log.info("init => END. Error Size : " + ae.size());
       
        return ae;

    }

    private static ActionErrors checkRequest(HttpServletRequest request) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No user info"));
            return ae;
        }

        return ae;
    }

    public static ActionErrors changeGroup(UiHomeMgrForm pForm, HttpServletRequest request) throws Exception {

        log.info("changeGroup => BEGIN");

        ActionErrors ae = checkRequest(pForm, request);
        if (ae.size() > 0) {
            return ae;
        }

        int groupId = getRequestedId(request);
        if (groupId < 0) {
            ae.add("error", new ActionError("error.badRequest2"));
            return ae;
        }

        UiGroupDataView group = getGroup(pForm.getManagedGroups(), groupId);
        if (group == null) {
            ae.add("error", new ActionError("error.badRequest2"));
            return ae;
        }

        selectGroup(request, group, pForm);

        log.info("changeGroup => END.");

        return ae;
    }

    private static ActionErrors selectGroup(HttpServletRequest request, UiGroupDataView groupV, UiHomeMgrForm pForm) throws Exception {

        log.info("selectGroup => BEGIN");

        ActionErrors ae = new ActionErrors();

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        Group groupEjb = factory.getGroupAPI();

        IdVector idsToAccess = new IdVector();
        GroupData group = groupV.getGroupData();

        if (RefCodeNames.GROUP_TYPE_CD.STORE_UI.equals(group.getGroupTypeCd())) {
            idsToAccess.addAll(Utility.toIdVector(groupEjb.getStoresForGroup(group.getGroupId(), null)));
        } else if (RefCodeNames.GROUP_TYPE_CD.USER_UI.equals(group.getGroupTypeCd())) {
            GroupSearchCriteriaView gsc = GroupSearchCriteriaView.createValue();
            gsc.setGroupId(group.getGroupId());
            idsToAccess.addAll(Utility.toIdVector(groupEjb.getUsersForGroup(gsc, Group.NAME_EXACT, Group.ORDER_BY_NAME)));
        } else if (RefCodeNames.GROUP_TYPE_CD.ACCOUNT_UI.equals(group.getGroupTypeCd())) {
            GroupSearchCriteriaView gsc = GroupSearchCriteriaView.createValue();
            gsc.setGroupId(group.getGroupId());
            idsToAccess.addAll(Utility.toIdVector(groupEjb.getBusEntitysForGroup(group.getGroupId(), RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT)));
        } else {
            ae.add("error", new ActionError("error.badRequest2"));
            return ae;
        }

        UserDataVector users = groupEjb.getUiGroupUsers(group.getGroupId());
        groupV.setUsers(users);

        UiConfigContext context = (UiConfigContext) request.getSession().getAttribute(Constants.UI_CONFIG_CONTEXT);
        context.setManagedGroup(group);
        context.setAccessibleIds(idsToAccess);
        pForm.setCurrentGroup(groupV);

        log.info("selectGroup => END.idsToAccess:" + idsToAccess);

        return ae;

    }

    private static UiGroupDataView getGroup(UiGroupDataViewVector pGroups, int pGroupId) {
        if (pGroups != null) {
            for (Object oGroup : pGroups) {
                UiGroupDataView group = (UiGroupDataView) oGroup;
                if (group.getGroupData().getGroupId() == pGroupId) {
                    return group;
                }
            }
        }
        return null;
    }

    private static ActionErrors checkRequest(ActionForm pForm, HttpServletRequest request) {

        ActionErrors ae = checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        if (pForm == null) {
            ae.add("error", new ActionError("error.systemError", "Form not initialized"));
            return ae;
        }

        UiConfigContext context = (UiConfigContext) request.getSession().getAttribute(Constants.UI_CONFIG_CONTEXT);
        if (context == null) {
            ae.add("error", new ActionError("error.systemError", "No ui context info"));
            return ae;
        }

        return ae;

    }

    private static int getRequestedId(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            log.error("getRequestedId => ERROR: " + e.getMessage(), e);
            return -1;
        }
    }

    public static ActionErrors autosuggestAssocName(HttpServletRequest request, HttpServletResponse response, ActionForm pForm) throws Exception {
        log.info("autosuggestUiAccosString => BEGIN.");
        UiHomeMgrForm sForm = (UiHomeMgrForm)pForm;
        ActionErrors ae = new ActionErrors();
        try {
            String value = sForm.getAssocName();
            log.info("autosuggestUiAccosString => value: " + value);

            String jsonStr = "{items:[";
            if (value != null) {

                APIAccess factory = APIAccess.getAPIAccess();
                Group groupBean = factory.getGroupAPI();
                List<String> assocVector = groupBean.getGroupAssociationsStartWith(value, Constants.AUTOSAGGEST_REQUEST_ROWS);

                log.info("autosuggestUiAccosString => assoc size : " + assocVector.size());

                int i = 0;
                for (String assocName : assocVector) {
                    if (i > 0) {
                        jsonStr += ",";
                    }
                    jsonStr += "{name:\"" + assocName + "\"}";
                    i++;
                }
                //jsonStr += "{name:\"account1\"},{name:\"account2\"},{name:\"account3\"}";
            }
            jsonStr += "]}";

            response.setContentType("json-comment-filtered");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonStr);

            log.info("autosuggestUiAccosString => jsonStr : " + jsonStr);

        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("autosuggestUiAccosString => END.");

        return ae;

    }

    public static ActionErrors search(UiHomeMgrForm pForm, HttpServletRequest request) throws Exception {
        ActionErrors ae = new ActionErrors();
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        Group groupEjb = factory.getGroupAPI();
        String assocName = pForm.getAssocName();
        if (!Utility.isSet(assocName)) {
            return viewAll(pForm, request);
        }
        UiGroupDataViewVector uiGroups = groupEjb.searchUiGroupIdsByAssocName(assocName);
        pForm.setManagedGroups(uiGroups);

        return ae;
    }

    public static ActionErrors viewAll(UiHomeMgrForm pForm, HttpServletRequest request) throws Exception {
        ActionErrors ae = new ActionErrors();

        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        Group groupEjb = factory.getGroupAPI();

        UiGroupDataViewVector uiGroups = groupEjb.getUiGroupDataViewVector();
        pForm.setManagedGroups(uiGroups);
        pForm.setAssocName("");
        return ae;
    }

}
