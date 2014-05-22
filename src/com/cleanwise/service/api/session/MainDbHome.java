package com.cleanwise.service.api.session;

/**
 * Title:        StoreMessageHome

 * Description:  Home Interface for MainDb Stateless Session Bean
 * Purpose:      Provides access to the services for managing the Main Db information and configurations.
 */

import javax.ejb.*;
import java.rmi.*;

public interface MainDbHome extends EJBHome {
    /**
     * Simple create method used to get a reference to the MainDb Bean
     * remote reference.
     * 
     * @return The remote reference to be used to execute the methods associated
     *         with the MainDbBean.
     * @throws CreateException
     * @throws RemoteException
     * @see MainDb
     * @see CreateException
     * @see RemoteException
     */
    public MainDb create() throws CreateException, RemoteException;
}
