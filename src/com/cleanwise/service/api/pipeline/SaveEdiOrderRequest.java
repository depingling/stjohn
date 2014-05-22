/*
 * CancelReplaceItemDuplication.java
 *
 * Created on August 25, 2003
 */

package com.cleanwise.service.api.pipeline;
import java.sql.Connection;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.ItemSkuMapping;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.dao.ItemSubstitutionDefDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.EmailDataAccess;
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
/**
 * Pipeline class that does intial ui order request parsing.
 * @author  YKupershmidt
 */
public class SaveEdiOrderRequest  implements OrderPipeline
{

    /**
     *Converts an order address data object into a pre order address data object.
     */
    private PreOrderAddressData createPreOrderAddressData(OrderAddressData source){
        PreOrderAddressData preOrderAddressD = PreOrderAddressData.createValue();
        preOrderAddressD.setAddressTypeCd(source.getAddressTypeCd());
        preOrderAddressD.setShortDesc(source.getShortDesc());
        preOrderAddressD.setAddress1(source.getAddress1());
        preOrderAddressD.setAddress2(source.getAddress2());
        preOrderAddressD.setAddress3(source.getAddress3());
        preOrderAddressD.setAddress4(source.getAddress4());
        preOrderAddressD.setCity(source.getCity());
        preOrderAddressD.setStateProvinceCd(source.getStateProvinceCd());
        preOrderAddressD.setCountryCd(source.getCountryCd());
        preOrderAddressD.setCountyCd(source.getCountyCd());
        preOrderAddressD.setPostalCode(source.getPostalCode());
        return preOrderAddressD;
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
                APIAccess pFactory)
    throws PipelineException
    {
     try{
       OrderRequestData orderReq = pBaton.getOrderRequestData();
       if(orderReq instanceof CustomerOrderRequestData) {
         return pBaton; //Not Edi order
       }
       pBaton.setCurrentDate(new Date());
       String userName = "Edi order pipeline";
       if(orderReq.getUserNameKey()!=null && orderReq.getUserNameKey().trim().length()>0){
    	   userName = orderReq.getUserNameKey();
    	   if (userName.length() > 30)
    		   userName = userName.substring(0,30);
       }
       pBaton.setUserName(userName);
       PreOrderData preOrderD = PreOrderData.createValue();
       pBaton.setPreOrderData(preOrderD);
       //
       String sourceCd = orderReq.getOrderSourceCd();
       if(sourceCd==null){
         sourceCd = RefCodeNames.ORDER_SOURCE_CD.EDI_850;
         orderReq.setOrderSourceCd(sourceCd);
       }
       preOrderD.setOrderSourceCd(sourceCd);

       //get account id for edi orders
       Account actEjb = pFactory.getAccountAPI();
       String siteName = orderReq.getSiteName();
       preOrderD.setSiteName(siteName);
       int accountId = orderReq.getAccountId();
       preOrderD.setAccountId(accountId);
       String orderTypeCd = orderReq.getOrderType();
       preOrderD.setOrderTypeCd(orderTypeCd);
       String custPoNumber = orderReq.getCustomerPoNumber();
       preOrderD.setCustomerPoNumber(custPoNumber);
       int tradingPartnerId = orderReq.getTradingPartnerId();
       preOrderD.setTradingPartnerId(tradingPartnerId);
       //Site
       String contactName = orderReq.getOrderContactName();
       preOrderD.setOrderContactName(contactName);
       int siteId = orderReq.getSiteId();
       preOrderD.setSiteId(siteId);

       //user name key
       preOrderD.setUserNameKey(orderReq.getUserNameKey());
 
       preOrderD.setOrderTelephoneNumber(orderReq.getOrderTelephoneNumber());
       preOrderD.setOrderFaxNumber(orderReq.getOrderFaxNumber());
       preOrderD.setOrderEmail(orderReq.getOrderEmail());

      //
       preOrderD.setOrderRefNumber(orderReq.getOrderRefNumber());
       preOrderD.setCustomerComments(orderReq.getCustomerComments());

       preOrderD.setRefOrderId(orderReq.getRefOrderId());

       //Payment type
       preOrderD.setPaymentType(orderReq.getPaymentType());

       //
       Date reqShipDate = orderReq.getOrderRequestedShipDate();
       preOrderD.setOrderRequestedShipDate(reqShipDate);

       //trading profile
       preOrderD.setIncomingProfileId(orderReq.getIncomingProfileId());

       String billingOrder = (orderReq.isBillingOrder())?"T":"F";
       preOrderD.setBillingOrder(billingOrder);

       String customerBillingUnit = orderReq.getCustomerBillingUnit();
       if(Utility.isSet(customerBillingUnit)){
         preOrderD.setCustomerBillingUnit(customerBillingUnit);
       }
       // status override
       preOrderD.setOrderStatusCdOveride(orderReq.getOrderStatusCdOveride());

       //
       PreOrderPropertyDataVector preOrderPropertyDV =
                                              new PreOrderPropertyDataVector();
       pBaton.setPreOrderPropertyDataVector(preOrderPropertyDV);
       PreOrderMetaDataVector preOrderMetaDV = new  PreOrderMetaDataVector();
       pBaton.setPreOrderMetaDataVector(preOrderMetaDV);

       //Properties
       PropertyDataVector props = orderReq.getProperties();
       for (int ii = 0; null != props && ii < props.size(); ii++) {
         PropertyData oProp = (PropertyData) props.get(ii);
         PreOrderPropertyData popD = PreOrderPropertyData.createValue();
         popD.setOrderPropertyTypeCd(oProp.getPropertyTypeCd());
         popD.setShortDesc(oProp.getShortDesc());
         popD.setValue(oProp.getValue());
         preOrderPropertyDV.add(popD);
       }


      PreOrderAddressDataVector preOrderAddressDV = new PreOrderAddressDataVector();
      pBaton.setPreOrderAddressDataVector(preOrderAddressDV);

      OrderAddressData custShipto = orderReq.getOrderShipAddress();
      custShipto.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_SHIPPING);
      custShipto.setShortDesc("Shipping addr from EDI order");
      PreOrderAddressData preOrderAddressDST = createPreOrderAddressData(custShipto);
      preOrderAddressDV.add(preOrderAddressDST);
      pBaton.setCustShipToData(custShipto);

      OrderAddressData custBillto = orderReq.getOrderBillAddress();
      if (Utility.isSet(custBillto.getAddress1()) ||
        Utility.isSet(custBillto.getAddress2()) || Utility.isSet(custBillto.getAddress3()) ||
        Utility.isSet(custBillto.getAddress4()) || Utility.isSet(custBillto.getCity()) ||
        Utility.isSet(custBillto.getStateProvinceCd()) || Utility.isSet(custBillto.getCountryCd()) ||
        Utility.isSet(custBillto.getCountyCd()) || Utility.isSet(custBillto.getPostalCode()) ){
      custBillto.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_BILLING);
      custBillto.setShortDesc("Billing addr from EDI order");
      PreOrderAddressData preOrderAddressDBT = createPreOrderAddressData(custBillto);
      preOrderAddressDV.add(preOrderAddressDBT);
      pBaton.setCustBillToData(custBillto);
      }


      //Bypass order routing
       String bypassOrderRouting = (orderReq.getBypassOrderRouting())?"T":"F";
       preOrderD.setBypassOrderRouting(bypassOrderRouting);


      //
      preOrderD.setOrderNote(orderReq.getOrderNote());

      //
      String freeFormatAddress = (orderReq.getFreeFormatAddress())?"T":"F";
      preOrderD.setFreeFormatAddress(freeFormatAddress);

      //
      preOrderD.setCustomerOrderDate(orderReq.getCustomerOrderDate());


      // Add any order notes attached to the request.
      ArrayList orderRequestNotes = orderReq.getOrderNotes();
      for (int ii = 0; orderRequestNotes != null && ii < orderRequestNotes.size(); ii++) {
        String onote = (String)orderRequestNotes.get(ii);
        if(Utility.isSet(onote)) {
         String shortDesc = "Order Request Note";
         PreOrderPropertyData popD = PreOrderPropertyData.createValue();
         popD.setShortDesc(shortDesc);
         popD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_ORDER_NOTE);
         popD.setValue(onote);
         preOrderPropertyDV.add(popD);
       }
     }


      //add some miscellanious properties
      if(Utility.isSet(orderReq.getPunchOutOrderOrigOrderNum())){
       PreOrderPropertyData oProp = PreOrderPropertyData.createValue();
       oProp.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.PUNCH_OUT_ORDER_ORIG_ORDER_NUM);
       oProp.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.PUNCH_OUT_ORDER_ORIG_ORDER_NUM);
       oProp.setValue(orderReq.getPunchOutOrderOrigOrderNum());
       preOrderPropertyDV.add(oProp);
      }

      if(orderReq.isHistoricalOrder()){
    	  PreOrderPropertyData oProp = PreOrderPropertyData.createValue();
          oProp.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.HISTORICAL_ORDER);
          oProp.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.HISTORICAL_ORDER);
          oProp.setValue("true");
          preOrderPropertyDV.add(oProp);
      }
      
      if(orderReq.isSkipDupOrderValidation()){
    	  PreOrderPropertyData oProp = PreOrderPropertyData.createValue();
          oProp.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.SKIP_DUPLICATED_ORDER_VALIDATION);
          oProp.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.SKIP_DUPLICATED_ORDER_VALIDATION);
          oProp.setValue("true");
          preOrderPropertyDV.add(oProp);
      }

      //-------------- Items

      OrderRequestData.ItemEntryVector reqItems = orderReq.getEntriesCollection();
      PreOrderItemDataVector preOrderItemDV = new PreOrderItemDataVector();
      for (int ii = 0; ii < reqItems.size(); ii++) {
        OrderRequestData.ItemEntry reqItem =
                            (OrderRequestData.ItemEntry)reqItems.get(ii);
        PreOrderItemData preOrderItemD = PreOrderItemData.createValue();
        preOrderItemDV.add(preOrderItemD);
        pBaton.setPreOrderItemDataVector(preOrderItemDV);

        preOrderItemD.setItemId(reqItem.getItemId());

        preOrderItemD.setSaleTypeCd(reqItem.getSaleTypeCd());

        int qty = reqItem.getQuantity();
        preOrderItemD.setQuantity(qty);

        preOrderItemD.setLineNumber(reqItem.getLineNumber());

        preOrderItemD.setCustomerUom(reqItem.getCustomerUom());

        String reqSku = reqItem.getCustomerSku();
        preOrderItemD.setCustomerSku(reqSku);
        preOrderItemD.setCustomerProductDesc(reqItem.getCustomerProductDesc());
        preOrderItemD.setCustomerPack(reqItem.getCustomerPack());
        preOrderItemD.setDistributorId(reqItem.getDistributorId());
        preOrderItemD.setOrderItemActionCd(reqItem.getInitialOrderItemAction());
        
        if(Utility.isSet(reqItem.getIsTaxExempt())){
        	preOrderItemD.setTaxExempt(reqItem.getIsTaxExempt());
        }
        if(Utility.isSet(reqItem.getLineTaxAmount())){
        	preOrderItemD.setTaxAmount(reqItem.getLineTaxAmount());
        }

        BigDecimal price = null;
        try {
          price = new BigDecimal(reqItem.getPrice());
        } catch (Exception exc){}
        if(price==null) {
          //String mess = "Wrong price in request. Customer sku number = "+reqSku;
          pBaton.addError(pCon, OrderPipelineBaton.INVALID_SKU_PRICE,
                           null, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,
                                                   preOrderItemD.getLineNumber(),0,
                                                   "pipeline.message.invalidSkuPrice",
                                                   ""+reqSku, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);

        }

        preOrderItemD.setPrice(price);

        String isAnInvItemFl = (reqItem.isaInventoryItem())?"T":"F";
        preOrderItemD.setAsInventoryItem(isAnInvItemFl);

        if (reqItem.isaInventoryItem()) {
          preOrderItemD.setInventoryQtyOnHand(reqItem.getInventoryQtyOnHand());
          preOrderItemD.setInventoryParValue(reqItem.getInventoryParValue());
        }
      }


      //Sku Type
      preOrderD.setSkuTypeCd(orderReq.getSkuTypeCd());
      preOrderD.setOrderBudgetTypeCd(orderReq.getOrderBudgetTypeCd());
      
      //Save  order request
      //PreOrder
      preOrderD = PreOrderDataAccess.insert(pCon,preOrderD);
      int preOrderId = preOrderD.getPreOrderId();

      //PreOrderItem
      for(Iterator iter = preOrderItemDV.iterator(); iter.hasNext();) {
        PreOrderItemData poiD = (PreOrderItemData) iter.next();
        poiD.setPreOrderId(preOrderId);
        poiD = PreOrderItemDataAccess.insert(pCon,poiD);
      }

      //PreOrderProperty
      for(Iterator iter = preOrderPropertyDV.iterator(); iter.hasNext();) {
        PreOrderPropertyData popD = (PreOrderPropertyData) iter.next();
        popD.setPreOrderId(preOrderId);
        popD = PreOrderPropertyDataAccess.insert(pCon,popD);
      }


      //PreOrderAddress
      for(Iterator iter = preOrderAddressDV.iterator(); iter.hasNext();) {
        PreOrderAddressData poaD = (PreOrderAddressData) iter.next();
        poaD.setPreOrderId(preOrderId);
        poaD = PreOrderAddressDataAccess.insert(pCon,poaD);
      }
      
      
      //Meta
      OrderMetaDataVector meta = orderReq.getOrderMeta();
      for (int ii = 0; null != meta && ii < meta.size(); ii++) {
        OrderMetaData oMeta = (OrderMetaData) meta.get(ii);
        PreOrderMetaData  pomD = PreOrderMetaData.createValue();
        pomD.setName(oMeta.getName());
        pomD.setValue(oMeta.getValue());
        pomD.setValueNum(oMeta.getValueNum());
        pomD.setAddBy(userName);
        pomD.setPreOrderId(preOrderId);
        pomD = PreOrderMetaDataAccess.insert(pCon,pomD);
        preOrderMetaDV.add(pomD);
      }

      //Create order object
       OrderData orderD = OrderData.createValue();
       pBaton.setOrderData(orderD);
       orderD.setPreOrderId(preOrderId);
       orderD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.RECEIVED);
       orderD.setOrderBudgetTypeCd(preOrderD.getOrderBudgetTypeCd());
       orderD.setAddBy(userName);
       orderD.setModBy(userName);
       orderD.setRequestPoNum(preOrderD.getCustomerPoNumber());
       orderD.setOrderSiteName(preOrderD.getSiteName());
       orderD.setOrderContactName(preOrderD.getOrderContactName());
       orderD = OrderDataAccess.insert(pCon, orderD);
       int orderId = orderD.getOrderId();

       //Create order item objects
       OrderItemDataVector orderItemDV = new OrderItemDataVector();
       pBaton.setOrderItemDataVector(orderItemDV);
       //PreOrderItem
       for(Iterator iter = preOrderItemDV.iterator(); iter.hasNext();) {
         PreOrderItemData poiD = (PreOrderItemData) iter.next();
         OrderItemData oiD = OrderItemData.createValue();
         orderItemDV.add(oiD);
         oiD.setOrderId(orderId);
         oiD.setCustLineNum(poiD.getLineNumber());
         oiD.setCustItemSkuNum(poiD.getCustomerSku());
         oiD.setCustItemUom(poiD.getCustomerUom());
         oiD.setItemId(poiD.getItemId());
         oiD.setSaleTypeCd(poiD.getSaleTypeCd());
         oiD.setAddBy(userName);
         oiD.setModBy(userName);
         oiD = OrderItemDataAccess.insert(pCon, oiD);

         }



      //Return
      pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
      return pBaton;
    }catch(Exception e){
       e.printStackTrace();
        throw new PipelineException(e.getMessage());
    }finally{
    }
    }

}
