
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        OrderItemActionDataAccess
 * Description:  This class is used to build access methods to the CLW_ORDER_ITEM_ACTION table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.OrderItemActionData;
import com.cleanwise.service.api.value.OrderItemActionDataVector;
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
 * <code>OrderItemActionDataAccess</code>
 */
public class OrderItemActionDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(OrderItemActionDataAccess.class.getName());

    /** <code>CLW_ORDER_ITEM_ACTION</code> table name */
	/* Primary key: ORDER_ITEM_ACTION_ID */
	
    public static final String CLW_ORDER_ITEM_ACTION = "CLW_ORDER_ITEM_ACTION";
    
    /** <code>ORDER_ITEM_ACTION_ID</code> ORDER_ITEM_ACTION_ID column of table CLW_ORDER_ITEM_ACTION */
    public static final String ORDER_ITEM_ACTION_ID = "ORDER_ITEM_ACTION_ID";
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_ORDER_ITEM_ACTION */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>ORDER_ITEM_ID</code> ORDER_ITEM_ID column of table CLW_ORDER_ITEM_ACTION */
    public static final String ORDER_ITEM_ID = "ORDER_ITEM_ID";
    /** <code>AFFECTED_ORDER_NUM</code> AFFECTED_ORDER_NUM column of table CLW_ORDER_ITEM_ACTION */
    public static final String AFFECTED_ORDER_NUM = "AFFECTED_ORDER_NUM";
    /** <code>AFFECTED_SKU</code> AFFECTED_SKU column of table CLW_ORDER_ITEM_ACTION */
    public static final String AFFECTED_SKU = "AFFECTED_SKU";
    /** <code>AFFECTED_LINE_ITEM</code> AFFECTED_LINE_ITEM column of table CLW_ORDER_ITEM_ACTION */
    public static final String AFFECTED_LINE_ITEM = "AFFECTED_LINE_ITEM";
    /** <code>ACTION_CD</code> ACTION_CD column of table CLW_ORDER_ITEM_ACTION */
    public static final String ACTION_CD = "ACTION_CD";
    /** <code>ACTUAL_TRANSACTION_ID</code> ACTUAL_TRANSACTION_ID column of table CLW_ORDER_ITEM_ACTION */
    public static final String ACTUAL_TRANSACTION_ID = "ACTUAL_TRANSACTION_ID";
    /** <code>AFFECTED_TABLE</code> AFFECTED_TABLE column of table CLW_ORDER_ITEM_ACTION */
    public static final String AFFECTED_TABLE = "AFFECTED_TABLE";
    /** <code>AFFECTED_ID</code> AFFECTED_ID column of table CLW_ORDER_ITEM_ACTION */
    public static final String AFFECTED_ID = "AFFECTED_ID";
    /** <code>AMOUNT</code> AMOUNT column of table CLW_ORDER_ITEM_ACTION */
    public static final String AMOUNT = "AMOUNT";
    /** <code>QUANTITY</code> QUANTITY column of table CLW_ORDER_ITEM_ACTION */
    public static final String QUANTITY = "QUANTITY";
    /** <code>COMMENTS</code> COMMENTS column of table CLW_ORDER_ITEM_ACTION */
    public static final String COMMENTS = "COMMENTS";
    /** <code>ACTION_DATE</code> ACTION_DATE column of table CLW_ORDER_ITEM_ACTION */
    public static final String ACTION_DATE = "ACTION_DATE";
    /** <code>ACTION_TIME</code> ACTION_TIME column of table CLW_ORDER_ITEM_ACTION */
    public static final String ACTION_TIME = "ACTION_TIME";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ORDER_ITEM_ACTION */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ORDER_ITEM_ACTION */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ORDER_ITEM_ACTION */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ORDER_ITEM_ACTION */
    public static final String MOD_BY = "MOD_BY";
    /** <code>STATUS</code> STATUS column of table CLW_ORDER_ITEM_ACTION */
    public static final String STATUS = "STATUS";

    /**
     * Constructor.
     */
    public OrderItemActionDataAccess()
    {
    }

    /**
     * Gets a OrderItemActionData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pOrderItemActionId The key requested.
     * @return new OrderItemActionData()
     * @throws            SQLException
     */
    public static OrderItemActionData select(Connection pCon, int pOrderItemActionId)
        throws SQLException, DataNotFoundException {
        OrderItemActionData x=null;
        String sql="SELECT ORDER_ITEM_ACTION_ID,ORDER_ID,ORDER_ITEM_ID,AFFECTED_ORDER_NUM,AFFECTED_SKU,AFFECTED_LINE_ITEM,ACTION_CD,ACTUAL_TRANSACTION_ID,AFFECTED_TABLE,AFFECTED_ID,AMOUNT,QUANTITY,COMMENTS,ACTION_DATE,ACTION_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,STATUS FROM CLW_ORDER_ITEM_ACTION WHERE ORDER_ITEM_ACTION_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pOrderItemActionId=" + pOrderItemActionId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pOrderItemActionId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=OrderItemActionData.createValue();
            
            x.setOrderItemActionId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setOrderItemId(rs.getInt(3));
            x.setAffectedOrderNum(rs.getString(4));
            x.setAffectedSku(rs.getString(5));
            x.setAffectedLineItem(rs.getInt(6));
            x.setActionCd(rs.getString(7));
            x.setActualTransactionId(rs.getInt(8));
            x.setAffectedTable(rs.getString(9));
            x.setAffectedId(rs.getInt(10));
            x.setAmount(rs.getBigDecimal(11));
            x.setQuantity(rs.getInt(12));
            x.setComments(rs.getString(13));
            x.setActionDate(rs.getDate(14));
            x.setActionTime(rs.getTimestamp(15));
            x.setAddDate(rs.getTimestamp(16));
            x.setAddBy(rs.getString(17));
            x.setModDate(rs.getTimestamp(18));
            x.setModBy(rs.getString(19));
            x.setStatus(rs.getString(20));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ORDER_ITEM_ACTION_ID :" + pOrderItemActionId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a OrderItemActionDataVector object that consists
     * of OrderItemActionData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new OrderItemActionDataVector()
     * @throws            SQLException
     */
    public static OrderItemActionDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a OrderItemActionData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ORDER_ITEM_ACTION.ORDER_ITEM_ACTION_ID,CLW_ORDER_ITEM_ACTION.ORDER_ID,CLW_ORDER_ITEM_ACTION.ORDER_ITEM_ID,CLW_ORDER_ITEM_ACTION.AFFECTED_ORDER_NUM,CLW_ORDER_ITEM_ACTION.AFFECTED_SKU,CLW_ORDER_ITEM_ACTION.AFFECTED_LINE_ITEM,CLW_ORDER_ITEM_ACTION.ACTION_CD,CLW_ORDER_ITEM_ACTION.ACTUAL_TRANSACTION_ID,CLW_ORDER_ITEM_ACTION.AFFECTED_TABLE,CLW_ORDER_ITEM_ACTION.AFFECTED_ID,CLW_ORDER_ITEM_ACTION.AMOUNT,CLW_ORDER_ITEM_ACTION.QUANTITY,CLW_ORDER_ITEM_ACTION.COMMENTS,CLW_ORDER_ITEM_ACTION.ACTION_DATE,CLW_ORDER_ITEM_ACTION.ACTION_TIME,CLW_ORDER_ITEM_ACTION.ADD_DATE,CLW_ORDER_ITEM_ACTION.ADD_BY,CLW_ORDER_ITEM_ACTION.MOD_DATE,CLW_ORDER_ITEM_ACTION.MOD_BY,CLW_ORDER_ITEM_ACTION.STATUS";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated OrderItemActionData Object.
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
    *@returns a populated OrderItemActionData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         OrderItemActionData x = OrderItemActionData.createValue();
         
         x.setOrderItemActionId(rs.getInt(1+offset));
         x.setOrderId(rs.getInt(2+offset));
         x.setOrderItemId(rs.getInt(3+offset));
         x.setAffectedOrderNum(rs.getString(4+offset));
         x.setAffectedSku(rs.getString(5+offset));
         x.setAffectedLineItem(rs.getInt(6+offset));
         x.setActionCd(rs.getString(7+offset));
         x.setActualTransactionId(rs.getInt(8+offset));
         x.setAffectedTable(rs.getString(9+offset));
         x.setAffectedId(rs.getInt(10+offset));
         x.setAmount(rs.getBigDecimal(11+offset));
         x.setQuantity(rs.getInt(12+offset));
         x.setComments(rs.getString(13+offset));
         x.setActionDate(rs.getDate(14+offset));
         x.setActionTime(rs.getTimestamp(15+offset));
         x.setAddDate(rs.getTimestamp(16+offset));
         x.setAddBy(rs.getString(17+offset));
         x.setModDate(rs.getTimestamp(18+offset));
         x.setModBy(rs.getString(19+offset));
         x.setStatus(rs.getString(20+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the OrderItemActionData Object represents.
    */
    public int getColumnCount(){
        return 20;
    }

    /**
     * Gets a OrderItemActionDataVector object that consists
     * of OrderItemActionData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new OrderItemActionDataVector()
     * @throws            SQLException
     */
    public static OrderItemActionDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ORDER_ITEM_ACTION_ID,ORDER_ID,ORDER_ITEM_ID,AFFECTED_ORDER_NUM,AFFECTED_SKU,AFFECTED_LINE_ITEM,ACTION_CD,ACTUAL_TRANSACTION_ID,AFFECTED_TABLE,AFFECTED_ID,AMOUNT,QUANTITY,COMMENTS,ACTION_DATE,ACTION_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,STATUS FROM CLW_ORDER_ITEM_ACTION");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ORDER_ITEM_ACTION.ORDER_ITEM_ACTION_ID,CLW_ORDER_ITEM_ACTION.ORDER_ID,CLW_ORDER_ITEM_ACTION.ORDER_ITEM_ID,CLW_ORDER_ITEM_ACTION.AFFECTED_ORDER_NUM,CLW_ORDER_ITEM_ACTION.AFFECTED_SKU,CLW_ORDER_ITEM_ACTION.AFFECTED_LINE_ITEM,CLW_ORDER_ITEM_ACTION.ACTION_CD,CLW_ORDER_ITEM_ACTION.ACTUAL_TRANSACTION_ID,CLW_ORDER_ITEM_ACTION.AFFECTED_TABLE,CLW_ORDER_ITEM_ACTION.AFFECTED_ID,CLW_ORDER_ITEM_ACTION.AMOUNT,CLW_ORDER_ITEM_ACTION.QUANTITY,CLW_ORDER_ITEM_ACTION.COMMENTS,CLW_ORDER_ITEM_ACTION.ACTION_DATE,CLW_ORDER_ITEM_ACTION.ACTION_TIME,CLW_ORDER_ITEM_ACTION.ADD_DATE,CLW_ORDER_ITEM_ACTION.ADD_BY,CLW_ORDER_ITEM_ACTION.MOD_DATE,CLW_ORDER_ITEM_ACTION.MOD_BY,CLW_ORDER_ITEM_ACTION.STATUS FROM CLW_ORDER_ITEM_ACTION");
                where = pCriteria.getSqlClause("CLW_ORDER_ITEM_ACTION");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ORDER_ITEM_ACTION.equals(otherTable)){
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
        OrderItemActionDataVector v = new OrderItemActionDataVector();
        while (rs.next()) {
            OrderItemActionData x = OrderItemActionData.createValue();
            
            x.setOrderItemActionId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setOrderItemId(rs.getInt(3));
            x.setAffectedOrderNum(rs.getString(4));
            x.setAffectedSku(rs.getString(5));
            x.setAffectedLineItem(rs.getInt(6));
            x.setActionCd(rs.getString(7));
            x.setActualTransactionId(rs.getInt(8));
            x.setAffectedTable(rs.getString(9));
            x.setAffectedId(rs.getInt(10));
            x.setAmount(rs.getBigDecimal(11));
            x.setQuantity(rs.getInt(12));
            x.setComments(rs.getString(13));
            x.setActionDate(rs.getDate(14));
            x.setActionTime(rs.getTimestamp(15));
            x.setAddDate(rs.getTimestamp(16));
            x.setAddBy(rs.getString(17));
            x.setModDate(rs.getTimestamp(18));
            x.setModBy(rs.getString(19));
            x.setStatus(rs.getString(20));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a OrderItemActionDataVector object that consists
     * of OrderItemActionData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for OrderItemActionData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new OrderItemActionDataVector()
     * @throws            SQLException
     */
    public static OrderItemActionDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        OrderItemActionDataVector v = new OrderItemActionDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_ITEM_ACTION_ID,ORDER_ID,ORDER_ITEM_ID,AFFECTED_ORDER_NUM,AFFECTED_SKU,AFFECTED_LINE_ITEM,ACTION_CD,ACTUAL_TRANSACTION_ID,AFFECTED_TABLE,AFFECTED_ID,AMOUNT,QUANTITY,COMMENTS,ACTION_DATE,ACTION_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,STATUS FROM CLW_ORDER_ITEM_ACTION WHERE ORDER_ITEM_ACTION_ID IN (");

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
            OrderItemActionData x=null;
            while (rs.next()) {
                // build the object
                x=OrderItemActionData.createValue();
                
                x.setOrderItemActionId(rs.getInt(1));
                x.setOrderId(rs.getInt(2));
                x.setOrderItemId(rs.getInt(3));
                x.setAffectedOrderNum(rs.getString(4));
                x.setAffectedSku(rs.getString(5));
                x.setAffectedLineItem(rs.getInt(6));
                x.setActionCd(rs.getString(7));
                x.setActualTransactionId(rs.getInt(8));
                x.setAffectedTable(rs.getString(9));
                x.setAffectedId(rs.getInt(10));
                x.setAmount(rs.getBigDecimal(11));
                x.setQuantity(rs.getInt(12));
                x.setComments(rs.getString(13));
                x.setActionDate(rs.getDate(14));
                x.setActionTime(rs.getTimestamp(15));
                x.setAddDate(rs.getTimestamp(16));
                x.setAddBy(rs.getString(17));
                x.setModDate(rs.getTimestamp(18));
                x.setModBy(rs.getString(19));
                x.setStatus(rs.getString(20));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a OrderItemActionDataVector object of all
     * OrderItemActionData objects in the database.
     * @param pCon An open database connection.
     * @return new OrderItemActionDataVector()
     * @throws            SQLException
     */
    public static OrderItemActionDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ORDER_ITEM_ACTION_ID,ORDER_ID,ORDER_ITEM_ID,AFFECTED_ORDER_NUM,AFFECTED_SKU,AFFECTED_LINE_ITEM,ACTION_CD,ACTUAL_TRANSACTION_ID,AFFECTED_TABLE,AFFECTED_ID,AMOUNT,QUANTITY,COMMENTS,ACTION_DATE,ACTION_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,STATUS FROM CLW_ORDER_ITEM_ACTION";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        OrderItemActionDataVector v = new OrderItemActionDataVector();
        OrderItemActionData x = null;
        while (rs.next()) {
            // build the object
            x = OrderItemActionData.createValue();
            
            x.setOrderItemActionId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setOrderItemId(rs.getInt(3));
            x.setAffectedOrderNum(rs.getString(4));
            x.setAffectedSku(rs.getString(5));
            x.setAffectedLineItem(rs.getInt(6));
            x.setActionCd(rs.getString(7));
            x.setActualTransactionId(rs.getInt(8));
            x.setAffectedTable(rs.getString(9));
            x.setAffectedId(rs.getInt(10));
            x.setAmount(rs.getBigDecimal(11));
            x.setQuantity(rs.getInt(12));
            x.setComments(rs.getString(13));
            x.setActionDate(rs.getDate(14));
            x.setActionTime(rs.getTimestamp(15));
            x.setAddDate(rs.getTimestamp(16));
            x.setAddBy(rs.getString(17));
            x.setModDate(rs.getTimestamp(18));
            x.setModBy(rs.getString(19));
            x.setStatus(rs.getString(20));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * OrderItemActionData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_ITEM_ACTION_ID FROM CLW_ORDER_ITEM_ACTION");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_ITEM_ACTION");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_ITEM_ACTION");
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
     * Inserts a OrderItemActionData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderItemActionData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new OrderItemActionData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderItemActionData insert(Connection pCon, OrderItemActionData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ORDER_ITEM_ACTION_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ORDER_ITEM_ACTION_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setOrderItemActionId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ORDER_ITEM_ACTION (ORDER_ITEM_ACTION_ID,ORDER_ID,ORDER_ITEM_ID,AFFECTED_ORDER_NUM,AFFECTED_SKU,AFFECTED_LINE_ITEM,ACTION_CD,ACTUAL_TRANSACTION_ID,AFFECTED_TABLE,AFFECTED_ID,AMOUNT,QUANTITY,COMMENTS,ACTION_DATE,ACTION_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,STATUS) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getOrderItemActionId());
        pstmt.setInt(2,pData.getOrderId());
        pstmt.setInt(3,pData.getOrderItemId());
        pstmt.setString(4,pData.getAffectedOrderNum());
        pstmt.setString(5,pData.getAffectedSku());
        pstmt.setInt(6,pData.getAffectedLineItem());
        pstmt.setString(7,pData.getActionCd());
        pstmt.setInt(8,pData.getActualTransactionId());
        pstmt.setString(9,pData.getAffectedTable());
        pstmt.setInt(10,pData.getAffectedId());
        pstmt.setBigDecimal(11,pData.getAmount());
        pstmt.setInt(12,pData.getQuantity());
        pstmt.setString(13,pData.getComments());
        pstmt.setDate(14,DBAccess.toSQLDate(pData.getActionDate()));
        pstmt.setTimestamp(15,DBAccess.toSQLTimestamp(pData.getActionTime()));
        pstmt.setTimestamp(16,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(17,pData.getAddBy());
        pstmt.setTimestamp(18,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(19,pData.getModBy());
        pstmt.setString(20,pData.getStatus());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_ITEM_ACTION_ID="+pData.getOrderItemActionId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ORDER_ITEM_ID="+pData.getOrderItemId());
            log.debug("SQL:   AFFECTED_ORDER_NUM="+pData.getAffectedOrderNum());
            log.debug("SQL:   AFFECTED_SKU="+pData.getAffectedSku());
            log.debug("SQL:   AFFECTED_LINE_ITEM="+pData.getAffectedLineItem());
            log.debug("SQL:   ACTION_CD="+pData.getActionCd());
            log.debug("SQL:   ACTUAL_TRANSACTION_ID="+pData.getActualTransactionId());
            log.debug("SQL:   AFFECTED_TABLE="+pData.getAffectedTable());
            log.debug("SQL:   AFFECTED_ID="+pData.getAffectedId());
            log.debug("SQL:   AMOUNT="+pData.getAmount());
            log.debug("SQL:   QUANTITY="+pData.getQuantity());
            log.debug("SQL:   COMMENTS="+pData.getComments());
            log.debug("SQL:   ACTION_DATE="+pData.getActionDate());
            log.debug("SQL:   ACTION_TIME="+pData.getActionTime());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   STATUS="+pData.getStatus());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setOrderItemActionId(0);
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
     * Updates a OrderItemActionData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderItemActionData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderItemActionData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ORDER_ITEM_ACTION SET ORDER_ID = ?,ORDER_ITEM_ID = ?,AFFECTED_ORDER_NUM = ?,AFFECTED_SKU = ?,AFFECTED_LINE_ITEM = ?,ACTION_CD = ?,ACTUAL_TRANSACTION_ID = ?,AFFECTED_TABLE = ?,AFFECTED_ID = ?,AMOUNT = ?,QUANTITY = ?,COMMENTS = ?,ACTION_DATE = ?,ACTION_TIME = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,STATUS = ? WHERE ORDER_ITEM_ACTION_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getOrderId());
        pstmt.setInt(i++,pData.getOrderItemId());
        pstmt.setString(i++,pData.getAffectedOrderNum());
        pstmt.setString(i++,pData.getAffectedSku());
        pstmt.setInt(i++,pData.getAffectedLineItem());
        pstmt.setString(i++,pData.getActionCd());
        pstmt.setInt(i++,pData.getActualTransactionId());
        pstmt.setString(i++,pData.getAffectedTable());
        pstmt.setInt(i++,pData.getAffectedId());
        pstmt.setBigDecimal(i++,pData.getAmount());
        pstmt.setInt(i++,pData.getQuantity());
        pstmt.setString(i++,pData.getComments());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getActionDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getActionTime()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getStatus());
        pstmt.setInt(i++,pData.getOrderItemActionId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   ORDER_ITEM_ID="+pData.getOrderItemId());
            log.debug("SQL:   AFFECTED_ORDER_NUM="+pData.getAffectedOrderNum());
            log.debug("SQL:   AFFECTED_SKU="+pData.getAffectedSku());
            log.debug("SQL:   AFFECTED_LINE_ITEM="+pData.getAffectedLineItem());
            log.debug("SQL:   ACTION_CD="+pData.getActionCd());
            log.debug("SQL:   ACTUAL_TRANSACTION_ID="+pData.getActualTransactionId());
            log.debug("SQL:   AFFECTED_TABLE="+pData.getAffectedTable());
            log.debug("SQL:   AFFECTED_ID="+pData.getAffectedId());
            log.debug("SQL:   AMOUNT="+pData.getAmount());
            log.debug("SQL:   QUANTITY="+pData.getQuantity());
            log.debug("SQL:   COMMENTS="+pData.getComments());
            log.debug("SQL:   ACTION_DATE="+pData.getActionDate());
            log.debug("SQL:   ACTION_TIME="+pData.getActionTime());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   STATUS="+pData.getStatus());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a OrderItemActionData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderItemActionId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderItemActionId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ORDER_ITEM_ACTION WHERE ORDER_ITEM_ACTION_ID = " + pOrderItemActionId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes OrderItemActionData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ORDER_ITEM_ACTION");
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
     * Inserts a OrderItemActionData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderItemActionData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, OrderItemActionData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ORDER_ITEM_ACTION (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ORDER_ITEM_ACTION_ID,ORDER_ID,ORDER_ITEM_ID,AFFECTED_ORDER_NUM,AFFECTED_SKU,AFFECTED_LINE_ITEM,ACTION_CD,ACTUAL_TRANSACTION_ID,AFFECTED_TABLE,AFFECTED_ID,AMOUNT,QUANTITY,COMMENTS,ACTION_DATE,ACTION_TIME,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,STATUS) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getOrderItemActionId());
        pstmt.setInt(2+4,pData.getOrderId());
        pstmt.setInt(3+4,pData.getOrderItemId());
        pstmt.setString(4+4,pData.getAffectedOrderNum());
        pstmt.setString(5+4,pData.getAffectedSku());
        pstmt.setInt(6+4,pData.getAffectedLineItem());
        pstmt.setString(7+4,pData.getActionCd());
        pstmt.setInt(8+4,pData.getActualTransactionId());
        pstmt.setString(9+4,pData.getAffectedTable());
        pstmt.setInt(10+4,pData.getAffectedId());
        pstmt.setBigDecimal(11+4,pData.getAmount());
        pstmt.setInt(12+4,pData.getQuantity());
        pstmt.setString(13+4,pData.getComments());
        pstmt.setDate(14+4,DBAccess.toSQLDate(pData.getActionDate()));
        pstmt.setTimestamp(15+4,DBAccess.toSQLTimestamp(pData.getActionTime()));
        pstmt.setTimestamp(16+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(17+4,pData.getAddBy());
        pstmt.setTimestamp(18+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(19+4,pData.getModBy());
        pstmt.setString(20+4,pData.getStatus());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a OrderItemActionData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderItemActionData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new OrderItemActionData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderItemActionData insert(Connection pCon, OrderItemActionData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a OrderItemActionData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderItemActionData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderItemActionData pData, boolean pLogFl)
        throws SQLException {
        OrderItemActionData oldData = null;
        if(pLogFl) {
          int id = pData.getOrderItemActionId();
          try {
          oldData = OrderItemActionDataAccess.select(pCon,id);
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
     * Deletes a OrderItemActionData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderItemActionId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderItemActionId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ORDER_ITEM_ACTION SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_ITEM_ACTION d WHERE ORDER_ITEM_ACTION_ID = " + pOrderItemActionId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pOrderItemActionId);
        return n;
     }

    /**
     * Deletes OrderItemActionData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ORDER_ITEM_ACTION SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_ITEM_ACTION d ");
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

