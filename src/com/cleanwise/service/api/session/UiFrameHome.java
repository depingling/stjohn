package com.cleanwise.service.api.session;

/**
 * Title:        UiFrameHome
 * Description:  Home Interface for Product Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving product information.
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface UiFrameHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the UiFrame Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the ProductBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     UiFrame
   * @see     CreateException
   * @see     RemoteException
   */
  public UiFrame create() throws CreateException, RemoteException;


}
