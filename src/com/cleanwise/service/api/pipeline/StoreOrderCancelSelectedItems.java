package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;

import java.sql.Connection;

import com.cleanwise.service.api.value.*;

import java.util.Iterator;
import java.math.BigDecimal;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.util.DBCriteria;


/**
 *
 */
public class StoreOrderCancelSelectedItems  extends StoreOrderPipeline {

    /**
     *Cancel order items
     */
    public OrderPipelineBaton process(OrderPipelineBaton pBaton,
            OrderPipelineActor pActor,
            Connection pCon,
            APIAccess pFactory)
            throws PipelineException {
        pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
        StoreOrderPipelineBaton sBaton =(StoreOrderPipelineBaton)pBaton;
        try{
          OrderData order = sBaton.getStoreOrderChangeRequestData().getOrderData();
          IdVector selOrderItems = sBaton.getStoreOrderChangeRequestData().getDelOrderItems();
          OrderItemDataVector orderItems = sBaton.getStoreOrderChangeRequestData().getOrderItems();
          OrderMetaDataVector orderMeta = sBaton.getStoreOrderChangeRequestData().getOrderMeta();
          String user = sBaton.getStoreOrderChangeRequestData().getUserName();
          BigDecimal freight = sBaton.getStoreOrderChangeRequestData().getTotalFreightCost();
          BigDecimal misc = sBaton.getStoreOrderChangeRequestData().getTotalMiscCost();
          BigDecimal smallOrderFee = sBaton.getStoreOrderChangeRequestData().getSmallOrderFeeAmt();
          BigDecimal fuelSurcharge = sBaton.getStoreOrderChangeRequestData().getFuelSurchargeAmt();
          BigDecimal discount = sBaton.getStoreOrderChangeRequestData().getDiscountAmt();
//        DBCriteria dbc = new DBCriteria();
//        dbc.addEqualTo(OrderDataAccess.ORDER_ID, order.getOrderId());
//        OrderItemDataVector orderItemsDV = OrderItemDataAccess.select(pCon,dbc);
          setTotalPrice(order, orderItems, selOrderItems);
          setHandlingCharges(order, freight, misc);
          setMetaCharges(order, orderMeta, smallOrderFee, fuelSurcharge, discount, user);
          cancelSelectedItems(pCon, pFactory, order, selOrderItems, user, false);
        } catch(Exception e){
          e.printStackTrace();
          throw new PipelineException(e.getMessage());
        }
        return sBaton;
    }

    /**
     *calculate the total price
     *@param pOrderD order
     *@param pOrderItemsDV order items
     *@param pSelOrderItemsDV selected order items
     */
    public void setTotalPrice(OrderData pOrderD,
                              OrderItemDataVector pOrderItemsDV,
                              IdVector pSelOrderItemsDV) {

    BigDecimal totalPrice = new BigDecimal(0);
    for(Iterator iter = pOrderItemsDV.iterator(); iter.hasNext();) {
      OrderItemData orderItemD = (OrderItemData) iter.next();
      int orderItemId = orderItemD.getOrderItemId();
      for (Iterator it = pSelOrderItemsDV.iterator(); it.hasNext(); ) {
        int selOrderItemId = ( (Integer) it.next()).intValue();
        if (orderItemId != selOrderItemId) {
          String itemStatus = orderItemD.getOrderItemStatusCd();
          if (!RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.equals(itemStatus)) {
            BigDecimal amount = orderItemD.getCustContractPrice();
            if (amount != null) {
              int qty = orderItemD.getTotalQuantityOrdered();
              BigDecimal price = amount.multiply(new BigDecimal(qty));
              //price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
              totalPrice = totalPrice.add(price);

            }
          }
        }
      }
    }
    //totalPrice = totalPrice.setScale(2,BigDecimal.ROUND_HALF_UP);
    pOrderD.setOriginalAmount(totalPrice);
    pOrderD.setTotalPrice(totalPrice);

  }

  private void cancelSelectedItems(Connection pConn,
                                   APIAccess pFactory,
                                   OrderData pOrder,
                                   IdVector pOrderItemIdV,
                                   String pUser,
                                   boolean pDoFreightUpdate)
     throws PipelineException {
      if (null==pOrderItemIdV || pOrderItemIdV.size()==0) {
        return;
      }
      try {
          //Cancel from Lawson first
          DBCriteria crit = new DBCriteria();
          crit.addOneOf(OrderItemDataAccess.ORDER_ITEM_ID,
                            pOrderItemIdV);
          OrderItemDataVector oiDV = OrderItemDataAccess.select(pConn,crit);
          OrderDAO.cancelAndUpdateOrderItems(pConn, oiDV, pOrder, pUser);
      } catch (Exception e) {
        throw new PipelineException(e.getMessage());

      } finally {
      }

      return;
  }


}
