
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ReportResultDataAccess
 * Description:  This class is used to build access methods to the RPT_REPORT_RESULT table.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ReportResultData;
import com.cleanwise.service.api.value.ReportResultDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.sql.*;
import java.util.*;

/**
 * <code>ReportResultDataAccess</code>
 */
public class ReportResultDataAccess
{
    private static Category log = Category.getInstance(ReportResultDataAccess.class.getName());

    /** <code>RPT_REPORT_RESULT</code> table name */
    public static final String RPT_REPORT_RESULT = "RPT_REPORT_RESULT";
    
    /** <code>GENERIC_REPORT_ID</code> GENERIC_REPORT_ID column of table RPT_REPORT_RESULT */
    public static final String GENERIC_REPORT_ID = "GENERIC_REPORT_ID";
    /** <code>REPORT_RESULT_ID</code> REPORT_RESULT_ID column of table RPT_REPORT_RESULT */
    public static final String REPORT_RESULT_ID = "REPORT_RESULT_ID";
    /** <code>REPORT_SCHEDULE_ID</code> REPORT_SCHEDULE_ID column of table RPT_REPORT_RESULT */
    public static final String REPORT_SCHEDULE_ID = "REPORT_SCHEDULE_ID";
    /** <code>USER_ID</code> USER_ID column of table RPT_REPORT_RESULT */
    public static final String USER_ID = "USER_ID";
    /** <code>ADD_BY</code> ADD_BY column of table RPT_REPORT_RESULT */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table RPT_REPORT_RESULT */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table RPT_REPORT_RESULT */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table RPT_REPORT_RESULT */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>PROTECTED_FL</code> PROTECTED_FL column of table RPT_REPORT_RESULT */
    public static final String PROTECTED_FL = "PROTECTED_FL";
    /** <code>REPORT_CATEGORY</code> REPORT_CATEGORY column of table RPT_REPORT_RESULT */
    public static final String REPORT_CATEGORY = "REPORT_CATEGORY";
    /** <code>REPORT_NAME</code> REPORT_NAME column of table RPT_REPORT_RESULT */
    public static final String REPORT_NAME = "REPORT_NAME";
    /** <code>REPORT_RESULT_STATUS_CD</code> REPORT_RESULT_STATUS_CD column of table RPT_REPORT_RESULT */
    public static final String REPORT_RESULT_STATUS_CD = "REPORT_RESULT_STATUS_CD";

    /**
     * Constructor.
     */
    public ReportResultDataAccess() 
    {
    }

    /**
     * Gets a ReportResultData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pReportResultId The key requested.
     * @return new ReportResultData()
     * @throws            SQLException
     */
    public static ReportResultData select(Connection pCon, int pReportResultId)
        throws SQLException, DataNotFoundException {
        ReportResultData x=null;
        String sql="SELECT GENERIC_REPORT_ID,REPORT_RESULT_ID,REPORT_SCHEDULE_ID,USER_ID,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,PROTECTED_FL,REPORT_CATEGORY,REPORT_NAME,REPORT_RESULT_STATUS_CD FROM RPT_REPORT_RESULT WHERE REPORT_RESULT_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pReportResultId=" + pReportResultId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pReportResultId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ReportResultData.createValue();
            
            x.setGenericReportId(rs.getInt(1));
            x.setReportResultId(rs.getInt(2));
            x.setReportScheduleId(rs.getInt(3));
            x.setUserId(rs.getInt(4));
            x.setAddBy(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setModBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setProtectedFl(rs.getString(9));
            x.setReportCategory(rs.getString(10));
            x.setReportName(rs.getString(11));
            x.setReportResultStatusCd(rs.getString(12));
            
        } else {
	    rs.close();
	    stmt.close();
	    throw new DataNotFoundException("REPORT_RESULT_ID :" + pReportResultId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ReportResultDataVector object that consists
     * of ReportResultData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ReportResultDataVector()
     * @throws            SQLException
     */
    public static ReportResultDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
     * Gets a ReportResultDataVector object that consists
     * of ReportResultData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ReportResultDataVector()
     * @throws            SQLException
     */
    public static ReportResultDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT GENERIC_REPORT_ID,REPORT_RESULT_ID,REPORT_SCHEDULE_ID,USER_ID,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,PROTECTED_FL,REPORT_CATEGORY,REPORT_NAME,REPORT_RESULT_STATUS_CD FROM RPT_REPORT_RESULT");
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
        if ( pMaxRows > 0 ) {
            // Insure that only positive values are set.
              stmt.setMaxRows(pMaxRows);
        }
        ResultSet rs=stmt.executeQuery(sql);
        ReportResultDataVector v = new ReportResultDataVector();
        ReportResultData x=null;
        while (rs.next()) {
            // build the object
            x = ReportResultData.createValue();
            
            x.setGenericReportId(rs.getInt(1));
            x.setReportResultId(rs.getInt(2));
            x.setReportScheduleId(rs.getInt(3));
            x.setUserId(rs.getInt(4));
            x.setAddBy(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setModBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setProtectedFl(rs.getString(9));
            x.setReportCategory(rs.getString(10));
            x.setReportName(rs.getString(11));
            x.setReportResultStatusCd(rs.getString(12));
            
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ReportResultDataVector object that consists
     * of ReportResultData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ReportResultData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ReportResultDataVector()
     * @throws            SQLException
     */
    public static ReportResultDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ReportResultDataVector v = new ReportResultDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT GENERIC_REPORT_ID,REPORT_RESULT_ID,REPORT_SCHEDULE_ID,USER_ID,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,PROTECTED_FL,REPORT_CATEGORY,REPORT_NAME,REPORT_RESULT_STATUS_CD FROM RPT_REPORT_RESULT WHERE REPORT_RESULT_ID IN (");
        
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
            ReportResultData x=null;
            while (rs.next()) {
                // build the object
                x=ReportResultData.createValue();
                
                x.setGenericReportId(rs.getInt(1));
                x.setReportResultId(rs.getInt(2));
                x.setReportScheduleId(rs.getInt(3));
                x.setUserId(rs.getInt(4));
                x.setAddBy(rs.getString(5));
                x.setAddDate(rs.getTimestamp(6));
                x.setModBy(rs.getString(7));
                x.setModDate(rs.getTimestamp(8));
                x.setProtectedFl(rs.getString(9));
                x.setReportCategory(rs.getString(10));
                x.setReportName(rs.getString(11));
                x.setReportResultStatusCd(rs.getString(12));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ReportResultDataVector object of all
     * ReportResultData objects in the database.
     * @param pCon An open database connection.
     * @return new ReportResultDataVector()
     * @throws            SQLException
     */
    public static ReportResultDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT GENERIC_REPORT_ID,REPORT_RESULT_ID,REPORT_SCHEDULE_ID,USER_ID,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,PROTECTED_FL,REPORT_CATEGORY,REPORT_NAME,REPORT_RESULT_STATUS_CD FROM RPT_REPORT_RESULT";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ReportResultDataVector v = new ReportResultDataVector();
        ReportResultData x = null;
        while (rs.next()) {
            // build the object
            x = ReportResultData.createValue();
            
            x.setGenericReportId(rs.getInt(1));
            x.setReportResultId(rs.getInt(2));
            x.setReportScheduleId(rs.getInt(3));
            x.setUserId(rs.getInt(4));
            x.setAddBy(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setModBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setProtectedFl(rs.getString(9));
            x.setReportCategory(rs.getString(10));
            x.setReportName(rs.getString(11));
            x.setReportResultStatusCd(rs.getString(12));
            
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ReportResultData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT REPORT_RESULT_ID FROM RPT_REPORT_RESULT");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM RPT_REPORT_RESULT");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM RPT_REPORT_RESULT");
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
     * Inserts a ReportResultData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportResultData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and 
     * "ModDate" fields will be set to the current date. 
     * @return new ReportResultData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ReportResultData insert(Connection pCon, ReportResultData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
	    log.debug("SELECT RPT_REPORT_RESULT_SEQ.NEXTVAL FROM DUAL");
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT RPT_REPORT_RESULT_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setReportResultId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO RPT_REPORT_RESULT (GENERIC_REPORT_ID,REPORT_RESULT_ID,REPORT_SCHEDULE_ID,USER_ID,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE,PROTECTED_FL,REPORT_CATEGORY,REPORT_NAME,REPORT_RESULT_STATUS_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pData.setAddDate(current);

        pData.setModDate(current);

        pstmt.setInt(1,pData.getGenericReportId());
        pstmt.setInt(2,pData.getReportResultId());
        pstmt.setInt(3,pData.getReportScheduleId());
        pstmt.setInt(4,pData.getUserId());
        pstmt.setString(5,pData.getAddBy());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getModBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getProtectedFl());
        pstmt.setString(10,pData.getReportCategory());
        pstmt.setString(11,pData.getReportName());
        pstmt.setString(12,pData.getReportResultStatusCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   GENERIC_REPORT_ID="+pData.getGenericReportId());
            log.debug("SQL:   REPORT_RESULT_ID="+pData.getReportResultId());
            log.debug("SQL:   REPORT_SCHEDULE_ID="+pData.getReportScheduleId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   PROTECTED_FL="+pData.getProtectedFl());
            log.debug("SQL:   REPORT_CATEGORY="+pData.getReportCategory());
            log.debug("SQL:   REPORT_NAME="+pData.getReportName());
            log.debug("SQL:   REPORT_RESULT_STATUS_CD="+pData.getReportResultStatusCd());
	    log.debug("SQL: " + sql);
        }

        pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);
        return pData;
    }

    /**
     * Updates a ReportResultData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportResultData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ReportResultData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE RPT_REPORT_RESULT SET GENERIC_REPORT_ID = ?,REPORT_SCHEDULE_ID = ?,USER_ID = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ?,PROTECTED_FL = ?,REPORT_CATEGORY = ?,REPORT_NAME = ?,REPORT_RESULT_STATUS_CD = ? WHERE REPORT_RESULT_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getGenericReportId());
        pstmt.setInt(i++,pData.getReportScheduleId());
        pstmt.setInt(i++,pData.getUserId());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getProtectedFl());
        pstmt.setString(i++,pData.getReportCategory());
        pstmt.setString(i++,pData.getReportName());
        pstmt.setString(i++,pData.getReportResultStatusCd());
        pstmt.setInt(i++,pData.getReportResultId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   GENERIC_REPORT_ID="+pData.getGenericReportId());
            log.debug("SQL:   REPORT_SCHEDULE_ID="+pData.getReportScheduleId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   PROTECTED_FL="+pData.getProtectedFl());
            log.debug("SQL:   REPORT_CATEGORY="+pData.getReportCategory());
            log.debug("SQL:   REPORT_NAME="+pData.getReportName());
            log.debug("SQL:   REPORT_RESULT_STATUS_CD="+pData.getReportResultStatusCd());
	    log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ReportResultData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pReportResultId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pReportResultId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM RPT_REPORT_RESULT WHERE REPORT_RESULT_ID = " + pReportResultId;

        if (log.isDebugEnabled()) {
	    log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ReportResultData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM RPT_REPORT_RESULT");
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
}

