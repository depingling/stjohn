package com.cleanwise.service.api.session;

/**
 * Title:        KeywordHome
 * Description:  Home Interface for Keyword Stateless Session Bean
 * Purpose:      Provides access to keyword add and retrieval of information.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface KeywordHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Keyword Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the KeywordBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Keyword
   * @see     CreateException
   * @see     RemoteException
   */
  public Keyword create() throws CreateException, RemoteException;

}
