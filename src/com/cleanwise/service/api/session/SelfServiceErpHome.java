package com.cleanwise.service.api.session;

import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Title:        SelfServiceErpHome
 * Description:  Home Interface for SelfSerficeErp Stateless Session Bean
 * Purpose:      Provides access to the methods associated with self-service
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 * Date:         02.12.2008
 * Time:         8:57:41
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public interface SelfServiceErpHome extends javax.ejb.EJBHome {

    /**
     * Simple create method used to get a reference to the SelfServiceErp Bean
     * remote reference.
     *
     * @return The remote reference to be used to execute the methods
     *         associated with the SelfServiceErpBean.
     * @throws javax.ejb.CreateException
     * @throws java.rmi.RemoteException
     */

    public SelfServiceErp create() throws CreateException, RemoteException;
}
