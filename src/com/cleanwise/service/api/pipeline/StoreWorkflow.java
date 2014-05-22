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
import java.sql.SQLException;


import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Collection;
import java.util.Locale;
/**
 * Pipeline class that does intial ui order request parsing.
 * No analysis so far
 * @author  YKupershmidt
 */
public class StoreWorkflow  implements OrderPipeline
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
    if (pBaton.getSimpleServiceOrderFl()) {
            return pBaton;
        }
    OrderData orderD = pBaton.getOrderData();
    String orderStatusCd = orderD.getOrderStatusCd();

    if(pBaton.hasErrors()) {
        return pBaton;
    }
    int storeId = orderD.getStoreId();
    //Get workflow
    DBCriteria dbc = new DBCriteria();
    dbc.addEqualTo(WorkflowDataAccess.BUS_ENTITY_ID,storeId);
    dbc.addEqualTo(WorkflowDataAccess.WORKFLOW_TYPE_CD,
                                    RefCodeNames.WORKFLOW_TYPE_CD.CWSKU);
    dbc.addEqualTo(WorkflowDataAccess.WORKFLOW_STATUS_CD,
                            RefCodeNames.WORKFLOW_STATUS_CD.ACTIVE);
    WorkflowDataVector wDV = WorkflowDataAccess.select(pCon,dbc);
    if(wDV.size()==0) {
       pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
       return pBaton;
    }
    WorkflowData wD = (WorkflowData) wDV.get(0);
    //Check distrib info
    OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
    boolean missingDistInfoFl = false;
    for(int ii=0; ii<orderItemDV.size(); ii++) {
      OrderItemData orderItemD = (OrderItemData) orderItemDV.get(ii);
      int skuNum = orderItemD.getItemSkuNum();
      String distItemSku = orderItemD.getDistItemSkuNum();
      if (!Utility.isSet(distItemSku) ||
        distItemSku.trim().toLowerCase(Locale.US).startsWith("cw")){
        missingDistInfoFl = true;
        break;
      }
    }
    if(missingDistInfoFl) {
      pBaton.addResultMessage("Met Store workflow criteria.");
      WorkflowQueueData wqD =
          createQueueEntry(orderD.getOrderId(),orderD.getStoreId(),
          RefCodeNames.WORKFLOW_TYPE_CD.CWSKU,
          RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER,
          wD.getWorkflowId(), 0,
          orderD.getModBy());
       pBaton.addWorkflowQueueData(wqD);
    }


    //Return
     return pBaton;
    }
    catch(SQLException exc) {
      throw new PipelineException(exc.getMessage());
    }
    finally{
    }
    }
    //--------------------------------------------------------------------------
    private WorkflowQueueData createQueueEntry(int pOrderId, int pBusEntityId,
                                               String pRuleAction,
                                               String pWorkflowRoleCd,
                                               int pWorkflowId,
                                               int pWorkflowRuleId,
                                               String pUser)
   {

        WorkflowQueueData wqD = WorkflowQueueData.createValue();
        wqD.setOrderId(pOrderId);
        wqD.setBusEntityId(pBusEntityId);
        wqD.setShortDesc(pRuleAction);
        wqD.setWorkflowRoleCd(pWorkflowRoleCd);
        wqD.setWorkflowId(pWorkflowId);
        if (pWorkflowRuleId > 0) {
          wqD.setWorkflowRuleId(pWorkflowRuleId);
        }
        wqD.setAddBy(pUser);
        wqD.setModBy(pUser);
        return wqD;
    }

}
