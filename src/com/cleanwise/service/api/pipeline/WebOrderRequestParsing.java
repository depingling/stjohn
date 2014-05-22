/*
 * CancelReplaceItemDuplication.java
 *
 * Created on August 25, 2003
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.cachecos.CacheKey;
import com.cleanwise.service.api.cachecos.Cachecos;
import com.cleanwise.service.api.cachecos.CachecosManager;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * Pipeline class that does intial ui order request parsing.
 * @author  YKupershmidt
 */
public class WebOrderRequestParsing implements OrderPipeline {
  /** Process this pipeline.
   *
   * @param OrderRequestData the order request object to act upon
   * @param Connection a active database connection
   * @param APIAccess
   *
   */
  public OrderPipelineBaton process(OrderPipelineBaton pBaton,
                                    OrderPipelineActor pActor,
                                    Connection pCon,
                                    APIAccess pFactory) throws PipelineException {
    try {
      PreOrderData preOrderD = pBaton.getPreOrderData();
      String orderSourceCd = preOrderD.getOrderSourceCd();
      pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
      if (RefCodeNames.ORDER_SOURCE_CD.EDI_850.equals(orderSourceCd)) {
        return pBaton;
      }

      OrderData orderD = pBaton.getOrderData();
      String orderStatusCd = orderD.getOrderStatusCd();

      //Set account and store data
      AccountData accountD = BusEntityDAO.getAccountFromCache(orderD.getAccountId());
      if (accountD == null) {
        pBaton.addError(pCon, OrderPipelineBaton.NO_ACCOUNT_FOUND,
                        RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                        "pipeline.message.noAccountFound");

      } else {
        orderD.setAccountErpNum(accountD.getBusEntity().getErpNum());
        String acctLocale = accountD.getBusEntity().getLocaleCd();
        if (acctLocale == null || acctLocale.length() == 0 ||
            acctLocale.equals("unk")) {
          acctLocale = "en_US";
        }
        orderD.setLocaleCd(acctLocale);

        if (!RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE.equals(accountD.getBusEntity().getBusEntityStatusCd())) {
          pBaton.addError(pCon, OrderPipelineBaton.ACCOUNT_NOT_ACTIVE,
                          RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW, 0, 0,
                          "pipeline.message.accountNotActive");
        }
      }

      //Set site data
      SiteData siteD = BusEntityDAO.getSiteFromCache(orderD.getSiteId());
      // veronika: to check
      //siteD = null;
      if (siteD == null) {
        orderD.setOrderSiteName(preOrderD.getSiteName());
        pBaton.addError(pCon, OrderPipelineBaton.NO_SITE_FOUND,
                        RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                        "pipeline.message.noSiteFound");
      } else {
        orderD.setOrderSiteName(siteD.getBusEntity().getShortDesc());
        orderD.setSiteErpNum(siteD.getBusEntity().getErpNum());
      }

      orderD.setOrderBudgetTypeCd(preOrderD.getOrderBudgetTypeCd());

      //Order Source
      orderD.setOrderSourceCd(orderSourceCd);

      //Customer Po number
      String custPoNum = preOrderD.getCustomerPoNumber();
      //Do no processing if this order already has a purchase order number assigned
      //and the order has no po number
      if(Utility.isSet(orderD.getRequestPoNum())){
    	  if(Utility.isSet(preOrderD.getCustomerPoNumber()) && !preOrderD.getCustomerPoNumber().equals(orderD.getRequestPoNum())){
    		  throw new Exception("PO Mismatch (preorder and order): "+preOrderD.getCustomerPoNumber()+"!="+orderD.getRequestPoNum());
    	  }
    	  custPoNum = orderD.getRequestPoNum();
      }
      orderD.setRequestPoNum(custPoNum);

      //Erp system
       String erpSystemCd = getErpSystemCd(orderD.getStoreId(), pCon);
       orderD.setErpSystemCd(erpSystemCd);

      //Ref order and comments
      orderD.setRefOrderNum(preOrderD.getOrderRefNumber());
      orderD.setComments(preOrderD.getCustomerComments());

      orderD.setRefOrderId(preOrderD.getRefOrderId());

      //User related
      UserData userD = null;
      int userId = preOrderD.getUserId();
      orderD.setUserId(userId);
      String userFirstName = " - ";
      String userLastName = " - ";
      String workflowStatus = null;
      if (userId > 0) {
        DBCriteria dbcUser = new DBCriteria();
        dbcUser.addEqualTo(UserDataAccess.USER_ID, userId);
        UserDataVector userDV = UserDataAccess.select(pCon, dbcUser);
        if (userDV.size() == 0) {
          pBaton.addError(pCon, OrderPipelineBaton.NO_USER_FOUND, null,
                          RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                          "pipeline.message.noUserFound",
                          "" + userId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
        } else {
          userD = (UserData) userDV.get(0);
          userFirstName = userD.getFirstName();
          userLastName = userD.getLastName();
          workflowStatus = userD.getWorkflowRoleCd();
          pBaton.setUserWorkflowRoleCd(workflowStatus);
        }
      }
      orderD.setUserFirstName(userFirstName);
      orderD.setUserLastName(userLastName);
      orderD.setWorkflowStatusCd(workflowStatus);

      setContactInformation(siteD, orderD, preOrderD, pCon);

      //Cost Center
      orderD.setCostCenterId(preOrderD.getCostCenterId());

      //Contract
      int contractId = preOrderD.getContractId();
      orderD.setContractId(contractId);
      Contract contractEjb = null;
      if (contractId > 0) {
        try {
          contractEjb = pFactory.getContractAPI();
        } catch (Exception exc) {
          String mess = "Can't get contract Ejb";
          throw new Exception(mess);
        }
        try {
          ContractData contractD = contractEjb.getContract(contractId);
          orderD.setContractShortDesc(contractD.getShortDesc());
          String localeCd = contractD.getLocaleCd();
          orderD.setLocaleCd(localeCd);
          CurrencyData currD = I18nUtil.getCurrency(localeCd);
          if (currD != null) {
            orderD.setCurrencyCd(currD.getGlobalCode());
          }
        } catch (DataNotFoundException exc) {
          //Check conract validity in other pipeline class.
          //It is not mandatory to make an order
        }
      }
      //Intit price, cost, tax etc.
      BigDecimal bigzero = new BigDecimal(0);
      orderD.setTotalPrice(bigzero);
      orderD.setOriginalAmount(bigzero);
      orderD.setTotalTaxCost(bigzero);
      orderD.setTotalCleanwiseCost(bigzero);

      //Freight
      orderD.setTotalFreightCost(preOrderD.getFreightCharge());
      orderD.setTotalMiscCost(preOrderD.getHandlingCharge());
      if (preOrderD.getRushCharge() != null) {
        orderD.setTotalRushCharge(preOrderD.getRushCharge());
      }

      //Payment
      orderD.setOrderTypeCd(preOrderD.getOrderTypeCd());

      //Trading profile //usually = 0 for non edi orders
      orderD.setIncomingTradingProfileId(preOrderD.getIncomingProfileId());

      //Order meta data
      saveOrderMetaData(pBaton, pCon);

      //Order properties
      saveOrderPropertyData(pBaton);

      //billto
      saveOrderBillToAddress(pBaton, pFactory, accountD, siteD, pCon);

      //shipto
      saveOrderShipToAddress(pBaton, siteD);

      //save various order properties (billing order, any entered notes)
      boolean billingOrderFl = ("T".equals(preOrderD.getBillingOrder())) ? true : false;
      if (billingOrderFl) {
        OrderPropertyData opD = OrderPropertyData.createValue();
        opD.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORDER);
        opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORDER);
        opD.setValue("true");
        opD.setAddBy(orderD.getAddBy());
        opD.setModBy(orderD.getAddBy());
        pBaton.addOrderPropertyData(opD);
      }
      if (Utility.isSet(preOrderD.getOrderNote())) {
        String shortDesc = "Checkout Order Note";
        OrderPropertyData opD = OrderPropertyData.createValue();
        opD.setShortDesc(shortDesc);
        opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        opD.setValue(preOrderD.getOrderNote());
        opD.setAddBy(orderD.getAddBy());
        opD.setModBy(orderD.getAddBy());
        pBaton.addOrderPropertyData(opD);
      }

      // Add any order notes attached to the request.
      //ArrayList orderRequestNotes = custOrderReq.getOrderNotes();
      PreOrderPropertyDataVector preOrderPropertyDV = pBaton.getPreOrderPropertyDataVector();
      for (Iterator iter = preOrderPropertyDV.iterator(); iter.hasNext(); ) {
        //String onote = (String)orderRequestNotes.get(ii);
        PreOrderPropertyData popD = (PreOrderPropertyData) iter.next();
        String onote = popD.getValue();
        String noteType = popD.getOrderPropertyTypeCd();
        if (Utility.isSet(onote) &&
            (RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_ORDER_NOTE.equals(noteType)
             || RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_SYSTEM_ID.equals(noteType)
             || RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_SYSTEM_URL.equals(noteType))
          ) {
          OrderPropertyData opD = OrderPropertyData.createValue();
          opD.setShortDesc(popD.getShortDesc());
          opD.setOrderPropertyTypeCd(noteType);
          opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
          opD.setValue(onote);
          opD.setAddBy(orderD.getAddBy());
          opD.setModBy(orderD.getAddBy());
          pBaton.addOrderPropertyData(opD);
        }
      }

      //Status
      String status = pBaton.getOrderStatus();
      orderD.setOrderStatusCd(status);

      //Return
      pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
      return pBaton;
    } catch (Exception e) {
      e.printStackTrace();
      throw new PipelineException(e.getMessage());
    } finally {
    }
  }

  private void setContactInformation(SiteData siteD, OrderData orderD, PreOrderData preOrderD, Connection con) throws java.
    sql.SQLException {
    int userId = orderD.getUserId();
    //order contact name
    String contactName = preOrderD.getOrderContactName();
    if (!Utility.isSet(contactName) && userId > 0) {
      contactName = orderD.getUserFirstName() + " " + orderD.getUserLastName();
    }
    if (!Utility.isSet(contactName) && siteD != null && siteD.getSiteAddress() != null) {
      contactName = siteD.getSiteAddress().getName1() + " " + siteD.getSiteAddress().getName2();
    }
    orderD.setOrderContactName(contactName);
    //order phone
    PhoneDataVector phones = null;
    if (userId > 0) {
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(PhoneDataAccess.USER_ID, userId);
      phones = PhoneDataAccess.select(con, crit);
    }
    if (Utility.isSet(preOrderD.getOrderTelephoneNumber())) {
      orderD.setOrderContactPhoneNum(preOrderD.getOrderTelephoneNumber());
    } else {
      String num = getPhoneValue(phones, RefCodeNames.PHONE_TYPE_CD.PHONE);
      orderD.setOrderContactPhoneNum(num);
    }
    if (Utility.isSet(preOrderD.getOrderFaxNumber())) {
      orderD.setOrderContactFaxNum(preOrderD.getOrderFaxNumber());
    } else {
      String num = getPhoneValue(phones, RefCodeNames.PHONE_TYPE_CD.FAX);
      orderD.setOrderContactFaxNum(num);
    }
    if (Utility.isSet(preOrderD.getOrderEmail())) {
      orderD.setOrderContactEmail(preOrderD.getOrderEmail());
    } else {
      orderD.setOrderContactEmail(getUserEmail(userId, con));
    }
  }

  /**
   *Returns a value out of the specified phone data vector.  null vectors are ok.
   */
  private String getUserEmail(int userId, Connection con) throws java.sql.SQLException {
    if (userId <= 0) {
      return "";
    }
    DBCriteria crit = new DBCriteria();
    crit.addEqualTo(EmailDataAccess.USER_ID, userId);
    crit.addEqualTo(EmailDataAccess.EMAIL_TYPE_CD, RefCodeNames.EMAIL_TYPE_CD.PRIMARY_CONTACT);
    EmailDataVector emails = EmailDataAccess.select(con, crit);
    if (emails != null && emails.size() > 0) {
      EmailData em = (EmailData) emails.get(0);
      return em.getEmailAddress();
    }
    return "";
  }

  /**
   *Returns a value out of the specified phone data vector.  null vectors are ok.
   */
  private String getPhoneValue(PhoneDataVector phones, String typeCd) {
    if (phones == null) {
      return "";
    }
    Iterator it = phones.iterator();
    while (it.hasNext()) {
      PhoneData aPhone = (PhoneData) it.next();
      if (typeCd.equals(aPhone.getPhoneTypeCd())) {
        return aPhone.getPhoneNum();
      }
    }
    return "";
  }

/*  //---------------------------------------------------------------------------
  private AccountData getAccountData(int pAccountId, APIAccess pFactory) throws Exception {
    Account actEjb = null;
    try {
      actEjb = pFactory.getAccountAPI();
      return actEjb.getAccount(pAccountId, 0);
    } catch (DataNotFoundException exc) {
      return null;
    } catch (Exception e) {
      String msg = "No Account Ejb Access. ";
      throw new Exception(msg + e.getMessage());
    }
  }

  //----------------------------------------------------------------------------
  private SiteData getSiteData(int pSiteId, APIAccess pFactory) throws Exception {
    try {
      return pFactory.getSiteAPI().getSite(pSiteId);
    } catch (DataNotFoundException exc) {
      return null;
    } catch (Exception exc) {
      String msg = "No Site Ejb Access. ";
      throw new Exception(msg + exc.getMessage());
    }
  }*/


    private String getErpSystemCd(int pStoreId, Connection pCon) throws Exception {

        StoreData store = null;
        String erpSystemCd = null;

        try {
            if (pStoreId != 0) {

                CachecosManager cacheManager = Cachecos.getCachecosManager();

                if (cacheManager != null && cacheManager.isStarted()) {
                    try {
                        CacheKey cacheKey = BusEntityDAO.getStoreCacheKey(pStoreId);
                        store = (StoreData) cacheManager.get(cacheKey);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (store == null) {

                    DBCriteria dbc = new DBCriteria();

                    dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pStoreId);
                    dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.ERP_SYSTEM);
                    dbc.addOrderBy(PropertyDataAccess.PROPERTY_TYPE_CD); //To get always the same if there are a few

                    PropertyDataVector propDV = PropertyDataAccess.select(pCon, dbc);

                    if (propDV.size() != 0) {
                        PropertyData pD = (PropertyData) propDV.get(0);
                        erpSystemCd = pD.getValue();
                    }

                } else {
                    erpSystemCd = store.getErpSystemCode();
                }
            }
            return erpSystemCd;
        } catch (Exception exc) {
            throw new Exception(exc.getMessage());
        }
    }

    private void saveOrderMetaData(OrderPipelineBaton pBaton, Connection pCon) throws Exception {
    PreOrderData preOrderD = pBaton.getPreOrderData();
    OrderData orderD = pBaton.getOrderData();
    PreOrderMetaDataVector preOrderMetaDV = pBaton.getPreOrderMetaDataVector();
    String userName = orderD.getAddBy();
    OrderMetaDataVector orderMetaDV = new OrderMetaDataVector();

    pBaton.setOrderMetaDataVector(orderMetaDV);

      if (preOrderMetaDV != null) {
          Iterator it = preOrderMetaDV.iterator();
          while (it.hasNext()) {
              PreOrderMetaData oPreOrderMeta = (PreOrderMetaData) it.next();
              OrderMetaData orderMetaD = OrderMetaData.createValue();
              orderMetaD.setAddBy(userName);
              orderMetaD.setName(oPreOrderMeta.getName());
              orderMetaD.setValue(oPreOrderMeta.getValue());
              orderMetaD.setValueNum(oPreOrderMeta.getValueNum());
              orderMetaDV.add(orderMetaD);
          }
      }

      //Payment type
    String paymentType = preOrderD.getPaymentType();
    if (Utility.isSet(paymentType)) {
      OrderMetaData orderMetaD = OrderMetaData.createValue();
      orderMetaD.setAddBy(userName);
      orderMetaD.setName(RefCodeNames.CC_CD.TYPE);
      orderMetaD.setValue(paymentType);
      orderMetaDV.add(orderMetaD);
      pBaton.addError(pCon, OrderPipelineBaton.CREDIT_CARD_ORDER,
                      RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                      "pipeline.message.creditCardOrder");

    }
    /* Do not process credit cards so far
            String ccNum = custOrderReq.getCcNumber();
     if (Utility.isSet(ccNum)) {
       //CC number
       // zzz encrypt here
       OrderMetaData orderMetaD = OrderMetaData.createValue();
       orderMetaD.setAddBy(userName);
       orderMetaD.setName(RefCodeNames.CC_CD.NUMBER);
       orderMetaD.setValue(ccNum);
       orderMetaDV.add(orderMetaD);

       //Exp month
       int expMonth = custOrderReq.getCcExpMonth();
       orderMetaD = OrderMetaData.createValue();
       orderMetaD.setAddBy(userName);
       orderMetaD.setName(RefCodeNames.CC_CD.EXP_MONTH);
       orderMetaD.setValueNum(expMonth);
       orderMetaD.setValue(""+expMonth);
       orderMetaDV.add(orderMetaD);

       //Exp Year
       int expYear = custOrderReq.getCcExpYear();
       orderMetaD = OrderMetaData.createValue();
       orderMetaD.setAddBy(userName);
       orderMetaD.setName(RefCodeNames.CC_CD.EXP_YEAR);
       orderMetaD.setValueNum(expYear);
       orderMetaD.setValue(""+expYear);
       orderMetaDV.add(orderMetaD);

       //CC buyer
       String buyer = custOrderReq.getCcBuyerName();
       if (Utility.isSet(buyer)) {
         orderMetaD = OrderMetaData.createValue();
         orderMetaD.setAddBy(userName);
         orderMetaD.setName(RefCodeNames.CC_CD.BUYER_NAME);
         orderMetaD.setValue(buyer);
         orderMetaDV.add(orderMetaD);
       }
       //CC street1
       String street1 = custOrderReq.getCcStreet1();
       if (Utility.isSet(street1)) {
         orderMetaD = OrderMetaData.createValue();
         orderMetaD.setAddBy(userName);
         orderMetaD.setName(RefCodeNames.CC_CD.STREET1);
         orderMetaD.setValue(street1);
         orderMetaDV.add(orderMetaD);
       }
       //CC street2
       String street2 = custOrderReq.getCcStreet2();
       if(Utility.isSet(street2)) {
         orderMetaD = OrderMetaData.createValue();
         orderMetaD.setAddBy(userName);
         orderMetaD.setName(RefCodeNames.CC_CD.STREET2);
         orderMetaD.setValue(street2);
         orderMetaDV.add(orderMetaD);
       }
       //CC City
       String city = custOrderReq.getCcCity();
       if(Utility.isSet(city)) {
         orderMetaD = OrderMetaData.createValue();
         orderMetaD.setAddBy(userName);
         orderMetaD.setName(RefCodeNames.CC_CD.CITY);
         orderMetaD.setValue(city);
         orderMetaDV.add(orderMetaD);
       }
       //CC State
       String state = custOrderReq.getCcState();
       if(Utility.isSet(state)) {
         orderMetaD = OrderMetaData.createValue();
         orderMetaD.setAddBy(userName);
         orderMetaD.setName(RefCodeNames.CC_CD.STATE);
         orderMetaD.setValue(state);
         orderMetaDV.add(orderMetaD);
       }
       //CC Postal Code
       String postalCode = custOrderReq.getCcPostalCode();
       if(Utility.isSet(postalCode)) {
         orderMetaD = OrderMetaData.createValue();
         orderMetaD.setAddBy(userName);
         orderMetaD.setName(RefCodeNames.CC_CD.POSTAL_CODE);
         orderMetaD.setValue(postalCode);
         orderMetaDV.add(orderMetaD);
       }
     }
     */
    //Requested Ship date
    Date reqShipDate = preOrderD.getOrderRequestedShipDate();
    if (reqShipDate != null) {
      OrderMetaData orderMetaD = OrderMetaData.createValue();
      orderMetaD.setAddBy(userName);
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

      orderMetaD.setName(RefCodeNames.ORDER_PROPERTY_TYPE_CD.REQUESTED_SHIP_DATE);
      orderMetaD.setValue(sdf.format(reqShipDate));
      orderMetaDV.add(orderMetaD);
    }
    
    //Bypass order routing
    boolean bypassOrderRouting = ("T".equals(preOrderD.getBypassOrderRouting())) ? true : false;
    if (bypassOrderRouting) {
      // This order is not be routed even if it is a small order.
      OrderMetaData orderMetaD = OrderMetaData.createValue();
      orderMetaD.setAddBy(userName);
      orderMetaD.setValue("TRUE");
      orderMetaD.setName(Order.BYPASS_ORDER_ROUTING);
      orderMetaDV.add(orderMetaD);
    }

    orderD.setWorkflowInd(preOrderD.getWorkflowInd());

    //Hold until date
    //OrderRequestData orderReq = pBaton.getOrderRequestData();
    Date holdDate = preOrderD.getHoldUntilDate();
    if (holdDate != null) {
      if (holdDate.before(Utility.MAX_DATE)) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String holdDateS = sdf.format(holdDate);
        OrderMetaData orderMetaD = OrderMetaData.createValue();
        orderMetaD.setAddBy(userName);
        orderMetaD.setValue(holdDateS);
        orderMetaD.setName(RefCodeNames.ORDER_PROPERTY_TYPE_CD.PENDING_DATE);
        orderMetaDV.add(orderMetaD);
        pBaton.setOrderStatus(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE);
      } else {
        pBaton.setOrderStatus(RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION);
      }
    }
  }

  //------------------------------------------------------------------------------
  private void saveOrderPropertyData(OrderPipelineBaton pBaton) throws Exception {
    PreOrderData preOrdeD = pBaton.getPreOrderData();
    OrderData orderD = pBaton.getOrderData();
    String userName = orderD.getAddBy();

    PreOrderPropertyDataVector preOrderPropertyDV = pBaton.getPreOrderPropertyDataVector();
    OrderPropertyDataVector orderPropertyDV = new OrderPropertyDataVector();
    pBaton.setOrderPropertyDataVector(orderPropertyDV);
    for (Iterator iter = preOrderPropertyDV.iterator(); iter.hasNext(); ) {
      //String onote = (String)orderRequestNotes.get(ii);
      PreOrderPropertyData popD = (PreOrderPropertyData) iter.next();
      String propType = popD.getOrderPropertyTypeCd();
      if (!RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_ORDER_NOTE.equals(propType)
        ) {
        OrderPropertyData opD = OrderPropertyData.createValue();
        opD.setShortDesc(popD.getShortDesc());
        opD.setOrderPropertyTypeCd(propType);
        opD.setValue(popD.getValue());
        opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
        opD.setAddBy(orderD.getAddBy());
        opD.setModBy(orderD.getAddBy());
        orderPropertyDV.add(opD);
      }
    }

  }

  BillToData matchAccountBillTo(OrderPipelineBaton pBaton,
                                APIAccess pFactory,
                                AccountData pAccountData,
                                SiteData pSiteData) throws Exception {
    if (pAccountData == null || null == pBaton.getCustBillToData()) {
      return null;
    }
    Account actEjb = pFactory.getAccountAPI();
    return actEjb.lookupBillTo(pAccountData,
                               pBaton.getCustBillToData()
      );
  }

  //-------------------------------------------------------------------
  private void saveOrderBillToAddress(OrderPipelineBaton pBaton,
                                      APIAccess pFactory,
                                      AccountData pAccountData,
                                      SiteData pSiteData,
                                      Connection pCon) throws Exception {
    if (pAccountData == null) {
      return;
    }
    OrderData orderD = pBaton.getOrderData();
    String userName = orderD.getAddBy();
    
    OrderAddressData billto = pBaton.getBillToData();
    if (billto == null) {
        billto = OrderAddressData.createValue();
        pBaton.setBillToData(billto);
    }
    billto.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.BILLING);

    PropertyService propertyServiceBean;
    try {
      propertyServiceBean = pFactory.getPropertyServiceAPI();
    } catch (Exception e) {
      String msg = "EJB problem. ";
      throw new Exception(msg + e.getMessage());
    }

    // Check to see if there was a requested bill to address
    // for this order.
    if (saveOrderCustomerBillingAddress(pBaton)) {

      // A customer specific billing address was loaded.
      billto.setErpNum("");
      // Try to match the billing address specified to
      // a known billing address for this account.  If none
      // is found, the order must be handled manually.
      BillToData btd = matchAccountBillTo
                       (pBaton, pFactory, pAccountData, pSiteData);

      if (null != btd) {
        billto.setShortDesc(btd.getBusEntity().getShortDesc());
        billto.setErpNum(btd.getBusEntity().getErpNum());
        billto.setAddress1(btd.getBillToAddress().getAddress1());
        billto.setAddress2(btd.getBillToAddress().getAddress2());
        billto.setAddress3(btd.getBillToAddress().getAddress3());
        billto.setAddress4(btd.getBillToAddress().getAddress4());
        billto.setCity(btd.getBillToAddress().getCity());
        billto.setPostalCode(btd.getBillToAddress().getPostalCode());
        billto.setStateProvinceCd(btd.getBillToAddress().getStateProvinceCd());
        billto.setCountryCd(btd.getBillToAddress().getCountryCd());

        PhoneData ap = pAccountData.getPrimaryPhone();
        billto.setPhoneNum(ap.getPhoneNum());

        PhoneData afax = pAccountData.getPrimaryFax();
        billto.setFaxPhoneNum(afax.getPhoneNum());

        EmailData email = pAccountData.getPrimaryEmail();
        billto.setEmailAddress(email.getEmailAddress());
        billto.setEmailTypeCd(email.getEmailTypeCd());

        return;
      }

      pBaton.addError(pCon, OrderPipelineBaton.BILL_TO_ADDRESS_PROBLEM,
                      RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                      "pipeline.message.billToAddressProblem");
      return;
    }

    String makeShipToBillToS
      = propertyServiceBean.checkBusEntityProperty(pAccountData.getBusEntity().getBusEntityId(),
      RefCodeNames.PROPERTY_TYPE_CD.MAKE_SHIP_TO_BILL_TO);

    if (Utility.isSet(makeShipToBillToS) &&
        "T".equalsIgnoreCase(makeShipToBillToS.substring(0, 1))) { //Use site data
      if (pSiteData == null) {
        pBaton.addError(pCon, OrderPipelineBaton.NO_BILL_TO,
                        RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                        "pipeline.message.noBillTo");
      } else {
        billto.setShortDesc(pSiteData.getBusEntity().getShortDesc());
        billto.setErpNum(pSiteData.getBusEntity().getErpNum());
        billto.setAddress1(pSiteData.getSiteAddress().getAddress1());
        billto.setAddress2(pSiteData.getSiteAddress().getAddress2());
        billto.setAddress3(pSiteData.getSiteAddress().getAddress3());
        billto.setAddress4(pSiteData.getSiteAddress().getAddress4());
        billto.setCity(pSiteData.getSiteAddress().getCity());
        billto.setPostalCode(pSiteData.getSiteAddress().getPostalCode());
        billto.setStateProvinceCd(pSiteData.getSiteAddress().getStateProvinceCd());
        billto.setCountryCd(pSiteData.getSiteAddress().getCountryCd());

        PhoneDataVector sphones = pSiteData.getPhones();
        for (int i1 = 0; i1 < sphones.size(); i1++) {
          PhoneData pd = (PhoneData) sphones.get(i1);
          if (pd.getPhoneTypeCd().equals(
            RefCodeNames.PHONE_TYPE_CD.PHONE)) {
            billto.setPhoneNum(pd.getPhoneNum());
          } else if (pd.getPhoneTypeCd().equals(
            RefCodeNames.PHONE_TYPE_CD.FAX)) {
            billto.setFaxPhoneNum(pd.getPhoneNum());
          }
        }
      }
    } else { //Use account data
      billto.setShortDesc(pAccountData.getBusEntity().getShortDesc());
      billto.setErpNum(pAccountData.getBusEntity().getErpNum());
      billto.setAddress1(pAccountData.getBillingAddress().getAddress1());
      billto.setAddress2(pAccountData.getBillingAddress().getAddress2());
      billto.setAddress3(pAccountData.getBillingAddress().getAddress3());
      billto.setAddress4(pAccountData.getBillingAddress().getAddress4());
      billto.setCity(pAccountData.getBillingAddress().getCity());
      billto.setPostalCode(pAccountData.getBillingAddress().getPostalCode());
      billto.setStateProvinceCd(pAccountData.getBillingAddress().getStateProvinceCd());
      billto.setCountryCd(pAccountData.getBillingAddress().getCountryCd());

      PhoneData ap = pAccountData.getPrimaryPhone();
      billto.setPhoneNum(ap.getPhoneNum());

      PhoneData afax = pAccountData.getPrimaryFax();
      billto.setFaxPhoneNum(afax.getPhoneNum());

      EmailData email = pAccountData.getPrimaryEmail();
      billto.setEmailAddress(email.getEmailAddress());
      billto.setEmailTypeCd(email.getEmailTypeCd());
    }
  }

  //-------------------------------------------------------------------------
  private void saveOrderShipToAddress(OrderPipelineBaton pBaton,
                                      SiteData pSiteData) throws Exception {
    if (null == pSiteData) {
      return;
    }
    OrderData orderD = pBaton.getOrderData();
    String userName = orderD.getAddBy();

    OrderAddressData shipto = pBaton.getShipToData();
    if (shipto == null) {
        shipto = OrderAddressData.createValue();
        pBaton.setShipToData(shipto);
    }
    shipto.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);

    shipto.setShortDesc(pSiteData.getBusEntity().getShortDesc());
    shipto.setErpNum(pSiteData.getBusEntity().getErpNum());
    shipto.setAddress1(pSiteData.getSiteAddress().getAddress1());
    shipto.setAddress2(pSiteData.getSiteAddress().getAddress2());
    shipto.setAddress3(pSiteData.getSiteAddress().getAddress3());
    shipto.setAddress4(pSiteData.getSiteAddress().getAddress4());
    shipto.setCity(pSiteData.getSiteAddress().getCity());
    shipto.setPostalCode(pSiteData.getSiteAddress().getPostalCode());
    shipto.setStateProvinceCd(pSiteData.getSiteAddress().getStateProvinceCd());
    shipto.setCountryCd(pSiteData.getSiteAddress().getCountryCd());

    PhoneDataVector sphones = pSiteData.getPhones();
    for (int i1 = 0; i1 < sphones.size(); i1++) {
      PhoneData pd = (PhoneData) sphones.get(i1);
      if (pd.getPhoneTypeCd().equals(RefCodeNames.PHONE_TYPE_CD.PHONE)) {
        shipto.setPhoneNum(pd.getPhoneNum());
      } else if (pd.getPhoneTypeCd().equals(
        RefCodeNames.PHONE_TYPE_CD.FAX)) {
        shipto.setFaxPhoneNum(pd.getPhoneNum());
      }
    }
  }

  //---------------------------------------------------------------------------
  private CatalogData getCatlaog(OrderPipelineBaton pBaton, APIAccess pFactory, int pSiteId, Connection pCon) throws
    Exception {
    CatalogInformation catInfEjb = null;
    try {
      catInfEjb = pFactory.getCatalogInformationAPI();
    } catch (Exception exc) {
      String msg = "EJB problem. ";
      throw new Exception(msg + exc.getMessage());
    }
    CatalogData catalogD = catInfEjb.getSiteCatalog(pSiteId);
    if (catalogD == null) {
      pBaton.addError(pCon, OrderPipelineBaton.NO_ACTIVE_CATALOG_FOUND, null,
                      RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                      "pipeline.message.noActiveCatalogFound",
                      "" + pSiteId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
    }
    return catalogD;
  }

  private boolean saveOrderCustomerBillingAddress(OrderPipelineBaton pBaton) {
    //OrderRequestData orderReq = pBaton.getOrderRequestData();
    PreOrderAddressDataVector preOrderAddressDV =
      pBaton.getPreOrderAddressDataVector();

    boolean savedBillingInfo = false;
    for (Iterator iter = preOrderAddressDV.iterator(); iter.hasNext(); ) {
      PreOrderAddressData poaD = (PreOrderAddressData) iter.next();
      String addrType = poaD.getAddressTypeCd();

      if (RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_SHIPPING.equals(addrType)) {
        pBaton.setCustShipToData(createOrderAddressData(poaD));
      } else if (RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_BILLING.equals(addrType)) {
        pBaton.setCustBillToData(createOrderAddressData(poaD));
        savedBillingInfo = true;
      }
    }
    return savedBillingInfo;
  }

  private OrderAddressData createOrderAddressData(PreOrderAddressData source) {
    OrderAddressData oaD = OrderAddressData.createValue();
    oaD.setAddressTypeCd(source.getAddressTypeCd());
    oaD.setShortDesc(source.getShortDesc());
    oaD.setAddress1(source.getAddress1());
    oaD.setAddress2(source.getAddress2());
    oaD.setAddress3(source.getAddress3());
    oaD.setAddress4(source.getAddress4());
    oaD.setCity(source.getCity());
    oaD.setStateProvinceCd(source.getStateProvinceCd());
    oaD.setCountryCd(source.getCountryCd());
    oaD.setCountyCd(source.getCountyCd());
    oaD.setPostalCode(source.getPostalCode());
    return oaD;
  }

}
