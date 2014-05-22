package com.cleanwise.service.api.session;

/**
 * Title:        EventHome
 * Description:  Home Interface for Event Stateless Session Bean
 * Purpose:      Provides event driven data processing 
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface EventHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Event Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the EventBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Event
   * @see     CreateException
   * @see     RemoteException
   */
  public Event create() throws CreateException, RemoteException;

}
