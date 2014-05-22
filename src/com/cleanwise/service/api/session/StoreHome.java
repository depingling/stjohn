package com.cleanwise.service.api.session;

/**
 * Title:        StoreHome
 * Description:  Home Interface for Store Stateless Session Bean
 * Purpose:      Provides access to the services for managing the store information, properties and relationships.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface StoreHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Store Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the StoreBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Store
   * @see     CreateException
   * @see     RemoteException
   */
  public Store create() throws CreateException, RemoteException;

}
