package com.cleanwise.service.api.session;

/**
 * Title:        AuditHome
 * Description:  Home Interface for Audit Stateless Session Bean
 * Purpose:      Provides access to the table-level audit methods (add by, add date, mod by, mod date)  
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface AuditHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Audit Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the AuditBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     Audit
   * @see     CreateException
   * @see     RemoteException
   */
  public Audit create() throws CreateException, RemoteException;

}
