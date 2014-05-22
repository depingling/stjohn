package com.cleanwise.service.api.session;

/**
 * Title:        InterchangeHome
 * Description:  Home Interface for Interchange Stateless Session Bean
 * Purpose:      Provides access to interchange methods
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * @author       Yuriy Remizov
 */

import javax.ejb.*;
import java.rmi.*;

public interface InterchangeHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the ItemInformation Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the ItemInformationBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     ItemInformation
   * @see     CreateException
   * @see     RemoteException
   */
  public Interchange create() throws CreateException, RemoteException;

}
