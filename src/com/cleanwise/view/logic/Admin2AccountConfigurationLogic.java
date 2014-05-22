package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.OrderGuide;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.Admin2AccountConfigurationForm;
import com.cleanwise.view.forms.Admin2AccountMgrDetailForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.Admin2Tool;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Admin2AccountConfigurationLogic {

    private static final Logger log = Logger.getLogger(Admin2AccountMgrLogic.class);

    private static final String ADMIN2_ACCOUNT_CONFIGURATION_FORM = "ADMIN2_ACCOUNT_CONFIGURATION_FORM";

    /**
     * Initialize the search list when looking for objects related to an
     * account.
     *
     * @param pForm   ActionForm
     * @param request HttpServletRequest
     * @return action errors
     * @throws Exception if an error
     */
    public static ActionErrors init(HttpServletRequest request, Admin2AccountConfigurationForm pForm) throws Exception {

        log.info("init => BEGIN");

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        HttpSession session = request.getSession();

        Admin2AccountMgrDetailForm dForm = (Admin2AccountMgrDetailForm) session.getAttribute(Admin2AccountMgrLogic.ADMIN2_ACCOUNT_DETAIL_MGR_FORM);
        if (dForm == null || !dForm.isInit()) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.formNotInit", new Object[]{Admin2AccountMgrLogic.ADMIN2_ACCOUNT_DETAIL_MGR_FORM});
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        if (dForm.getAccountData() == null) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.noAccountInfo", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        pForm = new Admin2AccountConfigurationForm();
        pForm.setAccountId(dForm.getAccountData().getAccountId());
        pForm.setSearchForType(Admin2AccountConfigurationForm.CONF_FUNUNCTION.CATALOGS);

        Admin2Tool.initFormVectors(request, Admin2Tool.FORM_VECTORS.USER_TYPE_CODE);

        session.setAttribute(ADMIN2_ACCOUNT_CONFIGURATION_FORM, pForm);

        log.info("init => END.");

        return ae;
    }


    private static ActionErrors checkRequest(HttpServletRequest request, Admin2AccountConfigurationForm pForm) throws Exception {

        ActionErrors ae = Admin2Tool.checkRequest(request);
        if (ae.size() > 0) {
            return ae;
        }

        if (pForm == null) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.formNotInit", new Object[]{ADMIN2_ACCOUNT_CONFIGURATION_FORM});
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        if (!(pForm.getAccountId() > 0)) {
            String errorMess = ClwI18nUtil.getMessage(request, "admin2.errors.accountIdWasNotSpecified", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        return ae;

    }

    /**
     * Find objects related to this account.
     *
     * @param pForm   Admin2AccountConfigurationForm search form.
     * @param request HttpServletRequest
     * @return ActionErrors if an erros
     * @throws Exception if an exception
     */
    public static ActionErrors search(Admin2AccountConfigurationForm pForm, HttpServletRequest request) throws Exception {

        ActionErrors ae = checkRequest(request, pForm);
        if (ae.size() > 0) return ae;
        
        ae = checkAccountSearchAttr(pForm, request);
        if (ae.size() > 0) return ae;

        String sType = pForm.getSearchForType();

        resetConfigs(pForm);

        // Get the sites attached to this account.
        if (sType.equals(Admin2AccountConfigurationForm.CONF_FUNUNCTION.SITES)) {
            ae = searchAccountSites(pForm, pForm.getAccountId());
            resetShowInactive(pForm);
            return ae;
        }

        // Get the catalogs attached to this account.
        if (sType.equals(Admin2AccountConfigurationForm.CONF_FUNUNCTION.CATALOGS)) {
            ae = searchAccountCatalogs(pForm, pForm.getAccountId());
            resetShowInactive(pForm);
            return ae;
        }

        // Get the order guides attached to this account.
        if (sType.equals(Admin2AccountConfigurationForm.CONF_FUNUNCTION.ORDER_GUIDES)) {
            ae = searchAccountGuides(pForm, pForm.getAccountId());
            resetShowInactive(pForm);
            return ae;
        }

        // Get the users attached to this account.
        if (sType.equals(Admin2AccountConfigurationForm.CONF_FUNUNCTION.USERS)) {
            ae = searchAccountUsers(request, pForm, pForm.getAccountId());
            resetShowInactive(pForm);
            return ae;
        }

        resetShowInactive(pForm);

        return ae;

    }

    /**
     * searchAccountSites, fetches the current sites tied to this account
     *
     * @param pForm      ActionForm
     * @param pAccountId Account Id
     * @return ActionErrors errors
     * @throws Exception if an errors
     */
    private static ActionErrors searchAccountSites(Admin2AccountConfigurationForm pForm, int pAccountId) throws Exception {

        ActionErrors ae = new ActionErrors();

        APIAccess factory = new APIAccess();

        Site siteEjb = factory.getSiteAPI();

        String fieldValue = pForm.getSearchForName();
        String searchType = pForm.getSearchType();
        QueryRequest qr = new QueryRequest();

        SiteViewVector sites = new SiteViewVector();
        pForm.setSiteConfigs(sites);

        qr.filterByAccountId(pAccountId);

        if (searchType.equals(Constants.ID) && fieldValue.length() > 0) {

            Integer id = new Integer(fieldValue);
            qr.filterBySiteId(id);

        } else {

            qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);

            if (searchType.equals(Constants.NAME_BEGINS) && fieldValue.length() > 0) {
                qr.filterBySiteName(fieldValue, QueryRequest.BEGINS_IGNORE_CASE);
            } else if (fieldValue.length() > 0 || pForm.getCity().length() > 0 || pForm.getState().length() > 0) {
                qr.filterBySiteName(fieldValue, QueryRequest.CONTAINS_IGNORE_CASE);
            }

            if (Utility.isSet(pForm.getCity())) {
                qr.filterByCity(pForm.getCity(), QueryRequest.BEGINS_IGNORE_CASE);
            }

            if (Utility.isSet(pForm.getState())) {
                qr.filterByState(pForm.getState(), QueryRequest.BEGINS_IGNORE_CASE);
            }
        }

        if (!pForm.getShowInactiveFl()) {
            ArrayList<String> statusList = new ArrayList<String>();
            statusList.add(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
            statusList.add(RefCodeNames.BUS_ENTITY_STATUS_CD.LIMITED);
            qr.filterBySiteStatusCdList(statusList);
        }

        sites = siteEjb.getSiteCollection(qr);


        pForm.setSiteConfigs(sites);

        return ae;

    }

    /**
     * searchAccountCatalogs, fetches the current catalogs tied to this account
     *
     * @param pForm      ActionForm
     * @param pAccountId AccountId
     * @return ActionErrors errors
     * @throws Exception if an exc
     */
    private static ActionErrors searchAccountCatalogs(Admin2AccountConfigurationForm pForm, int pAccountId) throws Exception {

        ActionErrors ae = new ActionErrors();

        APIAccess factory = new APIAccess();

        String sName = pForm.getSearchForName();
        String sType = pForm.getSearchType();

        CatalogInformation catinfo = factory.getCatalogInformationAPI();
        CatalogDataVector catalogs;

        // set results to 0
        pForm.setCatalogConfigs(null);

        if (Constants.NAME_BEGINS.equals(sType) && sName.length() > 0) {
            catalogs = catinfo.getCatalogsCollectionByNameAndBusEntity(sName,
                    SearchCriteria.BEGINS_WITH_IGNORE_CASE,
                    pAccountId);
        } else if (sName.length() > 0) { // "nameContains"
            catalogs = catinfo.getCatalogsCollectionByNameAndBusEntity(sName,
                    SearchCriteria.CONTAINS_IGNORE_CASE,
                    pAccountId);
        } else {
            catalogs = catinfo.getCatalogsCollectionByBusEntity(pAccountId);
        }

        if (!pForm.getShowInactiveFl()) {
            Iterator it = catalogs.iterator();
            while (it.hasNext()) {
                CatalogData cD = (CatalogData) it.next();
                if (RefCodeNames.CATALOG_STATUS_CD.INACTIVE.equals(cD.getCatalogStatusCd())) {
                    it.remove();
                }
            }
        }

        // Catalogs found for this account
        pForm.setCatalogConfigs(catalogs);

        return ae;
    }


    /**
     * searchAccountGuides, fetches the current guides tied to this account
     *
     * @param pForm      ActionForm
     * @param pAccountId AccountId
     * @return ActionErrors errors
     * @throws Exception if an exc
     */
    private static ActionErrors searchAccountGuides(Admin2AccountConfigurationForm pForm, int pAccountId) throws Exception {


        ActionErrors ae = new ActionErrors();

        APIAccess factory = new APIAccess();

        OrderGuide orderGuideAPI = factory.getOrderGuideAPI();

        String sName = pForm.getSearchForName();
        String sType = pForm.getSearchType();

        OrderGuideDescDataVector orderGuides;

        int ogType = OrderGuide.TYPE_TEMPLATE;

        pForm.setOrderGuideConfigs(null);

        if (Constants.NAME_BEGINS.equals(sType) && sName.length() > 0) {
            orderGuides = orderGuideAPI.getCollectionByNameAndAccount(sName,
                    pAccountId,
                    OrderGuide.BEGINS_WITH_IGNORE_CASE,
                    ogType);
        } else if (sName.length() > 0) { // "nameContains"
            orderGuides = orderGuideAPI.getCollectionByNameAndAccount(sName,
                    pAccountId,
                    OrderGuide.CONTAINS_IGNORE_CASE,
                    ogType);
        } else {
            orderGuides = orderGuideAPI.getCollectionByAccount(pAccountId, ogType);
        }
        ////////////////////////////////////////////////
        if (!pForm.getShowInactiveFl()) {
            Iterator it = orderGuides.iterator();
            while (it.hasNext()) {
                OrderGuideDescData ogD = (OrderGuideDescData) it.next();
                if (RefCodeNames.SIMPLE_STATUS_CD.INACTIVE.equals(ogD.getStatus())){
                    it.remove();
                }
            }
        }

        // OrderGuides found for this account
        pForm.setOrderGuideConfigs(orderGuides);

        return ae;
    }

    /**
     * searchAccountUsers, fetches the current users tied to this account
     *
     * @param request    HttpServletRequest
     * @param pForm      ActionForm
     * @param pAccountId AccountId
     * @return ActionErrors errors
     * @throws Exception if an exc
     */
    private static ActionErrors searchAccountUsers(HttpServletRequest request, Admin2AccountConfigurationForm pForm, int pAccountId) throws Exception {

        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = new APIAccess();

        User userEjb = factory.getUserAPI();

        UserSearchCriteriaData searchCriteria = UserSearchCriteriaData.createValue();

        // Reset the session variables.
        pForm.setUserConfigs(null);

        RefCdDataVector userTypesCds = (RefCdDataVector) Admin2Tool.getFormVector(request, Admin2Tool.FORM_VECTORS.USER_TYPE_CODE);
        List<String> userTypes = Admin2Tool.toValueList(userTypesCds);

        String fieldValue = pForm.getSearchForName();
        String fieldSearchType = pForm.getSearchType();

        searchCriteria.setIncludeInactiveFl(pForm.getShowInactiveFl());

        searchCriteria.setUserTypes(userTypes);
        searchCriteria.setAccountId(pAccountId);

        if (Utility.isSet(pForm.getUserType())) {
            searchCriteria.setUserTypeCd(pForm.getUserType());
        }

        searchCriteria.setUserStoreIds(Utility.toIdVector(appUser.getAssociatedStores()));
        if (appUser.isaAccountAdmin()) {
            searchCriteria.setUserAccountIds(Utility.toIdVector(appUser.getAccounts()));
        }

        if (Constants.ID.equals(fieldSearchType)) {
            searchCriteria.setUserId(fieldValue);
        } else if (Constants.NAME_BEGINS.equals(fieldSearchType) && fieldValue.length() > 0) {
            // Lookup by name.  Two assumptions are made: 1) user may
            // have entered the whole name or the initial part of the
            // name; 2) the search is case insensitive.
            searchCriteria.setUserName(fieldValue);
            searchCriteria.setFirstName(pForm.getFirstName());
            searchCriteria.setLastName(pForm.getLastName());
            searchCriteria.setUserNameMatch(User.NAME_BEGINS_WITH_IGNORE_CASE);
        } else
        if (fieldValue.length() > 0 || pForm.getFirstName().length() > 0 || pForm.getLastName().length() > 0 || (pForm.getUserType().length() > 0)) {
            // Lookup by name.  Two assumptions are made: 1) user may
            // have entered the whole name or the initial part of the
            // name; 2) the search is case insensitive.
            searchCriteria.setUserName(fieldValue);
            searchCriteria.setFirstName(pForm.getFirstName());
            searchCriteria.setLastName(pForm.getLastName());
            searchCriteria.setUserNameMatch(User.NAME_CONTAINS_IGNORE_CASE);
        }

        UserDataVector uv = userEjb.getUsersCollectionByCriteria(searchCriteria);
        // Users found for this account.
        pForm.setUserConfigs(uv);

        return ae;
    }

    public static void resetShowInactive(Admin2AccountConfigurationForm pForm) {
        pForm.setShowInactiveFl(false);
    }

    public static void resetConfigs(Admin2AccountConfigurationForm pForm) {
        pForm.setUserConfigs(null);
        pForm.setOrderGuideConfigs(null);
        pForm.setSiteConfigs(null);
        pForm.setCatalogConfigs(null);
    }
    
    private static ActionErrors checkAccountSearchAttr(Admin2AccountConfigurationForm pForm, HttpServletRequest request) {
        ActionErrors ae = new ActionErrors();
        if (Constants.ID.equalsIgnoreCase(pForm.getSearchType()) && Utility.isSet(pForm.getSearchForName())) {
            try {
                Integer.parseInt(pForm.getSearchForName());
            } catch (NumberFormatException e) {
                String err = ClwI18nUtil.getMessage(request, "admin2.errors.wrongIdFormat", new Object[]{pForm.getSearchForName()});
                ae.add("error", new ActionError("error.simpleGenericError", err));
            }
        }
        return ae;
    }

}
