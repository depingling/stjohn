
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        MessageResourceDataAccess
 * Description:  This class is used to build access methods to the CLW_MESSAGE_RESOURCE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.MessageResourceData;
import com.cleanwise.service.api.value.MessageResourceDataVector;
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
 * <code>MessageResourceDataAccess</code>
 */
public class MessageResourceDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(MessageResourceDataAccess.class.getName());

    /** <code>CLW_MESSAGE_RESOURCE</code> table name */
	/* Primary key: MESSAGE_RESOURCE_ID */
	
    public static final String CLW_MESSAGE_RESOURCE = "CLW_MESSAGE_RESOURCE";
    
    /** <code>MESSAGE_RESOURCE_ID</code> MESSAGE_RESOURCE_ID column of table CLW_MESSAGE_RESOURCE */
    public static final String MESSAGE_RESOURCE_ID = "MESSAGE_RESOURCE_ID";
    /** <code>LOCALE</code> LOCALE column of table CLW_MESSAGE_RESOURCE */
    public static final String LOCALE = "LOCALE";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_MESSAGE_RESOURCE */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>NAME</code> NAME column of table CLW_MESSAGE_RESOURCE */
    public static final String NAME = "NAME";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_MESSAGE_RESOURCE */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_MESSAGE_RESOURCE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_MESSAGE_RESOURCE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_MESSAGE_RESOURCE */
    public static final String MOD_BY = "MOD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_MESSAGE_RESOURCE */
    public static final String MOD_DATE = "MOD_DATE";

    /**
     * Constructor.
     */
    public MessageResourceDataAccess()
    {
    }

    /**
     * Gets a MessageResourceData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pMessageResourceId The key requested.
     * @return new MessageResourceData()
     * @throws            SQLException
     */
    public static MessageResourceData select(Connection pCon, int pMessageResourceId)
        throws SQLException, DataNotFoundException {
        MessageResourceData x=null;
        String sql="SELECT MESSAGE_RESOURCE_ID,LOCALE,BUS_ENTITY_ID,NAME,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_MESSAGE_RESOURCE WHERE MESSAGE_RESOURCE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pMessageResourceId=" + pMessageResourceId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pMessageResourceId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=MessageResourceData.createValue();
            
            x.setMessageResourceId(rs.getInt(1));
            x.setLocale(rs.getString(2));
            x.setBusEntityId(rs.getInt(3));
            x.setName(rs.getString(4));
            x.setValue(rs.getString(5));
            x.setAddBy(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("MESSAGE_RESOURCE_ID :" + pMessageResourceId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a MessageResourceDataVector object that consists
     * of MessageResourceData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new MessageResourceDataVector()
     * @throws            SQLException
     */
    public static MessageResourceDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a MessageResourceData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_MESSAGE_RESOURCE.MESSAGE_RESOURCE_ID,CLW_MESSAGE_RESOURCE.LOCALE,CLW_MESSAGE_RESOURCE.BUS_ENTITY_ID,CLW_MESSAGE_RESOURCE.NAME,CLW_MESSAGE_RESOURCE.CLW_VALUE,CLW_MESSAGE_RESOURCE.ADD_BY,CLW_MESSAGE_RESOURCE.ADD_DATE,CLW_MESSAGE_RESOURCE.MOD_BY,CLW_MESSAGE_RESOURCE.MOD_DATE";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated MessageResourceData Object.
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
    *@returns a populated MessageResourceData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         MessageResourceData x = MessageResourceData.createValue();
         
         x.setMessageResourceId(rs.getInt(1+offset));
         x.setLocale(rs.getString(2+offset));
         x.setBusEntityId(rs.getInt(3+offset));
         x.setName(rs.getString(4+offset));
         x.setValue(rs.getString(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the MessageResourceData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a MessageResourceDataVector object that consists
     * of MessageResourceData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new MessageResourceDataVector()
     * @throws            SQLException
     */
    public static MessageResourceDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT MESSAGE_RESOURCE_ID,LOCALE,BUS_ENTITY_ID,NAME,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_MESSAGE_RESOURCE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_MESSAGE_RESOURCE.MESSAGE_RESOURCE_ID,CLW_MESSAGE_RESOURCE.LOCALE,CLW_MESSAGE_RESOURCE.BUS_ENTITY_ID,CLW_MESSAGE_RESOURCE.NAME,CLW_MESSAGE_RESOURCE.CLW_VALUE,CLW_MESSAGE_RESOURCE.ADD_BY,CLW_MESSAGE_RESOURCE.ADD_DATE,CLW_MESSAGE_RESOURCE.MOD_BY,CLW_MESSAGE_RESOURCE.MOD_DATE FROM CLW_MESSAGE_RESOURCE");
                where = pCriteria.getSqlClause("CLW_MESSAGE_RESOURCE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_MESSAGE_RESOURCE.equals(otherTable)){
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
        MessageResourceDataVector v = new MessageResourceDataVector();
        while (rs.next()) {
            MessageResourceData x = MessageResourceData.createValue();
            
            x.setMessageResourceId(rs.getInt(1));
            x.setLocale(rs.getString(2));
            x.setBusEntityId(rs.getInt(3));
            x.setName(rs.getString(4));
            x.setValue(rs.getString(5));
            x.setAddBy(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a MessageResourceDataVector object that consists
     * of MessageResourceData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for MessageResourceData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new MessageResourceDataVector()
     * @throws            SQLException
     */
    public static MessageResourceDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        MessageResourceDataVector v = new MessageResourceDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT MESSAGE_RESOURCE_ID,LOCALE,BUS_ENTITY_ID,NAME,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_MESSAGE_RESOURCE WHERE MESSAGE_RESOURCE_ID IN (");

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
            MessageResourceData x=null;
            while (rs.next()) {
                // build the object
                x=MessageResourceData.createValue();
                
                x.setMessageResourceId(rs.getInt(1));
                x.setLocale(rs.getString(2));
                x.setBusEntityId(rs.getInt(3));
                x.setName(rs.getString(4));
                x.setValue(rs.getString(5));
                x.setAddBy(rs.getString(6));
                x.setAddDate(rs.getTimestamp(7));
                x.setModBy(rs.getString(8));
                x.setModDate(rs.getTimestamp(9));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a MessageResourceDataVector object of all
     * MessageResourceData objects in the database.
     * @param pCon An open database connection.
     * @return new MessageResourceDataVector()
     * @throws            SQLException
     */
    public static MessageResourceDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT MESSAGE_RESOURCE_ID,LOCALE,BUS_ENTITY_ID,NAME,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE FROM CLW_MESSAGE_RESOURCE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        MessageResourceDataVector v = new MessageResourceDataVector();
        MessageResourceData x = null;
        while (rs.next()) {
            // build the object
            x = MessageResourceData.createValue();
            
            x.setMessageResourceId(rs.getInt(1));
            x.setLocale(rs.getString(2));
            x.setBusEntityId(rs.getInt(3));
            x.setName(rs.getString(4));
            x.setValue(rs.getString(5));
            x.setAddBy(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * MessageResourceData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT MESSAGE_RESOURCE_ID FROM CLW_MESSAGE_RESOURCE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_MESSAGE_RESOURCE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_MESSAGE_RESOURCE");
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
     * Inserts a MessageResourceData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A MessageResourceData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new MessageResourceData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static MessageResourceData insert(Connection pCon, MessageResourceData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_MESSAGE_RESOURCE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_MESSAGE_RESOURCE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setMessageResourceId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_MESSAGE_RESOURCE (MESSAGE_RESOURCE_ID,LOCALE,BUS_ENTITY_ID,NAME,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getMessageResourceId());
        pstmt.setString(2,pData.getLocale());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getBusEntityId());
        }

        pstmt.setString(4,pData.getName());
        pstmt.setString(5,pData.getValue());
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getModBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));

        if (log.isDebugEnabled()) {
            log.debug("SQL:   MESSAGE_RESOURCE_ID="+pData.getMessageResourceId());
            log.debug("SQL:   LOCALE="+pData.getLocale());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   NAME="+pData.getName());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setMessageResourceId(0);
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
     * Updates a MessageResourceData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A MessageResourceData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, MessageResourceData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_MESSAGE_RESOURCE SET LOCALE = ?,BUS_ENTITY_ID = ?,NAME = ?,CLW_VALUE = ?,ADD_BY = ?,ADD_DATE = ?,MOD_BY = ?,MOD_DATE = ? WHERE MESSAGE_RESOURCE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getLocale());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getBusEntityId());
        }

        pstmt.setString(i++,pData.getName());
        pstmt.setString(i++,pData.getValue());
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setInt(i++,pData.getMessageResourceId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   LOCALE="+pData.getLocale());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   NAME="+pData.getName());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a MessageResourceData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pMessageResourceId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pMessageResourceId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_MESSAGE_RESOURCE WHERE MESSAGE_RESOURCE_ID = " + pMessageResourceId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes MessageResourceData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_MESSAGE_RESOURCE");
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
     * Inserts a MessageResourceData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A MessageResourceData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, MessageResourceData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_MESSAGE_RESOURCE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "MESSAGE_RESOURCE_ID,LOCALE,BUS_ENTITY_ID,NAME,CLW_VALUE,ADD_BY,ADD_DATE,MOD_BY,MOD_DATE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getMessageResourceId());
        pstmt.setString(2+4,pData.getLocale());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getBusEntityId());
        }

        pstmt.setString(4+4,pData.getName());
        pstmt.setString(5+4,pData.getValue());
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getModBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a MessageResourceData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A MessageResourceData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new MessageResourceData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static MessageResourceData insert(Connection pCon, MessageResourceData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a MessageResourceData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A MessageResourceData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, MessageResourceData pData, boolean pLogFl)
        throws SQLException {
        MessageResourceData oldData = null;
        if(pLogFl) {
          int id = pData.getMessageResourceId();
          try {
          oldData = MessageResourceDataAccess.select(pCon,id);
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
     * Deletes a MessageResourceData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pMessageResourceId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pMessageResourceId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_MESSAGE_RESOURCE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_MESSAGE_RESOURCE d WHERE MESSAGE_RESOURCE_ID = " + pMessageResourceId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pMessageResourceId);
        return n;
     }

    /**
     * Deletes MessageResourceData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_MESSAGE_RESOURCE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_MESSAGE_RESOURCE d ");
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

