/**
 *
 */
package com.cleanwise.service.api.pipeline;

import java.sql.Connection;
import java.util.ArrayList;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.dao.PurchaseOrderDataAccess;
import com.cleanwise.service.api.dao.ShoppingControlDataAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.PurchaseOrderData;
import com.cleanwise.service.api.value.ShoppingControlData;
import com.cleanwise.service.api.value.ShoppingControlDataVector;
import com.cleanwise.service.api.value.SiteData;

/**
 * Modify shopping controls when order is placed.
 * @author Ssharma
 *
 */
public class UpdateShoppingControls implements OrderPipeline{

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
	   pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
	   if(pBaton.hasErrors()){
           return pBaton;
       }
	   try{

		   OrderData order = pBaton.getOrderData();
		   OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();

		   ArrayList accts = new ArrayList();
		   accts.add(new Integer(order.getAccountId()));
		   accts.add(new Integer(0));

		   ArrayList sites = new ArrayList();
		   sites.add(new Integer(order.getSiteId()));
		   sites.add(new Integer(0));

		   for(int i=0; i<orderItemDV.size(); i++){

			   OrderItemData oid = (OrderItemData)orderItemDV.get(i);
			   int thisitemid = oid.getItemId();

			   DBCriteria crit = new DBCriteria();

			   crit.addEqualTo(ShoppingControlDataAccess.ITEM_ID, thisitemid);
			   crit.addOneOf(ShoppingControlDataAccess.ACCOUNT_ID, accts);
			   crit.addOneOf(ShoppingControlDataAccess.SITE_ID, sites);

			   ShoppingControlDataVector scDV = ShoppingControlDataAccess.select(pCon,crit);
			   for(int j=0; j<scDV.size(); j++){
				   ShoppingControlData sdata = (ShoppingControlData)scDV.get(j);
				   sdata.setActualMaxQty(0);
				   sdata.setHistoryOrderQty(0);
				   sdata.setExpDate(null);

				   ShoppingControlDataAccess.update(pCon, sdata);
			   }

		   }

		   //Return
		   pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
		   return pBaton;
	   }
	   catch(Exception exc) {
		   throw new PipelineException(exc.getMessage());
	   }
	   finally{
	   }
   	}

}
