package com.cleanwise.service.api.session;

import javax.ejb.*;
import java.rmi.*;


/**
 * Title:        WarrantyHome
 * Description:  Home Interface for Warranty Stateless Session Bean
 * Purpose:      Provides access to the add, update, and retrieval methods associated with warranty schedule management
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         16.09.2007
 * Time:         9:58:22
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public interface WarrantyHome extends javax.ejb.EJBHome {

    /**
     * Simple create method used to get a reference to the Warranty Bean
     * remote reference.
     * @return The remote reference to be used to execute the methods
     *         associated with the WarrantyBean.
     * @throws CreateException
     * @throws RemoteException
     */

    public Warranty create() throws CreateException, RemoteException;
}

