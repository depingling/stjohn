
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ShoppingOptConfigDataAccess
 * Description:  This class is used to build access methods to the CLW_SHOPPING_OPT_CONFIG table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ShoppingOptConfigData;
import com.cleanwise.service.api.value.ShoppingOptConfigDataVector;
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
 * <code>ShoppingOptConfigDataAccess</code>
 */
public class ShoppingOptConfigDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ShoppingOptConfigDataAccess.class.getName());

    /** <code>CLW_SHOPPING_OPT_CONFIG</code> table name */
	/* Primary key: SHOPPING_OPT_CONFIG_ID */
	
    public static final String CLW_SHOPPING_OPT_CONFIG = "CLW_SHOPPING_OPT_CONFIG";
    
    /** <code>SHOPPING_OPT_CONFIG_ID</code> SHOPPING_OPT_CONFIG_ID column of table CLW_SHOPPING_OPT_CONFIG */
    public static final String SHOPPING_OPT_CONFIG_ID = "SHOPPING_OPT_CONFIG_ID";
    /** <code>ACCOUNT_ID</code> ACCOUNT_ID column of table CLW_SHOPPING_OPT_CONFIG */
    public static final String ACCOUNT_ID = "ACCOUNT_ID";
    /** <code>FIELD_NAME</code> FIELD_NAME column of table CLW_SHOPPING_OPT_CONFIG */
    public static final String FIELD_NAME = "FIELD_NAME";
    /** <code>FIELD_PROMPT</code> FIELD_PROMPT column of table CLW_SHOPPING_OPT_CONFIG */
    public static final String FIELD_PROMPT = "FIELD_PROMPT";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_SHOPPING_OPT_CONFIG */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_SHOPPING_OPT_CONFIG */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_SHOPPING_OPT_CONFIG */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_SHOPPING_OPT_CONFIG */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public ShoppingOptConfigDataAccess()
    {
    }

    /**
     * Gets a ShoppingOptConfigData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pShoppingOptConfigId The key requested.
     * @return new ShoppingOptConfigData()
     * @throws            SQLException
     */
    public static ShoppingOptConfigData select(Connection pCon, int pShoppingOptConfigId)
        throws SQLException, DataNotFoundException {
        ShoppingOptConfigData x=null;
        String sql="SELECT SHOPPING_OPT_CONFIG_ID,ACCOUNT_ID,FIELD_NAME,FIELD_PROMPT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_SHOPPING_OPT_CONFIG WHERE SHOPPING_OPT_CONFIG_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pShoppingOptConfigId=" + pShoppingOptConfigId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pShoppingOptConfigId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ShoppingOptConfigData.createValue();
            
            x.setShoppingOptConfigId(rs.getInt(1));
            x.setAccountId(rs.getInt(2));
            x.setFieldName(rs.getString(3));
            x.setFieldPrompt(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("SHOPPING_OPT_CONFIG_ID :" + pShoppingOptConfigId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ShoppingOptConfigDataVector object that consists
     * of ShoppingOptConfigData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ShoppingOptConfigDataVector()
     * @throws            SQLException
     */
    public static ShoppingOptConfigDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ShoppingOptConfigData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_SHOPPING_OPT_CONFIG.SHOPPING_OPT_CONFIG_ID,CLW_SHOPPING_OPT_CONFIG.ACCOUNT_ID,CLW_SHOPPING_OPT_CONFIG.FIELD_NAME,CLW_SHOPPING_OPT_CONFIG.FIELD_PROMPT,CLW_SHOPPING_OPT_CONFIG.ADD_DATE,CLW_SHOPPING_OPT_CONFIG.ADD_BY,CLW_SHOPPING_OPT_CONFIG.MOD_DATE,CLW_SHOPPING_OPT_CONFIG.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ShoppingOptConfigData Object.
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
    *@returns a populated ShoppingOptConfigData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ShoppingOptConfigData x = ShoppingOptConfigData.createValue();
         
         x.setShoppingOptConfigId(rs.getInt(1+offset));
         x.setAccountId(rs.getInt(2+offset));
         x.setFieldName(rs.getString(3+offset));
         x.setFieldPrompt(rs.getString(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ShoppingOptConfigData Object represents.
    */
    public int getColumnCount(){
        return 8;
    }

    /**
     * Gets a ShoppingOptConfigDataVector object that consists
     * of ShoppingOptConfigData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ShoppingOptConfigDataVector()
     * @throws            SQLException
     */
    public static ShoppingOptConfigDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT SHOPPING_OPT_CONFIG_ID,ACCOUNT_ID,FIELD_NAME,FIELD_PROMPT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_SHOPPING_OPT_CONFIG");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_SHOPPING_OPT_CONFIG.SHOPPING_OPT_CONFIG_ID,CLW_SHOPPING_OPT_CONFIG.ACCOUNT_ID,CLW_SHOPPING_OPT_CONFIG.FIELD_NAME,CLW_SHOPPING_OPT_CONFIG.FIELD_PROMPT,CLW_SHOPPING_OPT_CONFIG.ADD_DATE,CLW_SHOPPING_OPT_CONFIG.ADD_BY,CLW_SHOPPING_OPT_CONFIG.MOD_DATE,CLW_SHOPPING_OPT_CONFIG.MOD_BY FROM CLW_SHOPPING_OPT_CONFIG");
                where = pCriteria.getSqlClause("CLW_SHOPPING_OPT_CONFIG");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_SHOPPING_OPT_CONFIG.equals(otherTable)){
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
        ShoppingOptConfigDataVector v = new ShoppingOptConfigDataVector();
        while (rs.next()) {
            ShoppingOptConfigData x = ShoppingOptConfigData.createValue();
            
            x.setShoppingOptConfigId(rs.getInt(1));
            x.setAccountId(rs.getInt(2));
            x.setFieldName(rs.getString(3));
            x.setFieldPrompt(rs.getString(4));
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
     * Gets a ShoppingOptConfigDataVector object that consists
     * of ShoppingOptConfigData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ShoppingOptConfigData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ShoppingOptConfigDataVector()
     * @throws            SQLException
     */
    public static ShoppingOptConfigDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ShoppingOptConfigDataVector v = new ShoppingOptConfigDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT SHOPPING_OPT_CONFIG_ID,ACCOUNT_ID,FIELD_NAME,FIELD_PROMPT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_SHOPPING_OPT_CONFIG WHERE SHOPPING_OPT_CONFIG_ID IN (");

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
            ShoppingOptConfigData x=null;
            while (rs.next()) {
                // build the object
                x=ShoppingOptConfigData.createValue();
                
                x.setShoppingOptConfigId(rs.getInt(1));
                x.setAccountId(rs.getInt(2));
                x.setFieldName(rs.getString(3));
                x.setFieldPrompt(rs.getString(4));
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
     * Gets a ShoppingOptConfigDataVector object of all
     * ShoppingOptConfigData objects in the database.
     * @param pCon An open database connection.
     * @return new ShoppingOptConfigDataVector()
     * @throws            SQLException
     */
    public static ShoppingOptConfigDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT SHOPPING_OPT_CONFIG_ID,ACCOUNT_ID,FIELD_NAME,FIELD_PROMPT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_SHOPPING_OPT_CONFIG";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ShoppingOptConfigDataVector v = new ShoppingOptConfigDataVector();
        ShoppingOptConfigData x = null;
        while (rs.next()) {
            // build the object
            x = ShoppingOptConfigData.createValue();
            
            x.setShoppingOptConfigId(rs.getInt(1));
            x.setAccountId(rs.getInt(2));
            x.setFieldName(rs.getString(3));
            x.setFieldPrompt(rs.getString(4));
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
     * ShoppingOptConfigData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT SHOPPING_OPT_CONFIG_ID FROM CLW_SHOPPING_OPT_CONFIG");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SHOPPING_OPT_CONFIG");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SHOPPING_OPT_CONFIG");
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
     * Inserts a ShoppingOptConfigData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingOptConfigData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ShoppingOptConfigData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ShoppingOptConfigData insert(Connection pCon, ShoppingOptConfigData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_SHOPPING_OPT_CONFIG_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_SHOPPING_OPT_CONFIG_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setShoppingOptConfigId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_SHOPPING_OPT_CONFIG (SHOPPING_OPT_CONFIG_ID,ACCOUNT_ID,FIELD_NAME,FIELD_PROMPT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getShoppingOptConfigId());
        pstmt.setInt(2,pData.getAccountId());
        pstmt.setString(3,pData.getFieldName());
        pstmt.setString(4,pData.getFieldPrompt());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHOPPING_OPT_CONFIG_ID="+pData.getShoppingOptConfigId());
            log.debug("SQL:   ACCOUNT_ID="+pData.getAccountId());
            log.debug("SQL:   FIELD_NAME="+pData.getFieldName());
            log.debug("SQL:   FIELD_PROMPT="+pData.getFieldPrompt());
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
        pData.setShoppingOptConfigId(0);
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
     * Updates a ShoppingOptConfigData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingOptConfigData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ShoppingOptConfigData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_SHOPPING_OPT_CONFIG SET ACCOUNT_ID = ?,FIELD_NAME = ?,FIELD_PROMPT = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE SHOPPING_OPT_CONFIG_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getAccountId());
        pstmt.setString(i++,pData.getFieldName());
        pstmt.setString(i++,pData.getFieldPrompt());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getShoppingOptConfigId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ACCOUNT_ID="+pData.getAccountId());
            log.debug("SQL:   FIELD_NAME="+pData.getFieldName());
            log.debug("SQL:   FIELD_PROMPT="+pData.getFieldPrompt());
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
     * Deletes a ShoppingOptConfigData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pShoppingOptConfigId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pShoppingOptConfigId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_SHOPPING_OPT_CONFIG WHERE SHOPPING_OPT_CONFIG_ID = " + pShoppingOptConfigId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ShoppingOptConfigData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_SHOPPING_OPT_CONFIG");
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
     * Inserts a ShoppingOptConfigData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingOptConfigData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ShoppingOptConfigData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_SHOPPING_OPT_CONFIG (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "SHOPPING_OPT_CONFIG_ID,ACCOUNT_ID,FIELD_NAME,FIELD_PROMPT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getShoppingOptConfigId());
        pstmt.setInt(2+4,pData.getAccountId());
        pstmt.setString(3+4,pData.getFieldName());
        pstmt.setString(4+4,pData.getFieldPrompt());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ShoppingOptConfigData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingOptConfigData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ShoppingOptConfigData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ShoppingOptConfigData insert(Connection pCon, ShoppingOptConfigData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ShoppingOptConfigData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingOptConfigData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ShoppingOptConfigData pData, boolean pLogFl)
        throws SQLException {
        ShoppingOptConfigData oldData = null;
        if(pLogFl) {
          int id = pData.getShoppingOptConfigId();
          try {
          oldData = ShoppingOptConfigDataAccess.select(pCon,id);
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
     * Deletes a ShoppingOptConfigData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pShoppingOptConfigId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pShoppingOptConfigId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_SHOPPING_OPT_CONFIG SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_SHOPPING_OPT_CONFIG d WHERE SHOPPING_OPT_CONFIG_ID = " + pShoppingOptConfigId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pShoppingOptConfigId);
        return n;
     }

    /**
     * Deletes ShoppingOptConfigData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_SHOPPING_OPT_CONFIG SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_SHOPPING_OPT_CONFIG d ");
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

