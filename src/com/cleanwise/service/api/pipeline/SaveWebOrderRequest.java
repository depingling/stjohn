/*
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
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.session.ShoppingServicesBean.ServiceFeeDetail;
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
/**
 * Pipeline class that saves order request to database.
 * @author  YKupershmidt
 */
public class SaveWebOrderRequest  implements OrderPipeline
{
    /** Process this pipeline.
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
       if(!(orderReq instanceof CustomerOrderRequestData)) {
         return pBaton; //Edi order
       }

      IdVector replacedOrderIds = orderReq.getReplacedOrderIds();

       CustomerOrderRequestData custOrderReq = (CustomerOrderRequestData) orderReq;
       String userName = custOrderReq.getUserName();
       pBaton.setUserName(userName);
       pBaton.setCurrentDate(new Date());
       PreOrderData preOrderD = PreOrderData.createValue();
       pBaton.setPreOrderData(preOrderD);
       PreOrderAddressDataVector preOrderAddressDV =
                                               new PreOrderAddressDataVector();
       if ( null != custOrderReq.getRequestedBillingAddress() ) {
           AddressData billAddr = custOrderReq.getRequestedBillingAddress();
           PreOrderAddressData poa = PreOrderAddressData.createValue();
           poa.setAddressTypeCd(
           RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_BILLING);
           poa.setShortDesc("Customer billing address request");
           poa.setAddress1(billAddr.getAddress1());
           poa.setAddress2(billAddr.getAddress2());
           poa.setAddress3(billAddr.getAddress3());
           poa.setAddress4(billAddr.getAddress4());
           poa.setCity(billAddr.getCity());
           poa.setStateProvinceCd(billAddr.getStateProvinceCd());
           poa.setCountryCd(billAddr.getCountryCd());
           poa.setCountyCd(billAddr.getCountyCd());
           poa.setPostalCode(billAddr.getPostalCode());

           preOrderAddressDV.add(poa);
       }

       pBaton.setPreOrderAddressDataVector(preOrderAddressDV);
       PreOrderPropertyDataVector preOrderPropertyDV =
                                              new PreOrderPropertyDataVector();
       pBaton.setPreOrderPropertyDataVector(preOrderPropertyDV);

       PreOrderMetaDataVector preOrderMetaDV = new  PreOrderMetaDataVector();
       pBaton.setPreOrderMetaDataVector(preOrderMetaDV);

       boolean stopOrderFl = false;

       preOrderD.setUserName(custOrderReq.getUserName());

       //Account, site
       int accountId = custOrderReq.getAccountId();
       preOrderD.setAccountId(accountId);
       int siteId = custOrderReq.getSiteId();
       preOrderD.setSiteId(siteId);
       preOrderD.setSiteName(custOrderReq.getSiteName());

       preOrderD.setOrderBudgetTypeCd(custOrderReq.getOrderBudgetTypeCd());

       //Contact info
       preOrderD.setOrderContactName(custOrderReq.getOrderContactName());
       preOrderD.setOrderTelephoneNumber(custOrderReq.getOrderTelephoneNumber());
       preOrderD.setOrderFaxNumber(custOrderReq.getOrderFaxNumber());
       preOrderD.setOrderEmail(custOrderReq.getOrderEmail());


       //Order Source
       String orderSourceCd = custOrderReq.getOrderSourceCd();
       if (Utility.isSet(orderSourceCd)) {
         preOrderD.setOrderSourceCd(orderSourceCd);
       } else {
         preOrderD.setOrderSourceCd(RefCodeNames.ORDER_SOURCE_CD.WEB);
       }

       //Order Type
       String orderTypeCd = custOrderReq.getOrderType();
       preOrderD.setOrderTypeCd(orderTypeCd);

       //Customer Po number
       preOrderD.setCustomerPoNumber(custOrderReq.getCustomerPoNumber()==null ? null : custOrderReq.getCustomerPoNumber().trim());

       //StoreId
       DBCriteria dbc = new DBCriteria();
       dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID,accountId);
       dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                             RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
       IdVector storeIdV = BusEntityAssocDataAccess.selectIdOnly(pCon,
                                  BusEntityAssocDataAccess.BUS_ENTITY2_ID,dbc);
       if(storeIdV.size()==0){
          throw new Exception("No store found for account. Account Id: " + accountId);
       }
       if(storeIdV.size()>1) {
          throw new Exception("More than one store found for account. Accont Id: "
                                                                   + accountId);
       }
       Integer storeIdI = (Integer) storeIdV.get(0);
       int storeId = storeIdI.intValue();

       if (   custOrderReq.getOtherPaymentInfo() != null
           && custOrderReq.getOtherPaymentInfo().length() > 0 ) {
           PreOrderPropertyData popD = PreOrderPropertyData.createValue();
           popD.setOrderPropertyTypeCd
           (RefCodeNames.ORDER_PROPERTY_TYPE_CD.OTHER_PAYMENT_INFO);
           popD.setShortDesc
           (RefCodeNames.ORDER_PROPERTY_TYPE_CD.OTHER_PAYMENT_INFO);
           popD.setValue(custOrderReq.getOtherPaymentInfo());
           preOrderPropertyDV.add(popD);
       }

       // Save the rush charge specified.
       if (   custOrderReq.getRushOrderCharge() > 0 ) {
     preOrderD.setRushCharge
         (new BigDecimal(custOrderReq.getRushOrderCharge()));
       }

       //Ref order and comments
       preOrderD.setOrderRefNumber(custOrderReq.getOrderRefNumber());
       preOrderD.setCustomerComments(custOrderReq.getCustomerComments());


       preOrderD.setRefOrderId(custOrderReq.getRefOrderId());
       preOrderD.setOrderBudgetTypeCd(custOrderReq.getOrderBudgetTypeCd());

       //User related
       int userId = custOrderReq.getUserId();
       preOrderD.setUserId(userId);
       preOrderD.setAddBy(userName);
       preOrderD.setModBy(userName);

       //Cost Center
       preOrderD.setCostCenterId(custOrderReq.getCostCenterId());

       //Contract
       int contractId = custOrderReq.getContractId();
       preOrderD.setContractId(contractId);

       //Freight
       preOrderD.setFreightCharge(new BigDecimal(custOrderReq.getFreightCharge()));
       preOrderD.setHandlingCharge(new BigDecimal(custOrderReq.getHandlingCharge()));

       //Payment
       preOrderD.setPaymentType(custOrderReq.getPaymentType());

       //Trading profile //usually = 0 for non edi orders
       preOrderD.setIncomingProfileId(custOrderReq.getIncomingProfileId());

       //save various order properties (billing order, any entered notes)
       String billingOrderFl = (custOrderReq.isBillingOrder())?"T":"F";
       preOrderD.setBillingOrder(billingOrderFl);

       preOrderD.setOrderNote(custOrderReq.getOrderNote());

       // Add any order notes attached to the request.
       ArrayList orderRequestNotes = custOrderReq.getOrderNotes();
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

      //add customer URL and ID Properties
      if(Utility.isSet(orderReq.getCustomerSystemIdentifier())){
          String type = RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_SYSTEM_ID;
          PreOrderPropertyData popD = PreOrderPropertyData.createValue();
          popD.setShortDesc(type);
          popD.setOrderPropertyTypeCd(type);
          popD.setValue(orderReq.getCustomerSystemIdentifier());
          preOrderPropertyDV.add(popD);
      }
       if(Utility.isSet(orderReq.getCustomerSystemURL())){
          String type = RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_SYSTEM_URL;
          PreOrderPropertyData popD = PreOrderPropertyData.createValue();
          popD.setShortDesc(type);
          popD.setOrderPropertyTypeCd(type);
          popD.setValue(orderReq.getCustomerSystemURL());
          preOrderPropertyDV.add(popD);
      }

      preOrderD.setPaymentType(custOrderReq.getPaymentType());
      preOrderD.setOrderRequestedShipDate(custOrderReq.getOrderRequestedShipDate());
      String bypassOrderRoutingFl = (custOrderReq.getBypassOrderRouting())?"T":"F";
      preOrderD.setBypassOrderRouting(bypassOrderRoutingFl);

      if(custOrderReq.isBypassCustomerWorkflow()){
        preOrderD.setWorkflowInd(RefCodeNames.WORKFLOW_IND_CD.SKIP);
      }

      //Hold until date
      String holdDateS = custOrderReq.getHoldUntilDate();
      Date holdDate = null;
      if(Utility.isSet(holdDateS)) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
           holdDate = sdf.parse(holdDateS);
        }catch(Exception exc) {}
        if(holdDate==null) {
            //String errorText = "Wrong Place Order On date format: "+holdDateS;
            pBaton.addError(pCon,OrderPipelineBaton.WRONG_HOLD_UNTIL_DATE, null,
                           RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW,0, 0,
                           "pipeline.message.wrongHoldUntilDate",
                           holdDateS, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
        }
      }
      preOrderD.setHoldUntilDate(holdDate);

      //Properties
      PropertyDataVector props = custOrderReq.getProperties();
      for (int ii = 0; null != props && ii < props.size(); ii++) {
        PropertyData oProp = (PropertyData) props.get(ii);
        PreOrderPropertyData popD = PreOrderPropertyData.createValue();
        popD.setOrderPropertyTypeCd(oProp.getPropertyTypeCd());
        popD.setShortDesc(oProp.getShortDesc());
        popD.setValue(oProp.getValue());
        preOrderPropertyDV.add(popD);
      }

      //Meta
      OrderMetaDataVector meta = custOrderReq.getOrderMeta();
      for (int ii = 0; null != meta && ii < meta.size(); ii++) {
        OrderMetaData oMeta = (OrderMetaData) meta.get(ii);
        PreOrderMetaData  pomD = PreOrderMetaData.createValue();
        pomD.setName(oMeta.getName());
        pomD.setValue(oMeta.getValue());
        pomD.setValueNum(oMeta.getValueNum());
        pomD.setAddBy(userName); 
        preOrderMetaDV.add(pomD);
      }
      //-------------- Items

      HashMap serviceFeeDetMap = orderReq.getServiceFeeDetail();
      OrderRequestData.ItemEntryVector reqItems = orderReq.getEntriesCollection();
      PreOrderItemDataVector preOrderItemDV = new PreOrderItemDataVector();
      //Create order item objects
      OrderItemDataVector orderItemDV = new OrderItemDataVector();
      pBaton.setOrderItemDataVector(orderItemDV);
      for (int ii = 0; ii < reqItems.size(); ii++) {
        OrderRequestData.ItemEntry reqItem =
                            (OrderRequestData.ItemEntry)reqItems.get(ii);
        PreOrderItemData preOrderItemD = PreOrderItemData.createValue();
        preOrderItemDV.add(preOrderItemD);
        pBaton.setPreOrderItemDataVector(preOrderItemDV);
        preOrderItemD.setItemId(reqItem.getItemId());
        preOrderItemD.setAssetId(reqItem.getAssetId());
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

        BigDecimal price = null;
        try {
          price = new BigDecimal(reqItem.getPrice());
        } catch (Exception exc){}
        if(price==null) {
//          String mess = "Wrong price in request. Customer sku number = "+reqSku;
          pBaton.addError(pCon, OrderPipelineBaton.INVALID_SKU_PRICE,
                          null, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,
                          preOrderItemD.getLineNumber(), 0,
                          "pipeline.message.invalidSkuPrice",
                          reqSku, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);

        }
        preOrderItemD.setPrice(price);

        String isAnInvItemFl = (reqItem.isaInventoryItem())?"T":"F";
        preOrderItemD.setAsInventoryItem(isAnInvItemFl);

        if (reqItem.isaInventoryItem()) {
          preOrderItemD.setInventoryQtyOnHand(reqItem.getInventoryQtyOnHand());
          preOrderItemD.setInventoryParValue(reqItem.getInventoryParValue());
        }
        
        OrderItemData oiD = OrderItemData.createValue();
        orderItemDV.add(oiD);
        oiD.setCustLineNum(preOrderItemD.getLineNumber());
        oiD.setCustItemSkuNum(preOrderItemD.getCustomerSku());
        oiD.setItemId(preOrderItemD.getItemId());
        oiD.setSaleTypeCd(preOrderItemD.getSaleTypeCd());
        oiD.setAssetId(preOrderItemD.getAssetId());
        oiD.setDistErpNum(reqItem.getDistErpNum());
        if(serviceFeeDetMap!=null && serviceFeeDetMap.size()>0){       	
       	 //Set service fee if applicable          
       	 Integer item = new Integer(preOrderItemD.getItemId());
       	 ServiceFeeDetail sfd = (ServiceFeeDetail)serviceFeeDetMap.get(item);
       	 if(sfd!=null){      		
       		 oiD.setServiceFee(sfd.getAmount());
       	 }          
        }
        
      }
      //Sku Type
      preOrderD.setSkuTypeCd(orderReq.getSkuTypeCd());

      //Save  order request
      //PreOrder
      preOrderD = PreOrderDataAccess.insert(pCon,preOrderD);
      int preOrderId = preOrderD.getPreOrderId();

      //PreOrderAddress
      for(Iterator iter = preOrderAddressDV.iterator(); iter.hasNext();) {
        PreOrderAddressData poaD = (PreOrderAddressData) iter.next();
        poaD.setPreOrderId(preOrderId);
        poaD = PreOrderAddressDataAccess.insert(pCon,poaD);
      }

      //PreOrderProperty
      for(Iterator iter = preOrderPropertyDV.iterator(); iter.hasNext();) {
        PreOrderPropertyData popD = (PreOrderPropertyData) iter.next();
        popD.setPreOrderId(preOrderId);
        popD = PreOrderPropertyDataAccess.insert(pCon,popD);
      }

      //PreOrderItem
      for(Iterator iter = preOrderItemDV.iterator(); iter.hasNext();) {
        PreOrderItemData poiD = (PreOrderItemData) iter.next();
        poiD.setPreOrderId(preOrderId);
        poiD = PreOrderItemDataAccess.insert(pCon,poiD);
        
        //populate pre_order_item_id into pre_order_meta
        if(serviceFeeDetMap!=null && serviceFeeDetMap.size()>0){       	
        	//Set service fee if applicable          
        	Integer item = new Integer(poiD.getItemId());
        	ServiceFeeDetail sfd = (ServiceFeeDetail)serviceFeeDetMap.get(item);
        	if(sfd!=null){
        		PreOrderMetaData pomd = PreOrderMetaData.createValue();
        		pomd.setName(RefCodeNames.PRICE_RULE_TYPE_CD.SERVICE_FEE);
        		pomd.setValue(sfd.getAmount().toString());
        		pomd.setAddBy(userName); 
        		pomd.setPreOrderItemId(poiD.getPreOrderItemId());
        		preOrderMetaDV.add(pomd);
        	}          
        }
      }
      

      //PreOrderMeta
     for(Iterator iter = preOrderMetaDV.iterator(); iter.hasNext();) {
       PreOrderMetaData pomD = (PreOrderMetaData) iter.next();
       pomD.setPreOrderId(preOrderId);
       pomD = PreOrderMetaDataAccess.insert(pCon,pomD);
     }
     
     //Alternate ShipTo Address
     if ( null != custOrderReq.getAlternateShipToAddress()) {
         AddressData alternateShipTo = custOrderReq.getAlternateShipToAddress();
         PreOrderAddressData poa = PreOrderAddressData.createValue();
         poa.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
         poa.setShortDesc("Customer alternate shipTo address");
         poa.setAddress1(alternateShipTo.getAddress1());
         poa.setAddress2(alternateShipTo.getAddress2());
         poa.setAddress3(alternateShipTo.getAddress3());
         poa.setAddress4(alternateShipTo.getAddress4());
         poa.setCity(alternateShipTo.getCity());
         poa.setStateProvinceCd(alternateShipTo.getStateProvinceCd());
         poa.setCountryCd(alternateShipTo.getCountryCd());
         poa.setCountyCd(alternateShipTo.getCountyCd());
         poa.setPostalCode(alternateShipTo.getPostalCode());
            
         poa.setPreOrderId(preOrderId);
         poa = PreOrderAddressDataAccess.insert(pCon,poa);
     }
        
      //Create order object
       OrderData orderD = OrderData.createValue();
       pBaton.setOrderData(orderD);
       orderD.setPreOrderId(preOrderId);
       orderD.setStoreId(storeId);
       orderD.setAccountId(accountId);
       orderD.setSiteId(siteId);
       orderD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.RECEIVED);
       orderD.setOrderTypeCd(orderTypeCd);
       orderD.setOrderBudgetTypeCd(preOrderD.getOrderBudgetTypeCd());    

      //check for replaced orders
      if(replacedOrderIds!=null && replacedOrderIds.size()>0) {
    	  dbc = new DBCriteria();
          dbc.addOneOf(OrderAssocDataAccess.ORDER1_ID,replacedOrderIds);
          dbc.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_CD, RefCodeNames.ORDER_ASSOC_CD.REPLACED);
          OrderAssocDataVector oaDV =
              OrderAssocDataAccess.select(pCon,dbc);
          if(oaDV.size()>0) {
              OrderAssocData oaD = (OrderAssocData) oaDV.get(0);
              int oId = oaD.getOrder2Id();
              OrderData oD = OrderDataAccess.select(pCon,oId);
              String messKey = "pipeline.message.orderReplacesAlreadyReplacedOrders";
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
              String arg0 = "";
              if(oD.getUserFirstName()!=null || oD.getUserLastName()!=null) {
                  arg0 = Utility.strNN(oD.getUserFirstName())+" "+
                              Utility.strNN(oD.getUserLastName());
              } else {
                  if(userId>0) {
                      UserData uD = UserDataAccess.select(pCon,userId);
                      userName = Utility.strNN(uD.getFirstName())+" "+
                              Utility.strNN(uD.getLastName());
                  }
              }
              pBaton.addError(pCon, OrderPipelineBaton.GENERIC_ERROR, null,
                           RefCodeNames.ORDER_STATUS_CD.REJECTED,0, 0,
                           messKey,
                           oD.getOrderNum(),RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
                           arg0, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
                           sdf.format(oD.getAddDate()), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
             pBaton.setWhatNext(OrderPipelineBaton.STOP);
             return pBaton; //Does not save web orders if error found
          } else  {
             dbc = new DBCriteria();
             dbc.addOneOf(OrderDataAccess.ORDER_ID, replacedOrderIds);
             OrderDataVector replacedOrderDV =
                 OrderDataAccess.select(pCon,dbc);
             for(Iterator iter=replacedOrderDV.iterator(); iter.hasNext();) {
                 OrderData replOrderD = (OrderData) iter.next();
                 String statusCd = replOrderD.getOrderStatusCd();
                 if(!RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW.equals(statusCd) &&
                    !RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW.equals(statusCd) &&
                    !RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(statusCd) &&
                    !RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION.equals(statusCd) &&
                    !RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL.equals(statusCd) &&
					!RefCodeNames.ORDER_STATUS_CD.REJECTED.equals(statusCd) &&
					!RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(statusCd))
                 {
                      pBaton.addError(pCon, OrderPipelineBaton.GENERIC_ERROR, null,
                                   RefCodeNames.ORDER_STATUS_CD.REJECTED,0, 0,
                                   "pipeline.message.orderReplacesNotPendingOrders");
                 }
             }
          }
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
