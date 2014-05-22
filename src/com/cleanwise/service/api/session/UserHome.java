package com.cleanwise.service.api.session;

/**
 * Title:        UserHome
 * Description:  Home Interface for User Stateless Session Bean
 * Purpose:      Manages application user including login, authentication and user information.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import java.rmi.*;

public interface UserHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the User Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the UserBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     User
   * @see     CreateException
   * @see     RemoteException
   */
  public User create() throws CreateException, RemoteException;

}
