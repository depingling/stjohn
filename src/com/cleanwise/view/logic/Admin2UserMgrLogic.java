package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.dao.UniversalDAO.dbrow;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.wrapper.UiPageViewWrapper;
import com.cleanwise.view.forms.*;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.*;
import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.*;

/**
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * Date:         29.06.2009
 * Time:         13:34:13
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class Admin2UserMgrLogic {

    private static final Logger log = Logger.getLogger(Admin2UserMgrLogic.class);

    public static final String ADMIN2_USER_MGR_FORM = "ADMIN2_USER_MGR_FORM";
    public static final String ADMIN2_USER_DETAIL_MGR_FORM = "ADMIN2_USER_DETAIL_MGR_FORM";
    public static final String ADMIN2_USER_PROFILE_MGR_FORM = "ADMIN2_USER_PROFILE_MGR_FORM";
    public static final String ADMIN2_USER_CONFIG_MGR_FORM = "ADMIN2_USER_CONFIG_MGR_FORM";

    private static final Comparator BUS_ENTITY_DATA_ID_COMPARE = new Comparator() {
        public int compare(Object o1, Object o2) {
            int id1 = ((BusEntityData) o1).getBusEntityId();
            int id2 = ((BusEntityData) o2).getBusEntityId();
            return id1 - id2;
        }
    };

    private static final Comparator SITE_VIEW_ID_COMPARE = new Comparator() {
        public int compare(Object o1, Object o2) {
            int id1 = ((SiteView) o1).getId();
            int id2 = ((SiteView) o2).getId();
            return id1 - id2;
        }
    };

    private static final Comparator CATALOG_DATA_ID_COMPARE = new Comparator() {
        public int compare(Object o1, Object o2) {
            int id1 = ((CatalogData) o1).getCatalogId();
            int id2 = ((CatalogData) o2).getCatalogId();
            return id1 - id2;
        }
    };

    private static final Comparator OG_DESC_DATA_ID_COMPARE = new Comparator() {
        public int compare(Object o1, Object o2) {
            int id1 = ((OrderGuideDescData) o1).getOrderGuideId();
            int id2 = ((OrderGuideDescData) o2).getOrderGuideId();
            return id1 - id2;
        }
    };

    private static final Comparator<? super PairView> USER_GROUP_ID_COMPARE = new Comparator<PairView>() {
        public int compare(PairView o1, PairView o2) {
            int id1 = (Integer) o1.getObject1();
            int id2 = (Integer) o2.getObject1();
            return id1 - id2;
        }
    };

    private static final Comparator<? super DistributorData> DISTRIBUTOR_DATA_ID_COMPARE = new Comparator<DistributorData>() {
        public int compare(DistributorData o1, DistributorData o2) {
            int id1 = o1.getBusEntity().getBusEntityId();
            int id2 = o2.getBusEntity().getBusEntityId();
            return id1 - id2;
        }
    };

    private static final Comparator<? super ServiceProviderData> SERVICE_PROVIDER_DATA_ID_COMPARE = new Comparator<ServiceProviderData>() {
        public int compare(ServiceProviderData o1, ServiceProviderData o2) {
            int id1 = o1.getBusEntity().getBusEntityId();
            int id2 = o2.getBusEntity().getBusEntityId();
            return id1 - id2;
        }
    };

    public static ActionErrors init(HttpServletRequest request, Admin2UserMgrForm pForm) throws Exception {
        log.info("init => BEGIN");

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        if (pForm == null || !pForm.isInit(request)) {

            pForm = new Admin2UserMgrForm();
            pForm.init(request);

            ae = checkRequest(pForm, request);
            if (ae.size() > 0) {
                return ae;
            }

            HttpSession session = request.getSession();
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            log.info("init => user: " + appUser.getUser().getUserTypeCd());

            Admin2Tool.initFormVectors(request, Admin2Tool.FORM_VECTORS.USER_TYPE_CODE);

            session.setAttribute(ADMIN2_USER_MGR_FORM, pForm);

        }

        log.info("init => END. Error Size : " + ae.size());

        return ae;
    }

    public static ActionErrors clone(HttpServletRequest request, Admin2UserMgrForm pForm) throws Exception {

        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = checkRequest(pForm, request);
        if (ae.size() > 0) {
            return ae;
        }

        ae = init(request, (Admin2UserDetailMgrForm) null);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = new APIAccess();
        User userEjb = factory.getUserAPI();

        int templateId = pForm.getSelectedId();
        UserDataVector users = pForm.getSearchResult();

        if (templateId <= 0 || users == null || users.size() == 0) {
            ae.add("error", new ActionError("error.simpleGenericError", "No user selected"));
            return ae;
        }

        boolean foundFl = false;
        for (Object oUser : users) {
            UserData user = (UserData) oUser;
            if (user.getUserId() == templateId) {
                foundFl = true;
                break;
            }
        }

        if (!foundFl) {
            ae.add("error", new ActionError("error.simpleGenericError", "Template user id is not in the list."));
            return ae;
        }
        if (!isUserEditable(request, templateId) ){
          ae.add("error",new ActionError("error.simpleGenericError","You are not allowed to create clone of this user"));
          return ae;
        }

        int userId = userEjb.createUserClone(templateId, appUser.getUserName(), "", RefCodeNames.USER_STATUS_CD.ACTIVE);

        Admin2UserDetailMgrForm dForm = (Admin2UserDetailMgrForm) session.getAttribute(ADMIN2_USER_DETAIL_MGR_FORM);
        ae = checkRequest(dForm, request);
        if (ae.size() > 0) {
            return ae;
        }

        dForm.setId(userId);

        return detail(request, dForm);

    }


    public static ActionErrors search(HttpServletRequest request, Admin2UserMgrForm pForm) throws Exception {

        log.info("search => BEGIN");

        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = checkRequest(pForm, request);
        if (ae.size() > 0) return ae;

        ae = checkUserSearchAttr(pForm, request);
        if (ae.size() > 0) return ae;


        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        String fieldSearchType = pForm.getSearchType();
        String fieldValue = pForm.getSearchField();
        String fieldFirstName = pForm.getFirstName();
        String fieldLastName = pForm.getLastName();
        String userTypeCd = pForm.getUserType();
        boolean fieldShowInactive = pForm.getSearchShowInactiveFl();
        IdVector accountIds = appUser.isaAdmin() ? Utility.toIdVector(pForm.getAccountFilter()) : Utility.toIdVector(appUser.getUserAccount().getAccountId());
        IdVector storeIds = Utility.toIdVector(appUser.getUserStore().getStoreId());
        RefCdDataVector userTypesCds = (RefCdDataVector) Admin2Tool.getFormVector(request, Admin2Tool.FORM_VECTORS.USER_TYPE_CODE);
        List<String> userTypes = Admin2Tool.toValueList(userTypesCds);

        UserDataVector users = search(appUser,
                fieldValue,
                fieldSearchType,
                fieldFirstName,
                fieldLastName,
                userTypeCd,
                userTypes,
                fieldShowInactive,
                storeIds,
                accountIds);

        pForm.setSearchResult(users);

        if (pForm.getSearchResult() != null) {
            DisplayListSort.sort(pForm.getSearchResult(), "name");
        }

        log.info("search => END.");

        return ae;

    }

    public static UserDataVector search(CleanwiseUser pAppUser,
                                        String pFieldValue,
                                        String pFieldSearchType,
                                        String pFirstName,
                                        String pLastName,
                                        String pTypeCd,
                                        List<String> pTypes,
                                        boolean pShowInactiveFl,
                                        IdVector pStoreIds,
                                        IdVector pAccountFilter) throws Exception {


        APIAccess factory = new APIAccess();
        User userEjb = factory.getUserAPI();

        UserSearchCriteriaData searchCriteria = UserSearchCriteriaData.createValue();

        searchCriteria.setStoreIds(pStoreIds);
        searchCriteria.setUserStoreIds(Utility.toIdVector(pAppUser.getAssociatedStores()));

        if (Constants.ID.equalsIgnoreCase(pFieldSearchType)) {
            searchCriteria.setUserId(pFieldValue);
        } else if (Constants.NAME_CONTAINS.equalsIgnoreCase(pFieldSearchType)) {
            // Lookup by name.  Two assumptions are made: 1) user may
            // have entered the whole name or the initial part of the
            // name; 2) the search is case insensitive.
            searchCriteria.setUserName(pFieldValue);
            searchCriteria.setUserNameMatch(User.NAME_CONTAINS_IGNORE_CASE);
        } else if (Constants.NAME_BEGINS.equalsIgnoreCase(pFieldSearchType)) {
            // Lookup by name.  Two assumptions are made: 1) user may
            // have entered the whole name or the initial part of the
            // name; 2) the search is case insensitive.
            searchCriteria.setUserName(pFieldValue);
            searchCriteria.setUserNameMatch(User.NAME_BEGINS_WITH_IGNORE_CASE);
        }

        searchCriteria.setFirstName(pFirstName);
        searchCriteria.setLastName(pLastName);
        searchCriteria.setIncludeInactiveFl(pShowInactiveFl);
        searchCriteria.setUserTypes(pTypes);

        if (Utility.isSet(pTypeCd)) {
            searchCriteria.setUserTypeCd(pTypeCd);
        }

        if (pAppUser.isaAccountAdmin()) {
            searchCriteria.setAccountId(pAppUser.getUserAccount().getAccountId());
            searchCriteria.setUserAccountIds(Utility.toIdVector(pAppUser.getAccounts()));
        } else if (pAppUser.isaAdmin() && pAccountFilter != null) {
            if (pAccountFilter.size() > 0) {
                searchCriteria.setAccountIds(pAccountFilter);
            }
        }

        return userEjb.getUsersCollectionByCriteria(searchCriteria);
    }

    public static ActionErrors detail(HttpServletRequest request, Admin2UserDetailMgrForm pForm) throws Exception {

        ActionErrors ae;
        HttpSession session = request.getSession();

        if (pForm == null || !pForm.isInit()) {
            ae = init(request, pForm);
            if (ae.size() > 0) {
                return ae;
            }
        } else {
            reset(pForm);
        }

        pForm = (Admin2UserDetailMgrForm) session.getAttribute(ADMIN2_USER_DETAIL_MGR_FORM);
        ae = checkRequest(pForm, request);
        if (ae.size() > 0) {
            return ae;
        }

        int reqUserId = Admin2Tool.getRequestedId(request);
        if (reqUserId <= 0) {
            reqUserId = pForm.getId();
        }

        pForm.setIsEditableForUserFl(isUserEditable(request, reqUserId));

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        UserInfoData userInfoData;
        if (reqUserId > 0) {
            try {
                userInfoData = getUserDetailById(request, reqUserId);
            } catch (DataNotFoundException e) {
                reset(pForm);
                ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
                return ae;
            }
        } else {
            userInfoData = createUserInfo(appUser);
        }

        pForm.setDetail(userInfoData);
        pForm.setId(userInfoData.getUserData().getUserId());
        initEntitiesOptions(userInfoData.getUserData().getUserId(), pForm, session);
        setUserCheckboxes(pForm);
        setUserProps(userInfoData.getUserData().getUserId(), pForm, session);
        initDateAttr(userInfoData, request, pForm);
        initUiPage(request, pForm);

        session.setAttribute(ADMIN2_USER_DETAIL_MGR_FORM, pForm);

        return ae;
    }

    private static void initUiPage(HttpServletRequest request, Admin2UserDetailMgrForm pForm) {

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        if (appUser.getUi() != null) {
            UiPageView uiPage = Utility.getUiPage(appUser.getUi().getUiPages(), RefCodeNames.UI_PAGE_CD.USER_DETAIL);
            pForm.setUiPage(new UiPageViewWrapper(uiPage));
        } else {
            pForm.setUiPage(null);
        }

    }

    private static UserInfoData createUserInfo(CleanwiseUser pAppUser) {
        UserInfoData user = UserInfoData.createValue();
        if (pAppUser.isaAccountAdmin()) {
            user.getUserData().setUserTypeCd(RefCodeNames.USER_TYPE_CD.MSB);
        }
        return user;
    }

    private static void initDateAttr(UserInfoData pUserInfoData, HttpServletRequest request, Admin2UserDetailMgrForm pForm) {

        pUserInfoData.getUserData().getEffDate();
        pUserInfoData.getUserData().getExpDate();

        if (pUserInfoData.getUserData().getEffDate() != null) {
            try {
                pForm.setEffDate(ClwI18nUtil.formatDateInp(request, pUserInfoData.getUserData().getEffDate()));
            } catch (Exception e) {
                pForm.setEffDate(pUserInfoData.getUserData().getEffDate().toString());
            }
        } else {
            pForm.setEffDate("");
        }

        if (pUserInfoData.getUserData().getExpDate() != null) {
            try {
                pForm.setExpDate(ClwI18nUtil.formatDateInp(request, pUserInfoData.getUserData().getExpDate()));
            } catch (Exception e) {
                pForm.setExpDate(pUserInfoData.getUserData().getExpDate().toString());
            }
        } else {
            pForm.setExpDate("");
        }

    }

    private static void reset(Admin2UserDetailMgrForm pForm) {

        pForm.setBaseUserForm(new Admin2UserRightsForm());
        pForm.setConfirmPassword(null);
        pForm.setCustomerServiceRoleCd(null);
        pForm.setDetail(UserInfoData.createValue());
        pForm.setDistributionCenterId(null);
        pForm.setEffDate(null);
        pForm.setExpDate(null);
        pForm.setEntities(new SelectableObjects(new BusEntityDataVector(), new BusEntityDataVector(), ClwComparatorFactory.getBusEntityComparator()));
        pForm.setIsCorporateUser(null);
        pForm.setManifestLabelHeight(null);
        pForm.setManifestLabelPrintMode(null);
        pForm.setManifestLabelType(null);
        pForm.setManifestLabelWidth(null);
        pForm.setPassword(null);
        pForm.setTotalReadOnly(null);
        pForm.setReceiveInvMissingEmail(null);
        pForm.setTotalReadOnly(null);
        pForm.setUserIDCode(null);
        pForm.setUiPage(null);

    }

    private static void setUserProps(int pUserId,
                                     Admin2UserDetailMgrForm pForm,
                                     HttpSession session) throws Exception {

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        PropertyService propEjb = factory.getPropertyServiceAPI();

        try {
            Properties updv = propEjb.getUserPropertyCollection(pUserId);
            Enumeration<?> enume = updv.propertyNames();
            while (enume.hasMoreElements()) {
                String key = (String) enume.nextElement();
                String value = updv.getProperty(key);
                if (key.equals(RefCodeNames.PROPERTY_TYPE_CD.DISTRIBUTION_CENTER_ID)) {
                    pForm.setDistributionCenterId(value);
                } else if (key.equals(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_HEIGHT)) {
                    pForm.setManifestLabelHeight(value);
                } else if (key.equals(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_WIDTH)) {
                    pForm.setManifestLabelWidth(value);
                } else if (key.equals(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_MODE)) {
                    pForm.setManifestLabelPrintMode(value);
                } else if (key.equals(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_TYPE)) {
                    pForm.setManifestLabelType(value);
                } else if (key.equals(RefCodeNames.PROPERTY_TYPE_CD.TOTAL_FIELD_READONLY)) {
                    pForm.setTotalReadOnly(String.valueOf(Utility.isSelected(value)));
                } else if (key.equals(RefCodeNames.PROPERTY_TYPE_CD.USER_ID_CODE)) {
                    pForm.setUserIDCode(value);
                } else if (key.equals(RefCodeNames.PROPERTY_TYPE_CD.CORPORATE_USER)) {
                    pForm.setIsCorporateUser(String.valueOf(Utility.isSelected(value)));
                } else if (key.equals(RefCodeNames.PROPERTY_TYPE_CD.RECEIVE_INV_MISSING_EMAIL)) {
                    pForm.setReceiveInvMissingEmail(String.valueOf(Utility.isSelected(value)));
                }
            }
        } catch (DataNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    public static ActionErrors init(HttpServletRequest request, Admin2UserDetailMgrForm pForm) throws Exception {

        log.info("init => BEGIN");

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        pForm = new Admin2UserDetailMgrForm();
        pForm.init();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        log.info("init => user: " + appUser.getUser().getUserTypeCd());

        initFormVectors(request);

        session.setAttribute(ADMIN2_USER_DETAIL_MGR_FORM, pForm);
        log.info("init => END. Error Size : " + ae.size());

        return ae;

    }


    private static void initFormVectors(HttpServletRequest request) throws Exception {

        Admin2Tool.initFormVectors(request, Admin2Tool.FORM_VECTORS.USER_STATUS_CODE,
                Admin2Tool.FORM_VECTORS.USER_TYPE_CODE,
                Admin2Tool.FORM_VECTORS.LOCALE_CD,
                Admin2Tool.FORM_VECTORS.COUNTRY_CD,
                Admin2Tool.FORM_VECTORS.MANIFEST_LABEL_MODE_CD,
                Admin2Tool.FORM_VECTORS.MANIFEST_LABEL_TYPE_CD,
                Admin2Tool.FORM_VECTORS.CUSTOMER_SERVICE_ROLE_CD);

    }


    public static UserInfoData getUserDetailById(HttpServletRequest request, int pUserId) throws Exception {

        HttpSession session = request.getSession(true);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = new APIAccess();
        User userBean = factory.getUserAPI();

        UserInfoData uid;
        if (pUserId > 0) {
            try {
                uid = userBean.getUserContactForStore(pUserId, appUser.getUserStoreAsIdVector(), true);
            } catch (DataNotFoundException e) {
                throw new DataNotFoundException(e.getMessage());
            }
        } else {
            uid = UserInfoData.createValue();
        }
        return uid;

    }


    private static void initEntitiesOptions(int pUserId,
                                            Admin2UserDetailMgrForm pForm,
                                            HttpSession session) throws Exception {

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        User userEjb = factory.getUserAPI();
        Account accountEjb = factory.getAccountAPI();
        Store storeEjb = factory.getStoreAPI();

        BusEntityDataVector options = new BusEntityDataVector();
        BusEntityDataVector userEntities = new BusEntityDataVector();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        log.info("initEntitiesOptions => pUserId:" + pUserId);
        log.info("initEntitiesOptions => appUser:" + appUser.getUserName());
        if (pUserId > 0) {
            if (appUser.isaAccountAdmin()) {
                userEntities = userEjb.getBusEntityCollection(pUserId, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
                log.info("initEntitiesOptions => accounts: " + userEntities.size());
            } else if (appUser.isaAdmin()) {
                userEntities = userEjb.getBusEntityCollection(pUserId, RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
                log.info("initEntitiesOptions => stores: " + userEntities.size());
            }
        }

        if (pUserId > 0) {
            if (appUser.isaAccountAdmin()) {
                options.addAll(userEntities);
                log.info("initEntitiesOptions => account options: " + options.size());
            } else if (RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
                options = storeEjb.getAllStoresBusEntityData(Store.ORDER_BY_NAME);
                log.info("initEntitiesOptions => store options: " + options.size());
            } else if (appUser.isaAdmin()) {
                options = userEjb.getBusEntityCollection(appUser.getUser().getUserId(), RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
                log.info("initEntitiesOptions => store options: " + options.size());
            }
        } else {
            if (appUser.isaAccountAdmin()) {
                BusEntitySearchCriteria criteria = new BusEntitySearchCriteria();
                criteria.setAccountBusEntityIds(Utility.toIdVector(appUser.getUserAccount().getAccountId()));
                options = accountEjb.getAccountBusEntByCriteria(criteria);
            } else if (appUser.isaAdmin()) {
                BusEntitySearchCriteria criteria = new BusEntitySearchCriteria();
                criteria.setStoreBusEntityIds(Utility.toIdVector(appUser.getUserStore().getStoreId()));
                options = storeEjb.getStoresBusEntByCriteria(criteria);
            }
        }

        SelectableObjects so = new SelectableObjects(userEntities, options, ClwComparatorFactory.getBusEntityComparator());
        pForm.setEntities(so);

    }


    public static ActionErrors setUserCheckboxes(Admin2UserDetailMgrForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();

        // Set the form values.
        UserInfoData uid = pForm.getDetail();
        if (uid != null && uid.getUserData().getUserId() > 0) {
            UserData ud = uid.getUserData();
            //set user rights
            UserRightsTool urt = new UserRightsTool(ud);
            Admin2UserRightsForm baseUserForm = pForm.getBaseUserForm();
            baseUserForm.setShowPrice(String.valueOf(urt.getShowPrice()));
            baseUserForm.setBrowseOnly(String.valueOf(urt.isBrowseOnly()));
            baseUserForm.setContractItemsOnly(String.valueOf(urt.isUserOnContract()));
            baseUserForm.setOnAccount(String.valueOf(urt.getOnAccount()));
            baseUserForm.setCreditCard(String.valueOf(urt.getCreditCardFlag()));
            baseUserForm.setOtherPayment(String.valueOf(urt.getOtherPaymentFlag()));
            baseUserForm.setPoNumRequired(String.valueOf(urt.getPoNumRequired()));
            baseUserForm.setSalesPresentationOnly(String.valueOf(urt.isPresentationOnly()));
            baseUserForm.setNoReporting(String.valueOf(urt.isNoReporting()));
            baseUserForm.setReportingManager(String.valueOf(urt.isReportingManager()));
            baseUserForm.setReportingAssignAllAccts(String.valueOf(urt.isReportingAssignAllAccts()));
            baseUserForm.setOrderNotificationApprovedEmail(String.valueOf(urt.getsEmailOrderApproved()));
            baseUserForm.setOrderNotificationModifiedEmail(String.valueOf(urt.getsEmailOrderModifications()));
            baseUserForm.setOrderNotificationRejectedEmail(String.valueOf(urt.getsEmailOrderRejection()));
            baseUserForm.setOrderNotificationNeedsApprovalEmail(String.valueOf(urt.getsEmailForApproval()));
            baseUserForm.setCanEditShipTo(String.valueOf(urt.canEditShipTo()));
            baseUserForm.setCanEditBillTo(String.valueOf(urt.canEditBillTo()));
            baseUserForm.setOrderDetailNotificationEmail(String.valueOf(urt.getEmailOrderDetailNotification()));
            baseUserForm.setOrderNotificationShippedEmail(String.valueOf(urt.getOrderNotificationShipped()));
            baseUserForm.setWorkOrderCompletedNotification(String.valueOf(urt.getWorkOrderCompletedNotification()));
            baseUserForm.setWorkOrderAcceptedByProviderNotification(String.valueOf(urt.getWorkOrderAcceptedByProviderNotification()));
            baseUserForm.setWorkOrderRejectedByProviderNotification(String.valueOf(urt.getWorkOrderRejectedByProviderNotification()));

            if (RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER.equals(ud.getWorkflowRoleCd())) {
                baseUserForm.setCanApproveOrders(String.valueOf(true));
            }

            pForm.setCustomerServiceRoleCd("");

            if (urt.isCustServiceApprover()) {
                pForm.setCustomerServiceRoleCd(RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.APPROVER);
            }

            if (urt.isCustServicePublisher()) {
                pForm.setCustomerServiceRoleCd(RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.PUBLISHER);
            }

            if (urt.isCustServiceViewer()) {
                pForm.setCustomerServiceRoleCd(RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.VIEWER);
            }

        } else {
            pForm.setBaseUserForm(new Admin2UserRightsForm());
            pForm.setCustomerServiceRoleCd("");
        }

        return ae;
    }

    public static ActionErrors updateUser(HttpServletRequest request, Admin2UserDetailMgrForm pForm) throws Exception {

        log.info("updateUser => BEGIN");

        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = checkRequest(pForm, request);
        if (ae.size() > 0) {
            log.info("updateUser => ae: " + ae);
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = new APIAccess();
        User userEjb = factory.getUserAPI();
        PropertyService propEjb = factory.getPropertyServiceAPI();
        Store storeEjb = factory.getStoreAPI();

        int userid = pForm.getDetail().getUserData().getUserId();
        UserInfoData uid = pForm.getDetail();
        if (0 == userid) {
            uid.getUserData().setUserRoleCd(Constants.UNKNOWN);
            uid.getUserData().setWorkflowRoleCd(Constants.UNKNOWN);
        }

        Admin2UserRightsForm urf = pForm.getBaseUserForm();
        boolean isCorporateUser = Utility.isSelected(pForm.getIsCorporateUser());
        boolean receiveInvMissingEmail = Utility.isSelected(pForm.getReceiveInvMissingEmail());
        boolean totalReadOnly = Utility.isSelected(pForm.getTotalReadOnly());
        String userIDCode = pForm.getUserIDCode();
        String selectedManifestLabelType = Utility.strNN(pForm.getManifestLabelType());
        String manifestLabelPrintMode = Utility.strNN(pForm.getManifestLabelPrintMode());
        String password = pForm.getPassword();
        String userTypeCd = uid.getUserData().getUserTypeCd();

        ae = validate(request, pForm);
        if (ae.size() > 0) {
            log.info("updateUser => ae: " + ae);
            return ae;
        }
        // Hash password (if updating an existing user, a new password
        // may not have been specified)
        if (Utility.isSet(password)) {
            uid.getUserData().setPassword(PasswordUtil.getHash(uid.getUserData().getUserName(), password));
        }

        if (!Utility.isSet(pForm.getDistributionCenterId())) {
            pForm.setDistributionCenterId(Constants.NA);
        }

        String rightsStr = urf.getRightsFromOptions();
        if (!Utility.isSet(rightsStr)) {
            rightsStr = Constants.UNKNOWN;
        }

        if (Utility.isSelected(urf.getCanApproveOrders())) {
            uid.getUserData().setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER);
            urf.setCanApproveOrders(String.valueOf(false));
        } else {
            uid.getUserData().setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
        }

        uid.getUserData().setUserRoleCd(rightsStr);

        Date now = new Date();
        uid.getUserData().setLastActivityDate(now);

        uid.getUserData().setModDate(now);
        uid.getUserData().setModBy((String) session.getAttribute(Constants.USER_NAME));

        uid.getAddressData().setName1(uid.getUserData().getFirstName());
        uid.getAddressData().setName2(uid.getUserData().getLastName());
        UserInfoData newUserInfo;
        try {

            newUserInfo = userEjb.addUserInfo(uid);
            userid = newUserInfo.getUserData().getUserId();

            //update/add/remove the user to store associations
            //this configuration is only avaliable for certain user types.  Don't do
            //anything with it if it is null
            if (pForm.getEntities() != null) {

                IdVector newlyIds = Utility.toIdVector(pForm.getEntities().getNewlySelected());
                IdVector deselectedIds = Utility.toIdVector(pForm.getEntities().getDeselected());

                if (appUser.isaAccountAdmin()) {

                    IdVector storesToAdd = new IdVector();
                    IdVector storesToDel = new IdVector();

                    BusEntityDataVector accountBusEntities = userEjb.getBusEntityCollection(userid, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);

                    IdVector accountBusEntityIds = Utility.toIdVector(accountBusEntities);
                    accountBusEntityIds.addAll(newlyIds);
                    accountBusEntityIds.removeAll(deselectedIds);

                    BusEntitySearchCriteria besc = new BusEntitySearchCriteria();
                    besc.setAccountBusEntityIds(accountBusEntityIds.isEmpty() ? null : accountBusEntityIds);
                    besc.setSearchForInactive(true);

                    IdVector accountStoreIds = storeEjb.getStoreIdsByCriteria(besc);

                    BusEntityDataVector userStores = userEjb.getBusEntityCollection(userid, RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);

                    IdVector userStoreIds = Utility.toIdVector(userStores);
                    ArrayList<Integer> newlyStoreIds = new ArrayList<Integer>(accountStoreIds);

                    userStoreIds.removeAll(accountStoreIds);
                    newlyStoreIds.removeAll(Utility.toIdVector(userStores));

                    storesToDel.addAll(userStoreIds);
                    storesToAdd.addAll(newlyStoreIds);

                    log.info("updateUser => stores to add: " + storesToAdd);
                    log.info("updateUser => stores to del: " + storesToDel);

                    log.info("updateUser => accounts to add: " + newlyIds);
                    log.info("updateUser => accounts to del: " + deselectedIds);

                    userEjb.updateUserAssoc(newUserInfo.getUserData().getUserId(),
                            storesToAdd,
                            newlyIds,
                            null,
                            storesToDel,
                            deselectedIds,
                            null,
                            appUser.getUserName());

                    pForm.getEntities().resetState();

                } else {

                    userEjb.updateUserAssoc(newUserInfo.getUserData().getUserId(),
                            newlyIds,
                            null,
                            null,
                            deselectedIds,
                            null,
                            null,
                            appUser.getUserName());

                    pForm.getEntities().resetState();
                }
            }

            // Refresh user info from the database.
            newUserInfo = getUserDetailById(request, userid);

        } catch (DuplicateNameException ne) {
            ae.add("user", new ActionError("user.duplicate.username"));
            return ae;
        }

        log.info("update => userid=" + userid + " user type=" + userTypeCd + " user info saved ");

        int theUserId = userid;
        if (0 == userid) {
            theUserId = newUserInfo.getUserData().getUserId();
        }

        log.info("update => userid=" + userid + " user type=" + userTypeCd + " save properties ");

        //update/add any properties
        Properties userProps = new Properties();
        //add requiered properties.  Check if they are set is done else where as they are requiered
        userProps.put(RefCodeNames.PROPERTY_TYPE_CD.DISTRIBUTION_CENTER_ID, pForm.getDistributionCenterId());
        userProps.put(RefCodeNames.PROPERTY_TYPE_CD.CORPORATE_USER, String.valueOf(isCorporateUser));
        userProps.put(RefCodeNames.PROPERTY_TYPE_CD.RECEIVE_INV_MISSING_EMAIL, String.valueOf(receiveInvMissingEmail));
        //add any of the non required properties if they were set
        if (Utility.isSet(pForm.getManifestLabelHeight())) {
            userProps.put(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_HEIGHT, pForm.getManifestLabelHeight());
        }
        if (Utility.isSet(pForm.getManifestLabelWidth())) {
            userProps.put(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_WIDTH, pForm.getManifestLabelWidth());
        }

        log.info("update => userid=" + userid + " label print mode=" + manifestLabelPrintMode);
        if (Utility.isSet(manifestLabelPrintMode)) {
            userProps.put(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_MODE, manifestLabelPrintMode);
        }

        log.info("update => userid=" + userid + " label type=" + selectedManifestLabelType);
        if (Utility.isSet(selectedManifestLabelType)) {
            userProps.put(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_TYPE, selectedManifestLabelType);
        }
        if (RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userTypeCd)) {
            userProps.put(RefCodeNames.PROPERTY_TYPE_CD.TOTAL_FIELD_READONLY, totalReadOnly);
        }
        if (Utility.isSet(userIDCode)) {
            userProps.put(RefCodeNames.PROPERTY_TYPE_CD.USER_ID_CODE, userIDCode);
        }

        log.info("update => userid=" + userid + " user type=" + userTypeCd + " save properties 2 size=" + userProps.size());
        propEjb.setUserPropertyCollection(theUserId, userProps);

        pForm.setId(newUserInfo.getUserData().getUserId());

        log.info("update => ae: " + ae);

        return detail(request, pForm);

    }

    private static ActionErrors validate(HttpServletRequest request, Admin2UserDetailMgrForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        UserInfoData uid = pForm.getDetail();
        String userTypeCd = uid.getUserData().getUserTypeCd();

        RefCdDataVector userTypesCds = (RefCdDataVector) Admin2Tool.getFormVector(request, Admin2Tool.FORM_VECTORS.USER_TYPE_CODE);
        List<String> userTypes = Admin2Tool.toValueList(userTypesCds);
        if (!userTypes.contains(userTypeCd)) {
            String message = ClwI18nUtil.getMessage(request, "admin2.errors.noUserRightToSaveUser", new Object[]{userTypeCd});
            ae.add("rightsToSave", new ActionError("error.simpleGenericError", message));
            return ae;
        }
        if (!pForm.isIsEditableForUserFl()){
//          validateField = false;
          String message = ClwI18nUtil.getMessage(request, "user.unallowable.update.error", new Object[]{});
          ae.add("rightsToSave", new ActionError("error.simpleGenericError", message));
          return ae;
        }

        // If creating new user...
        if (uid.getUserData().getUserId() == 0) {
            // password and confirm password must both be set and equal
            if (!Utility.isSet(pForm.getPassword())) {
                ae.add("password", new ActionError("variable.empty.error", "Password"));
            } else if (!pForm.getPassword().equals(pForm.getConfirmPassword())) {
                String message = ClwI18nUtil.getMessage(request, "admin2.errors.passwordAndPasswordConfirmationDoNotMatch", null);
                ae.add("password", new ActionError("error.simpleGenericError", message));
            }
        } else { // updating user
            // if password set, then password must equal confirm password
            if (Utility.isSet(pForm.getPassword())) {
                if (!pForm.getPassword().equals(pForm.getConfirmPassword())) {
                    String message = ClwI18nUtil.getMessage(request, "admin2.errors.passwordAndPasswordConfirmationDoNotMatch", null);
                    ae.add("password", new ActionError("error.simpleGenericError", message));
                }
            }
        }

        if (!Utility.isSet(uid.getUserData().getUserName())) {
            ae.add("username", new ActionError("variable.empty.error", "UserName"));
        }

        if (!Utility.isSet(userTypeCd)) {
            ae.add("usertype", new ActionError("variable.empty.error", "User Type"));
        }

        if (RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(userTypeCd)) {

            if (uid.getUserData().getUserId() > 0 && !Utility.isSet(pForm.getDistributionCenterId())) {
                ae.add("distributionCenterId", new ActionError("variable.empty.error", "Distribution Center Id"));
            }

            if (Utility.isSet(pForm.getManifestLabelHeight())) {
                try {
                    Integer.parseInt(pForm.getManifestLabelHeight());
                } catch (NumberFormatException e) {
                    ae.add("manifestLabelHeight", new ActionError("error.invalidNumber", "Manifest Label Height"));
                }
            }

            if (Utility.isSet(pForm.getManifestLabelWidth())) {
                try {
                    new Integer(pForm.getManifestLabelWidth());
                } catch (NumberFormatException e) {
                    ae.add("manifestLabelWidth", new ActionError("error.invalidNumber", "Manifest Label Width"));
                }
            }
        }

        if (Utility.isSet(pForm.getExpDate())) {
            try {
                uid.getUserData().setExpDate(ClwI18nUtil.parseDateInp(request, pForm.getExpDate()));
            } catch (ParseException e) {
                ae.add("ExpDate", new ActionError("admin2.errors.wrongDateFormat", pForm.getExpDate()));
                uid.getUserData().setExpDate(null);
            }
        } else {
            uid.getUserData().setExpDate(null);
        }

        if (Utility.isSet(pForm.getEffDate())) {
            try {
                uid.getUserData().setEffDate(ClwI18nUtil.parseDateInp(request, pForm.getEffDate()));
            } catch (ParseException e) {
                ae.add("EffDate", new ActionError("admin2.errors.wrongDateFormat", pForm.getEffDate()));
                uid.getUserData().setEffDate(null);
            }
        } else {
            ae.add("EffDate", new ActionError("variable.empty.error", "User Active Date"));
        }

        String rightsStr = pForm.getBaseUserForm().getRightsFromOptions();
        if (!Utility.isSet(rightsStr)) {
            if (RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(userTypeCd) ||
                    RefCodeNames.USER_TYPE_CD.MSB.equals(userTypeCd) ||
                    RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userTypeCd) ||
                    RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userTypeCd)) {
                ae.add("userrights", new ActionError("variable.empty.error", "Default User Rights"));
            }
        }


        if (!Utility.isSet(uid.getUserData().getUserStatusCd())) {
            ae.add("userstatus", new ActionError("variable.empty.error", "User Status"));
        }

        if (!Utility.isSet(uid.getUserData().getPrefLocaleCd())) {
            ae.add("username", new ActionError("variable.empty.error", "User Preferred Language"));
        }

        if (!Utility.isSet(uid.getUserData().getFirstName())) {
            ae.add("userfirstname", new ActionError("variable.empty.error", "Contact First Name"));
        }

        if (!Utility.isSet(uid.getUserData().getLastName())) {
            ae.add("userlastname", new ActionError("variable.empty.error", "Contact Last Name"));
        }

        if (!Utility.isSet(uid.getPhone().getPhoneNum())) {
            ae.add("userphonr", new ActionError("variable.empty.error", "Contact Phone Number"));
        }

        if (!Utility.isSet(uid.getAddressData().getAddress1()) && !Utility.isSet(uid.getAddressData().getAddress2())) {
            ae.add("useraddress", new ActionError("variable.empty.error", "Contact Address"));
        }

        if (!Utility.isSet(uid.getAddressData().getCity())) {
            ae.add("usercity", new ActionError("variable.empty.error", "Contact Address City"));
        }

        if (!Utility.isSet(uid.getAddressData().getCountryCd())) {
            ae.add("usercountry", new ActionError("variable.empty.error", "Contact Address Country"));
        }

        if (!Utility.isSet(uid.getAddressData().getStateProvinceCd())) {
            ae.add("userstate", new ActionError("variable.empty.error", "Contact Address State"));
        }

        if (!Utility.isSet(uid.getAddressData().getPostalCode())) {
            ae.add("userzip", new ActionError("variable.empty.error", "Contact Zip Code"));
        }


        if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(userTypeCd)) {

            if (appUser.isaAccountAdmin()) {

                if (pForm.getEntities().getCurrentlySelected().isEmpty()) {
                    ae.add("accounts", new ActionError("variable.empty.error", "Accounts"));
                }

            } else {

                if (!RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userTypeCd)
                        && !RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userTypeCd)
                        && !RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(userTypeCd)
                        && !RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userTypeCd)
                        && !RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userTypeCd)
                        && !RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userTypeCd)) {

                    if (pForm.getEntities().getCurrentlySelected().size() > 1) {
                        ae.add("multystores", new ActionError("admin2.errors.multipleStoresForUser", userTypeCd));
                    }


                }

                if (pForm.getEntities().getCurrentlySelected().isEmpty()) {
                    ae.add("stores", new ActionError("variable.empty.error", "Stores"));
                }

            }
        }

        if (!Utility.isSet(uid.getEmailData().getEmailAddress())) {
            Admin2UserRightsForm userRights = pForm.getBaseUserForm();
            if (Utility.isSelected(userRights.getOrderDetailNotificationEmail())
                    || Utility.isSelected(userRights.getOrderNotificationShippedEmail())
                    || Utility.isSelected(userRights.getOrderNotificationNeedsApprovalEmail())
                    || Utility.isSelected(userRights.getOrderNotificationApprovedEmail())
                    || Utility.isSelected(userRights.getOrderNotificationRejectedEmail())
                    || Utility.isSelected(userRights.getOrderNotificationModifiedEmail())) {
                String message = ClwI18nUtil.getMessage(request, "admin2.errors.emailAddressRequiredIfOrderNotificationSelected", null);
                ae.add("emailAddress", new ActionError("error.simpleGenericError", message));

            }
        }

        return ae;

    }

    public static ActionErrors addUser(HttpServletRequest request) throws Exception {
        return detail(request, null);
    }

    public static ActionErrors init(HttpServletRequest request, Admin2UserProfileMgrForm pForm) throws Exception {

        log.info("init => BEGIN");

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        pForm = new Admin2UserProfileMgrForm();
        pForm.init();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        log.info("init => user: " + appUser.getUser().getUserTypeCd());

        session.setAttribute(ADMIN2_USER_PROFILE_MGR_FORM, pForm);
        log.info("init => END. Error Size : " + ae.size());

        return ae;

    }

    public static ActionErrors profileDetail(HttpServletRequest request, Admin2UserProfileMgrForm pForm) throws Exception {

        log.info("profileDetail => BEGIN");

        ActionErrors ae;
        HttpSession session = request.getSession();

        if (pForm == null || !pForm.isInit()) {
            ae = init(request, pForm);
            if (ae.size() > 0) {
                return ae;
            }
        } else {
            reset(pForm);
        }

        pForm = (Admin2UserProfileMgrForm) session.getAttribute(ADMIN2_USER_PROFILE_MGR_FORM);
        ae = checkRequest(pForm, request);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        pForm.setUser(appUser.getUser());
        log.info("profileDetail => appUser.getUser(): " + appUser.getUser());

        session.setAttribute(ADMIN2_USER_PROFILE_MGR_FORM, pForm);
        log.info("profileDetail => END. Error Size : " + ae.size());

        return ae;
    }

    private static void reset(Admin2UserProfileMgrForm pForm) {
        pForm.setConfirmPassword(null);
        pForm.setPassword(null);
        pForm.setUser(null);
        pForm.setConfirmationMessage(Constants.EMPTY);
    }

    public static ActionErrors updateProfile(HttpServletRequest request, Admin2UserProfileMgrForm pForm) throws Exception {

        log.info("updateProfile => BEGIN");

        ActionErrors ae;
        HttpSession session = request.getSession();

        ae = checkRequest(pForm, request);
        if (ae.size() > 0) {
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = new APIAccess();
        User userEjb = factory.getUserAPI();

        // Hash password (if updating an existing user, a new password
        // may not have been specified)
        String password = pForm.getPassword();
        String confpassword = pForm.getConfirmPassword();

        if (appUser.getUser().getUserId() != pForm.getUser().getUserId()) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2badRequest", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        if (password == null || password.trim().length() == 0) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.pleaseSetThePasswordField", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        if (confpassword == null || confpassword.trim().length() == 0) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.pleaseSetTheConfirmPasswordField", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        if (!confpassword.equals(password)) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.passwordAndPasswordConfirmationDoNotMatch", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        UserData userData = pForm.getUser();
        userData.setPassword(PasswordUtil.getHash(userData.getUserName(), password));

        userEjb.updateUser(userData, userData.getUserId());

        reset(pForm);

        pForm.setUser(appUser.getUser());
        pForm.setConfirmationMessage(ClwI18nUtil.getMessage(request, "admin2.message.profileUpdateConfirmationMessage", null));

        session.setAttribute(ADMIN2_USER_PROFILE_MGR_FORM, pForm);
        log.info("updateProfile => END. Error Size : " + ae.size());

        return ae;

    }


    public static ActionErrors init(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        log.info("init => BEGIN");

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        log.info("init => user: " + appUser.getUser().getUserTypeCd());

        Admin2UserDetailMgrForm dForm = (Admin2UserDetailMgrForm) session.getAttribute(ADMIN2_USER_DETAIL_MGR_FORM);

        if (dForm == null || !dForm.isInit()) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.formNotInit", new Object[]{ADMIN2_USER_DETAIL_MGR_FORM});
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        if (dForm.getDetail() == null || dForm.getDetail().getUserData() == null) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.noUserInfo", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        pForm = new Admin2UserConfigMgrForm();
        pForm.init();
        pForm.setUser(dForm.getDetail().getUserData());

        if (RefCodeNames.USER_TYPE_CD.SERVICE_PROVIDER.equals(pForm.getUser().getUserTypeCd())) {
            pForm.setConfFunction(Admin2UserConfigMgrForm.CONF_FUNUNCTION.SERVICE_PROVIDERS);
        } else if (RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(pForm.getUser().getUserTypeCd())) {
            pForm.setConfFunction(Admin2UserConfigMgrForm.CONF_FUNUNCTION.DISTRIBUTORS);
        } else if (appUser.isaAccountAdmin()) {
            pForm.setConfFunction(Admin2UserConfigMgrForm.CONF_FUNUNCTION.SITES);
        } else if (appUser.isaAdmin()) {
            pForm.setConfFunction(Admin2UserConfigMgrForm.CONF_FUNUNCTION.ACCOUNTS);
        }

        session.setAttribute(ADMIN2_USER_CONFIG_MGR_FORM, pForm);
        log.info("init => END. Error Size : " + ae.size());

        return initConfig(request, pForm);
    }

    public static ActionErrors initConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        log.info("initConfig => BEGIN");

        ActionErrors ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        User userBean = factory.getUserAPI();
        Group groupBean = factory.getGroupAPI();

        reset(pForm);

//        pForm.setGroupsReport(groupBean.getUserGroupsReport(pForm.getUser().getUserId()));

        session.setAttribute(ADMIN2_USER_CONFIG_MGR_FORM, pForm);

        log.info("initConfig => Conf.Function: " + pForm.getConfFunction());
        log.info("initConfig => config: " + pForm.getConfig());

        log.info("initConfig => END.");

        return ae;
    }


    public static ActionErrors searchConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        log.info("searchConfig => BEGIN.");

        ActionErrors ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        String confFunc = pForm.getConfFunction();

        log.info("searchConfig => confFunc: " + confFunc);

        if (Admin2UserConfigMgrForm.CONF_FUNUNCTION.ACCOUNTS.equals(confFunc)) {
            ae = searchUserAcctConfig(request, pForm);
        } else if (Admin2UserConfigMgrForm.CONF_FUNUNCTION.SITES.equals(confFunc)) {
            ae = searchUserSiteConfig(request, pForm);
        } else if (Admin2UserConfigMgrForm.CONF_FUNUNCTION.CATALOGS.equals(confFunc)) {
            ae = searchCatalogConfig(request, pForm);
        } else if (Admin2UserConfigMgrForm.CONF_FUNUNCTION.ORDER_GUIDES.equals(confFunc)) {
            ae = searchOgConfig(request, pForm);
        } else if (Admin2UserConfigMgrForm.CONF_FUNUNCTION.GROUPS.equals(confFunc)) {
            ae = searchGroupConfig(request, pForm);
        } else if (Admin2UserConfigMgrForm.CONF_FUNUNCTION.DISTRIBUTORS.equals(confFunc)) {
            ae = searchUserDistConfig(request, pForm);
        } else if (Admin2UserConfigMgrForm.CONF_FUNUNCTION.SERVICE_PROVIDERS.equals(confFunc)) {
            ae = searchServiceProviderConfig(request, pForm);
        }


        log.info("searchConfig => END.Errors: " + ae);

        return ae;
    }

    private static ActionErrors searchGroupConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        ae = checkConfigSearchAttr(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        SelectableObjects config = null;
        if (appUser.isaAccountAdmin() || appUser.isaAdmin()) {
            config = getUserGroupConfig(request, pForm);
        } else {
            reset(pForm);
        }

        pForm.setConfig(config);

        return ae;
    }

    private static SelectableObjects getUserGroupConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws APIServiceAccessException, RemoteException {

        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        Group groupEjb = factory.getGroupAPI();

        String searchType = pForm.getConfSearchType();
        String fieldValue = pForm.getConfSearchField();

        Map optionGroups = new HashMap();

        if (appUser.isaAccountAdmin()) {
        	optionGroups = groupEjb.getUserGroups(Utility.toIdVector(appUser.getAccounts()),RefCodeNames.GROUP_ASSOC_CD.ACCOUNT_OF_GROUP);
        } else if (appUser.isaStoreAdmin()) {
        	 optionGroups = groupEjb.getUserGroups(appUser.getUserStoreAsIdVector());
        } else if (appUser.isaAdmin()) {
            optionGroups = groupEjb.getUserGroups();
        }

        Map selectedGroups = groupEjb.getGroupsUserIsMemberOf(pForm.getUser().getUserId());

        List<PairView> options = searchUserGroups(optionGroups, fieldValue, searchType);
        List<PairView> selected = searchUserGroups(selectedGroups, fieldValue, searchType);

        //for reports
        ArrayList reports = groupEjb.getUserGroupsReport(pForm.getUser().getUserId());
        Iterator optIter = optionGroups.entrySet().iterator();

        ArrayList resultReports = new ArrayList();

        while(optIter.hasNext()){
        	Map.Entry<Integer, String> option = (Map.Entry<Integer, String>)optIter.next();
        	Iterator<dbrow> reportIter = reports.iterator();
        	while(reportIter.hasNext()){
        		dbrow dbc = reportIter.next();
        		if(new Integer(dbc.getStringValue(0)).equals(option.getKey()))
	        		resultReports.add(dbc);
        		}

        }

        pForm.setGroupsReport(resultReports);

        return new SelectableObjects(selected, options, USER_GROUP_ID_COMPARE, pForm.getConifiguredOnlyFl());

    }

    private static List<PairView> searchUserGroups(Map pUserGroups, String pFieldValue, String pSearchType) {

        List<PairView> resultUserGroups = new ArrayList<PairView>();

        if (pUserGroups != null) {
            for (Object oGroupId : pUserGroups.keySet()) {

                Integer groupId = (Integer) oGroupId;
                String groupName = (String) pUserGroups.get(groupId);

                if (Utility.isSet(pFieldValue)) {
                    if (Constants.NAME_BEGINS.equals(pSearchType) && groupName.toUpperCase().startsWith(pFieldValue.toUpperCase())) {
                        resultUserGroups.add(new PairView(groupId, groupName));
                    } else
                    if (Constants.NAME_CONTAINS.equals(pSearchType) && groupName.toUpperCase().contains(pFieldValue.toUpperCase())) {
                        resultUserGroups.add(new PairView(groupId, groupName));
                    }

                } else {
                    resultUserGroups.add(new PairView(groupId, groupName));
                }
            }

            Collections.sort(resultUserGroups, USER_GROUP_ID_COMPARE);

        }

        return resultUserGroups;
    }

    public static ActionErrors updateSiteConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        log.info("updateSiteConfig => BEGIN.");

        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        SelectableObjects configObjs = pForm.getConfig();

        if (configObjs != null && (appUser.isaAccountAdmin() || appUser.isaAdmin())) {

            ae = updateConfig(request, pForm, configObjs, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
            if (ae.size() > 0) {
                return ae;
            }

            ae = searchConfig(request, pForm);

        } else {
            log.info("updateSiteConfig => no objects to update.");
        }

        log.info("updateSiteConfig => END.");

        return ae;

    }


    public static ActionErrors updateGroupConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        log.info("updateGroupConfig => BEGIN.");

        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        SelectableObjects configObjs = pForm.getConfig();

        if (configObjs != null && (appUser.isaAccountAdmin() || appUser.isaAdmin())) {

            ae = updateGroupConfig(request, pForm, configObjs);
            if (ae.size() > 0) {
                return ae;
            }

            ae = searchConfig(request, pForm);

        } else {
            log.info("updateGroupConfig => no objects to update.");
        }

        log.info("updateGroupConfig => END.");

        return ae;

    }

    private static ActionErrors updateGroupConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm, SelectableObjects pConfigObjs) throws Exception {

        log.info("updateGroupConfig => BEGIN");

        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        User userEjb = factory.getUserAPI();
        Group groupEjb = factory.getGroupAPI();

        List selected = pConfigObjs.getNewlySelected();
        List deselected = pConfigObjs.getDeselected();

        Map userGroups = groupEjb.getGroupsUserIsMemberOf(pForm.getUser().getUserId());

        log.info("updateGroupConfig => userGroups:"+userGroups);
        log.info("updateGroupConfig => selected:"+selected);
        log.info("updateGroupConfig => deselected:"+deselected);

        for (Object oPair : selected) {
            PairView pair = (PairView) oPair;
            if (!userGroups.containsKey(pair.getObject1())) {
                userGroups.put(pair.getObject1(), pair.getObject2());
            }
        }

        for (Object oPair : deselected) {
            PairView pair = (PairView) oPair;
            if (userGroups.containsKey(pair.getObject1())) {
                userGroups.remove(pair.getObject1());
            }
        }

        log.info("updateGroupConfig => userGroups:"+userGroups);

        userEjb.updateUserGroups(pForm.getUser().getUserId(), new ArrayList<Integer>(userGroups.keySet()), appUser.getUserName());

        log.info("updateGroupConfig => END.");

        return ae;

    }


    private static ActionErrors searchUserSiteConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {
        log.info("searchUserAcctConfig => BEGIN");

        ActionErrors ae = checkConfigSearchAttr(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        log.info("searchUserAcctConfig => user: " + appUser.getUser().getUserTypeCd());

        SelectableObjects config = null;
        if (appUser.isaAccountAdmin() || appUser.isaAdmin()) {
            config = getUserSiteConfig(request, pForm);
        } else {
            reset(pForm);
        }

        pForm.setConfig(config);

        return ae;
    }

    private static SelectableObjects getUserSiteConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        Site siteEjb = factory.getSiteAPI();
        User userEjb = factory.getUserAPI();

        String fieldValue = pForm.getConfSearchField().trim();
        String fieldSearchRefNum = Utility.strNN(pForm.getSearchRefNum()).trim();
        String city = Utility.strNN(pForm.getConfCity()).trim();
        String state = Utility.strNN(pForm.getConfState()).trim();
        String searchType = Utility.strNN(pForm.getConfSearchType());
        String searchRefNumType = Utility.strNN(pForm.getSearchRefNumType());

        QueryRequest qr = new QueryRequest();

        qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);

        if (Utility.isSet(fieldValue)) {
            if (Constants.ID.equalsIgnoreCase(searchType)) {
                qr.filterBySiteId(Integer.parseInt(fieldValue));
            } else {
                if (Constants.NAME_BEGINS.equals(searchType)) {
                    qr.filterByOnlySiteName(fieldValue, QueryRequest.BEGINS_IGNORE_CASE);
                } else if (Constants.NAME_CONTAINS.equals(searchType)) {
                    qr.filterByOnlySiteName(fieldValue, QueryRequest.CONTAINS_IGNORE_CASE);
                }
            }
        }

        if (Utility.isSet(fieldSearchRefNum)) {
            if (Constants.NAME_BEGINS.equals(searchRefNumType)) {
                qr.filterByRefNum(fieldSearchRefNum, QueryRequest.BEGINS_IGNORE_CASE);
            } else if (Constants.NAME_CONTAINS.equals(searchRefNumType)) {
                qr.filterByRefNum(fieldSearchRefNum, QueryRequest.CONTAINS_IGNORE_CASE);
            }
        }

        if (Utility.isSet(city)) {
            qr.filterByCity(city, QueryRequest.BEGINS_IGNORE_CASE);
        }

        if (Utility.isSet(state)) {
            qr.filterByState(state, QueryRequest.BEGINS_IGNORE_CASE);
        }

        IdVector accountIds = new IdVector();
        if (appUser.isaAccountAdmin()) {
            accountIds = Utility.toIdVector(appUser.getUserAccount().getAccountId());
        } else if (appUser.isaAdmin()) {
            accountIds = userEjb.getUserAccountIds(pForm.getUser().getUserId(), appUser.getUserStore().getStoreId(), false);
            qr.filterByStoreIds(Utility.toIdVector(appUser.getUserStore().getStoreId()));
        }
        qr.filterByAccountIds(accountIds.isEmpty() ? Utility.toIdVector(-1) : accountIds);

        SiteViewVector options = siteEjb.getSiteCollection(qr);

        qr.filterByUserId(pForm.getUser().getUserId());

        SiteViewVector selected = siteEjb.getSiteCollection(qr);

        return new SelectableObjects(selected, options, SITE_VIEW_ID_COMPARE, pForm.getConifiguredOnlyFl());

    }

    private static ActionErrors checkRequest(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        if (pForm == null || !pForm.isInit()) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.formNotInit", new Object[]{ADMIN2_USER_CONFIG_MGR_FORM});
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        if (pForm.getUser() == null || !(pForm.getUser().getUserId() > 0)) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.noUserInfo", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        return ae;
    }

    private static ActionErrors checkRequest(Admin2UserMgrForm pForm, HttpServletRequest request) throws Exception {

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        if (pForm == null || !pForm.isInit(request)) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.formNotInit", new Object[]{ADMIN2_USER_MGR_FORM});
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        return ae;
    }

    private static ActionErrors checkRequest(Admin2UserDetailMgrForm pForm, HttpServletRequest request) throws Exception {

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        if (pForm == null || !pForm.isInit()) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.formNotInit", new Object[]{ADMIN2_USER_DETAIL_MGR_FORM});
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        return ae;
    }

    private static ActionErrors checkRequest(Admin2UserProfileMgrForm pForm, HttpServletRequest request) throws Exception {

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        if (pForm == null || !pForm.isInit()) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.formNotInit", new Object[]{ADMIN2_USER_PROFILE_MGR_FORM});
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        return ae;
    }

    public static ActionErrors searchUserAcctConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        log.info("searchUserAcctConfig => BEGIN");

        ActionErrors ae = checkConfigSearchAttr(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        log.info("searchUserAcctConfig => user: " + appUser.getUser().getUserTypeCd());

        SelectableObjects config = null;
        if (appUser.isaAccountAdmin()) {
            config = getAcctAdminUserAcctConfig(request, pForm);
        } else if (appUser.isaAdmin()) {
            config = getAdminUserAcctConfig(request, pForm);
        } else {
            reset(pForm);
        }

        pForm.setConfig(config);

        return ae;

    }

    private static ActionErrors checkConfigSearchAttr(HttpServletRequest request, Admin2UserConfigMgrForm pForm) {
        ActionErrors ae = new ActionErrors();
        if (Constants.ID.equalsIgnoreCase(pForm.getConfSearchType()) && Utility.isSet(pForm.getConfSearchField())) {
            try {
                Integer.parseInt(pForm.getConfSearchField());
            } catch (NumberFormatException e) {
                String err = ClwI18nUtil.getMessage(request, "admin2.errors.wrongIdFormat", new Object[]{pForm.getConfSearchField()});
                ae.add("error", new ActionError("error.simpleGenericError", err));
            }
        }
        return ae;
    }


    private static SelectableObjects getAcctAdminUserAcctConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws APIServiceAccessException, RemoteException {

        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        Account acctEjb = factory.getAccountAPI();
        User userEjb = factory.getUserAPI();

        UserData user = pForm.getUser();
        String searchType = pForm.getConfSearchType();
        String fieldValue = pForm.getConfSearchField();

        if (Constants.ID.equalsIgnoreCase(searchType) && Utility.isSet(fieldValue)) {

            int id = Integer.parseInt(fieldValue);

//            IdVector userAccountIds = Utility.toIdVector(appUser.getAccounts());
            int storeId = appUser.getUserStore().getBusEntity().getBusEntityId();
            IdVector userAccountIds = userEjb.getUserAccountIds(user.getUserId(), storeId, true);
            BusEntityDataVector options = appUser.getAccounts();

            BusEntityDataVector selected = new BusEntityDataVector();
            if (userAccountIds.contains(id)) {
                for (Object oUserAccount : options) {
                    if (((BusEntityData) oUserAccount).getBusEntityId() == id) {
                        selected.add(oUserAccount);
                    }
                }
            }

            BusEntityDataVector unSelected = new BusEntityDataVector();
            for (Object oUserAccount : options) {
              if (((BusEntityData) oUserAccount).getBusEntityId() == id) {
                unSelected.add(oUserAccount);
              }
            }

            return new SelectableObjects(selected, unSelected, BUS_ENTITY_DATA_ID_COMPARE, pForm.getConifiguredOnlyFl());

        } else {

            BusEntitySearchCriteria besc = new BusEntitySearchCriteria();

            besc.setSearchForInactive(true);

            if (Utility.isSet(fieldValue)) {

                besc.setSearchName(fieldValue);

                if (searchType.equals(Constants.NAME_BEGINS)) {
                    besc.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
                }

                if (searchType.equals(Constants.NAME_CONTAINS)) {
                    besc.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
                }

            }

            besc.setAccountBusEntityIds(Utility.toIdVector(appUser.getAccounts()));
            besc.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);

            BusEntityDataVector options = acctEjb.getAccountBusEntByCriteria(besc);

            besc.addUserId(user.getUserId());

            BusEntityDataVector selected = acctEjb.getAccountBusEntByCriteria(besc);

            return new SelectableObjects(selected, options, BUS_ENTITY_DATA_ID_COMPARE, pForm.getConifiguredOnlyFl());

        }

    }

    private static SelectableObjects getAdminUserAcctConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws APIServiceAccessException, RemoteException {

        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        Account acctEjb = factory.getAccountAPI();

        UserData user = pForm.getUser();
        String searchType = pForm.getConfSearchType();
        String fieldValue = pForm.getConfSearchField();

        if (Constants.ID.equalsIgnoreCase(searchType) && Utility.isSet(fieldValue)) {

            int id = Integer.parseInt(fieldValue);

            BusEntitySearchCriteria besc = new BusEntitySearchCriteria();

            besc.setSearchForInactive(true);


            besc.setStoreBusEntityIds(Utility.toIdVector(appUser.getUserStore().getStoreId()));
            besc.setStoreBusEntityIds(Utility.toIdVector(id));
            besc.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);

            BusEntityDataVector options = acctEjb.getAccountBusEntByCriteria(besc);

            besc.addUserId(user.getUserId());

            BusEntityDataVector selected = acctEjb.getAccountBusEntByCriteria(besc);

            return new SelectableObjects(selected, options, BUS_ENTITY_DATA_ID_COMPARE, pForm.getConifiguredOnlyFl());


        } else {

            BusEntitySearchCriteria besc = new BusEntitySearchCriteria();

            besc.setSearchForInactive(true);

            if (Utility.isSet(fieldValue)) {

                besc.setSearchName(fieldValue);

                if (Constants.NAME_BEGINS.equals(searchType)) {
                    besc.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
                }

                if (Constants.NAME_CONTAINS.equals(searchType)) {
                    besc.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
                }
            }

            besc.setStoreBusEntityIds(Utility.toIdVector(appUser.getUserStore().getStoreId()));
            besc.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);

            BusEntityDataVector options = acctEjb.getAccountBusEntByCriteria(besc);

            besc.addUserId(user.getUserId());

            BusEntityDataVector selected = acctEjb.getAccountBusEntByCriteria(besc);

            return new SelectableObjects(selected, options, BUS_ENTITY_DATA_ID_COMPARE, pForm.getConifiguredOnlyFl());

        }

    }

    private static void reset(Admin2UserConfigMgrForm pForm) {
        pForm.setConfig(null);
        pForm.setConfSearchType(Constants.NAME_BEGINS);
        pForm.setConifiguredOnlyFl(false);
        pForm.setSearchRefNumType(Constants.NAME_BEGINS);
    }

    public static ActionErrors sort(HttpServletRequest request, Admin2UserMgrForm pForm) throws Exception {

        log.info("search => BEGIN");

        ActionErrors ae;

        ae = checkRequest(pForm, request);
        if (ae.size() > 0) {
            return ae;
        }

        if (pForm.getSearchResult() == null) {
            return ae;
        }

        String sortField = request.getParameter(Constants.SORT_FIELD);
        DisplayListSort.sort(pForm.getSearchResult(), sortField);

        return ae;

    }

    public static ActionErrors updateAccountConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        log.info("updateAccountConfig => BEGIN.");

        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        SelectableObjects configObjs = pForm.getConfig();

        if (configObjs != null) {

            ae = checkAcctConfig(request, pForm, configObjs);
            if (!ae.isEmpty()) {
                return ae;
            }

            ae = updateConfig(request, pForm, configObjs, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
            if (!ae.isEmpty()) {
                return ae;
            }

            ae = searchConfig(request, pForm);

        } else {
            log.info("updateAccountConfig =>  no objects to update.");
        }

        log.info("updateAccountConfig => END.");

        return ae;

    }

    private static ActionErrors checkAcctConfig(HttpServletRequest request,
                                                Admin2UserConfigMgrForm pForm,
                                                SelectableObjects pConfigObjs) throws Exception {

        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        Site siteEjb = factory.getSiteAPI();

        IdVector deselectedIds = Utility.toIdVector(pConfigObjs.getDeselected());

        if (appUser.isaAccountAdmin()) {
            if (deselectedIds.contains(appUser.getUserAccount().getAccountId())) {
                String err = ClwI18nUtil.getMessage(request, "admin2.errors.cantRemoveCurrAcctAssoc", new Object[]{"" + appUser.getUserAccount().getAccountId()});
                ae.add("error", new ActionError("error.simpleGenericError", err));
                return ae;
            }
/* // Account Admin user will be allowed to remove Site Associations for Accounts the Account Admin is configured to //
            } else if (pForm.getRemoveSiteAssocFl()) {
                String err = ClwI18nUtil.getMessage(request, "admin2.errors.noUserRightToRemoveSiteAssoc", new Object[]{"" + appUser.getUserAccount().getAccountId()});
                ae.add("error", new ActionError("error.simpleGenericError", err));
                return ae;
            }
 */
        }

        for (Object oBusEntityId : deselectedIds) {

            if (!pForm.getRemoveSiteAssocFl()) {

                QueryRequest qr = new QueryRequest();
                qr.setResultLimit(4);
                qr.filterByUserId(pForm.getUser().getUserId());
                qr.filterByAccountIds(Utility.toIdVector((Integer) oBusEntityId));

                SiteViewVector accountSites = siteEjb.getSiteCollection(qr);

                if (!accountSites.isEmpty()) {

                    String str = "";
                    for (int i = 0; i < (accountSites.size() > 3 ? 3 : accountSites.size()); i++) {
                        SiteView siteView = (SiteView) accountSites.get(i);
                        str += " <" + siteView.getName() + "> ";
                    }

                    if (accountSites.size() > 3) {
                        str += " ...";
                    }

                    log.info("checkAcctConfig => str: " + str);
                    String err = ClwI18nUtil.getMessage(request, "admin2.errors.cantRemoveAcctAssoc", new Object[]{"" + oBusEntityId, str});
                    ae.add("error", new ActionError("error.simpleGenericError", err));

                    return ae;
                }
            }
        }

        return ae;

    }

    /*
    *Updates the configuration when using the user config screen.
    */
    private static ActionErrors updateConfig(HttpServletRequest request,
                                             Admin2UserConfigMgrForm pForm,
                                             SelectableObjects pConfigObjs,
                                             String pBusEntityTypeCd) throws Exception {

        log.info("updateConfig => BEGIN");

        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

        User userEjb = factory.getUserAPI();
        Store storeEjb = factory.getStoreAPI();
        Site siteEjb = factory.getSiteAPI();

        IdVector selected = Utility.toIdVector(pConfigObjs.getNewlySelected());
        IdVector deselected = Utility.toIdVector(pConfigObjs.getDeselected());

        log.info("updateConfig => selected: " + selected);
        log.info("updateConfig => deselected: " + deselected);

        if (RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT.equals(pBusEntityTypeCd)) {

            BusEntitySearchCriteria besc;

            IdVector storesToAdd = null;
            IdVector storesToDel = null;
            IdVector sitesToDel = null;

            if (appUser.isaAccountAdmin()) {

                storesToAdd = new IdVector();
                storesToDel = new IdVector();

                BusEntityDataVector accountBusEntities = userEjb.getBusEntityCollection(pForm.getUser().getUserId(), RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);

                IdVector accountBusEntityIds = Utility.toIdVector(accountBusEntities);
                accountBusEntityIds.addAll(selected);
                accountBusEntityIds.removeAll(deselected);

                besc = new BusEntitySearchCriteria();
                besc.setAccountBusEntityIds(accountBusEntityIds.isEmpty() ? null : accountBusEntityIds);
                besc.setSearchForInactive(true);

                IdVector accountStoreIds = storeEjb.getStoreIdsByCriteria(besc);

                BusEntityDataVector userStores = userEjb.getBusEntityCollection(pForm.getUser().getUserId(), RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);

                IdVector userStoreIds = Utility.toIdVector(userStores);
                ArrayList<Integer> newlyStoreIds = new ArrayList<Integer>(accountStoreIds);

                userStoreIds.removeAll(accountStoreIds);
                newlyStoreIds.removeAll(Utility.toIdVector(userStores));

                storesToDel.addAll(userStoreIds);
                storesToAdd.addAll(newlyStoreIds);
            }

            if (!deselected.isEmpty()) {

                besc = new BusEntitySearchCriteria();

                besc.setAccountBusEntityIds(deselected);
                besc.addUserId(pForm.getUser().getUserId());
                besc.setSearchForInactive(true);

                sitesToDel = siteEjb.getSiteIdsByCriteria(besc);
            }

            log.info("updateUser => stores to add: " + storesToAdd);
            log.info("updateUser => stores to del: " + storesToDel);

            log.info("updateUser => accounts to add: " + selected);
            log.info("updateUser => accounts to del: " + deselected);

            userEjb.updateUserAssoc(pForm.getUser().getUserId(),
                    storesToAdd,
                    selected,
                    null,
                    storesToDel,
                    deselected,
                    sitesToDel,
                    appUser.getUserName());

        } else if (RefCodeNames.BUS_ENTITY_TYPE_CD.SITE.equals(pBusEntityTypeCd)) {

            for (Object oSiteId : deselected) {
                userEjb.removeUserAssoc(pForm.getUser().getUserId(), (Integer) oSiteId);
            }

            for (Object oSiteId : selected) {
                userEjb.addUserAssoc(pForm.getUser().getUserId(), (Integer) oSiteId, RefCodeNames.USER_ASSOC_CD.SITE);
            }

        } else if (RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR.equals(pBusEntityTypeCd)) {

            for (Object oDistributorId : deselected) {
                userEjb.removeUserAssoc(pForm.getUser().getUserId(), (Integer) oDistributorId);
            }

            for (Object oDistributorId : selected) {
                userEjb.addUserAssoc(pForm.getUser().getUserId(), (Integer) oDistributorId, RefCodeNames.USER_ASSOC_CD.DISTRIBUTOR);
            }
        } else if (RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER.equals(pBusEntityTypeCd)) {

            for (Object oServiceProviderId : deselected) {
                userEjb.removeUserAssoc(pForm.getUser().getUserId(), (Integer) oServiceProviderId);
            }

            for (Object oServiceProviderId : selected) {
                userEjb.addUserAssoc(pForm.getUser().getUserId(), (Integer) oServiceProviderId, RefCodeNames.USER_ASSOC_CD.SERVICE_PROVIDER);
            }
        }

        log.info("updateConfig => END.");

        return ae;
    }

    public static ActionErrors searchUserDistConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        log.info("searchUserDistConfig => BEGIN");

        ActionErrors ae = checkConfigSearchAttr(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        log.info("searchUserDistConfig => user: " + appUser.getUser().getUserTypeCd());

        SelectableObjects config = null;
        if (RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(pForm.getUser().getUserTypeCd())) {
            config = getUserDistConfig(request, pForm);
        } else {
            reset(pForm);
        }

        pForm.setConfig(config);

        log.info("searchUserDistConfig => END.");

        return ae;
    }

    public static SelectableObjects getUserDistConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        Distributor distEjb = factory.getDistributorAPI();

        String searchType = pForm.getConfSearchType();
        String fieldValue = pForm.getConfSearchField();

        int userId    = pForm.getUser().getUserId();
        int storeId   = appUser.getUserStore().getStoreId();

        BusEntitySearchCriteria besc = new BusEntitySearchCriteria();

        if (Constants.ID.equalsIgnoreCase(searchType) && Utility.isSet(fieldValue)) {
            int id = Integer.parseInt(fieldValue);
            besc.setDistributorBusEntityIds(Utility.toIdVector(id));
        } else if (Constants.NAME_CONTAINS.equalsIgnoreCase(searchType)) {
            besc.setSearchName(fieldValue);
            besc.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
        } else if (Utility.isSet(fieldValue)) {
            besc.setSearchName(fieldValue);
            besc.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
        }

        if (appUser.isaAccountAdmin()) {
            int accountId = appUser.getUserAccount().getAccountId();
            besc.setAccountBusEntityIds(Utility.toIdVector(accountId));
        } else {
            besc.setStoreBusEntityIds(Utility.toIdVector(storeId));
        }

        besc.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);

        DistributorDataVector options = distEjb.getDistributorByCriteria(besc);

        besc.addUserId(userId);

        DistributorDataVector selected = distEjb.getDistributorByCriteria(besc);

        return new SelectableObjects(selected, options, DISTRIBUTOR_DATA_ID_COMPARE, pForm.getConifiguredOnlyFl());
    }

    public static ActionErrors updateUserDistConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        log.info("updateUserDistConfig => BEGIN.");

        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        SelectableObjects configObjs = pForm.getConfig();

        if (configObjs != null) {

            ae = updateConfig(request, pForm, configObjs, RefCodeNames.USER_TYPE_CD.DISTRIBUTOR);
            if (!ae.isEmpty()) {
                return ae;
            }

            ae = searchConfig(request, pForm);

        } else {
            log.info("updateUserDistConfig =>  no objects to update.");
        }

        log.info("updateUserDistConfig => END.");

        return ae;
    }

    public static ActionErrors searchServiceProviderConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        log.info("searchServiceProviderConfig => BEGIN");

        ActionErrors ae = checkConfigSearchAttr(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        log.info("searchServiceProviderConfig => user: " + appUser.getUser().getUserTypeCd());

        SelectableObjects config = null;
        if (RefCodeNames.USER_TYPE_CD.SERVICE_PROVIDER.equals(pForm.getUser().getUserTypeCd())) {
            config = getServiceProviderConfig(request, pForm);
        } else {
            reset(pForm);
        }

        pForm.setConfig(config);

        log.info("searchServiceProviderConfig => END.");

        return ae;
    }

    public static SelectableObjects getServiceProviderConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        Service serviceEjb = factory.getServiceAPI();

        String searchType = pForm.getConfSearchType();
        String fieldValue = pForm.getConfSearchField();

        int userId = pForm.getUser().getUserId();
        int storeId = appUser.getUserStore().getStoreId();

        BusEntitySearchCriteria besc = new BusEntitySearchCriteria();

        if (Constants.ID.equalsIgnoreCase(searchType) && Utility.isSet(fieldValue)) {
            int id = Integer.parseInt(fieldValue);
            besc.setServiceProviderBusEntityIds(Utility.toIdVector(id));
        } else if (Constants.NAME_CONTAINS.equalsIgnoreCase(searchType)) {
            besc.setSearchName(fieldValue);
            besc.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
        } else if (Utility.isSet(fieldValue)) {
            besc.setSearchName(fieldValue);
            besc.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
        }

        if (appUser.isaAccountAdmin()) {
            int accountId = appUser.getUserAccount().getAccountId();
            besc.setAccountBusEntityIds(Utility.toIdVector(accountId));
        } else {
            besc.setStoreBusEntityIds(Utility.toIdVector(storeId));
        }

        besc.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);

        ServiceProviderDataVector options = serviceEjb.getServiceProviderByCriteria(besc);

        besc.addUserId(userId);

        ServiceProviderDataVector selected = serviceEjb.getServiceProviderByCriteria(besc);

        return new SelectableObjects(selected, options, SERVICE_PROVIDER_DATA_ID_COMPARE, pForm.getConifiguredOnlyFl());

    }

      public static ActionErrors updateServiceProviderConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        log.info("updateServiceProviderConfig => BEGIN.");

        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        SelectableObjects configObjs = pForm.getConfig();

        if (configObjs != null) {

            ae = updateConfig(request, pForm, configObjs, RefCodeNames.USER_TYPE_CD.SERVICE_PROVIDER);
            if (!ae.isEmpty()) {
                return ae;
            }

            ae = searchConfig(request, pForm);

        } else {
            log.info("updateServiceProviderConfig =>  no objects to update.");
        }

        log.info("updateServiceProviderConfig => END.");

        return ae;
    }

    public static ActionErrors configureAllAccountSites(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        ActionErrors ae;

        log.info("configureAllAccountSites => BEGIN");

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        SelectableObjects configObjs = pForm.getConfig();

//        if (configObjs != null && (!appUser.isaAccountAdmin() && appUser.isaAdmin())) {
        if (configObjs != null && (appUser.isaAccountAdmin() || appUser.isaAdmin())) {

            ae = updateConfig(request, pForm, configObjs, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
            if (ae.size() > 0) {
                return ae;
            }


            APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
            User userEjb = factory.getUserAPI();
            UserData user = pForm.getUser();

            IdVector currentlySelected = Utility.toIdVector(configObjs.getCurrentlySelected());

            log.info("configureAllAccountSites => accounts to process: " + currentlySelected);

            for (Object oAccountId : currentlySelected) {
                int accountId = (Integer) oAccountId;
                if (accountId > 0) {
                    userEjb.assignAllSites(user.getUserId(), accountId, appUser.getUserName());
                }
            }
        }

        log.info("configureAllAccountSites => END. Errors: " + ae);

        return ae;

    }

    public static ActionErrors configureAllAccounts(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        log.info("configureAllAccounts => BEGIN");

        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        User userEjb = factory.getUserAPI();

//        if (!appUser.isaAccountAdmin() && appUser.isaAdmin()) {
        int accountAdminId = (appUser.isaAccountAdmin()) ? appUser.getUserId() : -1;
        if (appUser.isaAccountAdmin() || appUser.isaAdmin()) {

            int storeId = appUser.getUserStore().getStoreId();
            int userId = pForm.getUser().getUserId();
            try {
                userEjb.configureAllAccounts(userId, storeId, appUser.getUserName(), accountAdminId);
            } catch (Exception exc) {
                ae = StringUtils.getUiErrorMess(exc);
            }

            if (!ae.isEmpty()) {
                return ae;
            }

            ae = searchConfig(request, pForm);

        }

        log.info("configureAllAccounts => END.");

        return ae;
    }


    public static ActionErrors searchCatalogConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        ae = checkConfigSearchAttr(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        SelectableObjects config = null;
        if (appUser.isaAccountAdmin() || appUser.isaAdmin()) {
            config = getUserCatalogsConfig(request, pForm);
        } else {
            reset(pForm);
        }

        pForm.setConfig(config);

        return ae;
    }

    private static SelectableObjects getUserCatalogsConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        CatalogInformation catInfEjb = factory.getCatalogInformationAPI();

        String searchType = pForm.getConfSearchType();
        String fieldValue = pForm.getConfSearchField();
        int userId = pForm.getUser().getUserId();
        int storeId = appUser.getUserStore().getStoreId();

        if (appUser.isaAccountAdmin()) {

            CatalogDataVector catalogs = catInfEjb.getUserCatalogs(userId,
                    Utility.toIdVector(appUser.getUserAccount().getAccountId()),
                    fieldValue,
                    searchType);

            return new SelectableObjects(catalogs, catalogs, CATALOG_DATA_ID_COMPARE);

        } else if (appUser.isaAdmin()) {

            CatalogDataVector catalogs = catInfEjb.getUserCatalogs(userId,
                    storeId,
                    fieldValue,
                    searchType);

            return new SelectableObjects(catalogs, catalogs, CATALOG_DATA_ID_COMPARE);

        } else {

            return null;

        }
    }

    public static ActionErrors searchOgConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        ActionErrors ae;

        ae = checkRequest(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        ae = checkConfigSearchAttr(request, pForm);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        SelectableObjects config = null;
        if (appUser.isaAccountAdmin() || appUser.isaAdmin()) {
            config = getUserOgConfig(request, pForm);
        } else {
            reset(pForm);
        }

        pForm.setConfig(config);

        return ae;
    }

    private static SelectableObjects getUserOgConfig(HttpServletRequest request, Admin2UserConfigMgrForm pForm) throws Exception {

        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        OrderGuide ogEjb = factory.getOrderGuideAPI();

        String searchType = pForm.getConfSearchType();
        String fieldValue = pForm.getConfSearchField();
        int userId = pForm.getUser().getUserId();
        int storeId = appUser.getUserStore().getStoreId();

        if (appUser.isaAccountAdmin()) {

            OrderGuideDescDataVector orderGuides = ogEjb.getCollectionByUser(userId,
                    null,
                    Utility.toIdVector(appUser.getUserAccount().getAccountId()),
                    fieldValue,
                    searchType);

            return new SelectableObjects(orderGuides, orderGuides, OG_DESC_DATA_ID_COMPARE);

        } else if (appUser.isaAdmin()) {

            OrderGuideDescDataVector orderGuides = ogEjb.getCollectionByUser(userId,
                    storeId,
                    null,
                    fieldValue,
                    searchType);

            return new SelectableObjects(orderGuides, orderGuides, OG_DESC_DATA_ID_COMPARE);

        } else {

            return null;

        }
    }

    public static ActionErrors changeUserType(HttpServletRequest request, Admin2UserDetailMgrForm pForm) throws Exception {

        log.info("changeUserType => BEGIN");

        ActionErrors ae;

        ae = checkRequest(pForm, request);
        if (ae.size() > 0) {
            return ae;
        }

        pForm.setManifestLabelHeight(null);
        pForm.setManifestLabelWidth(null);
        pForm.setManifestLabelType(null);
        pForm.setManifestLabelPrintMode(null);
        pForm.setCustomerServiceRoleCd(null);
        pForm.setTotalReadOnly(null);
        pForm.getBaseUserForm().setReportingManager(null);
        pForm.getBaseUserForm().setReportingAssignAllAccts(null);

        log.info("changeUserType => END.");

        return ae;

    }


    private static ActionErrors checkUserSearchAttr(Admin2UserMgrForm pForm, HttpServletRequest request) {
        ActionErrors ae = new ActionErrors();
        if (Constants.ID.equalsIgnoreCase(pForm.getSearchType()) && Utility.isSet(pForm.getSearchField())) {
            try {
                Integer.parseInt(pForm.getSearchField());
            } catch (NumberFormatException e) {
                String err = ClwI18nUtil.getMessage(request, "admin2.errors.wrongIdFormat", new Object[]{pForm.getSearchField()});
                ae.add("error", new ActionError("error.simpleGenericError", err));
            }
        }
        return ae;
    }
    private static boolean isUserEditable(HttpServletRequest request, int pUserId) throws Exception{
      IdVector superUserIdV = null;
      String SUPER_RIGHT_USERS = "SUPER_RIGHT_USERS";
      HttpSession session = request.getSession();

      if (session.getAttribute(SUPER_RIGHT_USERS) == null) {
        APIAccess factory = new APIAccess();
        User userEJB = factory.getUserAPI();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        UserData userD = appUser.getUser();
        int userStoreId = appUser.getUserStore().getStoreId();
        superUserIdV = userEJB.getSuperRightUserCollection(userD, userStoreId);
        session.setAttribute(SUPER_RIGHT_USERS, superUserIdV);
      } else {
        superUserIdV = (IdVector)session.getAttribute(SUPER_RIGHT_USERS);
      }

      boolean isEditable = !(superUserIdV!=null && superUserIdV.size()>0 && superUserIdV.contains(new Integer(pUserId)));
      return isEditable;
    }

}

