/**
 *
 */
package com.cleanwise.service.api.pipeline;
import java.sql.Connection;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.ItemSkuMapping;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.dao.ItemSubstitutionDefDataAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.OrderTotalException;
import com.cleanwise.service.api.util.BudgetRuleException;
import com.cleanwise.service.api.util.PipelineUtil;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.sql.*;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.text.NumberFormat;


import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Collection;
import java.util.Locale;


/**
 * Modify Kohls outbound PO Num to identify discretionary cart order
 * @author Ssharma
 *
 */
public class ModifyKohlsPONum implements OrderPipeline{

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

		  // DBCriteria crit = new DBCriteria();
		   OrderData order = pBaton.getOrderData();
		   OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
		   int siteId = order.getSiteId();

           if(siteId<=0) {
               return pBaton;
           }
		   //SiteData sd = pFactory.getSiteAPI().getSite(siteId);

		   if(!(order.getOrderSourceCd().equalsIgnoreCase(RefCodeNames.ORDER_SOURCE_CD.INVENTORY) ||
			   (Utility.isSet(order.getOrderTypeCd()) && order.getOrderTypeCd().equalsIgnoreCase(RefCodeNames.ORDER_TYPE_CD.BATCH_ORDER)))){

			   for(int i=0; i<orderItemDV.size(); i++){
				   OrderItemData oitem = (OrderItemData)orderItemDV.get(i);

				   PurchaseOrderData poD = pFactory.getPurchaseOrderAPI().getPurchaseOrder(order.getOrderId(), oitem.getDistErpNum(), true);

				   String outboundPO = poD.getOutboundPoNum();
				   String modifiedPONum = null;
				   if(outboundPO.startsWith("RUSH")){
					   modifiedPONum = outboundPO;
				   }
				   else{
					   modifiedPONum= "RUSH"+outboundPO;
				   }

				   oitem.setOutboundPoNum(modifiedPONum);
				   poD.setOutboundPoNum(modifiedPONum);

				   //update order in database
				   OrderDAO.updateOrderItem(pCon,oitem);
				   PurchaseOrderDataAccess.update(pCon, poD);

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
