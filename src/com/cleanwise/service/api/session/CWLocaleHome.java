package com.cleanwise.service.api.session;

/**
 * Title:        CWLocaleHome
 * Description:  Home Interface for CWLocale Stateless Session Bean
 * Purpose:      Provides access to locale methods.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface CWLocaleHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the CWLocale Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the CWLocaleBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     CWLocale
   * @see     CreateException
   * @see     RemoteException
   */
  public CWLocale create() throws CreateException, RemoteException;

}
