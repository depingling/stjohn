/*
 * CancelReplaceItemDuplication.java
 *
 * Created on August 25, 2003
 */

package com.cleanwise.service.api.pipeline;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderDataVector;
import com.cleanwise.service.api.value.WorkflowRuleData;
import com.cleanwise.service.api.value.WorkflowRuleDataVector;
/**
 * Checks interval between orders for the site
 * @author  YKupershmidt (copied from WorkflowBean)
 */
public class OrderVelocityWorkflow  implements OrderPipeline
{
	  private static final Logger log = Logger.getLogger(OrderVelocityWorkflow.class);
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
      String orderStatusCd = orderD.getOrderStatusCd();
      int siteId = orderD.getSiteId();
      
      if(siteId<=0) {
        return pBaton;
      }
      if(pBaton.hasErrors()) {
        return pBaton;
      }
      String bypassWkflRuleActionCd = pBaton.getBypassWkflRuleActionCd();

      // Check the workflow role.  Workflow's apply only to
      // customers which are not APPROVERs.
      String wfrcd = pBaton.getUserWorkflowRoleCd();
      if (RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER.equals(wfrcd)) {
        // No need to check the rules since this user
        // has the authority to overide them.
        return pBaton;
      }

      WorkflowRuleDataVector wfrv = 
        pBaton.getWorkflowRuleDataVector(
                    RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_VELOCITY);
        
      if (wfrv.size() <= 0) {
        return pBaton;
      }

      
      // Iterate through the rules for the site.
      boolean evalNextRule = true;
      for (int ruleidx = 0; evalNextRule &&
                 ruleidx < wfrv.size(); ruleidx++) {

        WorkflowRuleData rd = (WorkflowRuleData)wfrv.get(ruleidx);

        // Get the rule type.
        String intervalS = rd.getRuleExpValue();
        if(intervalS==null) continue; //no value for the rule
        int pDaysBetweenOrders = 0;
        try {
          pDaysBetweenOrders = Integer.parseInt(intervalS);
        }catch(Exception exc){
          String mess = "Wrong rule value format. Rule: "+rd;            
          log.info(mess);
          continue;
        }
        
        String dateCond = OrderDataAccess.ORIGINAL_ORDER_DATE 
        +" > (sysdate - " + pDaysBetweenOrders + ") ";
        String statusCond = OrderDataAccess.ORDER_STATUS_CD + " in " +
                     OrderDAO.kGoodOrderStatusSqlList;
        DBCriteria dbc = new DBCriteria();
        dbc.addNotEqualTo(OrderDataAccess.ORDER_ID, orderD.getOrderId());
        dbc.addEqualTo(OrderDataAccess.SITE_ID, siteId);
        dbc.addCondition(dateCond);
        dbc.addCondition(statusCond);
        
        OrderDataVector oDV = OrderDataAccess.select(pCon, dbc);
        if (oDV.size() > 0 ) {
          if (RefCodeNames.PIPELINE_CD.CHECKOUT_CAPTURE.equals(pBaton.getPipelineTypeCd())){
        	  pBaton.addResultMessage(rd.getWarningMessage());
          }else{
	          //find the latest order
	          Date latestOrderDate = null;
	          for(int ii=0; ii<oDV.size(); ii++) {
	            OrderData oD = (OrderData) oDV.get(ii);
	            Date orderDate = oD.getOriginalOrderDate();
	            if(latestOrderDate==null || latestOrderDate.before(orderDate)){
	              latestOrderDate = orderDate;
	            }
	          }
	          /*String errorMessage = "The order has been placed within "+pDaysBetweenOrders+" days from the last order";
	          GregorianCalendar periodEndGC = new GregorianCalendar();
	          periodEndGC.setTime(latestOrderDate);
	          periodEndGC.add(GregorianCalendar.DATE, pDaysBetweenOrders+1);
	          Date periodEnd = periodEndGC.getTime();
	          SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	          errorMessage += ". The period ends on "+sdf.format(periodEnd);
	          evalNextRule =  OrderPipelineActor.performRuleAction(pCon, pBaton,rd, errorMessage, null, bypassWkflRuleActionCd);*/
	          
	          //STJ-5359
	          String errorMessage = "pipeline.message.orderHasBeenPlacedWithin";
	          Object[] args = new Object[2];
	          args[0] = pDaysBetweenOrders;
	          String[] argTypes = new String[]{RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING};
	          GregorianCalendar periodEndGC = new GregorianCalendar();
	          periodEndGC.setTime(latestOrderDate);
	          periodEndGC.add(GregorianCalendar.DATE, pDaysBetweenOrders+1);
	          Date periodEnd = periodEndGC.getTime();
	          SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	          //errorMessage += ". The period ends on "+sdf.format(periodEnd);
	          args[1] = sdf.format(periodEnd);
	          //evalNextRule =  OrderPipelineActor.performRuleAction(pCon, pBaton,rd, errorMessage, null, bypassWkflRuleActionCd);
	          evalNextRule = OrderPipelineActor.performRuleAction(pCon, pBaton, rd, errorMessage, args, argTypes, null, bypassWkflRuleActionCd);
          }
          break;
        }
     }
     if (!evalNextRule) {
       //pBaton.stopWorkflow();
     }

    //Return
     return pBaton;
    }
    catch(Exception exc) {
      throw new PipelineException(exc.getMessage());
    }
    finally{
    }
    }    
}
