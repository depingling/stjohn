
package com.cleanwise.view.logic;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Group;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.service.api.value.AccountOrderPipeline;
import com.cleanwise.service.api.value.AccountSearchResultViewVector;
import com.cleanwise.service.api.value.AccountSettingsData;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.BillToData;
import com.cleanwise.service.api.value.BusEntityAssocData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityParameterData;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.GroupDataVector;
import com.cleanwise.service.api.value.GroupSearchCriteriaView;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderRoutingData;
import com.cleanwise.service.api.value.OrderRoutingDataVector;
import com.cleanwise.service.api.value.OrderRoutingDescView;
import com.cleanwise.service.api.value.OrderRoutingDescViewVector;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.SiteDeliveryScheduleViewVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.StoreDataVector;
import com.cleanwise.view.forms.AccountBillToForm;
import com.cleanwise.view.forms.AccountMgrDetailForm;
import com.cleanwise.view.forms.AccountMgrSearchForm;
import com.cleanwise.view.forms.StoreAccountMgrForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.CurrencyFormat;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.cleanwise.view.utils.StringUtils;

/**
 *  <code>AccountMgrLogic</code> implements the logic needed to manipulate
 *  purchasing account records.
 *
 *@author     tbesser
 *@created    August 23, 2001
 */
public class AccountMgrLogic {
    private static final Logger log = Logger.getLogger(AccountMgrLogic.class);
    private static final String ACCOUNT_STORE_MAP_CACHE = "account.store.map.cache";
    
    /**
     *Returns the store data for the account that is currently being managed.
     *For non system_administrator users this is simply the store that they are currently
     *managed.  For system_administrator this is the store that the account is tied
     *to.  This information is cached in the session.
     *@param pSession the user who is making the request session
     *@param pAccountId the account id that we want the store for
     */
    public static StoreData getStoreForManagedAccount(HttpSession pSession, int pAccountId)
    throws APIServiceAccessException, RemoteException{
        CleanwiseUser appUser = ShopTool.getCurrentUser(pSession);
        if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            return appUser.getUserStore();
        }
        StoreData theStore = null;
        Integer key = new Integer(pAccountId);
        Map storeMap = (Map) pSession.getAttribute(ACCOUNT_STORE_MAP_CACHE);
        if(storeMap == null){
            storeMap = new HashMap();
            pSession.setAttribute(ACCOUNT_STORE_MAP_CACHE,storeMap);
        }else{
            theStore = (StoreData) storeMap.get(key);
        }
        if(theStore == null){
            APIAccess factory = (APIAccess) pSession.getAttribute(Constants.APIACCESS);
            BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
            IdVector idv = new IdVector();
            idv.add(new Integer(pAccountId));
            crit.setAccountBusEntityIds(idv);
            StoreDataVector stores = factory.getStoreAPI().getStoresByCriteria(crit);
            int size = stores.size();
            if(size == 0){
                theStore = null;
            }else if(size == 1){
                theStore = (StoreData) stores.get(0);
            }else{
                throw new RuntimeException("Multiple stores for account id: "+pAccountId);
            }
            storeMap.put(key, theStore);
        }
        return theStore;
    }
    
    /**
     *updates the caching between stores and accounts
     */
    private static void resetStoreAccountCache(HttpSession pSession, int pAccountId, int pStoreId){
        if(pAccountId == 0){
            return;
        }
        Map storeMap = (HashMap) pSession.getAttribute(ACCOUNT_STORE_MAP_CACHE);
        if(storeMap != null){
            Integer key = new Integer(pAccountId);
            storeMap.remove(key);
        }
    }
    
    /**
     *  <code>getAll</code> accounts, note that the Account list returned will
     *  be limited to a certain amount of records. It is up to the jsp page to
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
        Account accountBean = factory.getAccountAPI();
        AccountDataVector dv;
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
            crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
            crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
            dv = accountBean.getAccountsByCriteria(crit);
        }else{
            dv = accountBean.getAllAccounts(0, Account.ORDER_BY_NAME);
        }
        
        session.setAttribute("Account.found.vector", dv);
        session.setAttribute("Account.found.total",
                String.valueOf(dv.size()));
    }
    
    
    /**
     *  Describe <code>getDetail</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void getDetail(HttpServletRequest request,
            ActionForm form)
            throws Exception {
        
        HttpSession session = request.getSession();
        AccountMgrDetailForm sForm =
                (AccountMgrDetailForm) session.getAttribute("ACCOUNT_DETAIL_FORM");
        
        init(request, form);
        
        if (sForm == null) {
            sForm = new AccountMgrDetailForm();
            session.setAttribute("ACCOUNT_DETAIL_FORM",
                    sForm);
        }
        
        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        Store storei = factory.getStoreAPI();
        
        
        String fieldValue = request.getParameter("accountId");
        if ( null == fieldValue ) {
            fieldValue = request.getParameter("searchField");
        }
        if (null == fieldValue) {
            fieldValue = "0";
        }
        
        int accountid = Integer.parseInt(fieldValue);
        AccountData dd = accountBean.getAccount(accountid, 0);
        sForm.setAccountData(dd);
        if (null != dd) {
            // Set the form values.
            
            sForm.setRuntimeDisplayOrderItemActionTypes((String[]) dd.getRuntimeDisplayOrderItemActionTypes().toArray(new String[0]));
            
            BusEntityData bed = dd.getBusEntity();
            sForm.setName(bed.getShortDesc());
            sForm.setStatusCd(bed.getBusEntityStatusCd());
            sForm.setId(String.valueOf(bed.getBusEntityId()));
            sForm.setTypeCd(bed.getBusEntityTypeCd());
            sForm.setAccountNumber(bed.getErpNum());
            
            BusEntityAssocData storeassoc = dd.getStoreAssoc();
            int storeid = storeassoc.getBusEntity2Id();
            sForm.setStoreId(String.valueOf(storeid));
            StoreData storedata = storei.getStore(storeid);
            sForm.setStoreName(storedata.getBusEntity().getShortDesc());
            
            PropertyData acctType = dd.getAccountType();
            sForm.setTypeDesc(acctType.getValue());
            
            PropertyData orderMinimum = dd.getOrderMinimum();
            BigDecimal minvalue;
            
            try {
                minvalue = CurrencyFormat.parse(orderMinimum.getValue());
            } catch (ParseException pe) {
                // this should only happen if bad value in db
                minvalue = new BigDecimal(0);
            }
            sForm.setOrderMinimum(CurrencyFormat.format(minvalue));
            
            PropertyData creditLimit = dd.getCreditLimit();
            BigDecimal credvalue;
            try {
                credvalue = CurrencyFormat.parse(creditLimit.getValue());
            } catch (ParseException pe) {
                // this should only happen if bad value in db
                credvalue = new BigDecimal(0);
            }
            sForm.setCreditLimit(CurrencyFormat.format(credvalue));
            
            PropertyData creditRating = dd.getCreditRating();
            sForm.setCreditRating(creditRating.getValue());
            
            PropertyData crcShop = dd.getCrcShop();
            String value = crcShop.getValue();
            if (value != null && value.length() > 0 && "T".equalsIgnoreCase(crcShop.getValue().substring(0, 1))) {
                sForm.setCrcShop(true);
            } else {
                sForm.setCrcShop(false);
            }
            
            PhoneData phone = dd.getPrimaryPhone();
            sForm.setPhone(phone.getPhoneNum());
            
            PhoneData orderPhone = dd.getOrderPhone();
            sForm.setOrderPhone(orderPhone.getPhoneNum());
            
            PhoneData fax = dd.getPrimaryFax();
            sForm.setFax(fax.getPhoneNum());
            
            PhoneData orderFax = dd.getOrderFax();
            sForm.setOrderFax(orderFax.getPhoneNum());
            
            PhoneData poConfirmFax = dd.getFaxBackConfirm();
            sForm.setFaxBackConfirm(poConfirmFax.getPhoneNum());
            
            PropertyData comments = dd.getComments();
            sForm.setComments(comments.getValue());
            
            PropertyData orderGuideNote = dd.getOrderGuideNote();
            if ( null != orderGuideNote ) {
                sForm.setOrderGuideNote(orderGuideNote.getValue());
            } else {
                sForm.setOrderGuideNote("");
            }
            
            PropertyData skuTag = dd.getSkuTag();
            if ( null != skuTag ) {
                sForm.setSkuTag(skuTag.getValue());
            } else {
                sForm.setSkuTag("");
            }
            
            sForm.setCustomerSystemApprovalCd(dd.getCustomerSystemApprovalCd());
            sForm.setCustomerRequestPoAllowed(dd.isCustomerRequestPoAllowed());
            sForm.setTaxableIndicator(dd.isTaxableIndicator());
            
            EmailData email = dd.getPrimaryEmail();
            sForm.setEmailAddress(email.getEmailAddress());
            
            sForm.setDataFieldProperties(dd.getDataFieldProperties());
            
            AddressData address = dd.getPrimaryAddress();
            sForm.setName1(address.getName1());
            sForm.setName2(address.getName2());
            sForm.setPostalCode(address.getPostalCode());
            sForm.setStateOrProv(address.getStateProvinceCd());
            sForm.setCountry(address.getCountryCd());
            sForm.setCity(address.getCity());
            sForm.setStreetAddr1(address.getAddress1());
            sForm.setStreetAddr2(address.getAddress2());
            sForm.setStreetAddr3(address.getAddress3());
            
            AddressData billingAddress = dd.getBillingAddress();
            sForm.setBillingPostalCode(billingAddress.getPostalCode());
            sForm.setBillingState(billingAddress.getStateProvinceCd());
            sForm.setBillingCountry(billingAddress.getCountryCd());
            sForm.setBillingCity(billingAddress.getCity());
            sForm.setBillingAddress1(billingAddress.getAddress1());
            sForm.setBillingAddress2(billingAddress.getAddress2());
            sForm.setBillingAddress3(billingAddress.getAddress3());
            
            sForm.setAuthorizedForResale(dd.isAuthorizedForResale());
            sForm.setReSaleAccountErpNumber(dd.getResaleAccountErpNumber().getValue());
            String ediShipToPrefix = dd.getEdiShipToPrefix();
            if(ediShipToPrefix==null || ediShipToPrefix.trim().length()==0) {
                ediShipToPrefix = ""+accountid;
            }
            sForm.setEdiShipToPrefix(ediShipToPrefix);
            
            if(dd.getTargetMargin() == null){
                sForm.setTargetMarginStr("0");
            }else{
                sForm.setTargetMarginStr(dd.getTargetMargin().toString());
            }
            
            session.setAttribute("Account.id", sForm.getId());
            session.setAttribute("Account.name", sForm.getName());
            session.setAttribute("Store.id", sForm.getStoreId());
            session.setAttribute("Store.name", sForm.getStoreName());
        } else {
            session.setAttribute("Account.id", "0");
            session.setAttribute("Account.name", "-");
            session.setAttribute("Store.id", "0");
            session.setAttribute("Store.name", "--");
            
        }
        
        if(!Utility.isSet(dd.getFreightChargeType())){
            sForm.setFreightChargeType("PC");
        }else{
            sForm.setFreightChargeType(dd.getFreightChargeType());
        }
        sForm.setPurchaseOrderAccountName(dd.getPurchaseOrderAccountName());
        sForm.setBudgetTypeCd(dd.getBudgetTypeCd());
        
        try{
            // Look for an order pipeline.
            initOrderPipeline(factory, accountid, sForm);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        
        
        
        
        boolean showSchedDel = false;
        String showSchedDelS =dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_SCHED_DELIVERY);
        sForm.setMakeShipToBillTo(dd.isMakeShipToBillTo());
        
        if (showSchedDelS != null && showSchedDelS.length() > 0 &&
                "T".equalsIgnoreCase(showSchedDelS.substring(0, 1))) {
            showSchedDel = true;
        }
        sForm.setShowScheduledDelivery(showSchedDel);
        
        sForm.setRushOrderCharge(
                dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.RUSH_ORDER_CHARGE)
                );
        
        if (showSchedDelS != null && showSchedDelS.length() > 0 &&
                "T".equalsIgnoreCase(showSchedDelS.substring(0, 1))) {
            showSchedDel = true;
        }
        sForm.setShowScheduledDelivery(showSchedDel);
        
        
        boolean allowOrderConsolidation = false;
        String allowOrderConsolidationS =
                dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_CONSOLIDATION);
        if (allowOrderConsolidationS != null && allowOrderConsolidationS.length() > 0 &&
                "T".equalsIgnoreCase(allowOrderConsolidationS.substring(0, 1))) {
            allowOrderConsolidation = true;
        }
        sForm.setAllowOrderConsolidation(allowOrderConsolidation);
        
        boolean showDistSkuNum = false;
        String showDistSkuNumS =
                dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_DIST_SKU_NUM);
        if (showDistSkuNumS != null && showDistSkuNumS.length() > 0 &&
                "T".equalsIgnoreCase(showDistSkuNumS.substring(0, 1))) {
            showDistSkuNum = true;
        }
        sForm.setShowDistSkuNum(showDistSkuNum);
        
        String scheduleCutoffDaysS =
                dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SCHEDULE_CUTOFF_DAYS);
        if(scheduleCutoffDaysS==null) scheduleCutoffDaysS = "";
        sForm.setScheduleCutoffDays(scheduleCutoffDaysS);
        
        BusEntityParameterData rebatePersentBEPD =
                dd.getAccountParameterActual("Rebate Persent");
        String rebatePersentS = "";
        String rebateEffDateS = "";
        if(rebatePersentBEPD!=null){
            rebatePersentS = rebatePersentBEPD.getValue();
            if(Utility.isSet(rebatePersentS)) {
                Date rebateEffDate = rebatePersentBEPD.getEffDate();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                rebateEffDateS = sdf.format(rebateEffDate);
            }
        }
        
        sForm.setRebatePersent(rebatePersentS);
        sForm.setRebateEffDate(rebateEffDateS);
        
        
        
        ArrayList orderManagerEmailV = dd.getOrderManagerEmails();
        String orderManagerEmailS = "";
        if(orderManagerEmailV!=null) {
            for(int ii=0;  ii<orderManagerEmailV.size(); ii++) {
                String eMail = (String) orderManagerEmailV.get(ii);
                if(eMail!=null) {
                    eMail = eMail.trim();
                    if(eMail.length()>0) {
                        if(ii>0) orderManagerEmailS += ",";
                        orderManagerEmailS += eMail;
                    }
                }
            }
        }
        sForm.setOrderManagerEmails(orderManagerEmailS);
        //Jd begin
        //if ("jd".equals(ClwCustomizer.getStoreDir())) {
        //    getJdThresholdValues(request, sForm, dd);
        //}
        //Jd end
    }
    
    //Jd begin
        
    //Jd end
    
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
        HttpSession session = request.getSession();
        
        // Cache the lists needed for Accounts.
        APIAccess factory = new APIAccess();
        ListService lsvc = factory.getListServiceAPI();
        Group groupEjb = factory.getGroupAPI();
        
        // Set up the account status list.
        if (session.getAttribute("Account.status.vector") == null) {
            RefCdDataVector statusv =
                    lsvc.getRefCodesCollection("BUS_ENTITY_STATUS_CD");
            session.setAttribute("Account.status.vector", statusv);
        }
        
        if (session.getAttribute("countries.vector") == null) {
            RefCdDataVector countriesv =
                    lsvc.getRefCodesCollection("ADDRESS_COUNTRY_CD");
            session.setAttribute("countries.vector", countriesv);
        }
        
        if (session.getAttribute("Account.type.vector") == null) {
            RefCdDataVector typesv =
                    lsvc.getRefCodesCollection("ACCOUNT_TYPE_CD");
            session.setAttribute("Account.type.vector", typesv);
        }
        
        if (session.getAttribute("CUSTOMER_SYSTEM_APPROVAL_CD") == null) {
            RefCdDataVector typesv =
                    lsvc.getRefCodesCollection("CUSTOMER_SYSTEM_APPROVAL_CD");
            session.setAttribute("CUSTOMER_SYSTEM_APPROVAL_CD", typesv);
        }
        
        
        if (session.getAttribute("ORDER_ITEM_DETAIL_ACTION_CD") == null) {
            RefCdDataVector typesv =
                    lsvc.getRefCodesCollection("ORDER_ITEM_DETAIL_ACTION_CD");
            session.setAttribute("ORDER_ITEM_DETAIL_ACTION_CD", typesv);
        }
        
        if (session.getAttribute("BUDGET_ACCRUAL_TYPE_CD") == null) {
            RefCdDataVector typesv =
                    lsvc.getRefCodesCollection("BUDGET_ACCRUAL_TYPE_CD");
            session.setAttribute("BUDGET_ACCRUAL_TYPE_CD", typesv);
        }
        
        
        
        // Remove the previous data cache.
        session.removeAttribute("Workflow.found.vector");
        GroupSearchCriteriaView grSearchCrVw = 
	    GroupSearchCriteriaView.createValue();
        grSearchCrVw.setGroupType(RefCodeNames.GROUP_TYPE_CD.ACCOUNT);
        grSearchCrVw.setGroupName("");
	CleanwiseUser appUser = (CleanwiseUser) 
	    session.getAttribute(Constants.APP_USER);
	grSearchCrVw.setStoreId(appUser.getUserStore().getStoreId());
        GroupDataVector groups =
                groupEjb.getGroups(grSearchCrVw,Group.NAME_BEGINS_WITH
				   ,Group.ORDER_BY_NAME);
        if(form instanceof AccountMgrSearchForm) {
            AccountMgrSearchForm pForm = (AccountMgrSearchForm) form;
            pForm.setAccountGroups(groups);
        } else if(form instanceof StoreAccountMgrForm) {
            StoreAccountMgrForm pForm = (StoreAccountMgrForm) form;
            pForm.setAccountGroups(groups);
        }
        
        return;
    }
    
    

    public static ActionErrors accountSearch(HttpServletRequest request,
  		  								   ActionForm form) throws Exception{
	  	ActionErrors ae = new ActionErrors();
	  	HttpSession session = request.getSession();
	  	APIAccess factory = new APIAccess();
	  	Account accountBean = factory.getAccountAPI();
	  	AccountSearchResultViewVector acntSrchRsltVctr = null;
	  	
	  	AccountMgrSearchForm sForm = (AccountMgrSearchForm) form;
	  	String fieldValue = sForm.getSearchField();
	  	String fieldSearchType = sForm.getSearchType();
	  	String searchGroupId = sForm.getSearchGroupId();
	  	boolean showInactive = true;
	  	int id =0;
	  	CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	  	String storeStr = null;
	  	if (!appUser.isaSystemAdmin()) {
	  		IdVector stores = appUser.getUserStoreAsIdVector();
	  		storeStr = stores.toCommaString(stores);
	  	}
	
	  	try{
	  		if(request.getAttribute("viewAll") != null){
	  			acntSrchRsltVctr = accountBean.search(storeStr, "", "", "", showInactive);
	  		}else{
	  			acntSrchRsltVctr = accountBean.search(storeStr, fieldValue, fieldSearchType, searchGroupId, showInactive);
	  		}
	  		
	  	}catch(Exception e){
	  		ActionErrors clwError = StringUtils.getUiErrorMess(e);
	  		return clwError;
	  	}
	  	session.setAttribute("Account.search.result", acntSrchRsltVctr);
	  	return ae;
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
        
        HttpSession session = request.getSession();
        AccountMgrSearchForm sForm = (AccountMgrSearchForm) form;
        APIAccess factory = new APIAccess();
        
        // Reset the session variables.
        AccountDataVector dv = new AccountDataVector();
        session.setAttribute("Account.found.vector", dv);
        session.setAttribute("Account.found.total", "0");
        
        String fieldValue = sForm.getSearchField();
        String fieldSearchType = sForm.getSearchType();
        String searchGroupId = sForm.getSearchGroupId();
        dv = search(request, fieldValue,fieldSearchType,searchGroupId, true);
        
        
        session.setAttribute("Account.found.vector", dv);
        session.setAttribute("Account.found.total",
                String.valueOf(dv.size()));
        
    }
    
    
    public static void storeSearch(HttpServletRequest request,
            ActionForm form)
            throws Exception {
        
        HttpSession session = request.getSession();
        StoreAccountMgrForm sForm = (StoreAccountMgrForm) form;
        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        
        // Reset the session variables.
        AccountDataVector dv = new AccountDataVector();
        session.setAttribute("Account.found.vector", dv);
        session.setAttribute("Account.found.total", "0");
        
        String fieldValue = sForm.getSearchField();
        String fieldSearchType = sForm.getSearchType();
        String searchGroupId = sForm.getSearchGroupId();
        dv = search(request, fieldValue,fieldSearchType,
                searchGroupId,
                sForm.getShowInactiveFl() );
        
        session.setAttribute("Account.found.vector", dv);
        session.setAttribute("Account.found.total",
                String.valueOf(dv.size()));
        
    }
    
    static public AccountDataVector search(HttpServletRequest request,
            String fieldValue,
            String fieldSearchType,
            String searchGroupId )
            throws Exception{
        return  search( request,fieldValue,fieldSearchType,
                searchGroupId, true );
    }
    
    /**
     *Returns an account data vector based off the specified search criteria
     */
    static public AccountDataVector search(HttpServletRequest request,
            String fieldValue,
            String fieldSearchType,
            String searchGroupId,
            boolean pShowInactiveFlag)
            throws Exception{
        return search(request, fieldValue, fieldSearchType, searchGroupId, pShowInactiveFlag, false);
    }

   static public AccountDataVector search(HttpServletRequest request,
                String fieldValue,
                String fieldSearchType,
                String searchGroupId,
                boolean pShowInactiveFlag,
                boolean pForcedUsingStoreIds)
                throws Exception{
        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Account acctBean = factory.getAccountAPI();
        
        BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
        
        boolean emptyCritFl = true;
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        
        if(! appUser.isaSystemAdmin() || pForcedUsingStoreIds == true) {
            emptyCritFl = false;
            crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
        }
        
        if(fieldValue!=null && fieldValue.trim().length()>0) {
            emptyCritFl = false;
            fieldValue = fieldValue.trim();
            if ("id".equals(fieldSearchType)) {
                crit.setSearchId(fieldValue);
            } else {
                if ("nameBegins".equals(fieldSearchType)) {
                    crit.setSearchName(fieldValue);
                    crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
                } else {
                    crit.setSearchName(fieldValue);
                    crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
                }
            }
        }
        
        
        if(searchGroupId !=null && searchGroupId.trim().length()>0 &&
                Integer.parseInt(searchGroupId) > 0 )             {
            emptyCritFl = false;
            crit.setSearchGroupId(searchGroupId.trim());
        }
	
	crit.setSearchForInactive(pShowInactiveFlag);


        AccountDataVector dv;
        if(emptyCritFl) {
            dv = acctBean.getAllAccounts(0,Account.ORDER_BY_NAME);
        } else {
            crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
            dv = acctBean.getAccountsByCriteria(crit);
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
        String sortField = request.getParameter("sortField");
        if (sortField.startsWith("sl_")) {
            SiteDeliveryScheduleViewVector sl =
                    (SiteDeliveryScheduleViewVector) session.getAttribute("account.delivery.schedules");
            if (sl == null) {
                return;
            }
            if (sortField.equals("sl_site_name")) {
                sl.sort("SiteName");
            } else if (sortField.equals("sl_site_status_cd")) {
                sl.sort("SiteStatusCd");
            } else if (sortField.equals("sl_curr_sched")) {
                sl.sort("SiteScheduleType");
            } else if (sortField.equals("sl_city")) {
                sl.sort("City");
            } else if (sortField.equals("sl_state")) {
                sl.sort("State");
            } else if (sortField.equals("sl_postal_code")) {
                sl.sort("PostalCode");
            } else if (sortField.equals("sl_county")) {
                sl.sort("County");
            } else {
                sl.sort("SiteId");
            }
        } else {
            AccountDataVector accounts =
                    (AccountDataVector) session.getAttribute("Account.found.vector");
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
        }
    }
    
    
    /**
     *  Describe <code>addAccount</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void addAccount(HttpServletRequest request,
            ActionForm form)
            throws Exception {
        
        HttpSession session = request.getSession();
        AccountMgrDetailForm sForm = new AccountMgrDetailForm();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if(appUser != null && !RefCodeNames.STORE_TYPE_CD.MLA.equals(appUser.getUserStore().getStoreType().getValue())){
	        sForm.setRuntimeDisplayOrderItemActionTypes(new String[]{
	            RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.SYSTEM_ACCEPTED,
	            RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_SHIPPED,
	            RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_INVOICED,
	            RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CANCELED
	        });
        }else{
        	sForm.setRuntimeDisplayOrderItemActionTypes(new String[]{
    	            RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.SYSTEM_ACCEPTED,
    	            RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CUST_SHIPPED,
    	            RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CUST_INVOICED,
    	            RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CANCELED
    	        });
        }
        session.setAttribute("ACCOUNT_DETAIL_FORM", sForm);
    }
    
    
    /**
     *  Describe <code>updateAccount</code> method here.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@return                an <code>ActionErrors</code> value
     *@exception  Exception  if an error occurs
     */
    public static ActionErrors updateAccount(HttpServletRequest request,
            ActionForm form)
            throws Exception {
        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        
        AccountMgrDetailForm sForm = (AccountMgrDetailForm) form;
        BigDecimal targetMargin = null;
        if (sForm != null) {
            // Verify the form information submitted.
            if(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
                if (sForm.getStoreId() == null || sForm.getStoreId().length() == 0) {
                    lUpdateErrors.add("storeId",
                            new ActionError("variable.empty.error",
                            "Store Id"));
                }
            }
            if (sForm.getName() == null || sForm.getName().length() == 0) {
                lUpdateErrors.add("name",
                        new ActionError("variable.empty.error",
                        "Name"));
            }
            if (sForm.getTypeDesc() == null || sForm.getTypeDesc().length() == 0) {
                lUpdateErrors.add("typeDesc",
                        new ActionError("variable.empty.error",
                        "Account Type"));
            }
            if (sForm.getStatusCd() == null || sForm.getStatusCd().length() == 0) {
                lUpdateErrors.add("statusCd",
                        new ActionError("variable.empty.error",
                        "Status"));
            }
            if (sForm.getAccountNumber() == null || sForm.getAccountNumber().length() == 0) {
                lUpdateErrors.add("accountNumber",
                        new ActionError("variable.empty.error",
                        "Account Number"));
            }
            if (sForm.getName1() == null || sForm.getName1().length() == 0) {
                lUpdateErrors.add("name1",
                        new ActionError("variable.empty.error",
                        "First Name"));
            }
            if (sForm.getName2() == null || sForm.getName2().length() == 0) {
                lUpdateErrors.add("name2",
                        new ActionError("variable.empty.error",
                        "Last Name"));
            }
            if (sForm.getStreetAddr1() == null || sForm.getStreetAddr1().length() == 0) {
                lUpdateErrors.add("streetAddr1",
                        new ActionError("variable.empty.error",
                        "Primary Contact Street Address 1"));
            }
            if (sForm.getCity() == null || sForm.getCity().length() == 0) {
                lUpdateErrors.add("city",
                        new ActionError("variable.empty.error",
                        "Primary Contact City"));
            }
            if (sForm.getStateOrProv() == null || sForm.getStateOrProv().length() == 0) {
                lUpdateErrors.add("stateOrProv",
                        new ActionError("variable.empty.error",
                        "Primary Contact State/Province"));
            }
            if (sForm.getCountry() == null || sForm.getCountry().length() == 0) {
                lUpdateErrors.add("country",
                        new ActionError("variable.empty.error",
                        "Primary Contact Country"));
            }
            if (sForm.getPostalCode() == null || sForm.getPostalCode().length() == 0) {
                lUpdateErrors.add("postalCode",
                        new ActionError("variable.empty.error",
                        "Primary Contact Zip/Postal Code"));
            }
            if (sForm.getPhone() == null || sForm.getPhone().length() == 0) {
                lUpdateErrors.add("phone",
                        new ActionError("variable.empty.error",
                        "Phone"));
            }
            if (sForm.getOrderPhone() == null || sForm.getOrderPhone().length() == 0) {
                lUpdateErrors.add("orderPhone",
                        new ActionError("variable.empty.error",
                        "OrderPhone"));
            }
            if (sForm.getFax() == null || sForm.getFax().length() == 0) {
                lUpdateErrors.add("fax",
                        new ActionError("variable.empty.error",
                        "Fax"));
            }
            if (sForm.getOrderFax() == null || sForm.getOrderFax().length() == 0) {
                lUpdateErrors.add("orderFax",
                        new ActionError("variable.empty.error",
                        "OrderFax"));
            }
            if (sForm.getEmailAddress() == null || sForm.getEmailAddress().length() == 0) {
                lUpdateErrors.add("emailAddress",
                        new ActionError("variable.empty.error",
                        "Email"));
            }
            if (sForm.getOrderMinimum() != null && sForm.getOrderMinimum().length() != 0) {
                try {
                    BigDecimal minvalue =
                            CurrencyFormat.parse(sForm.getOrderMinimum());
                } catch (ParseException pe) {
                    lUpdateErrors.add("orderMinimum",
                            new ActionError("error.invalidAmount",
                            "Order Minimum"));
                }
            }
            if (sForm.getCreditLimit() != null && sForm.getCreditLimit().length() != 0) {
                try {
                    BigDecimal minvalue =
                            CurrencyFormat.parse(sForm.getCreditLimit());
                } catch (ParseException pe) {
                    lUpdateErrors.add("CreditLimit",
                            new ActionError("error.invalidAmount",
                            "Order Minimum"));
                }
            }
            if (sForm.getBillingAddress1() == null || sForm.getBillingAddress1().length() == 0) {
                lUpdateErrors.add("billingAddress1",
                        new ActionError("variable.empty.error",
                        "Billing Street Address 1"));
            }
            if (sForm.getBillingCity() == null || sForm.getBillingCity().length() == 0) {
                lUpdateErrors.add("billingCity",
                        new ActionError("variable.empty.error",
                        "Billing City"));
            }
            if (sForm.getBillingState() == null || sForm.getBillingState().length() == 0) {
                lUpdateErrors.add("billingState",
                        new ActionError("variable.empty.error",
                        "Billing State/Province"));
            }
            if (sForm.getBillingCountry() == null || sForm.getBillingCountry().length() == 0) {
                lUpdateErrors.add("billingCountry",
                        new ActionError("variable.empty.error",
                        "Billing Country"));
            }
            if (sForm.getBillingPostalCode() == null || sForm.getBillingPostalCode().length() == 0) {
                lUpdateErrors.add("billingPostalCode",
                        new ActionError("variable.empty.error",
                        "Billing Zip/Postal Code"));
            }
            if (sForm.isAuthorizedForResale() && !Utility.isSet(sForm.getReSaleAccountErpNumber())) {
                lUpdateErrors.add("reSaleAccountErpNumber",
                        new ActionError("variable.empty.error",
                        "reSaleAccountErpNumber"));
            }
            try{
                if(Utility.isSet(sForm.getTargetMarginStr())){
                    targetMargin = new BigDecimal(sForm.getTargetMarginStr());
                }else{
                    targetMargin = null;
                }
            }catch(Exception e){
                lUpdateErrors.add("targetMarginStr",new ActionError("error.invalidAmount","Target Margin"));
            }
        }
        if (lUpdateErrors.size() > 0) {
            // Report the errors to allow for edits.
            return lUpdateErrors;
        }
        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        Store storeBean = factory.getStoreAPI();
        
        int accountid = 0;
        if (!sForm.getId().equals("")) {
            accountid = Integer.parseInt(sForm.getId());
        }
        
        
        
        // Get the current information for this Account.
        AccountData dd;
        BusEntityData bed;
        AddressData address;
        AddressData billingAddress;
        EmailData email;
        PhoneData phone;
        PhoneData orderPhone;
        PhoneData fax;
        PhoneData orderFax;
        PhoneData poConfirmFax;
        PropertyData acctType;
        PropertyData orderMinimum;
        PropertyData creditLimit;
        PropertyData creditRating;
        PropertyData crcShop;
        PropertyData comments, orderGuideNote, skuTag;
        PropertyData reSaleAccountErpNumber;
        
        int storeid = 0;
        if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            storeid = appUser.getUserStore().getStoreId();
        }else{
            if (!sForm.getStoreId().equals("")) {
                storeid = Integer.parseInt(sForm.getStoreId());
            }
        }
        resetStoreAccountCache(session,accountid,storeid);
        
        if (accountid > 0) {
            dd = accountBean.getAccount(accountid, storeid);
            sForm.setAccountData(dd);
        } else {
            dd = AccountData.createValue();
        }
        
        dd.getRuntimeDisplayOrderItemActionTypes().clear();
        for(int i=0;i<sForm.getRuntimeDisplayOrderItemActionTypes().length;i++){
            if(Utility.isSet(sForm.getRuntimeDisplayOrderItemActionTypes()[i])){
                dd.getRuntimeDisplayOrderItemActionTypes().add(sForm.getRuntimeDisplayOrderItemActionTypes()[i]);
            }
        }
        
        bed = dd.getBusEntity();
        address = dd.getPrimaryAddress();
        billingAddress = dd.getBillingAddress();
        phone = dd.getPrimaryPhone();
        orderPhone = dd.getOrderPhone();
        fax = dd.getPrimaryFax();
        orderFax = dd.getOrderFax();
        poConfirmFax = dd.getFaxBackConfirm();
        email = dd.getPrimaryEmail();
        acctType = dd.getAccountType();
        orderMinimum = dd.getOrderMinimum();
        reSaleAccountErpNumber = dd.getResaleAccountErpNumber();
        
        creditLimit = dd.getCreditLimit();
        creditRating = dd.getCreditRating();
        crcShop = dd.getCrcShop();
        comments = dd.getComments();
        orderGuideNote = dd.getOrderGuideNote();
        skuTag = dd.getSkuTag();
        
        // XXX, values to be determined.
        bed.setWorkflowRoleCd("UNKNOWN");
        SessionTool stl = new SessionTool(request);
        String cuser =         stl.getLoginName();

        // Now update with the data from the form.
        dd.setDataFieldProperties(sForm.getDataFieldProperties());
        dd.setAuthorizedForResale(sForm.isAuthorizedForResale());
        dd.setEdiShipToPrefix(sForm.getEdiShipToPrefix());
        dd.setTargetMargin(targetMargin);
        bed.setShortDesc(sForm.getName());
        bed.setLongDesc(sForm.getName());
        bed.setBusEntityStatusCd(sForm.getStatusCd());
        bed.setErpNum(sForm.getAccountNumber());
        
        dd.setCustomerSystemApprovalCd(sForm.getCustomerSystemApprovalCd());
        dd.setCustomerRequestPoAllowed(sForm.isCustomerRequestPoAllowed());
        dd.setTaxableIndicator(sForm.isTaxableIndicator());
        
        bed.setLocaleCd("unk");
        bed.setModBy(cuser);
        
        acctType.setValue(sForm.getTypeDesc());
        
        // In the property, save as a number rather than a currency
        BigDecimal minvalue = CurrencyFormat.parse(sForm.getOrderMinimum());
        orderMinimum.setValue(CurrencyFormat.formatAsNumber(minvalue));
        sForm.setOrderMinimum(CurrencyFormat.format(minvalue));
        
        BigDecimal credvalue = CurrencyFormat.parse(sForm.getCreditLimit());
        creditLimit.setValue(CurrencyFormat.formatAsNumber(credvalue));
        sForm.setCreditLimit(CurrencyFormat.format(credvalue));
        
        creditRating.setShortDesc("Credit Rating");
        creditRating.setValue(sForm.getCreditRating());
        
        crcShop.setShortDesc("Crc Shop");
        if (sForm.getCrcShop()) {
            crcShop.setValue("true");
        } else {
            crcShop.setValue("false");
        }
        comments.setShortDesc("Comments");
        comments.setValue(sForm.getComments());
        
        skuTag.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SKU_TAG);
        skuTag.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SKU_TAG);
        skuTag.setValue(sForm.getSkuTag());
        
        orderGuideNote.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.ORDER_GUIDE_NOTE);
        orderGuideNote.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ORDER_GUIDE_NOTE);
        orderGuideNote.setValue(sForm.getOrderGuideNote());
        
        reSaleAccountErpNumber.setShortDesc("ReSaleAccountErpNumber");
        reSaleAccountErpNumber.setValue(sForm.getReSaleAccountErpNumber());
        
        phone.setShortDesc("Primary Phone");
        phone.setPhoneNum(sForm.getPhone());
        
        orderPhone.setShortDesc("Order Phone");
        orderPhone.setPhoneNum(sForm.getOrderPhone());
        
        fax.setShortDesc("Primary Fax");
        fax.setPhoneNum(sForm.getFax());
        
        orderFax.setShortDesc("Order Fax");
        orderFax.setPhoneNum(sForm.getOrderFax());
        
        poConfirmFax.setShortDesc("Fax Back Confirm");
        poConfirmFax.setPhoneNum(sForm.getFaxBackConfirm());
        
        email.setShortDesc(sForm.getName1() + " " + sForm.getName2());
        email.setEmailAddress(sForm.getEmailAddress());
        
        address.setName1(sForm.getName1());
        address.setName2(sForm.getName2());
        address.setAddress1(sForm.getStreetAddr1());
        address.setAddress2(sForm.getStreetAddr2());
        address.setAddress3(sForm.getStreetAddr3());
        address.setCity(sForm.getCity());
        address.setStateProvinceCd(sForm.getStateOrProv());
        address.setPostalCode(sForm.getPostalCode());
        address.setCountryCd(sForm.getCountry());
        
        billingAddress.setAddress1(sForm.getBillingAddress1());
        billingAddress.setAddress2(sForm.getBillingAddress2());
        billingAddress.setAddress3(sForm.getBillingAddress3());
        billingAddress.setCity(sForm.getBillingCity());
        billingAddress.setStateProvinceCd(sForm.getBillingState());
        billingAddress.setPostalCode(sForm.getBillingPostalCode());
        billingAddress.setCountryCd(sForm.getBillingCountry());
        //Jd begin
        //ActionErrors ae = setJdThresholdValues(request, sForm, dd, cuser);
        //if (ae.size() > 0) {
        //    return ae;
        //}
        //Jd end
        String orderManagerEmailS = sForm.getOrderManagerEmails();
        ArrayList orderManagerEmailV = new ArrayList();
        java.util.StringTokenizer st = new StringTokenizer(orderManagerEmailS, ",");
        while(st.hasMoreElements()) {
            String eMail = st.nextToken();
            eMail = eMail.trim();
            if(eMail.indexOf('@')<0) {
                eMail += "@cleanwise.com";
            }
            orderManagerEmailV.add(eMail);
        }
        dd.setOrderManagerEmails(orderManagerEmailV);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_SCHED_DELIVERY,
                String.valueOf(sForm.getShowScheduledDelivery()),cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_CONSOLIDATION,
                String.valueOf(sForm.getAllowOrderConsolidation()),cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_DIST_SKU_NUM,
                String.valueOf(sForm.getShowDistSkuNum()),cuser);
        dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.RUSH_ORDER_CHARGE,
                sForm.getRushOrderCharge(), cuser );
        
        String scheduleCutoffDaysS = sForm.getScheduleCutoffDays();
        if(Utility.isSet(scheduleCutoffDaysS)) {
            try {
                int scheduleCutoffDays = Integer.parseInt(scheduleCutoffDaysS);
                if(scheduleCutoffDays<0) {
                    String errorMess = "Field Schedule Cutoff Days. Invalid value ";
                    lUpdateErrors.add("CutoffDays",new ActionError("error.simpleGenericError",errorMess));
                }
                dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SCHEDULE_CUTOFF_DAYS,
                        scheduleCutoffDaysS,cuser);
            } catch (Exception exc) {
                String errorMess = "Field Schedule Cutoff Days. Invalid value ";
                lUpdateErrors.add("CutoffDays",new ActionError("error.simpleGenericError",errorMess));
            }
        } else {
            dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SCHEDULE_CUTOFF_DAYS,
                    "0",cuser);
        }
        
        String rebatePersentS = sForm.getRebatePersent();
        if (rebatePersentS != null && rebatePersentS.trim().length() > 0) {
            try {
                BigDecimal rebatePersentBD =
                        new BigDecimal(rebatePersentS);
            } catch (Exception exc) {
                String errorMess = "Field Rebate Persent. Invalid value ";
                lUpdateErrors.add("RebatePersent",new ActionError("error.simpleGenericError",errorMess));
            }
            String rebateEffDateS = sForm.getRebateEffDate();
            if(!Utility.isSet(rebateEffDateS)) {
                String errorMess = "Field Rebate Eff Date can't be empty when Rebate Persent is set";
                lUpdateErrors.add("RebatePersent",new ActionError("error.simpleGenericError",errorMess));
            }
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date rebateEffDate = sdf.parse(rebateEffDateS);
                BusEntityParameterData rebatePersentBEPD =
                        BusEntityParameterData.createValue();
                rebatePersentBEPD.setBusEntityId(bed.getBusEntityId());
                rebatePersentBEPD.setName("Rebate Persent");
                rebatePersentBEPD.setValue(rebatePersentS);
                rebatePersentBEPD.setEffDate(rebateEffDate);
                rebatePersentBEPD.setAddBy(cuser);
                rebatePersentBEPD.setModBy(cuser);
                dd.setAccountParameter(rebatePersentBEPD);
            } catch(Exception exc) {
                String errorMess = "Field Rebate Eff Date. Invalid value ";
                lUpdateErrors.add("RebatePersent",new ActionError("error.simpleGenericError",errorMess));
            }
        } else {
            BusEntityParameterData rebatePersentBEPD =
                    dd.getAccountParameterActual("Rebate Persent");
            if(rebatePersentBEPD!=null) {
                dd.removeAccountParameter(rebatePersentBEPD.getBusEntityParameterId());
                rebatePersentBEPD =
                        dd.getAccountParameterActual("Rebate Persent");
                rebatePersentS = "";
                String rebateEffDateS = "";
                if(rebatePersentBEPD!=null){
                    rebatePersentS = rebatePersentBEPD.getValue();
                    if(Utility.isSet(rebatePersentS)) {
                        Date rebateEffDate = rebatePersentBEPD.getEffDate();
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        rebateEffDateS = sdf.format(rebateEffDate);
                    }
                }
                
                sForm.setRebatePersent(rebatePersentS);
                sForm.setRebateEffDate(rebateEffDateS);
            }
        }
        
        dd.setMakeShipToBillTo(sForm.getMakeShipToBillTo());
        
        dd.setFreightChargeType(sForm.getFreightChargeType());
        dd.setPurchaseOrderAccountName(sForm.getPurchaseOrderAccountName());
        dd.setBudgetTypeCd(sForm.getBudgetTypeCd());
        
        if(lUpdateErrors.size()>0) {
            return lUpdateErrors;
        }
        
        
        bed.setModBy(cuser);
        address.setModBy(cuser);
        billingAddress.setModBy(cuser);
        email.setModBy(cuser);
        orderPhone.setModBy(cuser);
        orderFax.setModBy(cuser);
        poConfirmFax.setModBy(cuser);
        acctType.setModBy(cuser);
        reSaleAccountErpNumber.setModBy(cuser);
        phone.setModBy(cuser);
        fax.setModBy(cuser);
        skuTag.setModBy(cuser);
        orderGuideNote.setModBy(cuser);
        
        if (accountid == 0) {
            // get store - both to verify that id is that of store and get
            // it's name
            try {
                StoreData sd = storeBean.getStore(storeid);
                sForm.setStoreName(sd.getBusEntity().getShortDesc());
            } catch (DataNotFoundException de) {
                // the given store id is apparently not a store
                sForm.setStoreName("");
                lUpdateErrors.add("Store.id",
                        new ActionError("account.bad.store"));
                return lUpdateErrors;
            } catch (Exception e) {
                throw e;
            }
            
            bed.setAddBy(cuser);
            address.setAddBy(cuser);
            billingAddress.setAddBy(cuser);
            email.setAddBy(cuser);
            phone.setAddBy(cuser);
            fax.setAddBy(cuser);
            acctType.setAddBy(cuser);
            reSaleAccountErpNumber.setAddBy(cuser);
            skuTag.setAddBy(cuser);
            orderGuideNote.setAddBy(cuser);
            
            dd = accountBean.addAccount(dd, storeid);
            
            sForm.setId(String.valueOf(dd.getBusEntity().getBusEntityId()));
        } else {
            // Now update this Account
            accountBean.updateAccount(dd);
        }
        
        sForm.setDataFieldProperties(dd.getDataFieldProperties());
        session.setAttribute("ACCOUNT_DETAIL_FORM", sForm);
        session.setAttribute("Account.id", sForm.getId());
        session.setAttribute("Account.name", sForm.getName());
        session.setAttribute("Store.id", sForm.getStoreId());
        session.setAttribute("Store.name", sForm.getStoreName());
        
        return lUpdateErrors;
    }
    
    /*public static ActionErrors updateAccountFiscalCal(HttpServletRequest request,
            ActionForm form)
            throws Exception {

        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        
        AccountMgrDetailForm sForm = (AccountMgrDetailForm) form;

        if (sForm == null) {
	    lUpdateErrors.add("Fiscal Calendar",
			      new ActionError("variable.empty.error",
					      "Fiscal Calendar"));
	    // Report the errors to allow for edits.
            return lUpdateErrors;
        }

	if(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
	    if (sForm.getStoreId() == null || sForm.getStoreId().length() == 0) {
		lUpdateErrors.add("storeId",
				  new ActionError("variable.empty.error",
						  "Store Id"));
	    }
	}
	if ( null != lUpdateErrors && lUpdateErrors.size() > 0 ) {
	    return lUpdateErrors;
	}


        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
	SessionTool stl = new SessionTool(request);
        String cuser =         stl.getLoginName();
	sForm.getFiscalCalUpdate().setModBy(cuser);
	lUpdateErrors = checkOnFiscalCalData(sForm.getFiscalCalUpdate());
	if ( null != lUpdateErrors && lUpdateErrors.size() > 0 ) {
	    lUpdateErrors.add("Fiscal Calendar Data",
			      new ActionError("error.simpleGenericError",
					      "Check the data values entered"));

	    return lUpdateErrors;
	}
	FiscalCalenderData fcd = accountBean.updateFiscalCal(sForm.getFiscalCalUpdate());
	sForm.getAccountData().setFiscalCalenders
	    (accountBean.getFiscalCalCollection(fcd.getBusEntityId()) 
	     );
	    return lUpdateErrors;
    }

    private static ActionErrors checkOnFiscalCalData(FiscalCalenderData pFcd) {
	ActionErrors dataCk = new ActionErrors();
	if ( null == pFcd ) {;
	    dataCk.add("Fiscal Calendar Data",
		       new ActionError("error.simpleGenericError",
				       "No data present."));
	}
	if ( pFcd.getFiscalYear() <= 0 || pFcd.getFiscalYear()> 9999 ) {
	    dataCk.add("Fiscal Calendar Year",
		       new ActionError("error.simpleGenericError",
				       "Fiscal Calendar Year " +
				       pFcd.getFiscalYear() + 
				       " is out of range. (1 - 9999 allowed)"));
	    
	}

	Calendar now = Calendar.getInstance();
	int nowYear = now.get(Calendar.YEAR);
	if ( pFcd.getFiscalYear() < nowYear ) {
	    dataCk.add("Fiscal Calendar Year",
		       new ActionError("error.simpleGenericError",
				       "Fiscal Calendar Year " +
				       pFcd.getFiscalYear() + 
				       " updates are not allowed." +
				       "  Only years " + nowYear + " and later "
				       + " can be modified." ));
	    
	}

	return dataCk;
    }*/
    
    
    //Jd begin
    /**
     *  Sets the jdThresholdValues attribute of the AccountMgrLogic class
     *
     *@param  request   The new jdThresholdValues value
     *@param  sForm     The new jdThresholdValues value
     *@param  pAccount  The new jdThresholdValues value
     *@param  pUser     The new jdThresholdValues value
     *@return           Description of the Return Value
     */
    private static ActionErrors setJdThresholdValues(HttpServletRequest request,
            AccountMgrDetailForm sForm,
            AccountData pAccount,
            String pUser) {
        ActionErrors ae = new ActionErrors();
        String weightThreshold = sForm.getWeightThreshold();
        String priceThreshold = sForm.getPriceThreshold();
        String contractThreshold = sForm.getContractThreshold();
        PropertyData weightThresholdProp = null;
        PropertyData priceThresholdProp = null;
        PropertyData contractThresholdProp = null;
        PropertyDataVector prop = pAccount.getMiscProperties();
        for (int ii = 0; ii < prop.size(); ii++) {
            PropertyData prD = (PropertyData) prop.get(ii);
            String shortDesc = prD.getShortDesc();
            if ("Weight Threshold".equalsIgnoreCase(shortDesc)) {
                weightThresholdProp = prD;
            } else if ("Price Threshold".equalsIgnoreCase(shortDesc)) {
                priceThresholdProp = prD;
            } else if ("Contract Threshold".equalsIgnoreCase(shortDesc)) {
                contractThresholdProp = prD;
            }
        }
        if (weightThreshold != null && weightThreshold.trim().length() > 0) {
            try {
                Double.parseDouble(weightThreshold);
                if (weightThresholdProp == null) {
                    weightThresholdProp = PropertyData.createValue();
                    weightThresholdProp.setBusEntityId(pAccount.getBusEntity().getBusEntityId());
                    weightThresholdProp.setAddBy(pUser);
                    weightThresholdProp.setModBy(pUser);
                    weightThresholdProp.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                    weightThresholdProp.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.EXTRA);
                    weightThresholdProp.setShortDesc("Weight Threshold");
                    weightThresholdProp.setValue(weightThreshold);
                    prop.add(weightThresholdProp);
                } else {
                    weightThresholdProp.setModBy(pUser);
                    weightThresholdProp.setValue(weightThreshold);
                }
            } catch (Exception exc) {
                ae.add("error", new ActionError("error.simpleGenericError", "Wrong Weight Threshold value: " + weightThreshold));
            }
        } else {
            if (weightThresholdProp != null) {
                prop.remove(weightThresholdProp);
            }
        }
        if (priceThreshold != null && priceThreshold.trim().length() > 0) {
            try {
                Double.parseDouble(priceThreshold);
                if (priceThresholdProp == null) {
                    priceThresholdProp = PropertyData.createValue();
                    priceThresholdProp.setBusEntityId(pAccount.getBusEntity().getBusEntityId());
                    priceThresholdProp.setAddBy(pUser);
                    priceThresholdProp.setModBy(pUser);
                    priceThresholdProp.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                    priceThresholdProp.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.EXTRA);
                    priceThresholdProp.setShortDesc("Price Threshold");
                    priceThresholdProp.setValue(priceThreshold);
                    prop.add(priceThresholdProp);
                } else {
                    priceThresholdProp.setModBy(pUser);
                    priceThresholdProp.setValue(priceThreshold);
                }
            } catch (Exception exc) {
                ae.add("error", new ActionError("error.simpleGenericError", "Wrong Price Threshold value: " + priceThreshold));
            }
        } else {
            if (priceThresholdProp != null) {
                prop.remove(priceThresholdProp);
            }
        }
        if (contractThreshold != null && contractThreshold.trim().length() > 0) {
            try {
                Double.parseDouble(contractThreshold);
                if (contractThresholdProp == null) {
                    contractThresholdProp = PropertyData.createValue();
                    contractThresholdProp.setBusEntityId(pAccount.getBusEntity().getBusEntityId());
                    contractThresholdProp.setAddBy(pUser);
                    contractThresholdProp.setModBy(pUser);
                    contractThresholdProp.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                    contractThresholdProp.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.EXTRA);
                    contractThresholdProp.setShortDesc("Contract Threshold");
                    contractThresholdProp.setValue(contractThreshold);
                    prop.add(contractThresholdProp);
                } else {
                    contractThresholdProp.setModBy(pUser);
                    contractThresholdProp.setValue(contractThreshold);
                }
            } catch (Exception exc) {
                ae.add("error", new ActionError("error.simpleGenericError", "Wrong Contract Threshold value: " + contractThreshold));
            }
        } else {
            if (contractThresholdProp != null) {
                prop.remove(contractThresholdProp);
            }
        }
        return ae;
    }
    
    //Jd end
    
    /**
     *  The <code>delete</code> method removes the database entries defining
     *  this account if no other database entry is dependent on it.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@return                an <code>ActionErrors</code> value
     *@exception  Exception  if an error occurs
     *@see                   com.cleanwise.service.api.session.Account
     */
    public static ActionErrors delete(HttpServletRequest request,
            ActionForm form)
            throws Exception {
        
        ActionErrors deleteErrors = new ActionErrors();
        
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        String strid = request.getParameter("id");
        if (strid == null || strid.length() == 0) {
            deleteErrors.add("id", new ActionError("error.badRequest", "id"));
            return deleteErrors;
        }
        
        Integer id = new Integer(strid);
        int storeid = Integer.parseInt(request.getParameter("storeId"));
        AccountData dd = accountBean.getAccount(id.intValue(), storeid);
        
        if (null != dd) {
            try {
                accountBean.removeAccount(dd);
            } catch (Exception e) {
                deleteErrors.add("id",
                        new ActionError("error.deleteFailed",
                        "Account"));
                return deleteErrors;
            }
            session.removeAttribute("Account.found.vector");
        }
        
        return deleteErrors;
    }
    
    
    /**
     *  Description of the Method
     *
     *@param  pFactory       Description of the Parameter
     *@param  pAccountId     Description of the Parameter
     *@param  pForm          Description of the Parameter
     *@exception  Exception  Description of the Exception
     */
    private static void initOrderPipeline(APIAccess pFactory, int pAccountId, AccountMgrDetailForm pForm)
    throws Exception {
        
        AccountOrderPipeline orderPipeline = new AccountOrderPipeline(pFactory, pAccountId);
        pForm.setOrderPipeline(orderPipeline);
        pForm.setMaxItemCubicSize(orderPipeline.getMaxItemCubicSize().toString());
        pForm.setMaxItemWeight(orderPipeline.getMaxItemWeight().toString());
        Account act = pFactory.getAccountAPI();
        OrderRoutingDescViewVector lOrderRouting = null;
        try {
            lOrderRouting = act.getAccountOrderRoutingCollection(pAccountId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        pForm.setAccountOrderRoutingList(lOrderRouting);
    }
    
    
    /**
     *  Adds a feature to the OrderRoute attribute of the AccountMgrLogic class
     *
     *@param  request        The feature to be added to the OrderRoute attribute
     *@param  form           The feature to be added to the OrderRoute attribute
     *@return                Description of the Return Value
     *@exception  Exception  Description of the Exception
     */
    public static ActionErrors addOrderRoute(HttpServletRequest request, ActionForm form)
    throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        AccountMgrDetailForm sForm = (AccountMgrDetailForm) form;
        String cuser = (String) session.getAttribute(Constants.USER_NAME);
        try {
            if (sForm != null) {
                APIAccess factory = new APIAccess();
                OrderRoutingData route = sForm.getNewOrderRoutingData();
                route.setAccountId(sForm.getIntId());
                OrderRoutingDataVector smallVector = new OrderRoutingDataVector();
                smallVector.add(route);
                factory.getAccountAPI().updateAccountOrderRoutingCollection(smallVector);
                initOrderPipeline(factory, sForm.getIntId(), sForm);
            }
        } catch (Exception e) {
            ae.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }
    
    
    /**
     *  A unit test for JUnit
     *
     *@param  request        Description of the Parameter
     *@param  form           Description of the Parameter
     *@return                Description of the Return Value
     *@exception  Exception  Description of the Exception
     */
    public static ActionErrors testOrderRoute(HttpServletRequest request, ActionForm form)
    throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        AccountMgrDetailForm sForm = (AccountMgrDetailForm) form;
        String cuser = (String) session.getAttribute(Constants.USER_NAME);
        try {
            if (sForm != null) {
                int accountId = sForm.getIntId();
                APIAccess factory = new APIAccess();
                OrderRoutingDescView result;
                try {
                    result = factory.getPipelineAPI().getOrderRoutingDescForPostalCode(
                            sForm.getOrderRouteTestOrderZip(), sForm.getIntId());
                } catch (DataNotFoundException e) {
                    ae.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.noOrderRouteFound", sForm.getOrderRouteTestOrderZip()));
                    result = null;
                }
                sForm.setOrderRoutingDescTestResult(result);
            }
        } catch (Exception e) {
            ae.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }
    
    
    /**
     *  Description of the Method
     *
     *@param  request        Description of the Parameter
     *@param  form           Description of the Parameter
     *@return                Description of the Return Value
     *@exception  Exception  Description of the Exception
     */
    public static ActionErrors savePipelineData(HttpServletRequest request,
            ActionForm form)
            throws Exception {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "";
        }
        if (action.equals("update_zip_entry")) {
            return updatePipelineEntry(request, form);
        }
        return saveAllPipelineData(request, form);
    }
    
    
    /**
     *  Description of the Method
     *
     *@param  request        Description of the Parameter
     *@param  form           Description of the Parameter
     *@return                Description of the Return Value
     *@exception  Exception  Description of the Exception
     */
    public static ActionErrors updatePipelineEntry(HttpServletRequest request,
            ActionForm form)
            throws Exception {
        ActionErrors ae = new ActionErrors();
        
        HttpSession session = request.getSession();
        String zip = request.getParameter("zip");
        String
                account_id = request.getParameter("account_id");
        String
                form_action = request.getParameter("form_action");
        String
                newContractId = request.getParameter("newContract");
        String
                newDistributor = request.getParameter("newDistributor");
        String
                newFreightHandler = request.getParameter("newFreightHandler");
        String
                newFinalContract = request.getParameter("newFinalContract");
        String
                newFinalDistributor = request.getParameter("newFinalDistributor");
        String
                newFinalFreightHandler = request.getParameter("newFinalFreightHandler");
        String
                newLtlFreightHandler = request.getParameter("newLtlFreightHandler");
        
        try {
            APIAccess factory = new APIAccess();
            if (form_action.equals("Delete")) {
                factory.getAccountAPI().deleteOrderRoutingEntry
                        (Integer.parseInt(account_id), zip);
            } else {
                OrderRoutingData ord = OrderRoutingData.createValue();
                ord.setAccountId
                        (Integer.parseInt(account_id));
                ord.setZip(zip);
                ord.setContractId
                        (Integer.parseInt(newContractId));
                ord.setDistributorId
                        (Integer.parseInt(newDistributor));
                ord.setFreightHandlerId
                        (Integer.parseInt(newFreightHandler));
                ord.setFinalContractId
                        (Integer.parseInt(newFinalContract));
                ord.setFinalDistributorId
                        (Integer.parseInt(newFinalDistributor));
                ord.setFinalFreightHandlerId
                        (Integer.parseInt(newFinalFreightHandler));
                ord.setLtlFreightHandlerId
                        (Integer.parseInt(newLtlFreightHandler));
                factory.getAccountAPI().updateOrderRoutingEntry(ord);
            }
            
            AccountMgrDetailForm sessForm = (AccountMgrDetailForm)
            session.getAttribute("ACCOUNT_DETAIL_FORM");
            if (sessForm != null) {
                initOrderPipeline(factory, Integer.parseInt(account_id),
                        sessForm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ae;
    }
    
    
    /**
     *  Description of the Method
     *
     *@param  request        Description of the Parameter
     *@param  form           Description of the Parameter
     *@return                Description of the Return Value
     *@exception  Exception  Description of the Exception
     */
    public static ActionErrors saveAllPipelineData(HttpServletRequest request,
            ActionForm form)
            throws Exception {
        ActionErrors ae = new ActionErrors();
        
        HttpSession session = request.getSession();
        AccountMgrDetailForm sForm = (AccountMgrDetailForm) form;
        String cuser = (String) session.getAttribute(Constants.USER_NAME);
        
        try {
            if (sForm != null) {
                BigDecimal maxItemCubicSize = null;
                BigDecimal maxItemWeight = null;
                try {
                    maxItemCubicSize = new BigDecimal(sForm.getMaxItemCubicSize());
                } catch (RuntimeException e) {
                    ae.add("maxItemCubicSize", new ActionError("error.invalidNumber", "maxItemCubicSize"));
                }
                try {
                    maxItemWeight = new BigDecimal(sForm.getMaxItemWeight());
                } catch (RuntimeException e) {
                    ae.add("maxItemWeight", new ActionError("error.invalidNumber", "maxItemWeight"));
                    
                }
                if (ae.size() > 0) {
                    return ae;
                }
                APIAccess factory = new APIAccess();
                sForm.getOrderPipeline().setMaxItemCubicSize(maxItemCubicSize);
                sForm.getOrderPipeline().setMaxItemWeight(maxItemWeight);
                sForm.getOrderPipeline().save(factory, cuser);
                factory.getAccountAPI().updateAccountOrderRoutingCollection(sForm.getAccountOrderRoutingList());
                initOrderPipeline(factory, sForm.getIntId(), sForm);
            }
        } catch (Exception e) {
            ae.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.simpleGenericError", e.getMessage()));
        }
        return ae;
    }
    
    
    /**
     *  Description of the Method
     *
     *@param  request        Description of the Parameter
     *@param  form           Description of the Parameter
     *@return                Description of the Return Value
     *@exception  Exception  Description of the Exception
     */
    public static ActionErrors initDeliverySchedules
            (HttpServletRequest request,
            ActionForm form)
            throws Exception {
        
        ActionErrors ae = new ActionErrors();
        
        HttpSession session = request.getSession();
        String aid = (String) session.getAttribute("Account.id");
        APIAccess factory = new APIAccess();
        SiteDeliveryScheduleViewVector v =
                factory.getAccountAPI().getAccountDeliveryScheduleCollection
                (Integer.parseInt(aid));
        session.setAttribute("account.delivery.schedules", v);
        return ae;
    }
    
    
    /**
     *  Description of the Method
     *
     *@param  request        Description of the Parameter
     *@param  form           Description of the Parameter
     *@return                Description of the Return Value
     *@exception  Exception  Description of the Exception
     */
    public static ActionErrors
            updateDeliverySchedule(HttpServletRequest request,
            ActionForm form)
            throws Exception {
        ActionErrors ae = new ActionErrors();
        APIAccess factory = new APIAccess();
        HttpSession session = request.getSession();
        String sched_type = request.getParameter("sched_type");
        String site_id = request.getParameter("site_id");
        java.lang.String[] wkvals =
                request.getParameterValues("sched_week");
        String intervWeek = request.getParameter("intervWeek");
        if (sched_type.startsWith("Spe")) {
            if (intervWeek != null && intervWeek.length() > 0) {
                intervWeek = intervWeek.trim();
                Integer intW = null;
                
                try {
                    intW = new Integer(intervWeek);
                    if (intW.intValue() < 1 || intW.intValue() > 52) {
                        ae.add(ActionErrors.GLOBAL_ERROR,
                                new ActionError("error.simpleGenericError",
                                "Interval in weeks shoild be between 1 and 52"));
                    }
                } catch (NumberFormatException e) {
                    ae.add("error", new ActionError("error.invalidNumber", "Number of weeks"));
                }
            } else {
                ae.add(ActionErrors.GLOBAL_ERROR,
                        new ActionError("error.simpleGenericError",
                        "Empty interval weeks value"));
            }
        }
        if (site_id == null || site_id.length() == 0) {
            ae.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("error.simpleGenericError",
                    "Site identifier is missing."));
        }
        
        if (sched_type == null || sched_type.length() == 0) {
            ae.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("error.simpleGenericError",
                    "Schedule type is missing."));
        }
        if (ae.size() > 0) {
            return ae;
        }
        factory.getAccountAPI().updateSiteDeliverySchedule
                (Integer.parseInt(site_id), sched_type, wkvals, intervWeek);
        initDeliverySchedules(request, form);
        return ae;
    }
    
    public static ActionErrors
            getInventoryItemsAvailable(HttpServletRequest request,
            ActionForm form)
            throws Exception         {
        
        ActionErrors reqErrors = new ActionErrors();
        AccountMgrDetailForm sForm = (AccountMgrDetailForm) form;
        
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        String strid = request.getParameter("accountId");
        if (strid == null || strid.length() == 0) {
            reqErrors.add("id", new ActionError("error.badRequest", "id"));
            return reqErrors;
        }
        
        Integer id = new Integer(strid);
        sForm.setInventoryItemsAvailable
                (accountBean.getInventoryItemsAvailable(id.intValue()));
        
        return reqErrors;
    }
    
    public static ActionErrors
            addInventoryItems(HttpServletRequest request,
            ActionForm form)
            throws Exception         {
        
        ActionErrors reqErrors = new ActionErrors();
        AccountMgrDetailForm sForm = (AccountMgrDetailForm) form;
        
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        
        String [] itemsarr = sForm.getItemsToAddToInventory();
        accountBean.addInventoryItems(
                sForm.getAccountData().getBusEntity().getBusEntityId(),
                itemsarr, (String) session.getAttribute(Constants.USER_NAME)
                );
        
        // Reset the inventory list.
        sForm.getAccountData().setInventoryItemsData(
                accountBean.getInventoryItems(
                sForm.getAccountData().getBusEntity().getBusEntityId())
                );
        // Remove the list of items available.
        sForm.setInventoryItemsAvailable(new ArrayList());
        return reqErrors;
    }
    
    public static ActionErrors
            removeInventoryItems(HttpServletRequest request,
            ActionForm form)
            throws Exception         {
        return updateInventoryItems(request, form, "remove");
    }
    
    public static ActionErrors
            enableAutoOrderForInventoryItems(HttpServletRequest request,
            ActionForm form)
            throws Exception         {
        return updateInventoryItems(request, form, "enable-auto-order");
    }
    
    public static ActionErrors
            disableAutoOrderForInventoryItems(HttpServletRequest request,
            ActionForm form)
            throws Exception         {
        return updateInventoryItems(request, form, "disable-auto-order");
    }
    
    
    public static ActionErrors
            updateInventoryItems(HttpServletRequest request,
            ActionForm form, String pAction)
            throws Exception         {
        ActionErrors reqErrors = new ActionErrors();
        AccountMgrDetailForm sForm = (AccountMgrDetailForm) form;
        
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        
        String [] itemsarr = sForm.getSelectedInventoryItems();
        accountBean.updateInventoryItems(
                sForm.getAccountData().getBusEntity().getBusEntityId(),
                itemsarr , (String) session.getAttribute(Constants.USER_NAME),
                pAction       );
        
        // Reset the inventory list.
        sForm.getAccountData().setInventoryItemsData(
                accountBean.getInventoryItems(
                sForm.getAccountData().getBusEntity().getBusEntityId())
                );
        sForm.setSelectedInventoryItems(new String[0]);
        
        return reqErrors;
    }
    
    
    
    public static ActionErrors
            getAccountBillTos(HttpServletRequest request,
            ActionForm form)
            throws Exception         {
        ActionErrors reqErrors = new ActionErrors();
        String acctid = request.getParameter("accountId");
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        
        ArrayList v = accountBean.getAccountBillTos(Integer.parseInt(acctid));
        session.setAttribute("account.billtos.vector", v);
        
        return reqErrors;
    }
    
    public static ActionErrors
            addBillTo(HttpServletRequest request,
            ActionForm form)
            throws Exception         {
        ActionErrors reqErrors = new ActionErrors();
        AccountBillToForm sform = (AccountBillToForm)form;
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        CleanwiseUser cwu = ShopTool.getCurrentUser(request);
        Account accountBean = factory.getAccountAPI();
        int accountId = Integer.parseInt(request.getParameter("accountId"));
        sform.getBillTo().getAccountBusEntity().setBusEntityId(accountId);
        sform.getBillTo().getBusEntity().setAddBy(cwu.getUserName());
        sform.getBillTo().getBusEntity().setModBy(cwu.getUserName());
        accountBean.addBillTo(sform.getBillTo());
        ArrayList v = accountBean.getAccountBillTos(accountId);
        session.setAttribute("account.billtos.vector", v);
        
        return reqErrors;
    }
    
    public static ActionErrors
            updateBillTo(HttpServletRequest request,
            ActionForm form)
            throws Exception         {
        ActionErrors reqErrors = new ActionErrors();
        AccountBillToForm sform = (AccountBillToForm)form;
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        CleanwiseUser cwu = ShopTool.getCurrentUser(request);
        Account accountBean = factory.getAccountAPI();
        BillToData btd = sform.getBillTo();
        btd.getBusEntity().setAddBy(cwu.getUserName());
        btd.getBusEntity().setModBy(cwu.getUserName());
        accountBean.addBillTo(btd);
        BillToData btd2 = accountBean.getBillToDetail
                (btd.getBusEntity().getBusEntityId());
        log.info("btd2="+btd2);
        sform.setBillTo(btd2);
        
        return reqErrors;
    }
    
    public static ActionErrors
            getBillToDetail(HttpServletRequest request,
            ActionForm form)
            throws Exception         {
        ActionErrors reqErrors = new ActionErrors();
        AccountBillToForm sform = (AccountBillToForm)form;
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        CleanwiseUser cwu = ShopTool.getCurrentUser(request);
        Account accountBean = factory.getAccountAPI();
        
        String btid = request.getParameter("billtoId");
        log.info("logic lookup btid=" + btid);
        BillToData btd2 = accountBean.getBillToDetail
                (Integer.parseInt(btid));
        log.info("btd2="+btd2);
        sform.setBillTo(btd2);
        
        return reqErrors;
    }
    
    public static ActionErrors
            changeOrderBillTo(HttpServletRequest request,
            ActionForm form)
            throws Exception         {
        ActionErrors reqErrors = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        CleanwiseUser cwu = ShopTool.getCurrentUser(request);
        Order orderBean = factory.getOrderAPI();
        
        String new_btid = request.getParameter("billtoId")
        , accountId = request.getParameter("accountId")
        , orderId = request.getParameter("orderId")
        ;
        orderBean.updateBillTo
                (Integer.parseInt(orderId), Integer.parseInt(new_btid));
        
        return reqErrors;
    }
    
    public static ActionErrors lookupShoppingControls
            (HttpServletRequest request,
            ActionForm form)    {
        
        ActionErrors ae = new ActionErrors();
        
        HttpSession session = request.getSession();
        APIAccess factory = null;
        //        StoreAccountMgrForm pForm = (StoreAccountMgrForm) form;
        AccountMgrDetailForm pForm =
                (AccountMgrDetailForm) session.getAttribute("ACCOUNT_DETAIL_FORM");
        SessionTool st = new SessionTool(request);
        int acctid = pForm.getAccountData().getAccountId();
        
        try {
            factory = new APIAccess();
            ShoppingServices ssvc = null;
            ssvc = factory.getShoppingServicesAPI();
            Account acct = factory.getAccountAPI();
            AccountData acctData = acct.getAccount(acctid, 0);
            CatalogData cat = acct.getAccountCatalog(acctid);
            ShoppingCartItemDataVector controlItems =
                    ssvc.getItemControlInfoForAccount
                    (acctid, cat.getCatalogId());
            if ( null != controlItems ) {
                AccountSettingsData accountSpecific
                        = st.mkAccountSpecificData(request,acctData);
                accountSpecific.setShoppingControls(controlItems);
                st.setAccountSettings(request,accountSpecific);
            }
            
        } catch(Exception exc) {
            exc.printStackTrace();
            ae.add("error",new ActionError("error.systemError",
                    "No ejb access"));
        }
        return ae;
    }
}

