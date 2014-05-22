package com.cleanwise.service.api.session;

/**
 * Title:        CurrencyHome
 * Description:  Home Interface for Currency Stateless Session Bean
 * Purpose:      Provides access to currency methods
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface CurrencyHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Currency Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the CurrencyBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Currency
   * @see     CreateException
   * @see     RemoteException
   */
  public Currency create() throws CreateException, RemoteException;

}
