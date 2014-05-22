package com.cleanwise.service.api.session;

import java.rmi.*;
import java.sql.*;

import javax.ejb.*;

import com.cleanwise.service.api.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.pipeline.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class StoreOrderBean extends ApplicationServicesAPI{

  /**
  *
  */
 public StoreOrderBean() {
 }

 /**
  *
  */
 public void ejbCreate()
 throws CreateException, RemoteException
 {
 }

 /**
  * Updates the Order information values to be used by the request.
  * @param pOrderData  the OrderData .
   *@param pUser the user login name
  * @return a <code>OrderData</code> value
  * @throws            RemoteException Required by EJB 1.0
  */
 public OrderData updateOrder(StoreOrderChangeRequestData pChangeRequest)
     throws RemoteException {
   OrderPipelineBaton rBaton = new OrderPipelineBaton();
   StoreOrderPipelineBaton baton = new StoreOrderPipelineBaton();
   baton.setStoreOrderChangeRequestData(pChangeRequest);
   rBaton = pipelineProcessing(baton, RefCodeNames.PIPELINE_CD.STORE_ORDER_UPDATE);
   return rBaton.getOrderData();
 }

 /**
  *  Description of the Method
  *
  *@param  pOrderId
  *@param pUser the user login name
  *@exception  RemoteException     Description of Exception
  */
 public void cancelOrder(int pOrderId, String pUser) throws RemoteException {
   StoreOrderPipelineBaton baton = new StoreOrderPipelineBaton();
   StoreOrderChangeRequestData changeRequest = StoreOrderChangeRequestData.createValue();
   OrderData order = OrderData.createValue();
   order.setOrderId(pOrderId);
   changeRequest.setOrderData(order);
   baton.setStoreOrderChangeRequestData(changeRequest);
   baton.setUserName(pUser);
   pipelineProcessing(baton, RefCodeNames.PIPELINE_CD.STORE_ORDER_CANCEL);
}

 /**
  *  Description of the Method
  *@param pOrderData OrderData
  *@param  pOrderItemIdV IdVector
  *@param pUser the user login name
  *@exception  RemoteException     Description of Exception
  */

 public OrderData cancelOrderItems(StoreOrderChangeRequestData pChangeRequest)
 throws RemoteException {
   OrderPipelineBaton rBaton;
   StoreOrderPipelineBaton baton = new StoreOrderPipelineBaton();
   baton.setStoreOrderChangeRequestData(pChangeRequest);
   rBaton = pipelineProcessing(baton, RefCodeNames.PIPELINE_CD.STORE_ORDER_ITEMS_CANCEL);
   return ((StoreOrderPipelineBaton)rBaton).getStoreOrderChangeRequestData().getOrderData();
 }

 CacheManager mCacheManager = new CacheManager("StoreOrderBean ");

 public OrderStatusDescDataVector getOrderStatusDescCollection(OrderStatusCriteriaData pOrderStatusCriteria)
     throws RemoteException {

     OrderStatusDescDataVector orderStatusDescV = new OrderStatusDescDataVector();
     Connection conn = null;

     BusEntityDAO beDAO = mCacheManager.getBusEntityDAO();
     try
     {
         OrderDataVector orderStatusV =
         getOrderStatusCollection(pOrderStatusCriteria);

         if (null == orderStatusV ) {
             return orderStatusDescV;
         }

         orderStatusDescV = new OrderStatusDescDataVector();
         conn = getConnection();

         for (int i = 0; i < orderStatusV.size(); i++)
         {
             OrderData orderStatusD = (OrderData)orderStatusV.get(i);
             OrderStatusDescData orderStatusDescD = OrderStatusDescData.createValue();
             orderStatusDescD.setOrderDetail(orderStatusD);
 // Set any order history info.
 orderStatusDescD.setShoppingHistory
     (ShoppingDAO.getOrderHistory(conn, orderStatusD.getOrderId() ));

             try{
                 orderStatusDescD.setPlacedBy
   ( getCachedUserByName(conn, orderStatusD) );
             }catch(DataNotFoundException e){
                 //user who placed order no longer exists or
                 // the order was system generated order
             }

             //get the order item data
             DBCriteria crit = new DBCriteria();
             crit.addEqualTo(OrderItemDataAccess.ORDER_ID,orderStatusD.getOrderId());
             OrderItemDataVector orderItemV = OrderItemDataAccess.select(conn, crit);

             //figure out if all the items have been shipped according to the order
             //item actions
             initOrderItemActionProperties(orderStatusDescD, orderItemV, conn);


             orderStatusDescD.setAllowModifFl(calcAllowModifFlag(conn, orderStatusD));
             orderStatusDescD.setOrderItemList(orderItemV);
             if(RefCodeNames.ORDER_STATUS_CD.CANCELLED.
             equals(orderStatusD.getOrderStatusCd())) {
                 OrderData consolidatedOrderD =
                 getConsolidatedOrderForReplaced(conn, orderStatusD.getOrderId());
                 orderStatusDescD.setConsolidatedOrder(consolidatedOrderD);
             }
             // set the erpOrderNum
             if (null != orderItemV && 0 < orderItemV.size())
             {
                 OrderItemData orderItemD = (OrderItemData)orderItemV.get(0);
                 orderStatusDescD.setErpOrderNum(orderItemD.getErpOrderNum());
             }

             // Order ship to and bill to information.
             crit = new DBCriteria();
             crit.addEqualTo(OrderAddressDataAccess.ORDER_ID,
             orderStatusD.getOrderId());

             OrderAddressDataVector oadv = OrderAddressDataAccess.select(
             conn, crit);

             for (int ia = 0; null != oadv && ia < oadv.size(); ia++)
             {
                 OrderAddressData oa = (OrderAddressData)oadv.get(ia);
                 if (oa.getAddressTypeCd().equals(
                     RefCodeNames.ADDRESS_TYPE_CD.SHIPPING))
                 {
                     orderStatusDescD.setShipTo(oa);
                 }
                 else if (oa.getAddressTypeCd().equals(
                     RefCodeNames.ADDRESS_TYPE_CD.BILLING))
                 {
                     orderStatusDescD.setBillTo(oa);
                 }
             }

             // add the account name
             try{

                 BusEntityData accountD = beDAO.getAccountById
                 (conn,orderStatusDescD.getAccountId());
                 orderStatusDescD.setAccountId(accountD.getBusEntityId());
                 orderStatusDescD.setAccountName(accountD.getShortDesc());
             }catch(DataNotFoundException e){
                 orderStatusDescD.setAccountId(0);
                 orderStatusDescD.setAccountName("Error");
             }

             // get the dist name
             OrderItemDataVector distOrderItemV = new OrderItemDataVector();

             for (int j = 0; null != orderItemV && j < orderItemV.size(); j++)
             {

                 OrderItemData itemD = (OrderItemData)orderItemV.get(j);
                 String distErpNum = itemD.getDistErpNum();

                 if (null != distErpNum && !"".equals(distErpNum))
                 {
                     distOrderItemV.add(itemD);
                 }
             }

             String distName = new String("");
             ArrayList distErpList = new ArrayList();

             for (int j = 0; null != distOrderItemV && j < distOrderItemV.size(); j++)
             {

                 String distErpNum = ((OrderItemData)distOrderItemV.get(
                 j)).getDistErpNum();

                 if (0 == j) {
                     distErpList.add(distErpNum);
                 }
                 else
                 {

                     boolean matchFlag = false;

                     for (int k = 0; k < distErpList.size(); k++)
                     {

                         if (distErpNum.equals(
                             (String)distErpList.get(k)))
                         {
                             matchFlag = true;

                             break;
                         }
                     }

                     if (false == matchFlag)
                     {
                         distErpList.add(distErpNum);
                     }
                 }
             }

             for (int j = 0; null != distErpList && j < distErpList.size(); j++)
             {
                 String thisDistErpNum = (String)distErpList.get(j);
                 BusEntityData distBE = beDAO.getDistributorByErpNum(conn, thisDistErpNum);
                 distName += distBE.getShortDesc() + " / ";
             }

             if (3 < distName.length())
             {
                 orderStatusDescD.setDistName(distName.substring(0,
                 distName.length() - 3));
             }

             crit = new DBCriteria();
             crit.addEqualTo(OrderMetaDataAccess.ORDER_ID,
             orderStatusD.getOrderId());
             OrderMetaDataVector metaV =
             OrderMetaDataAccess.select(conn, crit);
             orderStatusDescD.setOrderMetaData(metaV);



             //get any order properties that are necessary
             crit = new DBCriteria();
             ArrayList filter = new ArrayList();
             filter.add(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_RECEIVED);
             crit.addOneOf(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,filter);
             crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD,RefCodeNames.SIMPLE_STATUS_CD.ACTIVE);
             crit.addEqualTo(OrderPropertyDataAccess.ORDER_ID,orderStatusD.getOrderId());

             OrderPropertyDataVector opdv = OrderPropertyDataAccess.select(conn, crit);
             Iterator it = opdv.iterator();
             while(it.hasNext()){
               OrderPropertyData opd = (OrderPropertyData) it.next();
               if(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_RECEIVED.equals(opd.getOrderPropertyTypeCd())){
                 orderStatusDescD.setReceived(Utility.isTrue(opd.getValue()));
               }
             }

             orderStatusDescV.add(orderStatusDescD);
         }
     }
     catch (Exception e)
     {
         e.printStackTrace();
         throw new RemoteException("getOrderStatusDescCollection: " +
                                   e.getMessage());
     }
     finally
     {
         closeConnection(conn);
     }

     return orderStatusDescV;
 }


 /**
  * Get order data vector according to the orderStatusCriteriaData
  * @param pOrderStatusCriteria an <code>OrderStatusCriteriaData</code> value
  * @return an <code>OrderDataVector</code> value
  * @exception            RemoteException
  */
 private OrderDataVector getOrderStatusCollection(OrderStatusCriteriaData pOrderStatusCriteria)
 throws RemoteException
 {

     OrderDataVector statusVec = new OrderDataVector();
     Connection conn = null;

     try{
         conn = getConnection();
         statusVec = getOrdersInCriteria(conn, pOrderStatusCriteria, null);
     }catch (Exception e){
         throw processException(e);

     }finally{
         closeConnection(conn);
     }

     return statusVec;
 }


 /**
  *Sets the properties of the OrderStatusDescData that are based on the order item action records
  */
 private void initOrderItemActionProperties(OrderStatusDescData orderStatusDesc, OrderItemDataVector oItems, Connection con)
 throws SQLException{
 	boolean shipped = true;
 	int ackOnHold = 0; //-1 =
     Iterator it = oItems.iterator();
     while(it.hasNext()){
         OrderItemData itm = (OrderItemData) it.next();
         if(Utility.isGoodOrderItemStatus(itm.getOrderItemStatusCd())){
             int totalActionQty = 0;
             DBCriteria crit = new DBCriteria();
             crit.addEqualTo(OrderItemActionDataAccess.ORDER_ITEM_ID,itm.getOrderItemId());
             crit.addOrderBy(OrderItemActionDataAccess.ADD_DATE,true); //order by is used in logic later on
             OrderItemActionDataVector actions = OrderItemActionDataAccess.select(con, crit);
             Iterator actIt = actions.iterator();
             while(actIt.hasNext()){
             	OrderItemActionData act = (OrderItemActionData) actIt.next();
             	if(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_SHIPPED.equals(act.getActionCd())){
             		totalActionQty += act.getQuantity();
                 }else if(!actIt.hasNext()){
                 	if(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACK_ON_HOLD.equals(act.getActionCd())){
                 		//if the last action code is
                 		if(ackOnHold != -1){
                 			ackOnHold = 1;
                 		}
                 	}else{
                 		ackOnHold = -1;
                 	}
                 }
             }

             if(totalActionQty < itm.getTotalQuantityOrdered()){
                 //no need to continue, if one is undershipped the order is undershipped
             	shipped = false;
             }
         }
     }
     orderStatusDesc.setShipped(shipped);
     orderStatusDesc.setAckOnHold(ackOnHold == 1);
 }

 private boolean calcAllowModifFlag(Connection pConn, OrderData pOrder)
 throws Exception
 {
    boolean allowModifFl = false;
    String orderSource = pOrder.getOrderSourceCd();
    if(RefCodeNames.ORDER_SOURCE_CD.EDI_850.equals(orderSource)){
      int tradingProfileId = pOrder.getIncomingTradingProfileId();
      if(tradingProfileId>0) {
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(TradingProfileConfigDataAccess.INCOMING_TRADING_PROFILE_ID,
                              tradingProfileId);
        dbc.addEqualTo(TradingProfileConfigDataAccess.DIRECTION,"OUT");
        dbc.addEqualTo(TradingProfileConfigDataAccess.SET_TYPE,"855");
        IdVector tpcIdV = TradingProfileConfigDataAccess.selectIdOnly(
           pConn, dbc);
        if(tpcIdV.size()>0) {
          allowModifFl = true;
        }
      }
    } else {
      allowModifFl = true;
    }
   return allowModifFl;
 }

 private OrderData getConsolidatedOrderForReplaced(Connection pConn, int pOrderId)
 throws Exception
 {
   DBCriteria dbc = new DBCriteria();
   OrderData consolidatedOrderD = null;
   dbc.addEqualTo(OrderAssocDataAccess.ORDER1_ID,pOrderId);
   dbc.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_CD,
                           RefCodeNames.ORDER_ASSOC_CD.CONSOLIDATED);
   dbc.addEqualTo(OrderAssocDataAccess.ORDER_ASSOC_STATUS_CD,
                           RefCodeNames.ORDER_ASSOC_STATUS_CD.ACTIVE);
   IdVector consolidatedOrderIdV =
        OrderAssocDataAccess.selectIdOnly(pConn,
                          OrderAssocDataAccess.ORDER2_ID,dbc);
   if(consolidatedOrderIdV.size()>0) {
     Integer consolidatedOrderIdI =
                         (Integer) consolidatedOrderIdV.get(0);
     consolidatedOrderD =
        OrderDataAccess.select(pConn,consolidatedOrderIdI.intValue());
   }
    return consolidatedOrderD;
 }

 private OrderDataVector getOrdersInCriteria(Connection conn,
         OrderStatusCriteriaData pOrderStatusCriteria,
         IdVector pOrderIdV)
 throws RemoteException,
             java.sql.SQLException,
             com.cleanwise.service.api.util.DataNotFoundException,
             Exception
 {
     return OrderDAO.getStoreOrdersInCriteria(conn, pOrderStatusCriteria, pOrderIdV);
 }

 /**
  * Get order status desc data vector according to the orderStatusCriteriaData
  * @param pOrderStatusCriteria an <code>OrderStatusCriteriaData</code> value with the
  *  criteria of what to return.
  *  be changed to be more friendly and take into account the order item actions.
  * @return an <code>OrderStatusDescDataVector</code> value
  * @exception            RemoteException
  */

 private UserData getCachedUserByName(Connection conn,
                                      OrderData orderStatusD) throws Exception {
   return mCacheManager.getCachedUserForOrder(conn, orderStatusD);
 }

 /**
  * Order Processing with pipeline.
  * @param pBaton  the StoreOrderPipelineBaton .
  * @param pipelineType the String .
  * @return a <code>OrderPipelineBaton</code> value
  * @throws            RemoteException Required by EJB 1.0
  */
 public OrderPipelineBaton pipelineProcessing(StoreOrderPipelineBaton pBaton,
                                              String pipelineType)
    throws RemoteException {
   Connection conn = null;
   OrderPipelineBaton rBaton = null;
   pBaton.setBatonNumber(0);
   try {
     conn = getConnection();
     APIAccess factory = getAPIAccess();
     if (isPipelineActive(conn, pipelineType)) {
       OrderPipelineActor pipelineActor = new OrderPipelineActor();
       OrderPipelineBaton[] pipelineResult =
           pipelineActor.processPipeline(pBaton, pipelineType, conn, factory);
       if (pipelineResult.length > 0) {
         rBaton = pipelineResult[0];
       }
     } else {
       throw new Exception("No order pipeline found");
     }
   } catch (PipelineMessageException pe) {
     pe.printStackTrace();
     throw (EJBException) new EJBException(pe);
   } catch (Exception e) {
    e.printStackTrace();
     throw new EJBException(pipelineType + ": " + e.getMessage());
   } finally {
     closeConnection(conn);
   }
   return rBaton;
 }


 private boolean isPipelineActive(Connection pCon, String pPipelineTypeCd)
 throws SQLException
 {
   DBCriteria dbc = new DBCriteria();
   dbc.addEqualTo(PipelineDataAccess.PIPELINE_TYPE_CD,pPipelineTypeCd);
   dbc.addEqualTo(PipelineDataAccess.PIPELINE_STATUS_CD, RefCodeNames.PIPELINE_STATUS_CD.ACTIVE);
   dbc.addEqualTo(PipelineDataAccess.OPTIONAL,0);
   IdVector pipelineIds = PipelineDataAccess.selectIdOnly(pCon,dbc);
   if(pipelineIds.size()>0) return true;
   return false;
 }

 public void changeSite(StoreOrderChangeRequestData pStoreOrderChangeRequestData) {
     Connection pConn = null;
     try {
         if (pStoreOrderChangeRequestData.getNewSiteId() == null) {
             return;
         }
         pConn = getConnection();
         int preOrderId = pStoreOrderChangeRequestData.getOrderData()
                 .getPreOrderId();
         int newSiteId = pStoreOrderChangeRequestData.getNewSiteId();
         int oldSiteId = pStoreOrderChangeRequestData.getOldOrderData().getSiteId();
         if (oldSiteId == newSiteId) {
             return;
         }
         PreOrderData preOrderData = PreOrderDataAccess.select(pConn,
                 preOrderId);
         DBCriteria cr = new DBCriteria();
         cr.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, newSiteId);
         cr.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                 RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
         BusEntityDataVector sites = BusEntityDataAccess.select(pConn, cr);
         BusEntityData site = (BusEntityData) sites.get(0);
         final String newSiteName = site.getShortDesc();
         String oldSiteName = "<Not Found>";
         cr = new DBCriteria();
         cr.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, oldSiteId);
         BusEntityDataVector oldSiteDV = BusEntityDataAccess.select(pConn, cr);		 
         if (oldSiteDV.size() >0) {
		     BusEntityData oldSiteD = (BusEntityData) oldSiteDV.get(0);
             oldSiteName = oldSiteD.getShortDesc();
         }
         cr = new DBCriteria();
         cr.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, newSiteId);
         cr.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                 RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
         BusEntityAssocDataVector sitesOfAccount = BusEntityAssocDataAccess
                 .select(pConn, cr);
         cr = new DBCriteria();
         BusEntityAssocData siteOfAccount = (BusEntityAssocData) sitesOfAccount
                 .get(0);
         int accountId = siteOfAccount.getBusEntity2Id();
         cr.addJoinTable(CatalogAssocDataAccess.CLW_CATALOG_ASSOC);
         cr.addJoinCondition(ContractDataAccess.CLW_CONTRACT,
                 ContractDataAccess.CATALOG_ID,
                 CatalogAssocDataAccess.CLW_CATALOG_ASSOC,
                 CatalogAssocDataAccess.CATALOG_ID);
         cr.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC,
                 CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                 RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
         cr.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC,
                 CatalogAssocDataAccess.BUS_ENTITY_ID, newSiteId);
         ContractDataVector contracts = ContractDataAccess.select(pConn, cr);
         int contractId = ((ContractData) contracts.get(0)).getContractId();
         preOrderData.setSiteId(newSiteId);
         preOrderData.setSiteName(site.getShortDesc());
         preOrderData.setAccountId(accountId);
         preOrderData.setContractId(contractId);
         PreOrderDataAccess.update(pConn, preOrderData);
         // Change OrderData
         OrderData orderData = pStoreOrderChangeRequestData.getOrderData();
         orderData.setSiteId(preOrderData.getSiteId());
         orderData.setAccountId(preOrderData.getAccountId());
         orderData.setContractId(preOrderData.getContractId());
         orderData.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.RECEIVED);
         // Add order note
         OrderPropertyData orderNote = OrderPropertyData.createValue();
         orderNote.setOrderId(orderData.getOrderId());
         orderNote.setAddBy(pStoreOrderChangeRequestData.getUserName());
         orderNote.setModBy(pStoreOrderChangeRequestData.getUserName());
         orderNote.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
         orderNote.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
         orderNote.setShortDesc("Order Update");
         orderNote.setValue("Order Site changed to:\"" + newSiteName + "\"(ID:"
                    + newSiteId + ")" + ", the previous was:\"" + oldSiteName
                    + "\"(ID:" + oldSiteId + ")");
         OrderPropertyDataAccess.insert(pConn, orderNote);
     } catch (Exception e) {
         e.printStackTrace();
         throw new RuntimeException("changeSite:" + e.getMessage());
     } finally {
         closeConnection(pConn);
     }
 }

 public List<String> validateSite(StoreOrderChangeRequestData pChangeRequest)
            throws RemoteException {
        List<String> errors = new ArrayList<String>();
        Connection conn = null;
        try {
            int siteId = pChangeRequest.getNewSiteId();
            conn = getConnection();
            DBCriteria cr = new DBCriteria();
            cr.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, siteId);
            cr.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                    RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
            BusEntityDataVector sites = BusEntityDataAccess.select(conn, cr);
            BusEntityData site = null;
            if (sites == null || sites.size() == 0) {
                errors.add("Not found site for ID:" + siteId);
            } else if (sites.size() > 1) {
                errors.add("Was more than ONE sites for ID:" + siteId);
            } else {
                site = (BusEntityData) sites.get(0);
                if (RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE.equals(site.getBusEntityStatusCd()) == false) {
                    errors.add("Site " + siteId + " isn't ACTIVE!");
                }
            }
            if (site != null && errors.size() == 0) {
                cr = new DBCriteria();
                cr.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, site
                        .getBusEntityId());
                cr.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                        RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
                BusEntityAssocDataVector sitesOfAccount = BusEntityAssocDataAccess
                        .select(conn, cr);
                if (sitesOfAccount.size() == 0) {
                    errors.add("Not found account for site :"
                            + site.getBusEntityId());
                } else if (sitesOfAccount.size() > 1) {
                    errors.add("Was found more than ONE ("
                            + sitesOfAccount.size() + ") accounts for site"
                            + site.getBusEntityId());
                }
                cr = new DBCriteria();
                cr.addJoinTable(CatalogAssocDataAccess.CLW_CATALOG_ASSOC);
                cr.addJoinCondition(ContractDataAccess.CLW_CONTRACT,
                        ContractDataAccess.CATALOG_ID,
                        CatalogAssocDataAccess.CLW_CATALOG_ASSOC,
                        CatalogAssocDataAccess.CATALOG_ID);
                cr.addJoinTableEqualTo(
                        CatalogAssocDataAccess.CLW_CATALOG_ASSOC,
                        CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                        RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
                cr.addJoinTableEqualTo(
                        CatalogAssocDataAccess.CLW_CATALOG_ASSOC,
                        CatalogAssocDataAccess.BUS_ENTITY_ID, site
                                .getBusEntityId());
                cr.addEqualTo(ContractDataAccess.CONTRACT_STATUS_CD,
                        RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
                ContractDataVector contracts = ContractDataAccess.select(conn,
                        cr);
                if (contracts == null || contracts.size() == 0) {
                    errors.add("Not found contract for site:"
                            + site.getBusEntityId());
                } else if (contracts.size() > 1) {
                    errors.add("Was found more than ONE (" + contracts.size()
                            + ") contracts for site " + site.getBusEntityId());
                } /* else {
                    int contractId = ((ContractData) contracts.get(0))
                            .getContractId();
                    OrderItemDataVector orderItems = pChangeRequest
                            .getOldOrderItems();
                    Set<Integer> notAssignedIds = getItemsNotAssigned2Contract(
                            conn, contractId, orderItems);
                    if (notAssignedIds.size() > 0) {
                        errors.add("Not assigned items:" + notAssignedIds
                                + " to contract " + contractId);
                    }
                }*/
            }
            return errors;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("checkChangingSiteForOrder:"
                    + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private Set<Integer> getItemsNotAssigned2Contract(Connection pConn,
            int pContractId, OrderItemDataVector pOrderItems)
            throws SQLException {
        Set<Integer> itemIds = new TreeSet<Integer>();
        DBCriteria cr = new DBCriteria();
        for (int i = 0; pOrderItems != null && i < pOrderItems.size(); i++) {
            OrderItemData item = (OrderItemData) pOrderItems.get(i);
            itemIds.add(item.getItemId());
        }
        cr.addEqualTo(ContractItemDataAccess.CONTRACT_ID, pContractId);
        cr.addOneOf(ContractItemDataAccess.ITEM_ID, new ArrayList<Integer>(
                itemIds));
        ContractItemDataVector contractItems = ContractItemDataAccess.select(
                pConn, cr);
        for (int i = 0; contractItems != null && i < contractItems.size(); i++) {
            ContractItemData item = (ContractItemData) contractItems.get(i);
            itemIds.remove(item.getItemId());
        }
        return itemIds;
    }
    
    public static void moveOrderToReceiveStatus(Connection pCon,
            OrderData pOrderData) throws Exception {
        pOrderData.setExceptionInd(null);
        pOrderData.setWorkflowStatusCd(null);
        pOrderData.setAccountErpNum(null);
        pOrderData.setRequestPoNum(null);
        pOrderData.setUserId(0);
        pOrderData.setUserFirstName(null);
        pOrderData.setUserLastName(null);
        pOrderData.setOrderSiteName(null);
        pOrderData.setOrderContactName(null);
        pOrderData.setOrderContactPhoneNum(null);
        pOrderData.setContractId(0);
        pOrderData.setContractShortDesc(null);
        pOrderData.setOrderSourceCd(null);
        pOrderData.setOriginalAmount(null);
        pOrderData.setTotalPrice(null);
        pOrderData.setTotalFreightCost(null);
        pOrderData.setTotalMiscCost(null);
        pOrderData.setTotalTaxCost(null);
        pOrderData.setTotalCleanwiseCost(null);
        pOrderData.setComments(null);
        pOrderData.setLocaleCd(null);
        pOrderData.setCurrencyCd(null);
        pOrderData.setErpSystemCd(null);
        OrderDataAccess.update(pCon, pOrderData);
        DBCriteria cr = new DBCriteria();
        cr.addEqualTo(OrderItemDataAccess.ORDER_ID, pOrderData.getOrderId());
        OrderItemDataVector orderItems = OrderItemDataAccess.select(pCon, cr);
        for (int i = 0; i < orderItems.size(); i++) {
            OrderItemData orderItem = (OrderItemData) orderItems.get(i);
            orderItem.setOrderLineNum(0);
            orderItem.setItemSkuNum(0);
            orderItem.setItemShortDesc(null);
            orderItem.setItemUom(null);
            orderItem.setItemPack(null);
            orderItem.setCustItemShortDesc(null);
            orderItem.setCustItemUom(null);
            orderItem.setCustItemPack(null);
            orderItem.setCustContractPrice(null);
            orderItem.setManuItemSkuNum(null);
            orderItem.setManuItemShortDesc(null);
            orderItem.setManuItemMsrp(null);
            orderItem.setDistItemShortDesc(null);
            orderItem.setDistItemCost(null);
            orderItem.setDistErpNum(null);
            orderItem.setTotalQuantityOrdered(0);
            OrderItemDataAccess.update(pCon, orderItem);
        }
    }
    
    public void processPipelineSyncAsync(OrderData orderData) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            moveOrderToReceiveStatus(conn, orderData);
            APIAccess factory = new APIAccess();
            Pipeline pipeline = factory.getPipelineAPI();
            pipeline.processPipeline(orderData, RefCodeNames.PIPELINE_CD.SYNCH_ASYNCH);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
    }
}
