
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        ShoppingControlDataAccess
 * Description:  This class is used to build access methods to the CLW_SHOPPING_CONTROL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.ShoppingControlData;
import com.cleanwise.service.api.value.ShoppingControlDataVector;
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
 * <code>ShoppingControlDataAccess</code>
 */
public class ShoppingControlDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(ShoppingControlDataAccess.class.getName());

    /** <code>CLW_SHOPPING_CONTROL</code> table name */
	/* Primary key: SHOPPING_CONTROL_ID */
	
    public static final String CLW_SHOPPING_CONTROL = "CLW_SHOPPING_CONTROL";
    
    /** <code>SHOPPING_CONTROL_ID</code> SHOPPING_CONTROL_ID column of table CLW_SHOPPING_CONTROL */
    public static final String SHOPPING_CONTROL_ID = "SHOPPING_CONTROL_ID";
    /** <code>SITE_ID</code> SITE_ID column of table CLW_SHOPPING_CONTROL */
    public static final String SITE_ID = "SITE_ID";
    /** <code>ITEM_ID</code> ITEM_ID column of table CLW_SHOPPING_CONTROL */
    public static final String ITEM_ID = "ITEM_ID";
    /** <code>MAX_ORDER_QTY</code> MAX_ORDER_QTY column of table CLW_SHOPPING_CONTROL */
    public static final String MAX_ORDER_QTY = "MAX_ORDER_QTY";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_SHOPPING_CONTROL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_SHOPPING_CONTROL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_SHOPPING_CONTROL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_SHOPPING_CONTROL */
    public static final String MOD_BY = "MOD_BY";
    /** <code>CONTROL_STATUS_CD</code> CONTROL_STATUS_CD column of table CLW_SHOPPING_CONTROL */
    public static final String CONTROL_STATUS_CD = "CONTROL_STATUS_CD";
    /** <code>ACCOUNT_ID</code> ACCOUNT_ID column of table CLW_SHOPPING_CONTROL */
    public static final String ACCOUNT_ID = "ACCOUNT_ID";
    /** <code>RESTRICTION_DAYS</code> RESTRICTION_DAYS column of table CLW_SHOPPING_CONTROL */
    public static final String RESTRICTION_DAYS = "RESTRICTION_DAYS";
    /** <code>HISTORY_ORDER_QTY</code> HISTORY_ORDER_QTY column of table CLW_SHOPPING_CONTROL */
    public static final String HISTORY_ORDER_QTY = "HISTORY_ORDER_QTY";
    /** <code>EXP_DATE</code> EXP_DATE column of table CLW_SHOPPING_CONTROL */
    public static final String EXP_DATE = "EXP_DATE";
    /** <code>ACTUAL_MAX_QTY</code> ACTUAL_MAX_QTY column of table CLW_SHOPPING_CONTROL */
    public static final String ACTUAL_MAX_QTY = "ACTUAL_MAX_QTY";
    /** <code>ACTION_CD</code> ACTION_CD column of table CLW_SHOPPING_CONTROL */
    public static final String ACTION_CD = "ACTION_CD";

    /**
     * Constructor.
     */
    public ShoppingControlDataAccess()
    {
    }

    /**
     * Gets a ShoppingControlData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pShoppingControlId The key requested.
     * @return new ShoppingControlData()
     * @throws            SQLException
     */
    public static ShoppingControlData select(Connection pCon, int pShoppingControlId)
        throws SQLException, DataNotFoundException {
        ShoppingControlData x=null;
        String sql="SELECT SHOPPING_CONTROL_ID,SITE_ID,ITEM_ID,MAX_ORDER_QTY,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTROL_STATUS_CD,ACCOUNT_ID,RESTRICTION_DAYS,HISTORY_ORDER_QTY,EXP_DATE,ACTUAL_MAX_QTY,ACTION_CD FROM CLW_SHOPPING_CONTROL WHERE SHOPPING_CONTROL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pShoppingControlId=" + pShoppingControlId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pShoppingControlId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=ShoppingControlData.createValue();
            
            x.setShoppingControlId(rs.getInt(1));
            x.setSiteId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setMaxOrderQty(rs.getInt(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setControlStatusCd(rs.getString(9));
            x.setAccountId(rs.getInt(10));
            x.setRestrictionDays(rs.getInt(11));
            x.setHistoryOrderQty(rs.getInt(12));
            x.setExpDate(rs.getDate(13));
            x.setActualMaxQty(rs.getInt(14));
            x.setActionCd(rs.getString(15));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("SHOPPING_CONTROL_ID :" + pShoppingControlId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a ShoppingControlDataVector object that consists
     * of ShoppingControlData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new ShoppingControlDataVector()
     * @throws            SQLException
     */
    public static ShoppingControlDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a ShoppingControlData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_SHOPPING_CONTROL.SHOPPING_CONTROL_ID,CLW_SHOPPING_CONTROL.SITE_ID,CLW_SHOPPING_CONTROL.ITEM_ID,CLW_SHOPPING_CONTROL.MAX_ORDER_QTY,CLW_SHOPPING_CONTROL.ADD_DATE,CLW_SHOPPING_CONTROL.ADD_BY,CLW_SHOPPING_CONTROL.MOD_DATE,CLW_SHOPPING_CONTROL.MOD_BY,CLW_SHOPPING_CONTROL.CONTROL_STATUS_CD,CLW_SHOPPING_CONTROL.ACCOUNT_ID,CLW_SHOPPING_CONTROL.RESTRICTION_DAYS,CLW_SHOPPING_CONTROL.HISTORY_ORDER_QTY,CLW_SHOPPING_CONTROL.EXP_DATE,CLW_SHOPPING_CONTROL.ACTUAL_MAX_QTY,CLW_SHOPPING_CONTROL.ACTION_CD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated ShoppingControlData Object.
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
    *@returns a populated ShoppingControlData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         ShoppingControlData x = ShoppingControlData.createValue();
         
         x.setShoppingControlId(rs.getInt(1+offset));
         x.setSiteId(rs.getInt(2+offset));
         x.setItemId(rs.getInt(3+offset));
         x.setMaxOrderQty(rs.getInt(4+offset));
         x.setAddDate(rs.getTimestamp(5+offset));
         x.setAddBy(rs.getString(6+offset));
         x.setModDate(rs.getTimestamp(7+offset));
         x.setModBy(rs.getString(8+offset));
         x.setControlStatusCd(rs.getString(9+offset));
         x.setAccountId(rs.getInt(10+offset));
         x.setRestrictionDays(rs.getInt(11+offset));
         x.setHistoryOrderQty(rs.getInt(12+offset));
         x.setExpDate(rs.getDate(13+offset));
         x.setActualMaxQty(rs.getInt(14+offset));
         x.setActionCd(rs.getString(15+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the ShoppingControlData Object represents.
    */
    public int getColumnCount(){
        return 15;
    }

    /**
     * Gets a ShoppingControlDataVector object that consists
     * of ShoppingControlData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new ShoppingControlDataVector()
     * @throws            SQLException
     */
    public static ShoppingControlDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT SHOPPING_CONTROL_ID,SITE_ID,ITEM_ID,MAX_ORDER_QTY,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTROL_STATUS_CD,ACCOUNT_ID,RESTRICTION_DAYS,HISTORY_ORDER_QTY,EXP_DATE,ACTUAL_MAX_QTY,ACTION_CD FROM CLW_SHOPPING_CONTROL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_SHOPPING_CONTROL.SHOPPING_CONTROL_ID,CLW_SHOPPING_CONTROL.SITE_ID,CLW_SHOPPING_CONTROL.ITEM_ID,CLW_SHOPPING_CONTROL.MAX_ORDER_QTY,CLW_SHOPPING_CONTROL.ADD_DATE,CLW_SHOPPING_CONTROL.ADD_BY,CLW_SHOPPING_CONTROL.MOD_DATE,CLW_SHOPPING_CONTROL.MOD_BY,CLW_SHOPPING_CONTROL.CONTROL_STATUS_CD,CLW_SHOPPING_CONTROL.ACCOUNT_ID,CLW_SHOPPING_CONTROL.RESTRICTION_DAYS,CLW_SHOPPING_CONTROL.HISTORY_ORDER_QTY,CLW_SHOPPING_CONTROL.EXP_DATE,CLW_SHOPPING_CONTROL.ACTUAL_MAX_QTY,CLW_SHOPPING_CONTROL.ACTION_CD FROM CLW_SHOPPING_CONTROL");
                where = pCriteria.getSqlClause("CLW_SHOPPING_CONTROL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_SHOPPING_CONTROL.equals(otherTable)){
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
        ShoppingControlDataVector v = new ShoppingControlDataVector();
        while (rs.next()) {
            ShoppingControlData x = ShoppingControlData.createValue();
            
            x.setShoppingControlId(rs.getInt(1));
            x.setSiteId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setMaxOrderQty(rs.getInt(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setControlStatusCd(rs.getString(9));
            x.setAccountId(rs.getInt(10));
            x.setRestrictionDays(rs.getInt(11));
            x.setHistoryOrderQty(rs.getInt(12));
            x.setExpDate(rs.getDate(13));
            x.setActualMaxQty(rs.getInt(14));
            x.setActionCd(rs.getString(15));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a ShoppingControlDataVector object that consists
     * of ShoppingControlData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for ShoppingControlData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new ShoppingControlDataVector()
     * @throws            SQLException
     */
    public static ShoppingControlDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        ShoppingControlDataVector v = new ShoppingControlDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT SHOPPING_CONTROL_ID,SITE_ID,ITEM_ID,MAX_ORDER_QTY,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTROL_STATUS_CD,ACCOUNT_ID,RESTRICTION_DAYS,HISTORY_ORDER_QTY,EXP_DATE,ACTUAL_MAX_QTY,ACTION_CD FROM CLW_SHOPPING_CONTROL WHERE SHOPPING_CONTROL_ID IN (");

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
            ShoppingControlData x=null;
            while (rs.next()) {
                // build the object
                x=ShoppingControlData.createValue();
                
                x.setShoppingControlId(rs.getInt(1));
                x.setSiteId(rs.getInt(2));
                x.setItemId(rs.getInt(3));
                x.setMaxOrderQty(rs.getInt(4));
                x.setAddDate(rs.getTimestamp(5));
                x.setAddBy(rs.getString(6));
                x.setModDate(rs.getTimestamp(7));
                x.setModBy(rs.getString(8));
                x.setControlStatusCd(rs.getString(9));
                x.setAccountId(rs.getInt(10));
                x.setRestrictionDays(rs.getInt(11));
                x.setHistoryOrderQty(rs.getInt(12));
                x.setExpDate(rs.getDate(13));
                x.setActualMaxQty(rs.getInt(14));
                x.setActionCd(rs.getString(15));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a ShoppingControlDataVector object of all
     * ShoppingControlData objects in the database.
     * @param pCon An open database connection.
     * @return new ShoppingControlDataVector()
     * @throws            SQLException
     */
    public static ShoppingControlDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT SHOPPING_CONTROL_ID,SITE_ID,ITEM_ID,MAX_ORDER_QTY,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTROL_STATUS_CD,ACCOUNT_ID,RESTRICTION_DAYS,HISTORY_ORDER_QTY,EXP_DATE,ACTUAL_MAX_QTY,ACTION_CD FROM CLW_SHOPPING_CONTROL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        ShoppingControlDataVector v = new ShoppingControlDataVector();
        ShoppingControlData x = null;
        while (rs.next()) {
            // build the object
            x = ShoppingControlData.createValue();
            
            x.setShoppingControlId(rs.getInt(1));
            x.setSiteId(rs.getInt(2));
            x.setItemId(rs.getInt(3));
            x.setMaxOrderQty(rs.getInt(4));
            x.setAddDate(rs.getTimestamp(5));
            x.setAddBy(rs.getString(6));
            x.setModDate(rs.getTimestamp(7));
            x.setModBy(rs.getString(8));
            x.setControlStatusCd(rs.getString(9));
            x.setAccountId(rs.getInt(10));
            x.setRestrictionDays(rs.getInt(11));
            x.setHistoryOrderQty(rs.getInt(12));
            x.setExpDate(rs.getDate(13));
            x.setActualMaxQty(rs.getInt(14));
            x.setActionCd(rs.getString(15));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * ShoppingControlData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT SHOPPING_CONTROL_ID FROM CLW_SHOPPING_CONTROL");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT SHOPPING_CONTROL_ID FROM CLW_SHOPPING_CONTROL");
                where = pCriteria.getSqlClause("CLW_SHOPPING_CONTROL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_SHOPPING_CONTROL.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SHOPPING_CONTROL");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SHOPPING_CONTROL");
                where = pCriteria.getSqlClause("CLW_SHOPPING_CONTROL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_SHOPPING_CONTROL.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SHOPPING_CONTROL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_SHOPPING_CONTROL");
                where = pCriteria.getSqlClause("CLW_SHOPPING_CONTROL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_SHOPPING_CONTROL.equals(otherTable)){
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
     * Inserts a ShoppingControlData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingControlData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new ShoppingControlData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ShoppingControlData insert(Connection pCon, ShoppingControlData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_SHOPPING_CONTROL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_SHOPPING_CONTROL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setShoppingControlId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_SHOPPING_CONTROL (SHOPPING_CONTROL_ID,SITE_ID,ITEM_ID,MAX_ORDER_QTY,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTROL_STATUS_CD,ACCOUNT_ID,RESTRICTION_DAYS,HISTORY_ORDER_QTY,EXP_DATE,ACTUAL_MAX_QTY,ACTION_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getShoppingControlId());
        pstmt.setInt(2,pData.getSiteId());
        pstmt.setInt(3,pData.getItemId());
        pstmt.setInt(4,pData.getMaxOrderQty());
        pstmt.setTimestamp(5,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6,pData.getAddBy());
        pstmt.setTimestamp(7,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8,pData.getModBy());
        pstmt.setString(9,pData.getControlStatusCd());
        pstmt.setInt(10,pData.getAccountId());
        pstmt.setInt(11,pData.getRestrictionDays());
        pstmt.setInt(12,pData.getHistoryOrderQty());
        pstmt.setDate(13,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setInt(14,pData.getActualMaxQty());
        pstmt.setString(15,pData.getActionCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SHOPPING_CONTROL_ID="+pData.getShoppingControlId());
            log.debug("SQL:   SITE_ID="+pData.getSiteId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   MAX_ORDER_QTY="+pData.getMaxOrderQty());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   CONTROL_STATUS_CD="+pData.getControlStatusCd());
            log.debug("SQL:   ACCOUNT_ID="+pData.getAccountId());
            log.debug("SQL:   RESTRICTION_DAYS="+pData.getRestrictionDays());
            log.debug("SQL:   HISTORY_ORDER_QTY="+pData.getHistoryOrderQty());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   ACTUAL_MAX_QTY="+pData.getActualMaxQty());
            log.debug("SQL:   ACTION_CD="+pData.getActionCd());
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
     * Updates a ShoppingControlData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingControlData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ShoppingControlData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_SHOPPING_CONTROL SET SITE_ID = ?,ITEM_ID = ?,MAX_ORDER_QTY = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,CONTROL_STATUS_CD = ?,ACCOUNT_ID = ?,RESTRICTION_DAYS = ?,HISTORY_ORDER_QTY = ?,EXP_DATE = ?,ACTUAL_MAX_QTY = ?,ACTION_CD = ? WHERE SHOPPING_CONTROL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getSiteId());
        pstmt.setInt(i++,pData.getItemId());
        pstmt.setInt(i++,pData.getMaxOrderQty());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getControlStatusCd());
        pstmt.setInt(i++,pData.getAccountId());
        pstmt.setInt(i++,pData.getRestrictionDays());
        pstmt.setInt(i++,pData.getHistoryOrderQty());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setInt(i++,pData.getActualMaxQty());
        pstmt.setString(i++,pData.getActionCd());
        pstmt.setInt(i++,pData.getShoppingControlId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   SITE_ID="+pData.getSiteId());
            log.debug("SQL:   ITEM_ID="+pData.getItemId());
            log.debug("SQL:   MAX_ORDER_QTY="+pData.getMaxOrderQty());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   CONTROL_STATUS_CD="+pData.getControlStatusCd());
            log.debug("SQL:   ACCOUNT_ID="+pData.getAccountId());
            log.debug("SQL:   RESTRICTION_DAYS="+pData.getRestrictionDays());
            log.debug("SQL:   HISTORY_ORDER_QTY="+pData.getHistoryOrderQty());
            log.debug("SQL:   EXP_DATE="+pData.getExpDate());
            log.debug("SQL:   ACTUAL_MAX_QTY="+pData.getActualMaxQty());
            log.debug("SQL:   ACTION_CD="+pData.getActionCd());
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
     * Deletes a ShoppingControlData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pShoppingControlId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pShoppingControlId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_SHOPPING_CONTROL WHERE SHOPPING_CONTROL_ID = " + pShoppingControlId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes ShoppingControlData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_SHOPPING_CONTROL");
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
     * Inserts a ShoppingControlData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingControlData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, ShoppingControlData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_SHOPPING_CONTROL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "SHOPPING_CONTROL_ID,SITE_ID,ITEM_ID,MAX_ORDER_QTY,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,CONTROL_STATUS_CD,ACCOUNT_ID,RESTRICTION_DAYS,HISTORY_ORDER_QTY,EXP_DATE,ACTUAL_MAX_QTY,ACTION_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getShoppingControlId());
        pstmt.setInt(2+4,pData.getSiteId());
        pstmt.setInt(3+4,pData.getItemId());
        pstmt.setInt(4+4,pData.getMaxOrderQty());
        pstmt.setTimestamp(5+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(6+4,pData.getAddBy());
        pstmt.setTimestamp(7+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(8+4,pData.getModBy());
        pstmt.setString(9+4,pData.getControlStatusCd());
        pstmt.setInt(10+4,pData.getAccountId());
        pstmt.setInt(11+4,pData.getRestrictionDays());
        pstmt.setInt(12+4,pData.getHistoryOrderQty());
        pstmt.setDate(13+4,DBAccess.toSQLDate(pData.getExpDate()));
        pstmt.setInt(14+4,pData.getActualMaxQty());
        pstmt.setString(15+4,pData.getActionCd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a ShoppingControlData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingControlData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new ShoppingControlData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static ShoppingControlData insert(Connection pCon, ShoppingControlData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a ShoppingControlData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A ShoppingControlData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, ShoppingControlData pData, boolean pLogFl)
        throws SQLException {
        ShoppingControlData oldData = null;
        if(pLogFl) {
          int id = pData.getShoppingControlId();
          try {
          oldData = ShoppingControlDataAccess.select(pCon,id);
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
     * Deletes a ShoppingControlData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pShoppingControlId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pShoppingControlId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_SHOPPING_CONTROL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_SHOPPING_CONTROL d WHERE SHOPPING_CONTROL_ID = " + pShoppingControlId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pShoppingControlId);
        return n;
     }

    /**
     * Deletes ShoppingControlData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_SHOPPING_CONTROL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_SHOPPING_CONTROL d ");
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

