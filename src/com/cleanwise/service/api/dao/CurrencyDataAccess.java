
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        CurrencyDataAccess
 * Description:  This class is used to build access methods to the CLW_CURRENCY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.CurrencyData;
import com.cleanwise.service.api.value.CurrencyDataVector;
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
 * <code>CurrencyDataAccess</code>
 */
public class CurrencyDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(CurrencyDataAccess.class.getName());

    /** <code>CLW_CURRENCY</code> table name */
	/* Primary key: CURRENCY_ID */
	
    public static final String CLW_CURRENCY = "CLW_CURRENCY";
    
    /** <code>CURRENCY_ID</code> CURRENCY_ID column of table CLW_CURRENCY */
    public static final String CURRENCY_ID = "CURRENCY_ID";
    /** <code>LOCALE</code> LOCALE column of table CLW_CURRENCY */
    public static final String LOCALE = "LOCALE";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_CURRENCY */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>LOCAL_CODE</code> LOCAL_CODE column of table CLW_CURRENCY */
    public static final String LOCAL_CODE = "LOCAL_CODE";
    /** <code>CURRENCY_POSITION_CD</code> CURRENCY_POSITION_CD column of table CLW_CURRENCY */
    public static final String CURRENCY_POSITION_CD = "CURRENCY_POSITION_CD";
    /** <code>DECIMALS</code> DECIMALS column of table CLW_CURRENCY */
    public static final String DECIMALS = "DECIMALS";
    /** <code>GLOBAL_CODE</code> GLOBAL_CODE column of table CLW_CURRENCY */
    public static final String GLOBAL_CODE = "GLOBAL_CODE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_CURRENCY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_CURRENCY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_CURRENCY */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_CURRENCY */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public CurrencyDataAccess()
    {
    }

    /**
     * Gets a CurrencyData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pCurrencyId The key requested.
     * @return new CurrencyData()
     * @throws            SQLException
     */
    public static CurrencyData select(Connection pCon, int pCurrencyId)
        throws SQLException, DataNotFoundException {
        CurrencyData x=null;
        String sql="SELECT CURRENCY_ID,LOCALE,SHORT_DESC,LOCAL_CODE,CURRENCY_POSITION_CD,DECIMALS,GLOBAL_CODE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CURRENCY WHERE CURRENCY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pCurrencyId=" + pCurrencyId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pCurrencyId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=CurrencyData.createValue();
            
            x.setCurrencyId(rs.getInt(1));
            x.setLocale(rs.getString(2));
            x.setShortDesc(rs.getString(3));
            x.setLocalCode(rs.getString(4));
            x.setCurrencyPositionCd(rs.getString(5));
            x.setDecimals(rs.getInt(6));
            x.setGlobalCode(rs.getString(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("CURRENCY_ID :" + pCurrencyId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a CurrencyDataVector object that consists
     * of CurrencyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new CurrencyDataVector()
     * @throws            SQLException
     */
    public static CurrencyDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a CurrencyData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_CURRENCY.CURRENCY_ID,CLW_CURRENCY.LOCALE,CLW_CURRENCY.SHORT_DESC,CLW_CURRENCY.LOCAL_CODE,CLW_CURRENCY.CURRENCY_POSITION_CD,CLW_CURRENCY.DECIMALS,CLW_CURRENCY.GLOBAL_CODE,CLW_CURRENCY.ADD_DATE,CLW_CURRENCY.ADD_BY,CLW_CURRENCY.MOD_DATE,CLW_CURRENCY.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated CurrencyData Object.
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
    *@returns a populated CurrencyData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         CurrencyData x = CurrencyData.createValue();
         
         x.setCurrencyId(rs.getInt(1+offset));
         x.setLocale(rs.getString(2+offset));
         x.setShortDesc(rs.getString(3+offset));
         x.setLocalCode(rs.getString(4+offset));
         x.setCurrencyPositionCd(rs.getString(5+offset));
         x.setDecimals(rs.getInt(6+offset));
         x.setGlobalCode(rs.getString(7+offset));
         x.setAddDate(rs.getTimestamp(8+offset));
         x.setAddBy(rs.getString(9+offset));
         x.setModDate(rs.getTimestamp(10+offset));
         x.setModBy(rs.getString(11+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the CurrencyData Object represents.
    */
    public int getColumnCount(){
        return 11;
    }

    /**
     * Gets a CurrencyDataVector object that consists
     * of CurrencyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new CurrencyDataVector()
     * @throws            SQLException
     */
    public static CurrencyDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT CURRENCY_ID,LOCALE,SHORT_DESC,LOCAL_CODE,CURRENCY_POSITION_CD,DECIMALS,GLOBAL_CODE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CURRENCY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_CURRENCY.CURRENCY_ID,CLW_CURRENCY.LOCALE,CLW_CURRENCY.SHORT_DESC,CLW_CURRENCY.LOCAL_CODE,CLW_CURRENCY.CURRENCY_POSITION_CD,CLW_CURRENCY.DECIMALS,CLW_CURRENCY.GLOBAL_CODE,CLW_CURRENCY.ADD_DATE,CLW_CURRENCY.ADD_BY,CLW_CURRENCY.MOD_DATE,CLW_CURRENCY.MOD_BY FROM CLW_CURRENCY");
                where = pCriteria.getSqlClause("CLW_CURRENCY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CURRENCY.equals(otherTable)){
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
        CurrencyDataVector v = new CurrencyDataVector();
        while (rs.next()) {
            CurrencyData x = CurrencyData.createValue();
            
            x.setCurrencyId(rs.getInt(1));
            x.setLocale(rs.getString(2));
            x.setShortDesc(rs.getString(3));
            x.setLocalCode(rs.getString(4));
            x.setCurrencyPositionCd(rs.getString(5));
            x.setDecimals(rs.getInt(6));
            x.setGlobalCode(rs.getString(7));
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
     * Gets a CurrencyDataVector object that consists
     * of CurrencyData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for CurrencyData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new CurrencyDataVector()
     * @throws            SQLException
     */
    public static CurrencyDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        CurrencyDataVector v = new CurrencyDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT CURRENCY_ID,LOCALE,SHORT_DESC,LOCAL_CODE,CURRENCY_POSITION_CD,DECIMALS,GLOBAL_CODE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CURRENCY WHERE CURRENCY_ID IN (");

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
            CurrencyData x=null;
            while (rs.next()) {
                // build the object
                x=CurrencyData.createValue();
                
                x.setCurrencyId(rs.getInt(1));
                x.setLocale(rs.getString(2));
                x.setShortDesc(rs.getString(3));
                x.setLocalCode(rs.getString(4));
                x.setCurrencyPositionCd(rs.getString(5));
                x.setDecimals(rs.getInt(6));
                x.setGlobalCode(rs.getString(7));
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
     * Gets a CurrencyDataVector object of all
     * CurrencyData objects in the database.
     * @param pCon An open database connection.
     * @return new CurrencyDataVector()
     * @throws            SQLException
     */
    public static CurrencyDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT CURRENCY_ID,LOCALE,SHORT_DESC,LOCAL_CODE,CURRENCY_POSITION_CD,DECIMALS,GLOBAL_CODE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_CURRENCY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        CurrencyDataVector v = new CurrencyDataVector();
        CurrencyData x = null;
        while (rs.next()) {
            // build the object
            x = CurrencyData.createValue();
            
            x.setCurrencyId(rs.getInt(1));
            x.setLocale(rs.getString(2));
            x.setShortDesc(rs.getString(3));
            x.setLocalCode(rs.getString(4));
            x.setCurrencyPositionCd(rs.getString(5));
            x.setDecimals(rs.getInt(6));
            x.setGlobalCode(rs.getString(7));
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
     * CurrencyData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT CURRENCY_ID FROM CLW_CURRENCY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CURRENCY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CURRENCY");
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
     * Inserts a CurrencyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CurrencyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new CurrencyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CurrencyData insert(Connection pCon, CurrencyData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_CURRENCY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_CURRENCY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setCurrencyId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_CURRENCY (CURRENCY_ID,LOCALE,SHORT_DESC,LOCAL_CODE,CURRENCY_POSITION_CD,DECIMALS,GLOBAL_CODE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getCurrencyId());
        pstmt.setString(2,pData.getLocale());
        pstmt.setString(3,pData.getShortDesc());
        pstmt.setString(4,pData.getLocalCode());
        pstmt.setString(5,pData.getCurrencyPositionCd());
        pstmt.setInt(6,pData.getDecimals());
        pstmt.setString(7,pData.getGlobalCode());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9,pData.getAddBy());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CURRENCY_ID="+pData.getCurrencyId());
            log.debug("SQL:   LOCALE="+pData.getLocale());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LOCAL_CODE="+pData.getLocalCode());
            log.debug("SQL:   CURRENCY_POSITION_CD="+pData.getCurrencyPositionCd());
            log.debug("SQL:   DECIMALS="+pData.getDecimals());
            log.debug("SQL:   GLOBAL_CODE="+pData.getGlobalCode());
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
        pData.setCurrencyId(0);
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
     * Updates a CurrencyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CurrencyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CurrencyData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_CURRENCY SET LOCALE = ?,SHORT_DESC = ?,LOCAL_CODE = ?,CURRENCY_POSITION_CD = ?,DECIMALS = ?,GLOBAL_CODE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE CURRENCY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getLocale());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getLocalCode());
        pstmt.setString(i++,pData.getCurrencyPositionCd());
        pstmt.setInt(i++,pData.getDecimals());
        pstmt.setString(i++,pData.getGlobalCode());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getCurrencyId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   LOCALE="+pData.getLocale());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LOCAL_CODE="+pData.getLocalCode());
            log.debug("SQL:   CURRENCY_POSITION_CD="+pData.getCurrencyPositionCd());
            log.debug("SQL:   DECIMALS="+pData.getDecimals());
            log.debug("SQL:   GLOBAL_CODE="+pData.getGlobalCode());
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
     * Deletes a CurrencyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCurrencyId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCurrencyId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_CURRENCY WHERE CURRENCY_ID = " + pCurrencyId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes CurrencyData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_CURRENCY");
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
     * Inserts a CurrencyData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CurrencyData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, CurrencyData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_CURRENCY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "CURRENCY_ID,LOCALE,SHORT_DESC,LOCAL_CODE,CURRENCY_POSITION_CD,DECIMALS,GLOBAL_CODE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getCurrencyId());
        pstmt.setString(2+4,pData.getLocale());
        pstmt.setString(3+4,pData.getShortDesc());
        pstmt.setString(4+4,pData.getLocalCode());
        pstmt.setString(5+4,pData.getCurrencyPositionCd());
        pstmt.setInt(6+4,pData.getDecimals());
        pstmt.setString(7+4,pData.getGlobalCode());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9+4,pData.getAddBy());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a CurrencyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CurrencyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new CurrencyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CurrencyData insert(Connection pCon, CurrencyData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a CurrencyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CurrencyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CurrencyData pData, boolean pLogFl)
        throws SQLException {
        CurrencyData oldData = null;
        if(pLogFl) {
          int id = pData.getCurrencyId();
          try {
          oldData = CurrencyDataAccess.select(pCon,id);
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
     * Deletes a CurrencyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCurrencyId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCurrencyId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_CURRENCY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CURRENCY d WHERE CURRENCY_ID = " + pCurrencyId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pCurrencyId);
        return n;
     }

    /**
     * Deletes CurrencyData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_CURRENCY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CURRENCY d ");
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

