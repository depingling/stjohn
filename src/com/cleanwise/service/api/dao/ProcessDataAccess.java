
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ProcessDataAccess
 * Description:  This class is used to build access methods to the CLW_PROCESS table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ProcessData;
import com.cleanwise.service.api.value.ProcessDataVector;
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
 * <code>ProcessDataAccess</code>
 */
public class ProcessDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ProcessDataAccess.class.getName());

    /** <code>CLW_PROCESS</code> table name */
	/* Primary key: PROCESS_ID */
	
    public static final String CLW_PROCESS = "CLW_PROCESS";
    
    /** <code>PROCESS_ID</code> PROCESS_ID column of table CLW_PROCESS */
    public static final String PROCESS_ID = "PROCESS_ID";
    /** <code>PROCESS_TEMPLATE_ID</code> PROCESS_TEMPLATE_ID column of table CLW_PROCESS */
    public static final String PROCESS_TEMPLATE_ID = "PROCESS_TEMPLATE_ID";
    /** <code>PROCESS_NAME</code> PROCESS_NAME column of table CLW_PROCESS */
    public static final String PROCESS_NAME = "PROCESS_NAME";
    /** <code>PROCESS_TYPE_CD</code> PROCESS_TYPE_CD column of table CLW_PROCESS */
    public static final String PROCESS_TYPE_CD = "PROCESS_TYPE_CD";
    /** <code>PROCESS_STATUS_CD</code> PROCESS_STATUS_CD column of table CLW_PROCESS */
    public static final String PROCESS_STATUS_CD = "PROCESS_STATUS_CD";
    /** <code>PROCESS_HOSTNAME</code> PROCESS_HOSTNAME column of table CLW_PROCESS */
    public static final String PROCESS_HOSTNAME = "PROCESS_HOSTNAME";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_PROCESS */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_PROCESS */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_PROCESS */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_PROCESS */
    public static final String MOD_BY = "MOD_BY";
    /** <code>PROCESS_PRIORITY</code> PROCESS_PRIORITY column of table CLW_PROCESS */
    public static final String PROCESS_PRIORITY = "PROCESS_PRIORITY";

    /**
     * Constructor.
     */
    public ProcessDataAccess()
    {
    }

    /**
     * Gets a ProcessData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pProcessId The key requested.
     * @return new ProcessData()
     * @throws            SQLException
     */
    public static ProcessData select(Connection pCon, int pProcessId)
        throws SQLException, DataNotFoundException {
        ProcessData x=null;
        String sql="SELECT PROCESS_ID,PROCESS_TEMPLATE_ID,PROCESS_NAME,PROCESS_TYPE_CD,PROCESS_STATUS_CD,PROCESS_HOSTNAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PROCESS_PRIORITY FROM CLW_PROCESS WHERE PROCESS_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pProcessId=" + pProcessId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pProcessId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ProcessData.createValue();
            
            x.setProcessId(rs.getInt(1));
            x.setProcessTemplateId(rs.getInt(2));
            x.setProcessName(rs.getString(3));
            x.setProcessTypeCd(rs.getString(4));
            x.setProcessStatusCd(rs.getString(5));
            x.setProcessHostname(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setProcessPriority(rs.getInt(11));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("PROCESS_ID :" + pProcessId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ProcessDataVector object that consists
     * of ProcessData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ProcessDataVector()
     * @throws            SQLException
     */
    public static ProcessDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ProcessData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_PROCESS.PROCESS_ID,CLW_PROCESS.PROCESS_TEMPLATE_ID,CLW_PROCESS.PROCESS_NAME,CLW_PROCESS.PROCESS_TYPE_CD,CLW_PROCESS.PROCESS_STATUS_CD,CLW_PROCESS.PROCESS_HOSTNAME,CLW_PROCESS.ADD_DATE,CLW_PROCESS.ADD_BY,CLW_PROCESS.MOD_DATE,CLW_PROCESS.MOD_BY,CLW_PROCESS.PROCESS_PRIORITY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ProcessData Object.
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
    *@returns a populated ProcessData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ProcessData x = ProcessData.createValue();
         
         x.setProcessId(rs.getInt(1+offset));
         x.setProcessTemplateId(rs.getInt(2+offset));
         x.setProcessName(rs.getString(3+offset));
         x.setProcessTypeCd(rs.getString(4+offset));
         x.setProcessStatusCd(rs.getString(5+offset));
         x.setProcessHostname(rs.getString(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         x.setProcessPriority(rs.getInt(11+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ProcessData Object represents.
    */
    public int getColumnCount(){
        return 11;
    }

    /**
     * Gets a ProcessDataVector object that consists
     * of ProcessData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ProcessDataVector()
     * @throws            SQLException
     */
    public static ProcessDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT PROCESS_ID,PROCESS_TEMPLATE_ID,PROCESS_NAME,PROCESS_TYPE_CD,PROCESS_STATUS_CD,PROCESS_HOSTNAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PROCESS_PRIORITY FROM CLW_PROCESS");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_PROCESS.PROCESS_ID,CLW_PROCESS.PROCESS_TEMPLATE_ID,CLW_PROCESS.PROCESS_NAME,CLW_PROCESS.PROCESS_TYPE_CD,CLW_PROCESS.PROCESS_STATUS_CD,CLW_PROCESS.PROCESS_HOSTNAME,CLW_PROCESS.ADD_DATE,CLW_PROCESS.ADD_BY,CLW_PROCESS.MOD_DATE,CLW_PROCESS.MOD_BY,CLW_PROCESS.PROCESS_PRIORITY FROM CLW_PROCESS");
                where = pCriteria.getSqlClause("CLW_PROCESS");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PROCESS.equals(otherTable)){
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
        ProcessDataVector v = new ProcessDataVector();
        while (rs.next()) {
            ProcessData x = ProcessData.createValue();
            
            x.setProcessId(rs.getInt(1));
            x.setProcessTemplateId(rs.getInt(2));
            x.setProcessName(rs.getString(3));
            x.setProcessTypeCd(rs.getString(4));
            x.setProcessStatusCd(rs.getString(5));
            x.setProcessHostname(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setProcessPriority(rs.getInt(11));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ProcessDataVector object that consists
     * of ProcessData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ProcessData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ProcessDataVector()
     * @throws            SQLException
     */
    public static ProcessDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ProcessDataVector v = new ProcessDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT PROCESS_ID,PROCESS_TEMPLATE_ID,PROCESS_NAME,PROCESS_TYPE_CD,PROCESS_STATUS_CD,PROCESS_HOSTNAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PROCESS_PRIORITY FROM CLW_PROCESS WHERE PROCESS_ID IN (");

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
            ProcessData x=null;
            while (rs.next()) {
                // build the object
                x=ProcessData.createValue();
                
                x.setProcessId(rs.getInt(1));
                x.setProcessTemplateId(rs.getInt(2));
                x.setProcessName(rs.getString(3));
                x.setProcessTypeCd(rs.getString(4));
                x.setProcessStatusCd(rs.getString(5));
                x.setProcessHostname(rs.getString(6));
                x.setAddDate(rs.getTimestamp(7));
                x.setAddBy(rs.getString(8));
                x.setModDate(rs.getTimestamp(9));
                x.setModBy(rs.getString(10));
                x.setProcessPriority(rs.getInt(11));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ProcessDataVector object of all
     * ProcessData objects in the database.
     * @param pCon An open database connection.
     * @return new ProcessDataVector()
     * @throws            SQLException
     */
    public static ProcessDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT PROCESS_ID,PROCESS_TEMPLATE_ID,PROCESS_NAME,PROCESS_TYPE_CD,PROCESS_STATUS_CD,PROCESS_HOSTNAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PROCESS_PRIORITY FROM CLW_PROCESS";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ProcessDataVector v = new ProcessDataVector();
        ProcessData x = null;
        while (rs.next()) {
            // build the object
            x = ProcessData.createValue();
            
            x.setProcessId(rs.getInt(1));
            x.setProcessTemplateId(rs.getInt(2));
            x.setProcessName(rs.getString(3));
            x.setProcessTypeCd(rs.getString(4));
            x.setProcessStatusCd(rs.getString(5));
            x.setProcessHostname(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            x.setProcessPriority(rs.getInt(11));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ProcessData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT PROCESS_ID FROM CLW_PROCESS");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT PROCESS_ID FROM CLW_PROCESS");
                where = pCriteria.getSqlClause("CLW_PROCESS");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PROCESS.equals(otherTable)){
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
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PROCESS");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PROCESS");
                where = pCriteria.getSqlClause("CLW_PROCESS");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PROCESS.equals(otherTable)){
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
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PROCESS");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PROCESS");
                where = pCriteria.getSqlClause("CLW_PROCESS");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PROCESS.equals(otherTable)){
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
            log.debug("SQL text: " + sql);
        }

        return sql;
    }

    /**
     * Inserts a ProcessData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProcessData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ProcessData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ProcessData insert(Connection pCon, ProcessData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_PROCESS_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PROCESS_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setProcessId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_PROCESS (PROCESS_ID,PROCESS_TEMPLATE_ID,PROCESS_NAME,PROCESS_TYPE_CD,PROCESS_STATUS_CD,PROCESS_HOSTNAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PROCESS_PRIORITY) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getProcessId());
        pstmt.setInt(2,pData.getProcessTemplateId());
        pstmt.setString(3,pData.getProcessName());
        pstmt.setString(4,pData.getProcessTypeCd());
        pstmt.setString(5,pData.getProcessStatusCd());
        pstmt.setString(6,pData.getProcessHostname());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());
        pstmt.setInt(11,pData.getProcessPriority());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PROCESS_ID="+pData.getProcessId());
            log.debug("SQL:   PROCESS_TEMPLATE_ID="+pData.getProcessTemplateId());
            log.debug("SQL:   PROCESS_NAME="+pData.getProcessName());
            log.debug("SQL:   PROCESS_TYPE_CD="+pData.getProcessTypeCd());
            log.debug("SQL:   PROCESS_STATUS_CD="+pData.getProcessStatusCd());
            log.debug("SQL:   PROCESS_HOSTNAME="+pData.getProcessHostname());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   PROCESS_PRIORITY="+pData.getProcessPriority());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setProcessId(0);
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
     * Updates a ProcessData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ProcessData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ProcessData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_PROCESS SET PROCESS_TEMPLATE_ID = ?,PROCESS_NAME = ?,PROCESS_TYPE_CD = ?,PROCESS_STATUS_CD = ?,PROCESS_HOSTNAME = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,PROCESS_PRIORITY = ? WHERE PROCESS_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getProcessTemplateId());
        pstmt.setString(i++,pData.getProcessName());
        pstmt.setString(i++,pData.getProcessTypeCd());
        pstmt.setString(i++,pData.getProcessStatusCd());
        pstmt.setString(i++,pData.getProcessHostname());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getProcessPriority());
        pstmt.setInt(i++,pData.getProcessId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PROCESS_TEMPLATE_ID="+pData.getProcessTemplateId());
            log.debug("SQL:   PROCESS_NAME="+pData.getProcessName());
            log.debug("SQL:   PROCESS_TYPE_CD="+pData.getProcessTypeCd());
            log.debug("SQL:   PROCESS_STATUS_CD="+pData.getProcessStatusCd());
            log.debug("SQL:   PROCESS_HOSTNAME="+pData.getProcessHostname());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   PROCESS_PRIORITY="+pData.getProcessPriority());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ProcessData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pProcessId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pProcessId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_PROCESS WHERE PROCESS_ID = " + pProcessId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ProcessData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_PROCESS");
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
     * Inserts a ProcessData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProcessData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ProcessData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_PROCESS (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "PROCESS_ID,PROCESS_TEMPLATE_ID,PROCESS_NAME,PROCESS_TYPE_CD,PROCESS_STATUS_CD,PROCESS_HOSTNAME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PROCESS_PRIORITY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getProcessId());
        pstmt.setInt(2+4,pData.getProcessTemplateId());
        pstmt.setString(3+4,pData.getProcessName());
        pstmt.setString(4+4,pData.getProcessTypeCd());
        pstmt.setString(5+4,pData.getProcessStatusCd());
        pstmt.setString(6+4,pData.getProcessHostname());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());
        pstmt.setInt(11+4,pData.getProcessPriority());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ProcessData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ProcessData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ProcessData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ProcessData insert(Connection pCon, ProcessData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ProcessData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ProcessData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ProcessData pData, boolean pLogFl)
        throws SQLException {
        ProcessData oldData = null;
        if(pLogFl) {
          int id = pData.getProcessId();
          try {
          oldData = ProcessDataAccess.select(pCon,id);
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
     * Deletes a ProcessData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pProcessId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pProcessId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_PROCESS SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PROCESS d WHERE PROCESS_ID = " + pProcessId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pProcessId);
        return n;
     }

    /**
     * Deletes ProcessData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_PROCESS SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PROCESS d ");
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

