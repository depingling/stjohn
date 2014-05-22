package com.cleanwise.service.api.session;

/**
 * Title:        DataUploaderHelperHome
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2010
 * Company:      eSpendwise, Inc.
 */


import java.rmi.RemoteException;
import javax.ejb.CreateException;


public interface DataUploaderHelperHome extends javax.ejb.EJBHome {
    /**
     * Simple create method used to get a reference to the DataUploaderHelperBean
     * remote reference.
     * 
     * @return The remote reference to be used to execute the methods associated
     *         with the DataUploaderHelperBean.
     * @throws CreateException
     * @throws RemoteException
     */
    public DataUploaderHelper create() throws CreateException, RemoteException;
}
