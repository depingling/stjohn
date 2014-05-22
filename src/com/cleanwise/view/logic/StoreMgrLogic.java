package com.cleanwise.view.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.StoreMgrSearchForm;
import com.cleanwise.view.forms.StoreMgrDetailForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import javax.naming.NamingException;
import org.apache.struts.util.MessageResources;

/**
 *  <code>StoreMgrLogic</code> implements the logic needed to manipulate
 *  store records.
 *
 *@author     tbesser
 *@created    August 23, 2001
 */
public class StoreMgrLogic {

  /**
   *  <code>getAll</code> stores
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void getAll(HttpServletRequest request,
                            ActionForm form) throws Exception {

    // Get a reference to the admin facade
    HttpSession session = request.getSession();

    APIAccess factory = new APIAccess();
    Store storeBean = factory.getStoreAPI();
    StoreDataVector dv;

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
      BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
      crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
      crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
      dv = storeBean.getStoresByCriteria(crit);
    } else {
      dv = storeBean.getAllStores(Store.ORDER_BY_NAME);
    }

    session.setAttribute("Store.found.vector", dv);
    session.setAttribute("Store.found.total",
                         String.valueOf(dv.size()));
  }


  public static void getDetail(HttpServletRequest request,
                               ActionForm form) throws Exception {

    HttpSession session = request.getSession();
    StoreMgrDetailForm sForm =
      (StoreMgrDetailForm) session.getAttribute("STORE_DETAIL_FORM");

    init(request, form);

    if (sForm == null) {
      sForm = new StoreMgrDetailForm();
      session.setAttribute("STORE_DETAIL_FORM", sForm);
    }

    String fieldValue = request.getParameter("searchField");
    if (null == fieldValue) {
      fieldValue = "0";
    }
    int storeid = Integer.parseInt(fieldValue);
    getDetail(request, storeid);
  }

  public static void getDetail(HttpServletRequest request, int pStoreId) throws Exception {

    APIAccess factory = new APIAccess();
    Store storeBean = factory.getStoreAPI();

    HttpSession session = request.getSession();
    StoreMgrDetailForm sForm =
      (StoreMgrDetailForm) session.getAttribute("STORE_DETAIL_FORM");

    if (pStoreId == 0) {
      String v = (String) session.getAttribute("Store.id");
      if (v == null || v.trim().length() == 0) {
        pStoreId = 0;
      } else {
        pStoreId = Integer.parseInt(v);
      }
    }

    StoreData dd = storeBean.getStore(pStoreId);
    if (null != dd) {
      // Set the form values.
      sForm.setStoreBusinessName(dd.getStoreBusinessName().getValue());
      sForm.setStorePrimaryWebAddress
        (dd.getStorePrimaryWebAddress().getValue());

      BusEntityData bed = dd.getBusEntity();
      sForm.setName(bed.getShortDesc());
      sForm.setDescription(bed.getLongDesc());
      sForm.setStatusCd(bed.getBusEntityStatusCd());
      sForm.setId(String.valueOf(bed.getBusEntityId()));
      sForm.setTypeCd(bed.getBusEntityTypeCd());
      sForm.setLocale(bed.getLocaleCd());

      PropertyData storeType = dd.getStoreType();
      sForm.setTypeDesc(storeType.getValue());

      sForm.setCallHours(dd.getCallHours().getValue());
      sForm.setEvenRowColor(dd.getEvenRowColor());
      sForm.setOddRowColor(dd.getOddRowColor());

      PropertyData prefix = dd.getPrefix();
      sForm.setPrefix(prefix.getValue());

      PhoneData phone = dd.getPrimaryPhone();
      sForm.setPhone(phone.getPhoneNum());

      PhoneData fax = dd.getPrimaryFax();
      sForm.setFax(fax.getPhoneNum());

      EmailData customerEmail = dd.getCustomerServiceEmail();
      sForm.setCustomerEmail(customerEmail.getEmailAddress());

      EmailData contactEmail = dd.getContactUsEmail();
      sForm.setContactEmail(contactEmail.getEmailAddress());

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

      sForm.setSkuWorkflowFlag
        (new Boolean
         (getSkuWorkflowSetting(pStoreId)));
      session.setAttribute("STORE_DETAIL_FORM", sForm);
      session.setAttribute("Store.id", String.valueOf(pStoreId));
      session.setAttribute("Store.name", bed.getShortDesc());

      PropertyDataVector prop = dd.getMiscProperties();
      int orderNumberStoreId = 1; //Cleanwise store id
      String erpSystem = "";
      String contactUsType = RefCodeNames.CONTACT_US_TYPE_CD.STORE;
      sForm.setAutoSkuAssign(false);
      sForm.setRequireExternalSysLogon(false);
      sForm.setTaxableIndicator(false);
      sForm.setOrderProcessingSplitTaxExemptOrders(false);
      sForm.setShowDistrNotes(false);
      sForm.setCleanwiseOrderNumFlag(false);
      sForm.setIncludeAccountNameInSiteAddress(false);
      sForm.setAllowPONumberByVendor(false);
      for (int ii = 0; ii < prop.size(); ii++) {
        PropertyData pD = (PropertyData) prop.get(ii);
        String propType = pD.getPropertyTypeCd();
        String propValue = pD.getValue();
        if (RefCodeNames.PROPERTY_TYPE_CD.ERP_SYSTEM.equals(propType)) {
          erpSystem = propValue;
        } else if (RefCodeNames.PROPERTY_TYPE_CD.ORDER_NUMBERING_STORE_ID.equals(propType)) {
          try {
            orderNumberStoreId = Integer.parseInt(propValue);
          } catch (Exception exc) {
            String errorMess = "Wrong ORDER_NUMBERING_STORE_ID property format: " + propValue;
            throw new Exception(errorMess);
          }
        } else if (RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_TYPE_CD.equals(propType)) {
          contactUsType = propValue;
        } else if (RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_NAME_IN_SITE_ADDRESS.equals(propType)) {
          sForm.setIncludeAccountNameInSiteAddress(Utility.isTrue(propValue));
        } else if (RefCodeNames.PROPERTY_TYPE_CD.AUTO_SKU_ASSIGN.equals(propType)) {
          sForm.setAutoSkuAssign(Utility.isTrue(propValue));
        } else if (RefCodeNames.PROPERTY_TYPE_CD.REQUIRE_EXTERNAL_SYS_LOGON.equals(propType)) {
          sForm.setRequireExternalSysLogon(Utility.isTrue(propValue));
        } else if (RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR.equals(propType)) {
          sForm.setTaxableIndicator(Utility.isTrue(propValue));
        } else if (RefCodeNames.PROPERTY_TYPE_CD.SHOW_DISTR_NOTES_TO_CUSTOMER.equals(propType)) {
          sForm.setShowDistrNotes(Utility.isTrue(propValue));
        } else if (RefCodeNames.PROPERTY_TYPE_CD.ORDER_PROCESSING_SPLIT_TAX.equals(propType)) {
          sForm.setOrderProcessingSplitTaxExemptOrders(Utility.isTrue(propValue));
        } else if (RefCodeNames.PROPERTY_TYPE_CD.ALLOW_PO_NUM_BY_VENDER.equals(propType)) {
            sForm.setAllowPONumberByVendor(Utility.isTrue(propValue));
        }

      }
      if (orderNumberStoreId == 1) {
        sForm.setCleanwiseOrderNumFlag(true);
      } else {
        sForm.setCleanwiseOrderNumFlag(false);
      }
      sForm.setErpSystem(erpSystem);
      sForm.setContactUsType(contactUsType);
    }
  }

  // Get the workflow setting for the store.
  public static boolean getSkuWorkflowSetting(int pStoreId) throws Exception {

    APIAccess factory = new APIAccess();
    Workflow wkflBean = factory.getWorkflowAPI();
    WorkflowData wd = getSkuWorkflow(pStoreId);
    if (null == wd) {
      return false;
    }
    String v = wd.getWorkflowStatusCd();
    return v.equals(RefCodeNames.WORKFLOW_STATUS_CD.ACTIVE);
  }

  // Set the workflow setting for the store.
  public static void setSkuWorkflowSetting(int pStoreId,
                                           boolean pSkuWkflFlag) throws Exception {

    APIAccess factory = new APIAccess();
    Workflow wkflBean = factory.getWorkflowAPI();
    WorkflowData wd = getSkuWorkflow(pStoreId);
    if (null == wd) {
      wd = WorkflowData.createValue();
      if (pSkuWkflFlag) {
        wd.setWorkflowStatusCd
          (RefCodeNames.WORKFLOW_STATUS_CD.ACTIVE);
      } else {
        wd.setWorkflowStatusCd
          (RefCodeNames.WORKFLOW_STATUS_CD.INACTIVE);
      }
      wd.setWorkflowTypeCd(RefCodeNames.WORKFLOW_TYPE_CD.CWSKU);
      wd.setBusEntityId(pStoreId);
      wd.setShortDesc("CW SKU for store id: " + pStoreId);
      wkflBean.createWorkflow(wd);

      return;
    }

    if (pSkuWkflFlag) {
      wd.setWorkflowStatusCd
        (RefCodeNames.WORKFLOW_STATUS_CD.ACTIVE);
    } else {
      wd.setWorkflowStatusCd
        (RefCodeNames.WORKFLOW_STATUS_CD.INACTIVE);
    }

    wkflBean.updateWorkflow(wd);
  }

  // Get the workflow setting for the store.
  public static WorkflowData getSkuWorkflow(int pStoreId) throws Exception {

    APIAccess factory = new APIAccess();
    Workflow wkflBean = factory.getWorkflowAPI();
    WorkflowDataVector wdv =
      wkflBean.getWorkflowCollectionByEntity
      (pStoreId);
    for (int idx = 0; idx < wdv.size(); idx++) {
      WorkflowData wd = (WorkflowData) wdv.get(idx);
      String v = wd.getWorkflowTypeCd();
      if (v.equals(RefCodeNames.WORKFLOW_TYPE_CD.CWSKU)) {
        return wd;
      }
    }
    return null;
  }

  /**
   *  <code>init</code> method.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void init(HttpServletRequest request,
                          ActionForm form) throws Exception {
    HttpSession session = request.getSession();

    // Cache the lists needed for Stores.
    APIAccess factory = new APIAccess();
    ListService lsvc = factory.getListServiceAPI();

    // Set up the store status list.
    if (session.getAttribute("Store.status.vector") == null) {
      RefCdDataVector statusv =
        lsvc.getRefCodesCollection("BUS_ENTITY_STATUS_CD");
      session.setAttribute("Store.status.vector", statusv);
    }

    if (session.getAttribute("country.vector") == null)
    {
    Country countryBean = factory.getCountryAPI();
    CountryDataVector countriesv = countryBean.getAllCountries();
    session.setAttribute("country.vector", countriesv);
    }

    if (session.getAttribute("Store.locale.vector") == null) {
      RefCdDataVector locales =
        lsvc.getRefCodesCollection("LOCALE_CD");
      session.setAttribute("Store.locale.vector", locales);
    }

    if (session.getAttribute("Store.type.vector") == null) {
      RefCdDataVector typesv =
        lsvc.getRefCodesCollection("STORE_TYPE_CD");
      session.setAttribute("Store.type.vector", typesv);
    }
    if (session.getAttribute("contact.type.vector") == null) {
      RefCdDataVector typesv =
        lsvc.getRefCodesCollection("CONTACT_US_TYPE_CD");
      session.setAttribute("contact.type.vector", typesv);
    }

    return;
  }

  /**
   *inits the reasource configuration for the store admin
   */
  public static void initReasourceConfig(HttpServletRequest request,
                                         ActionForm form, MessageResources pResources) throws Exception {
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }
    PropertyService propEjb = factory.getPropertyServiceAPI();
    StoreMgrDetailForm sForm = (StoreMgrDetailForm) form;
    int storeId = sForm.getIntId();

    //load the main resources as a stream, any localized ones can still
    //be used but his one file should be the master one and decare any property
    //that is used.
    String propFileName = pResources.getConfig();
    propFileName = propFileName.replace('.', '/');
    propFileName += ".properties";
    Properties mainRes = new Properties();
    mainRes.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propFileName));

    //get the customized properties from the database
    MessageResourceDataVector mrdv = propEjb.getMessageResourcesWithNulls(storeId, null);

    //match them and the properties for editing
    HashMap existingMap = new HashMap();
    Iterator it = mrdv.iterator();
    while (it.hasNext()) {
      MessageResourceData mess = (MessageResourceData) it.next();
      existingMap.put(mess.getName(), mess);
    }

    //create any resources not in the database, during the adding we won't
    //actually set them
    Enumeration enm = mainRes.propertyNames();
    while (enm.hasMoreElements()) {
      String name = (String) enm.nextElement();
      MessageResourceData mess = (MessageResourceData) existingMap.get(name);
      if (mess == null) {
        MessageResourceData newMess = MessageResourceData.createValue();
        newMess.setName(name);
        mrdv.add(newMess);
      }
    }

    //sort the messageReasources
    DisplayListSort.sort(mrdv, "name");
    sForm.setStoreMessageResources(mrdv);
  }

  /**
   *saves the reasource configuration for the store admin
   */
  public static void saveReasourceConfig(HttpServletRequest request,
                                         ActionForm form) throws Exception {
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    PropertyService propEjb = factory.getPropertyServiceAPI();
    StoreMgrDetailForm sForm = (StoreMgrDetailForm) form;
    int storeId = sForm.getIntId();
    MessageResourceDataVector mrdv = sForm.getStoreMessageResources();
    Iterator it = mrdv.iterator();
    while (it.hasNext()) {
      MessageResourceData resource = (MessageResourceData) it.next();
      //if this was not set, and is not currently in the db then don't send it
      //to ejb for update
      if (resource.getMessageResourceId() == 0 && !Utility.isSet(resource.getValue())) {
        it.remove();
      } else {
        resource.setBusEntityId(storeId);
      }
    }

    propEjb.saveMessageResources(mrdv, appUser.getUserName());
  }

  /**
   *  <code>search</code>
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void search(HttpServletRequest request,
                            ActionForm form) throws Exception {
    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    StoreMgrSearchForm sForm = (StoreMgrSearchForm) form;

    // Reset the session variables.

    session.setAttribute("Store.found.total", "0");
    String fieldValue = sForm.getSearchField();
    String fieldSearchType = sForm.getSearchType();

    StoreDataVector dv = search(request, fieldValue, fieldSearchType, null);

    session.setAttribute("Store.found.vector", dv);
    session.setAttribute("Store.found.total",
                         String.valueOf(dv.size()));
  }

  /**
   *Preforms a search based off the supplied criteria not any form objects.  Returns
   *a StoreDataVector, does not alter the session.
   */
  public static StoreDataVector search(HttpServletRequest request, String fieldValue,
                                       String fieldSearchType, String searchGroupId) throws NamingException,
    APIServiceAccessException, RemoteException {
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    Store storeBean = factory.getStoreAPI();

    BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
    crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
      crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
    }
    if (Utility.isSet(searchGroupId)) {
      crit.setSearchGroupId(searchGroupId);
    }

    if (fieldSearchType.equals("id")) {
      crit.setSearchId(fieldValue);
    } else {
      // Lookup by name.  Two assumptions are made: 1) user may
      // have entered the whole name or the initial part of the
      // name; 2) the search is case insensitive.
      crit.setSearchName(fieldValue);
      if (fieldSearchType.equals("nameBegins")) {
        crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
      } else { // nameContains
        crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
      }
      crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);

    }
    StoreDataVector dv = storeBean.getStoresByCriteria(crit);
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
    StoreDataVector stores =
      (StoreDataVector) session.getAttribute("Store.found.vector");
    if (stores == null) {
      return;
    }
    String sortField = request.getParameter("sortField");
    DisplayListSort.sort(stores, sortField);
  }

  /**
   *  Describe <code>addStore</code> method here.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void addStore(HttpServletRequest request,
                              ActionForm form) throws Exception {

    HttpSession session = request.getSession();
    StoreMgrDetailForm sForm = new StoreMgrDetailForm();
    session.setAttribute("STORE_DETAIL_FORM", sForm);
  }


  /**
   *  Describe <code>updateStore</code> method here.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@return                an <code>ActionErrors</code> value
   *@exception  Exception  if an error occurs
   */
  public static ActionErrors updateStore(HttpServletRequest request,
                                         ActionForm form) throws Exception {
    ActionErrors lUpdateErrors = new ActionErrors();

    HttpSession session = request.getSession();
    StoreMgrDetailForm sForm = (StoreMgrDetailForm) form;
    if (sForm != null) {
      // Verify the form information submitted.
      if (sForm.getName().length() == 0) {
        lUpdateErrors.add("name",
                          new ActionError("variable.empty.error",
                                          "Name"));
      }
      if (sForm.getTypeDesc().length() == 0) {
        lUpdateErrors.add("typeDesc",
                          new ActionError("variable.empty.error",
                                          "Store Type"));
      }
      if (sForm.getStatusCd().length() == 0) {
        lUpdateErrors.add("statusCd",
                          new ActionError("variable.empty.error",
                                          "Status"));
      }
      if (sForm.getName1().length() == 0) {
        lUpdateErrors.add("name1",
                          new ActionError("variable.empty.error",
                                          "First Name"));
      }
      if (sForm.getName2().length() == 0) {
        lUpdateErrors.add("name2",
                          new ActionError("variable.empty.error",
                                          "Last Name"));
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
      if (sForm.getCountry().length() == 0) {
        lUpdateErrors.add("country",
                          new ActionError("variable.empty.error",
                                          "Country"));
      }
      // check country and province
      CountryData country = getCountry(session, sForm);
      APIAccess factory = new APIAccess();
      Country countryBean = factory.getCountryAPI();
      CountryPropertyData countryProp =
        countryBean.getCountryPropertyData(country.getCountryId(), RefCodeNames.COUNTRY_PROPERTY.USES_STATE);
      boolean isStateProvinceRequired = countryProp != null && countryProp.getValue().equalsIgnoreCase("true");

      if (isStateProvinceRequired && !Utility.isSet(sForm.getStateOrProv())) {
        lUpdateErrors.add("stateOrProv",
                          new ActionError("variable.empty.error",
                                          "State/Province"));
      }
      if (!isStateProvinceRequired && Utility.isSet(sForm.getStateOrProv())) {
        lUpdateErrors.add("stateOrProv",
                          new ActionError("variable.must.be.empty.error",
                                          "State/Province"));
      }


      if (sForm.getPostalCode().length() == 0) {
        lUpdateErrors.add("postalCode",
                          new ActionError("variable.empty.error",
                                          "Zip/Postal Code"));
      }
      if (sForm.getPhone().length() == 0) {
        lUpdateErrors.add("phone",
                          new ActionError("variable.empty.error",
                                          "Phone"));
      }
      if (sForm.getFax().length() == 0) {
        lUpdateErrors.add("fax",
                          new ActionError("variable.empty.error",
                                          "Fax"));
      }
      if (sForm.getEmailAddress().length() == 0) {
        lUpdateErrors.add("emailAddress",
                          new ActionError("variable.empty.error",
                                          "Email"));
      }
      if (sForm.getContactEmail().length() == 0) {
        lUpdateErrors.add("contactEmail",
                          new ActionError("variable.empty.error",
                                          "Contact Us Email"));
      }
      if (sForm.getCustomerEmail().length() == 0) {
        lUpdateErrors.add("customerEmail",
                          new ActionError("variable.empty.error",
                                          "Customer Service Email"));
      }
      if (sForm.getLocale().length() == 0) {
        lUpdateErrors.add("locale",
                          new ActionError("variable.empty.error",
                                          "Default Locale"));
      }
      if (sForm.getStoreBusinessName().length() == 0) {
        lUpdateErrors.add("storeBusinessName",
                          new ActionError("variable.empty.error",
                                          "Store Business Name"));
      }
      if (sForm.getStorePrimaryWebAddress().length() == 0) {
        lUpdateErrors.add("StorePrimaryWebAddress",
                          new ActionError("variable.empty.error",
                                          "Store Primary Web Address"));
      }

    }

    if (lUpdateErrors.size() > 0) {
      // Report the errors to allow for edits.
      return lUpdateErrors;
    }
    APIAccess factory = new APIAccess();
    Store storeBean = factory.getStoreAPI();

    int storeid = 0;
    if (!sForm.getId().equals("")) {
      storeid = Integer.parseInt(sForm.getId());
    }
    // Get the current information for this Store.
    StoreData dd;
    BusEntityData bed;
    AddressData address;
    EmailData email;
    EmailData contactEmail;
    EmailData customerEmail;
    PhoneData phone;
    PhoneData fax;
    PropertyData storeType;
    PropertyData storePrefix;
    PropertyData storeBusinessName;
    PropertyData callHours;
    PropertyData storePrimaryWebAddress;
    PropertyDataVector miscPropV;

    if (storeid > 0) {
      dd = storeBean.getStore(storeid);
    } else {
      CleanwiseUser appUser = ShopTool.getCurrentUser(session);
      if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
        lUpdateErrors.add(ActionErrors.GLOBAL_ERROR,
                          new ActionError("error.unauthorized"));
        return lUpdateErrors;
      }
      dd = StoreData.createValue();
    }

    bed = dd.getBusEntity();
    address = dd.getPrimaryAddress();
    phone = dd.getPrimaryPhone();
    fax = dd.getPrimaryFax();
    email = dd.getPrimaryEmail();
    contactEmail = dd.getContactUsEmail();
    customerEmail = dd.getCustomerServiceEmail();
    storeType = dd.getStoreType();
    storePrefix = dd.getPrefix();
    storeBusinessName = dd.getStoreBusinessName();
    storePrimaryWebAddress = dd.getStorePrimaryWebAddress();
    miscPropV = dd.getMiscProperties();
    callHours = dd.getCallHours();

    callHours.setValue(sForm.getCallHours());
    dd.setEvenRowColor(sForm.getEvenRowColor());
    dd.setOddRowColor(sForm.getOddRowColor());

    // XXX, values to be determined.
    bed.setWorkflowRoleCd("UNKNOWN");

    String cuser = (String) session.getAttribute(Constants.USER_NAME);

    // Now update with the data from the form.
    bed.setShortDesc(sForm.getName());
    bed.setLongDesc(sForm.getDescription());
    bed.setBusEntityStatusCd(sForm.getStatusCd());
    bed.setLocaleCd(sForm.getLocale());

    storeType.setValue(sForm.getTypeDesc());

    phone.setShortDesc("Primary Phone");
    phone.setPhoneNum(sForm.getPhone());

    fax.setShortDesc("Primary Fax");
    fax.setPhoneNum(sForm.getFax());

    email.setShortDesc(sForm.getName1() + " " + sForm.getName2());
    email.setEmailAddress(sForm.getEmailAddress());

    contactEmail.setEmailAddress(sForm.getContactEmail());
    customerEmail.setEmailAddress(sForm.getCustomerEmail());

    address.setName1(sForm.getName1());
    address.setName2(sForm.getName2());
    address.setAddress1(sForm.getStreetAddr1());
    address.setAddress2(sForm.getStreetAddr2());
    address.setAddress3(sForm.getStreetAddr3());
    address.setCity(sForm.getCity());
    address.setStateProvinceCd(sForm.getStateOrProv());
    address.setPostalCode(sForm.getPostalCode());
    address.setCountryCd(sForm.getCountry());
    address.setPrimaryInd(true);
    address.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
    address.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
    boolean erpFlag = false;
    boolean orderNumberStoreFlag = false;
    boolean contactUsTypeFlag = false;
    boolean autoSkuAssign = false;
    boolean requireExternalSysLogon = false;
    boolean taxableIndicator = false;
    boolean orderProcessingSplitTaxExemptOrders = false;
    boolean showDistrNotes = false;
    boolean accountNameInAddress = false;
    boolean allowPONumberByVendor = false;

    for (int ii = 0; ii < miscPropV.size(); ii++) {
      PropertyData pD = (PropertyData) miscPropV.get(ii);
      String propType = pD.getPropertyTypeCd();
      if (RefCodeNames.PROPERTY_TYPE_CD.ORDER_NUMBERING_STORE_ID.equals(propType)) {
        orderNumberStoreFlag = true;
        if (sForm.getCleanwiseOrderNumFlag()) {
          pD.setValue("1");
        } else {
          pD.setValue("" + storeid);
        }
        pD.setModBy(cuser);
        pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
      } else if (RefCodeNames.PROPERTY_TYPE_CD.ERP_SYSTEM.equals(propType)) {
        erpFlag = true;
        pD.setValue(sForm.getErpSystem());
        pD.setModBy(cuser);
        pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
      } else if (RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_NAME_IN_SITE_ADDRESS.equals(propType)) {
        accountNameInAddress = true;
        pD.setValue(Boolean.toString(sForm.isIncludeAccountNameInSiteAddress()));
        pD.setModBy(cuser);
        pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
      } else if (RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_TYPE_CD.equals(propType)) {
        contactUsTypeFlag = true;
        pD.setValue(sForm.getContactUsType());
        pD.setModBy(cuser);
        pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
      } else if (RefCodeNames.PROPERTY_TYPE_CD.AUTO_SKU_ASSIGN.equals(propType)) {
        autoSkuAssign = true;
        pD.setValue(Boolean.toString(sForm.isAutoSkuAssign()));
        pD.setModBy(cuser);
        pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
      } else if (RefCodeNames.PROPERTY_TYPE_CD.REQUIRE_EXTERNAL_SYS_LOGON.equals(propType)) {
        requireExternalSysLogon = true;
        pD.setValue(Boolean.toString(sForm.isRequireExternalSysLogon()));
        pD.setModBy(cuser);
        pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
      } else if (RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR.equals(propType)) {
        taxableIndicator = true;
        pD.setValue(Boolean.toString(sForm.isTaxableIndicator()));
        pD.setModBy(cuser);
        pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
      } else if (RefCodeNames.PROPERTY_TYPE_CD.ORDER_PROCESSING_SPLIT_TAX.equals(propType)) {
        orderProcessingSplitTaxExemptOrders = true;
        pD.setValue(Boolean.toString(sForm.isOrderProcessingSplitTaxExemptOrders()));
        pD.setModBy(cuser);
        pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
      } else if (RefCodeNames.PROPERTY_TYPE_CD.SHOW_DISTR_NOTES_TO_CUSTOMER.equals(propType)) {
        showDistrNotes = true;
        pD.setValue(Boolean.toString(sForm.isShowDistrNotes()));
        pD.setModBy(cuser);
        pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
      } else if (RefCodeNames.PROPERTY_TYPE_CD.ALLOW_PO_NUM_BY_VENDER.equals(propType)) {
    	  allowPONumberByVendor = true;
          pD.setValue(Boolean.toString(sForm.isAllowPONumberByVendors()));
          pD.setModBy(cuser);
          pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
        }
    }
    if (!orderNumberStoreFlag && storeid != 1 && sForm.getCleanwiseOrderNumFlag()) {
      PropertyData pD = PropertyData.createValue();
      pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.ORDER_NUMBERING_STORE_ID);
      pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ORDER_NUMBERING_STORE_ID);
      pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
      pD.setAddBy(cuser);
      pD.setModBy(cuser);
      pD.setValue("1");
      miscPropV.add(pD);
    }
    if (!erpFlag) {
      PropertyData pD = PropertyData.createValue();
      pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.ERP_SYSTEM);
      pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ERP_SYSTEM);
      pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
      pD.setAddBy(cuser);
      pD.setModBy(cuser);
      pD.setValue(sForm.getErpSystem());
      miscPropV.add(pD);
    }
    if (!accountNameInAddress) {
      PropertyData pD = PropertyData.createValue();
      pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_NAME_IN_SITE_ADDRESS);
      pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_NAME_IN_SITE_ADDRESS);
      pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
      pD.setAddBy(cuser);
      pD.setModBy(cuser);
      pD.setValue(Boolean.toString(sForm.isIncludeAccountNameInSiteAddress()));
      miscPropV.add(pD);
    }
    if (!contactUsTypeFlag) {
      PropertyData pD = PropertyData.createValue();
      pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_TYPE_CD);
      pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_TYPE_CD);
      pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
      pD.setAddBy(cuser);
      pD.setModBy(cuser);
      pD.setValue(sForm.getContactUsType());
      miscPropV.add(pD);
    }
    if (!autoSkuAssign) {
      PropertyData pD = PropertyData.createValue();
      pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.AUTO_SKU_ASSIGN);
      pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.AUTO_SKU_ASSIGN);
      pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
      pD.setAddBy(cuser);
      pD.setModBy(cuser);
      pD.setValue(Boolean.toString(sForm.isAutoSkuAssign()));
      miscPropV.add(pD);
    }
    if (!requireExternalSysLogon) {
      PropertyData pD = PropertyData.createValue();
      pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.REQUIRE_EXTERNAL_SYS_LOGON);
      pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.REQUIRE_EXTERNAL_SYS_LOGON);
      pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
      pD.setAddBy(cuser);
      pD.setModBy(cuser);
      pD.setValue(Boolean.toString(sForm.isRequireExternalSysLogon()));
      miscPropV.add(pD);
    }
    if (!taxableIndicator) {
      PropertyData pD = PropertyData.createValue();
      pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR);
      pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR);
      pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
      pD.setAddBy(cuser);
      pD.setModBy(cuser);
      pD.setValue(Boolean.toString(sForm.isTaxableIndicator()));
      miscPropV.add(pD);
    }
    if (!orderProcessingSplitTaxExemptOrders) {
      PropertyData pD = PropertyData.createValue();
      pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.ORDER_PROCESSING_SPLIT_TAX);
      pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ORDER_PROCESSING_SPLIT_TAX);
      pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
      pD.setAddBy(cuser);
      pD.setModBy(cuser);
      pD.setValue(Boolean.toString(sForm.isOrderProcessingSplitTaxExemptOrders()));
      miscPropV.add(pD);
    }

    if (!allowPONumberByVendor) {
        PropertyData pD = PropertyData.createValue();
        pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_PO_NUM_BY_VENDER);
        pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_PO_NUM_BY_VENDER);
        pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
        pD.setAddBy(cuser);
        pD.setModBy(cuser);
        pD.setValue(Boolean.toString(sForm.isAllowPONumberByVendors()));
        miscPropV.add(pD);
    }

    if (!showDistrNotes) {
      PropertyData pD = PropertyData.createValue();
      pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SHOW_DISTR_NOTES_TO_CUSTOMER);
      pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SHOW_DISTR_NOTES_TO_CUSTOMER);
      pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
      pD.setAddBy(cuser);
      pD.setModBy(cuser);
      pD.setValue(Boolean.toString(sForm.isShowDistrNotes()));
      miscPropV.add(pD);
    }
    if (storeid == 0) {
      bed.setAddBy(cuser);
      bed.setModBy(cuser);
      address.setAddBy(cuser);
      address.setModBy(cuser);
      email.setAddBy(cuser);
      email.setModBy(cuser);
      contactEmail.setAddBy(cuser);
      contactEmail.setModBy(cuser);
      customerEmail.setAddBy(cuser);
      customerEmail.setModBy(cuser);
      phone.setAddBy(cuser);
      phone.setModBy(cuser);
      fax.setAddBy(cuser);
      fax.setModBy(cuser);
      storeType.setAddBy(cuser);
      storeType.setModBy(cuser);
      // Only set the prefix when a new store is
      // created.
      storePrefix.setValue(sForm.getPrefix());
      storePrefix.setAddBy(cuser);
      storePrefix.setModBy(cuser);
      storeBusinessName.setValue(sForm.getStoreBusinessName());
      storeBusinessName.setAddBy(cuser);
      storeBusinessName.setModBy(cuser);
      storePrimaryWebAddress.setValue(sForm.getStorePrimaryWebAddress());
      storePrimaryWebAddress.setAddBy(cuser);
      storePrimaryWebAddress.setModBy(cuser);
      dd = storeBean.addStore(dd);
      storeid = dd.getBusEntity().getBusEntityId();
      sForm.setId(String.valueOf(storeid));

      //// create catalog
      try {
        Catalog catalogEjb = factory.getCatalogAPI();
        // creating catalog data
        CatalogData catalogD = CatalogData.createValue();
        catalogD.setShortDesc(sForm.getName());
        catalogD.setCatalogStatusCd(RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
        catalogD.setCatalogTypeCd(RefCodeNames.CATALOG_TYPE_CD.SHOPPING);

        // create contract data
        ContractData contractData = ContractData.createValue();
        OrderGuideDescDataVector orderGuideDescDV = new OrderGuideDescDataVector();
        contractData.setRefContractNum("0");
        contractData.setContractStatusCd(RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
        contractData.setContractTypeCd("UNKNOWN");
        contractData.setLocaleCd(sForm.getLocale());
        contractData.setShortDesc(sForm.getName());

        String user = (String) session.getAttribute(Constants.USER_NAME);

        CatalogContractView catConVw = catalogEjb.addCatalogContract(catalogD,
          contractData, true,
          orderGuideDescDV, storeid, 0, user, false);

        /// create order guide
        // create order guide data
        OrderGuideData ogD = OrderGuideData.createValue();
        ogD.setCatalogId(catConVw.getCatalog().getCatalogId());
        ogD.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
        ogD.setShortDesc(sForm.getName());
        ogD.setAddBy(user);
        OrderGuide ogi = factory.getOrderGuideAPI();

        //OrderGuideInfoData ogid = ogi.createFromCatalog(ogD);
        ogi.createFromCatalog(ogD);

      } catch (DuplicateNameException de) {
        lUpdateErrors.add("name", new ActionError("error.field.notUnique", "Name"));
      }
    } else {
      // Now update this Store
      bed.setModBy(cuser);
      address.setModBy(cuser);
      email.setModBy(cuser);
      contactEmail.setModBy(cuser);
      customerEmail.setModBy(cuser);
      phone.setModBy(cuser);
      fax.setModBy(cuser);
      storeType.setModBy(cuser);
      storeBusinessName.setValue(sForm.getStoreBusinessName());
      storeBusinessName.setModBy(cuser);
      storePrimaryWebAddress.setValue(sForm.getStorePrimaryWebAddress());
      storePrimaryWebAddress.setModBy(cuser);
      storeBean.updateStore(dd);
    }

    session.setAttribute("STORE_DETAIL_FORM", sForm);

    return lUpdateErrors;
  }


  /**
   *  The <code>delete</code> method removes the database entries defining
   *  this store if no other database entry is dependent on it.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@return                an <code>ActionErrors</code> value
   *@exception  Exception  if an error occurs
   *@see                   com.cleanwise.service.api.session.Store
   */
  public static ActionErrors delete(HttpServletRequest request,
                                    ActionForm form) throws Exception {

    ActionErrors deleteErrors = new ActionErrors();

    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    Store storeBean = factory.getStoreAPI();
    String strid = request.getParameter("id");
    if (strid == null || strid.length() == 0) {
      deleteErrors.add("id", new ActionError("error.badRequest", "id"));
      return deleteErrors;
    }

    Integer id = new Integer(strid);
    StoreData dd = storeBean.getStore(id.intValue());

    if (null != dd) {
      try {
        storeBean.removeStore(dd);
      } catch (Exception e) {
        deleteErrors.add("id",
                         new ActionError("error.deleteFailed",
                                         "Store"));
        return deleteErrors;
      }
      session.removeAttribute("Store.found.vector");
    }

    return deleteErrors;
  }


  public static BusEntityFieldsData
    fetchAccountFields(HttpServletRequest request,
                       ActionForm form) throws Exception {

    StoreMgrDetailForm sfdf = (StoreMgrDetailForm) form;
    int id = sfdf.getIntId();
    APIAccess factory = new APIAccess();
    PropertyService psvcBean = factory.getPropertyServiceAPI();
    BusEntityFieldsData sfd = psvcBean.fetchAccountFieldsData(id);
    sfdf.setBusEntityFieldsData(sfd);
    return sfd;
  }


  public static void
    saveAccountFields(HttpServletRequest pRequest,
                      ActionForm pForm) throws Exception {

    StoreMgrDetailForm form = (StoreMgrDetailForm) pForm;
    BusEntityFieldsData siteFieldsData = form.getBusEntityFieldsData();
    int id = form.getIntId();
    APIAccess factory = new APIAccess();
    PropertyService psvcBean = factory.getPropertyServiceAPI();
    psvcBean.updateAccountFieldsData(id, siteFieldsData);
    return;
  }


  private static CountryData getCountry(HttpSession session, StoreMgrDetailForm pForm) {
    CountryDataVector countries = (CountryDataVector)session.getAttribute("country.vector");
    String countryStr = pForm.getCountry();
    CountryData country = null;
    if (countryStr != null) {
      for (int i=0; i<countries.size(); i++) {
        country = (CountryData)countries.get(i);
        if (country.getShortDesc().equals(countryStr)) {
          return country;
        }
      }
    }
    return country;
  }

}


