package com.cleanwise.service.api.session;

/**
 * Title:        NoteHome
 * Description:  Home Interface for Note Stateless Session Bean
 * Purpose:      Provides access to the add, update, and retrieval methods associated with Note Ejb interface
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmid, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;

public interface NoteHome extends javax.ejb.EJBHome
{
  /**
   * Simple create method used to get a reference to the Note Bean
   * remote reference.
   *
   * @return  The remote reference to be used to execute the methods
   *          associated with the NoteBean.
   * @throws  CreateException
   * @throws  RemoteException
   * @see     NoteBean
   * @see     CreateException
   * @see     RemoteException
   */
  public Note create() throws CreateException, RemoteException;

}
