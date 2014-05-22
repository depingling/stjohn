package com.cleanwise.service.api.session;

/**
 * Title:        ContractHome
 * Description:  Home Interface for Item Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving contract information. 
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface ContractHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Contract Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the ContractBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Contract
   * @see     CreateException
   * @see     RemoteException
   */
  public Contract create() throws CreateException, RemoteException;
}
