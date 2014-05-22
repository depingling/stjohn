package com.cleanwise.service.api.session;

import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Title:        ProcessHome
 * Description:  Home Interface for Process Stateless Session Bean
 * Purpose:      Provides access to the add, update, and retrieval
 *               methods associated with process schedule management
 *
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         05.04.2007
 * Time:         14:32:52
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public interface ProcessHome extends javax.ejb.EJBHome {

    /**
     * Simple create method used to get a reference to the Process Bean
     * remote reference.
     * @return The remote reference to be used to execute the methods
     *         associated with the ProcessBean.
     * @throws javax.ejb.CreateException
     * @throws java.rmi.RemoteException
     */

    public Process create() throws CreateException, RemoteException;

}
