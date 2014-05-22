package com.cleanwise.service.api.session;

/**
 * Title:        FreightTableHome
 * Description:  Home Interface for FreightTable Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving FreightTable information. 
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface FreightTableHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the FreightTable Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the FreightTableBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     FreightTable
   * @see     CreateException
   * @see     RemoteException
   */
  public FreightTable create() throws CreateException, RemoteException;
}
