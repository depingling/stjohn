package com.cleanwise.service.api.session;

import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Title:        WorkOrderHome
 * Description:  Home Interface for WorkOrder Stateless Session Bean
 * Purpose:      Provides access to the add, update, and retrieval methods associated with work order schedule management
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date: 09.10.2007
 * Time: 19:16:47
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public interface WorkOrderHome extends javax.ejb.EJBHome {

    /**
     * Simple create method used to get a reference to the WorkOrder Bean
     * remote reference.
     * @return The remote reference to be used to execute the methods
     *         associated with the WorkOrderBean.
     * @throws javax.ejb.CreateException
     * @throws java.rmi.RemoteException
     */

    public WorkOrder create() throws CreateException, RemoteException;
}
