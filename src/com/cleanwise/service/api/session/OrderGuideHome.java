package com.cleanwise.service.api.session;

/**
 * Title:        OrderGuideHome
 * Description:  Home Interface for OrderGuide Stateless Session Bean
 * Purpose:      Provides access to the methods for establishing, maintaining, and retrieving information for the order templates; e.g. Order Guide.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface OrderGuideHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the OrderGuide Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the OrderGuideBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     OrderGuide
   * @see     CreateException
   * @see     RemoteException
   */
  public OrderGuide create() throws CreateException, RemoteException;

}
