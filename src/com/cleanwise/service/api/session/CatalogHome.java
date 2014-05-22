package com.cleanwise.service.api.session;

/**
 * Title:        CatalogHome
 * Description:  Home Interface for Catalog Stateless Session Bean
 * Purpose:      Provides access to the methods for establishing and maintaining the catalog..
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface CatalogHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Catalog Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the CatalogBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Catalog
   * @see     CreateException
   * @see     RemoteException
   */
  public Catalog create() throws CreateException, RemoteException;

}
