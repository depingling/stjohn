package com.cleanwise.service.api.session;

import java.rmi.RemoteException;
import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * Title:        JanPakSiteLoader
 * Description:  RemoteInterface for JanPakSiteLoader Stateless Session Bean
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 * Date:         13.08.2008
 * Time:         14:54:07
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public interface JanPakSiteLoader extends javax.ejb.EJBObject {

    public void match(int pStoreId, String pTable, String pUser) throws RemoteException;

    public void prepare(String pTable, String pUser) throws RemoteException;

    public void insert(int pStoreId, String pTable, String pUser) throws RemoteException;

    public void update(String pTable, String pUser) throws RemoteException;

    public void delete(int pStoreId, String pTable, String pUser) throws RemoteException;

    public void accept(byte[] pData, String pTable, String pFileName, Date pModDate, String pUser) throws RemoteException;

    public StringBuffer report(String pTable) throws RemoteException;
}
