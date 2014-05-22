package com.cleanwise.view.logic;


import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Budget;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Contract;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.Group;
import com.cleanwise.service.api.session.ListService;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.OrderRoutingUtil;
import com.cleanwise.service.api.util.QueryRequest;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.TemplateUtilities;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.service.api.value.AccountOrderPipeline;
import com.cleanwise.service.api.value.AccountSearchResultViewVector;
import com.cleanwise.service.api.value.AccountSettingsData;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.BillToData;
import com.cleanwise.service.api.value.BudgetDetailData;
import com.cleanwise.service.api.value.BudgetDetailDataVector;
import com.cleanwise.service.api.value.BudgetView;
import com.cleanwise.service.api.value.BudgetViewVector;
import com.cleanwise.service.api.value.BusEntityAssocData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityParameterData;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.ContractDescData;
import com.cleanwise.service.api.value.ContractDescDataVector;
import com.cleanwise.service.api.value.CountryData;
import com.cleanwise.service.api.value.CountryDataVector;
import com.cleanwise.service.api.value.CountryPropertyData;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.DistributorDataVector;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.FiscalCalendarInfo;
import com.cleanwise.service.api.value.FiscalCalenderData;
import com.cleanwise.service.api.value.FiscalCalenderDetailData;
import com.cleanwise.service.api.value.FiscalCalenderDetailDataVector;
import com.cleanwise.service.api.value.FiscalCalenderView;
import com.cleanwise.service.api.value.FiscalCalenderViewVector;
import com.cleanwise.service.api.value.FreightHandlerView;
import com.cleanwise.service.api.value.FreightHandlerViewVector;
import com.cleanwise.service.api.value.GroupDataVector;
import com.cleanwise.service.api.value.GroupSearchCriteriaView;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderRoutingData;
import com.cleanwise.service.api.value.OrderRoutingDataVector;
import com.cleanwise.service.api.value.OrderRoutingDescView;
import com.cleanwise.service.api.value.OrderRoutingDescViewVector;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.service.api.value.ProductViewDefData;
import com.cleanwise.service.api.value.ProductViewDefDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.RefCdData;
import com.cleanwise.service.api.value.RefCdDataVector;
import com.cleanwise.service.api.value.ShoppingControlData;
import com.cleanwise.service.api.value.ShoppingControlDataVector;
import com.cleanwise.service.api.value.ShoppingControlItemView;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteDataVector;
import com.cleanwise.service.api.value.SiteDeliveryScheduleViewVector;
import com.cleanwise.service.api.value.SiteView;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.StoreDataVector;
import com.cleanwise.service.api.value.TemplateData;
import com.cleanwise.service.api.value.TemplateDataVector;
import com.cleanwise.view.forms.StoreAccountBillToForm;
import com.cleanwise.view.forms.StoreAccountMgrDetailForm;
import com.cleanwise.view.forms.StoreAccountMgrForm;
import com.cleanwise.view.forms.StoreAccountMgrSearchForm;
import com.cleanwise.view.forms.StoreAccountShoppingControlForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.CurrencyFormat;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.cleanwise.view.utils.StringUtils;
import com.cleanwise.view.utils.validators.EmailValidator;

/**
 *
 * @author Alexander Chikin
 * Date: 15.08.2006
 * Time: 1:34:10
 ** Code: StoreAccountMgrLogic implements the logic needed to manipulate
 * purchasing account records in storeportal.
 */
public class StoreAccountMgrLogic {
  private static final String ACCOUNT_STORE_MAP_CACHE = "account.store.map.cache";
  private static final Logger log = Logger.getLogger(StoreAccountMgrLogic.class);


  /**
   * This method establishes a set of the requested values(AccountBillTos)
   * in attribute of session
   * @param request
   * @param form
   * @return
   * @throws Exception
   */
  public static ActionErrors getAccountBillTos(HttpServletRequest request,
                                               ActionForm form) throws Exception {
	log.info("in method: ");
    ActionErrors reqErrors = new ActionErrors();
    HttpSession session = request.getSession();
    int accountId = getAccounIDfromRequest(request);

    APIAccess factory = new APIAccess();
    Account accountBean = factory.getAccountAPI();

    log.debug("class StoreAccountMgrLogic informs : ");
    log.debug("getAccountBillTos, accountId=" + accountId);
    ArrayList v = accountBean.getAccountBillTos(accountId);
    session.setAttribute("account.billtos.vector", v);

    return reqErrors;
  }

  /**^
   * This method add BillTos for current account
   * @param request
   * @param form
   * @return
   * @throws Exception
   */
  public static ActionErrors
    addBillTo(HttpServletRequest request,
              ActionForm form) throws Exception {

	  log.info("addBillTo");
    ActionErrors reqErrors = new ActionErrors();
    HttpSession session = request.getSession();
    int accountId = getAccounIDfromRequest(request);
    
    StoreAccountBillToForm sform = (StoreAccountBillToForm) form;
    APIAccess factory = new APIAccess();
    Account accountBean = factory.getAccountAPI();
    
    //check for required fields
    if (!Utility.isSet(sform.getBillTo().getBusEntity().getShortDesc())) {
    	reqErrors.add("error",new ActionError("variable.select.empty.error","Bill To Name"));
    }
    if (!Utility.isSet(sform.getBillTo().getBusEntity().getBusEntityStatusCd())) {
    	reqErrors.add("error",new ActionError("variable.select.empty.error","Status"));
    }

    if (reqErrors.isEmpty()) {
    	log.debug("class StoreAccountMgrLogic informs : ");
    	log.debug("addBillTo to accountId=" + accountId);
    	sform.getBillTo().getAccountBusEntity().setBusEntityId(accountId);
    	CleanwiseUser cwu = ShopTool.getCurrentUser(request);
    	sform.getBillTo().getBusEntity().setAddBy(cwu.getUserName());
    	sform.getBillTo().getBusEntity().setModBy(cwu.getUserName());
    	accountBean.addBillTo(sform.getBillTo());
    }

    ArrayList v = accountBean.getAccountBillTos(accountId);

    session.setAttribute("account.billtos.vector", v);

    return reqErrors;
  }


  /**^
   * This method searches account_id in (HttpServletRequest)request
   * or (StoreAccountMgrDetailForm )pForm and returnes this a  value
   * @param request
   * @return  account_id
   */
  public static int getAccounIDfromRequest(HttpServletRequest request) {
	  log.info("getAccounIDfromRequest");
    HttpSession session = request.getSession();
    String acctid_str = request.getParameter("accountId");
    int acctid_int = -1;
    if (acctid_str == null) {
      StoreAccountMgrDetailForm pForm =
        (StoreAccountMgrDetailForm) session.getAttribute("STORE_ACCOUNT_DETAIL_FORM");
      acctid_int = pForm.getAccountData().getAccountId();
    } else acctid_int = Integer.parseInt(acctid_str);

    return acctid_int;
  }

  /**
   *  Describe <code>getDetail</code> method here.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static ActionErrors getDetail(HttpServletRequest request,
                                       ActionForm form) throws Exception {

	  log.info("getDetail");
      ActionErrors ae = new ActionErrors();
      HttpSession session = request.getSession();
      StoreAccountMgrDetailForm sForm = (StoreAccountMgrDetailForm) session.getAttribute("STORE_ACCOUNT_DETAIL_FORM");

      init(request, form);

      String fieldValue = request.getParameter("accountId");
      if (null == fieldValue && sForm != null) {
          fieldValue = sForm.getId();
      }
      if (null == fieldValue) {
          fieldValue = request.getParameter("searchField");
      }

      if (null == fieldValue) {
          fieldValue = "0";
      }

      if (sForm == null) {
          sForm = new StoreAccountMgrDetailForm();
          session.setAttribute("STORE_ACCOUNT_DETAIL_FORM", sForm);
      }

      APIAccess factory = new APIAccess();
      Account accountBean = factory.getAccountAPI();
      Store storei = factory.getStoreAPI();

      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      String erpSystemCode = appUser.getUserStore().getErpSystemCode();
      CleanwiseUser cwu = ShopTool.getCurrentUser(request);
      int accountid = Integer.parseInt(fieldValue);

      AccountData dd;
      try{
          dd = accountBean.getAccount(accountid, cwu.getUserStore().getStoreId());
      }catch(DataNotFoundException e){

          session.setAttribute("Account.id", "0");
          session.setAttribute("Account.name", "-");
          session.setAttribute("Store.id", "0");
          session.setAttribute("Store.name", "--");
          session.setAttribute("STORE_ACCOUNT_DETAIL_FORM", new StoreAccountMgrDetailForm());
          ae.add("error1", new ActionError("error.simpleGenericError", "Account is not found"));
          return ae;
      }

      sForm.setAccountData(dd);

      if (null != dd) {
          // Set the form values.
          sForm.setRuntimeDisplayOrderItemActionTypes((String[]) dd.getRuntimeDisplayOrderItemActionTypes().toArray(new String[0]));

          BusEntityData bed = dd.getBusEntity();
          sForm.setName(bed.getShortDesc());
          sForm.setStatusCd(bed.getBusEntityStatusCd());
          sForm.setTimeZoneCd(bed.getTimeZoneCd());
          sForm.setId(String.valueOf(bed.getBusEntityId()));
          sForm.setTypeCd(bed.getBusEntityTypeCd());

          sForm.setGlTransformationType(dd.getGlTransformationType());
          sForm.setDistributorAccountRefNum(dd.getDistributorAccountRefNum());

          if (bed.getErpNum() != null && bed.getErpNum().length() > 0)
              sForm.setAccountNumber(bed.getErpNum());
          else {
              sForm.setAccountNumber("#" + accountid);
              bed.setErpNum("#" + accountid);
          }

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
          if (null != orderGuideNote) {
              sForm.setOrderGuideNote(orderGuideNote.getValue());
          } else {
              sForm.setOrderGuideNote("");
          }

          PropertyData skuTag = dd.getSkuTag();
          if (null != skuTag) {
              sForm.setSkuTag(skuTag.getValue());
          } else {
              sForm.setSkuTag("");
          }

          sForm.setCustomerSystemApprovalCd(dd.getCustomerSystemApprovalCd());
          sForm.setCustomerRequestPoAllowed(dd.isCustomerRequestPoAllowed());
          sForm.setTaxableIndicator(dd.isTaxableIndicator());


          EmailData email = dd.getPrimaryEmail();
          sForm.setEmailAddress(email.getEmailAddress());

          EmailData customerEmail = dd.getCustomerServiceEmail();
          sForm.setCustomerEmail(customerEmail.getEmailAddress());

          EmailData defaultEmail = dd.getDefaultEmail();
          sForm.setDefaultEmail(defaultEmail.getEmailAddress());

          String contactUsCCEmail = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_CC_ADD);
          sForm.setContactUsCCEmail(contactUsCCEmail);

          log.debug("---------------------->>Setting filed Properties: " + dd.getDataFieldProperties().size());
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

          if (dd.isAuthorizedForResale()) {
              sForm.setReSaleAccountErpNumber(bed.getErpNum());
              dd.getResaleAccountErpNumber().setValue(bed.getErpNum());
          } else {
              if (dd.getResaleAccountErpNumber() != null) {
                  sForm.setReSaleAccountErpNumber(dd.getResaleAccountErpNumber().getValue());
              } else sForm.setReSaleAccountErpNumber(null);
          }
          String ediShipToPrefix = dd.getEdiShipToPrefix();
          if (ediShipToPrefix == null || ediShipToPrefix.trim().length() == 0) {
              ediShipToPrefix = "" + accountid;
          }
          sForm.setEdiShipToPrefix(ediShipToPrefix);

          if (dd.getTargetMargin() == null) {
              sForm.setTargetMarginStr("0");
          } else {
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

      if (!Utility.isSet(dd.getFreightChargeType())) {
          sForm.setFreightChargeType("PC");
      } else {
          sForm.setFreightChargeType(dd.getFreightChargeType());
      }
      sForm.setPurchaseOrderAccountName(dd.getPurchaseOrderAccountName());
      sForm.setBudgetTypeCd(dd.getBudgetTypeCd());
      sForm.setDistrPoType(dd.getDistrPOType());

      try {
          // Look for an order pipeline.
          initOrderPipeline(factory, accountid, sForm);
          log.debug("  initOrderPipeline done");
      } catch (Exception exc) {
          exc.printStackTrace();
      }

      boolean showSchedDel = false;
      String showSchedDelS = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_SCHED_DELIVERY);
      sForm.setMakeShipToBillTo(dd.isMakeShipToBillTo());

      if (showSchedDelS != null && showSchedDelS.length() > 0 &&
              "T".equalsIgnoreCase(showSchedDelS.substring(0, 1))) {
          showSchedDel = true;
      }
      sForm.setShowScheduledDelivery(showSchedDel);

      sForm.setRushOrderCharge(dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.RUSH_ORDER_CHARGE));
      sForm.setAutoOrderFactor(dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.AUTO_ORDER_FACTOR));
      sForm.setCartReminderInterval(dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CART_REMINDER_INTERVAL));

      sForm.setInvPOSuffix(Utility.strNN(dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_PO_SUFFIX)));
      sForm.setInvLedgerSwitch(Utility.isOff(dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_LEDGER_SWITCH),false));
      sForm.setInvOGListUI(Utility.strNN(dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_OG_LIST_UI)));
      sForm.setInvMissingNotification(Utility.strNN(dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_MISSING_NOTIFICATION)));
      sForm.setInvCheckPlacedOrder(Utility.strNN(dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_CHECK_PLACED_ORDER)));
      sForm.setConnectionCustomer(Utility.isOn(dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONNECTION_CUSTOMER),false));

      sForm.setShowDistInventory(dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.DIST_INVENTORY_DISPLAY));
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

      String showDistDeliveryDateS =
              dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_DIST_DELIVERY_DATE);
      boolean showDistDeliveryDate = Utility.isTrue(showDistDeliveryDateS, false);
      sForm.setShowDistDeliveryDate(showDistDeliveryDate);

      String scheduleCutoffDaysS =
              dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SCHEDULE_CUTOFF_DAYS);
      if (scheduleCutoffDaysS == null) scheduleCutoffDaysS = "";
      sForm.setScheduleCutoffDays(scheduleCutoffDaysS);

      BusEntityParameterData rebatePersentBEPD =
              dd.getAccountParameterActual("Rebate Persent");
      String rebatePersentS = "";
      String rebateEffDateS = "";
      if (rebatePersentBEPD != null) {
          rebatePersentS = rebatePersentBEPD.getValue();
          if (Utility.isSet(rebatePersentS)) {
              Date rebateEffDate = rebatePersentBEPD.getEffDate();
              SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
              rebateEffDateS = sdf.format(rebateEffDate);
          }
      }

      sForm.setRebatePersent(rebatePersentS);
      sForm.setRebateEffDate(rebateEffDateS);

      ArrayList orderManagerEmailV = dd.getOrderManagerEmails();
      String orderManagerEmailS = "";
      if (orderManagerEmailV != null) {
          for (int ii = 0; ii < orderManagerEmailV.size(); ii++) {
              String eMail = (String) orderManagerEmailV.get(ii);
              if (eMail != null) {
                  eMail = eMail.trim();
                  if (eMail.length() > 0) {
                      if (ii > 0) orderManagerEmailS += ",";
                      orderManagerEmailS += eMail;
                  }
              }
          }
      }
      sForm.setOrderManagerEmails(orderManagerEmailS);

      sForm.setInvReminderEmailSub(Utility.strNN(dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_SUBJECT)));
      sForm.setInvReminderEmailMsg(Utility.strNN(dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_MSG)));

      String notifyOrderEmailGenerator = dd.getPropertyValue(
          RefCodeNames.PROPERTY_TYPE_CD.NOTIFY_ORDER_EMAIL_GENERATOR);
      if (notifyOrderEmailGenerator != null) {
          sForm.setNotifyOrderEmailGenerator(notifyOrderEmailGenerator);
      }
      String confirmOrderEmailGenerator = dd.getPropertyValue(
          RefCodeNames.PROPERTY_TYPE_CD.CONFIRM_ORDER_EMAIL_GENERATOR);
      if (confirmOrderEmailGenerator != null) {
          sForm.setConfirmOrderEmailGenerator(confirmOrderEmailGenerator);
      }
      String rejectOrderEmailGenerator = dd.getPropertyValue(
          RefCodeNames.PROPERTY_TYPE_CD.REJECT_ORDER_EMAIL_GENERATOR);
      if (rejectOrderEmailGenerator != null) {
          sForm.setRejectOrderEmailGenerator(rejectOrderEmailGenerator);
      }
      String pendingApprovEmailGenerator = dd.getPropertyValue(
          RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROV_EMAIL_GENERATOR);
      if (pendingApprovEmailGenerator != null) {
          sForm.setPendingApprovEmailGenerator(pendingApprovEmailGenerator);
      }

      //set email template information
      String orderConfirmationEmailTemplate = dd.getPropertyValue(
              RefCodeNames.PROPERTY_TYPE_CD.ORDER_CONFIRMATION_EMAIL_TEMPLATE);
      if (orderConfirmationEmailTemplate != null) {
          sForm.setOrderConfirmationEmailTemplate(orderConfirmationEmailTemplate);
      }
      String shippingNotificationEmailTemplate = dd.getPropertyValue(
              RefCodeNames.PROPERTY_TYPE_CD.SHIPPING_NOTIFICATION_EMAIL_TEMPLATE);
      if (shippingNotificationEmailTemplate != null) {
          sForm.setShippingNotificationEmailTemplate(shippingNotificationEmailTemplate);
      }
      String pendingApprovalEmailTemplate = dd.getPropertyValue(
              RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROVAL_EMAIL_TEMPLATE);
      if (pendingApprovalEmailTemplate != null) {
          sForm.setPendingApprovalEmailTemplate(pendingApprovalEmailTemplate);
      }
      String rejectedOrderEmailTemplate = dd.getPropertyValue(
              RefCodeNames.PROPERTY_TYPE_CD.REJECTED_ORDER_EMAIL_TEMPLATE);
      if (rejectedOrderEmailTemplate != null) {
          sForm.setRejectedOrderEmailTemplate(rejectedOrderEmailTemplate);
      }
      String modifiedOrderEmailTemplate = dd.getPropertyValue(
              RefCodeNames.PROPERTY_TYPE_CD.MODIFIED_ORDER_EMAIL_TEMPLATE);
      if (modifiedOrderEmailTemplate != null) {
          sForm.setModifiedOrderEmailTemplate(modifiedOrderEmailTemplate);
      }
      sForm.setEmailTemplateChoices(StoreAccountMgrLogic.getStoreEmailTemplateChoices(sForm.getStoreId()));
      
      boolean showSPL = false;
      String showSPLS = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_SPL);
      if (showSPLS != null
              && showSPLS.length() > 0
              && "T".equalsIgnoreCase(showSPLS.substring(0, 1))) {
          showSPL = true;
      }
      sForm.setShowSPL(showSPL);

      boolean addServiceFee = false;
      String addServiceFeeS = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADD_SERVICE_FEE);
      if (addServiceFeeS != null
              && addServiceFeeS.length() > 0
              && "T".equalsIgnoreCase(addServiceFeeS.substring(0, 1))) {
    	  addServiceFee = true;
      }
      sForm.setAddServiceFee(addServiceFee);

      boolean holdPO = false;
      String holdPOS = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.HOLD_PO);
      holdPO = Utility.isTrue(holdPOS,false);
      sForm.setHoldPO(holdPO);

      boolean allowChangePassword = false;
      String allowChangePasswordS =
          dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_USER_CHANGE_PASSWORD);
      //allowChangePassword = Utility.isTrue(allowChangePasswordS,false);
      allowChangePassword = Utility.isTrue(allowChangePasswordS,true);
      sForm.setAllowChangePassword(allowChangePassword);


      String accountFolder = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FOLDER);
      if (accountFolder != null) {
          sForm.setAccountFolder(accountFolder);
      }
      String accountFolderNew = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FOLDER_NEW);
      if (accountFolderNew != null) {
          sForm.setAccountFolderNew(accountFolderNew);
      }
      /*
      String workflowEmail = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.WORKFLOW_EMAIL);
      if (workflowEmail != null) {
          sForm.setWorkflowEmail(workflowEmail);
      }
      */

      String budgetThresholdType = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.BUDGET_THRESHOLD_TYPE);
      budgetThresholdType = Utility.isSet(budgetThresholdType) ? budgetThresholdType : RefCodeNames.BUDGET_THRESHOLD_TYPE.NONE;
      sForm.setBudgetThresholdType(Utility.strNN(budgetThresholdType));

      String modifyQtyBy855 = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADJUST_QTY_BY_855);
      sForm.setModifyQtyBy855(Utility.isTrue(modifyQtyBy855,false));

      String createOrder855  = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CREATE_ORDER_BY_855);
      sForm.setCreateOrderBy855(Utility.isTrue(createOrder855,false));

      String createOrderItems855 = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CREATE_ORDER_ITEMS_BY_855);
      sForm.setCreateOrderItemsBy855(Utility.isTrue(createOrderItems855,false));

      String allowModernShopping = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_MODERN_SHOPPING);
      sForm.setAllowModernShopping(Utility.isTrue(allowModernShopping,false));

      String allowSiteLLC = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SITE_LLC);
      sForm.setAllowSiteLLC(Utility.isTrue(allowSiteLLC,false));

      String showExpressOrder = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_EXPRESS_ORDER);
      sForm.setShowExpressOrder(Utility.isTrue(showExpressOrder,false));

      String allowOrderInvItems = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_INV_ITEMS);
      sForm.setAllowOrderInvItems(Utility.isTrue(allowOrderInvItems,false));

      String allowReorder = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_REORDER);
      sForm.setAllowReorder(Utility.isTrue(allowReorder,false));

      String usePhysicalInventory = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.USE_PHYSICAL_INVENTORY);
      sForm.setUsePhysicalInventory(Utility.isTrue(usePhysicalInventory,false));

      String showInvCartTotal = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_INV_CART_TOTAL);
      sForm.setShowInvCartTotal(Utility.isTrue(showInvCartTotal,false));

      String showMyShoppingLists = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_MY_SHOPPING_LISTS);
      sForm.setShowMyShoppingLists(Utility.isTrue(showMyShoppingLists,false));

      String showReBillOrder = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_REBILL_ORDER);
      sForm.setShowReBillOrder(Utility.isTrue(showReBillOrder,false));


      sForm.setShopUIType(Utility.strNN(dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOP_UI_TYPE)));
      String allowSetWorkOrderPoNumber = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SET_WORKORDER_PO_NUMBER);
      sForm.setAllowSetWorkOrderPoNumber(Utility.isTrue(allowSetWorkOrderPoNumber,false));
      String workOrderPoNumberIsRequired = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.WORK_ORDER_PO_NUM_REQUIRED);
      sForm.setWorkOrderPoNumberIsRequired(Utility.isTrue(workOrderPoNumberIsRequired,false));
      String allowBuyWorkOrderParts = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_BUY_WORK_ORDER_PARTS);
      sForm.setAllowBuyWorkOrderParts(Utility.isTrue(allowBuyWorkOrderParts,false));
      String userAssignedAssetNumber = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.USER_ASSIGNED_ASSET_NUMBER);
      sForm.setUserAssignedAssetNumber(Utility.isTrue(userAssignedAssetNumber,false));
      sForm.setContactInformationType(Utility.strNN(dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_INFORMATION_TYPE)));
      String allowAlternateShipTo = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ALTERNATE_SHIP_TO_ADDRESS);
      sForm.setAllowAlternateShipTo(Utility.isTrue(allowAlternateShipTo,false));
      String cloneModifyCustPoNumBy855 = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.MODIFY_CUST_PO_NUM_BY_855);
      sForm.setModifyCustPoNumBy855(Utility.isTrue(cloneModifyCustPoNumBy855,false));
      String cloneSupportsBudget = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SUPPORTS_BUDGET);
      sForm.setSupportsBudget(Utility.isTrue(cloneSupportsBudget,false));

      String faqLink = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.FAQ_LINK);
      sForm.setFaqLink(faqLink);

      String pdfOrderClass = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PDF_ORDER_CLASS);
      sForm.setPdfOrderClass(pdfOrderClass);

      String pdfOrderStatusClass = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PDF_ORDER_STATUS_CLASS);
      sForm.setPdfOrderStatusClass(pdfOrderStatusClass);

      boolean allowCC = false;
      String allowCCS = dd.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CC_PAYMENT);
      if (allowCCS != null
              && allowCCS.length() > 0
              && "T".equalsIgnoreCase(allowCCS.substring(0, 1))) {
          allowCC = true;
      }
      sForm.setAllowCreditCard(allowCC);

      sForm.setFirstUpdate(false);


      return ae;
  }


  /**
   *  Describe <code>updateAccount</code> method here.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@return                an <code>ActionErrors</code> value
   *@exception  Exception  if an error occurs
   */
	public static ActionErrors updateAccount(HttpServletRequest request, ActionForm form) throws Exception {
		log.info("updateAccount");
		ActionErrors lUpdateErrors = new ActionErrors();
		HttpSession session = request.getSession();

		CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
		String erpSystemCode = appUser.getUserStore().getErpSystemCode();
		CleanwiseUser cwu = ShopTool.getCurrentUser(request);
		boolean noReSaleAccountErpNumber = false;
		boolean noAccountErpNumber = false;

		StoreAccountMgrDetailForm sForm = (StoreAccountMgrDetailForm) form;

		BigDecimal targetMargin = null;
		if (sForm != null) {
			// Verify the form information submitted.

			if (sForm.getControlAccountData().equals("YES") && sForm.getAccountData() == null) {
				lUpdateErrors.add("error1", new ActionError("error.simpleGenericError", "Probably expired page was used. Please select account again"));
				recoveryForm(request, sForm);
				return lUpdateErrors;
			}

			if (sForm.getControlAccountData().equals("NO") && !sForm.isFirstUpdate()) {

				lUpdateErrors.add("error2", new ActionError("error.simpleGenericError", "Probably expired page was used. Please select account again"));

				recoveryForm(request, sForm);
				return lUpdateErrors;
			}

			if (RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
				if (sForm.getStoreId() == null || sForm.getStoreId().length() == 0) {
					lUpdateErrors.add("storeId", new ActionError("variable.empty.error", "Store Id"));
				}
			}

			if (sForm.getName() == null || sForm.getName().trim().length() == 0) {
				lUpdateErrors.add("name", new ActionError("variable.empty.error", "Name"));
			} else {
				sForm.setName(sForm.getName().trim());
			}

			if (sForm.getTypeDesc() == null || sForm.getTypeDesc().length() == 0) {
				lUpdateErrors.add("typeDesc", new ActionError("variable.empty.error", "Account Type"));
			}
			if (sForm.getStatusCd() == null || sForm.getStatusCd().length() == 0) {
				lUpdateErrors.add("statusCd", new ActionError("variable.empty.error", "Status"));
			}
			if (sForm.getBudgetTypeCd() == null || sForm.getBudgetTypeCd().length() == 0) {
				lUpdateErrors.add("budgetTypeCd", new ActionError("variable.empty.error", "Budget Type"));
			}
			if ((sForm.getAccountNumber() == null || sForm.getAccountNumber().length() == 0)) {
				noAccountErpNumber = true;
			}
			if (!sForm.isFirstUpdate() && noAccountErpNumber) {
				lUpdateErrors.add("accountNumber", new ActionError("variable.empty.error", "Account Number"));
				sForm.setAccountNumber("#" + sForm.getId());
			}
			if (Utility.isSet(sForm.getName1())) {
				sForm.setName1(sForm.getName1().trim());
			}
			if (Utility.isSet(sForm.getName2())){
				sForm.setName2(sForm.getName2().trim());
			}
			if ((sForm.getName1().length() + sForm.getName2().length()) > 80) {
				lUpdateErrors.add("name1", new ActionError("error.simpleGenericError", "The combination of First Name and Last Name should be less than 80 characters"));
			}
			if (Utility.isSet(sForm.getStreetAddr1())) {
				sForm.setStreetAddr1(sForm.getStreetAddr1().trim());
			}
			if (Utility.isSet(sForm.getCity())) {
				sForm.setCity(sForm.getCity().trim());
			}

			// check country and province
			CountryData country = getCountry(session, sForm.getCountry());
			APIAccess factory = new APIAccess();
			Country countryBean = factory.getCountryAPI();
			CountryPropertyData countryProp = countryBean.getCountryPropertyData(country.getCountryId(), RefCodeNames.COUNTRY_PROPERTY.USES_STATE);
			boolean isStateProvinceRequired = countryProp != null && countryProp.getValue().equalsIgnoreCase("true");
			
			if(Utility.isSet(sForm.getStateOrProv())){
				if(isStateProvinceRequired) sForm.setStateOrProv(sForm.getStateOrProv().trim());
				else lUpdateErrors.add("stateOrProv", new ActionError("variable.must.be.empty.error", "State/Province"));
			}

			if (Utility.isSet(sForm.getPostalCode())) {
				sForm.setPostalCode(sForm.getPostalCode().trim());
			}
			if (Utility.isSet(sForm.getPhone())) {
				sForm.setPhone(sForm.getPhone().trim());
			}
			if (Utility.isSet(sForm.getOrderPhone())){
				sForm.setOrderPhone(sForm.getOrderPhone().trim());
			}
			if (Utility.isSet(sForm.getFax())){
				sForm.setFax(sForm.getFax().trim());
			}
			if (Utility.isSet(sForm.getOrderFax())) {
				sForm.setOrderFax(sForm.getOrderFax().trim());
			}
			if (Utility.isSet(sForm.getEmailAddress())) {
				// STJ - 3846
				sForm.setEmailAddress(sForm.getEmailAddress().trim());
			}
			if (sForm.getDefaultEmail() != null) {
				sForm.setDefaultEmail(sForm.getDefaultEmail().trim());
			}
			if (sForm.getCustomerEmail() != null) {
				sForm.setCustomerEmail(sForm.getCustomerEmail().trim());
			}
			if (sForm.getContactUsCCEmail() != null) {
				sForm.setContactUsCCEmail(sForm.getContactUsCCEmail().trim());
			}

			if (sForm.getOrderMinimum() != null && sForm.getOrderMinimum().length() != 0) {
				try {
					BigDecimal minvalue = CurrencyFormat.parse(sForm.getOrderMinimum());
				} catch (ParseException pe) {
					lUpdateErrors.add("orderMinimum", new ActionError("error.invalidAmount", "Order Minimum"));
				}
			}
			if (sForm.getCreditLimit() != null && sForm.getCreditLimit().length() != 0) {
				try {
					BigDecimal minvalue = CurrencyFormat.parse(sForm.getCreditLimit());
				} catch (ParseException pe) {
					lUpdateErrors.add("CreditLimit", new ActionError("error.invalidAmount", "Order Minimum"));
				}
			}
			if (sForm.getBillingAddress1() == null || sForm.getBillingAddress1().trim().length() == 0) {
				lUpdateErrors.add("billingAddress1", new ActionError("variable.empty.error", "Billing Street Address 1"));
			} else {
				sForm.setBillingAddress1(sForm.getBillingAddress1().trim());
			}
			if (sForm.getBillingCity() == null || sForm.getBillingCity().trim().length() == 0) {
				lUpdateErrors.add("billingCity", new ActionError("variable.empty.error", "Billing City"));
			} else {
				sForm.setBillingCity(sForm.getBillingCity().trim());
			}
			if (sForm.getBillingCountry() == null || sForm.getBillingCountry().trim().length() == 0) {
				lUpdateErrors.add("billingCountry", new ActionError("variable.empty.error", "Billing Country"));
			}
			// check Billing State/Province
			country = getCountry(session, sForm.getBillingCountry());
			countryProp = countryBean.getCountryPropertyData(country.getCountryId(), RefCodeNames.COUNTRY_PROPERTY.USES_STATE);
			isStateProvinceRequired = countryProp != null && countryProp.getValue().equalsIgnoreCase("true");

			if (isStateProvinceRequired && !Utility.isSet(sForm.getBillingState().trim())) {
				lUpdateErrors.add("billingState", new ActionError("variable.empty.error", "Billing State/Province"));
			}

			if (!isStateProvinceRequired && Utility.isSet(sForm.getBillingState())) {
				lUpdateErrors.add("billingState", new ActionError("variable.must.be.empty.error", "Billing State/Province"));
			} else {
				sForm.setBillingState(sForm.getBillingState().trim());
			}

			if (sForm.getBillingPostalCode() == null || sForm.getBillingPostalCode().trim().length() == 0) {
				lUpdateErrors.add("billingPostalCode", new ActionError("variable.empty.error", "Billing Zip/Postal Code"));
			} else {
				sForm.setBillingPostalCode(sForm.getBillingPostalCode().trim());
			}
			if (sForm.isAuthorizedForResale() && !Utility.isSet(sForm.getReSaleAccountErpNumber())) {
				noReSaleAccountErpNumber = true;
			}

			try {
				if (Utility.isSet(sForm.getTargetMarginStr())) {
					targetMargin = new BigDecimal(sForm.getTargetMarginStr());
				} else {
					targetMargin = null;
				}
			} catch (Exception e) {
				lUpdateErrors.add("targetMarginStr", new ActionError("error.invalidAmount", "Target Margin"));
			}
		}
		if (sForm.getAutoOrderFactor() == null || sForm.getAutoOrderFactor().trim().length() == 0) {
			lUpdateErrors.add("autoOrderFactor", new ActionError("variable.empty.error", "Auto Order Qty Factor"));
		}
		String autoOrderFactorS = Utility.strNN(sForm.getAutoOrderFactor()).trim();
		double autoOrderFactor = 0;
		if (autoOrderFactorS.length() > 0) {
			try {
				autoOrderFactor = Double.parseDouble(autoOrderFactorS);
			} catch (Exception exc) {
			}
			if (autoOrderFactor <= 0) {
				lUpdateErrors.add("error1", new ActionError("error.simpleGenericError", "Invalid Auto Order Factor: " + autoOrderFactorS));
			}
			if (autoOrderFactor < 0 || autoOrderFactor > 1) {
				lUpdateErrors.add("autoOrderFactorError", new ActionError("error.simpleGenericError",
						"Auto Order Factor must be between 0 and 1. Entered value: " + autoOrderFactorS));
			}
		}

		if (Utility.isSet(sForm.getCartReminderInterval())) {
			try {
				int val = Integer.parseInt(sForm.getCartReminderInterval());
				if (val <= 0) {
					lUpdateErrors.add("cartReminderInterval", new ActionError("error.simpleGenericError", "CartReminderInterval.Value must be greater than 0"));
				}
			} catch (NumberFormatException e) {
				lUpdateErrors.add("cartReminderInterval", new ActionError("error.simpleGenericError", "Wrong Cart Reminder Interval format"));
			}
		}

		if (Utility.isSet(sForm.getInvMissingNotification())) {
			try {
				Integer.parseInt(sForm.getInvMissingNotification());
			} catch (NumberFormatException e) {
				lUpdateErrors.add("invMissingNotification", new ActionError("error.simpleGenericError", "Inv.MissingNotification:Wrong number format"));
			}
		}

		if (Utility.isSet(sForm.getInvCheckPlacedOrder())) {
			try {
				Integer.parseInt(sForm.getInvCheckPlacedOrder());
			} catch (NumberFormatException e) {
				lUpdateErrors.add("doNotPlaceInvOrder", new ActionError("error.simpleGenericError", "DoNotPlaceInvOrder:Wrong number format"));
			}
		}

		// disallow the specification of both an email generator and email
		// template for those email events that
		// have both sets of controls
		if (Utility.isSet(sForm.getConfirmOrderEmailGenerator()) && Utility.isSet(sForm.getOrderConfirmationEmailTemplate())) {
			lUpdateErrors.add("emailSpecification", new ActionError("error.simpleGenericError",
					"You cannot specify both a confirm order email generator and order confirmation email template."));
		}
		if (Utility.isSet(sForm.getRejectOrderEmailGenerator()) && Utility.isSet(sForm.getRejectedOrderEmailTemplate())) {
			lUpdateErrors.add("emailSpecification", new ActionError("error.simpleGenericError",
					"You cannot specify both an order rejected email generator and rejected order email template."));
		}
		if (Utility.isSet(sForm.getPendingApprovEmailGenerator()) && Utility.isSet(sForm.getPendingApprovalEmailTemplate())) {
			lUpdateErrors.add("emailSpecification", new ActionError("error.simpleGenericError",
					"You cannot specify both a pending approval email generator and pending approval email template."));
		}

		// set the email template choices here, in case we are returning the
		// user to the form to
		// fix any mistakes. If the form has a store id use it, otherwise assume
		// the store id is
		// the user's current store.
		String storeId = null;
		if (Utility.isSet(sForm.getStoreId()) && !"0".equalsIgnoreCase(sForm.getStoreId().trim())) {
			storeId = sForm.getStoreId();
		} else {
			storeId = appUser.getUserStore().getStoreId() + "";
		}
		sForm.setEmailTemplateChoices(StoreAccountMgrLogic.getStoreEmailTemplateChoices(storeId));

		// if any email templates are specified, make sure they exist
		TemplateDataVector existingTemplates = TemplateUtilities.getAllEmailTemplatesForStore(new Integer(storeId).intValue());
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
		EmailValidator.validateForm(sForm, request, lUpdateErrors);
		if (lUpdateErrors.size() > 0) {
			// Report the errors to allow for edits.
			return lUpdateErrors;
		}
		APIAccess factory = new APIAccess();
		Account accountBean = factory.getAccountAPI();
		PropertyService propertyEjb = factory.getPropertyServiceAPI();
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
		EmailData customerEmail;
		EmailData defaultEmail;
		String contactUsCCEmail;
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
		if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
			storeid = appUser.getUserStore().getStoreId();
		} else {
			if (!sForm.getStoreId().equals("")) {
				storeid = Integer.parseInt(sForm.getStoreId());
			}
		}
		resetStoreAccountCache(session, accountid, storeid);

		if (accountid > 0) {
			dd = accountBean.getAccount(accountid, storeid);
			sForm.setAccountData(dd);
		} else {
			dd = AccountData.createValue();
		}

		dd.getRuntimeDisplayOrderItemActionTypes().clear();
		for (int i = 0; i < sForm.getRuntimeDisplayOrderItemActionTypes().length; i++) {
			if (Utility.isSet(sForm.getRuntimeDisplayOrderItemActionTypes()[i])) {
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
		customerEmail = dd.getCustomerServiceEmail();
		defaultEmail = dd.getDefaultEmail();
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
		String cuser = stl.getLoginName();

		// Now update with the data from the form.
		dd.setDataFieldProperties(sForm.getDataFieldProperties());
		dd.setAuthorizedForResale(sForm.isAuthorizedForResale());
		dd.setEdiShipToPrefix(sForm.getEdiShipToPrefix());
		dd.setTargetMargin(targetMargin);
		bed.setShortDesc(sForm.getName());
		bed.setLongDesc(sForm.getName());
		bed.setBusEntityStatusCd(sForm.getStatusCd());
		bed.setTimeZoneCd(sForm.getTimeZoneCd());
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

		email.setShortDesc(sForm.getName1().trim() + " " + sForm.getName2().trim());
		email.setEmailAddress(sForm.getEmailAddress());

		customerEmail.setShortDesc(RefCodeNames.EMAIL_TYPE_CD.CUSTOMER_SERVICE);
		customerEmail.setEmailAddress(sForm.getCustomerEmail());

		defaultEmail.setShortDesc(RefCodeNames.EMAIL_TYPE_CD.DEFAULT);
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

		billingAddress.setAddress1(sForm.getBillingAddress1());
		billingAddress.setAddress2(sForm.getBillingAddress2());
		billingAddress.setAddress3(sForm.getBillingAddress3());
		billingAddress.setCity(sForm.getBillingCity());
		billingAddress.setStateProvinceCd(sForm.getBillingState());
		billingAddress.setPostalCode(sForm.getBillingPostalCode());
		billingAddress.setCountryCd(sForm.getBillingCountry());

		String orderManagerEmailS = sForm.getOrderManagerEmails();
		String emailSub = sForm.getInvReminderEmailSub();
		String emailMsg = sForm.getInvReminderEmailMsg();
		String notifyOrderEmailGenerator = sForm.getNotifyOrderEmailGenerator();
		String confirmOrderEmailGenerator = sForm.getConfirmOrderEmailGenerator();
		String rejectOrderEmailGenerator = sForm.getRejectOrderEmailGenerator();
		String pendingApprovEmailGenerator = sForm.getPendingApprovEmailGenerator();

		String orderConfirmationEmailTemplate = sForm.getOrderConfirmationEmailTemplate();
		String shippingNotificationEmailTemplate = sForm.getShippingNotificationEmailTemplate();
		String pendingApprovalEmailTemplate = sForm.getPendingApprovalEmailTemplate();
		String rejectedOrderEmailTemplate = sForm.getRejectedOrderEmailTemplate();
		String modifiedOrderEmailTemplate = sForm.getModifiedOrderEmailTemplate();

		ArrayList orderManagerEmailV = new ArrayList();
		java.util.StringTokenizer st = new StringTokenizer(orderManagerEmailS, ",");
		while (st.hasMoreElements()) {
			String eMail = st.nextToken();
			eMail = eMail.trim();
			if (eMail.indexOf('@') < 0) {
				eMail += "@cleanwise.com";
			}
			orderManagerEmailV.add(eMail);
		}
		// inventory properties
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_LEDGER_SWITCH, sForm.getInvLedgerSwitch() ? Constants.OFF : Constants.ON, cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_PO_SUFFIX, sForm.getInvPOSuffix(), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_OG_LIST_UI, sForm.getInvOGListUI(), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_MISSING_NOTIFICATION, sForm.getInvMissingNotification(), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_CHECK_PLACED_ORDER, sForm.getInvCheckPlacedOrder(), cuser);

		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONNECTION_CUSTOMER, sForm.getConnectionCustomer() ? Constants.ON : Constants.OFF, cuser);

		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CART_REMINDER_INTERVAL, sForm.getCartReminderInterval(), cuser);
		dd.setOrderManagerEmails(orderManagerEmailV);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_SCHED_DELIVERY, String.valueOf(sForm.getShowScheduledDelivery()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_CONSOLIDATION, String.valueOf(sForm.getAllowOrderConsolidation()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_DIST_SKU_NUM, String.valueOf(sForm.getShowDistSkuNum()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_DIST_DELIVERY_DATE, String.valueOf(sForm.getShowDistDeliveryDate()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.RUSH_ORDER_CHARGE, sForm.getRushOrderCharge(), cuser);

		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.AUTO_ORDER_FACTOR, autoOrderFactorS, cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.DIST_INVENTORY_DISPLAY, sForm.getShowDistInventory(), cuser);

		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_SPL, String.valueOf(sForm.getShowSPL()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADD_SERVICE_FEE, String.valueOf(sForm.getAddServiceFee()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.HOLD_PO, String.valueOf(sForm.getHoldPO()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_USER_CHANGE_PASSWORD, String.valueOf(sForm.getAllowChangePassword()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FOLDER, sForm.getAccountFolder(), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FOLDER_NEW, sForm.getAccountFolderNew(), cuser);
		// dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.WORKFLOW_EMAIL,
		// sForm.getWorkflowEmail(), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADJUST_QTY_BY_855, String.valueOf(sForm.isModifyQtyBy855()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CREATE_ORDER_BY_855, String.valueOf(sForm.isCreateOrderBy855()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CREATE_ORDER_ITEMS_BY_855, String.valueOf(sForm.isCreateOrderItemsBy855()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.MODIFY_CUST_PO_NUM_BY_855, String.valueOf(sForm.getModifyCustPoNumBy855()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SUPPORTS_BUDGET, String.valueOf(sForm.getSupportsBudget()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_MODERN_SHOPPING, String.valueOf(sForm.getAllowModernShopping()), cuser);
		// bug # 5029
		if (cwu.getUserStore().isAllowBudgetThreshold()) {
			if (sForm.getAccountData() != null && sForm.getBudgetThresholdType() != null) {
				int accountId = sForm.getAccountData().getAccountId();
				String pStoreid = sForm.getStoreId();
				String accountThresholdType = sForm.getBudgetThresholdType();
				String accountThreshold = RefCodeNames.BUDGET_THRESHOLD_TYPE.ACCOUNT_BUDGET_THRESHOLD;
				String siteThreshold = RefCodeNames.BUDGET_THRESHOLD_TYPE.SITE_BUDGET_THRESHOLD;
				String noneThreshold = RefCodeNames.BUDGET_THRESHOLD_TYPE.NONE;
				String accThresholdType;
				int catalogId;
				int pAccountId;
				int errorCount = 0;
				Account accEjb = APIAccess.getAPIAccess().getAccountAPI();
				CatalogData catalogD = accEjb.getAccountCatalog(accountId);
				if (catalogD != null) {
					catalogId = catalogD.getCatalogId();
					CatalogInformation catalogInfBean = factory.getCatalogInformationAPI();
					IdVector accountIds = catalogInfBean.getBusEntityIdCollection(catalogId, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);

					for (int j = 0; accountIds != null && j < accountIds.size(); j++) {
						pAccountId = ((Integer) accountIds.get(j)).intValue();
						accThresholdType = null;
						try {
							accThresholdType = propertyEjb.getBusEntityProperty(pAccountId, RefCodeNames.PROPERTY_TYPE_CD.BUDGET_THRESHOLD_TYPE);
						} catch (DataNotFoundException e) {
						}
						if (!Utility.isSet(accThresholdType))
							accThresholdType = noneThreshold;
						if (pAccountId != accountId) {
							if (!(accountThresholdType.equals(accThresholdType)
									|| (accountThresholdType.equals(siteThreshold) && accThresholdType.equals(noneThreshold)) || (accountThresholdType
									.equals(noneThreshold) && accThresholdType.equals(siteThreshold)))) {
								errorCount++;
							}
						}

					}
					if (errorCount > 0) {
						String errorMess = "Account has been configured to catalog and budget threshold preferences differ for configured accounts. Unable to Save.";
						lUpdateErrors.add("error", new ActionError("error.simpleGenericError", errorMess));
					}
				}
			}
		}

		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.BUDGET_THRESHOLD_TYPE, sForm.getBudgetThresholdType(), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SITE_LLC, String.valueOf(sForm.getAllowSiteLLC()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_EXPRESS_ORDER, String.valueOf(sForm.getShowExpressOrder()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_INV_ITEMS, String.valueOf(sForm.getAllowOrderInvItems()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_REORDER, String.valueOf(sForm.getAllowReorder()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.USE_PHYSICAL_INVENTORY, String.valueOf(sForm.getUsePhysicalInventory()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_INV_CART_TOTAL, String.valueOf(sForm.getShowInvCartTotal()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_MY_SHOPPING_LISTS, String.valueOf(sForm.getShowMyShoppingLists()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_REBILL_ORDER, String.valueOf(sForm.getShowReBillOrder()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SET_WORKORDER_PO_NUMBER, String.valueOf(sForm.getAllowSetWorkOrderPoNumber()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.USER_ASSIGNED_ASSET_NUMBER, String.valueOf(sForm.getUserAssignedAssetNumber()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_BUY_WORK_ORDER_PARTS, String.valueOf(sForm.getAllowBuyWorkOrderParts()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_INFORMATION_TYPE, sForm.getContactInformationType(), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.WORK_ORDER_PO_NUM_REQUIRED, String.valueOf(sForm.getWorkOrderPoNumberIsRequired()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOP_UI_TYPE, sForm.getShopUIType(), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.FAQ_LINK, sForm.getFaqLink(), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_SUBJECT, emailSub, cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_MSG, emailMsg, cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.NOTIFY_ORDER_EMAIL_GENERATOR, notifyOrderEmailGenerator, cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROV_EMAIL_GENERATOR, pendingApprovEmailGenerator, cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REJECT_ORDER_EMAIL_GENERATOR, rejectOrderEmailGenerator, cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONFIRM_ORDER_EMAIL_GENERATOR, confirmOrderEmailGenerator, cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ORDER_CONFIRMATION_EMAIL_TEMPLATE, orderConfirmationEmailTemplate, cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHIPPING_NOTIFICATION_EMAIL_TEMPLATE, shippingNotificationEmailTemplate, cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROVAL_EMAIL_TEMPLATE, pendingApprovalEmailTemplate, cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REJECTED_ORDER_EMAIL_TEMPLATE, rejectedOrderEmailTemplate, cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.MODIFIED_ORDER_EMAIL_TEMPLATE, modifiedOrderEmailTemplate, cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_CC_ADD, String.valueOf(sForm.getContactUsCCEmail()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PDF_ORDER_CLASS, sForm.getPdfOrderClass(), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PDF_ORDER_STATUS_CLASS, sForm.getPdfOrderStatusClass(), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CC_PAYMENT, String.valueOf(sForm.getAllowCreditCard()), cuser);
		dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ALTERNATE_SHIP_TO_ADDRESS, String.valueOf(sForm.getAllowAlternateShipTo()), cuser);
		String scheduleCutoffDaysS = sForm.getScheduleCutoffDays();
		if (Utility.isSet(scheduleCutoffDaysS)) {
			try {
				int scheduleCutoffDays = Integer.parseInt(scheduleCutoffDaysS);
				if (scheduleCutoffDays < 0) {
					String errorMess = "Field Schedule Cutoff Days. Invalid value ";
					lUpdateErrors.add("CutoffDays", new ActionError("error.simpleGenericError", errorMess));
				}
				dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SCHEDULE_CUTOFF_DAYS, scheduleCutoffDaysS, cuser);
			} catch (Exception exc) {
				String errorMess = "Field Schedule Cutoff Days. Invalid value ";
				lUpdateErrors.add("CutoffDays", new ActionError("error.simpleGenericError", errorMess));
			}
		} else {
			dd.setPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SCHEDULE_CUTOFF_DAYS, "0", cuser);
		}

		String rebatePersentS = sForm.getRebatePersent();
		if (rebatePersentS != null && rebatePersentS.trim().length() > 0) {
			try {
				BigDecimal rebatePersentBD = new BigDecimal(rebatePersentS);
			} catch (Exception exc) {
				String errorMess = "Field Rebate Persent. Invalid value ";
				lUpdateErrors.add("RebatePersent", new ActionError("error.simpleGenericError", errorMess));
			}
			String rebateEffDateS = sForm.getRebateEffDate();
			if (!Utility.isSet(rebateEffDateS)) {
				String errorMess = "Field Rebate Eff Date can't be empty when Rebate Persent is set";
				lUpdateErrors.add("RebatePersent", new ActionError("error.simpleGenericError", errorMess));
			}
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			try {
				Date rebateEffDate = sdf.parse(rebateEffDateS);
				BusEntityParameterData rebatePersentBEPD = BusEntityParameterData.createValue();
				rebatePersentBEPD.setBusEntityId(bed.getBusEntityId());
				rebatePersentBEPD.setName("Rebate Persent");
				rebatePersentBEPD.setValue(rebatePersentS);
				rebatePersentBEPD.setEffDate(rebateEffDate);
				rebatePersentBEPD.setAddBy(cuser);
				rebatePersentBEPD.setModBy(cuser);
				dd.setAccountParameter(rebatePersentBEPD);
			} catch (Exception exc) {
				String errorMess = "Field Rebate Eff Date. Invalid value ";
				lUpdateErrors.add("RebatePersent", new ActionError("error.simpleGenericError", errorMess));
			}
		} else {
			BusEntityParameterData rebatePersentBEPD = dd.getAccountParameterActual("Rebate Persent");
			if (rebatePersentBEPD != null) {
				dd.removeAccountParameter(rebatePersentBEPD.getBusEntityParameterId());
				rebatePersentBEPD = dd.getAccountParameterActual("Rebate Persent");
				rebatePersentS = "";
				String rebateEffDateS = "";
				if (rebatePersentBEPD != null) {
					rebatePersentS = rebatePersentBEPD.getValue();
					if (Utility.isSet(rebatePersentS)) {
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

		if (!sForm.isFirstUpdate() && noReSaleAccountErpNumber) {

			dd.getResaleAccountErpNumber().setValue(dd.getBusEntity().getErpNum());
			sForm.setReSaleAccountErpNumber(dd.getResaleAccountErpNumber().getValue());

		}

		dd.setFreightChargeType(sForm.getFreightChargeType());
		dd.setPurchaseOrderAccountName(sForm.getPurchaseOrderAccountName());
		dd.setBudgetTypeCd(sForm.getBudgetTypeCd());
		dd.setGlTransformationType(sForm.getGlTransformationType());
		dd.setDistributorAccountRefNum(sForm.getDistributorAccountRefNum());
		dd.setDistrPOType(sForm.getDistrPoType());
		if (lUpdateErrors.size() > 0) {
			return lUpdateErrors;
		}

		bed.setModBy(cuser);
		address.setModBy(cuser);
		billingAddress.setModBy(cuser);
		email.setModBy(cuser);
		customerEmail.setModBy(cuser);
		if (customerEmail.getEmailId() == 0)
			customerEmail.setAddBy(cuser);
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
				lUpdateErrors.add("Store.id", new ActionError("account.bad.store"));
				return lUpdateErrors;
			} catch (Exception e) {
				throw e;
			}

			bed.setAddBy(cuser);
			address.setAddBy(cuser);
			billingAddress.setAddBy(cuser);
			email.setAddBy(cuser);
			customerEmail.setAddBy(cuser);
			phone.setAddBy(cuser);
			fax.setAddBy(cuser);
			acctType.setAddBy(cuser);
			reSaleAccountErpNumber.setAddBy(cuser);
			skuTag.setAddBy(cuser);
			orderGuideNote.setAddBy(cuser);

			dd = accountBean.addAccount(dd, storeid);
			sForm.setId(String.valueOf(dd.getBusEntity().getBusEntityId()));
			sForm.setDataFieldProperties(dd.getDataFieldProperties());
			session.setAttribute("STORE_ACCOUNT_DETAIL_FORM", sForm);
			session.setAttribute("Account.id", sForm.getId());
			session.setAttribute("Account.name", sForm.getName());
			session.setAttribute("Store.id", sForm.getStoreId());
			session.setAttribute("Store.name", sForm.getStoreName());

			if (sForm.isFirstUpdate() && sForm.isClone()) {
				try {
					FiscalCalenderViewVector fcDV = cloneFiscalCalendars(accountBean, sForm.getCloneAccoundId(), dd.getBusEntity().getBusEntityId());
					// Setting clone Objects in AccountData
					dd.setFiscalCalenders(fcDV);
					ProductViewDefDataVector pvdDV = cloneProductViewDefs(accountBean, sForm.getCloneAccoundId(), dd.getBusEntity().getBusEntityId(), cuser);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			// Now update this Account
			accountBean.updateAccount(dd);

			sForm.setDataFieldProperties(dd.getDataFieldProperties());
			session.setAttribute("STORE_ACCOUNT_DETAIL_FORM", sForm);
			session.setAttribute("Account.id", sForm.getId());
			session.setAttribute("Account.name", sForm.getName());
			session.setAttribute("Store.id", sForm.getStoreId());
			session.setAttribute("Store.name", sForm.getStoreName());
		}
		if (lUpdateErrors.size() == 0)
			if (sForm.isFirstUpdate()) {
				boolean isUpdate = false;
				// add if no AccountErpNumber
				if (noAccountErpNumber) {
					log.debug("---------------------< No AccountErpNumber >-------------------------------");
					dd.getBusEntity().setErpNum("#" + dd.getBusEntity().getBusEntityId());
					log.debug("StoreAccountMgrLogic.updateAccount informs :Added AccountErpNumber.AccountErpNumber = " + dd.getBusEntity().getErpNum());
					isUpdate = true;

				}
				// add if no ReSaleAccountErpNumber
				if (noReSaleAccountErpNumber) {

					log.debug("----------------------------< No ResaleAccountErpNumber >--------------------");
					dd.getResaleAccountErpNumber().setValue(dd.getBusEntity().getErpNum());
					log.debug("StoreAccountMgrLogic.updateAccount informs : Added  ResaleAccountErpNumber." + " ResaleAccountErpNumber = "
							+ dd.getResaleAccountErpNumber().getValue() + " property  id : " + dd.getResaleAccountErpNumber().getPropertyId()
							+ " propertydata :" + dd.getResaleAccountErpNumber());
					isUpdate = true;
				} // add if no EdiShipToPrefix
				if ((dd.getEdiShipToPrefix() == null || dd.getEdiShipToPrefix().trim().length() == 0)) {

					log.debug("---------------------< No EdiShipToPrefix >----------------------------------");
					dd.setEdiShipToPrefix(String.valueOf(dd.getBusEntity().getBusEntityId()));
					log.debug("StoreAccountMgrLogic.updateAccount informs : Added  EdiShipToPrefix." + "EdiShipToPrefix = " + dd.getEdiShipToPrefix());
					isUpdate = true;
				}
				sForm.setFirstUpdate(false);
				if (isUpdate) {
					log.debug("-----------------------< UPDATE ACCOUNT >-------------------------------------");
					accountBean.updateAccount(dd);
				}
				if (sForm.isClone())
					sForm.setClone(false);

				log.debug("Ok!");
				log.debug("-----------------------< GET DETAIL  ACCOUNT >---------------------------");
				getDetail(request, sForm);

			}

		return lUpdateErrors;

	}

  private static void recoveryForm(HttpServletRequest request, StoreAccountMgrDetailForm sForm) throws Exception {

	  log.info("recoveryForm");
    if (!sForm.getId().trim().equals("") && sForm.getIntId() > 0) {
      if (new Integer(sForm.getId()).intValue() == sForm.getIntId()) {
        getDetail(request, sForm);
        log.debug("AccountData has been restored for id->" + sForm.getId());
      }
    } else {
      if (sForm.getCloneAccoundId() > 0) {
        sForm.setId(sForm.getCloneAccoundId());
        sForm.setId(String.valueOf(sForm.getCloneAccoundId()));
        getDetail(request, sForm);
        cloneAccountData(request, sForm);
        log.debug("CloneAccountData has been restored for id ->" + sForm.getId());
      } else {
        addAccount(request, sForm);
        log.debug("New AccountData");

      }

    }
  }

    private static FiscalCalenderViewVector cloneFiscalCalendars(Account accountBean,
                                                                 int cloneAccoundId,
                                                                 int newAccountId) throws RemoteException {

        log.info("cloneFiscalCalendars => BEGIN");
        log.info("cloneFiscalCalendars => Clone Fiscal Calendars And Update");

        FiscalCalenderViewVector fcCollection = accountBean.getFiscalCalCollection(cloneAccoundId);
        FiscalCalenderViewVector cloneFCCollection = new FiscalCalenderViewVector();
        Iterator it = fcCollection.iterator();
        int i = 0;
        while (it.hasNext()) {
            log.info("cloneFiscalCalendars => Set new accountId ");
            FiscalCalenderView fcv = ((FiscalCalenderView) it.next());

            FiscalCalenderView newFiscalCal = FiscalCalenderView.createValue();
            newFiscalCal.setFiscalCalender((FiscalCalenderData) fcv.getFiscalCalender().clone());
            newFiscalCal.getFiscalCalender().setBusEntityId(newAccountId);
            newFiscalCal.setFiscalCalenderDetails(new FiscalCalenderDetailDataVector());
            for (Object oDet : fcv.getFiscalCalenderDetails()) {
                newFiscalCal.getFiscalCalenderDetails().add(((FiscalCalenderDetailData) oDet).clone());
            }
            log.info("cloneFiscalCalendars => Uppdate-" + (i++));
            cloneFCCollection.add(accountBean.updateFiscalCal(newFiscalCal));
        }

        log.info("cloneFiscalCalendars => ok!");
        log.info("cloneFiscalCalendars => Result : " + cloneFCCollection);
        log.info("cloneFiscalCalendars => END.");

        return cloneFCCollection;

    }
    
    private static ProductViewDefDataVector cloneProductViewDefs(Account accountBean,
    		int cloneAccoundId,	int newAccountId, String userName) throws RemoteException {

    	log.info("cloneProductViewDefs => BEGIN");
    	log.info("cloneProductViewDefs => Clone Product View Defs And Update");
    	ProductViewDefDataVector pvdDV = accountBean.getProductViewDefData(cloneAccoundId);
    	if (pvdDV.size() > 0){
	    	Iterator it = pvdDV.iterator();
	    	int i = 0;
	    	while (it.hasNext()) {
	    		log.info("cloneProductViewDefs => Set new accountId ");
	    		ProductViewDefData pvdD = ((ProductViewDefData) it.next());
	    		pvdD.setAccountId(newAccountId);
	    		pvdD.setProductViewDefId(0);
	    	}
	    	accountBean.updateProductViewDefData(newAccountId, pvdDV, userName);	    	
    	}
    	log.info("cloneProductViewDefs => END.");
    	return pvdDV;
    }

  /**
   *Returns the store data for the account that is currently being managed.
   *For non system_administrator users this is simply the store that they are currently
   *managed.  For system_administrator this is the store that the account is tied
   *to.  This information is cached in the session.
   *@param pSession the user who is making the request session
   *@param pAccountId the account id that we want the store for
   */
  public static StoreData getStoreForManagedAccount(HttpSession pSession, int pAccountId) throws APIServiceAccessException,
    RemoteException {
	  log.info("getStoreForManagedAccount");
    CleanwiseUser appUser = ShopTool.getCurrentUser(pSession);
    if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
      return appUser.getUserStore();
    }
    StoreData theStore = null;
    Integer key = new Integer(pAccountId);
    Map storeMap = (Map) pSession.getAttribute(ACCOUNT_STORE_MAP_CACHE);
    if (storeMap == null) {
      storeMap = new HashMap();
      pSession.setAttribute(ACCOUNT_STORE_MAP_CACHE, storeMap);
    } else {
      theStore = (StoreData) storeMap.get(key);
    }
    if (theStore == null) {
      APIAccess factory = (APIAccess) pSession.getAttribute(Constants.APIACCESS);
      BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
      IdVector idv = new IdVector();
      idv.add(new Integer(pAccountId));
      crit.setAccountBusEntityIds(idv);
      StoreDataVector stores = factory.getStoreAPI().getStoresByCriteria(crit);
      int size = stores.size();
      if (size == 0) {
        theStore = null;
      } else if (size == 1) {
        theStore = (StoreData) stores.get(0);
      } else {
        throw new RuntimeException("Multiple stores for account id: " + pAccountId);
      }
      storeMap.put(key, theStore);
    }
    return theStore;
  }

  /**
   *updates the caching between stores and accounts
   */
  private static void resetStoreAccountCache(HttpSession pSession, int pAccountId, int pStoreId) {
	  log.info("resetStoreAccountCache");
    if (pAccountId == 0) {
      return;
    }
    Map storeMap = (HashMap) pSession.getAttribute(ACCOUNT_STORE_MAP_CACHE);
    if (storeMap != null) {
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
                            ActionForm form) throws Exception {

	  log.info("getAll");
    // Get a reference to the admin facade
    HttpSession session = request.getSession();

    APIAccess factory = new APIAccess();
    Account accountBean = factory.getAccountAPI();
    AccountDataVector dv;
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
      BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
      crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
      crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
      crit.setResultLimit(Constants.MAX_ACCOUNTS_TO_RETURN);
      dv = accountBean.getAccountsByCriteria(crit);
    } else {
      dv = accountBean.getAllAccounts(0, Account.ORDER_BY_NAME);
    }

    session.setAttribute("Account.found.vector", dv);
    session.setAttribute("Account.found.total",
                         String.valueOf(dv.size()));
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
	  log.info("init");
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
      if (null == session.getAttribute("country.vector")) {
          Country countryBean = factory.getCountryAPI();
          CountryDataVector countriesv = countryBean.getAllCountries();
          session.setAttribute("country.vector", countriesv);
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

      if (session.getAttribute("INVENTORY_OG_LIST_UI") == null) {
          RefCdDataVector typesv =
                  lsvc.getRefCodesCollection("INVENTORY_OG_LIST_UI");
          session.setAttribute("INVENTORY_OG_LIST_UI", typesv);
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

      if (session.getAttribute("GL_TRANSFORMATION_TYPE") == null) {
          RefCdDataVector typesv =
                  lsvc.getRefCodesCollection("GL_TRANSFORMATION_TYPE");
          session.setAttribute("GL_TRANSFORMATION_TYPE", typesv);
      }
      if (session.getAttribute("TimeZone.vector") == null) {
          RefCdDataVector timeZoneV =
                  lsvc.getRefCodesCollection("TIME_ZONE_CD");
          session.setAttribute("TimeZone.vector", timeZoneV);
      }


      CleanwiseUser appUser = (CleanwiseUser)
              session.getAttribute(Constants.APP_USER);

      // Remove the previous data cache.
      session.removeAttribute("Workflow.found.vector");
      session.removeAttribute("FreightHandlers.found.vector");

      GroupSearchCriteriaView grSearchCrVw =
              GroupSearchCriteriaView.createValue();
      grSearchCrVw.setGroupType(RefCodeNames.GROUP_TYPE_CD.ACCOUNT);
      grSearchCrVw.setGroupName("");

      grSearchCrVw.setStoreId(appUser.getUserStore().getStoreId());
      GroupDataVector groups =
              groupEjb.getGroups(grSearchCrVw, Group.NAME_BEGINS_WITH
                      , Group.ORDER_BY_NAME);
      if (form instanceof StoreAccountMgrSearchForm) {
          StoreAccountMgrSearchForm pForm = (StoreAccountMgrSearchForm) form;
          pForm.setAccountGroups(groups);
          // Reset the session variables.
          int curr_store = pForm.getCurrentStore();
          if (curr_store != appUser.getUserStore().getStoreId()) {
              if (session.getAttribute("Account.found.vector") != null) {
                  AccountDataVector dv = new AccountDataVector();
                  session.setAttribute("Account.found.vector", dv);
              }
              pForm.setCurrentStore(appUser.getUserStore().getStoreId());
          }
      } else if (form instanceof StoreAccountMgrForm) {
          StoreAccountMgrForm pForm = (StoreAccountMgrForm) form;
          pForm.setAccountGroups(groups);
          // Reset the session variables.
          int curr_store = pForm.getCurrentStore();
          if (curr_store != appUser.getUserStore().getStoreId()) {
              if (session.getAttribute("Account.found.vector") != null) {
                  AccountDataVector dv = new AccountDataVector();
                  session.setAttribute("Account.found.vector", dv);
              }
              pForm.setCurrentStore(appUser.getUserStore().getStoreId());
          }
      } else if(form instanceof StoreAccountMgrDetailForm){
    	  checkForCurrentFiscalYearBudgets(form,appUser);
      }

      return;
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
	  log.info("search");

    HttpSession session = request.getSession();
    StoreAccountMgrSearchForm sForm = (StoreAccountMgrSearchForm) form;
    APIAccess factory = new APIAccess();
    Account accountBean = factory.getAccountAPI();

    // Reset the session variables.
    AccountDataVector dv = new AccountDataVector();
    session.setAttribute("Account.found.vector", dv);
    session.setAttribute("Account.found.total", "0");

    String fieldValue = sForm.getSearchField();
    String fieldSearchType = sForm.getSearchType();
    String searchGroupId = sForm.getSearchGroupId();
    dv = search(request, fieldValue, fieldSearchType, searchGroupId, true);

    session.setAttribute("Account.found.vector", dv);
    session.setAttribute("Account.found.total",
                         String.valueOf(dv.size()));

  }

  public static ActionErrors accountSearch(HttpServletRequest request,
		  								   ActionForm form) throws Exception{
	ActionErrors ae = new ActionErrors();
	HttpSession session = request.getSession();
	APIAccess factory = new APIAccess();
	Account accountBean = factory.getAccountAPI();
	AccountSearchResultViewVector acntSrchRsltVctr = null;

	StoreAccountMgrForm sForm = (StoreAccountMgrForm) form;
	String fieldValue = sForm.getSearchField();
	String fieldSearchType = sForm.getSearchType();
	String searchGroupId = sForm.getSearchGroupId();
	String refNumValue = sForm.getSearchRefNum();
	String refNumSearchType = sForm.getRefNumSearchType();

	boolean showInactive = sForm.getShowInactiveFl();
	int id =0;
	CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	String storeStr = null;
    if (appUser.isaSystemAdmin()) {
        IdVector stores = appUser.getUserStoreAsIdVector();
        storeStr = stores.toCommaString(stores);
    } else {
        IdVector stores = appUser.getUserStoreAsIdVector();
        storeStr = stores.toCommaString(stores);
    }

	try{
		acntSrchRsltVctr = accountBean.search(storeStr,
		        fieldValue, fieldSearchType,
		        refNumValue, refNumSearchType,
		        searchGroupId, showInactive);
	}catch(Exception e){
		log.debug("Error Message :" + e.getMessage());
		ActionErrors clwError = StringUtils.getUiErrorMess(e);
		return clwError;
	}
	session.setAttribute("Account.search.result", acntSrchRsltVctr);
	return ae;
  }

  public static ActionErrors storeSearch(HttpServletRequest request,
                                         ActionForm form) throws NamingException, APIServiceAccessException {
	  log.info("storeSearch");
    ActionErrors ae = new ActionErrors();
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
    try {
      dv = search(request, fieldValue, fieldSearchType,
                  searchGroupId,
                  sForm.getShowInactiveFl());
    } catch (Exception e) {
      try {
        log.debug("Error Message :" + e.getMessage());
        ActionErrors clwError = StringUtils.getUiErrorMess(e);
        return clwError;
      } catch (Exception e1) {
        ae.add(ActionErrors.GLOBAL_ERROR,
               new ActionError("error.simpleGenericError", "Problem getting the account for storeportal:" +
                               " Error: " + e));

      }

    }

    session.setAttribute("Account.found.vector", dv);
    session.setAttribute("Account.found.total",
                         String.valueOf(dv.size()));

    return ae;
  }

  static public AccountDataVector search(HttpServletRequest request,
                                         String fieldValue,
                                         String fieldSearchType,
                                         String searchGroupId) throws Exception {
    return search(request, fieldValue, fieldSearchType,
                  searchGroupId, true);
  }

  /**
   *Returns an account data vector based off the specified search criteria
   */
  static public AccountDataVector search(HttpServletRequest request,
                                         String fieldValue,
                                         String fieldSearchType,
                                         String searchGroupId,
                                         boolean pShowInactiveFlag) throws Exception {
	  log.info("search");

    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    Account acctBean = factory.getAccountAPI();

    BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
    crit.setResultLimit(Constants.MAX_ACCOUNTS_TO_RETURN);

    boolean emptyCritFl = true;
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

    if (!appUser.isaSystemAdmin()) {
      emptyCritFl = false;
      crit.setStoreBusEntityIds(appUser.getUserStoreAsIdVector());
    }

    if (fieldValue != null && fieldValue.trim().length() > 0) {
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

    if (searchGroupId != null && searchGroupId.trim().length() > 0 &&
        Integer.parseInt(searchGroupId) > 0) {
      emptyCritFl = false;
      crit.setSearchGroupId(searchGroupId.trim());
    }

    crit.setSearchForInactive(pShowInactiveFlag);

    AccountDataVector dv;
    crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
    dv = acctBean.getAccountsByCriteria(crit);
    if (dv != null) {
      log.debug("found " + dv.size() + " accounts");
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
                          ActionForm form) throws Exception {
	  log.info("sort");
    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    String sortField = request.getParameter("sortField");
    log.debug("sortField=" + sortField);
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
      if (accounts == null) {
        return;
      }
      DisplayListSort.sort(accounts, sortField);
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
                                ActionForm form) throws Exception {
	  log.info("addAccount");
    HttpSession session = request.getSession();
    StoreAccountMgrDetailForm sForm = new StoreAccountMgrDetailForm();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if (appUser != null && !RefCodeNames.STORE_TYPE_CD.MLA.equals(appUser.getUserStore().getStoreType().getValue())) {
      sForm.setRuntimeDisplayOrderItemActionTypes(new String[] {
                                                  RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.SYSTEM_ACCEPTED,
                                                  RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_SHIPPED,
                                                  RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_INVOICED,
                                                  RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CANCELED
      });
    } else {
      sForm.setRuntimeDisplayOrderItemActionTypes(new String[] {
                                                  RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.SYSTEM_ACCEPTED,
                                                  RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CUST_SHIPPED,
                                                  RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CUST_INVOICED,
                                                  RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CANCELED
      });
    }
    sForm.setFirstUpdate(true);
    session.setAttribute("Account.id", "0");
    session.setAttribute("Account.name", "-");
    session.setAttribute("Store.id", "0");
    session.setAttribute("Store.name", "--");
    session.setAttribute("STORE_ACCOUNT_DETAIL_FORM", sForm);
    if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
      String country = appUser.getUserStore().getPrimaryAddress().getCountryCd();
      sForm.setCountry(country);
      sForm.setBillingCountry(country);
    }
    //If the form has a valid store id use it, otherwise assume the store id is the user's
    //current store.
    String storeId = null;
    if (Utility.isSet(sForm.getStoreId()) && !"0".equalsIgnoreCase(sForm.getStoreId().trim())) {
  	  storeId = sForm.getStoreId();
    }
    else {
  	  storeId = appUser.getUserStore().getStoreId() + "";
    }
    sForm.setEmailTemplateChoices(StoreAccountMgrLogic.getStoreEmailTemplateChoices(storeId));
  }


  public static ActionErrors cloneAccountData(HttpServletRequest request,
                                              ActionForm form) throws Exception {
	  log.info("cloneAccountData");
    StoreAccountMgrDetailForm sForm = (StoreAccountMgrDetailForm) form;
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    ActionErrors ae = new ActionErrors();
    AccountData ad = sForm.getAccountData();
    if (ad != null) {
      BusEntityData bed = ad.getBusEntity();
      PropertyData acctType = ad.getAccountType();
      PropertyData orderMinimum = ad.getOrderMinimum();
      PropertyData creditLimit = ad.getCreditLimit();
      PropertyData creditRating = ad.getCreditRating();
      AddressData address = ad.getPrimaryAddress();
      PhoneData phone = ad.getPrimaryPhone();
      PhoneData fax = ad.getPrimaryFax();
      PhoneData poConfirmFax = ad.getFaxBackConfirm();
      EmailData email = ad.getPrimaryEmail();
      EmailData customerEmail = ad.getCustomerServiceEmail();
      EmailData defaultEmail = ad.getDefaultEmail();
      AddressData billingAddress = ad.getBillingAddress();
      PhoneData orderPhone = ad.getOrderPhone();
      PhoneData orderFax = ad.getOrderFax();
      PropertyData comments = ad.getComments();
      PropertyData orderGuideNote = ad.getOrderGuideNote();
      PropertyData skuTag = ad.getSkuTag();
      PropertyData crcShop = ad.getCrcShop();
      BusEntityParameterData rebatePersentBEPD =
        ad.getAccountParameterActual("Rebate Persent");
      int cloneAccountId = ad.getAccountId();
      String cloneStatusCd = bed.getBusEntityStatusCd();
      String cloneTimeZoneCd = bed.getTimeZoneCd();
      String cloneTypeCd = bed.getBusEntityTypeCd();
      FiscalCalenderViewVector cloneFiscalCalendar = ad.getFiscalCalenders();
      String cloneName = "Clone of >>> " + bed.getShortDesc();
      String cloneAccountType = acctType.getValue(); //
      //String cloneAccountNumber=bed.getErpNum();
      String cloneOrderMinimum = orderMinimum.getValue();
      BigDecimal minvalue;
      try {
        minvalue = CurrencyFormat.parse(cloneOrderMinimum);

      } catch (ParseException pe) {
        // this should only happen if bad value in db
        minvalue = new BigDecimal(0);
      }
      cloneOrderMinimum = CurrencyFormat.format(minvalue);
      String cloneCreditLimit = creditLimit.getValue();
      BigDecimal creditvalue;
      try {
        creditvalue = CurrencyFormat.parse(cloneCreditLimit);
      } catch (ParseException pe) {
        // this should only happen if bad value in db
        creditvalue = new BigDecimal(0);
      }
      cloneCreditLimit = CurrencyFormat.format(creditvalue);
      ///////////////////////////////////////////////////////
      String cloneCreditRating = creditRating.getValue();
      //////////////////////////////////////////////////////
      String cloneBudgetTypeCd = ad.getBudgetTypeCd();
      String cloneDistrPOType = ad.getDistrPOType();
      //////////////////////////////////////////////////////
      List cloneDataFieldProperties = ad.getDataFieldProperties();
      //////////////////////////////////////////////////////
      String cloneWeightThreshold = null;
      String cloneContractThreshold = null;
      String clonePriceThreshold = null;
      if (!ClwCustomizer.getClwSwitch()) {
        cloneWeightThreshold = sForm.getWeightThreshold();
        cloneContractThreshold = sForm.getContractThreshold();
        clonePriceThreshold = sForm.getPriceThreshold();
      }
      ///////////////////Primary Contact/////////////////////

      String cloneName1 = address.getName1();
      String cloneName2 = address.getName2();
      String clonePhone = phone.getPhoneNum();
      String cloneFax = fax.getPhoneNum();
      String cloneConfirmFax = poConfirmFax.getPhoneNum();
      String cloneStreetAddress1 = address.getAddress1();
      String cloneStreetAddress2 = address.getAddress2();
      String cloneStreetAddress3 = address.getAddress3();
      String cloneEmailAddress = email.getEmailAddress();
      String cloneCustomerEmail = customerEmail.getEmailAddress();
      String cloneDefaultEmail = defaultEmail.getEmailAddress();
      String cloneCountry = address.getCountryCd();
      String cloneStateOrProvince = address.getStateProvinceCd();
      String cloneCity = address.getCity();
      String clonePostalCode = address.getPostalCode();

      //////////////////////Billing Address//////////////////////

      String clonebilStreetAddress1 = billingAddress.getAddress1();
      String clonebilCountry = billingAddress.getCountryCd();
      String clonebilStreetAddress2 = billingAddress.getAddress2();
      String clonebilStateOrProvince = billingAddress.getStateProvinceCd();
      String clonebilBillingAddress3 = billingAddress.getAddress3();
      String clonebilZipOrPostalCode = billingAddress.getPostalCode();
      String clonebilCity = billingAddress.getCity();
      String clonePurchaseOrderAccountName = ad.getPurchaseOrderAccountName();

      ////////////////Order Contact Information////////////////////////
      String cloneOrderPhone = orderPhone.getPhoneNum();
      String cloneOrderFax = orderFax.getPhoneNum();
      String cloneOrderGuideComments = comments.getValue();
      String cloneOrderGuideNote = "";
      if (null != orderGuideNote)
        cloneOrderGuideNote = orderGuideNote.getValue();
      //////////////////////////////////////////////////////////////////
      String cloneSkuTag = "";
      if (skuTag != null)
        cloneSkuTag = skuTag.getValue();
      /////////////////////////////////////////////////////////////////////
      String[] cloneRuntimeDisplayOrderItemActionTypes =
        (String[]) ad.getRuntimeDisplayOrderItemActionTypes().toArray(new String[0]);
      ////////////////////////////////////////////////////////////////////

      boolean cloneTaxableIndicator = ad.isTaxableIndicator();
      ////////////////////////////////////////////////////////////////////
      boolean cloneCrcShop = false;
      String crcValue = crcShop.getValue();
      if (crcValue != null && crcValue.length() > 0
          && "T".equalsIgnoreCase(crcShop.getValue().substring(0, 1))) {
        cloneCrcShop = true;
      }
      ////////////////////////////////////////////////////////////////////
      String cloneFreightChargeType = "";
      if (!Utility.isSet(ad.getFreightChargeType())) {
        cloneFreightChargeType = "PC";
      } else {
        cloneFreightChargeType = ad.getFreightChargeType();
      }
      ////////////////////////////////////////////////////////////////////
      boolean cloneMakeShipToBillTo = ad.isMakeShipToBillTo();
      ////////////////////////////////////////////////////////////////////
      String cloneContactUsCCEmail = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_CC_ADD);
      String cloneOrderManagerEmailS = "";
      String cloneEmailSub = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_SUBJECT);
      String cloneEmailMsg = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REMINDER_EMAIL_MSG);
      String cloneNotifyOrderEmailGenerator = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.NOTIFY_ORDER_EMAIL_GENERATOR);
      String clonePendingApprovEmailGenerator = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROV_EMAIL_GENERATOR);
      String cloneRejectOrderEmailGenerator = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REJECT_ORDER_EMAIL_GENERATOR);
      String cloneConfirmOrderEmailGenerator = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONFIRM_ORDER_EMAIL_GENERATOR);
      String cloneOrderConfirmationEmailTemplate = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ORDER_CONFIRMATION_EMAIL_TEMPLATE);
      String cloneShippingNotificationEmailTemplate = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHIPPING_NOTIFICATION_EMAIL_TEMPLATE);
      String clonePendingApprovalEmailTemplate = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PENDING_APPROVAL_EMAIL_TEMPLATE);
      String cloneRejectedOrderEmailTemplate = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.REJECTED_ORDER_EMAIL_TEMPLATE);
      String cloneModifiedOrderEmailTemplate = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.MODIFIED_ORDER_EMAIL_TEMPLATE);

      ArrayList orderManagerEmailV = ad.getOrderManagerEmails();
      String orderManagerEmailS = "";
      if (orderManagerEmailV != null) {
        for (int ii = 0; ii < orderManagerEmailV.size(); ii++) {
          String eMail = (String) orderManagerEmailV.get(ii);
          if (eMail != null) {
            eMail = eMail.trim();
            if (eMail.length() > 0) {
              if (ii > 0) orderManagerEmailS += ",";
              orderManagerEmailS += eMail;
            }
          }
        }
      }
      cloneOrderManagerEmailS = orderManagerEmailS;
      boolean cloneShowSchedDel = false;
      String showSchedDelS = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_SCHED_DELIVERY);
      if (showSchedDelS != null && showSchedDelS.length() > 0 &&
          "T".equalsIgnoreCase(showSchedDelS.substring(0, 1))) {
        cloneShowSchedDel = true;
      }
      /*String cloneEdiShipToPrefix = ad.getEdiShipToPrefix();
               if(cloneEdiShipToPrefix==null || cloneEdiShipToPrefix.trim().length()==0) {
          cloneEdiShipToPrefix = "";
               }*/
      boolean cloneConnectionCustomer = Utility.isOn(ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONNECTION_CUSTOMER),false);

      boolean cloneLedgerSwitch = Utility.isOff(ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_LEDGER_SWITCH),false);
      String clonePoSuffix=Utility.strNN(ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_PO_SUFFIX));
      String cloneInvOGListUI=Utility.strNN(ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_OG_LIST_UI));
      String cloneInvMissingNotify=Utility.strNN(ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_MISSING_NOTIFICATION));
      String cloneCheckPlacedOrder=Utility.strNN(ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_CHECK_PLACED_ORDER));
      String cloneShopUIType=Utility.strNN(ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOP_UI_TYPE));
      String clonePdfOrderClass=Utility.strNN(ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PDF_ORDER_CLASS));
      String clonePdfOrderStatusClass=Utility.strNN(ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.PDF_ORDER_STATUS_CLASS));

      boolean cloneCustomerRequestPoAllowed = ad.isCustomerRequestPoAllowed();
      String cloneRushOrderCharge = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.RUSH_ORDER_CHARGE);
      String cloneAutoOrderFactor = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.AUTO_ORDER_FACTOR);
      String cloneCartReminderInt = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CART_REMINDER_INTERVAL);
      String cloneShowDistInventory = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.DIST_INVENTORY_DISPLAY);
      boolean cloneAuthorizedForResale = ad.isAuthorizedForResale();
      //String cloneReSaleAccountErpNumber = ad.getResaleAccountErpNumber().getValue();
      boolean cloneAllowOrderConsolidationt = false;
      String allowOrderConsolidationS =
        ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_CONSOLIDATION);
      if (allowOrderConsolidationS != null && allowOrderConsolidationS.length() > 0 &&
          "T".equalsIgnoreCase(allowOrderConsolidationS.substring(0, 1))) {
        cloneAllowOrderConsolidationt = true;
      }
      ///////////////////////////////////////////////////////////////////
      String cloneScheduleCutoffDays = "";
      String scheduleCutoffDaysS =
        ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SCHEDULE_CUTOFF_DAYS);
      if (scheduleCutoffDaysS == null) scheduleCutoffDaysS = "";
      cloneScheduleCutoffDays = scheduleCutoffDaysS;
      /////////////////////////////////////////////////////////////////////
      boolean cloneShowDistSkuNum = false;
      String showDistSkuNumS =
        ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_DIST_SKU_NUM);
      if (showDistSkuNumS != null && showDistSkuNumS.length() > 0 &&
          "T".equalsIgnoreCase(showDistSkuNumS.substring(0, 1))) {
        cloneShowDistSkuNum = true;
      }

      String showDistDeliveryDateS =
        ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_DIST_DELIVERY_DATE);
      boolean cloneShowDistDeliveryDate =
        Utility.isTrue(showDistDeliveryDateS, false);

      ///////////////////////////////////////////////////////////////////////
      String cloneRebatePersentS = "";
      String cloneRebateEffDateS = "";
      if (rebatePersentBEPD != null) {
        cloneRebatePersentS = rebatePersentBEPD.getValue();
        if (Utility.isSet(cloneRebatePersentS)) {
          Date rebateEffDate = rebatePersentBEPD.getEffDate();
          SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
          cloneRebateEffDateS = sdf.format(rebateEffDate);
        }
      }
      //////////////////////////////////////////////////////////
      String cloneTargetMarginStr = "0";
      if (ad.getTargetMargin() != null) {
        cloneTargetMarginStr = ad.getTargetMargin().toString();
      }
      ///////////////////////////////////////////////////////////
      String cloneCustomerSystemApprovalCd = ad.getCustomerSystemApprovalCd();

      boolean cloneShowSPL = false;
      String showSPLS = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_SPL);
      if (showSPLS != null && showSPLS.length() > 0 &&
          "T".equalsIgnoreCase(showSPLS.substring(0, 1))) {
        cloneShowSPL = true;
      }

      boolean cloneAddServiceFee = false;
      String addServiceFeeS = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADD_SERVICE_FEE);
      if (addServiceFeeS != null && addServiceFeeS.length() > 0 &&
          "T".equalsIgnoreCase(addServiceFeeS.substring(0, 1))) {
    	  cloneAddServiceFee = true;
      }

      boolean cloneHoldPO = false;
      String holdPOS = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.HOLD_PO);
      cloneHoldPO = Utility.isTrue(holdPOS,false);

      boolean cloneAllowChangePassword = false;
      String allowChangePasswordS =
          ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_USER_CHANGE_PASSWORD);
      cloneAllowChangePassword = Utility.isTrue(allowChangePasswordS,false);

      String cloneAccountFolder = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FOLDER);
      String cloneAccountFolderNew = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FOLDER_NEW);
      //String cloneWorkflowEmail = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.WORKFLOW_EMAIL);
      String cloneModifyQtyBy855 = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADJUST_QTY_BY_855);
      String cloneCreateOrderItemsBy855 = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CREATE_ORDER_ITEMS_BY_855);
      String cloneCreateOrderBy855 = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CREATE_ORDER_BY_855);
      String cloneAllowModernShopping = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_MODERN_SHOPPING);
      String cloneBudgetThresholdType = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.BUDGET_THRESHOLD_TYPE);
      String cloneAllowSiteLLC = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SITE_LLC);
      String cloneShowExpressOrder = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_EXPRESS_ORDER);
      String cloneAllowOrderInvItems = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_INV_ITEMS);
      String cloneAllowReorder = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_REORDER);
      String cloneUsePhysicalInventory = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.USE_PHYSICAL_INVENTORY);
      String cloneShowMyShoppingLists = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_MY_SHOPPING_LISTS);
      String cloneShowReBillOrder = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_REBILL_ORDER);
      String cloneShowInvCartTotal = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_INV_CART_TOTAL);
      String cloneAllowSetWorkOrderPoNumber = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_SET_WORKORDER_PO_NUMBER);
      String cloneWorkOrderPoNumberIsRequired = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.WORK_ORDER_PO_NUM_REQUIRED);
      String cloneAllowBuyWorkOrderParts = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_BUY_WORK_ORDER_PARTS);
      String cloneUserAssignedAssetNumber = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.USER_ASSIGNED_ASSET_NUMBER);
      String cloneContactInformationType = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_INFORMATION_TYPE);
      String cloneFaqLink = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.FAQ_LINK);
      String cloneAllowAlternateShipTo = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ALTERNATE_SHIP_TO_ADDRESS);
      String cloneModifyCustPoNumBy855 = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.MODIFY_CUST_PO_NUM_BY_855);
      String cloneSupportsBudget = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SUPPORTS_BUDGET);
      boolean cloneAllowCC = false;
      String allowCCS = ad.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CC_PAYMENT);
      if (allowCCS != null && allowCCS.length() > 0 &&
          "T".equalsIgnoreCase(allowCCS.substring(0, 1))) {
        cloneAllowCC = true;
      }
      ////////////////////////////////////////////////////////////
      ///////////////////////SETTING Clone data in form //////////
      ////////////////////////////////////////////////////////////

      sForm = new StoreAccountMgrDetailForm();

      sForm.setStoreId(String.valueOf(appUser.getUserStore().getStoreId()));
      sForm.setCloneAccoundId(cloneAccountId);
      sForm.setStatusCd(cloneStatusCd);
      sForm.setTimeZoneCd(cloneTimeZoneCd);
      sForm.setTypeCd(cloneTypeCd);
      sForm.setTypeDesc(cloneAccountType);
      sForm.setName(cloneName);
      //sForm.setAccountNumber(cloneAccountNumber);
      sForm.setOrderMinimum(cloneOrderMinimum);
      sForm.setCreditLimit(cloneCreditLimit);
      sForm.setCreditRating(cloneCreditRating);
      sForm.setBudgetTypeCd(cloneBudgetTypeCd);
      sForm.setDistrPoType(cloneDistrPOType);
      sForm.setDataFieldProperties(cloneDataFieldProperties);
      sForm.setWeightThreshold(cloneWeightThreshold);
      sForm.setContractThreshold(cloneContractThreshold);
      sForm.setPriceThreshold(clonePriceThreshold);
      sForm.setName1(cloneName1);
      sForm.setName2(cloneName2);
      sForm.setPhone(clonePhone);
      sForm.setFax(cloneFax);
      sForm.setFaxBackConfirm(cloneConfirmFax);
      sForm.setStreetAddr1(cloneStreetAddress1);
      sForm.setStreetAddr2(cloneStreetAddress2);
      sForm.setStreetAddr3(cloneStreetAddress3);
      sForm.setEmailAddress(cloneEmailAddress);
      sForm.setCustomerEmail(cloneCustomerEmail);
      sForm.setContactUsCCEmail(cloneContactUsCCEmail);
      sForm.setDefaultEmail(cloneDefaultEmail);
      sForm.setCountry(cloneCountry);
      sForm.setStateOrProv(cloneStateOrProvince);
      sForm.setCity(cloneCity);
      sForm.setPostalCode(clonePostalCode);
      sForm.setBillingAddress1(clonebilStreetAddress1);
      sForm.setBillingAddress2(clonebilStreetAddress2);
      sForm.setBillingAddress3(clonebilBillingAddress3);
      sForm.setBillingPostalCode(clonebilZipOrPostalCode);
      sForm.setBillingCountry(clonebilCountry);
      sForm.setBillingState(clonebilStateOrProvince);
      sForm.setBillingCity(clonebilCity);
      sForm.setPurchaseOrderAccountName(clonePurchaseOrderAccountName);
      sForm.setOrderPhone(cloneOrderPhone);
      sForm.setOrderFax(cloneOrderFax);
      sForm.setOrderGuideNote(cloneOrderGuideNote);
      sForm.setComments(cloneOrderGuideComments);
      sForm.setSkuTag(cloneSkuTag);
      sForm.setRuntimeDisplayOrderItemActionTypes(cloneRuntimeDisplayOrderItemActionTypes);
      sForm.setTaxableIndicator(cloneTaxableIndicator);
      sForm.setCrcShop(cloneCrcShop);
      sForm.setFreightChargeType(cloneFreightChargeType);
      sForm.setMakeShipToBillTo(cloneMakeShipToBillTo);
      sForm.setOrderManagerEmails(cloneOrderManagerEmailS);
      sForm.setInvReminderEmailSub(cloneEmailSub);
      sForm.setInvReminderEmailMsg(cloneEmailMsg);
      sForm.setNotifyOrderEmailGenerator(cloneNotifyOrderEmailGenerator);
      sForm.setPendingApprovEmailGenerator(clonePendingApprovEmailGenerator);
      sForm.setRejectOrderEmailGenerator(cloneRejectOrderEmailGenerator);
      sForm.setConfirmOrderEmailGenerator(cloneConfirmOrderEmailGenerator);
      sForm.setOrderConfirmationEmailTemplate(cloneOrderConfirmationEmailTemplate);
      sForm.setShippingNotificationEmailTemplate(cloneShippingNotificationEmailTemplate);
      sForm.setPendingApprovalEmailTemplate(clonePendingApprovalEmailTemplate);
      sForm.setRejectedOrderEmailTemplate(cloneRejectedOrderEmailTemplate);
      sForm.setModifiedOrderEmailTemplate(cloneModifiedOrderEmailTemplate);
      String storeId = null;
      if (Utility.isSet(sForm.getStoreId()) && !"0".equalsIgnoreCase(sForm.getStoreId().trim())) {
    	  storeId = sForm.getStoreId();
      }
      else {
    	  storeId = appUser.getUserStore().getStoreId() + "";
      }
      sForm.setEmailTemplateChoices(StoreAccountMgrLogic.getStoreEmailTemplateChoices(storeId));
      
      //sForm.setEdiShipToPrefix(cloneEdiShipToPrefix);
      sForm.setCustomerRequestPoAllowed(cloneCustomerRequestPoAllowed);
      sForm.setAutoOrderFactor(cloneAutoOrderFactor);
      sForm.setShowDistInventory(cloneShowDistInventory);
      sForm.setInvPOSuffix(clonePoSuffix);
      sForm.setInvLedgerSwitch(cloneLedgerSwitch);
      sForm.setConnectionCustomer(cloneConnectionCustomer);
      sForm.setInvOGListUI(cloneInvOGListUI);
      sForm.setInvCheckPlacedOrder(cloneCheckPlacedOrder);
      sForm.setInvMissingNotification(cloneInvMissingNotify);
      sForm.setCartReminderInterval(cloneCartReminderInt);
      sForm.setAuthorizedForResale(cloneAuthorizedForResale);
      sForm.setAllowOrderConsolidation(cloneAllowOrderConsolidationt);
      sForm.setScheduleCutoffDays(cloneScheduleCutoffDays);
      sForm.setShowScheduledDelivery(cloneShowSchedDel);
      sForm.setShowDistSkuNum(cloneShowDistSkuNum);
      sForm.setShowDistDeliveryDate(cloneShowDistDeliveryDate);
      sForm.setRebateEffDate(cloneRebateEffDateS);
      sForm.setRebatePersent(cloneRebatePersentS);
      sForm.setTargetMarginStr(cloneTargetMarginStr);
      sForm.setCustomerSystemApprovalCd(cloneCustomerSystemApprovalCd);
      sForm.setShowSPL(cloneShowSPL);
      sForm.setAddServiceFee(cloneAddServiceFee);
      sForm.setHoldPO(cloneHoldPO);
      sForm.setAllowChangePassword(cloneAllowChangePassword);
      sForm.setAccountFolder(cloneAccountFolder);
      sForm.setAccountFolderNew(cloneAccountFolderNew);
      //sForm.setWorkflowEmail(cloneWorkflowEmail);
      sForm.setModifyQtyBy855(Utility.isTrue(cloneModifyQtyBy855,false));
      sForm.setCreateOrderItemsBy855(Utility.isTrue(cloneCreateOrderItemsBy855,false));
      sForm.setCreateOrderBy855(Utility.isTrue(cloneCreateOrderBy855,false));

      sForm.setAllowModernShopping(Utility.isTrue(cloneAllowModernShopping,false));
      sForm.setBudgetThresholdType(cloneBudgetThresholdType);
      sForm.setAllowSiteLLC(Utility.isTrue(cloneAllowSiteLLC,false));
      sForm.setShowExpressOrder(Utility.isTrue(cloneShowExpressOrder,false));
      sForm.setAllowOrderInvItems(Utility.isTrue(cloneAllowOrderInvItems,false));
      sForm.setAllowReorder(Utility.isTrue(cloneAllowReorder,false));
      sForm.setUsePhysicalInventory(Utility.isTrue(cloneUsePhysicalInventory,false));
      sForm.setShowInvCartTotal(Utility.isTrue(cloneShowInvCartTotal,false));
      sForm.setShowMyShoppingLists(Utility.isTrue(cloneShowMyShoppingLists,false));
      sForm.setShowReBillOrder(Utility.isTrue(cloneShowReBillOrder,false));
      sForm.setAllowSetWorkOrderPoNumber(Utility.isTrue(cloneAllowSetWorkOrderPoNumber,false));
      sForm.setWorkOrderPoNumberIsRequired(Utility.isTrue(cloneWorkOrderPoNumberIsRequired,false));
      sForm.setAllowBuyWorkOrderParts(Utility.isTrue(cloneAllowBuyWorkOrderParts,false));
      sForm.setUserAssignedAssetNumber(Utility.isTrue(cloneUserAssignedAssetNumber,false));
      sForm.setContactInformationType(cloneContactInformationType);
      sForm.setShopUIType(cloneShopUIType);
      sForm.setFaqLink(cloneFaqLink);
      sForm.setPdfOrderClass(clonePdfOrderClass);
      sForm.setPdfOrderStatusClass(clonePdfOrderStatusClass);
      sForm.setAllowCreditCard(cloneAllowCC);
      sForm.setAllowAlternateShipTo(Utility.isTrue(cloneAllowAlternateShipTo,false));
      sForm.setModifyCustPoNumBy855(Utility.isTrue(cloneModifyCustPoNumBy855,false));
      sForm.setSupportsBudget(Utility.isTrue(cloneSupportsBudget, false));
      
      // sForm.setReSaleAccountErpNumber(cloneReSaleAccountErpNumber);
      session.setAttribute("STORE_ACCOUNT_DETAIL_FORM", sForm);

      //flag of Clone
      sForm.setClone(true);
      sForm.setFirstUpdate(true);
    } else {
      ae.add("error",
             new ActionError("error.simpleGenericError", "No account data found.Probably expired page was used. Please select account again"));
      getDetail(request, sForm);
      log.debug("AccountData was recovery for id->" + sForm.getId());
    }
    return ae;
  }

  public static ActionErrors editAccountFiscalCal(HttpServletRequest request,
            ActionForm form) throws Exception {
        log.info("editAccountFiscalCal");
        ActionErrors lUpdateErrors = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session
                .getAttribute(Constants.APP_USER);
        StoreAccountMgrDetailForm sForm = (StoreAccountMgrDetailForm) form;
        int id = Utility.parseInt(request.getParameter("fiscalCalendarId"));
        if (sForm == null) {
            lUpdateErrors.add("Fiscal Calendar", new ActionError(
                    "variable.empty.error", "Fiscal Calendar"));
            return lUpdateErrors;
        }
        APIAccess factory = new APIAccess();
        Account accountBean = factory.getAccountAPI();
        sForm.getAccountData().setFiscalCalenders(
                accountBean.getFiscalCalCollection(sForm.getAccountData()
                        .getBusEntity().getBusEntityId()));
        List list = sForm.getAccountData().getFiscalCalenders();
        if (id > 0) {
            for (int i = 0; list != null && i < list.size(); i++) {
                FiscalCalenderView fcd = (FiscalCalenderView) list.get(i);
                if (fcd.getFiscalCalender().getFiscalCalenderId() == id) {
                	sForm.setFiscalYearString(Integer.toString(fcd.getFiscalCalender().getFiscalYear()));
                    FiscalCalenderView fiscalCalUpdate =  FiscalCalenderView.createValue();
                    fiscalCalUpdate.setFiscalCalender((FiscalCalenderData) fcd.getFiscalCalender().clone());
                    FiscalCalenderDetailDataVector details = new FiscalCalenderDetailDataVector();
                    for(Object oDet:fcd.getFiscalCalenderDetails()) {
                        details.add(((FiscalCalenderDetailData)oDet).clone());
                    }
                    fiscalCalUpdate.setFiscalCalenderDetails(details);
                    sForm.setFiscalCalUpdate(fiscalCalUpdate);
                    sForm.getFiscalCalUpdate().getFiscalCalender().setFiscalCalenderId(id);
                    break;
                }
            }
        }
        return lUpdateErrors;
    }

  public static ActionErrors updateAccountFiscalCal(HttpServletRequest request,
    ActionForm form, boolean createNew) throws Exception {
	  log.info("updateAccountFiscalCal");

    ActionErrors lUpdateErrors = new ActionErrors();
    HttpSession session = request.getSession();

    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

    StoreAccountMgrDetailForm sForm = (StoreAccountMgrDetailForm) form;

    if (sForm == null) {
      lUpdateErrors.add("Fiscal Calendar",new ActionError("variable.empty.error","Fiscal Calendar"));
      // Report the errors to allow for edits.
      return lUpdateErrors;
    }

    if(sForm.getNumberOfBudgetPeriods() != null && sForm.getNumberOfBudgetPeriods() > Constants.MAX_PERIODS){
    	lUpdateErrors.add("Fiscal Calendar",new ActionError("error.maximum.value.of.field", "Number of Periods", Constants.MAX_PERIODS));
    }

    String action = request.getParameter("action");
    if((action != null) &&(action.equalsIgnoreCase("Save Fiscal Calendar"))){
    	String fiscalYearStr = sForm.getFiscalYearString();
    	try{
    		sForm.getFiscalCalUpdate().getFiscalCalender().setFiscalYear(Integer.parseInt(fiscalYearStr));
    	}catch(Exception e)  {
    		lUpdateErrors.add("Fiscal Calendar Data",
                new ActionError("error.simpleGenericError",
                                "Invalid Fiscal Year"));
    		return lUpdateErrors;
    	}
    }
    if (RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
      if (sForm.getStoreId() == null || sForm.getStoreId().length() == 0) {
        lUpdateErrors.add("storeId",
                          new ActionError("variable.empty.error",
                                          "Store Id"));
      }
    }
    if (null != lUpdateErrors && lUpdateErrors.size() > 0) {
      return lUpdateErrors;
    }
    if (createNew == true) {
        sForm.setFiscalCalUpdate(null);
        if(action.equals("setForAllFiscalYears"))
        	sForm.setFiscalYearFormat("0");
        sForm.getFiscalCalUpdate().getFiscalCalender().setFiscalCalenderId(0);
        sForm.setNumberOfBudgetPeriods(null);
        return lUpdateErrors;
    }
    APIAccess factory = new APIAccess();
    Account accountBean = factory.getAccountAPI();
    SessionTool stl = new SessionTool(request);
    String cuser = stl.getLoginName();
    sForm.getFiscalCalUpdate().getFiscalCalender().setModBy(cuser);
    log.debug("calling accountBean.updateFiscalCal 0");

      //remove any spaces
      FiscalCalenderDetailDataVector details = sForm.getFiscalCalUpdate().getFiscalCalenderDetails();
      if (details != null && details.size() > 0) {
          for (Object oDetail : details) {
              FiscalCalenderDetailData fiscalDet = (FiscalCalenderDetailData) oDetail;
              if (Utility.isSet(fiscalDet.getMmdd())) {
                  fiscalDet.setMmdd(fiscalDet.getMmdd().replaceAll(" ", ""));
              }
          }
      }

    lUpdateErrors = checkOnFiscalCalData(sForm, request);
    if (null != lUpdateErrors && lUpdateErrors.size() > 0) {
      lUpdateErrors.add("Fiscal Calendar Data",
                        new ActionError("error.simpleGenericError",
                                        "Check the data values entered"));
      return lUpdateErrors;
    }
    FiscalCalenderView fcd = accountBean.updateFiscalCal(sForm.getFiscalCalUpdate());
    sForm.getAccountData().setFiscalCalenders
      (accountBean.getFiscalCalCollection(fcd.getFiscalCalender().getBusEntityId())
      );
    log.debug("DONE calling accountBean.updateFiscalCal 1");
    sForm.setFiscalCalUpdate(null);
    // display copy budgetTo next year in the current fiscal year row.
    checkForCurrentFiscalYearBudgets(form,appUser);
    return lUpdateErrors;
  }

  private final static SimpleDateFormat PERIOD_DATE_FORMAT = new SimpleDateFormat("M/d yyyy");
  static {
      PERIOD_DATE_FORMAT.setLenient(false);
  }

  private static Date parsePeriodDate(String source) {
      try {
    	  /*
    	   * Checking whether the source is formatted correctly.
    	   * For instance, Utility.parseDate will return null if
    	   * source is like "0001/5"
    	   */
    	  Date d = Utility.parseDate(source + "/2000");
    	  if(d==null){
    		  return null;
    	  }
    	  return PERIOD_DATE_FORMAT.parse(source + " 2000");
      } catch (Exception e) {
          return null;
      }
  }

  private static Date parsePeriodDate(String source, int fYear) {
 	 SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

	 if(fYear==0){
		 if(source.equals("2/29")){
			 return null;
		 }else{
			 //allow use 0 for fiscal year
			 fYear = 2000;
		 }
	 }
	 String dateStr =source +"/"+fYear;
	 format.setLenient(false);
	 try {
		 return format.parse(dateStr);
	 } catch (Exception e) {
		 return null;
	 }
	 //return null;
  }
  private static ActionErrors checkOnFiscalCalData(StoreAccountMgrDetailForm pForm,
		  HttpServletRequest request) {
	  log.info("checkOnFiscalCalData");
    final FiscalCalenderView pFcd = pForm.getFiscalCalUpdate();
    final List fiscalCalenders = pForm.getAccountData().getFiscalCalenders();
    int fYear = pFcd.getFiscalCalender().getFiscalYear();
      //StringBuilder sb = new StringBuilder();
      ArrayList<String> emptyPeriods = new ArrayList<String>();
    ActionErrors dataCk = new ActionErrors();
     Date firstMmddDate = null;
     Date lastMmddDate;
     //5156
     boolean incorrectFormat=false;
     String sb1="";
     ArrayList<Date> fiscalPeriods=new ArrayList<Date>();
     //5156

 	 int effDateYear = 0;
 	 if (pFcd.getFiscalCalender().getEffDate() == null) {
 		dataCk.add("Fiscal Calendar Year:Wrong format of Effective Date", new ActionError("error.simpleGenericError", "Need correct Effective Date!"));
	 } else {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(pFcd.getFiscalCalender().getEffDate());
		if (firstMmddDate != null) {
			Calendar c2 = Calendar.getInstance();
			c2.setTime(firstMmddDate);
			if (c1.get(Calendar.DAY_OF_MONTH) != c2.get(Calendar.DAY_OF_MONTH) || c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH)) {
				dataCk.add("Fiscal Calendar Year:Wrong value of Effective Date", new ActionError("error.simpleGenericError",
						"Value of Effective Date must be equal with value of first period!"));
			}
		}
		// 5156
		effDateYear = c1.get(Calendar.YEAR);
	  }
	  int wrkYear = effDateYear; 
      FiscalCalenderDetailDataVector details = pFcd.getFiscalCalenderDetails();
	  Date prevMmddDate = null;
      if (details != null && details.size() > 0) {
          for (int i = 0; i < details.size(); i++) {
              String mmddStr = ((FiscalCalenderDetailData) details.get(i)).getMmdd();
              Date mmddDate = parsePeriodDate(mmddStr, wrkYear);
              if(mmddDate != null){
				  if(prevMmddDate!=null) {
					if(!prevMmddDate.before(mmddDate)) {
						wrkYear++;
						mmddDate = parsePeriodDate(mmddStr, wrkYear);
					}
				  }
				  prevMmddDate = mmddDate;
				  log.info("StoreAccountMgrLogic MMMDDD mmddDate: "+mmddDate+" prevdate: "+prevMmddDate);
                  fiscalPeriods.add(mmddDate);
              } else {
                  if (i<12) {  // period 13 can be empty
                    emptyPeriods.add("" + (i+1));
                  } else { // period 13 should not have incorrect string
                      if (Utility.isSet(mmddStr)) {
                          emptyPeriods.add("" + (i+1));
                      }
                  }
             }
          }
      }

      
	if (fYear == effDateYear) {
		for (int i = 0; i < fiscalPeriods.size(); i++) {
			for (int j = i + 1; j < fiscalPeriods.size(); j++) {
				if (fiscalPeriods.get(i).after(fiscalPeriods.get(j)) || fiscalPeriods.get(i).equals((fiscalPeriods.get(j)))) {
					sb1 = sb1 + (i + 1);
					incorrectFormat = true;
					dataCk.add("Fiscal Calendar Year:Periods are not in order", 
							new ActionError("error.simpleGenericError","Incorrect Date(s) Order for period(s):" + sb1 + "!"));
					break;
				}
			}
			if (incorrectFormat) break;
		}
	}

    if (emptyPeriods.size() > 0) {
		dataCk.add("Fiscal Calendar Year:Wrong format of periods",
                new ActionError("error.simpleGenericError", "Incorrect date(s) for period(s):" +
                        Utility.toCommaSting(emptyPeriods) + "!"));
	}
    
    if(pFcd.getFiscalCalender().getEffDate() != null){
    	try{
    		Date effDate = pFcd.getFiscalCalender().getEffDate();
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(effDate);
    		int y = cal.get(Calendar.YEAR);
    		
    		if(y < 1000){
        		String message = ClwI18nUtil.getMessage(request, "error.invalidEffDate", null);
    			dataCk.add("Fiscal Calendar Year:Wrong format of Effective Date", new ActionError("error.simpleGenericError",message));
    		}
			if(fYear!=0) {
				Date firstPeriod = (Date) fiscalPeriods.get(0);
				if(firstPeriod!=null) {
					if(!effDate.equals(firstPeriod)) {
						String message = "First period should be consistent with effective date";
						dataCk.add("Fiscal Calendar Year:Effective Date and First Period Mismatch", new ActionError("error.simpleGenericError",message));
					}
				}
			}
    		
    	}catch(Exception exc){
    		
    		String message = ClwI18nUtil.getMessage(request, "error.invalidEffDate", null);
			dataCk.add("Fiscal Calendar Year:Wrong format of Effective Date", new ActionError("error.simpleGenericError",message));
    	}
    }
     if (pFcd.getFiscalCalender().getFiscalYear() >= 0) {
        for (int i = 0; fiscalCalenders != null
                && i < fiscalCalenders.size(); i++) {
            FiscalCalenderView fcv = (FiscalCalenderView) fiscalCalenders.get(i);
            if ( fcv.getFiscalCalender().getFiscalYear() == pFcd.getFiscalCalender().getFiscalYear()
                    && fcv.getFiscalCalender().getFiscalCalenderId() != pFcd.getFiscalCalender().getFiscalCalenderId()) {
            	String yearMsg = "";
            	if(pFcd.getFiscalCalender().getFiscalYear()==0) {
            		yearMsg = Constants.ALL;
            	} else {
            		yearMsg = String.valueOf(pFcd.getFiscalCalender().getFiscalYear());
            	}
            	String errorMsg="Fiscal year '" + yearMsg + "' already exists!";
                dataCk.add("Fiscal Calendar Year", new ActionError(
                        "error.simpleGenericError", errorMsg));
                break;
            }
        }
     }
        if (pFcd.getFiscalCalender().getFiscalYear() == 0) {
            Calendar calendar = null;
            if (firstMmddDate != null) {
                calendar = Calendar.getInstance();
                calendar.setTime(firstMmddDate);
            }
            if (calendar != null && (calendar.get(Calendar.DAY_OF_MONTH) != 1
                    || calendar.get(Calendar.MONTH) != Calendar.JANUARY)) {
                dataCk.add("Fiscal Calendar Year", new ActionError(
                        "error.simpleGenericError",
                        "Fiscal calendars of "+Constants.ALL+" require date of first period to start on 1/1, use a calendar with a fiscal year instead"));
            }
        }


    if (null == pFcd) {
      ;
      dataCk.add("Fiscal Calendar Data",
                 new ActionError("error.simpleGenericError",
                                 "No data present."));
    }
    if (pFcd.getFiscalCalender().getFiscalYear() < 0 || pFcd.getFiscalCalender().getFiscalYear() > 9999) {
      dataCk.add("Fiscal Calendar Year",
                 new ActionError("error.simpleGenericError",
                                 "Fiscal Calendar Year " +
                                 pFcd.getFiscalCalender().getFiscalYear() +
                                 " is out of range. (0 - 9999 allowed)"));

    }

    Calendar now = Calendar.getInstance();
    int nowYear = now.get(Calendar.YEAR);
    log.debug("pFcd.getFiscalYear()=" + pFcd.getFiscalCalender().getFiscalYear()
                       + " nowYear=" + nowYear);

    if (pFcd.getFiscalCalender().getFiscalYear() < nowYear && pFcd.getFiscalCalender().getFiscalYear() != 0) {
      dataCk.add("Fiscal Calendar Year",
                 new ActionError("error.simpleGenericError",
                                 "Fiscal Calendar Year " +
                                 pFcd.getFiscalCalender().getFiscalYear() +
                                 " updates are not allowed." +
                                 "  Only years " + nowYear + " and later "
                                 + " can be modified."));

    }

    return dataCk;
  }


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
                                    ActionForm form) throws Exception {
	  log.info("delete");

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
  private static void initOrderPipeline(APIAccess pFactory, int pAccountId, StoreAccountMgrDetailForm pForm) throws
    Exception {
	  log.info("initOrderPipeline");
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

    if (null != lOrderRouting) {
      log.debug("lOrderRouting.size=" + lOrderRouting.size());
    }
    pForm.setAccountOrderRoutingList(lOrderRouting);
  }


  /**
   *  Adds a feature to the OrderRoute attribute of the AccountMgrLogic class
   *  !!! Warning the fields(ContractId and FinalContractId) of  OrderRoutingData
   hold value CatalogId and FinalCatalogId !!!!
   *@param  request        The feature to be added to the OrderRoute attribute
   *@param  form           The feature to be added to the OrderRoute attribute
   *@return                Description of the Return Value
   *@exception  Exception  Description of the Exception
   */
  public static ActionErrors addOrderRoute(HttpServletRequest request, ActionForm form) throws Exception {

	  log.info("addOrderRoute");
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    StoreAccountMgrDetailForm sForm = (StoreAccountMgrDetailForm) form;
    String cuser = (String) session.getAttribute(Constants.USER_NAME);
    int tempCatalogId = 0; // hold current Catalog id.
    int tempFinalCatalogId = 0; // hold current FinalCatlog Id
    try {
      if (sForm != null) {
        log.debug("----StoreAccountMgrLogic.addOrderRoute informs : OrderRoutingData " +
                           sForm.getNewOrderRoutingData().toString() + "----------");
        APIAccess factory = new APIAccess();
        OrderRoutingData route = sForm.getNewOrderRoutingData();
        tempCatalogId = route.getContractId();
        tempFinalCatalogId = route.getFinalContractId();
        OrderRoutingUtil.exchangeOrderRoutingData(route, true, true);
        log.debug("----StoreAccountMgrLogic.addOrderRoute informs : OrderRoutingData " + route + "----------");
        route.setAccountId(sForm.getIntId());
        OrderRoutingDataVector smallVector = new OrderRoutingDataVector();
        smallVector.add(route);
        factory.getAccountAPI().updateAccountOrderRoutingCollection(smallVector);
        initOrderPipeline(factory, sForm.getIntId(), sForm);
        OrderRoutingUtil.backExchangeOrderRoutingData(sForm.getNewOrderRoutingData(), true, true);
        log.debug("----StoreAccountMgrLogic.addOrderRoute OrderRoutingData " + sForm.getNewOrderRoutingData() +
                           "----------");

      }
    } catch (Exception e) {
      log.debug("----StoreAccountMgrLogic.addOrderRoute informs :Error.Data rollback");
      sForm.getNewOrderRoutingData().setContractId(tempCatalogId);
      sForm.getNewOrderRoutingData().setFinalContractId(tempFinalCatalogId);
      log.debug("----StoreAccountMgrLogic.addOrderRoute informs :  OrderRoutingData  " +
                         sForm.getNewOrderRoutingData() + "----------");
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
  public static ActionErrors testOrderRoute(HttpServletRequest request, ActionForm form) throws Exception {
	  log.info("testOrderRoute");
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    StoreAccountMgrDetailForm sForm = (StoreAccountMgrDetailForm) form;
    String cuser = (String) session.getAttribute(Constants.USER_NAME);
    try {
      if (sForm != null) {
        int accountId = sForm.getIntId();
        APIAccess factory = new APIAccess();
        OrderRoutingDescView result;
        try {
          log.debug("testing: " + sForm.getOrderRouteTestOrderZip() + " " + sForm.getIntId());
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
                                              ActionForm form) throws Exception {
	  log.info("savePipelineData");
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
                                                 ActionForm form) throws Exception {
	  log.info("updatePipelineEntry");
    ActionErrors ae = new ActionErrors();

    HttpSession session = request.getSession();
    String zip = request.getParameter("zip");
    String
      account_id = request.getParameter("account_id");
    String
      form_action = request.getParameter("form_action");
    String
      newCatalogId = request.getParameter("newCatalog");
    String
      newDistributor = request.getParameter("newDistributor");
    String
      newFreightHandler = request.getParameter("newFreightHandler");
    String
      newFinalCatalog = request.getParameter("newFinalCatalog");
    String
      newFinalDistributor = request.getParameter("newFinalDistributor");
    String
      newFinalFreightHandler = request.getParameter("newFinalFreightHandler");
    String
      newLtlFreightHandler = request.getParameter("newLtlFreightHandler");

    try {
      APIAccess factory = new APIAccess();
      log.debug("updatePipelineEntry");
      if (form_action.equals("Delete")) {
        factory.getAccountAPI().deleteOrderRoutingEntry
          (Integer.parseInt(account_id), zip);
      } else {
        OrderRoutingData ord = OrderRoutingData.createValue();
        ord.setAccountId
          (Integer.parseInt(account_id));
        ord.setZip(zip);
        ord.setContractId
          (Integer.parseInt(newCatalogId));
        ord.setDistributorId
          (Integer.parseInt(newDistributor));
        ord.setFreightHandlerId
          (Integer.parseInt(newFreightHandler));
        ord.setFinalContractId
          (Integer.parseInt(newFinalCatalog));
        ord.setFinalDistributorId
          (Integer.parseInt(newFinalDistributor));
        ord.setFinalFreightHandlerId
          (Integer.parseInt(newFinalFreightHandler));
        ord.setLtlFreightHandlerId
          (Integer.parseInt(newLtlFreightHandler));
        log.debug("----StoreAccountMgrLogic.updateOrderRoutingEntry  OrderRoutingDataOrderRoutingData : " +
                           ord.toString() + "----------");
        OrderRoutingUtil.exchangeOrderRoutingData(ord, true, true);
        log.debug("----StoreAccountMgrLogic.updateOrderRoutingEntry  OrderRoutingDataOrderRoutingData : " +
                           ord.toString() + "----------");
        factory.getAccountAPI().updateOrderRoutingEntry(ord);

      }

      StoreAccountMgrDetailForm sessForm = (StoreAccountMgrDetailForm)
                                           session.getAttribute("STORE_ACCOUNT_DETAIL_FORM");
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
                                                 ActionForm form) throws Exception {
	  log.info("saveAllPipelineData");
    ActionErrors ae = new ActionErrors();

    HttpSession session = request.getSession();
    StoreAccountMgrDetailForm sForm = (StoreAccountMgrDetailForm) form;
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
     ActionForm form) throws Exception {

	  log.info("initDeliverySchedules");
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
                           ActionForm form) throws Exception {
	  log.info("updateDeliverySchedule");
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
      log.debug("site_id =" + site_id);
      ae.add(ActionErrors.GLOBAL_ERROR,
             new ActionError("error.simpleGenericError",
                             "Site identifier is missing."));
    }

    if (sched_type == null || sched_type.length() == 0) {
      log.debug("site_id =" + site_id +
                         " sched_type = " + sched_type);
      ae.add(ActionErrors.GLOBAL_ERROR,
             new ActionError("error.simpleGenericError",
                             "Schedule type is missing."));
    }
    if (sched_type.startsWith("Bi")) {
      if (wkvals == null || wkvals.length < 2) {
    	  ae.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.simpleGenericError", "Two weeks must be selected for Bi-Monthly schedule"));
      }
    }
    
    if (sched_type.equals("Monthly")) {
        if (wkvals == null) {
            ae.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("error.simpleGenericError",
                                    "At least one value for week must be specified"));
        }
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
                               ActionForm form) throws Exception {

	  log.info("getInventoryItemsAvailable");
    ActionErrors reqErrors = new ActionErrors();
    StoreAccountMgrDetailForm sForm = (StoreAccountMgrDetailForm) form;

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
                      ActionForm form) throws Exception {
	  log.info("addInventoryItems");

    ActionErrors reqErrors = new ActionErrors();
    StoreAccountMgrDetailForm sForm = (StoreAccountMgrDetailForm) form;

    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    Account accountBean = factory.getAccountAPI();

    log.debug("addInventoryItems, accountId=" +
                       sForm.getAccountData().getBusEntity().getBusEntityId());
    String[] itemsarr = sForm.getItemsToAddToInventory();
    for (int i = 0; i < itemsarr.length; i++) {
      log.debug("addInventoryItems, itemId=" +
                         itemsarr[i]);
    }
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
                         ActionForm form) throws Exception {
	  log.info("removeInventoryItems");
    return updateInventoryItems(request, form, "remove");
  }

  public static ActionErrors
    enableAutoOrderForInventoryItems(HttpServletRequest request,
                                     ActionForm form) throws Exception {
	  log.info("enableAutoOrderForInventoryItems");
    return updateInventoryItems(request, form, "enable-auto-order");
  }

  public static ActionErrors
    disableAutoOrderForInventoryItems(HttpServletRequest request,
                                      ActionForm form) throws Exception {
	  log.info("disableAutoOrderForInventoryItems");
    return updateInventoryItems(request, form, "disable-auto-order");
  }


  public static ActionErrors
    updateInventoryItems(HttpServletRequest request,
                         ActionForm form, String pAction) throws Exception {
	  log.info("updateInventoryItems");
    ActionErrors reqErrors = new ActionErrors();
    StoreAccountMgrDetailForm sForm = (StoreAccountMgrDetailForm) form;

    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    Account accountBean = factory.getAccountAPI();

    log.debug("updateInventoryItems, accountId=" +
                       sForm.getAccountData().getBusEntity().getBusEntityId());
    String[] itemsarr = sForm.getSelectedInventoryItems();
    for (int i = 0; i < itemsarr.length; i++) {
      log.debug("updateInventoryItems, itemId=" +
                         itemsarr[i]);
    }
    accountBean.updateInventoryItems(
      sForm.getAccountData().getBusEntity().getBusEntityId(),
      itemsarr, (String) session.getAttribute(Constants.USER_NAME),
      pAction);

    // Reset the inventory list.
    sForm.getAccountData().setInventoryItemsData(
      accountBean.getInventoryItems(
        sForm.getAccountData().getBusEntity().getBusEntityId())
      );
    sForm.setSelectedInventoryItems(new String[0]);

    return reqErrors;
  }


  public static ActionErrors
    updateBillTo(HttpServletRequest request,
                 ActionForm form) throws Exception {
	  log.info("updateBillTo");
    ActionErrors reqErrors = new ActionErrors();
    StoreAccountBillToForm sform = (StoreAccountBillToForm) form;
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
    log.debug("btd2=" + btd2);
    sform.setBillTo(btd2);

    return reqErrors;
  }

  public static ActionErrors
    getBillToDetail(HttpServletRequest request,
                    ActionForm form) throws Exception {
	  log.info("getBillToDetail");
    ActionErrors reqErrors = new ActionErrors();
    StoreAccountBillToForm sform = (StoreAccountBillToForm) form;
    HttpSession session = request.getSession();
    APIAccess factory = new APIAccess();
    CleanwiseUser cwu = ShopTool.getCurrentUser(request);
    Account accountBean = factory.getAccountAPI();

    String btid = request.getParameter("billtoId");
    log.debug("logic lookup btid=" + btid);
    BillToData btd2 = accountBean.getBillToDetail
                      (Integer.parseInt(btid));
    log.debug("btd2=" + btd2);
    sform.setBillTo(btd2);

    return reqErrors;
  }

  public static ActionErrors
    changeOrderBillTo(HttpServletRequest request,
                      ActionForm form) throws Exception {
	  log.info("changeOrderBillTo");
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

  public static ActionErrors initShoppingControls
  (HttpServletRequest request,
   ActionForm form) {
	  ActionErrors ae = new ActionErrors();
	  log.info("initShoppingControls");
	  HttpSession session = request.getSession();
	  StoreAccountMgrDetailForm pForm =
	      (StoreAccountMgrDetailForm) session.getAttribute("STORE_ACCOUNT_DETAIL_FORM");
	  int acctid = pForm.getAccountData().getAccountId();
	  StoreAccountShoppingControlForm sform = (StoreAccountShoppingControlForm) form;
	  
	  if (acctid != sform.getAccountId()){
		  sform.setShoppingControlItemViewList(new ArrayList());
	  	  sform.setSiteShoppingControlItemViewList(new ArrayList());
	  }
	  return ae;
	  
  }
  public static ActionErrors lookupShoppingControls
  (HttpServletRequest request,
		  ActionForm form) {
	  log.info("lookupShoppingControls");
	  ActionErrors ae = new ActionErrors();

	  HttpSession session = request.getSession();
	  APIAccess factory = null;

	  StoreAccountMgrDetailForm pForm =
		  (StoreAccountMgrDetailForm) session.getAttribute("STORE_ACCOUNT_DETAIL_FORM");
	  int acctid = pForm.getAccountData().getAccountId();


	  StoreAccountShoppingControlForm sForm = (StoreAccountShoppingControlForm) form;

	  try {
		  factory = new APIAccess();
		  ShoppingServices ssvc = null;
		  ssvc = factory.getShoppingServicesAPI();
		  Account acct = factory.getAccountAPI();
		  Site site = factory.getSiteAPI();      
		  CatalogData cat = acct.getAccountCatalog(acctid);
		  if (cat == null){
			  ae.add("error", new ActionMessage("error.simpleError", "Account Catalog is not configured"));
			  return ae;
		  }
		  log.debug("back from getAccountCatalog cat=");

		  String category = sForm.getCategoryTempl();
		  String shortDesc = sForm.getShortDescTempl();
		  String longDesc = sForm.getLongDescTempl();
		  String itemPropName = sForm.getItemPropertyName();
		  String itemPropTempl = sForm.getItemPropertyTempl();
		  String manuNameTempl = sForm.getManuNameTempl();
		  String distNameTempl = sForm.getDistNameTempl();
		  String skuNumber = sForm.getSkuTempl();
		  String skuType = sForm.getSkuType();
		  String searchNumType = sForm.getSearchNumType();
		  if(!Utility.isSet(skuType)) skuType = SearchCriteria.STORE_SKU_NUMBER;
		  sForm.setSkuType(skuType);
		  //Create a set of filters
		  LinkedList searchConditions = new LinkedList();

		  //Category
		  if(Utility.isSet(category)) {
			  SearchCriteria sc = new
			  SearchCriteria(SearchCriteria.CATALOG_CATEGORY,
					  SearchCriteria.CONTAINS_IGNORE_CASE,category);
			  searchConditions.add(sc);
		  }
		  //Short Desc
		  if(Utility.isSet(shortDesc)){
			  SearchCriteria sc = new
			  SearchCriteria(SearchCriteria.PRODUCT_SHORT_DESC,
					  SearchCriteria.CONTAINS_IGNORE_CASE,shortDesc);
			  searchConditions.add(sc);
		  }

		  //Long Desc
		  if(Utility.isSet(longDesc)){
			  SearchCriteria sc = new
			  SearchCriteria(SearchCriteria.PRODUCT_LONG_DESC,
					  SearchCriteria.CONTAINS_IGNORE_CASE,longDesc);
			  searchConditions.add(sc);
		  }

		  //Item Meta Property
		  if(Utility.isSet(itemPropName) && Utility.isSet(itemPropTempl)) {
			  SearchCriteria sc = new
			  SearchCriteria(SearchCriteria.ITEM_META+itemPropName,
					  SearchCriteria.CONTAINS_IGNORE_CASE,itemPropTempl);
			  searchConditions.add(sc);
		  }

		  //Manufacturer
		  if(Utility.isSet(manuNameTempl)) {
			  SearchCriteria sc = new
			  SearchCriteria(SearchCriteria.MANUFACTURER_SHORT_DESC,
					  SearchCriteria.CONTAINS_IGNORE_CASE,manuNameTempl);
			  searchConditions.add(sc);
		  }

		  //Distributor
		  if(Utility.isSet(distNameTempl)) {
			  SearchCriteria sc = new
			  SearchCriteria(SearchCriteria.DISTRIBUTOR_SHORT_DESC,
					  SearchCriteria.CONTAINS_IGNORE_CASE,distNameTempl);
			  searchConditions.add(sc);
		  }

		  //sku
		  if (Utility.isSet(skuNumber)) {
			  SearchCriteria sc = new SearchCriteria();

			  if(searchNumType.equals("nameBegins")){
				  sc = new SearchCriteria(skuType,SearchCriteria.BEGINS_WITH,skuNumber);
			  }else if(searchNumType.equals("nameContains")){
				  sc = new SearchCriteria(skuType,SearchCriteria.CONTAINS,skuNumber);
			  }else{
				  sc = new SearchCriteria(skuType,SearchCriteria.EXACT_MATCH,skuNumber);
			  }
			  searchConditions.add(sc);
		  }

		  SearchCriteria sc = new SearchCriteria(SearchCriteria.ITEM_STATUS_CD, SearchCriteria.EXACT_MATCH, RefCodeNames.ITEM_STATUS_CD.ACTIVE);
		  searchConditions.add(sc);

		  CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
		  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
		  IdVector itemIds = catalogInfEjb.searchCatalogProducts(searchConditions, appUser.getUserStore().getStoreId(), cat.getCatalogId());
		  if (itemIds.size() == 0){
			  sForm.setShoppingControlItemViewList(new ArrayList());
			  sForm.setSiteShoppingControlItemViewList(new ArrayList());
			  return ae;
		  }else if (itemIds.size() > 500){
			  ae.add("error", new ActionMessage("error.simpleError",
                      "More than 500 items exist. Narrow your search by using filters"));
			  IdVector ids = new IdVector();
			  for (int i = 0; i < 500; i++){				  
				  ids.add(itemIds.get(i));
			  }
			  itemIds = ids;
		  }
		  ShoppingControlDataVector scDV = acct.getShoppingControls(acctid, itemIds);
		  

		  ProductDataVector productDV = factory.getProductInformationAPI().getProductsCollectionByItemIds(itemIds);
		  ArrayList shoppingControls = AccountSettingsData.getShoppingControlsView(scDV,productDV);
		  sForm.setShoppingControlItemViewList(shoppingControls);


		  IdVector sites = site.getAllSiteIds(acctid);
		  log.info("lookupShoppingControls(): sites.size()=" + sites.size());
		  ShoppingControlDataVector siteScDV = site.lookupSiteShoppingControlsAcctAdmPortal(sites, itemIds);
		  sForm.setSiteShoppingControlItemViewList(siteScDV);

	  } catch (Exception exc) {
		  exc.printStackTrace();
		  ae.add("error", new ActionError("error.systemError",
		  "No ejb access"));
	  }
	  return ae;
  }

  public static ActionErrors updateSiteControls(HttpServletRequest request,HttpServletResponse response,
		  ActionForm form) {
	  log.info("updateSiteControls");
	  ActionErrors ae = new ActionErrors();
	  Map<String,String> itemMessages = new HashMap<String,String>();

	  HttpSession session = request.getSession();
	  APIAccess factory = null;

	  StoreAccountMgrDetailForm pForm =
		  (StoreAccountMgrDetailForm) session.getAttribute("STORE_ACCOUNT_DETAIL_FORM");
	  SessionTool st = new SessionTool(request);

	  String cuser = st.getLoginName();
	  int acctid = pForm.getAccountData().getAccountId();
	  int siteId = Utility.parseInt(request.getParameter("siteId"));
	  int itemId = Utility.parseInt(request.getParameter("itemId"));

	  String maxQtyS = request.getParameter("maxQty");
	  String restDS = request.getParameter("restDays");

	  String key = ""+itemId+siteId;
	  int maxQty=0;
	  try{
		  maxQty = Integer.parseInt(maxQtyS);
		  if(maxQty < (-1)){
			  itemMessages.put(key, "Error: "+maxQty+" is not a positive number");
			  pForm.setItemMessagesMap(itemMessages);
			  return ae;
		  }
	  }catch(NumberFormatException exc){
		  exc.printStackTrace();
		  itemMessages.put(key, "Error: "+maxQtyS+" is not a valid number");
		  pForm.setItemMessagesMap(itemMessages);
		  return ae;
	  }
	  int restD=0;
	  try{
		  restD = Integer.parseInt(restDS);
		  if(restD < (-1)){
			  itemMessages.put(key, "Error: "+restDS+" is not a positive number");
			  pForm.setItemMessagesMap(itemMessages);
			  return ae;
		  }
	  }catch(NumberFormatException exc){
		  exc.printStackTrace();
		  itemMessages.put(key, "Error: "+restDS+" is not a valid number");
		  pForm.setItemMessagesMap(itemMessages);
		  return ae;
	  }

	  try{

		  factory = new APIAccess();
		  Site site = factory.getSiteAPI();

		  ShoppingControlData scd = ShoppingControlData.createValue();
		  //scd.setAccountId(acctid);
		  scd.setSiteId(siteId);
		  scd.setItemId(itemId);
		  scd.setMaxOrderQty(maxQty);
		  scd.setRestrictionDays(restD);
		  scd.setModBy(cuser);
		  site.updateShoppingControl(scd);

		  if(itemMessages.containsKey(key)){
			  itemMessages.remove(key);
		  }
		  itemMessages.put(key, "Updated");

	  }catch (Exception exc) {
		  exc.printStackTrace();
		  ae.add("error", new ActionError("error.systemError",
	                                        "No ejb access"));
	  }

	  pForm.setItemMessagesMap(itemMessages);
	  return ae;

  }

  public static ActionErrors deleteAllSiteControls(HttpServletRequest request,HttpServletResponse response,
		  ActionForm form) {
	  log.info("deleteAllSiteControls");
	  ActionErrors ae = new ActionErrors();

	  HttpSession session = request.getSession();
	  APIAccess factory = null;

	  StoreAccountMgrDetailForm pForm =
		  (StoreAccountMgrDetailForm) session.getAttribute("STORE_ACCOUNT_DETAIL_FORM");
	  SessionTool st = new SessionTool(request);

	  String cuser = st.getLoginName();
	  int acctid = pForm.getAccountData().getAccountId();
	  int itemId = Utility.parseInt(request.getParameter("itemId"));

	  try{
		  factory = new APIAccess();
		  Site site = factory.getSiteAPI();

		  site.deleteAllSiteControlsForItem(acctid,itemId);
	  }catch (Exception exc) {
		  exc.printStackTrace();
		  ae.add("error", new ActionError("error.systemError",
	                                        "No ejb access"));
	  }
	  return ae;

  }

  public static ActionErrors deleteSiteControls(HttpServletRequest request,HttpServletResponse response,
		  ActionForm form) {
	  log.info("deleteSiteControls");
	  ActionErrors ae = new ActionErrors();

	  HttpSession session = request.getSession();
	  APIAccess factory = null;

	  StoreAccountMgrDetailForm pForm =
		  (StoreAccountMgrDetailForm) session.getAttribute("STORE_ACCOUNT_DETAIL_FORM");
	  SessionTool st = new SessionTool(request);

	  String cuser = st.getLoginName();
	  int acctid = pForm.getAccountData().getAccountId();
	  int siteId = Utility.parseInt(request.getParameter("siteId"));
	  int itemId = Utility.parseInt(request.getParameter("itemId"));

	  try{
		  factory = new APIAccess();
		  Site site = factory.getSiteAPI();

		  site.deleteShoppingControl(siteId, itemId);
	  }catch (Exception exc) {
		  exc.printStackTrace();
		  ae.add("error", new ActionError("error.systemError",
	                                        "No ejb access"));
	  }
	  return ae;

  }

  public static ActionErrors clearItemMessages(HttpServletRequest request,HttpServletResponse response,
		  ActionForm form) {
	  log.info("clearItemMessages");
	  ActionErrors ae = new ActionErrors();
	  APIAccess factory = null;
	  HttpSession session = request.getSession();
	  StoreAccountMgrDetailForm pForm =
	      (StoreAccountMgrDetailForm) session.getAttribute("STORE_ACCOUNT_DETAIL_FORM");
	  int itemId = Utility.parseInt(request.getParameter("itemId"));
	  int acctid = pForm.getAccountData().getAccountId();

	  try{
		  factory = new APIAccess();
		  Site site = factory.getSiteAPI();
		  IdVector sites = site.getAllSiteIds(acctid);

		  if(sites!=null && sites.size()>0){

			  if(pForm.getItemMessagesMap()!=null && pForm.getItemMessagesMap().size()>0){

				  Map<String,String> itemMessagesMap = pForm.getItemMessagesMap();
				  Iterator it = sites.iterator();
				  while(it.hasNext()){
					  Integer siteId = (Integer)it.next();
					  String key = ""+itemId+siteId.intValue();

					  if(itemMessagesMap.containsKey(key)){
						  itemMessagesMap.remove(key);
					  }
				  }
			  }
		  }
	  }catch (Exception exc) {
		  exc.printStackTrace();
		  ae.add("error", new ActionError("error.systemError",exc.getMessage()));
	  }
	  return ae;

  }

  public static ActionErrors getSiteControls(HttpServletRequest request,HttpServletResponse response,
		  ActionForm form) {
	  log.info("getSiteControls");

	  ActionErrors ae = new ActionErrors();

	    HttpSession session = request.getSession();
	    APIAccess factory = null;

	    StoreAccountMgrDetailForm pForm =
	      (StoreAccountMgrDetailForm) session.getAttribute("STORE_ACCOUNT_DETAIL_FORM");
	    SessionTool st = new SessionTool(request);
	    int acctid = pForm.getAccountData().getAccountId();

	    try {
	      factory = new APIAccess();
	      ShoppingServices ssvc = null;
	      ssvc = factory.getShoppingServicesAPI();
	      Account acct = factory.getAccountAPI();
	      Site site = factory.getSiteAPI();

	      int itemId = Utility.parseInt(request.getParameter("itemId"));
	      ShoppingControlDataVector itemSiteControls = new ShoppingControlDataVector();

	      //get all sites for the acct
	      IdVector sites = site.getAllSiteIds(acctid);
	      //get all ctrls for this item and sites
	      itemSiteControls = site.getSiteShoppingControlsForItem(sites, itemId);

	      request.getSession().setAttribute("item.site.controls", itemSiteControls);
	      IdVector itemIds = new IdVector();
	      itemIds.add(itemId);
	      Map sitesForItemsMap = site.getSitesForItems(itemIds, sites);

	      if (itemSiteControls != null) {

	    	  //get all site names
	    	  HashMap siteNameById = new HashMap();
	    	  for(int j=0; j<itemSiteControls.size(); j++){
	    		  ShoppingControlData scd = (ShoppingControlData)itemSiteControls.get(j);
	    		  int siteId = scd.getSiteId();
	    		  if(!siteNameById.containsKey(new Integer(siteId))){
	    			  String siteName = site.getSiteNameById(siteId);
	    			  siteNameById.put(new Integer(siteId), siteName);
	    		  }
	    	  }

	    	  Map<String,String> itemMessages = pForm.getItemMessagesMap();
	    	  Element rootEl = createXmlResponse(request, itemSiteControls,itemId, siteNameById, sitesForItemsMap,
	    			  itemMessages);
              response.setContentType("application/xml");
              response.setHeader("Cache-Control", "no-cache");
              response.setHeader("Pragma", "no-cache");
              response.setHeader("Cache-Control", "must-revalidate");
              response.setHeader("Cache-Control", "no-store");
              response.setDateHeader("Expires", -1);

              OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
              XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
              serializer.serialize(rootEl);
          }

	    } catch (Exception exc) {
	    	exc.printStackTrace();
	    	ae.add("error", new ActionError("error.systemError",
	                                        "No ejb access"));
	    }
	    return ae;
  }

  private static Element createXmlResponse(HttpServletRequest request, ShoppingControlDataVector scDV,
		  int itemId, HashMap pSiteNameById, Map<Integer, Set<Integer>> pSitesForItemsMap,
		  Map<String,String> pItemMessages) {

      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      DocumentBuilder docBuilder = null;

      try {
          docBuilder = factory.newDocumentBuilder();
      } catch (ParserConfigurationException ex) {


      }
      Document doc = docBuilder.getDOMImplementation().createDocument("", "SiteControls", null);

      Element  root = doc.getDocumentElement();
      root.setAttribute("Id", String.valueOf(itemId));
      //root.setAttribute("Title", String.valueOf(nextNote.getNote().getTitle()));

      for(int i=0; i<scDV.size(); i++){
          ShoppingControlData scd = (ShoppingControlData) scDV.get(i);
          String key = ""+scd.getItemId()+scd.getSiteId();
          String itemMessage=" ";
          if(pItemMessages!=null && pItemMessages.size()>0){
        	  if(pItemMessages.containsKey(key)){
        		  itemMessage = pItemMessages.get(key);
        	  }
          }
          Element outerNode = doc.createElement("SCD");
          root.appendChild(outerNode);

          Element node = doc.createElement("SiteId");
          node.appendChild(doc.createTextNode(String.valueOf(scd.getSiteId())));
          //node.appendChild(doc.createTextNode(String.valueOf(notetxt.toString().replaceAll("'", "&#39;"))));
          outerNode.appendChild(node);

          node = doc.createElement("SiteName");
          String siteName = (String) pSiteNameById.get(new Integer(scd.getSiteId()));
          node.appendChild(doc.createTextNode(siteName));
          outerNode.appendChild(node);

          node = doc.createElement("MaxQty");
          node.appendChild(doc.createTextNode(String.valueOf(scd.getMaxOrderQty())));
          outerNode.appendChild(node);

          node = doc.createElement("RestrictionDays");
          node.appendChild(doc.createTextNode(String.valueOf(scd.getRestrictionDays())));
          outerNode.appendChild(node);

          Set<Integer> siteIds = pSitesForItemsMap.get(itemId);
          boolean inCatalog = (siteIds != null && siteIds.contains(scd.getSiteId()));

          node = doc.createElement("InCatalog");
          node.appendChild(doc.createTextNode(inCatalog ? "Y" : "N"));
          outerNode.appendChild(node);

          node = doc.createElement("ItemMessage");
          node.appendChild(doc.createTextNode(itemMessage));
          outerNode.appendChild(node);
      }

      if(scDV==null || scDV.size()==0){
          Element node = doc.createElement("AjaxAction");
          node.appendChild(doc.createTextNode(String.valueOf("Close")));
          root.appendChild(node);
      }
      return root;
  }


  public static ActionErrors updateShoppingControls
    (HttpServletRequest request,
     ActionForm form) throws Exception {
	  log.info("updateShoppingControls");


    ActionErrors ae = new ActionErrors();

    SessionTool st = new SessionTool(request);
    HttpSession session = request.getSession();

    StoreAccountShoppingControlForm sform = (StoreAccountShoppingControlForm) form;
    int acctid = sform.getAccountId();
    IdVector sites = new IdVector();
    HashMap siteCtrls = new HashMap(); //  key= site, value= shopCtrlDV
    
    APIAccess factory = null;
    Site site = null;     
    try {
        factory = new APIAccess();
        site = factory.getSiteAPI();
    } catch (Exception exc) {
    	exc.printStackTrace();
    	ae.add("error", new ActionError("error.systemError",
                                        "No ejb access"));
    }    

    Iterator it = sform.getShoppingControlItemViewList().iterator();

        while (it.hasNext()) {
            ShoppingControlItemView scivd = (ShoppingControlItemView) it.next();
            if (scivd.isDirty()) {
                String maxQuantity = "*".equals(scivd.getMaxOrderQty()) ? "-1"
                        : scivd.getMaxOrderQty();
                String restrictionDays = "*".equals(scivd.getRestrictionDays()) ? "-1"
                        : scivd.getRestrictionDays();
                String errMsg = "";
                try {
                    if (maxQuantity != null && maxQuantity.trim().length() > 0) {
                        Integer.parseInt(maxQuantity);
                    }
                } catch (Exception e) {
                    errMsg +="[Max Order Qty]";
                }
                try {
                    if (restrictionDays != null
                            && restrictionDays.trim().length() > 0) {
                        Integer.parseInt(restrictionDays);
                    }
                } catch (Exception e) {
                    errMsg += "[Restriction Days]";
                }
                if (Utility.isSet(errMsg)) {
                    ae.add("error", new ActionMessage("error.simpleError",
                            "Invalid data in fields " + errMsg + " for item:"
                                    + scivd.getSkuNum() + ", '"
                                    + scivd.getShortDesc() + "'"));
                }
            }
        }
    if (ae.size() > 0) {
        return ae;
    }
    it = sform.getShoppingControlItemViewList().iterator();
    String cuser = st.getLoginName();

    ShoppingControlDataVector scdv = new ShoppingControlDataVector();
    while (it.hasNext()) {
    	ShoppingControlItemView scivd = (ShoppingControlItemView) it.next();
    	if(scivd.isDirty()){
    		String maxqty = scivd.getMaxOrderQty();
    		String restrictionDays = scivd.getRestrictionDays();
    		String scstatuscd = RefCodeNames.SIMPLE_STATUS_CD.ACTIVE;


    		if (!Utility.isSet(maxqty)) {
    			maxqty = "-1";
    		}
    		if (maxqty != null && maxqty.equals("*")) {
    			maxqty = "-1";
    		}
    		if (restrictionDays != null && restrictionDays.equals("*")) {
    			restrictionDays = "-1";
    		}
    		if (restrictionDays != null) {
    			restrictionDays = restrictionDays.trim();
    		}

    		if (!Utility.isSet(restrictionDays)) {
    			restrictionDays = "-1";
    		}
    		ShoppingControlData scd;
    		if(scivd.getShoppingControlData() == null){
    			scd = ShoppingControlData.createValue();
    			scd.setAddBy(cuser);
    		}else{
    			scd = scivd.getShoppingControlData();
    		}

	        scd.setMaxOrderQty(Integer.parseInt(maxqty));
	        scd.setAccountId(acctid);
	        scd.setModBy(cuser);
	        scd.setControlStatusCd(scstatuscd);
	        scd.setRestrictionDays(Integer.parseInt(restrictionDays));
          	scd.setHistoryOrderQty(0);
          	scd.setActualMaxQty(0);
          	scd.setExpDate(null);

          	scdv.add(scd);
          	scivd.setDirty(false);
    	}
    }
        
    log.debug("updateShoppingControls, acctid=" +acctid + " scdv.size=" + scdv.size());
    if (scdv.size() > 0){
      Account abean = st.getFactory().getAccountAPI();
      log.debug("1updateShoppingControls, acctid=" + acctid);
      ShoppingControlDataVector newvals = abean.updateShoppingControls(scdv);      
    }
      
    
    return ae;
  }


  public static ActionErrors setSelectedFhId(HttpServletRequest request, ActionForm sForm) {
	  log.info("setSelectedFhId");
    ActionErrors ae = new ActionErrors();
    String fhId = "";

    StoreAccountMgrDetailForm pForm = (StoreAccountMgrDetailForm) sForm;
    FreightHandlerViewVector fhVV = pForm.getFhFilter();
    if (fhVV != null && fhVV.size() > 0) {
      fhId = String.valueOf(((FreightHandlerView) fhVV.get(0)).getBusEntityData().getBusEntityId());
    }
    pForm.setFreightHandlerId(fhId);
    return ae;
  }

  public static ActionErrors setSelectedDistrId(HttpServletRequest request, ActionForm form) {
	  log.info("setSelectedDistrId");
    ActionErrors ae = new ActionErrors();
    String distId = "";

    StoreAccountMgrDetailForm pForm = (StoreAccountMgrDetailForm) form;
    DistributorDataVector distDV = pForm.getDistFilter();
    if (distDV != null && distDV.size() > 0) {
      distId = String.valueOf(((DistributorData) distDV.get(0)).getBusEntity().getBusEntityId());
    }

    pForm.setDistributorId(distId);
    return ae;
  }

  public static ActionErrors setSelectedCatalogId(HttpServletRequest request, ActionForm form) {
	  log.info("setSelectedCatalogId");
    ActionErrors ae = new ActionErrors();
    int iCatId = -1;
    StoreAccountMgrDetailForm pForm = (StoreAccountMgrDetailForm) form;
    CatalogDataVector catDV = pForm.getCatalogFilter();
    if (catDV != null && catDV.size() > 0) {
      iCatId = ((CatalogData) catDV.get(0)).getCatalogId();
    }
    if (iCatId > 0) {
      pForm.setCatalogId(String.valueOf(iCatId));

    } else {
      ae.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.simpleGenericError", "Not selected Catalog"));
      pForm.setCatalogId("");

    }
    return ae;

  }

  private static ActionErrors setContractIdbyCatalogId(StoreAccountMgrDetailForm pForm, int iCatId) {
	  log.info("setContractIdbyCatalogId");
    int iContractId = -1;
    boolean isContractDescDV = false;
    ActionErrors ae = new ActionErrors();
    try {
      APIAccess factory = new APIAccess();
      Contract contractEjb = factory.getContractAPI();
      ContractDescDataVector contractDescDV = contractEjb.getContractDescsByCatalog(iCatId);
      if (contractDescDV != null && contractDescDV.size() > 0)
        isContractDescDV = true;
      Iterator it = contractDescDV.iterator();
      while (it.hasNext()) {
        ContractDescData contractDescD = (ContractDescData) it.next();
        if (contractDescD.getStatus().equalsIgnoreCase(RefCodeNames.CONTRACT_STATUS_CD.ACTIVE)) {
          iContractId = contractDescD.getContractId();
          break;
        }
      }
      if (iContractId > 0) {
        //    pForm.setContractId(String.valueOf(iContractId));
      } else {
        //   pForm.setContractId("");
        if (isContractDescDV)
          ae.add(ActionErrors.GLOBAL_ERROR,
                 new ActionError("error.simpleGenericError", "Not found Active Contract by Catalog"));
        else ae.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.simpleGenericError", "Not found Contract by Catalog"));
      }
    } catch (NamingException e) {
      ae.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.simpleGenericError", e.getMessage()));
    } catch (RemoteException e) {
      ae.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.simpleGenericError", e.getMessage()));
    } catch (APIServiceAccessException e) {
      ae.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.simpleGenericError", e.getMessage()));
    }
    return ae;
  }

  public static void resetSelectedDistId(HttpServletRequest request, ActionForm form) {
	  log.info("resetSelectedDistId");
    StoreAccountMgrDetailForm pForm = (StoreAccountMgrDetailForm) form;
    pForm.setDistributorId("");
  }

  public static void resetSelectedFhId(HttpServletRequest request, ActionForm form) {
	  log.info("resetSelectedFhId");
    StoreAccountMgrDetailForm pForm = (StoreAccountMgrDetailForm) form;
    pForm.setFreightHandlerId("");
  }

  public static void resetSelectedCatId(HttpServletRequest request, ActionForm form) {
	  log.info("resetSelectedCatId");

    StoreAccountMgrDetailForm pForm = (StoreAccountMgrDetailForm) form;
    pForm.setCatalogId("");

  }

  private static CountryData getCountry(HttpSession session, String countryStr) {
	  log.info("getCountry");
    CountryDataVector countries = (CountryDataVector)session.getAttribute("country.vector");
    if (countryStr != null && countries != null) {
      for (int i=0; i<countries.size(); i++) {
        CountryData country = (CountryData)countries.get(i);
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
  
  /**
   * Copies the current fiscal year budget to next year for all sites.
   * @param form
   * @return - ActionErrors
   */
  public static ActionErrors copyBudgetToNextYear(ActionForm form,HttpServletRequest request) throws Exception { 
	  
		ActionErrors ae = new ActionErrors();
		StoreAccountMgrDetailForm sForm = (StoreAccountMgrDetailForm)form;

		APIAccess factory = new APIAccess();
		Account accountBean = factory.getAccountAPI();
	    int accountId = sForm.getIntId();
	    
	    //Get Current fiscal year.
	    FiscalCalenderData fiscalCalendarData = accountBean.getCurrentFiscalCalender(accountId);
	    int currentFiscalYear = 0;
	    if(fiscalCalendarData!=null) {
	    	currentFiscalYear = fiscalCalendarData.getFiscalYear();
	    }
	    if(currentFiscalYear==0) {
	    	GregorianCalendar cal = new GregorianCalendar();
	    	cal.setTime(new Date());
	    	currentFiscalYear = cal.get(Calendar.YEAR);
	    }	    
	    int nextFiscalYear = currentFiscalYear+1;
	    int fiscalCalendarPeriods = 0;
	    
	    //Get Fiscal Calendar for next fiscal year.
	    FiscalCalenderView nextFCalView = accountBean.getFiscalCalenderVForYear(accountId, nextFiscalYear);
	    if(nextFCalView==null) {
	    	String errorMessage = "No Fiscal calendar is found with next fiscal calendar year " +
	    			"(i.e. "+nextFiscalYear+") for this account."; 
	    	ae.add("Fiscal Calendar Data",new ActionError("error.simpleGenericError",errorMessage));
	    	return ae;
	    } else {
		    try {
		    	FiscalCalendarInfo fci = new FiscalCalendarInfo(nextFCalView);
		    	fiscalCalendarPeriods  = fci.getBudgetPeriodsAsHashMap().size();
		    }catch(Exception e) {	    	
		    }
	    }
	    
	    
		//Get Sites with budgets for next fiscal year. 
		SiteDataVector sites = getSitesWithBudgets(factory, accountId, nextFiscalYear);
        
        SiteData site;
        SiteView siteView;
        BudgetViewVector budgetViewVector;
        boolean isBudgetSetForNextYear = false;
        for (int index=0;index<sites.size(); index++) {
        	site = (SiteData)sites.get(index); 
        	if(site.getHasBudgets()) {
        		isBudgetSetForNextYear = true;
        		break;
        	}
        }
        
		if(isBudgetSetForNextYear) {			
			StringBuilder listOfIds = new StringBuilder();
			int sitesCount = 0;
			for(int index=0; index<sites.size(); index++) {
				site = (SiteData)sites.get(index); 
				budgetViewVector  = site.getBudgets();
				if(budgetViewVector!=null && budgetViewVector.size()>0) {
					if(sitesCount!=0 )
						listOfIds.append(",");
					listOfIds.append(String.valueOf(site.getBusEntity().getBusEntityId()));
					if(sitesCount==9) {
						listOfIds.append("...");
						break;
					}
					sitesCount++;
				}
			}
			
			if(sites.size()>0 && sites.size()<10) {
				listOfIds.append(".");
			}
			
			String errorMessage = "You cannot copy budgets from year " +currentFiscalYear
			+" to year "+nextFiscalYear +" because there are already budgets \n for year "+nextFiscalYear+
			" for site(s) "+listOfIds.toString() +"\n Please use the budget loader instead.";
			ae.add("Fiscal Calendar Data",
					new ActionError("error.simpleGenericError",errorMessage));
		} else { 
			//Get Sites with budgets for current fiscal year. 
			sites = getSitesWithBudgets(factory, accountId, currentFiscalYear);
			
			Budget budgetBean = factory.getBudgetAPI();			
			BudgetView item;
			BudgetDetailDataVector budgetDetailData = null; 
			SessionTool stl = new SessionTool(request);
		    String loginUserName = stl.getLoginName(); //Application User
			for(int ind=0; ind<sites.size(); ind++) {
				site = (SiteData)sites.get(ind);
				budgetViewVector = site.getBudgets();
				for (int i = 0; i < budgetViewVector.size(); i++) {
	                item = (BudgetView) budgetViewVector.get(i);
	                //Making item for next fiscal year budget.
	                item.getBudgetData().setBudgetId(0); 
	                item.getBudgetData().setBudgetYear(nextFiscalYear);
	                
	                //Set budgets equals to number of fiscal calendar 
	                budgetDetailData = item.getDetails();
	                for (Object oBudgetDetail : budgetDetailData) {
	                    BudgetDetailData budgetDetail = ((BudgetDetailData) oBudgetDetail);
                    	if(fiscalCalendarPeriods < budgetDetail.getPeriod() && budgetDetail.getAmount()!=null) {
                    		 budgetDetail.setAmount(null);
                    	}
                    }
	                
	            }
				budgetBean.updateBudgets(budgetViewVector, loginUserName);
			}
		}
		return ae;
	}
  
  	private static SiteDataVector getSitesWithBudgets(APIAccess factory,int accountId,int budgetYear) throws Exception {
  		SiteDataVector sites = null;
		Site siteBean = factory.getSiteAPI();
        QueryRequest qr = new QueryRequest();
        IdVector accountIds = new IdVector();
        accountIds.add(accountId);
        qr.filterByAccountIds(accountIds);
        ArrayList<String> statusList = new ArrayList<String>();
        statusList.add(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        statusList.add(RefCodeNames.BUS_ENTITY_STATUS_CD.LIMITED);
        qr.filterBySiteStatusCdList(statusList);
        
        sites = siteBean.getSitesForAccountWithBudgets(qr, accountId, budgetYear);    
  		return sites;
  	}
  	
  	private static void checkForCurrentFiscalYearBudgets(ActionForm form,CleanwiseUser appUser) {
  		int accountId = 0;
        StoreAccountMgrDetailForm pForm = (StoreAccountMgrDetailForm) form;
        if (appUser.isaAccountAdmin()) {
            accountId = appUser.getUserAccount().getAccountId();
        } else {
      	  try {
	        	  AccountData accountData = pForm.getAccountData();
	        	  BusEntityData busEntityData = accountData.getBusEntity();
	              accountId = busEntityData.getBusEntityId();
            } catch (Exception e) {
          	  log.info("StoreAccountMgrLogic --->>> No account Id found to get Cost centers.");
            }
        }
        try {
        	//STJ-4036
        	if(pForm.getFiscalCalUpdate()!= null && pForm.getFiscalCalUpdate().getFiscalCalender()!=null) {
        		pForm.getFiscalCalUpdate().getFiscalCalender().setFiscalCalenderId(-1);
        	}
      	  pForm.setShowCopyBudgets(false);
      	  if(accountId>0){
  		  	  APIAccess factory = new APIAccess();
	          Account accountBean = factory.getAccountAPI();
	          FiscalCalenderData fiscalCalendarData = accountBean.getCurrentFiscalCalender(accountId);
	          GregorianCalendar cal = new GregorianCalendar();
	          cal.setTime(new Date());
	          int fiscalYear = cal.get(Calendar.YEAR);
	          if(fiscalCalendarData!=null) {
	        	  pForm.setCurrentFiscalYear(fiscalCalendarData.getFiscalYear());
	        	  fiscalYear = fiscalCalendarData.getFiscalYear();
	          }
	          Budget budgetBean = factory.getBudgetAPI();
	          pForm.setShowCopyBudgets(budgetBean.isBudgetExistsForAccount(accountId, fiscalYear));	          
      	  }
        }catch (Exception e) {
      	  log.info("No cost center found for account id :"+accountId);
        }
  	}

}
