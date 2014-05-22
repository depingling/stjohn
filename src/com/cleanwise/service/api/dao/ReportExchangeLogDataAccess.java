
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ReportExchangeLogDataAccess
 * Description:  This class is used to build access methods to the CLW_REPORT_EXCHANGE_LOG table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ReportExchangeLogData;
import com.cleanwise.service.api.value.ReportExchangeLogDataVector;
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
 * <code>ReportExchangeLogDataAccess</code>
 */
public class ReportExchangeLogDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ReportExchangeLogDataAccess.class.getName());

    /** <code>CLW_REPORT_EXCHANGE_LOG</code> table name */
	/* Primary key: REPORT_EXCHANGE_LOG_ID */
	
    public static final String CLW_REPORT_EXCHANGE_LOG = "CLW_REPORT_EXCHANGE_LOG";
    
    /** <code>REPORT_EXCHANGE_LOG_ID</code> REPORT_EXCHANGE_LOG_ID column of table CLW_REPORT_EXCHANGE_LOG */
    public static final String REPORT_EXCHANGE_LOG_ID = "REPORT_EXCHANGE_LOG_ID";
    /** <code>GENERIC_REPORT_ID</code> GENERIC_REPORT_ID column of table CLW_REPORT_EXCHANGE_LOG */
    public static final String GENERIC_REPORT_ID = "GENERIC_REPORT_ID";
    /** <code>RECORD_KEY</code> RECORD_KEY column of table CLW_REPORT_EXCHANGE_LOG */
    public static final String RECORD_KEY = "RECORD_KEY";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_REPORT_EXCHANGE_LOG */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>SENT_DATE</code> SENT_DATE column of table CLW_REPORT_EXCHANGE_LOG */
    public static final String SENT_DATE = "SENT_DATE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_REPORT_EXCHANGE_LOG */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_REPORT_EXCHANGE_LOG */
    public static final String MOD_DATE = "MOD_DATE";

    /**
     * Constructor.
     */
    public ReportExchangeLogDataAccess()
    {
    }

    /**
     * Gets a ReportExchangeLogData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pReportExchangeLogId The key requested.
     * @return new ReportExchangeLogData()
     * @throws            SQLException
     */
    public static ReportExchangeLogData select(Connection pCon, int pReportExchangeLogId)
        throws SQLException, DataNotFoundException {
        ReportExchangeLogData x=null;
        String sql="SELECT REPORT_EXCHANGE_LOG_ID,GENERIC_REPORT_ID,RECORD_KEY,CLW_VALUE,SENT_DATE,ADD_DATE,MOD_DATE FROM CLW_REPORT_EXCHANGE_LOG WHERE REPORT_EXCHANGE_LOG_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pReportExchangeLogId=" + pReportExchangeLogId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pReportExchangeLogId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ReportExchangeLogData.createValue();
            
            x.setReportExchangeLogId(rs.getInt(1));
            x.setGenericReportId(rs.getInt(2));
            x.setRecordKey(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setSentDate(rs.getDate(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setModDate(rs.getTimestamp(7));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("REPORT_EXCHANGE_LOG_ID :" + pReportExchangeLogId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ReportExchangeLogDataVector object that consists
     * of ReportExchangeLogData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ReportExchangeLogDataVector()
     * @throws            SQLException
     */
    public static ReportExchangeLogDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ReportExchangeLogData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_REPORT_EXCHANGE_LOG.REPORT_EXCHANGE_LOG_ID,CLW_REPORT_EXCHANGE_LOG.GENERIC_REPORT_ID,CLW_REPORT_EXCHANGE_LOG.RECORD_KEY,CLW_REPORT_EXCHANGE_LOG.CLW_VALUE,CLW_REPORT_EXCHANGE_LOG.SENT_DATE,CLW_REPORT_EXCHANGE_LOG.ADD_DATE,CLW_REPORT_EXCHANGE_LOG.MOD_DATE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ReportExchangeLogData Object.
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
    *@returns a populated ReportExchangeLogData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ReportExchangeLogData x = ReportExchangeLogData.createValue();
         
         x.setReportExchangeLogId(rs.getInt(1+offset));
         x.setGenericReportId(rs.getInt(2+offset));
         x.setRecordKey(rs.getString(3+offset));
         x.setValue(rs.getString(4+offset));
         x.setSentDate(rs.getDate(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ReportExchangeLogData Object represents.
    */
    public int getColumnCount(){
        return 7;
    }

    /**
     * Gets a ReportExchangeLogDataVector object that consists
     * of ReportExchangeLogData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ReportExchangeLogDataVector()
     * @throws            SQLException
     */
    public static ReportExchangeLogDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT REPORT_EXCHANGE_LOG_ID,GENERIC_REPORT_ID,RECORD_KEY,CLW_VALUE,SENT_DATE,ADD_DATE,MOD_DATE FROM CLW_REPORT_EXCHANGE_LOG");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_REPORT_EXCHANGE_LOG.REPORT_EXCHANGE_LOG_ID,CLW_REPORT_EXCHANGE_LOG.GENERIC_REPORT_ID,CLW_REPORT_EXCHANGE_LOG.RECORD_KEY,CLW_REPORT_EXCHANGE_LOG.CLW_VALUE,CLW_REPORT_EXCHANGE_LOG.SENT_DATE,CLW_REPORT_EXCHANGE_LOG.ADD_DATE,CLW_REPORT_EXCHANGE_LOG.MOD_DATE FROM CLW_REPORT_EXCHANGE_LOG");
                where = pCriteria.getSqlClause("CLW_REPORT_EXCHANGE_LOG");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_REPORT_EXCHANGE_LOG.equals(otherTable)){
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
        ReportExchangeLogDataVector v = new ReportExchangeLogDataVector();
        while (rs.next()) {
            ReportExchangeLogData x = ReportExchangeLogData.createValue();
            
            x.setReportExchangeLogId(rs.getInt(1));
            x.setGenericReportId(rs.getInt(2));
            x.setRecordKey(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setSentDate(rs.getDate(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setModDate(rs.getTimestamp(7));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ReportExchangeLogDataVector object that consists
     * of ReportExchangeLogData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ReportExchangeLogData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ReportExchangeLogDataVector()
     * @throws            SQLException
     */
    public static ReportExchangeLogDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ReportExchangeLogDataVector v = new ReportExchangeLogDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT REPORT_EXCHANGE_LOG_ID,GENERIC_REPORT_ID,RECORD_KEY,CLW_VALUE,SENT_DATE,ADD_DATE,MOD_DATE FROM CLW_REPORT_EXCHANGE_LOG WHERE REPORT_EXCHANGE_LOG_ID IN (");

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
            ReportExchangeLogData x=null;
            while (rs.next()) {
                // build the object
                x=ReportExchangeLogData.createValue();
                
                x.setReportExchangeLogId(rs.getInt(1));
                x.setGenericReportId(rs.getInt(2));
                x.setRecordKey(rs.getString(3));
                x.setValue(rs.getString(4));
                x.setSentDate(rs.getDate(5));
                x.setAddDate(rs.getTimestamp(6));
                x.setModDate(rs.getTimestamp(7));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ReportExchangeLogDataVector object of all
     * ReportExchangeLogData objects in the database.
     * @param pCon An open database connection.
     * @return new ReportExchangeLogDataVector()
     * @throws            SQLException
     */
    public static ReportExchangeLogDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT REPORT_EXCHANGE_LOG_ID,GENERIC_REPORT_ID,RECORD_KEY,CLW_VALUE,SENT_DATE,ADD_DATE,MOD_DATE FROM CLW_REPORT_EXCHANGE_LOG";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ReportExchangeLogDataVector v = new ReportExchangeLogDataVector();
        ReportExchangeLogData x = null;
        while (rs.next()) {
            // build the object
            x = ReportExchangeLogData.createValue();
            
            x.setReportExchangeLogId(rs.getInt(1));
            x.setGenericReportId(rs.getInt(2));
            x.setRecordKey(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setSentDate(rs.getDate(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setModDate(rs.getTimestamp(7));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ReportExchangeLogData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT REPORT_EXCHANGE_LOG_ID FROM CLW_REPORT_EXCHANGE_LOG");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_REPORT_EXCHANGE_LOG");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_REPORT_EXCHANGE_LOG");
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
     * Inserts a ReportExchangeLogData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportExchangeLogData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ReportExchangeLogData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ReportExchangeLogData insert(Connection pCon, ReportExchangeLogData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_REPORT_EXCHANGE_LOG_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_REPORT_EXCHANGE_LOG_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setReportExchangeLogId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_REPORT_EXCHANGE_LOG (REPORT_EXCHANGE_LOG_ID,GENERIC_REPORT_ID,RECORD_KEY,CLW_VALUE,SENT_DATE,ADD_DATE,MOD_DATE) VALUES(?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getReportExchangeLogId());
        pstmt.setInt(2,pData.getGenericReportId());
        pstmt.setString(3,pData.getRecordKey());
        pstmt.setString(4,pData.getValue());
        pstmt.setDate(5,DBAccess.toSQLDate(pData.getSentDate()));
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));

        if (log.isDebugEnabled()) {
            log.debug("SQL:   REPORT_EXCHANGE_LOG_ID="+pData.getReportExchangeLogId());
            log.debug("SQL:   GENERIC_REPORT_ID="+pData.getGenericReportId());
            log.debug("SQL:   RECORD_KEY="+pData.getRecordKey());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   SENT_DATE="+pData.getSentDate());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setReportExchangeLogId(0);
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
     * Updates a ReportExchangeLogData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportExchangeLogData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ReportExchangeLogData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_REPORT_EXCHANGE_LOG SET GENERIC_REPORT_ID = ?,RECORD_KEY = ?,CLW_VALUE = ?,SENT_DATE = ?,ADD_DATE = ?,MOD_DATE = ? WHERE REPORT_EXCHANGE_LOG_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getGenericReportId());
        pstmt.setString(i++,pData.getRecordKey());
        pstmt.setString(i++,pData.getValue());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getSentDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setInt(i++,pData.getReportExchangeLogId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   GENERIC_REPORT_ID="+pData.getGenericReportId());
            log.debug("SQL:   RECORD_KEY="+pData.getRecordKey());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   SENT_DATE="+pData.getSentDate());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ReportExchangeLogData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pReportExchangeLogId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pReportExchangeLogId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_REPORT_EXCHANGE_LOG WHERE REPORT_EXCHANGE_LOG_ID = " + pReportExchangeLogId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ReportExchangeLogData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_REPORT_EXCHANGE_LOG");
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
     * Inserts a ReportExchangeLogData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportExchangeLogData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ReportExchangeLogData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_REPORT_EXCHANGE_LOG (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "REPORT_EXCHANGE_LOG_ID,GENERIC_REPORT_ID,RECORD_KEY,CLW_VALUE,SENT_DATE,ADD_DATE,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getReportExchangeLogId());
        pstmt.setInt(2+4,pData.getGenericReportId());
        pstmt.setString(3+4,pData.getRecordKey());
        pstmt.setString(4+4,pData.getValue());
        pstmt.setDate(5+4,DBAccess.toSQLDate(pData.getSentDate()));
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ReportExchangeLogData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportExchangeLogData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ReportExchangeLogData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ReportExchangeLogData insert(Connection pCon, ReportExchangeLogData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ReportExchangeLogData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportExchangeLogData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ReportExchangeLogData pData, boolean pLogFl)
        throws SQLException {
        ReportExchangeLogData oldData = null;
        if(pLogFl) {
          int id = pData.getReportExchangeLogId();
          try {
          oldData = ReportExchangeLogDataAccess.select(pCon,id);
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
     * Deletes a ReportExchangeLogData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pReportExchangeLogId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pReportExchangeLogId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_REPORT_EXCHANGE_LOG SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_REPORT_EXCHANGE_LOG d WHERE REPORT_EXCHANGE_LOG_ID = " + pReportExchangeLogId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pReportExchangeLogId);
        return n;
     }

    /**
     * Deletes ReportExchangeLogData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_REPORT_EXCHANGE_LOG SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_REPORT_EXCHANGE_LOG d ");
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

