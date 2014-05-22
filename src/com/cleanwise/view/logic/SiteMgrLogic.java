
package com.cleanwise.view.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

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
import com.cleanwise.service.api.session.CustomerRequestPoNumber;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.Request;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.session.Workflow;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.ShoppingRestrictionsViewComparatorByItem;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccCategoryToCostCenterView;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.BlanketPoNumData;
import com.cleanwise.service.api.value.BlanketPoNumDataVector;
import com.cleanwise.service.api.value.BuildingServicesContractorView;
import com.cleanwise.service.api.value.BuildingServicesContractorViewVector;
import com.cleanwise.service.api.value.BusEntityAssocData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityFieldsData;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.ShoppingControlData;
import com.cleanwise.service.api.value.ShoppingControlDataVector;
import com.cleanwise.service.api.value.ShoppingControlItemView;
import com.cleanwise.service.api.value.ShoppingRestrictionsView;
import com.cleanwise.service.api.value.ShoppingRestrictionsViewVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteInventoryConfigView;
import com.cleanwise.service.api.value.SiteInventoryConfigViewVector;
import com.cleanwise.service.api.value.SiteSettingsData;
import com.cleanwise.service.api.value.SiteView;
import com.cleanwise.service.api.value.SiteViewVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.service.api.value.WorkflowData;
import com.cleanwise.service.api.value.WorkflowRuleDataVector;
import com.cleanwise.view.forms.BSCDetailForm;
import com.cleanwise.view.forms.BusEntitySearchForm;
import com.cleanwise.view.forms.InventoryForm;
import com.cleanwise.view.forms.SiteMgrConfigForm;
import com.cleanwise.view.forms.SiteMgrDetailForm;
import com.cleanwise.view.forms.SiteMgrSearchForm;
import com.cleanwise.view.forms.SiteShoppingControlForm;
import com.cleanwise.view.forms.UserMgrDetailForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;

/**
 *  <code>SiteMgrLogic</code> implements the logic needed to manipulate
 *  purchasing site records.
 *
 *@author     durval
 *@created    August 15, 2001
 */
public class SiteMgrLogic {
    private static final Logger log = Logger.getLogger(SiteMgrLogic.class);

    /**
     *  <code>getAll</code> sites, note that the Site list returned will be
     *  limited to a certain amount of records. It is up to the jsp page to
     *  detect this and to issued a subsequent call to get more records.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void getAll(HttpServletRequest request,
			      ActionForm form)
        throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        // Account id 0 specifies all sites.  This should be
        // limited to a store is the store administrator is
        // making the request.  This should be limited to an
        // account if an account administrator is making the
        // request.
	QueryRequest qr = new QueryRequest();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            qr.filterByStoreIds(appUser.getUserStoreAsIdVector());
        }
	qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);
        SiteViewVector dv = siteBean.getSiteCollection(qr);

        session.setAttribute("Site.found.vector", dv);
        session.setAttribute("Site.found.total",
                             String.valueOf(dv.size()));
    }


    /**
     *  Describe <code>getDetail</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@return                The Detail value
     *@exception  Exception  if an error occurs
     */
    public static SiteMgrDetailForm getDetail(HttpServletRequest request,
                                              ActionForm form)
        throws Exception {

        HttpSession session = request.getSession();
        initFormVectors(request);

        String fieldValue = request.getParameter("searchField");
        if (null == fieldValue) {
            // Look for a cached site id.
            fieldValue = (String) session.getAttribute("Site.id");
            if (null == fieldValue) {
                fieldValue = "0";
            }
            fieldValue = fieldValue.trim();
        }
        int siteid = Integer.parseInt(fieldValue);
        SiteMgrDetailForm sForm = (SiteMgrDetailForm) form;
        fetchDetail(request, siteid,sForm);
        return sForm;
    }

    private static void fetchDetail
        (HttpServletRequest request, int pSiteId, SiteMgrDetailForm sForm)
        throws DataNotFoundException, Exception {

        HttpSession session = request.getSession();


        init(request, sForm);


        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        Account accti = factory.getAccountAPI();
        Store storei = factory.getStoreAPI();
        PropertyService propi = factory.getPropertyServiceAPI();
        CustomerRequestPoNumber custPoBean = factory.getCustomerRequestPoNumberAPI();
        BusEntityData acct = null;
        SiteData sd = siteBean.getSite(pSiteId, 0, false, SessionTool.getCategoryToCostCenterView(session, pSiteId));
        if (null == sd) {
            throw new
                DataNotFoundException
                ("SiteMgrLogic.fetchDetail: error, site: " +
                 pSiteId + " not found." );
        }

        BusEntityData bed = sd.getBusEntity();
        // Set the form values.

        BlanketPoNumDataVector bpodv = custPoBean.getBlanketPosForBusEntity(sd.getAccountId());
        if(bpodv == null || bpodv.isEmpty()){
            session.removeAttribute("Site.account.blanketPos");
        }else{
            session.setAttribute("Site.account.blanketPos",bpodv);
        }

        if(sd.getBlanketPoNum() != null){
            sForm.setBlanketPoNumId(new Integer(sd.getBlanketPoNum().getBlanketPoNumId()));
        }else{
            sForm.setBlanketPoNumId(new Integer(0));
        }

        sForm.setName(bed.getShortDesc());
        sForm.setStatusCd(bed.getBusEntityStatusCd());
        sForm.setId(String.valueOf(bed.getBusEntityId()));
        sForm.setTypeCd(bed.getBusEntityTypeCd());
        sForm.setSiteNumber(bed.getErpNum());
        sForm.setOldSiteNumber(bed.getErpNum());
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String effDateS="";
        Date effDate = bed.getEffDate();
        if(effDate!=null) {
          effDateS = df.format(effDate);
        }
        sForm.setEffDate(effDateS);
	sForm.setSiteData(sd);

        String expDateS="";
        Date expDate = bed.getExpDate();
        if(expDate!=null) {
          expDateS = df.format(expDate);
        }
        sForm.setExpDate(expDateS);

        if ( sd.hasBSC() )
        {
            sForm.setSubContractor(sd.getBSC().getBusEntityData().getShortDesc());
        }
        else
        {
            sForm.setSubContractor("");
        }

        if(sd.getTargetFacilityRank() != null){
            sForm.setTargetFacilityRank(sd.getTargetFacilityRank().toString());
        }else{
            sForm.setTargetFacilityRank("");
        }


        PropertyData taxableIndicator = sd.getTaxableIndicator();
        sForm.setTaxableIndicator(taxableIndicator.getValue());

        PropertyData invShopping = sd.getInventoryShopping();
        sForm.setInventoryShoppingStr(invShopping.getValue());

        acct = sd.getAccountBusEntity();
        int acctid = acct.getBusEntityId();
        sForm.setAccountId(String.valueOf(acctid));
        sForm.setAccountName(acct.getShortDesc());
        // Get the fields configured by the account.
        BusEntityFieldsData sfd = propi.fetchSiteFieldsData(acctid);
        session.setAttribute("Site.account.fields", sfd);
        session.setAttribute("Site.account.id", sForm.getAccountId());
        session.setAttribute("Account.id", sForm.getAccountId());

        // Set the account name.
        AccountData acctdata = accti.getAccount(acctid, 0);
        sForm.setAccountName(acctdata.getBusEntity().getShortDesc());

        BusEntityAssocData storeassoc = acctdata.getStoreAssoc();

        // Set the store id and store name.
        int storeid = storeassoc.getBusEntity2Id();
        StoreData storedata = storei.getStore(storeid);
        sForm.setStoreId(String.valueOf(storeid));
        session.setAttribute("Site.store.id", sForm.getStoreId());
        sForm.setStoreName(storedata.getBusEntity().getShortDesc());
        session.setAttribute("Site.store.name", sForm.getStoreName());

        AddressData address = sd.getSiteAddress();
        sForm.setName1(address.getName1());
        sForm.setName2(address.getName2());
        sForm.setPostalCode(address.getPostalCode());
        sForm.setStateOrProv(address.getStateProvinceCd());
        sForm.setCounty(address.getCountyCd());
        sForm.setCountry(address.getCountryCd());
        sForm.setCity(address.getCity());
        sForm.setStreetAddr1(address.getAddress1());
        sForm.setStreetAddr2(address.getAddress2());
        sForm.setStreetAddr3(address.getAddress3());
        sForm.setStreetAddr4(address.getAddress4());

        PropertyDataVector props = sd.getMiscProperties();
        sForm.setComments("");
        sForm.setShippingMessage("");

        Iterator itr = props.iterator();
        while (itr.hasNext()) {
            PropertyData prop = (PropertyData) itr.next();
            if (prop.getShortDesc().equals
                (RefCodeNames.PROPERTY_TYPE_CD.SITE_COMMENTS)
                ) {
                sForm.setComments(prop.getValue());
            }
            else if (prop.getShortDesc().equals
                     (RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG)
                     ) {
                sForm.setShippingMessage(prop.getValue());
            }
            else if (prop.getShortDesc().equals
                     (RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER)
                     ) {
                sForm.setSiteReferenceNumber(prop.getValue());
            }
            else if (prop.getShortDesc().equals
                    (RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER)
                    ) {
               sForm.setDistSiteReferenceNumber(prop.getValue());
           }
           else if (prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.BYPASS_ORDER_ROUTING)) {
                 if(Utility.isTrue(prop.getValue())){
                    sForm.setBypassOrderRouting(true);
                 }else{
                    sForm.setBypassOrderRouting(false);
                 }
            }
            else if (prop.getShortDesc().
                 equals(RefCodeNames.PROPERTY_TYPE_CD.CONSOLIDATED_ORDER_WAREHOUSE)) {
                 if(Utility.isTrue(prop.getValue())){
                    sForm.setConsolidatedOrderWarehouse(true);
                 }else{
                    sForm.setConsolidatedOrderWarehouse(false);
                 }
            }
        }

        PropertyDataVector props2 = sd.getDataFieldProperties();
        Iterator itr2 = props2.iterator();
        sForm.setF1Value("");
        sForm.setF2Value("");
        sForm.setF3Value("");
        sForm.setF4Value("");
        sForm.setF5Value("");
        sForm.setF6Value("");
        sForm.setF7Value("");
        sForm.setF8Value("");
        sForm.setF9Value("");
        sForm.setF10Value("");
        while (itr2.hasNext()) {
            PropertyData prop = (PropertyData) itr2.next();
            if (prop.getPropertyTypeCd().equals
                (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD)
                ) {
                String tag = prop.getShortDesc();
                if ( null == tag ) continue;
                if ( tag.equals(sfd.getF1Tag()) ) {
                    sForm.setF1Value(prop.getValue());
                }
                else if ( tag.equals(sfd.getF2Tag()) ) {
                    sForm.setF2Value(prop.getValue());
                }
                else if ( tag.equals(sfd.getF3Tag()) ) {
                    sForm.setF3Value(prop.getValue());
                }
                else if ( tag.equals(sfd.getF4Tag()) ) {
                    sForm.setF4Value(prop.getValue());
                }
                else if ( tag.equals(sfd.getF5Tag()) ) {
                    sForm.setF5Value(prop.getValue());
                }
                else if ( tag.equals(sfd.getF6Tag()) ) {
                    sForm.setF6Value(prop.getValue());
                }
                else if ( tag.equals(sfd.getF7Tag()) ) {
                    sForm.setF7Value(prop.getValue());
                }
                else if ( tag.equals(sfd.getF8Tag()) ) {
                    sForm.setF8Value(prop.getValue());
                }
                else if ( tag.equals(sfd.getF9Tag()) ) {
                    sForm.setF9Value(prop.getValue());
                }
                else if ( tag.equals(sfd.getF10Tag()) ) {
                    sForm.setF10Value(prop.getValue());
                }
            }
        }


        session.setAttribute("Site.id", String.valueOf(pSiteId));

            sForm.setERPEnabled(false);
            sForm.setAvailableShipto(new IdVector());
        return;
    }


    /**
     *  Describe <code>getBudgets</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void getBudgets(HttpServletRequest request,
                                  ActionForm form)
        throws Exception {
/*
        HttpSession session = request.getSession();
        SiteMgrDetailForm sForm = (SiteMgrDetailForm) form;


        APIAccess factory = new APIAccess();

        // Get the site data
        Site siteBean = factory.getSiteAPI();
        int siteId = Integer.parseInt(sForm.getId());
        SiteData site = siteBean.getSite(siteId, 0);

        // Get all the cost centers available for the site's account
        Account accountBean = factory.getAccountAPI();
        int accountId = Integer.parseInt(sForm.getAccountId());
        CostCenterDataVector costCenters =
            accountBean.getAllCostCenters(accountId, Account.ORDER_BY_NAME);

        // This will be the array of all the site budget info
        ArrayList budgets = new ArrayList();
        sForm.setSiteBudgetList(budgets);

        // Iterate thru the account's cost centers
        Iterator costIter = costCenters.iterator();
        for (int i = 0; costIter.hasNext(); i++) {
            CostCenterData costCenter = (CostCenterData) costIter.next();
            int costCenterId = costCenter.getCostCenterId();
            SiteBudget siteBudget = new SiteBudget();
            siteBudget.setName(costCenter.getShortDesc());
            siteBudget.setCostCenterData(costCenter);

            // look to see if this site has a budget for this cost center
            Iterator budgetIter = site.getBudgets().iterator();

            while (budgetIter.hasNext()) {
                BudgetData budget = (BudgetData) budgetIter.next();
                if (budget.getCostCenterId() == costCenterId) {
		            siteBudget.setBudgetData(budget);
                    // Calculate the remaining budget amount.
                    BudgetSpendView spent = siteBean.getBudgetSpent
			(siteId, costCenterId);
                    siteBudget.setSiteBudgetRemaining
			(spent.getAmountAllocated().subtract
			 (spent.getAmountSpent())
			 );
		    siteBudget.setCostCenterData(costCenter);
		    siteBudget.setCurrentBudgetPeriod(spent.getBudgetPeriod());
		    siteBudget.setCurrentBudgetYear(spent.getBudgetYear());
                    break;
                }
            }

            // if it has no explicit budget, it's zero
            if (siteBudget.getBudgetData() == null) {
                BudgetData budgetData = BudgetData.createValue();
                BigDecimal zero = new BigDecimal(0);
                budgetData.setAmount1(zero);
                budgetData.setAmount2(zero);
                budgetData.setAmount3(zero);
                budgetData.setAmount4(zero);
                budgetData.setAmount5(zero);
                budgetData.setAmount6(zero);
                budgetData.setAmount7(zero);
                budgetData.setAmount8(zero);
                budgetData.setAmount9(zero);
                budgetData.setAmount10(zero);
                budgetData.setAmount11(zero);
                budgetData.setAmount12(zero);
                budgetData.setCostCenterId(costCenterId);
                /siteBudget.setBudgetData(budgetData);
            }

	        budgets.add(siteBudget);
        }
        sForm.setSiteBudgetList(budgets);

        // this is ugly - but the cost detail jsp page needs
        // these session variables set; needs to be standardized
        // somehow
        session.setAttribute("Account.id", sForm.getAccountId());
        session.setAttribute("Account.name", sForm.getAccountName());
        session.setAttribute("Store.id", sForm.getStoreId());
        session.setAttribute("Store.name", sForm.getStoreName());*/
    }

    public static void setBudgets(HttpServletRequest request,
                                  ActionForm form)
        throws Exception {

    /*    HttpSession session = request.getSession();
        SiteMgrDetailForm sForm = (SiteMgrDetailForm) form;


        APIAccess factory = new APIAccess();
        Budget budBean = factory.getBudgetAPI();
        String cuser = (String) session.getAttribute(Constants.USER_NAME);

        // Get the site data
        ArrayList v = sForm.getSiteBudgetList();
	for ( int i = 0; null != v && i < v.size(); i++ ) {
	    SiteBudget sb = (SiteBudget)v.get(i);
	    String cctype = sb.getCostCenterData().getCostCenterTypeCd();

	    if ( !RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET.equals(cctype) ) {

		log.info
		    (" NO UPDATE, setBudgets, upd costCenterId="
		     + sb.getCostCenterData().getCostCenterId()
		     + " site Id="  + sb.getId()
		     + " cctype=" + cctype
		     + " wrong cctype"
		     );
	    }

	    log.info
		(" setBudgets, upd costCenterId="
		 + sb.getCostCenterData().getCostCenterId()
		 + " site Id="  + sb.getId()
		 + " cctype=" + cctype
		 );
	    BudgetData budgetData = sb.getBudgetData();
	    if (budgetData.getBudgetId() == 0) {
		budgetData.setAddBy(cuser);
	    }
	    budgetData.setBusEntityId(Integer.parseInt(sb.getId()));
	    budgetData.setBudgetTypeCd(cctype);
	    budgetData = budBean.updateBudget(budgetData,cuser);
	    sb.setBudgetData(budgetData);
	}
*/
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void initFormVectors(HttpServletRequest request)
        throws Exception {

        HttpSession session = request.getSession();

        // Cache the lists needed for Sites.
        APIAccess factory = new APIAccess();

        ListService lsvc = factory.getListServiceAPI();

        // Set up the site status list.
        if (null == session.getAttribute("Site.status.vector")) {
            RefCdDataVector statusv =
                lsvc.getRefCodesCollection("BUS_ENTITY_STATUS_CD");
            session.setAttribute("Site.status.vector", statusv);
        }

        /*if (null == session.getAttribute("countries.vector")) {
            RefCdDataVector countriesv =
                lsvc.getRefCodesCollection("ADDRESS_COUNTRY_CD");
            session.setAttribute("countries.vector", countriesv);
        }*/
    }


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
        initFormVectors(request);
        listAll("list.all.bsc", request, form);
        if (form instanceof SiteShoppingControlForm) {
            putShoppingControlItems2Form(request, (SiteShoppingControlForm)form);
        }
        return;
    }

    /**
     *  <code>search</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void search(HttpServletRequest request,
                              ActionForm form)
        throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();

        if(form instanceof BusEntitySearchForm){
            BusEntitySearchForm bForm = (BusEntitySearchForm) form;
            bForm.setResults(siteBean.getSitesByCriteria(bForm.constructCriteria(request)));
            return;
        }

        SiteMgrSearchForm sForm = (SiteMgrSearchForm) form;


        // Reset the session variables.
        SiteViewVector dv = new SiteViewVector();
        session.setAttribute("Site.found.vector", dv);
        session.setAttribute("Site.found.total", "0");

	String fieldSearchType = sForm.getSearchType();

	String fieldValue = sForm.getSearchField();
        String accountIdList = sForm.getAccountIdList();
        String city = sForm.getCity();
        String county = sForm.getCounty();
        String state = sForm.getState();
        String postalCode = sForm.getPostalCode();

        dv = search(request, fieldValue, fieldSearchType, accountIdList, city, county, state, postalCode);

        //set sites for accounts that crcs cannot shop for into the noShop
        //vector

        SiteViewVector noShop = new SiteViewVector();
        Hashtable accounts = new Hashtable();
        Account acctBean = factory.getAccountAPI();

        for(int i=0; i<dv.size();i++){
            SiteView sv = (SiteView)dv.get(i);
            String acctk = String.valueOf(sv.getAccountId());
            AccountData ad = null;
            if ( accounts.containsKey(acctk))
            {
                ad = (AccountData)accounts.get(acctk);
            }
            else
            {
                ad = acctBean.getAccountForSite(sv.getId());
                accounts.put(acctk, ad);
            }

            String shop = ad.getCrcShop().getValue();

            if(shop.equals("true")){
                noShop.add(sv);
            }
        }
        session.setAttribute("Site.noShop.vector", noShop);
        session.setAttribute("Site.found.vector", dv);
        session.setAttribute("Site.found.total", String.valueOf(dv.size()));
    }


    /**
     *searchs the site without the use of any forms.
     */
    public static SiteViewVector search(HttpServletRequest request,String fieldValue,String fieldSearchType,
            String accountIdList,String city,String county,String state,String postalCode)
    throws Exception{
	HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();

        SiteViewVector dv;

        fieldValue = fieldValue.trim();
	boolean multiFieldSearch = true, emptySearch = true;

        QueryRequest qr = new QueryRequest();
        boolean emptyCritFl = true;
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            emptyCritFl = false;
            qr.filterByStoreIds(appUser.getUserStoreAsIdVector());
        }

	if (fieldSearchType.equals("id")) {
            emptySearch = false;
	    Integer id = new Integer(fieldValue.trim());
	    qr.filterBySiteId(id.intValue());
	    dv = siteBean.getSiteCollection(qr);
	} else {
	    qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);
	    String f = "";
	    if (fieldSearchType.equals("nameBegins") &&
            fieldValue.length() > 0
		) {
            emptySearch = false;
            qr.filterBySiteName(fieldValue,
                                QueryRequest.BEGINS_IGNORE_CASE);
	    }
	    else if (fieldSearchType.equals("nameContains") &&
                 fieldValue.length() > 0
                 ) {
            emptySearch = false;
            qr.filterBySiteName(fieldValue,
                                QueryRequest.CONTAINS_IGNORE_CASE);
	    }

	    if ( multiFieldSearch ) {
            f = city;
            if (Utility.isSet(f)) {
                f=f.trim();
                emptySearch = false;
                qr.filterByCity(f, QueryRequest.BEGINS_IGNORE_CASE);
            }
            f = county;
            f =(f==null)?"":f.trim();
            if (f.length() > 0) {
                emptySearch = false;
                qr.filterByCounty(f, QueryRequest.BEGINS_IGNORE_CASE);
            }

            if (Utility.isSet(state)) {
                f = state.trim();
                emptySearch = false;
                qr.filterByState(f, QueryRequest.BEGINS_IGNORE_CASE);
            }
	    }
	    f = postalCode;
	    f =(f==null)?"":f.trim();
	    if (f.length() > 0) {
            emptySearch = false;
            qr.filterByZip(f, QueryRequest.BEGINS_IGNORE_CASE);
	    }


        if(Utility.isSet(accountIdList)) {
          IdVector accountIdV = new IdVector();
          StringTokenizer tok = new StringTokenizer(accountIdList,",");

          while(tok.hasMoreTokens()){
            String aIdS =tok.nextToken().trim();
            try{
              int accountId = Integer.parseInt(aIdS);
              accountIdV.add(new Integer(accountId));
            } catch(Exception exc) {}
          }
          if(accountIdV.size()>0) {
            emptySearch = false;
            qr.filterByAccountIds(accountIdV);
          }
        }

        if ( emptySearch ) {
            return new SiteViewVector();
        }

	    dv = siteBean.getSiteCollection(qr);
	}

        return dv;

    }

    /**
     *  <code>sort</code>
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
        SiteViewVector sites =
            (SiteViewVector)session.getAttribute("Site.found.vector");
        if (sites == null) {
            return;
        }
        String sortField = request.getParameter("sortField");
        DisplayListSort.sort(sites, sortField);
    }

    /**
     *  Describe <code>addSite</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void addSite(HttpServletRequest request,
                               ActionForm form)
        throws Exception {

        HttpSession session = request.getSession();
        SiteMgrDetailForm sForm = new SiteMgrDetailForm();
        session.setAttribute("SITE_DETAIL_FORM", sForm);

        // These can only be set after the account has been chosen.
        session.removeAttribute("Site.account.fields");
    }


    private static BuildingServicesContractorView findBSC(HttpServletRequest request,
    String pBSCName)
    {
        BuildingServicesContractorViewVector bscs =
        (BuildingServicesContractorViewVector)
        request.getSession().getAttribute("list.all.bsc");
        if (bscs == null || bscs.size() == 0 )
        {
            return null;
        }
        for ( int i=0; i<bscs.size(); i++)
        {
            BuildingServicesContractorView bsc =
            (BuildingServicesContractorView)bscs.get(i);
            if (bsc.getBusEntityData().getShortDesc().equals(pBSCName))
            {
                return bsc;
            }
        }
        return null;
    }
    /**
     *  Describe <code>updateSite</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@return                an <code>ActionErrors</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors updateSite(HttpServletRequest request,
                                          ActionForm form)
        throws Exception {
        ActionErrors lUpdateErrors = new ActionErrors();

        HttpSession session = request.getSession();
        SiteMgrDetailForm sForm = (SiteMgrDetailForm) form;
        Date effDate = null;
        Date expDate = null;
        BlanketPoNumData blanketPo = null;
        Integer targetFacilityRank = null;
        int accountId = 0;
        try{
            if(Utility.isSet(sForm.getAccountId())){
                accountId = Integer.parseInt(sForm.getAccountId());
            }
        }catch(NumberFormatException e){//exception handled later in validation
        }
        if (sForm != null) {
            String oldSiteNumber = sForm.getOldSiteNumber();
            if(oldSiteNumber!=null && oldSiteNumber.trim().length()>0 ) {
		String siteNumber = sForm.getSiteNumber();
                StoreData store = null;
                if(accountId > 0){
                    store = AccountMgrLogic.getStoreForManagedAccount(session, accountId);
                }
            }
            // Verify the form information submitted.
            if(sForm.getCity()!=null){sForm.setCity(sForm.getCity().trim());}
            if(sForm.getStreetAddr1()!=null){sForm.setStreetAddr1(sForm.getStreetAddr1().trim());}
            if(sForm.getStreetAddr2()!=null){sForm.setStreetAddr2(sForm.getStreetAddr2().trim());}
            if(sForm.getStreetAddr3()!=null){sForm.setStreetAddr3(sForm.getStreetAddr3().trim());}
            if(sForm.getStreetAddr4()!=null){sForm.setStreetAddr4(sForm.getStreetAddr4().trim());}
            if(sForm.getStateOrProv()!=null){sForm.setStateOrProv(sForm.getStateOrProv().trim());}
            if(sForm.getPostalCode()!=null){sForm.setPostalCode(sForm.getPostalCode().trim());}

            if (sForm.getAccountId().length() == 0 ||
                sForm.getAccountId().equals("0")) {
                lUpdateErrors.add("accountId",
                                  new ActionError("variable.empty.error",
                                                  "Account Id"));
            }
            if (sForm.getStatusCd().length() == 0) {
                lUpdateErrors.add("statusCd",
				  new ActionError("variable.empty.error",
						  "Status"));
            }
            if (sForm.getName().length() == 0) {
                lUpdateErrors.add("name", new ActionError("variable.empty.error",
							  "Name"));
            }

            if (sForm.getName1().length() == 0) {
                lUpdateErrors.add("name1", new ActionError("variable.empty.error",
                                                           "Name1"));
            }

            if (sForm.getName2().length() == 0) {
                lUpdateErrors.add("name2",
                                  new ActionError("variable.empty.error",
                                                  "Name2"));
            }
            if (sForm.getStreetAddr1().length() == 0) {
                lUpdateErrors.add("streetAddr1",
                                  new ActionError("variable.empty.error",
                                                  "Street Address 1"));
            }
            if (sForm.getCity().length() == 0) {
                lUpdateErrors.add("city",
                                  new ActionError("variable.empty.error",
                                                  "City"));
            }
            if (sForm.getStateOrProv().length() == 0) {
                lUpdateErrors.add("stateOrProv",
                                  new ActionError("variable.empty.error",
                                                  "State/Province"));
            }
            if (sForm.getCountry().length() == 0) {
                lUpdateErrors.add("country",
                                  new ActionError("variable.empty.error",
                                                  "Country"));
            }
            if (sForm.getPostalCode().length() == 0) {
                lUpdateErrors.add("postalCode",
                                  new ActionError("variable.empty.error",
                                                  "Zip/Postal Code"));
            }
            if (sForm.getTargetFacilityRank()!= null && sForm.getTargetFacilityRank().length() > 0) {
                try{
                    targetFacilityRank = new Integer(sForm.getTargetFacilityRank());
                }catch(NumberFormatException e){
                    lUpdateErrors.add("targetFacilityRank",
                        new ActionError("error.invalidNumber","Target Facility Rank"));
                }
            }

            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String effDateS = sForm.getEffDate();
            if(effDateS!=null && effDateS.trim().length()>0) {
              try{
               effDate = df.parse(effDateS);
              } catch (Exception exc) {
                lUpdateErrors.add("effDate",
                                  new ActionError("variable.date.format.error",
                                                  "Effective Date"));
              }
            }
            String expDateS = sForm.getExpDate();
            if(expDateS!=null && expDateS.trim().length()>0) {
              try{
               expDate = df.parse(expDateS);
              } catch (Exception exc) {
                lUpdateErrors.add("expDate",
                                  new ActionError("variable.date.format.error",
                                                  "Expiration Date"));
              }
            }
            if(effDate!=null && expDate!=null && expDate.before(effDate)) {
              lUpdateErrors.add("EffExpDates",
                             new ActionError("error.simpleGenericError",
                                              "Effective date can't be after expiration date"));
            }
            String statusCd = sForm.getStatusCd();
            if(statusCd.equals(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE)){
              Date curDate = new Date();
              if((effDate!=null && effDate.after(curDate)) ||
                 (expDate!=null && expDate.before(curDate))) {
                lUpdateErrors.add("EffExpDatesStatusCd",
                             new ActionError("error.simpleGenericError",
                                              "Exffective and expiration dates conflict with site status"));

              }
            }
        }
        if(sForm.getBlanketPoNumId() != null && !sForm.getBlanketPoNumId().equals(new Integer(0))){
            BlanketPoNumDataVector bpdv = (BlanketPoNumDataVector)session.getAttribute("Site.account.blanketPos");
            if(bpdv != null){
                int bpoid = sForm.getBlanketPoNumId().intValue();
                Iterator it = bpdv.iterator();
                while(it.hasNext()){
                    BlanketPoNumData avaBpo = (BlanketPoNumData) it.next();
                    if(avaBpo.getBlanketPoNumId() == bpoid){
                        blanketPo = avaBpo;
                    }
                }
            }

            if(blanketPo == null){
                lUpdateErrors.add("blanketPoNumId",new ActionError("error.siteBlanketPoNotAvaliable"));
            }
        }


        if (lUpdateErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lUpdateErrors;
        }

        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        //AddressValidator validator = factory.getAddressValidatorAPI();

        int siteid = 0;
        if (!sForm.getId().equals("")) {
            siteid = Integer.parseInt(sForm.getId());
        }
        // Get the current information for this Site.
        SiteData dd;
        BusEntityData bed;
        PropertyDataVector props;
        PropertyDataVector siteFields = new PropertyDataVector();
        PropertyDataVector siteFieldsRuntime = new PropertyDataVector();
        AddressData address;
        PropertyData taxableIndicator, invprop;

        int acctid = 0;
        if (!sForm.getAccountId().equals("")) {
            acctid = Integer.parseInt(sForm.getAccountId());
        }
        if (siteid > 0) {
            dd = siteBean.getSite(siteid, acctid,false, SessionTool.getCategoryToCostCenterView(session, siteid));
        }
        else {
            dd = SiteData.createValue();
        }

        dd.setBlanketPoNum(blanketPo);

        dd.setTargetFacilityRank(targetFacilityRank);
        bed = dd.getBusEntity();
        props = dd.getMiscProperties();
        address = dd.getSiteAddress();
        taxableIndicator = dd.getTaxableIndicator();
        invprop = dd.getInventoryShopping();

        bed.setWorkflowRoleCd("UNKNOWN");
        String cuser = (String) session.getAttribute(Constants.USER_NAME);

        // Now update with the data from the form.
        bed.setShortDesc(sForm.getName());
        bed.setLongDesc(sForm.getName());
        bed.setBusEntityStatusCd(sForm.getStatusCd());
        bed.setEffDate(effDate);
        bed.setExpDate(expDate);
	bed.setErpNum(sForm.getSiteNumber());
        bed.setLocaleCd("unk");
        bed.setModBy(cuser);

        address.setModBy(cuser);
        address.setName1(sForm.getName1());
        address.setName2(sForm.getName2());
        address.setAddress1(sForm.getStreetAddr1());
        address.setAddress2(sForm.getStreetAddr2());
        address.setAddress3(sForm.getStreetAddr3());
        address.setAddress4(sForm.getStreetAddr4());
        address.setCity(sForm.getCity());
        address.setStateProvinceCd(sForm.getStateOrProv());
        address.setPostalCode(sForm.getPostalCode());
        address.setCountryCd(sForm.getCountry());
        address.setPrimaryInd(true);
        address.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
        address.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);

       // if(!validator.validateAddress(address)){
       //     lUpdateErrors.add("updateSite",new ActionError("error.addressValidation"));
       //     return(lUpdateErrors);
       // }

        dd.setBSC(findBSC(request,sForm.getSubContractor()));

        taxableIndicator.setValue(sForm.getTaxableIndicator());
        taxableIndicator.setAddBy(cuser);

        Iterator itr = props.iterator();
        boolean updfComments = false;
        boolean updfShipMsg = false;
        boolean siteRefNum = false;
        boolean bypassOrderRouting = false;
        boolean consolidatedOrderWarehouse = false;
        while (itr.hasNext()) {
            PropertyData prop = (PropertyData) itr.next();
            if (prop.getShortDesc().equals
                (RefCodeNames.PROPERTY_TYPE_CD.SITE_COMMENTS)) {
                prop.setValue(sForm.getComments());
                updfComments = true;
            }
            else if (prop.getShortDesc().equals
                     (RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG)) {
                prop.setValue(sForm.getShippingMessage());
                updfShipMsg = true;
            }
            else if (prop.getShortDesc().equals
                     (RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER)) {
                prop.setValue(sForm.getSiteReferenceNumber());
                siteRefNum = true;
            }
            else if (prop.getShortDesc().equals
                    (RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER)) {
               prop.setValue(sForm.getDistSiteReferenceNumber());
               //siteRefNum = true;
           }
           else if (prop.getShortDesc().equals
                     (RefCodeNames.PROPERTY_TYPE_CD.BYPASS_ORDER_ROUTING)) {
                if(sForm.isBypassOrderRouting()){
                    prop.setValue("true");
                }else{
                    prop.setValue("false");
                }
                //prop.setValue(Boolean.toString(sForm.isBypassOrderRouting()));
                bypassOrderRouting = true;
            }
            else if (prop.getShortDesc().equals
                     (RefCodeNames.PROPERTY_TYPE_CD.CONSOLIDATED_ORDER_WAREHOUSE)) {
                if(sForm.isConsolidatedOrderWarehouse()){
                    prop.setValue("true");
                }else{
                    prop.setValue("false");
                }
                consolidatedOrderWarehouse = true;
            }
        }

	boolean tf = sForm.getInventoryShopping();
	if ( tf == true ) {
            invprop.setValue("on");
            invprop.setAddBy(cuser);
	}
	else {
            invprop.setValue("off");
            invprop.setModBy(cuser);
	}

	dd.setInventoryShopping(invprop);

        if (updfComments == false) {
            PropertyData nprop = PropertyData.createValue();
            nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SITE_COMMENTS);
            nprop.setValue(sForm.getComments());
            nprop.setAddBy(cuser);
            props.add(nprop);
        }

        if (updfShipMsg == false) {
            PropertyData nprop = PropertyData.createValue();
            nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG);
            nprop.setValue(sForm.getShippingMessage());
            nprop.setAddBy(cuser);
            props.add(nprop);
        }

        if (siteRefNum == false) {
            PropertyData nprop = PropertyData.createValue();
            nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
            nprop.setValue(sForm.getSiteReferenceNumber());
            nprop.setAddBy(cuser);
            props.add(nprop);
        }

        if (bypassOrderRouting == false) {
            PropertyData nprop = PropertyData.createValue();
            nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.BYPASS_ORDER_ROUTING);
            if(sForm.isBypassOrderRouting()){
                nprop.setValue("true");
            }else{
                nprop.setValue("false");
            }
            //nprop.setValue(Boolean.toString(sForm.isBypassOrderRouting()));
            nprop.setAddBy(cuser);
            props.add(nprop);
        }
        if (consolidatedOrderWarehouse == false) {
            PropertyData nprop = PropertyData.createValue();
            nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.CONSOLIDATED_ORDER_WAREHOUSE);
            if(sForm.isConsolidatedOrderWarehouse()){
                nprop.setValue("true");
            }else{
                nprop.setValue("false");
            }
            nprop.setAddBy(cuser);
            props.add(nprop);
        }

        // Set up the site fields vector.
        BusEntityFieldsData sfd = (BusEntityFieldsData)
            session.getAttribute("Site.account.fields");
        if ( null != sfd ) {
            if ( sfd.getF1ShowAdmin() || sfd.getF1ShowRuntime() ) {
                if(sfd.getF1Required() && !Utility.isSet(sForm.getF1Value())){
                    lUpdateErrors.add("F1Tag",
                                  new ActionError("variable.empty.error",sfd.getF1Tag()));
                }
                PropertyData nprop = PropertyData.createValue();
                nprop.setPropertyTypeCd
                    (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                nprop.setShortDesc(sfd.getF1Tag());
                nprop.setValue(sForm.getF1Value());
                nprop.setAddBy(cuser);
                siteFields.add(nprop);
                if(sfd.getF1ShowRuntime()){
		    siteFieldsRuntime.add(nprop);
                }

            }
            if ( sfd.getF2ShowAdmin() || sfd.getF2ShowRuntime()  ) {
                if(sfd.getF2Required() && !Utility.isSet(sForm.getF2Value())){
                    lUpdateErrors.add("F2Tag",
                                  new ActionError("variable.empty.error",sfd.getF2Tag()));
                }
                PropertyData nprop = PropertyData.createValue();
                nprop.setPropertyTypeCd
                    (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                nprop.setShortDesc(sfd.getF2Tag());
                nprop.setValue(sForm.getF2Value());
                nprop.setAddBy(cuser);
                siteFields.add(nprop);
                if(sfd.getF2ShowRuntime()){
		    siteFieldsRuntime.add(nprop);
                }
            }
            if ( sfd.getF3ShowAdmin() || sfd.getF3ShowRuntime()  ) {
                if(sfd.getF3Required() && !Utility.isSet(sForm.getF3Value())){
                    lUpdateErrors.add("F3Tag",
                                  new ActionError("variable.empty.error",sfd.getF3Tag()));
                }
                PropertyData nprop = PropertyData.createValue();
                nprop.setPropertyTypeCd
                    (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                nprop.setShortDesc(sfd.getF3Tag());
                nprop.setValue(sForm.getF3Value());
                nprop.setAddBy(cuser);
                siteFields.add(nprop);
                if(sfd.getF3ShowRuntime()){
		    siteFieldsRuntime.add(nprop);
                }
            }
            if ( sfd.getF4ShowAdmin() || sfd.getF4ShowRuntime()  ) {
                if(sfd.getF4Required() && !Utility.isSet(sForm.getF4Value())){
                    lUpdateErrors.add("F4Tag",
                                  new ActionError("variable.empty.error",sfd.getF4Tag()));
                }
                PropertyData nprop = PropertyData.createValue();
                nprop.setPropertyTypeCd
                    (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                nprop.setShortDesc(sfd.getF4Tag());
                nprop.setValue(sForm.getF4Value());
                nprop.setAddBy(cuser);
                siteFields.add(nprop);
                if(sfd.getF4ShowRuntime()){
		    siteFieldsRuntime.add(nprop);
                }
            }
            if ( sfd.getF5ShowAdmin() || sfd.getF5ShowRuntime()  ) {
                if(sfd.getF5Required() && !Utility.isSet(sForm.getF5Value())){
                    lUpdateErrors.add("F5Tag",
                                  new ActionError("variable.empty.error",sfd.getF5Tag()));
                }
                PropertyData nprop = PropertyData.createValue();
                nprop.setPropertyTypeCd
                    (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                nprop.setShortDesc(sfd.getF5Tag());
                nprop.setValue(sForm.getF5Value());
                nprop.setAddBy(cuser);
                siteFields.add(nprop);
                if(sfd.getF5ShowRuntime()){
		    siteFieldsRuntime.add(nprop);
                }
            }
            if ( sfd.getF6ShowAdmin() || sfd.getF6ShowRuntime()  ) {
                if(sfd.getF6Required() && !Utility.isSet(sForm.getF6Value())){
                    lUpdateErrors.add("F6Tag",
                                  new ActionError("variable.empty.error",sfd.getF6Tag()));
                }
                PropertyData nprop = PropertyData.createValue();
                nprop.setPropertyTypeCd
                    (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                nprop.setShortDesc(sfd.getF6Tag());
                nprop.setValue(sForm.getF6Value());
                nprop.setAddBy(cuser);
                siteFields.add(nprop);
                if(sfd.getF6ShowRuntime()){
		    siteFieldsRuntime.add(nprop);
                }
            }
            if ( sfd.getF7ShowAdmin() || sfd.getF7ShowRuntime()  ) {
                if(sfd.getF7Required() && !Utility.isSet(sForm.getF7Value())){
                    lUpdateErrors.add("F7Tag",
                                  new ActionError("variable.empty.error",sfd.getF7Tag()));
                }
                PropertyData nprop = PropertyData.createValue();
                nprop.setPropertyTypeCd
                    (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                nprop.setShortDesc(sfd.getF7Tag());
                nprop.setValue(sForm.getF7Value());
                nprop.setAddBy(cuser);
                siteFields.add(nprop);
                if(sfd.getF7ShowRuntime()){
		    siteFieldsRuntime.add(nprop);
                }
            }
            if ( sfd.getF8ShowAdmin() || sfd.getF8ShowRuntime()  ) {
                if(sfd.getF8Required() && !Utility.isSet(sForm.getF8Value())){
                    lUpdateErrors.add("F8Tag",
                                  new ActionError("variable.empty.error",sfd.getF8Tag()));
                }
                PropertyData nprop = PropertyData.createValue();
                nprop.setPropertyTypeCd
                    (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                nprop.setShortDesc(sfd.getF8Tag());
                nprop.setValue(sForm.getF8Value());
                nprop.setAddBy(cuser);
                siteFields.add(nprop);
                if(sfd.getF8ShowRuntime()){
		    siteFieldsRuntime.add(nprop);
                }
            }
            if ( sfd.getF9ShowAdmin() || sfd.getF9ShowRuntime()  ) {
                if(sfd.getF9Required() && !Utility.isSet(sForm.getF9Value())){
                    lUpdateErrors.add("F9Tag",
                                  new ActionError("variable.empty.error",sfd.getF9Tag()));
                }
                PropertyData nprop = PropertyData.createValue();
                nprop.setPropertyTypeCd
                    (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                nprop.setShortDesc(sfd.getF9Tag());
                nprop.setValue(sForm.getF9Value());
                nprop.setAddBy(cuser);
                siteFields.add(nprop);
                if(sfd.getF9ShowRuntime()){
		    siteFieldsRuntime.add(nprop);
                }
            }
            if ( sfd.getF10ShowAdmin() || sfd.getF10ShowRuntime()  ) {
                if(sfd.getF10Required() && !Utility.isSet(sForm.getF10Value())){
                    lUpdateErrors.add("F10Tag",
                                  new ActionError("variable.empty.error",sfd.getF10Tag()));
                }
                PropertyData nprop = PropertyData.createValue();
                nprop.setPropertyTypeCd
                    (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
                nprop.setShortDesc(sfd.getF10Tag());
                nprop.setValue(sForm.getF10Value());
                nprop.setAddBy(cuser);
                siteFields.add(nprop);
                if(sfd.getF10ShowRuntime()){
		    siteFieldsRuntime.add(nprop);
                }
            }
            dd.setDataFieldProperties(siteFields);
            dd.setDataFieldPropertiesRuntime(siteFieldsRuntime);

        }
        if(!(lUpdateErrors.size() == 0)){
            return lUpdateErrors;
        }

        if (siteid == 0) {
	    // get account - both to verify that the id is that
	    // of an account and get it's name
	    try {
		Account accountBean = factory.getAccountAPI();
		AccountData ad = accountBean.getAccount(acctid, 0);
		sForm.setAccountName(ad.getBusEntity().getShortDesc());
	    } catch (DataNotFoundException de) {
		// the given account id is apparently not an account
		sForm.setAccountName("");
                lUpdateErrors.add("Account.id",
				  new ActionError("site.bad.account"));
		return lUpdateErrors;
	    } catch (Exception e) {
                e.printStackTrace();
		lUpdateErrors.add("SiteErrors",new ActionError("error.genericError",e.getMessage()));
                return lUpdateErrors;
	    }

            // Add the site.
            bed.setAddBy(cuser);
            bed.setBusEntityStatusCd(sForm.getStatusCd());
            address.setAddBy(cuser);
	    try {
		dd = siteBean.addSite(dd, acctid);
	    } catch (DuplicateNameException ne) {
                lUpdateErrors.add("name",
				  new ActionError("error.field.notUnique",
						  "Name"));
		return lUpdateErrors;
	    } catch (Exception e) {
                e.printStackTrace();
		lUpdateErrors.add("SiteErrors",new ActionError("error.genericError",e.getMessage()));
                return lUpdateErrors;
	    }
            siteid = dd.getBusEntity().getBusEntityId();
            sForm.setId(String.valueOf(siteid));
        }
        else {
            // Update this Site
	    try {
		siteBean.updateSite(dd);
	    } catch (DuplicateNameException ne) {
                lUpdateErrors.add("name",
				  new ActionError("error.field.notUnique",
						  "Name"));
		return lUpdateErrors;
	    } catch (Exception e) {
                e.printStackTrace();
		lUpdateErrors.add("SiteErrors",new ActionError("error.genericError",e.getMessage()));
                return lUpdateErrors;
	    }
        }

        try {
            fetchDetail(request, siteid,sForm);
        }
        catch (DataNotFoundException e) {
            lUpdateErrors.add("SiteErrors",
                              new ActionError("error.systemError",
                                              e.getMessage())
			      );
        }

        return lUpdateErrors;
    }


    /**
     *  The <code>delete</code> method removes the database entries defining
     *  this site if no other database entry is dependent on it.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     *@see                   com.cleanwise.service.api.session.Site
     */
    public static ActionErrors delete(HttpServletRequest request,
				      ActionForm form)
        throws Exception {

        ActionErrors deleteErrors = new ActionErrors();
        SiteMgrDetailForm sForm = (SiteMgrDetailForm) form;
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        String strid = request.getParameter("id");
        if (strid == null || strid.length() == 0) {
	    deleteErrors.add("id", new ActionError("error.badRequest", "id"));
	    return deleteErrors;
        }

        Integer id = new Integer(strid);
        int acctid = Integer.parseInt(request.getParameter("accountId"));
        SiteData dd = siteBean.getSite(id.intValue(), acctid,false, SessionTool.getCategoryToCostCenterView(session, id.intValue()));

        if (null != dd) {
	    try {
		siteBean.removeSite(dd);
	    } catch (Exception e) {
                deleteErrors.add("id",
				 new ActionError("error.deleteFailed",
						 "Site"));
		return deleteErrors;
	    }
            session.removeAttribute("Site.found.vector");
        }

	return deleteErrors;
    }


    /**
     *  <code>initConfig</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void initConfig(HttpServletRequest request,
				  ActionForm form)
	throws Exception {

        HttpSession session = request.getSession();
        session.removeAttribute("site.catalogs.vector");
        session.removeAttribute("site.users.vector");

        return;
    }

    /**
     *  <code>searchConfig</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void searchConfig(HttpServletRequest request,
				    ActionForm form)
	throws Exception {

	initConfig(request, form);

        HttpSession session = request.getSession();

	APIAccess factory = new APIAccess();

	SiteMgrConfigForm configForm = (SiteMgrConfigForm)form;

        String fieldValue = configForm.getSearchField();
	if (fieldValue == null || fieldValue.equals("")) {
	    // treat just like a 'view all' request
	    getAllConfig(request, form);
	    return;
	}

        String fieldSearchType = configForm.getSearchType();
	String configType = configForm.getConfigType();

	SiteMgrDetailForm detailForm = (SiteMgrDetailForm) request.getSession().getAttribute("SITE_DETAIL_FORM");
	int accountId = Integer.parseInt(detailForm.getAccountId());
	int siteId = Integer.parseInt(detailForm.getId());

	if (configType.equals("Users")) {
	    User userBean = factory.getUserAPI();

	    // get all users that could possibly be associated with this site
	    // (i.e. all account users)
	    UserDataVector uv = null;
	    if (fieldSearchType.equals("nameBegins")) {
		uv = userBean.getUsersCollectionByBusEntity(accountId,
							    fieldValue,
							    User.NAME_BEGINS_WITH_IGNORE_CASE,
							    User.ORDER_BY_NAME);
	    } else { // "nameContains"
		uv = userBean.getUsersCollectionByBusEntity(accountId,
							    fieldValue,
							    User.NAME_CONTAINS_IGNORE_CASE,
							    User.ORDER_BY_NAME);
	    }
	    request.getSession().setAttribute("site.users.vector", uv);

	    // get all the users that are actually associated with the site now
	    UserDataVector users = null;
	    if (fieldSearchType.equals("nameBegins")) {
		users = userBean.getUsersCollectionByBusEntity(siteId,
							       fieldValue,
							       User.NAME_BEGINS_WITH_IGNORE_CASE,
							       User.ORDER_BY_NAME);
	    } else { // "nameContains"
		users = userBean.getUsersCollectionByBusEntity(siteId,
							       fieldValue,
							       User.NAME_CONTAINS_IGNORE_CASE,
							       User.ORDER_BY_NAME);
	    }

	    String[] selectIds = new String[users.size()];
	    IdVector userIds = new IdVector();

	    Iterator userI = users.iterator();
	    int i = 0;
	    while (userI.hasNext()) {
		Integer id =
		    new Integer(((UserData)userI.next()).getUserId());
		userIds.add(id);
		selectIds[i++] = new String(id.toString());
	    }

	    // the currently associated users are checked/selected
	    configForm.setSelectIds(selectIds);

	    // list of all associated user ids, in the update this
	    // will be compared with the selected
	    session.setAttribute("site.assoc.user.ids", userIds);

	} else if (configType.equals("Catalog")) {
	    CatalogInformation cati = factory.getCatalogInformationAPI();

	    // Get the current site catalog
	    CatalogData siteCatalog = cati.getSiteCatalog(siteId);
	    if (siteCatalog != null) {
		String catalogId = String.valueOf(siteCatalog.getCatalogId());
		configForm.setCatalogId(catalogId);
		configForm.setOldCatalogId(catalogId);
	    } else {
		configForm.setCatalogId("0");
		configForm.setOldCatalogId("0");
	    }

	    CatalogDataVector catalogs = null;
	    if (fieldSearchType.equals("nameBegins")) {
                catalogs = cati.getCatalogsCollectionByNameAndBusEntity(fieldValue, SearchCriteria.BEGINS_WITH_IGNORE_CASE, accountId);
	    } else { // "nameContains"
                catalogs = cati.getCatalogsCollectionByNameAndBusEntity(fieldValue, SearchCriteria.CONTAINS_IGNORE_CASE, accountId);
	    }

	    session.setAttribute("site.catalogs.vector", catalogs);
	}

        return;
    }

    /**
     *  <code>getAllConfig</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void getAllConfig(HttpServletRequest request,
				    ActionForm form)
	throws Exception {

	initConfig(request, form);

        HttpSession session = request.getSession();

	SiteMgrConfigForm configForm = (SiteMgrConfigForm)form;
	String configType = configForm.getConfigType();

	String fieldValue = configForm.getSearchField();
	String fieldSearchType = configForm.getSearchType();

	APIAccess factory = new APIAccess();

	SiteMgrDetailForm detailForm = (SiteMgrDetailForm)
	    request.getSession().getAttribute("SITE_DETAIL_FORM");
	int accountId = Integer.parseInt(detailForm.getAccountId());
	int siteId = Integer.parseInt(detailForm.getId());

	if (configType.equals("Users")) {
	    User userBean = factory.getUserAPI();

            //Collects all users eligible
	    UserDataVector uv =
		userBean.getUsersCollectionByBusEntity(accountId, null);
	    request.getSession().setAttribute("site.users.vector", uv);

            //Collects all already configured users
	    UserDataVector users =
		userBean.getUsersCollectionByBusEntity(siteId, null);


	    String[] selectIds = new String[users.size()];
	    IdVector userIds = new IdVector();

	    Iterator userI = users.iterator();
	    int i = 0;
	    while (userI.hasNext()) {
		Integer id =
		    new Integer(((UserData)userI.next()).getUserId());
		userIds.add(id);
		selectIds[i++] = new String(id.toString());
	    }

	    // the currently associated users are checked/selected
	    configForm.setSelectIds(selectIds);

	    // list of all associated user ids, in the update this
	    // will be compared with the selected
	    session.setAttribute("site.assoc.user.ids", userIds);

	} else if (configType.equals("Catalog")) {
	    CatalogInformation cati = factory.getCatalogInformationAPI();

	    // Get the current site catalog
	    CatalogData siteCatalog = cati.getSiteCatalog(siteId);
	    if (siteCatalog != null) {
		String catalogId = String.valueOf(siteCatalog.getCatalogId());
		configForm.setCatalogId(catalogId);
		configForm.setOldCatalogId(catalogId);
	    } else {
		configForm.setCatalogId("0");
		configForm.setOldCatalogId("0");
	    }

	    CatalogDataVector catalogs =
		cati.getCatalogsByAccountId(accountId);

	    session.setAttribute("site.catalogs.vector", catalogs);
	}

        return;
    }

    /**
     *  <code>updateConfig</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void updateConfig(HttpServletRequest request,
				    ActionForm form)
	throws Exception {

        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();

	SiteMgrDetailForm detailForm =
	    (SiteMgrDetailForm)session.getAttribute("SITE_DETAIL_FORM");
	int siteId = Integer.parseInt(detailForm.getId());
	int accountId = Integer.parseInt(detailForm.getAccountId());

        SiteMgrConfigForm sForm = (SiteMgrConfigForm)form;
	String configType = sForm.getConfigType();

	if (configType.equals("Users")) {
	    User userBean = factory.getUserAPI();

	    // get list of user ids displayed
	    String[] displayed = sForm.getDisplayIds();

	    // get list of user ids selected
	    String[] selected = sForm.getSelectIds();

	    // get list of currently associated user ids
	    IdVector assocUserIds =
		(IdVector)session.getAttribute("site.assoc.user.ids");

	    // Looking for two cases:
	    // 1. user is selected, but not currently associated - this
	    //    means we need to add the association
	    // 2. user is not selected, but is currently associated - this
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
		int assocIndex = assocUserIds.indexOf(id);
		if (foundId) {
		    if (assocIndex < 0) {
			// we need to add the association, the selected list
			// has the id, but not on assoc sites list
			userBean.addUserAssoc(id.intValue(), siteId,
					      RefCodeNames.USER_ASSOC_CD.SITE);
			assocUserIds.add(id);
		    }
		} else if (assocIndex >= 0) {
		    // we need to remove the association, the selected list
		    // doesn't have the id, but it is on the assoc sites list
		    // (means it was 'unselected')
		    userBean.removeUserAssoc(id.intValue(), siteId);
		    assocUserIds.remove(assocIndex);
		}
	    }

	    // update with changes
	    session.setAttribute("site.assoc.user.ids", assocUserIds);
	    selected = new String[assocUserIds.size()];
	    Iterator assocI = assocUserIds.iterator();
	    int i = 0;
	    while (assocI.hasNext()) {
		selected[i++] = new String(assocI.next().toString());
	    }
	    sForm.setSelectIds(selected);

	} else if (configType.equals("Catalog")) {
	    String newCatId = sForm.getCatalogId();
	    String oldCatId = sForm.getOldCatalogId();
	    if (!newCatId.equals(oldCatId)) {
		String cuser =
		    (String) session.getAttribute(Constants.USER_NAME);

		Catalog catBean = factory.getCatalogAPI();
		catBean.resetCatalogAssoc
		    (Integer.parseInt(newCatId),
		     siteId, cuser);
                sForm.setOldCatalogId(newCatId);
	    }
	}

        return;
    }

    /**
     *  <code>sortConfig</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sortConfig(HttpServletRequest request,
				  ActionForm form)
	throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();

	SiteMgrConfigForm sForm = (SiteMgrConfigForm)form;

        String sortField = request.getParameter("sortField");

	String configType = request.getParameter("configType");
	if (configType == null) {
	    return;
	}

	if (configType.equals("Users")) {
	    UserDataVector users =
		(UserDataVector)session.getAttribute("site.users.vector");
	    if (users == null) {
		return;
	    }
	    DisplayListSort.sort(users, sortField);

	    // Need to init the selected/checked users
	    IdVector assocUserIds =
		(IdVector)session.getAttribute("site.assoc.user.ids");
	    String[] selected = new String[assocUserIds.size()];
	    Iterator assocI = assocUserIds.iterator();
	    int i = 0;
	    while (assocI.hasNext()) {
		selected[i++] = new String(assocI.next().toString());
	    }
	    sForm.setSelectIds(selected);

	} else if (configType.equals("Catalog")) {
	    CatalogDataVector catalogs =
		(CatalogDataVector)session.getAttribute("site.users.vector");
	    if (catalogs == null) {
		return;
	    }
	    DisplayListSort.sort(catalogs, sortField);
	}

	// so that the display correctly shows the type of config being done
	sForm.setConfigType(configType);

        return;
    }

    public static ActionErrors fetchSiteWorkflow
        (HttpServletRequest request, ActionForm form)
	throws Exception {

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        String s = (String)session.getAttribute("Site.id");
        if (null == s) {
            ae.add("site",
		   new ActionError
		   ("Site Error: the site id was not specified."));
            return ae;
        }

        SiteMgrDetailForm sForm = (SiteMgrDetailForm) form;

        APIAccess factory = new APIAccess();
        Workflow wBean = factory.getWorkflowAPI();
        int sid = Integer.parseInt(s);
        // Get the site detail information.
        fetchDetail(request, sid,sForm);
        try {
            WorkflowData wfd = wBean.fetchWorkflowForSite(sid, RefCodeNames.WORKFLOW_TYPE_CD.ORDER_WORKFLOW);
            session.setAttribute("Site.workflow", wfd);

            // Get the rules for the workflow.
            WorkflowRuleDataVector wrv =
                wBean.getWorkflowRulesCollection
                (wfd.getWorkflowId());
            session.setAttribute("Site.workflow.rules.vector", wrv);

        }
        catch (Exception e) {
            session.setAttribute("Site.workflow", null);
            session.setAttribute("Site.workflow.rules.vector", null);
            ae.add("workflow",
		   new ActionError("Problem getting the Workflow for Site:" +
				   sid +   " Error: " + e));
        }

        return ae;
    }
    //--------------------------------------------------------------------------
    public static ActionErrors updateInventoryData(HttpServletRequest request, ActionForm form) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory;

        String cuser = (String) session.getAttribute(Constants.USER_NAME);

        try {

            factory = new APIAccess();

            InventoryForm iform = (InventoryForm) form;
            SiteInventoryConfigViewVector sivv = iform.getInventoryItems();
            InventoryForm oform = (InventoryForm) session.getAttribute("SITE_INVENTORY_FORM");

            SiteInventoryConfigViewVector ov = oform.getInvItemsCopy();

            for (int ix = 0; ov != null && ix < ov.size(); ix++) {
                SiteInventoryConfigView oldv = (SiteInventoryConfigView) ov.get(ix);
                SiteInventoryConfigView newv = (SiteInventoryConfigView) sivv.get(ix);
                newv.setSiteId(oldv.getSiteId());
                newv.setItemId(oldv.getItemId());
////////////////////////
                newv.setQtyOnHand(oldv.getQtyOnHand());
                newv.setOrderQty(oldv.getOrderQty());
                newv.setInitialQtyOnHand(oldv.getInitialQtyOnHand());
////////////////////////
                newv.setSumOfAllParValues(oldv.getSumOfAllParValues());
                newv.setModBy(cuser);
            }

            Site siteEjb = factory.getSiteAPI();

            siteEjb.storeInventoryConfig(sivv);

            SiteInventoryConfigViewVector invItems = siteEjb.lookupInventoryConfig(ShopTool.getCurrentSiteId(request), true, SessionTool.getCategoryToCostCenterView(session, ShopTool.getCurrentSiteId(request)));
            iform.setInventoryItems(invItems);

        } catch (Exception exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "No ejb access"));
        }
        return ae;
    }

    public static ActionErrors lookupInventoryData
        (HttpServletRequest request,
         ActionForm form)    {
        ActionErrors ae = new ActionErrors();
        InventoryForm iform;

        if ( form != null ) {
            iform = (InventoryForm)form;
        }
        else {
            iform = new InventoryForm();
        }

        if (ShopTool.getCurrentSiteId(request) <= 0) {
            SiteInventoryConfigViewVector ov = new SiteInventoryConfigViewVector();
            iform.setInventoryItems(ov);
            request.getSession().setAttribute("SITE_INVENTORY_FORM",
                                              iform);
            return ae   ;
        }
        HttpSession session = request.getSession();
        APIAccess factory = null;

        try {
            factory = new APIAccess();
            Site sbean = factory.getSiteAPI();
            SiteInventoryConfigViewVector ov = null;
            String actionStr = request.getParameter("action");
            if(actionStr.equals("customer_profile")
            		|| actionStr.equals("inventory_update")){
            	ov = sbean.lookupInventoryConfig(ShopTool.getCurrentSiteId(request), true, SessionTool.getCategoryToCostCenterView(session,ShopTool.getCurrentSiteId(request)));
            }else{
            	ov = sbean.lookupInventoryConfig(ShopTool.getCurrentSiteId(request), false, SessionTool.getCategoryToCostCenterView(session,ShopTool.getCurrentSiteId(request)));
            }
            iform.setInventoryItems(ov);
            iform.setInvItemsCopy(ov);

            request.getSession().setAttribute("SITE_INVENTORY_FORM",
                                              iform);

        } catch(Exception exc) {
            exc.printStackTrace();
            ae.add("error",new ActionError("error.systemError",
                                           "No ejb access"));
        }
        return ae;
    }

    public static ActionErrors lookupShoppingControls
        (HttpServletRequest request,
         ActionForm form)    {
        ActionErrors ae = new ActionErrors();

        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        if (appUser == null ) {
            ae.add("error",new ActionError("error.systemError",
                                           "User is not logged in 11.3"));
            return ae   ;
        }

        SiteData currSite = ShopTool.getCurrentSite(request);
        if (currSite == null ) {
            ae.add("error",new ActionError("error.systemError",
                                           "No site defined"));
            return ae   ;
        }

	    SessionTool st = new SessionTool(request);
	    SiteSettingsData siteSpecific
	    = st.mkSiteSpecificData(request,currSite);

        HttpSession session = request.getSession();
        APIAccess factory = null;
        try {
            factory = new APIAccess();
	    ShoppingServices ssvc = null;
	    ssvc = factory.getShoppingServicesAPI();
	    String storeType = "";
	    StoreData store = ShopTool.getCurrentUser(request).getUserStore();
	    if (store != null && store.getStoreType() != null ) {
		storeType = store.getStoreType().getValue();
	    }

	    ShoppingCartItemDataVector controlItems =
		ssvc.getItemControlInfo(storeType, currSite,SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
	    siteSpecific.setShoppingControls(controlItems);
	    st.setSiteSettings(request,siteSpecific);

        } catch(Exception exc) {
            exc.printStackTrace();
            ae.add("error",new ActionError("error.systemError",
                                           "No ejb access"));
        }
        return ae;
    }


    public static ActionErrors listAll
        (String pListReq, HttpServletRequest request,
         ActionForm form)    {
        return listAll( pListReq, request, form, false);
   }

    public static ActionErrors listAll
        (String pListReq, HttpServletRequest request,
         ActionForm form, boolean pResetList)    {
        ActionErrors ae = new ActionErrors();

        if ( pListReq.equals("list.all.bsc") &&
            request.getSession().getAttribute(pListReq) != null
             && pResetList == false
        )
        {
            return ae;
        }

        HttpSession session = request.getSession();
        ArrayList resl = new ArrayList();
        session.setAttribute(pListReq, resl);
        try {
            APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            IdVector storeIds;
            if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
                storeIds = appUser.getUserStoreAsIdVector();
            }else{
                storeIds = null;
            }
            // Get all the business entities in question.
            Request rBean = factory.getRequestAPI();
            List resList = rBean.listAll(storeIds,RefCodeNames.BUS_ENTITY_TYPE_CD.BUILDING_SVC_CONTRACTOR);

            for (int it=0; it<resList.size()-1;it++ ) {
            	for(int j=0; j<(resList.size()-1)-it;j++){

            		BuildingServicesContractorView B1 = (BuildingServicesContractorView)resList.get(j);
            		BuildingServicesContractorView B2 = (BuildingServicesContractorView)resList.get(j+1);
            		BuildingServicesContractorView temp = null;

            		if(B1.getBusEntityData().getShortDesc().compareToIgnoreCase(B2.getBusEntityData().getShortDesc())>0){
            			temp = B1;
            			resList.set(j, B2);
            			resList.set(j+1, temp);
            		}
            	}
             }

            session.setAttribute(pListReq,resList);

            /*session.setAttribute
                (pListReq,
                 rBean.listAll
                 (storeIds,RefCodeNames.BUS_ENTITY_TYPE_CD.BUILDING_SVC_CONTRACTOR)
                 );*/
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ae;
    }



    public static ActionErrors updateBSC
        ( HttpServletRequest request,
          ActionForm form)    {
        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        try {
            BSCDetailForm detForm = (BSCDetailForm)form;
            Integer storeId;
            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
                storeId = new Integer(appUser.getUserStore().getStoreId());
            }else{
                try{
                    storeId = new Integer(detForm.getStoreId());
                }catch(Exception e){
                    ae.add("storeId",new ActionError("error.invalidNumber","Store"));
                    return ae;
                }
            }

            Hashtable vals = new Hashtable();
            vals.put("busEntityTypeCd",
            RefCodeNames.BUS_ENTITY_TYPE_CD.BUILDING_SVC_CONTRACTOR);
            vals.put("id", detForm.getId());
            vals.put("bscName", detForm.getName());
            vals.put("bscDesc", detForm.getDescription());
            vals.put("bscOrderFaxNumber",detForm.getFaxPhoneNumber());
            vals.put("storeId", storeId);
            String cuser =
                (String) session.getAttribute(Constants.USER_NAME);
            vals.put("userName", cuser);

            Request rBean = factory.getRequestAPI();
            detForm.setId(rBean.updateInfo(vals));
            listAll("list.all.bsc", request, form, true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ae;
    }

    public static ActionErrors listBscRelationships
        (String pListReq, HttpServletRequest request,
         ActionForm form)    {
        String bscid = request.getParameter("bscid");
        ActionErrors ae = new ActionErrors();

        HttpSession session = request.getSession();
        APIAccess factory = null;
        try {
            factory = new APIAccess();

            Hashtable vals = new Hashtable();
            vals.put("busEntityTypeCd",
            RefCodeNames.BUS_ENTITY_TYPE_CD.BUILDING_SVC_CONTRACTOR);
            vals.put("bscid", bscid);

            Request rBean = factory.getRequestAPI();
            ArrayList sv = rBean.getSitesFor(vals);
            if ( null == sv ) {
                sv = new ArrayList();
            }
            session.setAttribute("list.bsc.sites", sv);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ae;

   }

    public static ActionErrors updateShoppingRestrictions(HttpServletRequest request,
            ActionForm form){

    	ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = null;
        String cuser = (String) session.getAttribute(Constants.USER_NAME);
        List msgList = new ArrayList();

        try {
        	factory = new APIAccess();
        	SiteShoppingControlForm sform = (SiteShoppingControlForm)form;
        	int siteid = sform.getSiteId();
        	Site sbean = factory.getSiteAPI();
        	ShoppingControlDataVector scdv = new ShoppingControlDataVector();
        	SessionTool st = new SessionTool(request);
        	SiteSettingsData ssd = st.getSiteSettings(request,siteid);

        	ae = checkMaxAllowedAndRestrictionDaysQty(request,ssd.getShoppingControls(),sform);
            if(ae.size()>0) {
                return ae;
            }

            int accid = ssd.getSiteData().getAccountId();
            int catid = ssd.getSiteData().getContractData().getCatalogId();

            //Iterator it = ssd.getShoppingControls().iterator();
            SiteData siteD = sbean.getSite(siteid, 0, false, SessionTool.getCategoryToCostCenterView(session, siteid));
            Iterator it = siteD.getShoppingControls().iterator();

            ShoppingControlDataVector updateVals = new ShoppingControlDataVector();
            ShoppingControlDataVector deleteVals = new ShoppingControlDataVector();
            while(it.hasNext()){
            	//ShoppingControlItemView sciV = (ShoppingControlItemView)it.next();
            	//String skuNum = sciV.getSkuNum();
            	//ShoppingControlData scD = sciV.getShoppingControlData();
            	ShoppingControlData scD = (ShoppingControlData)it.next();

            	int thisItemId = scD.getItemId();
            	int thisMaxQty = scD.getMaxOrderQty();
            	int thisRestDays = scD.getRestrictionDays();

            	String maxqty =   (String) sform.getItemIdMaxAllowed(thisItemId+"");
        		String restDays = (String) sform.getItemIdRestrictionDays(thisItemId+"");
        		int maxQ = 0;
        		int restD= 0;
        		try{

        			if(maxqty!=null && maxqty.trim().length()>0){
        				maxQ = Integer.parseInt(maxqty);
        			}
        			if(restDays!=null && restDays.trim().length()>0){
        				restD = Integer.parseInt(restDays);
        			}
        		}catch(NumberFormatException exc){
        			sform.setItemIdMaxAllowed(scD.getItemId()+"", maxqty);
        			msgList.add(thisItemId);
        			continue;
        		}

        		boolean isAcctCtrl = false;
        		boolean updateThis = false;
        		boolean deleteThis = false;

        		if ( null == maxqty ||	maxqty.trim().length() == 0 ) {
        			//If site ctrl is blanked out, it should go back to acct ctrl
        			isAcctCtrl = true;
        			maxqty = "-999";
        		}

        		if ( null == restDays || restDays.trim().length() == 0 ) {
        			restDays = "-999"; // null
        		}

        		if(isAcctCtrl){
        			scD.setAccountId(accid);
        			deleteThis = true;
        		}else{
        			if(Integer.parseInt(maxqty.trim())!=thisMaxQty && !(maxqty.equals("-999"))){

            			scD.setMaxOrderQty(maxQ);
            			scD.setModBy(cuser);
            			scD.setAccountId(0);
            			updateThis = true;
            		}

        			if(Integer.parseInt(restDays.trim())!=thisRestDays && !(restDays.equals("-999"))){

            			scD.setRestrictionDays(restD);
            			scD.setModBy(cuser);
            			scD.setAccountId(0);
            			updateThis = true;
            		}
        		}

        		if(deleteThis){
        			deleteVals.add(scD);
        		}
        		if(updateThis){
        			updateVals.add(scD);
        		}
            }

            if(msgList.size()>0 && !msgList.isEmpty()){

    	    	Object[] param = new Object[1];
    	    	param[0] = Utility.toCommaSting(msgList);
    	        String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.invalidQtyMessage",param);
    	        ae.add("error",
    	                new ActionError("error.simpleGenericError",errorMess));
    	    }
    		if(ae.size()>0){
    			return ae;
    		}

    		for(int i=0; i<deleteVals.size(); i++){
    			ShoppingControlData delVal = (ShoppingControlData)deleteVals.get(i);
    			sbean.deleteShoppingControl(delVal.getSiteId(), delVal.getItemId());
    		}

    		for(int i=0; i<updateVals.size(); i++){
    			ShoppingControlData updateVal = (ShoppingControlData)updateVals.get(i);
    			sbean.updateShoppingControl(updateVal);
    		}

    		lookupShoppingControls(request,form);
        	CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            if (form instanceof SiteShoppingControlForm) {
                putShoppingControlItems2Form(request, (SiteShoppingControlForm)form);
            }

        }catch (Exception e){
    	    e.printStackTrace();
    	}

        return ae;
    }
    
    public static ActionErrors updateShoppingRestrictionsNewXpedx(HttpServletRequest request,
            ActionForm form){
    	
    	ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = null;
        String cuser = (String) session.getAttribute(Constants.USER_NAME);
        List msgList = new ArrayList();
        
        try {
        	factory = new APIAccess();
        	SiteShoppingControlForm sform = (SiteShoppingControlForm)form;
        	int siteid = sform.getSiteId();
        	Site sbean = factory.getSiteAPI();
        	ShoppingControlDataVector scdv = new ShoppingControlDataVector();
        	SessionTool st = new SessionTool(request);        	
        	
           	//ShoppingRestrictionsViewVector srvV = getShoppingRestrictionsBySiteOnly(siteid);
        	
        	/////
        	
        	SiteSettingsData ssd = st.getSiteSettings(request,siteid);
            int accid = ssd.getSiteData().getAccountId();
            
            /**************************************************************************/
            /*** New piece of code - (it could be unnecessary(extra) piece of code: ***/
            /*** There could be junk (bad data) in the Database                     ***/
        	/*** Therefore, pull out Site Shopping Controls from the Database again ***/
        	/**************************************************************************/
            
        	//ShoppingControlDataVector oldvals = sbean.lookupSiteShoppingControlsNew(siteid, accid);
        	//log.info("SiteMgrLogic.updateShoppingRestrictionsNew: oldvals = " + oldvals);
        	//ssd.setShoppingControls(oldvals);
        	// STJ-6031
            SiteData siteD = sbean.getSite(siteid, 0, false, SessionTool.getCategoryToCostCenterView(session, siteid));
    		/**************************************************************************/

        	ae = checkMaxAllowedAndRestrictionDaysQtyNewXpedx(request,ssd.getShoppingControls(),sform);
            if(ae.size()>0) {
                return ae;
            }
            
            //int accid = ssd.getSiteData().getAccountId();
            int catid = ssd.getSiteData().getContractData().getCatalogId();
            
            //Iterator it = ssd.getShoppingControls().iterator();
            //SiteData siteD = sbean.getSite(siteid);
            //siteD.setShoppingControls(oldvals); /*** New stmt : it could be unnecessary (extra) statement ***/
            
            Iterator it = siteD.getShoppingControls().iterator();
            //log.info("updateShoppingRestrictionsNewXpedx:siteD.getShoppingControls size = "+siteD.getShoppingControls().size());
            //log.info("updateShoppingRestrictionsNewXpedx:siteD.getShoppingControls1 = "+siteD.getShoppingControls());
            
            ShoppingControlDataVector updateVals = new ShoppingControlDataVector();
            ShoppingControlDataVector deleteVals = new ShoppingControlDataVector();            
        	
            while(it.hasNext()){
            	//ShoppingControlItemView sciV = (ShoppingControlItemView)it.next();
            	//String skuNum = sciV.getSkuNum();
            	//ShoppingControlData scD = sciV.getShoppingControlData();
            	         	            	            	            	
            	ShoppingControlData scD = (ShoppingControlData)it.next();

            	int thisItemId = scD.getItemId();
            	int thisMaxQty = scD.getMaxOrderQty();       // from the clw_shopping_control DB table
            	int thisRestDays = scD.getRestrictionDays(); // from the clw_shopping_control DB table
            	
            	//if (thisItemId == 1255292){

            	   log.info("AAA Shopping controls for the Site: thisItemId=" + thisItemId);	
               	   log.info("BBB Shopping controls for the Site: thisMaxQty=" + thisMaxQty);	
               	  // log.info("CCC Shopping controls for the Site: thisRestDays=" + thisRestDays);

            	//}
            	String maxqty =   (String) sform.getItemIdMaxAllowed(thisItemId+"");
            	log.info("CCC1 maxqty from the form = " + maxqty);
        		if (maxqty != null && maxqty.trim().length() != 0) {
            	   if (maxqty.trim().equals("*")) maxqty = "-1";
        		}
            	//log.info("BBB1 maxqty after processing=" + maxqty);
            	
        		String restDays = (String) sform.getItemIdRestrictionDays(thisItemId+"");
            	//log.info("CCC2 restDays1 from the form=" + restDays);
        		if(restDays != null && restDays.trim().length() != 0){
        		  if (restDays.trim().equals("*")) restDays = "-1";
        		}
        		if(restDays==null || restDays.trim().length()==0){ // if restDays value on the screen is blank, make it  "unlimited" (as on Admin Portal)
        		   restDays = "-1";
    			}
            	//log.info("CCC3 restDays2 after processing=" + restDays);
        		//if (thisItemId == 1255292){
             	   //log.info("DDD maxqty_1 from the form=" + maxqty);
            	   //log.info("EEE restDays_1 from the form=" + restDays);
               	//}   
        		int maxQ = 0;
        		int restD= 0;
        		try{

        			if(maxqty!=null && maxqty.trim().length()>0){
        				maxQ = Integer.parseInt(maxqty);
        			}
        			if(restDays!=null && restDays.trim().length()>0){
        				restD = Integer.parseInt(restDays);
        			}
        		}catch(NumberFormatException exc){
        			sform.setItemIdMaxAllowed(scD.getItemId()+"", maxqty);
        			msgList.add(thisItemId);
        			continue;
        		}
        		
        		boolean isAcctCtrl = false;
        		boolean updateThis = false;
        		boolean deleteThis = false;
        		
        		if ( null == maxqty ||	maxqty.trim().length() == 0 ) {
        			//If site ctrl is blanked out, it should go back to acct ctrl
        			isAcctCtrl = true;
        			maxqty = "-999";
        		}
        		
        		if ( null == restDays || restDays.trim().length() == 0 ) { // from the screen (form)
        			restDays = "-999"; // null
        		}
        		
              	//if (thisItemId == 1255292){
              ///	   log.info("DDD maxqty_2 from the form=" + maxqty);
             //	   log.info("EEE restDays_2 from the form=" + restDays);
                //}   
              	
        		if(isAcctCtrl){
        			scD.setAccountId(accid);
        			
        			/***
                	//get account Shopping Restrictions
                	ShoppingRestrictionsViewVector acctR = sbean.getShoppingRestrictionsByAccountAndItem(accid, thisItemId);                	
                	ShoppingRestrictionsView srv = null;
                	try {
                	    srv = (ShoppingRestrictionsView) acctR.get(0);
                	}catch(Exception e){
                    	CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
                    	//siteD.setShoppingControls(null); // nullify Shopping Controls
                    	//appUser.getSite().setShoppingControls(null); // nullify Shopping Controls
                    	
                	    e.printStackTrace();
                	    ae.add("error", new ActionError("error.systemError", "No Account Shopping Controls for the account id = " + accid + " and item id = " + thisItemId +". Shopping Controls not updated"));
            	        updateThis = false;
            	        //return ae;
                	}
                	
                	//set account Shopping Restrictions for this Site
                	if (srv != null) {
                	   scD.setMaxOrderQty(Integer.parseInt(srv.getAccountMaxOrderQty()));
                	   scD.setRestrictionDays(Integer.parseInt(srv.getRestrictionDays()));
                	}
                	***/
        			
                	deleteThis = true;
        		}else{
        			// at this point we have two (2) records (entries) in the clw_shopping_control DB table
        			// for this Account+Site+Item
        			// ALWAYS update the ONE record per site+item, in which Site Id != 0 (NEVER update the record, in which Site id = 0 !!!)
        			if(Integer.parseInt(maxqty.trim())!=thisMaxQty && !(maxqty.equals("-999"))){
            			
            			scD.setMaxOrderQty(maxQ);
            			scD.setModBy(cuser);
            			scD.setAccountId(0);
            			updateThis = true;
            		}
        			
        			if(Integer.parseInt(restDays.trim())!=thisRestDays && !(restDays.equals("-999"))){ // old statement
        			//if(Integer.parseInt(restDays.trim())!=thisRestDays && !(restDays.equals("-999"))){ // new statement ???
            			
            			scD.setRestrictionDays(restD);
            			scD.setModBy(cuser);
            			scD.setAccountId(0);
            			updateThis = true;
            		}
        		}
            	
        		if(deleteThis){
        	       	//if (thisItemId == 1255292){
                    //	log.info("FFF deleteThis: scD= " + scD);
        	       	//}
        			deleteVals.add(scD);
        		}
        		if(updateThis){
           	       	//if (thisItemId == 1255292){
                    	log.info("GGG updateThis: scD= " + scD);
        	       	//}
        			updateVals.add(scD);
        		}
        		
        		
            } // while
            
            if(msgList.size()>0 && !msgList.isEmpty()){

    	    	Object[] param = new Object[1];
    	    	param[0] = Utility.toCommaSting(msgList);
    	        String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.invalidQtyMessage",param);
    	        ae.add("error",
    	                new ActionError("error.simpleGenericError",errorMess));
    	    }
    		if(ae.size()>0){
    			return ae;
    		}
    		
    		for(int i=0; i<deleteVals.size(); i++){
    			ShoppingControlData delVal = (ShoppingControlData)deleteVals.get(i);
    			sbean.deleteShoppingControlNewXpedx(delVal.getSiteId(), delVal.getItemId());
    		}
    		
    		for(int i=0; i<updateVals.size(); i++){
    			ShoppingControlData updateVal = (ShoppingControlData)updateVals.get(i);
    			sbean.updateShoppingControlNewXpedx(updateVal);
    		}
    		
    		//if (updateVals.size() > 0){
    		//	ssd.setShoppingControls(updateVals); // SVC: new stmt -> update ssd Object (Object type = SiteSettingsData)
    			//st.setSiteSettings(request, ssd);    // SVC: new stmt -> update st Object (Object type = SessionTool)
    		//}
    		
    		
    		//if (deleteVals.size() > 0){
    			//ssd.setShoppingControls(deleteVals); // SVC: new stmt -> update ssd Object (Object type = SiteSettingsData)
    			//st.setSiteSettings(request, ssd);    // SVC: new stmt -> update st Object (Object type = SessionTool)
    		//}
    		
    		/***
    		// New: find updated Shopping Controls: Begin
        	ShoppingControlDataVector newvals = sbean.lookupSiteShoppingControls2(siteid, accid);
        	log.info("SiteMgrLogic.updateShoppingRestrictionsNewXpedx: newvals = " + newvals);
    		// New: find updated Shopping Controls: End
    		
        	ssd.setShoppingControls(newvals); // New
        	***/
            //log.info("updateShoppingRestrictionsNewXpedx:siteD.getShoppingControls2 = "+siteD.getShoppingControls());
        	lookupShoppingControls(request,form);
            //log.info("updateShoppingRestrictionsNewXpedx:siteD.getShoppingControls3 = "+siteD.getShoppingControls());
        	CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        	/***
        	// New: set Shopping Controls : Begin
        	appUser.getSite().setShoppingControls(newvals); // NEW: update Shopping Controls
        	// New: set Shopping Controls : End
        	***/
        	
        	//ssd.setShoppingControls(null);
        	//siteD.setShoppingControls(null); // nullify Shopping Controls
        	//appUser.getSite().setShoppingControls(updateVals); // update Shopping Controls
        	//appUser.getSite().setShoppingControls(deleteVals); // update Shopping Controls
        	        	
            if (form instanceof SiteShoppingControlForm) {
                putShoppingControlItems2Form(request, (SiteShoppingControlForm)form);
            }
             
           // log.info("updateShoppingRestrictionsNewXpedx:siteD.getShoppingControls4 = "+siteD.getShoppingControls());
            
        }catch (Exception e){
    	    e.printStackTrace();
    	}

        return ae;
    }    
    

    public static ActionErrors updateShoppingControls
        (HttpServletRequest request,
         ActionForm form)    {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = null;
        String cuser = (String) session.getAttribute(Constants.USER_NAME);
        List msgList = new ArrayList();
        try {
        	factory = new APIAccess();
        	SiteShoppingControlForm sform = (SiteShoppingControlForm)form;
        	int siteid = sform.getSiteId();
        	Site sbean = factory.getSiteAPI();
        	ShoppingControlDataVector scdv = new ShoppingControlDataVector();
        	SessionTool st = new SessionTool(request);
        	SiteSettingsData ssd = st.getSiteSettings(request,siteid);

            ae = checkMaxAllowedAndRestrictionDaysQty(request,ssd.getShoppingControls(),sform);
            if(ae.size()>0) {
                return ae;
            }

            int accid = ssd.getSiteData().getAccountId();
            int catid = ssd.getSiteData().getContractData().getCatalogId();
        	Iterator it = ssd.getShoppingControls().iterator();

        	//get acct restrictions
        	ShoppingRestrictionsViewVector acctR = sbean.getShoppingRestrictionsByAccountOnly(accid);

        	while (it.hasNext()){
        		ShoppingControlItemView siteItemControl = (ShoppingControlItemView)it.next();
        		String skuNum = siteItemControl.getSkuNum();
        		ShoppingControlData siteControl = (siteItemControl).getShoppingControlData();

        		ShoppingRestrictionsView bothControlsView = new ShoppingRestrictionsView();
        		bothControlsView.setItemId(siteControl.getItemId());
        		bothControlsView.setSiteMaxOrderQty(Integer.toString(siteControl.getMaxOrderQty()));
        		bothControlsView.setRestrictionDays(Integer.toString(siteControl.getRestrictionDays()));

                //look for acct control  for this item
                int foundIndex = Collections.binarySearch(acctR, bothControlsView,
                		new ShoppingRestrictionsViewComparatorByItem());

                if(foundIndex >0){
                	ShoppingRestrictionsView acctView = (ShoppingRestrictionsView)acctR.get(foundIndex);
                	bothControlsView.setAccountMaxOrderQty(acctView.getAccountMaxOrderQty());
                }
                int siteMax = -1;
                int acctMax = -1;
                int currRestDays=(-1);
        		//bothControlsView - itemId, SiteMax, AcctMax, RestDays
                if(bothControlsView.getSiteMaxOrderQty()!=null && bothControlsView.getSiteMaxOrderQty().trim().length()>0){
                	siteMax = Integer.parseInt(bothControlsView.getSiteMaxOrderQty());
                }
                if(bothControlsView.getAccountMaxOrderQty()!=null && bothControlsView.getAccountMaxOrderQty().trim().length()>0){
                	acctMax = Integer.parseInt(bothControlsView.getAccountMaxOrderQty());
                }
                if(bothControlsView.getRestrictionDays()!=null && bothControlsView.getRestrictionDays().trim().length()>0){
                	currRestDays = Integer.parseInt(bothControlsView.getRestrictionDays());
                }



        		String maxqty =   (String) sform.getItemIdMaxAllowed(siteControl.getItemId()+"");
        		String restDays = (String) sform.getItemIdRestrictionDays(siteControl.getItemId()+"");
        		int maxQ = 0;
        		try{

        			if(maxqty!=null && maxqty.trim().length()>0){
        				maxQ = Integer.parseInt(maxqty);
        			}
        			if(restDays!=null && restDays.trim().length()>0){
        				int restD = Integer.parseInt(restDays);
        			}
        		}catch(NumberFormatException exc){
        			sform.setItemIdMaxAllowed(siteControl.getItemId()+"", maxqty);
        			msgList.add(skuNum);
        			continue;
        		}

        		boolean isAcctCtrl = false;
        		//int thisMaxQty = siteControl.getMaxOrderQty();
        		//int thisRestDays = siteControl.getRestrictionDays();

        		if ( null == maxqty ||	maxqty.trim().length() == 0 ) {
        			//maxqty = "-1";  // mark for deletion.
        			//If site ctrl is blanked out, it should go back to acct ctrl
        			isAcctCtrl = true;
        			maxqty = "-999";
        		}

        		if ( null == restDays || restDays.trim().length() == 0 ) {
        			restDays = "-999"; // null
        		}

        		boolean updateThis = false;

        		/*if( !(thisMaxQty < 0) && thisMaxQty != Integer.parseInt(maxqty.trim())){

        			siteControl.setMaxOrderQty(Integer.parseInt(maxqty.trim()));
        			siteControl.setActualMaxQty(siteControl.getMaxOrderQty());

        			updateThis = true;

        		}*/
        		if(!(siteMax <0) && Integer.parseInt(maxqty.trim())!=siteMax && !(maxqty.equals("-999"))){

        			siteControl.setMaxOrderQty(Integer.parseInt(maxqty.trim()));
        			siteControl.setActualMaxQty(siteControl.getMaxOrderQty());
        			updateThis = true;
        		}else if(!(acctMax <0) && Integer.parseInt(maxqty.trim())!=acctMax && !(maxqty.equals("-999"))){

        			siteControl.setMaxOrderQty(Integer.parseInt(maxqty.trim()));
        			siteControl.setActualMaxQty(siteControl.getMaxOrderQty());
        			updateThis = true;
        		}

        		if(currRestDays != Integer.parseInt(restDays.trim()) && !(restDays.equals("-999"))){
        			siteControl.setRestrictionDays(Integer.parseInt(restDays.trim()));
      			    updateThis = true;
        		}

        		if(siteMax == (-1) && Integer.parseInt(maxqty.trim())!=siteMax && !(maxqty.equalsIgnoreCase("-999"))){
        			siteControl.setMaxOrderQty(Integer.parseInt(maxqty.trim()));
        			siteControl.setActualMaxQty(siteControl.getMaxOrderQty());
        			updateThis = true;
        		}

        		//reset to acct ctrl
        		if(maxqty.equals("-999") && isAcctCtrl && (!(siteMax<0) || !(acctMax<0))){
        			siteControl.setMaxOrderQty(acctMax);
        			updateThis = true;
        		}

        		/*if(thisRestDays != Integer.parseInt(restDays.trim())){
      			    siteControl.setRestrictionDays(Integer.parseInt(restDays.trim()));
      			    updateThis = true;
        		}*/
        		//this handles the specific case where a user is setting the qantity
        		//to the same thing it used to be before it was inactive.  I.e. in the
        		//database the max quantity is 1 and the status code is "INACTIVE".
        		//The user then sets the maxqauantity to 1.  None of the above conditions
        		//will be met, and we just need to flip the status back (which is handled below).
        		if(RefCodeNames.SIMPLE_STATUS_CD.INACTIVE.equals(siteControl.getControlStatusCd()) && !(maxqty.equalsIgnoreCase("-999"))){
        			updateThis = true;
        		}


        		if(updateThis){
        			if(!(maxqty.equalsIgnoreCase("-999"))){
        				siteControl.setControlStatusCd(RefCodeNames.SIMPLE_STATUS_CD.ACTIVE);
        			}
        			if(isAcctCtrl){
        				siteControl.setAccountId(accid);
        				siteControl.setSiteId(0);
        			}else{
        				siteControl.setAccountId(0);
        				siteControl.setSiteId(siteid);
        			}

        			siteControl.setModBy(cuser);
        			siteControl.setExpDate(null);
        			siteControl.setHistoryOrderQty(-1);
        			siteControl.setActualMaxQty(-1);
        			scdv.add(siteControl);

        		}
        	}


    		if(msgList.size()>0 && !msgList.isEmpty()){

    	    	Object[] param = new Object[1];
    	    	param[0] = Utility.toCommaSting(msgList);
    	        String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.invalidQtyMessage",param);
    	        ae.add("error",
    	                new ActionError("error.simpleGenericError",errorMess));
    	    }
    		if(ae.size()>0){
    			return ae;
    		}

        	ShoppingControlDataVector newvals =  sbean.updateShoppingControls(scdv, true);

        	ssd.setShoppingControls(newvals);
        	lookupShoppingControls(request,form);
        	CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        	appUser.getSite().setShoppingControls(newvals);

            if (form instanceof SiteShoppingControlForm) {
                putShoppingControlItems2Form(request, (SiteShoppingControlForm)form);
            }

        }
	catch (Exception e){
	    e.printStackTrace();
	}

        return ae;
    }

    private static ActionErrors checkMaxAllowedAndRestrictionDaysQty(HttpServletRequest request,
                                                                     ArrayList shoppingControls,
                                                                     SiteShoppingControlForm sform) {

        ActionErrors ae = new ActionErrors();
        ArrayList<String> eMaxAllowedSku = new ArrayList<String>();
        ArrayList<String> eRestrictionDaysSku = new ArrayList<String>();

        for (Object shoppingControl : shoppingControls) {

            ShoppingControlItemView sciv = ((ShoppingControlItemView) shoppingControl);
            ShoppingControlData siteControl = sciv.getShoppingControlData();
            String maxqty = (String) sform.getItemIdMaxAllowed(String.valueOf(siteControl.getItemId()));
            String restDays = (String) sform.getItemIdRestrictionDays(String.valueOf(siteControl.getItemId()));

            if (Utility.isSet(maxqty)) {
                try {
                    Integer.parseInt(maxqty.trim());
                } catch (NumberFormatException e) {
                    eMaxAllowedSku.add(sciv.getSkuNum());
                }
            }

            if (Utility.isSet(restDays)) {
                try {
                    Integer.parseInt(restDays.trim());
                } catch (NumberFormatException e) {
                    eRestrictionDaysSku.add(sciv.getSkuNum());
                }
            }
        }

        if (!eMaxAllowedSku.isEmpty()) {
            Object[] param = new Object[1];
            param[0] = Utility.toCommaSting(eMaxAllowedSku);
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.invalidMaxAllowedQtyMessage", param);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        }


        if (!eRestrictionDaysSku.isEmpty()) {
            Object[] param = new Object[1];
            param[0] = Utility.toCommaSting(eRestrictionDaysSku);
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.invalidRestrictionDaysQtyMessage", param);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        }

        return ae;
    }


    private static ActionErrors checkMaxAllowedAndRestrictionDaysQtyNewXpedx(HttpServletRequest request,
            ArrayList shoppingControls,
            SiteShoppingControlForm sform) {

        ActionErrors ae = new ActionErrors();
        ArrayList<String> eMaxAllowedSku = new ArrayList<String>();
        ArrayList<String> eRestrictionDaysSku = new ArrayList<String>();

        for (Object shoppingControl : shoppingControls) {

           ShoppingControlItemView sciv = ((ShoppingControlItemView) shoppingControl);
           ShoppingControlData siteControl = sciv.getShoppingControlData();
           String maxqty = (String) sform.getItemIdMaxAllowed(String.valueOf(siteControl.getItemId()));
           String restDays = (String) sform.getItemIdRestrictionDays(String.valueOf(siteControl.getItemId()));

           log.info("checkMaxAllowedAndRestrictionDaysQty(): restDays1=" + restDays);
   		   if (restDays != null && restDays.trim().length() != 0){
              if (restDays.trim().equals("*")) restDays = "-1";
   		   }
           log.info("checkMaxAllowedAndRestrictionDaysQty(): restDays2=" + restDays);
   		   if (restDays==null || restDays.trim().length()==0){
 		       restDays = "-1";
		   }
           log.info("checkMaxAllowedAndRestrictionDaysQty(): restDays3=" + restDays);

           if (Utility.isSet(maxqty)) {
              try {
                 Integer.parseInt(maxqty.trim());
              } catch (NumberFormatException e) {
                 eMaxAllowedSku.add(sciv.getSkuNum());
              }
           }

           if (Utility.isSet(restDays)) {
               try {
                 Integer.parseInt(restDays.trim());
               } catch (NumberFormatException e) {
                 eRestrictionDaysSku.add(sciv.getSkuNum());
               }
           }
        }

        if (!eMaxAllowedSku.isEmpty()) {
            Object[] param = new Object[1];
            param[0] = Utility.toCommaSting(eMaxAllowedSku);
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.invalidMaxAllowedQtyMessage", param);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        }


        if (!eRestrictionDaysSku.isEmpty()) {
            Object[] param = new Object[1];
            param[0] = Utility.toCommaSting(eRestrictionDaysSku);
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.invalidRestrictionDaysQtyMessage", param);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        }

        return ae;
}    

    public static ActionErrors saveFieldValues
	(HttpServletRequest request,
	 ActionForm form)  throws Exception {
        ActionErrors lUpdateErrors = new ActionErrors();

        SiteMgrConfigForm cfgform = (SiteMgrConfigForm)form;
        java.util.HashMap propsmap = cfgform.getGeneralPropertyMap();

        HttpSession session = request.getSession();
        SiteMgrDetailForm sForm =
	    (SiteMgrDetailForm)session.getAttribute("SITE_DETAIL_FORM");

        APIAccess factory = new APIAccess();
        Site siteB = factory.getSiteAPI();

        SiteData dd = sForm.getSiteData();
        PropertyDataVector siteFields = dd.getDataFieldProperties();
        String cuser = (String) session.getAttribute(Constants.USER_NAME);

        // iterate through the values in the form and
        // update the values in the site.
        java.util.Iterator keyset = propsmap.keySet().iterator();
        while (keyset.hasNext() ) {
            String k = (String)keyset.next();

            // Look for this id in the site fields
            for (int idx = 0; null != siteFields && idx < siteFields.size();
                 idx++ ) {

                PropertyData sf = (PropertyData)siteFields.get(idx);
                if ( Integer.parseInt(k) == sf.getPropertyId() ) {
                    sf.setValue((String)propsmap.get(k));
                    sf.setModBy(cuser);
                    break;
                }
            }
        }
	siteB.saveSiteFields(dd.getSiteId(), siteFields);
        return lUpdateErrors;
    }


    public static void populateShoppingControlData(HttpServletRequest request, SiteShoppingControlForm pForm) {

        if (pForm != null) {

            pForm.setMap(new HashMap());
            pForm.setMap2(new HashMap());

            SiteSettingsData siteSettings = SessionTool.getSiteSettings(request, ShopTool.getCurrentSiteId(request));
            java.util.ArrayList<ShoppingControlItemView> sctrlv = null;
            if (siteSettings != null && siteSettings.getShoppingControls() != null) {
                sctrlv = new java.util.ArrayList<ShoppingControlItemView>(siteSettings.getShoppingControls());
            }

            for (int i = 0; sctrlv != null && i < sctrlv.size(); i++) {

                ShoppingControlItemView siteItemControl = sctrlv.get(i);
                String key = String.valueOf(siteItemControl.getShoppingControlData().getItemId());

                String maxOrderQty;
                if (siteItemControl.getShoppingControlData().getMaxOrderQty() < 0 ||
                        !RefCodeNames.SIMPLE_STATUS_CD.ACTIVE.equals(siteItemControl.getShoppingControlData().getControlStatusCd())) {
                    maxOrderQty = " ";
                } else {
                    maxOrderQty = String.valueOf(siteItemControl.getShoppingControlData().getMaxOrderQty());
                }

                String restrictionDays;
                if (siteItemControl.getShoppingControlData().getRestrictionDays() < 0 ||
                        !RefCodeNames.SIMPLE_STATUS_CD.ACTIVE.equals(siteItemControl.getShoppingControlData().getControlStatusCd())) {
                    restrictionDays = " ";
                } else {
                    restrictionDays = String.valueOf(siteItemControl.getShoppingControlData().getRestrictionDays());
                }

                pForm.setItemIdMaxAllowed(key, maxOrderQty);
                pForm.setItemIdRestrictionDays(key, restrictionDays);

            }

            request.setAttribute("SITE_SHOPPING_CONTROL_FORM", pForm);
        }
    }

    public static void populateShoppingControlDataNewXpedx(HttpServletRequest request, SiteShoppingControlForm pForm) {

        if (pForm != null) {

            pForm.setMap(new HashMap());
            pForm.setMap2(new HashMap());

            SiteSettingsData siteSettings = SessionTool.getSiteSettings(request, ShopTool.getCurrentSiteId(request));
            java.util.ArrayList<ShoppingControlItemView> sctrlv = null;
            if (siteSettings != null && siteSettings.getShoppingControls() != null) {
                sctrlv = new java.util.ArrayList<ShoppingControlItemView>(siteSettings.getShoppingControls());
            }

            for (int i = 0; sctrlv != null && i < sctrlv.size(); i++) {

                ShoppingControlItemView siteItemControl = sctrlv.get(i);
                String key = String.valueOf(siteItemControl.getShoppingControlData().getItemId());

                String maxOrderQty;
                                
                /***
                if (siteItemControl.getShoppingControlData().getMaxOrderQty() < 0 ||
                        !RefCodeNames.SIMPLE_STATUS_CD.ACTIVE.equals(siteItemControl.getShoppingControlData().getControlStatusCd())) {
                    maxOrderQty = " ";
                } else {
                    maxOrderQty = String.valueOf(siteItemControl.getShoppingControlData().getMaxOrderQty());
                }
                ***/
                if (!RefCodeNames.SIMPLE_STATUS_CD.ACTIVE.equals(siteItemControl.getShoppingControlData().getControlStatusCd())) {
                    maxOrderQty = " ";
                } else {
                    maxOrderQty = String.valueOf(siteItemControl.getShoppingControlData().getMaxOrderQty());
                }
                
                String restrictionDays;
                /***
                if (siteItemControl.getShoppingControlData().getRestrictionDays() < 0 ||
                        !RefCodeNames.SIMPLE_STATUS_CD.ACTIVE.equals(siteItemControl.getShoppingControlData().getControlStatusCd())) {
                    restrictionDays = " ";
                } else {
                    restrictionDays = String.valueOf(siteItemControl.getShoppingControlData().getRestrictionDays());
                }
                ***/
                if (!RefCodeNames.SIMPLE_STATUS_CD.ACTIVE.equals(siteItemControl.getShoppingControlData().getControlStatusCd())) {
                    restrictionDays = " ";
                } else {
                    restrictionDays = String.valueOf(siteItemControl.getShoppingControlData().getRestrictionDays());
                }
                
                pForm.setItemIdMaxAllowed(key, maxOrderQty);
                pForm.setItemIdRestrictionDays(key, restrictionDays);

            }

            request.setAttribute("SITE_SHOPPING_CONTROL_FORM", pForm);
        }
    }
    
    public static void putShoppingControlItems2Form(HttpServletRequest request, SiteShoppingControlForm form) throws Exception {
        if (form == null) {
            return;
        }
        CleanwiseUser appUser = (CleanwiseUser)request.getSession().getAttribute("ApplicationUser");
        int accountId = appUser.getSite().getAccountId();
        int catalogId = appUser.getSite().getSiteCatalogId();
        int siteId = appUser.getSite().getSiteId();

        AccCategoryToCostCenterView pCategToCostCenterView =
        	SessionTool.getCategoryToCostCenterViewByAcc(request.getSession(), accountId);
        
        APIAccess factory = APIAccess.getAPIAccess();
        Site siteBean = factory.getSiteAPI();
        ShoppingRestrictionsViewVector shoppingVector =
            siteBean.getAllShoppingRestrictions(accountId, catalogId, siteId, pCategToCostCenterView);

        ArrayList<ShoppingRestrictionsView> shoppingViews = new ArrayList<ShoppingRestrictionsView>();
        if (shoppingVector != null && shoppingVector.size() > 0) {
            for (int i = 0; i < shoppingVector.size(); ++i) {
                shoppingViews.add((ShoppingRestrictionsView)shoppingVector.get(i));
            }
        }

        form.setShoppingRestrictionsViews(shoppingViews);
    }

    public static void putShoppingControlItems2Form(HttpServletRequest request, UserMgrDetailForm form) throws Exception {
        if (form == null) {
            return;
        }
        ArrayList<ShoppingRestrictionsView> shoppingViews = new ArrayList<ShoppingRestrictionsView>();
        CleanwiseUser appUser = (CleanwiseUser)request.getSession().getAttribute("ApplicationUser");

        SiteData site = appUser.getSite();
        if (site != null) {
            int accountId = site.getAccountId(); 
            int catalogId = site.getSiteCatalogId(); 
            int siteId = site.getSiteId();

            AccCategoryToCostCenterView pCategToCostCenterView =
            	SessionTool.getCategoryToCostCenterViewByAcc(request.getSession(), accountId);
            
            Site siteBean = APIAccess.getAPIAccess().getSiteAPI();       
            ShoppingRestrictionsViewVector shoppingVector = 
                siteBean.getShoppingRestrictions(accountId, catalogId, siteId,pCategToCostCenterView);

            if (shoppingVector != null && shoppingVector.size() > 0) {
                for (int i = 0; i < shoppingVector.size(); ++i) {
                    shoppingViews.add((ShoppingRestrictionsView)shoppingVector.get(i));
                }
            }        	
        }

        form.setShoppingRestrictionsViews(shoppingViews);
    }
}



