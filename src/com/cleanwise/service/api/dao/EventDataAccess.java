
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        EventDataAccess
 * Description:  This class is used to build access methods to the CLW_EVENT table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.EventData;
import com.cleanwise.service.api.value.EventDataVector;
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
 * <code>EventDataAccess</code>
 */
public class EventDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(EventDataAccess.class.getName());

    /** <code>CLW_EVENT</code> table name */
	/* Primary key: EVENT_ID */
	
    public static final String CLW_EVENT = "CLW_EVENT";
    
    /** <code>EVENT_ID</code> EVENT_ID column of table CLW_EVENT */
    public static final String EVENT_ID = "EVENT_ID";
    /** <code>TYPE</code> TYPE column of table CLW_EVENT */
    public static final String TYPE = "TYPE";
    /** <code>STATUS</code> STATUS column of table CLW_EVENT */
    public static final String STATUS = "STATUS";
    /** <code>CD</code> CD column of table CLW_EVENT */
    public static final String CD = "CD";
    /** <code>ATTEMPT</code> ATTEMPT column of table CLW_EVENT */
    public static final String ATTEMPT = "ATTEMPT";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_EVENT */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_EVENT */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_EVENT */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_EVENT */
    public static final String MOD_BY = "MOD_BY";
    /** <code>EVENT_PRIORITY</code> EVENT_PRIORITY column of table CLW_EVENT */
    public static final String EVENT_PRIORITY = "EVENT_PRIORITY";
    /** <code>PROCESS_TIME</code> PROCESS_TIME column of table CLW_EVENT */
    public static final String PROCESS_TIME = "PROCESS_TIME";

    /**
     * Constructor.
     */
    public EventDataAccess()
    {
    }

    /**
     * Gets a EventData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pEventId The key requested.
     * @return new EventData()
     * @throws            SQLException
     */
    public static EventData select(Connection pCon, int pEventId)
        throws SQLException, DataNotFoundException {
        EventData x=null;
        String sql="SELECT EVENT_ID,TYPE,STATUS,CD,ATTEMPT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,EVENT_PRIORITY,PROCESS_TIME FROM CLW_EVENT WHERE EVENT_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pEventId=" + pEventId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pEventId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=EventData.createValue();
            
            x.setEventId(rs.getInt(1));
            x.setType(rs.getString(2));
            x.setStatus(rs.getString(3));
            x.setCd(rs.getString(4));
            x.setAttempt(rs.getInt(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            x.setEventPriority(rs.getInt(10));
            x.setProcessTime(rs.getTimestamp(11));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("EVENT_ID :" + pEventId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a EventDataVector object that consists
     * of EventData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new EventDataVector()
     * @throws            SQLException
     */
    public static EventDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a EventData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_EVENT.EVENT_ID,CLW_EVENT.TYPE,CLW_EVENT.STATUS,CLW_EVENT.CD,CLW_EVENT.ATTEMPT,CLW_EVENT.ADD_DATE,CLW_EVENT.ADD_BY,CLW_EVENT.MOD_DATE,CLW_EVENT.MOD_BY,CLW_EVENT.EVENT_PRIORITY,CLW_EVENT.PROCESS_TIME";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated EventData Object.
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
    *@returns a populated EventData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         EventData x = EventData.createValue();
         
         x.setEventId(rs.getInt(1+offset));
         x.setType(rs.getString(2+offset));
         x.setStatus(rs.getString(3+offset));
         x.setCd(rs.getString(4+offset));
         x.setAttempt(rs.getInt(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         x.setEventPriority(rs.getInt(10+offset));
         x.setProcessTime(rs.getTimestamp(11+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the EventData Object represents.
    */
    public int getColumnCount(){
        return 11;
    }

    /**
     * Gets a EventDataVector object that consists
     * of EventData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new EventDataVector()
     * @throws            SQLException
     */
    public static EventDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT EVENT_ID,TYPE,STATUS,CD,ATTEMPT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,EVENT_PRIORITY,PROCESS_TIME FROM CLW_EVENT");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_EVENT.EVENT_ID,CLW_EVENT.TYPE,CLW_EVENT.STATUS,CLW_EVENT.CD,CLW_EVENT.ATTEMPT,CLW_EVENT.ADD_DATE,CLW_EVENT.ADD_BY,CLW_EVENT.MOD_DATE,CLW_EVENT.MOD_BY,CLW_EVENT.EVENT_PRIORITY,CLW_EVENT.PROCESS_TIME FROM CLW_EVENT");
                where = pCriteria.getSqlClause("CLW_EVENT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_EVENT.equals(otherTable)){
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
        EventDataVector v = new EventDataVector();
        while (rs.next()) {
            EventData x = EventData.createValue();
            
            x.setEventId(rs.getInt(1));
            x.setType(rs.getString(2));
            x.setStatus(rs.getString(3));
            x.setCd(rs.getString(4));
            x.setAttempt(rs.getInt(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            x.setEventPriority(rs.getInt(10));
            x.setProcessTime(rs.getTimestamp(11));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a EventDataVector object that consists
     * of EventData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for EventData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new EventDataVector()
     * @throws            SQLException
     */
    public static EventDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        EventDataVector v = new EventDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT EVENT_ID,TYPE,STATUS,CD,ATTEMPT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,EVENT_PRIORITY,PROCESS_TIME FROM CLW_EVENT WHERE EVENT_ID IN (");

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
            EventData x=null;
            while (rs.next()) {
                // build the object
                x=EventData.createValue();
                
                x.setEventId(rs.getInt(1));
                x.setType(rs.getString(2));
                x.setStatus(rs.getString(3));
                x.setCd(rs.getString(4));
                x.setAttempt(rs.getInt(5));
                x.setAddDate(rs.getTimestamp(6));
                x.setAddBy(rs.getString(7));
                x.setModDate(rs.getTimestamp(8));
                x.setModBy(rs.getString(9));
                x.setEventPriority(rs.getInt(10));
                x.setProcessTime(rs.getTimestamp(11));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a EventDataVector object of all
     * EventData objects in the database.
     * @param pCon An open database connection.
     * @return new EventDataVector()
     * @throws            SQLException
     */
    public static EventDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT EVENT_ID,TYPE,STATUS,CD,ATTEMPT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,EVENT_PRIORITY,PROCESS_TIME FROM CLW_EVENT";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        EventDataVector v = new EventDataVector();
        EventData x = null;
        while (rs.next()) {
            // build the object
            x = EventData.createValue();
            
            x.setEventId(rs.getInt(1));
            x.setType(rs.getString(2));
            x.setStatus(rs.getString(3));
            x.setCd(rs.getString(4));
            x.setAttempt(rs.getInt(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));
            x.setEventPriority(rs.getInt(10));
            x.setProcessTime(rs.getTimestamp(11));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * EventData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT EVENT_ID FROM CLW_EVENT");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT EVENT_ID FROM CLW_EVENT");
                where = pCriteria.getSqlClause("CLW_EVENT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_EVENT.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_EVENT");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_EVENT");
                where = pCriteria.getSqlClause("CLW_EVENT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_EVENT.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_EVENT");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_EVENT");
                where = pCriteria.getSqlClause("CLW_EVENT");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_EVENT.equals(otherTable)){
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
     * Inserts a EventData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A EventData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new EventData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static EventData insert(Connection pCon, EventData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_EVENT_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_EVENT_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setEventId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_EVENT (EVENT_ID,TYPE,STATUS,CD,ATTEMPT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,EVENT_PRIORITY,PROCESS_TIME) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getEventId());
        pstmt.setString(2,pData.getType());
        pstmt.setString(3,pData.getStatus());
        pstmt.setString(4,pData.getCd());
        pstmt.setInt(5,pData.getAttempt());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());
        pstmt.setInt(10,pData.getEventPriority());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getProcessTime()));

        if (log.isDebugEnabled()) {
            log.debug("SQL:   EVENT_ID="+pData.getEventId());
            log.debug("SQL:   TYPE="+pData.getType());
            log.debug("SQL:   STATUS="+pData.getStatus());
            log.debug("SQL:   CD="+pData.getCd());
            log.debug("SQL:   ATTEMPT="+pData.getAttempt());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   EVENT_PRIORITY="+pData.getEventPriority());
            log.debug("SQL:   PROCESS_TIME="+pData.getProcessTime());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setEventId(0);
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
     * Updates a EventData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A EventData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, EventData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_EVENT SET TYPE = ?,STATUS = ?,CD = ?,ATTEMPT = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,EVENT_PRIORITY = ?,PROCESS_TIME = ? WHERE EVENT_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getType());
        pstmt.setString(i++,pData.getStatus());
        pstmt.setString(i++,pData.getCd());
        pstmt.setInt(i++,pData.getAttempt());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getEventPriority());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getProcessTime()));
        pstmt.setInt(i++,pData.getEventId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   TYPE="+pData.getType());
            log.debug("SQL:   STATUS="+pData.getStatus());
            log.debug("SQL:   CD="+pData.getCd());
            log.debug("SQL:   ATTEMPT="+pData.getAttempt());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   EVENT_PRIORITY="+pData.getEventPriority());
            log.debug("SQL:   PROCESS_TIME="+pData.getProcessTime());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a EventData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pEventId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pEventId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_EVENT WHERE EVENT_ID = " + pEventId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes EventData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_EVENT");
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
     * Inserts a EventData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A EventData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, EventData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_EVENT (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "EVENT_ID,TYPE,STATUS,CD,ATTEMPT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,EVENT_PRIORITY,PROCESS_TIME) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getEventId());
        pstmt.setString(2+4,pData.getType());
        pstmt.setString(3+4,pData.getStatus());
        pstmt.setString(4+4,pData.getCd());
        pstmt.setInt(5+4,pData.getAttempt());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9+4,pData.getModBy());
        pstmt.setInt(10+4,pData.getEventPriority());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getProcessTime()));


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a EventData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A EventData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new EventData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static EventData insert(Connection pCon, EventData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a EventData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A EventData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, EventData pData, boolean pLogFl)
        throws SQLException {
        EventData oldData = null;
        if(pLogFl) {
          int id = pData.getEventId();
          try {
          oldData = EventDataAccess.select(pCon,id);
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
     * Deletes a EventData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pEventId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pEventId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_EVENT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_EVENT d WHERE EVENT_ID = " + pEventId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pEventId);
        return n;
     }

    /**
     * Deletes EventData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_EVENT SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_EVENT d ");
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

