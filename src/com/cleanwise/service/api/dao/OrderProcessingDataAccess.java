
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        OrderProcessingDataAccess
 * Description:  This class is used to build access methods to the CLW_ORDER_PROCESSING table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.OrderProcessingData;
import com.cleanwise.service.api.value.OrderProcessingDataVector;
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
 * <code>OrderProcessingDataAccess</code>
 */
public class OrderProcessingDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(OrderProcessingDataAccess.class.getName());

    /** <code>CLW_ORDER_PROCESSING</code> table name */
	/* Primary key: ORDER_PROCESSING_ID */
	
    public static final String CLW_ORDER_PROCESSING = "CLW_ORDER_PROCESSING";
    
    /** <code>ORDER_PROCESSING_ID</code> ORDER_PROCESSING_ID column of table CLW_ORDER_PROCESSING */
    public static final String ORDER_PROCESSING_ID = "ORDER_PROCESSING_ID";
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_ORDER_PROCESSING */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>ORDER_ITEM_ID</code> ORDER_ITEM_ID column of table CLW_ORDER_PROCESSING */
    public static final String ORDER_ITEM_ID = "ORDER_ITEM_ID";
    /** <code>INVOICE_ID</code> INVOICE_ID column of table CLW_ORDER_PROCESSING */
    public static final String INVOICE_ID = "INVOICE_ID";
    /** <code>ORDER_PROCESSING_DATE</code> ORDER_PROCESSING_DATE column of table CLW_ORDER_PROCESSING */
    public static final String ORDER_PROCESSING_DATE = "ORDER_PROCESSING_DATE";
    /** <code>ORDER_PROCESSING_TIME</code> ORDER_PROCESSING_TIME column of table CLW_ORDER_PROCESSING */
    public static final String ORDER_PROCESSING_TIME = "ORDER_PROCESSING_TIME";
    /** <code>ACTUAL_TRANSACTION_ID</code> ACTUAL_TRANSACTION_ID column of table CLW_ORDER_PROCESSING */
    public static final String ACTUAL_TRANSACTION_ID = "ACTUAL_TRANSACTION_ID";
    /** <code>TRANSACTION_CD</code> TRANSACTION_CD column of table CLW_ORDER_PROCESSING */
    public static final String TRANSACTION_CD = "TRANSACTION_CD";
    /** <code>ORDER_PROCESSING_STATUS_CD</code> ORDER_PROCESSING_STATUS_CD column of table CLW_ORDER_PROCESSING */
    public static final String ORDER_PROCESSING_STATUS_CD = "ORDER_PROCESSING_STATUS_CD";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ORDER_PROCESSING */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ORDER_PROCESSING */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ORDER_PROCESSING */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ORDER_PROCESSING */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public OrderProcessingDataAccess()
    {
    }

    /**
     * Gets a OrderProcessingData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pOrderProcessingId The key requested.
     * @return new OrderProcessingData()
     * @throws            SQLException
     */
    public static OrderProcessingData select(Connection pCon, int pOrderProcessingId)
        throws SQLException, DataNotFoundException {
        OrderProcessingData x=null;
        String sql="SELECT ORDER_PROCESSING_ID,ORDER_ID,ORDER_ITEM_ID,INVOICE_ID,ORDER_PROCESSING_DATE,ORDER_PROCESSING_TIME,ACTUAL_TRANSACTION_ID,TRANSACTION_CD,ORDER_PROCESSING_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_PROCESSING WHERE ORDER_PROCESSING_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pOrderProcessingId=" + pOrderProcessingId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pOrderProcessingId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=OrderProcessingData.createValue();
            
            x.setOrderProcessingId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setOrderItemId(rs.getInt(3));
            x.setInvoiceId(rs.getInt(4));
            x.setOrderProcessingDate(rs.getDate(5));
            x.setOrderProcessingTime(rs.getTimestamp(6));
            x.setActualTransactionId(rs.getInt(7));
            x.setTransactionCd(rs.getString(8));
            x.setOrderProcessingStatusCd(rs.getString(9));
            x.setAddDate(rs.getTimestamp(10));
            x.setAddBy(rs.getString(11));
            x.setModDate(rs.getTimestamp(12));
            x.setModBy(rs.getString(13));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ORDER_PROCESSING_ID :" + pOrderProcessingId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a OrderProcessingDataVector object that consists
     * of OrderProcessingData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new OrderProcessingDataVector()
     * @throws            SQLException
     */
    public static OrderProcessingDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a OrderProcessingData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ORDER_PROCESSING.ORDER_PROCESSING_ID,CLW_ORDER_PROCESSING.ORDER_ID,CLW_ORDER_PROCESSING.ORDER_ITEM_ID,CLW_ORDER_PROCESSING.INVOICE_ID,CLW_ORDER_PROCESSING.ORDER_PROCESSING_DATE,CLW_ORDER_PROCESSING.ORDER_PROCESSING_TIME,CLW_ORDER_PROCESSING.ACTUAL_TRANSACTION_ID,CLW_ORDER_PROCESSING.TRANSACTION_CD,CLW_ORDER_PROCESSING.ORDER_PROCESSING_STATUS_CD,CLW_ORDER_PROCESSING.ADD_DATE,CLW_ORDER_PROCESSING.ADD_BY,CLW_ORDER_PROCESSING.MOD_DATE,CLW_ORDER_PROCESSING.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated OrderProcessingData Object.
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
    *@returns a populated OrderProcessingData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         OrderProcessingData x = OrderProcessingData.createValue();
         
         x.setOrderProcessingId(rs.getInt(1+offset));
         x.setOrderId(rs.getInt(2+offset));
         x.setOrderItemId(rs.getInt(3+offset));
         x.setInvoiceId(rs.getInt(4+offset));
         x.setOrderProcessingDate(rs.getDate(5+offset));
         x.setOrderProcessingTime(rs.getTimestamp(6+offset));
         x.setActualTransactionId(rs.getInt(7+offset));
         x.setTransactionCd(rs.getString(8+offset));
         x.setOrderProcessingStatusCd(rs.getString(9+offset));
         x.setAddDate(rs.getTimestamp(10+offset));
         x.setAddBy(rs.getString(11+offset));
         x.setModDate(rs.getTimestamp(12+offset));
         x.setModBy(rs.getString(13+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the OrderProcessingData Object represents.
    */
    public int getColumnCount(){
        return 13;
    }

    /**
     * Gets a OrderProcessingDataVector object that consists
     * of OrderProcessingData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new OrderProcessingDataVector()
     * @throws            SQLException
     */
    public static OrderProcessingDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ORDER_PROCESSING_ID,ORDER_ID,ORDER_ITEM_ID,INVOICE_ID,ORDER_PROCESSING_DATE,ORDER_PROCESSING_TIME,ACTUAL_TRANSACTION_ID,TRANSACTION_CD,ORDER_PROCESSING_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_PROCESSING");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ORDER_PROCESSING.ORDER_PROCESSING_ID,CLW_ORDER_PROCESSING.ORDER_ID,CLW_ORDER_PROCESSING.ORDER_ITEM_ID,CLW_ORDER_PROCESSING.INVOICE_ID,CLW_ORDER_PROCESSING.ORDER_PROCESSING_DATE,CLW_ORDER_PROCESSING.ORDER_PROCESSING_TIME,CLW_ORDER_PROCESSING.ACTUAL_TRANSACTION_ID,CLW_ORDER_PROCESSING.TRANSACTION_CD,CLW_ORDER_PROCESSING.ORDER_PROCESSING_STATUS_CD,CLW_ORDER_PROCESSING.ADD_DATE,CLW_ORDER_PROCESSING.ADD_BY,CLW_ORDER_PROCESSING.MOD_DATE,CLW_ORDER_PROCESSING.MOD_BY FROM CLW_ORDER_PROCESSING");
                where = pCriteria.getSqlClause("CLW_ORDER_PROCESSING");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ORDER_PROCESSING.equals(otherTable)){
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
        OrderProcessingDataVector v = new OrderProcessingDataVector();
        while (rs.next()) {
            OrderProcessingData x = OrderProcessingData.createValue();
            
            x.setOrderProcessingId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setOrderItemId(rs.getInt(3));
            x.setInvoiceId(rs.getInt(4));
            x.setOrderProcessingDate(rs.getDate(5));
            x.setOrderProcessingTime(rs.getTimestamp(6));
            x.setActualTransactionId(rs.getInt(7));
            x.setTransactionCd(rs.getString(8));
            x.setOrderProcessingStatusCd(rs.getString(9));
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
     * Gets a OrderProcessingDataVector object that consists
     * of OrderProcessingData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for OrderProcessingData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new OrderProcessingDataVector()
     * @throws            SQLException
     */
    public static OrderProcessingDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        OrderProcessingDataVector v = new OrderProcessingDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_PROCESSING_ID,ORDER_ID,ORDER_ITEM_ID,INVOICE_ID,ORDER_PROCESSING_DATE,ORDER_PROCESSING_TIME,ACTUAL_TRANSACTION_ID,TRANSACTION_CD,ORDER_PROCESSING_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_PROCESSING WHERE ORDER_PROCESSING_ID IN (");

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
            OrderProcessingData x=null;
            while (rs.next()) {
                // build the object
                x=OrderProcessingData.createValue();
                
                x.setOrderProcessingId(rs.getInt(1));
                x.setOrderId(rs.getInt(2));
                x.setOrderItemId(rs.getInt(3));
                x.setInvoiceId(rs.getInt(4));
                x.setOrderProcessingDate(rs.getDate(5));
                x.setOrderProcessingTime(rs.getTimestamp(6));
                x.setActualTransactionId(rs.getInt(7));
                x.setTransactionCd(rs.getString(8));
                x.setOrderProcessingStatusCd(rs.getString(9));
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
     * Gets a OrderProcessingDataVector object of all
     * OrderProcessingData objects in the database.
     * @param pCon An open database connection.
     * @return new OrderProcessingDataVector()
     * @throws            SQLException
     */
    public static OrderProcessingDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ORDER_PROCESSING_ID,ORDER_ID,ORDER_ITEM_ID,INVOICE_ID,ORDER_PROCESSING_DATE,ORDER_PROCESSING_TIME,ACTUAL_TRANSACTION_ID,TRANSACTION_CD,ORDER_PROCESSING_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_PROCESSING";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        OrderProcessingDataVector v = new OrderProcessingDataVector();
        OrderProcessingData x = null;
        while (rs.next()) {
            // build the object
            x = OrderProcessingData.createValue();
            
            x.setOrderProcessingId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setOrderItemId(rs.getInt(3));
            x.setInvoiceId(rs.getInt(4));
            x.setOrderProcessingDate(rs.getDate(5));
            x.setOrderProcessingTime(rs.getTimestamp(6));
            x.setActualTransactionId(rs.getInt(7));
            x.setTransactionCd(rs.getString(8));
            x.setOrderProcessingStatusCd(rs.getString(9));
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
     * OrderProcessingData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_PROCESSING_ID FROM CLW_ORDER_PROCESSING");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_PROCESSING");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_PROCESSING");
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
     * Inserts a OrderProcessingData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderProcessingData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new OrderProcessingData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderProcessingData insert(Connection pCon, OrderProcessingData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ORDER_PROCESSING_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ORDER_PROCESSING_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setOrderProcessingId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ORDER_PROCESSING (ORDER_PROCESSING_ID,ORDER_ID,ORDER_ITEM_ID,INVOICE_ID,ORDER_PROCESSING_DATE,ORDER_PROCESSING_TIME,ACTUAL_TRANSACTION_ID,TRANSACTION_CD,ORDER_PROCESSING_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getOrderProcessingId());
        pstmt.setInt(2,pData.getOrderId());
        pstmt.setInt(3,pData.getOrderItemId());
        if (pData.getInvoiceId() == 0) {
            pstmt.setNull(4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4,pData.getInvoiceId());
        }

        pstmt.setDate(5,DBAccess.toSQLDate(pData.getOrderProcessingDate()));
        pstmt.setTimestamp(6,DBAccess.toSQLTimestamp(pData.getOrderProcessingTime()));
        pstmt.setInt(7,pData.getActualTransactionId());
        pstmt.setString(8,pData.getTransactionCd());
        pstmt.setString(9,pData.getOrderProcessingStatusCd());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(11,pData.getAddBy());
        pstmt.setTimestamp(12,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(13,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_PROCESSING_ID="+pData.getOrderProcessingId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ORDER_ITEM_ID="+pData.getOrderItemId());
            log.debug("SQL:   INVOICE_ID="+pData.getInvoiceId());
            log.debug("SQL:   ORDER_PROCESSING_DATE="+pData.getOrderProcessingDate());
            log.debug("SQL:   ORDER_PROCESSING_TIME="+pData.getOrderProcessingTime());
            log.debug("SQL:   ACTUAL_TRANSACTION_ID="+pData.getActualTransactionId());
            log.debug("SQL:   TRANSACTION_CD="+pData.getTransactionCd());
            log.debug("SQL:   ORDER_PROCESSING_STATUS_CD="+pData.getOrderProcessingStatusCd());
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
        pData.setOrderProcessingId(0);
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
     * Updates a OrderProcessingData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderProcessingData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderProcessingData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ORDER_PROCESSING SET ORDER_ID = ?,ORDER_ITEM_ID = ?,INVOICE_ID = ?,ORDER_PROCESSING_DATE = ?,ORDER_PROCESSING_TIME = ?,ACTUAL_TRANSACTION_ID = ?,TRANSACTION_CD = ?,ORDER_PROCESSING_STATUS_CD = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE ORDER_PROCESSING_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getOrderId());
        pstmt.setInt(i++,pData.getOrderItemId());
        if (pData.getInvoiceId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getInvoiceId());
        }

        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getOrderProcessingDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getOrderProcessingTime()));
        pstmt.setInt(i++,pData.getActualTransactionId());
        pstmt.setString(i++,pData.getTransactionCd());
        pstmt.setString(i++,pData.getOrderProcessingStatusCd());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getOrderProcessingId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ORDER_ITEM_ID="+pData.getOrderItemId());
            log.debug("SQL:   INVOICE_ID="+pData.getInvoiceId());
            log.debug("SQL:   ORDER_PROCESSING_DATE="+pData.getOrderProcessingDate());
            log.debug("SQL:   ORDER_PROCESSING_TIME="+pData.getOrderProcessingTime());
            log.debug("SQL:   ACTUAL_TRANSACTION_ID="+pData.getActualTransactionId());
            log.debug("SQL:   TRANSACTION_CD="+pData.getTransactionCd());
            log.debug("SQL:   ORDER_PROCESSING_STATUS_CD="+pData.getOrderProcessingStatusCd());
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
     * Deletes a OrderProcessingData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderProcessingId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderProcessingId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ORDER_PROCESSING WHERE ORDER_PROCESSING_ID = " + pOrderProcessingId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes OrderProcessingData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ORDER_PROCESSING");
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
     * Inserts a OrderProcessingData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderProcessingData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, OrderProcessingData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ORDER_PROCESSING (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ORDER_PROCESSING_ID,ORDER_ID,ORDER_ITEM_ID,INVOICE_ID,ORDER_PROCESSING_DATE,ORDER_PROCESSING_TIME,ACTUAL_TRANSACTION_ID,TRANSACTION_CD,ORDER_PROCESSING_STATUS_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getOrderProcessingId());
        pstmt.setInt(2+4,pData.getOrderId());
        pstmt.setInt(3+4,pData.getOrderItemId());
        if (pData.getInvoiceId() == 0) {
            pstmt.setNull(4+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4+4,pData.getInvoiceId());
        }

        pstmt.setDate(5+4,DBAccess.toSQLDate(pData.getOrderProcessingDate()));
        pstmt.setTimestamp(6+4,DBAccess.toSQLTimestamp(pData.getOrderProcessingTime()));
        pstmt.setInt(7+4,pData.getActualTransactionId());
        pstmt.setString(8+4,pData.getTransactionCd());
        pstmt.setString(9+4,pData.getOrderProcessingStatusCd());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(11+4,pData.getAddBy());
        pstmt.setTimestamp(12+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(13+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a OrderProcessingData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderProcessingData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new OrderProcessingData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderProcessingData insert(Connection pCon, OrderProcessingData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a OrderProcessingData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderProcessingData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderProcessingData pData, boolean pLogFl)
        throws SQLException {
        OrderProcessingData oldData = null;
        if(pLogFl) {
          int id = pData.getOrderProcessingId();
          try {
          oldData = OrderProcessingDataAccess.select(pCon,id);
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
     * Deletes a OrderProcessingData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderProcessingId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderProcessingId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ORDER_PROCESSING SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_PROCESSING d WHERE ORDER_PROCESSING_ID = " + pOrderProcessingId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pOrderProcessingId);
        return n;
     }

    /**
     * Deletes OrderProcessingData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ORDER_PROCESSING SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_PROCESSING d ");
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

