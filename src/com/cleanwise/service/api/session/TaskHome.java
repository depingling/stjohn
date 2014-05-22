package com.cleanwise.service.api.session;

import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Title:        TaskHome
 * Description:  Home Interface for Task Stateless Session Bean
 * Purpose:      Provides access to the add, update, and retrieval
 *               methods associated with Task schedule management
 *
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         05.04.2007
 * Time:         14:38:57
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public interface TaskHome extends javax.ejb.EJBHome {

    /**
     * Simple create method used to get a reference to the Task Bean
     * remote reference.
     *
     * @return The remote reference to be used to execute the methods
     *         associated with the TaskBean.
     * @throws javax.ejb.CreateException
     * @throws java.rmi.RemoteException
     */

    public Task create() throws CreateException, RemoteException;

}
