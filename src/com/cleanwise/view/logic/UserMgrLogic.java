
package com.cleanwise.view.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.EmailClient;
import com.cleanwise.service.api.session.Group;
import com.cleanwise.service.api.session.Language;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.OrderGuide;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.I18nUtil;
import com.cleanwise.service.api.util.PasswordUtil;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.CountryData;
import com.cleanwise.service.api.value.CountryDataVector;
import com.cleanwise.service.api.value.DistributorDataVector;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.GroupData;
import com.cleanwise.service.api.value.GroupDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.LanguageData;
import com.cleanwise.service.api.value.LanguageDataVector;
import com.cleanwise.service.api.value.OrderGuideDescData;
import com.cleanwise.service.api.value.OrderGuideDescDataVector;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteDataVector;
import com.cleanwise.service.api.value.SiteViewVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.service.api.value.UserSearchCriteriaData;
import com.cleanwise.view.forms.UserMgrDetailForm;
import com.cleanwise.view.forms.UserMgrDistributorConfigForm;
import com.cleanwise.view.forms.UserMgrSearchForm;
import com.cleanwise.view.forms.UserMgrSiteConfigForm;
import com.cleanwise.view.forms.UserRightsForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwComparatorFactory;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.SelectableObjects;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.validators.EmailValidator;
import org.apache.log4j.*;
/**
 *  <code>UserMgrLogic</code> implements the logic needed to manipulate user
 *  records.
 *
 *@author     durval
 *@created    October 8, 2001
 */
public class UserMgrLogic {
    private static final Logger log = Logger.getLogger(UserMgrLogic.class);
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
            UserMgrSearchForm sForm = (UserMgrSearchForm)form;
            acctid = sForm.getSearchAccountId();
        }
        catch (Exception e) {
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
            }
            else {
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

        UserMgrSearchForm sForm = (UserMgrSearchForm)form;
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

        uv = search(request, fieldValue, fieldSearchType, searchAccountId, fName, lName, uType, storeIds);

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
	 	return search(request, fieldValue, fieldSearchType, searchAccountId, fName, lName, uType, storeIds, null);
	 }

    public static UserDataVector search(HttpServletRequest request, String fieldValue, String fieldSearchType,
        int searchAccountId, String fName, String lName, String uType, IdVector storeIds, IdVector accountIds)
            throws Exception {

        HttpSession session = request.getSession();
        APIAccess factory = new APIAccess();
        User userBean = factory.getUserAPI();

        UserSearchCriteriaData searchCriteria = UserSearchCriteriaData.createValue();

        if (searchAccountId > 0) {
            searchCriteria.setAccountId(searchAccountId);
        }
        if (accountIds != null) {
            searchCriteria.setAccountIds(accountIds);
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        appUser.getUser();

        searchCriteria.setUserStoreIds(Utility.toIdVector(appUser.getAssociatedStores()));

        if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
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
        searchCriteria.setUserTypeCd(uType);
        return userBean.getUsersCollectionByCriteria(searchCriteria);
    }

    public static UserDataVector searchMod(HttpServletRequest request, String fieldValue, String fieldSearchType,
            int searchAccountId, String fName, String lName, String uType, IdVector storeIds, IdVector accountIds)
                throws Exception {

            HttpSession session = request.getSession();
            APIAccess factory = new APIAccess();
            User userBean = factory.getUserAPI();

            UserSearchCriteriaData searchCriteria = UserSearchCriteriaData.createValue();

            if (searchAccountId > 0) {
                searchCriteria.setAccountId(searchAccountId);
            }
            if (accountIds != null) {
                searchCriteria.setAccountIds(accountIds);
            }

            CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
            appUser.getUser();

            searchCriteria.setUserStoreIds(Utility.toIdVector(appUser.getAssociatedStores()));

            if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
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
            searchCriteria.setUserTypeCd(uType);
            return userBean.getUsersCollectionByCriteriaMod(searchCriteria);
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

    private static void initStoreOptions(int pUserId, UserMgrDetailForm sForm, HttpSession session, User userBean, APIAccess factory)
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


    /**
     * <code>getUserDetailById</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static UserInfoData getUserDetailById
        (HttpServletRequest request, int pUserId)
        throws Exception {

        // Make sure the session variables needed have
        // been set up.  The user detail page may have been
        // accessed without the init() method being called.
        initSessionVariables(request);
        HttpSession session = request.getSession(true);
        UserMgrDetailForm sForm = (UserMgrDetailForm)
      session.getAttribute("USER_DETAIL_FORM");

        if (sForm == null) {
            sForm = new UserMgrDetailForm();
            session.setAttribute("USER_DETAIL_FORM",
                                 sForm);
        }

        APIAccess factory = new APIAccess();
        User userBean = factory.getUserAPI();
        Group groupBean = factory.getGroupAPI();
        PropertyService propBean = factory.getPropertyServiceAPI();

        initStoreOptions(pUserId, sForm, session, userBean, factory);

        //Sets up the list of groups this user is a member of
        Map userGroups = groupBean.getGroupsUserIsMemberOf(pUserId);
        session.setAttribute("Users.admin.memberof.groups.map",userGroups);
        java.util.Iterator it = userGroups.values().iterator();
        String [] gnamesArr = new String[userGroups.values().size()];
        int i = 0;
        while(it.hasNext()){
            gnamesArr[i] = (String) it.next();
            i++;
        }
        sForm.setMemberOfGroups(gnamesArr);
        sForm.setGroupsReport(groupBean.getUserGroupsReport(pUserId));

        UserInfoData ud = userBean.getUserContact(pUserId);
        
        //STJ-3996
        String userLocale = ud.getUserData().getPrefLocaleCd();
        String pLanguageCode = userLocale.substring(0,2);
        String pCountryCode = userLocale.substring(3,userLocale.trim().length());
        
        Language languageBean = factory.getLanguageAPI();
        LanguageData languageData = languageBean.getLanguageByLanguageCode(pLanguageCode);
        if(languageData!=null) {
        	ud.setLanguageData(languageData);
        }
        
        Country countryBean = factory.getCountryAPI();
        CountryData countryData = countryBean.getCountryByCountryCode(pCountryCode);
        if(countryData!=null) {
        	ud.setCountryData(countryData);
        }
        
        if(languageData!=null && countryData!=null) {
          	String pPrefLocaleCd = languageData.getLanguageCode().trim()+"_"+countryData.getCountryCode().trim();
          	ud.getUserData().setPrefLocaleCd(pPrefLocaleCd);
          }
        
        sForm.setDetail(ud);

        if (null == ud) {
            return null;
        }

  session.setAttribute("User.id", String.valueOf(pUserId));
  if (RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals
      (ud.getUserData().getUserTypeCd())
      || RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(ud.getUserData().getUserTypeCd())
      || RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(ud.getUserData().getUserTypeCd())
      || RefCodeNames.USER_TYPE_CD.REGISTRATION_USER.equals(ud.getUserData().getUserTypeCd())
      || RefCodeNames.USER_TYPE_CD.MSB.equals(ud.getUserData().getUserTypeCd())
      ) {

      BusEntityDataVector userassocV = userBean.getBusEntityCollection(ud.getUserData().getUserId(),RefCodeNames.USER_ASSOC_CD.ACCOUNT);
      if (null != userassocV && 1 == userassocV.size()) {
    BusEntityData userassoc = (BusEntityData) userassocV.get(0);

    // Set the account information.
    sForm.setAccountId(String.valueOf(userassoc.getBusEntityId()));
    sForm.setAccountName(userassoc.getShortDesc());

    Account accountBean = factory.getAccountAPI();
    AccountData account = accountBean.getAccount
        (userassoc.getBusEntityId(), 0);
    AddressData accountAddr = account.getPrimaryAddress();
    sForm.setAccountAddress(accountAddr);
      }

  }

  if (RefCodeNames.USER_TYPE_CD.SERVICE_PROVIDER.equals(ud.getUserData().getUserTypeCd())) {
      // Get the store information.
      BusEntityDataVector userassocV = userBean.getBusEntityCollection(ud.getUserData().getUserId(),RefCodeNames.USER_ASSOC_CD.STORE);
      if (null != userassocV && 1 == userassocV.size()) {
          BusEntityData userassoc = (BusEntityData) userassocV.get(0);
            // Set the store information.
            sForm.setStoreId(String.valueOf(userassoc.getBusEntityId()));
            sForm.setStoreName(userassoc.getShortDesc());
      }
  }

  //handle the user properties
  try{
      Properties updv = propBean.getUserPropertyCollection(pUserId);
      Enumeration enume = updv.propertyNames();
      while(enume.hasMoreElements()){
    String key = (String) enume.nextElement();
    String value = updv.getProperty(key);
    if(key.equals(RefCodeNames.PROPERTY_TYPE_CD.DISTRIBUTION_CENTER_ID)){
        sForm.setDistributionCenterId(value);
    }else if(key.equals(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_HEIGHT)){
        sForm.setManifestLabelHeight(value);
    }else if(key.equals(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_WIDTH)){
        sForm.setManifestLabelWidth(value);
    }else if(key.equals(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_MODE)){
        sForm.setManifestLabelPrintMode(value);
    }else if(key.equals(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_TYPE)){
        sForm.setManifestLabelType(value);
    }
      }
  }catch(Exception e){
      //no properties for this user

  }

  //session.setAttribute("USER_DETAIL_FORM", sForm);
  //request.setAttribute("USER_DETAIL_FORM", sForm);

  ud = sForm.getDetail();

  return ud;

    }


    /**
     *  <code>getUserDetail</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void getUserDetail(HttpServletRequest request,
                                     ActionForm form)
        throws Exception {

        String fieldValue = request.getParameter("searchField");

        if (null == fieldValue) {
            fieldValue = (String)
                request.getSession().getAttribute("User.id");
        }

        if (null == fieldValue) {
            fieldValue = "0";
        }

        Integer id = new Integer(fieldValue);
        getUserDetailById(request, id.intValue());

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
        UserMgrDetailForm sForm = (UserMgrDetailForm) form;

        if (sForm == null) {
            return lUpdateErrors;
        }

        APIAccess factory = new APIAccess();
        User userBean = factory.getUserAPI();

        UserInfoData ud = sForm.getDetail();
        UserData user = ud.getUserData();
        user.setFirstName(I18nUtil.getUtf8Str(user.getFirstName()));
        user.setLastName(I18nUtil.getUtf8Str(user.getLastName()));
        AddressData ad = ud.getAddressData();
        ad.setAddress1(I18nUtil.getUtf8Str(ad.getAddress1()));
        ad.setAddress2(I18nUtil.getUtf8Str(ad.getAddress2()));
        ad.setCity(I18nUtil.getUtf8Str(ad.getCity()));
        ad.setStateProvinceCd(I18nUtil.getUtf8Str(ad.getStateProvinceCd()));
        ad.setPostalCode(I18nUtil.getUtf8Str(ad.getPostalCode()));
        
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        //STJ - 3996
        CountryData countryData = null;
        String pCountryCode =  ud.getCountryData().getCountryCode().trim();
        Country countryBean = factory.getCountryAPI();
        countryData = countryBean.getCountryByCountryCode(pCountryCode);
        if(countryData!=null) {
        	ud.setCountryData(countryData);
        	ad.setCountryCd(I18nUtil.getUtf8Str(countryData.getShortDesc()));
        }
        
        LanguageData langugeData = null;
        String lShortDisc = ud.getLanguageData().getShortDesc().trim();
        Language languageBean = factory.getLanguageAPI();
        langugeData = languageBean.getLanguageByShortDesc(lShortDisc);
        if(langugeData!=null) {
        	ud.setLanguageData(langugeData);
        }
        
        if(langugeData!=null && countryData!=null) {
        	String pPrefLocaleCd = langugeData.getLanguageCode().trim()+"_"+countryData.getCountryCode().trim();
        	ud.getUserData().setPrefLocaleCd(pPrefLocaleCd);
        	//Setting edited user's locale into session.
        	appUser.getUser().setPrefLocaleCd(pPrefLocaleCd);
        }
        
        // if new password specified, hash it
        String password = sForm.getPassword();
        if(password==null) password ="";
        password = password.trim();
        String conpassword = sForm.getConfirmPassword();
        if(conpassword==null)conpassword = "";
        conpassword = conpassword.trim();
        String oldPassword = sForm.getOldPassword();
        if (oldPassword == null) oldPassword = "";
        oldPassword = oldPassword.trim();
        boolean forcedPasswordUpdate = Utility.isTrue(request.getParameter(Constants.FORCED_PASSWORD_UPDATE));
        if (password.length() == 0 && conpassword.length() == 0 &&
                (forcedPasswordUpdate == true || oldPassword.length() > 0)) {
            String errorMess = ClwI18nUtil.getMessage(request,
                    "shop.errors.youShouldEnterNewPassword", null);
            lUpdateErrors.add("error", new ActionError(
                    "error.simpleGenericError", errorMess));
//            return lUpdateErrors;
        }
        if (oldPassword.length() > 0 || forcedPasswordUpdate == true || password.length()>0 || conpassword.length()>0) {
            // checking old password
            String hash1 = user.getPassword();
            String hash2 = PasswordUtil.getHash(user.getUserName(), oldPassword);
            if (hash1.equals(hash2) == false) {
                String errorMess = ClwI18nUtil.getMessage(request,
                        "shop.errors.incorrectCurrentPassword", null);
                lUpdateErrors.add("error", new ActionError(
                        "error.simpleGenericError", errorMess));
//                return lUpdateErrors;
            }
        }
        String newPasswordHash = null;
        if (password.length()>0 || conpassword.length()>0) {
        	 if(!password.equals(conpassword)){
                 String errorMess =
                    ClwI18nUtil.getMessage(request,"shop.errors.passwordAndPasswordConfirmationDoNotMatch",null);
                 lUpdateErrors.add("error",
                   new ActionError("error.simpleGenericError",errorMess));
//                 return lUpdateErrors;
               }
               newPasswordHash = PasswordUtil.getHash(user.getUserName(), password);
        }

        int userid = ud.getUserData().getUserId();
        String emailAddress = ud.getEmailData().getEmailAddress();
        EmailValidator.validateEmail(request, lUpdateErrors,
                "shop.userProfile.text.email", null, emailAddress);
        if (lUpdateErrors.size() > 0) {
            return lUpdateErrors;
        }
        if (newPasswordHash != null) {
            user.setPassword(newPasswordHash);
        }
        String appUserType = appUser.getUser().getUserTypeCd();
        if(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(appUserType) && !Utility.isTrue(sForm.getProfileUpdate())) {
          userBean.updateUserInfo(ud);
        } else {
          userBean.updateUserProfile(ud);
        }
        sForm.setDetail(getUserDetailById( request, userid));
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
        UserMgrDetailForm sForm = new UserMgrDetailForm();
        initStoreOptions(0, sForm, session, null, null);
        session.setAttribute("USER_DETAIL_FORM", sForm);
    }


    public static ActionErrors updateUserAccountRights
        (HttpServletRequest request,
         ActionForm form)
        throws Exception {
        ActionErrors lUpdateErrors = new ActionErrors();

        HttpSession session = request.getSession();
        UserMgrDetailForm userDetailForm = (UserMgrDetailForm) form;
  int userId = userDetailForm.getDetail().getUserData().getUserId();
        ArrayList userAccountRights = userDetailForm.getRightsForms();
        APIAccess factory = new APIAccess();
        User userBean = factory.getUserAPI();
        for ( int i = 0; null != userAccountRights &&
                  i < userAccountRights.size(); i++ )
        {
            UserRightsForm userRights = (UserRightsForm)
                userAccountRights.get(i);

            if (userRights != null) {
                int accountId = userRights.getAccountData().getBusEntityId();
                String newRights = userRights.getRightsFromOptions();
                log.info
        ("1 updateUserAccountRights, userId=" + userId
         + " accountId=" + accountId + " newRights=" + newRights );
                userBean.updateUserAccountRights
                    (userId, accountId,newRights,
                     (String) session.getAttribute(Constants.USER_NAME));
                log.info
        ("2 updateUserAccountRights, userId=" + userId
         + " accountId=" + accountId + " newRights=" + newRights );
            }
        }

        // Update the base rights for this user.
  String baseRights = userDetailForm.makePermissionsToken();
  int noaccountId = 0;
  log.info
      ("BASE 1 updateUserAccountRights, userId=" + userId
       + " noaccountId=" + noaccountId + " baseRights=" + baseRights );
  userBean.updateUserAccountRights
      (userId, noaccountId,baseRights,
       (String) session.getAttribute(Constants.USER_NAME));

  // update all user info.
  userDetailForm.setDetail(getUserDetailById(request,userId) );

        return lUpdateErrors;
    }

    private static class CloneResult {
  public ActionErrors mAppErrors;
  public UserInfoData mUserInfoData;
    }

    public static ActionErrors registerUser
  (HttpServletRequest request,ActionForm form,
   MessageResources pMsgRes
   )
    throws Exception
    {
        ActionErrors lUpdateErrors = new ActionErrors();

        HttpSession session = request.getSession();
        UserMgrDetailForm sForm = (UserMgrDetailForm) form;


        if (sForm == null) {
            String errorMess =
               ClwI18nUtil.getMessage(request,"shop.errors.missingUserInformation2",null);
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
      return cloneRes.mAppErrors;
  }

  // Update the particulars for this user.
  UserData nuserdata = cloneRes.mUserInfoData.getUserData();
  nuserdata.setUserName(fuserdata.getUserName());
  nuserdata.setFirstName(fuserdata.getFirstName());
  nuserdata.setLastName(fuserdata.getLastName());
  nuserdata.setPassword(sForm.getConfirmPassword());
  nuserdata.setUserTypeCd(asUserType);

  sForm.getDetail().setUserData(nuserdata);

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
      String errorMess =
               ClwI18nUtil.getMessage(request,"shop.errors.thisUsernameIsAlreadyInUse",null);
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
        UserMgrDetailForm sForm = (UserMgrDetailForm) form;

  String selectedManifestLabelType = "",
      manifestLabelPrintMode = "";

  if ( null != sForm.getManifestLabelType() ) {
      selectedManifestLabelType =  new String
    (sForm.getManifestLabelType() );
  }

  if ( null != sForm.getManifestLabelPrintMode() ) {
      manifestLabelPrintMode = new String
    (sForm.getManifestLabelPrintMode());
  }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        if (sForm == null) {
      String errorMess =
               ClwI18nUtil.getMessage(request,"shop.errors.missingUserInformation1",null);
            lUpdateErrors.add
    ("error",
     new ActionError("error.simpleGenericError",errorMess));

      return lUpdateErrors;
  }

  APIAccess factory = new APIAccess();
  User userBean = factory.getUserAPI();
  PropertyService propBean = factory.getPropertyServiceAPI();
  OrderGuide orderGuideBean = factory.getOrderGuideAPI();

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
      String errorMess =
           ClwI18nUtil.getMessage(request,"shop.errors.emptyFieldDistributorCentrId",null);
    lUpdateErrors.add("distributionCenterId",new ActionError("error.simpleGenericError",errorMess));

      }
      if(Utility.isSet(sForm.getManifestLabelHeight())){
    try{
        new Integer(sForm.getManifestLabelHeight());
    }catch(NumberFormatException e){
      String errorMess =
           ClwI18nUtil.getMessage(request,"shop.errors.invalidValueOfManifestLabelHeight",null);
       lUpdateErrors.add("manifLabelHeight",new ActionError("error.simpleGenericError",errorMess));
    }
      }
      if(Utility.isSet(sForm.getManifestLabelWidth())){
    try{
        new Integer(sForm.getManifestLabelWidth());
    }catch(NumberFormatException e){
      String errorMess =
           ClwI18nUtil.getMessage(request,"shop.errors.invalidValueOfManifestLabelWidth",null);
       lUpdateErrors.add("manifestLabelWidth",new ActionError("error.simpleGenericError",errorMess));
    }
      }
  }
        if (RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userTypeCd) ||
    RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userTypeCd) ||
    RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userTypeCd) ||
    RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userTypeCd)
  ){
            if(sForm.getStores().getCurrentlySelected().isEmpty()){
      String errorMess =
           ClwI18nUtil.getMessage(request,"shop.errors.emptyFieldStores",null);
       lUpdateErrors.add("stores",new ActionError("error.simpleGenericError",errorMess));
            }
        }

  if(lUpdateErrors.size() > 0){
      return lUpdateErrors;
  }

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

        }
        catch (Exception e) {
      accountD = null;
        }
        if( null == accountD) {
      String errorMess =
           ClwI18nUtil.getMessage(request,"user.bad.account",null);
       lUpdateErrors.add("userAccount",new ActionError("error.simpleGenericError",errorMess));
      return lUpdateErrors;
        }
    }
      }
  }

  Date now = new Date();
  ud.getUserData().setLastActivityDate(now);
  ud.getUserData().setModDate(now);
  ud.getUserData().setModBy((String) session.getAttribute(Constants.USER_NAME));

  ud.getAddressData().setName1(ud.getUserData().getFirstName());
  ud.getAddressData().setName2(ud.getUserData().getLastName());

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
                userBean.addBusEntityAssociations(newUserInfo.getUserData().getUserId(), toAdd, RefCodeNames.BUS_ENTITY_TYPE_CD.STORE,appUser.getUserName());
                userBean.removeBusEntityAssociations(newUserInfo.getUserData().getUserId(), toDel);
                sForm.getStores().resetState();
            }

      // Refresh user info from the database.
      sForm.setDetail(getUserDetailById(request,userid));

  }
  catch(DuplicateNameException ne) {
      String errorMess =
           ClwI18nUtil.getMessage(request,"user.duplicate.username",null);
       lUpdateErrors.add("user",new ActionError("error.simpleGenericError",errorMess));
      return lUpdateErrors;
  }

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


        //update/add any properties
  Properties userProps = new Properties();
  //add requiered properties.  Check if they are set is done elsewhere as they are requiered
  userProps.put(RefCodeNames.PROPERTY_TYPE_CD.DISTRIBUTION_CENTER_ID,sForm.getDistributionCenterId());
  //add any of the non required properties if they were set
  if(Utility.isSet(sForm.getManifestLabelHeight())){
      userProps.put(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_HEIGHT,sForm.getManifestLabelHeight());
  }
  if(Utility.isSet(sForm.getManifestLabelWidth())){
      userProps.put(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_WIDTH,sForm.getManifestLabelWidth());
  }

  if(Utility.isSet(manifestLabelPrintMode)){
      userProps.put(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_MODE,
        manifestLabelPrintMode
        );
      sForm.setManifestLabelPrintMode(manifestLabelPrintMode);
  }
  if(Utility.isSet(selectedManifestLabelType)){
      userProps.put(RefCodeNames.PROPERTY_TYPE_CD.MANIFEST_LABEL_TYPE,
        selectedManifestLabelType);
      sForm.setManifestLabelType(selectedManifestLabelType);

  }

  propBean.setUserPropertyCollection(theUserId, userProps);
  //end update/add any properties

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
          siteBean.getAllSites(Integer.parseInt(accountid), Site.ORDER_BY_NAME, SessionTool.getCategoryToCostCenterViewByAcc(session, Integer.parseInt(accountid)));
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
  }
  else{
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

        return lUpdateErrors;
    }

    public static ActionErrors updateUserGroups
  (HttpServletRequest request,
   ActionForm form)
  throws Exception    	{

  ActionErrors lUpdateErrors = new ActionErrors();

        HttpSession session = request.getSession();
        UserMgrDetailForm sForm = (UserMgrDetailForm) form;


        if (sForm == null) {
      String errorMess =
           ClwI18nUtil.getMessage(request,"shop.errors.missingUserInformation1",null);
       lUpdateErrors.add("error",new ActionError("error.simpleGenericError",errorMess));

      return lUpdateErrors;
  }

  APIAccess factory = new APIAccess();
  User userBean = factory.getUserAPI();

  //update the members list (convert from string to int,
  //on fail set to null)
  //a null list will be interpreted as unchanged at the bean layer
  ArrayList memberOfGroups = new ArrayList();
  if( sForm.getMemberOfGroups() != null){
      Group groupBean = factory.getGroupAPI();
      GroupDataVector sysGropups = groupBean.getAllGroups();
      String [] membership = sForm.getMemberOfGroups();
      for(int i=0;i<membership.length;i++){
    String groupName = membership[i];
    // Get the group id for this name.
    for ( int j = 0; sysGropups != null &&
        j < sysGropups.size(); j++ ) {
        GroupData g = (GroupData)sysGropups.get(j);
        if ( groupName.trim().equals(g.getShortDesc().trim())) {
      memberOfGroups.add(new Integer(g.getGroupId()));
      break;
        }
    }
      }
  }

  try {
      String moduser = (String)
    session.getAttribute(Constants.USER_NAME);

      int userid = sForm.getDetail().getUserData().getUserId();
      userBean.updateUserGroups(userid, memberOfGroups,
              moduser);
      // Refresh user info from the database.
      sForm.setDetail(getUserDetailById(request,userid));

  }
  catch(Exception e) {
      lUpdateErrors.add("user", new ActionError
            (e.getMessage()));
  }

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
      getUserDetailById(request,userid);
  }
  catch(Exception e) {
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

        UserMgrDetailForm sForm = (UserMgrDetailForm) form;

  // Hash password (if updating an existing user, a new password
  // may not have been specified)
  String password = sForm.getPassword(),
      confpassword = sForm.getConfirmPassword();
  if (password == null || password.trim().length() == 0 ) {
      String errorMess =
           ClwI18nUtil.getMessage(request,"shop.errors.pleaseSetThePasswordField",null);
       lUpdateErrors.add("error",new ActionError("error.simpleGenericError",errorMess));
      return lUpdateErrors;
  }

  if (confpassword == null || confpassword.trim().length() == 0 ) {
      String errorMess =
           ClwI18nUtil.getMessage(request,"shop.errors.pleaseSetTheConfirmPasswordField",null);
       lUpdateErrors.add("error",new ActionError("error.simpleGenericError",errorMess));
      return lUpdateErrors;
  }
  if ( confpassword.equals(password) == false ) {
      String errorMess =
           ClwI18nUtil.getMessage(request,"shop.errors.passwordAndPasswordConfirmationDoNotMatch",null);
       lUpdateErrors.add("error",new ActionError("error.simpleGenericError",errorMess));
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
        UserMgrDetailForm sForm = (UserMgrDetailForm) form;

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
        
        //STJ - 3996
        if(session.getAttribute("languages.vector")==null) {
        	Language languageBean = factory.getLanguageAPI();
        	LanguageDataVector languageVector = languageBean.getAllLanguages();
        	session.setAttribute("languages.vector",languageVector);
        }

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
     * <code>initDistributorConfigDisplay</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    private static void initDistributorConfigDisplay(HttpServletRequest request,ActionForm form)
        throws Exception {
        // Get a reference to the admin facade
        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        User userBean = factory.getUserAPI();

        //this is not the form that is passed in!
        UserMgrDetailForm detailForm = (UserMgrDetailForm)session.getAttribute("USER_DETAIL_FORM");
        int userId = detailForm.getDetail().getUserData().getUserId();

        IdVector distIds = new IdVector();

        BusEntityDataVector dists =
            userBean.getBusEntityCollection(userId,RefCodeNames.USER_ASSOC_CD.DISTRIBUTOR);
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

        UserMgrDetailForm detailForm =
            (UserMgrDetailForm)session.getAttribute("USER_DETAIL_FORM");
        int userId = detailForm.getDetail().getUserData().getUserId();

        IdVector siteIds = new IdVector();

        BusEntityDataVector sites =
            userBean.getSiteCollection(userId);
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
        UserMgrSiteConfigForm sForm = (UserMgrSiteConfigForm)form;
        sForm.setSelectIds(selectIds);

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
        UserMgrDetailForm userForm = (UserMgrDetailForm)
            session.getAttribute("USER_DETAIL_FORM");
        IdVector accountIds = userForm.getUserAccountIds();
        QueryRequest qr = new QueryRequest();
        qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);
        qr.filterByAccountIds(accountIds);

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
    public static void searchDistributorConfig(HttpServletRequest request,
                                               ActionForm form, boolean fetchAll)
        throws Exception {

        HttpSession session = request.getSession();
        UserMgrDistributorConfigForm sForm = (UserMgrDistributorConfigForm)form;

        String fieldValue = sForm.getSearchField();

        APIAccess factory = new APIAccess();
        Distributor distributorBean = factory.getDistributorAPI();

        // Reset the results vector.
        DistributorDataVector dv = new DistributorDataVector();
        session.setAttribute("user.distributor.vector", dv);

        String searchType = sForm.getSearchType();
        fieldValue.trim();

        if (fieldValue == null || fieldValue.equals("") || fetchAll) {
            // treat a blank name/search value the same as 'view all'
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


    /**
     * <code>searchSiteConfig</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static ActionErrors searchSiteConfig(HttpServletRequest request,
                                                ActionForm form)
        throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        UserMgrSiteConfigForm sForm = (UserMgrSiteConfigForm)form;

        String fieldValue = sForm.getSearchField();
        String city = sForm.getCity();
        String state = sForm.getState();
        /*
          if ((fieldValue == null || fieldValue.equals("")) &&
          (city == null || city.equals("")) &&
          (state == null || state.equals(""))) {
          // treat a blank name/search value the same as 'view all'
          getAllSiteConfig(request, form);
          return;
          }
        */
        UserMgrDetailForm userForm = (UserMgrDetailForm)
            session.getAttribute("USER_DETAIL_FORM");
        IdVector accountIds = new IdVector();
        UserMgrDetailForm detailForm =
            (UserMgrDetailForm) session.getAttribute("USER_DETAIL_FORM");
        UserInfoData ud = detailForm.getDetail();
        int userId = ud.getUserData().getUserId();

        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();
        User userBean = factory.getUserAPI();
  BusEntityDataVector accts = userBean.getUserSiteAccounts(userId);
  for ( int i = 0; accts != null && i < accts.size(); i++) {
      BusEntityData thisAcct = ((BusEntityData)accts.get(i));
      accountIds.add( new Integer( thisAcct.getBusEntityId()));
  }

        // Reset the results vector.
        SiteViewVector dv = new SiteViewVector();
        session.setAttribute("user.siteview.vector", dv);

        String searchType = sForm.getSearchType();
        fieldValue.trim();

        QueryRequest qr = new QueryRequest();
        qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);
        String f = "";

        if (searchType.equals("id") &&
            fieldValue.length() > 0 ) {

            Integer id = new Integer(fieldValue);
            qr.filterBySiteId(id.intValue());
            dv = siteBean.getSiteCollection(qr);
        } else if (searchType.equals("nameBegins") &&
                   fieldValue.length() > 0 ) {
            qr.filterBySiteName(fieldValue,
                                QueryRequest.BEGINS_IGNORE_CASE);
        } else if (searchType.equals("nameContains") &&
                   fieldValue.length() > 0) {
            qr.filterBySiteName(fieldValue,
                                QueryRequest.CONTAINS_IGNORE_CASE);
        }

        f = city.trim();
        if (f.length() > 0) {
            qr.filterByCity(f, QueryRequest.BEGINS_IGNORE_CASE);
        }

        f = state.trim();
        if (f.length() > 0) {
            qr.filterByState(f, QueryRequest.BEGINS_IGNORE_CASE);
        }

  if (accountIds != null && accountIds.size() > 0 ) {
      qr.filterByAccountIds(accountIds);
  }

        dv = siteBean.getSiteCollection(qr);

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
        UserMgrDetailForm userForm = (UserMgrDetailForm) session.getAttribute("USER_DETAIL_FORM");
        int userId = userForm.getDetail().getUserData().getUserId();
        UserMgrSiteConfigForm sForm = (UserMgrSiteConfigForm)form;
        String fieldValue = sForm.getSearchField().trim();
        String city = sForm.getCity().trim();
        String state = sForm.getState().trim();
        String searchType = sForm.getSearchType();
        IdVector accountIds = userForm.getUserAccountIds();


        APIAccess factory = new APIAccess();
        Site siteBean = factory.getSiteAPI();

        // Reset the results vector.
        SiteViewVector dv = new SiteViewVector();
        session.setAttribute("user.siteview.vector", dv);


        QueryRequest qr = new QueryRequest();
        qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);
        qr.filterByUserId(userId);
        if (searchType.equals("id") &&
            fieldValue.length() > 0 ) {
            Integer id = new Integer(fieldValue);
            qr.filterBySiteId(id.intValue());
            dv = siteBean.getSiteCollection(qr);
        } else if (searchType.equals("nameBegins") &&
                   fieldValue.length() > 0 ) {
            qr.filterBySiteName(fieldValue,
                                QueryRequest.BEGINS_IGNORE_CASE);
        } else if (searchType.equals("nameContains") &&
                   fieldValue.length() > 0) {
            qr.filterBySiteName(fieldValue,
                                QueryRequest.CONTAINS_IGNORE_CASE);
        }
        if (city.length() > 0) {
            qr.filterByCity(city, QueryRequest.BEGINS_IGNORE_CASE);
        }

        if (state.length() > 0) {
            qr.filterByState(state, QueryRequest.BEGINS_IGNORE_CASE);
        }
        qr.filterByAccountIds(accountIds);

        dv = siteBean.getSiteCollection(qr);

        session.setAttribute("user.siteview.vector", dv);
        initSiteConfigDisplay(request, form);

        return ae;
    }

    /*
     *Updates the configuration when using the site config screen.
     */
    private static void updateConfig
  (String[] displayed,String[] selected,
   IdVector assocIds, String pUserAssociationType,
   APIAccess factory,HttpServletRequest request)
        throws Exception {
        HttpSession session = request.getSession();
        UserMgrDetailForm detailForm =
      (UserMgrDetailForm)session.getAttribute("USER_DETAIL_FORM");
        int userId = detailForm.getDetail().getUserData().getUserId();
  if ( request.getParameter("userid") != null ) {
      userId = Integer.parseInt
    ((String)request.getParameter("userid"));
      log.info("parameter userId=" + userId);
  }

        User userBean = factory.getUserAPI();
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
                userBean.removeUserAssoc(userId, id.intValue());
            }
        }
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

        UserMgrDistributorConfigForm sForm = (UserMgrDistributorConfigForm)form;

        // get list of site ids displayed
        String[] displayed = sForm.getDisplayIds();

        // get list of site ids selected
        String[] selected = sForm.getSelectIds();

        // get list of currently associated ids
        IdVector assocIds =
            (IdVector)session.getAttribute("user.assoc.distributor.ids");

        updateConfig(displayed, selected, assocIds, RefCodeNames.USER_ASSOC_CD.DISTRIBUTOR,factory,request);

        // update with changes
        //session.setAttribute("user.assoc.distributor.ids", assocIds);
        selected = new String[assocIds.size()];
        Iterator assocI = assocIds.iterator();
        int i = 0;
        while (assocI.hasNext()) {
            selected[i++] = new String(assocI.next().toString());
        }
        sForm.setSelectIds(selected);
    }

    /**
     * <code>updateSiteConfig</code>
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void updateSiteConfig(HttpServletRequest request,
                                        ActionForm form)
        throws Exception {

        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        User userBean = factory.getUserAPI();

        UserMgrSiteConfigForm sForm = (UserMgrSiteConfigForm)form;

        // get list of site ids displayed
        String[] displayed = sForm.getDisplayIds();

        // get list of site ids selected
        String[] selected = sForm.getSelectIds();

        // get list of currently associated site ids
        IdVector assocSiteIds =
            (IdVector)session.getAttribute("user.assoc.site.ids");

        updateConfig(displayed,
         selected,
         assocSiteIds,
         RefCodeNames.USER_ASSOC_CD.SITE,
         factory,request);

        // update with changes
        //session.setAttribute("user.assoc.site.ids", assocSiteIds);
  /*
        selected = new String[assocSiteIds.size()];
        Iterator assocI = assocSiteIds.iterator();
        int i = 0;
        while (assocI.hasNext()) {
            selected[i++] = new String(assocI.next().toString());
        }
        sForm.setSelectIds(selected);
  */
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

        initDistributorConfigDisplay(request, form);
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

        ActionErrors ae = new ActionErrors();
        CloneResult cRes = new CloneResult();
        cRes.mAppErrors = ae;

        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        User userBean = factory.getUserAPI();

        int templateId = 0;

        if ( pSourceUserId > 0 ) {
            templateId = pSourceUserId;
        }
        else {
            UserMgrSearchForm sForm = (UserMgrSearchForm) form;
            templateId = sForm.getSelectedId();
            UserDataVector uDV = (UserDataVector)
              session.getAttribute("Users.found.vector");

            if(templateId<=0 || uDV==null || uDV.size()==0) {
                String errorMess =
                     ClwI18nUtil.getMessage(request,"shop.errors.noUserSelected",null);
                cRes.mAppErrors.add("error",new ActionError("error.simpleGenericError",errorMess));
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
                String errorMess =
                     ClwI18nUtil.getMessage(request,"shop.errors.templatateUserIdIsNotInTheList",null);
                cRes.mAppErrors.add("error",new ActionError("error.simpleGenericError",errorMess));
                return cRes;
            }
        }

        String admin = (String) session.getAttribute(Constants.USER_NAME);
        int userId = userBean.createUserClone
        (templateId,admin,pAsUserType,pUserStatusCd);

        cRes.mUserInfoData = getUserDetailById(request,userId);

        return cRes;
    }

    //------------------------------------------------------------------
    /**
     *Configures all of the sites for the current request
     */
    public static ActionErrors configureAllSites(HttpServletRequest request,
                                                 ActionForm form)
        throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        UserMgrSiteConfigForm sForm = (UserMgrSiteConfigForm) form;
        APIAccess factory = new APIAccess();
        User userBean = factory.getUserAPI();

        UserMgrDetailForm detailForm = (UserMgrDetailForm)
            session.getAttribute("USER_DETAIL_FORM");
        UserInfoData ud = detailForm.getDetail();
        int userId = ud.getUserData().getUserId();
        String admin = (String) session.getAttribute(Constants.USER_NAME);
        try {
            BusEntityDataVector accts = userBean.getUserSiteAccounts(userId);
            for ( int i = 0; accts != null && i < accts.size(); i++)
            {
                BusEntityData thisAcct = ((BusEntityData)accts.get(i));
                int aid = thisAcct.getBusEntityId();
                userBean.assignAllSites(userId,aid,admin);
            }
        }
        catch(Exception exc) {
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

    public static ActionErrors  addAccountToUser(HttpServletRequest request,
                                                 ActionForm form)
        throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        UserMgrSiteConfigForm sForm = (UserMgrSiteConfigForm) form;
        int accountId = sForm.getAccountId();
        if(accountId==0) {
            String errorMess =
                 ClwI18nUtil.getMessage(request,"shop.errors.noAccountProvided",null);
            ae.add("error",new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }

        APIAccess factory = new APIAccess();
        User userBean = factory.getUserAPI();

        UserMgrDetailForm detailForm =
            (UserMgrDetailForm) session.getAttribute("USER_DETAIL_FORM");
        UserInfoData ud = detailForm.getDetail();
        int userId = ud.getUserData().getUserId();

        try {
            userBean.addUserAssoc(userId,accountId,
                                  RefCodeNames.USER_ASSOC_CD.ACCOUNT);
            // reload user info.
      detailForm.setDetail(getUserDetailById(request,userId));

        }
        catch(Exception exc) {
            Object[] params = new Object[3];
            params[0] = ""+userId;
            params[1] = ""+accountId;
            params[2] = exc.getMessage();
            String errorMess =
                 ClwI18nUtil.getMessage(request,"shop.errors.cannotAddUserToAccount",params);
            ae.add("admin",new ActionError("error.simpleGenericError",errorMess));

        }

        return ae;
    }

        public static ActionErrors  removeAccountFromUser(HttpServletRequest request,
        ActionForm form)
        throws Exception {

            ActionErrors ae = new ActionErrors();

            String accountIds = request.getParameter("accountId"),
            userIds = request.getParameter("userId");
            if ( null == userIds ||
                userIds.length() == 0 ||
            null == accountIds ||
            accountIds.length() == 0 ) {

              Object[] params = new Object[2];
              params[0] = ""+userIds;
              params[1] = ""+accountIds;
              String errorMess =
                   ClwI18nUtil.getMessage(request,"shop.errors.cannotRemoveAccountForUser",params);
              ae.add("admin",new ActionError("error.simpleGenericError",errorMess));

              return ae;
            }
            int userId = Integer.parseInt(userIds),
            accountId = Integer.parseInt(accountIds);
            HttpSession session = request.getSession();

            APIAccess factory = new APIAccess();
            User userBean = factory.getUserAPI();

        try {
            userBean.removeUserAssoc(userId,accountId);
            // reload user info.
            getUserDetailById(request,userId);
        }
        catch(Exception exc) {
            Object[] params = new Object[3];
            params[0] = ""+userId;
            params[1] = ""+accountId;
            params[2] = exc.getMessage();
            String errorMess =
                 ClwI18nUtil.getMessage(request,"shop.errors.cannotRemoveUserFromAccount",params);
            ae.add("admin",new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }

        return ae;
    }

    //------------------------------------------------------------------------


}



