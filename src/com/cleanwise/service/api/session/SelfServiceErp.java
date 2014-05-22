package com.cleanwise.service.api.session;

import com.cleanwise.service.api.value.InvoiceCustDescData;

import java.rmi.RemoteException;

/**
 * Title:        SelfServiceErp
 * Description:  Remote nterface for SelfSerficeErp Stateless Session Bean
 * Purpose:      Provides access to the methods associated with self-service
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 * Date:         02.12.2008
 * Time:         8:57:50
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public interface SelfServiceErp extends javax.ejb.EJBObject {

    public InvoiceCustDescData service(int pInvoiceId) throws RemoteException;

    public InvoiceCustDescData service(int pInvoiceId, boolean pReadOnly) throws RemoteException;
}
