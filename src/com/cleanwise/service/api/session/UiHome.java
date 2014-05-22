package com.cleanwise.service.api.session;

import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Title:        UiHome
 * Description:  Home Interface for Ui Stateless Session Bean
 * Purpose:      Provides access to the add, update, and retrieval methods associated with ui management
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * Date:         02.06.2009
 * Time:         19:15:46
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public interface UiHome extends javax.ejb.EJBHome {

    /**
     * Simple create method used to get a reference to the Ui Bean
     * remote reference.
     * @return The remote reference to be used to execute the methods
     *         associated with the UiBean.
     * @throws javax.ejb.CreateException
     * @throws java.rmi.RemoteException
     */

    public Ui create() throws CreateException, RemoteException;
}
