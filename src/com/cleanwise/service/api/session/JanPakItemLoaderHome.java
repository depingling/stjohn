package com.cleanwise.service.api.session;

import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Title:        JanPakItemLoaderHome
 * Description:  Home Interface for JanPakItemLoader Stateless Session Bean
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 * Date:         13.08.2008
 * Time:         14:55:03
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public interface JanPakItemLoaderHome extends javax.ejb.EJBHome {

    /**
     * Simple create method used to get a reference to the JanPakItemLoader Bean remote reference.
     *
     * @return The remote reference to be used to execute the methods associated with the JanPakItemLoaderBean.
     * @throws CreateException
     * @throws RemoteException
     */
    public JanPakItemLoader create() throws CreateException, RemoteException;


}
