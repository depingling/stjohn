package com.cleanwise.service.api.session;

/**
 * Title:        PipelineHome
 * Description:  Home Interface for Pipeline Stateless Session Bean
 * Purpose:      Provides access to methods for managing and accessing the application pipeline.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface PipelineHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Pipeline Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the PipelineBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Pipeline
   * @see     CreateException
   * @see     RemoteException
   */
  public Pipeline create() throws CreateException, RemoteException;

}
