
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        OrderGuideStructDelDataAccess
 * Description:  This class is used to build access methods to the CLW_ORDER_GUIDE_STRUCT_DEL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.OrderGuideStructDelData;
import com.cleanwise.service.api.value.OrderGuideStructDelDataVector;
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
 * <code>OrderGuideStructDelDataAccess</code>
 */
public class OrderGuideStructDelDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(OrderGuideStructDelDataAccess.class.getName());

    /** <code>CLW_ORDER_GUIDE_STRUCT_DEL</code> table name */
	/* Primary key: ORDER_GUIDE_STRUCT_DEL_ID */
	
    public static final String CLW_ORDER_GUIDE_STRUCT_DEL = "CLW_ORDER_GUIDE_STRUCT_DEL";
    
    /** <code>ORDER_GUIDE_STRUCT_DEL_ID</code> ORDER_GUIDE_STRUCT_DEL_ID column of table CLW_ORDER_GUIDE_STRUCT_DEL */
    public static final String ORDER_GUIDE_STRUCT_DEL_ID = "ORDER_GUIDE_STRUCT_DEL_ID";
    /** <code>ORDER_GUIDE_ID</code> ORDER_GUIDE_ID column of table CLW_ORDER_GUIDE_STRUCT_DEL */
    public static final String ORDER_GUIDE_ID = "ORDER_GUIDE_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_ORDER_GUIDE_STRUCT_DEL */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>CATEGORY_ITEM_ID</code> CATEGORY_ITEM_ID column of table CLW_ORDER_GUIDE_STRUCT_DEL */
    public static final String CATEGORY_ITEM_ID = "CATEGORY_ITEM_ID";
    /** <code>QUANTITY</code> QUANTITY column of table CLW_ORDER_GUIDE_STRUCT_DEL */
    public static final String QUANTITY = "QUANTITY";
    /** <code>COMMENTS</code> COMMENTS column of table CLW_ORDER_GUIDE_STRUCT_DEL */
    public static final String COMMENTS = "COMMENTS";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ORDER_GUIDE_STRUCT_DEL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ORDER_GUIDE_STRUCT_DEL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ORDER_GUIDE_STRUCT_DEL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ORDER_GUIDE_STRUCT_DEL */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public OrderGuideStructDelDataAccess()
    {
    }

    /**
     * Gets a OrderGuideStructDelData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pOrderGuideStructDelId The key requested.
     * @return new OrderGuideStructDelData()
     * @throws            SQLException
     */
    public static OrderGuideStructDelData select(Connection pCon, int pOrderGuideStructDelId)
        throws SQLException, DataNotFoundException {
        OrderGuideStructDelData x=null;
        String sql="SELECT ORDER_GUIDE_STRUCT_DEL_ID,ORDER_GUIDE_ID,ITEM_ID,CATEGORY_ITEM_ID,QUANTITY,COMMENTS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_GUIDE_STRUCT_DEL WHERE ORDER_GUIDE_STRUCT_DEL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pOrderGuideStructDelId=" + pOrderGuideStructDelId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pOrderGuideStructDelId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=OrderGuideStructDelData.createValue();
            
            x.setOrderGuideStructDelId(rs.getInt(1));
            x.setOrderGuideId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setCategoryItemId(rs.getInt(4));
            x.setQuantity(rs.getInt(5));
            x.setComments(rs.getString(6));
            x.setAddDate(rs.getTimestamp(7));
            x.setAddBy(rs.getString(8));
            x.setModDate(rs.getTimestamp(9));
            x.setModBy(rs.getString(10));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ORDER_GUIDE_STRUCT_DEL_ID :" + pOrderGuideStructDelId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a OrderGuideStructDelDataVector object that consists
     * of OrderGuideStructDelData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new OrderGuideStructDelDataVector()
     * @throws            SQLException
     */
    public static OrderGuideStructDelDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a OrderGuideStructDelData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ORDER_GUIDE_STRUCT_DEL.ORDER_GUIDE_STRUCT_DEL_ID,CLW_ORDER_GUIDE_STRUCT_DEL.ORDER_GUIDE_ID,CLW_ORDER_GUIDE_STRUCT_DEL.ITEM_ID,CLW_ORDER_GUIDE_STRUCT_DEL.CATEGORY_ITEM_ID,CLW_ORDER_GUIDE_STRUCT_DEL.QUANTITY,CLW_ORDER_GUIDE_STRUCT_DEL.COMMENTS,CLW_ORDER_GUIDE_STRUCT_DEL.ADD_DATE,CLW_ORDER_GUIDE_STRUCT_DEL.ADD_BY,CLW_ORDER_GUIDE_STRUCT_DEL.MOD_DATE,CLW_ORDER_GUIDE_STRUCT_DEL.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated OrderGuideStructDelData Object.
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
    *@returns a populated OrderGuideStructDelData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         OrderGuideStructDelData x = OrderGuideStructDelData.createValue();
         
         x.setOrderGuideStructDelId(rs.getInt(1+offset));
         x.setOrderGuideId(rs.getInt(2+offset));
         x.setItemId(rs.getInt(3+offset));
         x.setCategoryItemId(rs.getInt(4+offset));
         x.setQuantity(rs.getInt(5+offset));
         x.setComments(rs.getString(6+offset));
         x.setAddDate(rs.getTimestamp(7+offset));
         x.setAddBy(rs.getString(8+offset));
         x.setModDate(rs.getTimestamp(9+offset));
         x.setModBy(rs.getString(10+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the OrderGuideStructDelData Object represents.
    */
    public int getColumnCount(){
        return 10;
    }

    /**
     * Gets a OrderGuideStructDelDataVector object that consists
     * of OrderGuideStructDelData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new OrderGuideStructDelDataVector()
     * @throws            SQLException
     */
    public static OrderGuideStructDelDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ORDER_GUIDE_STRUCT_DEL_ID,ORDER_GUIDE_ID,ITEM_ID,CATEGORY_ITEM_ID,QUANTITY,COMMENTS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_GUIDE_STRUCT_DEL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ORDER_GUIDE_STRUCT_DEL.ORDER_GUIDE_STRUCT_DEL_ID,CLW_ORDER_GUIDE_STRUCT_DEL.ORDER_GUIDE_ID,CLW_ORDER_GUIDE_STRUCT_DEL.ITEM_ID,CLW_ORDER_GUIDE_STRUCT_DEL.CATEGORY_ITEM_ID,CLW_ORDER_GUIDE_STRUCT_DEL.QUANTITY,CLW_ORDER_GUIDE_STRUCT_DEL.COMMENTS,CLW_ORDER_GUIDE_STRUCT_DEL.ADD_DATE,CLW_ORDER_GUIDE_STRUCT_DEL.ADD_BY,CLW_ORDER_GUIDE_STRUCT_DEL.MOD_DATE,CLW_ORDER_GUIDE_STRUCT_DEL.MOD_BY FROM CLW_ORDER_GUIDE_STRUCT_DEL");
                where = pCriteria.getSqlClause("CLW_ORDER_GUIDE_STRUCT_DEL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ORDER_GUIDE_STRUCT_DEL.equals(otherTable)){
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
        OrderGuideStructDelDataVector v = new OrderGuideStructDelDataVector();
        while (rs.next()) {
            OrderGuideStructDelData x = OrderGuideStructDelData.createValue();
            
            x.setOrderGuideStructDelId(rs.getInt(1));
            x.setOrderGuideId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setCategoryItemId(rs.getInt(4));
            x.setQuantity(rs.getInt(5));
            x.setComments(rs.getString(6));
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
     * Gets a OrderGuideStructDelDataVector object that consists
     * of OrderGuideStructDelData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for OrderGuideStructDelData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new OrderGuideStructDelDataVector()
     * @throws            SQLException
     */
    public static OrderGuideStructDelDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        OrderGuideStructDelDataVector v = new OrderGuideStructDelDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_GUIDE_STRUCT_DEL_ID,ORDER_GUIDE_ID,ITEM_ID,CATEGORY_ITEM_ID,QUANTITY,COMMENTS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_GUIDE_STRUCT_DEL WHERE ORDER_GUIDE_STRUCT_DEL_ID IN (");

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
            OrderGuideStructDelData x=null;
            while (rs.next()) {
                // build the object
                x=OrderGuideStructDelData.createValue();
                
                x.setOrderGuideStructDelId(rs.getInt(1));
                x.setOrderGuideId(rs.getInt(2));
                x.setItemId(rs.getInt(3));
                x.setCategoryItemId(rs.getInt(4));
                x.setQuantity(rs.getInt(5));
                x.setComments(rs.getString(6));
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
     * Gets a OrderGuideStructDelDataVector object of all
     * OrderGuideStructDelData objects in the database.
     * @param pCon An open database connection.
     * @return new OrderGuideStructDelDataVector()
     * @throws            SQLException
     */
    public static OrderGuideStructDelDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ORDER_GUIDE_STRUCT_DEL_ID,ORDER_GUIDE_ID,ITEM_ID,CATEGORY_ITEM_ID,QUANTITY,COMMENTS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_GUIDE_STRUCT_DEL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        OrderGuideStructDelDataVector v = new OrderGuideStructDelDataVector();
        OrderGuideStructDelData x = null;
        while (rs.next()) {
            // build the object
            x = OrderGuideStructDelData.createValue();
            
            x.setOrderGuideStructDelId(rs.getInt(1));
            x.setOrderGuideId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setCategoryItemId(rs.getInt(4));
            x.setQuantity(rs.getInt(5));
            x.setComments(rs.getString(6));
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
     * OrderGuideStructDelData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_GUIDE_STRUCT_DEL_ID FROM CLW_ORDER_GUIDE_STRUCT_DEL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_GUIDE_STRUCT_DEL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_GUIDE_STRUCT_DEL");
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
     * Inserts a OrderGuideStructDelData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderGuideStructDelData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new OrderGuideStructDelData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderGuideStructDelData insert(Connection pCon, OrderGuideStructDelData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ORDER_GUIDE_STRUCT_DEL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ORDER_GUIDE_STRUCT_DEL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setOrderGuideStructDelId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ORDER_GUIDE_STRUCT_DEL (ORDER_GUIDE_STRUCT_DEL_ID,ORDER_GUIDE_ID,ITEM_ID,CATEGORY_ITEM_ID,QUANTITY,COMMENTS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getOrderGuideStructDelId());
        pstmt.setInt(2,pData.getOrderGuideId());
        pstmt.setInt(3,pData.getItemId());
        pstmt.setInt(4,pData.getCategoryItemId());
        pstmt.setInt(5,pData.getQuantity());
        pstmt.setString(6,pData.getComments());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8,pData.getAddBy());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_GUIDE_STRUCT_DEL_ID="+pData.getOrderGuideStructDelId());
            log.debug("SQL:   ORDER_GUIDE_ID="+pData.getOrderGuideId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   CATEGORY_ITEM_ID="+pData.getCategoryItemId());
            log.debug("SQL:   QUANTITY="+pData.getQuantity());
            log.debug("SQL:   COMMENTS="+pData.getComments());
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
        pData.setOrderGuideStructDelId(0);
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
     * Updates a OrderGuideStructDelData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderGuideStructDelData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderGuideStructDelData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ORDER_GUIDE_STRUCT_DEL SET ORDER_GUIDE_ID = ?,ITEM_ID = ?,CATEGORY_ITEM_ID = ?,QUANTITY = ?,COMMENTS = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE ORDER_GUIDE_STRUCT_DEL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getOrderGuideId());
        pstmt.setInt(i++,pData.getItemId());
        pstmt.setInt(i++,pData.getCategoryItemId());
        pstmt.setInt(i++,pData.getQuantity());
        pstmt.setString(i++,pData.getComments());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getOrderGuideStructDelId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_GUIDE_ID="+pData.getOrderGuideId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   CATEGORY_ITEM_ID="+pData.getCategoryItemId());
            log.debug("SQL:   QUANTITY="+pData.getQuantity());
            log.debug("SQL:   COMMENTS="+pData.getComments());
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
     * Deletes a OrderGuideStructDelData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderGuideStructDelId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderGuideStructDelId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ORDER_GUIDE_STRUCT_DEL WHERE ORDER_GUIDE_STRUCT_DEL_ID = " + pOrderGuideStructDelId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes OrderGuideStructDelData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ORDER_GUIDE_STRUCT_DEL");
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
     * Inserts a OrderGuideStructDelData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderGuideStructDelData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, OrderGuideStructDelData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ORDER_GUIDE_STRUCT_DEL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ORDER_GUIDE_STRUCT_DEL_ID,ORDER_GUIDE_ID,ITEM_ID,CATEGORY_ITEM_ID,QUANTITY,COMMENTS,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getOrderGuideStructDelId());
        pstmt.setInt(2+4,pData.getOrderGuideId());
        pstmt.setInt(3+4,pData.getItemId());
        pstmt.setInt(4+4,pData.getCategoryItemId());
        pstmt.setInt(5+4,pData.getQuantity());
        pstmt.setString(6+4,pData.getComments());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(8+4,pData.getAddBy());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(10+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a OrderGuideStructDelData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderGuideStructDelData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new OrderGuideStructDelData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderGuideStructDelData insert(Connection pCon, OrderGuideStructDelData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a OrderGuideStructDelData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderGuideStructDelData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderGuideStructDelData pData, boolean pLogFl)
        throws SQLException {
        OrderGuideStructDelData oldData = null;
        if(pLogFl) {
          int id = pData.getOrderGuideStructDelId();
          try {
          oldData = OrderGuideStructDelDataAccess.select(pCon,id);
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
     * Deletes a OrderGuideStructDelData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderGuideStructDelId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderGuideStructDelId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ORDER_GUIDE_STRUCT_DEL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_GUIDE_STRUCT_DEL d WHERE ORDER_GUIDE_STRUCT_DEL_ID = " + pOrderGuideStructDelId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pOrderGuideStructDelId);
        return n;
     }

    /**
     * Deletes OrderGuideStructDelData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ORDER_GUIDE_STRUCT_DEL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_GUIDE_STRUCT_DEL d ");
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

