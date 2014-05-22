/*
 * PunchOutOrderMatching.java
 *
 * Created on June 14, 2004, 12:55 PM
 */

package com.cleanwise.service.api.pipeline;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.*;
import java.sql.*;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;

/**
 *This class will map a new order to an existing punch out order.  There are a variety of
 *different cases that can happen here.  In the simpelest model the punch out order matches
 *exactly to an existing web order that is in a "Sent To Customer System" status.  This order
 *is updated and will be sent through an abriviated pipeline, and the exisiting order will be
 *cancelled as it is really just an approval.  In the second straightforward case the recieved
 *order is different than the exisintg order in a "Sent To Customer System" status and this
 *"Sent To Customer System" is cancelled with the appropriate reference information, and the
 *recieved order is processed through the system.  Some of the error use cases are more than
 *1 matching order is found, no matching order is found (but one is referenced), an order is
 *found but it is not in the correct state.
 *This class does not validate that the account is a punch out order.  This is assumed based
 *on the order.  Also for migratory reasons this should not care about the current state, if
 *there is an order that is in a sent to customer system status, and the inbound order references
 *it, then this order should be matched, regardless of the account configuration.
 * @author  bstevens
 */
public class PunchOutOrderMatching  implements OrderPipeline{



    public OrderPipelineBaton process(OrderPipelineBaton pBaton, OrderPipelineActor pActor, Connection pCon, APIAccess pFactory){
        String whatNext = OrderPipelineBaton.GO_NEXT;
        if (pBaton.getSimpleServiceOrderFl()) {
            return pBaton;
        }
        try{
            OrderData order = pBaton.getOrderData();
            OrderItemDataVector orderItems = pBaton.getOrderItemDataVector();

            String punchOutOrderOrigOrderNum = null;
            if(pBaton.getOrderPropertyDataVector() != null){
                OrderPropertyData punchOutOrderOrigOrderProp =
                    Utility.getOrderProperty(pBaton.getOrderPropertyDataVector(),RefCodeNames.ORDER_PROPERTY_TYPE_CD.PUNCH_OUT_ORDER_ORIG_ORDER_NUM);
                if(punchOutOrderOrigOrderProp != null){
                    punchOutOrderOrigOrderNum = punchOutOrderOrigOrderProp.getValue();
                }
            }
            if(!Utility.isSet(punchOutOrderOrigOrderNum)){
                //if the punchOutOrderOrigOrderNum is not set then there are 2 variables
                //to consider:
                //if the order is from an external source (currently that means 850 or External)
                //if the account is set up to ONLY allow punch out orders via external processing
                //  (That is all orders originate in the web)
                if(RefCodeNames.ORDER_SOURCE_CD.EDI_850.equals(order.getOrderSourceCd()) ||
                RefCodeNames.ORDER_SOURCE_CD.EXTERNAL.equals(order.getOrderSourceCd())){
                    PropertyUtil pu = new PropertyUtil(pCon);
                    String pd = pu.fetchValueIgnoreMissing(0, order.getAccountId(), RefCodeNames.PROPERTY_TYPE_CD.CUSTOMER_SYSTEM_APPROVAL_CD);
                    if(RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_NON_ELEC_ORD_ONLY.equals(pd)){
                        String messKey = "pipeline.message.punchOrderError";
                        //order is an external order type, and the customer is set up to not allow this.
                        //String mess="Customer approval code set to "+RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_NON_ELEC_ORD_ONLY+
                        //" and electronic order was sent without punch out order referenced.";

                        pBaton.addError(pCon, "", "EDI Order Exception",
                                        RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                                        messKey,
                                        RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_NON_ELEC_ORD_ONLY,
                                        RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
                        //pBaton.setWhatNext(OrderPipelineBaton.STOP);
                        //return pBaton;
                    }
                }
                pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
                return pBaton;
            }


            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderDataAccess.ORDER_NUM,punchOutOrderOrigOrderNum.trim());
            crit.addEqualTo(OrderDataAccess.STORE_ID,pBaton.getOrderData().getStoreId());
            OrderDataVector odv = OrderDataAccess.select(pCon,crit);
            if(odv.size() > 1){
                //Way bad, 2 with the same order number?!
                //String mess="Referenced punch out order ("+punchOutOrderOrigOrderNum+") found "+odv.size()+" existing orders, expected to find 1.";
                pBaton.addError(pCon, "", "EDI Order Exception",
                                RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                                "pipeline.message.punchOrderFoundExistingOrders",
                                punchOutOrderOrigOrderNum, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
                                ""+odv.size(),RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
            }else if(odv.size() == 0){
                //this an exception because the property is set, which means we are expecting this
                //to be a punch out order, and need to find the order.
                //String mess="Referenced punch out order ("+punchOutOrderOrigOrderNum+") not found";
                pBaton.addError(pCon, "", "EDI Order Exception",
                                RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0,0,
                                "pipeline.message.noPunchOutOrder",
                                punchOutOrderOrigOrderNum, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);

            }else if(odv.size() == 1){
                OrderData existingOrder = (OrderData) odv.get(0);
                if(!RefCodeNames.ORDER_STATUS_CD.SENT_TO_CUST_SYSTEM.equals(existingOrder.getOrderStatusCd())){
                    //String mess="Found existing referenced punch out order: " + existingOrder.getOrderNum();
                    //mess += " but it is not in a "+RefCodeNames.ORDER_STATUS_CD.SENT_TO_CUST_SYSTEM+" status.";
                    pBaton.addError(pCon, "", "EDI Order Exception",
                                    RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                                    "pipeline.message.wrongPunchOrderStatus",
                                    existingOrder.getOrderNum(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
                                    RefCodeNames.ORDER_STATUS_CD.SENT_TO_CUST_SYSTEM, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
                }else{
                    //now compare them.
                    boolean areTheSame = false;
                    if(isOrdersTheSame(existingOrder, order)){
                        crit = new DBCriteria();
                        crit.addEqualTo(OrderItemDataAccess.ORDER_ID, existingOrder.getOrderId());
                        OrderItemDataVector existingOrderItems = OrderItemDataAccess.select(pCon,crit);
                        if(isOrderItemsTheSame(existingOrderItems,orderItems)){
                            //copy over some of the data from the new order that would have been
                            //set by the customers System
                            existingOrder.setRequestPoNum(order.getRequestPoNum());
                            existingOrder.setRevisedOrderDate(order.getOriginalOrderDate());
                            existingOrder.setRevisedOrderTime(order.getOriginalOrderTime());
                            existingOrder.setRefOrderNum(order.getRefOrderNum());
                            existingOrder.setIncomingTradingProfileId(order.getIncomingTradingProfileId());

                            existingOrder.setComments(
                                Utility.concatonateStrings(existingOrder.getComments(),order.getComments(),":"));

                            //copy over the customer shipping address
                            pBaton.getCustShipToData().setOrderId(order.getOrderId());
                            pBaton.getCustShipToData().setOrderAddressId(0);
                            OrderAddressDataAccess.insert(pCon, pBaton.getCustShipToData());

                            //copy over any properties that should follow the new order (all except
                            //the PUNCH_OUT_ORDER_ORIG_ORDER_NUM property)
                            ///////////////
                            OrderPropertyDataVector opdv = pBaton.getOrderPropertyDataVector();
                            if(opdv==null || opdv.size()==0) {
                                DBCriteria opCrit = new DBCriteria();
                                opCrit.addEqualTo(OrderPropertyDataAccess.ORDER_ID, order.getOrderId());
                                opCrit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD,
                                        RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
                                opdv = OrderPropertyDataAccess.select(pCon,opCrit);
                            }
                            Iterator it = opdv.iterator();
                            while(it.hasNext()){
                                OrderPropertyData opd = (OrderPropertyData) it.next();
                                if(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_PO_NUM
                                               .equals(opd.getOrderPropertyTypeCd())){
                                    DBCriteria dbc = new DBCriteria();
                                    dbc.addEqualTo(OrderPropertyDataAccess.ORDER_ID,existingOrder.getOrderId());
                                    dbc.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
                                            RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_PO_NUM);
                                    dbc.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD,
                                            RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
                                    OrderPropertyDataVector existOpDV = 
                                            OrderPropertyDataAccess.select(pCon,dbc);
                                    if(existOpDV.size()>0) { 
                                        for(Iterator iter=existOpDV.iterator(); iter.hasNext();) {
                                            OrderPropertyData opD = (OrderPropertyData) iter.next();
                                            opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.INACTIVE);
                                            opD.setModBy("PunchOutOrderMatching");
                                            OrderPropertyDataAccess.update(pCon,opD);
                                        }                                        
                                    }
                                }
                                if(!RefCodeNames.ORDER_PROPERTY_TYPE_CD.PUNCH_OUT_ORDER_ORIG_ORDER_NUM
                                               .equals(opd.getOrderPropertyTypeCd())){
                                    opd.setOrderId(existingOrder.getOrderId());
                                    OrderPropertyDataAccess.insert(pCon, opd);
                                }
                            }
                            OrderPropertyData note = OrderPropertyData.createValue();
                            OrderDAO.addOrderNote(pCon, existingOrder.getOrderId(),
                                "Received Order Back From Customer System", "System_PunchOutOrderMatching");

                            boolean isAlright = doChecks(pCon,order,orderItems,existingOrder,existingOrderItems);
                            String mess= "Recieved approval from customer system.";
                            OrderDAO.addOrderNote(pCon, existingOrder.getOrderId(), mess, "punchOutOrderMatching");

                            existingOrder.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.PRE_PROCESSED);
                            order.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.REFERENCE_ONLY);
                            OrderDataAccess.update(pCon, existingOrder);
                            OrderDataAccess.update(pCon, order);
                            //stop processing right here.  This new order should not continue through
                            //the pipeline, it is mearly a confirm for the found order which will now
                            //complete its processing cycle.
                            whatNext = OrderPipelineBaton.STOP;
                            areTheSame = true;
                        }
                    }
                    if(!areTheSame){
                        SetOrderNumber son = new SetOrderNumber();
                        son.setOrderNumber(order,pFactory, pCon);
                        //if the are not the same then cancel the old order and run this one through
                        //the pipeline
                        order.setRefOrderId(existingOrder.getOrderId());
                        OrderDAO.cancelAndUpdateOrder(pCon, existingOrder, "PunchOutOrderMatching");
                        String mess = "Cancelled due to different order recieved back from customer system, Replaced by order num: "+order.getOrderNum();
                        OrderDAO.addOrderNote(pCon,  existingOrder.getOrderId(), mess, "PunchOutOrderMatching");
                        mess = "Replaces cancelled (due to different order recieved back from customer system) order: "+existingOrder.getOrderNum();
                        OrderDAO.addOrderNote(pCon,  order.getOrderId(), mess, "PunchOutOrderMatching");

                        if(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL.equals(order.getOrderStatusCd())){
                            order.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.ORDERED);
                        }
                        order.setWorkflowInd(RefCodeNames.WORKFLOW_IND_CD.SKIP);
                        if(pBaton.getWorkflowRuleDataVector() != null){
                            pBaton.getWorkflowRuleDataVector().clear();
                        }
                        OrderDataAccess.update(pCon, order);
                    }
                }
            }

        }catch(Exception e){
            whatNext = OrderPipelineBaton.STOP;
            e.printStackTrace();
            if(pBaton.getOrderData() != null){
                pBaton.getOrderData().setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
                try{
                    String mess = "Caught exception while processing PunchOutOrder: "+e.getMessage();
                    OrderDAO.addOrderNote(pCon,  pBaton.getOrderData().getOrderId(), mess, "PunchOutOrderMatching");
                    OrderDataAccess.update(pCon, pBaton.getOrderData());
                }catch(Exception e2){
                    e2.printStackTrace();
                }
            }
        }
        pBaton.setWhatNext(whatNext);
        return pBaton;
    }

    /**
     *Do some checks to make sure that even though the order is the same that there is nothing
     *else that is wrong with it, incorect pricing for example.
     */
    private boolean doChecks(Connection pCon,OrderData order, OrderItemDataVector orderItems,OrderData existingOrder, OrderItemDataVector existingOrderItems){
        boolean retValue = true;
        //should already be sorted from isOrderItemsTheSame method
        Iterator it1 = orderItems.iterator();
        Iterator it2 = existingOrderItems.iterator();
        while(it1.hasNext()){
            OrderItemData oi1 = (OrderItemData) it1.next();
            if(it2.hasNext()){
                OrderItemData oi2 = (OrderItemData) it2.next();
                if(oi1.getCustContractPrice().compareTo(oi2.getCustContractPrice())!=0){
                    String mess = "Price on EDI file does not match customer contract price";
                    OrderDAO.enterOrderProperty(pCon, RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE,
                        "Punch Out Order Matching Exception", mess,
                        oi2.getOrderId(), oi2.getOrderItemId(), 0, 0, 0, 0, 0, "Punch Out Order Matching");
                    retValue = false;
                }
            }
        }
        return retValue;
    }

    private boolean isOrdersTheSame(OrderData o1, OrderData o2){
        if(!o2.getOrderSiteName().equals(o1.getOrderSiteName())){
            return false;
        }
        return true;
    }

    //just compares skus and quantities.
    private boolean isOrderItemsTheSame(OrderItemDataVector oiv1, OrderItemDataVector oiv2){
        Collections.sort(oiv1, ORDER_ITEM_COMPARE);
        Collections.sort(oiv2, ORDER_ITEM_COMPARE);
        Iterator it1 = oiv1.iterator();
        Iterator it2 = oiv2.iterator();
        while(it1.hasNext()){
            boolean arethesame = false;
            OrderItemData oi1 = (OrderItemData) it1.next();
            if(it2.hasNext()){
                OrderItemData oi2 = (OrderItemData) it2.next();
                if(oi1.getItemSkuNum() == oi2.getItemSkuNum()){
                    if(oi1.getTotalQuantityOrdered() == oi2.getTotalQuantityOrdered()){
                        arethesame = true;
                    }
                }
            }
            if(!arethesame){
                return false;
            }
        }
        //make sure the 2nd list has no more elements, as we are done with all elements from the
        //first list.
        if(!it2.hasNext()){
            return true;
        }
        return false;
    }


    static final Comparator ORDER_ITEM_COMPARE = new Comparator() {
        public int compare(Object o1, Object o2) {
            OrderItemData oi1 = ((OrderItemData)o1);
            OrderItemData oi2 = ((OrderItemData)o2);
            int sku1 = oi1.getItemSkuNum();
            int sku2 = oi2.getItemSkuNum();
            return sku1 - sku2;
        }
    };
}
