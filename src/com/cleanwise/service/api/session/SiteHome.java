package com.cleanwise.service.api.session;

/**
 * Title:        SiteHome
 * Description:  Home Interface for Site Stateless Session Bean
 * Purpose:      Provides access to the services for managing the site information, properties and relationships.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface SiteHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Site Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the SiteBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Site
   * @see     CreateException
   * @see     RemoteException
   */
  public Site create() throws CreateException, RemoteException;

}
