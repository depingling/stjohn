package com.cleanwise.service.api.session;

/**
 * Title:        ProductHome
 * Description:  Home Interface for Product Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving product information. 
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface ProductHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Product Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the ProductBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Product
   * @see     CreateException
   * @see     RemoteException
   */
  public Product create() throws CreateException, RemoteException;

}
