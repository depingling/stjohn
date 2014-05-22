
package com.cleanwise.view.logic;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Catalog;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.DistributorDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteDataVector;
import com.cleanwise.service.api.value.SiteView;
import com.cleanwise.service.api.value.SiteViewVector;
import com.cleanwise.view.forms.CatalogMgrConfigForm;
import com.cleanwise.view.forms.CatalogMgrDetailForm;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.SessionTool;

/**
 * <code>CatalogMgrConfigLogic</code>
 *
 * @author     tbesser
 * @created    August 15, 2001
 */
public class CatalogMgrConfigLogic {
    private static final Logger log = Logger.getLogger(CatalogMgrConfigLogic.class);

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
        if(form instanceof CatalogMgrConfigForm) {
          CatalogMgrConfigForm  theForm = (CatalogMgrConfigForm) form;
          theForm.setOverwriteDistId(0);
        }

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
  	CatalogMgrConfigForm configForm = (CatalogMgrConfigForm)form;

  	String configType = configForm.getConfigType();

	String fieldValue = configForm.getSearchField();
  	if (fieldValue == null || fieldValue.equals("")) {
  	    // treat just like a 'view all' request
	    if (configType.equals("Items")) {
		getAllItemDist(request, form);
	    } else if (configType.equals("Distributors")) {
		getAllDist(request, form);
  	    } else if (configType.equals("Accounts")) {
  		getAllAccount(request, form);
  	    } else if (configType.equals("Sites")) {
                searchSite(request, form);
	    }
  	    return;
  	}

	if (configType.equals("Items")) {
	    searchItemDist(request, form);
	} else if (configType.equals("Distributors")) {
	    searchDist(request, form);
  	} else if (configType.equals("Accounts")) {
  	    searchAccount(request, form);
  	} else if (configType.equals("Sites")) {
  	    searchSite(request, form);
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
    public static void searchItemDist(HttpServletRequest request,
				      ActionForm form)
	throws Exception {

        HttpSession session = request.getSession();

	APIAccess factory = new APIAccess();
	CatalogInformation catalogInfBean = factory.getCatalogInformationAPI();

  	CatalogMgrConfigForm configForm = (CatalogMgrConfigForm)form;

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
/*
        if (fieldSearchType.equals("nameBegins")) {
	    distributors =
		catalogInfBean.getDistributorCollection(catalogId,
							fieldValue,
							SearchCriteria.BEGINS_WITH_IGNORE_CASE,
							SearchCriteria.ORDER_BY_SHORT_DESC);
	} else { // "nameContains"
	    distributors =
		catalogInfBean.getDistributorCollection(catalogId,
							fieldValue,
							SearchCriteria.CONTAINS_IGNORE_CASE,
							SearchCriteria.ORDER_BY_SHORT_DESC);
	}
*/
	session.setAttribute("catalog.item.distributors.vector", distributors);

        return;
    }

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

  	CatalogMgrConfigForm configForm = (CatalogMgrConfigForm)form;

	String fieldValue = configForm.getSearchField();
	String fieldSearchType = configForm.getSearchType();

  	CatalogMgrDetailForm detailForm =
  	    (CatalogMgrDetailForm)session.getAttribute("CATALOG_DETAIL_FORM");
  	int catalogId = detailForm.getDetail().getCatalogId();

	String catalogType = detailForm.getDetail().getCatalogTypeCd();

	Distributor distributorBean = factory.getDistributorAPI();
	DistributorDataVector distributors = null;

	int match;
	if (fieldSearchType.equals("nameBegins")) {
	    match = SearchCriteria.BEGINS_WITH_IGNORE_CASE;
	} else { // "nameContains"
	    match = SearchCriteria.CONTAINS_IGNORE_CASE;
	}

	// If the catalog is the STORE catalog, select from the pool
	// of all distributors.  If not, then you get the distributors
	// from the super/parent catalog.  e.g. the ACCOUNT catalog
	// if configuring a SHOPPING catalog, the STORE catalog if
	// configuring the ACCOUNT catalog
	if (catalogType.equals(RefCodeNames.CATALOG_TYPE_CD.STORE)) {
	    if (match == SearchCriteria.CONTAINS_IGNORE_CASE) {
		distributors =
		    distributorBean.getDistributorByName(fieldValue,
							 Distributor.CONTAINS_IGNORE_CASE,
							 Distributor.ORDER_BY_NAME);
	    } else {
		distributors =
		    distributorBean.getDistributorByName(fieldValue,
							 Distributor.BEGINS_WITH_IGNORE_CASE,
							 Distributor.ORDER_BY_NAME);
	    }
	} else {
	    int superCatalogId = detailForm.getMasterCatalogId();
	    distributors =
		catalogInfBean.getDistributorCollection(superCatalogId,
							fieldValue,
							match,
							SearchCriteria.ORDER_BY_SHORT_DESC);
	}
	session.setAttribute("catalog.distributors.vector", distributors);
        DistributorDataVector dists =
            catalogInfBean.getDistributorCollection(catalogId,
						    fieldValue,
						    match,
						    SearchCriteria.ORDER_BY_ID);
        String[] selectIds = new String[dists.size()];
	IdVector distIds = new IdVector();

	Iterator distI = dists.iterator();
	int i = 0;
	while (distI.hasNext()) {
	    Integer id =
		new Integer(((DistributorData)distI.next()).getBusEntity().getBusEntityId());
	    distIds.add(id);
	    selectIds[i++] = new String(id.toString());
	}

	// the currently associated distributors are checked/selected
	configForm.setSelectIds(selectIds);

	// list of all associated distributor ids, in the update this
	// will be compared with the selected
	session.setAttribute("catalog.assoc.ids", distIds);

        return;
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

  	CatalogMgrConfigForm configForm = (CatalogMgrConfigForm)form;

	String fieldValue = configForm.getSearchField();
	String fieldSearchType = configForm.getSearchType();

  	CatalogMgrDetailForm detailForm =
  	    (CatalogMgrDetailForm)session.getAttribute("CATALOG_DETAIL_FORM");
  	int catalogId = detailForm.getDetail().getCatalogId();
	int storeId = detailForm.getStoreId();

  	APIAccess factory = new APIAccess();
	CatalogInformation catalogInfBean = factory.getCatalogInformationAPI();

	Account accountBean = factory.getAccountAPI();

	AccountDataVector accounts;
	AccountDataVector relatedAccounts;
	if (fieldSearchType.equals("nameBegins")) {
	    accounts =
		accountBean.getAccountByName(fieldValue, storeId,
					     Account.BEGINS_WITH_IGNORE_CASE,
					     Account.ORDER_BY_NAME);

	    //find related accounts
	    relatedAccounts =
		catalogInfBean.getAccountCollection(catalogId, fieldValue,
						    SearchCriteria.BEGINS_WITH_IGNORE_CASE,
						    SearchCriteria.ORDER_BY_SHORT_DESC);
	} else { // "nameContains"
	    accounts =
		accountBean.getAccountByName(fieldValue, storeId,
					     Account.CONTAINS_IGNORE_CASE,
					     Account.ORDER_BY_NAME);

	    //find related accounts
	    relatedAccounts =
		catalogInfBean.getAccountCollection(catalogId, fieldValue,
						    SearchCriteria.CONTAINS_IGNORE_CASE,
						    SearchCriteria.ORDER_BY_SHORT_DESC);
	}

	session.setAttribute("catalog.accounts.vector", accounts);

	String[] selectIds = new String[relatedAccounts.size()];
	IdVector acctIds = new IdVector();

	Iterator acctI = relatedAccounts.iterator();
	int i = 0;
	while (acctI.hasNext()) {
	    Integer id =
		new Integer(((AccountData)acctI.next()).getBusEntity().getBusEntityId());
	    acctIds.add(id);
	    selectIds[i++] = new String(id.toString());
	}

	// the currently associated accounts are checked/selected
	configForm.setSelectIds(selectIds);

	// list of all associated account ids, in the update this
	// will be compared with the selected
	session.setAttribute("catalog.assoc.ids", acctIds);

        return;
    }

    /**
     *  <code>searchSite</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void searchSite(HttpServletRequest request,
				  ActionForm form)
	throws Exception {

        HttpSession session = request.getSession();

  	CatalogMgrConfigForm configForm = (CatalogMgrConfigForm)form;

	String fieldValue = configForm.getSearchField();
	String searchType = configForm.getSearchType();
        String state = configForm.getState();
        String city = configForm.getCity();
        String county = configForm.getCounty();
        String zip = configForm.getZipcode();
        String action = request.getParameter("action");

  	CatalogMgrDetailForm detailForm =
  	    (CatalogMgrDetailForm)session.getAttribute("CATALOG_DETAIL_FORM");
  	int catalogId = detailForm.getDetail().getCatalogId();
	int storeId = detailForm.getStoreId();

  	APIAccess factory = new APIAccess();
	CatalogInformation catalogInfBean = factory.getCatalogInformationAPI();

	Site siteBean = factory.getSiteAPI();

	SiteDataVector sdv = new SiteDataVector();
        SiteViewVector dv = new SiteViewVector();
	IdVector relatedSiteIds = new IdVector();

        BusEntityDataVector bedv =
	    catalogInfBean.getAccountCollection(catalogId);

	for ( int aidx = 0; aidx < bedv.size(); aidx++ ) {
	    BusEntityData bed = (BusEntityData)bedv.get(aidx);
	    int acctid = bed.getBusEntityId();
	    if(action.equals("View All")){
		SiteDataVector asdv = new SiteDataVector();
		asdv = siteBean.getAllSites(acctid, Site.ORDER_BY_ID, SessionTool.getCategoryToCostCenterViewByAcc(session, acctid));
		for ( int sidx = 0; sidx < asdv.size(); sidx++ ) {
		    sdv.add((SiteData)asdv.get(sidx));
		}
	    }
	    else{
		QueryRequest qr = new QueryRequest();

		if (searchType.equals("id") &&
		    fieldValue.length() > 0 ) {


		    Integer id = new Integer(fieldValue);
		    qr.filterBySiteId(id.intValue());
		    dv = siteBean.getSiteCollection(qr);
		} else {

		    qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);
		    String f = "";
		    if (searchType.equals("nameBegins") &&
			fieldValue.length() > 0 ) {
			qr.filterBySiteName(fieldValue,
					    QueryRequest.BEGINS_IGNORE_CASE);
		    }
		    else if (searchType.equals("nameContains") &&
			     fieldValue.length() > 0) {
			qr.filterBySiteName(fieldValue,
					    QueryRequest.CONTAINS_IGNORE_CASE);
		    }

		    f = city.trim();
		    if (f.length() > 0) {
			qr.filterByCity(f, QueryRequest.BEGINS_IGNORE_CASE);
		    }
            f = county;
            if(f==null) f="";
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

		qr.filterByAccountId(acctid);
		dv = siteBean.getSiteCollection(qr);
	    }

	    relatedSiteIds = catalogInfBean.getSiteIds(catalogId);
	    for(int i=0; i<dv.size(); i++){
		SiteView sv = (SiteView)dv.get(i);
		SiteData sd = siteBean.getSite(sv.getId(), sv.getAccountId(), false, SessionTool.getCategoryToCostCenterView(session, sv.getId()));
		sdv.add(sd);
	    }
        }

	session.setAttribute("catalog.sites.vector", sdv);

	String[] selectIds = new String[relatedSiteIds.size()];
        IdVector siteIds = new IdVector();
	for (int i=0,len=relatedSiteIds.size();i<len;i++){
            selectIds[i] = relatedSiteIds.get(i).toString();
            siteIds.add(relatedSiteIds.get(i));
        }

	// the currently associated sites are checked/selected
	configForm.setSelectIds(selectIds);

	// list of all associated site ids, in the update this
	// will be compared with the selected
	session.setAttribute("catalog.assoc.ids", siteIds);

        return;
    }

    /**
     *  <code>getAll</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void getAll(HttpServletRequest request,
			      ActionForm form)
	throws Exception {

	// reset the saved data
	init(request, form);

        HttpSession session = request.getSession();

  	CatalogMgrConfigForm configForm = (CatalogMgrConfigForm)form;
  	String configType = configForm.getConfigType();

	// removes any left-over settings
	configForm.setSearchField("");
	configForm.setSearchType("");

	if (configType.equals("Items")) {
	    getAllItemDist(request, form);
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
    public static void getAllItemDist(HttpServletRequest request,
				      ActionForm form)
	throws Exception {

        HttpSession session = request.getSession();

  	CatalogMgrConfigForm configForm = (CatalogMgrConfigForm)form;

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

  	CatalogMgrConfigForm configForm = (CatalogMgrConfigForm)form;

  	CatalogMgrDetailForm detailForm =
  	    (CatalogMgrDetailForm)session.getAttribute("CATALOG_DETAIL_FORM");
  	int catalogId = detailForm.getDetail().getCatalogId();

  	APIAccess factory = new APIAccess();
	CatalogInformation catalogInfBean = factory.getCatalogInformationAPI();

	String catalogType = detailForm.getDetail().getCatalogTypeCd();

	Distributor distributorBean = factory.getDistributorAPI();
	DistributorDataVector distributors = null;

	// If the catalog is the STORE catalog, select from the pool
	// of all distributors.  If not, then you get the distributors
	// from the super/parent catalog.  e.g. the ACCOUNT catalog
	// if configuring a SHOPPING catalog, the STORE catalog if
	// configuring the ACCOUNT catalog
	if (catalogType.equals(RefCodeNames.CATALOG_TYPE_CD.STORE)) {
	    distributors =
		distributorBean.getAllDistributors(Distributor.ORDER_BY_NAME);
	} else {
	    int superCatalogId = detailForm.getMasterCatalogId();
	    distributors =
		catalogInfBean.getDistributorCollection(superCatalogId,
							null,
							0,
							SearchCriteria.ORDER_BY_SHORT_DESC);
	}
	session.setAttribute("catalog.distributors.vector", distributors);

	//find related distributors
	DistributorDataVector dists =
	    catalogInfBean.getDistributorCollection(catalogId,
						    null,
						    0,
						    SearchCriteria.ORDER_BY_ID);
	String[] selectIds = new String[dists.size()];
	IdVector distIds = new IdVector();

	Iterator distI = dists.iterator();
	int i = 0;
	while (distI.hasNext()) {
	    Integer id =
		new Integer(((DistributorData)distI.next()).getBusEntity().getBusEntityId());
	    distIds.add(id);
	    selectIds[i++] = new String(id.toString());
	}

	// the currently associated distributors are checked/selected
	configForm.setSelectIds(selectIds);

	// list of all associated distributor ids, in the update this
	// will be compared with the selected
	session.setAttribute("catalog.assoc.ids", distIds);

        return;
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

  	CatalogMgrConfigForm configForm = (CatalogMgrConfigForm)form;

  	CatalogMgrDetailForm detailForm =
  	    (CatalogMgrDetailForm)session.getAttribute("CATALOG_DETAIL_FORM");
  	int catalogId = detailForm.getDetail().getCatalogId();
	int storeId = detailForm.getStoreId();

  	APIAccess factory = new APIAccess();
	CatalogInformation catalogInfBean = factory.getCatalogInformationAPI();

	Account accountBean = factory.getAccountAPI();
	AccountDataVector accounts =
	    accountBean.getAllAccounts(storeId, Account.ORDER_BY_NAME);
	session.setAttribute("catalog.accounts.vector", accounts);

	//find related accounts
	AccountDataVector relatedAccounts =
	    catalogInfBean.getAccountCollection(catalogId, null, 0,
						SearchCriteria.ORDER_BY_SHORT_DESC);

	String[] selectIds = new String[relatedAccounts.size()];
	IdVector acctIds = new IdVector();

	Iterator acctI = relatedAccounts.iterator();
	int i = 0;
	while (acctI.hasNext()) {
	    Integer id =
		new Integer(((AccountData)acctI.next()).getBusEntity().getBusEntityId());
	    acctIds.add(id);
	    selectIds[i++] = new String(id.toString());
	}

	// the currently associated accounts are checked/selected
	configForm.setSelectIds(selectIds);

	// list of all associated account ids, in the update this
	// will be compared with the selected
	session.setAttribute("catalog.assoc.ids", acctIds);

        return;
    }

    /**
     *  <code>getAllSite</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void getAllSite(HttpServletRequest request,
				  ActionForm form)
	throws Exception {

        HttpSession session = request.getSession();

  	CatalogMgrConfigForm configForm = (CatalogMgrConfigForm)form;
  	CatalogMgrDetailForm detailForm =
  	    (CatalogMgrDetailForm)session.getAttribute("CATALOG_DETAIL_FORM");
  	int catalogId = detailForm.getDetail().getCatalogId();
	int storeId = detailForm.getStoreId();

  	APIAccess factory = new APIAccess();
	CatalogInformation catalogInfBean = factory.getCatalogInformationAPI();

	Site siteBean = factory.getSiteAPI();
	// XXX - should just be getting sites for catalog store, not all sites
	SiteDataVector sites =
	    siteBean.getAllSites(0, Site.ORDER_BY_NAME);
	session.setAttribute("catalog.sites.vector", sites);

	//find related sites
	SiteDataVector relatedSites =
	    catalogInfBean.getSiteCollection(catalogId, null, 0,
					     SearchCriteria.ORDER_BY_SHORT_DESC);

	String[] selectIds = new String[relatedSites.size()];
	IdVector acctIds = new IdVector();

	Iterator acctI = relatedSites.iterator();
	int i = 0;
	while (acctI.hasNext()) {
	    Integer id =
		new Integer(((SiteData)acctI.next()).getBusEntity().getBusEntityId());
	    acctIds.add(id);
	    selectIds[i++] = new String(id.toString());
	}

	// the currently associated sites are checked/selected
	configForm.setSelectIds(selectIds);

	// list of all associated site ids, in the update this
	// will be compared with the selected
	session.setAttribute("catalog.assoc.ids", acctIds);

        return;
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

        CatalogMgrConfigForm sForm = (CatalogMgrConfigForm)form;
	String configType = sForm.getConfigType();

	if (configType.equals("Items")) {
	    updateItemDist(request, form);
	} else if (configType.equals("Distributors")) {
	    updateDist(request, form);
  	} else if (configType.equals("Accounts")) {
  	    ae = updateAccount(request, form);
  	} else if (configType.equals("Sites")) {
  	    updateSite(request, form);
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
    public static void updateDist(HttpServletRequest request,
				  ActionForm form)
	throws Exception {

        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();

  	CatalogMgrDetailForm detailForm =
  	    (CatalogMgrDetailForm)session.getAttribute("CATALOG_DETAIL_FORM");
  	int catalogId = detailForm.getDetail().getCatalogId();

        CatalogMgrConfigForm sForm = (CatalogMgrConfigForm)form;

	Catalog catalogBean = factory.getCatalogAPI();
	Distributor distBean = factory.getDistributorAPI();

	// get list of dist ids displayed
	String[] displayed = sForm.getDisplayIds();

	// get list of dist ids selected
	String[] selected = sForm.getSelectIds();

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
	    boolean foundId = false;
	    for (int j = 0; j < selected.length; j++) {
		if (did.equals(selected[j])) {
		    foundId = true;
		    break;
		}
	    }
	    Integer id = new Integer(did);
	    int assocIndex = assocDistIds.indexOf(id);
	    if (foundId) {
		if (assocIndex < 0) {
		    // we need to add the association, the selected list
		    // has the id, but not on assoc sites list
		    catalogBean.addCatalogAssoc(catalogId, id.intValue(),
						RefCodeNames.CATALOG_ASSOC_CD.CATALOG_DISTRIBUTOR);
		    assocDistIds.add(id);
		}
	    } else if (assocIndex >= 0) {
		// we need to remove the association, the selected list
		// doesn't have the id, but it is on the assoc catalogs list
		// (means it was 'unselected')
		String user =(String)session.getAttribute(Constants.USER_NAME);
		catalogBean.removeCatalogAssoc(catalogId, id.intValue(), user);
		assocDistIds.remove(assocIndex);
	    }
	}

	// update with changes
	session.setAttribute("catalog.assoc.ids", assocDistIds);
	selected = new String[assocDistIds.size()];
	Iterator assocI = assocDistIds.iterator();
	int i = 0;
	while (assocI.hasNext()) {
	    selected[i++] = new String(assocI.next().toString());
	}
	sForm.setSelectIds(selected);

        return;
    }

    /**
     * <code>updateItemDist</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void updateItemDist(HttpServletRequest request,
				      ActionForm form)
	throws Exception
    {
	HttpSession session = request.getSession();

	CatalogMgrConfigForm pForm = (CatalogMgrConfigForm)form;

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

  	CatalogMgrDetailForm detailForm =
  	    (CatalogMgrDetailForm)session.getAttribute("CATALOG_DETAIL_FORM");
  	int catalogId = detailForm.getDetail().getCatalogId();
	String user =(String)session.getAttribute(Constants.USER_NAME);

        CatalogMgrConfigForm sForm = (CatalogMgrConfigForm)form;

	Catalog catalogBean = factory.getCatalogAPI();
	Account accountBean = factory.getAccountAPI();
        CatalogInformation catinfo = factory.getCatalogInformationAPI();


	// get list of account ids displayed
	String[] displayed = sForm.getDisplayIds();

	// get list of account ids selected
	String[] selected = sForm.getSelectIds();

	// get list of currently associated account ids
	IdVector assocAccountIds =
	    (IdVector)session.getAttribute("catalog.assoc.ids");

	// Looking for two cases:
	// 1. account is selected, but not currently associated - this
	//    means we need to add the association
	// 2. account is not selected, but is currently associated - this
	//    means we need to remove the association

	for (int i = 0; i < displayed.length; i++) {
	    String did = displayed[i];
	    boolean foundId = false;
	    for (int j = 0; j < selected.length; j++) {
		if (did.equals(selected[j])) {
		    foundId = true;
		    break;
		}
	    }
	    Integer id = new Integer(did);
	    int assocIndex = assocAccountIds.indexOf(id);
	    if(foundId) {
		if(assocIndex < 0) {
		    CatalogData cat = (CatalogData)
			session.getAttribute("Catalog");

		    catalogBean.removeCatalogAssoc(catalogId,
						   id.intValue(), user);
		    catalogBean.addCatalogAssoc
			(catalogId, id.intValue(),
			 RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);

		    assocAccountIds.add(id);

                }
	    }
	    else if (assocIndex >= 0) {
		// we need to remove the association, the selected list
		// doesn't have the id, but it is on the assoc catalogs list
		// (means it was 'unselected')
		catalogBean.removeCatalogAssoc(catalogId, id.intValue(), user);
		assocAccountIds.remove(assocIndex);
	    }
	}

        CatalogData superCatalogD = catinfo.getSuperCatalog(catalogId);
        int superCatalogId = (superCatalogD==null)?0:superCatalogD.getCatalogId();
        detailForm.setMasterCatalogId(superCatalogId);

	// update with changes
	session.setAttribute("catalog.assoc.ids", assocAccountIds);
	selected = new String[assocAccountIds.size()];
	Iterator assocI = assocAccountIds.iterator();
	int i = 0;
	String newAcctsList = " ";
	while (assocI.hasNext()) {
	    selected[i] = new String(assocI.next().toString());
	    newAcctsList += selected[i] + " ";
	    i++;
	}

	log.info
	    ("NNN New accounts list for catalogId: " +
	     catalogId + " is: " + newAcctsList );

	sForm.setSelectIds(selected);

        return ae;
    }

    /**
     *  <code>updateSite</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void updateSite(HttpServletRequest request,
				  ActionForm form)
	throws Exception {

        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();

  	CatalogMgrDetailForm detailForm =
  	    (CatalogMgrDetailForm)session.getAttribute("CATALOG_DETAIL_FORM");
  	int catalogId = detailForm.getDetail().getCatalogId();
	String user =(String)session.getAttribute(Constants.USER_NAME);

        CatalogMgrConfigForm sForm = (CatalogMgrConfigForm)form;

	Catalog catalogBean = factory.getCatalogAPI();
	Site siteBean = factory.getSiteAPI();

	// get list of site ids displayed
	String[] displayed = sForm.getDisplayIds();

	// get list of site ids selected
	String[] selected = sForm.getSelectIds();

	// get list of currently associated site ids

	IdVector assocSiteIds =
	    (IdVector)session.getAttribute("catalog.assoc.ids");

	// Looking for two cases:
	// 1. site is selected, but not currently associated - this
	//    means we need to add the association
	// 2. site is not selected, but is currently associated - this
	//    means we need to remove the association

	for (int i = 0; i < displayed.length; i++) {
	    String did = displayed[i];
	    boolean foundId = false;
	    for (int j = 0; j < selected.length; j++) {
		if (did.equals(selected[j])) {
		    foundId = true;
		    break;
		}
	    }
	    Integer id = new Integer(did);
	    int assocIndex = assocSiteIds.indexOf(id);

	    if (foundId) {
		if (assocIndex < 0) {
		    // we need to add the association, the selected list
		    // has the id, but not on assoc sites list

		    // first remove any existing association (XXX this is
		    // business logic that belongs in the 'addCatalogAssoc')
		    catalogBean.removeCatalogAssoc(catalogId,
						   id.intValue(), user);
		    catalogBean.removeCatalogSiteAssoc(0,
						       id.intValue(), user);

		    catalogBean.addCatalogAssoc(catalogId, id.intValue(),
						RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
		    assocSiteIds.add(id);
		}
	    } else if (assocIndex >= 0) {
		// we need to remove the association, the selected list
		// doesn't have the id, but it is on the assoc catalogs list
		// (means it was 'unselected')
		catalogBean.removeCatalogAssoc(catalogId, id.intValue(), user);
		assocSiteIds.remove(assocIndex);
	    }
	}

	// update with changes
	session.setAttribute("catalog.assoc.ids", assocSiteIds);
	selected = new String[assocSiteIds.size()];
	Iterator assocI = assocSiteIds.iterator();
	int i = 0;
	while (assocI.hasNext()) {
	    selected[i++] = new String(assocI.next().toString());
	}
	sForm.setSelectIds(selected);

        return;
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

  	CatalogMgrConfigForm configForm = (CatalogMgrConfigForm)form;
  	String configType = request.getParameter("configType");

	if (configType.equals("Items")) {
	    sortItemDist(request, form);
	} else if (configType.equals("Distributors")) {
	    sortDist(request, form);
  	} else if (configType.equals("Accounts")) {
  	    sortAccount(request, form);
  	} else if (configType.equals("Sites")) {
  	    sortSite(request, form);
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
    public static void sortItemDist(HttpServletRequest request,
				    ActionForm form)
	throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();

  	CatalogMgrConfigForm configForm = (CatalogMgrConfigForm)form;

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

    /**
     *  <code>sortDist</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sortDist(HttpServletRequest request,
				ActionForm form)
	throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();

  	CatalogMgrConfigForm configForm = (CatalogMgrConfigForm)form;

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

  	CatalogMgrConfigForm configForm = (CatalogMgrConfigForm)form;

	String sortField = request.getParameter("sortField");

	AccountDataVector accounts =
	    (AccountDataVector)session.getAttribute("catalog.accounts.vector");
	if (accounts == null) {
	    return;
	}
	DisplayListSort.sort(accounts, sortField);

	// Need to init the selected/checked accounts
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
	configForm.setConfigType("Accounts");

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

  	CatalogMgrConfigForm configForm = (CatalogMgrConfigForm)form;

	String sortField = request.getParameter("sortField");

	SiteDataVector sites =
	    (SiteDataVector)session.getAttribute("catalog.sites.vector");
	if (sites == null) {
	    return;
	}
	DisplayListSort.sort(sites, sortField);

	// Need to init the selected/checked sites
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
	configForm.setConfigType("Sites");

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







