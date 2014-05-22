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

public interface HistoryHome extends javax.ejb.EJBHome {
	/**
	   * Method used to get a reference to the History Bean remote reference.
	   * @return  The remote reference to be used to execute the methods associated with 
	   * 		  the HistoryBean.
	   * @throws  CreateException, RemoteException
	   * @see     History, CreateException, RemoteException
	   */
	  public History create() throws CreateException, RemoteException;
}
