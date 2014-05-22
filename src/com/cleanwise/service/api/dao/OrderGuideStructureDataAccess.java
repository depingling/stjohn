
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        OrderGuideStructureDataAccess
 * Description:  This class is used to build access methods to the CLW_ORDER_GUIDE_STRUCTURE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.OrderGuideStructureData;
import com.cleanwise.service.api.value.OrderGuideStructureDataVector;
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
 * <code>OrderGuideStructureDataAccess</code>
 */
public class OrderGuideStructureDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(OrderGuideStructureDataAccess.class.getName());

    /** <code>CLW_ORDER_GUIDE_STRUCTURE</code> table name */
	/* Primary key: ORDER_GUIDE_STRUCTURE_ID */
	
    public static final String CLW_ORDER_GUIDE_STRUCTURE = "CLW_ORDER_GUIDE_STRUCTURE";
    
    /** <code>ORDER_GUIDE_STRUCTURE_ID</code> ORDER_GUIDE_STRUCTURE_ID column of table CLW_ORDER_GUIDE_STRUCTURE */
    public static final String ORDER_GUIDE_STRUCTURE_ID = "ORDER_GUIDE_STRUCTURE_ID";
    /** <code>ORDER_GUIDE_ID</code> ORDER_GUIDE_ID column of table CLW_ORDER_GUIDE_STRUCTURE */
    public static final String ORDER_GUIDE_ID = "ORDER_GUIDE_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_ORDER_GUIDE_STRUCTURE */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>CATEGORY_ITEM_ID</code> CATEGORY_ITEM_ID column of table CLW_ORDER_GUIDE_STRUCTURE */
    public static final String CATEGORY_ITEM_ID = "CATEGORY_ITEM_ID";
    /** <code>CUST_CATEGORY</code> CUST_CATEGORY column of table CLW_ORDER_GUIDE_STRUCTURE */
    public static final String CUST_CATEGORY = "CUST_CATEGORY";
    /** <code>QUANTITY</code> QUANTITY column of table CLW_ORDER_GUIDE_STRUCTURE */
    public static final String QUANTITY = "QUANTITY";
    /** <code>SORT_ORDER</code> SORT_ORDER column of table CLW_ORDER_GUIDE_STRUCTURE */
    public static final String SORT_ORDER = "SORT_ORDER";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ORDER_GUIDE_STRUCTURE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ORDER_GUIDE_STRUCTURE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ORDER_GUIDE_STRUCTURE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ORDER_GUIDE_STRUCTURE */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public OrderGuideStructureDataAccess()
    {
    }

    /**
     * Gets a OrderGuideStructureData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pOrderGuideStructureId The key requested.
     * @return new OrderGuideStructureData()
     * @throws            SQLException
     */
    public static OrderGuideStructureData select(Connection pCon, int pOrderGuideStructureId)
        throws SQLException, DataNotFoundException {
        OrderGuideStructureData x=null;
        String sql="SELECT ORDER_GUIDE_STRUCTURE_ID,ORDER_GUIDE_ID,ITEM_ID,CATEGORY_ITEM_ID,CUST_CATEGORY,QUANTITY,SORT_ORDER,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_GUIDE_STRUCTURE WHERE ORDER_GUIDE_STRUCTURE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pOrderGuideStructureId=" + pOrderGuideStructureId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pOrderGuideStructureId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=OrderGuideStructureData.createValue();
            
            x.setOrderGuideStructureId(rs.getInt(1));
            x.setOrderGuideId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setCategoryItemId(rs.getInt(4));
            x.setCustCategory(rs.getString(5));
            x.setQuantity(rs.getInt(6));
            x.setSortOrder(rs.getInt(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ORDER_GUIDE_STRUCTURE_ID :" + pOrderGuideStructureId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a OrderGuideStructureDataVector object that consists
     * of OrderGuideStructureData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new OrderGuideStructureDataVector()
     * @throws            SQLException
     */
    public static OrderGuideStructureDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a OrderGuideStructureData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ORDER_GUIDE_STRUCTURE.ORDER_GUIDE_STRUCTURE_ID,CLW_ORDER_GUIDE_STRUCTURE.ORDER_GUIDE_ID,CLW_ORDER_GUIDE_STRUCTURE.ITEM_ID,CLW_ORDER_GUIDE_STRUCTURE.CATEGORY_ITEM_ID,CLW_ORDER_GUIDE_STRUCTURE.CUST_CATEGORY,CLW_ORDER_GUIDE_STRUCTURE.QUANTITY,CLW_ORDER_GUIDE_STRUCTURE.SORT_ORDER,CLW_ORDER_GUIDE_STRUCTURE.ADD_DATE,CLW_ORDER_GUIDE_STRUCTURE.ADD_BY,CLW_ORDER_GUIDE_STRUCTURE.MOD_DATE,CLW_ORDER_GUIDE_STRUCTURE.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated OrderGuideStructureData Object.
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
    *@returns a populated OrderGuideStructureData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         OrderGuideStructureData x = OrderGuideStructureData.createValue();
         
         x.setOrderGuideStructureId(rs.getInt(1+offset));
         x.setOrderGuideId(rs.getInt(2+offset));
         x.setItemId(rs.getInt(3+offset));
         x.setCategoryItemId(rs.getInt(4+offset));
         x.setCustCategory(rs.getString(5+offset));
         x.setQuantity(rs.getInt(6+offset));
         x.setSortOrder(rs.getInt(7+offset));
         x.setAddDate(rs.getTimestamp(8+offset));
         x.setAddBy(rs.getString(9+offset));
         x.setModDate(rs.getTimestamp(10+offset));
         x.setModBy(rs.getString(11+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the OrderGuideStructureData Object represents.
    */
    public int getColumnCount(){
        return 11;
    }

    /**
     * Gets a OrderGuideStructureDataVector object that consists
     * of OrderGuideStructureData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new OrderGuideStructureDataVector()
     * @throws            SQLException
     */
    public static OrderGuideStructureDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ORDER_GUIDE_STRUCTURE_ID,ORDER_GUIDE_ID,ITEM_ID,CATEGORY_ITEM_ID,CUST_CATEGORY,QUANTITY,SORT_ORDER,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_GUIDE_STRUCTURE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ORDER_GUIDE_STRUCTURE.ORDER_GUIDE_STRUCTURE_ID,CLW_ORDER_GUIDE_STRUCTURE.ORDER_GUIDE_ID,CLW_ORDER_GUIDE_STRUCTURE.ITEM_ID,CLW_ORDER_GUIDE_STRUCTURE.CATEGORY_ITEM_ID,CLW_ORDER_GUIDE_STRUCTURE.CUST_CATEGORY,CLW_ORDER_GUIDE_STRUCTURE.QUANTITY,CLW_ORDER_GUIDE_STRUCTURE.SORT_ORDER,CLW_ORDER_GUIDE_STRUCTURE.ADD_DATE,CLW_ORDER_GUIDE_STRUCTURE.ADD_BY,CLW_ORDER_GUIDE_STRUCTURE.MOD_DATE,CLW_ORDER_GUIDE_STRUCTURE.MOD_BY FROM CLW_ORDER_GUIDE_STRUCTURE");
                where = pCriteria.getSqlClause("CLW_ORDER_GUIDE_STRUCTURE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ORDER_GUIDE_STRUCTURE.equals(otherTable)){
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
        OrderGuideStructureDataVector v = new OrderGuideStructureDataVector();
        while (rs.next()) {
            OrderGuideStructureData x = OrderGuideStructureData.createValue();
            
            x.setOrderGuideStructureId(rs.getInt(1));
            x.setOrderGuideId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setCategoryItemId(rs.getInt(4));
            x.setCustCategory(rs.getString(5));
            x.setQuantity(rs.getInt(6));
            x.setSortOrder(rs.getInt(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a OrderGuideStructureDataVector object that consists
     * of OrderGuideStructureData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for OrderGuideStructureData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new OrderGuideStructureDataVector()
     * @throws            SQLException
     */
    public static OrderGuideStructureDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        OrderGuideStructureDataVector v = new OrderGuideStructureDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_GUIDE_STRUCTURE_ID,ORDER_GUIDE_ID,ITEM_ID,CATEGORY_ITEM_ID,CUST_CATEGORY,QUANTITY,SORT_ORDER,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_GUIDE_STRUCTURE WHERE ORDER_GUIDE_STRUCTURE_ID IN (");

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
            OrderGuideStructureData x=null;
            while (rs.next()) {
                // build the object
                x=OrderGuideStructureData.createValue();
                
                x.setOrderGuideStructureId(rs.getInt(1));
                x.setOrderGuideId(rs.getInt(2));
                x.setItemId(rs.getInt(3));
                x.setCategoryItemId(rs.getInt(4));
                x.setCustCategory(rs.getString(5));
                x.setQuantity(rs.getInt(6));
                x.setSortOrder(rs.getInt(7));
                x.setAddDate(rs.getTimestamp(8));
                x.setAddBy(rs.getString(9));
                x.setModDate(rs.getTimestamp(10));
                x.setModBy(rs.getString(11));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a OrderGuideStructureDataVector object of all
     * OrderGuideStructureData objects in the database.
     * @param pCon An open database connection.
     * @return new OrderGuideStructureDataVector()
     * @throws            SQLException
     */
    public static OrderGuideStructureDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ORDER_GUIDE_STRUCTURE_ID,ORDER_GUIDE_ID,ITEM_ID,CATEGORY_ITEM_ID,CUST_CATEGORY,QUANTITY,SORT_ORDER,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_GUIDE_STRUCTURE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        OrderGuideStructureDataVector v = new OrderGuideStructureDataVector();
        OrderGuideStructureData x = null;
        while (rs.next()) {
            // build the object
            x = OrderGuideStructureData.createValue();
            
            x.setOrderGuideStructureId(rs.getInt(1));
            x.setOrderGuideId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setCategoryItemId(rs.getInt(4));
            x.setCustCategory(rs.getString(5));
            x.setQuantity(rs.getInt(6));
            x.setSortOrder(rs.getInt(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * OrderGuideStructureData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_GUIDE_STRUCTURE_ID FROM CLW_ORDER_GUIDE_STRUCTURE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_GUIDE_STRUCTURE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_GUIDE_STRUCTURE");
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
     * Inserts a OrderGuideStructureData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderGuideStructureData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new OrderGuideStructureData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderGuideStructureData insert(Connection pCon, OrderGuideStructureData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ORDER_GUIDE_STRUCTURE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ORDER_GUIDE_STRUCTURE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setOrderGuideStructureId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ORDER_GUIDE_STRUCTURE (ORDER_GUIDE_STRUCTURE_ID,ORDER_GUIDE_ID,ITEM_ID,CATEGORY_ITEM_ID,CUST_CATEGORY,QUANTITY,SORT_ORDER,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getOrderGuideStructureId());
        pstmt.setInt(2,pData.getOrderGuideId());
        pstmt.setInt(3,pData.getItemId());
        pstmt.setInt(4,pData.getCategoryItemId());
        pstmt.setString(5,pData.getCustCategory());
        pstmt.setInt(6,pData.getQuantity());
        pstmt.setInt(7,pData.getSortOrder());
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9,pData.getAddBy());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_GUIDE_STRUCTURE_ID="+pData.getOrderGuideStructureId());
            log.debug("SQL:   ORDER_GUIDE_ID="+pData.getOrderGuideId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   CATEGORY_ITEM_ID="+pData.getCategoryItemId());
            log.debug("SQL:   CUST_CATEGORY="+pData.getCustCategory());
            log.debug("SQL:   QUANTITY="+pData.getQuantity());
            log.debug("SQL:   SORT_ORDER="+pData.getSortOrder());
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
        pData.setOrderGuideStructureId(0);
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
     * Updates a OrderGuideStructureData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderGuideStructureData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderGuideStructureData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ORDER_GUIDE_STRUCTURE SET ORDER_GUIDE_ID = ?,ITEM_ID = ?,CATEGORY_ITEM_ID = ?,CUST_CATEGORY = ?,QUANTITY = ?,SORT_ORDER = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE ORDER_GUIDE_STRUCTURE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getOrderGuideId());
        pstmt.setInt(i++,pData.getItemId());
        pstmt.setInt(i++,pData.getCategoryItemId());
        pstmt.setString(i++,pData.getCustCategory());
        pstmt.setInt(i++,pData.getQuantity());
        pstmt.setInt(i++,pData.getSortOrder());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getOrderGuideStructureId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_GUIDE_ID="+pData.getOrderGuideId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   CATEGORY_ITEM_ID="+pData.getCategoryItemId());
            log.debug("SQL:   CUST_CATEGORY="+pData.getCustCategory());
            log.debug("SQL:   QUANTITY="+pData.getQuantity());
            log.debug("SQL:   SORT_ORDER="+pData.getSortOrder());
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
     * Deletes a OrderGuideStructureData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderGuideStructureId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderGuideStructureId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ORDER_GUIDE_STRUCTURE WHERE ORDER_GUIDE_STRUCTURE_ID = " + pOrderGuideStructureId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes OrderGuideStructureData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ORDER_GUIDE_STRUCTURE");
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
     * Inserts a OrderGuideStructureData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderGuideStructureData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, OrderGuideStructureData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ORDER_GUIDE_STRUCTURE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ORDER_GUIDE_STRUCTURE_ID,ORDER_GUIDE_ID,ITEM_ID,CATEGORY_ITEM_ID,CUST_CATEGORY,QUANTITY,SORT_ORDER,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getOrderGuideStructureId());
        pstmt.setInt(2+4,pData.getOrderGuideId());
        pstmt.setInt(3+4,pData.getItemId());
        pstmt.setInt(4+4,pData.getCategoryItemId());
        pstmt.setString(5+4,pData.getCustCategory());
        pstmt.setInt(6+4,pData.getQuantity());
        pstmt.setInt(7+4,pData.getSortOrder());
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9+4,pData.getAddBy());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a OrderGuideStructureData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderGuideStructureData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new OrderGuideStructureData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderGuideStructureData insert(Connection pCon, OrderGuideStructureData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a OrderGuideStructureData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderGuideStructureData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderGuideStructureData pData, boolean pLogFl)
        throws SQLException {
        OrderGuideStructureData oldData = null;
        if(pLogFl) {
          int id = pData.getOrderGuideStructureId();
          try {
          oldData = OrderGuideStructureDataAccess.select(pCon,id);
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
     * Deletes a OrderGuideStructureData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderGuideStructureId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderGuideStructureId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ORDER_GUIDE_STRUCTURE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_GUIDE_STRUCTURE d WHERE ORDER_GUIDE_STRUCTURE_ID = " + pOrderGuideStructureId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pOrderGuideStructureId);
        return n;
     }

    /**
     * Deletes OrderGuideStructureData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ORDER_GUIDE_STRUCTURE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_GUIDE_STRUCTURE d ");
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

