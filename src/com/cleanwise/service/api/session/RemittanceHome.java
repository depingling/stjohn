package com.cleanwise.service.api.session;
/**
 * Title:        RemittanceHome
 * Description:  Home Interface for Remittance Stateless Session Bean
 * Purpose:      Provides management of the Remittance and its relationships
 * Copyright:    Copyright (c) 2002
 * Company:      CleanWise, Inc.
 * @author       Brook Stevens, Cleanwise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;


public interface RemittanceHome extends javax.ejb.EJBHome{

  /**
   * Simple create method used to get a reference to the Remittance Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the RemittanceBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     PurchaseOrder
   * @see     CreateException
   * @see     RemoteException
   */
   public Remittance create() throws CreateException, RemoteException;

}
