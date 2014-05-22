package com.cleanwise.service.api.session;

/**
 * Title:        ThruStoreErp
 * Description:  Home Interface for ThruStoreErp Stateless Session Bean
 * Purpose:      Provides access to the add, update, and retrieval methods associated with ThruStoreErp Ejb interface
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmid, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface ThruStoreErpHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the ThruStoreErp Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the ThruStoreErpBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     ThruStoreErpBean
   * @see     CreateException
   * @see     RemoteException
   */
  public ThruStoreErp create() throws CreateException, RemoteException;

}
