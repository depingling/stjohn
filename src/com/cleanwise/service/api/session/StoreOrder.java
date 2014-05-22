package com.cleanwise.service.api.session;
import java.rmi.*;
import java.util.List;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.PipelineMessageException;

public interface StoreOrder extends javax.ejb.EJBObject
{

  public OrderData updateOrder(StoreOrderChangeRequestData pChangeRequest)
      throws RemoteException;

  public void cancelOrder(int pOrderId, String pUser)
      throws RemoteException;

  public OrderData cancelOrderItems(StoreOrderChangeRequestData pChangeRequest)
      throws RemoteException;

  public OrderStatusDescDataVector getOrderStatusDescCollection(OrderStatusCriteriaData pOrderStatusCriteria)
      throws RemoteException;

  public List<String> validateSite(StoreOrderChangeRequestData pChangeRequest) throws RemoteException;
  
  public void changeSite(StoreOrderChangeRequestData pStoreOrderChangeRequestData) throws RemoteException ;
  
  public void processPipelineSyncAsync(OrderData orderData) throws RemoteException;
}


