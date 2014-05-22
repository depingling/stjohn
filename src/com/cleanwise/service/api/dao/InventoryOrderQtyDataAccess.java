
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        InventoryOrderQtyDataAccess
 * Description:  This class is used to build access methods to the CLW_INVENTORY_ORDER_QTY table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.InventoryOrderQtyData;
import com.cleanwise.service.api.value.InventoryOrderQtyDataVector;
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
 * <code>InventoryOrderQtyDataAccess</code>
 */
public class InventoryOrderQtyDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(InventoryOrderQtyDataAccess.class.getName());

    /** <code>CLW_INVENTORY_ORDER_QTY</code> table name */
	/* Primary key: INVENTORY_ORDER_QTY_ID */
	
    public static final String CLW_INVENTORY_ORDER_QTY = "CLW_INVENTORY_ORDER_QTY";
    
    /** <code>INVENTORY_ORDER_QTY_ID</code> INVENTORY_ORDER_QTY_ID column of table CLW_INVENTORY_ORDER_QTY */
    public static final String INVENTORY_ORDER_QTY_ID = "INVENTORY_ORDER_QTY_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_INVENTORY_ORDER_QTY */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_INVENTORY_ORDER_QTY */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>ITEM_TYPE</code> ITEM_TYPE column of table CLW_INVENTORY_ORDER_QTY */
    public static final String ITEM_TYPE = "ITEM_TYPE";
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_INVENTORY_ORDER_QTY */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>PAR</code> PAR column of table CLW_INVENTORY_ORDER_QTY */
    public static final String PAR = "PAR";
    /** <code>QTY_ON_HAND</code> QTY_ON_HAND column of table CLW_INVENTORY_ORDER_QTY */
    public static final String QTY_ON_HAND = "QTY_ON_HAND";
    /** <code>INVENTORY_QTY</code> INVENTORY_QTY column of table CLW_INVENTORY_ORDER_QTY */
    public static final String INVENTORY_QTY = "INVENTORY_QTY";
    /** <code>ORDER_QTY</code> ORDER_QTY column of table CLW_INVENTORY_ORDER_QTY */
    public static final String ORDER_QTY = "ORDER_QTY";
    /** <code>AUTO_ORDER_APPLIED</code> AUTO_ORDER_APPLIED column of table CLW_INVENTORY_ORDER_QTY */
    public static final String AUTO_ORDER_APPLIED = "AUTO_ORDER_APPLIED";
    /** <code>ENABLE_AUTO_ORDER</code> ENABLE_AUTO_ORDER column of table CLW_INVENTORY_ORDER_QTY */
    public static final String ENABLE_AUTO_ORDER = "ENABLE_AUTO_ORDER";
    /** <code>AUTO_ORDER_FACTOR</code> AUTO_ORDER_FACTOR column of table CLW_INVENTORY_ORDER_QTY */
    public static final String AUTO_ORDER_FACTOR = "AUTO_ORDER_FACTOR";
    /** <code>CUTOFF_DATE</code> CUTOFF_DATE column of table CLW_INVENTORY_ORDER_QTY */
    public static final String CUTOFF_DATE = "CUTOFF_DATE";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_INVENTORY_ORDER_QTY */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_INVENTORY_ORDER_QTY */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_INVENTORY_ORDER_QTY */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_INVENTORY_ORDER_QTY */
    public static final String MOD_BY = "MOD_BY";
    /** <code>PRICE</code> PRICE column of table CLW_INVENTORY_ORDER_QTY */
    public static final String PRICE = "PRICE";
    /** <code>CATEGORY</code> CATEGORY column of table CLW_INVENTORY_ORDER_QTY */
    public static final String CATEGORY = "CATEGORY";
    /** <code>COST_CENTER</code> COST_CENTER column of table CLW_INVENTORY_ORDER_QTY */
    public static final String COST_CENTER = "COST_CENTER";
    /** <code>DIST_ITEM_NUM</code> DIST_ITEM_NUM column of table CLW_INVENTORY_ORDER_QTY */
    public static final String DIST_ITEM_NUM = "DIST_ITEM_NUM";

    /**
     * Constructor.
     */
    public InventoryOrderQtyDataAccess()
    {
    }

    /**
     * Gets a InventoryOrderQtyData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pInventoryOrderQtyId The key requested.
     * @return new InventoryOrderQtyData()
     * @throws            SQLException
     */
    public static InventoryOrderQtyData select(Connection pCon, int pInventoryOrderQtyId)
        throws SQLException, DataNotFoundException {
        InventoryOrderQtyData x=null;
        String sql="SELECT INVENTORY_ORDER_QTY_ID,BUS_ENTITY_ID,ITEM_ID,ITEM_TYPE,ORDER_ID,PAR,QTY_ON_HAND,INVENTORY_QTY,ORDER_QTY,AUTO_ORDER_APPLIED,ENABLE_AUTO_ORDER,AUTO_ORDER_FACTOR,CUTOFF_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PRICE,CATEGORY,COST_CENTER,DIST_ITEM_NUM FROM CLW_INVENTORY_ORDER_QTY WHERE INVENTORY_ORDER_QTY_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pInventoryOrderQtyId=" + pInventoryOrderQtyId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pInventoryOrderQtyId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=InventoryOrderQtyData.createValue();
            
            x.setInventoryOrderQtyId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setItemType(rs.getString(4));
            x.setOrderId(rs.getInt(5));
            x.setPar(rs.getInt(6));
            x.setQtyOnHand(rs.getString(7));
            x.setInventoryQty(rs.getString(8));
            x.setOrderQty(rs.getInt(9));
            x.setAutoOrderApplied(rs.getString(10));
            x.setEnableAutoOrder(rs.getString(11));
            x.setAutoOrderFactor(rs.getBigDecimal(12));
            x.setCutoffDate(rs.getDate(13));
            x.setAddDate(rs.getTimestamp(14));
            x.setAddBy(rs.getString(15));
            x.setModDate(rs.getTimestamp(16));
            x.setModBy(rs.getString(17));
            x.setPrice(rs.getBigDecimal(18));
            x.setCategory(rs.getString(19));
            x.setCostCenter(rs.getString(20));
            x.setDistItemNum(rs.getString(21));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("INVENTORY_ORDER_QTY_ID :" + pInventoryOrderQtyId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a InventoryOrderQtyDataVector object that consists
     * of InventoryOrderQtyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new InventoryOrderQtyDataVector()
     * @throws            SQLException
     */
    public static InventoryOrderQtyDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a InventoryOrderQtyData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_INVENTORY_ORDER_QTY.INVENTORY_ORDER_QTY_ID,CLW_INVENTORY_ORDER_QTY.BUS_ENTITY_ID,CLW_INVENTORY_ORDER_QTY.ITEM_ID,CLW_INVENTORY_ORDER_QTY.ITEM_TYPE,CLW_INVENTORY_ORDER_QTY.ORDER_ID,CLW_INVENTORY_ORDER_QTY.PAR,CLW_INVENTORY_ORDER_QTY.QTY_ON_HAND,CLW_INVENTORY_ORDER_QTY.INVENTORY_QTY,CLW_INVENTORY_ORDER_QTY.ORDER_QTY,CLW_INVENTORY_ORDER_QTY.AUTO_ORDER_APPLIED,CLW_INVENTORY_ORDER_QTY.ENABLE_AUTO_ORDER,CLW_INVENTORY_ORDER_QTY.AUTO_ORDER_FACTOR,CLW_INVENTORY_ORDER_QTY.CUTOFF_DATE,CLW_INVENTORY_ORDER_QTY.ADD_DATE,CLW_INVENTORY_ORDER_QTY.ADD_BY,CLW_INVENTORY_ORDER_QTY.MOD_DATE,CLW_INVENTORY_ORDER_QTY.MOD_BY,CLW_INVENTORY_ORDER_QTY.PRICE,CLW_INVENTORY_ORDER_QTY.CATEGORY,CLW_INVENTORY_ORDER_QTY.COST_CENTER,CLW_INVENTORY_ORDER_QTY.DIST_ITEM_NUM";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated InventoryOrderQtyData Object.
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
    *@returns a populated InventoryOrderQtyData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         InventoryOrderQtyData x = InventoryOrderQtyData.createValue();
         
         x.setInventoryOrderQtyId(rs.getInt(1+offset));
         x.setBusEntityId(rs.getInt(2+offset));
         x.setItemId(rs.getInt(3+offset));
         x.setItemType(rs.getString(4+offset));
         x.setOrderId(rs.getInt(5+offset));
         x.setPar(rs.getInt(6+offset));
         x.setQtyOnHand(rs.getString(7+offset));
         x.setInventoryQty(rs.getString(8+offset));
         x.setOrderQty(rs.getInt(9+offset));
         x.setAutoOrderApplied(rs.getString(10+offset));
         x.setEnableAutoOrder(rs.getString(11+offset));
         x.setAutoOrderFactor(rs.getBigDecimal(12+offset));
         x.setCutoffDate(rs.getDate(13+offset));
         x.setAddDate(rs.getTimestamp(14+offset));
         x.setAddBy(rs.getString(15+offset));
         x.setModDate(rs.getTimestamp(16+offset));
         x.setModBy(rs.getString(17+offset));
         x.setPrice(rs.getBigDecimal(18+offset));
         x.setCategory(rs.getString(19+offset));
         x.setCostCenter(rs.getString(20+offset));
         x.setDistItemNum(rs.getString(21+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the InventoryOrderQtyData Object represents.
    */
    public int getColumnCount(){
        return 21;
    }

    /**
     * Gets a InventoryOrderQtyDataVector object that consists
     * of InventoryOrderQtyData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new InventoryOrderQtyDataVector()
     * @throws            SQLException
     */
    public static InventoryOrderQtyDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT INVENTORY_ORDER_QTY_ID,BUS_ENTITY_ID,ITEM_ID,ITEM_TYPE,ORDER_ID,PAR,QTY_ON_HAND,INVENTORY_QTY,ORDER_QTY,AUTO_ORDER_APPLIED,ENABLE_AUTO_ORDER,AUTO_ORDER_FACTOR,CUTOFF_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PRICE,CATEGORY,COST_CENTER,DIST_ITEM_NUM FROM CLW_INVENTORY_ORDER_QTY");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_INVENTORY_ORDER_QTY.INVENTORY_ORDER_QTY_ID,CLW_INVENTORY_ORDER_QTY.BUS_ENTITY_ID,CLW_INVENTORY_ORDER_QTY.ITEM_ID,CLW_INVENTORY_ORDER_QTY.ITEM_TYPE,CLW_INVENTORY_ORDER_QTY.ORDER_ID,CLW_INVENTORY_ORDER_QTY.PAR,CLW_INVENTORY_ORDER_QTY.QTY_ON_HAND,CLW_INVENTORY_ORDER_QTY.INVENTORY_QTY,CLW_INVENTORY_ORDER_QTY.ORDER_QTY,CLW_INVENTORY_ORDER_QTY.AUTO_ORDER_APPLIED,CLW_INVENTORY_ORDER_QTY.ENABLE_AUTO_ORDER,CLW_INVENTORY_ORDER_QTY.AUTO_ORDER_FACTOR,CLW_INVENTORY_ORDER_QTY.CUTOFF_DATE,CLW_INVENTORY_ORDER_QTY.ADD_DATE,CLW_INVENTORY_ORDER_QTY.ADD_BY,CLW_INVENTORY_ORDER_QTY.MOD_DATE,CLW_INVENTORY_ORDER_QTY.MOD_BY,CLW_INVENTORY_ORDER_QTY.PRICE,CLW_INVENTORY_ORDER_QTY.CATEGORY,CLW_INVENTORY_ORDER_QTY.COST_CENTER,CLW_INVENTORY_ORDER_QTY.DIST_ITEM_NUM FROM CLW_INVENTORY_ORDER_QTY");
                where = pCriteria.getSqlClause("CLW_INVENTORY_ORDER_QTY");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_INVENTORY_ORDER_QTY.equals(otherTable)){
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
        InventoryOrderQtyDataVector v = new InventoryOrderQtyDataVector();
        while (rs.next()) {
            InventoryOrderQtyData x = InventoryOrderQtyData.createValue();
            
            x.setInventoryOrderQtyId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setItemType(rs.getString(4));
            x.setOrderId(rs.getInt(5));
            x.setPar(rs.getInt(6));
            x.setQtyOnHand(rs.getString(7));
            x.setInventoryQty(rs.getString(8));
            x.setOrderQty(rs.getInt(9));
            x.setAutoOrderApplied(rs.getString(10));
            x.setEnableAutoOrder(rs.getString(11));
            x.setAutoOrderFactor(rs.getBigDecimal(12));
            x.setCutoffDate(rs.getDate(13));
            x.setAddDate(rs.getTimestamp(14));
            x.setAddBy(rs.getString(15));
            x.setModDate(rs.getTimestamp(16));
            x.setModBy(rs.getString(17));
            x.setPrice(rs.getBigDecimal(18));
            x.setCategory(rs.getString(19));
            x.setCostCenter(rs.getString(20));
            x.setDistItemNum(rs.getString(21));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a InventoryOrderQtyDataVector object that consists
     * of InventoryOrderQtyData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for InventoryOrderQtyData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new InventoryOrderQtyDataVector()
     * @throws            SQLException
     */
    public static InventoryOrderQtyDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        InventoryOrderQtyDataVector v = new InventoryOrderQtyDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT INVENTORY_ORDER_QTY_ID,BUS_ENTITY_ID,ITEM_ID,ITEM_TYPE,ORDER_ID,PAR,QTY_ON_HAND,INVENTORY_QTY,ORDER_QTY,AUTO_ORDER_APPLIED,ENABLE_AUTO_ORDER,AUTO_ORDER_FACTOR,CUTOFF_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PRICE,CATEGORY,COST_CENTER,DIST_ITEM_NUM FROM CLW_INVENTORY_ORDER_QTY WHERE INVENTORY_ORDER_QTY_ID IN (");

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
            InventoryOrderQtyData x=null;
            while (rs.next()) {
                // build the object
                x=InventoryOrderQtyData.createValue();
                
                x.setInventoryOrderQtyId(rs.getInt(1));
                x.setBusEntityId(rs.getInt(2));
                x.setItemId(rs.getInt(3));
                x.setItemType(rs.getString(4));
                x.setOrderId(rs.getInt(5));
                x.setPar(rs.getInt(6));
                x.setQtyOnHand(rs.getString(7));
                x.setInventoryQty(rs.getString(8));
                x.setOrderQty(rs.getInt(9));
                x.setAutoOrderApplied(rs.getString(10));
                x.setEnableAutoOrder(rs.getString(11));
                x.setAutoOrderFactor(rs.getBigDecimal(12));
                x.setCutoffDate(rs.getDate(13));
                x.setAddDate(rs.getTimestamp(14));
                x.setAddBy(rs.getString(15));
                x.setModDate(rs.getTimestamp(16));
                x.setModBy(rs.getString(17));
                x.setPrice(rs.getBigDecimal(18));
                x.setCategory(rs.getString(19));
                x.setCostCenter(rs.getString(20));
                x.setDistItemNum(rs.getString(21));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a InventoryOrderQtyDataVector object of all
     * InventoryOrderQtyData objects in the database.
     * @param pCon An open database connection.
     * @return new InventoryOrderQtyDataVector()
     * @throws            SQLException
     */
    public static InventoryOrderQtyDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT INVENTORY_ORDER_QTY_ID,BUS_ENTITY_ID,ITEM_ID,ITEM_TYPE,ORDER_ID,PAR,QTY_ON_HAND,INVENTORY_QTY,ORDER_QTY,AUTO_ORDER_APPLIED,ENABLE_AUTO_ORDER,AUTO_ORDER_FACTOR,CUTOFF_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PRICE,CATEGORY,COST_CENTER,DIST_ITEM_NUM FROM CLW_INVENTORY_ORDER_QTY";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        InventoryOrderQtyDataVector v = new InventoryOrderQtyDataVector();
        InventoryOrderQtyData x = null;
        while (rs.next()) {
            // build the object
            x = InventoryOrderQtyData.createValue();
            
            x.setInventoryOrderQtyId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setItemType(rs.getString(4));
            x.setOrderId(rs.getInt(5));
            x.setPar(rs.getInt(6));
            x.setQtyOnHand(rs.getString(7));
            x.setInventoryQty(rs.getString(8));
            x.setOrderQty(rs.getInt(9));
            x.setAutoOrderApplied(rs.getString(10));
            x.setEnableAutoOrder(rs.getString(11));
            x.setAutoOrderFactor(rs.getBigDecimal(12));
            x.setCutoffDate(rs.getDate(13));
            x.setAddDate(rs.getTimestamp(14));
            x.setAddBy(rs.getString(15));
            x.setModDate(rs.getTimestamp(16));
            x.setModBy(rs.getString(17));
            x.setPrice(rs.getBigDecimal(18));
            x.setCategory(rs.getString(19));
            x.setCostCenter(rs.getString(20));
            x.setDistItemNum(rs.getString(21));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * InventoryOrderQtyData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT INVENTORY_ORDER_QTY_ID FROM CLW_INVENTORY_ORDER_QTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVENTORY_ORDER_QTY");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVENTORY_ORDER_QTY");
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
     * Inserts a InventoryOrderQtyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InventoryOrderQtyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new InventoryOrderQtyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InventoryOrderQtyData insert(Connection pCon, InventoryOrderQtyData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_INVENTORY_ORDER_QTY_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_INVENTORY_ORDER_QTY_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setInventoryOrderQtyId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_INVENTORY_ORDER_QTY (INVENTORY_ORDER_QTY_ID,BUS_ENTITY_ID,ITEM_ID,ITEM_TYPE,ORDER_ID,PAR,QTY_ON_HAND,INVENTORY_QTY,ORDER_QTY,AUTO_ORDER_APPLIED,ENABLE_AUTO_ORDER,AUTO_ORDER_FACTOR,CUTOFF_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PRICE,CATEGORY,COST_CENTER,DIST_ITEM_NUM) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getInventoryOrderQtyId());
        pstmt.setInt(2,pData.getBusEntityId());
        pstmt.setInt(3,pData.getItemId());
        pstmt.setString(4,pData.getItemType());
        pstmt.setInt(5,pData.getOrderId());
        pstmt.setInt(6,pData.getPar());
        pstmt.setString(7,pData.getQtyOnHand());
        pstmt.setString(8,pData.getInventoryQty());
        pstmt.setInt(9,pData.getOrderQty());
        pstmt.setString(10,pData.getAutoOrderApplied());
        pstmt.setString(11,pData.getEnableAutoOrder());
        pstmt.setBigDecimal(12,pData.getAutoOrderFactor());
        pstmt.setDate(13,DBAccess.toSQLDate(pData.getCutoffDate()));
        pstmt.setTimestamp(14,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(15,pData.getAddBy());
        pstmt.setTimestamp(16,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(17,pData.getModBy());
        pstmt.setBigDecimal(18,pData.getPrice());
        pstmt.setString(19,pData.getCategory());
        pstmt.setString(20,pData.getCostCenter());
        pstmt.setString(21,pData.getDistItemNum());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   INVENTORY_ORDER_QTY_ID="+pData.getInventoryOrderQtyId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   ITEM_TYPE="+pData.getItemType());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   PAR="+pData.getPar());
            log.debug("SQL:   QTY_ON_HAND="+pData.getQtyOnHand());
            log.debug("SQL:   INVENTORY_QTY="+pData.getInventoryQty());
            log.debug("SQL:   ORDER_QTY="+pData.getOrderQty());
            log.debug("SQL:   AUTO_ORDER_APPLIED="+pData.getAutoOrderApplied());
            log.debug("SQL:   ENABLE_AUTO_ORDER="+pData.getEnableAutoOrder());
            log.debug("SQL:   AUTO_ORDER_FACTOR="+pData.getAutoOrderFactor());
            log.debug("SQL:   CUTOFF_DATE="+pData.getCutoffDate());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   PRICE="+pData.getPrice());
            log.debug("SQL:   CATEGORY="+pData.getCategory());
            log.debug("SQL:   COST_CENTER="+pData.getCostCenter());
            log.debug("SQL:   DIST_ITEM_NUM="+pData.getDistItemNum());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setInventoryOrderQtyId(0);
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
     * Updates a InventoryOrderQtyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InventoryOrderQtyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InventoryOrderQtyData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_INVENTORY_ORDER_QTY SET BUS_ENTITY_ID = ?,ITEM_ID = ?,ITEM_TYPE = ?,ORDER_ID = ?,PAR = ?,QTY_ON_HAND = ?,INVENTORY_QTY = ?,ORDER_QTY = ?,AUTO_ORDER_APPLIED = ?,ENABLE_AUTO_ORDER = ?,AUTO_ORDER_FACTOR = ?,CUTOFF_DATE = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,PRICE = ?,CATEGORY = ?,COST_CENTER = ?,DIST_ITEM_NUM = ? WHERE INVENTORY_ORDER_QTY_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setInt(i++,pData.getItemId());
        pstmt.setString(i++,pData.getItemType());
        pstmt.setInt(i++,pData.getOrderId());
        pstmt.setInt(i++,pData.getPar());
        pstmt.setString(i++,pData.getQtyOnHand());
        pstmt.setString(i++,pData.getInventoryQty());
        pstmt.setInt(i++,pData.getOrderQty());
        pstmt.setString(i++,pData.getAutoOrderApplied());
        pstmt.setString(i++,pData.getEnableAutoOrder());
        pstmt.setBigDecimal(i++,pData.getAutoOrderFactor());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getCutoffDate()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setBigDecimal(i++,pData.getPrice());
        pstmt.setString(i++,pData.getCategory());
        pstmt.setString(i++,pData.getCostCenter());
        pstmt.setString(i++,pData.getDistItemNum());
        pstmt.setInt(i++,pData.getInventoryOrderQtyId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   ITEM_TYPE="+pData.getItemType());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   PAR="+pData.getPar());
            log.debug("SQL:   QTY_ON_HAND="+pData.getQtyOnHand());
            log.debug("SQL:   INVENTORY_QTY="+pData.getInventoryQty());
            log.debug("SQL:   ORDER_QTY="+pData.getOrderQty());
            log.debug("SQL:   AUTO_ORDER_APPLIED="+pData.getAutoOrderApplied());
            log.debug("SQL:   ENABLE_AUTO_ORDER="+pData.getEnableAutoOrder());
            log.debug("SQL:   AUTO_ORDER_FACTOR="+pData.getAutoOrderFactor());
            log.debug("SQL:   CUTOFF_DATE="+pData.getCutoffDate());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   PRICE="+pData.getPrice());
            log.debug("SQL:   CATEGORY="+pData.getCategory());
            log.debug("SQL:   COST_CENTER="+pData.getCostCenter());
            log.debug("SQL:   DIST_ITEM_NUM="+pData.getDistItemNum());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a InventoryOrderQtyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInventoryOrderQtyId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInventoryOrderQtyId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_INVENTORY_ORDER_QTY WHERE INVENTORY_ORDER_QTY_ID = " + pInventoryOrderQtyId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes InventoryOrderQtyData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_INVENTORY_ORDER_QTY");
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
     * Inserts a InventoryOrderQtyData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InventoryOrderQtyData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, InventoryOrderQtyData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_INVENTORY_ORDER_QTY (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "INVENTORY_ORDER_QTY_ID,BUS_ENTITY_ID,ITEM_ID,ITEM_TYPE,ORDER_ID,PAR,QTY_ON_HAND,INVENTORY_QTY,ORDER_QTY,AUTO_ORDER_APPLIED,ENABLE_AUTO_ORDER,AUTO_ORDER_FACTOR,CUTOFF_DATE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PRICE,CATEGORY,COST_CENTER,DIST_ITEM_NUM) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getInventoryOrderQtyId());
        pstmt.setInt(2+4,pData.getBusEntityId());
        pstmt.setInt(3+4,pData.getItemId());
        pstmt.setString(4+4,pData.getItemType());
        pstmt.setInt(5+4,pData.getOrderId());
        pstmt.setInt(6+4,pData.getPar());
        pstmt.setString(7+4,pData.getQtyOnHand());
        pstmt.setString(8+4,pData.getInventoryQty());
        pstmt.setInt(9+4,pData.getOrderQty());
        pstmt.setString(10+4,pData.getAutoOrderApplied());
        pstmt.setString(11+4,pData.getEnableAutoOrder());
        pstmt.setBigDecimal(12+4,pData.getAutoOrderFactor());
        pstmt.setDate(13+4,DBAccess.toSQLDate(pData.getCutoffDate()));
        pstmt.setTimestamp(14+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(15+4,pData.getAddBy());
        pstmt.setTimestamp(16+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(17+4,pData.getModBy());
        pstmt.setBigDecimal(18+4,pData.getPrice());
        pstmt.setString(19+4,pData.getCategory());
        pstmt.setString(20+4,pData.getCostCenter());
        pstmt.setString(21+4,pData.getDistItemNum());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a InventoryOrderQtyData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InventoryOrderQtyData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new InventoryOrderQtyData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InventoryOrderQtyData insert(Connection pCon, InventoryOrderQtyData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a InventoryOrderQtyData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InventoryOrderQtyData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InventoryOrderQtyData pData, boolean pLogFl)
        throws SQLException {
        InventoryOrderQtyData oldData = null;
        if(pLogFl) {
          int id = pData.getInventoryOrderQtyId();
          try {
          oldData = InventoryOrderQtyDataAccess.select(pCon,id);
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
     * Deletes a InventoryOrderQtyData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInventoryOrderQtyId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInventoryOrderQtyId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_INVENTORY_ORDER_QTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INVENTORY_ORDER_QTY d WHERE INVENTORY_ORDER_QTY_ID = " + pInventoryOrderQtyId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pInventoryOrderQtyId);
        return n;
     }

    /**
     * Deletes InventoryOrderQtyData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_INVENTORY_ORDER_QTY SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INVENTORY_ORDER_QTY d ");
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

