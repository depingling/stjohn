package com.cleanwise.view.logic;

import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.PhoneData;

import com.cleanwise.view.forms.*;
import com.cleanwise.view.utils.*;

import java.util.*;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;


/**
 *  Logic Class to find related objects.
 *
 *@author     dvieira
 *@created    August 27, 2001
 */
public class RelatedLogic {

    /**
     *  findSiteCatalog, fetches the current catalog tied to this site, and the
     *  catalogs tied to this site's account. The site can then be attached to
     *  any catalog belonging to its account.
     *
     *@param  request
     *@param  pForm
     *@param  pSiteid
     *@exception  Exception
     */
    private static void findSiteCatalog(HttpServletRequest request,
                                        RelatedForm pForm, int pSiteid)
                                 throws Exception {

        APIAccess factory = new APIAccess();
        CatalogInformation cati = factory.getCatalogInformationAPI();
        CatalogData sitecat = cati.getSiteCatalog(pSiteid);
        Site siteBean = factory.getSiteAPI();
        DistributorData siteDist = siteBean.getMajorSiteDist(pSiteid);

        // A bit goofy perhaps, since there is one at most, but to be
        // consistent, apply filtering
        String sType = pForm.getSearchType();
        boolean matches = true;

        if (sType.equals("nameBegins")) {

            String sName = pForm.getSearchForName().toUpperCase();
            matches = sName.startsWith(sitecat.getShortDesc().toUpperCase());
        } else if (sType.equals("nameContains")) {

            String sName = pForm.getSearchForName().toUpperCase();

            if (sName.indexOf(sitecat.getShortDesc().toUpperCase()) < 0) {
                matches = false;
            }
        }

        if (matches) {
            request.getSession().setAttribute("Related.site.catalog", sitecat);
            request.getSession().setAttribute("Related.site.distributor", siteDist);
        }
    }

    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pSiteid        Description of Parameter
     *@exception  Exception  Description of Exception
     */
    private static void findSiteOrderGuides(HttpServletRequest request,
                                            int pSiteid)
                                     throws Exception {

        APIAccess factory = new APIAccess();
        OrderGuide ogBean = factory.getOrderGuideAPI();
        OrderGuideDescDataVector ogv = ogBean.getCollectionBySite
            (pSiteid,OrderGuide.TYPE_ADMIN_RELATED);
        request.getSession().setAttribute("Related.site.orderguide.vector",
                                          ogv);
    }

    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pCatid         Description of Parameter
     *@exception  Exception  Description of Exception
     */
    private static void findCatalogSites(HttpServletRequest request,
                                         RelatedForm pForm, int pCatid)
                                  throws Exception {

        String sName = pForm.getSearchForName();
        String sType = pForm.getSearchType();
        APIAccess factory = new APIAccess();
        CatalogInformation cati = factory.getCatalogInformationAPI();
        SiteDataVector sitecatv;

        if (sType.equals("viewall")) {
            sitecatv = cati.getSiteCollection(pCatid, null, 0,
                                              SearchCriteria.ORDER_BY_SHORT_DESC);
        } else if (sType.equals("nameBegins")) {
            sitecatv = cati.getSiteCollection(pCatid, sName,
                                              SearchCriteria.BEGINS_WITH_IGNORE_CASE,
                                              SearchCriteria.ORDER_BY_SHORT_DESC);
        } // "nameContains"
        else {
            sitecatv = cati.getSiteCollection(pCatid, sName,
                                              SearchCriteria.CONTAINS_IGNORE_CASE,
                                              SearchCriteria.ORDER_BY_SHORT_DESC);
        }

        request.getSession().setAttribute("Related.site.vector", sitecatv);
    }

    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pCatid         Description of Parameter
     *@exception  Exception  Description of Exception
     */
    private static void findCatalogUsers(HttpServletRequest request,
                                         int pCatid)
                                  throws Exception {

        APIAccess factory = new APIAccess();
        CatalogInformation cati = factory.getCatalogInformationAPI();
        UserDataVector v = cati.getUserCollection(pCatid);
        request.getSession().setAttribute("Related.users.vector", v);
    }

    /**
     *  Fetch the contracts attached to this site's catalog.
     *
     *@param  request
     *@param  pSiteid
     *@exception  Exception
     */
    private static void findSiteContracts(HttpServletRequest request,
                                          RelatedForm pForm, int pSiteid)
                                   throws Exception {

        String sName = pForm.getSearchForName();
        String sType = pForm.getSearchType();
        APIAccess factory = new APIAccess();
        CatalogInformation cati = factory.getCatalogInformationAPI();
        CatalogData sitecat = cati.getSiteCatalog(pSiteid);
        Contract coni = factory.getContractAPI();
        ContractDescDataVector cdv = null;

        if (sType.equals("viewall")) {
            cdv = coni.getContractDescsByCatalog(sitecat.getCatalogId());
        } else if (sType.equals("nameBegins")) {
            cdv = coni.getContractDescsByCatalog(sName,
                                                 Contract.BEGINS_WITH_IGNORE_CASE,
                                                 sitecat.getCatalogId());
        } // "nameContains"
        else {
            cdv = coni.getContractDescsByCatalog(sName,
                                                 Contract.CONTAINS_IGNORE_CASE,
                                                 sitecat.getCatalogId());
        }

        // Contracts found for this site.
        request.getSession().setAttribute("Related.site.contract.vector", cdv);
    }

    /**
     *  Fetch the users tied to this site.
     *
     *@param  request
     *@param  sForm
     *@param  pSiteid
     *@exception  Exception
     */
    private static void findSiteUsers(HttpServletRequest request,
                                      RelatedForm pForm, int pSiteid)
                               throws Exception {

        String sName = pForm.getSearchForName();
        String sType = pForm.getSearchType();
        APIAccess factory = new APIAccess();
        User usersi = factory.getUserAPI();
        UserDataVector uv = null;

        if (sType.equals("viewall")) {
            uv = usersi.getUsersCollectionByBusEntity(pSiteid, null);
        } else if (sType.equals("nameBegins")) {
            uv = usersi.getUsersCollectionByBusEntity(pSiteid, sName,
                                                      User.NAME_BEGINS_WITH_IGNORE_CASE,
                                                      User.ORDER_BY_NAME);
        } // "nameContains"
        else {
            uv = usersi.getUsersCollectionByBusEntity(pSiteid, sName,
                                                      User.NAME_CONTAINS_IGNORE_CASE,
                                                      User.ORDER_BY_NAME);
        }

        request.getSession().setAttribute("Related.users.vector", uv);
    }

    /**
     *  Fetch distributor delivery schedules for the site
     *
     *@param  request
     *@param  pSiteid
     *@exception  Exception
     */
    private static void findSiteSchedules(HttpServletRequest request,
                                          RelatedForm pForm, int pSiteId)
                                   throws Exception {

        String sName = pForm.getSearchForName();
        String sType = pForm.getSearchType();
        APIAccess factory = new APIAccess();
        Distributor distEjb = factory.getDistributorAPI();
        Hashtable critParams = new Hashtable();
        critParams.put("SITE_ID", ""+pSiteId);
        boolean beginsWithFl = (sType.equals("nameBegins"))? true:false;

        if (!sType.equals("viewall")) {
           critParams.put("DISTRIBUTOR", sName);
        }
        DeliveryScheduleViewVector dsVwV =
           distEjb.getDeliverySchedules(0, critParams,beginsWithFl);
        // Contracts found for this site.
        request.getSession().setAttribute("Related.site.distschedules.vector", dsVwV);
    }

    /**
     *  findStoreCatalogs, fetches the current catalogs tied to this store
     *
     *@param  pStoreid
     *@param  request
     *@param  pForm          Description of Parameter
     *@exception  Exception
     */
    private static void findStoreCatalogs(HttpServletRequest request,
                                          RelatedForm pForm, int pStoreid)
                                   throws Exception {

        String sName = pForm.getSearchForName();
        String sType = pForm.getSearchType();
        APIAccess factory = new APIAccess();
        CatalogInformation catinfo = factory.getCatalogInformationAPI();
        CatalogDataVector catalogs;

        if (sType.equals("viewall")) {
            catalogs = catinfo.getCatalogsCollectionByBusEntity(pStoreid);
        } else if (sType.equals("nameBegins")) {
            catalogs = catinfo.getCatalogsCollectionByNameAndBusEntity(sName,
                                                                       SearchCriteria.BEGINS_WITH_IGNORE_CASE,
                                                                       pStoreid);
        } // "nameContains"
        else {
            catalogs = catinfo.getCatalogsCollectionByNameAndBusEntity(sName,
                                                                       SearchCriteria.CONTAINS_IGNORE_CASE,
                                                                       pStoreid);
        }

        // Catalogs found for this store
        request.getSession().setAttribute("Related.store.catalog.vector",
                                          catalogs);
    }

    /**
     *  findStoreContracts, fetches the current contracts tied to this store
     *
     *@param  request
     *@param  pStoreid       Description of Parameter
     *@exception  Exception
     */
    private static void findStoreContracts(HttpServletRequest request,
                                           int pStoreid)
                                    throws Exception {

        APIAccess factory = new APIAccess();
        CatalogInformation cati = factory.getCatalogInformationAPI();
        Contract contractEjb = factory.getContractAPI();
        ContractDescDataVector finalcontractv = new ContractDescDataVector();
        CatalogDataVector storecatv = cati.getCatalogsCollectionByBusEntity(
                                              pStoreid);
        int contractid = 0;

        for (int j = 0; j < storecatv.size(); j++) {

            int catid = 0;
            CatalogData cd = (CatalogData)storecatv.get(j);
            catid = cd.getCatalogId();

            ContractDescDataVector contractv = contractEjb.getContractDescsByCatalog(
                                                       catid);

            for (int k = 0; k < contractv.size(); k++) {

                ContractDescData contract = (ContractDescData)contractv.get(k);
                finalcontractv.add(contract);
                contractid = contract.getContractId();
            }
        }

        // Catalogs found for this store.
        request.getSession().setAttribute("Related.store.contract.id",
                                          String.valueOf(contractid));
        request.getSession().setAttribute("Related.store.contract.vector",
                                          finalcontractv);
    }

    /**
     *  findStoreAccounts, fetches the current accounts tied to this store
     *
     *@param  request
     *@param  pForm
     *@param  pStoreid       Description of Parameter
     *@exception  Exception
     */
    private static void findStoreAccounts(HttpServletRequest request,
                                          RelatedForm pForm, int pStoreid)
                                   throws Exception {

        String sName = pForm.getSearchForName();
        String sType = pForm.getSearchType();
        APIAccess factory = new APIAccess();
        Account acci = factory.getAccountAPI();
        AccountDataVector storeaccv;

        if (sType.equals("viewall")) {
            storeaccv = acci.getAllAccounts(pStoreid, Account.ORDER_BY_NAME);
        } else if (sType.equals("nameBegins")) {
            storeaccv = acci.getAccountByName(sName, pStoreid,
                                              Account.BEGINS_WITH_IGNORE_CASE,
                                              Account.ORDER_BY_NAME);
        } // "nameContains"
        else {
            storeaccv = acci.getAccountByName(sName, pStoreid,
                                              Account.CONTAINS_IGNORE_CASE,
                                              Account.ORDER_BY_NAME);
        }

        // Accounts found for this store
        request.getSession().setAttribute("Related.store.account.vector",
                                          storeaccv);
    }

    /**
     *  findStoreSites, fetches the current sites tied to this store
     *
     *@param  request
     *@param  pStoreid       Description of Parameter
     *@exception  Exception
     */
    private static void findStoreSites(HttpServletRequest request,
                                       int pStoreid)
                                throws Exception {

        APIAccess factory = new APIAccess();
        Account accounti = factory.getAccountAPI();
        Site sitei = factory.getSiteAPI();
        AccountDataVector storeaccountv = accounti.getAllAccounts(pStoreid,
                                                                  Account.ORDER_BY_NAME);
        SiteDataVector finalsitev = new SiteDataVector();

        for (int i = 0; i < storeaccountv.size(); i++) {

            AccountData ad = (AccountData)storeaccountv.get(i);
            int accid = 0;
            accid = ad.getBusEntity().getBusEntityId();

            SiteDataVector storesitev = sitei.getAllSites(accid,
                                                          Site.ORDER_BY_NAME,
                 SessionTool.getCategoryToCostCenterViewByAcc(request.getSession(), accid));

            for (int j = 0; j < storesitev.size(); j++) {
                finalsitev.add(storesitev.get(j));
            }
        }

        int siteid = 0;

        for (int j = 0; j < finalsitev.size(); j++) {

            SiteData sd = (SiteData)finalsitev.get(j);
            siteid = sd.getBusEntity().getBusEntityId();
        }

        // Sites found for this store.
        request.getSession().setAttribute("Related.store.site.id",
                                          String.valueOf(siteid));
        request.getSession().setAttribute("Related.store.site.vector",
                                          finalsitev);
    }

    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pForm        Description of Parameter
     *@param  pStoreid       Description of Parameter
     *@exception  Exception  Description of Exception
     */
    private static void findStoreUsers(HttpServletRequest request,
                                       RelatedForm pForm, int pStoreid)
                                throws Exception {

        String sName = pForm.getSearchForName();
        String sType = pForm.getSearchType();
        APIAccess factory = new APIAccess();
        User userBean = factory.getUserAPI();
        UserDataVector udv = new UserDataVector();

        // clear results
        request.getSession().setAttribute("Related.store.user.vector", udv);

        if (sType.equals("viewall")) {
            udv = userBean.getAllStoreUsers(pStoreid, User.ORDER_BY_NAME);
        } else if (sType.equals("nameBegins")) {
            udv = userBean.getStoreUsersByName(sName, pStoreid,
                                               User.NAME_BEGINS_WITH_IGNORE_CASE,
                                               User.ORDER_BY_NAME);
        } // "nameContains"
        else {
            udv = userBean.getStoreUsersByName(sName, pStoreid,
                                               User.NAME_CONTAINS_IGNORE_CASE,
                                               User.ORDER_BY_NAME);
        }

        // Users found for this store
        request.getSession().setAttribute("Related.store.user.vector", udv);
    }

    private static void findContractSites(HttpServletRequest request,
                                          RelatedForm pForm, int pContractid)
                                   throws Exception {

        String sName = pForm.getSearchForName();
        String sType = pForm.getSearchType();
        APIAccess factory = new APIAccess();
        Contract contracti = factory.getContractAPI();
        CatalogInformation cati = factory.getCatalogInformationAPI();
        ContractData contract = contracti.getContract(pContractid);
        SiteDataVector sites;

        if (sType.equals("viewall")) {
            sites = cati.getSiteCollection(contract.getCatalogId(), null, 0,
                                           SearchCriteria.ORDER_BY_SHORT_DESC);
        } else if (sType.equals("nameBegins")) {
            sites = cati.getSiteCollection(contract.getCatalogId(), sName,
                                           SearchCriteria.BEGINS_WITH_IGNORE_CASE,
                                           SearchCriteria.ORDER_BY_SHORT_DESC);
        } // "nameContains"
        else {
            sites = cati.getSiteCollection(contract.getCatalogId(), sName,
                                           SearchCriteria.CONTAINS_IGNORE_CASE,
                                           SearchCriteria.ORDER_BY_SHORT_DESC);
        }

        request.getSession().setAttribute("Related.contract.site.vector",
                                          sites);
    }

    private static void findContractAccounts(HttpServletRequest request,
                                             RelatedForm pForm,
                                             int pContractid)
                                      throws Exception {

        String sName = pForm.getSearchForName();
        String sType = pForm.getSearchType();
        APIAccess factory = new APIAccess();
        Contract contracti = factory.getContractAPI();
        CatalogInformation cati = factory.getCatalogInformationAPI();
        ContractData contract = contracti.getContract(pContractid);
        AccountDataVector accounts;

        if (sType.equals("viewall")) {
            accounts = cati.getAccountCollection(contract.getCatalogId(), null,
                                                 0,
                                                 SearchCriteria.ORDER_BY_SHORT_DESC);
        } else if (sType.equals("nameBegins")) {
            accounts = cati.getAccountCollection(contract.getCatalogId(),
                                                 sName,
                                                 SearchCriteria.BEGINS_WITH_IGNORE_CASE,
                                                 SearchCriteria.ORDER_BY_SHORT_DESC);
        } // "nameContains"
        else {
            accounts = cati.getAccountCollection(contract.getCatalogId(),
                                                 sName,
                                                 SearchCriteria.CONTAINS_IGNORE_CASE,
                                                 SearchCriteria.ORDER_BY_SHORT_DESC);
        }

        request.getSession().setAttribute("Related.contract.account.vector",
                                          accounts);
    }

    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pContractId   Description of Parameter
     *@exception  Exception  Description of Exception
     */
    private static void findContractOrderGuides(HttpServletRequest request,
                                                int pContractid)
                                         throws Exception {

        APIAccess factory = new APIAccess();
        Contract contracti = factory.getContractAPI();
        CatalogInformation cati = factory.getCatalogInformationAPI();
        ContractData contract = contracti.getContract(pContractid);
        OrderGuide ogBean = factory.getOrderGuideAPI();
        OrderGuideDescDataVector v = ogBean.getCollectionByCatalog(contract.getCatalogId(),
                                                                   OrderGuide.TYPE_TEMPLATE);
        request.getSession().setAttribute("Related.contract.orderguide.vector",
                                          v);
    }

    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pContractid    Description of Parameter
     *@exception  Exception  Description of Exception
     */
    private static void findContractUsers(HttpServletRequest request,
                                          RelatedForm pForm, int pContractid)
                                   throws Exception {

        APIAccess factory = new APIAccess();
        String sName = pForm.getSearchForName();
        String sType = pForm.getSearchType();
        Contract contracti = factory.getContractAPI();
        ContractData contract = contracti.getContract(pContractid);
        CatalogInformation cati = factory.getCatalogInformationAPI();
        SiteDataVector sites;
        sites = cati.getSiteCollection(contract.getCatalogId(), null, 0,
                                       SearchCriteria.ORDER_BY_SHORT_DESC);

        User usersi = factory.getUserAPI();
        UserDataVector uv = new UserDataVector();
        IdVector siteIds = new IdVector();
        for (int ii = 0; ii < sites.size(); ii++) {
           SiteData sd = (SiteData)sites.get(ii);
           int siteId = sd.getBusEntity().getBusEntityId();
           siteIds.add(new Integer(siteId));
        }

        if (sType.equals("viewall")) {
           uv = usersi.getUsersCollectionByBusEntityCollection(siteIds, null);
        } else if (sType.equals("nameBegins")) {
           uv = usersi.getUsersCollectionByBusEntityCollection(siteIds, sName,
                                                            User.NAME_BEGINS_WITH_IGNORE_CASE,
                                                            User.ORDER_BY_NAME);
        } // "nameContains"
          else {
           uv = usersi.getUsersCollectionByBusEntityCollection(siteIds, sName,
                                                            User.NAME_CONTAINS_IGNORE_CASE,
                                                            User.ORDER_BY_NAME);
        }

        request.getSession().setAttribute("Related.contract.user.vector", uv);
    }

    /**
     *  findUserCatalogs, fetches the current catalog tied to this user, and the
     *  catalogs tied to this user's account.
     *
     *@param  pUserid
     *@param  request
     *@exception  Exception
     */
    private static ActionErrors findUserCatalogs(HttpServletRequest request,
                                         RelatedForm pForm, int pUserid)
                                  throws Exception {
        ActionErrors ae = new ActionErrors();
        APIAccess factory = new APIAccess();
        CatalogInformation cati = factory.getCatalogInformationAPI();
        String sType = pForm.getSearchType();
        CatalogDataVector usercatv = new CatalogDataVector();
        User useri = factory.getUserAPI();
        SiteDataVector finalsitev = new SiteDataVector();
        finalsitev = useri.getSiteDescCollection(pUserid, "");

        IdVector stored = new IdVector();

        for (int i = 0; i < finalsitev.size(); i++) {

            SiteData sd = (SiteData)finalsitev.get(i);
            int siteId = sd.getBusEntity().getBusEntityId();
            CatalogData sitecat = cati.getSiteCatalog(siteId);
            if(sitecat==null) {
              ae.add("error",new ActionError("error.simpleGenericError","No catalog for site: "+siteId));
              return ae;
            }
            boolean matches = false;

            if (sType.equals("viewall")) {
                matches = true;
            } else if (sType.equals("nameBegins")) {

                String sName = pForm.getSearchForName().toUpperCase();
                matches = sitecat.getShortDesc().toUpperCase().startsWith(
                                  sName);
            } // "nameContains"
            else {

                String sName = pForm.getSearchForName().toUpperCase();

                if (sName == "") {
                    matches = true;
                } else if (sitecat.getShortDesc().toUpperCase().indexOf(sName) >= 0) {
                    matches = true;
                }
            }

            //we only want to add the catalog once, so we check to see if its there
            for (int j = 0; j < stored.size(); j++) {

                Integer id = (Integer)stored.get(j);
                Integer catId = new Integer(sitecat.getCatalogId());

                if (catId.equals(id)) {
                    matches = false;

                    break;
                }
            }

            // if this isn't a duplicate entry, we can add it
            if (matches) {
                usercatv.add(sitecat);
                stored.add(new Integer(sitecat.getCatalogId()));
            }
        }

        request.getSession().setAttribute("Related.user.catalog.vector",
                                          usercatv);
        return ae;
    }

    /**
     *  findUserContracts, fetches the contracts tied to this user
     *
     *@param  pUserId
     *@param  request
     *@param  pForm          Description of Parameter
     *@exception  Exception
     */
    private static ActionErrors findUserContracts(HttpServletRequest request,
                                          RelatedForm pForm, int pUserId)
                                   throws Exception {

        ActionErrors ae = new ActionErrors();
        String sName = pForm.getSearchForName();
        String sType = pForm.getSearchType();
        APIAccess factory = new APIAccess();
        ContractInformation contractInfoAPI = factory.getContractInformationAPI();
        CatalogInformation cati = factory.getCatalogInformationAPI();
        Contract coni = factory.getContractAPI();
        ContractDescDataVector contracts = new ContractDescDataVector();
        User useri = factory.getUserAPI();
        SiteDataVector finalsitev = new SiteDataVector();
        finalsitev = useri.getSiteDescCollection(pUserId, "");

        ContractDescDataVector cdv = new ContractDescDataVector();

        for (int i = 0; i < finalsitev.size(); i++) {

            SiteData sd = (SiteData)finalsitev.get(i);
            int siteId = sd.getBusEntity().getBusEntityId();
            CatalogData sitecat = cati.getSiteCatalog(siteId);
            if(sitecat==null) {
              ae.add("error",new ActionError("error.simpleGenericError","No catalog for site: "+siteId));
              return ae;
            }
            if (sType.equals("viewall")) {
                cdv = coni.getContractDescsByCatalog(sitecat.getCatalogId());
            } else if (sType.equals("nameBegins")) {
                cdv = coni.getContractDescsByCatalog(sName,
                                                     Contract.BEGINS_WITH_IGNORE_CASE,
                                                     sitecat.getCatalogId());
            } // "nameContains"
            else {
                cdv = coni.getContractDescsByCatalog(sName,
                                                     Contract.CONTAINS_IGNORE_CASE,
                                                     sitecat.getCatalogId());
            }

            contracts.addAll(cdv);
        }

        // Contracts found for this account
        request.getSession().setAttribute("Related.user.contract.vector",
                                          contracts);
        return ae;
    }

    /**
     *  Return the order guides for a particular user, including
     *  buyer order guides for the user, as well as template order
     *  guides valid for the user's catalog.
     *
     *@param  request        Description of Parameter
     *@param  pUserid        Description of Parameter
     *@exception  Exception  Description of Exception
     */
    private static void findUserOrderGuides(HttpServletRequest request,
                                            int pUserid)
                                     throws Exception {

        APIAccess factory = new APIAccess();
        OrderGuide ogBean = factory.getOrderGuideAPI();

        // First get the buyer order guides for this user.
        OrderGuideDescDataVector ogv = ogBean.getCollectionByUser(pUserid,
                                                                  OrderGuide.TYPE_BUYER);

        // Then add the order guide templates the user has access to.
        ogv.addAll(ogBean.getTemplateCollectionByUser(pUserid));
        request.getSession().setAttribute("Related.user.orderguide.vector",
                                          ogv);
    }

    /**
     *  findUserSites, fetches the current sites tied to this user
     *
     *@param  pUserId
     *@param  request
     *@exception  Exception
     */
    public static SiteDataVector findUserSites(HttpServletRequest request,
                                               int pUserid)
                                        throws Exception {

        APIAccess factory = new APIAccess();
        User useri = factory.getUserAPI();
        SiteDataVector finalsitev = new SiteDataVector();
        finalsitev = useri.getSiteDescCollection(pUserid, "");

        // Users found for this store.
        request.getSession().setAttribute("Related.user.site.vector",
                                          finalsitev);

        return finalsitev;
    }

    public static SiteViewVector findUserSites2(HttpServletRequest request,
                                                int pUserid, ActionForm pForm)
                                         throws Exception {

        APIAccess factory = new APIAccess();
        Site sbean = factory.getSiteAPI();
        SiteViewVector sitev = new SiteViewVector();
        QueryRequest req = new QueryRequest();
        req.filterByUserId(pUserid);
        req.filterBySiteStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        req.orderBySiteName(true);

        if (null != pForm) {

            SiteMgrSearchForm sForm = (SiteMgrSearchForm)pForm;
            String fieldSearchType = sForm.getSearchType();
            String fieldValue = sForm.getSearchField();
            fieldValue = fieldValue.trim();

            String f = "";

            if (fieldSearchType.equals("nameBegins") &&
                fieldValue.length() > 0) {
                req.filterBySiteName(fieldValue,
                                     QueryRequest.BEGINS_IGNORE_CASE);
            } else if (fieldSearchType.equals("nameContains") &&
                       fieldValue.length() > 0) {
                req.filterBySiteName(fieldValue,
                                     QueryRequest.CONTAINS_IGNORE_CASE);
            } else {
            	req.filterBySiteName(fieldValue,
                        QueryRequest.CONTAINS_IGNORE_CASE);
            }

            f = sForm.getCity().trim();

            if (sForm.getCity().trim().length() > 0) {
                req.filterByCity(f, QueryRequest.BEGINS_IGNORE_CASE);
            }

            f = sForm.getState().trim();

            if (sForm.getState().trim().length() > 0) {
                req.filterByState(f, QueryRequest.BEGINS_IGNORE_CASE);
            }

            f = sForm.getCountry().trim();

            if (sForm.getCountry().trim().length() > 0) {
            	if(f.equalsIgnoreCase("USA")){
            		f = "United States";
            	}
                req.filterByCountry(f, QueryRequest.BEGINS_IGNORE_CASE);
            }

            f = sForm.getPostalCode().trim();

            if (sForm.getPostalCode().trim().length() > 0) {
                req.filterByZip(f, QueryRequest.EXACT);
            }
        }

        sitev = sbean.getSiteCollection(req);

        // Users found for this store.
        request.getSession().setAttribute("Related.user.site.view.vector",
                                          sitev);

        return sitev;
    }

    /**
     *  findAccountUsers, fetches the current users tied to this account
     *
     *@param  pAccountId
     *@param  request
     *@exception  Exception
     */
    private static void findAccountUsers(HttpServletRequest request,
                                         RelatedForm sForm, int pAccountId)
                                  throws Exception {

        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        User userBean = factory.getUserAPI();
        UserDataVector uv = new UserDataVector();

        // Reset the session variables.
        session.setAttribute("Related.users.vector", uv);

        UserSearchCriteriaData searchCriteria = UserSearchCriteriaData.createValue();
        String fieldValue = sForm.getSearchForName();
        String fieldSearchType = sForm.getSearchType();
        int searchAccountId = pAccountId;
        searchCriteria.setAccountId(searchAccountId);
        searchCriteria.setFirstName(sForm.getFirstName());
        searchCriteria.setLastName(sForm.getLastName());
        searchCriteria.setUserTypeCd(sForm.getUserType());

        if (fieldSearchType.equals("viewall")) {
            uv = userBean.getAllUsers(pAccountId, Site.ORDER_BY_NAME);
	    // Users found for this account.
	    request.getSession().setAttribute("Related.users.vector", uv);
	    return;
        } else if (fieldSearchType.equals("id")) {
            searchCriteria.setUserId(fieldValue);
        } else if (fieldSearchType.equals("nameContains")) {

            // Lookup by name.  Two assumptions are made: 1) user may
            // have entered the whole name or the initial part of the
            // name; 2) the search is case insensitive.
            searchCriteria.setUserName(fieldValue);
            searchCriteria.setUserNameMatch(User.NAME_CONTAINS_IGNORE_CASE);
        } else if (fieldSearchType.equals("nameBegins")) {

            // Lookup by name.  Two assumptions are made: 1) user may
            // have entered the whole name or the initial part of the
            // name; 2) the search is case insensitive.
            searchCriteria.setUserName(fieldValue);
            searchCriteria.setUserNameMatch(User.NAME_BEGINS_WITH_IGNORE_CASE);
        }

        uv = userBean.getUsersCollectionByCriteria(searchCriteria);

        // Users found for this account.
        request.getSession().setAttribute("Related.users.vector", uv);
    }

    /**
     *  findAccountSites, fetches the current sites tied to this account
     *
     *@param  pAccountId
     *@param  request
     *@param  pForm          Description of Parameter
     *@exception  Exception
     */
    private static void findAccountSites(HttpServletRequest request,
                                         RelatedForm pForm, int pAccountId)
                                  throws Exception {

        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        String fieldValue = pForm.getSearchForName();
        String searchType = pForm.getSearchType();
        QueryRequest qr = new QueryRequest();
        SiteDataVector sdv = new SiteDataVector();
        SiteViewVector dv = new SiteViewVector();

        // set results to 0
        request.getSession().setAttribute("Related.sites.vector", sdv);

        if (searchType.equals("viewall")) {
            sdv = siteBean.getAllSites(pAccountId, Site.ORDER_BY_NAME, SessionTool.getCategoryToCostCenterViewByAcc(request.getSession(), pAccountId));
        } else if (searchType.equals("id") && fieldValue.length() > 0) {

            Integer id = new Integer(fieldValue);
            qr.filterBySiteId(id.intValue());
            dv = siteBean.getSiteCollection(qr);
        } else {
            qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);

            String f = "";

            if (searchType.equals("nameBegins") && fieldValue.length() > 0) {
                qr.filterBySiteName(fieldValue,
                                    QueryRequest.BEGINS_IGNORE_CASE);
            } else if (searchType.equals("nameContains") &&
                       fieldValue.length() > 0) {
                qr.filterBySiteName(fieldValue,
                                    QueryRequest.CONTAINS_IGNORE_CASE);
            }

            f = pForm.getCity().trim();

            if (f.length() > 0) {
                qr.filterByCity(f, QueryRequest.BEGINS_IGNORE_CASE);
            }

            f = pForm.getState().trim();

            if (f.length() > 0) {
                qr.filterByState(f, QueryRequest.BEGINS_IGNORE_CASE);
            }
        }

        if (sdv.size() == 0) { //if not getAll
            qr.filterByAccountId(pAccountId);
            dv = siteBean.getSiteCollection(qr);

            for (int i = 0; i < dv.size(); i++) {

                SiteView sv = (SiteView)dv.get(i);
                SiteData sd = siteBean.getSite(sv.getId(), sv.getAccountId(),false, SessionTool.getCategoryToCostCenterView(request.getSession(), sv.getId()));
                sdv.add(sd);
            }
        }

        // Sites found for this account
        request.getSession().setAttribute("Related.sites.vector", sdv);
    }

    /**
     *  findAccountCatalogs, fetches the current catalogs tied to this account
     *
     *@param  pAccountId
     *@param  request
     *@param  pForm          Description of Parameter
     *@exception  Exception
     */
    private static void findAccountCatalogs(HttpServletRequest request,
                                            RelatedForm pForm, int pAccountId)
                                     throws Exception {

        String sName = pForm.getSearchForName();
        String sType = pForm.getSearchType();
        APIAccess factory = new APIAccess();
        CatalogInformation catinfo = factory.getCatalogInformationAPI();
        CatalogDataVector catalogs;

        if (sType.equals("viewall")) {
            catalogs = catinfo.getCatalogsCollectionByBusEntity(pAccountId);
        } else if (sType.equals("nameBegins")) {
            catalogs = catinfo.getCatalogsCollectionByNameAndBusEntity(sName,
                                                                       SearchCriteria.BEGINS_WITH_IGNORE_CASE,
                                                                       pAccountId);
        } // "nameContains"
        else {
            catalogs = catinfo.getCatalogsCollectionByNameAndBusEntity(sName,
                                                                       SearchCriteria.CONTAINS_IGNORE_CASE,
                                                                       pAccountId);
        }

        // Catalogs found for this account
        request.getSession().setAttribute("Related.catalogs.vector", catalogs);
    }

    /**
     *  findAccountContracts, fetches the contracts tied to this account
     *
     *@param  pAccountId
     *@param  request
     *@param  pForm          Description of Parameter
     *@exception  Exception
     */
    private static void findAccountContracts(HttpServletRequest request,
                                             RelatedForm pForm, int pAccountId)
                                      throws Exception {

        String sName = pForm.getSearchForName();
        String sType = pForm.getSearchType();
        APIAccess factory = new APIAccess();
        Contract contractAPI = factory.getContractAPI();
        ContractDescDataVector contracts;

        if (sType.equals("viewall")) {
            contracts = contractAPI.getContractDescsByAccount(pAccountId);
        } else if (sType.equals("nameBegins")) {
            contracts = contractAPI.getContractDescsByAccount(sName,
                                                              Contract.BEGINS_WITH_IGNORE_CASE,
                                                              pAccountId);
        } // "nameContains"
        else {
            contracts = contractAPI.getContractDescsByAccount(sName,
                                                              Contract.CONTAINS_IGNORE_CASE,
                                                              pAccountId);
        }

        // Contracts found for this account
        request.getSession().setAttribute("Related.contracts.vector",
                                          contracts);
    }

    /**
     *  findAccountGuides, fetches the current guides tied to this account
     *
     *@param  pAccountId
     *@param  request
     *@param  pForm          Description of Parameter
     *@exception  Exception
     */
    private static void findAccountGuides(HttpServletRequest request,
                                          RelatedForm pForm, int pAccountId)
                                   throws Exception {

        APIAccess factory = new APIAccess();
        OrderGuide orderGuideAPI = factory.getOrderGuideAPI();
        String sName = pForm.getSearchForName();
        String sType = pForm.getSearchType();
        OrderGuideDescDataVector orderGuides;
        int ogType = OrderGuide.TYPE_TEMPLATE;

        if (sType.equals("viewall")) {
            orderGuides = orderGuideAPI.getCollectionByAccount(pAccountId,
                                                               ogType);
        } else if (sType.equals("nameBegins")) {
            orderGuides = orderGuideAPI.getCollectionByNameAndAccount(sName,
                                                                      pAccountId,
                                                                      OrderGuide.BEGINS_WITH_IGNORE_CASE,
                                                                      ogType);
        } // "nameContains"
        else {
            orderGuides = orderGuideAPI.getCollectionByNameAndAccount(sName,
                                                                      pAccountId,
                                                                      OrderGuide.CONTAINS_IGNORE_CASE,
                                                                      ogType);
        }

        // OrderGuides found for this account
        request.getSession().setAttribute("Related.guides.vector", orderGuides);
    }

    /**
     *  Find objects (users | catalogs | contracts) related to this site.
     *
     *@param  sForm          Related search form.
     *@param  request
     *@exception  Exception
     */
    public static void findSiteInfo(RelatedForm sForm,
                                    HttpServletRequest request)
                             throws Exception {

        String sType = sForm.getSearchForType();
        String siteid = (String)request.getSession().getAttribute("Site.id");

        if (null == siteid) {

            return;
        }

        int isite = Integer.parseInt(siteid);

        // Get the Catalog for attached to this site.
        if (sType.equals("catalogs")) {
            findSiteCatalog(request, sForm, isite);

            return;
        }
        // Using the catalog, find the contracts.
        else if (sType.equals("contracts")) {
            findSiteContracts(request, sForm, isite);

            return;
        }
        // Find the order guides tied to the users
        // for this site.
        else if (sType.equals("orderguides")) {
            findSiteOrderGuides(request, isite);

            return;
        }
        // Find the users for this site.
        else if (sType.equals("users")) {
            findSiteUsers(request, sForm, isite);

            return;
        }
        // Find distributor delivery schedules form the site
        else if (sType.equals("distschedules")) {
            findSiteSchedules(request, sForm, isite);

            return;
        }
        return;
    }

    /**
     *  Find objects (account | catalog | contracts | users | sites) related to
     *  this order guide.
     *
     *@param  sForm          Related search form.
     *@param  request
     *@exception  Exception
     */
    public static void findOrderGuideInfo(RelatedForm sForm,
                                          HttpServletRequest request)
                                   throws Exception {

        String sType = sForm.getSearchForType();
        OrderGuidesMgrDetailForm ogForm = (OrderGuidesMgrDetailForm)request.getSession()
               .getAttribute("ORDER_GUIDES_DETAIL_FORM");

        if (null == ogForm) {

            return;
        }

        OrderGuideInfoData ogid = (OrderGuideInfoData)ogForm.getOrderGuideInfoData();

        if (sType.equals("sites")) {
            findCatalogSites(request, sForm,
                             ogid.getOrderGuideData().getCatalogId());

            return;
        } else if (sType.equals("users")) {
            findCatalogUsers(request, ogid.getOrderGuideData().getCatalogId());

            return;
        } else if (sType.equals("accounts")) {

            APIAccess factory = new APIAccess();
            CatalogInformation catalogIBean = factory.getCatalogInformationAPI();
            CatalogData catalog = catalogIBean.getCatalog(ogid.getOrderGuideData().getCatalogId());
            findCatalogAccounts(request, sForm, catalog);

            return;
        }

        return;
    }

    /**
     *  Find objects related to this store.
     *
     *@param  sForm          Related search form.
     *@param  request
     *@exception  Exception
     */
    public static void findStoreInfo(RelatedForm sForm,
                                     HttpServletRequest request)
                              throws Exception {

        String sType = sForm.getSearchForType();
        StoreMgrDetailForm storeForm = (StoreMgrDetailForm)request.getSession()
               .getAttribute("STORE_DETAIL_FORM");

        if (null == storeForm) {

            return;
        }

        String storeid = (String)storeForm.getId();

        if (null == storeid) {

            return;
        }

        int istore = Integer.parseInt(storeid);

        // Get the Catalog for attached to this store.
        // Get the catalogs attached to this store.
        if (sType.equals("catalogs")) {
            findStoreCatalogs(request, sForm, istore);

            return;
        }
        // Get the Account for attached to this store.
        // Get the accounts attached to this store.
        else if (sType.equals("accounts")) {
            findStoreAccounts(request, sForm, istore);

            return;
        }
        // Get the Contract for attached to this store.
        // Get the contracts attached to this store.
        else if (sType.equals("contracts")) {
            findStoreContracts(request, istore);

            return;
        }
        // Get the sites attached to this store.
        else if (sType.equals("sites")) {
            findStoreSites(request, istore);

            return;
        }
        // Get the users attached to this store.
        else if (sType.equals("users")) {
            findStoreUsers(request, sForm, istore);

            return;
        }

        return;
    }

    /**
     *  Find objects related to this contract.
     *
     *@param  sForm          Related search form.
     *@param  request
     *@exception  Exception
     */
    public static void findContractInfo(RelatedForm sForm,
                                        HttpServletRequest request)
                                 throws Exception {

        String sType = sForm.getSearchForType();
        String contractid = (String)request.getSession().getAttribute(
                                    "Contract.id");

        if (null == contractid) {

            return;
        }

        int icontract = Integer.parseInt(contractid);

        // Get the Account for attached to this store.
        // Get the accounts attached to this store.
        if (sType.equals("accounts")) {
            findContractAccounts(request, sForm, icontract);

            return;
        } else if (sType.equals("orderguides")) {
            findContractOrderGuides(request, icontract);

            return;
        } else if (sType.equals("users")) {
            findContractUsers(request, sForm, icontract);

            return;
        }
        // Get the Site for attached to this store.
        // Get the sites attached to this store.
        else if (sType.equals("sites")) {
            findContractSites(request, sForm, icontract);

            return;
        }

        return;
    }

    /**
     *  Find objects related to this user.
     *
     *@param  sForm          Related search form.
     *@param  request
     *@exception  Exception
     */
    public static ActionErrors findUserInfo(RelatedForm sForm,
                                    HttpServletRequest request)
                             throws Exception {

        ActionErrors ae = new ActionErrors();
        String sType = sForm.getSearchForType();
        UserMgrDetailForm userForm = (UserMgrDetailForm)request.getSession().getAttribute(
                                             "USER_DETAIL_FORM");

        if (null == userForm) {

            return ae;
        }

        int iuser = userForm.getDetail().getUserData().getUserId();

        // Get the catalogs attached to this user.
        if (sType.equals("catalogs")) {
            ae = findUserCatalogs(request, sForm, iuser);

            return ae;
        }

        // Get the contracts attached to this user.
        if (sType.equals("contracts")) {
            ae = findUserContracts(request, sForm, iuser);

            return ae;
        }

        // Get the order guides attached to this user.
        if (sType.equals("orderguides")) {
            findUserOrderGuides(request, iuser);

            return ae;
        }
        // Get the sites attached to this user.
        else if (sType.equals("sites")) {
            findUserSites(request, iuser);

            return ae;
        }

        return ae;
    }

    /**
     *  Find objects related to this account.
     *
     *@param  sForm          Related search form.
     *@param  request
     *@exception  Exception
     */
    public static void findAccountInfo(RelatedForm sForm,
                                       HttpServletRequest request)
                                throws Exception {

        String sAccountid = (String)request.getSession().getAttribute(
                                    "Account.id");

        if (sAccountid == null) {

            return;
        }

        int accountid = Integer.parseInt(sAccountid);
        String sType = sForm.getSearchForType();

        // Get the sites attached to this account.
        if (sType.equals("sites")) {
            findAccountSites(request, sForm, accountid);

            return;
        }

        // Get the catalogs attached to this account.
        if (sType.equals("catalogs")) {
            findAccountCatalogs(request, sForm, accountid);

            return;
        }

        // Get the contracts attached to this account.
        if (sType.equals("contracts")) {
            findAccountContracts(request, sForm, accountid);

            return;
        }

        // Get the order guides attached to this account.
        if (sType.equals("orderguides")) {
            findAccountGuides(request, sForm, accountid);

            return;
        }

        // Get the users attached to this account.
        if (sType.equals("users")) {
            findAccountUsers(request, sForm, accountid);

            return;
        }

        return;
    }

    /**
     *  Initialize the search list when looking for objects related to a site.
     *
     *@param  request
     */
    public static void initForSite(HttpServletRequest request) {

        ArrayList opts = new ArrayList();

        // Set the search type array
        opts.add(new FormArrayElement("catalogs", "Catalog"));
        opts.add(new FormArrayElement("contracts", "Contracts"));
        opts.add(new FormArrayElement("orderguides", "Order Guides"));
        opts.add(new FormArrayElement("users", "Users"));
        opts.add(new FormArrayElement("distschedules", "Distributor Schedule"));
        request.getSession().setAttribute("Related.options.vector", opts);
        request.getSession().removeAttribute("Related.users.vector");
        request.getSession().removeAttribute(
                "Related.site.account.catalog.vector");
        request.getSession().removeAttribute("Related.site.catalog");
        request.getSession().removeAttribute("Related.site.distributor");

        request.getSession().removeAttribute("Related.site.orderguide.vector");
        request.getSession().removeAttribute("Related.site.contract.vector");
        request.getSession().removeAttribute("Related.site.distschedules.vector");
    }

    /**
     *  Initialize the search list when looking for objects related to a group.
     *
     *@param  request
     */
    public static void initForProfiling(HttpServletRequest request) {
        ArrayList opts = new ArrayList();
        // Set the search type array
        opts.add(new FormArrayElement("accounts", "Accounts"));
        request.getSession().setAttribute("Related.options.vector", opts);
        request.getSession().removeAttribute("Related.profileing.accounts.vector");
    }

    /**
     *  Initialize the search list when looking for objects related to a profile.
     *
     *@param  request
     */
    public static void initForGroups(HttpServletRequest request) {
        ArrayList opts = new ArrayList();
        // Set the search type array
        opts.add(new FormArrayElement("accounts", "Accounts"));
        opts.add(new FormArrayElement("distributors", "Distributors"));
        opts.add(new FormArrayElement("reports", "Reports"));
        opts.add(new FormArrayElement("sites", "Sites"));
        opts.add(new FormArrayElement("users", "Users"));
        request.getSession().setAttribute("Related.options.vector", opts);
        request.getSession().removeAttribute("Related.group.users.vector");
        request.getSession().removeAttribute("Related.group.reports.vector");
        request.getSession().removeAttribute("Related.group.bus.entity.vector");
    }



    /**
     *  Initialize the search list when looking for objects related to a
     *  catalog.
     *
     *@param  request
     */
    public static void initForCatalog(HttpServletRequest request) {

        ArrayList opts = new ArrayList();

        // Set the search type array to Catalog, Contracts, and Users
        opts.add(new FormArrayElement("accounts", "Accounts"));
        opts.add(new FormArrayElement("contracts", "Contracts"));
        opts.add(new FormArrayElement("distributors", "Distributors"));
        opts.add(new FormArrayElement("orderguides", "Order Guides"));
        opts.add(new FormArrayElement("sites", "Sites"));
        opts.add(new FormArrayElement("users", "Users"));
        request.getSession().setAttribute("Related.options.vector", opts);
        request.getSession().removeAttribute("Related.site.vector");
        request.getSession().removeAttribute("Related.orderguide.vector");
        request.getSession().removeAttribute("Related.contract.vector");
        request.getSession().removeAttribute("Related.account.vector");
        request.getSession().removeAttribute("Related.users.vector");
        request.getSession().removeAttribute("Related.distributor.vector");
    }

    /**
     *  Initialize the search list when looking for objects related to a store.
     *
     *@param  request
     */
    public static void initForStore(HttpServletRequest request) {

        ArrayList opts = new ArrayList();

        // Set the search type array to Catalog, Contracts, and Users
        opts.add(new FormArrayElement("accounts", "Accounts"));
        opts.add(new FormArrayElement("catalogs", "Catalogs"));
        opts.add(new FormArrayElement("users", "Users"));
        request.getSession().setAttribute("Related.options.vector", opts);
        request.getSession().removeAttribute("Related.store.user.vector");
        request.getSession().removeAttribute("Related.store.account.vector");
        request.getSession().removeAttribute("Related.store.catalog.vector");
    }

    /**
     *  Initialize the search list when looking for objects related to a contract.
     *
     *@param  request
     */
    public static void initForContract(HttpServletRequest request) {

        ArrayList opts = new ArrayList();

        // Set the search type array to Catalog, Contracts, and Users
        opts.add(new FormArrayElement("accounts", "Accounts"));
        opts.add(new FormArrayElement("orderguides", "Order Guides"));
        opts.add(new FormArrayElement("sites", "Sites"));
        opts.add(new FormArrayElement("users", "Users"));
        request.getSession().setAttribute("Related.options.vector", opts);
        request.getSession().removeAttribute("Related.contract.account.vector");
        request.getSession().removeAttribute(
                "Related.contract.orderguide.vector");
        request.getSession().removeAttribute("Related.contract.user.vector");
        request.getSession().removeAttribute("Related.contract.site.vector");
    }

    /**
     *  Initialize the search list when looking for objects related to a user.
     *
     *@param  request
     */
    public static void initForUser(HttpServletRequest request) {

        ArrayList opts = new ArrayList();

        // Set the search type array to Catalogs, Contracts, sites and orderGuides
        opts.add(new FormArrayElement("catalogs", "Catalogs"));
        opts.add(new FormArrayElement("contracts", "Contracts"));
        opts.add(new FormArrayElement("orderguides", "Order Guides"));
        opts.add(new FormArrayElement("sites", "Sites"));
        request.getSession().setAttribute("Related.options.vector", opts);
        request.getSession().removeAttribute("Related.user.catalog.vector");
        request.getSession().removeAttribute("Related.user.contract.vector");
        request.getSession().removeAttribute("Related.user.orderguide.vector");
        request.getSession().removeAttribute("Related.user.site.vector");
    }

    /**
     *  Description of the Method
     *
     *@param  request  Description of Parameter
     */
    public static void initForOrderGuide(HttpServletRequest request) {

        ArrayList opts = new ArrayList();

        // Set the search type array to Catalog, Contracts, and Users
        opts.add(new FormArrayElement("accounts", "Accounts"));
        opts.add(new FormArrayElement("sites", "Sites"));
        opts.add(new FormArrayElement("users", "Users"));
        request.getSession().setAttribute("Related.options.vector", opts);
        request.getSession().removeAttribute("Related.account.vector");
        request.getSession().removeAttribute("Related.site.vector");
        request.getSession().removeAttribute("Related.users.vector");
    }

    /**
     *  Initialize the search list when looking for objects related to an
     *  account.
     *
     *@param  request
     */
    public static void initForAccount(HttpServletRequest request)
                               throws Exception {

        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        ListService lsvc = factory.getListServiceAPI();

        // Set up the user types list.
        if (session.getAttribute("Users.types.vector") == null) {

            RefCdDataVector usertypes = lsvc.getRefCodesCollection(
                                                "USER_TYPE_CD");
            session.setAttribute("Users.types.vector", usertypes);
        }

        ArrayList opts = new ArrayList();

        // Set the search type array to Catalog, Contracts, OrderGuides,
        // Users, Sites
        opts.add(new FormArrayElement("catalogs", "Catalogs"));
        opts.add(new FormArrayElement("contracts", "Contracts"));
        opts.add(new FormArrayElement("orderguides", "Order Guides"));
        opts.add(new FormArrayElement("sites", "Sites"));
        opts.add(new FormArrayElement("users", "Users"));
        request.getSession().setAttribute("Related.options.vector", opts);
        request.getSession().removeAttribute("Related.catalogs.vector");
        request.getSession().removeAttribute("Related.contracts.vector");
        request.getSession().removeAttribute("Related.guides.vector");
        request.getSession().removeAttribute("Related.sites.vector");
        request.getSession().removeAttribute("Related.users.vector");
    }


    /**
     *Finds the profiling relationships
     */
    public static void findProfilingInfo(RelatedForm sForm, HttpServletRequest request)
    throws Exception {
        String sType = sForm.getSearchType();
        String sWhatToGet = sForm.getSearchForType();
        String sName = sForm.getSearchForName();
        if(!Utility.isSet(sName)){
            sName = null;
        }
        HttpSession session = request.getSession();
        int profileId = 0;
        try{
            String profileIdS = (String) session.getAttribute("profileId");
            if(profileIdS != null){
                profileId = Integer.parseInt(profileIdS);
            }
        }catch(RuntimeException e){}
        if(profileId == 0){
            ProfilingMgrSurveyForm theForm = (ProfilingMgrSurveyForm)request.getSession().getAttribute("PROFILING_MGR_FORM");
            profileId = theForm.getProfile().getProfileView().getProfileData().getProfileId();
        }
        if(profileId == 0){
            return;
        }
        APIAccess factory = new APIAccess();
        Profiling profilingEjb = factory.getProfilingAPI();

        //not supported yet
        /*int matchType = Account.CONTAINS_IGNORE_CASE;
        if(sType.equals("nameBegins")){
            matchType = Account.BEGINS_WITH_IGNORE_CASE;
        }else if (sType.equals("nameContains")) {
            matchType = Account.CONTAINS_IGNORE_CASE;
        }*/
        if (sWhatToGet.equals("accounts")) {
            BusEntityDataVector accounts =
                profilingEjb.getBusEntityCollectionForProfile(profileId,
                RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT, sName);
            request.getSession().setAttribute("Related.profileing.accounts.vector",accounts);
        }
    }

    /**
     *Finds the group relationships
     */
    public static void findGroupInfo(RelatedForm sForm, HttpServletRequest request)
    throws Exception {
        String sType = sForm.getSearchForType();
        HttpSession session = request.getSession();
        String matchTypeStr = sForm.getSearchType();
        int groupId = 0;
        try{
            String groupIdS = (String) session.getAttribute("groupId");
            if(groupIdS != null){
                groupId = Integer.parseInt(groupIdS);
            }
        }catch(RuntimeException e){}
        if(groupId == 0){
            GroupForm gForm = (GroupForm)request.getSession().getAttribute("GROUP_FORM");
            GroupData grp = gForm.getGroupData();
            groupId = grp.getGroupId();
        }
        if(groupId == 0){
            return;
        }
        APIAccess factory = new APIAccess();
        Group groupEJB = factory.getGroupAPI();
        //pre populate the bus entity search crit
        BusEntitySearchCriteria beCrit = new BusEntitySearchCriteria();
        beCrit.setSearchGroupId(Integer.toString(groupId));
        beCrit.setSearchName(sForm.getSearchForName());
        beCrit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
        //pre populate the group search crit
        GroupSearchCriteriaView crit = GroupSearchCriteriaView.createValue();
        crit.setGroupId(groupId);
        int matchType = Group.NAME_CONTAINS_IGNORE_CASE;
        if(matchTypeStr.equals("nameBegins")){
            matchType = Group.NAME_BEGINS_WITH_IGNORE_CASE;
            beCrit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
        }else if (matchTypeStr.equals("nameContains")) {
            matchType = Group.NAME_CONTAINS_IGNORE_CASE;
            beCrit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
        }
        if (sType.equals("users")) {
            crit.setUserName(sForm.getSearchForName());
            session.setAttribute("Related.group.users.vector", groupEJB.getUsersForGroup(crit,matchType,Group.ORDER_BY_NAME));
        } else if (sType.equals("reports")) {
            crit.setReportName(sForm.getSearchForName());
            session.setAttribute("Related.group.reports.vector",groupEJB.getReportsForGroup(crit,matchType,Group.ORDER_BY_NAME));
        } else if (sType.equals("accounts")) {
            Account accountEJB = factory.getAccountAPI();
            session.setAttribute("Related.group.bus.entity.vector",accountEJB.getAccountsByCriteria(beCrit));
        } else if (sType.equals("distributors")) {
            Distributor distributorEJB = factory.getDistributorAPI();
            session.setAttribute("Related.group.bus.entity.vector",distributorEJB.getDistributorByCriteria(beCrit));
        } else if (sType.equals("sites")) {
            Site siteEJB = factory.getSiteAPI();
            session.setAttribute("Related.group.bus.entity.vector",siteEJB.getSitesByCriteria(beCrit));
        }
    }

    /**
     *  Description of the Method
     *
     *@param  sForm          Description of Parameter
     *@param  request        Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void findCatalogInfo(RelatedForm sForm,
                                       HttpServletRequest request)
                                throws Exception {

        String sType = sForm.getSearchForType();
        CatalogMgrDetailForm catForm = (CatalogMgrDetailForm)request.getSession()
               .getAttribute("CATALOG_DETAIL_FORM");
        CatalogData catdata = catForm.getDetail();

        if (sType.equals("contracts")) {
            findCatalogContracts(request, catdata);
        } else if (sType.equals("sites")) {
            findCatalogSites(request, sForm, catdata.getCatalogId());
        } else if (sType.equals("orderguides")) {
            findCatalogOrderGuides(request, catdata);
        } else if (sType.equals("distributors")) {
            findCatalogDistributors(request, sForm, catdata);
        } else if (sType.equals("users")) {
            findCatalogUsers(request, catdata.getCatalogId());
        } else if (sType.equals("accounts")) {
            findCatalogAccounts(request, sForm, catdata);
        }

        return;
    }

    /**
     *  Description of the Method
     *
     *@param  request          Description of Parameter
     *@param  pCatalogData     Description of Parameter
     *@exception  Exception  Description of Exception
     */
    private static void findCatalogContracts(HttpServletRequest request,
                                             CatalogData pCatalogData)
                                      throws Exception {

        APIAccess factory = new APIAccess();
        Contract con = factory.getContractAPI();
        ContractDescDataVector cdv = con.getContractDescsByCatalog(pCatalogData.getCatalogId());

        // Catalogs found for this site.
        request.getSession().setAttribute("Related.contract.vector", cdv);
    }

    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pCatalogData   Description of Parameter
     *@exception  Exception  Description of Exception
     */
    private static void findCatalogOrderGuides(HttpServletRequest request,
                                               CatalogData pCatalogData)
                                        throws Exception {

        APIAccess factory = new APIAccess();
        OrderGuide ogBean = factory.getOrderGuideAPI();
        OrderGuideDescDataVector v = ogBean.getCollectionByCatalog(pCatalogData.getCatalogId(), OrderGuide.TYPE_ADMIN_RELATED);

        request.getSession().setAttribute("Related.orderguide.vector", v);
    }

    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pCatalogData   Description of Parameter
     *@exception  Exception  Description of Exception
     */
    private static void findCatalogDistributors(HttpServletRequest request,
                                                RelatedForm pForm,
                                                CatalogData pCatalogData)
                                         throws Exception {

        String sName = pForm.getSearchForName();
        String sType = pForm.getSearchType();
        APIAccess factory = new APIAccess();
        CatalogInformation cati = factory.getCatalogInformationAPI();
        DistributorDataVector v;

        if (sType.equals("viewall")) {
            v = cati.getDistributorCollection(pCatalogData.getCatalogId(),
                                              null, 0,
                                              SearchCriteria.ORDER_BY_SHORT_DESC);
        } else if (sType.equals("nameBegins")) {
            v = cati.getDistributorCollection(pCatalogData.getCatalogId(),
                                              sName,
                                              SearchCriteria.BEGINS_WITH_IGNORE_CASE,
                                              SearchCriteria.ORDER_BY_SHORT_DESC);
        } // "nameContains"
        else {
            v = cati.getDistributorCollection(pCatalogData.getCatalogId(),
                                              sName,
                                              SearchCriteria.CONTAINS_IGNORE_CASE,
                                              SearchCriteria.ORDER_BY_SHORT_DESC);
        }

        request.getSession().setAttribute("Related.distributor.vector", v);
    }

    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  pCatalogData   Description of Parameter
     *@exception  Exception  Description of Exception
     */
    private static void findCatalogAccounts(HttpServletRequest request,
                                            RelatedForm pForm,
                                            CatalogData pCatalogData)
                                     throws Exception {

        String sName = pForm.getSearchForName();
        String sType = pForm.getSearchType();
        APIAccess factory = new APIAccess();
        CatalogInformation cati = factory.getCatalogInformationAPI();
        AccountDataVector v = new AccountDataVector();

        if (pCatalogData.getCatalogTypeCd().equals(
                    RefCodeNames.CATALOG_TYPE_CD.ACCOUNT)) {

            if (sType.equals("viewall")) {
                v = cati.getAccountCollection(pCatalogData.getCatalogId(),
                                              null, 0,
                                              SearchCriteria.ORDER_BY_SHORT_DESC);
            } else if (sType.equals("nameBegins")) {
                v = cati.getAccountCollection(pCatalogData.getCatalogId(),
                                              sName,
                                              SearchCriteria.BEGINS_WITH_IGNORE_CASE,
                                              SearchCriteria.ORDER_BY_SHORT_DESC);
            } // "nameContains"
            else {
                v = cati.getAccountCollection(pCatalogData.getCatalogId(),
                                              sName,
                                              SearchCriteria.CONTAINS_IGNORE_CASE,
                                              SearchCriteria.ORDER_BY_SHORT_DESC);
            }
        } else if (pCatalogData.getCatalogTypeCd().equals(
                           RefCodeNames.CATALOG_TYPE_CD.SHOPPING)) {

            /*    // XXX - filtering is missing here
                    HashMap acctids = new HashMap();
                    BusEntityDataVector bedv =
                            cati.getSiteCollection(pCatalogData.getCatalogId());

                    Account acctBean = factory.getAccountAPI();
                    for (int i = 0; i < bedv.size(); i++) {
                        BusEntityData be = (BusEntityData) bedv.get(i);
                        AccountData ad = acctBean.getAccountForSite
                                (be.getBusEntityId());
                        Integer acctkey = new Integer(ad.getBusEntity().getBusEntityId());
                        if (acctids.containsKey(acctkey) == false) {
                            v.add(ad);
                            acctids.put(acctkey, ad);
                        }
                    }
                } */
            BusEntityDataVector bedv = cati.getAccountCollection(pCatalogData.getCatalogId());
            Account acctBean = factory.getAccountAPI();

            for (int i = 0; i < bedv.size(); i++) {

                BusEntityData be = (BusEntityData)bedv.get(i);
                AccountData ad = acctBean.getAccountDetails(be);
                v.add(ad);
            }
        }

        request.getSession().setAttribute("Related.account.vector", v);
    }

    /**
     *  <code>doSort</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void doSort(HttpServletRequest request, ActionForm form)
                       throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        String sortType = request.getParameter("type");
        String sortField = request.getParameter("field");
        String sortName = request.getParameter("name");
        RelatedForm rForm = (RelatedForm)form;
        List list = (List)session.getAttribute(sortName);

        if (sortType.equals("site")) {
            DisplayListSort.sort((SiteDataVector)list, sortField);
            rForm.setSearchForType("sites");
        } else if (sortType.equals("account")) {
            DisplayListSort.sort((AccountDataVector)list, sortField);
            rForm.setSearchForType("accounts");
        } else if (sortType.equals("store")) {
            DisplayListSort.sort((StoreDataVector)list, sortField);
            rForm.setSearchForType("stores");
        } else if (sortType.equals("contract")) {
            DisplayListSort.sort((ContractDescDataVector)list, sortField);
            rForm.setSearchForType("contracts");
        } else if (sortType.equals("user")) {
            DisplayListSort.sort((UserDataVector)list, sortField);
            rForm.setSearchForType("users");
        } else if (sortType.equals("distributors")) {
            DisplayListSort.sort((DistributorDataVector)list, sortField);
            rForm.setSearchForType("distributors");
        } else if (sortType.equals("manufacturer")) {
            DisplayListSort.sort((ManufacturerDataVector)list, sortField);
            rForm.setSearchForType("manufacturers");
        } else if (sortType.equals("catalog")) {
            DisplayListSort.sort((CatalogDataVector)list, sortField);
            rForm.setSearchForType("catalogs");
        } else if (sortType.equals("orderguide")) {
            rForm.setSearchForType("orderguides");
            DisplayListSort.sort((OrderGuideDescDataVector)list, sortField);
        } else if (sortType.equals("report")) {
            rForm.setSearchForType("report");
            DisplayListSort.sort((GenericReportDataVector)list, sortField);
        }
    }
}
