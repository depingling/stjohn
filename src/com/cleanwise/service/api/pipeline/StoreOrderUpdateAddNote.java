package com.cleanwise.service.api.pipeline;
import java.math.*;
import java.rmi.*;
import java.sql.*;
import java.util.*;

import com.cleanwise.service.api.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.Order;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
public class StoreOrderUpdateAddNote  implements OrderPipeline
{
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
    pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
    StoreOrderPipelineBaton sBaton =(StoreOrderPipelineBaton)pBaton;
    OrderData oldOrderD = sBaton.getStoreOrderChangeRequestData().getOldOrderData();
    OrderData newOrderD = sBaton.getStoreOrderChangeRequestData().getOrderData();
    OrderItemDataVector oldOrderItemV = sBaton.getStoreOrderChangeRequestData().getOldOrderItems();
    OrderItemDataVector newOrderItemV = pBaton.getOrderItemDataVector();

    // check order information
    checkOrderUpdate(pFactory, oldOrderD, newOrderD, pBaton.getUserName(), pCon);

    // check items information
    for (int i = 0; i < newOrderItemV.size(); i++) {
      OrderItemData newD = (OrderItemData)newOrderItemV.get(i);
      boolean found = false;
      for (int j = 0; j < oldOrderItemV.size(); j++) {
        OrderItemData oldD = (OrderItemData)oldOrderItemV.get(j);
        if (oldD.getOrderItemId() == newD.getOrderItemId()) {
          found = true;
          checkOrderItemUpdate(pFactory, oldD.getOrderId(), oldD, newD, pBaton.getUserName(), pCon);
        }
      }
      if(!found) {
        checkOrderItemUpdate(pFactory, newD.getOrderId(), null, newD, pBaton.getUserName(), pCon);
      }
    }

    //Return
     pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
     return pBaton;
    }
    catch(Exception exc) {
       exc.printStackTrace();
      throw new PipelineException(exc.getMessage());
    }
    finally{
    }
    }


    private void checkOrderUpdate(APIAccess pFactory,
                                  OrderData pOldOrder,
                                  OrderData pNewOrder,
                                  String pUser,
                                  Connection pCon)  throws Exception {
      checkField(pFactory, pNewOrder.getOrderId(), 0,
                 "Status", pOldOrder.getOrderStatusCd(), pNewOrder.getOrderStatusCd(),
                 pUser, pCon);

      checkField(pFactory, pNewOrder.getOrderId(), 0,
                 "Workflow Indicator", pOldOrder.getWorkflowInd(),pNewOrder.getWorkflowInd(),
                 pUser, pCon);

      checkField(pFactory, pNewOrder.getOrderId(), 0,
                 "Budget Type", pOldOrder.getOrderBudgetTypeCd(), pNewOrder.getOrderBudgetTypeCd(),
                 pUser, pCon);
      DateFormat mDateFormat = new SimpleDateFormat("MM/dd/yyyy");
//      java.util.Date oldOrderDate = pOldOrder.getRevisedOrderDate()==null?pOldOrder.getOriginalOrderDate():pOldOrder.getRevisedOrderDate();
//      java.util.Date newOrderDate = pNewOrder.getRevisedOrderDate()==null?pNewOrder.getOriginalOrderDate():pNewOrder.getRevisedOrderDate();
//      mDateFormat.format(pOldOrder.getRevisedOrderDate()==null?pOldOrder.getOriginalOrderDate():pOldOrder.getRevisedOrderDate());
//      mDateFormat.format(pNewOrder.getRevisedOrderDate()==null?pNewOrder.getOriginalOrderDate():pNewOrder.getRevisedOrderDate());
      checkField(pFactory, pNewOrder.getOrderId(), 0,
                 "Date Ordered",
                 mDateFormat.format(pOldOrder.getRevisedOrderDate()==null?pOldOrder.getOriginalOrderDate():pOldOrder.getRevisedOrderDate()),
                 mDateFormat.format(pNewOrder.getRevisedOrderDate()==null?pNewOrder.getOriginalOrderDate():pNewOrder.getRevisedOrderDate()),
                 pUser, pCon);
    }

    private void checkOrderItemUpdate(APIAccess pFactory,
                                  int pOrderId,
                                  OrderItemData pOldOrderItem,
                                  OrderItemData pNewOrderItem,
                                  String pUser,
                                  Connection pCon)  throws Exception {
      String mes = "";
      if (pOldOrderItem == null) {
        //mes = "New item added: line " + pNewOrderItem.getOrderLineNum() +
        //    ", cw sku#: " + pNewOrderItem.getCustItemSkuNum();
        String messKey = "pipeline.message.newItemAdded";
        addNote(pFactory, pOrderId, 0, pUser, messKey, ""+pNewOrderItem.getOrderLineNum(), pNewOrderItem.getCustItemSkuNum(), null, pCon);
      } else {
        checkField(pFactory, pOrderId, pNewOrderItem.getOrderItemId(),
                   "CW Sku#", pOldOrderItem.getCustItemSkuNum(), pNewOrderItem.getCustItemSkuNum(),
                   pUser, pCon);
        checkField(pFactory, pOrderId, pNewOrderItem.getOrderItemId(),
                   "Dist Sku#", pOldOrderItem.getDistItemSkuNum(), pNewOrderItem.getDistItemSkuNum(),
                   pUser, pCon);
        checkField(pFactory, pOrderId, pNewOrderItem.getOrderItemId(),
                   "Customer Price", pOldOrderItem.getCustContractPrice(), pNewOrderItem.getCustContractPrice(),
                   pUser, pCon);
        checkField(pFactory, pOrderId, pNewOrderItem.getOrderItemId(),
                   "CW Cost", pOldOrderItem.getDistItemCost(), pNewOrderItem.getDistItemCost(),
                   pUser, pCon);
        checkField(pFactory, pOrderId, pNewOrderItem.getOrderItemId(),
                   "Order Quantity ", "" + pOldOrderItem.getTotalQuantityOrdered(), "" + pNewOrderItem.getTotalQuantityOrdered(),
                   pUser, pCon);
        checkField(pFactory, pOrderId, pNewOrderItem.getOrderItemId(),
                   "Sale Type", pOldOrderItem.getSaleTypeCd(), pNewOrderItem.getSaleTypeCd(),
                   pUser, pCon);
        checkField(pFactory, pOrderId, pNewOrderItem.getOrderItemId(),
                   "Item status", pOldOrderItem.getOrderItemStatusCd(), pNewOrderItem.getOrderItemStatusCd(),
                   pUser, pCon);
        checkField(pFactory, pOrderId, pNewOrderItem.getOrderItemId(),
                   "Tax exempt", pOldOrderItem.getTaxExempt(), pNewOrderItem.getTaxExempt(),
                   pUser, pCon);
      }

    }

    private void checkField(APIAccess pFactory,
                            int orderId,
                            int orderItemId,
                            String fName,
                            Object oldS,
                            Object newS,
                            String pUser,
                            Connection pCon)  throws Exception {
      String mes = "";
      String o = null;
      String n = null;
	  if(oldS instanceof String) {
	     o = (String)oldS;
         if(!Utility.isSet(o)) o = "None";
      } else {
         o = (oldS==null)?"None":oldS.toString();
	  }
	  if(newS instanceof String) {
         n = (String) newS;
		 if(!Utility.isSet(n)) n = "None";
      } else {
         n = (newS==null)?"None":newS.toString();
	  }
      if (!o.equals(n)) {
        //mes = fName + " changed to: " + n + ", the previous was: " + o;
        String messKey = "pipeline.message.fieldValueChanged";
        addNote(pFactory, orderId, orderItemId, pUser, messKey, fName, n, o, pCon);
      }

    }

    private void addNote(APIAccess pFactory,
                         int pOrderId,
                         int pOrderItemId,
                         String pUser,
                         String messKey,
                         String arg0,
                         String arg1,
                         String arg2,
                         Connection pCon)  throws Exception {

      Order orderEjb = pFactory.getOrderAPI();
      OrderPropertyData opd = OrderPropertyData.createValue();
      String type = RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING;
	//STJ-5604
      String message = PipelineUtil.translateMessage(messKey, null, arg0, type, arg1, type, arg2, type, null, null);
      opd.setOrderId(pOrderId);

      if (pOrderItemId != 0) {
        opd.setOrderItemId(pOrderItemId);
      }
      opd.setShortDesc("Order Update");
      opd.setValue(message);
      opd.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
      opd.setOrderPropertyStatusCd("ACTIVE");
      opd.setAddBy(pUser);
      opd.setModBy(pUser);
      opd.setMessageKey(messKey);
      opd.setArg0(arg0);
      opd.setArg0TypeCd(RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
      opd.setArg1(arg1);
      opd.setArg1TypeCd(RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
      opd.setArg2(arg2);
      opd.setArg2TypeCd(RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
      orderEjb.addNote(opd);

    }






}
