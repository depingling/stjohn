/*
 * EveryOrderWorkflow.java
 *
 * Created on January 25, 2006, 11:54 AM
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
public class EveryOrderWorkflow  implements OrderPipeline
{  
    public OrderPipelineBaton process(OrderPipelineBaton pBaton, 
                OrderPipelineActor pActor, 
                Connection pCon, 
                APIAccess pFactory) 
    throws PipelineException
    {
    try{
      pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
      OrderData orderD = pBaton.getOrderData();
      String orderStatusCd = orderD.getOrderStatusCd();
      int siteId = orderD.getSiteId();
      
      if(siteId<=0) {
        return pBaton;
      }
      if(pBaton.hasErrors()) {
        return pBaton;
      }
      String bypassWkflRuleActionCd = pBaton.getBypassWkflRuleActionCd();
      String wfrcd = pBaton.getUserWorkflowRoleCd();

      if (RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER.equals(wfrcd)) {
        return pBaton;
      }
      
      WorkflowRuleDataVector wfrv = 
        pBaton.getWorkflowRuleDataVector(
                    RefCodeNames.WORKFLOW_RULE_TYPE_CD.EVERY_ORDER);
      if (wfrv.size() <= 0) {
        return pBaton;
      }

      // Iterate through the rules for the site.

      boolean evalNextRule = true;
      for (int ruleidx = 0; evalNextRule &&
                 ruleidx < wfrv.size(); ruleidx++) {

        WorkflowRuleData rd = (WorkflowRuleData)wfrv.get(ruleidx);
        //String ruleMess =rd.getRuleAction()+" (Every order workflow rule)";
        /*String ruleMess =Utility.strNN(rd.getRuleExp())+Utility.strNN(rd.getRuleExpValue())+
                " (Every order workflow rule)";*/
        
        //STJ-5359
        String ruleMess = "pipeline.message.everyOrderWorkflow";
        Object[] args = new Object[2];
        args[0] = Utility.strNN(rd.getRuleExp());
        args[1] = Utility.strNN(rd.getRuleExpValue());
        String[] argTypes = new String[]{RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING};
        
        /*evalNextRule = OrderPipelineActor.
        performRuleAction(pCon, pBaton, rd, ruleMess, null, bypassWkflRuleActionCd);*/
        
        evalNextRule = OrderPipelineActor.performRuleAction(pCon, pBaton, rd, ruleMess, args, 
        		argTypes, null, bypassWkflRuleActionCd);

      }

      if (!evalNextRule) {
       //pBaton.stopWorkflow();
       // wkflrr.setStatus(WorkflowRuleResult.OK);
      }

    //Return
     return pBaton;
    }
    catch(Exception exc) {
      throw new PipelineException(exc.getMessage());
    }
    }

}
