
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        CleaningProcDataAccess
 * Description:  This class is used to build access methods to the CLW_CLEANING_PROC table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.CleaningProcData;
import com.cleanwise.service.api.value.CleaningProcDataVector;
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
 * <code>CleaningProcDataAccess</code>
 */
public class CleaningProcDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(CleaningProcDataAccess.class.getName());

    /** <code>CLW_CLEANING_PROC</code> table name */
	/* Primary key: CLEANING_PROC_ID */
	
    public static final String CLW_CLEANING_PROC = "CLW_CLEANING_PROC";
    
    /** <code>CLEANING_PROC_ID</code> CLEANING_PROC_ID column of table CLW_CLEANING_PROC */
    public static final String CLEANING_PROC_ID = "CLEANING_PROC_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_CLEANING_PROC */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>CLEANING_PROC_CD</code> CLEANING_PROC_CD column of table CLW_CLEANING_PROC */
    public static final String CLEANING_PROC_CD = "CLEANING_PROC_CD";
    /** <code>ESTIMATOR_PAGE_CD</code> ESTIMATOR_PAGE_CD column of table CLW_CLEANING_PROC */
    public static final String ESTIMATOR_PAGE_CD = "ESTIMATOR_PAGE_CD";
    /** <code>SEQ_NUM</code> SEQ_NUM column of table CLW_CLEANING_PROC */
    public static final String SEQ_NUM = "SEQ_NUM";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_CLEANING_PROC */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_CLEANING_PROC */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_CLEANING_PROC */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_CLEANING_PROC */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public CleaningProcDataAccess()
    {
    }

    /**
     * Gets a CleaningProcData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pCleaningProcId The key requested.
     * @return new CleaningProcData()
     * @throws            SQLException
     */
    public static CleaningProcData select(Connection pCon, int pCleaningProcId)
        throws SQLException, DataNotFoundException {
        CleaningProcData x=null;
        String sql="SELECT CLEANING_PROC_ID,SHORT_DESC,CLEANING_PROC_CD,ESTIMATOR_PAGE_CD,SEQ_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CLEANING_PROC WHERE CLEANING_PROC_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pCleaningProcId=" + pCleaningProcId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pCleaningProcId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=CleaningProcData.createValue();
            
            x.setCleaningProcId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setCleaningProcCd(rs.getString(3));
            x.setEstimatorPageCd(rs.getString(4));
            x.setSeqNum(rs.getInt(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("CLEANING_PROC_ID :" + pCleaningProcId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a CleaningProcDataVector object that consists
     * of CleaningProcData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new CleaningProcDataVector()
     * @throws            SQLException
     */
    public static CleaningProcDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a CleaningProcData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_CLEANING_PROC.CLEANING_PROC_ID,CLW_CLEANING_PROC.SHORT_DESC,CLW_CLEANING_PROC.CLEANING_PROC_CD,CLW_CLEANING_PROC.ESTIMATOR_PAGE_CD,CLW_CLEANING_PROC.SEQ_NUM,CLW_CLEANING_PROC.ADD_DATE,CLW_CLEANING_PROC.ADD_BY,CLW_CLEANING_PROC.MOD_DATE,CLW_CLEANING_PROC.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated CleaningProcData Object.
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
    *@returns a populated CleaningProcData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         CleaningProcData x = CleaningProcData.createValue();
         
         x.setCleaningProcId(rs.getInt(1+offset));
         x.setShortDesc(rs.getString(2+offset));
         x.setCleaningProcCd(rs.getString(3+offset));
         x.setEstimatorPageCd(rs.getString(4+offset));
         x.setSeqNum(rs.getInt(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the CleaningProcData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a CleaningProcDataVector object that consists
     * of CleaningProcData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new CleaningProcDataVector()
     * @throws            SQLException
     */
    public static CleaningProcDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT CLEANING_PROC_ID,SHORT_DESC,CLEANING_PROC_CD,ESTIMATOR_PAGE_CD,SEQ_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CLEANING_PROC");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_CLEANING_PROC.CLEANING_PROC_ID,CLW_CLEANING_PROC.SHORT_DESC,CLW_CLEANING_PROC.CLEANING_PROC_CD,CLW_CLEANING_PROC.ESTIMATOR_PAGE_CD,CLW_CLEANING_PROC.SEQ_NUM,CLW_CLEANING_PROC.ADD_DATE,CLW_CLEANING_PROC.ADD_BY,CLW_CLEANING_PROC.MOD_DATE,CLW_CLEANING_PROC.MOD_BY FROM CLW_CLEANING_PROC");
                where = pCriteria.getSqlClause("CLW_CLEANING_PROC");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CLEANING_PROC.equals(otherTable)){
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
        CleaningProcDataVector v = new CleaningProcDataVector();
        while (rs.next()) {
            CleaningProcData x = CleaningProcData.createValue();
            
            x.setCleaningProcId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setCleaningProcCd(rs.getString(3));
            x.setEstimatorPageCd(rs.getString(4));
            x.setSeqNum(rs.getInt(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a CleaningProcDataVector object that consists
     * of CleaningProcData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for CleaningProcData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new CleaningProcDataVector()
     * @throws            SQLException
     */
    public static CleaningProcDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        CleaningProcDataVector v = new CleaningProcDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT CLEANING_PROC_ID,SHORT_DESC,CLEANING_PROC_CD,ESTIMATOR_PAGE_CD,SEQ_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CLEANING_PROC WHERE CLEANING_PROC_ID IN (");

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
            CleaningProcData x=null;
            while (rs.next()) {
                // build the object
                x=CleaningProcData.createValue();
                
                x.setCleaningProcId(rs.getInt(1));
                x.setShortDesc(rs.getString(2));
                x.setCleaningProcCd(rs.getString(3));
                x.setEstimatorPageCd(rs.getString(4));
                x.setSeqNum(rs.getInt(5));
                x.setAddDate(rs.getTimestamp(6));
                x.setAddBy(rs.getString(7));
                x.setModDate(rs.getTimestamp(8));
                x.setModBy(rs.getString(9));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a CleaningProcDataVector object of all
     * CleaningProcData objects in the database.
     * @param pCon An open database connection.
     * @return new CleaningProcDataVector()
     * @throws            SQLException
     */
    public static CleaningProcDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT CLEANING_PROC_ID,SHORT_DESC,CLEANING_PROC_CD,ESTIMATOR_PAGE_CD,SEQ_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CLEANING_PROC";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        CleaningProcDataVector v = new CleaningProcDataVector();
        CleaningProcData x = null;
        while (rs.next()) {
            // build the object
            x = CleaningProcData.createValue();
            
            x.setCleaningProcId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setCleaningProcCd(rs.getString(3));
            x.setEstimatorPageCd(rs.getString(4));
            x.setSeqNum(rs.getInt(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * CleaningProcData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT CLEANING_PROC_ID FROM CLW_CLEANING_PROC");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CLEANING_PROC");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CLEANING_PROC");
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
     * Inserts a CleaningProcData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CleaningProcData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new CleaningProcData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CleaningProcData insert(Connection pCon, CleaningProcData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_CLEANING_PROC_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_CLEANING_PROC_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setCleaningProcId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_CLEANING_PROC (CLEANING_PROC_ID,SHORT_DESC,CLEANING_PROC_CD,ESTIMATOR_PAGE_CD,SEQ_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getCleaningProcId());
        pstmt.setString(2,pData.getShortDesc());
        pstmt.setString(3,pData.getCleaningProcCd());
        pstmt.setString(4,pData.getEstimatorPageCd());
        pstmt.setInt(5,pData.getSeqNum());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CLEANING_PROC_ID="+pData.getCleaningProcId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   CLEANING_PROC_CD="+pData.getCleaningProcCd());
            log.debug("SQL:   ESTIMATOR_PAGE_CD="+pData.getEstimatorPageCd());
            log.debug("SQL:   SEQ_NUM="+pData.getSeqNum());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setCleaningProcId(0);
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
     * Updates a CleaningProcData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CleaningProcData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CleaningProcData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_CLEANING_PROC SET SHORT_DESC = ?,CLEANING_PROC_CD = ?,ESTIMATOR_PAGE_CD = ?,SEQ_NUM = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE CLEANING_PROC_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getCleaningProcCd());
        pstmt.setString(i++,pData.getEstimatorPageCd());
        pstmt.setInt(i++,pData.getSeqNum());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getCleaningProcId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   CLEANING_PROC_CD="+pData.getCleaningProcCd());
            log.debug("SQL:   ESTIMATOR_PAGE_CD="+pData.getEstimatorPageCd());
            log.debug("SQL:   SEQ_NUM="+pData.getSeqNum());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a CleaningProcData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCleaningProcId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCleaningProcId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_CLEANING_PROC WHERE CLEANING_PROC_ID = " + pCleaningProcId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes CleaningProcData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_CLEANING_PROC");
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
     * Inserts a CleaningProcData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CleaningProcData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, CleaningProcData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_CLEANING_PROC (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "CLEANING_PROC_ID,SHORT_DESC,CLEANING_PROC_CD,ESTIMATOR_PAGE_CD,SEQ_NUM,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getCleaningProcId());
        pstmt.setString(2+4,pData.getShortDesc());
        pstmt.setString(3+4,pData.getCleaningProcCd());
        pstmt.setString(4+4,pData.getEstimatorPageCd());
        pstmt.setInt(5+4,pData.getSeqNum());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a CleaningProcData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CleaningProcData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new CleaningProcData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CleaningProcData insert(Connection pCon, CleaningProcData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a CleaningProcData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CleaningProcData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CleaningProcData pData, boolean pLogFl)
        throws SQLException {
        CleaningProcData oldData = null;
        if(pLogFl) {
          int id = pData.getCleaningProcId();
          try {
          oldData = CleaningProcDataAccess.select(pCon,id);
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
     * Deletes a CleaningProcData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCleaningProcId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCleaningProcId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_CLEANING_PROC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CLEANING_PROC d WHERE CLEANING_PROC_ID = " + pCleaningProcId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pCleaningProcId);
        return n;
     }

    /**
     * Deletes CleaningProcData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_CLEANING_PROC SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CLEANING_PROC d ");
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

