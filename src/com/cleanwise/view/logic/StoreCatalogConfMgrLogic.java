/*
 * StoreCatalogConfMgrLogic.java
 *
 * Created on July 18, 2005, 4:19 PM
 */

package com.cleanwise.view.logic;

import java.math.BigDecimal;
import java.util.*;
import java.rmi.RemoteException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.cleanwise.service.api.value.AccountSearchResultViewVector;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.StoreCatalogMgrForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.dao.CatalogAssocDataAccess;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;


/**
 *
 * @author Ykupershmidt
 */
public class StoreCatalogConfMgrLogic {

    /**
     *  <code>init</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void init(HttpServletRequest request,
			  ActionForm form)
      	throws Exception {

      // reset all the saved data
      HttpSession session = request.getSession();
      session.removeAttribute("catalog.item.distributors.vector");
      session.removeAttribute("catalog.item.alldistributors.vector");
      session.removeAttribute("catalog.distributors.vector");
      session.removeAttribute("catalog.accounts.vector");
      session.removeAttribute("catalog.sites.vector");
      session.removeAttribute("catalog.assoc.ids");
      session.removeAttribute("catalog.item.distributor.error");
      session.removeAttribute("Related.users.vector");
      session.removeAttribute("Account.search.result");
      StoreCatalogMgrForm  pForm = (StoreCatalogMgrForm) form;
      if("Users".equals(pForm.getConfType())) {
        searchUser(request, form);
      }
      //theForm.setOverwriteDistId(0);


        return;
    }

    /**
     *  <code>search</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void search(HttpServletRequest request,
			      ActionForm form)
	     throws Exception {

	     // new search, reset saved data
	     init(request, form);
       HttpSession session = request.getSession();
  	   StoreCatalogMgrForm configForm = (StoreCatalogMgrForm)form;

  	   String configType = Utility.strNN(configForm.getConfType());
	     String fieldValue = configForm.getConfSearchField();
  	   if (!Utility.isSet(fieldValue)) {
  	     // treat just like a 'view all' request
	       if (configType.equals("Items")) {
		       //getAllItemDist(request, form);
	       } else if (configType.equals("Distributors")) {
		       getAllDist(request, form);
  	     } else if (configType.equals("Accounts")) {
  		     //getAllAccount(request, form);
  		     getAllAccountAsSearchResultVector(request, form);
  	     } else if (configType.equals("Sites")) {
           searchSite(request, form);
	       }
  	     return;
  	   }

     if (configType.equals("Items")) {
	     //searchItemDist(request, form);
	   } else if (configType.equals("Distributors")) {
	     searchDist(request, form);
  	 } else if (configType.equals("Accounts")) {
  		//searchAccount(request, form);
  		searchAccountAsSearchResultVector(request, form);
  	 } else if (configType.equals("Sites")) {
  	    searchSite(request, form);
	   } else if (configType.equals("Users")) {
        searchUser(request, form);
     }

        return;
   }

    /**
     *  <code>searchItemDist</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
   /* 
    public static void searchItemDist(HttpServletRequest request,
				      ActionForm form)
	  throws Exception {

    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    CatalogInformation catalogInfBean = factory.getCatalogInformationAPI();

    StoreCatalogMgrForm configForm = (StoreCatalogMgrForm)form;

    String fieldValue = configForm.getSearchField();
    String fieldSearchType = configForm.getSearchType();

    CatalogMgrDetailForm detailForm = 
      (CatalogMgrDetailForm)session.getAttribute("CATALOG_DETAIL_FORM");
    int catalogId = detailForm.getDetail().getCatalogId();

  	configForm.setDistributorId("");
	  //find related distributors
	  DistributorDataVector allItemDists =
	    catalogInfBean.getDistributorCollection(catalogId,
						    null,
						    0, 
						    SearchCriteria.ORDER_BY_ID);

	  session.setAttribute("catalog.item.alldistributors.vector", allItemDists);
        //find related distributors
	  DistributorDataVector distributors = new DistributorDataVector();
        if(fieldValue!=null) fieldValue = fieldValue.trim().toUpperCase();
        for(int ii=0; ii<allItemDists.size(); ii++) {
          DistributorData dD = (DistributorData) allItemDists.get(ii);
          String distName = dD.getBusEntity().getShortDesc();
          if(distName!=null) {
            distName = distName.toUpperCase();
            int mm = distName.indexOf(fieldValue);
            if (fieldValue==null ||
                fieldSearchType.equals("nameContains") && mm>=0 ||
                fieldSearchType.equals("nameBegins") && mm==0) {
              distributors.add(dD);
            }
          }
        }
	  session.setAttribute("catalog.item.distributors.vector", distributors);

    return;
  }
 */

    /**
     *  <code>searchDist</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    
    public static void searchDist(HttpServletRequest request,
				  ActionForm form)
	  throws Exception {

        HttpSession session = request.getSession();

	  APIAccess factory = new APIAccess();
	  CatalogInformation catalogInfBean = factory.getCatalogInformationAPI();

  	StoreCatalogMgrForm pForm = (StoreCatalogMgrForm)form;

	  String fieldValue = pForm.getConfSearchField();
	  String fieldSearchType = pForm.getConfSearchType();
    
    CatalogData catalogD = pForm.getCatalogDetail();
   	int catalogId = catalogD.getCatalogId();
	  int storeId = pForm.getStoreId();

	  String catalogType = catalogD.getCatalogTypeCd();

	  Distributor distributorBean = factory.getDistributorAPI();
	  DistributorDataVector distributors = null;
    
	  int match;
	  if (fieldSearchType.equals("nameBegins")) {
	    match = SearchCriteria.BEGINS_WITH_IGNORE_CASE;
	  } else { // "nameContains"
	    match = SearchCriteria.CONTAINS_IGNORE_CASE;
	  }
    boolean showInactiveFl = pForm.getConfShowInactiveFl();

    DistributorDataVector filterDists = null;
    CatalogDataVector filterCatalogDV = pForm.getConfCatalogFilter();
    if(filterCatalogDV!=null && filterCatalogDV.size()>0) {
      CatalogData filterCatalogD = (CatalogData) filterCatalogDV.get(0);
      filterDists = 
        catalogInfBean.getDistributorCollection(filterCatalogD.getCatalogId(),
						    fieldValue,
						    match, 
						    SearchCriteria.ORDER_BY_SHORT_DESC);
    }
    
   
    DistributorDataVector configuredDists = 
            catalogInfBean.getDistributorCollection(catalogId,
						    fieldValue,
						    match, 
						    SearchCriteria.ORDER_BY_SHORT_DESC);

    if(filterDists!=null) {
      configuredDists = applyFilterDist(configuredDists,filterDists);
    } 

    String[] selectIds = new String[configuredDists.size()];
	  IdVector distIds = new IdVector();

	  int ind = 0;
	  for (Iterator iter = configuredDists.iterator(); iter.hasNext();) {
      DistributorData distD = (DistributorData) iter.next();
      if(!showInactiveFl) {
        if(RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE.
                equals(distD.getBusEntity().getBusEntityStatusCd())) {
          iter.remove();
        }
      }
      int distId = distD.getBusEntity().getBusEntityId();
	    Integer distIdI = new Integer(distId);
	    distIds.add(distIdI);
	    selectIds[ind++] = distIdI.toString();
	  }

    if(!pForm.getConfShowConfguredOnlyFl()) {
      int superCatalogId = pForm.getMasterCatalogId();          
	    distributors = 
		    catalogInfBean.getDistributorCollection(superCatalogId, 
							fieldValue,
              showInactiveFl,
							match,
							SearchCriteria.ORDER_BY_SHORT_DESC);
      if(filterDists!=null) {
        distributors = applyFilterDist(distributors,filterDists);
      }
    } else {
      distributors = configuredDists;
    }

       //setSelectedMainDistributor
       IdVector mainDistrIds = getMainDistributorIds(catalogId);
       if(mainDistrIds==null)     throw new Exception("There is no information on the main distributor");
       if(mainDistrIds.size()>1)  throw new Exception("For selected catalog the main distributor not one");
       if(mainDistrIds.size()>0)
       pForm.setSelectedMainDistributorId(String.valueOf(mainDistrIds.get(0)));
       else pForm.setSelectedMainDistributorId("");


       session.setAttribute("catalog.distributors.vector", distributors);

	  // the currently associated distributors are checked/selected
	  pForm.setSelectIds(selectIds);

	  // list of all associated distributor ids, in the update this
	  // will be compared with the selected
	  session.setAttribute("catalog.assoc.ids", distIds);
	
    return;
    }



    private static IdVector getMainDistributorIds(int catalogId) throws NamingException, APIServiceAccessException, RemoteException {
        APIAccess factory=new APIAccess();
        CatalogInformation catalogInfoBean=factory.getCatalogInformationAPI();
        CatalogAssocDataVector catAssocDV = catalogInfoBean.getCatalogAssoc(catalogId,0,RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR);
        IdVector ids=new IdVector();
        Iterator it=catAssocDV.iterator();
        while(it.hasNext())
        {
          CatalogAssocData catAssocD=(CatalogAssocData)it.next();
          ids.add(new Integer(catAssocD.getBusEntityId()));
        }
        return  ids;
    }
    /**
     *  <code>searchAccount</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
  public static void searchAccount(HttpServletRequest request,
                                   ActionForm form)
    throws Exception {

  HttpSession session = request.getSession();

  StoreCatalogMgrForm pForm = (StoreCatalogMgrForm)form;

    String fieldValue = pForm.getConfSearchField();
    String fieldSearchType = pForm.getConfSearchType();

     int catalogId = pForm.getCatalogDetail().getCatalogId();
    int storeId = pForm.getStoreId();

  APIAccess factory = new APIAccess();
    CatalogInformation catalogInfBean = factory.getCatalogInformationAPI();
    Account accountBean = factory.getAccountAPI();

    AccountDataVector accounts;
    AccountDataVector filterAccounts = null;
  CatalogDataVector filterCatalogDV = pForm.getConfCatalogFilter();

  if(filterCatalogDV!=null && filterCatalogDV.size()>0) {
    CatalogData filterCatalogD = (CatalogData) filterCatalogDV.get(0);
      filterAccounts =
        catalogInfBean.getAccountCollection(filterCatalogD.getCatalogId(), null, 0,
                                                       SearchCriteria.ORDER_BY_SHORT_DESC);

  }

  AccountDataVector relatedAccounts;
    if (fieldSearchType.equals("nameBegins")) {
    relatedAccounts =
      catalogInfBean.getAccountCollection(catalogId, fieldValue,
              SearchCriteria.BEGINS_WITH_IGNORE_CASE,
              SearchCriteria.ORDER_BY_SHORT_DESC);
  } else {
    relatedAccounts =
    catalogInfBean.getAccountCollection(catalogId, fieldValue,
              SearchCriteria.CONTAINS_IGNORE_CASE,
              SearchCriteria.ORDER_BY_SHORT_DESC);
  }

  if(filterAccounts!=null) {
    relatedAccounts = applyFilterAccount(relatedAccounts, filterAccounts);
  }

  if (fieldSearchType.equals("nameBegins")) {
      if(!pForm.getConfShowConfguredOnlyFl()) {
            accounts =
              accountBean.getAccountByName(fieldValue, storeId,
                         Account.BEGINS_WITH_IGNORE_CASE,
                         Account.ORDER_BY_NAME);

        if(filterAccounts!=null) {
          accounts = applyFilterAccount(accounts, filterAccounts);
        }
      } else {
        accounts = relatedAccounts;
      }
    } else { // "nameContains"
      //
      if(!pForm.getConfShowConfguredOnlyFl()) {
          accounts =
            accountBean.getAccountByName(fieldValue, storeId,
                         Account.CONTAINS_IGNORE_CASE,
                         Account.ORDER_BY_NAME);
        if(filterAccounts!=null) {
          accounts = applyFilterAccount(accounts, filterAccounts);
        }
      } else {
        accounts = relatedAccounts;
      }
    }
    session.setAttribute("catalog.accounts.vector", accounts);
    String[] selectIds = new String[relatedAccounts.size()];
    IdVector acctIds = new IdVector();

    int ind = 0;
    for (Iterator iter = relatedAccounts.iterator(); iter.hasNext();) {
    AccountData aD = (AccountData) iter.next();
       Integer idI = new Integer(aD.getBusEntity().getBusEntityId());
       acctIds.add(idI);
       selectIds[ind++] = new String(idI.toString());
    }

    // the currently associated accounts are checked/selected
    pForm.setSelectIds(selectIds);

    // list of all associated account ids, in the update this
    // will be compared with the selected
    session.setAttribute("catalog.assoc.ids", acctIds);

  return;
 }

  public static void searchAccountAsSearchResultVector(HttpServletRequest request,
                                   ActionForm form)
    throws Exception {

  HttpSession session = request.getSession();

  StoreCatalogMgrForm pForm = (StoreCatalogMgrForm)form;

  String fieldValue = pForm.getConfSearchField();
  String fieldSearchType = pForm.getConfSearchType();

  int catalogId = pForm.getCatalogDetail().getCatalogId();
  int storeId = pForm.getStoreId();

  APIAccess factory = new APIAccess();
  CatalogInformation catalogInfBean = factory.getCatalogInformationAPI();
  Account accountBean = factory.getAccountAPI();

  AccountDataVector accounts;
  AccountDataVector filterAccounts = null;
  CatalogDataVector filterCatalogDV = pForm.getConfCatalogFilter();
  
  String storeStr = null;
  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
  if (!appUser.isaSystemAdmin()) {
	  IdVector stores = appUser.getUserStoreAsIdVector();
	  storeStr = stores.toCommaString(stores);
  }
  String searchGroupId = "";
  boolean showInactive = true;
  AccountSearchResultViewVector acntSrchRsltVctr = null;
  if(filterCatalogDV!=null && filterCatalogDV.size()>0) {
    CatalogData filterCatalogD = (CatalogData) filterCatalogDV.get(0);
    filterAccounts =
        catalogInfBean.getAccountCollection(filterCatalogD.getCatalogId(), null, 0,
                                                       SearchCriteria.ORDER_BY_SHORT_DESC);
  }

  AccountDataVector relatedAccounts;
  if (fieldSearchType.equals("nameBegins")) {
  relatedAccounts =
      catalogInfBean.getAccountCollection(catalogId, fieldValue,
              SearchCriteria.BEGINS_WITH_IGNORE_CASE,
              SearchCriteria.ORDER_BY_SHORT_DESC);
  } else {
    relatedAccounts =
    catalogInfBean.getAccountCollection(catalogId, fieldValue,
              SearchCriteria.CONTAINS_IGNORE_CASE,
              SearchCriteria.ORDER_BY_SHORT_DESC);
  }

  if(filterAccounts!=null) {
    relatedAccounts = applyFilterAccount(relatedAccounts, filterAccounts);
  }
      if(!pForm.getConfShowConfguredOnlyFl()) {
            acntSrchRsltVctr = accountBean.search(storeStr, fieldValue, fieldSearchType, searchGroupId, showInactive);
        if(filterAccounts!=null) {
        	acntSrchRsltVctr = applyFilterAccount(acntSrchRsltVctr, filterAccounts);
        }
      } else {
        accounts = relatedAccounts;
        acntSrchRsltVctr = Utility.toAccountSearchResultViewVector(accounts);
      }
    session.setAttribute("Account.search.result", acntSrchRsltVctr);
    String[] selectIds = new String[relatedAccounts.size()];
    IdVector acctIds = new IdVector();

    int ind = 0;
    for (Iterator iter = relatedAccounts.iterator(); iter.hasNext();) {
    AccountData aD = (AccountData) iter.next();
       Integer idI = new Integer(aD.getBusEntity().getBusEntityId());
       acctIds.add(idI);
       selectIds[ind++] = new String(idI.toString());
    }

    // the currently associated accounts are checked/selected
    pForm.setSelectIds(selectIds);

    // list of all associated account ids, in the update this
    // will be compared with the selected
    session.setAttribute("catalog.assoc.ids", acctIds);

  return;
 }

    /**
     * <code>searchSite</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form    an <code>ActionForm</code> value
     * @throws Exception if an error occurs
     */
    public static void searchSite(HttpServletRequest request, ActionForm form) throws Exception {

        HttpSession session = request.getSession();
        StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;

        String fieldValue = pForm.getConfSearchField();
        String searchType = pForm.getConfSearchType();
        String state = pForm.getConfState();
        String city = pForm.getConfCity();
        String county = pForm.getConfCounty();
        String zip = pForm.getConfZipcode();
        String action = request.getParameter("action");

        int catalogId = pForm.getCatalogDetail().getCatalogId();

        APIAccess factory = new APIAccess();

        CatalogInformation catalogInfBean = factory.getCatalogInformationAPI();
        Site siteBean = factory.getSiteAPI();

        BusEntityDataVector bedv = catalogInfBean.getAccountCollection(catalogId);

        SiteViewVector dv;

        QueryRequest qr = new QueryRequest();
        qr.filterByAccountIds(Utility.toIdVector(bedv));
        qr.setMaxRows(Constants.MAX_SITES_TO_RETURN);

        if (action.equals("View All")) {

            qr.orderBySiteId(true);

        } else {

            if (searchType.equals("id") && fieldValue.length() > 0) {

                int id = Integer.parseInt(fieldValue);
                qr.filterBySiteId(id);

            } else {

                String f = "";
                if (searchType.equals("nameBegins") && fieldValue.length() > 0) {
                    qr.filterBySiteName(fieldValue, QueryRequest.BEGINS_IGNORE_CASE);
                } else if (searchType.equals("nameContains") && fieldValue.length() > 0) {
                    qr.filterBySiteName(fieldValue, QueryRequest.CONTAINS_IGNORE_CASE);
                }

                f = city.trim();
                if (f.length() > 0) {
                    qr.filterByCity(f, QueryRequest.BEGINS_IGNORE_CASE);
                }

                f = Utility.strNN(county);
                f = f.trim();
                if (f.length() > 0) {
                    qr.filterByCounty(f, QueryRequest.BEGINS_IGNORE_CASE);
                }

                f = state.trim();
                if (f.length() > 0) {
                    qr.filterByState(f, QueryRequest.BEGINS_IGNORE_CASE);
                }

                f = zip.trim();
                if (f.length() > 0) {
                    qr.filterByZip(f, QueryRequest.BEGINS_IGNORE_CASE);
                }
            }
            
            if (pForm.getConfShowConfguredOnlyFl()) {
                qr.filterByCatalogId(catalogId);
            }

            CatalogDataVector filterCatalogs = pForm.getConfCatalogFilter();
            if (filterCatalogs != null && filterCatalogs.size() > 0) {
                CatalogData cD = (CatalogData) filterCatalogs.get(0);
                qr.filterByCatalogId(cD.getCatalogId());
            }

        }

        dv = siteBean.getSiteCollection(qr);

        IdVector relatedSiteIds = catalogInfBean.getSiteIds(catalogId);

        session.setAttribute("catalog.sites.vector", dv);

        String[] selectIds = new String[relatedSiteIds.size()];

        IdVector siteIds = new IdVector();
        for (int i = 0, len = relatedSiteIds.size(); i < len; i++) {
            selectIds[i] = relatedSiteIds.get(i).toString();
            siteIds.add(relatedSiteIds.get(i));
        }

        // the currently associated sites are checked/selected
        pForm.setSelectIds(selectIds);

        // list of all associated site ids, in the update this
        // will be compared with the selected
        session.setAttribute("catalog.assoc.ids", siteIds);

    }

    private static void searchUser(HttpServletRequest request,
                  ActionForm form)
     throws Exception {
    HttpSession session = request.getSession();
    StoreCatalogMgrForm pForm = (StoreCatalogMgrForm)form;

    String fieldValue = pForm.getConfSearchField();
    String searchType = pForm.getConfSearchType();
    String state = pForm.getConfState();
    String city = pForm.getConfCity();
    String county = pForm.getConfCounty();
    String zip = pForm.getConfZipcode();
    String action = request.getParameter("action");

    int catalogId = pForm.getCatalogDetail().getCatalogId();
    int storeId = pForm.getStoreId();

    APIAccess factory = new APIAccess();
      CatalogInformation catalogInfBean = factory.getCatalogInformationAPI();
    UserDataVector userDV = catalogInfBean.getUserCollection(catalogId);
    request.getSession().setAttribute("Related.users.vector", userDV);
   }


    /**
     * <code>getAll</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form    an <code>ActionForm</code> value
     * @throws Exception if an error occurs
     * @depricated very slow search, throws OutOfMemoryException in case the result count is very big
     * was replaced on search
     */
    public static void getAll(HttpServletRequest request, ActionForm form) throws Exception {

        // reset the saved data
        init(request, form);

        HttpSession session = request.getSession();
        StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;
        String configType = pForm.getConfType();

        // removes any left-over settings
        pForm.setConfSearchField("");
        pForm.setConfSearchType("");

        if (configType.equals("Items")) {
            //getAllItemDist(request, form);
        } else if (configType.equals("Distributors")) {
            getAllDist(request, form);
        } else if (configType.equals("Accounts")) {
            getAllAccount(request, form);
        } else if (configType.equals("Sites")) {
            getAllSite(request, form);
        }

        return;
    }

    /**
     *  <code>getAllItemDist</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    /*
    public static void getAllItemDist(HttpServletRequest request,
				      ActionForm form)
	throws Exception {

        HttpSession session = request.getSession();

  	StoreCatalogMgrForm configForm = (StoreCatalogMgrForm)form;

  	CatalogMgrDetailForm detailForm = 
  	    (CatalogMgrDetailForm)session.getAttribute("CATALOG_DETAIL_FORM");
  	int catalogId = detailForm.getDetail().getCatalogId();

  	APIAccess factory = new APIAccess();
	CatalogInformation catalogInfBean = factory.getCatalogInformationAPI();

	configForm.setDistributorId("");

	//find related distributors
	DistributorDataVector distributors =
	    catalogInfBean.getDistributorCollection(catalogId,
						    null,
						    0,
						    SearchCriteria.ORDER_BY_SHORT_DESC);
	session.setAttribute("catalog.item.distributors.vector", 
			     distributors);
	session.setAttribute("catalog.item.alldistributors.vector", 
			     distributors);

        return;
    }
    */

    /**
     *  <code>getAllDist</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */

  public static void getAllDist(HttpServletRequest request,
                  ActionForm form)
      throws Exception {

    HttpSession session = request.getSession();

    StoreCatalogMgrForm pForm = (StoreCatalogMgrForm)form;
    CatalogData catalogD = pForm.getCatalogDetail();
    int catalogId = catalogD.getCatalogId();
      int storeId = pForm.getStoreId();

      APIAccess factory = new APIAccess();
      CatalogInformation catalogInfBean = factory.getCatalogInformationAPI();
      Distributor distributorBean = factory.getDistributorAPI();
      String catalogType = catalogD.getCatalogTypeCd();

      DistributorDataVector distributors = null;
    boolean showInactiveFl = pForm.getConfShowInactiveFl();


    DistributorDataVector filterDists = null;
    CatalogDataVector filterCatalogDV = pForm.getConfCatalogFilter();
    if(filterCatalogDV!=null && filterCatalogDV.size()>0) {
      CatalogData filterCatalogD = (CatalogData) filterCatalogDV.get(0);
      filterDists =
        catalogInfBean.getDistributorCollection(filterCatalogD.getCatalogId(),
                  null,
                  showInactiveFl,
                  0,
                  SearchCriteria.ORDER_BY_SHORT_DESC);
    }

    //find related distributors
    DistributorDataVector configuredDists =
        catalogInfBean.getDistributorCollection(catalogId,
                  null,
                  showInactiveFl,
                  0,
                  SearchCriteria.ORDER_BY_SHORT_DESC);

    if(filterDists!=null) {
      configuredDists = applyFilterDist(configuredDists,filterDists);
    }
    String[] selectIds = new String[configuredDists.size()];
      IdVector distIds = new IdVector();

      int ind = 0;
      for (Iterator iter = configuredDists.iterator(); iter.hasNext();) {
      DistributorData distD = (DistributorData) iter.next();
      int distId = distD.getBusEntity().getBusEntityId();
        Integer distIdI = new Integer(distId);
        distIds.add(distIdI);
        selectIds[ind++] = distIdI.toString();
      }

    if(!pForm.getConfShowConfguredOnlyFl()) {
      int superCatalogId = pForm.getMasterCatalogId();
      if(filterDists==null) {
        distributors =
                catalogInfBean.getDistributorCollection(superCatalogId,
                            null,
              showInactiveFl,
                            0,
                            SearchCriteria.ORDER_BY_SHORT_DESC);
      } else {
        distributors = filterDists;
      }
    } else {
      distributors = configuredDists;
    }
       //setSelectedMainDistributor
       IdVector mainDistrIds = getMainDistributorIds(catalogId);
       if(mainDistrIds==null)     throw new Exception("There is no information on the main distributor");
       if(mainDistrIds.size()>1)  throw new Exception("The selected catalog has multiple main distributors");
       if(mainDistrIds.size()>0)
       pForm.setSelectedMainDistributorId(String.valueOf(mainDistrIds.get(0)));
       else pForm.setSelectedMainDistributorId("");


     session.setAttribute("catalog.distributors.vector", distributors);


      // the currently associated distributors are checked/selected
      pForm.setSelectIds(selectIds);

    // list of all associated distributor ids, in the update this
    // will be compared with the selected
    session.setAttribute("catalog.assoc.ids", distIds);

    return;
  }

  private static DistributorDataVector
    applyFilterDist(DistributorDataVector distDV, DistributorDataVector filterDistDV) {

    DistributorDataVector crossDists = new DistributorDataVector();
    if(distDV==null || filterDistDV==null) {
      return crossDists;
    }
    DistributorData wrkFltDistD = null;
    for(Iterator iter=distDV.iterator(),iter1=filterDistDV.iterator();
        iter.hasNext();) {
      DistributorData dD = (DistributorData) iter.next();
      String shortDesc = dD.getBusEntity().getShortDesc();
      while(wrkFltDistD!=null || iter1.hasNext()) {
        if(wrkFltDistD==null) wrkFltDistD = (DistributorData) iter1.next();
        int comp = shortDesc.compareTo(wrkFltDistD.getBusEntity().getShortDesc());
        if(comp<0) {
          break;
        }
        if(comp>0) {
          wrkFltDistD = null;
          continue;
        }
        if(dD.getBusEntity().getBusEntityId()==wrkFltDistD.getBusEntity().getBusEntityId()) {
          crossDists.add(dD);
          wrkFltDistD = null;
          break;
        }
        wrkFltDistD = null;
        continue;
      }
    }
    return crossDists;
  }


    /**
     *  <code>getAllAccount</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
  public static void getAllAccount(HttpServletRequest request,
                     ActionForm form)
    throws Exception {
	HttpSession session = request.getSession();
	StoreCatalogMgrForm pForm = (StoreCatalogMgrForm)form;

    int catalogId = pForm.getCatalogDetail().getCatalogId();
    int storeId = pForm.getStoreId();

    APIAccess factory = new APIAccess();
    CatalogInformation catalogInfBean = factory.getCatalogInformationAPI();

    Account accountBean = factory.getAccountAPI();

    AccountDataVector filterAccounts = null;
    CatalogDataVector filterCatalogDV = pForm.getConfCatalogFilter();
    if(filterCatalogDV!=null && filterCatalogDV.size()>0) {
      CatalogData filterCatalogD = (CatalogData) filterCatalogDV.get(0);
      filterAccounts =
        catalogInfBean.getAccountCollection(filterCatalogD.getCatalogId(), null, 0,
                                                       SearchCriteria.ORDER_BY_SHORT_DESC);
    }

    //find related accounts
    AccountDataVector relatedAccounts =
        catalogInfBean.getAccountCollection(catalogId, null, 0,
                        SearchCriteria.ORDER_BY_SHORT_DESC);
    if(filterAccounts!=null) {
    	relatedAccounts = applyFilterAccount(relatedAccounts, filterAccounts);
    }
    AccountDataVector accounts = null;
    if(!pForm.getConfShowConfguredOnlyFl()) {
      if(filterAccounts!=null) {
        accounts = filterAccounts;
      } else {
        accounts = accountBean.getAllAccounts(storeId, Account.ORDER_BY_NAME);
      }
    } else {
      accounts = relatedAccounts;
    }
    session.setAttribute("catalog.accounts.vector", accounts);

    String[] selectIds = new String[relatedAccounts.size()];
    IdVector acctIds = new IdVector();


    int ind = 0;
    for (Iterator iter = relatedAccounts.iterator(); iter.hasNext();) {
      AccountData aD = (AccountData) iter.next();
      int id = aD.getBusEntity().getBusEntityId();
      acctIds.add(new Integer(id));
      selectIds[ind++] = ""+id;
    }

    // the currently associated accounts are checked/selected
    pForm.setSelectIds(selectIds);

    // list of all associated account ids, in the update this
    // will be compared with the selected
    session.setAttribute("catalog.assoc.ids", acctIds);
    return;
  }
  
  public static void getAllAccountAsSearchResultVector(HttpServletRequest request,
          ActionForm form)
	throws Exception {
	HttpSession session = request.getSession();
	StoreCatalogMgrForm pForm = (StoreCatalogMgrForm)form;
	
	int catalogId = pForm.getCatalogDetail().getCatalogId();
	int storeId = pForm.getStoreId();
	
	APIAccess factory = new APIAccess();
	CatalogInformation catalogInfBean = factory.getCatalogInformationAPI();
	
	Account accountBean = factory.getAccountAPI();
	
	AccountSearchResultViewVector acntSrchRsltVctr = null;
	AccountDataVector filterAccounts = null;
	CatalogDataVector filterCatalogDV = pForm.getConfCatalogFilter();
	if(filterCatalogDV!=null && filterCatalogDV.size()>0) {
	  CatalogData filterCatalogD = (CatalogData) filterCatalogDV.get(0);
	  filterAccounts =
		catalogInfBean.getAccountCollection(filterCatalogD.getCatalogId(), null, 0,
		                                            SearchCriteria.ORDER_BY_SHORT_DESC);
	}
	
	//find related accounts
	AccountDataVector relatedAccounts =
	catalogInfBean.getAccountCollection(catalogId, null, 0,
	             SearchCriteria.ORDER_BY_SHORT_DESC);
	if(filterAccounts!=null) {
	  relatedAccounts = applyFilterAccount(relatedAccounts, filterAccounts);
	}
	AccountDataVector accounts = null;
	//AccountSearchResultViewVector accounts = null;
	if(!pForm.getConfShowConfguredOnlyFl()) {
	  if(filterAccounts!=null) {
	    accounts = filterAccounts;
	    acntSrchRsltVctr = Utility.toAccountSearchResultViewVector(accounts);
	  } else {
	    //accounts = accountBean.getAllAccounts(storeId, Account.ORDER_BY_NAME);
	    String storeStr = null;
	    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	    if (!appUser.isaSystemAdmin()) {
		  IdVector stores = appUser.getUserStoreAsIdVector();
		  storeStr = stores.toCommaString(stores);
	    }
	    String fieldValue = "";
		String fieldSearchType = "";
		String searchGroupId = "";
		boolean showInactive = true;
		
	    acntSrchRsltVctr = accountBean.search(storeStr, fieldValue, fieldSearchType, searchGroupId, showInactive);
	  }
	} else {
		accounts = relatedAccounts;
		acntSrchRsltVctr = Utility.toAccountSearchResultViewVector(accounts);
	}
	session.setAttribute("Account.search.result", acntSrchRsltVctr);
	
	String[] selectIds = new String[relatedAccounts.size()];
	IdVector acctIds = new IdVector();
	
	
	int ind = 0;
	for (Iterator iter = relatedAccounts.iterator(); iter.hasNext();) {
	  AccountData aD = (AccountData) iter.next();
	  int id = aD.getBusEntity().getBusEntityId();
	  acctIds.add(new Integer(id));
	  selectIds[ind++] = ""+id;
	}
	
	// the currently associated accounts are checked/selected
	pForm.setSelectIds(selectIds);
	
	// list of all associated account ids, in the update this
	// will be compared with the selected
	session.setAttribute("catalog.assoc.ids", acctIds);
	return;
  }



  private static AccountDataVector
    applyFilterAccount(AccountDataVector accountDV, AccountDataVector filterAccountDV) {

    AccountDataVector crossAccounts = new AccountDataVector();
    if(accountDV==null || filterAccountDV==null) {
      return crossAccounts;
    }
    AccountData wrkFltAcctD = null;
    for(Iterator iter=accountDV.iterator(),iter1=filterAccountDV.iterator();
        iter.hasNext();) {
      AccountData aD = (AccountData) iter.next();
      String shortDesc = aD.getBusEntity().getShortDesc();
      while(wrkFltAcctD!=null || iter1.hasNext()) {
        if(wrkFltAcctD==null) wrkFltAcctD = (AccountData) iter1.next();
        int comp = shortDesc.compareTo(wrkFltAcctD.getBusEntity().getShortDesc());
        if(comp<0) {
          break;
        }
        if(comp>0) {
          wrkFltAcctD = null;
          continue;
        }
        if(aD.getBusEntity().getBusEntityId()==wrkFltAcctD.getBusEntity().getBusEntityId()) {
          crossAccounts.add(aD);
          wrkFltAcctD = null;
          break;
        }
        wrkFltAcctD = null;
        continue;
      }
    }
    return crossAccounts;
  }


  private static AccountSearchResultViewVector
    applyFilterAccount(AccountSearchResultViewVector accountDV, AccountDataVector filterAccountDV) {

	AccountSearchResultViewVector crossAccounts = new AccountSearchResultViewVector();
    if(accountDV==null || filterAccountDV==null) {
      return crossAccounts;
    }
    AccountData wrkFltAcctD = null;
    for(Iterator iter=accountDV.iterator(),iter1=filterAccountDV.iterator();
      iter.hasNext();) {
      AccountSearchResultView aD = (AccountSearchResultView) iter.next();
      String shortDesc = aD.getShortDesc();
      while(wrkFltAcctD!=null || iter1.hasNext()) {
        if(wrkFltAcctD==null) wrkFltAcctD = (AccountData) iter1.next();
        int comp = shortDesc.compareTo(wrkFltAcctD.getBusEntity().getShortDesc());
        if(comp<0) {
          break;
        }
        if(comp>0) {
          wrkFltAcctD = null;
          continue;
        }
        if(aD.getAccountId()==wrkFltAcctD.getBusEntity().getBusEntityId()) {
          crossAccounts.add(aD);
          wrkFltAcctD = null;
          break;
        }
        wrkFltAcctD = null;
        continue;
      }
    }
    return crossAccounts;
  }


    /**
     * <code>getAllSite</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form    an <code>ActionForm</code> value
     * @throws Exception if an error occurs
     * @depricated very slow search, throws OutOfMemoryException in case the result count is very big
     * was replaced on searchSite
     */
    public static void getAllSite(HttpServletRequest request, ActionForm form) throws Exception {

        HttpSession session = request.getSession();

        StoreCatalogMgrForm pForm = (StoreCatalogMgrForm) form;
        int catalogId = pForm.getCatalogDetail().getCatalogId();

        APIAccess factory = new APIAccess();
        CatalogInformation catalogInfBean = factory.getCatalogInformationAPI();

        Site siteBean = factory.getSiteAPI();
        // XXX - should just be getting sites for catalog store, not all sites

        SiteDataVector filterSites = null;
        CatalogDataVector filterCatalogDV = pForm.getConfCatalogFilter();
        if (filterCatalogDV != null && filterCatalogDV.size() > 0) {
            CatalogData filterCatalogD = (CatalogData) filterCatalogDV.get(0);
            filterSites =
                    catalogInfBean.getSiteCollection(filterCatalogD.getCatalogId(), null, 0,
                            SearchCriteria.ORDER_BY_SHORT_DESC);

        }

        //find related sites
        SiteDataVector relatedSites =
                catalogInfBean.getSiteCollection(catalogId, null, 0,
                        SearchCriteria.ORDER_BY_SHORT_DESC);

        if (filterSites != null) {
            relatedSites = applyFilterSite(relatedSites, filterSites);
        }
        //
        SiteDataVector sites = null;
        if (!pForm.getConfShowConfguredOnlyFl()) {
            if (filterSites == null) {
                sites = siteBean.getAllSites(0, Site.ORDER_BY_NAME);
            } else {
                sites = filterSites;
            }
        } else {
            sites = relatedSites;
        }
        session.setAttribute("catalog.sites.vector", sites);

        String[] selectIds = new String[relatedSites.size()];
        IdVector acctIds = new IdVector();


        int ind = 0;
        for (Object relatedSite : relatedSites) {
            SiteData sD = (SiteData) relatedSite;
            int id = sD.getBusEntity().getBusEntityId();
            acctIds.add(id);
            selectIds[ind++] = "" + id;
        }

        // the currently associated sites are checked/selected
        pForm.setSelectIds(selectIds);

        // list of all associated site ids, in the update this
        // will be compared with the selected
        session.setAttribute("catalog.assoc.ids", acctIds);

  }

    private static SiteDataVector applyFilterSite(SiteDataVector siteDV, SiteDataVector filterSiteDV) {

        SiteDataVector crossSites = new SiteDataVector();
        if (siteDV == null || filterSiteDV == null) {
            return crossSites;
        }

        SiteData wrkFltSiteD = null;
        for (Iterator iter = siteDV.iterator(), iter1 = filterSiteDV.iterator();
             iter.hasNext();) {
            SiteData sD = (SiteData) iter.next();
            String shortDesc = sD.getBusEntity().getShortDesc();
            while (wrkFltSiteD != null || iter1.hasNext()) {
                if (wrkFltSiteD == null) wrkFltSiteD = (SiteData) iter1.next();
                int comp = shortDesc.compareTo(wrkFltSiteD.getBusEntity().getShortDesc());
                if (comp < 0) {
                    break;
                }
                if (comp > 0) {
                    wrkFltSiteD = null;
                    continue;
                }
                if (sD.getBusEntity().getBusEntityId() == wrkFltSiteD.getBusEntity().getBusEntityId()) {
                    crossSites.add(sD);
                    wrkFltSiteD = null;
                    break;
                }
                wrkFltSiteD = null;
                continue;
            }
        }
        return crossSites;
    }

  /**
     * <code>update</code> method.
     *
     * @param  request        a <code>HttpServletRequest</code> value
     * @param  form           an <code>ActionForm</code> value
     * @return an <code>ActionErrors</code> value
     * @exception  Exception  if an error occurs
     */
  public static ActionErrors update(HttpServletRequest request,
                      ActionForm form)
    throws Exception {

    ActionErrors ae =  new ActionErrors();
    HttpSession session = request.getSession();

    StoreCatalogMgrForm pForm = (StoreCatalogMgrForm)form;
      String configType = pForm.getConfType();

      if (configType.equals("Items")) {
        //updateItemDist(request, form);
      } else if (configType.equals("Distributors")) {
          ae = updateDist(request, form);
      } else if (configType.equals("Accounts")) {
          ae = updateAccount(request, form);
      } else if (configType.equals("Sites")) {
          ae = updateSite(request, form);
      }

    return ae;
  }

    /**
     *  <code>updateDist</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */

  public static ActionErrors updateDist(HttpServletRequest request,
                  ActionForm form) throws Exception {
   ActionErrors ae = new ActionErrors();
   HttpSession session = request.getSession();
   APIAccess factory = new APIAccess();
   StoreCatalogMgrForm pForm = (StoreCatalogMgrForm)form;

     int catalogId = pForm.getCatalogDetail().getCatalogId();
     int storeId = pForm.getStoreId();
     String user =(String)session.getAttribute(Constants.USER_NAME);


     Catalog catalogBean = factory.getCatalogAPI();
     Distributor distBean = factory.getDistributorAPI();
     CatalogInformation catinfo=factory.getCatalogInformationAPI();
     // get list of dist ids displayed
     String[] displayed = pForm.getDisplayIds();

     // get list of dist ids selected
     String[] selected = pForm.getSelectIds();

     // get list of currently associated dist ids
     IdVector assocDistIds =
       (IdVector)session.getAttribute("catalog.assoc.ids");

   // Looking for two cases:
   // 1. dist is selected, but not currently associated - this
   //    means we need to add the association
   // 2. dist is not selected, but is currently associated - this
   //    means we need to remove the association

     for (int i = 0; i < displayed.length; i++) {
       String did = displayed[i];
       boolean foundFl = false;
       for (int j = 0; j < selected.length; j++) {
           if (did.equals(selected[j])) {
             foundFl = true;
             break;
           }
       }
       Integer idI = new Integer(did);
       int assocIndex = assocDistIds.indexOf(idI);
       if (foundFl) {
           if (assocIndex < 0) {
             // we need to add the association, the selected list
             // has the id, but not on assoc sites list
         try {
                catalogBean.addCatalogAssoc(catalogId, idI.intValue(),
                                 RefCodeNames.CATALOG_ASSOC_CD.CATALOG_DISTRIBUTOR);
         }catch (Exception exc) {
           String errorMess = exc.getMessage();
           int ind1 = -1;
           int ind2 = -1;
           if(errorMess==null) throw exc;
           ind1 = errorMess.indexOf("^clw^");
           if(ind1>=0) {
             ind2 = errorMess.indexOf("^clw^", ind1+3);
             if(ind2>0) {
              errorMess = errorMess.substring(ind1+5,ind2);
              ae.add("error",new ActionError("error.simpleGenericError",errorMess));
              return ae;
             }
           }
           throw exc;
         }
             assocDistIds.add(idI);
           }
       } else if (assocIndex >= 0) {
           // we need to remove the association, the selected list
           // doesn't have the id, but it is on the assoc catalogs list
           // (means it was 'unselected')
           CatalogAssocDataVector catAssocDV = catinfo.getCatalogAssoc(catalogId,0,RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR);
           if(catAssocDV!=null&&catAssocDV.size()>0&&((CatalogAssocData)(catAssocDV.get(0))).getBusEntityId()==idI.intValue())
           {
           ae.add("error1",new ActionError("error.simpleGenericError","It is impossible to remove distributor because it assigned as main distributor for the catalog"));
           }
           else{
           catalogBean.removeCatalogAssoc(catalogId, idI.intValue(), user);
           assocDistIds.remove(assocIndex);
           }
       }
     }
       /////////////////////////////////////////////////////////
       //////////////////to set up  main distributor/////////////
       /////////////////////////////////////////////////////////

        String mainDistId = pForm.getSelectedMainDistributorId();
        Integer mainDistIdInt=null;
        if(mainDistId!=null&&!mainDistId.trim().equals(""))
        {
            try {

                mainDistIdInt=new Integer(mainDistId);
                // Looking:
                // main  dist is selected and  dist not currently associated
                boolean assocFl= assocDistIds.indexOf(mainDistIdInt)!=-1?true:false;
                if(!assocFl)  ae.add("error2",new ActionError("error.simpleGenericError","The selected distributor is not assigned to the catalog"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // ae.add("error3",new ActionError("error.simpleGenericError","Not integer format distributor Id  "));
                throw new Exception(e.getMessage());
            }
        }
        else
        {   //Reset of data about the main distributor
            if(mainDistId!=null&&mainDistId.trim().equals("")){
                //to get  the currently  main distributor
                CatalogAssocDataVector catAssocDV = catinfo.getCatalogAssoc(catalogId,0,RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR);
                if(catAssocDV!=null&&catAssocDV.size()>0)
                {
                    //to remove the current main distributor
                    CatalogAssocData catAssocD=(CatalogAssocData) catAssocDV.get(0);
                    catalogBean.removeCatalogAssoc(catAssocD.getCatalogAssocId(), user);

                }
            }
        }

        if(mainDistIdInt!=null&&ae.size()==0)
        {
            //to get  the currently  main distributor
            CatalogAssocDataVector catAssocDVDist = catinfo.getCatalogAssoc(catalogId,0,RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR);
            if(catAssocDVDist==null)  ae.add("error3",new ActionError("error.simpleGenericError","It is impossible to assign the main distributor because there is no information from a database "));
            if(ae.size()==0&&catAssocDVDist.size()>0){
                CatalogAssocData catAssocDataDist=(CatalogAssocData) catAssocDVDist.get(0);
                if(catAssocDataDist!=null&&catAssocDataDist.getBusEntityId()!=mainDistIdInt.intValue())
                {

                    try {
                        //to remove the current main distributor
                        catalogBean.removeCatalogAssoc(catAssocDataDist.getCatalogAssocId(),user);
                    } catch (RemoteException e) {
                        try {
                            ae=StringUtils.getUiErrorMess(e);
                        } catch (Exception e1) {
                            ae.add("error4",new ActionError("error.simpleGenericError",e1.getMessage()));
                            ae.add("error5",new ActionError("error.simpleGenericError","It is impossible to assign the main distributor"));
                        }
                    }

                    if(ae.size()==0)
                        try {
                            catalogBean.addMainDistributorAssocCd(catalogId, mainDistIdInt.intValue(),user);
                        } catch (Exception e) {
                            try {
                                ae=StringUtils.getUiErrorMess(e);
                            } catch (Exception e1) {
                                ae.add("error6",new ActionError("error.simpleGenericError",e1.getMessage()));
                                ae.add("error7",new ActionError("error.simpleGenericError","It is impossible to assign the main distributor"));
                            }
                        }
                }
            }   else    {
                //to add if is not present current
                if(ae.size()==0)
                    try {
                        catalogBean.addMainDistributorAssocCd(catalogId, mainDistIdInt.intValue(),user);
                    } catch (Exception e) {
                        try {
                            ae=StringUtils.getUiErrorMess(e);
                        } catch (Exception e1) {
                            ae.add("error8",new ActionError("error.simpleGenericError",e1.getMessage()));
                            ae.add("error9",new ActionError("error.simpleGenericError","It is impossible to assign the main distributor"));
                        }
                    }
            }
        }

       //////////////////////////////////////
      //////////////////////////////////////
      /////////////////////////////////////

    // update with changes
      session.setAttribute("catalog.assoc.ids", assocDistIds);
      selected = new String[assocDistIds.size()];

      int ind = 0;
      for(Iterator iter = assocDistIds.iterator(); iter.hasNext();) {
        selected[ind++] =  iter.next().toString();
      }
      pForm.setSelectIds(selected);

    return ae;
    }

    /**
     * <code>updateItemDist</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
  /*
    public static void updateItemDist(HttpServletRequest request,
				      ActionForm form)
	throws Exception
    {
	HttpSession session = request.getSession();

	StoreCatalogMgrForm pForm = (StoreCatalogMgrForm)form;

        APIAccess factory = new APIAccess();
	Catalog catalogBean = factory.getCatalogAPI();

  	CatalogMgrDetailForm detailForm = 
  	    (CatalogMgrDetailForm)session.getAttribute("CATALOG_DETAIL_FORM");
  	int catalogId = detailForm.getDetail().getCatalogId();

	int distId = 0;
	try {
	    distId = Integer.parseInt(pForm.getDistributorId());
	} catch (NumberFormatException ne) {
	    // didn't give us a distributor id, nothing to do
	    return;
	}
	
        String cuser = (String) session.getAttribute(Constants.USER_NAME);
	ItemDataVector exceptionItems = 
               catalogBean.updateProductDistributor(catalogId, distId, pForm.getOverwriteDistId(), cuser);
        if(exceptionItems.size()>0) {
          session.setAttribute("catalog.item.distributor.error",exceptionItems);
        }else{
          session.removeAttribute("catalog.item.distributor.error");
        }

	return;
    }
  */
    /**
     * <code>updateAccount</code> method.
     *
     * @param  request        a <code>HttpServletRequest</code> value
     * @param  form           an <code>ActionForm</code> value
     * @return an <code>ActionErrors</code> value
     * @exception  Exception  if an error occurs
     */
  public static ActionErrors updateAccount(HttpServletRequest request,
                         ActionForm form)
    throws Exception {


    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    APIAccess factory = new APIAccess();
    StoreCatalogMgrForm pForm = (StoreCatalogMgrForm)form;

    int catalogId = pForm.getCatalogDetail().getCatalogId();
      int storeId = pForm.getStoreId();
      String user =(String)session.getAttribute(Constants.USER_NAME);


      Catalog catalogBean = factory.getCatalogAPI();
      Account accountBean = factory.getAccountAPI();
    CatalogInformation catinfo = factory.getCatalogInformationAPI();


      // get list of account ids displayed
      String[] displayed = pForm.getDisplayIds();

      // get list of account ids selected
      String[] selected = pForm.getSelectIds();
    //5029
      if(selected!=null && selected.length>0) {
	      IdVector accountIds =  new IdVector();
	      int accountId = 0;
	      for(int i=0;i<selected.length;i++) {
	    	  accountId = Utility.parseInt(selected[i]);
	    	  if(accountId>0) {
	    		  accountIds.add(accountId);
	    	  }
	      }
	      
	      if(accountIds.size()>0) {
		      AccountDataVector accountDataVector = accountBean.getAccountCollection(accountIds);
		      if(accountDataVector!=null && accountDataVector.size()>0) {
		    	  String accountThreshold = RefCodeNames.BUDGET_THRESHOLD_TYPE.ACCOUNT_BUDGET_THRESHOLD;
			      String siteThreshold = RefCodeNames.BUDGET_THRESHOLD_TYPE.SITE_BUDGET_THRESHOLD;
			      String noneThreshold = RefCodeNames.BUDGET_THRESHOLD_TYPE.NONE;
			      AccountData accountData = null;
			      int accountThresholdCount = 0;
			      int siteThresholdCount = 0;
			      int noneThresholdCount = 0;
			      for(int i=0; i<accountDataVector.size(); i++) {
			    	  accountData = (AccountData)accountDataVector.get(i);
			    	  if(accountThreshold.equalsIgnoreCase(accountData.getBudgetThresholdType())) {
			    		  accountThresholdCount++;
			    	  }else if(siteThreshold.equalsIgnoreCase(accountData.getBudgetThresholdType())){
			    		  siteThresholdCount++;
			    	  }else if(noneThreshold.equalsIgnoreCase(accountData.getBudgetThresholdType())){
			    		  noneThresholdCount++;
			    	  }
			      }
		    	  if(accountThresholdCount>0 && siteThresholdCount>0) {
		    		  String errorMess = "Budget threshold preferences differ. Unable to configure.";
		              ae.add("error",new ActionError("error.simpleGenericError",errorMess));
		              return ae;
		    	  }else if(accountThresholdCount>0 && noneThresholdCount>0) {
		    		  String errorMess = "Budget threshold preferences differ. Unable to configure.";
		              ae.add("error",new ActionError("error.simpleGenericError",errorMess));
		              return ae;
		    	  }
		      }
	      }
      }
	      //5029 end
      
      
      
      // get list of currently associated account ids
      IdVector assocAccountIds =
        (IdVector)session.getAttribute("catalog.assoc.ids");

    // Looking for two cases:
    // 1. account is selected, but not currently associated - this
    //    means we need to add the association
    // 2. account is not selected, but is currently associated - this
    //    means we need to remove the association

      for (int ii = 0; ii < displayed.length; ii++) {
        String did = displayed[ii];
        boolean foundId = false;
        for (int jj = 0; jj < selected.length; jj++) {
            if (did.equals(selected[jj])) {
              foundId = true;
              break;
            }
        }
        int id = Integer.parseInt(did);
        Integer idI = new Integer(id);
        int assocIndex = assocAccountIds.indexOf(idI);

        if(foundId) {
        if(assocIndex < 0) {
            CatalogData cat = (CatalogData)
                                          session.getAttribute("Catalog");
            // STJ-4659
            // remove all account sites association with this catalog
            catalogBean.removeCatalogAccountAssoc(catalogId, id, user);
            

          try {
                 catalogBean.addCatalogAssoc(catalogId, id,
                                       RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
          }catch (Exception exc) {
            String errorMess = exc.getMessage();
            int ind1 = -1;
            int ind2 = -1;
            if(errorMess==null) throw exc;
            ind1 = errorMess.indexOf("^clw^");
            if(ind1>=0) {
              ind2 = errorMess.indexOf("^clw^", ind1+3);
              if(ind2>0) {
               errorMess = errorMess.substring(ind1+5,ind2);
               ae.add("error",new ActionError("error.simpleGenericError",errorMess));
               return ae;
              }
            }
            throw exc;
          }
              assocAccountIds.add(idI);
        }
        } else if (assocIndex >= 0) {
        // we need to remove the association, the selected list
        // doesn't have the id, but it is on the assoc catalogs list
        // (means it was 'unselected')

            // STJ-4659
            // remove all account sites association with this catalog
            catalogBean.removeCatalogAccountAssoc(catalogId, id, user);
            assocAccountIds.remove(assocIndex);
        }
      }

    CatalogData superCatalogD = catinfo.getSuperCatalog(catalogId);
    int superCatalogId = (superCatalogD==null)?0:superCatalogD.getCatalogId();
    pForm.setMasterCatalogId(superCatalogId);

      // update with changes
      session.setAttribute("catalog.assoc.ids", assocAccountIds);
      selected = new String[assocAccountIds.size()];

      int ind = 0;
      String newAcctsList = " ";
      for (Iterator iter = assocAccountIds.iterator(); iter.hasNext();) {
        selected[ind] = new String(iter.next().toString());
        newAcctsList += selected[ind] + " ";
        ind++;
      }


      pForm.setSelectIds(selected);

    return ae;
  }

    /**
     *  <code>updateSite</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
  public static ActionErrors updateSite(HttpServletRequest request,
                  ActionForm form)
    throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
      String user =(String)session.getAttribute(Constants.USER_NAME);
    StoreCatalogMgrForm pForm = (StoreCatalogMgrForm)form;

    int catalogId = pForm.getCatalogDetail().getCatalogId();
      int storeId = pForm.getStoreId();

      Catalog catalogBean = factory.getCatalogAPI();
      Site siteBean = factory.getSiteAPI();

      // get list of site ids displayed
      String[] displayed = pForm.getDisplayIds();

      // get list of site ids selected
      String[] selected = pForm.getSelectIds();

      // get list of currently associated site ids

      IdVector assocSiteIds =
        (IdVector)session.getAttribute("catalog.assoc.ids");

    // Looking for two cases:
    // 1. site is selected, but not currently associated - this
    //    means we need to add the association
    // 2. site is not selected, but is currently associated - this
    //    means we need to remove the association

      for (int ii = 0; ii < displayed.length; ii++) {
        String did = displayed[ii];
        boolean foundId = false;
        for (int jj = 0; jj < selected.length; jj++) {
          if (did.equals(selected[jj])) {
            foundId = true;
            break;
          }
      }
    int id = Integer.parseInt(did);
      Integer idI = new Integer(id);
        int assocIndex = assocSiteIds.indexOf(idI);
        if (foundId) {
            if (assocIndex < 0) {
          if(pForm.getConfMoveSitesFl()) {
                catalogBean.removeCatalogSiteAssoc(0,id, user);
          }
          try {
                catalogBean.addCatalogAssoc(catalogId, id,user);
          } catch (Exception exc) {
            String errorMess = exc.getMessage();
            int ind1 = -1;
            int ind2 = -1;
            if(errorMess==null) throw exc;
            ind1 = errorMess.indexOf("^clw^");
            if(ind1>=0) {
              ind2 = errorMess.indexOf("^clw^", ind1+3);
              if(ind2>0) {
               errorMess = errorMess.substring(ind1+5,ind2);
               ae.add("error",new ActionError("error.simpleGenericError",errorMess));
               return ae;
              }
            }
            throw exc;
          }
              assocSiteIds.add(idI);
            }
        } else if (assocIndex >= 0) {
            // we need to remove the association, the selected list
            // doesn't have the id, but it is on the assoc catalogs list
            // (means it was 'unselected')
            catalogBean.removeCatalogAssoc(catalogId, id, user);
            assocSiteIds.remove(assocIndex);
        }
      }

      // update with changes
      session.setAttribute("catalog.assoc.ids", assocSiteIds);
      selected = new String[assocSiteIds.size()];

      int ind = 0;
      for (Iterator iter = assocSiteIds.iterator(); iter.hasNext();) {
        selected[ind++] = new String(iter.next().toString());
      }
      pForm.setSelectIds(selected);

    return ae;
  }

    /**
     *  <code>sort</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
  public static void sort(HttpServletRequest request,
                ActionForm form)
    throws Exception {

    // Get a reference to the admin facade
    HttpSession session = request.getSession();

      StoreCatalogMgrForm configForm = (StoreCatalogMgrForm)form;
      String configType = request.getParameter("configType");

      if (configType.equals("Items")) {
        //sortItemDist(request, form);
      } else if (configType.equals("Distributors")) {
        //sortDist(request, form);
      } else if (configType.equals("Accounts")) {
          sortAccount(request, form);
      } else if (configType.equals("Sites")) {
          sortSite(request, form);
      } else if (configType.equals("Users")) {
          sortUser(request, form);
      }

     return;
  }

    /**
     *  <code>sortItemDist</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    /*
    public static void sortItemDist(HttpServletRequest request,
				    ActionForm form)
	throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();

  	StoreCatalogMgrForm configForm = (StoreCatalogMgrForm)form;

	String sortField = request.getParameter("sortField");

	DistributorDataVector distributors =
	    (DistributorDataVector)session.getAttribute("catalog.item.distributors.vector");
	if (distributors == null) {
	    return;
	}
	DisplayListSort.sort(distributors, sortField);
	    
	// so that the display correctly shows the type of config being done
	configForm.setConfigType("Items");

        return;
    }
   */
    /**
     *  <code>sortDist</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
  /*
    public static void sortDist(HttpServletRequest request,
				ActionForm form)
	throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();

  	StoreCatalogMgrForm configForm = (StoreCatalogMgrForm)form;

	String sortField = request.getParameter("sortField");

	DistributorDataVector distributors =
	    (DistributorDataVector)session.getAttribute("catalog.distributors.vector");
	if (distributors == null) {
	    return;
	}
	DisplayListSort.sort(distributors, sortField);

	// Need to init the selected/checked distributors
	IdVector assocDistIds = 
	    (IdVector)session.getAttribute("catalog.assoc.ids");
	String[] selected = new String[assocDistIds.size()];
	Iterator assocI = assocDistIds.iterator();
	int i = 0;
	while (assocI.hasNext()) {
	    selected[i++] = new String(assocI.next().toString());
	}
	configForm.setSelectIds(selected);
	    
	// so that the display correctly shows the type of config being done
	configForm.setConfigType("Distributors");

        return;
    }
*/
    /**
     *  <code>sortAccount</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
  public static void sortAccount(HttpServletRequest request,
                   ActionForm form)
    throws Exception {

    // Get a reference to the admin facade
    HttpSession session = request.getSession();

    StoreCatalogMgrForm pForm = (StoreCatalogMgrForm)form;
    String sortField = request.getParameter("sortField");

    AccountDataVector accounts =
        (AccountDataVector)session.getAttribute("catalog.accounts.vector");
    if (accounts != null) {
    	DisplayListSort.sort(accounts, sortField);
    } else {
    	AccountSearchResultViewVector acntSrchRsltVctr = (AccountSearchResultViewVector)session.getAttribute("Account.search.result");
    	if(acntSrchRsltVctr != null){
    		DisplayListSort.sort(acntSrchRsltVctr, sortField);
    	}else{
    		return;
    	}
    	
    }
    //DisplayListSort.sort(accounts, sortField);

    // Need to init the selected/checked accounts
    IdVector assocDistIds =
        (IdVector)session.getAttribute("catalog.assoc.ids");
    String[] selected = new String[assocDistIds.size()];

    int ind = 0;
    for (Iterator iter = assocDistIds.iterator();iter.hasNext();) {
        selected[ind++] = new String(iter.next().toString());
    }
    pForm.setSelectIds(selected);

    // so that the display correctly shows the type of config being done
    pForm.setConfType("Accounts");

    return;
    }

    /**
     *  <code>sortUser</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
  public static void sortUser(HttpServletRequest request,
                   ActionForm form)
    throws Exception {
    // Get a reference to the admin facade
    HttpSession session = request.getSession();

    StoreCatalogMgrForm pForm = (StoreCatalogMgrForm)form;
    String sortField = request.getParameter("sortField");

    UserDataVector users =
        (UserDataVector)session.getAttribute("Related.users.vector");
    if (users == null) {
        return;
    }
    DisplayListSort.sort(users, sortField);

    pForm.setConfType("Users");

    return;
    }

  /**
     *  <code>sortSite</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
  public static void sortSite(HttpServletRequest request,
                ActionForm form)
    throws Exception {

        // Get a reference to the admin facade
    HttpSession session = request.getSession();

      StoreCatalogMgrForm pForm = (StoreCatalogMgrForm)form;

      String sortField = request.getParameter("sortField");

      SiteViewVector sites =
        (SiteViewVector)session.getAttribute("catalog.sites.vector");
      if (sites == null) {
        return;
      }
      DisplayListSort.sort(sites, sortField);

      // Need to init the selected/checked sites
      IdVector assocDistIds =
        (IdVector)session.getAttribute("catalog.assoc.ids");
      String[] selected = new String[assocDistIds.size()];

      int ind = 0;
      for (Iterator iter = assocDistIds.iterator(); iter.hasNext();) {
        selected[ind++] = new String(iter.next().toString());
      }
      pForm.setSelectIds(selected);

      // so that the display correctly shows the type of config being done
      pForm.setConfType("Sites");

    return;
  }

    private static ActionErrors testCatalogAssoc(int pBusEntityId)
    throws Exception{

    ActionErrors ae = new ActionErrors();

    APIAccess factory = new APIAccess();

    CatalogInformation catalogInfoBean = factory.getCatalogInformationAPI();
    CatalogDataVector catVec = new CatalogDataVector();
    CatalogData cat = null;
    Integer id = new Integer(pBusEntityId);

    catVec = catalogInfoBean.getCatalogsCollectionByBusEntity(pBusEntityId);

    for(int j=0; j<catVec.size(); j++){

        cat = (CatalogData)catVec.get(j);

        String stat = cat.getCatalogStatusCd();
        String type = cat.getCatalogTypeCd();
        //if any attached accounts are active, we can't attach
        if(stat.equals(RefCodeNames.CATALOG_STATUS_CD.ACTIVE) && type.equals("ACCOUNT")){
        Integer catId = new Integer(cat.getCatalogId());
        String s1 = catId.toString();
        String s2 = id.toString();
        ae.add("Catalog Config",
               new ActionError("catalog.config.error", s1, s2));

        }
    }

    return ae;

    }


}







