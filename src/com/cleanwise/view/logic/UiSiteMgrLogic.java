package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.wrapper.UiPageViewWrapper;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.UiSiteMgrForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.UiConfigContext;
import com.cleanwise.view.utils.DisplayListSort;
import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class UiSiteMgrLogic {


    private static final Logger log = Logger.getLogger(UiSiteMgrLogic.class);

    private static final String UI_SITE_MGR_FORM = "UI_SITE_MGR_FORM";

    public static ActionErrors init(UiSiteMgrForm pForm, HttpServletRequest request) {

        log.info("init => BEGIN.pForm " + pForm);

        pForm = new UiSiteMgrForm();

        ActionErrors ae = checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        session.setAttribute(UI_SITE_MGR_FORM, pForm);
        log.info("init => END. Error Size : " + ae.size());

        return ae;

    }

    public static ActionErrors detail(UiSiteMgrForm pForm, HttpServletRequest request) throws Exception {

        log.info("detail => BEGIN");

        pForm = new UiSiteMgrForm();

        ActionErrors ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        Ui uiEjb = factory.getUiAPI();
        initFormVectors(request);

        UiConfigContext context = (UiConfigContext) request.getSession().getAttribute(Constants.UI_CONFIG_CONTEXT);
        GroupData group = context.getManagedGroup();

        UiData uiData;
        try {
            uiData = uiEjb.getUiDataForGroup(group.getGroupId());
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            log.info(e.getMessage(), e);
            ae.add("error", new ActionError("error.systemError", e.getMessage()));
            return ae;
        }

        try {
            UiPageView uiPage = uiEjb.getUiPage(uiData.getUiId(), RefCodeNames.UI_PAGE_TYPE_CD.SITE, RefCodeNames.UI_PAGE_CD.SITE_DETAIL);
            pForm.setUiPage(new UiPageViewWrapper(uiPage));
        } catch (DataNotFoundException e) {
            pForm.setUiPage(null);
            log.info(e.getMessage());
        }

        UiPageViewWrapper uiPage = pForm.getUiPage();
        if (uiPage == null) {
             uiPage = new UiPageViewWrapper(createNewPage(uiData));
        }

        log.info(uiData);
        pForm.setUiPage(uiPage);

        session.setAttribute(UI_SITE_MGR_FORM, pForm);
        log.info("detail => END. Error Size : " + ae.size());

        return ae;


    }

    private static UiPageView createNewPage(UiData uiData) {

        UiPageView uiPage = UiPageView.createValue();
        uiPage.setUiData(uiData);
        uiPage.setUiControls(new UiControlViewVector());

        UiPageData uiPageData = UiPageData.createValue();
        uiPageData.setShortDesc(RefCodeNames.UI_PAGE_CD.SITE_DETAIL);
        uiPageData.setTypeCd(RefCodeNames.UI_PAGE_TYPE_CD.SITE);
        uiPageData.setStatusCd(RefCodeNames.UI_PAGE_STATUS_CD.ACTIVE);
        uiPageData.setUiId(uiData.getUiId());

        uiPage.setUiPage(uiPageData);

        return uiPage;

    }

    public static ActionErrors savePage(UiSiteMgrForm pForm, HttpServletRequest request) throws Exception {

        log.info("savePage => BEGIN");

        ActionErrors ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        Ui uiEjb = factory.getUiAPI();

        UiPageViewWrapper uiPage = pForm.getUiPage();

        uiPage = new UiPageViewWrapper(uiEjb.updateUiPage(uiPage.getUiPageView(), appUser.getUserName()));

        log.info("savePage =>  uiPage : " + uiPage);

        session.setAttribute(UI_SITE_MGR_FORM, pForm);
        log.info("savePage => END. Error Size : " + ae.size());

        return ae;

    }

    private static ActionErrors checkRequest(HttpServletRequest request, ActionForm pForm) {

        ActionErrors ae = checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        if (pForm == null) {
            ae.add("error", new ActionError("error.systemError", "Form not initialized"));
            return ae;
        }

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

        UiConfigContext context = (UiConfigContext) request.getSession().getAttribute(Constants.UI_CONFIG_CONTEXT);
        if (context == null) {
            ae.add("error", new ActionError("error.systemError", "No ui context info"));
            return ae;
        }

        if (context.getManagedGroup() == null) {
            ae.add("error", new ActionError("error.systemError", "No group selected"));
            return ae;
        }

        return ae;
    }

    public static void initFormVectors(HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();

        if (null == session.getAttribute("ui.site.status.vector")) {
            ListService lsvc = factory.getListServiceAPI();
            RefCdDataVector statusv = lsvc.getRefCodesCollection("BUS_ENTITY_STATUS_CD");
            session.setAttribute("ui.site.status.vector", statusv);
        }

        if (null == session.getAttribute("ui.country.vector")) {
            Country countryEjb = factory.getCountryAPI();
            CountryDataVector countriesv = countryEjb.getAllCountries();
            session.setAttribute("ui.country.vector", countriesv);
        }

        UiConfigContext context = (UiConfigContext) request.getSession().getAttribute(Constants.UI_CONFIG_CONTEXT);
        GroupData group = context.getManagedGroup();

        IdVector storeIds = new IdVector();
        if (RefCodeNames.GROUP_TYPE_CD.STORE_UI.equals(group.getGroupTypeCd())) {
            storeIds = context.getAccessibleIds();
        } else if (RefCodeNames.GROUP_TYPE_CD.USER_UI.equals(group.getGroupTypeCd())) {
            User userEjb = factory.getUserAPI();
            storeIds = userEjb.getUserAssocCollecton(context.getAccessibleIds(), RefCodeNames.USER_ASSOC_CD.STORE, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        } else if (RefCodeNames.GROUP_TYPE_CD.ACCOUNT_UI.equals(group.getGroupTypeCd())) {
            Account accountEjb = factory.getAccountAPI();
            storeIds = accountEjb.getAccountAssocCollection(context.getAccessibleIds(), RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        }

        // Get all the business entities in question.
        Request rEjb = factory.getRequestAPI();
        List listAll = rEjb.listAll(storeIds, RefCodeNames.BUS_ENTITY_TYPE_CD.BUILDING_SVC_CONTRACTOR);
        DisplayListSort.sort((BuildingServicesContractorViewVector) listAll, "short_desc");
        session.setAttribute("ui.list.all.bsc", listAll);


    }

}
