/*
 * CancelReplaceItemDuplication.java
 *
 * Created on August 25, 2003
 */

package com.cleanwise.service.api.pipeline;
import java.sql.Connection;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;


import java.util.*;

import org.apache.log4j.Logger;


/**
 * Pipeline class. Stops pipeline and saves order data if there is no reject order
 * workflow rules
 * @author  YKupershmidt
 */
public class EndSynchPipeline  implements OrderPipeline
{
    private static final Logger log = Logger.getLogger(EndSynchPipeline.class);

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
      OrderData orderD = pBaton.getOrderData();
      if(pBaton.hasErrors()) {
         pBaton.setWhatNext(OrderPipelineBaton.STOP);
         return pBaton; //Does not save web orders if error found
      }
      PreOrderData preOrderD = pBaton.getPreOrderData();
      String orderSourceCd = preOrderD.getOrderSourceCd();
      int storeId = orderD.getStoreId();

      String userName = pBaton.getUserName();
      orderD.setAddBy(userName);
      orderD.setModBy(userName);
      orderD.setOriginalOrderDate(pBaton.getCurrentDate());
      orderD.setOriginalOrderTime(pBaton.getCurrentDate());

      OrderRequestData orderReq = pBaton.getOrderRequestData();
      IdVector replacedOrderIds = orderReq.getReplacedOrderIds();
      int correspondingWorkOrderItemId = orderReq.getWorkOrderItemId();

      DBCriteria dbc = new DBCriteria();

      //Save order items
      OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
      for(Iterator iter = orderItemDV.iterator(); iter.hasNext();) {
         OrderItemData oiD = (OrderItemData) iter.next();
         oiD.setAddBy(userName);
         oiD.setModBy(userName);
       }

      if(!RefCodeNames.ORDER_SOURCE_CD.EDI_850.equals(orderSourceCd)) {
        //Get store workflows
        dbc = new DBCriteria();
        dbc.addEqualTo(WorkflowDataAccess.BUS_ENTITY_ID,storeId);
        dbc.addEqualTo(WorkflowDataAccess.WORKFLOW_TYPE_CD,
                                        RefCodeNames.WORKFLOW_TYPE_CD.CWSKU);
        dbc.addEqualTo(WorkflowDataAccess.WORKFLOW_STATUS_CD,
                                RefCodeNames.WORKFLOW_STATUS_CD.ACTIVE);
        IdVector workflowIds = WorkflowDataAccess.selectIdOnly(pCon,
                                             WorkflowDataAccess.WORKFLOW_ID,dbc);

        //Get site workflows
        int siteId = orderD.getSiteId();
        dbc = new DBCriteria();
        dbc.addEqualTo(SiteWorkflowDataAccess.SITE_ID, siteId);
        IdVector wIds = SiteWorkflowDataAccess.selectIdOnly(pCon,
                                       SiteWorkflowDataAccess.WORKFLOW_ID, dbc);
        dbc = new DBCriteria();
        dbc.addOneOf(WorkflowDataAccess.WORKFLOW_ID,wIds);
        dbc.addNotEqualTo(WorkflowDataAccess.WORKFLOW_STATUS_CD,
                                        RefCodeNames.WORKFLOW_STATUS_CD.INACTIVE);
        dbc.addEqualTo(WorkflowDataAccess.WORKFLOW_TYPE_CD,RefCodeNames.WORKFLOW_TYPE_CD.ORDER_WORKFLOW);
        IdVector siteWorkflowIds = WorkflowDataAccess.selectIdOnly(pCon,
                                             WorkflowDataAccess.WORKFLOW_ID,dbc);
        workflowIds.addAll(siteWorkflowIds);

        dbc = new DBCriteria();
        dbc.addOneOf(WorkflowRuleDataAccess.WORKFLOW_ID, workflowIds);
        dbc.addEqualTo(WorkflowRuleDataAccess.RULE_ACTION,
                       RefCodeNames.WORKFLOW_RULE_ACTION.REJECT_ORDER);
        dbc.addNotEqualTo(WorkflowRuleDataAccess.WORKFLOW_RULE_STATUS_CD,
                                        RefCodeNames.WORKFLOW_STATUS_CD.INACTIVE);

        IdVector rejectWorkflowIds = WorkflowRuleDataAccess.selectIdOnly(pCon,
                                         WorkflowRuleDataAccess.WORKFLOW_ID, dbc);
        boolean doFullPipeline = false;
        if(rejectWorkflowIds.size()>0 ||
           RefCodeNames.ORDER_SOURCE_CD.SCHEDULER.equals(orderSourceCd)) {
           doFullPipeline = true;
        }else{
            //look for other criteria that would lead us to go through the entire pipeline
           int accountId = preOrderD.getAccountId();
           if(accountId > 0){
                PropertyUtil pu = new PropertyUtil(pCon);
                String custSysAp = pu.fetchValueIgnoreMissing(0,accountId,RefCodeNames.PROPERTY_TYPE_CD.CUSTOMER_SYSTEM_APPROVAL_CD);
                if(RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.equals(custSysAp)){
                    doFullPipeline = true;
                }
           }
        }
        if(doFullPipeline){
           //Go to full preorder capture pipeline
           pActor.addPipeline(pBaton, RefCodeNames.PIPELINE_CD.SYNCH_ASYNCH, pCon);
           pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
           return pBaton;
        }
      }


      //Save order in Received state and finish pipeline
      //Get order number
      IntegrationServices intServEjb = pFactory.getIntegrationServicesAPI();
      int orderNum = intServEjb.getNextOrderNumber(pCon, storeId);
      orderD.setOrderNum(""+orderNum);
      //Save order info
      orderD = OrderDataAccess.insert(pCon, orderD);
      int orderId = orderD.getOrderId();
      if(pBaton.getOrderCreditCardData() != null){
          pBaton.getOrderCreditCardData().setOrderId(orderId);
          if(pBaton.getOrderCreditCardData().getOrderCreditCardId() > 0){
            OrderCreditCardDataAccess.update(pCon,pBaton.getOrderCreditCardData());
          }else{
            pBaton.setOrderCreditCardData(OrderCreditCardDataAccess.insert(pCon,pBaton.getOrderCreditCardData()));
          }
          if (pBaton.getCreditCardTransData() != null) {
              pBaton.getCreditCardTransData().setOrderCreditCardId(pBaton.getOrderCreditCardData().getOrderCreditCardId());
              if(pBaton.getCreditCardTransData().getCreditCardTransId() > 0){
                CreditCardTransDataAccess.update(pCon,pBaton.getCreditCardTransData());
              }else{
                pBaton.setCreditCardTransData(CreditCardTransDataAccess.insert(pCon,pBaton.getCreditCardTransData()));
              }
          }
      }

      //Save order items
      for(Iterator iter = orderItemDV.iterator(); iter.hasNext();) {
         OrderItemData oiD = (OrderItemData) iter.next();
         oiD.setOrderId(orderId);
         oiD = OrderItemDataAccess.insert(pCon, oiD);
      }


         ServiceTicketOrderRequestView serviceRequest = orderReq instanceof CustomerOrderRequestData
                 ? ((CustomerOrderRequestData) orderReq).getServiceTicketOrderRequest()
                 : null;

         if (serviceRequest != null && serviceRequest.getServiceTickets() != null) {
             updateStOrderLinks(
                     pCon,
                     serviceRequest.getServiceTickets(),
                     orderId,
                     userName
             );
         }

         //IF consolidated order
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
          orderPropD.setShortDesc("Consolidation");
          orderPropD.setValue("The order was replaced by consolidated order "+
                  orderD.getOrderNum());
          orderPropD.
             setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
          orderPropD.
             setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
          orderPropD.setAddBy(userName);
          orderPropD.setModBy(userName);
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
        orderPropD.setAddBy(userName);
        orderPropD.setModBy(userName);
        OrderPropertyDataAccess.insert(pCon, orderPropD);
      } else if (replacedOrderIds != null) {
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
        dbc = new DBCriteria();
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


      } else if (correspondingWorkOrderItemId > 0) {

              OrderAssocData orderAssocD = OrderAssocData.createValue();
              orderAssocD.setOrder2Id(orderId);
              orderAssocD.setOrderAssocCd(RefCodeNames.ORDER_ASSOC_CD.WORK_ORDER_PART);
              orderAssocD.setOrderAssocStatusCd(RefCodeNames.ORDER_ASSOC_STATUS_CD.ACTIVE);
              orderAssocD.setAddBy(userName);
              orderAssocD.setModBy(userName);
              orderAssocD.setWorkOrderItemId(correspondingWorkOrderItemId);
              OrderAssocDataAccess.insert(pCon, orderAssocD);

      }


        //Liang - save order freight information which is related to
        // distributors
        OrderFreightDataVector orderFreightList =
               ((CustomerOrderRequestData)orderReq).getOrderFreightList();
        if (null != orderFreightList && 0 < orderFreightList.size()) {
            for(int i = 0; i < orderFreightList.size(); i++) {
                OrderFreightData orderFreightD = (OrderFreightData)orderFreightList.get(i);
                orderFreightD.setOrderId(orderD.getOrderId());
                OrderFreightDataAccess.insert(pCon, orderFreightD);
            }
        }

        // Save order Discount information which is related to
        // Distributors in the CLW_ORDER_ADD_ON_CHARGE DB table (per Distributor)
        OrderAddOnChargeDataVector orderDiscountList =
               ((CustomerOrderRequestData)orderReq).getOrderDiscountList();
        if (null != orderDiscountList && 0 < orderDiscountList.size()) {
            for(int i = 0; i < orderDiscountList.size(); i++) {
            	OrderAddOnChargeData OrderAddOnChargeD = (OrderAddOnChargeData)orderDiscountList.get(i);
            	OrderAddOnChargeD.setOrderId(orderD.getOrderId());
                OrderAddOnChargeDataAccess.insert(pCon, OrderAddOnChargeD);
            }
        }
        //Save other add on charges
        OrderAddOnChargeDataVector orderAddOnChargeList =
            ((CustomerOrderRequestData)orderReq).getOrderAddOnChargeList();
        if (null != orderAddOnChargeList && 0 < orderAddOnChargeList.size()) {
        	for(int i = 0; i < orderAddOnChargeList.size(); i++) {
        		OrderAddOnChargeData OrderAddOnChargeD = (OrderAddOnChargeData)orderAddOnChargeList.get(i);
        		OrderAddOnChargeD.setOrderId(orderD.getOrderId());
        		OrderAddOnChargeDataAccess.insert(pCon, OrderAddOnChargeD);
        	}
        }


        /// Save 'REBILL_ORDER' specific order propertiy
/*
        PreOrderPropertyDataVector allPreOrderProperties = pBaton.getPreOrderPropertyDataVector();
        if (allPreOrderProperties != null) {
            for(Iterator iter = allPreOrderProperties.iterator(); iter.hasNext();) {
                PreOrderPropertyData preOrderProperty = (PreOrderPropertyData)iter.next();
                if (preOrderProperty == null) {
                    continue;
                }
                if (RefCodeNames.ORDER_PROPERTY_TYPE_CD.REBILL_ORDER.equals(preOrderProperty.getOrderPropertyTypeCd())) {
                    OrderPropertyData orderProperty = OrderPropertyData.createValue();
                    orderProperty.setOrderId(orderD.getOrderId());
                    orderProperty.setShortDesc(preOrderProperty.getOrderPropertyTypeCd());
                    orderProperty.setValue(preOrderProperty.getValue());
                    orderProperty.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
                    orderProperty.setOrderPropertyTypeCd(preOrderProperty.getOrderPropertyTypeCd());
                    orderProperty.setAddBy(userName);
                    orderProperty.setModBy(userName);
                    OrderPropertyDataAccess.insert(pCon, orderProperty);
                    break;
                }
            }
        }
*/

      //Return
      pBaton.setWhatNext(OrderPipelineBaton.STOP);
      return pBaton;
    }catch(Exception e){
       e.printStackTrace();
        throw new PipelineException(e.getMessage());
    }finally{
    }
    }

    public Boolean updateStOrderLinks(Connection pCon, IdVector pServiceTicketNumbers, Integer pOrderId, String pUserName) throws Exception {

        log.info("updateStOrderLinks()=> BEGIN" +
                ", pOrderId: " + pOrderId +
                ", pServiceTicketNumbers: " + pServiceTicketNumbers
        );
        if (pServiceTicketNumbers != null) {

            DBCriteria crit;

            crit = new DBCriteria();
            crit.addEqualTo(OrderAssocDataAccess.ORDER2_ID, pOrderId);
            crit.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_CD, RefCodeNames.ORDER_ASSOC_CD.SERVICE_TICKET);
            crit.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_STATUS_CD, RefCodeNames.ORDER_ASSOC_STATUS_CD.ACTIVE);

            IdVector existsSts = OrderAssocDataAccess.selectIdOnly(pCon, OrderAssocDataAccess.SERVICE_TICKET_ID, crit);

            Set<Integer> duplSet = new HashSet<Integer>(existsSts);

            for (Object oStNumber : pServiceTicketNumbers) {

                Integer ticketNumber = (Integer) oStNumber;

                boolean b = duplSet.remove(ticketNumber);

                if (!b) {

                    OrderAssocData orderAssocD = new OrderAssocData();

                    orderAssocD.setOrder2Id(pOrderId);
                    orderAssocD.setOrderAssocCd(RefCodeNames.ORDER_ASSOC_CD.SERVICE_TICKET);
                    orderAssocD.setOrderAssocStatusCd(RefCodeNames.ORDER_ASSOC_STATUS_CD.ACTIVE);
                    orderAssocD.setAddBy(pUserName);
                    orderAssocD.setModBy(pUserName);
                    orderAssocD.setServiceTicketId(ticketNumber);

                    OrderAssocDataAccess.insert(pCon, orderAssocD);
                }
            }

            if (Utility.isSet(duplSet)) {
                crit = new DBCriteria();
                crit.addEqualTo(OrderAssocDataAccess.ORDER2_ID, pOrderId);
                crit.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_CD, RefCodeNames.ORDER_ASSOC_CD.SERVICE_TICKET);
                crit.addOneOf(OrderAssocDataAccess.SERVICE_TICKET_ID, new ArrayList<Integer>(duplSet));
                OrderAssocDataAccess.remove(pCon, crit);
            }

        }

        log.info("updateStOrderLinks()=> END.");

        return Boolean.TRUE;

    }
}
