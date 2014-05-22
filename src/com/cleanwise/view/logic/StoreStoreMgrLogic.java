package com.cleanwise.view.logic;

import org.apache.struts.action.ActionForm;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.TemplateUtilities;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.I18nUtil;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dto.StoreProfileDto;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.view.forms.StoreStoreMgrSearchForm;
import com.cleanwise.view.forms.StoreStoreMgrDetailForm;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.utils.validators.EmailValidator;
import com.cleanwise.view.i18n.ClwI18nUtil;

import java.rmi.RemoteException;
import java.util.*;

import org.apache.log4j.Logger;


/**
 * @author Alexander Chikin
 * Date: 28.09.2006
 * Time: 2:20:07
 * <code>StoreStoreMgrLogic</code> implements the logic needed to manipulate
 * store records.
 */
public class StoreStoreMgrLogic {

  private static final Logger log = Logger.getLogger(StoreStoreMgrLogic.class);
  public static final int DEFAULT_USER_NAME_SIZE = 30;
  
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

    if (session.getAttribute("country.vector") == null) {
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
   *  Describe <code>addStore</code> method here.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void addStore(HttpServletRequest request,
                              ActionForm form) throws Exception {

    HttpSession session = request.getSession();
    StoreStoreMgrDetailForm sForm = new StoreStoreMgrDetailForm();
    init(request, null);
    session.setAttribute("STORE_STORE_DETAIL_FORM", sForm);
  }

  /**
   *  <code>search</code>
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static ActionErrors search(HttpServletRequest request,
                                    ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    StoreStoreMgrSearchForm sForm = (StoreStoreMgrSearchForm) form;

    // Reset the session variables.

    session.setAttribute("Store.found.total", "0");
    String fieldValue = sForm.getSearchField();
    String fieldSearchType = sForm.getSearchType();

    StoreDataVector dv = new StoreDataVector();
    try {
      if (fieldValue != null && fieldValue.equals(""))
        dv = getAll(request, sForm);
      else dv = search(request, fieldValue, fieldSearchType, null);

    } catch (Exception e) {
      ae = StringUtils.getUiErrorMess(e);
    }

    session.setAttribute("Store.found.vector", dv);
    session.setAttribute("Store.found.total",
                         String.valueOf(dv.size()));
    return ae;
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
   *  <code>getAll</code> stores
   *
   *@param  request        a <code>HttpServletRequest</code> value
   * @param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
	public static StoreDataVector getAll(HttpServletRequest request, ActionForm form) throws Exception {

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

		return dv;
	}

  public static void getDetail(HttpServletRequest request,
                               ActionForm form) throws Exception {
    HttpSession session = request.getSession();
    StoreStoreMgrDetailForm sForm =
      (StoreStoreMgrDetailForm) session.getAttribute("STORE_STORE_DETAIL_FORM");
    init(request, form);
    if (sForm == null) {
      sForm = new StoreStoreMgrDetailForm();
      session.setAttribute("STORE_STORE_DETAIL_FORM", sForm);
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
        StoreStoreMgrDetailForm sForm =
                (StoreStoreMgrDetailForm) session.getAttribute("STORE_STORE_DETAIL_FORM");

        if (pStoreId == 0) {
            String v = (String) session.getAttribute("Store.id");
            if (v == null || v.trim().length() == 0) {
                CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
                String userType = appUser.getUser().getUserTypeCd();
                if (userType.equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR)) {
                    pStoreId = 0;
                } else {
                    pStoreId = appUser.getUserStore().getStoreId();
                }
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
            sForm.setSelectedResourceLocale(bed.getLocaleCd());

            PropertyData storeType = dd.getStoreType();
            sForm.setTypeDesc(storeType.getValue());

            sForm.setCallHours(dd.getCallHours().getValue());
            sForm.setEvenRowColor(dd.getEvenRowColor());
            sForm.setOddRowColor(dd.getOddRowColor());
            sForm.setPendingOrderNotification(dd.getPendingOrderNotification());

            PropertyData prefix = dd.getPrefix();
            sForm.setPrefix(prefix.getValue());

//            PropertyData prefixNew = dd.getPrefixNew();
//            sForm.setPrefixNew(prefixNew.getValue());

            PhoneData phone = dd.getPrimaryPhone();
            sForm.setPhone(phone.getPhoneNum());

            PhoneData fax = dd.getPrimaryFax();
            sForm.setFax(fax.getPhoneNum());

            EmailData customerEmail = dd.getCustomerServiceEmail();
            sForm.setCustomerEmail(customerEmail.getEmailAddress());

            EmailData contactEmail = dd.getContactUsEmail();
            sForm.setContactEmail(contactEmail.getEmailAddress());

            EmailData defaultEmail = dd.getDefaultEmail();
            sForm.setDefaultEmail(defaultEmail.getEmailAddress());

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

            sForm.setSkuWorkflowFlag(new Boolean (getSkuWorkflowSetting(pStoreId)));
            session.setAttribute("STORE_STORE_DETAIL_FORM", sForm);
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
            sForm.setAllowAssetManagement(false);
            sForm.setOrderGuideNotReqd(false);
            sForm.setIncludeAccountNameInSiteAddress(false);
            sForm.setAllowPONumberByVendor(false);
            sForm.setEqualCostAndPrice(false);
            sForm.setWorkOrderEmailAddress("");
            sForm.setParentStore(false);
            sForm.setParentStoreId("");
            sForm.setStageUnmatched(false);
            sForm.setUseXiPay(false);
            sForm.setGoogleAnalyticsId("");
            sForm.setUserNameMaxSize("" +DEFAULT_USER_NAME_SIZE);
            
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
                } else if (RefCodeNames.PROPERTY_TYPE_CD.ASSET_MANAGEMENT.equals(propType)) {
                    sForm.setAllowAssetManagement(Utility.isTrue(propValue));
                } else if (RefCodeNames.PROPERTY_TYPE_CD.ERP_ACCOUNT_NUMBER.equals(propType)) {
                    sForm.setRequireErpAccountNumber(Utility.isTrue(propValue));
                } else if (RefCodeNames.PROPERTY_TYPE_CD.ORDER_GUIDE_NOT_REQD.equals(propType)) {
                    sForm.setOrderGuideNotReqd(Utility.isTrue(propValue));
                }else if (RefCodeNames.PROPERTY_TYPE_CD.REQUIRE_EXTERNAL_SYS_LOGON.equals(propType)) {
                    sForm.setRequireExternalSysLogon(Utility.isTrue(propValue));
                } else if (RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR.equals(propType)) {
                    sForm.setTaxableIndicator(Utility.isTrue(propValue));
                } else if (RefCodeNames.PROPERTY_TYPE_CD.SHOW_DISTR_NOTES_TO_CUSTOMER.equals(propType)) {
                    sForm.setShowDistrNotes(Utility.isTrue(propValue));
                } else if (RefCodeNames.PROPERTY_TYPE_CD.ORDER_PROCESSING_SPLIT_TAX.equals(propType)) {
                    sForm.setOrderProcessingSplitTaxExemptOrders(Utility.isTrue(propValue));
                } else if (RefCodeNames.PROPERTY_TYPE_CD.ALLOW_PO_NUM_BY_VENDER.equals(propType)) {
                    sForm.setAllowPONumberByVendor(Utility.isTrue(propValue));
                } else if (RefCodeNames.PROPERTY_TYPE_CD.EQUAL_COST_AND_PRICE.equals(propType)) {
                    sForm.setEqualCostAndPrice(Utility.isTrue(propValue));
                } else if (RefCodeNames.PROPERTY_TYPE_CD.WORK_ORDER_EMAIL_ADDRESS.equals(propType)) {
                    sForm.setWorkOrderEmailAddress(propValue);
                } else if (RefCodeNames.PROPERTY_TYPE_CD.DISPLAY_DISTR_ACCT_REF_NUM.equals(propType)) {
                    sForm.setDisplayDistributorAccountReferenceNumber(Utility.isTrue(propValue));
	            } else if (RefCodeNames.PROPERTY_TYPE_CD.DISPLAY_DISTR_SITE_REF_NUM.equals(propType)) {
	                sForm.setDisplayDistributorSiteReferenceNumber(Utility.isTrue(propValue));
	            } else if (RefCodeNames.PROPERTY_TYPE_CD.BUDGET_THRESHOLD_FL.equals(propType)) {
	                sForm.setBudgetThresholdFl(Utility.isTrue(propValue));
	            } else if (RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SPECIAL_PRTMISSION_ITEMS.equals(propType)) {
	                sForm.setAllowSpecPermissionItemsMgt(Utility.isTrue(propValue));
	            } else if (RefCodeNames.PROPERTY_TYPE_CD.IS_PARENT_STORE.equals(propType)) {
	                sForm.setParentStore(Utility.isTrue(propValue));
	            } else if (RefCodeNames.PROPERTY_TYPE_CD.PARENT_STORE_ID.equals(propType)) {
                    sForm.setParentStoreId(propValue);
	            } else if (RefCodeNames.PROPERTY_TYPE_CD.STAGE_UNMATCHED.equals(propType)) {
	                sForm.setStageUnmatched(Utility.isTrue(propValue));
                } else if (RefCodeNames.PROPERTY_TYPE_CD.USE_XI_PAY.equals(propType)) {
                    sForm.setUseXiPay(Utility.isTrue(propValue));
                } else if (RefCodeNames.PROPERTY_TYPE_CD.GOOGLE_ANALYTICS_ID.equals(propType)) {
                    sForm.setGoogleAnalyticsId(propValue);
                }
                //email template information
                else if (RefCodeNames.PROPERTY_TYPE_CD.ORDER_CONFIRMATION_EMAIL_TEMPLATE.equals(propType)) {
                    sForm.setOrderConfirmationEmailTemplate(propValue);
                }
                else if (RefCodeNames.PROPERTY_TYPE_CD.SHIPPING_NOTIFICATION_EMAIL_TEMPLATE.equals(propType)) {
                    sForm.setShippingNotificationEmailTemplate(propValue);
                }
                else if (RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROVAL_EMAIL_TEMPLATE.equals(propType)) {
                    sForm.setPendingApprovalEmailTemplate(propValue);
                }
                else if (RefCodeNames.PROPERTY_TYPE_CD.REJECTED_ORDER_EMAIL_TEMPLATE.equals(propType)) {
                    sForm.setRejectedOrderEmailTemplate(propValue);
                }
                else if (RefCodeNames.PROPERTY_TYPE_CD.MODIFIED_ORDER_EMAIL_TEMPLATE.equals(propType)) {
                    sForm.setModifiedOrderEmailTemplate(propValue);
                }
                else if (RefCodeNames.PROPERTY_TYPE_CD.ALTERNATE_UI.equals(propType)) {
                    sForm.setAlternateUI(propValue);
                } 
                else if (RefCodeNames.PROPERTY_TYPE_CD.USER_NAME_MAX_SIZE.equals(propType)) {
                    sForm.setUserNameMaxSize(propValue);
                } else if (RefCodeNames.PROPERTY_TYPE_CD.ALLOW_MIXED_CATEGORY_AND_ITEM.equals(propType)) {
	                sForm.setAllowMixedCategoryAndItemUnderSameParent(Utility.isTrue(propValue));
	            } 
            }
            if (orderNumberStoreId == 1) {
                sForm.setCleanwiseOrderNumFlag(true);
            } else {
                sForm.setCleanwiseOrderNumFlag(false);
            }
            sForm.setErpSystem(erpSystem);
            sForm.setContactUsType(contactUsType);
            
    		//Main DB: Begin
    		MainDb mainDbEjb = null;
    		boolean mainDbAlive = false;
    		AllStoreData allStoreData = null;
    		if ("yes".equals(System.getProperty("multi.store.db"))) {
    			try {
    	    		mainDbEjb = factory.getMainDbAPI();
    	    	} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
    	    		String errorMess = "No MainDb API Access";
    	    		throw new Exception(errorMess);
    	    	}
    	        try {
    	        	mainDbAlive = mainDbEjb.isAliveUnitMainDb("Main");
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	    		String errorMess = "No MainDb";
    	    		throw new Exception(errorMess);
    	        }
    	        if (mainDbAlive == true) { // Main DB is up and running
    	        	// find store datasource for the existing store in the Main DB
    	        	allStoreData = mainDbEjb.getStoreByStoreId(pStoreId); 
    	        	sForm.setStoreDatasource(allStoreData.getDatasource());
    	        }
    		}
    		//Main Db: End
        }
        sForm.setChildStores(storeBean.getChildStores(pStoreId));
        sForm.setEmailTemplateChoices(StoreStoreMgrLogic.getStoreEmailTemplateChoices(pStoreId + ""));        
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

  /**
   *  Describe <code>updateStore</code> method here.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@return                an <code>ActionErrors</code> value
   *@exception  Exception  if an error occurs
   */
	public static ActionErrors updateStore(HttpServletRequest request, ActionForm form) throws Exception {
		ActionErrors lUpdateErrors = new ActionErrors();

		log.info("Updating Store data...");

		HttpSession session = request.getSession();
		APIAccess factory = new APIAccess();
		Country countryBean = factory.getCountryAPI();
		Store storeBean = factory.getStoreAPI();
		User userBean = factory.getUserAPI();
		
		//Main DB: Begin
		MainDb mainDbEjb = null;
		boolean mainDbAlive = false;
		if ("yes".equals(System.getProperty("multi.store.db"))) {
			try {
	    		mainDbEjb = factory.getMainDbAPI();
	    	} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
	    		lUpdateErrors.add("error", new ActionError("error.systemError", "No MainDb API Access"));
	    	    return lUpdateErrors;
	    	}
	        try {
	        	mainDbAlive = mainDbEjb.isAliveUnitMainDb("Main");
	        } catch (Exception e) {
	            e.printStackTrace();
	            String message = ClwI18nUtil.getMessage(request,"admin.errors.multi.noMainDb", null);
	            lUpdateErrors.add(
	                    "error",
	                    new ActionError("error.simpleGenericError",message));
	            return lUpdateErrors;
	        }
		}
		AllUserData allUserData = new AllUserData();
		AllStoreData allStoreData = new AllStoreData();
		UserStoreData userStoreData = new UserStoreData();
		//Main DB: End
		
		StoreStoreMgrDetailForm sForm = (StoreStoreMgrDetailForm) form;
		CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
		
		log.info("appUser.getUser().getUserId()_1 = " + appUser.getUser().getUserId());

		if (sForm != null) {
			// Verify the form information submitted.
			if (sForm.getName() == null || sForm.getName().trim().length() == 0) {
				lUpdateErrors.add("name", new ActionError("variable.empty.error", "Name"));
			} else {
				sForm.setName(sForm.getName().trim());
			}
			if (sForm.getTypeDesc().length() == 0) {
				lUpdateErrors.add("typeDesc", new ActionError("variable.empty.error", "Store Type"));
			}
			if (sForm.getStatusCd().length() == 0) {
				lUpdateErrors.add("statusCd", new ActionError("variable.empty.error", "Status"));
			}
			if(Utility.isSet(sForm.getName1())){
				sForm.setName1(sForm.getName1().trim());
			}
			if(Utility.isSet(sForm.getName2())){
				sForm.setName2(sForm.getName2().trim());
			}
			if (Utility.isSet(sForm.getStreetAddr1())){
				sForm.setStreetAddr1(sForm.getStreetAddr1().trim());
			}
			if (Utility.isSet(sForm.getCity())) {
				sForm.setCity(sForm.getCity().trim());
			}
			
			// check country and province
			 
			CountryData country = getCountry(session, sForm);
			//next  statement: what should be done with multiple Database schemas => for the new Store this check CAN be done ONLY if this dasta is in the new Store Database schema
			CountryPropertyData countryProp = countryBean.getCountryPropertyData(country.getCountryId(), RefCodeNames.COUNTRY_PROPERTY.USES_STATE);
			boolean isStateProvinceRequired = countryProp != null && countryProp.getValue().equalsIgnoreCase("true");

			if (Utility.isSet(sForm.getStateOrProv())) {
				sForm.setStateOrProv(sForm.getStateOrProv().trim());
			}

			if (!isStateProvinceRequired && Utility.isSet(sForm.getStateOrProv())) {
				lUpdateErrors.add("stateOrProv", new ActionError("variable.must.be.empty.error", "State/Province"));
			}

			if (Utility.isSet(sForm.getPostalCode())) {
				sForm.setPostalCode(sForm.getPostalCode().trim());
			} 
			
			if (Utility.isSet(sForm.getPhone())) {
				sForm.setPhone(sForm.getPhone().trim());
			}
			if (Utility.isSet(sForm.getFax())) {
				sForm.setFax(sForm.getFax().trim());
			}

			// STJ-3846
			if (sForm.getEmailAddress() != null) {
				sForm.setEmailAddress(sForm.getEmailAddress().trim());
			}

			if (Utility.isSet(sForm.getContactEmail())) {
				sForm.setContactEmail(sForm.getContactEmail().trim());
			}else{
				lUpdateErrors.add("contactEmail", new ActionError("variable.empty.error", "Contact Us Email"));
			}

			if (sForm.getDefaultEmail() != null) {
				sForm.setDefaultEmail(sForm.getDefaultEmail().trim());
			}
			if (sForm.getDefaultEmail().trim().length() == 0) {
				lUpdateErrors.add("defaultEmail", new ActionError("variable.empty.error", "Default Email"));
			}

			if (sForm.getCustomerEmail() != null) {
				sForm.setCustomerEmail(sForm.getCustomerEmail().trim());
			}
			if (sForm.getCustomerEmail().trim().length() == 0) {
				lUpdateErrors.add("customerEmail", new ActionError("variable.empty.error", "Customer Service Email"));
			}
			if (sForm.getLocale().length() == 0) {
				lUpdateErrors.add("locale", new ActionError("variable.empty.error", "Default Locale"));
			}
			if(Utility.isSet(sForm.getStoreBusinessName())){
				sForm.setStoreBusinessName(sForm.getStoreBusinessName().trim());
			}
			if (Utility.isSet(sForm.getStorePrimaryWebAddress())){
				sForm.setStorePrimaryWebAddress(sForm.getStorePrimaryWebAddress().trim());
			}
			if (Utility.isSet(sForm.getPendingOrderNotification()) == true) {
				String[] notifyEmails = sForm.getPendingOrderNotification().trim().split(",");
				List<String> incorrectEmails = new ArrayList<String>();
				for (String notifyEmail : notifyEmails) {
					if (Utility.isValidEmailAddress(notifyEmail) == false) {
						incorrectEmails.add(notifyEmail);
					}
				}
				if (incorrectEmails.size() > 0) {
					lUpdateErrors.add("PendingOrderNotification", new ActionError("error.simpleGenericError",
							"Incorrect next email address(es) for Pending Order Notification:" + incorrectEmails));
				}
			}
			EmailValidator.validateForm(sForm, request, lUpdateErrors);
			ActionErrors ae = checkParentStoreProperties(request, storeBean, userBean, appUser, sForm);
			lUpdateErrors.add(ae);

		}

		// set the email template choices here, in case we are returning the
		// user to the form to
		// fix any mistakes. If the form has a store id use it, otherwise blank
		// out the template
		// choices since we cannot tell what choices to use (they are based on
		// the store id)
		String storeId = null;
		if (Utility.isSet(sForm.getId()) && !"0".equalsIgnoreCase(sForm.getId().trim())) {
			storeId = sForm.getId();
		} else {
			storeId = "";
		}
		sForm.setEmailTemplateChoices(StoreStoreMgrLogic.getStoreEmailTemplateChoices(storeId));

		// if any email templates are specified, make sure they exist
		TemplateDataVector existingTemplates = null;
		// STJ-3887
		if (Utility.isSet(sForm.getId()) && !"0".equalsIgnoreCase(sForm.getId().trim())) {
			existingTemplates = TemplateUtilities.getAllEmailTemplatesForStore(new Integer(storeId).intValue());
		} else {
			existingTemplates = new TemplateDataVector();
		}
		String specifiedName = sForm.getOrderConfirmationEmailTemplate();
		if (Utility.isSet(specifiedName)) {
			Iterator<TemplateData> templateIterator = existingTemplates.iterator();
			boolean found = false;
			while (templateIterator.hasNext()) {
				if (specifiedName.equalsIgnoreCase(templateIterator.next().getName())) {
					found = true;
				}
			}
			if (!found) {
				lUpdateErrors.add("emailSpecification", new ActionError("error.simpleGenericError", "Unknown template (" + specifiedName
						+ ") specified for Order Confirmation Template."));
			}
		}
		specifiedName = sForm.getShippingNotificationEmailTemplate();
		if (Utility.isSet(specifiedName)) {
			Iterator<TemplateData> templateIterator = existingTemplates.iterator();
			boolean found = false;
			while (templateIterator.hasNext()) {
				if (specifiedName.equalsIgnoreCase(templateIterator.next().getName())) {
					found = true;
				}
			}
			if (!found) {
				lUpdateErrors.add("emailSpecification", new ActionError("error.simpleGenericError", "Unknown template (" + specifiedName
						+ ") specified for Shipping Notification Template."));
			}
		}
		specifiedName = sForm.getPendingApprovalEmailTemplate();
		if (Utility.isSet(specifiedName)) {
			Iterator<TemplateData> templateIterator = existingTemplates.iterator();
			boolean found = false;
			while (templateIterator.hasNext()) {
				if (specifiedName.equalsIgnoreCase(templateIterator.next().getName())) {
					found = true;
				}
			}
			if (!found) {
				lUpdateErrors.add("emailSpecification", new ActionError("error.simpleGenericError", "Unknown template (" + specifiedName
						+ ") specified for Pending Approval Template."));
			}
		}
		specifiedName = sForm.getRejectedOrderEmailTemplate();
		if (Utility.isSet(specifiedName)) {
			Iterator<TemplateData> templateIterator = existingTemplates.iterator();
			boolean found = false;
			while (templateIterator.hasNext()) {
				if (specifiedName.equalsIgnoreCase(templateIterator.next().getName())) {
					found = true;
				}
			}
			if (!found) {
				lUpdateErrors.add("emailSpecification", new ActionError("error.simpleGenericError", "Unknown template (" + specifiedName
						+ ") specified for Rejected Order Template."));
			}
		}
		specifiedName = sForm.getModifiedOrderEmailTemplate();
		if (Utility.isSet(specifiedName)) {
			Iterator<TemplateData> templateIterator = existingTemplates.iterator();
			boolean found = false;
			while (templateIterator.hasNext()) {
				if (specifiedName.equalsIgnoreCase(templateIterator.next().getName())) {
					found = true;
				}
			}
			if (!found) {
				lUpdateErrors.add("emailSpecification", new ActionError("error.simpleGenericError", "Unknown template (" + specifiedName
						+ ") specified for Modified Order Template."));
			}
		}

		int storeid = 0;
		if (!sForm.getId().equals("")) {
			storeid = Integer.parseInt(sForm.getId());
		}
		String userNameMaxSizeStr = sForm.getUserNameMaxSize();
		int userNameMaxSize = 30;
		if (Utility.isSet(userNameMaxSizeStr)) {
			try {
				userNameMaxSize = new Integer(userNameMaxSizeStr).intValue();
			} catch (NumberFormatException e) {
				lUpdateErrors.add("userNameMaxSize", new ActionError("variable.integer.format.error", "User Name Max Size"));
			}
		} else {
			sForm.setUserNameMaxSize("" + DEFAULT_USER_NAME_SIZE);
		}
		if (userNameMaxSize > 255) {
			lUpdateErrors.add("userNameMaxSize", new ActionError("error.simpleGenericError", "User Name Max Size could not larger than 255."));
		} else {
			if (storeid > 0) {
				IdVector userIds = userBean.getUserIdsWithNameExccedMaxSize(storeid, userNameMaxSize);
				if (userIds.size() > 0) {
					String errMsg = "Following user id has user name size excced Max Size(" + userNameMaxSize + ") in system: ";
					for (int i = 0; i < userIds.size() && i < 10; i++) {
						errMsg += userIds.get(i) + ", ";
					}
					errMsg = errMsg.substring(0, errMsg.lastIndexOf(','));
					if (userIds.size() > 10) {
						errMsg += "......";
					} else {
						errMsg += ".";
					}
					lUpdateErrors.add("userNameMaxSize", new ActionError("error.simpleGenericError", errMsg));
				}
			}
		}

		if (lUpdateErrors.size() > 0) {
			// Report the errors to allow for edits.
			return lUpdateErrors;
		}

		// Get the current information for this Store.
		StoreData dd;
		BusEntityData bed;
		AddressData address;
		EmailData email;
		EmailData contactEmail;
		EmailData customerEmail;
		EmailData defaultEmail;
		PhoneData phone;
		PhoneData fax;
		PropertyData storeType;
		PropertyData storePrefix;
		// PropertyData storePrefixNew;
		PropertyData storeBusinessName;
		PropertyData callHours;
		PropertyData storePrimaryWebAddress;
		PropertyDataVector miscPropV;

		if (storeid > 0) {
			dd = storeBean.getStore(storeid);
		} else {
			if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
				lUpdateErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.unauthorized"));
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
		defaultEmail = dd.getDefaultEmail();
		storeType = dd.getStoreType();
		storePrefix = dd.getPrefix();
		// storePrefixNew = dd.getPrefixNew();
		storeBusinessName = dd.getStoreBusinessName();
		storePrimaryWebAddress = dd.getStorePrimaryWebAddress();
		miscPropV = dd.getMiscProperties();
		callHours = dd.getCallHours();

		callHours.setValue(sForm.getCallHours().trim());
		dd.setEvenRowColor(sForm.getEvenRowColor());
		dd.setOddRowColor(sForm.getOddRowColor());
		dd.setPendingOrderNotification(sForm.getPendingOrderNotification());

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
		defaultEmail.setEmailAddress(sForm.getDefaultEmail());

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
		boolean allowAssetManagement = false;
		boolean orderGuideNotReqd = false;
		boolean allowPONumberByVendor = false;
		boolean equalCostAndPrice = false;
		boolean workOrderEmailAddress = false;

		boolean requireErpAccountNumber = false;
		boolean useXiPay = false;
		boolean displayDistributorAccountReferenceNumber = false;
		boolean displayDistributorSiteReferenceNumber = false;
		boolean budgetThresholdFl = false;
		boolean allowSpecPermissionItemsMgt = false;
		boolean parentStore = false;
		boolean parentStoreId = false;
		boolean stageUnmatched = false;
		boolean googleAnalyticsId = false;
		boolean orderConfirmationEmailTemplateSet = false;
		boolean shippingNotificationEmailTemplateSet = false;
		boolean pendingApprovalEmailTemplateSet = false;
		boolean rejectedOrderEmailTemplateSet = false;
		boolean modifiedOrderEmailTemplateSet = false;
		boolean alternateUISet = false;
		boolean userNameMaxSizeSet = false;
		boolean allowMixedCategoryAndItemUnderSameParent = false;

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
			} else if (RefCodeNames.PROPERTY_TYPE_CD.ERP_ACCOUNT_NUMBER.equals(propType)) {
				requireErpAccountNumber = true;
				pD.setValue(Boolean.toString(sForm.isRequireErpAccountNumber()));
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.ASSET_MANAGEMENT.equals(propType)) {
				allowAssetManagement = true;
				pD.setValue(Boolean.toString(sForm.isAllowAssetManagement()));
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.ORDER_GUIDE_NOT_REQD.equals(propType)) {
				orderGuideNotReqd = true;
				pD.setValue(Boolean.toString(sForm.isOrderGuideNotReqd()));
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
				pD.setValue(Boolean.toString(sForm.isAllowPONumberByVendor()));
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.EQUAL_COST_AND_PRICE.equals(propType)) {
				equalCostAndPrice = true;
				pD.setValue(Boolean.toString(sForm.isEqualCostAndPrice()));
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.WORK_ORDER_EMAIL_ADDRESS.equals(propType)) {
				workOrderEmailAddress = true;
				pD.setValue(sForm.getWorkOrderEmailAddress());
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.DISPLAY_DISTR_ACCT_REF_NUM.equals(propType)) {
				displayDistributorAccountReferenceNumber = true;
				pD.setValue(Boolean.toString(sForm.isDisplayDistributorAccountReferenceNumber()));
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.DISPLAY_DISTR_SITE_REF_NUM.equals(propType)) {
				displayDistributorSiteReferenceNumber = true;
				pD.setValue(Boolean.toString(sForm.isDisplayDistributorSiteReferenceNumber()));
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.BUDGET_THRESHOLD_FL.equals(propType)) {
				budgetThresholdFl = true;
				pD.setValue(Boolean.toString(sForm.isBudgetThresholdFl()));
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SPECIAL_PRTMISSION_ITEMS.equals(propType)) {
				allowSpecPermissionItemsMgt = true;
				pD.setValue(Boolean.toString(sForm.getAllowSpecPermissionItemsMgt()));
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.IS_PARENT_STORE.equals(propType)) {
				parentStore = true;
				pD.setValue(Boolean.toString(sForm.isParentStore()));
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.PARENT_STORE_ID.equals(propType)) {
				parentStoreId = true;
				pD.setValue(sForm.getParentStoreId());
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.STAGE_UNMATCHED.equals(propType)) {
				stageUnmatched = true;
				pD.setValue(Boolean.toString(sForm.isStageUnmatched()));
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.USE_XI_PAY.equals(propType)) {
				useXiPay = true;
				pD.setValue(Boolean.toString(sForm.isUseXiPay()));
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.GOOGLE_ANALYTICS_ID.equals(propType)) {
				googleAnalyticsId = true;
				pD.setValue(sForm.getGoogleAnalyticsId());
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.ORDER_CONFIRMATION_EMAIL_TEMPLATE.equals(propType)) {
				orderConfirmationEmailTemplateSet = true;
				pD.setValue(sForm.getOrderConfirmationEmailTemplate());
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.SHIPPING_NOTIFICATION_EMAIL_TEMPLATE.equals(propType)) {
				shippingNotificationEmailTemplateSet = true;
				pD.setValue(sForm.getShippingNotificationEmailTemplate());
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROVAL_EMAIL_TEMPLATE.equals(propType)) {
				pendingApprovalEmailTemplateSet = true;
				pD.setValue(sForm.getPendingApprovalEmailTemplate());
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.REJECTED_ORDER_EMAIL_TEMPLATE.equals(propType)) {
				rejectedOrderEmailTemplateSet = true;
				pD.setValue(sForm.getRejectedOrderEmailTemplate());
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.MODIFIED_ORDER_EMAIL_TEMPLATE.equals(propType)) {
				modifiedOrderEmailTemplateSet = true;
				pD.setValue(sForm.getModifiedOrderEmailTemplate());
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.ALTERNATE_UI.equals(propType)) {
				alternateUISet = true;
				pD.setValue(sForm.getAlternateUI());
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.USER_NAME_MAX_SIZE.equals(propType)) {
				userNameMaxSizeSet = true;
				pD.setValue(sForm.getUserNameMaxSize());
				pD.setModBy(cuser);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			} else if (RefCodeNames.PROPERTY_TYPE_CD.ALLOW_MIXED_CATEGORY_AND_ITEM.equals(propType)) {
				allowMixedCategoryAndItemUnderSameParent = true;
				pD.setValue(Boolean.toString(sForm.getAllowMixedCategoryAndItemUnderSameParent()));
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
		if (!requireErpAccountNumber) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.ERP_ACCOUNT_NUMBER);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ERP_ACCOUNT_NUMBER);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(Boolean.toString(sForm.isRequireErpAccountNumber()));
			miscPropV.add(pD);
		}
		if (!allowAssetManagement) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.ASSET_MANAGEMENT);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ASSET_MANAGEMENT);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(Boolean.toString(sForm.isAllowAssetManagement()));
			miscPropV.add(pD);
		}
		if (!orderGuideNotReqd) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.ORDER_GUIDE_NOT_REQD);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ORDER_GUIDE_NOT_REQD);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(Boolean.toString(sForm.isOrderGuideNotReqd()));
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
			pD.setValue(Boolean.toString(sForm.isAllowPONumberByVendor()));
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

		if (!equalCostAndPrice) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.EQUAL_COST_AND_PRICE);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.EQUAL_COST_AND_PRICE);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(Boolean.toString(sForm.isEqualCostAndPrice()));
			miscPropV.add(pD);
		}

		if (!workOrderEmailAddress) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.WORK_ORDER_EMAIL_ADDRESS);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.WORK_ORDER_EMAIL_ADDRESS);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(sForm.getWorkOrderEmailAddress());
			miscPropV.add(pD);
		}
		if (!displayDistributorAccountReferenceNumber) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.DISPLAY_DISTR_ACCT_REF_NUM);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.DISPLAY_DISTR_ACCT_REF_NUM);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(Boolean.toString(sForm.isDisplayDistributorAccountReferenceNumber()));
			miscPropV.add(pD);
		}
		if (!displayDistributorSiteReferenceNumber) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.DISPLAY_DISTR_SITE_REF_NUM);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.DISPLAY_DISTR_SITE_REF_NUM);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(Boolean.toString(sForm.isDisplayDistributorSiteReferenceNumber()));
			miscPropV.add(pD);
		}
		if (!budgetThresholdFl) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.BUDGET_THRESHOLD_FL);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.BUDGET_THRESHOLD_FL);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(Boolean.toString(sForm.isBudgetThresholdFl()));
			miscPropV.add(pD);
		}
		if (!allowSpecPermissionItemsMgt) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SPECIAL_PRTMISSION_ITEMS);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SPECIAL_PRTMISSION_ITEMS);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(Boolean.toString(sForm.getAllowSpecPermissionItemsMgt()));
			miscPropV.add(pD);
		}
		if (!parentStore) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.IS_PARENT_STORE);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.IS_PARENT_STORE);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(Boolean.toString(sForm.isParentStore()));
			miscPropV.add(pD);
		}
		if (!parentStoreId) {
			if (Utility.isSet(sForm.getParentStoreId())) {
				PropertyData pD = PropertyData.createValue();
				pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.PARENT_STORE_ID);
				pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.PARENT_STORE_ID);
				pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
				pD.setAddBy(cuser);
				pD.setModBy(cuser);
				pD.setValue(sForm.getParentStoreId());
				miscPropV.add(pD);
			}
		}
		if (!stageUnmatched) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.STAGE_UNMATCHED);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.STAGE_UNMATCHED);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(Boolean.toString(sForm.isStageUnmatched()));
			miscPropV.add(pD);
		}

		if (!useXiPay) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.USE_XI_PAY);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.USE_XI_PAY);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(Boolean.toString(sForm.isUseXiPay()));
			miscPropV.add(pD);
		}

		if (googleAnalyticsId == false) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.GOOGLE_ANALYTICS_ID);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.GOOGLE_ANALYTICS_ID);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(sForm.getGoogleAnalyticsId());
			miscPropV.add(pD);
		}

		if (!orderConfirmationEmailTemplateSet) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.ORDER_CONFIRMATION_EMAIL_TEMPLATE);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ORDER_CONFIRMATION_EMAIL_TEMPLATE);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(sForm.getOrderConfirmationEmailTemplate());
			miscPropV.add(pD);
		}
		if (!shippingNotificationEmailTemplateSet) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.SHIPPING_NOTIFICATION_EMAIL_TEMPLATE);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.SHIPPING_NOTIFICATION_EMAIL_TEMPLATE);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(sForm.getShippingNotificationEmailTemplate());
			miscPropV.add(pD);
		}
		if (!pendingApprovalEmailTemplateSet) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROVAL_EMAIL_TEMPLATE);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROVAL_EMAIL_TEMPLATE);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(sForm.getPendingApprovalEmailTemplate());
			miscPropV.add(pD);
		}
		if (!rejectedOrderEmailTemplateSet) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.REJECTED_ORDER_EMAIL_TEMPLATE);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.REJECTED_ORDER_EMAIL_TEMPLATE);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(sForm.getRejectedOrderEmailTemplate());
			miscPropV.add(pD);
		}
		if (!modifiedOrderEmailTemplateSet) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.MODIFIED_ORDER_EMAIL_TEMPLATE);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.MODIFIED_ORDER_EMAIL_TEMPLATE);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(sForm.getModifiedOrderEmailTemplate());
			miscPropV.add(pD);
		}
		if (!allowMixedCategoryAndItemUnderSameParent) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_MIXED_CATEGORY_AND_ITEM);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_MIXED_CATEGORY_AND_ITEM);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(Boolean.toString(sForm.getAllowMixedCategoryAndItemUnderSameParent()));
			miscPropV.add(pD);
		}		
		if (!alternateUISet) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.ALTERNATE_UI);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ALTERNATE_UI);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(sForm.getAlternateUI());
			miscPropV.add(pD);
		}  
		if (!userNameMaxSizeSet) {
			PropertyData pD = PropertyData.createValue();
			pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.USER_NAME_MAX_SIZE);
			pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.USER_NAME_MAX_SIZE);
			pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
			pD.setAddBy(cuser);
			pD.setModBy(cuser);
			pD.setValue(sForm.getUserNameMaxSize());
			miscPropV.add(pD);
		}

		if (storeid == 0) {
			
			// MainDb: Begin
			if ("yes".equals(System.getProperty("multi.store.db"))) {
			  if (mainDbAlive) { // if Main DB is accessible, insert records in ESW_ALL_STORE, ESW_USER_STORE DB tables
				log.info("updateStore(); storeid_1 = " + storeid); 
				String storeDatasource = sForm.getStoreDatasource();
				log.info("updateStore(): storeDatasource_1 = " + storeDatasource);
				if (storeDatasource == null || storeDatasource.equals("")) {
					lUpdateErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("Datasource was not defined"));
					return lUpdateErrors;
				}
				
				CallerParametersStJohn.JaasLogin(request, storeDatasource, true);
			  }
			}
			// MainDb: End
			
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
			defaultEmail.setAddBy(cuser);
			defaultEmail.setModBy(cuser);
			phone.setAddBy(cuser);
			phone.setModBy(cuser);
			fax.setAddBy(cuser);
			fax.setModBy(cuser);
			storeType.setAddBy(cuser);
			storeType.setModBy(cuser);
			storePrefix.setValue(sForm.getPrefix());
			storePrefix.setAddBy(cuser);
			storePrefix.setModBy(cuser);
			// storePrefixNew.setValue(sForm.getPrefixNew());
			// storePrefixNew.setAddBy(cuser);
			// storePrefixNew.setModBy(cuser);
			storeBusinessName.setValue(sForm.getStoreBusinessName());
			storeBusinessName.setAddBy(cuser);
			storeBusinessName.setModBy(cuser);
			storePrimaryWebAddress.setValue(sForm.getStorePrimaryWebAddress());
			storePrimaryWebAddress.setAddBy(cuser);
			storePrimaryWebAddress.setModBy(cuser);
			dd = storeBean.addStore(dd);
			storeid = dd.getBusEntity().getBusEntityId();
			sForm.setId(String.valueOf(storeid));
			
			// // create catalog
			try {
				Catalog catalogEjb = factory.getCatalogAPI();
				String user = (String) session.getAttribute(Constants.USER_NAME);
				// creating catalog data
				CatalogData catalogD = CatalogData.createValue();
				catalogD.setShortDesc("Store_Catalog_" + sForm.getId());
				catalogD.setCatalogStatusCd(RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
				catalogD.setCatalogTypeCd(RefCodeNames.CATALOG_TYPE_CD.STORE);
				catalogEjb.addCatalog(catalogD, storeid, user);
				// create contract data
				/*
				 * ContractData contractData = ContractData.createValue();
				 * OrderGuideDescDataVector orderGuideDescDV = new
				 * OrderGuideDescDataVector();
				 * contractData.setRefContractNum("0");
				 * contractData.setContractStatusCd
				 * (RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
				 * contractData.setContractTypeCd("UNKNOWN");
				 * contractData.setLocaleCd(sForm.getLocale());
				 * contractData.setShortDesc(sForm.getName());
				 * 
				 * 
				 * /*CatalogContractView catConVw =
				 * catalogEjb.addCatalogContract(catalogD, contractData, true,
				 * orderGuideDescDV, storeid, 0, user);
				 * 
				 * /// create order guide // create order guide data
				 * OrderGuideData ogD = OrderGuideData.createValue();
				 * ogD.setCatalogId(catConVw.getCatalog().getCatalogId());
				 * ogD.setOrderGuideTypeCd
				 * (RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
				 * ogD.setShortDesc(sForm.getName()); ogD.setAddBy(user);
				 * OrderGuide ogi = factory.getOrderGuideAPI();
				 * 
				 * //OrderGuideInfoData ogid = ogi.createFromCatalog(ogD);
				 * ogi.createFromCatalog(ogD);
				 */
			} catch (DuplicateNameException de) {
				lUpdateErrors.add("name", new ActionError("error.field.notUnique", "Name"));
			}
			// MainDb: Begin
			if ("yes".equals(System.getProperty("multi.store.db"))) {
			  if (mainDbAlive) { // if Main DB is accessible, insert records in ESW_ALL_STORE, ESW_USER_STORE DB tables
				log.info("updateStore(): storeid_2 = " + storeid); 
				String storeDatasource = sForm.getStoreDatasource();
				if (storeDatasource == null || storeDatasource.equals("")) {
					lUpdateErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("Datasource was not defined"));
					return lUpdateErrors;
				}
				log.info("updateStore(): storeDatasource_2 = " + storeDatasource);
				Date dNow = new Date();

				allStoreData.setStoreId(storeid);
				allStoreData.setStoreName(sForm.getName());
				allStoreData.setDomain(null); // ??? where from should I take it ?
				allStoreData.setDatasource(storeDatasource); 
				allStoreData.setAddBy(cuser);
				allStoreData.setModBy(cuser);				
				Integer allStoreId = mainDbEjb.updateAllStore(allStoreData, cuser);
				
				// find ALL_USER_ID in ESW_ALL_USER table of the Main DB
				log.info("appUser.getUser().getUserId()_2 = " + appUser.getUser().getUserId());
				allUserData = mainDbEjb.getAllUserDataByUserId(appUser.getUser().getUserId());
				log.info("allUserData = " + allUserData);
				
				userStoreData.setAllUserId(allUserData.getAllUserId());
				userStoreData.setAllStoreId(allStoreId.intValue());
				userStoreData.setLastLoginDate(dNow); 
				userStoreData.setAddBy(cuser);
				userStoreData.setModBy(cuser);	
				userStoreData.setLocaleCd(sForm.getLocale());
				mainDbEjb.saveUserStoreData(userStoreData, cuser);				
			  }
			}
			// MainDb: End
			
		} else { // store_id != 0 => existing store
			// Now update this Store
			bed.setModBy(cuser);
			address.setModBy(cuser);
			email.setModBy(cuser);
			contactEmail.setModBy(cuser);
			customerEmail.setModBy(cuser);
			defaultEmail.setModBy(cuser);
			phone.setModBy(cuser);
			fax.setModBy(cuser);
			storeType.setModBy(cuser);
			storeBusinessName.setValue(sForm.getStoreBusinessName());
			storeBusinessName.setModBy(cuser);
			storePrefix.setValue(sForm.getPrefix());
			storePrefix.setModBy(cuser);
			// storePrefixNew.setValue(sForm.getPrefixNew());
			// storePrefixNew.setModBy(cuser);
			storePrimaryWebAddress.setValue(sForm.getStorePrimaryWebAddress());
			storePrimaryWebAddress.setModBy(cuser);
			try {
				storeBean.updateStore(dd);
			} catch (RemoteException e) {
				String error = ClwI18nUtil.formatEjbError(request, e.getMessage());
				if (Utility.isSet(error)) {
					lUpdateErrors.add("error", new ActionError("error.simpleGenericError", error));
				} else {
					throw e;
				}
			}
			// MainDb: Begin
			if ("yes".equals(System.getProperty("multi.store.db"))) {
			  if (mainDbAlive) { // if Main DB is accessible, update ESW_ALL_STORE DB table
				// read appropriate record from the Main DB table and update ESW_ALL_STORE DB table
				Integer integerStoreid = new Integer(storeid);
				log.info("integerStoreid = " + integerStoreid);
				allStoreData = mainDbEjb.getStoreByStoreId(integerStoreid);
				allStoreData.setStoreName(sForm.getName());
				allStoreData.setModBy(cuser);
				
				mainDbEjb.updateAllStore(allStoreData, cuser);
			  }
			}
			// MainDb: End
		}
		session.setAttribute("STORE_STORE_DETAIL_FORM", sForm);

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
  public static ActionErrors delete(HttpServletRequest request, ActionForm form) throws Exception {

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

	//Main DB: Begin
	MainDb mainDbEjb = null;
	boolean mainDbAlive = false;
	if ("yes".equals(System.getProperty("multi.store.db"))) {
		try {
    		mainDbEjb = factory.getMainDbAPI();
    	} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
    		deleteErrors.add("error", new ActionError("error.systemError", "No MainDb API Access"));
    	    return deleteErrors;
    	}
        try {
        	mainDbAlive = mainDbEjb.isAliveUnitMainDb("Main");
        } catch (Exception e) {
            e.printStackTrace();
            String message = ClwI18nUtil.getMessage(request,"admin.errors.multi.noMainDb", null);
            deleteErrors.add(
                    "error",
                    new ActionError("error.simpleGenericError",message));
            return deleteErrors;
        }
        if (mainDbAlive) {        	
           try {
        	   //find ESW_ALL_STORE record with storeId = id
        	   AllStoreData allStoreData = mainDbEjb.getAllStoreDataByStoreId(id.intValue()); 
        	   // delete records from ESW_USER_STORE and ESW_ALL_STORE DB tables of the Main DB
        	   if (allStoreData != null) {
        	       mainDbEjb.deleteUserStoreByAllStoreId(allStoreData.getAllStoreId());
        	       mainDbEjb.deleteAllStoreByAllStoreId(allStoreData.getAllStoreId());
        	   }
        	} catch(Exception e) {
                deleteErrors.add("id",
                        new ActionError("error.deleteFailed",
                                        "Store in Main DB"));
                return deleteErrors; 
        		
        	}
        }
	}
    return deleteErrors;
  }

  private static ActionErrors checkParentStoreProperties(HttpServletRequest request,
                                                         Store storeBean,
                                                         User userBean,
                                                         CleanwiseUser appUser,
                                                         StoreStoreMgrDetailForm sForm) throws Exception {
      ActionErrors ae = new ActionErrors();
      if (Utility.isSet(sForm.getParentStoreId())) {
          try {
              if (sForm.isParentStore()) {
                  String propertyParentStore = ClwI18nUtil.getMessageOrNull(request, "store.property.parentStore");
                  if (propertyParentStore == null) {
                      propertyParentStore = "ParentStore";
                  }
                  String propertyParentStoreId = ClwI18nUtil.getMessageOrNull(request, "store.property.parentStoreId");
                  if (propertyParentStoreId == null) {
                      propertyParentStoreId = "ParentStoreId";
                  }
                  String mess = ClwI18nUtil.getMessage(request, "error.store.parentChild", new Object[]{propertyParentStore, propertyParentStoreId}, true);
                  sForm.setParentStore(false);
                  ae.add("ParentChild", new ActionError("error.simpleError", mess));
              }

              int parentStoreId = Integer.parseInt(sForm.getParentStoreId());
              StoreData parentStoreD = storeBean.getStore(parentStoreId);
              if (parentStoreId == sForm.getIntId()) {
                    String mess = ClwI18nUtil.getMessage(request, "error.store.child.to.itself", new Object[]{sForm.getParentStoreId()}, true);
                    ae.add("ParentStoreId", new ActionError("error.simpleError", mess));
              }

              IdVector userIds = new IdVector();
              userIds.add(appUser.getUserId());
              IdVector assocStoreIds = userBean.getUserAssocCollecton(userIds,
                                                                      RefCodeNames.USER_ASSOC_CD.STORE,
                                                                      RefCodeNames.USER_STATUS_CD.ACTIVE);
              PropertyDataVector props = parentStoreD.getMiscProperties();
              PropertyData property;
              boolean isParentStore = false;
              for (int i = 0; i < props.size(); i++ ) {
                  property = (PropertyData) props.get(i);
                  if (RefCodeNames.PROPERTY_TYPE_CD.IS_PARENT_STORE.equals(property.getPropertyTypeCd()) &&
                      RefCodeNames.PROPERTY_STATUS_CD.ACTIVE.equals(property.getPropertyStatusCd()) &&
                      Boolean.parseBoolean(property.getValue())) {
                      isParentStore = true;
                      break;
                  }
              }
              if (!isParentStore || !assocStoreIds.contains(Integer.valueOf(parentStoreId))) {
                  String propertyParentStoreId = ClwI18nUtil.getMessageOrNull(request, "store.property.parentStoreId");
                  if (propertyParentStoreId == null) {
                      propertyParentStoreId = "ParentStoreId";
                  }
                  String mess = ClwI18nUtil.getMessage(request, "error.store.parentNotAuthorized", new Object[]{sForm.getParentStoreId()}, true);
                  ae.add("ParentNotAuthorized", new ActionError("error.simpleError", mess));
              }
          } catch (NumberFormatException e) {
              String property = ClwI18nUtil.getMessageOrNull(request, "store.property.parentStoreId");
              if (property == null) {
                  property = "ParentStoreId";
              }
              String mess = ClwI18nUtil.getMessage(request, "error.wrongNumberFormat", new Object[]{property, sForm.getParentStoreId()}, true);
              ae.add("ParentStoreId", new ActionError("error.simpleError", mess));
          } catch (DataNotFoundException e) {
              String mess = ClwI18nUtil.getMessage(request, "error.noStore.withId", new Object[]{sForm.getParentStoreId()}, true);
              ae.add("ParentStoreId", new ActionError("error.simpleError", mess));
          }

          if (ae.size() > 0) {
              sForm.setParentStoreId("");
          }
      }
      return ae;
  }

  public static ActionErrors synchronizeStore(HttpServletRequest request,
                                              ActionForm form) throws Exception {
      ActionErrors ae = new ActionErrors();

      HttpSession session = request.getSession();
      StoreStoreMgrDetailForm sForm = (StoreStoreMgrDetailForm) form;
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

      APIAccess factory = new APIAccess();
      Store storeBean = factory.getStoreAPI();
      User userBean = factory.getUserAPI();

      if (!Utility.isSet(sForm.getParentStoreId())) {
          String propertyParentStoreId = ClwI18nUtil.getMessageOrNull(request, "store.property.parentStoreId");
          if (propertyParentStoreId == null) {
              propertyParentStoreId = "ParentStoreId";
          }
          ae.add("ParentStoreId", new ActionError("error.store.parentSynchronization", propertyParentStoreId));
      } else {
          ae = checkParentStoreProperties(request,
                                          storeBean,
                                          userBean,
                                          appUser,
                                          sForm);
      }
      if (ae.size() > 0) {
          return ae;
      }

      int storeId = 0;
      if (Utility.isSet(sForm.getId())) {
          storeId = Integer.parseInt(sForm.getId());
      }
      StoreData storeD = null;
      if (storeId > 0) {
          storeD = storeBean.getStore(storeId);
      }
      PropertyDataVector props = null;
      if (storeD != null) {
          props = storeD.getMiscProperties();
          if (props == null) {
              props = new PropertyDataVector();
          }
          PropertyData pD;
          String propType = "";
          boolean parentStoreId = false;
          for (int i = 0; i < props.size(); i++) {
             pD = (PropertyData) props.get(i);
             propType = pD.getPropertyTypeCd();

             if (RefCodeNames.PROPERTY_TYPE_CD.PARENT_STORE_ID.equals(propType)) {
                 parentStoreId = true;
                 pD.setValue(sForm.getParentStoreId());
                 pD.setModBy(appUser.getUserName());
                 pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
             break;
             }
          }
          if (!parentStoreId) {
              if (Utility.isSet(sForm.getParentStoreId())) {
                  pD = PropertyData.createValue();
                  pD.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.PARENT_STORE_ID);
                  pD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.PARENT_STORE_ID);
                  pD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                  pD.setAddBy(appUser.getUserName());
                  pD.setModBy(appUser.getUserName());
                  pD.setValue(sForm.getParentStoreId());
                  props.add(pD);
              }
          }
          storeBean.updateStore(storeD);
      }

      storeBean.synchronizeChildStore(storeId, appUser);

      return ae;
  }

  public static BusEntityFieldsData fetchAccountFields(HttpServletRequest request, ActionForm form) throws Exception {

    StoreStoreMgrDetailForm sfdf = (StoreStoreMgrDetailForm) form;
    int id = sfdf.getIntId();
    APIAccess factory = new APIAccess();
    PropertyService psvcBean = factory.getPropertyServiceAPI();
    BusEntityFieldsData sfd = psvcBean.fetchAccountFieldsData(id);
    sfdf.setBusEntityFieldsData(sfd);
    return sfd;
  }

  public static BusEntityFieldsData fetchMasterItemFields(HttpServletRequest request, ActionForm form) throws Exception {

      StoreStoreMgrDetailForm sfdf = (StoreStoreMgrDetailForm) form;
      int id = sfdf.getIntId();
      APIAccess factory = new APIAccess();
      PropertyService psvcBean = factory.getPropertyServiceAPI();
      BusEntityFieldsData sfd = psvcBean.fetchMasterItemFieldsData(id);
      sfdf.setMasterItemFieldsData(sfd);
      return sfd;
    }

  public static void saveAccountFields(HttpServletRequest pRequest, ActionForm pForm) throws Exception {

    StoreStoreMgrDetailForm form = (StoreStoreMgrDetailForm) pForm;
    BusEntityFieldsData siteFieldsData = form.getBusEntityFieldsData();
    int id = form.getIntId();
    APIAccess factory = new APIAccess();
    PropertyService psvcBean = factory.getPropertyServiceAPI();
    psvcBean.updateAccountFieldsData(id, siteFieldsData);
    return;
  }

  public static void saveMasterItemFields(HttpServletRequest pRequest, ActionForm pForm) throws Exception {

      StoreStoreMgrDetailForm form = (StoreStoreMgrDetailForm) pForm;
      BusEntityFieldsData masterItemFieldsData = form.getMasterItemFieldsData();
      int id = form.getIntId();
      APIAccess factory = new APIAccess();
      PropertyService psvcBean = factory.getPropertyServiceAPI();
      psvcBean.updateMasterItemFieldsData(id, masterItemFieldsData);
      return;
  }

  /**
   *inits the reasource configuration for the store
   */
  public static ActionErrors initResourceConfig(HttpServletRequest request,
                                                ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }
    PropertyService propEjb = factory.getPropertyServiceAPI();
    StoreStoreMgrDetailForm sForm = (StoreStoreMgrDetailForm) form;
    if (sForm == null)throw new Exception("Form not found");

    int storeId = sForm.getIntId();
    sForm.setNameSearchMessage("");
    sForm.setResMessageShowType("Store");

    MessageResourceDataVector resultDV = propEjb.getMessageResourcesByKey(storeId, null, sForm.getSelectedResourceLocale(), null);
    MessageResourceDataVector mrdvDefault = propEjb.getMessageResourcesByKey(0, null, sForm.getSelectedResourceLocale(), null);
    ae = checkOnMultipleRecord(resultDV);
    if (ae.size() > 0) {
      sForm.setStoreMessageResources(new MessageResourceDataVector());
      return ae;
    }
    Hashtable htMessageResValue = new Hashtable();
    ae = loadHTMessageResourcesValue(resultDV, mrdvDefault, htMessageResValue);

    if (resultDV != null)
      DisplayListSort.sort(resultDV, "name");
    sForm.setStoreMessageResources(resultDV);
    sForm.setMessageResourcesDefaultValue(htMessageResValue);
    return ae;
  }

  private static ActionErrors mergeMessageResourcesDVAndSetDefValue(MessageResourceDataVector mrStDV,
    MessageResourceDataVector mrDefDV, MessageResourceDataVector resDV, Hashtable htDefValue) throws Exception {

    ActionErrors ae = new ActionErrors();
    if (mrStDV == null || mrDefDV == null || resDV == null || htDefValue == null)throw new Exception("Not all data");

    MessageResourceDataVector mnrDV = new MessageResourceDataVector();
    resDV.addAll(mrStDV);

    MessageResourceData wrkMessResD = null;
    for (Iterator iter = mrDefDV.iterator(); iter.hasNext(); ) {
      MessageResourceData mrDefD = (MessageResourceData) iter.next();
      String nameDef = mrDefD.getName();
      boolean foundFl = false;
      boolean searchFl = false;
      Iterator iter1 = mrStDV.iterator();
      while (iter1.hasNext()) {
        wrkMessResD = (MessageResourceData) iter1.next();
        if (!searchFl) searchFl = true;

        if (nameDef.equals(wrkMessResD.getName())) {
          foundFl = true;
          break;
        }

      }
      if (foundFl) {

        htDefValue.put(wrkMessResD.getName(), mrDefD.getValue());
      } else {

        if (mrDefD.getValue() != null)
          htDefValue.put(nameDef, mrDefD.getValue());
        mrDefD.setValue("");
        mrDefD.setMessageResourceId(0);
        resDV.add(mrDefD);
      }
    }
    ae = checkOnMultipleRecord(resDV);
    return ae;
  }

  private static ActionErrors checkOnMultipleRecord(MessageResourceDataVector mrStDV) {
    ActionErrors ae = new ActionErrors();
    ArrayList multipleControl = new ArrayList();
    Iterator iter = mrStDV.iterator();
    while (iter.hasNext()) {
      MessageResourceData mrD = (MessageResourceData) iter.next();
      if (multipleControl.contains(mrD.getName())) {
        ae.add("error", new ActionError("error.simpleGenericError", "Multiple message resource key.Key : " + mrD.getName()));
        return ae;
      }
      multipleControl.add(mrD.getName());
    }
    return ae;
  }

  public static ActionErrors searchMessageResources(HttpServletRequest request, ActionForm form) throws Exception {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

    if (null == factory) {
      throw new Exception("Without APIAccess.");
    }

    PropertyService propEjb = factory.getPropertyServiceAPI();
    StoreStoreMgrDetailForm sForm = (StoreStoreMgrDetailForm) form;

    int storeId = sForm.getIntId();
    String type = sForm.getResMessageShowType();

    MessageResourceDataVector mrdv = new MessageResourceDataVector();
    MessageResourceDataVector resultDV = new MessageResourceDataVector();
    Hashtable htMessageResValue = new Hashtable();

    if (type.equals("Store")) {

      if (sForm.getNameSearchMessage() != null && sForm.getNameSearchMessage().trim().length() > 0) {
        resultDV = propEjb.getMessageResourcesByKey(storeId, sForm.getNameSearchMessage(), sForm.getSelectedResourceLocale(),
          "contains");
        MessageResourceDataVector mrdvDefault = propEjb.getMessageResourcesByKey(0, sForm.getNameSearchMessage(),
          sForm.getSelectedResourceLocale(), "contains");
        ae = checkOnMultipleRecord(resultDV);
        if (ae.size() > 0)return ae;
        ae = loadHTMessageResourcesValue(resultDV, mrdvDefault, htMessageResValue);

      } else {
        resultDV = propEjb.getMessageResourcesByKey(storeId, null, sForm.getSelectedResourceLocale(), null);
        MessageResourceDataVector mrdvDefault = propEjb.getMessageResourcesByKey(0, null, sForm.getSelectedResourceLocale(), null);
        ae = checkOnMultipleRecord(resultDV);
        if (ae.size() > 0)return ae;
        ae = loadHTMessageResourcesValue(resultDV, mrdvDefault, htMessageResValue);

      }

    } else if (type.equals("Default")) {

      if (sForm.getNameSearchMessage() != null && sForm.getNameSearchMessage().trim().length() > 0) {
        mrdv = propEjb.getMessageResourcesByKey(0, sForm.getNameSearchMessage(), sForm.getSelectedResourceLocale(),
                                                "contains");
        ae = mergeMessageResourcesDVAndSetDefValue(new MessageResourceDataVector(), mrdv, resultDV, htMessageResValue);
      } else {
        mrdv = propEjb.getMessageResourcesByKey(0, null, sForm.getSelectedResourceLocale(), null);
        ae = mergeMessageResourcesDVAndSetDefValue(new MessageResourceDataVector(), mrdv, resultDV, htMessageResValue);
      }

    } else {

      if (sForm.getNameSearchMessage() != null && sForm.getNameSearchMessage().trim().length() > 0) {
        MessageResourceDataVector mrdvStore = propEjb.getMessageResourcesByKey(storeId, sForm.getNameSearchMessage(),
          sForm.getSelectedResourceLocale(), "contains");
        MessageResourceDataVector mrdvDefault = propEjb.getMessageResourcesByKey(0, sForm.getNameSearchMessage(),
          sForm.getSelectedResourceLocale(), "contains");
        ae = mergeMessageResourcesDVAndSetDefValue(mrdvStore, mrdvDefault, resultDV, htMessageResValue);

      } else {
        MessageResourceDataVector mrdvStore = propEjb.getMessageResourcesByKey(storeId, null,
          sForm.getSelectedResourceLocale(), null);
        MessageResourceDataVector mrdvDefault = propEjb.getMessageResourcesByKey(0, null, sForm.getSelectedResourceLocale(), null);
        ae = mergeMessageResourcesDVAndSetDefValue(mrdvStore, mrdvDefault, resultDV, htMessageResValue);

      }
    }
    if (ae.size() > 0) {
      sForm.setStoreMessageResources(new MessageResourceDataVector());
      return ae;
    }

    if (resultDV != null)
      DisplayListSort.sort(resultDV, "name");
    sForm.setStoreMessageResources(resultDV);
    sForm.setMessageResourcesDefaultValue(htMessageResValue);
    return ae;
  }

  private static ActionErrors loadHTMessageResourcesValue(MessageResourceDataVector mrStDV,
    MessageResourceDataVector mrDefDV, Hashtable htDefaultValue) {

    ActionErrors ae = new ActionErrors();
    Iterator iter = mrStDV.iterator();
    while (iter.hasNext()) {
      MessageResourceData mrD = (MessageResourceData) iter.next();
      Iterator iter2 = mrDefDV.iterator();
      while (iter2.hasNext()) {
        MessageResourceData mrD2 = (MessageResourceData) iter2.next();
        if (mrD2.getName().equals(mrD.getName())) {

          htDefaultValue.put(mrD.getName(), mrD2.getValue());

        }
      }
    }
    return ae;
  }

  /**
   *saves the reasource configuration for the store
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
    StoreStoreMgrDetailForm sForm = (StoreStoreMgrDetailForm) form;
    int storeId = sForm.getIntId();
    MessageResourceDataVector mrdv = sForm.getStoreMessageResources();
    Iterator it = mrdv.iterator();
    while (it.hasNext()) {
      MessageResourceData resource = (MessageResourceData) it.next();
      //if this was not set, and is not currently in the db then don't send it
      //to ejb for update
      if (!Utility.isSet(resource.getValue())) {
        it.remove();
      } else {
        resource.setBusEntityId(storeId);
        resource.setValue(I18nUtil.getUtf8Str(resource.getValue()));
      }
    }

    propEjb.saveMessageResources(mrdv, appUser.getUserName());
    //initResourceConfig(request,form);
  }

  private static CountryData getCountry(HttpSession session, StoreStoreMgrDetailForm pForm) {
    CountryDataVector countries = (CountryDataVector) session.getAttribute("country.vector");
    String countryStr = pForm.getCountry();
    if (countryStr != null) {
      for (int i = 0; i < countries.size(); i++) {
        CountryData country = (CountryData) countries.get(i);
        if (country.getShortDesc().equals(countryStr)) {
          return country;
        }
      }
    }
    return CountryData.createValue();
  }
  
  private static RefCdDataVector getStoreEmailTemplateChoices(String storeId) throws Exception {
	  RefCdDataVector templateChoices = new RefCdDataVector();
      //if a store id is available, populate the choices for the email template drop downs.
      //Do this from scratch to make sure the list of choices reflects the most up to date information.
      //Also, pass this list of choices on the form instead of via a session attribute
      if (Utility.isSet(storeId)) {
    	  TemplateDataVector emailTemplates = TemplateUtilities.getAllEmailTemplatesForStore(new Integer(storeId.trim()).intValue());
    	  Map<String, String> templateNames = new HashMap();
    	  Iterator<TemplateData> templateIterator = emailTemplates.iterator();
    	  while (templateIterator.hasNext()) {
    		  TemplateData template = templateIterator.next();
    		  templateNames.put(template.getName().toLowerCase(), template.getName());
    	  }
    	  Iterator<String> templateNameIterator = new TreeMap(templateNames).keySet().iterator();
    	  while (templateNameIterator.hasNext()) {
    		  String templateName = templateNames.get(templateNameIterator.next());
    		  RefCdData refCode = new RefCdData();
    		  refCode.setShortDesc(templateName);
    		  refCode.setValue(templateName);
    		  templateChoices.add(refCode);
    	  }
      }
	  return templateChoices;
  }

  public static ActionErrors initStoreProfile(HttpServletRequest pRequest, ActionForm pForm) throws Exception {
	  
	  ActionErrors ae = new ActionErrors();
	  
	  HttpSession session = pRequest.getSession();
	  String sid = (String)session.getAttribute("Store.id");
	  
	  StoreStoreMgrDetailForm form = (StoreStoreMgrDetailForm) pForm;
	  StoreProfileDto storeProfile = new StoreProfileDto();
	  List selectedLanguages = new ArrayList();
	  
	  APIAccess factory = new APIAccess();
	  Store storeBean = factory.getStoreAPI();
	  Language langBean = factory.getLanguageAPI();
	  
	  StoreProfileDataVector storeProfileDV = storeBean.getStoreProfile(Integer.parseInt(sid));
	  
	  Iterator it = storeProfileDV.iterator();
	  while(it.hasNext()){
		  StoreProfileData prof = (StoreProfileData)it.next();
		  //STJ-5778 - if a value of "None" was selected for a language option, the short description for that
		  //StoreProfileData will be null.  Code for that to avoid a NullPointerException. 
		  String shortDescription = Utility.strNN(prof.getShortDesc());
		  
		  if(shortDescription.equals(RefCodeNames.STORE_PROFILE_FIELD.PROFILE_NAME)){
			  storeProfile.setProfileNameDisplay(Utility.isTrue(prof.getDisplay()));
			  storeProfile.setProfileNameEdit(Utility.isTrue(prof.getEdit()));
			  
		  }else if(shortDescription.equals(RefCodeNames.STORE_PROFILE_FIELD.LANGUAGE)){
			  storeProfile.setLanguageDisplay(Utility.isTrue(prof.getDisplay()));
			  storeProfile.setLanguageEdit(Utility.isTrue(prof.getEdit()));
			  
		  }else if(shortDescription.equals(RefCodeNames.STORE_PROFILE_FIELD.COUNTRY)){
			  storeProfile.setCountryDisplay(Utility.isTrue(prof.getDisplay()));
			  storeProfile.setCountryEdit(Utility.isTrue(prof.getEdit()));
			  
		  }else if(shortDescription.equals(RefCodeNames.STORE_PROFILE_FIELD.CONTACT_ADDRESS)){
			  storeProfile.setContactAddressDisplay(Utility.isTrue(prof.getDisplay()));
			  storeProfile.setContactAddressEdit(Utility.isTrue(prof.getEdit()));
		
		  }else if(shortDescription.equals(RefCodeNames.STORE_PROFILE_FIELD.PHONE)){
			  storeProfile.setPhoneDisplay(Utility.isTrue(prof.getDisplay()));
			  storeProfile.setPhoneEdit(Utility.isTrue(prof.getEdit()));
			  
		  }else if(shortDescription.equals(RefCodeNames.STORE_PROFILE_FIELD.MOBILE)){
			  storeProfile.setMobileDisplay(Utility.isTrue(prof.getDisplay()));
			  storeProfile.setMobileEdit(Utility.isTrue(prof.getEdit()));
			  
		  }else if(shortDescription.equals(RefCodeNames.STORE_PROFILE_FIELD.FAX)){
			  storeProfile.setFaxDisplay(Utility.isTrue(prof.getDisplay()));
			  storeProfile.setFaxEdit(Utility.isTrue(prof.getEdit()));
			  
		  }else if(shortDescription.equals(RefCodeNames.STORE_PROFILE_FIELD.EMAIL)){
			  storeProfile.setEmailDisplay(Utility.isTrue(prof.getDisplay()));
			  storeProfile.setEmailEdit(Utility.isTrue(prof.getEdit()));
			  
		  }else if(shortDescription.equals(RefCodeNames.STORE_PROFILE_FIELD.CHANGE_PASSWORD)){
			  storeProfile.setChangePassword(Utility.isTrue(prof.getDisplay()));
			  
		  }else{
			  if(prof.getOptionTypeCd().equals(RefCodeNames.STORE_PROFILE_TYPE_CD.LANGUAGE_OPTION)){
				  selectedLanguages.add(shortDescription);
			  }		  
		  }
	  }
	  
	  //Languages
	  if(session.getAttribute("store.languages.options")==null){
		  //String[] storeLanguageOptions = Constants.STORE_LANGUAGE_LIST;		  
		  LanguageDataVector lDV = langBean.getSupportedLanguages();
		  List<LabelValueBean> langList = new ArrayList();
		  
		  for(int i=0; i<lDV.size(); i++){
			  LanguageData lData = (LanguageData)lDV.get(i);
			  langList.add(new LabelValueBean(lData.getShortDesc(),Integer.toString(lData.getLanguageId())));
		  }	  
          session.setAttribute("store.languages.options",langList);
      }
	
	  if(selectedLanguages!=null){
		  String[] storeLanguages = new String[selectedLanguages.size()];
		  Iterator it2 = selectedLanguages.iterator();
		  int ii=0;
		  while(it2.hasNext()){
			  String lang = (String)it2.next();
			  storeLanguages[ii] = lang;
			  ii++;
		  }
		  storeProfile.setStoreLanguages(storeLanguages);
	  }  
	  
	  form.setStoreProfile(storeProfile);
	  return ae;
  }
  
  public static ActionErrors saveStoreProfile(HttpServletRequest pRequest, ActionForm pForm) throws Exception {
	  ActionErrors ae = new ActionErrors();
	  HttpSession session = pRequest.getSession();
	  String sid = (String)pRequest.getSession().getAttribute("Store.id");
	  int storeId = Integer.parseInt(sid);
	  StoreStoreMgrDetailForm form = (StoreStoreMgrDetailForm)pForm;
	  StoreProfileDto storeProfile = form.getStoreProfile();
	  CleanwiseUser appUser = (CleanwiseUser) pRequest.getSession().getAttribute(Constants.APP_USER);
	  
	  APIAccess factory = new APIAccess();
	  Store storeBean = factory.getStoreAPI();
	  Language langBean = factory.getLanguageAPI();
	  List allLanguages = new ArrayList();
	  	  
	  LanguageDataVector lDV = langBean.getSupportedLanguages();
	  List langList = new ArrayList();
		  
	  for(int i=0; i<lDV.size(); i++){
		  LanguageData lData = (LanguageData)lDV.get(i);
		  langList.add(Integer.toString(lData.getLanguageId()));
	  }	  

	  storeBean.updateStoreProfile(appUser.getUserName(), storeId, storeProfile,langList);
	  return ae;
  }
  
  
}
