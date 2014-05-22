package com.cleanwise.service.api.session;

import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Title:        JanPakInvoiceLoaderHome
 * Description:  Home Interface for JanPakInvoiceLoader Stateless Session Bean
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 * Date:         08.08.2008
 * Time:         09:55:03
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public interface JanPakInvoiceLoaderHome extends javax.ejb.EJBHome {

    /**
     * Simple create method used to get a reference to the JanPakInvoiceLoader Bean remote reference.
     *
     * @return The remote reference to be used to execute the methods associated with the JanPakInvoiceLoaderBean.
     * @throws CreateException
     * @throws RemoteException
     */
    public JanPakInvoiceLoader create() throws CreateException, RemoteException;


}
