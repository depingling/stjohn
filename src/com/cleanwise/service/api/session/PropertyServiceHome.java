package com.cleanwise.service.api.session;

/**
 * Title:        PropertyServiceHome
 * Description:  Home Interface for PropertyService Stateless Session Bean
 * Purpose:      Provides caching and loading of site-specific properties
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface PropertyServiceHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the PropertyService Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the PropertyServiceBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     PropertyService
   * @see     CreateException
   * @see     RemoteException
   */
  public PropertyService create() throws CreateException, RemoteException;

}
