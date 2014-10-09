/**
 * Title:        UserShopLogic
 * Description:  This is the business logic class for the
 * UserShopAction and UserShopForm.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */

package com.cleanwise.view.logic;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.axis.transport.http.HTTPConstants;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import Paymetric.XiPaySoap30.XiPayWSLocator;
import Paymetric.XiPaySoap30.XiPayWSSoapStub;
import Paymetric.XiPaySoap30.message.IPackets;
import Paymetric.XiPaySoap30.message.ITransactionHeader;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Catalog;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.FreightTable;
import com.cleanwise.service.api.session.History;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.OrderGuide;
import com.cleanwise.service.api.session.Pipeline;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.session.ShoppingServicesBean.ServiceFeeDetail;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.session.WorkOrder;
import com.cleanwise.service.api.util.CreditCardUtil;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.I18nUtil;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.TaxCalculationException;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccCategoryToCostCenterView;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.AddressDataVector;
import com.cleanwise.service.api.value.BusEntityFieldDataElement;
import com.cleanwise.service.api.value.BusEntityFieldsData;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.ConsolidatedCartView;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.CurrencyData;
import com.cleanwise.service.api.value.CustomerOrderRequestData;
import com.cleanwise.service.api.value.FiscalCalendarInfo;
import com.cleanwise.service.api.value.FiscalCalenderView;
import com.cleanwise.service.api.value.FiscalCalenderViewVector;
import com.cleanwise.service.api.value.FreightTableCriteriaData;
import com.cleanwise.service.api.value.FreightTableData;
import com.cleanwise.service.api.value.HistoryData;
import com.cleanwise.service.api.value.HistoryObjectData;
import com.cleanwise.service.api.value.HistorySecurityData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemMappingData;
import com.cleanwise.service.api.value.OrderAddOnChargeData;
import com.cleanwise.service.api.value.OrderAddOnChargeDataVector;
import com.cleanwise.service.api.value.OrderCreditCardData;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderDataVector;
import com.cleanwise.service.api.value.OrderFreightData;
import com.cleanwise.service.api.value.OrderFreightDataVector;
import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.OrderHandlingItemView;
import com.cleanwise.service.api.value.OrderHandlingItemViewVector;
import com.cleanwise.service.api.value.OrderHandlingView;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderItemJoinData;
import com.cleanwise.service.api.value.OrderItemJoinDataVector;
import com.cleanwise.service.api.value.OrderJoinData;
import com.cleanwise.service.api.value.OrderMetaDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.OrderRequestData;
import com.cleanwise.service.api.value.OutboundEDIRequestData;
import com.cleanwise.service.api.value.OutboundEDIRequestDataVector;
import com.cleanwise.service.api.value.PairView;
import com.cleanwise.service.api.value.ProcessOrderResultData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.PurchaseOrderData;
import com.cleanwise.service.api.value.PurchaseOrderDataVector;
import com.cleanwise.service.api.value.PurchaseOrderStatusCriteriaData;
import com.cleanwise.service.api.value.ReplacedOrderViewVector;
import com.cleanwise.service.api.value.ServiceTicketOrderRequestView;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.ShoppingCartDistData;
import com.cleanwise.service.api.value.ShoppingCartDistDataVector;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.ShoppingCartServiceData;
import com.cleanwise.service.api.value.ShoppingCartServiceDataVector;
import com.cleanwise.service.api.value.ShoppingControlData;
import com.cleanwise.service.api.value.ShoppingControlDataVector;
import com.cleanwise.service.api.value.ShoppingInfoData;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteDeliveryData;
import com.cleanwise.service.api.value.SiteDeliveryDataVector;
import com.cleanwise.service.api.value.WorkOrderData;
import com.cleanwise.service.api.value.WorkOrderDetailView;
import com.cleanwise.service.api.value.WorkOrderDetailViewVector;
import com.cleanwise.service.api.value.WorkOrderItemData;
import com.cleanwise.service.api.value.WorkOrderItemDetailView;
import com.cleanwise.service.api.value.WorkOrderItemDetailViewVector;
import com.cleanwise.service.api.value.WorkOrderSimpleSearchCriteria;
import com.cleanwise.service.apps.dataexchange.OutboundTranslate;
import com.cleanwise.view.forms.CheckoutForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.CurrencyFormat;
import com.cleanwise.view.utils.FormArrayElement;
import com.cleanwise.view.utils.RemoteWebClient;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;


/**
 * Class description.
 *
 */
public class CheckoutLogic {
  private static final Logger log = Logger.getLogger(CheckoutLogic.class);
  private static final BigDecimal ZERO = new BigDecimal(0);
  private static final String SHIP_TO_OVERRIDE_COMMENT = "Ship-to Override";
  private static final String SHIP_TO_OVERRIDE_ATTENTION = "Attn: ";

  protected static org.apache.axis.EngineConfiguration getEngineConfiguration(Boolean bDebug) {
      java.lang.StringBuffer sb = new java.lang.StringBuffer();
      sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
      sb.append("<deployment name=\"defaultClientConfig\"\r\n");
      sb.append("xmlns=\"http://xml.apache.org/axis/wsdd/\"\r\n");
      sb.append("xmlns:java=\"http://xml.apache.org/axis/wsdd/providers/java\">\r\n");
      sb.append("<transport name=\"http\" pivot=\"java:org.apache.axis.transport.http.CommonsHTTPSender\" />\r\n");
      sb.append("<transport name=\"local\" pivot=\"java:org.apache.axis.transport.local.LocalSender\" />\r\n");
      sb.append("<transport name=\"java\" pivot=\"java:org.apache.axis.transport.java.JavaSender\" />\r\n");


      if (true == bDebug){
          //----------------------------------------------------------------------
          //-- The following code dumps the axis payload to axis.log
          //- bad for production but here if needed
          log.info("DEBUG enabled! Payloads will be written to: ./axis.log");
          sb.append("<handler name=\"log\" type =\"java:org.apache.axis.handlers.LogHandler\"/>\r\n");
          sb.append("<globalConfiguration> \r\n");
          sb.append("<enableNamespacePrefixOptimization>true</enableNamespacePrefixOptimization>\r\n");
          sb.append("<requestFlow><handler type=\"log\"/></requestFlow>\r\n");
          sb.append("<responseFlow><handler type=\"log\"/></responseFlow>\r\n");
          sb.append("</globalConfiguration> \r\n");
      //----------------------------------------------------------------------
      }
      sb.append("</deployment>\r\n");
      org.apache.axis.configuration.XMLStringProvider config =
              new org.apache.axis.configuration.XMLStringProvider(sb.toString());
      return config;
  }

  public static ActionErrors paymetricAuthorization(HttpServletRequest request, CheckoutForm pForm) {

      String strEndPoint = System.getProperty("XiPay.URL");
      String strUsername = System.getProperty("XiPay.Domain") + "\\" + System.getProperty("XiPay.Username");
      String strPassword = System.getProperty("XiPay.Password");
      String strMerchantID = System.getProperty("XiPay.MerchantID");
      String strXIID = "";
      Boolean bDebug = true;
      Boolean bChunked = false;
      Integer iTimeOut = 60000;
      IPackets oPackets;
      ITransactionHeader oTransaction;
      IPackets oPackets_response;

      ActionErrors ae = new ActionErrors();
      HttpSession session = request.getSession();
      CustomerOrderRequestData orderReq = pForm.getOrderRequest();

      String currency = (String)session.getAttribute("paymetrics_currency");
      String reqAmount = (String)session.getAttribute("paymetrics_amount");
      int ccType = pForm.getXiPayCCType();
      int binRangeType = pForm.getXiPayBinRangeType();

      if (pForm.getXiPayCCToken() == null || "".equals(pForm.getXiPayCCToken())) {
//          String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.discretionaryCartUnavailable",null);
          String errorMess = "Cannot process Credit Card number at this time. Please try again later.";
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
      }
      if (ccType == 1000 && binRangeType == 6000) {
          ccType = 6000;
      }
      if (ccType == 5000 && binRangeType == 1000) {
          binRangeType = 5000;
      }
      if (ccType != binRangeType) {
//          String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.discretionaryCartUnavailable",null);
          String errorMess = "Credit Card number is incorrect. Please enter valid credit card number";
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
      }

      String ccTypeStr = "";
      if (ccType == 1000) {
          ccTypeStr = "DinersClub";
      } else if (ccType == 2000) {
          ccTypeStr = "BankCard";
      } else if (ccType == 3000) {
          ccTypeStr = "AmericanExpress";
      } else if (ccType == 4000) {
          ccTypeStr = "Visa";
      } else if (ccType == 5000) {
          ccTypeStr = "MasterCard";
      } else if (ccType == 6000) {
          ccTypeStr = "Discover";
      } else if (ccType == 7000) {
          ccTypeStr = "JCB";
      } else if (ccType == 8000) {
          ccTypeStr = "Laser";
      } else if (ccType == 9000) {
          ccTypeStr = "Maestro";
      } else if (ccType == 10000) {
          ccTypeStr = "Solo";
      } else if (ccType == 11000) {
          ccTypeStr = "Switch";
      }

      if ("".equals(ccTypeStr)) {
//          String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.discretionaryCartUnavailable",null);
          String errorMess = "Credit Card number is incorrect. Please enter valid credit card number";
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
      }


      try {
          log.info("Chunking: " + bChunked);
          log.info("Timeout: " + iTimeOut);
          log.info("Endpoint: " + strEndPoint);
          log.info("UserName: " + strUsername);
          //log.info("Password: " + strPassword);
          log.info("XIID: " + strXIID);
              oPackets = null;
              oTransaction = null;
              oPackets_response = null;
              org.apache.axis.EngineConfiguration ntlmEngine = getEngineConfiguration(bDebug);
              XiPayWSLocator oLoc = new XiPayWSLocator(ntlmEngine);
              XiPayWSSoapStub oPort = null;

              oPackets = new IPackets();
              oTransaction = new ITransactionHeader();
              oLoc.setXiPayWSSoapEndpointAddress(strEndPoint);

              oPort = (XiPayWSSoapStub) oLoc.getXiPayWSSoap();
              ((XiPayWSSoapStub) oPort).setUsername(strUsername);
              ((XiPayWSSoapStub) oPort).setPassword(strPassword);
              ((XiPayWSSoapStub) oPort).setTimeout(iTimeOut);
              ((XiPayWSSoapStub) oPort)._setProperty(HTTPConstants.HEADER_TRANSFER_ENCODING_CHUNKED, bChunked);

             ((XiPayWSSoapStub) oPort)._setProperty(org.apache.axis.MessageContext.HTTP_TRANSPORT_VERSION, org.apache.axis.transport.http.HTTPConstants.HEADER_PROTOCOL_V11);

              //oTransaction.setXIID(strXIID);
              oTransaction.setMerchantID(strMerchantID);
              oTransaction.setCurrencyKey(currency);
              oTransaction.setAmount(reqAmount);
              oTransaction.setCardType(ccTypeStr);
              oTransaction.setCardNumber(pForm.getXiPayCCToken());
              oTransaction.setCardExpirationDate(pForm.getCcExpMonth() + "/" + pForm.getCcExpYear());
              oTransaction.setCardHolderName(pForm.getCcPersonName());
              oTransaction.setCardHolderAddress1(pForm.getCcStreet1());
              oTransaction.setCardHolderAddress2(pForm.getCcStreet2());
              oTransaction.setCardHolderCity(pForm.getCcCity());
              oTransaction.setCardHolderState(pForm.getCcState());
              oTransaction.setCardHolderZip(pForm.getCcZipCode());

              oTransaction.setPacketOperation(1);
              //--Initialize Dates
              oTransaction.setAuthorizationDate(Calendar.getInstance());
              oTransaction.setBillingDate(Calendar.getInstance());
              oTransaction.setSettlementDate(Calendar.getInstance());
              oTransaction.setOrderDate(Calendar.getInstance());
              oTransaction.setShippingCaptureDate(Calendar.getInstance());
              oTransaction.setCreationDate(Calendar.getInstance());
              oTransaction.setLastModificationDate(Calendar.getInstance());
              oTransaction.setCaptureDate(Calendar.getInstance());

              oPackets.setPackets(new ITransactionHeader[1]);
              oPackets.setCount(1);
              oPackets.setXipayvbresult(false);
              oPackets.getPackets()[0] = oTransaction;
              oPackets_response = oPort.soapOp(oPackets);

              log.info("Status Code: " + oPackets_response.getPackets()[0].getStatusCode());
              log.info("Transaction Id: " + oPackets_response.getPackets()[0].getTransactionID());
              log.info("Message: " + oPackets_response.getPackets()[0].getMessage() );

              int statusCode = oPackets_response.getPackets()[0].getStatusCode();
              if (statusCode != 100) {
                  ae.add("error", new ActionError("error.simpleGenericError", "Authorization Error: " + oPackets_response.getPackets()[0].getMessage()));
                  return ae;
              }
              String respAmount = oPackets_response.getPackets()[0].getAmount();
              log.info("respAmount: " + respAmount);
              double respAmountD = Double.parseDouble(respAmount);
              double reqAmountD = Double.parseDouble(reqAmount);
              if (respAmountD != reqAmountD) {
            	  log.info("respAmount !!!!!!!!");
//                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.discretionaryCartUnavailable",null);
                String errorMess = "Amount authorized on credit card does not equal amount requested. Please enter valid credit card";
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
              }

              orderReq.setCcNumber(oPackets_response.getPackets()[0].getCardNumber());
              orderReq.setCcType(oPackets_response.getPackets()[0].getCardType());
              String[] ccExpDate = oPackets_response.getPackets()[0].getCardExpirationDate().split("/");
              orderReq.setCcExpMonth(Integer.parseInt(ccExpDate[0]));
              orderReq.setCcExpYear(Integer.parseInt(ccExpDate[1]));
              orderReq.setCcBuyerName(oPackets_response.getPackets()[0].getCardHolderName());
              orderReq.setCcStreet1(oPackets_response.getPackets()[0].getCardHolderAddress1());
              orderReq.setCcStreet2(oPackets_response.getPackets()[0].getCardHolderAddress2());
              orderReq.setCcCity(oPackets_response.getPackets()[0].getCardHolderCity());
              orderReq.setCcState(oPackets_response.getPackets()[0].getCardHolderState());
              orderReq.setCcPostalCode(oPackets_response.getPackets()[0].getCardHolderZip());
              orderReq.setCcPaymetricCurrency(oPackets_response.getPackets()[0].getCurrencyKey());
              orderReq.setCcPaymetricAvsStatus(oPackets_response.getPackets()[0].getAVSCode());
              orderReq.setCcPaymetricAvsAddress(oPackets_response.getPackets()[0].getAVSAddress());
              orderReq.setCcPaymetricAvsZipCode(oPackets_response.getPackets()[0].getAVSZipCode());
              orderReq.setCcPaymetricAmount(new BigDecimal(respAmount));
              log.info("orderReq.getCcPaymetricAmount(): " + orderReq.getCcPaymetricAmount());
              orderReq.setCcPaymetricAuthRefCode(oPackets_response.getPackets()[0].getAuthorizationReferenceCode());
              orderReq.setCcPaymetricAuthCode(oPackets_response.getPackets()[0].getAuthorizationCode());
              orderReq.setCcPaymetricResponseCode(oPackets_response.getPackets()[0].getResponseCode());
              orderReq.setCcPaymetricAuthDate(oPackets_response.getPackets()[0].getAuthorizationDate().getTime());
              SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ssss");
              orderReq.setCcPaymetricAuthTime(tf.parse(oPackets_response.getPackets()[0].getAuthorizationTime()));
              orderReq.setCcPaymetricTransactionId(oPackets_response.getPackets()[0].getTransactionID());
              orderReq.setCcPaymetricMessage(oPackets_response.getPackets()[0].getMessage());

      } catch (Exception ex) {
          log.error(ex);
          ex.printStackTrace();
          ae.add("error", new ActionError("error.simpleGenericError", "Authorization Error: " + ex.getMessage()));
          return ae;
      }
      return ae;

  }

    public static void setupShippingMessage(CheckoutForm pForm,
                                          SiteData pSite,
                                          CatalogData pSiteCatalog) {

    PropertyDataVector props = pSite.getMiscProperties();
    pForm.setShippingMessage("");

    Iterator itr = props.iterator();
    while (itr.hasNext()) {
      PropertyData prop = (PropertyData) itr.next();
      if (prop.getShortDesc().equals
          (RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG)
        ) {
        pForm.setShippingMessage(prop.getValue());
      }
    }

    if (pForm.getShippingMessage().length() == 0) {
      // No site specific message is set.  Get the
      // shipping message from the catalog.
      pForm.setShippingMessage
        (pSiteCatalog.getShippingMessage());
    }
  }

  public static ActionErrors init(HttpServletRequest request, CheckoutForm pForm) {

	pForm.init();

	log.info("**SVC: CheckoutLogic.init");
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if (factory == null) {
      ae.add("error", new ActionError("error.systemError", "No Ejb access"));
      return ae;
    }
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if (appUser == null) {
      ae.add("error", new ActionError("error.systemError", "No " + Constants.APP_USER + " session object found"));
      return ae;
    }
    pForm.setUserId(appUser.getUserId());
    pForm.setIsStateProvinceRequired(appUser.getUserStore().isStateProvinceRequired());

    SiteData site = appUser.getSite();
    if (site == null) {
      ae.add("error", new ActionError("error.systemError", "No current site information found"));
      return ae;
    }
    pForm.setSite(site);
    CatalogInformation catinfoEjb = null;
    try {
      catinfoEjb = factory.getCatalogInformationAPI();
    } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error", new ActionError("error.systemError",
                                      "No CatalogInformation API Access"));
      return ae;
    }
    CatalogData siteCat = null;
    int siteid = site.getSiteId();
    try {
      siteCat = catinfoEjb.getSiteCatalog(siteid);
    } catch (Exception exc) {
      ae.add("error", new ActionError
             ("error.systemError",
              "No catalog found for site:" + siteid));
      return ae;
    }

    setupShippingMessage(pForm, site, siteCat);

    AddressData siteAddress = site.getSiteAddress();
    if (siteAddress == null) {
      ae.add("error", new ActionError("error.systemError", "No current site address information found"));
      return ae;
    }
    Account accountEjb = null;
    try {
      accountEjb = factory.getAccountAPI();
    } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error", new ActionError("error.systemError", "No account API Access"));
      return ae;
    }
    AccountData account = null;
    try {
      account = accountEjb.getAccountForSite(site.getSiteId());
      pForm.setAccount(account);

      String allowAlternateShipTo = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ALTERNATE_SHIP_TO_ADDRESS);
      pForm.setAllowAlternateShipTo(Utility.isTrue(allowAlternateShipTo, false));
      if (appUser.canEditBillTo()) {
        // Get the requested bill to for this user.
        pForm.setRequestedBillToAddress
          (getLastBillTo(appUser.getUserId(), site.getSiteId()));

      }
    } catch (Exception exc) {
      ae.add("error", new ActionError("error.systemError", "Can't pickup account information from database"));
      return ae;
    }

    ShoppingServices shoppingServEjb = null;
    try {
      shoppingServEjb = factory.getShoppingServicesAPI();
    } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error", new ActionError("error.systemError", "No shopping services API Access"));
      return ae;
    }

    FreightTable freightTableEjb = null;
    try {
        freightTableEjb = factory.getFreightTableAPI();
    } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
        ae.add("error", new ActionError("error.systemError", "No freight table API Access"));
        return ae;
    }

    AddressData accountAddress = account.getBillingAddress();
    if (accountAddress == null) {
      ae.add("error", new ActionError("error.systemError", "No current account address information found"));
      return ae;
    }

    if (appUser.getSite() != null) {
      SelectShippingAddressLogic.setShoppingSessionObjects
        (session, appUser);
    }
    if (ShopTool.isModernInventoryShopping(request)
              && !ShopTool.hasDiscretionaryCartAccessOpen(request)) {
          String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.discretionaryCartUnavailable", null);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
    }

    ShoppingCartData shoppingCartD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
    if (shoppingCartD == null) {
      ae.add("error", new ActionError("error.systemError", "No " + Constants.SHOPPING_CART + " session object found"));
      return ae;
    }

    ShopTool.initShoppingForm(session);

    Integer contractIdI = (Integer) session.getAttribute(Constants.CONTRACT_ID);
    BigDecimal freightAmt = new BigDecimal(0);
    BigDecimal handlingAmt = new BigDecimal(0);
    BigDecimal fuelSurchargeAmt = new BigDecimal(0);
    BigDecimal smallOrderFee = new BigDecimal(0);
    BigDecimal discountAmt = new BigDecimal(0);

    BigDecimal cartAmt = new BigDecimal(shoppingCartD.getItemsCost());

    cartAmt = cartAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
    pForm.setCartAmt(cartAmt);
    int contractId = 0;
    if (contractIdI != null) {
      contractId = contractIdI.intValue();
    }
    log.info("***CheckoutLogic: contractId = " + contractId);
    if (contractId > 0){
    	try {
    		FreightTableData currentFreightTable = freightTableEjb.getFreightTableByContractId(contractId);
    		log.info("**********SVC: currentFreightTable = " + currentFreightTable); // SVC
    		pForm.setFreightByContract(currentFreightTable != null);
    	} catch (RemoteException exc) {
    		ae.add("error", new ActionError("error.systemError", exc.getMessage()));
    		exc.printStackTrace();
    		return ae;
    	}
    }else{
    	pForm.setFreightByContract(false);
    }

    //Freight
    OrderHandlingItemViewVector frItems = new OrderHandlingItemViewVector();

    List cartItems = shoppingCartD.getItems();

    for (int ii = 0; ii < cartItems.size(); ii++) {
      ShoppingCartItemData cartItem = (ShoppingCartItemData) cartItems.get(ii);
      OrderHandlingItemView frItem = OrderHandlingItemView.createValue();
      frItem.setItemId(cartItem.getProduct().getProductId());
      BigDecimal priceBD = new BigDecimal(cartItem.getPrice());
      priceBD.setScale(2, BigDecimal.ROUND_HALF_UP);
      frItem.setPrice(priceBD);
      frItem.setQty(cartItem.getQuantity());
      BigDecimal weight = null;
      String weightS = cartItem.getProduct().getShipWeight();
      try {
        weight = new BigDecimal(weightS);
      } catch (Exception exc) {}
      frItem.setWeight(weight);
      frItems.add(frItem);
    }
    OrderHandlingView frOrder = OrderHandlingView.createValue();
    frOrder.setTotalHandling(new BigDecimal(0));
    frOrder.setTotalFreight(new BigDecimal(0));
    frOrder.setContractId(contractId);
    int accountId = appUser.getSite().getAccountBusEntity().getBusEntityId();
    frOrder.setAccountId(accountId);
    frOrder.setSiteId(shoppingCartD.getSite().getBusEntity().getBusEntityId());
    frOrder.setAmount(cartAmt);
    frOrder.setWeight(new BigDecimal(0));
    frOrder.setItems(frItems);

	log.info("**********SVC: frOrder = " + frOrder); // SVC

    /// Create map to catch the proper 'Freight Table' for every distributor
    TreeMap<Integer, FreightTableData> distFreightTables =
        new TreeMap<Integer, FreightTableData>();
    /// Create map to catch the proper 'Discount Table' for every distributor
    TreeMap<Integer, FreightTableData> distDiscountTables =
        new TreeMap<Integer, FreightTableData>();

    /// Filling of the maps 'distFreightTables' and 'distDiscountTables' by distributors id
    if (cartItems != null && cartItems.size() > 0) {
        for (int i = 0; i < cartItems.size(); ++i) {
            ShoppingCartItemData item = (ShoppingCartItemData)cartItems.get(i);
            if (item == null) {
                continue;
            }
            if (item.getProduct() == null) {
                continue;
            }
            if (item.getProduct().getCatalogDistributor() == null) {
                continue;
            }
            Integer distributorId =
                new Integer(item.getProduct().getCatalogDistributor().getBusEntityId());
            distFreightTables.put(distributorId, null);
            distDiscountTables.put(distributorId, null);
        }
    }

    /// Calculation of Catalog Id
    int storeId = appUser.getUserStore().getStoreId();
    ContractData contractData = appUser.getSite().getContractData();
    int catalogId = 0;
    if (contractData != null) {
        catalogId = contractData.getCatalogId();
    }

    log.info("### [CheckoutLogic.init] storeId: " + storeId + ", catalogId: " + catalogId);

    /// Filling of the maps 'distFreightTables' and 'distDiscountTables' by 'Freight Tables' and by 'Discount Tables'
    try {
        calculateFreightTablesForDistributors(freightTableEjb, storeId, catalogId, distFreightTables);
        calculateDiscountTablesForDistributors(freightTableEjb, storeId, catalogId, distDiscountTables);
    } catch (Exception ex) {
        ae.add("error", new ActionError("error.systemError", ex.getMessage()));
        ex.printStackTrace();
        return ae;
    }

    /// Search any not empty 'Freight Table' in the map 'distFreightTables'
    boolean existsFreightTableForDistributors = false;
    if (distFreightTables.size() > 0) {
        Collection<FreightTableData> values = distFreightTables.values();
        Iterator<FreightTableData> iterator = values.iterator();
        while (iterator.hasNext()) {
            FreightTableData table = (FreightTableData)iterator.next();
            if (table != null) {
                existsFreightTableForDistributors = true;
                break;
            }
        }
    }
    /// Search any not empty 'Discount Table' in the map 'distDiscountTables'
    boolean existsDiscountTableForDistributors = false;
    if (distDiscountTables.size() > 0) {
        Collection<FreightTableData> values = distDiscountTables.values();
        Iterator<FreightTableData> iterator = values.iterator();
        while (iterator.hasNext()) {
            FreightTableData table = (FreightTableData)iterator.next();
            if (table != null) {
                existsDiscountTableForDistributors = true;
                break;
            }
        }
    }

    ///
    boolean useCatalogForCharges = true;
    if (existsFreightTableForDistributors || existsDiscountTableForDistributors) {
        useCatalogForCharges = false;
    }

    log.info("### [CheckoutLogic.init] useCatalogForCharges: " + useCatalogForCharges);

    if (useCatalogForCharges) {
        try {
            frOrder = shoppingServEjb.calcTotalFreightAndHandlingAmount(frOrder);
            freightAmt = frOrder.getTotalFreight();
            log.info("***************SVC: freightAmt1 = " + freightAmt);
            handlingAmt = frOrder.getTotalHandling();
            log.info("***************SVC: handlingAmt1 = " + handlingAmt);
            fuelSurchargeAmt = shoppingServEjb.getChargeAmtByCode(contractId,cartAmt,frOrder,RefCodeNames.CHARGE_CD.FUEL_SURCHARGE);
            log.info("***************SVC: fuelSurchargeAmt = " + fuelSurchargeAmt);
            smallOrderFee = shoppingServEjb.getChargeAmtByCode(contractId,cartAmt,frOrder,RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE);
            //select * from  clw_pre_order where order_num in ( '348616', '348615')  freightAmt = shoppingServEjb.getFreightAmt(contractId,cartAmt,new BigDecimal(0));
            //  handlingAmt = shoppingServEjb.getHandlingAmt(contractId,cartAmt,new BigDecimal(0));
            log.info("***************SVC: cartAmt = " + cartAmt);
            // Discount calculation
            discountAmt = shoppingServEjb.getDiscountAmt(contractId, cartAmt, frOrder); // SVC: new => calculate discount
        } catch (RemoteException exc) {
            ae.add("error", new ActionError("error.systemError", exc.getMessage()));
            exc.printStackTrace();
            return ae;
        }
        discountAmt = discountAmt.setScale(2, BigDecimal.ROUND_HALF_UP); // SVC: new
        log.info("***************SVC: discountAmt from the DB = " + discountAmt);
        //discountAmt = -discountAmt.abs(); // prepare negative discount value to be shown on the screen - doesn't work
        // prepare negative discount value to be shown on the screen
        BigDecimal zeroValue = new BigDecimal(0);

        if ( discountAmt.compareTo(zeroValue)>0 ) {
            discountAmt = discountAmt.negate();
        }

        log.info("***************SVC: discountAmt for the screen = " + discountAmt);

        pForm.setDiscountAmt(discountAmt); // SVC: new

        freightAmt = freightAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
        log.info("***************SVC: freightAmt for the screen = " + freightAmt);
        pForm.setFreightAmt(freightAmt);

        handlingAmt = handlingAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
        log.info("***************SVC: handlingAmt for the screen = " + handlingAmt);
        pForm.setHandlingAmt(handlingAmt);

        smallOrderFee = smallOrderFee.setScale(2, BigDecimal.ROUND_HALF_UP);
        pForm.setSmallOrderFeeAmt(smallOrderFee);

        fuelSurchargeAmt = fuelSurchargeAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
        log.info("***************SVC: fuelSurchargeAmt for the screen = " + fuelSurchargeAmt);
        pForm.setFuelSurchargeAmt(fuelSurchargeAmt);

        log.info("### [CheckoutLogic.init] Total (by catalog)" +
                        "  freightAmt: " + pForm.getFreightAmt() +
                        ", handlingAmt: " + pForm.getHandlingAmt() +
                        ", fuelSurchargeAmt: " + pForm.getFuelSurchargeAmt() +
                        ", smallOrderFee: " + pForm.getSmallOrderFeeAmt() +
                        ", discountAmt: " + pForm.getDiscountAmt());
    }

      //Rush order charge
    OrderData prevOrderD = shoppingCartD.getPrevOrderData();
    BigDecimal rushOrderCharge = shoppingCartD.getPrevRushCharge();
    if((rushOrderCharge==null || rushOrderCharge.doubleValue()<0.0001) && prevOrderD != null) {
        rushOrderCharge = prevOrderD.getTotalRushCharge();
    } else {
        String rushOrderChargeS = ShopTool.getRushOrderCharge(request);
        if(Utility.isSet(rushOrderChargeS)) {
            try {
                rushOrderCharge = new BigDecimal(rushOrderChargeS.trim());
            } catch (Exception exc) {
            }
        }
    }
    String rushOrderChargeS = "";
    if(rushOrderCharge!=null && rushOrderCharge.doubleValue()>=0.01) {
        rushOrderChargeS = rushOrderCharge.setScale(2,BigDecimal.ROUND_HALF_UP).toString();
    }
    pForm.setRushChargeAmtString(rushOrderChargeS);

    log.info("init() ***************SVC: shoppingCartD.getOrderBudgetTypeCd() = " + shoppingCartD.getOrderBudgetTypeCd());
// MANTA-300
    SessionDataUtil sessionData = Utility.getSessionDataUtil(request);

    if (RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCartD.getOrderBudgetTypeCd()) ||
    	(sessionData != null && sessionData.isExcludeOrderFromBudget() )	) {
        pForm.setBypassBudget(true);
      } else {
        pForm.setBypassBudget(false);
      }
    log.info("init() ***************SVC: pForm.isBypassBudget() = " + pForm.isBypassBudget());
    
    // set budget period choices. Make sure categories are configured to Cost Centers and account has fiscal Calender
    // Should return current + next period. If current is last period, do not set the choices
    if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.CHANGE_ORDER_BUDGET_PERIOD)) {
    	List<LabelValueBean> budgetPeriodChoices = null;
    	AccCategoryToCostCenterView categoryToCostCenterV = null;
    	try {
    		categoryToCostCenterV = SessionTool.getCategoryToCostCenterView(session, site.getSiteId(), 0);
		} catch (Exception exc) {
			ae.add("error", new ActionError("error.systemError", exc.getMessage()));
            exc.printStackTrace();
            return ae;
		}
		if (categoryToCostCenterV != null){
			FiscalCalenderView fcV;
			try {
				fcV = accountEjb.getCurrentFiscalCalenderV(accountId);
			} catch (RemoteException e) {
				ae.add("error", new ActionError("error.systemError", e.getMessage()));
			    return ae;
			}
			if (fcV != null){
				budgetPeriodChoices = new ArrayList<LabelValueBean>();  
		    	String defaultDateFormat = ClwI18nUtil.getDatePattern(request);
		    	SimpleDateFormat tf = new SimpleDateFormat(defaultDateFormat);
		    	
		        int budgetYear = fcV.getFiscalCalender().getFiscalYear();
		        FiscalCalendarInfo fiscalInfo = new FiscalCalendarInfo(fcV);
		        
		    	int budgetPeriod = site.getCurrentBudgetPeriod();
		    	FiscalCalendarInfo.BudgetPeriodInfo periodInfo = fiscalInfo.getBudgetPeriod(budgetPeriod);
		    	String temp = "Period " + budgetPeriod + " (" + tf.format(periodInfo.getStartDate())+")";
		    	LabelValueBean lvBean = new LabelValueBean(temp, budgetYear+":" + budgetPeriod);
		    	budgetPeriodChoices.add(lvBean);
		    	periodInfo = fiscalInfo.getBudgetPeriod(budgetPeriod+1);
		    	if (periodInfo != null){ // get next period of same budget year
		    		budgetPeriod++;
		    		temp = "Period " + budgetPeriod + " (" + tf.format(periodInfo.getStartDate())+")";
		    		lvBean = new LabelValueBean(temp, budgetYear+":" + budgetPeriod);
		        	budgetPeriodChoices.add(lvBean);
		    	} else { // get first period of next budget year
					try {
						budgetYear++;
						FiscalCalenderViewVector fcCollection = accountEjb.getFiscalCalCollection(accountId);
						for (Object aFcCollection : fcCollection) {
							fcV = ((FiscalCalenderView) aFcCollection);
				            if (fcV.getFiscalCalender().getFiscalYear() == budgetYear){
				            	fiscalInfo = new FiscalCalendarInfo(fcV);
				            	budgetPeriod = 1;
				            	periodInfo = fiscalInfo.getBudgetPeriod(budgetPeriod);
				            	temp = "Period " + budgetPeriod + " (" + tf.format(periodInfo.getStartDate())+")";
				        		lvBean = new LabelValueBean(temp, budgetYear+":" + budgetPeriod);
				        		budgetPeriodChoices.add(lvBean);
				            }
						}   
					} catch (RemoteException e) {
						ae.add("error", new ActionError("error.systemError", e.getMessage()));
					    return ae;
					}	
		    	}
			}	        
		}  
		if (budgetPeriodChoices == null || budgetPeriodChoices.size()<2)
			pForm.setBudgetPeriodChoices(null);
        else
        	pForm.setBudgetPeriodChoices(budgetPeriodChoices);
    }

    // getting list of checkout property fields from account property
    try {
	  List<BusEntityFieldDataElement> checkoutFields = getCheckoutFieldProperties(factory, accountId);
	  pForm.setCheckoutFieldProperties(checkoutFields);
    }catch (RemoteException exc) {
		ae.add("error", new ActionError("error.systemError", exc.getMessage()));
		exc.printStackTrace();
		return ae;
	}

    //New Way of accessing Service Tickets Info
    SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
    if(sessionDataUtil.isRemoteAccess()) {
    	pForm.setOrderServiceTicketNumbers(sessionDataUtil.getServiceTicketNumbers());
    }
    

    //Calculate Service Fee if applicable
    String addServiceFee = appUser.getUserAccount().getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADD_SERVICE_FEE);
    BigDecimal serviceFee = new BigDecimal(0);
    if(addServiceFee.equals("true") && contractId > 0){
    	try {
    		HashMap serviceFeeDetMap = shoppingServEjb.calculateServiceFee(contractId,cartItems,accountId);

    		if(serviceFeeDetMap!=null && serviceFeeDetMap.size()>0){
    			pForm.setServiceFeeDetail(serviceFeeDetMap);
    			//Calculate total service fee
    			for (int s = 0;s < cartItems.size();s++) {
    				ShoppingCartItemData sData = (ShoppingCartItemData)cartItems.get(s);
    				int qty = sData.getQuantity();
    				Integer item = new Integer(sData.getItemId());

    				ServiceFeeDetail finalDet = (ServiceFeeDetail)serviceFeeDetMap.get(item);
    				if(finalDet!=null){
    					BigDecimal amt = finalDet.getAmount();
    					BigDecimal totAmt = amt.multiply(new BigDecimal(qty));
    					serviceFee = serviceFee.add(totAmt);
    				}
    			}
    			log.info("Total Service Fee: "+serviceFee);
    		}

    		if(serviceFee!=null && serviceFee.compareTo(new BigDecimal(0))>0){
    			pForm.setServiceFeeAmt(serviceFee);
    			pForm.setServiceFeeAmtString(serviceFee.toString());
    		}
    	}catch (RemoteException exc) {
    		ae.add("error", new ActionError("error.systemError", exc.getMessage()));
    		exc.printStackTrace();
    		return ae;
    	}
    }

    //Set distributor inventory info
    String showDistInventory = ShopTool.getShowDistInventoryCode(request);
    if (RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_FLAG.equals(showDistInventory) ||
        RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_QUANTITIES.equals(showDistInventory)) {
      for (Iterator iter = cartItems.iterator(); iter.hasNext(); ) {
        ShoppingCartItemData cartItem = (ShoppingCartItemData) iter.next();
        ItemMappingData distMappingD = cartItem.getProduct().getCatalogDistrMapping();
        if (distMappingD != null) {
          String distSku = distMappingD.getItemNum();
          if (distSku != null) {
            int distInventoryQty = ShopTool
                                   .getDistInventory(request, distSku,
              distMappingD.getBusEntityId());
            cartItem.setDistInventoryQty(distInventoryQty);
          }
        }
      }
    }

    //List cartItems = shoppingCartD.getItems();
    orderItems(session, cartItems, distFreightTables, distDiscountTables, pForm);

    if (!useCatalogForCharges) {
        ShoppingCartDistDataVector cartDistVector = pForm.getCartDistributors();
        if (cartDistVector != null) {
            for (int i = 0; i < cartDistVector.size(); ++i) {
                ShoppingCartDistData cartDistData = (ShoppingCartDistData)cartDistVector.get(i);
                if (cartDistData != null) {
                    freightAmt = freightAmt.add(new BigDecimal(cartDistData.getDistFreightCost()));
                    handlingAmt = handlingAmt.add(new BigDecimal(cartDistData.getDistHandlingCost()));
                    fuelSurchargeAmt = fuelSurchargeAmt.add(new BigDecimal(cartDistData.getDistFuelSurchargeCost()));
                    smallOrderFee = smallOrderFee.add(new BigDecimal(cartDistData.getDistSmallOrderFeeCost()));
                    double discount = cartDistData.getDistImpliedDiscountCost();
                    if (discount > 0.0) {
                        discountAmt = discountAmt.add(new BigDecimal(-discount));
                    } else {
                        discountAmt = discountAmt.add(new BigDecimal(discount));
                    }

                    log.info("### [CheckoutLogic.init] Distributor: " + cartDistData.getDistId() +
                        ", freightAmt: " + cartDistData.getDistFreightCost() +
                        ", handlingAmt: " + cartDistData.getDistHandlingCost() +
                        ", fuelSurchargeAmt: " + cartDistData.getDistFuelSurchargeCost() +
                        ", smallOrderFee: " + cartDistData.getDistSmallOrderFeeCost() +
                        ", discountAmt: " + cartDistData.getDistImpliedDiscountCost());
                }
            }
        }

        discountAmt = discountAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
        pForm.setDiscountAmt(discountAmt);

        freightAmt = freightAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
        pForm.setFreightAmt(freightAmt);

        handlingAmt = handlingAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
        pForm.setHandlingAmt(handlingAmt);

        smallOrderFee = smallOrderFee.setScale(2, BigDecimal.ROUND_HALF_UP);
        pForm.setSmallOrderFeeAmt(smallOrderFee);

        fuelSurchargeAmt = fuelSurchargeAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
        pForm.setFuelSurchargeAmt(fuelSurchargeAmt);

        log.info("### [CheckoutLogic.init] Total (by distributor)" +
                        "  freightAmt: " + pForm.getFreightAmt() +
                        ", handlingAmt: " + pForm.getHandlingAmt() +
                        ", fuelSurchargeAmt: " + pForm.getFuelSurchargeAmt() +
                        ", smallOrderFee: " + pForm.getSmallOrderFeeAmt() +
                        ", discountAmt: " + pForm.getDiscountAmt());
    }

    recalculateDependencies(request, pForm, true);

    if (shoppingCartD.getWorkOrderItem() != null && shoppingCartD.getWorkOrderItem().getWorkOrderId() > 0) {
        WorkOrder woEjb = null;
        try {
            woEjb = factory.getWorkOrderAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No Work Order API Access"));
            return ae;
        }
        try {
            WorkOrderDetailView workOrderDetail = woEjb.getWorkOrderDetailView(shoppingCartD.getWorkOrderItem().getWorkOrderId());
            if (workOrderDetail != null && workOrderDetail.getWorkOrder() != null) {
                pForm.setWorkOrderNumber(workOrderDetail.getWorkOrder().getWorkOrderNum());
            }
        } catch (RemoteException e) {
            ae.add("error", new ActionError("error.systemError", "Error getting Detail View data for a WorkOrder"));
            return ae;
        }
    }
    /*//update the budget info for this session
    try{
    	ShopTool.saveBudgetSessionInfoForCurrentDiscretionaryCart(request);
    }catch(APIServiceAccessException exc){
    	ae.add("error", new ActionError("error.systemError", exc.getMessage()));
        exc.printStackTrace();
        return ae;
    }catch(RemoteException exc){
    	ae.add("error", new ActionError("error.systemError", exc.getMessage()));
        exc.printStackTrace();
        return ae;
    }*/
    String userTypeCd = appUser.getUser().getUserTypeCd();
    if (RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userTypeCd) ||
        RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userTypeCd) ||
        RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userTypeCd)) {
      String contactName = (String) session.getAttribute("OrderContactName");

      if (Utility.isSet(contactName)) {
        pForm.setOrderContactName(contactName);
      }

      String contactPhone = (String) session.getAttribute("OrderContactPhone");
      if (Utility.isSet(contactPhone)) {
        pForm.setOrderContactPhoneNum(contactPhone);
      }

      String contactEmail = (String) session.getAttribute("OrderContactEmail");
      if (Utility.isSet(contactEmail)) {
        pForm.setOrderContactEmail(contactEmail);
      }

      String orderMethod = (String) session.getAttribute("OrderMethod");
      if (Utility.isSet(orderMethod)) {
        pForm.setOrderOriginationMethod(orderMethod);
      }
  }
  //getting SiteDelivery information
  SiteDeliveryDataVector deliveryDataCollection = null;
  SiteDeliveryDataVector dataV = null;
  try {
    Site siteBean = factory.getSiteAPI();
    deliveryDataCollection = siteBean.getSiteDeliveryDateCollection(site.getSiteId());
    dataV = siteBean.getNextSiteDeliveryData(appUser.getSite().getSiteId());
  } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
       ae.add("error", new ActionError("error.systemError", "No Site Delivery Table API Access"));
       return ae;
  } catch (RemoteException exc) {
       ae.add("error", new ActionError("error.systemError", exc.getMessage()));
       exc.printStackTrace();
       return ae;
  } catch (DataNotFoundException exc) {
       ae.add("error", new ActionError("error.systemError", exc.getMessage()));
       exc.printStackTrace();
       return ae;
  }

  pForm.setDeliveryDataVector(deliveryDataCollection);
  /*-----------------------------------------*/
    ArrayList deliveryDateList = getDeliveryDates(request, deliveryDataCollection);
    if (deliveryDateList != null && deliveryDateList.size() > 0) {
      request.getSession().setAttribute("Delivery.dates.vector", deliveryDateList);
    }
    
    String deliveryDatesString = getDeliveryDateList(request, deliveryDataCollection);
    if(Utility.isSet(deliveryDatesString)){
    	pForm.setDeliveryDateList(deliveryDatesString);
    	
    	SiteDeliveryData nextDeliveryData = null;
    	
    	Date dt = new Date();
    	if (dataV != null && dataV.size() == 1){
			 nextDeliveryData  = (SiteDeliveryData)(dataV.get(0));
		 } else if (dataV.size() == 2) {
			 Date d1 = ((SiteDeliveryData)(dataV.get(0))).getCutoffSystemTime();
			 Date d2 = ((SiteDeliveryData)(dataV.get(1))).getCutoffSystemTime();
			 nextDeliveryData =( d1.before(d2) && dt.before(d1) ) ? (SiteDeliveryData)(dataV.get(0)) : (SiteDeliveryData)(dataV.get(1));
		 }
    	
		if (nextDeliveryData != null){
			
			if (nextDeliveryData.getDeliveryDate() != null) {
			
				String pattern = ClwI18nUtil.getDatePattern(request);
		    	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				pForm.setDeliveryDate(sdf.format(nextDeliveryData.getDeliveryDate()));
			}
		}
    }
    ae = checkoutWorkflow(request, pForm, ae, factory, appUser, cartItems);

    return ae;
  }

 private static ActionErrors checkoutWorkflow(HttpServletRequest request, CheckoutForm pForm, ActionErrors ae, APIAccess factory, CleanwiseUser appUser, List cartItems) {
	log.info("checkoutWorkflow() ====> Begin : pForm.isBypassBudget()=" + pForm.isBypassBudget());
	 
	OrderData orderD = OrderData.createValue();
    orderD.setUserId(pForm.getUserId());
    orderD.setAccountId(pForm.getAccount().getAccountId());
    orderD.setSiteId(pForm.getSiteId());
    orderD.setStoreId(appUser.getUserStore().getStoreId());
    orderD.setOriginalOrderDate(new Date());
    orderD.setOriginalAmount(new BigDecimal(pForm.getItemsAmt(request)).setScale(2, BigDecimal.ROUND_HALF_UP));
    orderD.setTotalPrice(orderD.getOriginalAmount());
    
    if (pForm.isBypassBudget()) {
    	orderD.setOrderBudgetTypeCd(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE);
    } else {
    	orderD.setOrderBudgetTypeCd(null);
    }    
    orderD.setOrderSourceCd(RefCodeNames.ORDER_SOURCE_CD.WEB);
    //setupFreightTable (request, pForm, null);

    ShoppingCartDistDataVector cartDistributors = pForm.getCartDistributors();
    String freightType = "";

    if (null != cartDistributors && 0 < cartDistributors.size()) {
        for (int i = 0; i < cartDistributors.size(); i++) {
            ShoppingCartDistData distD = (ShoppingCartDistData) cartDistributors.get(i);
            List impliedFreight = null;
            FreightTableCriteriaData selectedFreight = null;
            if (null != distD) {
               impliedFreight = distD.getDistFreightImplied();
               selectedFreight = distD.getDistSelectedFreight();
            }
            if (null != impliedFreight && 0 < impliedFreight.size()) {
               for (int j = 0; j < impliedFreight.size(); j++) {
                   FreightTableCriteriaData crit = (FreightTableCriteriaData) impliedFreight.get(j);
                   freightType = crit.getShortDesc();
               }
           }
           if (null != selectedFreight) {
        	   freightType = selectedFreight.getShortDesc();
          }
        }
      }

    OrderItemDataVector oiDV = new OrderItemDataVector();
    for (int ii = 0; ii < cartItems.size(); ii++) {
    	log.info(1);
      ShoppingCartItemData cartItem = (ShoppingCartItemData) cartItems.get(ii);
      OrderItemData oItem = OrderItemData.createValue();
      oItem.setItemId(cartItem.getProduct().getProductId());
      oItem.setCustItemSkuNum(cartItem.getProduct().getActualCustomerSkuNum());
      oItem.setManuItemSkuNum(cartItem.getProduct().getManufacturerSku());
      oItem.setDistItemSkuNum(cartItem.getProduct().getCatalogDistrMapping().getItemNum());
      oItem.setItemSkuNum(cartItem.getProduct().getSkuNum());
      oItem.setItemUom(cartItem.getProduct().getUom());
      oItem.setTotalQuantityOrdered(cartItem.getQuantity());
      oItem.setCustContractPrice(new BigDecimal(cartItem.getPrice()));
      oItem.setCostCenterId(cartItem.getProduct().getCostCenterId());
      
      String saleTypeCd = cartItem.getReSaleItem() ? RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE : RefCodeNames.ITEM_SALE_TYPE_CD.END_USE;
      oItem.setSaleTypeCd(saleTypeCd);
      BigDecimal price = oItem.getCustContractPrice().multiply(new BigDecimal(cartItem.getQuantity()));
	  price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
	  	  
      oiDV.add(oItem);
    }    
    
    Pipeline pipelineEjb = null;
    try {
    	pipelineEjb = factory.getPipelineAPI();
    } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error", new ActionError("error.systemError",
                                      "No Pipeline API Access"));
      return ae;
    }

    log.info("***SVC: orderD = " + orderD);

    try{
    	List warningMessages = pipelineEjb.processCheckoutRequest(orderD, oiDV, appUser.getUserRole(), freightType);
    	   log.info("***SVC: warningMessages = " + warningMessages);
    	pForm.setWarningMessages(warningMessages);
    } catch (Exception exc) {
        ae.add("error", new ActionError("error.systemError",
                                        exc.getMessage()));
        return ae;
    }
    return ae;
}


    /**********************************************************************/
    private static void orderItems(HttpSession session, List pItems,
        TreeMap<Integer, FreightTableData> distFreightTables,
        TreeMap<Integer, FreightTableData> distDiscountTables,
        CheckoutForm pForm) {

    //Arrage Items
    Integer sortByI = (Integer) session.getAttribute(Constants.SORT_BY);
    int sortBy = (sortByI != null) ? sortByI.intValue() : -1;
    pForm.setSortBy(sortBy);
    ShoppingCartData shoppingCartD = ShopTool.getCurrentShoppingCart(session);

    // get the ShoppingCartDistData, which has all distributor info, including
    // FreightTable, to do this, we need know the StoreID as well
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int storeId = appUser.getUserStore().getStoreId();


    ShoppingCartDistDataVector cartDistributorV = new ShoppingCartDistDataVector(
        shoppingCartD.getItems(), storeId, distFreightTables, distDiscountTables);

    switch (sortBy) {
    case Constants.ORDER_BY_CATEGORY:
      shoppingCartD.orderByCategory();
      cartDistributorV.orderByCategory();
      break;
    case Constants.ORDER_BY_CUST_SKU:
      shoppingCartD.orderBySku();
      cartDistributorV.orderBySku();
      break;
    case Constants.ORDER_BY_NAME:
      shoppingCartD.orderByName();
      cartDistributorV.orderByName();
      break;
    default:
      sortBy = Constants.ORDER_BY_CATEGORY;
      shoppingCartD.orderByCategory();
      cartDistributorV.orderByCategory();
    }

    pForm.setItems(shoppingCartD.getItems());
    pForm.setSortBy(sortBy);
    pForm.setStoreId(storeId);

    boolean isaMLAStore = RefCodeNames.STORE_TYPE_CD.MLA.equals
                          (appUser.getUserStore().getStoreType().getValue());


    if (!isaMLAStore) {
      pForm.setCartDistributors(cartDistributorV);
    }
  }


  /**********************************************************************/
  public static void orderServices
    (HttpSession session, List pServices, CheckoutForm pForm) {

    ShoppingCartData shoppingCartD = ShopTool.getCurrentShoppingCart(session);

    // get the ShoppingCartDistData, which has all distributor info, including
    // FreightTable, to do this, we need know the StoreID as well
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int storeId = appUser.getUserStore().getStoreId();

    ShoppingCartDistDataVector cartDistributorV =
      new ShoppingCartDistDataVector(shoppingCartD.getServices(), storeId,appUser.getSite().getContractData());

    pForm.setServices(shoppingCartD.getServices());
    pForm.setStoreId(storeId);

    boolean isaMLAStore = RefCodeNames.STORE_TYPE_CD.MLA.equals
                          (appUser.getUserStore().getStoreType().getValue());
    if (!isaMLAStore) {
      pForm.setCartDistributors(cartDistributorV);
    }
  }


  //****************************************************************************
   public static ActionErrors sort(HttpServletRequest request, CheckoutForm pForm) {
     ActionErrors ae = new ActionErrors();
     HttpSession session = request.getSession();
     ShoppingCartData shoppingCartD = (ShoppingCartData)
                                      session.getAttribute(Constants.SHOPPING_CART);
     int sortBy = pForm.getSortBy();
     session.setAttribute(Constants.SORT_BY, new Integer(sortBy));
     ae = init(request, pForm);
     return ae;
   }

  private static Date checkoutDbg(HttpServletRequest request,
                                  String pTag, Date pStartTime) {

    Date thisTime = new Date();
    if (pStartTime == null) pStartTime = thisTime;
    double dur = (double) thisTime.getTime() - pStartTime.getTime();
    dur /= 1000;

    SessionTool st = new SessionTool(request);
    return pStartTime;
  }

  //****************************************************************************
   public static ActionErrors placeOrder(HttpServletRequest request,
                                         CheckoutForm pForm) throws Exception {

     pForm.setComments(I18nUtil.getUtf8Str(pForm.getComments()));

     pForm.setRequestedBillToAddress(getUtf8(pForm.getRequestedBillToAddress()));

     Date stime = checkoutDbg(request, "placeOrder START", null);
     ActionErrors ae = new ActionErrors();
     HttpSession session = request.getSession();

     session.setAttribute("CheckoutForm", pForm);

     Integer contractId = (Integer)
                          session.getAttribute(Constants.CONTRACT_ID);
     if (null == contractId) {
       contractId = new Integer(0);
     }

     if(ShopTool.isModernInventoryShopping(request)
              &&!ShopTool.hasDiscretionaryCartAccessOpen(request)){
         String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.discretionaryCartUnavailable",null);
         ae.add("error", new ActionError("error.simpleGenericError", errorMess));
         return ae;
     }

     //pickup items
     ShoppingCartData shoppingCartD = ShopTool.getCurrentShoppingCart(session);
     if (shoppingCartD == null) {
       ae.add("error", new ActionError("error.systemError", "Shopping cart available."));
       return ae;
     }
     int originalCartId = shoppingCartD.getOrderGuideId();
     //Place order
     APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
     if (factory == null) {
       ae.add("error", new ActionError("error.systemError", "No Ejb access"));
       return ae;
     }
     Account accountEjb = null;
     try {
       accountEjb = factory.getAccountAPI();
     } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
       ae.add("error", new ActionError("error.systemError", "No account API Access"));
       return ae;
     }
     Order orderEjb = factory.getOrderAPI();
      try {
       orderEjb = factory.getOrderAPI();
     } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
       ae.add("error", new ActionError("error.systemError", "No order API Access"));
       return ae;
     }
     WorkOrder workOrderEjb = null;
      try {
       workOrderEjb = factory.getWorkOrderAPI();
     } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
       ae.add("error", new ActionError("error.systemError", "No work order API Access"));
       return ae;
     }
     //Compare items with shoppingCart
     List cartItems = shoppingCartD.getItems();
     if (cartItems == null || cartItems.isEmpty()) {
       String errorMess =
         ClwI18nUtil.getMessage(request, "shop.errors.orderCannotBeProcessedDueEmptyShoppingCart", null);
       ae.add(ActionErrors.GLOBAL_ERROR,
              new ActionError("error.simpleGenericError", errorMess));
       return ae;
     }

      ///
      log.info("before calling validateBudgetThreshold()");
      if (shoppingCartD != null) {
          ae = ShopTool.validateBudgetThreshold(request, shoppingCartD);
          if (!ae.isEmpty()) {
              return ae;
          }
      }

     CustomerOrderRequestData orderReq = new CustomerOrderRequestData();

     Date currDate = new Date();
     SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
     boolean pendingConsolidationFl = pForm.getPendingConsolidation();
     if (pendingConsolidationFl) {
       orderReq.setHoldUntilDate(sdf.format(Utility.MAX_DATE));
       orderReq.setOrderType(RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED);
     }

     orderReq.setContractId(contractId.intValue());

     log.info("before calling setupFreightTable()");
     //Set up freight request
     ae = setupFreightTable (request, pForm, orderReq);
     if(ae.size()>0) {
         return ae;
     }

    ///
    OrderAddOnChargeDataVector orderAddOnCharges =
        getFuelSurchargeAndSmallOrderFeeForOrder(pForm.getCartDistributors());
    if (orderAddOnCharges != null && orderAddOnCharges.size() > 0) {
        orderReq.setOrderAddOnChargeList(orderAddOnCharges);
    }

     // Send the freight specified in the UI.
     double frtamt = 0, hndlamt = 0;
     if (pForm.getFreightAmt() != null) {
       frtamt = pForm.getFreightAmt().doubleValue();
     }
     // Send the handling specified in the UI.
     if (pForm.getHandlingAmt() != null) {
       hndlamt = pForm.getHandlingAmt().doubleValue();
     }

     orderReq.setFreightCharge(frtamt);
     orderReq.setHandlingCharge(hndlamt);
     
     //Rush charge
     String rushOrderChargeS = pForm.getRushChargeAmtString();
     if(Utility.isSet(rushOrderChargeS)) {
         double rushOrderCharge = 0;
         try {
             rushOrderChargeS = rushOrderChargeS.trim();
             rushOrderCharge =
                     Double.parseDouble(rushOrderChargeS);
         } catch (Exception exc) {
             log.info("CheckoutLogic CCCCCCCCCCCCCCC inavlid rush order charge: "+rushOrderChargeS);
         }
         orderReq.setRushOrderCharge(rushOrderCharge);
     }
     log.info("before calling setupOrderAddOnChargeTable()");
     /*** Add Discount per Distributor info. to the OrderReq Object ***/
     ae = setupOrderAddOnChargeTable(request, pForm, orderReq);
     if(ae.size()>0) {
         return ae;
     }
     log.info("call setupOrderAddOnChargeTable() was successful !");
     /*****************************************************************/

     if (Utility.isSet(pForm.getRequestedShipDate())) {
       try {
         //Date aDate = sdf.parse(pForm.getRequestedShipDate());
         Date aDate = ClwI18nUtil.parseDateInp(request, pForm.getRequestedShipDate());

         if (aDate.compareTo(currDate) <= 0) {
           String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.deliveryBeforeCurrentDate", null);
           ae.add("error", new ActionError("error.simpleGenericError", errorMess));
           return ae;
         }
         orderReq.setOrderRequestedShipDate(aDate);
       } catch (Exception e) {
    	 Object[] args = new Object[1];
    	 args[0]=pForm.getRequestedShipDate();
    	 String errorMess = ClwI18nUtil.getMessage(request, "error.badDateFormat", args);
         ae.add("error", new ActionError("error.simpleGenericError", errorMess));
         return ae;
       }
     }
/*  Moved UP  to use recalculated freight && handle values in orderReq
     //Set up freigh request
     ae = setupFreightTable (request, pForm, orderReq);
     if(ae.size()>0) {
         return ae;
     }
*/
     CleanwiseUser appUser = (CleanwiseUser)
                             session.getAttribute(Constants.APP_USER);

     //Check site
     SiteData site = appUser.getSite();
     int siteId = site.getBusEntity().getBusEntityId();
     if (siteId != pForm.getSiteId()) {
       String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.pageExpired1", null);
       ae.add("error", new ActionError("error.simpleGenericError", errorMess));
       return ae;
     }
     pForm.setSite(site);
     //Get Account
     //XXX why not get out of session ala: AccountData account = appUser.getUserAccount()?
     AccountData account = accountEjb.getAccountForSite(siteId);

     if (account == null) {
       log.info("No account information found for site=" + siteId);
       ae.add("error", new ActionError("error.systemError", "No account information found"));
       return ae;
     }

     // check store properties ALLOW_PO_NUM_BY_VENDER
     boolean allowPoByVender =
             Utility.isTrue(PropertyUtil.getPropertyValue(appUser.getUserStore().getMiscProperties(),
    		 		RefCodeNames.PROPERTY_TYPE_CD.ALLOW_PO_NUM_BY_VENDER));

     if (allowPoByVender){
    	 ShoppingCartDistDataVector cartDistributors = pForm.getCartDistributors();
    	 Map poNumberByVendor = new HashMap();
    	 for (int i = 0; i < cartDistributors.size(); i++) {
    		 ShoppingCartDistData distD = (ShoppingCartDistData) cartDistributors.get(i);
    		 poNumberByVendor.put(distD.getDistributor().getErpNum(), distD.getPoNumber());
    	 }
    	 orderReq.setPoNumberByVendor(poNumberByVendor);
     }

    for (int ii = 0; ii < cartItems.size(); ii++) {
       ShoppingCartItemData cartItem =
         (ShoppingCartItemData) cartItems.get(ii);
       int itemId = cartItem.getProduct().getItemData().getItemId();
       int clw_skunum = cartItem.getProduct().getItemData().getSkuNum();

       if (cartItem.getIsaInventoryItem()) {
         String t2 = cartItem.getInventoryQtyOnHandString();
         if (t2 == null || t2.length() == 0) {
           String msg = " On Hand value not set for inventory item: " + cartItem.description();
           orderReq.addOrderNote(msg);
         }
       }

       int qty = cartItem.getQuantity();
       if (qty == 0)continue; // 0 qty line

       double price = cartItem.getPrice();
       boolean resaleFlag = cartItem.getReSaleItem();
       //log.info (" --- cartItem=" + cartItem);
       // Verify that the account is allowed to purchase resale items.
       if (!account.isAuthorizedForResale()) {
         // the account is not set up for resale.
         // Reset the resale flag as the account setting must win.
         if (true == resaleFlag) {
        	 log.info
             ("RESALE MISCONFIGURATION, account id=" +
              account.getAccountId() +
              " is not set up for resale but item id=" + itemId +
              " sku=" + clw_skunum + " is set up for resale.");
         }
         resaleFlag = false;
       }

       String distErpNum = "";
       if (allowPoByVender){
    	   if (cartItem.getProduct().getCatalogDistributor() != null) {
    		   distErpNum = cartItem.getProduct().getCatalogDistributor().getErpNum();
    	   }

       }
       orderReq.addItemEntry(ii + 1, itemId, clw_skunum,
                             qty, price,
                             cartItem.getProduct().getUom(),
                             cartItem.getProduct().getShortDesc(),
                             cartItem.getProduct().getPack(),
                             cartItem.getIsaInventoryItem(),
                             cartItem.getInventoryParValue(),
                             cartItem.getInventoryQtyOnHandString(),
                             resaleFlag,
                             distErpNum);
     }

     String paymentCd = RefCodeNames.PAYMENT_TYPE_CD.ACCOUNT;
     //Check user
     int userId = appUser.getUser().getUserId();
     orderReq.setUserId(userId);
     orderReq.setUserName(appUser.getUser().getUserName());
     if (userId != pForm.getUserId()) {
       String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.pageExpired", null);
       ae.add("error", new ActionError("error.simpleGenericError", errorMess));
       return ae;
     }

     String ccNumber = pForm.getCcNumber();
     //
     // If this is not a credit card purchase and the
     // user can specify their own bill to address, then
     // perform some basic address information checking.
     //
     if (!pForm.isaCcReq() && appUser.canEditBillTo()) {
       if (!pForm.hasRequestedBillToAddress()) {
         Object[] param = new Object[1];
         param[0] = ClwI18nUtil.getMessage(request, pForm.getRequestBillToCheckMessage(), null);
         String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.checkBillingAddress", param);
         ae.add("error", new ActionError("error.simpleGenericError", errorMess));
         return ae;
       }

       orderReq.setRequestedBillingAddress
         (pForm.getRequestedBillToAddress());
     }

     orderReq.setSiteId(siteId);
     orderReq.setAccountId(account.getBusEntity().getBusEntityId());

     orderReq.setCustomerSystemIdentifier((String) session.getAttribute(Constants.CUSTOMER_SYSTEM_ID));
     orderReq.setCustomerSystemURL((String) session.getAttribute(Constants.CUSTOMER_SYSTEM_URL));

     try {
       PropertyData orderMinimum = account.getOrderMinimum();
       double orderTotal = pForm.getItemsAmt(request);

       String minTot = orderMinimum.getValue();
       double minimumTotal;
       try {
         minimumTotal = CurrencyFormat.parse(minTot).doubleValue();
       } catch (ParseException pe) {
         // this should only happen if bad value in db
         minimumTotal = 0.0;
       }
       BigDecimal bigDminTot = new BigDecimal(minimumTotal);
       String formattedMinTot = CurrencyFormat.format(bigDminTot);

       if (orderTotal < minimumTotal) {
         Object[] param = new Object[1];
         param[0] = formattedMinTot;
         String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.totalLessThanMinimum", param);
         ae.add("error", new ActionError("error.simpleGenericError", errorMess));
         return ae;
       }

       if (appUser.canMakePurchases() == false) {
         String errorMess =
           ClwI18nUtil.getMessage(request, "shop.errors.billingMustBeOnAccountOrCreditCard", null);
         ae.add("error", new ActionError("error.simpleGenericError", errorMess));
         return ae;

       }

       //Check CC validity
       ccNumber = pForm.getCcNumber();
       if (pForm.isaCcReq()) {
         ae = ccValidation(pForm, request, appUser.getUserStore().isStateProvinceRequired());
         if (ae.size() > 0)
           return ae;
         //encrypt
         ae = ccEncrypt(pForm);
         if (ae.size() > 0)
           return ae;
         //
         paymentCd = pForm.getCcType();

         // copy the credit card information into the
         // order request object.
         orderReq.setCcNumber(pForm.getCcNumber());
         orderReq.setCcExpMonth(pForm.getCcExpMonth());
         orderReq.setCcExpYear(pForm.getCcExpYear());
         orderReq.setCcBuyerName(pForm.getCcPersonName());
         orderReq.setCcStreet1(pForm.getCcStreet1());
         orderReq.setCcStreet2(pForm.getCcStreet2());
         orderReq.setCcCity(pForm.getCcCity());
         orderReq.setCcState(pForm.getCcState());
         orderReq.setCcPostalCode(pForm.getCcZipCode());
         orderReq.setCcType(pForm.getCcType());

       }

       // other payment
       String otherPayment = pForm.getOtherPaymentInfo();
       if (pForm.getReqPmt().equals("Other") &&
           (otherPayment == null || otherPayment.trim().length() == 0)
         ) {
         // the buyer must enter something if other payment
         // is used to make a purchase.
         String errorMess =
           ClwI18nUtil.getMessage(request, "shop.errors.fieldRecordOfCallIsEmpty", null);
         ae.add("error", new ActionError("error.simpleGenericError", errorMess));
         return ae;
       }

       if (otherPayment != null && otherPayment.trim().length() > 0
           && appUser.getOtherPaymentFlag() == true) {
         orderReq.setOtherPaymentInfo(I18nUtil.getUtf8Str(otherPayment));
       }

       // Check PO number presence
       String poNum = pForm.getPoNumber();
       if (Utility.isSet(poNum) && !account.isCustomerRequestPoAllowed()) {
         //UI should not be allowing this
         String errorMess =
           ClwI18nUtil.getMessage(request, "shop.errors.poNumberIsNotAllowedForTheAccount", null);
         ae.add("error", new ActionError("error.simpleGenericError", errorMess));
         return ae;
       }

       boolean poNumFl = (poNum == null || poNum.trim().length() == 0) ? false : true;
       if (pendingConsolidationFl && poNumFl) {
         String errorMess =
           ClwI18nUtil.getMessage(request,
                                  "shop.errors.poNumberMustBeEmptyForConsolidatingOrders", null);
         ae.add("error", new ActionError("error.simpleGenericError", errorMess));
         return ae;
       }

       if (appUser.getPoNumRequired() && account.isCustomerRequestPoAllowed()){
    	   boolean failed = false;
    	   if (allowPoByVender){
    		   ShoppingCartDistDataVector cartDistributors = pForm.getCartDistributors();
    	       for (int i = 0; i < cartDistributors.size(); i++) {
    	           ShoppingCartDistData distD = (ShoppingCartDistData) cartDistributors.get(i);
    	           if (!Utility.isSet(distD.getPoNumber())){
    	        	   failed = true;
    	        	   break;
    	           }
    	       }
    	   }else if (!pendingConsolidationFl && !poNumFl && pForm.getReqPmt().equals("PO") && !site.isBlanketPoNumSet()){
    		   failed = true;
    	   }
    	   if (failed){
    		   Object[] args = new Object[1];
    		   String poNumLabel = ClwI18nUtil.getMessage(request, "shop.checkout.text.poNumber", null);
    		   args[0]=poNumLabel;
    		   String errorMess = ClwI18nUtil.getMessage(request, "variable.empty.error", args);
    		   ae.add("error", new ActionError("error.simpleGenericError", errorMess));
    		   return ae;
		       /*  String poNumLabel = ClwI18nUtil.getMessage(request, "shop.checkout.text.poNumber", null);
		         ae.add("error", new ActionError("variable.empty.error", poNumLabel));
		         return ae;*/
    	   }
       }
       
       if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.PLACE_ORDER_MANDATORY_REQUEST_SHIP_DATE)
    		   && !Utility.isSet(pForm.getRequestedShipDate())){
    	   Object[] args = new Object[1];
		   args[0]=ClwI18nUtil.getMessage(request, "shop.checkout.text.requestedDelivery", null);;
		   String errorMess = ClwI18nUtil.getMessage(request, "variable.empty.error", args);
		   ae.add("error", new ActionError("error.simpleGenericError", errorMess));
		   return ae;
       }
       //----- check Delivery Date-----
       SimpleDateFormat sdfTime = new SimpleDateFormat(Constants.SIMPLE_TIME_PATTERN);
       Date expiredCutOff = isValidDeliveryDate(request, pForm);
       if(expiredCutOff != null){
         Object[] params = new Object[2];
         params[0] = pForm.getDeliveryDate();
         params[1] = ClwI18nUtil.formatDate(request,expiredCutOff,DateFormat.FULL)+" " + sdfTime.format(expiredCutOff);
         String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.deliveryDateError", params);
         ae.add("error", new ActionError("error.simpleGenericError", errorMess));
         return ae;
       }
       //------------------------------
       CheckoutLogic checkOutLogic =
               (CheckoutLogic) ClwCustomizer.getJavaObject(request,"com.cleanwise.view.logic.CheckoutLogic");
       ae = checkOutLogic.validatePoNum(request,pForm);
       if(!ae.isEmpty()) {
           return ae;
       }

       ae = validateCheckoutFields(request, pForm);
       if(!ae.isEmpty()) {
           return ae;
       }

       orderReq.setCustomerPoNumber(poNum);
       ae = checkAndSaveAlternateAddress(request, pForm, ae, orderReq, ClwI18nUtil.getMessage(request, "userportal.esw.checkout.text.attention"));
       if(!ae.isEmpty()) {
           return ae;
       }
       orderReq.setCustomerComments(pForm.getComments());
       //orderReq.setCustomerComments(pForm.getCustomerComment());

       if (shoppingCartD.getWorkOrderItem() == null && Utility.isSet(pForm.getWorkOrderNumber())) {
           WorkOrderSimpleSearchCriteria crit = new WorkOrderSimpleSearchCriteria();
           crit.setStoreId(pForm.getStoreId());
           IdVector sites= new IdVector();
           sites.add(Integer.valueOf(pForm.getSiteId()));
           crit.setSiteIds(sites);
           crit.setWorkOrderNum(pForm.getWorkOrderNumber());

           WorkOrderDetailViewVector woDVV = workOrderEjb.getWorkOrderDetailCollection(crit);
           if (woDVV.isEmpty()) {
               //error message
               String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.invalidWorkOrderNumber", new Object[] {pForm.getWorkOrderNumber()});
               ae.add("error", new ActionError("error.simpleGenericError", errorMess));
               return ae;
           } else {
               //wo - order binding
               WorkOrderDetailView wo = (WorkOrderDetailView)woDVV.get(0);
               String woStatus = wo.getWorkOrder().getStatusCd();
               if (RefCodeNames.WORK_ORDER_STATUS_CD.CLOSED.equals(woStatus) ||
                   RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED.equals(woStatus) ||
                   RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED.equals(woStatus)) {

                   String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.inapropriateWorkOrderStatus", new Object[] {woStatus});
                   ae.add("error", new ActionError("error.simpleGenericError", errorMess));
               return ae;
               }
               WorkOrderItemDetailViewVector woItems = wo.getWorkOrderItems();
               if (!woItems.isEmpty()) {
                   WorkOrderItemDetailView woItem = (WorkOrderItemDetailView)woItems.get(0);
                   WorkOrderItemData woItemData = woItem.getWorkOrderItem();
                   if (woItemData != null && woItemData.getWorkOrderItemId() > 0) {
                       orderReq.setWorkOrderItemId(woItemData.getWorkOrderItemId());
                   }
               }
           }
       }
       
       //Check whether remote access is enabled
       SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
       if (sessionData.isRemoteAccess()) {
           ae = checkServiceTicket(request, pForm.getOrderServiceTicketNumbers(), new Date());
           if (ae.isEmpty()) {
               orderReq.setServiceTicketOrderRequest(createServiceOrderRequest(pForm));
           } else {
               return ae;
           }
       }


       String processOrderDate = pForm.getProcessOrderDate();
       if (Utility.isSet(processOrderDate)) {
         if (pendingConsolidationFl) {
           String errorMess =
             ClwI18nUtil.getMessage(request,
                                    "shop.errors.processOrderOnMustBeEmptyForConsolidatingOrders", null);
           ae.add("error", new ActionError("error.simpleGenericError", errorMess));
           return ae;
         }
         Date procDate = null;
         try {
           //procDate = Utility.parseDate(processOrderDate);
           procDate = ClwI18nUtil.parseDateInp(request, processOrderDate);
         } catch (Exception exc) {}
         if (procDate == null) {
           Object[] param = new Object[1];
           param[0] = processOrderDate;
           String errorMess =
             ClwI18nUtil.getMessage(request,
                                    "shop.errors.wrongProcessOrderOnDateFormat", param);
           ae.add("error", new ActionError("error.simpleGenericError",
                                           errorMess));
           return ae;
         }

         try {
           currDate = sdf.parse(sdf.format(currDate));
         } catch (Exception exc) {} //never should happen
         if (procDate.compareTo(currDate) <= 0) {
           String errorMess =
             ClwI18nUtil.getMessage(request,
                                    "shop.errors.processOnDateCannotBeBeforeCurrenDate", null);
           ae.add("error", new ActionError
                  ("error.simpleGenericError", errorMess));
           return ae;
         }

         processOrderDate = sdf.format(procDate);
         orderReq.setHoldUntilDate(processOrderDate);
         String processOrderDateInp = ClwI18nUtil.formatDateInp(request, procDate);
         pForm.setProcessOrderDate(processOrderDateInp);
       }

       if (orderReq.getProperties() == null) {
         orderReq.setProperties(new PropertyDataVector());
       }

       if (pForm.getFuelSurchargeAmt() != null && pForm.getFuelSurchargeAmt().doubleValue() > 0) {
             orderReq.addArbitraryOrderMeta(RefCodeNames.CHARGE_CD.FUEL_SURCHARGE, pForm.getFuelSurchargeAmt().toString(),0);
       }

       if (pForm.getSmallOrderFeeAmt() != null && pForm.getSmallOrderFeeAmt().doubleValue() > 0) {
             orderReq.addArbitraryOrderMeta(RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE, pForm.getSmallOrderFeeAmt().toString(),0);
       }

       /*** Save Discount in the orderReq Object ***/
       log.info("*************SVC: pForm.getDiscountAmt() = " + pForm.getDiscountAmt());
       if (pForm.getDiscountAmt() != null && pForm.getDiscountAmt().doubleValue() < 0) { //Discount should always be negative
    	     log.info("***************SVC: Saving discount in the orderReq object");
             orderReq.addArbitraryOrderMeta(RefCodeNames.CHARGE_CD.DISCOUNT, pForm.getDiscountAmt().toString(),0);
             log.info("***************SVC: discount saved in the orderReq object");
       }
       /*****************************************************/

       log.info("************SVC: orderReq = " + orderReq); // SVC
       OrderMetaDataVector checkout_meta = orderReq.getOrderMeta();
       log.info("**********SVC: checkout_meta = " + checkout_meta);

       ActionError checkOnOptions = verifyCheckOutOptions(pForm);
       if (null != checkOnOptions) {
         ae.add("error", checkOnOptions);
         return ae;
       }

       orderReq.getProperties().addAll(mkCheckOutProperties(pForm));

       //Order note is required for a billing order
       if (pForm.isBillingOrder()) {

         if (appUser.isaCustServiceRep()) {
           if ((pForm.getOrderNote() == null || pForm.getOrderNote().trim().length() == 0)) {
             String orderNoteLabel =
               ClwI18nUtil.getMessage(request,
                                      "shop.checkout.text.oderNote", null);
             ae.add("error", new ActionError("variable.empty.error", orderNoteLabel));
             return (ae);
           }
         }

         Iterator it = pForm.getItems().iterator();
         String newDistErpNum = null;
         while (it.hasNext()) {
           ShoppingCartItemData itm = (ShoppingCartItemData) it.next();
           String newErp = null;
           if (itm.getProduct().getCatalogDistributor() != null) {
             newErp = itm.getProduct().getCatalogDistributor().getErpNum();
           } else {
             Object[] param = new Object[1];
             param[0] = "" + itm.getProduct().getSkuNum();
             String errorMess =
               ClwI18nUtil.getMessage(request,
                                      "shop.errors.missingDistributorForBillingOrder", param);
             ae.add("error", new ActionError("error.simpleGenericError", errorMess));
             return ae;
           }

           if (newDistErpNum == null) {
             newDistErpNum = newErp;
           } else if (!newDistErpNum.equals(newErp)) {
             String errorMess =
               ClwI18nUtil.getMessage(request,
                                      "shop.errors.multipleDistributorsForBillingOrder", null);
             ae.add("error", new ActionError("error.simpleGenericError", errorMess));
             return ae;
           }
         }

         if ((pForm.getBillingOriginalPurchaseOrder() == null ||
              pForm.getBillingOriginalPurchaseOrder().trim().length() == 0)) {
           //ae.add("error",new ActionError("variable.empty.error","Billing Original Purchase Order"));
           //return (ae);
         } else {
           PurchaseOrderStatusCriteriaData pocrit = PurchaseOrderStatusCriteriaData.createValue();

           pocrit.setStoreIdVector(appUser.getUserStoreAsIdVector());
           pocrit.setErpPONum(pForm.getBillingOriginalPurchaseOrder());
           PurchaseOrderDataVector pov = factory.getPurchaseOrderAPI().getPurchaseOrderCollection(pocrit);
           if (pov.size() != 1) {
             Object[] param = new Object[2];
             param[0] = "" + pov.size();
             param[1] = pForm.getBillingOriginalPurchaseOrder();
             String errorMess =
               ClwI18nUtil.getMessage(request,
                                      "shop.errors.multipleErpPos", param);
             ae.add("error", new ActionError("error.simpleGenericError", errorMess));
             return (ae);
           } else {
             PurchaseOrderData po = (PurchaseOrderData) pov.get(0);
             if (newDistErpNum != null) {
               if (!newDistErpNum.equals(po.getDistErpNum())) {
                 Object[] param = new Object[2];
                 param[0] = po.getDistErpNum();
                 param[1] = newDistErpNum;
                 String errorMess =
                   ClwI18nUtil.getMessage(request,
                                          "shop.errors.distributorPoMismatch", param);
                 ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                 return ae;
               }
             }
           }
         }
       }

       if ((pForm.getOrderNote() != null && pForm.getOrderNote().trim().length() > 0)) {
         orderReq.setOrderNote(I18nUtil.getUtf8Str(pForm.getOrderNote()));
       }

       if (shoppingCartD.getPrevOrderData() != null) {
         OrderData prevod = shoppingCartD.getPrevOrderData();
         if (prevod.getOrderStatusCd() != null &&
             prevod.getOrderStatusCd().equals
             (RefCodeNames.ORDER_STATUS_CD.CANCELLED)) {
           String tnewordermsg = " This order replaces " +
                                 " order number=" + prevod.getOrderNum() +
                                 " , placed by=" + prevod.getAddBy() +
                                 " , original order date=" + prevod.getOriginalOrderDate();

           String tnewmsg = orderReq.getOrderNote();
           if (null == tnewmsg) {
             tnewmsg = "";
           }
           tnewmsg += "  " + tnewordermsg;
           orderReq.setOrderNote(tnewmsg);
           //orderReq.setOrderRefNumber(prevod.getOrderNum());
           orderReq.setRefOrderId(prevod.getOrderId());
         }
       }

       if (Utility.isSet(pForm.getCustomerComment())) {
         orderReq.addArbitraryOrderProperty
           (RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS, pForm.getCustomerComment());
         //pForm.setCustomerComment(null);
       }

       // save checkout field value
	   for (BusEntityFieldDataElement checkoutProp : pForm.getCheckoutFieldProperties()){
		   if (Utility.isSet(checkoutProp.getValue())){
			   PropertyData prop = checkoutProp.getPropertyData();
			   orderReq.addArbitraryOrderProperty(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CHECKOUT_FIELD_CD, checkoutProp.getTag(), prop.getValue());
		   }
	   }

       if (orderReq.getProperties() == null) {
         orderReq.setProperties(new PropertyDataVector());
       }
       orderReq.setBillingOrder(pForm.isBillingOrder());
       if (pForm.isBillingOrder()) {
         //add dist invoice prop
         PropertyData pd = PropertyData.createValue();
         pd.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
         pd.setPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_DISTRIBUTOR_INVOICE);
         pd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_DISTRIBUTOR_INVOICE);
         pd.setValue(pForm.getBillingDistributorInvoice());
         orderReq.getProperties().add(pd);
         //add bill only orig po num prop
         pd = PropertyData.createValue();
         pd.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
         pd.setPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORIGINAL_PO_NUM);
         pd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORIGINAL_PO_NUM);
         pd.setValue(pForm.getBillingOriginalPurchaseOrder());
         orderReq.getProperties().add(pd);
       }
       //add customerRequestedReshipOrderNum prop
       if (!(pForm.getCustomerRequestedReshipOrderNum() == null ||
             pForm.getCustomerRequestedReshipOrderNum().trim().length() == 0)) {
         PropertyData pd = PropertyData.createValue();
         pd.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
         pd.setPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUST_REQ_RESHIP_ORDER_NUM);
         pd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUST_REQ_RESHIP_ORDER_NUM);
         pd.setValue(pForm.getCustomerRequestedReshipOrderNum());
         orderReq.getProperties().add(pd);
       }
       
       //add BUDGET_YEAR_PERIOD prop
       if (!(pForm.getBudgetYearPeriod() == null ||
             pForm.getBudgetYearPeriod().trim().length() == 0)) {
         PropertyData pd = PropertyData.createValue();
         pd.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
         pd.setPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BUDGET_YEAR_PERIOD);
         pd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BUDGET_YEAR_PERIOD);
         pd.setValue(pForm.getBudgetYearPeriod());
         orderReq.getProperties().add(pd);
         
         pd = PropertyData.createValue();
         pd.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
         pd.setPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BUDGET_YEAR_PERIOD_LABEL);
         pd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BUDGET_YEAR_PERIOD_LABEL);
         pd.setValue(pForm.getBudgetYearPeriodLabel());
         orderReq.getProperties().add(pd);
       }

       orderReq.setBypassOrderRouting(pForm.isBypassOrderRouting());
       orderReq.setBypassCustomerWorkflow(pForm.isBypassCustomerWorkflow());

 //      log.info("placeOrder() ***************SVC 2: pForm.isBypassBudget() = " + pForm.isBypassBudget());
       if (pForm.isBypassBudget()) {
         orderReq.setOrderBudgetTypeCd(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE);
       } else {
         orderReq.setOrderBudgetTypeCd(null);
       }

        /// add 'RebillOrder' property into 'orderReq'
        setRebillOrderPropertyForOrderRequest(orderReq, pForm);

       //for crc, make sure contact name and phone number are present
       String type = appUser.getUser().getUserTypeCd();
       if (type.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE) ||
           type.equals(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR) ||
           type.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER)) {
         if (pForm.getOrderContactName() == null ||
             pForm.getOrderContactName().trim().length() == 0) {
           String contactNameLabel =
             ClwI18nUtil.getMessage(request, "shop.checkout.text.contactName", null);

           ae.add("error", new ActionError
                  ("variable.empty.error", contactNameLabel));
           return ae;
         } else {
           pForm.setOrderContactName(I18nUtil.getUtf8Str(pForm.getOrderContactName()));
         }
         if (pForm.getOrderContactPhoneNum() == null ||
             pForm.getOrderContactPhoneNum().trim().length() == 0) {
           String contactPhoneNumberLabel =
             ClwI18nUtil.getMessage(request, "shop.checkout.text.contactPhoneNum", null);

           ae.add("error", new ActionError
                  ("variable.empty.error", contactPhoneNumberLabel));
           return ae;
         }
         if (pForm.getOrderOriginationMethod() == null ||
             pForm.getOrderOriginationMethod().trim().length() == 0) {
           String methodLabel =
             ClwI18nUtil.getMessage(request, "shop.checkout.text.method", null);

           ae.add("error", new ActionError
                  ("variable.empty.error", methodLabel));
           return ae;
         }
         saveContactInfo(request, pForm);

       } else {
         pForm.setOrderContactName(appUser.getUser().getFirstName() + " " + appUser.getUser().getLastName());
       }
       orderReq.setOrderContactName(I18nUtil.getUtf8Str(pForm.getOrderContactName()));

       orderReq.setOrderTelephoneNumber(pForm.getOrderContactPhoneNum());
       orderReq.setOrderEmail(pForm.getOrderContactEmail());
       String orderSourceCd = pForm.getOrderOriginationMethod();
       OrderData prevOrderD = shoppingCartD.getPrevOrderData();
       if (prevOrderD != null) {
         String prevOrderSourceCd = prevOrderD.getOrderSourceCd();
         if (RefCodeNames.ORDER_SOURCE_CD.INVENTORY.equals(prevOrderSourceCd)) {
           orderSourceCd = prevOrderSourceCd;
         }
       }
       orderReq.setOrderSourceCd(orderSourceCd);
     } catch (Exception e2) {
       e2.printStackTrace();
       ae.add("error", new ActionError("error.systemError", "Page expired 2"));
       return ae;
     }
     //Verifiy quantities for consolidated order
     boolean consolidatedOrderFl = false;
     if (shoppingCartD instanceof ConsolidatedCartView) {
       consolidatedOrderFl = true;
       HashMap itemHM = new HashMap();
       ArrayList sourceOrders = ((ConsolidatedCartView) shoppingCartD).getOrders();
       for (Iterator iter = sourceOrders.iterator(); iter.hasNext(); ) {
         OrderJoinData ojD = (OrderJoinData) iter.next();
         OrderItemJoinDataVector oijDV = ojD.getOrderJoinItems();
         for (Iterator iter1 = oijDV.iterator(); iter1.hasNext(); ) {
           OrderItemJoinData oijD = (OrderItemJoinData) iter1.next();
           OrderItemData oiD = oijD.getOrderItem();
           Integer skuNumI = new Integer(oiD.getItemSkuNum());

           int qty = oiD.getTotalQuantityOrdered();
           Integer qtyI = (Integer) itemHM.get(skuNumI);
           if (qtyI == null) {
             qtyI = new Integer(qty);
           } else {
             qtyI = new Integer(qtyI.intValue() + qty);
           }
           itemHM.put(skuNumI, qtyI);
         }
       }
       List reqItems = shoppingCartD.getItems();
       String addedSkus = "";
       int addedCount = 0;
       String modifiedSkus = "";
       int modifiedCount = 0;
       String removedSkus = "";
       int removedCount = 0;
       for (Iterator iter = reqItems.iterator(); iter.hasNext(); ) {
         ShoppingCartItemData sciD =
           (ShoppingCartItemData) iter.next();
         Integer skuNumI = new Integer(sciD.getProduct().getItemData().getSkuNum());
         int qty = sciD.getQuantity();
         Integer sQtyI = (Integer) itemHM.get(skuNumI);
         if (sQtyI == null) {
           if (addedCount > 0) addedSkus += ",";
           addedSkus += skuNumI;
           addedCount++;
         } else {
           if (qty != sQtyI.intValue()) {
             if (modifiedCount > 0) modifiedSkus += ",";
             modifiedSkus += skuNumI;
             modifiedCount++;
           }
           itemHM.remove(skuNumI);
         }
       }
       if (!itemHM.isEmpty()) {
         Set skus = itemHM.keySet();
         for (Iterator iter = skus.iterator(); iter.hasNext(); ) {
           Integer skuNumI = (Integer) iter.next();
           if (removedCount > 0) removedSkus += ",";
           removedSkus += skuNumI;
           removedCount++;
         }
       }
       if (addedCount > 0 || removedCount > 0 || modifiedCount > 0) {
         String errorMess =
           ClwI18nUtil.getMessage(request, "shop.errors.consolidatedOrderCannotBeModified", null);
         ae.add("error", new ActionError("error.simpleGenericError", errorMess));
         if (addedCount > 0) {
           Object[] param = new Object[1];
           param[0] = addedSkus;
           errorMess =
             ClwI18nUtil.getMessage(request, "shop.errors.addedSkus", param);
           ae.add("errorAdd", new ActionError("error.simpleGenericError", errorMess));
         }
         if (removedCount > 0) {
           Object[] param = new Object[1];
           param[0] = removedSkus;
           errorMess =
             ClwI18nUtil.getMessage(request, "shop.errors.removedSkus", param);
           ae.add("errorRem", new ActionError("error.simpleGenericError", errorMess));
         }
         if (modifiedCount > 0) {
           Object[] param = new Object[1];
           param[0] = modifiedSkus;
           errorMess =
             ClwI18nUtil.getMessage(request, "shop.errors.modifiedSkus", param);
           ae.add("errorMod", new ActionError("error.simpleGenericError", errorMess));
         }
         return ae;
       }
       //Poupulate consolidating order ids
       IdVector replacedOrderIds = new IdVector();
       for (Iterator iter = sourceOrders.iterator(); iter.hasNext(); ) {
         OrderJoinData ojD = (OrderJoinData) iter.next();
         int orderId = ojD.getOrderId();
         replacedOrderIds.add(new Integer(orderId));
       }
       orderReq.setReplacedOrderIds(replacedOrderIds);

       //Set order type
       orderReq.setOrderType(RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED);
     }
     //If order is replacing another one
     OrderData replacedOrderD = shoppingCartD.getPrevOrderData();
     if (replacedOrderD != null) {
       IdVector replacedOrderIds = new IdVector();
       replacedOrderIds.add(new Integer(replacedOrderD.getOrderId()));
       orderReq.setReplacedOrderIds(replacedOrderIds);
     }

     //If order is for a WorkOrderItem orderReq.getSiteId();
     WorkOrderItemData workOrderItemD = shoppingCartD.getWorkOrderItem();
     if (workOrderItemD != null && workOrderItemD.getWorkOrderItemId() > 0) {
         if (orderReq.getSiteId() != workOrderItemD.getBusEntityId()) {
             String errorMessage = ClwI18nUtil.getMessageOrNull(request, "shop.errors.mustBelongToSameSite");
             if (errorMessage == null) {
                 errorMessage = "Web Order and Work Order must belong to the same site.";
             }
             ae.add("error", new ActionError("error.simpleGenericError", errorMessage));
             return ae;
         }
         WorkOrderData wo = workOrderEjb.getWorkOrder(workOrderItemD.getWorkOrderId());
         String woStatus = wo.getStatusCd();
         if (RefCodeNames.WORK_ORDER_STATUS_CD.CLOSED.equals(woStatus) ||
             RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED.equals(woStatus) ||
             RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED.equals(woStatus)) {

             String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.inapropriateWorkOrderStatus", new Object[] {woStatus});
             ae.add("error", new ActionError("error.simpleGenericError", errorMess));
         return ae;
         }
         orderReq.setWorkOrderItemId(workOrderItemD.getWorkOrderItemId());
         shoppingCartD.setWorkOrderItem(null);
         pForm.setWorkOrderNumber("");
     }

     IntegrationServices isvcEjb = null;

     //ShoppingServices shoppingServeicesEjb = null;
     String emsg = "";
     try {
       emsg = "No IntegrationServices API Access";
       isvcEjb = factory.getIntegrationServicesAPI();
     } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
       ae.add("error", new ActionError("error.systemError", emsg));
       return ae;
     }

     Integer catalogIdI = (Integer)
                          session.getAttribute(Constants.CATALOG_ID);
     if (catalogIdI == null) {
       ae.add("error", new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session attrbute found"));
       return ae;
     }

     ProcessOrderResultData orderRes = null;
     String lockName = "==CheckOutLock==";
     Integer lockValue = null;
     
     log.error("CheckoutLogic: PlaceOrder 1");
     		
     try {
    	 log.error("CheckoutLogic: PlaceOrder 2");
       Object oforlock = new Object();
       log.error("CheckoutLogic: PlaceOrder 3");
       synchronized (oforlock) {
    	   log.error("CheckoutLogic: PlaceOrder 4");
         lockValue = (Integer) session.getAttribute(lockName);
         log.error("CheckoutLogic: PlaceOrder 5");
         if (lockValue == null) {
        	 log.error("CheckoutLogic: PlaceOrder 6");
           lockValue = new Integer("0");
           session.setAttribute(lockName, lockValue);
         }
       }
       log.error("CheckoutLogic: PlaceOrder 7");
       if (lockValue.intValue() == 0) {
    	   log.error("CheckoutLogic: PlaceOrder 8");
         // Lock the purchase capability.
         synchronized (oforlock) {
        	 log.error("CheckoutLogic: PlaceOrder 9");
           lockValue = new Integer("1");
           session.setAttribute(lockName, lockValue);
         }
         log.error("CheckoutLogic: PlaceOrder 10");
         //deal with inline customer ordering that requieres system pass off (cXML)
         if (doInlineOrderInit(ae, pForm, session, factory, appUser)) {
           checkoutDbg(request, "placeOrder processOrderRequest 0", stime);
           pForm.setOrderRequest(orderReq);
           orderRes = isvcEjb.processOrderRequest(orderReq);
           checkoutDbg(request, "placeOrder processOrderRequest 1", stime);
           if (orderRes.getOrderId() == 0) {
             String errorMess =
               ClwI18nUtil.getMessage(request, "shop.errors.orderRejected", null);
             String[] messA = orderRes.getMessages();
             if (messA != null && messA.length > 0) {
               for (int ii = 0; ii < messA.length; ii++) {
                 String translatedMess = ClwI18nUtil.formatEjbError(request, messA[ii]);
                 if (translatedMess != null && translatedMess.length() > 0) {
                   errorMess += ". " + translatedMess;
                 }
               }
             }
             ae.add("error", new
                    ActionError("error.simpleGenericError", errorMess));
             return ae;
           }
         }
       } else {
         // The lock value is 1.  Meaning a purchase is
         // already in progress.
         String errorMess =
           ClwI18nUtil.getMessage(request, "shop.errors.purchaseIsInProgress", null);
         ae.add("error", new ActionError("error.simpleGenericError", errorMess));
         return ae;
       }

       if (!(ae.size() == 0)) {
         return ae;
       }

       if (consolidatedOrderFl) {
         int orderId = orderRes.getOrderId();
         ReplacedOrderViewVector replOrderVwV =
           orderEjb.getReplacedOrders(orderId);
         pForm.setReplacedOrders(replOrderVwV);
       }

       pForm.setConfirmationFlag(true);
       pForm.setOrderResult(orderRes);
       pForm.setSite(site);
       pForm.setAccount(account);
       pForm.setItems(shoppingCartD.getItems());

       try{

    	   int accId = account.getAccountId();

    	   SessionTool st = new SessionTool(request);
    	   Site sbean = st.getFactory().getSiteAPI();

    	   ShoppingControlDataVector shopCtrls = site.getShoppingControls();

    	   ShoppingCartItemDataVector sciDV = shoppingCartD.getItems();
    	   for(int i=0; i<sciDV.size(); i++){
    		   ShoppingCartItemData thisItem = (ShoppingCartItemData)sciDV.get(i);

    		   int itemId = thisItem.getItemId();
    		   int qtyOrdered = thisItem.getQuantity();

    		   for(int s=0; s<shopCtrls.size(); s++){
    			   ShoppingControlData sdata = (ShoppingControlData)shopCtrls.get(s);

    			   if(sdata.getItemId()==itemId){
    				   int newActualMaxQty = sdata.getActualMaxQty() - qtyOrdered;

    				   sdata.setActualMaxQty(newActualMaxQty);
    				   //sdata.setExpDate(null);
    				   //sdata.setHistoryOrderQty(0);

    			   }
    		   }
    		   sbean.updateShoppingControls(shopCtrls, false);




    	   }
           // addind DeliveryDate into Order_Meta
    	   String deliveryDateS = null;
    	   try{
    		   deliveryDateS = Utility.convertFullDate2Short(pForm.getDeliveryDate());
    	   }catch(Exception exc){
    		   exc.printStackTrace();
    	   }
    	   
    	   if(!Utility.isSet(deliveryDateS)){ //date from new ui checkout
    		   String pattern = ClwI18nUtil.getDatePattern(request);
    		   SimpleDateFormat sdfpattern = new SimpleDateFormat(pattern);
    		   SimpleDateFormat dfShort = new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN);
    		   
    		   Date deliveryDate = sdfpattern.parse(pForm.getDeliveryDate());
    		   
    		   deliveryDateS = dfShort.format(deliveryDate);
    		   
    	   }
    	   
           if(orderRes!=null && orderRes.getOrderId()>0){
           log.info("CheckoutLogic >> placeOrder : UserName :" +
                              appUser.getUser().getUserName() +
                              ", OrderId = " + orderRes.getOrderId() +
                              ", deliveryDate =" + deliveryDateS);
           }
           if (Utility.isSet(deliveryDateS) ){
             sbean.addDeliveryDateForSite(deliveryDateS, orderRes.getOrderId(),
                                        appUser.getUser().getUserName());
           }
       }catch(Exception e){
    	   e.printStackTrace();
    	   //throw new Exception(e.getMessage());
       }

       //Remove items from shopping cart
       shoppingCartD.setItems(new ShoppingCartItemDataVector());

       shoppingCartD.setPrevOrderData(null);
       if (shoppingCartD instanceof ConsolidatedCartView) {
         ((ConsolidatedCartView) shoppingCartD).setOrders(new ArrayList());
       }
       //checkoutDbg(request, "placeOrder saveShoppingCart", stime);
       //ae = ShoppingCartLogic.saveShoppingCart(site,session,orderRes);
       if (replacedOrderD != null) {
         ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();
         //shoppingServEjb.createShoppingInfo (shoppingCartD,
         //    orderReq.getUserName(), orderRes, pForm.getItems());
         shoppingServEjb.saveModifiedOrderShoppingInfo(shoppingCartD,
           orderReq.getUserName(), orderRes);
       } else {
         ae = ShoppingCartLogic.saveShoppingCart(site, session, orderRes);
       }



       // Unlock the purchase capability.
       lockValue = new Integer("0");
       session.setAttribute(lockName, lockValue);
       //checkoutDbg(request, "placeOrder DONE", stime);
     }

     catch (Exception exc) {
       exc.printStackTrace();
       log.error("CheckoutLogic: PlaceOrder ERRROR  1 MESSAGE="+exc.getMessage());
       return mkUserOrderMessage(request, exc.getMessage());
     } finally {
       //unlock the order functionality
       lockValue = new Integer("0");
       session.setAttribute(lockName, lockValue);
     }
     return ae;
   }

    private static ServiceTicketOrderRequestView createServiceOrderRequest(CheckoutForm pForm) throws Exception {
        ServiceTicketOrderRequestView serviceOrderRequest = new ServiceTicketOrderRequestView();
        IdVector stnumbers = Utility.parseIdStringToVector(pForm.getOrderServiceTicketNumbers(), ",", true);
        serviceOrderRequest.setServiceTickets(stnumbers);
        return serviceOrderRequest;
    }

    private static ActionErrors checkServiceTicket(HttpServletRequest request, String pServiceTicketNumbers, Date pDate) throws Exception {

        ActionErrors ae = new ActionErrors();

        CleanwiseUser appUser = ShopTool.getCurrentUser(request);

        List<Integer> validNumbers = new ArrayList<Integer>();
        List<String> invalidNumbers = new ArrayList<String>();

        StringTokenizer tok = new StringTokenizer(pServiceTicketNumbers, ",");
        while (tok.hasMoreTokens()) {
            String stnum = tok.nextToken().trim();
            try {
                validNumbers.add(Integer.parseInt(stnum));
            } catch (Exception exc) {
                invalidNumbers.add(stnum);
            }
        }

        if (Utility.isSet(invalidNumbers)) {
            for (String num : invalidNumbers) {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.wrongStNumberFormat", new String[]{num});
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            }
        }

        if (!ae.isEmpty()) {
            return ae;
        }

        SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
        RemoteWebClient orcaWebClient = sessionData.getRemoteWebClient();

        IdVector unavailableNumbers = null;

        try {

            if (orcaWebClient!=null && Utility.isSet(validNumbers)) {

                unavailableNumbers = orcaWebClient.determineUnAvailableStNumbers(
                        request.getSession().getId(),
                        appUser,
                        validNumbers,
                        pDate,
                        true
                );
            }

        } catch (Exception e) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.couldNotVerifyServiceTickets");
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        if (Utility.isSet(unavailableNumbers)) {
            for (Object num : unavailableNumbers) {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.unavailableServiceTicket", new String[]{num.toString()});
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            }
        }

        return ae;

    }


    //****************************************************************************
    public static ActionErrors placeOrderVerify(HttpServletRequest request,
                                          CheckoutForm pForm) throws Exception {

      pForm.setComments(I18nUtil.getUtf8Str(pForm.getComments()));

      pForm.setRequestedBillToAddress(getUtf8(pForm.getRequestedBillToAddress()));

      Date stime = checkoutDbg(request, "placeOrder START", null);
      ActionErrors ae = new ActionErrors();
      HttpSession session = request.getSession();

      session.setAttribute("CheckoutForm", pForm);

      Integer contractId = (Integer)
                           session.getAttribute(Constants.CONTRACT_ID);
      if (null == contractId) {
        contractId = new Integer(0);
      }

      if(ShopTool.isModernInventoryShopping(request)
               &&!ShopTool.hasDiscretionaryCartAccessOpen(request)){
          String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.discretionaryCartUnavailable",null);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
      }

      //pickup items
      ShoppingCartData shoppingCartD = ShopTool.getCurrentShoppingCart(session);
      if (shoppingCartD == null) {
        ae.add("error", new ActionError("error.systemError", "Shopping cart available."));
        return ae;
      }
      int originalCartId = shoppingCartD.getOrderGuideId();
      //Place order
      APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
      if (factory == null) {
        ae.add("error", new ActionError("error.systemError", "No Ejb access"));
        return ae;
      }
      Account accountEjb = null;
      try {
        accountEjb = factory.getAccountAPI();
      } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
        ae.add("error", new ActionError("error.systemError", "No account API Access"));
        return ae;
      }
      Order orderEjb = factory.getOrderAPI();
       try {
        orderEjb = factory.getOrderAPI();
      } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
        ae.add("error", new ActionError("error.systemError", "No order API Access"));
        return ae;
      }
      WorkOrder workOrderEjb = null;
       try {
        workOrderEjb = factory.getWorkOrderAPI();
      } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
        ae.add("error", new ActionError("error.systemError", "No work order API Access"));
        return ae;
      }
      //Compare items with shoppingCart
      List cartItems = shoppingCartD.getItems();
      if (cartItems == null || cartItems.isEmpty()) {
        String errorMess =
          ClwI18nUtil.getMessage(request, "shop.errors.orderCannotBeProcessedDueEmptyShoppingCart", null);
        ae.add(ActionErrors.GLOBAL_ERROR,
               new ActionError("error.simpleGenericError", errorMess));
        return ae;
      }

       ///
       if (shoppingCartD != null) {
           ae = ShopTool.validateBudgetThreshold(request, shoppingCartD);
           if (!ae.isEmpty()) {
               return ae;
           }
       }

      CustomerOrderRequestData orderReq = new CustomerOrderRequestData();

      Date currDate = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
      boolean pendingConsolidationFl = pForm.getPendingConsolidation();
      if (pendingConsolidationFl) {
        orderReq.setHoldUntilDate(sdf.format(Utility.MAX_DATE));
        orderReq.setOrderType(RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED);
      }

      orderReq.setContractId(contractId.intValue());

      //Set up freight request
      ae = setupFreightTable (request, pForm, orderReq);
      if(ae.size()>0) {
          return ae;
      }

     ///
     OrderAddOnChargeDataVector orderAddOnCharges =
         getFuelSurchargeAndSmallOrderFeeForOrder(pForm.getCartDistributors());
     if (orderAddOnCharges != null && orderAddOnCharges.size() > 0) {
         orderReq.setOrderAddOnChargeList(orderAddOnCharges);
     }

      // Send the freight specified in the UI.
      double frtamt = 0, hndlamt = 0;
      if (pForm.getFreightAmt() != null) {
        frtamt = pForm.getFreightAmt().doubleValue();
      }
      // Send the handling specified in the UI.
      if (pForm.getHandlingAmt() != null) {
    	log.info("pForm.getHandlingAmt()= " + pForm.getHandlingAmt());
        hndlamt = pForm.getHandlingAmt().doubleValue();
      }
      log.info("CheckoutLogic <placeOrder>  WWWWWWWWWWWWWWWWWWWWW pForm.getFreightAmt() : "+pForm.getFreightAmt() );

      orderReq.setFreightCharge(frtamt);
      orderReq.setHandlingCharge(hndlamt);

      //Rush charge
      String rushOrderChargeS = pForm.getRushChargeAmtString();
      if(Utility.isSet(rushOrderChargeS)) {
          double rushOrderCharge = 0;
          try {
              rushOrderChargeS = rushOrderChargeS.trim();
              rushOrderCharge =
                      Double.parseDouble(rushOrderChargeS);
          } catch (Exception exc) {
              log.info("CheckoutLogic CCCCCCCCCCCCCCC inavlid rush order charge: "+rushOrderChargeS);
          }
          orderReq.setRushOrderCharge(rushOrderCharge);
      }

      log.info("Rush order charge was set");
      
      /*** Add Discount per Distributor info. to the OrderReq Object ***/
      ae = setupOrderAddOnChargeTable(request, pForm, orderReq);
      if(ae.size()>0) {
          return ae;
      }
      log.info("dicount and didtributor info were added");
      /*****************************************************************/

      if (Utility.isSet(pForm.getRequestedShipDate())) {
        try {
          //Date aDate = sdf.parse(pForm.getRequestedShipDate());
          Date aDate = ClwI18nUtil.parseDateInp(request, pForm.getRequestedShipDate());

          if (aDate.compareTo(currDate) <= 0) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.deliveryBeforeCurrentDate", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
          }
          orderReq.setOrderRequestedShipDate(aDate);
        } catch (Exception e) {
          Object[] args = new Object[1];
          args[0]=pForm.getRequestedShipDate();
          String errorMess = ClwI18nUtil.getMessage(request, "error.badDateFormat", args);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }
      }
      log.info("Requested ship date was set");
 /*  Moved UP  to use recalculated freight && handle values in orderReq
      //Set up freigh request
      ae = setupFreightTable (request, pForm, orderReq);
      if(ae.size()>0) {
          return ae;
      }
 */
      CleanwiseUser appUser = (CleanwiseUser)
                              session.getAttribute(Constants.APP_USER);

      //Check site
      SiteData site = appUser.getSite();
      int siteId = site.getBusEntity().getBusEntityId();
      if (siteId != pForm.getSiteId()) {
        String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.pageExpired1", null);
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        return ae;
      }
      pForm.setSite(site);
      log.info("site was set");
      //Get Account
      //XXX why not get out of session ala: AccountData account = appUser.getUserAccount()?
      AccountData account = accountEjb.getAccountForSite(siteId);

      if (account == null) {
        log.info("No account information found for site=" + siteId);
        ae.add("error", new ActionError("error.systemError", "No account information found"));
        return ae;
      }

      // check store properties ALLOW_PO_NUM_BY_VENDER
      boolean allowPoByVender =
              Utility.isTrue(PropertyUtil.getPropertyValue(appUser.getUserStore().getMiscProperties(),
                                 RefCodeNames.PROPERTY_TYPE_CD.ALLOW_PO_NUM_BY_VENDER));

      if (allowPoByVender){
          ShoppingCartDistDataVector cartDistributors = pForm.getCartDistributors();
          Map poNumberByVendor = new HashMap();
          for (int i = 0; i < cartDistributors.size(); i++) {
                  ShoppingCartDistData distD = (ShoppingCartDistData) cartDistributors.get(i);
                  poNumberByVendor.put(distD.getDistributor().getErpNum(), distD.getPoNumber());
          }
          orderReq.setPoNumberByVendor(poNumberByVendor);
      }
      log.info("allowPoByVendor is done");
      
     for (int ii = 0; ii < cartItems.size(); ii++) {
        ShoppingCartItemData cartItem =
          (ShoppingCartItemData) cartItems.get(ii);
        int itemId = cartItem.getProduct().getItemData().getItemId();
        int clw_skunum = cartItem.getProduct().getItemData().getSkuNum();

        if (cartItem.getIsaInventoryItem()) {
          String t2 = cartItem.getInventoryQtyOnHandString();
          if (t2 == null || t2.length() == 0) {
            String msg = " On Hand value not set for inventory item: " + cartItem.description();
            orderReq.addOrderNote(msg);
          }
        }
        
        int qty = cartItem.getQuantity();
        if (qty == 0)continue; // 0 qty line

        double price = cartItem.getPrice();
        boolean resaleFlag = cartItem.getReSaleItem();
        //log.info (" --- cartItem=" + cartItem);
        // Verify that the account is allowed to purchase resale items.
        if (!account.isAuthorizedForResale()) {
          // the account is not set up for resale.
          // Reset the resale flag as the account setting must win.
          if (true == resaleFlag) {
        	  log.info
              ("RESALE MISCONFIGURATION, account id=" +
               account.getAccountId() +
               " is not set up for resale but item id=" + itemId +
               " sku=" + clw_skunum + " is set up for resale.");
          }
          resaleFlag = false;
        }

        String distErpNum = "";
        if (allowPoByVender){
            if (cartItem.getProduct().getCatalogDistributor() != null) {
                    distErpNum = cartItem.getProduct().getCatalogDistributor().getErpNum();
            }

        }
        orderReq.addItemEntry(ii + 1, itemId, clw_skunum,
                              qty, price,
                              cartItem.getProduct().getUom(),
                              cartItem.getProduct().getShortDesc(),
                              cartItem.getProduct().getPack(),
                              cartItem.getIsaInventoryItem(),
                              cartItem.getInventoryParValue(),
                              cartItem.getInventoryQtyOnHandString(),
                              resaleFlag,
                              distErpNum);
      }
      log.info("Cart items were processed");
      String paymentCd = RefCodeNames.PAYMENT_TYPE_CD.ACCOUNT;
      //Check user
      int userId = appUser.getUser().getUserId();
      orderReq.setUserId(userId);
      orderReq.setUserName(appUser.getUser().getUserName());
      if (userId != pForm.getUserId()) {
        String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.pageExpired", null);
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        return ae;
      }

      String ccNumber = pForm.getCcNumber();
      //
      // If this is not a credit card purchase and the
      // user can specify their own bill to address, then
      // perform some basic address information checking.
      //
      if (!pForm.isaCcReq() && appUser.canEditBillTo()) {
        if (!pForm.hasRequestedBillToAddress()) {
          Object[] param = new Object[1];
          param[0] = ClwI18nUtil.getMessage(request, pForm.getRequestBillToCheckMessage(), null);
          String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.checkBillingAddress", param);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }

        orderReq.setRequestedBillingAddress
          (pForm.getRequestedBillToAddress());
      }
      orderReq.setSiteId(siteId);
      orderReq.setAccountId(account.getBusEntity().getBusEntityId());

      orderReq.setCustomerSystemIdentifier((String) session.getAttribute(Constants.CUSTOMER_SYSTEM_ID));
      orderReq.setCustomerSystemURL((String) session.getAttribute(Constants.CUSTOMER_SYSTEM_URL));

      try {
        PropertyData orderMinimum = account.getOrderMinimum();
        double orderTotal = pForm.getItemsAmt(request);

        String minTot = orderMinimum.getValue();
        double minimumTotal;
        try {
          minimumTotal = CurrencyFormat.parse(minTot).doubleValue();
        } catch (ParseException pe) {
          // this should only happen if bad value in db
          minimumTotal = 0.0;
        }
        BigDecimal bigDminTot = new BigDecimal(minimumTotal);
        String formattedMinTot = CurrencyFormat.format(bigDminTot);

        if (orderTotal < minimumTotal) {
          Object[] param = new Object[1];
          param[0] = formattedMinTot;
          String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.totalLessThanMinimum", param);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }

        if (appUser.canMakePurchases() == false) {
          String errorMess =
            ClwI18nUtil.getMessage(request, "shop.errors.billingMustBeOnAccountOrCreditCard", null);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;

        }

//        //Check CC validity
//        ccNumber = pForm.getCcNumber();
//        if (pForm.isaCcReq()) {
//          ae = ccValidation(pForm, request, appUser.getUserStore().isStateProvinceRequired());
//          if (ae.size() > 0)
//            return ae;
//          //encrypt
//          ae = ccEncrypt(pForm);
//          if (ae.size() > 0)
//            return ae;
//          //
//          paymentCd = pForm.getCcType();
//
//          // copy the credit card information into the
//          // order request object.
//          orderReq.setCcNumber(pForm.getCcNumber());
//          orderReq.setCcExpMonth(pForm.getCcExpMonth());
//          orderReq.setCcExpYear(pForm.getCcExpYear());
//          orderReq.setCcBuyerName(pForm.getCcPersonName());
//          orderReq.setCcStreet1(pForm.getCcStreet1());
//          orderReq.setCcStreet2(pForm.getCcStreet2());
//          orderReq.setCcCity(pForm.getCcCity());
//          orderReq.setCcState(pForm.getCcState());
//          orderReq.setCcPostalCode(pForm.getCcZipCode());
//          orderReq.setCcType(pForm.getCcType());
//
//        }

//        // other payment
//        String otherPayment = pForm.getOtherPaymentInfo();
//        if (pForm.getReqPmt().equals("Other") &&
//            (otherPayment == null || otherPayment.trim().length() == 0)
//          ) {
//          // the buyer must enter something if other payment
//          // is used to make a purchase.
//          String errorMess =
//            ClwI18nUtil.getMessage(request, "shop.errors.fieldRecordOfCallIsEmpty", null);
//          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
//          return ae;
//        }
//
//        if (otherPayment != null && otherPayment.trim().length() > 0
//            && appUser.getOtherPaymentFlag() == true) {
//          orderReq.setOtherPaymentInfo(I18nUtil.getUtf8Str(otherPayment));
//        }

        // Check PO number presence
        String poNum = pForm.getPoNumber();
        if (Utility.isSet(poNum) && !account.isCustomerRequestPoAllowed()) {
          //UI should not be allowing this
          String errorMess =
            ClwI18nUtil.getMessage(request, "shop.errors.poNumberIsNotAllowedForTheAccount", null);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }

        boolean poNumFl = (poNum == null || poNum.trim().length() == 0) ? false : true;
        if (pendingConsolidationFl && poNumFl) {
          String errorMess =
            ClwI18nUtil.getMessage(request,
                                   "shop.errors.poNumberMustBeEmptyForConsolidatingOrders", null);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }

        if (appUser.getPoNumRequired() && account.isCustomerRequestPoAllowed()){
            boolean failed = false;
            if (allowPoByVender){
                    ShoppingCartDistDataVector cartDistributors = pForm.getCartDistributors();
                for (int i = 0; i < cartDistributors.size(); i++) {
                    ShoppingCartDistData distD = (ShoppingCartDistData) cartDistributors.get(i);
                    if (!Utility.isSet(distD.getPoNumber())){
                            failed = true;
                            break;
                    }
                }
            }else if (!pendingConsolidationFl && !poNumFl && pForm.getReqPmt().equals("PO") && !site.isBlanketPoNumSet()){
                    failed = true;
            }
            if (failed){
            	
            	Object[] args = new Object[1];
            	String poNumLabel = ClwI18nUtil.getMessage(request, "shop.checkout.text.poNumber", null);
            	args[0]=poNumLabel;
            	String errorMess = ClwI18nUtil.getMessage(request, "variable.empty.error", args);
            	ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
                
            	/*String poNumLabel = ClwI18nUtil.getMessage(request, "shop.checkout.text.poNumber", null);
            	ae.add("error", new ActionError("variable.empty.error", poNumLabel));
            	return ae;*/
            }
        }
        log.info("before checking delivery date");
        //----- check Delivery Date-----
        SimpleDateFormat sdfTime = new SimpleDateFormat(Constants.SIMPLE_TIME_PATTERN);
        Date expiredCutOff = isValidDeliveryDate(request, pForm);
        if(expiredCutOff != null){
          Object[] params = new Object[2];
          params[0] = pForm.getDeliveryDate();
          params[1] = ClwI18nUtil.formatDate(request,expiredCutOff,DateFormat.FULL)+" " + sdfTime.format(expiredCutOff);
          String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.deliveryDateError", params);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }
        //------------------------------
        log.info("before validating PO NUM");
        CheckoutLogic checkOutLogic =
                (CheckoutLogic) ClwCustomizer.getJavaObject(request,"com.cleanwise.view.logic.CheckoutLogic");
        ae = checkOutLogic.validatePoNum(request,pForm);
        if(!ae.isEmpty()) {
            return ae;
        }
        log.info("before validating Checkout fields");
        ae = validateCheckoutFields(request, pForm);
        if(!ae.isEmpty()) {
            return ae;
        }

        orderReq.setCustomerPoNumber(poNum);
        orderReq.setCustomerComments(pForm.getComments());
        //orderReq.setCustomerComments(pForm.getCustomerComment());

        if (shoppingCartD.getWorkOrderItem() == null && Utility.isSet(pForm.getWorkOrderNumber())) {
            WorkOrderSimpleSearchCriteria crit = new WorkOrderSimpleSearchCriteria();
            crit.setStoreId(pForm.getStoreId());
            IdVector sites= new IdVector();
            sites.add(Integer.valueOf(pForm.getSiteId()));
            crit.setSiteIds(sites);
            crit.setWorkOrderNum(pForm.getWorkOrderNumber());

            WorkOrderDetailViewVector woDVV = workOrderEjb.getWorkOrderDetailCollection(crit);
            if (woDVV.isEmpty()) {
                //error message
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.invalidWorkOrderNumber", new Object[] {pForm.getWorkOrderNumber()});
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
            } else {
                //wo - order binding
                WorkOrderDetailView wo = (WorkOrderDetailView)woDVV.get(0);
                String woStatus = wo.getWorkOrder().getStatusCd();
                if (RefCodeNames.WORK_ORDER_STATUS_CD.CLOSED.equals(woStatus) ||
                    RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED.equals(woStatus) ||
                    RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED.equals(woStatus)) {

                    String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.inapropriateWorkOrderStatus", new Object[] {woStatus});
                    ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                return ae;
                }
                WorkOrderItemDetailViewVector woItems = wo.getWorkOrderItems();
                if (!woItems.isEmpty()) {
                    WorkOrderItemDetailView woItem = (WorkOrderItemDetailView)woItems.get(0);
                    WorkOrderItemData woItemData = woItem.getWorkOrderItem();
                    if (woItemData != null && woItemData.getWorkOrderItemId() > 0) {
                        orderReq.setWorkOrderItemId(woItemData.getWorkOrderItemId());
                    }
                }
            }
        }

       //Check whether remote access is enabled
        SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
        if (sessionData.isRemoteAccess()) {
          ae = checkServiceTicket(request, pForm.getOrderServiceTicketNumbers(), new Date());
          if (ae.isEmpty()) {
              orderReq.setServiceTicketOrderRequest(createServiceOrderRequest(pForm));
          } else {
              return ae;
          }
       }

        String processOrderDate = pForm.getProcessOrderDate();
        if (Utility.isSet(processOrderDate)) {
          if (pendingConsolidationFl) {
            String errorMess =
              ClwI18nUtil.getMessage(request,
                                     "shop.errors.processOrderOnMustBeEmptyForConsolidatingOrders", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
          }
          Date procDate = null;
          try {
            //procDate = Utility.parseDate(processOrderDate);
            procDate = ClwI18nUtil.parseDateInp(request, processOrderDate);
          } catch (Exception exc) {}
          if (procDate == null) {
            Object[] param = new Object[1];
            param[0] = processOrderDate;
            String errorMess =
              ClwI18nUtil.getMessage(request,
                                     "shop.errors.wrongProcessOrderOnDateFormat", param);
            ae.add("error", new ActionError("error.simpleGenericError",
                                            errorMess));
            return ae;
          }

          try {
            currDate = sdf.parse(sdf.format(currDate));
          } catch (Exception exc) {} //never should happen
          if (procDate.compareTo(currDate) <= 0) {
            String errorMess =
              ClwI18nUtil.getMessage(request,
                                     "shop.errors.processOnDateCannotBeBeforeCurrenDate", null);
            ae.add("error", new ActionError
                   ("error.simpleGenericError", errorMess));
            return ae;
          }

          processOrderDate = sdf.format(procDate);
          orderReq.setHoldUntilDate(processOrderDate);
          String processOrderDateInp = ClwI18nUtil.formatDateInp(request, procDate);
          pForm.setProcessOrderDate(processOrderDateInp);
        }

        if (orderReq.getProperties() == null) {
          orderReq.setProperties(new PropertyDataVector());
        }

        if (pForm.getFuelSurchargeAmt() != null && pForm.getFuelSurchargeAmt().doubleValue() > 0) {
              orderReq.addArbitraryOrderMeta(RefCodeNames.CHARGE_CD.FUEL_SURCHARGE, pForm.getFuelSurchargeAmt().toString(),0);
        }

        if (pForm.getSmallOrderFeeAmt() != null && pForm.getSmallOrderFeeAmt().doubleValue() > 0) {
              orderReq.addArbitraryOrderMeta(RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE, pForm.getSmallOrderFeeAmt().toString(),0);
        }

        /*** Save Discount in the orderReq Object ***/
        log.info("*************SVC: pForm.getDiscountAmt() = " + pForm.getDiscountAmt());
        if (pForm.getDiscountAmt() != null && pForm.getDiscountAmt().doubleValue() < 0) { //Discount should always be negative
              log.info("***************SVC: Saving discount in the orderReq object");
              orderReq.addArbitraryOrderMeta(RefCodeNames.CHARGE_CD.DISCOUNT, pForm.getDiscountAmt().toString(),0);
              log.info("***************SVC: discount saved in the orderReq object");
        }
        /*****************************************************/

        log.info("************SVC: orderReq = " + orderReq); // SVC
        OrderMetaDataVector checkout_meta = orderReq.getOrderMeta();
        log.info("**********SVC: checkout_meta = " + checkout_meta);

        ActionError checkOnOptions = verifyCheckOutOptions(pForm);
        if (null != checkOnOptions) {
          ae.add("error", checkOnOptions);
          return ae;
        }

        orderReq.getProperties().addAll(mkCheckOutProperties(pForm));

        //Order note is required for a billing order
        if (pForm.isBillingOrder()) {

          if (appUser.isaCustServiceRep()) {
            if ((pForm.getOrderNote() == null || pForm.getOrderNote().trim().length() == 0)) {
              String orderNoteLabel =
                ClwI18nUtil.getMessage(request,
                                       "shop.checkout.text.oderNote", null);
              ae.add("error", new ActionError("variable.empty.error", orderNoteLabel));
              return (ae);
            }
          }

          Iterator it = pForm.getItems().iterator();
          String newDistErpNum = null;
          while (it.hasNext()) {
            ShoppingCartItemData itm = (ShoppingCartItemData) it.next();
            String newErp = null;
            if (itm.getProduct().getCatalogDistributor() != null) {
              newErp = itm.getProduct().getCatalogDistributor().getErpNum();
            } else {
              Object[] param = new Object[1];
              param[0] = "" + itm.getProduct().getSkuNum();
              String errorMess =
                ClwI18nUtil.getMessage(request,
                                       "shop.errors.missingDistributorForBillingOrder", param);
              ae.add("error", new ActionError("error.simpleGenericError", errorMess));
              return ae;
            }

            if (newDistErpNum == null) {
              newDistErpNum = newErp;
            } else if (!newDistErpNum.equals(newErp)) {
              String errorMess =
                ClwI18nUtil.getMessage(request,
                                       "shop.errors.multipleDistributorsForBillingOrder", null);
              ae.add("error", new ActionError("error.simpleGenericError", errorMess));
              return ae;
            }
          }

          if ((pForm.getBillingOriginalPurchaseOrder() == null ||
               pForm.getBillingOriginalPurchaseOrder().trim().length() == 0)) {
            //ae.add("error",new ActionError("variable.empty.error","Billing Original Purchase Order"));
            //return (ae);
          } else {
            PurchaseOrderStatusCriteriaData pocrit = PurchaseOrderStatusCriteriaData.createValue();

            pocrit.setStoreIdVector(appUser.getUserStoreAsIdVector());
            pocrit.setErpPONum(pForm.getBillingOriginalPurchaseOrder());
            PurchaseOrderDataVector pov = factory.getPurchaseOrderAPI().getPurchaseOrderCollection(pocrit);
            if (pov.size() != 1) {
              Object[] param = new Object[2];
              param[0] = "" + pov.size();
              param[1] = pForm.getBillingOriginalPurchaseOrder();
              String errorMess =
                ClwI18nUtil.getMessage(request,
                                       "shop.errors.multipleErpPos", param);
              ae.add("error", new ActionError("error.simpleGenericError", errorMess));
              return (ae);
            } else {
              PurchaseOrderData po = (PurchaseOrderData) pov.get(0);
              if (newDistErpNum != null) {
                if (!newDistErpNum.equals(po.getDistErpNum())) {
                  Object[] param = new Object[2];
                  param[0] = po.getDistErpNum();
                  param[1] = newDistErpNum;
                  String errorMess =
                    ClwI18nUtil.getMessage(request,
                                           "shop.errors.distributorPoMismatch", param);
                  ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                  return ae;
                }
              }
            }
          }
        }

        if ((pForm.getOrderNote() != null && pForm.getOrderNote().trim().length() > 0)) {
          orderReq.setOrderNote(I18nUtil.getUtf8Str(pForm.getOrderNote()));
        }

        if (shoppingCartD.getPrevOrderData() != null) {
          OrderData prevod = shoppingCartD.getPrevOrderData();
          if (prevod.getOrderStatusCd() != null &&
              prevod.getOrderStatusCd().equals
              (RefCodeNames.ORDER_STATUS_CD.CANCELLED)) {
            String tnewordermsg = " This order replaces " +
                                  " order number=" + prevod.getOrderNum() +
                                  " , placed by=" + prevod.getAddBy() +
                                  " , original order date=" + prevod.getOriginalOrderDate();

            String tnewmsg = orderReq.getOrderNote();
            if (null == tnewmsg) {
              tnewmsg = "";
            }
            tnewmsg += "  " + tnewordermsg;
            orderReq.setOrderNote(tnewmsg);
            //orderReq.setOrderRefNumber(prevod.getOrderNum());
            orderReq.setRefOrderId(prevod.getOrderId());
          }
        }

        if (Utility.isSet(pForm.getCustomerComment())) {
          orderReq.addArbitraryOrderProperty
            (RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS, pForm.getCustomerComment());
          //pForm.setCustomerComment(null);
        }

        // save checkout field value
            for (BusEntityFieldDataElement checkoutProp : pForm.getCheckoutFieldProperties()){
                    if (Utility.isSet(checkoutProp.getValue())){
                            PropertyData prop = checkoutProp.getPropertyData();
                            orderReq.addArbitraryOrderProperty(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CHECKOUT_FIELD_CD, checkoutProp.getTag(), prop.getValue());
                    }
            }

        if (orderReq.getProperties() == null) {
          orderReq.setProperties(new PropertyDataVector());
        }
        orderReq.setBillingOrder(pForm.isBillingOrder());
        if (pForm.isBillingOrder()) {
          //add dist invoice prop
          PropertyData pd = PropertyData.createValue();
          pd.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
          pd.setPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_DISTRIBUTOR_INVOICE);
          pd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_DISTRIBUTOR_INVOICE);
          pd.setValue(pForm.getBillingDistributorInvoice());
          orderReq.getProperties().add(pd);
          //add bill only orig po num prop
          pd = PropertyData.createValue();
          pd.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
          pd.setPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORIGINAL_PO_NUM);
          pd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORIGINAL_PO_NUM);
          pd.setValue(pForm.getBillingOriginalPurchaseOrder());
          orderReq.getProperties().add(pd);
        }
        //add customerRequestedReshipOrderNum prop
        if (!(pForm.getCustomerRequestedReshipOrderNum() == null ||
              pForm.getCustomerRequestedReshipOrderNum().trim().length() == 0)) {
          PropertyData pd = PropertyData.createValue();
          pd.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
          pd.setPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUST_REQ_RESHIP_ORDER_NUM);
          pd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUST_REQ_RESHIP_ORDER_NUM);
          pd.setValue(pForm.getCustomerRequestedReshipOrderNum());
          orderReq.getProperties().add(pd);
        }

        orderReq.setBypassOrderRouting(pForm.isBypassOrderRouting());
        orderReq.setBypassCustomerWorkflow(pForm.isBypassCustomerWorkflow());

//        log.info("placeOrderVerify() ***************SVC 3: pForm.isBypassBudget() = " + pForm.isBypassBudget());
        if (pForm.isBypassBudget()) {
          orderReq.setOrderBudgetTypeCd(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE);
        } else {
          orderReq.setOrderBudgetTypeCd(null);
        }

         /// add 'RebillOrder' property into 'orderReq'
         setRebillOrderPropertyForOrderRequest(orderReq, pForm);

        //for crc, make sure contact name and phone number are present
        String type = appUser.getUser().getUserTypeCd();
        if (type.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE) ||
            type.equals(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR) ||
            type.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER)) {
          if (pForm.getOrderContactName() == null ||
              pForm.getOrderContactName().trim().length() == 0) {
            String contactNameLabel =
              ClwI18nUtil.getMessage(request, "shop.checkout.text.contactName", null);

            ae.add("error", new ActionError
                   ("variable.empty.error", contactNameLabel));
            return ae;
          } else {
            pForm.setOrderContactName(I18nUtil.getUtf8Str(pForm.getOrderContactName()));
          }
          if (pForm.getOrderContactPhoneNum() == null ||
              pForm.getOrderContactPhoneNum().trim().length() == 0) {
            String contactPhoneNumberLabel =
              ClwI18nUtil.getMessage(request, "shop.checkout.text.contactPhoneNum", null);

            ae.add("error", new ActionError
                   ("variable.empty.error", contactPhoneNumberLabel));
            return ae;
          }
          if (pForm.getOrderOriginationMethod() == null ||
              pForm.getOrderOriginationMethod().trim().length() == 0) {
            String methodLabel =
              ClwI18nUtil.getMessage(request, "shop.checkout.text.method", null);

            ae.add("error", new ActionError
                   ("variable.empty.error", methodLabel));
            return ae;
          }
          saveContactInfo(request, pForm);

        } else {
          pForm.setOrderContactName(appUser.getUser().getFirstName() + " " + appUser.getUser().getLastName());
        }
        orderReq.setOrderContactName(I18nUtil.getUtf8Str(pForm.getOrderContactName()));

        orderReq.setOrderTelephoneNumber(pForm.getOrderContactPhoneNum());
        orderReq.setOrderEmail(pForm.getOrderContactEmail());
        String orderSourceCd = pForm.getOrderOriginationMethod();
        OrderData prevOrderD = shoppingCartD.getPrevOrderData();
        if (prevOrderD != null) {
          String prevOrderSourceCd = prevOrderD.getOrderSourceCd();
          if (RefCodeNames.ORDER_SOURCE_CD.INVENTORY.equals(prevOrderSourceCd)) {
            orderSourceCd = prevOrderSourceCd;
          }
        }
        orderReq.setOrderSourceCd(orderSourceCd);
      } catch (Exception e2) {
        e2.printStackTrace();
        ae.add("error", new ActionError("error.systemError", "Page expired 2"));
        return ae;
      }
      //Verifiy quantities for consolidated order
      boolean consolidatedOrderFl = false;
      if (shoppingCartD instanceof ConsolidatedCartView) {
        consolidatedOrderFl = true;
        HashMap itemHM = new HashMap();
        ArrayList sourceOrders = ((ConsolidatedCartView) shoppingCartD).getOrders();
        for (Iterator iter = sourceOrders.iterator(); iter.hasNext(); ) {
          OrderJoinData ojD = (OrderJoinData) iter.next();
          OrderItemJoinDataVector oijDV = ojD.getOrderJoinItems();
          for (Iterator iter1 = oijDV.iterator(); iter1.hasNext(); ) {
            OrderItemJoinData oijD = (OrderItemJoinData) iter1.next();
            OrderItemData oiD = oijD.getOrderItem();
            Integer skuNumI = new Integer(oiD.getItemSkuNum());

            int qty = oiD.getTotalQuantityOrdered();
            Integer qtyI = (Integer) itemHM.get(skuNumI);
            if (qtyI == null) {
              qtyI = new Integer(qty);
            } else {
              qtyI = new Integer(qtyI.intValue() + qty);
            }
            itemHM.put(skuNumI, qtyI);
          }
        }
        List reqItems = shoppingCartD.getItems();
        String addedSkus = "";
        int addedCount = 0;
        String modifiedSkus = "";
        int modifiedCount = 0;
        String removedSkus = "";
        int removedCount = 0;
        for (Iterator iter = reqItems.iterator(); iter.hasNext(); ) {
          ShoppingCartItemData sciD =
            (ShoppingCartItemData) iter.next();
          Integer skuNumI = new Integer(sciD.getProduct().getItemData().getSkuNum());
          int qty = sciD.getQuantity();
          Integer sQtyI = (Integer) itemHM.get(skuNumI);
          if (sQtyI == null) {
            if (addedCount > 0) addedSkus += ",";
            addedSkus += skuNumI;
            addedCount++;
          } else {
            if (qty != sQtyI.intValue()) {
              if (modifiedCount > 0) modifiedSkus += ",";
              modifiedSkus += skuNumI;
              modifiedCount++;
            }
            itemHM.remove(skuNumI);
          }
        }
        if (!itemHM.isEmpty()) {
          Set skus = itemHM.keySet();
          for (Iterator iter = skus.iterator(); iter.hasNext(); ) {
            Integer skuNumI = (Integer) iter.next();
            if (removedCount > 0) removedSkus += ",";
            removedSkus += skuNumI;
            removedCount++;
          }
        }
        if (addedCount > 0 || removedCount > 0 || modifiedCount > 0) {
          String errorMess =
            ClwI18nUtil.getMessage(request, "shop.errors.consolidatedOrderCannotBeModified", null);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          if (addedCount > 0) {
            Object[] param = new Object[1];
            param[0] = addedSkus;
            errorMess =
              ClwI18nUtil.getMessage(request, "shop.errors.addedSkus", param);
            ae.add("errorAdd", new ActionError("error.simpleGenericError", errorMess));
          }
          if (removedCount > 0) {
            Object[] param = new Object[1];
            param[0] = removedSkus;
            errorMess =
              ClwI18nUtil.getMessage(request, "shop.errors.removedSkus", param);
            ae.add("errorRem", new ActionError("error.simpleGenericError", errorMess));
          }
          if (modifiedCount > 0) {
            Object[] param = new Object[1];
            param[0] = modifiedSkus;
            errorMess =
              ClwI18nUtil.getMessage(request, "shop.errors.modifiedSkus", param);
            ae.add("errorMod", new ActionError("error.simpleGenericError", errorMess));
          }
          return ae;
        }
        //Poupulate consolidating order ids
        IdVector replacedOrderIds = new IdVector();
        for (Iterator iter = sourceOrders.iterator(); iter.hasNext(); ) {
          OrderJoinData ojD = (OrderJoinData) iter.next();
          int orderId = ojD.getOrderId();
          replacedOrderIds.add(new Integer(orderId));
        }
        orderReq.setReplacedOrderIds(replacedOrderIds);

        //Set order type
        orderReq.setOrderType(RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED);
      }
      //If order is replacing another one
      OrderData replacedOrderD = shoppingCartD.getPrevOrderData();
      if (replacedOrderD != null) {
        IdVector replacedOrderIds = new IdVector();
        replacedOrderIds.add(new Integer(replacedOrderD.getOrderId()));
        orderReq.setReplacedOrderIds(replacedOrderIds);
      }

      //If order is for a WorkOrderItem orderReq.getSiteId();
      WorkOrderItemData workOrderItemD = shoppingCartD.getWorkOrderItem();
      if (workOrderItemD != null && workOrderItemD.getWorkOrderItemId() > 0) {
          if (orderReq.getSiteId() != workOrderItemD.getBusEntityId()) {
              String errorMessage = ClwI18nUtil.getMessageOrNull(request, "shop.errors.mustBelongToSameSite");
              if (errorMessage == null) {
                  errorMessage = "Web Order and Work Order must belong to the same site.";
              }
              ae.add("error", new ActionError("error.simpleGenericError", errorMessage));
              return ae;
          }
          WorkOrderData wo = workOrderEjb.getWorkOrder(workOrderItemD.getWorkOrderId());
          String woStatus = wo.getStatusCd();
          if (RefCodeNames.WORK_ORDER_STATUS_CD.CLOSED.equals(woStatus) ||
              RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED.equals(woStatus) ||
              RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED.equals(woStatus)) {

              String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.inapropriateWorkOrderStatus", new Object[] {woStatus});
              ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
          }
          orderReq.setWorkOrderItemId(workOrderItemD.getWorkOrderItemId());
          shoppingCartD.setWorkOrderItem(null);
          pForm.setWorkOrderNumber("");
      }

//      IntegrationServices isvcEjb = null;
//
//      //ShoppingServices shoppingServeicesEjb = null;
//      String emsg = "";
//      try {
//        emsg = "No IntegrationServices API Access";
//        isvcEjb = factory.getIntegrationServicesAPI();
//      } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
//        ae.add("error", new ActionError("error.systemError", emsg));
//        return ae;
//      }

      Integer catalogIdI = (Integer)
                           session.getAttribute(Constants.CATALOG_ID);
      if (catalogIdI == null) {
        ae.add("error", new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session attrbute found"));
        return ae;
      }

      pForm.setSite(site);
      pForm.setAccount(account);
      pForm.setOrderRequest(orderReq);

      return ae;
    }

    //****************************************************************************
     public static ActionErrors placeOrderSave(HttpServletRequest request,
                                           CheckoutForm pForm) throws Exception {

         Date stime = checkoutDbg(request, "placeOrder START", null);
         ActionErrors ae = new ActionErrors();
         HttpSession session = request.getSession();

         CleanwiseUser appUser = (CleanwiseUser)
                                 session.getAttribute(Constants.APP_USER);

         APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
       if (factory == null) {
         ae.add("error", new ActionError("error.systemError", "No Ejb access"));
         return ae;
       }

       IntegrationServices isvcEjb = null;

       //ShoppingServices shoppingServeicesEjb = null;
       String emsg = "";
       try {
         emsg = "No IntegrationServices API Access";
         isvcEjb = factory.getIntegrationServicesAPI();
       } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
         ae.add("error", new ActionError("error.systemError", emsg));
         return ae;
       }

       Order orderEjb = factory.getOrderAPI();
        try {
         orderEjb = factory.getOrderAPI();
       } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
         ae.add("error", new ActionError("error.systemError", "No order API Access"));
         return ae;
       }

       ShoppingCartData shoppingCartD = ShopTool.getCurrentShoppingCart(session);

       OrderData replacedOrderD = shoppingCartD.getPrevOrderData();

       boolean consolidatedOrderFl = false;
       if (shoppingCartD instanceof ConsolidatedCartView) {
           consolidatedOrderFl = true;
       }

       Integer catalogIdI = (Integer)
                            session.getAttribute(Constants.CATALOG_ID);
       if (catalogIdI == null) {
         ae.add("error", new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session attrbute found"));
         return ae;
       }
       CustomerOrderRequestData orderReq = pForm.getOrderRequest();

       ProcessOrderResultData orderRes = null;
       String lockName = "==CheckOutLock==";
       Integer lockValue = null;

       try {
         Object oforlock = new Object();
         synchronized (oforlock) {
           lockValue = (Integer) session.getAttribute(lockName);
           if (lockValue == null) {
             lockValue = new Integer("0");
             session.setAttribute(lockName, lockValue);
           }
         }
         if (lockValue.intValue() == 0) {
           // Lock the purchase capability.
           synchronized (oforlock) {
             lockValue = new Integer("1");
             session.setAttribute(lockName, lockValue);
           }

           //deal with inline customer ordering that requieres system pass off (cXML)
           if (doInlineOrderInit(ae, pForm, session, factory, appUser)) {
             checkoutDbg(request, "placeOrder processOrderRequest 0", stime);
             pForm.setOrderRequest(orderReq);
             orderRes = isvcEjb.processOrderRequest(orderReq);
             checkoutDbg(request, "placeOrder processOrderRequest 1", stime);
             if (orderRes.getOrderId() == 0) {
               String errorMess =
                 ClwI18nUtil.getMessage(request, "shop.errors.orderRejected", null);
               String[] messA = orderRes.getMessages();
               if (messA != null && messA.length > 0) {
                 for (int ii = 0; ii < messA.length; ii++) {
                   String translatedMess = ClwI18nUtil.formatEjbError(request, messA[ii]);
                   if (translatedMess != null && translatedMess.length() > 0) {
                     errorMess += ". " + translatedMess;
                   }
                 }
               }
               ae.add("error", new
                      ActionError("error.simpleGenericError", errorMess));
               return ae;
             }
           }
         } else {
           // The lock value is 1.  Meaning a purchase is
           // already in progress.
           String errorMess =
             ClwI18nUtil.getMessage(request, "shop.errors.purchaseIsInProgress", null);
           ae.add("error", new ActionError("error.simpleGenericError", errorMess));
           return ae;
         }

         if (!(ae.size() == 0)) {
           return ae;
         }

         if (consolidatedOrderFl) {
           int orderId = orderRes.getOrderId();
           ReplacedOrderViewVector replOrderVwV =
             orderEjb.getReplacedOrders(orderId);
           pForm.setReplacedOrders(replOrderVwV);
         }

         pForm.setConfirmationFlag(true);
         pForm.setOrderResult(orderRes);
//         pForm.setSite(site);
//         pForm.setAccount(account);
         pForm.setItems(shoppingCartD.getItems());

         try{

             int accId = pForm.getAccount().getAccountId();

             SessionTool st = new SessionTool(request);
             Site sbean = st.getFactory().getSiteAPI();

             ShoppingControlDataVector shopCtrls = pForm.getSite().getShoppingControls();

             ShoppingCartItemDataVector sciDV = shoppingCartD.getItems();
             for(int i=0; i<sciDV.size(); i++){
                     ShoppingCartItemData thisItem = (ShoppingCartItemData)sciDV.get(i);

                     int itemId = thisItem.getItemId();
                     int qtyOrdered = thisItem.getQuantity();

                     for(int s=0; s<shopCtrls.size(); s++){
                             ShoppingControlData sdata = (ShoppingControlData)shopCtrls.get(s);

                             if(sdata.getItemId()==itemId){
                                     int newActualMaxQty = sdata.getActualMaxQty() - qtyOrdered;

                                     sdata.setActualMaxQty(newActualMaxQty);
                                     //sdata.setExpDate(null);
                                     //sdata.setHistoryOrderQty(0);

                             }
                     }
                     sbean.updateShoppingControls(shopCtrls, false);




             }
             // addind DeliveryDate into Order_Meta
             String deliveryDateS = Utility.convertFullDate2Short(pForm.getDeliveryDate());
             if(orderRes!=null && orderRes.getOrderId()>0){
             log.info("CheckoutLogic >> placeOrder : UserName :" +
                                appUser.getUser().getUserName() +
                                ", OrderId = " + orderRes.getOrderId() +
                                ", deliveryDate =" + deliveryDateS);
             }
             if (Utility.isSet(deliveryDateS) ){
               sbean.addDeliveryDateForSite(deliveryDateS, orderRes.getOrderId(),
                                          appUser.getUser().getUserName());
             }
         }catch(Exception e){
             e.printStackTrace();
             //throw new Exception(e.getMessage());
         }

         //Remove items from shopping cart
         shoppingCartD.setItems(new ShoppingCartItemDataVector());

         shoppingCartD.setPrevOrderData(null);
         if (shoppingCartD instanceof ConsolidatedCartView) {
           ((ConsolidatedCartView) shoppingCartD).setOrders(new ArrayList());
         }
         //checkoutDbg(request, "placeOrder saveShoppingCart", stime);
         //ae = ShoppingCartLogic.saveShoppingCart(site,session,orderRes);
         if (replacedOrderD != null) {
           ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();
           //shoppingServEjb.createShoppingInfo (shoppingCartD,
           //    orderReq.getUserName(), orderRes, pForm.getItems());
           shoppingServEjb.saveModifiedOrderShoppingInfo(shoppingCartD,
             orderReq.getUserName(), orderRes);
         } else {
           ae = ShoppingCartLogic.saveShoppingCart(pForm.getSite(), session, orderRes);
         }



         // Unlock the purchase capability.
         lockValue = new Integer("0");
         session.setAttribute(lockName, lockValue);
         //checkoutDbg(request, "placeOrder DONE", stime);
       }

       catch (Exception exc) {
         exc.printStackTrace();
         log.error("CheckoutLogic: placeOrderSave ERRROR 2 MESSAGE="+exc.getMessage());
         return mkUserOrderMessage(request, exc.getMessage());
       } finally {
         //unlock the order functionality
         lockValue = new Integer("0");
         session.setAttribute(lockName, lockValue);
       }
       return ae;
     }




   /**
    *Validates that the po number that was requested by the customer is correct.  This is based off the
    *configuration of the account and may be overided by the logic template code lookup mechanism:
    *Rules currently implemented are unique po number for an account.
    *
    * @return any errors or an empty ActionErrors object
    */
  public ActionErrors validatePoNum (HttpServletRequest request, CheckoutForm pForm) {
	  HttpSession session = request.getSession();
	  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	  String poNum = pForm.getPoNumber();
	  String uniquePoReq =  appUser.getUserAccount().getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.UNIQUE_PO_NUM_DAYS);

	  ActionErrors ae = new ActionErrors();
	  try{
		  if(Utility.isSet(uniquePoReq)){
			  int uniquePoDays = Integer.parseInt(uniquePoReq);
			  //this account requiers a unique po number.  This will be validated against the database
			  if(uniquePoDays > 0 && Utility.isSet(poNum)){
				  Date fromDate = Utility.addDays(new Date(), uniquePoDays * -1);
				  APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
				  OrderDataVector orders = factory.getOrderAPI().getOrderByCustomerPoNumber(poNum,appUser.getUserAccount().getAccountId(),fromDate);
				  if(orders != null && orders.size() > 0){
					  String errorMess =ClwI18nUtil.getMessage(request, "shop.errors.poNumberMustBeUnique", null);
				      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
				  }
			  }
		  }
	  }catch(Exception e){
		  log.error(e);
	  }
	  return ae;
    }

   private static ActionErrors setupFreightTable (HttpServletRequest request,
           CheckoutForm pForm, CustomerOrderRequestData orderReq)
   {
     //Liang - set up the order freight table according to distributors
     // only for stores other than MLA
     ActionErrors ae = new ActionErrors();

     HttpSession session = request.getSession();

     CleanwiseUser appUser =
           (CleanwiseUser) session.getAttribute(Constants.APP_USER);

     boolean isaMLAStore = RefCodeNames.STORE_TYPE_CD.MLA.equals
                           (appUser.getUserStore().getStoreType().getValue());

     ae = recalculateDependencies(request, pForm, false);
     if(ae.size()>0) {
         return ae;
     }
     if (isaMLAStore) {
         return ae;
     }
     
     //Total Freight/Handling for order
     BigDecimal totalFreight = new BigDecimal(0);
     BigDecimal totalHandling = new BigDecimal(0);
     
     OrderFreightDataVector orderFreightList = null;
     ShoppingCartDistDataVector cartDistributors = pForm.getCartDistributors();
     if (null != cartDistributors && 0 < cartDistributors.size()) {
         orderFreightList = new OrderFreightDataVector();
         for (int i = 0; i < cartDistributors.size(); i++) {
             ShoppingCartDistData distD = (ShoppingCartDistData) cartDistributors.get(i);
             List impliedFreight = null;
             FreightTableCriteriaData selectedFreight = null;
             if (null != distD) {
                impliedFreight = distD.getDistFreightImplied();
                selectedFreight = distD.getDistSelectedFreight();
             }
             if (null != impliedFreight && 0 < impliedFreight.size()) {
                for (int j = 0; j < impliedFreight.size(); j++) {
                    FreightTableCriteriaData crit = (FreightTableCriteriaData) impliedFreight.get(j);
                    OrderFreightData orderFreightD = OrderFreightData.createValue();
                    orderFreightD.setBusEntityId(distD.getDistributor().getBusEntityId());
                    orderFreightD.setFreightTypeCd(crit.getFreightCriteriaTypeCd());
                    orderFreightD.setShortDesc(crit.getShortDesc());
                    crit.getFreightCriteriaTypeCd();
                    orderFreightD.setAmount(crit.getFreightAmount()!=null?
                                            crit.getFreightAmount():
                                            crit.getHandlingAmount());
                    orderFreightD.setFreightHandlerId(crit.getFreightHandlerId());
                    orderFreightList.add(orderFreightD);
                }
            }
            if (null != selectedFreight) {
                OrderFreightData orderFreightD = OrderFreightData.createValue();
                orderFreightD.setBusEntityId(distD.getDistributor().getBusEntityId());
                orderFreightD.setFreightTypeCd(selectedFreight.getFreightCriteriaTypeCd());
                orderFreightD.setShortDesc(selectedFreight.getShortDesc());
                orderFreightD.setAmount(selectedFreight.getFreightAmount()!=null?
                                        selectedFreight.getFreightAmount():
                                        selectedFreight.getHandlingAmount());
                orderFreightD.setFreightHandlerId(selectedFreight.getFreightHandlerId());
                orderFreightList.add(orderFreightD);
                
                if(selectedFreight.getFreightAmount()!=null){
                	totalFreight = Utility.addAmt(totalFreight,selectedFreight.getFreightAmount());
                }
                if(selectedFreight.getHandlingAmount()!=null){
                	totalHandling = Utility.addAmt(totalHandling,selectedFreight.getHandlingAmount());
                }
           }
         }
       }

       if (!totalFreight.equals(ZERO))
    	   pForm.setFreightAmt(totalFreight);
       if (!totalHandling.equals(ZERO))
    	   pForm.setHandlingAmt(totalHandling);
	   
       if (null != orderFreightList && 0 < orderFreightList.size() && orderReq != null) {
         orderReq.setOrderFreightList(orderFreightList);
       }
     
       return ae;
   }

   private static ActionErrors setupOrderAddOnChargeTable (HttpServletRequest request,
           CheckoutForm pForm, CustomerOrderRequestData orderReq)
   {
     //Set up Discount for the CLW_ORDER_ADD_ON_CHARGE DB table per distributor
     // only for stores other than MLA ??? - Do I need it ???
     ActionErrors ae = new ActionErrors();

     HttpSession session = request.getSession();

     CleanwiseUser appUser =
           (CleanwiseUser) session.getAttribute(Constants.APP_USER);

     boolean isaMLAStore = RefCodeNames.STORE_TYPE_CD.MLA.equals
                           (appUser.getUserStore().getStoreType().getValue());

     ae = recalculateDependencies(request, pForm, false); //??? Do I need to recalculate the dependencies here ?

     if(ae.size()>0) {
         return ae;
     }

     /*** Do I need the check for an MLA store here ? ***/
     if (isaMLAStore) {
         return ae;
     }
     OrderAddOnChargeDataVector orderDiscountList = null;
     ShoppingCartDistDataVector cartDistributors = pForm.getCartDistributors();

     //log.info("PPPPPPP cartDistributors = " + cartDistributors);

     log.info("Data for the CLW_ORDER_ADD_ON_CHARGE Table; cartDistributors: "+cartDistributors);

     HashMap<Integer,BigDecimal> discAmtPerDistHM = pForm.getDiscountAmtPerDist();

     log.info("Discount(s) per Distributor(s) = " + discAmtPerDistHM);

     if (null != cartDistributors && 0 < cartDistributors.size() && discAmtPerDistHM.size() > 0) {
         orderDiscountList = new OrderAddOnChargeDataVector();
         for (int i = 0; i < cartDistributors.size(); i++) {
             ShoppingCartDistData distD = (ShoppingCartDistData) cartDistributors.get(i);
             List impliedDiscount = null;
             if (null != distD) {
                impliedDiscount = distD.getDistDiscountImplied();
             }
             if (null != impliedDiscount && 0 < impliedDiscount.size()) {
                for (int j = 0; j < impliedDiscount.size(); j++) {
                	// I add orderId to the OrderAddOnChargeD Object (see below) in the EndSynchPipeline class
                    FreightTableCriteriaData crit = (FreightTableCriteriaData) impliedDiscount.get(j);
                    OrderAddOnChargeData OrderAddOnChargeD = OrderAddOnChargeData.createValue();
                    OrderAddOnChargeD.setBusEntityId(distD.getDistributor().getBusEntityId());
                    OrderAddOnChargeD.setDistFeeTypeCd(crit.getFreightCriteriaTypeCd());
                    OrderAddOnChargeD.setShortDesc(crit.getShortDesc());
                    OrderAddOnChargeD.setDistFeeChargeCd(RefCodeNames.CHARGE_CD.DISCOUNT);

                    Integer distIdInt = new Integer( distD.getDistributor().getBusEntityId() );
            	    BigDecimal distIdBD = new BigDecimal(0);
            	    distIdBD = (BigDecimal)discAmtPerDistHM.get(distIdInt);
            	    distIdBD = distIdBD.setScale(2, BigDecimal.ROUND_HALF_UP);
            	    // prepare negative discount value to be stored in the Database
            	    BigDecimal zeroValue = new BigDecimal(0);
            	    if ( distIdBD.compareTo(zeroValue)>0 ) {
            	    	distIdBD = distIdBD.negate();
            	    }
            	    log.info("SSSS distIdBD = " + distIdBD);
                    OrderAddOnChargeD.setAmount(distIdBD);            	    	

                    
                    orderDiscountList.add(OrderAddOnChargeD);
                }
            }
         }
       }

       log.info("NNNNN orderDiscountList = " + orderDiscountList);

       if (null != orderDiscountList && 0 < orderDiscountList.size() && orderReq != null) {
         orderReq.setOrderDiscountList(orderDiscountList);
       }
       return ae;
   }

   /**
   *Returns true if order should be placed false otherwise
   */
  private static boolean doInlineOrderInit(ActionErrors ae, CheckoutForm pForm, HttpSession session, APIAccess factory,
                                           CleanwiseUser appUser) {

	boolean performPunchout = RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.equals(appUser.getUserAccount().
		      getCustomerSystemApprovalCd());
    if (performPunchout) {
      pForm.setCustomerSystemId((String) session.getAttribute(Constants.CUSTOMER_SYSTEM_ID));

      //inline type customer accounts need to have this property set.
      String custSysUrl = (String) session.getAttribute(Constants.CUSTOMER_SYSTEM_URL);
      if ((custSysUrl == null) || (custSysUrl.trim().length() == 0)) {
        ae.add("error", new ActionError("error.noCustSysData"));
        return false;
      }

      AccountData account = appUser.getUserAccount();
      //perform the translation
      //This is here for the cXML accounts.  At this point the actual implementation
      // may change to be more server driven.  This is here as a temporary work around untill
      // the real method of implementation is decided upon (I hope)
      try {
        TradingPartner tpEjb = factory.getTradingPartnerAPI();
        IntegrationServices isEjb = factory.getIntegrationServicesAPI();
        java.io.ByteArrayOutputStream bout = new java.io.ByteArrayOutputStream();

        OutboundTranslate translator = new OutboundTranslate(account.getBusEntity().getErpNum(), account.getBusEntity().getBusEntityId(),
          RefCodeNames.EDI_TYPE_CD.TPUNCH_OUT_ORDER_OUT, isEjb, tpEjb, bout);
        OutboundEDIRequestDataVector orders = new OutboundEDIRequestDataVector();
        OutboundEDIRequestData toTranslate = OutboundEDIRequestData.createValue();

        OrderData punchOutOrder = OrderData.createValue();
        punchOutOrder.setTotalPrice(pForm.getCartAmt());
        punchOutOrder.setTotalMiscCost(pForm.getMiscAmt());
        BigDecimal frt = new BigDecimal(0.00);
        frt.setScale(2);
        if(pForm.getHandlingAmt() != null){
            frt = frt.add(pForm.getHandlingAmt());
        }
        if(pForm.getFreightAmt() != null){
            frt = frt.add(pForm.getFreightAmt());
        }
        punchOutOrder.setTotalFreightCost(frt);
        punchOutOrder.setComments("");
        punchOutOrder.setTotalTaxCost(new BigDecimal(0));
        punchOutOrder.setLocaleCd(appUser.getUser().getPrefLocaleCd());
        CurrencyData currD = I18nUtil.getCurrency(appUser.getUser().getPrefLocaleCd());
        if (currD != null) {
        	punchOutOrder.setCurrencyCd(currD.getGlobalCode());
        }

        toTranslate.setOrderD(punchOutOrder);

        OrderPropertyDataVector opdv = new OrderPropertyDataVector();
        toTranslate.setOrderPropertyDV(opdv);

        OrderPropertyData opd = OrderPropertyData.createValue();
        opd.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_SYSTEM_ID);
        opd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_SYSTEM_ID);
        opd.setValue(pForm.getCustomerSystemId());
        opdv.add(opd);

        Iterator it = pForm.getItems().iterator();
        OrderItemDataVector oidv = new OrderItemDataVector();
        toTranslate.setOrderItemDV(oidv);
        Map genericMap = new HashMap();
        toTranslate.setGenericMap(genericMap);
        while(it.hasNext()){
            ShoppingCartItemData itm = (ShoppingCartItemData) it.next();
            OrderItemData oid = OrderItemData.createValue();
            oidv.add(oid);
            oid.setTotalQuantityOrdered(itm.getQuantity());
            oid.setCustItemSkuNum(itm.getProduct().getActualCustomerSkuNum());
            oid.setManuItemSkuNum(itm.getProduct().getManufacturerSku());
            oid.setDistItemSkuNum(itm.getProduct().getCatalogDistrMapping().getItemNum());
            oid.setDistItemUom(itm.getProduct().getCatalogDistrMapping().getItemUom());            
            oid.setItemSkuNum(itm.getProduct().getSkuNum());
            oid.setItemShortDesc(itm.getProduct().getShortDesc());
            oid.setCustContractPrice(new BigDecimal(itm.getPrice()));
            oid.setItemUom(itm.getProduct().getUom());
            oid.setItemId(itm.getProduct().getItemData().getItemId());
            if (Utility.isSet(itm.getProduct().getUnspscCd())){
	            genericMap.put("UNSPSC_CD"+":" + oid.getItemId(), itm.getProduct().getUnspscCd());
            }
        }

        orders.add(toTranslate);

        /*translator.translate(orders, null, account.getBusEntity().getBusEntityId(),
                             RefCodeNames.EDI_TYPE_CD.TPUNCH_OUT_ORDER_OUT);*/
         log.info(Thread.currentThread().getContextClassLoader());
        translator.translate(orders, account.getBusEntity().getErpNum(), account.getBusEntity().getBusEntityId(),
                RefCodeNames.EDI_TYPE_CD.TPUNCH_OUT_ORDER_OUT);
        pForm.setInlinePostFieldName("cXML-urlencoded");

        String translationResultStr = new String(bout.toByteArray());
        
        //if the account is set up to do so, create a history record of the punchout order message
        if (Utility.isTrue(account.getTrackPunchoutOrderMessages())) {
        	recordPunchoutOrderMessage(appUser, translationResultStr);
        }
        translationResultStr = translationResultStr.replace('\n', ' ');
        translationResultStr = translationResultStr.replace('\f', ' ');
        translationResultStr = translationResultStr.replace('\r', ' ');
        translationResultStr = org.apache.struts.util.ResponseUtils.filter(translationResultStr);

        pForm.setInlinePostData(translationResultStr);
      } catch (Exception exc) {
        exc.printStackTrace();
        ae.add("error", new ActionError("error.systemError", exc.getLocalizedMessage()));
      }
    } else {
      //order should in fact be placed
      return true;
    }
    //order should not be placed
    return false;
  }
  
  	private static void recordPunchoutOrderMessage(CleanwiseUser appUser, String punchoutOrderMessage) {
  		try {
  			AccountData account = appUser.getUserAccount();
  	        if (Utility.isTrue(account.getTrackPunchoutOrderMessages())) {
  	  			//create the history data, history security data, and history object data objects needed
  	  			//NOTE - these objects must be set up to match the MANTA CreatePunchoutOrderMessageHistoryRecord
  	  			//class.  *** Do NOT change this method without making corresponding changes in MANTA! ***
  				HistoryData historyData = new HistoryData();
  				Date date = new Date();
  				historyData.setActivityDate(date);
  				historyData.setAddBy(appUser.getUserName());
  				historyData.setAddDate(date);
  	  	      	historyData.setHistoryTypeCd(RefCodeNames.HISTORY_TYPE_CD.CREATE_PUNCHOUT_ORDER_MESSAGE);
  				historyData.setModBy(appUser.getUserName());
  				historyData.setModDate(date);
  	  	        historyData.setUserId(appUser.getUserName());
  				SiteData location = appUser.getSite();
  				if (location != null) {
  					historyData.setAttribute01(location.getSiteId() + "");
  					historyData.setAttribute02(location.getBusEntity().getShortDesc());
  				}
  				historyData.setAttribute03(account.getAccountId() + "");
  				historyData.setAttribute04(account.getBusEntity().getShortDesc());
  				historyData.setAttribute05(appUser.getUserStore().getStoreId() + "");
  				historyData.setAttribute06(appUser.getUserStore().getBusEntity().getShortDesc());
  				historyData.setClob1(punchoutOrderMessage);
  				List<HistoryObjectData> historyObjectDatas = new ArrayList<HistoryObjectData>();
  				if (location != null) {
  					HistoryObjectData locationObject = new HistoryObjectData();
  					locationObject.setObjectId(location.getSiteId());
  					locationObject.setObjectTypeCd(RefCodeNames.HISTORY_OBJECT_TYPE_CD.LOCATION);
  					locationObject.setAddBy(appUser.getUserName());
  					locationObject.setAddDate(date);
  					locationObject.setModBy(appUser.getUserName());
  					locationObject.setModDate(date);
  					historyObjectDatas.add(locationObject);
  				}
				HistoryObjectData accountObject = new HistoryObjectData();
				accountObject.setObjectId(account.getAccountId());
				accountObject.setObjectTypeCd(RefCodeNames.HISTORY_OBJECT_TYPE_CD.ACCOUNT);
				accountObject.setAddBy(appUser.getUserName());
				accountObject.setAddDate(date);
				accountObject.setModBy(appUser.getUserName());
				accountObject.setModDate(date);
				historyObjectDatas.add(accountObject);
				HistoryObjectData storeObject = new HistoryObjectData();
				storeObject.setObjectId(appUser.getUserStore().getStoreId());
				storeObject.setObjectTypeCd(RefCodeNames.HISTORY_OBJECT_TYPE_CD.STORE);
				storeObject.setAddBy(appUser.getUserName());
				storeObject.setAddDate(date);
				storeObject.setModBy(appUser.getUserName());
				storeObject.setModDate(date);
				historyObjectDatas.add(storeObject);
  				List<HistorySecurityData> historySecurityDatas = new ArrayList<HistorySecurityData>();
  				HistorySecurityData accountSecurity = new HistorySecurityData();
  				accountSecurity.setBusEntityId(account.getAccountId());
  				accountSecurity.setBusEntityTypeCd(RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
  				accountSecurity.setAddBy(appUser.getUserName());
  				accountSecurity.setAddDate(date);
  				accountSecurity.setModBy(appUser.getUserName());
  				accountSecurity.setModDate(date);
  				historySecurityDatas.add(accountSecurity);
  				History historyEjb = APIAccess.getAPIAccess().getHistoryAPI();
  				historyEjb.addHistory(historyData, historyObjectDatas, historySecurityDatas);
  			}
  		}
  		catch (Exception e) {
  			log.error("Exception occurred in recordPunchoutOrderMessage", e);
  		}
  	}


  private static ActionErrors mkUserOrderMessage(HttpServletRequest request, String pMsg) {
    ActionErrors ae = new ActionErrors();
    String errorMess = ClwI18nUtil.formatEjbError(request, pMsg);
    log.error("CheckoutLogic: mkUserOrderMessage errorMess="+errorMess+" pMsg="+pMsg);
    if (errorMess != null && errorMess.length() > 0) {
      log.error("CheckoutLogic: mkUserOrderMessage 1");
      ae.add("Error",
             new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }
    log.error("CheckoutLogic: mkUserOrderMessage 2");
    if (pMsg == null) pMsg = "";
    String m = "", m1 = "";
    StringTokenizer st = new StringTokenizer(pMsg, ";");
    if (st.hasMoreTokens()) {
      m = st.nextToken();
    }
    int idx = m.indexOf(":");
    if (idx > 0){
    	m1 = m.substring(idx+1);
    }
    log.error("CheckoutLogic: mkUserOrderMessage m="+m+" m1="+m1);
    if (m.startsWith("BudgetRuleException")) {
      ae.add("error", new ActionError("order.refused.budgetmsg", m1));
    } else if (m.startsWith("OrderTotalException")) {
    	String param = Utility.strNN(m1).trim();
    	if (param.toLowerCase().startsWith("order total ")) {
    		param = param.substring(11);
    	}
      ae.add("error", new ActionError
             ("order.refused.order_totalmsg", param));
    } else if (m.startsWith("BudgetYTDRuleException")) {
      ae.add("error", new ActionError
              ("error.simpleGenericError", m1));
    } else if (m.startsWith("OrderWorkflowException")) {
      ae.add("error", new ActionError
             ("order.refused.workflow_msg", m1));
    } else {
      ae.add("error", new ActionError("order.refused.msg"));
    }
    return ae;
  }

  //*****************************************************************************
   private static ActionErrors ccValidation(CheckoutForm pForm, HttpServletRequest request, boolean isStateProvinceRequired) {
     ActionErrors ae = new ActionErrors();
     String ccNumber = pForm.getCcNumber();
     String ccType = pForm.getCcType();
     int ccExpMonth = pForm.getCcExpMonth();
     int ccExpYear = pForm.getCcExpYear();
     String ccPersonName = pForm.getCcPersonName();
     String ccStreet1 = pForm.getCcStreet1();
     String ccCity = pForm.getCcCity();
     String ccState = pForm.getCcState();
     String ccZipCd = pForm.getCcZipCode();
     OrderCreditCardData cc = OrderCreditCardData.createValue();
     cc.setExpMonth(ccExpMonth);
     cc.setExpYear(ccExpYear);
     cc.setName(ccPersonName);
     cc.setAddress1(ccStreet1);
     cc.setCity(ccCity);
     cc.setStateProvinceCd(ccState);
     cc.setPostalCode(ccZipCd);
     cc.setCreditCardType(ccType);
     CreditCardUtil ccU = new CreditCardUtil(ccNumber, cc, isStateProvinceRequired);
     if (!ccU.isValid()) {
       if (ccU.getValidationMessageReasourceErrorMessage() == null) {
         String errorMess =
           ClwI18nUtil.getMessage(request, ccU.getValidationMessageReasourceErrorMessage(), null);
         ae.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.simpleGenericError", errorMess));
       } else if (ccU.getValidationErrorField() != null) {
         String[] args = new String[1];
         args[0] = ccU.getValidationErrorField();
         String errorMess =
           ClwI18nUtil.getMessage(request, ccU.getValidationMessageReasourceErrorMessage(), args);
         ae.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.simpleGenericError", errorMess));
       } else {
         String errorMess =
           ClwI18nUtil.getMessage(request, ccU.getValidationMessageReasourceErrorMessage(), null);
	       ae.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.simpleGenericError", errorMess));
       }
     }
     return ae;
   }

  //************************************************************************
   private static ActionErrors ccEncrypt(CheckoutForm pForm) {
     ActionErrors ae = new ActionErrors();
     /*
         byte[] raw = Constants.CC_BYTES;
         SecretKey desKey = new SecretKeySpec(raw, "Blowfish");

         // Create the cipher
         Cipher desCipher = null;
         try {
           desCipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
         } catch(NoSuchPaddingException exc) {
            exc.printStackTrace();
            ae.add("error",new ActionError("error.systemError",exc.getMessage()));
            return ae;
         } catch(NoSuchAlgorithmException exc) {
            exc.printStackTrace();
            ae.add("error",new ActionError("error.systemError",exc.getMessage()));
            return ae;
         } catch(ExceptionInInitializerError exc) {
            exc.printStackTrace();
            ae.add("error",new ActionError("error.systemError",exc.getMessage()));
            return ae;
         } catch(SecurityException exc) {
            exc.printStackTrace();
            ae.add("error",new ActionError("error.systemError",exc.getMessage()));
            return ae;
         }
         try {
           desCipher.init(Cipher.ENCRYPT_MODE, desKey);
         } catch(InvalidKeyException exc) {
            exc.printStackTrace();
            ae.add("error",new ActionError("error.systemError",exc.getMessage()));
            return ae;
         }
         byte[] encrypted = new byte[0];
         String ccNumber = pForm.getCcNumber();
         if(ccNumber==null || ccNumber.trim().length()<=4) {
           return ae;
         }
         ccNumber = ccNumber.trim();
         int len = ccNumber.length();
         String ccNumberSub = ccNumber.substring(0,len-4);
         String ccNumberTail = ccNumber.substring(len-4);
         try {
           encrypted = desCipher.doFinal(ccNumberSub.getBytes());
         } catch(BadPaddingException exc) {
           exc.printStackTrace();
           ae.add("error",new ActionError("error.systemError",exc.getMessage()));
           return ae;
         } catch(IllegalBlockSizeException exc) {
           exc.printStackTrace();
           ae.add("error",new ActionError("error.systemError",exc.getMessage()));
           return ae;
         }

         String encryptedS = "";
         for(int ii=0; ii<encrypted.length; ii++) {
           int val1 = encrypted[ii]&0xff;
           if(val1<16) encryptedS+="0";
           encryptedS += Integer.toHexString(val1);
         }

         byte[] encrypted1 = new byte[encrypted.length];
         for(int ii=0; ii<encryptedS.length(); ii+=2) {
           String ss = "0x"+encryptedS.substring(ii,ii+2);
           int bb = Integer.decode(ss).intValue();
           encrypted1[ii/2]=(byte)(bb&0xFF);
         }

         pForm.setCcNumber(encryptedS.toUpperCase()+ccNumberTail);
      */
     return ae;
   }

  //************************************************************************
   private static ActionErrors ccDecrypt(CheckoutForm pForm) {
     ActionErrors ae = new ActionErrors();
     /*
         byte[] raw = Constants.CC_BYTES;
         SecretKey desKey = new SecretKeySpec(raw, "Blowfish");
         // Create the cipher
         Cipher desCipher = null;
         try {
           desCipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
         } catch(NoSuchPaddingException exc) {
            exc.printStackTrace();
            ae.add("error",new ActionError("error.systemError",exc.getMessage()));
            return ae;
         } catch(NoSuchAlgorithmException exc) {
            exc.printStackTrace();
            ae.add("error",new ActionError("error.systemError",exc.getMessage()));
            return ae;
         } catch(ExceptionInInitializerError exc) {
            exc.printStackTrace();
            ae.add("error",new ActionError("error.systemError",exc.getMessage()));
            return ae;
         } catch(SecurityException exc) {
            exc.printStackTrace();
            ae.add("error",new ActionError("error.systemError",exc.getMessage()));
            return ae;
         }
         try {
           desCipher.init(Cipher.DECRYPT_MODE, desKey);
         } catch(InvalidKeyException exc) {
            exc.printStackTrace();
            ae.add("error",new ActionError("error.systemError",exc.getMessage()));
            return ae;
         }
         String ccNumber = pForm.getCcNumber();
         if(ccNumber==null || ccNumber.trim().length()<=4) {
           return ae;
         }
         ccNumber = ccNumber.trim();
         int len = ccNumber.length();
         String encryptedS = ccNumber.substring(0,len-4);
         String ccNumTail = ccNumber.substring(len-4);
         if(encryptedS.length()%2!=0) {
           ae.add("error",new ActionError("error.systemError","Wrong CC Number format"));
           return ae;
         }
         byte[] encrypted = new byte[encryptedS.length()/2];
         for(int ii=0; ii<encryptedS.length(); ii+=2) {
           String ss = "0x"+encryptedS.substring(ii,ii+2);
           int bb = Integer.decode(ss).intValue();
           encrypted[ii/2]=(byte)(bb&0xFF);
         }
         byte[] ccNumSubByte = null;
         try {
           ccNumSubByte = desCipher.doFinal(encrypted);
         } catch(BadPaddingException exc) {
           exc.printStackTrace();
           ae.add("error",new ActionError("error.systemError",exc.getMessage()));
           return ae;
         } catch(IllegalBlockSizeException exc) {
           exc.printStackTrace();
           ae.add("error",new ActionError("error.systemError",exc.getMessage()));
           return ae;
         }
         String ccNumSub = new String(ccNumSubByte);
         pForm.setCcNumber(ccNumSub+ccNumTail);
      */
     return ae;
   }

  //*****************************************************************
   public static ActionErrors previewOrder(HttpServletRequest request,
                                           CheckoutForm pForm) throws Exception {
     ActionErrors ae = new ActionErrors();
     HttpSession session = request.getSession();

     session.setAttribute("CheckoutForm", pForm);
     pForm.setComments(I18nUtil.getUtf8Str(pForm.getComments()));
     pForm.setRequestedBillToAddress(getUtf8(pForm.getRequestedBillToAddress()));

     Integer contractId = (Integer)
                          session.getAttribute(Constants.CONTRACT_ID);
     if (null == contractId) {
       contractId = new Integer(0);
     }

     //pickup items selected for ordering
     long[] itemsToOrder = pForm.getOrderSelectBox();
     ShoppingCartData shoppingCartD = ShopTool.getCurrentShoppingCart(session);
     if (shoppingCartD == null) {
       ae.add("error", new ActionError
              ("error.systemError", "Shopping cart available."));
       return ae;
     }

     ShoppingCartItemDataVector itemsSelected = new ShoppingCartItemDataVector();
     //figure out what to order
     for (int i = 0; i < itemsToOrder.length; i++) {
       ShoppingCartItemData scid = shoppingCartD.findItem(itemsToOrder[i]);
       if (scid != null) {
         itemsSelected.add(scid);
       }
     }
     //setup status
     String[] resaleItems = pForm.getReSaleSelectBox();
     for (int i = 0; i < resaleItems.length; i++) {
       long RSvalue = 0;
       try {
         RSvalue = Long.parseLong(resaleItems[i]);
       } catch (Exception e) {}
       ShoppingCartItemData scid = shoppingCartD.findItem(RSvalue);
     }

     pForm.setItems(itemsSelected);
     initReviewOrder(request, pForm, itemsSelected);
     pForm.setItems(itemsSelected);
     CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
     String type = appUser.getUser().getUserTypeCd();
     if (type.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE) ||
         type.equals(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR) ||
         type.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER)) {
       saveContactInfo(request, pForm);
     }

     return ae;

   }

  public static ActionErrors initReviewOrder(HttpServletRequest request, CheckoutForm pForm,
                                             java.util.List pFormItems) {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if (factory == null) {
      ae.add("error", new ActionError("error.systemError", "No Ejb access"));
      return ae;
    }
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if (appUser == null) {
      ae.add("error", new ActionError("error.systemError", "No " + Constants.APP_USER + " session object found"));
      return ae;
    }
    SiteData site = appUser.getSite();
    if (site == null) {
      ae.add("error", new ActionError("error.systemError", "No current site information found"));
      return ae;
    }
    pForm.setSite(site);

    CatalogInformation catinfoEjb = null;
    try {
      catinfoEjb = factory.getCatalogInformationAPI();
    } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error", new ActionError("error.systemError",
                                      "No CatalogInformation API Access"));
      return ae;
    }
    CatalogData siteCat = null;
    int siteid = site.getBusEntity().getBusEntityId();
    try {
      siteCat = catinfoEjb.getSiteCatalog(siteid);
    } catch (Exception exc) {
      ae.add("error", new ActionError
             ("error.systemError",
              "No catalog found for site:" + siteid));
      return ae;
    }

    setupShippingMessage(pForm, site, siteCat);

    AddressData siteAddress = site.getSiteAddress();
    if (siteAddress == null) {
      ae.add("error", new ActionError("error.systemError", "No current site address information found"));
      return ae;
    }
    Account accountEjb = null;
    try {
      accountEjb = factory.getAccountAPI();
    } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error", new ActionError("error.systemError", "No account API Access"));
      return ae;
    }
    AccountData account = null;
    try {
      account = accountEjb.getAccountForSite(site.getSiteId());
      pForm.setAccount(account);
    } catch (Exception exc) {
      ae.add("error", new ActionError("error.systemError", "Can't pickup account information from database"));
      return ae;
    }

    ShoppingServices shoppingServEjb = null;
    try {
      shoppingServEjb = factory.getShoppingServicesAPI();
    } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error", new ActionError("error.systemError", "No shopping services API Access"));
      return ae;
    }

    AddressData accountAddress = account.getBillingAddress();
    if (accountAddress == null) {
      ae.add("error", new ActionError("error.systemError", "No current account address information found"));
      return ae;
    }

    Integer contractIdI = (Integer) session.getAttribute(Constants.CONTRACT_ID);
    BigDecimal freightAmt = new BigDecimal(0);
    BigDecimal handlingAmt = new BigDecimal(0);
    BigDecimal fuelSurchargeAmt = new BigDecimal(0);
    BigDecimal smallOrderFee = new BigDecimal(0);

    int contractId = 0;
    if (contractIdI != null) {
      contractId = contractIdI.intValue();
    }

    //Freight
    OrderHandlingItemViewVector frItems = new OrderHandlingItemViewVector();

    for (int ii = 0; ii < pFormItems.size(); ii++) {
      ShoppingCartItemData cartItem = (ShoppingCartItemData) pFormItems.get(ii);
      OrderHandlingItemView frItem = OrderHandlingItemView.createValue();
      frItem.setItemId(cartItem.getProduct().getProductId());
      BigDecimal priceBD = new BigDecimal(cartItem.getPrice());
      priceBD.setScale(2, BigDecimal.ROUND_HALF_UP);
      frItem.setPrice(priceBD);
      frItem.setQty(cartItem.getQuantity());
      BigDecimal weight = null;
      String weightS = cartItem.getProduct().getShipWeight();
      try {
        weight = new BigDecimal(weightS);
      } catch (Exception exc) {}
      frItem.setWeight(weight);
      frItems.add(frItem);
    }

    OrderHandlingView frOrder = OrderHandlingView.createValue();
    frOrder.setTotalHandling(new BigDecimal(0));
    frOrder.setTotalFreight(new BigDecimal(0));
    frOrder.setContractId(contractId);
    int accountId = appUser.getSite().getAccountBusEntity().getBusEntityId();
    frOrder.setAccountId(accountId);
    frOrder.setSiteId(appUser.getSite().getBusEntity().getBusEntityId());
    frOrder.setWeight(new BigDecimal(0));
    frOrder.setItems(frItems);

    try {
      frOrder = shoppingServEjb.calcTotalFreightAndHandlingAmount(frOrder);
      freightAmt = frOrder.getTotalFreight();
      handlingAmt = frOrder.getTotalHandling();
      fuelSurchargeAmt = shoppingServEjb.getChargeAmtByCode(contractId,frOrder.getAmount(),frOrder,RefCodeNames.CHARGE_CD.FUEL_SURCHARGE);
      smallOrderFee = shoppingServEjb.getChargeAmtByCode(contractId,frOrder.getAmount(),frOrder,RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE);

    } catch (RemoteException exc) {
      ae.add("error", new ActionError("error.systemError", exc.getMessage()));
      exc.printStackTrace();
      return ae;
    }
    freightAmt = freightAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
    pForm.setFreightAmt(freightAmt);

    handlingAmt = handlingAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
    pForm.setHandlingAmt(handlingAmt);

    smallOrderFee = smallOrderFee.setScale(2, BigDecimal.ROUND_HALF_UP);
    pForm.setSmallOrderFeeAmt(smallOrderFee);

    fuelSurchargeAmt = fuelSurchargeAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
    pForm.setFuelSurchargeAmt(fuelSurchargeAmt);

    return ae;
  }

  //*****************************************************************
   public static ActionErrors placeOrderForAll(HttpServletRequest request,
                                               CheckoutForm pForm) throws Exception {
     ActionErrors ae = new ActionErrors();
     HttpSession session = request.getSession();
     session.setAttribute("CheckoutForm", pForm);

     //check: all or no items could be selected (false click protection)
     long[] itemsToOrder = pForm.getOrderSelectBox();
     ShoppingCartData shoppingCartD = ShopTool.getCurrentShoppingCart(session);
     if (itemsToOrder != null && itemsToOrder.length > 0) {
       if (shoppingCartD == null) {
         ae.add("error", new ActionError
                ("error.systemError", "Shopping cart not available."));
         return ae;
       }
       int cartItemQty = shoppingCartD.getItemsQty();
       if (cartItemQty == 0) {
         String errorMess =
           ClwI18nUtil.getMessage(request, "shoppingCart.text.shoppingCartIsEmpty", null);
         ae.add("error", new ActionError("error.simpleGenericError", errorMess));
         return ae;
       }
       if (cartItemQty != itemsToOrder.length) {
         String errorMess =
           ClwI18nUtil.getMessage(request, "shop.errors.allOrNoItemsMustBeSelected", null);
         ae.add("error", new ActionError("error.simpleGenericError", errorMess));
         return ae;
       }
       for (int i = 0; i < itemsToOrder.length; i++) {
         ShoppingCartItemData scid = shoppingCartD.findItem(itemsToOrder[i]);
         if (scid == null) {
           String errorMess =
             ClwI18nUtil.getMessage(request, "shop.errors.allOrNoItemsMustBeSelected", null);
           ae.add("error", new ActionError("error.simpleGenericError", errorMess));
           return ae;
         }
       }
     }

     ae = placeRushOrder(request, pForm);
     if(ae.size()==0) {
       Object[] params = new Object[1];
       params[0] = pForm.getOrderResult().getOrderNum();
       pForm.setConfirmMessage(ClwI18nUtil.getMessage(request,
                 "shop.checkout.text.actionMessage.orderSubmitted", params));
    }
     return ae;

   }

  //****************************************************************************
   public static ActionErrors placeRushOrder
     (HttpServletRequest request,
      CheckoutForm pForm) throws Exception {
     Date stime = checkoutDbg(request, "placeRushOrder START", null);
     ActionErrors ae = new ActionErrors();
     HttpSession session = request.getSession();

     CheckoutForm sForm = (CheckoutForm) session.getAttribute("CheckoutForm");

     pForm.setRequestedBillToAddress(getUtf8(pForm.getRequestedBillToAddress()));

     Integer contractId = (Integer) session.getAttribute(Constants.CONTRACT_ID);
     if (null == contractId) {
       contractId = new Integer(0);
     }

     if (ShopTool.isModernInventoryShopping(request)
              && !ShopTool.hasDiscretionaryCartAccessOpen(request)) {
          String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.discretionaryCartUnavailable", null);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
     }

     ShoppingCartData shoppingCartD = ShopTool.getCurrentShoppingCart(session);
     if (shoppingCartD == null) {
       ae.add("error", new ActionError
              ("error.systemError", "Shopping cart available."));
       return ae;
     }
     OrderData prevOrderD = shoppingCartD.getPrevOrderData();
     boolean inventoryFl = false;
     if (prevOrderD != null) {
       String prevOrderSource = prevOrderD.getOrderSourceCd();
       if (RefCodeNames.ORDER_SOURCE_CD.INVENTORY.equals(prevOrderSource)) {
         // Process as reqular order
         inventoryFl = true;
         //ae = placeOrder(request, pForm);
         //return ae;
       }
     }
     int originalCartId = shoppingCartD.getOrderGuideId();

     APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
     if (factory == null) {
       ae.add("error", new ActionError
              ("error.systemError", "No Ejb access"));
       return ae;
     }

     Account accountEjb = null;
     try {
       accountEjb = factory.getAccountAPI();
     } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
       ae.add("error", new ActionError
              ("error.systemError", "No account API Access"));
       return ae;
     }

     Order orderEjb = factory.getOrderAPI();
      try {
       orderEjb = factory.getOrderAPI();
     } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
       ae.add("error", new ActionError("error.systemError", "No order API Access"));
       return ae;
     }
     ShoppingCartItemDataVector currentCartItems = shoppingCartD.getItems();
     List formItems = sForm.getItems();
     if (formItems == null || formItems.size() == 0 ||
         currentCartItems==null || currentCartItems.size()==0 ) {
        String errorMess =
           ClwI18nUtil.getMessage(request, "shop.errors.noItemsAvailableForProcessing", null);
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        return ae;
     }

     CustomerOrderRequestData orderReq = new CustomerOrderRequestData();
     sForm.setOrderRequest(orderReq);

     orderReq.setContractId(contractId.intValue());

     //Set up freigh request
     ae = setupFreightTable(request, pForm, orderReq);
     if (ae.size() > 0) {
       return ae;
     }

     // Send the freight specified in the UI.
     double frtamt = 0, hndlamt = 0;
     if (sForm.getFreightAmt() != null) {
       frtamt = sForm.getFreightAmt().doubleValue();
     }
     log.info("CheckoutLogic <placeRushOrder>  WWWWWWWWWWWWWWWWWWWWW sForm.getFreightAmt() : "+sForm.getFreightAmt());

     orderReq.setFreightCharge(frtamt);

     // Send the handling specified in the UI.
     if (sForm.getHandlingAmt() != null) {
       hndlamt = sForm.getHandlingAmt().doubleValue();
     }
     orderReq.setHandlingCharge(hndlamt);

     if (!inventoryFl && sForm.getRushChargeAmtString() != null &&
         sForm.getRushChargeAmtString().trim().length() > 0) {
       try {
         BigDecimal amt = new BigDecimal(sForm.getRushChargeAmtString());
         orderReq.setRushOrderCharge(amt.doubleValue());
       } catch (Exception e) {
         String errorMess =
           ClwI18nUtil.getMessage(request, "shop.errors.invalidRushOrderChargeValue", null);
         ae.add("error", new ActionError("error.simpleGenericError", errorMess));
         return ae;
       }
     }

     //Set Service Fee detail
     HashMap serviceFeeDetMap = pForm.getServiceFeeDetail();
     if(serviceFeeDetMap!=null && serviceFeeDetMap.size()>0){
    	 orderReq.setServiceFeeDetail(serviceFeeDetMap);
     }

     //Check user
     CleanwiseUser appUser = (CleanwiseUser)
                             session.getAttribute(Constants.APP_USER);
     int userId = appUser.getUser().getUserId();
     orderReq.setUserId(userId);
     orderReq.setUserName(appUser.getUser().getUserName());

     //Check site
     SiteData site = appUser.getSite();
     int siteId = site.getBusEntity().getBusEntityId();
     if (siteId != sForm.getSiteId()) {
       String errorMess =
         ClwI18nUtil.getMessage(request, "shop.errors.pageExpired1", null);
       ae.add("error", new ActionError
              ("error.simpleGenericError", errorMess));
       return ae;
     }
     sForm.setSite(site);

     orderReq.setSiteId(siteId);

     //Get Account
     AccountData account = null;
     try {
       account = accountEjb.getAccountForSite(site.getSiteId());
       sForm.setAccount(account);
     } catch (Exception exc) {
       ae.add("error", new ActionError
              ("error.systemError",
               "No account information available"));
       return ae;
     }

     if (account == null) {
       checkoutDbg(request, "No account information found for site=" +
                   siteId, stime);
       ae.add("error", new ActionError
              ("error.systemError",
               "No account information found"));
       return ae;
     }

     orderReq.setAccountId(account.getBusEntity().getBusEntityId());
          
     // check store properties ALLOW_PO_NUM_BY_VENDER
     boolean allowPoByVender = Utility.isTrue(PropertyUtil.getPropertyValue(appUser.getUserStore().getMiscProperties(),
    		 		RefCodeNames.PROPERTY_TYPE_CD.ALLOW_PO_NUM_BY_VENDER));

      ae = ShopTool.validateBudgetThreshold(request, (ShoppingCartItemDataVector) formItems);
      if (!ae.isEmpty()) {
          return ae;
      }

      for (int ii = 0; ii < formItems.size(); ii++) {
       ShoppingCartItemData cartItem =
         (ShoppingCartItemData) formItems.get(ii);
       int itemId = cartItem.getProduct().getItemData().getItemId();
       int clw_skunum = cartItem.getProduct().getItemData().getSkuNum();

       if (cartItem.getIsaInventoryItem()) {
         String t2 = cartItem.getInventoryQtyOnHandString();
         if (t2 == null || t2.length() == 0) {
           String msg =
             " On Hand value not set for inventory item: "
             + cartItem.description();
           checkoutDbg(request, msg, stime);
           orderReq.addOrderNote(msg);
         }
       }

       int qty = cartItem.getQuantity();
       if (qty == 0)continue; // 0 qty line

       double price = cartItem.getPrice();
       String distErpNum = "";
       if (allowPoByVender){
    	   if (cartItem.getProduct().getCatalogDistributor() != null) {
    		   distErpNum = cartItem.getProduct().getCatalogDistributor().getErpNum();
    	   }
       }

       orderReq.addItemEntry(ii + 1, itemId, clw_skunum,
                             qty, price,
                             cartItem.getProduct().getUom(),
                             cartItem.getProduct().getShortDesc(),
                             cartItem.getProduct().getPack(),
                             cartItem.getIsaInventoryItem(),
                             cartItem.getInventoryParValue(),
                             cartItem.getInventoryQtyOnHandString(),
                             cartItem.getReSaleItem(),
                             distErpNum);
     }

     String paymentCd = RefCodeNames.PAYMENT_TYPE_CD.ACCOUNT;
     try {

       PropertyData orderMinimum = account.getOrderMinimum();
       double orderTotal = sForm.getItemsAmt(request);

       String minTot = orderMinimum.getValue();
       double minimumTotal;
       try {
         minimumTotal = CurrencyFormat.parse(minTot).doubleValue();
       } catch (ParseException pe) {
         // this should only happen if bad value in db
         minimumTotal = 0.0;
       }
       BigDecimal bigDminTot = new BigDecimal(minimumTotal);
       String formattedMinTot = CurrencyFormat.format(bigDminTot);

       if (orderTotal < minimumTotal) {
         ae.add("error", new ActionError
                ("error.systemErrorNoSupport",
                 "Total is less than Order Minimum, which is " +
                 formattedMinTot));
         return ae;
       }

       //Check CC validity
       if(sForm.isaCcReq()){
       String ccNumber = sForm.getCcNumber();
         ae = ccValidation(sForm, request, appUser.getUserStore().isStateProvinceRequired());
         if (ae.size() > 0)
           return ae;
         //encrypt
         ae = ccEncrypt(sForm);
         if (ae.size() > 0)
           return ae;
         //
         paymentCd = sForm.getCcType();

         // copy the credit card information into the
         // order request object.
         orderReq.setCcNumber(sForm.getCcNumber());
         orderReq.setCcExpMonth(sForm.getCcExpMonth());
         orderReq.setCcExpYear(sForm.getCcExpYear());
         orderReq.setCcBuyerName(sForm.getCcPersonName());
         orderReq.setCcStreet1(sForm.getCcStreet1());
         orderReq.setCcStreet2(sForm.getCcStreet2());
         orderReq.setCcCity(sForm.getCcCity());
         orderReq.setCcState(sForm.getCcState());
         orderReq.setCcPostalCode(sForm.getCcZipCode());
         orderReq.setCcType(pForm.getCcType());

       } else {
         String workflowRole = appUser.getUser().getWorkflowRoleCd();
         boolean approverFl =
           (workflowRole.indexOf(RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER) >= 0) ?
           true : false;
         if (!approverFl || !inventoryFl) {
           if (appUser.getOnAccount() == false) {
             if (appUser.getCreditCardFlag() == false) {
               String errorMess =
                 ClwI18nUtil.getMessage(request,
                                        "shop.errors.billingMustBeOnAccountOrCreditCard", null);
               ae.add("error", new ActionError
                      ("error.simpleGenericError", errorMess));
               return ae;

             } else {
               String errorMess =
                 ClwI18nUtil.getMessage(request,
                                        "shop.errors.creditCardInfoIsNecessary", null);
               ae.add("error", new ActionError
                      ("error.simpleGenericError", errorMess));
               return ae;
             }
           }
         }
       }

       // Check PO number presence
       String poNum = sForm.getPoNumber();
       if (appUser.getPoNumRequired()) {
         if (poNum == null || poNum.trim().length() == 0) {
           String poNumLabel =
             ClwI18nUtil.getMessage(request, "shop.checkout.text.poNumber", null);
           ae.add("error", new ActionError
                  ("variable.empty.error", poNumLabel));
           return ae;
         }
       }

     ae = checkAndSaveAlternateAddress(request, pForm, ae, orderReq, ClwI18nUtil.getMessage(request, "shop.checkout.text.Attn"));
     if (ae.size() > 0)
         return ae;
     sForm.setComments(I18nUtil.getUtf8Str(sForm.getComments()));
       
     orderReq.setCustomerPoNumber(poNum);
     orderReq.setCustomerComments(sForm.getComments());
       
       //orderReq.setCustomerComments(sForm.getCustomerComment());
       if (orderReq.getProperties() == null) {
         orderReq.setProperties(new PropertyDataVector());
       }

//       log.info("placeRushOrder() ***************SVC 4: pForm.isBypassBudget() = " + pForm.isBypassBudget());
       if (sForm.isBypassBudget()) {
         orderReq.setOrderBudgetTypeCd(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE);
       } else {
         orderReq.setOrderBudgetTypeCd(null);
       }

       orderReq.getProperties().addAll(sForm.getSite().getDataFieldProperties());

       //Order note is required for a billing order
       if (sForm.isBillingOrder()) {
         if ((sForm.getOrderNote() == null || sForm.getOrderNote().trim().length() == 0)) {
           String orderNoteLabel =
             ClwI18nUtil.getMessage(request, "shop.checkout.text.oderNote", null);
           ae.add("error", new ActionError("variable.empty.error", orderNoteLabel));
           return (ae);
         }

         Iterator it = sForm.getItems().iterator();
         String newDistErpNum = null;
         while (it.hasNext()) {
           ShoppingCartItemData itm = (ShoppingCartItemData) it.next();
           String newErp = itm.getProduct().getCatalogDistributor().getErpNum();
           if (newDistErpNum == null) {
             newDistErpNum = newErp;
           } else if (!newDistErpNum.equals(newErp)) {
             String errorMess =
               ClwI18nUtil.getMessage(request,
                                      "shop.errors.multipleDistributorsForBillingOrder", null);
             ae.add("error", new ActionError("error.simpleGenericError", errorMess));
             return ae;
           }
         }

         if ((sForm.getBillingOriginalPurchaseOrder() == null ||
              sForm.getBillingOriginalPurchaseOrder().trim().length() == 0)) {
           //ae.add("error",new ActionError("variable.empty.error","Billing Original Purchase Order"));
           //return (ae);
         } else {
           PurchaseOrderStatusCriteriaData pocrit = PurchaseOrderStatusCriteriaData.createValue();
           pocrit.setStoreIdVector(appUser.getUserStoreAsIdVector());
           pocrit.setErpPONum(sForm.getBillingOriginalPurchaseOrder());
           PurchaseOrderDataVector pov = factory.getPurchaseOrderAPI().getPurchaseOrderCollection(pocrit);
           if (pov.size() != 1) {
             Object[] param = new Object[2];
             param[0] = "" + pov.size();
             param[1] = pForm.getBillingOriginalPurchaseOrder();
             String errorMess =
               ClwI18nUtil.getMessage(request,
                                      "shop.errors.multipleErpPos", param);
             ae.add("error", new ActionError("error.simpleGenericError", errorMess));
             return (ae);
           } else {
             PurchaseOrderData po = (PurchaseOrderData) pov.get(0);
             if (newDistErpNum != null) {
               if (!newDistErpNum.equals(po.getDistErpNum())) {
                 Object[] param = new Object[2];
                 param[0] = po.getDistErpNum();
                 param[1] = newDistErpNum;
                 String errorMess =
                   ClwI18nUtil.getMessage(request,
                                          "shop.errors.distributorPoMismatch", param);
                 ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                 return ae;
               }
             }
           }
         }
       }

       if (Utility.isSet(pForm.getCustomerComment())) {
         orderReq.addArbitraryOrderProperty
           (RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS, pForm.getCustomerComment());
         //pForm.setCustomerComment(null);
       }
       if ((sForm.getOrderNote() != null && sForm.getOrderNote().trim().length() > 0)) {
         orderReq.setOrderNote(I18nUtil.getUtf8Str(sForm.getOrderNote()));
       }
       if (!inventoryFl) {
         orderReq.setOrderSourceCd(sForm.getOrderOriginationMethod());
       } else {
         orderReq.setOrderSourceCd(RefCodeNames.ORDER_SOURCE_CD.INVENTORY);
       }

       if (shoppingCartD.getPrevOrderData() != null) {
         OrderData prevod = shoppingCartD.getPrevOrderData();
         /*
                          String tnewordermsg = " This order replaces " +
                          " order number=" + prevod.getOrderNum() +
                          " , placed by=" + prevod.getAddBy() +
                          " , original order date=" + prevod.getOriginalOrderDate();

                          String tnewmsg = orderReq.getOrderNote();
                          if ( null == tnewmsg ) {
             tnewmsg = "";
                          }
                          tnewmsg += "  " + tnewordermsg;
                          orderReq.setOrderNote(tnewmsg);
          */
         //orderReq.setOrderRefNumber(prevod.getOrderNum());
         orderReq.setRefOrderId(prevod.getOrderId());
         IdVector repOrderIdV = orderReq.getReplacedOrderIds();
         if (repOrderIdV == null) {
           repOrderIdV = new IdVector();
           orderReq.setReplacedOrderIds(repOrderIdV);
         }
         repOrderIdV.add(new Integer(prevod.getOrderId()));

         // Set the order source code to be the same
         // as the order being replaced.
         orderReq.setOrderSourceCd(prevod.getOrderSourceCd());
       }
       // If order is for a WorkOrderItem
       WorkOrderItemData workOrderItemD = shoppingCartD.getWorkOrderItem();
       WorkOrder workOrderEjb = APIAccess.getAPIAccess().getWorkOrderAPI();
       if (workOrderItemD != null && workOrderItemD.getWorkOrderItemId() > 0) {
           if (orderReq.getSiteId() != workOrderItemD.getBusEntityId()) {
               String errorMessage = ClwI18nUtil.getMessageOrNull(request, "shop.errors.mustBelongToSameSite");
               if (errorMessage == null) {
                   errorMessage = "Web Order and Work Order must belong to the same site.";
               }
               ae.add("error", new ActionError("error.simpleGenericError", errorMessage));
           return ae;
           }
           WorkOrderData wo = workOrderEjb.getWorkOrder(workOrderItemD.getWorkOrderId());
           String woStatus = wo.getStatusCd();
           if (RefCodeNames.WORK_ORDER_STATUS_CD.CLOSED.equals(woStatus) ||
               RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED.equals(woStatus) ||
               RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED.equals(woStatus)) {

               String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.inapropriateWorkOrderStatus", new Object[] {woStatus});
               ae.add("error", new ActionError("error.simpleGenericError", errorMess));
           return ae;
           }
           orderReq.setWorkOrderItemId(workOrderItemD.getWorkOrderItemId());
           shoppingCartD.setWorkOrderItem(null);
           pForm.setWorkOrderNumber("");
       } else if (workOrderItemD == null && Utility.isSet(pForm.getWorkOrderNumber())) {
           WorkOrderSimpleSearchCriteria crit = new WorkOrderSimpleSearchCriteria();
           crit.setStoreId(pForm.getStoreId());
           IdVector sites= new IdVector();
           sites.add(Integer.valueOf(pForm.getSiteId()));
           crit.setSiteIds(sites);
           crit.setWorkOrderNum(pForm.getWorkOrderNumber());

           WorkOrderDetailViewVector woDVV = workOrderEjb.getWorkOrderDetailCollection(crit);
           if (woDVV.isEmpty()) {
               //error message
               String errorMessage = ClwI18nUtil.getMessage(request, "shop.errors.invalidWorkOrderNumber", new Object[] {pForm.getWorkOrderNumber()});
               ae.add("error", new ActionError("error.simpleGenericError", errorMessage));
               return ae;
           } else {
               //wo - order binding
               WorkOrderDetailView wo = (WorkOrderDetailView)woDVV.get(0);
               String woStatus = wo.getWorkOrder().getStatusCd();
               if (RefCodeNames.WORK_ORDER_STATUS_CD.CLOSED.equals(woStatus) ||
                   RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED.equals(woStatus) ||
                   RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED.equals(woStatus)) {

                   String errorMessage = ClwI18nUtil.getMessage(request, "shop.errors.inapropriateWorkOrderStatus", new Object[] {woStatus});
                   ae.add("error", new ActionError("error.simpleGenericError", errorMessage));
               return ae;
               }
               WorkOrderItemDetailViewVector woItems = wo.getWorkOrderItems();
               if (!woItems.isEmpty()) {
                   WorkOrderItemDetailView woItem = (WorkOrderItemDetailView)woItems.get(0);
                   WorkOrderItemData woItemData = woItem.getWorkOrderItem();
                   if (woItemData != null && woItemData.getWorkOrderItemId() > 0) {
                       orderReq.setWorkOrderItemId(woItemData.getWorkOrderItemId());
                   }
               }
           }
       }

     //Check whether remote access is enabled
       SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
       if (sessionData.isRemoteAccess()) {
           ae = checkServiceTicket(request, pForm.getOrderServiceTicketNumbers(), new Date());
           if (ae.isEmpty()) {
               orderReq.setServiceTicketOrderRequest(createServiceOrderRequest(pForm));
           } else {
               return ae;
           }
       }

       if (orderReq.getProperties() == null) {
         orderReq.setProperties(new PropertyDataVector());
       }

         if (pForm.getFuelSurchargeAmt() != null && pForm.getFuelSurchargeAmt().doubleValue() > 0.01) {
             orderReq.addArbitraryOrderMeta(RefCodeNames.CHARGE_CD.FUEL_SURCHARGE, pForm.getFuelSurchargeAmt().toString(),0);
         }

         if (pForm.getSmallOrderFeeAmt() != null && pForm.getSmallOrderFeeAmt().doubleValue() > 0.01) {
             orderReq.addArbitraryOrderMeta(RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE, pForm.getSmallOrderFeeAmt().toString(),0);
         }

       orderReq.setBillingOrder(sForm.isBillingOrder());
       if (sForm.isBillingOrder()) {
         //add dist invoice prop
         PropertyData pd = PropertyData.createValue();
         pd.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
         pd.setPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_DISTRIBUTOR_INVOICE);
         pd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_DISTRIBUTOR_INVOICE);
         pd.setValue(sForm.getBillingDistributorInvoice());
         orderReq.getProperties().add(pd);
         //add bill only orig po num prop
         pd = PropertyData.createValue();
         pd.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
         pd.setPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORIGINAL_PO_NUM);
         pd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORIGINAL_PO_NUM);
         pd.setValue(sForm.getBillingOriginalPurchaseOrder());
         orderReq.getProperties().add(pd);
       }
       //add customerRequestedReshipOrderNum prop
       if (!(sForm.getCustomerRequestedReshipOrderNum() == null ||
             sForm.getCustomerRequestedReshipOrderNum().trim().length() == 0)) {
         PropertyData pd = PropertyData.createValue();
         pd.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
         pd.setPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUST_REQ_RESHIP_ORDER_NUM);
         pd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUST_REQ_RESHIP_ORDER_NUM);
         pd.setValue(sForm.getCustomerRequestedReshipOrderNum());
         orderReq.getProperties().add(pd);
       }

       orderReq.setBypassOrderRouting(sForm.isBypassOrderRouting());
       orderReq.setBypassCustomerWorkflow(pForm.isBypassCustomerWorkflow());

        /// add 'RebillOrder' property into 'orderReq'
        setRebillOrderPropertyForOrderRequest(orderReq, pForm);

       // Check for an order scheduled delivery date and
       // that the account is set up to handle scheduled
       // orders.
       /*
         Requested ship date is no longer used since
         we now control the time when order get sent to
         the distributor.
         durval 10/6/2003
                    if (site.getNextDeliveryDate() != null &&
           ShopTool.showScheduledDelivery(request) ) {
           orderReq.setOrderRequestedShipDate
               (site.getNextDeliveryDate());
                    }
        */

       //for crc, make sure contact name and phone number are present
       String type = appUser.getUser().getUserTypeCd();
       if (type.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE) ||
           type.equals(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR) ||
           type.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER)) {
         if (sForm.getOrderContactName() == null ||
             sForm.getOrderContactName().trim().length() == 0) {
           String contactNameLabel =
             ClwI18nUtil.getMessage(request, "shop.checkout.text.contactName", null);
           ae.add("error", new ActionError
                  ("variable.empty.error", contactNameLabel));
           return ae;
         }
         sForm.setOrderContactName(I18nUtil.getUtf8Str(sForm.getOrderContactName()));
         if (sForm.getOrderContactPhoneNum() == null ||
             sForm.getOrderContactPhoneNum().trim().length() == 0) {
           String contactNameLabel =
             ClwI18nUtil.getMessage(request, "shop.checkout.text.contactName", null);

           ae.add("error", new ActionError
                  ("variable.empty.error", contactNameLabel));
           return ae;
         }
         if (sForm.getOrderOriginationMethod() == null ||
             sForm.getOrderOriginationMethod().trim().length() == 0) {

           String methodLabel =
             ClwI18nUtil.getMessage(request, "shop.checkout.text.method", null);
           ae.add("error", new ActionError
                  ("variable.empty.error", methodLabel));
           return ae;
         }
         saveContactInfo(request, pForm);
       }

       orderReq.setOrderContactName(sForm.getOrderContactName());
       orderReq.setOrderTelephoneNumber(sForm.getOrderContactPhoneNum());
       orderReq.setOrderEmail(sForm.getOrderContactEmail());
     } catch (Exception e2) {
       e2.printStackTrace();
       ae.add("error", new ActionError("error.systemError",
                                       "Page expired 2"));
       return ae;
     }

     //Set up freigh request
     ae = setupFreightTable (request, sForm, orderReq);
     if(ae.size()>0) {
         return ae;
     }

    ///
    OrderAddOnChargeDataVector orderAddOnCharges =
        getFuelSurchargeAndSmallOrderFeeForOrder(sForm.getCartDistributors());
    if (orderAddOnCharges != null && orderAddOnCharges.size() > 0) {
        orderReq.setOrderAddOnChargeList(orderAddOnCharges);
    }

     IntegrationServices isvcEjb = null;

     String emsg = "";
     try {
       emsg = "No IntegrationServices API Access";
       isvcEjb = factory.getIntegrationServicesAPI();
     } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
       ae.add("error", new ActionError("error.systemError", emsg));
       return ae;
     }
     Site siteEjb = null;
     try {
       siteEjb = factory.getSiteAPI();
     } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
       ae.add("error",
              new ActionError("error.systemError",
                              "No EJB services available 101."));
       return ae;
     }

     Integer catalogIdI = (Integer)
                          session.getAttribute(Constants.CATALOG_ID);
     if (catalogIdI == null) {
       ae.add("error", new ActionError
              ("error.systemError", "No " + Constants.CATALOG_ID +
               " session attrbute found"));
       return ae;
     }

     ProcessOrderResultData orderRes = null;
     String lockName = "==CheckOutLock==";
     Integer lockValue = null;

     try {
       Object oforlock = new Object();
       synchronized (oforlock) {
         lockValue = (Integer) session.getAttribute(lockName);
         if (lockValue == null) {
           lockValue = new Integer("0");
           session.setAttribute(lockName, lockValue);
         }
       }
       checkoutDbg(request, "order req: " + orderReq +
                   " lock val: " + lockValue, stime);
       if (lockValue.intValue() == 0) {

         // Lock the purchase capability.
         synchronized (oforlock) {
           lockValue = new Integer("1");
           session.setAttribute(lockName, lockValue);
         }
         
         orderRes = isvcEjb.processOrderRequest(orderReq);

         // Unlock the purchase method.
         lockValue = new Integer("0");
         session.setAttribute(lockName, lockValue);

         if (orderRes.getOrderId() == 0) {
           String errorMess =
             ClwI18nUtil.getMessage(request, "shop.errors.orderRejected", null);
           String[] messA = orderRes.getMessages();
           if (messA != null && messA.length > 0) {
             for (int ii = 0; ii < messA.length; ii++) {
               String translatedMess = ClwI18nUtil.formatEjbError(request, messA[ii]);
               if (translatedMess != null && translatedMess.length() > 0) {
                 errorMess += ". " + translatedMess;
               }
             }
           }
           ae.add("error", new
                  ActionError("error.simpleGenericError", errorMess));
           return ae;
         }
       } else {
         // The lock value is 1.  Meaning a purchase is
         // already in progress.
         String errorMess =
           ClwI18nUtil.getMessage(request, "shop.errors.purchaseIsInProgress", null);
         ae.add("error", new
                ActionError("error.simpleGenericError", errorMess));
         return ae;
       }
     } catch (Exception exc) {
       session.setAttribute(lockName, lockValue);
       log.error("CheckoutLogic: placeRushOrder ERRROR 3 MESSAGE="+exc.getMessage());
       return mkUserOrderMessage(request, exc.getMessage());
     } finally {
       lockValue = new Integer("0");
       session.setAttribute(lockName, lockValue);
     }

     // Unlock the purchase capability.
     lockValue = new Integer("0");
     session.setAttribute(lockName, lockValue);

     sForm.setConfirmationFlag(true);
     sForm.setOrderResult(orderRes);
     sForm.setSite(site);
     sForm.setAccount(account);


     try{
       SessionTool st = new SessionTool(request);
	   Site sbean = st.getFactory().getSiteAPI();

	   ShoppingControlDataVector shopCtrls = site.getShoppingControls();

	   ShoppingCartItemDataVector sciDV = shoppingCartD.getItems();
	   Map preItemQuantityMap = shoppingCartD.getPrevItemsQuantityMap();
	   List itemIds = new ArrayList();
	   for(int i=0; i<sciDV.size(); i++){
		   ShoppingCartItemData thisItem = (ShoppingCartItemData)sciDV.get(i);

		   int itemId = thisItem.getItemId();
		   Integer itemIdI = new Integer(itemId);
		   itemIds.add(itemIdI);

		   int qtyOrdered = thisItem.getQuantity();

		   for(int s=0; s<shopCtrls.size(); s++){
			   ShoppingControlData sdata = (ShoppingControlData)shopCtrls.get(s);

			   if(sdata.getItemId()==itemId){
				   int newActualMaxQty = sdata.getActualMaxQty() - qtyOrdered;

				   if (preItemQuantityMap != null){// if it is a existing order, need to adjust the actual max qty
					   Integer preQuantity = (Integer)preItemQuantityMap.get(itemIdI);
					   if (preQuantity != null){
						   newActualMaxQty += preQuantity;
					   }

				   }
				   sdata.setActualMaxQty(newActualMaxQty);
				   //sdata.setExpDate(null);
				   //sdata.setHistoryOrderQty(0);
			   }
		   }
	   }

	   // taking care deleted item
	   if (preItemQuantityMap != null){
		   Iterator it = preItemQuantityMap.keySet().iterator();
		   while (it.hasNext()) {
			   Integer itemId = (Integer) it.next();
			   if (!itemIds.contains(itemId)){
				   int qtyOrdered = ((Integer)preItemQuantityMap.get(itemId)).intValue();

				   for(int s=0; s<shopCtrls.size(); s++){
					   ShoppingControlData sdata = (ShoppingControlData)shopCtrls.get(s);

					   if(sdata.getItemId()==itemId.intValue()){
						   int newActualMaxQty = sdata.getActualMaxQty() + qtyOrdered;

						   sdata.setActualMaxQty(newActualMaxQty);
						   sbean.updateShoppingControls(shopCtrls, false);
						   break;
					   }
				   }
			   }
		   }
	   }

	   sbean.updateShoppingControls(shopCtrls, false);
     }catch(Exception e){
    	 //keep going on error.
    	 e.printStackTrace();
     }


     //update the budget info for this session. Making the update conditional to take care of
     //spcecial case where workflow rule has reject order rule. If there is a reject order
     //rule then the order status of 'orderRes' will be ordered.
     if(!RefCodeNames.ORDER_STATUS_CD.ORDERED.equals(orderRes.getOrderStatusCd())){
    	 ShopTool.saveBudgetSessionInfoForCurrentDiscretionaryCart(request);
     }

     //sForm.setItems(shoppingCartD.getItems());
     //Remove selected items from shopping cart
     //ShoppingCartItemDataVector currentCartItems = shoppingCartD.getItems();
     ShoppingCartItemDataVector itemsOrdered = new ShoppingCartItemDataVector();

     /*for (int ii = 0; ii < formItems.size(); ii++) {
       ShoppingCartItemData fItem =
         (ShoppingCartItemData) formItems.get(ii);

       for (int cii = 0; cii < currentCartItems.size(); cii++) {
         ShoppingCartItemData itm = (ShoppingCartItemData)
                                    currentCartItems.get(cii);

         if (itm.isSameItem(fItem)) {
           itemsOrdered.add(itm);
           currentCartItems.remove(cii);
           break;
         }
       }
     }*/

     for(Iterator it=formItems.iterator();it.hasNext();){
    	 ShoppingCartItemData fItem = (ShoppingCartItemData)it.next();

    	 for(Iterator it2=currentCartItems.iterator();it2.hasNext();){
    		 ShoppingCartItemData cItem = (ShoppingCartItemData)it2.next();

    		 if(cItem.isSameItem(fItem)){
    			 itemsOrdered.add(cItem);
    			 break;
    		 }
    	 }
     }

     currentCartItems.removeAll(itemsOrdered);

   //update the budget info for this session.
     if(RefCodeNames.ORDER_STATUS_CD.ORDERED.equals(orderRes.getOrderStatusCd())){
    	 ShopTool.saveBudgetSessionInfoForCurrentDiscretionaryCart(request);
     }
     //shoppingCartD.setPrevOrderData(null);
     //ae = ShoppingCartLogic.saveShoppingCart
     //(site, session, orderRes, itemsOrdered);

     shoppingCartD.setItems(currentCartItems);

     OrderData replacedOrderD = shoppingCartD.getPrevOrderData();
     if (replacedOrderD != null) {
       IdVector replacedOrderIds = new IdVector();
       replacedOrderIds.add(new Integer(replacedOrderD.getOrderId()));
       orderReq.setReplacedOrderIds(replacedOrderIds);
       ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();
       shoppingServEjb.saveModifiedOrderShoppingInfo(shoppingCartD,
         orderReq.getUserName(), orderRes);
       shoppingCartD.setPrevOrderData(null);

     } else {
         ae = ShoppingCartLogic.saveShoppingCart
                 (site, session, orderRes, itemsOrdered);
     }

     pForm.setConfirmationFlag(true);
     pForm.setOrderResult(orderRes);
     pForm.setSite(site);
     pForm.setAccount(account);
     pForm.setItems(itemsOrdered);


     return ae;
   }

   private static ActionErrors checkAndSaveAlternateAddress(
		HttpServletRequest request, CheckoutForm pForm, ActionErrors ae,
		CustomerOrderRequestData orderReq, String strAttention)
		throws Exception {
		if (pForm.getAllowAlternateShipTo()) {
			 if (Utility.isSet(pForm.getAlternateAddress1()) ||
	             Utility.isSet(pForm.getAlternateAddress2()) ||
	             Utility.isSet(pForm.getAlternateAddress3()) ||
	             Utility.isSet(pForm.getAlternateCity()) ||
	             Utility.isSet(pForm.getAlternateStateProvince()) ||
	             Utility.isSet(pForm.getAlternatePostalCode()) ||
	             Utility.isSet(pForm.getAlternateCountry()) ||
	             Utility.isSet(pForm.getAlternatePhoneNum())
	            ) {
	
	            if (!Utility.isSet(pForm.getAlternateAddress1())) {
	            	Object[] param = new Object[1];
	                param[0] = strAttention;
	            	String errorMess = ClwI18nUtil.getMessage(request, "error.shop.checkout.attnRequired", param);
	                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
	            }
	            
	            if (!Utility.isSet(pForm.getAlternatePhoneNum())) {
	            	String phoneLabel = ClwI18nUtil.getMessage(request, "shop.checkout.text.phone", null);
	            	String errorMess = ClwI18nUtil.getMessage(request, "error.shop.checkout.attnRequired", new Object[]{phoneLabel});
	                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
  
	            }
	            
	            if (ae.size() > 0)
	            	return ae;
	
	            pForm.setAlternateAddress1(pForm.getAlternateAddress1());
	            AddressData alternateShipToAddress = AddressData.createValue();
	            alternateShipToAddress.setAddress1(pForm.getAlternateAddress1());
	            alternateShipToAddress.setAddress2(pForm.getAlternateAddress2());
	            alternateShipToAddress.setAddress3(pForm.getAlternateAddress3());
	            alternateShipToAddress.setCity(pForm.getAlternateCity());
	            alternateShipToAddress.setStateProvinceCd(pForm.getAlternateStateProvince());
	            alternateShipToAddress.setPostalCode(pForm.getAlternatePostalCode());
	            alternateShipToAddress.setCountryCd(pForm.getAlternateCountry());
	            alternateShipToAddress = getUtf8(alternateShipToAddress);
	            
	            pForm.setOrderContactPhoneNum(pForm.getAlternatePhoneNum());
	
	            if (!Utility.isSet(pForm.getComments())) {
	            	pForm.setComments(SHIP_TO_OVERRIDE_COMMENT);
	            }
	            orderReq.setAlternateShipToAddress(alternateShipToAddress);
	            if (alternateShipToAddress != null){
	            	orderReq.addArbitraryOrderMeta(RefCodeNames.ORDER_PROPERTY_TYPE_CD.SHIP_TO_OVERRIDE, "TRUE",0);
	            }
	         }        
	     }
		return ae;
   }

  private static void saveContactInfo(HttpServletRequest request,
                                      CheckoutForm pForm) {
    HttpSession session = request.getSession();
    String contactName = pForm.getOrderContactName();
    if (Utility.isSet(contactName)) {
      session.setAttribute("OrderContactName", contactName);
    }
    String contactPhone = pForm.getOrderContactPhoneNum();
    if (Utility.isSet(contactPhone)) {
      session.setAttribute("OrderContactPhone", contactPhone);
    }
    String contactEmail = pForm.getOrderContactEmail();
    if (Utility.isSet(contactEmail)) {
      session.setAttribute("OrderContactEmail", contactEmail);
    }
    String orderMethod = pForm.getOrderOriginationMethod();
    if (Utility.isSet(orderMethod)) {
      session.setAttribute("OrderMethod", orderMethod);
    }

  }

  public static ActionErrors updateCartInfo(HttpServletRequest request,
                                            CheckoutForm pForm) throws Exception {
    ActionErrors ae = new ActionErrors();
    pForm.setComments(I18nUtil.getUtf8Str(pForm.getComments()));
    pForm.setRequestedBillToAddress(getUtf8(pForm.getRequestedBillToAddress()));

    HttpSession session = request.getSession();
    session.setAttribute("CheckoutForm", pForm);
    ShoppingCartData shoppingCartD = ShopTool.getCurrentShoppingCart(session);
    if (shoppingCartD == null) {
      ae.add("error", new ActionError
             ("error.systemError", "Shopping cart available."));
      return ae;
    }

    APIAccess factory = (APIAccess) session.getAttribute
                        (Constants.APIACCESS);
    if (factory == null) {
      ae.add("error", new ActionError
             ("error.systemError", "No Ejb access"));
      return ae;
    }

    ShoppingServices shoppingServeicesEjb = null;
    OrderGuide ogEjb= null;
    String emsg = "";
    try {
      emsg = "No ShoppingServices API Access";
      shoppingServeicesEjb = factory.getShoppingServicesAPI();
    } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error", new ActionError("error.systemError", emsg));
      return ae;
    }
    try {
         emsg = "No OrderGuide API Access";
         ogEjb = factory.getOrderGuideAPI();
       } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
         ae.add("error", new ActionError("error.systemError", emsg));
         return ae;
       }

    CleanwiseUser appUser = (CleanwiseUser)
                            session.getAttribute(Constants.APP_USER);

      int orderGuideId = 0;

      if (!shoppingCartD.getSite().hasInventoryShoppingOn()
              || shoppingCartD.getSite().hasModernInventoryShopping()) {
          OrderGuideData ogData = ogEjb.getOrderGuide(appUser.getUser().getUserId(), shoppingCartD.getSite().getSiteId(), RefCodeNames.ORDER_GUIDE_TYPE_CD.SHOPPING_CART);

          if (ogData != null) {
              orderGuideId = ogData.getOrderGuideId();
          }
      }


      java.util.List cartInfoVector =
      new java.util.ArrayList();
    int siteid = shoppingCartD.getSite().getBusEntity().getBusEntityId();
    ShoppingInfoData comment = ShoppingInfoData.createValue();
    comment.setSiteId(siteid);
    comment.setValue(pForm.getComments());
    comment.setShortDesc(ShoppingCartData.CART_COMMENTS);
    comment.setAddBy(appUser.getUser().getUserName());
    comment.setModBy(appUser.getUser().getUserName());
    comment.setOrderGuideId(orderGuideId);
    cartInfoVector.add(comment);

    if (Utility.isSet(pForm.getCustomerComment())) {
      ShoppingInfoData custComment = ShoppingInfoData.createValue();
      custComment.setSiteId(siteid);
      custComment.setValue(pForm.getCustomerComment());
      custComment.setShortDesc(ShoppingCartData.CUSTOMER_CART_COMMENTS);
      custComment.setAddBy(appUser.getUser().getUserName());
      custComment.setModBy(appUser.getUser().getUserName());
      custComment.setOrderGuideId(orderGuideId);
      cartInfoVector.add(custComment);
      pForm.setCustomerComment(null);
    }

    ShoppingInfoData ponumb = ShoppingInfoData.createValue();
    ponumb.setSiteId(siteid);
    ponumb.setValue(pForm.getPoNumber());
    ponumb.setShortDesc(ShoppingCartData.CART_PO_NUM);
    ponumb.setAddBy(appUser.getUser().getUserName());
    ponumb.setModBy(appUser.getUser().getUserName());
    ponumb.setOrderGuideId(orderGuideId);
    cartInfoVector.add(ponumb);


      shoppingCartD.setShoppingInfo
      (
        shoppingServeicesEjb.updateShoppingInfo(siteid,orderGuideId,cartInfoVector)
      );

    return ae;
  }

  public static AddressData getLastBillTo
    (int pUserId, int pSiteId) throws Exception {

    APIAccess factory = new APIAccess();

    ShoppingServices shoppingServeicesEjb = null;
    shoppingServeicesEjb = factory.getShoppingServicesAPI();
    AddressDataVector adv =
      shoppingServeicesEjb.getUserBillTos(pUserId, pSiteId);
    if (adv != null && adv.size() > 0) {
      return (AddressData) adv.get(0);
    }
    return null;

  }

  /**
   *Recaluclates items that are based off various numbers that can change on the checkout screen.
   */
  public static ActionErrors recalculateDependencies(HttpServletRequest request, CheckoutForm pForm,
    boolean isInitialRequest) {

    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();
    //Setup the resale flag, needed for tax calculations
    ShoppingCartData shoppingCartD = ShopTool.getCurrentShoppingCart(session);
    //set all to be false
    Iterator it = shoppingCartD.getItems().iterator();
    ArrayList tmpReSaleItemL = new ArrayList();
    while (it.hasNext()) {
      ShoppingCartItemData sciD = (ShoppingCartItemData) it.next();
      if (!isInitialRequest) {
        sciD.setReSaleItem(false);
      }
    }

    //loop through what we got from the UI and set the values that are true
    for (int ii = 0; ii < pForm.getReSaleSelectBox().length; ii++) {
      long RSvalue = 0;
      if (pForm.getReSaleSelectBox()[ii] != null && !"".equals(pForm.getReSaleSelectBox()[ii])) {
        try {
          RSvalue = Long.parseLong(pForm.getReSaleSelectBox()[ii]);
        } catch (Exception e) {}
      }
      if (RSvalue > 0) {
        ShoppingCartItemData scid = shoppingCartD.findItem(RSvalue);
        if (scid != null) {
          scid.setReSaleItem(true);
        }
      }
      tmpReSaleItemL.add(pForm.getReSaleSelectBox()[ii]);
    }
    pForm.setReSaleSelectBox((String[]) tmpReSaleItemL.toArray(pForm.getReSaleSelectBox()));

    Order orderEjb;
    try {
      //get the order API used for tax calculations
      orderEjb = ((APIAccess) session.getAttribute(Constants.APIACCESS)).getOrderAPI();
    } catch (Exception e) {
      ae.add("error", new ActionError("error.systemError", "No APIAccess."));
      return ae;
    }

    Site siteEjb;
    try {
      //get the site API used for tax calculations
      siteEjb = ((APIAccess) session.getAttribute(Constants.APIACCESS)).getSiteAPI();
    } catch (Exception e) {
      ae.add("error", new ActionError("error.systemError", "No APIAccess."));
      return ae;
    }

    Distributor distributorEjb;
    try {
      //get the Distributor API used for tax calculations
      distributorEjb = ((APIAccess) session.getAttribute(Constants.APIACCESS)).getDistributorAPI();
    } catch (Exception e) {
      ae.add("error", new ActionError("error.systemError", "No APIAccess."));
      return ae;
    }

    Catalog catalogEjb;
    try {
      //get the Catalog API used for tax calculations
    	catalogEjb = ((APIAccess) session.getAttribute(Constants.APIACCESS)).getCatalogAPI();
    } catch (Exception e) {
      ae.add("error", new ActionError("error.systemError", "No APIAccess."));
      return ae;
    }

    //Liang - set up the order freight table according to distributors
    // only for stores other than MLA
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    boolean isaMLAStore = RefCodeNames.STORE_TYPE_CD.MLA.equals(appUser.getUserStore().getStoreType().getValue());

    boolean fl_salesTax = true;

    if (!isaMLAStore) { //NOT an MLA store
      ShoppingCartDistDataVector cartDistV = pForm.getCartDistributors();
      if (!isInitialRequest) {
        String[] freightVendors = pForm.getDistFreightVendor();
        if (null != cartDistV && cartDistV.size() > 0) {
          boolean hasError = false;
          int minNum = 0;
          if (freightVendors != null) {
            minNum = freightVendors.length < cartDistV.size() ? freightVendors.length : cartDistV.size();
          }
          for (int i = 0; i < minNum; i++) {
            ShoppingCartDistData distD = (ShoppingCartDistData) cartDistV.get(i);
            if ((null == freightVendors[i] || 0 == freightVendors[i].trim().length())
                && (null != distD.getDistFreightOptions() && 0 < distD.getDistFreightOptions().size())) {
              Object[] param = new Object[1];
              param[0] = distD.getDistributorName();
              String errorMess =
                ClwI18nUtil.getMessage(request, "shop.errors.selectShippingMethodForDistributor", param);
              ae.add(ActionErrors.GLOBAL_ERROR,
                     new ActionError("error.simpleGenericError", errorMess));
              hasError = true;
            }
          }
          if (hasError) {
            // return ae;
          }
        }

        cartDistV.setDistFreightVendor(freightVendors);
        pForm.setCartDistributors(cartDistV);
      }

      BigDecimal totalSalesTax = ZERO;
      it = cartDistV.iterator();

      //boolean fl_salesTax = true;
      while (it.hasNext()) {
        ShoppingCartDistData distD = (ShoppingCartDistData) it.next();
        //Now set the sales tax
        //calculate the sales tax
        try {
          if (distD != null) {
              ShoppingCartItemDataVector items = distD.getShoppingCartItems();

              /*** old piece of code
              BigDecimal salesTax = ShopTool.getSalesTax(orderEjb
                                                                ,siteEjb
                                                                ,appUser.getUserStore()
                                                                ,appUser.getUserAccount()
                                                                ,appUser.getSite()
                                                                ,items);
              ***/

              /*** SVC: new piece of code ***/
              BigDecimal salesTax = ShopTool.getSalesTaxAvalara(orderEjb
              //ArrayList salesTaxList = ShopTool.getSalesTaxAvalara(orderEjb
                                                                ,siteEjb
                                                                ,distributorEjb
                                                                ,appUser.getUserStore()
                                                                ,appUser.getUserAccount()
                                                                ,appUser.getSite()
                                                                ,items
                                                                ,catalogEjb);
              //BigDecimal salesTax = (BigDecimal) salesTaxList.get(0); // amount of salesTax in local currency
              //log.info("salesTaxList.get(1) = " + salesTaxList.get(1));
              //if (((Boolean) salesTaxList.get(1)).booleanValue() == false) {
              //	  fl_salesTax = false;
              //}
              /******************************/
              distD.setSalesTax(salesTax);
              totalSalesTax=Utility.addAmt(totalSalesTax,salesTax);

            }
        /***
        } catch (TaxCalculationException e) {
          e.printStackTrace();
          //XXX possibly display error message when system matures a litte?
          //ae.add("salesTax",new ActionError("error.systemError",e.getMessage()));
          //don't return, just continue
        ***/
        } catch (Exception e) {
          //display error message when AvaTax tax calculation fails (do we need it ?)
          ae.add("salesTax",new ActionError("error.systemError",e.getMessage()));
          e.printStackTrace();
        }
      }
      pForm.setSalesTax(totalSalesTax);
      /***
      if (fl_salesTax == false) {
          String errorMessage = "Calculation of Sales Tax for one or more items failed.";
          ae.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.simpleGenericError", errorMessage));
      }
      ***/
    } else {
        //else this IS an MLA store and should not display distributor specific data.
        //calculae the sales tax
        BigDecimal totalSalesTax = ZERO;
        try {

            if (shoppingCartD != null) {

                ShoppingCartItemDataVector items = shoppingCartD.getItems();
                /*** old piece of code
                BigDecimal salesTax = ShopTool.getSalesTax(orderEjb
                                                                  ,siteEjb
                                                                  ,appUser.getUserStore()
                                                                  ,appUser.getUserAccount()
                                                                  ,appUser.getSite()
                                                                  ,items);
                ***/

                /*** SVC: new piece of code ***/
                BigDecimal salesTax = ShopTool.getSalesTaxAvalara(orderEjb
                //ArrayList salesTaxList = ShopTool.getSalesTaxAvalara(orderEjb
                                                                  ,siteEjb
                                                                  ,distributorEjb
                                                                  ,appUser.getUserStore()
                                                                  ,appUser.getUserAccount()
                                                                  ,appUser.getSite()
                                                                  ,items
                                                                  ,catalogEjb);
                /***
                BigDecimal salesTax = (BigDecimal) salesTaxList.get(0); // amount of salesTax in local currency
                log.info("salesTaxList.get(1) = " + salesTaxList.get(1));
                if (((Boolean) salesTaxList.get(1)).booleanValue() == false) {
              	  fl_salesTax = false;
                }
                ***/
                /******************************/
                totalSalesTax=Utility.addAmt(totalSalesTax,salesTax);

            }
        /***
        } catch (TaxCalculationException e) {
            e.printStackTrace();
            //XXX possibly display error message when system matures a litte?
            //ae.add("salesTax",new ActionError("error.systemError",e.getMessage()));
            //don't return, just continue
        ***/
        } catch (Exception e) {
            e.printStackTrace();
        }
        pForm.setSalesTax(totalSalesTax);
        /***
        if (fl_salesTax == false) {
            String errorMessage = "Calculation of Sales Tax for one or more items failed.";
            ae.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.simpleGenericError", errorMessage));
        }
        ***/
    }

    return ae;

  }



    public static ActionErrors setupServiceOrderFreightTable(HttpServletRequest request, CheckoutForm pForm,
    boolean isInitialRequest) {

    HttpSession session = request.getSession();
    ActionErrors ae = new ActionErrors();
    //Setup the resale flag, needed for tax calculations
    ShoppingCartData shoppingCartD = ShopTool.getCurrentShoppingCart(session);

    Order orderEjb;
    try {
      //get the order API used for tax calculations
      orderEjb = ((APIAccess) session.getAttribute(Constants.APIACCESS)).getOrderAPI();
    } catch (Exception e) {
      ae.add("error", new ActionError("error.systemError", "No APIAccess."));
      return ae;
    }

    Site siteEjb;
    try {
      //get the site API used for tax calculations
      siteEjb = ((APIAccess) session.getAttribute(Constants.APIACCESS)).getSiteAPI();
    } catch (Exception e) {
      ae.add("error", new ActionError("error.systemError", "No APIAccess."));
      return ae;
    }

    //Liang - set up the order freight table according to distributors
    // only for stores other than MLA
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    boolean isaMLAStore = RefCodeNames.STORE_TYPE_CD.MLA.equals(appUser.getUserStore().getStoreType().getValue());
    if (!isaMLAStore) {
      ShoppingCartDistDataVector cartDistV = pForm.getCartDistributors();
      if (!isInitialRequest) {
        String[] freightVendors = pForm.getDistFreightVendor();
        if (null != cartDistV && cartDistV.size() > 0) {
          boolean hasError = false;
          int minNum = 0;
          if (freightVendors != null) {
            minNum = freightVendors.length < cartDistV.size() ? freightVendors.length : cartDistV.size();
          }
          for (int i = 0; i < minNum; i++) {
            ShoppingCartDistData distD = (ShoppingCartDistData) cartDistV.get(i);
            if ((null == freightVendors[i] || 0 == freightVendors[i].trim().length())
                && (null != distD.getDistFreightOptions() && 0 < distD.getDistFreightOptions().size())) {
              Object[] param = new Object[1];
              param[0] = distD.getDistributorName();
              String errorMess =
                ClwI18nUtil.getMessage(request, "shop.errors.selectShippingMethodForDistributor", param);
              ae.add(ActionErrors.GLOBAL_ERROR,
                     new ActionError("error.simpleGenericError", errorMess));
              hasError = true;
            }
          }
          if (hasError) {
            // return ae;
          }
        }

        cartDistV.setDistFreightVendor(freightVendors == null ? new String[] {""}
                                        : freightVendors);
        pForm.setCartDistributors(cartDistV);
      }

      BigDecimal totalSalesTax = ZERO;
      Iterator it = cartDistV.iterator();
        while (it.hasNext()) {
            ShoppingCartDistData distD = (ShoppingCartDistData) it.next();
            //Now set the sales tax
            //calculae the sales tax
            try {
                if (distD != null) {

                    ShoppingCartServiceDataVector services = distD.getShoppingCartServices();
                    // getSalesTax() method below was NOT changed to accomodate Avatax,
                    // because Service taxes currently are NOT used in stjon application
                    BigDecimal salesTax = ShopTool.getSalesTax(orderEjb,
                                                      siteEjb,
                                                      appUser.getUserStore(),
                                                      appUser.getUserAccount(),
                                                      appUser.getSite(),
                                                      services);

                    distD.setSalesTax(salesTax);
                    totalSalesTax = Utility.addAmt(totalSalesTax, salesTax);

                }
            } catch (TaxCalculationException e) {
                e.printStackTrace();
                //XXX possibly display error message when system matures a litte?
                //ae.add("salesTax",new ActionError("error.systemError",e.getMessage()));
                //don't return, just continue
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
      pForm.setSalesTax(totalSalesTax);
    } else {
        //else this IS an MLA store and should not display distributor specific data.
        //calculae the sales tax
        BigDecimal totalSalesTax = ZERO;
        try {

            if (shoppingCartD != null) {

                ShoppingCartServiceDataVector services = shoppingCartD.getServices();
                // getSalesTax() method below was NOT changed to accomodate Avatax,
                // because Service taxes currently are NOT used in stjon application
                BigDecimal salesTax = ShopTool.getSalesTax(orderEjb,
                                                  siteEjb,
                                                  appUser.getUserStore(),
                                                  appUser.getUserAccount(),
                                                  appUser.getSite(),
                                                  services);

                totalSalesTax = Utility.addAmt(totalSalesTax, salesTax);
            }
        } catch (TaxCalculationException e) {
            e.printStackTrace();
            //XXX possibly display error message when system matures a litte?
            //ae.add("salesTax",new ActionError("error.systemError",e.getMessage()));
            //don't return, just continue
        } catch (Exception e) {
            e.printStackTrace();
        }
        pForm.setSalesTax(totalSalesTax);
    }

    return ae;

  }

  private static PropertyDataVector mkCheckOutProperties(CheckoutForm pForm) {
    PropertyDataVector v = pForm.getSite().getDataFieldProperties();
    if (null == v) {
      v = new PropertyDataVector();
    }

    // Now add any options configured for checkout.
    java.util.Iterator keys = pForm.getCheckoutOptionsMapKeys();
    while (null != keys && keys.hasNext()) {
      String k = (String) keys.next();
      String val = (String) pForm.getCheckoutOption(k);
      PropertyData prop = PropertyData.createValue();
      prop.setShortDesc(k);
      prop.setValue(val);
      prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.CHECKOUT_OPTION);
      v.add(prop);
    }
    return v;
  }

  private static ActionError verifyCheckOutOptions(CheckoutForm pForm) {

    // Now check on checkout keys.
    java.util.Iterator keys = pForm.getCheckoutOptionsMapKeys();
    while (null != keys && keys.hasNext()) {
      String k = (String) keys.next();
      String val = (String) pForm.getCheckoutOption(k);
      if (null != val && (val.length() == 0 || val.equals(Constants.CwUi.SELECT))) {
        return new ActionError("variable.select.empty.error", k);
      }
    }
    return null;
  }


  //****************************************************************************
   public static ActionErrors orderService(HttpServletRequest request, CheckoutForm pForm) throws Exception {

     pForm.setComments(I18nUtil.getUtf8Str(pForm.getComments()));
     pForm.setRequestedBillToAddress(getUtf8(pForm.getRequestedBillToAddress()));

     Date stime = checkoutDbg(request, "orderService START", null);
     ActionErrors ae = new ActionErrors();
     HttpSession session = request.getSession();

     session.setAttribute("CheckoutForm", pForm);

     Integer contractId = (Integer)
                          session.getAttribute(Constants.CONTRACT_ID);
     if (null == contractId) {
       contractId = new Integer(0);
     }

     //pickup items
     ShoppingCartData shoppingCartD = ShopTool.getCurrentShoppingCart(session);
     if (shoppingCartD == null) {
       ae.add("error", new ActionError("error.systemError", "Shopping cart available."));
       return ae;
     }

     APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
     if (factory == null) {
       ae.add("error", new ActionError("error.systemError", "No Ejb access"));
       return ae;
     }
     Account accountEjb = null;
     try {
       accountEjb = factory.getAccountAPI();
     } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
       ae.add("error", new ActionError("error.systemError", "No account API Access"));
       return ae;
     }

     //Compare cartServices with shoppingCart
     // List  cartServices = getSelectedServices(shoppingCartD.getServices(),pForm.getOrderServiceSelectBox());
     List cartServices = shoppingCartD.getServices();

     if (cartServices == null || cartServices.isEmpty()) {
       String errorMess =
         ClwI18nUtil.getMessage(request, "shop.errors.orderCannotBeProcessedDueEmptyShoppingCart", null);
       ae.add(ActionErrors.GLOBAL_ERROR,
              new ActionError("error.simpleGenericError", errorMess));
       return ae;
     }

     CustomerOrderRequestData orderReq = new CustomerOrderRequestData();
     Date currDate = new Date();
     SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
     orderReq.setContractId(contractId.intValue());
     // Send the freight specified in the UI.
     double frtamt = 0, hndlamt = 0;
     if (pForm.getFreightAmt() != null) {
       frtamt = pForm.getFreightAmt().doubleValue();
     }
     // Send the handling specified in the UI.
     if (pForm.getHandlingAmt() != null) {
       hndlamt = pForm.getHandlingAmt().doubleValue();
     }

     orderReq.setFreightCharge(frtamt);
     orderReq.setHandlingCharge(hndlamt);

     if (Utility.isSet(pForm.getRequestedShipDate())) {
       try {
         //Date aDate = sdf.parse(pForm.getRequestedShipDate());
         Date aDate = ClwI18nUtil.parseDateInp(request, pForm.getRequestedShipDate());

         if (aDate.compareTo(currDate) <= 0) {
           String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.deliveryBeforeCurrentDate", null);
           ae.add("error", new ActionError("error.simpleGenericError", errorMess));
           return ae;
         }
         orderReq.setOrderRequestedShipDate(aDate);

       } catch (Exception e) {
         ae.add("error", new ActionError("error.badDateFormat", pForm.getRequestedShipDate()));
         return ae;
       }
     }

     //Liang - set up the order freight table according to distributors
     // only for stores other than MLA
     CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
     boolean isaMLAStore = RefCodeNames.STORE_TYPE_CD.MLA.equals(appUser.getUserStore().getStoreType().getValue());
     ae = setupServiceOrderFreightTable(request, pForm, false);
     if (!isaMLAStore) {
       if (!ae.isEmpty()) {
         return ae;
       }

       OrderFreightDataVector orderFreightList = null;
       ShoppingCartDistDataVector cartDistributors = pForm.getCartDistributors();
       if (null != cartDistributors && 0 < cartDistributors.size()) {
         orderFreightList = new OrderFreightDataVector();
         for (int i = 0; i < cartDistributors.size(); i++) {
           ShoppingCartDistData distD = (ShoppingCartDistData) cartDistributors.get(i);
           List impliedFreight = null;
           FreightTableCriteriaData selectedFreight = null;

           if (null != distD) {
             impliedFreight = distD.getDistFreightImplied();
             selectedFreight = distD.getDistSelectedFreight();

           }
           if (null != impliedFreight && 0 < impliedFreight.size()) {
             for (int j = 0; j < impliedFreight.size(); j++) {
               FreightTableCriteriaData crit = (FreightTableCriteriaData) impliedFreight.get(j);
               OrderFreightData orderFreightD = OrderFreightData.createValue();
               orderFreightD.setBusEntityId(distD.getDistributor().getBusEntityId());
               orderFreightD.setFreightTypeCd(crit.getFreightCriteriaTypeCd());
               orderFreightD.setShortDesc(crit.getShortDesc());
               orderFreightD.setAmount(crit.getFreightAmount()!=null?
                                       crit.getFreightAmount():crit.getHandlingAmount());
               orderFreightD.setFreightHandlerId(crit.getFreightHandlerId());
               orderFreightList.add(orderFreightD);
             }
           }
           if (null != selectedFreight) {
             OrderFreightData orderFreightD = OrderFreightData.createValue();
             orderFreightD.setBusEntityId(distD.getDistributor().getBusEntityId());
             orderFreightD.setFreightTypeCd(selectedFreight.getFreightCriteriaTypeCd());
             orderFreightD.setShortDesc(selectedFreight.getShortDesc());
             orderFreightD.setAmount(selectedFreight.getFreightAmount()!=null?
                                     selectedFreight.getFreightAmount():
                                     selectedFreight.getHandlingAmount());
             orderFreightD.setFreightHandlerId(selectedFreight.getFreightHandlerId());

             orderFreightList.add(orderFreightD);
           }

         }
       }

       if (null != orderFreightList && 0 < orderFreightList.size()) {
         orderReq.setOrderFreightList(orderFreightList);
       }

        ///
        OrderAddOnChargeDataVector orderAddOnCharges =
            getFuelSurchargeAndSmallOrderFeeForOrder(pForm.getCartDistributors());
        if (orderAddOnCharges != null && orderAddOnCharges.size() > 0) {
            orderReq.setOrderAddOnChargeList(orderAddOnCharges);
        }
     }
     //Check site
     SiteData site = appUser.getSite();
     int siteId = site.getBusEntity().getBusEntityId();
     if (siteId != pForm.getSiteId()) {
       String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.pageExpired1", null);
       ae.add("error", new ActionError("error.simpleGenericError", errorMess));
       return ae;
     }
     pForm.setSite(site);
     //Get Account
     //XXX why not get out of session ala: AccountData account = appUser.getUserAccount()?
     AccountData account = accountEjb.getAccountForSite(siteId);
     if (account == null) {
       ae.add("error", new ActionError("error.systemError", "No account information found"));
       return ae;
     }

     for (int ii = 0; ii < cartServices.size(); ii++) {
       ShoppingCartServiceData cartService =
         (ShoppingCartServiceData) cartServices.get(ii);

       if (cartService.getQuantity() == 0)continue; // 0 qty line
       if (cartService.getAssetData() == null)continue;
       if (cartService.getItemData() == null)continue;

       int itemId = cartService.getItemData().getItemId();
       int clw_skunum = cartService.getItemData().getSkuNum();
       int qty = cartService.getQuantity();
       String shortDesc = cartService.getItemData().getShortDesc();

       double price = cartService.getPrice();
       orderReq.addItemEntry(ii + 1, itemId, cartService.getAssetData().getAssetId(),
                             RefCodeNames.ITEM_TYPE_CD.SERVICE, clw_skunum, qty, price, shortDesc);
     }

     //Check user
     int userId = appUser.getUser().getUserId();
     orderReq.setUserId(userId);
     orderReq.setUserName(appUser.getUser().getUserName());
     if (userId != pForm.getUserId()) {
       String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.pageExpired", null);
       ae.add("error", new ActionError("error.simpleGenericError", errorMess));
       return ae;
     }

     //
     // If this is not a credit card purchase and the
     // user can specify their own bill to address, then
     // perform some basic address information checking.
     //
     if (appUser.canEditBillTo()) {
       if (!pForm.hasRequestedBillToAddress()) {
         Object[] param = new Object[1];
         param[0] = pForm.getRequestBillToCheckMessage();
         String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.checkBillingAddress", param);
         ae.add("error", new ActionError("error.simpleGenericError", errorMess));
         return ae;
       }

       orderReq.setRequestedBillingAddress(pForm.getRequestedBillToAddress());
     }

     orderReq.setSiteId(siteId);
     orderReq.setAccountId(account.getBusEntity().getBusEntityId());

     orderReq.setCustomerSystemIdentifier((String) session.getAttribute(Constants.CUSTOMER_SYSTEM_ID));
     orderReq.setCustomerSystemURL((String) session.getAttribute(Constants.CUSTOMER_SYSTEM_URL));

     try {

       // Check PO number presence
       String poNum = pForm.getPoNumber();
       if (Utility.isSet(poNum) && !account.isCustomerRequestPoAllowed()) {
         //UI should not be allowing this
         String errorMess =
           ClwI18nUtil.getMessage(request, "shop.errors.poNumberIsNotAllowedForTheAccount", null);
         ae.add("error", new ActionError("error.simpleGenericError", errorMess));
         return ae;
       }
       boolean poNumFl = (!(poNum == null || poNum.trim().length() == 0));

       if (appUser.getPoNumRequired() && !poNumFl && pForm.getReqPmt().equals("PO")
           && !site.isBlanketPoNumSet() && account.isCustomerRequestPoAllowed()
         ) {
         String poNumLabel = ClwI18nUtil.getMessage(request, "shop.checkout.text.poNumber", null);
         ae.add("error", new ActionError("variable.empty.error", poNumLabel));
         return ae;
       }
       orderReq.setCustomerPoNumber(poNum);
       orderReq.setCustomerComments(pForm.getComments());
       String processOrderDate = pForm.getProcessOrderDate();
       if (Utility.isSet(processOrderDate)) {

         Date procDate = null;
         try {
           //procDate = Utility.parseDate(processOrderDate);
           procDate = ClwI18nUtil.parseDateInp(request, processOrderDate);
         } catch (Exception exc) {}
         if (procDate == null) {
           Object[] param = new Object[1];
           param[0] = processOrderDate;
           String errorMess =
             ClwI18nUtil.getMessage(request,
                                    "shop.errors.wrongProcessOrderOnDateFormat", param);
           ae.add("error", new ActionError("error.simpleGenericError",
                                           errorMess));
           return ae;
         }

         try {
           currDate = sdf.parse(sdf.format(currDate));
         } catch (Exception exc) {} //never should happen
         if (procDate.compareTo(currDate) <= 0) {
           String errorMess =
             ClwI18nUtil.getMessage(request,
                                    "shop.errors.processOnDateCannotBeBeforeCurrenDate", null);
           ae.add("error", new ActionError
                  ("error.simpleGenericError", errorMess));
           return ae;
         }

         processOrderDate = sdf.format(procDate);
         orderReq.setHoldUntilDate(processOrderDate);
         String processOrderDateInp = ClwI18nUtil.formatDateInp(request, procDate);
         pForm.setProcessOrderDate(processOrderDateInp);
       }

       if (orderReq.getProperties() == null) {
         orderReq.setProperties(new PropertyDataVector());
       }

       ActionError checkOnOptions = verifyCheckOutOptions(pForm);
       if (null != checkOnOptions) {
         ae.add("error", checkOnOptions);
         return ae;
       }

       orderReq.getProperties().addAll(mkCheckOutProperties(pForm));

       //Order note is required for a billing order
       if (pForm.isBillingOrder()) {

         if (appUser.isaCustServiceRep()) {
           if ((pForm.getOrderNote() == null || pForm.getOrderNote().trim().length() == 0)) {
             String orderNoteLabel =
               ClwI18nUtil.getMessage(request,
                                      "shop.checkout.text.oderNote", null);
             ae.add("error", new ActionError("variable.empty.error", orderNoteLabel));
             return (ae);
           }
         }

         Iterator it = pForm.getServices().iterator();
         String newDistErpNum = null;
         while (it.hasNext()) {
           ShoppingCartServiceData itm = (ShoppingCartServiceData) it.next();
           String newErp = null;
           if (itm.getService().getCatalogDistributor() != null) {
             newErp = itm.getService().getCatalogDistributor().getErpNum();
           } else {
             Object[] param = new Object[1];
             param[0] = "" + itm.getService().getItemData().getSkuNum();
             String errorMess =
               ClwI18nUtil.getMessage(request,
                                      "shop.errors.missingDistributorForBillingOrder", param);
             ae.add("error", new ActionError("error.simpleGenericError", errorMess));
             return ae;
           }

           if (newDistErpNum == null) {
             newDistErpNum = newErp;
           } else if (!newDistErpNum.equals(newErp)) {
             String errorMess =
               ClwI18nUtil.getMessage(request,
                                      "shop.errors.multipleDistributorsForBillingOrder", null);
             ae.add("error", new ActionError("error.simpleGenericError", errorMess));
             return ae;
           }
         }

         if ((pForm.getBillingOriginalPurchaseOrder() == null ||
              pForm.getBillingOriginalPurchaseOrder().trim().length() == 0)) {
           //ae.add("error",new ActionError("variable.empty.error","Billing Original Purchase Order"));
           //return (ae);
         } else {
           PurchaseOrderStatusCriteriaData pocrit = PurchaseOrderStatusCriteriaData.createValue();

           pocrit.setStoreIdVector(appUser.getUserStoreAsIdVector());
           pocrit.setErpPONum(pForm.getBillingOriginalPurchaseOrder());
           PurchaseOrderDataVector pov = factory.getPurchaseOrderAPI().getPurchaseOrderCollection(pocrit);
           if (pov.size() != 1) {
             Object[] param = new Object[2];
             param[0] = "" + pov.size();
             param[1] = pForm.getBillingOriginalPurchaseOrder();
             String errorMess =
               ClwI18nUtil.getMessage(request,
                                      "shop.errors.multipleErpPos", param);
             ae.add("error", new ActionError("error.simpleGenericError", errorMess));
             return (ae);
           } else {
             PurchaseOrderData po = (PurchaseOrderData) pov.get(0);
             if (newDistErpNum != null) {
               if (!newDistErpNum.equals(po.getDistErpNum())) {
                 Object[] param = new Object[2];
                 param[0] = po.getDistErpNum();
                 param[1] = newDistErpNum;
                 String errorMess =
                   ClwI18nUtil.getMessage(request,
                                          "shop.errors.distributorPoMismatch", param);
                 ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                 return ae;
               }
             }
           }
         }
       }

       if ((pForm.getOrderNote() != null && pForm.getOrderNote().trim().length() > 0)) {
         orderReq.setOrderNote(I18nUtil.getUtf8Str(pForm.getOrderNote()));
       }

       if (Utility.isSet(pForm.getCustomerComment())) {
         orderReq.addArbitraryOrderProperty
           (RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS, pForm.getCustomerComment());
         //pForm.setCustomerComment(null);
       }

       if (orderReq.getProperties() == null) {
         orderReq.setProperties(new PropertyDataVector());
       }
       orderReq.setBillingOrder(pForm.isBillingOrder());
       if (pForm.isBillingOrder()) {
         //add dist invoice prop
         PropertyData pd = PropertyData.createValue();
         pd.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
         pd.setPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_DISTRIBUTOR_INVOICE);
         pd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_DISTRIBUTOR_INVOICE);
         pd.setValue(pForm.getBillingDistributorInvoice());
         orderReq.getProperties().add(pd);
         //add bill only orig po num prop
         pd = PropertyData.createValue();
         pd.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
         pd.setPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORIGINAL_PO_NUM);
         pd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORIGINAL_PO_NUM);
         pd.setValue(pForm.getBillingOriginalPurchaseOrder());
         orderReq.getProperties().add(pd);
       }
       //add customerRequestedReshipOrderNum prop
       if (!(pForm.getCustomerRequestedReshipOrderNum() == null ||
             pForm.getCustomerRequestedReshipOrderNum().trim().length() == 0)) {
         PropertyData pd = PropertyData.createValue();
         pd.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
         pd.setPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUST_REQ_RESHIP_ORDER_NUM);
         pd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUST_REQ_RESHIP_ORDER_NUM);
         pd.setValue(pForm.getCustomerRequestedReshipOrderNum());
         orderReq.getProperties().add(pd);
       }

       orderReq.setBypassOrderRouting(pForm.isBypassOrderRouting());
       orderReq.setBypassCustomerWorkflow(pForm.isBypassCustomerWorkflow());

//       log.info("orderService() ***************SVC 5: pForm.isBypassBudget() = " + pForm.isBypassBudget());
       if (pForm.isBypassBudget()) {
         orderReq.setOrderBudgetTypeCd(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE);
       } else {
         orderReq.setOrderBudgetTypeCd(null);
       }

        /// add 'RebillOrder' property into 'orderReq'
        setRebillOrderPropertyForOrderRequest(orderReq, pForm);

       //for crc, make sure contact name and phone number are present
       String type = appUser.getUser().getUserTypeCd();
       if (type.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE) ||
           type.equals(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR) ||
           type.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER)) {
         if (pForm.getOrderContactName() == null ||
             pForm.getOrderContactName().trim().length() == 0) {
           String contactNameLabel =
             ClwI18nUtil.getMessage(request, "shop.checkout.text.contactName", null);

           ae.add("error", new ActionError
                  ("variable.empty.error", contactNameLabel));
           return ae;
         }
         pForm.setOrderContactName(I18nUtil.getUtf8Str(pForm.getOrderContactName()));
         if (pForm.getOrderContactPhoneNum() == null ||
             pForm.getOrderContactPhoneNum().trim().length() == 0) {
           String contactPhoneNumberLabel =
             ClwI18nUtil.getMessage(request, "shop.checkout.text.contactPhoneNum", null);

           ae.add("error", new ActionError
                  ("variable.empty.error", contactPhoneNumberLabel));
           return ae;
         }
         if (pForm.getOrderOriginationMethod() == null ||
             pForm.getOrderOriginationMethod().trim().length() == 0) {
           String methodLabel =
             ClwI18nUtil.getMessage(request, "shop.checkout.text.method", null);

           ae.add("error", new ActionError
                  ("variable.empty.error", methodLabel));
           return ae;
         }
         saveContactInfo(request, pForm);

       } else {
         pForm.setOrderContactName(appUser.getUser().getFirstName() + " " + appUser.getUser().getLastName());
       }
       orderReq.setOrderContactName(pForm.getOrderContactName());
       orderReq.setOrderTelephoneNumber(pForm.getOrderContactPhoneNum());
       orderReq.setOrderEmail(pForm.getOrderContactEmail());
       String orderSourceCd = pForm.getOrderOriginationMethod();
       orderReq.setOrderSourceCd(orderSourceCd);
     } catch (Exception e2) {
       e2.printStackTrace();
       ae.add("error", new ActionError("error.systemError", "Page expired 2"));
       return ae;
     }

     IntegrationServices isvcEjb = null;
     //ShoppingServices shoppingServeicesEjb = null;
     String emsg = "";
     try {
       emsg = "No IntegrationServices API Access";
       isvcEjb = factory.getIntegrationServicesAPI();
     } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
       ae.add("error", new ActionError("error.systemError", emsg));
       return ae;
     }

     Integer catalogIdI = (Integer)
                          session.getAttribute(Constants.CATALOG_ID);
     if (catalogIdI == null) {
       ae.add("error", new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session attrbute found"));
       return ae;
     }

     ProcessOrderResultData orderRes = null;
     String lockName = "==CheckOutLock==";
     Integer lockValue = null;

     try {
       Object oforlock = new Object();
       synchronized (oforlock) {
         lockValue = (Integer) session.getAttribute(lockName);
         if (lockValue == null) {
           lockValue = new Integer("0");
           session.setAttribute(lockName, lockValue);
         }
       }
       if (lockValue.intValue() == 0) {
         // Lock the purchase capability.
         synchronized (oforlock) {
           lockValue = new Integer("1");
           session.setAttribute(lockName, lockValue);
         }

         //deal with inline customer ordering that requieres system pass off (cXML)
         if (doInlineOrderInit(ae, pForm, session, factory, appUser)) {
           checkoutDbg(request, "rdeServicer processOrderRequest 0", stime);
           pForm.setOrderRequest(orderReq);
           orderRes = isvcEjb.processOrderRequest(orderReq);
           checkoutDbg(request, "placeOrder processOrderRequest 1", stime);
           if (orderRes.getOrderId() == 0) {
             String errorMess =
               ClwI18nUtil.getMessage(request, "shop.errors.orderRejected", null);
             String[] messA = orderRes.getMessages();
             if (messA != null && messA.length > 0) {
               for (int ii = 0; ii < messA.length; ii++) {
                 String translatedMess = ClwI18nUtil.formatEjbError(request, messA[ii]);
                 if (translatedMess != null && translatedMess.length() > 0) {
                   errorMess += ". " + translatedMess;
                 }
               }
             }
             ae.add("error", new
                    ActionError("error.simpleGenericError", errorMess));
             return ae;
           }
         }
       } else {
         // The lock value is 1.  Meaning a purchase is
         // already in progress.
         String errorMess =
           ClwI18nUtil.getMessage(request, "shop.errors.purchaseIsInProgress", null);
         ae.add("error", new ActionError("error.simpleGenericError", errorMess));
         return ae;
       }

       if (!(ae.size() == 0)) {
         return ae;
       }

       pForm.setConfirmationFlag(true);
       pForm.setOrderServiceFlag(true);
       pForm.setOrderResult(orderRes);
       pForm.setSite(site);
       pForm.setAccount(account);
       pForm.setServices(shoppingCartD.getServices());

       //Remove items from shopping cart
       shoppingCartD.setServices(new ShoppingCartServiceDataVector());
       shoppingCartD.setPrevOrderData(null);

       //   ae = ShoppingCartLogic.saveShoppingCart(site,session,orderRes);


       // Unlock the purchase capability.
       lockValue = new Integer("0");
       session.setAttribute(lockName, lockValue);
       checkoutDbg(request, "orderService DONE", stime);
     }

     catch (Exception exc) {
       exc.printStackTrace();
       log.error("CheckoutLogic: orderService ERRROR 4 MESSAGE="+exc.getMessage());
       return mkUserOrderMessage(request, exc.getMessage());
     } finally {
       //unlock the order functionality
       lockValue = new Integer("0");
       session.setAttribute(lockName, lockValue);
     }
     return ae;
   }

  private static List getSelectedServices(ShoppingCartServiceDataVector services, String[] orderServiceSelectBox) {

    ShoppingCartServiceDataVector selServ = new ShoppingCartServiceDataVector();

    if (services != null && services.size() > 0 &&
        orderServiceSelectBox != null && orderServiceSelectBox.length > 0) {
      for (int i = 0; i < orderServiceSelectBox.length; i++) {

        StringTokenizer st = new StringTokenizer(orderServiceSelectBox[i], ",");

        int numParam = 1;
        PairView pair = null;
        String param1 = null;
        String param2 = null;
        while (st.hasMoreTokens()) {

          String param = st.nextToken();
          if (numParam == 1) {
            param1 = param;
          }
          if (numParam == 2) {
            param2 = param;
            try {
              pair = new PairView(new Integer(param1), new Integer(param2));
              break;
            } catch (NumberFormatException e) {
              e.printStackTrace();
            }
          }
          numParam++;
        }
        if (pair != null) {
          Iterator itServices = services.iterator();
          while (itServices.hasNext()) {
            ShoppingCartServiceData servData = (ShoppingCartServiceData) itServices.next();
            if (servData.getService() != null
                && servData.getAssetData() != null
                && servData.getService().getItemData() != null) {
              if (servData.getService().getItemData().getItemId() == ((Integer) pair.getObject1()).intValue()
                  && servData.getAssetData().getAssetId() == ((Integer) pair.getObject2()).intValue()) {
                selServ.add(servData);
                break;
              }
            }
          }
        }
      }

    }

    return selServ;
  }

  public static ActionErrors initOrderService(HttpServletRequest request, CheckoutForm pForm) throws
    APIServiceAccessException, RemoteException {

    pForm = new CheckoutForm();
    pForm.init();

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
    if (factory == null) {
      ae.add("error", new ActionError("error.systemError", "No Ejb access"));
      return ae;
    }
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    if (appUser == null) {
      ae.add("error", new ActionError("error.systemError", "No " + Constants.APP_USER + " session object found"));
      return ae;
    }
    pForm.setIsStateProvinceRequired(appUser.getUserStore().isStateProvinceRequired());
    SiteData site = appUser.getSite();
    if (site == null) {
      ae.add("error", new ActionError("error.systemError", "No current site information found"));
      return ae;
    }
    pForm.setSite(site);

    CatalogInformation catinfoEjb = null;
    try {
      catinfoEjb = factory.getCatalogInformationAPI();
    } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error", new ActionError("error.systemError",
                                      "No CatalogInformation API Access"));
      return ae;
    }
    CatalogData siteCat = null;
    int siteid = site.getSiteId();
    try {
          siteCat = catinfoEjb.getSiteCatalog(siteid);
    } catch (Exception exc) {
      ae.add("error", new ActionError
             ("error.systemError",
              "No catalog found for site:" + siteid));
      return ae;
    }

    setupShippingMessage(pForm, site, siteCat);

    AddressData siteAddress = site.getSiteAddress();
    if (siteAddress == null) {
      ae.add("error", new ActionError("error.systemError", "No current site address information found"));
      return ae;
    }
    Account accountEjb = null;
    try {
      accountEjb = factory.getAccountAPI();
    } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error", new ActionError("error.systemError", "No account API Access"));
      return ae;
    }
    AccountData account = null;
    try {
      account = accountEjb.getAccountForSite(site.getSiteId());
      pForm.setAccount(account);
      if (appUser.canEditBillTo()) {
        // Get the requested bill to for this user.
        pForm.setRequestedBillToAddress
          (getLastBillTo(appUser.getUserId(), site.getSiteId()));

      }
    } catch (Exception exc) {
      ae.add("error", new ActionError("error.systemError", "Can't pickup account information from database"));
      return ae;
    }

    ShoppingServices shoppingServEjb = null;
    try {
      shoppingServEjb = factory.getShoppingServicesAPI();
    } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error", new ActionError("error.systemError", "No shopping services API Access"));
      return ae;
    }

    AddressData accountAddress = account.getBillingAddress();
    if (accountAddress == null) {
      ae.add("error", new ActionError("error.systemError", "No current account address information found"));
      return ae;
    }
    ////// !!!!!! SelectShippingAddressLogic.setShoppingSessionObjects not support reload services
    ShoppingCartData shoppingCartD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
    if (appUser.getSite() != null) {
      SelectShippingAddressLogic.setShoppingSessionObjects(session, appUser);
    }
    session.setAttribute(Constants.SHOPPING_CART, shoppingCartD);
    ///////////////////////////////////////////////////////////////////////////////////////////////////



    shoppingCartD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
    if (shoppingCartD == null) {
      ae.add("error", new ActionError("error.systemError", "No " + Constants.SHOPPING_CART + " session object found"));
      return ae;
    }

    ShopTool.initShoppingForm(session);

    Integer contractIdI = (Integer) session.getAttribute(Constants.CONTRACT_ID);
    BigDecimal freightAmt = new BigDecimal(0);
    BigDecimal handlingAmt = new BigDecimal(0);
    BigDecimal cartAmt = new BigDecimal(shoppingCartD.getServices().getItemsCost());
    cartAmt = cartAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
    pForm.setCartServiceAmt(cartAmt);
    int contractId = 0;
    if (contractIdI != null) {
      contractId = contractIdI.intValue();
    }

    //Freight
    OrderHandlingItemViewVector frItems = new OrderHandlingItemViewVector();

    List cartServices = shoppingCartD.getServices();

    for (int ii = 0; ii < cartServices.size(); ii++) {
      ShoppingCartServiceData cartServ = (ShoppingCartServiceData) cartServices.get(ii);
      OrderHandlingItemView frItem = OrderHandlingItemView.createValue();
      frItem.setItemId(cartServ.getItemId());
      BigDecimal priceBD = new BigDecimal(cartServ.getPrice());
      priceBD.setScale(2, BigDecimal.ROUND_HALF_UP);
      frItem.setPrice(priceBD);
      frItem.setQty(cartServ.getQuantity());
      frItems.add(frItem);
    }
    OrderHandlingView frOrder = OrderHandlingView.createValue();
    frOrder.setTotalHandling(new BigDecimal(0));
    frOrder.setTotalFreight(new BigDecimal(0));
    frOrder.setContractId(contractId);
    int accountId = appUser.getSite().getAccountBusEntity().getBusEntityId();
    frOrder.setAccountId(accountId);
    frOrder.setSiteId(shoppingCartD.getSite().getBusEntity().getBusEntityId());
    frOrder.setAmount(cartAmt);
    frOrder.setWeight(new BigDecimal(0));
    frOrder.setItems(frItems);

    try {
      //frOrder = shoppingServEjb.calcTotalFreightAndHandlingAmount(frOrder);
      freightAmt = frOrder.getTotalFreight();
      handlingAmt = frOrder.getTotalHandling();
      //  freightAmt = shoppingServEjb.getFreightAmt(contractId,cartAmt,new BigDecimal(0));
      //  handlingAmt = shoppingServEjb.getHandlingAmt(contractId,cartAmt,new BigDecimal(0));
    } catch (Exception exc) {
      ae.add("error", new ActionError("error.systemError", exc.getMessage()));
      exc.printStackTrace();
      return ae;
    }
    freightAmt = freightAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
    pForm.setFreightAmt(freightAmt);

    handlingAmt = handlingAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
    pForm.setHandlingAmt(handlingAmt);
    
    SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
//    log.info("initOrderService()***************SVC: shoppingCartD.getOrderBudgetTypeCd() = " + shoppingCartD.getOrderBudgetTypeCd());
   if (RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(shoppingCartD.getOrderBudgetTypeCd())||
		   (sessionData != null && sessionData.isExcludeOrderFromBudget())) {
        pForm.setBypassBudget(true);
      } else {
        pForm.setBypassBudget(false);
      }
//   log.info("initOrderService()***************SVC: pForm.isBypassBudget() = " + pForm.isBypassBudget());

    //List cartItems = shoppingCartD.getItems();
    orderServices(session, cartServices, pForm);

    setupServiceOrderFreightTable(request, pForm, true);

    String userTypeCd = appUser.getUser().getUserTypeCd();
    if (RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userTypeCd) ||
        RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userTypeCd) ||
        RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userTypeCd)) {
      String contactName = (String) session.getAttribute("OrderContactName");
      if (Utility.isSet(contactName)) {
        pForm.setOrderContactName(contactName);
      }

      String contactPhone = (String) session.getAttribute("OrderContactPhone");
      if (Utility.isSet(contactPhone)) {
        pForm.setOrderContactPhoneNum(contactPhone);
      }

      String contactEmail = (String) session.getAttribute("OrderContactEmail");
      if (Utility.isSet(contactEmail)) {
        pForm.setOrderContactEmail(contactEmail);
      }

      String orderMethod = (String) session.getAttribute("OrderMethod");
      if (Utility.isSet(orderMethod)) {
        pForm.setOrderOriginationMethod(orderMethod);
      }
    }
    pForm.setOrderServiceFlag(true);
    session.setAttribute("CHECKOUT_FORM", pForm);
    return ae;
  }

  private static AddressData getUtf8(AddressData billToAddress) throws Exception {
    billToAddress.setAddress1(I18nUtil.getUtf8Str(billToAddress.getAddress1()));
    billToAddress.setAddress2(I18nUtil.getUtf8Str(billToAddress.getAddress2()));
    billToAddress.setAddress3(I18nUtil.getUtf8Str(billToAddress.getAddress3()));
    billToAddress.setAddress4(I18nUtil.getUtf8Str(billToAddress.getAddress4()));
    billToAddress.setCity(I18nUtil.getUtf8Str(billToAddress.getCity()));
    billToAddress.setCountryCd(I18nUtil.getUtf8Str(billToAddress.getCountryCd()));
    billToAddress.setCountyCd(I18nUtil.getUtf8Str(billToAddress.getCountyCd()));
    billToAddress.setStateProvinceCd(I18nUtil.getUtf8Str(billToAddress.getStateProvinceCd()));
    billToAddress.setPostalCode(I18nUtil.getUtf8Str(billToAddress.getPostalCode()));
    billToAddress.setName1(I18nUtil.getUtf8Str(billToAddress.getName1()));
    billToAddress.setName2(I18nUtil.getUtf8Str(billToAddress.getName2()));
    return billToAddress;
  }

    public static ActionErrors confirmEarlyRelease(HttpServletRequest request, CheckoutForm pForm) {

        pForm = new CheckoutForm();
        pForm.init();
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.APP_USER + " session object found"));
            return ae;
        }

        SiteData site = appUser.getSite();
        if (site == null) {
            ae.add("error", new ActionError("error.systemError", "No current site information found"));
            return ae;
        }

        CatalogInformation catinfoEjb = null;
        try {
            catinfoEjb = factory.getCatalogInformationAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No CatalogInformation API Access"));
            return ae;
        }

        Account accountEjb;
        try {
            accountEjb = factory.getAccountAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No account API Access"));
            return ae;
        }

        Site siteEjb;
        try {
            siteEjb = factory.getSiteAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No account API Access"));
            return ae;
        }

        Order orderEjb;
        try {
            orderEjb = factory.getOrderAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No order API Access"));
            return ae;
        }

        Distributor distributorEjb;
        try {
            distributorEjb = factory.getDistributorAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No Distributor API Access"));
            return ae;
        }

        Catalog catalogEjb;
        try {
            catalogEjb = factory.getCatalogAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No Catalog API Access"));
            return ae;
        }

        CatalogData siteCat = null;
        int siteid = site.getSiteId();
        try {
            siteCat = catinfoEjb.getSiteCatalog(siteid);
        } catch (Exception exc) {
            ae.add("error", new ActionError("error.systemError", "No catalog found for site:" + siteid));
            return ae;
        }

        AddressData siteAddress = site.getSiteAddress();
        if (siteAddress == null) {
            ae.add("error", new ActionError("error.systemError", "No current site address information found"));
            return ae;
        }

        AccountData account;
        try {
            account = accountEjb.getAccountForSite(site.getSiteId());
            pForm.setAccount(account);
            if (appUser.canEditBillTo()) {
                // Get the requested bill to for this user.
                pForm.setRequestedBillToAddress(getLastBillTo(appUser.getUserId(), site.getSiteId()));
            }
        } catch (Exception exc) {
            ae.add("error", new ActionError("error.systemError", "Can't pickup account information from database"));
            return ae;
        }

        AddressData accountAddress = account.getBillingAddress();
        if (accountAddress == null) {
            ae.add("error", new ActionError("error.systemError", "No current account address information found"));
            return ae;
        }

        setupShippingMessage(pForm, site, siteCat);
        SelectShippingAddressLogic.setShoppingSessionObjects(session, appUser);

        if(!ShopTool.hasInventoryCartAccessOpen(request)) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.scheduleOrderNoLongerAvailable",null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        ShoppingCartData shoppingCartD = ShopTool.getCurrentInventoryCart(session);

        if (shoppingCartD == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.INVENTORY_SHOPPING_CART + " session object found"));
            return ae;
        }


        CustomerOrderRequestData orderReq = null;
        ShoppingCartItemDataVector reqItems = new ShoppingCartItemDataVector();
        try {
            orderReq = siteEjb.constructOrderRequest(appUser.getSite(), shoppingCartD, appUser.getUser());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if(orderReq==null){
           ae.add("error", new ActionError("error.systemError", "No order request object"));
           return ae;
        }

        reqItems = setQtyforRequstedItems(shoppingCartD.getItems(), orderReq);

        BigDecimal salesTax = ZERO;
        try {
            salesTax = ShopTool.getSalesTaxAvalara(orderEjb,
                                   siteEjb,
                                   distributorEjb,
                                   appUser.getUserStore(),
                                   appUser.getUserAccount(),
                                   appUser.getSite(),
                                   reqItems,
                                   catalogEjb);
        } catch (RemoteException e) {
            e.printStackTrace();
        /***
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        ***/
        }

        pForm.setSite(appUser.getSite());
        pForm.setAccount(appUser.getUserAccount());
        pForm.setItems(reqItems);
        pForm.setOrderRequest(orderReq);
        pForm.setSalesTax(salesTax);

        pForm.setFreightAmt(new BigDecimal(orderReq.getFreightCharge()).setScale(2, BigDecimal.ROUND_HALF_UP));
        if (orderReq.getFreightCharge() > 0) {
            pForm.setFreightAmtString(pForm.getHandlingAmt().toString());
        }

        pForm.setHandlingAmt(new BigDecimal(orderReq.getHandlingCharge()).setScale(2, BigDecimal.ROUND_HALF_UP));
        if (orderReq.getHandlingCharge() > 0) {
            pForm.setHandlingAmtString(pForm.getHandlingAmt().toString());
        }
        pForm.setConfirmationFlag(true);
        pForm.setEarlyReleaseFlag(true);
        session.setAttribute("CHECKOUT_FORM", pForm);
        return ae;
    }

    private static ShoppingCartItemDataVector setQtyforRequstedItems(ShoppingCartItemDataVector items, CustomerOrderRequestData orderReq) {

        ShoppingCartItemDataVector resultItems = new ShoppingCartItemDataVector();
        if (items != null && items.size() > 0) {
            if (orderReq != null) {
                Iterator it = items.iterator();
                while (it.hasNext()) {
                    ShoppingCartItemData cartItem = (ShoppingCartItemData) it.next();
                    Iterator it2 = orderReq.getEntriesCollection().iterator();
                    while (it2.hasNext()) {
                        OrderRequestData.ItemEntry itemEntry = (OrderRequestData.ItemEntry) it2.next();
                        if (itemEntry.getItemId() == cartItem.getItemId()) {
                            cartItem.setQuantity(itemEntry.getQuantity());
                            if (cartItem.getQuantity() > 0) {
                                cartItem.setQuantityString(String.valueOf(cartItem.getQuantity()));
                            }
                            resultItems.add(cartItem);
                        }
                    }
                }
            }
        }
        return resultItems;
    }

    public static ActionErrors earlyRelease(HttpServletRequest request, CheckoutForm pForm) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();


        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No " + Constants.APP_USER + " session object found"));
            return ae;
        }

        SiteData site = appUser.getSite();
        if (site == null) {
            ae.add("error", new ActionError("error.systemError", "No current site information found"));
            return ae;
        }

        Site siteEjb = null;
        try {
            siteEjb = factory.getSiteAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No site API Access"));
            return ae;
        }

        ShoppingServices shoppinServEjb = null;
        try {
            shoppinServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            ae.add("error", new ActionError("error.systemError", "No shopping API Access"));
            return ae;
        }

        if(!ShopTool.isModernInventoryCartAvailable(request)) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.modernInventoryCartNotAvailable",null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        //gets store type
        String storeType = null;
        if (appUser.getUserStore().getStoreType() != null) {
            storeType = appUser.getUserStore().getStoreType().getValue();
        }
        //set comments
        if (pForm.getOrderRequest() != null) {
            pForm.getOrderRequest().setCustomerComments(pForm.getComments());
        }

        ShoppingCartData shoppingCart = ShopTool.getCurrentInventoryCart(session);

        if (shoppingCart != null) {
            ae = ShopTool.validateBudgetThreshold(request, shoppingCart);
            if (!ae.isEmpty()) {
                return ae;
            }
        }

        ProcessOrderResultData res = null;
        try {
            if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.INVENTORY_EARLY_RELEASE)) {
                res = siteEjb.inventoryEarlyRelease(site, appUser.getUser(), shoppingCart, pForm.getOrderRequest(), SessionTool.getCategoryToCostCenterView(session, site.getSiteId(), 0));
                if (res == null || res.getOrderId() <= 0) {
                    throw new Exception("No order placed");
                }
            } else {
                throw new Exception("INVENTORY_EARLY_RELEASE function are not allowed for this user.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            String errorMess =
                    ClwI18nUtil.getMessage(request, "shop.errors.orderRejected", null);
            if (res != null) {
                String[] messA = res.getMessages();
                if (messA != null && messA.length > 0) {
                    for (int ii = 0; ii < messA.length; ii++) {
                        String translatedMess = ClwI18nUtil.formatEjbError(request, messA[ii]);
                        if (translatedMess != null && translatedMess.length() > 0) {
                            errorMess += ". " + translatedMess;
                        }
                    }
                }
            }
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        pForm.setSite(appUser.getSite());
        pForm.setAccount(appUser.getUserAccount());
        pForm.setOrderResult(res);
        session.setAttribute("CHECKOUT_FORM", pForm);
        session.setAttribute("CheckoutForm",pForm);
        ae = ShopTool.reloadInvShoppingCart(shoppinServEjb, session, null, appUser.getSite(), appUser.getUser(), storeType);

        return ae;
    }

//  ****************************************************************************
    public static ActionErrors freightTypeChanged(HttpServletRequest request, CheckoutForm pForm) {
      ActionErrors ae = new ActionErrors();
      HttpSession session = request.getSession();
      APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
      if (factory == null) {
        ae.add("error", new ActionError("error.systemError", "No Ejb access"));
        return ae;
      }
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      if (appUser == null) {
        ae.add("error", new ActionError("error.systemError", "No " + Constants.APP_USER + " session object found"));
        return ae;
      }

      ShoppingCartData shoppingCartD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
      if (shoppingCartD == null) {
        ae.add("error", new ActionError("error.systemError", "No " + Constants.SHOPPING_CART + " session object found"));
        return ae;
      }
      List cartItems = shoppingCartD.getItems();
      ae = checkoutWorkflow(request, pForm, ae, factory, appUser, cartItems);
      return ae;
    }

    public static ArrayList getDeliveryDates(HttpServletRequest request, SiteDeliveryDataVector dateCollection) {
      ArrayList dates=new ArrayList();

       // Set the search type array
      if (dateCollection != null && dateCollection.size() != 0 ) {
        Iterator itr = dateCollection.iterator();
         while (itr.hasNext()) {
           SiteDeliveryData dateItem = (SiteDeliveryData) itr.next();
           if (dateItem != null){
        	   Date date = dateItem.getDeliveryDate();
        	   String deliveryDateS = ClwI18nUtil.formatDate(request,date,DateFormat.FULL);
        	   dates.add(new FormArrayElement(deliveryDateS, deliveryDateS));
           }
         }
      }
      return dates;
    }
    
    public static String getDeliveryDateList(HttpServletRequest request, SiteDeliveryDataVector dateCollection){
    	StringBuilder deliveryDatesString = new StringBuilder(50);
    	String pattern = ClwI18nUtil.getDatePattern(request);
    	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    	
    	if (Utility.isSet(dateCollection)) {
    		boolean includeSeperator = false;
    		
    		Iterator itr = dateCollection.iterator();
    		while (itr.hasNext()) {
    			if (includeSeperator) {
    				deliveryDatesString.append(Constants.MULTIPLE_DATE_SEPARATOR);
    			}
    			
    			SiteDeliveryData dateItem = (SiteDeliveryData) itr.next();
    			if (dateItem != null){
    				Date date = dateItem.getDeliveryDate();
    				deliveryDatesString.append(sdf.format(date));
    				includeSeperator = true;
    			}
    		}
    	}
       return deliveryDatesString.toString();
    }
    
    // validate selected Delivery Date
    public static Date isValidDeliveryDate(HttpServletRequest request, CheckoutForm pForm) {
      Date expiredCutOff = null;
      String selectedDateS = pForm.getDeliveryDate();
      
      //Date needs to be formatted if using new ui
      String pattern = ClwI18nUtil.getDatePattern(request);
      log.info("CheckoutLogic isValidDeliveryDate SimpleDateFormat pattern="+pattern+" selectedDateS="+selectedDateS);
  	  SimpleDateFormat sdf = new SimpleDateFormat(pattern);
  	  String formattedDeliveryDateS = "";
  	  try{
  		  Date formattedDeliveryDate = null; 
  		  if (selectedDateS != null) {
  		    formattedDeliveryDate = sdf.parse(selectedDateS);
  		    formattedDeliveryDateS = ClwI18nUtil.formatDate(request, formattedDeliveryDate, DateFormat.FULL);
  		  } else {
  			formattedDeliveryDate = null;  
  		  }
  	  }catch(Exception exc){
  		  exc.printStackTrace();
  	  }
      SiteDeliveryDataVector dateCollection = pForm.getDeliveryDataVector();
      Date currentSystemCutOff = null;
      Date currentSiteCutOff = null;
      Date currentTime = new Date(System.currentTimeMillis());
      if (dateCollection != null && dateCollection.size() != 0 ) {
        Iterator itr = dateCollection.iterator();
        while (itr.hasNext()) {
          SiteDeliveryData dateItem = (SiteDeliveryData) itr.next();
          if (dateItem != null) {
        	  Date date = dateItem.getDeliveryDate();
            String deliveryDateS = ClwI18nUtil.formatDate(request, date, DateFormat.FULL);

            if (selectedDateS.equals(deliveryDateS) || formattedDeliveryDateS.equals(deliveryDateS)){
              currentSystemCutOff = dateItem.getCutoffSystemTime();
              currentSiteCutOff = dateItem.getCutoffSiteTime();
              break;
            }
          }
        }

        if ( currentSystemCutOff.before(currentTime) ){
          expiredCutOff = currentSiteCutOff;
        }
      }
      return expiredCutOff;

    }

    private static void calculateFreightTablesForDistributors(FreightTable freightTableEjb,
        int storeId, int catalogId, TreeMap<Integer, FreightTableData> distFreightTables) throws RemoteException {
        if (distFreightTables == null) {
            return;
        }
        if (distFreightTables.isEmpty()) {
            return;
        }
        Set<Integer> keys = distFreightTables.keySet();
        Iterator iterator = keys.iterator();
        while (iterator.hasNext()) {
            Integer key = (Integer)iterator.next();
            FreightTableData freightTable = freightTableEjb.getFreightTableByDistributorAndCatalog(storeId, catalogId, key.intValue());
            distFreightTables.put(key, freightTable);
        }
    }

    private static void calculateDiscountTablesForDistributors(FreightTable freightTableEjb,
        int storeId, int catalogId, TreeMap<Integer, FreightTableData> distDiscountTables) throws Exception {
        if (distDiscountTables == null) {
            return;
        }
        if (distDiscountTables.isEmpty()) {
            return;
        }
        Set<Integer> keys = distDiscountTables.keySet();
        Iterator iterator = keys.iterator();
        while (iterator.hasNext()) {
            Integer key = (Integer)iterator.next();
            FreightTableData freightTable = freightTableEjb.getDiscountTableByDistributorAndCatalog(storeId, catalogId, key.intValue());
            distDiscountTables.put(key, freightTable);
        }
    }

    private static OrderAddOnChargeDataVector getFuelSurchargeAndSmallOrderFeeForOrder(
        ShoppingCartDistDataVector cartDistributors) {
        if (cartDistributors == null) {
            return null;
        }
        if (cartDistributors.size() == 0) {
            return null;
        }
        OrderAddOnChargeDataVector chargesList = new OrderAddOnChargeDataVector();
        for (int i = 0; i < cartDistributors.size(); i++) {
            ShoppingCartDistData cartDistData = (ShoppingCartDistData)cartDistributors.get(i);
            if (cartDistData == null || cartDistData.getDistributor() == null) {
                continue;
            }
            int distributorId = cartDistData.getDistributor().getBusEntityId();
            ///
            List fuelSurchargeList = cartDistData.getDistFuelSurchargeList();
            if (fuelSurchargeList != null && fuelSurchargeList.size() > 0) {
                for (int k = 0; k < fuelSurchargeList.size(); k++) {
                    FreightTableCriteriaData crit = (FreightTableCriteriaData)fuelSurchargeList.get(k);
                    OrderAddOnChargeData chargeData = OrderAddOnChargeData.createValue();

                    chargeData.setBusEntityId(distributorId);
                    chargeData.setDistFeeTypeCd(crit.getFreightCriteriaTypeCd());
                    chargeData.setDistFeeChargeCd(crit.getChargeCd());
                    chargeData.setAmount(crit.getHandlingAmount());
                    chargeData.setShortDesc(crit.getShortDesc());

                    chargesList.add(chargeData);
                }
            }
            ///
            List smallOrderFeeList = cartDistData.getDistSmallOrderFeeList();
            if (smallOrderFeeList != null && smallOrderFeeList.size() > 0) {
                for (int k = 0; k < smallOrderFeeList.size(); k++) {
                    FreightTableCriteriaData crit = (FreightTableCriteriaData)smallOrderFeeList.get(k);
                    OrderAddOnChargeData chargeData = OrderAddOnChargeData.createValue();

                    chargeData.setBusEntityId(distributorId);
                    chargeData.setDistFeeTypeCd(crit.getFreightCriteriaTypeCd());
                    chargeData.setDistFeeChargeCd(crit.getChargeCd());
                    chargeData.setAmount(crit.getHandlingAmount());
                    chargeData.setShortDesc(crit.getShortDesc());

                    chargesList.add(chargeData);
                }
            }
        }
        return chargesList;
    }

    public static ActionErrors validateCheckoutFields (HttpServletRequest request, CheckoutForm pForm) {
    	ActionErrors ae = new ActionErrors();
    	List<BusEntityFieldDataElement> checkoutFields = pForm.getCheckoutFieldProperties();
    	for (BusEntityFieldDataElement checkoutField : checkoutFields){
    		if (Utility.isSet(checkoutField.getTag())){
    			if (checkoutField.isRequired() && !Utility.isSet(checkoutField.getValue())){
    				ae.add("error", new ActionError("variable.required.error",
    						checkoutField.getTag()));
    			}
    		}
    	}
  	    return ae;
    }

    public static List<BusEntityFieldDataElement> getCheckoutFieldProperties(APIAccess factory, int accountId) throws RemoteException{
    	try{
	        BusEntityFieldsData bfd =
	                factory.getPropertyServiceAPI().fetchCheckoutFieldsData(
	                accountId);

	        PropertyDataVector fieldPropertiesUnordered = new PropertyDataVector();
	        List<BusEntityFieldDataElement> checkoutFieldProperties = BusEntityFieldDataElement.createBusEntityFieldDataElementCol(
	                bfd, fieldPropertiesUnordered, RefCodeNames.PROPERTY_TYPE_CD.CHECKOUT_FIELD_CD, 0);
	        return checkoutFieldProperties;
        }catch(Exception e){
        	e.printStackTrace();
            throw new RemoteException("CheckoutLogic.getCheckoutFieldProperties: " + e.getMessage());
        }
    }

    private static void setRebillOrderPropertyForOrderRequest(CustomerOrderRequestData orderRequestData, CheckoutForm form) {
        if (orderRequestData == null || form == null) {
            return;
        }
        if (orderRequestData.getProperties() == null) {
            return;
        }
        if (form.getRebillOrder() == null) {
            return;
        }
        String rebillOrder = form.getRebillOrder().trim();
        if (rebillOrder.length() == 0) {
            return;
        }

        if (rebillOrder.equalsIgnoreCase("true") ||
            rebillOrder.equalsIgnoreCase("yes") ||
            rebillOrder.equalsIgnoreCase("on")) {
        	// if bypassbudget and rebillOrder both checked set orderBudgetTypeCd=NON_APPLICABLE 
        	// so that order still go through workflow rule for approval
        	// if only rebillOrder checked set orderBudgetTypeCd=REBILL, order will not be held by workflow rule for approval
 
        	log.info("setRebillOrderPropertyForOrderRequest()***************SVC: form.isBypassBudget() = " + form.isBypassBudget());
        	if (!form.isBypassBudget()){	
        		orderRequestData.setOrderBudgetTypeCd(RefCodeNames.ORDER_BUDGET_TYPE_CD.REBILL);
        	}
            PropertyData pd = PropertyData.createValue();
            pd.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
            pd.setPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.REBILL_ORDER);
            pd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.REBILL_ORDER);
            pd.setValue("true");
            orderRequestData.getProperties().add(pd);
        }
    }
    
  //****************************************************************************
    public static ActionErrors endShopping(HttpServletRequest request,
                                          CheckoutForm pForm) throws Exception {
    	ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        pForm.setItems(new ShoppingCartItemDataVector()); // empty the cart
        doInlineOrderInit(ae, pForm, session, factory, appUser);
        session.setAttribute("CheckoutForm", pForm);
        return ae;
    }

}

