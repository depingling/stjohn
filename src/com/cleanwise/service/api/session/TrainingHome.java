package com.cleanwise.service.api.session;

/**
 * Title:        TrainingHome
 * Description:  Home Interface for Training Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving Training information. 
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface TrainingHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Training Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the TrainingBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Training
   * @see     CreateException
   * @see     RemoteException
   */
  public Training create() throws CreateException, RemoteException;

}
