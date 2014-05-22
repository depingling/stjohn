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
import com.cleanwise.service.api.dao.ItemSubstitutionDefDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.sql.*;
import java.util.GregorianCalendar;
import java.util.Calendar;


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
 * Picks up site workflow rules for further processing
 * @author  YKupershmidt 
 */
public class SelectSiteWorkflowRules  implements OrderPipeline
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
      String orderStatusCd = orderD.getOrderStatusCd();
      int siteId = orderD.getSiteId();
      
      if(siteId<=0) {
        return pBaton;
      }
           
      if(pBaton.hasErrors()) {
        return pBaton;
      }
      
      

      
      

      boolean bypassWorkflow = false;
      if(pBaton.getOrderData() != null){
          if(RefCodeNames.WORKFLOW_IND_CD.SKIP.equals(pBaton.getOrderData().getWorkflowInd())){
              bypassWorkflow = true;
          }
      }
      if(pBaton.getOrderRequestData() != null){
        if(pBaton.getOrderRequestData().isBypassCustomerWorkflow()){
              bypassWorkflow = true;
        }
      }
      
      if(bypassWorkflow){
          return pBaton;
      }

      //Set the code that indicates the customer workflow sub system has run against
      //this order
      orderD.setWorkflowInd(RefCodeNames.WORKFLOW_IND_CD.PROCESSED);
      
      
      // Check the workflow role.  Workflow's apply only to
      // customers which are not APPROVERs.
      String wfrcd = pBaton.getUserWorkflowRoleCd();
     /* 
      if (RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER.equals(wfrcd)) {
         return pBaton;
      }
     */
      // Get the workflow for the site.
      WorkflowData wfd = fetchWorkflowForSite(siteId, pCon);
      if(wfd==null) {
         return pBaton;
      }

      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(WorkflowRuleDataAccess.WORKFLOW_ID, wfd.getWorkflowId());
      crit.addOrderBy(WorkflowRuleDataAccess.RULE_SEQ);

      WorkflowRuleDataVector wfrv = WorkflowRuleDataAccess.select(pCon, crit);
      if (wfrv.size() <= 0) {
        return pBaton;
      }
      
      pBaton.setWorkflowRuleDataVector(wfrv);
    //Return
     return pBaton;
    }
    catch(Exception exc) {
      throw new PipelineException(exc.getMessage());
    }
    finally{
    }
    }
    //--------------------------------------------------------------------------
    private WorkflowData fetchWorkflowForSite(int pSiteId, Connection pCon)
    throws Exception
    {

      WorkflowData wfd = null;
      IdVector wids = new IdVector();
      
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(SiteWorkflowDataAccess.SITE_ID, pSiteId);
      wids = SiteWorkflowDataAccess.selectIdOnly(pCon, 
                                         SiteWorkflowDataAccess.WORKFLOW_ID, 
                                         crit);
      for(int ii=0; ii<wids.size();ii++) {
        WorkflowData wD = 
            WorkflowDataAccess.select(pCon,((Integer)wids.get(ii)).intValue());
        if(!RefCodeNames.WORKFLOW_STATUS_CD.INACTIVE.equals(wD.getWorkflowStatusCd()) &&
                RefCodeNames.WORKFLOW_TYPE_CD.ORDER_WORKFLOW.equals(wD.getWorkflowTypeCd())){
          if(wfd==null) {
            wfd = wD;
          } else {
            break;
          }
        }
      }
      return wfd;
    }

}
