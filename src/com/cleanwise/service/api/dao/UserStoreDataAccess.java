
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        UserStoreDataAccess
 * Description:  This class is used to build access methods to the ESW_USER_STORE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.UserStoreData;
import com.cleanwise.service.api.value.UserStoreDataVector;
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
 * <code>UserStoreDataAccess</code>
 */
public class UserStoreDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(UserStoreDataAccess.class.getName());

    /** <code>ESW_USER_STORE</code> table name */
	/* Primary key: USER_STORE_ID */
	
    public static final String ESW_USER_STORE = "ESW_USER_STORE";
    
    /** <code>USER_STORE_ID</code> USER_STORE_ID column of table ESW_USER_STORE */
    public static final String USER_STORE_ID = "USER_STORE_ID";
    /** <code>ALL_USER_ID</code> ALL_USER_ID column of table ESW_USER_STORE */
    public static final String ALL_USER_ID = "ALL_USER_ID";
    /** <code>ALL_STORE_ID</code> ALL_STORE_ID column of table ESW_USER_STORE */
    public static final String ALL_STORE_ID = "ALL_STORE_ID";
    /** <code>LAST_LOGIN_DATE</code> LAST_LOGIN_DATE column of table ESW_USER_STORE */
    public static final String LAST_LOGIN_DATE = "LAST_LOGIN_DATE";
    /** <code>ADD_DATE</code> ADD_DATE column of table ESW_USER_STORE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table ESW_USER_STORE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table ESW_USER_STORE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table ESW_USER_STORE */
    public static final String MOD_BY = "MOD_BY";
    /** <code>LOCALE_CD</code> LOCALE_CD column of table ESW_USER_STORE */
    public static final String LOCALE_CD = "LOCALE_CD";

    /**
     * Constructor.
     */
    public UserStoreDataAccess()
    {
    }

    /**
     * Gets a UserStoreData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pUserStoreId The key requested.
     * @return new UserStoreData()
     * @throws            SQLException
     */
    public static UserStoreData select(Connection pCon, int pUserStoreId)
        throws SQLException, DataNotFoundException {
        UserStoreData x=null;
        String sql="SELECT USER_STORE_ID,ALL_USER_ID,ALL_STORE_ID,LAST_LOGIN_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOCALE_CD FROM ESW_USER_STORE WHERE USER_STORE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pUserStoreId=" + pUserStoreId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pUserStoreId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=UserStoreData.createValue();
            
            x.setUserStoreId(rs.getInt(1));
            x.setAllUserId(rs.getInt(2));
            x.setAllStoreId(rs.getInt(3));
            x.setLastLoginDate(rs.getDate(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setLocaleCd(rs.getString(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("USER_STORE_ID :" + pUserStoreId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a UserStoreDataVector object that consists
     * of UserStoreData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new UserStoreDataVector()
     * @throws            SQLException
     */
    public static UserStoreDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a UserStoreData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "ESW_USER_STORE.USER_STORE_ID,ESW_USER_STORE.ALL_USER_ID,ESW_USER_STORE.ALL_STORE_ID,ESW_USER_STORE.LAST_LOGIN_DATE,ESW_USER_STORE.ADD_DATE,ESW_USER_STORE.ADD_BY,ESW_USER_STORE.MOD_DATE,ESW_USER_STORE.MOD_BY,ESW_USER_STORE.LOCALE_CD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated UserStoreData Object.
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
    *@returns a populated UserStoreData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         UserStoreData x = UserStoreData.createValue();
         
         x.setUserStoreId(rs.getInt(1+offset));
         x.setAllUserId(rs.getInt(2+offset));
         x.setAllStoreId(rs.getInt(3+offset));
         x.setLastLoginDate(rs.getDate(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         x.setLocaleCd(rs.getString(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the UserStoreData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a UserStoreDataVector object that consists
     * of UserStoreData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new UserStoreDataVector()
     * @throws            SQLException
     */
    public static UserStoreDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT USER_STORE_ID,ALL_USER_ID,ALL_STORE_ID,LAST_LOGIN_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOCALE_CD FROM ESW_USER_STORE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT ESW_USER_STORE.USER_STORE_ID,ESW_USER_STORE.ALL_USER_ID,ESW_USER_STORE.ALL_STORE_ID,ESW_USER_STORE.LAST_LOGIN_DATE,ESW_USER_STORE.ADD_DATE,ESW_USER_STORE.ADD_BY,ESW_USER_STORE.MOD_DATE,ESW_USER_STORE.MOD_BY,ESW_USER_STORE.LOCALE_CD FROM ESW_USER_STORE");
                where = pCriteria.getSqlClause("ESW_USER_STORE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!ESW_USER_STORE.equals(otherTable)){
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
        UserStoreDataVector v = new UserStoreDataVector();
        while (rs.next()) {
            UserStoreData x = UserStoreData.createValue();
            
            x.setUserStoreId(rs.getInt(1));
            x.setAllUserId(rs.getInt(2));
            x.setAllStoreId(rs.getInt(3));
            x.setLastLoginDate(rs.getDate(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setLocaleCd(rs.getString(9));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a UserStoreDataVector object that consists
     * of UserStoreData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for UserStoreData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new UserStoreDataVector()
     * @throws            SQLException
     */
    public static UserStoreDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        UserStoreDataVector v = new UserStoreDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT USER_STORE_ID,ALL_USER_ID,ALL_STORE_ID,LAST_LOGIN_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOCALE_CD FROM ESW_USER_STORE WHERE USER_STORE_ID IN (");

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
            UserStoreData x=null;
            while (rs.next()) {
                // build the object
                x=UserStoreData.createValue();
                
                x.setUserStoreId(rs.getInt(1));
                x.setAllUserId(rs.getInt(2));
                x.setAllStoreId(rs.getInt(3));
                x.setLastLoginDate(rs.getDate(4));
                x.setAddDate(rs.getTimestamp(5));
                x.setAddBy(rs.getString(6));
                x.setModDate(rs.getTimestamp(7));
                x.setModBy(rs.getString(8));
                x.setLocaleCd(rs.getString(9));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a UserStoreDataVector object of all
     * UserStoreData objects in the database.
     * @param pCon An open database connection.
     * @return new UserStoreDataVector()
     * @throws            SQLException
     */
    public static UserStoreDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT USER_STORE_ID,ALL_USER_ID,ALL_STORE_ID,LAST_LOGIN_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOCALE_CD FROM ESW_USER_STORE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        UserStoreDataVector v = new UserStoreDataVector();
        UserStoreData x = null;
        while (rs.next()) {
            // build the object
            x = UserStoreData.createValue();
            
            x.setUserStoreId(rs.getInt(1));
            x.setAllUserId(rs.getInt(2));
            x.setAllStoreId(rs.getInt(3));
            x.setLastLoginDate(rs.getDate(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setLocaleCd(rs.getString(9));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * UserStoreData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT USER_STORE_ID FROM ESW_USER_STORE");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT USER_STORE_ID FROM ESW_USER_STORE");
                where = pCriteria.getSqlClause("ESW_USER_STORE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!ESW_USER_STORE.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM ESW_USER_STORE");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM ESW_USER_STORE");
                where = pCriteria.getSqlClause("ESW_USER_STORE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!ESW_USER_STORE.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM ESW_USER_STORE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM ESW_USER_STORE");
                where = pCriteria.getSqlClause("ESW_USER_STORE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!ESW_USER_STORE.equals(otherTable)){
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
     * Inserts a UserStoreData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UserStoreData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new UserStoreData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static UserStoreData insert(Connection pCon, UserStoreData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT ESW_USER_STORE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT ESW_USER_STORE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setUserStoreId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO ESW_USER_STORE (USER_STORE_ID,ALL_USER_ID,ALL_STORE_ID,LAST_LOGIN_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOCALE_CD) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getUserStoreId());
        pstmt.setInt(2,pData.getAllUserId());
        pstmt.setInt(3,pData.getAllStoreId());
        pstmt.setDate(4,DBAccess.toSQLDate(pData.getLastLoginDate()));
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());
        pstmt.setString(9,pData.getLocaleCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   USER_STORE_ID="+pData.getUserStoreId());
            log.debug("SQL:   ALL_USER_ID="+pData.getAllUserId());
            log.debug("SQL:   ALL_STORE_ID="+pData.getAllStoreId());
            log.debug("SQL:   LAST_LOGIN_DATE="+pData.getLastLoginDate());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   LOCALE_CD="+pData.getLocaleCd());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setUserStoreId(0);
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
     * Updates a UserStoreData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A UserStoreData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, UserStoreData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE ESW_USER_STORE SET ALL_USER_ID = ?,ALL_STORE_ID = ?,LAST_LOGIN_DATE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,LOCALE_CD = ? WHERE USER_STORE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getAllUserId());
        pstmt.setInt(i++,pData.getAllStoreId());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getLastLoginDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getLocaleCd());
        pstmt.setInt(i++,pData.getUserStoreId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ALL_USER_ID="+pData.getAllUserId());
            log.debug("SQL:   ALL_STORE_ID="+pData.getAllStoreId());
            log.debug("SQL:   LAST_LOGIN_DATE="+pData.getLastLoginDate());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   LOCALE_CD="+pData.getLocaleCd());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a UserStoreData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pUserStoreId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pUserStoreId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM ESW_USER_STORE WHERE USER_STORE_ID = " + pUserStoreId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes UserStoreData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM ESW_USER_STORE");
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
     * Inserts a UserStoreData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UserStoreData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, UserStoreData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LESW_USER_STORE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "USER_STORE_ID,ALL_USER_ID,ALL_STORE_ID,LAST_LOGIN_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOCALE_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getUserStoreId());
        pstmt.setInt(2+4,pData.getAllUserId());
        pstmt.setInt(3+4,pData.getAllStoreId());
        pstmt.setDate(4+4,DBAccess.toSQLDate(pData.getLastLoginDate()));
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());
        pstmt.setString(9+4,pData.getLocaleCd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a UserStoreData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UserStoreData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new UserStoreData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static UserStoreData insert(Connection pCon, UserStoreData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a UserStoreData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A UserStoreData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, UserStoreData pData, boolean pLogFl)
        throws SQLException {
        UserStoreData oldData = null;
        if(pLogFl) {
          int id = pData.getUserStoreId();
          try {
          oldData = UserStoreDataAccess.select(pCon,id);
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
     * Deletes a UserStoreData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pUserStoreId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pUserStoreId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LESW_USER_STORE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM ESW_USER_STORE d WHERE USER_STORE_ID = " + pUserStoreId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pUserStoreId);
        return n;
     }

    /**
     * Deletes UserStoreData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LESW_USER_STORE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM ESW_USER_STORE d ");
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

