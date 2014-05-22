/*
 * OrderSkuQtyWorkflow.java
 * Calculates quantity of UOMs of rule skus for the order. The rule triggers if
 * calculated quantity < or <= or > or >= of rule amount
 * Created on August 25, 2003
 */

package com.cleanwise.service.api.pipeline;
import java.sql.Connection;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.value.*;
import org.apache.log4j.*;

/**
 * Processes special sku workflow rules
 * @author  YKupershmidt (copied from WorkflowBean)
 */
public class OrderSkuQtyWorkflow  implements OrderPipeline
{
    private static final Logger log = Logger.getLogger(OrderSkuQtyWorkflow.class);
    /** Process this pipeline.
     *
     * @param pBaton the order request object to act upon
     * @param pActor a active database connection
     * @param pCon
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
                    RefCodeNames.WORKFLOW_RULE_TYPE_CD.ORDER_SKU_QTY);

      if (wfrv.size() <= 0) {
        return pBaton;
      }

      OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
      if(orderItemDV==null) {
        return pBaton;
      }

      // Iterate through the rules for the site.
      boolean evalNextRule = true;
      int uomQty = 0;
      int ruleQty = 0;
      String compSign = "";
      String skuString = "";
      int skuCount = 0;
      WorkflowRuleData rdAction = null;
      String warningMessage = null;
      for (int ruleidx = 0; ruleidx < wfrv.size(); ruleidx++) {

        int ruleSku = 0;
        WorkflowRuleData rd = (WorkflowRuleData)wfrv.get(ruleidx);
        String ruleAction = rd.getRuleAction();
        if(ruleAction!=null && ruleAction.trim().length()>0) {
          rdAction = rd;
        }
        warningMessage = rd.getWarningMessage();

        // Get the rule data.
        String ruleVal = rd.getRuleExpValue();
        String ruleExp = rd.getRuleExp();
        if(RefCodeNames.RULE_EXPRESSION.GREATER.equals(ruleExp) ||
           RefCodeNames.RULE_EXPRESSION.GREATER_OR_EQUAL.equals(ruleExp)||
           RefCodeNames.RULE_EXPRESSION.LESS.equals(ruleExp)||
           RefCodeNames.RULE_EXPRESSION.LESS_OR_EQUAL.equals(ruleExp)) {
          try {
            ruleQty = Integer.parseInt(ruleVal);
            compSign = ruleExp;
            continue;
          }catch(Exception exc){
            exc.printStackTrace();
            log.info("Non numeric workflow rule quantity: "+ruleVal);
            return pBaton;
          }
        }
        else if(RefCodeNames.RULE_EXPRESSION.SKU_NUM.equals(ruleExp)) {
          try {
            ruleSku = Integer.parseInt(ruleVal);
          }catch(Exception exc){
            exc.printStackTrace();
            log.info("Non numeric sku workflow rule number: "+ruleVal);
            continue;
          }
          // Iterate through the items in the order.
          for ( int oi_idx = 0; oi_idx < orderItemDV.size(); oi_idx++ ) {
            OrderItemData oid = (OrderItemData) orderItemDV.get(oi_idx);
            int oiSku = oid.getItemSkuNum();
            if (ruleSku == oiSku ) {
              int oiQty = oid.getTotalQuantityOrdered();
              uomQty += oiQty;
              if(skuString.length()>0) skuString += ", ";
              skuString += oid.getItemSkuNum();
              skuCount++;

            }
          }
        }
     }
     if(uomQty>0 && ruleQty>0) {
       Object[] args = { new Integer(ruleQty), new String(skuString) };
       String[] types = { RefCodeNames.PIPELINE_MESSAGE_ARG_CD.INTEGER, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING };;
       String errMessKey = null;
       boolean ruleFlag = false;
        if(RefCodeNames.RULE_EXPRESSION.GREATER.equals(compSign)) {
          if(uomQty>ruleQty) {
        	ruleFlag = true;
        	errMessKey = "pipeline.message.tooMuchUnits";
          }
        }
        else if(RefCodeNames.RULE_EXPRESSION.GREATER_OR_EQUAL.equals(compSign)) {
          if(uomQty>=ruleQty) {
        	ruleFlag = true;
            errMessKey = "pipeline.message.tooLessUnits";
          }
        }
        else if(RefCodeNames.RULE_EXPRESSION.LESS.equals(compSign)) {
          if(uomQty<ruleQty) {
        	ruleFlag = true;
          	errMessKey = "pipeline.message.unitsRequired";
          }
        }
        else if(RefCodeNames.RULE_EXPRESSION.LESS_OR_EQUAL.equals(compSign)) {
          if(uomQty<=ruleQty) {
        	ruleFlag = true;
          	errMessKey = "pipeline.message.unitsRequired";
          }
        }
        if (ruleFlag){
        	evalNextRule =  OrderPipelineActor.performRuleAction(pCon, pBaton, rdAction, errMessKey, args, types, null, bypassWkflRuleActionCd);
        }
     }
     /*
     if (!evalNextRule) {
       pBaton.stopWorkflow();
     }
     */
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
