package com.cleanwise.service.api.session;

/**
 * Title:        EstimationHome
 * Description:  Home Interface for Estimation Stateless Session Bean
 * Purpose:      Provides access to the methods for establishing and maintaining the Spending Estimation Model.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt
 */

import javax.ejb.*;
import java.rmi.*;

public interface EstimationHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Estimation Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the CatalogBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Estimation
   * @see     CreateException
   * @see     RemoteException
   */
  public Estimation create() throws CreateException, RemoteException;

}
