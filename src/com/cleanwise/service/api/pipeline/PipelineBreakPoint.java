/*
 * PipelineBreakPoint.java
 *
 * Created on January 17, 2006, 3:37 PM
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
import com.cleanwise.service.api.util.PipelineUtil;
import com.cleanwise.service.api.dao.ItemSubstitutionDefDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.OrderTotalException;
import com.cleanwise.service.api.util.BudgetRuleException;
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
 *
 * @author Ykupershmidt
 */
public class PipelineBreakPoint implements OrderPipeline
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
      pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
      OrderData orderD = pBaton.getOrderData();
      String orderStatusCd = pBaton.getOrderStatus();
      if(RefCodeNames.ORDER_STATUS_CD.ORDERED.equals(orderStatusCd)||
         RefCodeNames.ORDER_STATUS_CD.RECEIVED.equals(orderStatusCd)||
         RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(orderStatusCd)||
         RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(orderStatusCd)         
         ) {
        return pBaton; //No processing if everything okay
      }

      //Generate step meta
      OrderMetaDataVector orderMetaDV = pBaton.getOrderMetaDataVector(); 
      OrderMetaData orderMetaD = null;
      for(Iterator iter=orderMetaDV.iterator(); iter.hasNext();) {
        OrderMetaData omD = (OrderMetaData) iter.next();
        if(RefCodeNames.ORDER_PROPERTY_TYPE_CD.PIPELINE_STEP.equals(omD.getName())) {
          orderMetaD = omD;
          break;
        }
      }
      if(orderMetaD==null) {
        orderMetaD = OrderMetaData.createValue();
        orderMetaD.setAddBy(pBaton.getUserName());
        orderMetaD.setName(RefCodeNames.ORDER_PROPERTY_TYPE_CD.PIPELINE_STEP);
        orderMetaDV.add(orderMetaD);
      }     
      orderMetaD.setOrderId(orderD.getOrderId());
      orderMetaD.setModBy(pBaton.getUserName());
      int pipelineId = pBaton.getPipelineData().getPipelineId();
      orderMetaD.setValue(pBaton.getPipelineData().getPipelineTypeCd());
      orderMetaD.setValueNum(pipelineId);
     
      orderD.setWorkflowInd(RefCodeNames.WORKFLOW_IND_CD.INTERRUPTED);
      
      //Save order
      int orderId = orderD.getOrderId();
      OrderData bdOrderD = OrderDataAccess.select(pCon,orderId);
      String orderStatus = bdOrderD.getOrderStatusCd();
      if(RefCodeNames.ORDER_STATUS_CD.RECEIVED.equals(orderStatus)) {
        pBaton = PipelineUtil.saveNewOrder(pBaton, pActor, pCon, pFactory);
      } else {
        pBaton = PipelineUtil.updateOrder(pBaton, pActor, pCon, pFactory);
      }
      pBaton.setWhatNext(OrderPipelineBaton.STOP);
      return pBaton;
    }
    catch(Exception exc) {
      throw new PipelineException(exc.getMessage());
    }
    }

  
}
