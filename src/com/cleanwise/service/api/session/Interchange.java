package com.cleanwise.service.api.session;

/**
 * Title:        Interchange
 * Description:  Remote Interface for Interchange Stateless Session Bean
 * Purpose:      Provides access to interchange methods
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * @author       Yuriy Remizov
 *
 */

import java.rmi.*;

import com.cleanwise.service.api.value.*;


public interface Interchange extends javax.ejb.EJBObject {

    public void saveInboundData(InboundData inbound, byte[] enc, byte[] dec) throws RemoteException;

    public int saveInboundData(InboundData inbound, String pUser) throws RemoteException;

    public byte[] getInboundDecContent(int pInboundId) throws RemoteException;

    public void updateInboundDecContent(int pInboundId, byte[] pData, String pUser) throws RemoteException;
}

