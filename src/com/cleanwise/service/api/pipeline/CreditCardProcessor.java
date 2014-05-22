/*
 * CreditCardProcessor.java
 *
 * Created on October 11, 2004, 2:31 PM
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.CreditCardUtil;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.OrderCreditCardDataAccess;
import com.cleanwise.service.crypto.*;
import java.security.MessageDigest;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;

/**
 * Preforms the encryption of the credit card
 * data before it is sent into the database and any psudo validation (mod 10 check etc)
 * @author  bstevens
 */
public class CreditCardProcessor implements OrderPipeline {

  public OrderPipelineBaton process(OrderPipelineBaton pBaton, OrderPipelineActor pActor, java.sql.Connection pCon,
                                    com.cleanwise.service.api.APIAccess pFactory) throws com.cleanwise.service.api.util.
    PipelineException {
    try {
      OrderRequestData orderReq = pBaton.getOrderRequestData();
      String ccNumber = orderReq.getCcNumber();
      if (!Utility.isSet(ccNumber)) {
        //not a credit card order
        return pBaton;
      }
      //create the credit card object
      String userName;
      if (!(orderReq instanceof CustomerOrderRequestData)) {
        userName = ((CustomerOrderRequestData) orderReq).getUserName();
      } else {
        userName = "System";
      }

      OrderCreditCardData cc = OrderCreditCardData.createValue();
      cc.setAddBy(userName);
      cc.setAddress1(orderReq.getCcStreet1());
      cc.setAddress2(orderReq.getCcStreet2());
      cc.setCity(orderReq.getCcCity());
      cc.setExpMonth(orderReq.getCcExpMonth());
      cc.setExpYear(orderReq.getCcExpYear());

      cc.setModBy(userName);
      cc.setName(orderReq.getCcBuyerName());
      cc.setOrderId(0);
      cc.setPostalCode(orderReq.getCcPostalCode());
      cc.setStateProvinceCd(orderReq.getCcState());
      cc.setCreditCardType(orderReq.getCcType());

      if(orderReq.getCcPaymetricTransactionId() == null || "".equals(orderReq.getCcPaymetricTransactionId())) {
          cc.setAuthAddressStatusCd(RefCodeNames.CREDIT_CARD_AUTH_ADDR_STATUS.PENDING);
          cc.setAuthStatusCd(RefCodeNames.CREDIT_CARD_AUTH_STATUS.PENDING);
          //deal with hashing and encrypting and setting associated data.
          CreditCardUtil ccU = new CreditCardUtil(ccNumber, cc, true);
          if (!ccU.isValid()) {
              pBaton.addError(pCon, pBaton.CREDIT_CARD_ERROR, null,
                              RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW, 0, 0,
                              "pipeline.message.creditCardError",
                              ccU.getValidationApiErrorMessage(),
                              RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
              return pBaton;
          }

          //StoreId for credit card encryption
          DBCriteria dbc = new DBCriteria();
          dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID,
                         orderReq.getAccountId());
          dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                         RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
          IdVector storeIdV = BusEntityAssocDataAccess.selectIdOnly(pCon,
                  BusEntityAssocDataAccess.BUS_ENTITY2_ID, dbc);
          if (storeIdV.size() == 0) {
              throw new Exception("No store found for account. Account Id: " +
                                  orderReq.getAccountId());
          }
          if (storeIdV.size() > 1) {
              throw new Exception(
                      "More than one store found for account. Accont Id: "
                      + orderReq.getAccountId());
          }
          Integer storeIdI = (Integer) storeIdV.get(0);
          ccU.encryptOrderCreditCard(pCon, storeIdI.intValue());
      } else {
          cc.setEncryptedCreditCardNumber(orderReq.getCcNumber());
          cc.setAuthStatusCd(RefCodeNames.CREDIT_CARD_AUTH_STATUS.AUTH_SUCCESS);
          cc.setEncryptionAlgorithm(RefCodeNames.CREDIT_CARD_ENC_ALG.NONE);
          cc.setCurrency(orderReq.getCcPaymetricCurrency());
          cc.setAvsStatus(orderReq.getCcPaymetricAvsStatus());
          cc.setAvsAddress(orderReq.getCcPaymetricAvsAddress());
          cc.setAvsZipCode(orderReq.getCcPaymetricAvsZipCode());

          CreditCardTransData cct = CreditCardTransData.createValue();
          cct.setInvoiceCustId(0);
          cct.setTransactionTypeCd(RefCodeNames.CREDIT_CARD_TRANS_TYPE_CD.AUTHORIZATION);
          cct.setAmount(orderReq.getCcPaymetricAmount());
          cct.setTransactionReference(orderReq.getCcPaymetricAuthRefCode());
          cct.setAuthCode(orderReq.getCcPaymetricAuthCode());
          cct.setPaymetricResponseCode(orderReq.getCcPaymetricResponseCode());
          cct.setPaymetricAuthDate(orderReq.getCcPaymetricAuthDate());
          cct.setPaymetricAuthTime(orderReq.getCcPaymetricAuthTime());
          cct.setPaymetricTransactionId(orderReq.getCcPaymetricTransactionId());
          cct.setPaymetricAuthMessage(orderReq.getCcPaymetricMessage());
          pBaton.setCreditCardTransData(cct);
      }
      pBaton.setOrderCreditCardData(cc);

      //dealing with existing order
      if (pBaton.getOrderData() != null && pBaton.getOrderData().getOrderId() > 0) {
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(OrderCreditCardDataAccess.ORDER_ID, pBaton.getOrderData().getOrderId());
        OrderCreditCardDataVector ccdv = OrderCreditCardDataAccess.select(pCon, crit);
        if (ccdv.size() == 1) {
          OrderCreditCardData old = (OrderCreditCardData) ccdv.get(0);
          //switch it over
          cc.setOrderCreditCardId(old.getOrderCreditCardId());
        } else if (ccdv.size() > 1) {
          throw new Exception("More than one existing credit card data objects tied to order id: " +
                              pBaton.getOrderData().getOrderId());
        }
      }

      pBaton.setWhatNext(pBaton.getWhatNext());
    } catch (Exception e) {
      e.printStackTrace();
      pBaton.addError(pCon, pBaton.STOP, null, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                      "pipeline.message.exception",
                      e.getMessage(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
    }
    return pBaton;
  }

}
