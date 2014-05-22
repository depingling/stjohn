package com.cleanwise.service.api.session;

/**
 * Title:        TroubleshooterHome
 * Description:  Home Interface for Troubleshooter Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving Troubleshooter information. 
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface TroubleshooterHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Troubleshooter Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the TroubleshooterBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Troubleshooter
   * @see     CreateException
   * @see     RemoteException
   */
  public Troubleshooter create() throws CreateException, RemoteException;

}
