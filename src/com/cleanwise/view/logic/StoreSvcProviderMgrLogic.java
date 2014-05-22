package com.cleanwise.view.logic;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.Group;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.OrderGuide;
import com.cleanwise.service.api.session.Service;
import com.cleanwise.service.api.session.ServiceProviderType;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AccountView;
import com.cleanwise.service.api.value.AccountViewVector;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.CountryDataVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.DistributorDataVector;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderGuideDescDataVector;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.ServiceProviderData;
import com.cleanwise.service.api.value.ServiceProviderDataVector;
import com.cleanwise.service.api.value.SiteView;
import com.cleanwise.service.api.value.SiteViewVector;
import com.cleanwise.service.api.value.UserAccountRightsView;
import com.cleanwise.service.api.value.UserAccountRightsViewVector;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.view.forms.StoreServiceProviderMgrDetailForm;
import com.cleanwise.view.forms.StoreServiceProviderMgrSearchForm;
import com.cleanwise.view.forms.StoreUserMgrForm;
import com.cleanwise.view.forms.UserRightsForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.validators.EmailValidator;


public class StoreSvcProviderMgrLogic {
	private static final Logger log = Logger.getLogger(StoreSvcProviderMgrLogic.class);

	
  public static ActionErrors getDetail(HttpServletRequest request,
                               ActionForm form) throws Exception {
    StoreServiceProviderMgrDetailForm sForm = (StoreServiceProviderMgrDetailForm) form;
    APIAccess factory = new APIAccess();
    Service svcProvBean = factory.getServiceAPI();
    ActionErrors ae=new ActionErrors();
    String fieldValue = request.getParameter("searchField");
    if (null == fieldValue) {
      fieldValue = "0";
    }
    Integer id = new Integer(fieldValue);
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      ServiceProviderData dd = null;
      try {
          dd = svcProvBean.getServiceProviderForStore(id.intValue(),appUser.getUserStoreAsIdVector(),true);
      } catch (DataNotFoundException e) {
         ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
         sForm=new StoreServiceProviderMgrDetailForm();
         session.setAttribute("STORE_SVCPROV_DETAIL_FORM", sForm);
         init(request,sForm);
         return ae;
      }
      if (null != dd) {
      BusEntityData bed = dd.getBusEntity();
      // Set the form values.
      sForm.setName(bed.getShortDesc());
      sForm.setStatusCd(bed.getBusEntityStatusCd());
      sForm.setId(String.valueOf(bed.getBusEntityId()));
      sForm.setTypeCd(bed.getBusEntityTypeCd());
      sForm.setStoreId(Integer.toString(dd.getStoreId()));
      
      sForm.setServiceProviderType(dd.getServiceProviderTypeId());
      PhoneData fax;
      sForm.setDetail(dd);
      PhoneData phone;
      fax = dd.getPrimaryFax();
      sForm.setFax(fax.getPhoneNum());
      phone = dd.getPrimaryPhone();
      sForm.setPhone(phone.getPhoneNum());
      EmailData email = dd.getPrimaryEmail();
      sForm.setEmailAddress(email.getEmailAddress());
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
      // Get the specializations for this manufacturer.
//      PropertyDataVector specz = dd.getSpecializations();
//
//      setSPFlags(sForm, specz);
    }
      return ae;
  }
  /**
   *  <code>init</code> method.  Creates the SvcProv.status.vector
   * , countries.vector, and the states.vector session scoped objects.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void init(HttpServletRequest request,
                          ActionForm form) throws Exception {
    HttpSession session = request.getSession();
    // Cache the lists needed for ServiceProviders.
    APIAccess factory = new APIAccess();
    ListService lsvc = factory.getListServiceAPI();
    ServiceProviderType sptEjb = factory.getServiceProviderTypeAPI();
    // Set up the manufacturer status list.
    RefCdDataVector statusv = lsvc.getRefCodesCollection("BUS_ENTITY_STATUS_CD");
    session.setAttribute("SvcProv.status.vector", statusv);
    //RefCdDataVector typev = lsvc.getRefCodesCollection("SERVICE_PROVIDER_CD");    
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    BusEntityDataVector types = sptEjb.getServiceProviderTypesForStore(appUser.getUserStore().getStoreId(), false);
    session.setAttribute("SvcProv.type.vector", types);
    
    Country countryBean = factory.getCountryAPI();
    CountryDataVector countriesv = countryBean.getAllCountries();
    session.setAttribute("countries.vector", countriesv);
    
//    session.removeAttribute("user.distributor.vector");
//    session.removeAttribute("user.assoc.site.ids");
//    session.removeAttribute("user.siteview.vector");
    
    return;
  }
  /**
   *  <code>search</code> for the manufacturer(s)
   according to the criteria specified.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void search(HttpServletRequest request,
                            ActionForm form) throws Exception {
    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    StoreServiceProviderMgrSearchForm sForm = (StoreServiceProviderMgrSearchForm) form;
    String fieldValue = sForm.getSearchField();
    String fieldSearchType = sForm.getSearchType();
    session.setAttribute("StoreServiceProvider.found.vector",
                         search(request, fieldValue, fieldSearchType, sForm.getSearchShowInactiveFl()));
  }
  public static ServiceProviderDataVector search(HttpServletRequest request,
                                              String fieldValue,
                                              String fieldSearchType,
                                              boolean showInactiveFl) throws
    Exception {
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    Service svcProvBean = factory.getServiceAPI();
    // Reset the session variables.
    ServiceProviderDataVector dv = new ServiceProviderDataVector();
    session.setAttribute("StoreServiceProvider.found.vector", dv);
    BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
    if (fieldSearchType.equals("id")) {
      Integer id = new Integer(fieldValue);
      crit.setSearchId(fieldValue);
    } else {
      crit.setSearchName(fieldValue);
      // Lookup by name.  Two assumptions are made: 1) user may
      // have entered the whole name or the initial part of the
      // name; 2) the search is case insensitive.
      if (fieldSearchType.equals("nameBegins")) {
        crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
      } else { // nameContains
        crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
      }
    }
    crit.setSearchForInactive(showInactiveFl);
    dv = svcProvBean.getServiceProviderByCriteria(crit);
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
                          ActionForm form) throws Exception {
    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    ServiceProviderDataVector svcprs =
      (ServiceProviderDataVector) session.getAttribute("StoreServiceProvider.found.vector");
    if (svcprs == null) {
      return;
    }
    String sortField = request.getParameter("sortField");
    DisplayListSort.sort(svcprs, sortField);
  }
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
  /**
   *   <code>addServiceProvider</code>, clears the STORE_SVCPROV_DETAIL_FORM.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void addServiceProvider(HttpServletRequest request,
                                     ActionForm form) throws Exception {
    HttpSession session = request.getSession();
    StoreServiceProviderMgrDetailForm sForm = new StoreServiceProviderMgrDetailForm();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    sForm.setStoreId("" + appUser.getUserStore().getStoreId());
    session.setAttribute("STORE_SVCPROV_DETAIL_FORM", sForm);
  }
  /**
   *  <code>updateServiceProvider</code>, update the values pertaining
   to a service provider with the data carried in the STORE_SVCPROV_DETAIL_FORM.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@return                an <code>ActionErrors</code> value
   *@exception  Exception  if an error occurs
   */
  public static ActionErrors updateServiceProvider(HttpServletRequest request,
                                                ActionForm form) throws
    Exception {
    ActionErrors lUpdateErrors = new ActionErrors();
    HttpSession session = request.getSession();
    StoreServiceProviderMgrDetailForm sForm = (StoreServiceProviderMgrDetailForm) form;
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if (sForm != null) {
	  //STJ - 3846
	  if(sForm.getEmailAddress()!=null) {
		  sForm.setEmailAddress(sForm.getEmailAddress().trim());
	  }
      // Verify the form information submitted.
      verifyUpdateValues(sForm, lUpdateErrors);
      EmailValidator.validateEmail(request, lUpdateErrors, "Email", sForm.getEmailAddress());
    }
    if (lUpdateErrors.size() > 0) {
      // Report the errors to allow for edits.
      return lUpdateErrors;
    }
    APIAccess factory = new APIAccess();
    Service serviceProviderBean = factory.getServiceAPI();
    int serviceProviderId = 0;
    if (!sForm.getId().equals("")) {
      serviceProviderId = Integer.parseInt(sForm.getId());
    }
    // Get the current information for this ServiceProvider.
    ServiceProviderData dd;
    BusEntityData bed;
    PropertyDataVector props;
    AddressData address;
    EmailData email;
    PhoneData phone, fax;
    if (serviceProviderId > 0) {
      dd = serviceProviderBean.getServiceProvider(serviceProviderId);
    } else {
      dd = ServiceProviderData.createValue();
    }
    dd.setStoreId(appUser.getUserStore().getStoreId());
    
    dd.setServiceProviderTypeId(sForm.getServiceProviderType());
    bed = dd.getBusEntity();
//    props = dd.getSpecializations();
    email = dd.getPrimaryEmail();
    fax = dd.getPrimaryFax();
    phone = dd.getPrimaryPhone();
    address = dd.getPrimaryAddress();    
    // XXX, values to be determined.
    bed.setWorkflowRoleCd("UNKNOWN");
    String cuser = (String) session.getAttribute(Constants.USER_NAME);
    // Now update with the data from the form.
    bed.setShortDesc(sForm.getName());
    bed.setLongDesc(sForm.getName());
    bed.setBusEntityStatusCd(sForm.getStatusCd());
    bed.setLocaleCd("unk");
    bed.setModBy(cuser);
    email.setShortDesc("STORE PROVIDER email");
    email.setModBy(cuser);
    email.setEmailAddress(sForm.getEmailAddress());
    email.setPrimaryInd(true);
    fax.setModBy(cuser);
    fax.setPhoneAreaCode(" ");
    fax.setPhoneCountryCd(" ");
    fax.setPhoneNum(sForm.getFax());
    fax.setPrimaryInd(true);
    fax.setShortDesc("STORE PROVIDER fax");
    phone.setModBy(cuser);
    phone.setPhoneAreaCode(" ");
    phone.setPhoneCountryCd(" ");
    phone.setPhoneNum(sForm.getPhone());
    phone.setPrimaryInd(true);
    phone.setShortDesc("STORE PROVIDER phone");
    String stateProvince =  sForm.getStateOrProv();
    if (stateProvince.length() == 0) {
      stateProvince = " ";
    }
    String pCode =  sForm.getPostalCode();
    if (pCode.length() == 0) {
      pCode = " ";
    }
    String country =  sForm.getCountry();
    if (country.length() == 0) {
      country = " ";
    }
    address.setModBy(cuser);
    address.setName1(sForm.getName1());
    address.setName2(sForm.getName2());
    address.setAddress1(sForm.getStreetAddr1());
    address.setAddress2(sForm.getStreetAddr2());
    address.setAddress3(sForm.getStreetAddr3());
    address.setCity(sForm.getCity());
    address.setStateProvinceCd(stateProvince);
    address.setPostalCode(pCode);
    address.setCountryCd(country);
    address.setPrimaryInd(true);
//    if (props.size() == 0) {
//
//      // Set the specialization properties.
//
//      dd.setSpecializations(createSPFlags(sForm));
//
//    } else {
//
//      getSPFlags(sForm, props);
//
//    }
    if (serviceProviderId == 0) {
      bed.setAddBy(cuser);
      email.setAddBy(cuser);
      fax.setAddBy(cuser);
      phone.setAddBy(cuser);
      address.setAddBy(cuser);
      dd = serviceProviderBean.addServiceProvider(dd);
      sForm.setId(String.valueOf(dd.getBusEntity().getBusEntityId()));
    } else {
      // Now update this Service Provider
    	serviceProviderBean.updateServiceProvider(dd);
    }
    sForm.setDetail(dd);
    session.setAttribute("STORE_SVCPROV_DETAIL_FORM", sForm);
    return lUpdateErrors;
  }
  /**
   *  The <code>delete</code> method removes the database entries defining
   *  this service provider if no other database entry is dependent on it.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   *@see                   com.cleanwise.service.api.session.ServiceProvider
   */
  public static ActionErrors delete(HttpServletRequest request,
                                    ActionForm form) throws Exception {
    ActionErrors deleteErrors = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    Service svcProvBean = factory.getServiceAPI();
    String strid = request.getParameter("id");
    if (strid == null || strid.length() == 0) {
      deleteErrors.add("id", new ActionError("error.badRequest", "id"));
      return deleteErrors;
    }
    Integer id = new Integer(strid);
    ServiceProviderData dd = svcProvBean.getServiceProvider(id.intValue());
    if (null != dd) {
      try {
        svcProvBean.removeServiceProvider(dd);
      } catch (Exception e) {
        deleteErrors.add("id",
                         new ActionError("error.deleteFailed",
                                         "ServiceProvider"));
        return deleteErrors;
      }
      session.removeAttribute("StoreServiceProvider.found.vector");
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
	StoreServiceProviderMgrDetailForm detailForm = (StoreServiceProviderMgrDetailForm) form;
    String confFunc = "";  // detailForm.getConfFunction();
    UserInfoData uid = null; // detailForm.getDetail();
    String userType = ""; //uid.getUserData().getUserTypeCd();
    if(RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(userType)) {
      confFunc = "distConfig";
      //detailForm.setConfFunction(confFunc);
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
    APIAccess factory = new APIAccess();
    User userBean = factory.getUserAPI();
    Group groupBean = factory.getGroupAPI();
    StoreUserMgrForm detailForm = (StoreUserMgrForm) form;
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
  
  public static ActionErrors searchSitesToConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    StoreServiceProviderMgrDetailForm detailForm = (StoreServiceProviderMgrDetailForm) form;
    if(detailForm.getConifiguredOnlyFl()) {
      ae = searchSPSiteConfig(request,form);
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
  public static ActionErrors searchSiteConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
    CleanwiseUser adminUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int storeId = adminUser.getUserStore().getBusEntity().getBusEntityId();
    StoreServiceProviderMgrDetailForm detailForm = (StoreServiceProviderMgrDetailForm) form;
    ServiceProviderData serviceProviderData = detailForm.getDetail();
    int sPId = serviceProviderData.getBusEntity().getBusEntityId();
    APIAccess factory = new APIAccess();
    Site siteEjb = factory.getSiteAPI();
    Service serviceProviderEjb = factory.getServiceAPI();
    
    IdVector accountIds = serviceProviderEjb.getSvcProviderAccountIds(sPId, storeId, false);
    if (accountIds.size() == 0) accountIds.add(new Integer(-1));
    SiteViewVector sitesV = new SiteViewVector();
    
    String fieldValue = detailForm.getConfSearchField();
    String fieldSearchRefNum = detailForm.getSearchRefNum().trim();
    String city = detailForm.getConfCity();
    String state = detailForm.getConfState();
    
    String searchType = detailForm.getConfSearchType();
    String searchRefNumType = detailForm.getSearchRefNumType();
    fieldValue.trim();
    QueryRequest qr = new QueryRequest();
    qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);
    String field = "";
    if (searchType.equals("id") && fieldValue.length() > 0 ) {
        Integer id = new Integer(fieldValue);
        qr.filterBySiteId(id.intValue());
        sitesV = siteEjb.getSiteCollection(qr);
    } else if (searchType.equals("nameBegins") && fieldValue.length() > 0 ) {
        qr.filterByOnlySiteName(fieldValue, QueryRequest.BEGINS_IGNORE_CASE);
    } else if (searchType.equals("nameContains") && fieldValue.length() > 0) {
        qr.filterByOnlySiteName(fieldValue, QueryRequest.CONTAINS_IGNORE_CASE);
    }
    if (fieldSearchRefNum != null && fieldSearchRefNum.length() > 0) {
        if (searchRefNumType.equals("nameBegins"))  {
            qr.filterByRefNum(fieldSearchRefNum,QueryRequest.BEGINS_IGNORE_CASE);
    	} else if (searchRefNumType.equals("nameContains") ) {
            qr.filterByRefNum(fieldSearchRefNum,QueryRequest.CONTAINS_IGNORE_CASE);
    	}
    }
    field = city.trim();
    if (field.length() > 0) {
        qr.filterByCity(field, QueryRequest.BEGINS_IGNORE_CASE);
    }
    field = state.trim();
    if (field.length() > 0) {
        qr.filterByState(field, QueryRequest.BEGINS_IGNORE_CASE);
    }
    qr.filterByAccountIds(accountIds);
    sitesV = siteEjb.getSiteCollection(qr);
    session.setAttribute("user.siteview.vector", sitesV);
    initSiteConfigDisplay(request, form);
    
    return ae;
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
    CleanwiseUser adminUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int storeId = adminUser.getUserStore().getBusEntity().getBusEntityId();
    APIAccess factory = new APIAccess();
    Service serviceProviderEjb = factory.getServiceAPI();
    
    StoreServiceProviderMgrDetailForm detailForm = (StoreServiceProviderMgrDetailForm) form;
    ServiceProviderData serviceProviderData = detailForm.getDetail();
    int sPId = serviceProviderData.getBusEntity().getBusEntityId();
    IdVector siteIds = serviceProviderEjb.getSvcProviderSiteIds(sPId, storeId, true);
    
    String[] selectIds = new String[siteIds.size()];
    Iterator it = siteIds.iterator();
    for (int i = 0; it.hasNext(); i++) {
      selectIds[i] = new String(((Integer)it.next()).toString());
    }
    // the currently associated sites are checked/selected
    detailForm.setConfSelectIds(selectIds);
    // list of all associated site ids, in the update this
    // will be compared with the selected
    session.setAttribute("user.assoc.site.ids", siteIds);
  }
  
  
  /**
   * <code>searchUserSiteConfig</code>
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors searchSPSiteConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    CleanwiseUser adminUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int storeId = adminUser.getUserStore().getBusEntity().getBusEntityId();
    StoreServiceProviderMgrDetailForm detailForm = (StoreServiceProviderMgrDetailForm) form;
    ServiceProviderData serviceProviderData = detailForm.getDetail();
    int sPId = serviceProviderData.getBusEntity().getBusEntityId();
    APIAccess factory = new APIAccess();
    Site siteEjb = factory.getSiteAPI();
    Service serviceProviderEjb = factory.getServiceAPI();
    
    IdVector accountIds = serviceProviderEjb.getSvcProviderAccountIds(sPId, storeId, false);
    if (accountIds.size() == 0) accountIds.add(new Integer(-1));
    SiteViewVector sitesV = new SiteViewVector();
    
    String fieldValue = detailForm.getConfSearchField();
    String fieldSearchRefNum = detailForm.getSearchRefNum().trim();
    String city = detailForm.getConfCity();
    String state = detailForm.getConfState();
    
    String searchType = detailForm.getConfSearchType();
    String searchRefNumType = detailForm.getSearchRefNumType();
    fieldValue.trim();
    QueryRequest qr = new QueryRequest();
    qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);
    if (searchType.equals("id") && fieldValue.length() > 0 ) {
        Integer id = new Integer(fieldValue);
        qr.filterBySiteId(id.intValue());
        sitesV = siteEjb.getSiteCollection(qr);
    } else if (searchType.equals("nameBegins") && fieldValue.length() > 0 ) {
        qr.filterByOnlySiteName(fieldValue, QueryRequest.BEGINS_IGNORE_CASE);
    } else if (searchType.equals("nameContains") && fieldValue.length() > 0) {
        qr.filterByOnlySiteName(fieldValue, QueryRequest.CONTAINS_IGNORE_CASE);
    }
    if (fieldSearchRefNum != null && fieldSearchRefNum.length() > 0) {
        if (searchRefNumType.equals("nameBegins"))  {
            qr.filterByRefNum(fieldSearchRefNum,QueryRequest.BEGINS_IGNORE_CASE);
    	} else if (searchRefNumType.equals("nameContains") ) {
            qr.filterByRefNum(fieldSearchRefNum,QueryRequest.CONTAINS_IGNORE_CASE);
    	}
    }
    String field = city.trim();
    if (field.length() > 0) {
        qr.filterByCity(field, QueryRequest.BEGINS_IGNORE_CASE);
    }
    field = state.trim();
    if (field.length() > 0) {
        qr.filterByState(field, QueryRequest.BEGINS_IGNORE_CASE);
    }
    qr.filterByAccountIds(accountIds);
    sitesV = siteEjb.getSiteCollectionByServiceProvider(qr, sPId);
    session.setAttribute("user.siteview.vector", sitesV);
    
    // initSiteConfigDisplay
    IdVector siteIds = Utility.toIdVector(sitesV);
    
    String[] selectIds = new String[siteIds.size()];
    Iterator it = siteIds.iterator();
    for (int i = 0; it.hasNext(); i++) {
      selectIds[i] = new String(((Integer)it.next()).toString());
    }
    
    detailForm.setConfSelectIds(selectIds);
    session.setAttribute("user.assoc.site.ids", siteIds);
    
    return ae;
  }  
  
  public static ActionErrors searchAcctToConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    BusEntitySearchCriteria besc = new BusEntitySearchCriteria();
    StoreServiceProviderMgrDetailForm detailForm = (StoreServiceProviderMgrDetailForm) form;
    if(detailForm.getConifiguredOnlyFl()) {
      ae = searchSPAcctConfig(request,form);
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
    StoreServiceProviderMgrDetailForm detailForm = (StoreServiceProviderMgrDetailForm) form;
    IdVector accountIds = new IdVector();
    ServiceProviderData serviceProviderData = detailForm.getDetail();
//    int userId = serviceProviderData.getUserData().getUserId();
    int sPId = serviceProviderData.getBusEntity().getBusEntityId();
    APIAccess factory = new APIAccess();
    Account acctBean = factory.getAccountAPI();
    User userBean = factory.getUserAPI();
    Service serviceProviderBean = factory.getServiceAPI();
    String searchType = detailForm.getConfSearchType();
    String fieldValue = detailForm.getConfSearchField();
    AccountViewVector accountVwV = new AccountViewVector();
    HashSet sPAcctIdHS = new HashSet();
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
//        BusEntityDataVector beDV = userBean.getBusEntityCollection(sPId,
//            RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT, shortDesc);
//        BusEntityDataVector beDV = userBean.getBusEntityCollection(sPId,
//                RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT, shortDesc); 
        IdVector assignedAccountIds =
            serviceProviderBean.getSvcProviderAccountIds(sPId, storeId, true);
        for(Iterator iter = assignedAccountIds.iterator(); iter.hasNext();) {
          Integer accountId = (Integer) iter.next();
          if(accountId.intValue()==id) {
            sPAcctIdHS.add(accountId);
            break;
          }
        }
//        sPAcctIdHS.add(new Integer(acctBeD.getBusEntityId()));
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
      accountVwV = acctBean.getAccountsViewList(besc);
//      besc.addUserId(sPId);
//      AccountViewVector aVwV = acctBean.getAccountsViewList(besc);
      IdVector assignedAccountIds =
          serviceProviderBean.getSvcProviderAccountIds(sPId, storeId, true);
      
//      for(Iterator iter = aVwV.iterator(); iter.hasNext();) {
//        AccountView aVw = (AccountView) iter.next();
//        sPAcctIdHS.add(new Integer(aVw.getAcctId()));
//      }
      for(Iterator iter = assignedAccountIds.iterator(); iter.hasNext();) {
          Integer a = (Integer) iter.next();
          sPAcctIdHS.add(a);
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
      if(sPAcctIdHS.contains(new Integer(id))){
        selectIdA[indexSel++] = String.valueOf(id);
      }
    }
    detailForm.setConfSelectIds(selectIdA);
    detailForm.setConfDisplayIds(displIdA);
    return ae;
  }
  
    public static ActionErrors updateStatus
        (HttpServletRequest request,
         ActionForm form,
         String pNewUserStatusCd)
             throws Exception    	{
        ActionErrors lUpdateErrors = new ActionErrors();
        StoreServiceProviderMgrDetailForm sForm = (StoreServiceProviderMgrDetailForm) form;
        log.info("updateStatus 13");
        ServiceProviderData serviceProviderData = sForm.getDetail();
        log.info("updateStatus 13 ud=" + serviceProviderData);
//        serviceProviderData.setUserStatusCd(pNewUserStatusCd);
        return null;   //_updateReq(request, sForm, serviceProviderData);
}
  
  public static ActionErrors searchSPAcctConfig(HttpServletRequest request,
          ActionForm form)
          throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    CleanwiseUser adminUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int storeId = adminUser.getUserStore().getBusEntity().getBusEntityId();
    StoreServiceProviderMgrDetailForm detailForm = (StoreServiceProviderMgrDetailForm) form;
        ServiceProviderData serviceProviderData = detailForm.getDetail();
    int sPId = serviceProviderData.getBusEntity().getBusEntityId();
    APIAccess factory = new APIAccess();
    Account acctBean = factory.getAccountAPI();
	Service serviceProviderBean = factory.getServiceAPI();
    String searchType = detailForm.getConfSearchType();
    String fieldValue = detailForm.getConfSearchField();
    AccountViewVector accountVwV = new AccountViewVector();
	HashSet sPAcctIdHS = new HashSet();
    if (searchType.equals("id") && Utility.isSet(fieldValue) ) {
      try {
        int id = Integer.parseInt(fieldValue);
        AccountData accountD = acctBean.getAccount(id, storeId);
        //String shortDesc = accountD.getBusEntity().getShortDesc();
        IdVector assignedAccountIds =
            serviceProviderBean.getSvcProviderAccountIds(sPId, storeId, true);
        for(Iterator iter = assignedAccountIds.iterator(); iter.hasNext();) {
            Integer accountId = (Integer) iter.next();
        	if(accountId.intValue()==id) {
                AccountView aVw = AccountView.createValue();
                aVw.setStoreId(storeId);
                BusEntityData acctBeD = accountD.getBusEntity();
                aVw.setAcctId(acctBeD.getBusEntityId());
                aVw.setAcctName(acctBeD.getShortDesc());
                aVw.setAcctStatusCd(acctBeD.getBusEntityStatusCd());
                accountVwV.add(aVw);
                break;
              }
//            if(accountId.intValue()==id) {
//              sPAcctIdHS.add(accountId);
//              break;
//            }
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
      accountVwV = acctBean.getAccountsViewList(besc);
      IdVector assignedAccountIds =
          serviceProviderBean.getSvcProviderAccountIds(sPId, storeId, true);
      for(Iterator iter = assignedAccountIds.iterator(); iter.hasNext();) {
          Integer a = (Integer) iter.next();
          for(Iterator avwvIterator = accountVwV.iterator(); avwvIterator.hasNext();) {
        	  AccountView accV = (AccountView) avwvIterator.next();
        	  if (accV.getAcctId()==a.intValue()) {
        		  sPAcctIdHS.add(a);
        	  }
          }
      }
      for(Iterator deleteIterator = accountVwV.iterator(); deleteIterator.hasNext();) {
    	  if(!sPAcctIdHS.contains(new Integer(((AccountView) deleteIterator.next()).getAcctId()))) {
    		  deleteIterator.remove();
    	  } 
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
      selectIdA[indexSel++] = String.valueOf(id);
    }
    detailForm.setConfSelectIds(selectIdA);
    detailForm.setConfDisplayIds(displIdA);
    return ae;
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
    Service sPBean = factory.getServiceAPI();
    StoreServiceProviderMgrDetailForm detailForm = (StoreServiceProviderMgrDetailForm) form;
    ServiceProviderData sPD = detailForm.getDetail();
    int serviceProviderId = sPD.getBusEntity().getBusEntityId();
    
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    String admin = appUser.getUser().getUserName();
    int storeId = appUser.getUserStore().getBusEntity().getBusEntityId();
    try {
      sPBean.configureAllAccounts(serviceProviderId,storeId,admin);
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
    StoreServiceProviderMgrDetailForm detailForm = (StoreServiceProviderMgrDetailForm)form;
    String[] displayed = detailForm.getConfDisplayIds();
    String[] selected = detailForm.getConfSelectIds();
    // get list of currently associated site ids
    IdVector assocSiteIds =
            (IdVector)session.getAttribute("user.assoc.site.ids");
    ae = updateConfig(displayed,
                      selected,
                      RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_SITE,
                      factory,
                      request);
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
    Service serviceProviderBean = factory.getServiceAPI();
    StoreServiceProviderMgrDetailForm detailForm = (StoreServiceProviderMgrDetailForm)form;
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
            RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_ACCOUNT,
            factory,request);
    if(ae.size() > 0) {
      searchAcctToConfig(request, form);
      return ae;
    }
    ae = searchAcctToConfig(request, form);
    return ae;
  }
  /*
   *Updates the configuration when using the site config screen.
   */
	private static ActionErrors updateConfig (String[] displayed,
                                                  String[] selected,
                                                  String pAT,
                                                  APIAccess factory,
                                                  HttpServletRequest request)
                                                  throws Exception {
	  ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();
	  CleanwiseUser adminUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	  int storeId = adminUser.getUserStore().getBusEntity().getBusEntityId();
	
	  StoreServiceProviderMgrDetailForm detailForm =
	          (StoreServiceProviderMgrDetailForm)session.getAttribute("STORE_SVCPROV_DETAIL_FORM");
	  int serviceProviderId = detailForm.getDetail().getBusEntity().getBusEntityId(); //.getUserData().getUserId();
	  if ( request.getParameter("sprid") != null ) {
	    serviceProviderId = Integer.parseInt
	            ((String)request.getParameter("sprid"));
	    log.info("parameter sprId=" + serviceProviderId);
	  }
	
	  Service serviceProviderEjb = factory.getServiceAPI();
	  Site siteEjb = factory.getSiteAPI();
	  // Looking for two cases:
	  // 1. site is selected, but not currently associated - this
	  //    means we need to add the association
	  // 2. site is not selected, but is currently associated - this
	  //    means we need to remove the association
          
          SiteViewVector sitesV;
          QueryRequest qr;
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
                  serviceProviderEjb.addAssoc(serviceProviderId, id.intValue(), pAT);
              } else {
                  // we need to remove the association, the selected list
                  if (RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_ACCOUNT.equals(pAT) &&
                      !detailForm.getRemoveSiteAssocFl()) {
                      IdVector assingedAccountIds = serviceProviderEjb.getSvcProviderAccountIds(serviceProviderId,
                                                                                                storeId,
                                                                                                false);
                      if (!assingedAccountIds.contains(id)) {
                          continue; //nothing to remove
                      }
                      //Check assigned account related sites
                      IdVector accountIds = new IdVector();
                      accountIds.add(id);
                      qr = new QueryRequest();
                      qr.setResultLimit(3);
                      qr.filterByAccountIds(accountIds);
                      sitesV = siteEjb.getSiteCollectionByServiceProvider(qr, serviceProviderId);
                      if (sitesV.size() > 0) {
                          String errorMess = "Can't remove account association. Account " + id + " has configured sites: ";
                          SiteView sVw;
                          for(Iterator iter = sitesV.iterator(); iter.hasNext();) {
                              sVw = (SiteView) iter.next();
                              errorMess += " <" + sVw.getName() + "> ";
                          }
                          ae.add("error",new ActionError("error.simpleGenericError", errorMess));
                          return ae;
                      }
                  } else if (RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_SITE.equals(pAT) &&
                             !detailForm.getRemoveSiteAssocFl()) {
                      
                      IdVector accountIds = serviceProviderEjb.getSvcProviderAccountIds(serviceProviderId,
                                                                                        storeId,
                                                                                        false);
                      qr = new QueryRequest();
                      qr.setResultLimit(Constants.MAX_SITES_TO_RETURN);
                      qr.filterByAccountIds(accountIds);
                      sitesV = siteEjb.getSiteCollectionByServiceProvider(qr, serviceProviderId);
                      IdVector assignedSiteIds = Utility.toIdVector(sitesV);
                      if (!assignedSiteIds.contains(id)) {
                          continue; //nothing to remove
                      }
                  }
                  serviceProviderEjb.removeAssoc(serviceProviderId, id.intValue());
              }
	  }
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
  
  /**
   *  Description of the Method
   *
   *@param  pDataErrors  Description of Parameter
   *@param  pForm        Description of Parameter
   */
  private static void verifyUpdateValues(StoreServiceProviderMgrDetailForm pForm,
                                         ActionErrors pDataErrors) {
    if (pForm.getName().length() == 0) {
      pDataErrors.add("name", new ActionError("variable.empty.error", "Name"));
    }
    if (pForm.getStatusCd().length() == 0) {
      pDataErrors.add("statusCd", new ActionError("variable.empty.error", "Status"));
    }
    
    String[] serviceProviderType = (pForm.getServiceProviderType() != null)
                               ? pForm.getServiceProviderType() : new String[0];
    
    if (serviceProviderType.length == 0) {
      pDataErrors.add("typeCd", new ActionError("variable.empty.error", "Type"));
    }
    try {
      Integer.parseInt(pForm.getStoreId());
    } catch (NumberFormatException e) {
      pDataErrors.add("storeId",
                      new ActionError("error.invalidNumber", "Store Id"));
    }
  }
}
