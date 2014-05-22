package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.view.forms.Admin2HomeMgrForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.Admin2Tool;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Title:        Admin2HomeMgrLogic
 * Description:  Logic manager
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * Date:         25.05.2009
 * Time:         13:33:13
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class Admin2HomeMgrLogic {

    private static final Logger log = Logger.getLogger(Admin2HomeMgrLogic.class);

    private static final String ADMIN2_HOME_MGR_FORM = "ADMIN2_HOME_MGR_FORM";

    public static ActionErrors initUserHome(Admin2HomeMgrForm pForm, HttpServletRequest request) throws Exception {

        log.info("initUserHome => BEGIN");

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        pForm = new Admin2HomeMgrForm();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        log.info("initUserHome => user: " + appUser.getUser().getUserTypeCd());

        if (appUser.isaAccountAdmin()) {
            ae = initAccountAdminHome(request, pForm);
        } else if (appUser.isaAdmin()) {
            ae = initStoreAdminHome(request, pForm);
        }

        if (!ae.isEmpty()) {
            resetAdmin2Ui(request);
            return ae;
        }

        initAdmin2UI(request);

        session.setAttribute(ADMIN2_HOME_MGR_FORM, pForm);
        log.info("initUserHome => END. Error Size : " + ae.size());

        return ae;

    }

    public static ActionErrors reloadUserHome(HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();
        Admin2HomeMgrForm homeForm = (Admin2HomeMgrForm) session.getAttribute(ADMIN2_HOME_MGR_FORM);

        return initUserHome(homeForm, request);

    }

    private static ActionErrors initAccountAdminHome(HttpServletRequest request, Admin2HomeMgrForm pForm) throws Exception {

        log.info("initAccountAdminHome => BEGIN");

        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        User userEjb = factory.getUserAPI();
        Store storeEjb = factory.getStoreAPI();

        BusEntityDataVector userAccounts = userEjb.getBusEntityCollection(appUser.getUser().getUserId(), RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
        if (userAccounts == null || userAccounts.isEmpty()) {
            ae.add("error", new ActionError("account.assign.no_from"));
            return ae;
        }

        BusEntityData currentAccount = null;
        for (Object oUserAccount : userAccounts) {
            if (appUser.getUserAccount().getAccountId() == ((BusEntityData) oUserAccount).getBusEntityId()) {
                currentAccount = ((BusEntityData) oUserAccount);
                break;
            }
        }

        if (currentAccount == null) {
            ae.add("error", new ActionError("error.badRequest2"));
            return ae;
        }

        BusEntityData currentStore;
        try {
            currentStore = storeEjb.getStoreBusEntityByAccount(currentAccount.getBusEntityId());
        } catch (DataNotFoundException e) {
            currentStore = null;
        }

        if (currentStore == null) {
            ae.add("error", new ActionError("account.assign.no_store"));
            return ae;
        }

        if (appUser.getUserStore().getStoreId() != currentStore.getBusEntityId()) {
            ae.add("error", new ActionError("error.badRequest2"));
            return ae;
        }

        DisplayListSort.sort(userAccounts, "short_desc");
        pForm.setManagedEntities(userAccounts);

        pForm.setCurrentStore(currentStore);
        pForm.setCurrentAccount(currentAccount);

        log.info("initAccountAdminHome => END.");

        return ae;
    }


    private static ActionErrors initStoreAdminHome(HttpServletRequest request, Admin2HomeMgrForm pForm) throws Exception {

        log.info("initStoreAdminHome => BEGIN");

        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        User userEjb = factory.getUserAPI();

        BusEntityDataVector userStores = userEjb.getBusEntityCollection(appUser.getUser().getUserId(), RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
        if (userStores == null || userStores.isEmpty()) {
            ae.add("error", new ActionError("error.simpleGenericError", "No stores found for user: " + appUser.getUserName()));
            return ae;
        }

        BusEntityData currentStore = null;
        for (Object oUserStore : userStores) {
            if (appUser.getUserStore().getStoreId() == ((BusEntityData) oUserStore).getBusEntityId()) {
                currentStore = ((BusEntityData) oUserStore);
                break;
            }
        }

        if (currentStore == null) {
            ae.add("error", new ActionError("error.badRequest2"));
            return ae;
        }

        DisplayListSort.sort(userStores, "short_desc");
        pForm.setManagedEntities(userStores);

        pForm.setCurrentStore(currentStore);
        pForm.setCurrentAccount(null);

        log.info("initStoreAdminHome => END.");

        return ae;
    }

    public static ActionErrors changeEntity(Admin2HomeMgrForm pForm, HttpServletRequest request) throws Exception {

        log.info("changeEntity => BEGIN");

        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = checkRequest(pForm, request);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        int entityId = getRequestedId(request);
        if (entityId < 0) {
            ae.add("error", new ActionError("error.badRequest2"));
            return ae;
        }

        log.info("changeEntity => entityId: " + entityId);
        log.info("changeEntity => user: " + appUser.getUser().getUserTypeCd());

        ae = switchUserHome(request, appUser, entityId);
        if (!ae.isEmpty()) {
            return ae;
        }

        ae = initUserHome(pForm, request);

        log.info("changeEntity => END.Errors: " + ae);

        return ae;

    }


    private static ActionErrors switchUserHome(HttpServletRequest request, CleanwiseUser pAppUser, int pEntityId) throws Exception {

        ActionErrors ae = new ActionErrors();

        ActionMessages am = new ActionMessages();
        if (pAppUser.isaAccountAdmin()) {
            am = LogOnLogic.switchUserAccount(pEntityId, request);
        } else if (pAppUser.isaAdmin()) {
            am = LogOnLogic.switchUserStore(pEntityId, request);
        }

        Admin2Tool.removeAllFormVectors(request);

        if (!am.isEmpty()) {
            resetAdmin2Ui(request);
            ae.add(am);
            return ae;
        }

        initAdmin2UI(request);

        return ae;
    }

    private static ActionErrors checkRequest(Admin2HomeMgrForm pForm, HttpServletRequest request) throws Exception {

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        if (pForm == null) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.formNotInit", new Object[]{ADMIN2_HOME_MGR_FORM});
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        return ae;
    }

    private static void initAdmin2UI(HttpServletRequest request) {
        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
        LogOnLogic.initAdmin2UI(appUser, request);
    }

    private static void resetAdmin2Ui(HttpServletRequest request) {
        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
        appUser.setUi(null);
    }

    private static int getRequestedId(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            log.error("getRequestedId => ERROR: " + e.getMessage(), e);
            return -1;
        }
    }
}
