package com.cleanwise.service.api.pipeline;

import java.sql.*;

import com.cleanwise.service.api.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;


/**
 *
 */
public class StoreOrderCancel implements OrderPipeline{

    /**
     *Cancel an order
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
          // old order and order items
          OrderData oldOrder = OrderDataAccess.select(pCon, order.getOrderId());
          sBaton.getStoreOrderChangeRequestData().setOldOrderData(oldOrder);
          DBCriteria dbc = new DBCriteria();
          dbc.addEqualTo(OrderDataAccess.ORDER_ID,order.getOrderId());
          OrderItemDataVector oldOrderItems = OrderItemDataAccess.select(pCon,dbc);
          sBaton.getStoreOrderChangeRequestData().setOldOrderItems(oldOrderItems);

          String user = sBaton.getStoreOrderChangeRequestData().getUserName();

          // cancel order and items
          OrderData orderD = OrderDataAccess.select(pCon, order.getOrderId());
          OrderDAO.cancelAndUpdateOrder(pCon, orderD, user);

          // select updated order
          order = OrderDataAccess.select(pCon, order.getOrderId());
          sBaton.getStoreOrderChangeRequestData().setOrderData(order);
          // select updated items
          dbc = new DBCriteria();
          dbc.addEqualTo(OrderDataAccess.ORDER_ID,order.getOrderId());
          OrderItemDataVector orderItems = OrderItemDataAccess.select(pCon,dbc);
          pBaton.setUserName(user);
          pBaton.setOrderItemDataVector(orderItems);

        } catch(Exception e){
            e.printStackTrace();
            throw new PipelineException(e.getMessage());
        }
        return sBaton;
    }

    /**
     *Cancel Order
     *@param pOrder order
     *@param pUser the user login name
     *@exception  RemoteException     Description of Exception
     */
    public void cancelOrder(Connection conn, int pOrderId, String pUser)
    throws Exception{
      OrderData orderD = OrderDataAccess.select(conn, pOrderId);
      OrderDAO.cancelAndUpdateOrder(conn, orderD, pUser);
    }

}
