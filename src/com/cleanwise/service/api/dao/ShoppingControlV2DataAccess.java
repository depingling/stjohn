
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ShoppingControlV2DataAccess
 * Description:  This class is used to build access methods to the CLW_SHOPPING_CONTROL_V2 table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ShoppingControlV2Data;
import com.cleanwise.service.api.value.ShoppingControlV2DataVector;
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
 * <code>ShoppingControlV2DataAccess</code>
 */
public class ShoppingControlV2DataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ShoppingControlV2DataAccess.class.getName());

    /** <code>CLW_SHOPPING_CONTROL_V2</code> table name */
	/* Primary key: SHOPPING_CONTROL_ID */
	
    public static final String CLW_SHOPPING_CONTROL_V2 = "CLW_SHOPPING_CONTROL_V2";
    
    /** <code>SHOPPING_CONTROL_ID</code> SHOPPING_CONTROL_ID column of table CLW_SHOPPING_CONTROL_V2 */
    public static final String SHOPPING_CONTROL_ID = "SHOPPING_CONTROL_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_SHOPPING_CONTROL_V2 */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_SHOPPING_CONTROL_V2 */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>MAX_ORDER_QTY</code> MAX_ORDER_QTY column of table CLW_SHOPPING_CONTROL_V2 */
    public static final String MAX_ORDER_QTY = "MAX_ORDER_QTY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_SHOPPING_CONTROL_V2 */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_SHOPPING_CONTROL_V2 */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_SHOPPING_CONTROL_V2 */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_SHOPPING_CONTROL_V2 */
    public static final String MOD_BY = "MOD_BY";
    /** <code>CONTROL_STATUS_CD</code> CONTROL_STATUS_CD column of table CLW_SHOPPING_CONTROL_V2 */
    public static final String CONTROL_STATUS_CD = "CONTROL_STATUS_CD";
    /** <code>RESTRICTION_DAYS</code> RESTRICTION_DAYS column of table CLW_SHOPPING_CONTROL_V2 */
    public static final String RESTRICTION_DAYS = "RESTRICTION_DAYS";

    /**
     * Constructor.
     */
    public ShoppingControlV2DataAccess()
    {
    }

    /**
     * Gets a ShoppingControlV2Data object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pShoppingControlId The key requested.
     * @return new ShoppingControlV2Data()
     * @throws            SQLException
     */
    public static ShoppingControlV2Data select(Connection pCon, int pShoppingControlV2Id)
        throws SQLException, DataNotFoundException {
        ShoppingControlV2Data x=null;
        String sql="SELECT SHOPPING_CONTROL_ID,BUS_ENTITY_ID,ITEM_ID,MAX_ORDER_QTY,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTROL_STATUS_CD,RESTRICTION_DAYS FROM CLW_SHOPPING_CONTROL_V2 WHERE SHOPPING_CONTROL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pShoppingControlV2Id=" + pShoppingControlV2Id);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pShoppingControlV2Id);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ShoppingControlV2Data.createValue();
            
            x.setShoppingControlId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setMaxOrderQty(rs.getInt(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setControlStatusCd(rs.getString(9));
            x.setRestrictionDays(rs.getInt(10));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("SHOPPING_CONTROL_ID :" + pShoppingControlV2Id);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ShoppingControlV2DataVector object that consists
     * of ShoppingControlV2Data objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ShoppingControlV2DataVector()
     * @throws            SQLException
     */
    public static ShoppingControlV2DataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ShoppingControlV2Data Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_SHOPPING_CONTROL_V2.SHOPPING_CONTROL_ID,CLW_SHOPPING_CONTROL_V2.BUS_ENTITY_ID,CLW_SHOPPING_CONTROL_V2.ITEM_ID,CLW_SHOPPING_CONTROL_V2.MAX_ORDER_QTY,CLW_SHOPPING_CONTROL_V2.ADD_DATE,CLW_SHOPPING_CONTROL_V2.ADD_BY,CLW_SHOPPING_CONTROL_V2.MOD_DATE,CLW_SHOPPING_CONTROL_V2.MOD_BY,CLW_SHOPPING_CONTROL_V2.CONTROL_STATUS_CD,CLW_SHOPPING_CONTROL_V2.RESTRICTION_DAYS";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ShoppingControlV2Data Object.
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
    *@returns a populated ShoppingControlV2Data Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ShoppingControlV2Data x = ShoppingControlV2Data.createValue();
         
         x.setShoppingControlId(rs.getInt(1+offset));
         x.setBusEntityId(rs.getInt(2+offset));
         x.setItemId(rs.getInt(3+offset));
         x.setMaxOrderQty(rs.getInt(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         x.setControlStatusCd(rs.getString(9+offset));
         x.setRestrictionDays(rs.getInt(10+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ShoppingControlV2Data Object represents.
    */
    public int getColumnCount(){
        return 10;
    }

    /**
     * Gets a ShoppingControlV2DataVector object that consists
     * of ShoppingControlV2Data objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ShoppingControlV2DataVector()
     * @throws            SQLException
     */
    public static ShoppingControlV2DataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT SHOPPING_CONTROL_ID,BUS_ENTITY_ID,ITEM_ID,MAX_ORDER_QTY,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTROL_STATUS_CD,RESTRICTION_DAYS FROM CLW_SHOPPING_CONTROL_V2");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_SHOPPING_CONTROL_V2.SHOPPING_CONTROL_ID,CLW_SHOPPING_CONTROL_V2.BUS_ENTITY_ID,CLW_SHOPPING_CONTROL_V2.ITEM_ID,CLW_SHOPPING_CONTROL_V2.MAX_ORDER_QTY,CLW_SHOPPING_CONTROL_V2.ADD_DATE,CLW_SHOPPING_CONTROL_V2.ADD_BY,CLW_SHOPPING_CONTROL_V2.MOD_DATE,CLW_SHOPPING_CONTROL_V2.MOD_BY,CLW_SHOPPING_CONTROL_V2.CONTROL_STATUS_CD,CLW_SHOPPING_CONTROL_V2.RESTRICTION_DAYS FROM CLW_SHOPPING_CONTROL_V2");
                where = pCriteria.getSqlClause("CLW_SHOPPING_CONTROL_V2");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_SHOPPING_CONTROL_V2.equals(otherTable)){
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
        ShoppingControlV2DataVector v = new ShoppingControlV2DataVector();
        while (rs.next()) {
            ShoppingControlV2Data x = ShoppingControlV2Data.createValue();
            
            x.setShoppingControlId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setMaxOrderQty(rs.getInt(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setControlStatusCd(rs.getString(9));
            x.setRestrictionDays(rs.getInt(10));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ShoppingControlV2DataVector object that consists
     * of ShoppingControlV2Data objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ShoppingControlV2Data
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ShoppingControlV2DataVector()
     * @throws            SQLException
     */
    public static ShoppingControlV2DataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ShoppingControlV2DataVector v = new ShoppingControlV2DataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT SHOPPING_CONTROL_ID,BUS_ENTITY_ID,ITEM_ID,MAX_ORDER_QTY,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTROL_STATUS_CD,RESTRICTION_DAYS FROM CLW_SHOPPING_CONTROL_V2 WHERE SHOPPING_CONTROL_ID IN (");

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
            ShoppingControlV2Data x=null;
            while (rs.next()) {
                // build the object
                x=ShoppingControlV2Data.createValue();
                
                x.setShoppingControlId(rs.getInt(1));
                x.setBusEntityId(rs.getInt(2));
                x.setItemId(rs.getInt(3));
                x.setMaxOrderQty(rs.getInt(4));
                x.setAddDate(rs.getTimestamp(5));
                x.setAddBy(rs.getString(6));
                x.setModDate(rs.getTimestamp(7));
                x.setModBy(rs.getString(8));
                x.setControlStatusCd(rs.getString(9));
                x.setRestrictionDays(rs.getInt(10));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ShoppingControlV2DataVector object of all
     * ShoppingControlV2Data objects in the database.
     * @param pCon An open database connection.
     * @return new ShoppingControlV2DataVector()
     * @throws            SQLException
     */
    public static ShoppingControlV2DataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT SHOPPING_CONTROL_ID,BUS_ENTITY_ID,ITEM_ID,MAX_ORDER_QTY,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTROL_STATUS_CD,RESTRICTION_DAYS FROM CLW_SHOPPING_CONTROL_V2";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ShoppingControlV2DataVector v = new ShoppingControlV2DataVector();
        ShoppingControlV2Data x = null;
        while (rs.next()) {
            // build the object
            x = ShoppingControlV2Data.createValue();
            
            x.setShoppingControlId(rs.getInt(1));
            x.setBusEntityId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setMaxOrderQty(rs.getInt(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setControlStatusCd(rs.getString(9));
            x.setRestrictionDays(rs.getInt(10));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ShoppingControlV2Data objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT SHOPPING_CONTROL_ID FROM CLW_SHOPPING_CONTROL_V2");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT SHOPPING_CONTROL_ID FROM CLW_SHOPPING_CONTROL_V2");
                where = pCriteria.getSqlClause("CLW_SHOPPING_CONTROL_V2");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_SHOPPING_CONTROL_V2.equals(otherTable)){
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
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SHOPPING_CONTROL_V2");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SHOPPING_CONTROL_V2");
                where = pCriteria.getSqlClause("CLW_SHOPPING_CONTROL_V2");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_SHOPPING_CONTROL_V2.equals(otherTable)){
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
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SHOPPING_CONTROL_V2");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SHOPPING_CONTROL_V2");
                where = pCriteria.getSqlClause("CLW_SHOPPING_CONTROL_V2");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_SHOPPING_CONTROL_V2.equals(otherTable)){
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
            log.debug("SQL text: " + sql);
        }

        return sql;
    }

    /**
     * Inserts a ShoppingControlV2Data object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingControlV2Data object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ShoppingControlV2Data() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ShoppingControlV2Data insert(Connection pCon, ShoppingControlV2Data pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_SHOPPING_CONTROL_V2_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_SHOPPING_CONTROL_V2_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setShoppingControlId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_SHOPPING_CONTROL_V2 (SHOPPING_CONTROL_ID,BUS_ENTITY_ID,ITEM_ID,MAX_ORDER_QTY,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTROL_STATUS_CD,RESTRICTION_DAYS) VALUES(?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getShoppingControlId());
        pstmt.setInt(2,pData.getBusEntityId());
        pstmt.setInt(3,pData.getItemId());
        pstmt.setInt(4,pData.getMaxOrderQty());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());
        pstmt.setString(9,pData.getControlStatusCd());
        pstmt.setInt(10,pData.getRestrictionDays());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHOPPING_CONTROL_ID="+pData.getShoppingControlId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   MAX_ORDER_QTY="+pData.getMaxOrderQty());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   CONTROL_STATUS_CD="+pData.getControlStatusCd());
            log.debug("SQL:   RESTRICTION_DAYS="+pData.getRestrictionDays());
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
        pData.setShoppingControlId(0);
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
     * Updates a ShoppingControlV2Data object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingControlV2Data object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ShoppingControlV2Data pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_SHOPPING_CONTROL_V2 SET BUS_ENTITY_ID = ?,ITEM_ID = ?,MAX_ORDER_QTY = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,CONTROL_STATUS_CD = ?,RESTRICTION_DAYS = ? WHERE SHOPPING_CONTROL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setInt(i++,pData.getItemId());
        pstmt.setInt(i++,pData.getMaxOrderQty());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getControlStatusCd());
        pstmt.setInt(i++,pData.getRestrictionDays());
        pstmt.setInt(i++,pData.getShoppingControlId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   MAX_ORDER_QTY="+pData.getMaxOrderQty());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   CONTROL_STATUS_CD="+pData.getControlStatusCd());
            log.debug("SQL:   RESTRICTION_DAYS="+pData.getRestrictionDays());
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
     * Deletes a ShoppingControlV2Data object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pShoppingControlId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pShoppingControlV2Id)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_SHOPPING_CONTROL_V2 WHERE SHOPPING_CONTROL_ID = " + pShoppingControlV2Id;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ShoppingControlV2Data objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_SHOPPING_CONTROL_V2");
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
     * Inserts a ShoppingControlV2Data log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingControlV2Data object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ShoppingControlV2Data pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_SHOPPING_CONTROL_V2 (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "SHOPPING_CONTROL_ID,BUS_ENTITY_ID,ITEM_ID,MAX_ORDER_QTY,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTROL_STATUS_CD,RESTRICTION_DAYS) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getShoppingControlId());
        pstmt.setInt(2+4,pData.getBusEntityId());
        pstmt.setInt(3+4,pData.getItemId());
        pstmt.setInt(4+4,pData.getMaxOrderQty());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());
        pstmt.setString(9+4,pData.getControlStatusCd());
        pstmt.setInt(10+4,pData.getRestrictionDays());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ShoppingControlV2Data object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingControlV2Data object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ShoppingControlV2Data() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ShoppingControlV2Data insert(Connection pCon, ShoppingControlV2Data pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ShoppingControlV2Data object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingControlV2Data object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ShoppingControlV2Data pData, boolean pLogFl)
        throws SQLException {
        ShoppingControlV2Data oldData = null;
        if(pLogFl) {
          int id = pData.getShoppingControlId();
          try {
          oldData = ShoppingControlV2DataAccess.select(pCon,id);
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
     * Deletes a ShoppingControlV2Data object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pShoppingControlId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pShoppingControlV2Id, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_SHOPPING_CONTROL_V2 SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_SHOPPING_CONTROL_V2 d WHERE SHOPPING_CONTROL_ID = " + pShoppingControlV2Id;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pShoppingControlV2Id);
        return n;
     }

    /**
     * Deletes ShoppingControlV2Data objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_SHOPPING_CONTROL_V2 SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_SHOPPING_CONTROL_V2 d ");
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

