/*
 * CancelReplaceItemDuplication.java
 *
 * Created on August 25, 2003
 */

package com.cleanwise.service.api.pipeline;

import java.sql.Connection;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.util.I18nUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.ItemSkuMapping;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.dao.ItemSubstitutionDefDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.dao.TradingProfileConfigDataAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import java.text.SimpleDateFormat;


import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Collection;
import java.util.Calendar;
import java.rmi.RemoteException;

import org.apache.log4j.Category;

/**
 * Pipeline class that does intial ui order request parsing.
 * @author  YKupershmidt
 */
public class EdiOrderRequestParsing implements OrderPipeline {
  private static final Category log = Category.getInstance(EdiOrderRequestParsing.class);

  private boolean isPunchOutTypeApprovalAccount(AccountData acd) {
    if (RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_NON_ELEC_ORD.equals(acd.getCustomerSystemApprovalCd()) ||
        RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_NON_ELEC_ORD_ONLY.equals(acd.getCustomerSystemApprovalCd())) {
      return true;
    }
    return false;
  }

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
      //OrderRequestData orderReq = pBaton.getOrderRequestData();
      PreOrderData preOrderD = pBaton.getPreOrderData();
      pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
      String orderSourceCd = preOrderD.getOrderSourceCd();
      if (!RefCodeNames.ORDER_SOURCE_CD.EDI_850.equals(orderSourceCd)) {
        return pBaton;
      }

      PreOrderPropertyDataVector preOrderPropertyDV =
        pBaton.getPreOrderPropertyDataVector();

      pBaton.setCurrentDate(new Date());
      OrderData orderD = pBaton.getOrderData();
      String userName = "Edi order pipeline";
      
      if (Utility.isSet(preOrderD.getUserNameKey())) { 	  
    		  userName = preOrderD.getUserNameKey();
      }
      
      pBaton.setUserName(userName);
      orderD.setAddBy(userName);
      pBaton.setOrderData(orderD);
      orderD.setModBy(userName);
      //Initialize currency.  Logic happens later
      orderD.setLocaleCd(RefCodeNames.LOCALE_CD.EN_US);
      orderD.setCurrencyCd(RefCodeNames.CURRENCY_CD.USD);
      //
      String sourceCd = preOrderD.getOrderSourceCd();
      orderD.setOrderSourceCd(sourceCd);

      //get account id for edi orders
      AccountData accountD = null;

      Account actEjb = pFactory.getAccountAPI();
      String tempSiteName = preOrderD.getSiteName();
      int accountId = preOrderD.getAccountId();
      if (accountId <= 0) {
        try {

          PreOrderPropertyData punchOutProp = Utility.getPreOrderProperty(preOrderPropertyDV,
            RefCodeNames.ORDER_PROPERTY_TYPE_CD.PUNCH_OUT_ORDER_ORIG_ORDER_NUM);
          boolean isPunchOutAproval = false;
          if (punchOutProp != null && Utility.isSet(punchOutProp.getValue())) {
            log.debug("Punch out order: " + punchOutProp.getValue());
            isPunchOutAproval = true;
            //find the account for this original order
            DBCriteria orderCrit = new DBCriteria();
            orderCrit.addEqualTo(OrderDataAccess.ORDER_NUM, punchOutProp.getValue().trim());
            OrderDataVector odv = OrderDataAccess.select(pCon, orderCrit);
            if (odv.size() == 1) {
              accountId = ((OrderData) odv.get(0)).getAccountId();
            }
          }

          //find account based off user key
          if (accountId <= 0) {
            if (Utility.isSet(preOrderD.getUserNameKey())) {
              DBCriteria userCrit = new DBCriteria();
              userCrit.addEqualTo(UserDataAccess.USER_NAME, preOrderD.getUserNameKey());
              userCrit.addEqualTo(UserDataAccess.USER_STATUS_CD, RefCodeNames.USER_STATUS_CD.ACTIVE);
              IdVector uidv = UserDataAccess.selectIdOnly(pCon, userCrit);
              if (uidv.size() == 1) {
                Integer uId = (Integer) uidv.get(0);
                userCrit = new DBCriteria();
                userCrit.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.ACCOUNT);
                userCrit.addEqualTo(UserAssocDataAccess.USER_ID, uId.intValue());
                IdVector acctIds = UserAssocDataAccess.selectIdOnly(pCon, UserAssocDataAccess.BUS_ENTITY_ID, userCrit);
                if (acctIds.size() == 1) {
                  accountId = ((Integer) acctIds.get(0)).intValue();
                }
              }
            }
          }

          if (accountId <= 0) {
        	  log.debug("Didn't find order to match");
            AccountDataVector acdv = actEjb.getAccountsByTrParnterIdAndSiteName(preOrderD.getTradingPartnerId(),
              tempSiteName);
            //Iterator<AccountData> it = acdv.iterator();
            //if there are 2 then set one or the other based off approval type
            log.debug("acdv.size()::" + acdv.size());
            if (acdv.size() == 1) {
              accountD = (AccountData) acdv.get(0);
              accountId = accountD.getBusEntity().getBusEntityId();
            }
            if (acdv.size() == 2) {
              AccountData acd = (AccountData) acdv.get(0);
              //get the 2 accounts, one should be set to punch out approval style (not inline)
              //and the other should be unset or set to NONE
              AccountData nonApprovalAccount = null;
              AccountData approvalReqAccount = null;
              if (isPunchOutTypeApprovalAccount(acd)) {
                approvalReqAccount = acd;
              } else {
                nonApprovalAccount = acd;
              }
              acd = (AccountData) acdv.get(1);
              if (isPunchOutTypeApprovalAccount(acd)) {
                approvalReqAccount = acd;
              } else {
                nonApprovalAccount = acd;
              }

              if (approvalReqAccount == null) {
                String messKey = "pipeline.message.approvalReqAccount";
                pBaton.addError(pCon, OrderPipelineBaton.ACCOUNT_FIND_PROBLEM,
                                RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                                messKey);
              }
              if (nonApprovalAccount == null) {
                String messKey = "pipeline.message.nonApprovalAccount";
                pBaton.addError(pCon, OrderPipelineBaton.ACCOUNT_FIND_PROBLEM,
                                RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                                messKey);
              }
              //set the account id
              if (nonApprovalAccount != null && approvalReqAccount != null) {
                if (isPunchOutAproval) {
                  accountId = approvalReqAccount.getBusEntity().getBusEntityId();
                  accountD = approvalReqAccount;
                } else {
                  accountId = nonApprovalAccount.getBusEntity().getBusEntityId();
                  accountD = nonApprovalAccount;
                }
              }

            }
          }
          preOrderD.setAccountId(accountId);
        } catch (Exception exc) {
          String messKey = "pipeline.message.noAccountFound";
          String mess = "Can't find account";
          if (!(exc instanceof DataNotFoundException)) {
            exc.printStackTrace();
            mess += "-Caught unexpected exception: " + exc.getMessage();
          } else {
            mess += "-" + exc.getMessage();
          }

          pBaton.addError(pCon, OrderPipelineBaton.ACCOUNT_FIND_PROBLEM,
                          RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                          messKey);

        }
      }

      orderD.setAccountId(accountId);
      String s = preOrderD.getCustomerPoNumber();
      orderD.setRequestPoNum(s);
      orderD.setRefOrderNum(preOrderD.getOrderRefNumber());

      // Try to set up the store, account, and site information
      if (accountD == null) {
        accountD = getAccountData(accountId, pFactory);
      }
      //String  acctLocale = "null";
      int storeId = 0;
      if (accountD == null) {
        accountD = AccountData.createValue();
        String messKey = "pipeline.message.noAccountFound";
        pBaton.addError(pCon, OrderPipelineBaton.NO_ACCOUNT_FOUND,
                        RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                        messKey);
        //try to pick up store using tranding partner info
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ID,
                       preOrderD.getTradingPartnerId());
        dbc.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_CD,
                       RefCodeNames.TRADING_PARTNER_ASSOC_CD.ACCOUNT);
        String assocReq = TradingPartnerAssocDataAccess.getSqlSelectIdOnly(
          TradingPartnerAssocDataAccess.BUS_ENTITY_ID, dbc);
        dbc = new DBCriteria();
        dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, assocReq);
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                       RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
        IdVector storeIdV = BusEntityAssocDataAccess.selectIdOnly(pCon,
          BusEntityAssocDataAccess.BUS_ENTITY2_ID, dbc);
        storeId = 0;
        for (int ii = 0; ii < storeIdV.size(); ii++) {
          Integer storeIdI = (Integer) storeIdV.get(ii);
          if (storeId == 0) {
            storeId = storeIdI.intValue();
          } else if (storeId != storeIdI.intValue()) {
            storeId = 0;
            break;
          }
        }
        if (storeId > 0) {
          orderD.setStoreId(storeId);
        } else {
          messKey = "pipeline.message.noStoreFound";
          pBaton.addError(pCon, OrderPipelineBaton.NO_STORE_FOUND,
                          RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                          messKey);
        }

      } else {
        BusEntityAssocData storeAssocD = accountD.getStoreAssoc();
        if (storeAssocD != null) {
          storeId = storeAssocD.getBusEntity2Id();
          orderD.setStoreId(storeId);
          if (storeId <= 0) {
            String messKey = "pipeline.message.noStoreFound";
            pBaton.addError(pCon, OrderPipelineBaton.NO_STORE_FOUND,
                            RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                            messKey);
          }
        }
        BusEntityData accountBusEntityD = accountD.getBusEntity();
        //acctLocale = accountBusEntityD.getLocaleCd();
        String acctErpNum = accountBusEntityD.getErpNum();
        orderD.setAccountErpNum(acctErpNum);
      }

      if(storeId>0 & !Utility.isSet(orderD.getOrderNum())){
    	  IntegrationServices intServEjb = pFactory.getIntegrationServicesAPI();
    	  int orderNum = intServEjb.getNextOrderNumber(pCon, storeId);
    	  orderD.setOrderNum(""+orderNum);
    	  pBaton.setOrderData(orderD);
      }
      
      if (!RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE.equals(accountD.getBusEntity().getBusEntityStatusCd())) {
        String messKey = "pipeline.message.accountNotActive";
        pBaton.addError(pCon, OrderPipelineBaton.ACCOUNT_NOT_ACTIVE,
                        RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW, 0, 0,
                        messKey);
      }


      boolean historicalOrder = pBaton.isHistoricalOrderPreOrderProps();
      boolean batchOrder = RefCodeNames.ORDER_TYPE_CD.BATCH_ORDER.equals(preOrderD.getOrderTypeCd());
      log.info("historicalOrder="+historicalOrder);



      //if(!Utility.isSet(acctLocale) || acctLocale.equals("unk")) {
      //   acctLocale = java.util.Locale.US.toString();
      //}
      //orderD.setLocaleCd(acctLocale);
      //Site
      String logShippingDate = null;
      //Trading partner info
      TradingPartnerInfo tpInfo = null;
      int partnerId = preOrderD.getTradingPartnerId();
      if(!historicalOrder && !batchOrder){
	      if (partnerId <= 0) partnerId = getPartnerId(pBaton, pCon);
	      if (partnerId > 0) {
	        TradingPartner tradingPartnerEjb = pFactory.getTradingPartnerAPI();
	        tpInfo = tradingPartnerEjb.getTradingPartnerInfo(partnerId);
	      }
      }
      int siteId = preOrderD.getSiteId();
      SiteData siteD = null;
      if (siteId <= 0) {
        siteId = 0;
        siteD = getSiteData(tempSiteName, accountId, pFactory);
        if (siteD != null) siteId = siteD.getBusEntity().getBusEntityId();
      } else {
        siteD = getSiteData(siteId, pFactory);
      }
      if (siteD == null) {
        log.debug("EdiOrderRequestParsing RRRRRRRRRRRRRRRRRRR shipping address: " + pBaton.getCustShipToData());
        if (!Utility.isSet(tempSiteName)) {
          log.debug("SiteData being searched from address");
          for (Iterator<PreOrderAddressData> iter = pBaton.getPreOrderAddressDataVector().iterator(); iter.hasNext(); ) {
            PreOrderAddressData poaD = iter.next();
            String addrType = poaD.getAddressTypeCd();
            if (RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_SHIPPING.equals(addrType)) {
              siteD = getSiteData(poaD, accountId, pFactory);
              if (siteD != null) {
                orderD.setOrderSiteName(siteD.getBusEntity().getShortDesc());
                siteId = siteD.getBusEntity().getBusEntityId();
              }
            }
          }
        }
        log.debug("SiteData::" + siteD);
      }
      //shipto
      String contactName = preOrderD.getOrderContactName();
      orderD.setSiteId(siteId);
      if (siteD == null) {
        //Set site data
        orderD.setOrderSiteName(preOrderD.getSiteName());
        String messKey = "pipeline.message.noSiteFound";
        pBaton.addError(pCon, OrderPipelineBaton.NO_SITE_FOUND,
                        RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                        messKey);
      } else {

        // check if customer shipping address from edi
        // match site address in our system

        saveOrderShipToAddress(pBaton, siteD);
        saveOrderCustomerAddresses(pBaton);

        if (!historicalOrder && !batchOrder && !shipAddressAndCustomerShipAddressMatch(pBaton)) {
          String messStatus = null;
          String messKey = null;
          boolean foundFl = false;
          boolean freeFormatAddress =
            ("T".equals(preOrderD.getFreeFormatAddress())) ? true : false;
          if (freeFormatAddress) {
            messKey = "pipeline.message.freeFormatCustomerShippingAddress";
            messStatus = RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW;
          } else {
            log.debug("SiteData being searched from address");
            SiteData siteData = searchSiteDataOnlyForUSPS(preOrderD.getSiteName(), accountId, pBaton.getCustShipToData(),
              pFactory);
            if (siteData != null) {
              siteD = siteData;
              siteId = siteD.getBusEntity().getBusEntityId();
              saveOrderShipToAddress(pBaton, siteD);
              foundFl = true; ;
            } else {
              messKey = "pipeline.message.shippingAddressNotMatchedSiteAddress";
              messStatus = RefCodeNames.ORDER_STATUS_CD.ORDERED;
            }
          }
          if (!historicalOrder && !batchOrder && !foundFl) {
            if (siteId > 0 && (tpInfo == null || tpInfo.isCheckAddress())) {
              pBaton.addError(pCon, OrderPipelineBaton.REQ_SHIP_TO_ADDRESS_PROBLEM,
                              RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                              messKey);
            } else {
              String shortDesc = orderD.getOrderSourceCd() + " Order Note";
              pBaton.addError(pCon, OrderPipelineBaton.REQ_SHIP_TO_ADDRESS_PROBLEM,
                              shortDesc, messStatus, 0, 0,
                              messKey);
            }
          }
        }
        if (siteD != null) {

          orderD.setSiteId(siteId);
          orderD.setOrderSiteName(siteD.getBusEntity().getShortDesc());
          orderD.setSiteErpNum(siteD.getBusEntity().getErpNum());
          if (!Utility.isSet(contactName)) {
        	  contactName = siteD.getSiteAddress().getName1();
        	  if (siteD.getSiteAddress().getName2() != null)
        		  contactName += " " + siteD.getSiteAddress().getName2();
          }

          ContractData contractD = siteD.getContractData();
          if (contractD != null){
	          String localeCd = contractD.getLocaleCd();
	          orderD.setLocaleCd(localeCd);
	          CurrencyData currD = I18nUtil.getCurrency(localeCd);
	          if (currD != null) {
	            orderD.setCurrencyCd(currD.getGlobalCode());
	          }
          }else{
        	  log.info("Trying to set locale and currency based off alternative catalog");
	          int tradingProfileId = preOrderD.getIncomingProfileId();
	          
	          DBCriteria dbc = new DBCriteria();
	          dbc.addEqualTo(TradingPropertyMapDataAccess.TRADING_PROFILE_ID, tradingProfileId);
	          //if this is a historical order assume it came from an 855 not a regular 850
	          if(pBaton.isHistoricalOrderOrderProps() || pBaton.isHistoricalOrderPreOrderProps()){
	        	  dbc.addEqualTo(TradingPropertyMapDataAccess.SET_TYPE, RefCodeNames.EDI_TYPE_CD.T855);
	          }else{
	        	  dbc.addEqualTo(TradingPropertyMapDataAccess.SET_TYPE, RefCodeNames.EDI_TYPE_CD.T850);
	          }
	          dbc.addEqualTo(TradingPropertyMapDataAccess.DIRECTION, "IN");
	          dbc.addEqualTo(TradingPropertyMapDataAccess.PROPERTY_TYPE_CD,"ALTERNATIVE_CATALOG_ID");
	          TradingPropertyMapDataVector tpmdv = TradingPropertyMapDataAccess.select(pCon, dbc);
	          Integer altCatId = null;
	          if(tpmdv != null && tpmdv.size() > 0){
	        	  String altCatIdStr = ((TradingPropertyMapData)tpmdv.get(0)).getHardValue();
	        	try{
	        		
	        		if(Utility.isSet(altCatIdStr)){
	        			log.info("Found alt catalog id set for trading profile.  Catalog id is: "+altCatIdStr);
	        			altCatId = Integer.parseInt(altCatIdStr.trim());
	        		}
	        		
	        	}catch(NumberFormatException e){
	        		log.error("Could not parse alternative catalog id as a number: "+altCatIdStr+" continuing but currency will default to USD and locale will default to en_US");
	        	}
	          }
	          if(altCatId != null){
	        	  DBCriteria dbc2 = new DBCriteria();
	        	  dbc2.addEqualTo(ContractDataAccess.CATALOG_ID, altCatId);
	        	  dbc2.addEqualTo(ContractDataAccess.CONTRACT_STATUS_CD, RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
	        	  ContractDataVector cdv = ContractDataAccess.select(pCon, dbc2);
	        	  if(cdv != null && cdv.size() > 0){
	        		  ContractData altContractD = (ContractData) cdv.get(0);
	        		  String localeCd = altContractD.getLocaleCd();
	        		  log.info("Setting currency and locale based off code: "+localeCd);
	    	          orderD.setLocaleCd(localeCd);
	    	          CurrencyData currD = I18nUtil.getCurrency(localeCd);
	    	          if (currD != null) {
	    	            orderD.setCurrencyCd(currD.getGlobalCode());
	    	          }
	        	  }
	        	  
	          }
	          
	          //CurrencyData currD = I18nUtil.getCurrency(localeCd);
	          //if (currD != null) {
	          //  orderD.setCurrencyCd(currD.getGlobalCode());
	          //}
          }
          

          // check if same order has been pulled into the system before
          if (!pBaton.isSkipDuplicatedOrderValidationProps() && duplicateOrder(pBaton, pCon, false)) {
            String messKey = "pipeline.message.duplicateOrderFound";
            pBaton.addError(pCon, OrderPipelineBaton.DUPLICATED_ORDER,
                            RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                            messKey);
          }
        }
      }

      //set contract name
      if (contactName != null && contactName.length() > 80)
    	  contactName = contactName.substring(0, 80);
      orderD.setOrderContactName(contactName);
      //set Erp system
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, storeId);
      dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,
                     RefCodeNames.PROPERTY_TYPE_CD.ERP_SYSTEM);
      dbc.addOrderBy(PropertyDataAccess.PROPERTY_TYPE_CD); //To get always the same if there are a few
      PropertyDataVector propDV = PropertyDataAccess.select(pCon, dbc);
      if (propDV.size() != 0) {
        PropertyData pD = (PropertyData) propDV.get(0);
        orderD.setErpSystemCd(pD.getValue());
      }
      orderD.setOrderContactPhoneNum(preOrderD.getOrderTelephoneNumber());
      orderD.setOrderContactFaxNum(preOrderD.getOrderFaxNumber());
      orderD.setOrderContactEmail(preOrderD.getOrderEmail());

      // Initialize the freight charges to zero.
      orderD.setTotalFreightCost(new BigDecimal(0));
      orderD.setTotalMiscCost(new BigDecimal(0));
      //
      orderD.setRefOrderNum(preOrderD.getOrderRefNumber());
      orderD.setComments(preOrderD.getCustomerComments());
      //Contract
      if (siteId > 0 && !historicalOrder) {
        ContractDescData contractDD = getContract(siteId, pFactory);
        if (contractDD != null) {
          orderD.setContractId(contractDD.getContractId());
          orderD.setContractShortDesc(contractDD.getContractName());
        } else {
          String messKey = "pipeline.message.multipleContactFound";
          pBaton.addError(pCon, OrderPipelineBaton.SITE_CONTRACT_PROBLEM, null,
                          RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                          messKey,
                          ""+preOrderD.getAccountId(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
                          ""+preOrderD.getSiteId(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
                          preOrderD.getSiteName(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
        }
      }

      //Order type
      orderD.setOrderTypeCd(preOrderD.getOrderTypeCd());

     

      if(!pBaton.isHistoricalOrderPreOrderProps()){
	      // check to see if same item sku + uom occured on different line item
	      checkDuplicateLineItem(pBaton, pCon, pBaton.getPreOrderItemDataVector());	      
      }

      //Set order date and time
      orderD.setOriginalOrderDate(pBaton.getCurrentDate());
      orderD.setOriginalOrderTime(pBaton.getCurrentDate());

      // Other order information.
      // XXX - this should be at the account level.
      //orderD.setCurrencyCd("USD");

      // log customer requested ship date into
      Date reqShipDate = preOrderD.getOrderRequestedShipDate();

      if (reqShipDate != null) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(pBaton.getCurrentDate());
        //calendar.add(Calendar.HOUR, 48); // give two day to
        calendar.add(Calendar.HOUR, (7 * 24)); // give seven days to
        // process and deliver the goods

        if (preOrderD.getOrderRequestedShipDate().after(calendar.getTime()) && !historicalOrder) {
          String messKey = "pipeline.message.wrongShippindDate";
          logShippingDate = "Customer requested ship date is " +
                            preOrderD.getOrderRequestedShipDate().toString() +
                            ". Do not release the order to ERP before that date.";
          pBaton.addError(pCon, OrderPipelineBaton.SHIP_DATE_REQUST, null,
                          RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                          messKey,
                          preOrderD.getOrderRequestedShipDate().toString(),
                          RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING );
        }
      }

      //trading profile
      orderD.setIncomingTradingProfileId(preOrderD.getIncomingProfileId());

      //Order meta data
      saveOrderMetaData(pBaton);

      //billto
      saveOrderBillToAddress(pCon, pBaton, pFactory, accountD, siteD);

      //shipto
      saveOrderShipToAddress(pBaton, siteD);
      //save various order properties (billing order, any entered notes)
      boolean isBillingOrder = ("T".equals(preOrderD.getBillingOrder())) ? true : false;
      if (isBillingOrder) {
        OrderPropertyData opD = OrderPropertyData.createValue();
        opD.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORDER);
        opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORDER);
        opD.setValue("true");
        opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
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
        opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
        opD.setAddBy(orderD.getAddBy());
        opD.setModBy(orderD.getAddBy());
        pBaton.addOrderPropertyData(opD);
      }

      //Trading partner info
      /*
             int partnerId = preOrderD.getTradingPartnerId();
             if(partnerId<=0) partnerId = getPartnerId(pBaton,pCon);
             if(partnerId>0) {
        TradingPartner tradingPartnerEjb = pFactory.getTradingPartnerAPI();
        tpInfo = tradingPartnerEjb.getTradingPartnerInfo(partnerId);
             }
       */
      saveOrderCustomerAddresses(pBaton);
      // log customer order date into ORDER_PROPERTY table
      {
        OrderPropertyData opD = OrderPropertyData.createValue();
        opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_ORDER_DATE);
        opD.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_ORDER_DATE);
        opD.setValue(preOrderD.getCustomerOrderDate());
        opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
        opD.setAddBy(userName);
        opD.setModBy(userName);
        pBaton.addOrderPropertyData(opD);
      }

      //log billing unit into ORDER_PROPERTY table
      if (Utility.isSet(preOrderD.getCustomerBillingUnit())) {
        OrderPropertyData opD = OrderPropertyData.createValue();
        opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_BILLING_UNIT);
        opD.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_BILLING_UNIT);
        opD.setValue(preOrderD.getCustomerBillingUnit());
        opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
        opD.setAddBy(userName);
        opD.setModBy(userName);
        pBaton.addOrderPropertyData(opD);
      }

      //log requested ship date into ORDER_PROPERTY table
      if (logShippingDate != null) {
        OrderPropertyData opD = OrderPropertyData.createValue();
        opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.REQUESTED_SHIP_DATE);
        opD.setShortDesc(logShippingDate);
        opD.setValue(preOrderD.getOrderRequestedShipDate().toString());
        opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
        opD.setAddBy(userName);
        opD.setModBy(userName);
        pBaton.addOrderPropertyData(opD);
      }

      // Add any order properties
      for (Iterator<PreOrderPropertyData> iter = preOrderPropertyDV.iterator(); iter.hasNext(); ) {
        PreOrderPropertyData popD = iter.next();
        if (Utility.isSet(popD.getValue())) {
          OrderPropertyData opD = OrderPropertyData.createValue();
          opD.setShortDesc(popD.getShortDesc());
          opD.setOrderPropertyTypeCd(popD.getOrderPropertyTypeCd());
          opD.setValue(popD.getValue());
          opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
          opD.setAddBy(orderD.getAddBy());
          opD.setModBy(orderD.getAddBy());
          pBaton.addOrderPropertyData(opD);
        }
      }

      //Intit price, cost, tax etc.
      BigDecimal bigzero = new BigDecimal(0);
      orderD.setTotalPrice(bigzero);
      orderD.setOriginalAmount(bigzero);
      orderD.setTotalTaxCost(bigzero);
      orderD.setTotalCleanwiseCost(bigzero);
      orderD.setTotalFreightCost(bigzero);
      orderD.setTotalMiscCost(bigzero);

      //Status
      String status = pBaton.getOrderStatus();
      orderD.setOrderStatusCd(status);

      //Return
      pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);

      return pBaton;
    } catch (Exception e) {
    	log.error("Unexpected Exception",e);
      throw new PipelineException(e.getMessage());
    } finally {
    }
  }

  //---------------------------------------------------------------------------
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
  private SiteData getSiteData(String siteName, int accountId, APIAccess pFactory) throws Exception {
    if (!Utility.isSet(siteName)) {
      return null;
    }
    Site siteEjb = pFactory.getSiteAPI();
    if (siteName.indexOf(' ') == -1) {
      siteName += " "; // Find this site, asume the name contains
      // the sitename then a space and the
      // account name.
    }
    siteName = siteName.trim();
    SiteViewVector sdv = siteEjb.getSiteByNameDeprecated(siteName, accountId,0,false,
                                               Site.BEGINS_WITH,
                                               Site.ORDER_BY_ID);


    if (sdv.size() > 1) {
      log.error("getSiteData, error, multiple sites " +
                         "found for site name: " + siteName +
                         " account id: " + accountId);

      return null;
    } else if (sdv.size() == 0) {
    	log.error("getSiteData, error, no site " +
                         "found for site name: " + siteName +
                         " account id: " + accountId);

      return null;
    }

    SiteView sv = (SiteView) sdv.get(0);
    return (SiteData) siteEjb.getSite(sv.getId());
  }

  //----------------------------------------------------------------------------
  private SiteData getSiteData(PreOrderAddressData siteAddress, int accountId, APIAccess pFactory) throws Exception {
    if (siteAddress == null) {
      return null;
    }
    Site siteEjb = pFactory.getSiteAPI();
    AddressData crit = Utility.toAddress(siteAddress);
    crit.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
    SiteDataVector sdv = siteEjb.getSitesByAddress(crit, accountId, Site.ORDER_BY_ID);

    if (sdv.size() > 1) {
      log.error("getSiteData, error, multiple sites " +
                         "found for site address: \n" + siteAddress +
                         "\n account id: " + accountId);

      return null;
    } else if (sdv.size() != 1) {
      log.error("getSiteData, error, no site " +
                         "found for site address: \n" + siteAddress +
                         "\n account id: " + accountId);

      return null;
    }
    return (SiteData) sdv.get(0);
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
  }

  private void saveOrderMetaData(OrderPipelineBaton pBaton) throws Exception {
    //OrderRequestData orderReq = pBaton.getOrderRequestData();
    PreOrderData preOrderD = pBaton.getPreOrderData();
    OrderData orderD = pBaton.getOrderData();
    String userName = orderD.getAddBy();
    PreOrderMetaDataVector preOrderMetaDV = pBaton.getPreOrderMetaDataVector();
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
    }
    /*
            String ccNum = orderReq.getCcNumber();
     if (Utility.isSet(ccNum)) {
       //CC number
       // zzz encrypt here
       OrderMetaData orderMetaD = OrderMetaData.createValue();
       orderMetaD.setAddBy(userName);
       orderMetaD.setName(RefCodeNames.CC_CD.NUMBER);
       orderMetaD.setValue(ccNum);
       orderMetaDV.add(orderMetaD);

       //Exp month
       int expMonth = orderReq.getCcExpMonth();
       orderMetaD = OrderMetaData.createValue();
       orderMetaD.setAddBy(userName);
       orderMetaD.setName(RefCodeNames.CC_CD.EXP_MONTH);
       orderMetaD.setValueNum(expMonth);
       orderMetaD.setValue(""+expMonth);
       orderMetaDV.add(orderMetaD);

       //Exp Year
       int expYear = orderReq.getCcExpYear();
       orderMetaD = OrderMetaData.createValue();
       orderMetaD.setAddBy(userName);
       orderMetaD.setName(RefCodeNames.CC_CD.EXP_YEAR);
       orderMetaD.setValueNum(expYear);
       orderMetaD.setValue(""+expYear);
       orderMetaDV.add(orderMetaD);

       //CC buyer
       String buyer = orderReq.getCcBuyerName();
       if (Utility.isSet(buyer)) {
         orderMetaD = OrderMetaData.createValue();
         orderMetaD.setAddBy(userName);
         orderMetaD.setName(RefCodeNames.CC_CD.BUYER_NAME);
         orderMetaD.setValue(buyer);
         orderMetaDV.add(orderMetaD);
       }
       //CC street1
       String street1 = orderReq.getCcStreet1();
       if (Utility.isSet(street1)) {
         orderMetaD = OrderMetaData.createValue();
         orderMetaD.setAddBy(userName);
         orderMetaD.setName(RefCodeNames.CC_CD.STREET1);
         orderMetaD.setValue(street1);
         orderMetaDV.add(orderMetaD);
       }
       //CC street2
       String street2 = orderReq.getCcStreet2();
       if(Utility.isSet(street2)) {
         orderMetaD = OrderMetaData.createValue();
         orderMetaD.setAddBy(userName);
         orderMetaD.setName(RefCodeNames.CC_CD.STREET2);
         orderMetaD.setValue(street2);
         orderMetaDV.add(orderMetaD);
       }
       //CC City
       String city = orderReq.getCcCity();
       if(Utility.isSet(city)) {
         orderMetaD = OrderMetaData.createValue();
         orderMetaD.setAddBy(userName);
         orderMetaD.setName(RefCodeNames.CC_CD.CITY);
         orderMetaD.setValue(city);
         orderMetaDV.add(orderMetaD);
       }
       //CC State
       String state = orderReq.getCcState();
       if(Utility.isSet(state)) {
         orderMetaD = OrderMetaData.createValue();
         orderMetaD.setAddBy(userName);
         orderMetaD.setName(RefCodeNames.CC_CD.STATE);
         orderMetaD.setValue(state);
         orderMetaDV.add(orderMetaD);
       }
       //CC Postal Code
       String postalCode = orderReq.getCcPostalCode();
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
    boolean bypassOrderRouting =
      ("T".equals(preOrderD.getBypassOrderRouting())) ? true : false;
    if (bypassOrderRouting) {
      // This order is not be routed even if it is a small order.
      OrderMetaData orderMetaD = OrderMetaData.createValue();
      orderMetaD.setAddBy(userName);
      orderMetaD.setValue("TRUE");
      orderMetaD.setName(Order.BYPASS_ORDER_ROUTING);
      orderMetaDV.add(orderMetaD);
    }
  }

  //------------------------------------------------------------------------------
  /*
      private void saveOrderPropertyData(OrderPipelineBaton pBaton)
      throws Exception
      {
         //OrderRequestData orderReq = pBaton.getOrderRequestData();
         OrderData orderD = pBaton.getOrderData();
         String userName = orderD.getAddBy();

         PropertyDataVector props = orderReq.getProperties();
         OrderPropertyDataVector orderPropertyDV = new OrderPropertyDataVector();
         pBaton.setOrderPropertyDataVector(orderPropertyDV);
         for (int ii = 0; null != props && ii < props.size(); ii++) {
           PropertyData oProp = (PropertyData) props.get(ii);
           OrderPropertyData opD = OrderPropertyData.createValue();
           opD.setOrderPropertyTypeCd(oProp.getPropertyTypeCd());
           opD.setShortDesc(oProp.getShortDesc());
           opD.setValue(oProp.getValue());
           opD.setOrderPropertyStatusCd(oProp.getPropertyStatusCd());
           opD.setAddBy(userName);
           orderPropertyDV.add(opD);
         }

       }
   */
  //--------------------------------------------------------------------------------------
  private void saveOrderBillToAddress(Connection pCon,
                                      OrderPipelineBaton pBaton,
                                      APIAccess pFactory,
                                      AccountData pAccountData,
                                      SiteData pSiteData) throws Exception {
    if (pAccountData == null) {
      return;
    }
    
    OrderAddressData billto = pBaton.getBillToData();
	if (billto==null) billto = OrderAddressData.createValue();
    pBaton.setBillToData(billto);
    billto.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.BILLING);

    PropertyService propertyServiceBean;
    try {
      propertyServiceBean = pFactory.getPropertyServiceAPI();
    } catch (Exception e) {
      String msg = "EJB problem. ";
      throw new Exception(msg + e.getMessage());
    }

    String makeShipToBillToS
      = propertyServiceBean.checkBusEntityProperty(pAccountData.getBusEntity().getBusEntityId(),
      RefCodeNames.PROPERTY_TYPE_CD.MAKE_SHIP_TO_BILL_TO);


    if (Utility.isSet(makeShipToBillToS) &&
        "T".equalsIgnoreCase(makeShipToBillToS.substring(0, 1))) { //Use site data
      if (pSiteData == null) {
        String messKey = "pipline.message.noBillTo";
        pBaton.addError(pCon, OrderPipelineBaton.NO_BILL_TO,
                        RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                        messKey);
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
    OrderAddressData shipto = pBaton.getShipToData();
	if(shipto==null) shipto = OrderAddressData.createValue();
    pBaton.setShipToData(shipto);
    shipto.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);

    if (null == pSiteData) {
      return;
    }

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

  //--------------------------------------------------------------------------
  private void saveOrderCustomerAddresses(OrderPipelineBaton pBaton) {
    //OrderRequestData orderReq = pBaton.getOrderRequestData();
    PreOrderAddressDataVector preOrderAddressDV =
      pBaton.getPreOrderAddressDataVector();

    log.debug("EdiOrderRequestParising PPPPPPPPPPPPPPPPPPPPPPPPP preOrderAddressDV: " + preOrderAddressDV);
	boolean custShipToExistsFl = (pBaton.getCustShipToData()!=null)? true:false;
	boolean custBillToExistsFl = (pBaton.getCustBillToData()!=null)? true:false;
    for (Iterator<PreOrderAddressData> iter = preOrderAddressDV.iterator(); iter.hasNext(); ) {
      PreOrderAddressData poaD = iter.next();
      String addrType = poaD.getAddressTypeCd();
      if (RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_SHIPPING.equals(addrType) && !custShipToExistsFl) {	    
        pBaton.setCustShipToData(createOrderAddressData(poaD));
      } else if (RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_BILLING.equals(addrType) && !custBillToExistsFl) {
        pBaton.setCustBillToData(createOrderAddressData(poaD));
      }
    }
  }

  /**
   *Converts a PreOrderAddressData object into an OrderAddressData object
   */
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

  private OrderAddressData createOrderAddressData(SiteData siteData) {

    AddressData source = siteData.getSiteAddress();
    OrderAddressData oaD = OrderAddressData.createValue();
    oaD.setAddressTypeCd(source.getAddressTypeCd());
    oaD.setShortDesc(siteData.getBusEntity().getShortDesc());
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

  //--------------------------------------------------------------------------
  private boolean shipAddressAndCustomerShipAddressMatch(OrderPipelineBaton pBaton) {
    OrderAddressData custShipto = pBaton.getCustShipToData();
    OrderAddressData shipto = pBaton.getShipToData();
    if (custShipto == null || shipto == null) {
      return false;
    }
    return shipAddressAndCustomerShipAddressMatch(custShipto, shipto);
  }

  //--------------------------------------------------------------------------
  private void checkDuplicateLineItem(OrderPipelineBaton pBaton, Connection pCon, PreOrderItemDataVector pPreOrderItems) {

	  String skuAndUom;

	  // list of sku number content in order. It is used to
	  // check duplicated item ordered in a order
	  HashMap<String, String> skuAndUoms = new HashMap<String, String>();

	  for (int i = 0; i < pPreOrderItems.size(); i++) {
		  PreOrderItemData poiD = (PreOrderItemData) pPreOrderItems.get(i);
		  skuAndUom = poiD.getCustomerSku();
		  if (Utility.isSet(poiD.getCustomerUom())){
			  skuAndUom +=":" + poiD.getCustomerUom();
			  if (skuAndUoms.containsKey(skuAndUom)) {
				  String messKey = "pipeline.message.duplicateLineItemForSkuAndUom";
				  pBaton.addError(pCon, OrderPipelineBaton.DUPLICATED_LINE_ITEM, null,
						  RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
						  messKey,
						  poiD.getCustomerSku(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
						  poiD.getCustomerUom(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
				  return;
			  } else {
				  skuAndUoms.put(skuAndUom, skuAndUom);
			  }
		  }else{
			  if (skuAndUoms.containsKey(skuAndUom)) {
				  String messKey = "pipeline.message.duplicateLineItemForSku";
				  pBaton.addError(pCon, OrderPipelineBaton.DUPLICATED_LINE_ITEM, null,
						  RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
						  messKey,
						  poiD.getCustomerSku(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
				  return;
			  } else {
				  skuAndUoms.put(skuAndUom, skuAndUom);
			  }
		  }
	  }
  }

  //--------------------------------------------------------------------------
  private int getPartnerId(OrderPipelineBaton pBaton, Connection pCon) throws Exception {
    DBCriteria dbc;
    OrderData orderD = pBaton.getOrderData();
    int accountId = orderD.getAccountId();
    if (accountId <= 0) {
      return 0;
    }
    dbc = new DBCriteria();
    dbc.addEqualTo(TradingPartnerAssocDataAccess.BUS_ENTITY_ID, accountId);
    dbc.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_CD,
                   RefCodeNames.TRADING_PARTNER_ASSOC_CD.ACCOUNT);

    String tpAssocReq = TradingPartnerAssocDataAccess.getSqlSelectIdOnly(
      TradingPartnerAssocDataAccess.TRADING_PARTNER_ID, dbc);

    dbc = new DBCriteria();
    dbc.addOneOf(TradingPartnerDataAccess.TRADING_PARTNER_ID, tpAssocReq);
    dbc.addEqualTo(TradingPartnerDataAccess.TRADING_PARTNER_STATUS_CD,
                   RefCodeNames.TRADING_PARTNER_STATUS_CD.ACTIVE);
    dbc.addEqualTo(TradingPartnerDataAccess.TRADING_PARTNER_TYPE_CD,
                   RefCodeNames.TRADING_PARTNER_TYPE_CD.CUSTOMER);

    log.debug("EdiOrderRequestParsing tp sql: " + TradingPartnerDataAccess.getSqlSelectIdOnly("*", dbc));
    IdVector partnerIdV = TradingPartnerDataAccess.selectIdOnly(pCon, dbc);


    if (partnerIdV.size() == 0) {
      String messKey = "pipeline.message.noTradingPartnerFound";
      pBaton.addError(pCon, OrderPipelineBaton.TRADING_PARTNER_ERROR,
                      RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                      messKey);
      return 0;
    }
    if (partnerIdV.size() > 1) {
      String messKey = "pipeline.message.multipleTradingPartners";
      pBaton.addError(pCon, OrderPipelineBaton.TRADING_PARTNER_ERROR,
                      RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                      messKey);
      return 0;
    }
    Integer partnerIdI = (Integer) partnerIdV.get(0);
    return partnerIdI.intValue();
  }

  //----------------------------------------------------------------------------
  private boolean duplicateOrder(OrderPipelineBaton pBaton, Connection pCon,
                                 boolean pReprocessingOrder) throws Exception {
    DBCriteria dbc;
    OrderData orderD = pBaton.getOrderData();
    PreOrderData preOrderD = pBaton.getPreOrderData();
    String custPoNum = orderD.getRequestPoNum();
    String refOrderNum = orderD.getRefOrderNum();

    if (!Utility.isSet(custPoNum) && !Utility.isSet(refOrderNum)) {
      return false;
    }
    String acctErpNum = orderD.getAccountErpNum();
    String siteName = orderD.getOrderSiteName();
    int acctId = preOrderD.getAccountId();
    
    dbc = new DBCriteria();

  //  	dbc.addEqualTo(OrderDataAccess.ACCOUNT_ERP_NUM, acctErpNum);
    dbc.addEqualTo(OrderDataAccess.ACCOUNT_ID, acctId);
    if (Utility.isSet(custPoNum))
    	dbc.addEqualTo(OrderDataAccess.REQUEST_PO_NUM, custPoNum);
    if (Utility.isSet(refOrderNum))
    	dbc.addEqualTo(OrderDataAccess.REF_ORDER_NUM, refOrderNum);
    dbc.addEqualTo(OrderDataAccess.ORDER_SITE_NAME, siteName);
    dbc.addNotEqualTo(OrderDataAccess.ORDER_ID, orderD.getOrderId()); //in case we are reprocessing

    log.info("Duplicate order sql " + OrderDataAccess.getSqlSelectIdOnly("*", dbc));
    OrderDataVector orderDV = OrderDataAccess.select(pCon, dbc);
    if (pReprocessingOrder) {
      return (orderDV.size() > 1);
    } else {
      return (orderDV.size() >= 1);
    }
  }

  //----------------------------------------------------------------------------
  private ContractDescData getContract(int pSiteId, APIAccess pFactory) throws Exception {
    ContractDescData contractDD = null;

    CatalogInformation catEjb = pFactory.getCatalogInformationAPI();

    Contract contractEjb = pFactory.getContractAPI();

    CatalogData catData = catEjb.getSiteCatalog(pSiteId);

    if (null != catData) {
      ContractDescDataVector contractDescDV = contractEjb.getContractDescsByCatalog(catData.getCatalogId());
      for (int ii = 0; ii < contractDescDV.size(); ii++) {
        ContractDescData cDD = (ContractDescData) contractDescDV.get(ii);
        if (RefCodeNames.CONTRACT_STATUS_CD.ACTIVE.equals(cDD.getStatus())) {
          if (contractDD == null) {
            contractDD = cDD;
          } else {
            return null;
          }
        }
      }
    }
    return contractDD;
  }

  public SiteData searchSiteDataOnlyForUSPS(String fedstrip, int pAccountId, OrderAddressData custShipto, APIAccess pFactory) throws
    Exception {
    SiteDataVector siteDV = null;
    try {
      siteDV = pFactory.getSiteAPI().getSiteByName(fedstrip, pAccountId, Site.CONTAINS, Site.ORDER_BY_NAME);
    } catch (APIServiceAccessException e) {
      String msg = "No Site Ejb Access. ";
      throw new Exception(msg + e.getMessage());

    }
    if (siteDV != null) {
      Iterator<SiteData> iter = siteDV.iterator();
      while (iter.hasNext()) {
        SiteData sD = (SiteData) iter.next();
        OrderAddressData siteAddressData = createOrderAddressData(sD);
        if (sD.getBusEntity().getShortDesc().startsWith("DS")
            && shipAddressAndCustomerShipAddressMatch(custShipto, siteAddressData)) {
          return sD;
        }
      }

    }
    return null;
  }


  /**
   * Determines if the shipto information matches.  It does this by verifying the city and the state match
   * and then finds the first street address in the data sent to us.  It compares this street address to the 
   * data we have for the shipto; if this address matches any of the fields for the shipto it is deemed valid.
   * @param custShipto the address from the customer system
   * @param shipto the address stored in the database
   * @return true if this address matches our system false if it does not
   */
  private boolean shipAddressAndCustomerShipAddressMatch(OrderAddressData custShipto, OrderAddressData shipto) {
	  //if city or state do not match exit immediately.
    if (!Utility.stringMatch(custShipto.getCity(), shipto.getCity(), 40) ||
        !Utility.stringMatch(custShipto.getStateProvinceCd(), shipto.getStateProvinceCd(), 2) ||
        !Utility.postalCodeMatch(custShipto.getPostalCode(), shipto.getPostalCode())){
    	log.info("Shipto not valid as city state postalcode failed check");
      return false;
    }
    //find an address from the customer shipto that is a street
    String custStreet = Utility.getStreetAddress(custShipto.getAddress1(), custShipto.getAddress2(), custShipto.getAddress3(), custShipto.getAddress4());
        
    //there is no street address to check so just accept this address
    if(!Utility.isSet(custStreet)){
    	log.info("Could not find shipping address in customer shipto; do not evaluate street logic.  Address is valid.");
    	return true;
    }
    
    //finally check if the street address we found matches anything in the 
    //address that we have for the site.
    if(Utility.stringMatch(custStreet, shipto.getAddress1(), 40) ||
		Utility.stringMatch(custStreet, shipto.getAddress2(), 40) ||
		Utility.stringMatch(custStreet, shipto.getAddress3(), 40) ||
		Utility.stringMatch(custStreet, shipto.getAddress4(), 40)){
    	log.info("Found matching street address");
    	return true;
    }
    log.info("Address does not match a street address");
    return false;
  }

}

