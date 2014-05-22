package com.cleanwise.service.api.session;

/**
 * Title:        SKUHome
 * Description:  Home Interface for SKU Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving SKU information.   
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface SKUHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the SKU Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the SKUBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     SKU
   * @see     CreateException
   * @see     RemoteException
   */
  public SKU create() throws CreateException, RemoteException;

}
