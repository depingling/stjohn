/*
 * OrderErpProcessor.java
 *
 * Created on September 22, 2003, 1:26 PM
 */

package com.cleanwise.service.api.pipeline;
import com.cleanwise.service.api.session.ThruStoreErp;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.APIAccess;
import java.sql.Connection;

import org.apache.log4j.Logger;


/**
 *
 * @author  bstevens
 * changed by scher 5/14/2009
 */
public class OrderErpProcessor implements OrderPipeline, PostOrderCapturePipeline
{

    private static final Logger log = Logger.getLogger(OrderErpProcessor.class);

    /** Creates a new instance of OrderErpProcessor */
    public OrderErpProcessor() {
    }
    /**
     * Deprecated
     */
    public void process(com.cleanwise.service.api.value.OrderData pOrderData, com.cleanwise.service.api.value.OrderItemDataVector pOrderItemDataVector, int pAccountId, java.sql.Connection pCon, com.cleanwise.service.api.APIAccess pFactory,  AccCategoryToCostCenterView pCategToCostCenterView)
    throws com.cleanwise.service.api.util.PipelineException {

    	log.info("****************SVC: process() method1");
        try{
            if(RefCodeNames.ORDER_STATUS_CD.SENDING_TO_ERP.equals(pOrderData.getOrderStatusCd())){
            	log.info("****************SVC: process() method1; status_code = SENDING_TO_ERP");
                pFactory.getThruStoreErpAPI().processNewThruStoreOrder(pOrderData.getOrderId(), "ERP Pipeline:orders_to_lawson");
            }else{
                //if the order is in an error state preserve it's state, otherwise set it to rejected status
                //this allows the pipeline objects to set the order status to an error state.
                if(!(
                    RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW.equals(pOrderData.getOrderStatusCd()) ||
                    RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW.equals(pOrderData.getOrderStatusCd()) ||
                    RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED.equals(pOrderData.getOrderStatusCd())
                )){
                    OrderPropertyData error = OrderPropertyData.createValue();
                    error.setOrderId(pOrderData.getOrderId());
                    error.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
                    error.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
                    error.setAddBy("OrderErpProcessor");
                    error.setModBy("OrderErpProcessor");
                    String errorMess = "The order status is not \""+RefCodeNames.ORDER_STATUS_CD.SENDING_TO_ERP+"\". Order id: "+pOrderData.getOrderId();
                    errorMess = errorMess + " :: "+pOrderData.getOrderStatusCd();
                    error.setValue(errorMess);
                    OrderPropertyDataAccess.insert(pCon, error);
                    pOrderData.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED);
                    OrderDataAccess.update(pCon, pOrderData);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new PipelineException(e.getMessage());
        }
    }


    public OrderPipelineBaton process(OrderPipelineBaton pBaton,
                OrderPipelineActor pActor,
                Connection pCon,
                APIAccess pFactory)
    throws PipelineException
    {

      //log.info("****************SVC: process() method2");

      OrderData orderD = pBaton.getOrderData();
      OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
      pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
      try{
        orderD = OrderDataAccess.select(pCon,orderD.getOrderId());
        String status = orderD.getOrderStatusCd();
        if(RefCodeNames.ORDER_STATUS_CD.ORDERED.equals(status) ||
        RefCodeNames.ORDER_STATUS_CD.PRE_PROCESSED.equals(status)){
          status = RefCodeNames.ORDER_STATUS_CD.SENDING_TO_ERP;
          //log.info("****************SVC: process() method2; status = " + status);
          orderD.setOrderStatusCd(status);
          OrderDataAccess.update(pCon, orderD);
        }
        if(RefCodeNames.ORDER_STATUS_CD.SENDING_TO_ERP.equals(status)){
            log.info("****************SVC: process() method2; status = " + status);
              pFactory.getThruStoreErpAPI().
                processNewThruStoreOrder(orderD.getOrderId(), pBaton.getUserName());
         }else{
           //if the order is in an error state preserve it's state, otherwise set it to rejected status
           //this allows the pipeline objects to set the order status to an error state.
           if(!(
               RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW.equals(status) ||
               RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW.equals(status) ||
               RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED.equals(status)
           )){
             OrderPropertyData error = OrderPropertyData.createValue();
             error.setOrderId(orderD.getOrderId());
             error.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
             error.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
             error.setAddBy("OrderErpProcessor");
             error.setModBy("OrderErpProcessor");
             String errorMess = "The order status is not \""+
                   RefCodeNames.ORDER_STATUS_CD.SENDING_TO_ERP+"\". Order id: "+orderD.getOrderId();
             errorMess = errorMess + " :: "+status;
             error.setValue(errorMess);
             error = OrderPropertyDataAccess.insert(pCon, error);
             pBaton.addOrderPropertyData(error);
             orderD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED);
             OrderDataAccess.update(pCon, orderD);
           }
        }

        //Pick up order data and order item data. In case they have changed

        log.info("****************SVC: process() method2; Pick up order data and order item data. In case they have changed");

        /*** SVC: new piece of code 5/14/2009 ***/
        // check if the order has a type "Confirmation Only Order":
        // 1)create select statement for clw_order_property table
        DBCriteria dbc = new DBCriteria();
        OrderPropertyDataVector orderPropertyDV;
        log.info("order_id for creating SQL stmt = " + orderD.getOrderId());
        String orderPropertyTypeCd = "trim(" + OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD + ")";
        String clwValue = "trim(" + OrderPropertyDataAccess.CLW_VALUE +")";
        dbc.addEqualTo(OrderPropertyDataAccess.ORDER_ID,orderD.getOrderId()); // match by specific ORDER_ID
        dbc.addEqualTo(orderPropertyTypeCd,RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORDER); // match by "BILLING_ORDER" value
        dbc.addEqualTo(clwValue,"true");

        orderPropertyDV = OrderPropertyDataAccess.select(pCon,dbc);
        log.info("orderPropertyDV = " + orderPropertyDV);
        if ( !orderPropertyDV.isEmpty() ) { //this is a "Confirmation Only Order"

        	log.info("Confirmation Only Order: order_id = " + orderD.getOrderId());

        	// get clw_value=invoice_number from the clw_order_property table
        	dbc = new DBCriteria();
            log.info("order_id for creating SQL stmt = " + orderD.getOrderId());
            orderPropertyTypeCd = "trim(" + OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD + ")";
            dbc.addEqualTo(OrderPropertyDataAccess.ORDER_ID,orderD.getOrderId()); // match by specific ORDER_ID
            dbc.addEqualTo(orderPropertyTypeCd,RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_DISTRIBUTOR_INVOICE);// match by "BILLING_DISTRIBUTOR_INVOICE" value

            orderPropertyDV = OrderPropertyDataAccess.select(pCon,dbc);
            log.info("orderPropertyDV = " + orderPropertyDV);

            if ( !orderPropertyDV.isEmpty() ) {

               //get the value of the clw_value field
               OrderPropertyData opd = (OrderPropertyData) orderPropertyDV.get(0);
               String clwValue1="";
               if(opd!=null && opd.getValue()!=null){
            	   clwValue1 = opd.getValue().trim();
               }

               log.info("clwValue1 = " + clwValue1);

        	   // 2) select the single record from the clw_invoice_dist DB table with the invoice_num=invoiceNum
        	   dbc = new DBCriteria();
        	   String invoiceNum = "trim(" + InvoiceDistDataAccess.INVOICE_NUM + ")";
        	   dbc.addEqualTo(invoiceNum, clwValue1);
        	   //add distributor in criteria ??? <- suggested by Brook Stevens
        	   InvoiceDistDataVector invoiceDistDV = InvoiceDistDataAccess.select(pCon,dbc);
        	   log.info("invoiceDistDV = " + invoiceDistDV);
        	   if ( !invoiceDistDV.isEmpty() ) {
        	      InvoiceDistData idd = (InvoiceDistData) invoiceDistDV.get(0);
        	      int invoiceDistOrderId = orderD.getOrderId(); // save found value of the order_id
        	      log.info("**********SVC: invoiceDistOrderId = " + invoiceDistOrderId);

        		  // 3) update clw_invoice_dist DB table: match existing (old) invoice with the newly created Purchase Order (PO)

        		  //set new values to update single record in the clw_invoice_dist DB table
        		  idd.setOrderId(invoiceDistOrderId);
        		  String newErpPoNum = "#" + orderD.getOrderNum() + "-00";
        		  log.info("***********SVC: newErpPoNum = " + newErpPoNum);
        		  idd.setErpPoNum(newErpPoNum);
        		  idd.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.DIST_SHIPPED); // Brook Stevens and Andy Snow recommendation


        		  InvoiceDistDataAccess.update(pCon,idd); // update single record in the clw_invoice_dist DB table

        		  int invoiceDistId = idd.getInvoiceDistId();
        		  log.info("********SVC: invoiceDistId = " + invoiceDistId);

        		  //find invoice_dist items based off of dist_item_sku_num (and quantity ??? - I think that quantity is irrelevant)
        		  //set the order_item_id in those items
        		  log.info("********SVC: size of the OrderItemDV = " + orderItemDV.size());
        		  for(int ii=0; ii<orderItemDV.size(); ii++) {
                      OrderItemData oiD = (OrderItemData) orderItemDV.get(ii);
                      if (oiD.getOrderId() == orderD.getOrderId()) { // we found the same order
                    	  int orderItemId = oiD.getOrderItemId(); // store order_item_id in the var
                    	  log.info("********SVC: orderItemId = " + orderItemId);
                    	  String distItemSkuNum = oiD.getDistItemSkuNum().trim(); // or getManuItemSkuNum() ???
                    	  log.info("********SVC: distItemSkuNum = " + distItemSkuNum);
                    	  /******************************************************************************************************/
                    	  /*** update record(s) in the DB table clw_invoice_dist_detail;                                      ***/
                    	  /*** this operation will consist of 2 steps:                                                        ***/
                    	  /*** 1) select the record from clw_invoice_dist_detail table based off of dist_item_sku_num;        ***/
                    	  /*** 2) update this record with the new value of order_item_id (this value is 0 before the update)  ***/
                    	  /******************************************************************************************************/
                    	  dbc = new DBCriteria();
                    	  dbc.addEqualTo(InvoiceDistDetailDataAccess.INVOICE_DIST_ID, invoiceDistId); // match by INVOICE_DIST_ID
                   	      String sqlDistItemSkuNum = "trim(" + InvoiceDistDetailDataAccess.DIST_ITEM_SKU_NUM + ")";
                   	      dbc.addEqualTo(sqlDistItemSkuNum, distItemSkuNum); // match by DIST_ITEM_SKU_NUM

                   	      InvoiceDistDetailDataVector invoiceDistDetailDV = InvoiceDistDetailDataAccess.select(pCon,dbc);
                   	      log.info("invoiceDistDetailDV = " + invoiceDistDetailDV);

                   	      if ( invoiceDistDetailDV.size() > 0 ) {
                   	         InvoiceDistDetailData iddd = (InvoiceDistDetailData) invoiceDistDetailDV.get(0);
                   	         iddd.setOrderItemId(orderItemId);
                   	         InvoiceDistDetailDataAccess.update(pCon,iddd); // update single record in the clw_invoice_dist_detail DB table
                   	      } else { // size = 0 => order item is NOT in the Invoice (and invoice table),
                                   //but ONLY in the Placed Order
                   	    	 log.info("Order Item SKU NUM = " + distItemSkuNum + " is in the PLACED ORDER but not in the INVOICE.");
                   	      }
                      }
                  }

        	   }

        	}


        }

        /*****************************************/
        dbc = new DBCriteria(); //SVC
        dbc.addEqualTo(OrderItemDataAccess.ORDER_ID,orderD.getOrderId());
        dbc.addOrderBy(OrderItemDataAccess.ORDER_ITEM_ID);
        orderItemDV = OrderItemDataAccess.select(pCon,dbc);

        pBaton.setOrderData(orderD);
        pBaton.setOrderItemDataVector(orderItemDV);
        pBaton.setOrderStatus(orderD.getOrderStatusCd());
        if(pBaton.hasErrors()){
          pBaton.setWhatNext(OrderPipelineBaton.STOP);
        } else {
          pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
        }
        return pBaton;
      }catch(Exception exc) {
        exc.printStackTrace();
        throw new PipelineException(exc.getMessage());
      }
    }

}
