/*
 * LocateReportAccountLogic.java
 *
 * Created on October, 2008
 */
package com.cleanwise.view.logic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.LocatePropertyNames;
import com.cleanwise.view.forms.LocateReportAccountForm;
import com.cleanwise.view.forms.StorePortalForm;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.session.DWOperation;
import com.cleanwise.service.api.util.QueryRequest;


public class LocateReportAccountLogic {

    public static ActionErrors processAction(HttpServletRequest request, StorePortalForm baseForm, String action) throws Exception {
        LocateReportAccountForm form = baseForm.getLocateReportAccountForm();

        try {
            ActionErrors ae = new ActionErrors();
            HttpSession session = request.getSession();
            if (LocatePropertyNames.INIT_SEARCH_ACTION.equals(action)) {
                ae = initSearch(request, baseForm);
                return ae;
            }

            int myLevel = form.getLevel() + 1;
            form.setAccountsToReturn(null);

            if (LocatePropertyNames.CANCEL_ACTION.equals(action)) {
                ae = returnNoValue(request, form);
            }
            else if (LocatePropertyNames.SEARCH_ACTION.equals(action)) {
                ae = search(request, form);
            }
            else if (LocatePropertyNames.RETURN_SELECTED_ACTION.equals(action)) {
                ae = returnSelected(request, form);
            }
            return ae;
        }
        finally {
            if (form != null) {
                form.reset(null, null);
            }
        }
    }

    public static ActionErrors initSearch(HttpServletRequest request, StorePortalForm baseForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session  = request.getSession();
        APIAccess factory = new APIAccess();
        Group groupEjb = factory.getGroupAPI();

        CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();
        if(storeD == null) {
            ae.add("error",new ActionError("error.simpleGenericError","No store info"));
            return ae;
        }

        LocateReportAccountForm form = baseForm.getLocateReportAccountForm();
        if (form == null) {
            form = new LocateReportAccountForm();
            form.setLevel(1);
            baseForm.setLocateReportAccountForm(form);
        }

        String searchType = form.getSearchType();
        if (searchType == null || searchType.trim().length() == 0) {
            searchType = LocatePropertyNames.NAME_BEGINS_SEARCH_TYPE;
        }
        form.setSearchType(searchType);

        form.setSearchGroupId(0);

        GroupSearchCriteriaView grSearchCrVw = GroupSearchCriteriaView.createValue();
        grSearchCrVw.setGroupType(RefCodeNames.GROUP_TYPE_CD.ACCOUNT);
        grSearchCrVw.setGroupName("");
        grSearchCrVw.setStoreId(storeD.getBusEntity().getBusEntityId());
        grSearchCrVw.setGroupStatus(RefCodeNames.GROUP_STATUS_CD.ACTIVE);
        GroupDataVector groups = groupEjb.getGroups(grSearchCrVw, Group.NAME_BEGINS_WITH, Group.ORDER_BY_NAME);
        form.setAccountGroups(groups);

        AccountUIViewVector accountDV = form.getAccounts();
        if (accountDV == null) {
            form.setAccounts(new AccountUIViewVector());
        }
        form.setAccountsToReturn(null);
        return ae;
    }

    public static ActionErrors returnNoValue(HttpServletRequest request, LocateReportAccountForm form) throws Exception {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionAttributes.SEARCH_RESULT.REPORT_ACCOUNTS);
        return new ActionErrors();
    }

    public static ActionErrors search(HttpServletRequest request, LocateReportAccountForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();

        String fieldValue = form.getSearchField();
        String fieldSearchType = form.getSearchType();
        int searchGroupId = form.getSearchGroupId();

        AccountUIViewVector dv = null;
        if (form.getDataSourceType().equals("DW")) {
           dv = searchDW(request, fieldValue, fieldSearchType, searchGroupId, form.getShowInactiveFl());
        } else {
           dv = search(request, fieldValue, fieldSearchType, searchGroupId, form.getShowInactiveFl());
        }
        form.setAccounts(dv);

        return ae;
    }

    /**
     *Returns an account data vector based off the specified search criteria
     */
    static public AccountUIViewVector search(HttpServletRequest request, String fieldValue,
        String fieldSearchType, int searchGroupId, boolean showInactiveFl) throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        Account acctBean = factory.getAccountAPI();

        BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
        crit.setResultLimit(Constants.MAX_ACCOUNTS_TO_RETURN);


        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        // add criteria for accounts based on the type of user and store selected
        IdVector selectedStoreIds = AnalyticReportLogic.getStoreSelectedFilter(request, Constants.STORE_FILTER_NAME);
        crit.setStoreBusEntityIds(selectedStoreIds);


        UserData ud = appUser.getUser();
        String userType = ud.getUserTypeCd();
        if (! ( userType.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR) ||
                userType.equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR) ||
                userType.equals(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR) )
            ){
            crit.addUserId(ud.getUserId());
        }
        
        if (fieldValue != null && fieldValue.trim().length() > 0) {
            fieldValue = fieldValue.trim();
            if (LocatePropertyNames.ID_SEARCH_TYPE.equals(fieldSearchType)) {
                crit.setSearchId(fieldValue);
            }
            else {
                if (LocatePropertyNames.NAME_BEGINS_SEARCH_TYPE.equals(fieldSearchType)) {
                    crit.setSearchName(fieldValue);
                    crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
                }
                else {
                    crit.setSearchName(fieldValue);
                    crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
                }
            }
        }
        if(searchGroupId > 0) {
           crit.setSearchGroupId("" + searchGroupId);
        }
        crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
        crit.setSearchForInactive(showInactiveFl);

//        AccountDataVector dv = acctBean.getAccountsByCriteria(crit);
        AccountUIViewVector dv = acctBean.getAccountsUIByCriteria(crit);

        return dv;
    }

    static public AccountUIViewVector searchDW(HttpServletRequest request, String fieldValue,
         String fieldSearchType, int searchGroupId, boolean showInactiveFl) throws Exception {

         // Get a reference to the admin facade
         HttpSession session = request.getSession();
         APIAccess factory = new APIAccess();
         DWOperation dwEjb = factory.getDWOperationAPI();

         BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
         crit.setResultLimit(Constants.MAX_ACCOUNTS_TO_RETURN);
         // add criteria for accounts based on the type of user and store selected
         // Store FILTER
         IdVector selectedStoreIds = AnalyticReportLogic.getStoreSelectedFilter(request, Constants.DW_STORE_FILTER_NAME);
         crit.setStoreBusEntityIds(selectedStoreIds);

         // User FILTER
         IdVector selectedUserIds = AnalyticReportLogic.getUserSelectedFilter(request, Constants.DW_USER_FILTER_NAME);
         crit.setUserIds(selectedUserIds);

         // Account FILTER
         if (fieldValue != null && fieldValue.trim().length() > 0) {
             fieldValue = fieldValue.trim();
             if (LocatePropertyNames.ID_SEARCH_TYPE.equals(fieldSearchType)) {
                 crit.setSearchId(fieldValue);
             }
             else {
                 if (LocatePropertyNames.NAME_BEGINS_SEARCH_TYPE.equals(fieldSearchType)) {
                     crit.setSearchName(fieldValue);
                     crit.setSearchNameType(QueryRequest.BEGINS);
                 }
                 else {
                     crit.setSearchName(fieldValue);
                     crit.setSearchNameType(QueryRequest.CONTAINS);
                 }
             }
         }
         if(searchGroupId > 0) {
            crit.setSearchGroupId("" + searchGroupId);
         }
         crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
         crit.setSearchForInactive(showInactiveFl);

//        AccountDataVector dv = acctBean.getAccountsByCriteria(crit);
//         AccountUIViewVector dv = acctBean.getAccountsUIByCriteria(crit);
         AccountUIViewVector dv = dwEjb.getAccountsUIByCriteria(crit);

         return dv;
    }
    public static ActionErrors returnSelected(HttpServletRequest request, LocateReportAccountForm form) throws Exception {
        HttpSession session = request.getSession();
        int[] selected = form.getSelected();
        AccountUIViewVector dv = form.getAccounts();
        AccountUIViewVector retDV = new AccountUIViewVector();

        for (Iterator iter = dv.iterator(); iter.hasNext();) {
            AccountUIView aD = (AccountUIView) iter.next();
            for (int i = 0; i < selected.length; i++) {
                if (aD.getBusEntity().getBusEntityId() == selected[i]) {
                    retDV.add(aD);
                }
            }
        }
        form.setAccountsToReturn(retDV);

        clearMultiAccountFilterInSession(request, getFilterName(form));
	putMultiAccountFilter2Session(request, retDV, getFilterName(form));
        return new ActionErrors();
    }

    public static ActionErrors clearFilter(HttpServletRequest request, StorePortalForm form) throws Exception {
        HttpSession session = request.getSession();
        LocateReportAccountForm acctForm = form.getLocateReportAccountForm();
        if( acctForm !=null ){
          org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(acctForm.getName()), acctForm.getProperty(), null);
        }

        clearMultiAccountFilterInSession(request, getFilterName(acctForm));
        return new ActionErrors();
    }

    public static ActionErrors setFilter(HttpServletRequest request, StorePortalForm form) throws Exception {

        HttpSession session = request.getSession();
        LocateReportAccountForm acctForm = form.getLocateReportAccountForm();
        AccountUIViewVector acctDV = acctForm.getAccountsToReturn();
        org.apache.commons.beanutils.BeanUtils.setProperty(session.getAttribute(acctForm.getName()), acctForm.getProperty(), acctDV);
        acctForm.setLocateAccountFl(false);
        form.setReturnLocateTypeCd(RefCodeNames.RETURN_LOCATE_TYPE_CD.ACCOUNT);
        return new ActionErrors();
    }


    public static HashMap getReportSearchFilter(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	HashMap hashMapFilter = null;
    	if (session.getAttribute("REPORT_CONTROL_FILTER_MAP") != null) {
    		hashMapFilter = (HashMap)session.getAttribute("REPORT_CONTROL_FILTER_MAP");
    	}
    	return hashMapFilter;
    }

    public static ActionErrors clearMultiAccountFilterInSession(HttpServletRequest request, String filterName) {
    	HashMap hashMapFilter = getReportSearchFilter(request);
    	if (hashMapFilter != null) {
    		hashMapFilter.put(filterName, new LinkedList());
    	}
    	return new ActionErrors();
    }


    public static ActionErrors putMultiAccountFilter2Session(HttpServletRequest request, AccountUIViewVector accounts, String filterName) {
    	HashMap hashMapFilter = null;
    	boolean canPutAccounts2Session = false;
    	ActionErrors errors = new ActionErrors();

    	/// Checking of the presence of the found store in the session attribute
    	hashMapFilter = getReportSearchFilter(request);
    	if (hashMapFilter == null) {
    		// to-do 'error'
		}
		else {
			canPutAccounts2Session = true;
		}

    	/// Putting the found accounts into proper session attribute
    	if (canPutAccounts2Session) {
    		LinkedList accountList = new LinkedList();
    		if (accounts != null && accounts.size() > 0) {
    			for (int i = 0; i < accounts.size(); ++i) {
    				AccountUIView account = (AccountUIView)accounts.get(i);

                                if (filterName.equals(Constants.DW_ACCOUNT_FILTER_NAME)) {
                                  accountList.add(account.getAccountDimIds());
                                } else {
                                  accountList.add(new Integer(account.getBusEntity().getBusEntityId()));
                                }

    			}
    		}
    		hashMapFilter.put(filterName, accountList);
    	}

    	return errors;
    }

    private static String getFilterName (LocateReportAccountForm pForm){
      String filterName = Constants.ACCOUNT_FILTER_NAME;
      if (pForm != null) {
        if (pForm.getDataSourceType().equals("DW")) {
          filterName = Constants.DW_ACCOUNT_FILTER_NAME;
        }
      }
      return filterName;
     }


}
