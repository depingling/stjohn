package com.cleanwise.service.api.session;

import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Title:        JanPakSiteLoaderHome
 * Description:  Home Interface for JanPakSiteLoader Stateless Session Bean
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 * Date:         13.08.2008
 * Time:         14:53:03
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public interface JanPakSiteLoaderHome extends javax.ejb.EJBHome {

    /**
     * Simple create method used to get a reference to the JanPakSiteLoader Bean remote reference.
     *
     * @return The remote reference to be used to execute the methods associated with the JanPakSiteLoaderBean.
     * @throws CreateException
     * @throws RemoteException
     */
    public JanPakSiteLoader create() throws CreateException, RemoteException;


}
