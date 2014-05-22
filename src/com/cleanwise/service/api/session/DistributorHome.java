package com.cleanwise.service.api.session;

/**
 * Title:        DistributorHome
 * Description:  Home Interface for Distributor Stateless Session Bean
 * Purpose:      Provides access to the services for managing the distributor information, properties and relationships.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface DistributorHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Distributor Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the DistributorBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Distributor
   * @see     CreateException
   * @see     RemoteException
   */
  public Distributor create() throws CreateException, RemoteException;

}
