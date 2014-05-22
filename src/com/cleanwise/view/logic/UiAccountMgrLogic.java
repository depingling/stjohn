package com.cleanwise.view.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Ui;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.CountryDataVector;
import com.cleanwise.service.api.value.GroupData;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.UiControlViewVector;
import com.cleanwise.service.api.value.UiData;
import com.cleanwise.service.api.value.UiPageData;
import com.cleanwise.service.api.value.UiPageView;
import com.cleanwise.service.api.wrapper.UiPageViewWrapper;
import com.cleanwise.view.forms.UiAccountMgrForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.UiConfigContext;
import com.cleanwise.view.utils.Admin2Tool;


public class UiAccountMgrLogic {
	
	private static final Logger log = Logger.getLogger(UiAccountMgrLogic.class);
	
	private static final String UI_ACCOUNT_MGR_FORM = "UI_ACCOUNT_MGR_FORM";
	
    public static ActionErrors init(UiAccountMgrForm pForm, HttpServletRequest request) {

        pForm = new UiAccountMgrForm();

        ActionErrors ae = checkRequest(request);
        if (ae.size() > 0) return ae;

        HttpSession session = request.getSession();
        session.setAttribute(UI_ACCOUNT_MGR_FORM, pForm);

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
	
    private static ActionErrors checkRequest(HttpServletRequest request, ActionForm pForm) {
        ActionErrors ae = checkRequest(request);
        if (ae.size() > 0) return ae;
        if (pForm == null)
            ae.add("error", new ActionError("error.systemError", "Form not initialized"));
        return ae;
    }
	
    public static ActionErrors savePage(UiAccountMgrForm pForm, HttpServletRequest request) throws Exception {
        ActionErrors ae = checkRequest(request, pForm);
        if (ae.size() > 0) return ae;

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        Ui uiEjb = factory.getUiAPI();

        UiPageView uiPage = pForm.getUiPage().getUiPageView();
        uiPage = uiEjb.updateUiPage(uiPage, appUser.getUserName());

        session.setAttribute(UI_ACCOUNT_MGR_FORM, pForm);
        return ae;
    }

    public static ActionErrors detail(UiAccountMgrForm pForm, HttpServletRequest request) throws Exception {
        pForm = new UiAccountMgrForm();

        ActionErrors ae = checkRequest(request, pForm);
        if (ae.size() > 0)return ae;

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
            UiPageView uiPage = uiEjb.getUiPage(uiData.getUiId(), RefCodeNames.UI_PAGE_TYPE_CD.ACCOUNT, RefCodeNames.UI_PAGE_CD.ACCOUNT_DETAIL);
            pForm.setUiPage(new UiPageViewWrapper(uiPage));
        } catch (DataNotFoundException e) {
            pForm.setUiPage(null);
            log.info(e.getMessage());
        }
        UiPageViewWrapper uiPage = pForm.getUiPage();
        if (uiPage == null) {
            uiPage = new UiPageViewWrapper(createNewPage(uiData));
        }
        pForm.setUiPage(uiPage);
        session.setAttribute(UI_ACCOUNT_MGR_FORM, pForm);
        return ae;
    }
    
    private static UiPageView createNewPage(UiData uiData) {

    	UiPageView uiPage = UiPageView.createValue();
        uiPage.setUiData(uiData);
        uiPage.setUiControls(new UiControlViewVector());

        UiPageData uiPageData = UiPageData.createValue();
        uiPageData.setShortDesc(RefCodeNames.UI_PAGE_CD.ACCOUNT_DETAIL);
        uiPageData.setTypeCd(RefCodeNames.UI_PAGE_TYPE_CD.ACCOUNT);
        uiPageData.setStatusCd(RefCodeNames.UI_PAGE_STATUS_CD.ACTIVE);
        uiPageData.setUiId(uiData.getUiId());
        uiPage.setUiPage(uiPageData);

        return uiPage;
    }

    private static void initFormVectors(HttpServletRequest request) throws Exception {

        Admin2Tool.initFormVectors(request,
                Admin2Tool.FORM_VECTORS.BUS_ENTITY_STATUS_CD,
                Admin2Tool.FORM_VECTORS.COUNTRY_CD,
                Admin2Tool.FORM_VECTORS.ACCOUNT_TYPE_CD,
                Admin2Tool.FORM_VECTORS.CUSTOMER_SYSTEM_APPROVAL_CD,
                Admin2Tool.FORM_VECTORS.INVENTORY_OG_LIST_UI,
                Admin2Tool.FORM_VECTORS.ORDER_ITEM_DETAIL_ACTION_CD,
                Admin2Tool.FORM_VECTORS.BUDGET_ACCRUAL_TYPE_CD,
                Admin2Tool.FORM_VECTORS.GL_TRANSFORMATION_TYPE,
                Admin2Tool.FORM_VECTORS.TIME_ZONE_CD,
                Admin2Tool.FORM_VECTORS.SHOP_UI_TYPE,
                Admin2Tool.FORM_VECTORS.FREIGHT_CHARGE_CD,
                Admin2Tool.FORM_VECTORS.DIST_INVENTORY_DISPLAY,
                Admin2Tool.FORM_VECTORS.DISTR_PO_TYPE);

    }
    
    
}
