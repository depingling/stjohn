
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ShoppingOptionsDataAccess
 * Description:  This class is used to build access methods to the CLW_SHOPPING_OPTIONS table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ShoppingOptionsData;
import com.cleanwise.service.api.value.ShoppingOptionsDataVector;
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
 * <code>ShoppingOptionsDataAccess</code>
 */
public class ShoppingOptionsDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ShoppingOptionsDataAccess.class.getName());

    /** <code>CLW_SHOPPING_OPTIONS</code> table name */
	/* Primary key: SHOPPING_OPTIONS_ID */
	
    public static final String CLW_SHOPPING_OPTIONS = "CLW_SHOPPING_OPTIONS";
    
    /** <code>SHOPPING_OPTIONS_ID</code> SHOPPING_OPTIONS_ID column of table CLW_SHOPPING_OPTIONS */
    public static final String SHOPPING_OPTIONS_ID = "SHOPPING_OPTIONS_ID";
    /** <code>ACCOUNT_ID</code> ACCOUNT_ID column of table CLW_SHOPPING_OPTIONS */
    public static final String ACCOUNT_ID = "ACCOUNT_ID";
    /** <code>FIELD_NAME</code> FIELD_NAME column of table CLW_SHOPPING_OPTIONS */
    public static final String FIELD_NAME = "FIELD_NAME";
    /** <code>FIELD_VALUE</code> FIELD_VALUE column of table CLW_SHOPPING_OPTIONS */
    public static final String FIELD_VALUE = "FIELD_VALUE";
    /** <code>OPTION_VALUE</code> OPTION_VALUE column of table CLW_SHOPPING_OPTIONS */
    public static final String OPTION_VALUE = "OPTION_VALUE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_SHOPPING_OPTIONS */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_SHOPPING_OPTIONS */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_SHOPPING_OPTIONS */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_SHOPPING_OPTIONS */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public ShoppingOptionsDataAccess()
    {
    }

    /**
     * Gets a ShoppingOptionsData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pShoppingOptionsId The key requested.
     * @return new ShoppingOptionsData()
     * @throws            SQLException
     */
    public static ShoppingOptionsData select(Connection pCon, int pShoppingOptionsId)
        throws SQLException, DataNotFoundException {
        ShoppingOptionsData x=null;
        String sql="SELECT SHOPPING_OPTIONS_ID,ACCOUNT_ID,FIELD_NAME,FIELD_VALUE,OPTION_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_SHOPPING_OPTIONS WHERE SHOPPING_OPTIONS_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pShoppingOptionsId=" + pShoppingOptionsId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pShoppingOptionsId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ShoppingOptionsData.createValue();
            
            x.setShoppingOptionsId(rs.getInt(1));
            x.setAccountId(rs.getInt(2));
            x.setFieldName(rs.getString(3));
            x.setFieldValue(rs.getString(4));
            x.setOptionValue(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("SHOPPING_OPTIONS_ID :" + pShoppingOptionsId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ShoppingOptionsDataVector object that consists
     * of ShoppingOptionsData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ShoppingOptionsDataVector()
     * @throws            SQLException
     */
    public static ShoppingOptionsDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ShoppingOptionsData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_SHOPPING_OPTIONS.SHOPPING_OPTIONS_ID,CLW_SHOPPING_OPTIONS.ACCOUNT_ID,CLW_SHOPPING_OPTIONS.FIELD_NAME,CLW_SHOPPING_OPTIONS.FIELD_VALUE,CLW_SHOPPING_OPTIONS.OPTION_VALUE,CLW_SHOPPING_OPTIONS.ADD_DATE,CLW_SHOPPING_OPTIONS.ADD_BY,CLW_SHOPPING_OPTIONS.MOD_DATE,CLW_SHOPPING_OPTIONS.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ShoppingOptionsData Object.
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
    *@returns a populated ShoppingOptionsData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ShoppingOptionsData x = ShoppingOptionsData.createValue();
         
         x.setShoppingOptionsId(rs.getInt(1+offset));
         x.setAccountId(rs.getInt(2+offset));
         x.setFieldName(rs.getString(3+offset));
         x.setFieldValue(rs.getString(4+offset));
         x.setOptionValue(rs.getString(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ShoppingOptionsData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a ShoppingOptionsDataVector object that consists
     * of ShoppingOptionsData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ShoppingOptionsDataVector()
     * @throws            SQLException
     */
    public static ShoppingOptionsDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT SHOPPING_OPTIONS_ID,ACCOUNT_ID,FIELD_NAME,FIELD_VALUE,OPTION_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_SHOPPING_OPTIONS");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_SHOPPING_OPTIONS.SHOPPING_OPTIONS_ID,CLW_SHOPPING_OPTIONS.ACCOUNT_ID,CLW_SHOPPING_OPTIONS.FIELD_NAME,CLW_SHOPPING_OPTIONS.FIELD_VALUE,CLW_SHOPPING_OPTIONS.OPTION_VALUE,CLW_SHOPPING_OPTIONS.ADD_DATE,CLW_SHOPPING_OPTIONS.ADD_BY,CLW_SHOPPING_OPTIONS.MOD_DATE,CLW_SHOPPING_OPTIONS.MOD_BY FROM CLW_SHOPPING_OPTIONS");
                where = pCriteria.getSqlClause("CLW_SHOPPING_OPTIONS");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_SHOPPING_OPTIONS.equals(otherTable)){
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
        ShoppingOptionsDataVector v = new ShoppingOptionsDataVector();
        while (rs.next()) {
            ShoppingOptionsData x = ShoppingOptionsData.createValue();
            
            x.setShoppingOptionsId(rs.getInt(1));
            x.setAccountId(rs.getInt(2));
            x.setFieldName(rs.getString(3));
            x.setFieldValue(rs.getString(4));
            x.setOptionValue(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ShoppingOptionsDataVector object that consists
     * of ShoppingOptionsData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ShoppingOptionsData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ShoppingOptionsDataVector()
     * @throws            SQLException
     */
    public static ShoppingOptionsDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ShoppingOptionsDataVector v = new ShoppingOptionsDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT SHOPPING_OPTIONS_ID,ACCOUNT_ID,FIELD_NAME,FIELD_VALUE,OPTION_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_SHOPPING_OPTIONS WHERE SHOPPING_OPTIONS_ID IN (");

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
            ShoppingOptionsData x=null;
            while (rs.next()) {
                // build the object
                x=ShoppingOptionsData.createValue();
                
                x.setShoppingOptionsId(rs.getInt(1));
                x.setAccountId(rs.getInt(2));
                x.setFieldName(rs.getString(3));
                x.setFieldValue(rs.getString(4));
                x.setOptionValue(rs.getString(5));
                x.setAddDate(rs.getTimestamp(6));
                x.setAddBy(rs.getString(7));
                x.setModDate(rs.getTimestamp(8));
                x.setModBy(rs.getString(9));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ShoppingOptionsDataVector object of all
     * ShoppingOptionsData objects in the database.
     * @param pCon An open database connection.
     * @return new ShoppingOptionsDataVector()
     * @throws            SQLException
     */
    public static ShoppingOptionsDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT SHOPPING_OPTIONS_ID,ACCOUNT_ID,FIELD_NAME,FIELD_VALUE,OPTION_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_SHOPPING_OPTIONS";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ShoppingOptionsDataVector v = new ShoppingOptionsDataVector();
        ShoppingOptionsData x = null;
        while (rs.next()) {
            // build the object
            x = ShoppingOptionsData.createValue();
            
            x.setShoppingOptionsId(rs.getInt(1));
            x.setAccountId(rs.getInt(2));
            x.setFieldName(rs.getString(3));
            x.setFieldValue(rs.getString(4));
            x.setOptionValue(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ShoppingOptionsData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT SHOPPING_OPTIONS_ID FROM CLW_SHOPPING_OPTIONS");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SHOPPING_OPTIONS");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SHOPPING_OPTIONS");
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
     * Inserts a ShoppingOptionsData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingOptionsData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ShoppingOptionsData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ShoppingOptionsData insert(Connection pCon, ShoppingOptionsData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_SHOPPING_OPTIONS_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_SHOPPING_OPTIONS_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setShoppingOptionsId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_SHOPPING_OPTIONS (SHOPPING_OPTIONS_ID,ACCOUNT_ID,FIELD_NAME,FIELD_VALUE,OPTION_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getShoppingOptionsId());
        pstmt.setInt(2,pData.getAccountId());
        pstmt.setString(3,pData.getFieldName());
        pstmt.setString(4,pData.getFieldValue());
        pstmt.setString(5,pData.getOptionValue());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHOPPING_OPTIONS_ID="+pData.getShoppingOptionsId());
            log.debug("SQL:   ACCOUNT_ID="+pData.getAccountId());
            log.debug("SQL:   FIELD_NAME="+pData.getFieldName());
            log.debug("SQL:   FIELD_VALUE="+pData.getFieldValue());
            log.debug("SQL:   OPTION_VALUE="+pData.getOptionValue());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
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
        pData.setShoppingOptionsId(0);
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
     * Updates a ShoppingOptionsData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingOptionsData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ShoppingOptionsData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_SHOPPING_OPTIONS SET ACCOUNT_ID = ?,FIELD_NAME = ?,FIELD_VALUE = ?,OPTION_VALUE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE SHOPPING_OPTIONS_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getAccountId());
        pstmt.setString(i++,pData.getFieldName());
        pstmt.setString(i++,pData.getFieldValue());
        pstmt.setString(i++,pData.getOptionValue());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getShoppingOptionsId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ACCOUNT_ID="+pData.getAccountId());
            log.debug("SQL:   FIELD_NAME="+pData.getFieldName());
            log.debug("SQL:   FIELD_VALUE="+pData.getFieldValue());
            log.debug("SQL:   OPTION_VALUE="+pData.getOptionValue());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
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
     * Deletes a ShoppingOptionsData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pShoppingOptionsId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pShoppingOptionsId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_SHOPPING_OPTIONS WHERE SHOPPING_OPTIONS_ID = " + pShoppingOptionsId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ShoppingOptionsData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_SHOPPING_OPTIONS");
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
     * Inserts a ShoppingOptionsData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingOptionsData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ShoppingOptionsData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_SHOPPING_OPTIONS (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "SHOPPING_OPTIONS_ID,ACCOUNT_ID,FIELD_NAME,FIELD_VALUE,OPTION_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getShoppingOptionsId());
        pstmt.setInt(2+4,pData.getAccountId());
        pstmt.setString(3+4,pData.getFieldName());
        pstmt.setString(4+4,pData.getFieldValue());
        pstmt.setString(5+4,pData.getOptionValue());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ShoppingOptionsData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingOptionsData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ShoppingOptionsData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ShoppingOptionsData insert(Connection pCon, ShoppingOptionsData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ShoppingOptionsData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingOptionsData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ShoppingOptionsData pData, boolean pLogFl)
        throws SQLException {
        ShoppingOptionsData oldData = null;
        if(pLogFl) {
          int id = pData.getShoppingOptionsId();
          try {
          oldData = ShoppingOptionsDataAccess.select(pCon,id);
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
     * Deletes a ShoppingOptionsData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pShoppingOptionsId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pShoppingOptionsId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_SHOPPING_OPTIONS SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_SHOPPING_OPTIONS d WHERE SHOPPING_OPTIONS_ID = " + pShoppingOptionsId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pShoppingOptionsId);
        return n;
     }

    /**
     * Deletes ShoppingOptionsData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_SHOPPING_OPTIONS SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_SHOPPING_OPTIONS d ");
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

