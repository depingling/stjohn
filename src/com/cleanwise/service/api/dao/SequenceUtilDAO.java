package com.cleanwise.service.api.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;

/**
 * Class <code>SequenceUtilDAO</code>, provides the methods to work with new
 * order numbers for different stores, using specific database sequences.
 * 
 */
public class SequenceUtilDAO {
    public final static String CLW_ORDER_SEQUENCE_ = "CLW_ORDER_SEQUENCE_";

    private final static Object MUTEX = new Object();

    /**
     * Get next value from order sequence for store pStoreId.
     * 
     * @param pConn
     *            Connection to database.
     * @param pStoreId
     *            Store ID.
     * @return Return next value of sequence.
     * @throws SQLException
     */
    public static Integer getNextVal(Connection pConn, int pStoreId)
            throws SQLException {
        Integer result = null;
        String sql = "SELECT " + CLW_ORDER_SEQUENCE_ + pStoreId
                + ".nextval FROM DUAL";
        result = getInteger(pConn, sql);
        return result;
    }

    /**
     * Get current value from order sequence for store pStoreId.
     * 
     * @param pConn
     *            Connection to database.
     * @param pStoreId
     *            Store ID.
     * @return Return current value of sequence.
     * @throws SQLException
     */
    public static Integer getCurrVal(Connection pConn, int pStoreId)
            throws SQLException {
        Integer result = null;
        String sql = "SELECT last_number - 1 FROM user_sequences WHERE sequence_name  = '"
                + CLW_ORDER_SEQUENCE_ + pStoreId + "'";
        result = getInteger(pConn, sql);
        return result;
    }

    /**
     * 
     * Check availability of sequence for store pStoreId.
     * 
     * @param pConn
     *            Connection to database.
     * @param pStoreId
     *            Store ID.
     * @return true if exist sequence for store pStoreId.
     * @throws SQLException
     */
    public static boolean isExistSequenceForStore(Connection pConn, int pStoreId)
            throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        String sql = "SELECT sequence_name FROM user_sequences WHERE sequence_name = '"
                + CLW_ORDER_SEQUENCE_ + pStoreId + "'";
        try {
            statement = pConn.createStatement();
            resultSet = statement.executeQuery(sql);
            return resultSet.next();
        } finally {
            close(resultSet, statement);
        }
    }

    /**
     * Create sequence for generate new order nums for store pStoreId.
     * 
     * @param pConn
     *            Connection to database.
     * @param pStoreId
     *            Store ID.
     * @param pStartWith
     *            Start value for new sequence.
     * @throws SQLException
     */
    public static void createSequence(Connection pConn, int pStoreId,
            int pStartWith) throws SQLException {
        String sql = "CREATE SEQUENCE " + CLW_ORDER_SEQUENCE_ + pStoreId
                + " START WITH " + pStartWith + " INCREMENT BY 1 NOCACHE";
        Statement statement = null;
        try {
            statement = pConn.createStatement();
            statement.execute(sql);
        } finally {
            close(null, statement);
        }
    }

    /**
     * Get maximum order number for store pStoreId.
     * 
     * @param pConn
     *            Connection to database
     * @param pStoreId
     *            Store ID.
     * @return Maximum order number.
     * @throws SQLException
     */
    public static int getMaxOrderNum(Connection pConn, int pStoreId)
            throws SQLException {
        String sql = "SELECT Max(order_num) FROM clw_order WHERE store_id = "
                + pStoreId;
        Integer result = getInteger(pConn, sql);
        if (result == null) {
            return 999;
        } else {
            return result;
        }
    }

    /**
     * Helper method to fetch integer value from first column of first row of
     * SQL-query.
     * 
     * @param pConn
     *            Connection to database.
     * @param pSQL
     *            SQL query string.
     * @return Integer value if exist, else - null.
     * @throws SQLException
     */
    private static Integer getInteger(Connection pConn, String pSQL)
            throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        Integer result = null;
        try {
            statement = pConn.createStatement();
            resultSet = statement.executeQuery(pSQL);
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } finally {
            close(resultSet, statement);
        }
        return result;

    }

    /**
     * Helper method to close DB-objects.
     * 
     * @param resultSet
     *            ResultSet for close.
     * @param statement
     *            Statement for close.
     * @throws SQLException
     */
    private static void close(ResultSet resultSet, Statement statement)
            throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (statement != null) {
            statement.close();
        }
    }

    /**
     * Helper method to get new order number for store's order.
     * 
     * @param pConn
     *            Connection to database.
     * @param pStoreId
     *            Store ID.
     * @return Next order number.
     * @throws RemoteException
     * @throws SQLException
     */
    public static int getNextOrderNumber(Connection pConn, int pStoreId)
            throws RemoteException, SQLException {
        synchronized (MUTEX) {
            pStoreId = getStoreIdForOrderNumber(pConn, pStoreId);
//            boolean isExistSeq = isExistSequenceForStore(pConn, pStoreId);
//            if (isExistSeq == false) {
//                throw new RemoteException("^clwKey^error.sequence.doesnt.exist^clwKey^^clwParam^"
//                        + CLW_ORDER_SEQUENCE_ + pStoreId + "^clwParam^");
//            }
//            int currVal = getCurrVal(pConn, pStoreId);
//            int maxOrderNum = getMaxOrderNum(pConn, pStoreId);
//            if (currVal < maxOrderNum) {
//                throw new RemoteException("^clwKey^error.sequence.doesnt.match^clwKey^^clwParam^"
//                        + CLW_ORDER_SEQUENCE_ + pStoreId + "^clwParam^");
//            }
            int nextVal = getNextVal(pConn, pStoreId);
            return nextVal;
        }
    }

    /**
     * Checks exist order number sequence for concrete store and create if need.
     * 
     * @param pConn
     *            Connection to database.
     * @param pStoreId
     *            Store ID.
     * @throws RemoteException
     * @throws SQLException
     */
    public static void checkAndCreateIfNeed(Connection pConn, int pStoreId)
            throws RemoteException, SQLException {
        pStoreId = getStoreIdForOrderNumber(pConn, pStoreId);
        if (isExistSequenceForStore(pConn, pStoreId) == false) {
            int pStartWith = getMaxOrderNum(pConn, pStoreId);
            createSequence(pConn, pStoreId, pStartWith + 1);
        } else {
            int currVal = getCurrVal(pConn, pStoreId);
            int maxOrderNum = getMaxOrderNum(pConn, pStoreId);
            if (currVal < maxOrderNum) {
                throw new RemoteException("^clwKey^error.sequence.doesnt.match^clwKey^^clwParam^"
                        + CLW_ORDER_SEQUENCE_ + pStoreId + "^clwParam^");
            }
        }
    }

    /**
     * Return store ID to use correct sequence.
     * 
     * @param pConn
     *            Connection to database.
     * @param pStoreId
     *            Store ID.
     * @return
     * @throws RemoteException
     * @throws SQLException
     */
    public static int getStoreIdForOrderNumber(Connection pConn, int pStoreId)
            throws RemoteException, SQLException {
        int numStoreId = pStoreId;
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pStoreId);
        dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.ORDER_NUMBERING_STORE_ID);
        PropertyDataVector propDV = PropertyDataAccess.select(pConn, dbc);
        for (int ii = 0; ii < propDV.size(); ii++) {
            PropertyData pD = (PropertyData) propDV.get(ii);
            String val = pD.getValue();
            int nsId = 0;
            try {
                nsId = Integer.parseInt(val);
            } catch (Exception exc) {
                String mess = "Wrong store property format. Property name = "
                        + RefCodeNames.PROPERTY_TYPE_CD.ORDER_NUMBERING_STORE_ID
                        + " Property value = " + val + " Store Id = "
                        + pStoreId;
                throw new RemoteException(mess);
            }
            if (ii == 0) {
                numStoreId = nsId;
            } else if (numStoreId != nsId) {
                String mess = "More than one store property value. Property name = "
                        + RefCodeNames.PROPERTY_TYPE_CD.ORDER_NUMBERING_STORE_ID
                        + " Store Id = " + pStoreId;
                System.err.println(mess);
            }
        }
        return numStoreId;
    }
}
