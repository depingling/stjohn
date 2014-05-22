/*
 * CustomerRequestPoNumberHome.java
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
public interface CustomerRequestPoNumberHome  extends javax.ejb.EJBHome{
   /**
   * Simple create method used to get a reference to the CustomerRequestPoNumber Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the CustomerRequestPoNumberBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     CustomerRequestPoNumber
   * @see     CreateException
   * @see     RemoteException
   */
  public CustomerRequestPoNumber create() throws CreateException, RemoteException;
        
}
