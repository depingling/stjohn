package com.cleanwise.service.api.session;

import javax.ejb.*;
import java.rmi.*;

public interface ServiceHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Service Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the ServiceBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Manufacturer
   * @see     CreateException
   * @see     RemoteException
   */
  public Service create() throws CreateException, RemoteException;

}
