package com.cleanwise.service.api.session;

/**
 * Title:        CatalogInformationHome
 * Description:  Home Interface for CatalogInformation Stateless Session Bean
 * Purpose:      Provides access to the methods for retrieving and evaluating catalog and associated contracts.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface CatalogInformationHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the CatalogInformation Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the CatalogInformationBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     CatalogInformation
   * @see     CreateException
   * @see     RemoteException
   */
  public CatalogInformation create() throws CreateException, RemoteException;

}
