


/*
 * BreakPointWorkflow.java
 *
 * Created on February 9, 2006, 5:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cleanwise.service.api.pipeline;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.PipelineUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderMetaData;
import com.cleanwise.service.api.value.OrderMetaDataVector;
import com.cleanwise.service.api.value.WorkflowRuleData;
import com.cleanwise.service.api.value.WorkflowRuleDataVector;


/**
 *
 * @author Ykupershmidt
 */
public class BreakPointWorkflow  implements OrderPipeline
{
	  private static final Logger log = Logger.getLogger(BreakPointWorkflow.class);
    
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

      WorkflowRuleDataVector wfrv = 
        pBaton.getWorkflowRuleDataVector(
                    RefCodeNames.WORKFLOW_RULE_TYPE_CD.BREAK_POINT);
      if (wfrv.size() <= 0) {
        return pBaton;
      }
      
      WorkflowRuleData workflowRuleD = (WorkflowRuleData) wfrv.get(0);

      //Generate pipeline step meta
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
     

      //Generate workflow step meta
      orderMetaD = null;
      for(Iterator iter=orderMetaDV.iterator(); iter.hasNext();) {
        OrderMetaData omD = (OrderMetaData) iter.next();
        if(RefCodeNames.ORDER_PROPERTY_TYPE_CD.WORKFLOW_STEP.equals(omD.getName())) {
          orderMetaD = omD;
          break;
        }
      }
      if(orderMetaD==null) {
        orderMetaD = OrderMetaData.createValue();
        orderMetaD.setAddBy(pBaton.getUserName());
        orderMetaD.setName(RefCodeNames.ORDER_PROPERTY_TYPE_CD.WORKFLOW_STEP);
        orderMetaDV.add(orderMetaD);
      }     
      orderMetaD.setOrderId(orderD.getOrderId());
      orderMetaD.setModBy(pBaton.getUserName());
      orderMetaD.setValue(""+workflowRuleD.getWorkflowRuleId());
      orderMetaD.setValueNum(workflowRuleD.getRuleSeq());
      
      orderD.setWorkflowInd(RefCodeNames.WORKFLOW_IND_CD.INTERRUPTED);
      
      //Save order
      int orderId = orderD.getOrderId();
      String orderStatus = orderD.getOrderStatusCd();
      if(orderId != 0){
         OrderData bdOrderD = OrderDataAccess.select(pCon,orderId);
         orderStatus = bdOrderD.getOrderStatusCd();
      }
     if(RefCodeNames.ORDER_STATUS_CD.RECEIVED.equals(orderStatus)) {
        pBaton = PipelineUtil.saveNewOrder(pBaton, pActor, pCon, pFactory);
      } else {
        pBaton = PipelineUtil.updateOrder(pBaton, pActor, pCon, pFactory);
      }

  orderStatusCd= pBaton.getOrderStatus();
    if (orderStatusCd.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL) ){
       //prepare HashMap of approvers for triggered roles before breake point
        HashSet sendToV = PipelineUtil.getUsersToSendPendingApprovalEmais( pBaton);
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        log.info("BreakPointRule : sendToV = " + sendToV);
        log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        if (sendToV == null) {
          // Email can be sent to default user or email group as before
          PipelineUtil.sendPendingApprovalEmail(pCon, pBaton);
          pBaton.setWhatNext(OrderPipelineBaton.STOP);
          return pBaton;
        }
        /* The system should attempt to route the order through each BreakPoint.
         * If there is NOT a user associated with the Site in the next Approver Group Level
         * the system should continue forward until it finds one or ultimately ends up
        */
        if (!sendToV.isEmpty()) {
          PipelineUtil.sendPendingApprovalEmail(pBaton, sendToV);
          pBaton.setWhatNext(OrderPipelineBaton.STOP);
        }
        else {
          pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
        }
      }
      return pBaton;
    }
    catch(Exception exc) {
    	exc.printStackTrace();
      throw new PipelineException(exc.getMessage());
    }
    }

}
       
