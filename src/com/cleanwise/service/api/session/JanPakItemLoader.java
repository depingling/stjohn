package com.cleanwise.service.api.session;

import java.rmi.RemoteException;
import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * Title:        JanPakItemLoader
 * Description:  RemoteInterface for JanPakItemLoader Stateless Session Bean
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 * Date:         18.08.2008
 * Time:         10:59:07
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public interface JanPakItemLoader extends javax.ejb.EJBObject {

    public void accept(byte[] pData, String pTable, String pFileName, Date pModDate, String pUser) throws RemoteException;

    public void prepare(String pTable, String pFilterTable, String pUser) throws RemoteException;

    public void match(int pStoreId, int pDistributorId, int pStoreCatalogId, String pTable, String pUser) throws RemoteException;

    public void update(int pDistributorId, int pStoreCatalogId, String pTable, String pUser) throws RemoteException;

    public void insert(int pStoreId, int pStoreCatalogId, int pDistributorId, String pTable, String pUser) throws RemoteException;

    public void delete(int pStoreCatalogId, String pTable, String pUser) throws RemoteException;

    public void dropWorkTables(String pTable) throws RemoteException;
}
