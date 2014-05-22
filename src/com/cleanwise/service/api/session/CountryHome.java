package com.cleanwise.service.api.session;

/**
 * Title:        CountryHome
 * Description:  Home Interface for Country Stateless Session Bean
 * Purpose:      Provides access to country methods
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * @author       Veronika Denega
 */

import javax.ejb.*;
import java.rmi.*;

public interface CountryHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Country Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the CountryBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Country
   * @see     CreateException
   * @see     RemoteException
   */
  public Country create() throws CreateException, RemoteException;

}
