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
 * Updates janitor closet after with order data
 * @author  YKupershmidt (copied from WorkflowBean)
 */
public class UpdateJanitorCloset  implements OrderPipeline
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
      int orderId = orderD.getOrderId();
      int siteId = orderD.getSiteId();
      int userId = orderD.getUserId();
      String userName = pBaton.getUserName();
      //Works only if order is in database and site and user are set
      if(orderId<=0 || siteId<=0 || userId<=0) {
        return pBaton;
      }

      if (RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(orderD.getOrderTypeCd())){
        // pick up original orders from consolidated order and process Update Janitor Closet for them
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(OrderAssocDataAccess.ORDER2_ID, orderId);
        dbc.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_CD, RefCodeNames.ORDER_ASSOC_CD.CONSOLIDATED );
        dbc.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_STATUS_CD, RefCodeNames.ORDER_ASSOC_STATUS_CD.ACTIVE);
        String originalOrderIdSQL= OrderAssocDataAccess.getSqlSelectIdOnly(OrderAssocDataAccess.ORDER1_ID, dbc);

        dbc = new DBCriteria();
        dbc.addOneOf(OrderDataAccess.ORDER_ID, originalOrderIdSQL);
        OrderDataVector odV = OrderDataAccess.select(pCon, dbc);
        for (int i = 0; odV!= null && i < odV.size(); i++) {
          orderD = (OrderData)odV.get(i);
          if (orderD != null){
            OrderItemDataVector oiDV = OrderDAO.getOrderItems(pCon, orderD.getOrderId());
            doUpdateByOrder(pCon, orderD.getOrderId(), orderD.getSiteId(), orderD.getUserId(), userName, oiDV);
          }
        }
      } else {
        // process Update Janitor Closet for current order
        OrderItemDataVector oiDV = pBaton.getOrderItemDataVector();
        doUpdateByOrder(pCon, orderId, siteId, userId, userName, oiDV);
      }
 /*     //Get current records
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(JanitorClosetDataAccess.ORDER_ID,orderId);
      dbc.addOrderBy(JanitorClosetDataAccess.ITEM_ID);
      JanitorClosetDataVector jcDV = JanitorClosetDataAccess.select(pCon,dbc);
      //
      OrderItemDataVector oiDV = pBaton.getOrderItemDataVector();
      IdVector idToSave = new IdVector();
      // Add new items
      if(oiDV==null) oiDV = new OrderItemDataVector();
      for(int ii=0; ii<oiDV.size(); ii++) {
        OrderItemData oiD = (OrderItemData) oiDV.get(ii);
        int itemId = oiD.getItemId();
        boolean foundFl = false;
        for(int jj=0; jj<jcDV.size(); jj++) {
          JanitorClosetData jcD = (JanitorClosetData) jcDV.get(jj);
          int jItemId = jcD.getItemId();
          if(itemId==jItemId) {
            idToSave.add(new Integer(jcD.getJanitorClosetId()));
            foundFl = true;
            break;
          }
          if(itemId<jItemId) {
            break;
          }
        }
        if(!foundFl) {
          JanitorClosetData jcD = JanitorClosetData.createValue();
          jcD.setBusEntityId(siteId);
          jcD.setUserId(userId);
          jcD.setItemId(oiD.getItemId());
          jcD.setOrderId(orderId);
          jcD.setAddBy(userName);
          jcD = JanitorClosetDataAccess.insert(pCon, jcD);
          idToSave.add(new Integer(jcD.getJanitorClosetId()));
        }
      }
      //Delete extra
      dbc = new DBCriteria();
      dbc.addEqualTo(JanitorClosetDataAccess.ORDER_ID,orderId);
      dbc.addNotOneOf(JanitorClosetDataAccess.JANITOR_CLOSET_ID,idToSave);
      JanitorClosetDataAccess.remove(pCon,dbc);
*/
      //Return
      return pBaton;
    }
    catch(Exception exc) {
      throw new PipelineException(exc.getMessage());
    }
    }

    private void doUpdateByOrder(Connection pCon, int pOrderId, int pSiteId, int pUserId, String pUserName, OrderItemDataVector pOiDV)  throws Exception {
      //Get current records
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(JanitorClosetDataAccess.ORDER_ID, pOrderId);
      dbc.addOrderBy(JanitorClosetDataAccess.ITEM_ID);
      JanitorClosetDataVector jcDV = JanitorClosetDataAccess.select(pCon, dbc);
    //
      IdVector idToSave = new IdVector();
    // Add new items
     if (pOiDV == null) {
       pOiDV = new OrderItemDataVector();
     }
     for (int ii = 0; ii < pOiDV.size(); ii++) {
        OrderItemData oiD = (OrderItemData) pOiDV.get(ii);
        int itemId = oiD.getItemId();
        boolean foundFl = false;
        for (int jj = 0; jj < jcDV.size(); jj++) {
          JanitorClosetData jcD = (JanitorClosetData) jcDV.get(jj);
          int jItemId = jcD.getItemId();
          if (itemId == jItemId) {
            idToSave.add(new Integer(jcD.getJanitorClosetId()));
            foundFl = true;
            break;
          }
          if (itemId < jItemId) {
            break;
          }
        }
        if (!foundFl) {
          JanitorClosetData jcD = JanitorClosetData.createValue();
          jcD.setBusEntityId(pSiteId);
          jcD.setUserId(pUserId);
          jcD.setItemId(oiD.getItemId());
          jcD.setOrderId(pOrderId);
          jcD.setAddBy(pUserName);
          jcD = JanitorClosetDataAccess.insert(pCon, jcD);
          idToSave.add(new Integer(jcD.getJanitorClosetId()));
        }
      }
      //Delete extra
      dbc = new DBCriteria();
      dbc.addEqualTo(JanitorClosetDataAccess.ORDER_ID, pOrderId);
      dbc.addNotOneOf(JanitorClosetDataAccess.JANITOR_CLOSET_ID, idToSave);
      JanitorClosetDataAccess.remove(pCon, dbc);

    }
  }
