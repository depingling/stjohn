package com.cleanwise.service.api.session;

/**
 * Title:        ContractInformationHome
 * Description:  Home Interface for ContractInformation Stateless Session Bean
 * Purpose:      Provides access to the methods for retrieving and evaluating contract information
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface ContractInformationHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the ContractInformation Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the ContractInformationBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     ContractInformation
   * @see     CreateException
   * @see     RemoteException
   */
  public ContractInformation create() throws CreateException, RemoteException;

}
