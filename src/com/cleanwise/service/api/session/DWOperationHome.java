package com.cleanwise.service.api.session;

import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Title:        DWDimHome
 * Description:  Home Interface for DWDim Stateless Session Bean
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 * Date:         08.08.2008
 * Time:         09:55:03
 *
 * @author N Guschina, TrinitySoft, Inc.
 */

public interface DWOperationHome extends javax.ejb.EJBHome {

    /**
     * Simple create method used to get a reference to the DWDim Bean remote reference.
     *
     * @return The remote reference to be used to execute the methods associated with the DWDimBean.
     * @throws CreateException
     * @throws RemoteException
     */
    public DWOperation create() throws CreateException, RemoteException;


}
