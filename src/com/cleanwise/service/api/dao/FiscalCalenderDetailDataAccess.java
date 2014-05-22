
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        FiscalCalenderDetailDataAccess
 * Description:  This class is used to build access methods to the CLW_FISCAL_CALENDER_DETAIL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.FiscalCalenderDetailData;
import com.cleanwise.service.api.value.FiscalCalenderDetailDataVector;
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
 * <code>FiscalCalenderDetailDataAccess</code>
 */
public class FiscalCalenderDetailDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(FiscalCalenderDetailDataAccess.class.getName());

    /** <code>CLW_FISCAL_CALENDER_DETAIL</code> table name */
	/* Primary key: FISCAL_CALENDER_DETAIL_ID */
	
    public static final String CLW_FISCAL_CALENDER_DETAIL = "CLW_FISCAL_CALENDER_DETAIL";
    
    /** <code>FISCAL_CALENDER_DETAIL_ID</code> FISCAL_CALENDER_DETAIL_ID column of table CLW_FISCAL_CALENDER_DETAIL */
    public static final String FISCAL_CALENDER_DETAIL_ID = "FISCAL_CALENDER_DETAIL_ID";
    /** <code>FISCAL_CALENDER_ID</code> FISCAL_CALENDER_ID column of table CLW_FISCAL_CALENDER_DETAIL */
    public static final String FISCAL_CALENDER_ID = "FISCAL_CALENDER_ID";
    /** <code>PERIOD</code> PERIOD column of table CLW_FISCAL_CALENDER_DETAIL */
    public static final String PERIOD = "PERIOD";
    /** <code>MMDD</code> MMDD column of table CLW_FISCAL_CALENDER_DETAIL */
    public static final String MMDD = "MMDD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_FISCAL_CALENDER_DETAIL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_FISCAL_CALENDER_DETAIL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_FISCAL_CALENDER_DETAIL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_FISCAL_CALENDER_DETAIL */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public FiscalCalenderDetailDataAccess()
    {
    }

    /**
     * Gets a FiscalCalenderDetailData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pFiscalCalenderDetailId The key requested.
     * @return new FiscalCalenderDetailData()
     * @throws            SQLException
     */
    public static FiscalCalenderDetailData select(Connection pCon, int pFiscalCalenderDetailId)
        throws SQLException, DataNotFoundException {
        FiscalCalenderDetailData x=null;
        String sql="SELECT FISCAL_CALENDER_DETAIL_ID,FISCAL_CALENDER_ID,PERIOD,MMDD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_FISCAL_CALENDER_DETAIL WHERE FISCAL_CALENDER_DETAIL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pFiscalCalenderDetailId=" + pFiscalCalenderDetailId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pFiscalCalenderDetailId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=FiscalCalenderDetailData.createValue();
            
            x.setFiscalCalenderDetailId(rs.getInt(1));
            x.setFiscalCalenderId(rs.getInt(2));
            x.setPeriod(rs.getInt(3));
            x.setMmdd(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("FISCAL_CALENDER_DETAIL_ID :" + pFiscalCalenderDetailId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a FiscalCalenderDetailDataVector object that consists
     * of FiscalCalenderDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new FiscalCalenderDetailDataVector()
     * @throws            SQLException
     */
    public static FiscalCalenderDetailDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a FiscalCalenderDetailData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_FISCAL_CALENDER_DETAIL.FISCAL_CALENDER_DETAIL_ID,CLW_FISCAL_CALENDER_DETAIL.FISCAL_CALENDER_ID,CLW_FISCAL_CALENDER_DETAIL.PERIOD,CLW_FISCAL_CALENDER_DETAIL.MMDD,CLW_FISCAL_CALENDER_DETAIL.ADD_DATE,CLW_FISCAL_CALENDER_DETAIL.ADD_BY,CLW_FISCAL_CALENDER_DETAIL.MOD_DATE,CLW_FISCAL_CALENDER_DETAIL.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated FiscalCalenderDetailData Object.
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
    *@returns a populated FiscalCalenderDetailData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         FiscalCalenderDetailData x = FiscalCalenderDetailData.createValue();
         
         x.setFiscalCalenderDetailId(rs.getInt(1+offset));
         x.setFiscalCalenderId(rs.getInt(2+offset));
         x.setPeriod(rs.getInt(3+offset));
         x.setMmdd(rs.getString(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the FiscalCalenderDetailData Object represents.
    */
    public int getColumnCount(){
        return 8;
    }

    /**
     * Gets a FiscalCalenderDetailDataVector object that consists
     * of FiscalCalenderDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new FiscalCalenderDetailDataVector()
     * @throws            SQLException
     */
    public static FiscalCalenderDetailDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT FISCAL_CALENDER_DETAIL_ID,FISCAL_CALENDER_ID,PERIOD,MMDD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_FISCAL_CALENDER_DETAIL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_FISCAL_CALENDER_DETAIL.FISCAL_CALENDER_DETAIL_ID,CLW_FISCAL_CALENDER_DETAIL.FISCAL_CALENDER_ID,CLW_FISCAL_CALENDER_DETAIL.PERIOD,CLW_FISCAL_CALENDER_DETAIL.MMDD,CLW_FISCAL_CALENDER_DETAIL.ADD_DATE,CLW_FISCAL_CALENDER_DETAIL.ADD_BY,CLW_FISCAL_CALENDER_DETAIL.MOD_DATE,CLW_FISCAL_CALENDER_DETAIL.MOD_BY FROM CLW_FISCAL_CALENDER_DETAIL");
                where = pCriteria.getSqlClause("CLW_FISCAL_CALENDER_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_FISCAL_CALENDER_DETAIL.equals(otherTable)){
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
        FiscalCalenderDetailDataVector v = new FiscalCalenderDetailDataVector();
        while (rs.next()) {
            FiscalCalenderDetailData x = FiscalCalenderDetailData.createValue();
            
            x.setFiscalCalenderDetailId(rs.getInt(1));
            x.setFiscalCalenderId(rs.getInt(2));
            x.setPeriod(rs.getInt(3));
            x.setMmdd(rs.getString(4));
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
     * Gets a FiscalCalenderDetailDataVector object that consists
     * of FiscalCalenderDetailData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for FiscalCalenderDetailData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new FiscalCalenderDetailDataVector()
     * @throws            SQLException
     */
    public static FiscalCalenderDetailDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        FiscalCalenderDetailDataVector v = new FiscalCalenderDetailDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT FISCAL_CALENDER_DETAIL_ID,FISCAL_CALENDER_ID,PERIOD,MMDD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_FISCAL_CALENDER_DETAIL WHERE FISCAL_CALENDER_DETAIL_ID IN (");

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
            FiscalCalenderDetailData x=null;
            while (rs.next()) {
                // build the object
                x=FiscalCalenderDetailData.createValue();
                
                x.setFiscalCalenderDetailId(rs.getInt(1));
                x.setFiscalCalenderId(rs.getInt(2));
                x.setPeriod(rs.getInt(3));
                x.setMmdd(rs.getString(4));
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
     * Gets a FiscalCalenderDetailDataVector object of all
     * FiscalCalenderDetailData objects in the database.
     * @param pCon An open database connection.
     * @return new FiscalCalenderDetailDataVector()
     * @throws            SQLException
     */
    public static FiscalCalenderDetailDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT FISCAL_CALENDER_DETAIL_ID,FISCAL_CALENDER_ID,PERIOD,MMDD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_FISCAL_CALENDER_DETAIL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        FiscalCalenderDetailDataVector v = new FiscalCalenderDetailDataVector();
        FiscalCalenderDetailData x = null;
        while (rs.next()) {
            // build the object
            x = FiscalCalenderDetailData.createValue();
            
            x.setFiscalCalenderDetailId(rs.getInt(1));
            x.setFiscalCalenderId(rs.getInt(2));
            x.setPeriod(rs.getInt(3));
            x.setMmdd(rs.getString(4));
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
     * FiscalCalenderDetailData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT FISCAL_CALENDER_DETAIL_ID FROM CLW_FISCAL_CALENDER_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_FISCAL_CALENDER_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_FISCAL_CALENDER_DETAIL");
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
     * Inserts a FiscalCalenderDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FiscalCalenderDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new FiscalCalenderDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static FiscalCalenderDetailData insert(Connection pCon, FiscalCalenderDetailData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_FISCAL_CALENDER_DETAIL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_FISCAL_CALENDER_DETAIL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setFiscalCalenderDetailId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_FISCAL_CALENDER_DETAIL (FISCAL_CALENDER_DETAIL_ID,FISCAL_CALENDER_ID,PERIOD,MMDD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getFiscalCalenderDetailId());
        pstmt.setInt(2,pData.getFiscalCalenderId());
        pstmt.setInt(3,pData.getPeriod());
        pstmt.setString(4,pData.getMmdd());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   FISCAL_CALENDER_DETAIL_ID="+pData.getFiscalCalenderDetailId());
            log.debug("SQL:   FISCAL_CALENDER_ID="+pData.getFiscalCalenderId());
            log.debug("SQL:   PERIOD="+pData.getPeriod());
            log.debug("SQL:   MMDD="+pData.getMmdd());
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
        pData.setFiscalCalenderDetailId(0);
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
     * Updates a FiscalCalenderDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A FiscalCalenderDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, FiscalCalenderDetailData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_FISCAL_CALENDER_DETAIL SET FISCAL_CALENDER_ID = ?,PERIOD = ?,MMDD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE FISCAL_CALENDER_DETAIL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getFiscalCalenderId());
        pstmt.setInt(i++,pData.getPeriod());
        pstmt.setString(i++,pData.getMmdd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getFiscalCalenderDetailId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   FISCAL_CALENDER_ID="+pData.getFiscalCalenderId());
            log.debug("SQL:   PERIOD="+pData.getPeriod());
            log.debug("SQL:   MMDD="+pData.getMmdd());
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
     * Deletes a FiscalCalenderDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pFiscalCalenderDetailId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pFiscalCalenderDetailId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_FISCAL_CALENDER_DETAIL WHERE FISCAL_CALENDER_DETAIL_ID = " + pFiscalCalenderDetailId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes FiscalCalenderDetailData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_FISCAL_CALENDER_DETAIL");
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
     * Inserts a FiscalCalenderDetailData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FiscalCalenderDetailData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, FiscalCalenderDetailData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_FISCAL_CALENDER_DETAIL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "FISCAL_CALENDER_DETAIL_ID,FISCAL_CALENDER_ID,PERIOD,MMDD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getFiscalCalenderDetailId());
        pstmt.setInt(2+4,pData.getFiscalCalenderId());
        pstmt.setInt(3+4,pData.getPeriod());
        pstmt.setString(4+4,pData.getMmdd());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a FiscalCalenderDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FiscalCalenderDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new FiscalCalenderDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static FiscalCalenderDetailData insert(Connection pCon, FiscalCalenderDetailData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a FiscalCalenderDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A FiscalCalenderDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, FiscalCalenderDetailData pData, boolean pLogFl)
        throws SQLException {
        FiscalCalenderDetailData oldData = null;
        if(pLogFl) {
          int id = pData.getFiscalCalenderDetailId();
          try {
          oldData = FiscalCalenderDetailDataAccess.select(pCon,id);
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
     * Deletes a FiscalCalenderDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pFiscalCalenderDetailId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pFiscalCalenderDetailId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_FISCAL_CALENDER_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_FISCAL_CALENDER_DETAIL d WHERE FISCAL_CALENDER_DETAIL_ID = " + pFiscalCalenderDetailId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pFiscalCalenderDetailId);
        return n;
     }

    /**
     * Deletes FiscalCalenderDetailData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_FISCAL_CALENDER_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_FISCAL_CALENDER_DETAIL d ");
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

