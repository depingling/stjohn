/*
 * CorporateOrderHome.java
 *
 * Created on February 10, 2005, 11:05 AM
 */

package com.cleanwise.service.api.session;

import java.rmi.RemoteException;
import javax.ejb.CreateException;

/**
 *
 * @author bstevens
 */
public interface CorporateOrderHome  extends javax.ejb.EJBHome{
   /**
   * Simple create method used to get a reference to the CorporateOrder Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the CorporateOrderBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     CorporateOrder
   * @see     CreateException
   * @see     RemoteException
   */
  public CorporateOrder create() throws CreateException, RemoteException;
        
}
