
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        PostalCodeDataAccess
 * Description:  This class is used to build access methods to the CLW_POSTAL_CODE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.PostalCodeData;
import com.cleanwise.service.api.value.PostalCodeDataVector;
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
 * <code>PostalCodeDataAccess</code>
 */
public class PostalCodeDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(PostalCodeDataAccess.class.getName());

    /** <code>CLW_POSTAL_CODE</code> table name */
	/* Primary key: POSTAL_CODE_ID */
	
    public static final String CLW_POSTAL_CODE = "CLW_POSTAL_CODE";
    
    /** <code>POSTAL_CODE_ID</code> POSTAL_CODE_ID column of table CLW_POSTAL_CODE */
    public static final String POSTAL_CODE_ID = "POSTAL_CODE_ID";
    /** <code>POSTAL_CODE</code> POSTAL_CODE column of table CLW_POSTAL_CODE */
    public static final String POSTAL_CODE = "POSTAL_CODE";
    /** <code>COUNTY_CD</code> COUNTY_CD column of table CLW_POSTAL_CODE */
    public static final String COUNTY_CD = "COUNTY_CD";
    /** <code>STATE_PROVINCE_CD</code> STATE_PROVINCE_CD column of table CLW_POSTAL_CODE */
    public static final String STATE_PROVINCE_CD = "STATE_PROVINCE_CD";
    /** <code>STATE_PROVINCE_NAM</code> STATE_PROVINCE_NAM column of table CLW_POSTAL_CODE */
    public static final String STATE_PROVINCE_NAM = "STATE_PROVINCE_NAM";
    /** <code>COUNTRY_CD</code> COUNTRY_CD column of table CLW_POSTAL_CODE */
    public static final String COUNTRY_CD = "COUNTRY_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_POSTAL_CODE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_POSTAL_CODE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_POSTAL_CODE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_POSTAL_CODE */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public PostalCodeDataAccess()
    {
    }

    /**
     * Gets a PostalCodeData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pPostalCodeId The key requested.
     * @return new PostalCodeData()
     * @throws            SQLException
     */
    public static PostalCodeData select(Connection pCon, int pPostalCodeId)
        throws SQLException, DataNotFoundException {
        PostalCodeData x=null;
        String sql="SELECT POSTAL_CODE_ID,POSTAL_CODE,COUNTY_CD,STATE_PROVINCE_CD,STATE_PROVINCE_NAM,COUNTRY_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_POSTAL_CODE WHERE POSTAL_CODE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pPostalCodeId=" + pPostalCodeId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pPostalCodeId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=PostalCodeData.createValue();
            
            x.setPostalCodeId(rs.getInt(1));
            x.setPostalCode(rs.getString(2));
            x.setCountyCd(rs.getString(3));
            x.setStateProvinceCd(rs.getString(4));
            x.setStateProvinceNam(rs.getString(5));
            x.setCountryCd(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("POSTAL_CODE_ID :" + pPostalCodeId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a PostalCodeDataVector object that consists
     * of PostalCodeData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new PostalCodeDataVector()
     * @throws            SQLException
     */
    public static PostalCodeDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a PostalCodeData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_POSTAL_CODE.POSTAL_CODE_ID,CLW_POSTAL_CODE.POSTAL_CODE,CLW_POSTAL_CODE.COUNTY_CD,CLW_POSTAL_CODE.STATE_PROVINCE_CD,CLW_POSTAL_CODE.STATE_PROVINCE_NAM,CLW_POSTAL_CODE.COUNTRY_CD,CLW_POSTAL_CODE.ADD_DATE,CLW_POSTAL_CODE.ADD_BY,CLW_POSTAL_CODE.MOD_DATE,CLW_POSTAL_CODE.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated PostalCodeData Object.
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
    *@returns a populated PostalCodeData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         PostalCodeData x = PostalCodeData.createValue();
         
         x.setPostalCodeId(rs.getInt(1+offset));
         x.setPostalCode(rs.getString(2+offset));
         x.setCountyCd(rs.getString(3+offset));
         x.setStateProvinceCd(rs.getString(4+offset));
         x.setStateProvinceNam(rs.getString(5+offset));
         x.setCountryCd(rs.getString(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the PostalCodeData Object represents.
    */
    public int getColumnCount(){
        return 10;
    }

    /**
     * Gets a PostalCodeDataVector object that consists
     * of PostalCodeData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new PostalCodeDataVector()
     * @throws            SQLException
     */
    public static PostalCodeDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT POSTAL_CODE_ID,POSTAL_CODE,COUNTY_CD,STATE_PROVINCE_CD,STATE_PROVINCE_NAM,COUNTRY_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_POSTAL_CODE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_POSTAL_CODE.POSTAL_CODE_ID,CLW_POSTAL_CODE.POSTAL_CODE,CLW_POSTAL_CODE.COUNTY_CD,CLW_POSTAL_CODE.STATE_PROVINCE_CD,CLW_POSTAL_CODE.STATE_PROVINCE_NAM,CLW_POSTAL_CODE.COUNTRY_CD,CLW_POSTAL_CODE.ADD_DATE,CLW_POSTAL_CODE.ADD_BY,CLW_POSTAL_CODE.MOD_DATE,CLW_POSTAL_CODE.MOD_BY FROM CLW_POSTAL_CODE");
                where = pCriteria.getSqlClause("CLW_POSTAL_CODE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_POSTAL_CODE.equals(otherTable)){
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
        PostalCodeDataVector v = new PostalCodeDataVector();
        while (rs.next()) {
            PostalCodeData x = PostalCodeData.createValue();
            
            x.setPostalCodeId(rs.getInt(1));
            x.setPostalCode(rs.getString(2));
            x.setCountyCd(rs.getString(3));
            x.setStateProvinceCd(rs.getString(4));
            x.setStateProvinceNam(rs.getString(5));
            x.setCountryCd(rs.getString(6));
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
     * Gets a PostalCodeDataVector object that consists
     * of PostalCodeData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for PostalCodeData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new PostalCodeDataVector()
     * @throws            SQLException
     */
    public static PostalCodeDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        PostalCodeDataVector v = new PostalCodeDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT POSTAL_CODE_ID,POSTAL_CODE,COUNTY_CD,STATE_PROVINCE_CD,STATE_PROVINCE_NAM,COUNTRY_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_POSTAL_CODE WHERE POSTAL_CODE_ID IN (");

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
            PostalCodeData x=null;
            while (rs.next()) {
                // build the object
                x=PostalCodeData.createValue();
                
                x.setPostalCodeId(rs.getInt(1));
                x.setPostalCode(rs.getString(2));
                x.setCountyCd(rs.getString(3));
                x.setStateProvinceCd(rs.getString(4));
                x.setStateProvinceNam(rs.getString(5));
                x.setCountryCd(rs.getString(6));
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
     * Gets a PostalCodeDataVector object of all
     * PostalCodeData objects in the database.
     * @param pCon An open database connection.
     * @return new PostalCodeDataVector()
     * @throws            SQLException
     */
    public static PostalCodeDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT POSTAL_CODE_ID,POSTAL_CODE,COUNTY_CD,STATE_PROVINCE_CD,STATE_PROVINCE_NAM,COUNTRY_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_POSTAL_CODE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        PostalCodeDataVector v = new PostalCodeDataVector();
        PostalCodeData x = null;
        while (rs.next()) {
            // build the object
            x = PostalCodeData.createValue();
            
            x.setPostalCodeId(rs.getInt(1));
            x.setPostalCode(rs.getString(2));
            x.setCountyCd(rs.getString(3));
            x.setStateProvinceCd(rs.getString(4));
            x.setStateProvinceNam(rs.getString(5));
            x.setCountryCd(rs.getString(6));
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
     * PostalCodeData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT POSTAL_CODE_ID FROM CLW_POSTAL_CODE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_POSTAL_CODE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_POSTAL_CODE");
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
     * Inserts a PostalCodeData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PostalCodeData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new PostalCodeData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PostalCodeData insert(Connection pCon, PostalCodeData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_POSTAL_CODE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_POSTAL_CODE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setPostalCodeId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_POSTAL_CODE (POSTAL_CODE_ID,POSTAL_CODE,COUNTY_CD,STATE_PROVINCE_CD,STATE_PROVINCE_NAM,COUNTRY_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getPostalCodeId());
        pstmt.setString(2,pData.getPostalCode());
        pstmt.setString(3,pData.getCountyCd());
        pstmt.setString(4,pData.getStateProvinceCd());
        pstmt.setString(5,pData.getStateProvinceNam());
        pstmt.setString(6,pData.getCountryCd());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   POSTAL_CODE_ID="+pData.getPostalCodeId());
            log.debug("SQL:   POSTAL_CODE="+pData.getPostalCode());
            log.debug("SQL:   COUNTY_CD="+pData.getCountyCd());
            log.debug("SQL:   STATE_PROVINCE_CD="+pData.getStateProvinceCd());
            log.debug("SQL:   STATE_PROVINCE_NAM="+pData.getStateProvinceNam());
            log.debug("SQL:   COUNTRY_CD="+pData.getCountryCd());
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
        pData.setPostalCodeId(0);
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
     * Updates a PostalCodeData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PostalCodeData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PostalCodeData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_POSTAL_CODE SET POSTAL_CODE = ?,COUNTY_CD = ?,STATE_PROVINCE_CD = ?,STATE_PROVINCE_NAM = ?,COUNTRY_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE POSTAL_CODE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getPostalCode());
        pstmt.setString(i++,pData.getCountyCd());
        pstmt.setString(i++,pData.getStateProvinceCd());
        pstmt.setString(i++,pData.getStateProvinceNam());
        pstmt.setString(i++,pData.getCountryCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getPostalCodeId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   POSTAL_CODE="+pData.getPostalCode());
            log.debug("SQL:   COUNTY_CD="+pData.getCountyCd());
            log.debug("SQL:   STATE_PROVINCE_CD="+pData.getStateProvinceCd());
            log.debug("SQL:   STATE_PROVINCE_NAM="+pData.getStateProvinceNam());
            log.debug("SQL:   COUNTRY_CD="+pData.getCountryCd());
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
     * Deletes a PostalCodeData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPostalCodeId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPostalCodeId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_POSTAL_CODE WHERE POSTAL_CODE_ID = " + pPostalCodeId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes PostalCodeData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_POSTAL_CODE");
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
     * Inserts a PostalCodeData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PostalCodeData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, PostalCodeData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_POSTAL_CODE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "POSTAL_CODE_ID,POSTAL_CODE,COUNTY_CD,STATE_PROVINCE_CD,STATE_PROVINCE_NAM,COUNTRY_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getPostalCodeId());
        pstmt.setString(2+4,pData.getPostalCode());
        pstmt.setString(3+4,pData.getCountyCd());
        pstmt.setString(4+4,pData.getStateProvinceCd());
        pstmt.setString(5+4,pData.getStateProvinceNam());
        pstmt.setString(6+4,pData.getCountryCd());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a PostalCodeData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PostalCodeData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new PostalCodeData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PostalCodeData insert(Connection pCon, PostalCodeData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a PostalCodeData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PostalCodeData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PostalCodeData pData, boolean pLogFl)
        throws SQLException {
        PostalCodeData oldData = null;
        if(pLogFl) {
          int id = pData.getPostalCodeId();
          try {
          oldData = PostalCodeDataAccess.select(pCon,id);
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
     * Deletes a PostalCodeData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPostalCodeId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPostalCodeId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_POSTAL_CODE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_POSTAL_CODE d WHERE POSTAL_CODE_ID = " + pPostalCodeId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pPostalCodeId);
        return n;
     }

    /**
     * Deletes PostalCodeData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_POSTAL_CODE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_POSTAL_CODE d ");
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

