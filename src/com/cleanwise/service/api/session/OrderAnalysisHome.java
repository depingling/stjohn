package com.cleanwise.service.api.session;

/**
 * Title:        OrderAnalysisHome
 * Description:  Home Interface for Order Analysis Stateless Session Bean
 * Purpose:      Provides access to the methods for order analysis requests.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Tim Besser, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface OrderAnalysisHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the OrderAnalysis Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the OrderAnalysisBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     OrderAnalysis
   * @see     CreateException
   * @see     RemoteException
   */
  public OrderAnalysis create() throws CreateException, RemoteException;

}
