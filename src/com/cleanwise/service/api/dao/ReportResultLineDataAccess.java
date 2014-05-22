
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ReportResultLineDataAccess
 * Description:  This class is used to build access methods to the RPT_REPORT_RESULT_LINE table.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ReportResultLineData;
import com.cleanwise.service.api.value.ReportResultLineDataVector;
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
 * <code>ReportResultLineDataAccess</code>
 */
public class ReportResultLineDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ReportResultLineDataAccess.class.getName());

    /** <code>RPT_REPORT_RESULT_LINE</code> table name */
    public static final String RPT_REPORT_RESULT_LINE = "RPT_REPORT_RESULT_LINE";
    
    /** <code>REPORT_RESULT_ID</code> REPORT_RESULT_ID column of table RPT_REPORT_RESULT_LINE */
    public static final String REPORT_RESULT_ID = "REPORT_RESULT_ID";
    /** <code>REPORT_RESULT_LINE_ID</code> REPORT_RESULT_LINE_ID column of table RPT_REPORT_RESULT_LINE */
    public static final String REPORT_RESULT_LINE_ID = "REPORT_RESULT_LINE_ID";
    /** <code>LINE_VALUE</code> LINE_VALUE column of table RPT_REPORT_RESULT_LINE */
    public static final String LINE_VALUE = "LINE_VALUE";
    /** <code>LINE_VALUE1</code> LINE_VALUE1 column of table RPT_REPORT_RESULT_LINE */
    public static final String LINE_VALUE1 = "LINE_VALUE1";
    /** <code>LINE_VALUE_BLOB</code> LINE_VALUE_BLOB column of table RPT_REPORT_RESULT_LINE */
    public static final String LINE_VALUE_BLOB = "LINE_VALUE_BLOB";
    /** <code>PAGE_NAME</code> PAGE_NAME column of table RPT_REPORT_RESULT_LINE */
    public static final String PAGE_NAME = "PAGE_NAME";
    /** <code>REPORT_RESULT_LINE_CD</code> REPORT_RESULT_LINE_CD column of table RPT_REPORT_RESULT_LINE */
    public static final String REPORT_RESULT_LINE_CD = "REPORT_RESULT_LINE_CD";

    /**
     * Constructor.
     */
    public ReportResultLineDataAccess() 
    {
    }

    /**
     * Gets a ReportResultLineData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param p The key requested.
     * @return new ReportResultLineData()
     * @throws            SQLException
     */
    public static ReportResultLineData select(Connection pCon, int pReportResultLineId)
        throws SQLException, DataNotFoundException {
        ReportResultLineData x=null;
        String sql="SELECT REPORT_RESULT_ID,REPORT_RESULT_LINE_ID,LINE_VALUE,LINE_VALUE1,LINE_VALUE_BLOB,PAGE_NAME,REPORT_RESULT_LINE_CD FROM RPT_REPORT_RESULT_LINE WHERE  = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pReportResultLineId=" + pReportResultLineId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pReportResultLineId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ReportResultLineData.createValue();
            
            x.setReportResultId(rs.getInt(1));
            x.setReportResultLineId(rs.getInt(2));
            x.setLineValue(rs.getString(3));
            x.setLineValue1(rs.getString(4));
            x.setLineValueBlob(rs.getBytes(5));
            x.setPageName(rs.getString(6));
            x.setReportResultLineCd(rs.getString(7));
            
        } else {
	    rs.close();
	    stmt.close();
	    throw new DataNotFoundException(" :" + pReportResultLineId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ReportResultLineDataVector object that consists
     * of ReportResultLineData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ReportResultLineDataVector()
     * @throws            SQLException
     */
    public static ReportResultLineDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
     * Gets a ReportResultLineDataVector object that consists
     * of ReportResultLineData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ReportResultLineDataVector()
     * @throws            SQLException
     */
    public static ReportResultLineDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT REPORT_RESULT_ID,REPORT_RESULT_LINE_ID,LINE_VALUE,LINE_VALUE1,LINE_VALUE_BLOB,PAGE_NAME,REPORT_RESULT_LINE_CD FROM RPT_REPORT_RESULT_LINE");
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
        ReportResultLineDataVector v = new ReportResultLineDataVector();
        ReportResultLineData x=null;
        while (rs.next()) {
            // build the object
            x = ReportResultLineData.createValue();
            
            x.setReportResultId(rs.getInt(1));
            x.setReportResultLineId(rs.getInt(2));
            x.setLineValue(rs.getString(3));
            x.setLineValue1(rs.getString(4));
            x.setLineValueBlob(rs.getBytes(5));
            x.setPageName(rs.getString(6));
            x.setReportResultLineCd(rs.getString(7));
            
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ReportResultLineDataVector object that consists
     * of ReportResultLineData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ReportResultLineData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ReportResultLineDataVector()
     * @throws            SQLException
     */
    public static ReportResultLineDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ReportResultLineDataVector v = new ReportResultLineDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT REPORT_RESULT_ID,REPORT_RESULT_LINE_ID,LINE_VALUE,LINE_VALUE1,LINE_VALUE_BLOB,PAGE_NAME,REPORT_RESULT_LINE_CD FROM RPT_REPORT_RESULT_LINE WHERE  IN (");
        
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
            ReportResultLineData x=null;
            while (rs.next()) {
                // build the object
                x=ReportResultLineData.createValue();
                
                x.setReportResultId(rs.getInt(1));
                x.setReportResultLineId(rs.getInt(2));
                x.setLineValue(rs.getString(3));
                x.setLineValue1(rs.getString(4));
                x.setLineValueBlob(rs.getBytes(5));
                x.setPageName(rs.getString(6));
                x.setReportResultLineCd(rs.getString(7));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ReportResultLineDataVector object of all
     * ReportResultLineData objects in the database.
     * @param pCon An open database connection.
     * @return new ReportResultLineDataVector()
     * @throws            SQLException
     */
    public static ReportResultLineDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT REPORT_RESULT_ID,REPORT_RESULT_LINE_ID,LINE_VALUE,LINE_VALUE1,LINE_VALUE_BLOB,PAGE_NAME,REPORT_RESULT_LINE_CD FROM RPT_REPORT_RESULT_LINE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ReportResultLineDataVector v = new ReportResultLineDataVector();
        ReportResultLineData x = null;
        while (rs.next()) {
            // build the object
            x = ReportResultLineData.createValue();
            
            x.setReportResultId(rs.getInt(1));
            x.setReportResultLineId(rs.getInt(2));
            x.setLineValue(rs.getString(3));
            x.setLineValue1(rs.getString(4));
            x.setLineValueBlob(rs.getBytes(5));
            x.setPageName(rs.getString(6));
            x.setReportResultLineCd(rs.getString(7));
            
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ReportResultLineData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT  FROM RPT_REPORT_RESULT_LINE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM RPT_REPORT_RESULT_LINE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM RPT_REPORT_RESULT_LINE");
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
     * Inserts a ReportResultLineData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportResultLineData object to insert.
     * @return new ReportResultLineData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ReportResultLineData insert(Connection pCon, ReportResultLineData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
	    log.debug("SELECT RPT_REPORT_RESULT_LINE_SEQ.NEXTVAL FROM DUAL");
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT RPT_REPORT_RESULT_LINE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setReportResultLineId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO RPT_REPORT_RESULT_LINE (REPORT_RESULT_ID,REPORT_RESULT_LINE_ID,LINE_VALUE,LINE_VALUE1,LINE_VALUE_BLOB,PAGE_NAME,REPORT_RESULT_LINE_CD) VALUES(?,?,?,?,?,?,?)";

        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setInt(1,pData.getReportResultId());
        pstmt.setInt(2,pData.getReportResultLineId());
        pstmt.setString(3,pData.getLineValue());
        pstmt.setString(4,pData.getLineValue1());
        //pstmt.setBlob(5,pData.getLineValueBlob());
        if (ORACLE.equals(getDatabaseName(pCon))) {
        	pstmt.setBlob(5,toBlob(pCon,pData.getLineValueBlob()));
        } else { //Postgres (Enterprise DB)
            try {
            	/***
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getLineValueBlob());
                pstmt.setBinaryStream(5, is, is.available());
                ***/
            	byte b[] = pData.getLineValueBlob();
                log.info(".insert getLineValueBlob() = " + b);
                pstmt.setBytes(5,b);
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setString(6,pData.getPageName());
        pstmt.setString(7,pData.getReportResultLineCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   REPORT_RESULT_ID="+pData.getReportResultId());
            log.debug("SQL:   REPORT_RESULT_LINE_ID="+pData.getReportResultLineId());
            log.debug("SQL:   LINE_VALUE="+pData.getLineValue());
            log.debug("SQL:   LINE_VALUE1="+pData.getLineValue1());
            log.debug("SQL:   LINE_VALUE_BLOB="+pData.getLineValueBlob());
            log.debug("SQL:   PAGE_NAME="+pData.getPageName());
            log.debug("SQL:   REPORT_RESULT_LINE_CD="+pData.getReportResultLineCd());
	    log.debug("SQL: " + sql);
        }

        pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);
        return pData;
    }

    /**
     * Updates a ReportResultLineData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ReportResultLineData object to update. 
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ReportResultLineData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE RPT_REPORT_RESULT_LINE SET REPORT_RESULT_ID = ?,REPORT_RESULT_LINE_ID = ?,LINE_VALUE = ?,LINE_VALUE1 = ?,LINE_VALUE_BLOB = ?,PAGE_NAME = ?,REPORT_RESULT_LINE_CD = ? WHERE  = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        int i = 1;
        
        pstmt.setInt(i++,pData.getReportResultId());
        pstmt.setInt(i++,pData.getReportResultLineId());
        pstmt.setString(i++,pData.getLineValue());
        pstmt.setString(i++,pData.getLineValue1());
        //pstmt.setBlob(i++,pData.getLineValueBlob());
        if (ORACLE.equals(getDatabaseName(pCon))) {
            pstmt.setBlob(i++, toBlob(pCon,pData.getLineValueBlob()));
        } else {
            try {
            	/***
                ByteArrayInputStream is = new ByteArrayInputStream(pData.getBlobValue());
                pstmt.setBinaryStream(i++, is, is.available());
                ***/
            	byte b[] = pData.getLineValueBlob();
                log.info(".update LineValueBlob = " + b);
                pstmt.setBytes(i++,b);
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
        pstmt.setString(i++,pData.getPageName());
        pstmt.setString(i++,pData.getReportResultLineCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   REPORT_RESULT_ID="+pData.getReportResultId());
            log.debug("SQL:   REPORT_RESULT_LINE_ID="+pData.getReportResultLineId());
            log.debug("SQL:   LINE_VALUE="+pData.getLineValue());
            log.debug("SQL:   LINE_VALUE1="+pData.getLineValue1());
            log.debug("SQL:   LINE_VALUE_BLOB="+pData.getLineValueBlob());
            log.debug("SQL:   PAGE_NAME="+pData.getPageName());
            log.debug("SQL:   REPORT_RESULT_LINE_CD="+pData.getReportResultLineCd());
	    log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ReportResultLineData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param p Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pReportResultLineId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM RPT_REPORT_RESULT_LINE WHERE  = " + pReportResultLineId;

        if (log.isDebugEnabled()) {
	    log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ReportResultLineData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM RPT_REPORT_RESULT_LINE");
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
     *@Returns a count of the number of columns the ReportResultLineData Object represents.
     */
     public int getColumnCount(){
         return 7;
     }
     
     /**
      *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ReportResultLineData Object
      *using the parseResultSet method.
      */
      public String getSelectColumns(){
          return "RPT_REPORT_RESULT_LINE.REPORT_RESULT_ID,RPT_REPORT_RESULT_LINE.REPORT_RESULT_LINE_ID,RPT_REPORT_RESULT_LINE.LINE_VALUE,RPT_REPORT_RESULT_LINE.LINE_VALUE1,RPT_REPORT_RESULT_LINE.LINE_VALUE_BLOB,RPT_REPORT_RESULT_LINE.PAGE_NAME,RPT_REPORT_RESULT_LINE.REPORT_RESULT_LINE_CD";
      }
      
      /**
       *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
       *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
       *The result set is not incremented, so calls to this method leave the resultset object unchanged.
       *@param ResultSet an open result set.
       *@returns a populated EventPropertyData Object.
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
        *@returns a populated EventPropertyData Object.
        *@throws SQLException
        */
        public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
             // build the object
        	 ReportResultLineData x = ReportResultLineData.createValue();
             
             x.setReportResultId(rs.getInt(1));
             x.setReportResultLineId(rs.getInt(2));
             x.setLineValue(rs.getString(3));
             x.setLineValue1(rs.getString(4));
             x.setLineValueBlob(rs.getBytes(5));
             x.setPageName(rs.getString(6));
             x.setReportResultLineCd(rs.getString(7));
             return x;
        }

}

