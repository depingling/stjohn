
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        WorkflowLogDataAccess
 * Description:  This class is used to build access methods to the CLW_WORKFLOW_LOG table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.WorkflowLogData;
import com.cleanwise.service.api.value.WorkflowLogDataVector;
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
 * <code>WorkflowLogDataAccess</code>
 */
public class WorkflowLogDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(WorkflowLogDataAccess.class.getName());

    /** <code>CLW_WORKFLOW_LOG</code> table name */
	/* Primary key: WORKFLOW_LOG_ID */
	
    public static final String CLW_WORKFLOW_LOG = "CLW_WORKFLOW_LOG";
    
    /** <code>WORKFLOW_LOG_ID</code> WORKFLOW_LOG_ID column of table CLW_WORKFLOW_LOG */
    public static final String WORKFLOW_LOG_ID = "WORKFLOW_LOG_ID";
    /** <code>WORKFLOW_ID</code> WORKFLOW_ID column of table CLW_WORKFLOW_LOG */
    public static final String WORKFLOW_ID = "WORKFLOW_ID";
    /** <code>START_DATE</code> START_DATE column of table CLW_WORKFLOW_LOG */
    public static final String START_DATE = "START_DATE";
    /** <code>START_TIME</code> START_TIME column of table CLW_WORKFLOW_LOG */
    public static final String START_TIME = "START_TIME";
    /** <code>WORKFLOW_TYPE_CD</code> WORKFLOW_TYPE_CD column of table CLW_WORKFLOW_LOG */
    public static final String WORKFLOW_TYPE_CD = "WORKFLOW_TYPE_CD";
    /** <code>WORKFLOW_STATUS_CD</code> WORKFLOW_STATUS_CD column of table CLW_WORKFLOW_LOG */
    public static final String WORKFLOW_STATUS_CD = "WORKFLOW_STATUS_CD";
    /** <code>END_DATE</code> END_DATE column of table CLW_WORKFLOW_LOG */
    public static final String END_DATE = "END_DATE";
    /** <code>END_TIME</code> END_TIME column of table CLW_WORKFLOW_LOG */
    public static final String END_TIME = "END_TIME";

    /**
     * Constructor.
     */
    public WorkflowLogDataAccess()
    {
    }

    /**
     * Gets a WorkflowLogData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pWorkflowLogId The key requested.
     * @return new WorkflowLogData()
     * @throws            SQLException
     */
    public static WorkflowLogData select(Connection pCon, int pWorkflowLogId)
        throws SQLException, DataNotFoundException {
        WorkflowLogData x=null;
        String sql="SELECT WORKFLOW_LOG_ID,WORKFLOW_ID,START_DATE,START_TIME,WORKFLOW_TYPE_CD,WORKFLOW_STATUS_CD,END_DATE,END_TIME FROM CLW_WORKFLOW_LOG WHERE WORKFLOW_LOG_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pWorkflowLogId=" + pWorkflowLogId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pWorkflowLogId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=WorkflowLogData.createValue();
            
            x.setWorkflowLogId(rs.getInt(1));
            x.setWorkflowId(rs.getInt(2));
            x.setStartDate(rs.getDate(3));
            x.setStartTime(rs.getTimestamp(4));
            x.setWorkflowTypeCd(rs.getString(5));
            x.setWorkflowStatusCd(rs.getString(6));
            x.setEndDate(rs.getDate(7));
            x.setEndTime(rs.getTimestamp(8));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("WORKFLOW_LOG_ID :" + pWorkflowLogId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a WorkflowLogDataVector object that consists
     * of WorkflowLogData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new WorkflowLogDataVector()
     * @throws            SQLException
     */
    public static WorkflowLogDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a WorkflowLogData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_WORKFLOW_LOG.WORKFLOW_LOG_ID,CLW_WORKFLOW_LOG.WORKFLOW_ID,CLW_WORKFLOW_LOG.START_DATE,CLW_WORKFLOW_LOG.START_TIME,CLW_WORKFLOW_LOG.WORKFLOW_TYPE_CD,CLW_WORKFLOW_LOG.WORKFLOW_STATUS_CD,CLW_WORKFLOW_LOG.END_DATE,CLW_WORKFLOW_LOG.END_TIME";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated WorkflowLogData Object.
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
    *@returns a populated WorkflowLogData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         WorkflowLogData x = WorkflowLogData.createValue();
         
         x.setWorkflowLogId(rs.getInt(1+offset));
         x.setWorkflowId(rs.getInt(2+offset));
         x.setStartDate(rs.getDate(3+offset));
         x.setStartTime(rs.getTimestamp(4+offset));
         x.setWorkflowTypeCd(rs.getString(5+offset));
         x.setWorkflowStatusCd(rs.getString(6+offset));
         x.setEndDate(rs.getDate(7+offset));
         x.setEndTime(rs.getTimestamp(8+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the WorkflowLogData Object represents.
    */
    public int getColumnCount(){
        return 8;
    }

    /**
     * Gets a WorkflowLogDataVector object that consists
     * of WorkflowLogData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new WorkflowLogDataVector()
     * @throws            SQLException
     */
    public static WorkflowLogDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT WORKFLOW_LOG_ID,WORKFLOW_ID,START_DATE,START_TIME,WORKFLOW_TYPE_CD,WORKFLOW_STATUS_CD,END_DATE,END_TIME FROM CLW_WORKFLOW_LOG");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_WORKFLOW_LOG.WORKFLOW_LOG_ID,CLW_WORKFLOW_LOG.WORKFLOW_ID,CLW_WORKFLOW_LOG.START_DATE,CLW_WORKFLOW_LOG.START_TIME,CLW_WORKFLOW_LOG.WORKFLOW_TYPE_CD,CLW_WORKFLOW_LOG.WORKFLOW_STATUS_CD,CLW_WORKFLOW_LOG.END_DATE,CLW_WORKFLOW_LOG.END_TIME FROM CLW_WORKFLOW_LOG");
                where = pCriteria.getSqlClause("CLW_WORKFLOW_LOG");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_WORKFLOW_LOG.equals(otherTable)){
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
        WorkflowLogDataVector v = new WorkflowLogDataVector();
        while (rs.next()) {
            WorkflowLogData x = WorkflowLogData.createValue();
            
            x.setWorkflowLogId(rs.getInt(1));
            x.setWorkflowId(rs.getInt(2));
            x.setStartDate(rs.getDate(3));
            x.setStartTime(rs.getTimestamp(4));
            x.setWorkflowTypeCd(rs.getString(5));
            x.setWorkflowStatusCd(rs.getString(6));
            x.setEndDate(rs.getDate(7));
            x.setEndTime(rs.getTimestamp(8));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a WorkflowLogDataVector object that consists
     * of WorkflowLogData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for WorkflowLogData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new WorkflowLogDataVector()
     * @throws            SQLException
     */
    public static WorkflowLogDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        WorkflowLogDataVector v = new WorkflowLogDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT WORKFLOW_LOG_ID,WORKFLOW_ID,START_DATE,START_TIME,WORKFLOW_TYPE_CD,WORKFLOW_STATUS_CD,END_DATE,END_TIME FROM CLW_WORKFLOW_LOG WHERE WORKFLOW_LOG_ID IN (");

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
            WorkflowLogData x=null;
            while (rs.next()) {
                // build the object
                x=WorkflowLogData.createValue();
                
                x.setWorkflowLogId(rs.getInt(1));
                x.setWorkflowId(rs.getInt(2));
                x.setStartDate(rs.getDate(3));
                x.setStartTime(rs.getTimestamp(4));
                x.setWorkflowTypeCd(rs.getString(5));
                x.setWorkflowStatusCd(rs.getString(6));
                x.setEndDate(rs.getDate(7));
                x.setEndTime(rs.getTimestamp(8));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a WorkflowLogDataVector object of all
     * WorkflowLogData objects in the database.
     * @param pCon An open database connection.
     * @return new WorkflowLogDataVector()
     * @throws            SQLException
     */
    public static WorkflowLogDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT WORKFLOW_LOG_ID,WORKFLOW_ID,START_DATE,START_TIME,WORKFLOW_TYPE_CD,WORKFLOW_STATUS_CD,END_DATE,END_TIME FROM CLW_WORKFLOW_LOG";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        WorkflowLogDataVector v = new WorkflowLogDataVector();
        WorkflowLogData x = null;
        while (rs.next()) {
            // build the object
            x = WorkflowLogData.createValue();
            
            x.setWorkflowLogId(rs.getInt(1));
            x.setWorkflowId(rs.getInt(2));
            x.setStartDate(rs.getDate(3));
            x.setStartTime(rs.getTimestamp(4));
            x.setWorkflowTypeCd(rs.getString(5));
            x.setWorkflowStatusCd(rs.getString(6));
            x.setEndDate(rs.getDate(7));
            x.setEndTime(rs.getTimestamp(8));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * WorkflowLogData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT WORKFLOW_LOG_ID FROM CLW_WORKFLOW_LOG");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORKFLOW_LOG");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_WORKFLOW_LOG");
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
     * Inserts a WorkflowLogData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowLogData object to insert.
     * @return new WorkflowLogData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkflowLogData insert(Connection pCon, WorkflowLogData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_WORKFLOW_LOG_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_WORKFLOW_LOG_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setWorkflowLogId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_WORKFLOW_LOG (WORKFLOW_LOG_ID,WORKFLOW_ID,START_DATE,START_TIME,WORKFLOW_TYPE_CD,WORKFLOW_STATUS_CD,END_DATE,END_TIME) VALUES(?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pstmt.setInt(1,pData.getWorkflowLogId());
        pstmt.setInt(2,pData.getWorkflowId());
        pstmt.setDate(3,DBAccess.toSQLDate(pData.getStartDate()));
        pstmt.setTimestamp(4,DBAccess.toSQLTimestamp(pData.getStartTime()));
        pstmt.setString(5,pData.getWorkflowTypeCd());
        pstmt.setString(6,pData.getWorkflowStatusCd());
        pstmt.setDate(7,DBAccess.toSQLDate(pData.getEndDate()));
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getEndTime()));

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WORKFLOW_LOG_ID="+pData.getWorkflowLogId());
            log.debug("SQL:   WORKFLOW_ID="+pData.getWorkflowId());
            log.debug("SQL:   START_DATE="+pData.getStartDate());
            log.debug("SQL:   START_TIME="+pData.getStartTime());
            log.debug("SQL:   WORKFLOW_TYPE_CD="+pData.getWorkflowTypeCd());
            log.debug("SQL:   WORKFLOW_STATUS_CD="+pData.getWorkflowStatusCd());
            log.debug("SQL:   END_DATE="+pData.getEndDate());
            log.debug("SQL:   END_TIME="+pData.getEndTime());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setWorkflowLogId(0);
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
     * Updates a WorkflowLogData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowLogData object to update. 
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkflowLogData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_WORKFLOW_LOG SET WORKFLOW_ID = ?,START_DATE = ?,START_TIME = ?,WORKFLOW_TYPE_CD = ?,WORKFLOW_STATUS_CD = ?,END_DATE = ?,END_TIME = ? WHERE WORKFLOW_LOG_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        int i = 1;
        
        pstmt.setInt(i++,pData.getWorkflowId());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getStartDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getStartTime()));
        pstmt.setString(i++,pData.getWorkflowTypeCd());
        pstmt.setString(i++,pData.getWorkflowStatusCd());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEndDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getEndTime()));
        pstmt.setInt(i++,pData.getWorkflowLogId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   WORKFLOW_ID="+pData.getWorkflowId());
            log.debug("SQL:   START_DATE="+pData.getStartDate());
            log.debug("SQL:   START_TIME="+pData.getStartTime());
            log.debug("SQL:   WORKFLOW_TYPE_CD="+pData.getWorkflowTypeCd());
            log.debug("SQL:   WORKFLOW_STATUS_CD="+pData.getWorkflowStatusCd());
            log.debug("SQL:   END_DATE="+pData.getEndDate());
            log.debug("SQL:   END_TIME="+pData.getEndTime());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a WorkflowLogData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkflowLogId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkflowLogId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_WORKFLOW_LOG WHERE WORKFLOW_LOG_ID = " + pWorkflowLogId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes WorkflowLogData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_WORKFLOW_LOG");
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
     * Inserts a WorkflowLogData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowLogData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, WorkflowLogData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_WORKFLOW_LOG (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "WORKFLOW_LOG_ID,WORKFLOW_ID,START_DATE,START_TIME,WORKFLOW_TYPE_CD,WORKFLOW_STATUS_CD,END_DATE,END_TIME) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getWorkflowLogId());
        pstmt.setInt(2+4,pData.getWorkflowId());
        pstmt.setDate(3+4,DBAccess.toSQLDate(pData.getStartDate()));
        pstmt.setTimestamp(4+4,DBAccess.toSQLTimestamp(pData.getStartTime()));
        pstmt.setString(5+4,pData.getWorkflowTypeCd());
        pstmt.setString(6+4,pData.getWorkflowStatusCd());
        pstmt.setDate(7+4,DBAccess.toSQLDate(pData.getEndDate()));
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getEndTime()));


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a WorkflowLogData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowLogData object to insert.
     * @param pLogFl  Creates record in log table if true
     * @return new WorkflowLogData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static WorkflowLogData insert(Connection pCon, WorkflowLogData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a WorkflowLogData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A WorkflowLogData object to update. 
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, WorkflowLogData pData, boolean pLogFl)
        throws SQLException {
        WorkflowLogData oldData = null;
        if(pLogFl) {
          int id = pData.getWorkflowLogId();
          try {
          oldData = WorkflowLogDataAccess.select(pCon,id);
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
     * Deletes a WorkflowLogData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pWorkflowLogId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pWorkflowLogId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_WORKFLOW_LOG SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORKFLOW_LOG d WHERE WORKFLOW_LOG_ID = " + pWorkflowLogId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pWorkflowLogId);
        return n;
     }

    /**
     * Deletes WorkflowLogData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_WORKFLOW_LOG SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_WORKFLOW_LOG d ");
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

