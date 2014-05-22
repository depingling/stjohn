
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        InventoryOrderLogDataAccess
 * Description:  This class is used to build access methods to the CLW_INVENTORY_ORDER_LOG table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.InventoryOrderLogData;
import com.cleanwise.service.api.value.InventoryOrderLogDataVector;
import com.cleanwise.service.api.framework.DataAccessImpl;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.*;

/**
 * <code>InventoryOrderLogDataAccess</code>
 */
public class InventoryOrderLogDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(InventoryOrderLogDataAccess.class.getName());

    /** <code>CLW_INVENTORY_ORDER_LOG</code> table name */
	/* Primary key: INVENTORY_ORDER_LOG_ID */
	
    public static final String CLW_INVENTORY_ORDER_LOG = "CLW_INVENTORY_ORDER_LOG";
    
    /** <code>INVENTORY_ORDER_LOG_ID</code> INVENTORY_ORDER_LOG_ID column of table CLW_INVENTORY_ORDER_LOG */
    public static final String INVENTORY_ORDER_LOG_ID = "INVENTORY_ORDER_LOG_ID";
    /** <code>SITE_ID</code> SITE_ID column of table CLW_INVENTORY_ORDER_LOG */
    public static final String SITE_ID = "SITE_ID";
    /** <code>ORDER_CUTOFF_DATE</code> ORDER_CUTOFF_DATE column of table CLW_INVENTORY_ORDER_LOG */
    public static final String ORDER_CUTOFF_DATE = "ORDER_CUTOFF_DATE";
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_INVENTORY_ORDER_LOG */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_INVENTORY_ORDER_LOG */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_INVENTORY_ORDER_LOG */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_INVENTORY_ORDER_LOG */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_INVENTORY_ORDER_LOG */
    public static final String MOD_BY = "MOD_BY";
    /** <code>ORDER_DELIVERY_DATE</code> ORDER_DELIVERY_DATE column of table CLW_INVENTORY_ORDER_LOG */
    public static final String ORDER_DELIVERY_DATE = "ORDER_DELIVERY_DATE";

    /**
     * Constructor.
     */
    public InventoryOrderLogDataAccess()
    {
    }

    /**
     * Gets a InventoryOrderLogData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pInventoryOrderLogId The key requested.
     * @return new InventoryOrderLogData()
     * @throws            SQLException
     */
    public static InventoryOrderLogData select(Connection pCon, int pInventoryOrderLogId)
        throws SQLException, DataNotFoundException {
        InventoryOrderLogData x=null;
        String sql="SELECT INVENTORY_ORDER_LOG_ID,SITE_ID,ORDER_CUTOFF_DATE,ORDER_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_DELIVERY_DATE FROM CLW_INVENTORY_ORDER_LOG WHERE INVENTORY_ORDER_LOG_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pInventoryOrderLogId=" + pInventoryOrderLogId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pInventoryOrderLogId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=InventoryOrderLogData.createValue();
            
            x.setInventoryOrderLogId(rs.getInt(1));
            x.setSiteId(rs.getInt(2));
            x.setOrderCutoffDate(rs.getDate(3));
            x.setOrderId(rs.getInt(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setOrderDeliveryDate(rs.getDate(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("INVENTORY_ORDER_LOG_ID :" + pInventoryOrderLogId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a InventoryOrderLogDataVector object that consists
     * of InventoryOrderLogData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new InventoryOrderLogDataVector()
     * @throws            SQLException
     */
    public static InventoryOrderLogDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a InventoryOrderLogData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_INVENTORY_ORDER_LOG.INVENTORY_ORDER_LOG_ID,CLW_INVENTORY_ORDER_LOG.SITE_ID,CLW_INVENTORY_ORDER_LOG.ORDER_CUTOFF_DATE,CLW_INVENTORY_ORDER_LOG.ORDER_ID,CLW_INVENTORY_ORDER_LOG.ADD_DATE,CLW_INVENTORY_ORDER_LOG.ADD_BY,CLW_INVENTORY_ORDER_LOG.MOD_DATE,CLW_INVENTORY_ORDER_LOG.MOD_BY,CLW_INVENTORY_ORDER_LOG.ORDER_DELIVERY_DATE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated InventoryOrderLogData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs) throws SQLException{
         return parseResultSet(rs,0);
    }

    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@param int the offset to use which is useful when using 1 query to populate multiple objects
    *@returns a populated InventoryOrderLogData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         InventoryOrderLogData x = InventoryOrderLogData.createValue();
         
         x.setInventoryOrderLogId(rs.getInt(1+offset));
         x.setSiteId(rs.getInt(2+offset));
         x.setOrderCutoffDate(rs.getDate(3+offset));
         x.setOrderId(rs.getInt(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         x.setOrderDeliveryDate(rs.getDate(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the InventoryOrderLogData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a InventoryOrderLogDataVector object that consists
     * of InventoryOrderLogData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new InventoryOrderLogDataVector()
     * @throws            SQLException
     */
    public static InventoryOrderLogDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT INVENTORY_ORDER_LOG_ID,SITE_ID,ORDER_CUTOFF_DATE,ORDER_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_DELIVERY_DATE FROM CLW_INVENTORY_ORDER_LOG");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_INVENTORY_ORDER_LOG.INVENTORY_ORDER_LOG_ID,CLW_INVENTORY_ORDER_LOG.SITE_ID,CLW_INVENTORY_ORDER_LOG.ORDER_CUTOFF_DATE,CLW_INVENTORY_ORDER_LOG.ORDER_ID,CLW_INVENTORY_ORDER_LOG.ADD_DATE,CLW_INVENTORY_ORDER_LOG.ADD_BY,CLW_INVENTORY_ORDER_LOG.MOD_DATE,CLW_INVENTORY_ORDER_LOG.MOD_BY,CLW_INVENTORY_ORDER_LOG.ORDER_DELIVERY_DATE FROM CLW_INVENTORY_ORDER_LOG");
                where = pCriteria.getSqlClause("CLW_INVENTORY_ORDER_LOG");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_INVENTORY_ORDER_LOG.equals(otherTable)){
                        sqlBuf.append(",");
                        sqlBuf.append(otherTable);
				}
                }
        }

        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        if ( pMaxRows > 0 ) {
            // Insure that only positive values are set.
              stmt.setMaxRows(pMaxRows);
        }
        ResultSet rs=stmt.executeQuery(sql);
        InventoryOrderLogDataVector v = new InventoryOrderLogDataVector();
        while (rs.next()) {
            InventoryOrderLogData x = InventoryOrderLogData.createValue();
            
            x.setInventoryOrderLogId(rs.getInt(1));
            x.setSiteId(rs.getInt(2));
            x.setOrderCutoffDate(rs.getDate(3));
            x.setOrderId(rs.getInt(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setOrderDeliveryDate(rs.getDate(9));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a InventoryOrderLogDataVector object that consists
     * of InventoryOrderLogData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for InventoryOrderLogData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new InventoryOrderLogDataVector()
     * @throws            SQLException
     */
    public static InventoryOrderLogDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        InventoryOrderLogDataVector v = new InventoryOrderLogDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT INVENTORY_ORDER_LOG_ID,SITE_ID,ORDER_CUTOFF_DATE,ORDER_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_DELIVERY_DATE FROM CLW_INVENTORY_ORDER_LOG WHERE INVENTORY_ORDER_LOG_ID IN (");

        if ( pIdVector.size() > 0 ) {
            sqlBuf.append(pIdVector.get(0).toString());
            int vecsize = pIdVector.size();
            for ( int idx = 1; idx < vecsize; idx++ ) {
                sqlBuf.append("," + pIdVector.get(idx).toString());
            }
            sqlBuf.append(")");


            String sql = sqlBuf.toString();
            if (log.isDebugEnabled()) {
                log.debug("SQL: " + sql);
            }

            Statement stmt = pCon.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            InventoryOrderLogData x=null;
            while (rs.next()) {
                // build the object
                x=InventoryOrderLogData.createValue();
                
                x.setInventoryOrderLogId(rs.getInt(1));
                x.setSiteId(rs.getInt(2));
                x.setOrderCutoffDate(rs.getDate(3));
                x.setOrderId(rs.getInt(4));
                x.setAddDate(rs.getTimestamp(5));
                x.setAddBy(rs.getString(6));
                x.setModDate(rs.getTimestamp(7));
                x.setModBy(rs.getString(8));
                x.setOrderDeliveryDate(rs.getDate(9));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a InventoryOrderLogDataVector object of all
     * InventoryOrderLogData objects in the database.
     * @param pCon An open database connection.
     * @return new InventoryOrderLogDataVector()
     * @throws            SQLException
     */
    public static InventoryOrderLogDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT INVENTORY_ORDER_LOG_ID,SITE_ID,ORDER_CUTOFF_DATE,ORDER_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_DELIVERY_DATE FROM CLW_INVENTORY_ORDER_LOG";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        InventoryOrderLogDataVector v = new InventoryOrderLogDataVector();
        InventoryOrderLogData x = null;
        while (rs.next()) {
            // build the object
            x = InventoryOrderLogData.createValue();
            
            x.setInventoryOrderLogId(rs.getInt(1));
            x.setSiteId(rs.getInt(2));
            x.setOrderCutoffDate(rs.getDate(3));
            x.setOrderId(rs.getInt(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setOrderDeliveryDate(rs.getDate(9));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * InventoryOrderLogData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT INVENTORY_ORDER_LOG_ID FROM CLW_INVENTORY_ORDER_LOG");
        String where = pCriteria.getSqlClause();
        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        IdVector v = new IdVector();
        while (rs.next()) {
            Integer x = new Integer(rs.getInt(1));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of requested
     * objects in the database.
     * @param pCon An open database connection.
     * @param pIdName A column name
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, String pIdName, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVENTORY_ORDER_LOG");
        String where = pCriteria.getSqlClause();
        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        IdVector v = new IdVector();
        while (rs.next()) {
            Integer x = new Integer(rs.getInt(1));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }


    /**
     * Gets an sql statement to request ids of
     * objects in the database.
     * @param pIdName A column name
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return String
     */
    public static String getSqlSelectIdOnly(String pIdName, DBCriteria pCriteria)
    {
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVENTORY_ORDER_LOG");
        String where = pCriteria.getSqlClause();
        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL text: " + sql);
        }

        return sql;
    }

    /**
     * Inserts a InventoryOrderLogData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InventoryOrderLogData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new InventoryOrderLogData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InventoryOrderLogData insert(Connection pCon, InventoryOrderLogData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_INVENTORY_ORDER_LOG_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_INVENTORY_ORDER_LOG_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setInventoryOrderLogId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_INVENTORY_ORDER_LOG (INVENTORY_ORDER_LOG_ID,SITE_ID,ORDER_CUTOFF_DATE,ORDER_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_DELIVERY_DATE) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getInventoryOrderLogId());
        pstmt.setInt(2,pData.getSiteId());
        pstmt.setDate(3,DBAccess.toSQLDate(pData.getOrderCutoffDate()));
        pstmt.setInt(4,pData.getOrderId());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());
        pstmt.setDate(9,DBAccess.toSQLDate(pData.getOrderDeliveryDate()));

        if (log.isDebugEnabled()) {
            log.debug("SQL:   INVENTORY_ORDER_LOG_ID="+pData.getInventoryOrderLogId());
            log.debug("SQL:   SITE_ID="+pData.getSiteId());
            log.debug("SQL:   ORDER_CUTOFF_DATE="+pData.getOrderCutoffDate());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   ORDER_DELIVERY_DATE="+pData.getOrderDeliveryDate());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setInventoryOrderLogId(0);
        exceptionMessage=e.getMessage();
        }
        finally{
        pstmt.close();
        }

        if(exceptionMessage!=null) {
                 throw new SQLException(exceptionMessage);
        }

        return pData;
    }

    /**
     * Updates a InventoryOrderLogData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InventoryOrderLogData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InventoryOrderLogData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_INVENTORY_ORDER_LOG SET SITE_ID = ?,ORDER_CUTOFF_DATE = ?,ORDER_ID = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,ORDER_DELIVERY_DATE = ? WHERE INVENTORY_ORDER_LOG_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getSiteId());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getOrderCutoffDate()));
        pstmt.setInt(i++,pData.getOrderId());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getOrderDeliveryDate()));
        pstmt.setInt(i++,pData.getInventoryOrderLogId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SITE_ID="+pData.getSiteId());
            log.debug("SQL:   ORDER_CUTOFF_DATE="+pData.getOrderCutoffDate());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   ORDER_DELIVERY_DATE="+pData.getOrderDeliveryDate());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a InventoryOrderLogData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInventoryOrderLogId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInventoryOrderLogId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_INVENTORY_ORDER_LOG WHERE INVENTORY_ORDER_LOG_ID = " + pInventoryOrderLogId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes InventoryOrderLogData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_INVENTORY_ORDER_LOG");
        String where = pCriteria.getSqlClause();
        if (where != null && !where.equals("")) {
            sqlBuf.append(" WHERE ");
            sqlBuf.append(where);
        }

        String sql = sqlBuf.toString();
        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
    }

    /**
     * Inserts a InventoryOrderLogData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InventoryOrderLogData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, InventoryOrderLogData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_INVENTORY_ORDER_LOG (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "INVENTORY_ORDER_LOG_ID,SITE_ID,ORDER_CUTOFF_DATE,ORDER_ID,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_DELIVERY_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getInventoryOrderLogId());
        pstmt.setInt(2+4,pData.getSiteId());
        pstmt.setDate(3+4,DBAccess.toSQLDate(pData.getOrderCutoffDate()));
        pstmt.setInt(4+4,pData.getOrderId());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());
        pstmt.setDate(9+4,DBAccess.toSQLDate(pData.getOrderDeliveryDate()));


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a InventoryOrderLogData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InventoryOrderLogData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new InventoryOrderLogData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InventoryOrderLogData insert(Connection pCon, InventoryOrderLogData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a InventoryOrderLogData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InventoryOrderLogData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InventoryOrderLogData pData, boolean pLogFl)
        throws SQLException {
        InventoryOrderLogData oldData = null;
        if(pLogFl) {
          int id = pData.getInventoryOrderLogId();
          try {
          oldData = InventoryOrderLogDataAccess.select(pCon,id);
          } catch(DataNotFoundException exc) {}
        }
        int n = update(pCon,pData);
        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, oldData, millis, "U", "O");
          insertLog(pCon, pData, millis, "U", "N");
        }
        return n;
    }

    /**
     * Deletes a InventoryOrderLogData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInventoryOrderLogId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInventoryOrderLogId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_INVENTORY_ORDER_LOG SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INVENTORY_ORDER_LOG d WHERE INVENTORY_ORDER_LOG_ID = " + pInventoryOrderLogId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pInventoryOrderLogId);
        return n;
     }

    /**
     * Deletes InventoryOrderLogData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria, boolean pLogFl)
        throws SQLException {
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          StringBuffer sqlBuf =
             new StringBuffer("INSERT INTO LCLW_INVENTORY_ORDER_LOG SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INVENTORY_ORDER_LOG d ");
          String where = pCriteria.getSqlClause();
          sqlBuf.append(" WHERE ");
          sqlBuf.append(where);

          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlBuf.toString());
          stmt.close();
        }
        int n = remove(pCon,pCriteria);
        return n;
    }
///////////////////////////////////////////////
}

