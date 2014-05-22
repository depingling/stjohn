package com.cleanwise.service.api.session;

/**
 * Title:        ManufacturerHome
 * Description:  Home Interface for Manufacturer Stateless Session Bean
 * Purpose:      Provides access to the services for managing the manufacturer information, properties and relationships.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface ManufacturerHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Manufacturer Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the ManufacturerBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Manufacturer
   * @see     CreateException
   * @see     RemoteException
   */
  public Manufacturer create() throws CreateException, RemoteException;

}
