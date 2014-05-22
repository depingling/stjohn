

package com.cleanwise.view.logic;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.UserMgrLocateForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.PasswordUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.Utility;
/**
 *  <code>UserMgrLocateLogic</code> implements the logic needed to manipulate user
 *  records.
 *
 *@author     durval
 *@created    October 8, 2001
 */
public class UserMgrLocateLogic {

    /**
     *  Gets the UserDetailById attribute of the UserMgrLogic class
     *
     *@param  request        Description of Parameter
     *@param  pUserId        Description of Parameter
     *@exception  Exception  Description of Exception
     *
     *
     * /**
     * <code>getAll</code> users, note that the user list
     * returned will be limited to a certain amount of records.
     * It is up to the jsp page to detect this and to issued a
     * subsequent call to get more records.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void getAll(HttpServletRequest request,
    ActionForm form)
    throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
  int acctid = 0;
        try {
      UserMgrLocateForm sForm = (UserMgrLocateForm)form;
      acctid = sForm.getSearchAccountId();
  }
  catch (Exception e) {
      acctid = 0;
  }

        APIAccess factory = new APIAccess();
        User userBean = factory.getUserAPI();
        // XXX Implement the logic to get the next set.
        UserDataVector uv;
  if ( acctid <= 0 ) {
      uv = userBean.getUsersCollection(0, 1000);
  }
  else {
      // Get all the users for the account.
      uv = userBean.getUsersCollectionByBusEntity(acctid, null);
  }

        session.setAttribute("Users.found.vector", uv);
        session.setAttribute("Users.found.total",
        String.valueOf(uv.size()));
    }


    /**
     * <code>search</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void search(HttpServletRequest request,
    ActionForm form)
    throws Exception {

        HttpSession session = request.getSession();
        UserMgrLocateForm sForm = (UserMgrLocateForm)form;
        APIAccess factory = new APIAccess();
        User userBean = factory.getUserAPI();
        UserDataVector uv = new UserDataVector();

        // Reset the session variables.
        session.setAttribute("Users.found.vector", uv);
        session.setAttribute("Users.found.total", "0");

        UserSearchCriteriaData searchCriteria = UserSearchCriteriaData.createValue();

        String fieldValue = sForm.getSearchField();
        String fieldSearchType = sForm.getSearchType();
        int searchAccountId = sForm.getSearchAccountId();
        String showStoreMSBOnly =  sForm.getShowStoreMSBOnly();


        searchCriteria.setAccountId(searchAccountId);
        searchCriteria.setFirstName(sForm.getFirstName());
        searchCriteria.setLastName(sForm.getLastName());
        searchCriteria.setUserTypeCd(sForm.getUserType());

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        searchCriteria.setStoreId(appUser.getUserStore().getBusEntity().getBusEntityId());

        if(Utility.isSet(showStoreMSBOnly) && showStoreMSBOnly.equalsIgnoreCase("yes")){
        	LinkedList userTypeNames = new LinkedList();
        	//searchCriteria.setUserTypeCd(RefCodeNames.USER_TYPE_CD.MSB);
        	userTypeNames.add(RefCodeNames.USER_TYPE_CD.MSB);
        	userTypeNames.add(RefCodeNames.USER_TYPE_CD.CUSTOMER);

                searchCriteria.setUserTypes(userTypeNames);
        	searchCriteria.setStoreId(appUser.getUserStore().getBusEntity().getBusEntityId());
        } else if (Utility.isSet(showStoreMSBOnly) && !showStoreMSBOnly.equalsIgnoreCase("no")){
          String[] repUserTypes = showStoreMSBOnly.split(",");
          LinkedList userTypeNames = new LinkedList();
          for (int i = 0; i < repUserTypes.length; i++) {
            userTypeNames.add(repUserTypes[i]);
          }
          searchCriteria.setUserTypes(userTypeNames);
        }

        if (fieldSearchType.equals("id")) {
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

        session.setAttribute("Users.found.vector", uv);
        session.setAttribute("Users.found.total",
            String.valueOf(uv.size()));
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
        UserDataVector users =
        (UserDataVector)session.getAttribute("Users.found.vector");
        if (users == null) {
            return;
        }
        //String sortField = request.getParameter("sortField");
        String sortField = request.getParameter("sortBy");
  DisplayListSort.sort(users, sortField);
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
        initSessionVariables(request);
        UserMgrLocateForm sForm = (UserMgrLocateForm)form;
        Enumeration paramsEnum = request.getParameterNames();
        Hashtable paramsTable=new Hashtable();
        while(paramsEnum.hasMoreElements())
        {
            String param=(String) paramsEnum.nextElement();
            if (param.contains("showStoreMSBOnly")){
              sForm.setShowStoreMSBOnly(request.getParameter(param));
            }
            paramsTable.put(param,request.getParameter(param));
        }
        return;
    }


    /**
     *  <code>initSessionVariables</code>, creates the needed session scoped
     *  variables for the search and detail pages.
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@exception  Exception  if an error occurs
     */
    private static void initSessionVariables(HttpServletRequest request)
    throws Exception {
        HttpSession session = request.getSession();

        // Cache the lists needed for users.
        APIAccess factory = new APIAccess();
        ListService lsvc = factory.getListServiceAPI();

        // Set up the user types list.
        if (session.getAttribute("Users.types.vector") == null) {
            RefCdDataVector usertypes =
            lsvc.getRefCodesCollection("USER_TYPE_CD");
            session.setAttribute("Users.types.vector", usertypes);
        }
        // Set up the user status list.
        if (session.getAttribute("Users.status.vector") == null) {
            RefCdDataVector userstatus =
            lsvc.getRefCodesCollection("USER_STATUS_CD");
            session.setAttribute("Users.status.vector", userstatus);
        }

        if (session.getAttribute("Users.locales.vector") == null) {
            RefCdDataVector locales =
            lsvc.getRefCodesCollection("LOCALE_CD");
            session.setAttribute("Users.locales.vector", locales);
        }

        /*if (session.getAttribute("countries.vector") == null) {
            RefCdDataVector countriesv =
            lsvc.getRefCodesCollection("ADDRESS_COUNTRY_CD");
            session.setAttribute("countries.vector", countriesv);
        }*/
  if (session.getAttribute("country.vector") == null) {
    Country countryBean = factory.getCountryAPI();
    CountryDataVector countriesv = countryBean.getAllCountries();
    session.setAttribute("country.vector", countriesv);
  }

        if (session.getAttribute("CustomerService.role.vector") == null) {
            RefCdDataVector crcRolev =
            lsvc.getRefCodesCollection("CUSTOMER_SERVICE_ROLE_CD");
            session.setAttribute("CustomerService.role.vector", crcRolev);
        }

    }



}



