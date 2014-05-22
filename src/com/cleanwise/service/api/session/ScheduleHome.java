package com.cleanwise.service.api.session;

import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Title:        ScheduleHome
 * Description:  Home Interface for Schedule Stateless Session Bean
 * Purpose:      Provides access to the methods associated with schedule
 *
 * @author Deping
 */
public interface ScheduleHome extends javax.ejb.EJBHome {

    /**
     * Simple create method used to get a reference to the Schedule Bean
     * remote reference.
     *
     * @return The remote reference to be used to execute the methods
     *         associated with the SelfServiceErpBean.
     * @throws javax.ejb.CreateException
     * @throws java.rmi.RemoteException
     */

    public Schedule create() throws CreateException, RemoteException;
}
