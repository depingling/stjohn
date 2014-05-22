/*
 * UpdateOrderSummaryInfo.java
 *
 * Created on August 3, 2005, 5:07 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
//import com.cleanwise.service.api.util.TaxUtil;
import com.cleanwise.service.api.util.TaxUtilAvalara;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.TaxQuery;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Iterator;

import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.APIServiceAccessException;

/**
 * Updates the order summary fields, total cost, total price, tax based of the data in the items.
 * @author bstevens
 */
public class UpdateOrderSummaryInfo implements  OrderPipeline
{
    public OrderPipelineBaton process(OrderPipelineBaton pBaton,
                OrderPipelineActor pActor,
                Connection pCon,
                APIAccess pFactory)
    throws PipelineException {

    	Distributor distributorEjb = null;
    	try {
    	     distributorEjb = pFactory.getDistributorAPI(); // SVC_Avatax: new stmt
    	} catch (APIServiceAccessException e) {
    		e.printStackTrace();

        }

    	Site siteEjb = null;
    	try {
    	     siteEjb = pFactory.getSiteAPI(); // SVC_Avatax: new stmt
    	} catch (APIServiceAccessException e) {
    		e.printStackTrace();

        }

        OrderItemDataVector oItms = pBaton.getOrderItemDataVector();
        OrderData orderD = pBaton.getOrderData();
        BigDecimal totalPrice = new BigDecimal(0.00);
        BigDecimal taxableTotal = new BigDecimal(0.00);
        for(Iterator iter=oItms.iterator(); iter.hasNext();) {
           OrderItemData orderItemD = (OrderItemData)iter.next();
           if (null==orderItemD) continue;
           String itemStatus = orderItemD.getOrderItemStatusCd();
           if(!RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.equals(itemStatus)){
              BigDecimal amount = orderItemD.getCustContractPrice();
              if(amount!=null) {
                int qty = orderItemD.getTotalQuantityOrdered();
                amount = amount.multiply(new BigDecimal(qty));
                //amount = amount.setScale(2,BigDecimal.ROUND_HALF_UP);
                totalPrice = totalPrice.add(amount);
              }
           }
        }
        //totalPrice = totalPrice.setScale(2,BigDecimal.ROUND_HALF_UP);
        orderD.setTotalPrice(totalPrice);
        orderD.setOriginalAmount(totalPrice);
        try{
            int siteId = pBaton.getOrderData().getSiteId();
            int acctId = pBaton.getOrderData().getAccountId();
            int storeId = pBaton.getOrderData().getStoreId();
            if(siteId == 0){
                siteId = pBaton.getPreOrderData().getSiteId();
            }
            if(acctId == 0){
                acctId = pBaton.getPreOrderData().getAccountId();
            }

// Old code
//            BigDecimal tax = TaxUtilAvalara.calculateItemTax(pCon,oItms,siteId, acctId, storeId, distributorEjb, siteEjb);
// New code Avalara Tax. YR
            BigDecimal tax = TaxUtilAvalara.calculateOrderItemsTax(pCon,oItms,siteId, acctId, storeId, distributorEjb, siteEjb);
// End of new code Avalara Tax. YR
            orderD.setTotalTaxCost(tax);

        }catch(Exception e){
            e.printStackTrace();
        }


        pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
        return pBaton;
    }
}
