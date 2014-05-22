package com.cleanwise.service.api.session;

/**
 * Title:        OrderHome
 * Description:  Home Interface for Order Stateless Session Bean
 * Purpose:      Provides access to the methods for adding and modifying the shopping cart request.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface OrderHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Order Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the OrderBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Order
   * @see     CreateException
   * @see     RemoteException
   */
  public Order create() throws CreateException, RemoteException;

}
