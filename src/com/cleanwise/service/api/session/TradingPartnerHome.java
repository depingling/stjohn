package com.cleanwise.service.api.session;

/**
 * Title:        TradingPartnerHome
 * Description:  Home Interface for TradingPartner Stateless Session Bean
 * Purpose:      Provides access to the add, update, and retrieval methods associated with order schedule processing
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmid, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface TradingPartnerHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the TradingPartner Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the TradingPartnerBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     ShoppingServices
   * @see     CreateException
   * @see     RemoteException
   */
  public TradingPartner create() throws CreateException, RemoteException;

}
