package com.cleanwise.service.api.session;

/**
 * Title:        Template
 * Description:  Home Interface for Template Stateless Session Bean
 * Purpose:      Provides access to the services for managing template information.
 * Copyright:    Copyright (c) 2010
 * Company:      eSpendWise, Inc.
 * @author       John Esielionis, eSpendWise, Inc.
 */

import java.rmi.RemoteException;

import javax.ejb.CreateException;

public interface TemplateHome extends javax.ejb.EJBHome
{
  /**
   * Method used to get a reference to the Template Bean remote reference.
   * @return  The remote reference to be used to execute the methods associated with 
   * 		  the TemplateBean.
   * @throws  CreateException, RemoteException
   * @see     Template, CreateException, RemoteException
   */
  public Template create() throws CreateException, RemoteException;

}
