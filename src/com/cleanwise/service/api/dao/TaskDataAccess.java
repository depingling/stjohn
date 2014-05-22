
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        TaskDataAccess
 * Description:  This class is used to build access methods to the CLW_TASK table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.TaskData;
import com.cleanwise.service.api.value.TaskDataVector;
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
 * <code>TaskDataAccess</code>
 */
public class TaskDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(TaskDataAccess.class.getName());

    /** <code>CLW_TASK</code> table name */
	/* Primary key: TASK_ID */
	
    public static final String CLW_TASK = "CLW_TASK";
    
    /** <code>TASK_ID</code> TASK_ID column of table CLW_TASK */
    public static final String TASK_ID = "TASK_ID";
    /** <code>TASK_TEMPLATE_ID</code> TASK_TEMPLATE_ID column of table CLW_TASK */
    public static final String TASK_TEMPLATE_ID = "TASK_TEMPLATE_ID";
    /** <code>PROCESS_ID</code> PROCESS_ID column of table CLW_TASK */
    public static final String PROCESS_ID = "PROCESS_ID";
    /** <code>TASK_NAME</code> TASK_NAME column of table CLW_TASK */
    public static final String TASK_NAME = "TASK_NAME";
    /** <code>VAR_CLASS</code> VAR_CLASS column of table CLW_TASK */
    public static final String VAR_CLASS = "VAR_CLASS";
    /** <code>METHOD</code> METHOD column of table CLW_TASK */
    public static final String METHOD = "METHOD";
    /** <code>TASK_TYPE_CD</code> TASK_TYPE_CD column of table CLW_TASK */
    public static final String TASK_TYPE_CD = "TASK_TYPE_CD";
    /** <code>TASK_STATUS_CD</code> TASK_STATUS_CD column of table CLW_TASK */
    public static final String TASK_STATUS_CD = "TASK_STATUS_CD";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_TASK */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_TASK */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_TASK */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_TASK */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>OPERATION_TYPE_CD</code> OPERATION_TYPE_CD column of table CLW_TASK */
    public static final String OPERATION_TYPE_CD = "OPERATION_TYPE_CD";

    /**
     * Constructor.
     */
    public TaskDataAccess()
    {
    }

    /**
     * Gets a TaskData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pTaskId The key requested.
     * @return new TaskData()
     * @throws            SQLException
     */
    public static TaskData select(Connection pCon, int pTaskId)
        throws SQLException, DataNotFoundException {
        TaskData x=null;
        String sql="SELECT TASK_ID,TASK_TEMPLATE_ID,PROCESS_ID,TASK_NAME,VAR_CLASS,METHOD,TASK_TYPE_CD,TASK_STATUS_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,OPERATION_TYPE_CD FROM CLW_TASK WHERE TASK_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pTaskId=" + pTaskId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pTaskId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=TaskData.createValue();
            
            x.setTaskId(rs.getInt(1));
            x.setTaskTemplateId(rs.getInt(2));
            x.setProcessId(rs.getInt(3));
            x.setTaskName(rs.getString(4));
            x.setVarClass(rs.getString(5));
            x.setMethod(rs.getString(6));
            x.setTaskTypeCd(rs.getString(7));
            x.setTaskStatusCd(rs.getString(8));
            x.setAddBy(rs.getString(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setOperationTypeCd(rs.getString(13));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("TASK_ID :" + pTaskId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a TaskDataVector object that consists
     * of TaskData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new TaskDataVector()
     * @throws            SQLException
     */
    public static TaskDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a TaskData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_TASK.TASK_ID,CLW_TASK.TASK_TEMPLATE_ID,CLW_TASK.PROCESS_ID,CLW_TASK.TASK_NAME,CLW_TASK.VAR_CLASS,CLW_TASK.METHOD,CLW_TASK.TASK_TYPE_CD,CLW_TASK.TASK_STATUS_CD,CLW_TASK.ADD_BY,CLW_TASK.ADD_DATE,CLW_TASK.MOD_BY,CLW_TASK.MOD_DATE,CLW_TASK.OPERATION_TYPE_CD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated TaskData Object.
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
    *@returns a populated TaskData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         TaskData x = TaskData.createValue();
         
         x.setTaskId(rs.getInt(1+offset));
         x.setTaskTemplateId(rs.getInt(2+offset));
         x.setProcessId(rs.getInt(3+offset));
         x.setTaskName(rs.getString(4+offset));
         x.setVarClass(rs.getString(5+offset));
         x.setMethod(rs.getString(6+offset));
         x.setTaskTypeCd(rs.getString(7+offset));
         x.setTaskStatusCd(rs.getString(8+offset));
         x.setAddBy(rs.getString(9+offset));
         x.setAddDate(rs.getTimestamp(10+offset));
         x.setModBy(rs.getString(11+offset));
         x.setModDate(rs.getTimestamp(12+offset));
         x.setOperationTypeCd(rs.getString(13+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the TaskData Object represents.
    */
    public int getColumnCount(){
        return 13;
    }

    /**
     * Gets a TaskDataVector object that consists
     * of TaskData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new TaskDataVector()
     * @throws            SQLException
     */
    public static TaskDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT TASK_ID,TASK_TEMPLATE_ID,PROCESS_ID,TASK_NAME,VAR_CLASS,METHOD,TASK_TYPE_CD,TASK_STATUS_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,OPERATION_TYPE_CD FROM CLW_TASK");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_TASK.TASK_ID,CLW_TASK.TASK_TEMPLATE_ID,CLW_TASK.PROCESS_ID,CLW_TASK.TASK_NAME,CLW_TASK.VAR_CLASS,CLW_TASK.METHOD,CLW_TASK.TASK_TYPE_CD,CLW_TASK.TASK_STATUS_CD,CLW_TASK.ADD_BY,CLW_TASK.ADD_DATE,CLW_TASK.MOD_BY,CLW_TASK.MOD_DATE,CLW_TASK.OPERATION_TYPE_CD FROM CLW_TASK");
                where = pCriteria.getSqlClause("CLW_TASK");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_TASK.equals(otherTable)){
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
        TaskDataVector v = new TaskDataVector();
        while (rs.next()) {
            TaskData x = TaskData.createValue();
            
            x.setTaskId(rs.getInt(1));
            x.setTaskTemplateId(rs.getInt(2));
            x.setProcessId(rs.getInt(3));
            x.setTaskName(rs.getString(4));
            x.setVarClass(rs.getString(5));
            x.setMethod(rs.getString(6));
            x.setTaskTypeCd(rs.getString(7));
            x.setTaskStatusCd(rs.getString(8));
            x.setAddBy(rs.getString(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setOperationTypeCd(rs.getString(13));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a TaskDataVector object that consists
     * of TaskData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for TaskData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new TaskDataVector()
     * @throws            SQLException
     */
    public static TaskDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        TaskDataVector v = new TaskDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT TASK_ID,TASK_TEMPLATE_ID,PROCESS_ID,TASK_NAME,VAR_CLASS,METHOD,TASK_TYPE_CD,TASK_STATUS_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,OPERATION_TYPE_CD FROM CLW_TASK WHERE TASK_ID IN (");

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
            TaskData x=null;
            while (rs.next()) {
                // build the object
                x=TaskData.createValue();
                
                x.setTaskId(rs.getInt(1));
                x.setTaskTemplateId(rs.getInt(2));
                x.setProcessId(rs.getInt(3));
                x.setTaskName(rs.getString(4));
                x.setVarClass(rs.getString(5));
                x.setMethod(rs.getString(6));
                x.setTaskTypeCd(rs.getString(7));
                x.setTaskStatusCd(rs.getString(8));
                x.setAddBy(rs.getString(9));
                x.setAddDate(rs.getTimestamp(10));
                x.setModBy(rs.getString(11));
                x.setModDate(rs.getTimestamp(12));
                x.setOperationTypeCd(rs.getString(13));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a TaskDataVector object of all
     * TaskData objects in the database.
     * @param pCon An open database connection.
     * @return new TaskDataVector()
     * @throws            SQLException
     */
    public static TaskDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT TASK_ID,TASK_TEMPLATE_ID,PROCESS_ID,TASK_NAME,VAR_CLASS,METHOD,TASK_TYPE_CD,TASK_STATUS_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,OPERATION_TYPE_CD FROM CLW_TASK";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        TaskDataVector v = new TaskDataVector();
        TaskData x = null;
        while (rs.next()) {
            // build the object
            x = TaskData.createValue();
            
            x.setTaskId(rs.getInt(1));
            x.setTaskTemplateId(rs.getInt(2));
            x.setProcessId(rs.getInt(3));
            x.setTaskName(rs.getString(4));
            x.setVarClass(rs.getString(5));
            x.setMethod(rs.getString(6));
            x.setTaskTypeCd(rs.getString(7));
            x.setTaskStatusCd(rs.getString(8));
            x.setAddBy(rs.getString(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setOperationTypeCd(rs.getString(13));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * TaskData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT TASK_ID FROM CLW_TASK");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_TASK");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_TASK");
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
     * Inserts a TaskData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A TaskData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new TaskData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static TaskData insert(Connection pCon, TaskData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_TASK_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_TASK_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setTaskId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_TASK (TASK_ID,TASK_TEMPLATE_ID,PROCESS_ID,TASK_NAME,VAR_CLASS,METHOD,TASK_TYPE_CD,TASK_STATUS_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,OPERATION_TYPE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getTaskId());
        pstmt.setInt(2,pData.getTaskTemplateId());
        if (pData.getProcessId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getProcessId());
        }

        pstmt.setString(4,pData.getTaskName());
        pstmt.setString(5,pData.getVarClass());
        pstmt.setString(6,pData.getMethod());
        pstmt.setString(7,pData.getTaskTypeCd());
        pstmt.setString(8,pData.getTaskStatusCd());
        pstmt.setString(9,pData.getAddBy());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(11,pData.getModBy());
        pstmt.setTimestamp(12,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(13,pData.getOperationTypeCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   TASK_ID="+pData.getTaskId());
            log.debug("SQL:   TASK_TEMPLATE_ID="+pData.getTaskTemplateId());
            log.debug("SQL:   PROCESS_ID="+pData.getProcessId());
            log.debug("SQL:   TASK_NAME="+pData.getTaskName());
            log.debug("SQL:   VAR_CLASS="+pData.getVarClass());
            log.debug("SQL:   METHOD="+pData.getMethod());
            log.debug("SQL:   TASK_TYPE_CD="+pData.getTaskTypeCd());
            log.debug("SQL:   TASK_STATUS_CD="+pData.getTaskStatusCd());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   OPERATION_TYPE_CD="+pData.getOperationTypeCd());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setTaskId(0);
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
     * Updates a TaskData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A TaskData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, TaskData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_TASK SET TASK_TEMPLATE_ID = ?,PROCESS_ID = ?,TASK_NAME = ?,VAR_CLASS = ?,METHOD = ?,TASK_TYPE_CD = ?,TASK_STATUS_CD = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ?,OPERATION_TYPE_CD = ? WHERE TASK_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getTaskTemplateId());
        if (pData.getProcessId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getProcessId());
        }

        pstmt.setString(i++,pData.getTaskName());
        pstmt.setString(i++,pData.getVarClass());
        pstmt.setString(i++,pData.getMethod());
        pstmt.setString(i++,pData.getTaskTypeCd());
        pstmt.setString(i++,pData.getTaskStatusCd());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getOperationTypeCd());
        pstmt.setInt(i++,pData.getTaskId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   TASK_TEMPLATE_ID="+pData.getTaskTemplateId());
            log.debug("SQL:   PROCESS_ID="+pData.getProcessId());
            log.debug("SQL:   TASK_NAME="+pData.getTaskName());
            log.debug("SQL:   VAR_CLASS="+pData.getVarClass());
            log.debug("SQL:   METHOD="+pData.getMethod());
            log.debug("SQL:   TASK_TYPE_CD="+pData.getTaskTypeCd());
            log.debug("SQL:   TASK_STATUS_CD="+pData.getTaskStatusCd());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   OPERATION_TYPE_CD="+pData.getOperationTypeCd());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a TaskData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pTaskId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pTaskId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_TASK WHERE TASK_ID = " + pTaskId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes TaskData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_TASK");
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
     * Inserts a TaskData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A TaskData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, TaskData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_TASK (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "TASK_ID,TASK_TEMPLATE_ID,PROCESS_ID,TASK_NAME,VAR_CLASS,METHOD,TASK_TYPE_CD,TASK_STATUS_CD,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,OPERATION_TYPE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getTaskId());
        pstmt.setInt(2+4,pData.getTaskTemplateId());
        if (pData.getProcessId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getProcessId());
        }

        pstmt.setString(4+4,pData.getTaskName());
        pstmt.setString(5+4,pData.getVarClass());
        pstmt.setString(6+4,pData.getMethod());
        pstmt.setString(7+4,pData.getTaskTypeCd());
        pstmt.setString(8+4,pData.getTaskStatusCd());
        pstmt.setString(9+4,pData.getAddBy());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(11+4,pData.getModBy());
        pstmt.setTimestamp(12+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(13+4,pData.getOperationTypeCd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a TaskData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A TaskData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new TaskData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static TaskData insert(Connection pCon, TaskData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a TaskData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A TaskData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, TaskData pData, boolean pLogFl)
        throws SQLException {
        TaskData oldData = null;
        if(pLogFl) {
          int id = pData.getTaskId();
          try {
          oldData = TaskDataAccess.select(pCon,id);
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
     * Deletes a TaskData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pTaskId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pTaskId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_TASK SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_TASK d WHERE TASK_ID = " + pTaskId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pTaskId);
        return n;
     }

    /**
     * Deletes TaskData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_TASK SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_TASK d ");
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

