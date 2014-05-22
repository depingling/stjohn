
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        UserNoteActivityDataAccess
 * Description:  This class is used to build access methods to the CLW_USER_NOTE_ACTIVITY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.UserNoteActivityData;
import com.cleanwise.service.api.value.UserNoteActivityDataVector;
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
 * <code>UserNoteActivityDataAccess</code>
 */
public class UserNoteActivityDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(UserNoteActivityDataAccess.class.getName());

    /** <code>CLW_USER_NOTE_ACTIVITY</code> table name */
	/* Primary key: USER_NOTE_ACTIVITY_ID */
	
    public static final String CLW_USER_NOTE_ACTIVITY = "CLW_USER_NOTE_ACTIVITY";
    
    /** <code>USER_NOTE_ACTIVITY_ID</code> USER_NOTE_ACTIVITY_ID column of table CLW_USER_NOTE_ACTIVITY */
    public static final String USER_NOTE_ACTIVITY_ID = "USER_NOTE_ACTIVITY_ID";
    /** <code>USER_ID</code> USER_ID column of table CLW_USER_NOTE_ACTIVITY */
    public static final String USER_ID = "USER_ID";
    /** <code>NOTE_ID</code> NOTE_ID column of table CLW_USER_NOTE_ACTIVITY */
    public static final String NOTE_ID = "NOTE_ID";
    /** <code>NOTE_COUNTER</code> NOTE_COUNTER column of table CLW_USER_NOTE_ACTIVITY */
    public static final String NOTE_COUNTER = "NOTE_COUNTER";

    /**
     * Constructor.
     */
    public UserNoteActivityDataAccess()
    {
    }

    /**
     * Gets a UserNoteActivityData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pUserNoteActivityId The key requested.
     * @return new UserNoteActivityData()
     * @throws            SQLException
     */
    public static UserNoteActivityData select(Connection pCon, int pUserNoteActivityId)
        throws SQLException, DataNotFoundException {
        UserNoteActivityData x=null;
        String sql="SELECT USER_NOTE_ACTIVITY_ID,USER_ID,NOTE_ID,NOTE_COUNTER FROM CLW_USER_NOTE_ACTIVITY WHERE USER_NOTE_ACTIVITY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pUserNoteActivityId=" + pUserNoteActivityId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pUserNoteActivityId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=UserNoteActivityData.createValue();
            
            x.setUserNoteActivityId(rs.getInt(1));
            x.setUserId(rs.getInt(2));
            x.setNoteId(rs.getInt(3));
            x.setNoteCounter(rs.getInt(4));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("USER_NOTE_ACTIVITY_ID :" + pUserNoteActivityId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a UserNoteActivityDataVector object that consists
     * of UserNoteActivityData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new UserNoteActivityDataVector()
     * @throws            SQLException
     */
    public static UserNoteActivityDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a UserNoteActivityData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_USER_NOTE_ACTIVITY.USER_NOTE_ACTIVITY_ID,CLW_USER_NOTE_ACTIVITY.USER_ID,CLW_USER_NOTE_ACTIVITY.NOTE_ID,CLW_USER_NOTE_ACTIVITY.NOTE_COUNTER";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated UserNoteActivityData Object.
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
    *@returns a populated UserNoteActivityData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         UserNoteActivityData x = UserNoteActivityData.createValue();
         
         x.setUserNoteActivityId(rs.getInt(1+offset));
         x.setUserId(rs.getInt(2+offset));
         x.setNoteId(rs.getInt(3+offset));
         x.setNoteCounter(rs.getInt(4+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the UserNoteActivityData Object represents.
    */
    public int getColumnCount(){
        return 4;
    }

    /**
     * Gets a UserNoteActivityDataVector object that consists
     * of UserNoteActivityData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new UserNoteActivityDataVector()
     * @throws            SQLException
     */
    public static UserNoteActivityDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT USER_NOTE_ACTIVITY_ID,USER_ID,NOTE_ID,NOTE_COUNTER FROM CLW_USER_NOTE_ACTIVITY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_USER_NOTE_ACTIVITY.USER_NOTE_ACTIVITY_ID,CLW_USER_NOTE_ACTIVITY.USER_ID,CLW_USER_NOTE_ACTIVITY.NOTE_ID,CLW_USER_NOTE_ACTIVITY.NOTE_COUNTER FROM CLW_USER_NOTE_ACTIVITY");
                where = pCriteria.getSqlClause("CLW_USER_NOTE_ACTIVITY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_USER_NOTE_ACTIVITY.equals(otherTable)){
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
        UserNoteActivityDataVector v = new UserNoteActivityDataVector();
        while (rs.next()) {
            UserNoteActivityData x = UserNoteActivityData.createValue();
            
            x.setUserNoteActivityId(rs.getInt(1));
            x.setUserId(rs.getInt(2));
            x.setNoteId(rs.getInt(3));
            x.setNoteCounter(rs.getInt(4));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a UserNoteActivityDataVector object that consists
     * of UserNoteActivityData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for UserNoteActivityData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new UserNoteActivityDataVector()
     * @throws            SQLException
     */
    public static UserNoteActivityDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        UserNoteActivityDataVector v = new UserNoteActivityDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT USER_NOTE_ACTIVITY_ID,USER_ID,NOTE_ID,NOTE_COUNTER FROM CLW_USER_NOTE_ACTIVITY WHERE USER_NOTE_ACTIVITY_ID IN (");

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
            UserNoteActivityData x=null;
            while (rs.next()) {
                // build the object
                x=UserNoteActivityData.createValue();
                
                x.setUserNoteActivityId(rs.getInt(1));
                x.setUserId(rs.getInt(2));
                x.setNoteId(rs.getInt(3));
                x.setNoteCounter(rs.getInt(4));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a UserNoteActivityDataVector object of all
     * UserNoteActivityData objects in the database.
     * @param pCon An open database connection.
     * @return new UserNoteActivityDataVector()
     * @throws            SQLException
     */
    public static UserNoteActivityDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT USER_NOTE_ACTIVITY_ID,USER_ID,NOTE_ID,NOTE_COUNTER FROM CLW_USER_NOTE_ACTIVITY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        UserNoteActivityDataVector v = new UserNoteActivityDataVector();
        UserNoteActivityData x = null;
        while (rs.next()) {
            // build the object
            x = UserNoteActivityData.createValue();
            
            x.setUserNoteActivityId(rs.getInt(1));
            x.setUserId(rs.getInt(2));
            x.setNoteId(rs.getInt(3));
            x.setNoteCounter(rs.getInt(4));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * UserNoteActivityData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT USER_NOTE_ACTIVITY_ID FROM CLW_USER_NOTE_ACTIVITY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_USER_NOTE_ACTIVITY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_USER_NOTE_ACTIVITY");
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
     * Inserts a UserNoteActivityData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UserNoteActivityData object to insert.
     * @return new UserNoteActivityData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static UserNoteActivityData insert(Connection pCon, UserNoteActivityData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_USER_NOTE_ACTIVITY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_USER_NOTE_ACTIVITY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setUserNoteActivityId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_USER_NOTE_ACTIVITY (USER_NOTE_ACTIVITY_ID,USER_ID,NOTE_ID,NOTE_COUNTER) VALUES(?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pstmt.setInt(1,pData.getUserNoteActivityId());
        pstmt.setInt(2,pData.getUserId());
        if (pData.getNoteId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getNoteId());
        }

        pstmt.setInt(4,pData.getNoteCounter());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   USER_NOTE_ACTIVITY_ID="+pData.getUserNoteActivityId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   NOTE_ID="+pData.getNoteId());
            log.debug("SQL:   NOTE_COUNTER="+pData.getNoteCounter());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setUserNoteActivityId(0);
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
     * Updates a UserNoteActivityData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A UserNoteActivityData object to update. 
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, UserNoteActivityData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_USER_NOTE_ACTIVITY SET USER_ID = ?,NOTE_ID = ?,NOTE_COUNTER = ? WHERE USER_NOTE_ACTIVITY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        int i = 1;
        
        pstmt.setInt(i++,pData.getUserId());
        if (pData.getNoteId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getNoteId());
        }

        pstmt.setInt(i++,pData.getNoteCounter());
        pstmt.setInt(i++,pData.getUserNoteActivityId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   NOTE_ID="+pData.getNoteId());
            log.debug("SQL:   NOTE_COUNTER="+pData.getNoteCounter());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a UserNoteActivityData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pUserNoteActivityId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pUserNoteActivityId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_USER_NOTE_ACTIVITY WHERE USER_NOTE_ACTIVITY_ID = " + pUserNoteActivityId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes UserNoteActivityData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_USER_NOTE_ACTIVITY");
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
     * Inserts a UserNoteActivityData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UserNoteActivityData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, UserNoteActivityData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_USER_NOTE_ACTIVITY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "USER_NOTE_ACTIVITY_ID,USER_ID,NOTE_ID,NOTE_COUNTER) VALUES(?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getUserNoteActivityId());
        pstmt.setInt(2+4,pData.getUserId());
        if (pData.getNoteId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getNoteId());
        }

        pstmt.setInt(4+4,pData.getNoteCounter());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a UserNoteActivityData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A UserNoteActivityData object to insert.
     * @param pLogFl  Creates record in log table if true
     * @return new UserNoteActivityData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static UserNoteActivityData insert(Connection pCon, UserNoteActivityData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a UserNoteActivityData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A UserNoteActivityData object to update. 
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, UserNoteActivityData pData, boolean pLogFl)
        throws SQLException {
        UserNoteActivityData oldData = null;
        if(pLogFl) {
          int id = pData.getUserNoteActivityId();
          try {
          oldData = UserNoteActivityDataAccess.select(pCon,id);
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
     * Deletes a UserNoteActivityData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pUserNoteActivityId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pUserNoteActivityId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_USER_NOTE_ACTIVITY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_USER_NOTE_ACTIVITY d WHERE USER_NOTE_ACTIVITY_ID = " + pUserNoteActivityId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pUserNoteActivityId);
        return n;
     }

    /**
     * Deletes UserNoteActivityData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_USER_NOTE_ACTIVITY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_USER_NOTE_ACTIVITY d ");
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

