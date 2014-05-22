package com.cleanwise.service.api.session;

/**
 * Title:        ServiceProviderTypeHome
 * Description:  Home Interface for ServiceProviderType Stateless Session Bean
 * Purpose:      Provides access to the services for managing the ServiceProviderType information, properties and relationships.
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 * @author       alexxey
 */

import javax.ejb.*;
import java.rmi.*;

public interface ServiceProviderTypeHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the ServiceProviderType Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the ServiceProviderTypeBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     ServiceProviderType
   * @see     CreateException
   * @see     RemoteException
   */
  public ServiceProviderType create() throws CreateException, RemoteException;

}
