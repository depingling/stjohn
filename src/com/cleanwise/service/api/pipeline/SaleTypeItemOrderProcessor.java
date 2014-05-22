/*
 * SaleTypeOrderProcessor.java
 *
 * Created on September 24, 2003, 2:34 PM
 */

package com.cleanwise.service.api.pipeline;
import com.cleanwise.service.api.util.Utility;
import java.util.Iterator;
import java.util.ArrayList;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import java.sql.*;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.session.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Collection;
/**
 *Handles processing items with Re Sale (BillBack) Items on an order.  That is an order that has items on it that are
 *flagged for re-sale.  This results in 2 orders being placed behind the scenes.  This has implecations on
 *taxing, which means that the order is split into 2 different orders, and 2 different accounts.
 * @author  bstevens
 */
public class SaleTypeItemOrderProcessor implements OrderPipeline{
    
    public OrderPipelineBaton process(OrderPipelineBaton pBaton,
            OrderPipelineActor pActor,
            Connection pCon,
            APIAccess pFactory)
            throws PipelineException {
        try{
            pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
            
            String splitS = pBaton.getBusEntityPropertyCached(pBaton.getOrderData().getStoreId(), RefCodeNames.PROPERTY_TYPE_CD.ORDER_PROCESSING_SPLIT_TAX, pCon);
            boolean split = Utility.isTrue(splitS);
            if(!split){
                return pBaton;
            }
            
            OrderItemDataVector orderItems = pBaton.getOrderItemDataVector();
            OrderItemDataVector resaleItems = new OrderItemDataVector();
            OrderItemDataVector nonResaleItems = new OrderItemDataVector();
            for(Iterator iter=orderItems.iterator(); iter.hasNext();) {
               OrderItemData oiD = (OrderItemData) iter.next();
               if(RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE.equals(oiD.getSaleTypeCd())){
                    resaleItems.add(oiD);
                }else{
                    nonResaleItems.add(oiD);
                }
            }
            //if they are both not empty create a new baton and process it
            if(!nonResaleItems.isEmpty() && !resaleItems.isEmpty()){
                OrderPipelineBaton resaleBaton = pBaton.copy();
                resaleBaton.setOrderItemDataVector(resaleItems);
                pActor.addOrderToPipeline(resaleBaton);
            }
            
            if(nonResaleItems.isEmpty()){
                //if there are only resale items then set the items and continue
                pBaton.setOrderItemDataVector(resaleItems);
            }else{
                //otherwise there are only non re-sal items, set the items and continue
                pBaton.setOrderItemDataVector(nonResaleItems);
            }
            return pBaton;
        }catch(Exception e){
            e.printStackTrace();
            throw new com.cleanwise.service.api.util.PipelineException(e.getMessage());
        }
        
        
    }
}
