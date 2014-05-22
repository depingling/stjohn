
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        OrderMetaDataAccess
 * Description:  This class is used to build access methods to the CLW_ORDER_META table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.OrderMetaData;
import com.cleanwise.service.api.value.OrderMetaDataVector;
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
 * <code>OrderMetaDataAccess</code>
 */
public class OrderMetaDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(OrderMetaDataAccess.class.getName());

    /** <code>CLW_ORDER_META</code> table name */
	/* Primary key: ORDER_META_ID */
	
    public static final String CLW_ORDER_META = "CLW_ORDER_META";
    
    /** <code>ORDER_META_ID</code> ORDER_META_ID column of table CLW_ORDER_META */
    public static final String ORDER_META_ID = "ORDER_META_ID";
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_ORDER_META */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>NAME</code> NAME column of table CLW_ORDER_META */
    public static final String NAME = "NAME";
    /** <code>VALUE_NUM</code> VALUE_NUM column of table CLW_ORDER_META */
    public static final String VALUE_NUM = "VALUE_NUM";
    /** <code>CLW_VALUE</code> CLW_VALUE column of table CLW_ORDER_META */
    public static final String CLW_VALUE = "CLW_VALUE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ORDER_META */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ORDER_META */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ORDER_META */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ORDER_META */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public OrderMetaDataAccess()
    {
    }

    /**
     * Gets a OrderMetaData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pOrderMetaId The key requested.
     * @return new OrderMetaData()
     * @throws            SQLException
     */
    public static OrderMetaData select(Connection pCon, int pOrderMetaId)
        throws SQLException, DataNotFoundException {
        OrderMetaData x=null;
        String sql="SELECT ORDER_META_ID,ORDER_ID,NAME,VALUE_NUM,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_META WHERE ORDER_META_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pOrderMetaId=" + pOrderMetaId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pOrderMetaId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=OrderMetaData.createValue();
            
            x.setOrderMetaId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setName(rs.getString(3));
            x.setValueNum(rs.getInt(4));
            x.setValue(rs.getString(5));
            x.setAddDate(rs.getTimestamp(6));
            x.setAddBy(rs.getString(7));
            x.setModDate(rs.getTimestamp(8));
            x.setModBy(rs.getString(9));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ORDER_META_ID :" + pOrderMetaId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a OrderMetaDataVector object that consists
     * of OrderMetaData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new OrderMetaDataVector()
     * @throws            SQLException
     */
    public static OrderMetaDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a OrderMetaData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ORDER_META.ORDER_META_ID,CLW_ORDER_META.ORDER_ID,CLW_ORDER_META.NAME,CLW_ORDER_META.VALUE_NUM,CLW_ORDER_META.CLW_VALUE,CLW_ORDER_META.ADD_DATE,CLW_ORDER_META.ADD_BY,CLW_ORDER_META.MOD_DATE,CLW_ORDER_META.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated OrderMetaData Object.
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
    *@returns a populated OrderMetaData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         OrderMetaData x = OrderMetaData.createValue();
         
         x.setOrderMetaId(rs.getInt(1+offset));
         x.setOrderId(rs.getInt(2+offset));
         x.setName(rs.getString(3+offset));
         x.setValueNum(rs.getInt(4+offset));
         x.setValue(rs.getString(5+offset));
         x.setAddDate(rs.getTimestamp(6+offset));
         x.setAddBy(rs.getString(7+offset));
         x.setModDate(rs.getTimestamp(8+offset));
         x.setModBy(rs.getString(9+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the OrderMetaData Object represents.
    */
    public int getColumnCount(){
        return 9;
    }

    /**
     * Gets a OrderMetaDataVector object that consists
     * of OrderMetaData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new OrderMetaDataVector()
     * @throws            SQLException
     */
    public static OrderMetaDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ORDER_META_ID,ORDER_ID,NAME,VALUE_NUM,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_META");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ORDER_META.ORDER_META_ID,CLW_ORDER_META.ORDER_ID,CLW_ORDER_META.NAME,CLW_ORDER_META.VALUE_NUM,CLW_ORDER_META.CLW_VALUE,CLW_ORDER_META.ADD_DATE,CLW_ORDER_META.ADD_BY,CLW_ORDER_META.MOD_DATE,CLW_ORDER_META.MOD_BY FROM CLW_ORDER_META");
                where = pCriteria.getSqlClause("CLW_ORDER_META");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ORDER_META.equals(otherTable)){
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
        OrderMetaDataVector v = new OrderMetaDataVector();
        while (rs.next()) {
            OrderMetaData x = OrderMetaData.createValue();
            
            x.setOrderMetaId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setName(rs.getString(3));
            x.setValueNum(rs.getInt(4));
            x.setValue(rs.getString(5));
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
     * Gets a OrderMetaDataVector object that consists
     * of OrderMetaData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for OrderMetaData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new OrderMetaDataVector()
     * @throws            SQLException
     */
    public static OrderMetaDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        OrderMetaDataVector v = new OrderMetaDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_META_ID,ORDER_ID,NAME,VALUE_NUM,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_META WHERE ORDER_META_ID IN (");

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
            OrderMetaData x=null;
            while (rs.next()) {
                // build the object
                x=OrderMetaData.createValue();
                
                x.setOrderMetaId(rs.getInt(1));
                x.setOrderId(rs.getInt(2));
                x.setName(rs.getString(3));
                x.setValueNum(rs.getInt(4));
                x.setValue(rs.getString(5));
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
     * Gets a OrderMetaDataVector object of all
     * OrderMetaData objects in the database.
     * @param pCon An open database connection.
     * @return new OrderMetaDataVector()
     * @throws            SQLException
     */
    public static OrderMetaDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ORDER_META_ID,ORDER_ID,NAME,VALUE_NUM,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_META";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        OrderMetaDataVector v = new OrderMetaDataVector();
        OrderMetaData x = null;
        while (rs.next()) {
            // build the object
            x = OrderMetaData.createValue();
            
            x.setOrderMetaId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setName(rs.getString(3));
            x.setValueNum(rs.getInt(4));
            x.setValue(rs.getString(5));
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
     * OrderMetaData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_META_ID FROM CLW_ORDER_META");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_META");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_META");
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
     * Inserts a OrderMetaData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderMetaData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new OrderMetaData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderMetaData insert(Connection pCon, OrderMetaData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ORDER_META_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ORDER_META_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setOrderMetaId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ORDER_META (ORDER_META_ID,ORDER_ID,NAME,VALUE_NUM,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getOrderMetaId());
        pstmt.setInt(2,pData.getOrderId());
        pstmt.setString(3,pData.getName());
        pstmt.setInt(4,pData.getValueNum());
        pstmt.setString(5,pData.getValue());
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7,pData.getAddBy());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_META_ID="+pData.getOrderMetaId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   NAME="+pData.getName());
            log.debug("SQL:   VALUE_NUM="+pData.getValueNum());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
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
        pData.setOrderMetaId(0);
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
     * Updates a OrderMetaData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderMetaData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderMetaData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ORDER_META SET ORDER_ID = ?,NAME = ?,VALUE_NUM = ?,CLW_VALUE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE ORDER_META_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getOrderId());
        pstmt.setString(i++,pData.getName());
        pstmt.setInt(i++,pData.getValueNum());
        pstmt.setString(i++,pData.getValue());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getOrderMetaId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   NAME="+pData.getName());
            log.debug("SQL:   VALUE_NUM="+pData.getValueNum());
            log.debug("SQL:   CLW_VALUE="+pData.getValue());
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
     * Deletes a OrderMetaData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderMetaId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderMetaId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ORDER_META WHERE ORDER_META_ID = " + pOrderMetaId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes OrderMetaData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ORDER_META");
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
     * Inserts a OrderMetaData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderMetaData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, OrderMetaData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ORDER_META (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ORDER_META_ID,ORDER_ID,NAME,VALUE_NUM,CLW_VALUE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getOrderMetaId());
        pstmt.setInt(2+4,pData.getOrderId());
        pstmt.setString(3+4,pData.getName());
        pstmt.setInt(4+4,pData.getValueNum());
        pstmt.setString(5+4,pData.getValue());
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(7+4,pData.getAddBy());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(9+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a OrderMetaData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderMetaData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new OrderMetaData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderMetaData insert(Connection pCon, OrderMetaData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a OrderMetaData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderMetaData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderMetaData pData, boolean pLogFl)
        throws SQLException {
        OrderMetaData oldData = null;
        if(pLogFl) {
          int id = pData.getOrderMetaId();
          try {
          oldData = OrderMetaDataAccess.select(pCon,id);
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
     * Deletes a OrderMetaData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderMetaId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderMetaId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ORDER_META SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_META d WHERE ORDER_META_ID = " + pOrderMetaId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pOrderMetaId);
        return n;
     }

    /**
     * Deletes OrderMetaData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ORDER_META SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_META d ");
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

