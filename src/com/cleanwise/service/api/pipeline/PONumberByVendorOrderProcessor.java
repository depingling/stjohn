/*
 * SaleTypeOrderProcessor.java
 *
 * Created on September 24, 2003, 2:34 PM
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderRequestData;
import com.cleanwise.service.api.value.StoreData;

import java.sql.Connection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *Handles processing items with Re Sale (BillBack) Items on an order.  That is an order that has items on it that are
 *flagged for re-sale.  This results in 2 orders being placed behind the scenes.  This has implecations on
 *taxing, which means that the order is split into 2 different orders, and 2 different accounts.
 * @author  bstevens
 */
public class PONumberByVendorOrderProcessor implements OrderPipeline{

    public OrderPipelineBaton process(OrderPipelineBaton pBaton,
            OrderPipelineActor pActor,
            Connection pCon,
            APIAccess pFactory)
            throws PipelineException {
        try{
            pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
            OrderRequestData orderReq = pBaton.getOrderRequestData();
            StoreData storeD = getStore(pBaton.getOrderData().getStoreId());

            // check store properties ALLOW_PO_NUM_BY_VENDER
            String allowPoByVender = PropertyUtil.getPropertyValue(storeD.getMiscProperties(), RefCodeNames.PROPERTY_TYPE_CD.ALLOW_PO_NUM_BY_VENDER);
            if(!Utility.isTrue(allowPoByVender)){
                return pBaton;
            }

            // split order based on distributor erp number
            Map splitOrderMap = new LinkedHashMap();
            OrderItemDataVector orderItems = pBaton.getOrderItemDataVector();
            for(Iterator iter=orderItems.iterator(); iter.hasNext();) {
               OrderItemData oiD = (OrderItemData) iter.next();
               String distErpNum = oiD.getDistErpNum();
               OrderItemDataVector items = (OrderItemDataVector)splitOrderMap.get(distErpNum);
               if (splitOrderMap.get(distErpNum) == null){
            	   items = new OrderItemDataVector();;
               }
               items.add(oiD);
               splitOrderMap.put(distErpNum, items);
            }

            // split order if multiple distributor is involved
            boolean firstOrder = true;
            for(Iterator iter=splitOrderMap.keySet().iterator(); iter.hasNext();) {
            	OrderPipelineBaton currBaton;
            	String distErpNum = (String)iter.next();
            	OrderItemDataVector items = (OrderItemDataVector) splitOrderMap.get(distErpNum);
            	if (firstOrder){
            		pBaton.setOrderItemDataVector(items);
            		firstOrder = false;
            		currBaton = pBaton;
            	}else{
            		currBaton = pBaton.copy();
            		currBaton.setOrderItemDataVector(items);
            		pActor.addOrderToPipeline(currBaton);
            	}

            	String custPo = (String) orderReq.getPoNumberByVendor().get(distErpNum);
            	if (orderReq.getPoNumberByVendor() != null &&  custPo != null){
            		currBaton.getOrderData().setRequestPoNum(custPo);
            	}
            }
            return pBaton;
        }catch(Exception e){
            e.printStackTrace();
            throw new com.cleanwise.service.api.util.PipelineException(e.getMessage());
        }


    }

    private StoreData getStore(int storeId) throws Exception {

        StoreData store;
        store = BusEntityDAO.getStoreFromCache(storeId);
        if (store == null) {
            throw new DataNotFoundException("Store not found. StoreId: " + storeId);
        }

        return store;
    }
}
