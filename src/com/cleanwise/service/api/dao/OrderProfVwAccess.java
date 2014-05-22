
/* DO NOT EDIT - Generated code from XSL file VwDataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        OrderProfVwAccess
 * Description:  This class is used to build access methods to the CLV_ORDER_PROF table.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file VwAccess.xsl
 */

import com.cleanwise.service.api.value.OrderProfVw;
import com.cleanwise.service.api.value.OrderProfVwVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.sql.*;
import java.util.*;

/**
 * <code>OrderProfVwAccess</code>
 */
public class OrderProfVwAccess
{
    private static Category log = Category.getInstance(OrderProfVwAccess.class.getName());

    /** <code>CLV_ORDER_PROF</code> table name */
    public static final String CLV_ORDER_PROF = "CLV_ORDER_PROF";
    
    /** <code>CUSTOMER</code> CUSTOMER column of table CLV_ORDER_PROF */
    public static final String CUSTOMER = "CUSTOMER";
    /** <code>CUST_NAME</code> CUST_NAME column of table CLV_ORDER_PROF */
    public static final String CUST_NAME = "CUST_NAME";
    /** <code>ORDER_NBR</code> ORDER_NBR column of table CLV_ORDER_PROF */
    public static final String ORDER_NBR = "ORDER_NBR";
    /** <code>ORDER_DATE</code> ORDER_DATE column of table CLV_ORDER_PROF */
    public static final String ORDER_DATE = "ORDER_DATE";
    /** <code>CUST_INVOICE_PREFIX</code> CUST_INVOICE_PREFIX column of table CLV_ORDER_PROF */
    public static final String CUST_INVOICE_PREFIX = "CUST_INVOICE_PREFIX";
    /** <code>CUST_INVOICE_NUM</code> CUST_INVOICE_NUM column of table CLV_ORDER_PROF */
    public static final String CUST_INVOICE_NUM = "CUST_INVOICE_NUM";
    /** <code>CUST_INVOICE_DATE</code> CUST_INVOICE_DATE column of table CLV_ORDER_PROF */
    public static final String CUST_INVOICE_DATE = "CUST_INVOICE_DATE";
    /** <code>CUST_TOTAL_PRICE</code> CUST_TOTAL_PRICE column of table CLV_ORDER_PROF */
    public static final String CUST_TOTAL_PRICE = "CUST_TOTAL_PRICE";
    /** <code>CUST_GOODS</code> CUST_GOODS column of table CLV_ORDER_PROF */
    public static final String CUST_GOODS = "CUST_GOODS";
    /** <code>CUST_MISC</code> CUST_MISC column of table CLV_ORDER_PROF */
    public static final String CUST_MISC = "CUST_MISC";
    /** <code>CUST_TAX</code> CUST_TAX column of table CLV_ORDER_PROF */
    public static final String CUST_TAX = "CUST_TAX";
    /** <code>APPR_VENDOR</code> APPR_VENDOR column of table CLV_ORDER_PROF */
    public static final String APPR_VENDOR = "APPR_VENDOR";
    /** <code>VENDOR_NAME</code> VENDOR_NAME column of table CLV_ORDER_PROF */
    public static final String VENDOR_NAME = "VENDOR_NAME";
    /** <code>APPR_PO_NUMBER</code> APPR_PO_NUMBER column of table CLV_ORDER_PROF */
    public static final String APPR_PO_NUMBER = "APPR_PO_NUMBER";
    /** <code>VEN_INVOICE_NUM</code> VEN_INVOICE_NUM column of table CLV_ORDER_PROF */
    public static final String VEN_INVOICE_NUM = "VEN_INVOICE_NUM";
    /** <code>VEN_INVOICE_DATE</code> VEN_INVOICE_DATE column of table CLV_ORDER_PROF */
    public static final String VEN_INVOICE_DATE = "VEN_INVOICE_DATE";
    /** <code>VEN_TOTAL_COST</code> VEN_TOTAL_COST column of table CLV_ORDER_PROF */
    public static final String VEN_TOTAL_COST = "VEN_TOTAL_COST";
    /** <code>VEN_GOODS_COST</code> VEN_GOODS_COST column of table CLV_ORDER_PROF */
    public static final String VEN_GOODS_COST = "VEN_GOODS_COST";
    /** <code>VEN_ADDITIONAL_CHARGES</code> VEN_ADDITIONAL_CHARGES column of table CLV_ORDER_PROF */
    public static final String VEN_ADDITIONAL_CHARGES = "VEN_ADDITIONAL_CHARGES";
    /** <code>VEN_TAX</code> VEN_TAX column of table CLV_ORDER_PROF */
    public static final String VEN_TAX = "VEN_TAX";

    /**
     * Constructor.
     */
    public OrderProfVwAccess() 
    {
    }

    /**
     * Gets a OrderProfVwVector object that consists
     * of OrderProfVw objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new OrderProfVwVector()
     * @throws            SQLException
     */
    public static OrderProfVwVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
     * Gets a OrderProfVwVector object that consists
     * of OrderProfVw objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new OrderProfVwVector()
     * @throws            SQLException
     */
    public static OrderProfVwVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT CUSTOMER,CUST_NAME,ORDER_NBR,ORDER_DATE,CUST_INVOICE_PREFIX,CUST_INVOICE_NUM,CUST_INVOICE_DATE,CUST_TOTAL_PRICE,CUST_GOODS,CUST_MISC,CUST_TAX,APPR_VENDOR,VENDOR_NAME,APPR_PO_NUMBER,VEN_INVOICE_NUM,VEN_INVOICE_DATE,VEN_TOTAL_COST,VEN_GOODS_COST,VEN_ADDITIONAL_CHARGES,VEN_TAX FROM CLV_ORDER_PROF");
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
        OrderProfVwVector v = new OrderProfVwVector();
        OrderProfVw x=null;
        while (rs.next()) {
            // build the object
            x = OrderProfVw.createValue();
            
            x.setCustomer(rs.getString(1));
            x.setCustName(rs.getString(2));
            x.setOrderNbr(rs.getBigDecimal(3));
            x.setOrderDate(rs.getDate(4));
            x.setCustInvoicePrefix(rs.getString(5));
            x.setCustInvoiceNum(rs.getBigDecimal(6));
            x.setCustInvoiceDate(rs.getDate(7));
            x.setCustTotalPrice(rs.getBigDecimal(8));
            x.setCustGoods(rs.getBigDecimal(9));
            x.setCustMisc(rs.getBigDecimal(10));
            x.setCustTax(rs.getBigDecimal(11));
            x.setApprVendor(rs.getString(12));
            x.setVendorName(rs.getString(13));
            x.setApprPoNumber(rs.getString(14));
            x.setVenInvoiceNum(rs.getString(15));
            x.setVenInvoiceDate(rs.getDate(16));
            x.setVenTotalCost(rs.getBigDecimal(17));
            x.setVenGoodsCost(rs.getBigDecimal(18));
            x.setVenAdditionalCharges(rs.getBigDecimal(19));
            x.setVenTax(rs.getBigDecimal(20));
            
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a OrderProfVwVector object that consists
     * of OrderProfVw objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for OrderProfVw
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new OrderProfVwVector()
     * @throws            SQLException
     */
    public static OrderProfVwVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        OrderProfVwVector v = new OrderProfVwVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT CUSTOMER,CUST_NAME,ORDER_NBR,ORDER_DATE,CUST_INVOICE_PREFIX,CUST_INVOICE_NUM,CUST_INVOICE_DATE,CUST_TOTAL_PRICE,CUST_GOODS,CUST_MISC,CUST_TAX,APPR_VENDOR,VENDOR_NAME,APPR_PO_NUMBER,VEN_INVOICE_NUM,VEN_INVOICE_DATE,VEN_TOTAL_COST,VEN_GOODS_COST,VEN_ADDITIONAL_CHARGES,VEN_TAX FROM CLV_ORDER_PROF WHERE  IN (");
        
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
            OrderProfVw x=null;
            while (rs.next()) {
                // build the object
                x=OrderProfVw.createValue();
                
                x.setCustomer(rs.getString(1));
                x.setCustName(rs.getString(2));
                x.setOrderNbr(rs.getBigDecimal(3));
                x.setOrderDate(rs.getDate(4));
                x.setCustInvoicePrefix(rs.getString(5));
                x.setCustInvoiceNum(rs.getBigDecimal(6));
                x.setCustInvoiceDate(rs.getDate(7));
                x.setCustTotalPrice(rs.getBigDecimal(8));
                x.setCustGoods(rs.getBigDecimal(9));
                x.setCustMisc(rs.getBigDecimal(10));
                x.setCustTax(rs.getBigDecimal(11));
                x.setApprVendor(rs.getString(12));
                x.setVendorName(rs.getString(13));
                x.setApprPoNumber(rs.getString(14));
                x.setVenInvoiceNum(rs.getString(15));
                x.setVenInvoiceDate(rs.getDate(16));
                x.setVenTotalCost(rs.getBigDecimal(17));
                x.setVenGoodsCost(rs.getBigDecimal(18));
                x.setVenAdditionalCharges(rs.getBigDecimal(19));
                x.setVenTax(rs.getBigDecimal(20));
                
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a OrderProfVwVector object of all
     * OrderProfVw objects in the database.
     * @param pCon An open database connection.
     * @return new OrderProfVwVector()
     * @throws            SQLException
     */
    public static OrderProfVwVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT CUSTOMER,CUST_NAME,ORDER_NBR,ORDER_DATE,CUST_INVOICE_PREFIX,CUST_INVOICE_NUM,CUST_INVOICE_DATE,CUST_TOTAL_PRICE,CUST_GOODS,CUST_MISC,CUST_TAX,APPR_VENDOR,VENDOR_NAME,APPR_PO_NUMBER,VEN_INVOICE_NUM,VEN_INVOICE_DATE,VEN_TOTAL_COST,VEN_GOODS_COST,VEN_ADDITIONAL_CHARGES,VEN_TAX FROM CLV_ORDER_PROF";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        OrderProfVwVector v = new OrderProfVwVector();
        OrderProfVw x = null;
        while (rs.next()) {
            // build the object
            x = OrderProfVw.createValue();
            
            x.setCustomer(rs.getString(1));
            x.setCustName(rs.getString(2));
            x.setOrderNbr(rs.getBigDecimal(3));
            x.setOrderDate(rs.getDate(4));
            x.setCustInvoicePrefix(rs.getString(5));
            x.setCustInvoiceNum(rs.getBigDecimal(6));
            x.setCustInvoiceDate(rs.getDate(7));
            x.setCustTotalPrice(rs.getBigDecimal(8));
            x.setCustGoods(rs.getBigDecimal(9));
            x.setCustMisc(rs.getBigDecimal(10));
            x.setCustTax(rs.getBigDecimal(11));
            x.setApprVendor(rs.getString(12));
            x.setVendorName(rs.getString(13));
            x.setApprPoNumber(rs.getString(14));
            x.setVenInvoiceNum(rs.getString(15));
            x.setVenInvoiceDate(rs.getDate(16));
            x.setVenTotalCost(rs.getBigDecimal(17));
            x.setVenGoodsCost(rs.getBigDecimal(18));
            x.setVenAdditionalCharges(rs.getBigDecimal(19));
            x.setVenTax(rs.getBigDecimal(20));
            
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLV_ORDER_PROF");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLV_ORDER_PROF");
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
}
