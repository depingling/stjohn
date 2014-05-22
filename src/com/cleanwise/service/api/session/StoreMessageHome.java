package com.cleanwise.service.api.session;

/**
 * Title:        StoreMessageHome

 * Description:  Home Interface for StoreMessage Stateless Session Bean
 * Purpose:      Provides access to the services for managing the store message information and configurations.
 */

import javax.ejb.*;
import java.rmi.*;

public interface StoreMessageHome extends EJBHome {
    /**
     * Simple create method used to get a reference to the StoreMessage Bean
     * remote reference.
     * 
     * @return The remote reference to be used to execute the methods associated
     *         with the StoreMessageBean.
     * @throws CreateException
     * @throws RemoteException
     * @see StoreMessage
     * @see CreateException
     * @see RemoteException
     */
    public StoreMessage create() throws CreateException, RemoteException;
}
