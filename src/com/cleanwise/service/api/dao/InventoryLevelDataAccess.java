
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        InventoryLevelDataAccess
 * Description:  This class is used to build access methods to the CLW_INVENTORY_LEVEL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.InventoryLevelData;
import com.cleanwise.service.api.value.InventoryLevelDataVector;
import com.cleanwise.service.api.framework.DataAccessImpl;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.cachecos.CachecosManager;
import com.cleanwise.service.api.cachecos.Cachecos;
import org.apache.log4j.Category;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.*;

/**
 * <code>InventoryLevelDataAccess</code>
 */
public class InventoryLevelDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(InventoryLevelDataAccess.class.getName());

    /** <code>CLW_INVENTORY_LEVEL</code> table name */
	/* Primary key: INVENTORY_LEVEL_ID */
	
    public static final String CLW_INVENTORY_LEVEL = "CLW_INVENTORY_LEVEL";
    
    /** <code>INVENTORY_LEVEL_ID</code> INVENTORY_LEVEL_ID column of table CLW_INVENTORY_LEVEL */
    public static final String INVENTORY_LEVEL_ID = "INVENTORY_LEVEL_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_INVENTORY_LEVEL */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_INVENTORY_LEVEL */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>QTY_ON_HAND</code> QTY_ON_HAND column of table CLW_INVENTORY_LEVEL */
    public static final String QTY_ON_HAND = "QTY_ON_HAND";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_INVENTORY_LEVEL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_INVENTORY_LEVEL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_INVENTORY_LEVEL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_INVENTORY_LEVEL */
    public static final String MOD_BY = "MOD_BY";
    /** <code>PARS_MOD_DATE</code> PARS_MOD_DATE column of table CLW_INVENTORY_LEVEL */
    public static final String PARS_MOD_DATE = "PARS_MOD_DATE";
    /** <code>PARS_MOD_BY</code> PARS_MOD_BY column of table CLW_INVENTORY_LEVEL */
    public static final String PARS_MOD_BY = "PARS_MOD_BY";
    /** <code>ORDER_QTY</code> ORDER_QTY column of table CLW_INVENTORY_LEVEL */
    public static final String ORDER_QTY = "ORDER_QTY";
    /** <code>INITIAL_QTY_ON_HAND</code> INITIAL_QTY_ON_HAND column of table CLW_INVENTORY_LEVEL */
    public static final String INITIAL_QTY_ON_HAND = "INITIAL_QTY_ON_HAND";

    /**
     * Constructor.
     */
    public InventoryLevelDataAccess()
    {
    }

    /**
     * Gets a InventoryLevelData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pInventoryLevelId The key requested.
     * @return new InventoryLevelData()
     * @throws            SQLException
     */
    public static InventoryLevelData select(Connection pCon, int pInventoryLevelId)
        throws SQLException, DataNotFoundException {
        InventoryLevelData x=null;
        String sql="SELECT INVENTORY_LEVEL_ID,BUS_ENTITY_ID,ITEM_ID,QTY_ON_HAND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PARS_MOD_DATE,PARS_MOD_BY,ORDER_QTY,INITIAL_QTY_ON_HAND FROM CLW_INVENTORY_LEVEL WHERE INVENTORY_LEVEL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pInventoryLevelId=" + pInventoryLevelId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pInventoryLevelId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=InventoryLevelData.createValue();
            
            x.setInventoryLevelId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setQtyOnHand(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setParsModDate(rs.getDate(9));
            x.setParsModBy(rs.getString(10));
            x.setOrderQty(rs.getString(11));
            x.setInitialQtyOnHand(rs.getString(12));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("INVENTORY_LEVEL_ID :" + pInventoryLevelId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a InventoryLevelDataVector object that consists
     * of InventoryLevelData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new InventoryLevelDataVector()
     * @throws            SQLException
     */
    public static InventoryLevelDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a InventoryLevelData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_INVENTORY_LEVEL.INVENTORY_LEVEL_ID,CLW_INVENTORY_LEVEL.BUS_ENTITY_ID,CLW_INVENTORY_LEVEL.ITEM_ID,CLW_INVENTORY_LEVEL.QTY_ON_HAND,CLW_INVENTORY_LEVEL.ADD_DATE,CLW_INVENTORY_LEVEL.ADD_BY,CLW_INVENTORY_LEVEL.MOD_DATE,CLW_INVENTORY_LEVEL.MOD_BY,CLW_INVENTORY_LEVEL.PARS_MOD_DATE,CLW_INVENTORY_LEVEL.PARS_MOD_BY,CLW_INVENTORY_LEVEL.ORDER_QTY,CLW_INVENTORY_LEVEL.INITIAL_QTY_ON_HAND";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated InventoryLevelData Object.
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
    *@returns a populated InventoryLevelData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         InventoryLevelData x = InventoryLevelData.createValue();
         
         x.setInventoryLevelId(rs.getInt(1+offset));
         x.setBusEntityId(rs.getInt(2+offset));
         x.setItemId(rs.getInt(3+offset));
         x.setQtyOnHand(rs.getString(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         x.setParsModDate(rs.getDate(9+offset));
         x.setParsModBy(rs.getString(10+offset));
         x.setOrderQty(rs.getString(11+offset));
         x.setInitialQtyOnHand(rs.getString(12+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the InventoryLevelData Object represents.
    */
    public int getColumnCount(){
        return 12;
    }

    /**
     * Gets a InventoryLevelDataVector object that consists
     * of InventoryLevelData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new InventoryLevelDataVector()
     * @throws            SQLException
     */
    public static InventoryLevelDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT INVENTORY_LEVEL_ID,BUS_ENTITY_ID,ITEM_ID,QTY_ON_HAND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PARS_MOD_DATE,PARS_MOD_BY,ORDER_QTY,INITIAL_QTY_ON_HAND FROM CLW_INVENTORY_LEVEL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_INVENTORY_LEVEL.INVENTORY_LEVEL_ID,CLW_INVENTORY_LEVEL.BUS_ENTITY_ID,CLW_INVENTORY_LEVEL.ITEM_ID,CLW_INVENTORY_LEVEL.QTY_ON_HAND,CLW_INVENTORY_LEVEL.ADD_DATE,CLW_INVENTORY_LEVEL.ADD_BY,CLW_INVENTORY_LEVEL.MOD_DATE,CLW_INVENTORY_LEVEL.MOD_BY,CLW_INVENTORY_LEVEL.PARS_MOD_DATE,CLW_INVENTORY_LEVEL.PARS_MOD_BY,CLW_INVENTORY_LEVEL.ORDER_QTY,CLW_INVENTORY_LEVEL.INITIAL_QTY_ON_HAND FROM CLW_INVENTORY_LEVEL");
                where = pCriteria.getSqlClause("CLW_INVENTORY_LEVEL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_INVENTORY_LEVEL.equals(otherTable)){
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
        InventoryLevelDataVector v = new InventoryLevelDataVector();
        while (rs.next()) {
            InventoryLevelData x = InventoryLevelData.createValue();
            
            x.setInventoryLevelId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setQtyOnHand(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setParsModDate(rs.getDate(9));
            x.setParsModBy(rs.getString(10));
            x.setOrderQty(rs.getString(11));
            x.setInitialQtyOnHand(rs.getString(12));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a InventoryLevelDataVector object that consists
     * of InventoryLevelData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for InventoryLevelData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new InventoryLevelDataVector()
     * @throws            SQLException
     */
    public static InventoryLevelDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        InventoryLevelDataVector v = new InventoryLevelDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT INVENTORY_LEVEL_ID,BUS_ENTITY_ID,ITEM_ID,QTY_ON_HAND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PARS_MOD_DATE,PARS_MOD_BY,ORDER_QTY,INITIAL_QTY_ON_HAND FROM CLW_INVENTORY_LEVEL WHERE INVENTORY_LEVEL_ID IN (");

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
            InventoryLevelData x=null;
            while (rs.next()) {
                // build the object
                x=InventoryLevelData.createValue();
                
                x.setInventoryLevelId(rs.getInt(1));
                x.setBusEntityId(rs.getInt(2));
                x.setItemId(rs.getInt(3));
                x.setQtyOnHand(rs.getString(4));
                x.setAddDate(rs.getTimestamp(5));
                x.setAddBy(rs.getString(6));
                x.setModDate(rs.getTimestamp(7));
                x.setModBy(rs.getString(8));
                x.setParsModDate(rs.getDate(9));
                x.setParsModBy(rs.getString(10));
                x.setOrderQty(rs.getString(11));
                x.setInitialQtyOnHand(rs.getString(12));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a InventoryLevelDataVector object of all
     * InventoryLevelData objects in the database.
     * @param pCon An open database connection.
     * @return new InventoryLevelDataVector()
     * @throws            SQLException
     */
    public static InventoryLevelDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT INVENTORY_LEVEL_ID,BUS_ENTITY_ID,ITEM_ID,QTY_ON_HAND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PARS_MOD_DATE,PARS_MOD_BY,ORDER_QTY,INITIAL_QTY_ON_HAND FROM CLW_INVENTORY_LEVEL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        InventoryLevelDataVector v = new InventoryLevelDataVector();
        InventoryLevelData x = null;
        while (rs.next()) {
            // build the object
            x = InventoryLevelData.createValue();
            
            x.setInventoryLevelId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setQtyOnHand(rs.getString(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setParsModDate(rs.getDate(9));
            x.setParsModBy(rs.getString(10));
            x.setOrderQty(rs.getString(11));
            x.setInitialQtyOnHand(rs.getString(12));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * InventoryLevelData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT INVENTORY_LEVEL_ID FROM CLW_INVENTORY_LEVEL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVENTORY_LEVEL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVENTORY_LEVEL");
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
     * Inserts a InventoryLevelData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InventoryLevelData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new InventoryLevelData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InventoryLevelData insert(Connection pCon, InventoryLevelData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_INVENTORY_LEVEL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_INVENTORY_LEVEL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setInventoryLevelId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_INVENTORY_LEVEL (INVENTORY_LEVEL_ID,BUS_ENTITY_ID,ITEM_ID,QTY_ON_HAND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PARS_MOD_DATE,PARS_MOD_BY,ORDER_QTY,INITIAL_QTY_ON_HAND) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getInventoryLevelId());
        pstmt.setInt(2,pData.getBusEntityId());
        pstmt.setInt(3,pData.getItemId());
        pstmt.setString(4,pData.getQtyOnHand());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());
        pstmt.setDate(9,DBAccess.toSQLDate(pData.getParsModDate()));
        pstmt.setString(10,pData.getParsModBy());
        pstmt.setString(11,pData.getOrderQty());
        pstmt.setString(12,pData.getInitialQtyOnHand());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   INVENTORY_LEVEL_ID="+pData.getInventoryLevelId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   QTY_ON_HAND="+pData.getQtyOnHand());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   PARS_MOD_DATE="+pData.getParsModDate());
            log.debug("SQL:   PARS_MOD_BY="+pData.getParsModBy());
            log.debug("SQL:   ORDER_QTY="+pData.getOrderQty());
            log.debug("SQL:   INITIAL_QTY_ON_HAND="+pData.getInitialQtyOnHand());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        try {
            CachecosManager cacheManager = Cachecos.getCachecosManager();
            if(cacheManager != null && cacheManager.isStarted()){
                cacheManager.remove(pData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setInventoryLevelId(0);
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
     * Updates a InventoryLevelData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InventoryLevelData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InventoryLevelData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_INVENTORY_LEVEL SET BUS_ENTITY_ID = ?,ITEM_ID = ?,QTY_ON_HAND = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,PARS_MOD_DATE = ?,PARS_MOD_BY = ?,ORDER_QTY = ?,INITIAL_QTY_ON_HAND = ? WHERE INVENTORY_LEVEL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setInt(i++,pData.getItemId());
        pstmt.setString(i++,pData.getQtyOnHand());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getParsModDate()));
        pstmt.setString(i++,pData.getParsModBy());
        pstmt.setString(i++,pData.getOrderQty());
        pstmt.setString(i++,pData.getInitialQtyOnHand());
        pstmt.setInt(i++,pData.getInventoryLevelId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   QTY_ON_HAND="+pData.getQtyOnHand());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   PARS_MOD_DATE="+pData.getParsModDate());
            log.debug("SQL:   PARS_MOD_BY="+pData.getParsModBy());
            log.debug("SQL:   ORDER_QTY="+pData.getOrderQty());
            log.debug("SQL:   INITIAL_QTY_ON_HAND="+pData.getInitialQtyOnHand());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        try {
            CachecosManager cacheManager = Cachecos.getCachecosManager();
            if(cacheManager != null && cacheManager.isStarted()){
                cacheManager.remove(pData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a InventoryLevelData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInventoryLevelId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInventoryLevelId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_INVENTORY_LEVEL WHERE INVENTORY_LEVEL_ID = " + pInventoryLevelId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes InventoryLevelData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_INVENTORY_LEVEL");
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
     * Inserts a InventoryLevelData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InventoryLevelData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, InventoryLevelData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_INVENTORY_LEVEL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "INVENTORY_LEVEL_ID,BUS_ENTITY_ID,ITEM_ID,QTY_ON_HAND,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,PARS_MOD_DATE,PARS_MOD_BY,ORDER_QTY,INITIAL_QTY_ON_HAND) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getInventoryLevelId());
        pstmt.setInt(2+4,pData.getBusEntityId());
        pstmt.setInt(3+4,pData.getItemId());
        pstmt.setString(4+4,pData.getQtyOnHand());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());
        pstmt.setDate(9+4,DBAccess.toSQLDate(pData.getParsModDate()));
        pstmt.setString(10+4,pData.getParsModBy());
        pstmt.setString(11+4,pData.getOrderQty());
        pstmt.setString(12+4,pData.getInitialQtyOnHand());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a InventoryLevelData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InventoryLevelData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new InventoryLevelData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InventoryLevelData insert(Connection pCon, InventoryLevelData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a InventoryLevelData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InventoryLevelData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InventoryLevelData pData, boolean pLogFl)
        throws SQLException {
        InventoryLevelData oldData = null;
        if(pLogFl) {
          int id = pData.getInventoryLevelId();
          try {
          oldData = InventoryLevelDataAccess.select(pCon,id);
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
     * Deletes a InventoryLevelData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInventoryLevelId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInventoryLevelId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_INVENTORY_LEVEL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INVENTORY_LEVEL d WHERE INVENTORY_LEVEL_ID = " + pInventoryLevelId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pInventoryLevelId);
        return n;
     }

    /**
     * Deletes InventoryLevelData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_INVENTORY_LEVEL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INVENTORY_LEVEL d ");
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

