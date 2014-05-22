/*
 * CancelReplaceItemDuplication.java
 *
 * Created on August 25, 2003
 */

package com.cleanwise.service.api.pipeline;
import java.sql.Connection;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.ItemSkuMapping;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.PipelineUtil;
import com.cleanwise.service.api.dao.ItemSubstitutionDefDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import java.text.SimpleDateFormat;



import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Collection;
/**
 * Pipeline class. Saves order date to the database.
 * @author  YKupershmidt
 */
public class SaveNewOrder  implements OrderPipeline
{
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
     try{
       pBaton = PipelineUtil.saveNewOrder(pBaton, pActor, pCon, pFactory);

       OrderData orderD = pBaton.getOrderData();
   
       String orderStatus = pBaton.getOrderStatus();
       String  orderSourceCd = orderD.getOrderSourceCd();
       int orderId = orderD.getOrderId();
       if(!RefCodeNames.ORDER_SOURCE_CD.EDI_850.equals(orderSourceCd) &&
           orderId<=0 ) {
         if(!RefCodeNames.ORDER_STATUS_CD.RECEIVED.equals(orderStatus) &&
            !RefCodeNames.ORDER_STATUS_CD.ORDERED.equals(orderStatus) &&
            !RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL.equals(orderStatus) &&
            !RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(orderStatus) &&
            !RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION.equals(orderStatus) &&
            !RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(orderStatus) &&
            !RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW.equals(orderStatus)) {
            pBaton.setWhatNext(OrderPipelineBaton.STOP);
            return pBaton; //Does not save web orders if error found
         }   
       }
       if(RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(orderStatus)) {
          pBaton.setWhatNext(OrderPipelineBaton.STOP);
       }

       //Return
       pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
       return pBaton;
    }catch(Exception e){
       e.printStackTrace();
        throw new PipelineException(e.getMessage());
    }finally{
    }
    }
}
