
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        BackorderDataAccess
 * Description:  This class is used to build access methods to the CLW_BACKORDER table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.BackorderData;
import com.cleanwise.service.api.value.BackorderDataVector;
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
 * <code>BackorderDataAccess</code>
 */
public class BackorderDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(BackorderDataAccess.class.getName());

    /** <code>CLW_BACKORDER</code> table name */
	/* Primary key: BACKORDER_ID */
	
    public static final String CLW_BACKORDER = "CLW_BACKORDER";
    
    /** <code>BACKORDER_ID</code> BACKORDER_ID column of table CLW_BACKORDER */
    public static final String BACKORDER_ID = "BACKORDER_ID";
    /** <code>LOCATION</code> LOCATION column of table CLW_BACKORDER */
    public static final String LOCATION = "LOCATION";
    /** <code>PO_NUM</code> PO_NUM column of table CLW_BACKORDER */
    public static final String PO_NUM = "PO_NUM";
    /** <code>ITEM_NUM</code> ITEM_NUM column of table CLW_BACKORDER */
    public static final String ITEM_NUM = "ITEM_NUM";
    /** <code>ITEM_STATUS</code> ITEM_STATUS column of table CLW_BACKORDER */
    public static final String ITEM_STATUS = "ITEM_STATUS";
    /** <code>BACKORDER_QTY</code> BACKORDER_QTY column of table CLW_BACKORDER */
    public static final String BACKORDER_QTY = "BACKORDER_QTY";
    /** <code>EST_IN_STOCK</code> EST_IN_STOCK column of table CLW_BACKORDER */
    public static final String EST_IN_STOCK = "EST_IN_STOCK";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_BACKORDER */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_BACKORDER */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_BACKORDER */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_BACKORDER */
    public static final String MOD_BY = "MOD_BY";
    /** <code>ORDER_ENTRY_DATE</code> ORDER_ENTRY_DATE column of table CLW_BACKORDER */
    public static final String ORDER_ENTRY_DATE = "ORDER_ENTRY_DATE";
    /** <code>ORDER_BRANCH_CODE</code> ORDER_BRANCH_CODE column of table CLW_BACKORDER */
    public static final String ORDER_BRANCH_CODE = "ORDER_BRANCH_CODE";
    /** <code>ORDER_NUM</code> ORDER_NUM column of table CLW_BACKORDER */
    public static final String ORDER_NUM = "ORDER_NUM";
    /** <code>ORDER_DISTRIBUTION_NUM</code> ORDER_DISTRIBUTION_NUM column of table CLW_BACKORDER */
    public static final String ORDER_DISTRIBUTION_NUM = "ORDER_DISTRIBUTION_NUM";
    /** <code>ORDER_SHIPMENT_NUM</code> ORDER_SHIPMENT_NUM column of table CLW_BACKORDER */
    public static final String ORDER_SHIPMENT_NUM = "ORDER_SHIPMENT_NUM";
    /** <code>SHIP_BRANCH_CODE</code> SHIP_BRANCH_CODE column of table CLW_BACKORDER */
    public static final String SHIP_BRANCH_CODE = "SHIP_BRANCH_CODE";
    /** <code>ITEM_DESC</code> ITEM_DESC column of table CLW_BACKORDER */
    public static final String ITEM_DESC = "ITEM_DESC";
    /** <code>ITEM_DESC2</code> ITEM_DESC2 column of table CLW_BACKORDER */
    public static final String ITEM_DESC2 = "ITEM_DESC2";
    /** <code>UOM</code> UOM column of table CLW_BACKORDER */
    public static final String UOM = "UOM";
    /** <code>CUST_INDICATOR_CODE</code> CUST_INDICATOR_CODE column of table CLW_BACKORDER */
    public static final String CUST_INDICATOR_CODE = "CUST_INDICATOR_CODE";
    /** <code>ORDER_QTY</code> ORDER_QTY column of table CLW_BACKORDER */
    public static final String ORDER_QTY = "ORDER_QTY";
    /** <code>SHIP_QTY</code> SHIP_QTY column of table CLW_BACKORDER */
    public static final String SHIP_QTY = "SHIP_QTY";

    /**
     * Constructor.
     */
    public BackorderDataAccess()
    {
    }

    /**
     * Gets a BackorderData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pBackorderId The key requested.
     * @return new BackorderData()
     * @throws            SQLException
     */
    public static BackorderData select(Connection pCon, int pBackorderId)
        throws SQLException, DataNotFoundException {
        BackorderData x=null;
        String sql="SELECT BACKORDER_ID,LOCATION,PO_NUM,ITEM_NUM,ITEM_STATUS,BACKORDER_QTY,EST_IN_STOCK,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_ENTRY_DATE,ORDER_BRANCH_CODE,ORDER_NUM,ORDER_DISTRIBUTION_NUM,ORDER_SHIPMENT_NUM,SHIP_BRANCH_CODE,ITEM_DESC,ITEM_DESC2,UOM,CUST_INDICATOR_CODE,ORDER_QTY,SHIP_QTY FROM CLW_BACKORDER WHERE BACKORDER_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pBackorderId=" + pBackorderId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pBackorderId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=BackorderData.createValue();
            
            x.setBackorderId(rs.getInt(1));
            x.setLocation(rs.getString(2));
            x.setPoNum(rs.getString(3));
            x.setItemNum(rs.getInt(4));
            x.setItemStatus(rs.getString(5));
            x.setBackorderQty(rs.getInt(6));
            x.setEstInStock(rs.getDate(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            x.setOrderEntryDate(rs.getDate(12));
            x.setOrderBranchCode(rs.getString(13));
            x.setOrderNum(rs.getString(14));
            x.setOrderDistributionNum(rs.getInt(15));
            x.setOrderShipmentNum(rs.getInt(16));
            x.setShipBranchCode(rs.getString(17));
            x.setItemDesc(rs.getString(18));
            x.setItemDesc2(rs.getString(19));
            x.setUom(rs.getString(20));
            x.setCustIndicatorCode(rs.getString(21));
            x.setOrderQty(rs.getInt(22));
            x.setShipQty(rs.getInt(23));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("BACKORDER_ID :" + pBackorderId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a BackorderDataVector object that consists
     * of BackorderData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new BackorderDataVector()
     * @throws            SQLException
     */
    public static BackorderDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a BackorderData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_BACKORDER.BACKORDER_ID,CLW_BACKORDER.LOCATION,CLW_BACKORDER.PO_NUM,CLW_BACKORDER.ITEM_NUM,CLW_BACKORDER.ITEM_STATUS,CLW_BACKORDER.BACKORDER_QTY,CLW_BACKORDER.EST_IN_STOCK,CLW_BACKORDER.ADD_DATE,CLW_BACKORDER.ADD_BY,CLW_BACKORDER.MOD_DATE,CLW_BACKORDER.MOD_BY,CLW_BACKORDER.ORDER_ENTRY_DATE,CLW_BACKORDER.ORDER_BRANCH_CODE,CLW_BACKORDER.ORDER_NUM,CLW_BACKORDER.ORDER_DISTRIBUTION_NUM,CLW_BACKORDER.ORDER_SHIPMENT_NUM,CLW_BACKORDER.SHIP_BRANCH_CODE,CLW_BACKORDER.ITEM_DESC,CLW_BACKORDER.ITEM_DESC2,CLW_BACKORDER.UOM,CLW_BACKORDER.CUST_INDICATOR_CODE,CLW_BACKORDER.ORDER_QTY,CLW_BACKORDER.SHIP_QTY";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated BackorderData Object.
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
    *@returns a populated BackorderData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         BackorderData x = BackorderData.createValue();
         
         x.setBackorderId(rs.getInt(1+offset));
         x.setLocation(rs.getString(2+offset));
         x.setPoNum(rs.getString(3+offset));
         x.setItemNum(rs.getInt(4+offset));
         x.setItemStatus(rs.getString(5+offset));
         x.setBackorderQty(rs.getInt(6+offset));
         x.setEstInStock(rs.getDate(7+offset));
         x.setAddDate(rs.getTimestamp(8+offset));
         x.setAddBy(rs.getString(9+offset));
         x.setModDate(rs.getTimestamp(10+offset));
         x.setModBy(rs.getString(11+offset));
         x.setOrderEntryDate(rs.getDate(12+offset));
         x.setOrderBranchCode(rs.getString(13+offset));
         x.setOrderNum(rs.getString(14+offset));
         x.setOrderDistributionNum(rs.getInt(15+offset));
         x.setOrderShipmentNum(rs.getInt(16+offset));
         x.setShipBranchCode(rs.getString(17+offset));
         x.setItemDesc(rs.getString(18+offset));
         x.setItemDesc2(rs.getString(19+offset));
         x.setUom(rs.getString(20+offset));
         x.setCustIndicatorCode(rs.getString(21+offset));
         x.setOrderQty(rs.getInt(22+offset));
         x.setShipQty(rs.getInt(23+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the BackorderData Object represents.
    */
    public int getColumnCount(){
        return 23;
    }

    /**
     * Gets a BackorderDataVector object that consists
     * of BackorderData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new BackorderDataVector()
     * @throws            SQLException
     */
    public static BackorderDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT BACKORDER_ID,LOCATION,PO_NUM,ITEM_NUM,ITEM_STATUS,BACKORDER_QTY,EST_IN_STOCK,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_ENTRY_DATE,ORDER_BRANCH_CODE,ORDER_NUM,ORDER_DISTRIBUTION_NUM,ORDER_SHIPMENT_NUM,SHIP_BRANCH_CODE,ITEM_DESC,ITEM_DESC2,UOM,CUST_INDICATOR_CODE,ORDER_QTY,SHIP_QTY FROM CLW_BACKORDER");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_BACKORDER.BACKORDER_ID,CLW_BACKORDER.LOCATION,CLW_BACKORDER.PO_NUM,CLW_BACKORDER.ITEM_NUM,CLW_BACKORDER.ITEM_STATUS,CLW_BACKORDER.BACKORDER_QTY,CLW_BACKORDER.EST_IN_STOCK,CLW_BACKORDER.ADD_DATE,CLW_BACKORDER.ADD_BY,CLW_BACKORDER.MOD_DATE,CLW_BACKORDER.MOD_BY,CLW_BACKORDER.ORDER_ENTRY_DATE,CLW_BACKORDER.ORDER_BRANCH_CODE,CLW_BACKORDER.ORDER_NUM,CLW_BACKORDER.ORDER_DISTRIBUTION_NUM,CLW_BACKORDER.ORDER_SHIPMENT_NUM,CLW_BACKORDER.SHIP_BRANCH_CODE,CLW_BACKORDER.ITEM_DESC,CLW_BACKORDER.ITEM_DESC2,CLW_BACKORDER.UOM,CLW_BACKORDER.CUST_INDICATOR_CODE,CLW_BACKORDER.ORDER_QTY,CLW_BACKORDER.SHIP_QTY FROM CLW_BACKORDER");
                where = pCriteria.getSqlClause("CLW_BACKORDER");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_BACKORDER.equals(otherTable)){
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
        BackorderDataVector v = new BackorderDataVector();
        while (rs.next()) {
            BackorderData x = BackorderData.createValue();
            
            x.setBackorderId(rs.getInt(1));
            x.setLocation(rs.getString(2));
            x.setPoNum(rs.getString(3));
            x.setItemNum(rs.getInt(4));
            x.setItemStatus(rs.getString(5));
            x.setBackorderQty(rs.getInt(6));
            x.setEstInStock(rs.getDate(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            x.setOrderEntryDate(rs.getDate(12));
            x.setOrderBranchCode(rs.getString(13));
            x.setOrderNum(rs.getString(14));
            x.setOrderDistributionNum(rs.getInt(15));
            x.setOrderShipmentNum(rs.getInt(16));
            x.setShipBranchCode(rs.getString(17));
            x.setItemDesc(rs.getString(18));
            x.setItemDesc2(rs.getString(19));
            x.setUom(rs.getString(20));
            x.setCustIndicatorCode(rs.getString(21));
            x.setOrderQty(rs.getInt(22));
            x.setShipQty(rs.getInt(23));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a BackorderDataVector object that consists
     * of BackorderData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for BackorderData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new BackorderDataVector()
     * @throws            SQLException
     */
    public static BackorderDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        BackorderDataVector v = new BackorderDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT BACKORDER_ID,LOCATION,PO_NUM,ITEM_NUM,ITEM_STATUS,BACKORDER_QTY,EST_IN_STOCK,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_ENTRY_DATE,ORDER_BRANCH_CODE,ORDER_NUM,ORDER_DISTRIBUTION_NUM,ORDER_SHIPMENT_NUM,SHIP_BRANCH_CODE,ITEM_DESC,ITEM_DESC2,UOM,CUST_INDICATOR_CODE,ORDER_QTY,SHIP_QTY FROM CLW_BACKORDER WHERE BACKORDER_ID IN (");

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
            BackorderData x=null;
            while (rs.next()) {
                // build the object
                x=BackorderData.createValue();
                
                x.setBackorderId(rs.getInt(1));
                x.setLocation(rs.getString(2));
                x.setPoNum(rs.getString(3));
                x.setItemNum(rs.getInt(4));
                x.setItemStatus(rs.getString(5));
                x.setBackorderQty(rs.getInt(6));
                x.setEstInStock(rs.getDate(7));
                x.setAddDate(rs.getTimestamp(8));
                x.setAddBy(rs.getString(9));
                x.setModDate(rs.getTimestamp(10));
                x.setModBy(rs.getString(11));
                x.setOrderEntryDate(rs.getDate(12));
                x.setOrderBranchCode(rs.getString(13));
                x.setOrderNum(rs.getString(14));
                x.setOrderDistributionNum(rs.getInt(15));
                x.setOrderShipmentNum(rs.getInt(16));
                x.setShipBranchCode(rs.getString(17));
                x.setItemDesc(rs.getString(18));
                x.setItemDesc2(rs.getString(19));
                x.setUom(rs.getString(20));
                x.setCustIndicatorCode(rs.getString(21));
                x.setOrderQty(rs.getInt(22));
                x.setShipQty(rs.getInt(23));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a BackorderDataVector object of all
     * BackorderData objects in the database.
     * @param pCon An open database connection.
     * @return new BackorderDataVector()
     * @throws            SQLException
     */
    public static BackorderDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT BACKORDER_ID,LOCATION,PO_NUM,ITEM_NUM,ITEM_STATUS,BACKORDER_QTY,EST_IN_STOCK,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_ENTRY_DATE,ORDER_BRANCH_CODE,ORDER_NUM,ORDER_DISTRIBUTION_NUM,ORDER_SHIPMENT_NUM,SHIP_BRANCH_CODE,ITEM_DESC,ITEM_DESC2,UOM,CUST_INDICATOR_CODE,ORDER_QTY,SHIP_QTY FROM CLW_BACKORDER";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        BackorderDataVector v = new BackorderDataVector();
        BackorderData x = null;
        while (rs.next()) {
            // build the object
            x = BackorderData.createValue();
            
            x.setBackorderId(rs.getInt(1));
            x.setLocation(rs.getString(2));
            x.setPoNum(rs.getString(3));
            x.setItemNum(rs.getInt(4));
            x.setItemStatus(rs.getString(5));
            x.setBackorderQty(rs.getInt(6));
            x.setEstInStock(rs.getDate(7));
            x.setAddDate(rs.getTimestamp(8));
            x.setAddBy(rs.getString(9));
            x.setModDate(rs.getTimestamp(10));
            x.setModBy(rs.getString(11));
            x.setOrderEntryDate(rs.getDate(12));
            x.setOrderBranchCode(rs.getString(13));
            x.setOrderNum(rs.getString(14));
            x.setOrderDistributionNum(rs.getInt(15));
            x.setOrderShipmentNum(rs.getInt(16));
            x.setShipBranchCode(rs.getString(17));
            x.setItemDesc(rs.getString(18));
            x.setItemDesc2(rs.getString(19));
            x.setUom(rs.getString(20));
            x.setCustIndicatorCode(rs.getString(21));
            x.setOrderQty(rs.getInt(22));
            x.setShipQty(rs.getInt(23));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * BackorderData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT BACKORDER_ID FROM CLW_BACKORDER");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_BACKORDER");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_BACKORDER");
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
     * Inserts a BackorderData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BackorderData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new BackorderData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static BackorderData insert(Connection pCon, BackorderData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_BACKORDER_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_BACKORDER_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setBackorderId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_BACKORDER (BACKORDER_ID,LOCATION,PO_NUM,ITEM_NUM,ITEM_STATUS,BACKORDER_QTY,EST_IN_STOCK,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_ENTRY_DATE,ORDER_BRANCH_CODE,ORDER_NUM,ORDER_DISTRIBUTION_NUM,ORDER_SHIPMENT_NUM,SHIP_BRANCH_CODE,ITEM_DESC,ITEM_DESC2,UOM,CUST_INDICATOR_CODE,ORDER_QTY,SHIP_QTY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getBackorderId());
        pstmt.setString(2,pData.getLocation());
        pstmt.setString(3,pData.getPoNum());
        pstmt.setInt(4,pData.getItemNum());
        pstmt.setString(5,pData.getItemStatus());
        pstmt.setInt(6,pData.getBackorderQty());
        pstmt.setDate(7,DBAccess.toSQLDate(pData.getEstInStock()));
        pstmt.setTimestamp(8,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9,pData.getAddBy());
        pstmt.setTimestamp(10,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11,pData.getModBy());
        pstmt.setDate(12,DBAccess.toSQLDate(pData.getOrderEntryDate()));
        pstmt.setString(13,pData.getOrderBranchCode());
        pstmt.setString(14,pData.getOrderNum());
        pstmt.setInt(15,pData.getOrderDistributionNum());
        pstmt.setInt(16,pData.getOrderShipmentNum());
        pstmt.setString(17,pData.getShipBranchCode());
        pstmt.setString(18,pData.getItemDesc());
        pstmt.setString(19,pData.getItemDesc2());
        pstmt.setString(20,pData.getUom());
        pstmt.setString(21,pData.getCustIndicatorCode());
        pstmt.setInt(22,pData.getOrderQty());
        pstmt.setInt(23,pData.getShipQty());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   BACKORDER_ID="+pData.getBackorderId());
            log.debug("SQL:   LOCATION="+pData.getLocation());
            log.debug("SQL:   PO_NUM="+pData.getPoNum());
            log.debug("SQL:   ITEM_NUM="+pData.getItemNum());
            log.debug("SQL:   ITEM_STATUS="+pData.getItemStatus());
            log.debug("SQL:   BACKORDER_QTY="+pData.getBackorderQty());
            log.debug("SQL:   EST_IN_STOCK="+pData.getEstInStock());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   ORDER_ENTRY_DATE="+pData.getOrderEntryDate());
            log.debug("SQL:   ORDER_BRANCH_CODE="+pData.getOrderBranchCode());
            log.debug("SQL:   ORDER_NUM="+pData.getOrderNum());
            log.debug("SQL:   ORDER_DISTRIBUTION_NUM="+pData.getOrderDistributionNum());
            log.debug("SQL:   ORDER_SHIPMENT_NUM="+pData.getOrderShipmentNum());
            log.debug("SQL:   SHIP_BRANCH_CODE="+pData.getShipBranchCode());
            log.debug("SQL:   ITEM_DESC="+pData.getItemDesc());
            log.debug("SQL:   ITEM_DESC2="+pData.getItemDesc2());
            log.debug("SQL:   UOM="+pData.getUom());
            log.debug("SQL:   CUST_INDICATOR_CODE="+pData.getCustIndicatorCode());
            log.debug("SQL:   ORDER_QTY="+pData.getOrderQty());
            log.debug("SQL:   SHIP_QTY="+pData.getShipQty());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setBackorderId(0);
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
     * Updates a BackorderData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A BackorderData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, BackorderData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_BACKORDER SET LOCATION = ?,PO_NUM = ?,ITEM_NUM = ?,ITEM_STATUS = ?,BACKORDER_QTY = ?,EST_IN_STOCK = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,ORDER_ENTRY_DATE = ?,ORDER_BRANCH_CODE = ?,ORDER_NUM = ?,ORDER_DISTRIBUTION_NUM = ?,ORDER_SHIPMENT_NUM = ?,SHIP_BRANCH_CODE = ?,ITEM_DESC = ?,ITEM_DESC2 = ?,UOM = ?,CUST_INDICATOR_CODE = ?,ORDER_QTY = ?,SHIP_QTY = ? WHERE BACKORDER_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setString(i++,pData.getLocation());
        pstmt.setString(i++,pData.getPoNum());
        pstmt.setInt(i++,pData.getItemNum());
        pstmt.setString(i++,pData.getItemStatus());
        pstmt.setInt(i++,pData.getBackorderQty());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getEstInStock()));
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getOrderEntryDate()));
        pstmt.setString(i++,pData.getOrderBranchCode());
        pstmt.setString(i++,pData.getOrderNum());
        pstmt.setInt(i++,pData.getOrderDistributionNum());
        pstmt.setInt(i++,pData.getOrderShipmentNum());
        pstmt.setString(i++,pData.getShipBranchCode());
        pstmt.setString(i++,pData.getItemDesc());
        pstmt.setString(i++,pData.getItemDesc2());
        pstmt.setString(i++,pData.getUom());
        pstmt.setString(i++,pData.getCustIndicatorCode());
        pstmt.setInt(i++,pData.getOrderQty());
        pstmt.setInt(i++,pData.getShipQty());
        pstmt.setInt(i++,pData.getBackorderId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   LOCATION="+pData.getLocation());
            log.debug("SQL:   PO_NUM="+pData.getPoNum());
            log.debug("SQL:   ITEM_NUM="+pData.getItemNum());
            log.debug("SQL:   ITEM_STATUS="+pData.getItemStatus());
            log.debug("SQL:   BACKORDER_QTY="+pData.getBackorderQty());
            log.debug("SQL:   EST_IN_STOCK="+pData.getEstInStock());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   ORDER_ENTRY_DATE="+pData.getOrderEntryDate());
            log.debug("SQL:   ORDER_BRANCH_CODE="+pData.getOrderBranchCode());
            log.debug("SQL:   ORDER_NUM="+pData.getOrderNum());
            log.debug("SQL:   ORDER_DISTRIBUTION_NUM="+pData.getOrderDistributionNum());
            log.debug("SQL:   ORDER_SHIPMENT_NUM="+pData.getOrderShipmentNum());
            log.debug("SQL:   SHIP_BRANCH_CODE="+pData.getShipBranchCode());
            log.debug("SQL:   ITEM_DESC="+pData.getItemDesc());
            log.debug("SQL:   ITEM_DESC2="+pData.getItemDesc2());
            log.debug("SQL:   UOM="+pData.getUom());
            log.debug("SQL:   CUST_INDICATOR_CODE="+pData.getCustIndicatorCode());
            log.debug("SQL:   ORDER_QTY="+pData.getOrderQty());
            log.debug("SQL:   SHIP_QTY="+pData.getShipQty());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a BackorderData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pBackorderId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pBackorderId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_BACKORDER WHERE BACKORDER_ID = " + pBackorderId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes BackorderData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_BACKORDER");
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
     * Inserts a BackorderData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BackorderData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, BackorderData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_BACKORDER (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "BACKORDER_ID,LOCATION,PO_NUM,ITEM_NUM,ITEM_STATUS,BACKORDER_QTY,EST_IN_STOCK,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ORDER_ENTRY_DATE,ORDER_BRANCH_CODE,ORDER_NUM,ORDER_DISTRIBUTION_NUM,ORDER_SHIPMENT_NUM,SHIP_BRANCH_CODE,ITEM_DESC,ITEM_DESC2,UOM,CUST_INDICATOR_CODE,ORDER_QTY,SHIP_QTY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getBackorderId());
        pstmt.setString(2+4,pData.getLocation());
        pstmt.setString(3+4,pData.getPoNum());
        pstmt.setInt(4+4,pData.getItemNum());
        pstmt.setString(5+4,pData.getItemStatus());
        pstmt.setInt(6+4,pData.getBackorderQty());
        pstmt.setDate(7+4,DBAccess.toSQLDate(pData.getEstInStock()));
        pstmt.setTimestamp(8+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(9+4,pData.getAddBy());
        pstmt.setTimestamp(10+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(11+4,pData.getModBy());
        pstmt.setDate(12+4,DBAccess.toSQLDate(pData.getOrderEntryDate()));
        pstmt.setString(13+4,pData.getOrderBranchCode());
        pstmt.setString(14+4,pData.getOrderNum());
        pstmt.setInt(15+4,pData.getOrderDistributionNum());
        pstmt.setInt(16+4,pData.getOrderShipmentNum());
        pstmt.setString(17+4,pData.getShipBranchCode());
        pstmt.setString(18+4,pData.getItemDesc());
        pstmt.setString(19+4,pData.getItemDesc2());
        pstmt.setString(20+4,pData.getUom());
        pstmt.setString(21+4,pData.getCustIndicatorCode());
        pstmt.setInt(22+4,pData.getOrderQty());
        pstmt.setInt(23+4,pData.getShipQty());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a BackorderData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A BackorderData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new BackorderData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static BackorderData insert(Connection pCon, BackorderData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a BackorderData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A BackorderData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, BackorderData pData, boolean pLogFl)
        throws SQLException {
        BackorderData oldData = null;
        if(pLogFl) {
          int id = pData.getBackorderId();
          try {
          oldData = BackorderDataAccess.select(pCon,id);
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
     * Deletes a BackorderData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pBackorderId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pBackorderId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_BACKORDER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_BACKORDER d WHERE BACKORDER_ID = " + pBackorderId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pBackorderId);
        return n;
     }

    /**
     * Deletes BackorderData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_BACKORDER SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_BACKORDER d ");
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

