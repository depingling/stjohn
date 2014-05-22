package com.cleanwise.service.api.session;

/**
 * Title:        BudgetHome
 * Description:  Home Interface for Budget Stateless Session Bean
 * Purpose:      Provides access to the table-level Budget methods (add by, add date, mod by, mod date)  
 * Copyright:    Copyright (c) 2005
 * Company:      Cleanwise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface BudgetHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Budget Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the BudgetBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Budget
   * @see     CreateException
   * @see     RemoteException
   */
  public Budget create() throws CreateException, RemoteException;

}
