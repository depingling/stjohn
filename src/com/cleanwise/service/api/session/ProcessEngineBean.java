package com.cleanwise.service.api.session;

import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.service.api.process.ProcessActive;
import com.cleanwise.service.api.value.ProcessData;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.RefCodeNames;

import javax.ejb.CreateException;
import java.rmi.RemoteException;
import java.lang.reflect.Method;
import com.cleanwise.service.api.util.DataNotFoundException;

/**
 * Title:        ProcessEngineBean
 * Description:  Bean implementation for ProcessEngine Session Bean
 * Purpose:      Ejb for scheduled task management
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         26.04.2007
 * Time:         21:54:12
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class ProcessEngineBean extends ApplicationServicesAPI {

    private final String className="ProcessEngineBean";

    /**
     * Describe <code>ejbCreate</code> method here.
     *
     * @throws javax.ejb.CreateException if an error occurs
     * @throws java.rmi.RemoteException  if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {
    }

    public Object invoke(Method worker, Object o, Object[] inParams) throws RemoteException, Exception {
      return worker.invoke(o, inParams);

 /*       try {
            return worker.invoke(o, inParams);
        } catch (Exception e) {
              throw new Exception(e.getMessage(), e);
        }*/
    }
}
