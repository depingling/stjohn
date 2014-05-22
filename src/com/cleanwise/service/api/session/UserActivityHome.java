package com.cleanwise.service.api.session;

/**
 * Title:        UserActivityHome
 * Description:  Home Interface for UserActivity Stateless Session Bean
 * Purpose:      Provides access to user activity tracking methods.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface UserActivityHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the UserActivity Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the UserActivityBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     UserActivity
   * @see     CreateException
   * @see     RemoteException
   */
  public UserActivity create() throws CreateException, RemoteException;

}
