package com.cleanwise.service.api.session;

/**
 * Title:        GroupHome
 * Description:  Home Interface for Group Stateless Session Bean
 * Purpose:      Provides access to the table-level Group methods (add by, add date, mod by, mod date)  
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface GroupHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Group Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the GroupBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Group
   * @see     CreateException
   * @see     RemoteException
   */
  public Group create() throws CreateException, RemoteException;

}
