
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        PasswordHistoryDataAccess
 * Description:  This class is used to build access methods to the CLW_PASSWORD_HISTORY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.PasswordHistoryData;
import com.cleanwise.service.api.value.PasswordHistoryDataVector;
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
 * <code>PasswordHistoryDataAccess</code>
 */
public class PasswordHistoryDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(PasswordHistoryDataAccess.class.getName());

    /** <code>CLW_PASSWORD_HISTORY</code> table name */
	/* Primary key: PASSWORD_HISTORY_ID */
	
    public static final String CLW_PASSWORD_HISTORY = "CLW_PASSWORD_HISTORY";
    
    /** <code>PASSWORD_HISTORY_ID</code> PASSWORD_HISTORY_ID column of table CLW_PASSWORD_HISTORY */
    public static final String PASSWORD_HISTORY_ID = "PASSWORD_HISTORY_ID";
    /** <code>USER_ID</code> USER_ID column of table CLW_PASSWORD_HISTORY */
    public static final String USER_ID = "USER_ID";
    /** <code>PASSWORD</code> PASSWORD column of table CLW_PASSWORD_HISTORY */
    public static final String PASSWORD = "PASSWORD";
    /** <code>NEED_INITIAL_RESET</code> NEED_INITIAL_RESET column of table CLW_PASSWORD_HISTORY */
    public static final String NEED_INITIAL_RESET = "NEED_INITIAL_RESET";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_PASSWORD_HISTORY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_PASSWORD_HISTORY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_PASSWORD_HISTORY */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_PASSWORD_HISTORY */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public PasswordHistoryDataAccess()
    {
    }

    /**
     * Gets a PasswordHistoryData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pPasswordHistoryId The key requested.
     * @return new PasswordHistoryData()
     * @throws            SQLException
     */
    public static PasswordHistoryData select(Connection pCon, int pPasswordHistoryId)
        throws SQLException, DataNotFoundException {
        PasswordHistoryData x=null;
        String sql="SELECT PASSWORD_HISTORY_ID,USER_ID,PASSWORD,NEED_INITIAL_RESET,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PASSWORD_HISTORY WHERE PASSWORD_HISTORY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pPasswordHistoryId=" + pPasswordHistoryId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pPasswordHistoryId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=PasswordHistoryData.createValue();
            
            x.setPasswordHistoryId(rs.getInt(1));
            x.setUserId(rs.getInt(2));
            x.setPassword(rs.getString(3));
            x.setNeedInitialReset(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("PASSWORD_HISTORY_ID :" + pPasswordHistoryId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a PasswordHistoryDataVector object that consists
     * of PasswordHistoryData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new PasswordHistoryDataVector()
     * @throws            SQLException
     */
    public static PasswordHistoryDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a PasswordHistoryData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_PASSWORD_HISTORY.PASSWORD_HISTORY_ID,CLW_PASSWORD_HISTORY.USER_ID,CLW_PASSWORD_HISTORY.PASSWORD,CLW_PASSWORD_HISTORY.NEED_INITIAL_RESET,CLW_PASSWORD_HISTORY.ADD_DATE,CLW_PASSWORD_HISTORY.ADD_BY,CLW_PASSWORD_HISTORY.MOD_DATE,CLW_PASSWORD_HISTORY.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated PasswordHistoryData Object.
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
    *@returns a populated PasswordHistoryData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         PasswordHistoryData x = PasswordHistoryData.createValue();
         
         x.setPasswordHistoryId(rs.getInt(1+offset));
         x.setUserId(rs.getInt(2+offset));
         x.setPassword(rs.getString(3+offset));
         x.setNeedInitialReset(rs.getString(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the PasswordHistoryData Object represents.
    */
    public int getColumnCount(){
        return 8;
    }

    /**
     * Gets a PasswordHistoryDataVector object that consists
     * of PasswordHistoryData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new PasswordHistoryDataVector()
     * @throws            SQLException
     */
    public static PasswordHistoryDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT PASSWORD_HISTORY_ID,USER_ID,PASSWORD,NEED_INITIAL_RESET,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PASSWORD_HISTORY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_PASSWORD_HISTORY.PASSWORD_HISTORY_ID,CLW_PASSWORD_HISTORY.USER_ID,CLW_PASSWORD_HISTORY.PASSWORD,CLW_PASSWORD_HISTORY.NEED_INITIAL_RESET,CLW_PASSWORD_HISTORY.ADD_DATE,CLW_PASSWORD_HISTORY.ADD_BY,CLW_PASSWORD_HISTORY.MOD_DATE,CLW_PASSWORD_HISTORY.MOD_BY FROM CLW_PASSWORD_HISTORY");
                where = pCriteria.getSqlClause("CLW_PASSWORD_HISTORY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PASSWORD_HISTORY.equals(otherTable)){
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
        PasswordHistoryDataVector v = new PasswordHistoryDataVector();
        while (rs.next()) {
            PasswordHistoryData x = PasswordHistoryData.createValue();
            
            x.setPasswordHistoryId(rs.getInt(1));
            x.setUserId(rs.getInt(2));
            x.setPassword(rs.getString(3));
            x.setNeedInitialReset(rs.getString(4));
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
     * Gets a PasswordHistoryDataVector object that consists
     * of PasswordHistoryData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for PasswordHistoryData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new PasswordHistoryDataVector()
     * @throws            SQLException
     */
    public static PasswordHistoryDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        PasswordHistoryDataVector v = new PasswordHistoryDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT PASSWORD_HISTORY_ID,USER_ID,PASSWORD,NEED_INITIAL_RESET,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PASSWORD_HISTORY WHERE PASSWORD_HISTORY_ID IN (");

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
            PasswordHistoryData x=null;
            while (rs.next()) {
                // build the object
                x=PasswordHistoryData.createValue();
                
                x.setPasswordHistoryId(rs.getInt(1));
                x.setUserId(rs.getInt(2));
                x.setPassword(rs.getString(3));
                x.setNeedInitialReset(rs.getString(4));
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
     * Gets a PasswordHistoryDataVector object of all
     * PasswordHistoryData objects in the database.
     * @param pCon An open database connection.
     * @return new PasswordHistoryDataVector()
     * @throws            SQLException
     */
    public static PasswordHistoryDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT PASSWORD_HISTORY_ID,USER_ID,PASSWORD,NEED_INITIAL_RESET,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_PASSWORD_HISTORY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        PasswordHistoryDataVector v = new PasswordHistoryDataVector();
        PasswordHistoryData x = null;
        while (rs.next()) {
            // build the object
            x = PasswordHistoryData.createValue();
            
            x.setPasswordHistoryId(rs.getInt(1));
            x.setUserId(rs.getInt(2));
            x.setPassword(rs.getString(3));
            x.setNeedInitialReset(rs.getString(4));
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
     * PasswordHistoryData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT PASSWORD_HISTORY_ID FROM CLW_PASSWORD_HISTORY");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT PASSWORD_HISTORY_ID FROM CLW_PASSWORD_HISTORY");
                where = pCriteria.getSqlClause("CLW_PASSWORD_HISTORY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PASSWORD_HISTORY.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PASSWORD_HISTORY");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PASSWORD_HISTORY");
                where = pCriteria.getSqlClause("CLW_PASSWORD_HISTORY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PASSWORD_HISTORY.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PASSWORD_HISTORY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_PASSWORD_HISTORY");
                where = pCriteria.getSqlClause("CLW_PASSWORD_HISTORY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_PASSWORD_HISTORY.equals(otherTable)){
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
     * Inserts a PasswordHistoryData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PasswordHistoryData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new PasswordHistoryData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PasswordHistoryData insert(Connection pCon, PasswordHistoryData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_PASSWORD_HISTORY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_PASSWORD_HISTORY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setPasswordHistoryId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_PASSWORD_HISTORY (PASSWORD_HISTORY_ID,USER_ID,PASSWORD,NEED_INITIAL_RESET,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getPasswordHistoryId());
        pstmt.setInt(2,pData.getUserId());
        pstmt.setString(3,pData.getPassword());
        pstmt.setString(4,pData.getNeedInitialReset());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   PASSWORD_HISTORY_ID="+pData.getPasswordHistoryId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   PASSWORD="+pData.getPassword());
            log.debug("SQL:   NEED_INITIAL_RESET="+pData.getNeedInitialReset());
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
        pData.setPasswordHistoryId(0);
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
     * Updates a PasswordHistoryData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PasswordHistoryData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PasswordHistoryData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_PASSWORD_HISTORY SET USER_ID = ?,PASSWORD = ?,NEED_INITIAL_RESET = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE PASSWORD_HISTORY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getUserId());
        pstmt.setString(i++,pData.getPassword());
        pstmt.setString(i++,pData.getNeedInitialReset());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getPasswordHistoryId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   PASSWORD="+pData.getPassword());
            log.debug("SQL:   NEED_INITIAL_RESET="+pData.getNeedInitialReset());
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
     * Deletes a PasswordHistoryData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPasswordHistoryId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPasswordHistoryId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_PASSWORD_HISTORY WHERE PASSWORD_HISTORY_ID = " + pPasswordHistoryId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes PasswordHistoryData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_PASSWORD_HISTORY");
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
     * Inserts a PasswordHistoryData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PasswordHistoryData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, PasswordHistoryData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_PASSWORD_HISTORY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "PASSWORD_HISTORY_ID,USER_ID,PASSWORD,NEED_INITIAL_RESET,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getPasswordHistoryId());
        pstmt.setInt(2+4,pData.getUserId());
        pstmt.setString(3+4,pData.getPassword());
        pstmt.setString(4+4,pData.getNeedInitialReset());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a PasswordHistoryData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A PasswordHistoryData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new PasswordHistoryData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static PasswordHistoryData insert(Connection pCon, PasswordHistoryData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a PasswordHistoryData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A PasswordHistoryData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, PasswordHistoryData pData, boolean pLogFl)
        throws SQLException {
        PasswordHistoryData oldData = null;
        if(pLogFl) {
          int id = pData.getPasswordHistoryId();
          try {
          oldData = PasswordHistoryDataAccess.select(pCon,id);
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
     * Deletes a PasswordHistoryData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pPasswordHistoryId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pPasswordHistoryId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_PASSWORD_HISTORY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PASSWORD_HISTORY d WHERE PASSWORD_HISTORY_ID = " + pPasswordHistoryId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pPasswordHistoryId);
        return n;
     }

    /**
     * Deletes PasswordHistoryData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_PASSWORD_HISTORY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_PASSWORD_HISTORY d ");
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

