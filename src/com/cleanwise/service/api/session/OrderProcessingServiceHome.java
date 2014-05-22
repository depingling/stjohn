package com.cleanwise.service.api.session;

/**
 * Title:        OrderProcessingServiceHome
 * Description:  Home Interface for OrderProcessingService Stateless Session Bean
 * Purpose:      Provides order processing related services to the server-side presentation layer to perfrom checkout processing.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface OrderProcessingServiceHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the OrderProcessingService Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the OrderProcessingServiceBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     OrderProcessingService
   * @see     CreateException
   * @see     RemoteException
   */
  public OrderProcessingService create() throws CreateException, RemoteException;

}
