package com.cleanwise.service.api.session;

/**
 * Title:        SystemAgentHome
 * Description:  Home Interface for SystemAgent Stateless Session Bean
 * Purpose:      Provides access to the services for managing the SystemAgent information, properties and relationships.
 * Copyright:    Copyright (c) 2002
 * Company:      CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface SystemAgentHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the SystemAgent Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the SystemAgentBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     SystemAgent
   * @see     CreateException
   * @see     RemoteException
   */
  public SystemAgent create() throws CreateException, RemoteException;

}
