package com.cleanwise.service.api.session;

import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Title:        ProcessEngineHome
 * Description:  Home Interface for ProcessEngine Stateless Session Bean
 * Purpose:      Provides access to the methods associated with ProcessEngine schedule management
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         26.04.2007
 * Time:         21:46:07
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public interface ProcessEngineHome extends javax.ejb.EJBHome {

    /**
     * Simple create method used to get a reference to the ProcessEngine Bean
     * remote reference.
     *
     * @return The remote reference to be used to execute the methods
     *         associated with the ProcessEngineBean.
     * @throws javax.ejb.CreateException
     * @throws java.rmi.RemoteException
     */
    public ProcessEngine create() throws CreateException, RemoteException;
}

