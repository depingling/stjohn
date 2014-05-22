
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        CountryDataAccess
 * Description:  This class is used to build access methods to the CLW_COUNTRY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.CountryData;
import com.cleanwise.service.api.value.CountryDataVector;
import com.cleanwise.service.api.framework.DataAccessImpl;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.cachecos.CachecosManager;
import com.cleanwise.service.api.cachecos.Cachecos;
import org.apache.log4j.Category;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.*;

/**
 * <code>CountryDataAccess</code>
 */
public class CountryDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(CountryDataAccess.class.getName());

    /** <code>CLW_COUNTRY</code> table name */
	/* Primary key: COUNTRY_ID */
	
    public static final String CLW_COUNTRY = "CLW_COUNTRY";
    
    /** <code>COUNTRY_ID</code> COUNTRY_ID column of table CLW_COUNTRY */
    public static final String COUNTRY_ID = "COUNTRY_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_COUNTRY */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>UI_NAME</code> UI_NAME column of table CLW_COUNTRY */
    public static final String UI_NAME = "UI_NAME";
    /** <code>COUNTRY_CODE</code> COUNTRY_CODE column of table CLW_COUNTRY */
    public static final String COUNTRY_CODE = "COUNTRY_CODE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_COUNTRY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_COUNTRY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_COUNTRY */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_COUNTRY */
    public static final String MOD_BY = "MOD_BY";
    /** <code>LOCALE_CD</code> LOCALE_CD column of table CLW_COUNTRY */
    public static final String LOCALE_CD = "LOCALE_CD";
    /** <code>INPUT_DATE_FORMAT</code> INPUT_DATE_FORMAT column of table CLW_COUNTRY */
    public static final String INPUT_DATE_FORMAT = "INPUT_DATE_FORMAT";
    /** <code>INPUT_TIME_FORMAT</code> INPUT_TIME_FORMAT column of table CLW_COUNTRY */
    public static final String INPUT_TIME_FORMAT = "INPUT_TIME_FORMAT";
    /** <code>INPUT_DAY_MONTH_FORMAT</code> INPUT_DAY_MONTH_FORMAT column of table CLW_COUNTRY */
    public static final String INPUT_DAY_MONTH_FORMAT = "INPUT_DAY_MONTH_FORMAT";
    /** <code>ADDRESS_FORMAT</code> ADDRESS_FORMAT column of table CLW_COUNTRY */
    public static final String ADDRESS_FORMAT = "ADDRESS_FORMAT";

    /**
     * Constructor.
     */
    public CountryDataAccess()
    {
    }

    /**
     * Gets a CountryData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pCountryId The key requested.
     * @return new CountryData()
     * @throws            SQLException
     */
    public static CountryData select(Connection pCon, int pCountryId)
        throws SQLException, DataNotFoundException {
        CountryData x=null;
        String sql="SELECT COUNTRY_ID,SHORT_DESC,UI_NAME,COUNTRY_CODE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOCALE_CD,INPUT_DATE_FORMAT,INPUT_TIME_FORMAT,INPUT_DAY_MONTH_FORMAT,ADDRESS_FORMAT FROM CLW_COUNTRY WHERE COUNTRY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pCountryId=" + pCountryId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pCountryId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=CountryData.createValue();
            
            x.setCountryId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setUiName(rs.getString(3));
            x.setCountryCode(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setLocaleCd(rs.getString(9));
            x.setInputDateFormat(rs.getString(10));
            x.setInputTimeFormat(rs.getString(11));
            x.setInputDayMonthFormat(rs.getString(12));
            x.setAddressFormat(rs.getString(13));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("COUNTRY_ID :" + pCountryId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a CountryDataVector object that consists
     * of CountryData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new CountryDataVector()
     * @throws            SQLException
     */
    public static CountryDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a CountryData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_COUNTRY.COUNTRY_ID,CLW_COUNTRY.SHORT_DESC,CLW_COUNTRY.UI_NAME,CLW_COUNTRY.COUNTRY_CODE,CLW_COUNTRY.ADD_DATE,CLW_COUNTRY.ADD_BY,CLW_COUNTRY.MOD_DATE,CLW_COUNTRY.MOD_BY,CLW_COUNTRY.LOCALE_CD,CLW_COUNTRY.INPUT_DATE_FORMAT,CLW_COUNTRY.INPUT_TIME_FORMAT,CLW_COUNTRY.INPUT_DAY_MONTH_FORMAT,CLW_COUNTRY.ADDRESS_FORMAT";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated CountryData Object.
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
    *@returns a populated CountryData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         CountryData x = CountryData.createValue();
         
         x.setCountryId(rs.getInt(1+offset));
         x.setShortDesc(rs.getString(2+offset));
         x.setUiName(rs.getString(3+offset));
         x.setCountryCode(rs.getString(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         x.setLocaleCd(rs.getString(9+offset));
         x.setInputDateFormat(rs.getString(10+offset));
         x.setInputTimeFormat(rs.getString(11+offset));
         x.setInputDayMonthFormat(rs.getString(12+offset));
         x.setAddressFormat(rs.getString(13+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the CountryData Object represents.
    */
    public int getColumnCount(){
        return 13;
    }

    /**
     * Gets a CountryDataVector object that consists
     * of CountryData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new CountryDataVector()
     * @throws            SQLException
     */
    public static CountryDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT COUNTRY_ID,SHORT_DESC,UI_NAME,COUNTRY_CODE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOCALE_CD,INPUT_DATE_FORMAT,INPUT_TIME_FORMAT,INPUT_DAY_MONTH_FORMAT,ADDRESS_FORMAT FROM CLW_COUNTRY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_COUNTRY.COUNTRY_ID,CLW_COUNTRY.SHORT_DESC,CLW_COUNTRY.UI_NAME,CLW_COUNTRY.COUNTRY_CODE,CLW_COUNTRY.ADD_DATE,CLW_COUNTRY.ADD_BY,CLW_COUNTRY.MOD_DATE,CLW_COUNTRY.MOD_BY,CLW_COUNTRY.LOCALE_CD,CLW_COUNTRY.INPUT_DATE_FORMAT,CLW_COUNTRY.INPUT_TIME_FORMAT,CLW_COUNTRY.INPUT_DAY_MONTH_FORMAT,CLW_COUNTRY.ADDRESS_FORMAT FROM CLW_COUNTRY");
                where = pCriteria.getSqlClause("CLW_COUNTRY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_COUNTRY.equals(otherTable)){
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
        CountryDataVector v = new CountryDataVector();
        while (rs.next()) {
            CountryData x = CountryData.createValue();
            
            x.setCountryId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setUiName(rs.getString(3));
            x.setCountryCode(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setLocaleCd(rs.getString(9));
            x.setInputDateFormat(rs.getString(10));
            x.setInputTimeFormat(rs.getString(11));
            x.setInputDayMonthFormat(rs.getString(12));
            x.setAddressFormat(rs.getString(13));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a CountryDataVector object that consists
     * of CountryData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for CountryData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new CountryDataVector()
     * @throws            SQLException
     */
    public static CountryDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        CountryDataVector v = new CountryDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT COUNTRY_ID,SHORT_DESC,UI_NAME,COUNTRY_CODE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOCALE_CD,INPUT_DATE_FORMAT,INPUT_TIME_FORMAT,INPUT_DAY_MONTH_FORMAT,ADDRESS_FORMAT FROM CLW_COUNTRY WHERE COUNTRY_ID IN (");

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
            CountryData x=null;
            while (rs.next()) {
                // build the object
                x=CountryData.createValue();
                
                x.setCountryId(rs.getInt(1));
                x.setShortDesc(rs.getString(2));
                x.setUiName(rs.getString(3));
                x.setCountryCode(rs.getString(4));
                x.setAddDate(rs.getTimestamp(5));
                x.setAddBy(rs.getString(6));
                x.setModDate(rs.getTimestamp(7));
                x.setModBy(rs.getString(8));
                x.setLocaleCd(rs.getString(9));
                x.setInputDateFormat(rs.getString(10));
                x.setInputTimeFormat(rs.getString(11));
                x.setInputDayMonthFormat(rs.getString(12));
                x.setAddressFormat(rs.getString(13));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a CountryDataVector object of all
     * CountryData objects in the database.
     * @param pCon An open database connection.
     * @return new CountryDataVector()
     * @throws            SQLException
     */
    public static CountryDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT COUNTRY_ID,SHORT_DESC,UI_NAME,COUNTRY_CODE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOCALE_CD,INPUT_DATE_FORMAT,INPUT_TIME_FORMAT,INPUT_DAY_MONTH_FORMAT,ADDRESS_FORMAT FROM CLW_COUNTRY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        CountryDataVector v = new CountryDataVector();
        CountryData x = null;
        while (rs.next()) {
            // build the object
            x = CountryData.createValue();
            
            x.setCountryId(rs.getInt(1));
            x.setShortDesc(rs.getString(2));
            x.setUiName(rs.getString(3));
            x.setCountryCode(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setLocaleCd(rs.getString(9));
            x.setInputDateFormat(rs.getString(10));
            x.setInputTimeFormat(rs.getString(11));
            x.setInputDayMonthFormat(rs.getString(12));
            x.setAddressFormat(rs.getString(13));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * CountryData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT COUNTRY_ID FROM CLW_COUNTRY");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT COUNTRY_ID FROM CLW_COUNTRY");
                where = pCriteria.getSqlClause("CLW_COUNTRY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_COUNTRY.equals(otherTable)){
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
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_COUNTRY");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_COUNTRY");
                where = pCriteria.getSqlClause("CLW_COUNTRY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_COUNTRY.equals(otherTable)){
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
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_COUNTRY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_COUNTRY");
                where = pCriteria.getSqlClause("CLW_COUNTRY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_COUNTRY.equals(otherTable)){
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
            log.debug("SQL text: " + sql);
        }

        return sql;
    }

    /**
     * Inserts a CountryData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CountryData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new CountryData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CountryData insert(Connection pCon, CountryData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_COUNTRY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_COUNTRY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setCountryId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_COUNTRY (COUNTRY_ID,SHORT_DESC,UI_NAME,COUNTRY_CODE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOCALE_CD,INPUT_DATE_FORMAT,INPUT_TIME_FORMAT,INPUT_DAY_MONTH_FORMAT,ADDRESS_FORMAT) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getCountryId());
        pstmt.setString(2,pData.getShortDesc());
        pstmt.setString(3,pData.getUiName());
        pstmt.setString(4,pData.getCountryCode());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());
        pstmt.setString(9,pData.getLocaleCd());
        pstmt.setString(10,pData.getInputDateFormat());
        pstmt.setString(11,pData.getInputTimeFormat());
        pstmt.setString(12,pData.getInputDayMonthFormat());
        pstmt.setString(13,pData.getAddressFormat());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   COUNTRY_ID="+pData.getCountryId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   UI_NAME="+pData.getUiName());
            log.debug("SQL:   COUNTRY_CODE="+pData.getCountryCode());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   LOCALE_CD="+pData.getLocaleCd());
            log.debug("SQL:   INPUT_DATE_FORMAT="+pData.getInputDateFormat());
            log.debug("SQL:   INPUT_TIME_FORMAT="+pData.getInputTimeFormat());
            log.debug("SQL:   INPUT_DAY_MONTH_FORMAT="+pData.getInputDayMonthFormat());
            log.debug("SQL:   ADDRESS_FORMAT="+pData.getAddressFormat());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        try {
            CachecosManager cacheManager = Cachecos.getCachecosManager();
            if(cacheManager != null && cacheManager.isStarted()){
                cacheManager.remove(pData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setCountryId(0);
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
     * Updates a CountryData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CountryData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CountryData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_COUNTRY SET SHORT_DESC = ?,UI_NAME = ?,COUNTRY_CODE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,LOCALE_CD = ?,INPUT_DATE_FORMAT = ?,INPUT_TIME_FORMAT = ?,INPUT_DAY_MONTH_FORMAT = ?,ADDRESS_FORMAT = ? WHERE COUNTRY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getUiName());
        pstmt.setString(i++,pData.getCountryCode());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getLocaleCd());
        pstmt.setString(i++,pData.getInputDateFormat());
        pstmt.setString(i++,pData.getInputTimeFormat());
        pstmt.setString(i++,pData.getInputDayMonthFormat());
        pstmt.setString(i++,pData.getAddressFormat());
        pstmt.setInt(i++,pData.getCountryId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   UI_NAME="+pData.getUiName());
            log.debug("SQL:   COUNTRY_CODE="+pData.getCountryCode());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   LOCALE_CD="+pData.getLocaleCd());
            log.debug("SQL:   INPUT_DATE_FORMAT="+pData.getInputDateFormat());
            log.debug("SQL:   INPUT_TIME_FORMAT="+pData.getInputTimeFormat());
            log.debug("SQL:   INPUT_DAY_MONTH_FORMAT="+pData.getInputDayMonthFormat());
            log.debug("SQL:   ADDRESS_FORMAT="+pData.getAddressFormat());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        try {
            CachecosManager cacheManager = Cachecos.getCachecosManager();
            if(cacheManager != null && cacheManager.isStarted()){
                cacheManager.remove(pData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a CountryData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCountryId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCountryId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_COUNTRY WHERE COUNTRY_ID = " + pCountryId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes CountryData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_COUNTRY");
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
     * Inserts a CountryData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CountryData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, CountryData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_COUNTRY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "COUNTRY_ID,SHORT_DESC,UI_NAME,COUNTRY_CODE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOCALE_CD,INPUT_DATE_FORMAT,INPUT_TIME_FORMAT,INPUT_DAY_MONTH_FORMAT,ADDRESS_FORMAT) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getCountryId());
        pstmt.setString(2+4,pData.getShortDesc());
        pstmt.setString(3+4,pData.getUiName());
        pstmt.setString(4+4,pData.getCountryCode());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());
        pstmt.setString(9+4,pData.getLocaleCd());
        pstmt.setString(10+4,pData.getInputDateFormat());
        pstmt.setString(11+4,pData.getInputTimeFormat());
        pstmt.setString(12+4,pData.getInputDayMonthFormat());
        pstmt.setString(13+4,pData.getAddressFormat());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a CountryData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CountryData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new CountryData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CountryData insert(Connection pCon, CountryData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a CountryData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CountryData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CountryData pData, boolean pLogFl)
        throws SQLException {
        CountryData oldData = null;
        if(pLogFl) {
          int id = pData.getCountryId();
          try {
          oldData = CountryDataAccess.select(pCon,id);
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
     * Deletes a CountryData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCountryId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCountryId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_COUNTRY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_COUNTRY d WHERE COUNTRY_ID = " + pCountryId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pCountryId);
        return n;
     }

    /**
     * Deletes CountryData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_COUNTRY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_COUNTRY d ");
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

