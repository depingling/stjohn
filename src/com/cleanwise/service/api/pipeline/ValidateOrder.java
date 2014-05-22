/*
 * ValidateOrder.java
 *
 * Created on April 12, 2005
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
import com.cleanwise.view.utils.Constants;



import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Collection;
/**
 * Pipeline class that gathers data to reprocess order.
 * @author  YKupershmidt
 */
public class ValidateOrder  implements OrderPipeline
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

       DBCriteria dbc;
       OrderData orderD = pBaton.getOrderData();
       int orderId = orderD.getOrderId();
       // Check the site information.
       String siteName = orderD.getOrderSiteName();
       if (!Utility.isSet(siteName)) {
         String messKey = "pipeline.message.noSiteName";
         String orderNote = "No site name in the order";
         pBaton.addError(pCon, "No site name",
                           RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,0, 0,
                           messKey);
       }

       String orderSourceCd = orderD.getOrderSourceCd();
       String customerPoNumber = orderD.getRequestPoNum();
       String accountErpNum = orderD.getAccountErpNum();
       if(RefCodeNames.ORDER_SOURCE_CD.EDI_850.equals(orderSourceCd) &&
         Utility.isSet(customerPoNumber) &&
         Utility.isSet(siteName)){
         dbc = new DBCriteria();
         dbc.addEqualTo(OrderDataAccess.ACCOUNT_ERP_NUM, accountErpNum);
         dbc.addEqualTo(OrderDataAccess.REQUEST_PO_NUM,customerPoNumber);
         dbc.addEqualTo(OrderDataAccess.ORDER_SITE_NAME, siteName);
         dbc.addNotEqualTo(OrderDataAccess.ORDER_ID,orderId);
         OrderDataVector oDV = OrderDataAccess.select(pCon,dbc);
         if(oDV.size()>0) {
           String duplicatedOrdersS = "";
           for(Iterator iter= oDV.iterator(); iter.hasNext(); ) {
             OrderData oD = (OrderData) iter.next();
             if(duplicatedOrdersS.length()>0) duplicatedOrdersS += ", ";
              duplicatedOrdersS += oD.getOrderNum();
           }
           String messKey = "pipeline.message.duplicateOrderFound";
           String orderNote = "Duplicate order(s) found matching" +
         " on account number and customer po number. Order num: "+duplicatedOrdersS;
           if(orderNote.length()>2000) orderNote = orderNote.substring(0,2000);
           pBaton.addError(pCon, OrderPipelineBaton.DUPLICATED_ORDER,
                                 RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,0, 0, messKey);
           return pBaton;
         }
       }

       // Check the account information.
       AccountBean acct = new AccountBean();
       int siteId = orderD.getSiteId();
       int accountId = orderD.getAccountId();
       dbc = new DBCriteria();
       dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, siteId);
       dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
         RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
       IdVector acctIdV = BusEntityAssocDataAccess.selectIdOnly(pCon,
          BusEntityAssocDataAccess.BUS_ENTITY2_ID,dbc);
       dbc = new DBCriteria();
       dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, acctIdV);
       dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
         RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
       dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
         RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
       acctIdV = BusEntityDataAccess.selectIdOnly(pCon,
          BusEntityDataAccess.BUS_ENTITY_ID,dbc);

       if(acctIdV.size()>1) {
         String messKey = "pipeline.message.multipleAccounts";
         String orderNote = "The site belongs to multiple accounts: " + IdVector.toCommaString(acctIdV);
         pBaton.addError(pCon, "Multiple accounts for the site",
                               RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,0, 0,
                               messKey);

       } else if(acctIdV.size()==0) {
         String messKey = "pipeline.message.noAccountFound";
         String orderNote = "The site doesn't belong to any account: ";
         pBaton.addError(pCon, "No accounts for the site",
                               RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,0, 0,
                               messKey);

       } else {
         int siteAccountId = ((Integer) acctIdV.get(0)).intValue();
         if (accountId!=siteAccountId) {
           String messKey = "pipeline.message.siteAccountMismatch";
           String orderNote = "This order was purchase with account: " + accountId+
           " but the site is set up for account: " + siteAccountId;
           pBaton.addError(pCon, "Site Account Mismatch",null,
                                 RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,0, 0,
                                 messKey,
                                 ""+accountId,RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
                                 ""+siteAccountId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
         }
       }

        OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
        for(Iterator iter=orderItemDV.iterator(); iter.hasNext();) {
          OrderItemData oiD = (OrderItemData) iter.next();
          int qty = oiD.getTotalQuantityOrdered();
          if(qty<=0) {
            continue;
          }
          if(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.
                  equals(oiD.getOrderItemStatusCd())) {
            continue;
          }
          int orderLineNum = oiD.getOrderLineNum();
          int itemId = oiD.getItemId();
          if (itemId <= 0) {
            String messKey = "pipeline.message.noItemFound";
            String orderNote = "No item id for the order.  Line num: " + orderLineNum;
            pBaton.addError(pCon, pBaton.NO_ITEM_FOUND,null,
               RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,oiD.getOrderLineNum(), 0,
               messKey,
               ""+orderLineNum, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
            continue;
          }
          int skuNum = oiD.getItemSkuNum();
          if (skuNum <= 0) {
            String messKey = "pipeline.message.noSku";
            String orderNote = "No sku number. Line num: " + orderLineNum;
            pBaton.addError(pCon, pBaton.NO_ITEM_FOUND,null,
               RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,oiD.getOrderLineNum(), 0,
               messKey,
               ""+orderLineNum, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
            continue;
          }
          String distSku = oiD.getDistItemSkuNum();
          if(!Utility.isSet(distSku)) {
            String messKey = "pipeline.message.noDistSku";
            String orderNote = "No distributor sku. Line num: " + orderLineNum;
            pBaton.addError(pCon, pBaton.NO_ITEM_FOUND,null,
               RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,oiD.getOrderLineNum(),0,
               messKey,
               ""+orderLineNum, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
            continue;
          }
          /*String distUom = oiD.getDistItemUom();
          if(!Utility.isSet(distUom)) {
            String orderNote = "No distributor sku uom. Line num: " + orderLineNum;
            pBaton.addError(pBaton.NO_ITEM_FOUND,orderNote,
               RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,oiD.getOrderLineNum(),0);
          }
          String distPack = oiD.getDistItemPack();
          if(!Utility.isSet(distPack)) {
            String orderNote = "No distributor sku pack. Line num: " + orderLineNum;
            pBaton.addError(pBaton.NO_ITEM_FOUND,orderNote,
               RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,oiD.getOrderLineNum(),0);
          }*/
          BigDecimal distCost = Utility.bdNN(oiD.getDistItemCost());
        }
      //if(pBaton.hasErrors()) {
      //  pBaton.setWhatNext(OrderPipelineBaton.STOP);
      //}
      return pBaton;

    }catch(Exception e){
       e.printStackTrace();
        throw new PipelineException(e.getMessage());
    }finally{
    }
  }


}

