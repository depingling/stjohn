package com.cleanwise.service.api.pipeline;
import java.math.*;
import java.rmi.*;
import java.sql.*;
import java.util.*;

import com.cleanwise.service.api.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
/**
 * Makes Ledger Entry
 * @author  YKupershmidt (copied from IntegrationServicesBean)
 */
public class StoreOrderUpdatePoEntry  implements OrderPipeline
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
    OrderData orderD = sBaton.getStoreOrderChangeRequestData().getOrderData();
    OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();

    updatePo(pCon, pFactory, orderD, orderItemDV, pBaton.getUserName());

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

    public void updatePo(Connection pCon,
                         APIAccess pFactory,
                         OrderData pOrder,
                         OrderItemDataVector pOrderItemDV,
                         String pUser)  throws Exception {

        String erpSystemCd = pOrder.getErpSystemCd();
        java.util.HashSet poNums = new java.util.HashSet();
        for (int ii = 0; ii < pOrderItemDV.size(); ii++) {
          OrderItemData oiD = (OrderItemData) pOrderItemDV.get(ii);
          poNums.add(new Integer(oiD.getPurchaseOrderId()));
        }
        Iterator it = poNums.iterator();
        while (it.hasNext()) {
          Integer poId = (Integer) it.next();
//          if (poId.intValue() > 0) {
//            if (RefCodeNames.ERP_SYSTEM_CD.LAWSON.equals(erpSystemCd)) {
//              lawson.updatePurchaseOrder(poId.intValue());
//            }
//            else {
              updatePurchaseOrderFromOrderItems(pCon, poId.intValue(), pUser);
//            }
//          }

        }
    }


    public void updatePurchaseOrderFromOrderItems(Connection pCon,
                                                  int pPurchaseOrderId,
                                                  String pUser) throws Exception {
      if (pPurchaseOrderId == 0) {
        return;
      }
        //get the stjohn po to update
        PurchaseOrderData sjpo = PurchaseOrderDataAccess.select(pCon, pPurchaseOrderId);
        ResultSet rs;
        String sql = "SELECT SUM(total_quantity_ordered * dist_item_cost) AS LINE_TOTAL " +
                     "FROM clw_order_item WHERE order_item_status_cd <> '" + RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED  + "'" +
                           " AND purchase_order_id = " + pPurchaseOrderId;
        rs = pCon.createStatement().executeQuery(sql);
        //now get the po data
        if (rs == null || !rs.next()) {
          throw new RemoteException("No purchase Order Items for poId: " + pPurchaseOrderId);
        }
        BigDecimal subTot = rs.getBigDecimal("LINE_TOTAL");
        //means that the order was cancelled.
        if(subTot == null){
        	subTot = new BigDecimal("0");
        }
        //new total is old Misc Charges plus new subTotal
    	BigDecimal tot = sjpo.getPurchaseOrderTotal().subtract(sjpo.getLineItemTotal()).add(subTot);
    	rs.close();
        sjpo.setPurchaseOrderTotal(tot);
        sjpo.setLineItemTotal(subTot);
        sjpo.setModBy(pUser);
        
        

        PurchaseOrderDataAccess.update(pCon, sjpo);
    }

}
