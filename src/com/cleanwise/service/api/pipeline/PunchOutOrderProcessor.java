/*
 * PunchOutOrderProcessor.java
 *
 * Created on June 9, 2004, 2:28 PM
 */

package com.cleanwise.service.api.pipeline;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*; 
import com.cleanwise.service.api.dao.*;
import java.sql.*;
import com.cleanwise.service.api.APIAccess;
import java.math.BigDecimal;
import java.util.Iterator;
/**
 * Figures out wheather to proceed forther into the pipeline based off the configuration of
 * the account.  If an order is for an account that is setup for punch out orders then the 
 *  pipeline will not continue.  This does not process EDI orders as they complete the loop.
 * @author  bstevens
 */
public class PunchOutOrderProcessor implements OrderPipeline{
    private static BigDecimal ZERO = new BigDecimal(0);
    
    public OrderPipelineBaton process(OrderPipelineBaton pBaton, OrderPipelineActor pActor, Connection pCon, APIAccess pFactory) throws PipelineException {
        OrderData orderD = pBaton.getOrderData();
        String whatNext = null;
        try{
            int accountId = orderD.getAccountId();
            if(accountId > 0){
                PropertyUtil pu = new PropertyUtil(pCon);
                String custSysAp = pu.fetchValueIgnoreMissing(0,accountId,RefCodeNames.PROPERTY_TYPE_CD.CUSTOMER_SYSTEM_APPROVAL_CD);
                if(RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_NON_ELEC_ORD.equals(custSysAp) ||
                    RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_NON_ELEC_ORD_ONLY.equals(custSysAp)){
                    //if it is an EDI order, nothing left to verify.
                    if (!RefCodeNames.ORDER_SOURCE_CD.EDI_850.equals(orderD.getOrderSourceCd())){
                        if(!isZeroDollarOrder(pBaton)){
                            whatNext = OrderPipelineBaton.STOP;
                            orderD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.READY_TO_SEND_TO_CUST_SYS);
                            OrderDataAccess.update(pCon,orderD);
                        }
                    }
                }
            }
        }catch(Exception e){
            pBaton.setWhatNext(OrderPipelineBaton.STOP);
            e.printStackTrace();
            orderD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
        }
        if(whatNext == null){
            whatNext = OrderPipelineBaton.GO_NEXT;
        }
        pBaton.setWhatNext(whatNext);
        return pBaton;
    }
    
    /**
     *If order is for "free" items (items with no cost to the customer) method returns true.
     */
    private boolean isZeroDollarOrder(OrderPipelineBaton pBaton){
        boolean zeroDollarOrder = false;
        OrderData orderD = pBaton.getOrderData();
        OrderItemDataVector items = pBaton.getOrderItemDataVector();
        if(orderD.getTotalPrice() != null && ZERO.compareTo(orderD.getTotalPrice()) == 0){  //total cannot be null
            if(orderD.getTotalMiscCost() == null || ZERO.compareTo(orderD.getTotalMiscCost()) == 0){
                if(orderD.getTotalFreightCost() == null || ZERO.compareTo(orderD.getTotalFreightCost()) == 0){
                    Iterator it = items.iterator();
                    zeroDollarOrder = true;
                    while(it.hasNext()){
                        OrderItemData oid = (OrderItemData) it.next();
                        if(!(oid.getCustContractPrice() != null && ZERO.compareTo(oid.getCustContractPrice()) == 0)){
                            zeroDollarOrder = false;
                        }
                    }
                }
            }
        }
        return zeroDollarOrder;
    }
    
}
