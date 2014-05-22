package com.cleanwise.service.api.session;

import com.cleanwise.service.api.process.ProcessActive;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.lang.reflect.Method;

/**
 * Title:        ProcessEngine
 * Description:  Remote Interface for ProcessEngine Stateless Session Bean
 * Purpose:      Provides access to the methods
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         26.04.2007
 * Time:         21:47:45
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public interface ProcessEngine extends EJBObject {

      public Object invoke(Method worker, Object o, Object[] inParams) throws RemoteException, Exception ;
}
