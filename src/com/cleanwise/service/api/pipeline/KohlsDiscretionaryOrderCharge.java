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
import com.cleanwise.service.api.dao.OrderFreightDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.dao.PurchaseOrderDataAccess;
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
 * Apply Discretionary order charge for any order placed outside the scheduled
 * cart period.
 * @author ssharma
 *
 */
public class KohlsDiscretionaryOrderCharge implements OrderPipeline{

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

		   if(order.getOrderSourceCd().equalsIgnoreCase(RefCodeNames.ORDER_SOURCE_CD.INVENTORY)){

			   order.setTotalFreightCost(new BigDecimal(0));
			   order.setTotalMiscCost(new BigDecimal(0));
			   OrderDataAccess.update(pCon, order);
			   String fname = pBaton.getFreightType();

			   if (fname != null && fname.trim().length() > 0) {
				   DBCriteria cr = new DBCriteria();
				   cr.addEqualTo(OrderFreightDataAccess.SHORT_DESC, fname);
				   cr.addEqualTo(OrderFreightDataAccess.ORDER_ID, pBaton.getOrderData().getOrderId());
				   OrderFreightDataVector freightHandlers = OrderFreightDataAccess.select(pCon, cr);

				   //clear all freight info
				   for(int i=0; i<freightHandlers.size(); i++){
					   OrderFreightData ofd = (OrderFreightData)freightHandlers.get(i);
					   ofd.setFreightHandlerId(0);
					   ofd.setAmount(new BigDecimal(0));

					   OrderFreightDataAccess.update(pCon, ofd);
				   }

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
