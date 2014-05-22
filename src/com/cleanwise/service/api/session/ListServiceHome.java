package com.cleanwise.service.api.session;

/**
 * Title:        ListServiceHome
 * Description:  Home Interface for ListService Stateless Session Bean
 * Purpose:      Provide reference code lists and lookups.  
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface ListServiceHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the ListService Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the ListServiceBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     ListService
   * @see     CreateException
   * @see     RemoteException
   */
  public ListService create() throws CreateException, RemoteException;

}
