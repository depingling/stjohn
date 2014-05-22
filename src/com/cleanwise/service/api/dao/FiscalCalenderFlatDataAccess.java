
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        FiscalCalenderFlatDataAccess
 * Description:  This class is used to build access methods to the CLW_FISCAL_CALENDER_FLAT table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.FiscalCalenderFlatData;
import com.cleanwise.service.api.value.FiscalCalenderFlatDataVector;
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
 * <code>FiscalCalenderFlatDataAccess</code>
 */
public class FiscalCalenderFlatDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(FiscalCalenderFlatDataAccess.class.getName());

    /** <code>CLW_FISCAL_CALENDER_FLAT</code> table name */
	/* Primary key: FISCAL_CALENDER_FLAT_ID */
	
    public static final String CLW_FISCAL_CALENDER_FLAT = "CLW_FISCAL_CALENDER_FLAT";
    
    /** <code>FISCAL_CALENDER_FLAT_ID</code> FISCAL_CALENDER_FLAT_ID column of table CLW_FISCAL_CALENDER_FLAT */
    public static final String FISCAL_CALENDER_FLAT_ID = "FISCAL_CALENDER_FLAT_ID";
    /** <code>FISCAL_CALENDER_ID</code> FISCAL_CALENDER_ID column of table CLW_FISCAL_CALENDER_FLAT */
    public static final String FISCAL_CALENDER_ID = "FISCAL_CALENDER_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_FISCAL_CALENDER_FLAT */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>START_DATE</code> START_DATE column of table CLW_FISCAL_CALENDER_FLAT */
    public static final String START_DATE = "START_DATE";
    /** <code>END_DATE</code> END_DATE column of table CLW_FISCAL_CALENDER_FLAT */
    public static final String END_DATE = "END_DATE";
    /** <code>PERIOD</code> PERIOD column of table CLW_FISCAL_CALENDER_FLAT */
    public static final String PERIOD = "PERIOD";
    /** <code>FISCAL_YEAR</code> FISCAL_YEAR column of table CLW_FISCAL_CALENDER_FLAT */
    public static final String FISCAL_YEAR = "FISCAL_YEAR";

    /**
     * Constructor.
     */
    public FiscalCalenderFlatDataAccess()
    {
    }

    /**
     * Gets a FiscalCalenderFlatData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pFiscalCalenderFlatId The key requested.
     * @return new FiscalCalenderFlatData()
     * @throws            SQLException
     */
    public static FiscalCalenderFlatData select(Connection pCon, int pFiscalCalenderFlatId)
        throws SQLException, DataNotFoundException {
        FiscalCalenderFlatData x=null;
        String sql="SELECT FISCAL_CALENDER_FLAT_ID,FISCAL_CALENDER_ID,BUS_ENTITY_ID,START_DATE,END_DATE,PERIOD,FISCAL_YEAR FROM CLW_FISCAL_CALENDER_FLAT WHERE FISCAL_CALENDER_FLAT_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pFiscalCalenderFlatId=" + pFiscalCalenderFlatId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pFiscalCalenderFlatId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=FiscalCalenderFlatData.createValue();
            
            x.setFiscalCalenderFlatId(rs.getInt(1));
            x.setFiscalCalenderId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setStartDate(rs.getDate(4));
            x.setEndDate(rs.getDate(5));
            x.setPeriod(rs.getInt(6));
            x.setFiscalYear(rs.getInt(7));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("FISCAL_CALENDER_FLAT_ID :" + pFiscalCalenderFlatId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a FiscalCalenderFlatDataVector object that consists
     * of FiscalCalenderFlatData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new FiscalCalenderFlatDataVector()
     * @throws            SQLException
     */
    public static FiscalCalenderFlatDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a FiscalCalenderFlatData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_FISCAL_CALENDER_FLAT.FISCAL_CALENDER_FLAT_ID,CLW_FISCAL_CALENDER_FLAT.FISCAL_CALENDER_ID,CLW_FISCAL_CALENDER_FLAT.BUS_ENTITY_ID,CLW_FISCAL_CALENDER_FLAT.START_DATE,CLW_FISCAL_CALENDER_FLAT.END_DATE,CLW_FISCAL_CALENDER_FLAT.PERIOD,CLW_FISCAL_CALENDER_FLAT.FISCAL_YEAR";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated FiscalCalenderFlatData Object.
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
    *@returns a populated FiscalCalenderFlatData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         FiscalCalenderFlatData x = FiscalCalenderFlatData.createValue();
         
         x.setFiscalCalenderFlatId(rs.getInt(1+offset));
         x.setFiscalCalenderId(rs.getInt(2+offset));
         x.setBusEntityId(rs.getInt(3+offset));
         x.setStartDate(rs.getDate(4+offset));
         x.setEndDate(rs.getDate(5+offset));
         x.setPeriod(rs.getInt(6+offset));
         x.setFiscalYear(rs.getInt(7+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the FiscalCalenderFlatData Object represents.
    */
    public int getColumnCount(){
        return 7;
    }

    /**
     * Gets a FiscalCalenderFlatDataVector object that consists
     * of FiscalCalenderFlatData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new FiscalCalenderFlatDataVector()
     * @throws            SQLException
     */
    public static FiscalCalenderFlatDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT FISCAL_CALENDER_FLAT_ID,FISCAL_CALENDER_ID,BUS_ENTITY_ID,START_DATE,END_DATE,PERIOD,FISCAL_YEAR FROM CLW_FISCAL_CALENDER_FLAT");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_FISCAL_CALENDER_FLAT.FISCAL_CALENDER_FLAT_ID,CLW_FISCAL_CALENDER_FLAT.FISCAL_CALENDER_ID,CLW_FISCAL_CALENDER_FLAT.BUS_ENTITY_ID,CLW_FISCAL_CALENDER_FLAT.START_DATE,CLW_FISCAL_CALENDER_FLAT.END_DATE,CLW_FISCAL_CALENDER_FLAT.PERIOD,CLW_FISCAL_CALENDER_FLAT.FISCAL_YEAR FROM CLW_FISCAL_CALENDER_FLAT");
                where = pCriteria.getSqlClause("CLW_FISCAL_CALENDER_FLAT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_FISCAL_CALENDER_FLAT.equals(otherTable)){
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
        FiscalCalenderFlatDataVector v = new FiscalCalenderFlatDataVector();
        while (rs.next()) {
            FiscalCalenderFlatData x = FiscalCalenderFlatData.createValue();
            
            x.setFiscalCalenderFlatId(rs.getInt(1));
            x.setFiscalCalenderId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setStartDate(rs.getDate(4));
            x.setEndDate(rs.getDate(5));
            x.setPeriod(rs.getInt(6));
            x.setFiscalYear(rs.getInt(7));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a FiscalCalenderFlatDataVector object that consists
     * of FiscalCalenderFlatData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for FiscalCalenderFlatData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new FiscalCalenderFlatDataVector()
     * @throws            SQLException
     */
    public static FiscalCalenderFlatDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        FiscalCalenderFlatDataVector v = new FiscalCalenderFlatDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT FISCAL_CALENDER_FLAT_ID,FISCAL_CALENDER_ID,BUS_ENTITY_ID,START_DATE,END_DATE,PERIOD,FISCAL_YEAR FROM CLW_FISCAL_CALENDER_FLAT WHERE FISCAL_CALENDER_FLAT_ID IN (");

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
            FiscalCalenderFlatData x=null;
            while (rs.next()) {
                // build the object
                x=FiscalCalenderFlatData.createValue();
                
                x.setFiscalCalenderFlatId(rs.getInt(1));
                x.setFiscalCalenderId(rs.getInt(2));
                x.setBusEntityId(rs.getInt(3));
                x.setStartDate(rs.getDate(4));
                x.setEndDate(rs.getDate(5));
                x.setPeriod(rs.getInt(6));
                x.setFiscalYear(rs.getInt(7));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a FiscalCalenderFlatDataVector object of all
     * FiscalCalenderFlatData objects in the database.
     * @param pCon An open database connection.
     * @return new FiscalCalenderFlatDataVector()
     * @throws            SQLException
     */
    public static FiscalCalenderFlatDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT FISCAL_CALENDER_FLAT_ID,FISCAL_CALENDER_ID,BUS_ENTITY_ID,START_DATE,END_DATE,PERIOD,FISCAL_YEAR FROM CLW_FISCAL_CALENDER_FLAT";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        FiscalCalenderFlatDataVector v = new FiscalCalenderFlatDataVector();
        FiscalCalenderFlatData x = null;
        while (rs.next()) {
            // build the object
            x = FiscalCalenderFlatData.createValue();
            
            x.setFiscalCalenderFlatId(rs.getInt(1));
            x.setFiscalCalenderId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setStartDate(rs.getDate(4));
            x.setEndDate(rs.getDate(5));
            x.setPeriod(rs.getInt(6));
            x.setFiscalYear(rs.getInt(7));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * FiscalCalenderFlatData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT FISCAL_CALENDER_FLAT_ID FROM CLW_FISCAL_CALENDER_FLAT");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_FISCAL_CALENDER_FLAT");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_FISCAL_CALENDER_FLAT");
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
     * Inserts a FiscalCalenderFlatData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FiscalCalenderFlatData object to insert.
     * @return new FiscalCalenderFlatData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static FiscalCalenderFlatData insert(Connection pCon, FiscalCalenderFlatData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_FISCAL_CALENDER_FLAT_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_FISCAL_CALENDER_FLAT_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setFiscalCalenderFlatId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_FISCAL_CALENDER_FLAT (FISCAL_CALENDER_FLAT_ID,FISCAL_CALENDER_ID,BUS_ENTITY_ID,START_DATE,END_DATE,PERIOD,FISCAL_YEAR) VALUES(?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pstmt.setInt(1,pData.getFiscalCalenderFlatId());
        pstmt.setInt(2,pData.getFiscalCalenderId());
        pstmt.setInt(3,pData.getBusEntityId());
        pstmt.setDate(4,DBAccess.toSQLDate(pData.getStartDate()));
        pstmt.setDate(5,DBAccess.toSQLDate(pData.getEndDate()));
        pstmt.setInt(6,pData.getPeriod());
        pstmt.setInt(7,pData.getFiscalYear());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   FISCAL_CALENDER_FLAT_ID="+pData.getFiscalCalenderFlatId());
            log.debug("SQL:   FISCAL_CALENDER_ID="+pData.getFiscalCalenderId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   START_DATE="+pData.getStartDate());
            log.debug("SQL:   END_DATE="+pData.getEndDate());
            log.debug("SQL:   PERIOD="+pData.getPeriod());
            log.debug("SQL:   FISCAL_YEAR="+pData.getFiscalYear());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setFiscalCalenderFlatId(0);
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
     * Updates a FiscalCalenderFlatData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A FiscalCalenderFlatData object to update. 
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, FiscalCalenderFlatData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_FISCAL_CALENDER_FLAT SET FISCAL_CALENDER_ID = ?,BUS_ENTITY_ID = ?,START_DATE = ?,END_DATE = ?,PERIOD = ?,FISCAL_YEAR = ? WHERE FISCAL_CALENDER_FLAT_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        int i = 1;
        
        pstmt.setInt(i++,pData.getFiscalCalenderId());
        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getStartDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEndDate()));
        pstmt.setInt(i++,pData.getPeriod());
        pstmt.setInt(i++,pData.getFiscalYear());
        pstmt.setInt(i++,pData.getFiscalCalenderFlatId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   FISCAL_CALENDER_ID="+pData.getFiscalCalenderId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   START_DATE="+pData.getStartDate());
            log.debug("SQL:   END_DATE="+pData.getEndDate());
            log.debug("SQL:   PERIOD="+pData.getPeriod());
            log.debug("SQL:   FISCAL_YEAR="+pData.getFiscalYear());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a FiscalCalenderFlatData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pFiscalCalenderFlatId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pFiscalCalenderFlatId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_FISCAL_CALENDER_FLAT WHERE FISCAL_CALENDER_FLAT_ID = " + pFiscalCalenderFlatId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes FiscalCalenderFlatData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_FISCAL_CALENDER_FLAT");
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
     * Inserts a FiscalCalenderFlatData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FiscalCalenderFlatData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, FiscalCalenderFlatData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_FISCAL_CALENDER_FLAT (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "FISCAL_CALENDER_FLAT_ID,FISCAL_CALENDER_ID,BUS_ENTITY_ID,START_DATE,END_DATE,PERIOD,FISCAL_YEAR) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getFiscalCalenderFlatId());
        pstmt.setInt(2+4,pData.getFiscalCalenderId());
        pstmt.setInt(3+4,pData.getBusEntityId());
        pstmt.setDate(4+4,DBAccess.toSQLDate(pData.getStartDate()));
        pstmt.setDate(5+4,DBAccess.toSQLDate(pData.getEndDate()));
        pstmt.setInt(6+4,pData.getPeriod());
        pstmt.setInt(7+4,pData.getFiscalYear());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a FiscalCalenderFlatData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A FiscalCalenderFlatData object to insert.
     * @param pLogFl  Creates record in log table if true
     * @return new FiscalCalenderFlatData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static FiscalCalenderFlatData insert(Connection pCon, FiscalCalenderFlatData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a FiscalCalenderFlatData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A FiscalCalenderFlatData object to update. 
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, FiscalCalenderFlatData pData, boolean pLogFl)
        throws SQLException {
        FiscalCalenderFlatData oldData = null;
        if(pLogFl) {
          int id = pData.getFiscalCalenderFlatId();
          try {
          oldData = FiscalCalenderFlatDataAccess.select(pCon,id);
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
     * Deletes a FiscalCalenderFlatData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pFiscalCalenderFlatId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pFiscalCalenderFlatId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_FISCAL_CALENDER_FLAT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_FISCAL_CALENDER_FLAT d WHERE FISCAL_CALENDER_FLAT_ID = " + pFiscalCalenderFlatId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pFiscalCalenderFlatId);
        return n;
     }

    /**
     * Deletes FiscalCalenderFlatData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_FISCAL_CALENDER_FLAT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_FISCAL_CALENDER_FLAT d ");
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

