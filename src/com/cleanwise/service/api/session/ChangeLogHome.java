package com.cleanwise.service.api.session;

/**
 * Title:        ChangeLogHome
 * Description:  Home Interface for ChangeLog Stateless Session Bean
 * Purpose:      Provides access to change log methods.  
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface ChangeLogHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the ChangeLog Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the ChangeLogBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     ChangeLog
   * @see     CreateException
   * @see     RemoteException
   */
  public ChangeLog create() throws CreateException, RemoteException;

}
