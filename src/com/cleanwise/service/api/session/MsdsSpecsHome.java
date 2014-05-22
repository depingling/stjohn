package com.cleanwise.service.api.session;

/**
 * Title:        ShoppingServicesHome
 * Description:  Home Interface for MsdsSpecs Stateless Session Bean
 * Purpose:      Provides access to the add, update, and retrieval methods associated with msds & specs documents
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmid, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface MsdsSpecsHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the MsdsSpecs Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the MsdsSpecsBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     ShoppingServices
   * @see     CreateException
   * @see     RemoteException
   */
  public MsdsSpecs create() throws CreateException, RemoteException;

}
