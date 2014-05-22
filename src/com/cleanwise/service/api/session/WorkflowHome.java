package com.cleanwise.service.api.session;

/**
 * Title:        WorkflowHome
 * Description:  Home Interface for Workflow Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving Workflow information. 
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface WorkflowHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Workflow Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the WorkflowBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Workflow
   * @see     CreateException
   * @see     RemoteException
   */
  public Workflow create() throws CreateException, RemoteException;

}
