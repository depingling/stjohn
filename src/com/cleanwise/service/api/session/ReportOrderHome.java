package com.cleanwise.service.api.session;

/**
 * Title:        ReportOrderHome
 * Description:  Home Interface for Lawson Backfill Stateless Session Bean
 * Purpose:      Provides access to the add, update, and retrieval methods associated with Lawson Backfill Ejb interface
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmid, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface ReportOrderHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Lawson Backfill Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the ReportOrderBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     ReportOrderBean
   * @see     CreateException
   * @see     RemoteException
   */
  public ReportOrder create() throws CreateException, RemoteException;

}
