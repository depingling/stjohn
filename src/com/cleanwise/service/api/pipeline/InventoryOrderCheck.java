/*
 * CancelReplaceItemDuplication.java
 *
 * Created on August 25, 2003
 */

package com.cleanwise.service.api.pipeline;
import java.sql.Connection;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.SiteData;

/**
 * check each order to see if all items are inventory
 * auto-order items.  if so, the order needs to be held
 * for approval.
 */
public class InventoryOrderCheck  implements OrderPipeline {
	  private static final Logger log = Logger.getLogger(InventoryOrderCheck.class);
    
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
            throws PipelineException {
        try{

            pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
            if (pBaton.getSimpleServiceOrderFl()) {

            return pBaton;

            }
            OrderData orderD = pBaton.getOrderData();

      // For now this is the only account for which this check is valid.
      // durval, 8/8/2005
      if ( orderD.getAccountId() != 94010 ) {
    return pBaton;
      }


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

            OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
            if(orderItemDV==null) {
                return pBaton;
            }

            PropertyUtil pu = new PropertyUtil(pCon);
            SiteData sd = getSite(siteId);

      // Iterate through the items in the order.
      for ( int oi_idx = 0; oi_idx < orderItemDV.size(); oi_idx++ ) {
    OrderItemData oid = (OrderItemData) orderItemDV.get(oi_idx);
    if ( !sd.isAnInventoryAutoOrderItem(oid.getItemId())  ) {
        log.info
      (" ITEM SKU=" + oid.getItemSkuNum()
       + "\n is not an inventory auto order item. "
       + "\n oid=" + oid);

        return pBaton;
    }
      }

      String mess = "This order consists of inventory "
    + " auto order items.  The order needs to be reviewed "
    + " as a person has most likely not entered the order. ";

      pBaton.addError
    (pCon, OrderPipelineBaton.WORKFLOW_RULE_WARNING,
     "Inventory Item Check",
     RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL,0, 0,
     "pipeline.message.inventoryCheck");

      orderD.setOrderStatusCd
    (RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL);
            pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
            return pBaton;

        } catch(Exception exc) {
            throw new PipelineException(exc.getMessage());
        }
    }

    private SiteData getSite(int siteId) throws Exception {
        SiteData site = BusEntityDAO.getSiteFromCache(siteId);
        if (site == null) {
            throw new DataNotFoundException("Site not found.SiteId: " + siteId);
        }
        return site;
    }

}
