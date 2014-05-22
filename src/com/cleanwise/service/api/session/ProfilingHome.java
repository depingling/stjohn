package com.cleanwise.service.api.session;

/**
 * Title:        ProfilingHome
 * Description:  Home Interface for Profiling Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving Profiling information. 
 * Copyright:    Copyright (c) 2003
 * Company:      Cleanwise, Inc.
 * @author       Brook Stevens, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface ProfilingHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Profiling Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the ProfilingBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Profiling
   * @see     CreateException
   * @see     RemoteException
   */
  public Profiling create() throws CreateException, RemoteException;

}
