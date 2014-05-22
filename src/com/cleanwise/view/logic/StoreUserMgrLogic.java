/*
 * StoreUserMgrLogic.java
 *
 * Created on May 1, 2006, 2:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.cleanwise.view.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.UniversalDAO;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.EmailClient;
import com.cleanwise.service.api.session.Group;
import com.cleanwise.service.api.session.Language;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.MainDb;
import com.cleanwise.service.api.session.OrderGuide;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.Service;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.PasswordUtil;
import com.cleanwise.service.api.util.QueryCriteria;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.UserRightsTool;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.service.api.value.AccountView;
import com.cleanwise.service.api.value.AccountViewVector;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.AllStoreData;
import com.cleanwise.service.api.value.AllStoreDataVector;
import com.cleanwise.service.api.value.AllUserData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.CountryData;
import com.cleanwise.service.api.value.CountryDataVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.DistributorDataVector;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.GroupData;
import com.cleanwise.service.api.value.GroupDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.LanguageData;
import com.cleanwise.service.api.value.LanguageDataVector;
import com.cleanwise.service.api.value.OrderGuideDescDataVector;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.service.api.value.RefCdData;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.ServiceProviderData;
import com.cleanwise.service.api.value.ServiceProviderDataVector;
import com.cleanwise.service.api.value.SiteView;
import com.cleanwise.service.api.value.SiteViewVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UserAccountRightsView;
import com.cleanwise.service.api.value.UserAccountRightsViewVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.service.api.value.UserSearchCriteriaData;
import com.cleanwise.view.forms.StoreUserMgrForm;
import com.cleanwise.view.forms.UserRightsForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CallerParametersStJohn;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwComparatorFactory;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.SelectableObjects;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.validators.EmailValidator;

/**
 *
 * @author Ykupershmidt
 */

public class StoreUserMgrLogic {
	
	public static Logger log = Logger.getLogger(StoreUserMgrLogic.class);

  /**
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
      StoreUserMgrForm sForm = (StoreUserMgrForm)form;
      acctid = sForm.getSearchAccountId();
    } catch (Exception e) {
      acctid = 0;
    }

    APIAccess factory = new APIAccess();
    User userBean = factory.getUserAPI();
    UserDataVector uv;

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
      UserSearchCriteriaData searchCriteria = UserSearchCriteriaData.createValue();
      if (acctid <= 0){
        searchCriteria.setAccountId(acctid);
      }
      searchCriteria.setStoreIds(appUser.getUserStoreAsIdVector());
      uv = userBean.getUsersCollectionByCriteria(searchCriteria);
    }else{
      // XXX Implement the logic to get the next set.
      if ( acctid <= 0 ) {
        uv = userBean.getUsersCollection(0, 1000);
      } else {
        // Get all the users for the account.
        uv = userBean.getUsersCollectionByBusEntity(acctid, null);
      }
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

    StoreUserMgrForm sForm = (StoreUserMgrForm)form;
    UserDataVector uv = new UserDataVector();


    // Reset the session variables.
    session.setAttribute("Users.found.vector", uv);
    session.setAttribute("Users.found.total", "0");



    String fieldValue = sForm.getSearchField();
    String fieldSearchType = sForm.getSearchType();
    int searchAccountId = sForm.getSearchAccountId();
    String fName = sForm.getFirstName();
    String lName = sForm.getLastName();
    String uType = sForm.getUserType();
    IdVector storeIds = null;
    try{
      if(Utility.isSet(sForm.getSearchStoreId())){
        Integer storeId = new Integer(sForm.getSearchStoreId());
        storeIds = new IdVector();
        storeIds.add(storeId);
      }
    }catch(NumberFormatException e){
      //XXX maybe add action error somewhere?
    }
    AccountDataVector accountDataVector = sForm.getAccountFilter();

    uv = search(request, fieldValue, fieldSearchType, searchAccountId, fName,
                  lName, uType, storeIds,sForm.getSearchShowInactiveFl(), accountDataVector);

    session.setAttribute("Users.found.vector", uv);
    session.setAttribute("Users.found.total",String.valueOf(uv.size()));
  }

  /**
   * <code>search</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static UserDataVector search(HttpServletRequest request,String fieldValue,String fieldSearchType,
          int searchAccountId,String fName,String lName,String uType, IdVector storeIds)
          throws Exception {
    return search(request,fieldValue,fieldSearchType,
          searchAccountId, fName,lName,uType,storeIds,true);
  }

  /**
   * <code>search</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static UserDataVector search(HttpServletRequest request,String fieldValue,String fieldSearchType,
          int searchAccountId,String fName,String lName, String uType, IdVector storeIds,
          boolean showInactiveFl)
          throws Exception {
    return search(request, fieldValue, fieldSearchType, searchAccountId, fName, lName,
    	uType, storeIds, showInactiveFl, null);
  }

    /**
     * <code>search</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static UserDataVector search(HttpServletRequest request,
        String fieldValue, String fieldSearchType, int searchAccountId,
        String fName, String lName, String uType, IdVector storeIds,
        boolean showInactiveFl, AccountDataVector accountDataVector)
        throws Exception {

        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        User userBean = factory.getUserAPI();

        UserSearchCriteriaData searchCriteria = UserSearchCriteriaData.createValue();

        if (searchAccountId > 0){
            searchCriteria.setAccountId(searchAccountId);
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        appUser.getUser();

        searchCriteria.setUserStoreIds(Utility.toIdVector(appUser.getAssociatedStores()));

        String theUserType = appUser.getUser().getUserTypeCd();
        if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(theUserType)) {
            searchCriteria.setStoreIds(appUser.getUserStoreAsIdVector());
        }
        else {
            searchCriteria.setStoreIds(storeIds);
        }

        if (fieldSearchType.equals("id")) {
            searchCriteria.setUserId(fieldValue);
        }
        else if (fieldSearchType.equals("nameContains")) {
            // Lookup by name.  Two assumptions are made: 1) user may
            // have entered the whole name or the initial part of the
            // name; 2) the search is case insensitive.
            searchCriteria.setUserName(fieldValue);
            searchCriteria.setUserNameMatch(User.NAME_CONTAINS_IGNORE_CASE);
        }
        else if (fieldSearchType.equals("nameBegins")) {
            // Lookup by name.  Two assumptions are made: 1) user may
            // have entered the whole name or the initial part of the
            // name; 2) the search is case insensitive.
            searchCriteria.setUserName(fieldValue);
            searchCriteria.setUserNameMatch(User.NAME_BEGINS_WITH_IGNORE_CASE);
        }
        searchCriteria.setFirstName(fName);
        searchCriteria.setLastName(lName);
        if (!Utility.isSet(uType) && !RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(theUserType)) {
            List userTypes = (List) session.getAttribute("Users.types.vector");
            LinkedList userTypeNames = new LinkedList();
            for (Iterator iter = userTypes.iterator(); iter.hasNext();) {
                RefCdData rcD = (RefCdData) iter.next();
                userTypeNames.add(rcD.getValue());
            }
            searchCriteria.setUserTypes(userTypeNames);
        }
        else {
            searchCriteria.setUserTypeCd(uType);
        }
        searchCriteria.setIncludeInactiveFl(showInactiveFl);

        if (accountDataVector != null) {
            IdVector accountIds = new IdVector();
            for (Iterator iter = accountDataVector.iterator(); iter.hasNext();) {
                AccountData accountData = (AccountData) iter.next();
                accountIds.add(new Integer(accountData.getBusEntity().getBusEntityId()));
            }
            if (accountIds.size() > 0) {
                searchCriteria.setAccountIds(accountIds);
            }
        }

        return userBean.getUsersCollectionByCriteria(searchCriteria);
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
    String sortField = request.getParameter("sortField");
    DisplayListSort.sort(users, sortField);
  }

  private static void initStoreOptions(int pUserId, StoreUserMgrForm sForm, HttpSession session, User userBean, APIAccess factory)
  throws Exception{
    if(factory == null){
      factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    }
    if(userBean == null){
      userBean = factory.getUserAPI();
    }
    BusEntityDataVector storeOptions;
    BusEntityDataVector userStores;
        
    if(pUserId == 0){
      userStores = new BusEntityDataVector();
    }else{
      userStores = userBean.getBusEntityCollection(pUserId,RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
    }
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
      Store storeBean = factory.getStoreAPI();
      storeOptions = storeBean.getAllStoresBusEntityData(Store.ORDER_BY_NAME);
    }else{
      storeOptions = appUser.getStores();
    }

    SelectableObjects so = new SelectableObjects(userStores,storeOptions,ClwComparatorFactory.getBusEntityComparator());
    sForm.setStores(so);
  }

  private static void initStoreOptionsMultiStoreDb(int pUserId, StoreUserMgrForm sForm, HttpSession session, User userBean, APIAccess factory)
  throws Exception{
    if(factory == null){
      factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    }
    if(userBean == null){
      userBean = factory.getUserAPI();
    }
	MainDb mainDbEjb = factory.getMainDbAPI();

	CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	
    //BusEntityDataVector storeOptions;
    //BusEntityDataVector userStores;
    
    AllStoreDataVector storeOptionsMainDb;
    AllStoreDataVector userStoresMainDb;
    
    if(pUserId == 0){
      //userStores = new BusEntityDataVector();
      userStoresMainDb = new AllStoreDataVector();
    }else{
      //userStores = userBean.getBusEntityCollection(pUserId,RefCodeNames.BUS_ENTITY_TYPE_CD.STORE); //old staff
      
    	//userStoresMainDb = mainDbEjb.getAllStoreListByUserId(pUserId); // new code for multi store db schemas; it shows ONLY ONE Store (WRONG!!!)
    	
    	AllUserData allUserData = mainDbEjb.getAllUserDataByUserId(pUserId);
    	String pUserName = allUserData.getUserName().trim();
    	String pPassword = allUserData.getPassword().trim();    	
        userStoresMainDb = mainDbEjb.getAllStoreListByUserNameAndPassword(pUserName, pPassword); //should show multiple stores
    }

    if(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
      //Store storeBean = factory.getStoreAPI();
      //storeOptions = storeBean.getAllStoresBusEntityData(Store.ORDER_BY_NAME);
      storeOptionsMainDb = mainDbEjb.getAllStores();	
    }else{
      //storeOptions = appUser.getStores();
      storeOptionsMainDb = appUser.getMultiDbStores();
    }

    //SelectableObjects so = new SelectableObjects(userStores,storeOptions,ClwComparatorFactory.getBusEntityComparator());
    //sForm.setStores(so);
    log.info("initStoreOptionsMultiStoreDb(): userStoresMainDb = " + userStoresMainDb);
    log.info("initStoreOptionsMultiStoreDb(): storeOptionsMainDb = " + storeOptionsMainDb);
    SelectableObjects so = new SelectableObjects(userStoresMainDb, storeOptionsMainDb, ClwComparatorFactory.getBusEntityComparator());
    sForm.setAllStoresMainDb(so);
  }

  /**
   * <code>getUserDetailById</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static UserInfoData getUserDetailById
          (HttpServletRequest request, StoreUserMgrForm pForm, int pUserId)
          throws Exception {

    // Make sure the session variables needed have
    // been set up.  The user detail page may have been
    // accessed without the init() method being called.
    initSessionVariables(request);
    // STORE Admin is not allowed to edit other Store Admin user details if
    // he has not full list of stores avalable for this user.
    IdVector superUserV = (IdVector)request.getSession().getAttribute("SUPER_RIGHT_USERS");
//    log.info("[StoreUserMgrLogic]---> superUserV =" + superUserV);

    boolean isEditable = !(superUserV!=null && superUserV.size()>0 && superUserV.contains(new Integer(pUserId)));
//    log.info("[StoreUserMgrLogic]---> isEditable =" + isEditable);

    pForm.setIsEditableForUserFl(isEditable);

    HttpSession session = request.getSession(true);
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    APIAccess factory = new APIAccess();
    User userBean = factory.getUserAPI();
    Group groupBean = factory.getGroupAPI();
    PropertyService propBean = factory.getPropertyServiceAPI();

    if ("yes".equals(System.getProperty("multi.store.db"))) { //multiple Database schemas 
    	initStoreOptionsMultiStoreDb(pUserId, pForm, session, userBean, factory);
    } else {
        initStoreOptions(pUserId, pForm, session, userBean, factory); //one Database schema
    }

    UserInfoData ud = null;
    String locale = null;
    if(pUserId>0) {
        try {
            ud = userBean.getUserContactForStore(pUserId,appUser.getUserStoreAsIdVector(),true);
            locale = ud.getUserData().getPrefLocaleCd();
        } catch (DataNotFoundException e) {
          log.info("ERROR : "+ e.getMessage());
          pForm=new StoreUserMgrForm() ;
          if ("yes".equals(System.getProperty("multi.store.db"))) { //multiple Database schemas 
          	  initStoreOptionsMultiStoreDb(pUserId, pForm, session, userBean, factory);
          } else {
              initStoreOptions(pUserId, pForm, session, userBean, factory);
          }
          ud = UserInfoData.createValue();
          pForm.setDetail(ud);
          session.setAttribute("STORE_ADMIN_USER_FORM",pForm);
          init(request,pForm);
          throw  new DataNotFoundException(e.getMessage());

        }
    } else {
      ud = UserInfoData.createValue();
      //STJ - 3115 : Need to select store's locale as users locale by default.
      StoreData storeData = appUser.getUserStore();
      locale = storeData.getBusEntity().getLocaleCd().trim();
    }
    
    String pLanguageCode = locale.substring(0,2);
    String pCountryCode = locale.substring(3,locale.trim().length());
    
    Language languageBean = factory.getLanguageAPI();
    LanguageData languageData = languageBean.getLanguageByLanguageCode(pLanguageCode);
    if(languageData!=null && pUserId>0) {
    	ud.setLanguageData(languageData);
    	//ud.getUserData().setLanguageId(languageData.getLanguageId());
    }
    
    Country countryBean = factory.getCountryAPI();
    CountryData countryData = countryBean.getCountryByCountryCode(pCountryCode);
    if(countryData!=null) {
    	ud.setCountryData(countryData);
    	//ud.getUserData().setCountryId(countryData.getCountryId());
    }
    
    if(languageData!=null && countryData!=null && pUserId>0) {
      	String pPrefLocaleCd = languageData.getLanguageCode().trim()+"_"+countryData.getCountryCode().trim();
      	ud.getUserData().setPrefLocaleCd(pPrefLocaleCd);
      }
    
    pForm.setDetail(ud);
    pForm.setGroupsReport(null);
    pForm.setMemberOfGroups(null);
    pForm.setTotalReadOnly(null);
    pForm.setUserIDCode(null);
    setUserCheckboxes(request,pForm);

    session.setAttribute("User.id", String.valueOf(pUserId));

    //handle the user properties
    if(pUserId>0) {
      try{
        Properties updv = propBean.getUserPropertyCollection(pUserId);
        Enumeration enume = updv.propertyNames();
        while(enume.hasMoreElements()){
          String key = (String) enume.nextElement();
          String value = updv.getProperty(key);
          log.info(" pUserId=" + pUserId + " key=" + key + " value=" + value);
          if(key.equals(RefCodeNames.PROPERTY_TYPE_CD.DISTRIBUTION_CENTER_ID)){
            pForm.setDistributionCenterId(value);
          } else if(key.equals(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_HEIGHT)){
            pForm.setManifestLabelHeight(value);
          }else if(key.equals(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_WIDTH)){
            pForm.setManifestLabelWidth(value);
          }else if(key.equals(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_MODE)){
            pForm.setManifestLabelPrintMode(value);
          }else if(key.equals(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_TYPE)){
            log.info(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_TYPE
                    + " pUserId=" + pUserId + " key=" + key + " value=" + value);
            pForm.setManifestLabelType(value);
          }else if (key.equals(RefCodeNames.PROPERTY_TYPE_CD.TOTAL_FIELD_READONLY)) {
            pForm.setTotalReadOnly(value);
          } else if (key.equals(RefCodeNames.PROPERTY_TYPE_CD.USER_ID_CODE)) {
              pForm.setUserIDCode(value);
          }else if(key.equals(RefCodeNames.PROPERTY_TYPE_CD.CORPORATE_USER)){
        	  pForm.setIsCorporateUser(Utility.isTrue(value, false));
          }else if(key.equals(RefCodeNames.PROPERTY_TYPE_CD.RESTRICT_ACCT_INVOICES)){
            	  pForm.setRestrictAcctInvoices(Utility.isTrue(value, false));
          }else if(key.equals(RefCodeNames.PROPERTY_TYPE_CD.RECEIVE_INV_MISSING_EMAIL)){
        	  pForm.setReceiveInvMissingEmail(Utility.isTrue(value, false));
          }else if(key.equals(RefCodeNames.PROPERTY_TYPE_CD.CUTOFF_TIME_EMAIL_REMINDER_CNT)){
        	  pForm.getConfPermForm(0).setCutoffTimeReminderEmailCount(value);
          }
        }
      }catch(Exception e){} //no properties for this user
    }

    return ud;

  }


  /**
   *  <code>getUserDetail</code>
   *
   * @param  request        a <code>HttpServletRequest</code> value
   * @param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static ActionErrors getUserDetail(HttpServletRequest request,ActionForm form) throws Exception {
      ActionErrors ae = new ActionErrors();
      String userIdS = request.getParameter("userId");

      if (null == userIdS) {
          userIdS = (String) request.getSession().getAttribute("User.id");
      }

      if (null == userIdS) {
          userIdS = "0";
      }

      int id = Integer.parseInt(userIdS);
      try {
          getUserDetailById(request, (StoreUserMgrForm) form, id);
      } catch (DataNotFoundException e) {
          ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
      }
      return ae;
  }


    /**
   *  Description of the Method
   *
   *@param  request        Description of Parameter
   *@param  form           Description of Parameter
   *@return                Description of the Returned Value
   *@exception  Exception  Description of Exception
   */
  public static ActionErrors updateCustomerInfo(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors lUpdateErrors = new ActionErrors();

    HttpSession session = request.getSession();
    StoreUserMgrForm sForm = (StoreUserMgrForm) form;

    if (sForm == null) {
      return lUpdateErrors;
    }

    APIAccess factory = new APIAccess();
    User userBean = factory.getUserAPI();

    UserInfoData ud = sForm.getDetail();

    // if new password specified, hash it
    String password = sForm.getPassword();
    if(password==null) password ="";
    password = password.trim();
    String conpassword = sForm.getConfirmPassword();
    if(conpassword==null)conpassword = "";
    conpassword = conpassword.trim();
    if (password.length()>0 || conpassword.length()>0) {
      if(!password.equals(conpassword)){
        String errorMess = "Password and password confirmation do not match";
        lUpdateErrors.add("error",
                new ActionError("error.simpleGenericError",errorMess));
        return lUpdateErrors;
      }
      UserData userD = sForm.getDetail().getUserData();
      String userName = userD.getUserName();
      userD.setPassword(PasswordUtil.getHash(userName, password));
    }

    int userid = ud.getUserData().getUserId();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    String appUserType = appUser.getUser().getUserTypeCd();

    if(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(appUserType) && !Utility.isTrue(sForm.getProfileUpdate())) {
      userBean.updateUserInfo(ud);
    } else {
      userBean.updateUserProfile(ud);
    }
    getUserDetailById( request, (StoreUserMgrForm)form, userid);
    return lUpdateErrors;
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
    return;
  }

  /**
   *  Describe <code>addUser</code> method here.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void addUser(HttpServletRequest request,
          ActionForm form)
          throws Exception {

    HttpSession session = request.getSession();
    //StoreUserMgrForm sForm = new StoreUserMgrForm();
    //initStoreOptions(0, sForm, session, null, null);
    getUserDetailById(request,(StoreUserMgrForm) form, 0);
    //session.setAttribute("USER_DETAIL_FORM", sForm);
  }

  private static class CloneResult {
    public ActionErrors mAppErrors;
    public UserInfoData mUserInfoData;
  }

  public static ActionErrors registerUser
          (HttpServletRequest request,ActionForm form,
          MessageResources pMsgRes
          )
          throws Exception {
    ActionErrors lUpdateErrors = new ActionErrors();

    HttpSession session = request.getSession();
    StoreUserMgrForm sForm = (StoreUserMgrForm) form;


    if (sForm == null) {
      String errorMess = "Missing user information. 2";
      lUpdateErrors.add
              ("error",
              new ActionError("error.simpleGenericError",errorMess));

      return lUpdateErrors;
    }
    APIAccess factory = new APIAccess();
    User userBean = factory.getUserAPI();
    // Check to make sure this user name is not in use.
    // Clone the registration user in use.
    SessionTool st = new SessionTool(request);
    CleanwiseUser cwu = st.getUserData();
    String asUserType = RefCodeNames.USER_TYPE_CD.MSB;
    log.info("clone user " + cwu.getUser());
    UserData fuserdata = (UserData)sForm.getDetail().getUserData().clone();
    AddressData faddrdata = (AddressData)sForm.getDetail().getAddressData().clone();
    EmailData femaildata = (EmailData)sForm.getDetail().getEmailData().clone();
    PhoneData ffaxdata = (PhoneData)sForm.getDetail().getFax().clone();
    PhoneData fmobiledata = (PhoneData)sForm.getDetail().getMobile().clone();
    PhoneData fphonedata = (PhoneData)sForm.getDetail().getPhone().clone();
    boolean validEmailAddr = false;
    String newUserStatus = RefCodeNames.USER_STATUS_CD.LIMITED;

    String toEmail = sForm.getDetail().getEmailData().getEmailAddress();

    if ( null != toEmail && toEmail.trim().length() > 0 ) {
      validEmailAddr = true;
      newUserStatus = RefCodeNames.USER_STATUS_CD.ACTIVE;
    }
    CloneResult cloneRes =
            cloneId(request, form,
            cwu.getUser().getUserId(),
            asUserType, newUserStatus);
    if ( null != cloneRes.mAppErrors &&
            cloneRes.mAppErrors.size() > 0) {
      log.info("clone user ERROR " + cwu);
      return cloneRes.mAppErrors;
    }

    log.info("1RESULT clone user " + sForm);

    // Update the particulars for this user.
    UserData nuserdata = cloneRes.mUserInfoData.getUserData();
    nuserdata.setUserName(fuserdata.getUserName());
    nuserdata.setFirstName(fuserdata.getFirstName());
    nuserdata.setLastName(fuserdata.getLastName());
    nuserdata.setPassword(sForm.getConfirmPassword());
    nuserdata.setUserTypeCd(asUserType);

    sForm.getDetail().setUserData(nuserdata);
    log.info("1RESULT clone fuser " + fuserdata);
    log.info("1RESULT clone nuser " + nuserdata);


    sForm.getDetail().getEmailData().setEmailAddress
            (femaildata.getEmailAddress());

    sForm.getDetail().getFax().setPhoneNum(ffaxdata.getPhoneNum());
    sForm.getDetail().getMobile().setPhoneNum(fmobiledata.getPhoneNum());
    sForm.getDetail().getPhone().setPhoneNum(fphonedata.getPhoneNum());
    sForm.getDetail().getAddressData().setAddress1(faddrdata.getAddress1());
    sForm.getDetail().getAddressData().setAddress2(faddrdata.getAddress2());
    sForm.getDetail().getAddressData().setCity(faddrdata.getCity());
    sForm.getDetail().getAddressData().setCountryCd(faddrdata.getCountryCd());
    sForm.getDetail().getAddressData().setStateProvinceCd(faddrdata.getStateProvinceCd());
    sForm.getDetail().getAddressData().setPostalCode(faddrdata.getPostalCode());



    try {
      lUpdateErrors = updateCustomerInfo(request, sForm);
    } catch (DuplicateNameException dupname) {
      String errorMess = dupname.getMessage();
      log.info("duplicate name error: " + errorMess);
      errorMess = " This username is already in use.  "
              + " Please choose another username and try again";
      lUpdateErrors.add
              ("error",
              new ActionError("error.simpleGenericError",errorMess));

      return lUpdateErrors;

    }

    if ( validEmailAddr ) {
      // Send a registration confirmation email.
      String
              subj = pMsgRes.getMessage("registration.email.subject"),
              mailmsg =
              pMsgRes.getMessage("registration.email.start") +
              " \nUser Name:" + nuserdata.getUserName() + " (case sensitive) " +
              " \nPassword :" + sForm.getConfirmPassword() + " (case sensitive) " +
              "\n" + pMsgRes.getMessage("registration.email.end")
              ;
      try {


        EmailClient emc = factory.getEmailClientAPI();
        emc.send(toEmail,
                emc.getDefaultEmailAddress(),
                subj, mailmsg,
                Constants.EMAIL_FORMAT_PLAIN_TEXT,0,0);
      } catch (Exception e) {
        log.info("Failed to send confirmation email "
                + " toEmail=" + toEmail
                + " subj=" + subj
                + " mailmsg=" + mailmsg );
        e.printStackTrace();
      }
    } else {
      sForm.getDetail().getEmailData().setEmailAddress("");

    }
    return lUpdateErrors;
  }

  public static ActionErrors updateUser
          (HttpServletRequest request,
          ActionForm form)
          throws Exception    	{

  ActionErrors lUpdateErrors = new ActionErrors();

  HttpSession session = request.getSession();
  StoreUserMgrForm sForm = (StoreUserMgrForm) form;
//STJ-3846  
 if(sForm!=null) {
	   sForm.setEmailAddress(sForm.getEmailAddress().trim());
 }

  String selectedManifestLabelType = "",
          manifestLabelPrintMode = "",
          totalReadOnly = "off";
  String userIDCode = sForm.getUserIDCode();
  boolean isCorporateUser = sForm.getIsCorporateUser();
  boolean receiveInvMissingEmail = sForm.getReceiveInvMissingEmail();
  boolean restrictAcctInvoices = sForm.isRestrictAcctInvoices();
  String cutoffTimeRemCnt = sForm.getConfPermForm(0).getCutoffTimeReminderEmailCount();
  
  if ( null != sForm.getManifestLabelType() ) {
    selectedManifestLabelType =  new String
            (sForm.getManifestLabelType() );
  }

  if ( null != sForm.getManifestLabelPrintMode() ) {
    manifestLabelPrintMode = new String
            (sForm.getManifestLabelPrintMode());
  }
  if ( null != request.getParameter("totalReadOnly")) {
    totalReadOnly = request.getParameter("totalReadOnly");
  }
  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

  APIAccess factory = new APIAccess();
  User userBean = factory.getUserAPI();
  Site siteBean = factory.getSiteAPI();
  Account accountBean = factory.getAccountAPI();
  Store storeBean = factory.getStoreAPI();
  PropertyService propBean = factory.getPropertyServiceAPI();
  OrderGuide orderGuideBean = factory.getOrderGuideAPI();
  Group groupBean = factory.getGroupAPI();
  
  int userid = sForm.getDetail().getUserData().getUserId();

  UserInfoData ud = sForm.getDetail();
  if (0 == userid) {
    ud.getUserData().setUserRoleCd("UNKNOWN");
    ud.getUserData().setWorkflowRoleCd("UNKNOWN");
  }

  // Hash password (if updating an existing user, a new password
  // may not have been specified)
  String password = sForm.getPassword();
  if (password != null && !password.equals("")) {
    ud.getUserData().setPassword(PasswordUtil.getHash(ud.getUserData().getUserName(), password));
  }

  String userTypeCd = ud.getUserData().getUserTypeCd();


  //form validation
  if(RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(userTypeCd)) {
    if(!Utility.isSet(sForm.getDistributionCenterId())){
      lUpdateErrors.add("distributionCenterId",new ActionError("variable.empty.error","Distribution Center Id"));
    }
    if(Utility.isSet(sForm.getManifestLabelHeight())){
      try{
        new Integer(sForm.getManifestLabelHeight());
      }catch(NumberFormatException e){
        lUpdateErrors.add("manifestLabelHeight",new ActionError("error.invalidNumber","Manifest Label Height"));
      }
    }
    if(Utility.isSet(sForm.getManifestLabelWidth())){
      try{
        new Integer(sForm.getManifestLabelWidth());
      }catch(NumberFormatException e){
        lUpdateErrors.add("manifestLabelWidth",new ActionError("error.invalidNumber","Manifest Label Width"));
      }
    }
  }
  if (RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userTypeCd) ||
          RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userTypeCd) ||
          RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userTypeCd) ||
          RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userTypeCd)
          ){
    if(sForm.getStores().getCurrentlySelected().isEmpty()){
      lUpdateErrors.add("stores",new ActionError("variable.empty.error","Stores"));
    }
  }
  if (RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(userTypeCd)) {
      if (sForm.getStores().getCurrentlySelected() != null && sForm.getStores().getCurrentlySelected().size() > 0) {
          int userStoreId = ((BusEntityData)sForm.getStores().getCurrentlySelected().get(0)).getBusEntityId();
          int appUserStoreId = appUser.getUserStore().getStoreId();
          if (userStoreId != appUserStoreId) {
              lUpdateErrors.add("stores",
                  new ActionError("user.stores.invalid_selected_store",
                      appUser.getUserStore().getStoreBusinessName().getValue()));
          }
      }
  }

  PropertyService propEjb = factory.getPropertyServiceAPI();
  String userNameMaxSizeStr = null;
  int appUserStoreId = appUser.getUserStore().getStoreId();
  try {
  	userNameMaxSizeStr = propEjb.getBusEntityProperty(appUserStoreId, RefCodeNames.PROPERTY_TYPE_CD.USER_NAME_MAX_SIZE);
  }
  catch (Exception ex) {}
  
  int maxUserNameLength = (userNameMaxSizeStr == null) ? StoreStoreMgrLogic.DEFAULT_USER_NAME_SIZE : new Integer(userNameMaxSizeStr).intValue();
  int userNameLength = ud.getUserData().getUserName().length();
  
  if (userNameLength > maxUserNameLength){
  	lUpdateErrors.add("userName",
              new ActionError("error.simpleGenericError", 
              		"User Name Length (" + userNameLength + ") exceed maximum (" + maxUserNameLength + ") allowed"));
  }

  EmailValidator.validateEmail(request, lUpdateErrors, "Email", sForm.getEmailAddress());
  if(lUpdateErrors.size() > 0){
    return lUpdateErrors;
  }


  UserRightsForm urf = sForm.getBaseUserForm();
  if(urf.isCanApproveOrders()) {
    ud.getUserData().setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER);
    urf.setCanApproveOrders(false);
  } else {
    ud.getUserData().setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
  }
    String rightsStr = urf.getRightsFromOptions();
    if(!Utility.isSet(rightsStr)) {
        if (RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(userTypeCd) ||
            RefCodeNames.USER_TYPE_CD.MSB.equals(userTypeCd) ||
            RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userTypeCd) ||
             RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userTypeCd))
    {
        lUpdateErrors.add("stores",new ActionError("variable.empty.error","Default User Rights"));
        return lUpdateErrors;
    }
    else   rightsStr=new String("UNKNOWN");

    }
    
    //verify user is associated with ACCOUNT groupType if Restrict user to invoices of accounts is set
    
    if(restrictAcctInvoices){
  	  boolean isGrpConf = true;
  	  GroupDataVector gdv = groupBean.getUserGroupsByType(userid,RefCodeNames.GROUP_TYPE_CD.ACCOUNT);
  	  
  	  if(gdv==null || gdv.size()<=0){  //User is not configured to a ACCOUNT_TYPE group
  		  isGrpConf = false;
  	  }else{
  		  boolean isAcctConf = false;
  		  Iterator it = gdv.iterator();
  		  while(it.hasNext()){
  			  int grpId = ((GroupData)it.next()).getGroupId();
  			  BusEntityDataVector beDV = groupBean.getBusEntitysForGroup(grpId, 
  					  RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
  			  if(beDV!=null && beDV.size()>0){
  				  isAcctConf = true;
  			  }
  		  }
  		  
  		  if(!isAcctConf){	//ACCOUNT-TYPE group is empty(does not have any configured accounts)
  			  isGrpConf = false;
  		  }
  	  }
  	  
  	  if(!isGrpConf){
  		  String errorMess = "Cannot restrict user to invoices of accounts. User is not configured " +
  		  "to a group with accounts. Configure user to a group with accounts and retry.";
  		  lUpdateErrors.add("error",
  				  new ActionError("error.simpleGenericError",errorMess));
  		  return lUpdateErrors;
  	  }
    }

  ud.getUserData().setUserRoleCd(rightsStr);



  //log.info(" userid=" + userid + " user type="
  //        + userTypeCd + " saving data" );
  /*
  if (0 == userid) {
    if (RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(userTypeCd)
    || RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(userTypeCd)
    || RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userTypeCd)
    || RefCodeNames.USER_TYPE_CD.REGISTRATION_USER.equals(userTypeCd)

    || RefCodeNames.USER_TYPE_CD.MSB.equals(userTypeCd)
    ) {
      String accountid = sForm.getAccountId();
      if (null != accountid && !"".equals(accountid)) {
        Account accountEjb = factory.getAccountAPI();
        AccountData accountD = null;
        try {
          accountD = accountEjb.getAccount(Integer.parseInt(accountid), 0);

        } catch (Exception e) {
          accountD = null;
        }
        if( null == accountD) {
          lUpdateErrors.add("user", new ActionError("user.bad.account"));
          return lUpdateErrors;
        }
      }
    }
  }
  */
  //log.info(" userid=" + userid + " user type="
  //        + userTypeCd + " saving data DONE" );


  Date now = new Date();
  ud.getUserData().setLastActivityDate(now);
  ud.getUserData().setModDate(now);
  ud.getUserData().setModBy((String) session.getAttribute(Constants.USER_NAME));

  ud.getAddressData().setName1(ud.getUserData().getFirstName());
  ud.getAddressData().setName2(ud.getUserData().getLastName());
  
  //STJ - 3115
  CountryData countryData = null;
  String pCountryCode =  ud.getCountryData().getCountryCode().trim();
  Country countryBean = factory.getCountryAPI();
  countryData = countryBean.getCountryByCountryCode(pCountryCode);
  if(countryData!=null) {
  	ud.setCountryData(countryData);
  	ud.getAddressData().setCountryCd(countryData.getShortDesc());
  	//ud.getUserData().setCountryId(countryData.getCountryId());
  }
  
  LanguageData langugeData = null;
  String lShortDisc = ud.getLanguageData().getShortDesc().trim();
  Language languageBean = factory.getLanguageAPI();
  langugeData = languageBean.getLanguageByShortDesc(lShortDisc);
  if(langugeData!=null) {
  	ud.setLanguageData(langugeData);
  	//ud.getUserData().setLanguageId(langugeData.getLanguageId());
  }
  
  if(langugeData!=null && countryData!=null) {
  	String pPrefLocaleCd = langugeData.getLanguageCode().trim()+"_"+countryData.getCountryCode().trim();
  	ud.getUserData().setPrefLocaleCd(pPrefLocaleCd);
  }
  
  UserInfoData newUserInfo = null;
  try {
    newUserInfo = userBean.addUserInfo(ud);
    userid = newUserInfo.getUserData().getUserId();

    //update/add/remove the user to store associations
    //this configuration is only avaliable for certain user types.  Don't do
    //anything with it if it is null
    if(sForm.getStores() != null){
      IdVector toAdd = Utility.toIdVector(sForm.getStores().getNewlySelected());
      IdVector toDel = Utility.toIdVector(sForm.getStores().getDeselected());
      if (!toDel.isEmpty()) {
          IdVector userStores = new IdVector();
          IdVector userAccounts = new IdVector();
          Iterator toDelIt = toDel.iterator();
          Integer storeId;
          IdVector accounts;
          while (toDelIt.hasNext()) {
              storeId = (Integer)toDelIt.next();
              accounts = userBean.getUserAccountIds(userid, storeId.intValue(), false);
              if (!accounts.isEmpty()) {
                  userStores.add(storeId);
                  userAccounts.addAll(accounts);
              }
          }
          SiteViewVector userSites = new SiteViewVector();
          if (!userAccounts.isEmpty()) {
               userSites = siteBean.getUserSites(0,
                                                 userid,
                                                 0,
                                                 "",
                                                 false,
                                                 "",
                                                 false,
                                                 "",
                                                 "",
                                                 userAccounts,
                                                 false,
                                                 0);
               StringBuffer assocsMessage = new StringBuffer("Accounts: ");
               int range = Math.min(userAccounts.size(), 5);
               for (int i = 0; i < range; i++) {
                   assocsMessage.append("'");
                   assocsMessage.append(accountBean.getAccount(((Integer)userAccounts.get(i)).intValue(), 0).getBusEntity().getShortDesc());
                   assocsMessage.append("'");
               }
               if (range < userAccounts.size()) {
                   assocsMessage.append(" ... ");
               }
               if (!userSites.isEmpty()) {
                   assocsMessage.append(" and Sites: ");
                   range = Math.min(userSites.size(), 5);
                   for (int i = 0; i < range; i++) {
                       assocsMessage.append("'");
                       assocsMessage.append(((SiteView)userSites.get(i)).getName());
                       assocsMessage.append("'");
                   }
                   if (range < userSites.size()) {
                      assocsMessage.append(" ... ");
                   }
               }
               StringBuffer storesMessage = new StringBuffer();
               range = Math.min(userStores.size(), 5);
               for (int i = 0; i < range; i++) {
                   storesMessage.append("'");
                   storesMessage.append(storeBean.getStore(((Integer)userStores.get(i)).intValue()).getBusEntity().getShortDesc());
                   storesMessage.append("'");
               }
               if (range < userStores.size()) {
                   storesMessage.append(" ...");
               }
               lUpdateErrors.add("userAssociations", new ActionError ("user.associations.in.stores", new Object[]{assocsMessage.toString(), storesMessage.toString()}));
               return lUpdateErrors;
          }
      }

      userBean.addBusEntityAssociations(newUserInfo.getUserData().getUserId(), toAdd, RefCodeNames.BUS_ENTITY_TYPE_CD.STORE,appUser.getUserName());
      userBean.removeBusEntityAssociations(newUserInfo.getUserData().getUserId(), toDel);
      sForm.getStores().resetState();
      if (!toAdd.isEmpty()) {
          if (((Integer)toAdd.get(0)).intValue() != ((StoreData)appUser.getUserStore()).getStoreId()) {
              appUser.setUserStore(storeBean.getStore(((Integer)toAdd.get(0)).intValue()));
              session.setAttribute(Constants.APP_USER, appUser);
          }
      }
    }

    // Refresh user info from the database.
    getUserDetailById(request,sForm, userid);

  } catch(DuplicateNameException ne) {
    lUpdateErrors.add("user", new ActionError
            ("user.duplicate.username"));
    return lUpdateErrors;
  }

  log.info(" userid=" + userid + " user type="
          + userTypeCd + " user info saved " );
  session.setAttribute
          ("User.id", String.valueOf
          (newUserInfo.getUserData().getUserId()));
  session.setAttribute
          ("User.type",
          newUserInfo.getUserData().getUserTypeCd());

  int theUserId = userid;
  if (0 == userid) {
    theUserId = newUserInfo.getUserData().getUserId();
  }


  log.info(" userid=" + userid + " user type="
          + userTypeCd + " save properties " );

  //update/add any properties
  Properties userProps = new Properties();
  //add requiered properties.  Check if they are set is done elsewhere as they are requiered
  userProps.put(RefCodeNames.PROPERTY_TYPE_CD.DISTRIBUTION_CENTER_ID,sForm.getDistributionCenterId());

  userProps.put(RefCodeNames.PROPERTY_TYPE_CD.CORPORATE_USER,String.valueOf(isCorporateUser));
  sForm.setIsCorporateUser(isCorporateUser);
  
  userProps.put(RefCodeNames.PROPERTY_TYPE_CD.RESTRICT_ACCT_INVOICES,String.valueOf(restrictAcctInvoices));
  sForm.setRestrictAcctInvoices(restrictAcctInvoices);

  userProps.put(RefCodeNames.PROPERTY_TYPE_CD.RECEIVE_INV_MISSING_EMAIL,String.valueOf(receiveInvMissingEmail));
  sForm.setReceiveInvMissingEmail(receiveInvMissingEmail);

  //add any of the non required properties if they were set
  if(Utility.isSet(sForm.getManifestLabelHeight())){
    userProps.put(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_HEIGHT,sForm.getManifestLabelHeight());
  }
  if(Utility.isSet(sForm.getManifestLabelWidth())){
    userProps.put(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_WIDTH,sForm.getManifestLabelWidth());
  }

  log.info(" userid=" + userid + " label print mode="
          + manifestLabelPrintMode);
  if(Utility.isSet(manifestLabelPrintMode)){
    userProps.put(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_MODE,
            manifestLabelPrintMode
            );
    sForm.setManifestLabelPrintMode(manifestLabelPrintMode);
  }
  log.info(" userid=" + userid + " label type="
          + selectedManifestLabelType);
  if(Utility.isSet(selectedManifestLabelType)){
    userProps.put(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_TYPE,
            selectedManifestLabelType);
    sForm.setManifestLabelType(selectedManifestLabelType);

  }
  if (RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userTypeCd)) {
    userProps.put(RefCodeNames.PROPERTY_TYPE_CD.TOTAL_FIELD_READONLY, totalReadOnly);
    sForm.setTotalReadOnly(totalReadOnly);
  }
  if (Utility.isSet(userIDCode)) {
      userProps.put(RefCodeNames.PROPERTY_TYPE_CD.USER_ID_CODE, userIDCode);
      sForm.setUserIDCode(userIDCode);
  }

  userProps.put(RefCodeNames.PROPERTY_TYPE_CD.CUTOFF_TIME_EMAIL_REMINDER_CNT, cutoffTimeRemCnt);
  sForm.getConfPermForm(0).setCutoffTimeReminderEmailCount(cutoffTimeRemCnt);

  log.info(" userid=" + userid + " user type="
          + userTypeCd + " save properties 2 "
          + " size=" + userProps.size() );
  propBean.setUserPropertyCollection(theUserId, userProps);
  //end update/add any properties
  /*
  if (0 == userid) {
    // for new user, added the account or store assoc
    int newuserid = newUserInfo.getUserData().getUserId();
    userTypeCd = ud.getUserData().getUserTypeCd();
    if (RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(userTypeCd)
    || RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(userTypeCd)
    || RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userTypeCd)
    || RefCodeNames.USER_TYPE_CD.REGISTRATION_USER.equals(userTypeCd)
    || RefCodeNames.USER_TYPE_CD.MSB.equals(userTypeCd)
    ) {
      String accountid = sForm.getAccountId();
      if (null != accountid && !"".equals(accountid)) {
        userBean.addUserAssoc
                (newuserid, Integer.parseInt(accountid),
                RefCodeNames.USER_ASSOC_CD.ACCOUNT);


        if(RefCodeNames.USER_TYPE_CD.
                ACCOUNT_ADMINISTRATOR.equals(userTypeCd)){

          //automatically configure the user for this account
          Site siteBean = factory.getSiteAPI();
          SiteDataVector dv =
                  siteBean.getAllSites(Integer.parseInt(accountid), Site.ORDER_BY_NAME);
          for(int i=0; i<dv.size(); i++){
            SiteData sd = (SiteData)dv.get(i);
            //configure the user for the sites
            userBean.addUserAssoc(newuserid,
                    sd.getBusEntity().getBusEntityId(),
                    RefCodeNames.USER_ASSOC_CD.SITE);
          }
        }
      }
    }
  } else{
    // update the account or store assoc
    userTypeCd = ud.getUserData().getUserTypeCd();
    int res = 0;
    if (RefCodeNames.USER_TYPE_CD.
            ACCOUNT_ADMINISTRATOR.equals(userTypeCd)
            || RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userTypeCd)
            || RefCodeNames.USER_TYPE_CD.REGISTRATION_USER.equals(userTypeCd)
            || RefCodeNames.USER_TYPE_CD.
            CUSTOMER.equals(userTypeCd)
            || RefCodeNames.USER_TYPE_CD.
            MSB.equals(userTypeCd)
            ) {
      String accountid = sForm.getAccountId();
      if (null != accountid && !"".equals(accountid)) {

        BusEntityDataVector bed = userBean.getBusEntityCollection(userid, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
        BusEntityData bd = (BusEntityData)bed.get(0);
        int oldAcc = bd.getBusEntityId();
        int newAcc = Integer.parseInt(accountid);

        res = userBean.updateUserAssoc
                (userid, Integer.parseInt(accountid),
                RefCodeNames.USER_ASSOC_CD.ACCOUNT);

        //check old accountId and if different
        //remove all account and site associations

        if(newAcc != oldAcc){

          userBean.removeUserAssoc(userid, oldAcc);
          SiteDataVector currSites = userBean.getSiteDescCollection(userid, null);
          log.info("removing "+currSites.size()+" sites");
          //now remove the old site assoc's and add the new ones
          for(int i=0; i<currSites.size();i++){
            SiteData s = (SiteData)currSites.get(i);
            int siteId = s.getBusEntity().getBusEntityId();
            userBean.removeUserAssoc(userid, siteId);
          }

          //find user (non-template) orderGuides and remove them
          OrderGuideDescDataVector ogVec =
                  orderGuideBean.getCollectionByUser
                  (userid,OrderGuide.TYPE_BUYER);

          for(int i=0; i<ogVec.size();i++){
            OrderGuideDescData og = (OrderGuideDescData)ogVec.get(i);
            int  ogId = og.getOrderGuideId();
            orderGuideBean.removeOrderGuide(ogId);
          }
        }
      }
    }
  }

  log.info(" userid=" + userid + " user type="
          + userTypeCd + " update DONE " );
  */
  return lUpdateErrors;
  }

  public static ActionErrors updateUserGroups(HttpServletRequest request,
            ActionForm form) throws Exception {
        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        StoreUserMgrForm sForm = (StoreUserMgrForm) form;
        int userid = sForm.getDetail().getUserData().getUserId();
        APIAccess factory = new APIAccess();
        User userBean = factory.getUserAPI();
        // update the members list (convert from string to int,
        // on fail set to null)
        // a null list will be interpreted as unchanged at the bean layer
        Set<Integer> memberOfGroups = new TreeSet<Integer>();
        if (sForm.getMemberOfGroups() != null) {
            Group groupBean = factory.getGroupAPI();
            GroupDataVector sysGropups = groupBean.getAllGroups();
            Map<String, Integer> groupIdByName = new TreeMap<String, Integer>();
            for (int j = 0; sysGropups != null && j < sysGropups.size(); j++) {
                GroupData g = (GroupData) sysGropups.get(j);
                groupIdByName.put(g.getShortDesc().trim(), g.getGroupId());
            }
            // Remove unchecked groups.
            List<String> shownUserGroups = (List<String>) session.getAttribute("Users.groups.list");
            Map groupsUser = groupBean.getGroupsUserIsMemberOf(userid);
            if (shownUserGroups != null) {
                for (String groupName : shownUserGroups) {
                    groupName = groupName.trim();
                    groupsUser.remove(groupIdByName.get(groupName));
                }
            }

            // Add 'checked' but aren't shown groups.
            memberOfGroups.addAll(groupsUser.keySet());

            // Add checked groups.
            String[] membership = sForm.getMemberOfGroups();
            for (int i = 0; i < membership.length; i++) {
                String groupName = membership[i];
                Integer groupId = groupIdByName.get(groupName.trim());
                if (groupId != null) {
                    memberOfGroups.add(groupId);
                }
            }
        }

        try {
            String moduser = (String) session.getAttribute(Constants.USER_NAME);
            userBean.updateUserGroups(userid, new ArrayList<Integer>(memberOfGroups), moduser);
        } catch (Exception e) {
            lUpdateErrors.add("user", new ActionError(e.getMessage()));
        }
        //searchGroupsToConfig(request, form);
        return lUpdateErrors;
    }


  private static ActionErrors _updateReq
          ( HttpServletRequest request,
          ActionForm form, UserData pUserData )
          throws Exception    	{

    ActionErrors lUpdateErrors = new ActionErrors();
    APIAccess factory = new APIAccess();
    User userBean = factory.getUserAPI();
    pUserData.setModBy((String)
    request.getSession().getAttribute
            (Constants.USER_NAME)
            );

    try {
      int userid = pUserData.getUserId();
      userBean.updateUser(pUserData, userid);
      // Refresh user info from the database.
      getUserDetailById(request,(StoreUserMgrForm)form, userid);
    } catch(Exception e) {
      lUpdateErrors.add("user", new ActionError
              (e.getMessage()));
    }
    return lUpdateErrors;
  }

  public static ActionErrors updatePassword
          (HttpServletRequest request,
          ActionForm form)
          throws Exception    	{

    ActionErrors lUpdateErrors = new ActionErrors();

    StoreUserMgrForm sForm = (StoreUserMgrForm) form;

    // Hash password (if updating an existing user, a new password
    // may not have been specified)
    String password = sForm.getPassword(),
            confpassword = sForm.getConfirmPassword();
    if (password == null || password.trim().length() == 0 ) {
      String errorMess = "Please set the password field";
      lUpdateErrors.add
              ("error",
              new ActionError("error.simpleGenericError",errorMess));

      return lUpdateErrors;
    }

    if (confpassword == null || confpassword.trim().length() == 0 ) {
      String errorMess = "Please set the confirm password field";
      lUpdateErrors.add
              ("error",
              new ActionError("error.simpleGenericError",errorMess));

      return lUpdateErrors;
    }
    if ( confpassword.equals(password) == false ) {
      String errorMess = "The confirm password ("
              + confpassword + ") does not match the password ("
              + password + ") value"
              ;
      lUpdateErrors.add
              ("error",
              new ActionError("error.simpleGenericError",errorMess));

      return lUpdateErrors;
    }

    UserData ud = sForm.getDetail().getUserData();
    ud.setPassword(PasswordUtil.getHash
            (ud.getUserName(), password));

    return _updateReq(request, sForm, ud);

  }

  public static ActionErrors updateStatus
          (HttpServletRequest request,
          ActionForm form,
          String pNewUserStatusCd)
          throws Exception    	{

    ActionErrors lUpdateErrors = new ActionErrors();
    StoreUserMgrForm sForm = (StoreUserMgrForm) form;

    log.info("updateStatus 13");
    UserData ud = sForm.getDetail().getUserData();
    log.info("updateStatus 13 ud=" + ud);

    ud.setUserStatusCd(pNewUserStatusCd);

    return _updateReq(request, sForm, ud);

  }



  /**
   *  <code>initSessionVariables</code>, creates the needed session scoped
   *  variables for the search and detail pages.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@exception  Exception  if an error occurs
   */
  public static void initSessionVariables(HttpServletRequest request)
  throws Exception {
    HttpSession session = request.getSession();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    UserData userD = appUser.getUser();
    int userStoreId = appUser.getUserStore().getStoreId();
    // Cache the lists needed for users.
    APIAccess factory = new APIAccess();
    ListService lsvc = factory.getListServiceAPI();
    User userEJB = factory.getUserAPI();
    Group groupBean = factory.getGroupAPI();

    //Sets up the user groups list
    if (session.getAttribute("Users.groups.map") == null) {
      Map userGroups = groupBean.getUserGroups();
      session.setAttribute("Users.groups.map",userGroups);
    }


    // Set up the user types list.
    if (session.getAttribute("Users.types.vector") == null) {
      String theUserType = userD.getUserTypeCd();
      RefCdDataVector usertypes =
              userEJB.getManageableUserTypes(theUserType);
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

    if (session.getAttribute("countries.vector") == null) {
      //RefCdDataVector countriesv =
      //        lsvc.getRefCodesCollection("ADDRESS_COUNTRY_CD");
      //session.setAttribute("countries.vector", countriesv);
      Country countryBean = factory.getCountryAPI();
      CountryDataVector countriesv = countryBean.getAllCountries();
      session.setAttribute("countries.vector", countriesv);
    }
    //STJ - 3115
   	LanguageDataVector languageVector = SessionTool.getUserAvailableLanguages(session);
   	session.setAttribute("languages.vector",languageVector);
    
    if (session.getAttribute("CustomerService.role.vector") == null) {
      RefCdDataVector crcRolev =
              lsvc.getRefCodesCollection("CUSTOMER_SERVICE_ROLE_CD");
      session.setAttribute("CustomerService.role.vector", crcRolev);
    }

    if (session.getAttribute("MANIFEST_LABEL_MODE_CD") == null) {
      RefCdDataVector col =
              lsvc.getRefCodesCollection("MANIFEST_LABEL_MODE_CD");
      session.setAttribute("MANIFEST_LABEL_MODE_CD", col);
    }

    if (session.getAttribute("MANIFEST_LABEL_TYPE_CD") == null) {
      RefCdDataVector col =
              lsvc.getRefCodesCollection("MANIFEST_LABEL_TYPE_CD");
      session.setAttribute("MANIFEST_LABEL_TYPE_CD", col);
    }

    session.removeAttribute("user.distributor.vector");
    session.removeAttribute("user.assoc.site.ids");
    session.removeAttribute("user.siteview.vector");

    log.info("[StoreUserMgrLogic]---> userStoreId =" + userStoreId);
    if (session.getAttribute("SUPER_RIGHT_USERS") == null) {
      IdVector superUserIdV = userEJB.getSuperRightUserCollection(userD, userStoreId);
      session.setAttribute("SUPER_RIGHT_USERS", superUserIdV);
    }

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

    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;
    String confFunc = detailForm.getConfFunction();
    UserInfoData uid = detailForm.getDetail();
    String userType = uid.getUserData().getUserTypeCd();
    if(RefCodeNames.USER_TYPE_CD.SERVICE_PROVIDER.equals(userType)) {
        confFunc = "spConfig";
        detailForm.setConfFunction(confFunc);
    }
   
    if(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userType)) {
    	confFunc = "groupConfig";
    	detailForm.setConfFunction(confFunc);
    }
    if(RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(userType)) {
      confFunc = "distConfig";
      detailForm.setConfFunction(confFunc);
    } else {
      if("distConfig".equals(confFunc) || !Utility.isSet(confFunc)) {
        confFunc = "acctConfig";
        detailForm.setConfFunction(confFunc);
      }
    }
    if("acctConfig".equals(confFunc)) {
      initAcctConfig(request,form);
    } else if("siteConfig".equals(confFunc)) {
      initSiteConfig(request,form);
    } else if("permConfig".equals(confFunc)) {
      initPermConfig(request,form);
    } else if("groupConfig".equals(confFunc)) {
      initGroupConfig(request,form);
    } else if("distConfig".equals(confFunc)) {
      initDistConfig(request,form);
    } else if("catalogConfig".equals(confFunc)) {
      initCatalogConfig(request,form);
    } else if("ogConfig".equals(confFunc)) {
      initOgConfig(request,form);
    } else if("spConfig".equals(confFunc)) {
      initServiceProviderConfig(request,form);
    }
    return;
  }

  /**
   *  <code>initSiteConfig</code> method.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void initSiteConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {

    HttpSession session = request.getSession();
    session.setAttribute("user.siteview.vector", null);

    return;
  }

  /**
   *  <code>initAcctConfig</code> method.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void initAcctConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {

    HttpSession session = request.getSession();
    session.setAttribute("user.account.vector", null);

    return;
  }

  /**
   *  <code>initAcctConfig</code> method.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void initPermConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {

    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;
    detailForm.setConfPermAccountIds(null);
    detailForm.setConfPermAccounts(null);
    detailForm.setConfPermAcctFilterFl(false);
    detailForm.setConfPermissions(null);

    return;
  }

  /**
   *  <code>initServiceProviderConfig</code> method.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void initServiceProviderConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {

    HttpSession session = request.getSession();
    session.setAttribute("user.serviceprovider.vector", null);
    return;
  }

  /**
   *  <code>initDistConfig</code> method.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void initDistConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {

    HttpSession session = request.getSession();
    session.setAttribute("user.distributor.vector", null);
    return;
  }

  /**
   *  <code>initGroupConfig</code> method.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void initGroupConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {
      HttpSession session = request.getSession();
      StoreUserMgrForm detailForm = (StoreUserMgrForm) form;
      detailForm.setGroupsReport(null);
      session.setAttribute("Users.groups.list", null);
      if (true) return;
    APIAccess factory = new APIAccess();
    User userBean = factory.getUserAPI();
    Group groupBean = factory.getGroupAPI();


    UserInfoData uid = detailForm.getDetail();
    int userId = 0;
    if(uid!=null) {
      userId = uid.getUserData().getUserId();
    }
    String[] gnamesArr = null;
    if(userId>0) {
      Map userGroups = groupBean.getGroupsUserIsMemberOf(userId);
      session.setAttribute("Users.admin.memberof.groups.map",userGroups);
      gnamesArr = new String[userGroups.values().size()];
      int ii = 0;
      for(Iterator it = userGroups.values().iterator(); it.hasNext();){
        gnamesArr[ii++] = (String) it.next();
      }
    } else {
      gnamesArr = new String[0];
    }
    detailForm.setGroupsReport(groupBean.getUserGroupsReport(userId));
    detailForm.setMemberOfGroups(gnamesArr);
    return;
  }

  public static void initCatalogConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {

    HttpSession session = request.getSession();
    session.setAttribute("user.catalog.vector", null);
    return;
  }

  public static void initOgConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {

    HttpSession session = request.getSession();
    session.setAttribute("user.og.vector", null);
    return;
  }

  /**
   * <code>initDistributorConfigDisplay</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  /*
  private static void initDistributorConfigDisplay(HttpServletRequest request,ActionForm form)
  throws Exception {
    // Get a reference to the admin facade
    HttpSession session = request.getSession();

    APIAccess factory = new APIAccess();
    User userBean = factory.getUserAPI();

    //this is not the form that is passed in!
    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;
    int userId = detailForm.getDetail().getUserData().getUserId();

    IdVector distIds = new IdVector();

    BusEntityDataVector dists =
            userBean.getBusEntityCollection(userId,RefCodeNames.USER_ASSOC_CD.DISTRIBUTOR);
    log.info("************Searching found configured: " + dists.size());
    String[] selectIds = new String[dists.size()];

    Iterator distI = dists.iterator();
    int i = 0;
    while (distI.hasNext()) {
      Integer id =  new Integer(((BusEntityData)distI.next()).getBusEntityId());
      distIds.add(id);
      selectIds[i++] = new String(id.toString());
    }

    // the currently associated sites are checked/selected
    UserMgrDistributorConfigForm sForm = (UserMgrDistributorConfigForm)form;
    sForm.setSelectIds(selectIds);

    // list of all associated site ids, in the update this
    // will be compared with the selected
    session.setAttribute("user.assoc.distributor.ids", distIds);
  }
*/

  /**
   * <code>initSiteConfigDisplay</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  private static void initSiteConfigDisplay(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    // Get a reference to the admin facade
    HttpSession session = request.getSession();

    APIAccess factory = new APIAccess();
    User userBean = factory.getUserAPI();
    PropertyService propBean = factory.getPropertyServiceAPI();
    
    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;
    int userId = detailForm.getDetail().getUserData().getUserId();

    IdVector siteIds = new IdVector();

    /*BusEntityDataVector sites =
            userBean.getSiteCollection(userId);*/
    //get inactive sites as well
    BusEntityDataVector sites = userBean.getBusEntityCollection(userId,
    		RefCodeNames.BUS_ENTITY_TYPE_CD.SITE,null,0,true);
    String[] selectIds = new String[sites.size()];

    Iterator siteI = sites.iterator();
    int i = 0;
    while (siteI.hasNext()) {
      Integer id =
              new Integer(((BusEntityData)siteI.next()).getBusEntityId());
      siteIds.add(id);
      selectIds[i++] = new String(id.toString());
    }

    // the currently associated sites are checked/selected
    detailForm.setConfSelectIds(selectIds);

    //get default sites 
    String defaultSite = null;
    try{
    	defaultSite = propBean.getUserProperty(userId, RefCodeNames.PROPERTY_TYPE_CD.DEFAULT_SITE);
    }catch(DataNotFoundException exc){
    	log.info("No default site found.");
    }
    //bug # 5159
    detailForm.setDefaultSite(defaultSite);
    
    // list of all associated site ids, in the update this
    // will be compared with the selected
    session.setAttribute("user.assoc.site.ids", siteIds);
  }


  /**
   * <code>getAllSiteConfig</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static void getAllSiteConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {

    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    StoreUserMgrForm userForm = (StoreUserMgrForm) form;
    QueryRequest qr = new QueryRequest();
    qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    String admin = appUser.getUser().getUserName();
    IdVector storeIdV = appUser.getUserStoreAsIdVector();
    qr.filterByStoreIds(storeIdV);

    APIAccess factory = new APIAccess();
    Site siteBean = factory.getSiteAPI();
    SiteViewVector dv =
            siteBean.getSiteCollection(qr);
    session.setAttribute("user.siteview.vector", dv);
    initSiteConfigDisplay(request, form);
  }


  /**
   * <code>searchDistributorConfig</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  /*
  public static void searchDistributorConfig(HttpServletRequest request,
          ActionForm form, boolean fetchAll)
          throws Exception {

    HttpSession session = request.getSession();
    StoreUserMgrForm sForm = (StoreUserMgrForm)form;


    APIAccess factory = new APIAccess();
    Distributor distributorBean = factory.getDistributorAPI();

    // Reset the results vector.
    DistributorDataVector dv = new DistributorDataVector();
    session.setAttribute("user.distributor.vector", dv);

    String searchType = sForm.getConfSearchType();
    String fieldValue = sForm.getConfSearchField();

    if (!Utility.isSet(fieldValue) || fetchAll) {
      dv.addAll(distributorBean.getAllDistributors(Distributor.ORDER_BY_ID));
    }else{
      if (searchType.equals("id") &&
              fieldValue.length() > 0 ) {
        int id = Integer.parseInt(fieldValue);
        dv.add(distributorBean.getDistributor(id));
      } else if (searchType.equals("nameBegins") && fieldValue.length() > 0 ) {
        dv.addAll(distributorBean.getDistributorByName(fieldValue,
                Distributor.BEGINS_WITH_IGNORE_CASE, Distributor.ORDER_BY_ID));
      } else if (searchType.equals("nameContains") && fieldValue.length() > 0) {
        dv.addAll(distributorBean.getDistributorByName(fieldValue,
                Distributor.CONTAINS_IGNORE_CASE, Distributor.ORDER_BY_ID));
      }
    }
    initDistributorConfigDisplay(request, form);
  }
  */
  public static ActionErrors searchAcctToConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    BusEntitySearchCriteria besc = new BusEntitySearchCriteria();
    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;
    if(detailForm.getConifiguredOnlyFl()) {
      ae = searchUserAcctConfig(request,form);
    } else {
      ae = searchAcctConfig(request,form);
    }
    return ae;
  }

  /**
   * <code>searchSiteConfig</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors searchAcctConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    CleanwiseUser adminUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int storeId = adminUser.getUserStore().getBusEntity().getBusEntityId();

    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;

    IdVector accountIds = new IdVector();
    UserInfoData ud = detailForm.getDetail();
    int userId = ud.getUserData().getUserId();


    APIAccess factory = new APIAccess();
    Account acctBean = factory.getAccountAPI();
    User userBean = factory.getUserAPI();

    boolean showInactiveF1 = detailForm.getConfShowInactiveFl();

    String searchType = detailForm.getConfSearchType();
    String fieldValue = detailForm.getConfSearchField();

    AccountViewVector accountVwV = new AccountViewVector();
    HashSet userAcctIdHS = new HashSet();

    if (searchType.equals("id") && Utility.isSet(fieldValue) ) {
      try {
        int id = Integer.parseInt(fieldValue);
        AccountData accountD = acctBean.getAccount(id, storeId);
        AccountView aVw = AccountView.createValue();
        aVw.setStoreId(storeId);
        BusEntityData acctBeD = accountD.getBusEntity();
        aVw.setAcctId(acctBeD.getBusEntityId());
        aVw.setAcctName(acctBeD.getShortDesc());
        aVw.setAcctStatusCd(acctBeD.getBusEntityStatusCd());
        accountVwV.add(aVw);
        String shortDesc = accountD.getBusEntity().getShortDesc();
        BusEntityDataVector beDV = userBean.getBusEntityCollection(userId,
            RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT, shortDesc, 0, showInactiveF1);
        for(Iterator iter = beDV.iterator(); iter.hasNext();) {
          BusEntityData beD = (BusEntityData) iter.next();
          if(beD.getBusEntityId()==id) {
            userAcctIdHS.add(new Integer(id));
            break;
          }
        }
      } catch(Exception exc) {// if error would not select anything
      }
    } else {
      BusEntitySearchCriteria besc = new BusEntitySearchCriteria();
      if(Utility.isSet(fieldValue)) {
        besc.setSearchName(fieldValue);
        if(searchType.equals("nameBegins")) {
          besc.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
        }
        if(searchType.equals("nameContains")) {
          besc.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
        }
      }
      IdVector al = new IdVector();
      al.add(new Integer(storeId));
      besc.setSearchForInactive(showInactiveF1);
      besc.setStoreBusEntityIds(al);
      besc.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
      accountVwV = acctBean.getAccountsViewList(besc);
      besc.addUserId(userId);

      AccountViewVector aVwV = acctBean.getAccountsViewList(besc);

      for(Iterator iter = aVwV.iterator(); iter.hasNext();) {
        AccountView aVw = (AccountView) iter.next();
        userAcctIdHS.add(new Integer(aVw.getAcctId()));
      }
    }
    session.setAttribute("user.account.vector", accountVwV);
    String[] selectIdA = new String[accountVwV.size()];
    String[] displIdA = new String[accountVwV.size()];
    int indexSel = 0;
    int indexDisp = 0;
    for(Iterator iter=accountVwV.iterator(); iter.hasNext();) {
      AccountView aVw = (AccountView) iter.next();
      int id = aVw.getAcctId();
      displIdA[indexDisp++] = String.valueOf(id);;
      if(userAcctIdHS.contains(new Integer(id))){
        selectIdA[indexSel++] = String.valueOf(id);
      }
    }

    detailForm.setConfSelectIds(selectIdA);
    detailForm.setConfDisplayIds(displIdA);
    return ae;
  }

  /**
   * <code>searchUserSiteConfig</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors searchUserAcctConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    CleanwiseUser adminUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int storeId = adminUser.getUserStore().getBusEntity().getBusEntityId();

    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;

    IdVector accountIds = new IdVector();
    UserInfoData ud = detailForm.getDetail();
    int userId = ud.getUserData().getUserId();


    APIAccess factory = new APIAccess();
    Account acctBean = factory.getAccountAPI();
    User userBean = factory.getUserAPI();

    boolean showInactiveF1 = detailForm.getConfShowInactiveFl();
    String searchType = detailForm.getConfSearchType();
    String fieldValue = detailForm.getConfSearchField();

    AccountViewVector accountVwV = new AccountViewVector();

    if (searchType.equals("id") && Utility.isSet(fieldValue) ) {
      try {
        int id = Integer.parseInt(fieldValue);
        AccountData accountD = acctBean.getAccount(id, storeId);
        String shortDesc = accountD.getBusEntity().getShortDesc();
        BusEntityDataVector beDV = userBean.getBusEntityCollection(userId,
            RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT, shortDesc,0,showInactiveF1);
        for(Iterator iter = beDV.iterator(); iter.hasNext();) {
          BusEntityData beD = (BusEntityData) iter.next();
          if(beD.getBusEntityId()==id) {
            AccountView aVw = AccountView.createValue();
            aVw.setStoreId(storeId);
            BusEntityData acctBeD = accountD.getBusEntity();
            aVw.setAcctId(acctBeD.getBusEntityId());
            aVw.setAcctName(acctBeD.getShortDesc());
            aVw.setAcctStatusCd(acctBeD.getBusEntityStatusCd());
            accountVwV.add(aVw);
            break;
          }
        }
      } catch(Exception exc) {// if error would not select anything
      }
    } else {
      BusEntitySearchCriteria besc = new BusEntitySearchCriteria();
      if(Utility.isSet(fieldValue)) {
        besc.setSearchName(fieldValue);
        if(searchType.equals("nameBegins")) {
          besc.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
        }
        if(searchType.equals("nameContains")) {
          besc.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
        }
      }
      IdVector al = new IdVector();
      al.add(new Integer(storeId));
      besc.setStoreBusEntityIds(al);
      besc.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
      besc.addUserId(userId);
      besc.setSearchForInactive(showInactiveF1);

      accountVwV = acctBean.getAccountsViewList(besc);
    }
    session.setAttribute("user.account.vector", accountVwV);
    String[] selectIdA = new String[accountVwV.size()];
    String[] displIdA = new String[accountVwV.size()];
    int indexSel = 0;
    int indexDisp = 0;
    for(Iterator iter=accountVwV.iterator(); iter.hasNext();) {
      AccountView aVw = (AccountView) iter.next();
      int id = aVw.getAcctId();
      displIdA[indexDisp++] = String.valueOf(id);;
      selectIdA[indexSel++] = String.valueOf(id);
    }

    detailForm.setConfSelectIds(selectIdA);
    detailForm.setConfDisplayIds(displIdA);
    return ae;
  }

  public static ActionErrors searchSitesToConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;
    if(detailForm.getConifiguredOnlyFl()) {
      ae = searchUserSiteConfig(request,form);
    } else {
      ae = searchSiteConfig(request,form);
    }
    return ae;
  }

  /**
   * <code>searchSiteConfig</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
	public static ActionErrors searchSiteConfig(HttpServletRequest request, ActionForm form) throws Exception {
		ActionErrors ae = new ActionErrors();
		HttpSession session = request.getSession();
		CleanwiseUser adminUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
		int storeId = adminUser.getUserStore().getBusEntity().getBusEntityId();

		StoreUserMgrForm detailForm = (StoreUserMgrForm) form;

		String fieldValue = detailForm.getConfSearchField();
		String fieldSearchRefNum = detailForm.getSearchRefNum().trim();
		String city = detailForm.getConfCity();
		String state = detailForm.getConfState();

		UserInfoData ud = detailForm.getDetail();
		int userId = ud.getUserData().getUserId();

		APIAccess factory = new APIAccess();
		Site siteBean = factory.getSiteAPI();
		User userBean = factory.getUserAPI();
		boolean showInactiveF1 = detailForm.getConfShowInactiveFl();

		// get all accounts (active and inactive)
		IdVector accountIds = userBean.getUserAccountIds(userId, storeId, true);
		if (accountIds.size() == 0)
			accountIds.add(new Integer(-1));

		// Reset the results vector.
		List<SiteView> dv = new ArrayList<SiteView>();
		session.setAttribute("user.siteview.vector", dv);

		String searchType = detailForm.getConfSearchType();
		String searchRefNumType = detailForm.getSearchRefNumType();
		fieldValue.trim();

		QueryRequest qr = new QueryRequest();
		qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);
		qr.filterByStoreIds(Utility.toIdVector(storeId));

		String f = "";

		if (searchType.equals("id") && fieldValue.length() > 0) {

			Integer id = new Integer(fieldValue);
			qr.filterBySiteId(id.intValue());
			dv = siteBean.getSiteCollection(qr);
		} else if (searchType.equals("nameBegins") && fieldValue.length() > 0) {
			qr.filterByOnlySiteName(fieldValue, QueryRequest.BEGINS_IGNORE_CASE);
		} else if (searchType.equals("nameContains") && fieldValue.length() > 0) {
			qr.filterByOnlySiteName(fieldValue, QueryRequest.CONTAINS_IGNORE_CASE);
		}

		if (fieldSearchRefNum != null && fieldSearchRefNum.length() > 0) {
			if (searchRefNumType.equals("nameBegins")) {
				qr.filterByRefNum(fieldSearchRefNum, QueryRequest.BEGINS_IGNORE_CASE);
			} else if (searchRefNumType.equals("nameContains")) {
				qr.filterByRefNum(fieldSearchRefNum, QueryRequest.CONTAINS_IGNORE_CASE);
			}
		}

		f = city.trim();
		if (f.length() > 0) {
			qr.filterByCity(f, QueryRequest.BEGINS_IGNORE_CASE);
		}

		f = state.trim();
		if (f.length() > 0) {
			qr.filterByState(f, QueryRequest.BEGINS_IGNORE_CASE);
		}

		if (!showInactiveF1) {
			qr.filterBySiteStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
		}

		if (dv.size() == 0) {

			// get all sites associated with this user
			QueryRequest qr1 = qr;
			qr1.filterByUserId(userId);

			log.info("qr1=" + qr1);
			SiteViewVector dv1 = siteBean.getSiteCollection(qr1);
			log.info("got dv1.size=" + dv1.size());

			// get sites for accounts configured to the user
			QueryRequest qr2 = qr;
			ArrayList filters = qr2.getFilters();
			for (int j = 0; j < filters.size(); j++) {
				QueryCriteria qc = (QueryCriteria) filters.get(j);
				if (qc.getFilterByType() == QueryCriteria.USER_ID) {
					filters.remove(qc);
				}
			}
			qr2.filterByAccountIds(accountIds);
			
			List<SiteView> dv2 = null;
			try{
				dv2 = siteBean.getSiteCollection(qr2);
			}catch(SQLException e){
				ae.add("UserErros", new ActionError("user.bad.search.criteria"));
				return ae;
			}

			dv = dv1;

			IdVector dvIds = Utility.toIdVector(dv);
			for (int i = 0; i < dv2.size(); i++) {

				SiteView sview = (SiteView) dv2.get(i);
				if (!dvIds.contains(sview.getId())) {
					dv.add(sview);
				}

			}

		}

		session.setAttribute("user.siteview.vector", dv);

		initSiteConfigDisplay(request, form);
		return ae;
	}

  /**
   * <code>searchUserSiteConfig</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors searchUserSiteConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;
    int userId = detailForm.getDetail().getUserData().getUserId();
    String fieldValue = detailForm.getConfSearchField().trim();
    String fieldSearchRefNum = detailForm.getSearchRefNum().trim();
    String city = detailForm.getConfCity().trim();
    String state = detailForm.getConfState().trim();
    String searchType = detailForm.getConfSearchType();
    String searchRefNumType = detailForm.getSearchRefNumType();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    String admin = appUser.getUser().getUserName();
    //IdVector storeIdV = appUser.getUserStoreAsIdVector();
    int storeId = appUser.getUserStore().getBusEntity().getBusEntityId();

    boolean showInactiveF1 = detailForm.getConfShowInactiveFl();

    APIAccess factory = new APIAccess();
    Site siteBean = factory.getSiteAPI();
    User userBean = factory.getUserAPI();

    //IdVector accountIds = userBean.getUserAccountIds(userId,storeId,false);
    //if(accountIds.size()==0) accountIds.add(new Integer(-1));

    // Reset the results vector.
    List<SiteView> dv = new ArrayList<SiteView>();
    session.setAttribute("user.siteview.vector", dv);


    QueryRequest qr = new QueryRequest();
    qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);
    qr.filterByUserId(userId);
    qr.filterByStoreIds(Utility.toIdVector(storeId));

    if (searchType.equals("id") &&
            fieldValue.length() > 0 ) {
      Integer id = new Integer(fieldValue);
      qr.filterBySiteId(id.intValue());
      dv = siteBean.getSiteCollection(qr);
    } else if (searchType.equals("nameBegins") &&
            fieldValue.length() > 0 ) {
      qr.filterByOnlySiteName(fieldValue,
              QueryRequest.BEGINS_IGNORE_CASE);
    } else if (searchType.equals("nameContains") &&
            fieldValue.length() > 0) {
      qr.filterByOnlySiteName(fieldValue,
              QueryRequest.CONTAINS_IGNORE_CASE);
    }

    if(fieldSearchRefNum !=null && fieldSearchRefNum.length() > 0){
    	if (searchRefNumType.equals("nameBegins"))  {
    		qr.filterByRefNum(fieldSearchRefNum,QueryRequest.BEGINS_IGNORE_CASE);
    	} else if (searchRefNumType.equals("nameContains") ) {
    		qr.filterByRefNum(fieldSearchRefNum,QueryRequest.CONTAINS_IGNORE_CASE);
    	}
    }

    if (city.length() > 0) {
      qr.filterByCity(city, QueryRequest.BEGINS_IGNORE_CASE);
    }

    if (state.length() > 0) {
      qr.filterByState(state, QueryRequest.BEGINS_IGNORE_CASE);
    }
    //qr.filterByStoreIds(storeIdV);

    /* commenting out so that all sites associated with the user can be seen,
 		even if there is some site which belongs to an account not associated with that user*/
    //qr.filterByAccountIds(accountIds);

    if(!showInactiveF1){
    	qr.filterBySiteStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
    }

    dv = siteBean.getSiteCollection(qr);

    session.setAttribute("user.siteview.vector", dv);
    initSiteConfigDisplay(request, form);
    return ae;
  }

  public static ActionErrors searchDistToConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    BusEntitySearchCriteria besc = new BusEntitySearchCriteria();
    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;
    if(detailForm.getConifiguredOnlyFl()) {
      ae = searchUserDistConfig(request,form);
    } else {
      ae = searchDistConfig(request,form);
    }
    return ae;
  }

  public static ActionErrors searchServiceProvidersToConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;
    if(detailForm.getConifiguredOnlyFl()) {
      ae = searchUserServiceProvidersConfig(request, form);
    } else {
      ae = searchServiceProvidersConfig(request, form);
    }
    return ae;
  }

  public static ActionErrors searchServiceProvidersConfig (HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int storeId = appUser.getUserStore().getStoreId();

    APIAccess factory = new APIAccess();
    Service serviceEjb = factory.getServiceAPI();

    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;
    String searchType = detailForm.getConfSearchType();
    String fieldValue = detailForm.getConfSearchField();

    UserInfoData userInfoD = detailForm.getDetail();
    int userId = userInfoD.getUserData().getUserId();

    BusEntitySearchCriteria crit = new BusEntitySearchCriteria();

    IdVector idV = new IdVector();
    idV.add(new Integer(storeId));
    crit.setStoreBusEntityIds(idV);

    if (Utility.isSet(fieldValue) ) {
        if (searchType.equals("id")) {
            IdVector serviceProviderIds = new IdVector();
            serviceProviderIds.add(new Integer(fieldValue));
            crit.setServiceProviderBusEntityIds(serviceProviderIds);
        } else if (searchType.equals("nameBegins")) {
            crit.setSearchName(fieldValue);
            crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
            crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
        } else if (searchType.equals("nameContains")) {
            crit.setSearchName(fieldValue);
            crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
            crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
        }
    }
    ServiceProviderDataVector spDV = serviceEjb.getServiceProviderByCriteria(crit);
    session.setAttribute("user.serviceprovider.vector", spDV);

    crit.addUserId(userId);
    ServiceProviderDataVector  assignedSPDV = serviceEjb.getServiceProviderByCriteria(crit);

    HashSet userServiceProvidersIdHS = new HashSet();
    for(Iterator iter = assignedSPDV.iterator(); iter.hasNext();) {
      ServiceProviderData spD = (ServiceProviderData) iter.next();
      userServiceProvidersIdHS.add(new Integer(spD.getBusEntity().getBusEntityId()));
    }

    String[] selectIdA = new String[spDV.size()];
    String[] displIdA = new String[spDV.size()];
    int indexSel = 0;
    int indexDisp = 0;
    int id;
    for(Iterator iter = spDV.iterator(); iter.hasNext();) {
      ServiceProviderData spD = (ServiceProviderData) iter.next();
      id = spD.getBusEntity().getBusEntityId();
      displIdA[indexDisp++] = String.valueOf(id);
      if (userServiceProvidersIdHS.contains(new Integer(id))) {
        selectIdA[indexSel++] = String.valueOf(id);
      }
    }

    detailForm.setConfSelectIds(selectIdA);
    detailForm.setConfDisplayIds(displIdA);

    return ae;
  }

  public static ActionErrors searchUserServiceProvidersConfig (HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int storeId = appUser.getUserStore().getStoreId();

    APIAccess factory = new APIAccess();
    Service serviceEjb = factory.getServiceAPI();

    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;
    String searchType = detailForm.getConfSearchType();
    String fieldValue = detailForm.getConfSearchField();

    UserInfoData userInfoD = detailForm.getDetail();
    int userId = userInfoD.getUserData().getUserId();

    BusEntitySearchCriteria crit = new BusEntitySearchCriteria();

    IdVector idV = new IdVector();
    idV.add(new Integer(storeId));
    crit.setStoreBusEntityIds(idV);

    if (Utility.isSet(fieldValue) ) {
        if (searchType.equals("id")) {
            IdVector serviceProviderIds = new IdVector();
            serviceProviderIds.add(new Integer(fieldValue));
            crit.setServiceProviderBusEntityIds(serviceProviderIds);
        } else if (searchType.equals("nameBegins")) {
            crit.setSearchName(fieldValue);
            crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
            crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
        } else if (searchType.equals("nameContains")) {
            crit.setSearchName(fieldValue);
            crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
            crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
        }
    }

    crit.addUserId(userId);
    ServiceProviderDataVector  assignedSPDV = serviceEjb.getServiceProviderByCriteria(crit);
    session.setAttribute("user.serviceprovider.vector", assignedSPDV);

    HashSet userServiceProvidersIdHS = new HashSet();
    for(Iterator iter = assignedSPDV.iterator(); iter.hasNext();) {
      ServiceProviderData spD = (ServiceProviderData) iter.next();
      userServiceProvidersIdHS.add(new Integer(spD.getBusEntity().getBusEntityId()));
    }

    String[] selectIdA = new String[assignedSPDV.size()];
    String[] displIdA = new String[assignedSPDV.size()];
    int indexSel = 0;
    int indexDisp = 0;
    int id;
    for(Iterator iter = assignedSPDV.iterator(); iter.hasNext();) {
      ServiceProviderData spD = (ServiceProviderData) iter.next();
      id = spD.getBusEntity().getBusEntityId();
      displIdA[indexDisp++] = String.valueOf(id);
      if (userServiceProvidersIdHS.contains(new Integer(id))) {
        selectIdA[indexSel++] = String.valueOf(id);
      }
    }

    detailForm.setConfSelectIds(selectIdA);
    detailForm.setConfDisplayIds(displIdA);

    return ae;
  }

  /**
   * <code>searchSiteConfig</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors searchDistConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    CleanwiseUser adminUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int storeId = adminUser.getUserStore().getBusEntity().getBusEntityId();

    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;

    IdVector distIds = new IdVector();
    UserInfoData ud = detailForm.getDetail();
    int userId = ud.getUserData().getUserId();


    APIAccess factory = new APIAccess();
    Distributor distBean = factory.getDistributorAPI();
    User userBean = factory.getUserAPI();


    String searchType = detailForm.getConfSearchType();
    String fieldValue = detailForm.getConfSearchField();

    DistributorDataVector distDV = new DistributorDataVector();
    HashSet userDistIdHS = new HashSet();
    BusEntitySearchCriteria besc = new BusEntitySearchCriteria();

    if (searchType.equals("id") && Utility.isSet(fieldValue) ) {
      try {
        int id = Integer.parseInt(fieldValue);
        IdVector distSearchIds = new IdVector();
        distIds.add(new Integer(id));
        besc.setDistributorBusEntityIds(distSearchIds);

        IdVector al = new IdVector();
        al.add(new Integer(storeId));
        besc.setStoreBusEntityIds(al);
      } catch(Exception exc) {// if error would not select anything
      }
    } else {
      if(Utility.isSet(fieldValue)) {
        besc.setSearchName(fieldValue);
        if(searchType.equals("nameBegins")) {
          besc.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
        }
        if(searchType.equals("nameContains")) {
          besc.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
        }
      }
      IdVector al = new IdVector();
      al.add(new Integer(storeId));
      besc.setStoreBusEntityIds(al);
      besc.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);

    }
    distDV = distBean.getDistributorByCriteria(besc);
    session.setAttribute("user.distributor.vector", distDV);
    besc.addUserId(userId);
    DistributorDataVector  dDV = distBean.getDistributorByCriteria(besc);
    for(Iterator iter = dDV.iterator(); iter.hasNext();) {
      DistributorData dD = (DistributorData) iter.next();
      userDistIdHS.add(new Integer(dD.getBusEntity().getBusEntityId()));
    }

    String[] selectIdA = new String[distDV.size()];
    String[] displIdA = new String[distDV.size()];
    int indexSel = 0;
    int indexDisp = 0;
    for(Iterator iter=distDV.iterator(); iter.hasNext();) {
      DistributorData dD = (DistributorData) iter.next();
      int id = dD.getBusEntity().getBusEntityId();
      displIdA[indexDisp++] = String.valueOf(id);;
      if(userDistIdHS.contains(new Integer(id))){
        selectIdA[indexSel++] = String.valueOf(id);
      }
    }

    detailForm.setConfSelectIds(selectIdA);
    detailForm.setConfDisplayIds(displIdA);
    return ae;
  }

  /**
   * <code>searchUserSiteConfig</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors searchUserDistConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    CleanwiseUser adminUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int storeId = adminUser.getUserStore().getBusEntity().getBusEntityId();

    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;

    IdVector distIds = new IdVector();
    UserInfoData ud = detailForm.getDetail();
    int userId = ud.getUserData().getUserId();


    APIAccess factory = new APIAccess();
    Distributor distBean = factory.getDistributorAPI();
    User userBean = factory.getUserAPI();


    String searchType = detailForm.getConfSearchType();
    String fieldValue = detailForm.getConfSearchField();

    DistributorDataVector distDV = new DistributorDataVector();
    BusEntitySearchCriteria besc = new BusEntitySearchCriteria();

    if (searchType.equals("id") && Utility.isSet(fieldValue) ) {
      try {
        int id = Integer.parseInt(fieldValue);
        IdVector distSearchIds = new IdVector();
        distIds.add(new Integer(id));
        besc.setDistributorBusEntityIds(distSearchIds);

        IdVector al = new IdVector();
        al.add(new Integer(storeId));
        besc.setStoreBusEntityIds(al);
      } catch(Exception exc) {// if error would not select anything
      }
    } else {
      if(Utility.isSet(fieldValue)) {
        besc.setSearchName(fieldValue);
        if(searchType.equals("nameBegins")) {
          besc.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
        }
        if(searchType.equals("nameContains")) {
          besc.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
        }
      }
      IdVector al = new IdVector();
      al.add(new Integer(storeId));
      besc.setStoreBusEntityIds(al);
      besc.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);

    }
    besc.addUserId(userId);
    distDV = distBean.getDistributorByCriteria(besc);
    session.setAttribute("user.distributor.vector", distDV);

    String[] selectIdA = new String[distDV.size()];
    String[] displIdA = new String[distDV.size()];
    int indexSel = 0;
    int indexDisp = 0;
    for(Iterator iter=distDV.iterator(); iter.hasNext();) {
      DistributorData dD = (DistributorData) iter.next();
      int id = dD.getBusEntity().getBusEntityId();
      displIdA[indexDisp++] = String.valueOf(id);;
      selectIdA[indexSel++] = String.valueOf(id);
    }

    detailForm.setConfSelectIds(selectIdA);
    detailForm.setConfDisplayIds(displIdA);
    return ae;
  }

  /**
   * <code>searchCatalogConfig</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors searchCatalogConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    CleanwiseUser adminUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int storeId = adminUser.getUserStore().getBusEntity().getBusEntityId();

    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;

    UserInfoData ud = detailForm.getDetail();
    int userId = ud.getUserData().getUserId();


    APIAccess factory = new APIAccess();
    CatalogInformation catInfBean = factory.getCatalogInformationAPI();

    String searchType = detailForm.getConfSearchType();
    String fieldValue = detailForm.getConfSearchField();

    CatalogDataVector catalogDV =
            catInfBean.getUserCatalogs(userId, storeId,fieldValue,searchType);
    session.setAttribute("user.catalog.vector", catalogDV);
    return ae;
  }

  /**
   * <code>searchOgConfig</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */

  public static ActionErrors searchOgConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    CleanwiseUser adminUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int storeId = adminUser.getUserStore().getBusEntity().getBusEntityId();

    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;

    UserInfoData ud = detailForm.getDetail();
    int userId = ud.getUserData().getUserId();


    APIAccess factory = new APIAccess();
    OrderGuide ogBean = factory.getOrderGuideAPI();

    String searchType = detailForm.getConfSearchType();
    String fieldValue = detailForm.getConfSearchField();

    OrderGuideDescDataVector orderGuideDescDV =
        ogBean.getCollectionByUser(userId, storeId, fieldValue, searchType);
    session.setAttribute("user.og.vector", orderGuideDescDV);
    return ae;
  }


  /*
     *Updates the configuration when using the site config screen.
     */
  private static ActionErrors updateConfig
          (String[] displayed,String[] selected,
          String pUserAssociationType,
          APIAccess factory,HttpServletRequest request)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    CleanwiseUser adminUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int storeId = adminUser.getUserStore().getBusEntity().getBusEntityId();

    StoreUserMgrForm detailForm =
            (StoreUserMgrForm)session.getAttribute("STORE_ADMIN_USER_FORM");
    int userId = detailForm.getDetail().getUserData().getUserId();
    String defaultSite = detailForm.getDefaultSite();
    boolean isDefaultSiteConf = false;
    
    if ( request.getParameter("userid") != null ) {
      userId = Integer.parseInt((String)request.getParameter("userid"));
    }

    User userBean = factory.getUserAPI();
    Site siteBean = factory.getSiteAPI();
    PropertyService propertyBean = factory.getPropertyServiceAPI();
    // Looking for two cases:
    // 1. site is selected, but not currently associated - this
    //    means we need to add the association
    // 2. site is not selected, but is currently associated - this
    //    means we need to remove the association

    for (int i = 0; i < displayed.length; i++) {
      String did = displayed[i];
      boolean foundId = false;
      Integer id = new Integer(did);

      for (int j = 0; j < selected.length; j++) {
    	  if(defaultSite!=null){
	    	  if(defaultSite.equals(did)){
	    		  if (did.equals(selected[j])) {
	    			  isDefaultSiteConf = true;
	    		  }
	    	  }
    	  }
    	  
        if (did.equals(selected[j])) {
          foundId = true;
          break;
        }
      }

      if (foundId) {
        // we need to add the association, the selected list
        // has the id, but not on assoc sites list
        userBean.addUserAssoc(userId,
                id.intValue(),
                pUserAssociationType);
        
      } else {
        // we need to remove the association, the selected list
        if(RefCodeNames.USER_ASSOC_CD.ACCOUNT.equals(pUserAssociationType) &&
           !detailForm.getRemoveSiteAssocFl()) {
          IdVector assingedAccountIds =
                  userBean.getUserAccountIds(userId,storeId,true);
          if(!assingedAccountIds.contains(id)) {
            continue; //nothing to remove
          }
          //Check assigned account related sites
          IdVector accountIds = new IdVector();
          accountIds.add(id);


          QueryRequest qr = new QueryRequest();
          qr.setResultLimit(3);
          qr.filterByUserId(userId);
          qr.filterByAccountIds(accountIds);

          SiteViewVector sVwV = siteBean.getSiteCollection(qr);
          if(sVwV.size()>0) {
            String errorMess = "Can't remove account association. Account "+id+" has configured sites: ";
            int ind = 0;
            for(Iterator iter = sVwV.iterator(); iter.hasNext();) {
              SiteView sVw = (SiteView) iter.next();
              errorMess += " <"+sVw.getName()+"> ";
            }
            ae.add("error",new ActionError("error.simpleGenericError",errorMess));
            return ae;
          }
        }
        if(defaultSite != null && id.equals(new Integer(defaultSite).intValue())){
    		String errorMess = "Default site:"+defaultSite+" is not configured to this user.";
    		ae.add("error",new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }
        userBean.removeUserAssoc(userId, id.intValue());
      }
      
    }
    
    if(pUserAssociationType.equalsIgnoreCase(RefCodeNames.USER_ASSOC_CD.SITE)){
    	if(isDefaultSiteConf){
	    	Integer dSite = new Integer(defaultSite);
	    	userBean.updateUserProperty(userId, dSite.intValue(),RefCodeNames.PROPERTY_TYPE_CD.DEFAULT_SITE);
    	}
    }
    
    return ae;
  }


  /**
   * <code>updateDistributorConfig</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static void updateDistributorConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {

    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    User userBean = factory.getUserAPI();

    StoreUserMgrForm sForm = (StoreUserMgrForm)form;

    String[] displayed = sForm.getConfDisplayIds();
    String[] selected = sForm.getConfSelectIds();

    // get list of currently associated ids
    IdVector assocIds =
            (IdVector)session.getAttribute("user.assoc.distributor.ids");

    updateConfig(displayed, selected, RefCodeNames.USER_ASSOC_CD.DISTRIBUTOR,factory,request);

  }

  /**
   * <code>updateServiceProviderConfig</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static void updateServiceProviderConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {

    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

    StoreUserMgrForm sForm = (StoreUserMgrForm)form;

    String[] displayed = sForm.getConfDisplayIds();
    String[] selected = sForm.getConfSelectIds();

    updateConfig(displayed, selected, RefCodeNames.USER_ASSOC_CD.SERVICE_PROVIDER, factory, request);
  }

  /**
   * <code>updateSiteConfig</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors updateSiteConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {
	  ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();

	  APIAccess factory = new APIAccess();
	  User userBean = factory.getUserAPI();

	  StoreUserMgrForm detailForm = (StoreUserMgrForm)form;
	  String[] displayed = detailForm.getConfDisplayIds();
	  String[] selected = detailForm.getConfSelectIds();
    
	  // get list of currently associated site ids
	  IdVector assocSiteIds =
		  (IdVector)session.getAttribute("user.assoc.site.ids");

	  ae = updateConfig(displayed,
			  selected,
			  RefCodeNames.USER_ASSOC_CD.SITE,
			  factory,request);
	  return ae;
  }

  /**
   * <code>updateAccountConfig</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors updateAccountConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    APIAccess factory = new APIAccess();
    User userBean = factory.getUserAPI();

    StoreUserMgrForm detailForm = (StoreUserMgrForm)form;
    AccountViewVector accountVwV =
            (AccountViewVector) session.getAttribute("user.account.vector");

    String[] displayed = new String[accountVwV.size()];
    int index = 0;
    for(Iterator iter = accountVwV.iterator(); iter.hasNext();) {
      AccountView aVw = (AccountView) iter.next();
      int acctId = aVw.getAcctId();
      displayed[index++] = String.valueOf(acctId);
    }
    String[] selected = detailForm.getConfSelectIds();


    ae = updateConfig(displayed,
            selected,
            RefCodeNames.USER_ASSOC_CD.ACCOUNT,
            factory,request);
    /*if(ae.size()>0) {
      searchAcctToConfig(request, form);
      return ae;
    }
    ae = searchAcctToConfig(request, form);*/
    return ae;
  }

/**
   *  <code>sortDistributorConfig</code>
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void sortDistributorConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {

    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    DistributorDataVector data =
            (DistributorDataVector)session.getAttribute("user.distributor.vector");
    if (data == null) {
      return;
    }
    String sortField = request.getParameter("sortField");
    DisplayListSort.sort(data, sortField);

    //initDistributorConfigDisplay(request, form);
  }

  /**
   *  <code>sortServiceProviderConfig</code>
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void sortServiceProviderConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {

    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    ServiceProviderDataVector data =
            (ServiceProviderDataVector)session.getAttribute("user.serviceprovider.vector");
    if (data == null) {
      return;
    }
    String sortField = request.getParameter("sortField");
    DisplayListSort.sort(data, sortField);
  }

/**
   *  <code>sortOgConfig</code>
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void sortOgConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {

    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    OrderGuideDescDataVector data =
            (OrderGuideDescDataVector)session.getAttribute("user.og.vector");
    if (data == null) {
      return;
    }
    String sortField = request.getParameter("sortField");
    DisplayListSort.sort(data, sortField);
  }

/**
   *  <code>sortCatalogConfig</code>
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void sortCatalogConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {

    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    CatalogDataVector data =
            (CatalogDataVector)session.getAttribute("user.catalog.vector");
    if (data == null) {
      return;
    }
    String sortField = request.getParameter("sortField");
    DisplayListSort.sort(data, sortField);
  }

  /**
   *  <code>sortSiteConfig</code>
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void sortSiteConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {

    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    SiteViewVector sites =
            (SiteViewVector)session.getAttribute("user.siteview.vector");
    if (sites == null) {
      return;
    }
    String sortField = request.getParameter("sortField");
    DisplayListSort.sort(sites, sortField);

    initSiteConfigDisplay(request, form);
  }

  /**
   *  <code>sortAcctConfig</code>
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void sortAcctConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {

    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    AccountViewVector data =
            (AccountViewVector)session.getAttribute("user.account.vector");
    if (data == null) {
      return;
    }
    String sortField = request.getParameter("sortField");
    DisplayListSort.sort(data, sortField);
  }

  //-------------------------------------------------------------
  public static ActionErrors clone(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    CloneResult res = cloneId
            (request, form, 0, "",
            RefCodeNames.USER_STATUS_CD.ACTIVE);
    return res.mAppErrors;
  }

  //-------------------------------------------------------------
  private static CloneResult cloneId(HttpServletRequest request,
          ActionForm form,
          int pSourceUserId ,
          String pAsUserType,
          String pUserStatusCd)
          throws Exception {

	log.info("cloneId().Begin");
	
    ActionErrors ae = new ActionErrors();
    CloneResult cRes = new CloneResult();
    cRes.mAppErrors = ae;

    HttpSession session = request.getSession();

    APIAccess factory = new APIAccess();
    User userBean = factory.getUserAPI();

    int templateId = 0;
    if ( pSourceUserId > 0 ) {
      templateId = pSourceUserId;
    } else {
     StoreUserMgrForm sForm = (StoreUserMgrForm) form;
     templateId = sForm.getSelectedId();

     UserDataVector uDV = (UserDataVector) session.getAttribute
              ("Users.found.vector");

      if(templateId<=0 || uDV==null || uDV.size()==0) {
        cRes.mAppErrors.add
                ("error",new ActionError
                ("error.simpleGenericError","No user selected"));
        return cRes;
      }

      boolean foundFl = false;
      for(int ii=0; ii<uDV.size(); ii++) {
        UserData uD = (UserData) uDV.get(ii);
        if(uD.getUserId()==templateId) {
          foundFl = true;
          break;
        }
      }

      if(!foundFl){
        cRes.mAppErrors.add
                ("error",new ActionError
                ("error.simpleGenericError",
                "Template user id is not in the list.")
                );
        return cRes;
      }
    }
    if (!isUserEditable(request, templateId) ){
      cRes.mAppErrors.add ("error",new ActionError("error.simpleGenericError","You are not allowed to create clone of this user"));
      return cRes;
    }

    String admin = (String) session.getAttribute(Constants.USER_NAME);
    
    log.info("templateId = " + templateId + " admin = " + admin + " pAsUserType = " + pAsUserType + " pUserStatusCd = " + pUserStatusCd);
    
    int userId = userBean.createUserClone
            (templateId,admin,pAsUserType,pUserStatusCd);
    
    //Multiple DB Schemas: Begin
    if ("yes".equals(System.getProperty("multi.store.db"))) { //Multiple DB schemas are defined
    	MainDb mainDbEjb = null;
    	try {
    		mainDbEjb = factory.getMainDbAPI();
    	} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
    		cRes.mAppErrors.add("error", new ActionError("error.systemError", "No MainDb API Access"));
    	    return cRes;
    	}
        try {
            mainDbEjb.isAliveUnitMainDb("Main");
        } catch (Exception e) {
            e.printStackTrace();
            String message = ClwI18nUtil.getMessage(request,"admin.errors.multi.noMainDb", null);
            cRes.mAppErrors.add(
                    "error",
                    new ActionError("error.simpleGenericError",message));
            return cRes;
        }
        
        // create clone in the Main DB
        UserData usrData = userBean.getUser(userId);
        log.info("usrData = " + usrData);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER); //get pUserStoreId to pass as param to mainDbEjb.createUserClone()
        int pUserStoreId = appUser.getUserStore().getStoreId();
        log.info("pUserStoreId = " + pUserStoreId); //pUserStoreId = bus_entity_id = storeId
        log.info("admin = " + admin);
        mainDbEjb.createUserClone(usrData, admin, pUserStoreId);
    }
    //Multiple DB Schemas: End	
    
    cRes.mUserInfoData = getUserDetailById(request,(StoreUserMgrForm)form, userId);
    
    log.info("cloneId().End");

    return cRes;
  }

  public static ActionErrors getUserPermissions(HttpServletRequest request,
          ActionForm form)
          throws Exception {

    ActionErrors ae = new ActionErrors();
    StoreUserMgrForm sForm = (StoreUserMgrForm) form;

    HttpSession session = request.getSession();
    CleanwiseUser adminUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int storeId = adminUser.getUserStore().getBusEntity().getBusEntityId();

    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;


    UserInfoData ud = detailForm.getDetail();
    UserData userD = ud.getUserData();
    int userId = userD.getUserId();


    APIAccess factory = new APIAccess();
    Account acctBean = factory.getAccountAPI();
    User userBean = factory.getUserAPI();


    String searchType = detailForm.getConfSearchType();
    String fieldValue = detailForm.getConfSearchField();


    IdVector accountIds = new IdVector();
    boolean filterFl = false;
    if (searchType.equals("id") && Utility.isSet(fieldValue) ) {
      try {
        int id = Integer.parseInt(fieldValue);
        AccountData accountD = acctBean.getAccount(id, storeId);
        accountIds.add(new Integer(id));
        filterFl = true;
      } catch (Exception exc) {
         String errorMess = "No Account found. Account id: "+fieldValue;
         ae.add("error",
            new ActionError("error.simpleGenericError",errorMess));
        return ae;
      }
    } else {
      BusEntitySearchCriteria besc = new BusEntitySearchCriteria();
      if(Utility.isSet(fieldValue)) {
        besc.setSearchName(fieldValue);
        if(searchType.equals("nameBegins")) {
          besc.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
          filterFl = true;
        }
        if(searchType.equals("nameContains")) {
          besc.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
          filterFl = true;
        }
      }
      IdVector al = new IdVector();
      al.add(new Integer(storeId));
      besc.setStoreBusEntityIds(al);
      besc.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
      AccountViewVector accountVwV = acctBean.getAccountsViewList(besc);
      besc.addUserId(userId);
      AccountViewVector aVwV = acctBean.getAccountsViewList(besc);

      for(Iterator iter = aVwV.iterator(); iter.hasNext();) {
        AccountView aVw = (AccountView) iter.next();
        accountIds.add(new Integer(aVw.getAcctId()));
      }
    }


    UserAccountRightsViewVector acctRightsVwV =
            userBean.getUserAccountRights(userId, accountIds);
    if(acctRightsVwV.size()>1) {
      Object[] acctRightsVwA = acctRightsVwV.toArray();
      for(int ii=0; ii<acctRightsVwA.length-1; ii++) {
        boolean exitFl = true;
        for(int jj=0; jj<acctRightsVwA.length-1-ii; jj++) {
          UserAccountRightsView uarVw1 = (UserAccountRightsView) acctRightsVwA[jj];
          UserAccountRightsView uarVw2 = (UserAccountRightsView) acctRightsVwA[jj+1];
          BusEntityData beD1 = uarVw1.getAccountData();
          BusEntityData beD2 = uarVw2.getAccountData();
          int comp = beD1.getShortDesc().compareTo(beD2.getShortDesc());
          if(comp>0) {
            acctRightsVwA[jj] = uarVw2;
            acctRightsVwA[jj+1] = uarVw1;
            exitFl = false;
          }
        }
        if(exitFl) {
          break;
        }
      }
      acctRightsVwV = new UserAccountRightsViewVector();
      for(int ii=0; ii<acctRightsVwA.length; ii++) {
        acctRightsVwV.add(acctRightsVwA[ii]);
      }
    }


    HashMap rightsFormHM = new HashMap();
    HashMap permAccountsHM = new HashMap();
    LinkedList permKeys = new LinkedList();
    int index = 1;
    for(Iterator iter = acctRightsVwV.iterator(); iter.hasNext();) {
      UserAccountRightsView uarVw = (UserAccountRightsView) iter.next();
      BusEntityData acctD = uarVw.getAccountData();
      UserRightsForm urf =
         new UserRightsForm(userD,null,uarVw.getUserSettings().getValue());
      int key = acctD.getBusEntityId();
      if(detailForm.getMergeAccountPermFl()) {
        key = urf.getPermisionsAsInt();
      }
      Integer keyI = new Integer(key);
      UserRightsForm wrkUrf = (UserRightsForm) rightsFormHM.get(keyI);
      if(wrkUrf==null) {
        permKeys.add(keyI);
        rightsFormHM.put(keyI,urf);
      }
      BusEntityDataVector accts = (BusEntityDataVector) permAccountsHM.get(keyI);
      if(accts == null) {
        accts = new BusEntityDataVector();
        permAccountsHM.put(keyI,accts);
      }
      accts.add(acctD);
    }
    sForm.setConfPermAcctFilterFl(filterFl);
    sForm.setConfPermissions(rightsFormHM);
    sForm.setConfPermAccounts(permAccountsHM);
    sForm.setConfPermKeys(permKeys);

    return ae;
  }

  public static ActionErrors updateUserAccountRights
          (HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();

    HttpSession session = request.getSession();
    StoreUserMgrForm sForm = (StoreUserMgrForm) form;
    int userId = sForm.getDetail().getUserData().getUserId();
    APIAccess factory = new APIAccess();
    User userBean = factory.getUserAPI();

    HashMap permHM = sForm.getConfPermissions();
    HashMap acctHM = sForm.getConfPermAccounts();

    Set keys = permHM.keySet();
    for(Iterator iter=keys.iterator(); iter.hasNext();) {
      Integer key = (Integer) iter.next();
      BusEntityDataVector acctDV = (BusEntityDataVector) acctHM.get(key);
      UserRightsForm urf = (UserRightsForm) permHM.get(key);
      String newRights = urf.getRightsFromOptions();
      for(Iterator iter1=acctDV.iterator(); iter1.hasNext();) {
        BusEntityData beD = (BusEntityData) iter1.next();
        int accountId = beD.getBusEntityId();
        userBean.updateUserAccountRights
                (userId, accountId,newRights,
                (String) session.getAttribute(Constants.USER_NAME));
      }
    }

    ae = getUserPermissions(request,form);
    return ae;
  }

  public static ActionErrors setWholeAcctList
          (HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();

    HttpSession session = request.getSession();
    StoreUserMgrForm sForm = (StoreUserMgrForm) form;
    sForm.setShowAllAcctFl(true);
    ae = getUserPermissions(request,form);
    return ae;
  }

//------------------------------------------------------------------
  /**
   *Configures all of the sites for the current request
   */
  /*
  public static ActionErrors configureAllSites(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    User userBean = factory.getUserAPI();

    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;
    UserInfoData ud = detailForm.getDetail();
    int userId = ud.getUserData().getUserId();
    String admin = (String) session.getAttribute(Constants.USER_NAME);
    try {
      BusEntityDataVector accts = userBean.getUserSiteAccounts(userId);
      for ( int i = 0; accts != null && i < accts.size(); i++) {
        BusEntityData thisAcct = ((BusEntityData)accts.get(i));
        int aid = thisAcct.getBusEntityId();
        userBean.assignAllSites(userId,aid,admin);
        List sites = (List) session.getAttribute("user.siteview.vector");
        if(sites!=null && sites.size()>0) {
          ae = searchSitesToConfig(request,form);
        }
      }
    } catch(Exception exc) {
      String errorMess = exc.getMessage();
      int idx = -1;
      int idx1 = -1;
      if(errorMess!=null){
        idx = errorMess.indexOf("@Data error.");
        if(idx>=0) {
          idx += "@Data error.".length();
          idx1 = errorMess.indexOf('@',idx);
        }
      }
      if(idx>=0 && idx1>0) {
        errorMess = errorMess.substring(idx,idx1);
        ae.add("error", new ActionError("error.simpleGenericError",errorMess));
        return ae;
      }
      throw exc;
    }
    return ae;
  }
  */
  public static ActionErrors configureAllAccountSites(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    ae = updateAccountConfig(request,form);
    if(ae.size()>0) {
      return ae;
    }
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    User userBean = factory.getUserAPI();

    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;
    UserInfoData ud = detailForm.getDetail();
    int userId = ud.getUserData().getUserId();
    String admin = (String) session.getAttribute(Constants.USER_NAME);
    try {
      String[] accountIdA = detailForm.getConfSelectIds();
      for ( int ii = 0; ii<accountIdA.length; ii++) {
        try {
          int acctId = Integer.parseInt(accountIdA[ii]);
          if(acctId>0) {
            userBean.assignAllSites(userId,acctId,admin);
          }
        } catch (Exception exc){}
      }
    } catch(Exception exc) {
      String errorMess = exc.getMessage();
      int idx = -1;
      int idx1 = -1;
      if(errorMess!=null){
        idx = errorMess.indexOf("@Data error.");
        if(idx>=0) {
          idx += "@Data error.".length();
          idx1 = errorMess.indexOf('@',idx);
        }
      }
      if(idx>=0 && idx1>0) {
        errorMess = errorMess.substring(idx,idx1);
        ae.add("error", new ActionError("error.simpleGenericError",errorMess));
        return ae;
      }
      throw exc;
    }
    return ae;
  }
  //------------------------------------------------------------------
  /**
   *Configures all of the sites for the current request
   */
  public static ActionErrors configureAllAccounts(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    User userBean = factory.getUserAPI();


    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;
    UserInfoData ud = detailForm.getDetail();
    int userId = ud.getUserData().getUserId();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    String admin = appUser.getUser().getUserName();
    int storeId = appUser.getUserStore().getBusEntity().getBusEntityId();
    try {
      userBean.configureAllAccounts(userId,storeId,admin);
    } catch(Exception exc) {
      String errorMess = exc.getMessage();
      int idx = -1;
      int idx1 = -1;
      if(errorMess!=null){
        idx = errorMess.indexOf("^clw^");
        if(idx>=0) {
          idx1 = errorMess.indexOf("^clw^",idx+5);
        }
      }
      if(idx>=0 && idx1>0) {
        errorMess = errorMess.substring(idx,idx1);
        ae.add("error", new ActionError("error.simpleGenericError",errorMess));
        return ae;
      }
      throw exc;
    }
    ae = searchAcctToConfig(request, form);
    return ae;
  }


  public static ActionErrors  setUserCheckboxes(HttpServletRequest request,
          StoreUserMgrForm detailForm)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    User userBean = factory.getUserAPI();
    // Set the form values.
    UserInfoData uid = detailForm.getDetail();
    if(uid!=null) {
      UserData ud = uid.getUserData();
      //set user rights
      UserRightsTool urt = new UserRightsTool(ud);
      UserRightsForm baseUserForm = detailForm.getBaseUserForm();
      baseUserForm.setShowPrice(urt.getShowPrice());
      baseUserForm.setBrowseOnly(urt.isBrowseOnly());
      baseUserForm.setContractItemsOnly(urt.isUserOnContract());
      baseUserForm.setOnAccount(urt.getOnAccount());
      baseUserForm.setCreditCard(urt.getCreditCardFlag());
      baseUserForm.setOtherPayment(urt.getOtherPaymentFlag());
      baseUserForm.setPoNumRequired(urt.getPoNumRequired());
      baseUserForm.setSalesPresentationOnly(urt.isPresentationOnly());
      baseUserForm.setNoReporting(urt.isNoReporting());
      baseUserForm.setReportingManager(urt.isReportingManager());					//aj
      baseUserForm.setReportingAssignAllAccts(urt.isReportingAssignAllAccts());		//aj
      baseUserForm.setOrderNotificationApprovedEmail(urt.getsEmailOrderApproved());
      baseUserForm.setOrderNotificationModifiedEmail(urt.getsEmailOrderModifications());
      baseUserForm.setOrderNotificationRejectedEmail(urt.getsEmailOrderRejection());
      baseUserForm.setOrderNotificationNeedsApprovalEmail(urt.getsEmailForApproval());
      baseUserForm.setCanEditShipTo(urt.canEditShipTo());
      baseUserForm.setCanEditBillTo(urt.canEditBillTo());
      baseUserForm.setOrderDetailNotificationEmail(urt.getEmailOrderDetailNotification());
      baseUserForm.setOrderNotificationShippedEmail(urt.getOrderNotificationShipped());
      baseUserForm.setWorkOrderCompletedNotification(urt.getWorkOrderCompletedNotification());
      baseUserForm.setWorkOrderAcceptedByProviderNotification(urt.getWorkOrderAcceptedByProviderNotification());
      baseUserForm.setWorkOrderRejectedByProviderNotification(urt.getWorkOrderRejectedByProviderNotification());
      baseUserForm.setCutoffTimeReminderEmail(urt.getCutoffTimeReminderEmail());
      baseUserForm.setPhysicalInvNonComplSiteListingEmail(urt.getPhysicalInvNonComplSiteListingEmail());
      baseUserForm.setPhysicalInvCountsPastDueEmail(urt.getPhysicalInvCountsPastDueEmail());

      if (RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER.equals
              (ud.getWorkflowRoleCd())) {
        baseUserForm.setCanApproveOrders(true);
      }

      detailForm.setCustomerServiceRoleCd("");
      if (urt.isCustServiceApprover()) {
        detailForm.setCustomerServiceRoleCd
                (RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.APPROVER);
      }
      if (urt.isCustServicePublisher()){
        detailForm.setCustomerServiceRoleCd
                (RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.PUBLISHER);
      }
      if (urt.isCustServiceViewer()){
        detailForm.setCustomerServiceRoleCd
                (RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.VIEWER);
      }

    }

    return ae;
  }

  public static ActionErrors searchGroupsToConfig(HttpServletRequest request,
            ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        User userBean = factory.getUserAPI();
        Group groupBean = factory.getGroupAPI();
        StoreUserMgrForm detailForm = (StoreUserMgrForm) form;
        UserInfoData uid = detailForm.getDetail();
        
        final int userId = (uid == null) ? 0 : uid.getUserData().getUserId();
        final List<String> memberOfGroups = (userId > 0) ? new ArrayList<String>(groupBean.getGroupsUserIsMemberOf(userId,true).values())
                : new ArrayList<String>();
        detailForm.setMemberOfGroups(memberOfGroups.toArray(new String[0]));
        SessionTool st = new SessionTool(request);
        String sessionUserTypeCd = st.getUserData().getUser().getUserTypeCd();
        Map userGroups = null;
        if (RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(sessionUserTypeCd)
                || RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(sessionUserTypeCd)) {
            userGroups = groupBean.getUserGroups(true);
        } else if (RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(sessionUserTypeCd)) {
            userGroups = groupBean.getUserGroups(st.getUserData().getUserStoreAsIdVector(),true);
        } else {
            ae.add("error", new ActionError("error.simpleGenericError", "Incorrect user type for this action!"));
            return ae;
        }
        Iterator it = userGroups.values().iterator();
        List<String> resultUserGroups = new ArrayList<String>();
        String searchType = detailForm.getConfSearchType();
        String searchField = detailForm.getConfSearchField();
        boolean addToResult;
        while (it.hasNext()) {
            String groupName = (String) it.next();
            addToResult = true;
            if (detailForm.getConifiguredOnlyFl() == true) {
                addToResult = addToResult && memberOfGroups.contains(groupName);
            }
            if (Utility.isSet(searchField) == true) {
                if (searchType.equals("nameBegins")) {
                    addToResult = addToResult && groupName.toUpperCase().startsWith(searchField.toUpperCase());
                } else if (searchType.equals("nameContains")) {
                    addToResult = addToResult && (groupName.toUpperCase().indexOf(searchField.toUpperCase()) >= 0);
                } else {
                    addToResult = false;
                }
            }
            if (addToResult == true) {
                /// To hide the 'EVERYONE' group for both the Account Administrator and Store Administrator users
                String userName = st.getUserData().getUser().getUserName();
                if (RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(sessionUserTypeCd) ||
                    RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(sessionUserTypeCd)) {
                    if ("EVERYONE".equals(groupName)) {
                        log.info("[StoreUserMgrLogic.searchGroupsToConfig] A 'EVERYONE' group for user " + userName + " was hidden.");
                        continue;
                    }
                }
                resultUserGroups.add(groupName);
            }
        }
        Collections.sort(resultUserGroups, new java.util.Comparator() {
            public int compare(Object o1, Object o2) {
                String s1 = (String) o1;
                String s2 = (String) o2;
                if (s1 == null) {
                    return -1;
                } else if (s2 == null) {
                    return 1;
                } else {
                    return s1.compareToIgnoreCase(s2);
                }
            }
        });
        List userGroupsReport = groupBean.getUserGroupsReport(userId);
        ArrayList resultUserGroupsReport = new ArrayList();
        for (Object item : userGroupsReport) {
            UniversalDAO.dbrow dbRow = (UniversalDAO.dbrow) item;
            UniversalDAO.dbcolumn dbColumn = dbRow.getColumn(1);
            if (resultUserGroups.contains(dbColumn.colVal)) {
                resultUserGroupsReport.add(item);
            }
        }
        detailForm.setGroupsReport(resultUserGroupsReport);
        session.setAttribute("Users.groups.list", resultUserGroups);
        return ae;
    }
    // ------------------------------------------------------------------------
    private static boolean isUserEditable(HttpServletRequest request, int pUserId) throws Exception{
      IdVector superUserIdV = null;
      String SUPER_RIGHT_USERS = "SUPER_RIGHT_USERS";
      HttpSession session = request.getSession();

      if (session.getAttribute(SUPER_RIGHT_USERS) == null) {
        APIAccess factory = new APIAccess();
        User userEJB = factory.getUserAPI();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        UserData userD = appUser.getUser();
        int userStoreId = appUser.getUserStore().getStoreId();
        log.info("[StoreUserMgrLogic]---> userStoreId =" + userStoreId);
        superUserIdV = userEJB.getSuperRightUserCollection(userD, userStoreId);
        session.setAttribute(SUPER_RIGHT_USERS, superUserIdV);
      } else {
        superUserIdV = (IdVector)session.getAttribute(SUPER_RIGHT_USERS);
      }

      log.info("[StoreUserMgrLogic]---> superUserIdV =" + ((superUserIdV!=null) ? superUserIdV.toString(): "NULL"));
      boolean isEditable = !(superUserIdV!=null && superUserIdV.size()>0 && superUserIdV.contains(new Integer(pUserId)));
      return isEditable;
    }
    
    // ------------------------------------------------------------------------
    public static ActionErrors addNewUserStoreAssociationsToMainDb(HttpServletRequest request, StoreUserMgrForm sForm, UserInfoData newUserInfo, Store storeBean, MainDb mainDbEjb) throws Exception {
      log.info("======addNewUserStoreAssociationsToMainDb().Begin");
      
      ActionErrors lUpdateErrors = new ActionErrors();
      HttpSession session = request.getSession();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            
      AllUserData allUserData = new AllUserData();
      allUserData.setUserId(newUserInfo.getUserData().getUserId());
      allUserData.setUserName(newUserInfo.getUserData().getUserName());
      allUserData.setFirstName(newUserInfo.getUserData().getFirstName());
      allUserData.setLastName(newUserInfo.getUserData().getLastName());
      allUserData.setPassword(newUserInfo.getUserData().getPassword());
      allUserData.setUserStatusCd(newUserInfo.getUserData().getUserStatusCd());
      allUserData.setUserTypeCd(newUserInfo.getUserData().getUserTypeCd());
      allUserData.setEffDate(newUserInfo.getUserData().getEffDate());
      allUserData.setExpDate(newUserInfo.getUserData().getExpDate());
//    allUserData.setDefaultStoreId(1); ???  
      log.info("allUserData 1 = " + allUserData);
      try {
          List<AllStoreData> allStoreList = new ArrayList<AllStoreData>();
          IdVector allStoreIdList = new IdVector();
          if (sForm.getAllStoresMainDb() != null) { //Main DB: we want to create a new user and user-store association 
              allStoreIdList = Utility.toIdVector(sForm.getAllStoresMainDb().getCurrentlySelected());
              log.info("allStoreIdList.size() = " + allStoreIdList.size());
              log.info("allStoreIdList = " + allStoreIdList);             
          }                 
          
          Integer allUserId = mainDbEjb.updateAllUser(allUserData, appUser.getUserName());
          log.info("allUserId = " + allUserId);
          List<Integer> IntegerAllStoreIdList = allStoreIdList;
          log.info("IntegerAllStoreIdList = " + IntegerAllStoreIdList);
          mainDbEjb.updateUserStores(allUserId, IntegerAllStoreIdList, appUser.getUserName());          
      } catch (Exception e) {
          e.printStackTrace();          
          lUpdateErrors.add(
          "error",
          new ActionError("error.simpleGenericError","Updating of Main DB failed!"));
          //return lUpdateErrors;
      }
      log.info("======addNewUserStoreAssociationsToMainDb().End");
      
      return lUpdateErrors;
      
  }  
    
  public static ActionErrors addNewUserToMainDb(UserInfoData newUserInfo, MainDb mainDbEjb) throws Exception {  
      log.info("======addNewUserToMainDb().Begin");
      
      ActionErrors lUpdateErrors = new ActionErrors();
      //HttpSession session = request.getSession();
      //CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            
      AllUserData allUserData = new AllUserData();
      allUserData.setUserId(newUserInfo.getUserData().getUserId());
      allUserData.setUserName(newUserInfo.getUserData().getUserName());
      allUserData.setFirstName(newUserInfo.getUserData().getFirstName());
      allUserData.setLastName(newUserInfo.getUserData().getLastName());
      allUserData.setPassword(newUserInfo.getUserData().getPassword());
      allUserData.setUserStatusCd(newUserInfo.getUserData().getUserStatusCd());
      allUserData.setUserTypeCd(newUserInfo.getUserData().getUserTypeCd());
      allUserData.setEffDate(newUserInfo.getUserData().getEffDate());
      allUserData.setExpDate(newUserInfo.getUserData().getExpDate());
//    allUserData.setDefaultStoreId(1); ???  
      log.info("allUserData = " + allUserData);	
      try {
          Integer allUserId = mainDbEjb.updateAllUser(allUserData, newUserInfo.getUserData().getUserName());      
      } catch (Exception e) {
    	  log.info("Updating of ESW_ALL_USER table in the Main DB failed!");
          e.printStackTrace();          
          lUpdateErrors.add(
          "error",
          new ActionError("error.simpleGenericError","User update in the Main DB failed!"));
          //return lUpdateErrors;
      }
      
      log.info("======addNewUserToMainDb().End");
      
      return lUpdateErrors;
  }
  
  /***
  public static ActionErrors addNewUserStoreAssociationForSingleStoreMainDb(HttpServletRequest request, StoreUserMgrForm sForm, UserInfoData newUserInfo, Store storeBean, MainDb mainDbEjb) throws Exception {
      log.info("======addNewUserStoreAssociationForSingleStoreMainDb().Begin");
      
      ActionErrors lUpdateErrors = new ActionErrors();
      HttpSession session = request.getSession();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      
      log.info("======addNewUserStoreAssociationForSingleStoreMainDb().End");
   }
   ***/
  
  public static ActionErrors updateUserMultiStoreDb
  (HttpServletRequest request,
  ActionForm form)
  throws Exception    	{

  ActionErrors lUpdateErrors = new ActionErrors();

  HttpSession session = request.getSession();
  StoreUserMgrForm sForm = (StoreUserMgrForm) form;
//STJ-3846  
 if(sForm!=null) {
	   sForm.setEmailAddress(sForm.getEmailAddress().trim());
 }

  String selectedManifestLabelType = "",
          manifestLabelPrintMode = "",
          totalReadOnly = "off";
  String userIDCode = sForm.getUserIDCode();
  boolean isCorporateUser = sForm.getIsCorporateUser();
  boolean receiveInvMissingEmail = sForm.getReceiveInvMissingEmail();
  boolean restrictAcctInvoices = sForm.isRestrictAcctInvoices();
  String cutoffTimeRemCnt = sForm.getConfPermForm(0).getCutoffTimeReminderEmailCount();
  
  if ( null != sForm.getManifestLabelType() ) {
    selectedManifestLabelType =  new String
            (sForm.getManifestLabelType() );
  }

  if ( null != sForm.getManifestLabelPrintMode() ) {
    manifestLabelPrintMode = new String
            (sForm.getManifestLabelPrintMode());
  }
  if ( null != request.getParameter("totalReadOnly")) {
    totalReadOnly = request.getParameter("totalReadOnly");
  }
  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

  APIAccess factory = new APIAccess();   
  
	MainDb mainDbEjb = null;
  
	// Main DB: Begin
  if ("yes".equals(System.getProperty("multi.store.db"))) {
  	try {
  		mainDbEjb = factory.getMainDbAPI();
  	} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
  		lUpdateErrors.add("error", new ActionError("error.systemError", "No MainDb API Access"));
  	    return lUpdateErrors;
  	}
      try {
          mainDbEjb.isAliveUnitMainDb("Main");
      } catch (Exception e) {
          e.printStackTrace();
          String message = ClwI18nUtil.getMessage(request,"admin.errors.multi.noMainDb", null);
          lUpdateErrors.add(
                  "error",
                  new ActionError("error.simpleGenericError",message));
          return lUpdateErrors;
      }
  }
  // Main DB: End
  
  User userBean = factory.getUserAPI();
  Site siteBean = factory.getSiteAPI();
  Account accountBean = factory.getAccountAPI();
  Store storeBean = factory.getStoreAPI();
  PropertyService propBean = factory.getPropertyServiceAPI();
  OrderGuide orderGuideBean = factory.getOrderGuideAPI();
  Group groupBean = factory.getGroupAPI();
  
  int userid = sForm.getDetail().getUserData().getUserId();
  log.info("updateUser(): userid = " + userid);

  UserInfoData ud = sForm.getDetail();
  if (0 == userid) {
    ud.getUserData().setUserRoleCd("UNKNOWN");
    ud.getUserData().setWorkflowRoleCd("UNKNOWN");
  }

  // Hash password (if updating an existing user, a new password
  // may not have been specified)
  String password = sForm.getPassword();
  if (password != null && !password.equals("")) {
    ud.getUserData().setPassword(PasswordUtil.getHash(ud.getUserData().getUserName(), password));
  }

  String userTypeCd = ud.getUserData().getUserTypeCd();


  //form validation
  if(RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(userTypeCd)) {
    if(!Utility.isSet(sForm.getDistributionCenterId())){
      lUpdateErrors.add("distributionCenterId",new ActionError("variable.empty.error","Distribution Center Id"));
    }
    if(Utility.isSet(sForm.getManifestLabelHeight())){
      try{
        new Integer(sForm.getManifestLabelHeight());
      }catch(NumberFormatException e){
        lUpdateErrors.add("manifestLabelHeight",new ActionError("error.invalidNumber","Manifest Label Height"));
      }
    }
    if(Utility.isSet(sForm.getManifestLabelWidth())){
      try{
        new Integer(sForm.getManifestLabelWidth());
      }catch(NumberFormatException e){
        lUpdateErrors.add("manifestLabelWidth",new ActionError("error.invalidNumber","Manifest Label Width"));
      }
    }
  }
  if (RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userTypeCd) ||
          RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userTypeCd) ||
          RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userTypeCd) ||
          RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userTypeCd)
          ){
  	// Main DB: Begin
      if ("yes".equals(System.getProperty("multi.store.db"))) { //Multiple DB schemas
      	if(sForm.getAllStoresMainDb().getCurrentlySelected().isEmpty()){
              lUpdateErrors.add("stores",new ActionError("variable.empty.error","Stores"));
          }
      } else { //One DB schema (old code)
          if(sForm.getStores().getCurrentlySelected().isEmpty()){
             lUpdateErrors.add("stores",new ActionError("variable.empty.error","Stores"));
          }
      }
      // Main DB: End
  }
  if (RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(userTypeCd)) {
  	if ("yes".equals(System.getProperty("multi.store.db"))) { //Multiple DB schemas
         if (sForm.getAllStoresMainDb().getCurrentlySelected() != null && sForm.getAllStoresMainDb().getCurrentlySelected().size() > 0) {
             int userStoreId = ((AllStoreData)sForm.getAllStoresMainDb().getCurrentlySelected().get(0)).getStoreId();
             int appUserStoreId = appUser.getUserStore().getStoreId();
             if (userStoreId != appUserStoreId) {
                 lUpdateErrors.add("stores",
                  new ActionError("user.stores.invalid_selected_store",
                      appUser.getUserStore().getStoreBusinessName().getValue()));
             }
         }
      } else { //One DB schema (old code)
      	if (sForm.getStores().getCurrentlySelected() != null && sForm.getStores().getCurrentlySelected().size() > 0) {
              int userStoreId = ((BusEntityData)sForm.getStores().getCurrentlySelected().get(0)).getBusEntityId();
              int appUserStoreId = appUser.getUserStore().getStoreId();
              if (userStoreId != appUserStoreId) {
                  lUpdateErrors.add("stores",
                      new ActionError("user.stores.invalid_selected_store",
                          appUser.getUserStore().getStoreBusinessName().getValue()));
              }
      	}
      }
  }

  PropertyService propEjb = factory.getPropertyServiceAPI();
  String userNameMaxSizeStr = null;
  int appUserStoreId = appUser.getUserStore().getStoreId();
  try {
  	userNameMaxSizeStr = propEjb.getBusEntityProperty(appUserStoreId, RefCodeNames.PROPERTY_TYPE_CD.USER_NAME_MAX_SIZE);
  }
  catch (Exception ex) {}
  
  int maxUserNameLength = (userNameMaxSizeStr == null) ? StoreStoreMgrLogic.DEFAULT_USER_NAME_SIZE : new Integer(userNameMaxSizeStr).intValue();
  int userNameLength = ud.getUserData().getUserName().length();
  
  if (userNameLength > maxUserNameLength){
  	lUpdateErrors.add("userName",
              new ActionError("error.simpleGenericError", 
              		"User Name Length (" + userNameLength + ") exceed maximum (" + maxUserNameLength + ") allowed"));
  }

  EmailValidator.validateEmail(request, lUpdateErrors, "Email", sForm.getEmailAddress());
  if(lUpdateErrors.size() > 0){
    return lUpdateErrors;
  }


  UserRightsForm urf = sForm.getBaseUserForm();
  if(urf.isCanApproveOrders()) {
    ud.getUserData().setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER);
    urf.setCanApproveOrders(false);
  } else {
    ud.getUserData().setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
  }
    String rightsStr = urf.getRightsFromOptions();
    if(!Utility.isSet(rightsStr)) {
        if (RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(userTypeCd) ||
            RefCodeNames.USER_TYPE_CD.MSB.equals(userTypeCd) ||
            RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userTypeCd) ||
             RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userTypeCd))
    {
        lUpdateErrors.add("stores",new ActionError("variable.empty.error","Default User Rights"));
        return lUpdateErrors;
    }
    else   rightsStr=new String("UNKNOWN");

    }
    
    //verify user is associated with ACCOUNT groupType if Restrict user to invoices of accounts is set
    
    if(restrictAcctInvoices){
  	  boolean isGrpConf = true;
  	  GroupDataVector gdv = groupBean.getUserGroupsByType(userid,RefCodeNames.GROUP_TYPE_CD.ACCOUNT);
  	  
  	  if(gdv==null || gdv.size()<=0){  //User is not configured to a ACCOUNT_TYPE group
  		  isGrpConf = false;
  	  }else{
  		  boolean isAcctConf = false;
  		  Iterator it = gdv.iterator();
  		  while(it.hasNext()){
  			  int grpId = ((GroupData)it.next()).getGroupId();
  			  BusEntityDataVector beDV = groupBean.getBusEntitysForGroup(grpId, 
  					  RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
  			  if(beDV!=null && beDV.size()>0){
  				  isAcctConf = true;
  			  }
  		  }
  		  
  		  if(!isAcctConf){	//ACCOUNT-TYPE group is empty(does not have any configured accounts)
  			  isGrpConf = false;
  		  }
  	  }
  	  
  	  if(!isGrpConf){
  		  String errorMess = "Cannot restrict user to invoices of accounts. User is not configured " +
  		  "to a group with accounts. Configure user to a group with accounts and retry.";
  		  lUpdateErrors.add("error",
  				  new ActionError("error.simpleGenericError",errorMess));
  		  return lUpdateErrors;
  	  }
    }

  ud.getUserData().setUserRoleCd(rightsStr);



  //log.info(" userid=" + userid + " user type="
  //        + userTypeCd + " saving data" );
  /*
  if (0 == userid) {
    if (RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(userTypeCd)
    || RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(userTypeCd)
    || RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userTypeCd)
    || RefCodeNames.USER_TYPE_CD.REGISTRATION_USER.equals(userTypeCd)

    || RefCodeNames.USER_TYPE_CD.MSB.equals(userTypeCd)
    ) {
      String accountid = sForm.getAccountId();
      if (null != accountid && !"".equals(accountid)) {
        Account accountEjb = factory.getAccountAPI();
        AccountData accountD = null;
        try {
          accountD = accountEjb.getAccount(Integer.parseInt(accountid), 0);

        } catch (Exception e) {
          accountD = null;
        }
        if( null == accountD) {
          lUpdateErrors.add("user", new ActionError("user.bad.account"));
          return lUpdateErrors;
        }
      }
    }
  }
  */
  //log.info(" userid=" + userid + " user type="
  //        + userTypeCd + " saving data DONE" );


  Date now = new Date();
  ud.getUserData().setLastActivityDate(now);
  ud.getUserData().setModDate(now);
  ud.getUserData().setModBy((String) session.getAttribute(Constants.USER_NAME));

  ud.getAddressData().setName1(ud.getUserData().getFirstName());
  ud.getAddressData().setName2(ud.getUserData().getLastName());
  
  //STJ - 3115
  CountryData countryData = null;
  String pCountryCode =  ud.getCountryData().getCountryCode().trim();
  Country countryBean = factory.getCountryAPI();
  countryData = countryBean.getCountryByCountryCode(pCountryCode);
  if(countryData!=null) {
  	ud.setCountryData(countryData);
  	//ud.getUserData().setCountryId(countryData.getCountryId());
  }
  
  LanguageData langugeData = null;
  String lShortDisc = ud.getLanguageData().getShortDesc().trim();
  Language languageBean = factory.getLanguageAPI();
  langugeData = languageBean.getLanguageByShortDesc(lShortDisc);
  if(langugeData!=null) {
  	ud.setLanguageData(langugeData);
  	//ud.getUserData().setLanguageId(langugeData.getLanguageId());
  }
  
  if(langugeData!=null && countryData!=null) {
  	String pPrefLocaleCd = langugeData.getLanguageCode().trim()+"_"+countryData.getCountryCode().trim();
  	ud.getUserData().setPrefLocaleCd(pPrefLocaleCd);
  }
  
  UserInfoData newUserInfo = null;
  String curUserName = ud.getUserData().getUserName(); //remember current userName from the ActionForm
  int curUserId = ud.getUserData().getUserId(); //remember current userId from the ActionForm
  String curUserPwd = ud.getUserData().getPassword(); //remember current userPassword from the ActionForm
  UserInfoData curUserInfo = ud; //remember current object of type UserInfoData
  
  //int curUserId = userId; //remember userId
  try {  // Multiple Stores DB Schemas    
    
  	String curDatasource = mainDbEjb.getCurrentUnit(); //remember current datasource  
  	
  	log.info("updateUser(): curUserInfo1 = " + curUserInfo);
  	
  	if (curUserId == 0) { //new user: add
  		if(sForm.getAllStoresMainDb() != null){
    		//log.info("updateUser(): curDatasource = " + curDatasource);
    		IdVector toAddMainDb = Utility.toIdVector(sForm.getAllStoresMainDb().getNewlySelected()); //allStoreIds
            log.info("updateUser(): toAddMainDb = " + toAddMainDb);
    		
            if (!toAddMainDb.isEmpty()) { 
               // check if the User is assigned to the current Store on the screen(it MUST be assigned on the screen, because I have to land in the current Store)               
               boolean storeAssignedFl = isCurStoreAssigned(toAddMainDb, mainDbEjb, appUser);
               if (!storeAssignedFl) {
            	   String errorMess = "current Store must be assigned";
           		   lUpdateErrors.add("error",
           				  new ActionError("error.simpleGenericError",errorMess));
           		   return lUpdateErrors;
               }
               for (int j=0; j<toAddMainDb.size(); j++) {
                   // create NEW user in a Single Store DB Schema
       		       curUserInfo.getUserData().setUserId(0);//we need it because userId gets a new value after updateUserInfo() invokation
       	              		          
       		       // 1) get storeId based off allStoreId in the Main Db
       		       AllStoreData allStoreD = (AllStoreData) mainDbEjb.getStoreByAllStoreId((Integer) toAddMainDb.get(j));
       		       //AllStoreData allStoreD = (AllStoreData) allStoreDV.get(0);       	         	
       	           String datasource = allStoreD.getDatasource();
       	      	   log.info("updateUser(): datasource = " + datasource);
       	      	   
       	      	   if(datasource != null && datasource.length() != 0) {
       	      		   
       	      		  log.info("updateUser(): curUserInfo2 = " + curUserInfo);
       	      		  
       	      		  lUpdateErrors = createNewUserInDiffentDbTypes(curUserInfo, datasource, appUser, allStoreD, userBean, mainDbEjb);
       	      		  if (lUpdateErrors.size()>0) {
          	        	return lUpdateErrors;
          	          }
       	      	   } // if(datasource != null...
               }
            }
  		}
  	} else {  // curUserId != 0 => existing user => update
  		if(sForm.getAllStoresMainDb() != null){
    		//log.info("updateUser(): curDatasource = " + curDatasource);
    		IdVector toAddMainDb = Utility.toIdVector(sForm.getAllStoresMainDb().getNewlySelected()); //allStoreIds
            log.info("updateUser(): toAddMainDb = " + toAddMainDb);
            IdVector toDelMainDb = Utility.toIdVector(sForm.getAllStoresMainDb().getDeselected()); //allStoreIds
            log.info("updateUser(): toDelMainDb = " + toDelMainDb);      
            IdVector toCheckMainDb = Utility.toIdVector(sForm.getAllStoresMainDb().getCurrentlySelected()); //allStoreIds
            log.info("updateUser(): toCheckMainDb = " + toCheckMainDb); 
            
            if (!toAddMainDb.isEmpty()) { 
                // check if the User is assigned to the current Store on the screen (it MUST be assigned on the screen, because I have to land in the current Store)
                boolean storeAssignedFl = isCurStoreAssigned(toCheckMainDb, mainDbEjb, appUser);            	
                if (!storeAssignedFl) {
             	   String errorMess = "current Store must be assigned";
            		   lUpdateErrors.add("error",
            				  new ActionError("error.simpleGenericError",errorMess));
            		   return lUpdateErrors;
                }
            	
            	for (int j=0; j<toAddMainDb.size(); j++) {            	                  
                   AllStoreData allStoreD = (AllStoreData) mainDbEjb.getStoreByAllStoreId((Integer) toAddMainDb.get(j));
       		       //AllStoreData allStoreD = (AllStoreData) allStoreDV.get(0);       	         	
       	           String datasource = allStoreD.getDatasource();
       	      	   log.info("updateUser(): datasource(add) = " + datasource);
       	      	   
       	      	   if(datasource != null && datasource.length() != 0) {
       	      	       // check by USER_NAME whether this user exists in the Single Store DB Schema (NOT in Main DB)
                	   UserData userD = userBean.getUserByNameStoreDbSchema(curUserName, datasource);
                	   
                	   if(userD != null){ // user exists => update existing user in ALL necessary Databases
                		  
                		  // update existing user in the clw_user table of the Single Store DB Schema 
                		  int storeUserId = userD.getUserId();
              		      curUserInfo.getUserData().setUserId(storeUserId);
              		      newUserInfo = userBean.updateUserInfo(curUserInfo, datasource);
              		      
              		      // update existing user in the Main DB (esw_all_user table)
              		      AllUserData allUserData = new AllUserData();
                          allUserData.setUserId(curUserInfo.getUserData().getUserId());
                          allUserData.setUserName(curUserInfo.getUserData().getUserName());
                          allUserData.setFirstName(curUserInfo.getUserData().getFirstName());
                          allUserData.setLastName(curUserInfo.getUserData().getLastName());
                          allUserData.setPassword(curUserInfo.getUserData().getPassword());
                          allUserData.setUserStatusCd(curUserInfo.getUserData().getUserStatusCd());
                          allUserData.setUserTypeCd(curUserInfo.getUserData().getUserTypeCd());
                          allUserData.setEffDate(curUserInfo.getUserData().getEffDate());
                          allUserData.setExpDate(curUserInfo.getUserData().getExpDate());
                          Integer allUserId = mainDbEjb.updateAllUser(allUserData, curUserInfo.getUserData().getUserName());
                	   
                	   } else { //user DOES NOT exist => ADD new user to the Databases
                		   curUserInfo.getUserData().setUserId(0);//we need it because userId gets a new value after updateUserInfo() invokation 
                		   lUpdateErrors = createNewUserInDiffentDbTypes(curUserInfo, datasource, appUser, allStoreD, userBean, mainDbEjb);                	       
                	   }
       	      	   }
               } //for
            } //if
            if (!toDelMainDb.isEmpty()) { 
                for (int jj=0; jj<toDelMainDb.size(); jj++) { 
                	IdVector elementToDel = new IdVector(); 
                	elementToDel.add(toDelMainDb.get(jj));
                    AllStoreDataVector allStoreDVDel = (AllStoreDataVector) mainDbEjb.getAllStoreDataVectorByAllStoreIds((IdVector) elementToDel);
                    log.info("updateUser(): allStoreDVDel.size() = " + allStoreDVDel.size());
                	//AllStoreData allStoreD = mainDbEjb.getAllStoreDataByAllStoreId(((Integer) toDelMainDb.get(jj)).intValue() )
                	AllStoreData allStoreD = (AllStoreData) allStoreDVDel.get(0);       	         	
        	        String datasourceDel = allStoreD.getDatasource();
        	      	log.info("updateUser(): datasourceDel(delete) = " + datasourceDel);
        	      	   
        	      	if(datasourceDel != null && datasourceDel.length() != 0) {
        	      	   // check by USER_NAME does this user exist in the Single Store DB Schema (NOT in the Main DB)
                 	   UserData userD = userBean.getUserByNameStoreDbSchema(curUserName, datasourceDel);
                 	   if(userD == null){
                 		  //User DOES NOT exist => delete nothing from the Single Store DB Schema
                 		   
                 		  //it is possible that this user still exists in the Main Db => first, find User in the Main DB        		  
                 		  AllUserData allUserD = mainDbEjb.getAllUserDataByUserNamePasswordAndStoreId(curUserName, curUserPwd, allStoreD.getStoreId()); 
                 		  log.info("updateUser(): allUserD = " + allUserD);
                 		  //then delete appropriate records(s) from the Main DB
                 		  if (allUserD != null) {
                              log.info("updateUser(): elementToDel = " + elementToDel);
                              log.info("updateUser(): allUserD.getUserId() = " + allUserD.getUserId());
               	              mainDbEjb.removeUserStoreAssociationsByUserIdAndAllStoreIds(allUserD.getUserId(), elementToDel);
                 		      mainDbEjb.removeAllUserData(allUserD.getAllUserId());
                 		  }
                 	   } else { //user exists => delete existing user from ALL necessary Databases
                 		  //delete appropriate records(s) from the Main DB
               	          mainDbEjb.removeUserStoreAssociationsByUserIdAndAllStoreIds(userD.getUserId(), elementToDel);
               	
               	          //delete BusEntityAssociation(s) for a specific User from a Single Store DB Schema 
               	          userBean.removeBusEntityAssociationsMultiStoreDb(userD.getUserId(), (AllStoreDataVector) allStoreDVDel);
                 	   } //endif
                 	} //endif
        	    } //end for
            } //endif
            if (toDelMainDb.isEmpty() && toAddMainDb.isEmpty()) {
            	
               //We didn't create or remove user-store associations, becuase user-store associations were not changed on the screen
               //update user info in the Single Store DB Schema (clw_user table; what about properties ?)
               newUserInfo = userBean.updateUserInfo(curUserInfo, curDatasource);
               
               //update Main DB (esw_all_user table) 
               AllUserData allUserData = new AllUserData();
               allUserData.setUserId(curUserInfo.getUserData().getUserId());
               allUserData.setUserName(curUserInfo.getUserData().getUserName());
               allUserData.setFirstName(curUserInfo.getUserData().getFirstName());
               allUserData.setLastName(curUserInfo.getUserData().getLastName());
               allUserData.setPassword(curUserInfo.getUserData().getPassword());
               allUserData.setUserStatusCd(curUserInfo.getUserData().getUserStatusCd());
               allUserData.setUserTypeCd(curUserInfo.getUserData().getUserTypeCd());
               allUserData.setEffDate(curUserInfo.getUserData().getEffDate());
               allUserData.setExpDate(curUserInfo.getUserData().getExpDate());
               Integer allUserId = mainDbEjb.updateAllUser(allUserData, curUserInfo.getUserData().getUserName());
               
               //update Main DB (esw_user_store table)
                              
               AllStoreData asd = mainDbEjb.getAllStoreDataByStoreId(appUser.getUserStore().getStoreId());
               ArrayList<Integer> pAllStoreIds = new ArrayList<Integer>();
               pAllStoreIds.add(new Integer(asd.getAllStoreId()));
               mainDbEjb.updateUserStores(allUserId, pAllStoreIds, curUserInfo.getUserData().getUserName());
            }
  		} //endif
  	} //endif (curUserId == 0) 
  	
    //connect to the Single Store DB Schema using current datasource
  	CallerParametersStJohn.JaasLogin(request, curDatasource, true);
      
  	sForm.getAllStoresMainDb().resetState(); 
  	/*** Do I need it; if I do, what does it mean ???
      if (!toAddMainDb.isEmpty()) { 
          if (((Integer)toAddMainDb.get(0)).intValue() != ((StoreData)appUser.getUserStore()).getStoreId()) {
              appUser.setUserStore(storeBean.getStore(((Integer)toAddMainDb.get(0)).intValue()));
              session.setAttribute(Constants.APP_USER, appUser);
          }
      }
    ***/
  	
  	// Refresh user info. from the Single Store DB Schema:
  	// we have to use curUserName to get userId from the current Store DB Schema
  	UserData userData = userBean.getUserByNameStoreDbSchema(curUserName, curDatasource);
  	int refreshUserId = userData.getUserId();
  	log.info("Refresh user info. from the Single Store DB Schema");
    getUserDetailById(request,sForm, refreshUserId);
      
  } catch(DuplicateNameException ne) {
    lUpdateErrors.add("user", new ActionError
            ("user.duplicate.username"));
    return lUpdateErrors;
  }

  log.info(" userid=" + userid + " user type="
          + userTypeCd + " user info saved " );
  /***    
  session.setAttribute
          ("User.id", String.valueOf
          (newUserInfo.getUserData().getUserId()));
  session.setAttribute
          ("User.type",
          newUserInfo.getUserData().getUserTypeCd());
  ***/
  session.setAttribute
  ("User.id", String.valueOf
  (curUserInfo.getUserData().getUserId()));
  session.setAttribute
  ("User.type",
  curUserInfo.getUserData().getUserTypeCd());
  // Main DB
  /*** written by SVC
  if ("yes".equals(System.getProperty("multi.store.db"))) {
      AllUserData allUserData = new AllUserData();
      allUserData.setUserId(newUserInfo.getUserData().getUserId());
      allUserData.setUserName(newUserInfo.getUserData().getUserName());
      allUserData.setFirstName(newUserInfo.getUserData().getFirstName());
      allUserData.setLastName(newUserInfo.getUserData().getLastName());
      allUserData.setPassword(newUserInfo.getUserData().getPassword());
      allUserData.setUserStatusCd(newUserInfo.getUserData().getUserStatusCd());
      allUserData.setUserTypeCd(newUserInfo.getUserData().getUserTypeCd());
      allUserData.setEffDate(newUserInfo.getUserData().getEffDate());
      allUserData.setExpDate(newUserInfo.getUserData().getExpDate());
      log.info("allUserData 1 = " + allUserData);
      try {
          List<AllStoreData> allStoreList = new ArrayList<AllStoreData>();
          if (sForm.getStores() != null) {
              IdVector selIds = Utility.toIdVector(sForm.getStores().getCurrentlySelected());
              Iterator selIt = selIds.iterator();
              while (selIt.hasNext()) {
                  AllStoreData allStoreData = new AllStoreData();
                  Integer storeId = (Integer) selIt.next();
                  log.info("======updateUser() storeId: " + storeId);
                  StoreData store = storeBean.getStore(storeId.intValue());
                  allStoreData.setStoreId(store.getStoreId());
                  allStoreData.setStoreName(store.getBusEntity().getShortDesc());
                  allStoreData.setDomain(store.getStorePrimaryWebAddress().getValue());
                  log.info("allStoreData = " + allStoreData);
                  allStoreList.add(allStoreData);
              }
          }
//      allUserData.setDefaultStoreId(1);
          log.info("allUserData 2 = " + allUserData);
          log.info("allStoreList.size() = " + allStoreList.size());
          log.info("allStoreList = " + allStoreList);
          mainDbEjb.updateAllUserAndStore(allUserData, allStoreList, appUser.getUserName());
      } catch (Exception e) {
          e.printStackTrace();
          String message = ClwI18nUtil.getMessage(request,"admin.errors.multi.noMainDb", null);
          lUpdateErrors.add(
          "error",
          new ActionError("error.simpleGenericError",message));
          return lUpdateErrors;
      }
  }
  ***/
  // End Main DB    

  int theUserId = userid;
  if (0 == userid) {
    //theUserId = newUserInfo.getUserData().getUserId(); //old code: produces NullPointer exception
    theUserId = curUserInfo.getUserData().getUserId();
  }


  log.info(" userid=" + userid + " user type="
          + userTypeCd + " save properties " );

  //update/add any properties
  Properties userProps = new Properties();
  //add requiered properties.  Check if they are set is done elsewhere as they are requiered
  userProps.put(RefCodeNames.PROPERTY_TYPE_CD.DISTRIBUTION_CENTER_ID,sForm.getDistributionCenterId());

  userProps.put(RefCodeNames.PROPERTY_TYPE_CD.CORPORATE_USER,String.valueOf(isCorporateUser));
  sForm.setIsCorporateUser(isCorporateUser);
  
  userProps.put(RefCodeNames.PROPERTY_TYPE_CD.RESTRICT_ACCT_INVOICES,String.valueOf(restrictAcctInvoices));
  sForm.setRestrictAcctInvoices(restrictAcctInvoices);

  userProps.put(RefCodeNames.PROPERTY_TYPE_CD.RECEIVE_INV_MISSING_EMAIL,String.valueOf(receiveInvMissingEmail));
  sForm.setReceiveInvMissingEmail(receiveInvMissingEmail);

  //add any of the non required properties if they were set
  if(Utility.isSet(sForm.getManifestLabelHeight())){
    userProps.put(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_HEIGHT,sForm.getManifestLabelHeight());
  }
  if(Utility.isSet(sForm.getManifestLabelWidth())){
    userProps.put(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_WIDTH,sForm.getManifestLabelWidth());
  }

  log.info(" userid=" + userid + " label print mode="
          + manifestLabelPrintMode);
  if(Utility.isSet(manifestLabelPrintMode)){
    userProps.put(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_MODE,
            manifestLabelPrintMode
            );
    sForm.setManifestLabelPrintMode(manifestLabelPrintMode);
  }
  log.info(" userid=" + userid + " label type="
          + selectedManifestLabelType);
  if(Utility.isSet(selectedManifestLabelType)){
    userProps.put(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_TYPE,
            selectedManifestLabelType);
    sForm.setManifestLabelType(selectedManifestLabelType);

  }
  if (RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userTypeCd)) {
    userProps.put(RefCodeNames.PROPERTY_TYPE_CD.TOTAL_FIELD_READONLY, totalReadOnly);
    sForm.setTotalReadOnly(totalReadOnly);
  }
  if (Utility.isSet(userIDCode)) {
      userProps.put(RefCodeNames.PROPERTY_TYPE_CD.USER_ID_CODE, userIDCode);
      sForm.setUserIDCode(userIDCode);
  }

  userProps.put(RefCodeNames.PROPERTY_TYPE_CD.CUTOFF_TIME_EMAIL_REMINDER_CNT, cutoffTimeRemCnt);
  sForm.getConfPermForm(0).setCutoffTimeReminderEmailCount(cutoffTimeRemCnt);

  log.info(" userid=" + userid + " user type="
          + userTypeCd + " save properties 2 "
          + " size=" + userProps.size() );
  propBean.setUserPropertyCollection(theUserId, userProps);
  //end update/add any properties
  /*
  if (0 == userid) {
    // for new user, added the account or store assoc
    int newuserid = newUserInfo.getUserData().getUserId();
    userTypeCd = ud.getUserData().getUserTypeCd();
    if (RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(userTypeCd)
    || RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(userTypeCd)
    || RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userTypeCd)
    || RefCodeNames.USER_TYPE_CD.REGISTRATION_USER.equals(userTypeCd)
    || RefCodeNames.USER_TYPE_CD.MSB.equals(userTypeCd)
    ) {
      String accountid = sForm.getAccountId();
      if (null != accountid && !"".equals(accountid)) {
        userBean.addUserAssoc
                (newuserid, Integer.parseInt(accountid),
                RefCodeNames.USER_ASSOC_CD.ACCOUNT);


        if(RefCodeNames.USER_TYPE_CD.
                ACCOUNT_ADMINISTRATOR.equals(userTypeCd)){

          //automatically configure the user for this account
          Site siteBean = factory.getSiteAPI();
          SiteDataVector dv =
                  siteBean.getAllSites(Integer.parseInt(accountid), Site.ORDER_BY_NAME);
          for(int i=0; i<dv.size(); i++){
            SiteData sd = (SiteData)dv.get(i);
            //configure the user for the sites
            userBean.addUserAssoc(newuserid,
                    sd.getBusEntity().getBusEntityId(),
                    RefCodeNames.USER_ASSOC_CD.SITE);
          }
        }
      }
    }
  } else{
    // update the account or store assoc
    userTypeCd = ud.getUserData().getUserTypeCd();
    int res = 0;
    if (RefCodeNames.USER_TYPE_CD.
            ACCOUNT_ADMINISTRATOR.equals(userTypeCd)
            || RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userTypeCd)
            || RefCodeNames.USER_TYPE_CD.REGISTRATION_USER.equals(userTypeCd)
            || RefCodeNames.USER_TYPE_CD.
            CUSTOMER.equals(userTypeCd)
            || RefCodeNames.USER_TYPE_CD.
            MSB.equals(userTypeCd)
            ) {
      String accountid = sForm.getAccountId();
      if (null != accountid && !"".equals(accountid)) {

        BusEntityDataVector bed = userBean.getBusEntityCollection(userid, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
        BusEntityData bd = (BusEntityData)bed.get(0);
        int oldAcc = bd.getBusEntityId();
        int newAcc = Integer.parseInt(accountid);

        res = userBean.updateUserAssoc
                (userid, Integer.parseInt(accountid),
                RefCodeNames.USER_ASSOC_CD.ACCOUNT);

        //check old accountId and if different
        //remove all account and site associations

        if(newAcc != oldAcc){

          userBean.removeUserAssoc(userid, oldAcc);
          SiteDataVector currSites = userBean.getSiteDescCollection(userid, null);
          log.info("removing "+currSites.size()+" sites");
          //now remove the old site assoc's and add the new ones
          for(int i=0; i<currSites.size();i++){
            SiteData s = (SiteData)currSites.get(i);
            int siteId = s.getBusEntity().getBusEntityId();
            userBean.removeUserAssoc(userid, siteId);
          }

          //find user (non-template) orderGuides and remove them
          OrderGuideDescDataVector ogVec =
                  orderGuideBean.getCollectionByUser
                  (userid,OrderGuide.TYPE_BUYER);

          for(int i=0; i<ogVec.size();i++){
            OrderGuideDescData og = (OrderGuideDescData)ogVec.get(i);
            int  ogId = og.getOrderGuideId();
            orderGuideBean.removeOrderGuide(ogId);
          }
        }
      }
    }
  }

  log.info(" userid=" + userid + " user type="
          + userTypeCd + " update DONE " );
  */
  return lUpdateErrors;
 
  }
  
  public static ActionErrors createNewUserInDiffentDbTypes(UserInfoData curUserInfo, String datasource, CleanwiseUser appUser, AllStoreData allStoreD, User userBean, MainDb mainDbEjb) throws Exception {
	  
	    ActionErrors lUpdateErrors = new ActionErrors();
	    UserInfoData newUserInfo = null;
	  
	    // 2) create NEW user in the clw_user table 
	    
        newUserInfo = userBean.updateUserInfo(curUserInfo, datasource);
        log.info("curUserInfo.getUserData().getUserId()_1 = " + curUserInfo.getUserData().getUserId());
      
        // 3) add BusEntityAssociation for a specific User for the Single Store DB Schema (clw_user_assoc table)
        userBean.addBusEntityAssociationsSingleStoreDb(newUserInfo.getUserData().getUserId(), RefCodeNames.USER_ASSOC_CD.STORE, appUser.getUserName(), datasource, allStoreD.getStoreId());
 	   
        // 4) retrieve UserData from the Single Store DB Schema
        UserData userDataAdd = userBean.getUserByUserIdFromStoreDbSchema(newUserInfo.getUserData().getUserId(), datasource);
        log.info("createNewUserInDiffentDbTypes(): userDataAdd1 = " + userDataAdd);
        
        // 5) add records to the table of the Main DB (esw_all_user)
        lUpdateErrors = addNewUserToMainDb(newUserInfo, mainDbEjb);
        log.info("curUserInfo.getUserData().getUserId()_2 = " + newUserInfo.getUserData().getUserId());               
        
        if (lUpdateErrors.size()>0) {
        	log.info("createNewUserInDiffentDbTypes(): returning errors...");
      	    return lUpdateErrors;
        }
        
        // 6) add record to the table of the Main DB (esw_user_store)
        if (userDataAdd != null) {
        
             mainDbEjb.addUserStoreAssociationSingleStore(userDataAdd.getUserId(), allStoreD.getAllStoreId(), userDataAdd.getUserName(), userDataAdd.getPrefLocaleCd()); 
        }
         
        return lUpdateErrors;
  }
  
  public static boolean isCurStoreAssigned(IdVector toProcessMainDb, MainDb mainDbEjb, CleanwiseUser appUser) throws Exception {
  	boolean storeConfigFl = false;
  	AllStoreData asd = mainDbEjb.getAllStoreDataByStoreId(appUser.getUserStore().getStoreId());
    for (int j0=0; j0<toProcessMainDb.size(); j0++) {
 	    
    	// check if the CURRENT Store is checkmarked on the screen (= assigned to the User)    	
        if ( ((Integer)toProcessMainDb.get(j0)).intValue() == asd.getAllStoreId()) {
     	   storeConfigFl = true;
        }
    }
    return storeConfigFl;
  }
  
}
