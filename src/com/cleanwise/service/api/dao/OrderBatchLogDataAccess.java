
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        OrderBatchLogDataAccess
 * Description:  This class is used to build access methods to the CLW_ORDER_BATCH_LOG table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.OrderBatchLogData;
import com.cleanwise.service.api.value.OrderBatchLogDataVector;
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
 * <code>OrderBatchLogDataAccess</code>
 */
public class OrderBatchLogDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(OrderBatchLogDataAccess.class.getName());

    /** <code>CLW_ORDER_BATCH_LOG</code> table name */
	/* Primary key: ORDER_BATCH_LOG_ID */
	
    public static final String CLW_ORDER_BATCH_LOG = "CLW_ORDER_BATCH_LOG";
    
    /** <code>ORDER_BATCH_LOG_ID</code> ORDER_BATCH_LOG_ID column of table CLW_ORDER_BATCH_LOG */
    public static final String ORDER_BATCH_LOG_ID = "ORDER_BATCH_LOG_ID";
    /** <code>ORDER_BATCH_TYPE_CD</code> ORDER_BATCH_TYPE_CD column of table CLW_ORDER_BATCH_LOG */
    public static final String ORDER_BATCH_TYPE_CD = "ORDER_BATCH_TYPE_CD";
    /** <code>ORDER_BATCH_STATUS_CD</code> ORDER_BATCH_STATUS_CD column of table CLW_ORDER_BATCH_LOG */
    public static final String ORDER_BATCH_STATUS_CD = "ORDER_BATCH_STATUS_CD";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_ORDER_BATCH_LOG */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>ORDER_NUM</code> ORDER_NUM column of table CLW_ORDER_BATCH_LOG */
    public static final String ORDER_NUM = "ORDER_NUM";
    /** <code>ORDER_FOR_DATE</code> ORDER_FOR_DATE column of table CLW_ORDER_BATCH_LOG */
    public static final String ORDER_FOR_DATE = "ORDER_FOR_DATE";
    /** <code>ORDER_DATE</code> ORDER_DATE column of table CLW_ORDER_BATCH_LOG */
    public static final String ORDER_DATE = "ORDER_DATE";
    /** <code>ORDER_SOURCE_ID</code> ORDER_SOURCE_ID column of table CLW_ORDER_BATCH_LOG */
    public static final String ORDER_SOURCE_ID = "ORDER_SOURCE_ID";
    /** <code>MESSAGE</code> MESSAGE column of table CLW_ORDER_BATCH_LOG */
    public static final String MESSAGE = "MESSAGE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ORDER_BATCH_LOG */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ORDER_BATCH_LOG */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ORDER_BATCH_LOG */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ORDER_BATCH_LOG */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public OrderBatchLogDataAccess()
    {
    }

    /**
     * Gets a OrderBatchLogData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pOrderBatchLogId The key requested.
     * @return new OrderBatchLogData()
     * @throws            SQLException
     */
    public static OrderBatchLogData select(Connection pCon, int pOrderBatchLogId)
        throws SQLException, DataNotFoundException {
        OrderBatchLogData x=null;
        String sql="SELECT ORDER_BATCH_LOG_ID,ORDER_BATCH_TYPE_CD,ORDER_BATCH_STATUS_CD,BUS_ENTITY_ID,ORDER_NUM,ORDER_FOR_DATE,ORDER_DATE,ORDER_SOURCE_ID,MESSAGE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_BATCH_LOG WHERE ORDER_BATCH_LOG_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pOrderBatchLogId=" + pOrderBatchLogId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pOrderBatchLogId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=OrderBatchLogData.createValue();
            
            x.setOrderBatchLogId(rs.getInt(1));
            x.setOrderBatchTypeCd(rs.getString(2));
            x.setOrderBatchStatusCd(rs.getString(3));
            x.setBusEntityId(rs.getInt(4));
            x.setOrderNum(rs.getString(5));
            x.setOrderForDate(rs.getDate(6));
            x.setOrderDate(rs.getDate(7));
            x.setOrderSourceId(rs.getInt(8));
            x.setMessage(rs.getString(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setAddBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setModBy(rs.getString(13));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ORDER_BATCH_LOG_ID :" + pOrderBatchLogId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a OrderBatchLogDataVector object that consists
     * of OrderBatchLogData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new OrderBatchLogDataVector()
     * @throws            SQLException
     */
    public static OrderBatchLogDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a OrderBatchLogData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ORDER_BATCH_LOG.ORDER_BATCH_LOG_ID,CLW_ORDER_BATCH_LOG.ORDER_BATCH_TYPE_CD,CLW_ORDER_BATCH_LOG.ORDER_BATCH_STATUS_CD,CLW_ORDER_BATCH_LOG.BUS_ENTITY_ID,CLW_ORDER_BATCH_LOG.ORDER_NUM,CLW_ORDER_BATCH_LOG.ORDER_FOR_DATE,CLW_ORDER_BATCH_LOG.ORDER_DATE,CLW_ORDER_BATCH_LOG.ORDER_SOURCE_ID,CLW_ORDER_BATCH_LOG.MESSAGE,CLW_ORDER_BATCH_LOG.ADD_DATE,CLW_ORDER_BATCH_LOG.ADD_BY,CLW_ORDER_BATCH_LOG.MOD_DATE,CLW_ORDER_BATCH_LOG.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated OrderBatchLogData Object.
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
    *@returns a populated OrderBatchLogData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         OrderBatchLogData x = OrderBatchLogData.createValue();
         
         x.setOrderBatchLogId(rs.getInt(1+offset));
         x.setOrderBatchTypeCd(rs.getString(2+offset));
         x.setOrderBatchStatusCd(rs.getString(3+offset));
         x.setBusEntityId(rs.getInt(4+offset));
         x.setOrderNum(rs.getString(5+offset));
         x.setOrderForDate(rs.getDate(6+offset));
         x.setOrderDate(rs.getDate(7+offset));
         x.setOrderSourceId(rs.getInt(8+offset));
         x.setMessage(rs.getString(9+offset));
         x.setAddDate(rs.getTimestamp(10+offset));
         x.setAddBy(rs.getString(11+offset));
         x.setModDate(rs.getTimestamp(12+offset));
         x.setModBy(rs.getString(13+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the OrderBatchLogData Object represents.
    */
    public int getColumnCount(){
        return 13;
    }

    /**
     * Gets a OrderBatchLogDataVector object that consists
     * of OrderBatchLogData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new OrderBatchLogDataVector()
     * @throws            SQLException
     */
    public static OrderBatchLogDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ORDER_BATCH_LOG_ID,ORDER_BATCH_TYPE_CD,ORDER_BATCH_STATUS_CD,BUS_ENTITY_ID,ORDER_NUM,ORDER_FOR_DATE,ORDER_DATE,ORDER_SOURCE_ID,MESSAGE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_BATCH_LOG");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ORDER_BATCH_LOG.ORDER_BATCH_LOG_ID,CLW_ORDER_BATCH_LOG.ORDER_BATCH_TYPE_CD,CLW_ORDER_BATCH_LOG.ORDER_BATCH_STATUS_CD,CLW_ORDER_BATCH_LOG.BUS_ENTITY_ID,CLW_ORDER_BATCH_LOG.ORDER_NUM,CLW_ORDER_BATCH_LOG.ORDER_FOR_DATE,CLW_ORDER_BATCH_LOG.ORDER_DATE,CLW_ORDER_BATCH_LOG.ORDER_SOURCE_ID,CLW_ORDER_BATCH_LOG.MESSAGE,CLW_ORDER_BATCH_LOG.ADD_DATE,CLW_ORDER_BATCH_LOG.ADD_BY,CLW_ORDER_BATCH_LOG.MOD_DATE,CLW_ORDER_BATCH_LOG.MOD_BY FROM CLW_ORDER_BATCH_LOG");
                where = pCriteria.getSqlClause("CLW_ORDER_BATCH_LOG");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ORDER_BATCH_LOG.equals(otherTable)){
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
        OrderBatchLogDataVector v = new OrderBatchLogDataVector();
        while (rs.next()) {
            OrderBatchLogData x = OrderBatchLogData.createValue();
            
            x.setOrderBatchLogId(rs.getInt(1));
            x.setOrderBatchTypeCd(rs.getString(2));
            x.setOrderBatchStatusCd(rs.getString(3));
            x.setBusEntityId(rs.getInt(4));
            x.setOrderNum(rs.getString(5));
            x.setOrderForDate(rs.getDate(6));
            x.setOrderDate(rs.getDate(7));
            x.setOrderSourceId(rs.getInt(8));
            x.setMessage(rs.getString(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setAddBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setModBy(rs.getString(13));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a OrderBatchLogDataVector object that consists
     * of OrderBatchLogData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for OrderBatchLogData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new OrderBatchLogDataVector()
     * @throws            SQLException
     */
    public static OrderBatchLogDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        OrderBatchLogDataVector v = new OrderBatchLogDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_BATCH_LOG_ID,ORDER_BATCH_TYPE_CD,ORDER_BATCH_STATUS_CD,BUS_ENTITY_ID,ORDER_NUM,ORDER_FOR_DATE,ORDER_DATE,ORDER_SOURCE_ID,MESSAGE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_BATCH_LOG WHERE ORDER_BATCH_LOG_ID IN (");

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
            OrderBatchLogData x=null;
            while (rs.next()) {
                // build the object
                x=OrderBatchLogData.createValue();
                
                x.setOrderBatchLogId(rs.getInt(1));
                x.setOrderBatchTypeCd(rs.getString(2));
                x.setOrderBatchStatusCd(rs.getString(3));
                x.setBusEntityId(rs.getInt(4));
                x.setOrderNum(rs.getString(5));
                x.setOrderForDate(rs.getDate(6));
                x.setOrderDate(rs.getDate(7));
                x.setOrderSourceId(rs.getInt(8));
                x.setMessage(rs.getString(9));
                x.setAddDate(rs.getTimestamp(10));
                x.setAddBy(rs.getString(11));
                x.setModDate(rs.getTimestamp(12));
                x.setModBy(rs.getString(13));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a OrderBatchLogDataVector object of all
     * OrderBatchLogData objects in the database.
     * @param pCon An open database connection.
     * @return new OrderBatchLogDataVector()
     * @throws            SQLException
     */
    public static OrderBatchLogDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ORDER_BATCH_LOG_ID,ORDER_BATCH_TYPE_CD,ORDER_BATCH_STATUS_CD,BUS_ENTITY_ID,ORDER_NUM,ORDER_FOR_DATE,ORDER_DATE,ORDER_SOURCE_ID,MESSAGE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_BATCH_LOG";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        OrderBatchLogDataVector v = new OrderBatchLogDataVector();
        OrderBatchLogData x = null;
        while (rs.next()) {
            // build the object
            x = OrderBatchLogData.createValue();
            
            x.setOrderBatchLogId(rs.getInt(1));
            x.setOrderBatchTypeCd(rs.getString(2));
            x.setOrderBatchStatusCd(rs.getString(3));
            x.setBusEntityId(rs.getInt(4));
            x.setOrderNum(rs.getString(5));
            x.setOrderForDate(rs.getDate(6));
            x.setOrderDate(rs.getDate(7));
            x.setOrderSourceId(rs.getInt(8));
            x.setMessage(rs.getString(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setAddBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setModBy(rs.getString(13));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * OrderBatchLogData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_BATCH_LOG_ID FROM CLW_ORDER_BATCH_LOG");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_BATCH_LOG");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_BATCH_LOG");
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
     * Inserts a OrderBatchLogData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderBatchLogData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new OrderBatchLogData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderBatchLogData insert(Connection pCon, OrderBatchLogData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ORDER_BATCH_LOG_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ORDER_BATCH_LOG_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setOrderBatchLogId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ORDER_BATCH_LOG (ORDER_BATCH_LOG_ID,ORDER_BATCH_TYPE_CD,ORDER_BATCH_STATUS_CD,BUS_ENTITY_ID,ORDER_NUM,ORDER_FOR_DATE,ORDER_DATE,ORDER_SOURCE_ID,MESSAGE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getOrderBatchLogId());
        pstmt.setString(2,pData.getOrderBatchTypeCd());
        pstmt.setString(3,pData.getOrderBatchStatusCd());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4,pData.getBusEntityId());
        }

        pstmt.setString(5,pData.getOrderNum());
        pstmt.setDate(6,DBAccess.toSQLDate(pData.getOrderForDate()));
        pstmt.setDate(7,DBAccess.toSQLDate(pData.getOrderDate()));
        pstmt.setInt(8,pData.getOrderSourceId());
        pstmt.setString(9,pData.getMessage());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(11,pData.getAddBy());
        pstmt.setTimestamp(12,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(13,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_BATCH_LOG_ID="+pData.getOrderBatchLogId());
            log.debug("SQL:   ORDER_BATCH_TYPE_CD="+pData.getOrderBatchTypeCd());
            log.debug("SQL:   ORDER_BATCH_STATUS_CD="+pData.getOrderBatchStatusCd());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   ORDER_NUM="+pData.getOrderNum());
            log.debug("SQL:   ORDER_FOR_DATE="+pData.getOrderForDate());
            log.debug("SQL:   ORDER_DATE="+pData.getOrderDate());
            log.debug("SQL:   ORDER_SOURCE_ID="+pData.getOrderSourceId());
            log.debug("SQL:   MESSAGE="+pData.getMessage());
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
        pData.setOrderBatchLogId(0);
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
     * Updates a OrderBatchLogData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderBatchLogData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderBatchLogData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ORDER_BATCH_LOG SET ORDER_BATCH_TYPE_CD = ?,ORDER_BATCH_STATUS_CD = ?,BUS_ENTITY_ID = ?,ORDER_NUM = ?,ORDER_FOR_DATE = ?,ORDER_DATE = ?,ORDER_SOURCE_ID = ?,MESSAGE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE ORDER_BATCH_LOG_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getOrderBatchTypeCd());
        pstmt.setString(i++,pData.getOrderBatchStatusCd());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getBusEntityId());
        }

        pstmt.setString(i++,pData.getOrderNum());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getOrderForDate()));
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getOrderDate()));
        pstmt.setInt(i++,pData.getOrderSourceId());
        pstmt.setString(i++,pData.getMessage());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getOrderBatchLogId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_BATCH_TYPE_CD="+pData.getOrderBatchTypeCd());
            log.debug("SQL:   ORDER_BATCH_STATUS_CD="+pData.getOrderBatchStatusCd());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   ORDER_NUM="+pData.getOrderNum());
            log.debug("SQL:   ORDER_FOR_DATE="+pData.getOrderForDate());
            log.debug("SQL:   ORDER_DATE="+pData.getOrderDate());
            log.debug("SQL:   ORDER_SOURCE_ID="+pData.getOrderSourceId());
            log.debug("SQL:   MESSAGE="+pData.getMessage());
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
     * Deletes a OrderBatchLogData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderBatchLogId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderBatchLogId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ORDER_BATCH_LOG WHERE ORDER_BATCH_LOG_ID = " + pOrderBatchLogId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes OrderBatchLogData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ORDER_BATCH_LOG");
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
     * Inserts a OrderBatchLogData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderBatchLogData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, OrderBatchLogData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ORDER_BATCH_LOG (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ORDER_BATCH_LOG_ID,ORDER_BATCH_TYPE_CD,ORDER_BATCH_STATUS_CD,BUS_ENTITY_ID,ORDER_NUM,ORDER_FOR_DATE,ORDER_DATE,ORDER_SOURCE_ID,MESSAGE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getOrderBatchLogId());
        pstmt.setString(2+4,pData.getOrderBatchTypeCd());
        pstmt.setString(3+4,pData.getOrderBatchStatusCd());
        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(4+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4+4,pData.getBusEntityId());
        }

        pstmt.setString(5+4,pData.getOrderNum());
        pstmt.setDate(6+4,DBAccess.toSQLDate(pData.getOrderForDate()));
        pstmt.setDate(7+4,DBAccess.toSQLDate(pData.getOrderDate()));
        pstmt.setInt(8+4,pData.getOrderSourceId());
        pstmt.setString(9+4,pData.getMessage());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(11+4,pData.getAddBy());
        pstmt.setTimestamp(12+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(13+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a OrderBatchLogData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderBatchLogData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new OrderBatchLogData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderBatchLogData insert(Connection pCon, OrderBatchLogData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a OrderBatchLogData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderBatchLogData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderBatchLogData pData, boolean pLogFl)
        throws SQLException {
        OrderBatchLogData oldData = null;
        if(pLogFl) {
          int id = pData.getOrderBatchLogId();
          try {
          oldData = OrderBatchLogDataAccess.select(pCon,id);
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
     * Deletes a OrderBatchLogData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderBatchLogId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderBatchLogId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ORDER_BATCH_LOG SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_BATCH_LOG d WHERE ORDER_BATCH_LOG_ID = " + pOrderBatchLogId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pOrderBatchLogId);
        return n;
     }

    /**
     * Deletes OrderBatchLogData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ORDER_BATCH_LOG SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_BATCH_LOG d ");
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

