
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ReportResultParamDataAccess
 * Description:  This class is used to build access methods to the RPT_REPORT_RESULT_PARAM table.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ReportResultParamData;
import com.cleanwise.service.api.value.ReportResultParamDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.sql.*;
import java.util.*;

/**
 * <code>ReportResultParamDataAccess</code>
 */
public class ReportResultParamDataAccess
{
    private static Category log = Category.getInstance(ReportResultParamDataAccess.class.getName());

    /** <code>RPT_REPORT_RESULT_PARAM</code> table name */
    public static final String RPT_REPORT_RESULT_PARAM = "RPT_REPORT_RESULT_PARAM";
    
    /** <code>REPORT_RESULT_PARAM_ID</code> REPORT_RESULT_PARAM_ID column of table RPT_REPORT_RESULT_PARAM */
    public static final String REPORT_RESULT_PARAM_ID = "REPORT_RESULT_PARAM_ID";
    /** <code>REPORT_RESULT_ID</code> REPORT_RESULT_ID column of table RPT_REPORT_RESULT_PARAM */
    public static final String REPORT_RESULT_ID = "REPORT_RESULT_ID";
    /** <code>PARAM_NAME</code> PARAM_NAME column of table RPT_REPORT_RESULT_PARAM */
    public static final String PARAM_NAME = "PARAM_NAME";
    /** <code>PARAM_VALUE</code> PARAM_VALUE column of table RPT_REPORT_RESULT_PARAM */
    public static final String PARAM_VALUE = "PARAM_VALUE";

    /**
     * Constructor.
     */
    public ReportResultParamDataAccess() 
    {
    }

    /**
     * Gets a ReportResultParamData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param p The key requested.
     * @return new ReportResultParamData()
     * @throws            SQLException
     */
    public static ReportResultParamData select(Connection pCon, int pReportResultParamId)
        throws SQLException, DataNotFoundException {
        ReportResultParamData x=null;
        String sql="SELECT REPORT_RESULT_PARAM_ID,REPORT_RESULT_ID,PARAM_NAME,PARAM_VALUE FROM RPT_REPORT_RESULT_PARAM WHERE  = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pReportResultParamId=" + pReportResultParamId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pReportResultParamId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ReportResultParamData.createValue();
            
            x.setReportResultParamId(rs.getInt(1));
            x.setReportResultId(rs.getInt(2));
            x.setParamName(rs.getString(3));
            x.setParamValue(rs.getString(4));
            
        } else {
	    rs.close();
	    stmt.close();
	    throw new DataNotFoundException(" :" + pReportResultParamId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ReportResultParamDataVector object that consists
     * of ReportResultParamData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ReportResultParamDataVector()
     * @throws            SQLException
     */
    public static ReportResultParamDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
     * Gets a ReportResultParamDataVector object that consists
     * of ReportResultParamData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ReportResultParamDataVector()
     * @throws            SQLException
     */
    public static ReportResultParamDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT REPORT_RESULT_PARAM_ID,REPORT_RESULT_ID,PARAM_NAME,PARAM_VALUE FROM RPT_REPORT_RESULT_PARAM");
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
        ReportResultParamDataVector v = new ReportResultParamDataVector();
        ReportResultParamData x=null;
        while (rs.next()) {
            // build the object
            x = ReportResultParamData.createValue();
            
            x.setReportResultParamId(rs.getInt(1));
            x.setReportResultId(rs.getInt(2));
            x.setParamName(rs.getString(3));
            x.setParamValue(rs.getString(4));
            
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ReportResultParamDataVector object that consists
     * of ReportResultParamData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ReportResultParamData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ReportResultParamDataVector()
     * @throws            SQLException
     */
    public static ReportResultParamDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ReportResultParamDataVector v = new ReportResultParamDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT REPORT_RESULT_PARAM_ID,REPORT_RESULT_ID,PARAM_NAME,PARAM_VALUE FROM RPT_REPORT_RESULT_PARAM WHERE  IN (");
        
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
            ReportResultParamData x=null;
            while (rs.next()) {
                // build the object
                x=ReportResultParamData.createValue();
                
                x.setReportResultParamId(rs.getInt(1));
                x.setReportResultId(rs.getInt(2));
                x.setParamName(rs.getString(3));
                x.setParamValue(rs.getString(4));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ReportResultParamDataVector object of all
     * ReportResultParamData objects in the database.
     * @param pCon An open database connection.
     * @return new ReportResultParamDataVector()
     * @throws            SQLException
     */
    public static ReportResultParamDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT REPORT_RESULT_PARAM_ID,REPORT_RESULT_ID,PARAM_NAME,PARAM_VALUE FROM RPT_REPORT_RESULT_PARAM";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ReportResultParamDataVector v = new ReportResultParamDataVector();
        ReportResultParamData x = null;
        while (rs.next()) {
            // build the object
            x = ReportResultParamData.createValue();
            
            x.setReportResultParamId(rs.getInt(1));
            x.setReportResultId(rs.getInt(2));
            x.setParamName(rs.getString(3));
            x.setParamValue(rs.getString(4));
            
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ReportResultParamData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT  FROM RPT_REPORT_RESULT_PARAM");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM RPT_REPORT_RESULT_PARAM");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM RPT_REPORT_RESULT_PARAM");
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
     * Inserts a ReportResultParamData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportResultParamData object to insert.
     * @return new ReportResultParamData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ReportResultParamData insert(Connection pCon, ReportResultParamData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
	    log.debug("SELECT RPT_REPORT_RESULT_PARAM_SEQ.NEXTVAL FROM DUAL");
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT RPT_REPORT_RESULT_PARAM_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setReportResultParamId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO RPT_REPORT_RESULT_PARAM (REPORT_RESULT_PARAM_ID,REPORT_RESULT_ID,PARAM_NAME,PARAM_VALUE) VALUES(?,?,?,?)";

        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setInt(1,pData.getReportResultParamId());
        pstmt.setInt(2,pData.getReportResultId());
        pstmt.setString(3,pData.getParamName());
        pstmt.setString(4,pData.getParamValue());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   REPORT_RESULT_PARAM_ID="+pData.getReportResultParamId());
            log.debug("SQL:   REPORT_RESULT_ID="+pData.getReportResultId());
            log.debug("SQL:   PARAM_NAME="+pData.getParamName());
            log.debug("SQL:   PARAM_VALUE="+pData.getParamValue());
	    log.debug("SQL: " + sql);
        }

        pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);
        return pData;
    }

    /**
     * Updates a ReportResultParamData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportResultParamData object to update. 
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ReportResultParamData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE RPT_REPORT_RESULT_PARAM SET REPORT_RESULT_PARAM_ID = ?,REPORT_RESULT_ID = ?,PARAM_NAME = ?,PARAM_VALUE = ? WHERE  = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        int i = 1;
        
        pstmt.setInt(i++,pData.getReportResultParamId());
        pstmt.setInt(i++,pData.getReportResultId());
        pstmt.setString(i++,pData.getParamName());
        pstmt.setString(i++,pData.getParamValue());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   REPORT_RESULT_PARAM_ID="+pData.getReportResultParamId());
            log.debug("SQL:   REPORT_RESULT_ID="+pData.getReportResultId());
            log.debug("SQL:   PARAM_NAME="+pData.getParamName());
            log.debug("SQL:   PARAM_VALUE="+pData.getParamValue());
	    log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ReportResultParamData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param p Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pReportResultParamId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM RPT_REPORT_RESULT_PARAM WHERE  = " + pReportResultParamId;

        if (log.isDebugEnabled()) {
	    log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ReportResultParamData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM RPT_REPORT_RESULT_PARAM");
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

