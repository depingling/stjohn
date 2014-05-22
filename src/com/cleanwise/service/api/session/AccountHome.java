package com.cleanwise.service.api.session;

/**
 * Title:        AccountHome
 * Description:  Home Interface for Account Stateless Session Bean
 * Purpose:      Provides access to the services for managing the account information, properties and relationships.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface AccountHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Account Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the AccountBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Account
   * @see     CreateException
   * @see     RemoteException
   */
  public Account create() throws CreateException, RemoteException;

}
