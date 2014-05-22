package com.cleanwise.service.api.session;

/**
 * Title:        AddressValidatorHome
 * Description:  Home Interface for AddressValidator Stateless Session Bean
 * Purpose:      Provides access to the table-level AddressValidator methods
 * Copyright:    Copyright (c) 2003
 * Company:      CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface AddressValidatorHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the AddressValidator Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the AddressValidatorBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     AddressValidator
   * @see     CreateException
   * @see     RemoteException
   */
  public AddressValidator create() throws CreateException, RemoteException;

}
