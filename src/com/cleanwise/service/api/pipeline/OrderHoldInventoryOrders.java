package com.cleanwise.service.api.pipeline;

import java.sql.Connection;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.PipelineUtil;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderPropertyData;

public class OrderHoldInventoryOrders implements OrderPipeline {
	/** Process this pipeline.
    *
    * @param OrderRequestData the order request object to act upon
    * @param Connection a active database connection
    * @param APIAccess
    */
   public OrderPipelineBaton process(OrderPipelineBaton pBaton,OrderPipelineActor pActor,Connection pCon,APIAccess pFactory)throws PipelineException {
	   try{
		   //wait untill the order is cleared before doing anything with it
		   if(pBaton.hasErrors()){
			   return pBaton;
		   }
		   
		   OrderData order = pBaton.getOrderData();
		   PropertyUtil pru = new PropertyUtil(pCon);
		   String invProp = pru.fetchValueIgnoreMissing(0,order.getSiteId(),RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOP_HOLD_DEL_DATE);
		   //if site is configured to hold the invoice until the appropriate delivery date and 
		   //the order came from an inventory order then put it on hold.
		   if(Utility.isTrue(invProp) && RefCodeNames.SYS_ORDER_SOURCE_CD.INVENTORY.equals(order.getOrderSourceCd())){
			   pBaton.setOrderStatus(RefCodeNames.ORDER_STATUS_CD.WAITING_FOR_ACTION);
			   order.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.WAITING_FOR_ACTION);
			   OrderPropertyData opd = OrderPropertyData.createValue();
			   opd.setAddBy("system");
			   opd.setOrderId(order.getOrderId());
			   opd.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
			   opd.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.INVENTORY_ORDER_HOLD);
			   opd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.INVENTORY_ORDER_HOLD);
			   opd.setValue(RefCodeNames.ORDER_PROPERTY_TYPE_CD.INVENTORY_ORDER_HOLD);
			   pBaton.addOrderPropertyData(opd);
			   pBaton = PipelineUtil.updateOrder(pBaton, pActor, pCon, pFactory);
			   pBaton.setWhatNext(OrderPipelineBaton.STOP);
		   }
		   return pBaton;
	   }catch(Exception e){
		   e.printStackTrace();
		   throw new PipelineException(e.getMessage());
	   }
	   
   }
}
