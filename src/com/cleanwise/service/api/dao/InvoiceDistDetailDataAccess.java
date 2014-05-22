
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        InvoiceDistDetailDataAccess
 * Description:  This class is used to build access methods to the CLW_INVOICE_DIST_DETAIL table.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
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
 * <code>InvoiceDistDetailDataAccess</code>
 */
public class InvoiceDistDetailDataAccess extends DataAccessImpl
{
    private static Category log = Category.getInstance(InvoiceDistDetailDataAccess.class.getName());

    /** <code>CLW_INVOICE_DIST_DETAIL</code> table name */
	/* Primary key: INVOICE_DIST_DETAIL_ID */
	
    public static final String CLW_INVOICE_DIST_DETAIL = "CLW_INVOICE_DIST_DETAIL";
    
    /** <code>INVOICE_DIST_DETAIL_ID</code> INVOICE_DIST_DETAIL_ID column of table CLW_INVOICE_DIST_DETAIL */
    public static final String INVOICE_DIST_DETAIL_ID = "INVOICE_DIST_DETAIL_ID";
    /** <code>INVOICE_DIST_ID</code> INVOICE_DIST_ID column of table CLW_INVOICE_DIST_DETAIL */
    public static final String INVOICE_DIST_ID = "INVOICE_DIST_ID";
    /** <code>INVOICE_CUST_ID</code> INVOICE_CUST_ID column of table CLW_INVOICE_DIST_DETAIL */
    public static final String INVOICE_CUST_ID = "INVOICE_CUST_ID";
    /** <code>INVOICE_CUST_DETAIL_ID</code> INVOICE_CUST_DETAIL_ID column of table CLW_INVOICE_DIST_DETAIL */
    public static final String INVOICE_CUST_DETAIL_ID = "INVOICE_CUST_DETAIL_ID";
    /** <code>ORDER_ITEM_ID</code> ORDER_ITEM_ID column of table CLW_INVOICE_DIST_DETAIL */
    public static final String ORDER_ITEM_ID = "ORDER_ITEM_ID";
    /** <code>ERP_PO_LINE_NUM</code> ERP_PO_LINE_NUM column of table CLW_INVOICE_DIST_DETAIL */
    public static final String ERP_PO_LINE_NUM = "ERP_PO_LINE_NUM";
    /** <code>DIST_LINE_NUMBER</code> DIST_LINE_NUMBER column of table CLW_INVOICE_DIST_DETAIL */
    public static final String DIST_LINE_NUMBER = "DIST_LINE_NUMBER";
    /** <code>DIST_ITEM_SKU_NUM</code> DIST_ITEM_SKU_NUM column of table CLW_INVOICE_DIST_DETAIL */
    public static final String DIST_ITEM_SKU_NUM = "DIST_ITEM_SKU_NUM";
    /** <code>DIST_ITEM_SHORT_DESC</code> DIST_ITEM_SHORT_DESC column of table CLW_INVOICE_DIST_DETAIL */
    public static final String DIST_ITEM_SHORT_DESC = "DIST_ITEM_SHORT_DESC";
    /** <code>DIST_ITEM_UOM</code> DIST_ITEM_UOM column of table CLW_INVOICE_DIST_DETAIL */
    public static final String DIST_ITEM_UOM = "DIST_ITEM_UOM";
    /** <code>DIST_ITEM_PACK</code> DIST_ITEM_PACK column of table CLW_INVOICE_DIST_DETAIL */
    public static final String DIST_ITEM_PACK = "DIST_ITEM_PACK";
    /** <code>DIST_ITEM_QUANTITY</code> DIST_ITEM_QUANTITY column of table CLW_INVOICE_DIST_DETAIL */
    public static final String DIST_ITEM_QUANTITY = "DIST_ITEM_QUANTITY";
    /** <code>ITEM_SKU_NUM</code> ITEM_SKU_NUM column of table CLW_INVOICE_DIST_DETAIL */
    public static final String ITEM_SKU_NUM = "ITEM_SKU_NUM";
    /** <code>ITEM_SHORT_DESC</code> ITEM_SHORT_DESC column of table CLW_INVOICE_DIST_DETAIL */
    public static final String ITEM_SHORT_DESC = "ITEM_SHORT_DESC";
    /** <code>ITEM_UOM</code> ITEM_UOM column of table CLW_INVOICE_DIST_DETAIL */
    public static final String ITEM_UOM = "ITEM_UOM";
    /** <code>ITEM_PACK</code> ITEM_PACK column of table CLW_INVOICE_DIST_DETAIL */
    public static final String ITEM_PACK = "ITEM_PACK";
    /** <code>ITEM_QUANTITY</code> ITEM_QUANTITY column of table CLW_INVOICE_DIST_DETAIL */
    public static final String ITEM_QUANTITY = "ITEM_QUANTITY";
    /** <code>LINE_TOTAL</code> LINE_TOTAL column of table CLW_INVOICE_DIST_DETAIL */
    public static final String LINE_TOTAL = "LINE_TOTAL";
    /** <code>ITEM_COST</code> ITEM_COST column of table CLW_INVOICE_DIST_DETAIL */
    public static final String ITEM_COST = "ITEM_COST";
    /** <code>ADJUSTED_COST</code> ADJUSTED_COST column of table CLW_INVOICE_DIST_DETAIL */
    public static final String ADJUSTED_COST = "ADJUSTED_COST";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_INVOICE_DIST_DETAIL */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_INVOICE_DIST_DETAIL */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_INVOICE_DIST_DETAIL */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_INVOICE_DIST_DETAIL */
    public static final String MOD_BY = "MOD_BY";
    /** <code>ITEM_RECEIVED_COST</code> ITEM_RECEIVED_COST column of table CLW_INVOICE_DIST_DETAIL */
    public static final String ITEM_RECEIVED_COST = "ITEM_RECEIVED_COST";
    /** <code>DIST_ITEM_QTY_RECEIVED</code> DIST_ITEM_QTY_RECEIVED column of table CLW_INVOICE_DIST_DETAIL */
    public static final String DIST_ITEM_QTY_RECEIVED = "DIST_ITEM_QTY_RECEIVED";
    /** <code>ERP_ACCOUNT_CODE</code> ERP_ACCOUNT_CODE column of table CLW_INVOICE_DIST_DETAIL */
    public static final String ERP_ACCOUNT_CODE = "ERP_ACCOUNT_CODE";
    /** <code>ERP_PO_REF_LINE_NUM</code> ERP_PO_REF_LINE_NUM column of table CLW_INVOICE_DIST_DETAIL */
    public static final String ERP_PO_REF_LINE_NUM = "ERP_PO_REF_LINE_NUM";
    /** <code>INVOICE_DIST_SKU_NUM</code> INVOICE_DIST_SKU_NUM column of table CLW_INVOICE_DIST_DETAIL */
    public static final String INVOICE_DIST_SKU_NUM = "INVOICE_DIST_SKU_NUM";
    /** <code>DIST_INTO_STOCK_COST</code> DIST_INTO_STOCK_COST column of table CLW_INVOICE_DIST_DETAIL */
    public static final String DIST_INTO_STOCK_COST = "DIST_INTO_STOCK_COST";
    /** <code>SHIP_STATUS_CD</code> SHIP_STATUS_CD column of table CLW_INVOICE_DIST_DETAIL */
    public static final String SHIP_STATUS_CD = "SHIP_STATUS_CD";

    /**
     * Constructor.
     */
    public InvoiceDistDetailDataAccess()
    {
    }

    /**
     * Gets a InvoiceDistDetailData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pInvoiceDistDetailId The key requested.
     * @return new InvoiceDistDetailData()
     * @throws            SQLException
     */
    public static InvoiceDistDetailData select(Connection pCon, int pInvoiceDistDetailId)
        throws SQLException, DataNotFoundException {
        InvoiceDistDetailData x=null;
        String sql="SELECT INVOICE_DIST_DETAIL_ID,INVOICE_DIST_ID,INVOICE_CUST_ID,INVOICE_CUST_DETAIL_ID,ORDER_ITEM_ID,ERP_PO_LINE_NUM,DIST_LINE_NUMBER,DIST_ITEM_SKU_NUM,DIST_ITEM_SHORT_DESC,DIST_ITEM_UOM,DIST_ITEM_PACK,DIST_ITEM_QUANTITY,ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_QUANTITY,LINE_TOTAL,ITEM_COST,ADJUSTED_COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ITEM_RECEIVED_COST,DIST_ITEM_QTY_RECEIVED,ERP_ACCOUNT_CODE,ERP_PO_REF_LINE_NUM,INVOICE_DIST_SKU_NUM,DIST_INTO_STOCK_COST,SHIP_STATUS_CD FROM CLW_INVOICE_DIST_DETAIL WHERE INVOICE_DIST_DETAIL_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL: pInvoiceDistDetailId=" + pInvoiceDistDetailId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pInvoiceDistDetailId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=InvoiceDistDetailData.createValue();
            
            x.setInvoiceDistDetailId(rs.getInt(1));
            x.setInvoiceDistId(rs.getInt(2));
            x.setInvoiceCustId(rs.getInt(3));
            x.setInvoiceCustDetailId(rs.getInt(4));
            x.setOrderItemId(rs.getInt(5));
            x.setErpPoLineNum(rs.getInt(6));
            x.setDistLineNumber(rs.getInt(7));
            x.setDistItemSkuNum(rs.getString(8));
            x.setDistItemShortDesc(rs.getString(9));
            x.setDistItemUom(rs.getString(10));
            x.setDistItemPack(rs.getString(11));
            x.setDistItemQuantity(rs.getInt(12));
            x.setItemSkuNum(rs.getInt(13));
            x.setItemShortDesc(rs.getString(14));
            x.setItemUom(rs.getString(15));
            x.setItemPack(rs.getString(16));
            x.setItemQuantity(rs.getInt(17));
            x.setLineTotal(rs.getBigDecimal(18));
            x.setItemCost(rs.getBigDecimal(19));
            x.setAdjustedCost(rs.getBigDecimal(20));
            x.setAddDate(rs.getTimestamp(21));
            x.setAddBy(rs.getString(22));
            x.setModDate(rs.getTimestamp(23));
            x.setModBy(rs.getString(24));
            x.setItemReceivedCost(rs.getBigDecimal(25));
            x.setDistItemQtyReceived(rs.getInt(26));
            x.setErpAccountCode(rs.getString(27));
            x.setErpPoRefLineNum(rs.getInt(28));
            x.setInvoiceDistSkuNum(rs.getString(29));
            x.setDistIntoStockCost(rs.getBigDecimal(30));
            x.setShipStatusCd(rs.getString(31));

        } else {
            rs.close();
            stmt.close();
            throw new DataNotFoundException("INVOICE_DIST_DETAIL_ID :" + pInvoiceDistDetailId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a InvoiceDistDetailDataVector object that consists
     * of InvoiceDistDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new InvoiceDistDetailDataVector()
     * @throws            SQLException
     */
    public static InvoiceDistDetailDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
    *@Returns a String of the columns suitable for using in a sql statement that may then be parsed into a InvoiceDistDetailData Object
    *using the parseResultSet method.
    */
    public String getSelectColumns(){
        return "CLW_INVOICE_DIST_DETAIL.INVOICE_DIST_DETAIL_ID,CLW_INVOICE_DIST_DETAIL.INVOICE_DIST_ID,CLW_INVOICE_DIST_DETAIL.INVOICE_CUST_ID,CLW_INVOICE_DIST_DETAIL.INVOICE_CUST_DETAIL_ID,CLW_INVOICE_DIST_DETAIL.ORDER_ITEM_ID,CLW_INVOICE_DIST_DETAIL.ERP_PO_LINE_NUM,CLW_INVOICE_DIST_DETAIL.DIST_LINE_NUMBER,CLW_INVOICE_DIST_DETAIL.DIST_ITEM_SKU_NUM,CLW_INVOICE_DIST_DETAIL.DIST_ITEM_SHORT_DESC,CLW_INVOICE_DIST_DETAIL.DIST_ITEM_UOM,CLW_INVOICE_DIST_DETAIL.DIST_ITEM_PACK,CLW_INVOICE_DIST_DETAIL.DIST_ITEM_QUANTITY,CLW_INVOICE_DIST_DETAIL.ITEM_SKU_NUM,CLW_INVOICE_DIST_DETAIL.ITEM_SHORT_DESC,CLW_INVOICE_DIST_DETAIL.ITEM_UOM,CLW_INVOICE_DIST_DETAIL.ITEM_PACK,CLW_INVOICE_DIST_DETAIL.ITEM_QUANTITY,CLW_INVOICE_DIST_DETAIL.LINE_TOTAL,CLW_INVOICE_DIST_DETAIL.ITEM_COST,CLW_INVOICE_DIST_DETAIL.ADJUSTED_COST,CLW_INVOICE_DIST_DETAIL.ADD_DATE,CLW_INVOICE_DIST_DETAIL.ADD_BY,CLW_INVOICE_DIST_DETAIL.MOD_DATE,CLW_INVOICE_DIST_DETAIL.MOD_BY,CLW_INVOICE_DIST_DETAIL.ITEM_RECEIVED_COST,CLW_INVOICE_DIST_DETAIL.DIST_ITEM_QTY_RECEIVED,CLW_INVOICE_DIST_DETAIL.ERP_ACCOUNT_CODE,CLW_INVOICE_DIST_DETAIL.ERP_PO_REF_LINE_NUM,CLW_INVOICE_DIST_DETAIL.INVOICE_DIST_SKU_NUM,CLW_INVOICE_DIST_DETAIL.DIST_INTO_STOCK_COST,CLW_INVOICE_DIST_DETAIL.SHIP_STATUS_CD";
    }


    /**
    *Parses a result set into a value object.  Uses the position of the columns as its key so this method should only
    *be used in conjunction with the getSelectColumns() method or the wrong data will be parsed into the wrong properties.
    *The result set is not incremented, so calls to this method leave the resultset object unchanged.
    *@param ResultSet an open result set.
    *@returns a populated InvoiceDistDetailData Object.
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
    *@returns a populated InvoiceDistDetailData Object.
    *@throws SQLException
    */
    public com.cleanwise.service.api.framework.ValueObject parseResultSet(ResultSet rs, int offset) throws SQLException{
         // build the object
         InvoiceDistDetailData x = InvoiceDistDetailData.createValue();
         
         x.setInvoiceDistDetailId(rs.getInt(1+offset));
         x.setInvoiceDistId(rs.getInt(2+offset));
         x.setInvoiceCustId(rs.getInt(3+offset));
         x.setInvoiceCustDetailId(rs.getInt(4+offset));
         x.setOrderItemId(rs.getInt(5+offset));
         x.setErpPoLineNum(rs.getInt(6+offset));
         x.setDistLineNumber(rs.getInt(7+offset));
         x.setDistItemSkuNum(rs.getString(8+offset));
         x.setDistItemShortDesc(rs.getString(9+offset));
         x.setDistItemUom(rs.getString(10+offset));
         x.setDistItemPack(rs.getString(11+offset));
         x.setDistItemQuantity(rs.getInt(12+offset));
         x.setItemSkuNum(rs.getInt(13+offset));
         x.setItemShortDesc(rs.getString(14+offset));
         x.setItemUom(rs.getString(15+offset));
         x.setItemPack(rs.getString(16+offset));
         x.setItemQuantity(rs.getInt(17+offset));
         x.setLineTotal(rs.getBigDecimal(18+offset));
         x.setItemCost(rs.getBigDecimal(19+offset));
         x.setAdjustedCost(rs.getBigDecimal(20+offset));
         x.setAddDate(rs.getTimestamp(21+offset));
         x.setAddBy(rs.getString(22+offset));
         x.setModDate(rs.getTimestamp(23+offset));
         x.setModBy(rs.getString(24+offset));
         x.setItemReceivedCost(rs.getBigDecimal(25+offset));
         x.setDistItemQtyReceived(rs.getInt(26+offset));
         x.setErpAccountCode(rs.getString(27+offset));
         x.setErpPoRefLineNum(rs.getInt(28+offset));
         x.setInvoiceDistSkuNum(rs.getString(29+offset));
         x.setDistIntoStockCost(rs.getBigDecimal(30+offset));
         x.setShipStatusCd(rs.getString(31+offset));
         return x;
    }

    /**
    *@Returns a count of the number of columns the InvoiceDistDetailData Object represents.
    */
    public int getColumnCount(){
        return 31;
    }

    /**
     * Gets a InvoiceDistDetailDataVector object that consists
     * of InvoiceDistDetailData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new InvoiceDistDetailDataVector()
     * @throws            SQLException
     */
    public static InvoiceDistDetailDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf;
        Collection otherTables = pCriteria.getJoinTables();
        String where;
        if(otherTables == null || otherTables.isEmpty()){
                sqlBuf = new StringBuffer("SELECT INVOICE_DIST_DETAIL_ID,INVOICE_DIST_ID,INVOICE_CUST_ID,INVOICE_CUST_DETAIL_ID,ORDER_ITEM_ID,ERP_PO_LINE_NUM,DIST_LINE_NUMBER,DIST_ITEM_SKU_NUM,DIST_ITEM_SHORT_DESC,DIST_ITEM_UOM,DIST_ITEM_PACK,DIST_ITEM_QUANTITY,ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_QUANTITY,LINE_TOTAL,ITEM_COST,ADJUSTED_COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ITEM_RECEIVED_COST,DIST_ITEM_QTY_RECEIVED,ERP_ACCOUNT_CODE,ERP_PO_REF_LINE_NUM,INVOICE_DIST_SKU_NUM,DIST_INTO_STOCK_COST,SHIP_STATUS_CD FROM CLW_INVOICE_DIST_DETAIL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT CLW_INVOICE_DIST_DETAIL.INVOICE_DIST_DETAIL_ID,CLW_INVOICE_DIST_DETAIL.INVOICE_DIST_ID,CLW_INVOICE_DIST_DETAIL.INVOICE_CUST_ID,CLW_INVOICE_DIST_DETAIL.INVOICE_CUST_DETAIL_ID,CLW_INVOICE_DIST_DETAIL.ORDER_ITEM_ID,CLW_INVOICE_DIST_DETAIL.ERP_PO_LINE_NUM,CLW_INVOICE_DIST_DETAIL.DIST_LINE_NUMBER,CLW_INVOICE_DIST_DETAIL.DIST_ITEM_SKU_NUM,CLW_INVOICE_DIST_DETAIL.DIST_ITEM_SHORT_DESC,CLW_INVOICE_DIST_DETAIL.DIST_ITEM_UOM,CLW_INVOICE_DIST_DETAIL.DIST_ITEM_PACK,CLW_INVOICE_DIST_DETAIL.DIST_ITEM_QUANTITY,CLW_INVOICE_DIST_DETAIL.ITEM_SKU_NUM,CLW_INVOICE_DIST_DETAIL.ITEM_SHORT_DESC,CLW_INVOICE_DIST_DETAIL.ITEM_UOM,CLW_INVOICE_DIST_DETAIL.ITEM_PACK,CLW_INVOICE_DIST_DETAIL.ITEM_QUANTITY,CLW_INVOICE_DIST_DETAIL.LINE_TOTAL,CLW_INVOICE_DIST_DETAIL.ITEM_COST,CLW_INVOICE_DIST_DETAIL.ADJUSTED_COST,CLW_INVOICE_DIST_DETAIL.ADD_DATE,CLW_INVOICE_DIST_DETAIL.ADD_BY,CLW_INVOICE_DIST_DETAIL.MOD_DATE,CLW_INVOICE_DIST_DETAIL.MOD_BY,CLW_INVOICE_DIST_DETAIL.ITEM_RECEIVED_COST,CLW_INVOICE_DIST_DETAIL.DIST_ITEM_QTY_RECEIVED,CLW_INVOICE_DIST_DETAIL.ERP_ACCOUNT_CODE,CLW_INVOICE_DIST_DETAIL.ERP_PO_REF_LINE_NUM,CLW_INVOICE_DIST_DETAIL.INVOICE_DIST_SKU_NUM,CLW_INVOICE_DIST_DETAIL.DIST_INTO_STOCK_COST,CLW_INVOICE_DIST_DETAIL.SHIP_STATUS_CD FROM CLW_INVOICE_DIST_DETAIL");
                where = pCriteria.getSqlClause("CLW_INVOICE_DIST_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_INVOICE_DIST_DETAIL.equals(otherTable)){
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
        InvoiceDistDetailDataVector v = new InvoiceDistDetailDataVector();
        while (rs.next()) {
            InvoiceDistDetailData x = InvoiceDistDetailData.createValue();
            
            x.setInvoiceDistDetailId(rs.getInt(1));
            x.setInvoiceDistId(rs.getInt(2));
            x.setInvoiceCustId(rs.getInt(3));
            x.setInvoiceCustDetailId(rs.getInt(4));
            x.setOrderItemId(rs.getInt(5));
            x.setErpPoLineNum(rs.getInt(6));
            x.setDistLineNumber(rs.getInt(7));
            x.setDistItemSkuNum(rs.getString(8));
            x.setDistItemShortDesc(rs.getString(9));
            x.setDistItemUom(rs.getString(10));
            x.setDistItemPack(rs.getString(11));
            x.setDistItemQuantity(rs.getInt(12));
            x.setItemSkuNum(rs.getInt(13));
            x.setItemShortDesc(rs.getString(14));
            x.setItemUom(rs.getString(15));
            x.setItemPack(rs.getString(16));
            x.setItemQuantity(rs.getInt(17));
            x.setLineTotal(rs.getBigDecimal(18));
            x.setItemCost(rs.getBigDecimal(19));
            x.setAdjustedCost(rs.getBigDecimal(20));
            x.setAddDate(rs.getTimestamp(21));
            x.setAddBy(rs.getString(22));
            x.setModDate(rs.getTimestamp(23));
            x.setModBy(rs.getString(24));
            x.setItemReceivedCost(rs.getBigDecimal(25));
            x.setDistItemQtyReceived(rs.getInt(26));
            x.setErpAccountCode(rs.getString(27));
            x.setErpPoRefLineNum(rs.getInt(28));
            x.setInvoiceDistSkuNum(rs.getString(29));
            x.setDistIntoStockCost(rs.getBigDecimal(30));
            x.setShipStatusCd(rs.getString(31));
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a InvoiceDistDetailDataVector object that consists
     * of InvoiceDistDetailData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for InvoiceDistDetailData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new InvoiceDistDetailDataVector()
     * @throws            SQLException
     */
    public static InvoiceDistDetailDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        InvoiceDistDetailDataVector v = new InvoiceDistDetailDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT INVOICE_DIST_DETAIL_ID,INVOICE_DIST_ID,INVOICE_CUST_ID,INVOICE_CUST_DETAIL_ID,ORDER_ITEM_ID,ERP_PO_LINE_NUM,DIST_LINE_NUMBER,DIST_ITEM_SKU_NUM,DIST_ITEM_SHORT_DESC,DIST_ITEM_UOM,DIST_ITEM_PACK,DIST_ITEM_QUANTITY,ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_QUANTITY,LINE_TOTAL,ITEM_COST,ADJUSTED_COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ITEM_RECEIVED_COST,DIST_ITEM_QTY_RECEIVED,ERP_ACCOUNT_CODE,ERP_PO_REF_LINE_NUM,INVOICE_DIST_SKU_NUM,DIST_INTO_STOCK_COST,SHIP_STATUS_CD FROM CLW_INVOICE_DIST_DETAIL WHERE INVOICE_DIST_DETAIL_ID IN (");

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
            InvoiceDistDetailData x=null;
            while (rs.next()) {
                // build the object
                x=InvoiceDistDetailData.createValue();
                
                x.setInvoiceDistDetailId(rs.getInt(1));
                x.setInvoiceDistId(rs.getInt(2));
                x.setInvoiceCustId(rs.getInt(3));
                x.setInvoiceCustDetailId(rs.getInt(4));
                x.setOrderItemId(rs.getInt(5));
                x.setErpPoLineNum(rs.getInt(6));
                x.setDistLineNumber(rs.getInt(7));
                x.setDistItemSkuNum(rs.getString(8));
                x.setDistItemShortDesc(rs.getString(9));
                x.setDistItemUom(rs.getString(10));
                x.setDistItemPack(rs.getString(11));
                x.setDistItemQuantity(rs.getInt(12));
                x.setItemSkuNum(rs.getInt(13));
                x.setItemShortDesc(rs.getString(14));
                x.setItemUom(rs.getString(15));
                x.setItemPack(rs.getString(16));
                x.setItemQuantity(rs.getInt(17));
                x.setLineTotal(rs.getBigDecimal(18));
                x.setItemCost(rs.getBigDecimal(19));
                x.setAdjustedCost(rs.getBigDecimal(20));
                x.setAddDate(rs.getTimestamp(21));
                x.setAddBy(rs.getString(22));
                x.setModDate(rs.getTimestamp(23));
                x.setModBy(rs.getString(24));
                x.setItemReceivedCost(rs.getBigDecimal(25));
                x.setDistItemQtyReceived(rs.getInt(26));
                x.setErpAccountCode(rs.getString(27));
                x.setErpPoRefLineNum(rs.getInt(28));
                x.setInvoiceDistSkuNum(rs.getString(29));
                x.setDistIntoStockCost(rs.getBigDecimal(30));
                x.setShipStatusCd(rs.getString(31));
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a InvoiceDistDetailDataVector object of all
     * InvoiceDistDetailData objects in the database.
     * @param pCon An open database connection.
     * @return new InvoiceDistDetailDataVector()
     * @throws            SQLException
     */
    public static InvoiceDistDetailDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT INVOICE_DIST_DETAIL_ID,INVOICE_DIST_ID,INVOICE_CUST_ID,INVOICE_CUST_DETAIL_ID,ORDER_ITEM_ID,ERP_PO_LINE_NUM,DIST_LINE_NUMBER,DIST_ITEM_SKU_NUM,DIST_ITEM_SHORT_DESC,DIST_ITEM_UOM,DIST_ITEM_PACK,DIST_ITEM_QUANTITY,ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_QUANTITY,LINE_TOTAL,ITEM_COST,ADJUSTED_COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ITEM_RECEIVED_COST,DIST_ITEM_QTY_RECEIVED,ERP_ACCOUNT_CODE,ERP_PO_REF_LINE_NUM,INVOICE_DIST_SKU_NUM,DIST_INTO_STOCK_COST,SHIP_STATUS_CD FROM CLW_INVOICE_DIST_DETAIL";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        InvoiceDistDetailDataVector v = new InvoiceDistDetailDataVector();
        InvoiceDistDetailData x = null;
        while (rs.next()) {
            // build the object
            x = InvoiceDistDetailData.createValue();
            
            x.setInvoiceDistDetailId(rs.getInt(1));
            x.setInvoiceDistId(rs.getInt(2));
            x.setInvoiceCustId(rs.getInt(3));
            x.setInvoiceCustDetailId(rs.getInt(4));
            x.setOrderItemId(rs.getInt(5));
            x.setErpPoLineNum(rs.getInt(6));
            x.setDistLineNumber(rs.getInt(7));
            x.setDistItemSkuNum(rs.getString(8));
            x.setDistItemShortDesc(rs.getString(9));
            x.setDistItemUom(rs.getString(10));
            x.setDistItemPack(rs.getString(11));
            x.setDistItemQuantity(rs.getInt(12));
            x.setItemSkuNum(rs.getInt(13));
            x.setItemShortDesc(rs.getString(14));
            x.setItemUom(rs.getString(15));
            x.setItemPack(rs.getString(16));
            x.setItemQuantity(rs.getInt(17));
            x.setLineTotal(rs.getBigDecimal(18));
            x.setItemCost(rs.getBigDecimal(19));
            x.setAdjustedCost(rs.getBigDecimal(20));
            x.setAddDate(rs.getTimestamp(21));
            x.setAddBy(rs.getString(22));
            x.setModDate(rs.getTimestamp(23));
            x.setModBy(rs.getString(24));
            x.setItemReceivedCost(rs.getBigDecimal(25));
            x.setDistItemQtyReceived(rs.getInt(26));
            x.setErpAccountCode(rs.getString(27));
            x.setErpPoRefLineNum(rs.getInt(28));
            x.setInvoiceDistSkuNum(rs.getString(29));
            x.setDistIntoStockCost(rs.getBigDecimal(30));
            x.setShipStatusCd(rs.getString(31));

            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * InvoiceDistDetailData objects in the database.
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
                sqlBuf = new StringBuffer("SELECT DISTINCT INVOICE_DIST_DETAIL_ID FROM CLW_INVOICE_DIST_DETAIL");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT INVOICE_DIST_DETAIL_ID FROM CLW_INVOICE_DIST_DETAIL");
                where = pCriteria.getSqlClause("CLW_INVOICE_DIST_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_INVOICE_DIST_DETAIL.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVOICE_DIST_DETAIL");
                where = pCriteria.getSqlClause();
        } else {
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVOICE_DIST_DETAIL");
                where = pCriteria.getSqlClause("CLW_INVOICE_DIST_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_INVOICE_DIST_DETAIL.equals(otherTable)){
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
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVOICE_DIST_DETAIL");
                where = pCriteria.getSqlClause();
        }else{
                sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVOICE_DIST_DETAIL");
                where = pCriteria.getSqlClause("CLW_INVOICE_DIST_DETAIL");

                Iterator it = otherTables.iterator();
                while(it.hasNext()){
                        String otherTable = (String) it.next();
                        if(!CLW_INVOICE_DIST_DETAIL.equals(otherTable)){
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
     * Inserts a InvoiceDistDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceDistDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @return new InvoiceDistDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InvoiceDistDetailData insert(Connection pCon, InvoiceDistDetailData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
            log.debug("SELECT CLW_INVOICE_DIST_DETAIL_SEQ.NEXTVAL FROM DUAL");
        }
        String exceptionMessage=null;
        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_INVOICE_DIST_DETAIL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setInvoiceDistDetailId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_INVOICE_DIST_DETAIL (INVOICE_DIST_DETAIL_ID,INVOICE_DIST_ID,INVOICE_CUST_ID,INVOICE_CUST_DETAIL_ID,ORDER_ITEM_ID,ERP_PO_LINE_NUM,DIST_LINE_NUMBER,DIST_ITEM_SKU_NUM,DIST_ITEM_SHORT_DESC,DIST_ITEM_UOM,DIST_ITEM_PACK,DIST_ITEM_QUANTITY,ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_QUANTITY,LINE_TOTAL,ITEM_COST,ADJUSTED_COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ITEM_RECEIVED_COST,DIST_ITEM_QTY_RECEIVED,ERP_ACCOUNT_CODE,ERP_PO_REF_LINE_NUM,INVOICE_DIST_SKU_NUM,DIST_INTO_STOCK_COST,SHIP_STATUS_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getInvoiceDistDetailId());
        pstmt.setInt(2,pData.getInvoiceDistId());
        if (pData.getInvoiceCustId() == 0) {
            pstmt.setNull(3, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3,pData.getInvoiceCustId());
        }

        pstmt.setInt(4,pData.getInvoiceCustDetailId());
        pstmt.setInt(5,pData.getOrderItemId());
        pstmt.setInt(6,pData.getErpPoLineNum());
        pstmt.setInt(7,pData.getDistLineNumber());
        pstmt.setString(8,pData.getDistItemSkuNum());
        pstmt.setString(9,pData.getDistItemShortDesc());
        pstmt.setString(10,pData.getDistItemUom());
        pstmt.setString(11,pData.getDistItemPack());
        pstmt.setInt(12,pData.getDistItemQuantity());
        pstmt.setInt(13,pData.getItemSkuNum());
        pstmt.setString(14,pData.getItemShortDesc());
        pstmt.setString(15,pData.getItemUom());
        pstmt.setString(16,pData.getItemPack());
        pstmt.setInt(17,pData.getItemQuantity());
        pstmt.setBigDecimal(18,pData.getLineTotal());
        pstmt.setBigDecimal(19,pData.getItemCost());
        pstmt.setBigDecimal(20,pData.getAdjustedCost());
        pstmt.setTimestamp(21,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(22,pData.getAddBy());
        pstmt.setTimestamp(23,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(24,pData.getModBy());
        pstmt.setBigDecimal(25,pData.getItemReceivedCost());
        pstmt.setInt(26,pData.getDistItemQtyReceived());
        pstmt.setString(27,pData.getErpAccountCode());
        pstmt.setInt(28,pData.getErpPoRefLineNum());
        pstmt.setString(29,pData.getInvoiceDistSkuNum());
        pstmt.setBigDecimal(30,pData.getDistIntoStockCost());
        pstmt.setString(31,pData.getShipStatusCd());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   INVOICE_DIST_DETAIL_ID="+pData.getInvoiceDistDetailId());
            log.debug("SQL:   INVOICE_DIST_ID="+pData.getInvoiceDistId());
            log.debug("SQL:   INVOICE_CUST_ID="+pData.getInvoiceCustId());
            log.debug("SQL:   INVOICE_CUST_DETAIL_ID="+pData.getInvoiceCustDetailId());
            log.debug("SQL:   ORDER_ITEM_ID="+pData.getOrderItemId());
            log.debug("SQL:   ERP_PO_LINE_NUM="+pData.getErpPoLineNum());
            log.debug("SQL:   DIST_LINE_NUMBER="+pData.getDistLineNumber());
            log.debug("SQL:   DIST_ITEM_SKU_NUM="+pData.getDistItemSkuNum());
            log.debug("SQL:   DIST_ITEM_SHORT_DESC="+pData.getDistItemShortDesc());
            log.debug("SQL:   DIST_ITEM_UOM="+pData.getDistItemUom());
            log.debug("SQL:   DIST_ITEM_PACK="+pData.getDistItemPack());
            log.debug("SQL:   DIST_ITEM_QUANTITY="+pData.getDistItemQuantity());
            log.debug("SQL:   ITEM_SKU_NUM="+pData.getItemSkuNum());
            log.debug("SQL:   ITEM_SHORT_DESC="+pData.getItemShortDesc());
            log.debug("SQL:   ITEM_UOM="+pData.getItemUom());
            log.debug("SQL:   ITEM_PACK="+pData.getItemPack());
            log.debug("SQL:   ITEM_QUANTITY="+pData.getItemQuantity());
            log.debug("SQL:   LINE_TOTAL="+pData.getLineTotal());
            log.debug("SQL:   ITEM_COST="+pData.getItemCost());
            log.debug("SQL:   ADJUSTED_COST="+pData.getAdjustedCost());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   ITEM_RECEIVED_COST="+pData.getItemReceivedCost());
            log.debug("SQL:   DIST_ITEM_QTY_RECEIVED="+pData.getDistItemQtyReceived());
            log.debug("SQL:   ERP_ACCOUNT_CODE="+pData.getErpAccountCode());
            log.debug("SQL:   ERP_PO_REF_LINE_NUM="+pData.getErpPoRefLineNum());
            log.debug("SQL:   INVOICE_DIST_SKU_NUM="+pData.getInvoiceDistSkuNum());
            log.debug("SQL:   DIST_INTO_STOCK_COST="+pData.getDistIntoStockCost());
            log.debug("SQL:   SHIP_STATUS_CD="+pData.getShipStatusCd());
            log.debug("SQL: " + sql);
        }
        try {
        pstmt.executeUpdate();
        pData.setDirty(false);
        }
        catch(SQLException e){
        pData.setInvoiceDistDetailId(0);
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
     * Updates a InvoiceDistDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceDistDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InvoiceDistDetailData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_INVOICE_DIST_DETAIL SET INVOICE_DIST_ID = ?,INVOICE_CUST_ID = ?,INVOICE_CUST_DETAIL_ID = ?,ORDER_ITEM_ID = ?,ERP_PO_LINE_NUM = ?,DIST_LINE_NUMBER = ?,DIST_ITEM_SKU_NUM = ?,DIST_ITEM_SHORT_DESC = ?,DIST_ITEM_UOM = ?,DIST_ITEM_PACK = ?,DIST_ITEM_QUANTITY = ?,ITEM_SKU_NUM = ?,ITEM_SHORT_DESC = ?,ITEM_UOM = ?,ITEM_PACK = ?,ITEM_QUANTITY = ?,LINE_TOTAL = ?,ITEM_COST = ?,ADJUSTED_COST = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ?,ITEM_RECEIVED_COST = ?,DIST_ITEM_QTY_RECEIVED = ?,ERP_ACCOUNT_CODE = ?,ERP_PO_REF_LINE_NUM = ?,INVOICE_DIST_SKU_NUM = ?,DIST_INTO_STOCK_COST = ?,SHIP_STATUS_CD = ? WHERE INVOICE_DIST_DETAIL_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getInvoiceDistId());
        if (pData.getInvoiceCustId() == 0) {
            pstmt.setNull(i++, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(i++,pData.getInvoiceCustId());
        }

        pstmt.setInt(i++,pData.getInvoiceCustDetailId());
        pstmt.setInt(i++,pData.getOrderItemId());
        pstmt.setInt(i++,pData.getErpPoLineNum());
        pstmt.setInt(i++,pData.getDistLineNumber());
        pstmt.setString(i++,pData.getDistItemSkuNum());
        pstmt.setString(i++,pData.getDistItemShortDesc());
        pstmt.setString(i++,pData.getDistItemUom());
        pstmt.setString(i++,pData.getDistItemPack());
        pstmt.setInt(i++,pData.getDistItemQuantity());
        pstmt.setInt(i++,pData.getItemSkuNum());
        pstmt.setString(i++,pData.getItemShortDesc());
        pstmt.setString(i++,pData.getItemUom());
        pstmt.setString(i++,pData.getItemPack());
        pstmt.setInt(i++,pData.getItemQuantity());
        pstmt.setBigDecimal(i++,pData.getLineTotal());
        pstmt.setBigDecimal(i++,pData.getItemCost());
        pstmt.setBigDecimal(i++,pData.getAdjustedCost());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setTimestamp(i++,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setBigDecimal(i++,pData.getItemReceivedCost());
        pstmt.setInt(i++,pData.getDistItemQtyReceived());
        pstmt.setString(i++,pData.getErpAccountCode());
        pstmt.setInt(i++,pData.getErpPoRefLineNum());
        pstmt.setString(i++,pData.getInvoiceDistSkuNum());
        pstmt.setBigDecimal(i++,pData.getDistIntoStockCost());
        pstmt.setString(i++,pData.getShipStatusCd());
        pstmt.setInt(i++,pData.getInvoiceDistDetailId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   INVOICE_DIST_ID="+pData.getInvoiceDistId());
            log.debug("SQL:   INVOICE_CUST_ID="+pData.getInvoiceCustId());
            log.debug("SQL:   INVOICE_CUST_DETAIL_ID="+pData.getInvoiceCustDetailId());
            log.debug("SQL:   ORDER_ITEM_ID="+pData.getOrderItemId());
            log.debug("SQL:   ERP_PO_LINE_NUM="+pData.getErpPoLineNum());
            log.debug("SQL:   DIST_LINE_NUMBER="+pData.getDistLineNumber());
            log.debug("SQL:   DIST_ITEM_SKU_NUM="+pData.getDistItemSkuNum());
            log.debug("SQL:   DIST_ITEM_SHORT_DESC="+pData.getDistItemShortDesc());
            log.debug("SQL:   DIST_ITEM_UOM="+pData.getDistItemUom());
            log.debug("SQL:   DIST_ITEM_PACK="+pData.getDistItemPack());
            log.debug("SQL:   DIST_ITEM_QUANTITY="+pData.getDistItemQuantity());
            log.debug("SQL:   ITEM_SKU_NUM="+pData.getItemSkuNum());
            log.debug("SQL:   ITEM_SHORT_DESC="+pData.getItemShortDesc());
            log.debug("SQL:   ITEM_UOM="+pData.getItemUom());
            log.debug("SQL:   ITEM_PACK="+pData.getItemPack());
            log.debug("SQL:   ITEM_QUANTITY="+pData.getItemQuantity());
            log.debug("SQL:   LINE_TOTAL="+pData.getLineTotal());
            log.debug("SQL:   ITEM_COST="+pData.getItemCost());
            log.debug("SQL:   ADJUSTED_COST="+pData.getAdjustedCost());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
            log.debug("SQL:   ITEM_RECEIVED_COST="+pData.getItemReceivedCost());
            log.debug("SQL:   DIST_ITEM_QTY_RECEIVED="+pData.getDistItemQtyReceived());
            log.debug("SQL:   ERP_ACCOUNT_CODE="+pData.getErpAccountCode());
            log.debug("SQL:   ERP_PO_REF_LINE_NUM="+pData.getErpPoRefLineNum());
            log.debug("SQL:   INVOICE_DIST_SKU_NUM="+pData.getInvoiceDistSkuNum());
            log.debug("SQL:   DIST_INTO_STOCK_COST="+pData.getDistIntoStockCost());
            log.debug("SQL:   SHIP_STATUS_CD="+pData.getShipStatusCd());
            log.debug("SQL: " + sql);
        }

        n = pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);

        return n;
    }

    /**
     * Deletes a InvoiceDistDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInvoiceDistDetailId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInvoiceDistDetailId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_INVOICE_DIST_DETAIL WHERE INVOICE_DIST_DETAIL_ID = " + pInvoiceDistDetailId;

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes InvoiceDistDetailData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_INVOICE_DIST_DETAIL");
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
     * Inserts a InvoiceDistDetailData log object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceDistDetailData object to insert
     * @param pMillis current time in milliseconds
     * @param pAction action made (I, U, D)
     * @param pStatus record status (O, N)
     * @throws            SQLException
     */
    public static void insertLog(Connection pCon, InvoiceDistDetailData pData, long pMillis, String pAction, String pStatus)
        throws SQLException {

        String sql = "INSERT INTO LCLW_INVOICE_DIST_DETAIL (LOG_DATE_MS,LOG_DATE,ACTION, STATUS,"+
          "INVOICE_DIST_DETAIL_ID,INVOICE_DIST_ID,INVOICE_CUST_ID,INVOICE_CUST_DETAIL_ID,ORDER_ITEM_ID,ERP_PO_LINE_NUM,DIST_LINE_NUMBER,DIST_ITEM_SKU_NUM,DIST_ITEM_SHORT_DESC,DIST_ITEM_UOM,DIST_ITEM_PACK,DIST_ITEM_QUANTITY,ITEM_SKU_NUM,ITEM_SHORT_DESC,ITEM_UOM,ITEM_PACK,ITEM_QUANTITY,LINE_TOTAL,ITEM_COST,ADJUSTED_COST,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,ITEM_RECEIVED_COST,DIST_ITEM_QTY_RECEIVED,ERP_ACCOUNT_CODE,ERP_PO_REF_LINE_NUM,INVOICE_DIST_SKU_NUM,DIST_INTO_STOCK_COST,SHIP_STATUS_CD) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        pstmt.setLong(1,pMillis);
        pstmt.setTimestamp(2,DBAccess.toSQLTimestamp(new java.util.Date(pMillis)));
        pstmt.setString(3,pAction);
        pstmt.setString(4,pStatus);

        pstmt.setInt(1+4,pData.getInvoiceDistDetailId());
        pstmt.setInt(2+4,pData.getInvoiceDistId());
        if (pData.getInvoiceCustId() == 0) {
            pstmt.setNull(3+4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(3+4,pData.getInvoiceCustId());
        }

        pstmt.setInt(4+4,pData.getInvoiceCustDetailId());
        pstmt.setInt(5+4,pData.getOrderItemId());
        pstmt.setInt(6+4,pData.getErpPoLineNum());
        pstmt.setInt(7+4,pData.getDistLineNumber());
        pstmt.setString(8+4,pData.getDistItemSkuNum());
        pstmt.setString(9+4,pData.getDistItemShortDesc());
        pstmt.setString(10+4,pData.getDistItemUom());
        pstmt.setString(11+4,pData.getDistItemPack());
        pstmt.setInt(12+4,pData.getDistItemQuantity());
        pstmt.setInt(13+4,pData.getItemSkuNum());
        pstmt.setString(14+4,pData.getItemShortDesc());
        pstmt.setString(15+4,pData.getItemUom());
        pstmt.setString(16+4,pData.getItemPack());
        pstmt.setInt(17+4,pData.getItemQuantity());
        pstmt.setBigDecimal(18+4,pData.getLineTotal());
        pstmt.setBigDecimal(19+4,pData.getItemCost());
        pstmt.setBigDecimal(20+4,pData.getAdjustedCost());
        pstmt.setTimestamp(21+4,DBAccess.toSQLTimestamp(pData.getAddDate()));
        pstmt.setString(22+4,pData.getAddBy());
        pstmt.setTimestamp(23+4,DBAccess.toSQLTimestamp(pData.getModDate()));
        pstmt.setString(24+4,pData.getModBy());
        pstmt.setBigDecimal(25+4,pData.getItemReceivedCost());
        pstmt.setInt(26+4,pData.getDistItemQtyReceived());
        pstmt.setString(27+4,pData.getErpAccountCode());
        pstmt.setInt(28+4,pData.getErpPoRefLineNum());
        pstmt.setString(29+4,pData.getInvoiceDistSkuNum());
        pstmt.setBigDecimal(30+4,pData.getDistIntoStockCost());
        pstmt.setString(31+4,pData.getShipStatusCd());


        pstmt.executeUpdate();
        pstmt.close();
    }




///////////////////////////////////////////////
    /**
     * Inserts a InvoiceDistDetailData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceDistDetailData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and
     * "ModDate" fields will be set to the current date. 
     * @param pLogFl  Creates record in log table if true
     * @return new InvoiceDistDetailData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InvoiceDistDetailData insert(Connection pCon, InvoiceDistDetailData pData, boolean pLogFl)
        throws SQLException {
        pData = insert(pCon, pData);

        if(pLogFl) {
          long millis = System.currentTimeMillis();
          insertLog(pCon, pData, millis, "I", "N");
        }

        return pData;
    }

    /**
     * Updates a InvoiceDistDetailData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceDistDetailData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @param pLogFl  Creates record in log table if true
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InvoiceDistDetailData pData, boolean pLogFl)
        throws SQLException {
        InvoiceDistDetailData oldData = null;
        if(pLogFl) {
          int id = pData.getInvoiceDistDetailId();
          try {
          oldData = InvoiceDistDetailDataAccess.select(pCon,id);
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
     * Deletes a InvoiceDistDetailData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInvoiceDistDetailId Key of object to be deleted.
     * @param pLogFl  Create record in log table if true
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInvoiceDistDetailId, boolean pLogFl)
        throws SQLException{
        if(pLogFl){
          long millis = System.currentTimeMillis();
          java.util.Date rmDate = new java.util.Date(millis);
          java.text.SimpleDateFormat smf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String oracleDate = "to_date('"+smf.format(rmDate)+"','YYYY-MM-DD HH24:MI:SS')";
          String sqlLog ="INSERT INTO LCLW_INVOICE_DIST_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INVOICE_DIST_DETAIL d WHERE INVOICE_DIST_DETAIL_ID = " + pInvoiceDistDetailId;
          Statement stmt = pCon.createStatement();
          stmt.executeUpdate(sqlLog);
           stmt.close();
        }
        int n = remove(pCon,pInvoiceDistDetailId);
        return n;
     }

    /**
     * Deletes InvoiceDistDetailData objects that match the specified criteria.
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
             new StringBuffer("INSERT INTO LCLW_INVOICE_DIST_DETAIL SELECT "+millis+ ","+oracleDate+",'D','O',d.* FROM CLW_INVOICE_DIST_DETAIL d ");
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

