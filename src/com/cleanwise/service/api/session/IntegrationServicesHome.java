package com.cleanwise.service.api.session;

/**
 * Title:        IntegrationServicesHome
 * Description:  Home Interface for IntegrationServices Stateless Session Bean
 * Purpose:      Provides access to the application integration methods.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface IntegrationServicesHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the
   * IntegrationServices Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the IntegrationServicesBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     IntegrationServices
   * @see     CreateException
   * @see     RemoteException
   */
  public IntegrationServices create() throws CreateException, RemoteException;

}
