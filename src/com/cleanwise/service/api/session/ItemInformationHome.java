package com.cleanwise.service.api.session;

/**
 * Title:        ItemInformationHome
 * Description:  Home Interface for ItemInformation Stateless Session Bean
 * Purpose:      Provides access to the methods for retrieving and evaluating item information
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface ItemInformationHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the ItemInformation Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the ItemInformationBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     ItemInformation
   * @see     CreateException
   * @see     RemoteException
   */
  public ItemInformation create() throws CreateException, RemoteException;

}
