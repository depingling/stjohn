/*
 * OrderERPUpdater.java
 *
 * Created on August 3, 2005, 10:06 PM
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.dao.OrderPropertyDataAccess;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.ReplacedOrderItemViewVector;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;


/**
 *
 * @author bstevens
 */
public class OrderErpUpdater {
    
    /**
     *Updates an order that already exists in the revelant erp sub system
     */
    public OrderPipelineBaton process(OrderPipelineBaton pBaton,
            OrderPipelineActor pActor,
            Connection pCon,
            APIAccess pFactory)
            throws PipelineException {
        pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
        return pBaton;
    }
    
    /**
     *Updates order items, if they were not canceled.
     *Initiates request to change item in Lawson (cost and quantiy so far)
     *Stolen from the order bean method of the same name
     *@param pOrder order
     *@param  pOrderItemDV the list of modified OrderItemData objects
     *@param pOrderPropertyDV the list of item quntity modification requests
     *@param pUser the user login name
     *@exception  RemoteException     Description of Exception
     */
    public void updateOrderSentToErp(Connection conn,OrderData pOrder,
                                     OrderItemDataVector pOrderItemDV, 
                                     OrderPropertyDataVector pOrderPropertyDV, 
                                     ReplacedOrderItemViewVector pReplacedOrderItemVwV,
                                     String pUser) 
    throws Exception{
        int erpOrderNum = pOrder.getErpOrderNum();
          OrderItemData oiD = (OrderItemData) pOrderItemDV.get(0);
          boolean lawsonFl = false;
          String erpSystemCd = pOrder.getErpSystemCd();
          if(pReplacedOrderItemVwV!=null) { 
            //consolidated order
            OrderDAO.updateReplacedOrderItems(conn, pOrderItemDV, pReplacedOrderItemVwV, pUser);
            
          }
          //Udate order items
          java.util.HashSet poNums = new java.util.HashSet();
          for(int ii=0; ii<pOrderItemDV.size(); ii++) {
            oiD = (OrderItemData) pOrderItemDV.get(ii);
            poNums.add(new Integer(oiD.getPurchaseOrderId()));
            if(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.
                equals(oiD.getOrderItemStatusCd())) {
              continue;
            }
            int orderItemId = oiD.getOrderItemId();
            //get order item data againg to minimize probability of asynchrony 
            OrderItemData oiD1 = OrderItemDataAccess.select(conn,orderItemId);
            if(oiD1.getItemQty855()!=0)
              //save quantiy update request only if 855 was already sent
              for(int jj=0; jj<pOrderPropertyDV.size(); jj++) {
                OrderPropertyData opD = (OrderPropertyData) pOrderPropertyDV.get(jj);
                if(opD.getOrderItemId()==orderItemId &&
                   RefCodeNames.ORDER_PROPERTY_TYPE_CD.QUANTITY_UPDATE.
                   equals(opD.getOrderPropertyTypeCd())) {
                   OrderPropertyDataAccess.insert(conn,opD);
                }
            }
            oiD.setModBy(pUser);
            oiD = updateAddOrderItemWorker(conn, oiD);
          }
          //update the pos
          Iterator it = poNums.iterator();
          while(it.hasNext()){
              Integer poId = (Integer) it.next();
              APIAccess factory = APIAccess.getAPIAccess();
              if(poId.intValue() > 0){
                    factory.getPurchaseOrderAPI().updatePurchaseOrderFromOrderItems(poId.intValue());
              }
              
          }
    }
    
    private OrderItemData updateAddOrderItemWorker(Connection pCon, OrderItemData pOrderItemData)
    throws Exception{

            if (pOrderItemData.isDirty())
            {

                if (pOrderItemData.getOrderItemId() == 0)
                {
                    pOrderItemData = OrderItemDataAccess.insert(pCon, pOrderItemData);
                }
                else
                {
                    OrderDAO.updateOrderItem(pCon,pOrderItemData);
                }
            }

        return pOrderItemData;
    }
    
}
