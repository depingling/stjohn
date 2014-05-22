package com.cleanwise.service.api.session;

/**
 * Title:        HistoryBean
 * Description:  Bean implementation for Language Stateless Session Bean
 * Purpose:      Provides access to the services for managing history information.
 * Copyright:    Copyright (c) 2010
 * Company:      eSpendWise, Inc.
 * @author       John Esielionis.
 */

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.dao.HistoryDataAccess;
import com.cleanwise.service.api.dao.HistoryObjectDataAccess;
import com.cleanwise.service.api.dao.HistorySecurityDataAccess;
import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.value.HistoryData;
import com.cleanwise.service.api.value.HistoryObjectData;
import com.cleanwise.service.api.value.HistorySecurityData;

/**
 * <code>History</code> stateless session bean.
 */
public class HistoryBean extends ApplicationServicesAPI {

    private static final Logger log = Logger.getLogger(HistoryBean.class);
	
	/**
     * Creates a new <code>HistoryBean</code> instance.
     *
     */
	public HistoryBean() {}
	
	/**
     * Describe <code>ejbCreate</code> method here.
     *
     * @exception CreateException if an error occurs
     * @exception RemoteException if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {}
    

    public void addHistory(HistoryData historyData, List<HistoryObjectData> historyObjectDatas, 
    		List <HistorySecurityData> historySecurityDatas) throws RemoteException {
        Connection conn = null;
        try {
            //Create record for history data object
            conn = getConnection();
            Date activityDate = historyData.getActivityDate();
            historyData = HistoryDataAccess.insert(conn, historyData);
            //HistoryDataAccess doesn't retain the time component of the activity date, so it must
            //be set explicitly
            String sql = "UPDATE CLW_HISTORY SET ACTIVITY_DATE = ? WHERE HISTORY_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setTimestamp(1,DBAccess.toSQLTimestamp(activityDate));
            pstmt.setInt(2,historyData.getHistoryId());
            pstmt.executeUpdate();
            
            //if there are any history object entities, create records for them
            if (!historyObjectDatas.isEmpty()) {
            	Iterator<HistoryObjectData> objectIterator = historyObjectDatas.iterator();
            	while (objectIterator.hasNext()) {
            		HistoryObjectData historyObject = objectIterator.next();
                    historyObject.setHistoryId(historyData.getHistoryId());
                    historyObject = HistoryObjectDataAccess.insert(conn, historyObject);
            	}
            }
            
            //if there are any history security entities, create records for them
            if (!historySecurityDatas.isEmpty()) {
            	Iterator<HistorySecurityData> securityIterator = historySecurityDatas.iterator();
            	while (securityIterator.hasNext()) {
            		HistorySecurityData historySecurity = securityIterator.next();
                    historySecurity.setHistoryId(historyData.getHistoryId());
                    historySecurity = HistorySecurityDataAccess.insert(conn, historySecurity);
            	}
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage(), e);
        }
        finally {
            closeConnection(conn);
        }
    }

}
