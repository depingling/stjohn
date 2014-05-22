package com.cleanwise.service.api.session;

import java.io.ByteArrayOutputStream;
import java.rmi.RemoteException;

/**
 * Title:        JanPakInvoiceLoader
 * Description:  RemoteInterface for JanPakInvoiceLoader Stateless Session Bean
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 * Date:         08.09.2008
 * Time:         9:34:07
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public interface JanPakInvoiceLoader extends javax.ejb.EJBObject {

    public void accept(byte[] pData, String pTable, String pFileName, java.util.Date pModDate, String pUser) throws RemoteException;

    public void prepare(int pStoreId, int pDistributorId, String pTable) throws RemoteException;

    public void match(int pStoreId, int pDistributorId, String pTable) throws RemoteException;

    public void insert(int pStoreId, int pDistributorId, String pTable, String pUser) throws RemoteException;

    public void  dropWorkTables(String pTable) throws RemoteException;

    public StringBuffer report(String pTable) throws RemoteException;

}
