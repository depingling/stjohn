package com.cleanwise.service.api.session;

/**
 * Title:        ShoppingServicesHome
 * Description:  Home Interface for ShoppingServices Stateless Session Bean
 * Purpose:      Provides access to the add, update, and retrieval methods associated with the shopping cart.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface ShoppingServicesHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the ShoppingServices Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the ShoppingServicesBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     ShoppingServices
   * @see     CreateException
   * @see     RemoteException
   */
  public ShoppingServices create() throws CreateException, RemoteException;

}
