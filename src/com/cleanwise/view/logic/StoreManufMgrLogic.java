package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Manufacturer;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.DataNotFoundException;

import com.cleanwise.service.api.util.Utility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.CountryDataVector;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.ManufacturerData;
import com.cleanwise.service.api.value.ManufacturerDataVector;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.view.forms.StoreManufMgrSearchForm;
import com.cleanwise.view.forms.StoreManufMgrDetailForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.validators.EmailValidator;

import java.rmi.RemoteException;


/**
 *  <code>ManufMgrLogic</code> implements the logic needed to manipulate
 *  Manufacturer records.
 *
 *@author     durval
 *@created    August 8, 2001
 */
public class StoreManufMgrLogic {

  /**
   *  <code>getDetail</code> method provides the data needed to describe
   a manufacturer.
   *
   * @param  request        a <code>HttpServletRequest</code> value
   * @param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static ActionErrors getDetail(HttpServletRequest request,
                               ActionForm form) throws Exception {
    StoreManufMgrDetailForm sForm = (StoreManufMgrDetailForm) form;
    APIAccess factory = new APIAccess();
    Manufacturer manufBean = factory.getManufacturerAPI();
    Store storeBean = factory.getStoreAPI();
    ActionErrors ae=new ActionErrors();
    String fieldValue = request.getParameter("searchField");
    if (null == fieldValue) {
      fieldValue = "0";
    }


    Integer id = new Integer(fieldValue);
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      ManufacturerData dd = null;
      try {
          dd = manufBean.getManufacturerForStore(id.intValue(),appUser.getUserStoreAsIdVector(),true);
      } catch (DataNotFoundException e) {
         ae.add("error", new ActionError("error.simpleGenericError", e.getMessage()));
         sForm=new StoreManufMgrDetailForm();
         session.setAttribute("STORE_MANUF_DETAIL_FORM", sForm);
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
      PhoneData fax;

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
      PropertyDataVector specz = dd.getSpecializations();
      setSPFlags(sForm, specz);
      PropertyDataVector miscProps = dd.getMiscProperties();
      sForm.setOtherEquivalentManufNames(dd.getOtherNames().getValue());
      // check should the Other Equivalent Manufacturer Names section be shown
      
      boolean showEquivManufNames = false;
      StoreData storeD = storeBean.getStore(appUser.getUserStore().getStoreId());
      PropertyData storeType = storeD.getStoreType();
      PropertyDataVector storeProps = storeD.getMiscProperties();
      if (storeType != null) {
          if (RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equals(storeType.getValue())) {
              showEquivManufNames = true;
          }
      }
      String msdsPlugin = (miscProps == null) ? null : Utility.getPropertyValue(
              miscProps, RefCodeNames.PROPERTY_TYPE_CD.MSDS_PLUGIN);
      sForm.setMsdsPlugin(msdsPlugin == null
              ? RefCodeNames.MSDS_PLUGIN_CD.DEFAULT
              : msdsPlugin);
      if (!showEquivManufNames) {
          if (storeProps != null) {
              PropertyData prop;
              for (int i = 0; i < storeProps.size(); i++) {
                  prop = (PropertyData)storeProps.get(i);
                  if (RefCodeNames.PROPERTY_TYPE_CD.IS_PARENT_STORE.equals(prop.getPropertyTypeCd()) &&
                      Boolean.parseBoolean(prop.getValue()) == true) {
                      showEquivManufNames = true;
                  break;
                  }
              }
          }
      }
      sForm.setShowManufEquvalentName(showEquivManufNames);
    }
      return ae;
  }


  /**
   *  <code>init</code> method.  Creates the Manuf.status.vector
   * , countries.vector, and the states.vector session scoped objects.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void init(HttpServletRequest request,
                          ActionForm form) throws Exception {
    HttpSession session = request.getSession();

    // Cache the lists needed for Manufacturers.
    APIAccess factory = new APIAccess();
    ListService lsvc = factory.getListServiceAPI();

    // Set up the manufacturer status list.
    RefCdDataVector statusv = lsvc.getRefCodesCollection("BUS_ENTITY_STATUS_CD");
    session.setAttribute("Manuf.status.vector", statusv);

    Country countryBean = factory.getCountryAPI();
    CountryDataVector countriesv = countryBean.getAllCountries();
    session.setAttribute("countries.vector", countriesv);

    RefCdDataVector business_classv = lsvc.getRefCodesCollection("BUSINESS_CLASS_CD");
    session.setAttribute("business.class.vector", business_classv);

    RefCdDataVector woman_owned_businessv = lsvc.getRefCodesCollection("WOMAN_OWNED_BUSINESS_CD");
    session.setAttribute("woman.owned.business.vector", woman_owned_businessv);

    RefCdDataVector minority_owned_businessv = lsvc.getRefCodesCollection("MINORITY_OWNED_BUSINESS_CD");
    session.setAttribute("minority.owned.business.vector", minority_owned_businessv);

    RefCdDataVector jwodv = lsvc.getRefCodesCollection("JWOD_CD");
    session.setAttribute("jwod.vector", jwodv);

    RefCdDataVector other_businessv = lsvc.getRefCodesCollection("OTHER_BUSINESS_CD");
    session.setAttribute("other.business.vector", other_businessv);

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
    StoreManufMgrSearchForm sForm = (StoreManufMgrSearchForm) form;
    String fieldValue = sForm.getSearchField();
    String fieldSearchType = sForm.getSearchType();
    session.setAttribute("StoreManuf.found.vector",
                         search(request, fieldValue, fieldSearchType, sForm.getSearchShowInactiveFl()));
  }

  public static ManufacturerDataVector search(HttpServletRequest request,
                                              String fieldValue,
                                              String fieldSearchType,
                                              boolean showInactiveFl) throws
    Exception {
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    Manufacturer manufBean = factory.getManufacturerAPI();

    // Reset the session variables.
    ManufacturerDataVector dv = new ManufacturerDataVector();
    session.setAttribute("StoreManuf.found.vector", dv);

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

    dv = manufBean.getManufacturerByCriteria(crit);
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
    ManufacturerDataVector manufacturers =
      (ManufacturerDataVector) session.getAttribute("StoreManuf.found.vector");
    if (manufacturers == null) {
      return;
    }
    String sortField = request.getParameter("sortField");
    DisplayListSort.sort(manufacturers, sortField);
  }

  /**
   *   <code>addManufacturer</code>, clears the MANUF_DETAIL_FORM.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void addManufacturer(HttpServletRequest request,
                                     ActionForm form) throws Exception {

    HttpSession session = request.getSession();
    StoreManufMgrDetailForm sForm = new StoreManufMgrDetailForm();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    sForm.setStoreId("" + appUser.getUserStore().getStoreId());
    session.setAttribute("STORE_MANUF_DETAIL_FORM", sForm);
  }


  /**
   *  <code>updateManufacturer</code>, update the values pertaining
   to a manufacturer with the data carried in the MANUF_DETAIL_FORM.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@return                an <code>ActionErrors</code> value
   *@exception  Exception  if an error occurs
   */
  public static ActionErrors updateManufacturer(HttpServletRequest request,
                                                ActionForm form) throws
    Exception {

    ActionErrors lUpdateErrors = new ActionErrors();

    HttpSession session = request.getSession();
    
    StoreManufMgrDetailForm sForm = (StoreManufMgrDetailForm) form;
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    
    StoreData storeD = appUser.getUserStore();
    boolean isParentStore = false;
    if (storeD != null) {
        String isParentStoreStr = Utility.getPropertyValue(storeD.getMiscProperties(),
                                       RefCodeNames.PROPERTY_TYPE_CD.IS_PARENT_STORE);
        isParentStore = Utility.isTrue(isParentStoreStr, false);
    }
    
    if (sForm != null) {
    	//STJ - 3846
    	if(sForm.getEmailAddress()!=null) {
    		sForm.setEmailAddress(sForm.getEmailAddress().trim());
    	}
    	
    	// Verify the form information submitted.
    	verifyUpdateValues(sForm, lUpdateErrors);
    	EmailValidator.validateEmail(request, lUpdateErrors, sForm.getEmailAddress());
    }

    if (lUpdateErrors.size() > 0) {
      // Report the errors to allow for edits.
      return lUpdateErrors;
    }

    APIAccess factory = new APIAccess();
    Manufacturer manufacturerBean = factory.getManufacturerAPI();

    int manufacturerid = 0;
    if (!sForm.getId().equals("")) {
      manufacturerid = Integer.parseInt(sForm.getId());
    }
    verifyUniqueName(appUser, manufacturerid, sForm.getName(),
                manufacturerBean, lUpdateErrors);
    if (lUpdateErrors.size() > 0) {
        return lUpdateErrors;
    }

    // Get the current information for this Manufacturer.
    ManufacturerData dd;
    BusEntityData bed;
    PropertyDataVector props;    
    AddressData address;
    EmailData email;
    PhoneData phone, fax;

    if (manufacturerid > 0) {
      dd = manufacturerBean.getManufacturer(manufacturerid);
    } else {
      dd = ManufacturerData.createValue();
    }
    dd.setStoreId(appUser.getUserStore().getStoreId());
    bed = dd.getBusEntity();
    props = dd.getSpecializations();    
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

    email.setShortDesc("MANUFACTURER email");
    email.setModBy(cuser);
    email.setEmailAddress(sForm.getEmailAddress());
    email.setPrimaryInd(true);

    fax.setModBy(cuser);
    fax.setPhoneAreaCode(" ");
    fax.setPhoneCountryCd(" ");
    fax.setPhoneNum(sForm.getFax());
    fax.setPrimaryInd(true);
    fax.setShortDesc("MANUFACTURER fax");

    phone.setModBy(cuser);
    phone.setPhoneAreaCode(" ");
    phone.setPhoneCountryCd(" ");
    phone.setPhoneNum(sForm.getPhone());
    phone.setPrimaryInd(true);
    phone.setShortDesc("MANUFACTURER phone");

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

    if (props.size() == 0) {
      // Set the specialization properties.
      dd.setSpecializations(createSPFlags(sForm));
    } else {
      getSPFlags(sForm, props);
    }
    String storeType = appUser.getUserStore().getStoreType().getValue();
    if (RefCodeNames.STORE_TYPE_CD.ENTERPRISE.equalsIgnoreCase(storeType) || isParentStore){
    	PropertyData otherNames = dd.getOtherNames();
    	String otherNameStr = sForm.getOtherEquivalentManufNames();
    	if (otherNameStr != null && !otherNameStr.equals("")){
    		if (!otherNameStr.equalsIgnoreCase(otherNames.getValue())){
    			otherNames.setValue(otherNameStr);
    		}
    	}else{
    		otherNames.setValue(null);
    	}    	
    }
    PropertyDataVector miscProps = dd.getMiscProperties();
    if (miscProps == null) {
        miscProps = new PropertyDataVector();
    }
    PropertyData msdsPlugin = Utility.getProperty(miscProps, RefCodeNames.PROPERTY_TYPE_CD.MSDS_PLUGIN);
    if (msdsPlugin == null) {
        msdsPlugin = PropertyData.createValue();
        miscProps.add(msdsPlugin);
        msdsPlugin.setAddBy(cuser);
        msdsPlugin.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
        msdsPlugin.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.EXTRA);
    }
    msdsPlugin.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.MSDS_PLUGIN);
    msdsPlugin.setValue(sForm.getMsdsPlugin());
    msdsPlugin.setModBy(cuser);
    if (manufacturerid == 0) {
      bed.setAddBy(cuser);
      email.setAddBy(cuser);
      fax.setAddBy(cuser);
      phone.setAddBy(cuser);
      address.setAddBy(cuser);

      dd = manufacturerBean.addManufacturer(dd);
      sForm.setId(String.valueOf(dd.getBusEntity().getBusEntityId()));

    } else {

      // Now update this Manufacturer
      manufacturerBean.updateManufacturer(dd);
    }

    session.setAttribute("STORE_MANUF_DETAIL_FORM", sForm);

    return lUpdateErrors;
  }


  /**
   *  The <code>delete</code> method removes the database entries defining
   *  this manufacturer if no other database entry is dependent on it.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   *@see                   com.cleanwise.service.api.session.Manufacturer
   */
  public static ActionErrors delete(HttpServletRequest request,
                                    ActionForm form) throws Exception {

    ActionErrors deleteErrors = new ActionErrors();

    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    Manufacturer manufBean = factory.getManufacturerAPI();
    String strid = request.getParameter("id");
    if (strid == null || strid.length() == 0) {
      deleteErrors.add("id", new ActionError("error.badRequest", "id"));
      return deleteErrors;
    }

    Integer id = new Integer(strid);
    ManufacturerData dd = manufBean.getManufacturer(id.intValue());
    if (null != dd) {
      try {
        manufBean.removeManufacturer(dd);
      } catch (Exception e) {
        deleteErrors.add("id",
                         new ActionError("error.deleteFailed",
                                         "Manufacturer"));
        return deleteErrors;
      }
      session.removeAttribute("Manuf.found.vector");
    }
    return deleteErrors;
  }


  /**
   *  Sets the specialization flags for a manufacturer.
   *
   *@param  pForm  , StoreManufMgrDetailForm
   *@param  pSpecializationProps , the property vector to hold the flags.
   */
  private static void setSPFlags(StoreManufMgrDetailForm pForm,
                                 PropertyDataVector pSpecializationProps) {

    pForm.setSpecialization1(true);
    pForm.setSpecialization2(true);
    pForm.setSpecialization3(true);
    pForm.setSpecialization4(true);

    for (int i = 0; i < pSpecializationProps.size(); i++) {
      PropertyData pd = (PropertyData) pSpecializationProps.get(i);
      if (pd.getShortDesc().equals("SP1") &&
          pd.getValue().equals("0")) {
        pForm.setSpecialization1(false);
      } else if (pd.getShortDesc().equals("SP2") &&
                 pd.getValue().equals("0")) {
        pForm.setSpecialization2(false);
      } else if (pd.getShortDesc().equals("SP3") &&
                 pd.getValue().equals("0")) {
        pForm.setSpecialization3(false);
      } else if (pd.getShortDesc().equals("SP4") &&
                 pd.getValue().equals("0")) {
        pForm.setSpecialization4(false);
      }
    }
  }


  /**
   *  Gets the specialization flags for a manufacturer.
   *
   *@param  pForm  , StoreManufMgrDetailForm
   *@param  pSpecializationProps , the property vector to hold the flags.
   */
  private static void getSPFlags(StoreManufMgrDetailForm pForm,
                                 PropertyDataVector pSpecializationProps) {
    for (int i = 0; i < pSpecializationProps.size(); i++) {
      PropertyData pd = (PropertyData) pSpecializationProps.get(i);
      if (pd.getShortDesc().equals("SP1")) {
        if (pForm.getSpecialization1()) {
          pd.setValue("1");
        } else {
          pd.setValue("0");
        }
      } else if (pd.getShortDesc().equals("SP2")) {
        if (pForm.getSpecialization2()) {
          pd.setValue("1");
        } else {
          pd.setValue("0");
        }
      } else if (pd.getShortDesc().equals("SP3")) {
        if (pForm.getSpecialization3()) {
          pd.setValue("1");
        } else {
          pd.setValue("0");
        }
      } else if (pd.getShortDesc().equals("SP4")) {
        if (pForm.getSpecialization4()) {
          pd.setValue("1");
        } else {
          pd.setValue("0");
        }
      }

    }
  }


  /**
   *  Create the specialization flags for a manufacturer.
   *
   *@param  pForm  , StoreManufMgrDetailForm
   *@return       PropertyDataVector  , the property vector to hold the flags.
   */
  private static PropertyDataVector createSPFlags(StoreManufMgrDetailForm pForm) {
    PropertyDataVector specz = new PropertyDataVector();

    PropertyData pd1 = PropertyData.createValue();
    pd1.setShortDesc("SP1");
    if (pForm.getSpecialization1()) {
      pd1.setValue("1");
    } else {
      pd1.setValue("0");
    }
    specz.add(pd1);

    PropertyData pd2 = PropertyData.createValue();
    pd2.setShortDesc("SP2");
    if (pForm.getSpecialization2()) {
      pd2.setValue("1");
    } else {
      pd2.setValue("0");
    }
    specz.add(pd2);

    PropertyData pd3 = PropertyData.createValue();
    pd3.setShortDesc("SP3");
    if (pForm.getSpecialization3()) {
      pd3.setValue("1");
    } else {
      pd3.setValue("0");
    }
    specz.add(pd3);

    PropertyData pd4 = PropertyData.createValue();
    pd4.setShortDesc("SP4");
    if (pForm.getSpecialization4()) {
      pd4.setValue("1");
    } else {
      pd4.setValue("0");
    }
    specz.add(pd4);

    return specz;
  }
  /**
   *  Description of the Method
   *
   *@param  pDataErrors  Description of Parameter
   *@param  pForm        Description of Parameter
   */
  private static void verifyUpdateValues(StoreManufMgrDetailForm pForm,
                                         ActionErrors pDataErrors) {

    if (pForm.getName().length() == 0) {
      pDataErrors.add("name", new ActionError("variable.empty.error", "Name"));
    }

    if (pForm.getStatusCd().length() == 0) {
      pDataErrors.add("statusCd", new ActionError("variable.empty.error", "Status"));
    }

    /*
             if (pForm.getStreetAddr1().length() == 0) {
        pDataErrors.add("streetAddr1", new ActionError("variable.empty.error",
                "Street Address 1"));
             }

             if (pForm.getStateOrProv().length() == 0) {
        pDataErrors.add("stateOrProv",
            new ActionError("variable.empty.error",
     "State/Province"));
             }

             if (pForm.getCity().length() == 0) {
        pDataErrors.add("city", new ActionError("variable.empty.error",
                "City"));
             }

             if (pForm.getCountry().length() == 0) {
        pDataErrors.add("country", new ActionError("variable.empty.error",
                "Country"));
             }

             if (pForm.getPostalCode().length() == 0) {
        pDataErrors.add("postalCode", new ActionError("variable.empty.error",
                "Zip/Postal Code"));
             }
     */
    try {
      Integer.parseInt(pForm.getStoreId());
    } catch (NumberFormatException e) {
      pDataErrors.add("storeId",
                      new ActionError("error.invalidNumber", "Store Id"));
    }
  }

  private static void verifyUniqueName(CleanwiseUser appUser,
            int manufacturerid, String name, Manufacturer manufacturer,
            ActionErrors pDataErrors) throws Exception {
        BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
        crit.setSearchName(name);
        crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
        crit.setSearchNameType(BusEntitySearchCriteria.EXACT_MATCH);
        ManufacturerDataVector mdv = manufacturer
                .getManufacturerByCriteria(crit);
        if (mdv.size() > 0) {
            ManufacturerData first = (ManufacturerData) mdv.get(0);
            if (mdv.size() == 1
                    && first.getBusEntity().getBusEntityId() == manufacturerid) {
                return;
            }
            pDataErrors.add("name", new ActionError("error.field.notUnique",
                    "'Name'"));
        }
    }
}
