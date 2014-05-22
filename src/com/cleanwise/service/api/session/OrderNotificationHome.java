package com.cleanwise.service.api.session;

/**
 * Title:        OrderNotificationHome
 * Description:  Home Interface for OrderNotification Stateless Session Bean
 * Purpose:      Provides notification services by sending email messages to the customer and order processing department.  
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface OrderNotificationHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the OrderNotification Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the OrderNotificationBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     OrderNotification
   * @see     CreateException
   * @see     RemoteException
   */
  public OrderNotification create() throws CreateException, RemoteException;

}
