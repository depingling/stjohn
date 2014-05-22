package com.cleanwise.service.api.session;

/**
 * Title:        Language
 * Description:  Home Interface for Language Stateless Session Bean
 * Purpose:      Provides access to the services for managing language information.
 * Copyright:    Copyright (c) 2010
 * Company:      eSpendWise, Inc.
 * @author       Srinivas Chittibomma.
 */

import java.rmi.RemoteException;

import javax.ejb.CreateException;

public interface LanguageHome extends javax.ejb.EJBHome {
	/**
	   * Method used to get a reference to the Language Bean remote reference.
	   * @return  The remote reference to be used to execute the methods associated with 
	   * 		  the LanguageBean.
	   * @throws  CreateException, RemoteException
	   * @see     Language, CreateException, RemoteException
	   */
	  public Language create() throws CreateException, RemoteException;
}
