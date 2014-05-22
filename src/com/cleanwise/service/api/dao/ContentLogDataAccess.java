
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ContentLogDataAccess
 * Description:  This class is used to build access methods to the CLW_CONTENT_LOG table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ContentLogData;
import com.cleanwise.service.api.value.ContentLogDataVector;
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
 * <code>ContentLogDataAccess</code>
 */
public class ContentLogDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ContentLogDataAccess.class.getName());

    /** <code>CLW_CONTENT_LOG</code> table name */
	/* Primary key: CONTENT_LOG_ID */
	
    public static final String CLW_CONTENT_LOG = "CLW_CONTENT_LOG";
    
    /** <code>CONTENT_LOG_ID</code> CONTENT_LOG_ID column of table CLW_CONTENT_LOG */
    public static final String CONTENT_LOG_ID = "CONTENT_LOG_ID";
    /** <code>CONTENT_ID</code> CONTENT_ID column of table CLW_CONTENT_LOG */
    public static final String CONTENT_ID = "CONTENT_ID";
    /** <code>CLW_DATE</code> CLW_DATE column of table CLW_CONTENT_LOG */
    public static final String CLW_DATE = "CLW_DATE";
    /** <code>CLW_TIME</code> CLW_TIME column of table CLW_CONTENT_LOG */
    public static final String CLW_TIME = "CLW_TIME";
    /** <code>CHANGE</code> CHANGE column of table CLW_CONTENT_LOG */
    public static final String CHANGE = "CHANGE";

    /**
     * Constructor.
     */
    public ContentLogDataAccess()
    {
    }

    /**
     * Gets a ContentLogData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pContentLogId The key requested.
     * @return new ContentLogData()
     * @throws            SQLException
     */
    public static ContentLogData select(Connection pCon, int pContentLogId)
        throws SQLException, DataNotFoundException {
        ContentLogData x=null;
        String sql="SELECT CONTENT_LOG_ID,CONTENT_ID,CLW_DATE,CLW_TIME,CHANGE FROM CLW_CONTENT_LOG WHERE CONTENT_LOG_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pContentLogId=" + pContentLogId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pContentLogId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ContentLogData.createValue();
            
            x.setContentLogId(rs.getInt(1));
            x.setContentId(rs.getInt(2));
            x.setDate(rs.getDate(3));
            x.setTime(rs.getTimestamp(4));
            x.setChange(rs.getString(5));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("CONTENT_LOG_ID :" + pContentLogId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ContentLogDataVector object that consists
     * of ContentLogData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ContentLogDataVector()
     * @throws            SQLException
     */
    public static ContentLogDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ContentLogData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_CONTENT_LOG.CONTENT_LOG_ID,CLW_CONTENT_LOG.CONTENT_ID,CLW_CONTENT_LOG.CLW_DATE,CLW_CONTENT_LOG.CLW_TIME,CLW_CONTENT_LOG.CHANGE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ContentLogData Object.
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
    *@returns a populated ContentLogData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ContentLogData x = ContentLogData.createValue();
         
         x.setContentLogId(rs.getInt(1+offset));
         x.setContentId(rs.getInt(2+offset));
         x.setDate(rs.getDate(3+offset));
         x.setTime(rs.getTimestamp(4+offset));
         x.setChange(rs.getString(5+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ContentLogData Object represents.
    */
    public int getColumnCount(){
        return 5;
    }

    /**
     * Gets a ContentLogDataVector object that consists
     * of ContentLogData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ContentLogDataVector()
     * @throws            SQLException
     */
    public static ContentLogDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT CONTENT_LOG_ID,CONTENT_ID,CLW_DATE,CLW_TIME,CHANGE FROM CLW_CONTENT_LOG");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_CONTENT_LOG.CONTENT_LOG_ID,CLW_CONTENT_LOG.CONTENT_ID,CLW_CONTENT_LOG.CLW_DATE,CLW_CONTENT_LOG.CLW_TIME,CLW_CONTENT_LOG.CHANGE FROM CLW_CONTENT_LOG");
                where = pCriteria.getSqlClause("CLW_CONTENT_LOG");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_CONTENT_LOG.equals(otherTable)){
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
        ContentLogDataVector v = new ContentLogDataVector();
        while (rs.next()) {
            ContentLogData x = ContentLogData.createValue();
            
            x.setContentLogId(rs.getInt(1));
            x.setContentId(rs.getInt(2));
            x.setDate(rs.getDate(3));
            x.setTime(rs.getTimestamp(4));
            x.setChange(rs.getString(5));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ContentLogDataVector object that consists
     * of ContentLogData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ContentLogData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ContentLogDataVector()
     * @throws            SQLException
     */
    public static ContentLogDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ContentLogDataVector v = new ContentLogDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT CONTENT_LOG_ID,CONTENT_ID,CLW_DATE,CLW_TIME,CHANGE FROM CLW_CONTENT_LOG WHERE CONTENT_LOG_ID IN (");

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
            ContentLogData x=null;
            while (rs.next()) {
                // build the object
                x=ContentLogData.createValue();
                
                x.setContentLogId(rs.getInt(1));
                x.setContentId(rs.getInt(2));
                x.setDate(rs.getDate(3));
                x.setTime(rs.getTimestamp(4));
                x.setChange(rs.getString(5));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ContentLogDataVector object of all
     * ContentLogData objects in the database.
     * @param pCon An open database connection.
     * @return new ContentLogDataVector()
     * @throws            SQLException
     */
    public static ContentLogDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT CONTENT_LOG_ID,CONTENT_ID,CLW_DATE,CLW_TIME,CHANGE FROM CLW_CONTENT_LOG";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ContentLogDataVector v = new ContentLogDataVector();
        ContentLogData x = null;
        while (rs.next()) {
            // build the object
            x = ContentLogData.createValue();
            
            x.setContentLogId(rs.getInt(1));
            x.setContentId(rs.getInt(2));
            x.setDate(rs.getDate(3));
            x.setTime(rs.getTimestamp(4));
            x.setChange(rs.getString(5));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ContentLogData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT CONTENT_LOG_ID FROM CLW_CONTENT_LOG");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CONTENT_LOG");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_CONTENT_LOG");
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
     * Inserts a ContentLogData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContentLogData object to insert.
     * @return new ContentLogData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ContentLogData insert(Connection pCon, ContentLogData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_CONTENT_LOG_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_CONTENT_LOG_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setContentLogId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_CONTENT_LOG (CONTENT_LOG_ID,CONTENT_ID,CLW_DATE,CLW_TIME,CHANGE) VALUES(?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pstmt.setInt(1,pData.getContentLogId());
        if (pData.getContentId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getContentId());
        }

        pstmt.setDate(3,DBAccess.toSQLDate(pData.getDate()));
        pstmt.setTimestamp(4,DBAccess.toSQLTimestamp(pData.getTime()));
        pstmt.setString(5,pData.getChange());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CONTENT_LOG_ID="+pData.getContentLogId());
            log.debug("SQL:   CONTENT_ID="+pData.getContentId());
            log.debug("SQL:   CLW_DATE="+pData.getDate());
            log.debug("SQL:   CLW_TIME="+pData.getTime());
            log.debug("SQL:   CHANGE="+pData.getChange());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setContentLogId(0);
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
     * Updates a ContentLogData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ContentLogData object to update. 
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ContentLogData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_CONTENT_LOG SET CONTENT_ID = ?,CLW_DATE = ?,CLW_TIME = ?,CHANGE = ? WHERE CONTENT_LOG_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        int i = 1;
        
        if (pData.getContentId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getContentId());
        }

        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getTime()));
        pstmt.setString(i++,pData.getChange());
        pstmt.setInt(i++,pData.getContentLogId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   CONTENT_ID="+pData.getContentId());
            log.debug("SQL:   CLW_DATE="+pData.getDate());
            log.debug("SQL:   CLW_TIME="+pData.getTime());
            log.debug("SQL:   CHANGE="+pData.getChange());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a ContentLogData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pContentLogId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pContentLogId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_CONTENT_LOG WHERE CONTENT_LOG_ID = " + pContentLogId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ContentLogData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_CONTENT_LOG");
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
     * Inserts a ContentLogData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContentLogData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ContentLogData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_CONTENT_LOG (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "CONTENT_LOG_ID,CONTENT_ID,CLW_DATE,CLW_TIME,CHANGE) VALUES(?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getContentLogId());
        if (pData.getContentId() == 0) {
            pstmt.setNull(2+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2+4,pData.getContentId());
        }

        pstmt.setDate(3+4,DBAccess.toSQLDate(pData.getDate()));
        pstmt.setTimestamp(4+4,DBAccess.toSQLTimestamp(pData.getTime()));
        pstmt.setString(5+4,pData.getChange());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ContentLogData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ContentLogData object to insert.
     * @param pLogFl  Creates record in log table if true
     * @return new ContentLogData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ContentLogData insert(Connection pCon, ContentLogData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ContentLogData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ContentLogData object to update. 
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ContentLogData pData, boolean pLogFl)
        throws SQLException {
        ContentLogData oldData = null;
        if(pLogFl) {
          int id = pData.getContentLogId();
          try {
          oldData = ContentLogDataAccess.select(pCon,id);
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
     * Deletes a ContentLogData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pContentLogId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pContentLogId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_CONTENT_LOG SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CONTENT_LOG d WHERE CONTENT_LOG_ID = " + pContentLogId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pContentLogId);
        return n;
     }

    /**
     * Deletes ContentLogData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_CONTENT_LOG SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_CONTENT_LOG d ");
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

