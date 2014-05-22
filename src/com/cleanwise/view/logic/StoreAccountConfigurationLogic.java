package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.StoreAccountConfigurationForm;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.FormArrayElement;
import com.cleanwise.view.utils.StringUtils;
import com.cleanwise.view.utils.CleanwiseUser;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Alexander Chikin
 *         Date: 31.08.2006
 *         Time: 11:28:14
 */
public class StoreAccountConfigurationLogic {
	
	private static final Logger log = Logger.getLogger(StoreAccountConfigurationLogic.class);

    /**
     * Initialize the search list when looking for objects related to an
     * account.
     *
     * @param request
     */
    public static ActionErrors init(HttpServletRequest request)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        try {
            HttpSession session = request.getSession();
            APIAccess factory = new APIAccess();
            ListService lsvc = factory.getListServiceAPI();

            // Set up the user types list.
            if (session.getAttribute("Users.types.vector") == null) {

                RefCdDataVector usertypes = lsvc.getRefCodesCollection("USER_TYPE_CD");
                session.setAttribute("Users.types.vector", usertypes);
            }

            ArrayList opts = new ArrayList();

            // Set the search type array to Catalog, OrderGuides,
            // Users, Sites
            opts.add(new FormArrayElement("catalogs", "Catalogs(Info)"));
            opts.add(new FormArrayElement("orderguides", "Order Guides(Info)"));
            opts.add(new FormArrayElement("sites", "Sites(Info)"));
            opts.add(new FormArrayElement("users", "Users(Info)"));
            request.getSession().setAttribute("Account.configuration.options.vector", opts);
            request.getSession().removeAttribute("Account.configuration.catalogs.vector");
            request.getSession().removeAttribute("Account.configuration.guides.vector");
            request.getSession().removeAttribute("Account.configuration.sites.vector");
            request.getSession().removeAttribute("Account.configuration.users.vector");
        }
        catch (NamingException e) {
            try {
                ae = StringUtils.getUiErrorMess(e);
            } catch (Exception e1) {
                ae.add(ActionErrors.GLOBAL_MESSAGE,
                        new ActionError("error.simpleGenericError",
                                e.getMessage()));
                e1.printStackTrace();
            }

        } catch (APIServiceAccessException e) {
            try {
                ae = StringUtils.getUiErrorMess(e);
            } catch (Exception e1) {
                ae.add(ActionErrors.GLOBAL_MESSAGE,
                        new ActionError("error.simpleGenericError",
                                e.getMessage()));
                e1.printStackTrace();
            }

        } catch (RemoteException e) {
            try {
                ae = StringUtils.getUiErrorMess(e);
            } catch (Exception e1) {
                ae.add(ActionErrors.GLOBAL_MESSAGE,
                        new ActionError("error.simpleGenericError",
                                e.getMessage()));
                e1.printStackTrace();

            }

        }
        return ae;
    }

    /**
     * Find objects related to this account.
     *
     * @param sForm   StoreAccountConfigurationForm search form.
     * @param request
     * @return ActionErrors
     */
    public static ActionErrors search(StoreAccountConfigurationForm sForm,
                                      HttpServletRequest request) {
        ActionErrors ae = new ActionErrors();

        String sAccountid = (String) request.getSession().getAttribute(
                "Account.id");

        if (sAccountid == null) {
            //ae.add("Account.id",new ActionError("error.simpleGenericError","Not session attribute Account.id"));
            return ae;
        }

        int accountid = Integer.parseInt(sAccountid);
        String sType = sForm.getSearchForType();

        // Get the sites attached to this account.
        if (sType.equals("sites")) {
            ae = searchAccountSites(request, sForm, accountid);
            resetShowInactive(sForm);
            return ae;
        }

        // Get the catalogs attached to this account.
        if (sType.equals("catalogs")) {
            ae = searchAccountCatalogs(request, sForm, accountid);
            resetShowInactive(sForm);
            return ae;
        }

        // Get the order guides attached to this account.
        if (sType.equals("orderguides")) {
            ae = searchAccountGuides(request, sForm, accountid);
            resetShowInactive(sForm);
            return ae;
        }

        // Get the users attached to this account.
        if (sType.equals("users")) {
            ae = searchAccountUsers(request, sForm, accountid);
            resetShowInactive(sForm);
            return ae;
        }
        resetShowInactive(sForm);
        return ae;
    }


    /**
     * searchAccountSites, fetches the current sites tied to this account
     *
     * @param request
     * @param pForm      Description of Parameter
     * @param pAccountId
     * @return ActionErrors
     */
    private static ActionErrors searchAccountSites(HttpServletRequest request, StoreAccountConfigurationForm pForm, int pAccountId) {
        
        ActionErrors ae = new ActionErrors();
    try{
        APIAccess factory = new APIAccess();

        Site siteEjb = factory.getSiteAPI();

        String fieldValue = pForm.getSearchForName();
        String searchType = pForm.getSearchType();
        QueryRequest qr = new QueryRequest();

        List<SiteView> sites = new ArrayList<SiteView>();
                
        request.getSession().setAttribute("Account.configuration.sites.vector", sites);

        qr.filterByAccountId(pAccountId);

        if (searchType.equalsIgnoreCase(Constants.ID) && fieldValue.length() > 0) {

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
        	 
        if (!pForm.isShowInactiveFl()) {
            ArrayList<String> statusList = new ArrayList<String>();
            statusList.add(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
            statusList.add(RefCodeNames.BUS_ENTITY_STATUS_CD.LIMITED);
            qr.filterBySiteStatusCdList(statusList);
        }

        sites = siteEjb.getSiteCollection(qr);


        request.getSession().setAttribute("Account.configuration.sites.vector", sites);

        } catch (NumberFormatException e) {
            try {
                ae = StringUtils.getUiErrorMess(e);
            } catch (Exception e1) {
                ae.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.invalidNumber", "Search Field"));
                e1.printStackTrace();
            }
        } catch (Exception e) {
            try {
                ae = StringUtils.getUiErrorMess(e);
            } catch (Exception e1) {
                ae.add(ActionErrors.GLOBAL_MESSAGE,
                        new ActionError("error.simpleGenericError",
                        e.getMessage()));
                e1.printStackTrace();

            }
        }

        return ae;

    }
    
    
    /**
     * searchAccountCatalogs, fetches the current catalogs tied to this account
     *
     * @param request
     * @param pForm      Description of Parameter
     * @param pAccountId
     * @return ActionErrors
     */
    private static ActionErrors searchAccountCatalogs(HttpServletRequest request,
                                                      StoreAccountConfigurationForm pForm, int pAccountId) {

        String sName = pForm.getSearchForName();
        String sType = pForm.getSearchType();
        String fieldValue = pForm.getSearchForName();
        ActionErrors ae = new ActionErrors();
        try {
            APIAccess factory = new APIAccess();
            CatalogInformation catinfo = factory.getCatalogInformationAPI();
            CatalogDataVector catalogs;

            // set results to 0
            request.getSession().setAttribute("Account.configuration.catalogs.vector", new CatalogDataVector());

            if (sType.equals("nameBegins")&&sName.length()>0) {
                catalogs = catinfo.getCatalogsCollectionByNameAndBusEntity(sName,
                        SearchCriteria.BEGINS_WITH_IGNORE_CASE,
                        pAccountId);
            } // "nameContains"
            else if ( fieldValue.length()>0 ) {
                catalogs = catinfo.getCatalogsCollectionByNameAndBusEntity(sName,
                        SearchCriteria.CONTAINS_IGNORE_CASE,
                        pAccountId);
            } else
            {
                 log.info("================GET ALL=================");
                 catalogs = catinfo.getCatalogsCollectionByBusEntity(pAccountId);
            }

            ////////////////////////////////////////////////

            if (!pForm.isShowInactiveFl()) {
                Iterator it = catalogs.iterator();
                while (it.hasNext()) {
                    CatalogData cD = (CatalogData) it.next();
                    if (cD.getCatalogStatusCd().equals("INACTIVE"))
                        it.remove();
                }
            }

            ////////////////////////////////////////////////

            // Catalogs found for this account
            request.getSession().setAttribute("Account.configuration.catalogs.vector", catalogs);


        } catch (NamingException e) {
            try {
                ae = StringUtils.getUiErrorMess(e);
            } catch (Exception e1) {
                ae.add(ActionErrors.GLOBAL_MESSAGE,
                        new ActionError("error.simpleGenericError",
                                e.getMessage()));
                e1.printStackTrace();
            }

        } catch (APIServiceAccessException e) {
            try {
                ae = StringUtils.getUiErrorMess(e);
            } catch (Exception e1) {
                ae.add(ActionErrors.GLOBAL_MESSAGE,
                        new ActionError("error.simpleGenericError",
                                e.getMessage()));
                e1.printStackTrace();
            }

        } catch (RemoteException e) {
            try {
                ae = StringUtils.getUiErrorMess(e);
            } catch (Exception e1) {
                ae.add(ActionErrors.GLOBAL_MESSAGE,
                        new ActionError("error.simpleGenericError",
                                e.getMessage()));
                e1.printStackTrace();

            }

        }
        return ae;
    }


    /**
     * searchAccountGuides, fetches the current guides tied to this account
     *
     * @param request
     * @param pForm      Description of Parameter
     * @param pAccountId
     * @return ActionErrors
     */
    private static ActionErrors searchAccountGuides(HttpServletRequest request,
                                                    StoreAccountConfigurationForm pForm, int pAccountId) {


        ActionErrors ae = new ActionErrors();
        try {
            APIAccess factory = new APIAccess();
            OrderGuide orderGuideAPI = factory.getOrderGuideAPI();
            String sName = pForm.getSearchForName();
            String sType = pForm.getSearchType();
            OrderGuideDescDataVector orderGuides;
            int ogType = OrderGuide.TYPE_TEMPLATE;
            log.info("===================SEARCH ACCOUNT GUIDES===============sName.length: "+sName.length());
             request.getSession().setAttribute("Account.configuration.guides.vector", null);

            if (sType.equals("nameBegins")&&sName.length()>0) {
                orderGuides = orderGuideAPI.getCollectionByNameAndAccount(sName,
                        pAccountId,
                        OrderGuide.BEGINS_WITH_IGNORE_CASE,
                        ogType);
            } // "nameContains"
            else if ( sName.length()>0) {
                orderGuides = orderGuideAPI.getCollectionByNameAndAccount(sName,
                        pAccountId,
                        OrderGuide.CONTAINS_IGNORE_CASE,
                        ogType);
            } else
            {
                log.info("===================get ALL===================");

                orderGuides = orderGuideAPI.getCollectionByAccount(pAccountId, ogType);

            }
            ////////////////////////////////////////////////


            if (!pForm.isShowInactiveFl()) {
                Iterator it = orderGuides.iterator();
                while (it.hasNext()) {
                    OrderGuideDescData ogD = (OrderGuideDescData) it.next();
                    if (ogD.getStatus().equals("INACTIVE") )
                        it.remove();
                }
            }

            ////////////////////////////////////////////////

            // OrderGuides found for this account
            request.getSession().setAttribute("Account.configuration.guides.vector", orderGuides);

        } catch (NamingException e) {
            try {
                ae = StringUtils.getUiErrorMess(e);
            } catch (Exception e1) {
                ae.add(ActionErrors.GLOBAL_MESSAGE,
                        new ActionError("error.simpleGenericError",
                                e.getMessage()));
                e1.printStackTrace();
            }

        } catch (APIServiceAccessException e) {
            try {
                ae = StringUtils.getUiErrorMess(e);
            } catch (Exception e1) {
                ae.add(ActionErrors.GLOBAL_MESSAGE,
                        new ActionError("error.simpleGenericError",
                                e.getMessage()));
                e1.printStackTrace();
            }

        } catch (RemoteException e) {
            try {
                ae = StringUtils.getUiErrorMess(e);
            } catch (Exception e1) {
                ae.add(ActionErrors.GLOBAL_MESSAGE,
                        new ActionError("error.simpleGenericError",
                                e.getMessage()));
                e1.printStackTrace();

            }

        }
        return ae;
    }

    /**
     * searchAccountUsers, fetches the current users tied to this account
     *
     * @param request
     * @param pAccountId
     * @return ActionErrors
     */
    private static ActionErrors searchAccountUsers(HttpServletRequest request,
                                                   StoreAccountConfigurationForm sForm, int pAccountId) {


        HttpSession session = request.getSession();
        UserDataVector uv = new UserDataVector();
        ActionErrors ae = new ActionErrors();
        // Reset the session variables.
        try {

            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            APIAccess factory = new APIAccess();
            User userBean = factory.getUserAPI();
            session.setAttribute("Account.configuration.users.vector",null);

            UserSearchCriteriaData searchCriteria = UserSearchCriteriaData.createValue();
            String fieldValue = sForm.getSearchForName();
            String fieldSearchType = sForm.getSearchType();
            int searchAccountId = pAccountId;
            searchCriteria.setUserStoreIds(Utility.toIdVector(appUser.getAssociatedStores()));
            searchCriteria.setAccountId(searchAccountId);
            searchCriteria.setFirstName(sForm.getFirstName());
            searchCriteria.setLastName(sForm.getLastName());
            searchCriteria.setUserTypeCd(sForm.getUserType());

            if (fieldSearchType.equals("id")) {
                log.info("Search type \"id\" ");
                searchCriteria.setUserId(fieldValue);
            }    else if (fieldSearchType.equals("nameBegins") && fieldValue.length()>0) {

                // Lookup by name.  Two assumptions are made: 1) user may
                // have entered the whole name or the initial part of the
                // name; 2) the search is case insensitive.
                log.info("Search type \"nameBegins\" ");
                searchCriteria.setUserName(fieldValue);
                searchCriteria.setUserNameMatch(User.NAME_BEGINS_WITH_IGNORE_CASE);
            }
            else if (fieldValue.length()>0
                    ||sForm.getFirstName().length()>0 ||sForm.getLastName().length()>0||(sForm.getUserType().length()>0)) {

                // Lookup by name.  Two assumptions are made: 1) user may
                // have entered the whole name or the initial part of the
                // name; 2) the search is case insensitive.
                log.info("Search type \"nameContains\" ");
                searchCriteria.setUserName(fieldValue);
                searchCriteria.setUserNameMatch(User.NAME_CONTAINS_IGNORE_CASE);
            } else {//getAll

                 log.info("================GET ALL=================");
                uv = userBean.getAllUsers(pAccountId, Site.ORDER_BY_NAME);

                ////////////////////////////////////////

                if (!sForm.isShowInactiveFl()) {
                    Iterator it = uv.iterator();
                    while (it.hasNext()) {
                        UserData uD = (UserData) it.next();
                        if (uD.getUserStatusCd().equals("INACTIVE") )
                            it.remove();
                    }
                }

                ////////////////////////////////////////

                // Users found for this account.
                request.getSession().setAttribute("Account.configuration.users.vector", uv);
                return ae;
            }

            //not getAll
            searchCriteria.setIncludeInactiveFl(sForm.isShowInactiveFl());
            uv = userBean.getUsersCollectionByCriteria(searchCriteria);

            // Users found for this account.
            request.getSession().setAttribute("Account.configuration.users.vector", uv);

        } catch (NamingException e) {
            try {
                ae = StringUtils.getUiErrorMess(e);
            } catch (Exception e1) {
                ae.add(ActionErrors.GLOBAL_MESSAGE,
                        new ActionError("error.simpleGenericError",
                                e.getMessage()));
                e1.printStackTrace();
            }

        } catch (APIServiceAccessException e) {
            try {
                ae = StringUtils.getUiErrorMess(e);
            } catch (Exception e1) {
                ae.add(ActionErrors.GLOBAL_MESSAGE,
                        new ActionError("error.simpleGenericError",
                                e.getMessage()));
                e1.printStackTrace();
            }

        } catch (RemoteException e) {
            try {
                ae = StringUtils.getUiErrorMess(e);
            } catch (Exception e1) {
                ae.add(ActionErrors.GLOBAL_MESSAGE,
                        new ActionError("error.simpleGenericError",
                                e.getMessage()));
                e1.printStackTrace();
            }
        }
        catch (NumberFormatException e) {
            try {
                ae = StringUtils.getUiErrorMess(e);
            } catch (Exception e1) {
                ae.add(ActionErrors.GLOBAL_ERROR,
                        new ActionError("error.invalidNumber", "Search Field"));
                e1.printStackTrace();

            }

        }

        return ae;
    }

    public static void resetShowInactive(StoreAccountConfigurationForm pForm)
    {
     pForm.setShowInactiveFl(false);
    }
}
