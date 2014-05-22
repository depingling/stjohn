/*
 * StoreItemMgrLogic.java
 *
 * Created on March 17, 2005, 2:59 PM
 */
package com.cleanwise.view.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Group;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.service.api.value.AccountSearchResultView;
import com.cleanwise.service.api.value.AccountSearchResultViewVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.FiscalCalenderData;
import com.cleanwise.service.api.value.GroupDataVector;
import com.cleanwise.service.api.value.GroupSearchCriteriaView;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.view.forms.Admin2BudgetLoaderMgrForm;
import com.cleanwise.view.forms.LocateStoreAccountForm;
import com.cleanwise.view.forms.StorePortalForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionAttributes;
import com.cleanwise.view.utils.StringUtils;
import org.apache.log4j.*;


/**
 *
 * @author Ykupershmidt
 */
public class LocateStoreAccountLogic {
private static final Logger log = Logger.getLogger(LocateStoreAccountLogic.class);
 
 public static ActionErrors processAction(HttpServletRequest request, 
         StorePortalForm baseForm, String action)
    	throws Exception
    {
    LocateStoreAccountForm pForm = baseForm.getLocateStoreAccountForm();

    try {
    ActionErrors ae = new ActionErrors();
    HttpSession session  = request.getSession();
    if("initSearch".equals(action)) {
      ae = initSearch(request,baseForm);
      return ae;
    }
    int myLevel = pForm.getLevel()+1;
    //pForm.setLevel(myLevel);
    //pForm = (LocateStoreAccountForm) 
    //  session.getAttribute(SessionAttributes.SEARCH_FORM.LOCATE_STORE_ACCOUNT_FORM);
    pForm.setAccountsToReturn(null);    

    if("Cancel".equals(action)) {
      ae = returnNoValue(request,pForm);
    } else if("Search".equals(action)) {
        //ae = search(request,pForm);
        HashMap formVars = baseForm.getFormVars();
        if (formVars != null &&
            "userAccounts".equals(formVars.get("accountSearchType"))) {
            ae = userAccountSearch(request, pForm);
        } else {
            ae = accountSearch(request,pForm);
            // bug # 4601 - Added to display fiscal year drop down for budget loader page.
            if(baseForm instanceof Admin2BudgetLoaderMgrForm) {
        		ae = getFiscalYearsList(request,baseForm);
        	}
        }
    } else if("Return Selected".equals(action)) {
      ae = returnSelected(request,pForm);
    }
    return ae;
    }finally {
      if(pForm!=null)  pForm.reset(null, null);
    }
 }


 
 public static ActionErrors initSearch(HttpServletRequest request, StorePortalForm baseForm)					    
    	throws Exception
    {
    ActionErrors ae = new ActionErrors();
    HttpSession session  = request.getSession();

    APIAccess factory = new APIAccess();
    Group groupEjb = factory.getGroupAPI();

        
    CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
    StoreData storeD = appUser.getUserStore();
    if(storeD==null) {
      ae.add("error",new ActionError("error.simpleGenericError","No store info"));
      return ae;
    }
    
    LocateStoreAccountForm pForm = baseForm.getLocateStoreAccountForm();
    if(pForm==null) {
      pForm = new LocateStoreAccountForm();
      pForm.setLevel(1);
      baseForm.setLocateStoreAccountForm(pForm);
    }
    //baseForm.setEmbeddedForm(pForm);
    
    String searchType = pForm.getSearchType(); 
    if(searchType==null || searchType.trim().length()==0) searchType = "nameBegins";
    pForm.setSearchType(searchType);

    //pForm.setSearchGroupId(0);
        
    GroupSearchCriteriaView grSearchCrVw = GroupSearchCriteriaView.createValue();
    grSearchCrVw.setGroupType(RefCodeNames.GROUP_TYPE_CD.ACCOUNT);
    grSearchCrVw.setGroupName("");
    grSearchCrVw.setStoreId(storeD.getBusEntity().getBusEntityId());
    grSearchCrVw.setGroupStatus(RefCodeNames.GROUP_STATUS_CD.ACTIVE);
    GroupDataVector groups =
        groupEjb.getGroups(grSearchCrVw,Group.NAME_BEGINS_WITH,Group.ORDER_BY_NAME);    
    pForm.setAccountGroups(groups);
    
    AccountDataVector accountDV = pForm.getAccounts();
    if(accountDV==null) {
      pForm.setAccounts(new AccountDataVector());
    }
    pForm.setAccountsToReturn(null);    
    return ae;
 }

 
 public static ActionErrors returnNoValue(HttpServletRequest request,
                      LocateStoreAccountForm pForm)					    
    	throws Exception
    {
    ActionErrors ae = new ActionErrors();
    HttpSession session  = request.getSession();
    session.removeAttribute(SessionAttributes.SEARCH_RESULT.STORE_ACCOUNTS);
    return ae;
 }
 
  public static ActionErrors accountSearch(HttpServletRequest request,
		   LocateStoreAccountForm pForm) throws Exception{
	ActionErrors ae = new ActionErrors();
	HttpSession session = request.getSession();
	APIAccess factory = new APIAccess();
	Account accountBean = factory.getAccountAPI();
	AccountSearchResultViewVector acntSrchRsltVctr = null;
	
	String fieldValue = pForm.getSearchField();
	String fieldSearchType = pForm.getSearchType();
	String searchGroupId = String.valueOf(pForm.getSearchGroupId());
	boolean showInactive = pForm.getShowInactiveFl();
	int id =0;
	CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	String storeStr = null;
    IdVector stores = new IdVector();
    if (!appUser.isaSystemAdmin()) {
	  stores = appUser.getUserStoreAsIdVector();
	  storeStr = stores.toCommaString(stores);
	}
    try{
      if (appUser.isaAccountAdmin()) {
        acntSrchRsltVctr = accountBean.searchAccounts(appUser.getUserId(),stores,fieldValue,fieldSearchType,searchGroupId,showInactive);
      } else {
    	  //Bug: 5097 - Modified to display accounts that were configured in all groups (when none option is selected) for store admin.
    	  boolean restrictAcctInvoiceByUser = false;
    	  if(appUser.isaStoreAdmin()) {
    		  Properties props = appUser.getUserProperties();
    		  String isRestrictAcctInvoices = props.getProperty(RefCodeNames.PROPERTY_TYPE_CD.RESTRICT_ACCT_INVOICES);
    		  restrictAcctInvoiceByUser = isRestrictAcctInvoices!=null && isRestrictAcctInvoices.equalsIgnoreCase("true");
    	  }
    	  if(restrictAcctInvoiceByUser){
    		  int userId = appUser.getUserId();
    		  acntSrchRsltVctr = accountBean.searchGroupsByUserId(storeStr, fieldValue, fieldSearchType, searchGroupId, userId, showInactive);
    	  } else {
    		  acntSrchRsltVctr = accountBean.search(storeStr, fieldValue, fieldSearchType, searchGroupId, showInactive);
    	  }     
      }
    }catch(Exception e){
	  //log.debug("Error Message :" + e.getMessage());
	  ActionErrors clwError = StringUtils.getUiErrorMess(e);
	  return clwError;
	}
	pForm.setAccountSearchResult(acntSrchRsltVctr);
	pForm.setAccounts(Utility.toAccountDataVector(acntSrchRsltVctr));
	return ae;
  }
  
  public static ActionErrors userAccountSearch(HttpServletRequest request,
		   LocateStoreAccountForm pForm) throws Exception{
	ActionErrors ae = new ActionErrors();
	HttpSession session = request.getSession();
	APIAccess factory = new APIAccess();
	Account accountBean = factory.getAccountAPI();
	AccountSearchResultViewVector accountSearchResultVV = null;
	
	String fieldValue = pForm.getSearchField();
	String fieldSearchType = pForm.getSearchType();
	String searchGroupId = String.valueOf(pForm.getSearchGroupId());
	boolean showInactive = pForm.getShowInactiveFl();
	int id =0;
	CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        int userId = appUser.getUserId();
	IdVector stores = null;
        //isaSystemAdmin()
	//if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ASSET_ADMINISTRATOR)) {
        //    stores = appUser.getUserStoreAsIdVector();
        //    userId = 0;
	//}
        try {
            accountSearchResultVV = accountBean.searchAccounts(userId,
                                                               stores,
                                                               fieldValue,
                                                               fieldSearchType,
                                                               searchGroupId,
                                                               showInactive);
	} catch(Exception e) {
	  ActionErrors clwError = StringUtils.getUiErrorMess(e);
	  return clwError;
	}
	pForm.setAccountSearchResult(accountSearchResultVV);
	pForm.setAccounts(Utility.toAccountDataVector(accountSearchResultVV));
	return ae;
  }
 
  public static ActionErrors search(HttpServletRequest request,
            LocateStoreAccountForm pForm)
             throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    Account accountBean = factory.getAccountAPI();


    String fieldValue = pForm.getSearchField();
    String fieldSearchType = pForm.getSearchType();
    int searchGroupId = pForm.getSearchGroupId();
    AccountDataVector dv = search(request, fieldValue,fieldSearchType,searchGroupId,pForm.getShowInactiveFl());
   /* if(!pForm.getShowInactiveFl()) {
      for(Iterator iter=dv.iterator(); iter.hasNext();) {
        AccountData aD = (AccountData) iter.next();
        if(RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE.
                equals(aD.getBusEntity().getBusEntityStatusCd())) {
          iter.remove();
        }
      }
    }*/
    pForm.setAccounts(dv);
    return ae;
    }


    /**
     *Returns an account data vector based off the specified search criteria
     */
    static public AccountDataVector search(HttpServletRequest request,String fieldValue,
        String fieldSearchType,int searchGroupId,boolean showInactiveFl) throws Exception{

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Account acctBean = factory.getAccountAPI();

        BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
	    crit.setResultLimit(Constants.MAX_ACCOUNTS_TO_RETURN);

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());

        if(fieldValue!=null && fieldValue.trim().length()>0) {
          fieldValue = fieldValue.trim();
          if ("id".equals(fieldSearchType)) {
              crit.setSearchId(fieldValue);
          }
          else {
	      if ("nameBegins".equals(fieldSearchType)) {
                crit.setSearchName(fieldValue);
                crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
            } else {
                crit.setSearchName(fieldValue);
                crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
            }
          }
        }


        if(searchGroupId>0) {
           crit.setSearchGroupId(""+searchGroupId);
        }
        crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
        crit.setSearchForInactive(showInactiveFl);
        AccountDataVector dv = acctBean.getAccountsByCriteria(crit);
        
        return dv;
    }

 
  public static ActionErrors returnSelected(HttpServletRequest request,
            LocateStoreAccountForm pForm)
             throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    int[] selected = pForm.getSelected();
    AccountDataVector dv = pForm.getAccounts();
    AccountDataVector retDV = new AccountDataVector();
    for(Iterator iter=dv.iterator(); iter.hasNext();) {
      AccountData aD = (AccountData) iter.next();
      for(int ii=0; ii<selected.length; ii++) {
        if(aD.getBusEntity().getBusEntityId()==selected[ii]) {
          retDV.add(aD);
        }
      }
    }
    pForm.setAccountsToReturn(retDV);
    return ae;
    }
  

  
  public static ActionErrors clearFilter(HttpServletRequest request,StorePortalForm pForm) throws Exception{
      HttpSession session = request.getSession();
      //LocateStoreAccountForm acctForm = (LocateStoreAccountForm) 
      //session.getAttribute(SessionAttributes.SEARCH_FORM.LOCATE_STORE_ACCOUNT_FORM);
      LocateStoreAccountForm acctForm = pForm.getLocateStoreAccountForm();
      org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(acctForm.getName()),acctForm.getProperty(),null);
      return new ActionErrors();
  }
            
  public static ActionErrors setFilter(HttpServletRequest request,StorePortalForm pForm)
             throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    LocateStoreAccountForm acctForm = pForm.getLocateStoreAccountForm();
    AccountDataVector acctDV = acctForm.getAccountsToReturn();
    org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(acctForm.getName()),acctForm.getProperty(),acctDV);
    acctForm.setLocateAccountFl(false);
    return ae;
    }
  
  /**
   * Gets the current fiscal year and next fiscal year for an account.
   * @param request
   * @param baseForm
   * @return
   * @throws Exception
   */
  private static ActionErrors getFiscalYearsList(HttpServletRequest request,StorePortalForm baseForm)
	    	throws Exception  {
	    ActionErrors ae = new ActionErrors();
	    LocateStoreAccountForm pForm = baseForm.getLocateStoreAccountForm();
	    AccountSearchResultViewVector searchResultsVector = pForm.getAccountSearchResult();
	    AccountSearchResultView accuntSearchResulstView;
	    List<Integer> fiscalYearsList;
        int accountId;
	    for(int ind=0; searchResultsVector!=null && ind<searchResultsVector.size(); ind++) {
	    	accuntSearchResulstView = (AccountSearchResultView)searchResultsVector.get(ind);
	    	accountId = accuntSearchResulstView.getAccountId();
	    	fiscalYearsList = getFiscalYearsForAccount(accountId);
	    	accuntSearchResulstView.setFiscalYearsList(fiscalYearsList);
	    	accuntSearchResulstView.setSelectedFiscalYear(fiscalYearsList.get(0));
	    }
    return ae;    	
    }
  
  /**
   * Gets Fiscal calendar years list, this list contains current fiscal calendar year and next year.
   * If an account has no fiscal year defined or an account has 0 fiscal calendar year, then the returned list
   * contains current year and next year.
   * @param pAccountId
   * @return
   */
  private static List<Integer> getFiscalYearsForAccount(int pAccountId){
	  List<Integer> fiscalYearsList = new ArrayList<Integer>();
	  int currentFiscalYear = -1;
      try {
			APIAccess factory = new APIAccess();
			Account accountBean = factory.getAccountAPI();
			FiscalCalenderData currentFiscalCalendar = accountBean.getCurrentFiscalCalender(pAccountId);
			if(currentFiscalCalendar!=null) {
				currentFiscalYear = currentFiscalCalendar.getFiscalYear();
			}
       } catch(Exception e){
    	  log.info("LocateStoreAccountLogic >>> An exception has occured while getting" +
    	  		" Fiscal Calendars for account -->>"+pAccountId);
       }
		if(currentFiscalYear<=0) {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(new java.util.Date());
			int currentYear = cal.get(Calendar.YEAR);
			fiscalYearsList.add(currentYear);
			fiscalYearsList.add(currentYear+1);
		} else {
			if(!fiscalYearsList.contains(currentFiscalYear));
				fiscalYearsList.add(currentFiscalYear);
			if(!fiscalYearsList.contains(currentFiscalYear+1));
				fiscalYearsList.add(currentFiscalYear+1);
		}
      java.util.Collections.sort(fiscalYearsList);
      
	  return fiscalYearsList;
  }
}
