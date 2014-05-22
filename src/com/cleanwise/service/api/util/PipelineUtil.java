



/*
 * PipelineUtil.java
 *
 * Created on August 3, 2005, 2:31 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.cleanwise.service.api.util;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.pipeline.*;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.value.*;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import com.cleanwise.service.api.session.Workflow;
import com.cleanwise.service.api.session.User;

import org.apache.log4j.Logger;

/**
 *
 * @author bstevens
 */
public class PipelineUtil {
	
  private static final Logger log = Logger.getLogger(PipelineUtil.class);
  public static final int VALUE_LENGTH = 2000;
  public static final int ARG_LENGTH = 255;


    /** Creates a new instance of PipelineUtil */
    public PipelineUtil() {
    }
    //------------------------------------------------------------------------------------
    public void
         processPipeline(Connection pCon,APIAccess factory, OrderData pOrderData, String pPipelineType)
    throws RemoteException {
      try {
        OrderPipelineActor pipelineActor = new OrderPipelineActor();
        int orderId = pOrderData.getOrderId();
        OrderPipelineBaton baton = createOrderPipelineBaton(pCon,pOrderData);
        baton.setPipelineTypeCd(pPipelineType);
        if(RefCodeNames.WORKFLOW_IND_CD.TO_RESUME.equals(pOrderData.getWorkflowInd())) {
          baton.setWhatNext(OrderPipelineBaton.GO_BREAK_POINT);
        }
        baton.setUserName(pPipelineType);

        OrderPipelineBaton[] pipelineResult =
                   pipelineActor.processPipeline(
                     baton,
                     pPipelineType,
                     pCon,
                     factory);
        ProcessOrderResultData result = ProcessOrderResultData.createValue();
        if(pipelineResult.length>1) {
        	if (orderId > 0){
        		OrderData orderD = OrderDataAccess.select(pCon, orderId);
        		if (RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(orderD.getOrderStatusCd())){
        			String replOrderNums = null;
        			for (OrderPipelineBaton pResult : pipelineResult){
        				if(replOrderNums==null) {
        					replOrderNums = pResult.getOrderData().getOrderNum();
        				} else {
        					replOrderNums += ", "+pResult.getOrderData().getOrderNum();
        				}
        			}
        			OrderPropertyData orderPropD = OrderPropertyData.createValue();
    	           orderPropD.setOrderId(orderId);
    	           orderPropD.setShortDesc("Cancelled");
    	           orderPropD.setValue("The order was replaced by split orders: "+
    	        		   replOrderNums);
    	           orderPropD.
    	              setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
    	           orderPropD.
    	              setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
    	           orderPropD.setAddBy(baton.getUserName());
    	           orderPropD.setModBy(baton.getUserName());
    	           OrderPropertyDataAccess.insert(pCon, orderPropD);
        		}
        	}
          //Comment out for split order
          /*String mess = "Something wrong with order processing. "+
                  "Order request generated  "+
                    pipelineResult.length +" orders";
          throw new RemoteException(mess);*/
        }
      } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
      }
    }
//---------------------------------------------------------------------------------------
private OrderPipelineBaton createOrderPipelineBaton(Connection pCon, OrderData pOrderData)
throws Exception
{
   OrderPipelineBaton baton = new OrderPipelineBaton();
   baton.setStepNum(0);
   baton.setPipelineTypeCd(RefCodeNames.PIPELINE_CD.POST_ORDER_CAPTURE);
   baton.setWhatNext(OrderPipelineBaton.GO_NEXT);
   baton.setOrderRequestData(null); //no order request necessary
   baton.setOrderStatus(RefCodeNames.ORDER_STATUS_CD.ORDERED);
   baton.setCurrentDate(new Date());
   baton.setUserWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
    //
   OrderData orderD = pOrderData;
   int orderId = orderD.getOrderId();
   baton.setOrderData(orderD);
   //
   DBCriteria dbc = null;
   int preOrderId = pOrderData.getPreOrderId();
   if(preOrderId>0) {
     PreOrderData poD = PreOrderDataAccess.select(pCon,preOrderId);
     //baton.setPreOrderData(poD);
     baton.setPreOrderData(poD);
     dbc = new DBCriteria();
     dbc.addEqualTo(PreOrderAddressDataAccess.PRE_ORDER_ID,preOrderId);
     PreOrderAddressDataVector poaDV = PreOrderAddressDataAccess.select(pCon,dbc);
     baton.setPreOrderAddressDataVector(poaDV);

     dbc = new DBCriteria();
     dbc.addEqualTo(PreOrderPropertyDataAccess.PRE_ORDER_ID,preOrderId);
     PreOrderPropertyDataVector popDV = PreOrderPropertyDataAccess.select(pCon,dbc);
     baton.setPreOrderPropertyDataVector(popDV);

     dbc = new DBCriteria();
     dbc.addEqualTo(PreOrderMetaDataAccess.PRE_ORDER_ID,preOrderId);
     PreOrderMetaDataVector pomDV = PreOrderMetaDataAccess.select(pCon,dbc);
     baton.setPreOrderMetaDataVector(pomDV);

     dbc = new DBCriteria();
     dbc.addEqualTo(PreOrderItemDataAccess.PRE_ORDER_ID,preOrderId);
     PreOrderItemDataVector poiDV = PreOrderItemDataAccess.select(pCon,dbc);
     baton.setPreOrderItemDataVector(poiDV);
   }


   //
   dbc = new DBCriteria();
   dbc.addEqualTo(OrderItemDataAccess.ORDER_ID,orderId);
   dbc.addOrderBy(OrderItemDataAccess.ORDER_ITEM_ID);
   OrderItemDataVector orderItemDV =
                      OrderItemDataAccess.select(pCon,dbc);
   baton.setOrderItemDataVector(orderItemDV);
   //
   dbc = new DBCriteria();
   dbc.addEqualTo(OrderAddressDataAccess.ORDER_ID,orderId);
   OrderAddressDataVector orderAddressDV =
      OrderAddressDataAccess.select(pCon,dbc);
   for(Iterator iter = orderAddressDV.iterator(); iter.hasNext();){
     OrderAddressData oaD = (OrderAddressData) iter.next();
     String addressType = oaD.getAddressTypeCd();
     if(RefCodeNames.ADDRESS_TYPE_CD.BILLING.equals(addressType)) {
       if(baton.getBillToData()==null) {
         baton.setBillToData(oaD);
       } else {
         baton.addError(pCon, OrderPipelineBaton.BILL_TO_ADDRESS_PROBLEM,
             RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,0, 0,
             "pipeline.message.billToAddressProblem");
       }
     }
     else if(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING.equals(addressType)) {
       if(baton.getShipToData()==null) {
         baton.setShipToData(oaD);
       } else {
         baton.addError(pCon, OrderPipelineBaton.SHIP_TO_ADDRESS_PROBLEM,
             RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,0, 0,
             "pipeline.message.shipToAddressProblem");
       }
     }
     else if(RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_SHIPPING.equals(addressType)) {
       if(baton.getCustShipToData()==null) {
         baton.setCustShipToData(oaD);
       } else {
         baton.addError(pCon, OrderPipelineBaton.REQ_SHIP_TO_ADDRESS_PROBLEM,
             RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,0, 0,
             "pipeline.message.reqShipToAddressProblem");
       }
     }
     else if(RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_BILLING.equals(addressType)) {
       if(baton.getCustBillToData()==null) {
         baton.setCustBillToData(oaD);
       } else {
         baton.addError(pCon, OrderPipelineBaton.REQ_BILL_TO_ADDRESS_PROBLEM,
             RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,0, 0,
             "pipeline.message.reqBillToAddressProblem");
       }
     }
   }
   //
   dbc = new DBCriteria();
   dbc.addEqualTo(OrderPropertyDataAccess.ORDER_ID,orderId);
   dbc.addOrderBy(OrderPropertyDataAccess.ORDER_PROPERTY_ID);
   OrderPropertyDataVector orderPropDV =
     OrderPropertyDataAccess.select(pCon,dbc);
   baton.setOrderPropertyDataVector(orderPropDV);
   //
   dbc = new DBCriteria();
   dbc.addEqualTo(OrderMetaDataAccess.ORDER_ID,orderId);
   dbc.addOrderBy(OrderMetaDataAccess.ORDER_META_ID);
   OrderMetaDataVector orderMetaDV =
     OrderMetaDataAccess.select(pCon,dbc);
   baton.setOrderMetaDataVector(orderMetaDV);
   //
   IdVector orderItemIdV = new IdVector();
   IdVector itemIdv=new IdVector();
   for(Iterator iter = orderItemDV.iterator(); iter.hasNext();){
     OrderItemData oiD = (OrderItemData) iter.next();
     int orderItemId = oiD.getOrderItemId();
     int itemId=oiD.getItemId();
     orderItemIdV.add(new Integer(orderItemId));
     itemIdv.add(new Integer(itemId));
   }
   dbc = new DBCriteria();
   dbc.addOneOf(OrderItemMetaDataAccess.ORDER_ITEM_ID,orderItemIdV);
   dbc.addOrderBy(OrderItemMetaDataAccess.ORDER_ITEM_ID);
   OrderItemMetaDataVector orderItemMetaDV =
     OrderItemMetaDataAccess.select(pCon,dbc);
   int orderItemIdPrev = -1;
   OrderItemMetaDataVector itemMetaGroupDV = null;
   for(Iterator iter = orderItemMetaDV.iterator(),
                iter1 = orderItemDV.iterator();
       iter.hasNext();){
     OrderItemMetaData oimD = (OrderItemMetaData) iter.next();
     int orderItemId = oimD.getOrderItemId();
     if(orderItemId!=orderItemIdPrev){
       orderItemIdPrev = orderItemId;
       int lineNum = 0;
       while(iter1.hasNext()) {
         OrderItemData oiD = (OrderItemData) iter1.next();
         int oiId = oiD.getOrderItemId();
         if(oiId==orderItemId) {
           lineNum = oiD.getOrderLineNum();
           break;
         }
       }
       itemMetaGroupDV = new OrderItemMetaDataVector();
       baton.addOrderItemMetaDataVector(lineNum, itemMetaGroupDV);
     }
     itemMetaGroupDV.add(oimD);
   }

    dbc = new DBCriteria();
    dbc.addOneOf(ItemDataAccess.ITEM_ID,itemIdv);
    dbc.addOrderBy(ItemDataAccess.ITEM_ID);
    ItemDataVector itemData =ItemDataAccess.select(pCon,dbc);
    baton.setSimpleServiceOrderFl(isSimpleServiceOrder(itemData));
    baton.setSimpleProductOrderFl(isSimpleProductOrder(itemData));


    baton.setBypassWkflRuleActionCd(
                      RefCodeNames.WORKFLOW_RULE_ACTION.REJECT_ORDER);
    baton.setBypassOptional(false);

//baton.setWorkflowRuleDataVector(
//baton.setWorkflowQueueDataVector(
    return baton;
}

////////////////////////////////////////////////////////////////////////////////
    public static OrderPipelineBaton saveNewOrder(OrderPipelineBaton pBaton,
                OrderPipelineActor pActor,
                Connection pCon,
                APIAccess pFactory)
    throws Exception
    {
     String userName = pBaton.getUserName();
     if(userName==null) userName="";
     String propUserName = "System (user: "+userName+")";
     String orderStatus = pBaton.getOrderStatus();
     //Change order status to ordered if it still equals RECEIVED
     if(RefCodeNames.ORDER_STATUS_CD.RECEIVED.equals(orderStatus)) {
       orderStatus = RefCodeNames.ORDER_STATUS_CD.ORDERED;
       pBaton.setOrderStatus(orderStatus);
     }
     //
     OrderData orderD = pBaton.getOrderData();
     if(RefCodeNames.ORDER_STATUS_CD.ORDERED.equals(orderStatus) &&
        RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED.
          equals(orderD.getOrderTypeCd())) {
       orderStatus = RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION;
       pBaton.setOrderStatus(orderStatus);
     }
     String  orderSourceCd = orderD.getOrderSourceCd();
     int orderId = orderD.getOrderId();
     if(!RefCodeNames.ORDER_SOURCE_CD.EDI_850.equals(orderSourceCd) &&
         orderId<=0 ) {
       if(!RefCodeNames.ORDER_STATUS_CD.RECEIVED.equals(orderStatus) &&
          !RefCodeNames.ORDER_STATUS_CD.ORDERED.equals(orderStatus) &&
          !RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL.equals(orderStatus) &&
          !RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(orderStatus) &&
          !RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION.equals(orderStatus) &&
          !RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(orderStatus) &&
          !RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW.equals(orderStatus) &&
          !RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW.equals(orderStatus)) {
          return pBaton; //Does not save web orders if error found
       }
     }
     //Get order number
     SetOrderNumber son = new SetOrderNumber();
     son.setOrderNumber(orderD,pFactory, pCon);
     String exceptionIndicator =
       (RefCodeNames.ORDER_STATUS_CD.ORDERED.equals(orderStatus))?"N":"Y";
     orderD.setExceptionInd(exceptionIndicator);
     orderD.setOrderStatusCd(orderStatus);
     //Save order info
     // Check/modify the site name as users can now
     // define them.
     /* it is no need to cut off site name to 30 symbols
     String t = orderD.getOrderSiteName();
     if ( t!= null && t.length() > 30 ) {
        t = t.substring(0,30);
        orderD.setOrderSiteName(t);
     } */
     
     boolean newOrder = false;
     if(orderId <=0 ) {
       newOrder = true;
       orderD = OrderDataAccess.insert(pCon, orderD);
       orderId = orderD.getOrderId();

       //IF consolidated order
       OrderRequestData orderReq = pBaton.getOrderRequestData();
       IdVector replacedOrderIds = orderReq.getReplacedOrderIds();
       if(RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(orderD.getOrderTypeCd())) {
         if(replacedOrderIds==null || replacedOrderIds.size()==0) {
           throw new PipelineException("No replaced orders for consolidated order");
         }
         String replOrderNums = null;
         for(Iterator iter = replacedOrderIds.iterator(); iter.hasNext(); ) {
           Integer replOrderIdI = (Integer) iter.next();
           int replOrderId = replOrderIdI.intValue();
           OrderData replOrderD = OrderDataAccess.select(pCon,replOrderId);
           replOrderD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.CANCELLED);
           OrderDataAccess.update(pCon,replOrderD);
           OrderAssocData orderAssocD = OrderAssocData.createValue();
           orderAssocD.setOrder1Id(replOrderId);
           orderAssocD.setOrder2Id(orderId);
           orderAssocD.setOrderAssocCd(RefCodeNames.ORDER_ASSOC_CD.CONSOLIDATED);
           orderAssocD.
               setOrderAssocStatusCd(RefCodeNames.ORDER_ASSOC_STATUS_CD.ACTIVE);
           orderAssocD.setAddBy(propUserName);
           orderAssocD.setModBy(propUserName);
           OrderAssocDataAccess.insert(pCon, orderAssocD);
           if(replOrderNums==null) {
             replOrderNums = replOrderD.getOrderNum();
           } else {
             replOrderNums += ", "+replOrderD.getOrderNum();
           }
           OrderPropertyData orderPropD = OrderPropertyData.createValue();
           orderPropD.setOrderId(replOrderId);
           orderPropD.setShortDesc("Consolidation");
           orderPropD.setValue("The order was replaced by consolidated order "+
                   orderD.getOrderNum());
           orderPropD.
              setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
           orderPropD.
              setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
           orderPropD.setAddBy(propUserName);
           orderPropD.setModBy(propUserName);
           OrderPropertyDataAccess.insert(pCon, orderPropD);
         }
         String consNote = "The order consolidates orders: "+replOrderNums;
         OrderPropertyData orderPropD = OrderPropertyData.createValue();
         orderPropD.setOrderId(orderD.getOrderId());
         orderPropD.setShortDesc("Consolidation");
         orderPropD.setValue(consNote);
         orderPropD.
            setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
         orderPropD.
            setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
         orderPropD.setAddBy(propUserName);
         orderPropD.setModBy(propUserName);
         pBaton.addOrderPropertyData(orderPropD);
      } else if(RefCodeNames.ORDER_TYPE_CD.SPLIT.equals(orderD.getOrderTypeCd())) {
    	  if(replacedOrderIds==null || replacedOrderIds.size()==0) {
    		  throw new PipelineException("No replaced orders for split order");
    	  }    	  
		  Integer replOrderIdI = (Integer) replacedOrderIds.get(0);
		  int replOrderId = replOrderIdI.intValue();
		  OrderData replOrderD = OrderDataAccess.select(pCon,replOrderId);
		  
		  OrderAssocData orderAssocD = OrderAssocData.createValue();
		  orderAssocD.setOrder1Id(orderId);
		  orderAssocD.setOrder2Id(replOrderId);
		  orderAssocD.setOrderAssocCd(RefCodeNames.ORDER_ASSOC_CD.SPLIT);
		  orderAssocD.
		  setOrderAssocStatusCd(RefCodeNames.ORDER_ASSOC_STATUS_CD.ACTIVE);
		  orderAssocD.setAddBy(propUserName);
		  orderAssocD.setModBy(propUserName);
		  OrderAssocDataAccess.insert(pCon, orderAssocD);
		  OrderPropertyData orderPropD = OrderPropertyData.createValue();
		  orderPropD.setOrderId(orderId);
		  orderPropD.setShortDesc("Split Order");
		  orderPropD.setValue("The order was split from cancelled order #: "+ replOrderD.getOrderNum());
		  orderPropD.
		  setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
		  orderPropD.
		  setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
		  orderPropD.setAddBy(propUserName);
		  orderPropD.setModBy(propUserName);
		  OrderPropertyDataAccess.insert(pCon, orderPropD);	
      }
      else if (replacedOrderIds != null && !replacedOrderIds.isEmpty()) {
        //Replaced orders
        String replOrderNums = null;
        for(Iterator iter = replacedOrderIds.iterator(); iter.hasNext(); ) {
          Integer replOrderIdI = (Integer) iter.next();
          int replOrderId = replOrderIdI.intValue();
          OrderData replOrderD = OrderDataAccess.select(pCon,replOrderId);
          replOrderD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.CANCELLED);
          OrderDataAccess.update(pCon,replOrderD);
          OrderAssocData orderAssocD = OrderAssocData.createValue();
          orderAssocD.setOrder1Id(replOrderId);
          orderAssocD.setOrder2Id(orderId);
          orderAssocD.setOrderAssocCd(RefCodeNames.ORDER_ASSOC_CD.REPLACED);
          orderAssocD.
              setOrderAssocStatusCd(RefCodeNames.ORDER_ASSOC_STATUS_CD.ACTIVE);
          orderAssocD.setAddBy(userName);
          orderAssocD.setModBy(userName);
          OrderAssocDataAccess.insert(pCon, orderAssocD);
          if(replOrderNums==null) {
            replOrderNums = replOrderD.getOrderNum();
          } else {
            replOrderNums += ", "+replOrderD.getOrderNum();
          }
          OrderPropertyData orderPropD = OrderPropertyData.createValue();
          orderPropD.setOrderId(replOrderId);
          orderPropD.setShortDesc("Replacement");
          orderPropD.setValue("The order was replaced by the order#: "+
                  orderD.getOrderNum());
          orderPropD.
             setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
          orderPropD.
             setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
          orderPropD.setAddBy(userName);
          orderPropD.setModBy(userName);
          OrderPropertyDataAccess.insert(pCon, orderPropD);
        }
        DBCriteria dbc = new DBCriteria();
        dbc.addOneOf(OrderMetaDataAccess.ORDER_ID,replacedOrderIds);
        dbc.addEqualTo(OrderMetaDataAccess.NAME,Order.MODIFICATION_STARTED);
        OrderMetaDataAccess.remove(pCon,dbc);

        String consNote = "The order replaces order#: "+replOrderNums;
        OrderPropertyData orderPropD = OrderPropertyData.createValue();
        orderPropD.setOrderId(orderD.getOrderId());
        orderPropD.setShortDesc("Replacement");
        orderPropD.setValue(consNote);
        orderPropD.
           setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
        orderPropD.
           setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        orderPropD.setAddBy(userName);
        orderPropD.setModBy(userName);
        OrderPropertyDataAccess.insert(pCon, orderPropD);
        //Remove replaced orders from clw_inventory_order_log
        dbc = new DBCriteria();
        dbc.addOneOf(InventoryOrderLogDataAccess.ORDER_ID, replacedOrderIds);
        InventoryOrderLogDataVector iolDV =
                InventoryOrderLogDataAccess.select(pCon,dbc);
        if(iolDV.size()>0) {
          InventoryOrderLogData iolD = (InventoryOrderLogData) iolDV.get(0);
          iolD.setOrderId(orderD.getOrderId());
          iolD.setModBy(userName);
          InventoryOrderLogDataAccess.update(pCon,iolD);
          if(iolDV.size()>1) {
            dbc.addNotEqualTo(InventoryOrderLogDataAccess.INVENTORY_ORDER_LOG_ID,
                    iolD.getInventoryOrderLogId());
            InventoryOrderLogDataAccess.remove(pCon,dbc);
          }
        }
      }
     }  else {
       OrderDataAccess.update(pCon, orderD);
     }
     //Save order items
     OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
     for(int ii=0; ii<orderItemDV.size(); ii++) {
       OrderItemData oiD = (OrderItemData) orderItemDV.get(ii);
       oiD.setOrderId(orderId);
       int orderItemId = oiD.getOrderItemId();
       if(newOrder || orderItemId<=0) {
         oiD = OrderItemDataAccess.insert(pCon, oiD);
         orderItemId = oiD.getOrderItemId();
       } else {
         OrderItemDataAccess.update(pCon, oiD);
       }
       OrderItemMetaDataVector oimDV =
                     pBaton.getOrderItemMetaDataVector(oiD.getOrderLineNum());
       if(oimDV!=null) {
         for(int jj=0; jj<oimDV.size(); jj++) {
           OrderItemMetaData oimD = (OrderItemMetaData) oimDV.get(jj);
           oimD.setOrderItemId(orderItemId);
           int orderItemMetaId = oimD.getOrderItemMetaId();
           if (newOrder || orderItemMetaId<=0) {
             oimD = OrderItemMetaDataAccess.insert(pCon, oimD);
           } else {
             OrderItemMetaDataAccess.update(pCon, oimD);
           }
         }
       }
     }

     //Save addresses
     OrderAddressData billTo = pBaton.getBillToData();
     if(billTo!=null) {
        billTo.setOrderId(orderId);
        int oaId = billTo.getOrderAddressId();
        if(newOrder || oaId<=0){
          OrderAddressDataAccess.insert(pCon, billTo);
        } else {
          OrderAddressDataAccess.update(pCon, billTo);
        }
     }
     OrderAddressData shipTo = null;
     PreOrderAddressDataVector preOrderAddresses = pBaton.getPreOrderAddressDataVector();
     Iterator it = preOrderAddresses.iterator();
     PreOrderAddressData preOrderAD;
     while (it.hasNext()) {
        preOrderAD = (PreOrderAddressData) it.next();
        if (RefCodeNames.ADDRESS_TYPE_CD.SHIPPING.equals(preOrderAD.getAddressTypeCd())) {
            shipTo = OrderAddressData.createValue();
            shipTo.setAddress1(preOrderAD.getAddress1());
            shipTo.setAddress2(preOrderAD.getAddress2());
            shipTo.setAddress3(preOrderAD.getAddress3());
            shipTo.setCity(preOrderAD.getCity());
            shipTo.setStateProvinceCd(preOrderAD.getStateProvinceCd());
            shipTo.setPostalCode(preOrderAD.getPostalCode());
            shipTo.setCountryCd(preOrderAD.getCountryCd());
            shipTo.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
        break;
        }
     }
     if (shipTo == null) {
        shipTo = pBaton.getShipToData();
     }
     if(shipTo!=null) {
        /* it is no need to cut off site name to 30 symbols
        String t2 = shipTo.getShortDesc();
        if ( t2!= null && t2.length() > 30 ) {
       t2 = t2.substring(0,30);
       shipTo.setShortDesc(t2);
        } */
        shipTo.setOrderId(orderId);
        int oaId = shipTo.getOrderAddressId();
        if(newOrder || oaId<=0){
          OrderAddressDataAccess.insert(pCon, shipTo);
        } else {
          OrderAddressDataAccess.update(pCon, shipTo);
        }
     }
     OrderAddressData custShipTo = pBaton.getCustShipToData();
     if(custShipTo!=null) {
        custShipTo.setOrderId(orderId);
        int oaId = custShipTo.getOrderAddressId();
        if(newOrder || oaId<=0){
          OrderAddressDataAccess.insert(pCon, custShipTo);
        } else {
          OrderAddressDataAccess.update(pCon, custShipTo);
        }
     }
     OrderAddressData custBillTo = pBaton.getCustBillToData();
     if(custBillTo!=null) {
        custBillTo.setOrderId(orderId);
        int oaId = custBillTo.getOrderAddressId();
        if(newOrder || oaId<=0){
          OrderAddressDataAccess.insert(pCon, custBillTo);
        } else {
          OrderAddressDataAccess.update(pCon, custBillTo);
        }
     }
     //Save order properties
     OrderPropertyDataVector orderPropertyDV = pBaton.getOrderPropertyDataVector();
     if(orderPropertyDV!=null) {
       for(int ii=0; ii<orderPropertyDV.size(); ii++) {
         OrderPropertyData opD = (OrderPropertyData) orderPropertyDV.get(ii);
         if(!Utility.isSet(opD.getOrderPropertyStatusCd())) {
           opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
         }
         opD.setOrderId(orderId);
         int opId = opD.getOrderPropertyId();
         if(newOrder || opId<=0) {
           opD = OrderPropertyDataAccess.insert(pCon, opD);
         } else {
           OrderPropertyDataAccess.update(pCon, opD);
         }
       }
     }
     //Save error notes
     OrderPropertyDataVector errorDV = pBaton.getErrorsAsProperties();

     String shortDesc = orderD.getOrderSourceCd() +
                             " Order Exception";
     for(int ii=0; ii<errorDV.size(); ii++){
       OrderPropertyData opD = (OrderPropertyData) errorDV.get(ii);
       opD.setOrderId(orderId);
       opD.setAddBy(propUserName);
       opD.setModBy(propUserName);
       if(!Utility.isSet(opD.getShortDesc())) {
         opD.setShortDesc(shortDesc);
       }
       opD = OrderPropertyDataAccess.insert(pCon,opD);
       pBaton.addOrderPropertyData(opD);
     }
     pBaton.setErrors(new HashMap());
     //Save order meta data
    OrderMetaDataVector omDV = pBaton.getOrderMetaDataVector();
    if(omDV!=null){
      for(int ii =0; ii<omDV.size(); ii++) {
        OrderMetaData omD = (OrderMetaData) omDV.get(ii);
        if(omD.getValue()==null || omD.getValue().trim().length()==0) {
            log.info("PipelineUtil.saveNewOrder. Error *********** empty value in OrderMetaObject: "+omD);
        }
        omD.setOrderId(orderId);
        omD.setModBy(propUserName);
        int omId = omD.getOrderMetaId();
        if(newOrder || omId<=0) {
          omD.setAddBy(propUserName);
          omD = OrderMetaDataAccess.insert(pCon, omD);
        } else {
          OrderMetaDataAccess.update(pCon, omD);
        }
      }
    }
    //Save workflow queue
    WorkflowQueueDataVector workflowQueueDV = pBaton.getWorkflowQueueDataVector();
    if(workflowQueueDV!=null) {
      for(int ii=0; ii<workflowQueueDV.size(); ii++) {
        WorkflowQueueData wqD = (WorkflowQueueData) workflowQueueDV.get(ii);
        if(!Utility.isSet(wqD.getWorkflowRoleCd())) {
          wqD.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
        }
        wqD.setOrderId(orderId);
        wqD.setAddBy(propUserName);
        wqD.setModBy(propUserName);
        int wqId = wqD.getWorkflowQueueId();
        if(newOrder || wqId<=0){
          wqD = WorkflowQueueDataAccess.insert(pCon,wqD);
        } else {
          WorkflowQueueDataAccess.update(pCon,wqD);
        }
      }
   }
    //Return
    return pBaton;
    }

    public static OrderPipelineBaton updateOrder(OrderPipelineBaton pBaton,
                OrderPipelineActor pActor,
                Connection pCon,
                APIAccess pFactory)
    throws Exception
    {
    OrderData orderD = pBaton.getOrderData();
    int orderId = orderD.getOrderId();
    if(orderId<=0) {
      return pBaton;
    }
    //Update order meta
    OrderMetaDataVector orderMetaDV = pBaton.getOrderMetaDataVector();
    if(orderMetaDV==null) orderMetaDV = new OrderMetaDataVector();
    DBCriteria dbc = new DBCriteria();
    dbc.addEqualTo(OrderMetaDataAccess.ORDER_ID,orderId);
    OrderMetaDataVector dbOrderMetaDV = OrderMetaDataAccess.select(pCon,dbc);
    OrderMetaDataVector updOrderMetaDV = new OrderMetaDataVector();

    for(Iterator iter=dbOrderMetaDV.iterator(); iter.hasNext();) {
      OrderMetaData orderMetaD = (OrderMetaData) iter.next();
      boolean foundFl = false;
      for(Iterator iter1=orderMetaDV.iterator(); iter1.hasNext();) {
        OrderMetaData omD = (OrderMetaData) iter1.next();
        if(omD.getOrderMetaId()==orderMetaD.getOrderMetaId()) {
          if(!orderMetaD.getName().equals(omD.getName()) ||
             !Utility.isEqual(orderMetaD.getValue(),omD.getValue()) ||
              orderMetaD.getValueNum()!=omD.getValueNum()) {
            if(omD.getValue()==null && omD.getValueNum()==0 && omD.getName()==null) {
              //delete
              OrderMetaDataAccess.remove(pCon,omD.getOrderMetaId());
            } else {
              orderMetaD.setName(omD.getName());
              orderMetaD.setValue(omD.getValue());
              orderMetaD.setValueNum(omD.getValueNum());
              orderMetaD.setModBy(pBaton.getUserName());
              OrderMetaDataAccess.update(pCon,orderMetaD);
              updOrderMetaDV.add(orderMetaD);
            }
            iter1.remove();
            break;
          }
          foundFl = true;
        }
      }
    }
    for(Iterator iter = orderMetaDV.iterator(); iter.hasNext();) {
      OrderMetaData omD = (OrderMetaData) iter.next();
      if(omD.getOrderMetaId()==0) {
        omD.setOrderId(orderId);
        omD.setAddBy(pBaton.getUserName());
        omD.setModBy(pBaton.getUserName());
        omD = OrderMetaDataAccess.insert(pCon,omD);
        updOrderMetaDV.add(omD);
      }
    }
    pBaton.setOrderMetaDataVector(updOrderMetaDV);

     //Save new errors
     OrderPropertyDataVector errorPropertyDV = pBaton.getErrorsAsProperties();
     if(errorPropertyDV!=null) {
       for(int ii=0; ii<errorPropertyDV.size(); ii++) {
         OrderPropertyData opD = (OrderPropertyData) errorPropertyDV.get(ii);
         if(opD.getOrderPropertyId()<=0) {
           if(!Utility.isSet(opD.getOrderPropertyStatusCd())) {
             opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
           }
           opD.setOrderId(orderId);
           opD = OrderPropertyDataAccess.insert(pCon, opD);
		   pBaton.addOrderPropertyData(opD);
         }
       }
     }

     //Save new properties
     OrderPropertyDataVector orderPropertyDV = pBaton.getOrderPropertyDataVector();
     if(orderPropertyDV!=null) {
       for(int ii=0; ii<orderPropertyDV.size(); ii++) {
         OrderPropertyData opD = (OrderPropertyData) orderPropertyDV.get(ii);
         if(opD.getOrderPropertyId()<=0) {
           if(!Utility.isSet(opD.getOrderPropertyStatusCd())) {
             opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
           }
           opD.setOrderId(orderId);
           opD = OrderPropertyDataAccess.insert(pCon, opD);
         }
       }
     }
     pBaton.setErrors(new HashMap());

    //Update for new order status
    //String orderStatusCd = orderD.getOrderStatusCd();
    String orderStatusCd = pBaton.getOrderStatus();
    OrderData bdOrderD = OrderDataAccess.select(pCon,orderD.getOrderId());
    bdOrderD.setModBy(pBaton.getUserName());
    bdOrderD.setWorkflowInd(orderD.getWorkflowInd());
    if (orderStatusCd.equals(RefCodeNames.ORDER_STATUS_CD.ORDERED)) {
       bdOrderD.setOrderStatusCd(orderStatusCd);
       bdOrderD.setExceptionInd("N");
       OrderDataAccess.update(pCon,bdOrderD);
         // String updateSql = " update " + OrderDataAccess.CLW_ORDER +
         //                    " set " + OrderDataAccess.ORDER_STATUS_CD +
         //                    " = '" + orderStatusCd + "' , " +
         //                    OrderDataAccess.EXCEPTION_IND + " = 'N' " +
         //                    " where " + OrderDataAccess.ORDER_ID + " = " +
         //                    orderId;
         // Statement stmt = pCon.createStatement();
         // stmt.executeQuery(updateSql);
         // stmt.close();

          String updateSql2 = " update " +
                              OrderItemDataAccess.CLW_ORDER_ITEM +
                              " set " +
                              OrderItemDataAccess.ORDER_ITEM_STATUS_CD +
                              " = '" +
                              RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_ERP_PO +
                              "' where " + OrderDataAccess.ORDER_ID +
                              " = " +
                              orderId +
                              " and " +
                              OrderItemDataAccess.ORDER_ITEM_STATUS_CD +
                              " not in ( " + "'" +
                              RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_ERP_PO + "'," +
                            "'" + RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED +
                            "'" + ")";
          Statement stmt2 = pCon.createStatement();
          stmt2.executeUpdate(updateSql2);
          stmt2.close();
      } else if (orderStatusCd.equals(
                         RefCodeNames.ORDER_STATUS_CD.PROCESS_ERP_PO)) {
         if(RefCodeNames.ORDER_STATUS_CD.ORDERED.equals(bdOrderD.getOrderStatusCd())){
            bdOrderD.setOrderStatusCd(orderStatusCd);
            bdOrderD.setExceptionInd("N");
            OrderDataAccess.update(pCon,bdOrderD);
         }
         // String updateSql = " update " + OrderDataAccess.CLW_ORDER +
         //                    " set " + OrderDataAccess.ORDER_STATUS_CD +
         //                    " = '" + orderStatusCd + "' , " +
         //                    OrderDataAccess.EXCEPTION_IND + " = 'N' " +
         //                    " where " + OrderDataAccess.ORDER_ID + " = " +
         //                    orderId + " and " +
         //                    OrderDataAccess.ORDER_STATUS_CD + " = '" +
         //                    RefCodeNames.ORDER_STATUS_CD.ORDERED + "'";
         // Statement stmt = pCon.createStatement();
         // stmt.executeQuery(updateSql);
         // stmt.close();

          String updateSql2 = " update " +
                              OrderItemDataAccess.CLW_ORDER_ITEM +
                              " set " +
                              OrderItemDataAccess.ORDER_ITEM_STATUS_CD +
                              " = '" +
                              RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_ERP_PO +
                              "' where " + OrderDataAccess.ORDER_ID +
                              " = " +
                              orderId +
                              " and " +
                              OrderItemDataAccess.ORDER_ITEM_STATUS_CD +
                              " not in ( " + "'" +
                              RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_ERP_PO + "'," +
                            "'" + RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED +
                            "'" + ")";
          Statement stmt2 = pCon.createStatement();
          stmt2.executeUpdate(updateSql2);
          stmt2.close();
      } else if (orderStatusCd.equals(RefCodeNames.ORDER_STATUS_CD.INVOICED)) {

          // Orders can only update to this status if all the
          // order items have been cancelled or invoiced.
          String checkSql = "select count(*) from " +
                            OrderItemDataAccess.CLW_ORDER_ITEM +
                            " where " + OrderDataAccess.ORDER_ID + " = " +
                            orderId + " and " +
                            OrderItemDataAccess.ORDER_ITEM_STATUS_CD +
                            " not in ( " + "'" +
                            RefCodeNames.ORDER_ITEM_STATUS_CD.INVOICED + "'," +
                            "'" + RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED +
                            "'" + ")";
          Statement stmt = pCon.createStatement();
          ResultSet rs = stmt.executeQuery(checkSql);
          int rescount = 0;

          if (rs.next()) {
              rescount = rs.getInt(1);
          }

          stmt.close();

          if (rescount == 0) {

              // No items pending any processing.
              String updateSql = " update " + OrderDataAccess.CLW_ORDER +
                                 " set " +
                                 OrderDataAccess.ORDER_STATUS_CD + " = '" +
                                 orderStatusCd + "' where " +
                                 OrderDataAccess.ORDER_ID + " = " +
                                 orderId + " and " +
                                 OrderDataAccess.ORDER_STATUS_CD + " = '" +
                                 RefCodeNames.ORDER_STATUS_CD.PROCESS_ERP_PO +
                                 "'";
              Statement stmt2 = pCon.createStatement();
              stmt2.executeUpdate(updateSql);
              stmt2.close();
          }
      } else if (orderStatusCd.equals(
                         RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW)) {
            bdOrderD.setOrderStatusCd(orderStatusCd);
            bdOrderD.setExceptionInd("Y");
            OrderDataAccess.update(pCon,bdOrderD);

          //String updateSql = " update " + OrderDataAccess.CLW_ORDER +
          //                   " set " + OrderDataAccess.ORDER_STATUS_CD +
          //                   " = '" + orderStatusCd + "' , " +
          //                   OrderDataAccess.EXCEPTION_IND + " = 'Y' " +
          //                   " where " + OrderDataAccess.ORDER_ID + " = " +
          //                   orderId;
          //Statement stmt = pCon.createStatement();
          //stmt.executeQuery(updateSql);
          //stmt.close();
      } else {
            bdOrderD.setOrderStatusCd(orderStatusCd);
            OrderDataAccess.update(pCon,bdOrderD);

          //String updateSql = " update " + OrderDataAccess.CLW_ORDER +
          //                   " set " + OrderDataAccess.ORDER_STATUS_CD +
          //                   " = '" + orderStatusCd + "'" + " where " +
          //                   OrderDataAccess.ORDER_ID + " = " + orderId;
          //Statement stmt = pCon.createStatement();
          //stmt.executeQuery(updateSql);
          //stmt.close();
      }

    //Save workflow queue
    WorkflowQueueDataVector workflowQueueDV = pBaton.getWorkflowQueueDataVector();
    if(workflowQueueDV!=null) {
      for(int ii=0; ii<workflowQueueDV.size(); ii++) {
        WorkflowQueueData wqD = (WorkflowQueueData) workflowQueueDV.get(ii);
        int wqId = wqD.getWorkflowQueueId();
        if(wqId<=0){
          if(!Utility.isSet(wqD.getWorkflowRoleCd())) {
            wqD.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
          }
          wqD.setOrderId(orderId);
          wqD.setAddBy(pBaton.getUserName());
          wqD.setModBy(pBaton.getUserName());
          wqD = WorkflowQueueDataAccess.insert(pCon,wqD);
        }
      }
   }
   //update order status
   OrderData oD = OrderDataAccess.select(pCon,orderId);
   pBaton.setOrderStatus(oD.getOrderStatusCd());
   pBaton.getOrderData().setOrderStatusCd(oD.getOrderStatusCd());

    //Return
    return pBaton;
    }

//STJ-5604
public static String translateMessage( String pMessageKey,
        String pLocalCd, 
        Object arg0, String arg0TypeCd,
        Object arg1, String arg1TypeCd,
        Object arg2, String arg2TypeCd,
        Object arg3, String arg3TypeCd) {
	

	
 //log.info("PipelineUtil translateMessage 1 pMessageKey="+pMessageKey+" pLocalCd="+pLocalCd+" arg0="+arg0+
//		   " arg0TypeCd="+arg0TypeCd+" arg1="+arg1+" arg1TypeCd="+arg1TypeCd+" arg2="+arg2+
//		   " arg2TypeCd="+arg2TypeCd+" arg3="+arg3+" arg3TypeCd="+arg3TypeCd);
  if ( arg0TypeCd != null && arg0TypeCd.equals(RefCodeNames.PIPELINE_MESSAGE_ARG_CD.MESSAGE_KEY) ) {
    //return translateMessage(pMessageKey, pLocalCd, null, null, null, null, null, null, null, null) + " - " +
   //   translateMessage(arg0.toString(), pLocalCd, arg0,  arg0TypeCd, arg1, arg1TypeCd, arg2, arg2TypeCd, arg3, arg3TypeCd);
	  return translateMessage(pMessageKey, pLocalCd, arg0,  null, arg1, arg1TypeCd, arg2, arg2TypeCd, arg3, arg3TypeCd) + " - " +
	     translateMessage(arg0.toString(), pLocalCd, arg1, arg1TypeCd, arg2, arg2TypeCd, arg3, arg3TypeCd, null, null);	  
  }
  if (pLocalCd == null) {
    pLocalCd = "en_US";
  }
  
  //STJ-5487-removed code to fetch messages from Database
	Object args[] = new Object[] {arg0, arg1, arg2, arg3};
	String argTypes[] = new String[] {arg0TypeCd, arg1TypeCd, arg2TypeCd, arg3TypeCd};  

  
 // log.info("PipelineUtil translateMessage 2 pMessageKey="+pMessageKey+" pLocalCd="+pLocalCd+" arg0="+arg0+
//		   " arg0TypeCd="+arg0TypeCd+" arg1="+arg1+" arg1TypeCd="+arg1TypeCd+" arg2="+arg2+
//		   " arg2TypeCd="+arg2TypeCd+" arg3="+arg3+" arg3TypeCd="+arg3TypeCd);  
  
  Locale locale = new Locale(pLocalCd.substring(0, 2), pLocalCd.substring(3, 5));
 // log.info("PipelineUtil translateMessage 2 pMessageKey="+pMessageKey);
  return I18nUtil.getMessage(locale, pMessageKey, args, argTypes);
}

public static String catStr(Object val, int length) {
  String res = null;
  if (val != null) {
    res = val.toString();
    if (res.length() > length) {
      res = res.substring(0, length);
    }
  }
  return res;
}


    public void processPipeline(Connection pCon, APIAccess pFactory, Hashtable pParametrs, String pPipelineType)
            throws RemoteException {
        try {
            MultiOrderPipelineActor pipelineActor = new MultiOrderPipelineActor();
            MultiOrderPipelineBaton mBaton = createMultiOrderPipelineBaton(pCon,pFactory,pParametrs,pPipelineType);
            pipelineActor.processPipeline(mBaton,pPipelineType,pCon,pFactory);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }

    private MultiOrderPipelineBaton createMultiOrderPipelineBaton(Connection pCon, APIAccess pFactory, Hashtable parametrs, String pPipelineType) throws Exception {


        IdVector orderIds=(IdVector)parametrs.get("orderNum");
        Date begDate= (Date) parametrs.get("bDate");
        Date endDate= (Date) parametrs.get("eDate");
        String bDate = null;
        String eDate =null;
        SimpleDateFormat propertySDF=new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        SimpleDateFormat sqlSDF=new SimpleDateFormat("MM/dd/yyyy");
        String strDate[]=new String[2];
        if((orderIds==null) ||(orderIds!=null&&orderIds.size()==0))
        {
            try{
                if(begDate == null||endDate==null)
                {
                    PropertyService propertyServiceBean= pFactory.getPropertyServiceAPI();
                    String property = propertyServiceBean.getProperty(pPipelineType);
                    StringTokenizer st=new StringTokenizer(property,",");
                    int i=0;
                    if(st.countTokens()==1)
                    {
                        if(st.hasMoreElements()){
                            strDate[0]=(String)st.nextElement();

                            Calendar cal=Calendar.getInstance();
                            try {
                                begDate=propertySDF.parse(strDate[0]);
                            }catch (ParseException e) {
                                throw new Exception("The data to be converted to date format was incorrect." +
                                        "\" Input string :[ "+strDate[0]+" ]\"");
                            }
                            endDate=new Date();

                        }
                    }
                    else if (st.countTokens()==2)
                    {
                        while(st.hasMoreElements())
                        {
                            strDate[i++]= (String) st.nextElement();
                        }
                        try{
                            begDate=propertySDF.parse(strDate[0]);
                            endDate=propertySDF.parse(strDate[1]);
                        }catch (ParseException e) {
                            throw new Exception("The data range to be converted to date format was incorrect." +
                                    "\"Input string :[ "+strDate[0]+","+strDate[1]+" ]\"");
                        }
                    }
                }
            }
            catch(Exception e){
                throw new Exception(e.getMessage());
            }

            bDate=sqlSDF.format(begDate);
            eDate=sqlSDF.format(endDate);
            parametrs.put("bDate",begDate);
            parametrs.put("eDate",endDate);

            String sql="select  distinct o.order_id from clw_order o, clw_order_item oi, \n" +
                    "( \n" +
                    "select bus_entity_id, max(to_date(to_char(eff_date,'mm/dd/yyyy'),'mm/dd/yyyy')) eff_date \n" +
                    "from clw_fiscal_calender \n" +
                    "where eff_date < to_date('"+eDate+"','mm/dd/yyyy') \n" +
                    "group  by bus_entity_id \n" +
                    ") bd \n" +
                    "where account_id = bd.bus_entity_id \n" +
                    "and nvl(revised_order_date, original_order_date) >= eff_date \n" +
                    "and o.mod_date >= to_date('"+bDate+"','mm/dd/yyyy') \n" +
                    "and o.mod_date <= to_date('"+eDate+"','mm/dd/yyyy')\n" +
                    "and o.order_id = oi.order_id\n" +
                    "and oi.cost_center_id >0\n" +
                    "\n" +
                    "union \n" +
                    "\n" +
                    "select  distinct o.order_id from clw_order o, clw_order_item oi, \n" +
                    "( \n" +
                    "select bus_entity_id, max(to_date(to_char(eff_date,'mm/dd/yyyy'),'mm/dd/yyyy')) eff_date \n" +
                    "from clw_fiscal_calender \n" +
                    "where eff_date < to_date('"+eDate+"','mm/dd/yyyy') \n" +
                    "group  by bus_entity_id \n" +
                    ") bd \n" +
                    "where account_id = bd.bus_entity_id \n" +
                    "and nvl(revised_order_date, original_order_date) >= eff_date \n" +
                    "and oi.mod_date >= to_date('"+bDate+"','mm/dd/yyyy') \n" +
                    "and oi.mod_date <= to_date('"+eDate+"','mm/dd/yyyy')\n" +
                    "and oi.cost_center_id>0\n" +
                    "and o.order_id = oi.order_id\n" +
                    "\n" +
                    "\n" +
                    "union\n" +
                    "\n" +
                    "select distinct o.order_id from clw_order o, clw_order_item oi,\n" +
                    " (   \n" +
                    " select distinct cs.item_id, contr.contract_id\n" +
                    "   from clw_catalog_structure cs, clw_contract contr\n" +
                    "  where 1=1\n" +
                    "    and cs.mod_date >= TO_DATE ('"+bDate+"', 'mm/dd/yyyy')\n" +
                    "    AND cs.mod_date <= TO_DATE ('"+eDate+"', 'mm/dd/yyyy')\n" +
                    "    and cs.catalog_id = contr.catalog_id\n" +
                    " ) catstr,    \n" +
                    " (\n" +
                    " select bus_entity_id, max(to_date(to_char(eff_date,'mm/dd/yyyy'),'mm/dd/yyyy')) eff_date \n" +
                    " from clw_fiscal_calender \n" +
                    " where eff_date < to_date('"+eDate+"','mm/dd/yyyy') \n" +
                    " group  by bus_entity_id \n" +
                    " ) fc\n" +
                    "  where 1=1\n" +
                    " and o.order_id = oi.order_id\n" +
                    " and o.contract_id = catstr.contract_id\n" +
                    " and oi.item_id = catstr.item_id\n" +
                    " and  nvl(o.revised_order_date, o.original_order_date) >= fc.eff_date \n" +
                    " and fc.bus_entity_id = o.account_id\n" +
                    " \n" +
                    " union \n" +
                    " \n" +
                    "select distinct o.order_id from clw_order_item oi, clw_order o,\n" +
                    " (\n" +
                    " select cc.cost_center_id from clw_cost_center_assoc cca,clw_cost_center cc \n" +
                    "  where 1=1\n" +
                    "    and cc.cost_center_id=cca.cost_center_id\n" +
                    "    and cca.mod_date >= TO_DATE ('"+bDate+"', 'mm/dd/yyyy')\n" +
                    "    and cca.mod_date <= TO_DATE ('"+eDate+"', 'mm/dd/yyyy') \n" +
                    "    and cc.cost_center_status_cd<>'INACTIVE'\n" +
                    "  union \n" +
                    "  select cost_center_id from clw_cost_center \n" +
                    "                          where mod_date >= to_date('"+bDate+"','mm/dd/yyyy') \n" +
                    "                          and mod_date <= to_date('"+eDate+"','mm/dd/yyyy')   \n" +
                    "    and cost_center_status_cd<>'INACTIVE'\n" +
                    " ) costs,\n" +
                    " (\n" +
                    " select bus_entity_id, max(to_date(to_char(eff_date,'mm/dd/yyyy'),'mm/dd/yyyy')) eff_date \n" +
                    "  from clw_fiscal_calender \n" +
                    " where eff_date < to_date('"+eDate+"','mm/dd/yyyy') \n" +
                    " group  by bus_entity_id \n" +
                    " ) fc\n" +
                    "where o.order_id = oi.order_id\n" +
                    "and oi.cost_center_id = costs.cost_center_id\n" +
                    "and  nvl(o.revised_order_date, o.original_order_date) >= fc.eff_date \n" +
                    "and fc.bus_entity_id = o.account_id\n" +
                    "\n" +
                    "union\n" +
                    "\n" +
                    "select o.order_id from clw_fiscal_calender fc, clw_order o \n" +
                    "where fc.mod_date >= to_date('"+bDate+"','mm/dd/yyyy') \n" +
                    "and fc.mod_date <= to_date('"+eDate+"','mm/dd/yyyy')   \n" +
                    "and o.account_id = fc.bus_entity_id\n" +
                    "and  nvl(o.revised_order_date, o.original_order_date) >= fc.eff_date";

            Statement stmt = pCon.createStatement();
            long startQuery=System.currentTimeMillis();
            ResultSet rs = stmt.executeQuery(sql);
            orderIds=new IdVector();
            while(rs.next())
            {
                orderIds.add(new Integer(rs.getInt(1)));
            }
        }

        OrderDataVector resOrderDV=new OrderDataVector();
        for(int j=0;j<=(int)(orderIds.size()/1000);j++)
        {
            IdVector nIdV=new IdVector();
            for(int i=0;i <(j==(int)(orderIds.size()/1000)?(orderIds.size()%1000):1000);i++)
            {

                nIdV.add(orderIds.get((j*1000)+i));

            }
            DBCriteria crit=new DBCriteria();
            crit.addOneOf(OrderDataAccess.ORDER_ID,nIdV);
            OrderDataVector orderDV=OrderDataAccess.select(pCon,crit);
            resOrderDV.addAll(orderDV);

        }

        MultiOrderPipelineBaton mMBaton=new MultiOrderPipelineBaton();

        mMBaton.setParametrs(parametrs);
        mMBaton.setOrderDataVector(resOrderDV);
        mMBaton.setStepNum(0);
        mMBaton.setBypassOptional(false);
        mMBaton.setPipelineTypeCd(pPipelineType);
        mMBaton.setUserName(pPipelineType);
        return mMBaton;

            }

    public static boolean isSimpleServiceOrder(ItemDataVector idv) {
        boolean isService = false;
        boolean isOther = false;
        if (idv != null) {
            Iterator it = idv.iterator();
            while (it.hasNext()) {
                ItemData iD = (ItemData) it.next();
                if (RefCodeNames.ITEM_TYPE_CD.SERVICE.equals(iD.getItemTypeCd())) {
                    isService = true;
                } else {
                    isOther = true;
                }

                if (isService == isOther) return false;
            }
        }
        return isService;
    }

    public static boolean isSimpleProductOrder(ItemDataVector idv) {
        boolean isProduct = false;
        boolean isOther = false;
        if (idv != null) {
            Iterator it = idv.iterator();
            while (it.hasNext()) {
                ItemData iD = (ItemData) it.next();
                if (RefCodeNames.ITEM_TYPE_CD.PRODUCT.equals(iD.getItemTypeCd())) {
                    isProduct = true;
                } else {
                    isOther = true;
                }

                if (isProduct == isOther) return false;
            }
        }
        return isProduct;
    }
    // Sends Pending Approval Email when order was stoped for review
    public static void sendPendingApprovalEmail(Connection pCon, OrderPipelineBaton pBaton) throws Exception{
      APIAccess factory = new APIAccess();
      Workflow workflowEjb = factory.getWorkflowAPI();
      OrderAddressData addressData = pBaton.getShipToData(); //OrderDAO.getShippingAddress(pCon, oD.getOrderId());
      OrderData oD = pBaton.getOrderData();
      oD.setOrderStatusCd(pBaton.getOrderStatus());
      OrderPropertyData note = workflowEjb.sendPendingApprovalEmail(pCon, oD, addressData);
      //STJ-6003
      log.info("sendPendingApprovalEmail() ===> note ="+ note);
      if (note != null){
    	  pBaton.setOrderStatus(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW); 
          oD.setOrderStatusCd(pBaton.getOrderStatus());
          pBaton.addOrderPropertyData(note);
          if(oD.getOrderId() > 0){
              OrderDataAccess.update(pCon,oD);
          }
      }
    }

    // Sends Pending Approval Email from Breake Point Workflow when order needs to be stoped for review
    public static void sendPendingApprovalEmail(OrderPipelineBaton pBaton, HashSet pSendToUsers) throws Exception{
      APIAccess factory = new APIAccess();
      Workflow workflowEjb = factory.getWorkflowAPI();
      OrderAddressData addressData = pBaton.getShipToData(); //OrderDAO.getShippingAddress(pCon, oD.getOrderId());
      OrderData oD = pBaton.getOrderData();
      oD.setOrderStatusCd(pBaton.getOrderStatus());
      workflowEjb.sendPendingApprovalEmail( oD, addressData, pSendToUsers);
    }

    public static IdVector getUsersForGroup( OrderPipelineBaton pBaton, int pGroupId) throws Exception{
      APIAccess factory = new APIAccess();
      User userEjb = factory.getUserAPI();
      OrderData oD = pBaton.getOrderData();
      int siteId = oD.getSiteId();
      IdVector groupIds = new IdVector();
      groupIds.add(new Integer(pGroupId));
      IdVector usersV = userEjb.getUsersForGroupSiteRights( siteId, groupIds, null);
      return usersV;
    }

    public static HashSet getUsersToSendPendingApprovalEmais( OrderPipelineBaton pBaton)  throws Exception{
     //prepare HashMap of approvers for triggered rules
     HashMap approversHM = new  HashMap();
     HashMap approverGroups = pBaton.getApproverGroupsHM();

     OrderPropertyDataVector errors = pBaton.getOrderPropertyDataVector(); // Errors are alredy saved and cleared!
    for (Iterator iter = errors.iterator(); iter.hasNext(); ) {
       OrderPropertyData opD = (OrderPropertyData) iter.next();
       if (opD.getMessageKey()!=null && opD.getMessageKey().equals("pipeline.message.fwdForApproval")){
		 if(opD.getApproveDate()!=null) {
			continue;
		 }
         int ruleId = opD.getWorkflowRuleId();
         Integer approverGroupId = (Integer)approverGroups.get(new Integer(ruleId));
         if (approverGroupId.intValue() == 0){
           return null;  // Approvers group wasn't configured
         }
         IdVector approvers = PipelineUtil.getUsersForGroup(pBaton,approverGroupId.intValue());
         approversHM.put(new Integer(ruleId), approvers);
       }
     }


     /* check is there at list one approver
      * in the list of all email receivers reqested 'pending approval'
      * notification
      */
     HashSet sendToV = new HashSet();
     HashSet receiversHS = pBaton.getEmailReceiversForApproval();
     if (receiversHS  == null || receiversHS.isEmpty()) {
       return sendToV ;
     }
     if (approversHM == null || approversHM.isEmpty()) {
       return sendToV ;
     }
     if (receiversHS != null && approversHM != null) {
       Collection values = approversHM.values();
       for (Iterator iter = values.iterator(); iter.hasNext(); ) {
         IdVector approversV = (IdVector) iter.next();
         for (int i = 0; i < approversV.size(); i++) {
           Integer approver = (Integer)approversV.get(i);
           for (Iterator iterJ = receiversHS.iterator(); iterJ.hasNext(); ) {
             Integer receiver = (Integer) iterJ.next();
             if( approver.equals(receiver)){
               sendToV.add(receiver);
             }
           }
         }
       }
     }
     return sendToV;
     //
   }

   private static MessageResourceData getMessageResourceForLocale(MessageResourceDataVector pMsrDV, String pLocaleCd, int pBusEntityId, String pMessKey) {
     MessageResourceData msrD = null;
     MessageResourceData msrDNotFound = MessageResourceData.createValue();
     msrDNotFound.setName(pMessKey);
     msrDNotFound.setValue("???? "+ pMessKey);

     String defLocale = "en_US";
     int defBusEntityId = 0;
     HashMap messageHM = new HashMap();
     if (pMsrDV == null ){
       return msrDNotFound;
     }
     for (int i = 0; i < pMsrDV.size(); i++) {
       msrD = (MessageResourceData) pMsrDV.get(i);
       String locale = msrD.getLocale();
       String storeId = "" + msrD.getBusEntityId();
       String key = locale+ "_" + storeId;
       MessageResourceData wrkMrD = (MessageResourceData) messageHM.get(key);
       if(wrkMrD==null) {
           messageHM.put(key, msrD);
       }
     }

     String reqKey0 = null;
     String reqKey1 = pLocaleCd+"_"+defBusEntityId;
     String reqKey2 = pLocaleCd.substring(0,2)+"_"+defBusEntityId;
     String defKey  = defLocale+"_"+ defBusEntityId;
     if (pBusEntityId > 0){
       reqKey0 = pLocaleCd+"_"+pBusEntityId;
     }
     // getting message with locale code for store
     if (Utility.isSet(reqKey0) && messageHM.containsKey(reqKey0)) {
       msrD = (MessageResourceData)messageHM.get(reqKey0);
     // getting message with locale code for default store
     } else if (messageHM.containsKey(reqKey1)) {
         msrD = (MessageResourceData)messageHM.get(reqKey1);
     } else if (messageHM.containsKey(reqKey2)) {
         msrD = (MessageResourceData)messageHM.get(reqKey2);
     } else if (messageHM.containsKey(defKey)) {
           msrD = (MessageResourceData)messageHM.get(defKey);
     } else {
       msrD = msrDNotFound;
     }
     return msrD;
   }
 }


