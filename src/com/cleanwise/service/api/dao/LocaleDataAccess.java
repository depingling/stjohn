
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        LocaleDataAccess
 * Description:  This class is used to build access methods to the CLW_LOCALE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.LocaleData;
import com.cleanwise.service.api.value.LocaleDataVector;
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
 * <code>LocaleDataAccess</code>
 */
public class LocaleDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(LocaleDataAccess.class.getName());

    /** <code>CLW_LOCALE</code> table name */
	/* Primary key: LOCALE_ID */
	
    public static final String CLW_LOCALE = "CLW_LOCALE";
    
    /** <code>LOCALE_ID</code> LOCALE_ID column of table CLW_LOCALE */
    public static final String LOCALE_ID = "LOCALE_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_LOCALE */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>COUNTRY_CD</code> COUNTRY_CD column of table CLW_LOCALE */
    public static final String COUNTRY_CD = "COUNTRY_CD";
    /** <code>LANGUAGE_CD</code> LANGUAGE_CD column of table CLW_LOCALE */
    public static final String LANGUAGE_CD = "LANGUAGE_CD";
    /** <code>CURRENCY_CD</code> CURRENCY_CD column of table CLW_LOCALE */
    public static final String CURRENCY_CD = "CURRENCY_CD";
    /** <code>DATE_FORMAT_CD</code> DATE_FORMAT_CD column of table CLW_LOCALE */
    public static final String DATE_FORMAT_CD = "DATE_FORMAT_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_LOCALE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_LOCALE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_LOCALE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_LOCALE */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public LocaleDataAccess()
    {
    }

    /**
     * Gets a LocaleData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pLocaleId The key requested.
     * @return new LocaleData()
     * @throws            SQLException
     */
    public static LocaleData select(Connection pCon, int pLocaleId)
        throws SQLException, DataNotFoundException {
        LocaleData x=null;
        String sql="SELECT LOCALE_ID,SHORT_DESC,COUNTRY_CD,LANGUAGE_CD,CURRENCY_CD,DATE_FORMAT_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_LOCALE WHERE LOCALE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pLocaleId=" + pLocaleId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pLocaleId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=LocaleData.createValue();
            
            x.setLocaleId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setCountryCd(rs.getString(3));
            x.setLanguageCd(rs.getString(4));
            x.setCurrencyCd(rs.getString(5));
            x.setDateFormatCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("LOCALE_ID :" + pLocaleId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a LocaleDataVector object that consists
     * of LocaleData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new LocaleDataVector()
     * @throws            SQLException
     */
    public static LocaleDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a LocaleData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_LOCALE.LOCALE_ID,CLW_LOCALE.SHORT_DESC,CLW_LOCALE.COUNTRY_CD,CLW_LOCALE.LANGUAGE_CD,CLW_LOCALE.CURRENCY_CD,CLW_LOCALE.DATE_FORMAT_CD,CLW_LOCALE.ADD_DATE,CLW_LOCALE.ADD_BY,CLW_LOCALE.MOD_DATE,CLW_LOCALE.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated LocaleData Object.
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
    *@returns a populated LocaleData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         LocaleData x = LocaleData.createValue();
         
         x.setLocaleId(rs.getInt(1+offset));
         x.setShortDesc(rs.getString(2+offset));
         x.setCountryCd(rs.getString(3+offset));
         x.setLanguageCd(rs.getString(4+offset));
         x.setCurrencyCd(rs.getString(5+offset));
         x.setDateFormatCd(rs.getString(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the LocaleData Object represents.
    */
    public int getColumnCount(){
        return 10;
    }

    /**
     * Gets a LocaleDataVector object that consists
     * of LocaleData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new LocaleDataVector()
     * @throws            SQLException
     */
    public static LocaleDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT LOCALE_ID,SHORT_DESC,COUNTRY_CD,LANGUAGE_CD,CURRENCY_CD,DATE_FORMAT_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_LOCALE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_LOCALE.LOCALE_ID,CLW_LOCALE.SHORT_DESC,CLW_LOCALE.COUNTRY_CD,CLW_LOCALE.LANGUAGE_CD,CLW_LOCALE.CURRENCY_CD,CLW_LOCALE.DATE_FORMAT_CD,CLW_LOCALE.ADD_DATE,CLW_LOCALE.ADD_BY,CLW_LOCALE.MOD_DATE,CLW_LOCALE.MOD_BY FROM CLW_LOCALE");
                where = pCriteria.getSqlClause("CLW_LOCALE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_LOCALE.equals(otherTable)){
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
        LocaleDataVector v = new LocaleDataVector();
        while (rs.next()) {
            LocaleData x = LocaleData.createValue();
            
            x.setLocaleId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setCountryCd(rs.getString(3));
            x.setLanguageCd(rs.getString(4));
            x.setCurrencyCd(rs.getString(5));
            x.setDateFormatCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a LocaleDataVector object that consists
     * of LocaleData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for LocaleData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new LocaleDataVector()
     * @throws            SQLException
     */
    public static LocaleDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        LocaleDataVector v = new LocaleDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT LOCALE_ID,SHORT_DESC,COUNTRY_CD,LANGUAGE_CD,CURRENCY_CD,DATE_FORMAT_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_LOCALE WHERE LOCALE_ID IN (");

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
            LocaleData x=null;
            while (rs.next()) {
                // build the object
                x=LocaleData.createValue();
                
                x.setLocaleId(rs.getInt(1));
                x.setShortDesc(rs.getString(2));
                x.setCountryCd(rs.getString(3));
                x.setLanguageCd(rs.getString(4));
                x.setCurrencyCd(rs.getString(5));
                x.setDateFormatCd(rs.getString(6));
                x.setAddDate(rs.getTimestamp(7));
                x.setAddBy(rs.getString(8));
                x.setModDate(rs.getTimestamp(9));
                x.setModBy(rs.getString(10));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a LocaleDataVector object of all
     * LocaleData objects in the database.
     * @param pCon An open database connection.
     * @return new LocaleDataVector()
     * @throws            SQLException
     */
    public static LocaleDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT LOCALE_ID,SHORT_DESC,COUNTRY_CD,LANGUAGE_CD,CURRENCY_CD,DATE_FORMAT_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_LOCALE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        LocaleDataVector v = new LocaleDataVector();
        LocaleData x = null;
        while (rs.next()) {
            // build the object
            x = LocaleData.createValue();
            
            x.setLocaleId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setCountryCd(rs.getString(3));
            x.setLanguageCd(rs.getString(4));
            x.setCurrencyCd(rs.getString(5));
            x.setDateFormatCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * LocaleData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT LOCALE_ID FROM CLW_LOCALE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_LOCALE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_LOCALE");
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
     * Inserts a LocaleData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A LocaleData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new LocaleData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static LocaleData insert(Connection pCon, LocaleData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_LOCALE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_LOCALE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setLocaleId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_LOCALE (LOCALE_ID,SHORT_DESC,COUNTRY_CD,LANGUAGE_CD,CURRENCY_CD,DATE_FORMAT_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getLocaleId());
        pstmt.setString(2,pData.getShortDesc());
        pstmt.setString(3,pData.getCountryCd());
        pstmt.setString(4,pData.getLanguageCd());
        pstmt.setString(5,pData.getCurrencyCd());
        pstmt.setString(6,pData.getDateFormatCd());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   LOCALE_ID="+pData.getLocaleId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   COUNTRY_CD="+pData.getCountryCd());
            log.debug("SQL:   LANGUAGE_CD="+pData.getLanguageCd());
            log.debug("SQL:   CURRENCY_CD="+pData.getCurrencyCd());
            log.debug("SQL:   DATE_FORMAT_CD="+pData.getDateFormatCd());
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
        pData.setLocaleId(0);
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
     * Updates a LocaleData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A LocaleData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, LocaleData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_LOCALE SET SHORT_DESC = ?,COUNTRY_CD = ?,LANGUAGE_CD = ?,CURRENCY_CD = ?,DATE_FORMAT_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE LOCALE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getCountryCd());
        pstmt.setString(i++,pData.getLanguageCd());
        pstmt.setString(i++,pData.getCurrencyCd());
        pstmt.setString(i++,pData.getDateFormatCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getLocaleId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   COUNTRY_CD="+pData.getCountryCd());
            log.debug("SQL:   LANGUAGE_CD="+pData.getLanguageCd());
            log.debug("SQL:   CURRENCY_CD="+pData.getCurrencyCd());
            log.debug("SQL:   DATE_FORMAT_CD="+pData.getDateFormatCd());
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
     * Deletes a LocaleData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pLocaleId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pLocaleId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_LOCALE WHERE LOCALE_ID = " + pLocaleId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes LocaleData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_LOCALE");
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
     * Inserts a LocaleData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A LocaleData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, LocaleData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_LOCALE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "LOCALE_ID,SHORT_DESC,COUNTRY_CD,LANGUAGE_CD,CURRENCY_CD,DATE_FORMAT_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getLocaleId());
        pstmt.setString(2+4,pData.getShortDesc());
        pstmt.setString(3+4,pData.getCountryCd());
        pstmt.setString(4+4,pData.getLanguageCd());
        pstmt.setString(5+4,pData.getCurrencyCd());
        pstmt.setString(6+4,pData.getDateFormatCd());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a LocaleData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A LocaleData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new LocaleData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static LocaleData insert(Connection pCon, LocaleData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a LocaleData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A LocaleData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, LocaleData pData, boolean pLogFl)
        throws SQLException {
        LocaleData oldData = null;
        if(pLogFl) {
          int id = pData.getLocaleId();
          try {
          oldData = LocaleDataAccess.select(pCon,id);
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
     * Deletes a LocaleData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pLocaleId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pLocaleId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_LOCALE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_LOCALE d WHERE LOCALE_ID = " + pLocaleId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pLocaleId);
        return n;
     }

    /**
     * Deletes LocaleData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_LOCALE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_LOCALE d ");
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

