package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.wrapper.UiPageViewWrapper;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Ui;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.UiUserMgrForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.UiConfigContext;
import com.cleanwise.view.utils.Admin2Tool;
import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UiUserMgrLogic {

    private static final Logger log = Logger.getLogger(UiUserMgrLogic.class);

    private static final String UI_USER_MGR_FORM = "UI_USER_MGR_FORM";

    public static ActionErrors init(UiUserMgrForm pForm, HttpServletRequest request) {

        log.info("init => BEGIN.pForm " + pForm);

        pForm = new UiUserMgrForm();

        ActionErrors ae = checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        session.setAttribute(UI_USER_MGR_FORM, pForm);
        log.info("init => END. Error Size : " + ae.size());

        return ae;

    }

    public static ActionErrors detail(UiUserMgrForm pForm, HttpServletRequest request) throws Exception {

        log.info("detail => BEGIN");

        pForm = new UiUserMgrForm();

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
            UiPageView uiPage = uiEjb.getUiPage(uiData.getUiId(), RefCodeNames.UI_PAGE_TYPE_CD.USER, RefCodeNames.UI_PAGE_CD.USER_DETAIL);
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

        session.setAttribute(UI_USER_MGR_FORM, pForm);
        log.info("detail => END. Error Size : " + ae.size());

        return ae;


    }

    private static UiPageView createNewPage(UiData uiData) {

        UiPageView uiPage = UiPageView.createValue();
        uiPage.setUiData(uiData);
        uiPage.setUiControls(new UiControlViewVector());

        UiPageData uiPageData = UiPageData.createValue();
        uiPageData.setShortDesc(RefCodeNames.UI_PAGE_CD.USER_DETAIL);
        uiPageData.setTypeCd(RefCodeNames.UI_PAGE_TYPE_CD.USER);
        uiPageData.setStatusCd(RefCodeNames.UI_PAGE_STATUS_CD.ACTIVE);
        uiPageData.setUiId(uiData.getUiId());

        uiPage.setUiPage(uiPageData);

        return uiPage;

    }

    private static void initFormVectors(HttpServletRequest request) throws Exception {
        log.info("initFormVectors => BEGIN");

        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();

        if (null == session.getAttribute("admin2.user.status.vector")) {
            ListService lsvc = factory.getListServiceAPI();
            RefCdDataVector statusv = lsvc.getRefCodesCollection("USER_STATUS_CD");
            session.setAttribute("admin2.user.status.vector", statusv);
        }

        if (null == session.getAttribute("admin2.user.type.vector")) {
            ListService lsvc = factory.getListServiceAPI();
            RefCdDataVector statusv = lsvc.getRefCodesCollection("USER_TYPE_CD");
            session.setAttribute("admin2.user.type.vector", statusv);
        }

        if (null == session.getAttribute("admin2.user.locale.vector")) {
            ListService lsvc = factory.getListServiceAPI();
            RefCdDataVector locales = lsvc.getRefCodesCollection("LOCALE_CD");
            log.info("initFormVectors => locale" + locales);
            session.setAttribute("admin2.user.locale.vector", locales);
        }

        if (null == session.getAttribute("admin2.country.vector")) {
            Country countryEjb = factory.getCountryAPI();
            CountryDataVector countriesv = countryEjb.getAllCountries();
            session.setAttribute("admin2.country.vector", countriesv);
        }

        Admin2Tool.initFormVectors(request,Admin2Tool.FORM_VECTORS.MANIFEST_LABEL_TYPE_CD);

        if (session.getAttribute("admin2.manifest_label_print_mode_cd.vector") == null) {
            ListService lsvc = factory.getListServiceAPI();
            RefCdDataVector col = lsvc.getRefCodesCollection("MANIFEST_LABEL_MODE_CD");
            session.setAttribute("admin2.manifest_label_print_mode_cd.vector", col);
        }

        if (session.getAttribute("admin2.customerService.role.vector") == null) {
            ListService lsvc = factory.getListServiceAPI();
            RefCdDataVector crcRolev = lsvc.getRefCodesCollection("CUSTOMER_SERVICE_ROLE_CD");
            session.setAttribute("admin2.customerService.role.vector", crcRolev);
        }

        log.info("initFormVectors => END.");

    }

    public static ActionErrors savePage(UiUserMgrForm pForm, HttpServletRequest request) throws Exception {

        log.info("savePage => BEGIN");

        ActionErrors ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        Ui uiEjb = factory.getUiAPI();

        UiPageView uiPage = pForm.getUiPage().getUiPageView();

        uiPage = uiEjb.updateUiPage(uiPage, appUser.getUserName());

        log.info("savePage =>  uiPage : " + uiPage);

        session.setAttribute(UI_USER_MGR_FORM, pForm);
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

}
