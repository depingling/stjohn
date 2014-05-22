package com.cleanwise.service.api.session;

/**
 * Title:        DataWarehouseHome
 * Description:  Home Interface for DataWarehouse Stateless Session Bean 
 * Purpose:      Provides access to the DataWarehouse functionality.  
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 * @author       Alexey Lukovnikov
 */

import java.rmi.RemoteException;
import javax.ejb.CreateException;


public interface DataWarehouseHome extends javax.ejb.EJBHome {
    /**
     * Simple create method used to get a reference to the DataWarehouseBean
     * remote reference.
     * 
     * @return The remote reference to be used to execute the methods associated
     *         with the DataWarehouseBean.
     * @throws CreateException
     * @throws RemoteException
     */
    public DataWarehouse create() throws CreateException, RemoteException;
}
