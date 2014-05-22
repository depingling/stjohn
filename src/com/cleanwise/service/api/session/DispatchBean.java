package com.cleanwise.service.api.session;

import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.service.api.value.DispatchData;

import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Title:        DispatchBean
 * Description:  Bean implementation for Dispatch Session Bean
 * Purpose:      Ejb for scheduled work order management
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         22.10.2007
 * Time:         3:27:51
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public class DispatchBean extends ApplicationServicesAPI {

    /* Describe <code>ejbCreate</code> method here.
    *
    * @throws javax.ejb.CreateException if an error occurs
    * @throws java.rmi.RemoteException  if an error occurs
    */
    public void ejbCreate() throws CreateException, RemoteException {
    }

    public DispatchData getDispatchByCriteria() throws RemoteException{
        return null;
    }

}
