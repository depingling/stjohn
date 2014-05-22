
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        InvoiceCustDetailDataAccess
 * Description:  This class is used to build access methods to the CLW_INVOICE_CUST_DETAIL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.InvoiceCustDetailData;
import com.cleanwise.service.api.value.InvoiceCustDetailDataVector;
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
 * <code>InvoiceCustDetailDataAccess</code>
 */
public class InvoiceCustDetailDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(InvoiceCustDetailDataAccess.class.getName());

    /** <code>CLW_INVOICE_CUST_DETAIL</code> table name */
	/* Primary key: INVOICE_CUST_DETAIL_ID */
	
    public static final String CLW_INVOICE_CUST_DETAIL = "CLW_INVOICE_CUST_DETAIL";
    
    /** <code>INVOICE_CUST_DETAIL_ID</code> INVOICE_CUST_DETAIL_ID column of table CLW_INVOICE_CUST_DETAIL */
    public static final String INVOICE_CUST_DETAIL_ID = "INVOICE_CUST_DETAIL_ID";
    /** <code>INVOICE_CUST_ID</code> INVOICE_CUST_ID column of table CLW_INVOICE_CUST_DETAIL */
    public static final String INVOICE_CUST_ID = "INVOICE_CUST_ID";
    /** <code>INVOICE_DETAIL_STATUS_CD</code> INVOICE_DETAIL_STATUS_CD column of table CLW_INVOICE_CUST_DETAIL */
    public static final String INVOICE_DETAIL_STATUS_CD = "INVOICE_DETAIL_STATUS_CD";
    /** <code>ORDER_ITEM_ID</code> ORDER_ITEM_ID column of table CLW_INVOICE_CUST_DETAIL */
    public static final String ORDER_ITEM_ID = "ORDER_ITEM_ID";
    /** <code>LINE_NUMBER</code> LINE_NUMBER column of table CLW_INVOICE_CUST_DETAIL */
    public static final String LINE_NUMBER = "LINE_NUMBER";
    /** <code>ITEM_SKU_NUM</code> ITEM_SKU_NUM column of table CLW_INVOICE_CUST_DETAIL */
    public static final String ITEM_SKU_NUM = "ITEM_SKU_NUM";
    /** <code>ITEM_SHORT_DESC</code> ITEM_SHORT_DESC column of table CLW_INVOICE_CUST_DETAIL */
    public static final String ITEM_SHORT_DESC = "ITEM_SHORT_DESC";
    /** <code>ITEM_UOM</code> ITEM_UOM column of table CLW_INVOICE_CUST_DETAIL */
    public static final String ITEM_UOM = "ITEM_UOM";
    /** <code>ITEM_PACK</code> ITEM_PACK column of table CLW_INVOICE_CUST_DETAIL */
    public static final String ITEM_PACK = "ITEM_PACK";
    /** <code>ITEM_QUANTITY</code> ITEM_QUANTITY column of table CLW_INVOICE_CUST_DETAIL */
    public static final String ITEM_QUANTITY = "ITEM_QUANTITY";
    /** <code>CUST_CONTRACT_PRICE</code> CUST_CONTRACT_PRICE column of table CLW_INVOICE_CUST_DETAIL */
    public static final String CUST_CONTRACT_PRICE = "CUST_CONTRACT_PRICE";
    /** <code>LINE_TOTAL</code> LINE_TOTAL column of table CLW_INVOICE_CUST_DETAIL */
    public static final String LINE_TOTAL = "LINE_TOTAL";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_INVOICE_CUST_DETAIL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_INVOICE_CUST_DETAIL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_INVOICE_CUST_DETAIL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_INVOICE_CUST_DETAIL */
    public static final String MOD_BY = "MOD_BY";
    /** <code>SHIP_STATUS_CD</code> SHIP_STATUS_CD column of table CLW_INVOICE_CUST_DETAIL */
    public static final String SHIP_STATUS_CD = "SHIP_STATUS_CD";
    /** <code>REBATE_STATUS_CD</code> REBATE_STATUS_CD column of table CLW_INVOICE_CUST_DETAIL */
    public static final String REBATE_STATUS_CD = "REBATE_STATUS_CD";

    /**
     * Constructor.
     */
    public InvoiceCustDetailDataAccess()
    {
    }

    /**
     * Gets a InvoiceCustDetailData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pInvoiceCustDetailId The key requested.
     * @return new InvoiceCustDetailData()
     * @throws            SQLException
     */
    public static InvoiceCustDetailData select(Connection pCon, int pInvoiceCustDetailId)
        throws SQLException, DataNotFoundException {
        InvoiceCustDetailData x=null;
        String sql="SELECT INVOICE_CUST_DETAIL_ID,INVOICE_CUST_ID,INVOICE_DETAIL_STATUS_CD,ORDER_ITEM_ID,LINE_NUMBER,ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_QUANTITY,CUST_CONTRACT_PRICE,LINE_TOTAL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SHIP_STATUS_CD,REBATE_STATUS_CD FROM CLW_INVOICE_CUST_DETAIL WHERE INVOICE_CUST_DETAIL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pInvoiceCustDetailId=" + pInvoiceCustDetailId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pInvoiceCustDetailId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=InvoiceCustDetailData.createValue();
            
            x.setInvoiceCustDetailId(rs.getInt(1));
            x.setInvoiceCustId(rs.getInt(2));
            x.setInvoiceDetailStatusCd(rs.getString(3));
            x.setOrderItemId(rs.getInt(4));
            x.setLineNumber(rs.getInt(5));
            x.setItemSkuNum(rs.getInt(6));
            x.setItemShortDesc(rs.getString(7));
            x.setItemUom(rs.getString(8));
            x.setItemPack(rs.getString(9));
            x.setItemQuantity(rs.getInt(10));
            x.setCustContractPrice(rs.getBigDecimal(11));
            x.setLineTotal(rs.getBigDecimal(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setShipStatusCd(rs.getString(17));
            x.setRebateStatusCd(rs.getString(18));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("INVOICE_CUST_DETAIL_ID :" + pInvoiceCustDetailId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a InvoiceCustDetailDataVector object that consists
     * of InvoiceCustDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new InvoiceCustDetailDataVector()
     * @throws            SQLException
     */
    public static InvoiceCustDetailDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a InvoiceCustDetailData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_INVOICE_CUST_DETAIL.INVOICE_CUST_DETAIL_ID,CLW_INVOICE_CUST_DETAIL.INVOICE_CUST_ID,CLW_INVOICE_CUST_DETAIL.INVOICE_DETAIL_STATUS_CD,CLW_INVOICE_CUST_DETAIL.ORDER_ITEM_ID,CLW_INVOICE_CUST_DETAIL.LINE_NUMBER,CLW_INVOICE_CUST_DETAIL.ITEM_SKU_NUM,CLW_INVOICE_CUST_DETAIL.ITEM_SHORT_DESC,CLW_INVOICE_CUST_DETAIL.ITEM_UOM,CLW_INVOICE_CUST_DETAIL.ITEM_PACK,CLW_INVOICE_CUST_DETAIL.ITEM_QUANTITY,CLW_INVOICE_CUST_DETAIL.CUST_CONTRACT_PRICE,CLW_INVOICE_CUST_DETAIL.LINE_TOTAL,CLW_INVOICE_CUST_DETAIL.ADD_DATE,CLW_INVOICE_CUST_DETAIL.ADD_BY,CLW_INVOICE_CUST_DETAIL.MOD_DATE,CLW_INVOICE_CUST_DETAIL.MOD_BY,CLW_INVOICE_CUST_DETAIL.SHIP_STATUS_CD,CLW_INVOICE_CUST_DETAIL.REBATE_STATUS_CD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated InvoiceCustDetailData Object.
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
    *@returns a populated InvoiceCustDetailData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         InvoiceCustDetailData x = InvoiceCustDetailData.createValue();
         
         x.setInvoiceCustDetailId(rs.getInt(1+offset));
         x.setInvoiceCustId(rs.getInt(2+offset));
         x.setInvoiceDetailStatusCd(rs.getString(3+offset));
         x.setOrderItemId(rs.getInt(4+offset));
         x.setLineNumber(rs.getInt(5+offset));
         x.setItemSkuNum(rs.getInt(6+offset));
         x.setItemShortDesc(rs.getString(7+offset));
         x.setItemUom(rs.getString(8+offset));
         x.setItemPack(rs.getString(9+offset));
         x.setItemQuantity(rs.getInt(10+offset));
         x.setCustContractPrice(rs.getBigDecimal(11+offset));
         x.setLineTotal(rs.getBigDecimal(12+offset));
         x.setAddDate(rs.getTimestamp(13+offset));
         x.setAddBy(rs.getString(14+offset));
         x.setModDate(rs.getTimestamp(15+offset));
         x.setModBy(rs.getString(16+offset));
         x.setShipStatusCd(rs.getString(17+offset));
         x.setRebateStatusCd(rs.getString(18+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the InvoiceCustDetailData Object represents.
    */
    public int getColumnCount(){
        return 18;
    }

    /**
     * Gets a InvoiceCustDetailDataVector object that consists
     * of InvoiceCustDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new InvoiceCustDetailDataVector()
     * @throws            SQLException
     */
    public static InvoiceCustDetailDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT INVOICE_CUST_DETAIL_ID,INVOICE_CUST_ID,INVOICE_DETAIL_STATUS_CD,ORDER_ITEM_ID,LINE_NUMBER,ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_QUANTITY,CUST_CONTRACT_PRICE,LINE_TOTAL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SHIP_STATUS_CD,REBATE_STATUS_CD FROM CLW_INVOICE_CUST_DETAIL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_INVOICE_CUST_DETAIL.INVOICE_CUST_DETAIL_ID,CLW_INVOICE_CUST_DETAIL.INVOICE_CUST_ID,CLW_INVOICE_CUST_DETAIL.INVOICE_DETAIL_STATUS_CD,CLW_INVOICE_CUST_DETAIL.ORDER_ITEM_ID,CLW_INVOICE_CUST_DETAIL.LINE_NUMBER,CLW_INVOICE_CUST_DETAIL.ITEM_SKU_NUM,CLW_INVOICE_CUST_DETAIL.ITEM_SHORT_DESC,CLW_INVOICE_CUST_DETAIL.ITEM_UOM,CLW_INVOICE_CUST_DETAIL.ITEM_PACK,CLW_INVOICE_CUST_DETAIL.ITEM_QUANTITY,CLW_INVOICE_CUST_DETAIL.CUST_CONTRACT_PRICE,CLW_INVOICE_CUST_DETAIL.LINE_TOTAL,CLW_INVOICE_CUST_DETAIL.ADD_DATE,CLW_INVOICE_CUST_DETAIL.ADD_BY,CLW_INVOICE_CUST_DETAIL.MOD_DATE,CLW_INVOICE_CUST_DETAIL.MOD_BY,CLW_INVOICE_CUST_DETAIL.SHIP_STATUS_CD,CLW_INVOICE_CUST_DETAIL.REBATE_STATUS_CD FROM CLW_INVOICE_CUST_DETAIL");
                where = pCriteria.getSqlClause("CLW_INVOICE_CUST_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_INVOICE_CUST_DETAIL.equals(otherTable)){
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
        InvoiceCustDetailDataVector v = new InvoiceCustDetailDataVector();
        while (rs.next()) {
            InvoiceCustDetailData x = InvoiceCustDetailData.createValue();
            
            x.setInvoiceCustDetailId(rs.getInt(1));
            x.setInvoiceCustId(rs.getInt(2));
            x.setInvoiceDetailStatusCd(rs.getString(3));
            x.setOrderItemId(rs.getInt(4));
            x.setLineNumber(rs.getInt(5));
            x.setItemSkuNum(rs.getInt(6));
            x.setItemShortDesc(rs.getString(7));
            x.setItemUom(rs.getString(8));
            x.setItemPack(rs.getString(9));
            x.setItemQuantity(rs.getInt(10));
            x.setCustContractPrice(rs.getBigDecimal(11));
            x.setLineTotal(rs.getBigDecimal(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setShipStatusCd(rs.getString(17));
            x.setRebateStatusCd(rs.getString(18));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a InvoiceCustDetailDataVector object that consists
     * of InvoiceCustDetailData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for InvoiceCustDetailData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new InvoiceCustDetailDataVector()
     * @throws            SQLException
     */
    public static InvoiceCustDetailDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        InvoiceCustDetailDataVector v = new InvoiceCustDetailDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT INVOICE_CUST_DETAIL_ID,INVOICE_CUST_ID,INVOICE_DETAIL_STATUS_CD,ORDER_ITEM_ID,LINE_NUMBER,ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_QUANTITY,CUST_CONTRACT_PRICE,LINE_TOTAL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SHIP_STATUS_CD,REBATE_STATUS_CD FROM CLW_INVOICE_CUST_DETAIL WHERE INVOICE_CUST_DETAIL_ID IN (");

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
            InvoiceCustDetailData x=null;
            while (rs.next()) {
                // build the object
                x=InvoiceCustDetailData.createValue();
                
                x.setInvoiceCustDetailId(rs.getInt(1));
                x.setInvoiceCustId(rs.getInt(2));
                x.setInvoiceDetailStatusCd(rs.getString(3));
                x.setOrderItemId(rs.getInt(4));
                x.setLineNumber(rs.getInt(5));
                x.setItemSkuNum(rs.getInt(6));
                x.setItemShortDesc(rs.getString(7));
                x.setItemUom(rs.getString(8));
                x.setItemPack(rs.getString(9));
                x.setItemQuantity(rs.getInt(10));
                x.setCustContractPrice(rs.getBigDecimal(11));
                x.setLineTotal(rs.getBigDecimal(12));
                x.setAddDate(rs.getTimestamp(13));
                x.setAddBy(rs.getString(14));
                x.setModDate(rs.getTimestamp(15));
                x.setModBy(rs.getString(16));
                x.setShipStatusCd(rs.getString(17));
                x.setRebateStatusCd(rs.getString(18));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a InvoiceCustDetailDataVector object of all
     * InvoiceCustDetailData objects in the database.
     * @param pCon An open database connection.
     * @return new InvoiceCustDetailDataVector()
     * @throws            SQLException
     */
    public static InvoiceCustDetailDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT INVOICE_CUST_DETAIL_ID,INVOICE_CUST_ID,INVOICE_DETAIL_STATUS_CD,ORDER_ITEM_ID,LINE_NUMBER,ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_QUANTITY,CUST_CONTRACT_PRICE,LINE_TOTAL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SHIP_STATUS_CD,REBATE_STATUS_CD FROM CLW_INVOICE_CUST_DETAIL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        InvoiceCustDetailDataVector v = new InvoiceCustDetailDataVector();
        InvoiceCustDetailData x = null;
        while (rs.next()) {
            // build the object
            x = InvoiceCustDetailData.createValue();
            
            x.setInvoiceCustDetailId(rs.getInt(1));
            x.setInvoiceCustId(rs.getInt(2));
            x.setInvoiceDetailStatusCd(rs.getString(3));
            x.setOrderItemId(rs.getInt(4));
            x.setLineNumber(rs.getInt(5));
            x.setItemSkuNum(rs.getInt(6));
            x.setItemShortDesc(rs.getString(7));
            x.setItemUom(rs.getString(8));
            x.setItemPack(rs.getString(9));
            x.setItemQuantity(rs.getInt(10));
            x.setCustContractPrice(rs.getBigDecimal(11));
            x.setLineTotal(rs.getBigDecimal(12));
            x.setAddDate(rs.getTimestamp(13));
            x.setAddBy(rs.getString(14));
            x.setModDate(rs.getTimestamp(15));
            x.setModBy(rs.getString(16));
            x.setShipStatusCd(rs.getString(17));
            x.setRebateStatusCd(rs.getString(18));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * InvoiceCustDetailData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT INVOICE_CUST_DETAIL_ID FROM CLW_INVOICE_CUST_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVOICE_CUST_DETAIL");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVOICE_CUST_DETAIL");
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
     * Inserts a InvoiceCustDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceCustDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new InvoiceCustDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InvoiceCustDetailData insert(Connection pCon, InvoiceCustDetailData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_INVOICE_CUST_DETAIL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_INVOICE_CUST_DETAIL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setInvoiceCustDetailId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_INVOICE_CUST_DETAIL (INVOICE_CUST_DETAIL_ID,INVOICE_CUST_ID,INVOICE_DETAIL_STATUS_CD,ORDER_ITEM_ID,LINE_NUMBER,ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_QUANTITY,CUST_CONTRACT_PRICE,LINE_TOTAL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SHIP_STATUS_CD,REBATE_STATUS_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getInvoiceCustDetailId());
        pstmt.setInt(2,pData.getInvoiceCustId());
        pstmt.setString(3,pData.getInvoiceDetailStatusCd());
        pstmt.setInt(4,pData.getOrderItemId());
        pstmt.setInt(5,pData.getLineNumber());
        pstmt.setInt(6,pData.getItemSkuNum());
        pstmt.setString(7,pData.getItemShortDesc());
        pstmt.setString(8,pData.getItemUom());
        pstmt.setString(9,pData.getItemPack());
        pstmt.setInt(10,pData.getItemQuantity());
        pstmt.setBigDecimal(11,pData.getCustContractPrice());
        pstmt.setBigDecimal(12,pData.getLineTotal());
        pstmt.setTimestamp(13,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(14,pData.getAddBy());
        pstmt.setTimestamp(15,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(16,pData.getModBy());
        pstmt.setString(17,pData.getShipStatusCd());
        pstmt.setString(18,pData.getRebateStatusCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   INVOICE_CUST_DETAIL_ID="+pData.getInvoiceCustDetailId());
            log.debug("SQL:   INVOICE_CUST_ID="+pData.getInvoiceCustId());
            log.debug("SQL:   INVOICE_DETAIL_STATUS_CD="+pData.getInvoiceDetailStatusCd());
            log.debug("SQL:   ORDER_ITEM_ID="+pData.getOrderItemId());
            log.debug("SQL:   LINE_NUMBER="+pData.getLineNumber());
            log.debug("SQL:   ITEM_SKU_NUM="+pData.getItemSkuNum());
            log.debug("SQL:   ITEM_SHORT_DESC="+pData.getItemShortDesc());
            log.debug("SQL:   ITEM_UOM="+pData.getItemUom());
            log.debug("SQL:   ITEM_PACK="+pData.getItemPack());
            log.debug("SQL:   ITEM_QUANTITY="+pData.getItemQuantity());
            log.debug("SQL:   CUST_CONTRACT_PRICE="+pData.getCustContractPrice());
            log.debug("SQL:   LINE_TOTAL="+pData.getLineTotal());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   SHIP_STATUS_CD="+pData.getShipStatusCd());
            log.debug("SQL:   REBATE_STATUS_CD="+pData.getRebateStatusCd());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setInvoiceCustDetailId(0);
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
     * Updates a InvoiceCustDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceCustDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InvoiceCustDetailData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_INVOICE_CUST_DETAIL SET INVOICE_CUST_ID = ?,INVOICE_DETAIL_STATUS_CD = ?,ORDER_ITEM_ID = ?,LINE_NUMBER = ?,ITEM_SKU_NUM = ?,ITEM_SHORT_DESC = ?,ITEM_UOM = ?,ITEM_PACK = ?,ITEM_QUANTITY = ?,CUST_CONTRACT_PRICE = ?,LINE_TOTAL = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,SHIP_STATUS_CD = ?,REBATE_STATUS_CD = ? WHERE INVOICE_CUST_DETAIL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getInvoiceCustId());
        pstmt.setString(i++,pData.getInvoiceDetailStatusCd());
        pstmt.setInt(i++,pData.getOrderItemId());
        pstmt.setInt(i++,pData.getLineNumber());
        pstmt.setInt(i++,pData.getItemSkuNum());
        pstmt.setString(i++,pData.getItemShortDesc());
        pstmt.setString(i++,pData.getItemUom());
        pstmt.setString(i++,pData.getItemPack());
        pstmt.setInt(i++,pData.getItemQuantity());
        pstmt.setBigDecimal(i++,pData.getCustContractPrice());
        pstmt.setBigDecimal(i++,pData.getLineTotal());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setString(i++,pData.getShipStatusCd());
        pstmt.setString(i++,pData.getRebateStatusCd());
        pstmt.setInt(i++,pData.getInvoiceCustDetailId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   INVOICE_CUST_ID="+pData.getInvoiceCustId());
            log.debug("SQL:   INVOICE_DETAIL_STATUS_CD="+pData.getInvoiceDetailStatusCd());
            log.debug("SQL:   ORDER_ITEM_ID="+pData.getOrderItemId());
            log.debug("SQL:   LINE_NUMBER="+pData.getLineNumber());
            log.debug("SQL:   ITEM_SKU_NUM="+pData.getItemSkuNum());
            log.debug("SQL:   ITEM_SHORT_DESC="+pData.getItemShortDesc());
            log.debug("SQL:   ITEM_UOM="+pData.getItemUom());
            log.debug("SQL:   ITEM_PACK="+pData.getItemPack());
            log.debug("SQL:   ITEM_QUANTITY="+pData.getItemQuantity());
            log.debug("SQL:   CUST_CONTRACT_PRICE="+pData.getCustContractPrice());
            log.debug("SQL:   LINE_TOTAL="+pData.getLineTotal());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   SHIP_STATUS_CD="+pData.getShipStatusCd());
            log.debug("SQL:   REBATE_STATUS_CD="+pData.getRebateStatusCd());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a InvoiceCustDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInvoiceCustDetailId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInvoiceCustDetailId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_INVOICE_CUST_DETAIL WHERE INVOICE_CUST_DETAIL_ID = " + pInvoiceCustDetailId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes InvoiceCustDetailData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_INVOICE_CUST_DETAIL");
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
     * Inserts a InvoiceCustDetailData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceCustDetailData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, InvoiceCustDetailData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_INVOICE_CUST_DETAIL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "INVOICE_CUST_DETAIL_ID,INVOICE_CUST_ID,INVOICE_DETAIL_STATUS_CD,ORDER_ITEM_ID,LINE_NUMBER,ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_QUANTITY,CUST_CONTRACT_PRICE,LINE_TOTAL,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,SHIP_STATUS_CD,REBATE_STATUS_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getInvoiceCustDetailId());
        pstmt.setInt(2+4,pData.getInvoiceCustId());
        pstmt.setString(3+4,pData.getInvoiceDetailStatusCd());
        pstmt.setInt(4+4,pData.getOrderItemId());
        pstmt.setInt(5+4,pData.getLineNumber());
        pstmt.setInt(6+4,pData.getItemSkuNum());
        pstmt.setString(7+4,pData.getItemShortDesc());
        pstmt.setString(8+4,pData.getItemUom());
        pstmt.setString(9+4,pData.getItemPack());
        pstmt.setInt(10+4,pData.getItemQuantity());
        pstmt.setBigDecimal(11+4,pData.getCustContractPrice());
        pstmt.setBigDecimal(12+4,pData.getLineTotal());
        pstmt.setTimestamp(13+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(14+4,pData.getAddBy());
        pstmt.setTimestamp(15+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(16+4,pData.getModBy());
        pstmt.setString(17+4,pData.getShipStatusCd());
        pstmt.setString(18+4,pData.getRebateStatusCd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a InvoiceCustDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceCustDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new InvoiceCustDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InvoiceCustDetailData insert(Connection pCon, InvoiceCustDetailData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a InvoiceCustDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceCustDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InvoiceCustDetailData pData, boolean pLogFl)
        throws SQLException {
        InvoiceCustDetailData oldData = null;
        if(pLogFl) {
          int id = pData.getInvoiceCustDetailId();
          try {
          oldData = InvoiceCustDetailDataAccess.select(pCon,id);
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
     * Deletes a InvoiceCustDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInvoiceCustDetailId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInvoiceCustDetailId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_INVOICE_CUST_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INVOICE_CUST_DETAIL d WHERE INVOICE_CUST_DETAIL_ID = " + pInvoiceCustDetailId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pInvoiceCustDetailId);
        return n;
     }

    /**
     * Deletes InvoiceCustDetailData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_INVOICE_CUST_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INVOICE_CUST_DETAIL d ");
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

