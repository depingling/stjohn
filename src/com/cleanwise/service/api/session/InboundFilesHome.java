package com.cleanwise.service.api.session;

/**
 * Title:        InboundFilesHome
 * Description:  Home Interface for InboundFiles Stateless Session Bean
 * Purpose:      Provides Inbound Files 
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author       
 */

import javax.ejb.*;
import java.rmi.*;

public interface InboundFilesHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the InboundFiles Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the InboundFilesBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     InboundFiles
   * @see     CreateException
   * @see     RemoteException
   */
  public InboundFiles create() throws CreateException, RemoteException;

}
