package com.cleanwise.service.api.pipeline;

import java.sql.Connection;
import java.util.Iterator;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.PipelineUtil;
import com.cleanwise.service.api.value.OrderItemData;

public class UpdateOrderStateAbsolute implements OrderPipeline {

	/** 
	 * Updates an order by calling the actual update/insert methods on the associated 
	 * DataAccess objects, as opposed to the more surgical approach of the
	 * UpdateOrderState pipeline component.  
	 * 
	 * That means this MAY BE INCOMPATIBLE with existing pipelines
	 * This is a replacement of that
	 * component and they do not need to both be in the same pipeline
	 * (just for performance reasons, no harm should occur).
	 */
public OrderPipelineBaton process(OrderPipelineBaton pBaton, 
               OrderPipelineActor pActor, 
               Connection pCon, 
               APIAccess pFactory) 
   throws PipelineException
   {
	   try{
		   if(pBaton.getOrderData().getOrderId() == 0){
			   OrderDataAccess.insert(pCon,pBaton.getOrderData());
		   }else{
			   OrderDataAccess.update(pCon,pBaton.getOrderData());
		   }
		   if(pBaton.getOrderItemDataVector() != null){
			   Iterator it = pBaton.getOrderItemDataVector().iterator();
			   while(it.hasNext()){
				   OrderItemData oid = (OrderItemData) it.next();
				   if(oid.getOrderItemId() == 0){
					   OrderItemDataAccess.insert(pCon,oid);
				   }else{
					   OrderItemDataAccess.update(pCon,oid);
				   }
			   }
		   }
		   pBaton = PipelineUtil.updateOrder(pBaton, pActor, pCon, pFactory);
		   pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
		   return pBaton;
	   }
	   catch(Exception exc) {
	     throw new PipelineException(exc.getMessage());
	   }
   }
}
