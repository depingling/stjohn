package com.cleanwise.service.api.session;

import com.cleanwise.service.api.value.DispatchData;

import java.rmi.RemoteException;

/**
 * Title:        Dispatch
 * Description:  Remote Interface for Dispatch Stateless Session Bean
 * Purpose:      Provides access to the methods
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         22.10.2007
 * Time:         3:23:27
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public interface Dispatch extends javax.ejb.EJBObject {
 public DispatchData getDispatchByCriteria() throws RemoteException;
}
