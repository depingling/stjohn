
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        JanitorClosetDataAccess
 * Description:  This class is used to build access methods to the CLW_JANITOR_CLOSET table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.JanitorClosetData;
import com.cleanwise.service.api.value.JanitorClosetDataVector;
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
 * <code>JanitorClosetDataAccess</code>
 */
public class JanitorClosetDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(JanitorClosetDataAccess.class.getName());

    /** <code>CLW_JANITOR_CLOSET</code> table name */
	/* Primary key: JANITOR_CLOSET_ID */
	
    public static final String CLW_JANITOR_CLOSET = "CLW_JANITOR_CLOSET";
    
    /** <code>JANITOR_CLOSET_ID</code> JANITOR_CLOSET_ID column of table CLW_JANITOR_CLOSET */
    public static final String JANITOR_CLOSET_ID = "JANITOR_CLOSET_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_JANITOR_CLOSET */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>USER_ID</code> USER_ID column of table CLW_JANITOR_CLOSET */
    public static final String USER_ID = "USER_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_JANITOR_CLOSET */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_JANITOR_CLOSET */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_JANITOR_CLOSET */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_JANITOR_CLOSET */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_JANITOR_CLOSET */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_JANITOR_CLOSET */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_JANITOR_CLOSET */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public JanitorClosetDataAccess()
    {
    }

    /**
     * Gets a JanitorClosetData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pJanitorClosetId The key requested.
     * @return new JanitorClosetData()
     * @throws            SQLException
     */
    public static JanitorClosetData select(Connection pCon, int pJanitorClosetId)
        throws SQLException, DataNotFoundException {
        JanitorClosetData x=null;
        String sql="SELECT JANITOR_CLOSET_ID,BUS_ENTITY_ID,USER_ID,ITEM_ID,ORDER_ID,SHORT_DESC,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_JANITOR_CLOSET WHERE JANITOR_CLOSET_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pJanitorClosetId=" + pJanitorClosetId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pJanitorClosetId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=JanitorClosetData.createValue();
            
            x.setJanitorClosetId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setUserId(rs.getInt(3));
            x.setItemId(rs.getInt(4));
            x.setOrderId(rs.getInt(5));
            x.setShortDesc(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("JANITOR_CLOSET_ID :" + pJanitorClosetId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a JanitorClosetDataVector object that consists
     * of JanitorClosetData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new JanitorClosetDataVector()
     * @throws            SQLException
     */
    public static JanitorClosetDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a JanitorClosetData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_JANITOR_CLOSET.JANITOR_CLOSET_ID,CLW_JANITOR_CLOSET.BUS_ENTITY_ID,CLW_JANITOR_CLOSET.USER_ID,CLW_JANITOR_CLOSET.ITEM_ID,CLW_JANITOR_CLOSET.ORDER_ID,CLW_JANITOR_CLOSET.SHORT_DESC,CLW_JANITOR_CLOSET.ADD_DATE,CLW_JANITOR_CLOSET.ADD_BY,CLW_JANITOR_CLOSET.MOD_DATE,CLW_JANITOR_CLOSET.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated JanitorClosetData Object.
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
    *@returns a populated JanitorClosetData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         JanitorClosetData x = JanitorClosetData.createValue();
         
         x.setJanitorClosetId(rs.getInt(1+offset));
         x.setBusEntityId(rs.getInt(2+offset));
         x.setUserId(rs.getInt(3+offset));
         x.setItemId(rs.getInt(4+offset));
         x.setOrderId(rs.getInt(5+offset));
         x.setShortDesc(rs.getString(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the JanitorClosetData Object represents.
    */
    public int getColumnCount(){
        return 10;
    }

    /**
     * Gets a JanitorClosetDataVector object that consists
     * of JanitorClosetData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new JanitorClosetDataVector()
     * @throws            SQLException
     */
    public static JanitorClosetDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT JANITOR_CLOSET_ID,BUS_ENTITY_ID,USER_ID,ITEM_ID,ORDER_ID,SHORT_DESC,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_JANITOR_CLOSET");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_JANITOR_CLOSET.JANITOR_CLOSET_ID,CLW_JANITOR_CLOSET.BUS_ENTITY_ID,CLW_JANITOR_CLOSET.USER_ID,CLW_JANITOR_CLOSET.ITEM_ID,CLW_JANITOR_CLOSET.ORDER_ID,CLW_JANITOR_CLOSET.SHORT_DESC,CLW_JANITOR_CLOSET.ADD_DATE,CLW_JANITOR_CLOSET.ADD_BY,CLW_JANITOR_CLOSET.MOD_DATE,CLW_JANITOR_CLOSET.MOD_BY FROM CLW_JANITOR_CLOSET");
                where = pCriteria.getSqlClause("CLW_JANITOR_CLOSET");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_JANITOR_CLOSET.equals(otherTable)){
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
        JanitorClosetDataVector v = new JanitorClosetDataVector();
        while (rs.next()) {
            JanitorClosetData x = JanitorClosetData.createValue();
            
            x.setJanitorClosetId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setUserId(rs.getInt(3));
            x.setItemId(rs.getInt(4));
            x.setOrderId(rs.getInt(5));
            x.setShortDesc(rs.getString(6));
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
     * Gets a JanitorClosetDataVector object that consists
     * of JanitorClosetData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for JanitorClosetData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new JanitorClosetDataVector()
     * @throws            SQLException
     */
    public static JanitorClosetDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        JanitorClosetDataVector v = new JanitorClosetDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT JANITOR_CLOSET_ID,BUS_ENTITY_ID,USER_ID,ITEM_ID,ORDER_ID,SHORT_DESC,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_JANITOR_CLOSET WHERE JANITOR_CLOSET_ID IN (");

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
            JanitorClosetData x=null;
            while (rs.next()) {
                // build the object
                x=JanitorClosetData.createValue();
                
                x.setJanitorClosetId(rs.getInt(1));
                x.setBusEntityId(rs.getInt(2));
                x.setUserId(rs.getInt(3));
                x.setItemId(rs.getInt(4));
                x.setOrderId(rs.getInt(5));
                x.setShortDesc(rs.getString(6));
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
     * Gets a JanitorClosetDataVector object of all
     * JanitorClosetData objects in the database.
     * @param pCon An open database connection.
     * @return new JanitorClosetDataVector()
     * @throws            SQLException
     */
    public static JanitorClosetDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT JANITOR_CLOSET_ID,BUS_ENTITY_ID,USER_ID,ITEM_ID,ORDER_ID,SHORT_DESC,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_JANITOR_CLOSET";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        JanitorClosetDataVector v = new JanitorClosetDataVector();
        JanitorClosetData x = null;
        while (rs.next()) {
            // build the object
            x = JanitorClosetData.createValue();
            
            x.setJanitorClosetId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setUserId(rs.getInt(3));
            x.setItemId(rs.getInt(4));
            x.setOrderId(rs.getInt(5));
            x.setShortDesc(rs.getString(6));
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
     * JanitorClosetData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT JANITOR_CLOSET_ID FROM CLW_JANITOR_CLOSET");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_JANITOR_CLOSET");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_JANITOR_CLOSET");
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
     * Inserts a JanitorClosetData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A JanitorClosetData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new JanitorClosetData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static JanitorClosetData insert(Connection pCon, JanitorClosetData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_JANITOR_CLOSET_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_JANITOR_CLOSET_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setJanitorClosetId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_JANITOR_CLOSET (JANITOR_CLOSET_ID,BUS_ENTITY_ID,USER_ID,ITEM_ID,ORDER_ID,SHORT_DESC,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getJanitorClosetId());
        pstmt.setInt(2,pData.getBusEntityId());
        pstmt.setInt(3,pData.getUserId());
        pstmt.setInt(4,pData.getItemId());
        pstmt.setInt(5,pData.getOrderId());
        pstmt.setString(6,pData.getShortDesc());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   JANITOR_CLOSET_ID="+pData.getJanitorClosetId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
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
        pData.setJanitorClosetId(0);
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
     * Updates a JanitorClosetData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A JanitorClosetData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, JanitorClosetData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_JANITOR_CLOSET SET BUS_ENTITY_ID = ?,USER_ID = ?,ITEM_ID = ?,ORDER_ID = ?,SHORT_DESC = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE JANITOR_CLOSET_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setInt(i++,pData.getUserId());
        pstmt.setInt(i++,pData.getItemId());
        pstmt.setInt(i++,pData.getOrderId());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getJanitorClosetId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   USER_ID="+pData.getUserId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
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
     * Deletes a JanitorClosetData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pJanitorClosetId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pJanitorClosetId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_JANITOR_CLOSET WHERE JANITOR_CLOSET_ID = " + pJanitorClosetId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes JanitorClosetData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_JANITOR_CLOSET");
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
     * Inserts a JanitorClosetData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A JanitorClosetData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, JanitorClosetData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_JANITOR_CLOSET (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "JANITOR_CLOSET_ID,BUS_ENTITY_ID,USER_ID,ITEM_ID,ORDER_ID,SHORT_DESC,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getJanitorClosetId());
        pstmt.setInt(2+4,pData.getBusEntityId());
        pstmt.setInt(3+4,pData.getUserId());
        pstmt.setInt(4+4,pData.getItemId());
        pstmt.setInt(5+4,pData.getOrderId());
        pstmt.setString(6+4,pData.getShortDesc());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a JanitorClosetData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A JanitorClosetData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new JanitorClosetData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static JanitorClosetData insert(Connection pCon, JanitorClosetData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a JanitorClosetData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A JanitorClosetData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, JanitorClosetData pData, boolean pLogFl)
        throws SQLException {
        JanitorClosetData oldData = null;
        if(pLogFl) {
          int id = pData.getJanitorClosetId();
          try {
          oldData = JanitorClosetDataAccess.select(pCon,id);
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
     * Deletes a JanitorClosetData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pJanitorClosetId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pJanitorClosetId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_JANITOR_CLOSET SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_JANITOR_CLOSET d WHERE JANITOR_CLOSET_ID = " + pJanitorClosetId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pJanitorClosetId);
        return n;
     }

    /**
     * Deletes JanitorClosetData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_JANITOR_CLOSET SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_JANITOR_CLOSET d ");
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

