/*
 * StoreSiteMgrLogic.java
 *
 * Created on March 20, 2006, 12:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.cleanwise.view.logic;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Budget;
import com.cleanwise.service.api.session.Catalog;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.CustomerRequestPoNumber;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.Request;
import com.cleanwise.service.api.session.Schedule;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.session.Workflow;
import com.cleanwise.service.api.util.BudgetUtil;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.BlanketPoNumData;
import com.cleanwise.service.api.value.BlanketPoNumDataVector;
import com.cleanwise.service.api.value.BudgetData;
import com.cleanwise.service.api.value.BudgetDetailData;
import com.cleanwise.service.api.value.BudgetDetailDataVector;
import com.cleanwise.service.api.value.BudgetSpendView;
import com.cleanwise.service.api.value.BudgetView;
import com.cleanwise.service.api.value.BudgetViewVector;
import com.cleanwise.service.api.value.BuildingServicesContractorView;
import com.cleanwise.service.api.value.BuildingServicesContractorViewVector;
import com.cleanwise.service.api.value.BusEntityAssocData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntityFieldsData;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.CostCenterData;
import com.cleanwise.service.api.value.CostCenterDataVector;
import com.cleanwise.service.api.value.CountryData;
import com.cleanwise.service.api.value.CountryDataVector;
import com.cleanwise.service.api.value.CountryPropertyData;
import com.cleanwise.service.api.value.DeliveryScheduleViewVector;
import com.cleanwise.service.api.value.FiscalCalenderData;
import com.cleanwise.service.api.value.FiscalCalenderView;
import com.cleanwise.service.api.value.FiscalCalenderViewVector;
import com.cleanwise.service.api.value.FiscalPeriodView;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.service.api.value.PhoneDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.ShoppingControlData;
import com.cleanwise.service.api.value.ShoppingControlDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteInventoryConfigView;
import com.cleanwise.service.api.value.SiteInventoryConfigViewVector;
import com.cleanwise.service.api.value.SiteSettingsData;
import com.cleanwise.service.api.value.SiteView;
import com.cleanwise.service.api.value.SiteViewVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.service.api.value.UserSearchCriteriaData;
import com.cleanwise.service.api.value.WorkflowData;
import com.cleanwise.service.api.value.WorkflowDataVector;
import com.cleanwise.service.api.value.WorkflowRuleDataVector;
import com.cleanwise.view.forms.BSCDetailForm;
import com.cleanwise.view.forms.InventoryForm;
import com.cleanwise.view.forms.SiteBudget;
import com.cleanwise.view.forms.SiteShoppingControlForm;
import com.cleanwise.view.forms.StoreSiteMgrForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;

/**
 *
 * @author Ykupershmidt
 */
public class StoreSiteMgrLogic {
    private static final Logger log = Logger.getLogger(StoreSiteMgrLogic.class);
    private static String className = "StoreSiteMgrLogic";

    /**
     *  <code>getAll</code> sites, note that the Site list returned will be
     *  limited to a certain amount of records. It is up to the jsp page to
     *  detect this and to issued a subsequent call to get more records.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
  /*
    public static void getAll(HttpServletRequest request,
            ActionForm form)
        throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
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
*/

    /**
     *  Describe <code>getDetail</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@return                The Detail value
     *@exception  Exception  if an error occurs
     */

    public static StoreSiteMgrForm getDetail(HttpServletRequest request,
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
        StoreSiteMgrForm pForm = (StoreSiteMgrForm) form;
        fetchDetail(request, siteid,pForm);
        return pForm;
    }

    private static void fetchDetail
        (HttpServletRequest request, int pSiteId, StoreSiteMgrForm pForm)
        throws DataNotFoundException, Exception {

        HttpSession session = request.getSession();


        init(request, pForm);


        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        Account accti = factory.getAccountAPI();
        Store storei = factory.getStoreAPI();
        PropertyService propi = factory.getPropertyServiceAPI();
        CustomerRequestPoNumber custPoBean = factory.getCustomerRequestPoNumberAPI();
        BusEntityData acct = null;
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        SiteData sd = null;

        try {
            sd = siteBean.getSiteForStore(pSiteId,appUser.getUserStoreAsIdVector(),true, SessionTool.getCategoryToCostCenterView(session, pSiteId));
        }catch (DataNotFoundException e) {
           pForm=new  StoreSiteMgrForm();
           request.setAttribute("STORE_ADMIN_SITE_FORM",pForm);
           init(request,pForm);
           throw new DataNotFoundException(e.getMessage());
        }


        if (null == sd) {
           throw new DataNotFoundException("SiteMgrLogic.fetchDetail: error, site: " +
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
            pForm.setBlanketPoNumId(new Integer(sd.getBlanketPoNum().getBlanketPoNumId()));
        }else{
            pForm.setBlanketPoNumId(new Integer(0));
        }

        pForm.setName(bed.getShortDesc());
        pForm.setStatusCd(bed.getBusEntityStatusCd());
        pForm.setId(bed.getBusEntityId());

        pForm.setTypeCd(bed.getBusEntityTypeCd());
        pForm.setSiteNumber(bed.getErpNum());
        pForm.setOldSiteNumber(bed.getErpNum());
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String effDateS="";
        Date effDate = bed.getEffDate();
        if(effDate!=null) {
          effDateS = df.format(effDate);
        }
        pForm.setEffDate(effDateS);
        pForm.setSiteData(sd);

        String expDateS="";
        Date expDate = bed.getExpDate();
        if(expDate!=null) {
          expDateS = df.format(expDate);
        }
        pForm.setExpDate(expDateS);

        if ( sd.hasBSC() )
        {
            pForm.setSubContractor(sd.getBSC().getBusEntityData().getShortDesc());
        }
        else
        {
            pForm.setSubContractor("");
        }

        if(sd.getTargetFacilityRank() != null){
            pForm.setTargetFacilityRank(sd.getTargetFacilityRank().toString());
        }else{
            pForm.setTargetFacilityRank("");
        }


        PropertyData taxableIndicator = sd.getTaxableIndicator();
        String taxable = "N";
        if(taxableIndicator!=null && Utility.isSet(taxableIndicator.getValue())) {
          if(Utility.isTrue(taxableIndicator.getValue())) taxable = "Y";
        }
        pForm.setTaxableIndicator(taxable);

        PropertyData invShopping = sd.getInventoryShopping();
        pForm.setInventoryShoppingStr(invShopping.getValue());

        PropertyData invShoppingType = sd.getInventoryShoppingType();
        pForm.setInventoryShoppingTypeStr(invShoppingType.getValue());
        
        //STJ-4384
        PropertyData reBillProperty = sd.getReBill();
        pForm.setReBill(Utility.isTrue(reBillProperty.getValue()));
        
        PropertyData invShoppingHoldOrder = sd.getInventoryShoppingHoldOrderUntilDeliveryDate();
        pForm.setInventoryShoppingHoldOrderUntilDeliveryDate(Utility.isTrue(invShoppingHoldOrder.getValue()));
        
        PropertyData allowCorpSchedOrder = sd.getAllowCorpSchedOrder();
        pForm.setAllowCorpSchedOrder(Utility.isTrue(allowCorpSchedOrder.getValue()));

        acct = sd.getAccountBusEntity();
        int acctid = acct.getBusEntityId();
        pForm.setAccountId(String.valueOf(acctid));
        pForm.setAccountName(acct.getShortDesc());
        // Get the fields configured by the account.
        BusEntityFieldsData sfd = propi.fetchSiteFieldsData(acctid);
        session.setAttribute("Site.account.fields", sfd);
        session.setAttribute("Site.account.id", pForm.getAccountId());
        session.setAttribute("Account.id", pForm.getAccountId());

        // Set the account name.
        AccountData acctdata = accti.getAccount(acctid, 0);
        pForm.setAccountName(acctdata.getBusEntity().getShortDesc());

        BusEntityAssocData storeassoc = acctdata.getStoreAssoc();

        // Set the store id and store name.
        int storeid = storeassoc.getBusEntity2Id();
        StoreData storedata = storei.getStore(storeid);

        pForm.setStoreId(String.valueOf(storeid));
        session.setAttribute("Site.store.id", pForm.getStoreId());
        pForm.setStoreName(storedata.getBusEntity().getShortDesc());
        session.setAttribute("Site.store.name", pForm.getStoreName());

        String allowBusgetThreshold = Utility.getPropertyValue(storedata.getMiscProperties(), RefCodeNames.PROPERTY_TYPE_CD.BUDGET_THRESHOLD_FL);
        String budgetThresholdType = Utility.getPropertyValue(acctdata.getMiscProperties(), RefCodeNames.PROPERTY_TYPE_CD.BUDGET_THRESHOLD_TYPE);

        pForm.setAllowBudgetThreshold(Utility.isTrue(allowBusgetThreshold));
        pForm.setBudgetThresholdType(Utility.strNN(budgetThresholdType));

        AddressData address = sd.getSiteAddress();
        pForm.setName1(address.getName1());
        pForm.setName2(address.getName2());
        pForm.setPostalCode(address.getPostalCode());
        pForm.setStateOrProv(address.getStateProvinceCd());
        pForm.setCounty(address.getCountyCd());
        pForm.setCountry(address.getCountryCd());
        pForm.setCity(address.getCity());
        pForm.setStreetAddr1(address.getAddress1());
        pForm.setStreetAddr2(address.getAddress2());
        pForm.setStreetAddr3(address.getAddress3());
        pForm.setStreetAddr4(address.getAddress4());

        // phone
        pForm.setPhone("");
        PhoneDataVector sitePhones = sd.getPhones();
        if (sitePhones == null) {
            sitePhones = new PhoneDataVector();
        }
        if (sitePhones.size() > 0) {
            PhoneData sitePhone = (PhoneData)sitePhones.get(0);
            pForm.setPhone(sitePhone.getPhoneNum());
        }


        PropertyDataVector props = sd.getMiscProperties();
        pForm.setComments("");
        pForm.setShippingMessage("");
		pForm.setProductBundle("");

        boolean foundDistRefNum = false;
        Iterator itr = props.iterator();
        while (itr.hasNext()) {
            PropertyData prop = (PropertyData) itr.next();
            if (prop.getShortDesc().equals
                (RefCodeNames.PROPERTY_TYPE_CD.SITE_COMMENTS)
                ) {
                pForm.setComments(prop.getValue());
            } else if (prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.SITE_PRODUCT_BUNDLE)) {
                pForm.setProductBundle(prop.getValue());
            }
            else if (prop.getShortDesc().equals
                     (RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG)
                     ) {
                pForm.setShippingMessage(prop.getValue());
            }
            else if (prop.getShortDesc().equals
                     (RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER)
                     ) {
                pForm.setSiteReferenceNumber(prop.getValue());
            }
            else if (prop.getShortDesc().equals
                    (RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER)
                    ) {
               pForm.setDistSiteReferenceNumber(prop.getValue());
               foundDistRefNum = true;
           }
           else if (prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.BYPASS_ORDER_ROUTING)) {
                 if(Utility.isTrue(prop.getValue())){
                    pForm.setBypassOrderRouting(true);
                 }else{
                    pForm.setBypassOrderRouting(false);
                 }
            }
            else if (prop.getShortDesc().
                 equals(RefCodeNames.PROPERTY_TYPE_CD.CONSOLIDATED_ORDER_WAREHOUSE)) {
                 if(Utility.isTrue(prop.getValue())){
                    pForm.setConsolidatedOrderWarehouse(true);
                 }else{
                    pForm.setConsolidatedOrderWarehouse(false);
                 }
            }
            else if (prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES)) {
                 if(Utility.isTrue(prop.getValue())){
                    pForm.setShareBuyerOrderGuides(true);
                 } else {
                    pForm.setShareBuyerOrderGuides(false);
                 }
            }
            else if(prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SITE_LLC)){
            	pForm.setLineLevelCode(prop.getValue());
            }

        }

        if(!foundDistRefNum){
        	pForm.setDistSiteReferenceNumber("");
        }
        PropertyDataVector props2 = sd.getDataFieldProperties();
        Iterator itr2 = props2.iterator();
        pForm.setF1Value("");
        pForm.setF2Value("");
        pForm.setF3Value("");
        pForm.setF4Value("");
        pForm.setF5Value("");
        pForm.setF6Value("");
        pForm.setF7Value("");
        pForm.setF8Value("");
        pForm.setF9Value("");
        pForm.setF10Value("");
        while (itr2.hasNext()) {
            PropertyData prop = (PropertyData) itr2.next();
            if (prop.getPropertyTypeCd().equals
                (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD)
                ) {
                String tag = prop.getShortDesc();
                if ( null == tag ) continue;
                if ( tag.equals(sfd.getF1Tag()) ) {
                    pForm.setF1Value(prop.getValue());
                }
                else if ( tag.equals(sfd.getF2Tag()) ) {
                    pForm.setF2Value(prop.getValue());
                }
                else if ( tag.equals(sfd.getF3Tag()) ) {
                    pForm.setF3Value(prop.getValue());
                }
                else if ( tag.equals(sfd.getF4Tag()) ) {
                    pForm.setF4Value(prop.getValue());
                }
                else if ( tag.equals(sfd.getF5Tag()) ) {
                    pForm.setF5Value(prop.getValue());
                }
                else if ( tag.equals(sfd.getF6Tag()) ) {
                    pForm.setF6Value(prop.getValue());
                }
                else if ( tag.equals(sfd.getF7Tag()) ) {
                    pForm.setF7Value(prop.getValue());
                }
                else if ( tag.equals(sfd.getF8Tag()) ) {
                    pForm.setF8Value(prop.getValue());
                }
                else if ( tag.equals(sfd.getF9Tag()) ) {
                    pForm.setF9Value(prop.getValue());
                }
                else if ( tag.equals(sfd.getF10Tag()) ) {
                    pForm.setF10Value(prop.getValue());
                }
            }
        }


        session.setAttribute("Site.id", String.valueOf(pSiteId));

        //Get lawson data
        pForm.setAvailableShipto(null);
        pForm.setERPEnabled(false);
        pForm.setAvailableShipto(new IdVector());

        return;
    }

    private static void cleanDetailFields
        (HttpServletRequest request, StoreSiteMgrForm pForm)
        throws Exception {

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();
        int storeId = storeD.getStoreId();
        init(request, pForm);
        session.removeAttribute("Site.account.blanketPos");
        pForm.setBlanketPoNumId(new Integer(0));

        pForm.setName("");
        pForm.setStatusCd("");
        pForm.setId(0);

        pForm.setTypeCd("");
        pForm.setSiteNumber("");
        pForm.setSiteReferenceNumber("");
        pForm.setDistSiteReferenceNumber("");
        pForm.setLineLevelCode("");
        pForm.setOldSiteNumber("");
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        pForm.setEffDate("");
        pForm.setSiteData(null);
        pForm.setExpDate("");
        pForm.setSubContractor("");
        pForm.setTargetFacilityRank("");
        pForm.setTaxableIndicator("N");
        pForm.setInventoryShoppingStr("");
        pForm.setInventoryShoppingTypeStr("");
        pForm.setInventoryShoppingHoldOrderUntilDeliveryDate(false);
        pForm.setAccountId("0");
        pForm.setAccountName("");
        session.removeAttribute("Site.account.fields");
        session.removeAttribute("Site.account.id");
        session.removeAttribute("Account.id");
        pForm.setAccountName("");
        pForm.setStoreId(String.valueOf(storeId));
        session.setAttribute("Site.store.id", pForm.getStoreId());
        pForm.setStoreName(storeD.getBusEntity().getShortDesc());
        session.setAttribute("Site.store.name", pForm.getStoreName());

        pForm.setName1("");
        pForm.setName2("");
        pForm.setPostalCode("");
        pForm.setStateOrProv("");
        pForm.setCounty("");
        pForm.setCountry(storeD.getPrimaryAddress().getCountryCd());
        pForm.setCity("");
        pForm.setStreetAddr1("");
        pForm.setStreetAddr2("");
        pForm.setStreetAddr3("");
        pForm.setStreetAddr4("");
        pForm.setPhone("");

        pForm.setComments("");
        pForm.setShippingMessage("");

        pForm.setF1Value("");
        pForm.setF2Value("");
        pForm.setF3Value("");
        pForm.setF4Value("");
        pForm.setF5Value("");
        pForm.setF6Value("");
        pForm.setF7Value("");
        pForm.setF8Value("");
        pForm.setF9Value("");
        pForm.setF10Value("");


        session.removeAttribute("Site.id");
        pForm.setERPEnabled(false);
        pForm.setAvailableShipto(new IdVector());

        pForm.setShareBuyerOrderGuides(false);
        return;
    }

    /**
     *  Describe <code>getBudgets</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors getBudgets(HttpServletRequest request,
                                  ActionForm form)
        throws Exception {

    	ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        StoreSiteMgrForm pForm = (StoreSiteMgrForm) form;


        APIAccess factory = new APIAccess();

        // Get the site data
        Site siteBean = factory.getSiteAPI();
        int siteId = pForm.getIntId();
        
        //List of Budget years for this site.
        List<Integer> fiscalYears =  new ArrayList<Integer>();
        
        //Gets the Account data.
        Account accountBean = factory.getAccountAPI();
        int selectedFiscalYear = pForm.getSelectedFiscalYear();
        int accountId = Utility.parseInt(pForm.getAccountId());
        int currentFiscalYear = getCurrentFiscalYear(accountId);
        
     // STJ-3813        
        boolean isZeroFiscalYear = false;
        if(currentFiscalYear<=0) {
	        GregorianCalendar cal = new GregorianCalendar();
	        cal.setTime(new Date());
	        int currentYear = cal.get(Calendar.YEAR);
	        isZeroFiscalYear = true;
	        fiscalYears.add(currentYear);
	        fiscalYears.add(currentYear+1);
	        currentFiscalYear = currentYear;
        }

        if(selectedFiscalYear==0 || selectedFiscalYear<currentFiscalYear) {
        	selectedFiscalYear = currentFiscalYear;
        }

        
        Budget budgetBean = factory.getBudgetAPI();

        // Get all the cost centers available for the site's account
        CostCenterDataVector costCenters =
            accountBean.getAllCostCenters(accountId, Account.ORDER_BY_NAME);

        //FiscalPeriodView fiscalInfo = accountBean.getFiscalInfo(accountId); 
        FiscalPeriodView fiscalInfo;
       //STJ-3827        
        fiscalInfo = FiscalPeriodView.createValue();
        FiscalCalenderView fcv = accountBean.getFiscalCalenderVForYear(accountId,selectedFiscalYear);
        fiscalInfo.setFiscalCalenderView(fcv);

        // This will be the array of all the site budget info
        ArrayList budgets = new ArrayList();
        pForm.setSiteBudgetList(budgets);

        int costCenterId = 0;
        // Iterate thru the account's cost centers
        Iterator costIter = costCenters.iterator();
        for (int i = 0; costIter.hasNext(); i++) {
          CostCenterData costCenter = (CostCenterData) costIter.next();
          costCenterId = costCenter.getCostCenterId();
          SiteBudget siteBudget = new SiteBudget();
          siteBudget.setName(costCenter.getShortDesc());
          siteBudget.setCostCenterData(costCenter);
         // siteBudget.setId(String.valueOf(siteId));

          //Get budgets for site.
          BudgetViewVector budgetViewVector = budgetBean.getBudgetsForSite(accountId, siteId, costCenterId, selectedFiscalYear);
          Iterator budgetIter = budgetViewVector.iterator();
          
          while (budgetIter.hasNext()) {
            BudgetView budget = (BudgetView) budgetIter.next();
            if (budget.getBudgetData().getCostCenterId() == costCenterId) {
              siteBudget.setBudgetView(budget);
              siteBudget.setCostCenterData(costCenter);
              siteBudget.setId(String.valueOf(budget.getBudgetData().getBusEntityId()));
              if(currentFiscalYear==selectedFiscalYear) {
            	  BudgetSpendView spent = siteBean.getBudgetSpent(siteId, costCenterId);
            	  
            	  //STJ - 3628
            	  int currentBudgetPeriod = spent.getBudgetPeriod();
            	  if(budget.getDetails().size()>0){
            		  BudgetDetailData budgetDetailData = (BudgetDetailData)budget.getDetails().get(currentBudgetPeriod-1);
                	  if(budgetDetailData!=null && budgetDetailData.getAmount()!=null) {
                		  siteBudget.setSiteBudgetRemaining(spent.getAmountAllocated().subtract(spent.getAmountSpent()));
                	  } else {
                		  siteBudget.setSiteBudgetRemaining(null);
                	  }
            	  }
                  
                  siteBudget.setCurrentBudgetPeriod(currentBudgetPeriod);
                  siteBudget.setCurrentBudgetYear(spent.getBudgetYear());
              } else {
                  siteBudget.setCurrentBudgetPeriod(0);
                  siteBudget.setCurrentBudgetYear(selectedFiscalYear);
                  siteBudget.setSiteBudgetRemaining(new BigDecimal(0));
              }
              break;
            }
          }

          // if it has no explicit budget, it's zero
          if (siteBudget.getBudgetView() == null) {
              BudgetData budgetData = BudgetData.createValue();
              budgetData.setCostCenterId(costCenterId);
              budgetData.setBudgetTypeCd(costCenter.getCostCenterTypeCd());
              siteBudget.setBudgetView(new BudgetView(budgetData,new BudgetDetailDataVector()));
              siteBudget.setCurrentBudgetYear(selectedFiscalYear);
              siteBudget.setSiteBudgetRemaining(new BigDecimal(0));
              int currentBudgetPeriod = 0;
              if(currentFiscalYear==selectedFiscalYear) {
            	  currentBudgetPeriod = budgetBean.getAccountBudgetPeriod(accountId, siteId, null);
              }
              siteBudget.setCurrentBudgetPeriod(currentBudgetPeriod);
          }
          budgets.add(siteBudget);
        }
        pForm.setSiteBudgetList(budgets);
        pForm.setFiscalInfo(fiscalInfo);
        
        pForm.setEnableCopyBudgetsButton(false);
        if(currentFiscalYear>0) {
        	 FiscalCalenderViewVector fsViewVector = accountBean.getFiscalCalCollection(accountId);
        	 //all fiscal years greater than or equals to current fiscal year,
             //should be displayed in the fiscal year drop down.
        	 if(fsViewVector.size()>0) {
             	FiscalCalenderView fcd;
             	int fiscalCalYear;
                // Get the site data
             	for(int i=0; i<fsViewVector.size(); i++) {
             		fcd = (FiscalCalenderView) fsViewVector.get(i);
             		fiscalCalYear = fcd.getFiscalCalender().getFiscalYear();
             		if((selectedFiscalYear==fiscalCalYear || fiscalCalYear==0) && costCenters.size()>0) { // For Zero fiscal calendar.
         				BudgetViewVector budgetViewVector = budgetBean.getBudgetsForSite(accountId, siteId, 0 , selectedFiscalYear-1);
         				if ( budgetViewVector != null && budgetViewVector.size() > 0 ) {
         					 pForm.setEnableCopyBudgetsButton(true); 
         				}
             		}
             		if(fiscalCalYear>=currentFiscalYear && !fiscalYears.contains(fiscalCalYear)){
             			fiscalYears.add(fiscalCalYear);
             		} 
             	}// end of for loop.
             	if(fiscalYears.size()==0 && currentFiscalYear>0) {// multiple calendar exists and most recent calendar year is 0
        	        isZeroFiscalYear = true;
        	        fiscalYears.add(currentFiscalYear);
        	        fiscalYears.add(currentFiscalYear+1);
                }
             } 
        }
        
        Collections.sort(fiscalYears);
        pForm.setFiscalYearsList(fiscalYears);
        pForm.setSelectedFiscalYear(selectedFiscalYear);
        
        // this is ugly - but the cost detail jsp page needs
        // these session variables set; needs to be standardized
        // somehow
        session.setAttribute("Account.id", pForm.getAccountId());
        session.setAttribute("Account.name", pForm.getAccountName());
        session.setAttribute("Store.id", pForm.getStoreId());
        session.setAttribute("Store.name", pForm.getStoreName());
        return ae;
    }

    public static ActionErrors setBudgets(HttpServletRequest request,
                                  ActionForm form)
        throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        StoreSiteMgrForm pForm = (StoreSiteMgrForm) form;

        APIAccess factory = new APIAccess();
        Budget budBean = factory.getBudgetAPI();
        String cuser = (String) session.getAttribute(Constants.USER_NAME);

        boolean isUsedSiteBudgetThr = false;
        if (BudgetUtil.isUsedSiteBudgetThreshold(pForm.getAllowBudgetThreshold(), pForm.getBudgetThresholdType())) {
            isUsedSiteBudgetThr = true;
        }

        ArrayList v = pForm.getSiteBudgetList();
        
        //STJ - 3834: Do not assign SITE_BUDGET and ACCOUNT_BUDGET to a site.
        ae = checkForBudgetErrors(v);
        if(ae.size()>0) {
        	return ae;
        }
        
        // Get the site data
        for ( int i = 0; null != v && i < v.size(); i++ ) {

          SiteBudget sb = (SiteBudget)v.get(i);

          String cctype = sb.getCostCenterData().getCostCenterTypeCd();
          String budgetThreshold = sb.getBudgetThreshold();
          
          if(sb.isNegativeAmount() || sb.isInCorrectAmount()) { // STJ-3909
        	  String errorMessage = "Incorrect budget amount values.(Only positive numbers are allowed for budget amounts).";
        	  ae.add("error", new ActionError("error.simpleGenericError",errorMessage));
        	  sb.setNegativeAmount(false);
        	  sb.setInCorrectAmount(false);
        	  return ae;
          }
          

          if (isUsedSiteBudgetThr && BudgetUtil.isWrongBudgetThreshold(budgetThreshold)) {
              String mp = "Wrong Budget Threshold '%1s' for Cost Center(s) '%2s'. Must be integer between -100 and 100.";
              ae.add("error", new ActionError("error.simpleGenericError", String.format(mp, budgetThreshold, sb.getName())));
              continue;
          }

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
          BudgetView budget = sb.getBudgetView();
          if (budget.getBudgetData().getBudgetId() == 0) {
             budget.getBudgetData().setAddBy(cuser);
          }
          budget.getBudgetData().setBusEntityId(Integer.parseInt(sb.getId()));
          budget.getBudgetData().setBudgetTypeCd(cctype);

          int budgetYear = 0;
          if(pForm.getSelectedFiscalYear()>0) {
        	  budgetYear = pForm.getSelectedFiscalYear();
          } else {
        	  budgetYear =pForm.getFiscalInfo().getFiscalCalenderView().getFiscalCalender().getFiscalYear();
          }
          budget.getBudgetData().setBudgetYear(budgetYear);
          budget.getBudgetData().setBudgetStatusCd(RefCodeNames.BUDGET_STATUS_CD.ACTIVE);

         if (isUsedSiteBudgetThr) {
              if (Utility.isSet(budgetThreshold)) {
                 budgetThreshold = String.valueOf(Utility.parsePercentInt(budgetThreshold));
                 budget.getBudgetData().setBudgetThreshold(budgetThreshold);
              } else {
                 budget.getBudgetData().setBudgetThreshold(null);
              }
         }

          budget = budBean.updateBudget(budget,cuser);
          if (budget.getBudgetData().getBudgetId() == 0) {
              ae.add("error",new ActionError("error.simpleGenericError","Cannot update budget."));
          } else {
              log.info
                (" UPDATED, setBudgets, upd costCenterId="
                 + sb.getCostCenterData().getCostCenterId()
                 + " site Id="  + sb.getId()
                 + " cctype=" + cctype
                 );

          }
          sb.setBudgetView(budget);

        }
        return ae;
    }
    
    public static ActionErrors setAllUnlimited(HttpServletRequest request, ActionForm form) throws Exception {
    	ActionErrors ae = new ActionErrors();
        StoreSiteMgrForm pForm = (StoreSiteMgrForm) form;
        // Get the site data
        ArrayList v = pForm.getSiteBudgetList();
        //STJ - 3834: Do not assign SITE_BUDGET and ACCOUNT_BUDGET to a site.
        ae = checkForBudgetErrors(v);
        if(ae.size()>0) {
        	return ae;
        }
        for ( int i = 0; null != v && i < v.size(); i++ ) {
        	 SiteBudget sb = (SiteBudget)v.get(i);
             String cctype = sb.getCostCenterData().getCostCenterTypeCd();
             if ( RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET.equals(cctype) ) {
            	 try {
            		 BudgetDetailDataVector budgetView = sb.getBudgetView().getDetails();
            		 Iterator it  = budgetView.iterator();
            		 while(it.hasNext()) {
            			 BudgetDetailData budgetDetailData = (BudgetDetailData)it.next();
            			 budgetDetailData.setAmount(null);
            		 }
            	 }catch(Exception e) {
            	 }
            	 
             }
        }
        return ae;
    }

    /**
     * Copies Previous fiscal year budgets to current year budgets for a site.
     * @param request		-	HttpServletRequest.
     * @param form			-	StoreSiteMgrForm 
     * @return	ActionErrors-	Returns ActionErrors if any.
     * @throws Exception	-	If an error occurs.
     */
    public static ActionErrors copyBudgetsFromPreviousYear(HttpServletRequest request, ActionForm form) throws Exception {
    	
    	ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        StoreSiteMgrForm pForm = (StoreSiteMgrForm) form;

        APIAccess factory = new APIAccess();
        Budget budgetBean = factory.getBudgetAPI();

        // Get the site data
        Site siteBean = factory.getSiteAPI();
        int siteId = pForm.getIntId();
        
        
        //Gets the Account data.
        Account accountBean = factory.getAccountAPI();
        int fiscalYear = pForm.getSelectedFiscalYear();
        int previousYear = fiscalYear-1;
        int accountId = Utility.parseInt(pForm.getAccountId());
        
        // Get all the cost centers available for the site's account
        CostCenterDataVector costCenters = accountBean.getAllCostCenters(accountId, Account.ORDER_BY_NAME);
        boolean costCentersMatchFound = false;
        
        // This will be the array of all the site budget info
        ArrayList budgets = new ArrayList();

        // Get all budget details for required year of a site.
        BudgetViewVector budgetViewVector = budgetBean.getBudgetsForSite(accountId, siteId, 0 , fiscalYear-1);
        
        if(costCenters!=null && costCenters.size()>0) {
        	// Iterate thru the account's cost centers
	        Iterator costIter = costCenters.iterator();
	        for (int i = 0; costIter.hasNext(); i++) {
	          CostCenterData costCenter = (CostCenterData) costIter.next();
	          int costCenterId = costCenter.getCostCenterId();
	          SiteBudget siteBudget = new SiteBudget();
	          siteBudget.setName(costCenter.getShortDesc());
	          siteBudget.setCostCenterData(costCenter);
	         // siteBudget.setId(String.valueOf(siteId));
	
	          Iterator budgetIter = budgetViewVector.iterator();
	          while (budgetIter.hasNext()) {
	            BudgetView budget = (BudgetView) budgetIter.next();
	            if (budget.getBudgetData().getCostCenterId() == costCenterId) {
	            	costCentersMatchFound = true;
	              siteBudget.setBudgetView(budget);
	              siteBudget.setCostCenterData(costCenter);
	              siteBudget.setId(String.valueOf(budget.getBudgetData().getBusEntityId()));
	              break;
	            }
	          }
	
	          // if it has no explicit budget, it's zero
	          if (siteBudget.getBudgetView() != null) {
	        	  siteBudget.getBudgetView().getBudgetData().setBudgetId(0);
	        	  siteBudget.getBudgetView().getBudgetData().setBudgetYear(fiscalYear);
	        	  budgets.add(siteBudget);	        	
	          } 
	        }
    	}
        
        if(costCentersMatchFound) {
        	pForm.setSiteBudgetList(budgets);
        	ae = setBudgets(request, pForm);
        } else {
        	ae.add("error",new ActionError("error.simpleGenericError","No cost centers from the previous year match" +
			" so nothing has been copied."));
        }
        return ae;
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

        if (null == session.getAttribute("country.vector")) {
          Country countryBean = factory.getCountryAPI();
          CountryDataVector countriesv = countryBean.getAllCountries();
          session.setAttribute("country.vector", countriesv);
        }

       if (null == session.getAttribute("store.site.productbundle.vector")) {
          RefCdDataVector productBundleList =  lsvc.getRefCodesCollection("SITE_PRODUCT_BUNDLE");
          session.setAttribute("store.site.productbundle.vector", productBundleList);
        }
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
        StoreSiteMgrForm pForm = (StoreSiteMgrForm) form;


        // Reset the session variables.
        SiteViewVector siteVwV = new SiteViewVector();
        session.setAttribute("Site.found.vector", siteVwV);
        session.setAttribute("Site.found.total", "0");

        String fieldSearchType = pForm.getSearchType();
        String fieldValue = pForm.getSearchField();
        String fieldSearchRefNum = pForm.getSearchRefNum();
        String fieldSearchRefNumType = pForm.getSearchRefNumType();
        String city = pForm.getSearchCity();
        String county = pForm.getSearchCounty();
        String state = pForm.getSearchState();
        String postalCode = pForm.getSearchPostalCode();
        boolean showInactiveFl = pForm.getShowInactiveFl();
        AccountDataVector accountDV = pForm.getAccountFilter();
        IdVector accountIdV = new IdVector();
        if(accountDV!=null) {
          for(Iterator iter = accountDV.iterator(); iter.hasNext();) {
            AccountData accountD = (AccountData) iter.next();
            accountIdV.add(new Integer(accountD.getBusEntity().getBusEntityId()));
          }
        }

        /*siteVwV = search(request, fieldValue, fieldSearchType, accountIdV, city,
                   county, state, postalCode, showInactiveFl);*/
        siteVwV = search(request, fieldValue, fieldSearchType, fieldSearchRefNum, fieldSearchRefNumType,
        		accountIdV, city,
                county, state, postalCode, showInactiveFl);

        //set sites for accounts that crcs cannot shop for into the noShop
        //vector

        SiteViewVector noShop = new SiteViewVector();
        SiteViewVector inactiveAccountSite = new SiteViewVector();

        Hashtable accounts = new Hashtable();
        Account acctBean = factory.getAccountAPI();

        IdVector accountIds = new IdVector();
        IdVector accountIds1 = new IdVector();
        IdVector inactiveAccountIds = new IdVector();

        for(Iterator iter=siteVwV.iterator(); iter.hasNext();){
            SiteView sVw = (SiteView)iter.next();
            accountIds.add(sVw.getAccountId());
            accountIds1.add(sVw.getAccountId());
        }
        Map<Integer, String> mapValues = acctBean.getPropertyValues(accountIds,
                    RefCodeNames.PROPERTY_TYPE_CD.CRC_SHOP);

        // find INACTIVE Accounts' Ids
        if (accountIds1.size() > 0) {
           inactiveAccountIds = acctBean.getInactiveAccountIds(accountIds1);
        }

        for(Iterator iter=siteVwV.iterator(); iter.hasNext();){
            SiteView sVw = (SiteView)iter.next();
//            String acctk = String.valueOf(sVw.getAccountId());
//            AccountData aD = null;
//            if ( accounts.containsKey(acctk))
//            {
//                aD = (AccountData)accounts.get(acctk);
//            }
//            else
//            {
//                aD = acctBean.getAccountForSite(sVw.getId());
//                accounts.put(acctk, aD);
//            }
//
//            String shop = aD.getCrcShop().getValue();
            String shop = mapValues.get(sVw.getAccountId());
            if(shop != null && shop.equals("true")){
                noShop.add(sVw);
            }
        }
        // Convert ArrayList inactiveAccountIds into an Integer array ia
        ArrayList<Integer> iaIds = new ArrayList<Integer>();
        iaIds = (ArrayList) inactiveAccountIds;
        Integer ia[] = new Integer[iaIds.size()];
        ia = iaIds.toArray(ia);

        for(Iterator iter=siteVwV.iterator(); iter.hasNext();){
            SiteView sVw = (SiteView)iter.next();
            for(int i=0; i<ia.length; i++){
                if(sVw.getAccountId() == ia[i].intValue()){
                   inactiveAccountSite.add(sVw);
                }
            }
        }

        session.setAttribute("Site.noShop.vector", noShop);
        session.setAttribute("Site.found.vector", siteVwV);
        session.setAttribute("Site.found.total", String.valueOf(siteVwV.size()));

        session.setAttribute("Site.inactiveAccountSite.vector", inactiveAccountSite);
    }


    /** DEPRICATED !!!!!!!!!!!!!!!!!!!!!!!!!
     *searchs the site without the use of any forms.
     */
    public static SiteViewVector search(HttpServletRequest request,String fieldValue,String fieldSearchType,
            String accountIdList,String city,String county,String state,String postalCode)
    throws Exception{
      IdVector accountIdV = new IdVector();
      if(Utility.isSet(accountIdList)) {
        StringTokenizer tok = new StringTokenizer(accountIdList,",");
        while(tok.hasMoreTokens()){
          String aIdS =tok.nextToken().trim();
          try{
            int accountId = Integer.parseInt(aIdS);
            accountIdV.add(new Integer(accountId));
          } catch(Exception exc) {}
        }
      }
      SiteViewVector siteVwV = search(request, fieldValue, fieldSearchType,null,null,
              accountIdV, city, county, state, postalCode, true);
      return siteVwV;

    }

    /**
     *searchs the site without the use of any forms.
     */
    public static SiteViewVector search(HttpServletRequest request,String fieldValue,String fieldSearchType,
    		String fieldSearchRefNum, String fieldSearchRefNumType,
            IdVector accountIdV,String city,String county,String state,String postalCode, boolean showInactive)
    throws Exception{
      HttpSession session = request.getSession();
      APIAccess factory = new APIAccess();
      Site siteBean = factory.getSiteAPI();

      SiteViewVector siteVwV;
      fieldValue = fieldValue.trim();
      fieldSearchRefNum = fieldSearchRefNum.trim();
      boolean multiFieldSearch = true;
      boolean emptySearch = true;

      QueryRequest qr = new QueryRequest();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      int storeId = appUser.getUserStore().getStoreId();
      IdVector storeIdV = new IdVector();
      storeIdV.add(new Integer(storeId));
      qr.filterByStoreIds(storeIdV);
      qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);
      boolean siteByNameFl = true;
      if(Utility.isSet(fieldValue)) {
        if(fieldSearchType.equals("id")) {
          siteByNameFl = false;
           int id = 0;
          try {
            id = Integer.parseInt(fieldValue.trim());
          }catch(Exception exc){}
          qr.filterBySiteId(id);
        } else {
          if (fieldSearchType.equals("nameBegins")) {
            //qr.filterBySiteName(fieldValue, QueryRequest.BEGINS_IGNORE_CASE);
        	  qr.filterByOnlySiteName(fieldValue,QueryRequest.BEGINS_IGNORE_CASE);
          }
          else if (fieldSearchType.equals("nameContains")) {
            //qr.filterBySiteName(fieldValue, QueryRequest.CONTAINS_IGNORE_CASE);
        	  qr.filterByOnlySiteName(fieldValue,QueryRequest.CONTAINS_IGNORE_CASE);
          }
        }
      }
      if(Utility.isSet(fieldSearchRefNum)) {

    	  if (fieldSearchRefNumType.equals("nameBegins")) {
    		  qr.filterByRefNum(fieldSearchRefNum, QueryRequest.BEGINS_IGNORE_CASE);
    	  }
    	  else if (fieldSearchRefNumType.equals("nameContains")) {
    		  qr.filterByRefNum(fieldSearchRefNum, QueryRequest.CONTAINS_IGNORE_CASE);
    	  }
      }

      if (Utility.isSet(city)) {
        siteByNameFl = false;
        qr.filterByCity(city.trim(), QueryRequest.BEGINS_IGNORE_CASE);
      }
      if (Utility.isSet(county)) {
        siteByNameFl = false;
        qr.filterByCounty(county.trim(), QueryRequest.BEGINS_IGNORE_CASE);
      }
      if (Utility.isSet(state)) {
        siteByNameFl = false;
        qr.filterByState(state.trim(), QueryRequest.BEGINS_IGNORE_CASE);
      }
      if (Utility.isSet(postalCode)) {
        siteByNameFl = false;
        qr.filterByZip(postalCode.trim(), QueryRequest.BEGINS_IGNORE_CASE);
      }
      if(accountIdV!=null && accountIdV.size()>0) {
        siteByNameFl = false;
        qr.filterByAccountIds(accountIdV);
      }

      if(!showInactive) {
        ArrayList statusAL = new ArrayList();
        statusAL.add(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        statusAL.add(RefCodeNames.BUS_ENTITY_STATUS_CD.LIMITED);
        qr.filterBySiteStatusCdList(statusAL);
      }
     /* if(siteByNameFl && Utility.isSet(fieldValue)) {
        int match = (fieldSearchType.equals("nameBegins"))?
             Site.BEGINS_WITH_IGNORE_CASE:
             Site.CONTAINS_IGNORE_CASE;
        siteVwV =
          siteBean.getSiteByName(fieldValue.trim(),0,storeId,showInactive,
                match,Site.ORDER_BY_NAME);
      } else {*/
        siteVwV = siteBean.getSiteCollection(qr);
  //    }
      return siteVwV;

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
        StoreSiteMgrForm pForm = (StoreSiteMgrForm) form;
        cleanDetailFields(request, pForm);
        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        StoreData store = appUser.getUserStore();
        pForm.setAvailableShipto(new IdVector());


        // These can only be set after the account has been chosen.
        session.removeAttribute("Site.account.fields");
        setDefaultCountry(pForm);
    }


    public static void setDefaultCountry(StoreSiteMgrForm pForm) throws Exception {
      AccountDataVector accountDV = pForm.getAccountFilter();
      if(accountDV!=null && accountDV.size()>0) {
        AccountData accountD = (AccountData) accountDV.get(0);
        pForm.setCountry(accountD.getPrimaryAddress().getCountryCd());
      }
    }


    /**
     *  Describe <code>cloneSite</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void cloneSite(HttpServletRequest request,
                               ActionForm form)
        throws Exception {

        StoreSiteMgrForm pForm = (StoreSiteMgrForm) form;

        String name = pForm.getSiteData().getBusEntity().getShortDesc();
        if (name == null) name = "";
        name = "Clone of >>> " + name;

        pForm.setName(name);
        pForm.setId(0);

        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        StoreData store = appUser.getUserStore();
        pForm.setSiteReferenceNumber("");
        pForm.setDistSiteReferenceNumber("");
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
        StoreSiteMgrForm pForm = (StoreSiteMgrForm) form;
        Date effDate = null;
        Date expDate = null;
        BlanketPoNumData blanketPo = null;
        Integer targetFacilityRank = null;

        int accountId = 0;
        try{
            if(Utility.isSet(pForm.getAccountId())){
              accountId = Integer.parseInt(pForm.getAccountId());
            }
        }catch(NumberFormatException e){//exception handled later in validation
        }



        if (pForm != null) {

           StoreData store = null;
            if(accountId > 0){
                store = AccountMgrLogic.getStoreForManagedAccount(session, accountId);
            }

        // Verify the form information submitted.
        if(pForm.getCity()!=null){pForm.setCity(pForm.getCity().trim());}
        if(pForm.getStreetAddr1()!=null){pForm.setStreetAddr1(pForm.getStreetAddr1().trim());}
        if(pForm.getStreetAddr2()!=null){pForm.setStreetAddr2(pForm.getStreetAddr2().trim());}
        if(pForm.getStreetAddr3()!=null){pForm.setStreetAddr3(pForm.getStreetAddr3().trim());}
        if(pForm.getStreetAddr4()!=null){pForm.setStreetAddr4(pForm.getStreetAddr4().trim());}
        if(pForm.getStateOrProv()!=null){pForm.setStateOrProv(pForm.getStateOrProv().trim());}
        if(pForm.getPostalCode()!=null){pForm.setPostalCode(pForm.getPostalCode().trim());}
        if(pForm.getPhone()!=null){pForm.setPhone(pForm.getPhone().trim());}


        if (pForm.getAccountId().length() == 0 ||
          pForm.getAccountId().equals("0")) {
          lUpdateErrors.add("accountId",
                    new ActionError("variable.empty.error", "Account Id"));
          }
        if (pForm.getStatusCd().length() == 0) {
          lUpdateErrors.add("statusCd", new ActionError("variable.empty.error",
                    "Status"));
        }
        if (pForm.getName().length() == 0) {
          lUpdateErrors.add("name", new ActionError("variable.empty.error",
                "Name"));
        }

        /*if (pForm.getName1().length() == 0) {
          lUpdateErrors.add("name1", new ActionError("variable.empty.error",
                 "Name1"));
        }

        if (pForm.getName2().length() == 0) {
          lUpdateErrors.add("name2", new ActionError("variable.empty.error",
                 "Name2"));
        }*/
        if (pForm.getStreetAddr1().length() == 0) {
          lUpdateErrors.add("streetAddr1", new ActionError("variable.empty.error",
                  "Street Address 1"));
        }
        if (pForm.getCity().length() == 0) {
          lUpdateErrors.add("city", new ActionError("variable.empty.error",
                  "City"));
        }
        if (pForm.getCountry().length() == 0) {
          lUpdateErrors.add("country", new ActionError("variable.empty.error",
                   "Country"));
        }
        //if (pForm.getStateOrProv().length() == 0) {
        //  lUpdateErrors.add("stateOrProv", new ActionError("variable.empty.error",
        //          "State/Province"));
       // }
       // check country and province
       CountryData country = getCountry(session, pForm);
       APIAccess factory = new APIAccess();
       Country countryBean = factory.getCountryAPI();
       CountryPropertyData countryProp =
         countryBean.getCountryPropertyData(country.getCountryId(), RefCodeNames.COUNTRY_PROPERTY.USES_STATE);
       boolean isStateProvinceRequired = countryProp != null && countryProp.getValue().equalsIgnoreCase("true");

       if (isStateProvinceRequired && !Utility.isSet(pForm.getStateOrProv())) {
         lUpdateErrors.add("stateOrProv",
                           new ActionError("variable.empty.error",
                                           "State/Province"));
       }
       if (!isStateProvinceRequired && Utility.isSet(pForm.getStateOrProv())) {
         lUpdateErrors.add("stateOrProv",
                           new ActionError("variable.must.be.empty.error",
                                           "State/Province"));
       }


        if (pForm.getPostalCode().length() == 0) {
          lUpdateErrors.add("postalCode",new ActionError("variable.empty.error",
                   "Zip/Postal Code"));
        }
        if (pForm.getTargetFacilityRank()!= null && pForm.getTargetFacilityRank().length() > 0) {
          try{
            targetFacilityRank = new Integer(pForm.getTargetFacilityRank());
          }catch(NumberFormatException e){
            lUpdateErrors.add("targetFacilityRank", new ActionError("error.invalidNumber",
                   "Target Facility Rank"));
          }
        }

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String effDateS = pForm.getEffDate();
        if(effDateS!=null && effDateS.trim().length()>0) {
          try{
            effDate = df.parse(effDateS);
          } catch (Exception exc) {
           lUpdateErrors.add("effDate", new ActionError("variable.date.format.error",
                 "Effective Date"));
          }
        }
        String expDateS = pForm.getExpDate();
        if(expDateS!=null && expDateS.trim().length()>0) {
          try{
            expDate = df.parse(expDateS);
          } catch (Exception exc) {
            lUpdateErrors.add("expDate", new ActionError("variable.date.format.error",
                  "Expiration Date"));
          }
        }
        if(effDate!=null && expDate!=null && expDate.before(effDate)) {
          lUpdateErrors.add("EffExpDates", new ActionError("error.simpleGenericError",
                   "Effective date can't be after expiration date"));
        }
        String statusCd = pForm.getStatusCd();
        if(statusCd.equals(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE)){
          Date curDate = new Date();
          if((effDate!=null && effDate.after(curDate)) ||
             (expDate!=null && expDate.before(curDate))) {
            lUpdateErrors.add("EffExpDatesStatusCd", new ActionError("error.simpleGenericError",
                     "Effective and expiration dates conflict with site status"));

          }
        }
      }
      if(pForm.getBlanketPoNumId() != null && pForm.getBlanketPoNumId().intValue()!=0){
        BlanketPoNumDataVector bpdv =
              (BlanketPoNumDataVector)session.getAttribute("Site.account.blanketPos");
        if(bpdv != null){
          int bpoid = pForm.getBlanketPoNumId().intValue();
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
      
      if (pForm.getInventoryShopping() && pForm.isAllowCorpSchedOrder()){
    	  lUpdateErrors.add("InventoryShopping", new ActionError("error.simpleGenericError",
          "Cannot set both \"Enable Inventory Shopping\" and \"Allow Corporate Scheduled Order\" for a site at the same time.  Please choose only one."));
      }
      
      if (lUpdateErrors.size() > 0) {
        // Report the errors to allow for edits.
        return lUpdateErrors;
      }

      APIAccess factory = new APIAccess();
      Site siteBean = factory.getSiteAPI();
     // AddressValidator validator = factory.getAddressValidatorAPI();

      int siteid = pForm.getIntId();
      // Get the current information for this Site.
      SiteData dd;
      BusEntityData bed;
      PropertyDataVector props;
      PropertyDataVector siteFields = new PropertyDataVector();
      PropertyDataVector siteFieldsRuntime = new PropertyDataVector();
      AddressData address;
      PropertyData taxableIndicator, 
      				invprop,invpropHoldOrder,
      				invpropType,reBill, allowCorpSchedOrder;

      if (siteid > 0) {
        dd = siteBean.getSite(siteid, accountId, true, SessionTool.getCategoryToCostCenterView(session, siteid));
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
      reBill = dd.getReBill();
      invpropHoldOrder = dd.getInventoryShoppingHoldOrderUntilDeliveryDate();
      invpropType=dd.getInventoryShoppingType();
      allowCorpSchedOrder = dd.getAllowCorpSchedOrder();

      bed.setWorkflowRoleCd("UNKNOWN");
      String cuser = (String) session.getAttribute(Constants.USER_NAME);

      // Now update with the data from the form.
      bed.setShortDesc(pForm.getName());
      bed.setLongDesc(pForm.getName());
      bed.setBusEntityStatusCd(pForm.getStatusCd());
      bed.setEffDate(effDate);
      bed.setExpDate(expDate);
      bed.setErpNum(pForm.getSiteNumber());
      bed.setLocaleCd("unk");
      bed.setModBy(cuser);

      address.setModBy(cuser);
      address.setName1(pForm.getName1());
      address.setName2(pForm.getName2());
      address.setAddress1(pForm.getStreetAddr1());
      address.setAddress2(pForm.getStreetAddr2());
      address.setAddress3(pForm.getStreetAddr3());
      address.setAddress4(pForm.getStreetAddr4());
      address.setCity(pForm.getCity());
      address.setStateProvinceCd(pForm.getStateOrProv());
      address.setPostalCode(pForm.getPostalCode());
      address.setCountryCd(pForm.getCountry());
      address.setPrimaryInd(true);
      address.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
      address.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);


      //if(!validator.validateAddress(address)){
     //   lUpdateErrors.add("updateSite",new ActionError("error.addressValidation"));
    //    return(lUpdateErrors);
     // }

      // set phone
      PhoneDataVector sitePhones = dd.getPhones();
      if (sitePhones == null) sitePhones = new PhoneDataVector();
      if (sitePhones.size() > 0) {
          PhoneData sitePhone = (PhoneData)sitePhones.get(0);
          sitePhone.setPhoneNum(pForm.getPhone());
      } else {
          if (Utility.isSet(pForm.getPhone())) {
              PhoneData sitePhone = PhoneData.createValue();
              sitePhone.setPhoneNum(pForm.getPhone());
              sitePhone.setBusEntityId(dd.getSiteId());
              sitePhone.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.PHONE);
              sitePhone.setShortDesc("Primary Phone");
              sitePhone.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
              sitePhones.add(sitePhone);
          }
      }


      dd.setBSC(findBSC(request,pForm.getSubContractor()));

      taxableIndicator.setValue(pForm.getTaxableIndicator());
      taxableIndicator.setAddBy(cuser);

      boolean updfComments = false;
      boolean updfShipMsg = false;
      boolean siteRefNum = false;
      boolean distSiteRefNum = false;
      boolean lineLevelCode = false;
      boolean bypassOrderRouting = false;
      boolean consolidatedOrderWarehouse = false;
      boolean shareOrderGuide = false;
      boolean updProductBundle = false;
      for (Iterator itr = props.iterator(); itr.hasNext();) {
        PropertyData prop = (PropertyData) itr.next();
        if (prop.getShortDesc().equals
          (RefCodeNames.PROPERTY_TYPE_CD.SITE_COMMENTS)) {
          prop.setValue(pForm.getComments());
          updfComments = true;
        }
        else if (prop.getShortDesc().equals
                 (RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG)) {
          prop.setValue(pForm.getShippingMessage());
          updfShipMsg = true;
        } else if (prop.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.SITE_PRODUCT_BUNDLE)) {
            prop.setValue(pForm.getProductBundle());
            updProductBundle = true;
        }
        else if (prop.getShortDesc().equals
                 (RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER)) {
          prop.setValue(pForm.getSiteReferenceNumber());
          siteRefNum = true;
        }
        else if (prop.getShortDesc().equals
                (RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER)) {
         prop.setValue(pForm.getDistSiteReferenceNumber());
         distSiteRefNum = true;
       }
       else if (prop.getShortDesc().equals
                (RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SITE_LLC)) {
         prop.setValue(pForm.getLineLevelCode());
         lineLevelCode = true;
       }
        else if (prop.getShortDesc().equals
                 (RefCodeNames.PROPERTY_TYPE_CD.BYPASS_ORDER_ROUTING)) {
          if(pForm.isBypassOrderRouting()){
            prop.setValue("true");
          }else{
            prop.setValue("false");
          }
          //prop.setValue(Boolean.toString(pForm.isBypassOrderRouting()));
          bypassOrderRouting = true;
        }
        else if (prop.getShortDesc().equals
                 (RefCodeNames.PROPERTY_TYPE_CD.CONSOLIDATED_ORDER_WAREHOUSE)) {
          if(pForm.isConsolidatedOrderWarehouse()){
            prop.setValue("true");
          }else{
            prop.setValue("false");
          }
          consolidatedOrderWarehouse = true;
        }
        else if (prop.getShortDesc().equals
                 (RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES)) {
          if(pForm.getShareBuyerOrderGuides()){
            prop.setValue("true");
          }else{
            prop.setValue("false");
          }
          shareOrderGuide = true;
        }
      }

      boolean tf = pForm.isInventoryShoppingHoldOrderUntilDeliveryDate();
      if ( tf == true ) {
        invpropHoldOrder.setValue("true");
        invpropHoldOrder.setModBy(cuser);
      }
      else {
        invpropHoldOrder.setValue("false");
        invpropHoldOrder.setModBy(cuser);
      }
      dd.setInventoryShoppingHoldOrderUntilDeliveryDate(invpropHoldOrder);

      /* As per bug 3690, modified UI(Site Detail page), so that a single button
       * 'Enable Inventory Shopping' will modify both mInventoryShopping and 
       * mInventoryShoppingType
       */
      tf = pForm.getInventoryShopping();
      if(invpropType == null){
    		invpropType=PropertyData.createValue();
    	}
      if ( tf == true ) {
        invprop.setValue("on");
        invprop.setAddBy(cuser);
        
        invpropType.setValue("on");
        invpropType.setAddBy(cuser);
      }
      else {
        invprop.setValue("off");
        invprop.setModBy(cuser);
        
        invpropType.setValue("off");
        invpropType.setModBy(cuser);
      }
      dd.setInventoryShopping(invprop);
      dd.setInventoryShoppingType(invpropType);
      
      if(allowCorpSchedOrder == null){
    	  allowCorpSchedOrder=PropertyData.createValue();
  	  }
      tf = pForm.isAllowCorpSchedOrder();
      if ( tf == true ) {
    	  allowCorpSchedOrder.setValue("true");
    	  allowCorpSchedOrder.setModBy(cuser);
      }
      else {
    	  allowCorpSchedOrder.setValue("false");
    	  allowCorpSchedOrder.setModBy(cuser);
      }
      dd.setAllowCorpSchedOrder(allowCorpSchedOrder);
     
      if (updfComments == false) {
        PropertyData nprop = PropertyData.createValue();
        nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SITE_COMMENTS);
        nprop.setValue(pForm.getComments());
        nprop.setAddBy(cuser);
        props.add(nprop);
      }

      if (updfShipMsg == false) {
        PropertyData nprop = PropertyData.createValue();
        nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG);
        nprop.setValue(pForm.getShippingMessage());
        nprop.setAddBy(cuser);
        props.add(nprop);
      }

        if (!updProductBundle) {
            PropertyData nprop = PropertyData.createValue();
            nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SITE_PRODUCT_BUNDLE);
            nprop.setValue(pForm.getProductBundle());
            nprop.setAddBy(cuser);
            props.add(nprop);
        }

      if (siteRefNum == false) {
        PropertyData nprop = PropertyData.createValue();
        nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
        nprop.setValue(pForm.getSiteReferenceNumber());
        nprop.setAddBy(cuser);
        props.add(nprop);
      }

      if (distSiteRefNum == false) {
          PropertyData nprop = PropertyData.createValue();
          nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER);
          nprop.setValue(pForm.getDistSiteReferenceNumber());
          nprop.setAddBy(cuser);
          props.add(nprop);
      }

      if (lineLevelCode == false) {
          PropertyData nprop = PropertyData.createValue();
          nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SITE_LLC);
          nprop.setValue(pForm.getLineLevelCode());
          nprop.setAddBy(cuser);
          props.add(nprop);
        }

      if (bypassOrderRouting == false) {
        PropertyData nprop = PropertyData.createValue();
        nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.BYPASS_ORDER_ROUTING);
        if(pForm.isBypassOrderRouting()){
          nprop.setValue("true");
        }else{
          nprop.setValue("false");
        }
        //nprop.setValue(Boolean.toString(pForm.isBypassOrderRouting()));
        nprop.setAddBy(cuser);
        props.add(nprop);
      }
      if (consolidatedOrderWarehouse == false) {
        PropertyData nprop = PropertyData.createValue();
        nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.CONSOLIDATED_ORDER_WAREHOUSE);
        if(pForm.isConsolidatedOrderWarehouse()){
          nprop.setValue("true");
        }else{
          nprop.setValue("false");
        }
        nprop.setAddBy(cuser);
        props.add(nprop);
      }
      if (shareOrderGuide == false) {
        PropertyData nprop = PropertyData.createValue();
        nprop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
        nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
        if(pForm.getShareBuyerOrderGuides()){
          nprop.setValue("true");
        }else{
          nprop.setValue("false");
        }
        nprop.setAddBy(cuser);
        props.add(nprop);
      }
      
      if (reBill==null || reBill.getBusEntityId()==0) {
          PropertyData nprop = PropertyData.createValue();
          nprop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SHOW_REBILL_ORDER);
          nprop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
          nprop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SHOW_REBILL_ORDER);
          if(pForm.isReBill()){
            nprop.setValue("true");
          }else{
            nprop.setValue("false");
          }
          nprop.setAddBy(cuser);
          nprop.setModBy(cuser);
          Date now = new Date();
          nprop.setAddDate(now);
          nprop.setModDate(now);
          props.add(nprop);
        } else {
        	if(pForm.isReBill()){
        		reBill.setValue("true");
            }else{
            	reBill.setValue("false");
            }
        	reBill.setModBy(cuser);
            Date now = new Date();
            reBill.setModDate(now);
        }

      // Set up the site fields vector.
      BusEntityFieldsData sfd =
              (BusEntityFieldsData) session.getAttribute("Site.account.fields");
      if (null != sfd ) {
        if (sfd.getF1ShowAdmin() || sfd.getF1ShowRuntime() ) {
          if(sfd.getF1Required() && !Utility.isSet(pForm.getF1Value())){
            lUpdateErrors.add("F1Tag", new ActionError("variable.empty.error",
                sfd.getF1Tag()));
          }
          PropertyData nprop = PropertyData.createValue();
          nprop.setPropertyTypeCd
              (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
          nprop.setShortDesc(sfd.getF1Tag());
          nprop.setValue(pForm.getF1Value());
          nprop.setAddBy(cuser);
          siteFields.add(nprop);
          if(sfd.getF1ShowRuntime()){
            siteFieldsRuntime.add(nprop);
          }

        }
        if ( sfd.getF2ShowAdmin() || sfd.getF2ShowRuntime()  ) {
          if(sfd.getF2Required() && !Utility.isSet(pForm.getF2Value())){
              lUpdateErrors.add("F2Tag",
                            new ActionError("variable.empty.error",sfd.getF2Tag()));
          }
          PropertyData nprop = PropertyData.createValue();
          nprop.setPropertyTypeCd
              (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
          nprop.setShortDesc(sfd.getF2Tag());
          nprop.setValue(pForm.getF2Value());
          nprop.setAddBy(cuser);
          siteFields.add(nprop);
          if(sfd.getF2ShowRuntime()){
            siteFieldsRuntime.add(nprop);
          }
        }
        if ( sfd.getF3ShowAdmin() || sfd.getF3ShowRuntime()  ) {
          if(sfd.getF3Required() && !Utility.isSet(pForm.getF3Value())){
            lUpdateErrors.add("F3Tag",
                        new ActionError("variable.empty.error",sfd.getF3Tag()));
          }
          PropertyData nprop = PropertyData.createValue();
          nprop.setPropertyTypeCd
              (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
          nprop.setShortDesc(sfd.getF3Tag());
          nprop.setValue(pForm.getF3Value());
          nprop.setAddBy(cuser);
          siteFields.add(nprop);
          if(sfd.getF3ShowRuntime()){
            siteFieldsRuntime.add(nprop);
          }
        }
        if ( sfd.getF4ShowAdmin() || sfd.getF4ShowRuntime()  ) {
          if(sfd.getF4Required() && !Utility.isSet(pForm.getF4Value())){
            lUpdateErrors.add("F4Tag",
                          new ActionError("variable.empty.error",sfd.getF4Tag()));
          }
          PropertyData nprop = PropertyData.createValue();
          nprop.setPropertyTypeCd
              (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
          nprop.setShortDesc(sfd.getF4Tag());
          nprop.setValue(pForm.getF4Value());
          nprop.setAddBy(cuser);
          siteFields.add(nprop);
          if(sfd.getF4ShowRuntime()){
            siteFieldsRuntime.add(nprop);
          }
        }
        if ( sfd.getF5ShowAdmin() || sfd.getF5ShowRuntime()  ) {
          if(sfd.getF5Required() && !Utility.isSet(pForm.getF5Value())){
            lUpdateErrors.add("F5Tag",
                       new ActionError("variable.empty.error",sfd.getF5Tag()));
          }
          PropertyData nprop = PropertyData.createValue();
          nprop.setPropertyTypeCd
              (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
          nprop.setShortDesc(sfd.getF5Tag());
          nprop.setValue(pForm.getF5Value());
          nprop.setAddBy(cuser);
          siteFields.add(nprop);
          if(sfd.getF5ShowRuntime()){
            siteFieldsRuntime.add(nprop);
          }
        }
        if ( sfd.getF6ShowAdmin() || sfd.getF6ShowRuntime()  ) {
          if(sfd.getF6Required() && !Utility.isSet(pForm.getF6Value())){
            lUpdateErrors.add("F6Tag",
                     new ActionError("variable.empty.error",sfd.getF6Tag()));
          }
          PropertyData nprop = PropertyData.createValue();
          nprop.setPropertyTypeCd
              (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
          nprop.setShortDesc(sfd.getF6Tag());
          nprop.setValue(pForm.getF6Value());
          nprop.setAddBy(cuser);
          siteFields.add(nprop);
          if(sfd.getF6ShowRuntime()){
            siteFieldsRuntime.add(nprop);
          }
        }
        if ( sfd.getF7ShowAdmin() || sfd.getF7ShowRuntime()  ) {
          if(sfd.getF7Required() && !Utility.isSet(pForm.getF7Value())){
              lUpdateErrors.add("F7Tag",
                            new ActionError("variable.empty.error",sfd.getF7Tag()));
          }
          PropertyData nprop = PropertyData.createValue();
          nprop.setPropertyTypeCd
              (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
          nprop.setShortDesc(sfd.getF7Tag());
          nprop.setValue(pForm.getF7Value());
          nprop.setAddBy(cuser);
          siteFields.add(nprop);
          if(sfd.getF7ShowRuntime()){
            siteFieldsRuntime.add(nprop);
          }
        }
        if ( sfd.getF8ShowAdmin() || sfd.getF8ShowRuntime()  ) {
          if(sfd.getF8Required() && !Utility.isSet(pForm.getF8Value())){
            lUpdateErrors.add("F8Tag",
                        new ActionError("variable.empty.error",sfd.getF8Tag()));
          }
          PropertyData nprop = PropertyData.createValue();
          nprop.setPropertyTypeCd
              (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
          nprop.setShortDesc(sfd.getF8Tag());
          nprop.setValue(pForm.getF8Value());
          nprop.setAddBy(cuser);
          siteFields.add(nprop);
          if(sfd.getF8ShowRuntime()){
            siteFieldsRuntime.add(nprop);
          }
        }
        if ( sfd.getF9ShowAdmin() || sfd.getF9ShowRuntime()  ) {
          if(sfd.getF9Required() && !Utility.isSet(pForm.getF9Value())){
            lUpdateErrors.add("F9Tag",
                          new ActionError("variable.empty.error",sfd.getF9Tag()));
          }
          PropertyData nprop = PropertyData.createValue();
          nprop.setPropertyTypeCd
              (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
          nprop.setShortDesc(sfd.getF9Tag());
          nprop.setValue(pForm.getF9Value());
          nprop.setAddBy(cuser);
          siteFields.add(nprop);
          if(sfd.getF9ShowRuntime()){
            siteFieldsRuntime.add(nprop);
          }
        }
        if ( sfd.getF10ShowAdmin() || sfd.getF10ShowRuntime()  ) {
          if(sfd.getF10Required() && !Utility.isSet(pForm.getF10Value())){
            lUpdateErrors.add("F10Tag",
                          new ActionError("variable.empty.error",sfd.getF10Tag()));
          }
          PropertyData nprop = PropertyData.createValue();
          nprop.setPropertyTypeCd
              (RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD);
          nprop.setShortDesc(sfd.getF10Tag());
          nprop.setValue(pForm.getF10Value());
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
          AccountData ad = accountBean.getAccount(accountId, 0);
          pForm.setAccountName(ad.getBusEntity().getShortDesc());
        } catch (DataNotFoundException de) {
          // the given account id is apparently not an account
          pForm.setAccountName("");
          lUpdateErrors.add("Account.id", new ActionError("site.bad.account"));
          return lUpdateErrors;
        } catch (Exception e) {
          e.printStackTrace();
           lUpdateErrors.add("SiteErrors",
                    new ActionError("error.genericError",e.getMessage()));
          return lUpdateErrors;
        }

        // create the default property ShareOrderGuide
        /*PropertyData pdata = PropertyData.createValue();
        pdata.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
        pdata.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SHARE_ORDER_GUIDES);
        pdata.setAddBy(cuser);
        pdata.setValue("true");
        props.add(pdata);*/

        // Add the site.
        bed.setAddBy(cuser);
        bed.setBusEntityStatusCd(pForm.getStatusCd());
        address.setAddBy(cuser);
         try {
          dd = siteBean.addSite(dd, accountId);
        } catch (DuplicateNameException ne) {
          lUpdateErrors.add("name", new ActionError("error.field.notUnique",
              "Name"));
          return lUpdateErrors;
        } catch (Exception e) {
          String errorMess = Utility.getUiErrorMess(e.getMessage());
          if(errorMess==null) errorMess = e.getMessage();
           lUpdateErrors.add("SiteErrors",
                new ActionError("error.simpleGenericError",errorMess));
          return lUpdateErrors;
        }
        siteid = dd.getBusEntity().getBusEntityId();
        pForm.setId(siteid);
      }
      else {
        // Update this Site
        try {
          siteBean.updateSite(dd);
        } catch (DuplicateNameException ne) {
          lUpdateErrors.add("name", new ActionError("error.field.notUnique",
              "Name"));
          return lUpdateErrors;
        } catch (Exception e) {
          String errorMess = Utility.getUiErrorMess(e.getMessage());
          if(errorMess==null) errorMess = e.getMessage();
           lUpdateErrors.add("SiteErrors",
                new ActionError("error.simpleGenericError",errorMess));
          return lUpdateErrors;
        }
      }
      pForm.setAvailableShipto(null);
      try {
          fetchDetail(request, siteid,pForm);
      }
      catch (DataNotFoundException e) {
        String errorMess = Utility.getUiErrorMess(e.getMessage());
        if(errorMess==null) errorMess = e.getMessage();
        lUpdateErrors.add("SiteErrors",
              new ActionError("error.simpleGenericError",errorMess));
        return lUpdateErrors;
      }

      return lUpdateErrors;
    }

    private static ActionErrors validateSiteErpNum(int accountId, int siteId, String siteErpNum) {
        ActionErrors ae = new ActionErrors();
        if (Utility.isSet(siteErpNum)) {
            try {
                APIAccess factory = new APIAccess();
                Site siteEjb = factory.getSiteAPI();

                BusEntityDataVector sites = siteEjb.getSiteByErpNum(accountId, siteErpNum);

                if (sites.size() > 1 ||
                        (sites.size() == 1 &&
                                siteId == 0)) {

                    ae.add("SiteErrors", new ActionError("error.field.notUnique", "Site Erp Number"));

                } else if (sites.size() == 1 && siteId > 0) {
                    if (((BusEntityData) sites.get(0)).getBusEntityId() != siteId) {

                        ae.add("SiteErrors", new ActionError("error.field.notUnique", "Site Erp Number"));

                    }

                }
            } catch (Exception e) {
                error(e.getMessage(), e);
                String errorMess = Utility.getUiErrorMess(e.getMessage());
                if (errorMess == null) errorMess = e.getMessage();
                ae.add("SiteErrors", new ActionError("error.simpleGenericError", errorMess));
            }
        }
        return ae;
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
        StoreSiteMgrForm pForm = (StoreSiteMgrForm) form;
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
        SiteData dd = siteBean.getSite(id.intValue(), acctid, false,  SessionTool.getCategoryToCostCenterView(session, id.intValue()));

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
        session.removeAttribute("Related.site.distschedules.vector");
        session.removeAttribute("Related.site.corpschedules.vector");
        return;
    }

    /**
     *  <code>searchConfig</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
  public static ActionErrors searchConfig(HttpServletRequest request,
            ActionForm form)
  throws Exception
  {
    ActionErrors ae = new ActionErrors();
     initConfig(request, form);
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    StoreSiteMgrForm pForm = (StoreSiteMgrForm)form;
    String configType = pForm.getConfigType();

    if (configType.equals("Users")) {
      ae = searchUserConfig(request,form);
    } else if (configType.equals("Catalog")) {
      ae = searchCatalogConfig(request,form);
    } else if(configType.equals("Distributor Schedule")) {
      ae = searchSiteSchedules(request,form);
    } else if(configType.equals("Corporate Schedule")) {
        ae = searchCorporateSchedules(request,form);
    }
    return ae;
  }

  public static ActionErrors searchUserConfig(HttpServletRequest request,
            ActionForm form)
  throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    User userBean = factory.getUserAPI();
    StoreSiteMgrForm pForm = (StoreSiteMgrForm)form;
    String searchField = pForm.getConfigSearchField();
    String searchType = pForm.getConfigSearchType();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

    int accountId = Integer.parseInt(pForm.getAccountId());
    int siteId = Integer.parseInt(pForm.getId());


    // get all users that could possibly be associated with this site
    // (i.e. all account users)
    UserDataVector uv = null;
    UserDataVector users = null;
    if(!Utility.isSet(searchField)) {
      //get all
      //Collects all already configured users
      users = userBean.getUsersCollectionByBusEntityExcludingType(siteId,
    			RefCodeNames.USER_TYPE_CD.DISTRIBUTOR,null);
      if(pForm.getConfiguredOnly()) {
        uv = users;
      } else {
        //Collects all users eligible
    	uv = userBean.getUsersCollectionByBusEntityExcludingType(accountId,
    			  RefCodeNames.USER_TYPE_CD.DISTRIBUTOR,null);
      }
    } else { //filter is set
      int searchTypeCdInt = searchType.equals("nameBegins")?
                                    User.NAME_BEGINS_WITH_IGNORE_CASE:
                                    User.NAME_CONTAINS_IGNORE_CASE;

      IdVector siteIds = new IdVector();
      siteIds.add(new Integer(siteId));
      users = userBean.getUsersCollectionByBusEntityCollectionExcludingType(siteIds,
    		  searchField, RefCodeNames.USER_TYPE_CD.DISTRIBUTOR,
    		  searchTypeCdInt, User.ORDER_BY_NAME);
      if(pForm.getConfiguredOnly()) {
        uv = users;
      } else {
    	IdVector accIds = new IdVector();
    	accIds.add(new Integer(accountId));
        uv = userBean.getUsersCollectionByBusEntityCollectionExcludingType(accIds,
        		  searchField, RefCodeNames.USER_TYPE_CD.DISTRIBUTOR,
        		  searchTypeCdInt, User.ORDER_BY_NAME);

      }
    }

    if(uv!=null && uv.size()>0) {
        UserSearchCriteriaData searchCriteria = UserSearchCriteriaData.createValue();
        searchCriteria.setUserStoreIds(Utility.toIdVector(appUser.getAssociatedStores()));
        searchCriteria.setUserIds(Utility.toIdVector(uv));
        uv = userBean.getUsersCollectionByCriteria(searchCriteria);
    }

    if (users != null && users.size() > 0) {
        UserSearchCriteriaData searchCriteria = UserSearchCriteriaData.createValue();
        searchCriteria.setUserStoreIds(Utility.toIdVector(appUser.getAssociatedStores()));
        searchCriteria.setUserIds(Utility.toIdVector(users));

        users = userBean.getUsersCollectionByCriteria(searchCriteria);
    }

    IdVector userIds = new IdVector();
    String[] selectIds = new String[users.size()];
    int ind = 0;
    for (Iterator userI = users.iterator(); userI.hasNext();) {
      Integer id = new Integer(((UserData)userI.next()).getUserId());

      userIds.add(id);
      selectIds[ind++] = id.toString();
    }

    pForm.setSelectIds(selectIds);
    session.setAttribute("site.assoc.user.ids", userIds);
    request.getSession().setAttribute("site.users.vector", uv);

    return ae;
  }

  public static ActionErrors searchCatalogConfig(HttpServletRequest request,
            ActionForm form)
  throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    StoreSiteMgrForm pForm = (StoreSiteMgrForm)form;
    String searchField = pForm.getConfigSearchField();
    String searchType = pForm.getConfigSearchType();

     int accountId = Integer.parseInt(pForm.getAccountId());
    int siteId = Integer.parseInt(pForm.getId());
    int storeId = Integer.parseInt(pForm.getStoreId());

    CatalogInformation cati = factory.getCatalogInformationAPI();

    // Get the current site catalog
    CatalogData catalogD = cati.getSiteCatalog(siteId);
    if (catalogD != null) {
      String catalogId = String.valueOf(catalogD.getCatalogId());
      pForm.setCatalogId(catalogId);
      pForm.setOldCatalogId(catalogId);
    } else {
      pForm.setCatalogId("0");
      pForm.setOldCatalogId("0");
    }

    CatalogDataVector catalogDV = null;
    if(pForm.getConfiguredOnly()) {
      catalogDV = new CatalogDataVector();
      if(catalogD!=null) {
        if(!Utility.isSet(searchField)) {
          catalogDV.add(catalogD);
        } else {
          searchField = searchField.toUpperCase();
          String ctalogName = catalogD.getShortDesc().toUpperCase();
          int ind = catalogD.getShortDesc().indexOf(searchField);
          if(ind==0 && "nameBegins".equals(searchType) ||
             ind>=0 && "nameContains".equals(searchType)) {
            catalogDV.add(catalogD);
          }
        }
      }
    } else { // not configured only
      if (!Utility.isSet(searchField)) {
        // treat just like a 'view all' request
        catalogDV = cati.getCatalogsByAccountId(accountId);
      } else {
        if(searchType.equals("nameBegins")) {
          catalogDV = cati.getCatalogsCollectionByNameAndBusEntity(searchField,
                               SearchCriteria.BEGINS_WITH_IGNORE_CASE, accountId);
        } else { // "nameContains"
          catalogDV = cati.getCatalogsCollectionByNameAndBusEntity(searchField,
                  SearchCriteria.CONTAINS_IGNORE_CASE, accountId);
        }
      }
    }
    if(catalogDV!=null) {
      for(Iterator iter=catalogDV.iterator(); iter.hasNext();) {
        CatalogData cD = (CatalogData) iter.next();
        String catalogType = cD.getCatalogTypeCd();
        String catalogStatus = cD.getCatalogStatusCd();
        if(!RefCodeNames.CATALOG_STATUS_CD.ACTIVE.equals(catalogStatus) &&
           !RefCodeNames.CATALOG_STATUS_CD.LIMITED.equals(catalogStatus) ) {
          iter.remove();
          continue;
        }
        if(!RefCodeNames.CATALOG_TYPE_CD.SHOPPING.equals(catalogType) &&
           !RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING.equals(catalogType) ) {
          iter.remove();
          continue;
        }
      }
    }
    session.setAttribute("site.catalogs.vector", catalogDV);
    return ae;
  }


  public static ActionErrors searchSiteSchedules(HttpServletRequest request,
                                          ActionForm form)
  throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    Distributor distEjb = factory.getDistributorAPI();
    StoreSiteMgrForm pForm = (StoreSiteMgrForm)form;
    String searchField = pForm.getConfigSearchField();
    String searchType = pForm.getConfigSearchType();
    Hashtable critParams = new Hashtable();
    critParams.put("SITE_ID", pForm.getId());
    boolean beginsWithFl = (searchType.equals("nameBegins"))? true:false;
    if (Utility.isSet(searchField)) {
      critParams.put("DISTRIBUTOR", searchField);
    }
    DeliveryScheduleViewVector dsVwV =
           distEjb.getDeliverySchedules(0, critParams,beginsWithFl);

    session.setAttribute("Related.site.distschedules.vector", dsVwV);
    return ae;
  }

    /**
     *  <code>getAllConfig</code> method.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
  /*
  public static void getAllConfig(HttpServletRequest request,
            ActionForm form)
  throws Exception {
    initConfig(request, form);
    HttpSession session = request.getSession();

    StoreSiteMgrForm configForm = (StoreSiteMgrForm)form;
    String configType = configForm.getConfigType();
    String fieldValue = configForm.getConfigSearchField();
    String fieldSearchType = configForm.getConfigSearchType();

    APIAccess factory = new APIAccess();
    int accountId = Integer.parseInt(configForm.getAccountId());
    int siteId = Integer.parseInt(configForm.getId());

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
        Integer id = new Integer(((UserData)userI.next()).getUserId());
        userIds.add(id);
        selectIds[i++] = new String(id.toString());
      }

      // the currently associated users are checked/selected
      configForm.setSelectIds(selectIds);

      // list of all associated user ids, in the update this
      // will be compared with the selected
      session.setAttribute("site.assoc.user.ids", userIds);

    }

    return;
  }
*/
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

    StoreSiteMgrForm pForm = (StoreSiteMgrForm) form;
     int siteId = Integer.parseInt(pForm.getId());
    int accountId = Integer.parseInt(pForm.getAccountId());

    String configType = pForm.getConfigType();
    if (configType.equals("Users")) {
      User userBean = factory.getUserAPI();
      // get list of user ids displayed
      String[] displayed = pForm.getDisplayIds();
      // get list of user ids selected
      String[] selected = pForm.getSelectIds();
      // get list of currently associated user ids
      IdVector assocUserIds = (IdVector)session.getAttribute("site.assoc.user.ids");

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
     pForm.setSelectIds(selected);

   } else if (configType.equals("Catalog")) {
     String newCatId = pForm.getCatalogId();
     String oldCatId = pForm.getOldCatalogId();
     if (!newCatId.equals(oldCatId)) {
       String cuser = (String) session.getAttribute(Constants.USER_NAME);
       Catalog catBean = factory.getCatalogAPI();
       catBean.resetCatalogAssoc(Integer.parseInt(newCatId), siteId, cuser);
       pForm.setOldCatalogId(newCatId);
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
    StoreSiteMgrForm pForm = (StoreSiteMgrForm)form;
    String sortField = request.getParameter("sortField");
    String configType = request.getParameter("configType");
    if (configType == null) {
      return;
    }

    if (configType.equals("Users")) {
      UserDataVector users = (UserDataVector)session.getAttribute("site.users.vector");
      if (users == null) {
        return;
      }
      DisplayListSort.sort(users, sortField);

      // Need to init the selected/checked users
      IdVector assocUserIds = (IdVector)session.getAttribute("site.assoc.user.ids");
      String[] selected = new String[assocUserIds.size()];
      Iterator assocI = assocUserIds.iterator();
      int i = 0;
      while (assocI.hasNext()) {
        selected[i++] = new String(assocI.next().toString());
      }
      pForm.setSelectIds(selected);
    } else if (configType.equals("Catalog")) {
      CatalogDataVector catalogs =
                 (CatalogDataVector)session.getAttribute("site.users.vector");
      if (catalogs == null) {
        return;
      }
      DisplayListSort.sort(catalogs, sortField);
    }

    // so that the display correctly shows the type of config being done
    pForm.setConfigType(configType);
    return;
  }

    /**
     *  Description of the Method
     *
     *@param   request        Description of Parameter
     *@param   form          Description of Parameter
     *@return                Description of the Returned Value
     *@exception  Exception  Description of Exception
     */
    public static ActionErrors linkSites(HttpServletRequest request,
                                         ActionForm form)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        StoreSiteMgrForm pForm = (StoreSiteMgrForm) form;
        String strSiteId = (String)session.getAttribute("Site.id");
        String strWId=pForm.getSelectedWorkflowId();
        String assignWId=pForm.getAssignedWorkflowId();
        if (null == strSiteId)
        {
            ae.add("site",new ActionError("Site Error: the site id was not specified."));
            return ae;
        }

        APIAccess factory = new APIAccess();
        Workflow wkflBean = factory.getWorkflowAPI();
        Site siteBean = factory.getSiteAPI();

        if(assignWId==null)  assignWId="";
        if (strWId==null)   strWId="";

        if(!strWId.equals(assignWId)) {

          try{

            if(assignWId.trim().length()>0)
            {
             wkflBean.unassignWorkflow(Integer.parseInt(assignWId), Integer.parseInt(strSiteId));
            }
            if(strWId.trim().length()>0)
            {
                WorkflowData w = null;
                int wid=Integer.parseInt(strWId);

                //Assign
                SessionTool lses = new SessionTool(request);
                wkflBean.assignWorkflow(wid, Integer.parseInt(strSiteId),lses.getLoginName());

            }
          } catch (Exception e)
                {
                    ae.add("workflow",
                            new ActionError("Workflow was not found." +
                                    " Error: " + e));
                }


            if(ae.size()==0)
                ae=fetchSiteWorkflow(request,pForm);
        }

       return ae;
    }


     public static WorkflowDataVector  getAllWorkflows(String accountId)
            throws Exception {

        APIAccess factory = new APIAccess();
        Workflow wkfl = factory.getWorkflowAPI();
        int aid = 0;
        String s = accountId;
        if (null == s)
            throw new  Exception("Site Error: the account id was not specified.");
        aid = Integer.parseInt(s);
        return   wkfl.getWorkflowCollectionByEntity(aid);
    }


    public static ActionErrors fetchSiteWorkflow(HttpServletRequest request, ActionForm form)
    throws Exception {

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        String s = (String)session.getAttribute("Site.id");
        if (null == s) {
            ae.add("site",new ActionError("Site Error: the site id was not specified."));
            return ae;
        }

        StoreSiteMgrForm pForm = (StoreSiteMgrForm) form;

        APIAccess factory = new APIAccess();
        Workflow wBean = factory.getWorkflowAPI();
        ListService listServiceEJB = factory.getListServiceAPI();
        int sid = Integer.parseInt(s);
        // Get the site detail information.
        fetchDetail(request, sid,pForm);
        try {

        	if(session.getAttribute("Site.workflow.type.vector")==null) {
                RefCdDataVector statusCds = listServiceEJB.getRefCodesCollection("WORKFLOW_TYPE_CD");
                session.setAttribute("Site.workflow.type.vector", statusCds);
            }

            //reset
            session.setAttribute("Site.workflow.found.vector", null);
            pForm.setAssignedWorkflowId("");
            pForm.setSelectedWorkflowId("");
            if(!Utility.isSet(pForm.getWorkflowTypeCd())){
                pForm.setWorkflowTypeCd(RefCodeNames.WORKFLOW_TYPE_CD.ORDER_WORKFLOW);
            }

            //to get workflows for account

            WorkflowDataVector wDV = filterByType(getAllWorkflows(pForm.getAccountId()), pForm.getWorkflowTypeCd());

            Hashtable htWorkflowRulesColl=new Hashtable();
            if(wDV!=null)
            {
                Iterator it=wDV.iterator();

                while(it.hasNext())
                {
                    int  id=((WorkflowData)it.next()).getWorkflowId();
                    htWorkflowRulesColl.put(new Integer(id), wBean.getWorkflowRulesCollection(id));

                }
            }

            session.setAttribute("Site.workflow.found.vector", wDV);
            session.setAttribute("Site.workflow.rules.hashtable",htWorkflowRulesColl);

            //
            WorkflowData wfd = wBean.fetchWorkflowForSite(sid, pForm.getWorkflowTypeCd());

            if(wfd != null){

            session.setAttribute("Site.workflow", wfd);
            pForm.setAssignedWorkflowId(String.valueOf(wfd.getWorkflowId()));
            pForm.setSelectedWorkflowId(pForm.getAssignedWorkflowId());
            pForm.setWorkflowTypeCd(wfd.getWorkflowTypeCd());

            // Get the rules for the workflow.
            //WorkflowRuleDataVector wrv =wBean.getWorkflowRulesCollection(wfd.getWorkflowId());
            session.setAttribute("Site.workflow.rules.vector", ((WorkflowRuleDataVector)htWorkflowRulesColl.get(new Integer(wfd.getWorkflowId()))));
            }

        }catch (DataNotFoundException e ){
        	session.setAttribute("Site.workflow", null);
            session.setAttribute("Site.workflow.rules.vector",null);
            pForm.setAssignedWorkflowId("");
            pForm.setSelectedWorkflowId(pForm.getAssignedWorkflowId());

        }catch (Exception e) {

            session.setAttribute("Site.workflow", null);
            session.setAttribute("Site.workflow.rules.vector",null);
            pForm.setAssignedWorkflowId("");
            pForm.setSelectedWorkflowId(pForm.getAssignedWorkflowId());

            log.info("Problem getting the Workflow for Site:" +
            sid +   " Error: " + e);
            ae.add("workflow",
            new ActionError("Problem getting the Workflow for Site:" +
            sid +   " Error: " + e));
        }

        return ae;
    }

    private static WorkflowDataVector filterByType(WorkflowDataVector allWorkflows, String pTypeCd) {
        WorkflowDataVector result = new WorkflowDataVector();
        if (!allWorkflows.isEmpty()) {
            Iterator it = allWorkflows.iterator();
            while (it.hasNext()) {
                WorkflowData wd = (WorkflowData) it.next();
                if (wd.getWorkflowTypeCd().equals(pTypeCd)) {
                    result.add(wd);
                }
            }
        }
        return result;
    }


    public static ActionErrors updateInventoryData
        (HttpServletRequest request,
         ActionForm form)    {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = null;
        String cuser =
        (String) session.getAttribute(Constants.USER_NAME);

        try {
            factory = new APIAccess();
            InventoryForm iform = (InventoryForm)form;
            SiteInventoryConfigViewVector sivv = iform.getInventoryItems();
            int siteId = (sivv!=null)? ((SiteInventoryConfigView)sivv.get(0)).getSiteId() : 0;

            InventoryForm oform = (InventoryForm)
                session.getAttribute("SITE_INVENTORY_FORM");
            SiteInventoryConfigViewVector ov = oform.getInventoryItems();

            for ( int ix = 0; ov != null && ix < ov.size(); ix++ ) {
                SiteInventoryConfigView oldv =
                    (SiteInventoryConfigView)ov.get(ix);
                SiteInventoryConfigView newv =
                    (SiteInventoryConfigView)sivv.get(ix);
                newv.setSiteId(oldv.getSiteId());
                newv.setItemId(oldv.getItemId());
                newv.setSumOfAllParValues(oldv.getSumOfAllParValues());
                newv.setModBy(cuser);
            }

            Site sbean = factory.getSiteAPI();
            iform.setInventoryItems
                (sbean.updateInventoryConfig(sivv, SessionTool.getCategoryToCostCenterView(session,siteId, 0 )));

        } catch(Exception exc) {
            exc.printStackTrace();
            ae.add("error",new ActionError("error.systemError",
                                           "No ejb access"));
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
            SiteInventoryConfigViewVector ov =
    new SiteInventoryConfigViewVector();
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
            SiteInventoryConfigViewVector ov = sbean.lookupInventoryConfig
                (ShopTool.getCurrentSiteId(request),false,
                 SessionTool.getCategoryToCostCenterView(session,ShopTool.getCurrentSiteId(request)));

            iform.setInventoryItems(ov);
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
            List listAll = rBean.listAll(storeIds, RefCodeNames.BUS_ENTITY_TYPE_CD.BUILDING_SVC_CONTRACTOR);
            DisplayListSort.sort((BuildingServicesContractorViewVector)listAll,"short_desc");
            session.setAttribute(pListReq,listAll);
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

   public static ActionErrors updateShoppingControls
        (HttpServletRequest request,
         ActionForm form)    {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = null;
    String cuser = (String) session.getAttribute(Constants.USER_NAME);

    try {
      factory = new APIAccess();
      SiteShoppingControlForm pForm = (SiteShoppingControlForm)form;
      int siteid = pForm.getSiteId();
      Iterator it = pForm.getShoppingControlItemIds();
      ShoppingControlDataVector scdv = new ShoppingControlDataVector();

      while (it.hasNext()){
        String thisitemid = (String)it.next();
        if ( thisitemid != null ) {
          String maxqty = (String)
          pForm.getItemIdMaxAllowed(thisitemid);
          ShoppingControlData scd =
          ShoppingControlData.createValue();
          scd.setItemId(Integer.parseInt(thisitemid));
          scd.setMaxOrderQty(Integer.parseInt(maxqty));
          scd.setSiteId(siteid);
          scd.setModBy(cuser);
          scdv.add(scd);
        }
      }

      Site sbean = factory.getSiteAPI();
      ShoppingControlDataVector newvals =	sbean.updateShoppingControls(scdv, true);
      SessionTool st = new SessionTool(request);
      SiteSettingsData ssd = st.getSiteSettings(request,siteid);
      ssd.setShoppingControls(newvals);
    }
    catch (Exception e){
      e.printStackTrace();
    }

    return ae;
 }


  public static ActionErrors saveFieldValues(HttpServletRequest request,
              ActionForm form)
  throws Exception {
    ActionErrors lUpdateErrors = new ActionErrors();
    StoreSiteMgrForm pForm = (StoreSiteMgrForm)form;
    HashMap propsmap = pForm.getGeneralPropertyMap();
    HttpSession session = request.getSession();

    APIAccess factory = new APIAccess();
    Site siteB = factory.getSiteAPI();

    SiteData dd = pForm.getSiteData();
    PropertyDataVector siteFields = dd.getDataFieldProperties();
    String cuser = (String) session.getAttribute(Constants.USER_NAME);

    // iterate through the values in the form and
    // update the values in the site.
    java.util.Iterator keyset = propsmap.keySet().iterator();
    while (keyset.hasNext() ) {
      String k = (String)keyset.next();

      // Look for this id in the site fields
      for (int idx = 0; null != siteFields && idx < siteFields.size(); idx++ ) {
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


  public static ActionErrors setSiteAccountFields(HttpServletRequest request,
              ActionForm form)
  throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreSiteMgrForm pForm = (StoreSiteMgrForm)form;
    HttpSession session = request.getSession();

    APIAccess factory = new APIAccess();
    PropertyService propi = factory.getPropertyServiceAPI();
    AccountDataVector accountDV = pForm.getAccountFilter();
    if(accountDV!=null && accountDV.size()>0) {
      AccountData accountD = (AccountData) accountDV.get(0);
      BusEntityFieldsData sfd =
              propi.fetchSiteFieldsData(accountD.getBusEntity().getBusEntityId());
      session.setAttribute("Site.account.fields", sfd);
      pForm.setCountry(accountD.getPrimaryAddress().getCountryCd());
    }
    return ae;
  }

    public static void sortWorkflows(HttpServletRequest request, ActionForm form) throws Exception {

        HttpSession session = request.getSession();

        WorkflowDataVector v =
                (WorkflowDataVector) session.getAttribute
                        ("Site.workflow.found.vector");
        if (v == null)
        {
            return;
        }
        String sortField = request.getParameter("sortField");
        DisplayListSort.sort(v, sortField);
        return;
    }

    public static void setAssignedToSelectedWorkflowId(HttpServletRequest request, ActionForm form) {

      StoreSiteMgrForm pForm=(StoreSiteMgrForm)form;
      if(pForm!=null)
      {
       pForm.setSelectedWorkflowId(pForm.getAssignedWorkflowId());
      }
    }

    private static CountryData getCountry(HttpSession session, StoreSiteMgrForm pForm) {
      CountryDataVector countries = (CountryDataVector) session.getAttribute("country.vector");
      String countryStr = pForm.getCountry();
      if (countryStr != null && countries != null) {
        for (int i = 0; i < countries.size(); i++) {
          CountryData country = (CountryData) countries.get(i);
          if (country.getShortDesc().equals(countryStr)) {
            return country;
          }
        }
      }
      return CountryData.createValue();
    }

    /**
     * Error logging
     *
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
     */
    private static void error(String message, Exception e) {

        log.info("ERROR in " + className + " :: " + message);

        String errorMessage;
        StringWriter wr = new StringWriter();
        PrintWriter prW = new PrintWriter(wr);
        e.printStackTrace(prW);
        errorMessage = wr.getBuffer().toString();

    }
    
    private static int getCurrentFiscalYear(int pAccountId) {
    	int currentFiscalYear = -1;
    	try {
    		APIAccess factory = new APIAccess();
			Account accountBean = factory.getAccountAPI();
			
			 FiscalCalenderViewVector fsViewVector = accountBean.getFiscalCalCollection(pAccountId);
        	 //If any fiscal Calendars found for the selected account.
			 if(!(fsViewVector==null || fsViewVector.size()==0)) {
				 if(fsViewVector.size()==1) {
					 FiscalCalenderView fcd = (FiscalCalenderView) fsViewVector.get(0);
					 currentFiscalYear = fcd.getFiscalCalender().getFiscalYear();
				 } else { // If an account has multiple Fiscal calendars. 
					 FiscalCalenderData currentFiscalCalendar = accountBean.getCurrentFiscalCalender(pAccountId);
						if(currentFiscalCalendar!=null) {
							currentFiscalYear = currentFiscalCalendar.getFiscalYear();
						}
				 }
			 }
		} catch (Exception e) {
			log.info("Admin2BudgetLoaderMgrLogic >> An exception has occured while getting current fiscal calendar - " + e.getMessage());
		} 
    	return currentFiscalYear;
    }
    
    /**
     * Resets the selected fiscal year (Current fiscal year should be selected 
     * 		by default for every site).
     * @param request
     * @param form
     * @throws Exception
     */
    public static void resetSelectedFiscalYear(ActionForm form) throws Exception {
    	StoreSiteMgrForm pForm=(StoreSiteMgrForm)form;
    	pForm.setSelectedFiscalYear(0);
    }
    
    /**
     * Checks for Budget types and an error will be added, 
     * if budget types are different.
     * @param v List of site budgets.
     * @return ActionErrors if any.
     */
    private static ActionErrors checkForBudgetErrors(ArrayList v) {
    	 ActionErrors ae = new ActionErrors();
    	 boolean isSiteBudgetSet = false;
         boolean isAccountBudgetSet = false;
         for ( int i = 0; null != v && i < v.size(); i++ ) {
             SiteBudget sb = (SiteBudget)v.get(i);
             String cctype = sb.getCostCenterData().getCostCenterTypeCd();
             if ( RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET.equals(cctype) ) {
             	isSiteBudgetSet = true;
             }else if(RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET.equals(cctype)) {
             	isAccountBudgetSet = true;
             }
             
             if(isSiteBudgetSet && isAccountBudgetSet) {
             	String errorMsg = "Cannot assign both site and account budget types to a site.";
             	ae.add("error",new ActionError("error.simpleGenericError",errorMsg));
             }
         }
         return ae;
    }
    
    public static ActionErrors searchCorporateSchedules(HttpServletRequest request,
    		ActionForm form)
    throws Exception {
    	ActionErrors ae = new ActionErrors();
    	HttpSession session = request.getSession();
    	APIAccess factory = new APIAccess();
    	Schedule scheduleEjb = factory.getScheduleAPI();
    	StoreSiteMgrForm pForm = (StoreSiteMgrForm)form;
    	String searchField = pForm.getConfigSearchField();
    	String searchType = pForm.getConfigSearchType();
    	boolean beginsWithFl = (searchType.equals("nameBegins"))? true:false;
    	CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        int storeId = appUser.getUserStore().getStoreId();
    	
    	DeliveryScheduleViewVector dsVwV =
    		scheduleEjb.getCorporateSchedules(storeId, Integer.parseInt(pForm.getId()), searchField, beginsWithFl);

    	session.setAttribute("Related.site.corpschedules.vector", dsVwV);
    	return ae;
    }

}
