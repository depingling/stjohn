package com.cleanwise.service.api.session;

/**
 * Title:        StoreOrderHome
 * Description:  Home Interface for StoreOrder Stateless Session Bean
 * Purpose:      Provides access to the methods for adding and modifying the shopping cart request.
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface StoreOrderHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Order Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the StoreOrderBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Order
   * @see     CreateException
   * @see     RemoteException
   */
  public StoreOrder create() throws CreateException, RemoteException;

}
