package com.cleanwise.service.api.session;

/**
 * Title:        InboundFilesBean
 * Description:  Bean implementation for InboundFiles Stateless Session Bean
 * Purpose:      Provides InboundFiles data processing
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author       
 *
 */

import com.cleanwise.service.api.framework.UtilityServicesAPI;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.value.InboundData;
import com.cleanwise.service.api.value.InboundDataVector;
import com.cleanwise.service.api.dao.InboundDataAccess;

import javax.ejb.CreateException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class InboundFilesBean extends UtilityServicesAPI
{
    private static final String className = "InboundFilesBean";

    /**
     *
     */
    public InboundFilesBean() {}

    /**
     *
     * @throws java.rmi.RemoteException
     * @throws javax.ejb.CreateException
     */
    public void ejbCreate() throws CreateException, RemoteException {}

    public int getInboundFileCount() throws RemoteException {
        int inboundCount = 0;
        Connection connection = null;
        try {
            connection = getConnection();
            String sql = "SELECT COUNT(INBOUND_ID) FROM CLW_INBOUND";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                inboundCount = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RemoteException(ex.getMessage());
        }
        finally {
            closeConnection(connection);
        }       
        return inboundCount;
    }

    public InboundData getInboundFileById(int id) throws RemoteException {
        InboundData inbound = null;
        Connection connection = null;
        try {
            connection = getConnection();
            inbound = InboundDataAccess.select(connection, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RemoteException(ex.getMessage());
        }
        finally {
            closeConnection(connection);
        }
        return inbound;
    }

    public InboundDataVector getInboundFilesByCriteria(DBCriteria dbc, int maxRows) throws RemoteException {
        InboundDataVector inboundDataVector = null;
        Connection connection = null;
        try {
            connection = getConnection();
            if (maxRows > 0) {
                inboundDataVector = InboundDataAccess.select(connection, dbc, maxRows);
            } else {
                inboundDataVector = InboundDataAccess.select(connection, dbc);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RemoteException(ex.getMessage());
        }
        finally {
            closeConnection(connection);
        }
        return inboundDataVector;
    }

    public InboundDataVector getInboundFilesByCriteria(DBCriteria dbc) throws RemoteException {
        return getInboundFilesByCriteria(dbc, -1);
    }

    public InboundDataVector getInboundFilesByCriteria(Date dateFrom, 
        Date dateTo, String fileName, String partnerKey, String url, int maxRows) throws RemoteException {
        InboundDataVector inboundDataVector = null;
        Connection connection = null;
        try {
            connection = getConnection();
            DBCriteria dbc = new DBCriteria();
            if (dateFrom != null) {
                dbc.addGreaterOrEqual(InboundDataAccess.ADD_DATE, dateFrom);
            }
            if (dateTo != null) {
                dbc.addLessOrEqual(InboundDataAccess.ADD_DATE, dateTo);
            }
            if (fileName != null && fileName.length() > 0) {
                dbc.addContainsIgnoreCase(InboundDataAccess.FILE_NAME, fileName);
            }
            if (partnerKey != null && partnerKey.length() > 0) {
                dbc.addContainsIgnoreCase(InboundDataAccess.PARTNER_KEY, partnerKey);
            }
            if (url != null && url.length() > 0) {
                dbc.addContainsIgnoreCase(InboundDataAccess.URL, url);
            }
            if (maxRows > 0) {
                inboundDataVector = InboundDataAccess.select(connection, dbc, maxRows);
            } else {
                inboundDataVector = InboundDataAccess.select(connection, dbc);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RemoteException(ex.getMessage());
        }
        finally {
            closeConnection(connection);
        }
        return inboundDataVector;  
    }

    public InboundDataVector getInboundFilesByCriteria(Date dateFrom, 
        Date dateTo, String fileName, String partnerKey, String url) throws RemoteException {
        return getInboundFilesByCriteria(dateFrom, dateTo, fileName, partnerKey, url, -1);
    }

}
