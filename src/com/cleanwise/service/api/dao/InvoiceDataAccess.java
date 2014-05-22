
/* DO NOT EDIT - Generated code from XSL file DataAccess.xsl */

package com.cleanwise.service.api.dao;

/**
 * Title:        InvoiceDataAccess
 * Description:  This class is used to build access methods to the CLW_INVOICE table.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file DataAccess.xsl
 */

import com.cleanwise.service.api.value.InvoiceData;
import com.cleanwise.service.api.value.InvoiceDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import org.apache.log4j.Category;
import java.sql.*;
import java.util.*;

/**
 * <code>InvoiceDataAccess</code>
 */
public class InvoiceDataAccess
{
    private static Category log = Category.getInstance(InvoiceDataAccess.class.getName());

    /** <code>CLW_INVOICE</code> table name */
    public static final String CLW_INVOICE = "CLW_INVOICE";
    
    /** <code>INVOICE_ID</code> INVOICE_ID column of table CLW_INVOICE */
    public static final String INVOICE_ID = "INVOICE_ID";
    /** <code>ORDER_ID</code> ORDER_ID column of table CLW_INVOICE */
    public static final String ORDER_ID = "ORDER_ID";
    /** <code>SHIPPING_HISTORY_ID</code> SHIPPING_HISTORY_ID column of table CLW_INVOICE */
    public static final String SHIPPING_HISTORY_ID = "SHIPPING_HISTORY_ID";
    /** <code>INVOICE_NUM</code> INVOICE_NUM column of table CLW_INVOICE */
    public static final String INVOICE_NUM = "INVOICE_NUM";
    /** <code>INVOICE_DATE</code> INVOICE_DATE column of table CLW_INVOICE */
    public static final String INVOICE_DATE = "INVOICE_DATE";
    /** <code>ERP_PO_NUM</code> ERP_PO_NUM column of table CLW_INVOICE */
    public static final String ERP_PO_NUM = "ERP_PO_NUM";
    /** <code>ERP_ORDER_NUM</code> ERP_ORDER_NUM column of table CLW_INVOICE */
    public static final String ERP_ORDER_NUM = "ERP_ORDER_NUM";
    /** <code>REF_ORDER_NUM</code> REF_ORDER_NUM column of table CLW_INVOICE */
    public static final String REF_ORDER_NUM = "REF_ORDER_NUM";
    /** <code>DISTRIBUTOR_SHIPMENT_ID</code> DISTRIBUTOR_SHIPMENT_ID column of table CLW_INVOICE */
    public static final String DISTRIBUTOR_SHIPMENT_ID = "DISTRIBUTOR_SHIPMENT_ID";
    /** <code>BILL_TO_NAME</code> BILL_TO_NAME column of table CLW_INVOICE */
    public static final String BILL_TO_NAME = "BILL_TO_NAME";
    /** <code>BILL_TO_ADDRESS_1</code> BILL_TO_ADDRESS_1 column of table CLW_INVOICE */
    public static final String BILL_TO_ADDRESS_1 = "BILL_TO_ADDRESS_1";
    /** <code>BILL_TO_ADDRESS_2</code> BILL_TO_ADDRESS_2 column of table CLW_INVOICE */
    public static final String BILL_TO_ADDRESS_2 = "BILL_TO_ADDRESS_2";
    /** <code>BILL_TO_ADDRESS_3</code> BILL_TO_ADDRESS_3 column of table CLW_INVOICE */
    public static final String BILL_TO_ADDRESS_3 = "BILL_TO_ADDRESS_3";
    /** <code>BILL_TO_ADDRESS_4</code> BILL_TO_ADDRESS_4 column of table CLW_INVOICE */
    public static final String BILL_TO_ADDRESS_4 = "BILL_TO_ADDRESS_4";
    /** <code>BILL_TO_CITY</code> BILL_TO_CITY column of table CLW_INVOICE */
    public static final String BILL_TO_CITY = "BILL_TO_CITY";
    /** <code>BILL_TO_STATE</code> BILL_TO_STATE column of table CLW_INVOICE */
    public static final String BILL_TO_STATE = "BILL_TO_STATE";
    /** <code>BILL_TO_POSTAL_CODE</code> BILL_TO_POSTAL_CODE column of table CLW_INVOICE */
    public static final String BILL_TO_POSTAL_CODE = "BILL_TO_POSTAL_CODE";
    /** <code>BILL_TO_COUNTRY</code> BILL_TO_COUNTRY column of table CLW_INVOICE */
    public static final String BILL_TO_COUNTRY = "BILL_TO_COUNTRY";
    /** <code>GROSS_AMOUNT</code> GROSS_AMOUNT column of table CLW_INVOICE */
    public static final String GROSS_AMOUNT = "GROSS_AMOUNT";
    /** <code>FREIGHT_AMOUNT</code> FREIGHT_AMOUNT column of table CLW_INVOICE */
    public static final String FREIGHT_AMOUNT = "FREIGHT_AMOUNT";
    /** <code>TAX_AMOUNT</code> TAX_AMOUNT column of table CLW_INVOICE */
    public static final String TAX_AMOUNT = "TAX_AMOUNT";
    /** <code>OTHER_AMOUNT</code> OTHER_AMOUNT column of table CLW_INVOICE */
    public static final String OTHER_AMOUNT = "OTHER_AMOUNT";
    /** <code>TOTAL_AMOUNT</code> TOTAL_AMOUNT column of table CLW_INVOICE */
    public static final String TOTAL_AMOUNT = "TOTAL_AMOUNT";
    /** <code>ADD_DATE</code> ADD_DATE column of table CLW_INVOICE */
    public static final String ADD_DATE = "ADD_DATE";
    /** <code>ADD_BY</code> ADD_BY column of table CLW_INVOICE */
    public static final String ADD_BY = "ADD_BY";
    /** <code>MOD_DATE</code> MOD_DATE column of table CLW_INVOICE */
    public static final String MOD_DATE = "MOD_DATE";
    /** <code>MOD_BY</code> MOD_BY column of table CLW_INVOICE */
    public static final String MOD_BY = "MOD_BY";

    /**
     * Constructor.
     */
    public InvoiceDataAccess() 
    {
    }

    /**
     * Gets a InvoiceData object with the specified
     * key from the database.
     * @param pCon An open database connection.
     * @param pInvoiceId The key requested.
     * @return new InvoiceData()
     * @throws            SQLException
     */
    public static InvoiceData select(Connection pCon, int pInvoiceId)
        throws SQLException, DataNotFoundException {
        InvoiceData x=null;
        String sql="SELECT INVOICE_ID,ORDER_ID,SHIPPING_HISTORY_ID,INVOICE_NUM,INVOICE_DATE,ERP_PO_NUM,ERP_ORDER_NUM,REF_ORDER_NUM,DISTRIBUTOR_SHIPMENT_ID,BILL_TO_NAME,BILL_TO_ADDRESS_1,BILL_TO_ADDRESS_2,BILL_TO_ADDRESS_3,BILL_TO_ADDRESS_4,BILL_TO_CITY,BILL_TO_STATE,BILL_TO_POSTAL_CODE,BILL_TO_COUNTRY,GROSS_AMOUNT,FREIGHT_AMOUNT,TAX_AMOUNT,OTHER_AMOUNT,TOTAL_AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_INVOICE WHERE INVOICE_ID = ?";

        if (log.isDebugEnabled()) {
            log.debug("SQL:   INVOICE_ID="+pInvoiceId);
            log.debug("SQL: " + sql);
        }

        PreparedStatement stmt = pCon.prepareStatement(sql);
        stmt.setInt(1,pInvoiceId);
        ResultSet rs=stmt.executeQuery();
        if (rs.next()) {
            // build the object
            x=InvoiceData.createValue();
            
            x.setInvoiceId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setShippingHistoryId(rs.getInt(3));
            x.setInvoiceNum(rs.getString(4));
            x.setInvoiceDate(rs.getDate(5));
            x.setErpPoNum(rs.getString(6));
            x.setErpOrderNum(rs.getString(7));
            x.setRefOrderNum(rs.getString(8));
            x.setDistributorShipmentId(rs.getString(9));
            x.setBillToName(rs.getString(10));
            x.setBillToAddress1(rs.getString(11));
            x.setBillToAddress2(rs.getString(12));
            x.setBillToAddress3(rs.getString(13));
            x.setBillToAddress4(rs.getString(14));
            x.setBillToCity(rs.getString(15));
            x.setBillToState(rs.getString(16));
            x.setBillToPostalCode(rs.getString(17));
            x.setBillToCountry(rs.getString(18));
            x.setGrossAmount(rs.getBigDecimal(19));
            x.setFreightAmount(rs.getBigDecimal(20));
            x.setTaxAmount(rs.getBigDecimal(21));
            x.setOtherAmount(rs.getBigDecimal(22));
            x.setTotalAmount(rs.getBigDecimal(23));
            x.setAddDate(rs.getDate(24));
            x.setAddBy(rs.getString(25));
            x.setModDate(rs.getDate(26));
            x.setModBy(rs.getString(27));
            
        } else {
	    rs.close();
	    stmt.close();
	    throw new DataNotFoundException("INVOICE_ID :" + pInvoiceId);
        }

        rs.close();
        stmt.close();

        return x;
    }

    /**
     * Gets a InvoiceDataVector object that consists
     * of InvoiceData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new InvoiceDataVector()
     * @throws            SQLException
     */
    public static InvoiceDataVector select(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        // Specify the maximum row limit to zero, unlimited size.
        return select(pCon, pCriteria, 0);
    }

    /**
     * Gets a InvoiceDataVector object that consists
     * of InvoiceData objects that match the supplied
     * criteria.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @param pMaxRows the maximum number of rows to return.
     * @return new InvoiceDataVector()
     * @throws            SQLException
     */
    public static InvoiceDataVector select(Connection pCon, DBCriteria pCriteria, int pMaxRows)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT INVOICE_ID,ORDER_ID,SHIPPING_HISTORY_ID,INVOICE_NUM,INVOICE_DATE,ERP_PO_NUM,ERP_ORDER_NUM,REF_ORDER_NUM,DISTRIBUTOR_SHIPMENT_ID,BILL_TO_NAME,BILL_TO_ADDRESS_1,BILL_TO_ADDRESS_2,BILL_TO_ADDRESS_3,BILL_TO_ADDRESS_4,BILL_TO_CITY,BILL_TO_STATE,BILL_TO_POSTAL_CODE,BILL_TO_COUNTRY,GROSS_AMOUNT,FREIGHT_AMOUNT,TAX_AMOUNT,OTHER_AMOUNT,TOTAL_AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_INVOICE");
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
        InvoiceDataVector v = new InvoiceDataVector();
        InvoiceData x=null;
        while (rs.next()) {
            // build the object
            x = InvoiceData.createValue();
            
            x.setInvoiceId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setShippingHistoryId(rs.getInt(3));
            x.setInvoiceNum(rs.getString(4));
            x.setInvoiceDate(rs.getDate(5));
            x.setErpPoNum(rs.getString(6));
            x.setErpOrderNum(rs.getString(7));
            x.setRefOrderNum(rs.getString(8));
            x.setDistributorShipmentId(rs.getString(9));
            x.setBillToName(rs.getString(10));
            x.setBillToAddress1(rs.getString(11));
            x.setBillToAddress2(rs.getString(12));
            x.setBillToAddress3(rs.getString(13));
            x.setBillToAddress4(rs.getString(14));
            x.setBillToCity(rs.getString(15));
            x.setBillToState(rs.getString(16));
            x.setBillToPostalCode(rs.getString(17));
            x.setBillToCountry(rs.getString(18));
            x.setGrossAmount(rs.getBigDecimal(19));
            x.setFreightAmount(rs.getBigDecimal(20));
            x.setTaxAmount(rs.getBigDecimal(21));
            x.setOtherAmount(rs.getBigDecimal(22));
            x.setTotalAmount(rs.getBigDecimal(23));
            x.setAddDate(rs.getDate(24));
            x.setAddBy(rs.getString(25));
            x.setModDate(rs.getDate(26));
            x.setModBy(rs.getString(27));
            
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets a InvoiceDataVector object that consists
     * of InvoiceData objects with the keys requested.
     * @param pCon An open database connection.
     * @param pIdVector A collection of keys for InvoiceData
     * to retrieve from the database (i.e. select 'IN' pIdVector)
     * @return new InvoiceDataVector()
     * @throws            SQLException
     */
    public static InvoiceDataVector select(Connection pCon, IdVector pIdVector)
        throws SQLException{
        InvoiceDataVector v = new InvoiceDataVector();
        StringBuffer sqlBuf = new StringBuffer("SELECT INVOICE_ID,ORDER_ID,SHIPPING_HISTORY_ID,INVOICE_NUM,INVOICE_DATE,ERP_PO_NUM,ERP_ORDER_NUM,REF_ORDER_NUM,DISTRIBUTOR_SHIPMENT_ID,BILL_TO_NAME,BILL_TO_ADDRESS_1,BILL_TO_ADDRESS_2,BILL_TO_ADDRESS_3,BILL_TO_ADDRESS_4,BILL_TO_CITY,BILL_TO_STATE,BILL_TO_POSTAL_CODE,BILL_TO_COUNTRY,GROSS_AMOUNT,FREIGHT_AMOUNT,TAX_AMOUNT,OTHER_AMOUNT,TOTAL_AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_INVOICE WHERE INVOICE_ID IN (");
        
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
            InvoiceData x=null;
            while (rs.next()) {
                // build the object
                x=InvoiceData.createValue();
                
                x.setInvoiceId(rs.getInt(1));
                x.setOrderId(rs.getInt(2));
                x.setShippingHistoryId(rs.getInt(3));
                x.setInvoiceNum(rs.getString(4));
                x.setInvoiceDate(rs.getDate(5));
                x.setErpPoNum(rs.getString(6));
                x.setErpOrderNum(rs.getString(7));
                x.setRefOrderNum(rs.getString(8));
                x.setDistributorShipmentId(rs.getString(9));
                x.setBillToName(rs.getString(10));
                x.setBillToAddress1(rs.getString(11));
                x.setBillToAddress2(rs.getString(12));
                x.setBillToAddress3(rs.getString(13));
                x.setBillToAddress4(rs.getString(14));
                x.setBillToCity(rs.getString(15));
                x.setBillToState(rs.getString(16));
                x.setBillToPostalCode(rs.getString(17));
                x.setBillToCountry(rs.getString(18));
                x.setGrossAmount(rs.getBigDecimal(19));
                x.setFreightAmount(rs.getBigDecimal(20));
                x.setTaxAmount(rs.getBigDecimal(21));
                x.setOtherAmount(rs.getBigDecimal(22));
                x.setTotalAmount(rs.getBigDecimal(23));
                x.setAddDate(rs.getDate(24));
                x.setAddBy(rs.getString(25));
                x.setModDate(rs.getDate(26));
                x.setModBy(rs.getString(27));
                
                v.add(x);
            }

            rs.close();
            stmt.close();
        }

        return v;
    }

    /**
     * Gets a InvoiceDataVector object of all
     * InvoiceData objects in the database.
     * @param pCon An open database connection.
     * @return new InvoiceDataVector()
     * @throws            SQLException
     */
    public static InvoiceDataVector selectAll(Connection pCon)
        throws SQLException{
        String sql = "SELECT INVOICE_ID,ORDER_ID,SHIPPING_HISTORY_ID,INVOICE_NUM,INVOICE_DATE,ERP_PO_NUM,ERP_ORDER_NUM,REF_ORDER_NUM,DISTRIBUTOR_SHIPMENT_ID,BILL_TO_NAME,BILL_TO_ADDRESS_1,BILL_TO_ADDRESS_2,BILL_TO_ADDRESS_3,BILL_TO_ADDRESS_4,BILL_TO_CITY,BILL_TO_STATE,BILL_TO_POSTAL_CODE,BILL_TO_COUNTRY,GROSS_AMOUNT,FREIGHT_AMOUNT,TAX_AMOUNT,OTHER_AMOUNT,TOTAL_AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM CLW_INVOICE";

        if (log.isDebugEnabled()) {
            log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        InvoiceDataVector v = new InvoiceDataVector();
        InvoiceData x = null;
        while (rs.next()) {
            // build the object
            x = InvoiceData.createValue();
            
            x.setInvoiceId(rs.getInt(1));
            x.setOrderId(rs.getInt(2));
            x.setShippingHistoryId(rs.getInt(3));
            x.setInvoiceNum(rs.getString(4));
            x.setInvoiceDate(rs.getDate(5));
            x.setErpPoNum(rs.getString(6));
            x.setErpOrderNum(rs.getString(7));
            x.setRefOrderNum(rs.getString(8));
            x.setDistributorShipmentId(rs.getString(9));
            x.setBillToName(rs.getString(10));
            x.setBillToAddress1(rs.getString(11));
            x.setBillToAddress2(rs.getString(12));
            x.setBillToAddress3(rs.getString(13));
            x.setBillToAddress4(rs.getString(14));
            x.setBillToCity(rs.getString(15));
            x.setBillToState(rs.getString(16));
            x.setBillToPostalCode(rs.getString(17));
            x.setBillToCountry(rs.getString(18));
            x.setGrossAmount(rs.getBigDecimal(19));
            x.setFreightAmount(rs.getBigDecimal(20));
            x.setTaxAmount(rs.getBigDecimal(21));
            x.setOtherAmount(rs.getBigDecimal(22));
            x.setTotalAmount(rs.getBigDecimal(23));
            x.setAddDate(rs.getDate(24));
            x.setAddBy(rs.getString(25));
            x.setModDate(rs.getDate(26));
            x.setModBy(rs.getString(27));
            
            v.add(x);
        }

        rs.close();
        stmt.close();

        return v;
    }

    /**
     * Gets an IdVector of Integers representing the Ids of the matching
     * InvoiceData objects in the database.
     * @param pCon An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return new IdVector()
     * @throws            SQLException
     */
    public static IdVector selectIdOnly(Connection pCon, DBCriteria pCriteria)
        throws SQLException{
        StringBuffer sqlBuf = new StringBuffer("SELECT INVOICE_ID FROM CLW_INVOICE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVOICE");
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
        StringBuffer sqlBuf = new StringBuffer("SELECT DISTINCT "+pIdName+" FROM CLW_INVOICE");
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
     * Inserts a InvoiceData object into the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceData object to insert.
     * The object id will be set to the one generated.  The "AddDate" and 
     * "ModDate" fields will be set to the current date. 
     * @return new InvoiceData() with the generated
     *         key set
     * @throws            SQLException
     */
    public static InvoiceData insert(Connection pCon, InvoiceData pData)
        throws SQLException {

        if (log.isDebugEnabled()) {
	    log.debug("SELECT CLW_INVOICE_SEQ.NEXTVAL FROM DUAL");
        }

        Statement stmt = pCon.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT CLW_INVOICE_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        pData.setInvoiceId(rs.getInt(1));
        stmt.close();

        String sql = "INSERT INTO CLW_INVOICE (INVOICE_ID,ORDER_ID,SHIPPING_HISTORY_ID,INVOICE_NUM,INVOICE_DATE,ERP_PO_NUM,ERP_ORDER_NUM,REF_ORDER_NUM,DISTRIBUTOR_SHIPMENT_ID,BILL_TO_NAME,BILL_TO_ADDRESS_1,BILL_TO_ADDRESS_2,BILL_TO_ADDRESS_3,BILL_TO_ADDRESS_4,BILL_TO_CITY,BILL_TO_STATE,BILL_TO_POSTAL_CODE,BILL_TO_COUNTRY,GROSS_AMOUNT,FREIGHT_AMOUNT,TAX_AMOUNT,OTHER_AMOUNT,TOTAL_AMOUNT,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        pData.setAddDate(current);
        pData.setModDate(current);

        pstmt.setInt(1,pData.getInvoiceId());
        pstmt.setInt(2,pData.getOrderId());
        pstmt.setInt(3,pData.getShippingHistoryId());
        pstmt.setString(4,pData.getInvoiceNum());
        pstmt.setDate(5,DBAccess.toSQLDate(pData.getInvoiceDate()));
        pstmt.setString(6,pData.getErpPoNum());
        pstmt.setString(7,pData.getErpOrderNum());
        pstmt.setString(8,pData.getRefOrderNum());
        pstmt.setString(9,pData.getDistributorShipmentId());
        pstmt.setString(10,pData.getBillToName());
        pstmt.setString(11,pData.getBillToAddress1());
        pstmt.setString(12,pData.getBillToAddress2());
        pstmt.setString(13,pData.getBillToAddress3());
        pstmt.setString(14,pData.getBillToAddress4());
        pstmt.setString(15,pData.getBillToCity());
        pstmt.setString(16,pData.getBillToState());
        pstmt.setString(17,pData.getBillToPostalCode());
        pstmt.setString(18,pData.getBillToCountry());
        pstmt.setBigDecimal(19,pData.getGrossAmount());
        pstmt.setBigDecimal(20,pData.getFreightAmount());
        pstmt.setBigDecimal(21,pData.getTaxAmount());
        pstmt.setBigDecimal(22,pData.getOtherAmount());
        pstmt.setBigDecimal(23,pData.getTotalAmount());
        pstmt.setDate(24,DBAccess.toSQLDate(pData.getAddDate()));
        pstmt.setString(25,pData.getAddBy());
        pstmt.setDate(26,DBAccess.toSQLDate(pData.getModDate()));
        pstmt.setString(27,pData.getModBy());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   INVOICE_ID="+pData.getInvoiceId());
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   SHIPPING_HISTORY_ID="+pData.getShippingHistoryId());
            log.debug("SQL:   INVOICE_NUM="+pData.getInvoiceNum());
            log.debug("SQL:   INVOICE_DATE="+pData.getInvoiceDate());
            log.debug("SQL:   ERP_PO_NUM="+pData.getErpPoNum());
            log.debug("SQL:   ERP_ORDER_NUM="+pData.getErpOrderNum());
            log.debug("SQL:   REF_ORDER_NUM="+pData.getRefOrderNum());
            log.debug("SQL:   DISTRIBUTOR_SHIPMENT_ID="+pData.getDistributorShipmentId());
            log.debug("SQL:   BILL_TO_NAME="+pData.getBillToName());
            log.debug("SQL:   BILL_TO_ADDRESS_1="+pData.getBillToAddress1());
            log.debug("SQL:   BILL_TO_ADDRESS_2="+pData.getBillToAddress2());
            log.debug("SQL:   BILL_TO_ADDRESS_3="+pData.getBillToAddress3());
            log.debug("SQL:   BILL_TO_ADDRESS_4="+pData.getBillToAddress4());
            log.debug("SQL:   BILL_TO_CITY="+pData.getBillToCity());
            log.debug("SQL:   BILL_TO_STATE="+pData.getBillToState());
            log.debug("SQL:   BILL_TO_POSTAL_CODE="+pData.getBillToPostalCode());
            log.debug("SQL:   BILL_TO_COUNTRY="+pData.getBillToCountry());
            log.debug("SQL:   GROSS_AMOUNT="+pData.getGrossAmount());
            log.debug("SQL:   FREIGHT_AMOUNT="+pData.getFreightAmount());
            log.debug("SQL:   TAX_AMOUNT="+pData.getTaxAmount());
            log.debug("SQL:   OTHER_AMOUNT="+pData.getOtherAmount());
            log.debug("SQL:   TOTAL_AMOUNT="+pData.getTotalAmount());
            log.debug("SQL:   ADD_DATE="+pData.getAddDate());
            log.debug("SQL:   ADD_BY="+pData.getAddBy());
            log.debug("SQL:   MOD_DATE="+pData.getModDate());
            log.debug("SQL:   MOD_BY="+pData.getModBy());
	    log.debug("SQL: " + sql);
        }

        pstmt.executeUpdate();
        pstmt.close();

        pData.setDirty(false);
        return pData;
    }

    /**
     * Updates a InvoiceData object in the database.
     * @param pCon  An open database connection.
     * @param pData  A InvoiceData object to update. 
     * The "ModDate" field of the object will be set to the current date.
     * @return int Number of rows updated (0 or 1).
     * @throws            SQLException
     */
    public static int update(Connection pCon, InvoiceData pData)
        throws SQLException {

        int n = 0;

        String sql = "UPDATE CLW_INVOICE SET ORDER_ID = ?,SHIPPING_HISTORY_ID = ?,INVOICE_NUM = ?,INVOICE_DATE = ?,ERP_PO_NUM = ?,ERP_ORDER_NUM = ?,REF_ORDER_NUM = ?,DISTRIBUTOR_SHIPMENT_ID = ?,BILL_TO_NAME = ?,BILL_TO_ADDRESS_1 = ?,BILL_TO_ADDRESS_2 = ?,BILL_TO_ADDRESS_3 = ?,BILL_TO_ADDRESS_4 = ?,BILL_TO_CITY = ?,BILL_TO_STATE = ?,BILL_TO_POSTAL_CODE = ?,BILL_TO_COUNTRY = ?,GROSS_AMOUNT = ?,FREIGHT_AMOUNT = ?,TAX_AMOUNT = ?,OTHER_AMOUNT = ?,TOTAL_AMOUNT = ?,ADD_DATE = ?,ADD_BY = ?,MOD_DATE = ?,MOD_BY = ? WHERE INVOICE_ID = ?";

        PreparedStatement pstmt = pCon.prepareStatement(sql);

        
        pData.setModDate(new java.util.Date(System.currentTimeMillis()));

        int i = 1;
        
        pstmt.setInt(i++,pData.getOrderId());
        pstmt.setInt(i++,pData.getShippingHistoryId());
        pstmt.setString(i++,pData.getInvoiceNum());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getInvoiceDate()));
        pstmt.setString(i++,pData.getErpPoNum());
        pstmt.setString(i++,pData.getErpOrderNum());
        pstmt.setString(i++,pData.getRefOrderNum());
        pstmt.setString(i++,pData.getDistributorShipmentId());
        pstmt.setString(i++,pData.getBillToName());
        pstmt.setString(i++,pData.getBillToAddress1());
        pstmt.setString(i++,pData.getBillToAddress2());
        pstmt.setString(i++,pData.getBillToAddress3());
        pstmt.setString(i++,pData.getBillToAddress4());
        pstmt.setString(i++,pData.getBillToCity());
        pstmt.setString(i++,pData.getBillToState());
        pstmt.setString(i++,pData.getBillToPostalCode());
        pstmt.setString(i++,pData.getBillToCountry());
        pstmt.setBigDecimal(i++,pData.getGrossAmount());
        pstmt.setBigDecimal(i++,pData.getFreightAmount());
        pstmt.setBigDecimal(i++,pData.getTaxAmount());
        pstmt.setBigDecimal(i++,pData.getOtherAmount());
        pstmt.setBigDecimal(i++,pData.getTotalAmount());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getAddDate()));
        pstmt.setString(i++,pData.getAddBy());
        pstmt.setDate(i++,DBAccess.toSQLDate(pData.getModDate()));
        pstmt.setString(i++,pData.getModBy());
        pstmt.setInt(i++,pData.getInvoiceId());

        if (log.isDebugEnabled()) {
            log.debug("SQL:   ORDER_ID="+pData.getOrderId());
            log.debug("SQL:   SHIPPING_HISTORY_ID="+pData.getShippingHistoryId());
            log.debug("SQL:   INVOICE_NUM="+pData.getInvoiceNum());
            log.debug("SQL:   INVOICE_DATE="+pData.getInvoiceDate());
            log.debug("SQL:   ERP_PO_NUM="+pData.getErpPoNum());
            log.debug("SQL:   ERP_ORDER_NUM="+pData.getErpOrderNum());
            log.debug("SQL:   REF_ORDER_NUM="+pData.getRefOrderNum());
            log.debug("SQL:   DISTRIBUTOR_SHIPMENT_ID="+pData.getDistributorShipmentId());
            log.debug("SQL:   BILL_TO_NAME="+pData.getBillToName());
            log.debug("SQL:   BILL_TO_ADDRESS_1="+pData.getBillToAddress1());
            log.debug("SQL:   BILL_TO_ADDRESS_2="+pData.getBillToAddress2());
            log.debug("SQL:   BILL_TO_ADDRESS_3="+pData.getBillToAddress3());
            log.debug("SQL:   BILL_TO_ADDRESS_4="+pData.getBillToAddress4());
            log.debug("SQL:   BILL_TO_CITY="+pData.getBillToCity());
            log.debug("SQL:   BILL_TO_STATE="+pData.getBillToState());
            log.debug("SQL:   BILL_TO_POSTAL_CODE="+pData.getBillToPostalCode());
            log.debug("SQL:   BILL_TO_COUNTRY="+pData.getBillToCountry());
            log.debug("SQL:   GROSS_AMOUNT="+pData.getGrossAmount());
            log.debug("SQL:   FREIGHT_AMOUNT="+pData.getFreightAmount());
            log.debug("SQL:   TAX_AMOUNT="+pData.getTaxAmount());
            log.debug("SQL:   OTHER_AMOUNT="+pData.getOtherAmount());
            log.debug("SQL:   TOTAL_AMOUNT="+pData.getTotalAmount());
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
     * Deletes a InvoiceData object with the specified
     * key from the database.
     * @param pCon  An open database connection.
     * @param pInvoiceId Key of object to be deleted.
     * @return int Number of rows deleted (0 or 1).
     * @throws            SQLException
     */
    public static int remove(Connection pCon, int pInvoiceId)
        throws SQLException{
        int n = 0;
        String sql="DELETE FROM CLW_INVOICE WHERE INVOICE_ID = " + pInvoiceId;

        if (log.isDebugEnabled()) {
	    log.debug("SQL: " + sql);
        }

        Statement stmt = pCon.createStatement();
        n = stmt.executeUpdate(sql);
        stmt.close();

        return n;
     }

    /**
     * Deletes InvoiceData objects that match the specified criteria.
     * @param pCon  An open database connection.
     * @param pCriteria A DBCriteria with the SQL 'where clause'
     * @return int Number of rows deleted.
     * @throws            SQLException
     */
    public static int remove(Connection pCon, DBCriteria pCriteria)
        throws SQLException {
        int n = 0;
        StringBuffer sqlBuf = new StringBuffer("DELETE FROM CLW_INVOICE");
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

