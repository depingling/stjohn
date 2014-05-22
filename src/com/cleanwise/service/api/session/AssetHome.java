package com.cleanwise.service.api.session;

import javax.ejb.*;
import java.rmi.*;


/**
 * Title:        AssetHome
 * Description:  Home Interface for Asset Stateless Session Bean
 * Purpose:      Provides access to the add, update, and retrieval methods associated with asset schedule management
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date:         19.12.2006
 * Time:         8:38:17
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public interface AssetHome extends javax.ejb.EJBHome {

    /**
     * Simple create method used to get a reference to the Asset Bean
     * remote reference.
     * @return The remote reference to be used to execute the methods
     *         associated with the AssetBean.
     * @throws CreateException
     * @throws RemoteException
     */

    public Asset create() throws CreateException, RemoteException;

}
