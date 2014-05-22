package com.cleanwise.service.api.session;

/**
 * Title:        ContractorHome
 * Description:  Home Interface for Contractor Stateless Session Bean
 * Purpose:      Provides access to the services for managing the Contractor information, properties and relationships.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
  */

import javax.ejb.*;
import java.rmi.*;

public interface ContractorHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Contractor Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the ContractorBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Contractor
   * @see     CreateException
   * @see     RemoteException
   */
  public Contractor create() throws CreateException, RemoteException;

}
