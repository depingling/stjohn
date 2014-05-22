
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ReportScheduleDetailDataAccess
 * Description:  This class is used to build access methods to the RPT_REPORT_SCHEDULE_DETAIL table.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ReportScheduleDetailData;
import com.cleanwise.service.api.value.ReportScheduleDetailDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.sql.*;
import java.util.*;

/**
 * <code>ReportScheduleDetailDataAccess</code>
 */
public class ReportScheduleDetailDataAccess
{
    private static Category log = Category.getInstance(ReportScheduleDetailDataAccess.class.getName());

    /** <code>RPT_REPORT_SCHEDULE_DETAIL</code> table name */
    public static final String RPT_REPORT_SCHEDULE_DETAIL = "RPT_REPORT_SCHEDULE_DETAIL";
    
    /** <code>REPORT_SCHEDULE_DETAIL_ID</code> REPORT_SCHEDULE_DETAIL_ID column of table RPT_REPORT_SCHEDULE_DETAIL */
    public static final String REPORT_SCHEDULE_DETAIL_ID = "REPORT_SCHEDULE_DETAIL_ID";
    /** <code>REPORT_SCHEDULE_ID</code> REPORT_SCHEDULE_ID column of table RPT_REPORT_SCHEDULE_DETAIL */
    public static final String REPORT_SCHEDULE_ID = "REPORT_SCHEDULE_ID";
    /** <code>REPORT_SCHEDULE_DETAIL_CD</code> REPORT_SCHEDULE_DETAIL_CD column of table RPT_REPORT_SCHEDULE_DETAIL */
    public static final String REPORT_SCHEDULE_DETAIL_CD = "REPORT_SCHEDULE_DETAIL_CD";
    /** <code>DETAIL_NAME</code> DETAIL_NAME column of table RPT_REPORT_SCHEDULE_DETAIL */
    public static final String DETAIL_NAME = "DETAIL_NAME";
    /** <code>DETAIL_VALUE</code> DETAIL_VALUE column of table RPT_REPORT_SCHEDULE_DETAIL */
    public static final String DETAIL_VALUE = "DETAIL_VALUE";
    /** <code>ADD_DATE</code> ADD_DATE column of table RPT_REPORT_SCHEDULE_DETAIL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table RPT_REPORT_SCHEDULE_DETAIL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table RPT_REPORT_SCHEDULE_DETAIL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table RPT_REPORT_SCHEDULE_DETAIL */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public ReportScheduleDetailDataAccess() 
    {
    }

    /**
     * Gets a ReportScheduleDetailData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pReportScheduleDetailId The key requested.
     * @return new ReportScheduleDetailData()
     * @throws            SQLException
     */
    public static ReportScheduleDetailData select(Connection pCon, int pReportScheduleDetailId)
        throws SQLException, DataNotFoundException {
        ReportScheduleDetailData x=null;
        String sql="SELECT REPORT_SCHEDULE_DETAIL_ID,REPORT_SCHEDULE_ID,REPORT_SCHEDULE_DETAIL_CD,DETAIL_NAME,DETAIL_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM RPT_REPORT_SCHEDULE_DETAIL WHERE REPORT_SCHEDULE_DETAIL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pReportScheduleDetailId=" + pReportScheduleDetailId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pReportScheduleDetailId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ReportScheduleDetailData.createValue();
            
            x.setReportScheduleDetailId(rs.getInt(1));
            x.setReportScheduleId(rs.getInt(2));
            x.setReportScheduleDetailCd(rs.getString(3));
            x.setDetailName(rs.getString(4));
            x.setDetailValue(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            
        } else {
	    rs.close();
	    stmt.close();
	    throw new DataNotFoundException("REPORT_SCHEDULE_DETAIL_ID :" + pReportScheduleDetailId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ReportScheduleDetailDataVector object that consists
     * of ReportScheduleDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ReportScheduleDetailDataVector()
     * @throws            SQLException
     */
    public static ReportScheduleDetailDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
     * Gets a ReportScheduleDetailDataVector object that consists
     * of ReportScheduleDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ReportScheduleDetailDataVector()
     * @throws            SQLException
     */
    public static ReportScheduleDetailDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT REPORT_SCHEDULE_DETAIL_ID,REPORT_SCHEDULE_ID,REPORT_SCHEDULE_DETAIL_CD,DETAIL_NAME,DETAIL_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM RPT_REPORT_SCHEDULE_DETAIL");
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
        ReportScheduleDetailDataVector v = new ReportScheduleDetailDataVector();
        ReportScheduleDetailData x=null;
        while (rs.next()) {
            // build the object
            x = ReportScheduleDetailData.createValue();
            
            x.setReportScheduleDetailId(rs.getInt(1));
            x.setReportScheduleId(rs.getInt(2));
            x.setReportScheduleDetailCd(rs.getString(3));
            x.setDetailName(rs.getString(4));
            x.setDetailValue(rs.getString(5));
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
     * Gets a ReportScheduleDetailDataVector object that consists
     * of ReportScheduleDetailData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ReportScheduleDetailData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ReportScheduleDetailDataVector()
     * @throws            SQLException
     */
    public static ReportScheduleDetailDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ReportScheduleDetailDataVector v = new ReportScheduleDetailDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT REPORT_SCHEDULE_DETAIL_ID,REPORT_SCHEDULE_ID,REPORT_SCHEDULE_DETAIL_CD,DETAIL_NAME,DETAIL_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM RPT_REPORT_SCHEDULE_DETAIL WHERE REPORT_SCHEDULE_DETAIL_ID IN (");
        
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
            ReportScheduleDetailData x=null;
            while (rs.next()) {
                // build the object
                x=ReportScheduleDetailData.createValue();
                
                x.setReportScheduleDetailId(rs.getInt(1));
                x.setReportScheduleId(rs.getInt(2));
                x.setReportScheduleDetailCd(rs.getString(3));
                x.setDetailName(rs.getString(4));
                x.setDetailValue(rs.getString(5));
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
     * Gets a ReportScheduleDetailDataVector object of all
     * ReportScheduleDetailData objects in the database.
     * @param pCon An open database connection.
     * @return new ReportScheduleDetailDataVector()
     * @throws            SQLException
     */
    public static ReportScheduleDetailDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT REPORT_SCHEDULE_DETAIL_ID,REPORT_SCHEDULE_ID,REPORT_SCHEDULE_DETAIL_CD,DETAIL_NAME,DETAIL_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM RPT_REPORT_SCHEDULE_DETAIL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ReportScheduleDetailDataVector v = new ReportScheduleDetailDataVector();
        ReportScheduleDetailData x = null;
        while (rs.next()) {
            // build the object
            x = ReportScheduleDetailData.createValue();
            
            x.setReportScheduleDetailId(rs.getInt(1));
            x.setReportScheduleId(rs.getInt(2));
            x.setReportScheduleDetailCd(rs.getString(3));
            x.setDetailName(rs.getString(4));
            x.setDetailValue(rs.getString(5));
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
     * ReportScheduleDetailData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT REPORT_SCHEDULE_DETAIL_ID FROM RPT_REPORT_SCHEDULE_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM RPT_REPORT_SCHEDULE_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM RPT_REPORT_SCHEDULE_DETAIL");
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
     * Inserts a ReportScheduleDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportScheduleDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and 
     * "ModDate" fields will be set to the current date. 
     * @return new ReportScheduleDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ReportScheduleDetailData insert(Connection pCon, ReportScheduleDetailData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
	    log.debug("SELECT RPT_REPORT_SCHEDULE_DETAIL_SEQ.NEXTVAL FROM DUAL");
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT RPT_REPORT_SCHEDULE_DETAIL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setReportScheduleDetailId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO RPT_REPORT_SCHEDULE_DETAIL (REPORT_SCHEDULE_DETAIL_ID,REPORT_SCHEDULE_ID,REPORT_SCHEDULE_DETAIL_CD,DETAIL_NAME,DETAIL_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?)";

        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pData.setAddDate(current);

        pData.setModDate(current);

        pstmt.setInt(1,pData.getReportScheduleDetailId());
        pstmt.setInt(2,pData.getReportScheduleId());
        pstmt.setString(3,pData.getReportScheduleDetailCd());
        pstmt.setString(4,pData.getDetailName());
        pstmt.setString(5,pData.getDetailValue());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   REPORT_SCHEDULE_DETAIL_ID="+pData.getReportScheduleDetailId());
            log.debug("SQL:   REPORT_SCHEDULE_ID="+pData.getReportScheduleId());
            log.debug("SQL:   REPORT_SCHEDULE_DETAIL_CD="+pData.getReportScheduleDetailCd());
            log.debug("SQL:   DETAIL_NAME="+pData.getDetailName());
            log.debug("SQL:   DETAIL_VALUE="+pData.getDetailValue());
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
     * Updates a ReportScheduleDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportScheduleDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ReportScheduleDetailData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE RPT_REPORT_SCHEDULE_DETAIL SET REPORT_SCHEDULE_ID = ?,REPORT_SCHEDULE_DETAIL_CD = ?,DETAIL_NAME = ?,DETAIL_VALUE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE REPORT_SCHEDULE_DETAIL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getReportScheduleId());
        pstmt.setString(i++,pData.getReportScheduleDetailCd());
        pstmt.setString(i++,pData.getDetailName());
        pstmt.setString(i++,pData.getDetailValue());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getReportScheduleDetailId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   REPORT_SCHEDULE_ID="+pData.getReportScheduleId());
            log.debug("SQL:   REPORT_SCHEDULE_DETAIL_CD="+pData.getReportScheduleDetailCd());
            log.debug("SQL:   DETAIL_NAME="+pData.getDetailName());
            log.debug("SQL:   DETAIL_VALUE="+pData.getDetailValue());
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
     * Deletes a ReportScheduleDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pReportScheduleDetailId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pReportScheduleDetailId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM RPT_REPORT_SCHEDULE_DETAIL WHERE REPORT_SCHEDULE_DETAIL_ID = " + pReportScheduleDetailId;

        if (log.isDebugEnabled()) {
	    log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ReportScheduleDetailData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM RPT_REPORT_SCHEDULE_DETAIL");
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

