package com.cleanwise.service.api.session;

/**
 * Title:        ContentHome
 * Description:  Home Interface for Content Stateless Session Bean
 * Purpose:      Provides access to Content add and retrieval of information.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface ContentHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Content Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the ContentBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Content
   * @see     CreateException
   * @see     RemoteException
   */
  public Content create() throws CreateException, RemoteException;

}
