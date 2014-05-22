
package com.cleanwise.service.api.pipeline;
import java.sql.Connection;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.ItemSkuMapping;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.dao.ItemSubstitutionDefDataAccess;
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
 * Updates order and item status
 * @author  YKupershmidt (copied from WorkflowBean)
 */
public class UpdateOrderState  implements OrderPipeline
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
      pBaton = PipelineUtil.updateOrder(pBaton, pActor, pCon, pFactory);
      String orderStatusCd= pBaton.getOrderStatus();
      if (orderStatusCd.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)) {
        PipelineUtil.sendPendingApprovalEmail(pCon, pBaton);
      }

      //Return
      pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
      return pBaton;
    }
    catch(Exception exc) {
    	exc.printStackTrace();
      throw new PipelineException(exc.getMessage());
    }
    finally{
    }
    }
}
