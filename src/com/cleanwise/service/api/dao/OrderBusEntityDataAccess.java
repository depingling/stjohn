
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        OrderBusEntityDataAccess
 * Description:  This class is used to build access methods to the CLW_ORDER_BUS_ENTITY table.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.OrderBusEntityData;
import com.cleanwise.service.api.value.OrderBusEntityDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.sql.*;
import java.util.*;

/**
 * <code>OrderBusEntityDataAccess</code>
 */
public class OrderBusEntityDataAccess
{
    private static Category log = Category.getInstance(OrderBusEntityDataAccess.class.getName());

    /** <code>CLW_ORDER_BUS_ENTITY</code> table name */
    public static final String CLW_ORDER_BUS_ENTITY = "CLW_ORDER_BUS_ENTITY";
    
    /** <code>ORDER_BUS_ENTITY_ID</code> ORDER_BUS_ENTITY_ID column of table CLW_ORDER_BUS_ENTITY */
    public static final String ORDER_BUS_ENTITY_ID = "ORDER_BUS_ENTITY_ID";
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_ORDER_BUS_ENTITY */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_ORDER_BUS_ENTITY */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_ORDER_BUS_ENTITY */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>LONG_DESC</code> LONG_DESC column of table CLW_ORDER_BUS_ENTITY */
    public static final String LONG_DESC = "LONG_DESC";
    /** <code>BUDGET</code> BUDGET column of table CLW_ORDER_BUS_ENTITY */
    public static final String BUDGET = "BUDGET";
    /** <code>ACTUAL_TO_DATE</code> ACTUAL_TO_DATE column of table CLW_ORDER_BUS_ENTITY */
    public static final String ACTUAL_TO_DATE = "ACTUAL_TO_DATE";

    /**
     * Constructor.
     */
    public OrderBusEntityDataAccess() 
    {
    }

    /**
     * Gets a OrderBusEntityData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pOrderBusEntityId The key requested.
     * @return new OrderBusEntityData()
     * @throws            SQLException
     */
    public static OrderBusEntityData select(Connection pCon, int pOrderBusEntityId)
        throws SQLException, DataNotFoundException {
        OrderBusEntityData x=null;
        String sql="SELECT ORDER_BUS_ENTITY_ID,ORDER_ID,BUS_ENTITY_ID,SHORT_DESC,LONG_DESC,BUDGET,ACTUAL_TO_DATE FROM CLW_ORDER_BUS_ENTITY WHERE ORDER_BUS_ENTITY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_BUS_ENTITY_ID="+pOrderBusEntityId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pOrderBusEntityId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=OrderBusEntityData.createValue();
            
            x.setOrderBusEntityId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setShortDesc(rs.getString(4));
            x.setLongDesc(rs.getString(5));
            x.setBudget(rs.getBigDecimal(6));
            x.setActualToDate(rs.getBigDecimal(7));
            
        } else {
	    rs.close();
	    stmt.close();
	    throw new DataNotFoundException("ORDER_BUS_ENTITY_ID :" + pOrderBusEntityId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a OrderBusEntityDataVector object that consists
     * of OrderBusEntityData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new OrderBusEntityDataVector()
     * @throws            SQLException
     */
    public static OrderBusEntityDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
     * Gets a OrderBusEntityDataVector object that consists
     * of OrderBusEntityData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new OrderBusEntityDataVector()
     * @throws            SQLException
     */
    public static OrderBusEntityDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_BUS_ENTITY_ID,ORDER_ID,BUS_ENTITY_ID,SHORT_DESC,LONG_DESC,BUDGET,ACTUAL_TO_DATE FROM CLW_ORDER_BUS_ENTITY");
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
        if ( pMaxRows > 0 ) {
            // Insure that only positive values are set.
              stmt.setMaxRows(pMaxRows);
        }
        ResultSet rs=stmt.executeQuery(sql);
        OrderBusEntityDataVector v = new OrderBusEntityDataVector();
        OrderBusEntityData x=null;
        while (rs.next()) {
            // build the object
            x = OrderBusEntityData.createValue();
            
            x.setOrderBusEntityId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setShortDesc(rs.getString(4));
            x.setLongDesc(rs.getString(5));
            x.setBudget(rs.getBigDecimal(6));
            x.setActualToDate(rs.getBigDecimal(7));
            
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a OrderBusEntityDataVector object that consists
     * of OrderBusEntityData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for OrderBusEntityData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new OrderBusEntityDataVector()
     * @throws            SQLException
     */
    public static OrderBusEntityDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        OrderBusEntityDataVector v = new OrderBusEntityDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_BUS_ENTITY_ID,ORDER_ID,BUS_ENTITY_ID,SHORT_DESC,LONG_DESC,BUDGET,ACTUAL_TO_DATE FROM CLW_ORDER_BUS_ENTITY WHERE ORDER_BUS_ENTITY_ID IN (");
        
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
            OrderBusEntityData x=null;
            while (rs.next()) {
                // build the object
                x=OrderBusEntityData.createValue();
                
                x.setOrderBusEntityId(rs.getInt(1));
                x.setOrderId(rs.getInt(2));
                x.setBusEntityId(rs.getInt(3));
                x.setShortDesc(rs.getString(4));
                x.setLongDesc(rs.getString(5));
                x.setBudget(rs.getBigDecimal(6));
                x.setActualToDate(rs.getBigDecimal(7));
                
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a OrderBusEntityDataVector object of all
     * OrderBusEntityData objects in the database.
     * @param pCon An open database connection.
     * @return new OrderBusEntityDataVector()
     * @throws            SQLException
     */
    public static OrderBusEntityDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ORDER_BUS_ENTITY_ID,ORDER_ID,BUS_ENTITY_ID,SHORT_DESC,LONG_DESC,BUDGET,ACTUAL_TO_DATE FROM CLW_ORDER_BUS_ENTITY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        OrderBusEntityDataVector v = new OrderBusEntityDataVector();
        OrderBusEntityData x = null;
        while (rs.next()) {
            // build the object
            x = OrderBusEntityData.createValue();
            
            x.setOrderBusEntityId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setShortDesc(rs.getString(4));
            x.setLongDesc(rs.getString(5));
            x.setBudget(rs.getBigDecimal(6));
            x.setActualToDate(rs.getBigDecimal(7));
            
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * OrderBusEntityData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_BUS_ENTITY_ID FROM CLW_ORDER_BUS_ENTITY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_BUS_ENTITY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_BUS_ENTITY");
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
     * Inserts a OrderBusEntityData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderBusEntityData object to insert.
     * @return new OrderBusEntityData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderBusEntityData insert(Connection pCon, OrderBusEntityData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
	    log.debug("SELECT CLW_ORDER_BUS_ENTITY_SEQ.NEXTVAL FROM DUAL");
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ORDER_BUS_ENTITY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setOrderBusEntityId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ORDER_BUS_ENTITY (ORDER_BUS_ENTITY_ID,ORDER_ID,BUS_ENTITY_ID,SHORT_DESC,LONG_DESC,BUDGET,ACTUAL_TO_DATE) VALUES(?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setInt(1,pData.getOrderBusEntityId());
        if (pData.getOrderId() == 0) {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(2,pData.getOrderId());
        }

        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getBusEntityId());
        }

        pstmt.setString(4,pData.getShortDesc());
        pstmt.setString(5,pData.getLongDesc());
        pstmt.setBigDecimal(6,pData.getBudget());
        pstmt.setBigDecimal(7,pData.getActualToDate());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_BUS_ENTITY_ID="+pData.getOrderBusEntityId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   BUDGET="+pData.getBudget());
            log.debug("SQL:   ACTUAL_TO_DATE="+pData.getActualToDate());
	    log.debug("SQL: " + sql);
        }

        pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);
        return pData;
    }

    /**
     * Updates a OrderBusEntityData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderBusEntityData object to update. 
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderBusEntityData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ORDER_BUS_ENTITY SET ORDER_ID = ?,BUS_ENTITY_ID = ?,SHORT_DESC = ?,LONG_DESC = ?,BUDGET = ?,ACTUAL_TO_DATE = ? WHERE ORDER_BUS_ENTITY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        int i = 1;
        
        if (pData.getOrderId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getOrderId());
        }

        if (pData.getBusEntityId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getBusEntityId());
        }

        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getLongDesc());
        pstmt.setBigDecimal(i++,pData.getBudget());
        pstmt.setBigDecimal(i++,pData.getActualToDate());
        pstmt.setInt(i++,pData.getOrderBusEntityId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   LONG_DESC="+pData.getLongDesc());
            log.debug("SQL:   BUDGET="+pData.getBudget());
            log.debug("SQL:   ACTUAL_TO_DATE="+pData.getActualToDate());
	    log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a OrderBusEntityData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderBusEntityId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderBusEntityId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ORDER_BUS_ENTITY WHERE ORDER_BUS_ENTITY_ID = " + pOrderBusEntityId;

        if (log.isDebugEnabled()) {
	    log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes OrderBusEntityData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ORDER_BUS_ENTITY");
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
}

