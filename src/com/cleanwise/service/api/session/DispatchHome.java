package com.cleanwise.service.api.session;

import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Title:        DispatchHome
 * Description:  Home Interface for Dispatch Stateless Session Bean
 * Purpose:      Provides access to the add, update, and retrieval methods associated with work order schedule management
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         22.10.2007
 * Time:         3:24:37
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public interface DispatchHome extends javax.ejb.EJBHome {

    /**
     * Simple create method used to get a reference to the Dispatch Bean
     * remote reference.
     *
     * @return The remote reference to be used to execute the methods associated with the DispatchBean.
     * @throws javax.ejb.CreateException
     * @throws java.rmi.RemoteException
     */
    public Dispatch create() throws CreateException, RemoteException;

}
