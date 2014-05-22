
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ReportResultAssocDataAccess
 * Description:  This class is used to build access methods to the RPT_REPORT_RESULT_ASSOC table.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ReportResultAssocData;
import com.cleanwise.service.api.value.ReportResultAssocDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.sql.*;
import java.util.*;

/**
 * <code>ReportResultAssocDataAccess</code>
 */
public class ReportResultAssocDataAccess
{
    private static Category log = Category.getInstance(ReportResultAssocDataAccess.class.getName());

    /** <code>RPT_REPORT_RESULT_ASSOC</code> table name */
    public static final String RPT_REPORT_RESULT_ASSOC = "RPT_REPORT_RESULT_ASSOC";
    
    /** <code>REPORT_RESULT_ASSOC_ID</code> REPORT_RESULT_ASSOC_ID column of table RPT_REPORT_RESULT_ASSOC */
    public static final String REPORT_RESULT_ASSOC_ID = "REPORT_RESULT_ASSOC_ID";
    /** <code>REPORT_RESULT_ID</code> REPORT_RESULT_ID column of table RPT_REPORT_RESULT_ASSOC */
    public static final String REPORT_RESULT_ID = "REPORT_RESULT_ID";
    /** <code>ASSOC_REF_ID</code> ASSOC_REF_ID column of table RPT_REPORT_RESULT_ASSOC */
    public static final String ASSOC_REF_ID = "ASSOC_REF_ID";
    /** <code>REPORT_RESULT_ASSOC_CD</code> REPORT_RESULT_ASSOC_CD column of table RPT_REPORT_RESULT_ASSOC */
    public static final String REPORT_RESULT_ASSOC_CD = "REPORT_RESULT_ASSOC_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table RPT_REPORT_RESULT_ASSOC */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table RPT_REPORT_RESULT_ASSOC */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table RPT_REPORT_RESULT_ASSOC */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table RPT_REPORT_RESULT_ASSOC */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public ReportResultAssocDataAccess() 
    {
    }

    /**
     * Gets a ReportResultAssocData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param p The key requested.
     * @return new ReportResultAssocData()
     * @throws            SQLException
     */
    public static ReportResultAssocData select(Connection pCon, int pReportResultAssocId)
        throws SQLException, DataNotFoundException {
        ReportResultAssocData x=null;
        String sql="SELECT REPORT_RESULT_ASSOC_ID,REPORT_RESULT_ID,ASSOC_REF_ID,REPORT_RESULT_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM RPT_REPORT_RESULT_ASSOC WHERE  = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pReportResultAssocId=" + pReportResultAssocId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pReportResultAssocId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ReportResultAssocData.createValue();
            
            x.setReportResultAssocId(rs.getInt(1));
            x.setReportResultId(rs.getInt(2));
            x.setAssocRefId(rs.getInt(3));
            x.setReportResultAssocCd(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            
        } else {
	    rs.close();
	    stmt.close();
	    throw new DataNotFoundException(" :" + pReportResultAssocId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ReportResultAssocDataVector object that consists
     * of ReportResultAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ReportResultAssocDataVector()
     * @throws            SQLException
     */
    public static ReportResultAssocDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
     * Gets a ReportResultAssocDataVector object that consists
     * of ReportResultAssocData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ReportResultAssocDataVector()
     * @throws            SQLException
     */
    public static ReportResultAssocDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT REPORT_RESULT_ASSOC_ID,REPORT_RESULT_ID,ASSOC_REF_ID,REPORT_RESULT_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM RPT_REPORT_RESULT_ASSOC");
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
        ReportResultAssocDataVector v = new ReportResultAssocDataVector();
        ReportResultAssocData x=null;
        while (rs.next()) {
            // build the object
            x = ReportResultAssocData.createValue();
            
            x.setReportResultAssocId(rs.getInt(1));
            x.setReportResultId(rs.getInt(2));
            x.setAssocRefId(rs.getInt(3));
            x.setReportResultAssocCd(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ReportResultAssocDataVector object that consists
     * of ReportResultAssocData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ReportResultAssocData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ReportResultAssocDataVector()
     * @throws            SQLException
     */
    public static ReportResultAssocDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ReportResultAssocDataVector v = new ReportResultAssocDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT REPORT_RESULT_ASSOC_ID,REPORT_RESULT_ID,ASSOC_REF_ID,REPORT_RESULT_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM RPT_REPORT_RESULT_ASSOC WHERE  IN (");
        
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
            ReportResultAssocData x=null;
            while (rs.next()) {
                // build the object
                x=ReportResultAssocData.createValue();
                
                x.setReportResultAssocId(rs.getInt(1));
                x.setReportResultId(rs.getInt(2));
                x.setAssocRefId(rs.getInt(3));
                x.setReportResultAssocCd(rs.getString(4));
                x.setAddDate(rs.getTimestamp(5));
                x.setAddBy(rs.getString(6));
                x.setModDate(rs.getTimestamp(7));
                x.setModBy(rs.getString(8));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ReportResultAssocDataVector object of all
     * ReportResultAssocData objects in the database.
     * @param pCon An open database connection.
     * @return new ReportResultAssocDataVector()
     * @throws            SQLException
     */
    public static ReportResultAssocDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT REPORT_RESULT_ASSOC_ID,REPORT_RESULT_ID,ASSOC_REF_ID,REPORT_RESULT_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM RPT_REPORT_RESULT_ASSOC";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ReportResultAssocDataVector v = new ReportResultAssocDataVector();
        ReportResultAssocData x = null;
        while (rs.next()) {
            // build the object
            x = ReportResultAssocData.createValue();
            
            x.setReportResultAssocId(rs.getInt(1));
            x.setReportResultId(rs.getInt(2));
            x.setAssocRefId(rs.getInt(3));
            x.setReportResultAssocCd(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ReportResultAssocData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT  FROM RPT_REPORT_RESULT_ASSOC");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM RPT_REPORT_RESULT_ASSOC");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM RPT_REPORT_RESULT_ASSOC");
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
     * Inserts a ReportResultAssocData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportResultAssocData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and 
     * "ModDate" fields will be set to the current date. 
     * @return new ReportResultAssocData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ReportResultAssocData insert(Connection pCon, ReportResultAssocData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
	    log.debug("SELECT RPT_REPORT_RESULT_ASSOC_SEQ.NEXTVAL FROM DUAL");
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT RPT_REPORT_RESULT_ASSOC_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setReportResultAssocId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO RPT_REPORT_RESULT_ASSOC (REPORT_RESULT_ASSOC_ID,REPORT_RESULT_ID,ASSOC_REF_ID,REPORT_RESULT_ASSOC_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?)";

        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pData.setAddDate(current);

        pData.setModDate(current);

        pstmt.setInt(1,pData.getReportResultAssocId());
        pstmt.setInt(2,pData.getReportResultId());
        pstmt.setInt(3,pData.getAssocRefId());
        pstmt.setString(4,pData.getReportResultAssocCd());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   REPORT_RESULT_ASSOC_ID="+pData.getReportResultAssocId());
            log.debug("SQL:   REPORT_RESULT_ID="+pData.getReportResultId());
            log.debug("SQL:   ASSOC_REF_ID="+pData.getAssocRefId());
            log.debug("SQL:   REPORT_RESULT_ASSOC_CD="+pData.getReportResultAssocCd());
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
     * Updates a ReportResultAssocData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportResultAssocData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ReportResultAssocData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE RPT_REPORT_RESULT_ASSOC SET REPORT_RESULT_ASSOC_ID = ?,REPORT_RESULT_ID = ?,ASSOC_REF_ID = ?,REPORT_RESULT_ASSOC_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE  = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getReportResultAssocId());
        pstmt.setInt(i++,pData.getReportResultId());
        pstmt.setInt(i++,pData.getAssocRefId());
        pstmt.setString(i++,pData.getReportResultAssocCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   REPORT_RESULT_ASSOC_ID="+pData.getReportResultAssocId());
            log.debug("SQL:   REPORT_RESULT_ID="+pData.getReportResultId());
            log.debug("SQL:   ASSOC_REF_ID="+pData.getAssocRefId());
            log.debug("SQL:   REPORT_RESULT_ASSOC_CD="+pData.getReportResultAssocCd());
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
     * Deletes a ReportResultAssocData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param p Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pReportResultAssocId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM RPT_REPORT_RESULT_ASSOC WHERE  = " + pReportResultAssocId;

        if (log.isDebugEnabled()) {
	    log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ReportResultAssocData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM RPT_REPORT_RESULT_ASSOC");
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

