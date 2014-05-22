package com.cleanwise.service.api.session;

/**
 * Title:        SKUInformationHome
 * Description:  Home Interface for SKUInformation Stateless Session Bean
 * Purpose:      Provides access to the methods for retrieving and evaluating SKU information
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface SKUInformationHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the SKUInformation Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the SKUInformationBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     SKUInformation
   * @see     CreateException
   * @see     RemoteException
   */
  public SKUInformation create() throws CreateException, RemoteException;

}
