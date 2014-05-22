
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        CountryPropertyDataAccess
 * Description:  This class is used to build access methods to the CLW_COUNTRY_PROPERTY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.CountryPropertyData;
import com.cleanwise.service.api.value.CountryPropertyDataVector;
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
 * <code>CountryPropertyDataAccess</code>
 */
public class CountryPropertyDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(CountryPropertyDataAccess.class.getName());

    /** <code>CLW_COUNTRY_PROPERTY</code> table name */
	/* Primary key: COUNTRY_PROPERTY_ID */
	
    public static final String CLW_COUNTRY_PROPERTY = "CLW_COUNTRY_PROPERTY";
    
    /** <code>COUNTRY_PROPERTY_ID</code> COUNTRY_PROPERTY_ID column of table CLW_COUNTRY_PROPERTY */
    public static final String COUNTRY_PROPERTY_ID = "COUNTRY_PROPERTY_ID";
    /** <code>COUNTRY_ID</code> COUNTRY_ID column of table CLW_COUNTRY_PROPERTY */
    public static final String COUNTRY_ID = "COUNTRY_ID";
    /** <code>COUNTRY_PROPERTY_CD</code> COUNTRY_PROPERTY_CD column of table CLW_COUNTRY_PROPERTY */
    public static final String COUNTRY_PROPERTY_CD = "COUNTRY_PROPERTY_CD";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_COUNTRY_PROPERTY */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_COUNTRY_PROPERTY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_COUNTRY_PROPERTY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_COUNTRY_PROPERTY */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_COUNTRY_PROPERTY */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public CountryPropertyDataAccess()
    {
    }

    /**
     * Gets a CountryPropertyData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pCountryPropertyId The key requested.
     * @return new CountryPropertyData()
     * @throws            SQLException
     */
    public static CountryPropertyData select(Connection pCon, int pCountryPropertyId)
        throws SQLException, DataNotFoundException {
        CountryPropertyData x=null;
        String sql="SELECT COUNTRY_PROPERTY_ID,COUNTRY_ID,COUNTRY_PROPERTY_CD,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_COUNTRY_PROPERTY WHERE COUNTRY_PROPERTY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pCountryPropertyId=" + pCountryPropertyId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pCountryPropertyId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=CountryPropertyData.createValue();
            
            x.setCountryPropertyId(rs.getInt(1));
            x.setCountryId(rs.getInt(2));
            x.setCountryPropertyCd(rs.getString(3));
            x.setValue(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("COUNTRY_PROPERTY_ID :" + pCountryPropertyId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a CountryPropertyDataVector object that consists
     * of CountryPropertyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new CountryPropertyDataVector()
     * @throws            SQLException
     */
    public static CountryPropertyDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a CountryPropertyData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_COUNTRY_PROPERTY.COUNTRY_PROPERTY_ID,CLW_COUNTRY_PROPERTY.COUNTRY_ID,CLW_COUNTRY_PROPERTY.COUNTRY_PROPERTY_CD,CLW_COUNTRY_PROPERTY.CLW_VALUE,CLW_COUNTRY_PROPERTY.ADD_DATE,CLW_COUNTRY_PROPERTY.ADD_BY,CLW_COUNTRY_PROPERTY.MOD_DATE,CLW_COUNTRY_PROPERTY.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated CountryPropertyData Object.
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
    *@returns a populated CountryPropertyData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         CountryPropertyData x = CountryPropertyData.createValue();
         
         x.setCountryPropertyId(rs.getInt(1+offset));
         x.setCountryId(rs.getInt(2+offset));
         x.setCountryPropertyCd(rs.getString(3+offset));
         x.setValue(rs.getString(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the CountryPropertyData Object represents.
    */
    public int getColumnCount(){
        return 8;
    }

    /**
     * Gets a CountryPropertyDataVector object that consists
     * of CountryPropertyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new CountryPropertyDataVector()
     * @throws            SQLException
     */
    public static CountryPropertyDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT COUNTRY_PROPERTY_ID,COUNTRY_ID,COUNTRY_PROPERTY_CD,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_COUNTRY_PROPERTY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_COUNTRY_PROPERTY.COUNTRY_PROPERTY_ID,CLW_COUNTRY_PROPERTY.COUNTRY_ID,CLW_COUNTRY_PROPERTY.COUNTRY_PROPERTY_CD,CLW_COUNTRY_PROPERTY.CLW_VALUE,CLW_COUNTRY_PROPERTY.ADD_DATE,CLW_COUNTRY_PROPERTY.ADD_BY,CLW_COUNTRY_PROPERTY.MOD_DATE,CLW_COUNTRY_PROPERTY.MOD_BY FROM CLW_COUNTRY_PROPERTY");
                where = pCriteria.getSqlClause("CLW_COUNTRY_PROPERTY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_COUNTRY_PROPERTY.equals(otherTable)){
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
        CountryPropertyDataVector v = new CountryPropertyDataVector();
        while (rs.next()) {
            CountryPropertyData x = CountryPropertyData.createValue();
            
            x.setCountryPropertyId(rs.getInt(1));
            x.setCountryId(rs.getInt(2));
            x.setCountryPropertyCd(rs.getString(3));
            x.setValue(rs.getString(4));
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
     * Gets a CountryPropertyDataVector object that consists
     * of CountryPropertyData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for CountryPropertyData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new CountryPropertyDataVector()
     * @throws            SQLException
     */
    public static CountryPropertyDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        CountryPropertyDataVector v = new CountryPropertyDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT COUNTRY_PROPERTY_ID,COUNTRY_ID,COUNTRY_PROPERTY_CD,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_COUNTRY_PROPERTY WHERE COUNTRY_PROPERTY_ID IN (");

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
            CountryPropertyData x=null;
            while (rs.next()) {
                // build the object
                x=CountryPropertyData.createValue();
                
                x.setCountryPropertyId(rs.getInt(1));
                x.setCountryId(rs.getInt(2));
                x.setCountryPropertyCd(rs.getString(3));
                x.setValue(rs.getString(4));
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
     * Gets a CountryPropertyDataVector object of all
     * CountryPropertyData objects in the database.
     * @param pCon An open database connection.
     * @return new CountryPropertyDataVector()
     * @throws            SQLException
     */
    public static CountryPropertyDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT COUNTRY_PROPERTY_ID,COUNTRY_ID,COUNTRY_PROPERTY_CD,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_COUNTRY_PROPERTY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        CountryPropertyDataVector v = new CountryPropertyDataVector();
        CountryPropertyData x = null;
        while (rs.next()) {
            // build the object
            x = CountryPropertyData.createValue();
            
            x.setCountryPropertyId(rs.getInt(1));
            x.setCountryId(rs.getInt(2));
            x.setCountryPropertyCd(rs.getString(3));
            x.setValue(rs.getString(4));
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
     * CountryPropertyData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT COUNTRY_PROPERTY_ID FROM CLW_COUNTRY_PROPERTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_COUNTRY_PROPERTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_COUNTRY_PROPERTY");
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
     * Inserts a CountryPropertyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CountryPropertyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new CountryPropertyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CountryPropertyData insert(Connection pCon, CountryPropertyData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_COUNTRY_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_COUNTRY_PROPERTY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setCountryPropertyId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_COUNTRY_PROPERTY (COUNTRY_PROPERTY_ID,COUNTRY_ID,COUNTRY_PROPERTY_CD,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getCountryPropertyId());
        pstmt.setInt(2,pData.getCountryId());
        pstmt.setString(3,pData.getCountryPropertyCd());
        pstmt.setString(4,pData.getValue());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   COUNTRY_PROPERTY_ID="+pData.getCountryPropertyId());
            log.debug("SQL:   COUNTRY_ID="+pData.getCountryId());
            log.debug("SQL:   COUNTRY_PROPERTY_CD="+pData.getCountryPropertyCd());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
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
        pData.setCountryPropertyId(0);
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
     * Updates a CountryPropertyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CountryPropertyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CountryPropertyData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_COUNTRY_PROPERTY SET COUNTRY_ID = ?,COUNTRY_PROPERTY_CD = ?,CLW_VALUE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE COUNTRY_PROPERTY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getCountryId());
        pstmt.setString(i++,pData.getCountryPropertyCd());
        pstmt.setString(i++,pData.getValue());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getCountryPropertyId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   COUNTRY_ID="+pData.getCountryId());
            log.debug("SQL:   COUNTRY_PROPERTY_CD="+pData.getCountryPropertyCd());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
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
     * Deletes a CountryPropertyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCountryPropertyId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCountryPropertyId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_COUNTRY_PROPERTY WHERE COUNTRY_PROPERTY_ID = " + pCountryPropertyId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes CountryPropertyData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_COUNTRY_PROPERTY");
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
     * Inserts a CountryPropertyData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CountryPropertyData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, CountryPropertyData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_COUNTRY_PROPERTY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "COUNTRY_PROPERTY_ID,COUNTRY_ID,COUNTRY_PROPERTY_CD,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getCountryPropertyId());
        pstmt.setInt(2+4,pData.getCountryId());
        pstmt.setString(3+4,pData.getCountryPropertyCd());
        pstmt.setString(4+4,pData.getValue());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a CountryPropertyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A CountryPropertyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new CountryPropertyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static CountryPropertyData insert(Connection pCon, CountryPropertyData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a CountryPropertyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A CountryPropertyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, CountryPropertyData pData, boolean pLogFl)
        throws SQLException {
        CountryPropertyData oldData = null;
        if(pLogFl) {
          int id = pData.getCountryPropertyId();
          try {
          oldData = CountryPropertyDataAccess.select(pCon,id);
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
     * Deletes a CountryPropertyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pCountryPropertyId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pCountryPropertyId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_COUNTRY_PROPERTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_COUNTRY_PROPERTY d WHERE COUNTRY_PROPERTY_ID = " + pCountryPropertyId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pCountryPropertyId);
        return n;
     }

    /**
     * Deletes CountryPropertyData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_COUNTRY_PROPERTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_COUNTRY_PROPERTY d ");
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

