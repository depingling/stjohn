package com.cleanwise.service.api.session;

/**
 * Title:        InboundFiles
 * Description:  Remote Interface for InboundFiles Stateless Session Bean
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author       
 *
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;

import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.value.InboundData;
import com.cleanwise.service.api.value.InboundDataVector;

public interface InboundFiles extends EJBObject {

    public int getInboundFileCount() throws RemoteException;

    public InboundData getInboundFileById(int id) throws RemoteException;

    public InboundDataVector getInboundFilesByCriteria(DBCriteria dbc) throws RemoteException;

    public InboundDataVector getInboundFilesByCriteria(DBCriteria dbc, int maxRows) throws RemoteException;

    public InboundDataVector getInboundFilesByCriteria(Date dateFrom, 
        Date dateTo, String fileName, String partnerKey, String url) throws RemoteException;

    public InboundDataVector getInboundFilesByCriteria(Date dateFrom, 
        Date dateTo, String fileName, String partnerKey, String url, int maxRows) throws RemoteException;

}
