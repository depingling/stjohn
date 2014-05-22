package com.cleanwise.service.api.session;

/**
 * Title:        ProductInformationHome
 * Description:  Home Interface for ProductInformation Stateless Session Bean
 * Purpose:      Provides access to the methods for retrieving and evaluating product information
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface ProductInformationHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the ProductInformation Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the ProductInformationBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     ProductInformation
   * @see     CreateException
   * @see     RemoteException
   */
  public ProductInformation create() throws CreateException, RemoteException;

}
