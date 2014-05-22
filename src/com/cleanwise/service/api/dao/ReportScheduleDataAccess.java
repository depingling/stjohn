
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ReportScheduleDataAccess
 * Description:  This class is used to build access methods to the RPT_REPORT_SCHEDULE table.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ReportScheduleData;
import com.cleanwise.service.api.value.ReportScheduleDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.sql.*;
import java.util.*;

/**
 * <code>ReportScheduleDataAccess</code>
 */
public class ReportScheduleDataAccess
{
    private static Category log = Category.getInstance(ReportScheduleDataAccess.class.getName());

    /** <code>RPT_REPORT_SCHEDULE</code> table name */
    public static final String RPT_REPORT_SCHEDULE = "RPT_REPORT_SCHEDULE";
    
    /** <code>REPORT_SCHEDULE_ID</code> REPORT_SCHEDULE_ID column of table RPT_REPORT_SCHEDULE */
    public static final String REPORT_SCHEDULE_ID = "REPORT_SCHEDULE_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table RPT_REPORT_SCHEDULE */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>GENERIC_REPORT_ID</code> GENERIC_REPORT_ID column of table RPT_REPORT_SCHEDULE */
    public static final String GENERIC_REPORT_ID = "GENERIC_REPORT_ID";
    /** <code>REPORT_SCHEDULE_STATUS_CD</code> REPORT_SCHEDULE_STATUS_CD column of table RPT_REPORT_SCHEDULE */
    public static final String REPORT_SCHEDULE_STATUS_CD = "REPORT_SCHEDULE_STATUS_CD";
    /** <code>REPORT_SCHEDULE_RULE_CD</code> REPORT_SCHEDULE_RULE_CD column of table RPT_REPORT_SCHEDULE */
    public static final String REPORT_SCHEDULE_RULE_CD = "REPORT_SCHEDULE_RULE_CD";
    /** <code>CYCLE</code> CYCLE column of table RPT_REPORT_SCHEDULE */
    public static final String CYCLE = "CYCLE";
    /** <code>LAST_RUN_DATE</code> LAST_RUN_DATE column of table RPT_REPORT_SCHEDULE */
    public static final String LAST_RUN_DATE = "LAST_RUN_DATE";
    /** <code>ADD_DATE</code> ADD_DATE column of table RPT_REPORT_SCHEDULE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table RPT_REPORT_SCHEDULE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table RPT_REPORT_SCHEDULE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table RPT_REPORT_SCHEDULE */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public ReportScheduleDataAccess() 
    {
    }

    /**
     * Gets a ReportScheduleData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pReportScheduleId The key requested.
     * @return new ReportScheduleData()
     * @throws            SQLException
     */
    public static ReportScheduleData select(Connection pCon, int pReportScheduleId)
        throws SQLException, DataNotFoundException {
        ReportScheduleData x=null;
        String sql="SELECT REPORT_SCHEDULE_ID,SHORT_DESC,GENERIC_REPORT_ID,REPORT_SCHEDULE_STATUS_CD,REPORT_SCHEDULE_RULE_CD,CYCLE,LAST_RUN_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM RPT_REPORT_SCHEDULE WHERE REPORT_SCHEDULE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pReportScheduleId=" + pReportScheduleId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pReportScheduleId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ReportScheduleData.createValue();
            
            x.setReportScheduleId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setGenericReportId(rs.getInt(3));
            x.setReportScheduleStatusCd(rs.getString(4));
            x.setReportScheduleRuleCd(rs.getString(5));
            x.setCycle(rs.getInt(6));
            x.setLastRunDate(rs.getDate(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            
        } else {
	    rs.close();
	    stmt.close();
	    throw new DataNotFoundException("REPORT_SCHEDULE_ID :" + pReportScheduleId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ReportScheduleDataVector object that consists
     * of ReportScheduleData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ReportScheduleDataVector()
     * @throws            SQLException
     */
    public static ReportScheduleDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
     * Gets a ReportScheduleDataVector object that consists
     * of ReportScheduleData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ReportScheduleDataVector()
     * @throws            SQLException
     */
    public static ReportScheduleDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT REPORT_SCHEDULE_ID,SHORT_DESC,GENERIC_REPORT_ID,REPORT_SCHEDULE_STATUS_CD,REPORT_SCHEDULE_RULE_CD,CYCLE,LAST_RUN_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM RPT_REPORT_SCHEDULE");
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
        ReportScheduleDataVector v = new ReportScheduleDataVector();
        ReportScheduleData x=null;
        while (rs.next()) {
            // build the object
            x = ReportScheduleData.createValue();
            
            x.setReportScheduleId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setGenericReportId(rs.getInt(3));
            x.setReportScheduleStatusCd(rs.getString(4));
            x.setReportScheduleRuleCd(rs.getString(5));
            x.setCycle(rs.getInt(6));
            x.setLastRunDate(rs.getDate(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ReportScheduleDataVector object that consists
     * of ReportScheduleData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ReportScheduleData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ReportScheduleDataVector()
     * @throws            SQLException
     */
    public static ReportScheduleDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ReportScheduleDataVector v = new ReportScheduleDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT REPORT_SCHEDULE_ID,SHORT_DESC,GENERIC_REPORT_ID,REPORT_SCHEDULE_STATUS_CD,REPORT_SCHEDULE_RULE_CD,CYCLE,LAST_RUN_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM RPT_REPORT_SCHEDULE WHERE REPORT_SCHEDULE_ID IN (");
        
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
            ReportScheduleData x=null;
            while (rs.next()) {
                // build the object
                x=ReportScheduleData.createValue();
                
                x.setReportScheduleId(rs.getInt(1));
                x.setShortDesc(rs.getString(2));
                x.setGenericReportId(rs.getInt(3));
                x.setReportScheduleStatusCd(rs.getString(4));
                x.setReportScheduleRuleCd(rs.getString(5));
                x.setCycle(rs.getInt(6));
                x.setLastRunDate(rs.getDate(7));
                x.setAddDate(rs.getTimestamp(8));
                x.setAddBy(rs.getString(9));
                x.setModDate(rs.getTimestamp(10));
                x.setModBy(rs.getString(11));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ReportScheduleDataVector object of all
     * ReportScheduleData objects in the database.
     * @param pCon An open database connection.
     * @return new ReportScheduleDataVector()
     * @throws            SQLException
     */
    public static ReportScheduleDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT REPORT_SCHEDULE_ID,SHORT_DESC,GENERIC_REPORT_ID,REPORT_SCHEDULE_STATUS_CD,REPORT_SCHEDULE_RULE_CD,CYCLE,LAST_RUN_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM RPT_REPORT_SCHEDULE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ReportScheduleDataVector v = new ReportScheduleDataVector();
        ReportScheduleData x = null;
        while (rs.next()) {
            // build the object
            x = ReportScheduleData.createValue();
            
            x.setReportScheduleId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setGenericReportId(rs.getInt(3));
            x.setReportScheduleStatusCd(rs.getString(4));
            x.setReportScheduleRuleCd(rs.getString(5));
            x.setCycle(rs.getInt(6));
            x.setLastRunDate(rs.getDate(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ReportScheduleData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT REPORT_SCHEDULE_ID FROM RPT_REPORT_SCHEDULE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM RPT_REPORT_SCHEDULE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM RPT_REPORT_SCHEDULE");
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
     * Inserts a ReportScheduleData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportScheduleData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and 
     * "ModDate" fields will be set to the current date. 
     * @return new ReportScheduleData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ReportScheduleData insert(Connection pCon, ReportScheduleData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
	    log.debug("SELECT RPT_REPORT_SCHEDULE_SEQ.NEXTVAL FROM DUAL");
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT RPT_REPORT_SCHEDULE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setReportScheduleId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO RPT_REPORT_SCHEDULE (REPORT_SCHEDULE_ID,SHORT_DESC,GENERIC_REPORT_ID,REPORT_SCHEDULE_STATUS_CD,REPORT_SCHEDULE_RULE_CD,CYCLE,LAST_RUN_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pData.setAddDate(current);

        pData.setModDate(current);

        pstmt.setInt(1,pData.getReportScheduleId());
        pstmt.setString(2,pData.getShortDesc());
        pstmt.setInt(3,pData.getGenericReportId());
        pstmt.setString(4,pData.getReportScheduleStatusCd());
        pstmt.setString(5,pData.getReportScheduleRuleCd());
        pstmt.setInt(6,pData.getCycle());
        pstmt.setDate(7,DBAccess.toSQLDate(pData.getLastRunDate()));
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9,pData.getAddBy());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   REPORT_SCHEDULE_ID="+pData.getReportScheduleId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   GENERIC_REPORT_ID="+pData.getGenericReportId());
            log.debug("SQL:   REPORT_SCHEDULE_STATUS_CD="+pData.getReportScheduleStatusCd());
            log.debug("SQL:   REPORT_SCHEDULE_RULE_CD="+pData.getReportScheduleRuleCd());
            log.debug("SQL:   CYCLE="+pData.getCycle());
            log.debug("SQL:   LAST_RUN_DATE="+pData.getLastRunDate());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
	    log.debug("SQL: " + sql);
        }

        pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);
        return pData;
    }

    /**
     * Updates a ReportScheduleData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportScheduleData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ReportScheduleData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE RPT_REPORT_SCHEDULE SET SHORT_DESC = ?,GENERIC_REPORT_ID = ?,REPORT_SCHEDULE_STATUS_CD = ?,REPORT_SCHEDULE_RULE_CD = ?,CYCLE = ?,LAST_RUN_DATE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE REPORT_SCHEDULE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setInt(i++,pData.getGenericReportId());
        pstmt.setString(i++,pData.getReportScheduleStatusCd());
        pstmt.setString(i++,pData.getReportScheduleRuleCd());
        pstmt.setInt(i++,pData.getCycle());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getLastRunDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getReportScheduleId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   GENERIC_REPORT_ID="+pData.getGenericReportId());
            log.debug("SQL:   REPORT_SCHEDULE_STATUS_CD="+pData.getReportScheduleStatusCd());
            log.debug("SQL:   REPORT_SCHEDULE_RULE_CD="+pData.getReportScheduleRuleCd());
            log.debug("SQL:   CYCLE="+pData.getCycle());
            log.debug("SQL:   LAST_RUN_DATE="+pData.getLastRunDate());
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
     * Deletes a ReportScheduleData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pReportScheduleId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pReportScheduleId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM RPT_REPORT_SCHEDULE WHERE REPORT_SCHEDULE_ID = " + pReportScheduleId;

        if (log.isDebugEnabled()) {
	    log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ReportScheduleData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM RPT_REPORT_SCHEDULE");
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

