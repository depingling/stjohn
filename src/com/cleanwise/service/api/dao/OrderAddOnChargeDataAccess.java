
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        OrderAddOnChargeDataAccess
 * Description:  This class is used to build access methods to the CLW_ORDER_ADD_ON_CHARGE table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.OrderAddOnChargeData;
import com.cleanwise.service.api.value.OrderAddOnChargeDataVector;
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
 * <code>OrderAddOnChargeDataAccess</code>
 */
public class OrderAddOnChargeDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(OrderAddOnChargeDataAccess.class.getName());

    /** <code>CLW_ORDER_ADD_ON_CHARGE</code> table name */
	/* Primary key: ORDER_ADD_ON_CHARGE_ID */
	
    public static final String CLW_ORDER_ADD_ON_CHARGE = "CLW_ORDER_ADD_ON_CHARGE";
    
    /** <code>ORDER_ADD_ON_CHARGE_ID</code> ORDER_ADD_ON_CHARGE_ID column of table CLW_ORDER_ADD_ON_CHARGE */
    public static final String ORDER_ADD_ON_CHARGE_ID = "ORDER_ADD_ON_CHARGE_ID";
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_ORDER_ADD_ON_CHARGE */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>BUS_ENTITY_ID</code> BUS_ENTITY_ID column of table CLW_ORDER_ADD_ON_CHARGE */
    public static final String BUS_ENTITY_ID = "BUS_ENTITY_ID";
    /** <code>PURCHASE_ORDER_ID</code> PURCHASE_ORDER_ID column of table CLW_ORDER_ADD_ON_CHARGE */
    public static final String PURCHASE_ORDER_ID = "PURCHASE_ORDER_ID";
    /** <code>DIST_FEE_TYPE_CD</code> DIST_FEE_TYPE_CD column of table CLW_ORDER_ADD_ON_CHARGE */
    public static final String DIST_FEE_TYPE_CD = "DIST_FEE_TYPE_CD";
    /** <code>SHORT_DESC</code> SHORT_DESC column of table CLW_ORDER_ADD_ON_CHARGE */
    public static final String SHORT_DESC = "SHORT_DESC";
    /** <code>DIST_FEE_CHARGE_CD</code> DIST_FEE_CHARGE_CD column of table CLW_ORDER_ADD_ON_CHARGE */
    public static final String DIST_FEE_CHARGE_CD = "DIST_FEE_CHARGE_CD";
    /** <code>AMOUNT</code> AMOUNT column of table CLW_ORDER_ADD_ON_CHARGE */
    public static final String AMOUNT = "AMOUNT";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_ORDER_ADD_ON_CHARGE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_ORDER_ADD_ON_CHARGE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_ORDER_ADD_ON_CHARGE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_ORDER_ADD_ON_CHARGE */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public OrderAddOnChargeDataAccess()
    {
    }

    /**
     * Gets a OrderAddOnChargeData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pOrderAddOnChargeId The key requested.
     * @return new OrderAddOnChargeData()
     * @throws            SQLException
     */
    public static OrderAddOnChargeData select(Connection pCon, int pOrderAddOnChargeId)
        throws SQLException, DataNotFoundException {
        OrderAddOnChargeData x=null;
        String sql="SELECT ORDER_ADD_ON_CHARGE_ID,ORDER_ID,BUS_ENTITY_ID,PURCHASE_ORDER_ID,DIST_FEE_TYPE_CD,SHORT_DESC,DIST_FEE_CHARGE_CD,AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_ADD_ON_CHARGE WHERE ORDER_ADD_ON_CHARGE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pOrderAddOnChargeId=" + pOrderAddOnChargeId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pOrderAddOnChargeId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=OrderAddOnChargeData.createValue();
            
            x.setOrderAddOnChargeId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setPurchaseOrderId(rs.getInt(4));
            x.setDistFeeTypeCd(rs.getString(5));
            x.setShortDesc(rs.getString(6));
            x.setDistFeeChargeCd(rs.getString(7));
            x.setAmount(rs.getBigDecimal(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("ORDER_ADD_ON_CHARGE_ID :" + pOrderAddOnChargeId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a OrderAddOnChargeDataVector object that consists
     * of OrderAddOnChargeData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new OrderAddOnChargeDataVector()
     * @throws            SQLException
     */
    public static OrderAddOnChargeDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a OrderAddOnChargeData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_ORDER_ADD_ON_CHARGE.ORDER_ADD_ON_CHARGE_ID,CLW_ORDER_ADD_ON_CHARGE.ORDER_ID,CLW_ORDER_ADD_ON_CHARGE.BUS_ENTITY_ID,CLW_ORDER_ADD_ON_CHARGE.PURCHASE_ORDER_ID,CLW_ORDER_ADD_ON_CHARGE.DIST_FEE_TYPE_CD,CLW_ORDER_ADD_ON_CHARGE.SHORT_DESC,CLW_ORDER_ADD_ON_CHARGE.DIST_FEE_CHARGE_CD,CLW_ORDER_ADD_ON_CHARGE.AMOUNT,CLW_ORDER_ADD_ON_CHARGE.ADD_DATE,CLW_ORDER_ADD_ON_CHARGE.ADD_BY,CLW_ORDER_ADD_ON_CHARGE.MOD_DATE,CLW_ORDER_ADD_ON_CHARGE.MOD_BY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated OrderAddOnChargeData Object.
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
    *@returns a populated OrderAddOnChargeData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         OrderAddOnChargeData x = OrderAddOnChargeData.createValue();
         
         x.setOrderAddOnChargeId(rs.getInt(1+offset));
         x.setOrderId(rs.getInt(2+offset));
         x.setBusEntityId(rs.getInt(3+offset));
         x.setPurchaseOrderId(rs.getInt(4+offset));
         x.setDistFeeTypeCd(rs.getString(5+offset));
         x.setShortDesc(rs.getString(6+offset));
         x.setDistFeeChargeCd(rs.getString(7+offset));
         x.setAmount(rs.getBigDecimal(8+offset));
         x.setAddDate(rs.getTimestamp(9+offset));
         x.setAddBy(rs.getString(10+offset));
         x.setModDate(rs.getTimestamp(11+offset));
         x.setModBy(rs.getString(12+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the OrderAddOnChargeData Object represents.
    */
    public int getColumnCount(){
        return 12;
    }

    /**
     * Gets a OrderAddOnChargeDataVector object that consists
     * of OrderAddOnChargeData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new OrderAddOnChargeDataVector()
     * @throws            SQLException
     */
    public static OrderAddOnChargeDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT ORDER_ADD_ON_CHARGE_ID,ORDER_ID,BUS_ENTITY_ID,PURCHASE_ORDER_ID,DIST_FEE_TYPE_CD,SHORT_DESC,DIST_FEE_CHARGE_CD,AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_ADD_ON_CHARGE");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_ORDER_ADD_ON_CHARGE.ORDER_ADD_ON_CHARGE_ID,CLW_ORDER_ADD_ON_CHARGE.ORDER_ID,CLW_ORDER_ADD_ON_CHARGE.BUS_ENTITY_ID,CLW_ORDER_ADD_ON_CHARGE.PURCHASE_ORDER_ID,CLW_ORDER_ADD_ON_CHARGE.DIST_FEE_TYPE_CD,CLW_ORDER_ADD_ON_CHARGE.SHORT_DESC,CLW_ORDER_ADD_ON_CHARGE.DIST_FEE_CHARGE_CD,CLW_ORDER_ADD_ON_CHARGE.AMOUNT,CLW_ORDER_ADD_ON_CHARGE.ADD_DATE,CLW_ORDER_ADD_ON_CHARGE.ADD_BY,CLW_ORDER_ADD_ON_CHARGE.MOD_DATE,CLW_ORDER_ADD_ON_CHARGE.MOD_BY FROM CLW_ORDER_ADD_ON_CHARGE");
                where = pCriteria.getSqlClause("CLW_ORDER_ADD_ON_CHARGE");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_ORDER_ADD_ON_CHARGE.equals(otherTable)){
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
        OrderAddOnChargeDataVector v = new OrderAddOnChargeDataVector();
        while (rs.next()) {
            OrderAddOnChargeData x = OrderAddOnChargeData.createValue();
            
            x.setOrderAddOnChargeId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setPurchaseOrderId(rs.getInt(4));
            x.setDistFeeTypeCd(rs.getString(5));
            x.setShortDesc(rs.getString(6));
            x.setDistFeeChargeCd(rs.getString(7));
            x.setAmount(rs.getBigDecimal(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a OrderAddOnChargeDataVector object that consists
     * of OrderAddOnChargeData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for OrderAddOnChargeData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new OrderAddOnChargeDataVector()
     * @throws            SQLException
     */
    public static OrderAddOnChargeDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        OrderAddOnChargeDataVector v = new OrderAddOnChargeDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_ADD_ON_CHARGE_ID,ORDER_ID,BUS_ENTITY_ID,PURCHASE_ORDER_ID,DIST_FEE_TYPE_CD,SHORT_DESC,DIST_FEE_CHARGE_CD,AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_ADD_ON_CHARGE WHERE ORDER_ADD_ON_CHARGE_ID IN (");

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
            OrderAddOnChargeData x=null;
            while (rs.next()) {
                // build the object
                x=OrderAddOnChargeData.createValue();
                
                x.setOrderAddOnChargeId(rs.getInt(1));
                x.setOrderId(rs.getInt(2));
                x.setBusEntityId(rs.getInt(3));
                x.setPurchaseOrderId(rs.getInt(4));
                x.setDistFeeTypeCd(rs.getString(5));
                x.setShortDesc(rs.getString(6));
                x.setDistFeeChargeCd(rs.getString(7));
                x.setAmount(rs.getBigDecimal(8));
                x.setAddDate(rs.getTimestamp(9));
                x.setAddBy(rs.getString(10));
                x.setModDate(rs.getTimestamp(11));
                x.setModBy(rs.getString(12));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a OrderAddOnChargeDataVector object of all
     * OrderAddOnChargeData objects in the database.
     * @param pCon An open database connection.
     * @return new OrderAddOnChargeDataVector()
     * @throws            SQLException
     */
    public static OrderAddOnChargeDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT ORDER_ADD_ON_CHARGE_ID,ORDER_ID,BUS_ENTITY_ID,PURCHASE_ORDER_ID,DIST_FEE_TYPE_CD,SHORT_DESC,DIST_FEE_CHARGE_CD,AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_ORDER_ADD_ON_CHARGE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        OrderAddOnChargeDataVector v = new OrderAddOnChargeDataVector();
        OrderAddOnChargeData x = null;
        while (rs.next()) {
            // build the object
            x = OrderAddOnChargeData.createValue();
            
            x.setOrderAddOnChargeId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setBusEntityId(rs.getInt(3));
            x.setPurchaseOrderId(rs.getInt(4));
            x.setDistFeeTypeCd(rs.getString(5));
            x.setShortDesc(rs.getString(6));
            x.setDistFeeChargeCd(rs.getString(7));
            x.setAmount(rs.getBigDecimal(8));
            x.setAddDate(rs.getTimestamp(9));
            x.setAddBy(rs.getString(10));
            x.setModDate(rs.getTimestamp(11));
            x.setModBy(rs.getString(12));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * OrderAddOnChargeData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT ORDER_ADD_ON_CHARGE_ID FROM CLW_ORDER_ADD_ON_CHARGE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_ADD_ON_CHARGE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_ORDER_ADD_ON_CHARGE");
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
     * Inserts a OrderAddOnChargeData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderAddOnChargeData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new OrderAddOnChargeData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderAddOnChargeData insert(Connection pCon, OrderAddOnChargeData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_ORDER_ADD_ON_CHARGE_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_ORDER_ADD_ON_CHARGE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setOrderAddOnChargeId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_ORDER_ADD_ON_CHARGE (ORDER_ADD_ON_CHARGE_ID,ORDER_ID,BUS_ENTITY_ID,PURCHASE_ORDER_ID,DIST_FEE_TYPE_CD,SHORT_DESC,DIST_FEE_CHARGE_CD,AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getOrderAddOnChargeId());
        pstmt.setInt(2,pData.getOrderId());
        pstmt.setInt(3,pData.getBusEntityId());
        pstmt.setInt(4,pData.getPurchaseOrderId());
        pstmt.setString(5,pData.getDistFeeTypeCd());
        pstmt.setString(6,pData.getShortDesc());
        pstmt.setString(7,pData.getDistFeeChargeCd());
        pstmt.setBigDecimal(8,pData.getAmount());
        pstmt.setTimestamp(9,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10,pData.getAddBy());
        pstmt.setTimestamp(11,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_ADD_ON_CHARGE_ID="+pData.getOrderAddOnChargeId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   PURCHASE_ORDER_ID="+pData.getPurchaseOrderId());
            log.debug("SQL:   DIST_FEE_TYPE_CD="+pData.getDistFeeTypeCd());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   DIST_FEE_CHARGE_CD="+pData.getDistFeeChargeCd());
            log.debug("SQL:   AMOUNT="+pData.getAmount());
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
        pData.setOrderAddOnChargeId(0);
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
     * Updates a OrderAddOnChargeData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderAddOnChargeData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderAddOnChargeData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_ORDER_ADD_ON_CHARGE SET ORDER_ID = ?,BUS_ENTITY_ID = ?,PURCHASE_ORDER_ID = ?,DIST_FEE_TYPE_CD = ?,SHORT_DESC = ?,DIST_FEE_CHARGE_CD = ?,AMOUNT = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE ORDER_ADD_ON_CHARGE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getOrderId());
        pstmt.setInt(i++,pData.getBusEntityId());
        pstmt.setInt(i++,pData.getPurchaseOrderId());
        pstmt.setString(i++,pData.getDistFeeTypeCd());
        pstmt.setString(i++,pData.getShortDesc());
        pstmt.setString(i++,pData.getDistFeeChargeCd());
        pstmt.setBigDecimal(i++,pData.getAmount());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getOrderAddOnChargeId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   BUS_ENTITY_ID="+pData.getBusEntityId());
            log.debug("SQL:   PURCHASE_ORDER_ID="+pData.getPurchaseOrderId());
            log.debug("SQL:   DIST_FEE_TYPE_CD="+pData.getDistFeeTypeCd());
            log.debug("SQL:   SHORT_DESC="+pData.getShortDesc());
            log.debug("SQL:   DIST_FEE_CHARGE_CD="+pData.getDistFeeChargeCd());
            log.debug("SQL:   AMOUNT="+pData.getAmount());
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
     * Deletes a OrderAddOnChargeData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderAddOnChargeId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderAddOnChargeId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_ORDER_ADD_ON_CHARGE WHERE ORDER_ADD_ON_CHARGE_ID = " + pOrderAddOnChargeId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes OrderAddOnChargeData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_ORDER_ADD_ON_CHARGE");
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
     * Inserts a OrderAddOnChargeData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderAddOnChargeData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, OrderAddOnChargeData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_ORDER_ADD_ON_CHARGE (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "ORDER_ADD_ON_CHARGE_ID,ORDER_ID,BUS_ENTITY_ID,PURCHASE_ORDER_ID,DIST_FEE_TYPE_CD,SHORT_DESC,DIST_FEE_CHARGE_CD,AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getOrderAddOnChargeId());
        pstmt.setInt(2+4,pData.getOrderId());
        pstmt.setInt(3+4,pData.getBusEntityId());
        pstmt.setInt(4+4,pData.getPurchaseOrderId());
        pstmt.setString(5+4,pData.getDistFeeTypeCd());
        pstmt.setString(6+4,pData.getShortDesc());
        pstmt.setString(7+4,pData.getDistFeeChargeCd());
        pstmt.setBigDecimal(8+4,pData.getAmount());
        pstmt.setTimestamp(9+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(10+4,pData.getAddBy());
        pstmt.setTimestamp(11+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(12+4,pData.getModBy());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a OrderAddOnChargeData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderAddOnChargeData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new OrderAddOnChargeData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static OrderAddOnChargeData insert(Connection pCon, OrderAddOnChargeData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a OrderAddOnChargeData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A OrderAddOnChargeData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, OrderAddOnChargeData pData, boolean pLogFl)
        throws SQLException {
        OrderAddOnChargeData oldData = null;
        if(pLogFl) {
          int id = pData.getOrderAddOnChargeId();
          try {
          oldData = OrderAddOnChargeDataAccess.select(pCon,id);
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
     * Deletes a OrderAddOnChargeData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pOrderAddOnChargeId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pOrderAddOnChargeId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_ORDER_ADD_ON_CHARGE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_ADD_ON_CHARGE d WHERE ORDER_ADD_ON_CHARGE_ID = " + pOrderAddOnChargeId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pOrderAddOnChargeId);
        return n;
     }

    /**
     * Deletes OrderAddOnChargeData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_ORDER_ADD_ON_CHARGE SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_ORDER_ADD_ON_CHARGE d ");
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

