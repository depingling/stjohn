package com.cleanwise.service.api.session;

/**
 * Title:        RequestHome
 * Description:  Home Interface for Request Stateless Session Bean
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface RequestHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Request Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the RequestBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Request
   * @see     CreateException
   * @see     RemoteException
   */
  public Request create() throws CreateException, RemoteException;

}
