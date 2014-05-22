
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ActivityDataAccess
 * Description:  This class is used to build access methods to the CLW_ACTIVITY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ActivityData;
import com.cleanwise.service.api.value.ActivityDataVector;
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
 * <code>ActivityDataAccess</code>
 */
public class ActivityDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ActivityDataAccess.class.getName());

    /** <code>CLW_ACTIVITY</code> table name */
	/* Primary key: ACTIVITY_ID */
	
    public static final String CLW_ACTIVITY = "CLW_ACTIVITY";
    
    /** <code>ACTIVITY_ID</code> ACTIVITY_ID column of table CLW_ACTIVITY */
    public static final String ACTIVITY_ID = "ACTIVITY_ID";
    /** <code>CLW_DATE</code> CLW_DATE column of table CLW_ACTIVITY */
    public static final String CLW_DATE = "CLW_DATE";
    /** <code>CLW_TIME</code> CLW_TIME column of table CLW_ACTIVITY */
    public static final String CLW_TIME = "CLW_TIME";
    /** <code>USER_ID</code> USER_ID column of table CLW_ACTIVITY */
    public static final String USER_ID = "USER_ID";
    /** <code>ACTIVITY</code> ACTIVITY column of table CLW_ACTIVITY */
    public static final String ACTIVITY = "ACTIVITY";

    /**
     * Constructor.
     */
    public ActivityDataAccess()
    {
    }

    /**
     * Gets a ActivityData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pActivityId The key requested.
     * @return new ActivityData()
     * @throws            SQLException
     */
    public static ActivityData select(Connection pCon, int pActivityId)
        throws SQLException, DataNotFoundException {
        ActivityData x=null;
        String sql="SELECT ACTIVITY_ID,CLW_DATE,CLW_TIME,USER_ID,ACTIVITY FROM CLW_ACTIVITY WHERE ACTIVITY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pActivityId=" + pActivityId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pActivityId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ActivityData.createValue();
            
            x.setActivityId(rs.getInt(1));
            x.setDate(rs.getDate(2));
            x.setTime(rs.getTimestamp(3));
            x.setUserId(rs.getInt(4));
            x.setActivity(rs.getString(5));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ACTIVITY_ID :" + pActivityId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ActivityDataVector object that consists
     * of ActivityData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ActivityDataVector()
     * @throws            SQLException
     */
    public static ActivityDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ActivityData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ACTIVITY.ACTIVITY_ID,CLW_ACTIVITY.CLW_DATE,CLW_ACTIVITY.CLW_TIME,CLW_ACTIVITY.USER_ID,CLW_ACTIVITY.ACTIVITY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ActivityData Object.
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
    *@returns a populated ActivityData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ActivityData x = ActivityData.createValue();
         
         x.setActivityId(rs.getInt(1+offset));
         x.setDate(rs.getDate(2+offset));
         x.setTime(rs.getTimestamp(3+offset));
         x.setUserId(rs.getInt(4+offset));
         x.setActivity(rs.getString(5+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ActivityData Object represents.
    */
    public int getColumnCount(){
        return 5;
    }

    /**
     * Gets a ActivityDataVector object that consists
     * of ActivityData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ActivityDataVector()
     * @throws            SQLException
     */
    public static ActivityDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ACTIVITY_ID,CLW_DATE,CLW_TIME,USER_ID,ACTIVITY FROM CLW_ACTIVITY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ACTIVITY.ACTIVITY_ID,CLW_ACTIVITY.CLW_DATE,CLW_ACTIVITY.CLW_TIME,CLW_ACTIVITY.USER_ID,CLW_ACTIVITY.ACTIVITY FROM CLW_ACTIVITY");
                where = pCriteria.getSqlClause("CLW_ACTIVITY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ACTIVITY.equals(otherTable)){
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
        ActivityDataVector v = new ActivityDataVector();
        while (rs.next()) {
            ActivityData x = ActivityData.createValue();
            
            x.setActivityId(rs.getInt(1));
            x.setDate(rs.getDate(2));
            x.setTime(rs.getTimestamp(3));
            x.setUserId(rs.getInt(4));
            x.setActivity(rs.getString(5));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ActivityDataVector object that consists
     * of ActivityData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ActivityData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ActivityDataVector()
     * @throws            SQLException
     */
    public static ActivityDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ActivityDataVector v = new ActivityDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ACTIVITY_ID,CLW_DATE,CLW_TIME,USER_ID,ACTIVITY FROM CLW_ACTIVITY WHERE ACTIVITY_ID IN (");

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
            ActivityData x=null;
            while (rs.next()) {
                // build the object
                x=ActivityData.createValue();
                
                x.setActivityId(rs.getInt(1));
                x.setDate(rs.getDate(2));
                x.setTime(rs.getTimestamp(3));
                x.setUserId(rs.getInt(4));
                x.setActivity(rs.getString(5));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ActivityDataVector object of all
     * ActivityData objects in the database.
     * @param pCon An open database connection.
     * @return new ActivityDataVector()
     * @throws            SQLException
     */
    public static ActivityDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ACTIVITY_ID,CLW_DATE,CLW_TIME,USER_ID,ACTIVITY FROM CLW_ACTIVITY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ActivityDataVector v = new ActivityDataVector();
        ActivityData x = null;
        while (rs.next()) {
            // build the object
            x = ActivityData.createValue();
            
            x.setActivityId(rs.getInt(1));
            x.setDate(rs.getDate(2));
            x.setTime(rs.getTimestamp(3));
            x.setUserId(rs.getInt(4));
            x.setActivity(rs.getString(5));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ActivityData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ACTIVITY_ID FROM CLW_ACTIVITY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ACTIVITY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ACTIVITY");
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
     * Inserts a ActivityData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ActivityData object to insert.
     * @return new ActivityData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ActivityData insert(Connection pCon, ActivityData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ACTIVITY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ACTIVITY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setActivityId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ACTIVITY (ACTIVITY_ID,CLW_DATE,CLW_TIME,USER_ID,ACTIVITY) VALUES(?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pstmt.setInt(1,pData.getActivityId());
        pstmt.setDate(2,DBAccess.toSQLDate(pData.getDate()));
        pstmt.setTimestamp(3,DBAccess.toSQLTimestamp(pData.getTime()));
        pstmt.setInt(4,pData.getUserId());
        pstmt.setString(5,pData.getActivity());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ACTIVITY_ID="+pData.getActivityId());
            log.debug("SQL:   CLW_DATE="+pData.getDate());
            log.debug("SQL:   CLW_TIME="+pData.getTime());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   ACTIVITY="+pData.getActivity());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setActivityId(0);
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
     * Updates a ActivityData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ActivityData object to update. 
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ActivityData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ACTIVITY SET CLW_DATE = ?,CLW_TIME = ?,USER_ID = ?,ACTIVITY = ? WHERE ACTIVITY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        int i = 1;
        
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getTime()));
        pstmt.setInt(i++,pData.getUserId());
        pstmt.setString(i++,pData.getActivity());
        pstmt.setInt(i++,pData.getActivityId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CLW_DATE="+pData.getDate());
            log.debug("SQL:   CLW_TIME="+pData.getTime());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   ACTIVITY="+pData.getActivity());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ActivityData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pActivityId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pActivityId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ACTIVITY WHERE ACTIVITY_ID = " + pActivityId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ActivityData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ACTIVITY");
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
     * Inserts a ActivityData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ActivityData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ActivityData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ACTIVITY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ACTIVITY_ID,CLW_DATE,CLW_TIME,USER_ID,ACTIVITY) VALUES(?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getActivityId());
        pstmt.setDate(2+4,DBAccess.toSQLDate(pData.getDate()));
        pstmt.setTimestamp(3+4,DBAccess.toSQLTimestamp(pData.getTime()));
        pstmt.setInt(4+4,pData.getUserId());
        pstmt.setString(5+4,pData.getActivity());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ActivityData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ActivityData object to insert.
     * @param pLogFl  Creates record in log table if true
     * @return new ActivityData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ActivityData insert(Connection pCon, ActivityData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ActivityData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ActivityData object to update. 
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ActivityData pData, boolean pLogFl)
        throws SQLException {
        ActivityData oldData = null;
        if(pLogFl) {
          int id = pData.getActivityId();
          try {
          oldData = ActivityDataAccess.select(pCon,id);
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
     * Deletes a ActivityData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pActivityId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pActivityId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ACTIVITY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ACTIVITY d WHERE ACTIVITY_ID = " + pActivityId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pActivityId);
        return n;
     }

    /**
     * Deletes ActivityData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ACTIVITY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ACTIVITY d ");
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

