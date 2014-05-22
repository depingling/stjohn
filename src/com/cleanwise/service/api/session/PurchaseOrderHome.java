package com.cleanwise.service.api.session;
/**
 * Title:        PurchaseOrderHome
 * Description:  Home Interface for PurchaseOrder Stateless Session Bean
 * Purpose:      Provides management of the Purchase Order and its relationships
 * Copyright:    Copyright (c) 2002
 * Company:      CleanWise, Inc.
 * @author       Brook Stevens, Cleanwise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;


public interface PurchaseOrderHome extends javax.ejb.EJBHome{

  /**
   * Simple create method used to get a reference to the PurchaseOrder Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the PurchaseOrderBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     PurchaseOrder
   * @see     CreateException
   * @see     RemoteException
   */
   public PurchaseOrder create() throws CreateException, RemoteException;

}
